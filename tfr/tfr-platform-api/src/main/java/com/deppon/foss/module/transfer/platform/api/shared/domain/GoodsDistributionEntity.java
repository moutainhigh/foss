package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 装运长货量流动分布
 * @author 200978
 * 2015-3-10
 */
public class GoodsDistributionEntity extends BaseEntity {

	/**转运场编码*/
	private String transferCenterCode;
	
	/**装运场名称*/
	private String transferCenterName;
	
	/**经营本部*/
	private String operationDeptCode;
	
	/**经营本部名称*/
	private String operationDeptName;
	
	/**到达货量*/
	private BigDecimal arriveCargo;
	
	/**到达货量体积*/
	private BigDecimal arriveVolume;
	
	/**出发货量*/
	private BigDecimal departCargo;
	
	/**出发货量体积*/
	private BigDecimal departVolume;
	
	/**实际流入货量*/
	private BigDecimal actualInCargo;
	
	/**实际流入货量体积*/
	private BigDecimal actualInVolume;
	
	/**实际流出货量*/
	private BigDecimal actualOutCargo;
	
	/**实际流出货量体积*/
	private BigDecimal actualOutVolume;
	
	/**货台库存货量*/
	private BigDecimal goodsStockWeight;
	
	/**货台库存货量体积*/
	private BigDecimal goodsStockVolume;
	
	/**统计日期*/
	private Date staDate;
	
	/**统计时间00:30*/
	private String staTime;
	
	/**创建时间*/
	private Date createTime;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public String getOperationDeptCode() {
		return operationDeptCode;
	}

	public void setOperationDeptCode(String operationDeptCode) {
		this.operationDeptCode = operationDeptCode;
	}

	public String getOperationDeptName() {
		return operationDeptName;
	}

	public void setOperationDeptName(String operationDeptName) {
		this.operationDeptName = operationDeptName;
	}

	public BigDecimal getArriveCargo() {
		return arriveCargo;
	}

	public void setArriveCargo(BigDecimal arriveCargo) {
		this.arriveCargo = arriveCargo;
	}

	public BigDecimal getArriveVolume() {
		return arriveVolume;
	}

	public void setArriveVolume(BigDecimal arriveVolume) {
		this.arriveVolume = arriveVolume;
	}

	public BigDecimal getDepartCargo() {
		return departCargo;
	}

	public void setDepartCargo(BigDecimal departCargo) {
		this.departCargo = departCargo;
	}

	public BigDecimal getDepartVolume() {
		return departVolume;
	}

	public void setDepartVolume(BigDecimal departVolume) {
		this.departVolume = departVolume;
	}

	public BigDecimal getActualInCargo() {
		return actualInCargo;
	}

	public void setActualInCargo(BigDecimal actualInCargo) {
		this.actualInCargo = actualInCargo;
	}

	public BigDecimal getActualInVolume() {
		return actualInVolume;
	}

	public void setActualInVolume(BigDecimal actualInVolume) {
		this.actualInVolume = actualInVolume;
	}

	public BigDecimal getActualOutCargo() {
		return actualOutCargo;
	}

	public void setActualOutCargo(BigDecimal actualOutCargo) {
		this.actualOutCargo = actualOutCargo;
	}

	public BigDecimal getActualOutVolume() {
		return actualOutVolume;
	}

	public void setActualOutVolume(BigDecimal actualOutVolume) {
		this.actualOutVolume = actualOutVolume;
	}

	public BigDecimal getGoodsStockWeight() {
		return goodsStockWeight;
	}

	public void setGoodsStockWeight(BigDecimal goodsStockWeight) {
		this.goodsStockWeight = goodsStockWeight;
	}

	public BigDecimal getGoodsStockVolume() {
		return goodsStockVolume;
	}

	public void setGoodsStockVolume(BigDecimal goodsStockVolume) {
		this.goodsStockVolume = goodsStockVolume;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public String getStaTime() {
		return staTime;
	}

	public void setStaTime(String staTime) {
		this.staTime = staTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
