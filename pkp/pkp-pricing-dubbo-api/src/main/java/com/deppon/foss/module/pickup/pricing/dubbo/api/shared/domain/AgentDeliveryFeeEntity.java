package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * @author 092020-lipengfei
 *	代理送货费实体
 */
public class AgentDeliveryFeeEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6951275152484769987L;
	/**
	 * 方案ID
	 */
	private String schemeId;
	/**
	 * 范围（起点）:重量/体积范围起点
	 */
	private BigDecimal startPoint;
	/**
	 * 范围（终点）：重量/体积范围终点
	 */
	private BigDecimal terminalPoint;
	/**
	 * 最低一票
	 */
	private int lowestPrice;
	/**
	 * 收费标准：针对重量或体积设定的基础值，重量按照一公斤多少元，体积按照一立方多少元
	 */
	private BigDecimal chargeStandard;
	/**
	 * 计价方式：按重量或按体积
	 */
	private String pricingManner;
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	/*=======================getter/setter==============================*/
	/**
	 * 版本号
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	/**
	 * 版本号
	 * @param versionNo
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * 范围（起点）:重量/体积范围起点
	 * @return startPoint
	 */
	public BigDecimal getStartPoint() {
		return startPoint;
	}
	/**
	 * 范围（起点）:重量/体积范围起点
	 * @param startPoint
	 */
	public void setStartPoint(BigDecimal startPoint) {
		this.startPoint = startPoint;
	}
	/**
	 * 范围（终点）：重量/体积范围终点
	 * @return terminalPoint
	 */
	public BigDecimal getTerminalPoint() {
		return terminalPoint;
	}
	/**
	 * 范围（终点）：重量/体积范围终点
	 * @param terminalPoint
	 */
	public void setTerminalPoint(BigDecimal terminalPoint) {
		this.terminalPoint = terminalPoint;
	}
	/**
	 * 最低一票
	 * @return lowestPrice
	 */
	public int getLowestPrice() {
		return lowestPrice;
	}
	/**
	 * 最低一票
	 * @param lowestPrice
	 */
	public void setLowestPrice(int lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	/**
	 * 收费标准：针对重量或体积设定的基础值，重量按照一公斤多少元，体积按照一立方多少元
	 * @return chargeStandard
	 */
	public BigDecimal getChargeStandard() {
		return chargeStandard;
	}
	/**
	 * 收费标准：针对重量或体积设定的基础值，重量按照一公斤多少元，体积按照一立方多少元
	 * @param chargeStandard
	 */
	public void setChargeStandard(BigDecimal chargeStandard) {
		this.chargeStandard = chargeStandard;
	}
	/**
	 * 计价方式：按重量或按体积
	 * @return pricingManner
	 */
	public String getPricingManner() {
		return pricingManner;
	}
	/**
	 * 计价方式：按重量或按体积
	 * @param pricingManner
	 */
	public void setPricingManner(String pricingManner) {
		this.pricingManner = pricingManner;
	}
	/**
	 * 方案ID
	 * @return schemeId
	 */
	public String getSchemeId() {
		return schemeId;
	}
	/**
	 * 方案ID
	 * @param schemeId
	 */
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
}
