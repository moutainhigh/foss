/**
*  
*  
* SR1	
*  1.	区域名称： 区域名称不能出现重复、
*  
*  校验时考虑左右空格情况、
*  
*  区域所含信息是比较广义的、
*  
*  它可以将所在地信息化为一个区域也可以讲对应所在地的部门网点作为一个区域。
*  
*  区域名称总长度不超过100。
*  
* 2.	区域编号：
* 
*  区域的编号不能出现重复信息、
*  
*  区域编号由字母加数字组成不能超过50个长度。
*  
* 3.	 目前改为按照行政区域，
* 
* 或者按照网点信息进行规划成一个逻辑区域
* 
*SR2	
*
*是否激活：
*
*代表是否立即启用该区域，
*
*规则只有未激活的区域可以做激活。
*
*激活区域时，
*
*需要选择生效时间。
*
* SR3	
* 
* 一个区域的类型，
* 
* 只能在行政区域和组织之间选择。
* 
* 不能两者都是，
* 
* 并且修改时不可以修改类型。
* 
* 但选择行政区域类型时，
* 
* 可以在逻辑区域与国家行政区域之间建立关联。
* 
* 但选择组织类型时，
* 
* 则下部部门信息可以添加：
* 
* 德邦内部组织，
* 
* 偏线代理，
* 
* 空运代理。
* 
*SR4	
*
*点击提交时存入区域类型字段其目的是在列表查询时
*
*显示以部门作为区域定义的需要给出超链接查询部门明细信息，
*
*区域类型是根据部门信息是否存在而决定的， 
*
*部门信息无它是以所在地信息作为一个区域，
*
*部门信息有则是遵守所在地对应的部门然后
*
*经过管理员选择筛选后所成立的逻辑区域信息。
*
*对于前者区域类型字段设置为所在地类型，
*
*对于后者区域类型设置为部门。
*
* SR5	
*
*同一个所在地不能重复建立区域信息
*
*SR6
*	
*新增区域原则： 
*
*同一个网点组织，\
*
*在同一时间段，
*
*不能所属两个逻辑区域信息，
*
*以免造成前台开单时读取区域信息出现混淆。
*
*前台开单按照异常优先级来决定在本用例维护中所定位的区域信息，
*
*获取区域信息优先级别如下：
*
*依次顺序为网点组织、区县、城市、省份
*
*根据开单时所提供的部门名称首先寻找本用例中所维护的部门区域类型是否存在不存在再依次
*
*往上找区县、市、省份直到找到为止， 
*	
*SR7	
*
*部门信息弹出窗口， 
*
*主要为网点筛选器，
*
*提供按照名称，
*
*编码、拼音，以及省，市，区，组织属性，
*
*类型等不同维度条件来筛选合适的网点信息。 
*
*组织类型包括、空运代理、偏线代理、内部组织。
*
*将这些网点组装起来划成一个逻辑区域来满足各种不同的特殊场景。
*
*（例如将上海某个公司内部网点与杭州公司某个网点划成一个逻辑区域）
*
*SR8	
*
*网点信息不能在同一个时间段存在多个逻辑区域信息中，
*
*例如(A逻辑区域包含上海浦东XX路营业部从有效起始时间2012-05-31 到 有效截止时间 2999-01-01，
*
* 这段时间内该营业部不能同时出现在其他逻辑区域中)，偏线、空运、代理同理
* 
*SR9	
*
*立即中止功能：
*
* 修改时部门查询列表中，
* 
* 点击“立即中止”超链接弹出立即中提示框，
* 
* 填写中止时间 ,选择“确认”系统将选中的方案截止时间调整为当前设置的中止的时间，
* 
* 出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” ，
* 
* 即时间可以在今天和今天以后任意调整，但是不能调整为昨天的数据。
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
* 维护始发区域和目的区域
* 
*SR1	
*
*弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成： 
*
*出发信息包括： 
*
*a)	生效日期: 
*
*设定生效日期。
*
*b)	始发区域: 
*
*区域信息来自产品基础数据维护下的区域来填充该下拉表请先熟悉 DP-FOSS-接送货系统用例-产品价格-城市列表-录入区域SUC-587-V0.1用例。          
*
* C) 未被激活的版本信息部能被正常使用。D) 方案描述：  对建立新方案的一些描述信息。
* 
*目的地信息：  由于可以设置至少一个到N个元素信息.故使用明细列表的方式来暂存数据一起提交。
*
*SR2	
*
*目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
*
*SR3	
*
*添加目的地明细信息时可一选择基础产品设置运营时效和取货时间，是否长短途， 在提交目的地设置相关信息时点击 “提交”按钮需要校验，目的地、基础产品、长短途在后台不能有相同的数据已存在， 提示如“广州-精准卡航-短途时效信息已经存在不能重复添加!”
*
*SR4
*
*图3中产品下拉列表中数据需要过滤规则：  只能显示该始发区域与目的地区域在 区域与产品基础数据中所维护的产品信息。
*
*SR5	
*
*新增时，所选择的区域信息（始发区域/目的地区域）都需要过滤，
*
*只能取时效相关的区域信息作为数据源。
*
*如：图2中界面元素下拉列标的始发区域，
*
*图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为时效的区域,”。
*	
*SR6	
*
*新增时效方案信息时，
*
* 必须先保存出发地信息，
* 
* 然后目的地信息中的“新增”、“删除”、“修改”、
* 
* 功能才可以被使用。 否则为禁用状态。
 *
*SR7	
*
*所有新建方案都以草稿数据存储。
*
*针对草稿数据，
*
*我们可以随时进行任意操作。
*
*一旦在查询列表中做过激活的方案，
*
*就不能再做删除与修改了。
*
*只能做方案的复制对该方案进行延续不同时间段内体现不同时效信息。
*
*SR8	
*
*在草稿状态的方案被建立之后做激活时，
*
*会去检查具体草稿方案信息下的目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
*
*重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品（三级），
*
*如果存在则提示“该方案下xxx目的地xxx产品xxx已经在另一个xxx方案下存在，
*
*请确认是否以该方案为准,请将xxx方案中止。”
*
*SR9	
*
*立即中止功能： 
*
*在时效查询列表中，
*
*只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
*
*点击中止，
*
*选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
*
*出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 。
*
 *
*SR10	
*
*立即激活功能： 
*
*在时效查询列表中，
*
*只能选择一条未激活的数据点击列表中“立即激活”按钮弹出
*
 *选择生效时间，
 *
 *点击激活按钮，
 
 *选择“是”系统将选中的方案生效时间调整为当前设置的生效时间，
* 
* 出现小于当前营业日期系统提示“立即激活操作的生效时间必须大于等于营业日期!” 。
*
*
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/RegionService.java
 * 
 * FILE NAME        	: RegionService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.*;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillRegionImpPushService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPriceBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPartnerPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.WaybillRegionInfoDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.RegionException;
import com.deppon.foss.module.pickup.pricing.server.cache.DistrictRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.EffectiveRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.EffectiveRegionOrgCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionBigCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionBigOrgCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionEcGoodsCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionEcGoodsOrgCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionOrgCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 区域新增修改查询service
 * 
 * @author 078838 DP-Foss-zhangbin
 * 
 * @date 2012-11-12 下午4:33:08
 * 
 */
@Transactional
public class RegionService implements IRegionService {
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(RegionService.class);
	/**
	 * 组织机构接口
	 */
	@Inject
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 网点SERVICe
	 */
	@Inject
	IVehicleAgencyDeptService vehicleAgencyDeptService;
	/**
	 * 行政区域SERVICE 
	 */
	@Inject
	private IAdministrativeRegionsService administrativeRegionsService;
	/**
	 * 职员service
	 */
	@Inject
	private IEmployeeService employeeService;
	/**
	 * 区域 DAO
	 */
	@Inject
	private IRegionDao regionDao;
	
	/**
	 * 大票区域 DAO
	 */
	@Inject
	private IRegionBigGoodsDao regionBigGoodsDao;

	/**
	 * 精准包裹区域 DAO
	 */
	@Inject
	private IRegionEcGoodsDao regionEcGoodsDao;
	/**
	 * 价格区域 缓存处理
	 */
	@Inject
	private PriceRegionCacheDeal priceRegionCacheDeal;
	
	/**
	 * 大票价格区域 缓存处理
	 */
	@Inject
	private PriceRegionBigCacheDeal priceRegionBigCacheDeal;

	/**
	 * 精准包裹价格区域 缓存处理
	 */
	@Inject
	private PriceRegionEcGoodsCacheDeal priceRegionEcCacheDeal;
	/**
	 * 时效区域 缓存处理
	 */
	@Inject
	private EffectiveRegionCacheDeal effectiveRegionCacheDeal;
	/**
	 * 价格区域与部门 缓存处理
	 */
	@Inject
	private PriceRegionOrgCacheDeal priceRegionOrgCacheDeal;
	
	/**
	 * 大票价格区域与部门 缓存处理
	 */
	@Inject
	private PriceRegionBigOrgCacheDeal priceRegionBigOrgCacheDeal;

	/**
	 * 精准包裹价格区域与部门 缓存处理
	 */
	@Inject
	private PriceRegionEcGoodsOrgCacheDeal priceRegionEcOrgCacheDeal;
	/**
	 * 时效区域与部门 缓存处理
	 */
	@Inject
	private EffectiveRegionOrgCacheDeal effectiveRegionOrgCacheDeal;
	/**
	 * 行政区域与区域关系缓存处理
	 */
	@Inject
	private DistrictRegionCacheDeal districtRegionCacheDeal;
	/**
	 * 
	 */
	@Inject
	private IDistrictRegionService districtRegionService;
	
	/**
	 * FOSS推送业务逻辑处理接口
	 */
	@Inject
	private IWaybillRegionImpPushService waybillRegionImpPushService;
	
	private IRegionPriceDao regionPriceDao;
	
	public void setWaybillRegionImpPushService(
			IWaybillRegionImpPushService waybillRegionImpPushService) {
		this.waybillRegionImpPushService = waybillRegionImpPushService;
	}


	public void setRegionEcGoodsDao(IRegionEcGoodsDao regionEcGoodsDao) {
		this.regionEcGoodsDao = regionEcGoodsDao;
	}

	public void setPriceRegionEcCacheDeal(PriceRegionEcGoodsCacheDeal priceRegionEcCacheDeal) {
		this.priceRegionEcCacheDeal = priceRegionEcCacheDeal;
	}

	public void setPriceRegionEcOrgCacheDeal(PriceRegionEcGoodsOrgCacheDeal priceRegionEcOrgCacheDeal) {
		this.priceRegionEcOrgCacheDeal = priceRegionEcOrgCacheDeal;
	}

	/**
	 * 
	 * 根据条件查询区域 
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:19:48
	 * 
	 * @param regionEntity
	 * 
	 * @param start
	 * 
	 * @param limit
	 * 
	 * @return 
	 */
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
	      //设置条件
		if (PricingConstants.ALL.equals(regionEntity.getActive())) {
			regionEntity.setActive(null);
		}
		List<PriceRegionEntity> priceRegionEntityList = regionDao
				.searchRegionByCondition(regionEntity, start, limit);
		if(CollectionUtils.isNotEmpty(priceRegionEntityList)){
			for (PriceRegionEntity model : priceRegionEntityList) {
				model.setRegionNature(regionEntity.getRegionNature());
				if(StringUtil.isNotBlank(model.getModifyUser())){
					//查询修改人信息
					EmployeeEntity employeeEntity= employeeService.queryEmployeeByEmpCode(model.getModifyUser());
					if(employeeEntity!=null){
						//设置修改人姓名
						model.setModifyUserName(employeeEntity.getEmpName());
					}
				}
			}
		}
		//返回结果
		return priceRegionEntityList;
	}
	/** 
	 * 
	 * 查询时效
	 *  
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:22:06
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 */
	@Override
	public List<PriceRegionEntity> findRegionByCondition(
			PriceRegionEntity regionEntity) {
	    //设置条件
		if (PricingConstants.ALL.equals(regionEntity.getActive())) {
			regionEntity.setActive(null);
		}
		List<PriceRegionEntity> priceRegionEntityList = regionDao
				.findRegionByCondition(regionEntity);
		for (PriceRegionEntity model : priceRegionEntityList) {
			model.setRegionNature(regionEntity.getRegionNature());
		}
		//返回结果
		return priceRegionEntityList;
	}
	/** 
	 * 
	 * 计算条数 
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:23:51
	 * 
	 * @param regionEntity
	 * 
	 * @return  
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		if (PricingConstants.ALL.equals(regionEntity.getActive())) {
			regionEntity.setActive(null);
		}
		return regionDao.countRegionByCondition(regionEntity);
	}
	
	/** 
	 * 
	 * 添加区域
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:24:14
	 * 
	 * @param regionEntity
	 * 
	 * @param addPriceRegioOrgnEntityList 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#addRegion(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity, java.util.List)
	 */
	@Override
	public void addRegion(PriceRegionEntity regionEntity,
			List<PriceRegioOrgnEntity> addPriceRegioOrgnEntityList) {
		WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
		List<PriceRegioOrgnEntity> priceRegioOrgnEntities = new ArrayList<PriceRegioOrgnEntity>();
		
		String regionId = UUIDUtils.getUUID();
		regionEntity.setId(regionId);// 设置ID
		//转换时间(时间得失明天开始)
//		Date begintime=DateUtils.convert(DateUtils.convert(new Date(new Date().getTime()+PricingConstants.ONE_DAY_MILLISECOND),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
		Date begintime = new Date();
		String regionNature = regionEntity.getRegionNature();
		Long versionNo = new Date().getTime();
		regionEntity.setVersionNo(versionNo);
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		String userCode = user.getEmployee().getEmpCode();
		String orgCode = user.getEmployee().getDepartment().getCode();
		regionEntity.setCreateOrgCode(orgCode);
		regionEntity.setCreateUser(userCode);
		regionEntity.setModifyOrgCode(orgCode);
		regionEntity.setModifyUser(userCode);
		regionEntity.setBeginTime(begintime);
		regionEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		// 判断名称是否重复
		boolean isTheSameRegionName = this.isTheSameRegionName(regionEntity,
				false);
		if (isTheSameRegionName) {
			throw new RegionException(RegionException.REGION_NAME_SAME,
					RegionException.REGION_NAME_SAME);
		}else{
			//did nothing
		}
		PriceRegionEntity checkEntity = new PriceRegionEntity();
		checkEntity.setRegionCode(regionEntity.getRegionCode());
		checkEntity.setRegionNature(regionEntity.getRegionNature());
		// 判断CODE是否重复
		boolean isTheSameRegionCode = this.isTheSameRegionCode(checkEntity);
		if (isTheSameRegionCode) {
			throw new RegionException(RegionException.REGION_CODE_SAME,
					RegionException.REGION_CODE_SAME);
		}else{
			//did nothing
		}
		if(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE.equalsIgnoreCase(regionEntity.getRegionType())){
			//校验行政区域
			checkRegionOrganizationType(regionEntity);
		}
		//添加数据
		regionDao.addRegion(regionEntity);
		//处理该区域包含的组织
		if (addPriceRegioOrgnEntityList != null) {
			for (PriceRegioOrgnEntity model : addPriceRegioOrgnEntityList) {
			    //准备数据
				String regionOrgId = UUIDUtils.getUUID();
				model.setId(regionOrgId);// 设置ID
				Long versionNoOrg = new Date().getTime();
				model.setVersionNo(versionNoOrg);
				model.setCreateOrgCode(orgCode);
				model.setCreateUser(userCode);
				model.setModifyOrgCode(orgCode);
				model.setModifyUser(userCode);
				model.setPriceRegionCode(regionEntity.getRegionCode());
				model.setPriceRegionId(regionId);
				model.setRegionNature(regionEntity.getRegionNature());
				//跟父表的状态一样
				model.setActive(regionEntity.getActive());
				model.setBeginTime(begintime);
				model.setEndTime(new Date(PricingConstants.ENDTIME));
				priceRegioOrgnEntities.add(model);
				//判断改组织在区域组织表中是否存在
				PriceRegioOrgnEntity priceRegioOrgnEntity = new PriceRegioOrgnEntity();
				priceRegioOrgnEntity.setIncludeOrgCode(model.getIncludeOrgCode());	
				priceRegioOrgnEntity.setRegionNature(regionNature);
				List<PriceRegioOrgnEntity> priceRegioOrgnEntityList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
				if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)){
					 for(int loop=0;loop<priceRegioOrgnEntityList.size();loop++){
						 PriceRegioOrgnEntity object = priceRegioOrgnEntityList.get(loop);
						 if(begintime.getTime() <= object.getEndTime().getTime()){
							 PriceRegionEntity priceRegionEntity = regionDao.searchRegionByID(object.getPriceRegionId(),regionNature);
							 String message = "在"+priceRegionEntity.getRegionName()+"区域下已经存在部门"+object.getIncludeOrgCode();
							 throw new RegionException(message,message);
						 }		 
					 }
					//没有抛异常则新增
					 regionDao.addRegionOrg(model);
					 //zoushengli
				}else{
					 regionDao.addRegionOrg(model);
					 //zoushengli
				}
			}
		}
		/**
		 * 行政区域与区域关系
		 */
		if(StringUtils.equals(FossConstants.ACTIVE, regionEntity.getActive())) {
			if(StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, regionEntity.getRegionType())){
				if(StringUtils.isNotEmpty(regionEntity.getCountyCode())) {
					districtRegionService.addDistrictRegion(regionEntity.getCountyCode());
					districtRegionCacheDeal.getDistrictRegionCache().invalid(regionEntity.getCountyCode());
				} 
				if(StringUtils.isNotEmpty(regionEntity.getCityCode())) {
					districtRegionService.addDistrictRegion(regionEntity.getCityCode());
					districtRegionCacheDeal.getDistrictRegionCache().invalid(regionEntity.getCityCode());
				}
			} else if(PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(regionEntity.getRegionType())) {
				if(CollectionUtils.isNotEmpty(addPriceRegioOrgnEntityList)) {
					List<String> cityList = new ArrayList<String>();
					List<String> countyList = new ArrayList<String>();
					for (PriceRegioOrgnEntity priceRegioOrgnEntity : addPriceRegioOrgnEntityList) {
						String deptCode = priceRegioOrgnEntity.getIncludeOrgCode();
						OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode, new Date());
						if (orginfo != null) {
							String countyCode = orginfo.getCountyCode();
							String cityCode = orginfo.getCityCode();
							if (StringUtil.isNotEmpty(countyCode) && !cityList.contains(countyCode)) {
								countyList.add(countyCode);
							}
							if (StringUtil.isNotEmpty(cityCode) && !cityList.contains(cityCode)) {
								cityList.add(cityCode);
							}
						}
					}
					if(CollectionUtils.isNotEmpty(countyList)) {
						for (String countyCode : countyList) {
							districtRegionService.addDistrictRegion(countyCode);
							districtRegionCacheDeal.getDistrictRegionCache().invalid(countyCode);
						}
					}
					if(CollectionUtils.isNotEmpty(cityList)) {
						for (String cityCode : cityList) {
							districtRegionService.addDistrictRegion(cityCode);
							districtRegionCacheDeal.getDistrictRegionCache().invalid(cityCode);
						}
					}
				}
			}
		}
		
		//FOSS推送价格出发区域到DTD
		if(StringUtils.isNotBlank(regionNature) && (PricingConstants.PRICING_REGION).equals(regionNature)){
		waybillRegionInfoDto.setPriceRegioOrgnEntityList(priceRegioOrgnEntities);
		waybillRegionInfoDto.setJudgmentOperation("add");
		waybillRegionInfoDto.setAddPriceRegioOrgnEntityList(addPriceRegioOrgnEntityList);
		waybillRegionInfoDto.setRegionEntity(regionEntity);
		waybillRegionImpPushService.pushWaybillHomeInfo(waybillRegionInfoDto);
		}
	}
	/** 
	 * 
	 * 检测行政组织区域是否重复
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2013-1-25 下午2:26:03
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 */
	private void checkRegionOrganizationType(PriceRegionEntity regionEntity){
		String administrativeRegionCodes = regionEntity.getNationCode()+regionEntity.getProCode()+regionEntity.getCityCode()+regionEntity.getCountyCode();
		String regionNature = regionEntity.getRegionNature();
		String id = regionEntity.getId();
		List<PriceRegionEntity> priceRegionEntityList=regionDao.checkRegionOrganizationType(id,administrativeRegionCodes, regionNature);
		if(CollectionUtils.isNotEmpty(priceRegionEntityList)){
			 throw new RegionException("该行政组织下已经存在区域！",null);
		}
	}
	/** 
	 * 
	 * 根据条件查询
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:26:03
	 * 
	 * @param priceRegioOrgnEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#searchRegionOrgByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity)
	 */
	@Override
	public List<PriceRegioOrgnEntity> searchRegionOrgByCondition(PriceRegioOrgnEntity priceRegioOrgnEntity) {
		List<PriceRegioOrgnEntity> priceRegioOrgnEntityList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
		List<PriceRegioOrgnEntity> resultlist = new ArrayList<PriceRegioOrgnEntity>();
		for (PriceRegioOrgnEntity model : priceRegioOrgnEntityList) {
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(model.getIncludeOrgCode());
			if (orgAdministrativeInfo != null && StringUtils.equalsIgnoreCase(orgAdministrativeInfo.getActive(), FossConstants.ACTIVE)) {
				model.setIncludeOrgName(orgAdministrativeInfo.getName());
				model.setCityCode(orgAdministrativeInfo.getCityCode());
				model.setCityName(orgAdministrativeInfo.getCityName());
				model.setNationCode(orgAdministrativeInfo.getCountryRegion());
				model.setNationName(orgAdministrativeInfo.getCountryRegionName());
				model.setProCode(orgAdministrativeInfo.getProvCode());
				model.setProName(orgAdministrativeInfo.getProvName());
				model.setCountyCode(orgAdministrativeInfo.getCountyCode());
				model.setCountyName(orgAdministrativeInfo.getCountyName());
				resultlist.add(model);
			} else {
				OuterBranchParamsDto dto = new OuterBranchParamsDto();
				dto.setAgentDeptCode(model.getIncludeOrgCode());
				List<OuterBranchEntity> entity = vehicleAgencyDeptService.queryOuterBranchs(dto);
				if (CollectionUtils.isNotEmpty(entity)) {
					OuterBranchEntity object = entity.get(0);
					if (object != null && StringUtils.equalsIgnoreCase(object.getActive(), FossConstants.ACTIVE)) {
						model.setIncludeOrgName(object.getAgentDeptName());
						model.setCityCode(object.getCityCode());
						model.setCityName(object.getCityName());
						model.setNationCode(object.getCountryRegion());
						model.setNationName(object.getCountryRegionName() == null ? "中国" : object.getCountryRegionName());
						model.setProCode(object.getProvCode());
						model.setProName(object.getProvName());
						model.setCountyCode(object.getCountyCode());
						model.setCountyName(object.getCountyName());
						resultlist.add(model);
					}

				}
			}
		}
		return resultlist;
	}
	/** 
	 * 
	 * 激活
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:26:20
	 * 
	 * @param regionIds
	 * 
	 * @param regionNature 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#activeRegion(java.util.List, java.lang.String)
	 */
	@Override
	public void activeRegion(List<String> regionIds, String regionNature,Date beginTime) {
		for(String id:regionIds){
			PriceRegionEntity model = regionDao.searchRegionByID(id, regionNature);
			// 获取当前登录用户
			UserEntity user = (UserEntity) UserContext.getCurrentUser();
			if(user==null){
				throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
			}
			String userCode = user.getEmployee().getEmpCode();
			String orgCode = user.getEmployee().getDepartment().getCode();
			Long versionNoOrg = new Date().getTime();
			model.setBeginTime(beginTime);
			model.setVersionNo(versionNoOrg);
			model.setModifyOrgCode(orgCode);
			model.setModifyUser(userCode);
			model.setActive(FossConstants.ACTIVE);
			model.setRegionNature(regionNature);
			regionDao.updateRegion(model);
			//zoushengli
			PriceRegioOrgnEntity priceRegioOrgnEntity = new PriceRegioOrgnEntity();
			priceRegioOrgnEntity.setPriceRegionId(model.getId());
			priceRegioOrgnEntity.setRegionNature(regionNature);
			List<PriceRegioOrgnEntity>  priceRegioOrgnEntityList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
			if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)){
				for(PriceRegioOrgnEntity priceRegioOrgnModel:priceRegioOrgnEntityList){
					priceRegioOrgnModel.setVersionNo(versionNoOrg);
					//修改状态
					priceRegioOrgnModel.setActive(FossConstants.ACTIVE);
					priceRegioOrgnModel.setRegionNature(regionNature);
					priceRegioOrgnModel.setBeginTime(beginTime);
					priceRegioOrgnModel.setModifyOrgCode(orgCode);
					priceRegioOrgnModel.setModifyUser(userCode);
					regionDao.updateRegionOrg(priceRegioOrgnModel);
				}
			}
		}
	}
	/**
	 * .
	 * <p>
	 * 删除区域<br/>
	 * 方法名：deleteRegion
	 * </p>
	 * 
	 * @param regionIds
	 *            要激活的区域的ID
	 *            
	 * @param regionNature
	 *            区域性质
	 *            
	 * @author 张斌
	 * 
	 * @时间 2013-1-25
	 * 
	 * @since JDK1.6
	 */
	@Override
	public void deleteRegion(List<String> regionIds, String regionNature) {
		regionDao.deleteRegion(regionIds, regionNature);
		regionDao.deleteRegionOrg(regionIds, regionNature);
		if(StringUtils.isNotBlank(regionNature) && (PricingConstants.PRICING_REGION).equals(regionNature)){
			WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
			waybillRegionInfoDto.setRegionIds(regionIds);
			waybillRegionInfoDto.setRegionNature(regionNature);
			waybillRegionInfoDto.setJudgmentOperation("delete");
			//FOSS推送价格出发区域到PTP
			waybillRegionImpPushService.pushWaybillHomeInfo(waybillRegionInfoDto);
		}
		
	}
	/** 
	 * 
	 * 更新
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:26:29
	 * 
	 * @param regionEntity
	 * 
	 * @param addPriceRegioOrgnEntityList
	 * 
	 * @param updatePriceRegioOrgnEntityList 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#updateRegion(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity, java.util.List, java.util.List)
	 */
	@Override
	public void updateRegion(PriceRegionEntity regionEntity,
			List<PriceRegioOrgnEntity> addPriceRegioOrgnEntityList,
			List<PriceRegioOrgnEntity> updatePriceRegioOrgnEntityList) {
		List<PriceRegioOrgnEntity> priceRegioOrgnEntityLists = new ArrayList<PriceRegioOrgnEntity>();
		List<PriceRegioOrgnEntity> priceRegioOrgnEntityListies = new ArrayList<PriceRegioOrgnEntity>();
		
		Long versionNo = new Date().getTime();
		regionEntity.setVersionNo(versionNo);
		String regionNature = regionEntity.getRegionNature();
		Date begintime = new Date();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		String userCode = user.getEmployee().getEmpCode();
		String orgCode = user.getEmployee().getDepartment().getCode();
		regionEntity.setModifyOrgCode(orgCode);
		regionEntity.setModifyUser(userCode);
		// 判断名称是否重复
		boolean isTheSameRegionName = this.isTheSameRegionName(regionEntity,true);
		if (isTheSameRegionName) {
			throw new RegionException(RegionException.REGION_NAME_SAME,RegionException.REGION_NAME_SAME);
		}
		if(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE.equalsIgnoreCase(regionEntity.getRegionType())){
			//校验行政区域
			checkRegionOrganizationType(regionEntity);
		}
		//更新数据
		regionDao.updateRegion(regionEntity);

		//处理包含组织
		if (addPriceRegioOrgnEntityList != null) {
			for (PriceRegioOrgnEntity model : addPriceRegioOrgnEntityList) {
				String regionOrgId = UUIDUtils.getUUID();
				// 设置ID
				model.setId(regionOrgId);
				Long versionNoOrg = new Date().getTime();
				model.setVersionNo(versionNoOrg);
				model.setCreateOrgCode(orgCode);
				model.setCreateUser(userCode);
				model.setModifyOrgCode(orgCode);
				model.setModifyUser(userCode);
				model.setPriceRegionCode(regionEntity.getRegionCode());
				model.setRegionNature(regionEntity.getRegionNature());
				model.setActive(regionEntity.getActive());
				model.setBeginTime(begintime);
				model.setEndTime(new Date(PricingConstants.ENDTIME));
				priceRegioOrgnEntityListies.add(model);
				//判断改组织在区域组织表中是否存在
				PriceRegioOrgnEntity priceRegioOrgnEntity = new PriceRegioOrgnEntity();
				priceRegioOrgnEntity.setIncludeOrgCode(model.getIncludeOrgCode());	
				priceRegioOrgnEntity.setRegionNature(regionNature);
				List<PriceRegioOrgnEntity>  priceRegioOrgnEntityList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
				if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)){
					 for(int loop=0;loop<priceRegioOrgnEntityList.size();loop++){
						 PriceRegioOrgnEntity object = priceRegioOrgnEntityList.get(loop);
						 if(begintime.getTime() <= object.getEndTime().getTime()){
							 PriceRegionEntity priceRegionEntity = regionDao.searchRegionByID(object.getPriceRegionId(), regionEntity.getRegionNature());
							//在哪个区域下已经存在该部门
							 String message = "在"+priceRegionEntity.getRegionName()+"区域下已经存在部门"+object.getIncludeOrgCode();
							 throw new RegionException(message,message);
						 }		 
					 }
					//没有抛异常则新
					 regionDao.addRegionOrg(model);
				}
				else{
					regionDao.addRegionOrg(model);
				}
				
			}
		}
		//处理其他数据
		if (updatePriceRegioOrgnEntityList != null) {
			for (PriceRegioOrgnEntity model : updatePriceRegioOrgnEntityList) {
				Long versionNoOrg = new Date().getTime();
				model.setVersionNo(versionNoOrg);
				model.setRegionNature(regionEntity.getRegionNature());
				model.setModifyOrgCode(orgCode);
				model.setModifyUser(userCode);
				priceRegioOrgnEntityLists.add(model);
				regionDao.updateRegionOrg(model);
			}
		}
		//FOSS推送价格出发区域到DTD
		if(StringUtils.isNotBlank(regionNature) && (PricingConstants.PRICING_REGION).equals(regionNature)){
			WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
			waybillRegionInfoDto.setRegionEntity(regionEntity);
			waybillRegionInfoDto.setJudgmentOperation("update");
			waybillRegionInfoDto.setPriceRegioOrgnEntityLists(priceRegioOrgnEntityLists);
			waybillRegionInfoDto.setPriceRegioOrgnEntityList(priceRegioOrgnEntityListies);
			waybillRegionImpPushService.pushWaybillHomeInfo(waybillRegionInfoDto);
		}
	}
	/**
	 * .
	 * <p>
	 * 检测区域名称<br/>
	 * 方法名：isTheSameRegionName
	 * </p>
	 * 
	 * @param priceRegionEntity
	 *            查询条件
	 *            
	 @param isUpdate
	 *            是否是修改
	 *            
	 * @return Boolean
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-11-23
	 * 
	 * @since JDK1.6
	 */
	public boolean isTheSameRegionName(PriceRegionEntity priceRegionEntity, boolean isUpdate) {
		boolean flag = false;
		List<PriceRegionEntity> priceRegionEntityList = regionDao.searchRegionByName(priceRegionEntity.getRegionName(), priceRegionEntity.getRegionNature());
		if (priceRegionEntityList != null && priceRegionEntityList.size() > 1) {
			flag = true;
		} else if (priceRegionEntityList != null && priceRegionEntityList.size() == 1) {
			// 如果修改还得判断是否ID相同
			if (isUpdate) {
				if (StringUtils.equals(priceRegionEntityList.get(0).getId(), priceRegionEntity.getId())) {
					flag = false;
				} else {
					flag = true;
				}
				// 如果是新增，直接表示有重复
			} else {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
	/**
	 * .
	 * <p>
	 * 检测区域CODE<br/>
	 * 方法名：isTheSameRegionCode
	 * </p>
	 * 
	 * @param priceRegionEntity
	 *            查询条件
	 *            
	 * @return Boolean
	 * 
	 * @author 张斌
	 * 
	 * @时间 2013-1-9
	 * 
	 * @since JDK1.6
	 */
	public boolean isTheSameRegionCode(PriceRegionEntity priceRegionEntity) {
		List<PriceRegionEntity> priceRegionEntityList = regionDao
				.searchRegionByCondition(priceRegionEntity,NumberConstants.ZERO,Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(priceRegionEntityList)) {
			return false;
		} 
		return true;
	}
	/**
	 * 
	 * <p>
	 * (根据始发部门寻找对应的时效区域 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * </p>
	 * 
	 * @author 岳洪杰
	 * 
	 * @date 2012-10-12 下午7:27:25
	 * 
	 * @param deptNo
	 *            组织机构code
	 *            
	 * @param billDate
	 *            开单日期
	 *            
	 * @param productCode
	 *            产品code
	 *            
	 * @param regionNature
	 *            区域性质
	 * @return
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveRegionOrgService#findRegionOrgByDeptNo(java.lang.String)
	 */
	@Override
	public String findRegionOrgByDeptNo(String orgCode, Date billDate, String productCode, String regionNature) {
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		/**
		 * 1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		 */
		PriceRegioOrgnEntity priceRegioOrgnEntity = null;
		
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				if (StringUtils.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
					priceRegioOrgnEntity = effectiveRegionOrgCacheDeal.getPriceRegionOrgByCache(orgCode, billDate);
				} else if (StringUtils.equals(PricingConstants.PRICING_REGION, regionNature)) {
					priceRegioOrgnEntity = priceRegionOrgCacheDeal.getPriceRegionOrgByCache(orgCode, billDate);
				}
			} catch (Exception e) {
				log.info("无法获取区域与部门关系缓存", e);
			}
		}
		if (priceRegioOrgnEntity != null) {
			return priceRegioOrgnEntity.getPriceRegionId();
		} else {
			priceRegioOrgnEntity = new PriceRegioOrgnEntity();
			priceRegioOrgnEntity.setIncludeOrgCode(orgCode);
			priceRegioOrgnEntity.setRegionNature(regionNature);
			priceRegioOrgnEntity.setActive(FossConstants.ACTIVE);
			priceRegioOrgnEntity.setBillDate(billDate);
			List<PriceRegioOrgnEntity> resultList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegioOrgnEntity object = resultList.get(0);
				return object.getPriceRegionId();
			}
		}
		/**
		 * 2 ,没找到，按组织所在行政区域匹配
		 */
		String countyCode = null;
		String cityCode = null;
		String provCode = null;
		//自有网点
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgInfoByCodeAndTimeNoAttach(orgCode, billDate);
		if (orginfo != null) {
			countyCode = orginfo.getCountyCode();
			cityCode = orginfo.getCityCode();
			provCode = orginfo.getProvCode();
			if (StringUtil.isEmpty(provCode)) {
				return null;
			}
		} else {
			        //外发网点
					OuterBranchParamsDto dto = new OuterBranchParamsDto();
					dto.setDate(billDate);
					dto.setAgentDeptCode(orgCode);
					List<OuterBranchEntity> entity = vehicleAgencyDeptService.queryOuterBranchsSimpleInfo(dto);
					if (CollectionUtils.isNotEmpty(entity)) {
						OuterBranchEntity obkect = entity.get(0);
						countyCode = obkect.getCountyCode();
						cityCode = obkect.getCityCode();
						provCode = obkect.getProvCode();
						if (StringUtil.isEmpty(provCode)) {
							return null;
						}
					} else {
						return null;
					}
		}
		// 根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";

		if (StringUtils.isEmpty(cityCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + cityCode;
		}
		if (StringUtils.isEmpty(countyCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + countyCode;
		}
		log.info("key str>>" + key);
		List<PriceRegionEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				if (StringUtils.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
					regionlist = effectiveRegionCacheDeal.getPriceRegionByCache(key, billDate);
				} else if (StringUtils.equals(PricingConstants.PRICING_REGION, regionNature)) {
					regionlist = priceRegionCacheDeal.getPriceRegionByCache(key, billDate);
				}
			} catch (Exception e) {
				log.info("无法获取区域缓存", e);
			}
		}
		if (CollectionUtils.isEmpty(regionlist)) {
			PriceRegionEntity regionEntity = new PriceRegionEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setRegionNature(regionNature);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionlist = regionDao.searchRegionByDistrictNew(regionEntity);
		}
		// 过滤最符合条件的数据
		PriceRegionEntity entity = filterBestMapEntity(regionlist, provCode, cityCode, countyCode);
		if (entity != null) {
			return entity.getId();
		}
		return null;
	}
	
	
	
	@Override
	public String findRegionOrgByCode(String countyCode, String cityCode,
			String provCode, Date billDate, String productCode,
			String regionNature) {
		if (StringUtil.isEmpty(provCode)) {
			return null;
		}
		// 根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";

		if (StringUtils.isEmpty(cityCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + cityCode;
		}
		if (StringUtils.isEmpty(countyCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + countyCode;
		}
		log.info("key str>>" + key);
		List<PriceRegionEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				if (StringUtils.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
					regionlist = effectiveRegionCacheDeal.getPriceRegionByCache(key, billDate);
				} else if (StringUtils.equals(PricingConstants.PRICING_REGION, regionNature)) {
					regionlist = priceRegionCacheDeal.getPriceRegionByCache(key, billDate);
				}
			} catch (Exception e) {
				log.info("无法获取区域缓存", e);
			}
		}
		if (CollectionUtils.isEmpty(regionlist)) {
			PriceRegionEntity regionEntity = new PriceRegionEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setRegionNature(regionNature);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionlist = regionDao.searchRegionByDistrictNew(regionEntity);
		}
		// 过滤最符合条件的数据
		PriceRegionEntity entity = filterBestMapEntity(regionlist, provCode, cityCode, countyCode);
		if (entity != null) {
			return entity.getId();
		}
		return null;
	}
	/**
	 * 大票直达 出发区域
	 */
	@Override
	public String findBGRegionOrgByDeptNo(String orgCode, Date billDate, String productCode, String regionNature) {
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		/**
		 * 1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		 */
		PriceRegioOrgnEntity priceRegioOrgnEntity = null;
		PriceRegioOrgnBigGoodsEntity priceRegioOrgnBigGoodsEntity = null;
		
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				if (StringUtils.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
					priceRegioOrgnEntity = effectiveRegionOrgCacheDeal.getPriceRegionOrgByCache(orgCode, billDate);
				} else if (StringUtils.equals(PricingConstants.PRICING_REGION, regionNature)) {
					priceRegioOrgnBigGoodsEntity = priceRegionBigOrgCacheDeal.getPriceRegionOrgByCache(orgCode, billDate);
				}
			} catch (Exception e) {
				log.info("无法获取区域与部门关系缓存", e);
			}
		}
		if (priceRegioOrgnEntity != null) {
			return priceRegioOrgnEntity.getPriceRegionId();
		} else {
			priceRegioOrgnBigGoodsEntity = new PriceRegioOrgnBigGoodsEntity();
			priceRegioOrgnBigGoodsEntity.setIncludeOrgCode(orgCode);
			priceRegioOrgnBigGoodsEntity.setRegionNature(regionNature);
			priceRegioOrgnBigGoodsEntity.setActive(FossConstants.ACTIVE);
			priceRegioOrgnBigGoodsEntity.setBillDate(billDate);
			List<PriceRegioOrgnBigGoodsEntity> resultList = regionBigGoodsDao.searchRegionOrgByCondition(priceRegioOrgnBigGoodsEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegioOrgnBigGoodsEntity object = resultList.get(0);
				return object.getPriceRegionId();
			}
		}
		/**
		 * 2 ,没找到，按组织所在行政区域匹配
		 */
		String countyCode = null;
		String cityCode = null;
		String provCode = null;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode, billDate);
		if (orginfo != null) {
			countyCode = orginfo.getCountyCode();
			cityCode = orginfo.getCityCode();
			provCode = orginfo.getProvCode();
			if (StringUtil.isEmpty(provCode)) {
				return null;
			}
		} else {
			OuterBranchParamsDto dto = new OuterBranchParamsDto();
			dto.setDate(billDate);
			dto.setAgentDeptCode(orgCode);
			List<OuterBranchEntity> entity = vehicleAgencyDeptService.queryOuterBranchsSimpleInfo(dto);
			if (CollectionUtils.isNotEmpty(entity)) {
				OuterBranchEntity obkect = entity.get(0);
				countyCode = obkect.getCountyCode();
				cityCode = obkect.getCityCode();
				provCode = obkect.getProvCode();
				if (StringUtil.isEmpty(provCode)) {
					return null;
				}
			} else {
				return null;
			}
		}
		// 根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";

		if (StringUtils.isEmpty(cityCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + cityCode;
		}
		if (StringUtils.isEmpty(countyCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + countyCode;
		}
		log.info("key str>>" + key);
		List<PriceRegionBigGoodsEntity> regionBiglist = null;
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				if (StringUtils.equals(PricingConstants.PRICING_REGION, regionNature)) {
					regionBiglist = priceRegionBigCacheDeal.getPriceRegionByCache(key, billDate);
				}
			} catch (Exception e) {
				log.info("无法获取区域缓存", e);
			}
		}
		if (CollectionUtils.isEmpty(regionBiglist)) {
			PriceRegionBigGoodsEntity regionEntity = new PriceRegionBigGoodsEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setRegionNature(regionNature);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionBiglist = regionBigGoodsDao.searchBGRegionByDistrictNew(regionEntity);
		}
		// 过滤最符合条件的数据
		PriceRegionBigGoodsEntity entity = filterBGBestMapEntity(regionBiglist, provCode, cityCode, countyCode);
		if (entity != null) {
			return entity.getId();
		}
		return null;
	}

	//精准包裹出发区域
	@Override
	public String findEcGoodsRegionOrgByDeptNo(String orgCode, Date billDate, String productCode, String regionNature) {
		/**
		 * 1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		 */
		PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcGoodsEntity=null;
		//先从缓存查找
		if(SqlUtil.loadCache){
			try{
				priceRegioOrgnEcGoodsEntity = priceRegionEcOrgCacheDeal.getPriceRegionOrgByCache(orgCode, billDate);
			}
			catch (Exception e){
				log.info("无法获取区域与部门关系缓存",e);
			}
		}

		if(priceRegioOrgnEcGoodsEntity!=null){
			return priceRegioOrgnEcGoodsEntity.getPriceRegionId();
		}
		else{
			priceRegioOrgnEcGoodsEntity =  new PriceRegioOrgnEcGoodsEntity();
			priceRegioOrgnEcGoodsEntity.setIncludeOrgCode(orgCode);
			priceRegioOrgnEcGoodsEntity.setRegionNature(regionNature);
			priceRegioOrgnEcGoodsEntity.setActive(FossConstants.ACTIVE);
			priceRegioOrgnEcGoodsEntity.setBillDate(billDate);
			//封装数据，调用Dao层进行查询
			List<PriceRegioOrgnEcGoodsEntity> resultList = regionEcGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEcGoodsEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegioOrgnEcGoodsEntity object = resultList.get(0);
				return object.getPriceRegionId();
			}
		}


		/**
		 * 2 ,没找到，按组织所在行政区域匹配
		 */
		String countyCode;
		String cityCode;
		String provCode;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode, billDate);
		GetOrgClass getOrgClass = new GetOrgClass(orgCode, billDate, orginfo).invoke();
		if (getOrgClass.is()) 
			return null;
		provCode = getOrgClass.getProvCode();
		cityCode = getOrgClass.getCityCode();
		countyCode = getOrgClass.getCountyCode();
		// 根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		//foss 343617 赵一清 20161004 增加省份查找ID
		String key = StringUtils.isEmpty(provCode) ? "key" : provCode;

		key = StringUtils.isEmpty(cityCode) ? key + "#key" : key + "#" + cityCode;

		key = StringUtils.isEmpty(countyCode) ? key + "#key" : key + "#" + countyCode;

		log.info("key str>>" + key);

		List<PriceRegionEcGoodsEntity> regionEcGoodsList = null;
		if (SqlUtil.loadCache) {
			try {
				regionEcGoodsList = priceRegionEcCacheDeal.getPriceRegionByCache(key, billDate);
			} catch (Exception e) {
				log.info("无法获取区域与部门关系缓存", e);
			}
		}
		if (CollectionUtils.isEmpty(regionEcGoodsList)) {
			PriceRegionEcGoodsEntity regionEntity = new PriceRegionEcGoodsEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setRegionNature(regionNature);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionEcGoodsList = regionEcGoodsDao.searchEcRegionByDistrictNew(regionEntity);
		}

		// 过滤最符合条件的数据
		PriceRegionEcGoodsEntity entity = filterEcGoodsBestMapEntity(regionEcGoodsList, provCode, cityCode, countyCode);
		if (entity != null) {
			return entity.getId();
		}
		return null;
	}

	private PriceRegionEcGoodsEntity filterEcGoodsBestMapEntity(List<PriceRegionEcGoodsEntity> regionlist, String provCode, String cityCode, String countyCode) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}

		List<PriceRegionEcGoodsEntity> countryRegionlist=new ArrayList<PriceRegionEcGoodsEntity>();
		List<PriceRegionEcGoodsEntity> cityRegionlist=new ArrayList<PriceRegionEcGoodsEntity>();
		List<PriceRegionEcGoodsEntity> provRegionlist=new ArrayList<PriceRegionEcGoodsEntity>();
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		//foss 343617 赵一清 20161004 增加省份查找ID
		for (int j = 0; j < regionlist.size(); j++) {
			PriceRegionEcGoodsEntity temp = regionlist.get(j);
			if (StringUtil.isNotEmpty(countyCode) && StringUtils.equalsIgnoreCase(temp.getCountyCode(), countyCode)  ) {
				countryRegionlist.add(temp);
			}
			if ( StringUtil.isNotEmpty(cityCode) && StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode)  && StringUtil.isEmpty(temp.getCountyCode())) {
				cityRegionlist.add(temp);
			}
			if ( StringUtil.isNotEmpty(provCode) && StringUtils.equalsIgnoreCase(temp.getProCode(), provCode)  && StringUtil.isEmpty(temp.getCountyCode()) && StringUtil.isEmpty(temp.getCityCode())) {
				provRegionlist.add(temp);
			}
		}
		if (CollectionUtils.isNotEmpty(countryRegionlist)){
			return countryRegionlist.get(0);
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
			return cityRegionlist.get(0);
		}
		if (CollectionUtils.isNotEmpty(provRegionlist)){
			return provRegionlist.get(0);
		}
		return null;
	}

	/**
	 * <p>
	 * 根据行政区域查找逻辑区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-2 下午10:52:58
	 * 
	 * @param code
	 * 
	 * @param billDate
	 * 
	 * @param regionNature
	 * 
	 * @return
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#findRegionIdByDistrictCode(java.lang.String,
	 *      java.util.Date, java.lang.String)
	 */
	public String findRegionIdByDistrictCode(String code, Date billDate, String regionNature) {
		if (StringUtil.isEmpty(code)) {
			return null;
		}
		// step 1 : 查询逻辑区域上，符合条件的数据，返回
		PriceRegionEntity regionEntity = new PriceRegionEntity();
		regionEntity.setProCode(code);
		regionEntity.setCityCode(code);
		regionEntity.setCountyCode(code);
		regionEntity.setBillDate(billDate);
		regionEntity.setRegionNature(regionNature);
		regionEntity.setActive(FossConstants.ACTIVE);
		List<PriceRegionEntity> regionlist = regionDao.searchRegionByDistrict(regionEntity);
		PriceRegionEntity entity = filterBestMapEntity(regionlist,code,code,code);
		if (entity != null) {
			return entity.getId();
		}
		// step 2
		// ：如果第一步没找到，查询组织机构，查找该行政区域下所有的组织。
		//然后循环组织信息，查询该组织属于的区域，直到找到逻辑区域后返回。如果最后都没找到，则返回空。
		List<OrgAdministrativeInfoEntity> orglist = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByDistrictCode(code, billDate);
		if (CollectionUtils.isNotEmpty(orglist)) {
			for (int loop = 0; loop < orglist.size(); loop++) {
				OrgAdministrativeInfoEntity orgentity = orglist.get(loop);
				PriceRegioOrgnEntity priceRegioOrgnEntity = new PriceRegioOrgnEntity();
				priceRegioOrgnEntity.setIncludeOrgCode(orgentity.getCode());
				priceRegioOrgnEntity.setRegionNature(regionNature);
				priceRegioOrgnEntity.setActive(FossConstants.ACTIVE);
				priceRegioOrgnEntity.setBillDate(billDate);
				List<PriceRegioOrgnEntity> resultList = 
					regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
				if (resultList != null && resultList.size() > 0) {
				    //找到后返回
					PriceRegioOrgnEntity object = resultList.get(0);
					return object.getPriceRegionId();
				}
			}
		}
		// step 2
		 // ：如 查询外部代理网点，查找该行政区域下所有的外部网点。
		 //然后外部网点信息，查询该外部网点属于的区域，直到找到逻辑区域后返回。如果最后都没找到，则返回空。
		List<OuterBranchEntity> outerorglist = vehicleAgencyDeptService.queryOuterBranchsByDistrictCode(code, billDate);
		if (CollectionUtils.isNotEmpty(outerorglist)) {
			for (int loop = 0; loop < outerorglist.size(); loop++) {
				OuterBranchEntity outorgentity = outerorglist.get(loop);
				PriceRegioOrgnEntity priceRegioOrgnEntity = new PriceRegioOrgnEntity();
				priceRegioOrgnEntity.setIncludeOrgCode(outorgentity.getAgentDeptCode());
				priceRegioOrgnEntity.setRegionNature(regionNature);
				priceRegioOrgnEntity.setActive(FossConstants.ACTIVE);
				priceRegioOrgnEntity.setBillDate(billDate);
				List<PriceRegioOrgnEntity> resultList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
				if (resultList != null && resultList.size() > 0) {
					// 找到后返回
					PriceRegioOrgnEntity object = resultList.get(0);
					return object.getPriceRegionId();
				}
			}
		}
		return null;
	}
	/**
	 * <p>
	 * 过滤最匹配的区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-4 下午2:09:09
	 * 
	 * @param regionlist
	 * 
	 * @return
	 * 
	 * @see
	 */
	private PriceRegionEntity filterBestMapEntity(
			List<PriceRegionEntity> regionlist,String provCode,String cityCode,String countryCode) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		List<PriceRegionEntity> countryRegionlist=new ArrayList<PriceRegionEntity>();
		List<PriceRegionEntity> cityRegionlist=new ArrayList<PriceRegionEntity>();
		List<PriceRegionEntity> provRegionlist=new ArrayList<PriceRegionEntity>();
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 0; j < regionlist.size(); j++) {
			PriceRegionEntity temp = regionlist.get(j);
			if (StringUtils.equalsIgnoreCase(temp.getCountyCode(), countryCode)) {
				countryRegionlist.add(temp);
			}
			if (StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode)) {
				cityRegionlist.add(temp);
			}
			if (StringUtils.equalsIgnoreCase(temp.getProCode(), provCode)) {
				provRegionlist.add(temp);
			}
		}
		if (CollectionUtils.isNotEmpty(countryRegionlist)){
		    return filterBestMapEntity(countryRegionlist);
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
		    return filterBestMapEntity(cityRegionlist);
		}
//		if (CollectionUtils.isNotEmpty(provRegionlist)){
//		    return filterBestMapEntity(provRegionlist);
//		}
//		return filterBestMapEntity(regionlist);
		return null;
	}
	
	/**
	 * <p>
	 * 过滤最匹配的区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-4 下午2:09:09
	 * 
	 * @param regionlist
	 * 
	 * @return
	 * 
	 * @see
	 */
	private PriceRegionBigGoodsEntity filterBGBestMapEntity(
			List<PriceRegionBigGoodsEntity> regionlist,String provCode,String cityCode,String countryCode) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionBigGoodsEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		List<PriceRegionBigGoodsEntity> countryRegionlist=new ArrayList<PriceRegionBigGoodsEntity>();
		List<PriceRegionBigGoodsEntity> cityRegionlist=new ArrayList<PriceRegionBigGoodsEntity>();
		List<PriceRegionBigGoodsEntity> provRegionlist=new ArrayList<PriceRegionBigGoodsEntity>();
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 0; j < regionlist.size(); j++) {
			PriceRegionBigGoodsEntity temp = regionlist.get(j);
			if (StringUtils.equalsIgnoreCase(temp.getCountyCode(), countryCode)) {
				countryRegionlist.add(temp);
			}
			if (StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode)) {
				cityRegionlist.add(temp);
			}
			if (StringUtils.equalsIgnoreCase(temp.getProCode(), provCode)) {
				provRegionlist.add(temp);
			}
		}
		if (CollectionUtils.isNotEmpty(countryRegionlist)){
		    return filterBGBestMapEntity(countryRegionlist);
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
		    return filterBGBestMapEntity(cityRegionlist);
		}
//		if (CollectionUtils.isNotEmpty(provRegionlist)){
//		    return filterBestMapEntity(provRegionlist);
//		}
//		return filterBestMapEntity(regionlist);
		return null;
	}
	
	/**
	 * <p>
	 * 过滤最匹配的区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-4 下午2:09:09
	 * 
	 * @param regionlist
	 * 
	 * @return
	 * 
	 * @see
	 */
	private PriceRegionBigGoodsEntity filterBGBestMapEntity(
			List<PriceRegionBigGoodsEntity> regionlist) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionBigGoodsEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 1; j < regionlist.size(); j++) {
			PriceRegionBigGoodsEntity temp = regionlist.get(j);
			if (temp.getPriority() < result.getPriority()) {
			    //找到后替换
				result = temp;
			}
		}
		return result;
	}


	/**
	 * <p>
	 * 过滤最匹配的区域
	 * </p>
	 *
	 * @author FOSS-343617
	 *
	 * @date 2016-07-13 下午4:09:09
	 *
	 * @param regionlist 结果集
	 *
	 * @return
	 *
	 * @see
	 */

	/**
	 * <p>
	 * 过滤最匹配的区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-4 下午2:09:09
	 * 
	 * @param regionlist
	 * 
	 * @return
	 * 
	 * @see
	 */
	private PriceRegionEntity filterBestMapEntity(
			List<PriceRegionEntity> regionlist) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 1; j < regionlist.size(); j++) {
			PriceRegionEntity temp = regionlist.get(j);
			if (temp.getPriority() < result.getPriority()) {
			    //找到后替换
				result = temp;
			}
		}
		return result;
	}
	/** 
	 * 
	 * 根据ID，查询区域 信息
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:31:35
	 * 
	 * @param id
	 * 
	 * @param regionNature
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#searchRegionByID(java.lang.String, java.lang.String)
	 */
	@Override
	public PriceRegionEntity searchRegionByID(String id,
			String regionNature) {
		if(StringUtils.isEmpty(id)){
			return new PriceRegionEntity();
		}
		PriceRegionEntity model = regionDao.searchRegionByID(id, regionNature);
		if(model != null) {
			if(PricingConstants.PRESCRIPTION_REGION.equals(regionNature)){
			    model.setRegionNature(PricingConstants.PRESCRIPTION_REGION_NAME);
			}else if(PricingConstants.PRICING_REGION.equals(regionNature)){
			    model.setRegionNature(PricingConstants.PRICING_REGION_NAME);
			}
			//判断国家CODE不为空
			if(StringUtil.isNotBlank(model.getNationCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getNationCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setNationName(administrativeRegionsEntity.getName());
			}
			//判断省份CODE不为空
			if(StringUtil.isNotBlank(model.getProCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getProCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setProName(administrativeRegionsEntity.getName());
			}
			//判断城市CODE不为空
			if(StringUtil.isNotBlank(model.getCityCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getCityCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setCityName(administrativeRegionsEntity.getName());
			}
			//判断区县CODE不为空
			if(StringUtil.isNotBlank(model.getCountyCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getCountyCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setCountyName(administrativeRegionsEntity.getName());
			}
		}
		return model;
	}
	/**
	 * 查询区域名称
	 */
	@Override
	public List<PriceRegionEntity> searchRegionByName(String regionName, String regionNature) {
		return regionDao.searchRegionByName(regionName, regionNature);
	}
	/**
	 * 根据区域名称批量查找区域信息
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-22 下午8:26:15
	 * 
	 * @param names 区域名称List
	 * 
	 * @param regionNature 区域性质
	 * 
	 * @param billDate 开单日期
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public Map<String, PriceRegionEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
		return regionDao.findRegionByNames(names, regionNature, billDate);
	}
	/**
	 * 
	 * @Description: 刷新时效区域缓存
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-2-22 下午1:59:04
	 * 
	 * @param provinceCode
	 * 
	 * @param cityCode
	 * 
	 * @param countyCode
	 * 
	 * @version V1.0
	 */
	@Override
	public void refreshEffectiveRegionCache(String provinceCode, String cityCode, String countyCode) {
		String key = "";
		if(StringUtils.isEmpty(provinceCode)) {
			key = "key";
		} else {
			key = provinceCode;
		}
		if(StringUtils.isEmpty(cityCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + cityCode;
		}
		if(StringUtils.isEmpty(countyCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + countyCode;
		}
		try {
			effectiveRegionCacheDeal.getEffectiveRegionCache().invalid(key);
		} catch (Exception e) {
			log.info("无法刷新时效区域缓存数据",e);
		}
	}
	/**
	 * 
	 * @Description: 刷新时效区域与组织缓存
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-2-22 下午1:59:46
	 * 
	 * @param deptNo
	 * 
	 * @version V1.0
	 * 
	 */
	@Override
	public void refreshEffectiveRegionOrgCache(String deptNo) {
		if(StringUtil.isNotBlank(deptNo)) {
			try {
				effectiveRegionOrgCacheDeal.getEffectiveRegionOrgCache().invalid(deptNo);
			} catch (Exception e) {
				log.info("无法刷新时效区域和部门缓存数据",e);
			}
		}
	}
	/**
	 * 
	 * @Description: 刷新价格区域缓存
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-2-22 下午1:59:04
	 * 
	 * @param provinceCode
	 * 
	 * @param cityCode
	 * 
	 * @param countyCode
	 * 
	 * @version V1.0
	 */
	@Override
	public void refreshPriceRegionCache(String provinceCode, String cityCode, String countyCode) {
		String key = "";
		if(StringUtils.isEmpty(provinceCode)) {
			key = "key";
		} else {
			key = provinceCode;
		}
		if(StringUtils.isEmpty(cityCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + cityCode;
		}
		if(StringUtils.isEmpty(countyCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + countyCode;
		}
		try {
			priceRegionCacheDeal.getPriceRegionCache().invalid(key);
		} catch (Exception e) {
			log.info("无法刷新价格区域缓存数据",e);
		}
	}
	/**
	 * 
	 * @Description: 刷新价格区域与组织缓存
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-2-22 下午1:59:46
	 * 
	 * @param deptNo
	 * 
	 * @version V1.0
	 */
	@Override
	public void refreshPriceRegionOrgCache(String deptNo) {
		if(StringUtil.isNotBlank(deptNo)) {
			try {
				priceRegionOrgCacheDeal.getPriceRegionOrgCache().invalid(deptNo);
			} catch (Exception e) {
				log.info("无法刷新价格区域和部门缓存数据",e);
			}
		}
	}
	
	/**
	 * 
	 * @Description: 立即中止
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-21 下午2:09:39
	 * 
	 * @param regionId
	 * 
	 * @param regionNature
	 * 
	 * @param date
	 * 
	 * @version V1.0
	 */
	@Override
	@Transactional
	public void immedietelyStopRegion(String regionId, String regionNature, Date endTime) {
		WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
		//查询需要更新的记录
		PriceRegionEntity model = regionDao.searchRegionByID(regionId, regionNature);
		// 获取当前登录用户
		String userCode = getCurrentUserCode();
		// 获取当前登录用户所在部门
		String orgCode = getCurrentOrgCode();
		//获取版本号
		Long versionNoOrg = new Date().getTime();
		//设置终止时间
		model.setEndTime(endTime);
		//补充更新信息
		model.setVersionNo(versionNoOrg);
		model.setModifyOrgCode(orgCode);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		model.setModifyUser(userCode);
		model.setRegionNature(regionNature);
		//执行更新操作
		waybillRegionInfoDto.setRegionEntity(model);
		regionDao.updateRegion(model);
		//查询区域与部门关系相关记录
		PriceRegioOrgnEntity priceRegioOrgnEntity = new PriceRegioOrgnEntity();
		priceRegioOrgnEntity.setPriceRegionId(model.getId());
		priceRegioOrgnEntity.setRegionNature(regionNature);
		List<PriceRegioOrgnEntity> priceRegioOrgnEntityList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
		if (CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
			waybillRegionInfoDto.setPriceRegioOrgnEntityList(priceRegioOrgnEntityList);
			for (PriceRegioOrgnEntity priceRegioOrgnModel : priceRegioOrgnEntityList) {
				priceRegioOrgnModel.setVersionNo(versionNoOrg);
				priceRegioOrgnModel.setRegionNature(regionNature);
				//如果原结束时间晚于此次更新时间，则执行更新
				//如果原结束时间早于此次更新时间，则不执行更新
				if(endTime.getTime() < priceRegioOrgnModel.getEndTime().getTime()) {
					priceRegioOrgnModel.setEndTime(endTime);
				} else {
					continue;
				}
				priceRegioOrgnModel.setModifyOrgCode(orgCode);
				priceRegioOrgnModel.setModifyUser(userCode);
				regionDao.updateRegionOrg(priceRegioOrgnModel);
			}
		}
		/**
		 * 维护行政区域与区域关系
		 */
		if(StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, model.getRegionType())){
			if(StringUtils.isNotEmpty(model.getCountyCode())) {
				districtRegionService.addDistrictRegion(model.getCountyCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCountyCode());
			} 
			if(StringUtils.isNotEmpty(model.getCityCode())) {
				districtRegionService.addDistrictRegion(model.getCityCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCityCode());
			}
		} else if(PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(model.getRegionType())) {
			if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
				List<String> cityList = new ArrayList<String>();
				List<String> countyList = new ArrayList<String>();
				for (PriceRegioOrgnEntity regioOrgnEntity : priceRegioOrgnEntityList) {
					String deptCode = regioOrgnEntity.getIncludeOrgCode();
					OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode, new Date());
					if (orginfo != null) {
						String countyCode = orginfo.getCountyCode();
						String cityCode = orginfo.getCityCode();
						if (StringUtil.isNotEmpty(countyCode) && !cityList.contains(countyCode)) {
							countyList.add(countyCode);
						}
						if (StringUtil.isNotEmpty(cityCode) && !cityList.contains(cityCode)) {
							cityList.add(cityCode);
						}
					}
				}
				if(CollectionUtils.isNotEmpty(countyList)) {
					for (String countyCode : countyList) {
						districtRegionService.addDistrictRegion(countyCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(countyCode);
					}
				}
				if(CollectionUtils.isNotEmpty(cityList)) {
					for (String cityCode : cityList) {
						districtRegionService.addDistrictRegion(cityCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(cityCode);
					}
				}
			}
		}
		//FOSS推送价格出发区域到DTD
	  if(StringUtils.isNotBlank(regionNature) && (PricingConstants.PRICING_REGION).equals(regionNature)){
		  waybillRegionInfoDto.setJudgmentOperation("stop");
		  waybillRegionImpPushService.pushWaybillHomeInfo(waybillRegionInfoDto);
	   }
	}
	/**
	 * 
	 * @Description: 立即激活
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-25 上午10:41:28
	 * 
	 * @param regionId
	 * 
	 * @param regionNature
	 * 
	 * @param beginTime
	 * 
	 * @version V1.0
	 */
	@Override
	@Transactional
	public void immedietelyActiveRegion(String regionId, String regionNature,Date beginTime){
		WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
		//查询需要更新的记录
		PriceRegionEntity model = regionDao.searchRegionByID(regionId, regionNature);
		// 获取当前登录用户
		String userCode = getCurrentUserCode();
		// 获取当前登录用户所在部门
		String orgCode = getCurrentOrgCode();
		//获取版本号
		Long versionNoOrg = new Date().getTime();
		//设置起始时间
		model.setBeginTime(beginTime);
		//补充更新信息
		model.setVersionNo(versionNoOrg);
		model.setModifyOrgCode(orgCode);
		model.setModifyUser(userCode);
		model.setActive(FossConstants.ACTIVE);
		model.setRegionNature(regionNature);
		//执行更新操作
		waybillRegionInfoDto.setRegionEntity(model);
		regionDao.updateRegion(model);
		//
		//查询区域与部门关系相关记录
		PriceRegioOrgnEntity priceRegioOrgnEntity = new PriceRegioOrgnEntity();
		priceRegioOrgnEntity.setPriceRegionId(model.getId());
		priceRegioOrgnEntity.setRegionNature(regionNature);
		List<PriceRegioOrgnEntity>  priceRegioOrgnEntityList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)){
			waybillRegionInfoDto.setPriceRegioOrgnEntityList(priceRegioOrgnEntityList);
			for(PriceRegioOrgnEntity priceRegioOrgnModel:priceRegioOrgnEntityList){
				priceRegioOrgnModel.setVersionNo(versionNoOrg);
				//修改状态
				priceRegioOrgnModel.setActive(FossConstants.ACTIVE);
				//补充更新信息
				priceRegioOrgnModel.setRegionNature(regionNature);
				priceRegioOrgnModel.setBeginTime(beginTime);
				priceRegioOrgnModel.setModifyOrgCode(orgCode);
				priceRegioOrgnModel.setModifyUser(userCode);
				regionDao.updateRegionOrg(priceRegioOrgnModel);
			}
		}
		/**
		 * 维护行政区域与区域关系
		 */
		if(StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, model.getRegionType())){
			if(StringUtils.isNotEmpty(model.getCountyCode())) {
				districtRegionService.addDistrictRegion(model.getCountyCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCountyCode());
			} 
			if(StringUtils.isNotEmpty(model.getCityCode())) {
				districtRegionService.addDistrictRegion(model.getCityCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCityCode());
			}
		} else if(PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(model.getRegionType())) {
			if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
				List<String> cityList = new ArrayList<String>();
				List<String> countyList = new ArrayList<String>();
				for (PriceRegioOrgnEntity regioOrgnEntity : priceRegioOrgnEntityList) {
					String deptCode = regioOrgnEntity.getIncludeOrgCode();
					OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode, new Date());
					if (orginfo != null) {
						String countyCode = orginfo.getCountyCode();
						String cityCode = orginfo.getCityCode();
						if (StringUtil.isNotEmpty(countyCode) && !cityList.contains(countyCode)) {
							countyList.add(countyCode);
						}
						if (StringUtil.isNotEmpty(cityCode) && !cityList.contains(cityCode)) {
							cityList.add(cityCode);
						}
					}
				}
				if(CollectionUtils.isNotEmpty(countyList)) {
					for (String countyCode : countyList) {
						districtRegionService.addDistrictRegion(countyCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(countyCode);
					}
				}
				if(CollectionUtils.isNotEmpty(cityList)) {
					for (String cityCode : cityList) {
						districtRegionService.addDistrictRegion(cityCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(cityCode);
					}
				}
			}
		}
		//FOSS推送价格出发区域到DTD
		if(StringUtils.isNotBlank(regionNature) && (PricingConstants.PRICING_REGION).equals(regionNature)){
			waybillRegionInfoDto.setJudgmentOperation("active");
			waybillRegionImpPushService.pushWaybillHomeInfo(waybillRegionInfoDto);
		}
	}
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
	public List<String> searchRegionOrgCodeByRegionId(String deptRegionId, String regionNature) {
		List<String> list = null;
		List<PriceRegioOrgnEntity> priceRegioOrgnEntities = regionDao.searchRegionOrgByRegionId(deptRegionId, regionNature);
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntities)) {
			list = new ArrayList<String>();
			for (PriceRegioOrgnEntity priceRegioOrgnEntity : priceRegioOrgnEntities) {
				list.add(priceRegioOrgnEntity.getIncludeOrgCode());
			}
		}
		return list;
	}
	/**
	 * 
	 * @Description: 获得当前部门值
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
	/**
	 * 
	 * @Description: 获得当前人
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		if(currentUser == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		return currentUser.getEmployee().getEmpCode();
	}
	/**
	 * 获取 网点SERVICe.
	 *
	 * @return the 网点SERVICe
	 */
	public IVehicleAgencyDeptService getVehicleAgencyDeptService() {
		return vehicleAgencyDeptService;
	}
	/**
	 * 设置 网点SERVICe.
	 *
	 * @param vehicleAgencyDeptService the new 网点SERVICe
	 */
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	/**
	 * 设置 行政区域SERVICE.
	 *
	 * @param administrativeRegionsService the new 行政区域SERVICE
	 */
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 设置 职员service.
	 *
	 * @param employeeService the new 职员service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * 设置 组织机构接口.
	 *
	 * @param orgAdministrativeInfoService the new 组织机构接口
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 获取 区域 DAO.
	 *
	 * @return the 区域 DAO
	 */
	public IRegionDao getRegionDao() {
		return regionDao;
	}
	/**
	 * 设置 区域 DAO.
	 *
	 * @param regionDao the new 区域 DAO
	 */
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}
	/**
	 * 获取 组织机构接口.
	 *
	 * @return the 组织机构接口
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}
	/**
	 * 获取 行政区域SERVICE.
	 *
	 * @return the 行政区域SERVICE
	 */
	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}
	/**
	 * 获取 职员service.
	 *
	 * @return the 职员service
	 */
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}
	/**
	 * 设置 价格区域 缓存处理.
	 *
	 * @param priceRegionCacheDeal the new 价格区域 缓存处理
	 */
	public void setPriceRegionCacheDeal(PriceRegionCacheDeal priceRegionCacheDeal) {
		this.priceRegionCacheDeal = priceRegionCacheDeal;
	}
	/**
	 * 设置 时效区域 缓存处理.
	 *
	 * @param effectiveRegionCacheDeal the new 时效区域 缓存处理
	 */
	public void setEffectiveRegionCacheDeal(EffectiveRegionCacheDeal effectiveRegionCacheDeal) {
		this.effectiveRegionCacheDeal = effectiveRegionCacheDeal;
	}
	/**
	 * 设置 价格区域与部门 缓存处理.
	 *
	 * @param priceRegionOrgCacheDeal the new 价格区域与部门 缓存处理
	 */
	public void setPriceRegionOrgCacheDeal(PriceRegionOrgCacheDeal priceRegionOrgCacheDeal) {
		this.priceRegionOrgCacheDeal = priceRegionOrgCacheDeal;
	}
	/**
	 * 设置 时效区域与部门 缓存处理.
	 *
	 * @param effectiveRegionOrgCacheDeal the new 时效区域与部门 缓存处理
	 */
	public void setEffectiveRegionOrgCacheDeal(EffectiveRegionOrgCacheDeal effectiveRegionOrgCacheDeal) {
		this.effectiveRegionOrgCacheDeal = effectiveRegionOrgCacheDeal;
	}
	public void setDistrictRegionCacheDeal(DistrictRegionCacheDeal districtRegionCacheDeal) {
		this.districtRegionCacheDeal = districtRegionCacheDeal;
	}
	public void setDistrictRegionService(IDistrictRegionService districtRegionService) {
		this.districtRegionService = districtRegionService;
	}
	public IRegionBigGoodsDao getRegionBigGoodsDao() {
		return regionBigGoodsDao;
	}
	public void setRegionBigGoodsDao(IRegionBigGoodsDao regionBigGoodsDao) {
		this.regionBigGoodsDao = regionBigGoodsDao;
	}
	public PriceRegionBigCacheDeal getPriceRegionBigCacheDeal() {
		return priceRegionBigCacheDeal;
	}
	public void setPriceRegionBigCacheDeal(
			PriceRegionBigCacheDeal priceRegionBigCacheDeal) {
		this.priceRegionBigCacheDeal = priceRegionBigCacheDeal;
	}
	public PriceRegionBigOrgCacheDeal getPriceRegionBigOrgCacheDeal() {
		return priceRegionBigOrgCacheDeal;
	}
	public void setPriceRegionBigOrgCacheDeal(
			PriceRegionBigOrgCacheDeal priceRegionBigOrgCacheDeal) {
		this.priceRegionBigOrgCacheDeal = priceRegionBigOrgCacheDeal;
	}

	public void setRegionPriceDao(IRegionPriceDao regionPriceDao) {
		this.regionPriceDao = regionPriceDao;
	}


	/**
	 * <p>TODO(获取当前出发部门的所有价格区域价格列表)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-23 下午6:55:13
	 * @param id
	 * @param currentDateTime
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#querykRegionPriceList(java.lang.String, java.util.Date)
	 */
	@Override
	public List<RegionPartnerPriceDto> queryRegionPriceList(QueryPriceBean queryPriceBean) {
		//到达目的站为网点价格区域
		List<RegionPartnerPriceDto> networkRegionPriceEntities=regionPriceDao.queryNetworkRegionPriceEntities(queryPriceBean);
		//行政区域市级的要看市下面是否有营业网点
		List<RegionPartnerPriceDto> cityRegionPriceEntities=regionPriceDao.queryCityRegionPriceEntities(queryPriceBean);
		//区级的要看区下面是否有营业网点 
		List<RegionPartnerPriceDto> countryRegionPriceEntities=regionPriceDao.queryCountyRegionPriceEntities(queryPriceBean);
		networkRegionPriceEntities.addAll(countryRegionPriceEntities);
		networkRegionPriceEntities.addAll(cityRegionPriceEntities);
		List<RegionPartnerPriceDto> regionPartnerPriceDtos=networkRegionPriceEntities;
		return regionPartnerPriceDtos;
	}



	private class GetOrgClass {
		private boolean myResult;
		private String orgCode;
		private Date billDate;
		private OrgAdministrativeInfoEntity orginfo;
		private String countyCode;
		private String cityCode;
		private String provCode;

		public GetOrgClass(String orgCode, Date billDate, OrgAdministrativeInfoEntity orginfo) {
			this.orgCode = orgCode;
			this.billDate = billDate;
			this.orginfo = orginfo;
		}

		boolean is() {
			return myResult;
		}

		public String getCountyCode() {
			return countyCode;
		}

		public String getCityCode() {
			return cityCode;
		}

		public String getProvCode() {
			return provCode;
		}

		public GetOrgClass invoke() {
			if (orginfo != null) {
                countyCode = orginfo.getCountyCode();
                cityCode = orginfo.getCityCode();
                provCode = orginfo.getProvCode();
                if (StringUtil.isEmpty(provCode)) {
					myResult = true;
					return this;
                }
            } else {
                OuterBranchParamsDto dto = new OuterBranchParamsDto();
                dto.setDate(billDate);
                dto.setAgentDeptCode(orgCode);
                List<OuterBranchEntity> entity = vehicleAgencyDeptService.queryOuterBranchsSimpleInfo(dto);
                if (CollectionUtils.isNotEmpty(entity)) {
                    OuterBranchEntity obkect = entity.get(0);
                    countyCode = obkect.getCountyCode();
                    cityCode = obkect.getCityCode();
                    provCode = obkect.getProvCode();
                    if (StringUtil.isEmpty(provCode)) {
						myResult = true;
						return this;
                    }
                } else {
					myResult = true;
					return this;
                }
            }
			myResult = false;
			return this;
		}
	}
}

