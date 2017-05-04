package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;

/**
 * 悟空补码时，到达应收网上支付还款单处理
 * @author 231434-foss-bieyexiong
 * @date 2016-10-3
 */
public interface IBillRepaymentComplementAutoDisableAndCreateService extends IService {

	/**
	 * 补码时自动作废 到达应收网上支付 还款单
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-3 
	 */
	void complementAutoDisableBillRepayment(BillRepaymentEntity entity,CurrentInfo cInfo);
	
	/**
	 * 补码时自动生成 到达应收网上支付 还款单(还款冲应收)
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-3 
	 */
	void complementAutoCreateBillRepayment(BillReceivableOnlineQueryDto queryDto,BillReceivableEntity receivableEntity);
}
