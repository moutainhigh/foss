package com.deppon.foss.module.transfer.unload.server.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadInstockService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PDAUnloadInstockService implements IPDAUnloadInstockService {
	static final Logger logger = LoggerFactory.getLogger(PDAUnloadInstockService.class);

	
	private IConfigurationParamsService configurationParamsService;
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
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
	
	/**
	 * PDA卸车运单入库
	 * @author 105869-heyongdong
	 * @date 2015年3月5日 17:17:55
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadInstockService#unloadInstock(int, int)
	 * */
	@Override
	public void unloadInstock(int threadNo, int threadCount) {
		//查询卸车需job入库的运单信息
	//	List<InOutStockEntity>  inOutStockEntitys = pdaUnloadTaskDao.queryInStockMsg(threadNo,threadCount);
//		if(CollectionUtils.isNotEmpty(inOutStockEntitys)&&inOutStockEntitys.size()>0){
//			String waybillNo= inOutStockEntitys.get(0).getWaybillNO();
//			String orgCode=inOutStockEntitys.get(0).getOrgCode();
//			//一票运单集合
//			List<InOutStockEntity>  inStockEntitys=new ArrayList<InOutStockEntity>();
//			//id集合
//			List<String> idList = new ArrayList<String>();
//			for(InOutStockEntity temp:inOutStockEntitys){
//				//该运单是同一个运单入库部门一样则加入集合
//				if(StringUtil.equals(waybillNo, temp.getWaybillNO())&&StringUtil.equals(orgCode, temp.getOrgCode())){
//					idList.add(temp.getId());
//					inStockEntitys.add(temp);
//					
//				}else{
//					//调用批量入库接口
//					try{
//						stockService.batchInStockUnload(inStockEntitys);
//						
//					}catch(Exception e){
//						logger.info("运单："+waybillNo+"入库失败！错误："+e.toString());
//						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
//						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PDA_UNLOAD_INSTOCK_JOB.getBizName());
//						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PDA_UNLOAD_INSTOCK_JOB.getBizCode());
//						jobProcessLogEntity.setRemark("运单："+waybillNo+"入库失败！");
//						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
//						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
//						
//						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
//					}finally{
//						//删除异步表中已入库的运单
//						pdaUnloadTaskDao.deleteUnloadAsyncMsg(idList);
//						//重新设置运单 和库存部门
//						waybillNo=temp.getWaybillNO();
//						orgCode=temp.getOrgCode();
//						inStockEntitys=new ArrayList<InOutStockEntity>();
//						inStockEntitys.add(temp);
//						idList = new ArrayList<String>();
//						idList.add(temp.getId());
//						
//					}
//					
//				}
//			}
//			//执行最后一个运单入库 前面的循环少入库最后一条
//			if(CollectionUtils.isNotEmpty(inStockEntitys)){
//				try{
//					stockService.batchInStockUnload(inStockEntitys);
//				}catch(Exception e){
//					logger.info("运单："+waybillNo+"入库失败！错误："+e.toString());
//					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
//					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PDA_UNLOAD_INSTOCK_JOB.getBizName());
//					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PDA_UNLOAD_INSTOCK_JOB.getBizCode());
//					jobProcessLogEntity.setRemark("运单："+waybillNo+"入库失败！");
//					jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
//					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
//					
//					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
//				}finally{
//					//删除异步表中已入库的运单
//					pdaUnloadTaskDao.deleteUnloadAsyncMsg(idList);
//				}
//			}
//			
//		}
//		//删除异步表中已入库的运单
//		//pdaUnloadTaskDao.deleteUnloadAsyncMsg(inOutStockEntitys);
//		
//		
//		
	}

	
	//创建线程池对象 （Spring 注入出问题 所以直接new）
//	private UnloadInStockThreadPoolService unloadInStockThreadPoolService = new UnloadInStockThreadPoolService();
	
	
	
	/**
	 * * 通过jobId查询卸车入库运单信息
	 * @author 105869
	 * @date 2015年3月20日 19:23:30
	 * @param jobId
	 * @return List<InOutStockEntity>
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadInstockService#queryInStockMsg(java.lang.String)
	 */
	@Override
	public List<PDAUnloadAsyncBillMsgDto> queryInStockMsg(String jobId) {
		return pdaUnloadTaskDao.queryInStockMsg(jobId);
	}
	
	/**
	 * 将处理的运单加入线程池
	 * @author 105869-heyongdong
	 * @date 2015年3月21日 11:06:39
	 * @param inOutStockEntitys
	 */
	@Override
	public int unloadInstockList(List<PDAUnloadAsyncBillMsgDto> inOutStockMsgs) {

		return 0;
	
	}
	/**
	 * 加业务锁
	 * */
	@Override
	public String upateUnloadMsgForJob() {
		String jobId = UUIDUtils.getUUID();
		
		ConfigurationParamsEntity paramEntity;
		int dateLimit =ConstantsNumberSonar.SONAR_NUMBER_1000;
		try {
			paramEntity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM_PDA_UNLOAD_INSTOCK_THREAD_COUNT,
							FossConstants.ROOT_ORG_CODE);
			if(paramEntity!=null){
				String value = paramEntity.getConfValue();
				dateLimit = Integer.parseInt(value);
			}
			
		} catch (Exception e) {
			logger.error("从数据字典获取PDA卸车入库线程查询的数据限制条数异常！"+e.toString());
		}
		
		//从数据字典获取线程数
		pdaUnloadTaskDao.upateUnloadMsgForJob(jobId,dateLimit);
		return jobId;
	}
	
	/**
	 * 重置线程异常停止没有处理的数据
	 * @author 105869
	 * @date 2015年3月25日 15:38:33
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadInstockService#reSetUnloadInstockMsg()
	 */
	@Override
	public void reSetUnloadInstockMsg() {
		pdaUnloadTaskDao.reSetUnloadInstockMsg(); 
	}

	
}
