package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillResultDto
 * @author: foss-yuting
 * @description: foss记录内物短少差错 上报OA   结果的DTO
 * @date:2014年12月5日 下午15:59:21
 */
@SuppressWarnings("serial")
public class RecordErrorWaybillResultDto implements Serializable {
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 外包装是否完好
	 */
	private String packingResult;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 运单状态
	 */
	private String active;
	
	/**
	 * 短少量
	 */
	private String alittleShort;
	
	/**
	 * 流水号
	 */ 
	private  String serialNo;
	
	/**
	 * 签收人
	 */
	private String operateName;
	
	/***
	 * 签收人code
	 */
	private String operateCode;
	
	/**
	 * 签收部门
	 */
	private String operateOrgCode;
	
	/**
	 * 签收部门名称
	 */
	private String operateOrgName;
	
	/**
	 * 派送车牌号（派送时）
	 */
	private String vehicleNo;
	
	/**
	 * 派送司机工号
	 */
	private String driverCode;
	
	/**
	 * 派送司机姓名
	 */
	private String driverName;
	
	/**
	 * 派送司机所在部门编码
	 */
	private String driverOrgCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getPackingResult() {
		return packingResult;
	}

	public void setPackingResult(String packingResult) {
		this.packingResult = packingResult;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAlittleShort() {
		return alittleShort;
	}

	public void setAlittleShort(String alittleShort) {
		this.alittleShort = alittleShort;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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

	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

}
