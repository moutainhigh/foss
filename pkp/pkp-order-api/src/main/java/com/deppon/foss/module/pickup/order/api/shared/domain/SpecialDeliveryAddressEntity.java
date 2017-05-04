package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @ClassName: SpecialDeliveryAddress 
 * @Description: 特殊送货地址类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-16 上午10:17:22 
 *  
 */
public class SpecialDeliveryAddressEntity extends BaseEntity {

	/**
	 * 特殊送货地址序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 特殊送货地址id
	 */
	private String id;
	
	/**
	 * 送货地址
	 */
	private String deliveryAddress;
	
	/**
	 * 送货小区名称
	 */
	private String deliveryResidenceName;
	
	/**
	 * 送货小区编码
	 */
	private String deliveryResidenceCode;
	
	/**
	 * 地址类型
	 */
	private String addressType;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 车所属部门名称
	 */
	private String vehicleDeptName;
	
	/**
	 * 车所属部门编码
	 */
	private String vehicleDeptCode;
	
	/**
	 * 操作人编码
	 */
	private String operatorCode;
	
	/**
	 * 操作人名称
	 */
	private String operatorName;
	
	/**
	 * 操作日期
	 */
	private Date operateDate;
	
	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;
	
	/**
	 * 操作部门名称
	 */
	private String operateOrgName;
	
	/**
	 * 创建人编码
	 */
	private String createrCode;
	
	/**
	 * 创建人名称
	 */
	private String createrName;
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/**
	 * 创建部门编码
	 */
	private String createOrgCode;
	
	/**
	 * 创建部门名称
	 */
	private String createOrgName;

	/**
	 * 获取id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取deliveryAddress
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * 设置deliveryAddress
	 * @param deliveryAddress 要设置的deliveryAddress
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	/**
	 * 获取deliveryResidenceName
	 * @return the deliveryResidenceName
	 */
	public String getDeliveryResidenceName() {
		return deliveryResidenceName;
	}

	/**
	 * 设置deliveryResidenceName
	 * @param deliveryResidenceName 要设置的deliveryResidenceName
	 */
	public void setDeliveryResidenceName(String deliveryResidenceName) {
		this.deliveryResidenceName = deliveryResidenceName;
	}

	/**
	 * 获取deliveryResidenceCode
	 * @return the deliveryResidenceCode
	 */
	public String getDeliveryResidenceCode() {
		return deliveryResidenceCode;
	}

	/**
	 * 设置deliveryResidenceCode
	 * @param deliveryResidenceCode 要设置的deliveryResidenceCode
	 */
	public void setDeliveryResidenceCode(String deliveryResidenceCode) {
		this.deliveryResidenceCode = deliveryResidenceCode;
	}

	/**
	 * 获取addressType
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * 设置addressType
	 * @param addressType 要设置的addressType
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * 获取vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置vehicleNo
	 * @param vehicleNo 要设置的vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取vehicleDeptName
	 * @return the vehicleDeptName
	 */
	public String getVehicleDeptName() {
		return vehicleDeptName;
	}

	/**
	 * 设置vehicleDeptName
	 * @param vehicleDeptName 要设置的vehicleDeptName
	 */
	public void setVehicleDeptName(String vehicleDeptName) {
		this.vehicleDeptName = vehicleDeptName;
	}

	/**
	 * 获取vehicleDeptCode
	 * @return the vehicleDeptCode
	 */
	public String getVehicleDeptCode() {
		return vehicleDeptCode;
	}

	/**
	 * 设置vehicleDeptCode
	 * @param vehicleDeptCode 要设置的vehicleDeptCode
	 */
	public void setVehicleDeptCode(String vehicleDeptCode) {
		this.vehicleDeptCode = vehicleDeptCode;
	}

	/**
	 * 获取operatorCode
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * 设置operatorCode
	 * @param operatorCode 要设置的operatorCode
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 获取operatorName
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 设置operatorName
	 * @param operatorName 要设置的operatorName
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 获取operateDate
	 * @return the operateDate
	 */
	public Date getOperateDate() {
		return operateDate;
	}

	/**
	 * 设置operateDate
	 * @param operateDate 要设置的operateDate
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 获取operateOrgCode
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * 设置operateOrgCode
	 * @param operateOrgCode 要设置的operateOrgCode
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * 获取operateOrgName
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * 设置operateOrgName
	 * @param operateOrgName 要设置的operateOrgName
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * 获取createrCode
	 * @return the createrCode
	 */
	public String getCreaterCode() {
		return createrCode;
	}

	/**
	 * 设置createrCode
	 * @param createrCode 要设置的createrCode
	 */
	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}

	/**
	 * 获取createrName
	 * @return the createrName
	 */
	public String getCreaterName() {
		return createrName;
	}

	/**
	 * 设置createrName
	 * @param createrName 要设置的createrName
	 */
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	/**
	 * 获取createDate
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置createDate
	 * @param createDate 要设置的createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取createOrgCode
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置createOrgCode
	 * @param createOrgCode 要设置的createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取createOrgName
	 * @return the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * 设置createOrgName
	 * @param createOrgName 要设置的createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
}
