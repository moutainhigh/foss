package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.List;

/**
 * 按运单号查询代收货款DTO.
 *
 * @author dp-zengbinwen
 * @date 2012-10-30 下午2:08:39
 */
public class BillCODWaybillNoQueryDto {

	/** 运单号. */
	private List<String> waybillNos;

	/** 用户编码. */
	private String userCode;
	
	/**
	 * 代收货款状态--用于传到mapper文件做判断
	 */
	private String statue;
	
	/** 代收货款状态. */
	private List<String> statuses;

	/** 应付部门. */
	private String payableOrgCode;
	
	/** 应付部门集合. */
	private List<String> payableOrgCodeList;

	/** 是否有效. */
	private String active;

	/** 应付单是否有效. */
	private String payableActive;

	/** 应付单单据类型. */
	private String payableBillType;

	/** 退款路径. */
	private String refundPath;

	/** 应付单是否有效. */
	private String effective;
	
	/** 核销类型.  */
	private String writeoffType;
	
	/**
	 * 排序字段
	 */
	private String sortProperty;
	
	/**
	 * 排序方式
	 */
	private String sortDirection;
	
	/**
	 * 判断是否 过滤未核销金额>0 判断
	 * true 表示过滤
	 */
	private String isFilterUnverifyAmount;

	/**
	 * Gets the waybill nos.
	 *
	 * @return waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * Sets the waybill nos.
	 *
	 * @param waybillNos the new waybill nos
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * Gets the statuses.
	 *
	 * @return statuses
	 */
	public List<String> getStatuses() {
		return statuses;
	}

	/**
	 * Sets the statuses.
	 *
	 * @param statuses the new statuses
	 */
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	/**
	 * Gets the payable org code.
	 *
	 * @return payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * Sets the payable org code.
	 *
	 * @param payableOrgCode the new payable org code
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	/**
	 * Gets the active.
	 *
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the payable active.
	 *
	 * @return payableActive
	 */
	public String getPayableActive() {
		return payableActive;
	}

	/**
	 * Sets the payable active.
	 *
	 * @param payableActive the new payable active
	 */
	public void setPayableActive(String payableActive) {
		this.payableActive = payableActive;
	}

	/**
	 * Gets the payable bill type.
	 *
	 * @return payableBillType
	 */
	public String getPayableBillType() {
		return payableBillType;
	}

	/**
	 * Sets the payable bill type.
	 *
	 * @param payableBillType the new payable bill type
	 */
	public void setPayableBillType(String payableBillType) {
		this.payableBillType = payableBillType;
	}

	/**
	 * Gets the refund path.
	 *
	 * @return refundPath
	 */
	public String getRefundPath() {
		return refundPath;
	}

	/**
	 * Sets the refund path.
	 *
	 * @param refundPath the new refund path
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}

	/**
	 * Gets the effective.
	 *
	 * @return effective
	 */
	public String getEffective() {
		return effective;
	}

	/**
	 * Sets the effective.
	 *
	 * @param effective the new effective
	 */
	public void setEffective(String effective) {
		this.effective = effective;
	}

	/**
	 * Gets the payable org code list.
	 *
	 * @return payableOrgCodeList
	 */
	public List<String> getPayableOrgCodeList() {
		return payableOrgCodeList;
	}

	/**
	 * Sets the payable org code list.
	 *
	 * @param payableOrgCodeList the new payable org code list
	 */
	public void setPayableOrgCodeList(List<String> payableOrgCodeList) {
		this.payableOrgCodeList = payableOrgCodeList;
	}

	/**
	 * Gets the user code.
	 *
	 * @return the user code
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * Sets the user code.
	 *
	 * @param userCode the new user code
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}

	/**
	 * @return statue
	 */
	public String getStatue() {
		return statue;
	}

	/**
	 * @param statue
	 */
	public void setStatue(String statue) {
		this.statue = statue;
	}

	/**
	 * @return sortProperty
	 */
	public String getSortProperty() {
		return sortProperty;
	}

	/**
	 * @param sortProperty
	 */
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

	/**
	 * @return sortDirection
	 */
	public String getSortDirection() {
		return sortDirection;
	}

	/**
	 * @param sortDirection
	 */
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	/**
	 * @return isFilterUnverifyAmount
	 */
	public String getIsFilterUnverifyAmount() {
		return isFilterUnverifyAmount;
	}

	/**
	 * @param isFilterUnverifyAmount
	 */
	public void setIsFilterUnverifyAmount(String isFilterUnverifyAmount) {
		this.isFilterUnverifyAmount = isFilterUnverifyAmount;
	}

}
