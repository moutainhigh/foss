package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;

/**
 * CUBC查询反签收申请的请求实体
 * @author 353654
 *
 */
public class CUBCQueryRevSignRequestDto implements Serializable{
	
	//序列化版本
	private static final long serialVersionUID = 1213213213213123L;
		
	//来源编号
	private String sourceNo;

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}
}
