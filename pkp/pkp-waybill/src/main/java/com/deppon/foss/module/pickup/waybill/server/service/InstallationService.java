package com.deppon.foss.module.pickup.waybill.server.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jfree.util.Log;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IinstallationDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IinstallationService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.util.UUIDUtils;

public class InstallationService implements IinstallationService {
	private IinstallationDao installationDao;

	public IinstallationDao getInstallationDao() {
		return installationDao;
	}
	
	public void setInstallationDao(IinstallationDao installationDao) {
		this.installationDao = installationDao;
	}
	/**
	 * 
	 * <p>
	 * 新增信息<br />
	 * </p>
	 * 
	 * @author foss-254615-mabinliang
	 * @version 0.1 2012-11-29
	 * @param ActualFreightEntity
	 *            void
	 */
	@Override
	public int insertInstallation(InstallationEntity installationEntity) {
			if (StringUtils.isEmpty(installationEntity.getUuid())) {
				installationEntity.setUuid(UUIDUtils.getUUID());
			}
			int insertNumber = installationDao.insertInstallationEntity(installationEntity);
			Log.warn("insert installationEntity record insertNum: " +  insertNumber );
			Log.warn("insert installationEntity record installationEntity: " + ToStringBuilder.reflectionToString(installationEntity)  );
			return insertNumber;
		}

	@Override
	public int deleteInstallationByWaybillNo(String waybillNo) {
		int deleteNumber = installationDao.deleteInstallationByWaybillNo(waybillNo);
		return deleteNumber;
	}
}
