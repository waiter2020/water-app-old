package pub.upc.dc.water.bean;



import java.io.Serializable;
import java.util.Date;

/**
 * Created by  waiter on 18-6-18.
 * @author waiter
 */

public class EquipmentInfo implements Serializable {

    private Long id;
	private String equipId;
	private String name;
    /**
     * 阈门开关
     */
	private boolean open;
    /**
     * 阈值类型
     */
	private int thresholdType;
    /**
     * 阈值大小
     */
	private int thresholdValue;
    /**
     * 检漏模式
     */
	private int model;
    /**
     * 是否在线
     */
    private boolean online;
    /**
     * 连接id
     */
    private String loginId;
	private double locLongitude;
	private double locLatitude;
	private double waterUsage;
	private int equipState;
    /**
     * 最后一个状态的时间
     */
	private Date endStateTime;

    private boolean lowQuantityOfElectricity;

	/**
	 * 标明此表属于哪个家庭
	 */
	private Family family;
	public EquipmentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

    public EquipmentInfo(String equipId, double locLongitude, double locLatitude, int waterUsage, int equipState) {
        this.equipId = equipId;
        this.locLongitude = locLongitude;
        this.locLatitude = locLatitude;
        this.waterUsage = waterUsage;
        this.equipState = equipState;
    }

    public EquipmentInfo(String equipId, int waterUsage, int equipState) {
        this.equipId = equipId;
        this.waterUsage = waterUsage;
        this.equipState = equipState;
    }

    @Override
    public String toString() {
        return "EquipmentInfo{" +
                "id=" + id +
                ", equipId='" + equipId + '\'' +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", thresholdType=" + thresholdType +
                ", thresholdValue=" + thresholdValue +
                ", model=" + model +
                ", online=" + online +
                ", loginId='" + loginId + '\'' +
                ", locLongitude=" + locLongitude +
                ", locLatitude=" + locLatitude +
                ", waterUsage=" + waterUsage +
                ", equipState=" + equipState +
                ", endStateTime=" + endStateTime +
                ", lowQuantityOfElectricity=" + lowQuantityOfElectricity +
                ", family=" + family +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        open = open;
    }

    public int getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType = thresholdType;
    }

    public int getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(int thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public double getLocLongitude() {
        return locLongitude;
    }

    public void setLocLongitude(double locLongitude) {
        this.locLongitude = locLongitude;
    }

    public double getLocLatitude() {
        return locLatitude;
    }

    public void setLocLatitude(double locLatitude) {
        this.locLatitude = locLatitude;
    }

    public double getWaterUsage() {
        return waterUsage;
    }

    public void setWaterUsage(double waterUsage) {
        this.waterUsage = waterUsage;
    }

    public int getEquipState() {
        return equipState;
    }

    public void setEquipState(int equipState) {
        this.equipState = equipState;
    }

    public Date getEndStateTime() {
        return endStateTime;
    }

    public void setEndStateTime(Date endStateTime) {
        this.endStateTime = endStateTime;
    }

    public boolean isLowQuantityOfElectricity() {
        return lowQuantityOfElectricity;
    }

    public void setLowQuantityOfElectricity(boolean lowQuantityOfElectricity) {
        this.lowQuantityOfElectricity = lowQuantityOfElectricity;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
}
