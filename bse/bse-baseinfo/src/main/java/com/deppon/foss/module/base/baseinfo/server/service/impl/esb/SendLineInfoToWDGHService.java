package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.SendLineRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.SendLinesRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagement.LineInfo;
import com.deppon.foss.inteface.domain.usermanagement.SendLineRequest;
import com.deppon.foss.inteface.domain.usermanagements.LinesInfo;
import com.deppon.foss.inteface.domain.usermanagements.SendLinesRequest;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendLineInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 同步给网点规划系统线路信息JMS 客户端
 * @author 130566
 * 
 *
 */
public class SendLineInfoToWDGHService implements ISendLineInfoToWDGHService{
	//日志
	private static final Logger log = LoggerFactory.getLogger(SendLineInfoToWDGHService.class);
	//组织接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	 private IEsbErrorLoggingDao esbErrorLoggingDao;
		public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
			this.esbErrorLoggingDao = esbErrorLoggingDao;
		}
	//服务编码
	private static final String ESB_SERVICE_CODE ="ESB_FOSS2ESB_QUERY_ROUTE_INFO";
	
	//WDGH线路信息服务编码
	private static final String ESB_SERVICE_LINES_CODE = "ESB_FOSS2ESB_WDGH_SYNC_LINES_FM_FOSS";

	private ISyncESBSendService syncESBSendService;
	
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
			this.syncESBSendService = syncESBSendService;
	}
	
	/**
	 * 
	 *<p>同步信息方法</p>
	 *@author 130566 -ZengJunfan
	 *@date 2014-12-12上午11:23:07
	 *@see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendLineInfoToWDGHService#SyncLineInfo(java.util.List)	
	 *@param dtos
	 */
	@Override
	public void syncLineInfo(List<LineEntity> dtos,String operateType) {
		//同步信息非空校验
		if(CollectionUtils.isNotEmpty(dtos)){
			SendLineRequest request = new SendLineRequest();
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setBusinessDesc1("同步线路信息");
			accessHeader.setVersion("0.1");
			try {
				//转换对象
				request = transtoEsbObject(dtos,operateType);
				log.info("开始调用 同步线路信息TO WDGH：\n"+new SendLineRequestTrans().fromMessage(request));
				ESBJMSAccessor.asynReqeust(accessHeader, request);
				log.info("结束调用 同步线路信息TO WDGH：\n"+new SendLineRequestTrans().fromMessage(request));
			} catch (ESBException e) {
				log.error(e.getMessage(), e);
				throw new SynchronousExternalSystemException(" 同步线路信息给网点规划"," 同步线路信息 发送数据到ESB错误");
			}
		}
	}
	/**
	 *<p>将foss对象转换成esb请求对象</p>
	 *@author 130566 -ZengJunfan
	 *@date 2014-12-12上午11:26:41
	 *@param dtos
	 *@return
	 */
	private SendLineRequest transtoEsbObject(List<LineEntity> dtos,String operateType) throws BusinessException{
		SendLineRequest request = new SendLineRequest();
		List<LineInfo> list=new ArrayList<LineInfo>();
		//非空校验
		if(CollectionUtils.isNotEmpty(dtos)){
			for (LineEntity lineEntity : dtos) {
				LineInfo info = new LineInfo();
				//查询到达
			 	OrgAdministrativeInfoEntity destOrg  =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(lineEntity.getDestinationOrganizationCode());
				//查询出发
			 	OrgAdministrativeInfoEntity origOrg =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(lineEntity.getOrginalOrganizationCode());
				if(null !=destOrg && null != origOrg){
					//到达线路
					if(StringUtils.equals(lineEntity.getLineSort(), DictionaryValueConstants.BSE_LINE_SORT_TARGET)){
					 	//营业部标杆编码
						info.setOrigOrgCode(destOrg.getUnifiedCode());
						//外场标杆编码
						info.setDestOrgCode(origOrg.getUnifiedCode());
						//是否出发、到达
						info.setIsArrive(FossConstants.YES);
						info.setIsSetOut(FossConstants.NO);
					//始发线路	
					}else if(StringUtils.equals(lineEntity.getLineSort(), DictionaryValueConstants.BSE_LINE_SORT_SOURCE)){
						//营业部标杆编码
						info.setOrigOrgCode(origOrg.getUnifiedCode());
						//外场标杆编码
						info.setDestOrgCode(destOrg.getUnifiedCode());
						//是否出发、到达
						info.setIsArrive(FossConstants.NO);
						info.setIsSetOut(FossConstants.YES);
					}
				}
				info.setLineName(lineEntity.getLineName());
				info.setOperate(operateType);
				list.add(info);
			}
		}
		//将集合添加到request对象中 
		request.getLineInfo().addAll(list);
		return request;
	}
	/**
	 * 
	 *<p>同步信息方法(新)</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-1-29上午11:23:07
	 *@see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendLineInfoToWDGHService#SyncLineInfo(java.util.List)	
	 *@param dtos
	 */
	@Override
	public void syncLineInfos(LineEntity dtos,String operateType) {
		//同步信息非空校验
		if(null != dtos){
			SendLinesRequest request = new SendLinesRequest();
			//转换对象
			request = transtoEsbObjects(dtos,operateType);
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_LINES_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setBusinessDesc1("同步线路信息(new)");
			accessHeader.setVersion("0.1");
			long startTime = System.currentTimeMillis();
			EsbErrorLogging erlog = new EsbErrorLogging();
			try {
				erlog.setMethodDesc("同步线路信息(new)TO WDGH");
				erlog.setParamenter(new SendLinesRequestTrans().fromMessage(request));
				erlog.setRequestSystem("ESB");
				erlog.setRequestTime(new Date());
				erlog.setMethodName(this.getClass().getName()+".SyncFreightRouteLineInfo");
				log.info("开始调用 同步线路信息(new)TO WDGH：\n"+new SendLinesRequestTrans().fromMessage(request));
				syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
				log.info("结束调用 同步线路信息(new)TO WDGH：\n"+new SendLinesRequestTrans().fromMessage(request));
				long responseTime = System.currentTimeMillis()-startTime; 
				if(responseTime>FossConstants.MAX_RESPONSE_TIME){
					erlog.setErrorMessage("响应超时");
					erlog.setCreateTime(new Date());
					erlog.setResponseTime(responseTime);
					esbErrorLoggingDao.addErrorMessage(erlog);
				}
			} catch (ESBException e) {
				log.error(e.getMessage(), e);
				erlog.setResponseTime((System.currentTimeMillis()-startTime));
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				erlog.setCreateTime(new Date());
				e.printStackTrace(pw);
				erlog.setErrorMessage(sw.toString());
				esbErrorLoggingDao.addErrorMessage(erlog);
				throw new SynchronousExternalSystemException(" 同步线路信息给网点规划(new)"," 同步线路信息(new) 发送数据到ESB错误");
			}
		}
	}

	/**
	 *<p>将foss线路信息对象转换成esb请求对象(新)</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-1-29上午11:26:41
	 *@param dtos
	 *@return
	 */
	private SendLinesRequest transtoEsbObjects(LineEntity dtos,String operateType) {
		SendLinesRequest request = new SendLinesRequest();
		LinesInfo reqInfo =new LinesInfo();
		//非空校验
		if(null != dtos){

			    reqInfo.setActive(dtos.getActive());
			    reqInfo.setLineName(dtos.getLineName());//线路名称
			    if(null != dtos.getCommonAging())
			    	reqInfo.setCommonAging(dtos.getCommonAging());
				reqInfo.setCreateDate(dtos.getCreateDate());
				reqInfo.setCreateUser(dtos.getCreateUser());
				reqInfo.setDestinationCityCode(dtos.getDestinationCityCode());
				if(null != dtos.getDistance())
					reqInfo.setDistance(dtos.getDistance());
				if(null != dtos.getFastAging())
					reqInfo.setFastAging(dtos.getFastAging());
				reqInfo.setId(dtos.getId());
				reqInfo.setIsDefault(dtos.getIsDefault());
				reqInfo.setIsNorewardPunish(dtos.getIsNorewardPunish());
				reqInfo.setLineSort(dtos.getLineSort());
				reqInfo.setLineType(dtos.getLineType());
				reqInfo.setModifyDate(dtos.getModifyDate());
				reqInfo.setModifyUser(dtos.getModifyUser());
				reqInfo.setNotes(dtos.getNotes());
				reqInfo.setOrganizationCode(dtos.getOrganizationCode());
				if(null != dtos.getOtherAging())
					reqInfo.setOtherAging(dtos.getOtherAging());
				reqInfo.setSimpleCode(dtos.getSimpleCode());
				reqInfo.setTransType(dtos.getTransType());
				reqInfo.setValid(dtos.getValid());
				if(null != dtos.getVersion())
					reqInfo.setVersionNO(dtos.getVersion());
				reqInfo.setVirtualCode(dtos.getVirtualCode());				
				reqInfo.setOriginalOrganizationCode(dtos.getOrginalOrganizationCode());
				reqInfo.setDestinationOrganizationCode(dtos.getDestinationOrganizationCode());
				reqInfo.setOriginalCityCode(dtos.getOrginalCityCode());
		}
		
		//将数据添加到request对象中 				
		request.setLinesInfo(reqInfo);
		request.setOperate(operateType);
		return request;
	}
}
