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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SalesDeptAccountantService.java
 * 
 * FILE NAME        	: SalesDeptAccountantService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDeptAccountantService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * service实现类
 * @author 027443-foss-zhaopeng
 * @date 2012-11-6 上午11:36:29
 */
public class SalesDeptAccountantService implements ISalesDeptAccountantService{

    private ISalesDeptAccountantDao salesDeptAccountantDao;
    public void setSalesDeptAccountantDao(ISalesDeptAccountantDao salesDeptAccountantDao){
	this.salesDeptAccountantDao = salesDeptAccountantDao;
    }
    
    /**
     * 
     * 新增区域会计信息，创建时间和修改时间在这里设置，其他数据前台获取。
     * @author 027443-foss-zhaopeng
     * @date 2012-11-6 下午3:29:06
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDeptAccountantService#addSalesDeptAccountant(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
     */
    @Override
    public synchronized int addSalesDeptAccountant(SalesDeptAccountantEntity entity) {
	
	if(entity == null){
	    return FossConstants.FAILURE;
	}
	
	//设置创建时间为当前服务器时间
	entity.setCreateDate(new Date());
	//创建时间和修改时间相同
	entity.setModifyDate(entity.getCreateDate());
	//新增默认有效
	entity.setActive(FossConstants.ACTIVE);
	return salesDeptAccountantDao.addSalesDeptAccountant(entity);
    }

    /**
     * 
     * 修改区域会计信息，区域会计基础资料不需要时间建模，因此直接在原记录上更新
     * @author 027443-foss-zhaopeng
     * @date 2012-11-6 下午3:37:41
     * @param entity 待修改的区域会计对象
     * @return 返回常量SUCCESS或者FAILURE
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDeptAccountantService#updateSalesDeptAccountant(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
     */
    @Override
    public synchronized int updateSalesDeptAccountant(SalesDeptAccountantEntity entity) {
	
	//设置修改时间为服务器时间
	entity.setModifyDate(new Date());
	
	int flag = this.salesDeptAccountantDao.updateSalesDeptAccountant(entity);
	if(flag>0){
	    return FossConstants.SUCCESS;
	}
	else{
	    return FossConstants.FAILURE;
	}
    }

    /**
     * 
     * 删除区域会计信息，区域会计基础资料不需要时间建模，因此直接删除
     * @author 027443-foss-zhaopeng
     * @date 2012-11-6 下午3:50:10
     * @param codes 前台把待删除的code通过数组传到后台
     * @param modifyUser 前台把当前修改人工号传入后台
     * @return 返回常量SUCCESS或者FAILURE
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDeptAccountantService#deleteSalesDeptAccountantByCodes(java.lang.String[], java.lang.String)
     */
    @Override
    public int deleteSalesDeptAccountantByCodes(String[] ids,
	    String modifyUser) {
	//数组为null或者数组为空
	if(ids == null || ids.length == 0){
	    return FossConstants.FAILURE;
	}
	int flag = this.salesDeptAccountantDao.deleteSalesDeptAccountantByCodes(ids, modifyUser);
	if(flag > 0){
	    return FossConstants.SUCCESS;
	}else{
	    return FossConstants.FAILURE;
	}
    }

    /**
     * 
     * 查询总行数
     * @author 027443-foss-zhaopeng
     * @date 2012-11-7 上午9:13:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDeptAccountantService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
     */
    @Override
    public Long queryRecordCount(SalesDeptAccountantEntity entity) {
	// TODO Auto-generated method stub
	return null;
    }
    
    /**
     * 
     * 分页查询
     * @author 027443-foss-zhaopeng
     * @date 2012-11-7 上午9:13:56
     * @param entity 把查询条件封装为一个对象
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDeptAccountantService#querySalesDeptAccountantGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity, int, int)
     */
    @Override
    public List<SalesDeptAccountantEntity> querySalesDeptAccountantGroup(
	    SalesDeptAccountantEntity entity, int limit, int start) {
	
	return this.salesDeptAccountantDao.querySalesDeptAccountantGroup(entity, limit, start);
    }

    
}
