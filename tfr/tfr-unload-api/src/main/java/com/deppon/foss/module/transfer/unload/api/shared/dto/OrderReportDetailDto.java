package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 点单差异报告明细
 * @author DPAP-CodeGenerator
 * @Date 2015-12-28 19:24:10
 */
public class OrderReportDetailDto  implements Serializable {
	
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
	* 交接单号 
	*/
	private String handoverNo;
	
	/** 
	* 运单号 
	*/
	private String waybillNo;
	
	/** 
	* 货物名称 
	*/
	private String goodsName;
	
	/** 
	* 包装 
	*/
	private String packing;
	
	/** 
	* 出发部门代码 
	*/
	private String origOrgCode;
	
	/** 
	* 出发部门名称 
	*/
	private String origOrgName;
	
	/** 
	* 到达部门/外发代理编码 
	*/
	private String destOrgCode;
	
	/** 
	* 到达部门/外发代理名称 
	*/
	private String destOrgName;
	
	/** 
	* 运输性质 
	*/
	private String transportType;
	
	/** 
	* 开单件数 
	*/
	private BigDecimal goodsQty;
	
	/** 
	* 点单件数 
	*/
	private BigDecimal orderGoodsQty;
	
	/** 
	* 已配件数 
	*/
	private BigDecimal handoverGoodsQty;
	
	/** 
	* 已配重量 
	*/
	private BigDecimal handoverWeight;
	
	/** 
	* 已配体积 
	*/
	private BigDecimal handoverVolume;
	/**
	 * 少货
	 */
	private BigDecimal lostGoodsQty;
	/**
	 * 多货
	 */
	private BigDecimal moreGoodsQty;
	/** 
	* 备注 
	*/
	private String notes;
	
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
	* 点单差异类型（NORMAL正常,LOSE少货,MORE多货） 
	*/
	private String orderReportType;
	/**
	 * 是否处理完毕对应流水Y是N否
	 */
	private String isHandle;
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
	/**get 报告编号 
	 * @return  the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}
	/**
	 * set 报告编号 
	 * @param reportNo the reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**get 交接单号 
	 * @return  the handoverNo
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	/**set 交接单号 
	 * @param handoverNo the handoverNo to set
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
	/**运单号
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**运单号
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**货物名称 
	 * @return  the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**货物名称 
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**包装
	 * @return  the packing
	 */
	public String getPacking() {
		return packing;
	}
	/**包装
	 * @param packing the packing to set
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}
	/**出发部门代码 
	 * @return  the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	/**出发部门代码 
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	/**出发部门名称
	 * @return  the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	/**出发部门名称
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	/**到达部门/外发代理编码 
	 * @return  the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	/**到达部门/外发代理编码 
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	/**
	 * 到达部门/外发代理编码 
	 * @return  the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	/**
	 * 到达部门/外发代理编码 
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	/**
	 * 运输性质
	 * @return  the transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * 运输性质
	 * @param transportType the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * 开单件数
	 * @return  the goodsQty
	 */
	public BigDecimal getGoodsQty() {
		return goodsQty;
	}
	/**
	 * 开单件数
	 * @param goodsQty the goodsQty to set
	 */
	public void setGoodsQty(BigDecimal goodsQty) {
		this.goodsQty = goodsQty;
	}
	/**
	 * 点单件数 
	 * @return  the orderGoodsQty
	 */
	public BigDecimal getOrderGoodsQty() {
		return orderGoodsQty;
	}
	/**
	 * 点单件数 
	 * @param orderGoodsQty the orderGoodsQty to set
	 */
	public void setOrderGoodsQty(BigDecimal orderGoodsQty) {
		this.orderGoodsQty = orderGoodsQty;
	}
	/**
	 * 已配件数 
	 * @return  the handoverGoodsQty
	 */
	public BigDecimal getHandoverGoodsQty() {
		return handoverGoodsQty;
	}
	/**
	 * 已配件数 
	 * @param handoverGoodsQty the handoverGoodsQty to set
	 */
	public void setHandoverGoodsQty(BigDecimal handoverGoodsQty) {
		this.handoverGoodsQty = handoverGoodsQty;
	}
	/**
	 * 已配重量 
	 * @return  the handoverWeight
	 */
	public BigDecimal getHandoverWeight() {
		return handoverWeight;
	}
	/**
	 * 已配重量 
	 * @param handoverWeight the handoverWeight to set
	 */
	public void setHandoverWeight(BigDecimal handoverWeight) {
		this.handoverWeight = handoverWeight;
	}
	/**
	 * 已配体积
	 * @return  the handoverVolume
	 */
	public BigDecimal getHandoverVolume() {
		return handoverVolume;
	}
	/**
	 * 已配体积
	 * @param handoverVolume the handoverVolume to set
	 */
	public void setHandoverVolume(BigDecimal handoverVolume) {
		this.handoverVolume = handoverVolume;
	}
	/**备注
	 * @return  the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**备注
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	 * 点单差异类型（NORMAL正常,LOSE少货,MORE多货）
	 * @return  the orderReportType
	 */
	public String getOrderReportType() {
		return orderReportType;
	}
	/** 
	* 点单差异类型（NORMAL正常,LOSE少货,MORE多货） 
	 * @param orderReportType the orderReportType to set
	 */
	public void setOrderReportType(String orderReportType) {
		this.orderReportType = orderReportType;
	}
	/**
	 * 是否处理完毕对应流水Y是N否
	 * @return  the isHandle
	 */
	public String getIsHandle() {
		return isHandle;
	}
	/**
	 * 是否处理完毕对应流水Y是N否
	 * @param isHandle the isHandle to set
	 */
	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}
	/**
	 * 少货
	 * @return  the lostGoodsQty
	 */
	public BigDecimal getLostGoodsQty() {
		return lostGoodsQty;
	}
	/**
	 * 少货
	 * @param lostGoodsQty the lostGoodsQty to set
	 */
	public void setLostGoodsQty(BigDecimal lostGoodsQty) {
		this.lostGoodsQty = lostGoodsQty;
	}
	/**
	 * 多货
	 * @return  the moreGoodsQty
	 */
	public BigDecimal getMoreGoodsQty() {
		return moreGoodsQty;
	}
	/**
	 * 多货
	 * @param moreGoodsQty the moreGoodsQty to set
	 */
	public void setMoreGoodsQty(BigDecimal moreGoodsQty) {
		this.moreGoodsQty = moreGoodsQty;
	}
	
}
