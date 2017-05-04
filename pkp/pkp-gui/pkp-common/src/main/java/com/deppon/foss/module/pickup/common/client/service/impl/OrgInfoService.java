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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/OrgInfoService.java
 * 
 * FILE NAME        	: OrgInfoService.java
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
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.Date;
import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.common.client.dao.IOrgInfoDao;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.google.inject.Inject;

/**
 * 部门服务类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:39:03,
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午9:39:03
 * @since
 * @version
 */
public class OrgInfoService implements IOrgInfoService {

	@Inject
	private IOrgInfoDao orgInfoDao;
	
	/**
	 * 注入基础资料DAO
	 */
	@Inject
	private IBaseDataService baseDataService;
	/**
	 * 
	 * 功能：按id查询
	 * 
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	@Override
	public OrgAdministrativeInfoEntity queryById(String id) {
		return orgInfoDao.queryById(id);
	}

	
	/**
	 * <p>
	 * (功能：按code查询)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-7 下午7:24:32
	 * @param id
	 * @return
	 * @see
	 */
	public OrgAdministrativeInfoEntity queryByCode(String code) {
		// 判断在线还是离线
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			return baseDataService.queryOrgAdministrativeInfoEntityByCode(code);		
		}else{
			return orgInfoDao.queryByCode(code);
		}
	}
	
	/**
	 * 根据历史时间和组织编码查询组织信息（查询历史组织信息）
	 * 
	 * 若时间为空，则只根据组织编码查询组织信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的部门
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	@Override
	public OrgAdministrativeInfoEntity queryByCode(String code,Date billTime) {
		return baseDataService.queryOrgAdministrativeInfoEntityByCode(code,billTime);
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addOrgInfo(OrgAdministrativeInfoEntity orgInfo) {
		orgInfoDao.addOrgInfo(orgInfo);
	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	public void updateOrgInfo(OrgAdministrativeInfoEntity orgInfo) {
		orgInfoDao.updateOrgInfo(orgInfo);
	}

	/**
	 * 
	 * 功能：新增或更新记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	public void saveOrUpdate(OrgAdministrativeInfoEntity orgInfo) {
		if(!orgInfoDao.addOrgInfo(orgInfo)){
			orgInfoDao.updateOrgInfo(orgInfo);
		}
	}

	/**
	 * 功能：查询记录
	 * 
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryAll() {

		return orgInfoDao.queryAll();
	}

	/**
	 * 根据标杆编码查询组织信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 上午9:48:55
	 */
	@Override
	public OrgAdministrativeInfoEntity queryOrgByUnifiedCode(String unifiedCode){
		return orgInfoDao.queryOrgByUnifiedCode(unifiedCode);
	}
	
    /**
     * 
     * 查询空运配载部门
     * @author 025000-FOSS-helong
     * @date 2013-1-21 下午07:31:00
     * @return
     */
    public List<OrgAdministrativeInfoEntity> queryOrgAirDepartment() {
    	return orgInfoDao.queryOrgAirDepartment();
    }

	/**
	 * @param orgInfoEntity
	 */
	public void delete(OrgAdministrativeInfoEntity orgInfoEntity) {
		orgInfoDao.delete(orgInfoEntity);
		
	}
	
	/**
	 * 
	 * 根据部门名称查询部门信息
	 * @author WangQianJin
	 * @date 2013-05-16
	 * @return
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgInfoByName(String name){
		//判断是否在线
		if(CommonUtils.isOnline()){
			//在线则调用在线远程接口方法
			return baseDataService.queryOrgInfoByNameOnline(name);
		}else{
			return orgInfoDao.queryOrgInfoByName(name);
		}
	}
	
	/**
	 * 该方法用于根据服务器时间查询组织信息，以免出现“客户修改本地时间在6月1日之前，而导致使用本地时间查询不出数据的现象”
	 * 
	 * BUG-29798网单：AL00012004348879 是5月21日的单子，有时间限制不能导入，但是把本地时间调整到5月21可以导入开单，
	 * 输入信息后，点击提交，确定提示找不到对应实体
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-20 下午6:26:49
	 */
	@Override
	public OrgAdministrativeInfoEntity queryByCodeAndServerTime(String code){
		return orgInfoDao.queryByCodeAndServerTime(code,baseDataService.gainServerTime());
	}
}