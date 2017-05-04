package com.deppon.foss.module.settlement.closing.api.shared.domain;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 空运往来月报表
 * @author 045738-foss-maojianqiang
 * @date 2013-11-15 下午3:59:04
 */
public class MvrNafiEntity implements Serializable{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	
    private String id;

    private String period;

    private String productCode;

    private String customerCode;

    private String customerName;

    private String orgCode;

    private String orgName;

    private String orgType;

    private String orgUnifiedCode;

    private Date voucherBeginTime;

    private Date voucherEndTime;

    private String customerType;

    private String invoiceMark;

    private BigDecimal uroaDestChNpod;

    private BigDecimal uroaDestCdPod;

    private BigDecimal urtaDestCdNpod;

    private BigDecimal apwrOthPayWoDestRcvoPod;

    private BigDecimal apwrCostWoDestRcvtPod;

    private BigDecimal apwrOthPayWoDestRcvtNpod;

    private BigDecimal airCodCdUrPod;

    private BigDecimal airCodOthPayWoCodRcvPod;

    private BigDecimal airDrWoDestRcvoNpod;

    private BigDecimal uroaDestCdNpod;

    private BigDecimal uroaDestChPod;

    private BigDecimal urtaDestChNpod;

    private BigDecimal urtaDestChPod;

    private BigDecimal urtaDestCdPod;

    private BigDecimal apwrCostWoDestRcvoPod;

    private BigDecimal apwrCostWoDestRcvoNpod;

    private BigDecimal apwrOthPayWoDestRcvoNpod;

    private BigDecimal apwrCostWoDestRcvtNpod;

    private BigDecimal apwrOthPayWoDestRcvtPod;

    private BigDecimal airCodPodNwo;

    private BigDecimal airCodUpdNwo;

    private BigDecimal airCodChUrPod;

    private BigDecimal airCodPodWo;

    private BigDecimal airCodUpdWo;

    private BigDecimal airCodDpayWoCodRcvPod;

    private BigDecimal airDrWoDestRcvoPod;

    private BigDecimal airDrWoDestRcvtPod;

    private BigDecimal airDrWoDestRcvtNpod;

    private BigDecimal airDrWoCodRcvPod;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgUnifiedCode() {
		return orgUnifiedCode;
	}

	public void setOrgUnifiedCode(String orgUnifiedCode) {
		this.orgUnifiedCode = orgUnifiedCode;
	}

	public Date getVoucherBeginTime() {
		return voucherBeginTime;
	}

	public void setVoucherBeginTime(Date voucherBeginTime) {
		this.voucherBeginTime = voucherBeginTime;
	}

	public Date getVoucherEndTime() {
		return voucherEndTime;
	}

	public void setVoucherEndTime(Date voucherEndTime) {
		this.voucherEndTime = voucherEndTime;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public BigDecimal getUroaDestChNpod() {
		return uroaDestChNpod;
	}

	public void setUroaDestChNpod(BigDecimal uroaDestChNpod) {
		this.uroaDestChNpod = uroaDestChNpod;
	}

	public BigDecimal getUroaDestCdPod() {
		return uroaDestCdPod;
	}

	public void setUroaDestCdPod(BigDecimal uroaDestCdPod) {
		this.uroaDestCdPod = uroaDestCdPod;
	}

	public BigDecimal getUrtaDestCdNpod() {
		return urtaDestCdNpod;
	}

	public void setUrtaDestCdNpod(BigDecimal urtaDestCdNpod) {
		this.urtaDestCdNpod = urtaDestCdNpod;
	}

	public BigDecimal getApwrOthPayWoDestRcvoPod() {
		return apwrOthPayWoDestRcvoPod;
	}

	public void setApwrOthPayWoDestRcvoPod(BigDecimal apwrOthPayWoDestRcvoPod) {
		this.apwrOthPayWoDestRcvoPod = apwrOthPayWoDestRcvoPod;
	}

	public BigDecimal getApwrCostWoDestRcvtPod() {
		return apwrCostWoDestRcvtPod;
	}

	public void setApwrCostWoDestRcvtPod(BigDecimal apwrCostWoDestRcvtPod) {
		this.apwrCostWoDestRcvtPod = apwrCostWoDestRcvtPod;
	}

	public BigDecimal getApwrOthPayWoDestRcvtNpod() {
		return apwrOthPayWoDestRcvtNpod;
	}

	public void setApwrOthPayWoDestRcvtNpod(BigDecimal apwrOthPayWoDestRcvtNpod) {
		this.apwrOthPayWoDestRcvtNpod = apwrOthPayWoDestRcvtNpod;
	}

	public BigDecimal getAirCodCdUrPod() {
		return airCodCdUrPod;
	}

	public void setAirCodCdUrPod(BigDecimal airCodCdUrPod) {
		this.airCodCdUrPod = airCodCdUrPod;
	}

	public BigDecimal getAirCodOthPayWoCodRcvPod() {
		return airCodOthPayWoCodRcvPod;
	}

	public void setAirCodOthPayWoCodRcvPod(BigDecimal airCodOthPayWoCodRcvPod) {
		this.airCodOthPayWoCodRcvPod = airCodOthPayWoCodRcvPod;
	}

	public BigDecimal getAirDrWoDestRcvoNpod() {
		return airDrWoDestRcvoNpod;
	}

	public void setAirDrWoDestRcvoNpod(BigDecimal airDrWoDestRcvoNpod) {
		this.airDrWoDestRcvoNpod = airDrWoDestRcvoNpod;
	}

	public BigDecimal getUroaDestCdNpod() {
		return uroaDestCdNpod;
	}

	public void setUroaDestCdNpod(BigDecimal uroaDestCdNpod) {
		this.uroaDestCdNpod = uroaDestCdNpod;
	}

	public BigDecimal getUroaDestChPod() {
		return uroaDestChPod;
	}

	public void setUroaDestChPod(BigDecimal uroaDestChPod) {
		this.uroaDestChPod = uroaDestChPod;
	}

	public BigDecimal getUrtaDestChNpod() {
		return urtaDestChNpod;
	}

	public void setUrtaDestChNpod(BigDecimal urtaDestChNpod) {
		this.urtaDestChNpod = urtaDestChNpod;
	}

	public BigDecimal getUrtaDestChPod() {
		return urtaDestChPod;
	}

	public void setUrtaDestChPod(BigDecimal urtaDestChPod) {
		this.urtaDestChPod = urtaDestChPod;
	}

	public BigDecimal getUrtaDestCdPod() {
		return urtaDestCdPod;
	}

	public void setUrtaDestCdPod(BigDecimal urtaDestCdPod) {
		this.urtaDestCdPod = urtaDestCdPod;
	}

	public BigDecimal getApwrCostWoDestRcvoPod() {
		return apwrCostWoDestRcvoPod;
	}

	public void setApwrCostWoDestRcvoPod(BigDecimal apwrCostWoDestRcvoPod) {
		this.apwrCostWoDestRcvoPod = apwrCostWoDestRcvoPod;
	}

	public BigDecimal getApwrCostWoDestRcvoNpod() {
		return apwrCostWoDestRcvoNpod;
	}

	public void setApwrCostWoDestRcvoNpod(BigDecimal apwrCostWoDestRcvoNpod) {
		this.apwrCostWoDestRcvoNpod = apwrCostWoDestRcvoNpod;
	}

	public BigDecimal getApwrOthPayWoDestRcvoNpod() {
		return apwrOthPayWoDestRcvoNpod;
	}

	public void setApwrOthPayWoDestRcvoNpod(BigDecimal apwrOthPayWoDestRcvoNpod) {
		this.apwrOthPayWoDestRcvoNpod = apwrOthPayWoDestRcvoNpod;
	}

	public BigDecimal getApwrCostWoDestRcvtNpod() {
		return apwrCostWoDestRcvtNpod;
	}

	public void setApwrCostWoDestRcvtNpod(BigDecimal apwrCostWoDestRcvtNpod) {
		this.apwrCostWoDestRcvtNpod = apwrCostWoDestRcvtNpod;
	}

	public BigDecimal getApwrOthPayWoDestRcvtPod() {
		return apwrOthPayWoDestRcvtPod;
	}

	public void setApwrOthPayWoDestRcvtPod(BigDecimal apwrOthPayWoDestRcvtPod) {
		this.apwrOthPayWoDestRcvtPod = apwrOthPayWoDestRcvtPod;
	}

	public BigDecimal getAirCodPodNwo() {
		return airCodPodNwo;
	}

	public void setAirCodPodNwo(BigDecimal airCodPodNwo) {
		this.airCodPodNwo = airCodPodNwo;
	}

	public BigDecimal getAirCodUpdNwo() {
		return airCodUpdNwo;
	}

	public void setAirCodUpdNwo(BigDecimal airCodUpdNwo) {
		this.airCodUpdNwo = airCodUpdNwo;
	}

	public BigDecimal getAirCodChUrPod() {
		return airCodChUrPod;
	}

	public void setAirCodChUrPod(BigDecimal airCodChUrPod) {
		this.airCodChUrPod = airCodChUrPod;
	}

	public BigDecimal getAirCodPodWo() {
		return airCodPodWo;
	}

	public void setAirCodPodWo(BigDecimal airCodPodWo) {
		this.airCodPodWo = airCodPodWo;
	}

	public BigDecimal getAirCodUpdWo() {
		return airCodUpdWo;
	}

	public void setAirCodUpdWo(BigDecimal airCodUpdWo) {
		this.airCodUpdWo = airCodUpdWo;
	}

	public BigDecimal getAirCodDpayWoCodRcvPod() {
		return airCodDpayWoCodRcvPod;
	}

	public void setAirCodDpayWoCodRcvPod(BigDecimal airCodDpayWoCodRcvPod) {
		this.airCodDpayWoCodRcvPod = airCodDpayWoCodRcvPod;
	}

	public BigDecimal getAirDrWoDestRcvoPod() {
		return airDrWoDestRcvoPod;
	}

	public void setAirDrWoDestRcvoPod(BigDecimal airDrWoDestRcvoPod) {
		this.airDrWoDestRcvoPod = airDrWoDestRcvoPod;
	}

	public BigDecimal getAirDrWoDestRcvtPod() {
		return airDrWoDestRcvtPod;
	}

	public void setAirDrWoDestRcvtPod(BigDecimal airDrWoDestRcvtPod) {
		this.airDrWoDestRcvtPod = airDrWoDestRcvtPod;
	}

	public BigDecimal getAirDrWoDestRcvtNpod() {
		return airDrWoDestRcvtNpod;
	}

	public void setAirDrWoDestRcvtNpod(BigDecimal airDrWoDestRcvtNpod) {
		this.airDrWoDestRcvtNpod = airDrWoDestRcvtNpod;
	}

	public BigDecimal getAirDrWoCodRcvPod() {
		return airDrWoCodRcvPod;
	}

	public void setAirDrWoCodRcvPod(BigDecimal airDrWoCodRcvPod) {
		this.airDrWoCodRcvPod = airDrWoCodRcvPod;
	}

    
}