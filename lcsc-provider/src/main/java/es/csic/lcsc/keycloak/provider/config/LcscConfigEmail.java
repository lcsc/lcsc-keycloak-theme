package es.csic.lcsc.keycloak.provider.config;

public class LcscConfigEmail {
    private String origin;
    private String destination;

    public LcscConfigEmail() {
    }

    public LcscConfigEmail(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }
    
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "LcscConfigEmail [origin=" + origin + ", destination=" + destination + "]";
    }
    
}
