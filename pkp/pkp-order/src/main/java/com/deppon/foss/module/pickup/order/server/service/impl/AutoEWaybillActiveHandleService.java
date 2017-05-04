package com.deppon.foss.module.pickup.order.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderLogEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderThreadPoollogDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoEWaybillActiveHandleService;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 电子面单数据的激活操作
 * @author Foss-105888-Zhangxingwang
 * @date 2015-3-19 10:36:05
 */
public class AutoEWaybillActiveHandleService extends OrderTheadPool implements IAutoEWaybillActiveHandleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoEWaybillActiveHandleService.class);
	/**
	 * 电子面单集中处理服务类
	 */
	private IEWaybillService ewaybillService;
	
	private IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	
	private IEWaybillOrderLogEntityDao ewaybillOrderLogEntityDao;
	
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * pkp.t_srv_ewaybil_process 数据持久层dao
	 */
	private IEWaybillProcessDao eWaybillProcessDao;

	/**
	 *  线程池异常日志
	 */
	private IOrderThreadPoollogDao orderThreadPoollogDao;

	@Override
	public void businessExecutor(Object obj) {
		EWaybillConditionDto eWaybillConditionDto = new EWaybillConditionDto();
		List<EWaybillProcessEntity> list = (List<EWaybillProcessEntity>) obj;
		List<String> waybillNos = new ArrayList<String>();
		for (EWaybillProcessEntity eprocess : list) {
			waybillNos.add(eprocess.getWaybillNo());
		}
		eWaybillConditionDto.seteWaybillNoList(waybillNos);
		ewaybillService.batchGenerateActiveEWaybillByPda(eWaybillConditionDto);
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoEWaybillActiveHandleService【线程池满异常处理开始。。。。。。】");
		List<EWaybillProcessEntity> list = (List<EWaybillProcessEntity>) obj;
		//判定运单号集合是否为空
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		EWaybillProcessEntity ewaybillProcess = list.get(0);
		//激活线程处理，线程池满数据回滚操作，将pkp.t_srv_ewaybil_process jobId 置为N/A,状态置为N待于再执行一次激活后续流程，151211，杨套红
		eWaybillProcessDao.updateProcessJobIdByIdForActive(ewaybillProcess.getJobId());
		
		//进行数据的查询与插入
		EWaybillOrderEntity entity = null;
		EWaybillOrderLogEntity entityLog = new EWaybillOrderLogEntity();
		OrderThreadPoollogEntity record = new OrderThreadPoollogEntity();
		for(EWaybillProcessEntity eprocess : list){
			String waybillNo = eprocess.getWaybillNo();
			//查询数据是否存在
			entity = ewaybillOrderEntityDao.queryEWaybillByWaybillNo(waybillNo);
			if(entity == null){
				continue;
			}
			//创建订单日志记录数据
			//进行数据的拷贝与其他数据操作
			try {
				//源跟目标一定不要写反了，要不被坑死
				BeanUtils.copyProperties(entityLog,entity);
				//设置ID等数据
				entityLog.setId(UUIDUtils.getUUID());
				entityLog.setCreateTime(new Date());
				entityLog.setModifyTime(new Date());
				//设置为失败
				entityLog.setOperateResult("线程池满");	
				ewaybillOrderLogEntityDao.addEWaybillOrderLogEntity(entityLog);
				//并将该日志写入线程池异常日志
				record.setId(UUIDUtils.getUUID());
				record.setServiceName("AutoEWaybillActiveHandleService");
				record.setExceptionData(entity.getId()+"-"+entity.getOrderNO());
				record.setExceptionPoolname("");
				record.setFailTimes(new BigDecimal(1));
				record.setCreateDate(new Date());
				record.setExceptionMsg("线程池满异常"+entity.getFailReason());
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

	/**
	 * 获得有效的线程数
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-19 10:03:08
	 */
	@Override
	public int getActiveThreads() {
		Integer initThreads=3;
		String[] codes = new String[1];
		codes[0]=WaybillConstants.PKP_EWAYBILL_ACTIVE_COUNT;
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
	public void process(List<EWaybillProcessEntity> list) {
		pushThreadsPool(list);
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void seteWaybillProcessDao(IEWaybillProcessDao eWaybillProcessDao) {
		this.eWaybillProcessDao = eWaybillProcessDao;
	}
}
