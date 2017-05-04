package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.dto.OppStatementDto;

/**
 * Created by 302307 on 2016/4/26.
 */
public interface IOppStatementService extends IService{

	/**
     * 根据传入参数查询对账单信息
     * @param dto
     * @return
     */
    OppStatementDto queryStatementsByCondition(OppStatementDto dto);

    /**
     * 根据对账单号查询对账单信息
     * @param dto
     * @return
     */
    
    OppStatementDto queryStatementByBillNos(OppStatementDto dto);

    /**
     * 根据对账单号查询对账单明细
     * @param dto
     * @return
     */
    OppStatementDto queryOppStatementDetailsByNos(OppStatementDto dto);

    /**
     * 根据对账单号查询对账单明细
     * @param dto
     * @return
     */
    OppStatementDto queryOppStatementDetailsAllByNos(OppStatementDto dto);

}
