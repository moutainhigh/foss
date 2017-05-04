/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;

public interface IPartnerPayStatementDDao {
	
	/**
	 * 按客户查询应付单
	 * @author hlw
	 * @date 2016-01-29
	 */
	public List<PartnerPayStatementDEntity> queryStatementDByCustomer(PartnerPayStatementDto dto, int start,int limit);
	/**
	 * 按对账单查询应付单
	 * @author wwb
	 * @date 2016-03-09
	 */
	public List<PartnerPayStatementDEntity> queryStatementDByExistCustomer(PartnerPayStatementDto dto, int start,int limit);
	/**
	 * 按应付单号查询应付单
	 * @author hlw
	 * @date 2016-01-29
	 */
	public List<PartnerPayStatementDEntity> queryStatementDByPayableNos(PartnerPayStatementDto dto);
	/**
	 * 按运单号查询应付单
	 * @author hlw
	 * @date 2016-01-29
	 */
	public List<PartnerPayStatementDEntity> queryStatementDByWaybillNos(PartnerPayStatementDto dto);
	/**
	 * 按对账单号更新应付单
	 * @author hlw
	 * @date 2016-03-03
	 */
	public int updatePayableByStatementBillNo(PartnerPayStatementDto dto);
	/**
	 * 保存对账单明细
	 * @author hlw
	 * @date 2016-03-03
	 */
	public int saveStatementDEntity(PartnerPayStatementDEntity entity);
	/**
	 * 按客户查询应付单总行数
	 * @author hlw
	 * @date 2016-01-29
	 */
	public int countStatementDByCustomer(PartnerPayStatementDto dto);
	/**
	 * 按对账单单号查询对账单明细总条数
	 * @author hlw
	 * @date 2016-01-29
	 */
	//public int countStatementDByStatementBillNo(PartnerPayStatementDto dto);

	/**
	 * 按单号查询对账单明细
	 * @author hlw
	 * @date 2016-01-29
	 */
	//public List<PartnerPayStatementDEntity> queryStatementDByStatementBillNo(PartnerPayStatementDto dto);

	/**
	 * 根据客户保存对账单明细
	 * @author 黄乐为
	 * @date 2016-3-4 下午10:55:26
	 */
	public int saveDetailByCustomer(PartnerPayStatementDto dto);
	
	/**
	 * 根据应付单号保存对账单明细
	 * @author 黄乐为
	 * @date 2016-3-4 下午10:55:29
	 */
	public int saveDetailByPayableNos(PartnerPayStatementDto dto);
	
	/**
	 * 根据运单号保存对账单明细
	 * @author 黄乐为
	 * @date 2016-3-4 下午10:55:32
	 */
	public int saveDetailByWaybillNos(PartnerPayStatementDto dto);
	/**
	 * 按对账单单号查询对账单明细
	 * @author hlw
	 * @date 2016-01-29
	 */
	//public List<PartnerPayStatementDEntity> queryStatementDByStatementBillNo(PartnerPayStatementDto dto, int start, int limit);

    
	
	/**
	 * 根据对账单号查询对账单明细信息
	 * 
	 * 按合伙人查询对账单
	 * @author wwb
	 * @date 2016-2-23 下午5:18:12
	 */
	public List<PartnerPayStatementDEntity> queryByStatementBillNo(String statementBillNo);
	
	/**
	 * 根据对账单号结合查询对账单明细信息
	 * 
	 * 按合伙人查询对账单
	 * @author wwb
	 * @date 2016-3-12 上午10:18:12
	 */
	List<PartnerPayStatementDEntity> queryByStatementBillNos(List<String> statementBillNos);
	/**
	 * 根据运单号、应付单号集合查询对账单明细
	 * 
	 * @author wwb
	 * @date 2016-03-02 上午11:00:12
	 */
	public List<PartnerPayStatementDEntity> queryByWaybillNos(List<String> waybillNos);
	/**
	 * 更新对账单明细已核销金额
	 * @author wwb
	 * @date 2016-3-3 下午1:52:12
	 */
	public int partnerStatementUpdateByStatementBillNo(PartnerPayStatementDto partnerPayStatementDto);
	
	/**
	 * 更新对账单明细已核销金额(对账单集合)
	 * @author wwb
	 * @date 2016-3-3 下午1:52:12
	 */
	public int partnerStatementUpdateByStatementBillNos(PartnerPayStatementDto partnerPayStatementDto);
	/**
	 * 按明细单id查询对账单明细
	 * @author wwb
	 * @date 2016-03-3
	 */
	public PartnerPayStatementDEntity queryStatementDById(String id);
	/**
	 * 按明细单id更新对账单明细(删除明细时)
	 * @author wwb
	 * @date 2016-03-3
	 */
	public int updateStatementEntryByDEntity(PartnerPayStatementDEntity partnerPayStatementDEntity);
	/**
	 * 付款失败回调更新已核销金额为0
	 * @author wwb
	 * @date 2016-3-10 下午5:44:26
	 */
	public int partnerStatementUpdateVerifyAmountZero(PartnerPayStatementDto dto);
	/**
	 * 付款对账单管理添加明细更新应付单
	 * @author wwb
	 * @date 2016-3-10 上午9:32:26
	 */
	int updatePayableByPayableNo(PartnerPayStatementDto dto);
	
	/**
	 * 付款对账单新增按日期查询总记录数和总金额
	 * @author gpz
	 * @date 2016年11月16日
	 */
	public PartnerPayStatementDto countStatementAmountByCustomer(
			PartnerPayStatementDto dto);
	
	/**
	 * 根据对账单明细id批量删除对账单明细
	 * @author gpz
	 * @date 2016年12月7日
	 */
	public int batchDeleteStatementEntry(Map<String, Object> params);
	
	/**
	 * 根据对账单号查询明细中的金额之和以及业务起止日期
	 * @author gpz
	 * @date 2016年12月7日
	 */
	public PartnerPayStatementDto queryTotalAmountByStatementNo(
			String statementBillNo);
}