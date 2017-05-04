package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/**
 * 合伙人奖罚对账单Service
 * @author 269044
 * @date 2016-01-27 
 */
public interface IPartnerStatementOfAwardService {
	
	/**
	 * 查询应收应付单
	 * @author 269044
	 * @date 2016-01-27
	 */
	public PartnerStatementOfAwardDto queryPartnerStatementOfAwardD(PartnerStatementOfAwardDto dto, int start, int limit);
	
	/**
	 * 设置对账单的本期未还金额
	 * @author 269044
	 * @date 2012-11-28 下午12:59:11601-28
	 */
	PartnerStatementOfAwardDto updateUnpaidAmount(PartnerStatementOfAwardDto dto);
	
	/**
	 * 生成合伙人奖罚对账单
	 * @author 269044
	 * @date 206-01-29
	 */
	public PartnerStatementOfAwardDto partnerStatementOfAwardSave(PartnerStatementOfAwardDto dto);
			
	/**
	 * 
	 * @author 367752
	 * @param map
	 * @return
	 * @date 2016-09-08
	 */
	public int updateDeductStatus(Map<String,Object> map);	
	
	/**
	 * 查询本期能够生成对账单的部门编码和呵护编码
	 * @author 367752
	 * @param dto
	 * @return
	 * @date 2016-09-08
	 */
	public List<PartnerStatementOfAwardDEntity> queryOrgCodeAndCustomerCode(PartnerStatementOfAwardDto dto);

	/**
	 * 按对账单号查询对账单明细
	 * @author 367752
	 * @param queryDto
	 * @return
	 */
	public List<PartnerStatementOfAwardDEntity> querypartnerDByStatementBillNo(PartnerStatementOfAwardDto queryDto);
	
	/**
	 * 按照应收单ID更新对账单明细的已核销和未核销金额
	 * @param nos
	 */
	public int updateAmountByReceivableNo(Map<String,Object> map);

	/**
	 * 按照应付单ID更新对账单明细的已核销和未核销金额
	 * @param nos
	 */
	public int updateAmountByPayableNo(Map<String,Object> map);
	
	/**
	 * 根据应付单号设置对账金额
	 * @param billPayableEntity
	 */
	public int updateAmountInStatementByPayableNo(BillPayableEntity billPayableEntity);
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param nos
	 */
	public int updateAmountByStatemnetNo(Map<String, Object> bmap);
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param Map<String, Object>
	 */
	public int updateDetailAmountByStatemnetNo(Map<String, Object> bmap);
}
