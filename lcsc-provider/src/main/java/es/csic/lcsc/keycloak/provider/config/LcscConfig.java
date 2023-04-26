package es.csic.lcsc.keycloak.provider.config;

import java.beans.Transient;
import java.util.List;

import org.keycloak.services.validation.Validation;

public class LcscConfig {
    private LcscConfigEmail email;
    private List<LcscConfigRealm> realms;
    private LcscConfigRealm defaultRealm;

    public LcscConfig(){}


    public LcscConfigEmail getEmail() {
        return email;
    }

    public void setEmail(LcscConfigEmail email) {
        this.email = email;
    }

    public List<LcscConfigRealm> getRealms() {
        return realms;
    }

    public void setRealms(List<LcscConfigRealm> realms) {
        this.realms = realms;
    }


    @Override
    public String toString() {
        return "LcscConfig [email=" + email + ", realms=" + realms + "]";
    }
    
    @Transient
    public LcscConfigRealm getRealm(String name){
        if(Validation.isBlank(name))
            return getDefaultRealm();
        for (LcscConfigRealm realm : realms){
            if(name.equals(realm.getName())){
                return realm;
            }
        }
        return getDefaultRealm();
    }

    @Transient
    public LcscConfigRealm getDefaultRealm(){
        if(this.defaultRealm==null){
            synchronized (this){
                if (this.defaultRealm==null){
                    for (LcscConfigRealm realm : realms){
                        if(realm.getName()==null){
                            this.defaultRealm=realm;
                            break;
                        }
                    }
                    if(this.defaultRealm==null){
                        this.defaultRealm=new LcscConfigRealm();
                        this.defaultRealm.setEnabled(false);
                    }
                }
            }
        }
        return this.defaultRealm;
    }
}
