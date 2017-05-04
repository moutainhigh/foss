package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.List;

public class WKLoadEntity {

	private WKSyncLoadTaskEntity loadTaskEntity;

	private List<LoaderParticipationEntity> loaderParticipationEntityList;

	public WKSyncLoadTaskEntity getLoadTaskEntity() {
		return loadTaskEntity;
	}

	public void setLoadTaskEntity(WKSyncLoadTaskEntity loadTaskEntity) {
		this.loadTaskEntity = loadTaskEntity;
	}

	public List<LoaderParticipationEntity> getLoaderParticipationEntityList() {
		return loaderParticipationEntityList;
	}

	public void setLoaderParticipationEntityList(List<LoaderParticipationEntity> loaderParticipationEntityList) {
		this.loaderParticipationEntityList = loaderParticipationEntityList;
	}

	@Override
	public String toString() {
		return "WKLoadEntity [loadTaskEntity=" + loadTaskEntity
				+ ", loaderParticipationEntityList="
				+ loaderParticipationEntityList + "]";
	}

}
