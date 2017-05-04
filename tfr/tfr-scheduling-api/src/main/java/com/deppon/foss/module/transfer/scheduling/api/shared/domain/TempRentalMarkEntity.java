package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;


/**
 *租车标记表 
 **/
public class TempRentalMarkEntity {

	private String id;
	private String tempRentalMarkNO;//租车编号
	private String rentalCarUsetype;//租车用途
	private Date  useCarDate;//用车时间
	private String userCarReason ;//用车原因
	private String inviteVehicleNo;//约车编号
	private String acceptPerson;//约车受理人
	private String acceptPersonCode;//约车受理人工号
	private String smallTicketNum;//小票号
	private BigDecimal rentalAmount;//租车金额
	private BigDecimal kmsNum ;//公里数
	private String departureName;//出发站
	private String departureCode;//出发站编码
	private String destinationCode;//目的站
	private String destinationName;//目的站编码
	private String notes;//备注
	private String markDepartName;//标记部门名称
	private String markDepartCode;//标记部门编码
	private String createUserName;//创建人
	private String createUserCode;//创建人工号
	private Date createDate;//创建时间
	private String modifyUserNme;//修改人
	private String modifyUserCode;//修改人工号
	private Date modifyDate;//修改时间
	private String active;//状态
	private BigDecimal weigthTotal;//总重量
	private BigDecimal volumeTotal;//总体积
	private BigDecimal shallTakeGoodsQyt;//应走货票数
	private BigDecimal actualTakeGoodsQyt;//实际走货票数
	private String vehicleNo;//车牌号
	private BigDecimal selfVolume;//净空
	private String vehicleLenghtCode;//车型
	private String driverCode;//司机编码
	private String driverName;//司机姓名
	private String driverPhone;//司机电话
	private String isRepeateMark;//是否多次标记
	private String accruedState;//预提状态
	private String accruedWorkNo;//预提工作流号
	private String accruedWorkResult;//预提工作流处理结果
	//326027--begin
	private String salesVehiclePlatformName;//请车平台名称
	//车辆任务ID
	private String truckTaskId;
	private String bearFeesDept;//费用承担部门
	private String bearFeesDeptCode;//费用承担部门编码
	
	
	public String getBearFeesDept() {
		return bearFeesDept;
	}
	public void setBearFeesDept(String bearFeesDept) {
		this.bearFeesDept = bearFeesDept;
	}
	public String getBearFeesDeptCode() {
		return bearFeesDeptCode;
	}
	public void setBearFeesDeptCode(String bearFeesDeptCode) {
		this.bearFeesDeptCode = bearFeesDeptCode;
	}
	/**
	 * @return the truckTaskId
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}
	/**
	 * @param truckTaskId the truckTaskId to set
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	public String getSalesVehiclePlatformName() {
		return salesVehiclePlatformName;
	}
	public void setSalesVehiclePlatformName(String salesVehiclePlatformName) {
		this.salesVehiclePlatformName = salesVehiclePlatformName;
	}
	/*public String getNosalesVehiclePlatformName() {
		return nosalesVehiclePlatformName;
	}
	public void setNosalesVehiclePlatformName(String nosalesVehiclePlatformName) {
		this.nosalesVehiclePlatformName = nosalesVehiclePlatformName;
	}
	private String nosalesVehiclePlatformName;*/
	
	private String useVehiclePlatform;//使用请车平台

	
	public String getUseVehiclePlatform() {
		return useVehiclePlatform;
	}
	public void setUseVehiclePlatform(String useVehiclePlatform) {
		this.useVehiclePlatform = useVehiclePlatform;
	}
	//326027--end
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTempRentalMarkNO() {
		return tempRentalMarkNO;
	}
	public void setTempRentalMarkNO(String tempRentalMarkNO) {
		this.tempRentalMarkNO = tempRentalMarkNO;
	}
	public String getRentalCarUsetype() {
		return rentalCarUsetype;
	}
	public void setRentalCarUsetype(String rentalCarUsetype) {
		this.rentalCarUsetype = rentalCarUsetype;
	}
	public Date getUseCarDate() {
		return useCarDate;
	}
	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}
	public String getUserCarReason() {
		return userCarReason;
	}
	public void setUserCarReason(String userCarReason) {
		this.userCarReason = userCarReason;
	}
	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}
	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}
	public String getAcceptPerson() {
		return acceptPerson;
	}
	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}
	public String getAcceptPersonCode() {
		return acceptPersonCode;
	}
	public void setAcceptPersonCode(String acceptPersonCode) {
		this.acceptPersonCode = acceptPersonCode;
	}
	public String getSmallTicketNum() {
		return smallTicketNum;
	}
	public void setSmallTicketNum(String smallTicketNum) {
		this.smallTicketNum = smallTicketNum;
	}
	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}
	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	
	public BigDecimal getKmsNum() {
		return kmsNum;
	}
	public void setKmsNum(BigDecimal kmsNum) {
		this.kmsNum = kmsNum;
	}
	public String getDepartureName() {
		return departureName;
	}
	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}
	public String getDepartureCode() {
		return departureCode;
	}
	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMarkDepartName() {
		return markDepartName;
	}
	public void setMarkDepartName(String markDepartName) {
		this.markDepartName = markDepartName;
	}
	public String getMarkDepartCode() {
		return markDepartCode;
	}
	public void setMarkDepartCode(String markDepartCode) {
		this.markDepartCode = markDepartCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getModifyUserNme() {
		return modifyUserNme;
	}
	public void setModifyUserNme(String modifyUserNme) {
		this.modifyUserNme = modifyUserNme;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
	public BigDecimal getWeigthTotal() {
		return weigthTotal;
	}
	public void setWeigthTotal(BigDecimal weigthTotal) {
		this.weigthTotal = weigthTotal;
	}
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	
	public BigDecimal getShallTakeGoodsQyt() {
		return shallTakeGoodsQyt;
	}
	public void setShallTakeGoodsQyt(BigDecimal shallTakeGoodsQyt) {
		this.shallTakeGoodsQyt = shallTakeGoodsQyt;
	}
	public BigDecimal getActualTakeGoodsQyt() {
		return actualTakeGoodsQyt;
	}
	public void setActualTakeGoodsQyt(BigDecimal actualTakeGoodsQyt) {
		this.actualTakeGoodsQyt = actualTakeGoodsQyt;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	
	public BigDecimal getSelfVolume() {
		return selfVolume;
	}
	public void setSelfVolume(BigDecimal selfVolume) {
		this.selfVolume = selfVolume;
	}
	public String getVehicleLenghtCode() {
		return vehicleLenghtCode;
	}
	public void setVehicleLenghtCode(String vehicleLenghtCode) {
		this.vehicleLenghtCode = vehicleLenghtCode;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
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
	public String getIsRepeateMark() {
		return isRepeateMark;
	}
	public void setIsRepeateMark(String isRepeateMark) {
		this.isRepeateMark = isRepeateMark;
	}
	public String getAccruedState() {
		return accruedState;
	}
	public void setAccruedState(String accruedState) {
		this.accruedState = accruedState;
	}
	public String getAccruedWorkNo() {
		return accruedWorkNo;
	}
	public void setAccruedWorkNo(String accruedWorkNo) {
		this.accruedWorkNo = accruedWorkNo;
	}
	public String getAccruedWorkResult() {
		return accruedWorkResult;
	}
	public void setAccruedWorkResult(String accruedWorkResult) {
		this.accruedWorkResult = accruedWorkResult;
	}
	
	
	
}
