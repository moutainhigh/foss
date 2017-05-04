package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.FreightRouteInfoRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagements.FreightRouteInfo;
import com.deppon.foss.inteface.domain.usermanagements.FreightRouteInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendFreightRouteInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
	
public class SendFreightRouteInfoToWDGHService implements ISendFreightRouteInfoToWDGHService {
	
	//日志
	private static final Logger log = LoggerFactory.getLogger(SendFreightRouteInfoToWDGHService.class);
	
	//服务编码
	private static final String ESB_SERVICE_CODE ="ESB_FOSS2ESB_SYNC_FREIGHT_ROUTE";
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
	 *@date 2016-3-18上午11:55:07
	 *@see
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	@Override
	public void syncFreightRouteInfo(List<FreightRouteEntity> dtos, String operateType) {
		//同步信息非空校验
		if(null != dtos){
			FreightRouteInfoRequest request = new FreightRouteInfoRequest();
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setVersion("0.1");
			accessHeader.setBusinessDesc1("同步线路信息(new)");
			long startTime = System.currentTimeMillis();
			EsbErrorLogging errlog = new EsbErrorLogging();
			try{
			//转换对象
			request = transtoEsbObjects(dtos,operateType);
			errlog.setParamenter(new FreightRouteInfoRequestTrans().fromMessage(request));
			errlog.setRequestSystem("ESB");
			errlog.setRequestTime(new Date());
			errlog.setMethodDesc("同步走货路径 TO WDGH");
			errlog.setMethodName(this.getClass().getName()+".SyncFreightRouteInfo");
				log.info("开始调用 同步走货路径 TO WDGH：\n"+new FreightRouteInfoRequestTrans().fromMessage(request));
				syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
				log.info("结束调用 同步走货路径 TO WDGH：\n"+new FreightRouteInfoRequestTrans().fromMessage(request));
				 long responseTime = System.currentTimeMillis()-startTime; 
					if(responseTime>FossConstants.MAX_RESPONSE_TIME){
						errlog.setErrorMessage("响应超时");
						errlog.setResponseTime(responseTime);
						errlog.setCreateTime(new Date());
						esbErrorLoggingDao.addErrorMessage(errlog);
					}
			} catch (ESBException e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				errlog.setResponseTime((System.currentTimeMillis()-startTime));
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				errlog.setCreateTime(new Date());
				errlog.setErrorMessage(sw.toString());
				esbErrorLoggingDao.addErrorMessage(errlog);
			}
		}

		
	}
	
	/**
	 *<p>将foss对象转换成esb请求对象</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-19下午02:16:41
	 *@param dtos
	 *@param OperateType  操作类型
	 *@return
	 */
	private FreightRouteInfoRequest transtoEsbObjects(List<FreightRouteEntity> dtos,
			String operateType) throws BusinessException{
		
		FreightRouteInfoRequest request = new FreightRouteInfoRequest();		
		List<FreightRouteInfo> list =new ArrayList<FreightRouteInfo>();
		//非空校验
		if(CollectionUtils.isNotEmpty(dtos)){
			for (FreightRouteEntity freightRouteEntity : dtos) {
				FreightRouteInfo reqInfo = new FreightRouteInfo();
			  	reqInfo.setActive(freightRouteEntity.getActive());
			  	reqInfo.setCreateDate(freightRouteEntity.getCreateDate());
			  	reqInfo.setCreateUser(freightRouteEntity.getCreateUser());
			  	reqInfo.setDestinationOrganizationCode(freightRouteEntity.getDestinationOrganizationCode());
			  	reqInfo.setDestinationOrgnizationType(freightRouteEntity.getDestinationType());
			  	reqInfo.setDoPacking(freightRouteEntity.getDoPacking());
			  	reqInfo.setOriginalOrganizationCode(freightRouteEntity.getOrginalOrganizationCode());
			  	reqInfo.setOriginalOrgnizationType(freightRouteEntity.getOrginalType());
			  	reqInfo.setPackingOrganizationCode(freightRouteEntity.getPackingOrganizationCode());
			  	//数据库中未使用此字段
				//reqInfo.setTransportLevel(null);
			  	reqInfo.setTransType(freightRouteEntity.getTransType());
			  	reqInfo.setValid(freightRouteEntity.getValid());
			  	reqInfo.setId(freightRouteEntity.getId());
			  	reqInfo.setModifyDate(freightRouteEntity.getModifyDate());
			  	reqInfo.setModifyUser(freightRouteEntity.getModifyUser());
			  	reqInfo.setNotes(freightRouteEntity.getNotes());
				if(null != freightRouteEntity.getVersion())
					reqInfo.setVersionNO(freightRouteEntity.getVersion());
			  	reqInfo.setVirtualCode(freightRouteEntity.getVirtualCode());
				if(null != freightRouteEntity.getAging())			  	
					reqInfo.setAging(freightRouteEntity.getAging());
			  	reqInfo.setDefaultRoute(freightRouteEntity.getDefaultRoute());
			  	
			  	list.add(reqInfo);
		}
		}
		//将数据添加到request对象中 				
		request.getFreightRouteInfo().addAll(list);
		request.setOperate(operateType);
		return request;
	}
}	

