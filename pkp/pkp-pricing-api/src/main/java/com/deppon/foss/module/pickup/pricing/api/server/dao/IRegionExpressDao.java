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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IRegionDao.java
 * 
 * FILE NAME        	: IRegionDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionOrgExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;

public interface IRegionExpressDao {
	/**.
	 * <p>
	 * 根据条件查询区域信息<br/>（分页）
	 * 方法名：searchRegionByCondition
	 * </p>
	 * @param regionEntity 查询条件
	 * @param start 其实查询位置
	 * @param limit 每页几条
	 * @author 张斌
	 * @时间 2012-10-12
	 * @since JDK1.6
	 */
	 List<PriceRegionExpressEntity> searchRegionByCondition(PriceRegionExpressEntity regionEntity,
			int start, int limit);
	
	/**.
	 * <p>
	 * 查询行政组织区域存在项<br/>（分页）
	 * 方法名：checkRegionOrganizationType
	 * </p>
	 * @param administrativeRegionCodes 行政区域CODE集合
	 * @param regionNature 区域性之
	 * @author 张斌
	 * @时间 2013-1-25
	 * @since JDK1.6
	 */
	 List<PriceRegionExpressEntity> checkRegionOrganizationType(String id,String administrativeRegionCodes,String regionNature);
	
	/**.
	 * <p>
	 * 根据区域名称区域信息<br/>
	 * 方法名：searchRegionByName
	 * </p>
	 * @param regionName 查询条件区域名称
	 * @author 张斌
	 * @时间 2012-11-23
	 * @since JDK1.6
	 */
	 List<PriceRegionExpressEntity> searchRegionByName(String regionName,String regionNature);
	/**.
	 * <p>
	 * 根据条件查询区域信息个数<br/>
	 * 方法名：countRegionByCondition
	 * </p>
	 * @param regionEntity 查询条件
	 * @author 张斌
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	Long countRegionByCondition(
			PriceRegionExpressEntity regionEntity);
	
	/**.
	 * <p>
	 * 新增区域<br/>
	 * 方法名：addRegion
	 * </p>
	 * @param regionEntity 新增区域
	 * @author 张斌
	 * @时间 2012-10-13
	 * @since JDK1.6
	 */
	void addRegion(
			PriceRegionExpressEntity regionEntity);
	
	/**.
	 * <p>
	 * 修改区域<br/>
	 * 方法名：updateRegion
	 * </p>
	 * @param regionEntity 修改区域
	 * @author 张斌
	 * @时间 2012-10-15
	 * @since JDK1.6
	 */
	void updateRegion(
			PriceRegionExpressEntity regionEntity);
	
	/**.
	 * <p>
	 * 新增区域与部门的关联<br/>
	 * 方法名：addRegionOrg
	 * </p>
	 * @param addPriceRegioOrgnEntityList 新增区域与部门的关联
	 * @author 张斌
	 * @时间 2012-10-13
	 * @since JDK1.6
	 */
	void addRegionOrg(
			PriceRegioOrgnExpressEntity priceRegioOrgnEntity);
	/**.
	 * <p>
	 * 修改区域与部门的关联<br/>
	 * 方法名：updateRegionOrg
	 * </p>
	 * @param addPriceRegioOrgnEntityList 新增区域与部门的关联
	 * @author 张斌
	 * @时间 2012-10-15
	 * @since JDK1.6
	 */
	void updateRegionOrg(
			PriceRegioOrgnExpressEntity priceRegioOrgnEntity);
	
	/**.
	 * <p>
	 * 根据条件查询区域关联部门信息<br/>
	 * 方法名：searchRegionOrgByCondition
	 * </p>
	 * @param priceRegioOrgnEntity 查询条件
	 * @author 张斌
	 * @时间 2012-10-14
	 * @since JDK1.6
	 */
	 List<PriceRegioOrgnExpressEntity> searchRegionOrgByCondition(PriceRegioOrgnExpressEntity priceRegioOrgnEntity);
	/**.
	 * <p>
	 * 激活区域<br/>
	 * 方法名：activeRegion
	 * </p>
	 * @param regionIds 要激活的区域的ID
	 * @param regionNature 区域性质
	 * @author 张斌
	 * @时间 2012-10-14
	 * @since JDK1.6
	 */
	 void activeRegion(List<String> regionIds,String regionNature);
	/**.
	 * <p>
	 * 根据条件查询区域信息<br/>
	 * 方法名：findRegionByCondition
	 * </p>
	 * @param regionEntity 查询条件
	 * @param start 起始查询位置
	 * @param limit 每页几条
	 * @author 张斌
	 * @时间 2012-10-12
	 * @since JDK1.6
	 */
	List<PriceRegionExpressEntity> findRegionByCondition(PriceRegionExpressEntity regionEntity);
	
	/**
	 * <p>根据行政区域查逻辑区域</p> 
	 *  行政区域由小到达，逐级过滤。传入的各级行政区域为或者的关系。
	 * @author zhangdongping
	 * @date 2012-11-4 下午2:01:41
	 * @param parmObject
	 * @return
	 * @see
	 */
	 List<PriceRegionExpressEntity> searchRegionByDistrict(
		PriceRegionExpressEntity regionEntity);	
	 /**
		 * <p> 根据行政区域查逻辑区域（区域查询专用，其他不可用）</p> 
		 *  行政区域由小到达，逐级过滤。传入的各级行政区域为或者的关系。
		 * @author zhangdongping
		 * @date 2012-11-4 下午2:01:41
		 * @param parmObject
		 * @return
		 * @see
		 */
	 List<PriceRegionExpressEntity> searchRegionByDistrictNew(PriceRegionExpressEntity regionEntity);
	/**
	 * .
	 * <p>
	 * 根据ID与区域标识查询区域信息<br/>
	 * 方法名：searchRegionByID
	 * </p>
	 * 
	 * @param id
	 *           id
	 * @param regionNature
	 *           区域性质
	 * @author 张斌
	 * @时间 2012-10-12
	 * @since JDK1.6
	 */
	 PriceRegionExpressEntity searchRegionByID(String id,String regionNature);
	/**.
	 * <p>
	 * 激活区域下关联的区域部门<br/>
	 * 方法名：activeRegionOrg
	 * </p>
	 * @param regionId 区域ID
	 * @param regionNature 区域性质
	 * @author 张斌
	 * @时间 2012-12-07
	 * @since JDK1.6
	 */
	 void activeRegionOrg(String regionId, String regionNature);
	
	/**.
	 * <p>
	 * 删除区域<br/>
	 * 方法名：deleteRegion
	 * </p>
	 * @param regionIds 区域ID
	 * @param regionNature 区域性质
	 * @author 张斌
	 * @时间 2013-1-25
	 * @since JDK1.6
	 */
	 void deleteRegion(List<String> regionIds, String regionNature);
	
	/**.
	 * <p>
	 * 删除区域下关联部门<br/>
	 * 方法名：deleteRegion
	 * </p>
	 * @param regionIds 区域ID
	 * @param regionNature 区域性质
	 * @author 张斌
	 * @时间 2013-1-25
	 * @since JDK1.6
	 */
	 void deleteRegionOrg(List<String> regionIds, String regionNature);

	/**
	 * 根据条件查询价格区域信息
	 * @author sz
	 * @date 2012-12-11 上午11:10:34
	 * @param priceRegionEntity
	 * @return
	 * @see
	 */
	 List<PriceRegionExpressEntity> selectPriceRegionByCondition(PriceRegionExpressEntity priceRegionEntity);
	/**
	 * 根据条件查询价格区域部门信息
	 * @author sz
	 * @date 2012-12-11 上午11:10:34
	 * @param priceRegioOrgnEntity
	 * @return
	 * @see
	 */
	 List<PriceRegioOrgnExpressEntity> selectPriceRegionOrgByCondition(PriceRegioOrgnExpressEntity priceRegioOrgnEntity);
	
	/**
	 * 根据条件查询时效区域信息
	 * @author sz
	 * @date 2012-12-11 上午11:10:34
	 * @param effectiveRegionEntity
	 * @return
	 * @see
	 */
	 List<EffectiveRegionExpressEntity> selectEffectiveRegionByCondition(EffectiveRegionExpressEntity effectiveRegionEntity);
	/**
	 * 根据条件查询时效区域部门信息
	 * @author sz
	 * @date 2012-12-11 上午11:10:34
	 * @param effectiveRegionOrgEntity
	 * @return
	 * @see
	 */
	 List<EffectiveRegionOrgExpressEntity> selectEffectiveRegionOrgByCondition(EffectiveRegionOrgExpressEntity effectiveRegionOrgEntity);

	/**
	 * 根据区域名称批量查找区域信息
	 * @author zhangdongping
	 * @date 2012-12-22 下午8:26:15
	 * @param names 区域名称List
	 * @param regionNature 区域性质
	 * @param billDate 开单日期
	 * @return
	 * @see
	 */
	 Map<String, PriceRegionExpressEntity> findRegionByNames(
		List<String> names, String regionNature, Date billDate);
	
	/**
	 * 
	 * @Description: 根据区域CODE 批量查找区域信息
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-8 下午8:09:34
	 * @param names
	 * @param regionNature
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	 List<PriceRegionExpressEntity> findRegionByCodes(List<String> codes, String regionNature, Date billDate);
	
	/**
	 * 
	 * @Description: 根据District CODE查询区域信息
	 * @author FOSSDP-sz
	 * @date 2013-2-20 上午11:04:55
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionExpressEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode,
			String countyCode, String regionNature, Date billDate);
	/**
	 * 
	 * @Description: 根据网点CODE查询价格区域关联部门 
	 * @author FOSSDP-sz
	 * @date 2013-2-19 下午1:54:28
	 * @param deptNo
	 * @return
	 * @version V1.0
	 */
	List<PriceRegioOrgnExpressEntity> searchRegionOrgByDeptNo(String deptNo, String regionNature);
	/**
	 * 
	 * @Description: 根据区域ID查询价格区域关联部门 
	 * @author FOSSDP-sz
	 * @date 2013-4-22 下午1:50:42
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegioOrgnExpressEntity> searchRegionOrgByRegionId(String deptRegionId, String regionNature);

	List<PriceRegionExpressEntity> searchRegionReNameByName(String regionName,
			Date beginTime, String regionNature);

	List<PriceRegionExpressEntity> findRegionByRegion(
			PriceRegionExpressEntity regionEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionExpressDao.searchExpressAllUpRegionByCondition
	 * @Description:查询当前价格区域的所有上级价格区域  根据价格区域的所在地信息
	 *
	 * @param regionEntity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-23 上午10:49:54
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-23    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	List<PriceRegionExpressEntity> searchExpressAllUpRegionByCondition(
			PriceRegionExpressEntity regionEntity);
}