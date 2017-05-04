/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/WorkdayService.java
 * 
 * FILE NAME        	: WorkdayService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 
 *
 * 后置条件	1、	为结算退运费、服务补救等提供工作日基础资料查询	suc-22-新增服务补救
		suc-652-新增退运费申请

    1.	日期选择区
    1）   日期选择区可以通过下拉框选择年和月份，也可以通过点击“左移” 和“右移” 按钮来调整年份和月份。
    2）     双击日历事件：双击日期后当前日期背景色变为黄色，表示不是工作日，再双击日期背景色变为初始状态，表示是工作日。
    2.	日期展示区
    1）      列表区域默认显示当前日期，点击左右图标或下拉列表选择日期查询条件。
    功能按钮区域功能按钮区域
    3.	功能按钮区
    1）   重置按钮：点击重置按钮，重置查询条件和对日历的初始化操作。
    2）   保存按钮：点击保存按钮
    1	进入工作日管理主界面	【工作日当前月信息】	
    2	点击日期，进行修改工作日参见业务规则SR-1，SR-2		成功保存至数据库
    
    
    1.6.2	查询工作日操作步骤
    序号	基本步骤	相关数据	补充步骤
    1	进入工作日管理主界面	【工作日当前月信息】	
    2	选择年，月进行查询。	【工作日查询条件】	系统返回查询结果
    1.7	业务规则
    序号	描述
    SR-1	工作日默认从周一到周五；
    SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。

 * 
 * 
 * 查工作日信息	结算系统	
 * 根据传入签收时间(格式如：yyyy-mm-dd)和工作日天数（N）返回从签收时间第2天起到第N个工作日的工作日日期
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWorkdayService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WorkdayException;

/**
 * 工作日 Service实现
 * 
 * SR-1	工作日默认从周一到周五；
 * 
 * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
 * 
 * 目前 结算组不使用，接送货组使用.
 *
 * @author 087584-foss-lijun
 * @date 2012-11-28 下午5:19:25
 */
public class WorkdayService implements IWorkdayService {
    

    /**
     * 下面是dao对象的声明及get,set方法：.
     */
    private IWorkdayDao workdayDao;

    /**
     * 设置 下面是dao对象的声明及get,set方法：.
     *
     * @param workdayDao the new 下面是dao对象的声明及get,set方法：
     */
    public void setWorkdayDao(IWorkdayDao workdayDao) {
	this.workdayDao = workdayDao;
    }

    /**
     * 工作日 新增 工作日
     * 
     * SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * @return 
     * @throws WorkdayException 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IWorkdayService#addWorkday(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Override
    @Transactional
    public WorkdayEntity addWorkday(WorkdayEntity entity)throws WorkdayException {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	WorkdayEntity workday = workdayDao.queryWorkdayByWorkMonth(entity.getWorkMonth());
	if(workday != null){
	    throw new WorkdayException("workMonth已存在");
	}
	
	return workdayDao.addWorkday(entity);
    }
    
    /**
     * <p>保存或修改工作日</p>
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 工作日实体
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-27 下午3:26:49
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IWorkdayService#saveOrUpdate(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Transactional
    @Override
    public WorkdayEntity saveOrUpdate(WorkdayEntity entity) {
	//参数验证
	if(null == entity){
	    throw new WorkdayException("传入的工作日实体不允许为空！");
	}else {
	    //根据工作月份判断
	    if(StringUtil.isNotBlank(entity.getWorkMonth())){
		//根据工作月份判断工作日是否存在
		//精确查询 根据WORK_MONTH 查询
		WorkdayEntity workday = workdayDao.queryWorkdayByWorkMonth(entity.getWorkMonth());
		//参数验证
		if(null == workday){
		    //如果数据不存在，保存该数据
		    //插入
		    return workdayDao.addWorkday(entity);
		}else {
		    //如果数据存在，修改该数据
		    return workdayDao.updateWorkday(entity);
		}
	    }else {
		//异常信息处理
		throw new WorkdayException("工作日的工作月份不允许为空！");
	    }
	}
    }

    /**
     * 通过WORK_MONTH标识来删除 工作日
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#deleteWorkday(java.lang.String)
     */
    @Override
    @Transactional
    public WorkdayEntity deleteWorkday(WorkdayEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getWorkMonth())) {
	    //返回空值
	    return null;
	}
	//根据WORK_MONTH删除
	return workdayDao.deleteWorkday(entity);
    }

    /**
     * 通过WORK_MONTH标识来批量删除 工作日
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param codes 
     * 
     * @param deleteUser 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-28 下午5:19:26
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#deleteWorkdayMore(java.lang.String[])
     */
    @Override
    @Transactional
    public WorkdayEntity deleteWorkdayMore(String[] codes , String deleteUser) {
	//根据WORK_MONTH批量删除 
	return workdayDao.deleteWorkdayMore(codes, deleteUser);
    }

    /**
     * 更新 工作日
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-28 下午5:19:26
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#updateWorkday(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Override
    @Transactional
    public WorkdayEntity updateWorkday(WorkdayEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getWorkMonth())) {
	   //返回空值
	    return null;
	}
	//更新 数据
	return workdayDao.updateWorkday(entity);
    }



    /**
     * 以下全为查询.
     *
     * @param code 
     * @return 
     */
	
    /**
     * 精确查询 
     * 
     * 通过 WORK_MONTH 查询 工作日
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-28 下午5:19:26
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayService#queryWorkdayByCode(java.lang.String)
     */
    @Override
    public WorkdayEntity queryWorkdayByWorkMonth(String code) {
	//防御参数验证
	if (null == code) {
	    //返回空值
	    return null;
	}
	//精确查询 根据WORK_MONTH 查询
	return workdayDao.queryWorkdayByWorkMonth(code);
    }


    /**
     * 精确查询
     * 
     * 根据多个编码批量查询 工作日
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param codes 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayService#queryWorkdayByCode(java.lang.String)
     */
    @Override
    public List<WorkdayEntity> queryWorkdayBatchByWorkMonth(
	    String[] codes) {
	//防御参数判断
	if (ArrayUtils.isEmpty(codes)){
	    //返回空值
	    return null;
	}
	
	//精确查询 根据多个WORK_MONTH 查询
	return workdayDao.queryWorkdayBatchByWorkMonth(codes);
    }

    /**
     * 精确查询 工作日
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * 
     * @param start 
     * 
     * @param limit 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-19 上午11:11:15
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayService#queryWorkdayExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity, int, int)
     */
    @Override
    public List<WorkdayEntity> queryWorkdayExactByEntity(WorkdayEntity entity, int start, int limit) {
	//精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，
	//可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	return workdayDao.queryWorkdayExactByEntity(entity, start, limit);
    }

    /**
     * 精确查询 工作日
     * 
     * 
     * 查询总条数，用于分页
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-19 上午11:09:53
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayService#queryWorkdayExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Override
    public long queryWorkdayExactByEntityCount(WorkdayEntity entity) {
	//精确查询-查询总条数，用于分页 动态的查询条件。 如果传入的对象为空，传入一个对象，
	//可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	return workdayDao.queryWorkdayExactByEntityCount(entity);
    }
	
    /**
     * 精确查询 工作日,返回所有的结果，
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-28 下午5:19:26
     * 
     */
    @Override
    public List<WorkdayEntity> queryWorkdayExactByEntityAll(WorkdayEntity entity) {
	//精确查询 工作日 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，
	//如果传入的对象的属性不为空或者空白，则设置为查询条件 * SR-1 工作日默认从周一到周五； 
	//SR-2 修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。 
	//目前 结算组不使用，接送货组使用.
	return this.queryWorkdayExactByEntity(entity, 0, Integer.MAX_VALUE);
    }
 
 
    /**
     * 模糊查询 工作日
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * 
     * @param start 
     * 
     * @param limit 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-28 下午5:19:26
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayService#deleteWorkdayMore(java.lang.String[])
     */
    @Override
    public List<WorkdayEntity> queryWorkdayByEntity(WorkdayEntity entity, int start, int limit) {
	//根据entity模糊查询，
	return workdayDao.queryWorkdayByEntity(entity, start, limit);
    }

    /**
     * 模糊查询 工作日
     * 
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-28 下午5:19:26
     * 
     * @see com.deppon.foss.module.baseinfo.server.service.IWorkdayService#queryWorkdayCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.WorkdayEntity)
     */
    @Override
    public long queryWorkdayByEntityCount(WorkdayEntity entity) {
	//查询queryWorkdayByEntity返回的记录总数,用于分页
	return workdayDao.queryWorkdayByEntityCount(entity);
    }

    /**
     * 根据entity模糊查询 工作日,返回所有的结果，
     * 
     * *  SR-1	工作日默认从周一到周五；
     * 
     * SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。
     * 
     * 目前 结算组不使用，接送货组使用.
     *
     * @param entity 
     * 
     * @return 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-28 下午5:19:26
     */
    @Override
    public List<WorkdayEntity> queryWorkdayByEntityAll(WorkdayEntity entity) {
	//模糊查询 工作日 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，
	//如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件 * 
	//SR-1 工作日默认从周一到周五； SR-2 修改数据后，操作员如果未保存就离开当前界面，
	//询问是否要保存已修改的记录。 目前 结算组不使用，接送货组使用.
	return this.queryWorkdayByEntity(entity, 0, Integer.MAX_VALUE);
    }
    
   
    
}
