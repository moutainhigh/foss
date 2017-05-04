/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-closing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/service/impl/WaybillChangeMsgProcessService.java
 * 
 * FILE NAME        	: WaybillChangeMsgProcessService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.settlement.closing.api.server.service.IWaybillChangeMsgProcessService;
import com.deppon.foss.module.settlement.closing.api.server.service.IWaybillFinancialStatusService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.WaybillFinancialStatusEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;


/**
 * 财务完结处理服务.
 *
 * @author ibm-zhuwei
 * @date 2012-12-5 下午2:50:40
 */
public class WaybillChangeMsgProcessService implements IWaybillChangeMsgProcessService {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(WaybillChangeMsgProcessService.class);
	
	/** 财务变更消息服务. */
	private IWaybillChangeMsgService waybillChangeMsgService;
	
	/** 运单财务状态服务. */
	private IWaybillFinancialStatusService waybillFinancialStatusService;
	
	/** 运单完结状态操作服务. */
	private IWaybillTransactionService waybillTransactionService;
	
	/**
	 * 处理业务完结消息.
	 *
	 * @author ibm-zhuwei
	 * @date 2012-12-5 上午10:55:31
	 */
	@Override
	public void processChangeMsg() {
		
		// 开始时间
		Date beginTime = new Date(Calendar.ERA);
		// 结束时间
		Date endTime = DateUtils.addMinutes(Calendar.getInstance().getTime(), SettlementConstants.JOB_MINUTES_INTERVAL);
		
		while (true) {

			logger.debug("处理本批次业务完结消息开始");
			
			// 1.read data 查询待处理业务完结消息
			List<WaybillChangeMsgEntity> allList = waybillChangeMsgService.queryChangeMsg(beginTime, endTime, SettlementConstants.JOB_SELECT_SIZE);
			
			// 无数据则返回
			if (CollectionUtils.isEmpty(allList)) {
				// 返回
				return;
			}
			
			// 2.deal data 
			// 拆分List为固定大小的多个集合
			List<List<WaybillChangeMsgEntity>> lists = com.deppon.foss.util.CollectionUtils
					.splitListBySize(allList, SettlementConstants.JOB_PROCESS_SIZE);
			
			for (List<WaybillChangeMsgEntity> list : lists) {

				logger.info("dealing process...");
				
				// 中间可能出现错误
				// 不影响后续数据处理
				try {
					doProcess(list);
				} catch(Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			logger.debug("处理本批次业务完结消息结束");
			
			// 本批次结束时间,是下批次开始时间
			beginTime = allList.get(allList.size() - 1).getMsgDate();
			endTime = DateUtils.addMinutes(Calendar.getInstance().getTime(), SettlementConstants.JOB_MINUTES_INTERVAL);
		}
		
	}

	/**
	 * 处理.
	 *
	 * @param list the list
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午3:09:49
	 */
	@Transactional
	private void doProcess(List<WaybillChangeMsgEntity> list) {

		// 获取消息运单集合
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.addAll(CollectionUtils.asSet(list, String.class, "getWaybillNo"));
		
		// read data
		// 通过运单读取数据库中财务状态集合
		List<WaybillFinancialStatusEntity> waybillFinancialStatusList = waybillFinancialStatusService.queryByWaybillNos(waybillNos);
		Map<String, WaybillFinancialStatusEntity> dbMaps = CollectionUtils.asMap(waybillFinancialStatusList, String.class, WaybillFinancialStatusEntity.class, "getWaybillNo");
		
		// deal data
		// 遍历消息,并内存中处理
		Map<String, WaybillFinancialStatusEntity> memMaps = new HashMap<String, WaybillFinancialStatusEntity>();
		memMaps.putAll(dbMaps);
		
		for (WaybillChangeMsgEntity msg : list) {
			
			String waybillNo = msg.getWaybillNo();
			WaybillFinancialStatusEntity entity = memMaps.get(waybillNo);
			
			if (entity == null) {	// 新增实体
				// 获取新的运单财务状态完结实体.
				entity = getNewWaybillFinancialStatusEntity(msg);
				memMaps.put(waybillNo, entity);
			}
			// 财务完结计数器
			int fcCount = entity.getFcCount();
			
			if (SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING
					.equals(msg.getMsgAction())) {	// 新增/反操作
				
				fcCount += 1;
			} else if (SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE
					.equals(msg.getMsgAction())) {	// 完结
				
				fcCount -= 1;
			}
			// 设置
			entity.setFcCount(fcCount);
		}

		// 比较内存和数据库中的记录,获取新增/修改/删除财务状态的数据
		Map<String, WaybillFinancialStatusEntity> newMaps = new HashMap<String, WaybillFinancialStatusEntity>();	// 需要新增状态的数据
		Map<String, WaybillFinancialStatusEntity> updateMaps = new HashMap<String, WaybillFinancialStatusEntity>();	// 需要修改状态的数据
		Map<String, WaybillFinancialStatusEntity> deleteMaps = new HashMap<String, WaybillFinancialStatusEntity>();	// 需要删除状态的数据
		
		List<String> waybillOverList = new ArrayList<String>();	// 财务完结列表
		List<String> waybillReverseOverList = new ArrayList<String>();	// 财务反完结列表
		
		for (Map.Entry<String, WaybillFinancialStatusEntity> entry : memMaps.entrySet()) {
			
			String waybillNo = entry.getKey();
			WaybillFinancialStatusEntity value = entry.getValue();
			
			if (!dbMaps.containsKey(waybillNo)) {	// 数据库中不存在
				if (value.getFcCount() == 0) {	// 内存中已完结 
					waybillOverList.add(waybillNo);	// 0->1->0
				} else {	// 财务未完结,新增记录
					newMaps.put(waybillNo, value);
					waybillReverseOverList.add(waybillNo);	// 0->1
				}
			} else {	// 数据库中已存在
				if (value.getFcCount() == 0) {	// 财务完结,删除
					deleteMaps.put(waybillNo, value);
					waybillOverList.add(waybillNo);	// 1->0
				} else {	// 财务未完结,更新计数器
					updateMaps.put(waybillNo, value);
				}
			}
		}
		
		// 同步结算的财务完结信息
		
		waybillFinancialStatusService.addByBatch(CollectionUtils.asList(newMaps.values()));//批量添加财务完结状态
		waybillFinancialStatusService.updateStatusByBatch(CollectionUtils.asList(updateMaps.values()));//批量更新财务完结状态
		waybillFinancialStatusService.deleteByBatch(CollectionUtils.asList(deleteMaps.values()));//批量删除财务完结状态
		
		// 更新接送货财务完结和反财务完结状态
		if(CollectionUtils.isNotEmpty(waybillOverList)){
			//批处理标识财务完结
			waybillTransactionService.updateFinanceOverforList(waybillOverList);//防止为空报错
		}
		if(CollectionUtils.isNotEmpty(waybillReverseOverList)){//防止为空报错
			//批处理反标识财务完结
			waybillTransactionService.updateReverseFinanceOverforList(waybillReverseOverList);
		}
		
		// 删除已处理完毕的财务消息
		waybillChangeMsgService.deleteChangeMsgByBatch(list);
	}
	
	/**
	 * 获取新的运单财务状态完结实体.
	 *
	 * @param msg the msg
	 * @return the new waybill financial status entity
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午5:32:41
	 */
	private WaybillFinancialStatusEntity getNewWaybillFinancialStatusEntity(WaybillChangeMsgEntity msg) {
		
		// 财务完结-运单财务状态实体
		WaybillFinancialStatusEntity newEntity = new WaybillFinancialStatusEntity();
		String waybillNo = msg.getWaybillNo();
		// ID
		newEntity.setId(UUIDUtils.getUUID());
		// 财务完结计数器
		newEntity.setFcCount(0);
		// 运单号
		newEntity.setWaybillNo(waybillNo);
		// 时间
		newEntity.setAccountDate(new Date());
		
		return newEntity;
	}
	
	
	/**
	 * Sets the waybill change msg service.
	 *
	 * @param waybillChangeMsgService the new waybill change msg service
	 */
	public void setWaybillChangeMsgService(IWaybillChangeMsgService waybillChangeMsgService) {
		this.waybillChangeMsgService = waybillChangeMsgService;
	}
	
	/**
	 * Sets the waybill financial status service.
	 *
	 * @param waybillFinancialStatusService the new waybill financial status service
	 */
	public void setWaybillFinancialStatusService(IWaybillFinancialStatusService waybillFinancialStatusService) {
		this.waybillFinancialStatusService = waybillFinancialStatusService;
	}
	
	/**
	 * Sets the waybill transaction service.
	 *
	 * @param waybillTransactionService the new waybill transaction service
	 */
	public void setWaybillTransactionService(IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}

}
