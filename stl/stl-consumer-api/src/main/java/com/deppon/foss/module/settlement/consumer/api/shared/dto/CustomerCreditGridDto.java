package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;

/**
 * 客户收支平衡表基本信息
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-16 下午6:06:42
 */
public class CustomerCreditGridDto {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 客户编码
	 */
	private String code;

	/**
	 * 最大额度
	 */
	private BigDecimal creditAmount;

	/**
	 * 客户名称
	 */
	private String name;

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
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return creditAmount
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * @param creditAmount
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
