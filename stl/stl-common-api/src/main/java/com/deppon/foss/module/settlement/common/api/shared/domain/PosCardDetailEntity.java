package com.deppon.foss.module.settlement.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * POS刷卡明细
 * @ClassName: PosCardDetailEntity
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-4-6 下午4:00:44
 */
public class PosCardDetailEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 交易流水号
	 */
	private String tradeSerialNo;

	/**
	 * 单据类型
	 */
	private String invoiceType;

	/**
	 * 单据号
	 */
	private String invoiceNo;

	/**
	 * 总金额
	 */
	private BigDecimal amount;

	/**
	 * 已使用流水号金额
	 */
	private BigDecimal occupateAmount;

	/**
	 * 单据未核销金额
	 */
	private BigDecimal unVerifyAmount;

	/**
	 * 创建人编码
	 */
	private String createUserCode;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 版本
	 */
	private String version;
	
	/**
	 * 单据修改时增加的金额
	 */
	private BigDecimal addAmount;

	/**
	 * 是否已删除
	 */
	private String isDelete;
	
	/**
	 * 标识字段
	 */
	private String isflag;
	
	/**
	 * 查询字段
	 */
	private String fields;
	
	/************* getter--setter *************/

	public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getOccupateAmount() {
		return occupateAmount;
	}

	public void setOccupateAmount(BigDecimal occupateAmount) {
		this.occupateAmount = occupateAmount;
	}

	public BigDecimal getUnVerifyAmount() {
		return unVerifyAmount;
	}

	public void setUnVerifyAmount(BigDecimal unVerifyAmount) {
		this.unVerifyAmount = unVerifyAmount;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 获取 单据修改时增加的金额
	 * 
	 * @return addAmount 单据修改时增加的金额
	 */
	public BigDecimal getAddAmount() {
		return addAmount;
	}

	/**
	 * 设置 单据修改时增加的金额
	 * 
	 * @param addAmount
	 *            单据修改时增加的金额
	 */
	public void setAddAmount(BigDecimal addAmount) {
		this.addAmount = addAmount;
	}

	/**
	 * 获取 是否已删除
	 * 
	 * @return isDelete 是否已删除
	 */
	public String getIsDelete() {
		return isDelete;
	}

	/**
	 * 设置 是否已删除
	 * 
	 * @param isDelete
	 *            是否已删除
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public void setIsflag(String isflag) {
		this.isflag = isflag;
	}

	public String getIsflag() {
		return isflag;
	}
	
	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

}
