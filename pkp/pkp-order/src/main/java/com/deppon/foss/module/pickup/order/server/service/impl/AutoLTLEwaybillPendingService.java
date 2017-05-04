package com.deppon.foss.module.pickup.order.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoLTLEwaybillPendingService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOmsOrderDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaAppInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 零担电子面单待补录处理Service
 * @author （323098王鹏涛）
 * @date 2016年8月1日
 */
public class AutoLTLEwaybillPendingService extends OrderTheadPool implements IAutoLTLEwaybillPendingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoLTLEwaybillPendingService.class);
	/**
	 * 零担电子面单待补录--数据集中处理Service
	 */
	private ILTLEWaybillService lTLEWaybillService;
	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * OMS订单处理DAO
	 */
	private IOmsOrderDao omsOrderDao;
	private ICrmOrderJMSService crmOrderJMSService;
	/**
	 * 零担电子面单日志DAO
	 */
	private IWaybillProcessLogDao waybillProcessLogDao;
	/**
	 * 零担电子面单激活处理DAO
	 */
	
	private IWaybillProcessDao waybillProcessDao;
	
	private IOmsOrderService omsOrderService;
	
	private IPdaAppInfoDao pdaAppInfoDao;
	
	private ILTLEWaybillProcessService lTLEWaybillProcessService;
	

	public void setlTLEWaybillService(ILTLEWaybillService lTLEWaybillService) {
		this.lTLEWaybillService = lTLEWaybillService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setOmsOrderDao(IOmsOrderDao omsOrderDao) {
		this.omsOrderDao = omsOrderDao;
	}

	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}

	@Override
	public void process(String jobId) {
		LOGGER.info("零担电子面单线程池jobid:"+jobId);
		pushThreadsPool(jobId);
	}
	
	@Override
	public void businessExecutor(Object obj) {
		String jobId = (String)obj;
		LOGGER.info("零担电子面单线程池执行executor:"+jobId);
		List<WaybillProcessEntity> waybillProcessEntityList = lTLEWaybillProcessService.queryWaybillProcessListByJobId(jobId);
		for (WaybillProcessEntity entity : waybillProcessEntityList) {
			Date start = new Date();
			//oms订单信息
			String waybillStatus = WaybillConstants.WAYBILL_STATUS_PDA_PENDING;
			String orderNo = null;
			String waybillNo = null;
			PdaAppInfoEntity pdaAppInfo = null; 
			String driverCode = null;
			String exceptionCode = null;
			String exceptionMessage = "";
			BigDecimal gvt = null;
			BigDecimal gwt = null;
			boolean addActiveProcess = false;
			boolean isAddActiveProcess = false;
			try {
				orderNo = entity.getOrderNo();
				waybillNo = entity.getWaybillNo();
				entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS);
				OmsOrderEntity omsOrderEntity = omsOrderService.queryOmsOrderByWaybillNo(waybillNo);
				/**
				 * 获取pdaAppInfo
				 */
				pdaAppInfo = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(waybillNo);
				if(pdaAppInfo != null && !StringUtils.isEmpty(pdaAppInfo.getDriverCode())){
					driverCode = pdaAppInfo.getDriverCode();
					String scan = pdaAppInfo.getScan();
					gvt = pdaAppInfo.getGoodsVolumeTotal();
					gwt = pdaAppInfo.getGoodsWeightTotal(); 
					/**
					 * 符合激活条件
					 */
					if(scan!=null&&null!=gvt&&null!=gwt
					&&gvt.compareTo(BigDecimal.ZERO)>0
					&&gwt.compareTo(BigDecimal.ZERO)>0
					&&WaybillConstants.LTLEWAYBILL_SCAN.equals(scan)){
						isAddActiveProcess = true;
					}
				}				
				LOGGER.info("待补录的订单号:"+orderNo+",运单号："+waybillNo);
				//处理待补录的业务逻辑
				lTLEWaybillService.insertAndUpdatePending(omsOrderEntity);
				/**
				 * 当存在重量体积时候才
				 */
				if(isAddActiveProcess){
					addActiveProcess = true;
				}
			}catch(BusinessException be){
				exceptionMessage = ExceptionUtils.getFullStackTrace(be);
				exceptionCode = be.getErrorCode();
				waybillStatus = WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN;
				entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
				LOGGER.error("零担电子运单生成代补录业务异常:" + exceptionMessage , be);
			}catch(Exception e){
				exceptionMessage = ExceptionUtils.getFullStackTrace(e);
				exceptionCode = WaybillValidateException.EWAYBILL_UNKOWN_EXCEPTION;
				waybillStatus = WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN;
				entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
				LOGGER.error("零担电子运单生成代补录异常:" + exceptionMessage , e);
			}finally{
				Date end = new Date();
				long time = end.getTime() - start.getTime();
				waybillProcessLogDao.saveLog(null, orderNo , waybillNo , new Date(), WaybillConstants.LTLEWAYBILL_GENERATE_WAYBILL_PENDING_MONITOR_LOG, time+"",null);
				/**
				 * 记录日志并且推送消息,在处理是否WaybillProcess的信息
				 * 如果在PushOmsOrderInfoStatust中出现异常，导致为handleActiveWaybillProcess
				 * 将在上层异常中处理WaybillProecss代补录记录强行修改WaybillProcess的SecondKey值为uuid
				 */
				try{
					LOGGER.info("生成待补录推送状态订单号:"+orderNo+",运单号:"+waybillNo);
					crmOrderJMSService.pushOmsOrderInfoStatust(orderNo , waybillNo , WaybillConstants.LTLEWAYBILL_GENERATE_WAYBILL_PENDING_LOG , null,waybillStatus, driverCode, exceptionCode,exceptionMessage);
				}catch(Exception e){
					LOGGER.error("待补录推送状态异常："+e.getMessage(), e);
				}
				lTLEWaybillService.handleActiveWaybillProcess(entity,addActiveProcess,waybillStatus,exceptionCode);
			}
		}
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoLTLEWaybillActiveService|零担电子面单批量待补录|线程池满异常处理|开始");
		// 入参转换与校验
		String jobId = (String)obj;
		List<WaybillProcessEntity> waybillProcessEntityList = lTLEWaybillProcessService.queryWaybillProcessListByJobId(jobId);
		if (CollectionUtils.isEmpty(waybillProcessEntityList)) {
			return;
		}
		/**
		 * 关于线程池异常回滚，线程表数据回滚
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldJobId", jobId);
		map.put("newJobId", WaybillConstants.UNKNOWN);
		map.put("modifyTime", new Date());
		//将jobId置为N/A
		lTLEWaybillProcessService.updateWaybillProcessByJobId(map);
		// 处理日志
		for(WaybillProcessEntity waybillProcessEntity : waybillProcessEntityList){
			// 查询订单是否存在，不存在就不记录失败日志了
			OmsOrderEntity omsOrderEntity = omsOrderDao.queryOmsOrderByWaybillNo(waybillProcessEntity.getWaybillNo());
			if(omsOrderEntity == null){
				continue;
			}
			// 组装日志记录
			WaybillProcessLogEntity waybillProcessLogEntity = new WaybillProcessLogEntity();
			waybillProcessLogEntity.setId(UUIDUtils.getUUID());
			waybillProcessLogEntity.setWaybillNo(waybillProcessEntity.getWaybillNo());
			waybillProcessLogEntity.setCreateTime(new Date());
			waybillProcessLogEntity.setModifyTime(new Date());
			waybillProcessLogEntity.setOperateResult("失败");
			waybillProcessLogEntity.setFailResion("线程池满");
			// 记录日志
			waybillProcessLogDao.addWaybillProcessLogEntity(waybillProcessLogEntity);
		}
		LOGGER.info("AutoLTLEWaybillActiveService|零担电子面单批量待补录|线程池满异常处理|结束");
	}

	@Override
	public int getActiveThreads() {
		Integer initThreads = 3;
		String[] codes = new String[] { WaybillConstants.PKP_LTL_EWAYBILL_PENDING_COUNT };
		List<ConfigurationParamsEntity> configList = configurationParamsService
				.queryConfigurationParamsBatchByCode(codes);
		if (CollectionUtils.isNotEmpty(configList) && configList.get(0) != null) {
			initThreads = Integer.valueOf(configList.get(0).getConfValue());
		}
		return initThreads;
	}

	public ICrmOrderJMSService getCrmOrderJMSService() {
		return crmOrderJMSService;
	}

	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	public IWaybillProcessDao getWaybillProcessDao() {
		return waybillProcessDao;
	}

	public void setWaybillProcessDao(IWaybillProcessDao waybillProcessDao) {
		this.waybillProcessDao = waybillProcessDao;
	}

	public IOmsOrderService getOmsOrderService() {
		return omsOrderService;
	}

	public void setOmsOrderService(IOmsOrderService omsOrderService) {
		this.omsOrderService = omsOrderService;
	}

	public IPdaAppInfoDao getPdaAppInfoDao() {
		return pdaAppInfoDao;
	}

	public void setPdaAppInfoDao(IPdaAppInfoDao pdaAppInfoDao) {
		this.pdaAppInfoDao = pdaAppInfoDao;
	}

	public ILTLEWaybillProcessService getlTLEWaybillProcessService() {
		return lTLEWaybillProcessService;
	}

	public void setlTLEWaybillProcessService(
			ILTLEWaybillProcessService lTLEWaybillProcessService) {
		this.lTLEWaybillProcessService = lTLEWaybillProcessService;
	}

	public ILTLEWaybillService getlTLEWaybillService() {
		return lTLEWaybillService;
	}

	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	public IOmsOrderDao getOmsOrderDao() {
		return omsOrderDao;
	}

	public IWaybillProcessLogDao getWaybillProcessLogDao() {
		return waybillProcessLogDao;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

}
