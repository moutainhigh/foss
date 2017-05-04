package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.IPushTrackSendDopService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TrackExternalLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TrackingsRequestCommDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TrackingsResponseCommDto;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
* @description 提供dop推送服务
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年5月29日 下午6:47:20
*/
public class PushTrackSendDopService extends TheadPool implements IPushTrackSendDopService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PushTrackSendDopService.class);
	
	/**
	 * esb服务编码
	* @fields esb_code
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:49:01
	* @version V1.0
	*/
	private static final String esb_code = "ESB_FOSS2ESB_TMALL_STATUS_PUSH";
	private ITrackingsDao trackingsDao;
	private IConfigurationParamsService configurationParamsService;
	
	//公共类
	private ITfrCommonService  tfrCommonService;
	
	
	
	
	public void setTrackingsDao(ITrackingsDao trackingsDao) {
		this.trackingsDao = trackingsDao;
	}
	
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	



	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}


	/**
	* @description 轨迹表中更新jobid
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IPushTrackSendDopService#updateAndGetJobId()
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:29:18
	* @version V1.0
	*/
	@Override
	public String updateAndGetJobId() {
		String jobid  =  UUIDUtils.getUUID();
		int limit = TransferConstants.SONAR_NUMBER_3000;
		List<String> list = new ArrayList<String>();
		String[] strArray = BusinessGoalContants.BUSINESS_GOAL_DOP.split(":");
		if(strArray!=null&&strArray.length>0){
			for (int i = 0; i < strArray.length; i++) {
				if(strArray[i]!=null){
					list.add(strArray[i].trim());
				}
			}
		}
		trackingsDao.updateAndGetJobId(jobid, limit,list);
		return jobid;
	}

	
	/**
	* @description 根据jobid查询待推送数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IPushTrackSendDopService#queryTrackingsInfobyJobId(java.lang.String)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:28:23
	* @version V1.0
	*/
	@Override  
	public List<TrackingsRequestCommDto> queryTrackingsInfobyJobId(String jobId) {
		List<String> list = new ArrayList<String>();
		String[] strArray = BusinessGoalContants.BUSINESS_GOAL_DOP.split(":");
		if(strArray!=null&&strArray.length>0){
			for (int i = 0; i < strArray.length; i++) {
				if(strArray[i]!=null){
					list.add(strArray[i].trim());
				}
			}
		}
		return trackingsDao.queryTrackingsInfobyJobId(jobId,list);
	}

	
	/**
	* @description 线程实现
	 */
	
	
	/**
	* @description 待推送数据放入线程池
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IPushTrackSendDopService#ThreadsPool(java.lang.Object)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:31:52
	* @version V1.0
	*/
	@Override
	public void ThreadsPool(Object obj) {
		pushThreadsPool(obj);
		
	}

	
	/**
	* @description 重置异常数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IPushTrackSendDopService#reSetTrackingsMsgbyJobId()
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:33:03
	* @version V1.0
	*/
	@Override
	public void reSetTrackingsMsgbyJobId() {
		trackingsDao.resetMsgbyJobId();
		
	}
	
	/**
	* @description  业务处理方法，推送轨迹dop
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#businessExecutor(java.lang.Object)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:27:39
	* @version V1.0
	*/
	@Override
	@Transactional
	public void businessExecutor(Object obj) {
		@SuppressWarnings("unchecked")
		List<TrackingsRequestCommDto> dtos = (List<TrackingsRequestCommDto>) obj;
		List<TrackExternalLogEntity> logEntitys = new ArrayList<TrackExternalLogEntity>();
		for(TrackingsRequestCommDto dto:dtos){
			TrackExternalLogEntity logEntity = new TrackExternalLogEntity();
			try {
				TrackingsResponseCommDto responseDto = pushTrackingsDOP(dto);
//				TrackingsResponseCommDto responseDto = null;
				if(null != responseDto){
					if(StringUtils.equals(responseDto.getIsSuccess(), "Y")){
						trackingsDao.deleteMsgById(dto.getId());
						logEntity.setId(UUIDUtils.getUUID());
						logEntity.setWaybillNo(dto.getWaybillNo());
						logEntity.setSerialNo(dto.getSerialNo());
						logEntity.setMsgType(dto.getEventType());
						logEntity.setCreateDate(new Date());
						logEntity.setMsgInfo("推送成功");
						logEntity.setSynResult("success");
						logEntitys.add(logEntity);
					}else{
						trackingsDao.updateTrackingsMsg(dto.getId());
						logEntity.setId(UUIDUtils.getUUID());
						logEntity.setWaybillNo(dto.getWaybillNo());
						logEntity.setSerialNo(dto.getSerialNo());
						logEntity.setMsgType(dto.getEventType());
						logEntity.setCreateDate(new Date());
						logEntity.setMsgInfo(responseDto.getError());
						logEntity.setSynResult("Fail");
						logEntitys.add(logEntity);
					}
				}else{
					LOGGER.error("FOSS推送OMS发生异常" + dto.getWaybillNo() + "给DOP结束...");
					trackingsDao.updateTrackingsMsg(dto.getId());
					logEntity.setId(UUIDUtils.getUUID());
					logEntity.setWaybillNo(dto.getWaybillNo());
					logEntity.setSerialNo(dto.getSerialNo());
					logEntity.setMsgType(dto.getEventType());
					logEntity.setCreateDate(new Date());
					logEntity.setMsgInfo("推送失败，返回对象为空");
					logEntity.setSynResult("Fail");
					logEntitys.add(logEntity);
				}
			} catch (Exception e) {
				LOGGER.error("FOSS推送快递单号发生异常" + dto.getWaybillNo() + "给DOP结束...");
				logEntity.setId(UUIDUtils.getUUID());
				logEntity.setWaybillNo(dto.getWaybillNo());
				logEntity.setSerialNo(dto.getSerialNo());
				logEntity.setMsgType(dto.getEventType());
				logEntity.setCreateDate(new Date());
				logEntity.setMsgInfo("推送失败，发送系统异常"+e.getMessage());
				logEntity.setSynResult("Fail");
				logEntitys.add(logEntity);
				trackingsDao.updateTrackingsMsg(dto.getId());
			}
			LOGGER.error("FOSS推送快递单号" + dto.getWaybillNo() + "给DOP结束...");
		}
		
		//插入日志
		if(CollectionUtils.isNotEmpty(logEntitys)){
			trackingsDao.addTrackLogs(logEntitys);
		}
		
	}

	
	/**
	* @description 处理线程池溢出异常
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#outOfUnloadPool(java.lang.Object)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:35:20
	* @version V1.0
	*/
	@Override
	public void outOfUnloadPool(Object obj) {
		LOGGER.info("【线程池满异常处理开始。。。。。。】");
		//插入日志
		@SuppressWarnings("unchecked")
		List<TrackingsRequestCommDto>  entitys =(List<TrackingsRequestCommDto>) obj;
		if(CollectionUtils.isNotEmpty(entitys) && entitys.size()>0){
			//运单号
			String waybillNo = entitys.get(0).getWaybillNo();
			LOGGER.info("运单："+waybillNo+"推送失败！线程池已满");
			//异常插入线程日志中
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName("推动淘宝轨迹线程:"+Thread.currentThread().getName());
			jobProcessLogEntity.setBizCode("推动淘宝轨迹线程类:"+Thread.class.getClass().getName());
			jobProcessLogEntity.setRemark("运单："+waybillNo+"推送失败！");
			jobProcessLogEntity.setExceptionInfo("线程池满");
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		//批量更新没有处理完成数据的jobid为N/A
		trackingsDao.updateTrackingsMsgs(entitys);
		LOGGER.info("【线程池满异常处理结束。。。。。。】");
		
	}

	
	/**
	* @description 获取线程数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#getActiveThreads()
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:35:13
	* @version V1.0
	*/
	@Override
	public int getActiveThreads() {
		ConfigurationParamsEntity paramEntity;
		int threadCount =TransferConstants.SONAR_NUMBER_50;
		//获取最大线程数
		try {
			paramEntity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM_PDA_UNLOAD_INSTOCK_JOB_COUNT,
							FossConstants.ROOT_ORG_CODE);
			if(paramEntity!= null){
				String value = paramEntity.getConfValue();
				//从数据字典获取线程数
				 threadCount = Integer.parseInt(value);
			}
			
		} catch (Exception e) {
			LOGGER.info("从数据字典获取卸车入库线程数异常！"+e.toString());
		}
		return threadCount;
	}

	
	public TrackingsResponseCommDto pushTrackingsDOP(TrackingsRequestCommDto dto){
		LOGGER.error("FOSS推送快递单号" + dto.getWaybillNo() + "给DOP开始...");
		
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + esb_code;
		//String url = "http://192.168.67.12:8580/esb2/rs" + "/" + esb_code;
		//String url = "http://192.168.20.123:8180/sync/webservice/dop/fossStatusService/receiverStatus";
		//String url =  "http://10.224.71.51:8085/oms-interface-sync-web/v1/oms/order/statuspush";
		PostMethod  postMethod=new PostMethod(url);
		
		HttpClient httpClient = new HttpClient();
		// 设置连接超时时间(单位毫秒) 20S
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TransferConstants.SONAR_NUMBER_20000); 
		// 设置读数据超时时间(单位毫秒) 40S
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TransferConstants.SONAR_NUMBER_40000); 
		TrackingsResponseCommDto responseDto = new TrackingsResponseCommDto();
		
		if(dto==null || dto.getId().isEmpty()){
			return null;
		}
	    try {
		
			String requestStr = JSONObject.toJSONString(dto);
			LOGGER.info("FOSS推送OMS=>Json：" + requestStr);

			//设置报文信息
			httpClient.getParams().setContentCharset("UTF-8");
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","UTF-8");
		    postMethod.setRequestEntity(requestEntity);
		    
			httpClient.executeMethod(postMethod);
			httpClient.getState();
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String responseStr= "";
			while((responseStr = br.readLine()) != null){
				stringBuffer.append(responseStr);
			}
			responseStr = stringBuffer.toString();
			LOGGER.info("FOSS推送OMS返回："+responseStr);
			if(!responseStr.isEmpty()){
				responseDto = JSONObject.parseObject(responseStr, TrackingsResponseCommDto.class);
				if(responseStr.contains("S000099")){
					return null;
				}
			}
			LOGGER.info("FOSS推送OMS返回提示："+responseDto.getError());
			//System.out.println("返回对象"+responseDto.toString()+responseDto.getSuccess());
			//System.out.println("发送成功************************************");
			return responseDto;
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setIsSuccess("N");
			responseDto.setError(e.getMessage());
			//System.out.println("发送失败************************************");
			return responseDto;
		}finally{
			postMethod.releaseConnection();
		} 
	}
}
