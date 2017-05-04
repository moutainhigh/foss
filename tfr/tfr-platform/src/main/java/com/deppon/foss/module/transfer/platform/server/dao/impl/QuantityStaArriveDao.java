package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQuantityStaArriveDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.QuantityStaFcstEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaGoodsCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrPerLineDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrTotalDto;

public class QuantityStaArriveDao extends iBatis3DaoImpl implements
		IQuantityStaArriveDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.IQuantityStaArriveDao.";

	@Override
	public void insert16Day(QuantityStaGoodsCondDto dto) {
		super.getSqlSession().insert(NAMESPACE + "insert16Day", dto);
	}

	@Override
	public void deleteArrive(QuantityStaGoodsCondDto dto) {
		super.getSqlSession().delete(NAMESPACE + "deleteArrive", dto);
	}

	@Override
	public void proInsertActual(QuantityStaGoodsCondDto dto) {
		super.getSqlSession().selectOne(NAMESPACE + "proInsertActual", dto);
	}

	@Override
	public void delete2ndDayArrive(QuantityStaGoodsCondDto dto) {
		super.getSqlSession().delete(NAMESPACE + "delete2ndDayArrive", dto);
	}

	@Override
	public void delete2ndDayArriveFcst(QuantityStaGoodsCondDto dto) {
		super.getSqlSession().delete(NAMESPACE + "delete2ndDayArriveFcst", dto);
	}

	@Override
	public void proInsert2ndDayActual(QuantityStaGoodsCondDto dto) {
		super.getSqlSession().selectOne(NAMESPACE + "proInsert2ndDayActual",
				dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaTfrCtrTotalDto> selectTfrCtrTotal(
			Map<String, Object> map) {
		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrTotal", map);
	}

	@Override
	public QuantityStaTfrCtrTotalDto selectTfrCtrTotal16Day(
			QuantityStaTfrCtrCondDto dto) {
		return (QuantityStaTfrCtrTotalDto) super.getSqlSession().selectOne(
				NAMESPACE + "selectTfrCtrTotal16Day", dto);
	}

	@Override
	public Integer selectLastStaHh16Day(Map<String, Object> map) {
		return (Integer) super.getSqlSession().selectOne(
				NAMESPACE + "selectLastStaHh16Day", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaTfrCtrPerLineDto> selectTfrCtrPerLine(
			Map<String, Object> map) {
		return super.getSqlSession().selectList(
				NAMESPACE + "selectTfrCtrPerLine", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaTfrCtrPerLineDto> select2ndDayTfrCtrPerLine(
			Map<String, Object> map) {
		return super.getSqlSession().selectList(
				NAMESPACE + "select2ndDayTfrCtrPerLine", map);
	}

	@Override
	public QuantityStaTfrCtrPerLineDto selectTfrCtrPerLine16Day(
			QuantityStaTfrCtrCondDto dto) {
		return (QuantityStaTfrCtrPerLineDto) super.getSqlSession().selectOne(
				NAMESPACE + "selectTfrCtrPerLine16Day", dto);
	}

	@Override
	public void insertFcst(QuantityStaFcstEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insertFcst", entity);
	}

	@Override
	public void insert2ndDayFcst(QuantityStaFcstEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insert2ndDayFcst", entity);
	}

	@Override
	public void insertHis(Date date) {
		super.getSqlSession().insert(NAMESPACE + "insertHis", date);
	}

	@Override
	public void delete16Day(Date date) {
		super.getSqlSession().delete(NAMESPACE + "delete16Day", date);

	}

	@Override
	public void insertFcstHis(Date date) {
		super.getSqlSession().insert(NAMESPACE + "insertFcstHis", date);
	}

	@Override
	public void deleteFcst(Date date) {
		super.getSqlSession().delete(NAMESPACE + "deleteFcst", date);

	}
}
