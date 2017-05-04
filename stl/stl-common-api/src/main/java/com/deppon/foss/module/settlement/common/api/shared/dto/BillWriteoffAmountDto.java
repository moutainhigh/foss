package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 应收单核销用的Dto
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 下午8:41:00
 * @since
 * @version
 */
public class BillWriteoffAmountDto {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 核销金额
	 */
	private BigDecimal writeoffAmount;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return writeoffAmount
	 */
	public BigDecimal getWriteoffAmount() {
		return writeoffAmount;
	}

	/**
	 * @param writeoffAmount
	 */
	public void setWriteoffAmount(BigDecimal writeoffAmount) {
		this.writeoffAmount = writeoffAmount;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	
	/**
	 * @return  the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	
	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

}
