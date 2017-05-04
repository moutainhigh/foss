package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardMDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-18 上午8:56:44    
 */


public class PartnerStatementOfAwardMDao extends iBatis3DaoImpl implements IPartnerStatementOfAwardMDao {
	
	private static final String NAMESPACE = "foss.stl.partnerStatementOfAwardMDao.";
	/** 
	 * 根据合伙人查询对账单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardEntity> queryPStatementsByPartner(
			PartnerStatementOfAwardDto queryDto, int start, int limit) {
		//分页设置
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryPStatementsByPartner", queryDto,rb);
	}

	/** 
	 * 按对账单号集合查询对账单--分页
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardEntity> queryPStatementsByStatementNos(
			List<String> statementBillNosList, int start, int limit) {
		//分页设置
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryPStatementsByStatementNos", statementBillNosList,rb);
	}
	
	/** 
	 * 根据对账单号集合查询对账单集合--不分页
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardEntity> queryPStatementsByStaNoList(List<String> statementNoList) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPStatementsByStaNoList", statementNoList);
	}
	
	/** 
	 * 根据对账单号集合查询对账单明细集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNoList(
			List<String> statementNoList) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPStatementDByStaNoList", statementNoList);
	}
	
	/** 
	 * 根据对账单号查询对账单明细集合 TODO
	 * @author 367752
	 * @date 2016-09-03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNo(String statementNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPStatementDByStaNo", statementNo);
	}
	
	/** 
	 * 根据运单号集合获取对账单明细集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardDEntity> queryPSAwardDList(List<String> wayBillList) {
		
		return this.getSqlSession().selectList(NAMESPACE + "queryPSAwardDList", wayBillList);
	}

	/** 
	 * 按部门查询对账单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerStatementOfAwardEntity> queryPStatementsBydep(
			PartnerStatementOfAwardDto queryDto, int start, int limit) {
		//分页设置
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryPStatementsBydep", queryDto,rb);
	}

	/** 
	 * 按对账单号查询对账单
	 */
	@Override
	public PartnerStatementOfAwardEntity queryPStatementsByStaNoNPage(String statementBillNo) {
		return (PartnerStatementOfAwardEntity) this.getSqlSession().selectOne(NAMESPACE + "queryPStatementsByStaNoNPage", statementBillNo);
	}
	
	/** 
	 * 更新合伙人奖罚对账单
	 */
	@Override
	public int updateStatementForWriteOff(PartnerStatementOfAwardDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "PAwardStatementUpdateByStatementBillNo",dto);
		return updateRows;
		
	}
	
	/** 
	 * 更新合伙人奖罚对账单--批量
	 */
	@Override
	public int updateStatementForWriteOffByList(List<PartnerStatementOfAwardDto> queryDtoList) {
		return this.getSqlSession().update(NAMESPACE + "updateStatementForWriteOffByList",queryDtoList);
	}
	
	/** 
	 * 更新合伙人奖罚对账单状态
	 */
	@Override
	public int updateStatusByStatementNo(PartnerStatementOfAwardDto dto) {
		return this.getSqlSession().update(NAMESPACE + "updateStatusByStatementNo",dto);
	}
	
	/** 
	 * 删除对账单明细
	 */
	@Override
	public int delPAwardStatementD(PartnerStatementOfAwardDto dto) {
		return this.getSqlSession().delete(NAMESPACE + "delPAwardStatementD",dto);
	}
	
	/** 
	 * 更新对账单金额
	 */
	@Override
	public int pAwardStatementUpdateAmountByStatementBillNo(
			PartnerStatementOfAwardDto dto) {
		return this.getSqlSession().update(NAMESPACE + "pAwardStatementUpdateAmountByStatementBillNo",dto);
	}
	
	/** 
	 * 更新应付单
	 */
	@Override
	public int updatePayStatementBillNo(PartnerStatementOfAwardDto dto) {
		return this.getSqlSession().update(NAMESPACE + "updatePayStatementBillNo",dto);
	}
	
	/** 
	 * 更新应收单
	 */
	@Override
	public int updateRecStatementBillNo(PartnerStatementOfAwardDto dto) {
		return this.getSqlSession().update(NAMESPACE + "updateRecStatementBillNo",dto);
	}

	/** 
	 * 按对账单号、应收应付单号添加对账单明细
	 */
	@Override
	public int addPAwardStatementD(PartnerStatementOfAwardDto dto) {
		return this.getSqlSession().insert(NAMESPACE + "addPAwardStatementD",dto);
	}

	/**
	 * 按合伙人查询总记录数
	 */
	@Override
	public Long queryPStatementsByPartnerNum(PartnerStatementOfAwardDto queryDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryPStatementsByPartnerNum",queryDto);
	}

	/** 
	 * 按对账单查询总记录数
	 */
	
	public Long queryPStatementsNos(List<String> statementBillNosList){
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryPStatementsNos",statementBillNosList);
	}

	/**
	 * 按部门查询总记录数
	 */
	@Override
	public Long queryPStatementsBydepNum(PartnerStatementOfAwardDto queryDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryPStatementsBydepNum",queryDto);
	}

	/** 
	 * 按对账单号查询对账单
	 * @author 367752
	 * @date 2016-10-12
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PartnerStatementOfAwardEntity> queryPStatementsByStaNo(
			String statementBillNo) {
		 return this.getSqlSession().selectList(NAMESPACE + "queryPStatementsByStaNoNPage", statementBillNo);
	}
	
	/**
	 * 查询需要重推的合伙人奖罚对账单,查询条件为：
	 * 1.按照操作的人为：SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE="partnerStatementOfAward_sysJob"
	 * 2.状态为未扣款
	 * 3.仅针对需要扣款的对账单
	 * @author 367752
	 * @return List<PartnerStatementOfAwardEntity>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PartnerStatementOfAwardEntity> querypartnerDNeedReDecduct(
			PartnerStatementOfAwardDto dto) {
		List<PartnerStatementOfAwardEntity> list = getSqlSession().selectList(NAMESPACE + "querypartnerDNeedReDecduct",dto);
		return list;
	}

}
