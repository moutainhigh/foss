package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.vo.AgentWaybillExceptionDto;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InternationalTrackingEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.AgentWaybillTrackDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExpressPushInfoDto;

public interface IAgentWaybillTrackService extends IService {

	void pushAgentWaybillNos();

	void addWaybillTrack(String agentWaybillNo,String agentCompanyCode,List<AgentWaybillTrackDto> trackList);
	
	void addWaybillTrackForInternal(String waybillNo,List<AgentWaybillTrackDto> trackList);
	
	void saveWaybillTrack();

	void addWaybillNos(List<String> waybillNoList);
	
	List<AgentWaybillExceptionDto> pushAgentWaybillNos(List<AgentWaybillNoRequestDto> agentWabillList);
	
	List<ExpressPushInfoDto>queryPushExpressListByWaybillNo(String waybillNo,String reasonCode);
	
	List<InternationalTrackingEntity> queryInterTrackingEntity(String waybillNo);

}
