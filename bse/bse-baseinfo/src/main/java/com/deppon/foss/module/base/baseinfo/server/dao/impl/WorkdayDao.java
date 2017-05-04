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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/WorkdayDao.java
 * 
 * FILE NAME        	: WorkdayDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 工作日DAO接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-3-13 下午1:35:46
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-3-13 下午1:35:46
 * @since
 * @version
 */
public class WorkdayDao extends SqlSessionDaoSupport implements IWorkdayDao {

    /**
     * 定义命名空间
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".workday.";

    /**
     * 新增工作日信息
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#addWorkday(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Override
    public WorkdayEntity addWorkday(WorkdayEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	// 创建添加日期
	Date now = new Date();
	// 设置ＩＤ
	entity.setId(UUIDUtils.getUUID());
	// 设置创建时间
	entity.setCreateDate(now);
	// 设置修改时间
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// 设置修改人
	entity.setModifyUser(entity.getCreateUser());
	// 设置新增时有效
	entity.setActive(FossConstants.ACTIVE);
	// 插入数据
	int result = getSqlSession().insert(NAMESPACE + "addWorkday", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过WORK_MONTH 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#deleteWorkday(java.lang.String)
     */
    @Override
    public WorkdayEntity deleteWorkday(WorkdayEntity entity) {
	// 请求参数合法性验证
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getWorkMonth())) {
	    return null;
	}

	// 处理删除时要更新的数据
	Date now = new Date();
	// 设置为无效
	entity.setActive(FossConstants.INACTIVE);
	// 设置删除时间
	entity.setModifyDate(now);
	// 定义MAP
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);

	// 更新数据
	int result = getSqlSession().update(NAMESPACE + "deleteWorkday", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过WORK_MONTH 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#deleteWorkdayMore(java.lang.String[],
     *      java.lang.String)
     */
    @Override
    public WorkdayEntity deleteWorkdayMore(String[] codes, String deleteUser) {
	// 请求合法性判断：
	if (ArrayUtils.isEmpty(codes)) {
	    return null;
	}

	// 处理删除时要更新的数据
	Date now = new Date();
	// 创建工作日对象
	WorkdayEntity entity = new WorkdayEntity();
	// 设置状态为无效
	entity.setActive(FossConstants.INACTIVE);
	// 设置修改时间
	entity.setModifyDate(now);
	// 设置修改人
	entity.setModifyUser(deleteUser);

	// 定义MAP对象
	Map<String, Object> map = new HashMap<String, Object>();
	// 存放信息
	map.put("codes", codes);
	// 存放信息
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	// 调用修改操作
	int result = getSqlSession().update(NAMESPACE + "deleteWorkdayMore",
		map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过WORK_MONTH标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#updateWorkday(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Override
    public WorkdayEntity updateWorkday(WorkdayEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	// 验证月份不允许为空！
	if (StringUtils.isBlank(entity.getWorkMonth())) {
	    return entity;
	}

	// 更新要先删除旧的数据：
	WorkdayEntity result = this.deleteWorkday(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}

	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	Date now = new Date();
	// 设置创建日期
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// 设置创建人
	entity.setCreateUser(entity.getModifyUser());
	// 设置状态为：有效
	entity.setActive(FossConstants.ACTIVE);
	// 调用插入操作
	int resultNum = getSqlSession()
		.insert(NAMESPACE + "addWorkday", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 以下全为查询：
     */

    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#queryWorkdayByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public WorkdayEntity queryWorkdayByWorkMonth(String code) {
	// 防御参数验证
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	WorkdayEntity entity = new WorkdayEntity();
	// 设置状态：有效
	entity.setActive(FossConstants.ACTIVE);
	// 设置月份
	entity.setWorkMonth(code);

	// 执行查询操作，返回结果集合
	List<WorkdayEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryWorkdayByWorkMonth", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(NumberConstants.ZERO);
	}
    }

    /**
     * 精确查询 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#queryWorkdayBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<WorkdayEntity> queryWorkdayBatchByWorkMonth(String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	// 设置查询编码数组
	map.put("codes", codes);
	// 设置状态：有效
	map.put("active", FossConstants.ACTIVE);

	// 调用查询操作，返回结果集合
	return getSqlSession().selectList(
		NAMESPACE + "queryWorkdayBatchByWorkMonth", map);
    }

    /**
     * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#queryWorkdayExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity,
     *      int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<WorkdayEntity> queryWorkdayExactByEntity(WorkdayEntity entity,
	    int start, int limit) {
	// 声明工作日实体
	WorkdayEntity queryEntity;
	// 防御参数判断
	if (null == entity) {
	    // 创建工作日对象
	    queryEntity = new WorkdayEntity();
	} else {
	    // 赋值
	    queryEntity = entity;
	}
	// 设置状态：有效
	queryEntity.setActive(FossConstants.ACTIVE);
	// 分页查询设置
	RowBounds rowBounds = new RowBounds(start, limit);

	// 返回符合条件工作日集合
	return getSqlSession()
		.selectList(NAMESPACE + "queryWorkdayExactByEntity",
			queryEntity, rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页 动态的查询条件。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#queryWorkdayExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Override
    public long queryWorkdayExactByEntityCount(WorkdayEntity entity) {
	// 声明工作日实体
	WorkdayEntity queryEntity;
	// 防御参数判断
	if (null == entity) {
	    // 创建工作日对象
	    queryEntity = new WorkdayEntity();
	} else {
	    // 赋值
	    queryEntity = entity;
	}
	// 设置状态：有效
	queryEntity.setActive(FossConstants.ACTIVE);
	// 查询总记录数
	return (Long) getSqlSession().selectOne(
		NAMESPACE + "queryWorkdayExactByEntityCount", queryEntity);
    }

    /**
     * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#queryWorkdayMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<WorkdayEntity> queryWorkdayByEntity(WorkdayEntity entity,
	    int start, int limit) {
	// 声明工作日实体
	WorkdayEntity queryEntity;
	// 防御参数判断
	if (null == entity) {
	    // 创建工作日对象
	    queryEntity = new WorkdayEntity();
	} else {
	    // 赋值
	    queryEntity = entity;
	}
	// 设置状态：有效
	queryEntity.setActive(FossConstants.ACTIVE);
	//分页查询设置
	RowBounds rowBounds = new RowBounds(start, limit);
	//返回符合条件的查询结果
	return getSqlSession().selectList(NAMESPACE + "queryWorkdayByEntity",
		queryEntity, rowBounds);
    }

    /**
     * 模糊查询 动态的查询条件-查询总条数。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IWorkdayDao#queryWorkdayByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity)
     */
    @Override
    public long queryWorkdayByEntityCount(WorkdayEntity entity) {
	// 声明工作日实体
	WorkdayEntity queryEntity;
	// 防御参数判断
	if (null == entity) {
	    // 创建工作日对象
	    queryEntity = new WorkdayEntity();
	} else {
	    // 赋值
	    queryEntity = entity;
	}
	// 设置状态：有效
	queryEntity.setActive(FossConstants.ACTIVE);
	//返回查询的总记录数
	return (Long) getSqlSession().selectOne(
		NAMESPACE + "queryWorkdayByEntityCount", queryEntity);
    }

    /**
     * 根据entity精确查询,用于数据下载 entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:8:45
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<WorkdayEntity> queryWorkdayForDownload(WorkdayEntity entity) {
	//声明工作日实体
	WorkdayEntity queryEntity;
	// 防御参数判断
	if (null == entity) {
	    // 创建工作日对象
	    queryEntity = new WorkdayEntity();
	} else {
	    // 赋值
	    queryEntity = entity;
	}
	//返回符合条件的结果集合
	return (List<WorkdayEntity>) getSqlSession().selectList(
		NAMESPACE + "queryWorkdayForDownload", queryEntity);
    }
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LimitedWarrantyItemsDao.class);

}
