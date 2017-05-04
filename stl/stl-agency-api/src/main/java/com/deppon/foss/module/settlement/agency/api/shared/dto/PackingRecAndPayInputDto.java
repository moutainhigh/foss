package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.math.BigDecimal;

/**
 * 包装供应商奖罚录入界面 dto <p style="display:none">modifyRecord</p> <p style="display:none">version:V1.0,author:105762,date:2014-5-16 下午3:09:10,content:TODO</p>
 * 
 * @author 105762
 * @date 2014-5-16 下午3:09:10
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayInputDto {

	/**
	 * 客户(包装供货商)编码
	 */
	private String customerCode;

	/**
	 * 客户(包装供货商)名称
	 */
	private String customerName;

	/**
	 * 部门编码
	 */
	private String deptCode;

	/**
	 * 时间(年月) 示例 "2014-01"
	 */
	private String billTime;

	/**
	 * 破损率
	 */
	private Double damageRate;

	/**
	 * 包装供应商配置破损率
	 */
	private Double standardDamageRate;

	/**
	 * 包装其他应收应付最大金额
	 */
	private BigDecimal packingDamageMaxAmount;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 包装其它应收应付输入类别
	 */
	private String inputType;

	/**
	 * 包装其它应收应付输入类别
	 */
	private String waybillNo;

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
	  * @return  the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	  * @return  the billTime
	 */
	public String getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime the billTime to set
	 */
	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	/**
	  * @return  the damageRate
	 */
	public Double getDamageRate() {
		return damageRate;
	}

	/**
	 * @param damageRate the damageRate to set
	 */
	public void setDamageRate(Double damageRate) {
		this.damageRate = damageRate;
	}

	/**
	  * @return  the standardDamageRate
	 */
	public Double getStandardDamageRate() {
		return standardDamageRate;
	}

	/**
	 * @param standardDamageRate the standardDamageRate to set
	 */
	public void setStandardDamageRate(Double standardDamageRate) {
		this.standardDamageRate = standardDamageRate;
	}

	/**
	  * @return  the packingDamageMaxAmount
	 */
	public BigDecimal getPackingDamageMaxAmount() {
		return packingDamageMaxAmount;
	}

	/**
	 * @param packingDamageMaxAmount the packingDamageMaxAmount to set
	 */
	public void setPackingDamageMaxAmount(BigDecimal packingDamageMaxAmount) {
		this.packingDamageMaxAmount = packingDamageMaxAmount;
	}

	/**
	  * @return  the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	  * @return  the inputType
	 */
	public String getInputType() {
		return inputType;
	}

	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	/**
	  * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}
