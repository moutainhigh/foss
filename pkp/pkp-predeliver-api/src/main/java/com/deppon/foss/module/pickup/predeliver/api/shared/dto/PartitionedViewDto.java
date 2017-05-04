package com.deppon.foss.module.pickup.predeliver.api.shared.dto;


/**
 * 创建派送单(新)——分区查看DTO
 * 
 * @author 中软国际—李顺翔
 * @date 2015-04-23 14:34:17
 */
public class PartitionedViewDto {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Final
	 */
	private static final int BEGIN_NUM = 0;
	private static final int PAGE_SIZE = 100;
	/**
	 * 基本数据
	 */
	private String RegionalName;//区域名称
	private String RegionalCode;//区域编码
	private Integer TotalVotes;//总票数
	private Integer AbnormalVotes;//异常票数
	private double Waybill_Weight;//运单重量
	private double Waybill_Volume;//运单体积
	private Integer Waybill_Qty;//运单件数
	private String Zone_Car;//定区车
	private String Driver_Name;//司机
	private String Driver_Phone;//手机
	private String Car_Models;//车型
	private String Scheduling;//班次
	private double Clearance;//净空
	private double Load_Weight;//载重
	private double Carry_Goods;//带货

	
	/**
	 * 综合数据
	 */
	private String VolumeWeightNumber;//体积-重量-件数
	private String DriverInformation;//司机信息
	private String VehicleScheduling;//车辆排班
	private String VehicleInformation;//车辆信息
	
	/**
	 * 查询条件
	 */
	private String smallZone;//小区
	private String bigZone;//大区
	private String teamGroup;//车队组
	private String productCode;//运输性质
	private String deliverTime;//送货日期
	private String goodStatus;//货物状态
	private String vehicleNo;//车牌号
	private String waybillNos;//运单集合
	private PermissionControlDto permissionControlDto;//加载部门
	private String virtualCade;//上级区域虚拟编码
	private String smallZoneCodes;//小区编码集合
	private String bigZoneCodes;//大区编码集合
	private int start;
	private int limit;
	
	public String[] getSmallZoneCodes() {
		if(smallZoneCodes==null)
			return null;
		return smallZoneCodes.split(",");
	}

	public void setSmallZoneCodes(String smallZoneCodes) {
		this.smallZoneCodes = smallZoneCodes;
	}
	public String[] getBigZoneCodes() {
		if(bigZoneCodes==null)
			return null;
		return bigZoneCodes.split(",");
	}

	public void setBigZoneCodes(String bigZoneCodes) {
		this.bigZoneCodes = bigZoneCodes;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getVirtualCade() {
		return virtualCade;
	}
	
	public void setVirtualCade(String virtualCade) {
		this.virtualCade = virtualCade;
	}
	
	public Integer getAbnormalVotes() {
		return AbnormalVotes;
	}

	
	public String getBigZone() {
		return bigZone;
	}


	public String getCar_Models() {
		return Car_Models;
	}


	public double getCarry_Goods() {
		return Carry_Goods;
	}


	public double getClearance() {
		return Clearance;
	}
	public String getDeliverTime() {
		return deliverTime;
	}
	public String getDriver_Name() {
		return Driver_Name;
	}
	public String getDriver_Phone() {
		return Driver_Phone;
	}
	public String getDriverInformation() {
		String str=this.Driver_Name+"/"+this.Driver_Phone;
		if(str.equals("/")){
			return null;
		}
		return str;
	}
	public String getGoodStatus() {
		return goodStatus;
	}
	public double getLoad_Weight() {
		return Load_Weight;
	}
	
	public PermissionControlDto getPermissionControlDto() {
		return permissionControlDto;
	}

	public void setPermissionControlDto(PermissionControlDto permissionControlDto) {
		this.permissionControlDto = permissionControlDto;
	}

	public String getProductCode() {
		return productCode;
	}
	public String getRegionalCode() {
		return RegionalCode;
	}
	public String getRegionalName() {
		return RegionalName;
	}
	public String getScheduling() {
		return Scheduling;
	}
	public String getSmallZone() {
		return smallZone;
	}
	public String getTeamGroup() {
		return teamGroup;
	}
	public Integer getTotalVotes() {
		return TotalVotes;
	}
	public String getVehicleInformation() {
		String str=this.Clearance+"/"+this.Load_Weight;
		if(str.equals("0.0/0.0")){
			return null;
		}
		return str;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public String getVehicleScheduling() {
		String str=this.Car_Models+"/"+this.Scheduling;
		if(str.equals("null/null")){
			return null;
		}
		return str;
	}
	public String getVolumeWeightNumber() {
		return this.Waybill_Weight+"/"+this.Waybill_Volume+"/"+this.Waybill_Qty;
	}
	public Integer getWaybill_Qty() {
		return Waybill_Qty;
	}
	public double getWaybill_Volume() {
		return Waybill_Volume;
	}
	public double getWaybill_Weight() {
		return Waybill_Weight;
	}
	public String[] getWaybillNos() {
		if(waybillNos==null || waybillNos.trim().length()<=0){
			return null;
		}
		return waybillNos.split("\n");
	}
	public String getZone_Car() {
		return Zone_Car;
	}
	public void setAbnormalVotes(Integer abnormalVotes) {
		AbnormalVotes = abnormalVotes;
	}
	public void setBigZone(String bigZone) {
		this.bigZone = bigZone;
	}
	public void setCar_Models(String car_Models) {
		Car_Models = car_Models;
	}
	public void setCarry_Goods(double carry_Goods) {
		Carry_Goods = carry_Goods;
	}
	public void setClearance(double clearance) {
		Clearance = clearance;
	}
	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}
	public void setDriver_Name(String driver_Name) {
		Driver_Name = driver_Name;
	}
	public void setDriver_Phone(String driver_Phone) {
		Driver_Phone = driver_Phone;
	}
	public void setDriverInformation(String driverInformation) {
		DriverInformation = driverInformation;
	}
	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	public void setLoad_Weight(double load_Weight) {
		Load_Weight = load_Weight;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setRegionalCode(String regionalCode) {
		RegionalCode = regionalCode;
	}
	public void setRegionalName(String regionalName) {
		RegionalName = regionalName;
	}

	public void setScheduling(String scheduling) {
		Scheduling = scheduling;
	}
	public void setSmallZone(String smallZone) {
		this.smallZone = smallZone;
	}
	public void setTeamGroup(String teamGroup) {
		this.teamGroup = teamGroup;
	}
	public void setTotalVotes(Integer totalVotes) {
		TotalVotes = totalVotes;
	}
	public void setVehicleInformation(String vehicleInformation) {
		VehicleInformation = vehicleInformation;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public void setVehicleScheduling(String vehicleScheduling) {
		VehicleScheduling = vehicleScheduling;
	}
	public void setVolumeWeightNumber(String volumeWeightNumber) {
		VolumeWeightNumber = volumeWeightNumber;
	}
	public void setWaybill_Qty(Integer waybill_Qty) {
		Waybill_Qty = waybill_Qty;
	}
	public void setWaybill_Volume(double waybill_Volume) {
		Waybill_Volume = waybill_Volume;
	}
	public void setWaybill_Weight(double waybill_Weight) {
		Waybill_Weight = waybill_Weight;
	}
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}
	public void setZone_Car(String zone_Car) {
		Zone_Car = zone_Car;
	}	
}
