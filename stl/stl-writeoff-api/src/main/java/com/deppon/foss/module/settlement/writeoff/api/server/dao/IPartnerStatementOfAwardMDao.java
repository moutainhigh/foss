package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-18 上午8:55:33    
 */
public interface IPartnerStatementOfAwardMDao {
	
	/** 
	 * 根据合伙人查询对账单
	 */
	List<PartnerStatementOfAwardEntity> queryPStatementsByPartner(
			PartnerStatementOfAwardDto queryDto, int start, int limit);

	
	/** 
	 * 按对账单号查询对账单--分页
	 */
	List<PartnerStatementOfAwardEntity> queryPStatementsByStatementNos(
			List<String> statementBillNosList, int start, int limit);
	
	/** 
	 * 查询对账单总记录数
	 */
	Long queryPStatementsNos(List<String> statementBillNosList);
	
	/** 
	 * 按对账单号查询对账单--不分页
	 */
	PartnerStatementOfAwardEntity queryPStatementsByStaNoNPage(
			String statementBillNo);


	/** 
	 * 根据运单号集合获取对账单明细集合
	 */
	List<PartnerStatementOfAwardDEntity> queryPSAwardDList(List<String> wayBillList);


	/** 
	 * 按部门查询对账单集合
	 */
	List<PartnerStatementOfAwardEntity> queryPStatementsBydep(
			PartnerStatementOfAwardDto queryDto, int start, int limit);


	/** 
	 * 更新合伙人奖罚对账单
	 */
	int updateStatementForWriteOff(PartnerStatementOfAwardDto queryDto);


	/** 
	 * 更新合伙人奖罚对账单状态
	 */
	int updateStatusByStatementNo(PartnerStatementOfAwardDto queryDto);


	/** 
	 * 根据单号删除对账单明细
	 */
	int delPAwardStatementD(PartnerStatementOfAwardDto dto);


	/** 
	 * 按对账单单号更新对账单金额、单据类型
	 */
	int pAwardStatementUpdateAmountByStatementBillNo(PartnerStatementOfAwardDto dto);


	/** 
	 * 根据单号更新应付单
	 */
	int updatePayStatementBillNo(PartnerStatementOfAwardDto dto);


	/** 
	 * 根据单号更新应收单
	 */
	int updateRecStatementBillNo(PartnerStatementOfAwardDto dto);


	/** 
	 * 按对账单号集合查询对账单集合
	 */
	List<PartnerStatementOfAwardEntity> queryPStatementsByStaNoList(List<String> statementNoList);


	/**
	 * 更新合伙人奖罚对账单金额
	 * @param queryDtoList
	 * @return
	 */
	int updateStatementForWriteOffByList(List<PartnerStatementOfAwardDto> queryDtoList);

	/** 
	 * 按对账单号集合查询对账单明细集合
	 */
	List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNoList(List<String> statementNoList);


	/** 
	 * 按对账单号、应收应付单号添加对账单明细
	 */
	int addPAwardStatementD(PartnerStatementOfAwardDto queryDto);


	/**
	 * 按合伙人查询总记录数
	 * @param queryDto
	 * @return
	 */
	Long queryPStatementsByPartnerNum(PartnerStatementOfAwardDto queryDto);

	/**
	 * 按部门查询总记录数
	 * @param queryDto
	 * @return
	 */
	Long queryPStatementsBydepNum(PartnerStatementOfAwardDto queryDto);

	/** 
	 * 按对账单号集合查询对账单明细集合
	 * @author 367752
	 * @date 2016-09-03
	 */
	List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNo(String statementNo);
	
	/** 
	 * 按对账单号查询对账单
	 * @author 367752
	 * @date 2016-10-12
	 */
	List<PartnerStatementOfAwardEntity> queryPStatementsByStaNo(
			String statementBillNo);

	/**
	 * 查询需要重推的合伙人奖罚对账单,查询条件为：
	 * 1.按照操作的人为：SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE="partnerStatementOfAward_sysJob"
	 * 2.状态为未扣款
	 * 3.仅针对需要扣款的对账单
	 * @author 367752
	 * @return List<PartnerStatementOfAwardEntity>
	 */
	List<PartnerStatementOfAwardEntity> querypartnerDNeedReDecduct(
			PartnerStatementOfAwardDto dto);

}
