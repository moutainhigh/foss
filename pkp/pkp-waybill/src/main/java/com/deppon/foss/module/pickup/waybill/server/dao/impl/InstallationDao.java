package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IinstallationDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 安装费明细
 * @author MaBinliang
 *
 */
public class InstallationDao extends iBatis3DaoImpl implements IinstallationDao {
	private static final String NAMESPACE = "foss.pkp.InstallationEntityMapper.";

	@Override
	public int insertInstallationEntity(InstallationEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", entity);
	}

	@Override
	public int deleteInstallationByWaybillNo(String waybillNo) {
		InstallationEntity entity = new InstallationEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setWaybillNo(waybillNo);
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNo", entity);
	}
}
