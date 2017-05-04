package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.util.List;

public class WaybillRfcRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private List<String> waybillNos;
	/**
	 * 标识  Y：为已申请或已同意  N：为拒绝或没有
	 */
	private String resultMark;

	public List<String> getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	public String getResultMark() {
		return resultMark;
	}

	public void setResultMark(String resultMark) {
		this.resultMark = resultMark;
	}
	
	
}
