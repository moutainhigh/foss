package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;

public class LclDeliveryToCashManagementUnloadingVo implements Serializable{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -2656996588275811044L;
	private LclDeliveryToCashManagementUnloadingEntity lclDeliveryToCashManagementUnloadingEntity;
    private List<LclDeliveryToCashManagementUnloadingEntity>  lclDeliveryToCashManagementUnloadingList;
	public LclDeliveryToCashManagementUnloadingEntity getLclDeliveryToCashManagementUnloadingEntity() {
		return lclDeliveryToCashManagementUnloadingEntity;
	}
	public void setLclDeliveryToCashManagementUnloadingEntity(
			LclDeliveryToCashManagementUnloadingEntity lclDeliveryToCashManagementUnloadingEntity) {
		this.lclDeliveryToCashManagementUnloadingEntity = lclDeliveryToCashManagementUnloadingEntity;
	}
	public List<LclDeliveryToCashManagementUnloadingEntity> getLclDeliveryToCashManagementUnloadingList() {
		return lclDeliveryToCashManagementUnloadingList;
	}
	public void setLclDeliveryToCashManagementUnloadingList(
			List<LclDeliveryToCashManagementUnloadingEntity> lclDeliveryToCashManagementUnloadingList) {
		this.lclDeliveryToCashManagementUnloadingList = lclDeliveryToCashManagementUnloadingList;
	}
    
}
