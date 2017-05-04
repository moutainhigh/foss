package com.deppon.esb.pojo.domain.crm2foss;

import java.util.List;

/**
 * 
 * 客户圈同步接口请求实体
 * @author 308861 
 * @date 2016-12-21 上午10:38:27
 * @since
 * @version
 */
public class CustCircleRelationRequest{
	private String transactionCode;

	private List<CustomerCircleCrmEntity> CustCircleRelationList;

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public List<CustomerCircleCrmEntity> getCustCircleRelationList() {
		return CustCircleRelationList;
	}

	public void setCustCircleRelationList(
			List<CustomerCircleCrmEntity> custCircleRelationList) {
		CustCircleRelationList = custCircleRelationList;
	}
}
