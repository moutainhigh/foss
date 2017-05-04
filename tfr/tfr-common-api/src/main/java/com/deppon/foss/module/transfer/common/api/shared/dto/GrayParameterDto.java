package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
 * 调用UCBC灰度工具类DOT，
 * @author 332209  ruilibao
 *
 */
public class GrayParameterDto implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 来源单据类型
	private String sourceBillType;
	
	// 来源单据
	private String[] sourceBillNos;

	/**
	 * @return the sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param sourceBillType the sourceBillType to set
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return the sourceBillNos
	 */
	public String[] getSourceBillNos() {
		return sourceBillNos;
	}

	/**
	 * @param sourceBillNos the sourceBillNos to set
	 */
	public void setSourceBillNos(String[] sourceBillNos) {
		this.sourceBillNos = sourceBillNos;
	}
}
