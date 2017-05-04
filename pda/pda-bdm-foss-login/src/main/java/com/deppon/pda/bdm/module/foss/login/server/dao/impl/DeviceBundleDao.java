package com.deppon.pda.bdm.module.foss.login.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.login.server.dao.IDeviceBundleDao;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DeviceBundleEntity;

public class DeviceBundleDao extends SqlSessionDaoSupport implements
		IDeviceBundleDao {

	/*
	 * 绑定
	 */
	public void boundDvcTruck(DeviceBundleEntity deviceBundleEntity) {
		getSqlSession().insert(getClass().getName() + ".boundDvcTruck",
				deviceBundleEntity);
	}

	/*
	 * 根据部门号查询顶级车队名称
	 */
	public String findtopFleet(String orgCode) {
		return (String) getSqlSession().selectOne(
				getClass().getName() + ".findtopFleet", orgCode);
	}

	/*
	 * 根据设备号查询外请车设备信息
	 */
	public List<DeviceBundleEntity> queryDeviceBundleMsg(String devCode,
			String truckCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("devCode", devCode);
		map.put("truckCode", truckCode);
		return getSqlSession().selectList(
				getClass().getName() + ".queryDeviceBundleMsg", map);
	}
}
