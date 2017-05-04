package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: ComplementLogEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码日志实体
 * @date: 2013-7-19 下午2:08:24
 * 
 */
public class ComplementLogEntity extends BaseEntity {
	
	private static final long serialVersionUID = 988789123132156L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 补码前部门code
	 */
	private String beforeOrgCode;
	
	/**
	 * 补码前部门name
	 */
	private String beforeOrgName;
	
	/**
	 * 补码后部门code
	 */
	private String afterOrgCode;
	
	/**
	 * 补码后部门name
	 */
	private String afterOrgName;
	
	/**
	 * 操作人code
	 */
	private String operatorCode;
	
	/**
	 * 操作人name
	 */
	private String operatorName;
	
	/**
	 * 补码部门code
	 */
	private String operationOrgCode;
	
	/**
	 * 补码部门name
	 */
	private String operationOrgName;
	
	/**
	 * 补码时间
	 */
	private Date operationTime;
	
	
	/**
	 * 运单的收货地址
	* @fields address
	* @author 14022-foss-songjie
	* @update 2015年6月30日 下午3:49:51
	* @version V1.0
	*/
	private String address;
	
	//326027--begin
	/**
	 * 出发城市code
	 * @return
	 */
	private String departCityCode;
	
	public String getDepartCityCode() {
		return departCityCode;
	}

	public void setDepartCityCode(String departCityCode) {
		this.departCityCode = departCityCode;
	}

	public String getDepartCityName() {
		return departCityName;
	}

	public void setDepartCityName(String departCityName) {
		this.departCityName = departCityName;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	/**
	 * 出发城市name
	 * @return
	 */
	private String departCityName;
	
	/**
	 * 匹配模式
	 * @return
	 */
	private String matchType;
	//326027--end
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getBeforeOrgCode() {
		return beforeOrgCode;
	}

	public void setBeforeOrgCode(String beforeOrgCode) {
		this.beforeOrgCode = beforeOrgCode;
	}

	public String getBeforeOrgName() {
		return beforeOrgName;
	}

	public void setBeforeOrgName(String beforeOrgName) {
		this.beforeOrgName = beforeOrgName;
	}

	public String getAfterOrgCode() {
		return afterOrgCode;
	}

	public void setAfterOrgCode(String afterOrgCode) {
		this.afterOrgCode = afterOrgCode;
	}

	public String getAfterOrgName() {
		return afterOrgName;
	}

	public void setAfterOrgName(String afterOrgName) {
		this.afterOrgName = afterOrgName;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperationOrgCode() {
		return operationOrgCode;
	}

	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}

	public String getOperationOrgName() {
		return operationOrgName;
	}

	public void setOperationOrgName(String operationOrgName) {
		this.operationOrgName = operationOrgName;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ComplementLogEntity [id=" + super.getId() + ", waybillNo=" + waybillNo
				+ ", beforeOrgCode=" + beforeOrgCode + ", beforeOrgName="
				+ beforeOrgName + ", afterOrgCode=" + afterOrgCode
				+ ", afterOrgName=" + afterOrgName + ", operatorCode="
				+ operatorCode + ", operatorName=" + operatorName
				+ ", operationOrgCode=" + operationOrgCode
				+ ", operationOrgName=" + operationOrgName + ", operationTime="
				+ String.format("%1$tF %2$tT", operationTime,operationTime) + "]";
	}
}
