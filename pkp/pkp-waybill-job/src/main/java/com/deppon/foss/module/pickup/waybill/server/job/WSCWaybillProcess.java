package com.deppon.foss.module.pickup.waybill.server.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWSCWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IAutoAddWSCWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.vo.WSCWaybillProcessVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * @author 350909   郭倩云
 * 从数据库表PKP.T_SRV_ADD_ASYN_WAYBILL中查询运单信息和代刷卡数据信息,然后调用结算的接口将数据推送给结算
 *
 */
public class WSCWaybillProcess extends OrderThreadPoolCaller{
	//是否调用结算接口生成财务单据和待刷卡数据的job开关
	private  static final String WSCWAYBILL_JOB_AUTO_SCHEDULE = WaybillConstants.WSCWAYBILL_JOB_AUTO_SCHEDULE;
	private IConfigurationParamsService configurationParamsService;
	private IWSCWaybillProcessDao  wSCWaybillProcessDao;
	private IAutoAddWSCWaybillProcessService autoAddWSCWaybillProcessService;
	/**
	 * @author 350909   郭倩云
	 * 主要的业务逻辑方法
	 */
	public int serviceCaller() {
		String flag = FossConstants.NO;
		String[] codes = new String[1];
		codes[0]=WSCWAYBILL_JOB_AUTO_SCHEDULE;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			//开关是否关闭
			flag = configurationParamsEntitys.get(0).getConfValue();
		}
		//不存在开关或者开关关闭
		if(!StringUtils.equals(FossConstants.YES, flag)){
			return 0;
		}else{
			String jobId = UUIDUtils.getUUID();
			String queryJobId = WaybillConstants.UNKNOWN;
			WSCWaybillProcessVo  wSCWaybillProcessVo=new WSCWaybillProcessVo();
			wSCWaybillProcessVo.setJobId(jobId);
			wSCWaybillProcessVo.setQueryNum(readChangeCount());
			wSCWaybillProcessVo.setQueryJobId(queryJobId);
			wSCWaybillProcessVo.setModifyTime(new Date());
			//将数据库表PKP.T_SRV_ADD_ASYN_WAYBILL中的jobId字段由N/A更新成uuid
			int result=wSCWaybillProcessDao.updateJobIDTopByRowNum(wSCWaybillProcessVo);
			if(result>0){
				//根据jobId执行具体的业务逻辑
				autoAddWSCWaybillProcessService.process(jobId);
			}else{
				return 0;
			}
			return result;
		}
	}
	
	/**
	 * 
	 * 线程每次跑需要读取的数量
	 */
	private String readChangeCount(){
		String num = "20";
		return num;
	}

	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public IWSCWaybillProcessDao getwSCWaybillProcessDao() {
		return wSCWaybillProcessDao;
	}

	public void setwSCWaybillProcessDao(IWSCWaybillProcessDao wSCWaybillProcessDao) {
		this.wSCWaybillProcessDao = wSCWaybillProcessDao;
	}

	public IAutoAddWSCWaybillProcessService getAutoAddWSCWaybillProcessService() {
		return autoAddWSCWaybillProcessService;
	}

	public void setAutoAddWSCWaybillProcessService(
			IAutoAddWSCWaybillProcessService autoAddWSCWaybillProcessService) {
		this.autoAddWSCWaybillProcessService = autoAddWSCWaybillProcessService;
	}

}
