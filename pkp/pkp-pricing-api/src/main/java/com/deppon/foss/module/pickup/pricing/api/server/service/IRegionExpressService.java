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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IExpressRegionService.java
 * 
 * FILE NAME        	: IExpressRegionService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;

public interface IRegionExpressService extends IService {
	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息<br/>
	 * （分页） 方法名：searchRegionByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @param start
	 *            其实查询位置
	 * @param limit
	 *            每页几条
	 * @author 张斌
	 * @时间 2012-10-12
	 * @since JDK1.6
	 */
	public List<PriceRegionExpressEntity> searchRegionByCondition(
			PriceRegionExpressEntity regionEntity, int start, int limit);
	
	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息<br/>
	 * （分页） 方法名：searchRegionByCondition
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

	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息个数<br/>
	 * 方法名：countRegionByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @author 张斌
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	Long countRegionByCondition(PriceRegionExpressEntity regionEntity);

	/**
	 * .
	 * <p>
	 * 新增区域<br/>
	 * 方法名：countRegionByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @author 张斌
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	void addRegion(PriceRegionExpressEntity regionEntity,
			List<PriceRegioOrgnExpressEntity> addPriceRegioOrgnEntityList);

	/**
	 * .
	 * <p>
	 * 根据条件查询区域关联部门信息<br/>
	 * 方法名：searchRegionOrgByCondition
	 * </p>
	 * 
	 * @param priceRegioOrgnEntity
	 *            查询条件
	 * @author 张斌
	 * @时间 2012-10-14
	 * @since JDK1.6
	 */
	 List<PriceRegioOrgnExpressEntity> searchRegionOrgByCondition(
			 PriceRegioOrgnExpressEntity priceRegioOrgnEntity);

	/**
	 * .
	 * <p>
	 * 激活区域<br/>
	 * 方法名：activeRegion
	 * </p>
	 * 
	 * @param regionIds
	 *            要激活的区域的ID
	 * @param regionNature
	 *            区域性质
	 * @author 张斌
	 * @param date 
	 * @时间 2012-10-14
	 * @since JDK1.6
	 */
	 void activeRegion(List<String> regionIds, String regionNature, Date date);
	
	/**
	 * .
	 * <p>
	 * 删除区域<br/>
	 * 方法名：deleteRegion
	 * </p>
	 * 
	 * @param regionIds
	 *            要激活的区域的ID
	 * @param regionNature
	 *            区域性质
	 * @author 张斌
	 * @时间 2013-1-25
	 * @since JDK1.6
	 */
	 void deleteRegion(List<String> regionIds, String regionNature);

	/**
	 * .
	 * <p>
	 * 修改区域相关信息<br/>
	 * 方法名：updateRegion
	 * </p>
	 * 
	 * @param regionEntity
	 *            区域相关信息
	 * @param addPriceRegioOrgnEntityList
	 *            新增区域与部门关联关系
	 * @param updatePriceRegioOrgnEntityList
	 *            修改区域与部门关联关系
	 * @author 张斌
	 * @时间 2012-10-14
	 * @since JDK1.6
	 */
	 void updateRegion(PriceRegionExpressEntity regionEntity,
			List<PriceRegioOrgnExpressEntity> addPriceRegioOrgnEntityList,
			List<PriceRegioOrgnExpressEntity> updatePriceRegioOrgnEntityList);

	/**
	 * 
	 * <p>
	 * Description: 寻找价格始发区域ID 查询规则： 根据获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * </p>
	 * <br />
	 * </p>
	 * 
	 * @author zhangwei
	 * @version 0.1 2012-10-25
	 * @param countyCode
	 *            区县code
	 * @param cityCode
	 *            城市code
	 * @param provCode
	 *            省code
	 * @param billDate
	 *            开单日期
	 * @param  productCode      
	 *            产品code     
	 * @param regionNature
	 *            区域性质（时效或价格），根据区域性质的不同查询时效或价格的区域表
	 * @return String
	 */
	 
	 String findRegionOrgByCode(String countyCode, String cityCode,
				String provCode, Date billDate,String productCode,
			String regionNature);
	 
	 /**
		 * 
		 * <p>
		 * Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
		 * 2.查询基础数据行政区域与部门关系表获得具体省份
		 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
		 * </p>
		 * <br />
		 * </p>
		 * 
		 * @author zhangwei
		 * @version 0.1 2012-10-25
		 * @param deptNo
		 *            部门code
		 * @param billDate
		 *            开单日期
		 * @param  productCode      
		 *            产品code     
		 * @param regionNature
		 *            区域性质（时效或价格），根据区域性质的不同查询时效或价格的区域表
		 * @return String
		 */
		 String findRegionOrgByDeptNo(String deptNo, Date billDate,String productCode,
				String regionNature);

	 /**
	 * 根据部门Code寻找价格始发区域ID,只查询行政区域ID
	 * @author foss-qiaolifeng
	 * @date 2013-9-17 上午11:35:09
	 * @param deptNo
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 */
	String findOrgAdministrativeInfoByDeptNo(String orgCode, Date billDate,String productCode,
				String regionNature);
	 
	/**
	 * 
	 * <p>
	 * Description:根据行政区域Code查询区域ID<br />
	 * </p>
	 * 
	 * @author zhangwei
	 * @version 0.1 2012-10-29
	 * @param cityCode
	 * @param billDate
	 *            开单日期
	 * @param regionNature
	 *            区域性质（时效或价格）
	 * @return String
	 */
	 String findRegionIdByDistrictCode(String code, Date billDate,
			String regionNature);

	/**
	 * .
	 * <p>
	 * 根据条件查询区域信息<br/>
	 * 方法名：findRegionByCondition
	 * </p>
	 * 
	 * @param regionEntity
	 *            查询条件
	 * @param start
	 *            其实查询位置
	 * @param limit
	 *            每页几条
	 * @author 张斌
	 * @时间 2012-10-12
	 * @since JDK1.6
	 */
	List<PriceRegionExpressEntity> findRegionByCondition(PriceRegionExpressEntity regionEntity);
	
	/**
	 * @Description: 根据时效区域名称查询时效区域
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-22 下午11:16:09
	 * @param regionName
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionExpressEntity> searchRegionByName(String regionName, String regionNature);

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
	Map<String,PriceRegionExpressEntity> findRegionByNames(List<String> names,String regionNature,Date billDate);
	/**
	 * 
	 * @Description: 刷新时效区域缓存
	 * @author FOSSDP-sz
	 * @date 2013-2-22 下午1:59:04
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @version V1.0
	 */
	void refreshEffectiveRegionCache(String provinceCode, String cityCode, String countyCode);
	/**
	 * 
	 * @Description: 刷新时效区域与组织缓存
	 * @author FOSSDP-sz
	 * @date 2013-2-22 下午1:59:46
	 * @param deptNo
	 * @version V1.0
	 */
	void refreshEffectiveRegionOrgCache(String deptNo);
	/**
	 * 
	 * @Description: 刷新价格区域缓存
	 * @author FOSSDP-sz
	 * @date 2013-2-22 下午1:59:04
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @version V1.0
	 */
	void refreshPriceRegionCache(String provinceCode, String cityCode, String countyCode);
	/**
	 * 
	 * @Description: 刷新价格区域与组织缓存
	 * @author FOSSDP-sz
	 * @date 2013-2-22 下午1:59:46
	 * @param deptNo
	 * @version V1.0
	 */
	void refreshPriceRegionOrgCache(String deptNo);
	/**
	 * 
	 * @Description: 立即中止
	 * @author FOSSDP-sz
	 * @date 2013-3-21 下午2:09:39
	 * @param regionId
	 * @param regionNature
	 * @param date
	 * @version V1.0
	 */
	void immedietelyStopRegion(String regionId, String regionNature, Date endTime);
	/**
	 * 
	 * @Description: 立即激活
	 * @author FOSSDP-Administrator
	 * @date 2013-3-25 上午10:41:28
	 * @param regionId
	 * @param regionNature
	 * @param beginTime
	 * @version V1.0
	 */
	void immedietelyActiveRegion(String regionId, String regionNature,Date beginTime);
	/**
	 * 
	 * @Description: 根据区域ID获取其下所有的营业部
	 * @author FOSSDP-sz
	 * @date 2013-4-22 下午2:15:29
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<String> searchRegionOrgCodeByRegionId(String deptRegionId, String regionNature);

	List<PriceRegionExpressEntity> findRegionByRegion(
			PriceRegionExpressEntity regionEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService.searchExpressAllUpRegionByCondition
	 * @Description:查询当前区域的所有上级区域  根据区域的所在地信息
	 *
	 * @param regionEntity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-23 上午10:59:52
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-23    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	List<PriceRegionExpressEntity> searchExpressAllUpRegionByCondition(
			PriceRegionExpressEntity regionEntity);
}