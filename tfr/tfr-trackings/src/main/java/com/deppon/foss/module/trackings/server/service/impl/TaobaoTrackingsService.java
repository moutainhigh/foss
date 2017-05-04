package com.deppon.foss.module.trackings.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao;
import com.deppon.foss.module.trackings.api.server.service.ITaobaoTrackingsService;
import com.deppon.foss.module.trackings.api.shared.domain.TrackExternalLogEntity;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsRequestDto;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsResponseDto;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * <p>推送轨迹给淘宝，实现类</p> 
 * @author alfred
 * @date 2015-4-28 上午10:31:45
 * @see
 */
public class TaobaoTrackingsService extends TheadPool implements ITaobaoTrackingsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaobaoTrackingsService.class);
	//公共类
	private ITfrCommonService  tfrCommonService;
	
	private IConfigurationParamsService configurationParamsService;
	
	private ITaobaoTrackingsDao taobaoTrackingsDao;
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setTaobaoTrackingsDao(ITaobaoTrackingsDao taobaoTrackingsDao) {
		this.taobaoTrackingsDao = taobaoTrackingsDao;
	}

	/**
	 * 
	 * <p>轨迹表中更新jobid</p> 
	 * @author alfred
	 * @date 2015-4-28 下午2:44:55
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.service.ITaobaoTrackingsService#updateAndGetJobId()
	 */
	@Override
	public String updateAndGetJobId() {
		String jobid  =  UUIDUtils.getUUID();
		int limit = 3000;
		taobaoTrackingsDao.updateAndGetJobId(jobid, limit);
		return jobid;
	}

	/**
	 * 
	 * <p>根据jobid查询待推送数据</p> 
	 * @author alfred
	 * @date 2015-4-28 下午2:45:47
	 * @param jobId
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.service.ITaobaoTrackingsService#queryTrackingsInfobyJobId(java.lang.String)
	 */
	@Override
	public List<TaobaoTrackingsRequestDto> queryTrackingsInfobyJobId(String jobId) {
		return taobaoTrackingsDao.queryTrackingsInfobyJobId(jobId);
	}

	/**
	 * 
	 * <p>业务处理方法，推送轨迹给淘宝</p> 
	 * @author alfred
	 * @date 2015-4-28 下午2:46:18
	 * @param obj 
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#businessExecutor(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void businessExecutor(Object obj) {
		List<TaobaoTrackingsRequestDto> dtos = (List<TaobaoTrackingsRequestDto>) obj;
		List<TrackExternalLogEntity> logEntitys = new ArrayList<TrackExternalLogEntity>();
		for(TaobaoTrackingsRequestDto dto:dtos){
			LOGGER.error("FOSS推送快递单号" + dto.getWaybillNo() + "给DOP开始...");
			TrackExternalLogEntity logEntity = new TrackExternalLogEntity();
			try {
				TaobaoTrackingsResponseDto responseDto = pushTrackings2taobao(dto);
				if(null != responseDto){
					if(StringUtils.equals(responseDto.getResult(), "true")){
						taobaoTrackingsDao.deleteMsgById(dto.getId());
						logEntity.setId(UUIDUtils.getUUID());
						logEntity.setWaybillNo(dto.getWaybillNo());
						logEntity.setSerialNo(dto.getSerialNo());
						logEntity.setMsgType(dto.getEventType());
						logEntity.setCreateDate(new Date());
						logEntity.setMsgInfo("推送成功");
						logEntity.setSynResult(TransferPDADictConstants.SUCCESS);
						logEntitys.add(logEntity);
					}else{
						taobaoTrackingsDao.updateTrackingsMsg(dto.getId());
						logEntity.setId(UUIDUtils.getUUID());
						logEntity.setWaybillNo(dto.getWaybillNo());
						logEntity.setSerialNo(dto.getSerialNo());
						logEntity.setMsgType(dto.getEventType());
						logEntity.setCreateDate(new Date());
						logEntity.setMsgInfo(responseDto.getMessage());
						logEntity.setSynResult(TransferPDADictConstants.FAIL);
						logEntitys.add(logEntity);
					}
				}
			} catch (Exception e) {
				LOGGER.error("FOSS推送快递单号发生异常" + dto.getWaybillNo() + "给taobao结束...");
				logEntity.setId(UUIDUtils.getUUID());
				logEntity.setWaybillNo(dto.getWaybillNo());
				logEntity.setMsgType(dto.getEventType());
				logEntity.setCreateDate(new Date());
				logEntity.setMsgInfo("推送失败，发送系统异常"+e.getMessage());
				logEntity.setSynResult(TransferPDADictConstants.FAIL);
				logEntitys.add(logEntity);
				taobaoTrackingsDao.updateTrackingsMsg(dto.getId());
			}
			LOGGER.error("FOSS推送快递单号" + dto.getWaybillNo() + "给DOP结束...");
		}
		
		//插入日志
		if(CollectionUtils.isNotEmpty(logEntitys)){
			taobaoTrackingsDao.addTrackLogs(logEntitys);
		}
		
	}

	/**
	 * 
	 * <p>处理线程池溢出异常</p> 
	 * @author alfred
	 * @date 2015-4-28 下午2:39:23
	 * @param obj 
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#outOfUnloadPool(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void outOfUnloadPool(Object obj) {
		LOGGER.info("【线程池满异常处理开始。。。。。。】");
		//插入日志
		List<TaobaoTrackingsRequestDto>  entitys =(List<TaobaoTrackingsRequestDto>) obj;
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
		taobaoTrackingsDao.updateTrackingsMsgs(entitys);
		LOGGER.info("【线程池满异常处理结束。。。。。。】");
	}

	/**
	 * 
	 * <p>获取线程数</p> 
	 * @author alfred
	 * @date 2015-4-28 下午2:37:36
	 * @return 
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool#getActiveThreads()
	 */
	@Override
	public int getActiveThreads() {
		ConfigurationParamsEntity paramEntity;
		int threadCount =50;
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

	/**
	 * 
	 * <p>待推送数据放入线程池</p> 
	 * @author alfred
	 * @date 2015-4-28 下午2:46:52
	 * @param obj 
	 * @see com.deppon.foss.module.trackings.api.server.service.ITaobaoTrackingsService#ThreadsPool(java.lang.Object)
	 */
	@Override
	public void ThreadsPool(Object obj) {
		pushThreadsPool(obj);
	}
	

	/**
	 * 
	 * <p>推送轨迹给淘宝</p> 
	 * @author alfred
	 * @date 2015-4-28 下午5:29:11
	 * @param dto
	 * @return
	 * @see
	 */
	public TaobaoTrackingsResponseDto pushTrackings2taobao(TaobaoTrackingsRequestDto dto){
		// esb服务编码
		String code = "";
		HttpClient httpClient = new HttpClient();
		
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		PostMethod  postMethod=new PostMethod(url);
		TaobaoTrackingsResponseDto responseDto = new TaobaoTrackingsResponseDto();
		String requestStr = JSONObject.toJSONString(dto);
		
		RequestEntity requestEntity = new StringRequestEntity("param="+requestStr);
	     postMethod.setRequestEntity(requestEntity);
	     try {
				httpClient.executeMethod(postMethod);
				String responseStr = postMethod.getResponseBodyAsString();
				if(responseStr.contains("exceptionCode")&&
						responseStr.contains("S000099")){
					return null;
				}
				responseDto = JSONObject.parseObject(responseStr, TaobaoTrackingsResponseDto.class);
				return responseDto;
			} catch (HttpException e) {
				e.printStackTrace();
				responseDto.setResult("false");
				responseDto.setMessage(e.getMessage());
				return responseDto;
			} catch (IOException e) {
				e.printStackTrace();
				responseDto.setResult("false");
				responseDto.setMessage(e.getMessage());
				return responseDto;
			}	
	}
	
	public static void main(String[] args) {
		
	}

	/**
	 * 
	 * <p>重置异常数据</p> 
	 * @author alfred
	 * @date 2015-4-29 下午7:10:59 
	 * @see com.deppon.foss.module.trackings.api.server.service.ITaobaoTrackingsService#reSetTrackingsMsgbyJobId()
	 */
	@Override
	public void reSetTrackingsMsgbyJobId() {
		taobaoTrackingsDao.resetMsgbyJobId();
	}
	
	

	
}
