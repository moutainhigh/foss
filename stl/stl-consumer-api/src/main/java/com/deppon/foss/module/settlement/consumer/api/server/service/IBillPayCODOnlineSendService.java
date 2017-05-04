package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;

/**
 * 
 * 将代收货款数据发送给银企
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-19 上午10:34:13
 */
public interface IBillPayCODOnlineSendService {

	/**
	 * 
	 * 发送代收货款到银企
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 上午10:38:33
	 */
	void billPayCODOnlineSend(String batchNo, List<CODMergeDto> mergeCodPayableDtoList) throws Exception;
}
