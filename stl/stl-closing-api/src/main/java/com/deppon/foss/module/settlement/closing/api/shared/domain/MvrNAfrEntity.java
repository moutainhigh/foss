package com.deppon.foss.module.settlement.closing.api.shared.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 空运月报表
 * @author 045738-foss-maojianqiang
 * @date 2013-11-15 下午3:59:04
 */
public class MvrNAfrEntity implements Serializable {
   
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String period;

    private String productCode;

    private String customerCode;

    private String customerName;

    private String origOrgCode;

    private String origOrgName;

    private String destOrgCode;

    private String destOrgName;

    private String origUnifiedCode;

    private String destUnifiedCode;

    private Date voucherBeginTime;

    private Date voucherEndTime;

    private String customerType;

    private BigDecimal uroaDestChNpod;

    private BigDecimal uroaDestCdPod;

    private BigDecimal urtaDestCdNpod;

    private BigDecimal airCostFeeConfirm;

    private BigDecimal airCostDestAgencyPayCfrm;

    private BigDecimal airCostOthPayCostCfrm;

    private BigDecimal airCostPayApply;

    private BigDecimal apwrOthPayWoDestRcvoPod;

    private BigDecimal apwrCostWoDestRcvtNpod;

    private BigDecimal apwrOthPayWoDestRcvtNpod;

    private BigDecimal airCodPodWo;

    private BigDecimal airCodCdUrNpod;

    private BigDecimal airCodCostWoCodRcvNpod;

    private BigDecimal airDrCh;

    private BigDecimal airDrWoDestRcvoNpod;

    private BigDecimal airDrWoCodRcvNpod;

    private BigDecimal aptAirCompany;

    private BigDecimal aptWoOthPay;

    private BigDecimal uroaDestCdNpod;

    private BigDecimal uroaDestChPod;

    private BigDecimal urtaDestChNpod;

    private BigDecimal urtaDestChPod;

    private BigDecimal urtaDestCdPod;

    private BigDecimal airCostOrigAgencyPayCfrm;

    private BigDecimal airCostDestAgencyPayGen;

    private BigDecimal airCostDestAgencyPayNcfrm;

    private BigDecimal aorEntry;

    private BigDecimal aorChUr;

    private BigDecimal aorCdUr;

    private BigDecimal apwrCostWoDestRcvoPod;

    private BigDecimal apwrCostWoDestRcvoNpod;

    private BigDecimal apwrOthPayWoDestRcvoNpod;

    private BigDecimal apwrCostWoDestRcvtPod;

    private BigDecimal apwrOthPayWoDestRcvtPod;

    private BigDecimal apwrOthPayWoOthRcv;

    private BigDecimal airCodChUrPod;

    private BigDecimal airCodCdUrPod;

    private BigDecimal airCodUpdWo;

    private BigDecimal airCodChUrNpod;

    private BigDecimal airCodDpayWoCodRcvPod;

    private BigDecimal airCodOthPayWoCodRcvPod;

    private BigDecimal airCodOpayWoCodRcvNpod;

    private BigDecimal airDrCd;

    private BigDecimal airDrWoDestRcvoPod;

    private BigDecimal airDrWoDestRcvtPod;

    private BigDecimal airDrWoDestRcvtNpod;

    private BigDecimal airDrWoOthRcv;

    private BigDecimal airDrWoCodRcvPod;

    private BigDecimal airDrPayApply;

    private BigDecimal aptWoAirPay;

    private BigDecimal bwor;

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

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getOrigUnifiedCode() {
		return origUnifiedCode;
	}

	public void setOrigUnifiedCode(String origUnifiedCode) {
		this.origUnifiedCode = origUnifiedCode;
	}

	public String getDestUnifiedCode() {
		return destUnifiedCode;
	}

	public void setDestUnifiedCode(String destUnifiedCode) {
		this.destUnifiedCode = destUnifiedCode;
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

	public BigDecimal getAirCostFeeConfirm() {
		return airCostFeeConfirm;
	}

	public void setAirCostFeeConfirm(BigDecimal airCostFeeConfirm) {
		this.airCostFeeConfirm = airCostFeeConfirm;
	}

	public BigDecimal getAirCostDestAgencyPayCfrm() {
		return airCostDestAgencyPayCfrm;
	}

	public void setAirCostDestAgencyPayCfrm(BigDecimal airCostDestAgencyPayCfrm) {
		this.airCostDestAgencyPayCfrm = airCostDestAgencyPayCfrm;
	}

	public BigDecimal getAirCostOthPayCostCfrm() {
		return airCostOthPayCostCfrm;
	}

	public void setAirCostOthPayCostCfrm(BigDecimal airCostOthPayCostCfrm) {
		this.airCostOthPayCostCfrm = airCostOthPayCostCfrm;
	}

	public BigDecimal getAirCostPayApply() {
		return airCostPayApply;
	}

	public void setAirCostPayApply(BigDecimal airCostPayApply) {
		this.airCostPayApply = airCostPayApply;
	}

	public BigDecimal getApwrOthPayWoDestRcvoPod() {
		return apwrOthPayWoDestRcvoPod;
	}

	public void setApwrOthPayWoDestRcvoPod(BigDecimal apwrOthPayWoDestRcvoPod) {
		this.apwrOthPayWoDestRcvoPod = apwrOthPayWoDestRcvoPod;
	}

	public BigDecimal getApwrCostWoDestRcvtNpod() {
		return apwrCostWoDestRcvtNpod;
	}

	public void setApwrCostWoDestRcvtNpod(BigDecimal apwrCostWoDestRcvtNpod) {
		this.apwrCostWoDestRcvtNpod = apwrCostWoDestRcvtNpod;
	}

	public BigDecimal getApwrOthPayWoDestRcvtNpod() {
		return apwrOthPayWoDestRcvtNpod;
	}

	public void setApwrOthPayWoDestRcvtNpod(BigDecimal apwrOthPayWoDestRcvtNpod) {
		this.apwrOthPayWoDestRcvtNpod = apwrOthPayWoDestRcvtNpod;
	}

	public BigDecimal getAirCodPodWo() {
		return airCodPodWo;
	}

	public void setAirCodPodWo(BigDecimal airCodPodWo) {
		this.airCodPodWo = airCodPodWo;
	}

	public BigDecimal getAirCodCdUrNpod() {
		return airCodCdUrNpod;
	}

	public void setAirCodCdUrNpod(BigDecimal airCodCdUrNpod) {
		this.airCodCdUrNpod = airCodCdUrNpod;
	}

	public BigDecimal getAirCodCostWoCodRcvNpod() {
		return airCodCostWoCodRcvNpod;
	}

	public void setAirCodCostWoCodRcvNpod(BigDecimal airCodCostWoCodRcvNpod) {
		this.airCodCostWoCodRcvNpod = airCodCostWoCodRcvNpod;
	}

	public BigDecimal getAirDrCh() {
		return airDrCh;
	}

	public void setAirDrCh(BigDecimal airDrCh) {
		this.airDrCh = airDrCh;
	}

	public BigDecimal getAirDrWoDestRcvoNpod() {
		return airDrWoDestRcvoNpod;
	}

	public void setAirDrWoDestRcvoNpod(BigDecimal airDrWoDestRcvoNpod) {
		this.airDrWoDestRcvoNpod = airDrWoDestRcvoNpod;
	}

	public BigDecimal getAirDrWoCodRcvNpod() {
		return airDrWoCodRcvNpod;
	}

	public void setAirDrWoCodRcvNpod(BigDecimal airDrWoCodRcvNpod) {
		this.airDrWoCodRcvNpod = airDrWoCodRcvNpod;
	}

	public BigDecimal getAptAirCompany() {
		return aptAirCompany;
	}

	public void setAptAirCompany(BigDecimal aptAirCompany) {
		this.aptAirCompany = aptAirCompany;
	}

	public BigDecimal getAptWoOthPay() {
		return aptWoOthPay;
	}

	public void setAptWoOthPay(BigDecimal aptWoOthPay) {
		this.aptWoOthPay = aptWoOthPay;
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

	public BigDecimal getAirCostOrigAgencyPayCfrm() {
		return airCostOrigAgencyPayCfrm;
	}

	public void setAirCostOrigAgencyPayCfrm(BigDecimal airCostOrigAgencyPayCfrm) {
		this.airCostOrigAgencyPayCfrm = airCostOrigAgencyPayCfrm;
	}

	public BigDecimal getAirCostDestAgencyPayGen() {
		return airCostDestAgencyPayGen;
	}

	public void setAirCostDestAgencyPayGen(BigDecimal airCostDestAgencyPayGen) {
		this.airCostDestAgencyPayGen = airCostDestAgencyPayGen;
	}

	public BigDecimal getAirCostDestAgencyPayNcfrm() {
		return airCostDestAgencyPayNcfrm;
	}

	public void setAirCostDestAgencyPayNcfrm(BigDecimal airCostDestAgencyPayNcfrm) {
		this.airCostDestAgencyPayNcfrm = airCostDestAgencyPayNcfrm;
	}

	public BigDecimal getAorEntry() {
		return aorEntry;
	}

	public void setAorEntry(BigDecimal aorEntry) {
		this.aorEntry = aorEntry;
	}

	public BigDecimal getAorChUr() {
		return aorChUr;
	}

	public void setAorChUr(BigDecimal aorChUr) {
		this.aorChUr = aorChUr;
	}

	public BigDecimal getAorCdUr() {
		return aorCdUr;
	}

	public void setAorCdUr(BigDecimal aorCdUr) {
		this.aorCdUr = aorCdUr;
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

	public BigDecimal getApwrCostWoDestRcvtPod() {
		return apwrCostWoDestRcvtPod;
	}

	public void setApwrCostWoDestRcvtPod(BigDecimal apwrCostWoDestRcvtPod) {
		this.apwrCostWoDestRcvtPod = apwrCostWoDestRcvtPod;
	}

	public BigDecimal getApwrOthPayWoDestRcvtPod() {
		return apwrOthPayWoDestRcvtPod;
	}

	public void setApwrOthPayWoDestRcvtPod(BigDecimal apwrOthPayWoDestRcvtPod) {
		this.apwrOthPayWoDestRcvtPod = apwrOthPayWoDestRcvtPod;
	}

	public BigDecimal getApwrOthPayWoOthRcv() {
		return apwrOthPayWoOthRcv;
	}

	public void setApwrOthPayWoOthRcv(BigDecimal apwrOthPayWoOthRcv) {
		this.apwrOthPayWoOthRcv = apwrOthPayWoOthRcv;
	}

	public BigDecimal getAirCodChUrPod() {
		return airCodChUrPod;
	}

	public void setAirCodChUrPod(BigDecimal airCodChUrPod) {
		this.airCodChUrPod = airCodChUrPod;
	}

	public BigDecimal getAirCodCdUrPod() {
		return airCodCdUrPod;
	}

	public void setAirCodCdUrPod(BigDecimal airCodCdUrPod) {
		this.airCodCdUrPod = airCodCdUrPod;
	}

	public BigDecimal getAirCodUpdWo() {
		return airCodUpdWo;
	}

	public void setAirCodUpdWo(BigDecimal airCodUpdWo) {
		this.airCodUpdWo = airCodUpdWo;
	}

	public BigDecimal getAirCodChUrNpod() {
		return airCodChUrNpod;
	}

	public void setAirCodChUrNpod(BigDecimal airCodChUrNpod) {
		this.airCodChUrNpod = airCodChUrNpod;
	}

	public BigDecimal getAirCodDpayWoCodRcvPod() {
		return airCodDpayWoCodRcvPod;
	}

	public void setAirCodDpayWoCodRcvPod(BigDecimal airCodDpayWoCodRcvPod) {
		this.airCodDpayWoCodRcvPod = airCodDpayWoCodRcvPod;
	}

	public BigDecimal getAirCodOthPayWoCodRcvPod() {
		return airCodOthPayWoCodRcvPod;
	}

	public void setAirCodOthPayWoCodRcvPod(BigDecimal airCodOthPayWoCodRcvPod) {
		this.airCodOthPayWoCodRcvPod = airCodOthPayWoCodRcvPod;
	}

	public BigDecimal getAirCodOpayWoCodRcvNpod() {
		return airCodOpayWoCodRcvNpod;
	}

	public void setAirCodOpayWoCodRcvNpod(BigDecimal airCodOpayWoCodRcvNpod) {
		this.airCodOpayWoCodRcvNpod = airCodOpayWoCodRcvNpod;
	}

	public BigDecimal getAirDrCd() {
		return airDrCd;
	}

	public void setAirDrCd(BigDecimal airDrCd) {
		this.airDrCd = airDrCd;
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

	public BigDecimal getAirDrWoOthRcv() {
		return airDrWoOthRcv;
	}

	public void setAirDrWoOthRcv(BigDecimal airDrWoOthRcv) {
		this.airDrWoOthRcv = airDrWoOthRcv;
	}

	public BigDecimal getAirDrWoCodRcvPod() {
		return airDrWoCodRcvPod;
	}

	public void setAirDrWoCodRcvPod(BigDecimal airDrWoCodRcvPod) {
		this.airDrWoCodRcvPod = airDrWoCodRcvPod;
	}

	public BigDecimal getAirDrPayApply() {
		return airDrPayApply;
	}

	public void setAirDrPayApply(BigDecimal airDrPayApply) {
		this.airDrPayApply = airDrPayApply;
	}

	public BigDecimal getAptWoAirPay() {
		return aptWoAirPay;
	}

	public void setAptWoAirPay(BigDecimal aptWoAirPay) {
		this.aptWoAirPay = aptWoAirPay;
	}

	public BigDecimal getBwor() {
		return bwor;
	}

	public void setBwor(BigDecimal bwor) {
		this.bwor = bwor;
	}

   
}