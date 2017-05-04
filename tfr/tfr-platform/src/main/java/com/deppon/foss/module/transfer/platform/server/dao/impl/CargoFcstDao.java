package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ICargoFcstDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CargoDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CargoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstResultDto;

public class CargoFcstDao extends iBatis3DaoImpl implements ICargoFcstDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ICargoFcstDao.";

	@Override
	public Long findHoliday(Date date) {
		String statement = NAMESPACE + "findHoliday";
		return (Long) super.getSqlSession().selectOne(statement, date);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findArrLines(String tfrCtrCode) {
		String statement = NAMESPACE + "findArrLines";
		return super.getSqlSession().selectList(statement, tfrCtrCode);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findDepartLines(String tfrCtrCode) {
		String statement = NAMESPACE + "findDepartLines";
		return super.getSqlSession().selectList(statement, tfrCtrCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findTfrCtrs() {
		String statement = NAMESPACE + "findTfrCtrs";
		return super.getSqlSession().selectList(statement);
	}

	@Override
	public String findFcstConfig(Map<String, String> map) {
		String statement = NAMESPACE + "findFcstConfig";
		return (String) super.getSqlSession().selectOne(statement, map);
	}

	@Override
	public void insertActualArrLng(CargoFcstDto parameter) {
		String statement = NAMESPACE + "insertActualArrLng";
		super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public void insertActualArrSht(CargoFcstDto parameter) {
		String statement = NAMESPACE + "insertActualArrSht";
		super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public void insertActualArrPickup(CargoFcstDto parameter) {
		String statement = NAMESPACE + "insertActualArrPickup";
		super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public void insertActualDptLng(CargoFcstDto parameter) {
		String statement = NAMESPACE + "insertActualDptLng";
		super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public void insertActualDptSht(CargoFcstDto parameter) {
		String statement = NAMESPACE + "insertActualDptSht";
		super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public void insertActualDptDispatch(CargoFcstDto parameter) {
		String statement = NAMESPACE + "insertActualDptDispatch";
		super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public void insertActual(CargoFcstDto parameter) {
		String statement = NAMESPACE + "insertActual";
		super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public CargoEntity findActual(String tfrCtrCode, Date staDate,
			String lineCode) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tfrCtrCode", tfrCtrCode);
		parameter.put("staDate", staDate);
		parameter.put("lineCode", lineCode);

		String statement = NAMESPACE + "findActual";
		return (CargoEntity) super.getSqlSession().selectOne(statement,
				parameter);
	}

	@Override
	public CargoDetailEntity findActualDetail(String tfrCtrCode, Date staDate,
			String lineCode, String goodsType) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tfrCtrCode", tfrCtrCode);
		parameter.put("staDate", staDate);
		parameter.put("lineCode", lineCode);
		parameter.put("goodsType", goodsType);

		String statement = NAMESPACE + "findActualDetail";
		return (CargoDetailEntity) super.getSqlSession().selectOne(statement,
				parameter);
	}

	@Override
	public int insertFcst(CargoEntity parameter) {
		String statement = NAMESPACE + "insertFcst";
		return super.getSqlSession().insert(statement, parameter);
	}

	@Override
	public void insertFcstDetail(CargoDetailEntity parameter) {
		String statement = NAMESPACE + "insertFcstDetail";
		super.getSqlSession().insert(statement, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoFcstResultDto> findFcstResult(CargoFcstDto parameter) {
		String statement = NAMESPACE + "findFcstResult";
		return super.getSqlSession().selectList(statement, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargoFcstResultDto> findFcstResultTotal(CargoFcstDto parameter) {
		String statement = NAMESPACE + "findFcstResultTotal";
		return super.getSqlSession().selectList(statement, parameter);
	}

	@Override
	public int cntFcst(String tfrCtrCode, String lineCode, Date staDate) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("lineCode", lineCode);
		map.put("staDate", staDate);
		return (Integer) super.getSqlSession().selectOne(NAMESPACE+"cntFcst", map);
	}

}
