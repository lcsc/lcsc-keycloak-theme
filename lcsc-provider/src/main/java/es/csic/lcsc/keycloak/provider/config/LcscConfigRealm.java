package es.csic.lcsc.keycloak.provider.config;

import java.util.List;

public class LcscConfigRealm {

    private String name;
    private Boolean enabled;
    private List<String> groups;
    
    public LcscConfigRealm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        if(enabled==null)
            enabled=Boolean.FALSE;
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "LcscConfigRealm [name=" + name + ", enabled=" + enabled + ", groups=" + groups + "]";
    }


    
}
