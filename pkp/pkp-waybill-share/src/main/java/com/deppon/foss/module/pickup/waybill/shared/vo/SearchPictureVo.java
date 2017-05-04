package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片开单查询vo
 * @author hehaisu
 *
 */
public class SearchPictureVo implements Serializable  {
	//序号
	private int no;
	//id
	private String id;
	//运单id
	private String waybillUuid;
	// 运单状态
	private String active;
	// 运单号
	private String waybillNo;
	// 订单号
	private String orderNo;
	// Foss提交时间
	private Date createTime;
	// 收货部门
	private String receiveOrgCode;
	//收货部门名称
	private String receiveOrgName;
	//司机工号
	private String driverWorkNo;
	//司机姓名
	private String driverName;
	//车牌号
	private String vehicleNo;
	//图片地址
	private String filePath;
	//创建人
	private String createUserCode;
	//修改人
	private String modifyUserCode;
	//是否大票货
	private String bigGoodsFlag;
	//备注
	private String reMark;
	//设备号
	private String equipmentNo;
	//操作人工号
	private String operator;
	//操作人姓名
	private String operatorName;
	//现金标记
	private String cashPayFlag;
	
	/**
	 * 图片上传时间
	 */
	private Date imgUploadTime;
	/**
	 * 运输性质
	 */
	private String transportType;
	/**
	 * 目的站
	 */
	private String distStation;
	/**
	 * 补录人
	 */
	private String outerOptCode;
	/**
	 * 补录人部门
	 */
	private String outerName;
	
	/**
	 * 本地开单部门
	 */
	private String localBillGroupCode;
	
	/**
	 * 是否属于本地部门
	 */
	private String isLocal;
	
	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getBigGoodsFlag() {
		return bigGoodsFlag;
	}

	public void setBigGoodsFlag(String bigGoodsFlag) {
		this.bigGoodsFlag = bigGoodsFlag;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getDriverWorkNo() {
		return driverWorkNo;
	}
	public void setDriverWorkNo(String driverWorkNo) {
		this.driverWorkNo = driverWorkNo;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillUuid() {
		return waybillUuid;
	}

	public void setWaybillUuid(String waybillUuid) {
		this.waybillUuid = waybillUuid;
	}
	
	public String getEquipmentNo() {
		return equipmentNo;
	}

	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getCashPayFlag() {
		return cashPayFlag;
	}

	public void setCashPayFlag(String cashPayFlag) {
		this.cashPayFlag = cashPayFlag;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getImgUploadTime() {
		return imgUploadTime;
	}

	public void setImgUploadTime(Date imgUploadTime) {
		this.imgUploadTime = imgUploadTime;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getDistStation() {
		return distStation;
	}

	public void setDistStation(String distStation) {
		this.distStation = distStation;
	}

	public String getOuterOptCode() {
		return outerOptCode;
	}

	public void setOuterOptCode(String outerOptCode) {
		this.outerOptCode = outerOptCode;
	}

	public String getOuterName() {
		return outerName;
	}

	public void setOuterName(String outerName) {
		this.outerName = outerName;
	}

	public String getLocalBillGroupCode() {
		return localBillGroupCode;
	}

	public void setLocalBillGroupCode(String localBillGroupCode) {
		this.localBillGroupCode = localBillGroupCode;
	}

	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}
	
	/**
	 * 所属开单部门
	 */
	private String belongOrgCode;

	public String getBelongOrgCode() {
		return belongOrgCode;
	}

	public void setBelongOrgCode(String belongOrgCode) {
		this.belongOrgCode = belongOrgCode;
	}
	
	

}
