package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @ClassName: DeliverBillVaryStatusEntity 
 * @Description: 派送单状态更新记录表Entity 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-27 下午2:59:07 
 *  
 */
public class DeliverBillVaryStatusEntity extends BaseEntity {
	
	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 派送单号
	 */
	private String deliverBillNo;
	
	/**
	 * 派送单状态
	 */
	private String deliverBillStatus;
	
	/**
	 * 操作人名称
	 */
	private String operatorName;
	
	/**
	 * 操作人编码
	 */
	private String operatorCode;
	
	/**
	 * 操作部门名称
	 */
	private String operateOrgName;
	
	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;
	
	/**
	 * 操作日期
	 */
	private Date operateDate;

	/**
	 * 获取deliverBillNo
	 * @return the deliverBillNo
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}

	/**
	 * 获取deliverBillStatus
	 * @return the deliverBillStatus
	 */
	public String getDeliverBillStatus() {
		return deliverBillStatus;
	}

	/**
	 * 获取operatorName
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 获取operatorCode
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * 获取operateOrgName
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * 获取operateOrgCode
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * 获取operateDate
	 * @return the operateDate
	 */
	public Date getOperateDate() {
		return operateDate;
	}

	/**
	 * 设置deliverBillNo
	 * @param deliverBillNo 要设置的deliverBillNo
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}

	/**
	 * 设置deliverBillStatus
	 * @param deliverBillStatus 要设置的deliverBillStatus
	 */
	public void setDeliverBillStatus(String deliverBillStatus) {
		this.deliverBillStatus = deliverBillStatus;
	}

	/**
	 * 设置operatorName
	 * @param operatorName 要设置的operatorName
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 设置operatorCode
	 * @param operatorCode 要设置的operatorCode
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 设置operateOrgName
	 * @param operateOrgName 要设置的operateOrgName
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * 设置operateOrgCode
	 * @param operateOrgCode 要设置的operateOrgCode
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * 设置operateDate
	 * @param operateDate 要设置的operateDate
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
}
