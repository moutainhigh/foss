package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadInStockThreadPoolService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;
import com.deppon.foss.module.transfer.unload.api.tools.UnloadTheadPool;
import com.deppon.foss.util.define.FossConstants;


public class UnloadInStockThreadPoolService  extends UnloadTheadPool implements IUnloadInStockThreadPoolService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UnloadInStockThreadPoolService.class);
	
//	//构造方法 初始化线程池  spring 注入出问题所以在构造方法内初始话 
//	public UnloadInStockThreadPoolService(){
//		super.afterPropertiesSet();
//	}
	/** 
	 * The stock service. 
	 * 
	 */
	private IStockService stockService;
	/**
	 * Sets the stock service.
	 *
	 * @param stockService the new stock service
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	//公共类
	private ITfrCommonService  tfrCommonService;
	
	
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/** 
	 * The pda unload task dao.
	 * 
	 * 
	 */
	private IPDAUnloadTaskDao pdaUnloadTaskDao;
	/**
	 * Sets the pda unload task dao.
	 *
	 * @param pdaUnloadTaskDao the new pda unload task dao
	 */
	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}
	private IConfigurationParamsService configurationParamsService;
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	
	@Override
	public void businessExecutor(Object obj) {
		
		List<PDAUnloadAsyncBillMsgDto> inStockMsgs = (List<PDAUnloadAsyncBillMsgDto>) obj;
		try {
			
			if (CollectionUtils.isNotEmpty(inStockMsgs)
					&& inStockMsgs.size() > 0) {

				//id集合
				List<String> idList = new ArrayList<String>();
				List<InOutStockEntity> inStockEntys = new ArrayList<InOutStockEntity>();
				for (PDAUnloadAsyncBillMsgDto temp : inStockMsgs) {
					//该运单是同一个运单入库部门一样则加入集合
					idList.add(temp.getId());
					//入库运单实体
					InOutStockEntity inStockEnty = new InOutStockEntity();
					inStockEnty.setWaybillNO(temp.getWayBillNo());
					inStockEnty.setSerialNO(temp.getSerialNo());
					inStockEnty.setOrgCode(temp.getOrgCode());
					inStockEnty.setOperatorCode(temp.getOperatorCode());
					inStockEnty.setOperatorName(temp.getOperatorName());
					inStockEnty.setInOutStockType(temp.getInStockType());
					inStockEnty.setBePackage(temp.getBePackage());
					//营业部交接- 加入交接单号  by linhua.yan 360903
					inStockEnty.setInOutStockBillNO(temp.getBillNo());
					
					inStockEntys.add(inStockEnty);
				}
				//删除入库运单从卸车入库运单临时表中 
				//先于 入库前删除是因为入库接口异常时也会删除该运单，所以提前删除，放在入库后面删除则入库异常后不能删除临时表数据
				pdaUnloadTaskDao.deleteUnloadAsyncMsg(idList);
				//调用批量入库接口
				stockService.batchInStockUnload(inStockEntys);
			}
		} catch (Exception e) {
			if(CollectionUtils.isNotEmpty(inStockMsgs) && inStockMsgs.size()>0){
				//运单号
				String waybillNo = inStockMsgs.get(0).getWayBillNo();
				LOGGER.info("运单："+waybillNo+"入库失败！错误："+e.toString());
				//异常插入job日志中
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName("PDA卸车扫描入库线程:"+Thread.currentThread().getName());
				jobProcessLogEntity.setBizCode("PDA卸车扫描入库线程类:"+Thread.class.getClass().getName());
				jobProcessLogEntity.setRemark("运单："+waybillNo+"入库失败！");
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
		}
		
		
	}

	@Override
	public void outOfUnloadPool(Object obj) {
		LOGGER.info("AutoOrderHandleService【线程池满异常处理开始。。。。。。】");
		//插入日志
		List<PDAUnloadAsyncBillMsgDto>  inStockMsgs =(List<PDAUnloadAsyncBillMsgDto>) obj;
		if(CollectionUtils.isNotEmpty(inStockMsgs) && inStockMsgs.size()>0){
			//运单号
			String waybillNo = inStockMsgs.get(0).getWayBillNo();
			LOGGER.info("运单："+waybillNo+"入库失败！线程池已满");
			//异常插入线程日志中
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName("PDA卸车扫描入库线程:"+Thread.currentThread().getName());
			jobProcessLogEntity.setBizCode("PDA卸车扫描入库线程类:"+Thread.class.getClass().getName());
			jobProcessLogEntity.setRemark("运单："+waybillNo+"入库失败！");
			jobProcessLogEntity.setExceptionInfo("线程池满");
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			//更新卸车入库运单信息表中jobid状态为N/A   
			pdaUnloadTaskDao.updateUnloadAsyncBillJobId(inStockMsgs);
		}
		
		LOGGER.info("AutoOrderHandleService【线程池满异常处理结束。。。。。。】");
	}
	
	/**
	 * 获取线程数
	 * @author 105869
	 * @date 2015年3月21日 17:13:13
	 * @see com.deppon.foss.module.transfer.unload.api.tools.UnloadTheadPool#getActiveThreads()
	 */
	@Override
	public int getActiveThreads() {
		ConfigurationParamsEntity paramEntity;
		int threadCount =ConstantsNumberSonar.SONAR_NUMBER_50;
		//获取最大线程数
		try {
			paramEntity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM_PDA_UNLOAD_INSTOCK_JOB_COUNT,
							FossConstants.ROOT_ORG_CODE);
			if(paramEntity!= null){
				String value = paramEntity.getConfValue();
				//从数据字典获取线程数
				 threadCount = Integer.parseInt(value);
			}
			
		} catch (Exception e) {
			LOGGER.info("从数据字典获取卸车入库线程数异常！"+e.toString());
		}
		return threadCount;
	}


	@Override
	public void pushDate(Object object) {
		pushThreadsPool(object);
		
	}

}
