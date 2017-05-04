package com.deppon.foss.module.pickup.order.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderLogEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderThreadPoollogDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoEWaybillUnActiveHandleService;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.UUIDUtils;

/**
 * 进行电子面单待激活运单数据的生成
 * @author Foss-105888-Zhangxingwang
 * @date 2015-3-19 10:35:25
 */
public class AutoEWaybillUnActiveHandleService extends OrderTheadPool implements IAutoEWaybillUnActiveHandleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoEWaybillUnActiveHandleService.class);
	
	/**
	 * 电子面单集中处理服务类
	 */
	private IEWaybillService ewaybillService;
	
	private IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	
	private IEWaybillOrderLogEntityDao ewaybillOrderLogEntityDao;
	
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 *  线程池异常日志
	 */
	private IOrderThreadPoollogDao orderThreadPoollogDao;
	
	@Override
	public void businessExecutor(Object obj) {
		//进行数据转换
		String jobId = (String) obj;
		ewaybillService.batchHandleEWaybillThread(jobId);
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoEWaybillActiveHandleService【线程池满异常处理开始。。。。。。】");
		//进行数据转换
		String jobId = (String) obj;
		if(StringUtils.isEmpty(jobId)){
			return;
		}
		List<EWaybillOrderEntity> eWaybillOrderList = ewaybillOrderEntityDao.queryGenerateUnActiveEWaybillByJobID(jobId);
		//判定运单号集合是否为空
		if(CollectionUtils.isEmpty(eWaybillOrderList)){
			return;
		}
		//待激活线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次待激活后续流程，151211，杨套红
		ewaybillOrderEntityDao.activeJobIdByJobId(jobId);				
		//进行数据的查询与插入
		EWaybillOrderLogEntity entityLog = new EWaybillOrderLogEntity();
		OrderThreadPoollogEntity record = new OrderThreadPoollogEntity();
		for(EWaybillOrderEntity eWaybillOrderEntity : eWaybillOrderList){
			//创建订单日志记录数据
			//进行数据的拷贝与其他数据操作
			try {
				BeanUtils.copyProperties(entityLog,eWaybillOrderEntity);
				//设置ID等数据
				entityLog.setId(UUIDUtils.getUUID());
				entityLog.setCreateTime(new Date());
				entityLog.setModifyTime(new Date());
				//设置为失败
				entityLog.setOperateResult("线程池满");																// 20140723
				ewaybillOrderLogEntityDao.addEWaybillOrderLogEntity(entityLog);
				//并将该日志写入线程池异常日志
				record.setId(UUIDUtils.getUUID());
				record.setServiceName("AutoEWaybillUnActiveHandleService");
				record.setExceptionData(eWaybillOrderEntity.getId()+"-"+eWaybillOrderEntity.getOrderNO());
				record.setExceptionPoolname("");
				record.setFailTimes(new BigDecimal(1));
				record.setCreateDate(new Date());
				record.setExceptionMsg("线程池满异常"+eWaybillOrderEntity.getFailReason());
				orderThreadPoollogDao.insert(record);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LOGGER.info("AutoEWaybillActiveHandleService【线程池满异常处理结束。。。。。。】");
	}

	@Override
	public int getActiveThreads() {
		Integer initThreads=3;
		String[] codes = new String[1];
		codes[0]=WaybillConstants.PKP_EWAYBILL_UNACTIVE_COUNT;
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

	@Override
	public void process(String jobId) {
		pushThreadsPool(jobId);
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
}


