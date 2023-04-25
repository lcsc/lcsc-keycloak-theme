package es.csic.lcsc.keycloak.provider.formaction;

public enum LcscLicenses {
    No(false,false,"license.no"),
    NonCommercial(false,"license.NonCommercial"),
    Commercial(true,"license.Commercial");

    private boolean valid;
    private boolean commercial;
    private String msg;
    LcscLicenses(boolean valid, boolean commercial, String msg){
        this.valid=valid;
        this.commercial=commercial;
        this.msg=msg;
    }
    LcscLicenses(boolean commercial, String msg){
        this(true,commercial,msg);
    }
    public boolean isValid(){
        return valid;
    }
    public boolean isComercial(){
        return commercial;
    }
    public String getMsg(){
        return msg;
    }
}
