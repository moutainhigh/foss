package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockDurationDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDuration;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationDispatch;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationTransfer;
import com.deppon.foss.module.transfer.platform.api.shared.dto.Dates4StockDurationDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.KeyValueDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StockDurationQcDto;

public class StockDurationDao extends iBatis3DaoImpl implements
		IStockDurationDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.IStockDurationDao.";

	@SuppressWarnings("unchecked")
	@Override
	public List<KeyValueDto> findReceiveMethod() {
		return super.getSqlSession().selectList(NAMESPACE + "findReceiveMethod");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findTfrCtrs(Integer threadCount,
			Integer threadNo) {
		String statement = NAMESPACE + "findTfrCtrs";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("threadCount", threadCount);
		map.put("threadNo", threadNo);
		return super.getSqlSession().selectList(statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationTransfer> findTfrLoadExp(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth) {
		String statement = NAMESPACE + "findTfrLoadExp";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("tfrCtrName", tfrCtrName);
		map.put("staDate", staDate);
		map.put("endDate", endDate);
		map.put("staMonth", staMonth);
		return super.getSqlSession().selectList(statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationTransfer> findTfrLoadLtc(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth) {
		String statement = NAMESPACE + "findTfrLoadLtc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("tfrCtrName", tfrCtrName);
		map.put("staDate", staDate);
		map.put("endDate", endDate);
		map.put("staMonth", staMonth);
		return super.getSqlSession().selectList(statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> findNextOrg(Map<String, String> map) {
		String statement = NAMESPACE + "findNextOrg";
		return (Map<String, String>) super.getSqlSession().selectOne(statement,
				map);
	}

	@Override
	public Date findArrivedTime(Map<String, String> map) {
		String statement = NAMESPACE + "findArrivedTime";
		return (Date) super.getSqlSession().selectOne(statement, map);
	}

	@Override
	public Dates4StockDurationDto findUnloadTaskBeginAndScanTime(
			Map<String, String> map) {
		String statement = NAMESPACE + "findUnloadTaskBeginAndScanTime";
		return (Dates4StockDurationDto) super.getSqlSession().selectOne(statement,
				map);
	}

	@Override
	public Dates4StockDurationDto findTrayBindingAndForkliftScanTime(
			Map<String, String> map) {
		String statement = NAMESPACE + "findTrayBindingAndForkliftScanTime";
		return (Dates4StockDurationDto) super.getSqlSession().selectOne(statement,
				map);
	}

	@Override
	public Date findOutPkgAreaTime(Map<String, String> map) {
		String statement = NAMESPACE + "findOutPkgAreaTime";
		return (Date) super.getSqlSession().selectOne(statement, map);
	}

	@Override
	public Date findInPkgAreaTime(Map<String, String> map) {
		String statement = NAMESPACE + "findInPkgAreaTime";
		return (Date) super.getSqlSession().selectOne(statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationDispatch> findDptLoadExp(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth) {
		String statement = NAMESPACE + "findDptLoadExp";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("tfrCtrName", tfrCtrName);
		map.put("staDate", staDate);
		map.put("endDate", endDate);
		map.put("staMonth", staMonth);
		return super.getSqlSession().selectList(statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationDispatch> findDptLoadLtc(String tfrCtrCode,
			String tfrCtrName, Date staDate, Date endDate, int staMonth) {
		String statement = NAMESPACE + "findDptLoadLtc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("tfrCtrName", tfrCtrName);
		map.put("staDate", staDate);
		map.put("endDate", endDate);
		map.put("staMonth", staMonth);
		return super.getSqlSession().selectList(statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> findPreOrg(Map<String, String> map) {
		String statement = NAMESPACE + "findPreOrg";
		return (Map<String, String>) super.getSqlSession().selectOne(statement,
				map);
	}

	@Override
	public Date findSignTime(String waybillNo) {
		String statement = NAMESPACE + "findSignTime";
		return (Date) super.getSqlSession().selectOne(statement, waybillNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findUnloadWaitTimeExp(Map<String, Object> map) {
		String statement = NAMESPACE + "findUnloadWaitTimeExp";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findUnloadTimeExp(Map<String, Object> map) {
		String statement = NAMESPACE + "findUnloadTimeExp";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findForkAreaStayTimeExp(
			Map<String, Object> map) {
		String statement = NAMESPACE + "findForkAreaStayTimeExp";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findPkgAreaStayTimeExp(
			Map<String, Object> map) {
		String statement = NAMESPACE + "findPkgAreaStayTimeExp";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findTfrAreaTimeExp(Map<String, Object> map) {
		String statement = NAMESPACE + "findTfrAreaTimeExp";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findDptAreaTimeExp(Map<String, Object> map) {
		String statement = NAMESPACE + "findDptAreaTimeExp";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findUnloadWaitTimeLtc(Map<String, Object> map) {
		String statement = NAMESPACE + "findUnloadWaitTimeLtc";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findUnloadTimeLtc(Map<String, Object> map) {
		String statement = NAMESPACE + "findUnloadTimeLtc";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findForkAreaStayTimeLtc(
			Map<String, Object> map) {
		String statement = NAMESPACE + "findForkAreaStayTimeLtc";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findPkgAreaStayTimeLtc(
			Map<String, Object> map) {
		String statement = NAMESPACE + "findPkgAreaStayTimeLtc";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findTfrAreaTimeLtc(Map<String, Object> map) {
		String statement = NAMESPACE + "findTfrAreaTimeLtc";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> findDptAreaTimeLtc(Map<String, Object> map) {
		String statement = NAMESPACE + "findDptAreaTimeLtc";
		return (Map<String, BigDecimal>) super.getSqlSession().selectOne(
				statement, map);
	}

	@Override
	public void insertTfrExp(StockDurationTransfer info) {
		super.getSqlSession().insert(NAMESPACE + "insertTfrExp", info);
	}

	@Override
	public void insertTfrLtc(StockDurationTransfer info) {
		super.getSqlSession().insert(NAMESPACE + "insertTfrLtc", info);
	}

	@Override
	public void insertDptExp(StockDurationDispatch info) {
		super.getSqlSession().insert(NAMESPACE + "insertDptExp", info);
	}

	@Override
	public void insertDptLtc(StockDurationDispatch info) {
		super.getSqlSession().insert(NAMESPACE + "insertDptLtc", info);
	}

	@Override
	public void insertExpDay(StockDuration info) {
		super.getSqlSession().insert(NAMESPACE + "insertExpDay", info);
	}

	@Override
	public void insertLtcDay(StockDuration info) {
		super.getSqlSession().insert(NAMESPACE + "insertLtcDay", info);
	}

	@Override
	public void insertExpMonth(String tfrCtrCode, Date staDate, int staMonth) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("staDate", staDate);
		map.put("staMonth", staMonth);
		super.getSqlSession().insert(NAMESPACE + "insertExpMonth", map);
	}

	@Override
	public void insertLtcMonth(String tfrCtrCode, Date staDate, int staMonth) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("staDate", staDate);
		map.put("staMonth", staMonth);
		super.getSqlSession().insert(NAMESPACE + "insertLtcMonth", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDuration> findExpDay(StockDurationQcDto parameter) {
		return super.getSqlSession().selectList(NAMESPACE + "findExpDay",
				parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDuration> findExpMonth(StockDurationQcDto parameter) {
		return super.getSqlSession().selectList(NAMESPACE + "findExpMonth",
				parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDuration> findLtcDay(StockDurationQcDto parameter) {
		return super.getSqlSession().selectList(NAMESPACE + "findLtcDay",
				parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDuration> findLtcMonth(StockDurationQcDto parameter) {
		return super.getSqlSession().selectList(NAMESPACE + "findLtcMonth",
				parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationTransfer> findTfrExp(StockDurationQcDto parameter,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return super.getSqlSession().selectList(NAMESPACE + "findTfrExp",
				parameter, rowBounds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationTransfer> findTfrLtc(StockDurationQcDto parameter,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return super.getSqlSession().selectList(NAMESPACE + "findTfrLtc",
				parameter, rowBounds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationDispatch> findDptExp(StockDurationQcDto parameter,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return super.getSqlSession().selectList(NAMESPACE + "findDptExp",
				parameter, rowBounds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockDurationDispatch> findDptLtc(StockDurationQcDto parameter,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return super.getSqlSession().selectList(NAMESPACE + "findDptLtc",
				parameter, rowBounds);
	}

	@Override
	public Long findTfrExpCnt(StockDurationQcDto parameter) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "findTfrExpCnt", parameter);
	}

	@Override
	public Long findTfrLtcCnt(StockDurationQcDto parameter) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "findTfrLtcCnt", parameter);
	}

	@Override
	public Long findDptExpCnt(StockDurationQcDto parameter) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "findDptExpCnt", parameter);
	}

	@Override
	public Long findDptLtcCnt(StockDurationQcDto parameter) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "findDptLtcCnt", parameter);
	}

}
