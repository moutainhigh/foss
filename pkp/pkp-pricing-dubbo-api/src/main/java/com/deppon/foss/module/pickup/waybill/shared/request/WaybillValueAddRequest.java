package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 获取增值服务的请求实体
 * 
 * @author foss-273279
 *
 */
public class WaybillValueAddRequest extends BaseEntity implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 出发部门code
	 */
	private String originalOrgCode;
	/**
	 * 到达部门code 
	 */
	private String destinationOrgCode;
	/**
	 * 产品类型
	 */
	private String productCode;
	/**
	 * 开单时间
	 */
	private Date billTime;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 币种
	 */
	private String currencyCode;
	/**
	 * 条目类型
	 */
	private String pricingEntryCode;
	
	
	@Override
	public String toString() {
		return "WaybillValueAddRequest [originalOrgCode=" + originalOrgCode + ", destinationOrgCode="
				+ destinationOrgCode + ", productCode=" + productCode + ", billTime=" + billTime + ", weight=" + weight
				+ ", volume=" + volume + ", currencyCode=" + currencyCode + ", pricingEntryCode=" + pricingEntryCode
				+ "]";
	}

	
	public String getOriginalOrgCode() {
		return originalOrgCode;
	}
	public void setOriginalOrgCode(String originalOrgCode) {
		this.originalOrgCode = originalOrgCode;
	}
	public String getDestinationOrgCode() {
		return destinationOrgCode;
	}
	public void setDestinationOrgCode(String destinationOrgCode) {
		this.destinationOrgCode = destinationOrgCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
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
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}
	
}
