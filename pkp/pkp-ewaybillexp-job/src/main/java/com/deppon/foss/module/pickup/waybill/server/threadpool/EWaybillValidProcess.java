package com.deppon.foss.module.pickup.waybill.server.threadpool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoEWaybillActiveHandleService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class EWaybillValidProcess extends OrderThreadPoolCaller {
	private IConfigurationParamsService configurationParamsService;
	private IEWaybillProcessService eWaybillProcessService;
	
	private IAutoEWaybillActiveHandleService autoEWaybillActiveHandleService;
	
	protected final static Logger LOG = LoggerFactory
			.getLogger(EWaybillValidProcess.class.getName());
	@Override
	public int serviceCaller() {
		//开关是否关闭 开着 走电子运单线程 从配置参数中读取自动调度电子运单开关值  默认开关关闭
		LOG.info("电子运单多线程激活开始=============================================");
		String flag = FossConstants.NO;
		String[] codes = new String[1];
		codes[0]=WaybillConstants.PKP_EWAYBILL_AUTO_VALID;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			//开关是否关闭
			flag = configurationParamsEntitys.get(0).getConfValue();
			LOG.info("电子运单多线程激活配置开关状态：================="+flag);
		}
		//不存在开关或者开关关闭
		if(!StringUtils.equals(FossConstants.YES, flag)){
			return 0;
		}
		int rowNum = 0;
		//先更新一条线程查询的电子运单数
		String jobId = updateActiveEWaybill();
		LOG.info("电子运单多线程激活批次JOBID：================="+jobId);
		if(StringUtils.isNotEmpty(jobId)){
			List<EWaybillProcessEntity> list = getEWaybillList(jobId);
			if(list!=null&&list.size()>0){
				autoEWaybillActiveHandleService.process(list);
				rowNum = list.size();
			}
		}
		if(rowNum > 0){
			return rowNum;
		}
		return 0;
	}
	
	/**
	 * 从配置参数中获得零担每个线程自动读取数量
	 * @return
	 */
	private String readChangeCount(){
		String num = "500";
		String[] codes = new String[1];
		codes[0]=WaybillConstants.PKP_EWAYBILL_AUTO_VALID_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			num = configurationParamsEntitys.get(0).getConfValue();
		}
		return num;
	}
	/**
	 * 更新线程锁
	 * updateActiveEWaybill
	 * Date:2014-7-9下午7:08:40
	 * @author 045925
	 * @return
	 * @since JDK 1.6
	 */
	public String updateActiveEWaybill(){
		String jobId = UUIDUtils.getUUID();
		Map<String, Object> map = new HashMap<String, Object>();
		String rownum = readChangeCount();
		map.put("jobId", jobId);
		map.put("rownum", rownum);
		map.put("modifyTime", new Date());
		int result = 0;
		result = eWaybillProcessService.updateForJob(map);
		if(result > 0){
			return jobId;
		}
		return null;
	}
	
	private List<EWaybillProcessEntity> getEWaybillList(String jobId){
		List<EWaybillProcessEntity> list  = null ;
		if(StringUtils.isEmpty(jobId)){
			return null;
		}
		//根据更新的JOBID获取所有激活运单集合
		list = eWaybillProcessService.queryAllByCommon(jobId);
		
		return list;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void seteWaybillProcessService(
			IEWaybillProcessService eWaybillProcessService) {
		this.eWaybillProcessService = eWaybillProcessService;
	}
	
	public void setAutoEWaybillActiveHandleService(IAutoEWaybillActiveHandleService autoEWaybillActiveHandleService) {
		this.autoEWaybillActiveHandleService = autoEWaybillActiveHandleService;
	}
}