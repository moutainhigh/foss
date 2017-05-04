/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/AirlinesValueAddDao.java
 * 
 * FILE NAME        	: AirlinesValueAddDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 航空增值服务运费 -  (代理)
 * 
 * @author DP-Foss-YueHongJie
 * @date 2012-12-21 上午11:16:42
 * @version 1.0
 */
public class AirlinesValueAddDao extends SqlSessionDaoSupport implements IAirlinesValueAddDao{
    
    /**
     * 航空增值服务运费mybatis
     */
    private static final String MYBATIS_NAME_SPASE = "com.deppon.foss.module.pickup.pricing.api.server.dao.AirlinesValueAddMapper.";
    
    /**
     * 
     * <p>新增</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:00:24
     * @param record 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#insert(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void insert(AirlinesValueAddEntity record) {
	getSqlSession().insert(MYBATIS_NAME_SPASE + "insert", record);
    }
    
    /**
     * 
     * <p>新增</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:00:43
     * @param record 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#insertSelective(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void insertSelective(AirlinesValueAddEntity record) {
	this.setAirlinesValueAttribute(record);
	getSqlSession().insert(MYBATIS_NAME_SPASE + "insertSelective", record);
    }
    
    /**
     * 
     * <p>删除</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:00:53
     * @param id 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#deleteByPrimaryKey(java.lang.String)
     */
    @Override
    public void deleteByPrimaryKey(String id) {
	getSqlSession().delete(MYBATIS_NAME_SPASE +"deleteByPrimaryKey", id);
    }
    
    /**
     * 
     * <p>修改</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:01:04
     * @param entity 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#updateByPrimaryKey(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void updateByPrimaryKey(AirlinesValueAddEntity entity) {
	this.setAirlinesValueAttribute(entity);
	getSqlSession().update(MYBATIS_NAME_SPASE+"updateByPrimaryKey" , entity);
    }
    
    /**
     * 
     * <p>删除</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:01:13
     * @param entity 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void updateByPrimaryKeySelective(AirlinesValueAddEntity entity) {
	this.setAirlinesValueAttribute(entity);
	getSqlSession().update(MYBATIS_NAME_SPASE+"updateByPrimaryKeySelective" , entity);
    }
    
    /**
     * 
     * <p>激活</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:01:31
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#activeAirlinesValueAdd(java.util.List)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void activeAirlinesValueAdd(List<String> ids) {
	Map parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().update(MYBATIS_NAME_SPASE+"activeAirlinesValueAdd", parameterMap);
    }
    
    /**
     * 
     * <p>批量删除</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:01:54
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#deleteAirlinesValueAdd(java.util.List)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void deleteAirlinesValueAdd(List<String> ids) {
	Map parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().delete(MYBATIS_NAME_SPASE +"deleteAirlinesValueAdd",parameterMap);
	
    }
    
    /**
     * 
     * <p>升级</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:02:11
     * @param entity 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#upgradeAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity)
     */
    @Override
    public void upgradeAirlinesValueAdd(AirlinesValueAddEntity entity) {
	this.setAirlinesValueAttribute(entity);
	getSqlSession().insert(MYBATIS_NAME_SPASE + "insertSelective", entity);
    }
    
    /**
     * 
     * <p>根据条件查询</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:02:25
     * @param airlinesValueAddDto
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#findAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AirlinesValueAddEntity> findAirlinesValueAdd(
	    AirlinesValueAddDto airlinesValueAddDto) {
	return getSqlSession().selectList(MYBATIS_NAME_SPASE + "findAirlinesValueAdd", airlinesValueAddDto);
    }
    
    /**
     * 
     * <p>查询单个实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:02:55
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#selectByPrimaryKey(java.lang.String)
     */
    @Override
    public AirlinesValueAddEntity selectByPrimaryKey(String id) {
	return (AirlinesValueAddEntity) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "selectByPrimaryKey" , id); 
    }

    /**
     * 
     * <p>分页查询</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:03:15
     * @param dto
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#findAirlinesValueAdd(com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AirlinesValueAddEntity> findAirlinesValueAdd(
	    AirlinesValueAddDto dto, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start,limit);
	return getSqlSession().selectList(MYBATIS_NAME_SPASE + "findAirlinesValueAddPagging" ,dto,rowBounds); 
    }

    /**
     * 
     * <p>查询总记录</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:03:25
     * @param dto
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IAirlinesValueAddDao#findAirlinesValueAddCount(com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto)
     */
    @Override
    public Long findAirlinesValueAddCount(AirlinesValueAddDto dto) {
	return (Long) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "findAirlinesValueAddCount",  dto); 
    }
    
    /**
     * 
     * <p>设置必要参数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:51:26
     * @param entity
     * @see
     */
    private void setAirlinesValueAttribute(AirlinesValueAddEntity entity){
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	entity.setModifyDate(new Date());
	entity.setVersionNo(new Date().getTime());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public AirlinesValueAddEntity queryAirLinesValueAddByCodes(
	    String airlinesCode, String airPortCode, String loadOrgCode,
	    Date businessDate) {
	HashMap parameterMap = new HashMap();
	parameterMap.put("airlinesCode", airlinesCode);
	parameterMap.put("airPortCode", airPortCode);
	parameterMap.put("loadOrgCode", loadOrgCode);
	parameterMap.put("businessDate", businessDate);
	parameterMap.put("active", FossConstants.ACTIVE);
	return (AirlinesValueAddEntity)getSqlSession().selectOne(MYBATIS_NAME_SPASE + "queryAirLinesValueAddByCodes",  parameterMap); 
    }

}