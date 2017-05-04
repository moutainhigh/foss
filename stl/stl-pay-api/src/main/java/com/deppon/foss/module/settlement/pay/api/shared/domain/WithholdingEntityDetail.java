/**
 * 
 */
package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 045738
 *  预提明细
 */
public class WithholdingEntityDetail implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8192586207422603476L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 预提单号
	 */
	private String withholdingNo;
	
	/**
	 * 租车编号
	 */
	private String rentCarNo;
	
	/**
	 * 应付单号
	 */
	private String payableNo;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 司机姓名
	 */
	private String driverName;
	
	/**
	 * 司机姓名
	 */
	private String driverCode;
	
	/**
	 * 联系电话
	 */
	private String  driverPhone;
	
	/**
	 * 租车金额
	 */
	private BigDecimal rentCarAmount; 
	
	/**
	 * 用车日期
	 */
	private Date useCarDate;
	
	/**
	 * 申请事由及说明
	 */
	private String notes;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 租车用途
	 */
	private String rentCarUseType;
	/**
	 * 版本号
	 */
	private short version;
	
	/**
	 * 来源单号---给校验重复运单使用的
	 */
	private String billNo;
	
	/**
	 * 是否多次(重复)标记
	 */
	private String repeatTag;
	
	public String getWithholdingNo() {
		return withholdingNo;
	}

	public void setWithholdingNo(String withholdingNo) {
		this.withholdingNo = withholdingNo;
	}

	public String getRentCarNo() {
		return rentCarNo;
	}

	public void setRentCarNo(String rentCarNo) {
		this.rentCarNo = rentCarNo;
	}

	public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public BigDecimal getRentCarAmount() {
		return rentCarAmount;
	}

	public void setRentCarAmount(BigDecimal rentCarAmount) {
		this.rentCarAmount = rentCarAmount;
	}

	public Date getUseCarDate() {
		return useCarDate;
	}

	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getRentCarUseType() {
		return rentCarUseType;
	}

	public void setRentCarUseType(String rentCarUseType) {
		this.rentCarUseType = rentCarUseType;
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getRepeatTag() {
		return repeatTag;
	}

	public void setRepeatTag(String repeatTag) {
		this.repeatTag = repeatTag;
	}
	
}
