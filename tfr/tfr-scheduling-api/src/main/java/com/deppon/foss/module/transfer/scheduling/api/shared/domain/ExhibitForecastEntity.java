package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 展会货 货量统计实体
 * 
 * @author 200978 xiaobingcheng 2014-11-26
 */

public class ExhibitForecastEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 运单号 */
	private String waybillNo;
	/** 状态 */
	private String status;
	/** 库存件数 */
	private Integer stockGoodsQty;
	/** 开单件数 */
	private Integer goodsQtyTotal;
	/** 开单时间 */
	private Date billTime;
	/**入库时间*/
	private Date inStockTime;
	/** 预计到达时间 */
	private Date planArriveTime;
	/** 运输性质编码 */
	private String productCode;
	/** 运输性质 */
	private String productName;
	/** 提货方式 */
	private String receiveMethod;
	/** 重量 */
	private BigDecimal weight;
	/** 体积 */
	private BigDecimal volume;
	/** 货物名称 */
	private String goodsName;
	/** 到达部门编码 */
	private String reachOrgCode;
	/** 到达部门名称 */
	private String reachOrgName;
	/** 包装 */
	private String packing;

	/** 流水号list，其间用，号隔开,方便以后查询异常信息 */
	private String goodsNos;
	/**创建时间*/
	private Date createTime;
	/**是否有效    最新的一次统计为有效，其他无效*/
	private String active;
	/**最终外场编码*/
	private String transferCenterCode;

	
	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStockGoodsQty() {
		return stockGoodsQty;
	}

	public void setStockGoodsQty(Integer stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}


	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getReachOrgCode() {
		return reachOrgCode;
	}

	public void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
	}

	public String getReachOrgName() {
		return reachOrgName;
	}

	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getGoodsNos() {
		return goodsNos;
	}

	public void setGoodsNos(String goodsNos) {
		this.goodsNos = goodsNos;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
