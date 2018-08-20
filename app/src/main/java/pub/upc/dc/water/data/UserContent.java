package pub.upc.dc.water.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.upc.dc.water.bean.User;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class UserContent {

    /**
     * An array of sample (dummy) items.
     */
    public static  List<User> ITEMS = new ArrayList<User>();




    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static List<User> getITEMS() {
        return ITEMS;
    }

    public static void setITEMS(List<User> ITEMS) {
        UserContent.ITEMS = ITEMS;
    }
}
