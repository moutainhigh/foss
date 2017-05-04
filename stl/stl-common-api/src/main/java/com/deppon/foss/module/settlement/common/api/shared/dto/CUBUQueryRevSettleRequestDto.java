package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * CUBC申请反结清请求参数
 * @date 2016-11-02
 * @author 378375
 *
 */
@SuppressWarnings("serial")
public class CUBUQueryRevSettleRequestDto implements Serializable{
	/**
	 * 序列化版本号
	 */
	private static final long SerialVersionUID = 378375008L;
	
	/**
	 *来源单号 
	 */
	private String sourseNo;

	public String getSourseNo() {
		return sourseNo;
	}

	public void setSourseNo(String sourseNo) {
		this.sourseNo = sourseNo;
	}
	
	
}
