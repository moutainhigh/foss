package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
/**
 * @218392 张永雪
 * @author 218392
 * @date 2016-06-21 23:08:10
 * 应付单和付款单付款时候，校验VTS运单号和来源单号：
 * 1.合同编码是否存在；2.单号+合同编码一一对应关系；3.运单状态：作废，不允许发起付款。
 */
public class RequestPayableCheckEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 单号
	 */
	private String waybillNo;
	
	/**
	 * 合同编码
	 */
	private String contractCode;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	
}
