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
import com.deppon.esb.pojo.transformer.jaxb.FreightRouteLineInfoRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagements.FreightRouteLineInfo;
import com.deppon.foss.inteface.domain.usermanagements.FreightRouteLineInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendFreightRouteLineInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.util.CollectionUtils;

public class SendFreightRouteLineInfoToWDGHService implements
		ISendFreightRouteLineInfoToWDGHService {
	
	//日志
	private static final Logger log = LoggerFactory.getLogger(SendFreightRouteLineInfoToWDGHService.class);
	
	//服务编码
	private static final String ESB_SERVICE_CODE ="ESB_FOSS2ESB_SYNC_FREIGHT_ROUTE_LINE";
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
	 *<p>同步走货路径线路方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-19下午03:51:07
	 *@see
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	@Override
	public void syncFreightRouteLineInfo(List<FreightRouteLineEntity> dtos,
			String operateType) {
		
		//同步信息非空校验
		if(null != dtos){
			FreightRouteLineInfoRequest request = new FreightRouteLineInfoRequest();
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setVersion("0.1");
			accessHeader.setBusinessDesc1("同步线段信息");
			long startTime = System.currentTimeMillis();
			EsbErrorLogging erlog = new EsbErrorLogging();
			try {
				//转换对象
				request = transtoEsbObjects(dtos,operateType);
				erlog.setMethodDesc("同步走货路径线路 TO WDGH");
				erlog.setParamenter(new FreightRouteLineInfoRequestTrans().fromMessage(request));
				erlog.setRequestSystem("ESB");
				erlog.setRequestTime(new Date());
				erlog.setMethodName(this.getClass().getName()+".SyncFreightRouteLineInfo");
				log.info("开始调用 同步走货路径线路 TO WDGH：\n"+new FreightRouteLineInfoRequestTrans().fromMessage(request));
				syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
				long responseTime = (System.currentTimeMillis()-startTime); 
				if(responseTime>FossConstants.MAX_RESPONSE_TIME){
					erlog.setErrorMessage("响应超时");
					erlog.setCreateTime(new Date());
					erlog.setResponseTime(responseTime);
					esbErrorLoggingDao.addErrorMessage(erlog);
				}
				log.info("结束调用 同步走货路径线路 TO WDGH：\n"+new FreightRouteLineInfoRequestTrans().fromMessage(request));
			} catch (ESBException e) {
				log.error(e.getMessage(), e);
				erlog.setResponseTime(System.currentTimeMillis()-startTime);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				erlog.setCreateTime(new Date());
				e.printStackTrace(pw);
				erlog.setErrorMessage(sw.toString());
				esbErrorLoggingDao.addErrorMessage(erlog);
				throw new SynchronousExternalSystemException(" 同步走货路径线路"," 同步走货路径线路 发送数据到ESB错误");
			}
		}
	}

	/**
	 *<p>将foss对象转换成esb请求对象</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-19下午03:56:41
	 *@param dtos
	 *@param OperateType  操作类型
	 *@return
	 */
	private FreightRouteLineInfoRequest transtoEsbObjects(
			List<FreightRouteLineEntity> dtos, String operateType) throws BusinessException{
		
		FreightRouteLineInfoRequest request = new FreightRouteLineInfoRequest();
		List<FreightRouteLineInfo> list=new ArrayList<FreightRouteLineInfo>();

		//非空校验
		if(CollectionUtils.isNotEmpty(dtos)){
			for (FreightRouteLineEntity freightRouteLineEntity : dtos) {
			FreightRouteLineInfo reqInfo = new FreightRouteLineInfo();
			reqInfo.setActive(freightRouteLineEntity.getActive());
			if(null != freightRouteLineEntity.getAging())
				reqInfo.setAging(freightRouteLineEntity.getAging());
			if(null != freightRouteLineEntity.getClasses())
				reqInfo.setClasses(freightRouteLineEntity.getClasses());
			reqInfo.setCreateDate(freightRouteLineEntity.getCreateDate());
			reqInfo.setCreateUser(freightRouteLineEntity.getCreateUser());
			reqInfo.setDestinationOrganizationCode(freightRouteLineEntity.getDestinationOrganizationCode());
			reqInfo.setFreightRouteVirtualCode(freightRouteLineEntity.getFreightRouteVirtualCode());
			reqInfo.setId(freightRouteLineEntity.getId());
			reqInfo.setLineVirtualCode(freightRouteLineEntity.getLineVirtualCode());
			reqInfo.setModifyDate(freightRouteLineEntity.getModifyDate());
			reqInfo.setModifyUser(freightRouteLineEntity.getModifyUser());
			reqInfo.setOrginalOrganizationCode(freightRouteLineEntity.getOrginalOrganizationCode());
			if(null != freightRouteLineEntity.getPassbyAging())			
				reqInfo.setPassbyAging(freightRouteLineEntity.getPassbyAging());
			if(null != freightRouteLineEntity.getSequence())
				reqInfo.setSequence(freightRouteLineEntity.getSequence());
			if(null != freightRouteLineEntity.getVersion())			
				reqInfo.setVersionNO(freightRouteLineEntity.getVersion());
			reqInfo.setVirtualCode(freightRouteLineEntity.getVirtualCode());
			
			list.add(reqInfo);
		}
		}
		//将数据添加到request对象中 
		request.getFreightRouteLineInfo().addAll(list);
		request.setOperate(operateType);
		return request;
	}

	
	
}
