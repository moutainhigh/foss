package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillResponseDto;

public interface IFOSSToAgentWaybillService extends IService{
	/**
	 * 
	 * @author nly
	 * @date 2015年2月3日 上午8:33:11
	 * @function
	 * @return
	 */
	public AgentWaybillResponseDto  pushAgentWaybillNo(AgentWaybillNoRequestDto dto);
	public AgentWaybillResponseDto  pushWaybillTrack(); 
}
