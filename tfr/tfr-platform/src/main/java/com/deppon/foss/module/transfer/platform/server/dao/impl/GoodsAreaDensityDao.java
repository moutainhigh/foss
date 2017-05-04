package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IGoodsAreaDensityDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensity4SumDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensityQcDto;

public class GoodsAreaDensityDao extends iBatis3DaoImpl implements
		IGoodsAreaDensityDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.IGoodsAreaDensityDao.";

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensity4SumDto> selectGoodsAreaDensity4Sum(
			GoodsAreaDensityQcDto dto) {

		return super.getSqlSession().selectList(
				NAMESPACE + "selectGoodsAreaDensity4Sum", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensity4SumDto> selectGoodsAreaDensity4Sum(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return super.getSqlSession().selectList(
				NAMESPACE + "selectGoodsAreaDensity4Sum", dto, rowBounds);
	}

	@Override
	public Long selectCntGoodsAreaDensity4Sum(GoodsAreaDensityQcDto dto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectCntGoodsAreaDensity4Sum", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectGoodsAreaDensity4Timely(
			GoodsAreaDensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "selectGoodsAreaDensity4Timely", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectGoodsAreaDensity4Timely(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return super.getSqlSession().selectList(
				NAMESPACE + "selectGoodsAreaDensity4Timely", dto, rowBounds);
	}

	@Override
	public Long selectCntGoodsAreaDensity4Timely(GoodsAreaDensityQcDto dto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectCntGoodsAreaDensity4Timely", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto) {

		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrGoodsAreaAvgDensity4Daily", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrGoodsAreaAvgDensity4Daily", dto,
				rowBounds);
	}

	@Override
	public Long selectCntTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectCntTfrCtrGoodsAreaAvgDensity4Daily", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Daily(
			GoodsAreaDensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrAvgDensity4Daily", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Daily(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrAvgDensity4Daily", dto, rowBounds);
	}

	@Override
	public Long selectCntTfrCtrAvgDensity4Daily(GoodsAreaDensityQcDto dto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectCntTfrCtrAvgDensity4Daily", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrGoodsAreaAvgDensity4Monthly", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrGoodsAreaAvgDensity4Monthly", dto,
				rowBounds);
	}

	@Override
	public Long selectCntTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectCntTfrCtrGoodsAreaAvgDensity4Monthly", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto) {
		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrAvgDensity4Monthly", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrAvgDensity4Monthly", dto, rowBounds);
	}

	@Override
	public Long selectCntTfrCtrAvgDensity4Monthly(GoodsAreaDensityQcDto dto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectCntTfrCtrAvgDensity4Monthly", dto);
	}

	@Override
	public void generateGoodsAreaDensity(Date date) {
		super.getSqlSession().insert(NAMESPACE + "generateGoodsAreaDensity", date);
	}
}
