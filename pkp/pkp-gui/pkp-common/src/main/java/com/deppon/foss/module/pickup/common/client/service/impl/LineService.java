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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/LineService.java
 * 
 * FILE NAME        	: LineService.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.common.client.dao.ILineDao;
import com.deppon.foss.module.pickup.common.client.dao.IOrgInfoDao;
import com.deppon.foss.module.pickup.common.client.service.ILineService;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;


/**
 * 线路服务类
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:50:13
 */
public class LineService implements ILineService {
	
	/**
	 * 线路dao
	 */
	@Inject
	ILineDao lineDao;
	
	/**
	 * 组织信息dao
	 */
	@Inject
	IOrgInfoDao orgInfoDao;

	/**
	 * 注入 组织信息dao
	 * @param lineDao
	 */
	public void setLineDao(ILineDao lineDao) {
		this.lineDao = lineDao;
	}

	/**
	 * 注入线路dao
	 * @param orgInfoDao
	 */
	public void setOrgInfoDao(IOrgInfoDao orgInfoDao) {
		this.orgInfoDao = orgInfoDao;
	}
	/**
	 * 
	 * 功能：按id查询
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	@Transactional
	public LineEntity queryById(String id) {
		return lineDao.queryById(id);
	}
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	public void addLine(LineEntity line) {
		lineDao.addLine(line);

	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	public void updateLine(LineEntity line) {
		lineDao.updateLine(line); 

	}
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	public void saveOrUpdate(LineEntity line) {
		if(!lineDao.addLine(line)){
			lineDao.updateLine(line); 
		}
	}
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	public List<LineEntity> queryAll() {
		
		return lineDao.queryAll();
	}
	
	 /**
     * 
     * <p>通过始发营业部部门编码查询始发外场部门编码</p> 
     * @author foss-jiangfei
     * @date Nov 6, 2012 4:37:14 PM
     * @param sourceSaleCode
     * @param productCode
     * @return
     * @see
     */
	public String querySourceTransCode(String sourceSaleCode, String productCode){
		String result = StringUtils.EMPTY;
		
		LineEntity sourceCondition = new LineEntity();
		sourceCondition.setOrginalOrganizationCode(sourceSaleCode);
		sourceCondition.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
		sourceCondition.setTransType(productCode);
		sourceCondition.setIsDefault(FossConstants.YES);
		List<LineEntity> sourceList = queryLineListByCondition(sourceCondition);
		if (sourceList.size()==0) {
			// 网点组配置和始发线路中都找不到，说明这是一个孤儿营业部，不能出发
			return result;
		} else {
			// 取得默认的始发配置外场
			return sourceList.get(0).getDestinationOrganizationCode();
		}
	}
	
	 /**
     * 
     * <p>通过到达营业部部门编码查询到达外场部门编码</p> 
     * @author foss-jiangfei
     * @date Nov 6, 2012 4:38:32 PM
     * @param targetSaleCode
     * @param productCode
     * @return
     * @see
     */
	public String queryTargetTransCode(String targetSaleCode, String productCode) {
		String result = StringUtils.EMPTY;
		LineEntity targetCondition = new LineEntity();
		targetCondition.setDestinationOrganizationCode(targetSaleCode);
		targetCondition.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
		//运输类型
		targetCondition.setTransType(productCode);
		targetCondition.setIsDefault(FossConstants.YES);
		List<LineEntity> targetList = queryLineListByCondition(targetCondition);
		if (targetList.size()==0) {
			// 网点组配置和到达线路中都找不到，说明这是一个孤儿营业部，不能出发
			return result;
		} else {
			// 取得默认的始发配置外场
			return targetList.get(0).getOrginalOrganizationCode();
		}
	}
	
	/**
	 * 
	 * <p>根据查询条件查询线路,不带分页</p> 
	 * @author foss-jiangfei
	 * @date Nov 4, 2012 5:20:59 PM
	 * @param line
	 * @return
	 * @see
	 */
	public List<LineEntity> queryLineListByCondition(LineEntity line) {
		return queryLineListByCondition(line, 0, Integer.MAX_VALUE);
	}
	/** 
	 * <p>根据查询条件查询线路</p> 
	 * @author foss-jiangfei
	 * @date Oct 26, 2012 3:22:56 PM
	 * @param line
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILineService#queryLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity, int, int)
	 */
	public List<LineEntity> queryLineListByCondition(LineEntity line,
			int start, int limit) {
		return enhanceLineList(lineDao.queryLineListByCondition(line, start, limit));
	}
	
	// 批量填充出发城市和目的城市
	private List<LineEntity> enhanceLineList(List<LineEntity> list) {
		if (list.size()==0){ 
			return new ArrayList<LineEntity> ();
		}
		for (LineEntity line : list) {
			enhanceLine(line);
		}
		return list;
	}
	// 填充出发城市和目的城市s
	private LineEntity enhanceLine(LineEntity line) {
		if (line == null) {
			return null;
		}
		// 填充出发城市和目的城市s
		line.setOrginalOrganizationName(orgInfoDao.queryByCode(line.getOrginalOrganizationCode()).getName());
		line.setDestinationOrganizationName(orgInfoDao.queryByCode(line.getDestinationOrganizationCode()).getName());
		line.setOrginalCityName(orgInfoDao.queryByCode(line.getOrginalCityCode()).getName());
		line.setDestinationCityName(orgInfoDao.queryByCode(line.getDestinationCityCode()).getName());
		line.setOrganizationName(orgInfoDao.queryByCode(line.getOrganizationCode()).getName());
		
		return line;
	}

	/**
	 * @param lineEntity
	 */
	public void delete(LineEntity lineEntity) {
		lineDao.delete(lineEntity);
	}
}