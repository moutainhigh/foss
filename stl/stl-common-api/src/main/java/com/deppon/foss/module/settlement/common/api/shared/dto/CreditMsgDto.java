package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;


@SuppressWarnings("serial")
public class CreditMsgDto implements Serializable {
	
	/**
	 * 财务收支平衡消息表集合
	 */
	private List<CreditMsgEntity> list;
	
	/**
	 * 状态
	 */
	private String status;

	
	/**
	 * @return  the list
	 */
	public List<CreditMsgEntity> getList() {
		return list;
	}

	
	/**
	 * @param list the list to set
	 */
	public void setList(List<CreditMsgEntity> list) {
		this.list = list;
	}

	
	/**
	 * @return  the status
	 */
	public String getStatus() {
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
