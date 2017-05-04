package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;

public class PartnerPayStatementDao extends iBatis3DaoImpl implements IPartnerPayStatementDao {

	// 命名空间
	private static final String NAMESPACE = "foss.stl.PartnerPayStatementDao.";

	/**
	 * 制作合伙人付款对账单，按单号查询应付单信息
	 * 
	 * @author 黄乐为
	 * @date 2016-1-29 下午7:58:12
	 * @param dto
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<PartnerPayStatementEntity> queryStatementByNumber(PartnerPayStatementDto dto) {
		// 执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYFByPayableNos", dto);
	}

	/**
	 * 插入一条合伙人付款对账单
	 * 
	 * @author 黄乐为
	 * @date 2016-2-25 下午9:18:33
	 * @param partnerPayStatementEntity
	 * @return
	 */
	@Override
	public int SaveStatement(PartnerPayStatementEntity entity) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "insert", entity);
		return insertDRows;
	}

	/**
	 * 查询合伙人付款对账单
	 * 
	 * @author 汪文博
	 * @date 2016-2-22 下午6:35:12
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementEntity> queryPayStatementByCustomer(
			PartnerPayStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start,limit);
		return getSqlSession().selectList(NAMESPACE + "selectPayStatementByCustomer", dto, rb);
	}

	/**
	 * 查询合伙人付款对账单条数
	 * 
	 * @author 汪文博
	 * @date 2016-2-22 下午6:35:12
	 * @param dto
	 * @return
	 */
	@Override
	public PartnerPayStatementDto queryPayStatementByCustomerCount(PartnerPayStatementDto dto) {
		return (PartnerPayStatementDto) getSqlSession().selectOne(NAMESPACE + "countPayStatementByCustomer", dto);
	}

	/**
	 * 按对账单号查询对账单按对账单号查询对账单(只查询本部门的对账单信息，供按对账单、对账单明细单号集合查询对账单使用)
	 * 
	 * @author 汪文博
	 * @date 2016-2-28 下午3:11:12
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementEntity> queryPayStatementByBillNos(PartnerPayStatementDto dto) {
		return getSqlSession().selectList(NAMESPACE + "queryPayStatementByBillNos", dto);
	}

	/**
	 * 更新对账单
	 * 
	 * @author 汪文博
	 * @date 2016-2-28 下午6:00:12
	 */
	@Override
	public int partnerStatementUpdateByStatementBillNo(PartnerPayStatementDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "partnerStatementUpdateByStatementBillNo", dto);
		return updateRows;
	}
	

	/**
	 * 批量付款更新对账单
	 * 
	 * @author 汪文博
	 * @date 2016-2-28 下午6:00:12
	 */
	@Override
	public int partnerStatementUpdateByStatementBillNos(PartnerPayStatementDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "partnerStatementUpdateByStatementBillNos", dto);
		return updateRows;
	}
	
	/*
	 * 付款成功，更新付款次数和本期应付金额
	 * 
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.
	 * IPartnerPayStatementDao
	 * #updatePartnerStatementPayAmount(com.deppon.foss.module
	 * .settlement.writeoff.api.shared.dto.PartnerPayStatementDto)
	 */
	@Override
	public int updatePartnerStatementPayAmount(PartnerPayStatementDto dto) {
		return getSqlSession().update(
				NAMESPACE + "updatePartnerStatementPayAmount", dto);
	}
	
	/*
	 * 付款失败，更新付款次数和本期应付金额
	 * 
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.
	 * IPartnerPayStatementDao
	 * #updatePartnerStatementForPayFail(com.deppon.foss.module
	 * .settlement.writeoff.api.shared.dto.PartnerPayStatementDto)
	 */
	@Override
	public int updatePartnerStatementForPayFail(PartnerPayStatementDto dto) {
		return getSqlSession().update(
				NAMESPACE + "updatePartnerStatementForPayFail", dto);
	}

	/**
	 * 确认对账单
	 * 
	 * @author 汪文博
	 * @date 2016-3-1
	 */
	@Override
	public int updateStatusByStatementNo(PartnerPayStatementDto queryDto) {
		int updateRows = getSqlSession().update(NAMESPACE + "updateStatusByStatementNo", queryDto);
		return updateRows;
	}
	/**
	 * 按部门查询对账单
	 * 
	 * @author 汪文博
	 * @date 2016-3-2
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementEntity> queryStatementByFailingInvoice(Map<String, String> map, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<PartnerPayStatementEntity> list = getSqlSession().selectList(NAMESPACE + "selectByFailingInvoice", map,rb);
		return list;
	}
	
	/*
	 * 按部门查询合伙人付款对账单(计数)
	 * 
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.
	 * IPartnerPayStatementDao#countStatementByFailingInvoice(java.util.Map)
	 */
	@Override
	public PartnerPayStatementDto countStatementByFailingInvoice(
			Map<String, String> map) {
		return (PartnerPayStatementDto) this.getSqlSession().selectOne(
				NAMESPACE + "countStatementByFailingInvoice", map);
	}
	
	/**
	 * 更新对账单本期发生额、应付金额（删除对账单明细）
	 * 
	 * @author 汪文博
	 * @date 2016-3-3
	 */
	public int updateAmountByStatementNo(PartnerPayStatementDto queryDto){
		int updateRows = getSqlSession().update(NAMESPACE + "updateAmountByStatementNo", queryDto);
		return updateRows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementEntity> queryPayStatementByBillNo(
			String statementBillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPayStatementByBillNo", statementBillNo);
	}
	/**
	 * 根据对账单号集合查询合伙人付款对账单
	 * @author cdj
	 * @date 2016年12月26日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementEntity> partnerPayqueryByStatementNos(List<String> sourcesStatementNos){
		if (CollectionUtils.isNotEmpty(sourcesStatementNos)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statementsNos", sourcesStatementNos);
			return this.getSqlSession().selectList(
					NAMESPACE + "selectPartnerByStatementNos", map);
		}
		return null;
	}
}