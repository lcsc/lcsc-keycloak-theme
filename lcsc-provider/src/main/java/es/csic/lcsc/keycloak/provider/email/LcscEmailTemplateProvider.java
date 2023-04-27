package es.csic.lcsc.keycloak.provider.email;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.email.EmailException;
import org.keycloak.email.freemarker.FreeMarkerEmailTemplateProvider;
import org.keycloak.models.KeycloakSession;

import es.csic.lcsc.keycloak.provider.config.LcscConfig;

public class LcscEmailTemplateProvider extends FreeMarkerEmailTemplateProvider{


    private LcscConfig lcscConfig;

    public LcscEmailTemplateProvider(KeycloakSession session, LcscConfig lcscConfig) {
        super(session);
        this.lcscConfig=lcscConfig;
    }

    public void sendRegisterRequest() throws EmailException{

        Map<String, Object> attributes = new HashMap<>(this.attributes);

        List<Object> subjectAttributes =  Arrays.asList(realm.getName(), user.getFirstName(), user.getLastName());
        EmailTemplate email = processTemplate("adminVerifyEmailSubject",subjectAttributes, "email-register-request.ftl", attributes);
        Map<String, String> smtpConfig = new HashMap<>();
        smtpConfig.putAll(realm.getSmtpConfig());
        smtpConfig.put("from",lcscConfig.getEmail().getOrigin());
        smtpConfig.put("fromDisplayName",lcscConfig.getEmail().getOrigin());
        send(smtpConfig, email.getSubject(), email.getTextBody(), email.getHtmlBody(),lcscConfig.getEmail().getDestination());
    }
    
}
