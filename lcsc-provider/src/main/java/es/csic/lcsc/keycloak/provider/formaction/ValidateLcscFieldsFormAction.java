package es.csic.lcsc.keycloak.provider.formaction;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;


import org.keycloak.Config.Scope;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.services.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateLcscFieldsFormAction implements FormAction, FormActionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateLcscFieldsFormAction.class);


	public static final String PROVIDER_ID = "lcsc-field-validation-action";
    public static final String DISPLAY_TYPE = "Validate LCSC Fields";
    public static final String HELP_TEXT = "Validate the required fields of LCSC Registration";
	private static Requirement[] REQUIREMENT_CHOICES = { Requirement.REQUIRED, Requirement.DISABLED };


    @Override
    public void close() {
        LOG.debug("ValidateLcscFieldsFormAction.close");
    }

    @Override
    public void buildPage(FormContext context, LoginFormsProvider form) {
        ////LOG.trace("ValidateLcscFieldsFormAction.buildPage");
        // Use context to get Configuration
        // Use form to add atributes (to show in the ftl)
        // https://www.keycloak.org/docs/latest/server_development/index.html#implementation-formaction-interface
        form.setAttribute("LcscLicenses", LcscLicenses.values().clone());
    }


    private String checkFieldBlank(MultivaluedMap<String, String> formData,String field,String message,List<FormMessage> errors){
        String value= formData.getFirst(field);
        if (Validation.isBlank(formData.getFirst(field))) {
            errors.add(new FormMessage(field, message));
            return null;
        }

        return value;
    }
    
    private LcscLicenses checkFieldLicense(MultivaluedMap<String, String> formData,String field,String message,List<FormMessage> errors){
        String value= formData.getFirst(field);
        if (Validation.isBlank(formData.getFirst(field))) {
            errors.add(new FormMessage(field, message));
            return null;
        }

        LcscLicenses ret=null;
        try{
            ret= Enum.valueOf(LcscLicenses.class,value);
        }catch(java.lang.IllegalArgumentException iae){
            // Do nothing
            LOG.error("Exception reading License" + iae.getMessage());
            LOG.debug("Exception reading License",iae);
        }
        if(ret == null || !ret.isValid()){
            errors.add(new FormMessage(field, message));
        }
        return ret;
    }
    

    @Override
    public void validate(ValidationContext context) {
     //   context.success();
        //LOG.trace("ValidateLcscFieldsFormAction.validate");


        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        List<FormMessage> errors = new ArrayList<>();

        //String eventError = Errors.INVALID_REGISTRATION;

        checkFieldBlank(formData,"user.attributes.institution","missingInstitution",errors);
        checkFieldBlank(formData,"user.attributes.usage","missingUsage",errors);
        checkFieldLicense(formData,"user.attributes.license","missingLicense",errors);

 
        if (errors.size() > 0) {
            context.validationError(formData, errors);
            return;

        } else {
            context.success();
        }
    }

    @Override
    public void success(FormContext context) {
        LOG.debug("ValidateLcscFieldsFormAction.success");
    }

    @Override
    public boolean requiresUser() {
        //LOG.trace("ValidateLcscFieldsFormAction.requiresUser");
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        //LOG.trace("ValidateLcscFieldsFormAction.configuredFor");
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        LOG.debug("ValidateLcscFieldsFormAction.setRequiredActions");
    }

    @Override
    public FormAction create(KeycloakSession session) {
        //LOG.trace("ValidateLcscFieldsFormAction.create");
        return this;
    }

    @Override
    public String getId() {
        //LOG.trace("ValidateLcscFieldsFormAction.getId");
        return PROVIDER_ID;
    }

    @Override
    public void init(Scope scope) {
        //LOG.trace("ValidateLcscFieldsFormAction.scope");
    }

    @Override
    public void postInit(KeycloakSessionFactory arg0) {
        //LOG.trace("ValidateLcscFieldsFormAction.postInit");
    }

    @Override
    public String getDisplayType() {
        //LOG.trace("ValidateLcscFieldsFormAction.getDisplayType");
        return DISPLAY_TYPE;
    }

    @Override
    public String getReferenceCategory() {
        //LOG.trace("ValidateLcscFieldsFormAction.getReferenceCategory");
        return null;
    }

    @Override
    public boolean isConfigurable() {
        //LOG.trace("ValidateLcscFieldsFormAction.isConfigurable");
        return false;
    }

    @Override
    public Requirement[] getRequirementChoices() {
        //LOG.trace("ValidateLcscFieldsFormAction.getRequirementChoices");
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        //LOG.trace("ValidateLcscFieldsFormAction.isUserSetupAllowed");
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        //LOG.trace("ValidateLcscFieldsFormAction.getConfigProperties");
        return null;
    }

    @Override
    public String getHelpText() {
        //LOG.trace("ValidateLcscFieldsFormAction.getHelpText");
        return HELP_TEXT;
    }
    
}
