package es.csic.lcsc.keycloak.provider.config;

import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class LcscConfigHelper {
    public static String dump(LcscConfig config){
        Yaml yaml = new Yaml();
        return yaml.dump(config);
    }

    public static LcscConfig load(InputStream is){
        Yaml yaml = new Yaml();
        return yaml.loadAs(is,LcscConfig.class);
    }

    public static LcscConfig loadDefault() throws IOException{
        InputStream is=null;
        LcscConfig ret = null;
        try{
        is = LcscConfigHelper.class.getResourceAsStream("/lcsc-config.yaml");
        ret = load(is);
        }finally{
            if(is!=null)
                is.close();
        }
        return ret;
    }
}
