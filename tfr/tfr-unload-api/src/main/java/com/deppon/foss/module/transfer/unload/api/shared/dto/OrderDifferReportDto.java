package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 点单任务差异报告
 * @author DPAP-CodeGenerator
 * @Date 2015-12-28 19:17:03
 */
public class OrderDifferReportDto implements Serializable {
	
	/** 
	* The Constant serialVersionUID. 
	*/
	private static final long serialVersionUID = 1L;
	
	/** 
	* id 
	*/
	private String id;
	
	/** 
	* 报告编号 
	*/
	private String reportNo;
	
	/** 
	* 多货件数 
	*/
	private BigDecimal moreGoodsQty;
	
	/** 
	* 少货件数 
	*/
	private BigDecimal lostGoodsQty;
	
	/** 
	* 点单人 
	*/
	private String orderManName;
	
	/** 
	* 点单人code 
	*/
	private String orderManCode;
	
	/** 
	 * 报告状态
	* ING处理中，END处理完成，viod作废 
	*/
	private String reportState;
	
	/** 
	* 创建时间
	*/
	private Date createTime;
	
	/** 
	* 创建人
	*/
	private String createUserCode;
	
	/** 
	* 修改时间
	*/
	private Date modifyTime;
	
	/** 
	* 修改人
	*/
	private String modifyUserCode;
	
	/** 
	* 点单任务编号 
	*/
	private String orderTaskNo;
	/**
	 * 点单任务创建部门（点单差异报告生成部门）
	 */
	private String orderOrgCode;
	/**
	 * 点单任务创建部门（点单差异报告生成部门）
	 */
	private String orderOrgName;
	/**
	 * 点单任务差异报告处理完成时间
	 */
	private Date reportEndTime;	
	/**
	 * 总件数
	 */
	private BigDecimal goodsQtyTotal;
	/**
	 * 总票数
	 */
	private BigDecimal waybillQtyTotal;
	/**
	 * 总重量
	 */
	private BigDecimal weightTotal	;	
	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;
	/**
	* Get() id.
	*
	* @return id.
	*/
	public String getId() {
		return id;
	}
	/**
	* Set() id.
	*
	* @param id.
	*/
	public void setId(String id) {
		this.id = id;
	}
	/**
	* Get() 报告编号.
	*
	* @return 报告编号.
	*/
	public String getReportNo() {
		return reportNo;
	}
	/**
	* Set() 报告编号.
	*
	* @param 报告编号.
	*/
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**
	* Get() 多货件数.
	*
	* @return 多货件数.
	*/
	public BigDecimal getMoreGoodsQty() {
		return moreGoodsQty;
	}
	/**
	* Set() 多货件数.
	*
	* @param 多货件数.
	*/
	public void setMoreGoodsQty(BigDecimal moreGoodsQty) {
		this.moreGoodsQty = moreGoodsQty;
	}
	/**
	* Get() 少货件数.
	*
	* @return 少货件数.
	*/
	public BigDecimal getLostGoodsQty() {
		return lostGoodsQty;
	}
	/**
	* Set() 少货件数.
	*
	* @param 少货件数.
	*/
	public void setLostGoodsQty(BigDecimal lostGoodsQty) {
		this.lostGoodsQty = lostGoodsQty;
	}
	/**
	* Get() 点单人.
	*
	* @return 点单人.
	*/
	public String getOrderManName() {
		return orderManName;
	}
	/**
	* Set() 点单人.
	*
	* @param 点单人.
	*/
	public void setOrderManName(String orderManName) {
		this.orderManName = orderManName;
	}
	/**
	* Get() 点单人code.
	*
	* @return 点单人code.
	*/
	public String getOrderManCode() {
		return orderManCode;
	}
	/**
	* Set() 点单人code.
	*
	* @param 点单人code.
	*/
	public void setOrderManCode(String orderManCode) {
		this.orderManCode = orderManCode;
	}
	/**
	* Get() ING处理中，END处理完成，viod作废.
	*
	* @return ING处理中，END处理完成，viod作废.
	*/
	public String getReportState() {
		return reportState;
	}
	/**
	* Set() ING处理中，END处理完成，viod作废.
	*
	* @param ING处理中，END处理完成，viod作废.
	*/
	public void setReportState(String reportState) {
		this.reportState = reportState;
	}
	
	/**
	 * 点单任务编号 
	 * @return  the orderTaskNo
	 */
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	/**
	 * 点单任务编号 
	 * @param orderTaskNo the orderTaskNo to set
	 */
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	/**
	 * @return  the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return  the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * @return  the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return  the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 点单任务创建部门（点单差异报告生成部门）
	 * @return  the orderOrgCode
	 */
	public String getOrderOrgCode() {
		return orderOrgCode;
	}
	/**
	 * 点单任务创建部门（点单差异报告生成部门）
	 * @param orderOrgCode the orderOrgCode to set
	 */
	public void setOrderOrgCode(String orderOrgCode) {
		this.orderOrgCode = orderOrgCode;
	}
	/**
	 * 点单任务创建部门（点单差异报告生成部门）
	 * @return  the orderOrgName
	 */
	public String getOrderOrgName() {
		return orderOrgName;
	}
	/**
	 * 点单任务创建部门（点单差异报告生成部门）
	 * @param orderOrgName the orderOrgName to set
	 */
	public void setOrderOrgName(String orderOrgName) {
		this.orderOrgName = orderOrgName;
	}
	/**
	 * 点单任务差异报告处理完成时间
	 * @return  the reportEndTime
	 */
	public Date getReportEndTime() {
		return reportEndTime;
	}
	/**
	 * 点单任务差异报告处理完成时间
	 * @param reportEndTime the reportEndTime to set
	 */
	public void setReportEndTime(Date reportEndTime) {
		this.reportEndTime = reportEndTime;
	}
	/**
	 * 总件数
	 * @return  the goodsQtyTotal
	 */
	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	/**
	 * 总件数
	 * @param goodsQtyTotal the goodsQtyTotal to set
	 */
	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	/**
	 * 总票数
	 * @return  the waybillQtyTotal
	 */
	public BigDecimal getWaybillQtyTotal() {
		return waybillQtyTotal;
	}
	/**
	 * @param waybillQtyTotal the waybillQtyTotal to set
	 */
	public void setWaybillQtyTotal(BigDecimal waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}
	/**
	 * @return  the weightTotal
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	/**
	 * @param weightTotal the weightTotal to set
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	/**
	 * @return  the volumeTotal
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	/**
	 * @param volumeTotal the volumeTotal to set
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	
}
