package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.SendAgentCompanyRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagements.AgentCompanyInfo;
import com.deppon.foss.inteface.domain.usermanagements.SendAgentCompanyRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendAgentCompanyInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;

public class SendAgentCompanyInfoToWDGHService implements
		ISendAgentCompanyInfoToWDGHService {

	//日志
	private static final Logger log = LoggerFactory.getLogger(SendAgentCompanyInfoToWDGHService.class);
	
	//服务编码
	private static final String ESB_SERVICE_CODE ="ESB_FOSS2ESB_SYNC_AGENT_COMPANY";
	
	private ISyncESBSendService syncESBSendService;
	
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
			this.syncESBSendService = syncESBSendService;
		}	
	/**
	 * 
	 *<p>同步代理公司方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-4-1下午05:20:17
	 *@see
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	@Override
	public void syncAgentCompanyInfo(BusinessPartnerEntity dtos,
			String operateType) {
		//同步信息非空校验
				if(null != dtos){
					SendAgentCompanyRequest request = new SendAgentCompanyRequest();
					//服务编码
					AccessHeader accessHeader = new AccessHeader();
					accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
					accessHeader.setBusinessId(UUID.randomUUID().toString());
					accessHeader.setVersion("0.1");
					accessHeader.setBusinessDesc1("同步线路信息(new)");
					try {
						//转换对象
						request = transtoEsbObjects(dtos,operateType);
						log.info("开始调用 同步代理公司 TO WDGH：\n"+new SendAgentCompanyRequestTrans().fromMessage(request));
						syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
						log.info("结束调用 同步代理公司 TO WDGH：\n"+new SendAgentCompanyRequestTrans().fromMessage(request));
					} catch (ESBException e) {
						log.error(e.getMessage(), e);
						throw new SynchronousExternalSystemException("同步代理公司 "," 同步代理公司  发送数据到ESB错误");
					}
				}

	}

	/**
	 *<p>将foss对象转换成esb请求对象</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-4-1下午05:22:51
	 *@param dtos
	 *@param OperateType  操作类型
	 *@return
	 */
	private SendAgentCompanyRequest transtoEsbObjects(BusinessPartnerEntity dtos,
			String operateType) throws BusinessException{
		
		SendAgentCompanyRequest request = new SendAgentCompanyRequest();
		AgentCompanyInfo reqInfo =new AgentCompanyInfo();

		//非空校验
		if(null != dtos){
			reqInfo.setActive(dtos.getActive());
			reqInfo.setAgentCompanyCode(dtos.getAgentCompanyCode());
			reqInfo.setAgentCompanyName(dtos.getAgentCompanyName());
			reqInfo.setAgentCompanySort(dtos.getAgentCompanySort());
			reqInfo.setCityCode(dtos.getCityCode());
			reqInfo.setContact(dtos.getContact());
			reqInfo.setContactAddress(dtos.getContactAddress());
			reqInfo.setContactPhone(dtos.getContactPhone());
			reqInfo.setCreateDate(dtos.getCreateDate());
			reqInfo.setCreateUser(dtos.getCreateUser());
			reqInfo.setId(dtos.getId());
			reqInfo.setInterfaceServiceCode(null);
			reqInfo.setManagement(dtos.getManagement());
			reqInfo.setMobilePhone(dtos.getMobilePhone());
			reqInfo.setModifyDate(dtos.getModifyDate());
			reqInfo.setModifyUser(dtos.getModifyUser());
			reqInfo.setNotes(dtos.getNotes());
			reqInfo.setProvCode(dtos.getProvCode());
			reqInfo.setSimplename(dtos.getSimplename());
			if( null != dtos.getVersionNo())
				reqInfo.setVersionNO(dtos.getVersionNo());
			reqInfo.setVirtualCode(dtos.getVirtualCode());
			reqInfo.setWriteOffType(null);
		}
		
		//将数据添加到request对象中 				
		request.setAgentCompanyInfo(reqInfo);
		request.setOperate(operateType);
		return request;
	}
}
