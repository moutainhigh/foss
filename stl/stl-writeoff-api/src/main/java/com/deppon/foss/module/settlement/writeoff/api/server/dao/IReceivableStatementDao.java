package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementRecivableDto;

import java.util.List;

/**
 * @author foss-youkun
 * @date 2016/1/19
 */
public interface IReceivableStatementDao {

    /**
     * 添加对账单
     * @param entity
     * @return
     */
    int AddRecivalbeStatement(StatementRecivableEntity entity);

    /**
     * 更新对账单的状态
     * @param statementStatus
     * @return
     */
    int updateStatementByStatementStatus(String statementStatus,List<String> statementBillNo);

    /**
     * 查询合伙人收款对账单
     * @param dto
     * @return
     */
    List<StatementRecivableEntity> queryPartnerReceivalbeStatement(StatementRecivableDto dto,int start ,int limit);

    /**
     * 查询合伙人对账单总记录数
     * @param dto
     * @return
     */
    Long queryPartnerReceivalbeStatementCount(StatementRecivableDto dto);

    /**
     * 根据对账单明细ID查询对账单状态
     * @param id
     * @return
     */
    StatementRecivableEntity queryReceivalbeStatementById(String id);

    /**
     * 根据对账单号查询对账单信息
     * @param statementNo
     * @return
     */
    StatementRecivableEntity queryPartnerReceivableByStatemenNo(String statementNo);

    /**
     *还款时更新对账单
     * @param entity
     * @return
     */
    int updateStatementRecivableEntity(StatementRecivableEntity entity);

    /***
     * 删除对账单明细时更新对账单
     */
    int updateByStatementNo(StatementRecivableEntity entity);


    /**
     * 根据对账单查询对账单信息
     * @param list
     * @return
     */
    List<StatementRecivableEntity> queryPartnerStatementList(List<String> list);

}
