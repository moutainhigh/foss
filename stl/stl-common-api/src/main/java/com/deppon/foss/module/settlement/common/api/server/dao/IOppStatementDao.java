package com.deppon.foss.module.settlement.common.api.server.dao;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.OppStatementDto;

import java.util.List;

/**
 * Created by 302307 on 2016/4/26.
 */
public interface IOppStatementDao {

    /**
     * 根据条件查询对账单信息
     * @param dto
     * @return
     */
    List<StatementOfAccountEntity> queryOppStatementByCondition(OppStatementDto dto);

    /**
     * 根据条件查询对账单信息总条数
     * @param dto
     * @return
     */
    int queryOppStatementByConditionCount(OppStatementDto dto);

    /**
     * 根据条件查询对账单号集合
     * @param dto
     * @return
     */
    List<String> queryOppStatementBillNosByCondition(OppStatementDto dto);

    /**
     * 根据对账单号查询对账单信息
     * @param dto
     * @return
     */
    List<StatementOfAccountEntity> queryOppStatementByNo(OppStatementDto dto);

    /**
     * 根据对账单号查询对账单明细
     * @param list
     * @return
     */
    List<StatementOfAccountDEntity> queryOppStatementDetailsByNos(List<String> list);

    /**
     * 根据对账单号查询对账单明细
     * @param dto
     * @return
     */
    List<StatementOfAccountDEntity> queryOppStatementDetailsByNo(OppStatementDto dto);

    /**
     * 查询对账单的总条数
     * @param dto
     * @return
     */
    int queryOppStatementDetailsByNoCount(OppStatementDto dto);

    /**
     * 根据对账单号查询对账单明细
     * @param dto
     * @return
     */
    List<StatementOfAccountDEntity> queryOppStatementDetailsAllByNo(OppStatementDto dto);

}
