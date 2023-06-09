package es.csic.lcsc.keycloak.provider.requiredaction;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.authentication.requiredactions.util.UpdateProfileContext;
import org.keycloak.authentication.requiredactions.util.UserUpdateProfileContext;
import org.keycloak.email.EmailException;

import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.forms.login.freemarker.model.ProfileBean;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.UserModel;
import org.keycloak.services.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.util.StdDateFormat;

import es.csic.lcsc.keycloak.provider.config.LcscConfig;
import es.csic.lcsc.keycloak.provider.config.LcscConfigHelper;
import es.csic.lcsc.keycloak.provider.config.LcscConfigRealm;
import es.csic.lcsc.keycloak.provider.email.LcscEmailTemplateProvider;

import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Niko Köbler, https://www.n-k.de, @dasniko
 */
public class AdminValidationRequiredAction implements RequiredActionProvider, RequiredActionFactory {
	private static Logger LOG=LoggerFactory.getLogger(AdminValidationRequiredAction.class);

	public static final String PROVIDER_ID = "lcsc-admin-validation";
	public static final String DISPLAY_TEXT = "Admin Validation";
	public static final String EMAIL_SENT_ATTRIBUTE= "admin_email_sent";

	private LcscConfig config;

	@Override
	public InitiatedActionSupport initiatedActionSupport() {
		return InitiatedActionSupport.SUPPORTED;
	}

	private boolean isMemberOf(UserModel user, List<String> groups){
		//TODO Optimizar esto (¿Cache, ID en setup/post-init,...?)
		if(groups==null || groups.isEmpty())
			return false;
		Iterator<GroupModel> it = user.getGroupsStream().iterator();
		while (it.hasNext()){		
			GroupModel group = it.next();
			for (String cfgGroup : groups){
				if(group.getName().equals(cfgGroup))
					return true;
			}
		}
		return false;
	}

	public boolean hasRequiredAction(UserModel user){
		Iterator<String> it = user.getRequiredActionsStream().iterator();
		while(it.hasNext()){
			String action =it.next();
			if(PROVIDER_ID.equals(action))return true;
		}
		return false;
	}
	@Override
	public void evaluateTriggers(RequiredActionContext context) {
		LOG.trace("Evaluating Triggers...");
		LcscConfigRealm realmCfg = config.getRealm(context.getRealm().getName());

		if (realmCfg.isEnabled()){
			if(hasRequiredAction(context.getUser())){
				LOG.debug("User already has the required action");
				return;
			}
			if(isMemberOf(context.getUser(),realmCfg.getGroups())){
				LOG.debug("User is in AutoBlock Groups");
				context.getUser().addRequiredAction(PROVIDER_ID);
			}else{
				LOG.debug("User is not in Groups");
			}
		}else{
			LOG.debug("Auto block by Realm/group "+context.getRealm().getName()+ " is disabled by "+
			((realmCfg.getName()==null)?"Default":realmCfg.getName())+
			" Realm Config");
		}
	}

	private void sendEmail(RequiredActionContext context) {

		LcscEmailTemplateProvider email = new LcscEmailTemplateProvider(context.getSession(),config);
		email.setAuthenticationSession(context.getAuthenticationSession()).setRealm(context.getRealm()).setUser(context.getUser());
		
		try {
			email.sendRegisterRequest();
		} catch (EmailException e) {
			LOG.error("Error Sending Mail", e);
		}
	}

	@Override
	public void requiredActionChallenge(RequiredActionContext context) {
		LcscConfigRealm realmCfg = config.getRealm(context.getRealm().getName());
		UserModel user = context.getUser();
		if(!isMemberOf(user,realmCfg.getGroups())){
			LOG.debug("The user "+user.getUsername()+" has been validated");
			//One Admin has granted access
			context.success();
			user.removeRequiredAction(PROVIDER_ID);

			return;
		}
		
		if(Validation.isBlank(user.getFirstAttribute(EMAIL_SENT_ATTRIBUTE))){
			LOG.debug("Sending email to admins requested by user: "+ user.getUsername());
			sendEmail(context);
			user.setSingleAttribute(EMAIL_SENT_ATTRIBUTE, StdDateFormat.instance.format(new Date()));
		}
        Response challenge;
		challenge = createForm(context,null);
		context.challenge(challenge);

	}

	@Override
	public void processAction(RequiredActionContext context) {
		UserModel user=context.getUser();
        LOG.debug("Re-sending email to admins requested by user: "+user.getUsername());
		user.removeAttribute(EMAIL_SENT_ATTRIBUTE);
        requiredActionChallenge(context);
	}

	@Override
	public void close() {
	}

	private Response createForm(RequiredActionContext context, Consumer<LoginFormsProvider> formConsumer) {
		LoginFormsProvider form = context.form();
		UpdateProfileContext userBasedContext1 = new UserUpdateProfileContext(context.getRealm(),context.getUser());
		form.setAttribute("user",new ProfileBean(userBasedContext1,context.getHttpRequest().getDecodedFormParameters()));
		form.setInfo("adminVerifyEmailInstruction0",context.getRealm().getName());
		return form.createForm("admin-validation.ftl");
	}

	@Override
	public RequiredActionProvider create(KeycloakSession session) {
		return this;
	}

	@Override
	public void init(Scope config) {
		try{
			String path= config.get("config-file");
			if (Validation.isBlank(path)){
				LOG.info("No Config presented via --spi-required-action-lcsc-admin-validation-config-file=, using default");
				this.config=LcscConfigHelper.loadDefault();
				return;
			}
			InputStream is = this.getClass().getResourceAsStream(path);
			if(is == null){
				LOG.debug("Not found by classloader");
				File f = new File(path);
				if(f.exists() && f.isFile()){
					is = new FileInputStream(f);
				}
			}
			if (is == null){
				LOG.error("Configuration: "+path+" Not Found");
				LOG.warn("Downgrading to default configuration");
				this.config=LcscConfigHelper.loadDefault();
				return;
			}
			this.config=LcscConfigHelper.load(is);
			is.close();
			LOG.info("Configuration: "+path+" Loaded");
		}catch(Exception e){
			LOG.error("Error loading LCSC Providers",e);
		}

	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {

	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public String getDisplayText() {
		return DISPLAY_TEXT;
	}

}
