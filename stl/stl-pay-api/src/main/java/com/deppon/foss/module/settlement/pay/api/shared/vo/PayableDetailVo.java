package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;

public class PayableDetailVo implements Serializable {

	private static final long serialVersionUID = -4071810926902743106L;

	/**
	 * 应付单号
	 */
	private String payableNo;
	
	/**
	 * 应付单明细
	 */
	private List<BillPayableDEntity> payableDEntityList;

	public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	public List<BillPayableDEntity> getPayableDEntityList() {
		return payableDEntityList;
	}

	public void setPayableDEntityList(List<BillPayableDEntity> payableDEntityList) {
		this.payableDEntityList = payableDEntityList;
	}
	
}
