/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/DeliverLoadGapReportAction.java
 *  
 *  FILE NAME          :DeliverLoadGapReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderEntity;
import com.deppon.foss.module.transfer.load.api.shared.vo.AssignLoadTaskVo;

/**
 * 分配派送装车任务.
 *
 * @author dp-duyi
 * @date 2012-10-26 下午3:00:36
 */
public class AssignLoadTaskAction extends AbstractAction {

	/** 
	 * The Constant serialVersionUID. 
	 */
	private static final long serialVersionUID = -3415916257657518326L;
	
	/** 
	 * The assign load task service. 
	 */
	private IAssignLoadTaskService assignLoadTaskService;
	
	/** 
	 * The vo. 
	 */
	private AssignLoadTaskVo vo;
	
	/**
	 * Query deliver bill.
	 * 
	 * 功能：查询派送单
	 * 
	 * @return the string
	 */
	public String queryDeliverBill() {
		try{
			//查询派送单
			List<DeliverBillEntity> bills = assignLoadTaskService.queryDeliverBill(vo.getDeliverBillQCVo(),this.getLimit(), this.getStart());
			//设置
			vo.setBills(bills);
			//设置总数
			this.setTotalCount(assignLoadTaskService.getDeliverBillCount(vo.getDeliverBillQCVo()));
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}
		//返回成功
		return returnSuccess();
	}
	/**
	 * Query loader.
	 * 
	 * 功能：查询装车员
	 * @return the string
	 */
	public String queryLoader() {
		try{
			//查询装车人员
			//分页
			List<LoaderEntity> loaders = assignLoadTaskService.queryLoader(vo.getLoaderQCVo(),this.getLimit(), this.getStart());
			//设置装车员
			vo.setLoaders(loaders);
			//设置查询总数
			this.setTotalCount(assignLoadTaskService.getLoaderCount(vo.getLoaderQCVo()));
			//返回成功
			return returnSuccess();
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}	
	}
	/**
	 * Query unstart assign load task.
	 * 功能：
	 * @return the string
	 */
	public String queryUnstartAssignLoadTask() {
		try{
			//查询未开始的分配装车任务
			List<AssignLoadTaskEntity> assignedTasks = assignLoadTaskService.queryUnstartAssignLoadTask(this.getLimit(), this.getStart());
			//构造VO
			vo = new AssignLoadTaskVo();
			//设置已分配装车任务
			vo.setAssignedTasks(assignedTasks);
			//设置总数
			this.setTotalCount(assignLoadTaskService.getUnstartAssignLoadTaskCount());
			//返回成功
			return returnSuccess();
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}	
	}
	/**
	 * Assign.
	 * 功能：分配任务
	 * @return the string
	 */
	public String assign() {
		try{
			//分配任务
			assignLoadTaskService.assign(vo.getAssignLoadTask());
			//返回成功
			return returnSuccess();
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}	
	}	
	/**
	 * Cancel assign.
	 * 
	 * 功能：取消任务
	 * 
	 * @return the string
	 */
	public String cancelAssign(){
		try{
			//取消任务
			assignLoadTaskService.cancelAssign(vo.getAssignTaskId(), vo.getAssignBillNo());
			//返回成功
			return returnSuccess();
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}	
	}	
	/**
	 * Query assign load task by condition.
	 * 
	 * 功能：查询分配装车任务
	 * 
	 * @return the string
	 */
	public String queryAssignLoadTaskByCondition(){
		try{
			//查询分配装车任务
			List<AssignLoadTaskEntity> assignedTasks = assignLoadTaskService.queryAssignLoadTaskByCondition(vo.getAssignLoadTaskQcVo(), this.getLimit(), this.getStart());
			//vo设值
			vo.setAssignedTasks(assignedTasks);
			//总数
			this.setTotalCount(assignLoadTaskService.queryAssignLoadTaskByConditionCount(vo.getAssignLoadTaskQcVo()));
			//返回成功
			return returnSuccess();
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}
	}	
	/**
	 * Query unfinished word load by loader.
	 *
	 * @return the string
	 */
	public String queryUnfinishedWordLoadByLoader(){
		try{
			//查询未完成的装车任务
			LoaderEntity unfinishWorkLoad = assignLoadTaskService.queryUnfinishedWorkLoad(vo.getLoaderCode());
			//设值
			vo.setUnfinishWorkLoad(unfinishWorkLoad);
			//返回成功
			return returnSuccess();
		}catch(TfrBusinessException e){
			//返回异常
			return super.returnError(e);
		}
	}
	/**
	 * Sets the assign load task service.
	 *
	 * @param assignLoadTaskService the new assign load task service
	 */
	public void setAssignLoadTaskService(IAssignLoadTaskService assignLoadTaskService) {
		this.assignLoadTaskService = assignLoadTaskService;
	}
	/**
	 * Gets the vo.
	 *
	 * @return the vo
	 */
	public AssignLoadTaskVo getVo() {
		return vo;
	}
	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(AssignLoadTaskVo vo) {
		this.vo = vo;
	}
}