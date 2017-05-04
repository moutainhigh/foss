package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.util.DateUtils;

public class WKSyncLoadTaskEntity {

	private static final String LONG_DISTANCE_LOAD = "LONG_DISTANCE_LOAD";

	private static final String LOADING = "LOADING";

	private static final String UNEXECUTE = "UNEXECUTE";

	private String loadTaskNo; // 装车任务编号

	private String vehicleNo; // 车牌号

	private String driverNo; // 司机工号

	private String origOrgCode; // 装车部门编号

	private Date loadBeginTime; // 装车开始时间

	private Date loadEndTime; // 装车结束时间

	private String arrivalDeptNo;// 到达部门编号

	private String loadDeptName;// 装车部门名称

	private String arrivalDeptName; // 到达部门名称

	private String platformNo; // 月台号

	private String driverName; // 司机名称

	private String driverPhone; // 司机电话

	private String arrivalDeptType; // 到达部门类型

	private String departDeptType; // 出发部门类型

	private BigDecimal loadWeight; // 装车重量

	private BigDecimal loadQty; // 装车件数

	private BigDecimal totalAmount; // 总金额

	private BigDecimal loadVolume; // 装车体积

	private String taskType; // 任务类型 短途、长途 、空运

	private String taskStatus; // 任务状态 未执行、执行中、已完成、已取消

	private String lineNo; // 线路编号

	private String lineName; // 线路名称

	private String truckOutboundPlanNo; // 发车计划编号

	private String loadMode; // 装车方式 PDA、PC

	private String loadType; // 装车类型 空运装车、汽运装车

	private String loadForm; // 装车形式 快递、与零担合车

	private String stationIsDepot; // 目的站是否营业部

	private String terminalstationVehicleNo; // 暂存点车牌号

	private String bizType; // 业务类型

	
	/**
	* @description 获取装车任务编号
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:05:18
	*/
	public String getLoadTaskNo() {
		return loadTaskNo;
	}

	
	/**
	* @description 装车任务编号
	* @param loadTaskNo
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:05:30
	*/
	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverNo() {
		return driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public Date getLoadBeginTime() {
		return loadBeginTime;
	}

	public void setLoadBeginTime(Date loadBeginTime) {
		this.loadBeginTime = loadBeginTime;
	}

	public Date getLoadEndTime() {
		return loadEndTime;
	}

	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}

	public String getArrivalDeptNo() {
		return arrivalDeptNo;
	}

	public void setArrivalDeptNo(String arrivalDeptNo) {
		this.arrivalDeptNo = arrivalDeptNo;
	}

	public String getLoadDeptName() {
		return loadDeptName;
	}

	public void setLoadDeptName(String loadDeptName) {
		this.loadDeptName = loadDeptName;
	}

	public String getArrivalDeptName() {
		return arrivalDeptName;
	}

	public void setArrivalDeptName(String arrivalDeptName) {
		this.arrivalDeptName = arrivalDeptName;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getArrivalDeptType() {
		return arrivalDeptType;
	}

	public void setArrivalDeptType(String arrivalDeptType) {
		this.arrivalDeptType = arrivalDeptType;
	}

	public String getDepartDeptType() {
		return departDeptType;
	}

	public void setDepartDeptType(String departDeptType) {
		this.departDeptType = departDeptType;
	}

	public BigDecimal getLoadWeight() {
		return loadWeight;
	}

	public void setLoadWeight(BigDecimal loadWeight) {
		this.loadWeight = loadWeight;
	}

	public BigDecimal getLoadQty() {
		return loadQty;
	}

	public void setLoadQty(BigDecimal loadQty) {
		this.loadQty = loadQty;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getLoadVolume() {
		return loadVolume;
	}

	public void setLoadVolume(BigDecimal loadVolume) {
		this.loadVolume = loadVolume;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getTruckOutboundPlanNo() {
		return truckOutboundPlanNo;
	}

	public void setTruckOutboundPlanNo(String truckOutboundPlanNo) {
		this.truckOutboundPlanNo = truckOutboundPlanNo;
	}

	public String getLoadMode() {
		return loadMode;
	}

	public void setLoadMode(String loadMode) {
		this.loadMode = loadMode;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getLoadForm() {
		return loadForm;
	}

	public void setLoadForm(String loadForm) {
		this.loadForm = loadForm;
	}

	public String getStationIsDepot() {
		return stationIsDepot;
	}

	public void setStationIsDepot(String stationIsDepot) {
		this.stationIsDepot = stationIsDepot;
	}

	public String getTerminalstationVehicleNo() {
		return terminalstationVehicleNo;
	}

	public void setTerminalstationVehicleNo(String terminalstationVehicleNo) {
		this.terminalstationVehicleNo = terminalstationVehicleNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	
	
	/**
	* @description 把wk那边的装车信息转为foss这边装车信息
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:04:11
	*/
	public LoadTaskEntity transEntity() {
		LoadTaskEntity loadTaskEntity = new LoadTaskEntity();
		loadTaskEntity.setBeCreateGapRep("N");
		loadTaskEntity.setDeliverBillNo("");
		loadTaskEntity.setGoodsType("");
		loadTaskEntity.setIsPicPackage("N");
		loadTaskEntity.setLine(lineName);
		loadTaskEntity.setLoadEndTime(DateUtils.convert(loadEndTime, DateUtils.DATE_TIME_FORMAT));
//		loadTaskEntity.setLoaderQty(0);//调用前设置
		loadTaskEntity.setLoadStartTime(DateUtils.convert(loadBeginTime, DateUtils.DATE_TIME_FORMAT));
		loadTaskEntity.setLoadWay(loadMode);
		loadTaskEntity.setOrigOrgCode(origOrgCode);
		loadTaskEntity.setOrigOrgName(loadDeptName);
		loadTaskEntity.setPlatformId("");
		loadTaskEntity.setPlatformNo(platformNo);
		if(UNEXECUTE.equals(taskStatus)) {
			loadTaskEntity.setState(LOADING);
		}
		else {
			loadTaskEntity.setState(taskStatus);
		}
		loadTaskEntity.setTaskNo(loadTaskNo);
		if(taskType != null) {
			if (taskType.contains("AIR")) {
				loadTaskEntity.setTaskType(LONG_DISTANCE_LOAD);
			} else {
				loadTaskEntity.setTaskType(taskType);
			}
		}
		loadTaskEntity.setTransitGoodsType(taskStatus);
		loadTaskEntity.setVehicleNo(vehicleNo);
		loadTaskEntity.setLoadSource(1);
		
		return loadTaskEntity;
	}
	
	/**
	* @description 把wk那边的更新装车信息转为foss装车信息
	* @return
	* @version 1.0
	* @author 335284
	* @update 2016年8月3日 10:24:54
	*/
	public LoadTaskEntity updateEntity() {
		LoadTaskEntity loadTaskEntity = new LoadTaskEntity();
		loadTaskEntity.setLine(lineName);
		loadTaskEntity.setLoadEndTime(DateUtils.convert(loadEndTime, DateUtils.DATE_TIME_FORMAT));
		loadTaskEntity.setLoadWay(loadMode);
		loadTaskEntity.setPlatformNo(platformNo);
		loadTaskEntity.setState(UNEXECUTE.equals(taskStatus) ? LOADING : taskStatus);
		loadTaskEntity.setTaskNo(loadTaskNo);
		if(taskType != null) {
			if (taskType.contains("AIR")) {
				loadTaskEntity.setTaskType(LONG_DISTANCE_LOAD);
			} else {
				loadTaskEntity.setTaskType(taskType);
			}
		}
		loadTaskEntity.setTransitGoodsType(taskStatus);
		loadTaskEntity.setVehicleNo(vehicleNo);
		return loadTaskEntity;
	}


	@Override
	public String toString() {
		return "WKSyncLoadTaskEntity [loadTaskNo=" + loadTaskNo
				+ ", vehicleNo=" + vehicleNo + ", driverNo=" + driverNo
				+ ", origOrgCode=" + origOrgCode + ", loadBeginTime="
				+ loadBeginTime + ", loadEndTime=" + loadEndTime
				+ ", arrivalDeptNo=" + arrivalDeptNo + ", loadDeptName="
				+ loadDeptName + ", arrivalDeptName=" + arrivalDeptName
				+ ", platformNo=" + platformNo + ", driverName=" + driverName
				+ ", driverPhone=" + driverPhone + ", arrivalDeptType="
				+ arrivalDeptType + ", departDeptType=" + departDeptType
				+ ", loadWeight=" + loadWeight + ", loadQty=" + loadQty
				+ ", totalAmount=" + totalAmount + ", loadVolume=" + loadVolume
				+ ", taskType=" + taskType + ", taskStatus=" + taskStatus
				+ ", lineNo=" + lineNo + ", lineName=" + lineName
				+ ", truckOutboundPlanNo=" + truckOutboundPlanNo
				+ ", loadMode=" + loadMode + ", loadType=" + loadType
				+ ", loadForm=" + loadForm + ", stationIsDepot="
				+ stationIsDepot + ", terminalstationVehicleNo="
				+ terminalstationVehicleNo + ", bizType=" + bizType + "]";
	}
	
}
