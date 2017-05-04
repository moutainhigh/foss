package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

import com.deppon.pda.bdm.module.foss.accept.shared.domain.DispatchOrderEntity;

public class DispatchOrderEntityList implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 接货订单返回对象list
	 */
	private List<DispatchOrderEntity> acctResponseDetail;
	
	/**
	 * 错误消息
	 */
	private String errorMsg;

	/**
	 * 
	 * @return 接货订单返回对象list
	 */
	public List<DispatchOrderEntity> getAcctResponseDetail() {
		return acctResponseDetail;
	}

	/**
	 * 
	 * @param acctResponseDetail
	 */
	public void setAcctResponseDetail(List<DispatchOrderEntity> acctResponseDetail) {
		this.acctResponseDetail = acctResponseDetail;
	}

	/**
	 * 
	 * @return 错误消息
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * 
	 * @param errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
