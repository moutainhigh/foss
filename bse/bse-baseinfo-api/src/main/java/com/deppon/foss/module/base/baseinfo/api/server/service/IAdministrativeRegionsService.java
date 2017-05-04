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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IAdministrativeRegionsService.java
 * 
 * FILE NAME        	: IAdministrativeRegionsService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AdministrativeRegionsDto;

/**
 * 行政区域 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-31 下午4:6:23
 */
public interface IAdministrativeRegionsService extends IService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    AdministrativeRegionsEntity addAdministrativeRegions(AdministrativeRegionsEntity entity);
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    AdministrativeRegionsEntity deleteAdministrativeRegions(AdministrativeRegionsEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午12:55:15
     */
    AdministrativeRegionsEntity deleteAdministrativeRegionsMore(String[] codes , String deleteUser);
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    AdministrativeRegionsEntity updateAdministrativeRegions(AdministrativeRegionsEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsByCode(java.lang.String)
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsExactByEntity(
	    AdministrativeRegionsEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    long queryAdministrativeRegionsExactByEntityCount(AdministrativeRegionsEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsByEntity(AdministrativeRegionsEntity entity,int start, int limit);
	
    /**
     * 查询queryAdministrativeRegionsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午4:6:23
     */
    long queryAdministrativeRegionsByEntityCount(AdministrativeRegionsEntity entity);
		
    
    
    /**
     * 下面是特殊查询
     */

    /**
     * 根据行政区域上级编码查询行政区域列表
     * 
     * @author 087584-lijun
     * @date 2012-10-12 上午10:18:55
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsByParentDistrictCode(
	    String code);

    /**
     * 查询行政区域根结点
     * 
     * @author 087584-lijun
     * @date 2012-10-12 上午10:19:29
     */
    List<AdministrativeRegionsEntity> queryRoot();
    
    /**
     * 根据code查询name
     * 
     * @author 087584-lijun
     * @date 2012-10-12 上午10:33:07
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryCount(java.lang.String,
     *      java.lang.String)
     */
    String queryAdministrativeRegionsNameByCode(
	    String code);

    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCode(
	    List<String> codes);
    
    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     */
    Map<String, AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCodeToMap(
	    List<String> codes);
    
    
    
    /**
     * 下面是特殊方法
     */

    
    /**
     * 根据行政区域编码获取行政区域名称
     * 
     * @author 087584-foss-lijun
     * @date 2013-1-7 上午10:33:17
     */
    String gainDistrictNameByCode(String code);
    /**
     * 获取最后更新时间
     * @author dp-yangtong
     * @date 2012-10-30 下午4:07:16
     * @param  
     * @return
     * @see
     */
    public Date getLastUpdateTime();
    
    /**
	 * @获得行政区域信息，用于行政区域信息同步
	 * @param fromDate
	 * @param toDate
	 * @param pageIndex
	 * @param PageSize
	 * @return Customer
	 */
	public List<AdministrativeRegionsEntity> getAdministrativeRegions(Date fromDate, String userID);
	
	/**
	 * 
	 * <p>根据城市名称精准查询城市信息</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-8 上午11:42:20
	 * @param cityName
	 * @return
	 * @see
	 */
	AdministrativeRegionsEntity queryAdministrativeRegionsByName(String cityName);
	
	/**
	 * 根据城市code获取行政区域信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 下午1:52:27
	 * @param code
	 * @return
	 */
	AdministrativeRegionsEntity queryAdministrativeRegionsByCodeNotCache(String code);	
	
	/**
     * 根据行政区域查询快递试点网点的点坐标和范围坐标
     * 
     * @author WeiXing
     * @date 2014-08-27 上午10:18:55
     */
    List<AdministrativeRegionsDto> queryServerCoordinatesByCode(String code);
    
    /**
     * 根据行政区域查询非试点快递网点的点坐标和范围坐标
     * 
     * @author WeiXing
     * @date 2014-08-27 上午10:18:55
     */
    List<AdministrativeRegionsDto> queryServerCoordinatesNotByCode(String code);

	String queryRegionName(String code);
	
	//350909       郭倩云             零担轻货上分拣取得是城市简称
	String gainDistrictSimpleNameByCode(String code);
}
