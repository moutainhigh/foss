package com.deppon.foss.module.pickup.order.server.service.impl;

import java.math.BigDecimal;
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
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderLogEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderThreadPoollogDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoEWaybillCzmActiveHandleService;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 进行电子面单待激活运单数据的生成
 * @author Foss-105888-Zhangxingwang
 * @date 2015-3-19 10:35:25
 */
/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-151211-yangtaohong,date:2016-8-14 下午5:40:24,content:TODO </p>
 * @author Foss-151211-yangtaohong 
 * @date 2016-8-14 下午5:40:24
 * @since
 * @version
 */
public class AutoEWaybillCzmActiveHandleService extends OrderTheadPool implements IAutoEWaybillCzmActiveHandleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoEWaybillCzmActiveHandleService.class);
	
	/**
	 * 电子面单集中处理服务类
	 */
	private IEWaybillService ewaybillService;
	
	private IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	
	private IEWaybillOrderLogEntityDao ewaybillOrderLogEntityDao;
	
	private IConfigurationParamsService configurationParamsService;
	
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/**
	 *  线程池异常日志
	 */
	private IOrderThreadPoollogDao orderThreadPoollogDao;
	/**
	 * 子母件数据持久层
	 */
	private IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao;
	
	@Override
	public void businessExecutor(Object obj) {
		//进行数据转换
		String jobId = (String) obj;
		LOGGER.info("子母件开始:jobId="+jobId);
		ewaybillService.batchGenerateActiveWaybillRelateJobs(jobId);
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoEWaybillActiveHandleService【线程池满异常处理开始。。。。。。】");
		//进行数据转换
		String jobId = (String) obj;
		if(StringUtils.isEmpty(jobId)){
			return;
		}
		//原来业务逻辑代码存在问题导致线程池满时无日志记录，需要修复151211，杨套红 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", jobId);
		List<EwaybillRelateEntity> ewaybillRelateList = waybillRelateDetailEntityService.queryAllEwaybillRelateByCommon(params);
		if(CollectionUtils.isEmpty(ewaybillRelateList)){
			return;
		}	
		//子母件激活线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次子母件激活后续流程，151211，杨套红
		waybillRelateDetailEntityService.updateJobIdForActive(jobId);
		
		//进行数据的查询与插入
		EWaybillOrderLogEntity entityLog = new EWaybillOrderLogEntity();
		OrderThreadPoollogEntity record = new OrderThreadPoollogEntity();
		for(EwaybillRelateEntity ewaybillRelateEntity : ewaybillRelateList){
			//创建订单日志记录数据
				entityLog.setId(UUIDUtils.getUUID());
				entityLog.setCreateTime(new Date());
				entityLog.setModifyTime(new Date());
				//针对子母件从子母件线程表只能取出原始单号
				entityLog.setOrderNO(ewaybillRelateEntity.getParentOrderNo());
				//设置为失败
				entityLog.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
				entityLog.setFailReason("线程池满，订单号存储为原始单号"+ewaybillRelateEntity.getFailReason());
				ewaybillOrderLogEntityDao.addEWaybillOrderLogEntity(entityLog);
				//并将该日志写入线程池异常日志
				record.setId(UUIDUtils.getUUID());
				record.setServiceName("AutoEWaybillCzmActiveHandleService");
				record.setExceptionData(ewaybillRelateEntity.getId()+"-"+ewaybillRelateEntity.getParentOrderNo());
				record.setExceptionPoolname("");
				record.setFailTimes(new BigDecimal(1));
				record.setCreateDate(new Date());
				record.setExceptionMsg("线程池满异常"+ewaybillRelateEntity.getFailReason());
				orderThreadPoollogDao.insert(record);
			} 
		LOGGER.info("AutoEWaybillActiveHandleService【线程池满异常处理结束。。。。。。】");
	}
	@Override
	public int getActiveThreads() {
		Integer initThreads=3;
		String[] codes = new String[1];
		codes[0]=WaybillConstants.PKP_EWAYBILL_AUTO_CZMTHREAD_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			if(null!=configurationParamsEntitys.get(0).getConfValue()){
				initThreads = Integer.valueOf(configurationParamsEntitys.get(0).getConfValue());
			}
		}
		return initThreads;
	}

	public void setEwaybillService(IEWaybillService ewaybillService) {
		this.ewaybillService = ewaybillService;
	}
	
	public void setEwaybillOrderEntityDao(IEWaybillOrderEntityDao ewaybillOrderEntityDao) {
		this.ewaybillOrderEntityDao = ewaybillOrderEntityDao;
	}
	
	public void setEwaybillOrderLogEntityDao(IEWaybillOrderLogEntityDao ewaybillOrderLogEntityDao) {
		this.ewaybillOrderLogEntityDao = ewaybillOrderLogEntityDao;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	@Override
	public void process(String jobId) {
		pushThreadsPool(jobId);
	}

	public void setWaybillRelateDetailEntityDao(
			IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao) {
		this.waybillRelateDetailEntityDao = waybillRelateDetailEntityDao;
	}
}
