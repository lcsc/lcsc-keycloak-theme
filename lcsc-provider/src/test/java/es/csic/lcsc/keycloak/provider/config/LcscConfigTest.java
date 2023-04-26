package es.csic.lcsc.keycloak.provider.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class LcscConfigTest {

    @Test
    public void dumpBasicConfig(){
        LcscConfig cfg = new LcscConfig();
        cfg.setEmail(new LcscConfigEmail("keycloak@lcsc.csic.es","kc_admins@lcsc.csic.es, admin@lcsc.csic.es"));

        String dump= LcscConfigHelper.dump(cfg);
        System.out.println(dump);
    }

    @Test
    public void testLoadConfig(){
        InputStream is = this.getClass().getResourceAsStream("/lcsc-config.yaml");
        assertNotNull(is, "Default Config Not Found");
        LcscConfig tmp = LcscConfigHelper.load(is);
        System.out.println(tmp);
    }

    @Test
    public void testLoadDefault() throws IOException{
        LcscConfig tmp = LcscConfigHelper.loadDefault();
        System.out.println(tmp);
    }
}
