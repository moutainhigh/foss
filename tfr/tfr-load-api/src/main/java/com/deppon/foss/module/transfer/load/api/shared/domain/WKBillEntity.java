package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;

public class WKBillEntity {

	private List<LimittitionTermEntity> limititionTermEntitys;

	private WKTfrBillEntity loadHandoverBillEntity;


	public WKTfrBillEntity getLoadHandoverBillEntity() {
		return loadHandoverBillEntity;
	}

	public void setLoadHandoverBillEntity(WKTfrBillEntity loadHandoverBillEntity) {
		this.loadHandoverBillEntity = loadHandoverBillEntity;
	}

	public List<LimittitionTermEntity> getLimititionTermEntitys() {
		return limititionTermEntitys;
	}

	public void setLimititionTermEntitys(List<LimittitionTermEntity> limititionTermEntitys) {
		this.limititionTermEntitys = limititionTermEntitys;
	}
	
	

}
