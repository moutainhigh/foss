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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IAdministrativeRegionsDao.java
 * 
 * FILE NAME        	: IAdministrativeRegionsDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AdministrativeRegionsDto;


/**
 * 行政区域DAO接口
 * 
 * @author 087584-lijun
 * @date 2012-10-12 上午10:15:32
 */
public interface IAdministrativeRegionsDao{
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:5:32
     */
    AdministrativeRegionsEntity addAdministrativeRegions(AdministrativeRegionsEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:5:32
     */
    AdministrativeRegionsEntity deleteAdministrativeRegions(AdministrativeRegionsEntity entity);

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:5:32
     */
    AdministrativeRegionsEntity deleteAdministrativeRegionsMore(String[] codes , String deleteUser);
	
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:5:32
     */
    AdministrativeRegionsEntity updateAdministrativeRegions(AdministrativeRegionsEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:31:59
     */
    AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code);	

    /**
     * 通过 标识编码CODE,是否有效ACTIVE精确查询
     * 
     * 两个参数都可传空，当传空时，不做为查询条件，查询时，取更新时间最近的一条
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @param code 标识编码（组织编码）
     * @param active FossConstants.YES:FossConstants.NO
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsByCodeActive(
	    List<String> codes, String active) ; 
    
    /**
     * 精确查询
     * 根据多个CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:31:59
     * @see com.deppon.foss.module.base.dict.api.server.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsByCode(java.lang.String)
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:31:59
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsExactByEntity(
	    AdministrativeRegionsEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:31:59
     */
    long queryAdministrativeRegionsExactByEntityCount(AdministrativeRegionsEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:31:59
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsByEntity(
	    AdministrativeRegionsEntity entity, int start, int limit);

    /**
     * 查询queryAdministrativeRegionsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午3:31:59
     */
    long queryAdministrativeRegionsByEntityCount(AdministrativeRegionsEntity entity);
	
    /**
     * 根据entity精确查询
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午4:36:59
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsForDownload(AdministrativeRegionsEntity entity);

    /**
     * 下载分页查询 
     * 
     * @author 157229-foss-zxy
     * @date 2014-04-17 
     */
    List<AdministrativeRegionsEntity> queryAdministrativeRegionsForDownloadByPage(
    	    AdministrativeRegionsEntity entity,int start, int limited);
    
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
     * 获取最后跟新时间
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param 
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    Date getLastModifyTime();
    
    /** 
     * 数据更新
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity 行政区域实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    List<AdministrativeRegionsEntity> getSyncData(Date fromDate, String userID);

    /**
     * 
     * <p>根据Code查询Name</p> 
     * @author foss-zhujunyong
     * @date Jan 25, 2013 11:27:41 AM
     * @param code
     * @return
     * @see
     */
    String queryRegionNameByCode(String code);
	
    /**
     * 
     * <p>按照城市名称名称查询有效城市信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-8 上午11:44:01
     * @param cityName
     * @return
     * @see
     */
    AdministrativeRegionsEntity queryAdministrativeRegionsByName(String cityName);
    
    /**
     * 根据行政区域代码查询快递试点网点的点坐标和范围坐标
     * 
     * @author WeiXing
     * @date 2014-08-27 上午10:18:55
     */
    List<AdministrativeRegionsDto> queryServerCoordinatesByCode(String code);
    
    /**
     * 根据行政区域代码查询非试点快递网点的点坐标和范围坐标
     * 
     * @author WeiXing
     * @date 2014-08-27 上午10:18:55
     */
    List<AdministrativeRegionsDto> queryServerCoordinatesNotByCode(String code);
}
