package pub.upc.dc.water.data;

import java.util.ArrayList;
import java.util.List;

import pub.upc.dc.water.bean.EquipmentInfo;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EquipmentInfoContent {

    /**
     * An array of sample (dummy) items.
     */
    public static  List<EquipmentInfo> ITEMS = new ArrayList<EquipmentInfo>();


    private static void addItem(EquipmentInfo item) {
        ITEMS.add(item);
    }



    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static List<EquipmentInfo> getITEMS() {
        return ITEMS;
    }

    public static void setITEMS(List<EquipmentInfo> ITEMS) {
        EquipmentInfoContent.ITEMS = ITEMS;
    }

    public static int getCOUNT() {
        return ITEMS.size();
    }


}
