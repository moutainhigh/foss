package com.deppon.foss.module.pickup.waybill.server.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoEWaybillUnActiveHandleService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class EWaybillGenerateProcess extends OrderThreadPoolCaller {
	private  static final String PKP_EWAYBILL_AUTO_SCHEDULE = WaybillConstants.PKP_EWAYBILL_AUTO_SCHEDULE;
	private static final String PKP_EWAYBILL_SCHEDULE_COUNT =WaybillConstants.PKP_EWAYBILL_SCHEDULE_COUNT;
	/**
	 * 电子运单订单实体DAO
	 */
	private IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	private IConfigurationParamsService configurationParamsService;
	
	private IAutoEWaybillUnActiveHandleService autoEWaybillUnActiveHandleService;
	@Override
	public int serviceCaller() {
		//开关是否关闭 开着 走电子运单线程 从配置参数中读取自动调度电子运单开关值
		//默认开关关闭
		String flag = FossConstants.NO;
		String[] codes = new String[1];
		codes[0]=PKP_EWAYBILL_AUTO_SCHEDULE;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			flag = configurationParamsEntitys.get(0).getConfValue();//开关是否关闭
		}
		if(!StringUtils.equals(FossConstants.YES, flag)){//不存在开关或者开关关闭
			return 0;
		}
		//更新数据
		String jobId = UUIDUtils.getUUID();
		String queryJobId = WaybillConstants.UNKNOWN;
		GenerateUnActiveEWaybillVo vo=new GenerateUnActiveEWaybillVo();
		vo.setJobId(jobId);
		vo.setQueryNum(readChangeCount());
		vo.setQueryJobId(queryJobId);
		int result=ewaybillOrderEntityDao.updateJobIDTopByRowNum(vo);
		//先更新一条线程查询的电子运单数
		if(StringUtils.isNotEmpty(jobId)){
			autoEWaybillUnActiveHandleService.process(jobId);
		}else{
			return 0;
		}
		return result;
	}
	
	/**
	 * 从配置参数中获得零担每个线程自动读取数量
	 * @return
	 */
	private String readChangeCount(){
		String num = "500";
		String[] codes = new String[1];
		codes[0]=PKP_EWAYBILL_SCHEDULE_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			num = configurationParamsEntitys.get(0).getConfValue();
		}
		return num;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setEwaybillOrderEntityDao(
			IEWaybillOrderEntityDao ewaybillOrderEntityDao) {
		this.ewaybillOrderEntityDao = ewaybillOrderEntityDao;
	}
	
	public void setAutoEWaybillUnActiveHandleService(IAutoEWaybillUnActiveHandleService autoEWaybillUnActiveHandleService) {
		this.autoEWaybillUnActiveHandleService = autoEWaybillUnActiveHandleService;
	}
}