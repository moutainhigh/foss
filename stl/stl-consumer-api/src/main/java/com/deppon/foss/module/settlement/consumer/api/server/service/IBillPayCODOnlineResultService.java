package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODOnlineResultDto;

/**
 * 
 * 代收货款线上汇款处理结果服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-26 上午9:22:53
 */
public interface IBillPayCODOnlineResultService extends IService {

	/**
	 * 
	 * 处理代收货款线上汇款结果
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 下午5:21:51
	 */
	void processPayResultService(List<BillCODOnlineResultDto> results,
			CurrentInfo currentInfo) throws SettlementException;

}
