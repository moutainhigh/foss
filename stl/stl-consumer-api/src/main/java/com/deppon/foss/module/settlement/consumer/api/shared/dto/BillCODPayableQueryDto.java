package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * 代收货款汇款查询DTO
 * 
 * @author dp-zengbinwen
 * @date 2012-10-30 上午10:11:45
 */
public class BillCODPayableQueryDto {
	/**
	 * @author 218392 zhangyongxue 2015-08-07 14:18:50
	 * 增加一个中间变量，传到CODEntityMapper.xml中，以供判断
	 * 当前台查询条件中含有打包退的时候，设置成Y;没有打包退的时候就设置"",空
	 */
	private String isPackage;

	/**
	 * 截止签收日期
	 */
	private Date endSignDate;

	/**
	 * 代收货款类型
	 */
	private List<String> codTypes;

	/**
	 * 银行
	 */
	private List<String> banks;

	/**
	 * 对公对私标志
	 */
	private String publicPrivateFlag;

	/**
	 * 代收货款状态--用于传到mapper文件做判断
	 */
	private String statue;
	
	/**
	 * 代收货款状态
	 */
	private List<String> statuses;

	/**
	 * 代收货款是否有效
	 */
	private String active;

	/**
	 * 应付单是否有效
	 */
	private String payableActive;

	/**
	 * 生效状态
	 */
	private String effectiveStatus;

	/**
	 * 应付单类型
	 */
	private String payableBillType;
	
	/** 分页开始. */
	private int offset;

	/** 分页大小. */
	private int limit;
	
	/**
	 * 排序字段
	 */
	private String sortProperty;
	
	/**
	 * 排序方式
	 */
	private String sortDirection;

	/**
	 * @return endSignDate
	 */
	public Date getEndSignDate() {
		return endSignDate;
	}

	/**
	 * @param endSignDate
	 */
	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}

	/**
	 * @return codTypes
	 */
	public List<String> getCodTypes() {
		return codTypes;
	}

	/**
	 * @param codTypes
	 */
	public void setCodTypes(List<String> codTypes) {
		this.codTypes = codTypes;
	}

	/**
	 * @return banks
	 */
	public List<String> getBanks() {
		return banks;
	}

	/**
	 * @param banks
	 */
	public void setBanks(List<String> banks) {
		this.banks = banks;
	}

	/**
	 * @return publicPrivateFlag
	 */
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	/**
	 * @param publicPrivateFlag
	 */
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	/**
	 * @return statuses
	 */
	public List<String> getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses
	 */
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return payableActive
	 */
	public String getPayableActive() {
		return payableActive;
	}

	/**
	 * @param payableActive
	 */
	public void setPayableActive(String payableActive) {
		this.payableActive = payableActive;
	}

	/**
	 * @return effectiveStatus
	 */
	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	/**
	 * @param effectiveStatus
	 */
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	/**
	 * @return payableBillType
	 */
	public String getPayableBillType() {
		return payableBillType;
	}

	/**
	 * @param payableBillType
	 */
	public void setPayableBillType(String payableBillType) {
		this.payableBillType = payableBillType;
	}

	/**
	 * @return offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
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

	public String getIsPackage() {
		return isPackage;
	}

	public void setIsPackage(String isPackage) {
		this.isPackage = isPackage;
	}

	
}
