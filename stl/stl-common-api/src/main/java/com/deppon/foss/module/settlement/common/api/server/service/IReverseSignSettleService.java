package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.AutoReverseSignSettleRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSCodAuditEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSResverSettleRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSReverseSettlRequest;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VTSCodAuditDto;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-06-12 15:52:20
 * vts自动反签收反结清service
 *
 */
public interface IReverseSignSettleService {
	
	//校验反签收
	String checkReverseSign(AutoReverseSignSettleRequestEntity dto);
	
	//校验反结清
	String checkReverseSettle(AutoReverseSignSettleRequestEntity dto);
	
	//查询代收货款支付审核
	List<VTSCodAuditEntity> queryCodAuditEntityByCondition(VTSCodAuditDto dto);
	
	//校验应付单
	List<String> checkBillPayableByReverseConfirmIncome(List<BillPayableEntity> billPayables, List<String> sourcesStatementNos);
	
	//(到付运费结转临欠/月结)根据红应收单动作，作废原应收单信息 生成新红单应收单信息、生成蓝单应收单信息
    void writeBackBillReceivableByPayable(BillReceivableEntity entity,PaymentSettlementDto dto, CurrentInfo currentInfo) ;
    
    //反结清状态为N：反实际承运表的结清状态为N 
    void updateSettleStatus(VTSRepaymentEntity repayment, String state);
    
    //反签收查询操作处理
    List<VTSResverSettleRepaymentEntity> handleReverseSign(VTSReverseSettlRequest request);
    
}
