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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/AssignLoadTaskDao.java
 *  
 *  FILE NAME          :AssignLoadTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto;

/**
 * AssignLoadTaskDao
 * @author 042795-foss-duyi
 * @date 2012-10-11 下午3:07:18
 * @since
 * @version
 */
public class AssignLoadTaskDao extends iBatis3DaoImpl implements IAssignLoadTaskDao{
	private static final String NAMESPACE = "tfr-load.";

	/** 
	 * 按时间段查询已完成、已分配工作量
	 * @author 042795-foss-duyi
	 * @date 2012-10-11 下午8:09:16
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#queryWorkLoad(java.util.List, java.lang.String, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderEntity> queryWorkLoadByTime(LoaderQueryConditionDto loaderQCVo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryWorkLoad",loaderQCVo);
	}

	/** 
	 * 查询未完成工作量
	 * @author 042795-foss-duyi
	 * @date 2012-10-12 下午5:14:30
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#queryUnFinishWorkLoad(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderEntity> queryUnFinishWorkLoad(
			LoaderQueryConditionDto loaderQCVo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryUnfinishedWorkLoad",loaderQCVo);
	}

	/** 
	 * 插入分配记录
	 * @author 042795-foss-duyi
	 * @date 2012-10-13 上午9:06:33
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#insert(com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity)
	 */
	@Override
	public int insert(AssignLoadTaskEntity assignLoadTask) {
		
		return this.getSqlSession().insert(NAMESPACE+"insertAssignItem",assignLoadTask);
	}

	/** 
	 * 查询未开始的分配记录
	 * @author 042795-foss-duyi
	 * @date 2012-10-13 下午1:35:58
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#queryUnstartAssignLoadTask(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignLoadTaskEntity> queryUnstartAssignLoadTask(AssignLoadTaskQueryConditionDto assignLoadTask,int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnstartAssignLoadTask",assignLoadTask,rowBounds);
	}

	/** 
	 * 查询未开始的分配记录总数
	 * @author 042795-foss-duyi
	 * @date 2012-10-13 下午2:58:28
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#getUnstartAssignLoadTaskCount()
	 */
	@Override
	public Long getUnstartAssignLoadTaskCount(AssignLoadTaskQueryConditionDto assignLoadTask) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryUnstartAssignLoadTaskCount",assignLoadTask);
	}

	/** 
	 * 取消分配
	 * @author 042795-foss-duyi
	 * @date 2012-10-13 下午8:28:36
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#cancelAssign(java.lang.String, java.lang.String)
	 */
	@Override
	public int cancelAssign(AssignLoadTaskEntity assignLoadTask) {
	
		return this.getSqlSession().update(NAMESPACE+"cancelAssignTask",assignLoadTask);
	}

	/** 
	 * 查询已分配任务状态
	 * @author 042795-foss-duyi
	 * @date 2012-10-13 下午8:29:36
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#assignTaskState(java.lang.String)
	 */
	@Override
	public AssignLoadTaskEntity getAssignTaskStateById(String assignTaskId) {
	
		
		return (AssignLoadTaskEntity) this.getSqlSession().selectOne(NAMESPACE+"getAssignTaskState", assignTaskId);
	}

	/** 
	 * 查询派送单状态
	 * @author 042795-foss-duyi
	 * @date 2012-10-15 下午4:11:37
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#deliverBillState(java.lang.String)
	 */
	@Override
	public String queryDeliverBillState(String assignBillNo) {
		
		return (String)this.getSqlSession().selectOne(NAMESPACE+"queryDeliverBillState", assignBillNo);
	}

	/** 
	 * 更新派送单状态
	 * @author 042795-foss-duyi
	 * @date 2012-10-15 下午4:44:18
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#updateDeliverBillState(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity)
	 */
	@Override
	public int updateDeliverBillState(DeliverBillEntity bill,String beforeState) {
		
		//添加时间字段，派送单修改时更新该时间字段
		bill.setModifyTime(new Date());
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("bill", bill);
		condition.put("beforeState", beforeState);
		return this.getSqlSession().update(NAMESPACE+"updateDeliverBillState", condition);
	}

	/** 
	 * 查询派送单
	 * @author 042795-foss-duyi
	 * @date 2012-10-15 下午8:07:21
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#queryDeliverBill(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliverBillEntity> queryDeliverBill(
			DeliverBillQueryConditionDto deliverBillQCVo,int limit,int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverBillByCondition", deliverBillQCVo, rowBounds);
	}

	/** 
	 * 查询派送单数量
	 * @author 042795-foss-duyi
	 * @date 2012-10-15 下午8:11:48
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#getDeliverBillCount(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto)
	 */
	@Override
	public Long getDeliverBillCount(DeliverBillQueryConditionDto deliverBillQCVo) {
	
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryDeliverBillCount", deliverBillQCVo);
	}

	/** 
	 * 按条件查询已分配派送装车任务
	 * @author 042795-foss-duyi
	 * @date 2012-10-16 下午4:56:14
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#queryAssignLoadTaskByCondition(com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignLoadTaskEntity> queryAssignLoadTaskByCondition(
			AssignLoadTaskQueryConditionDto assignLoadTaskVo, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryAssignLoadTaskByCondition", assignLoadTaskVo, rowBounds);
	}

	/** 
	 * 按条件查询已分配派送装车任务数量
	 * @author 042795-foss-duyi
	 * @date 2012-10-16 下午4:56:14
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#queryAssignLoadTaskByConditionCount(com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto)
	 */
	@Override
	public Long queryAssignLoadTaskByConditionCount(
			AssignLoadTaskQueryConditionDto assignLoadTaskVo) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryAssignLoadTaskCountByCondition", assignLoadTaskVo);
	}

	/** 
	 * 更新分配记录状态：进行中、已完成
	 * @author dp-duyi
	 * @date 2012-10-31 上午9:20:49
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#updateAssignedItemState(com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity)
	 */
	@Override
	public int updateAssignedLoadTaskState(Map<String,String> item) {
		return this.getSqlSession().update(NAMESPACE+"updateAssignedItemState", item);
	}

	/** 
	 * 查询分配状态
	 * @author dp-duyi
	 * @date 2013-3-14 下午5:32:12
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao#queryAssignState(java.lang.String, java.lang.String)
	 */
	@Override
	public String queryAssignState(String deliverNo, String loaderCode) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("deliverNo", deliverNo);
		condition.put("loaderCode", loaderCode);
		return (String)this.getSqlSession().selectOne(NAMESPACE+"queryAssignState", condition);
	}	
}