package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EscResultBillCalculateDto;

/**
 * 价格计算响应实体
 * @author Foss-308595-GELL
 *
 */
@XmlRootElement(name="EscWayBillResponseEntity")
public class EscWayBillResponseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 成功标志 0:失败；1：成功
     */
    private String success;
    /**
     * 失败原因
     */
    private String message;
    
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayFee;
	/**
	 *预付金额 
	 */
	private BigDecimal prePayFee;
	
	/**
	 * 公布价运费
	 */
	private BigDecimal TransportFee;
	
	/**
	 * 优惠总金额
	 */
	private BigDecimal promotionsFee;
	
	/**
	 * 增值费用
	 */
	private BigDecimal valueAddFee;
	
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	
	/**
	 * 总金额
	 */
	private BigDecimal totalFee;
	
	/**
	 * 费用信息
	 */
	private List<EscResultBillCalculateDto> resultBillCalculateDtos;
	
	//加收方式(前台页面显示)
  	private BigDecimal servicefee;
  	
    //折让方式-装卸费(数据库存储的费用)
  	private BigDecimal dcServicefee;
  	
  	//装卸费是否在前台页面展示（false：不在前台页面展示且不能编辑）
  	private boolean serviceChargeFlag;
  	
  	//代收手续费 
  	private BigDecimal codFee;
  	
  	//保价费 
  	private BigDecimal insuranceFee;
  	
  	//计费重量 
  	private BigDecimal billWeight;
  	
    //优惠卷
  	private CouponInfoDto couponInfoDto;
  	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public List<EscResultBillCalculateDto> getResultBillCalculateDtos() {
		return resultBillCalculateDtos;
	}
	public void setResultBillCalculateDtos(
			List<EscResultBillCalculateDto> resultBillCalculateDtos) {
		this.resultBillCalculateDtos = resultBillCalculateDtos;
	}
	public BigDecimal getToPayFee() {
		return toPayFee;
	}
	public void setToPayFee(BigDecimal toPayFee) {
		this.toPayFee = toPayFee;
	}
	public BigDecimal getPrePayFee() {
		return prePayFee;
	}
	public void setPrePayFee(BigDecimal prePayFee) {
		this.prePayFee = prePayFee;
	}
	public BigDecimal getTransportFee() {
		return TransportFee;
	}
	public void setTransportFee(BigDecimal transportFee) {
		TransportFee = transportFee;
	}
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}
	public void setPromotionsFee(BigDecimal promotionsFee) {
		// 四舍五入取整数
		this.promotionsFee = promotionsFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入;
	}
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}
	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BigDecimal getServicefee() {
		return servicefee;
	}
	public void setServicefee(BigDecimal servicefee) {
		this.servicefee = servicefee;
	}
	public BigDecimal getDcServicefee() {
		return dcServicefee;
	}
	public void setDcServicefee(BigDecimal dcServicefee) {
		this.dcServicefee = dcServicefee;
	}
	public boolean isServiceChargeFlag() {
		return serviceChargeFlag;
	}
	public void setServiceChargeFlag(boolean serviceChargeFlag) {
		this.serviceChargeFlag = serviceChargeFlag;
	}
	public BigDecimal getCodFee() {
		return codFee;
	}
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public BigDecimal getBillWeight() {
		return billWeight;
	}
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}
	public CouponInfoDto getCouponInfoDto() {
		return couponInfoDto;
	}
	public void setCouponInfoDto(CouponInfoDto couponInfoDto) {
		this.couponInfoDto = couponInfoDto;
	}
}
