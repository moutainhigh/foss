package com.deppon.foss.module.pickup.expresswaybill.server.job;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressMakeupService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEamService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
	/**
	 *快递自动补录多线程生成运单定时任务
	 *YangXiaolong 
	 *220125
	 */
public class ExpressAutoProcess extends OrderThreadPoolCaller {
	private ISysConfigService pkpsysConfigService;
	private IEamService eamService;
	private IAutoExpressMakeupService autoExpressMakeupService;
	
	protected final static Logger LOG = LoggerFactory
			.getLogger(ExpressAutoProcess.class.getName());
	@Override
	public int serviceCaller() {
		//开关是否关闭 开着 走快递集中录单线程 从配置参数中读取快递集中录单开关值
		LOG.info("快递集中录单多线程录单开始=============================================");
		String flag = FossConstants.NO;
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
			//开始测试job时，一定注意在数据库里的脚本配置，且该参数是针对于快递自动补录
			WaybillConstants.PKP_EXPRESS_AUTO_MKKEUP,FossConstants.ROOT_ORG_CODE);
		if(config != null && StringUtils.isNotEmpty(config.getConfValue()) && 
		      FossConstants.YES.equals(config.getConfValue())){
			flag= FossConstants.YES;
		}   
		//不存在开关或者开关关闭
		if(!StringUtils.equals(FossConstants.YES, flag)){
			return 0;
		}
		//更新数据
		String jobId = UUIDUtils.getUUID();
		String queryJobId = WaybillConstants.UNKNOWN;
		ExpressAutoMakeupVo vo = new ExpressAutoMakeupVo();
		vo.setJobId(jobId);
		vo.setQueryNum(readChangeCount());
		vo.setQueryJobId(queryJobId);
		//新加异常数据处理方法
		long curren = System.currentTimeMillis();
		curren = curren-(NumberConstants.NUMBER_60 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_1000);
		Date date = new Date(curren);
		vo.setCreateTime(date);
		int result=eamService.updateJobIDTopByRowNum(vo);
		//先更新一条线程查询的电子运单数
		if(StringUtils.isNotEmpty(jobId)){
			autoExpressMakeupService.process(jobId);
		}else{
			return 0;
		}
		return result;
	}
	/**
	 *快递自动补录多线程生成运单定时任务
	 *YangXiaolong 
	 *220125
	 */
	private String readChangeCount(){
		String num = "500";
		// 后台设置当前每个周期要处理的订单数，后期通过数据字典配置
		ConfigurationParamsEntity configQueryNum = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.PKP_EXPRESS_GENERATE_CYCLE,
						FossConstants.ROOT_ORG_CODE);
		if(configQueryNum != null && StringUtils.isNotBlank(configQueryNum.getConfValue())){
			num = configQueryNum.getConfValue();
		}
		return num;
	}
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	public void setEamService(IEamService eamService) {
		this.eamService = eamService;
	}

	public void setAutoExpressMakeupService(
			IAutoExpressMakeupService autoExpressMakeupService) {
		this.autoExpressMakeupService = autoExpressMakeupService;
	}
}