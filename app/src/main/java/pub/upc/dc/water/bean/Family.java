package pub.upc.dc.water.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by  waiter on 18-6-18.
 *
 * @author waiter
 */

public class Family implements Serializable{

    private int id;
    /**
     * 家庭名称
     */
    private String familyName;
    private String admin;

    public Family() {
    }

    public Family(int id, String familyName, String admin) {
        this.id = id;
        this.familyName = familyName;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", familyName='" + familyName + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
