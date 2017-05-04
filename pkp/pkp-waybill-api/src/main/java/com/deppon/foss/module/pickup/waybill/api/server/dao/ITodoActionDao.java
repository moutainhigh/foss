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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/ITodoActionDao.java
 * 
 * FILE NAME        	: ITodoActionDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.TodoActionEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LatestHandOverDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;


public interface ITodoActionDao {
	
	/**
	 * DMANA-9463 整车运单未签收提示功能
	 * @author 218438-foss-zhulei
	 */
	List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto, List<Integer> days);

	/**
	 * 
	 * <p>待办事项确认处理</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-23 下午2:01:54
	 * @param todoActionId
	 * @param confirmStatus
	 * @see
	 */
    void updateProcessingStatus(String todoActionId, int confirmStatus);

    /**
     * 
     * <p>查询所属部门的待办事项</p> 
     * @author DP-shaohongliang
     * @param limit 
     * @param start 
     * @date 2012-10-20 上午10:41:22
     * @param waybillNo
     * @param status
     * @param darftOrgName
     * @param loginOrgName
     * @param operateTimeBegin
     * @param operateTimeEnd
     * @return
     * @see
     */
    List<TodoActionDto> queryTodoActionsByCondition(TodoConditionDto todoConditionDto, int start, int limit);

    /**
     * 
     * <p>查询待办事项详情</p> 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-23 上午10:59:32
     * @param queryVo
     * @return
     * @see
     */
	LabeledGoodTodoDto queryTodoAction(String waybillRfcId, String orgCode);
	
	 /**
     * 
     * <p>查询待办事项详情</p> 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-23 上午10:59:32
     * @param queryVo
     * @return
     * @see
     */
	LabeledGoodTodoDto queryTodoAction(String waybillRfcId, String orgCode, String handleOrgCode, String handleOrgCode2);
	
	/**
     * 
     * <p>更新已打印的标签状态</p> 
     * @author DP-shaohongliang
     * @date 2012-10-19 下午3:09:45
     * @param labeledNumbers
     * @see
     */
	void updateLabeledPringStatus(String labeledGoodTodoId,String printed);

	/**
	 * 
	 * <p>待办事项标签都打印完毕返回待办事项ID</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-23 下午2:00:30
	 * @param labeledGoodTodoId
	 * @return
	 * @see
	 */
	String queryTodoActionIdIfAllPrinted(String labeledGoodTodoId);

	 /**
     * 
     * <p>获得待办事项总条数</p> 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-24 下午2:40:49
     * @param todoConditionDto
     * @return
     * @see
     */
	Long queryGoodsInfoCount(TodoConditionDto todoConditionDto);
    
	/**
	 * 
	 * <p>新增变更待办事项</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-3 下午7:54:29
	 * @param todoActionEntity
	 */
	void addToDoAction(TodoActionEntity todoActionEntity);
	
	/**
	 * 根据更改单ID更新代办状态
	 * @author 097972-foss-dengtingting
	 * @date 2013-2-27 下午2:31:56
	 */
	void updateLabeledHandleStatus(String waybillRfcId, String status, String orgCode);
	
	/**
	 * 查询未打印的条数
	 * @author 097972-foss-dengtingting
	 * @date 2013-2-27 下午3:10:09
	 */
	Long queryTodoActionNoPrintCount(TodoConditionDto todoConditionDto);
	
	/**
	 * 更新状态AND处理时间
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-26 下午4:01:06
	 */
	void updateLabeledHandleStatusAndTime(String waybillRfcId, String status, String orgCode);
	
	
	LabeledGoodTodoEntity selectById(String id);

	/**
	 * 
	 * @param waybillNo
	 * @param serials
	 */
	void updateLabeledStatusByWaybillNum(String waybillNo,List<String> serials);

	Long queryExceptMsgInfoCount(ExceptMsgConditionDto exceptMsgConditionDto);

	List<ExceptMsgActionDto> queryTodoActionsByCondition(
			ExceptMsgConditionDto exceptMsgConditionDto, int start, int limit);
	
	List<PendingMsgActionDto> queryPendTodoActionDto(PendingMsgConditionDto pendingMsgConditionDto, int start, int limit);

	Long queryPendTodoActionDtoCount(
			PendingMsgConditionDto pendingMsgConditionDto);

	List<LabelGoodTodoDto> queryLabelGoodTodo(String waybillRfcId);

	void updateLabelTodoAndNewPathDetail(String waybillRfcId);

	void updatePendTodoFailReason(List<String> pendTodoIdList);

	List<TodoActionDto> queryTodoActionsByConditionAll(
			TodoConditionDto todoConditionDto);

	Long queryGoodsInfoWithHandleOrgCount(TodoConditionDto todoConditionDto);

	List<TodoActionDto> queryTodoActionsByConditionWithHandleOrg(
			TodoConditionDto todoConditionDto, int start, int limit);

	void updateLabeledPringStatusBatch(List<LabeledGoodTodoEntity> labeledGoodTodoEntitys);

	void updateExceptMsgBatchStatus(List<String> waybillRfcIdList);

	List<LatestHandOverDto> queryLatestHandoverDto(TodoConditionDto todoConditionDto);

	void updateAllNotPrintLabeledBatch(List<String> labelGoodsIdList, String yes, String no);
	
	void updateTodoByHandoverBillNo(Map<Object, Object> maps);
	
	List<String> selectMaxCountHandoverSerialNo(List<String> handOverbillNoList);
	
}