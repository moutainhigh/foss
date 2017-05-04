package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;


public interface IPartnerPayStatementDao {
    
	/**
	 * 按客户查询对账单
	 * @author hlw
	 * @date 2016-01-29
	 */
	//public List<PartnerPayStatementEntity> queryStatementByCustomer(PartnerPayStatementDto dto, int start,int limit);
	/**
	 * 按单号查询对账单
	 * @author hlw
	 * @date 2016-01-29
	 */
	public List<PartnerPayStatementEntity> queryStatementByNumber(PartnerPayStatementDto dto);
	/**
	 * 按客户查询对账单总个数
	 * @author hlw
	 * @date 2016-01-29
	 */
	//public int countStatementByCustomer(PartnerPayStatementDto dto);
	/**
	 * 按对账单单号保存对账单
	 * @author hlw
	 * @date 2016-01-29
	 */
	//public int SaveByStatementBillNo(PartnerPayStatementDto dto);
	/**
	 * 插入一条合伙人付款对账单
	 * @author 黄乐为
	 * @date 2016-2-25 下午9:18:33
	 * @param partnerPayStatementEntity
	 * @return
	 */
	public int SaveStatement(PartnerPayStatementEntity entity);
	/**
	 * 按合伙人查询对账单
	 * @author wwb
	 * @date 2016-2-22 下午6:35:12
	 */
	public List<PartnerPayStatementEntity> queryPayStatementByCustomer(PartnerPayStatementDto dto, int start,int limit);
	/**
	 * 按合伙人查询对账单数量
	 * @author wwb
	 * @date 2016-2-22 下午6:35:12
	 */
	public PartnerPayStatementDto queryPayStatementByCustomerCount(PartnerPayStatementDto dto);

	/**
	 * 按对账单号查询对账单(只查询本部门的对账单信息，供按对账单、对账单明细单号集合查询对账单使用)
	 * @author wwb
	 * @date 2016-2-28 下午3:11:12
	 */
	public List<PartnerPayStatementEntity> queryPayStatementByBillNos(PartnerPayStatementDto dto);
	/**
	 * 按部门查询对账单
	* @author wwb
	 * @date 2016-3-2 
	 */
	public List<PartnerPayStatementEntity> queryStatementByFailingInvoice(Map<String, String> map,int start, int limit);
	
	/**
	 * 按部门查询对账单(计数)
	 * @author gpz
	 * @date 2017-1-11 
	 */
	public PartnerPayStatementDto countStatementByFailingInvoice(
			Map<String, String> map);
	
	/**
	 * 更新对账单
	 * @author wwb
	 * @date 2016-2-28 下午3:11:12
	 */
	public int partnerStatementUpdateByStatementBillNo(PartnerPayStatementDto dto);
	
	/**
	 * 批量付款 更新对账单
	 * @author wwb
	 * @date 2016-2-28 下午3:11:12
	 */
	public int partnerStatementUpdateByStatementBillNos(PartnerPayStatementDto partnerPayStatementDto);
	/**
	 * 确认对账单
	 * @author wwb
	 * @date 2016-3-1 
	 */
	public int updateStatusByStatementNo(PartnerPayStatementDto queryDto);
	/**
	 * 更新对账单本期发生额、应付金额（删除对账单明细）
	 * @author wwb
	 * @date 2016-3-3 
	 */
	public int updateAmountByStatementNo(PartnerPayStatementDto queryDto);
	
	/**
	 * 根据对账单号查询合伙人付款对账单
	 * @author gpz
	 * @date 2016年12月6日
	 */
	public List<PartnerPayStatementEntity> queryPayStatementByBillNo(String statementBillNo);

	/**
	 * 根据对账单号查询合伙人付款对账单
	 * @author cdj
	 * @date 2016年12月26日
	 */
	public List<PartnerPayStatementEntity> partnerPayqueryByStatementNos(List<String> sourcesStatementNos);

	/**
	 * 付款成功，更新付款次数和本期应付金额
	 * @author gpz
	 * @date 2017年1月6日
	 */
	public int updatePartnerStatementPayAmount(PartnerPayStatementDto dto);
	
	/**
	 * 付款失败，更新付款次数和本期应付金额
	 * @author gpz
	 * @date 2017年1月6日
	 */
	public int updatePartnerStatementForPayFail(PartnerPayStatementDto dto);
}