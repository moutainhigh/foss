/**
 * 
 * 
 *  总体原则：
 *  
 *  按照现有的框架，大多数还是按照单表
 *  
 *  （有特殊关联的表，不能单表下载，否则浪费流量和计算时间），
 *  
 *  增量下载数据。不过会要求客户端做更多的判断工作。现分别描述各表下载方法，以及客户端需要配合的工作。   
 *  
*1）可以简单的增量下载的数据，根据VERSION_NO 作比较，增量更新表有：
*
*产品 T_SRV_PRODUCT
*
*产品条目 T_SRV_PRODUCT_ITEM
*
*货物类型 T_SRV_GOODSTYPE
*
*计价条目 T_SRV_PRICING_ENTRY
*
*价格计算表达式 T_SRV_PRICE_RULE
*
*价格区域与部门对应关系  T_SRV_PRICE_REGION_ORG
*
*价格区域信息 T_SRV_PRICE_REGION 
*
*时效区域与部门对应关系 T_SRV_EFFECTIVE_REGION_ORG
*
*时效区域信息 T_SRV_EFFECTIVE_REGION
*
*市场活动 T_SRV_MARKETING_EVENT
*
*市场活动适用渠道 T_SRV_MARKETING_EVENT_CHANEL
*
*折扣适用起始目的组织网点 T_SRV_DISCOUNT_ORG
*
*2）不能简单处理的数据，需要客户端传入原区域id，和组织code：
*
*时效方案主信息 T_SRV_EFFECTIVE_PLAN
*
*时效方案详细信息 T_SRV_EFFECTIVE_PLAN_DETAIL
*
*原区域ID，取得方法（时效相关都是这个方法ID）：
*
*select DISTINCT DEPT_REGION_ID from  T_SRV_EFFECTIVE_PLAN ;
*
*如果能选出来2个，那我们程序肯定有问题，只能有一个。
*
*String 时效区域ID = regionService.findRegionOrgByDeptNo(orgCode, new Date(),null, PricingConstants.PRESCRIPTION_REGION);
*
*最大版本号，取得方法：
*
*select MAX(VERSION_NO) DEPT_REGION_ID from  T_SRV_EFFECTIVE_PLAN  where DEPT_REGION_ID =原区域ID（如果是时效方案详细信息 请换张表） ；
*
*同步处理顺序：
*
*第一步，例如，客户端在同步时效方案主信息把 原区域ID，
*
*和version_no ,orgCode，传上来。  
*
*第二步，服务端根据传入的组织code（orgCode），
*
*动态计算当前该组织属于的区域ID，并且与客户端传过来的区域ID比较。
*
*  如果区域相同，直接按照区域ID和version_no到数据库查询，然后做增量更新。
*  
*  如果区域不同，则需要删掉本地表中数据，
*  
*  然后用服务器端返回数据做全量更新(服务端使用新的区域ID，查询当前有效的，
*  
*  最新的时效信息，查找失效时间大于当前时间，并且已经生效的数据)。
*  
*  如果需要删除本地数据， 会在返回对象DataBundle上的属性needDeleteLocalData，明显设置为“Y”
*  
*第三步，客户端接受服务端返回的数据，做处理。
*
*如果needDeleteLocalData是“Y”，则先删掉本地数据，再把新的数据插入。删除逻辑是整表删除。
*
*删除逻辑是：delete from  T_SRV_EFFECTIVE_PLAN  where DEPT_REGION_ID =原区域ID ;
*
*delete from  T_SRV_EFFECTIVE_PLAN_DETAIL  where DEPT_REGION_ID =原区域ID ;
*
*3）情节特别严重的，需要复杂处理的数据，需要客户端按照数据不同的业务性质、原区域id，和组织code等传入，现分别描述如下：
*
*价格方案主信息  T_SRV_PRICE_PLAN
*
*该表处理方式，与时效方案一致，首先客户端传入原区域id，和组织code,以及数据最大版本号。
*
*原区域ID（这里是价格区域）要这样取(所有价格区域都这样取)：
*
*select  DISTINCT  T_SRV_PRICE_REGION_ID  from  T_SRV_PRICE_PLAN；
*
*如果能选出来2个，那我们程序肯定有问题，只能有一个。
*
*String 原价格区域ID = regionService.findRegionOrgByDeptNo(orgCode, new Date(),null, PricingConstants.PRICING_REGION);
*
*最大版本号，取得方法：
*
*select MAX(VERSION_NO)   from  T_SRV_PRICE_PLAN  where T_SRV_PRICE_REGION_ID =原价格区域ID ；
*
*同步处理顺序：
*
*第一步，客户端在同步价格方案主信息把 原价格区域ID，和version_no ,orgCode，传上来。  
*
*第二步，服务端根据传入的组织code（orgCode），动态计算当前该组织属于的区域ID，并且与客户端传过来的区域ID比较。  如果区域相同，直接按照区域ID和version_no到数据库查询，然后做增量更新。如果区域不同，则需要删掉本地表中数据，然后用服务器端返回数据做全量更新(服务端使用新的区域ID，查询当前有效的，最新的时效信息，查找失效时间大于当前时间，并且已经生效的数据)。如果需要删除本地数据，会在返回对象DataBundle上的属性needDeleteLocalData，明显设置为“Y”
*
*第三步，客户端接受服务端返回的数据，做处理。如果needDeleteLocalData是“Y”，则先删掉本地数据，再把新的数据插入。删除逻辑是整表删除。
*
*删除逻辑是：delete from  T_SRV_PRICE_PLAN  where T_SRV_PRICE_REGION_ID =原价格区域ID;
*
*
*计费规则 T_SRV_PRICING_VALUATION
*
*计价方式明细 T_SRV_PRICING_CRITERIA_DETAIL
*
*这两长表数据有很强的关联性，而且下载逻辑是一致的，所以，下载的时候，会一起下载下来。
*
*计费规则（T_SRV_PRICING_VALUATION）中包含了运费，增值服务，折扣等各种数据，建议一个按照不同数据类型，分不同接口下载，这样易于维护。所以，在下载该数据时（两张表），需要客户端提供：组织code，原价格区域id，各类数据最大版本号，服务端接到数据后，做相应处理。
*
*这几个分别接口是：运费下载接口，基础增值服务接口，产品增值服务接口，区域增值服务接口，折扣。
*
*原价格区域ID查找方式：
*
*所有区域ID都是从这句语句读取：select DISTINCT  T_SRV_PRICE_REGION_ID  from  T_SRV_PRICE_PLAN
*
*如果能选出来2个，那我们程序肯定有问题，只能有一个。
*
*String 原价格区域ID = regionService.findRegionOrgByDeptNo(orgCode, new Date(),null, PricingConstants.PRICING_REGION);
*
*最大版本号，取得方法：
*
*运费接口使用：
*
*select MAX(VERSION_NO)   from  T_SRV_PRICING_VALUATION where TYPE = PricingConstants.VALUATION_TYPE_PRICING and DEPT_REGION_ID=原价格区域ID  ；
*
*基础增值服务接口：
*
*select MAX(VERSION_NO)   from  T_SRV_PRICING_VALUATION where TYPE = PricingConstants. VALUATION_TYPE_BASICVALUEADDED；
*
*产品增值服务接口：
*
*select MAX(VERSION_NO)   from  T_SRV_PRICING_VALUATION where TYPE = PricingConstants. VALUATION_TYPE_PRODUCTVALUEADDED；
*
*区域增值服务接口：
*
*select MAX(VERSION_NO)   from  T_SRV_PRICING_VALUATION where TYPE = PricingConstants.VALUATION_TYPE_REGIONVALUEADDED and (DEPT_REGION_ID=原价格区域ID or  DEPT_REGION_ID= PricingConstants .ALL);
*
*同步处理顺序：
*
*第一步，客户端在同步数据时把 原区域ID，和version_no ,orgCode，传上来。  
*
*第二步，服务端根据传入的组织code（orgCode），动态计算当前该组织属于的区域ID，并且与客户端传过来的区域ID比较。 
*
* 如果区域相同，直接按照区域ID和version_no到数据库查询，然后做增量更新。
* 
* 如果区域不同，则需要删掉本地表中数据，然后用服务器端返回数据做全量更新（查找最新数据同之前时效方案描述一致）。
* 
* 如果需要删除本地数据，会在返回对象DataBundle上的属性needDeleteLocalData，明显设置为“Y”.
* 
* 注：如果是区域增值服务，服务端需要把始发网点为“ALL”的数据也要查出来，作为数据的补充。
* 
*第三步，客户端接受服务端返回的数据，做处理。如果needDeleteLocalData是“Y”，则先删掉本地数据，再把新的数据插入。删除逻辑是整表删除。
*
*客户端接受到的数据结构如下：
*
*包含T_SRV_PRICING_VALUATION数据 的List，每个T_SRV_PRICING_VALUATION数据的实体会包含一个子LIST，
*
*改子LIST即T_SRV_PRICING_CRITERIA_DETAIL表中的数据，直接更新或插入到该表中即可。
*
*客户端各类数据删除方法： 
*
*运费接口使用：
*
*delete from  T_SRV_PRICING_VALUATION where TYPE = PricingConstants. VALUATION_TYPE_PRICING and DEPT_REGION_ID=原价格区域ID ；
*
*基础增值服务接口（一般不会删除数据）：
*
*delete from T_SRV_PRICING_VALUATION where TYPE = PricingConstants. VALUATION_TYPE_BASICVALUEADDED  ；
*
*产品增值服务接口（一般不会删除数据）：
*
*delete from  T_SRV_PRICING_VALUATION where TYPE = PricingConstants. VALUATION_TYPE_PRODUCTVALUEADDED ；
*
*区域增值服务接口：
*
*delete from  T_SRV_PRICING_VALUATION where TYPE = PricingConstants. VALUATION_TYPE_REGIONVALUEADDED and (DEPT_REGION_ID=原价格区域ID or  DEPT_REGION_ID=PricingConstants.ALL);
*
*T_SRV_PRICING_CRITERIA_DETAIL 表中的数据在同步完成后，清除垃圾数据时统一处理。
*
*4）折扣相关信息特殊处理方案：
*
*与折扣相关的表包括以下：
*
*市场活动 T_SRV_MARKETING_EVENT（数据量不大，做简单增量更新）
*
*市场活动适用渠道 T_SRV_MARKETING_EVENT_CHANEL（数据量不大，做简单增量更新）
*
*折扣适用起始目的组织网点 T_SRV_DISCOUNT_ORG
*
*计费规则 T_SRV_PRICING_VALUATION
*
*
*计价方式明细 T_SRV_PRICING_CRITERIA_DETAIL
*
*下文主要介绍其余以上3张表数据的更新方法。
*
*此接口同之前接口一样，需要客户端传入价格区域ID，组织code,以及数据版本号，外加组织所在城市code：
*
*所有区域ID都是从这句语句读取：select DISTINCT  T_SRV_PRICE_REGION_ID  from  T_SRV_PRICE_PLAN
*
*
*如果能选出来2个，那我们程序肯定有问题，只能有一个。
*
*String 原价格区域ID = regionService.findRegionOrgByDeptNo(orgCode, new Date(),null, PricingConstants.PRICING_REGION);

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PriceDownLoadService.java
 * 
 * FILE NAME        	: PriceDownLoadService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountOrgDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountPriorityDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventChannelDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventDAO;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMinFeePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOuterPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionAirDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionArriveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountOrgConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @Description: 客户端基础数据下载 
 * 
 * PriceDownLoadService.java Create on 2012-12-25 下午3:37:11
 * 
 * Company:IBM
 * 
 * @author FOSSDP-sz
 * 
 * Copyright (c) 2012 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
public class PriceDownLoadService implements IPriceDownLoadService{
	 /**
		 * 
		 */
	private static final int BEFOREAMOUNT = 200;
	/**
	 * 
	 */
	private static final int THOUSAND = 1000;
    /**
     * 产品Dao
     */
    IProductDao productDao;
    /** 
     * 货物Dao
     */
    IGoodsTypeDao goodsTypeDao;
    /** 
     * 条目Dao
     */
    IProductItemDao productItemDao;    
    /** 
     * 区域Dao
     */
    IRegionDao regionDao;
    /** 
     * 空运区域Dao
     */
    IRegionAirDao regionAirDao;
    /**
     * 时效方案Dao
     */
    IEffectivePlanDao effectivePlanDao;
    /**
     * 时效方案明细Dao
     */
    IEffectivePlanDetailDao effectivePlanDetailDao;
    /**
     * 价格计费规则Dao
     */
    IPriceValuationDao priceValuationDao;  
    /**
     * 价格方案Dao
     */
    IPricePlanDao pricePlanDao;
    /**
     * 区域服务
     */
    IRegionService regionService;
    /**
     * 区域服务
     */
    IRegionAirService regionAirService;
    /**
     * 价格计算Dao
     */
    IPriceRuleDao priceRuleDao;
    /**
     * 计价方式明细Dao
     */
    IPriceCriteriaDetailDao priceCriteriaDetailDao;
    /**
     * 折扣方案（市场活动）Dao
     */
    IMarketingEventDAO marketingEventDao;
    /**
     * 折扣方案（市场活动渠道）Dao
     */
    IMarketingEventChannelDao marketingEventChannelDao;
    /**
     * 计价条目Dao
     */
    IPriceEntryDao priceEntryDao;
    /**
     * 折扣适用起始目的组织网点Dao
     */
    IDiscountOrgDao discountOrgDao;
    /**
     * 折扣优先级Dao
     */
    IDiscountPriorityDao discountPriorityDao;
    /**
     * 部门信息服务
     */
    IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 最低一票  zxy 20131010 BUG-55198
     */
    IMinFeePlanDao minFeePlanDao;
    /**
     * 增值区域  zxy 20131010 BUG-55198
     */
    IRegionValueAddDao regionValueAddDao;
    /**
     * 到达区域 zxy 20131010 BUG-55198
     */
    IRegionArriveDao regionArriveDao;
    /**
     * 偏线价格 zxy 20131010 BUG-55198
     */
    IOuterPriceDao outerPriceDao;
    /**
     * 设置 计价条目Dao.
     *
     * @param priceEntryDao the new 计价条目Dao
     */
    public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
		this.priceEntryDao = priceEntryDao;
	}
    /**
     * 设置 折扣方案（市场活动）Dao.
     *
     * @param marketingEventDao the new 折扣方案（市场活动）Dao
     */
    public void setMarketingEventDao(IMarketingEventDAO marketingEventDao) {
		this.marketingEventDao = marketingEventDao;
    }
	/**
	 * 设置 时效方案明细Dao.
	 *
	 * @param effectivePlanDetailDao the new 时效方案明细Dao
	 */
	public void setEffectivePlanDetailDao(IEffectivePlanDetailDao effectivePlanDetailDao) {
		this.effectivePlanDetailDao = effectivePlanDetailDao;
	}
	/**
	 * 设置 计价方式明细Dao.
	 *
	 * @param priceCriteriaDetailDao the new 计价方式明细Dao
	 */
	public void setPriceCriteriaDetailDao(
			IPriceCriteriaDetailDao priceCriteriaDetailDao) {
		this.priceCriteriaDetailDao = priceCriteriaDetailDao;
	}
	/**
	 * 设置 价格计算Dao.
	 *
	 * @param priceRuleDao the new 价格计算Dao
	 */
	public void setPriceRuleDao(IPriceRuleDao priceRuleDao) {
		this.priceRuleDao = priceRuleDao;
	}
	/**
	 * 设置 价格方案Dao.
	 *
	 * @param pricePlanDao the new 价格方案Dao
	 */
	public void setPricePlanDao(IPricePlanDao pricePlanDao) {
		this.pricePlanDao = pricePlanDao;
	}
    /**
     * 设置 价格计费规则Dao.
     *
     * @param priceValuationDao the new 价格计费规则Dao
     */
    public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
		this.priceValuationDao = priceValuationDao;
	}
	/**
	 * 设置 区域服务.
	 *
	 * @param regionService the new 区域服务
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	/**
	 * 设置 时效方案Dao.
	 *
	 * @param effectivePlanDao the new 时效方案Dao
	 */
	public void setEffectivePlanDao(IEffectivePlanDao effectivePlanDao) {
		this.effectivePlanDao = effectivePlanDao;
	}
	/**
	 * 设置 区域.
	 *
	 * @param regionDao the new 区域
	 */
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}
	/**
	 * 设置 条目.
	 *
	 * @param productItemDao the new 条目
	 */
	public void setProductItemDao(IProductItemDao productItemDao) {
        this.productItemDao = productItemDao;
    }
    /**
     * 设置 货物.
     *
     * @param goodsTypeDao the new 货物
     */
    public void setGoodsTypeDao(IGoodsTypeDao goodsTypeDao) {
        this.goodsTypeDao = goodsTypeDao;
    }
    /**
     * 设置 产品.
     *
     * @param productDao the new 产品
     */
    public void setProductDao(IProductDao productDao) {
        this.productDao = productDao;
    }
    /**
     * 设置 折扣方案（市场活动渠道）Dao.
     *
     * @param marketingEventChannelDao the new 折扣方案（市场活动渠道）Dao
     */
    public void setMarketingEventChannelDao(
			IMarketingEventChannelDao marketingEventChannelDao) {
		this.marketingEventChannelDao = marketingEventChannelDao;
	}
	/**
	 * 设置 折扣适用起始目的组织网点Dao.
	 *
	 * @param discountOrgDao the new 折扣适用起始目的组织网点Dao
	 */
	public void setDiscountOrgDao(IDiscountOrgDao discountOrgDao) {
		this.discountOrgDao = discountOrgDao;
	}
	/**
	 * 设置 部门信息服务.
	 *
	 * @param orgAdministrativeInfoService the new 部门信息服务
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 设置 折扣优先级Dao.
	 *
	 * @param discountPriorityDao the new 折扣优先级Dao
	 */
	public void setDiscountPriorityDao(IDiscountPriorityDao discountPriorityDao) {
		this.discountPriorityDao = discountPriorityDao;
	}
	/**
	 * 设置 空运区域Dao.
	 *
	 * @param regionAirDao the new 空运区域Dao
	 */
	public void setRegionAirDao(IRegionAirDao regionAirDao) {
		this.regionAirDao = regionAirDao;
	}
	/**
	 * 设置   区域服务
	 * @param regionAirService the regionAirService to set
	 */
	public void setRegionAirService(IRegionAirService regionAirService) {
		this.regionAirService = regionAirService;
	}
	
	/**
	 * 设置   最低一票服务
	 * @param regionAirService the regionAirService to set
	 */
	public void setMinFeePlanDao(IMinFeePlanDao minFeePlanDao) {
		this.minFeePlanDao = minFeePlanDao;
	}
	/**
	 * 设置   增值区域服务
	 * @param regionAirService the regionAirService to set
	 */
	public void setRegionValueAddDao(IRegionValueAddDao regionValueAddDao) {
		this.regionValueAddDao = regionValueAddDao;
	}
	/**
	 * 设置   到达区域服务
	 * @param regionAirService the regionAirService to set
	 */
	public void setRegionArriveDao(IRegionArriveDao regionArriveDao) {
		this.regionArriveDao = regionArriveDao;
	}
	public void setOuterPriceDao(IOuterPriceDao outerPriceDao) {
		this.outerPriceDao = outerPriceDao;
	}
	/**
	 * @Description: 折扣优先级(增量下载)
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午3:21:11
	 * 
	 * @param clientInfo
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public DataBundle downDiscountPriorityServerData(
			ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
		    DiscountPriorityEntity entity = new DiscountPriorityEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    return dataBundle.setObject(discountPriorityDao.selectByCondition(entity));
		}
	}
	/**
	 * 
	 * @Description: 产品信息数据下载(增量下载)
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午3:21:11
	 * 
	 * @param clientInfo
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
    public DataBundle downProductServerData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
		    ProductEntity entity = new ProductEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return dataBundle.setObject(productDao.findProduct(entity));
		}
    }
	/**
	 * 
     * @Description: 货物类型信息数据下载(增量下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downGoodsTypeServerData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
		    GoodsTypeEntity entity = new GoodsTypeEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(goodsTypeDao.findGoodsTypeByCondiction(entity));
		}
    }
    /**
     * 
     * @Description: 产品条目信息数据下载(增量下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downProductItemServerData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
		    ProductItemEntity entity = new ProductItemEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(productItemDao.downLoadByCondition(entity));
		}
	}
    /**
     * 
     * @Description: 时效区域信息下载(按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downEffectiveRegionServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
		    EffectiveRegionEntity entity = new EffectiveRegionEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionDao.selectEffectiveRegionByCondition(entity));
		}
    }
    /**
     * 
     * @Description:  时效区域与部门对应关系信息下载(按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downEffectiveRegionOrgDetailServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			EffectiveRegionOrgEntity entity = new EffectiveRegionOrgEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionDao.selectEffectiveRegionOrgByCondition(entity));
		}
    }
    /**
     * 
     * @Description: 时效主方案信息下载(按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downEffectivePlanServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
		String startRegionId = regionService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), 
				new Date(),null, PricingConstants.PRESCRIPTION_REGION);
		if(StringUtil.isNotBlank(startRegionId)) {
			EffectivePlanEntity entity = new EffectivePlanEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
				entity.setBusinessDate(new Date());
			} else if(clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime()!=null) {
				entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
			entity.setActive(FossConstants.ACTIVE);
			entity.setDeptRegionId(startRegionId);
			List<EffectivePlanEntity> effPlanList = effectivePlanDao.searchEffectivePlanByCondition(entity);
		    return dataBundle.setObject(effPlanList);
		} else {
			return dataBundle.setObject(null);
		}
    }
    /**
     * 
     * @Description: 时效方案详细下载(按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downEffectivePlanDetailServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
    	String startRegionId = regionService.findRegionOrgByDeptNo(clientInfo.getOrgCode(),new Date(),null, PricingConstants.PRESCRIPTION_REGION);
    	if(StringUtil.isNotBlank(startRegionId)) {
    		EffectivePlanDetailEntity entity =  new EffectivePlanDetailEntity();
    		if (!startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
			} else if(clientInfo.getLastUpdateTime()!=null) {
				entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
			entity.setActive(FossConstants.ACTIVE);
			entity.setDeptRegionId(startRegionId);
			
			if(FossConstants.YES.equals(clientInfo.getPagination())){
				//该表太大 需要分页
				return dataBundle.setObject(  effectivePlanDetailDao.queryEffectivePlanDetailByConditionByPage(entity
					,(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND));
			}else{
				return dataBundle.setObject(  effectivePlanDetailDao.queryEffectivePlanDetailByCondition(entity) );
			}
	    	
    	} else {
    		return dataBundle.setObject(null); 
    	}
    }
    /**
     * 
     * @Description: 价格区域信息下载(按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downPriceRegionServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceRegionEntity entity = new PriceRegionEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionDao.selectPriceRegionByCondition(entity));
		}
    }
    /**
     * 
     * @Description: 价格区域与部门对应关系信息下载(请按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downPriceRegionOrgDetailServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceRegioOrgnEntity entity = new PriceRegioOrgnEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionDao.selectPriceRegionOrgByCondition(entity));
		}
    }
    /**
     * 
     * @Description: 价格主方案信息下载(请按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downPricePlanServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
		String startRegionId = regionService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION);  
		if(StringUtil.isNotBlank(startRegionId)) {
			PricePlanEntity entity = new PricePlanEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
			} else if(clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime()!=null) {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setPriceRegionId(startRegionId);
	    	return dataBundle.setObject(pricePlanDao.queryPricePlanBatchInfo(entity));
		} else {
			return dataBundle.setObject(null);
		}
    }
    /**
     * 
     * @Description: 价格计算表达式信息下载(增量下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downPriceRuleServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceRuleEntity entity = new PriceRuleEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(priceRuleDao.selectPriceRuleByCondition(entity));
		}
    }
    /**
     * 
     * @Description: 计价条目信息下载(增量下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
	@Override
	public DataBundle downPriceEntity(ClientUpdateDataPack clientInfo) {		
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceEntity entity = new PriceEntity();		
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(priceEntryDao.searchPriceEntryByConditions(entity));
		}
	}
	/**
	 * 
     * @Description: 市场活动信息下载(折扣活动),(增量下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
	@Override
	public DataBundle downMarketingEvent(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			MarketingEventEntity entity = new MarketingEventEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(marketingEventDao.selectMarketingEventList(entity));
		}
	}
	 /**
	  * 
     * @Description: 市场活动渠道下载(增量下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
	@Override
	public DataBundle downMarketingEventChannel(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			MarketingEventChannelEntity entity = new MarketingEventChannelEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(marketingEventChannelDao.selectByCondition(entity));
		}
	}
	/**
	 * 
     * @Description: 打折处理程序下载，按照始发区域下载
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
	//已经不下载了  所以不用修改该方法 
	@Override
	public DataBundle downDiscountProgram(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}  
		List<DiscountOrgEntity> listRegions = null;
		List<DiscountOrgEntity> listDepts = null;
		List<DiscountOrgEntity> listCities = null;
		List<DiscountOrgEntity> list = new ArrayList<DiscountOrgEntity>();
		//根据传入参数获取始发区域ID
		String startRegionId = regionService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION);
		if(StringUtil.isNotBlank(startRegionId)) {
			DiscountOrgEntity entity = new DiscountOrgEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
				entity.setBeginTime(new Date());
				entity.setEndTime(new Date());
			} else if (clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime() != null) {
				entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
			entity.setActive(FossConstants.ACTIVE);
			//区域
			entity.setDeptOrgId(startRegionId);
			entity.setDeptOrgTypeCode(DiscountOrgConstants.DISCOUNT_ORG_REGION);
			listRegions = discountOrgDao.selectByCondition(entity);
			entity.setDeptOrgId(null);
			//城市
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(clientInfo.getOrgCode());
			if(orgAdministrativeInfoEntity != null && orgAdministrativeInfoEntity.getCityCode() != null) {
				String startCityCode = orgAdministrativeInfoEntity.getCityCode();
				entity.setDeptOrgCode(startCityCode);
				entity.setDeptOrgTypeCode(DiscountOrgConstants.DISCOUNT_ORG_CITY);
				listCities = discountOrgDao.selectByCondition(entity);
			}
			//部门
			entity.setDeptOrgCode(clientInfo.getOrgCode());
			entity.setDeptOrgTypeCode(DiscountOrgConstants.DISCOUNT_ORG_DEPT);
			listDepts = discountOrgDao.selectByCondition(entity);
			
			if(CollectionUtils.isNotEmpty(listRegions)) {
				for (int i = 0; i < listRegions.size(); i++) {
					DiscountOrgEntity discountOrgEntity = listRegions.get(i);
					PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(discountOrgEntity.gettSrvPricingValuationId());
					if(priceValuationEntity != null && PricingConstants.VALUATION_TYPE_DISCOUNT.equals(priceValuationEntity.getType())) {
						PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
			    		priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
						List<PriceCriteriaDetailEntity> criteriaDetailEntities = priceCriteriaDetailDao.findPriceCriteriaDetailByCondition(priceCriteriaDetailEntity);
						if(CollectionUtils.isNotEmpty(criteriaDetailEntities)) {
							priceValuationEntity.setCriteriaDetailEntities(criteriaDetailEntities);
							discountOrgEntity.setPriceValuationEntity(priceValuationEntity);
						}
					}
					list.add(discountOrgEntity);
				}
			}
			if(CollectionUtils.isNotEmpty(listCities)) {
				for (int i = 0; i < listCities.size(); i++) {
					DiscountOrgEntity discountOrgEntity = listCities.get(i);
					PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(discountOrgEntity.gettSrvPricingValuationId());
					if(priceValuationEntity != null && PricingConstants.VALUATION_TYPE_DISCOUNT.equals(priceValuationEntity.getType())) {
						PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
			    		priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
						List<PriceCriteriaDetailEntity> criteriaDetailEntities = priceCriteriaDetailDao.findPriceCriteriaDetailByCondition(priceCriteriaDetailEntity);
						if(CollectionUtils.isNotEmpty(criteriaDetailEntities)) {
							priceValuationEntity.setCriteriaDetailEntities(criteriaDetailEntities);
							discountOrgEntity.setPriceValuationEntity(priceValuationEntity);
						}
					}
					list.add(discountOrgEntity);
				}
			}
			if(CollectionUtils.isNotEmpty(listDepts)) {
				for (int i = 0; i < listDepts.size(); i++) {
					DiscountOrgEntity discountOrgEntity = listDepts.get(i);
					PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(discountOrgEntity.gettSrvPricingValuationId());
					if(priceValuationEntity != null && PricingConstants.VALUATION_TYPE_DISCOUNT.equals(priceValuationEntity.getType())) {
						PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
			    		priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
						List<PriceCriteriaDetailEntity> criteriaDetailEntities = priceCriteriaDetailDao.findPriceCriteriaDetailByCondition(priceCriteriaDetailEntity);
						if(CollectionUtils.isNotEmpty(criteriaDetailEntities)) {
							priceValuationEntity.setCriteriaDetailEntities(criteriaDetailEntities);
							discountOrgEntity.setPriceValuationEntity(priceValuationEntity);
						}
					}
					list.add(discountOrgEntity);
				}
			}
			return dataBundle.setObject(list);
		} else {
			return dataBundle.setObject(null);
		}
	}
	/**
	 * 
     * @Description: 计价规则 运费信息下载，按照始发区域下载
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
	@Override
    public DataBundle downPricingValuationPricingServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
		String startRegionId = regionService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION); 
		if(StringUtil.isNotBlank(startRegionId)) {
			PriceValuationEntity entity = new PriceValuationEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
				entity.setBeginTime(new Date());
			    entity.setEndTime(new Date());
			} else if (clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime() != null) {
				entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
			entity.setDeptRegionId(startRegionId);
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setType(PricingConstants.VALUATION_TYPE_PRICING);
		    
		    if(FossConstants.YES.equals(clientInfo.getPagination())){
		    	return dataBundle.setObject(priceValuationDao.downPricingValuationPricingAutoServerDataByPage(entity, 
		    			(clientInfo.getSyncPage())*THOUSAND  -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND));	
		    }else{
		    	return dataBundle.setObject(priceValuationDao.downPricingValuationPricingAutoServerData(entity));
		    }
		} else {
			return dataBundle.setObject(null);
		}
		
    }
	/**
	 * 
     * @Description: 计价规则  基础增值服务信息下载，按照始发区域下载
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     * 
     */
	@Override
	public DataBundle downPricingValuationBasicValueAddServerData(
			ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
    	    return dataBundle;
    	} 
		PriceValuationEntity entity = new PriceValuationEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
	    List<PriceValuationEntity> priceValList = null;
	    if(FossConstants.YES.equals(clientInfo.getPagination())){
	    	 priceValList =  priceValuationDao.queryByCoditionForDownloadByPage(
	    			 entity, (clientInfo.getSyncPage())*THOUSAND-(BEFOREAMOUNT* clientInfo.getSyncPage())   , THOUSAND);	
	    	
	    }else{
	    	 priceValList =  priceValuationDao.queryByCoditionForDownload(entity);	
	    }
    	return dataBundle.setObject(priceValList);
	}
	/**
	 * 
     * @Description: 计价规则  产品增值服务信息下载，按照始发区域下载
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     * 
     */
	@Override
	public DataBundle downPricingValuationProductValueAddServerData(
			ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
		PriceValuationEntity entity = new PriceValuationEntity();
		if (clientInfo.getLastUpdateTime() != null) {
			entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		}
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setType(PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED);
	    List<PriceValuationEntity> priceValList =null; 
	    
	    if(FossConstants.YES.equals(clientInfo.getPagination())){
	    	 priceValList =  priceValuationDao.queryByCoditionForDownloadByPage(
	    			 entity, (clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);	
	    	
	    }else{
	    	 priceValList =  priceValuationDao.queryByCoditionForDownload(entity);		
	    }
    	return dataBundle.setObject(priceValList);
	}
	/**
	 * 
     * @Description: 计价规则  区域增值服务信息下载，按照始发区域下载
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     * 
     */
	@Override
	public DataBundle downPricingValuationRegionValueAddServerData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
		String startRegionId = regionService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION); 
		if(StringUtil.isNotBlank(startRegionId)) {
			PriceValuationEntity entity = new PriceValuationEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
				entity.setBeginTime(new Date());
				entity.setEndTime(new Date());
			} else if(clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime()!=null) {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		    entity.setDeptRegionId(startRegionId);//ee17fcab-0b35-4e78-8b2f-b2cb671e870a
		    List<PriceValuationEntity> valuationEntities = new ArrayList<PriceValuationEntity>();
		    /**
		     * 根据指定区域查询
		     */
		    List<PriceValuationEntity> priceValList1 =  null;
		    if(FossConstants.YES.equals(clientInfo.getPagination())){
		    	priceValList1 =  priceValuationDao.queryByCoditionForDownloadByPage(
		    			entity, (clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);	
		    	
		    }else{
		    	priceValList1 =  priceValuationDao.queryByCoditionForDownload(entity);		
		    }
		    valuationEntities.addAll(priceValList1);
		    /**
		     * 根据区域值为ALL查询
		     */
		    entity.setDeptRegionId("ALL");
		    List<PriceValuationEntity> priceValList2 = null;
		    if(FossConstants.YES.equals(clientInfo.getPagination())){
		    	priceValList2 =  priceValuationDao.queryByCoditionForDownloadByPage(
		    			entity, (clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);	
		    	
		    }else{
		    	priceValList2 =  priceValuationDao.queryByCoditionForDownload(entity);		
		    }
		    
		    valuationEntities.addAll(priceValList2);
	    	return dataBundle.setObject(valuationEntities);
		} else {
			return dataBundle.setObject(null);
		}
	}
	/**
	 * 
     * @Description: 空运价格区域信息下载(按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public DataBundle downPriceRegionAirServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceRegionAirEntity entity = new PriceRegionAirEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionAirDao.selectPriceRegionByCondition(entity));
		}
    }
    /**
     * 
     * @Description: 空运价格区域与部门对应关系信息下载(请按照始发区域下载)
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 下午3:21:28
     * 
     * @param clientInfo
     * 
     * @return
     * 
     * @version V1.0
     * 
     */
    @Override
    public DataBundle downPriceRegionOrgAirDetailServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceRegionOrgAirEntity entity = new PriceRegionOrgAirEntity();
		    if(clientInfo.getLastUpdateTime()!=null){
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionAirDao.selectPriceRegionOrgByCondition(entity));
		}
    }
    /**
     * 
     * @Description: 计价规则 空运 运费信息下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    @Override
    public DataBundle downPricingValuationPricingAirServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
		String startRegionId = regionAirService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION); 
		if(StringUtil.isNotBlank(startRegionId)) {
			PriceValuationEntity entity = new PriceValuationEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
				entity.setBeginTime(new Date());
			    entity.setEndTime(new Date());
			} else if (clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime() != null) {
				entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
			entity.setDeptRegionId(startRegionId);
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setType(PricingConstants.VALUATION_TYPE_PRICING);
		    List<PriceValuationEntity> priceValList = null;
		    if(FossConstants.YES.equals(clientInfo.getPagination())){
		    	priceValList =  priceValuationDao.downPricingValuationPricingAirServerDataByPage(
		    			entity, (clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);
		    }else{
		    	priceValList =   priceValuationDao.downPricingValuationPricingAirServerData(entity);			
		    }
	    	return dataBundle.setObject(priceValList);
		} else {
			return dataBundle.setObject(null);
		}
    }
    /**
     * 
     * @Description: 空运价格区域信息下载(按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    @Override
    public DataBundle downPriceAirPlanServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}
    	//根据传入参数获取始发区域ID
		String startRegionId = regionAirService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION);  
		if(StringUtil.isNotBlank(startRegionId)) {
			PricePlanEntity entity = new PricePlanEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
			} else if(clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime()!=null) {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setTransportFlag("1");
		    entity.setPriceRegionId(startRegionId);
	    	return dataBundle.setObject(pricePlanDao.queryPricePlanBatchInfo(entity));
		} else {
			return dataBundle.setObject(null);
		}
    }
    
    /**
	 *  最低一票-暂时不实现功能
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
	@Override
	public DataBundle downMinFeePlanServerData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}
    	return dataBundle;
	}
	
	/**
	 *  增值区域
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
	@Override
	public DataBundle downPriceRegionValueAddData(
			ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}
		PriceRegionValueAddEntity entity = new PriceRegionValueAddEntity();
		if(clientInfo.getLastUpdateTime()!=null){
	    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
	    }
		List<PriceRegionValueAddEntity> prvLst = regionValueAddDao.selectPriceRegionByCondition(entity);
		dataBundle.setObject(prvLst);
		return dataBundle;
	}
	
	/**
	 *  偏线价格
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
	@Override
	public DataBundle downOuterPriceData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}
    	OuterPriceEntity entity = new OuterPriceEntity();
    	if(clientInfo.getLastUpdateTime()!=null){
	    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
	    }
		List<OuterPriceEntity> entityLst = outerPriceDao.searchOuterPriceByParamEntity(entity);
		dataBundle.setObject(entityLst);
		return dataBundle;
	}
	
	/**
	 *  到达区域
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
	@Override
	public DataBundle downPriceRegionArriveData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}
    	PriceRegionArriveEntity entity = new PriceRegionArriveEntity();
    	if(clientInfo.getLastUpdateTime()!=null){
	    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
	    }
		List<PriceRegionArriveEntity> entityLst = regionArriveDao.selectPriceRegionByCondition(entity);
		dataBundle.setObject(entityLst);
		return dataBundle;
	}
	
	/**
	 *  下载到达区域和机构关联数据
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
	@Override
	public DataBundle downPriceRegionOrgArriveData(
			ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
		if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}
		PriceRegionOrgArriveEntity entity = new PriceRegionOrgArriveEntity();
		if(clientInfo.getLastUpdateTime()!=null){
	    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
	    }
		List<PriceRegionOrgArriveEntity> entityLst = regionArriveDao.selectPriceRegionOrgByCondition(entity);
		dataBundle.setObject(entityLst);
		return dataBundle;
	}
	
	/**
	 *  下载增值区域与机构关联数据
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
	@Override
	public DataBundle downPriceRegionOrgValueAddData(
			ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	}
    	PriceRegionOrgValueAddEntity entity = new PriceRegionOrgValueAddEntity();
    	if(clientInfo.getLastUpdateTime()!=null){
	    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
	    }
		List<PriceRegionOrgValueAddEntity> entityLst = regionValueAddDao.selectPriceRegionOrgByCondition(entity);
		dataBundle.setObject(entityLst);
		return dataBundle;
	}
}
