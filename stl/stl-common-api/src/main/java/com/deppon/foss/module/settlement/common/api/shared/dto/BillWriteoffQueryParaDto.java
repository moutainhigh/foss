package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 核销操作Dto，用于Service和Dao之间的参数传递
 * 
 * @author foss-wangxuemin
 * @date 2012-10-19 下午4:16:00
 */
public class BillWriteoffQueryParaDto implements Serializable {

	private static final long serialVersionUID = 6871999852994208721L;

	/**
	 * 开始来源单号
	 */
	private String beginNo;

	/**
	 * 目的来源单号
	 */
	private String endNo;

	/**
	 * 核销方式
	 */
	private String createType;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 核销类型
	 */
	private String writeoffType;

	/**
	 * @return beginNo
	 */
	public String getBeginNo() {
		return beginNo;
	}

	/**
	 * @param beginNo
	 */
	public void setBeginNo(String beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * @return endNo
	 */
	public String getEndNo() {
		return endNo;
	}

	/**
	 * @param endNo
	 */
	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return createType
	 */
	public String getCreateType() {
		return createType;
	}

	/**
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}

}
