package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class GetDeryTaskResEntity extends BaseEntity {

	private static final long serialVersionUID = -5977125901891181739L;

	// 下拉明细
	private List<DeryCrgDetailEntity> deryCrgDetails;

	// 派送单号
	private String deryCode;

	public List<DeryCrgDetailEntity> getDeryCrgDetails() {
		return deryCrgDetails;
	}

	public void setDeryCrgDetails(List<DeryCrgDetailEntity> deryCrgDetails) {
		this.deryCrgDetails = deryCrgDetails;
	}

	public String getDeryCode() {
		return deryCode;
	}

	public void setDeryCode(String deryCode) {
		this.deryCode = deryCode;
	}

}
