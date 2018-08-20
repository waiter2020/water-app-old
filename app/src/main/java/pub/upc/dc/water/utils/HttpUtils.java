package pub.upc.dc.water.utils;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedList;
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
import pub.upc.dc.water.config.Config;
import pub.upc.dc.water.data.AppData;

public class HttpUtils {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    final static LinkedList<String> strings = new LinkedList<>();

    public static String post(final Map<String,? extends Object> map, final String url) {

        Thread t=new Thread(new Runnable() {

            @Override
            public void run() {
                final RequestBody body = RequestBody.create(JSON, gson.toJson(map));
                Request.Builder requestBuilder = new Request.Builder()
                        .url(url)
                        .post(body);
                if (AppData.getToken()!=null&&!"".equals(AppData.getToken())){
                    requestBuilder.addHeader("Authorization", AppData.getToken());
                }
                Request build = requestBuilder.build();

                try {
                    Response execute = client.newCall(build).execute();
                    strings.add(execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        while (t.isAlive()){

        }
        return strings.size()>0?strings.remove(0):null;
    }



    public static String postObject(final  Object o, final String url) {

        Thread t=new Thread(new Runnable() {

            @Override
            public void run() {
                final RequestBody body = RequestBody.create(JSON, gson.toJson(o));
                Request.Builder requestBuilder = new Request.Builder()
                        .url(url)
                        .post(body);
                if (AppData.getToken()!=null&&!"".equals(AppData.getToken())){
                    requestBuilder.addHeader("Authorization", AppData.getToken());
                }
                Request build = requestBuilder.build();
                try {
                    Response execute = client.newCall(build).execute();
                    strings.add(execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        while (t.isAlive()){

        }
        return strings.size()>0?strings.remove(0):null;
    }

    public static String get(final Map<String,? extends Object> map, final String url) {

        Thread t=new Thread(new Runnable() {

            @Override
            public void run() {
                StringBuffer stringBuffer = new StringBuffer(url);
                if (map!=null&&map.size()>0) {
                    stringBuffer.append("?");
                    for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
                        stringBuffer.append(entry.getKey()+"="+entry.getValue());
                        stringBuffer.append("&");
                    }
                    stringBuffer.deleteCharAt(stringBuffer.length()-1);
                }

                Request request = new Request.Builder()
                        .url(stringBuffer.toString())
                        .get()
                        .addHeader("Authorization", AppData.getToken())
                        .build();

                try {
                    Response execute = client.newCall(request).execute();
                    strings.add(execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        while (t.isAlive()){

        }
        return strings.size()>0?strings.remove(0):null;
    }
}
