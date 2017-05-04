package com.deppon.foss.module.transfer.stock.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.server.dao.ISalesDeptExpLostDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostOaLogEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.SalesDeptExpLostWaybillSerialNosDto;

public class SalesDeptExpLostDao extends iBatis3DaoImpl implements
		ISalesDeptExpLostDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.stock.api.server.dao.ISalesDeptExpLostDao.";

	@Override
	public void truncateStock0amSnapshot() {
		super.getSqlSession().selectOne(NAMESPACE + "truncateStock0amSnapshot");
	}

	@Override
	public void saveStock0amSnapshot(Date date) {
		super.getSqlSession().insert(NAMESPACE + "saveStock0amSnapshot", date);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryStockOrgCodes(Map<String, Integer> map) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryStockOrgCodes", map);
	}

	@Override
	public void insertSalesDeptExpLost2His(String orgCode) {
		super.getSqlSession().insert(NAMESPACE + "insertSalesDeptExpLost2His",
				orgCode);
	}

	@Override
	public void deleteSalesDeptExpLost(String orgCode) {
		super.getSqlSession().insert(NAMESPACE + "deleteSalesDeptExpLost",
				orgCode);
	}

	@Override
	public int insertReport24H(Map<String, Object> map) {
		return super.getSqlSession().insert(NAMESPACE + "insertReport24H", map);
	}

	@Override
	public int updateStatus48or72H(Map<String, Object> map) {
		return super.getSqlSession().update(NAMESPACE + "updateStatus48or72H",
				map);
	}

	@Override
	public void update72hReproted(SalesDeptExpLostEntity info) {
		super.getSqlSession().update(NAMESPACE + "update72hReproted", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDeptExpLostEntity> queryReport(SalesDeptExpLostEntity info) {
		return super.getSqlSession()
				.selectList(NAMESPACE + "queryReport", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDeptExpLostEntity> queryReportWaybillNo(
			SalesDeptExpLostEntity info) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryReportWaybillNo", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDeptExpLostEntity> queryReportWaybillNoPaging(
			SalesDeptExpLostEntity info, RowBounds rowBounds) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryReportWaybillNo", info, rowBounds);
	}

	@Override
	public Long queryReportWaybillNoCnt(SalesDeptExpLostEntity info) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "queryReportWaybillNoCnt", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDeptExpLostWaybillSerialNosDto> queryReportSerialNo(
			SalesDeptExpLostEntity info) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryReportSerialNo", info);
	}

	@Override
	public void insertReportOaLog(SalesDeptExpLostOaLogEntity oaLog) {
		super.getSqlSession().insert(NAMESPACE + "insertReportOaLog", oaLog);
	}
}
