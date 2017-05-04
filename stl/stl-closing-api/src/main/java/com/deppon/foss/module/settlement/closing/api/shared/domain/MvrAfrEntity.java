package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 空运月报.
 *
 * @author ibm-zhuwei
 * @date 2013-3-5 下午5:57:52
 */
public class MvrAfrEntity {
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

    private BigDecimal urDestChNpod;

    private BigDecimal urDestCdNpod;

    private BigDecimal urDestChPod;

    private BigDecimal urDestCdPod;

    private BigDecimal airCostComConfirm;

    private BigDecimal airCostOrigAgencyCfm;

    private BigDecimal airCostDestAgencyGen;

    private BigDecimal airCostDestAgencyCfm;

    private BigDecimal airCostDestAgencyNcfm;

    private BigDecimal airCostOtherConfirm;

    private BigDecimal airCostPayApply;

    private BigDecimal othEntry;

    private BigDecimal othRcvCh;

    private BigDecimal othRcvCd;

    private BigDecimal airDrDestRcvPod;

    private BigDecimal airDrDestRcvNpod;

    private BigDecimal airDrCh;

    private BigDecimal airDrCd;

    private BigDecimal airDrWoOtherRcv;

    private BigDecimal airDrWoCodRcvPod;

    private BigDecimal airDrWoCodRcvNpod;

    private BigDecimal airDrPayApply;

    private BigDecimal airPrAgencyWoDestRcvPod;

    private BigDecimal airPrAgencyWoDestRcvNpod;

    private BigDecimal airPrOtWoDestRcvPod;

    private BigDecimal airPrOthWoDestRcvNpod;

    private BigDecimal airPrOthWoOthRcv;

    private BigDecimal airCodChPod;

    private BigDecimal airCodCdPod;

    private BigDecimal airCodPodWoCod;

    private BigDecimal airCodNpodWoCod;

    private BigDecimal airCodChNpod;

    private BigDecimal airCodCdNpod;

    private BigDecimal airCodWoAgencyPayPod;

    private BigDecimal airCodWoOthPayCod;

    private BigDecimal airCodWoAgencyPayNpod;

    private BigDecimal airCodWoOthNpod;

    private BigDecimal aptCom;

    private BigDecimal aptWoComPay;

    private BigDecimal aptWoOthPay;

    private BigDecimal bdrWoOthRcv;

	
	/**
	 * @return  the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * @return  the period
	 */
	public String getPeriod() {
		return period;
	}

	
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	
	/**
	 * @return  the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	/**
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	/**
	 * @return  the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	
	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	
	/**
	 * @return  the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	
	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	
	/**
	 * @return  the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	
	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	
	/**
	 * @return  the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	
	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	
	/**
	 * @return  the origUnifiedCode
	 */
	public String getOrigUnifiedCode() {
		return origUnifiedCode;
	}

	
	/**
	 * @param origUnifiedCode the origUnifiedCode to set
	 */
	public void setOrigUnifiedCode(String origUnifiedCode) {
		this.origUnifiedCode = origUnifiedCode;
	}

	
	/**
	 * @return  the destUnifiedCode
	 */
	public String getDestUnifiedCode() {
		return destUnifiedCode;
	}

	
	/**
	 * @param destUnifiedCode the destUnifiedCode to set
	 */
	public void setDestUnifiedCode(String destUnifiedCode) {
		this.destUnifiedCode = destUnifiedCode;
	}

	
	/**
	 * @return  the voucherBeginTime
	 */
	public Date getVoucherBeginTime() {
		return voucherBeginTime;
	}

	
	/**
	 * @param voucherBeginTime the voucherBeginTime to set
	 */
	public void setVoucherBeginTime(Date voucherBeginTime) {
		this.voucherBeginTime = voucherBeginTime;
	}

	
	/**
	 * @return  the voucherEndTime
	 */
	public Date getVoucherEndTime() {
		return voucherEndTime;
	}

	
	/**
	 * @param voucherEndTime the voucherEndTime to set
	 */
	public void setVoucherEndTime(Date voucherEndTime) {
		this.voucherEndTime = voucherEndTime;
	}

	
	/**
	 * @return  the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	
	/**
	 * @return  the urDestChNpod
	 */
	public BigDecimal getUrDestChNpod() {
		return urDestChNpod;
	}

	
	/**
	 * @param urDestChNpod the urDestChNpod to set
	 */
	public void setUrDestChNpod(BigDecimal urDestChNpod) {
		this.urDestChNpod = urDestChNpod;
	}

	
	/**
	 * @return  the urDestCdNpod
	 */
	public BigDecimal getUrDestCdNpod() {
		return urDestCdNpod;
	}

	
	/**
	 * @param urDestCdNpod the urDestCdNpod to set
	 */
	public void setUrDestCdNpod(BigDecimal urDestCdNpod) {
		this.urDestCdNpod = urDestCdNpod;
	}

	
	/**
	 * @return  the urDestChPod
	 */
	public BigDecimal getUrDestChPod() {
		return urDestChPod;
	}

	
	/**
	 * @param urDestChPod the urDestChPod to set
	 */
	public void setUrDestChPod(BigDecimal urDestChPod) {
		this.urDestChPod = urDestChPod;
	}

	
	/**
	 * @return  the urDestCdPod
	 */
	public BigDecimal getUrDestCdPod() {
		return urDestCdPod;
	}

	
	/**
	 * @param urDestCdPod the urDestCdPod to set
	 */
	public void setUrDestCdPod(BigDecimal urDestCdPod) {
		this.urDestCdPod = urDestCdPod;
	}

	
	/**
	 * @return  the airCostComConfirm
	 */
	public BigDecimal getAirCostComConfirm() {
		return airCostComConfirm;
	}

	
	/**
	 * @param airCostComConfirm the airCostComConfirm to set
	 */
	public void setAirCostComConfirm(BigDecimal airCostComConfirm) {
		this.airCostComConfirm = airCostComConfirm;
	}

	
	/**
	 * @return  the airCostOrigAgencyCfm
	 */
	public BigDecimal getAirCostOrigAgencyCfm() {
		return airCostOrigAgencyCfm;
	}

	
	/**
	 * @param airCostOrigAgencyCfm the airCostOrigAgencyCfm to set
	 */
	public void setAirCostOrigAgencyCfm(BigDecimal airCostOrigAgencyCfm) {
		this.airCostOrigAgencyCfm = airCostOrigAgencyCfm;
	}

	
	/**
	 * @return  the airCostDestAgencyGen
	 */
	public BigDecimal getAirCostDestAgencyGen() {
		return airCostDestAgencyGen;
	}

	
	/**
	 * @param airCostDestAgencyGen the airCostDestAgencyGen to set
	 */
	public void setAirCostDestAgencyGen(BigDecimal airCostDestAgencyGen) {
		this.airCostDestAgencyGen = airCostDestAgencyGen;
	}

	
	/**
	 * @return  the airCostDestAgencyCfm
	 */
	public BigDecimal getAirCostDestAgencyCfm() {
		return airCostDestAgencyCfm;
	}

	
	/**
	 * @param airCostDestAgencyCfm the airCostDestAgencyCfm to set
	 */
	public void setAirCostDestAgencyCfm(BigDecimal airCostDestAgencyCfm) {
		this.airCostDestAgencyCfm = airCostDestAgencyCfm;
	}

	
	/**
	 * @return  the airCostDestAgencyNcfm
	 */
	public BigDecimal getAirCostDestAgencyNcfm() {
		return airCostDestAgencyNcfm;
	}

	
	/**
	 * @param airCostDestAgencyNcfm the airCostDestAgencyNcfm to set
	 */
	public void setAirCostDestAgencyNcfm(BigDecimal airCostDestAgencyNcfm) {
		this.airCostDestAgencyNcfm = airCostDestAgencyNcfm;
	}

	
	/**
	 * @return  the airCostOtherConfirm
	 */
	public BigDecimal getAirCostOtherConfirm() {
		return airCostOtherConfirm;
	}

	
	/**
	 * @param airCostOtherConfirm the airCostOtherConfirm to set
	 */
	public void setAirCostOtherConfirm(BigDecimal airCostOtherConfirm) {
		this.airCostOtherConfirm = airCostOtherConfirm;
	}

	
	/**
	 * @return  the airCostPayApply
	 */
	public BigDecimal getAirCostPayApply() {
		return airCostPayApply;
	}

	
	/**
	 * @param airCostPayApply the airCostPayApply to set
	 */
	public void setAirCostPayApply(BigDecimal airCostPayApply) {
		this.airCostPayApply = airCostPayApply;
	}

	
	/**
	 * @return  the othEntry
	 */
	public BigDecimal getOthEntry() {
		return othEntry;
	}

	
	/**
	 * @param othEntry the othEntry to set
	 */
	public void setOthEntry(BigDecimal othEntry) {
		this.othEntry = othEntry;
	}

	
	/**
	 * @return  the othRcvCh
	 */
	public BigDecimal getOthRcvCh() {
		return othRcvCh;
	}

	
	/**
	 * @param othRcvCh the othRcvCh to set
	 */
	public void setOthRcvCh(BigDecimal othRcvCh) {
		this.othRcvCh = othRcvCh;
	}

	
	/**
	 * @return  the othRcvCd
	 */
	public BigDecimal getOthRcvCd() {
		return othRcvCd;
	}

	
	/**
	 * @param othRcvCd the othRcvCd to set
	 */
	public void setOthRcvCd(BigDecimal othRcvCd) {
		this.othRcvCd = othRcvCd;
	}

	
	/**
	 * @return  the airDrDestRcvPod
	 */
	public BigDecimal getAirDrDestRcvPod() {
		return airDrDestRcvPod;
	}

	
	/**
	 * @param airDrDestRcvPod the airDrDestRcvPod to set
	 */
	public void setAirDrDestRcvPod(BigDecimal airDrDestRcvPod) {
		this.airDrDestRcvPod = airDrDestRcvPod;
	}

	
	/**
	 * @return  the airDrDestRcvNpod
	 */
	public BigDecimal getAirDrDestRcvNpod() {
		return airDrDestRcvNpod;
	}

	
	/**
	 * @param airDrDestRcvNpod the airDrDestRcvNpod to set
	 */
	public void setAirDrDestRcvNpod(BigDecimal airDrDestRcvNpod) {
		this.airDrDestRcvNpod = airDrDestRcvNpod;
	}

	
	/**
	 * @return  the airDrCh
	 */
	public BigDecimal getAirDrCh() {
		return airDrCh;
	}

	
	/**
	 * @param airDrCh the airDrCh to set
	 */
	public void setAirDrCh(BigDecimal airDrCh) {
		this.airDrCh = airDrCh;
	}

	
	/**
	 * @return  the airDrCd
	 */
	public BigDecimal getAirDrCd() {
		return airDrCd;
	}

	
	/**
	 * @param airDrCd the airDrCd to set
	 */
	public void setAirDrCd(BigDecimal airDrCd) {
		this.airDrCd = airDrCd;
	}

	
	/**
	 * @return  the airDrWoOtherRcv
	 */
	public BigDecimal getAirDrWoOtherRcv() {
		return airDrWoOtherRcv;
	}

	
	/**
	 * @param airDrWoOtherRcv the airDrWoOtherRcv to set
	 */
	public void setAirDrWoOtherRcv(BigDecimal airDrWoOtherRcv) {
		this.airDrWoOtherRcv = airDrWoOtherRcv;
	}

	
	/**
	 * @return  the airDrWoCodRcvPod
	 */
	public BigDecimal getAirDrWoCodRcvPod() {
		return airDrWoCodRcvPod;
	}

	
	/**
	 * @param airDrWoCodRcvPod the airDrWoCodRcvPod to set
	 */
	public void setAirDrWoCodRcvPod(BigDecimal airDrWoCodRcvPod) {
		this.airDrWoCodRcvPod = airDrWoCodRcvPod;
	}

	
	/**
	 * @return  the airDrWoCodRcvNpod
	 */
	public BigDecimal getAirDrWoCodRcvNpod() {
		return airDrWoCodRcvNpod;
	}

	
	/**
	 * @param airDrWoCodRcvNpod the airDrWoCodRcvNpod to set
	 */
	public void setAirDrWoCodRcvNpod(BigDecimal airDrWoCodRcvNpod) {
		this.airDrWoCodRcvNpod = airDrWoCodRcvNpod;
	}

	
	/**
	 * @return  the airDrPayApply
	 */
	public BigDecimal getAirDrPayApply() {
		return airDrPayApply;
	}

	
	/**
	 * @param airDrPayApply the airDrPayApply to set
	 */
	public void setAirDrPayApply(BigDecimal airDrPayApply) {
		this.airDrPayApply = airDrPayApply;
	}

	
	/**
	 * @return  the airPrAgencyWoDestRcvPod
	 */
	public BigDecimal getAirPrAgencyWoDestRcvPod() {
		return airPrAgencyWoDestRcvPod;
	}

	
	/**
	 * @param airPrAgencyWoDestRcvPod the airPrAgencyWoDestRcvPod to set
	 */
	public void setAirPrAgencyWoDestRcvPod(BigDecimal airPrAgencyWoDestRcvPod) {
		this.airPrAgencyWoDestRcvPod = airPrAgencyWoDestRcvPod;
	}

	
	/**
	 * @return  the airPrAgencyWoDestRcvNpod
	 */
	public BigDecimal getAirPrAgencyWoDestRcvNpod() {
		return airPrAgencyWoDestRcvNpod;
	}

	
	/**
	 * @param airPrAgencyWoDestRcvNpod the airPrAgencyWoDestRcvNpod to set
	 */
	public void setAirPrAgencyWoDestRcvNpod(BigDecimal airPrAgencyWoDestRcvNpod) {
		this.airPrAgencyWoDestRcvNpod = airPrAgencyWoDestRcvNpod;
	}

	
	/**
	 * @return  the airPrOtWoDestRcvPod
	 */
	public BigDecimal getAirPrOtWoDestRcvPod() {
		return airPrOtWoDestRcvPod;
	}

	
	/**
	 * @param airPrOtWoDestRcvPod the airPrOtWoDestRcvPod to set
	 */
	public void setAirPrOtWoDestRcvPod(BigDecimal airPrOtWoDestRcvPod) {
		this.airPrOtWoDestRcvPod = airPrOtWoDestRcvPod;
	}

	
	/**
	 * @return  the airPrOthWoDestRcvNpod
	 */
	public BigDecimal getAirPrOthWoDestRcvNpod() {
		return airPrOthWoDestRcvNpod;
	}

	
	/**
	 * @param airPrOthWoDestRcvNpod the airPrOthWoDestRcvNpod to set
	 */
	public void setAirPrOthWoDestRcvNpod(BigDecimal airPrOthWoDestRcvNpod) {
		this.airPrOthWoDestRcvNpod = airPrOthWoDestRcvNpod;
	}

	
	/**
	 * @return  the airPrOthWoOthRcv
	 */
	public BigDecimal getAirPrOthWoOthRcv() {
		return airPrOthWoOthRcv;
	}

	
	/**
	 * @param airPrOthWoOthRcv the airPrOthWoOthRcv to set
	 */
	public void setAirPrOthWoOthRcv(BigDecimal airPrOthWoOthRcv) {
		this.airPrOthWoOthRcv = airPrOthWoOthRcv;
	}

	
	/**
	 * @return  the airCodChPod
	 */
	public BigDecimal getAirCodChPod() {
		return airCodChPod;
	}

	
	/**
	 * @param airCodChPod the airCodChPod to set
	 */
	public void setAirCodChPod(BigDecimal airCodChPod) {
		this.airCodChPod = airCodChPod;
	}

	
	/**
	 * @return  the airCodCdPod
	 */
	public BigDecimal getAirCodCdPod() {
		return airCodCdPod;
	}

	
	/**
	 * @param airCodCdPod the airCodCdPod to set
	 */
	public void setAirCodCdPod(BigDecimal airCodCdPod) {
		this.airCodCdPod = airCodCdPod;
	}

	
	/**
	 * @return  the airCodPodWoCod
	 */
	public BigDecimal getAirCodPodWoCod() {
		return airCodPodWoCod;
	}

	
	/**
	 * @param airCodPodWoCod the airCodPodWoCod to set
	 */
	public void setAirCodPodWoCod(BigDecimal airCodPodWoCod) {
		this.airCodPodWoCod = airCodPodWoCod;
	}

	
	/**
	 * @return  the airCodNpodWoCod
	 */
	public BigDecimal getAirCodNpodWoCod() {
		return airCodNpodWoCod;
	}

	
	/**
	 * @param airCodNpodWoCod the airCodNpodWoCod to set
	 */
	public void setAirCodNpodWoCod(BigDecimal airCodNpodWoCod) {
		this.airCodNpodWoCod = airCodNpodWoCod;
	}

	
	/**
	 * @return  the airCodChNpod
	 */
	public BigDecimal getAirCodChNpod() {
		return airCodChNpod;
	}

	
	/**
	 * @param airCodChNpod the airCodChNpod to set
	 */
	public void setAirCodChNpod(BigDecimal airCodChNpod) {
		this.airCodChNpod = airCodChNpod;
	}

	
	/**
	 * @return  the airCodCdNpod
	 */
	public BigDecimal getAirCodCdNpod() {
		return airCodCdNpod;
	}

	
	/**
	 * @param airCodCdNpod the airCodCdNpod to set
	 */
	public void setAirCodCdNpod(BigDecimal airCodCdNpod) {
		this.airCodCdNpod = airCodCdNpod;
	}

	
	/**
	 * @return  the airCodWoAgencyPayPod
	 */
	public BigDecimal getAirCodWoAgencyPayPod() {
		return airCodWoAgencyPayPod;
	}

	
	/**
	 * @param airCodWoAgencyPayPod the airCodWoAgencyPayPod to set
	 */
	public void setAirCodWoAgencyPayPod(BigDecimal airCodWoAgencyPayPod) {
		this.airCodWoAgencyPayPod = airCodWoAgencyPayPod;
	}

	
	/**
	 * @return  the airCodWoOthPayCod
	 */
	public BigDecimal getAirCodWoOthPayCod() {
		return airCodWoOthPayCod;
	}

	
	/**
	 * @param airCodWoOthPayCod the airCodWoOthPayCod to set
	 */
	public void setAirCodWoOthPayCod(BigDecimal airCodWoOthPayCod) {
		this.airCodWoOthPayCod = airCodWoOthPayCod;
	}

	
	/**
	 * @return  the airCodWoAgencyPayNpod
	 */
	public BigDecimal getAirCodWoAgencyPayNpod() {
		return airCodWoAgencyPayNpod;
	}

	
	/**
	 * @param airCodWoAgencyPayNpod the airCodWoAgencyPayNpod to set
	 */
	public void setAirCodWoAgencyPayNpod(BigDecimal airCodWoAgencyPayNpod) {
		this.airCodWoAgencyPayNpod = airCodWoAgencyPayNpod;
	}

	
	/**
	 * @return  the airCodWoOthNpod
	 */
	public BigDecimal getAirCodWoOthNpod() {
		return airCodWoOthNpod;
	}

	
	/**
	 * @param airCodWoOthNpod the airCodWoOthNpod to set
	 */
	public void setAirCodWoOthNpod(BigDecimal airCodWoOthNpod) {
		this.airCodWoOthNpod = airCodWoOthNpod;
	}

	
	/**
	 * @return  the aptCom
	 */
	public BigDecimal getAptCom() {
		return aptCom;
	}

	
	/**
	 * @param aptCom the aptCom to set
	 */
	public void setAptCom(BigDecimal aptCom) {
		this.aptCom = aptCom;
	}

	
	/**
	 * @return  the aptWoComPay
	 */
	public BigDecimal getAptWoComPay() {
		return aptWoComPay;
	}

	
	/**
	 * @param aptWoComPay the aptWoComPay to set
	 */
	public void setAptWoComPay(BigDecimal aptWoComPay) {
		this.aptWoComPay = aptWoComPay;
	}

	
	/**
	 * @return  the aptWoOthPay
	 */
	public BigDecimal getAptWoOthPay() {
		return aptWoOthPay;
	}

	
	/**
	 * @param aptWoOthPay the aptWoOthPay to set
	 */
	public void setAptWoOthPay(BigDecimal aptWoOthPay) {
		this.aptWoOthPay = aptWoOthPay;
	}

	
	/**
	 * @return  the bdrWoOthRcv
	 */
	public BigDecimal getBdrWoOthRcv() {
		return bdrWoOthRcv;
	}

	
	/**
	 * @param bdrWoOthRcv the bdrWoOthRcv to set
	 */
	public void setBdrWoOthRcv(BigDecimal bdrWoOthRcv) {
		this.bdrWoOthRcv = bdrWoOthRcv;
	}
    
    
}
