package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.vo.AgentWaybillExceptionDto;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.AgentWaybillNoEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.AgentWaybillTrackEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InternationalTrackingEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExpressPushInfoDto;

public interface IAgentWaybillTrackDao{

	List<AgentWaybillNoEntity> queryWaybillNo(int count);

	int addOneTrack(AgentWaybillTrackEntity entity);

	int updateWaybillNoInfo(AgentWaybillNoEntity waybill);

	int addWaybillNo(AgentWaybillNoEntity entity);
	
	int addInternationTrackings(InternationalTrackingEntity entity);

	List<AgentWaybillNoRequestDto> queryAgentWaybillNoList(Date bizDate);

	int updatePrintWaybillInfo(String agentWaybillNo);

	int addExceptionInfo(AgentWaybillExceptionDto dto);

	/**
	 * 根据代理单号，外部代理公司编号更新订阅状态
	 * @param agentWaybillNo
	 * @param kd100CompanyCode
	 * @author 257220
	 * @date 2015-7-29下午2:27:18
	 */
	int updatePrintWaybillInfoByAgentInfo(String agentWaybillNo, String kd100CompanyCode);
	
	/**
	 * @author gongjp
	 * @date 2015年10月19日 上午10:24:34
	 * @function 保存异常信息
	 * @param expressPushInfoDto
	 */
	int savePushExpressInfo(ExpressPushInfoDto expressPushInfoDto);
	/**
	 * 根据运单号和拒收原因编码查询
	 * @param expressPushInfoDto
	 * @return
	 */
	List<ExpressPushInfoDto>queryPushExpressListByWaybillNo(String waybillNo,String reasonCode);
	
	/**
	 * @author alfred
	 * @date 2016-04-22 22:01:23
	 * @function 查询国际件轨迹
	 * @param waybillNo
	 */
	List<InternationalTrackingEntity> queryInterTrackingsEntity(String waybillNo);
	
	/**
	 * 删除运单轨迹信息 
	 * @author 352203
	 */
	int deleteInternationTrackings(String waybillNo);
}
