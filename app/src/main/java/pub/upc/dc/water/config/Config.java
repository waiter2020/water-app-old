package pub.upc.dc.water.config;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public  class Config {
    private static Properties props;

    public static Properties getProps(Context context) {
        if (props==null){
            // 给props进行初始化，
            try {
                InputStream in =context.getAssets().open("config.properties");
                props = new Properties();
                props.load(in);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return props;
    }
}
