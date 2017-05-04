package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 代收货款操作日志
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-14 上午10:35:52
 */
public class CODLogEntity extends BaseEntity {

	private static final long serialVersionUID = 3477450070298277001L;

	/**
	 * 代收货款ID
	 */
	private String codId;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 代收货款业务日期
	 */
	private Date businessDate;

	/**
	 * 操作时间
	 */
	private Date operateTime;

	/**
	 * 操作单据编号（代收货款编码）
	 */
	private String operateBillNo;

	/**
	 * 操作单据类型
	 */
	private String operateBillType;

	/**
	 * 操作内容
	 */
	private String operateContent;

	/**
	 * 操作动作类型
	 */
	private String operateActiontype;

	/**
	 * 操作人编码
	 */
	private String operatorCode;

	/**
	 * 操作人名称
	 */
	private String operatorName;

	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;

	/**
	 * 操作部门名称
	 */
	private String operateOrgName;

	/**
	 * 操作者IP
	 */
	private String operatorIp;

	/**
	 * 是否有效
	 */
	private String active;


	/**
	 * @return codId
	 */
	public String getCodId() {
		return codId;
	}

	/**
	 * @param  codId  
	 */
	public void setCodId(String codId) {
		this.codId = codId;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param  waybillNo  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param  businessDate  
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param  operateTime  
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return operateBillNo
	 */
	public String getOperateBillNo() {
		return operateBillNo;
	}

	/**
	 * @param  operateBillNo  
	 */
	public void setOperateBillNo(String operateBillNo) {
		this.operateBillNo = operateBillNo;
	}

	/**
	 * @return operateBillType
	 */
	public String getOperateBillType() {
		return operateBillType;
	}

	/**
	 * @param  operateBillType  
	 */
	public void setOperateBillType(String operateBillType) {
		this.operateBillType = operateBillType;
	}

	/**
	 * @return operateContent
	 */
	public String getOperateContent() {
		return operateContent;
	}

	/**
	 * @param  operateContent  
	 */
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	/**
	 * @return operateActiontype
	 */
	public String getOperateActiontype() {
		return operateActiontype;
	}

	/**
	 * @param  operateActiontype  
	 */
	public void setOperateActiontype(String operateActiontype) {
		this.operateActiontype = operateActiontype;
	}

	/**
	 * @return operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param  operatorCode  
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param  operatorName  
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * @return operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param  operateOrgCode  
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * @param  operateOrgName  
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * @return operatorIp
	 */
	public String getOperatorIp() {
		return operatorIp;
	}

	/**
	 * @param  operatorIp  
	 */
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}


}
