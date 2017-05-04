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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/complex/OrgAdministrativeInfoComplexDao.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoComplexDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.complex;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门复杂查询DAO
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-6 上午9:42:01
 */
public class OrgAdministrativeInfoComplexDao extends SqlSessionDaoSupport implements
	IOrgAdministrativeInfoComplexDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".orgAdministrativeInfo.";
    /**
     * 根据部门编码获取所属及下属部门信息 此部门及下属的所有部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:41:09
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao#queryOrgAdministrativeInfoEntityAllSubByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllSubByCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("conditionActive", FossConstants.ACTIVE);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(NAMESPACE	+ "queryOrgAdministrativeInfoEntityAllSubByCode", map);
    }
    

    /**
     * 根据部门编码获取所属及上级部门信息 此部门及上级的所有部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:41:09
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao#queryOrgAdministrativeInfoEntityAllUpByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllUpByCode(
	    String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("conditionActive", FossConstants.ACTIVE);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(NAMESPACE	+ "queryOrgAdministrativeInfoEntityAllUpByCode", map);
    }

    /**
     * 根据财务部门编码获取管辖大区信息 
     * 大区 是营业大区域 财务部门，不是财务组织的部门，不是一个虚拟的部门，是OA中一个实际存在的部门
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:41:49
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao#queryOrgAdministrativeInfoEntityBigAreaByFinance(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityBigAreaByFinance(
	    String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("entityFinance", code);
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("conditionYes", FossConstants.YES);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(
		NAMESPACE + "queryOrgAdministrativeInfoEntityBigAreaByFinance",	map);

    }

    /**
     * 根据大区编码获取下属小区信息 
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:42:04
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao#queryOrgAdministrativeInfoEntitySmallAreaByBig(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySmallAreaByBig(
	    String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("conditionYes", FossConstants.YES);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(
		NAMESPACE + "queryOrgAdministrativeInfoEntitySmallAreaByBig",
		map);

    }

    /**
     * 根据部门编码获取下属营业部部门信息 
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:42:12
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao#queryOrgAdministrativeInfoEntitySalesByBig(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySalesByUp(
	    String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("conditionYes", FossConstants.YES);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(
		NAMESPACE + "queryOrgAdministrativeInfoEntitySalesByUp",
		map);

    }


    /** 
     * 根据 部门编码（一般为小区编码，大区编码）获取下属营业部部门信息
     * 
     * 返回的编码要在指定的list中
     * 
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * 主要提供给结算
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午8:26:39
     * @param code部门编码，不是标杆编码
     * @param existCode 包含的营业部编码
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySalesByUpCode(
	    String code, List<String> existCode){
	if (StringUtils.isBlank(code) ) {
	    return null;
	}
	
	if (existCode != null) {
	    existCode.removeAll(Collections.singleton(null));
	}
	
	if(CollectionUtils.isEmpty(existCode)){
	    return null;
	}
	
	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("existCodes", existCode);
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("conditionYes", FossConstants.YES);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(
		NAMESPACE + "queryOrgAdministrativeInfoEntitySalesByUpCode", map);

    }

    
    
    
    /**
     * 根据部门编码获取下级指定类型的部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:41:09
     * 
     * code 部门编码
     * bizType 业务类型，同表的列名
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoSubByBizType(
	    String code, String bizType) {
	if (StringUtils.isBlank(code) || StringUtils.isBlank(bizType)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("bizType", bizType);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession()
		.selectList(NAMESPACE + "queryOrgAdministrativeInfoSubByBizType", map);
    }
    
    /**
     * 根据部门编码获取上级指定类型的部门。
     * 
     * @author 087584-foss-zhangjiheng
     * @date 2013-7-24 上午9:41:09
     * 
     * code 部门编码
     * bizType 业务类型，同表的列名
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoUpByBizType(
	    String code, String bizType) {
	if (StringUtils.isBlank(code) || StringUtils.isBlank(bizType)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("bizType", bizType);

	return (List<OrgAdministrativeInfoEntity>) getSqlSession()
		.selectList(NAMESPACE + "queryOrgAdministrativeInfoUpByBizType", map);
    }
    
    /**
	 * 根据外场编码查询所对应的虚拟营业部
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-15 上午10:47:27
	 * @param   codeList
	 * @return  List<OrgAdministrativeInfoEntity>
	 * ：
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<OrgAdministrativeInfoEntity> queryExpressSalesDepartmentByTransCenterCode(List<String> codeList) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codeList);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressSalesDepartmentByTransCenterCode", map);
	}


	@Override
	public List<String> queryExpressExpressPartByDeptCode(String empCode) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("empCode", empCode);
//		map.put("active", FossConstants.ACTIVE);
//		map.put("expressPart", FossConstants.ACTIVE);
//		List<OrgAdministrativeInfoEntity> entities = (List<OrgAdministrativeInfoEntity>)this.getSqlSession().selectList(NAMESPACE + "queryExpressExpressPartByDeptCode", map);
//		this.getSqlSession().selectList(NAMESPACE + "queryExpressExpressPartByDeptCode", map);
		return null;
	}

	/**
	 * 
	 *<p>根据大区编码查询下属所有快递点部</p>
	 * @author 130566-foss-ZengJunfan
	 * @date 2014-9-5 上午9:38:53 
	 * @see @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao#queryOrgAdminExpressPartsByBig(java.lang.String) 
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgAdminExpressPartsByBig(
			String code) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("conditionActive", FossConstants.ACTIVE);
		map.put("conditionYes", FossConstants.YES);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryOrgAdminExpressPartsByBig", map);
	}
}
