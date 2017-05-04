package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.NetworkGroupMixInfoRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagements.NetworkGroupMixInfo;
import com.deppon.foss.inteface.domain.usermanagements.NetworkGroupMixInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendNetGroupMixInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.util.CollectionUtils;

public class SendNetGroupMixInfoToWDGHService implements
		ISendNetGroupMixInfoToWDGHService {
	//日志
	private static final Logger log = LoggerFactory.getLogger(SendNetGroupMixInfoToWDGHService.class);
	
	//服务编码
	private static final String ESB_SERVICE_CODE ="ESB_FOSS2ESB_SYNC_NET_GROUP";
	 private IEsbErrorLoggingDao esbErrorLoggingDao;
		public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
			this.esbErrorLoggingDao = esbErrorLoggingDao;
		}

	private ISyncESBSendService syncESBSendService;
		
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
			this.syncESBSendService = syncESBSendService;
	}
	/**
	 * 
	 *<p>同步网点组信息方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-19下午03:23:07
	 *@see
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	@Override
	public void syncNetGroupMixInfo(
			List<NetGroupMixEntity> dtos, String operateType) {
		
		//同步信息非空校验
		if(null != dtos){
			NetworkGroupMixInfoRequest request = new NetworkGroupMixInfoRequest();
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setBusinessDesc1("同步网点组信息");
			accessHeader.setVersion("0.1");
			long startTime = System.currentTimeMillis();
			EsbErrorLogging erlog = new EsbErrorLogging();
			try {
				//转换对象
				request = transtoEsbObjects(dtos,operateType);
				erlog.setParamenter(new NetworkGroupMixInfoRequestTrans().fromMessage(request));
				erlog.setRequestSystem("ESB");
				erlog.setMethodDesc("同步网点组信息TO WDGH");
				erlog.setRequestTime(new Date());
				erlog.setMethodName(this.getClass().getName()+".SyncNetGroupMixInfo");
				log.info("开始调用 同步网点组信息TO WDGH：\n"+new NetworkGroupMixInfoRequestTrans().fromMessage(request));
				syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
				 long responseTime = (System.currentTimeMillis()-startTime); 
					if(responseTime>FossConstants.MAX_RESPONSE_TIME){
						erlog.setErrorMessage("响应超时");
						erlog.setCreateTime(new Date());
						erlog.setResponseTime(responseTime);
						esbErrorLoggingDao.addErrorMessage(erlog);
					}
				log.info("结束调用 同步网点组信息TO WDGH：\n"+new NetworkGroupMixInfoRequestTrans().fromMessage(request));
			} catch (ESBException e) {
				log.error(e.getMessage(), e);
				erlog.setResponseTime(System.currentTimeMillis()-startTime);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				erlog.setCreateTime(new Date());
				e.printStackTrace(pw);
				erlog.setErrorMessage(sw.toString());
				esbErrorLoggingDao.addErrorMessage(erlog);
				throw new SynchronousExternalSystemException(" 同步网点组信息给网点规划"," 同步网点组信息 发送数据到ESB错误");
			}
		}
		
	}
	
	/**
	 *<p>将foss线路信息对象转换成esb请求对象(新)</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-19下午03:26:41
	 *@param dtos
	 *@param OperateType  操作类型
	 *@return
	 */
	private NetworkGroupMixInfoRequest transtoEsbObjects(
			List<NetGroupMixEntity> dtos,String operateType) throws BusinessException{
		
		NetworkGroupMixInfoRequest request = new NetworkGroupMixInfoRequest();
		List<NetworkGroupMixInfo> list=new ArrayList<NetworkGroupMixInfo>();

		//非空校验
		if(CollectionUtils.isNotEmpty(dtos)){
			for (NetGroupMixEntity netGroupMixEntity : dtos) {
				NetworkGroupMixInfo reqInfo = new NetworkGroupMixInfo();
				reqInfo.setActive(netGroupMixEntity.getActive());
				reqInfo.setCreateDate(netGroupMixEntity.getCreateDate());
				reqInfo.setCreateUser(netGroupMixEntity.getCreateUser());
				reqInfo.setFreightRouteVirtualCode(netGroupMixEntity.getFreightRouteVirtualCode());
				reqInfo.setId(netGroupMixEntity.getId());
				reqInfo.setIsExpNetworkGroup(netGroupMixEntity.getExpNetworkGroup());
				reqInfo.setModifyDate(netGroupMixEntity.getModifyDate());
				reqInfo.setModifyUser(netGroupMixEntity.getModifyUser());
				reqInfo.setNetGroupCode(netGroupMixEntity.getNetGroupCode());
				reqInfo.setOrganizationCode(netGroupMixEntity.getOrgCode());
				reqInfo.setOrganizationType(netGroupMixEntity.getOrgType());	
				if(null != netGroupMixEntity.getVersion())
					reqInfo.setVersionNO(netGroupMixEntity.getVersion());
				reqInfo.setVirtualCode(netGroupMixEntity.getVirtualCode());	
				list.add(reqInfo);
		}
		}
		//将数据添加到request对象中 
		 request.getNetworkGroupMixInfo().addAll(list);
		request.setOperate(operateType);
		return request;
	}
}
