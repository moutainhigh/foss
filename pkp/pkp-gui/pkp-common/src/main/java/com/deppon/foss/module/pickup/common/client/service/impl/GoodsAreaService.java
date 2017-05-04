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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/GoodsAreaService.java
 * 
 * FILE NAME        	: GoodsAreaService.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.pickup.common.client.dao.IGoodsAreaDao;
import com.deppon.foss.module.pickup.common.client.service.IGoodsAreaService;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 库区服务
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午2:38:21
 */
public class GoodsAreaService implements IGoodsAreaService {

	@Inject
	public IGoodsAreaDao goodsAreaDao;
	
	@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	@Inject
	private IOutfieldService outfieldService;
	 
	 
	

	/**
	 * 
	 * 功能：按id查询
	 * 
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	@Override
	public GoodsAreaEntity queryById(String id) {

		return goodsAreaDao.queryById(id);
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
	public void addGoodsArea(GoodsAreaEntity goodsArea) {

		goodsAreaDao.addGoodsArea(goodsArea);
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
	public void updateGoodsArea(GoodsAreaEntity goodsArea) {
		goodsAreaDao.updateGoodsArea(goodsArea);

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
	public void saveOrUpdate(GoodsAreaEntity goodsArea) {
		if(!goodsAreaDao.addGoodsArea(goodsArea)){
			goodsAreaDao.updateGoodsArea(goodsArea);
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
	public List<GoodsAreaEntity> queryAll() {

		return goodsAreaDao.queryAll();
	}

	/**
	 * <p>
	 * (根据外场部门编码和目的站部门编码取货区编码)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-5 下午1:20:30
	 * @param organizationCode
	 * @param arriveRegionCode
	 * @return
	 * @see com.deppon.foss.module.pickup.common.client.service.IGoodsAreaService#queryCodeByArriveRegionCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String queryCodeByArriveRegionCode(String organizationCode,
			String arriveRegionCode) {
		if (StringUtils.isBlank(organizationCode)
				|| StringUtils.isBlank(arriveRegionCode)) {
			return null;
		}
		GoodsAreaEntity c = new GoodsAreaEntity();
		c.setActive(FossConstants.ACTIVE);
		c.setOrganizationCode(organizationCode);
		c.setArriveRegionCode(arriveRegionCode);
		// 只取1笔记录
		List<GoodsAreaEntity> list = queryGoodsAreaByCondition(c, 0, 1);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0).getGoodsAreaCode();
	}

	/**
	 * <p>
	 * 按条件查询库区列表
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Oct 30, 2012 10:35:16 AM
	 * @param goodsArea
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService#queryGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity,
	 *      int, int)
	 */
	@Override
	public List<GoodsAreaEntity> queryGoodsAreaByCondition(
			GoodsAreaEntity goodsArea, int start, int limit) {
		return goodsAreaDao.queryGoodsAreaByCondition(goodsArea, start, limit);
	}
	
	/**
     * 
     * <p>根据库区类型（卡货库区，普货库区，贵重物品库区等）</p> 
     * @author foss-zhujunyong
     * @date Nov 2, 2012 3:26:39 PM
     * @param goodsAreaType 库区类型，数据字典中取值
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION 异常货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE  贵重物品货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING   包装货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER     偏线货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION   驻地派送货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON    混装货区
     * @return 该类型的库区列表
     * @see
     */
    @Override
    public List<GoodsAreaEntity> queryGoodsAreaListByType(String organizationCode, String goodsAreaType) {
	List<GoodsAreaEntity> resultList = new ArrayList<GoodsAreaEntity> ();
	if (StringUtils.isBlank(organizationCode)) {
	    return resultList;
	}
	return enhanceGoodsAreaList(goodsAreaDao.queryGoodsAreaListByOrganizationCode(organizationCode, goodsAreaType));
    }
    
    
    // 批量填充外场名称
    private List<GoodsAreaEntity> enhanceGoodsAreaList(List<GoodsAreaEntity> list) {
	if (CollectionUtils.isEmpty(list)) {
	    return new ArrayList<GoodsAreaEntity> ();
	}
	for (GoodsAreaEntity goodsArea : list) {
	    enhanceGoodsArea(goodsArea);
	}
	return list;
    }
    
    
 // 填充外场名称
    private GoodsAreaEntity enhanceGoodsArea(GoodsAreaEntity goodsArea) {
	if (goodsArea == null) {
	    return null;
	}
	if (StringUtils.isBlank(goodsArea.getOrganizationName())) {
	    goodsArea.setOrganizationName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(goodsArea.getOrganizationCode()));
	}
	if (StringUtils.isBlank(goodsArea.getArriveRegionName())) {
	    goodsArea.setArriveRegionName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(goodsArea.getArriveRegionCode()));
	}
	if (StringUtils.isBlank(goodsArea.getTransferCode())) {
	    OutfieldEntity outfield = outfieldService.queryOutfieldByOrgCode(goodsArea.getOrganizationCode());
	    if (outfield != null) {
		goodsArea.setTransferCode(outfield.getCode());
	    }
	}
	return goodsArea;
    }

	/**
	 * 删除
	 * @param goodsAreaEntity
	 */
	public void delete(GoodsAreaEntity goodsAreaEntity) {
		goodsAreaDao.delete(goodsAreaEntity);
	}
}