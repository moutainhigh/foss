package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto
 * @author: foss-yuting
 * @description: foss记录内物短少差错 上报OA
 * @date:2014年12月5日 下午15:59:21
 */
@SuppressWarnings("serial")
public class RecordErrorWaybillDto implements Serializable {
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 *  运单号
	 */
	private String waybillNo;
	
	/***
	 * 短少货量(默认开单品名 常量)
	 */
	private String alittleShort;
	
	/** 
	 * 流水号
	 */ 
	private String serialNo;
	
	/**
	 * 外包装是否完好
	 */
	private String packingResult;
	
	/**
	 *  上报时间
	 */
	private Date createTime;
	
	/**
	 * 是否已经上报
	 */
	private String active;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/***
	 * 签收人名称
	 */
	private String operateName;

	/***
	 * 签收人code
	 */
	private String operateCode;
	
	/***
	 * 签收部门
	 */
	private String operateOrgCode;
	
	/**
	 * 签收部门名称
	 */
	private String operateOrgName;
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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

	
}
