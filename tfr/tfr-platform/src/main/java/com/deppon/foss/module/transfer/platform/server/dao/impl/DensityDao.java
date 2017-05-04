package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IDensityDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DptAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrDensityPeakEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityTrendDto;

public class DensityDao extends iBatis3DaoImpl implements IDensityDao {

	private final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.IDensityDao.";

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findTfrCtrs(Integer threadCount,
			Integer threadNo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("threadCount", threadCount);
		map.put("threadNo", threadNo);
		return super.getSqlSession().selectList(NAMESPACE + "findTfrCtrs", map);
	}

	@Override
	public void generateDensityHour(Map<String, Object> map) {
		super.getSqlSession().selectOne(NAMESPACE + "generateDensityHour", map);
	}

	@Override
	public void generateDensityDay(Map<String, Object> map) {
		super.getSqlSession().selectOne(NAMESPACE + "generateDensityDay", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrDensityPeakEntity> findTfrCtrDensityPeak(DensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findTfrCtrDensityPeak", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForkAreaDensityEntity> findForkAreaDensity(DensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findForkAreaDensity", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DptAreaDensityEntity> findDptAreaDensity(DensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findDptAreaDensity", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrAreaDensityEntity> findTfrAreaDensity(DensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "findTfrAreaDensity", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DensityTrendDto> findTrendDay(DensityQcDto dto) {
		return super.getSqlSession()
				.selectList(NAMESPACE + "findTrendDay", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DensityTrendDto> findTrendMonth(DensityQcDto dto) {
		return super.getSqlSession().selectList(NAMESPACE + "findTrendMonth",
				dto);
	}
}
