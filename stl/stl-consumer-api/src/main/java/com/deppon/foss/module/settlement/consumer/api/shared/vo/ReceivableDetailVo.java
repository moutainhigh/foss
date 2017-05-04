package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;

public class ReceivableDetailVo implements Serializable {

	private static final long serialVersionUID = -4776644067326469386L;

	/**
	 * 应收单号
	 */
	private String receivableNo;
	
	/**
	 * 应收单明细
	 */
	private List<BillReceivableDEntity> receivableDEntityList;

	public String getReceivableNo() {
		return receivableNo;
	}

	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	public List<BillReceivableDEntity> getReceivableDEntityList() {
		return receivableDEntityList;
	}

	public void setReceivableDEntityList(List<BillReceivableDEntity> receivableDEntityList) {
		this.receivableDEntityList = receivableDEntityList;
	}

}
