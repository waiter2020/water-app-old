package pub.upc.dc.water.update;

public class UpdateInfo {
    //app名字
    private String appname;
    //服务器版本
    private double serverVersion;
    //服务器标志
    private String serverFlag;
    //强制升级
    private String lastForce;
    //app最新版本地址
    private String updateUrl;
    //升级信息
    private String upgradeinfo;
    public UpdateInfo(){}

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public double getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(double serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getServerFlag() {
        return serverFlag;
    }

    public void setServerFlag(String serverFlag) {
        this.serverFlag = serverFlag;
    }

    public String getLastForce() {
        return lastForce;
    }

    public void setLastForce(String lastForce) {
        this.lastForce = lastForce;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpgradeinfo() {
        return upgradeinfo;
    }

    public void setUpgradeinfo(String upgradeinfo) {
        this.upgradeinfo = upgradeinfo;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "appname='" + appname + '\'' +
                ", serverVersion='" + serverVersion + '\'' +
                ", serverFlag='" + serverFlag + '\'' +
                ", lastForce='" + lastForce + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", upgradeinfo='" + upgradeinfo + '\'' +
                '}';
    }
}