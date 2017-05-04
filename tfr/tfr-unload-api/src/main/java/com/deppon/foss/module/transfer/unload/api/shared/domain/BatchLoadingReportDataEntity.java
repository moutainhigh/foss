/**
 * @author foss 257200
 * 2015-6-19
 * 257220
 */
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

/**
 * @author 257220
 *
 */
public class BatchLoadingReportDataEntity {
	private String id;
	
	private String batchLoadingId;
	
	private String reportStatus;
	//卸车任务编号
	private String unloadTaskNo;
	//卸车任务id
	private String unloadTaskId;
	
	private String unloadDetailId;
	
	private String wayBillNo;
	
	private Date taskSubmitTime;
	//实际卸车件数
	private String unloadGoodsQty;
	//开单件数
	private String goodsQtyTotal;
	//运输性质
	private String productCode;
	//货物体积
	private String goodsVolume;
	//生成时间
	private Date createTime;

	//货物重量
	private String goodsWeight;
	//上报时间
	private Date reportTime;
	//差错编号
	private String errorNo;
	//是否需要上报、
	private String isNeedReport;
	//不再上报原因
	private String noReportReasonNo;
	//不再上报原因详情
	private String noReportReason;
	//更新时间
	private Date updateTime;
	//备注
	private String notes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchLoadingId() {
		return batchLoadingId;
	}
	public void setBatchLoadingId(String batchLoadingId) {
		this.batchLoadingId = batchLoadingId;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	
	public String getUnloadTaskId() {
		return unloadTaskId;
	}
	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public Date getTaskSubmitTime() {
		return taskSubmitTime;
	}
	public void setTaskSubmitTime(Date taskSubmitTime) {
		this.taskSubmitTime = taskSubmitTime;
	}
	public String getUnloadGoodsQty() {
		return unloadGoodsQty;
	}
	public void setUnloadGoodsQty(String unloadGoodsQty) {
		this.unloadGoodsQty = unloadGoodsQty;
	}
	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getGoodsVolume() {
		return goodsVolume;
	}
	public void setGoodsVolume(String goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	public String getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(String goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	public String getIsNeedReport() {
		return isNeedReport;
	}
	public void setIsNeedReport(String isNeedReport) {
		this.isNeedReport = isNeedReport;
	}
	public String getNoReportReasonNo() {
		return noReportReasonNo;
	}
	public void setNoReportReasonNo(String noReportReasonNo) {
		this.noReportReasonNo = noReportReasonNo;
	}
	public String getNoReportReason() {
		return noReportReason;
	}
	public void setNoReportReason(String noReportReason) {
		this.noReportReason = noReportReason;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getUnloadDetailId() {
		return unloadDetailId;
	}
	public void setUnloadDetailId(String unloadDetailId) {
		this.unloadDetailId = unloadDetailId;
	}
	
}
