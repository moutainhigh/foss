/**
 *  查询界面主要分为3个部分查询条件区域、查询结果列。
 *  
*1.	查询条件区域：
*
* 出发城市、目的地
* 
* a)	出发城市：
* 
*出发城市默认是当前人员所在城市，
*
*也可以进行选择。
*
*b)	目的地：
*
*目的地是指货物运输的目的地。
*
*可以选择选择国家、省、市、区县以及填写地址来查询目的城市的网点。
*
*2.	功能按钮：  
*
*查询按钮、退出按钮。
*
*a)	查询：
*
*查询条件符合的列表数据。
*
*b)	退出：
*
*点击此按钮，退出当前页面回到主界面。
*
*3.	查询结果列表：
*
*目的城市、产品信息、重货价格、轻货价格、运营时效、
*
*取货时间、长短途、取货时间、最低一票、备注信息。
*
*
*通过公布价菜单进入此页面，
*
*默认当前营业部门所在城市，
*
*录入查询条件后，
*
*点击查询时，
*
*
*将符合的查询条件的信息列表展示在下面。
*
*点击城市超链接，
*
*会弹出新的窗口，
*
*该窗口展示网点信息。
*
*该网点信息的展现规则是：
*
*1.当只选择了城市层级，
*
*则根据GIS里城市所辖的全部网点返回网点信息。
*
*2.当选择到区县层级，
*
*则根据GIS里区县所辖的全部网点返回网点信息。
*
*3.当填写地址信息，
*
*则根据选择的信息以及填写的地址信息从GIS里所查询的全部网点返回网点信息。
*
*
*
*SR1	查询支持条件组合查询,
*
*目的地通过选择下拉列表中的类型来支持不同的查询。
*
*如果选择城市则按填写的相应城市所对应的区域进行查询；
*
*如果选择地址，
*
*则按填写地址通过GIS进行查找相对应的网点，
*
*并以这些网点对应的区域进行查询
*
*SR2	公布价后台查询规则描述： 
*
*请参考1.5.4逻辑图，
*
*根据部门信息到区域中去查找且按照异常优先级
*
*（先找区域类型为国标区域与部门所维护的区域，
*
*再找部门所在城市信息，
*
*最后找部门所在省份信息直到找到对应的区域为止）
*
*来确定最终区域信息，
*
*定位到了区域信息就可以分别找区域类型了，
*
*它包括了时效区域与价格区域，
*
*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
*
*如果时效区域信息在时效版本里面没有找到相关的信息，
*
*那么基于该区域的公布价就不用显示了，
*
*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
*
*SR3	
*
*查询列表中重货价格以公斤计算。
*
*SR4
*
*	查询列表中轻货价格以体积计算。
*
*SR5	
*
*查询列表中营运时效对客户承诺的时间。
*
*
*SR6	
*
*公布价信息中的最低一票信息是根据区域和产品条目信息关联
*
*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
*
*与产品-V0.1.doc 中所维护的最低一票而来的。

 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PublishPriceService.java
 * 
 * FILE NAME        	: PublishPriceService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import static java.util.Collections.sort;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPricingOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SaleDepartmentInfoDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPublishPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceCriteriaDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionBigGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionBigGoodsService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.server.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PopPublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublishPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PublishPriceException;
import com.deppon.foss.module.pickup.pricing.server.cache.DistrictRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 公布价查询Service
 * 
 * 	1、按照始发部门信息查询对应所有目的站的时效,价格信息 - 满足公布价SUC查询服务用例
 * 
 * 	2、按照始发城市信息获取所有目的站时效,价格信息 -   满足网银接口服务方法
 * 
 * 	3、按照始发部门、产品、货物类型查询相关目的站的时效价格-满足GUI开单时候所需要的价格、时效信息
 * 
 * @author 岳洪杰
 * 
 * @date 2012-10-12 上午8:43:02
 * 
 * @since
 * 
 * @version
 */
public class PublishPriceService implements IPublishPriceService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PublishPriceService.class);
	 /**
     * 时效详细信息接口
     */
    @Inject
    private IEffectivePlanDetailService effectivePlanDetailService;
    /**
     * 计价方案明细接口
     */
    @Inject
    private IPriceCriteriaDetailService priceCriteriaDetailService;
    /**
     * 组织机构接口
     */
    @Inject
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 行政区域接口
     */
    @Inject
    private IAdministrativeRegionsService  administrativeRegionsService;
    /**
     * 产品接口
     */
    @Inject
    private IProductService productService;
    /**
     * 产品接口
     */
    @Inject
    private IGoodsTypeService goodsTypeService;
    /**
     * 区域接口
     */
    @Inject
    private IRegionService regionService;
	/**
     *到达区域接口
     */
    @Inject
    private IRegionArriveService regionArriveService;
    
    /**
     * 空运区域接口
     */
    @Inject
    private IRegionAirService regionAirService;
    /**
     * 公布价DAO
     */
    @Inject
    private IPublishPriceDao publishPriceDao;
    /**
     * 营业部DAO
     */
    @Inject
    private ISaleDepartmentDao saleDepartmentDao;
    /**
     * 代理DAO
     */
    @Inject
    private IVehicleAgencyDeptDao vehicleAgencyDeptDao;
    /**
     * 网点综合Dao
     */
    @Inject
    private IPricingOrgDao pricingOrgDao;
    /**
     * 计价分段Dao
     * @author 219413-Luomengxiang  2014.11.17
     */
    @Inject
    private IPopPriceDetailSectionDao popPriceDetailSectionDao;
    /**
     * 价格区域
     */
    @Inject
	private IRegionValueAddService regionValueAddService;
    /**
     * 调用gis链接
     */
    @Inject
    private String gisUrl;
    
    private DistrictRegionCacheDeal districtRegionCacheDeal;
    @Inject
    private IDistrictRegionService districtRegionService;
	@Inject
    private IPricingValueAddedService pricingValueAddedService;
	
	@Inject
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/** 
     * 计费规则dao
     */
    @Inject
    IPriceValuationDao priceValuationDao;
    
	/**
	 * 偏线中转费服务
	 */
	@Inject
	private IOuterPriceService outerPriceService;
	    /**
     * 精准大票出发区域接口
     */
    @Inject
    private IRegionBigGoodsService regionBigGoodsService;
    /**
     * 精准大票到达区域接口
     * RegionBigGoodsArriveService
     * @return
     */
    private IRegionBigGoodsArriveService regionBigGoodsArriveService;
	
    /**
     * 精准包裹价格出发区域
     */
    private IRegionEcGoodsService regionEcGoodsService;
    
    /**
     * 精准包裹价格到达区域
     */
    private IRegionEcGoodsArriveService regionEcGoodsArriveService;
    
    public void setRegionEcGoodsService(IRegionEcGoodsService regionEcGoodsService) {
  		this.regionEcGoodsService = regionEcGoodsService;
  	}
  	public void setRegionEcGoodsArriveService(
  			IRegionEcGoodsArriveService regionEcGoodsArriveService) {
  		this.regionEcGoodsArriveService = regionEcGoodsArriveService;
  	}
    /**
	 * 魔法数字1
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int ONE=1;
	/**
	 * 魔法数字2
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int TWO=2;
	/**
	 * 魔法数字3
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int THREE=3;
	/**
	 * 魔法数字4
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int FOUR=4;
	/**
	 * 魔法数字5
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int FIVE=5;
	/**
	 * 魔法数字6
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int SIX=6;

	public void setRegionBigGoodsArriveService(
			IRegionBigGoodsArriveService regionBigGoodsArriveService) {
		this.regionBigGoodsArriveService = regionBigGoodsArriveService;
	}
	public void setRegionBigGoodsService(
			IRegionBigGoodsService regionBigGoodsService) {
		this.regionBigGoodsService = regionBigGoodsService;
	}
	public void setOuterPriceService(IOuterPriceService outerPriceService) {
		this.outerPriceService = outerPriceService;
	}
	/**
	 * 偏线时效服务
	 */
	@Inject
	private IOuterEffectivePlanService outerEffectivePlanService;
	
	/**
	 * 偏线服务接口
	 */
    @Inject
	private IVehicleAgencyDeptService  vehicleAgencyDeptService;
    
    public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	
	/**
	 * 走货路径
	 * 提供与走货路径相关的服务接口
	 */
	@Inject
	private IFreightRouteService freightRouteService;
	/**
	 * 走货路径
	 * 提供与走货路径相关的服务接口
	 */
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}
	/**
	 * 偏线时效服务
	 */
	public void setOuterEffectivePlanService(
			IOuterEffectivePlanService outerEffectivePlanService) {
		this.outerEffectivePlanService = outerEffectivePlanService;
	}
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
     * 方法（一）
     * 
     * 
     * <p>(查询公布价信息，根据条件始发部门到目的站所有相关最新的公布价信息,其中包括
     * 
     * 
     * 出发逻辑区域与到达逻辑区域之间承诺时效、取货时间、最低一票、重货、轻货费率)</p> 
     * 
     * @author 岳洪杰
     * 
     * 
     * @date 2012-10-12 上午8:46:51
     * 
     * 
     * @param startDeptNo-始发部门编号
     * 
     * 
     * @param destinationCode-目的地编码
     * 
     * 
     * @return 
     * 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService#queryPublishPriceDetail(java.lang.String, java.lang.String)
     * 
     */
    @Override
    public List<PublishPriceEntity> queryPublishPriceDetail(String startDeptNo, String destinationCode, Date billDate) {
    	LOGGER.info("queryPublishPriceDetail 1 startTime >>"+ new Date());
	    //(1) 时效
    	//根据出发部门Code，到达目的地获取
    	//（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
    	List<EffectivePlanDto> effectInfoList = effectivePlanDetailService.queryEffectivePlanDetailListByOrgCode(startDeptNo,
    			destinationCode, null, billDate);
	    //(2) 价格
		//根据出发部门Code,到达目的地获取
    	//（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
    	List<ProductPriceDto> priceInfoList = this.queryCuurentPublishPriceDetailInfo(startDeptNo, destinationCode, billDate, PricingConstants.VALUATION_TYPE_PRICING);
    	
		//(3) 增值服务
    	List<ValueAddDto>  valueAddDtoList = this.queryCuurentPublishValueAddDetailInfo(startDeptNo, destinationCode, billDate);
		List<PublishPriceEntity> voList = new ArrayList<PublishPriceEntity>();
//		//根据destinationCode获取组织名字
//		String arrvName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(destinationCode);
//		//根据startDeptNo获取组织名字
//		String startName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(startDeptNo);
		//根据destinationCode获取组织名字
		
		String arrvName = null;
		if(SqlUtil.loadCache){
		
        		CommonOrgEntity arrvCommonOrgEntity = pricingOrgDao.queryOrgByCode(destinationCode, FossConstants.ACTIVE);
        	
        		if(arrvCommonOrgEntity != null) {
        			arrvName = arrvCommonOrgEntity.getName();
        		}
		}else{
		  arrvName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(destinationCode);
		}
		//根据startDeptNo获取组织名字
		String startName = null;
		if(SqlUtil.loadCache){
			
        		CommonOrgEntity startCommonOrgEntity = pricingOrgDao.queryOrgByCode(startDeptNo, FossConstants.ACTIVE);
        		
        		if(startCommonOrgEntity != null) {
        			startName = startCommonOrgEntity.getName();
        		}
		}else{
		    startName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(startDeptNo);
			
		}
		//合并价格和时效信息
		//encapulatePublishPrice(effectInfoList, priceInfoList, voList, arrvName, startName);
   		 encapulatePublishPrice(effectInfoList, priceInfoList, voList,valueAddDtoList, arrvName, startName);
		LOGGER.info("queryPublishPriceDetail 1 endTime >>"+ new Date());
		//返回结果集合
		return voList;
    }
    
    /**
	 * 查询偏线公布价信息
	 * @author WangQianJin
	 * @param queryBillCacilateDto
	 * @return
	 */
    @Override
    public List<PublishPriceEntity> queryPublishPriceDetailForPX(QueryBillCacilateDto queryBillCacilateDto){
    	LOGGER.info("queryPublishPriceDetail 1 startTime >>"+ new Date());
	    //(1) 时效
    	//根据出发部门Code，到达目的地获取
    	//（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
    	List<EffectivePlanDto> effectInfoList = effectivePlanDetailService.queryEffectivePlanDetailListByOrgCode(
    			queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getLastOrgCode(), null, queryBillCacilateDto.getReceiveDate());
    			
	    //(2) 价格
		//根据出发部门Code,到达目的地获取
    	//（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
    	List<ProductPriceDto> priceInfoList = this.queryCuurentPublishPriceDetailInfo(
    			queryBillCacilateDto.getOriginalOrgCode(), queryBillCacilateDto.getLastOrgCode(), queryBillCacilateDto.getReceiveDate(), PricingConstants.VALUATION_TYPE_PRICING);
    	
		//(3) 增值服务
    	List<ValueAddDto>  valueAddDtoList = this.queryCuurentPublishValueAddDetailInfoForPX(
    			queryBillCacilateDto.getOriginalOrgCode(), queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(),queryBillCacilateDto.getProductCode());
		List<PublishPriceEntity> voList = new ArrayList<PublishPriceEntity>();

		//根据startDeptNo获取组织名字
		String startName = null;
		if(SqlUtil.loadCache){			
    		CommonOrgEntity startCommonOrgEntity = pricingOrgDao.queryOrgByCode(queryBillCacilateDto.getOriginalOrgCode(), FossConstants.ACTIVE);
    		
    		if(startCommonOrgEntity != null) {
    			startName = startCommonOrgEntity.getName();
    		}
		}else{
		    startName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(queryBillCacilateDto.getOriginalOrgCode());			
		}
		//合并价格和时效信息		
		encapulatePublishPriceForPX(effectInfoList, priceInfoList, voList,valueAddDtoList, queryBillCacilateDto.getAgentDeptName(), startName,queryBillCacilateDto);
		LOGGER.info("queryPublishPriceDetailForPX  endTime >>"+ new Date());
		//返回结果集合
		return voList;
    }
    
    private List<ValueAddDto> queryCuurentPublishValueAddDetailInfo(
    	    String deptNo, String arrvieNo,  Date billDate) {
    		//判断是否有业务时间
    		//如果没有业务时间NEW新值
    		Date currentDateTime = null;
    		if (billDate == null) {
    		    currentDateTime = new Date();
    		} else {
    		    currentDateTime = billDate;
    		}
        	// 根据出发部门编号定位价格逻辑区域
        	String deptRegionId = regionValueAddService.findRegionOrgByDeptNo(deptNo, currentDateTime, null,PricingConstants.VALUEADD_REGION);
    		if(StringUtil.isEmpty(deptRegionId)) {
    		    return null;
    		}
    		// 根据到达部门编号定位价格逻辑区域
        	String arrvieRegionId = regionValueAddService.findRegionOrgByDeptNo(arrvieNo, currentDateTime,null, PricingConstants.VALUEADD_REGION);
    		if(StringUtil.isEmpty(arrvieRegionId)) {
    		    return null;
    		}
    		//查询增值服务信息
    		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = new QueryBillCacilateValueAddDto(); 
    		queryBillCacilateValueAddDto.setDeptRegionId(deptRegionId);
    		queryBillCacilateValueAddDto.setArrvRegionId(arrvieRegionId);
    		queryBillCacilateValueAddDto.setReceiveDate(currentDateTime);
    		queryBillCacilateValueAddDto.setActive(FossConstants.ACTIVE);
    		queryBillCacilateValueAddDto.setPricingEntryCode(PricingConstants.PriceEntityConstants.PRICING_CODE_SH);
    		
    		Map<String, List<ResultProductPriceDto>> resultMap =  pricingValueAddedService.siftValueAddRuleService(queryBillCacilateValueAddDto);
    		List<ResultProductPriceDto> resultProductPriceDtoList = resultMap.get("base");
    		List<ResultProductPriceDto> resultOtherProductPriceDtoList = resultMap.get("other");
    		
    		if (CollectionUtils.isEmpty(resultProductPriceDtoList) && CollectionUtils.isEmpty(resultOtherProductPriceDtoList)) {
    			return null;
    		}
    		
    		List<ValueAddDto>  valueAddDtos = null;
    		/**
    		 * 基础增值
    		 */
    		if(CollectionUtils.isNotEmpty(resultProductPriceDtoList)){
    			List<Long> minFees = new ArrayList<Long>();
    			for (ResultProductPriceDto resultProductPriceDto : resultProductPriceDtoList) {
    				minFees.add(resultProductPriceDto.getMinFee());
    			}
    			Collections.sort(minFees);
    			valueAddDtos = new ArrayList<ValueAddDto>();
    			for (ResultProductPriceDto resultProductPriceDto : resultProductPriceDtoList) {
    					ValueAddDto valueAddDto = new ValueAddDto();
    					//设置产品信息
    					valueAddDto.setProductCode(resultProductPriceDto.getProductCode());
    					//出发区域ID
    					valueAddDto.setDeptRegionId(resultProductPriceDto.getDeptRegionId());
    					//到达区域ID
    					valueAddDto.setArrvRegionId(resultProductPriceDto.getArrvRegionId());
    					//实际费用
    					valueAddDto.setCaculateFee(BigDecimal.valueOf(resultProductPriceDto.getMinFee()/NumberConstants.NUMBER_100));
    					valueAddDtos.add(valueAddDto);
    			}
    		}
    	
    		if(CollectionUtils.isEmpty(resultOtherProductPriceDtoList)){
    			return valueAddDtos;
    		}
    		
    		/**
    		 * 特殊增值
    		 */
    		if(CollectionUtils.isNotEmpty(resultOtherProductPriceDtoList)){
    			List<Long> minFees = new ArrayList<Long>();
    			for (ResultProductPriceDto resultProductPriceDto : resultOtherProductPriceDtoList) {
    				minFees.add(resultProductPriceDto.getMinFee());
    			}
    			Collections.sort(minFees);
    			valueAddDtos = new ArrayList<ValueAddDto>();
    			for (ResultProductPriceDto resultProductPriceDto : resultOtherProductPriceDtoList) {
    					ValueAddDto valueAddDto = new ValueAddDto();
    					//设置产品信息
    					valueAddDto.setProductCode(resultProductPriceDto.getProductCode());
    					//出发区域ID
    					valueAddDto.setDeptRegionId(resultProductPriceDto.getDeptRegionId());
    					//到达区域ID
    					valueAddDto.setArrvRegionId(resultProductPriceDto.getArrvRegionId());
    					//实际费用
    					valueAddDto.setCaculateFee(BigDecimal.valueOf(resultProductPriceDto.getMinFee()/NumberConstants.NUMBER_100));
    					valueAddDtos.add(valueAddDto);
    			}
    		}
    		return valueAddDtos;
        }
    
    /**
     * 偏线增值服务查询
     * @author WangQianJin
     * @param deptNo
     * @param arrvieNo
     * @param billDate
     * @return
     */
    private List<ValueAddDto> queryCuurentPublishValueAddDetailInfoForPX(
    	    String deptNo, String arrvieNo,  Date billDate, String productCode) {
    		//判断是否有业务时间
    		//如果没有业务时间NEW新值
    		Date currentDateTime = null;
    		if (billDate == null) {
    		    currentDateTime = new Date();
    		} else {
    		    currentDateTime = billDate;
    		}
        	// 根据出发部门编号定位价格逻辑区域
        	String deptRegionId = regionValueAddService.findRegionOrgByDeptNo(deptNo, currentDateTime, null,PricingConstants.VALUEADD_REGION);
    		if(StringUtil.isEmpty(deptRegionId)) {
    		    return null;
    		}
    		// 根据到达部门编号定位价格逻辑区域
        	String arrvieRegionId = regionValueAddService.findRegionOrgByDeptNo(arrvieNo, currentDateTime,null, PricingConstants.VALUEADD_REGION);
    		if(StringUtil.isEmpty(arrvieRegionId)) {
    		    return null;
    		}
    		//查询增值服务信息
    		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = new QueryBillCacilateValueAddDto(); 
    		queryBillCacilateValueAddDto.setDeptRegionId(deptRegionId);
    		queryBillCacilateValueAddDto.setArrvRegionId(arrvieRegionId);
    		queryBillCacilateValueAddDto.setReceiveDate(currentDateTime);
    		queryBillCacilateValueAddDto.setActive(FossConstants.ACTIVE);
    		queryBillCacilateValueAddDto.setPricingEntryCode(PricingConstants.PriceEntityConstants.PRICING_CODE_SH);
    		//运输性质
    		queryBillCacilateValueAddDto.setProductCode(productCode);
    		Map<String, List<ResultProductPriceDto>> resultMap =  pricingValueAddedService.siftValueAddRuleService(queryBillCacilateValueAddDto);
    		List<ResultProductPriceDto> resultProductPriceDtoList = resultMap.get("base");
    		List<ResultProductPriceDto> resultOtherProductPriceDtoList = resultMap.get("other");
    		
    		if (CollectionUtils.isEmpty(resultProductPriceDtoList) && CollectionUtils.isEmpty(resultOtherProductPriceDtoList)) {
    			return null;
    		}
    		
    		List<ValueAddDto>  valueAddDtos = null;
    		/**
    		 * 基础增值
    		 */
    		if(CollectionUtils.isNotEmpty(resultProductPriceDtoList)){
    			List<Long> minFees = new ArrayList<Long>();
    			for (ResultProductPriceDto resultProductPriceDto : resultProductPriceDtoList) {
    				minFees.add(resultProductPriceDto.getMinFee());
    			}
    			Collections.sort(minFees);
    			valueAddDtos = new ArrayList<ValueAddDto>();
    			for (ResultProductPriceDto resultProductPriceDto : resultProductPriceDtoList) {
    					ValueAddDto valueAddDto = new ValueAddDto();
    					//设置产品信息
    					valueAddDto.setProductCode(resultProductPriceDto.getProductCode());
    					//出发区域ID
    					valueAddDto.setDeptRegionId(resultProductPriceDto.getDeptRegionId());
    					//到达区域ID
    					valueAddDto.setArrvRegionId(resultProductPriceDto.getArrvRegionId());
    					//实际费用
    					valueAddDto.setCaculateFee(BigDecimal.valueOf(resultProductPriceDto.getMinFee()/NumberConstants.NUMBER_100));
    					valueAddDtos.add(valueAddDto);
    			}
    		}
    	
    		if(CollectionUtils.isEmpty(resultOtherProductPriceDtoList)){
    			return valueAddDtos;
    		}
    		
    		/**
    		 * 特殊增值
    		 */
    		if(CollectionUtils.isNotEmpty(resultOtherProductPriceDtoList)){
    			List<Long> minFees = new ArrayList<Long>();
    			for (ResultProductPriceDto resultProductPriceDto : resultOtherProductPriceDtoList) {
    				minFees.add(resultProductPriceDto.getMinFee());
    			}
    			Collections.sort(minFees);
    			valueAddDtos = new ArrayList<ValueAddDto>();
    			for (ResultProductPriceDto resultProductPriceDto : resultOtherProductPriceDtoList) {
    					ValueAddDto valueAddDto = new ValueAddDto();
    					//设置产品信息
    					valueAddDto.setProductCode(resultProductPriceDto.getProductCode());
    					//出发区域ID
    					valueAddDto.setDeptRegionId(resultProductPriceDto.getDeptRegionId());
    					//到达区域ID
    					valueAddDto.setArrvRegionId(resultProductPriceDto.getArrvRegionId());
    					//实际费用
    					valueAddDto.setCaculateFee(BigDecimal.valueOf(resultProductPriceDto.getMinFee()/NumberConstants.NUMBER_100));
    					valueAddDtos.add(valueAddDto);
    			}
    		}
    		return valueAddDtos;
    }
    
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 方法（二）
     * 
     * @Description: 根据出发、到达城市Code查询公布价,PDA专用
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-25 下午2:06:45
     * 
     * @param startCityCode
     * 
     * @param destinationCityCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
//    public List<PublicPriceDto> queryPublishPriceDetailByCity(
//			String startCityCode, String destinationCityCode, Date billDate) {
//    	List<String> startDeptList = this.getStartDeptListByDistrict(startCityCode);
//    	List<String> arrvDeptList = this.getArrvDeptListByDistrict(destinationCityCode);
//    	if(CollectionUtils.isEmpty(startDeptList) || CollectionUtils.isEmpty(arrvDeptList)) {
//    		return null;
//    	}
//    	//根据始发和目的网点查询和封装区域数据
//    	Map<String, Map> areaMap = buildUpStartAndArrvArea(startDeptList, arrvDeptList);
//    	//起始时效区域
//    	Map<String, List<String>> effectiveStartRegionMap = areaMap.get("effectiveStartRegionMap");
//    	//目的时效区域
//    	Map<String, List<String>> effectiveArrvRegionMap = areaMap.get("effectiveArrvRegionMap");
//    	//起始时效区域名称
//    	Map<String, String> effectiveStartRegionNameMap = areaMap.get("effectiveStartRegionNameMap");
//    	//目的时效区域名称
//    	Map<String, String> effectiveArrvRegionNameMap = areaMap.get("effectiveArrvRegionNameMap");
//    	//起始价格区域
//    	Map<String, List<String>> priceStartRegionMap = areaMap.get("priceStartRegionMap"); 
//    	//目的价格区域
//    	Map<String, List<String>> priceArrvRegionMap = areaMap.get("priceArrvRegionMap");
////    	//起始价格区域名称
////    	Map<String, String> priceStartRegionNameMap = areaMap.get("priceStartRegionNameMap"); 
////    	//目的价格区域名称
////    	Map<String, String> priceArrvRegionNameMap = areaMap.get("priceArrvRegionNameMap");
//    	//起始空运价格区域
//    	Map<String, List<String>> priceStartRegionAirMap = areaMap.get("priceStartRegionAirMap"); 
//    	//目的空运价格区域
//    	Map<String, List<String>> priceArrvRegionAirMap = areaMap.get("priceArrvRegionAirMap");
//    	//起始空运价格区域名称
//    	Map<String, String> priceStartRegionAirNameMap = areaMap.get("priceStartRegionAirNameMap"); 
//    	//目的空运价格区域名称
//    	Map<String, String> priceArrvRegionAirNameMap = areaMap.get("priceArrvRegionAirNameMap");
//    	
//    	Set priceStartRegion = new HashSet();
//    	Set priceArrvRegion = new HashSet();
//    	
//    	//合并始发价格区域和空运价格区域
//    	priceStartRegion.addAll(priceStartRegionMap.keySet());
//    	priceStartRegion.addAll(priceStartRegionAirMap.keySet());
//    	//合并目的价格区域和空运价格区域
//    	priceArrvRegion.addAll(priceArrvRegionMap.keySet());
//    	priceArrvRegion.addAll(priceArrvRegionAirMap.keySet());
//		
//		List<PublicPriceDto> voList = null;
//		if(CollectionUtils.isNotEmpty(startDeptList) && CollectionUtils.isNotEmpty(arrvDeptList)) {
//			voList = new ArrayList<PublicPriceDto>();
//			// (1) 时效
//			// 根据出发部门Code，到达目的地获取（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
//			List<EffectivePlanDto> effectInfoList = effectivePlanDetailService.queryEffectivePlanDetailListByCondition(
//					effectiveStartRegionMap.keySet(), effectiveArrvRegionMap.keySet(), null, billDate);
//			// (2) 价格
//			// 根据出发部门Code,到达目的地获取（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
//			List<ProductPriceDto> priceInfoList = this.queryCurrentPublishPriceByRegionIdsForPickUp(
//					priceStartRegion, priceArrvRegion, PricingConstants.VALUATION_TYPE_PRICING,
//					billDate);
//			// 根据destinationCode获取组织名字
//			encapulatePublicPrice(effectInfoList, priceInfoList, voList);
//		}
//
//		if(CollectionUtils.isNotEmpty(voList) && CollectionUtils.isNotEmpty(startDeptList) && CollectionUtils.isNotEmpty(arrvDeptList)) {
//			for (int i = 0; i < voList.size(); i++) {
//				PublicPriceDto publicPriceDto = voList.get(i);
//				publicPriceDto.setId(UUIDUtils.getUUID());
//				//产品为汽运的情况
//				if (!StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
//					publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
//					publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
//					publicPriceDto.setStartDeptCodes(effectiveStartRegionMap.get(publicPriceDto.getDeptRegionId()));
//					publicPriceDto.setDeptCodes(effectiveArrvRegionMap.get(publicPriceDto.getArrvRegionId()));
//				//产品为空运的情况
//				} else {
//					publicPriceDto.setDeptRegionName(priceStartRegionAirNameMap.get(publicPriceDto.getDeptRegionId()));
//					publicPriceDto.setArrvRegionName(priceArrvRegionAirNameMap.get(publicPriceDto.getArrvRegionId()));
//					publicPriceDto.setStartDeptCodes(priceStartRegionAirMap.get(publicPriceDto.getDeptRegionId()));
//					publicPriceDto.setDeptCodes(priceArrvRegionAirMap.get(publicPriceDto.getArrvRegionId()));
//				}
//			}
//			return voList;
//		} else {
//			return voList;
//		}
//	}
    public List<PublicPriceDto> queryPublishPriceDetailByCity(
			String startCityCode, String destinationCityCode, Date billDate) {
    	//检验数据
    	if(StringUtils.isEmpty(startCityCode)) {
    		return null;
    	}
    	if(StringUtils.isEmpty(destinationCityCode)) {
    		return null;
    	}
    	//根据始发行政区域查询和封装区域数据
    	Map<String, Map> startAreaMap = buildUpDistrictRegion(startCityCode);
		if(startAreaMap.size() < 1) {
			districtRegionService.addDistrictRegion(startCityCode);
			startAreaMap = buildUpDistrictRegion(startCityCode);
			if(startAreaMap.size() < 1) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(startCityCode);
				if(administrativeRegionsEntity != null) {
					if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
						if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
							startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							if(startAreaMap.size() < 1) {
								districtRegionService.addDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
								startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							}
						}
					}
				}
			}
		}
		if(startAreaMap.size() < 1) {
			return null;
		}
    	//根据目的行政区域查询和封装区域数据
    	Map<String, Map> arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
    	if(arrvAreaMap.size() < 1) {
			districtRegionService.addDistrictRegion(destinationCityCode);
			arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
			if(arrvAreaMap.size() < 1) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(destinationCityCode);
				if(administrativeRegionsEntity != null) {
					if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
						if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
							arrvAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							if(arrvAreaMap.size() < 1) {
								districtRegionService.addDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
								arrvAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							}
						}
					}
				}
			}
		}
		if(arrvAreaMap.size() < 1) {
			return null;
		}
    	// 起始时效区域
		Map<String, List<String>> effectiveStartRegionMap = startAreaMap.get("effectiveRegionMap");
		// 目的时效区域
		Map<String, List<String>> effectiveArrvRegionMap = arrvAreaMap.get("effectiveRegionMap");
		// 起始时效区域名称
		Map<String, String> effectiveStartRegionNameMap = startAreaMap.get("effectiveRegionNameMap");
		// 目的时效区域名称
		Map<String, String> effectiveArrvRegionNameMap = arrvAreaMap.get("effectiveRegionNameMap");
		// 起始价格区域
		Map<String, List<String>> priceStartRegionMap = startAreaMap.get("priceRegionMap");
		// 目的价格区域
		Map<String, List<String>> priceArrvRegionMap = arrvAreaMap.get("priceRegionArriveMap");
		 //起始价格区域名称
		Map<String, String> priceStartRegionNameMap = startAreaMap.get("priceRegionNameMap");
		 //目的价格区域名称
		Map<String, String> priceArrvRegionNameMap = arrvAreaMap.get("priceRegionArriveNameMap");
		// 起始空运价格区域
		Map<String, List<String>> priceStartRegionAirMap = startAreaMap.get("priceRegionAirMap");
		// 目的空运价格区域
		Map<String, List<String>> priceArrvRegionAirMap = arrvAreaMap.get("priceRegionAirMap");
		// 起始空运价格区域名称
		Map<String, String> priceStartRegionAirNameMap = startAreaMap.get("priceRegionAirNameMap");
		// 目的空运价格区域名称
		Map<String, String> priceArrvRegionAirNameMap = arrvAreaMap.get("priceRegionAirNameMap");
		/**
		 * author  yangkang
		 * 门到门  场到场 价格区域(排除其他精准大票的运输性质)
		 * 
		 */
		//始发区域信息
		Map<String, List<String>> priceBigRegionMap = startAreaMap.get("priceBigRegionMap");
		Map<String, String> priceBigRegionNameMap = startAreaMap.get("priceBigRegionNameMap");
		//到达区域信息
		Map<String, List<String>> priceBigRegionArriveMap = arrvAreaMap.get("priceBigRegionArriveMap");
		Map<String, String> priceBigRegionArriveNameMap = arrvAreaMap.get("priceBigRegionArriveNameMap");
		
		//定义始发价格区域合并SET,用于合并汽运价格区域和空运价格区域
		Set priceStartRegion = new HashSet();
		//定义目的价格区域合并SET,用于合并汽运价格区域和空运价格区域
		Set priceArrvRegion = new HashSet();
		// 合并始发汽运价格区域
		if(priceStartRegionMap != null && priceStartRegionMap.size() > 0) {
			priceStartRegion.addAll(priceStartRegionMap.keySet());
		}
		// 合并始发空运价格区域
		if(priceStartRegionAirMap != null && priceStartRegionAirMap.size() > 0) {
			priceStartRegion.addAll(priceStartRegionAirMap.keySet());
		}
		// 合并目的汽运价格区域
		if(priceArrvRegionMap != null && priceArrvRegionMap.size() > 0) {
			priceArrvRegion.addAll(priceArrvRegionMap.keySet());
		}
		// 合并目的空运价格区域
		if(priceArrvRegionAirMap != null && priceArrvRegionAirMap.size() > 0) {
			priceArrvRegion.addAll(priceArrvRegionAirMap.keySet());
		}
		//合并门到门 场到场价格出发区域
		if(priceBigRegionMap != null && priceBigRegionMap.size() > 0) {
			priceStartRegion.addAll(priceBigRegionMap.keySet());
		}
		//合并门到门 场到场价格目的区域
		if(priceBigRegionArriveMap != null && priceBigRegionArriveMap.size() > 0) {
			priceArrvRegion.addAll(priceBigRegionArriveMap.keySet());
		}
		// (1) 时效
		// 根据出发部门Code，到达目的地获取（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
		List<EffectivePlanDto> effectInfoList= null;
		if(effectiveStartRegionMap != null && effectiveArrvRegionMap != null) {
			effectInfoList = effectivePlanDetailService.queryEffectivePlanDetailListByCondition(
					effectiveStartRegionMap.keySet(), effectiveArrvRegionMap.keySet(), null, billDate);
		}
		// (2) 价格
		// 根据出发部门Code,到达目的地获取（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
		List<ProductPriceDto> priceInfoList = null;
		if(CollectionUtils.isNotEmpty(priceStartRegion) && CollectionUtils.isNotEmpty(priceArrvRegion)) {
			priceInfoList = this.queryCurrentPublishPriceByRegionIdsForPickUp(
					priceStartRegion, priceArrvRegion, PricingConstants.VALUATION_TYPE_PRICING, billDate);
		}

		// 根据destinationCode获取组织名字
		List<PublicPriceDto> voList = null;
		if(CollectionUtils.isNotEmpty(effectInfoList) && CollectionUtils.isNotEmpty(priceInfoList)) {
			voList = new ArrayList<PublicPriceDto>();
			encapulatePublicPrice(effectInfoList, priceInfoList, voList);
		}
		if(CollectionUtils.isNotEmpty(voList)) {
			for (int i = 0; i < voList.size(); i++) {
				PublicPriceDto publicPriceDto = voList.get(i);
				publicPriceDto.setId(UUIDUtils.getUUID());
				//产品为汽运的情况
				if (!StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
					//过滤出门到门 场到场的产品进行处理
					if(StringUtil.equals(publicPriceDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR)
							||StringUtil.equals(publicPriceDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD)){	
						
						if(StringUtils.isNotEmpty(publicPriceDto.getDeptRegionId())) {
							publicPriceDto.setDeptRegionName(priceBigRegionNameMap.get(publicPriceDto.getDeptRegionId()));
						}
						if(StringUtils.isNotEmpty(publicPriceDto.getArrvRegionId())) {
							publicPriceDto.setArrvRegionName(priceBigRegionArriveNameMap.get(publicPriceDto.getArrvRegionId()));
						}
						publicPriceDto.setStartDeptCodes(priceBigRegionMap.get(publicPriceDto.getDeptRegionId()));
						publicPriceDto.setDeptCodes(priceBigRegionArriveMap.get(publicPriceDto.getArrvRegionId()));
					}else{
						if(StringUtils.isNotEmpty(publicPriceDto.getDeptRegionId())) {
							publicPriceDto.setDeptRegionName(priceStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
							if(StringUtils.isEmpty(publicPriceDto.getDeptRegionName())) {
								publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
							}
						}
						if(StringUtils.isNotEmpty(publicPriceDto.getArrvRegionId())) {
							publicPriceDto.setArrvRegionName(priceArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
							if(StringUtils.isEmpty(publicPriceDto.getArrvRegionName())) {
								publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
							}
						}
						publicPriceDto.setStartDeptCodes(effectiveStartRegionMap.get(publicPriceDto.getDeptRegionId()));
						publicPriceDto.setDeptCodes(effectiveArrvRegionMap.get(publicPriceDto.getArrvRegionId()));
					}
					
				//产品为空运的情况
				} else {
					publicPriceDto.setDeptRegionName(priceStartRegionAirNameMap.get(publicPriceDto.getDeptRegionId()));
					publicPriceDto.setArrvRegionName(priceArrvRegionAirNameMap.get(publicPriceDto.getArrvRegionId()));
					publicPriceDto.setStartDeptCodes(priceStartRegionAirMap.get(publicPriceDto.getDeptRegionId()));
					publicPriceDto.setDeptCodes(priceArrvRegionAirMap.get(publicPriceDto.getArrvRegionId()));
				}
			}
			return voList;
		} else {
			return voList;
		}
	}
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 方法（三）
     * @Description:根据出发城市、目的地、航班类型条件查询公布价
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-7 下午4:57:02
     * 
     * @param startCityCode
     * 
     * @param destinationCountryCode
     * 
     * @param destinationProvinceCode
     * 
     * @param destinationCityCode
     * 
     * @param destinationCountyCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PublicPriceDto> queryPublishPriceDetailByConditionWithFlight(String startCityCode, String destinationCountryCode, String destinationProvinceCode,
			String destinationCityCode, String destinationCountyCode, String destinationAddress, String flightSort,Date billDate) {
		List<PublicPriceDto> voList = null;
		// 根据始发网点查询和封装区域数据
		Map<String, Map> startAreaMap = null;
		// 根据目的网点查询和封装区域数据
		Map<String, Map> arrvAreaMap = null;
		try {
			this.validateAreaInfo(startCityCode, destinationCountryCode, destinationProvinceCode, destinationCityCode);
			if (billDate == null) {
				billDate = new Date();
			}
			
			startAreaMap = buildUpDistrictRegion(startCityCode);
			//抽取方法以便调用
			startAreaMap = getStringMap(startCityCode, startAreaMap);
			if(startAreaMap.size() < 1) {
				return null;
			}
			
			List<String> unifiedDeptList = null;
			String provinceName = null;
			String cityName = null;
			String countyName = null;
			// 根据省CODe查询省名称
			provinceName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationProvinceCode);
			// 根据市CODe查询市名称
			cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationCityCode);
			// 根据区县CODe查询区县名称
			countyName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationCountyCode);
			// 根据出发城市CODe查询其下的网点信息
			List<String> arrvDeptList = null;
			if (StringUtil.isNotBlank(destinationAddress)) {
				// 从GIS获取营业网点
				try {
					unifiedDeptList = this.getCompositeStationCodes(provinceName, cityName, countyName, destinationAddress, gisUrl);
				} catch (Exception e) {
					LOGGER.error("输入参数为:" + provinceName + "|" + cityName + "|" + countyName + "|" + destinationAddress + "|" + gisUrl);
					LOGGER.error("调用GIS接口出现错误:" + e);
				}
				if (CollectionUtils.isNotEmpty(unifiedDeptList)) {
					arrvDeptList = new ArrayList<String>();
					List<CommonOrgEntity> orgList = pricingOrgDao.queryOrgByBatchCodes(null, unifiedDeptList);
					List<CommonOrgEntity> otherOrgList = pricingOrgDao.queryOrgByBatchCodes(unifiedDeptList, null);
					if (CollectionUtils.isNotEmpty(orgList)) {
						for (CommonOrgEntity commonOrgEntity : orgList) {
							arrvDeptList.add(commonOrgEntity.getCode());
						}
					}
					if (CollectionUtils.isNotEmpty(otherOrgList)) {
						for (CommonOrgEntity commonOrgEntity : otherOrgList) {
							arrvDeptList.add(commonOrgEntity.getCode());
						}
					}
				}
				if(arrvDeptList != null){
					arrvAreaMap = buildUpDeptRegion(arrvDeptList);
				}
			} else {
				// 城市或区县查询营业网点
				if (StringUtil.isNotBlank(destinationCountyCode)) {
					arrvAreaMap = buildUpDistrictRegion(destinationCountyCode);
					if(arrvAreaMap.size() < 1) {
						districtRegionService.addDistrictRegion(destinationCountyCode);
						arrvAreaMap = buildUpDistrictRegion(destinationCountyCode);
						if(arrvAreaMap.size() < 1) {
							arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
							if(arrvAreaMap.size() < 1) {
								districtRegionService.addDistrictRegion(destinationCityCode);
								arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
							}
						}
					}
				} else {
					arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
					if(arrvAreaMap.size() < 1) {
						districtRegionService.addDistrictRegion(destinationCityCode);
						arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
					}
				}
			}
			if(arrvAreaMap.size() < 1) {
				return null;
			}
			// 起始时效区域
			Map<String, List<String>> effectiveStartRegionMap = startAreaMap.get("effectiveRegionMap");
			// 目的时效区域
			Map<String, List<String>> effectiveArrvRegionMap = arrvAreaMap.get("effectiveRegionMap");
			// 起始时效区域名称
			Map<String, String> effectiveStartRegionNameMap = startAreaMap.get("effectiveRegionNameMap");
			// 目的时效区域名称
			Map<String, String> effectiveArrvRegionNameMap = arrvAreaMap.get("effectiveRegionNameMap");
			// 起始价格区域
			Map<String, List<String>> priceStartRegionMap = startAreaMap.get("priceRegionMap");
			// 目的价格区域
//			Map<String, List<String>> priceArrvRegionMap = arrvAreaMap.get("priceRegionMap");
			Map<String, List<String>> priceRegionArriveMap = arrvAreaMap.get("priceRegionArriveMap");
			//起始价格区域名称
			Map<String, String> priceStartRegionNameMap = startAreaMap.get("priceRegionNameMap");
			 //目的价格区域名称
			Map<String, String> priceArrvRegionNameMap = arrvAreaMap.get("priceRegionArriveNameMap");
			// 起始空运价格区域
			Map<String, List<String>> priceStartRegionAirMap = startAreaMap.get("priceRegionAirMap");
			// 目的空运价格区域
			Map<String, List<String>> priceArrvRegionAirMap = arrvAreaMap.get("priceRegionAirMap");
			// 起始空运价格区域名称
			Map<String, String> priceStartRegionAirNameMap = startAreaMap.get("priceRegionAirNameMap");
			// 目的空运价格区域名称
			Map<String, String> priceArrvRegionAirNameMap = arrvAreaMap.get("priceRegionAirNameMap");

			/**
			 *新增精准大票区域信息
			 *
			 */
			// 精准大票价格出发区域
			Map<String, List<String>> priceStartRegionBigMap = startAreaMap.get("priceBigRegionMap");
			// 精准大票价格到达区域
			Map<String, List<String>> priceArriveRegionBigMap = arrvAreaMap.get("priceBigRegionArriveMap");
			// 精准大票价格出发区域名称
			Map<String, String> priceStartRegionBigNameMap = startAreaMap.get("priceBigRegionNameMap");
			// 精准大票价格到达区域名称
			Map<String, String> priceArriveRegionBigNameMap = arrvAreaMap.get("priceBigRegionArriveNameMap");
			/**
			 *新增精准包裹区域信息
			 *
			 */
			// 精准包裹价格出发区域
			Map<String, List<String>> priceStartRegionEcMap = startAreaMap.get("priceEcRegionMap");
			// 精准包裹价格到达区域
			Map<String, List<String>> priceArriveRegionEcMap = arrvAreaMap.get("priceEcRegionArriveMap");
			// 精准包裹价格出发区域名称
			Map<String, String> priceStartRegionEcNameMap = startAreaMap.get("priceEcRegionNameMap");
			// 精准包裹价格到达区域名称
			Map<String, String> priceArriveRegionEcNameMap = arrvAreaMap.get("priceEcRegionArriveNameMap");
			
			//定义始发价格区域合并SET,用于合并汽运价格区域和空运价格区域
			Set priceStartECRegion = new HashSet();
			//定义目的价格区域合并SET,用于合并汽运价格区域和空运价格区域
			Set priceArrvECRegion = new HashSet();
			
			if(priceStartRegionEcMap != null && priceStartRegionEcMap.size() > 0 && priceArriveRegionEcMap != null && priceArriveRegionEcMap.size()>0) {
				priceStartECRegion.addAll(priceStartRegionEcMap.keySet());
				priceArrvECRegion.addAll(priceArriveRegionEcMap.keySet());
			}
			
			//定义始发价格区域合并SET,用于合并汽运价格区域和空运价格区域
			Set priceStartRegion = new HashSet();
			//定义目的价格区域合并SET,用于合并汽运价格区域和空运价格区域
			Set priceArrvRegion = new HashSet();
			// 合并始发汽运价格区域
			if(priceStartRegionMap != null && priceStartRegionMap.size() > 0 && priceRegionArriveMap != null && priceRegionArriveMap.size() > 0) {
				priceStartRegion.addAll(priceStartRegionMap.keySet());
				priceArrvRegion.addAll(priceRegionArriveMap.keySet());
			}
			// 合并始发空运价格区域
			if(priceStartRegionAirMap != null && priceStartRegionAirMap.size() > 0 && priceArrvRegionAirMap != null && priceArrvRegionAirMap.size() > 0) {
				priceStartRegion.addAll(priceStartRegionAirMap.keySet());
				priceArrvRegion.addAll(priceArrvRegionAirMap.keySet());
			}
			//合并始发精准大票价格区域   
			if(priceStartRegionBigMap != null && priceStartRegionBigMap.size() > 0 && priceArriveRegionBigMap != null && priceArriveRegionBigMap.size()>0) {
				priceStartRegion.addAll(priceStartRegionBigMap.keySet());
				priceArrvRegion.addAll(priceArriveRegionBigMap.keySet());
			}
		
			// (1) 时效
			// 根据出发部门Code，到达目的地获取（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
			List<EffectivePlanDto> effectInfoList = null;
			if(effectiveStartRegionMap != null && effectiveArrvRegionMap != null && StringUtils.isEmpty(flightSort)) {
				effectInfoList = effectivePlanDetailService.queryEffectivePlanDetailListByCondition(effectiveStartRegionMap.keySet(),
						effectiveArrvRegionMap.keySet(), null, billDate);
			}
			// (2) 价格
			// 根据出发部门Code,到达目的地获取（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
			List<ProductPriceDto> priceInfoList = null;
 			if(CollectionUtils.isNotEmpty(priceStartRegion) && CollectionUtils.isNotEmpty(priceArrvRegion)) {
				//priceInfoList = this.queryCurrentPublishPriceByRegionIds(priceStartRegion, priceArrvRegion,
				//		PricingConstants.VALUATION_TYPE_PRICING, billDate);
				priceInfoList = queryCurrentPublishPriceByRegionIdsAndflightSort(priceStartRegion, priceArrvRegion,
						PricingConstants.VALUATION_TYPE_PRICING, billDate, flightSort, priceStartECRegion, priceArrvECRegion);
			}
			//组装时效和价格公布价
			if(CollectionUtils.isNotEmpty(effectInfoList) || CollectionUtils.isNotEmpty(priceInfoList)) {
				voList = new ArrayList<PublicPriceDto>();
				// 根据destinationCode获取组织名字
				assemblePublicPrice(effectInfoList, priceInfoList, voList);
			}
			//封装相关信息
			if (CollectionUtils.isNotEmpty(voList)) {
				for (int i = 0; i < voList.size(); i++) {
					PublicPriceDto publicPriceDto = voList.get(i);
					publicPriceDto.setId(UUIDUtils.getUUID());
					// 产品为空运的情况
					if (StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)
							) {
						publicPriceDto.setDeptPriceRegionName(priceStartRegionAirNameMap!=null?priceStartRegionAirNameMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setArrvPriceRegionName(priceArrvRegionAirNameMap!=null?priceArrvRegionAirNameMap.get(publicPriceDto.getArrvPriceRegionId()):null);
						
						publicPriceDto.setPriceStartDeptCodes(priceStartRegionAirMap!=null?priceStartRegionAirMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setPriceArrvDeptCodes(priceArrvRegionAirMap!=null?priceArrvRegionAirMap.get(publicPriceDto.getArrvPriceRegionId()):null);
					//产品为精准大票的情况
					} else if(StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG)||
							StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG)||
							StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG)||
							StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG)||
							StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR)||
							StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD)&&
							StringUtils.isNotEmpty(publicPriceDto.getDeptPriceRegionId())&&StringUtils.isNotEmpty(publicPriceDto.getArrvPriceRegionId())){
						
						publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap!=null?effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()):null);
						publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap!=null?effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()):null);
						publicPriceDto.setDeptPriceRegionName(priceStartRegionBigNameMap!=null?priceStartRegionBigNameMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setArrvPriceRegionName(priceArriveRegionBigNameMap!=null?priceArriveRegionBigNameMap.get(publicPriceDto.getArrvPriceRegionId()):null);

						publicPriceDto.setStartDeptCodes(effectiveStartRegionMap!=null?effectiveStartRegionMap.get(publicPriceDto.getDeptRegionId()):null);
						publicPriceDto.setDeptCodes(effectiveArrvRegionMap!=null?effectiveArrvRegionMap.get(publicPriceDto.getArrvRegionId()):null);
						
						publicPriceDto.setPriceStartDeptCodes(priceStartRegionBigMap!=null?priceStartRegionBigMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setPriceArrvDeptCodes(priceArriveRegionBigMap!=null?priceArriveRegionBigMap.get(publicPriceDto.getArrvPriceRegionId()):null);
						
					//产品为精准包裹的情况	
					}else if(StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PCP)){
						publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap!=null?effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()):null);
						publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap!=null?effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()):null);
						publicPriceDto.setDeptPriceRegionName(priceStartRegionEcNameMap!=null?priceStartRegionEcNameMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setArrvPriceRegionName(priceArriveRegionEcNameMap!=null?priceArriveRegionEcNameMap.get(publicPriceDto.getArrvPriceRegionId()):null);

						publicPriceDto.setStartDeptCodes(effectiveStartRegionMap!=null?effectiveStartRegionMap.get(publicPriceDto.getDeptRegionId()):null);
						publicPriceDto.setDeptCodes(effectiveArrvRegionMap!=null?effectiveArrvRegionMap.get(publicPriceDto.getArrvRegionId()):null);
						
						publicPriceDto.setPriceStartDeptCodes(priceStartRegionEcMap!=null?priceStartRegionEcMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setPriceArrvDeptCodes(priceArriveRegionEcMap!=null?priceArriveRegionEcMap.get(publicPriceDto.getArrvPriceRegionId()):null);
						
					}else {// 产品为其他汽运的情况		
						publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap!=null?effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()):null);
						publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap!=null?effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()):null);
						
						publicPriceDto.setDeptPriceRegionName(priceStartRegionNameMap!=null?priceStartRegionNameMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setArrvPriceRegionName(priceArrvRegionNameMap!=null?priceArrvRegionNameMap.get(publicPriceDto.getArrvPriceRegionId()):null);

						publicPriceDto.setStartDeptCodes(effectiveStartRegionMap!=null?effectiveStartRegionMap.get(publicPriceDto.getDeptRegionId()):null);
						publicPriceDto.setDeptCodes(effectiveArrvRegionMap!=null?effectiveArrvRegionMap.get(publicPriceDto.getArrvRegionId()):null);
						
						publicPriceDto.setPriceStartDeptCodes(priceStartRegionMap!=null?priceStartRegionMap.get(publicPriceDto.getDeptPriceRegionId()):null);
						publicPriceDto.setPriceArrvDeptCodes(priceRegionArriveMap!=null?priceRegionArriveMap.get(publicPriceDto.getArrvPriceRegionId()):null);
					}
				}
				return voList;
			} else {
				return voList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("queryPublishPriceDetailByCondition  >>" + e);
		}
		return voList;
	}

	//第一层抽取
	@SuppressWarnings("rawtypes")
	private Map<String, Map> getStringMap(String startCityCode,  Map<String, Map> startAreaMap) {
		if(startAreaMap.size() < 1) {
            districtRegionService.addDistrictRegion(startCityCode);
            startAreaMap = buildUpDistrictRegion(startCityCode);
            if(startAreaMap.size() < 1) {
                AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(startCityCode);
				startAreaMap = getStringMapNext(startAreaMap, administrativeRegionsEntity);
			}
        }
		return startAreaMap;
	}

	//第二层抽取
	@SuppressWarnings("rawtypes")
	private Map<String, Map> getStringMapNext(Map<String, Map> startAreaMap, AdministrativeRegionsEntity administrativeRegionsEntity) {
		if(administrativeRegionsEntity != null) {
            if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
                if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
                    startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
                    if(startAreaMap.size() < 1) {
                        districtRegionService.addDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
                        startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
                    }
                }
            }
        }
		return startAreaMap;
	}

	/**
     * 方法（三）
     * @Description: 根据出发城市、目的地条件查询公布价
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-7 下午4:57:02
     * 
     * @param startCityCode
     * 
     * @param destinationCountryCode
     * 
     * @param destinationProvinceCode
     * 
     * @param destinationCityCode
     * 
     * @param destinationCountyCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     * @param string 
     */
	@Override
	public List<PublicPriceDto> queryPublishPriceDetailByCondition(String startCityCode, String destinationCountryCode, String destinationProvinceCode,
			String destinationCityCode, String destinationCountyCode, String destinationAddress, Date billDate) {
				return this.queryPublishPriceDetailByConditionWithFlight(startCityCode,destinationCountryCode,destinationProvinceCode,destinationCityCode,destinationCountyCode,destinationAddress,null,billDate);
	}
	/**
	 * @功能 添加航班類型查询条件
	 * @date 2013-8-10 15:21:20
	 * @param startRegionIdSet
	 * @param arrvRegionIdSet
	 * @param type
	 * @param billDate
	 * @param flightSort
	 * @param priceArriveRegionEcMap 
	 * @param priceStartRegionEcMap 
	 * @return
	 */
@SuppressWarnings("rawtypes")
private List<ProductPriceDto> queryCurrentPublishPriceByRegionIdsAndflightSort(Set<String> startRegionIdSet,
		Set<String> arrvRegionIdSet, String type, Date billDate, String flightSort, Set<String> priceStartECRegionSet, Set<String> priceArrvECRegionSet) {
	List<ProductPriceDto> list = new ArrayList<ProductPriceDto>();
	Date currentDateTime = null;
	if (billDate == null) {
		currentDateTime = new Date();
	} else {
		currentDateTime = billDate;
	}

	if (CollectionUtils.isEmpty(startRegionIdSet) && CollectionUtils.isEmpty(arrvRegionIdSet) && CollectionUtils.isEmpty(priceStartECRegionSet) && CollectionUtils.isEmpty(priceArrvECRegionSet)) {
		return null;
	}
	
	List<String> startRegionIdList = new ArrayList<String>();
	for (Iterator iterator = startRegionIdSet.iterator(); iterator.hasNext();) {
		String startRegionId = (String) iterator.next();
		startRegionIdList.add(startRegionId);
	}
	
	List<String> arrvRegionIdList = new ArrayList<String>();
	for (Iterator iterator = arrvRegionIdSet.iterator(); iterator.hasNext();) {
		String arrvRegionId = (String) iterator.next();
		arrvRegionIdList.add(arrvRegionId);
	}
	
	List<String> startRegionIdECList = new ArrayList<String>();
	for (Iterator iterator = priceStartECRegionSet.iterator(); iterator.hasNext();) {
		String startRegionId = (String) iterator.next();
		startRegionIdECList.add(startRegionId);
	}
	
	List<String> arrvRegionIdECList = new ArrayList<String>();
	for (Iterator iterator = priceArrvECRegionSet.iterator(); iterator.hasNext();) {
		String arrvRegionId = (String) iterator.next();
		arrvRegionIdECList.add(arrvRegionId);
	}
	

	List<PublishPriceDto> publishPriceDtos = publishPriceDao.queryPublishPriceByRegionIdsAndFlightSort(startRegionIdList,
			arrvRegionIdList, type, FossConstants.ACTIVE, currentDateTime, flightSort, startRegionIdECList, arrvRegionIdECList);
	if (CollectionUtils.isNotEmpty(publishPriceDtos)) {
		List<PublishPriceDto> pcpList=new ArrayList<PublishPriceDto>(); 
		
		ListIterator<PublishPriceDto> listIterator = publishPriceDtos.listIterator();

		while(listIterator.hasNext()){
			PublishPriceDto priceDto = listIterator.next();
			if(StringUtils.equals(priceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20012)){
				//把是精准包裹的数据放入集合中 
				pcpList.add(priceDto);
				listIterator.remove();
			}
		}
		
		Collections.sort(pcpList, new Comparator<PublishPriceDto>() {
			@Override
			public int compare(PublishPriceDto t, PublishPriceDto t1) {
				return t.getLeftRange().compareTo(t1.getLeftRange());
			}
		});
		publishPriceDtos.addAll(pcpList);
		
		List<PublishPriceDto> priceDtos = appendSections(this.boxHeaveAndLight(publishPriceDtos));
		if (CollectionUtils.isNotEmpty(priceDtos)) {
			list = this.boxProductPriceDto(priceDtos);
		}
	}
	if (CollectionUtils.isNotEmpty(list)) {
		list = this.boxCatalogName(list);
	}
	return list;
	}
  
	/**
	   * 对汽运计价分段信息的处理
	   * 
	   * @author 219413-Luomengxiang   
	   * 
	   * @date  2014-11-19
	   */
  private List<PublishPriceDto> appendSections(List<PublishPriceDto> publishPriceDtoList){
	  List<PublishPriceDto> dtoList=new ArrayList<PublishPriceDto>();
	  if(publishPriceDtoList != null){
		  for(PublishPriceDto dto : publishPriceDtoList){
			   //获取规则Id
			    String valuationId=dto.getPricingValuationId();
			    //根据轻重货明细信息的规则id找到轻重货分段的信息
			    List<PopPriceDetailSectionEntity> popPriceDetailSectionEntityList=
			    		popPriceDetailSectionDao.selectByValuationId(valuationId);
			    if(CollectionUtils.isNotEmpty(popPriceDetailSectionEntityList)){
			    	List<PopPublicPriceDto> popPublicPriceDtoList=new ArrayList<PopPublicPriceDto>();
	    			PopPublicPriceDto dto1=new PopPublicPriceDto();
	    			PopPublicPriceDto dto2=new PopPublicPriceDto();
	    			PopPublicPriceDto dto3=new PopPublicPriceDto();
	    			PopPublicPriceDto dto4=new PopPublicPriceDto();
	    			PopPublicPriceDto dto5=new PopPublicPriceDto();
	    			PopPublicPriceDto dto6=new PopPublicPriceDto();
			    for(PopPriceDetailSectionEntity entities : popPriceDetailSectionEntityList){
			    	if(String.valueOf(ONE).equals(entities.getSectionID())){
			    		dto1.setSectionId(String.valueOf(ONE));
                        if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
                      	   dto1.setHeavyPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                        }else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
                      	  dto1.setLightPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                        } 
              }else  if(String.valueOf(TWO).equals(entities.getSectionID())){
				  dto2.setSectionId(String.valueOf(TWO));
              	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
                  	  dto2.setHeavyPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              		  dto2.setHeavyCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
                        	dto2.setLightPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100))); 
                  		   dto2.setLightCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                  }       
              }else if(String.valueOf(THREE).equals(entities.getSectionID())){
				  dto3.setSectionId(String.valueOf(THREE));
              	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
              		  dto3.setHeavyPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              		  dto3.setHeavyCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
                    	  dto3.setLightPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    	  dto3.setLightCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    } 
            }else if(String.valueOf(FOUR).equals(entities.getSectionID())){
				  dto4.setSectionId(String.valueOf(FOUR));
              	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
              		  dto4.setHeavyPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              		  dto4.setHeavyCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
                    	  dto4.setLightPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    	  dto4.setLightCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    }          
            }else if(String.valueOf(FIVE).equals(entities.getSectionID())){
				  dto5.setSectionId(String.valueOf(FIVE));
              	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
              		  dto5.setHeavyPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              		  dto5.setHeavyCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
                    	  dto5.setLightPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    	  dto5.setLightCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    }       
            }else if(String.valueOf(SIX).equals(entities.getSectionID())){
				  dto6.setSectionId(String.valueOf(SIX));
              	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
              		  dto6.setHeavyPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              		  dto6.setHeavyCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
              	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
                    	  dto6.setLightPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    	  dto6.setLightCriticalValue(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
                    }          
		     }
	    } 
			    //对轻、重货的上限、下限的处理   219413-Luomengxiang
			    if(dto2.getSectionId() != null){
			    	dto1.setHeavyRange("0 - "+dto2.getHeavyCriticalValue());
			    	dto1.setLightRange("0 - "+dto2.getLightCriticalValue());
			    	if(dto3.getSectionId() ==null){
			    		dto2.setHeavyRange(dto2.getHeavyCriticalValue()+" - 以上");
			    		dto2.setLightRange(dto2.getLightCriticalValue()+" - 以上");
			    	}
			    }
			    if(dto3.getSectionId() != null){
			    	dto2.setHeavyRange(dto2.getHeavyCriticalValue()+" - "+dto3.getHeavyCriticalValue());
			    	dto2.setLightRange(dto2.getLightCriticalValue()+" - "+dto3.getLightCriticalValue());
			    	if(dto4.getSectionId() ==null){
			    		dto3.setHeavyRange(dto3.getHeavyCriticalValue()+" - 以上");
			    		dto3.setLightRange(dto3.getLightCriticalValue()+" - 以上");
			    	}
			    }
			    if(dto4.getSectionId() != null){
			    	dto3.setHeavyRange(dto3.getHeavyCriticalValue()+" - "+dto4.getHeavyCriticalValue());
			    	dto3.setLightRange(dto3.getLightCriticalValue()+" - "+dto4.getLightCriticalValue());
			    	if(dto5.getSectionId() ==null){
			    		dto4.setHeavyRange(dto4.getHeavyCriticalValue()+" - 以上");
			    		dto4.setLightRange(dto4.getLightCriticalValue()+" - 以上");
			    	}
			    }
			    if(dto5.getSectionId() != null){
			    	dto4.setHeavyRange(dto4.getHeavyCriticalValue()+" - "+dto5.getHeavyCriticalValue());
			    	dto4.setLightRange(dto4.getLightCriticalValue()+" - "+dto5.getLightCriticalValue());
			    	if(dto6.getSectionId() ==null){
			    		dto5.setHeavyRange(dto5.getHeavyCriticalValue()+" - 以上");
			    		dto5.setLightRange(dto5.getLightCriticalValue()+" - 以上");
			    	}
			    }
			    if(dto6.getSectionId() != null){
			    	dto5.setHeavyRange(dto5.getHeavyCriticalValue()+" - "+dto6.getHeavyCriticalValue());
			    	dto5.setLightRange(dto5.getLightCriticalValue()+" - "+dto6.getLightCriticalValue());
			    	dto6.setHeavyRange(dto6.getHeavyCriticalValue()+" - 以上");
			    	dto6.setLightRange(dto6.getLightCriticalValue()+" - 以上");
			    }
			    //将分段信息插入到List   219413-Luomengxiang
			    if(dto1.getSectionId() != null){
			    	popPublicPriceDtoList.add(dto1);
			    }
			    if(dto2.getSectionId() != null){
			    	popPublicPriceDtoList.add(dto2);
			    }
			    if(dto3.getSectionId() != null){
			    	popPublicPriceDtoList.add(dto3);
			    }
			    if(dto4.getSectionId() != null){
			    	popPublicPriceDtoList.add(dto4);
			    }
			    if(dto5.getSectionId() != null){
			    	popPublicPriceDtoList.add(dto5);
			    }
			    if(dto6.getSectionId() != null){
			    	popPublicPriceDtoList.add(dto6);
			    }
			    dto.setPopPublishPriceDtoList(popPublicPriceDtoList);
		  }
			  dtoList.add(dto);
		  }
	}
	  return dtoList;
  }
//	public List<PublicPriceDto> queryPublishPriceDetailByCondition(String startCityCode, String destinationCountryCode, String destinationProvinceCode,
//			String destinationCityCode, String destinationCountyCode, String destinationAddress, Date billDate) {
//		List<PublicPriceDto> voList = null;
//		try {
//			LOGGER.info("queryPublishPriceDetailByCondition 3 startTime >>" + new Date());
//			Map<String, Date> timeMap = new HashMap<String, Date>();
//			timeMap.put("1startQuery", new Date());
//			this.validateAreaInfo(startCityCode, destinationCountryCode, destinationProvinceCode, destinationCityCode);
//			if (billDate == null) {
//				billDate = new Date();
//			}
//			List<String> unifiedDeptList = null;
//			String provinceName = null;
//			String cityName = null;
//			String countyName = null;
//			// 根据省CODe查询省名称
//			provinceName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationProvinceCode);
//			// 根据市CODe查询市名称
//			cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationCityCode);
//			// 根据区县CODe查询区县名称
//			countyName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationCountyCode);
//			// 根据出发城市CODe查询其下的网点信息
//			List<String> startDeptList = this.getStartDeptListByDistrict(startCityCode);
//			if (CollectionUtils.isEmpty(startDeptList)) {
//				return null;
//			}
//			timeMap.put("2startDepts", new Date());
//			List<String> arrvDeptList = null;
//			if (StringUtil.isNotBlank(destinationAddress)) {
//				// 从GIS获取营业网点
//				try {
//					unifiedDeptList = this.getCompositeStationCodes(provinceName, cityName, countyName, destinationAddress, gisUrl);
//				} catch (Exception e) {
//					LOGGER.error("输入参数为:" + provinceName + "|" + cityName + "|" + countyName + "|" + destinationAddress + "|" + gisUrl);
//					LOGGER.error("调用GIS接口出现错误:" + e);
//				}
//				if (CollectionUtils.isNotEmpty(unifiedDeptList)) {
//					arrvDeptList = new ArrayList<String>();
//					List<CommonOrgEntity> orgList = pricingOrgDao.queryOrgByBatchCodes(null, unifiedDeptList);
//					List<CommonOrgEntity> otherOrgList = pricingOrgDao.queryOrgByBatchCodes(unifiedDeptList, null);
//					if (CollectionUtils.isNotEmpty(orgList)) {
//						for (CommonOrgEntity commonOrgEntity : orgList) {
//							arrvDeptList.add(commonOrgEntity.getCode());
//						}
//					}
//					if (CollectionUtils.isNotEmpty(otherOrgList)) {
//						for (CommonOrgEntity commonOrgEntity : otherOrgList) {
//							arrvDeptList.add(commonOrgEntity.getCode());
//						}
//					}
//					// for (int i = 0; i < unifiedDeptList.size(); i++) {
//					// OrgAdministrativeInfoEntity orgAdministrativeInfoEntity =
//					// orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(unifiedDeptList.get(i));
//					// if(orgAdministrativeInfoEntity != null &&
//					// StringUtil.isNotBlank(orgAdministrativeInfoEntity.getCode()))
//					// {
//					// arrvDeptList.add(orgAdministrativeInfoEntity.getCode());
//					// }
//					// }
//				}
//				// 城市或区县查询营业网点
//			} else {
//				if (StringUtil.isNotBlank(destinationCountyCode)) {
//					arrvDeptList = this.getArrvDeptListByDistrict(destinationCountyCode);
//				} else {
//					arrvDeptList = this.getArrvDeptListByDistrict(destinationCityCode);
//				}
//			}
//			if (CollectionUtils.isEmpty(arrvDeptList)) {
//				return null;
//			}
//			timeMap.put("3arrvDepts", new Date());
//			// 根据始发和目的网点查询和封装区域数据
//			Map<String, Map> areaMap = buildUpStartAndArrvArea(startDeptList, arrvDeptList);
//			timeMap.put("4buildUpStartAndArrvArea", new Date());
//			// 起始时效区域
//			Map<String, List<String>> effectiveStartRegionMap = areaMap.get("effectiveStartRegionMap");
//			// 目的时效区域
//			Map<String, List<String>> effectiveArrvRegionMap = areaMap.get("effectiveArrvRegionMap");
//			// 起始时效区域名称
//			Map<String, String> effectiveStartRegionNameMap = areaMap.get("effectiveStartRegionNameMap");
//			// 目的时效区域名称
//			Map<String, String> effectiveArrvRegionNameMap = areaMap.get("effectiveArrvRegionNameMap");
//			// 起始价格区域
//			Map<String, List<String>> priceStartRegionMap = areaMap.get("priceStartRegionMap");
//			// 目的价格区域
//			Map<String, List<String>> priceArrvRegionMap = areaMap.get("priceArrvRegionMap");
//			// //起始价格区域名称
//			// Map<String, String> priceStartRegionNameMap =
//			// areaMap.get("priceStartRegionNameMap");
//			// //目的价格区域名称
//			// Map<String, String> priceArrvRegionNameMap =
//			// areaMap.get("priceArrvRegionNameMap");
//			// 起始空运价格区域
//			Map<String, List<String>> priceStartRegionAirMap = areaMap.get("priceStartRegionAirMap");
//			// 目的空运价格区域
//			Map<String, List<String>> priceArrvRegionAirMap = areaMap.get("priceArrvRegionAirMap");
//			// 起始空运价格区域名称
//			Map<String, String> priceStartRegionAirNameMap = areaMap.get("priceStartRegionAirNameMap");
//			// 目的空运价格区域名称
//			Map<String, String> priceArrvRegionAirNameMap = areaMap.get("priceArrvRegionAirNameMap");
//			Set priceStartRegion = new HashSet();
//			Set priceArrvRegion = new HashSet();
//			// 合并始发价格区域和空运价格区域
//			priceStartRegion.addAll(priceStartRegionMap.keySet());
//			priceStartRegion.addAll(priceStartRegionAirMap.keySet());
//			// 合并目的价格区域和空运价格区域
//			priceArrvRegion.addAll(priceArrvRegionMap.keySet());
//			priceArrvRegion.addAll(priceArrvRegionAirMap.keySet());
//			if (CollectionUtils.isNotEmpty(startDeptList) && CollectionUtils.isNotEmpty(arrvDeptList)) {
//				voList = new ArrayList<PublicPriceDto>();
//				timeMap.put("5startSelect", new Date());
//				// (1) 时效
//				// 根据出发部门Code，到达目的地获取（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
//				List<EffectivePlanDto> effectInfoList = effectivePlanDetailService.queryEffectivePlanDetailListByCondition(effectiveStartRegionMap.keySet(),
//						effectiveArrvRegionMap.keySet(), null, billDate);
//				timeMap.put("6effectiveSelect", new Date());
//				// (2) 价格
//				// 根据出发部门Code,到达目的地获取（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
//				List<ProductPriceDto> priceInfoList = this.queryCurrentPublishPriceByRegionIds(priceStartRegion, priceArrvRegion,
//						PricingConstants.VALUATION_TYPE_PRICING, billDate);
//				timeMap.put("7priceSelect", new Date());
//				// 根据destinationCode获取组织名字
//				assemblePublicPrice(effectInfoList, priceInfoList, voList);
//				timeMap.put("8encapulatePublicPrice", new Date());
//			}
//			if (CollectionUtils.isNotEmpty(voList) && CollectionUtils.isNotEmpty(startDeptList) && CollectionUtils.isNotEmpty(arrvDeptList)) {
//				for (int i = 0; i < voList.size(); i++) {
//					PublicPriceDto publicPriceDto = voList.get(i);
//					publicPriceDto.setId(UUIDUtils.getUUID());
//					// 产品为汽运的情况
//					if (!StringUtil.equals(publicPriceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
//						publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
//						publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
//
//						publicPriceDto.setStartDeptCodes(effectiveStartRegionMap.get(publicPriceDto.getDeptRegionId()));
//						publicPriceDto.setDeptCodes(effectiveArrvRegionMap.get(publicPriceDto.getArrvRegionId()));
//						// 产品为空运的情况
//					} else {
//						publicPriceDto.setDeptRegionName(priceStartRegionAirNameMap.get(publicPriceDto.getDeptRegionId()));
//						publicPriceDto.setArrvRegionName(priceArrvRegionAirNameMap.get(publicPriceDto.getArrvRegionId()));
//
//						publicPriceDto.setStartDeptCodes(priceStartRegionAirMap.get(publicPriceDto.getDeptRegionId()));
//						publicPriceDto.setDeptCodes(priceArrvRegionAirMap.get(publicPriceDto.getArrvRegionId()));
//					}
//				}
//				timeMap.put("9endQuery", new Date());
//				// for (Iterator iterator = timeMap.keySet().iterator();
//				// iterator.hasNext();) {
//				// String keyName = (String) iterator.next();
//				// LOGGER.info(keyName+ ">>" + timeMap.get(keyName));
//				// }
//				LOGGER.info("queryPublishPriceDetailByCondition 3 endTime >>" + new Date());
//				return voList;
//			} else {
//				return voList;
//			}
//		} catch (Exception e) {
//			LOGGER.info("queryPublishPriceDetailByCondition  >>" + e);
//		}
//		return voList;
//	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * <p>
	 * Description:组装公布价<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-11-2
	 * 
	 * @param effectInfoList
	 * 
	 * @param priceInfoList
	 * 
	 * @param voList
	 * 
	 * @param arrvName
	 * 
	 * @param startName
	 * 
	 * void
	 */
	@SuppressWarnings("unused")
	private void encapulatePublishPrice(List<EffectivePlanDto> effectInfoList, List<ProductPriceDto> priceInfoList,
			List<PublishPriceEntity> voList, String arrvName, String startName) {
		PublishPriceEntity publishPrice = null;
		if(CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectivePlanDto> effectivePlanDtos = new ArrayList<EffectivePlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
					for(int j = 0; j<priceInfoList.size(); j++){
						//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
					    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
				    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
				    	publishPrice = new PublishPriceEntity();
						//抽取方法调用
						addPriceToList(voList, arrvName, startName, publishPrice, effectivePlanDtos, effectiveDto, priceDto, effParentCode);
					}
				}
				//时效产品信息作为产品名显示，价格不显示空白展出
				//时效区域部分未匹配的情况
				if(effectivePlanDtos.size() != effectInfoList.size()) {
					for(int i=0; i<effectInfoList.size(); i++){
						EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
						if(effectivePlanDtos.contains(effectiveDto)) {
							continue;
						} 
						publishPrice = new PublishPriceEntity();
						publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
						publishPrice.setStartDept(startName);
						publishPrice.setArrvRegionId(effectiveDto.getDeptRegionId());
						publishPrice.setArrvRegionName(arrvName);
						publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
						publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
						publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
						publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
						publishPrice.setPickupTime(effectiveDto.getArriveTime());
						if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, effectiveDto.getLongOrShort())) {
							publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
						} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, effectiveDto.getLongOrShort())) {
							publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
						}
						publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//						publishPrice.setArriveTime(String.valueOf(effectiveDto.getMinTime()) + "-" + 
//								String.valueOf(effectiveDto.getMaxTime()));	
					    publishPrice.setProductItemName(effectiveDto.getProductName());
						publishPrice.setProductItemCode(effectiveDto.getProductCode());
						voList.add(publishPrice);
					}
				}
				//时效区域全部未匹配的情况
			} else {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
					publishPrice = new PublishPriceEntity();
					publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
					publishPrice.setStartDept(startName);
					publishPrice.setArrvRegionId(effectiveDto.getDeptRegionId());
					publishPrice.setArrvRegionName(arrvName);
					publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
					publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
					publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
					publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
					publishPrice.setPickupTime(effectiveDto.getArriveTime());
					if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, effectiveDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
					} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, effectiveDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
					}
					publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//					publishPrice.setArriveTime(String.valueOf(effectiveDto.getMinTime()) + "-" + 
//							String.valueOf(effectiveDto.getMaxTime()));	
					publishPrice.setProductItemName(effectiveDto.getProductName());
					publishPrice.setProductItemCode(effectiveDto.getProductCode());
					voList.add(publishPrice);
				}
			}
		}
		//筛选空运价格
		if(CollectionUtils.isNotEmpty(priceInfoList)) {
			for(int j = 0; j<priceInfoList.size(); j++){
		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
		    	//比较时效的父产品code和价格产品code是否相等
		    	publishPrice = new PublishPriceEntity();
		    	//判断是否为空运，空运价格目前没有时效，只有价格信息
		    	if (StringUtil.equals(priceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
		    		publishPrice.setStartDept(startName);
		    		publishPrice.setArrvRegionName(arrvName);
		    		publishPrice.setLightPrice(priceDto.getLightFeeRate());
					publishPrice.setHeavyPrice(priceDto.getHeavyFeeRate());
					publishPrice.setMinFee(priceDto.getMinFee());
					publishPrice.setProductItemCode(priceDto.getProductCode());
					publishPrice.setProductItemName(priceDto.getProductName()+"("+priceDto.getGoodsTypeName()+")");
					if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, priceDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
					} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, priceDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
					}
					publishPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
					publishPrice.setFlightShiftNo(priceDto.getFlightShiftNo());
					publishPrice.setCentralizePickup(priceDto.getCentralizePickup());
					voList.add(publishPrice);
		    	}
			}
		}
	}

	private void addPriceToList(List<PublishPriceEntity> voList, String arrvName, String startName, PublishPriceEntity publishPrice, List<EffectivePlanDto> effectivePlanDtos, EffectivePlanDto effectiveDto, ProductPriceDto priceDto, String effParentCode) {
		if(effParentCode.equals(priceDto.getProductCode())){
            publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
            publishPrice.setStartDept(startName);
            publishPrice.setArrvRegionId(effectiveDto.getDeptRegionId());
            publishPrice.setArrvRegionName(arrvName);
            publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
            publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
            publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
            publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
            //publishPrice.setPickupTime(effectiveDto.getArriveTime());
            /**
             * 设置取货时间
             */
            if(effectiveDto.getArriveTime()!=null && !"".equals(effectiveDto.getArriveTime())){
                if(effectiveDto.getArriveTime().length()>2){
                    String hours=effectiveDto.getArriveTime().substring(0,2);
                    String minutes=effectiveDto.getArriveTime().substring(2,effectiveDto.getArriveTime().length());
                    String pickupTime=hours+":"+minutes;
                    publishPrice.setPickupTime(pickupTime);
                }

            }
            if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, priceDto.getLongOrShort())) {
                publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
            } else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, priceDto.getLongOrShort())) {
                publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
            }
            publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" +
                    effectiveDto.getMaxTime());
            publishPrice.setLightPrice(priceDto.getLightFeeRate());
            publishPrice.setHeavyPrice(priceDto.getHeavyFeeRate());
            publishPrice.setMinFee(priceDto.getMinFee());
            publishPrice.setProductItemName(effectiveDto.getProductName()+"("+priceDto.getGoodsTypeName()+")");
            publishPrice.setProductItemCode(effectiveDto.getProductCode());
            publishPrice.setCentralizePickup(priceDto.getCentralizePickup());
            effectivePlanDtos.add(effectiveDto);
            voList.add(publishPrice);
        }
	}

	private void encapulatePublishPrice(List<EffectivePlanDto> effectInfoList, List<ProductPriceDto> priceInfoList,
			List<PublishPriceEntity> voList,List<ValueAddDto> valueAddDtos, String arrvName, String startName) {
		PublishPriceEntity publishPrice = null;
		if(CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectivePlanDto> effectivePlanDtos = new ArrayList<EffectivePlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
					for(int j = 0; j<priceInfoList.size(); j++){
						//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
					    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
				    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
				    	publishPrice = new PublishPriceEntity();
				    	if(effParentCode.equals(priceDto.getProductCode())){					
							publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
							publishPrice.setStartDept(startName);
							publishPrice.setStartDeptId(effectiveDto.getDeptRegionId());
							publishPrice.setArrvRegionName(arrvName);
							publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
							publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
							publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
							publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
							//publishPrice.setPickupTime(effectiveDto.getArriveTime());
							/**
							 * 设置取货时间
							 */
							if(effectiveDto.getArriveTime()!=null && !"".equals(effectiveDto.getArriveTime())){
								if(effectiveDto.getArriveTime().length()>2){
									String hours=effectiveDto.getArriveTime().substring(0,2);
									String minutes=effectiveDto.getArriveTime().substring(2,effectiveDto.getArriveTime().length());
									String pickupTime=hours+":"+minutes;
									publishPrice.setPickupTime(pickupTime);
								}
								
							}
							if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, priceDto.getLongOrShort())) {
								publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
							} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, priceDto.getLongOrShort())) {
								publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
							}
							if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20006, priceDto.getProductCode()) 
									||StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20007, priceDto.getProductCode())
									||StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20008,priceDto.getProductCode())
			    					||StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20009,priceDto.getProductCode())){
								publishPrice.setLightFeeRateStr(priceDto.getLightFeeRateStr());
								publishPrice.setHeavyFeeRateStr(priceDto.getHeavyFeeRateStr());
							} else {
								
								publishPrice.setLightFeeRateStr(priceDto.getLightFeeRateStr());
								publishPrice.setHeavyFeeRateStr(priceDto.getHeavyFeeRateStr());
								publishPrice.setLightPrice(priceDto.getLightFeeRate());
								publishPrice.setHeavyPrice(priceDto.getHeavyFeeRate());
								//新增计价分段明细 list   219413-Luomengxiang    2014-11-26
								publishPrice.setPopPublicPriceDtoList(priceDto.getPopPublicPriceDtoList());
							}
							publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" +effectiveDto.getMaxTime());	
							publishPrice.setMinFee(priceDto.getMinFee());
							publishPrice.setProductItemName(effectiveDto.getProductName()+"("+priceDto.getGoodsTypeName()+")");
							publishPrice.setProductItemCode(effectiveDto.getProductCode());
                            //判断精准电商情况下,不显示是否接货  add by wangfeng
							publishPrice.setCentralizePickup(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20012, priceDto.getProductCode()) ? null : priceDto.getCentralizePickup());

							//抽取方法
							addDateToList(valueAddDtos, publishPrice, effParentCode);
							effectivePlanDtos.add(effectiveDto);
							voList.add(publishPrice);
						}
				    }
				}
				
				//时效产品信息作为产品名显示，价格不显示空白展出
				//时效区域部分未匹配的情况
				if(effectivePlanDtos.size() != effectInfoList.size()) {
					for(int i=0; i<effectInfoList.size(); i++){
						EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
						if(effectivePlanDtos.contains(effectiveDto)) {
							continue;
						} 
						publishPrice = new PublishPriceEntity();
						publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
						publishPrice.setStartDept(startName);
						publishPrice.setStartDeptId(effectiveDto.getDeptRegionId());
						publishPrice.setArrvRegionName(arrvName);
						publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
						publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
						publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
						publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
						publishPrice.setPickupTime(effectiveDto.getArriveTime());
						if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, effectiveDto.getLongOrShort())) {
							publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
						} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, effectiveDto.getLongOrShort())) {
							publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
						}
						publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//						publishPrice.setArriveTime(String.valueOf(effectiveDto.getMinTime()) + "-" + 
//								String.valueOf(effectiveDto.getMaxTime()));	
					    publishPrice.setProductItemName(effectiveDto.getProductName());
						publishPrice.setProductItemCode(effectiveDto.getProductCode());
						voList.add(publishPrice);
					}
				}
				//时效区域全部未匹配的情况
			} else {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
					publishPrice = new PublishPriceEntity();
					publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
					publishPrice.setStartDept(startName);
					publishPrice.setStartDeptId(effectiveDto.getDeptRegionId());
					publishPrice.setArrvRegionName(arrvName);
					publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
					publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
					publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
					publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
					publishPrice.setPickupTime(effectiveDto.getArriveTime());
					if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, effectiveDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
					} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, effectiveDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
					}
					publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//					publishPrice.setArriveTime(String.valueOf(effectiveDto.getMinTime()) + "-" + 
//							String.valueOf(effectiveDto.getMaxTime()));	
					publishPrice.setProductItemName(effectiveDto.getProductName());
					publishPrice.setProductItemCode(effectiveDto.getProductCode());
					voList.add(publishPrice);
				}
			}
		}
		//筛选空运价格
		if(CollectionUtils.isNotEmpty(priceInfoList)) {
			for(int j = 0; j<priceInfoList.size(); j++){
		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
		    	//比较时效的父产品code和价格产品code是否相等
		    	publishPrice = new PublishPriceEntity();
		    	//判断是否为空运，空运价格目前没有时效，只有价格信息
		    	if (StringUtil.equals(priceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
		    		publishPrice.setStartDept(startName);
		    		publishPrice.setArrvRegionName(arrvName);
		    		publishPrice.setLightPrice(priceDto.getLightFeeRate());
					publishPrice.setHeavyPrice(priceDto.getHeavyFeeRate());
					//添加空运价格的字符串形式
					publishPrice.setHeavyFeeRateStr(priceDto.getHeavyFeeRateStr());
					publishPrice.setLightFeeRateStr(priceDto.getLightFeeRateStr());
					
					publishPrice.setMinFee(priceDto.getMinFee());
					publishPrice.setProductItemCode(priceDto.getProductCode());
					if(priceDto.getFlightShiftNo() != null){
						publishPrice.setProductItemName(priceDto.getProductName() + "(" + priceDto.getFlightShiftNo() + ")" +"("+priceDto.getGoodsTypeName()+")");
					}else{
						publishPrice.setProductItemName(priceDto.getProductName()+"("+priceDto.getGoodsTypeName()+")");
					}
					if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, priceDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
					} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, priceDto.getLongOrShort())) {
						publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
					}
					publishPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
					publishPrice.setFlightShiftNo(priceDto.getFlightShiftNo());
					publishPrice.setCentralizePickup(priceDto.getCentralizePickup());
					//zxy 20140509 MANA-1253 start 新增:网点价格查询页面 增加合票方式
					publishPrice.setCombBillTypeCode(priceDto.getCombBillTypeCode());
					publishPrice.setCombBillTypeName(priceDto.getCombBillTypeName());
					//zxy 20140509 MANA-1253 end 新增:网点价格查询页面 增加合票方式
					if(CollectionUtils.isNotEmpty(valueAddDtos)){
						List<BigDecimal> list1 = new ArrayList<BigDecimal>();
						List<BigDecimal> list2 = new ArrayList<BigDecimal>();
						List<BigDecimal> list3 = new ArrayList<BigDecimal>();
						List<BigDecimal> list4 = new ArrayList<BigDecimal>();
						for (ValueAddDto valueAdd : valueAddDtos) {
							LOGGER.info("yhj---送货费起步价");
							if(StringUtils.isEmpty(valueAdd.getProductCode())){
								list4.add(valueAdd.getCaculateFee());
							}else{
								ProductEntity valueAddProduct = productService.getProductByCache(valueAdd.getProductCode(), null);
								if(valueAddProduct.getParentCode().equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)){
									//出发、到达区域
									if(StringUtils.isNotEmpty(valueAdd.getDeptRegionId()) && StringUtils.isNotEmpty(valueAdd.getArrvRegionId())){
										//同一产品，出发、到达 找到最小起步价
										list1.add(valueAdd.getCaculateFee());
									}else if(StringUtils.isNotEmpty(valueAdd.getDeptRegionId()) && StringUtils.isEmpty(valueAdd.getArrvRegionId())){
										//同一产品、出发，到全网 找到最小起步价
										list2.add(valueAdd.getCaculateFee());
									}else if(StringUtils.isEmpty(valueAdd.getDeptRegionId()) && StringUtils.isNotEmpty(valueAdd.getArrvRegionId())){
										//同一产品、全网，到出发 找到最小起步价
										list3.add(valueAdd.getCaculateFee());
									}else{
										list4.add(valueAdd.getCaculateFee());
									}
								}
							}
						}
						//排序
						Collections.sort(list1);
						Collections.sort(list2);
						Collections.sort(list3);
						Collections.sort(list4);
						if(CollectionUtils.isNotEmpty(list1) && list1.size()>0){
							BigDecimal minFee = list1.get(0);
							publishPrice.setDeliveryCharges(minFee);
						}else if(CollectionUtils.isNotEmpty(list2) && list2.size()>0){
							BigDecimal minFee = list2.get(0);
							publishPrice.setDeliveryCharges(minFee);
						}else if(CollectionUtils.isNotEmpty(list3) && list3.size()>0){
							BigDecimal minFee = list3.get(0);
							publishPrice.setDeliveryCharges(minFee);
						}else if(CollectionUtils.isNotEmpty(list4) && list4.size()>0){
							BigDecimal minFee = list4.get(0);
							publishPrice.setDeliveryCharges(minFee);
						}
					}
					voList.add(publishPrice);
		    	}
			}
		}
	}

	private void addDateToList(List<ValueAddDto> valueAddDtos, PublishPriceEntity publishPrice, String effParentCode) {
		if(CollectionUtils.isNotEmpty(valueAddDtos)){
			//当时精准电商时,不设置送货费起步价
			if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_PCP, publishPrice.getProductItemCode())){
				publishPrice.setDeliveryCharges(null);
				return;
			}
			
            List<BigDecimal> list1 = new ArrayList<BigDecimal>();
            List<BigDecimal> list2 = new ArrayList<BigDecimal>();
            List<BigDecimal> list3 = new ArrayList<BigDecimal>();
            List<BigDecimal> list4 = new ArrayList<BigDecimal>();
            for (ValueAddDto valueAdd : valueAddDtos) {
                    LOGGER.info("yhj---送货费起步价");
                    if(StringUtils.isEmpty(valueAdd.getProductCode())){
                        list4.add(valueAdd.getCaculateFee());
                    }else{
                        //相同产品
                        ProductEntity valueAddProduct = productService.getProductByCache(valueAdd.getProductCode(), null);
                        if(valueAddProduct.getParentCode().equals(effParentCode)){
                            //出发、到达区域
                            if(StringUtils.isNotEmpty(valueAdd.getDeptRegionId()) && StringUtils.isNotEmpty(valueAdd.getArrvRegionId())){
                                //同一产品，出发、到达 找到最小起步价
                                list1.add(valueAdd.getCaculateFee());
                            }else if(StringUtils.isNotEmpty(valueAdd.getDeptRegionId()) && StringUtils.isEmpty(valueAdd.getArrvRegionId())){
                                //同一产品、出发，到全网 找到最小起步价
                                list2.add(valueAdd.getCaculateFee());
                            }else if(StringUtils.isEmpty(valueAdd.getDeptRegionId()) && StringUtils.isNotEmpty(valueAdd.getArrvRegionId())){
                                //同一产品、全网，到出发 找到最小起步价
                                list3.add(valueAdd.getCaculateFee());
                            }else{
                                list4.add(valueAdd.getCaculateFee());
                            }
                        }
                    }
            }
            //排序
            Collections.sort(list1);
            Collections.sort(list2);
            Collections.sort(list3);
            Collections.sort(list4);
            if(CollectionUtils.isNotEmpty(list1) && list1.size()>0){
                BigDecimal minFee = list1.get(0);
                publishPrice.setDeliveryCharges(minFee);
            }else if(CollectionUtils.isNotEmpty(list2) && list2.size()>0){
                BigDecimal minFee = list2.get(0);
                publishPrice.setDeliveryCharges(minFee);
            }else if(CollectionUtils.isNotEmpty(list3) && list3.size()>0){
                BigDecimal minFee = list3.get(0);
                publishPrice.setDeliveryCharges(minFee);
            }else if(CollectionUtils.isNotEmpty(list4) && list4.size()>0){
                BigDecimal minFee = list4.get(0);
                publishPrice.setDeliveryCharges(minFee);
            }
        }
	}


	/**
	 * 偏线价格改造
	 * @author WangQianJin
	 * @param effectInfoList
	 * @param priceInfoList
	 * @param voList
	 * @param valueAddDtos
	 * @param arrvName
	 * @param startName
	 */
	private void encapulatePublishPriceForPX(List<EffectivePlanDto> effectInfoList, List<ProductPriceDto> priceInfoList,
			List<PublishPriceEntity> voList,List<ValueAddDto> valueAddDtos, String arrvName, String startName,QueryBillCacilateDto queryBillCacilateDto) {
		PublishPriceEntity publishPrice = null;
		if(CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectivePlanDto> effectivePlanDtos = new ArrayList<EffectivePlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);					
					for(int j = 0; j<priceInfoList.size(); j++){						
						//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
					    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
				    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);
				    	//如果二级产品-精准，则直接循环下一个，偏线不属于精准
						if(ProductEntityConstants.PRICING_PRODUCT_C2_C20001.equals(priceDto.getProductCode())){
							continue;
						}
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
				    	publishPrice = new PublishPriceEntity();
				    	if(effParentCode.equals(priceDto.getProductCode())){					
							publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
							publishPrice.setStartDept(startName);
							publishPrice.setArrvRegionId(effectiveDto.getDeptRegionId());
							publishPrice.setArrvRegionName(arrvName);
							publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
							publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
							publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
							publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
							//publishPrice.setPickupTime(effectiveDto.getArriveTime());
							/**
							 * 设置取货时间
							 */
							if(effectiveDto.getArriveTime()!=null && !"".equals(effectiveDto.getArriveTime())){
								if(effectiveDto.getArriveTime().length()>2){
									String hours=effectiveDto.getArriveTime().substring(0,2);
									String minutes=effectiveDto.getArriveTime().substring(2,effectiveDto.getArriveTime().length());
									String pickupTime=hours+":"+minutes;
									publishPrice.setPickupTime(pickupTime);
								}
								
							}
							if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_L, priceDto.getLongOrShort())) {
								publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_L_NAME);
							} else if(StringUtil.equals(PricingConstants.LONG_OR_SHORT_S, priceDto.getLongOrShort())) {
								publishPrice.setLongOrShort(PricingConstants.LONG_OR_SHORT_S_NAME);
							}
							publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" +effectiveDto.getMaxTime());	
							publishPrice.setLightPrice(priceDto.getLightFeeRate());
							publishPrice.setHeavyPrice(priceDto.getHeavyFeeRate());
							publishPrice.setMinFee(priceDto.getMinFee());
							publishPrice.setProductItemName(effectiveDto.getProductName()+"("+priceDto.getGoodsTypeName()+")");
							publishPrice.setProductItemCode(effectiveDto.getProductCode());
							publishPrice.setCentralizePickup(priceDto.getCentralizePickup());							
							
							if(CollectionUtils.isNotEmpty(valueAddDtos)){
								List<BigDecimal> list1 = new ArrayList<BigDecimal>();
								List<BigDecimal> list2 = new ArrayList<BigDecimal>();
								List<BigDecimal> list3 = new ArrayList<BigDecimal>();
								List<BigDecimal> list4 = new ArrayList<BigDecimal>();
								for (ValueAddDto valueAdd : valueAddDtos) {
									LOGGER.info("yhj---送货费起步价");
									//如果当前是偏线产品
									if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(valueAdd.getProductCode())){
										//出发、到达区域
										if(StringUtils.isNotEmpty(valueAdd.getDeptRegionId()) && StringUtils.isNotEmpty(valueAdd.getArrvRegionId())){
											//同一产品，出发、到达 找到最小起步价
											list1.add(valueAdd.getCaculateFee());
										}else if(StringUtils.isNotEmpty(valueAdd.getDeptRegionId()) && StringUtils.isEmpty(valueAdd.getArrvRegionId())){
											//同一产品、出发，到全网 找到最小起步价
											list2.add(valueAdd.getCaculateFee());
										}else if(StringUtils.isEmpty(valueAdd.getDeptRegionId()) && StringUtils.isNotEmpty(valueAdd.getArrvRegionId())){
											//同一产品、全网，到出发 找到最小起步价
											list3.add(valueAdd.getCaculateFee());
										}else{
											list4.add(valueAdd.getCaculateFee());
										}
									}										
								}
								//排序
								Collections.sort(list1);
								Collections.sort(list2);
								Collections.sort(list3);
								Collections.sort(list4);
								if(CollectionUtils.isNotEmpty(list1) && list1.size()>0){
									BigDecimal minFee = list1.get(0);
									publishPrice.setDeliveryCharges(minFee);
								}else if(CollectionUtils.isNotEmpty(list2) && list2.size()>0){
									BigDecimal minFee = list2.get(0);
									publishPrice.setDeliveryCharges(minFee);
								}else if(CollectionUtils.isNotEmpty(list3) && list3.size()>0){
									BigDecimal minFee = list3.get(0);
									publishPrice.setDeliveryCharges(minFee);
								}else if(CollectionUtils.isNotEmpty(list4) && list4.size()>0){
									BigDecimal minFee = list4.get(0);
									publishPrice.setDeliveryCharges(minFee);
								}
							}
							
							//设置偏线价格
							setterPriceForPX(publishPrice,priceDto,queryBillCacilateDto);							
							//设置偏线时效
							setterEffeceForPX(publishPrice, effectiveDto, queryBillCacilateDto);
							//根据isPxFlag 判断时候显示偏线价格方案
							if(publishPrice.getIsPxFlag()!=null&&publishPrice.getIsPxFlag()){
							//判断是否有外发外场到偏线代理的价格
							if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(publishPrice.getProductItemCode())){
								effectivePlanDtos.add(effectiveDto);
								voList.add(publishPrice);
							}
						}
				    }
				    }
				}				
			}
		}		
	}
	
	/**
	 * 设置偏线时效时间
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-2 10:51:17
	 * @param publishPrice
	 * @param effectiveDto
	 * @param queryBillCacilateDto
	 */
	private void setterEffeceForPX(PublishPriceEntity publishPrice, EffectivePlanDto effectiveDto, QueryBillCacilateDto queryBillCacilateDto) {
		//如果当前是偏线产品
		if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(queryBillCacilateDto.getProductCode())){
			OuterEffectivePlanEntity outerEffectivePlan = outerEffectivePlanService.queryOuterEffectPlanByFieldAndBranch(queryBillCacilateDto.getLastOrgCode(), queryBillCacilateDto.getDestinationOrgCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001, queryBillCacilateDto.getReceiveDate());
			if(outerEffectivePlan != null){
				//转换到达时间格式如：0122转成01:22格式
				revertArriTime(outerEffectivePlan, publishPrice);
				//最小营运时效 ：专线+偏线
				publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()+outerEffectivePlan.getMinTime()));
				//最大营运时效 ：专线+偏线
				publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()+outerEffectivePlan.getMaxTime()));
				//设置营运时间
				publishPrice.setArriveTime(String.valueOf(effectiveDto.getMinTime()+outerEffectivePlan.getMinTime()) + "-" +String.valueOf(effectiveDto.getMaxTime()+outerEffectivePlan.getMaxTime()));	
			}			
		}
	}
	
	/**
	 * //转换到达时间格式如：0122转成01:22格式
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-3 14:31:00
	 * @param outerEffectivePlan
	 * @param publishPrice
	 */
	private void revertArriTime(OuterEffectivePlanEntity outerEffectivePlan, PublishPriceEntity publishPrice) {
		//进行相关格式的转换
		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		//开始进行到达偏线代理网点的时点转换，如果出现异常继续去取数据的原始数据
		Date d;
		//只有时点不为空才能进行转换
		if(StringUtils.isNotEmpty(outerEffectivePlan.getArriveOuterBranchTime())){
			String leaveTime;
			try {
				d = sdf1.parse(outerEffectivePlan.getArriveOuterBranchTime());
				leaveTime = sdf2.format(d);
			} catch (ParseException e) {
				leaveTime = outerEffectivePlan.getArriveOuterBranchTime();
			}
			//客户提货时间
			publishPrice.setPickupTime(leaveTime);
		}
	}

	/**
	 * 设置偏线的价格
	 * @author WangQianJin
	 * @param result
	 */
	private void setterPriceForPX(PublishPriceEntity publishPrice,ProductPriceDto priceDto,QueryBillCacilateDto queryBillCacilateDto){
		//如果当前是偏线产品
		if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(queryBillCacilateDto.getProductCode())){
			OuterPriceEntity outerPriceEntity = outerPriceService.searchOuterPriceByArgument(queryBillCacilateDto.getDestinationOrgCode(),
					queryBillCacilateDto.getLastOrgCode(),queryBillCacilateDto.getProductCode(), queryBillCacilateDto.getReceiveDate());		
			//偏线价格信息
			if(null!=outerPriceEntity){
				//重货价格
				BigDecimal heavyPrice=BigDecimal.ZERO;
				//重货价格2
				BigDecimal heavyPrice2 = BigDecimal.ZERO;
				//重货价格3
				BigDecimal heavyPrice3 = BigDecimal.ZERO;
				//重货价格4
				BigDecimal heavyPrice4 = BigDecimal.ZERO;
				//重货价格5
				BigDecimal heavyPrice5 = BigDecimal.ZERO;
				//重货价格6
				BigDecimal heavyPrice6 = BigDecimal.ZERO;
				//轻货价格
				BigDecimal lightPrice=BigDecimal.ZERO;
				//轻货价格2
				BigDecimal lightPrice2 = BigDecimal.ZERO;
				//轻货价格3
				BigDecimal lightPrice3 = BigDecimal.ZERO;
				//轻货价格4
				BigDecimal lightPrice4 = BigDecimal.ZERO;
				//轻货价格5
				BigDecimal lightPrice5 = BigDecimal.ZERO;
				//轻货价格6
				BigDecimal lightPrice6 = BigDecimal.ZERO;
				//最低一票
				BigDecimal minFee=BigDecimal.ZERO;
				
				/*专线价格*/
				//若分段实体为空则执行原操作   219413-Luomengxiang   2015.1.22
				if(CollectionUtils.isEmpty(priceDto.getPopPublicPriceDtoList())){
				if(priceDto.getHeavyFeeRate()!=null){
					heavyPrice=heavyPrice.add(priceDto.getHeavyFeeRate());
				}
				if(priceDto.getLightFeeRate()!=null){
					lightPrice=lightPrice.add(priceDto.getLightFeeRate());
				}
				if(priceDto.getMinFee()!=null){
					minFee=minFee.add(priceDto.getMinFee());
				}
				/*偏线价格*/
				if(outerPriceEntity.getWeightFeeRate()!=null){
					heavyPrice=heavyPrice.add(outerPriceEntity.getWeightFeeRate());
				}
				if(outerPriceEntity.getVolumeFeeRate()!=null){
					lightPrice=lightPrice.add(outerPriceEntity.getVolumeFeeRate());
				}
				if(outerPriceEntity.getMinFee()!=null){
					minFee=minFee.add(BigDecimal.valueOf(outerPriceEntity.getMinFee()));
				}
				
				/*设置总偏线信息*/
				publishPrice.setProductItemName("汽运偏线");
				publishPrice.setProductItemCode(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
				publishPrice.setArrvRegionName(queryBillCacilateDto.getAgentDeptName());
				//取货时间
				publishPrice.setPickupTime("");
				//长短途	
				publishPrice.setLongOrShort("");
				//航班信息
				publishPrice.setFlightShiftNo("");
				//价格
				publishPrice.setHeavyPrice(heavyPrice);
				publishPrice.setHeavyFeeRateStr(String.valueOf(heavyPrice));
				publishPrice.setLightPrice(lightPrice);
				publishPrice.setLightFeeRateStr(String.valueOf(lightPrice));				
				publishPrice.setMinFee(minFee);
			}
			  //若分段实体不为空   219413-Luomengxiang   2015.1.22
				if(CollectionUtils.isNotEmpty(priceDto.getPopPublicPriceDtoList())){
					//获取到分段信息list
					List<PopPublicPriceDto> popPublicPriceDtoList = 
							priceDto.getPopPublicPriceDtoList();
					//偏线整理list
					List<PopPublicPriceDto> popPriceDtos = new ArrayList<PopPublicPriceDto>();
					//设置6个实体
					PopPublicPriceDto popDto1 = null;
					PopPublicPriceDto popDto2 = null;
					PopPublicPriceDto popDto3 = null;
					PopPublicPriceDto popDto4 = null;
					PopPublicPriceDto popDto5 = null;
					PopPublicPriceDto popDto6 = null;
					for(PopPublicPriceDto popPublicPriceDto : popPublicPriceDtoList){
						//若为价格段1
						popDto1 = new PopPublicPriceDto();
						if(String.valueOf(ONE).equals(popPublicPriceDto.getSectionId())){
							if(priceDto.getHeavyFeeRate()!=null){
								heavyPrice = heavyPrice.add(popPublicPriceDto.getHeavyPrice());
								popDto1.setHeavyPrice(heavyPrice);
							}
							if(priceDto.getLightFeeRate()!=null){
								lightPrice = lightPrice.add(popPublicPriceDto.getLightPrice());
								popDto1.setLightPrice(lightPrice);
							}
							if(priceDto.getMinFee()!=null){
								minFee=minFee.add(priceDto.getMinFee());
							}
							/*偏线价格*/
							if(outerPriceEntity.getWeightFeeRate()!=null){
								heavyPrice = heavyPrice.add(outerPriceEntity.getWeightFeeRate());
								popDto1.setHeavyPrice(heavyPrice);
							}
							if(outerPriceEntity.getVolumeFeeRate()!=null){
								lightPrice = lightPrice.add(outerPriceEntity.getVolumeFeeRate());
								popDto1.setLightPrice(lightPrice);
							}
							if(outerPriceEntity.getMinFee()!=null){
								minFee=minFee.add(BigDecimal.valueOf(outerPriceEntity.getMinFee()));
							}
							//设置分段数
							popDto1.setSectionId(String.valueOf(ONE));
							//获取重货范围
							popDto1.setHeavyRange(popPublicPriceDto.getHeavyRange());
							//获取轻货范围
							popDto1.setLightRange(popPublicPriceDto.getLightRange());
							popPriceDtos.add(popDto1);
							
							/*设置总偏线信息*/
							publishPrice.setProductItemName("汽运偏线");
							publishPrice.setProductItemCode(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
							publishPrice.setArrvRegionName(queryBillCacilateDto.getAgentDeptName());
							//取货时间
							publishPrice.setPickupTime("");
							//长短途	
							publishPrice.setLongOrShort("");
							//航班信息
							publishPrice.setFlightShiftNo("");
							//价格
							publishPrice.setPopPublicPriceDtoList(popPriceDtos);				
							publishPrice.setMinFee(minFee);
						}else if(String.valueOf(TWO).equals(popPublicPriceDto.getSectionId())){
							popDto2 = new PopPublicPriceDto();
							//添加重货价格
							heavyPrice2 = heavyPrice2.add(popPublicPriceDto.getHeavyPrice());
							popDto2.setHeavyPrice(heavyPrice2);
							//添加轻货价格
							lightPrice2 = lightPrice2.add(popPublicPriceDto.getLightPrice());
							popDto2.setLightPrice(lightPrice2);
							
							/*偏线价格*/
							if(outerPriceEntity.getWeightFeeRate()!=null){
								heavyPrice2 = heavyPrice2.add(outerPriceEntity.getWeightFeeRate());
								popDto2.setHeavyPrice(heavyPrice2);
							}
							if(outerPriceEntity.getVolumeFeeRate()!=null){
								lightPrice2 = lightPrice2.add(outerPriceEntity.getVolumeFeeRate());
								popDto2.setLightPrice(lightPrice2);
							}
							//设置分段数
							popDto2.setSectionId(String.valueOf(TWO));
							//重货范围
							popDto2.setHeavyRange(popPublicPriceDto.getHeavyRange());
							//轻货范围
							popDto2.setLightRange(popPublicPriceDto.getLightRange());
							//将处理的轻重货价格段2数据添加到链表中
							popPriceDtos.add(popDto2);
							
							//价格
							publishPrice.setPopPublicPriceDtoList(popPriceDtos);
						}else if(String.valueOf(THREE).equals(popPublicPriceDto.getSectionId())){
							popDto3 = new PopPublicPriceDto();
							//添加重货价格
							heavyPrice3 = heavyPrice3.add(popPublicPriceDto.getHeavyPrice());
							popDto3.setHeavyPrice(heavyPrice3);
							//添加轻货价格
							lightPrice3 = lightPrice3.add(popPublicPriceDto.getLightPrice());
							popDto3.setLightPrice(lightPrice3);
							
							/*偏线价格*/
							if(outerPriceEntity.getWeightFeeRate()!=null){
								heavyPrice3 = heavyPrice3.add(outerPriceEntity.getWeightFeeRate());
								popDto3.setHeavyPrice(heavyPrice3);
							}
							if(outerPriceEntity.getVolumeFeeRate()!=null){
								lightPrice3 = lightPrice3.add(outerPriceEntity.getVolumeFeeRate());
								popDto3.setLightPrice(lightPrice3);
							}
							//设置分段数
							popDto3.setSectionId(String.valueOf(THREE));
							//重货范围
							popDto3.setHeavyRange(popPublicPriceDto.getHeavyRange());
							//轻货范围
							popDto3.setLightRange(popPublicPriceDto.getLightRange());
							//将处理的轻重货价格段3数据添加到链表中
							popPriceDtos.add(popDto3);
							
							//价格
							publishPrice.setPopPublicPriceDtoList(popPriceDtos);	
						}else if(String.valueOf(FOUR).equals(popPublicPriceDto.getSectionId())){
							popDto4 = new PopPublicPriceDto();
							//添加重货价格
							heavyPrice4 = heavyPrice4.add(popPublicPriceDto.getHeavyPrice());
							popDto4.setHeavyPrice(heavyPrice4);
							//添加轻货价格
							lightPrice4 = lightPrice4.add(popPublicPriceDto.getLightPrice());
							popDto4.setLightPrice(lightPrice4);
							
							/*偏线价格*/
							if(outerPriceEntity.getWeightFeeRate()!=null){
								heavyPrice4 = heavyPrice4.add(outerPriceEntity.getWeightFeeRate());
								popDto4.setHeavyPrice(heavyPrice4);
							}
							if(outerPriceEntity.getVolumeFeeRate()!=null){
								lightPrice4 = lightPrice4.add(outerPriceEntity.getVolumeFeeRate());
								popDto4.setLightPrice(lightPrice4);
							}
							//设置分段数
							popDto4.setSectionId(String.valueOf(FOUR));
							//重货范围
							popDto4.setHeavyRange(popPublicPriceDto.getHeavyRange());
							//轻货范围
							popDto4.setLightRange(popPublicPriceDto.getLightRange());
							//将处理的轻重货价格段4数据添加到链表中
							popPriceDtos.add(popDto4);
							
							//价格
							publishPrice.setPopPublicPriceDtoList(popPriceDtos);	
						}else if(String.valueOf(FIVE).equals(popPublicPriceDto.getSectionId())){
							popDto5 = new PopPublicPriceDto();
							//添加重货价格
							heavyPrice5 = heavyPrice5.add(popPublicPriceDto.getHeavyPrice());
							popDto5.setHeavyPrice(heavyPrice5);
							//添加轻货价格
							lightPrice5 = lightPrice5.add(popPublicPriceDto.getLightPrice());
							popDto5.setLightPrice(lightPrice5);
							
							/*偏线价格*/
							if(outerPriceEntity.getWeightFeeRate()!=null){
								heavyPrice5 = heavyPrice5.add(outerPriceEntity.getWeightFeeRate());
								popDto5.setHeavyPrice(heavyPrice5);
							}
							if(outerPriceEntity.getVolumeFeeRate()!=null){
								lightPrice5 = lightPrice5.add(outerPriceEntity.getVolumeFeeRate());
								popDto5.setLightPrice(lightPrice5);
							}
							//设置分段数
							popDto5.setSectionId(String.valueOf(FIVE));
							//重货范围
							popDto5.setHeavyRange(popPublicPriceDto.getHeavyRange());
							//轻货范围
							popDto5.setLightRange(popPublicPriceDto.getLightRange());
							//将处理的轻重货价格段5数据添加到链表中
							popPriceDtos.add(popDto5);
							
							//价格
							publishPrice.setPopPublicPriceDtoList(popPriceDtos);	
						}else if(String.valueOf(SIX).equals(popPublicPriceDto.getSectionId())){
							popDto6 = new PopPublicPriceDto();
							//添加重货价格
							heavyPrice6 = heavyPrice6.add(popPublicPriceDto.getHeavyPrice());
							popDto6.setHeavyPrice(heavyPrice6);
							//添加轻货价格
							lightPrice6 = lightPrice6.add(popPublicPriceDto.getLightPrice());
							popDto6.setLightPrice(lightPrice6);
							
							/*偏线价格*/
							if(outerPriceEntity.getWeightFeeRate()!=null){
								heavyPrice6 = heavyPrice6.add(outerPriceEntity.getWeightFeeRate());
								popDto6.setHeavyPrice(heavyPrice6);
							}
							if(outerPriceEntity.getVolumeFeeRate()!=null){
								lightPrice6 = lightPrice6.add(outerPriceEntity.getVolumeFeeRate());
								popDto6.setLightPrice(lightPrice6);
							}
							//设置分段数
							popDto6.setSectionId(String.valueOf(SIX));
							//重货范围
							popDto6.setHeavyRange(popPublicPriceDto.getHeavyRange());
							//轻货范围
							popDto6.setLightRange(popPublicPriceDto.getLightRange());
							//将处理的轻重货价格段6数据添加到链表中
							popPriceDtos.add(popDto6);
							
							//价格
							publishPrice.setPopPublicPriceDtoList(popPriceDtos);	
						}
						//如果有专线价格方案，也有偏线价格方案，那么设置isPxFlag为false，只显示专线加偏线价格，否则显示偏线价格。   
						if(priceDto!=null
								&&priceDto.getHeavyFeeRate()!=null
								&&priceDto.getLightFeeRate()!=null
								&&outerPriceEntity.getWeightFeeRate()!=null
								&&outerPriceEntity.getVolumeFeeRate()!=null){
							publishPrice.setIsPxFlag(true); //显示偏线
						}else{
							publishPrice.setIsPxFlag(false); //不显示偏线
					}
				}
		  }
		}
		}
		
	}

	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * <p>
	 * Description:组装官网的时效价格<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-11-5
	 * 
	 * @param effectInfoList
	 * 
	 * @param priceInfoList
	 * 
	 * @param voList
	 * 
	 * void
	 */
	private void encapulatePublicPrice(List<EffectivePlanDto> effectInfoList,
			List<ProductPriceDto> priceInfoList, List<PublicPriceDto> voList) {
		PublicPriceDto publicPrice = null;
		if (CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectivePlanDto> effectivePlanDtos = new ArrayList<EffectivePlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
				    for(int j = 0; j<priceInfoList.size(); j++){
						//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
					    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
				    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
						if(effParentCode.equals(priceDto.getProductCode())){	
							publicPrice = new PublicPriceDto();
							publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
							publicPrice.setAddDay(effectiveDto.getAddDay());
//							publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//							publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
							publicPrice.setArriveTime(effectiveDto.getArriveTime());
							publicPrice.setArrvRegionCode(priceDto.getArrvRegionId());
							publicPrice.setArrvRegionId(priceDto.getArrvRegionId());
//							publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
//							publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
							publicPrice.setDeliveryTime(effectiveDto.getDeliveryTime());
							publicPrice.setDeptRegionCode(priceDto.getStartRegionId());
							publicPrice.setDeptRegionId(priceDto.getStartRegionId());
//							publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
//							publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
							publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
							publicPrice.setMaxTime(effectiveDto.getMaxTime());
							publicPrice.setMaxTimeUnit(effectiveDto.getMaxTimeUnit());
							publicPrice.setMinTime(effectiveDto.getMinTime());
							publicPrice.setMinTimeUnit(effectiveDto.getMinTimeUnit());
							publicPrice.setProductCode(effectiveDto.getProductCode());
							publicPrice.setProductName(effectiveDto.getProductName());
							publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
							publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
							publicPrice.setLightFeeRatePickUpYes(priceDto.getLightFeeRatePickUpYes());
							publicPrice.setLightFeeRatePickUpNo(priceDto.getLightFeeRatePickUpNo());
							publicPrice.setHeavyFeeRatePickUpYes(priceDto.getHeavyFeeRatePickUpYes());
							publicPrice.setHeavyFeeRatePickUpNo(priceDto.getHeavyFeeRatePickUpNo());
							publicPrice.setMinFeePickUpYes(priceDto.getMinFeePickUpYes());
							publicPrice.setMinFeePickUpNo(priceDto.getMinFeePickUpNo());
							effectivePlanDtos.add(effectiveDto);
							voList.add(publicPrice);
						} 
				    }
				}
				//时效产品信息作为产品名显示，价格不显示空白展出
				//时效区域部分未匹配的情况
				if(effectivePlanDtos.size() != effectInfoList.size()) {
					for(int i=0; i<effectInfoList.size(); i++){
						EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
						if(effectivePlanDtos.contains(effectiveDto)) {
							continue;
						} 
						publicPrice = new PublicPriceDto();
						publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
						publicPrice.setAddDay(effectiveDto.getAddDay());
//						publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//						publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
						publicPrice.setArriveTime(effectiveDto.getArriveTime());
						publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
						publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
						publicPrice.setDeliveryTime(effectiveDto.getDeliveryTime());
						publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
						publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
						publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
						publicPrice.setMaxTime(effectiveDto.getMaxTime());
						publicPrice.setMaxTimeUnit(effectiveDto.getMaxTimeUnit());
						publicPrice.setMinTime(effectiveDto.getMinTime());
						publicPrice.setMinTimeUnit(effectiveDto.getMinTimeUnit());
						publicPrice.setProductCode(effectiveDto.getProductCode());
						publicPrice.setProductName(effectiveDto.getProductName());
						voList.add(publicPrice);
					}
				}
				//时效区域全部未匹配的情况
			} else {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
					publicPrice = new PublicPriceDto();
					publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
					publicPrice.setAddDay(effectiveDto.getAddDay());
//					publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//					publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
					publicPrice.setArriveTime(effectiveDto.getArriveTime());
					publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
					publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
					publicPrice.setDeliveryTime(effectiveDto.getDeliveryTime());
					publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
					publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
					publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
					publicPrice.setMaxTime(effectiveDto.getMaxTime());
					publicPrice.setMaxTimeUnit(effectiveDto.getMaxTimeUnit());
					publicPrice.setMinTime(effectiveDto.getMinTime());
					publicPrice.setMinTimeUnit(effectiveDto.getMinTimeUnit());
					publicPrice.setProductCode(effectiveDto.getProductCode());
					publicPrice.setProductName(effectiveDto.getProductName());
					voList.add(publicPrice);
				}
			}
		}
		//筛选空运价格
		if(CollectionUtils.isNotEmpty(priceInfoList)) {
			for(int j = 0; j<priceInfoList.size(); j++){
		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
		    	//比较时效的父产品code和价格产品code是否相等
		    	//判断是否为空运，空运价格目前没有时效，只有价格信息
		    	if (StringUtil.equals(priceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
		    		publicPrice = new PublicPriceDto();
		    		publicPrice.setArrvRegionId(priceDto.getArrvRegionId());
					publicPrice.setDeptRegionId(priceDto.getStartRegionId());
		    		publicPrice.setLightFeeRatePickUpYes(priceDto.getLightFeeRatePickUpYes());
					publicPrice.setLightFeeRatePickUpNo(priceDto.getLightFeeRatePickUpNo());
					publicPrice.setHeavyFeeRatePickUpYes(priceDto.getHeavyFeeRatePickUpYes());
					publicPrice.setHeavyFeeRatePickUpNo(priceDto.getHeavyFeeRatePickUpNo());
					publicPrice.setMinFeePickUpYes(priceDto.getMinFeePickUpYes());
					publicPrice.setMinFeePickUpNo(priceDto.getMinFeePickUpNo());
					publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
					publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
					publicPrice.setProductCode(priceDto.getProductCode());
					publicPrice.setProductName(priceDto.getProductName());
					voList.add(publicPrice);
		    	} 
		    }
		}
	}
//	private void encapulatePublicPrice(List<EffectivePlanDto> effectInfoList,
//			List<ProductPriceDto> priceInfoList, List<PublicPriceDto> voList) {
//		PublicPriceDto publicPrice = null;
//		//价格和时效都存在记录的情况
//		if (CollectionUtils.isNotEmpty(priceInfoList) && CollectionUtils.isNotEmpty(effectInfoList)) {
//			List<EffectivePlanDto> effectivePlanDtos = new ArrayList<EffectivePlanDto>();
//			List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
//			for(int i = 0; i < priceInfoList.size(); i++){
//				//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
//			    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
//		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(i);	
//		    	for(int j = 0; j < effectInfoList.size(); j++){
//		    		EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(j);
//			    	//比较时效的父产品code和价格产品code是否相等
//			    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
//			    	String effParentCode = product.getParentCode();
//					if(effParentCode.equals(priceDto.getProductCode())){	
//						publicPrice = new PublicPriceDto();
//						publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
//						publicPrice.setAddDay(effectiveDto.getAddDay());
//						publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//						publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//						publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
//						publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
//						publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
//						publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
//						publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
//						publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
//						publicPrice.setProductCode(effectiveDto.getProductCode());
//						publicPrice.setProductName(effectiveDto.getProductName());
//						publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
//						publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
//						publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
//						publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
//						publicPrice.setMinFee(priceDto.getMinFee());
//						publicPrice.setCentralizePickup(priceDto.getCentralizePickup());
//						productPriceDtos.add(priceDto);
//						effectivePlanDtos.add(effectiveDto);
//						voList.add(publicPrice);
//					} 
//			    }
//			}
//			//时效产品信息作为产品名显示，价格不显示空白展出
//			//时效区域部分未匹配的情况
//			if(effectivePlanDtos.size() != effectInfoList.size()) {
//				for(int i=0; i<effectInfoList.size(); i++){
//					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
//					if(effectivePlanDtos.contains(effectiveDto)) {
//						continue;
//					} 
//					publicPrice = new PublicPriceDto();
//					publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
//					publicPrice.setAddDay(effectiveDto.getAddDay());
//					publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//					publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//					publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
//					publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
//					publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
//					publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
//					publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
//					publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
//					publicPrice.setProductCode(effectiveDto.getProductCode());
//					publicPrice.setProductName(effectiveDto.getProductName());
//					voList.add(publicPrice);
//				}
//			}
//			//价格区域部分未匹配的情况
//			if(productPriceDtos.size() != priceInfoList.size()) {
//				for(int i=0; i<priceInfoList.size(); i++){
//					ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(i);	
//					if(productPriceDtos.contains(priceDto)) {
//						continue;
//					} 
//			    	publicPrice = new PublicPriceDto();
//					publicPrice.setArrvRegionId(priceDto.getArrvRegionId());
//					publicPrice.setDeptRegionId(priceDto.getStartRegionId());	
//		    		publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
//					publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
//					publicPrice.setMinFee(priceDto.getMinFee());
//					publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
//					publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
//					publicPrice.setProductCode(priceDto.getProductCode());
//					publicPrice.setProductName(priceDto.getProductName());
//					publicPrice.setFlightShiftNo(priceDto.getFlightShiftNo());
//					publicPrice.setCentralizePickup(priceDto.getCentralizePickup());
//					voList.add(publicPrice);
//				}
//			}
//		} else  if(CollectionUtils.isNotEmpty(effectInfoList) && CollectionUtils.isEmpty(priceInfoList)) {
//			//只存在时效区域的情况
//			for(int i=0; i<effectInfoList.size(); i++){
//				EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
//				publicPrice = new PublicPriceDto();
//				publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
//				publicPrice.setAddDay(effectiveDto.getAddDay());
//				publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//				publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//				publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
//				publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
//				publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
//				publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
//				publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
//				publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
//				publicPrice.setProductCode(effectiveDto.getProductCode());
//				publicPrice.setProductName(effectiveDto.getProductName());
//				voList.add(publicPrice);
//			}
//		} else if(CollectionUtils.isNotEmpty(priceInfoList) && CollectionUtils.isEmpty(effectInfoList)) {
//			//只存在价格区域的情况
//			for(int j = 0; j<priceInfoList.size(); j++){
//		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
//		    	publicPrice = new PublicPriceDto();
//				publicPrice.setArrvRegionId(priceDto.getArrvRegionId());
//				publicPrice.setDeptRegionId(priceDto.getStartRegionId());	
//	    		publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
//				publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
//				publicPrice.setMinFee(priceDto.getMinFee());
//				publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
//				publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
//				publicPrice.setProductCode(priceDto.getProductCode());
//				publicPrice.setProductName(priceDto.getProductName());
//				publicPrice.setFlightShiftNo(priceDto.getFlightShiftNo());
//				publicPrice.setCentralizePickup(priceDto.getCentralizePickup());
//				voList.add(publicPrice);
//		    }
//		}
//	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 组装本地时效价格
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-24 下午1:59:14
	 * 
	 * @param effectInfoList
	 * 
	 * @param priceInfoList
	 * 
	 * @param voList
	 * 
	 * @version V1.0
	 */
	private void assemblePublicPrice(List<EffectivePlanDto> effectInfoList,
			List<ProductPriceDto> priceInfoList, List<PublicPriceDto> voList) {
		PublicPriceDto publicPrice = null;
		if (CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectivePlanDto> effectivePlanDtos = new ArrayList<EffectivePlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
				    for(int j = 0; j<priceInfoList.size(); j++){
						//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
					    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
				    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
						if(effParentCode.equals(priceDto.getProductCode())){	
							publicPrice = new PublicPriceDto();
							publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
							publicPrice.setAddDay(effectiveDto.getAddDay());
							publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
							publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
							publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
							publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
							publicPrice.setArrvPriceRegionId(priceDto.getArrvRegionId());
							publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
							publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
							publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());	
							publicPrice.setDeptPriceRegionId(priceDto.getStartRegionId());
							publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
							publicPrice.setProductCode(effectiveDto.getProductCode());
							publicPrice.setProductName(effectiveDto.getProductName());
							publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
							publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
							publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
							publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
							publicPrice.setMinFee(priceDto.getMinFee());

							publicPrice.setCentralizePickup(StringUtils.equals(publicPrice.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PCP) ?
															null : priceDto.getCentralizePickup());
							publicPrice.setRegionNature(PricingConstants.PRESCRIPTION_REGION);
							/**
							 * 设置轻货价格和重货价格的字符串形式的值
							 * @author yangkang
							 */
							publicPrice.setHeavyFeeRateStr(priceDto.getHeavyFeeRateStr());
							publicPrice.setLightFeeRateStr(priceDto.getLightFeeRateStr());
							//新增轻、重货的分段明细信息list  219413-Luomengxiang  2014-11-26
							publicPrice.setPopPublicPriceDtoList(priceDto.getPopPublicPriceDtoList());
							if(!effectivePlanDtos.contains(effectiveDto)) {
								effectivePlanDtos.add(effectiveDto);
							}
							voList.add(publicPrice);
						} 
				    }
				}
				//时效产品信息作为产品名显示，价格不显示空白展出
				//时效区域部分未匹配的情况
				if(effectivePlanDtos.size() != effectInfoList.size()) {
					for(int i=0; i<effectInfoList.size(); i++){
						EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
						if(effectivePlanDtos.contains(effectiveDto)) {
							continue;
						} 
						publicPrice = new PublicPriceDto();
						publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
						publicPrice.setAddDay(effectiveDto.getAddDay());
						publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
						publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
						publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
						publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
						publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
						publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
						publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
						publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
						publicPrice.setProductCode(effectiveDto.getProductCode());
						publicPrice.setProductName(effectiveDto.getProductName());
						publicPrice.setRegionNature(PricingConstants.PRESCRIPTION_REGION);
						voList.add(publicPrice);
					}
				}
			} else {
				//时效区域全部未匹配的情况
				for(int i=0; i<effectInfoList.size(); i++){
					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
					publicPrice = new PublicPriceDto();
					publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
					publicPrice.setAddDay(effectiveDto.getAddDay());
					publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
					publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
					publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
					publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
					publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
					publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
					publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
					publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
					publicPrice.setProductCode(effectiveDto.getProductCode());
					publicPrice.setProductName(effectiveDto.getProductName());
					publicPrice.setRegionNature(PricingConstants.PRESCRIPTION_REGION);
					voList.add(publicPrice);
				}
			}
		}
		//筛选空运价格
		if(CollectionUtils.isNotEmpty(priceInfoList)) {
			for(int j = 0; j<priceInfoList.size(); j++){
		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
		    	//比较时效的父产品code和价格产品code是否相等
		    	//判断是否为空运，空运价格目前没有时效，只有价格信息
		    	if (StringUtil.equals(priceDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
		    		publicPrice = new PublicPriceDto();
//					publicPrice.setArrvRegionId(priceDto.getArrvRegionId());
//					publicPrice.setDeptRegionId(priceDto.getStartRegionId());	
		    		publicPrice.setArrvPriceRegionId(priceDto.getArrvRegionId());
					publicPrice.setDeptPriceRegionId(priceDto.getStartRegionId());
		    		publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
					publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
					publicPrice.setMinFee(priceDto.getMinFee());
					publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
					publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
					publicPrice.setProductCode(priceDto.getProductCode());
					publicPrice.setProductName(priceDto.getProductName());
					publicPrice.setFlightShiftNo(priceDto.getFlightShiftNo());
					if(StringUtils.equals(publicPrice.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PCP)){
						publicPrice.setCentralizePickup(null);
					}else{
						publicPrice.setCentralizePickup(priceDto.getCentralizePickup());
					}
					publicPrice.setRegionNature(PricingConstants.PRICING_REGION_AIR);
					//zxy 20140509 MANA-1253 start 新增:公布价查询界面 增加合票方式
					publicPrice.setCombBillTypeName(priceDto.getCombBillTypeName());
					publicPrice.setCombBillTypeCode(priceDto.getCombBillTypeCode());
					//zxy 20140509 MANA-1253 end 新增:公布价查询界面 增加合票方式
					/**
					 * 设置轻货价格和重货价格的字符串形式的值
					 * @author yangkang
					 */
					publicPrice.setHeavyFeeRateStr(priceDto.getHeavyFeeRateStr());
					publicPrice.setLightFeeRateStr(priceDto.getLightFeeRateStr());
					
					voList.add(publicPrice);
		    	} 
		    }
		}
	}
//	private void assemblePublicPrice(List<EffectivePlanDto> effectInfoList,
//			List<ProductPriceDto> priceInfoList, List<PublicPriceDto> voList) {
//		PublicPriceDto publicPrice = null;
//		//价格和时效都存在记录的情况
//		if (CollectionUtils.isNotEmpty(priceInfoList) && CollectionUtils.isNotEmpty(effectInfoList)) {
//			List<EffectivePlanDto> effectivePlanDtos = new ArrayList<EffectivePlanDto>();
//			List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
//			for(int i = 0; i < priceInfoList.size(); i++){
//				//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
//			    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
//		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(i);	
//		    	for(int j = 0; j < effectInfoList.size(); j++){
//		    		EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(j);
//			    	//比较时效的父产品code和价格产品code是否相等
//			    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
//			    	String effParentCode = product.getParentCode();
//					if(effParentCode.equals(priceDto.getProductCode())){	
//						publicPrice = new PublicPriceDto();
//						publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
//						publicPrice.setAddDay(effectiveDto.getAddDay());
//						publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//						publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//						publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
//						publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
//						publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
//						publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
//						publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
//						publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
//						publicPrice.setProductCode(effectiveDto.getProductCode());
//						publicPrice.setProductName(effectiveDto.getProductName());
//						publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
//						publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
//						publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
//						publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
//						publicPrice.setMinFee(priceDto.getMinFee());
//						publicPrice.setCentralizePickup(priceDto.getCentralizePickup());
//						productPriceDtos.add(priceDto);
//						effectivePlanDtos.add(effectiveDto);
//						voList.add(publicPrice);
//					} 
//			    }
//			}
//			//时效产品信息作为产品名显示，价格不显示空白展出
//			//时效区域部分未匹配的情况
//			if(effectivePlanDtos.size() != effectInfoList.size()) {
//				for(int i=0; i<effectInfoList.size(); i++){
//					EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
//					if(effectivePlanDtos.contains(effectiveDto)) {
//						continue;
//					} 
//					publicPrice = new PublicPriceDto();
//					publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
//					publicPrice.setAddDay(effectiveDto.getAddDay());
//					publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//					publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//					publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
//					publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
//					publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
//					publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
//					publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
//					publicPrice.setProductCode(effectiveDto.getProductCode());
//					publicPrice.setProductName(effectiveDto.getProductName());
//					voList.add(publicPrice);
//				}
//			}
//			//价格区域部分未匹配的情况
//			if(productPriceDtos.size() != priceInfoList.size()) {
//				for(int i=0; i<priceInfoList.size(); i++){
//					ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(i);	
//					if(productPriceDtos.contains(priceDto)) {
//						continue;
//					} 
//			    	publicPrice = new PublicPriceDto();
//					publicPrice.setArrvRegionId(priceDto.getArrvRegionId());
//					publicPrice.setDeptRegionId(priceDto.getStartRegionId());	
//		    		publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
//					publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
//					publicPrice.setMinFee(priceDto.getMinFee());
//					publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
//					publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
//					publicPrice.setProductCode(priceDto.getProductCode());
//					publicPrice.setProductName(priceDto.getProductName());
//					publicPrice.setFlightShiftNo(priceDto.getFlightShiftNo());
//					publicPrice.setCentralizePickup(priceDto.getCentralizePickup());
//					voList.add(publicPrice);
//				}
//			}
//		} else  if(CollectionUtils.isNotEmpty(effectInfoList) && CollectionUtils.isEmpty(priceInfoList)) {
//			//只存在时效区域的情况
//			for(int i=0; i<effectInfoList.size(); i++){
//				EffectivePlanDto effectiveDto = (EffectivePlanDto)effectInfoList.get(i);
//				publicPrice = new PublicPriceDto();
//				publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
//				publicPrice.setAddDay(effectiveDto.getAddDay());
//				publicPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
//				publicPrice.setPickTime(getStandardTime(effectiveDto.getArriveTime()));
//				publicPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());
//				publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
//				publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
//				publicPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());
//				publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());					
//				publicPrice.setHasSalesDept(effectiveDto.getHasSalesDept());
//				publicPrice.setProductCode(effectiveDto.getProductCode());
//				publicPrice.setProductName(effectiveDto.getProductName());
//				voList.add(publicPrice);
//			}
//		} else if(CollectionUtils.isNotEmpty(priceInfoList) && CollectionUtils.isEmpty(effectInfoList)) {
//			//只存在价格区域的情况
//			for(int j = 0; j<priceInfoList.size(); j++){
//		    	ProductPriceDto priceDto = (ProductPriceDto)priceInfoList.get(j);	
//		    	publicPrice = new PublicPriceDto();
//				publicPrice.setArrvRegionId(priceDto.getArrvRegionId());
//				publicPrice.setDeptRegionId(priceDto.getStartRegionId());	
//	    		publicPrice.setLightFeeRate(priceDto.getLightFeeRate());
//				publicPrice.setHeavyFeeRate(priceDto.getHeavyFeeRate());
//				publicPrice.setMinFee(priceDto.getMinFee());
//				publicPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
//				publicPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
//				publicPrice.setProductCode(priceDto.getProductCode());
//				publicPrice.setProductName(priceDto.getProductName());
//				publicPrice.setFlightShiftNo(priceDto.getFlightShiftNo());
//				publicPrice.setCentralizePickup(priceDto.getCentralizePickup());
//				voList.add(publicPrice);
//		    }
//		}
//	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
     * 
     * <p>
     * (根据始发部门编号与目的站区域编号查询时效方案明细信息)
     * </p>
     * 
     * @author sz
     * 
     * @date 2012-12-29
     * 
     * @param deptNo
     *            - 始发站编号
     *            
     * @param arrvieNo
     *            - 目的信息
     *            
     * @billDate 开单日期
     * 
     * @return billDate 开单日期
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService#
     * 
     * queryCuurentEffectivePlanDetailInfo(java.lang.String,
     *      java.lang.String)
     */
    private List<ProductPriceDto> queryCuurentPublishPriceDetailInfo(
	    String deptNo, String arrvieNo,  Date billDate, String type) {
		List<ProductPriceDto> list = new ArrayList<ProductPriceDto>();
		//判断是否有业务时间
		//如果没有业务时间NEW新值
		Date currentDateTime = null;
		if (billDate == null) {
		    currentDateTime = new Date();
		} else {
		    currentDateTime = billDate;
		}
    	// 根据出发部门编号定位价格逻辑区域
    	String deptRegionId = regionService.findRegionOrgByDeptNo(deptNo, currentDateTime, null,PricingConstants.PRICING_REGION);
		if(StringUtil.isEmpty(deptRegionId)) {
		    return null;
		}
		// 根据到达部门编号定位价格逻辑区域
    	String arrvieRegionId = regionArriveService.findRegionOrgByDeptNo(arrvieNo, currentDateTime,null, PricingConstants.ARRIVE_REGION);
		if(StringUtil.isEmpty(arrvieRegionId)) {
		    return null;
		}
		//查询汽运信息
		PublishPriceDto publishPriceDto = new PublishPriceDto(); 
		publishPriceDto.setDeptRegionId(deptRegionId);
		publishPriceDto.setArrvRegionId(arrvieRegionId);
		publishPriceDto.setReceiveDate(currentDateTime);
		publishPriceDto.setValuationType(type);
		publishPriceDto.setActive(FossConstants.ACTIVE);
		// 根据最新时效方案版本查询相关时效方案详细信息
		List<PublishPriceDto> publishPriceDtos = publishPriceDao.queryPublishPriceValuationByCalculaCondition(publishPriceDto);
		//查询空运信息
		// 根据出发部门编号定位空运价格逻辑区域
    	String deptRegionAirId = regionAirService.findRegionOrgByDeptNo(deptNo, currentDateTime, null,PricingConstants.PRICING_REGION);
		if(StringUtil.isEmpty(deptRegionId)) {
		    return null;
		}
		// 根据到达部门编号定位空运价格逻辑区域
    	String arrvieRegionAirId = regionAirService.findRegionOrgByDeptNo(arrvieNo, currentDateTime,null, PricingConstants.PRICING_REGION);
		if(StringUtil.isEmpty(arrvieRegionId)) {
		    return null;
		}
		//赋空运价格始发ID
		publishPriceDto.setDeptRegionId(deptRegionAirId);
		//赋空运价格到达ID
		publishPriceDto.setArrvRegionId(arrvieRegionAirId);
		// 根据最新时效方案版本查询相关时效方案详细信息
		List<PublishPriceDto> publishPriceAirDtos = publishPriceDao.queryPublishPriceValuationByCalculaCondition(publishPriceDto);
		//查询精准大票信息
		// 根据出发部门编号定位精准大票价格逻辑区域
		PriceRegionBigGoodsEntity deptRegionBig = districtRegionService.findRegionBigOrgByDeptCode(deptNo, currentDateTime, null, PricingConstants.PRICING_REGION);
		String deptRegionBigId = "";
		if(deptRegionBig != null){
			deptRegionBigId = deptRegionBig.getId();
		}
		
		// 根据到达部门编号定位精准大票价格逻辑区域
		List<PublishPriceDto> publishPriceBigDtos = null;
		//中转区域价格信息
		List<PublishPriceDto> publishPriceBigMiddleDtos =null;
		//直发区域价格信息
		List<PublishPriceDto> publishPriceBigDirectlyDtos =null;
		List<PriceRegionBigGoodsArriveEntity> lists = districtRegionService.findRegionBigArriveOrgByDeptNo(arrvieNo, currentDateTime, null, PricingConstants.PRICING_REGION);
		if(lists != null && lists.size() != 0){
			publishPriceDto.setDeptRegionId(deptRegionBigId);
			/**
			 * @author YANGKANG
			 * 精准大票价格如果同时存在直发区域和中转区域的价格则只取直发区域价格
			 * 门到门和场到场只存在直发区域价格
			 */
			for(PriceRegionBigGoodsArriveEntity arrvieRegionBig : lists){
				if(arrvieRegionBig.getTranSportType().equals(PricingConstants.BIG_SEND_DIRECTLY)){
					String arrvieRegionBigId = arrvieRegionBig.getId();
					publishPriceDto.setArrvRegionId(arrvieRegionBigId);
					publishPriceBigDirectlyDtos = publishPriceDao.queryPublishPriceValuationByCalculaCondition(publishPriceDto);
					if(publishPriceBigDirectlyDtos != null && publishPriceBigDirectlyDtos.size()!= 0){
						break;
					}
				}else{
					String arrvieRegionBigId = arrvieRegionBig.getId();
					publishPriceDto.setArrvRegionId(arrvieRegionBigId);
					publishPriceBigMiddleDtos = publishPriceDao.queryPublishPriceValuationByCalculaCondition(publishPriceDto);
//					//如果中转区域价格中存在门到门和场到场的价格，则直接过滤掉
//					if(publishPriceBigMiddleDtos != null && publishPriceBigMiddleDtos.size()!= 0){
//						//定义一个中间变量
//						List<PublishPriceDto> publishPriceBigMiddleDtosTemp = new ArrayList<PublishPriceDto>();
//						for(int i=0;i<publishPriceBigMiddleDtos.size();i++){
//							if(!ProductEntityConstants.PRICING_PRODUCT_C2_C20008.equals(publishPriceBigMiddleDtos.get(i).getProductCode())
//									&&!ProductEntityConstants.PRICING_PRODUCT_C2_C20009.equals(publishPriceBigMiddleDtos.get(i).getProductCode())){
//								publishPriceBigMiddleDtosTemp.add(publishPriceBigMiddleDtos.get(i));
//							}
//						}
//						publishPriceBigMiddleDtos = publishPriceBigMiddleDtosTemp;
//					}
					
				}	
			}
			
			 if(publishPriceBigDirectlyDtos!=null && publishPriceBigDirectlyDtos.size()>0){
				 publishPriceBigDtos = publishPriceBigDirectlyDtos;
			 }else{
				 publishPriceBigDtos = publishPriceBigMiddleDtos;
			 }
		}


        //查询精准电商信息 add by wangfeng
        // 精准电商价格到达区域信息
        List<PublishPriceDto> publishPriceEcDto = null;
        // 根据出发部门编号定位精准电商价格逻辑区域
        PriceRegionEcGoodsEntity deptRegionEc = districtRegionService.findRegionEcGoodsOrgByDeptCode(deptNo, currentDateTime, null, PricingConstants.PRICING_REGION);
        PriceRegionEcGoodsArriveEntity priceRegionEcGoodsArriveEntity = districtRegionService.findRegionEcGoodsArriveOrgByDeptNo(arrvieNo, currentDateTime, null, PricingConstants.PRICING_REGION);
        if(deptRegionEc != null && priceRegionEcGoodsArriveEntity != null){
            publishPriceDto.setDeptRegionId(deptRegionEc.getId());
            String arrvieRegionEcId = priceRegionEcGoodsArriveEntity.getId();
            publishPriceDto.setArrvRegionId(arrvieRegionEcId);
            //获取到精准包裹价格
            publishPriceEcDto = publishPriceDao.queryEcPublishPriceValuationByCalculaCondition(publishPriceDto);
        }
        if(CollectionUtils.isNotEmpty(publishPriceEcDto)){
        	//对精准电商集合进行排序,根据价格区间从小到大
        	sort(publishPriceEcDto, new Comparator<PublishPriceDto>() {
        		@Override
        		public int compare(PublishPriceDto t, PublishPriceDto t1) {
        			return t.getLeftRange().compareTo(t1.getLeftRange());
        		}
        	});
        }

        //合并二种价格集合
		//二种集合都有数据的处理
		if(CollectionUtils.isNotEmpty(publishPriceDtos) && CollectionUtils.isNotEmpty(publishPriceAirDtos) &&
				CollectionUtils.isNotEmpty(publishPriceBigDtos)) {
			publishPriceDtos.addAll(publishPriceAirDtos);
			publishPriceDtos.addAll(publishPriceBigDtos);
		//当汽运集合无数据，空运集合有数据
		} else if (CollectionUtils.isEmpty(publishPriceDtos) && CollectionUtils.isNotEmpty(publishPriceAirDtos) &&
				CollectionUtils.isNotEmpty(publishPriceBigDtos)) {
			publishPriceDtos = publishPriceAirDtos;
			publishPriceDtos.addAll(publishPriceBigDtos);
		} else if (CollectionUtils.isEmpty(publishPriceDtos) && CollectionUtils.isEmpty(publishPriceAirDtos) &&
				CollectionUtils.isNotEmpty(publishPriceBigDtos)){
				publishPriceDtos = publishPriceBigDtos;
		} else if (CollectionUtils.isNotEmpty(publishPriceDtos) && CollectionUtils.isEmpty(publishPriceAirDtos) &&
				CollectionUtils.isNotEmpty(publishPriceBigDtos)){
				publishPriceDtos.addAll(publishPriceBigDtos);
		} else if (CollectionUtils.isEmpty(publishPriceDtos) && CollectionUtils.isNotEmpty(publishPriceAirDtos) &&
				CollectionUtils.isEmpty(publishPriceBigDtos)){
				publishPriceDtos = publishPriceAirDtos;
		} else if (CollectionUtils.isNotEmpty(publishPriceDtos) && CollectionUtils.isNotEmpty(publishPriceAirDtos) &&
				CollectionUtils.isEmpty(publishPriceBigDtos)){
			publishPriceDtos.addAll(publishPriceAirDtos);
		}
        //加入精准电商集合
        if(CollectionUtils.isNotEmpty(publishPriceDtos) && CollectionUtils.isNotEmpty(publishPriceEcDto)){
            publishPriceDtos.addAll(publishPriceEcDto);
        }else if(CollectionUtils.isEmpty(publishPriceDtos) && CollectionUtils.isNotEmpty(publishPriceEcDto)){
            publishPriceDtos = publishPriceEcDto;
        }
        if(publishPriceDtos == null){
        	publishPriceDtos = Collections.emptyList();
        }
        //重新组合集合数据
		if(CollectionUtils.isNotEmpty(publishPriceDtos)) {
			List<PublishPriceDto> priceDtos = appendSections(this.boxHeaveAndLight(publishPriceDtos));
			if(CollectionUtils.isNotEmpty(priceDtos)) {
				//复制集合数据
				list = this.boxProductPriceDto(priceDtos);
			}
		}
		//封装名称
		if(CollectionUtils.isNotEmpty(list)) {
			list = this.boxCatalogName(list);
		}
		//返回集合
		return list;
    }
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-25 下午4:41:02
     * 
     * @param startCodeList
     * 
     * @param arrvCodeList
     * 
     * @param type
     * 
     * @param active
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
	@SuppressWarnings({ "rawtypes", "unused" })
	private List<ProductPriceDto> queryCurrentPublishPriceByRegionIds(Set<String> startRegionIdSet,
			Set<String> arrvRegionIdSet, String type, Date billDate) {
		List<ProductPriceDto> list = new ArrayList<ProductPriceDto>();
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}

		if (CollectionUtils.isEmpty(startRegionIdSet) || CollectionUtils.isEmpty(arrvRegionIdSet)) {
			return null;
		}
		
		List<String> startRegionIdList = new ArrayList<String>();
		for (Iterator iterator = startRegionIdSet.iterator(); iterator.hasNext();) {
			String startRegionId = (String) iterator.next();
			startRegionIdList.add(startRegionId);
		}
		
		List<String> arrvRegionIdList = new ArrayList<String>();
		for (Iterator iterator = arrvRegionIdSet.iterator(); iterator.hasNext();) {
			String arrvRegionId = (String) iterator.next();
			arrvRegionIdList.add(arrvRegionId);
		}

		List<PublishPriceDto> publishPriceDtos = publishPriceDao.queryPublishPriceByRegionIds(startRegionIdList,
				arrvRegionIdList, type, FossConstants.ACTIVE, currentDateTime);
		if (CollectionUtils.isNotEmpty(publishPriceDtos)) {
			List<PublishPriceDto> priceDtos = this.boxHeaveAndLight(publishPriceDtos);
			if (CollectionUtils.isNotEmpty(priceDtos)) {
				list = this.boxProductPriceDto(priceDtos);
			}
		}

		if (CollectionUtils.isNotEmpty(list)) {
			list = this.boxCatalogName(list);
		}
		return list;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-25 下午4:41:02
     * 
     * @param startCodeList
     * 
     * @param arrvCodeList
     * 
     * @param type
     * 
     * @param active
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
	@SuppressWarnings("rawtypes")
	private List<ProductPriceDto> queryCurrentPublishPriceByRegionIdsForPickUp(Set<String> startRegionIdSet,
			Set<String> arrvRegionIdSet, String type, Date billDate) {
		List<ProductPriceDto> list = new ArrayList<ProductPriceDto>();
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}
		if (CollectionUtils.isEmpty(startRegionIdSet) || CollectionUtils.isEmpty(arrvRegionIdSet)) {
			return null;
		}
		List<String> startRegionIdList = new ArrayList<String>();
		for (Iterator iterator = startRegionIdSet.iterator(); iterator.hasNext();) {
			String startRegionId = (String) iterator.next();
			startRegionIdList.add(startRegionId);
		}
		List<String> arrvRegionIdList = new ArrayList<String>();
		for (Iterator iterator = arrvRegionIdSet.iterator(); iterator.hasNext();) {
			String arrvRegionId = (String) iterator.next();
			arrvRegionIdList.add(arrvRegionId);
		}
		List<PublishPriceDto> publishPriceDtos = publishPriceDao.queryPublishPriceByRegionIds(startRegionIdList,
				arrvRegionIdList, type, FossConstants.ACTIVE, currentDateTime);
		if (CollectionUtils.isNotEmpty(publishPriceDtos)) {
			List<PublishPriceDto> priceDtos = this.boxHeaveAndLightForPickUp(publishPriceDtos);
			if (CollectionUtils.isNotEmpty(priceDtos)) {
				list = this.boxProductPriceDtoForPickUp(priceDtos);
			}
		}
		if (CollectionUtils.isNotEmpty(list)) {
			list = this.boxCatalogName(list);
		}
		return list;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
	 * 
	 * @Description: 获取网点信息
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-23 上午10:25:21
	 * 
	 * @param codes
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
    @Override
    public List<SaleDepartmentInfoDto> getOuterAndDepartment(List<String> codes) {
    	List<SaleDepartmentInfoDto> deptList = new  ArrayList<SaleDepartmentInfoDto>();
		List<OuterBranchEntity> outerList = new ArrayList<OuterBranchEntity>();
		//处理数据  避免查询数据量超过1000  数据库报错  add by  yangkang
    	if(codes.size()>NumberConstants.NUMBER_1000){
    		for(int i=0;i<codes.size();i+=NumberConstants.NUMBER_1000){
    			if(i+NumberConstants.NUMBER_1000>codes.size()){
    				List<SaleDepartmentInfoDto> deptListSub = saleDepartmentDao.querySaleDepartmentInfoByCodes(codes.subList(i, codes.size()));
        			List<OuterBranchEntity> outerListSub = vehicleAgencyDeptDao.queryOuterBranchByCodes(codes.subList(i, codes.size()), null); 
        			deptList.addAll(deptListSub);
        			outerList.addAll(outerListSub);
    			}else{
    				List<SaleDepartmentInfoDto> deptListSub = saleDepartmentDao.querySaleDepartmentInfoByCodes(codes.subList(i, i+NumberConstants.NUMBER_1000));
        			List<OuterBranchEntity> outerListSub = vehicleAgencyDeptDao.queryOuterBranchByCodes(codes.subList(i, i+NumberConstants.NUMBER_1000), null); 
        			deptList.addAll(deptListSub);
        			outerList.addAll(outerListSub);
    			}
    		}
    	}else{
    		 deptList = saleDepartmentDao.querySaleDepartmentInfoByCodes(codes);
        	 outerList = vehicleAgencyDeptDao.queryOuterBranchByCodes(codes, null); 
    	}    	
    	if(CollectionUtils.isEmpty(deptList)) {
    		deptList = new ArrayList<SaleDepartmentInfoDto>();
    	}
    	if(CollectionUtils.isNotEmpty(outerList)) {
    		for (int i = 0; i < outerList.size(); i++) {
    			OuterBranchEntity outerBranchEntity = outerList.get(i);
    			SaleDepartmentInfoDto infoDto = new SaleDepartmentInfoDto();
    			infoDto.setCode(outerBranchEntity.getAgentDeptCode());
    			infoDto.setName(outerBranchEntity.getAgentDeptName());
    			infoDto.setSimpleName(outerBranchEntity.getSimplename());
    			infoDto.setDelivery(outerBranchEntity.getPickupToDoor());
    			infoDto.setPickupSelf(outerBranchEntity.getPickupSelf());
    			infoDto.setTelephone(outerBranchEntity.getMobilePhone());
    			infoDto.setOrgType(outerBranchEntity.getBranchtype());
    			infoDto.setAddress(outerBranchEntity.getAddress());
    			deptList.add(infoDto);
			}
    	}
    	return deptList;
    }
    /**
	 * 
	 * @Description: 获取网点信息
	 * @author FOSSDP-sz
	 * @date 2013-4-22 上午11:45:02
	 * @param codes
	 * @param regionId
	 * @param regionNature
	 * @param productCode  区分产品类别
	 * @param priceRegionIdClass  区分是出发区域 或者目的区域
	 * @return
	 * @version V1.0
	 */
    @Override
        public List<SaleDepartmentInfoDto> getOuterAndDepartment(List<String> codes, String regionId, String regionNature,String productCode,String priceRegionIdClass) {
    	if(CollectionUtils.isNotEmpty(codes)) {
    		return this.getOuterAndDepartment(codes);
    	} else {
    		if(StringUtils.isNotEmpty(regionId) && StringUtils.isNotEmpty(regionNature)) {
    			if(StringUtils.equals(regionNature, PricingConstants.PRICING_REGION_AIR)) {
    				List<String> list = regionAirService.searchRegionOrgCodeByRegionId(regionId);
    				if(CollectionUtils.isNotEmpty(list)) {
    					return this.getOuterAndDepartment(list);
    				} else {
    					list = getAdministrativeDept(regionId, regionNature);
    					if(CollectionUtils.isNotEmpty(list)) {
    						return this.getOuterAndDepartment(list); 
    					}
    				}
    			//精准大票区域查询   author  yangkang   2014-07-11
    			} else if(StringUtil.equals(productCode, PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG)||
						StringUtil.equals(productCode, PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG)||
						StringUtil.equals(productCode, PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG)||
						StringUtil.equals(productCode, PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG)||
						StringUtil.equals(productCode, PricingConstants.ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR)||
						StringUtil.equals(productCode, PricingConstants.ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD)){
    					   List<String> list  = new ArrayList<String>(); 
    					  if(StringUtils.equals(priceRegionIdClass, PricingConstants.PRICING_START_REGION_ID)){
    						  list = regionBigGoodsService.searchRegionOrgCodeByRegionId(regionId, regionNature);
    					  }else{
    						  list = regionBigGoodsArriveService.searchRegionOrgCodeByRegionId(regionId);
    					  }
    						
		    				if(CollectionUtils.isNotEmpty(list)) {
		    					return this.getOuterAndDepartment(list);
		    				} else {
		    					if(StringUtils.equals(priceRegionIdClass, PricingConstants.PRICING_START_REGION_ID)){
		    						regionNature =PricingConstants.PRICING_START_REGION_ID;
		    					}else{
		    						regionNature =PricingConstants.PRICING_ARRIVE_REGION_ID;
		    					}
		    					list = getAdministrativeDept(regionId, regionNature);
		    					if(CollectionUtils.isNotEmpty(list)) {
		    						return this.getOuterAndDepartment(list); 
		    					}
		    				}
		    	//精准包裹区域下网点查询
    			}else if (StringUtil.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_PCP)){
					List<String> list  = new ArrayList<String>(); 
					if(StringUtils.equals(priceRegionIdClass, PricingConstants.PRICING_START_REGION_ID)){
						  list = regionEcGoodsService.searchRegionOrgCodeByRegionId(regionId, regionNature);
					}else{
						  list = regionEcGoodsArriveService.searchRegionOrgCodeByRegionId(regionId);
					}
					if(CollectionUtils.isNotEmpty(list)){
						return this.getOuterAndDepartment(list);
					}else {
						if(StringUtils.equals(priceRegionIdClass, PricingConstants.PRICING_START_REGION_ID)){
							regionNature =PricingConstants.PRICING_START_REGION_ID;
						}else{
							regionNature =PricingConstants.PRICING_ARRIVE_REGION_ID;
						}
						list = getAdministrativeDeptForPcp(regionId, regionNature);
						if(CollectionUtils.isNotEmpty(list)) {
							return this.getOuterAndDepartment(list);
						}
					}
				}else{    				
    				List<String> list = regionService.searchRegionOrgCodeByRegionId(regionId, regionNature);
    				if(CollectionUtils.isNotEmpty(list)) {
    					return this.getOuterAndDepartment(list);
    				} else {
    					list = getAdministrativeDept(regionId, regionNature);
    					if(CollectionUtils.isNotEmpty(list)) {
    						return this.getOuterAndDepartment(list); 
    					}
    				}
    			}
    		}
    		return null;
    	}
    }
    /**
     * 
     * @Description: 根据区域查找行政区域下的网点信息
     * @author FOSSDP-sz
     * @date 2013-4-28 上午11:06:56
     * @param regionId
     * @param regionNature
     * @return
     * @version V1.0
     */
    private List<String> getAdministrativeDept(String regionId, String regionNature) {
    	List<String> list = null;
    	String areaCode = null;
    	if(StringUtils.equals(regionNature, PricingConstants.PRICING_REGION_AIR)) {
    		PriceRegionAirEntity priceRegionAirEntity = regionAirService.searchRegionByID(regionId, regionNature);
    		if(priceRegionAirEntity != null && StringUtils.equals(priceRegionAirEntity.getRegionType(), PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE)) {
    			String countyCode = priceRegionAirEntity.getCountyCode();
    			String cityCode = priceRegionAirEntity.getCityCode();
    			String provinceCode = priceRegionAirEntity.getProCode();
    			if(StringUtil.isNotEmpty(countyCode)) {
    				areaCode = countyCode;
    			} else if(StringUtil.isNotEmpty(cityCode)) {
    				areaCode = cityCode;
    			} else if(StringUtil.isNotEmpty(provinceCode)) {
    				areaCode = provinceCode;
    			} else {
    				return null;
    			}
    		}
    	//精准大票区域下网点信息查找    yk-2014-07-11
    	} else if(StringUtils.equals(regionNature, PricingConstants.PRICING_START_REGION_ID)||StringUtils.equals(regionNature, PricingConstants.PRICING_ARRIVE_REGION_ID)){
    		
    		if(StringUtils.equals(regionNature, PricingConstants.PRICING_ARRIVE_REGION_ID)){
    			//重新复制参数  便于查询价格区域
    			regionNature =  PricingConstants.PRICING_REGION;
    			PriceRegionBigGoodsArriveEntity priceRegionEntity = regionBigGoodsArriveService.searchRegionByID(regionId, regionNature);
        		if(priceRegionEntity != null && StringUtils.equals(priceRegionEntity.getRegionType(), PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE)) {
        			String countyCode = priceRegionEntity.getCountyCode();
        			String cityCode = priceRegionEntity.getCityCode();
        			String provinceCode = priceRegionEntity.getProCode();
        			if(StringUtil.isNotEmpty(countyCode)) {
        				areaCode = countyCode;
        			} else if(StringUtil.isNotEmpty(cityCode)) {
        				areaCode = cityCode;
        			} else if(StringUtil.isNotEmpty(provinceCode)) {
        				areaCode = provinceCode;
        			} else {
        				return null;
        			}
        		}
    		}else{
    			//重新复制参数  便于查询价格区域
    			regionNature =  PricingConstants.PRICING_REGION;
    			PriceRegionBigGoodsEntity priceRegionEntity = regionBigGoodsService.searchRegionByID(regionId, regionNature);
        		if(priceRegionEntity != null && StringUtils.equals(priceRegionEntity.getRegionType(), PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE)) {
        			String countyCode = priceRegionEntity.getCountyCode();
        			String cityCode = priceRegionEntity.getCityCode();
        			String provinceCode = priceRegionEntity.getProCode();
        			if(StringUtil.isNotEmpty(countyCode)) {
        				areaCode = countyCode;
        			} else if(StringUtil.isNotEmpty(cityCode)) {
        				areaCode = cityCode;
        			} else if(StringUtil.isNotEmpty(provinceCode)) {
        				areaCode = provinceCode;
        			} else {
        				return null;
        			}
        		}
    		}
    	}else{    		PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(regionId, regionNature);
    		if(priceRegionEntity != null && StringUtils.equals(priceRegionEntity.getRegionType(), PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE)) {
    			String countyCode = priceRegionEntity.getCountyCode();
    			String cityCode = priceRegionEntity.getCityCode();
    			String provinceCode = priceRegionEntity.getProCode();
    			if(StringUtil.isNotEmpty(countyCode)) {
    				areaCode = countyCode;
    			} else if(StringUtil.isNotEmpty(cityCode)) {
    				areaCode = cityCode;
    			} else if(StringUtil.isNotEmpty(provinceCode)) {
    				areaCode = provinceCode;
    			} else {
    				return null;
    			}
    		}
    	}
    	List<CommonOrgEntity> commonOrgEntities = pricingOrgDao.queryArrvOrgByDistrict(areaCode, FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(commonOrgEntities)) {
			list = new ArrayList<String>();
			for (CommonOrgEntity commonOrgEntity : commonOrgEntities) {
				list.add(commonOrgEntity.getCode());
			}
		}
    	return list;
    }
	/**
	 * 精准包裹根据出发区域查找行政区域下的网点信息
	 * @param regionId
	 * @param regionNature
     * @return
     */
	private List<String> getAdministrativeDeptForPcp(String regionId, String regionNature) {
		List<String> list = null;
		String areaCode=null;
		if(StringUtils.equals(regionNature, PricingConstants.PRICING_START_REGION_ID)) {
			//重新复制参数  便于查询价格区域
			regionNature = PricingConstants.PRICING_REGION;
			PriceRegionEcGoodsEntity  priceRegionEntity = regionEcGoodsService.searchRegionByID(regionId, regionNature);
			if (priceRegionEntity != null && StringUtils.equals(priceRegionEntity.getRegionType(), PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE)) {
				String countyCode = priceRegionEntity.getCountyCode();
				String cityCode = priceRegionEntity.getCityCode();
				String provinceCode = priceRegionEntity.getProCode();
				if (StringUtil.isNotEmpty(countyCode)) {
					areaCode = countyCode;
				} else if (StringUtil.isNotEmpty(cityCode)) {
					areaCode = cityCode;
				} else if (StringUtil.isNotEmpty(provinceCode)) {
					areaCode = provinceCode;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}else{
			//重新复制参数  便于查询价格区域
			regionNature =  PricingConstants.PRICING_REGION;
			PriceRegionEcGoodsArriveEntity priceRegionEntity = regionEcGoodsArriveService.searchRegionByID(regionId, regionNature);
			if (priceRegionEntity != null && StringUtils.equals(priceRegionEntity.getRegionType(), PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE)) {
				String countyCode = priceRegionEntity.getCountyCode();
				String cityCode = priceRegionEntity.getCityCode();
				String provinceCode = priceRegionEntity.getProCode();
				if (StringUtil.isNotEmpty(countyCode)) {
					areaCode = countyCode;
				} else if (StringUtil.isNotEmpty(cityCode)) {
					areaCode = cityCode;
				} else if (StringUtil.isNotEmpty(provinceCode)) {
					areaCode = provinceCode;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		List<CommonOrgEntity> commonOrgEntities = pricingOrgDao.queryArrvOrgByDistrict(areaCode, FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(commonOrgEntities)) {
			list = new ArrayList<String>();
			for (CommonOrgEntity commonOrgEntity : commonOrgEntities) {
				list.add(commonOrgEntity.getCode());
			}
		}
		return list;
	}
	
	
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 	
     * @Description: 校验方法
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-3-14 上午10:13:07
     * 
     * @param startCityCode
     * 
     * @param destinationCountryCode
     * 
     * @param destinationProvinceCode
     * 
     * @param destinationCityCode
     * 
     * @version V1.0
     */
    private void validateAreaInfo(String startCityCode, String destinationCountryCode,
			String destinationProvinceCode, String destinationCityCode) {
    	if(StringUtil.isEmpty(destinationCountryCode)) {
    		throw new PublishPriceException("请选择国家", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	} else if(StringUtil.isEmpty(destinationProvinceCode)) {
    		throw new PublishPriceException("请选择省份", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	} else if(StringUtil.isEmpty(destinationCityCode)) {
    		throw new PublishPriceException("请选择目的城市", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	} else  if(StringUtil.isEmpty(startCityCode)) {
    		throw new PublishPriceException("请选择始发城市", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	}
    }
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 封装网点的区域信息
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-25 下午2:17:29
     * 
     * @param startDeptList
     * 
     * @param arrvDeptList
     * 
     * @return
     * 
     * @version V1.0
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Map> buildUpDistrictRegion(String districtCode) {
    	if(StringUtils.isEmpty(districtCode)) {
    		return null;
    	}
    	DistrictRegionEntity districtRegionEntity = districtRegionCacheDeal.getDistrictRegionByCache(districtCode);
    	
    	Map<String, List<String>> effectiveRegionMap = null;
		Map<String, String> effectiveRegionNameMap = null;
		
		Map<String, List<String>> priceRegionAutoMap = null;
		Map<String, String> priceRegionAutoNameMap = null;
		
		Map<String, List<String>> priceRegionAirMap = null;
		Map<String, String> priceRegionAirNameMap = null;
		Map<String, List<String>> priceRegionAutoArriveMap = null;
		Map<String, String> priceRegionAutoArriveNameMap = null;
		/**
		 * @author yangkang
		 * 新增精准大票价格区域信息
		 */
		Map<String, List<String>> priceBigRegionMap = null;
		Map<String, String> priceBigRegionNameMap = null;
		
		Map<String, List<String>> priceBigRegionArriveMap = null;
		Map<String, String> priceBigRegionArriveNameMap = null;
		/**
		 * @author lijinze
		 * 新增精准包裹价格区域信息
		 */
		Map<String, List<String>> priceEcRegionMap = null;
		Map<String, String> priceEcRegionNameMap = null;
		
		Map<String, List<String>> priceEcRegionArriveMap = null;
		Map<String, String> priceEcRegionArriveNameMap = null;
		
		
		// BUGKD-1497
		Map<String, Map> areaMap = new HashMap<String, Map>();
    	if(districtRegionEntity != null) {
    		String effectiveRegionIds = districtRegionEntity.getEffectiveRegionIds();
    		String priceAutoRegionIds = districtRegionEntity.getPriceAutoRegionIds();
    		String priceAirRegionIds = districtRegionEntity.getPriceAirRegionIds();
			String priceArriveRegionIds = districtRegionEntity.getPriceArriveRegionIds();
			//精准大票区域ID
    		String priceBigRegionIds  = districtRegionEntity.getPriceBigRegionIds();
    		String priceBigArriveRegionIds = districtRegionEntity.getPriceBigArriveRegionIds();
    		//精准包裹区域ID
    		String priceEcRegionIds  = districtRegionEntity.getPriceEcRegionIds();
    		String priceEcArriveRegionIds = districtRegionEntity.getPriceEcArriveRegionIds();
    		
    		
    		String effectiveRegionNames = districtRegionEntity.getEffectiveRegionNames();
    		String priceAutoRegionNames = districtRegionEntity.getPriceAutoRegionNames();
    		String priceAirRegionNames = districtRegionEntity.getPriceAirRegionNames();
			String priceArriveRegionNames = districtRegionEntity.getPriceArriveRegionNames();

			//精准大票区域名称
			String priceBigRegionNames = districtRegionEntity.getPriceBigRegionNames();
			String priceBigArriveRegionNames = districtRegionEntity.getPriceBigArriveRegionNames();
			//精准包裹区域名称
			String priceEcRegionNames = districtRegionEntity.getPriceEcRegionNames();
			String priceEcArriveRegionNames = districtRegionEntity.getPriceEcArriveRegionNames();
			
    		
    		if(StringUtils.isNotEmpty(effectiveRegionIds)) {
    			effectiveRegionMap = new HashMap<String, List<String>>();
    			effectiveRegionNameMap = new HashMap<String, String>();
    			String[] regionIds = effectiveRegionIds.split(",");
    			String[] regionNames = effectiveRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					effectiveRegionMap.put(regionIds[i], null);
            			effectiveRegionNameMap.put(regionIds[i], regionNames[i]);
					}
    			}
    		}
    		
    		if(StringUtils.isNotEmpty(priceAutoRegionIds)) {
    			priceRegionAutoMap = new HashMap<String, List<String>>();
    			priceRegionAutoNameMap = new HashMap<String, String>();
    			String[] regionIds = priceAutoRegionIds.split(",");
    			String[] regionNames = priceAutoRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					priceRegionAutoMap.put(regionIds[i], null);
    					priceRegionAutoNameMap.put(regionIds[i], regionNames[i]);
					} 
    			}
    		}
			
			if(StringUtils.isNotEmpty(priceAirRegionIds)) {
				priceRegionAirMap = new HashMap<String, List<String>>();
				priceRegionAirNameMap = new HashMap<String, String>();
				String[] regionIds = priceAirRegionIds.split(",");
				String[] regionNames = priceAirRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					priceRegionAirMap.put(regionIds[i], null);
    					priceRegionAirNameMap.put(regionIds[i], regionNames[i]);
					}
    			}
			}
			
			if(StringUtils.isNotEmpty(priceArriveRegionIds)) {
				priceRegionAutoArriveMap = new HashMap<String, List<String>>();
				priceRegionAutoArriveNameMap = new HashMap<String, String>();
				String[] regionIds = priceArriveRegionIds.split(",");
				String[] regionNames = priceArriveRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					priceRegionAutoArriveMap.put(regionIds[i], null);
    					priceRegionAutoArriveNameMap.put(regionIds[i], regionNames[i]);
					}
    			}
			}
			//精准大票出发区域
			if(StringUtils.isNotEmpty(priceBigRegionIds)) {
				priceBigRegionMap = new HashMap<String, List<String>>();
				priceBigRegionNameMap = new HashMap<String, String>();
    			String[] regionIds = priceBigRegionIds.split(",");
    			String[] regionNames = priceBigRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					priceBigRegionMap.put(regionIds[i], null);
    					priceBigRegionNameMap.put(regionIds[i], regionNames[i]);
					} 
    			}
    		}
			//精准大票到达区域
			if(StringUtils.isNotEmpty(priceBigArriveRegionIds)) {
				priceBigRegionArriveMap = new HashMap<String, List<String>>();
				priceBigRegionArriveNameMap = new HashMap<String, String>();
				String[] regionIds = priceBigArriveRegionIds.split(",");
				String[] regionNames = priceBigArriveRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					priceBigRegionArriveMap.put(regionIds[i], null);
    					priceBigRegionArriveNameMap.put(regionIds[i], regionNames[i]);
					}
    			}
			}
			//精准包裹出发区域
			if(StringUtils.isNotEmpty(priceEcRegionIds)) {
				priceEcRegionMap = new HashMap<String, List<String>>();
				priceEcRegionNameMap = new HashMap<String, String>();
    			String[] regionIds = priceEcRegionIds.split(",");
    			String[] regionNames = priceEcRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					priceEcRegionMap.put(regionIds[i], null);
    					priceEcRegionNameMap.put(regionIds[i], regionNames[i]);
					} 
    			}
    		}
			//精准包裹到达区域
			if(StringUtils.isNotEmpty(priceEcArriveRegionIds)) {
				priceEcRegionArriveMap = new HashMap<String, List<String>>();
				priceEcRegionArriveNameMap = new HashMap<String, String>();
				String[] regionIds = priceEcArriveRegionIds.split(",");
				String[] regionNames = priceEcArriveRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					priceEcRegionArriveMap.put(regionIds[i], null);
    					priceEcRegionArriveNameMap.put(regionIds[i], regionNames[i]);
					}
    			}
			}
			//存在缓存
//			areaMap.put("districtRegionEntityIsNotNull",null);
    	}
		//起始时效区域
    	if(effectiveRegionMap != null && effectiveRegionMap.size() > 0) {
    		areaMap.put("effectiveRegionMap", effectiveRegionMap);
    	}
    	//起始时效区域名称
    	if(effectiveRegionNameMap != null && effectiveRegionNameMap.size() > 0) {
    		areaMap.put("effectiveRegionNameMap", effectiveRegionNameMap);
    	}
		//起始价格区域
		if(priceRegionAutoMap != null && priceRegionAutoMap.size() > 0) {
			areaMap.put("priceRegionMap", priceRegionAutoMap);
    	}
		//起始价格区域名称
		if(priceRegionAutoNameMap != null && priceRegionAutoNameMap.size() > 0) {
			areaMap.put("priceRegionNameMap", priceRegionAutoNameMap);
    	}
		//起始空运价格区域
		if(priceRegionAirMap != null && priceRegionAirMap.size() > 0) {
			areaMap.put("priceRegionAirMap", priceRegionAirMap);
		}
		//起始空运价格区域名称
		if(priceRegionAirNameMap != null && priceRegionAirNameMap.size() > 0) {
			areaMap.put("priceRegionAirNameMap", priceRegionAirNameMap);
		}
		//到达价格区域
		if(priceRegionAutoArriveMap != null && priceRegionAutoArriveMap.size() > 0) {
			areaMap.put("priceRegionArriveMap", priceRegionAutoArriveMap);
		}
		//到达价格区域名称
		if(priceRegionAutoArriveNameMap != null && priceRegionAutoArriveNameMap.size() > 0) {
			areaMap.put("priceRegionArriveNameMap", priceRegionAutoArriveNameMap);
		}
		//精准大票起始价格区域
		if(priceBigRegionMap != null && priceBigRegionMap.size() > 0) {
			areaMap.put("priceBigRegionMap", priceBigRegionMap);
    	}
		//精准大票起始价格区域名称
		if(priceBigRegionNameMap != null && priceBigRegionNameMap.size() > 0) {
			areaMap.put("priceBigRegionNameMap", priceBigRegionNameMap);
    	}
		//精准大票价格到达区域
		if(priceBigRegionArriveMap != null && priceBigRegionArriveMap.size() > 0) {
			areaMap.put("priceBigRegionArriveMap", priceBigRegionArriveMap);
    	}
		//精准大票价格到达区域名称
		if(priceBigRegionArriveNameMap != null && priceBigRegionArriveNameMap.size() > 0) {
			areaMap.put("priceBigRegionArriveNameMap", priceBigRegionArriveNameMap);
    	}
		//精准包裹起始价格区域
		if(priceEcRegionMap != null && priceEcRegionMap.size() > 0) {
			areaMap.put("priceEcRegionMap", priceEcRegionMap);
    	}
		//精准包裹起始价格区域名称
		if(priceEcRegionNameMap != null && priceEcRegionNameMap.size() > 0) {
			areaMap.put("priceEcRegionNameMap", priceEcRegionNameMap);
    	}
		//精准包裹价格到达区域
		if(priceEcRegionArriveMap != null && priceEcRegionArriveMap.size() > 0) {
			areaMap.put("priceEcRegionArriveMap", priceEcRegionArriveMap);
    	}
		//精准包裹价格到达区域名称
		if(priceEcRegionArriveNameMap != null && priceEcRegionArriveNameMap.size() > 0) {
			areaMap.put("priceEcRegionArriveNameMap", priceEcRegionArriveNameMap);
    	}
		//返回结果MAP
		return areaMap;
    }
    /**
     * 
     * @Description: 
     * @author FOSSDP-Administrator
     * @date 2013-4-16 上午9:39:19
     * @param startDeptList
     * @param arrvDeptList
     * @return
     * @version V1.0
     */
    @SuppressWarnings({"rawtypes" })
    private Map<String, Map> buildUpDeptRegion(List<String> arrvDeptList) {
    	Map<String, Map> areaMap = new HashMap<String, Map>();
    	//查询新集合时效区域
    	Map<String, String> effectiveRegionMap = new HashMap<String, String>();
    	for (int i = 0; i < arrvDeptList.size(); i++) {
			String deptCode = arrvDeptList.get(i);
			String regionId = regionService.findRegionOrgByDeptNo(deptCode, new Date(), null, PricingConstants.PRESCRIPTION_REGION);
			effectiveRegionMap.put(deptCode, regionId);
		}
    	//查询新集合价格区域
    	Map<String, String> priceRegionMap = new HashMap<String, String>();
    	for (int i = 0; i < arrvDeptList.size(); i++) {
			String deptCode = arrvDeptList.get(i);
			String regionId = regionService.findRegionOrgByDeptNo(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
			priceRegionMap.put(deptCode, regionId);
		}
    	//查询新集合空运价格区域
    	Map<String, String> priceRegionAirMap = new HashMap<String, String>();
    	for (int i = 0; i < arrvDeptList.size(); i++) {
			String deptCode = arrvDeptList.get(i);
			String regionId = regionAirService.findRegionOrgByDeptNo(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
			priceRegionAirMap.put(deptCode, regionId);
		}
    	/**
		 * 时效区域
		 */
		//根据时效目的区域组装网点信息
		Map<String, List<String>> arrvRegionMap = new HashMap<String, List<String>>();
		Map<String, String> arrvRegionNameMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(arrvDeptList)) {
			for (int i = 0; i < arrvDeptList.size(); i++) {
				String deptNo = arrvDeptList.get(i);
				String arrvRegionId = effectiveRegionMap.get(deptNo);
				if(StringUtil.isNotBlank(arrvRegionId)) {
					List<String> regionDeptList = null;
					if(arrvRegionMap.containsKey(arrvRegionId)) {
						regionDeptList = arrvRegionMap.get(arrvRegionId);
					} else {
						regionDeptList = new ArrayList<String>();
					}
					regionDeptList.add(deptNo);
					arrvRegionMap.put(arrvRegionId, regionDeptList);
				}
			}
			if(arrvRegionMap.size() > 0) {
				for (Iterator<String> iterator = arrvRegionMap.keySet().iterator(); iterator.hasNext();) {
					String arrvRegionId = iterator.next();
					PriceRegionEntity regionEntity = (PriceRegionEntity) regionService.searchRegionByID(arrvRegionId,
							PricingConstants.PRESCRIPTION_REGION);
					if(regionEntity != null && regionEntity.getRegionName() != null) {
						arrvRegionNameMap.put(arrvRegionId, regionEntity.getRegionName());
					}
				}
			}
		}
		/**
		 * 价格区域
		 */
		//根据价格目的区域组装网点信息
		Map<String, List<String>> priceArrvRegionMap = new HashMap<String, List<String>>();
		Map<String, String> priceArrvRegionNameMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(arrvDeptList)) {
			for (int i = 0; i < arrvDeptList.size(); i++) {
				String deptNo = arrvDeptList.get(i);
				String arrvRegionId = priceRegionMap.get(deptNo);
				if(StringUtil.isNotBlank(arrvRegionId)) {
					List<String> regionDeptList = null;
					if(priceArrvRegionMap.containsKey(arrvRegionId)) {
						regionDeptList = priceArrvRegionMap.get(arrvRegionId);
					} else {
						regionDeptList = new ArrayList<String>();
					}
					regionDeptList.add(deptNo);
					priceArrvRegionMap.put(arrvRegionId, regionDeptList);
				}
			}
			if(priceArrvRegionMap.size() > 0) {
				for (Iterator<String> iterator = priceArrvRegionMap.keySet().iterator(); iterator.hasNext();) {
					String arrvRegionId = iterator.next();
					PriceRegionEntity regionEntity = (PriceRegionEntity) regionService.searchRegionByID(arrvRegionId,
							PricingConstants.PRICING_REGION);
					if(regionEntity != null && regionEntity.getRegionName() != null) {
						priceArrvRegionNameMap.put(arrvRegionId, regionEntity.getRegionName());
					}
				}
			}
		}
		/**
		 * 空运区域
		 */
		//根据价格目的区域组装网点信息
		Map<String, List<String>> priceArrvRegionAirMap = new HashMap<String, List<String>>();
		Map<String, String> priceArrvRegionAirNameMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(arrvDeptList)) {
			for (int i = 0; i < arrvDeptList.size(); i++) {
				String deptNo = arrvDeptList.get(i);
				String arrvRegionId = priceRegionAirMap.get(deptNo);
				if(StringUtil.isNotBlank(arrvRegionId)) {
					List<String> regionDeptList = null;
					if(priceArrvRegionAirMap.containsKey(arrvRegionId)) {
						regionDeptList = priceArrvRegionAirMap.get(arrvRegionId);
					} else {
						regionDeptList = new ArrayList<String>();
					}
					regionDeptList.add(deptNo);
					priceArrvRegionAirMap.put(arrvRegionId, regionDeptList);
				}
			}
			
			if(priceArrvRegionAirMap.size() > 0) {
				for (Iterator<String> iterator = priceArrvRegionAirMap.keySet().iterator(); iterator.hasNext();) {
					String arrvRegionId = iterator.next();
					PriceRegionAirEntity regionEntity = (PriceRegionAirEntity) regionAirService.searchRegionByID(arrvRegionId,
							PricingConstants.PRICING_REGION);
					if(regionEntity != null && regionEntity.getRegionName() != null) {
						priceArrvRegionAirNameMap.put(arrvRegionId, regionEntity.getRegionName());
					}
				}
			}
		}
		//目的时效区域
		areaMap.put("effectiveRegionMap", arrvRegionMap);
		//目的时效区域名称
		areaMap.put("effectiveRegionNameMap", arrvRegionNameMap);
		//目的价格区域
		areaMap.put("priceRegionMap", priceArrvRegionMap);
		//目的价格区域名称
		areaMap.put("priceRegionNameMap", priceArrvRegionNameMap);
		//目的空运价格区域
		areaMap.put("priceRegionAirMap", priceArrvRegionAirMap);
		//目的空运价格区域名称
		areaMap.put("priceRegionAirNameMap", priceArrvRegionAirNameMap);
		//返回结果MAP
		return areaMap;
    }
    
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 根据是否接货封装重货和轻货价格
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-28 下午5:47:38
     * 
     * @param list
     * 
     * @return
     * 
     * @version V1.0
     */
    private List<PublishPriceDto> boxHeaveAndLightForPickUp(List<PublishPriceDto> list) {
    	List<PublishPriceDto> priceDtos = new ArrayList<PublishPriceDto>();
    	List<PublishPriceDto> list1 = new ArrayList<PublishPriceDto>();
    	if(CollectionUtils.isNotEmpty(list)) {
			List<String> tempList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				PublishPriceDto dtoi = (PublishPriceDto)list.get(i);
    			if(tempList.contains(dtoi.getPricingValuationId())) {
    				continue;
    			}
    			
    			//对门到门 场到场产品价格进行特殊处理  根据产品code来合并重货和轻货的价格
				if(tempList.contains(dtoi.getProductCode())){
					continue;
				}
				
    			PublishPriceDto dto = new PublishPriceDto();
    			try {
					PropertyUtils.copyProperties(dto, dtoi);
    			} catch (IllegalAccessException e) {
    				LOGGER.info(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					LOGGER.info(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					LOGGER.info(e.getMessage(), e);
				}
				for (int j = 0; j < list.size(); j++) {
					PublishPriceDto dtoj = list.get(j);
					if(tempList.contains(dtoi.getPricingValuationId())) {
	    				continue;
	    			}
					//对门到门 场到场产品价格进行特殊处理  根据产品code来合并重货和轻货的价格
					if(tempList.contains(dtoi.getProductCode())){
						continue;
					}
					if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20008, dtoi.getProductCode())
							||StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20009, dtoi.getProductCode())){
						
						if( StringUtil.equals(dtoj.getProductCode(), dto.getProductCode())) {
							if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, dtoj.getCaculateType())) {
								dto.setHeavyFeeRate(dtoj.getFeeRate());
							}else if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, dtoj.getCaculateType())) {
								dto.setLightFeeRate(dtoj.getFeeRate());
							}
						}
						
					}else{
						if( StringUtil.equals(dtoj.getPricingValuationId(), dto.getPricingValuationId())) {
							if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, dtoj.getCaculateType())) {
								dto.setHeavyFeeRate(dtoj.getFeeRate());
							}else if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, dtoj.getCaculateType())) {
								dto.setLightFeeRate(dtoj.getFeeRate());
							}
						}

					}
				}
				if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20008, dtoi.getProductCode())
						||StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20009, dtoi.getProductCode())){
					tempList.add(dtoi.getProductCode());	
				}else{
					tempList.add(dtoi.getPricingValuationId());
				}
				list1.add(dto);
			}
//			for (int i = 0; i < list1.size(); i++) {
//				PublishPriceDto publishPriceDto = list1.get(i);
//				if(publishPriceDto.getDeptRegionId().equals("a5383894-4bd2-4c06-bdb1-9f6c60d6f1f4") 
//						&& publishPriceDto.getArrvRegionId().equals("8f57b95a-fd6a-4990-90eb-3df9f626b1d0")) {
//					System.out.println("ValuationId:"+publishPriceDto.getPricingValuationId());
//					System.out.println("重货:"+publishPriceDto.getHeavyFeeRate());
//					System.out.println("轻货:"+publishPriceDto.getLightFeeRate());
//					System.out.println("是否上门接货:"+publishPriceDto.getCentralizePickup());
//					System.out.println("产品:"+publishPriceDto.getProductCode());
//					System.out.println("货物类型:"+publishPriceDto.getGoodsTypeCode());
//					System.out.println("始发Code:"+publishPriceDto.getDeptRegionCode());
//					System.out.println("到达Code:"+publishPriceDto.getArrvRegionCode());
//					System.out.println("始发区域:"+publishPriceDto.getDeptRegionId());
//					System.out.println("到达区域:"+publishPriceDto.getArrvRegionId());
//				}
//			}
    		if(CollectionUtils.isNotEmpty(list1)) {
    			List<String> valuationIdList = new ArrayList<String>();
    			for (int i = 0; i < list1.size(); i++) {
    				PublishPriceDto publishPriceDtoi = list1.get(i);
        			if(valuationIdList.contains(publishPriceDtoi.getPricingValuationId())) {
        				continue;
        			}
        			PublishPriceDto publishPriceDto = new PublishPriceDto();
        			try {
						PropertyUtils.copyProperties(publishPriceDto, publishPriceDtoi);
        			} catch (IllegalAccessException e) {
        				LOGGER.info(e.getMessage(), e);
    				} catch (InvocationTargetException e) {
    					LOGGER.info(e.getMessage(), e);
    				} catch (NoSuchMethodException e) {
    					LOGGER.info(e.getMessage(), e);
    				}
        			publishPriceDto.setFeeRate(null);
        			publishPriceDto.setHeavyFeeRate(null);
        			publishPriceDto.setLightFeeRate(null);
        			publishPriceDto.setMinFee(null);
        			if(StringUtil.equals(PricingConstants.CENTRALIZE_PICKUP_YES, publishPriceDtoi.getCentralizePickup())) {
						publishPriceDto.setHeavyFeeRatePickUpYes(publishPriceDtoi.getHeavyFeeRate());
						publishPriceDto.setLightFeeRatePickUpYes(publishPriceDtoi.getLightFeeRate());
						//由于门到门 场到场的固定费用存储的字段与其他产品不一样  需对其另外赋值
						if(publishPriceDtoi.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20008)
								||publishPriceDtoi.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20009)){
							publishPriceDto.setMinFeePickUpYes(publishPriceDtoi.getFixedCosts());
						}else{
							publishPriceDto.setMinFeePickUpYes(publishPriceDtoi.getMinFee());
						}
						
        			} else if(StringUtil.equals(PricingConstants.CENTRALIZE_PICKUP_NO, publishPriceDtoi.getCentralizePickup())) {
        				publishPriceDto.setHeavyFeeRatePickUpNo(publishPriceDtoi.getHeavyFeeRate());
						publishPriceDto.setLightFeeRatePickUpNo(publishPriceDtoi.getLightFeeRate());
						//由于门到门 场到场的固定费用存储的字段与其他产品不一样  需对其另外赋值
						if(publishPriceDtoi.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20008)
								||publishPriceDtoi.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20009)){
							publishPriceDto.setMinFeePickUpNo(publishPriceDtoi.getFixedCosts());
						}else{
							publishPriceDto.setMinFeePickUpNo(publishPriceDtoi.getMinFee());
						}
						
        			}
    				for (int j = 0; j < list1.size(); j++) {
    					PublishPriceDto publishPriceDtoj = list1.get(j);
    					if(StringUtil.equals(publishPriceDtoi.getPricingValuationId(), publishPriceDtoj.getPricingValuationId())) {
    						continue;
    					}
    					if(valuationIdList.contains(publishPriceDtoj.getPricingValuationId())) {
            				continue;
            			}
    					if(StringUtil.equals(PricingConstants.CENTRALIZE_PICKUP_YES, publishPriceDtoi.getCentralizePickup())
								&& StringUtil.equals(PricingConstants.CENTRALIZE_PICKUP_NO, publishPriceDtoj.getCentralizePickup())
								&& StringUtil.equals(publishPriceDtoi.getDeptRegionId(), publishPriceDtoj.getDeptRegionId())
	    						&& StringUtil.equals(publishPriceDtoi.getArrvRegionId(), publishPriceDtoj.getArrvRegionId())
	    						&& StringUtil.equals(publishPriceDtoi.getProductCode(), publishPriceDtoj.getProductCode())
	    						&& StringUtil.equals(publishPriceDtoi.getGoodsTypeCode(), publishPriceDtoj.getGoodsTypeCode())) {
							publishPriceDto.setHeavyFeeRatePickUpNo(publishPriceDtoj.getHeavyFeeRate());
							publishPriceDto.setLightFeeRatePickUpNo(publishPriceDtoj.getLightFeeRate());
							publishPriceDto.setMinFeePickUpNo(publishPriceDtoj.getMinFee());
							valuationIdList.add(publishPriceDtoj.getPricingValuationId());
							break;
    					} else if(StringUtil.equals(PricingConstants.CENTRALIZE_PICKUP_NO, publishPriceDtoi.getCentralizePickup())
	    							&& StringUtil.equals(PricingConstants.CENTRALIZE_PICKUP_YES, publishPriceDtoj.getCentralizePickup())
	    							&& StringUtil.equals(publishPriceDtoi.getDeptRegionId(), publishPriceDtoj.getDeptRegionId())
    	    						&& StringUtil.equals(publishPriceDtoi.getArrvRegionId(), publishPriceDtoj.getArrvRegionId())
    	    						&& StringUtil.equals(publishPriceDtoi.getProductCode(), publishPriceDtoj.getProductCode())
    	    						&& StringUtil.equals(publishPriceDtoi.getGoodsTypeCode(), publishPriceDtoj.getGoodsTypeCode())) {
    						publishPriceDto.setHeavyFeeRatePickUpYes(publishPriceDtoj.getHeavyFeeRate());
    						publishPriceDto.setLightFeeRatePickUpYes(publishPriceDtoj.getLightFeeRate());
    						publishPriceDto.setMinFeePickUpYes(publishPriceDtoj.getMinFee());
    						valuationIdList.add(publishPriceDtoj.getPricingValuationId());
    						break;
        				} 
    				}
    				priceDtos.add(publishPriceDto);
    				valuationIdList.add(publishPriceDtoi.getPricingValuationId());
				}
    		}
    	}
    	return priceDtos;
    }
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * @Description: 封装ProductPriceDto模型
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:39:06
     * 
     * @param list
     * 
     * @return
     * 
     * @version V1.0
     */
    private List<ProductPriceDto> boxProductPriceDtoForPickUp(List<PublishPriceDto> list) {
    	List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
    	if(list != null && list.size() >0) {
    		for (int i = 0; i < list.size(); i++) {
    			PublishPriceDto publishPriceDto = list.get(i);
    			ProductPriceDto productPriceDto = new ProductPriceDto();
    			productPriceDto.setHeavyFeeRatePickUpYes(publishPriceDto.getHeavyFeeRatePickUpYes());
    			productPriceDto.setHeavyFeeRatePickUpNo(publishPriceDto.getHeavyFeeRatePickUpNo());
    			productPriceDto.setLightFeeRatePickUpYes(publishPriceDto.getLightFeeRatePickUpYes());
    			productPriceDto.setLightFeeRatePickUpNo(publishPriceDto.getLightFeeRatePickUpNo());
    			productPriceDto.setMinFeePickUpYes(publishPriceDto.getMinFeePickUpYes());
    			productPriceDto.setMinFeePickUpNo(publishPriceDto.getMinFeePickUpNo());
    			productPriceDto.setProductCode(publishPriceDto.getProductCode());
    			productPriceDto.setProductName(publishPriceDto.getProductName());
    			productPriceDto.setLongOrShort(publishPriceDto.getLongOrShort());
    			productPriceDto.setGoodsTypeCode(publishPriceDto.getGoodsTypeCode());
    			productPriceDto.setGoodsTypeName(publishPriceDto.getGoodsTypeName());
    			productPriceDto.setFlightShiftNo(publishPriceDto.getFlightShiftNo());
    			productPriceDto.setStartRegionId(publishPriceDto.getDeptRegionId());
    			productPriceDto.setArrvRegionId(publishPriceDto.getArrvRegionId());
    			productPriceDtos.add(productPriceDto);
			}
    	}
    	return productPriceDtos;
    }
	@SuppressWarnings("unused")
	private List<ValueAddDto> boxValueAddDtoForPickUp(List<ValueAddDto> list) {
    	List<ValueAddDto> productPriceDtos = new ArrayList<ValueAddDto>();
    	if(list != null && list.size() >0) {
    		for (int i = 0; i < list.size(); i++) {
    			ValueAddDto publishPriceDto = list.get(i);
    			ValueAddDto valueAddDto = new ValueAddDto();
    			valueAddDto.setProductCode(publishPriceDto.getProductCode());
    			valueAddDto.setProductName(publishPriceDto.getProductName());
    			valueAddDto.setGoodsTypeCode(publishPriceDto.getGoodsTypeCode());
    			valueAddDto.setGoodsTypeName(publishPriceDto.getGoodsTypeName());
    			valueAddDto.setArrvRegionId(publishPriceDto.getArrvRegionId());
    			productPriceDtos.add(valueAddDto);
			}
    	}
    	return productPriceDtos;
    }
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 同一计费规则封装重货和轻货价格
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-28 下午5:42:12
     * 
     * @param list
     * 
     * @return
     * 
     * @version V1.0
     */
    private List<PublishPriceDto> boxHeaveAndLight(List<PublishPriceDto> list) {
    	List<PublishPriceDto> priceDtos = new ArrayList<PublishPriceDto>();
    	if(CollectionUtils.isNotEmpty(list)) {
    		List<String> temp = new ArrayList<String> ();
			List<String>  bigPriceValuations = new ArrayList<String>();
			List<String>  ecPriceValuations = new ArrayList<String>();
    		for (int i = 0; i < list.size(); i++) {
    			PublishPriceDto dtoi = (PublishPriceDto)list.get(i);
    			PublishPriceDto dto = null;
				StringBuffer  heavyFeeRateStr =new StringBuffer();
				StringBuffer  lightFeeRateStr =new StringBuffer();
    			if(temp.contains(dtoi.getPricingValuationId())) {
    				continue;
    			} 
    			dto = new PublishPriceDto();
    			try {
					PropertyUtils.copyProperties(dto, dtoi);
    			} catch (IllegalAccessException e) {
    				LOGGER.info(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					LOGGER.info(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					LOGGER.info(e.getMessage(), e);
				}
    			dto.setFeeRate(null);
    			dto.setHeavyFeeRate(null);
    			dto.setLightFeeRate(null);
				
			/**
    			 * 对查询到的集合中的精准大票价格方案明细信息进行处理
    			 * author yangkang
    			 */
    			if(StringUtils.equals(dtoi.getProductCode(),ProductEntityConstants.PRICING_PRODUCT_C2_C20006)
    					||StringUtils.equals(dtoi.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20007)
    					||StringUtils.equals(dtoi.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20008)
    					||StringUtils.equals(dtoi.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20009)
    					){
    				
    				String  priceValuations =null;
    				
    				priceValuations=dtoi.getArrvRegionId()+dtoi.getDeptRegionId()+dtoi.getCentralizePickup()+dtoi.getProductCode();

    				if(bigPriceValuations.contains(priceValuations)){
        				continue;
        			}
    				
    				for(int j= 0;j<list.size();j++){
    					PublishPriceDto dtoj = list.get(j);
    					if(StringUtils.equals(dtoj.getProductCode(),ProductEntityConstants.PRICING_PRODUCT_C2_C20006)
    							||StringUtils.equals(dtoj.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20007)
    							||StringUtils.equals(dtoi.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20008)
    							||StringUtils.equals(dtoi.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20009)
    	    					){
    						   if(StringUtils.equals(dtoi.getDeptRegionId(), dtoj.getDeptRegionId())&&
    								   	StringUtils.equals(dtoi.getArrvRegionId(), dtoj.getArrvRegionId())&&
    								   	StringUtils.equals(dtoi.getCentralizePickup(), dtoj.getCentralizePickup())&&
    								   	StringUtils.equals(dtoi.getProductCode(), dtoj.getProductCode())){
    							   if(StringUtils.equals(dtoj.getCaculateType(), PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)){
    								   heavyFeeRateStr.append(dtoj.getLeftRange()).append("-").append(dtoj.getRightRange());
        							   heavyFeeRateStr.append(":").append(dtoj.getFeeRate()).append(",").append(dtoj.getFixedCosts()).append("</br>");
    							   }else{
    								   lightFeeRateStr.append(dtoj.getLeftRange()).append("-").append(dtoj.getRightRange());
        							   lightFeeRateStr.append(":").append(dtoj.getFeeRate()).append(",").append(dtoj.getFixedCosts()).append("</br>");
    							   }
    						   }      						   
    					}else{
    						continue;	
    					}
    				}
    				
    				bigPriceValuations.add(priceValuations);
    				dto.setHeavyFeeRateStr(heavyFeeRateStr.toString());
    				dto.setLightFeeRateStr(lightFeeRateStr.toString());
    			}//精准电商
    			else if(StringUtils.equals(dtoi.getProductCode(),ProductEntityConstants.PRICING_PRODUCT_C2_C20012)){
    				
    				String  priceValuations = dtoi.getArrvRegionId()+dtoi.getDeptRegionId()+dtoi.getProductCode();

    				if(ecPriceValuations.contains(priceValuations)){
        				continue;
        			}
    				//比较拼加精准包裹价格
    				compreAppendEc(list, dtoi, heavyFeeRateStr, lightFeeRateStr);
    				ecPriceValuations.add(priceValuations);
    				dto.setHeavyFeeRateStr(heavyFeeRateStr.toString());
    				dto.setLightFeeRateStr(lightFeeRateStr.toString());
    			}
    			else{
    				for (int j = 0; j < list.size(); j++) {
    					PublishPriceDto dtoj = list.get(j);
    					//过滤精准大票的价格方案数据
    					if(StringUtils.equals(dtoj.getProductCode(),ProductEntityConstants.PRICING_PRODUCT_C2_C20006)
    	    					||StringUtils.equals(dtoj.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20007)
    	    					||StringUtils.equals(dtoi.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20008)
    	    					||StringUtils.equals(dtoi.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20009)){
    						continue;
    					}
    					if(temp.contains(dtoj.getPricingValuationId())) {
    	    				continue;
    	    			} 
    					if(dtoj.getPricingValuationId().equals(dtoi.getPricingValuationId())) {
    							if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, dtoj.getCaculateType())) {
        							dto.setHeavyFeeRate(dtoj.getFeeRate());
        							//新增重货价格字符串形式
        							dto.setHeavyFeeRateStr(dtoj.getFeeRate().toString());
        						}else if(StringUtil.equals(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, dtoj.getCaculateType())
        								//空运的轻货价格应该为0
        								&& !ProductEntityConstants.PRICING_PRODUCT_C2_C20004.equals(dtoj.getProductCode())) {
        							dto.setLightFeeRate(dtoj.getFeeRate());
        							//新增轻货价格字符串形式
        							dto.setLightFeeRateStr(dtoj.getFeeRate().toString());
        						}
    						}
    					}
    				temp.add(dtoi.getPricingValuationId());
    	 }
				priceDtos.add(dto);
			}
    	}
    	return priceDtos;
    }
    /**
     * 比较拼装精准包裹价格
     * @param list
     * @param dtoi
     * @param heavyFeeRateStr
     * @param lightFeeRateStr
     * @author 311417 
     */
	private void compreAppendEc(List<PublishPriceDto> list,PublishPriceDto dtoi, StringBuffer heavyFeeRateStr,
			StringBuffer lightFeeRateStr) {
		for(int j= 0;j<list.size();j++){
			PublishPriceDto dtoj = list.get(j);
			if(StringUtils.equals(dtoi.getProductCode(),ProductEntityConstants.PRICING_PRODUCT_C2_C20012)){
				if(StringUtils.equals(dtoi.getDeptRegionId(), dtoj.getDeptRegionId())&&
						StringUtils.equals(dtoi.getArrvRegionId(), dtoj.getArrvRegionId())&&
						StringUtils.equals(dtoi.getProductCode(), dtoj.getProductCode())){
				   if(StringUtils.equals(dtoj.getCaculateType(), PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)){
					   heavyFeeRateStr.append(dtoj.getLeftRange()).append("-").append(dtoj.getRightRange());
					   heavyFeeRateStr.append(":");
					   if(dtoj.getFixedCosts() != null && dtoj.getFixedCosts().compareTo(BigDecimal.ZERO) > 0){
						   heavyFeeRateStr.append(dtoj.getFixedCosts()).append("(首重)");
					   }
					   if(dtoj.getFeeRate() != null && dtoj.getFeeRate().compareTo(BigDecimal.ZERO) > 0){
						   heavyFeeRateStr.append(dtoj.getFeeRate());
					   }
					   heavyFeeRateStr.append("</br>");
				   }else{
					   lightFeeRateStr.append(dtoj.getLeftRange()).append("-").append(dtoj.getRightRange());
					   lightFeeRateStr.append(":");
					   if(dtoj.getFixedCosts() != null && dtoj.getFixedCosts().compareTo(BigDecimal.ZERO) > 0){
						   lightFeeRateStr.append(dtoj.getFixedCosts()).append("(首重)");
					   }
					   if(dtoj.getFeeRate() != null && dtoj.getFeeRate().compareTo(BigDecimal.ZERO) > 0){
						   lightFeeRateStr.append(dtoj.getFeeRate());
					   }
					   lightFeeRateStr.append("</br>");
				   }
				}  
			}else{
				continue;	
			}
		}
	}
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 封装ProductPriceDto模型
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:39:06
     * 
     * @param list
     * 
     * @return
     * 
     * @version V1.0
     */
    private List<ProductPriceDto> boxProductPriceDto(List<PublishPriceDto> list) {
    	List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
    	if(list != null && list.size() >0) {
    		for (int i = 0; i < list.size(); i++) {
    			PublishPriceDto publishPriceDto = list.get(i);
    			ProductPriceDto productPriceDto = new ProductPriceDto();
    			productPriceDto.setStartRegionId(publishPriceDto.getDeptRegionId());
    			productPriceDto.setArrvRegionId(publishPriceDto.getArrvRegionId());
    			productPriceDto.setHeavyFeeRate(publishPriceDto.getHeavyFeeRate());
    			productPriceDto.setLightFeeRate(publishPriceDto.getLightFeeRate());
    			productPriceDto.setMinFee(publishPriceDto.getMinFee());
    			productPriceDto.setProductCode(publishPriceDto.getProductCode());
    			productPriceDto.setProductName(publishPriceDto.getProductName());
    			productPriceDto.setLongOrShort(publishPriceDto.getLongOrShort());
    			productPriceDto.setGoodsTypeCode(publishPriceDto.getGoodsTypeCode());
    			productPriceDto.setGoodsTypeName(publishPriceDto.getGoodsTypeName());
    			productPriceDto.setFlightShiftNo(publishPriceDto.getFlightShiftNo());
    			//精准包裹的是否接货都为N
    			if(StringUtils.equals(ProductEntityConstants.PRICING_PRODUCT_C2_C20012, publishPriceDto.getProductCode())){
    				productPriceDto.setCentralizePickup(null);
    			}else{
    				productPriceDto.setCentralizePickup(publishPriceDto.getCentralizePickup());
    			}
    			//zxy 20140429 MANA-1253 start 新增:合票类型
    			productPriceDto.setCombBillTypeCode(publishPriceDto.getCombBillTypeCode());
    			productPriceDto.setCombBillTypeName(publishPriceDto.getCombBillTypeName());
    			//zxy 20140429 MANA-1253 end 新增:合票类型
				/**
    			 * 新增轻货价格和重货价格的字符串形式字段   方便在公共查询界面增加精准大票价格信息的显示
    			 * author yangkang  20140708
    			 */
    			productPriceDto.setHeavyFeeRateStr(publishPriceDto.getHeavyFeeRateStr());
    			productPriceDto.setLightFeeRateStr(publishPriceDto.getLightFeeRateStr());
    			//获取规则id   219413-Luomengxiang  2014-11-20
    			String valuationId=publishPriceDto.getPricingValuationId();
    			List<PopPriceDetailSectionEntity> popPriceDetailSectionEntityList=
    					popPriceDetailSectionDao.selectByValuationId(valuationId);
    			/**
    			 * 新增轻重货分段信息  
    			 * 219413-Luomengxiang  2014-11-20
    			 */
    			if(CollectionUtils.isEmpty(popPriceDetailSectionEntityList)){
    				productPriceDtos.add(productPriceDto);
    				continue;
    			}
    			List<PopPublicPriceDto> popPublicPriceDtoList=publishPriceDto.getPopPublishPriceDtoList();
     			productPriceDto.setPopPublicPriceDtoList(popPublicPriceDtoList);
       			productPriceDtos.add(productPriceDto);
    			}
			}
    	return productPriceDtos;
    }
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
	 * @Description: 封装集合中的名称数据
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-27 下午2:36:48
	 * 
	 * @param priceDiscountDtos
	 * 
	 * @version V1.0
	 */
	private List<ProductPriceDto>  boxCatalogName(List<ProductPriceDto> productPriceDtos) {
		List<ProductPriceDto> list = null;
		if(CollectionUtils.isNotEmpty(productPriceDtos)) {
			
			//查询所有航班类型   2017-03-07 355673 chenpeng
			List<DataDictionaryValueEntity>  dataDicList= dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.AIR_FLIGHT_TYPE);
			//将航班类型list转map
			Map<String, DataDictionaryValueEntity> dataDicMap = new HashMap<String, DataDictionaryValueEntity>();
			for(DataDictionaryValueEntity dic:dataDicList){
				dataDicMap.put(dic.getValueCode(), dic);
			}
			
			list = new ArrayList<ProductPriceDto>();
			Map<String, String> productNames = getUtilProductNames(productPriceDtos); 
			Map<String, String> goodsTypeNames = getUtilGoodsTypeNames(productPriceDtos);
			for (int i = 0; i < productPriceDtos.size(); i++) {
				ProductPriceDto productPriceDto = productPriceDtos.get(i);
				//封装产品NAME
				if(StringUtil.isNotBlank(productPriceDto.getProductCode())) {
					String productName = productNames.get(productPriceDto.getProductCode());
					if(StringUtil.isNotBlank(productName)) {
						productPriceDto.setProductName(productName);
					}
				}
				//封装货物类型NAME
				if(StringUtil.isNotBlank(productPriceDto.getGoodsTypeCode())) {
					String goodsTypeName = goodsTypeNames.get(productPriceDto.getGoodsTypeCode());
					if(StringUtil.isNotBlank(goodsTypeName)) {
						productPriceDto.setGoodsTypeName(goodsTypeName);
					}
				}
				//zxy 20140523修改 原因 esb中的dataDictionaryValueService服务取不到，如果异常则绕过
				//zxy 20140429 MANA-1253 start 新增: 
				//封装合票类型NAME
				try{
					DataDictionaryValueEntity dicCombBillEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.MAKE_WAYBILL_WAY,productPriceDto.getCombBillTypeCode());
					if(dicCombBillEntity!=null){
						productPriceDto.setCombBillTypeName(dicCombBillEntity.getValueName());
					}
				}catch(Exception e){
					productPriceDto.setCombBillTypeName("");
				}
				
				//zxy 20140429 MANA-1253 end 新增
				//封装航班号NAME
				if(StringUtil.isNotBlank(productPriceDto.getFlightShiftNo())) {
					//根据代码值获取对应的航班名称
					DataDictionaryValueEntity DataDicEntity=dataDicMap.get(productPriceDto.getFlightShiftNo());
					if(DataDicEntity!=null){
						productPriceDto.setFlightShiftNo(DataDicEntity.getValueName());
					}
				}
				list.add(productPriceDto);
			}
		}
		return list;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
	 * @Description: 根据集合中的产品CODE查询产品NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilProductNames(List<ProductPriceDto> list) {
		Map<String, String> productMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String productCode = list.get(i).getProductCode();
			if(StringUtil.isNotBlank(productCode)) {
				ProductEntity productEntity = productService.getProductByCache(productCode, new Date());
				if(productEntity != null && productEntity.getName() != null) {
					productMap.put(productEntity.getCode(), productEntity.getName());
				}
			}
		}
		return productMap;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * @Description: 根据集合中的货物类型CODE查询货物类型NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilGoodsTypeNames(List<ProductPriceDto> list) {
		Map<String, String> goodsTypeMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String goodsTypeCode = list.get(i).getGoodsTypeCode();
			if(StringUtil.isNotBlank(goodsTypeCode)) {
				GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(goodsTypeCode, new Date());
				if(goodsTypeEntity != null && goodsTypeEntity.getName() != null) {
					goodsTypeMap.put(goodsTypeEntity.getCode(), goodsTypeEntity.getName());
				}
			}
		}
		return goodsTypeMap;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 从GIS获取网点Code
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-9 下午3:24:33
	 * 
	 * @param destinationProvinceCode
	 * 
	 * @param destinationCityCode
	 * 
	 * @param destinationCountyCode
	 * 
	 * @param destinationAddress
	 * 
	 * @param interfaceType
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "unchecked"})
	public List<String> getCompositeStationCodes(String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress, String interfaceType) {
		List<String> deptList = null;
		Map<String, Object> map = formatToMap(destinationProvinceCode, destinationCityCode, destinationCountyCode, destinationAddress);
		Map<String, Object> result = null;
		try {
			result = GisInterfaceUtil.callGisInterface(interfaceType, map);
			LOGGER.info("result.get(depts)" + result.get("depts").toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		if(result  != null && (Boolean)result.get("success")){
			List<Map<String, String>> gisDeptList = (List<Map<String, String>>)result.get("depts");
			if(CollectionUtils.isNotEmpty(gisDeptList)) {
				deptList = new ArrayList<String>();
				for (int i = 0; i < gisDeptList.size(); i++) {
					Map<String, String> deptMap = gisDeptList.get(i);
					deptList.add(deptMap.get("deptNo"));
				}
			}
		}
		return deptList;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 将字符串化为标准时间
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-24 下午2:07:35
	 * 
	 * @param str
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private String getStandardTime(String str) {
		String time = str;
		if(StringUtil.isNotBlank(str) && str.length() == NumberConstants.NUMBER_4) {
			String timeHead = time.substring(0, 2);
			String timeTail = time.substring(2, NumberConstants.NUMBER_4);
			time = timeHead +":"+ timeTail;
		} 
		return time;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 根据行政区域查网点
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-25 上午10:52:47
	 * 
	 * @param districtCode
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@SuppressWarnings("unused")
	private List<String> getStartDeptListByDistrict(String districtCode) {
		if(StringUtil.isEmpty(districtCode)) {
			return null;
		}
		List<String> list = null;
		AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(districtCode);
		if(administrativeRegionsEntity != null) {
			if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
				list = composeStartDeptList(administrativeRegionsEntity.getCode());
				if(CollectionUtils.isEmpty(list) && StringUtil.isNotBlank(administrativeRegionsEntity.getParentDistrictCode())) {
					list = composeStartDeptList(administrativeRegionsEntity.getParentDistrictCode());
				}
			} else if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_CITY)) {
				list = composeStartDeptList(administrativeRegionsEntity.getCode());
			} 
		}
		return list;
	}
	
	@SuppressWarnings("unused")
	private List<String> getArrvDeptListByDistrict(String districtCode) {
		if(StringUtil.isEmpty(districtCode)) {
			return null;
		}
		List<String> list = null;
		AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(districtCode);
		if(administrativeRegionsEntity != null) {
			if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
				list = composeArrvDeptList(administrativeRegionsEntity.getCode());
				if(CollectionUtils.isEmpty(list) && StringUtil.isNotBlank(administrativeRegionsEntity.getParentDistrictCode())) {
					list = composeArrvDeptList(administrativeRegionsEntity.getParentDistrictCode());
				}
			} else if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_CITY)) {
				list = composeArrvDeptList(administrativeRegionsEntity.getCode());
			} 
		}
		return list;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 封装网点Code信息
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-25 上午11:05:13
	 * 
	 * @param districtCode
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
//	private List<String> composeDeptList(String districtCode) {
//		List<String> list = null;
//		List<String> types = new ArrayList<String>();
//		//偏线代理网点
//		types.add(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
//		//德邦自自有网点
//		types.add(DictionaryValueConstants.DEPPON_OWN_ORG);
//		//空运代理网点
//		types.add(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
//		List<CommonOrgEntity> commonOrgEntities = pricingOrgDao.queryOrgByDistrict(districtCode, FossConstants.ACTIVE, types);
//		if(CollectionUtils.isNotEmpty(commonOrgEntities)) {
//			list = new ArrayList<String>();
//			for (int i = 0; i < commonOrgEntities.size(); i++) {
//				CommonOrgEntity commonOrgEntity = commonOrgEntities.get(i);
//				list.add(commonOrgEntity.getCode());
//			}
//		} 
//		return list;
//	}
	/**
	 * 
	 * @Description: 封装网点Code信息
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-25 上午11:05:13
	 * 
	 * @param districtCode
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private List<String> composeStartDeptList(String districtCode) {
		List<String> list = null;
		List<CommonOrgEntity> commonOrgEntities = pricingOrgDao.queryStartDeptByDistrict(districtCode);
		if(CollectionUtils.isNotEmpty(commonOrgEntities)) {
			list = new ArrayList<String>();
			for (int i = 0; i < commonOrgEntities.size(); i++) {
				CommonOrgEntity commonOrgEntity = commonOrgEntities.get(i);
				list.add(commonOrgEntity.getCode());
			}
		} 
		return list;
	}
	
	private List<String> composeArrvDeptList(String districtCode) {
		List<String> list = null;
		List<CommonOrgEntity> commonOrgEntities = pricingOrgDao.queryArrvOrgByDistrict(districtCode, FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(commonOrgEntities)) {
			list = new ArrayList<String>();
			for (int i = 0; i < commonOrgEntities.size(); i++) {
				CommonOrgEntity commonOrgEntity = commonOrgEntities.get(i);
				list.add(commonOrgEntity.getCode());
			}
		} 
		return list;
	}
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 封装MAP
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-1-7 下午5:36:59
	 * 
	 * @param dispatchOrderEntity
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private static Map<String, Object> formatToMap(String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress){
		Map<String, Object> map = new HashMap<String, Object>();
		// 省份
		map.put("province", destinationProvinceCode);
		// 城市
		map.put("city", destinationCityCode);
		// 区县
		map.put("county", destinationCountyCode);
		// 其他详细地址
		map.put("otherAddress", destinationAddress);
		// 网点类型
		List<String> matchtypes = new ArrayList<String>();
		//自提
		matchtypes.add("pickup");
		//派送
		matchtypes.add("deliver");
		//收货
		matchtypes.add("leave");
		map.put("matchtypes", matchtypes); 
		// 运输类型
		List<String> transportways = new ArrayList<String>();
		//汽运专线
		transportways.add("motor_self");   
		//汽运偏线	
		transportways.add("motor_agency"); 
		 //空运
		transportways.add("air");         
		map.put("transportways", transportways);	 
		return map;
	}
	/**
	 * 
	 *
	 * @param administrativeRegionsService 
	 */
	public void setAdminRegionService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 设置 计价方案明细接口.
	 *
	 * @param priceCriteriaDetailService the new 计价方案明细接口
	 */
	public void setPriceCriteriaDetailService(IPriceCriteriaDetailService priceCriteriaDetailService) {
		this.priceCriteriaDetailService = priceCriteriaDetailService;
	}

	/**
	 * 设置 时效详细信息接口.
	 *
	 * @param effectivePlanDetailService the new 时效详细信息接口
	 */
	public void setEffectivePlanDetailService(IEffectivePlanDetailService effectivePlanDetailService) {
		this.effectivePlanDetailService = effectivePlanDetailService;
	}

	/**
	 * 获取 区域接口.
	 *
	 * @return the 区域接口
	 */
	public IRegionService getRegionService() {
		return regionService;
	}

	/**
	 * 设置 区域接口.
	 *
	 * @param regionService the new 区域接口
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * 获取 公布价DAO.
	 *
	 * @return the 公布价DAO
	 */
	public IPublishPriceDao getPublishPriceDao() {
		return publishPriceDao;
	}

	/**
	 * 设置 公布价DAO.
	 *
	 * @param publishPriceDao the new 公布价DAO
	 */
	public void setPublishPriceDao(IPublishPriceDao publishPriceDao) {
		this.publishPriceDao = publishPriceDao;
	}

	/**
	 * 设置 营业部DAO.
	 *
	 * @param saleDepartmentDao the new 营业部DAO
	 */
	public void setSaleDepartmentDao(ISaleDepartmentDao saleDepartmentDao) {
		this.saleDepartmentDao = saleDepartmentDao;
	}

	/**
	 * 设置 代理DAO.
	 *
	 * @param vehicleAgencyDeptDao the new 代理DAO
	 */
	public void setVehicleAgencyDeptDao(IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
		this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
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
	 * 设置 行政区域接口.
	 *
	 * @param administrativeRegionsService the new 行政区域接口
	 */
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 获取 产品接口.
	 *
	 * @return the 产品接口
	 */
	public IProductService getProductService() {
		return productService;
	}

	/**
	 * 设置 产品接口.
	 *
	 * @param productService the new 产品接口
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * 获取 时效详细信息接口.
	 *
	 * @return the 时效详细信息接口
	 */
	public IEffectivePlanDetailService getEffectivePlanDetailService() {
		return effectivePlanDetailService;
	}

	/**
	 * 获取 计价方案明细接口.
	 *
	 * @return the 计价方案明细接口
	 */
	public IPriceCriteriaDetailService getPriceCriteriaDetailService() {
		return priceCriteriaDetailService;
	}

	/**
	 * 设置 网点综合Dao.
	 *
	 * @param pricingOrgDao the new 网点综合Dao
	 */
	public void setPricingOrgDao(IPricingOrgDao pricingOrgDao) {
		this.pricingOrgDao = pricingOrgDao;
	}

	/**
	 * 设置 产品接口.
	 *
	 * @param goodsTypeService the new 产品接口
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}

	/**
	 * 设置 调用gis链接.
	 *
	 * @param gisUrl the new 调用gis链接
	 */
	public void setGisUrl(String gisUrl) {
		this.gisUrl = gisUrl;
	}

	/** 到达区域 */
    public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}
	
	/**
	    * 设置计价分段明细接口    
	    * @return popPriceDetailSectionDao
	    * @author 219413-Luomengxiang     2014-11-20
	    */
	public void setPopPriceDetailSectionDao(
			IPopPriceDetailSectionDao popPriceDetailSectionDao) {
		this.popPriceDetailSectionDao = popPriceDetailSectionDao;
	}
	/**
	 * 设置 空运区域接口.
	 *
	 * @param regionAirService the new 空运区域接口
	 */
	public void setRegionAirService(IRegionAirService regionAirService) {
		this.regionAirService = regionAirService;
	}
	public void setDistrictRegionCacheDeal(DistrictRegionCacheDeal districtRegionCacheDeal) {
		this.districtRegionCacheDeal = districtRegionCacheDeal;
	}
	public void setDistrictRegionService(IDistrictRegionService districtRegionService) {
		this.districtRegionService = districtRegionService;
	}
	public void setRegionValueAddService(
			IRegionValueAddService regionValueAddService) {
		this.regionValueAddService = regionValueAddService;
	}
    public void setPricingValueAddedService(
			IPricingValueAddedService pricingValueAddedService) {
		this.pricingValueAddedService = pricingValueAddedService;
	}
    public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
    
    public IPriceValuationDao getPriceValuationDao() {
		return priceValuationDao;
	}
	public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
		this.priceValuationDao = priceValuationDao;
	}
	
	@Override
	public List<PublishPriceEntity> queryPublishPriceDetailForSalesAndPx(String startDeptCode, String arrvRegionCode) {
    	if(StringUtils.isEmpty(startDeptCode) || StringUtils.isEmpty(arrvRegionCode)){
    		throw new BusinessException("传入的参数为空,请重试");
    	}
    	LOGGER.info("营业部公布价传入的参数，出发部门："+startDeptCode+"到达部门："+arrvRegionCode);
    	//普通营业部查询
    	OrgAdministrativeInfoEntity lastOutLoadOrgDepartment = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(arrvRegionCode);
    	if(lastOutLoadOrgDepartment == null){
    		QueryBillCacilateDto queryBillCacilateDto = new QueryBillCacilateDto();
        	//封装偏线查询参数
    		queryBillCacilateDto.setOriginalOrgCode(startDeptCode);
    		queryBillCacilateDto.setDestinationOrgCode(arrvRegionCode);
    		queryBillCacilateDto.setReceiveDate(new Date());
    		
    		//偏线或者空运
	    	OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService.queryOuterBranchByBranchCode(arrvRegionCode, null);
	    	if(outerBranchEntity != null){
	    		if(WaybillConstants.PX.equals(outerBranchEntity.getBranchtype())){
	    			//运输性质
	        		queryBillCacilateDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
	    		}else if(WaybillConstants.KY.equals(outerBranchEntity.getBranchtype())){
	    			//运输性质
	        		queryBillCacilateDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
	    		}
	    	}
    		//获取最终配载部门
    		queryLastLoadOrgCode(queryBillCacilateDto);
    		if(outerBranchEntity != null){
    			queryBillCacilateDto.setAgentDeptName(outerBranchEntity.getAgentDeptName());
    			if(StringUtils.equals(WaybillConstants.PX, outerBranchEntity.getBranchtype())){
    				//偏线
    	        	return this.queryPublishPriceDetailForPX(queryBillCacilateDto);
    				
    			}else{
    				//普通营业部，目前空运耶按照这种方式进行查询
    	        	return this.queryPublishPriceDetail(startDeptCode, arrvRegionCode, new Date());
    			}
    		}
    		return null;
    	}else{
    		//普通营业部
        	return this.queryPublishPriceDetail(startDeptCode, arrvRegionCode, new Date());
    	}
	}
    /**
	 * 
	 * 获取最终配载部门
	 * 
	 * @author WangQianJin
	 * @date 2014-01-01
	 */
	private void queryLastLoadOrgCode(QueryBillCacilateDto queryBillCacilateDto) {
		List<FreightRouteLineDto> freightRouteList = freightRouteService.queryFreightRouteBySourceTarget(queryBillCacilateDto.getOriginalOrgCode(), queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getProductCode(), new Date());
		// 得到途径外场和 始发营业部, 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
		List<String> addressInfoList = new ArrayList<String>();

		for (FreightRouteLineDto f : freightRouteList) {//拼接走货路径
			addressInfoList.add(f.getSourceCode() + "-" + f.getTargetCode());
		}

		// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的 外场集合 同时包含 出发部门到达部门
		List<String> departmentInfoList = removeDuplicateRoute(addressInfoList);
		if(CollectionUtils.isNotEmpty(departmentInfoList)){
			// 获取最终配置外场
			String lastOutLoadOrgCode = null;
			OrgAdministrativeInfoEntity lastOutLoadOrgDepartment = null;
			// 最终配置外场
			for (int i = departmentInfoList.size() - 1; i > -1; i--) {
				lastOutLoadOrgCode = departmentInfoList.get(i);
				lastOutLoadOrgDepartment = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastOutLoadOrgCode);
				if (lastOutLoadOrgDepartment == null) {
					continue;
				} else {
					if (lastOutLoadOrgDepartment.checkTransferCenter()) {// 如果是外场，那么获取外场
						break;
					} else {
						continue;
					}
				}
			}
			queryBillCacilateDto.setLastOrgCode(lastOutLoadOrgCode);	
		}
	}
	
	/**
	 * <p>
	 * 根据第一个外场 从外场集合串 取出单个外场列表 删除重复的
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-8 下午2:15:21
	 * @param addressInfoList
	 * @see
	 */
	private List<String> removeDuplicateRoute(List<String> routeList) {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < routeList.size(); i++) {
			temp.add(routeList.get(i).substring(0,
					routeList.get(i).indexOf("-")));
		}		
		return temp;
	}
	
	/**
	 * 查询公布价分段信息
	 * @param queryProductPriceDto
	 * @return
	 */
	@Override
	public List<ResultPriceDto> queryResultPrice(
			QueryProductPriceDto queryProductPriceDto) {
		
		return priceValuationDao.queryResultPrice(queryProductPriceDto);
	}
	
	
	public boolean queryPublishPriceDetailisProvince(String startCityCode, String destinationCityCode) {
		// 检验数据
		if (StringUtils.isEmpty(startCityCode)) {
			return true;
		}
		if (StringUtils.isEmpty(destinationCityCode)) {
			return true;
		}
		//查出发城市编码是否为省
		AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(startCityCode);
		
		if (StringUtil.equals(administrativeRegionsEntity.getDegree(),DictionaryValueConstants.DISTRICT_PROVINCE) || StringUtil.equals(administrativeRegionsEntity.getDegree(),DictionaryValueConstants.DISTRICT_NATION)) {
			return true;
		}
		//查到达城市编码是否为省
		administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(destinationCityCode);
		
		if (StringUtil.equals(administrativeRegionsEntity.getDegree(),DictionaryValueConstants.DISTRICT_PROVINCE) || StringUtil.equals(administrativeRegionsEntity.getDegree(),DictionaryValueConstants.DISTRICT_NATION)) {
			return true;
		}
		
		return false;
	}
	
	
}

