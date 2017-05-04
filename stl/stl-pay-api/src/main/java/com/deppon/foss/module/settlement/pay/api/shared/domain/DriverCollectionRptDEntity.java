package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 司机收款报表明细
 * 
 * @author ibm-pengzhen
 * @date 2012-10-10 下午12:32:53
 * @since
 * @version
 */
public class DriverCollectionRptDEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -1190846520195844770L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 报表编号
	 */
	private String reportNo;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 重量
	 */
	private BigDecimal weight;

	/**
	 * 体积
	 */
	private BigDecimal volume;

	/**
	 * 件数
	 */
	private Integer qty;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 是否有签收单
	 */
	private String isSignwaybill;

	/**
	 * 是否有返单
	 */
	private String isReturnTicket;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 刷卡金额
	 */
	private BigDecimal cardAmount;
	
	/**
	 * 现金金额
	 */
	private BigDecimal cashAmount;
	
	/**
	 * @author 218392 张永雪
	 * @date 2015-07-06 08:42:13
	 * 付款方式
	 */
	private String payType;
	

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}

	/**
	 * @param reportNo
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return isSignwaybill
	 */
	public String getIsSignwaybill() {
		return isSignwaybill;
	}

	/**
	 * @param isSignwaybill
	 */
	public void setIsSignwaybill(String isSignwaybill) {
		this.isSignwaybill = isSignwaybill;
	}

	/**
	 * @return isReturnTicket
	 */
	public String getIsReturnTicket() {
		return isReturnTicket;
	}

	/**
	 * @param isReturnTicket
	 */
	public void setIsReturnTicket(String isReturnTicket) {
		this.isReturnTicket = isReturnTicket;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param  currencyCode  
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public BigDecimal getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	
}