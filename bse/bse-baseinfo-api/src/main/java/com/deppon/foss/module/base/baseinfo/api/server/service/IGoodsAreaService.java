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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IGoodsAreaService.java
 * 
 * FILE NAME        	: IGoodsAreaService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatAreaDistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.GoodsAreaExcelDto;


/**
 * 库区服务类
 * @author foss-zhujunyong
 * @date Oct 30, 2012 10:33:53 AM
 * @version 1.0
 */
public interface IGoodsAreaService extends IService{

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
     * <p>按条件查询库区列表</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:51:51 AM
     * @param goodsArea
     * @param userCode 员工号
     * @return
     * @see
     */
    List<GoodsAreaEntity> queryGoodsAreaByCondition(GoodsAreaEntity goodsArea, int start, int limit, String userCode, String deptCode);

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
     * <p>按条件查询库区数量</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:52:11 AM
     * @param goodsArea
     * @param userCode 员工号
     * @return
     * @see
     */
    long countGoodsAreaByCondition(GoodsAreaEntity goodsArea, String userCode, String deptCode);
    
    /**
     * 
     * <p>查询某一外场下的所有库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:52:25 AM
     * @param organizationCode
     * @return
     * @see
     */
    List<GoodsAreaEntity> queryGoodsAreaListByOrganizationCode(String organizationCode);

    /**
     * 
     * <p>查询某一外场下的所有库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:52:25 AM
     * @param organizationCode
     * @return
     * @see
     */
    List<GoodsAreaEntity> querySimpleGoodsAreaListByOrganizationCode(String organizationCode);
    
    /**
     * 
     * <p>根据外场编码和库区编码取库区名称</p> 
     * @author foss-zhujunyong
     * @date Oct 30, 2012 10:49:35 AM
     * @param organizationCode 组织代码
     * @param code 库区编码
     * @return
     * @see
     */
    String queryNameByCode(String organizationCode, String code);
    
    /**
     * 
     * <p>根据外场编码和库区编码取库区实体</p> 
     * @author foss-zhujunyong
     * @date Nov 5, 2012 2:21:46 PM
     * @param organizationCode
     * @param code
     * @return
     * @see
     */
    GoodsAreaEntity queryGoodsAreaByCode(String organizationCode, String code);
    
    
    /**
     * 
     * <p>根据外场部门编码和目的站部门编码取货区编码</p> 
     * @author foss-zhujunyong
     * @date Oct 30, 2012 2:31:44 PM
     * @param organizationCode 所在外场部门编码
     * @param arriveRegionCode 目的站部门编码
     * @return
     * @see
     * @deprecated 使用 String queryCodeByArriveRegionCode(String organizationCode, String arriveRegionCode, String productCode) 取代,该方法未区分卡，普和混装库区
     */
    String queryCodeByArriveRegionCode(String organizationCode, String arriveRegionCode);
    
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
    List<GoodsAreaEntity> queryGoodsAreaListByType(String organizationCode, String goodsAreaType);
 
    /**
     * 
     * <p>根据库区类型（卡货库区，普货库区，贵重物品库区等）</p> 
     * @author foss-zhujunyong
     * @date May 10, 2013 11:06:13 AM
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
    List<GoodsAreaEntity> querySimpleGoodsAreaListByType(String organizationCode, String goodsAreaType);    
    
    /**
     * 
     * <p>批量作废库区</p> 
     * @author foss-zhujunyong
     * @date Nov 28, 2012 11:48:10 AM
     * @param virtualCodes
     * @param modifyUser
     * @return
     * @see
     */
    int deleteGoodsAreas(List<String> virtualCodes, String modifyUser);

    /**
     * 
     * <p>根据外场部门编码和目的站部门编码取货区编码</p> 
     * @author foss-zhujunyong
     * @date Jan 5, 2013 1:55:20 PM
     * @param organizationCode
     * @param arriveRegionCode
     * @param productCode
     * @return
     * @see
     */
    String queryCodeByArriveRegionCode(String organizationCode, String arriveRegionCode, String productCode);


    /**
     * 
     * <p>根据外场部门编码和目的站部门编码取货区实体</p> 
     * @author foss-zhujunyong
     * @date Jan 6, 2013 1:55:20 PM
     * @param organizationCode
     * @param arriveRegionCode
     * @param productCode
     * @return
     * @see
     */
    GoodsAreaEntity queryGoodsAreaByArriveRegionCode(String organizationCode, String arriveRegionCode, String productCode);
   /**
    * 
    *<p>根据虚拟编码查询库区实体（从数据库中查询实体，不做填充操作）</p>
    *@author 130566-zengJunfan
    *@date   2013-8-1上午9:46:36
    * @param virtualCode
    * @return
    */
    GoodsAreaEntity searchGoodsAreaEntityByVirtualCode(String virtualCode);
    
    
    /**
     * 根据外场编码查询快递货区
     * 
     * @author  WangPeng
     * @Date    2013-8-12 下午2:23:39
     * @param   orgCode
     * @return  GoodsAreaEntity
     *  
     */
    GoodsAreaEntity queryExpressGoodsAreaByTransCenterCode(String orgCode, String productCode);

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
     * <p>根据纵横坐标初始化库区离月台距离</p> 
     * @author 107046
     * @date 2014-04-25
     * @return
     */
	List<PlatAreaDistanceEntity> initPlatAreaDistance(GoodsAreaEntity entity);

    List<List<String>> genDataForExport(GoodsAreaEntity entity, int start, int limit);
    
    List<List<String>> genTemplateData();
	
    long countPlatformByOrgCode(String orgCode);
	
    List<Integer> importGoodsAreaList(List<GoodsAreaExcelDto> dtoList, String createUser);

	/**
	 * <p>作为接口给中转调用
	 *    	  查询参数：驻地营业部编码
	 *    	  返回参数：快递驻地库区和驻地派送库区，这两个库区的实体（包含code和name）</p> 
	 * @author 232607 
	 * @date 2015-6-8 下午2:15:06
	 * @param code
	 * @return
	 * @see
	 */
	List<GoodsAreaEntity> queryGoodsAreaByStationSalesDept(String code);
	
}
