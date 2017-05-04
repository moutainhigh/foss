/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillRfcJobService.java
 * 
 * FILE NAME        	: WaybillRfcJobService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcJobService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoWaybillDto;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 运单变更定时任务service
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:43:41
 */
public class WaybillRfcJobService implements IWaybillRfcJobService {

 
    // 日志信息
    public static final Logger LOGGER = LoggerFactory
 			.getLogger(WaybillRfcJobService.class);
    
    /**
     * 系统常量service
     */
    private IConfigurationParamsService configurationParamsService;
    
    /**
     * TODO待办 dao 
     */
    private ILabeledGoodTodoDao labeledGoodTodoDao;
	
    /**
	 * 待办消息服务
	 */
	private IPickupToDoMsgService pickupToDoMsgService;
	
	/**
	 * 货物的入库、出库及查询库存方法
	 */
	private IStockService stockService;
	
	
	private IWaybillRfcJobService waybillRfcJobService;
    
	/**
	 *1 1）系统生成待办事项时货物未入受理部门库存，若（当前时间-货物入库时间）>X，即为处理超时，系统记录处理超时次数；
	 *  2）系统生成待办事项时货物已入受理部门库存，若（当前时间-生成待办事项时间）>X，即为处理超时，系统记录处理超时次数。
     *2.对于处理超时的运单变更，系统每隔Y时间发送一条催促信息给处理部门；
     *3.X与Y的值可在系统中进行配置；
     *4.待办事项处理后，系统不再提示催促信息。
     * 
	 */
   
	public void prepareSendMsg(){
    	
		
		//获得所有的remind time为null的对象列表
		List<LabeledGoodTodoWaybillDto> todoList = labeledGoodTodoDao.selectByNullRemindTime();
		
		//循环所有的todo对象
		for(LabeledGoodTodoWaybillDto entity : todoList){
			//这里要调用中转接口获得货物入库时间
			List<InOutStockEntity> stockList = stockService.queryInStockInfo(entity.getWaybillNo(), 
					entity.getSerialNo(), entity.getHandleOrgCode(), entity.getBillTime());
					
			if(stockList!=null && stockList.size()>0){		
				InOutStockEntity stockentity = (InOutStockEntity)stockList.get(0);
				//获取 出入库时间.
				Date date = stockentity.getInOutStockTime();
				
				if(date!=null){
					LabeledGoodTodoEntity todoEntity = new LabeledGoodTodoEntity();//标签重打
					todoEntity.setId(entity.getId());//id
					//set up time
					todoEntity.setRemindTime(date);
					//update database
					waybillRfcJobService.updateLabeledGoodTodoEntityById(todoEntity);
				}
			}
		}
		
		

		//获得天数组织配置参数
		String overdays = getConfigurationDays();
		
		//查询条件
		LabeledGoodTodoEntity dto = new LabeledGoodTodoEntity();
		//超时分钟数
		dto.setStatus(overdays);
		//是否已经提醒过了
		dto.setIsSendRemind(FossConstants.NO);
		
		//查询需要生成代办的列表
		List<LabeledGoodTodoWaybillDto> todoNeedSendList = labeledGoodTodoDao.selectByOverTime(dto);
		

		LOGGER.info("todoNeedSendList.size() = " + todoNeedSendList.size());
		
		int count = 1;
		//生成代办
		for(LabeledGoodTodoWaybillDto todoDto : todoNeedSendList){
		    LOGGER.info("已执行到" + count ++);
			
		    //DEFECT-2293 张兴旺  2014-3-31 08:42:23 该类型的数据不必在生成：主要因为该数据按照件数生成，数量很大，查询SQL消耗很大。而实际上GUI是通过查询待办表而不是这种数据
			 //待办提醒，打印标签提醒
//	    	pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_LABELED_PRINT,
//	    			WaybillRfcConstants.TODO_ACTION_URL, null, 
//	    			todoDto.getHandleOrgCode(), todoDto.getWaybillNo(), todoDto.getId());
//	    	
	    	LabeledGoodTodoEntity entity = new LabeledGoodTodoEntity();
	    	
	    	entity.setId(todoDto.getId());//id
	    	entity.setIsSendRemind(FossConstants.YES);//催促信息
	    	waybillRfcJobService.updateLabeledGoodTodoEntityById(entity);//更新执行节点
		}
	}
	
    
    
    
	
	/**
	 * @param waybillRfcJobService the waybillRfcJobService to set
	 */
	public void setWaybillRfcJobService(IWaybillRfcJobService waybillRfcJobService) {
		this.waybillRfcJobService = waybillRfcJobService;
	}

	@Transactional
	public void updateLabeledGoodTodoEntityById(LabeledGoodTodoEntity todoEntity ){
		labeledGoodTodoDao.updateLabeledGoodTodoEntityById(todoEntity);//更新执行节点
	}

	/**
	 * 获得天数 组织配置参数
	 */
	private String getConfigurationDays() {
		String defaultStorageDay = WaybillRfcConstants.DEFAULT_DAYS;//默认的待办事项扫描天数
		//获得配置对象
		ConfigurationParamsEntity entity = configurationParamsService.
			queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
					//组织配置参数-接送货模块
				ConfigurationParamsConstants.PKP_PARM__WAYBILLRFC_MINUTES,
					//运单变更定时任务  超时天数
					FossConstants.ROOT_ORG_CODE);
					//集团
		if(entity!=null){
			defaultStorageDay =  entity.getConfValue();
			//参数
			try{
				Integer.parseInt(defaultStorageDay);
				//天数如果弄错了不能解析为INTEGER
			}catch(Exception e){
			    	LOGGER.error("Number format Exception", e);
				//default 3
			}
		}
		return defaultStorageDay;
	}

	
	
	/**
	 * @return the pickupToDoMsgService
	 */
	public IPickupToDoMsgService getPickupToDoMsgService() {
		return pickupToDoMsgService;
	}


	/**
	 * @param pickupToDoMsgService the pickupToDoMsgService to set
	 */
	public void setPickupToDoMsgService(IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}


	/**
	 * @return the configurationParamsService
	 */
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}


	/**
	 * @return the labeledGoodTodoDao
	 */
	public ILabeledGoodTodoDao getLabeledGoodTodoDao() {
		return labeledGoodTodoDao;
	}


	/**
	 * @param labeledGoodTodoDao the labeledGoodTodoDao to set
	 */
	public void setLabeledGoodTodoDao(ILabeledGoodTodoDao labeledGoodTodoDao) {
		this.labeledGoodTodoDao = labeledGoodTodoDao;
	}


	/**
	 * @return the stockService
	 */
	public IStockService getStockService() {
		return stockService;
	}


	/**
	 * @param stockService the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	
}