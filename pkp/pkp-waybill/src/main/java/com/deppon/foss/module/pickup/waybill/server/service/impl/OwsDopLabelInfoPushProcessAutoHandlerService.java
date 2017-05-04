package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOwsDopLabelInfoJmsService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOwsDopLabelInfoPushProcessAutoHandlerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPushProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * OWS/DOP标签推送任务处理线程
 * @author 329834
 *
 */
public class OwsDopLabelInfoPushProcessAutoHandlerService extends OrderTheadPool implements IOwsDopLabelInfoPushProcessAutoHandlerService {

	/** 线程池的最大线程数 */
	private static final String OWSDOP_LABEL_PUSH_THREAD_COUNT =  WaybillConstants.PKP_OWSDOP_LABEL_PUSH_THREAD_COUNT;
	
	protected final static Logger logger = LoggerFactory.getLogger(OwsDopLabelInfoPushProcessAutoHandlerService.class.getName());
	
	private IConfigurationParamsService configurationParamsService;
	private ILabelPushProcessService labelPushProcessService;
	private IOwsDopLabelInfoJmsService owsDopLabelInfoJmsService;
	
	@Override
	public void process(String jobId) {
		pushThreadsPool(jobId);
	}

	@Override
	public void businessExecutor(Object obj) {
		String jobId = (String) obj;
		List<LabelPushProcessEntity> labelPushProcessEntityList = labelPushProcessService.queryWaitingProcessWaybillNoByJobId(jobId);
		if(null == labelPushProcessEntityList || labelPushProcessEntityList.isEmpty()){
			logger.warn("OwsDopLabelInfoPushProcessAutoHandlerService.businessExecutor: jobId ="+jobId+", 该jobId对应的任务列表为空，忽略此次调度！");
			return;
		}
		
		for(LabelPushProcessEntity labelPushProcessEntity : labelPushProcessEntityList){
			
			if(null == labelPushProcessEntity){
				logger.warn("OwsDopLabelInfoPushProcessAutoHandlerService.businessExecutor: jobId ="+jobId+", 取到了空的标签推送线程实体，改实体的处理跳过！");
				continue;
			}
			
			if(StringUtils.isEmpty(labelPushProcessEntity.getWaybillNo()) || StringUtils.isEmpty(labelPushProcessEntity.getId())){
				
				StringBuffer sb = new StringBuffer();
				sb.append("OwsDopLabelInfoPushProcessAutoHandlerService.businessExecutor: jobId =").append(jobId)
					.append(", 运单号：").append(labelPushProcessEntity.getWaybillNo())
					.append("labelPushProcessEntity.getId()=").append(labelPushProcessEntity.getId())
					.append("， 运单号或标签推送线程实体ID为空，此标签推送过程跳过！");
				
				logger.warn(sb.toString());
				continue;
			}
			
			try{
				ResultDto result = owsDopLabelInfoJmsService.sendLabelInfo(labelPushProcessEntity.getWaybillNo(), labelPushProcessEntity.getId());
				if("1".equals(result.getCode())){
					//推送成功
					labelPushProcessService.updateAfterProcess(labelPushProcessEntity.getWaybillNo(), 
							WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHED, null);
				}else{ 
					//JMS消息推送失败
					labelPushProcessService.updateAfterProcess(labelPushProcessEntity.getWaybillNo(), 
							WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL, 
							result.getMsg());
				}
			}catch(Exception e){
				StringBuffer err = new StringBuffer();
				err.append("处理运单号：").append(labelPushProcessEntity.getWaybillNo()).append("， jobId=").append(jobId)
					.append("， 标签推送处理线程实体ID：").append(labelPushProcessEntity.getId()).append("的标签推送任务失败， 异常信息为：")
					.append(e).append("， 异常的详细信息为：").append(ExceptionUtils.getFullStackTrace(e));
				labelPushProcessService.updateAfterProcess(labelPushProcessEntity.getWaybillNo(), 
						WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL, 
						err.toString());
			}
		}
	}

	@Override
	public void outOfOrderPool(Object obj) {
		logger.info("OwsDopLabelInfoPushProcessAutoHandlerService【线程池满异常处理开始......】");
		//进行数据转换
		String jobId = (String) obj;
		List<LabelPushProcessEntity> labelPushProcessEntityList = labelPushProcessService.queryWaitingProcessWaybillNoByJobId(jobId);
		for(LabelPushProcessEntity labelPushProcessEntity : labelPushProcessEntityList){
			if(StringUtils.isEmpty(labelPushProcessEntity.getWaybillNo())){
				logger.warn("OwsDopLabelInfoPushProcessAutoHandlerService.outOfOrderPool: 处理线程池满时，遇到waybillNo为空的标签线程推送任务实体，跳过该任务的回滚！实体内容："
						+ToStringBuilder.reflectionToString(labelPushProcessEntity));
				continue;
			}
			labelPushProcessService.rollbackWaitingJob(labelPushProcessEntity.getWaybillNo());
			logger.info("OwsDopLabelInfoPushProcessAutoHandlerService线程池满，对任务记录进行回滚，运单号："+labelPushProcessEntity.getWaybillNo());
		}
		
	
		logger.info("OwsDopLabelInfoPushProcessAutoHandlerService【线程池满异常处理结束......】");
	}

	@Override
	public int getActiveThreads() {
		Integer initThreads=NumberConstants.NUMBER_3;
		String[] codes = new String[1];
		codes[0]=OWSDOP_LABEL_PUSH_THREAD_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			if(null!=configurationParamsEntitys.get(0).getConfValue()){
				initThreads = Integer.valueOf(configurationParamsEntitys.get(0).getConfValue());
			}
		}
		return initThreads;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setLabelPushProcessService(
			ILabelPushProcessService labelPushProcessService) {
		this.labelPushProcessService = labelPushProcessService;
	}

	public void setOwsDopLabelInfoJmsService(
			IOwsDopLabelInfoJmsService owsDopLabelInfoJmsService) {
		this.owsDopLabelInfoJmsService = owsDopLabelInfoJmsService;
	}

}
