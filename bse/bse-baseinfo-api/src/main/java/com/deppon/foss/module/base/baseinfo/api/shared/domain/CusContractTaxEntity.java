package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 客户发票标记信息实体类
 * @author 132599-foss-shenweihua
 * @date 2013-11-19 上午11:42:24
 * @since
 * @version
 */
public class CusContractTaxEntity extends BaseEntity{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3387921476725163081L;
	
	/**
	 * 发票标记对应的合同ID
	 */
	private BigDecimal bargainCrmId;
	
	/**
	 * 在CRM中fid.
	 */
	private BigDecimal crmId;
	
	/**
	 * 发票标记
	 */
	private String invoiceType;
	
	/**
	 * 签署合同
	 */
	private String signCompany;
	
	 /**
     * 发票标记生效日期.
     */
    private Date beginTime;
    
    /**
     * 发票标记失效日期.
     */
    private Date endTime;
    
    /**
     * 获取发票标记对应的合同ID
     * @return
     */
	public BigDecimal getBargainCrmId() {
		return bargainCrmId;
	}
	
	/**
	 * 设置发票标记对应的合同ID
	 * @param bargainCrmId
	 */
	public void setBargainCrmId(BigDecimal bargainCrmId) {
		this.bargainCrmId = bargainCrmId;
	}
	
	/**
	 * 获取CRM_ID
	 * @return
	 */
	public BigDecimal getCrmId() {
		return crmId;
	}
	
	/**
	 * 设置CRM_ID
	 * @param crmId
	 */
	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
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
	 * @param invoiceType
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	/**
	 * 获取签署合同公司
	 * @return
	 */
	public String getSignCompany() {
		return signCompany;
	}
	
	/**
	 * 设置签署合同公司
	 * @param signCompany
	 */
	public void setSignCompany(String signCompany) {
		this.signCompany = signCompany;
	}
	
	/**
	 * 获取发票标记生效日期
	 * @return
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	
	/**
	 * 设置发票标记生效日期
	 * @param beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	/**
	 * 获取发票标记失效日期
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 * 设置发票标记失效日期
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

}
