package pub.upc.dc.water.data;

import pub.upc.dc.water.bean.Family;
import pub.upc.dc.water.bean.User;

public class AppData {

    private static String token;
    private static User user;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppData.token = token;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppData.user = user;
    }
}
