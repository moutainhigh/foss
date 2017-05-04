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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LeasedWhitelistAuditDao.java
 * 
 * FILE NAME        	: LeasedWhitelistAuditDao.java
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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请白名单（司机、车）”的数据库对应数据访问DAO接口实现类：SUC-750、SUC-104
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-25 上午10:54:31</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-25 上午10:54:31
 * @since
 * @version
 */
public class LeasedWhitelistAuditDao extends SqlSessionDaoSupport implements ILeasedWhitelistAuditDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".whitelistaudit";

    /**
     * <p>新增一个“外请白名单（司机、车）”实体入库（忽略实体中是否存在空值）/p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:55:20
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#addLeasedWhitelistAudit(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    public int addLeasedWhitelistAudit(WhitelistAuditEntity whitelistAudit) {
	whitelistAudit.setId(UUIDUtils.getUUID());
	whitelistAudit.setCreateDate(new Date());
	whitelistAudit.setActive(FossConstants.ACTIVE);
	whitelistAudit.setModifyUser(whitelistAudit.getCreateUser());
	whitelistAudit.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addLeasedWhitelistAudit", whitelistAudit);
    }

    /**
     * <p>新增一个“外请白名单（司机、车）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:04:04
     * @param whitelistAudit “外请白名单（司机、车）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#addLeasedWhitelistAuditBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    public int addLeasedWhitelistAuditBySelective(WhitelistAuditEntity whitelistAudit) {
	whitelistAudit.setId(UUIDUtils.getUUID());
	whitelistAudit.setCreateDate(new Date());
	whitelistAudit.setActive(FossConstants.ACTIVE);
	whitelistAudit.setModifyUser(whitelistAudit.getCreateUser());
	whitelistAudit.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addLeasedWhitelistAuditBySelective", whitelistAudit);
    }

    /**
     * <p>根据“外请白名单（司机、车）”记录唯一标识作废一条“外请白名单（司机、车）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:04:19
     * @param id 记录唯一标识
     * @return “外请白名单（司机、车）”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#deleteLeasedWhitelistAudit(java.lang.String)
     */
    @Override
    public int deleteLeasedWhitelistAudit(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteLeasedWhitelistAudit", id);
    }

    /**
     * <p>修改一个“外请白名单（司机、车）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:04:58
     * @param whitelistAudit “外请白名单（司机、车）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#updateLeasedWhitelistAuditBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    public int updateLeasedWhitelistAuditBySelective(WhitelistAuditEntity whitelistAudit) {
	whitelistAudit.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateLeasedWhitelistAuditBySelective", whitelistAudit);
    }

    /**
     * <p>修改一个“外请白名单（司机、车）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:05:12
     * @param whitelistAudit “外请白名单（司机、车）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#updateLeasedWhitelistAudit(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    public int updateLeasedWhitelistAudit(WhitelistAuditEntity whitelistAudit) {
	whitelistAudit.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateLeasedWhitelistAudit", whitelistAudit);
    }
    /**
     * <p>根据条件有选择查询“外请白名单（司机、车）”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:41
     * @param whitelistAudit “外请白名单（司机、车）”参数实体
     * @return “外请白名单（司机、车）”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistAuditBySelective(java.lang.String)
     */
    @Override
    public WhitelistAuditEntity queryLeasedWhitelistAuditBySelective(String id) {
	WhitelistAuditEntity whitelistAudit = new WhitelistAuditEntity();
	whitelistAudit.setId(id);
	return queryLeasedWhitelistAuditBySelective(whitelistAudit);
    }
    
    /**
     * <p>根据条件有选择查询“外请白名单（司机、车）”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:41
     * @param whitelistAudit “外请白名单（司机、车）”参数实体
     * @return “外请白名单（司机、车）”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistAuditBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public WhitelistAuditEntity queryLeasedWhitelistAuditBySelective(WhitelistAuditEntity whitelistAudit) {
	if(StringUtils.isBlank(whitelistAudit.getId())){
	    //强制设置为只查询“启用”的记录
	    whitelistAudit.setActive(FossConstants.ACTIVE);
	}

	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<WhitelistAuditEntity> whitelistAuditList = getSqlSession().selectList(NAMESPACE + ".queryLeasedWhitelistAuditListBySelective", whitelistAudit, rowBounds);
	if (CollectionUtils.isEmpty(whitelistAuditList)) {
	    return null;
	}
	return whitelistAuditList.get(NumberConstants.NUMERAL_ZORE);
    }

    /**
     * <p>根据条件有选择查询“外请白名单（司机、车）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:05:56
     * @param whitelistAudit “外请白名单（司机、车）”参数实体
     * @return “外请白名单（司机、车）”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistAuditListBySelective( com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<WhitelistAuditEntity> queryLeasedWhitelistAuditListBySelective(WhitelistAuditEntity whitelistAudit) {
	//强制设置为只查询“启用”的记录
	whitelistAudit.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryLeasedWhitelistAuditListBySelective", whitelistAudit);
    }

    /**
     * <p>根据条件有选择的统计出符合条件的待审核“外请白名单（司机、车）”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:03:05
     * @param leasedTruck 以“外请白名单（司机、车）”实体承载的条件参数实体
     * @return 符合条件的待审核“外请白名单（司机、车）”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistAuditRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    public long queryLeasedWhitelistAuditRecordCountBySelectiveCondition(WhitelistAuditEntity whitelistAudit) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	whitelistAudit.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryLeasedWhitelistAuditRecordCountBySelectiveCondition", whitelistAudit);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }

    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的待审核“外请白名单（司机、车）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:06:32
     * @param whitelistAudit 以“外请白名单（司机、车）”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的待审核“外请白名单（司机、车）”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistAuditListByBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<WhitelistAuditEntity> queryLeasedWhitelistAuditListBySelectiveCondition(
	    WhitelistAuditEntity whitelistAudit, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	whitelistAudit.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryLeasedWhitelistAuditListBySelectiveCondition", whitelistAudit, new RowBounds(offset, limit));
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的申请“外请白名单（司机、车）”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-26 上午8:57:05
     * @param leasedTruck 以“外请白名单（司机、车）”实体承载的条件参数实体
     * @return 符合条件的待申请“外请白名单（司机、车）”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    public long queryLeasedWhitelistApplyRecordCountBySelectiveCondition(WhitelistAuditQueryDto whitelistAuditQueryDto) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	whitelistAuditQueryDto.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryLeasedWhitelistApplyRecordCountBySelectiveCondition", whitelistAuditQueryDto);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的申请“外请白名单（司机、车）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-26 上午8:57:16
     * @param whitelistAudit 以“外请白名单（司机、车）”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的申请“外请白名单（司机、车）”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<WhitelistAuditEntity> queryLeasedWhitelistApplyListBySelectiveCondition(
    		WhitelistAuditQueryDto whitelistAuditQueryDto, int offset, int limit) {
	//强制设置为只查询“启用”的记录
    whitelistAuditQueryDto.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryLeasedWhitelistApplyListBySelectiveCondition", whitelistAuditQueryDto, new RowBounds(offset, limit));
    }
    
    /**
     * <p>根据身份证ID或车牌号修改一个“外请白名单（司机、车）”实体入库）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:05:12
     * @param whitelistAudit “外请白名单（司机、车）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#updateLeasedWhitelistAudit(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    public int updateLeasedWhitelistAuditByCardIdOrVehicleNo(WhitelistAuditEntity whitelistAudit) {
	whitelistAudit.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateLeasedWhitelistAuditByCardIdOrVehicleNo", whitelistAudit);
    }

	/** 
	 * 查询车辆白名单，包含申请部门
	 * @author foss-qiaolifeng
	 * @date 2013-7-4 下午3:31:38
	 * @param whitelistAuditQueryDto
	 * @param offset
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistApplyListBySelectiveConditionAndApplyOrg(com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WhitelistAuditEntity> queryLeasedWhitelistApplyListBySelectiveConditionAndApplyOrg(
			WhitelistAuditQueryDto whitelistAuditQueryDto, int offset, int limit) {
		 whitelistAuditQueryDto.setActive(FossConstants.ACTIVE);
			return getSqlSession().selectList(NAMESPACE + ".queryLeasedWhitelistApplyListBySelectiveConditionAndApplyOrg", whitelistAuditQueryDto, new RowBounds(offset, limit));
	}

	/** 
	 * 查询车辆白名单条数，包含申请部门
	 * @author foss-qiaolifeng
	 * @date 2013-7-4 下午3:31:43
	 * @param whitelistAuditQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao#queryLeasedWhitelistApplyRecordCountBySelectiveConditionAndApplyOrg(com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto)
	 */
	@Override
	public long queryLeasedWhitelistApplyRecordCountBySelectiveConditionAndApplyOrg(
			WhitelistAuditQueryDto whitelistAuditQueryDto) {
		long recordRount = 0;
		whitelistAuditQueryDto.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryLeasedWhitelistApplyRecordCountBySelectiveConditionAndApplyOrg", whitelistAuditQueryDto);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
	}
    
}
