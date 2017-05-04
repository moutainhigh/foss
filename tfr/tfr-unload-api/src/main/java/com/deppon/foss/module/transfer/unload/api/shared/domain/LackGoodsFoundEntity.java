/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 */
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: LackGoodsFoundEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 少货找到实体
 * @date: 2013-7-1 下午3:55:32
 * 
 */
public class LackGoodsFoundEntity extends BaseEntity {

	private static final long serialVersionUID = -4864944037640536208L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 流水号
	 */
	private String serialNo;
	
	/**
	 * 差异类型(卸车少货、清仓少货)
	 */
	private String reportType;
	
	/**
	 * oa差错单编号
	 */
	private String oaErrorNo;
	
	/**
	 * 差异报告ID
	 */
	private String reportId;
	
	/**
	 * 上报OA少货时间
	 */
	private Date reportOATime;
	
	/**
	 * 少货部门code
	 */
	private String lackGoodsOrgCode;
	
	/**
	 * 是否已找到
	 */
	private String beFound;
	
	/**
	 * 找到时间
	 */
	private Date foundTime;
	
	/**
	 * 发现人工号
	 */
	private String discovererCode;
	
	/**
	 * 发现人姓名
	 */
	private String discovererName;
	
	/**
	 * 发现人所在部门code
	 */
	private String discovererOrgCode;
	
	/**
	 * 入库记录ID
	 */
	private String inStockId;
	
	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	public Date getFoundTime() {
		return foundTime;
	}

	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getOaErrorNo() {
		return oaErrorNo;
	}

	public void setOaErrorNo(String oaErrorNo) {
		this.oaErrorNo = oaErrorNo;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public Date getReportOATime() {
		return reportOATime;
	}

	public void setReportOATime(Date reportOATime) {
		this.reportOATime = reportOATime;
	}

	public String getLackGoodsOrgCode() {
		return lackGoodsOrgCode;
	}

	public void setLackGoodsOrgCode(String lackGoodsOrgCode) {
		this.lackGoodsOrgCode = lackGoodsOrgCode;
	}

	public String getBeFound() {
		return beFound;
	}

	public void setBeFound(String beFound) {
		this.beFound = beFound;
	}

	public String getDiscovererCode() {
		return discovererCode;
	}

	public void setDiscovererCode(String discovererCode) {
		this.discovererCode = discovererCode;
	}

	public String getDiscovererName() {
		return discovererName;
	}

	public void setDiscovererName(String discovererName) {
		this.discovererName = discovererName;
	}

	public String getDiscovererOrgCode() {
		return discovererOrgCode;
	}

	public void setDiscovererOrgCode(String discovererOrgCode) {
		this.discovererOrgCode = discovererOrgCode;
	}
	
}
