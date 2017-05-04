package com.deppon.foss.module.transfer.oa.server.domain;

import java.io.Serializable;
/**
 * @title: LostCodeEntityResponse
 * @description：上报流水号给QMS实体
 * @author： ZHANGDANDAN
 * @date： 2016-12-16 上午9:31:02
 */
public class LostCodeEntityResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	//丢失流水号
	private String flowCode;

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
}
