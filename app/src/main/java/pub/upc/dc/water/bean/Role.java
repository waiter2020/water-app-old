package pub.upc.dc.water.bean;


import java.io.Serializable;

/**
 * Created by  waiter on 18-6-18.
 * @author waiter
 *
 * 用户权限
 */

public class Role implements Serializable{

    private int id ;
    /**
     * 用户权限
     *
     */
    private String role;
    Role(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
