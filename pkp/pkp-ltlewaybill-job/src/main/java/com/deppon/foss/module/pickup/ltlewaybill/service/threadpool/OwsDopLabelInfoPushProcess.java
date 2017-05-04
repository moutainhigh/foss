package com.deppon.foss.module.pickup.ltlewaybill.service.threadpool;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOwsDopLabelInfoPushProcessAutoHandlerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class OwsDopLabelInfoPushProcess extends OrderThreadPoolCaller {
	
	private  static final String PKP_OWSDOP_LABEL_PUSH_AUTO_SCHEDULE = WaybillConstants.PKP_OWSDOP_LABEL_PUSH_AUTO_SCHEDULE;
	private static final String PKP_OWSDOP_LABEL_PUSH_SCHEDULE_COUNT =WaybillConstants.PKP_OWSDOP_LABEL_PUSH_SCHEDULE_COUNT;
	
	private IConfigurationParamsService configurationParamsService;
	private ILabelPushProcessService labelPushProcessService;
	private IOwsDopLabelInfoPushProcessAutoHandlerService owsDopLabelInfoPushProcessAutoHandlerService;
	
	@Override
	public int serviceCaller() {
		if(!isJobOpen()){
			return 0; 
		}
		//更新数据
		String jobId = UUIDUtils.getUUID();
		int updateCount = readChangeCount();
		int result = labelPushProcessService.updateJobIdForWaitingJobs(jobId, updateCount);
		if(result > 0){
			owsDopLabelInfoPushProcessAutoHandlerService.process(jobId);
		}
		return result;
		
	}
	
	/**
	 * 从配置参数中获得零担每个线程自动读取数量
	 * @return
	 */
	private int readChangeCount(){
		String num = "50";
		String[] codes = new String[1];
		codes[0]=PKP_OWSDOP_LABEL_PUSH_SCHEDULE_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			num = configurationParamsEntitys.get(0).getConfValue();
		}
		return Integer.parseInt(num);
	}
	
	/**
	 * 服务是否开启
	 * @return
	 */
	private boolean isJobOpen(){
		//开关是否关闭 开着 走电子运单线程 从配置参数中读取自动调度电子运单开关值
		//默认开关关闭
		String flag = FossConstants.NO;
		String[] codes = new String[1];
		codes[0]=PKP_OWSDOP_LABEL_PUSH_AUTO_SCHEDULE;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			flag = configurationParamsEntitys.get(0).getConfValue();//开关是否关闭
		}
		return FossConstants.YES.equals(flag);
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	public void setLabelPushProcessService(
			ILabelPushProcessService labelPushProcessService) {
		this.labelPushProcessService = labelPushProcessService;
	}

	public void setOwsDopLabelInfoPushProcessAutoHandlerService(
			IOwsDopLabelInfoPushProcessAutoHandlerService owsDopLabelInfoPushProcessAutoHandlerService) {
		this.owsDopLabelInfoPushProcessAutoHandlerService = owsDopLabelInfoPushProcessAutoHandlerService;
	}
}
