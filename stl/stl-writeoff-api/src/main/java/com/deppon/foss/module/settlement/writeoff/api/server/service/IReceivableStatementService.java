package com.deppon.foss.module.settlement.writeoff.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementRecivableDto;

import java.util.List;

/**
 * @author foss-youkun
 * @date 2016/1/19
 */
public interface IReceivableStatementService {
    /**
     * 查询出应收单需要生成对账单的数据
     * @param dto
     * @param currentInfo
     * @return
     * @throws Exception
     */
    StatementRecivableDto queryBillRecivableByList(StatementRecivableDto dto,CurrentInfo currentInfo,int start,int limit) throws SettlementException;

    /**
     * 新增收款对账单
     * @param dto
     * @return
     * @throws SettlementException
     */
    String addReceivableStatement(StatementRecivableDto dto,CurrentInfo info) throws SettlementException;

    /**
     * 更新对账单的状态
     * @param statementStatus
     * @return
     */
    void updateStatementByStatementStatus(String statementStatus,List<String> statementBillNo) throws SettlementException;

    /**
     * 查询合伙人收款对账单
     * @param dto
     * @return
     * @throws SettlementException
     */
    List<StatementRecivableEntity> queryPartnerReceivalbeStatement(StatementRecivableDto dto,int start ,int limit) throws  SettlementException;

    /**
     * 查询合伙人收款对账单总记录数
     * @param dto
     * @return
     * @throws SettlementException
     */
    Long queryPartnerReceivalbeStatementCount(StatementRecivableDto dto) throws SettlementException;

    /**
     * 根据对账单号查询对账单信息
     * @param billStatementNo
     * @return
     * @throws SettlementException
     */
    List<StatementRecivableDEntity> queryReceivalbeStatementByBillNo(String billStatementNo)throws SettlementException;

    /**
     * 删除对账的明细
     * @param id
     * @throws SettlementException
     */
    void deleteReceivableStatementById(List<String> id)throws SettlementException;

    /**
     * 收款对账单还款
     * @return
     * @throws SettlementException
     */
    String  partnerRepayment(BillStatementToPaymentQueryDto dto,CurrentInfo cInfo)throws  SettlementException;

    /**
     * 根据对账单号查询对账单的信息
     * @param statementNoList
     * @return
     * @throws SettlementException
     */
    StatementRecivableEntity  repaymentBillofStatementNo(List<String> statementNoList) throws SettlementException;

    /**
     * 添加对账单明细
     * @param dto
     * @throws SettlementException
     */
    void addReceivableStatementDetail(StatementRecivableDto dto)throws SettlementException;

    /**
     * 根据对账单号查询对账信息
     * @param list
     * @return
     * @throws SettlementException
     */
    List<StatementRecivableEntity> queryPartnerStatementList(List<String> list) throws  SettlementException;

	/**
	 * 作废还款单还原合伙人收款对账单中的金额
	 * @author gpz
	 * @date 2017年2月8日
	 */
	void updateStatementForDisableRepayment(
			StatementRecivableEntity stateReceivableEntity);

}
