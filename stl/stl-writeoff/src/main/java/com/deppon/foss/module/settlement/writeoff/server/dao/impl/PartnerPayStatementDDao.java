package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;


public class PartnerPayStatementDDao extends iBatis3DaoImpl implements IPartnerPayStatementDDao{

	//命名空间
	private static final String NAMESPACE = "foss.stl.PartnerPayStatementDDao.";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementDEntity> queryStatementDByCustomer(
			PartnerPayStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<PartnerPayStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryStatementDByCustomer",dto,rb);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementDEntity> queryStatementDByExistCustomer(
			PartnerPayStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<PartnerPayStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryStatementDByExistCustomer",dto,rb);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementDEntity> queryStatementDByPayableNos(
			PartnerPayStatementDto dto) {
		return (List<PartnerPayStatementDEntity>) this.getSqlSession().selectList(NAMESPACE + "selectYFByPayableNos",dto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementDEntity> queryStatementDByWaybillNos(
			PartnerPayStatementDto dto) {
		return (List<PartnerPayStatementDEntity>) this.getSqlSession().selectList(NAMESPACE + "selectYFByWaybillNos",dto);
	}

	@Override
	public int updatePayableByStatementBillNo(PartnerPayStatementDto dto){
		int updatePayRows = getSqlSession().update(NAMESPACE + "updateByStatementBillNo",dto);
		return updatePayRows;
	}
	/**
	 * 付款对账单管理添加明细更新应付单
	 */
	@Override
	public int updatePayableByPayableNo(PartnerPayStatementDto dto){
		int updatePayRows = getSqlSession().update(NAMESPACE + "updatePayableByPayableNo",dto);
		return updatePayRows;
	}
	
	/**
	 * 根据客户保存对账单明细
	 * @author 黄乐为
	 * @date 2016-3-4 下午10:55:26
	 */
	@Override
	public int saveDetailByCustomer(PartnerPayStatementDto dto){
		int insertDRows = getSqlSession().insert(NAMESPACE + "saveDetailByCustomer",dto);
		return insertDRows;
	}
	
	/**
	 * 根据应付单号保存对账单明细
	 * @author 黄乐为
	 * @date 2016-3-4 下午10:55:29
	 */
	@Override
	public int saveDetailByPayableNos(PartnerPayStatementDto dto){
		int insertDRows = getSqlSession().insert(NAMESPACE + "saveDetailByPayableNos",dto);
		return insertDRows;
	}
	
	/**
	 * 根据运单号保存对账单明细
	 * @author 黄乐为
	 * @date 2016-3-4 下午10:55:32
	 */
	@Override
	public int saveDetailByWaybillNos(PartnerPayStatementDto dto){
		int insertDRows = getSqlSession().insert(NAMESPACE + "saveDetailByWaybillNos",dto);
		return insertDRows;
	}
	
	/**
	 * 查询可添加对账单明细
	 */
	@Override
	public int countStatementDByCustomer(PartnerPayStatementDto dto) {
		return (Integer)getSqlSession().selectOne(NAMESPACE + "countStatementDByCustomer", dto);
	}

	/**
	 * 查询合伙人付款对账单详细信息
	 * @author 汪文博
	 * @date 2016-2-23 下午5:19:12
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementDEntity> queryByStatementBillNo(String statementBillNo) {
		return (List<PartnerPayStatementDEntity>) this.getSqlSession()
				.selectList(NAMESPACE + "selectByStatementBillNo", statementBillNo);
	}
	
	/**
	 * 根据对账单号集合查询对账单明细
	 * @author 汪文博
	 * @date 2016-2-23 下午5:19:12
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementDEntity> queryByStatementBillNos(List<String> statementBillNos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statementBillNos", statementBillNos);
		return (List<PartnerPayStatementDEntity>) this.getSqlSession()
				.selectList(NAMESPACE + "selectByStatementBillNos", map);
	}
	
	/**
	 * 根据运单号、应付单号集合查询对账单明细
	 * 
	 * @author 汪文博
	 * @date 2016-03-02 上午11:00:12
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerPayStatementDEntity> queryByWaybillNos(List<String> waybillNos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		return this.getSqlSession().selectList(NAMESPACE + "selectStatementByWaybillNos", map);
	}
	
	/**
	 * 更新对账单明细已核销金额
	 * 
	 * @author 汪文博
	 * @date 2016-3-3 下午1:54:12
	 */
	@Override
	public int partnerStatementUpdateByStatementBillNo(PartnerPayStatementDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "partnerStatementUpdateByStatementBillNo", dto);
		return updateRows;
	}
	
	/**
	 * 更新对账单明细已核销金额(根据对账单集合)
	 * 
	 * @author 汪文博
	 * @date 2016-3-12 下午1:54:12
	 */
	@Override
	public int partnerStatementUpdateByStatementBillNos(PartnerPayStatementDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "partnerStatementUpdateByStatementBillNos", dto);
		return updateRows;
	}
	/**
	 * 根据id查询对账对账单明细
	 * 
	 * @author 汪文博
	 * @date 2016-3-3 下午1:54:12
	 */
	public PartnerPayStatementDEntity queryStatementDById(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		PartnerPayStatementDEntity partnerPayStatementDEntity = (PartnerPayStatementDEntity) getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", map);
		return partnerPayStatementDEntity;
	}
	/**
	 * 按明细单id更新对账单明细(删除明细时)
	 * @author wwb
	 * @date 2016-03-3
	 */
	public int updateStatementEntryByDEntity(PartnerPayStatementDEntity partnerPayStatementDEntity){
		int updateRows = getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", partnerPayStatementDEntity);
		return updateRows;
	}
	/**
	 * 添加对账单明细
	 * @author 311396 wwb
	 *  * @date 2016-03-09
	 */
	@Override
	public int saveStatementDEntity(PartnerPayStatementDEntity entity) {
		int updateRows = getSqlSession().update(NAMESPACE + "insertSelective", entity);
		return updateRows;
	}
	
	/**
	 * 付款失败回调更新已核销金额为0
	 * @author wwb
	 * @date 2016-3-10 下午5:44:26
	 */
	@Override
	public int partnerStatementUpdateVerifyAmountZero(PartnerPayStatementDto dto){
		int updateRows = getSqlSession().update(NAMESPACE + "partnerStatementUpdateVerifyAmountZero",dto);
		return updateRows;
	}

	@Override
	public PartnerPayStatementDto countStatementAmountByCustomer(
			PartnerPayStatementDto dto) {
		return (PartnerPayStatementDto) this.getSqlSession().selectOne(NAMESPACE + "getStatementAmountByCustomer", dto);
	}

	/* 
	 * 批量删除对账单明细
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDDao#batchDeleteStatementEntry(java.util.Map)
	 */
	@Override
	public int batchDeleteStatementEntry(Map<String, Object> params) {
		return this.getSqlSession().update(NAMESPACE + "batchDeleteStatementEntry", params);
	}

	@Override
	public PartnerPayStatementDto queryTotalAmountByStatementNo(
			String statementBillNo) {
		return (PartnerPayStatementDto) this.getSqlSession().selectOne(
				NAMESPACE + "queryTotalAmountByStatementNo", statementBillNo);
	}
}