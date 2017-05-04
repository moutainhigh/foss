package com.deppon.foss.module.settlement.pay.server.service.impl;/**
 * Created by 105762 on 2015/9/25.
 */

import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayableQueryForPkpService;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 105762
 */
public class PayableQueryForPkpService implements IPayableQueryForPkpService {
    IBillPayableService billPayableService;

    public BigDecimal queryClaimPayableBillByWaybill(String waybillNo) {
        if(StringUtils.isBlank(waybillNo)){
            throw new SettlementException("运单号不能为空");
        }

        BillPayableConditionDto dto = new BillPayableConditionDto();
        dto.setWaybillNo(waybillNo);
        dto.setActive(FossConstants.ACTIVE);
        String[] billTypes = {SettlementDictionaryConstants.BILL_CLAIM__TYPE__CLAIM};
        dto.setBillTypes(billTypes);
        List<BillPayableEntity> list =  billPayableService.queryBillPayableByCondition(dto);
        if(list.size() >0){
            BillPayableEntity entity = list.get(0);
            return entity.getAmount();
        } else {
            throw new SettlementException("未查询到数据或查询到多条数据");
        }
    }

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
    
}
