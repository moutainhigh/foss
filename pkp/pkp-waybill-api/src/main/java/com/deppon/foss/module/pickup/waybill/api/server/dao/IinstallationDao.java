package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;

public interface IinstallationDao {

	public int insertInstallationEntity(InstallationEntity installationEntity);

	public int deleteInstallationByWaybillNo(String waybillNo);

}
