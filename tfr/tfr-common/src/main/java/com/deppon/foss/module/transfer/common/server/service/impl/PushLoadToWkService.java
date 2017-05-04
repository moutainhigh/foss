package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.IPushLoadToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TrackingsRequestCommDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;
import com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadStatueEnum;
import com.deppon.foss.util.define.FossConstants;

/**
 * @description 这里用一句话描述这个类的作用
 * @version 1.0
 * @author 328864-foss-xieyang
 * @update 2016年5月6日 下午4:31:17
 */
public class PushLoadToWkService extends TheadPool implements IPushLoadToWkService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushLoadToWkService.class);

	private final String create_esb_code = "ESB_FOSS2ESB_CREATE_LOADTASK_FROMFOSS";
	
	private final String finish_esb_code = "ESB_FOSS2ESB_SUBMIT_LOAD_TASK";

	private IConfigurationParamsService configurationParamsService;

	private IWKLoadTempService wkLoadTempService;

	// 公共类
	private ITfrCommonService tfrCommonService;

	@Override
	@Transactional
	public int pushCreateLoadToWK(WKLoadTempDto dto) {
		if(LOGGER.isInfoEnabled()) {
			LOGGER.error("FOSS推送创建装车给悟空开始..." + dto.getJsonData());
		}
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒) 20S
		managerParams.setConnectionTimeout(TransferConstants.SONAR_NUMBER_20000);
		// 设置读数据超时时间(单位毫秒) 40S
		managerParams.setSoTimeout(TransferConstants.SONAR_NUMBER_40000);

		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + create_esb_code;

		PostMethod postMethod = new PostMethod(url);
		
		int result = 0; // 失败

		try {
			StringRequestEntity requestEntity = new StringRequestEntity(dto.getJsonData(), "application/json", "UTF-8");
			// 设置字符集编码
			postMethod.getParams().setContentCharset("UTF-8");
			postMethod.getParams().setHttpElementCharset("UTF-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			httpClient.getState();
			String responseStr = postMethod.getResponseBodyAsString();
			java.util.Map<String, Object> map = JSON.parseObject(responseStr);
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(map.toString());
			}
			if (map.containsKey("status") && map.get("status").toString().equals("1")) {
				wkLoadTempService.deleteData(dto);
				return 1;
			}
			LOGGER.info("responseStr:" + responseStr);
			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return result;
		} finally {
			postMethod.releaseConnection();
		}
	}

	@Override
//	@Transactional
	public int pushFinishCreateLoadToWK(WKLoadTempDto dto) {
		if(LOGGER.isInfoEnabled()) {
			LOGGER.error("FOSS推送完成创建装车给悟空开始..." + dto.getJsonData());
		}
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒) 20S
		managerParams.setConnectionTimeout(TransferConstants.SONAR_NUMBER_20000);
		// 设置读数据超时时间(单位毫秒) 40S
		managerParams.setSoTimeout(TransferConstants.SONAR_NUMBER_40000);

		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + finish_esb_code;

		PostMethod postMethod = new PostMethod(url);
		int result = 0; // 同步失败
		try {

			StringRequestEntity requestEntity = new StringRequestEntity(dto.getJsonData(), "application/json", "UTF-8");
			// 设置字符集编码
			postMethod.getParams().setContentCharset("UTF-8");
			postMethod.getParams().setHttpElementCharset("UTF-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			httpClient.getState();
			String responseStr = postMethod.getResponseBodyAsString();
			java.util.Map<String, Object> map = JSON.parseObject(responseStr);
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(map.toString());
			}
			
			// 推送成功就删除,失败更改状态
			if (map.containsKey("status") && map.get("status").toString().equals("1")) {
				wkLoadTempService.deleteData(dto);
				return 1;
			} else {
				wkLoadTempService.updateWKLoadTempDto(dto.getTaskNo());
			}
			LOGGER.info("responseStr:" + responseStr);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return result;
		} finally {
			postMethod.releaseConnection();
		}
	}

	@Override
//	@Transactional
	public void businessExecutor(Object obj) {

		LOGGER.info("执行:businessExecutor");

		int taskType = 0;
		int limit = TransferConstants.SONAR_NUMBER_900;
		int offset = 0;

		// 把所有需要推送的单子设置为未推送状态
		wkLoadTempService.updateWKLoadTempDtoState();
		int count = wkLoadTempService.getCount(taskType);
		
		LOGGER.info("数据库查询出来:" + count + "条数据");
		if (count == 0) {
			return;
		}

		int pageNum = (int) Math.ceil(count / limit);

		LOGGER.info("分:" + pageNum + "批处理");

		for (int i = 0; i <= pageNum; i++) {
			LOGGER.info("处理第:" + i + "批数据");
			List<WKLoadTempDto> tempList = wkLoadTempService.getDatasByPage(taskType, offset, limit);

			for (WKLoadTempDto wkLoadTempDto : tempList) {

				int result = 0;
				if (LoadStatueEnum.CREATE.getCode().equals(wkLoadTempDto.getTaskType())) {
					result = pushCreateLoadToWK(wkLoadTempDto);
				} else {
					result = pushFinishCreateLoadToWK(wkLoadTempDto);
				}
				if (result == 1) {
					LOGGER.info("推送消息成功");
				} else {
					LOGGER.error("推送消息失败, 任务号是 : " + wkLoadTempDto.getTaskNo());
				}
			}

		}

	}

	@Override
	public void outOfUnloadPool(Object obj) {
		LOGGER.info("【线程池满异常处理开始。。。。。。】");
		// 插入日志
		@SuppressWarnings("unchecked")
		List<TrackingsRequestCommDto> entitys = (List<TrackingsRequestCommDto>) obj;
		if (CollectionUtils.isNotEmpty(entitys) && entitys.size() > 0) {
			// 运单号
			String waybillNo = entitys.get(0).getWaybillNo();
			LOGGER.info("运单：" + waybillNo + "推送失败！线程池已满");
			// 异常插入线程日志中
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName("推送wk创建装车线程:" + Thread.currentThread().getName());
			jobProcessLogEntity.setBizCode("推动wk创建装车线程类:" + Thread.class.getClass().getName());
			jobProcessLogEntity.setRemark("运单：" + waybillNo + "推送失败！");
			jobProcessLogEntity.setExceptionInfo("线程池满");
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}
		LOGGER.info("【线程池满异常处理结束。。。。。。】");

	}

	@Override
	public int getActiveThreads() {
		ConfigurationParamsEntity paramEntity;
		int threadCount = TransferConstants.SONAR_NUMBER_50;
		// 获取最大线程数
		try {
			paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
					ConfigurationParamsConstants.TFR_PARM_PDA_UNLOAD_INSTOCK_JOB_COUNT, FossConstants.ROOT_ORG_CODE);
			if (paramEntity != null) {
				String value = paramEntity.getConfValue();
				// 从数据字典获取线程数
				threadCount = Integer.parseInt(value);
			}

		} catch (Exception e) {
			LOGGER.info("从数据字典获取卸车入库线程数异常！" + e.toString());
		}
		return threadCount;
	}

	@Override
	public void ThreadsPool(Object obj) {
		LOGGER.info("进入" + this.getClass().getName());
		pushThreadsPool(obj);

	}

	/*** setting ***/

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setWkLoadTempService(IWKLoadTempService wkLoadTempService) {
		this.wkLoadTempService = wkLoadTempService;
	}

}
