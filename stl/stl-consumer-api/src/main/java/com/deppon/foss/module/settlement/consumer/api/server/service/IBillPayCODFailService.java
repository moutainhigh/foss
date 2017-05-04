package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AuditResultEnum;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;

/**
 * 
 * 汇款失败审核确认服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-18 下午2:07:40
 */
public interface IBillPayCODFailService extends IService {

	/**
	 * 
	 * 汇款失败审核查询
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:42:23
	 */
	List<CODDto> queryBillCODPayFailed(int offset, int limit)
			throws SettlementException;

	/**
	 * 
	 * 汇款失败审核查询大小，合计金额
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午5:26:36
	 */
	CODDto queryBillCODPayFailedSize() throws SettlementException;

	/**
	 * 
	 * 处理付款失败确认
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-24 上午10:21:19
	 */
	void processPaymentFailed(List<String> entityIds,
			AuditResultEnum auditResult, CurrentInfo currentInfo)
			throws SettlementException;

}
