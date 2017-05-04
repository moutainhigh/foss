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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/SpecialAddressDao.java
 * 
 * FILE NAME        	: SpecialAddressDao.java
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
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExhibitionKeywordException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
* @ClassName: ExhibitionKeywordDao 
* @Description: 展馆关键字信息 DAO接口处理类
* @author 189284 ZhangXu
* @date 2014-12-27 上午11:25:26 
* @since
 * @version
 */
public class ExhibitionKeywordDao extends SqlSessionDaoSupport implements IExhibitionKeywordDao {

	 private static final String NAMESPACE = "foss.bse.bse-baseinfo.exhibitionKeyword.";
	 
	/**
	 * 
	* @Title: addExhibitionKeyword 
	* @Description: <p>新增展馆关键字信息</p>
	* @param ExhibitionKeyword
	* @author 189284 ZhangXu
	* @date 2014-12-27 上午11:23:21
	 */
	@Override
	public int addExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) {
		exhibitionKeyword.setId(UUIDUtils.getUUID());
		exhibitionKeyword.setActive(FossConstants.ACTIVE);
		exhibitionKeyword.setCreateDate(new Date());
		exhibitionKeyword.setModifyDate(exhibitionKeyword.getCreateDate());
		exhibitionKeyword.setModifyUser(exhibitionKeyword.getCreateUser());
		exhibitionKeyword.setModifyDate(new Date());
		int result = getSqlSession().insert(NAMESPACE+"addExhibitionKeyword", exhibitionKeyword);
		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

	/**
	 * 
	* @Title: deleteExhibitionKeyword 
	* @Description: 作废展馆关键字信息 
	* @param ExhibitionKeyword
	* @return
	* @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#deleteExhibitionKeyword(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-1-5 上午11:00:16
	 */
	@Override  
	public int deleteExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword ) {
		if (exhibitionKeyword == null ) {
			return FossConstants.FAILURE;
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("exhibitionKeyword", exhibitionKeyword);
		map.put("active", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE+"deleteExhibitionKeyword", map);
		
		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

	/**
	 * 
	* @Title: updateExhibitionKeyword 
	* @Description: 修改展馆关键字信息 
	* @param exhibitionKeyword
	* @return
	* @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#updateExhibitionKeyword(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-1-5 上午11:00:31
	 */
	@Override
	public int updateExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) {
		
		if (exhibitionKeyword == null) {
			return FossConstants.FAILURE;
		}
		exhibitionKeyword.setModifyDate(new Date());
		//Map<String, Object> map=new HashMap<String, Object>();
		//map.put("ExhibitionKeyword", ExhibitionKeyword);
		//map.put("active", FossConstants.ACTIVE);
		exhibitionKeyword.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE+"updateExhibitionKeyword", exhibitionKeyword);
		
		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}
	/**
	 * 
	 * <p>查询 展馆关键字信息  （关键字和地址-模糊-匹配）</p> 
	 * @author 189284-ZhangXu
	 * @date 2014-12-29 下午5:21:35
	 * @param ExhibitionKeyword
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#queryExhibitionKeyword(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitionKeywordEntity> queryExhibitionKeyword(
			ExhibitionKeywordEntity exhibitionKeyword) {
		if (exhibitionKeyword == null) {
			return null;
		}
		
		exhibitionKeyword.setActive(FossConstants.ACTIVE);
		List<ExhibitionKeywordEntity> exhibitionKeywords = getSqlSession()
				.selectList(NAMESPACE+"queryExhibitionKeywordList", exhibitionKeyword);
		return exhibitionKeywords;
	}
	/**
	 * 
	 * <p>查询 展馆关键字信息（关键字和地址-精确-匹配） </p> 
	 * @author 189284-ZhangXu
	 * @date 2014-12-29 下午5:21:35
	 * @param ExhibitionKeyword
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#queryExhibitionKeyword(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitionKeywordEntity> queryExhibitionKeywordByExact(
			ExhibitionKeywordEntity exhibitionKeyword) {
		if (exhibitionKeyword == null) {
			return null;
		}
		
		exhibitionKeyword.setActive(FossConstants.ACTIVE);
		List<ExhibitionKeywordEntity> exhibitionKeywords = getSqlSession()
				.selectList(NAMESPACE+"queryExhibitionKeywordByExact", exhibitionKeyword);
		return exhibitionKeywords;
	}
	/**
	 * 
	 * <p>查询 展馆关键字信息（关键字和地址-精确-匹配）id不等于  </p> 
	 * @author 189284-ZhangXu
	 * @date 2014-12-29 下午5:21:35
	 * @param ExhibitionKeyword
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#queryExhibitionKeyword(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitionKeywordEntity> queryExhibitionKeywordNotId(
			ExhibitionKeywordEntity exhibitionKeyword) {
		if (exhibitionKeyword == null) {
			return null;
		}
		
		exhibitionKeyword.setActive(FossConstants.ACTIVE);
		List<ExhibitionKeywordEntity> exhibitionKeywords = getSqlSession()
				.selectList(NAMESPACE+"queryExhibitionKeywordNotId", exhibitionKeyword);
		return exhibitionKeywords;
	}

	/**
	 * 
	* @Title: queryExhibitionKeywordBySelective 
	* @Description: 根据ID 查询展馆关键字信息
	* @param id
	* @return
	* @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#queryExhibitionKeywordBySelective(java.lang.String)    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-1-5 上午11:00:57
	 */
	@Override
	public ExhibitionKeywordEntity queryExhibitionKeywordBySelective(String id) {
		if (StringUtils.isBlank(id)) {
		    throw new  ExhibitionKeywordException("ID 为空");
		}
		
		ExhibitionKeywordEntity exhibitionKeyword = new ExhibitionKeywordEntity();
		exhibitionKeyword.setId(id);
		List<ExhibitionKeywordEntity> exhibitionKeywordList = queryExhibitionKeyword(exhibitionKeyword);
		if (CollectionUtils.isEmpty(exhibitionKeywordList)) {
		    return null;
		}
		return exhibitionKeywordList.get(NumberConstants.NUMERAL_ZORE);
	}

	/**
	 * 
	* @Title: queryExhibitionKeywordListBySelectiveCondition 
	* @Description: 分页 查询展馆关键字信息
	* @param ExhibitionKeyword
	* @param offset
	* @param limit
	* @return
	* @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#queryExhibitionKeywordListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity, int, int)    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-1-5 上午11:01:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitionKeywordEntity> queryExhibitionKeywordListBySelectiveCondition(
			ExhibitionKeywordEntity exhibitionKeyword, int offset, int limit) {
		exhibitionKeyword.setActive(FossConstants.ACTIVE);
		List<ExhibitionKeywordEntity> exhibitionKeywordList = getSqlSession()
				.selectList(NAMESPACE+"queryExhibitionKeywordList", exhibitionKeyword,new RowBounds(offset, limit));
		
        return exhibitionKeywordList;
	}

	/**
	 * 
	* @Title: queryExhibitionKeywordCountBySelectiveCondition 
	* @Description: 查询  总数 （满足条件的）展馆关键字信息
	* @param ExhibitionKeyword
	* @return
	* @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#queryExhibitionKeywordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-1-5 上午11:01:50
	 */
	@Override
	public Long queryExhibitionKeywordCountBySelectiveCondition(
			ExhibitionKeywordEntity exhibitionKeyword) {
		long recordRount = 0;
		//设置为只查询“启用”的记录
		exhibitionKeyword.setActive(FossConstants.ACTIVE);
		Object result = getSqlSession().selectOne(
				NAMESPACE + "queryExhibitionKeywordCountBySelectiveCondition",
				exhibitionKeyword);
		if (null != result) {
			recordRount = Long.parseLong(String.valueOf(result));
		}
		return recordRount;
	}
	/**
	 * 
	* @Title: queryExhibitionKeywordListByTargetOrgName 
	* @Description: 根据目的站地址查询目的站地址是否含有展馆关键字信息 
	* @param condition
	* @return
	* @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao#queryExhibitionKeywordListByTargetOrgName(java.lang.String)    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-1-5 上午11:02:20
	 */
	@Override
	public List<ExhibitionKeywordEntity> queryExhibitionKeywordListByTargetOrgName(
			String condition) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(condition)) {
		    throw new  ExhibitionKeywordException("收货人地址 为空");
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("active", FossConstants.ACTIVE);
		List<ExhibitionKeywordEntity> exhibitionKeywords = getSqlSession()
				.selectList(NAMESPACE+"queryExhibitionKeywordListByTargetOrgName",map);
		return exhibitionKeywords;
	}

}
