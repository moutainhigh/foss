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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/action/TodoAction.java
 * 
 * FILE NAME        	: TodoAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/* * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.action
 * FILE    NAME: TodoAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LatestHandOverDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.TodoActionException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillLabeledGoodException;
import com.deppon.foss.module.pickup.waybill.shared.vo.TodoActionVo;

/**
 * 处理待办事项Action
 * 
 * @author 043258-foss-zhaobin
 * @date 2012-10-24 下午2:07:18
 */
public class TodoAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	/**
	 * 查询待办VO
	 */
	private TodoActionVo vo;
	/**
	 * 查询待办Service
	 */
	private ITodoActionService todoActionService;
	
	/**
	 * 业务锁定超时自动释放时间:15秒
	 */
	private static final int LOCK_TIMEOUT = 0;
	
	/**
	 * 加上业务锁
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 加上业务锁
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	/**
	 * Gets the vo.
	 * 
	 * @return the vo
	 */
	public TodoActionVo getVo(){
		return vo;
	}

	/**
	 * Sets the vo.
	 * 
	 * @param vo
	 *            the new vo
	 */
	public void setVo(TodoActionVo vo){
		this.vo = vo;
	}

	/**
	 * Sets the todo action service.
	 * 
	 * @param todoActionService
	 *            the new todo action service
	 */
	public void setTodoActionService(ITodoActionService todoActionService){
		this.todoActionService = todoActionService;
	}

	/**
	 * 查询待办.
	 * 
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-24 下午2:59:16
	 */
	@JSON
	public String queryTodoAction(){
		try{
			vo.getTodoConditionDto().setIsPrinted(vo.getTodoConditionDto().getStatus());
			Long totalCount = todoActionService.queryGoodsInfoCount(vo.getTodoConditionDto());
			if(totalCount != null && totalCount.intValue() > 0){
				List<TodoActionDto> list = todoActionService.queryTodoActionsByCondition(vo.getTodoConditionDto(), this.getStart(), this.getLimit());
				vo.setTodoActionDtoList(list);
			}else{
				vo.setTodoActionDtoList(null);
			}
			this.setTotalCount(totalCount);
		} catch (BusinessException e){
			returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 查询待办详情.
	 * 
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-25 上午11:23:16
	 */
	@JSON
	public String queryLabeledGood(){
		try{
			vo.getTodoConditionDto().setIsPrinted(vo.getTodoConditionDto().getStatus());
			LabeledGoodTodoDto labeledGoodTodoDto = todoActionService.queryTodoAction(vo.getTodoConditionDto().getWaybillRfcId(), FossUserContext.getCurrentDeptCode());
			vo.setLabeledGoodTodoDto(labeledGoodTodoDto);
		} catch (BusinessException e){
			returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 打印更新待办状态.
	 * 
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-25 上午11:23:16
	 */
	@JSON
	public String updateLabeledPrintStatus() {
		//
		MutexElement mutexElement = new MutexElement(vo.getTodoConditionDto().getWaybillNo(), "处理待办", MutexElementType.RFC_ACCEPT);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		//如果没有上锁
		if(!isLocked){
			//返回异常
			return returnError(WaybillLabeledGoodException.LABEL_GOOD_TODO);
		}		
		try {
			//如果vo不为空
			if (vo != null) 
			{
				// 获得当前用户信息
				List<BarcodePrintLabelDto> barcodePrintLabelDto = todoActionService
						.getLabelPrintInfo(vo.getTodoConditionDto().getWaybillNo(),vo.getTodoConditionDto()
										.getLabeledGoodTodoEntityList(), vo.getTodoConditionDto().getWaybillRfcId());
				// 放进vo传入前台js
				vo.setBarcodePrintLabelDto(barcodePrintLabelDto);
			}
		} catch (BusinessException e) {
			//解锁
			businessLockService.unlock(mutexElement);
			if (TodoActionException.TODO_EXCEPTION.equals(e.getErrorCode())) {
				todoActionService.updateLabeledHandleStatusAndTime(vo.getTodoConditionDto()
						.getWaybillRfcId(),WaybillRfcConstants.TODO_STATUS_OUTHANDLE);
			}
			//返回异常
			return returnError(e);
		} finally{
			//解锁
			businessLockService.unlock(mutexElement);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 根据单号获取最新交接配载情况
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-14 12:36:02
	 * @return
	 */
	@JSON
	public String queryLatestHandoverDto(){
		try{
			List<LatestHandOverDto> latestHandoverDtoList = todoActionService.queryLatestHandoverDto(vo.getTodoConditionDto().getWaybillRfcId(), vo.getTodoConditionDto().getWaybillNo());
			vo.setLatestHandoverDtoList(latestHandoverDtoList);
		} catch (BusinessException e){
			returnError(e);
		}
		return returnSuccess();
	}
}