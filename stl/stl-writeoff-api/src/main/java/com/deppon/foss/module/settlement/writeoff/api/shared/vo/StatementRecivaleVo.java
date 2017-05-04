package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import com.deppon.foss.module.settlement.writeoff.api.server.dao.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementRecivableDto;

import java.io.Serializable;

/**
 * @author foss-youkun
 * @date 2016/1/22
 */
public class StatementRecivaleVo implements Serializable {

    /**
     * VO类序列号
     */
    private static final long serialVersionUID = 7379912636078020208L;


    /**
     * 对账的DTO
     */
    private StatementRecivableDto statementRecivableDto;

    /**
     * 还款对账单
     */
    private BillStatementToPaymentQueryDto billStatementToPaymentQueryDto;

    /**
     * 还款用到的
     */
    private BillRepaymentManageDto billRepaymentManageDto;

    public StatementRecivableDto getStatementRecivableDto() {
        return statementRecivableDto;
    }

    public void setStatementRecivableDto(StatementRecivableDto statementRecivableDto) {
        this.statementRecivableDto = statementRecivableDto;
    }

    public BillStatementToPaymentQueryDto getBillStatementToPaymentQueryDto() {
        return billStatementToPaymentQueryDto;
    }

    public void setBillStatementToPaymentQueryDto(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto) {
        this.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
    }

    public BillRepaymentManageDto getBillRepaymentManageDto() {
        return billRepaymentManageDto;
    }

    public void setBillRepaymentManageDto(BillRepaymentManageDto billRepaymentManageDto) {
        this.billRepaymentManageDto = billRepaymentManageDto;
    }
}
