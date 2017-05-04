package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.math.BigDecimal;

/**
 * 包装其他应收应付查询 dto <p style="display:none">modifyRecord</p> <p style="display:none">version:V1.0,author:105762,date:2014-5-16 下午3:09:10,content:TODO</p>
 * 
 * @author 105762
 * @date 2014-5-16 下午3:09:10
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayTfrDto {
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  开单部门code
	 */
	private String billOrgCode;
	/**
	 *  开单部门名称
	 */
	private String billOrgName;
	/**
	 *  包装部门code
	 */
	private String packageOrgCode;
	/**
	 *  包装部门名称
	 */
	private String packageOrgName;
	/**
	 *  应付金额
	 */
	private BigDecimal packagePayableMoney;

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

	/**
	  * @return  the billOrgCode
	 */
	public String getBillOrgCode() {
		return billOrgCode;
	}

	/**
	 * @param billOrgCode the billOrgCode to set
	 */
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}

	/**
	  * @return  the billOrgName
	 */
	public String getBillOrgName() {
		return billOrgName;
	}

	/**
	 * @param billOrgName the billOrgName to set
	 */
	public void setBillOrgName(String billOrgName) {
		this.billOrgName = billOrgName;
	}

	/**
	  * @return  the packageOrgCode
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	/**
	 * @param packageOrgCode the packageOrgCode to set
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	/**
	  * @return  the packageOrgName
	 */
	public String getPackageOrgName() {
		return packageOrgName;
	}

	/**
	 * @param packageOrgName the packageOrgName to set
	 */
	public void setPackageOrgName(String packageOrgName) {
		this.packageOrgName = packageOrgName;
	}

	/**
	  * @return  the packagePayableMoney
	 */
	public BigDecimal getPackagePayableMoney() {
		return packagePayableMoney;
	}

	/**
	 * @param packagePayableMoney the packagePayableMoney to set
	 */
	public void setPackagePayableMoney(BigDecimal packagePayableMoney) {
		this.packagePayableMoney = packagePayableMoney;
	}

}