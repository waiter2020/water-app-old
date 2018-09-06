package pub.upc.dc.water.update;

import pub.upc.dc.water.R;

public class UpdateInformation {
    public static String appname = "终端水管理系统";
    public static double localVersion = 1;// 本地版本
    public static String versionName = ""; // 本地版本名
    public static double serverVersion = 1;// 服务器版本
    public static int serverFlag = 0;// 服务器标志
    public static int lastForce = 0;// 之前强制升级版本
    public static String updateurl = "";// 升级包获取地址
    public static String upgradeinfo = "";// 升级信息

    public static String downloadDir = "water";// 下载目录
}