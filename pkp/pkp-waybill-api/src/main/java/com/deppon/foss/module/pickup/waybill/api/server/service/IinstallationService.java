package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;

public interface IinstallationService extends IService {
	/**
	 * 
	 * <p>
	 * 新增运单附加信息<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param ActualFreightEntity
	 *            void
	 */
	int insertInstallation(InstallationEntity installationEntity);

	int deleteInstallationByWaybillNo(String waybillNo);
}
