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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IPlatformService.java
 * 
 * FILE NAME        	: IPlatformService.java
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
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PlatformExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PlatformGoodsAreaDistanceDto;


/**
 * 月台服务类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhujunyong,date:Oct 12, 2012 10:56:05 AM</p>
 * @author zhujunyong
 * @date Oct 12, 2012 10:56:05 AM
 * @since
 * @version
 */
public interface IPlatformService  extends IService {

    	/**
    	 * 
    	 * <p>增加月台</p> 
    	 * @author zhujunyong
    	 * @date Oct 12, 2012 11:02:38 AM
    	 * @param platform
    	 * @return
    	 * @see
    	 */
    	PlatformEntity addPlatform(PlatformEntity platform);

	/**
	 * 
	 * <p>更新月台</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:03:45 AM
	 * @param platform
	 * @return
	 * @see
	 */
	PlatformEntity updatePlatform(PlatformEntity platform);
	
	/**
	 * 
	 * <p>取单个月台</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:04:00 AM
	 * @param virtualCode
	 * @return
	 * @see
	 */
	PlatformEntity queryPlatformByVirtualCode(String virtualCode);
	
	/**
	 * 
	 * <p>按条件查询月台</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:14 AM
	 * @param platform
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<PlatformEntity> queryPlatformListByCondition(PlatformEntity platform, int start, int limit);

	/**
	 * 
	 * <p>按条件查询月台，含数据权限</p> 
	 * @author foss-zhujunyong
	 * @date Jan 22, 2013 4:25:02 PM
	 * @param _platform
	 * @param start
	 * @param limit
	 * @param userCode 员工号
	 * @return
	 * @see
	 */
	List<PlatformEntity> queryPlatformListByCondition(PlatformEntity platform, int start, int limit, String userCode, String deptCode);

	/**
	 * 
	 * <p>按条件查询月台总数</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:33 AM
	 * @param platform
	 * @return
	 * @see
	 */
	long countPlatformListByCondition(PlatformEntity platform);

	/**
	 * 
	 * <p>按条件查询月台总数</p> 
	 * @author zhujunyong
	 * @date Oct 12, 2012 11:06:33 AM
	 * @param platform
	 * @param userCode 员工号
	 * @return
	 * @see
	 */
	long countPlatformListByCondition(PlatformEntity platform, String userCode, String deptCode);

	/**
	 * 
	 * <p>查询某一外场下的所有月台列表</p> 
	 * @author foss-zhujunyong
	 * @date Oct 16, 2012 10:42:43 AM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	List<PlatformEntity> queryPlatformListByOrganizationCode(String organizationCode);

	/**
	 * 
	 * <p>查询某一外场下的所有月台列表</p> 
	 * @author foss-zhujunyong
	 * @date Oct 16, 2012 10:42:43 AM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	List<PlatformEntity> querySimplePlatformListByOrganizationCode(String organizationCode);

	/**
	 * 
	 * <p>查询某一外场下的在起始编号(含)和截止编号(含)之间的月台列表</p> 
	 * @author foss-zhujunyong
	 * @date Nov 16, 2012 5:44:41 PM
	 * @param organizationCode
	 * @param startCode
	 * @param endCode
	 * @return
	 * @see
	 */
	List<PlatformEntity> queryPlatformListByOrgCodeAndPlatformCodeLimit(String organizationCode, String startCode, String endCode);
	
	/**
	 * 
	 * <p>查询某一外场下月台到库区的距离（单位米）</p>
	 * 算出月台到库位的距离平均值返回 ，并折算单位
	 * @author foss-zhujunyong
	 * @date Nov 20, 2012 2:25:13 PM
	 * @param organizationCode
	 * @param platformCode
	 * @param goodsAreaCode
	 * @return
	 * @see
	 * @deprecated use queryDistanceByPlatformGoodsAreaVir instead of it
	 */
	
	/**
	 * 
	 * <p>查询某一外场下月台到库区的距离（单位米）</p>
	 * 算出月台到库位的距离平均值返回 ，并折算单位
	 * @author foss-zhujunyong
	 * @date Feb 27, 2013 10:39:36 AM
	 * @param organizationCode
	 * @param platformVirtualCode
	 * @param goodsAreaVirtualCode
	 * @return
	 * @see
	 */
	Double queryDistanceByPlatformGoodsAreaVir(String organizationCode, String platformVirtualCode, String goodsAreaVirtualCode);
	
	/**
	 * 
	 * <p>查询指定外场，指定月台编码的月台</p> 
	 * @author foss-zhujunyong
	 * @date Nov 26, 2012 9:52:54 AM
	 * @param organizationCode
	 * @param platformCode
	 * @return
	 * @see
	 */
	PlatformEntity queryPlatformByCode(String organizationCode, String platformCode);
	
	/**
	 * 
	 * <p>返回可用的月台列表</p> 
	 * 车型代码要与PlatformEntity中的一致
	 * @author foss-zhujunyong
	 * @date Nov 27, 2012 2:30:39 PM
	 * @param organizationCode
	 * @param vehicleTypeCode
	 * @return
	 * @see
	 */
	List<PlatformEntity> queryPlatformListByVehicleType(String organizationCode, String vehicleTypeCode);
	
	/**
	 * 
	 * <p>返回可用的月台列表</p> 
	 * 车型代码要与PlatformEntity中的一致
	 * @author foss-zhujunyong
	 * @date Nov 27, 2012 2:30:39 PM
	 * @param organizationCode
	 * @param vehicleTypeCode
	 * @return
	 * @see
	 */
	List<PlatformEntity> querySimplePlatformListByVehicleType(String organizationCode, String vehicleTypeCode);
	
	/**
	 * 
	 * <p>批量作废月台</p> 
	 * @author foss-zhujunyong
	 * @date Nov 28, 2012 11:04:34 AM
	 * @param virtualCodes
	 * @param modifyUser
	 * @return
	 * @see
	 */
	int deletePlatforms(List<String> virtualCodes, String modifyUser);

	/**
	 * 
	 * <p>导入excel中的月台数据</p> 
	 * @author foss-zhujunyong
	 * @date Dec 6, 2012 3:33:14 PM
	 * @param dtoList
	 * @param createUser
	 * @return
	 * @see
	 */
	List<Integer> importPlatformList(List<PlatformExcelDto> dtoList, String createUser);	

	/**
	 * 
	 * <p>导出表中的月台数据</p> 
	 * @author foss-zhujunyong
	 * @date Dec 7, 2012 11:16:52 AM
	 * @param platform
	 * @param userCode
	 * @return
	 * @see
	 */
	ExportResource exportPlatformList(PlatformEntity platform, String userCode, String deptCode);	
	
	/**
	 * 
	 * <p>查询指定外场下的月台总数</p> 
	 * @author foss-zhujunyong
	 * @date Jan 31, 2013 5:44:23 PM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	int queryPlatformCountByOrganizationCode(String organizationCode);

	/**
	 * 
	 * <p>查询指定外场下有升降机的月台总数</p> 
	 * @author foss-zhujunyong
	 * @date Jan 31, 2013 5:44:39 PM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	int queryPlatformWithLiftCountByOrganizationCode(String organizationCode);
	
	/**
	 * 
	 * <p>查询指定外场下可停靠车长大于等于9.6米的月台个数</p> 
	 * @author foss-zhujunyong
	 * @date Jan 31, 2013 5:44:59 PM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	int queryBigPlatformCountByOrganizationCode(String organizationCode, Map<String, Integer> vehicleTypeMap);

	/**
	 * 
	 * <p>查询指定外场下可停靠车长小于9.6米的月台个数</p> 
	 * @author foss-zhujunyong
	 * @date Jan 31, 2013 5:45:39 PM
	 * @param organizationCode
	 * @return
	 * @see
	 */
	int querySmallPlatformCountByOrganizationCode(String organizationCode, Map<String, Integer> vehicleTypeMap);

    /**
     * 
     * <p>
     * 查询指定外场的所有月台到库位的距离
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 11, 2013 5:04:45 PM
     * @param organizationCode
     * @return
     * @see
     */
    List<DistanceEntity> queryDistanceListByOrganizationCode(String organizationCode);
    
    
    /**
     * 
     * <p>根据外场部门编码，到达部门编码和第三级产品类型找出最近的月台</p> 
     * 如果无月台或库区，则返回null
     * 
     * @author foss-zhujunyong
     * @date Apr 1, 2013 2:02:54 PM
     * @param organizationCode
     * @param arriveRegionCode
     * @param productCode 如果传入值为null，则返回找到的到第一个库区（无论卡普混装）的距离
     * @return
     * @see
     */
    PlatformEntity queryMinDistancePlatform(String organizationCode, String arriveRegionCode, String productCode);
    
    
    /**
     * 
     * <p>TODO("根据外场、月台、库区查询月台到库区的距离；")</p> 
     * @author 183272
     * @date 2014-4-24 上午10:06:29
     * @param organizationCode
     * @param goodsAreaCode
     * @param platformCode
     * @return
     * @see
     */
    public PlatformGoodsAreaDistanceDto queryDistanceByOrganizationPlatformGoodsarea(String organizationCode,String goodsAreaCode,String  platformCode );
    

    /**
     * 
  
     * 返回外场适合对应车型对应类型(新增字段：长途、短途、接送货)的月台列表；
     * 车型代码要与PlatformEntity中的一致
     * @author 183272
     * @date 2014-4-24 下午3:15:31
     * @param organizationCode
     * @param vehicleTypeCode
     * @param longDistance 长途  可取值为"Y" or "N"
     * @param shortDistance 短途  可取值为"Y" or "N"
     * @param pkp 接送货  可取值为"Y" or "N"
     * @return
     * @see
     */
	List<PlatformEntity> queryPlatformListByVehicleType2(String organizationCode, String vehicleTypeCode,String longDistance,String shortDistance,String pkp);
    
}
