package pub.upc.dc.water.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable{
    private int id;
    private String username;
    private String phoneNumber;
    private String email;
    private String passwd;
    /**
     *     生日
     */
    private Date birth;
    private boolean enabled;
    /**
     * 标明是哪个家庭
     */

    private Family family;
    /**
     * 权限列表
     */
    private List<Role> authorities ;

    public User() {
        super();
    }

    public User(int id, String username, String phoneNumber, String email, String passwd, Date birth, boolean enabled, Family family, List<Role> authorities) {
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwd = passwd;
        this.birth = birth;
        this.enabled = enabled;
        this.family = family;
        this.authorities = authorities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", birth=" + birth +
                ", enabled=" + enabled +
                ", family=" + family +
                ", authorities=" + authorities +
                '}';
    }
}
