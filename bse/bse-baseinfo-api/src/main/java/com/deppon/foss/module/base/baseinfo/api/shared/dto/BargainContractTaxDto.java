package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.math.BigDecimal;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;

/**
 * 客户合同-发票标记DTO
 * @author 132599-foss-shenweihua
 * @date 2013-11-16 下午5:06:22
 * @since
 * @version
 */
public class BargainContractTaxDto extends CusBargainEntity{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5896940979058415803L;
	
	/**
	 * 发票标记对应的合同ID
	 */
	private BigDecimal bargainCrmId;
	
	/**
	 * 发票标记
	 */
	private String invoiceType;
	
	/**
	 * 签署合同
	 */
	private String signCompany;
	
	/**
	 * 获取合同ID
	 * @return
	 */
	public BigDecimal getBargainCrmId() {
		return bargainCrmId;
	}
	
	/**
	 * 设置合同ID
	 * @return
	 */
	public void setBargainCrmId(BigDecimal bargainCrmId) {
		this.bargainCrmId = bargainCrmId;
	}
	
	/**
	 * 获取发票标记
	 * @return
	 */
	public String getInvoiceType() {
		return invoiceType;
	}
	
	/**
	 * 设置发票标记
	 * @return
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	/**
	 * 获取合同签署公司
	 * @return
	 */
	public String getSignCompany() {
		return signCompany;
	}
	
	/**
	 * 设置合同签署公司
	 * @return
	 */
	public void setSignCompany(String signCompany) {
		this.signCompany = signCompany;
	}
	

}
