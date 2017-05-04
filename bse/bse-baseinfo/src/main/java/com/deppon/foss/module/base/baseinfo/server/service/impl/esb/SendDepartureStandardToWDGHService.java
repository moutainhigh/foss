package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.DepartureStandardInfoRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagements.DepartureStandardInfo;
import com.deppon.foss.inteface.domain.usermanagements.DepartureStandardInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendDepartureStandardToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;

public class SendDepartureStandardToWDGHService implements ISendDepartureStandardToWDGHService{
	
	//日志
	private static final Logger log = LoggerFactory.getLogger(SendDepartureStandardToWDGHService.class);
	
	//服务编码
	private static final String ESB_SERVICE_CODE ="ESB_FOSS2ESB_SYNC_DEPARTURE_STD";
	
	private ISyncESBSendService syncESBSendService;
	
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
			this.syncESBSendService = syncESBSendService;
		}		
	/**
	 * 
	 *<p>同步发车标准方法</p>
	 *@author 269231 -qirongshseng
	 *@date 2016-3-17下午05:23:07
	 *@see
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	@Override
	public void syncDepartureStandard(DepartureStandardEntity dtos,
			String operateType) {	
			//同步信息非空校验
			if(null != dtos){
				DepartureStandardInfoRequest request = new DepartureStandardInfoRequest();
				//服务编码
				AccessHeader accessHeader = new AccessHeader();
				accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
				accessHeader.setBusinessId(UUID.randomUUID().toString());
				accessHeader.setVersion("0.1");
				accessHeader.setBusinessDesc1("同步发车标准信息");
				try {
					//转换对象
					request = transtoEsbObjects(dtos,operateType);
					log.info("开始调用 同步发车标准 TO WDGH：\n"+new DepartureStandardInfoRequestTrans().fromMessage(request));
					syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
					log.info("结束调用 同步发车标准 TO WDGH：\n"+new DepartureStandardInfoRequestTrans().fromMessage(request));
				} catch (ESBException e) {
					log.error(e.getMessage(), e);
					throw new SynchronousExternalSystemException(" 同步发车标准"," 同步发车标准 发送数据到ESB错误");
				}
			}
	
	}

	/**
	 *<p>将foss对象转换成esb请求对象</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-17下午05:26:41
	 *@param dtos
	 *@param OperateType  操作类型
	 *@return
	 */
	private DepartureStandardInfoRequest transtoEsbObjects (DepartureStandardEntity dtos,
			String operateType) throws BusinessException{
		// TODO Auto-generated method stub
		DepartureStandardInfoRequest request = new DepartureStandardInfoRequest();
		DepartureStandardInfo reqInfo = new DepartureStandardInfo();
		
		if(null != dtos){
			reqInfo.setActive(dtos.getActive());
			if(null != dtos.getArriveDay())
				reqInfo.setArriveDay(dtos.getArriveDay());
			reqInfo.setArriveTime(dtos.getArriveTime());
			reqInfo.setCreateDate(dtos.getCreateDate());
			reqInfo.setCreateUser(dtos.getCreateUser());
			if(null != dtos.getDeadDay())
				reqInfo.setDeadDay(dtos.getDeadDay());
			reqInfo.setDeadTime(dtos.getDeadTime());
			if(null != dtos.getOrder())	
				reqInfo.setFrequencyNo(dtos.getOrder());
			reqInfo.setId(dtos.getId());
			reqInfo.setLeaveTime(dtos.getLeaveTime());
			reqInfo.setLineVirtualCode(dtos.getLineVirtualCode());
			reqInfo.setModifyDate(dtos.getModifyDate());
			reqInfo.setModifyUser(dtos.getModifyUser());
			reqInfo.setNotes(dtos.getNotes()); 
			reqInfo.setProductType(dtos.getProductType());
			if(null != dtos.getVersion())				
				reqInfo.setVersionNO(dtos.getVersion());
			reqInfo.setVirtualCode(dtos.getVirtualCode());
			if(null != dtos.getOrder())	
			reqInfo.setFrequencyNo(dtos.getOrder());
		}
		
		//将数据添加到request对象中 
		request.setDepartureStandardInfo(reqInfo);
		request.setOperate(operateType);
		
		return request;
	}

	
}
