package com.deppon.esb.inteface.domain.fins;

import java.io.Serializable;
import java.util.List;

public class PartnerAccountInfoRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 批量合伙人账户基本数据信息
	 */
	protected List<PartnerAccountInfo> partnerAccountInfoList;

	public List<PartnerAccountInfo> getPartnerAccountInfoList() {
		return partnerAccountInfoList;
	}

	public void setPartnerAccountInfoList(
			List<PartnerAccountInfo> partnerAccountInfoList) {
		this.partnerAccountInfoList = partnerAccountInfoList;
	}
}
