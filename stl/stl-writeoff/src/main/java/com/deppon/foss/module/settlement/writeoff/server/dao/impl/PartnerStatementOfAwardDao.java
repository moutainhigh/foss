/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/**
 * 合伙人奖罚对账单Dao
 * @author 269044
 * @date 2016-01-27 
 */
public class PartnerStatementOfAwardDao extends iBatis3DaoImpl implements IPartnerStatementOfAwardDao {
	private static final String NAMESPACE = "foss.stl.partnerStatementOfAwardDao.";

	/**
	 * 根据客户编码和日期查询应收应付单 
	 * @author 269044
	 * @date 2016-01-27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardDEntity> queryPartnerStatementOfAwardDByCustomer(
			PartnerStatementOfAwardDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<PartnerStatementOfAwardDEntity> list = getSqlSession()
				.selectList(NAMESPACE + "queryPartnerStatementOfAwardDByCustomer",dto,rb);
		return list;
	}
	
	/**
	 * 按客户查询应收单应付单总行数
	 * @author 269044
	 * @date 2016-01-28
	 */
	@Override
	public int countPartnerStatementOfAwardDByCustomer(PartnerStatementOfAwardDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "countPartnerStatementOfAwardDByCustomer",dto);
		return count;
	}
	
	/**
	 * 按单号查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardDEntity> queryPartnerStatementOfAwardDByNumber(PartnerStatementOfAwardDto dto) {
		List<PartnerStatementOfAwardDEntity> list = getSqlSession().selectList(NAMESPACE + "queryPartnerStatementOfAwardDByNumber",dto);
		return list;
	}

	/**
	 * 按客户保存对账单明细
	 * @author 269044
	 * @date 2016-01-29
	 */
	@Override
	public int partnerStatementOfAwardDSaveByCustomer(PartnerStatementOfAwardDto dto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "partnerStatementOfAwardDSaveByCustomer",dto);
		return insertDRows;
	}
	
	/**
	 * 按单号保存对账单明细
	 * @author 269044
	 * @date 2016-01-29
	 */
	@Override
	public int partnerStatementOfAwardDSaveByNumber(PartnerStatementOfAwardDto dto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "partnerStatementOfAwardDSaveByNumber",dto);
		return insertDRows;
	}
	
	/**
	 * 按对账单号查询对账单明细
	 * @author 269044
	 * @date 2016-01-29
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardDEntity> querypartnerDByStatementBillNo(PartnerStatementOfAwardDto dto) {
		List<PartnerStatementOfAwardDEntity> list = getSqlSession().selectList(NAMESPACE + "querypartnerDByStatementBillNo",dto);
		return list;
	}
	
	/**
	 * 按对账单单号保存对账单
	 * @author 269044
	 * @date 2016-01-29
	 */
	@Override
	public int partnerStatementOfAwardSaveByStatementBillNo(PartnerStatementOfAwardDto dto) {
		int insertRows = getSqlSession().insert(NAMESPACE + "partnerStatementOfAwardSaveByStatementBillNo",dto);
		return insertRows;
	}
	
	/**
	 * 更新应付单
	 * @author 269044
	 * @date 2016-01-29 
	 */
	@Override
	public int partnerPayUpdateByStatementBillNo(PartnerStatementOfAwardDto dto) {
		int updatePayRows = getSqlSession().update(NAMESPACE + "partnerPayUpdateByStatementBillNo",dto);
		return updatePayRows;
	}

	/**
	 * 更新应收单
	 * @author 269044
	 * @date 2016-01-29 
	 */
	@Override
	public int partnerRecUpdateByStatementBillNo(PartnerStatementOfAwardDto dto) {
		int updatePayRows = getSqlSession().update(NAMESPACE + "partnerRecUpdateByStatementBillNo",dto);
		return updatePayRows;
	}

	/**
	 * 更新应收单扣款状态
	 */
	public int updateDeductStatus(Map<String,Object> map){
		return this.getSqlSession().update(NAMESPACE + "updateDeductStatus",map);
	}
	
	/**
	 * 更新应收单扣款状态
	 */
	public int updateDeductStatusAuto(Map<String,Object> map){
		return this.getSqlSession().update(NAMESPACE + "updateDeductStatusAuto",map);
	}
	
	/**
	 * 查询本期能够生成对账单的部门编码和合伙人编码 
	 * @author 367752
	 * @date 2016-09-01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardDEntity> queryOrgCodeAndCustomerCode(PartnerStatementOfAwardDto dto) {
		List<PartnerStatementOfAwardDEntity> list = getSqlSession().selectList(NAMESPACE + "queryOrgCodeAndCustomerCode",dto);
		return list;
	}
	
	/**
	 * 按客户和部门自动保存对账单明细
	 * @author 367752
	 * @date 2016-09-02
	 */
	@Override
	public int partnerStatementOfAwardDSaveByCustomerAuto(PartnerStatementOfAwardDto dto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "partnerStatementOfAwardDSaveByCustomerAuto",dto);
		return insertDRows;
	}
	
	/**
	 * 按对账单单号自动保存对账单
	 * @author 367752
	 * @date 2016-09-02
	 */
	@Override
	public int partnerStatementOfAwardSaveByStatementBillNoAuto(PartnerStatementOfAwardDto dto) {
		int insertRows = getSqlSession().insert(NAMESPACE + "partnerStatementOfAwardSaveByStatementBillNoAuto",dto);
		return insertRows;
	}

	/**
	 * 按照应收单ID更新对账单明细的已核销和未核销金额
	 * @param map
	 */
	@Override
	public int updateAmountByReceivableNo(Map<String,Object> map) {		
		int updateRows = this.getSqlSession().update(NAMESPACE + "updateAmountByReceivableNo",map);
		return updateRows;
	}

	/**
	 * 按照应付单ID更新对账单明细的已核销和未核销金额
	 * @param map
	 */	
	@Override
	public int updateAmountByPayableNo(Map<String, Object> map) {
		int updateRows = this.getSqlSession().update(NAMESPACE + "updateAmountByPayableNo",map);
		return updateRows;
	}
	
	/**
	 * 根据应付单号设置对账金额
	 * @param billPayableEntity
	 */
	@Override
	public int updateAmountInStatementByPayableNo(BillPayableEntity billPayableEntity) {
		int updateRows = this.getSqlSession().update(NAMESPACE + "updateAmountInStatementByPayableNo",billPayableEntity);
		return updateRows;
	}
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param map
	 */
	@Override
	public int updateAmountByStatemnetNo(Map<String, Object> map){
		int updateRows = this.getSqlSession().update(NAMESPACE + "updateAmountByStatemnetNo",map);
		return updateRows;
	}
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param Map<String, Object>
	 */
	public int updateDetailAmountByStatemnetNo(Map<String, Object> bmap){
		int updateRows = this.getSqlSession().update(NAMESPACE + "updateDetailAmountByStatemnetNo",bmap);
		return updateRows;
	}
}
