package com.deppon.foss.module.pickup.waybill.server.threadpool;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IPreHandEWaybillOrderDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoPreHandEWaybillOrderService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.util.UUIDUtils;
/**
 * 预找到收货部门与对应快递员
 * @author Foss-105888-Zhangxingwang
 * @date 2015-4-24 19:23:59
 */
public class EWaybillPreHandleProcess extends OrderThreadPoolCaller {
	private IConfigurationParamsService configurationParamsService;
	
	private IAutoPreHandEWaybillOrderService autoPreHandEWaybillOrderService;
	
	private IPreHandEWaybillOrderDao preHandEWaybillOrderDao;
	@Override
	public int serviceCaller() {
		//更新数据
		String jobId = UUIDUtils.getUUID();
		GenerateUnActiveEWaybillVo vo=new GenerateUnActiveEWaybillVo();
		vo.setJobId(jobId);
		vo.setQueryNum(readChangeCount());
		vo.setQueryJobId(WaybillConstants.UNKNOWN);
		int result = preHandEWaybillOrderDao.updateJobIDTopByRowNum(vo);
		//先更新一条线程查询的电子运单数
		if(result > 0){
			autoPreHandEWaybillOrderService.process(jobId);
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
		codes[0] = WaybillConstants.PKP_EWAYBILL_SCHEDULE_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			num = configurationParamsEntitys.get(0).getConfValue();
		}
		return num;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setAutoPreHandEWaybillOrderService(IAutoPreHandEWaybillOrderService autoPreHandEWaybillOrderService) {
		this.autoPreHandEWaybillOrderService = autoPreHandEWaybillOrderService;
	}

	public void setPreHandEWaybillOrderDao(IPreHandEWaybillOrderDao preHandEWaybillOrderDao) {
		this.preHandEWaybillOrderDao = preHandEWaybillOrderDao;
	}
}