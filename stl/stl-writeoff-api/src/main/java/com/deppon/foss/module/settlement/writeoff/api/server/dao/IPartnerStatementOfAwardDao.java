package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/**
 * 合伙人奖罚对账单Dao
 * @author 269044
 * @date 2016-01-27 
 */
public interface IPartnerStatementOfAwardDao {
	
	/**
	 * 按客户查询应收单应付单
	 * @author 269044
	 * @date 2016-01-27
	 */
	public List<PartnerStatementOfAwardDEntity> queryPartnerStatementOfAwardDByCustomer(PartnerStatementOfAwardDto dto, int start,int limit);
	
	/**
	 * 按客户查询应收单应付单总行数
	 * @author 269044
	 * @date 2016-01-27
	 */
	public int countPartnerStatementOfAwardDByCustomer(PartnerStatementOfAwardDto dto);
	
	/**
	 * 按单号查询应收单应付单
	 * @author 269044
	 * @date 2016-01-29
	 */
	public List<PartnerStatementOfAwardDEntity> queryPartnerStatementOfAwardDByNumber(PartnerStatementOfAwardDto dto);
	
	/**
	 * 按客户保存对账单明细
	 * @author 269044
	 * @date 206-01-29
	 */
	public int partnerStatementOfAwardDSaveByCustomer(PartnerStatementOfAwardDto dto);
	
	/**
	 * 按单号保存对账单明细
	 * @author 269044
	 * @date 2016-01-29
	 */
	public int partnerStatementOfAwardDSaveByNumber(PartnerStatementOfAwardDto dto);
	
	/**
	 * 按对账单号查询对账单明细
	 * @author 269044
	 * @date 2016-01-29
	 */
	public List<PartnerStatementOfAwardDEntity> querypartnerDByStatementBillNo(PartnerStatementOfAwardDto dto);
	
	/**
	 * 按对账单单号保存对账单
	 * @author 269044
	 * @date 2016-01-29
	 */
	public int partnerStatementOfAwardSaveByStatementBillNo(PartnerStatementOfAwardDto dto);
	
	/**
	 * 跟新应付单
	 * @author 269044
	 * @date 2016-01-29 
	 */
	public int partnerPayUpdateByStatementBillNo(PartnerStatementOfAwardDto dto);
	
	/**
	 * 跟新应收单
	 * @author 269044
	 * @date 2016-01-29 
	 */
	public int partnerRecUpdateByStatementBillNo(PartnerStatementOfAwardDto dto);

	/**
	 * 更新扣款状态
	 * @param map
	 * @return
     */
	public int updateDeductStatus(Map<String,Object> map);
	
	/**
	 * 更新扣款状态
	 * @param map
	 * @return
     */
	public int updateDeductStatusAuto(Map<String,Object> map);
	
	/**
	 * 查询本期能够生成对账单的部门编码和呵护编码 TODO
	 * @author 367752
	 * @date 2016-09-01
	 */
	public List<PartnerStatementOfAwardDEntity> queryOrgCodeAndCustomerCode(PartnerStatementOfAwardDto dto);

	/**
	 * 按客户和部门自动保存对账单明细 TODO
	 * @author 367752
	 * @date 2016-09-02
	 */
	public int partnerStatementOfAwardDSaveByCustomerAuto(PartnerStatementOfAwardDto dto);
	
	/**
	 * 按对账单单号自动保存对账单
	 * @author 367752
	 * @date 2016-09-02
	 */
	public int partnerStatementOfAwardSaveByStatementBillNoAuto(PartnerStatementOfAwardDto dto);

	/**
	 * 按照应收单ID更新对账单明细的已核销和未核销金额
	 * @param map
	 */
	public int updateAmountByReceivableNo(Map<String,Object> map);

	/**
	 * 按照应付单ID更新对账单明细的已核销和未核销金额
	 * @param map
	 */
	public int updateAmountByPayableNo(Map<String, Object> map);
	
	/**
	 * 根据应付单号设置对账金额
	 * @param billPayableEntity
	 */
	public int updateAmountInStatementByPayableNo(BillPayableEntity billPayableEntity);
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param map
	 */
	public int updateAmountByStatemnetNo(Map<String, Object> map);
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param Map<String, Object>
	 */
	public int updateDetailAmountByStatemnetNo(Map<String, Object> bmap);
}
