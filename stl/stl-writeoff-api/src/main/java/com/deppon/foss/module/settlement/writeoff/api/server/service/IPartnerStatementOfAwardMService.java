/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/** 
 * 合伙人奖罚对账单管理API service
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-17 下午2:12:06    
 */
public interface IPartnerStatementOfAwardMService extends IService{

	
	/**
	 * 合伙人奖罚对账单查询
	 * @param partnerStatementOfAwardDto
	 * @param start
	 * @param limit
	 * @return dto
	 */
	public PartnerStatementOfAwardDto queryPAwardStatement(
			PartnerStatementOfAwardDto partnerStatementOfAwardDto, int start,int limit);

	/**
	 * 根据对账单号查询对账单明细
	 * @param statementNo
	 * @return dto
	 */
	public PartnerStatementOfAwardDto queryPAwardStatementDetail(
			PartnerStatementOfAwardDto queryDto);

	/**
	 * 根据对账单号查询对账单
	 * @param statementNo
	 * @return PartnerStatementOfAwardEntity
	 */
	public PartnerStatementOfAwardEntity queryPStatementsByStaNoNPage( String statementBillNo);

	/**
	 * 更新合伙人奖罚对账单--对账单实体
	 * @param partnerStatementOfAwardEntity
	 */
	public void updateStatementForWriteOff(PartnerStatementOfAwardEntity partnerStatementOfAwardEntity,
			CurrentInfo currentInfo);
	

	/**
	 * 更新合伙人奖罚对账单--对账单实体集合
	 * @param partnerStatementOfAwardEntity
	 */
	public void updateStatementForWriteOffByList(List<PartnerStatementOfAwardEntity> pAwardEntityList,
			CurrentInfo currentInfo);

	/**
	 * 奖罚对账单确认/反确认
	 * @author foss结算-306579-guoxinru
	 * @date 2016-02-28 
	 */
	public void updateStatusByStatementNo(PartnerStatementOfAwardDto queryDto,CurrentInfo currentInfo);

	/**
	 * 奖罚对账单明细删除
	 * @author foss结算-306579-guoxinru
	 * @date 2016-03-04 
	 */
	public PartnerStatementOfAwardDto delPAwardStatementD(PartnerStatementOfAwardDto queryDto);

	/**
	 * 根据对账单号集合查询对账单集合
	 * @author foss结算-306579-guoxinru
	 * @date 2016-03-05
	 */
	public List<PartnerStatementOfAwardEntity> queryPStatementsByStaNoList(
			List<String> statementNoList);

	/**
	 * 根据对账单号集合查询对账单明细
	 * @param statementNoList
	 * @return
	 */
	public List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNoList(
			List<String> statementNoList);

	/**
	 * 根据对账单号、应收应付单号添加对账单明细
	 * @param queryDto
	 * @return
	 */
	public PartnerStatementOfAwardDto addPAwardStatementD(PartnerStatementOfAwardDto queryDto);

	/**
	 * 根据对账单号查询对账单 TODO
	 * @author foss结算-367752
	 * @date 2016-09-03
	 */
	PartnerStatementOfAwardEntity queryPStatementsByStaNo(String statementNo);

	/**
	 * 根据对账单号查询对账单明细集合 TODO
	 * @author foss结算-367752
	 * @date 2016-09-03
	 */
	List<PartnerStatementOfAwardDEntity> queryPStatementDByStaNo(String statementNo);

	/**
	 * 根据对账单ID个，自动更新对账单为已确认状态
	 * @param statementNo
	 */
	void updateStatusByStatementNoAuto(String statementNo);
	
	/**
	 * 查询需要重推的合伙人奖罚对账单,查询条件为：
	 * 1.按照操作的人为：SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE="partnerStatementOfAward_sysJob"
	 * 2.状态为未扣款
	 * 3.仅针对需要扣款的对账单
	 * @author 367752
	 * @return List<PartnerStatementOfAwardEntity>
	 */
	public List<PartnerStatementOfAwardEntity> querypartnerDNeedReDecduct(PartnerStatementOfAwardDto dto);

}
