package com.deppon.pda.bdm.module.ocb.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.ocb.server.dao.IDeviceRegistDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.DeviceRegistBean;

public class DeviceRegistDao extends SqlSessionDaoSupport implements IDeviceRegistDao{

	@Override
	public void addDevice(DeviceRegistBean device) {
		getSqlSession().insert(getClass().getName()+".addDevice", device);
	}

	//检查设备号是否存在
	@Override
	public boolean checkDeviceIsExist(String dvcCode) {
		@SuppressWarnings("unchecked")
		List<String> result = getSqlSession().selectList(getClass().getName()+".checkDeviceIsExist", dvcCode);
		return (result!=null && !result.isEmpty());
	}
}
