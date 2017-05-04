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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/ITodoActionService.java
 * 
 * FILE NAME        	: ITodoActionService.java
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LatestHandOverDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;

/**
 * 
 * 代办事项服务类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:DP-shaohongliang,date:2012-10-19 上午10:36:41, </p>
 * @author DP-shaohongliang
 * @date 2012-10-19 上午10:36:41
 * @since
 * @version
 */
public interface ITodoActionService extends IService {
    
    /**
     * 
     * <p>生成指定运单号的待办事项</p> 
     * @author DP-shaohongliang
     * @date 2012-10-19 上午10:39:16
     * @param waibillNo
     * @see
     */
    void addTodoAction(String waibillNo);
    
    
    /**
     * 
     * <p>查询待办事项</p> 
     * 1、更改受理的查询时间段不能超过X天（具体天数可配置）
     * 2、只能查询到由“变更申请部门”申请，处理部门为当前操作部门的待办事项，不能查询到其他部门的待办事项。
     * 3、若查询条件中包含有运单号时，则只以运单号作为唯一查询条件。
     * @author DP-shaohongliang
     * @date 2012-10-19 下午2:35:23
     * @param waybillNo 运单号
     * @param dealStatus 处理状态
     * @param appliyDept 申请部门
     * @param beginTime 受理时间（开始之后）
     * @param endTime 受理时间（结束之前）
     * @return
     * @see
     */
    List<TodoActionDto> queryTodoActionsByCondition(TodoConditionDto todoConditionDto, int start, int limit);
    List<TodoActionDto> queryTodoActionsByConditionAll(TodoConditionDto todoConditionDto);
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
     * <p>查询待办事项详情</p> 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-23 上午10:59:32
     * @param queryVo
     * @return
     * @see
     */
    LabeledGoodTodoDto queryTodoAction(String waybillRfcId, String currentDeptCode);
    
	 /**
    * 
    * 更新标签状态并返回标签打印的信息
    * @author 102246-foss-shaohongliang
    * @date 2012-12-26 上午9:59:07
    */
	List<BarcodePrintLabelDto> getLabelPrintInfo(String waybillNo,
			List<LabeledGoodTodoEntity> labeledGoodTodoEntitys,String waybillRfcId) ;
    
	/**
	 * 更新状态AND处理时间
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-26 下午4:01:06
	 */
	void updateLabeledHandleStatusAndTime(String waybillRfcId, String status);


	/**
	 * @param waybillNo
	 * @param labeledGoodTodoEntitys
	 * @param waybillRfcId
	 * @return
	 */
	List<String> updateLabeledPringStatus(String waybillNo,
			List<LabeledGoodTodoEntity> labeledGoodTodoEntitys,
			String waybillRfcId);
	
	
	
	
	/**
	 * 
	 * @param waybillNo
	 * @param serials
	 */
	void updateLabeledStatusByWaybillNum(String waybillNo,List<String> serials);
	
	/**
	 * 
	 * 导出
	 * @param todoConditionDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 16, 2013 3:21:08 PM
	 */
	InputStream exportTodoActionInfo(TodoConditionDto todoConditionDto);


	Long queryExceptMsgInfoCount(ExceptMsgConditionDto exceptMsgConditionDto);


	List<ExceptMsgActionDto> queryExceptMsgActionDto(
			ExceptMsgConditionDto exceptMsgConditionDto, int start, int limit);


	Long queryPendTodoInfoCount(PendingMsgConditionDto pendingMsgConditionDto);


	List<PendingMsgActionDto> queryPendTodoActionDto(
			PendingMsgConditionDto pendingMsgConditionDto, int start, int limit);


	List<LabelGoodTodoDto> queryLabelGoodTodo(String waybillRfcId);


	void updatePendTodoFailReason(List<String> pendTodoIdList);


	void updateLabelTodoAndNewPathDetail(String waybillRfcId);


	Long queryGoodsInfoWithHandleOrgCount(TodoConditionDto todoConditionDto);


	List<TodoActionDto> queryTodoActionsByConditionWithHandleOrg(
			TodoConditionDto todoConditionDto, int start, int limit);


	void updateExceptMsgBatch(List<String> waybillRfcIdList);


	void updateExceptMsgBatchStatus(List<String> waybillRfcIdList);


	List<LatestHandOverDto> queryLatestHandoverDto(String waybillRfcId, String waybillNo);
	
	/**
	 * 提供给中转使用：当车辆做到达时，更新待办为可见的状态
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-15 21:03:05
	 * @param handoverbillNo
	 * @param operatOrgCode
	 * @return
	 */
	ResultDto updateTodoByHandoverBillNo(TodoActionDto todoActionDto);


	/**
	 * DMANA-9463 整车运单未签收提示功能
	 * @author 218438-foss-zhulei
	 */
	List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto);
}