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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IGoodsAreaDao.java
 * 
 * FILE NAME        	: IGoodsAreaDao.java
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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;


/**
 * 库区Dao
 * @author foss-zhujunyong
 * @date Oct 19, 2012 4:21:13 PM
 * @version 1.0
 */
public interface IGoodsAreaDao {

    /**
     * 
     * <p>添加库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:50:34 AM
     * @param goodsArea
     * @return
     * @see
     */
    GoodsAreaEntity addGoodsArea(GoodsAreaEntity goodsArea);
    
    /**
     * 
     * <p>作废库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:50:46 AM
     * @param goodsArea
     * @return
     * @see
     */
    GoodsAreaEntity deleteGoodsArea(GoodsAreaEntity goodsArea);

    /**
     * 
     * <p>更新库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:51:05 AM
     * @param goodsArea
     * @return
     * @see
     */
    GoodsAreaEntity updateGoodsArea(GoodsAreaEntity goodsArea);
    
    /**
     * 
     * <p>根据虚拟代码查询库区详情</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:51:20 AM
     * @param virtualCode
     * @return
     * @see
     */
    GoodsAreaEntity queryGoodsAreaByVirtualCode(String virtualCode);

    /**
     * 
     * <p>按条件查询库区列表</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:51:51 AM
     * @param goodsArea
     * @return
     * @see
     */
    List<GoodsAreaEntity> queryGoodsAreaByCondition(GoodsAreaEntity goodsArea, int start, int limit);
    
    /**
     * 
     * <p>按条件查询库区数量</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:52:11 AM
     * @param goodsArea
     * @return
     * @see
     */
    long countGoodsAreaByCondition(GoodsAreaEntity goodsArea);

    /**
     * 
     * <p>查询某一外场下的所有库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:52:25 AM
     * @param organizationCode
     * @return
     * @see
     */
    List<GoodsAreaEntity> queryGoodsAreaListByOrganizationCode(String organizationCode, String goodsAreaType);

    /**
     * 
     * <p>下载货区</p> 
     * @author foss-zhujunyong
     * @date Oct 31, 2012 10:45:51 AM
     * @param goodsArea
     * @return
     * @see
     */
    List<GoodsAreaEntity> queryGoodsAreaListForDownload(GoodsAreaEntity goodsArea);
    
    /**
     * 
     * <p>批量作废库区</p> 
     * @author foss-zhujunyong
     * @date Nov 28, 2012 11:44:13 AM
     * @param goodsArea
     * @return
     * @see
     */
    int deleteGoodsAreas(List<String> virtualCodes, String modifyUser);
    
    /**
     * 
     * <p>取最后修改时间</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:37:04 PM
     * @return
     * @see
     */
    Date queryLastModifyTime();    
    
    /**
     * 
     * <p>取所有激活的库区进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<GoodsAreaEntity> queryGoodsAreaListForCache();

	/**
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<GoodsAreaEntity> queryGoodsAreaListForDownloadByPage(GoodsAreaEntity entity, int start,
			int thousand);    
	
	/**
     * 根据外场编码查询快递货区
     * 
     * @author  WangPeng
     * @Date    2013-8-12 下午2:23:39
     * @param   orgCode
     * @return  GoodsAreaEntity
     *  
     */
    List<GoodsAreaEntity> queryExpressGoodsAreaByTransCenterCode(String orgCode);

    /**
     * 根据外场编码查询有计票标志的库区
     * 
     * @author  lifanghong
     * @Date    2014-02-13
     * @param   orgCode
     * @return  List<GoodsAreaEntity>
     *  
     */
	List<GoodsAreaEntity> queryCountingModeGoodsAreaListByOrganizationCode(
			String organizationCode);

	/**
	 * <p>作为接口给中转调用
	 *    	  查询参数：驻地营业部编码
	 *    	  返回参数：快递驻地库区和驻地派送库区，这两个库区的实体（包含code和name）</p> 
	 * @author 232607 
	 * @date 2015-6-8 上午11:08:17
	 * @param code
	 * @return
	 * @see
	 */
	List<GoodsAreaEntity> queryGoodsAreaByStationSalesDept(String code);
    
}
