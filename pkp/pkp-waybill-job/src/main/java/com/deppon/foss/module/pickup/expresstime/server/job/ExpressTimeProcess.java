package com.deppon.foss.module.pickup.expresstime.server.job;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressTimeService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEamDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
	/**
	 *快递自动补录多线程PDA开单时时效推送
	 * YangXiaolong
	 *  220125
	 *
	 */
public class ExpressTimeProcess extends OrderThreadPoolCaller {
	private ISysConfigService pkpsysConfigService;
	private IEamDao eamDao;
	private IAutoExpressTimeService autoExpressTimeService;
	
	protected final static Logger LOG = LoggerFactory
			.getLogger(ExpressTimeProcess.class.getName());
	@Override
	public int serviceCaller() {
		//开关是否关闭 开着 走快递集中录单线程 从配置参数中读取快递集中录单开关值
		LOG.info("PDA开单时效传WICS开始=============================================");
		String flag = FossConstants.NO;
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						//开始测试job时，一定注意在数据库里的脚本配置，且该参数是针对于快递自动补录
						WaybillConstants.EXPRESS_PDA_TIME_SET,FossConstants.ROOT_ORG_CODE);
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
		int result=eamDao.updateJobIDtimeLookByRowNum(vo);
		//先更新一条线程查询的电子运单数
		if(StringUtils.isNotEmpty(jobId)){
			autoExpressTimeService.process(jobId);
		}else{
			return 0;
		}
		return result;
	}
	/**
	 *快递自动补录多线程PDA开单时时效推送：获取批量处理数据配置
	 * YangXiaolong 
	 * 220125
	 */
	private String readChangeCount(){
		String num = "2000";
		// 后台设置当前每个周期要处理的订单数，后期通过数据字典配置
		ConfigurationParamsEntity configQueryNum = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.PKP_EXPRESS_TIMESET_CYCLE,
						FossConstants.ROOT_ORG_CODE);
		if(configQueryNum != null && StringUtils.isNotBlank(configQueryNum.getConfValue())){
			num = configQueryNum.getConfValue();
		}
		return num;
	}
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	public void setEamDao(IEamDao eamDao) {
		this.eamDao = eamDao;
	}
	public void setAutoExpressTimeService(
			IAutoExpressTimeService autoExpressTimeService) {
		this.autoExpressTimeService = autoExpressTimeService;
	}
}