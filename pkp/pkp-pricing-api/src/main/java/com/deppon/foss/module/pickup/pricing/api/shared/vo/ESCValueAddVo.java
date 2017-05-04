package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 悟空系统增值服务查询参数实体
 * @author FOSS-273279 liding
 * 2016-4-20 上午10:13:02
 */
public class ESCValueAddVo implements Serializable{

		
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
