package com.deppon.pda.bdm.module.foss.upgrade.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.shared.domain.PdaDeviceEntity;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.IDeviceDao;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DeviceParamEntity;

/**
 * 
 * TODO(操作设备表DAO实现类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:chengang,date:2013-5-21 上午9:57:40,content:TODO
 * </p>
 * 
 * @author chengang
 * @date 2013-5-21 上午9:57:40
 * @since
 * @version
 */

public class DeviceDao extends SqlSessionDaoSupport implements IDeviceDao {

	@Override
	public PdaDeviceEntity getDeviceInfo(String pdaCode) {
		// TODO Auto-generated method stub
		return (PdaDeviceEntity) getSqlSession().selectOne(
				getClass().getName() + ".getDeviceInfo", pdaCode);
	}

	@Override
	public void updDeviceInfo(DeviceParamEntity parameter) {
		getSqlSession().update(getClass().getName() + ".updDeviceInfo",
				parameter);
	}

}
