package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITrayscanEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TrayscanEfficiencyEntity;

/**
 * 托盘绑定 dao
 * 
 * @author 200978 2015-2-2
 */
public class TrayscanEfficiencyDao extends iBatis3DaoImpl implements
		ITrayscanEfficiencyDao {

	private final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ITrayscanEfficiencyDao.";

	@Override
	public void generateTrayBindingEff(Date staDate, int staMonth) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staDate", staDate);
		map.put("staMonth", staMonth);
		getSqlSession().selectOne(NAMESPACE + "generateTrayBindingEff", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrayscanEfficiencyEntity> findTrayBindingEff(
			TrayscanEfficiencyEntity paramter) {
		return getSqlSession().selectList(NAMESPACE + "findTrayBindingEff",
				paramter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrayscanEfficiencyDetailEntity> findTrayBindingDetailEff(
			TrayscanEfficiencyDetailEntity paramter) {
		return getSqlSession().selectList(
				NAMESPACE + "findTrayBindingDetailEff", paramter);
	}

}
