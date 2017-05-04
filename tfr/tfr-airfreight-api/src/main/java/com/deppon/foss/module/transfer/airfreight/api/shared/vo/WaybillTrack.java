
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * 
 * 运单执行轨迹
 * 
* @author 200942
 * @date 2016-5-10 下午8:37:39
 */
public class WaybillTrack implements Serializable,Comparator{

	private static final long serialVersionUID = -3703272448562684594L;

	/************* 运单号****************/
	private String waybillNo;
	/************* 操作部门名称 ****************/
	private String operateOrgName;
	/************* 操作部门标杆编码 ****************/
	private String unifiedCode;
	/************* 操作部门编码 ****************/
	private String operateOrgCode;
	/************* 操作部门所在城市编码 ****************/
	private String operateCityCode;
	/************* 操作部门所在城市名称 ****************/
	private String operateCityName;
	/************* 操作类型****************/
	private String operateType;
	/************* 操作类型名称****************/
	private String operateTypeName;
	/************* 操作内容 ****************/
	private String operateContent;
	/************* 操作时间 ****************/
	private Date operateTime;
	/************* 操作人姓名 ****************/
	private String operateName;
	/************* 操作件数 ****************/
	private Integer operateNumber;
	/************* 单据编号 ****************/
	private String billNo;
	/************* 车牌号 ****************/
	private String vehicleNo;
	/************* 备注 ****************/
	private String notes;
	
	/************* 下一站部门编码 ****************/
	private String nextOrgCode;
	/************* 下一站部门名称 ****************/
	private String nextOrgName;
	/************* 下一站所在城市编码 ****************/
	private String nextCityCode;
	/************* 下一站所在城市名称 ****************/
	private String nextCityName;
	/************* 目的站部门编码 ****************/
	private String destinationStationOrgCode;
	/************* 目的站部门名称 ****************/
	private String destinationStationOrgName;
	/************* 目的站部门所在城市编码****************/
	private String destinationStationCityCode;
	/************* 目的站部门所在城市名称 ****************/
	private String destinationStationCityName;
	/************* 离开后预计到达下一操作部门时间 ****************/
	private Date planArriveTime;
	/************* 派送人员姓名****************/
	private String deliveryName;
	/************* 派送人员电话 ****************/
	private String deliveryPhone;
	/************* 签收人姓名 ****************/
	private String signManName;
	/************* 货物状态 ****************/
	private String currentStatus;
	/************* 第一次签收时间 ****************/
	private Date firstSignTime;
	/**********设置预计到达时间 200968 2016-02-20 *****/
	private Date preArriveTime;
	public Date getPreArriveTime() {
		return preArriveTime;
	}
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}
	/************** 定位编号 zwd 200968 20141221*********************/
	private String stockPositionNumber; 
	/************** 定位编号 zwd 200968 20141221*********************/
	public String getStockPositionNumber() {
		return stockPositionNumber;
	}
	/************** 定位编号 zwd 200968 20141221*********************/
	public void setStockPositionNumber(String stockPositionNumber) {
		this.stockPositionNumber = stockPositionNumber;
	}
	@Override
	public int compare(Object o1, Object o2) {
		if(o1==null)
			return -1;
		if(o2==null)
			return 1;
		WaybillTrack d1 = (WaybillTrack)o1;
		WaybillTrack d2 = (WaybillTrack)o2;
		if(d1.getOperateTime()==null)
			return -1;
		if(d2.getOperateTime()==null)
			return 1;
		if (d1.getOperateTime().before(d2.getOperateTime()))
			return -1;
		if (d2.getOperateTime().before(d1.getOperateTime()))
			return 1;
		return 0;
	}
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}
	/**
	 * @param operateOrgName the operateOrgName to set
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	/**
	 * @return the unifiedCode
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}
	/**
	 * @param unifiedCode the unifiedCode to set
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	/**
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}
	/**
	 * @param operateOrgCode the operateOrgCode to set
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}
	/**
	 * @return the operateCityCode
	 */
	public String getOperateCityCode() {
		return operateCityCode;
	}
	/**
	 * @param operateCityCode the operateCityCode to set
	 */
	public void setOperateCityCode(String operateCityCode) {
		this.operateCityCode = operateCityCode;
	}
	/**
	 * @return the operateCityName
	 */
	public String getOperateCityName() {
		return operateCityName;
	}
	/**
	 * @param operateCityName the operateCityName to set
	 */
	public void setOperateCityName(String operateCityName) {
		this.operateCityName = operateCityName;
	}
	/**
	 * @return the operateType
	 */
	public String getOperateType() {
		return operateType;
	}
	/**
	 * @param operateType the operateType to set
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	/**
	 * @return the operateTypeName
	 */
	public String getOperateTypeName() {
		return operateTypeName;
	}
	/**
	 * @param operateTypeName the operateTypeName to set
	 */
	public void setOperateTypeName(String operateTypeName) {
		this.operateTypeName = operateTypeName;
	}
	/**
	 * @return the operateContent
	 */
	public String getOperateContent() {
		return operateContent;
	}
	/**
	 * @param operateContent the operateContent to set
	 */
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * @return the operateName
	 */
	public String getOperateName() {
		return operateName;
	}
	/**
	 * @param operateName the operateName to set
	 */
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	/**
	 * @return the operateNumber
	 */
	public Integer getOperateNumber() {
		return operateNumber;
	}
	/**
	 * @param operateNumber the operateNumber to set
	 */
	public void setOperateNumber(Integer operateNumber) {
		this.operateNumber = operateNumber;
	}
	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * @return the nextOrgCode
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	/**
	 * @param nextOrgCode the nextOrgCode to set
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}
	/**
	 * @return the nextOrgName
	 */
	public String getNextOrgName() {
		return nextOrgName;
	}
	/**
	 * @param nextOrgName the nextOrgName to set
	 */
	public void setNextOrgName(String nextOrgName) {
		this.nextOrgName = nextOrgName;
	}
	/**
	 * @return the nextCityCode
	 */
	public String getNextCityCode() {
		return nextCityCode;
	}
	/**
	 * @param nextCityCode the nextCityCode to set
	 */
	public void setNextCityCode(String nextCityCode) {
		this.nextCityCode = nextCityCode;
	}
	/**
	 * @return the nextCityName
	 */
	public String getNextCityName() {
		return nextCityName;
	}
	/**
	 * @param nextCityName the nextCityName to set
	 */
	public void setNextCityName(String nextCityName) {
		this.nextCityName = nextCityName;
	}
	/**
	 * @return the destinationStationOrgCode
	 */
	public String getDestinationStationOrgCode() {
		return destinationStationOrgCode;
	}
	/**
	 * @param destinationStationOrgCode the destinationStationOrgCode to set
	 */
	public void setDestinationStationOrgCode(String destinationStationOrgCode) {
		this.destinationStationOrgCode = destinationStationOrgCode;
	}
	/**
	 * @return the destinationStationOrgName
	 */
	public String getDestinationStationOrgName() {
		return destinationStationOrgName;
	}
	/**
	 * @param destinationStationOrgName the destinationStationOrgName to set
	 */
	public void setDestinationStationOrgName(String destinationStationOrgName) {
		this.destinationStationOrgName = destinationStationOrgName;
	}
	/**
	 * @return the destinationStationCityCode
	 */
	public String getDestinationStationCityCode() {
		return destinationStationCityCode;
	}
	/**
	 * @param destinationStationCityCode the destinationStationCityCode to set
	 */
	public void setDestinationStationCityCode(String destinationStationCityCode) {
		this.destinationStationCityCode = destinationStationCityCode;
	}
	/**
	 * @return the destinationStationCityName
	 */
	public String getDestinationStationCityName() {
		return destinationStationCityName;
	}
	/**
	 * @param destinationStationCityName the destinationStationCityName to set
	 */
	public void setDestinationStationCityName(String destinationStationCityName) {
		this.destinationStationCityName = destinationStationCityName;
	}
	/**
	 * @return the planArriveTime
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}
	/**
	 * @param planArriveTime the planArriveTime to set
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
	/**
	 * @return the deliveryName
	 */
	public String getDeliveryName() {
		return deliveryName;
	}
	/**
	 * @param deliveryName the deliveryName to set
	 */
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	/**
	 * @return the deliveryPhone
	 */
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	/**
	 * @param deliveryPhone the deliveryPhone to set
	 */
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	/**
	 * @return the signManName
	 */
	public String getSignManName() {
		return signManName;
	}
	/**
	 * @param signManName the signManName to set
	 */
	public void setSignManName(String signManName) {
		this.signManName = signManName;
	}
	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}
	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	/**
	 * @return the firstSignTime
	 */
	public Date getFirstSignTime() {
		return firstSignTime;
	}
	/**
	 * @param firstSignTime the firstSignTime to set
	 */
	public void setFirstSignTime(Date firstSignTime) {
		this.firstSignTime = firstSignTime;
	}
	

}
