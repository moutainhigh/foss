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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IBaseDataService.java
 * 
 * FILE NAME        	: IBaseDataService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;

/**
 * 
 * 运单基础资料服务
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Bobby,date:2012-10-16 上午11:13:02, </p>
 * @author foss-sunrui
 * @date 2012-10-16 上午11:13:02
 * @since
 * @version
 */
public interface IBaseDataService extends IService {
    
    /**
     * <p>查询限保物品</p> 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    List<LimitedWarrantyItemsEntity> queryAllInsurGoods();
    
    /**
     * <p>判断是否是限保物品</p> 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    LimitedWarrantyItemsEntity isInsurGoods(String goodsName);
    
    /** 
     * <p>判断是否是限保物品并且带出限保金额</p> 
     * @author foss-sunrui
     * @date 2012-10-15 下午1:00:41
     * @param goodsName
     * @return 
     * @see
     */	
    String queryLimitAmount(String goodsName);
    
    /**
     * 
     * <p>判断是否是禁运物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午3:16:47
     * @param goodsName
     * @return
     * @see
     */
    boolean isProhibitGoods(String goodsName);
    
    /**
     * 
     * <p>查询所有有效的禁运物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午3:16:47
     * @param goodsName
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryAllProhibitGoods();
    
    /**
     * 
     * <p>根据类型查询有效的禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @param goodsName
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryProhibitGoodsByType(String type);
    
    /**
     * 
     * <p>获取汽运禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryProhibitGoodsForAutomobile(String otherType);
    
    /**
     * 
     * <p>判断是否是贵重物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午3:16:47
     * @param goodsName
     * @return
     * @see
     */
    boolean isValuablesGoods(String goodsName);
    
    /**
     * 
     * <p>通过行政级别获取</p> 
     * @author foss-sunrui
     * @date 2012-10-17 上午9:29:47
     * @param degree
     * @return
     * @see
     */
    List<AdministrativeRegionsEntity> queryByDegree(String degree);
    
    /**
     * 
     * <p>通过行政区域编码获取</p> 
     * @author foss-sunrui
     * @date 2012-10-17 上午9:30:04
     * @param parentDistCode
     * @return
     * @see
     */
    List<AdministrativeRegionsEntity> queryByParentDistCode(String parentDistCode);
    
    /**
     * 
     * <p>通过营业部查询出发网点组</p> 
     * @author foss-sunrui
     * @date 2012-10-17 上午9:33:42
     * @param saleDepartmentId
     * @return
     * @see
     */
    NetGroupEntity queryDepartureNetGrp(String saleDepartmentId);
    
    /**
     * 
     * <p>通过营业部查询到达网点组</p> 
     * @author foss-sunrui
     * @date 2012-10-17 上午9:34:10
     * @param saleDepartmentId
     * @return
     * @see
     */
    NetGroupEntity queryArrivalNetGrp(String saleDepartmentId);
    
    /**
     * 
     * <p>通过出发和到达营业部获取走货路径</p> 
     * @author foss-sunrui
     * @date 2012-10-17 上午10:53:30
     * @param transType	运输性质
     * @param departureSaleDept 出发营业部
     * @param arrivalSaleDept 到达营业部
     * @return
     * @see
     */
    FreightRouteEntity queryRouteBySaleDept(String transType, String departureSaleDept, String arrivalSaleDept);
    
    /**
     * 
     * <p>通过营业部编码获取运输性质</p> 
     * @author foss-sunrui
     * @date 2012-10-25 下午2:55:38
     * @param salesDeptId
     * @return
     * @see
     */
    List<ProductEntity> queryTransType(String salesDeptId);
    
    
    /**
     * 
     * 查询到达部门拥有的产品属性
     * @author 025000-FOSS-helong
     * @date 2013-1-30 上午11:20:01
     * @param arriveDept
     * @param productLevel
     * @return
     */
    List<ProductEntity> queryByArriveDeptProduct(String arriveDept);
    
    /**
     * 
     * <p>通过系统配置代码获取系统配置</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:35:33
     * @param confCode
     * @param orgCode
     * @return
     * @see
     */
    ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode);
    
    /**
     * 
     * <p>获取打木架体积计算系统参数</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午2:46:30
     * @return
     * @see
     */
    String queryGoodsPackingVolume();

    /**
     * 
     * 根据ID查询产品类型
     * @author 102246-foss-shaohongliang
     * @date 2012-11-7 上午10:20:40
     */
    ProductEntity queryTransTypeById(String transTypeId);
    
    /**
     * <p>
     * (按登录人查询收货部门)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
    List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
			SaleDepartmentEntity entity, int start, int limit);
    
    /**
     * 根据部门编码查询部门信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-14 上午10:45:03
     */
    SaleDepartmentEntity querySaleDepartmentByCode(String code);

	/**
	 * <p>
	 * 通过code查询组织实体
	 * </p>
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-15 上午11:07:33
	 * @return
	 * @see
	 */

	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoEntityByCode(String code);
	
	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-21 下午4:29:25
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);

	/**
	 * 根据历史时间和营业部编码查询营业部信息（查询历史营业部信息）
	 * 
	 * 若时间为空，则只根据营业部编码查询营业部信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的营业部
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String code, Date billTime);

	/**
	 * 根据历史时间和组织编码查询组织信息（查询历史组织信息）
	 * 
	 * 若时间为空，则只根据组织编码查询组织信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的部门
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoEntityByCode(String code, Date billTime);

    /**
 	 * 根据历史时间和代理网点编码查询代理网点信息（查询历史代理网点信息）
 	 * 
 	 * 若时间为空，则只根据代理网点编码查询代理网点信息
 	 * 否则将根据时间，查询在creatTime和modifyTime时间段的代理网点
 	 * 不根据Active='Y'来查询
 	 * 
 	 * @author 026123-foss-lifengteng
 	 * @date 2013-4-17 下午6:02:26
 	 */
	OuterBranchEntity queryOuterBranchByCode(String code, Date billTime);

	/**
	 * 根据营业部部门编码查询相关的集中开单组列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 上午11:03:01
	 */
	List<OrgAdministrativeInfoEntity> queryBillingGroupListBySalesCode(String salesCode);

	/**
	 * 根据集中开单组部门编码查询相关的营业部列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 上午11:05:36
	 */
	List<SaleDepartmentEntity> querySalesListByBillingGroupCode(String billingGroupCode);

	/**
	 * 获得服务器时间
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 下午3:44:42
	 */
	Date gainServerTime();
	
    /**
     * 根据部门编码在线查询部门信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-14 上午10:45:03
     */
    SaleDepartmentEntity querySaleDepartmentByCodeOnline(String code);
    
    /**
     * 通过营业部编码获取运输性质
     * @author WangQianJin
     * @date 2013-7-23 上午8:27:54
     */
    List<ProductEntity> queryBySalesDept(String salesDeptCode, String productLevel);
    
    /**
     * 根据到达部门编码获取运输性质
     * @author WangQianJin
     * @date 2013-7-23 上午8:27:54
     */
    List<ProductEntity> queryByArriveDept(String salesDeptCode, String productLevel);

	/**
	 * 根据部门名称在线模糊查询部门信息，若没有查询出来则本地糊糊查询部门信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 上午10:13:23
	 */
	List<OrgAdministrativeInfoEntity> queryOrgInfoByNameOnline(String name);

	/**
	 * 根据营业部实体条件在线查询营业部信息，若在线查询不到时再查询本地数据
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 下午3:56:52
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByEntityOnline(SaleDepartmentEntity entity);

    /**
     * 根据产品编码与营业日期来筛选产品
     * @author 026123-foss-lifengteng
     * @date 2013-10-26 下午3:41:00
     */
	ProductEntity getProductByCache(String productCode, Date billDate);
	
	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	List<ProductEntity> searchByArriveDept(String code, String productLevel);
	
	/**
	 * 获取配置参数
	 * @param model
	 * @param config
	 * @param orgCode
	 * @return
	 */
	ConfigurationParamsEntity queryConfigurationParamsByEntity(String model,String config,String orgCode);

	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoEntityByUnifiedCode(String receivingToPointUnifiedCode);
	/**
	 * 查询整车价格参数波动方案
	 * @author 076234-foss-PanGuoYang
	 * @param date
	 * @param invoceType
	 * @param code
	 * @return
	 */
	CarloadPricePlanDto queryByConfCode(Date date, String invoceType,
			String code);
	
	/**
     * 根据部门编码与运输性质获取信息数量
     * @param entity
     * @author WangQianJin
     * @return
     */
    int queryCountByCodeAndProduct(SalesProductEntity entity);

    /**
     * 通过以下三个参数，判定是否进行开箱验货的功能
     * @param orgAdministrativeInfoEntity
     * @param startRegionCode
     * @param districtProvinceCode
     * @return
     */
	BillInspectionRemindEntity queryBillInspectionRemindByRegionCode(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
			String startRegionCode, String districtProvinceCode);
	
	/**
	 * 根据客户编码、时间查询客户当前时间内的客户优惠信息
	 * @param customerCode
	 * @param date
	 * @param productCode
	 * @return
	 */
	PreferentialInfoDto queryPreferentialInfo(String customerCode,
		    Date date,String productCode);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.CarloadPricePlanDao.selectCarloadPricePlanDetailByOrganizationCode
	 * @Description:通过组织编码查询当前版本的整车价格波动参数方案
	 *
	 * @param carloadPricePlanDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-15 下午3:29:29
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-15    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public List<CarloadPricePlanDto> selectCarloadPricePlanDetailByOrganizationCode(
			CarloadPricePlanDto carloadPricePlanDto);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService.queryOrgAdministrativeInfoEntityAllUpNOCache
	 * @Description:通过部门编码查询本部门和所以的上级部门信息
	 *
	 * @param code
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-17 下午6:14:46
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-17    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllUpNOCache(
			String code);

	List<BargainAppOrgEntity> queryAppOrgByBargainCrmId(BigDecimal cusBargainId,List<String> unifiedCodeList);

	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeClean(
			String unifiedCode);

	PreferentialInfoDto queryPreferentialInfoForBubbleRate(String customerCode,
			Date date, String code);

	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.common.client.service.IBaseDataService.queryExhibitionKeywordListByTargetOrgName
	 * @Description:根据收货地址匹配展馆关键字信息
	 *
	 * @param condition
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-DONGJIALING
	 * @date:2014-12-16 下午4:34:04
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-12-16    DP-FOSS-DONGJIALING      v1.0.0         create
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeywordListByTargetOrgName(
			String condition);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.common.client.service.IBaseDataService.queryExhibitionKeyword
	 * @Description:根据收货地址匹配展馆详细地址
	 *
	 * @param exhibitionKeyword
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-DONGJIALING
	 * @date:2014-12-16 下午4:34:22
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-12-16    DP-FOSS-DONGJIALING      v1.0.0         create
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeyword(
			ExhibitionKeywordEntity exhibitionKeyword);
	
	/**
	 * 根据编码编码获取标杆编码
	 * @param code
	 * @return
	 */
	String queryUnifiedCodeByCode(String code);
	/**
	 * 
	 */

	List<CusLtDiscountItemDto> queryCusLtdiscountafterByCondition(
			CusLtdiscountafterEntity entity);
	List<ProductEntity> getAllProductInfoByLevels1(String transTypeCode);
	
	/**
	 *  根据部门编码在线查询部门信息 
	 *  优先读取缓存 如果没有则读数据库
	 * @param code
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-2-24
	 */
	SaleDepartmentEntity querySimpleSaleDepartmentByCodeCache(String code);

	/**
	 * <p>根据编码查询组织，不关联组织名称等只查询基本信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午9:03:22
	 * @param code
	 * @return
	 * @see
	 */
	SaleDepartmentEntity querySaleDepartmentNoAttachOnline(String code);
	
}