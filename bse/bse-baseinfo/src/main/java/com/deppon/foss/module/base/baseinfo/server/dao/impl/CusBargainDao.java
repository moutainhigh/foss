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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/CusBargainDao.java
 * 
 * FILE NAME        	: CusBargainDao.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户合同信息DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:41:05 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:41:05
 * @since
 * @version
 */
public class CusBargainDao extends SqlSessionDaoSupport implements ICusBargainDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.cusBargain.";

    /** 
     * 新增客户合同信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:05:30
     * @param entity
     *            客户合同信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#addCusBargain(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity)
     */
    @Override
    public int addCusBargain(CusBargainEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废客户合同信息
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:05:30
     * @param code
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#deleteCusBargainByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteCusBargainByCode(BigDecimal crmId, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", crmId);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改客户合同信息
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:05:30
     * @param entity
     *            客户合同信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#updateCusBargain(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity)
     */
    @Override
    public int updateCusBargain(CusBargainEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户合同是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午3:00:52
     * @param crmId
     * @param lastupdatetime
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#queryCusBargainByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryCusBargainByCrmId(BigDecimal crmId, Date lastupdatetime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", lastupdatetime);
	map.put("crmId", crmId);
	map.put("active", null);
	
	List<CusBargainEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusBargainByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }
    
    /**
     * <p>根据客户编码查询客户合同中月结客户的最大欠款天数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-17 下午2:03:26
     * @param custCode 客户编码
     * @param date 合同有效时间
     * @return CusBargainEntity(int debtDays:月结客户的最大欠款天数)
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#queryCusBargainByCustCode(java.lang.String)
     */
    @Override
    public CusBargainEntity queryCusBargainByCustCode(String custCode,Date date,String active) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("validDate", date);
	map.put("custCode", custCode);
	map.put("active", active);
	
	return (CusBargainEntity)this.getSqlSession().selectOne(NAMESPACE+"queryCusBargainByCustCode", map);
    }
    
    /**
     * 
     * 根据客户编码查询客户合同信息  
     * @author 308861 
     * @date 2017-1-10 下午6:19:35
     * @param custCode
     * @param date
     * @param active
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#queryCusBargainNewByCustCode(java.lang.String, java.util.Date, java.lang.String)
     */
    @Override
    public CusBargainNewEntity queryCusBargainNewByCustCode(String custCode,Date date,String active) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("validDate", date);
	map.put("custCode", custCode);
	map.put("active", active);
	return (CusBargainNewEntity)this.getSqlSession().selectOne(NAMESPACE+"queryCusBargainNewByCustCode", map);
    }
    
    /**
     * 
     * 根据crmID查询优惠信息（是否精准包裹）  
     * @author 308861 
     * @date 2017-1-11 下午3:44:38
     * @param crmId
     * @param date
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#iscpackBybargainByCrmId(java.lang.String, java.util.Date)
     */
    @Override
    public String iscpackBybargainByCrmId(String crmId,Date date) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("validDate", date);
	map.put("crmId", crmId);
	return (String)this.getSqlSession().selectOne(NAMESPACE+"iscpackBybargainByCrmId", map);
    }
   
    /**
     * 
     * 根据标杆编码查询所对应的部门名称 
     * @author 308861 
     * @date 2017-1-16 上午9:21:41
     * @param unifiedCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#applicateOrgNameByCrmId(java.lang.String)
     */
    public OrgAdministrativeInfoEntity applicateOrgNameByCrmId(String unifiedCode){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("unifiedCode", unifiedCode);
    	return (OrgAdministrativeInfoEntity)this.getSqlSession().selectOne(NAMESPACE+"applicateOrgNameByCrmId",map);
    }
    
    /**
     * <p>根据合同编码和部门编码查询合同信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 上午10:35:43
     * @param bargainCode 合同编号
     * @param deptCode 部门标杆编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao#queryCusBargainByParams(java.lang.String, java.lang.String)
     */
    @Override
    public CusBargainEntity queryCusBargainByParams(String bargainCode,
	    String deptCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("bargainCode", bargainCode);
	map.put("deptCode", deptCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (CusBargainEntity)this.getSqlSession().selectOne(NAMESPACE + "queryCusBargainByParams", map);
    }
    
    /**
     * 根据Vo获取客户信息
     * @author WangQianJin
     * @date 2013-7-30 下午8:33:30
     */
    @Override
    public CusBargainEntity queryCusBargainByVo(CusBargainVo vo){
    	List<CusBargainEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusBargainByVo", vo);
    	if(list!=null && list.size()>0){
    		return list.get(0);
    	}else{
    		return null;
    	}
    }
    
    /**
     * 根据Vo获取客户信息用于更改单
     * @author WangQianJin
     * @date 2013-8-31 下午16:33:30
     */
    @Override
    public CusBargainEntity queryCusBargainByVoForRfc(CusBargainVo vo){
    	List<CusBargainEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusBargainByVoForRfc", vo);
    	if(list!=null && list.size()>0){
    		return list.get(0);
    	}else{
    		return null;
    	}    	
    }

	@Override
	public CusBargainEntity queryCusBargainByVoExp(CusBargainVo vo) {
		List<CusBargainEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusBargainByVoExp", vo);
    	if(list!=null && list.size()>0){
    		return list.get(0);
    	}else{
    		return null;
    	}
	}

	@Override
	public CusBargainEntity queryCusBargainByVoForRfcExp(CusBargainVo vo) {
		List<CusBargainEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusBargainByVoForRfcExp", vo);
    	if(list!=null && list.size()>0){
    		return list.get(0);
    	}else{
    		return null;
    	}    
	}

}
