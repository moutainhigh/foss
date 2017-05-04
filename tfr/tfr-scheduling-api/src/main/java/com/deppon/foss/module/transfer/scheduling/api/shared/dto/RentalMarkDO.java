package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *租车标记表
 *337470 
 **/
public class RentalMarkDO implements Serializable {

	/**
	 * CUBC所给的接口实体模板
	 */
	private static final long serialVersionUID = 1L;
	
	//租车编号
	private String tempRentalMarkNO;
	//租车用途
	private String rentalCarUsetype;
	//用车时间
	private Date  useCarDate;
	//用车原因
	private String userCarReason ;
	//约车编号
	private String inviteVehicleNo;
	//租车金额
	private BigDecimal rentalAmount;
	//公里数
	private BigDecimal kmsNum ;
	//出发站
	private String departureName;
	//出发站编码
	private String departureCode;
	//目的站
	private String destinationCode;
	//目的站编码
	private String destinationName;
	//备注
	private String notes;
	//标记部门名称
	private String markDepartName;
	//标记部门编码
	private String markDepartCode;
	//创建人
	private String createUserName;
	//创建人工号
	private String createUserCode;
	//创建时间
	private Date createDate;
	//修改人
	private String modifyUserNme;
	//修改人工号
	private String modifyUserCode;
	//修改时间
	private Date modifyDate;
	//状态
	private String active;
	//总重量
	private BigDecimal weigthTotal;
	//总体积
	private BigDecimal volumeTotal;
	//实际走货票数
	private BigDecimal actualTakeGoodsQyt;
	//车牌号
	private String vehicleNo;
	//净重
	private BigDecimal selfVolume;
	//车型
	private String vehicleLenghtCode;
	//司机编码
	private String driverCode;
	//司机姓名
	private String driverName;
	//司机电话
	private String driverPhone;
	//是否多次标记
	private String isRepeateMark;
	//326027--begin//请车平台名称
	private String salesVehiclePlatformName;
	/****************当前登录人信息**************************/
	//当前操作人工号
	private String empCode;
	// 当前登录人姓名
	private String empName;
	//当前登录人部门名称
	private String currentDeptName;
	//当前登录人部门编码
	private String currentDeptCode;
	
	//多条运单号（ 用,拼接）
	private String wayBillNos;
	//单据类型
	private String billType;
	
	//费用承担部门
	private String bearFeesDept;
	//费用承担部门编码
	private String bearFeesDeptCode;
	

	/**
	 * 返回 租车编号
	 * 
	 * @return 租车编号
	*/
	public String getTempRentalMarkNO() {
		return tempRentalMarkNO;
	}
	

	/**  
	 * 设置  租车编号
	 * @param 租车编号
	 */
	public void setTempRentalMarkNO(String tempRentalMarkNO) {
		this.tempRentalMarkNO = tempRentalMarkNO;
	}
	

	/**
	 * 返回 租车用途
	 * 
	 * @return 租车用途
	*/
	public String getRentalCarUsetype() {
		return rentalCarUsetype;
	}
	

	/**  
	 * 设置  租车用途
	 * @param 租车用途
	 */
	public void setRentalCarUsetype(String rentalCarUsetype) {
		this.rentalCarUsetype = rentalCarUsetype;
	}
	

	/**
	 * 返回 用车时间
	 * 
	 * @return 用车时间
	*/
	public Date getUseCarDate() {
		return useCarDate;
	}
	

	/**  
	 * 设置  用车时间
	 * @param 用车时间
	 */
	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}
	

	/**
	 * 返回 用车原因
	 * 
	 * @return 用车原因
	*/
	public String getUserCarReason() {
		return userCarReason;
	}
	

	/**  
	 * 设置  用车原因
	 * @param 用车原因
	 */
	public void setUserCarReason(String userCarReason) {
		this.userCarReason = userCarReason;
	}
	

	/**
	 * 返回 约车编号
	 * 
	 * @return 约车编号
	*/
	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}
	

	/**  
	 * 设置  约车编号
	 * @param 约车编号
	 */
	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}
	
	/**
	 * 返回 租车金额
	 * 
	 * @return 租车金额
	*/
	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}
	

	/**  
	 * 设置  租车金额
	 * @param 租车金额
	 */
	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	

	/**
	 * 返回 公里数
	 * 
	 * @return 公里数
	*/
	public BigDecimal getKmsNum() {
		return kmsNum;
	}
	

	/**  
	 * 设置  公里数
	 * @param 公里数
	 */
	public void setKmsNum(BigDecimal kmsNum) {
		this.kmsNum = kmsNum;
	}
	

	/**
	 * 返回 出发站
	 * 
	 * @return 出发站
	*/
	public String getDepartureName() {
		return departureName;
	}
	

	/**  
	 * 设置  出发站
	 * @param 出发站
	 */
	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}
	

	/**
	 * 返回 出发站编码
	 * 
	 * @return 出发站编码
	*/
	public String getDepartureCode() {
		return departureCode;
	}
	

	/**  
	 * 设置  出发站编码
	 * @param 出发站编码
	 */
	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}
	

	/**
	 * 返回 目的站
	 * 
	 * @return 目的站
	*/
	public String getDestinationCode() {
		return destinationCode;
	}
	

	/**  
	 * 设置  目的站
	 * @param 目的站
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	

	/**
	 * 返回 目的站编码
	 * 
	 * @return 目的站编码
	*/
	public String getDestinationName() {
		return destinationName;
	}
	

	/**  
	 * 设置  目的站编码
	 * @param 目的站编码
	 */
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	

	/**
	 * 返回 备注
	 * 
	 * @return 备注
	*/
	public String getNotes() {
		return notes;
	}
	

	/**  
	 * 设置  备注
	 * @param 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	

	/**
	 * 返回 标记部门名称
	 * 
	 * @return 标记部门名称
	*/
	public String getMarkDepartName() {
		return markDepartName;
	}
	

	/**  
	 * 设置  标记部门名称
	 * @param 标记部门名称
	 */
	public void setMarkDepartName(String markDepartName) {
		this.markDepartName = markDepartName;
	}
	

	/**
	 * 返回 标记部门编码
	 * 
	 * @return 标记部门编码
	*/
	public String getMarkDepartCode() {
		return markDepartCode;
	}
	

	/**  
	 * 设置  标记部门编码
	 * @param 标记部门编码
	 */
	public void setMarkDepartCode(String markDepartCode) {
		this.markDepartCode = markDepartCode;
	}
	

	/**
	 * 返回 创建人
	 * 
	 * @return 创建人
	*/
	public String getCreateUserName() {
		return createUserName;
	}
	

	/**  
	 * 设置  创建人
	 * @param 创建人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	

	/**
	 * 返回 创建人工号
	 * 
	 * @return 创建人工号
	*/
	public String getCreateUserCode() {
		return createUserCode;
	}
	

	/**  
	 * 设置  创建人工号
	 * @param 创建人工号
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	

	/**
	 * 返回 创建时间
	 * 
	 * @return 创建时间
	*/
	public Date getCreateDate() {
		return createDate;
	}
	

	/**  
	 * 设置  创建时间
	 * @param 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	/**
	 * 返回 修改人
	 * 
	 * @return 修改人
	*/
	public String getModifyUserNme() {
		return modifyUserNme;
	}
	

	/**  
	 * 设置  修改人
	 * @param 修改人
	 */
	public void setModifyUserNme(String modifyUserNme) {
		this.modifyUserNme = modifyUserNme;
	}
	

	/**
	 * 返回 修改人工号
	 * 
	 * @return 修改人工号
	*/
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	

	/**  
	 * 设置  修改人工号
	 * @param 修改人工号
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	

	/**
	 * 返回 修改时间
	 * 
	 * @return 修改时间
	*/
	public Date getModifyDate() {
		return modifyDate;
	}
	

	/**  
	 * 设置  修改时间
	 * @param 修改时间
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	

	/**
	 * 返回 状态
	 * 
	 * @return 状态
	*/
	public String getActive() {
		return active;
	}
	

	/**  
	 * 设置  状态
	 * @param 状态
	 */
	public void setActive(String active) {
		this.active = active;
	}
	

	/**
	 * 返回 总重量
	 * 
	 * @return 总重量
	*/
	public BigDecimal getWeigthTotal() {
		return weigthTotal;
	}
	

	/**  
	 * 设置  总重量
	 * @param 总重量
	 */
	public void setWeigthTotal(BigDecimal weigthTotal) {
		this.weigthTotal = weigthTotal;
	}
	

	/**
	 * 返回 总体积
	 * 
	 * @return 总体积
	*/
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	

	/**  
	 * 设置  总体积
	 * @param 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	
	/**
	 * 返回 实际走货票数
	 * 
	 * @return 实际走货票数
	*/
	public BigDecimal getActualTakeGoodsQyt() {
		return actualTakeGoodsQyt;
	}
	

	/**  
	 * 设置  实际走货票数
	 * @param 实际走货票数
	 */
	public void setActualTakeGoodsQyt(BigDecimal actualTakeGoodsQyt) {
		this.actualTakeGoodsQyt = actualTakeGoodsQyt;
	}
	

	/**
	 * 返回 车牌号
	 * 
	 * @return 车牌号
	*/
	public String getVehicleNo() {
		return vehicleNo;
	}
	

	/**  
	 * 设置  车牌号
	 * @param 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	

	/**
	 * 返回 净重
	 * 
	 * @return 净重
	*/
	public BigDecimal getSelfVolume() {
		return selfVolume;
	}
	

	/**  
	 * 设置  净重
	 * @param 净重
	 */
	public void setSelfVolume(BigDecimal selfVolume) {
		this.selfVolume = selfVolume;
	}
	

	/**
	 * 返回 车型
	 * 
	 * @return 车型
	*/
	public String getVehicleLenghtCode() {
		return vehicleLenghtCode;
	}
	

	/**  
	 * 设置  车型
	 * @param 车型
	 */
	public void setVehicleLenghtCode(String vehicleLenghtCode) {
		this.vehicleLenghtCode = vehicleLenghtCode;
	}
	

	/**
	 * 返回 司机编码
	 * 
	 * @return 司机编码
	*/
	public String getDriverCode() {
		return driverCode;
	}
	

	/**  
	 * 设置  司机编码
	 * @param 司机编码
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	

	/**
	 * 返回 司机姓名
	 * 
	 * @return 司机姓名
	*/
	public String getDriverName() {
		return driverName;
	}
	

	/**  
	 * 设置  司机姓名
	 * @param 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	

	/**
	 * 返回 司机电话
	 * 
	 * @return 司机电话
	*/
	public String getDriverPhone() {
		return driverPhone;
	}
	

	/**  
	 * 设置  司机电话
	 * @param 司机电话
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	

	/**
	 * 返回 是否多次标记
	 * 
	 * @return 是否多次标记
	*/
	public String getIsRepeateMark() {
		return isRepeateMark;
	}
	

	/**  
	 * 设置  是否多次标记
	 * @param 是否多次标记
	 */
	public void setIsRepeateMark(String isRepeateMark) {
		this.isRepeateMark = isRepeateMark;
	}
	/**
	 * 返回 326027--begin//请车平台名称
	 * 
	 * @return 326027--begin//请车平台名称
	*/
	public String getSalesVehiclePlatformName() {
		return salesVehiclePlatformName;
	}
	

	/**  
	 * 设置  326027--begin//请车平台名称
	 * @param 326027--begin//请车平台名称
	 */
	public void setSalesVehiclePlatformName(String salesVehiclePlatformName) {
		this.salesVehiclePlatformName = salesVehiclePlatformName;
	}
	

	/**
	 * 返回 **********
	 * 
	 * @return **********
	*/
	public String getEmpCode() {
		return empCode;
	}
	

	/**  
	 * 设置  **********
	 * @param **********
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	

	/**
	 * 返回 当前登录人姓名
	 * 
	 * @return 当前登录人姓名
	*/
	public String getEmpName() {
		return empName;
	}
	

	/**  
	 * 设置  当前登录人姓名
	 * @param 当前登录人姓名
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	

	/**
	 * 返回 当前登录人部门名称
	 * 
	 * @return 当前登录人部门名称
	*/
	public String getCurrentDeptName() {
		return currentDeptName;
	}
	

	/**  
	 * 设置  当前登录人部门名称
	 * @param 当前登录人部门名称
	 */
	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}
	

	/**
	 * 返回 当前登录人部门编码
	 * 
	 * @return 当前登录人部门编码
	*/
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}
	

	/**  
	 * 设置  当前登录人部门编码
	 * @param 当前登录人部门编码
	 */
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	

	/**
	 * 返回 多条运单号（ 用,拼接）
	 * 
	 * @return 多条运单号（ 用,拼接）
	*/
	public String getWayBillNos() {
		return wayBillNos;
	}
	

	/**  
	 * 设置  多条运单号（ 用,拼接）
	 * @param 多条运单号（ 用,拼接）
	 */
	public void setWayBillNos(String wayBillNos) {
		this.wayBillNos = wayBillNos;
	}


	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}


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
     * toString()...
     */
	@Override
	public String toString() {
		return "RentalMarkDO [tempRentalMarkNO=" + tempRentalMarkNO
				+ ", rentalCarUsetype=" + rentalCarUsetype + ", useCarDate="
				+ useCarDate + ", userCarReason=" + userCarReason
				+ ", inviteVehicleNo=" + inviteVehicleNo + ", rentalAmount="
				+ rentalAmount + ", kmsNum=" + kmsNum + ", departureName="
				+ departureName + ", departureCode=" + departureCode
				+ ", destinationCode=" + destinationCode + ", destinationName="
				+ destinationName + ", notes=" + notes + ", markDepartName="
				+ markDepartName + ", markDepartCode=" + markDepartCode
				+ ", createUserName=" + createUserName + ", createUserCode="
				+ createUserCode + ", createDate=" + createDate
				+ ", modifyUserNme=" + modifyUserNme + ", modifyUserCode="
				+ modifyUserCode + ", modifyDate=" + modifyDate + ", active="
				+ active + ", weigthTotal=" + weigthTotal + ", volumeTotal="
				+ volumeTotal + ", actualTakeGoodsQyt=" + actualTakeGoodsQyt
				+ ", vehicleNo=" + vehicleNo + ", selfVolume=" + selfVolume
				+ ", vehicleLenghtCode=" + vehicleLenghtCode + ", driverCode="
				+ driverCode + ", driverName=" + driverName + ", driverPhone="
				+ driverPhone + ", isRepeateMark=" + isRepeateMark
				+ ", salesVehiclePlatformName=" + salesVehiclePlatformName
				+ ", empCode=" + empCode + ", empName=" + empName
				+ ", currentDeptName=" + currentDeptName + ", currentDeptCode="
				+ currentDeptCode + ", wayBillNos=" + wayBillNos
				+ ", billType=" + billType + ", bearFeesDept=" + bearFeesDept
				+ ", bearFeesDeptCode=" + bearFeesDeptCode + "]";
	}


	
	
	
}
