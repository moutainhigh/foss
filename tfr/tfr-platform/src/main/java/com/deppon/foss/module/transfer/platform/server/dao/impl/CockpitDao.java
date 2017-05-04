package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ICockpitDao;
import com.deppon.foss.module.transfer.platform.api.shared.vo.CockpitVo;

public class CockpitDao extends iBatis3DaoImpl implements ICockpitDao {
	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ICockpitDao.";

	/**
	 * @description 根据外场查询驾驶舱vo
	 * @param orgCode
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2015年9月2日 下午3:08:24
	 */
	@Override
	public CockpitVo queryCockpitByOrgCode(String orgCode) {
		String statement = NAMESPACE + "queryCockpitByOrgCode";
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgCode", orgCode);
		@SuppressWarnings("unchecked")
		List<CockpitVo> backList = super.getSqlSession().selectList(statement,
				map);
		if (backList != null && backList.size() > 0) {
			return backList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String onDutyNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "onDutyNums", map);
	}

	@Override
	public String absenteeNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "absenteeNums", map);
	}

	@Override
	public String tallyNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "tallyNums", map);
	}

	@Override
	public String eForkNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "eForkNums", map);
	}

	@Override
	public String stockWeight(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "stockWeight", map);
	}

	@Override
	public String waitUnloadWeight(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "waitUnloadWeight", map);
	}

	@Override
	public String lngDisOnTheWayNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "lngDisOnTheWayNums", map);
	}

	@Override
	public String shtDisOnTheWayNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "shtDisOnTheWayNums", map);
	}

	@Override
	public String lngDisArrivedNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "lngDisArrivedNums", map);
	}

	@Override
	public String shtDisArrivedNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "shtDisArrivedNums", map);
	}

	@Override
	public String loadUnloadProgressAbnormalNums(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "loadUnloadProgressAbnormalNums", map);
	}

	@Override
	public String platformUsageRate(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "platformUsageRate", map);
	}

	@Override
	public String dispatchStockVolume(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "dispatchStockVolume", map);
	}

	@Override
	public String sendBackPct(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "sendBackPct", map);
	}

	@Override
	public String stockSaturation(Map<String, Object> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "stockSaturation", map);
	}

}
