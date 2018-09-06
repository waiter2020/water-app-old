package pub.upc.dc.water.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import cn.jpush.android.api.JPushInterface;
import pub.upc.dc.water.LoginActivity;
import pub.upc.dc.water.bean.EquipmentInfo;
import pub.upc.dc.water.bean.Family;
import pub.upc.dc.water.bean.Role;
import pub.upc.dc.water.bean.User;
import pub.upc.dc.water.bean.WaterCondition;
import pub.upc.dc.water.config.Config;
import pub.upc.dc.water.data.AppData;
import pub.upc.dc.water.data.EquipmentInfoContent;
import pub.upc.dc.water.data.PageBean;
import pub.upc.dc.water.data.UserContent;
import pub.upc.dc.water.update.UpdateInfo;

public class Action {
    private static Properties props;
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public static boolean login(Map<String, String> map, Context context) {
        props = Config.getProps(context);
        String url = props.getProperty("server") + props.getProperty("login");
        try {
        String post = HttpUtils.post(map, url);

            map.putAll((Map<? extends String, ? extends String>) gson.fromJson(post, new TypeToken<Map<String, String>>() {
            }.getType()));
        } catch (Exception e) {
            Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_LONG).show();
            return false;
        }
        String token = map.get("token");
        if (token != null) {
            AppData.setToken(token);
        } else {
            return false;
        }
        return true;
    }


    public static boolean register(User addUser, Context context) {
        props = Config.getProps(context);
        String url = props.getProperty("server") + props.getProperty("register");
        String post = HttpUtils.postObject(addUser, url);
        User user = null;
        try {
            user = gson.fromJson(post, User.class);
        } catch (Exception e) {
            Toast.makeText(context, "注册失败", Toast.LENGTH_LONG).show();
            return false;
        }
        if (user != null) {
            Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
            Map<String, String> map = new TreeMap<>();
            map.put("username", addUser.getUsername());
            map.put("passwd", addUser.getPasswd());
            login(map, context);
            AppData.setUser(user);
        } else {
            return false;
        }
        return true;
    }

    public static boolean getEquipInfo(Context context) {
        String url = props.getProperty("server") + props.getProperty("equip_get_infos");
        String post = HttpUtils.get(null, url);
        try {
            List<EquipmentInfo> list = new ArrayList<>();
            list.addAll((Collection<? extends EquipmentInfo>) gson.fromJson(post, new TypeToken<ArrayList<EquipmentInfo>>() {
            }.getType()));
            EquipmentInfoContent.setITEMS(list);
        } catch (Exception e) {
            if (post == null || "".equals(post)) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "请先创建家庭组", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    public static String addEquip(String equipId,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_add");
        Map<String,String> map = new HashMap<>();
        map.put("equipId",equipId);
        return HttpUtils.post(map,url);
    }

    public static String deleteEquip(String equipId,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_delete");
        Map<String,String> map = new HashMap<>();
        map.put("equipId",equipId);
        return HttpUtils.post(map,url);
    }

    public static EquipmentInfo getEquipInfoById(String equipId,Context context) {
        EquipmentInfo equipmentInfo=null;
        String url = props.getProperty("server") + props.getProperty("equip_get_info");
        Map<String,String> map = new HashMap<>();
        map.put("equipId",equipId);
        String post = HttpUtils.get(map, url);
        try {
            equipmentInfo = gson.fromJson(post, EquipmentInfo.class);
        } catch (Exception e) {
            Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
            return null;
        }
        return equipmentInfo;
    }


    public static String openOrCloseEquip(String equipId,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_open_close");
        Map<String,String> map = new HashMap<>();
        map.put("equipId",equipId);
        return HttpUtils.post(map,url);
    }

    public static String getEquipData(String equipId,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_get_data");
        Map<String,String> map = new HashMap<>();
        map.put("equipId",equipId);
        return HttpUtils.post(map,url);
    }

    public static String restartEquip(String equipId,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_restart");
        Map<String,String> map = new HashMap<>();
        map.put("equipId",equipId);
        return HttpUtils.post(map,url);
    }

    public static String changeModelEquip(String equipId,int model,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_change_model");
        Map<String,Object> map = new HashMap<>();
        map.put("equipId",equipId);
        map.put("model",model);
        return HttpUtils.post(map,url);
    }

    public static String changeNameEquip(String equipId,String name,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_change_name");
        Map<String,Object> map = new HashMap<>();
        map.put("equipId",equipId);
        map.put("name",name);
        return HttpUtils.post(map,url);
    }

    public static String changeThresholdEquip(Map<String,Object> map,Context context){
        String url = props.getProperty("server") + props.getProperty("equip_change_threshold");
        return HttpUtils.post(map,url);
    }

    public static boolean getUserInfo(Context context) {
        String url = props.getProperty("server") + props.getProperty("get_user_info");
        String post = HttpUtils.get(null, url);
        try {
            User user = gson.fromJson(post, User.class);
            AppData.setUser(user);
        } catch (Exception e) {
            Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean getFamily(Context context) {
        String url = props.getProperty("server") + props.getProperty("family_get_family");
        String get = HttpUtils.get(null, url);
        ArrayList<User> list = new ArrayList<>();
        try {


            list.addAll((Collection<? extends User>) gson.fromJson(get, new TypeToken<ArrayList<User>>() {
            }.getType()));
            UserContent.setITEMS(list);

        } catch (Exception e) {
            UserContent.setITEMS(list);
        }
        return true;
    }

    public static PageBean getWaterCondition(Map<String,Object> map,Context context) {
        String url = props.getProperty("server") + props.getProperty("water_condition_get_water_condition");
        String get = HttpUtils.get(map, url);
        PageBean<WaterCondition> pageBean=null;
        try {
            pageBean = gson.fromJson(get, new TypeToken<PageBean<WaterCondition>>(){}.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageBean;
    }

    public static String addUser(String userName,Context context){
        String url = props.getProperty("server") + props.getProperty("family_add_user");
        Map<String,String> map = new HashMap<>();
        map.put("userName",userName);
        return HttpUtils.post(map,url);
    }

    public static String changeUser(Map<String,Object> map,Context context){
        String url = props.getProperty("server") + props.getProperty("user_change");
        return HttpUtils.post(map,url);
    }
    public static String changeUserPwd(Map<String,Object> map,Context context){
        String url = props.getProperty("server") + props.getProperty("user_change_pwd");
        return HttpUtils.post(map,url);
    }

    public static String deleteUser(int id,Context context){
        String url = props.getProperty("server") + props.getProperty("family_delete_user");
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return HttpUtils.post(map,url);
    }

    public static String removeFamily(Context context){
        String url = props.getProperty("server") + props.getProperty("family_remove_family");
        return HttpUtils.post(null,url);
    }
    public static String exitFamily(Context context){
        String url = props.getProperty("server") + props.getProperty("family_exit_family");
        return HttpUtils.post(null,url);
    }

    public static boolean createFamily(Family addFamily, Context context) {
        props = Config.getProps(context);
        String url = props.getProperty("server") + props.getProperty("family_create_family");
        String post = HttpUtils.postObject(addFamily, url);
        User user = null;
        try {
            user = gson.fromJson(post, User.class);
        } catch (Exception e) {
            Toast.makeText(context, "创建失败", Toast.LENGTH_LONG).show();
            return false;
        }
        if (user != null) {
            Toast.makeText(context, "创建成功", Toast.LENGTH_LONG).show();
            AppData.setUser(user);
        } else {
            return false;
        }
        return true;
    }


    public static void logout(Context context) {
        JPushInterface.setTags(context,AppData.getUser().getId(),new HashSet<String>());
        AppData.setUser(null);
        AppData.setToken(null);
        UserContent.setITEMS(new ArrayList<User>());
        EquipmentInfoContent.setITEMS(new ArrayList<EquipmentInfo>());
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("auto_login",false);
        context.startActivity(intent);
        Activity activity = (Activity) context;
        activity.finish();
    }


    public static UpdateInfo update(Context context){
        String url = props.getProperty("server") + props.getProperty("app_update");
        String post = HttpUtils.get(null, url);
        UpdateInfo updateInfo=null;
        try {
            updateInfo= gson.fromJson(post, UpdateInfo.class);
        } catch (Exception e) {
            Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
        }
        return updateInfo;
    }



}
