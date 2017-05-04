package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;

public class LclDeliveryToCashManagementDeliveryVo implements Serializable{

    
	/**
	 *  Serial Version UID
	 */
	private static final long serialVersionUID = -6859976619085104890L;
	private LclDeliveryToCashManagementDeliveryEntity  lclDeliveryToCashManagementDeliveryEntity;
	
	private List<LclDeliveryToCashManagementDeliveryEntity> lclDeliveryToCashManagementDeliveryList;

	public LclDeliveryToCashManagementDeliveryEntity getLclDeliveryToCashManagementDeliveryEntity() {
		return lclDeliveryToCashManagementDeliveryEntity;
	}

	public void setLclDeliveryToCashManagementDeliveryEntity(
			LclDeliveryToCashManagementDeliveryEntity lclDeliveryToCashManagementDeliveryEntity) {
		this.lclDeliveryToCashManagementDeliveryEntity = lclDeliveryToCashManagementDeliveryEntity;
	}

	public List<LclDeliveryToCashManagementDeliveryEntity> getLclDeliveryToCashManagementDeliveryList() {
		return lclDeliveryToCashManagementDeliveryList;
	}

	public void setLclDeliveryToCashManagementDeliveryList(
			List<LclDeliveryToCashManagementDeliveryEntity> lclDeliveryToCashManagementDeliveryList) {
		this.lclDeliveryToCashManagementDeliveryList = lclDeliveryToCashManagementDeliveryList;
	}
	
	
}
