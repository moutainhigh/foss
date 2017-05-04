package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.math.BigDecimal;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.LineItemInfoRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagements.LineItemInfo;
import com.deppon.foss.inteface.domain.usermanagements.LineItemInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendLineItemInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;

public class SendLineItemInfoToWDGHService implements
		ISendLineItemInfoToWDGHService {
	//日志
	private static final Logger log = LoggerFactory.getLogger(SendDepartureStandardToWDGHService.class);
	
	//服务编码
	private static final String ESB_SERVICE_CODE ="ESB_FOSS2ESB_SYNC_LINEITEM";
	
	private ISyncESBSendService syncESBSendService;
	
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
			this.syncESBSendService = syncESBSendService;
	}
	/**
	 * 
	 *<p>同步线段信息方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-18下午04:23:07
	 *@see
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	@Override
	public void syncLineItemInfo(LineItemEntity dtos, String operateType) {
		
		//同步信息非空校验
		if(null != dtos){
			LineItemInfoRequest request = new LineItemInfoRequest();
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setVersion("0.1");
			accessHeader.setBusinessDesc1("同步线段信息");
			try {
				//转换对象
				request = transtoEsbObjects(dtos,operateType);
				log.info("开始调用 同步线段信息 TO WDGH：\n"+new LineItemInfoRequestTrans().fromMessage(request));
				syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
				log.info("结束调用 同步线段信息 TO WDGH：\n"+new LineItemInfoRequestTrans().fromMessage(request));
			} catch (ESBException e) {
				log.error(e.getMessage(), e);
				throw new SynchronousExternalSystemException(" 同步线段信息"," 同步线段信息 发送数据到ESB错误");
			}
		}

	}
	
	/**
	 *<p>将foss对象转换成esb请求对象</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-18下午04:26:41
	 *@param dtos
	 *@param OperateType  操作类型
	 *@return
	 */
	private LineItemInfoRequest transtoEsbObjects(
			LineItemEntity dtos, String operateType) throws BusinessException{
		
		LineItemInfoRequest request = new LineItemInfoRequest();
		LineItemInfo reqInfo =new LineItemInfo();
		//非空校验
		if(null != dtos){

			  	reqInfo.setActive(dtos.getActive());
			  	if(null != dtos.getCommonAging())
			  		reqInfo.setCommonAging(dtos.getCommonAging());
			  	reqInfo.setCreateDate(dtos.getCreateDate());
			  	reqInfo.setCreateUser(dtos.getCreateUser());
			  	reqInfo.setDestinationCityCode(dtos.getDestinationCityCode());
			  	reqInfo.setDestinationOrganizationCode(dtos.getDestinationOrganizationCode());
			  	//reqInfo.setDestinationOrganizationName(dtos.getDestinationOrganizationName());
			  	if(null != dtos.getDistance())
			  		reqInfo.setDistance(new BigDecimal(dtos.getDistance()));
			  	if(null != dtos.getFastAging())
			  		reqInfo.setFastAging(dtos.getFastAging());
			  	reqInfo.setId(dtos.getId());
			  	reqInfo.setLineVirtualCode(dtos.getLineVirtualCode());
			  	reqInfo.setModifyDate(dtos.getModifyDate());
			  	reqInfo.setModifyUser(dtos.getModifyUser());
			  	reqInfo.setNotes(dtos.getNotes());
			  	reqInfo.setOriginalCityCode(dtos.getOrginalCityCode());
			  	//reqInfo.setOriginalCityName(dtos.getOrginalCityName());
			  	reqInfo.setOriginalOrganizationCode(dtos.getOrginalOrganizationCode());
			  	//reqInfo.setOriginalOrganizationName(dtos.getOrginalOrganizationName());			  	
			  	if(null != dtos.getPassbyAging())
			  		reqInfo.setPassbyAging(dtos.getPassbyAging());
				if(null != dtos.getSequence())
					reqInfo.setSequence(dtos.getSequence().toString());
				if(null != dtos.getVersion())
					reqInfo.setVersionNO(dtos.getVersion());
			  	reqInfo.setVirtualCode(dtos.getVirtualCode());
		}
		
		//将数据添加到request对象中 				
		request.setLineItemInfo(reqInfo);
		request.setOperate(operateType);
		return request;
	}

}
