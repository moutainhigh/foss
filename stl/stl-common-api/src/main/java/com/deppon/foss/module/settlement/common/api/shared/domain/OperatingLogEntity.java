package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 操作日志类
 * 
 * @date 2012-10-11 下午5:16:11
 * @since
 * @version
 */
public class OperatingLogEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 6634608067846063034L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 操作单据编号
	 */
	private String operateBillNo;

	/**
	 * 操作单据类型
	 */
	private String operateBillType;

	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;

	/**
	 * 操作部门名称
	 */
	private String operateOrgName;

	/**
	 * 操作人编码
	 */
	private String operatorCode;

	/**
	 * 操作人名称
	 */
	private String operatorName;

	/**
	 * 操作人IP
	 */
	private String operatorIp;

	/**
	 * 操作时间
	 */
	private Date operateTime;

	/**
	 * 操作类型
	 */
	private String operateType;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return operateBillNo
	 */
	public String getOperateBillNo() {
		return operateBillNo;
	}

	/**
	 * @param operateBillNo
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
	 * @param operateBillType
	 */
	public void setOperateBillType(String operateBillType) {
		this.operateBillType = operateBillType;
	}

	/**
	 * @return operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param operateOrgCode
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
	 * @param operateOrgName
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * @return operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode
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
	 * @param operatorName
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * @return operatorIp
	 */
	public String getOperatorIp() {
		return operatorIp;
	}

	/**
	 * @param operatorIp
	 */
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	/**
	 * @return operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return operateType
	 */
	public String getOperateType() {
		return operateType;
	}

	/**
	 * @param operateType
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
