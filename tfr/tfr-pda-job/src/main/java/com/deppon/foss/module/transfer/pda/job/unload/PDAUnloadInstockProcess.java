package com.deppon.foss.module.transfer.pda.job.unload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.importing.excel.exception.ExcelException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadInstockService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadInStockThreadPoolService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;
import com.deppon.foss.module.transfer.unload.api.tools.UnloadInstockThreadPoolcaller;
import com.deppon.foss.util.define.FossConstants;

public class PDAUnloadInstockProcess extends UnloadInstockThreadPoolcaller {
	
	//创建线程池对象 （Spring 注入出问题 所以直接new）
	private IUnloadInStockThreadPoolService unloadInStockThreadPoolService ;

	public void setUnloadInStockThreadPoolService(
			IUnloadInStockThreadPoolService unloadInStockThreadPoolService) {
		this.unloadInStockThreadPoolService = unloadInStockThreadPoolService;
	}
	private IConfigurationParamsService configurationParamsService;
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 卸车运单入库--线程
	 * @author 105869-heyongdong
	 * @date 2015年3月21日 11:01:07
	 * 
	 * */
	@Override
	public int serviceCaller() {
		//为卸车运单入库临时表加锁
		String jobId = upateUnloadMsgForJob();
		//获取要入库的运单
		List<PDAUnloadAsyncBillMsgDto>  inOutStockMsgs = getInStockMsg(jobId);
		if(CollectionUtils.isNotEmpty(inOutStockMsgs) && inOutStockMsgs.size()>0){
			//加入线程池
			unloadInstockList(inOutStockMsgs);
			return inOutStockMsgs.size();
		}
		return 0;
	}

	//为卸车运单入库信息表加锁
	private String upateUnloadMsgForJob(){
		return pdaUnloadInstockService.upateUnloadMsgForJob();
		 
	}
	//获取要入库的运单集合
	private List<PDAUnloadAsyncBillMsgDto> getInStockMsg(String jobId){
		return pdaUnloadInstockService.queryInStockMsg(jobId);
	}
	
	
	//
	public IPDAUnloadInstockService pdaUnloadInstockService;


	public void setPdaUnloadInstockService(
			IPDAUnloadInstockService pdaUnloadInstockService) {
		this.pdaUnloadInstockService = pdaUnloadInstockService;
	}

	private int unloadInstockList(List<PDAUnloadAsyncBillMsgDto>  inOutStockMsgs){
		if(CollectionUtils.isEmpty(inOutStockMsgs) && inOutStockMsgs.size()<=0){
			return 0;
		}
		String waybillNo= inOutStockMsgs.get(0).getWayBillNo();
		String orgCode=inOutStockMsgs.get(0).getOrgCode();
		//一票运单集合
		List<PDAUnloadAsyncBillMsgDto>  inStockEntitys=new ArrayList<PDAUnloadAsyncBillMsgDto>();
		// 将每一运单集合都放入线程池
		for(PDAUnloadAsyncBillMsgDto temp:inOutStockMsgs) {
			
			try {
				//该运单是同一个运单入库部门一样则加入集合
				if(StringUtil.equals(waybillNo, temp.getWayBillNo())&&StringUtil.equals(orgCode, temp.getOrgCode())){
					
					inStockEntitys.add(temp);
					
				}else{
					unloadInStockThreadPoolService.pushDate(inStockEntitys);
					//重新设置运单 和库存部门
					waybillNo=temp.getWayBillNo();
					orgCode=temp.getOrgCode();
					inStockEntitys=new ArrayList<PDAUnloadAsyncBillMsgDto>();
					inStockEntitys.add(temp);
				}
			} catch (Exception e) {
				//dispatchOrderChangeJobId(dispatchOrderEntity);
				e.printStackTrace();
			}
		}
		
		//把最后一条运单加入线程池中
		if(CollectionUtils.isNotEmpty(inStockEntitys) && inStockEntitys.size()>0){
			unloadInStockThreadPoolService.pushDate(inStockEntitys);
		}
		return 0;
	}
	
	
	/**
	 * 获取主线程休眠时间 （毫秒）
	 * @author 105869
	 * @date 2015年3月24日 10:58:17
	 */
	@Override
	public long getSleepTime() {
		
		ConfigurationParamsEntity paramEntity;
		long sleepTime =1000l;
		//获取最大线程数
		try {
			paramEntity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM_PDA_UNLOAD_INSTOCK_SLEEP_TIME,
							FossConstants.ROOT_ORG_CODE);
			if(paramEntity != null){
			
				String value = paramEntity.getConfValue();
				//从数据字典获取线程数
				sleepTime = Long.parseLong(value);
			}
			
		} catch (Exception e) {
			
		}
		return sleepTime;
	}
	
	/**
	 * 把线程异常停止，导致没有处理的数据 重置站位符
	 * @author 105869
	 * @date 2015年3月25日 15:17:18
	 * @see com.deppon.foss.module.transfer.unload.api.tools.UnloadInstockThreadPoolcaller#reSetDate()
	 */
	@Override
	public void reSetDate() {
		try{
			pdaUnloadInstockService.reSetUnloadInstockMsg();		
		}catch(ExcelException e){
			//异常不影响线程正常启动
		}
	}
	
}
