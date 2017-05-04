/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.math.BigDecimal;

/**
 * 制单费用dto 
 * @author 099197-foss-zhoudejun
 * @date 2013-3-14 下午3:19:19
 */
public class AwbChargeDto {

	private String awbPrefix;
	private String awbNo;
	private String awbPostfix;
	private String awbType;
	private String accountingInfo;
	private String accountingRule;
	private String currencyName;
	private String chgsCode;
	private Double discount;
	private String wt;
	private String other;
	private Double dvfCarrier;
	private Double dvfCustomer;
	private Double insurance;
	private Double cWeight;
	private Double rateCharge;
	private Double weightCharge;
	private Double valCharge;
	private Double insuranceRate;
	private Double insuranceFee;
	private Double groundRate;
	private Double groundFee;
	private Double oilRate;
	private Double oilFee;
	private Double chargeDueAgent;
	private Double chargeDueCarrier;
	private Double prepaidTotal;
	private Double collectTotal;
	private Double tax;
	private Double currencyRate;
	private Double actualCWeight;
	private Double actualRateCharge;
	private Double actualWeightCharge;
	private String currencyNameDestination;
	private Double currencyRateDestination;
	private Double ccChargesInDestinationCurrency;
	private Double chargesAtDestination;
	private Double totalCollectCharges;
	private String rateType;
	private String rateCode;
	private String rateName;
	private String rateFlightNo;
	private String rateFlightDate;
	private String spaceClass;
	private BigDecimal spaceClassDiscount;
	private String bookGrade;
	private String subSpaceClass;
	private String snapshotTime;
	private String executedRateClass;
	public String getAwbPrefix() {
		return awbPrefix;
	}
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	public String getAwbNo() {
		return awbNo;
	}
	public void setAwbNo(String awbNo) {
		this.awbNo = awbNo;
	}
	public String getAwbPostfix() {
		return awbPostfix;
	}
	public void setAwbPostfix(String awbPostfix) {
		this.awbPostfix = awbPostfix;
	}
	public String getAwbType() {
		return awbType;
	}
	public void setAwbType(String awbType) {
		this.awbType = awbType;
	}
	public String getAccountingInfo() {
		return accountingInfo;
	}
	public void setAccountingInfo(String accountingInfo) {
		this.accountingInfo = accountingInfo;
	}
	public String getAccountingRule() {
		return accountingRule;
	}
	public void setAccountingRule(String accountingRule) {
		this.accountingRule = accountingRule;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getChgsCode() {
		return chgsCode;
	}
	public void setChgsCode(String chgsCode) {
		this.chgsCode = chgsCode;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getWt() {
		return wt;
	}
	public void setWt(String wt) {
		this.wt = wt;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public Double getDvfCarrier() {
		return dvfCarrier;
	}
	public void setDvfCarrier(Double dvfCarrier) {
		this.dvfCarrier = dvfCarrier;
	}
	public Double getDvfCustomer() {
		return dvfCustomer;
	}
	public void setDvfCustomer(Double dvfCustomer) {
		this.dvfCustomer = dvfCustomer;
	}
	public Double getInsurance() {
		return insurance;
	}
	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}
	public Double getcWeight() {
		return cWeight;
	}
	public void setcWeight(Double cWeight) {
		this.cWeight = cWeight;
	}
	public Double getRateCharge() {
		return rateCharge;
	}
	public void setRateCharge(Double rateCharge) {
		this.rateCharge = rateCharge;
	}
	public Double getWeightCharge() {
		return weightCharge;
	}
	public void setWeightCharge(Double weightCharge) {
		this.weightCharge = weightCharge;
	}
	public Double getValCharge() {
		return valCharge;
	}
	public void setValCharge(Double valCharge) {
		this.valCharge = valCharge;
	}
	public Double getInsuranceRate() {
		return insuranceRate;
	}
	public void setInsuranceRate(Double insuranceRate) {
		this.insuranceRate = insuranceRate;
	}
	public Double getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(Double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public Double getGroundRate() {
		return groundRate;
	}
	public void setGroundRate(Double groundRate) {
		this.groundRate = groundRate;
	}
	public Double getGroundFee() {
		return groundFee;
	}
	public void setGroundFee(Double groundFee) {
		this.groundFee = groundFee;
	}
	public Double getOilRate() {
		return oilRate;
	}
	public void setOilRate(Double oilRate) {
		this.oilRate = oilRate;
	}
	public Double getOilFee() {
		return oilFee;
	}
	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}
	public Double getChargeDueAgent() {
		return chargeDueAgent;
	}
	public void setChargeDueAgent(Double chargeDueAgent) {
		this.chargeDueAgent = chargeDueAgent;
	}
	public Double getChargeDueCarrier() {
		return chargeDueCarrier;
	}
	public void setChargeDueCarrier(Double chargeDueCarrier) {
		this.chargeDueCarrier = chargeDueCarrier;
	}
	public Double getPrepaidTotal() {
		return prepaidTotal;
	}
	public void setPrepaidTotal(Double prepaidTotal) {
		this.prepaidTotal = prepaidTotal;
	}
	public Double getCollectTotal() {
		return collectTotal;
	}
	public void setCollectTotal(Double collectTotal) {
		this.collectTotal = collectTotal;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public Double getActualCWeight() {
		return actualCWeight;
	}
	public void setActualCWeight(Double actualCWeight) {
		this.actualCWeight = actualCWeight;
	}
	public Double getActualRateCharge() {
		return actualRateCharge;
	}
	public void setActualRateCharge(Double actualRateCharge) {
		this.actualRateCharge = actualRateCharge;
	}
	public Double getActualWeightCharge() {
		return actualWeightCharge;
	}
	public void setActualWeightCharge(Double actualWeightCharge) {
		this.actualWeightCharge = actualWeightCharge;
	}
	public String getCurrencyNameDestination() {
		return currencyNameDestination;
	}
	public void setCurrencyNameDestination(String currencyNameDestination) {
		this.currencyNameDestination = currencyNameDestination;
	}
	public Double getCurrencyRateDestination() {
		return currencyRateDestination;
	}
	public void setCurrencyRateDestination(Double currencyRateDestination) {
		this.currencyRateDestination = currencyRateDestination;
	}
	public Double getCcChargesInDestinationCurrency() {
		return ccChargesInDestinationCurrency;
	}
	public void setCcChargesInDestinationCurrency(
			Double ccChargesInDestinationCurrency) {
		this.ccChargesInDestinationCurrency = ccChargesInDestinationCurrency;
	}
	public Double getChargesAtDestination() {
		return chargesAtDestination;
	}
	public void setChargesAtDestination(Double chargesAtDestination) {
		this.chargesAtDestination = chargesAtDestination;
	}
	public Double getTotalCollectCharges() {
		return totalCollectCharges;
	}
	public void setTotalCollectCharges(Double totalCollectCharges) {
		this.totalCollectCharges = totalCollectCharges;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	public String getRateFlightNo() {
		return rateFlightNo;
	}
	public void setRateFlightNo(String rateFlightNo) {
		this.rateFlightNo = rateFlightNo;
	}
	public String getRateFlightDate() {
		return rateFlightDate;
	}
	public void setRateFlightDate(String rateFlightDate) {
		this.rateFlightDate = rateFlightDate;
	}
	public String getSpaceClass() {
		return spaceClass;
	}
	public void setSpaceClass(String spaceClass) {
		this.spaceClass = spaceClass;
	}
	public BigDecimal getSpaceClassDiscount() {
		return spaceClassDiscount;
	}
	public void setSpaceClassDiscount(BigDecimal spaceClassDiscount) {
		this.spaceClassDiscount = spaceClassDiscount;
	}
	public String getBookGrade() {
		return bookGrade;
	}
	public void setBookGrade(String bookGrade) {
		this.bookGrade = bookGrade;
	}
	public String getSubSpaceClass() {
		return subSpaceClass;
	}
	public void setSubSpaceClass(String subSpaceClass) {
		this.subSpaceClass = subSpaceClass;
	}
	public String getSnapshotTime() {
		return snapshotTime;
	}
	public void setSnapshotTime(String snapshotTime) {
		this.snapshotTime = snapshotTime;
	}
	public String getExecutedRateClass() {
		return executedRateClass;
	}
	public void setExecutedRateClass(String executedRateClass) {
		this.executedRateClass = executedRateClass;
	}
	
}