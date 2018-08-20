package pub.upc.dc.water;

import android.widget.Toast;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.upc.dc.water.bean.User;
import pub.upc.dc.water.config.Config;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.utils.HttpUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    @Test
    public void addi() {


    }

    @Test
    public void test(){
        Map<String,String> map = new TreeMap<>();
        User user = new User();
        user.setUsername("12345678");
        user.setPasswd("12345678");

        String url= "http://127.0.0.1:8080/auth";
        map.put("username","123456");
        map.put("passwd","123456");
        String post = HttpUtils.post(map, url);
        System.out.println(post);

    }
    @Test
    public void test2(){
        String url= "http://127.0.0.1:8080/api/equip/get_infos";
        String post = HttpUtils.get(null, url);
        System.out.println(post);
    }
}