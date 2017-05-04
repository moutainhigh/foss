package com.deppon.foss.module.settlement.agency.api.shared.domain;

import java.io.Serializable;

/**
 * 
 * @author 325369  chenzhuang
 * @date 2016-07-05 19:20:35
 * 客户端请求悟空系统request实体
 */
public class RequestLdpExternalBill implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 快递代理公司编码
	 */
	private String agentCompanyCode;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	
}
