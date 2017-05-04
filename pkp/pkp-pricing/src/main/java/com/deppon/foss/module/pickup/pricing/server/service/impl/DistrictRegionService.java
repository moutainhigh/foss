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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PriceEntryService.java
 * 
 * FILE NAME        	: PriceEntryService.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPricingOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDistrictRegionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionAirDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionArriveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionExpressDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.server.cache.DistrictRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.EffectiveRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.EffectiveRegionExpressCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.EffectiveRegionOrgCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.EffectiveRegionOrgExpressCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionAirCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionArriveCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionBigArriveCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionBigCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionBigOrgArriveCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionBigOrgCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionEcGoodsArriveCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionEcGoodsCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionEcGoodsOrgArriveCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionEcGoodsOrgCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionExpressCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionOrgAirCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionOrgArriveCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionOrgCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionOrgExpressCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * @Description: 行政区域与时效、汽运、空运价格区域关系表Service
 * DistrictRegionService.java Create on 2013-4-17 下午4:53:23
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DistrictRegionService implements IDistrictRegionService {
    /**
     * 日志信息
     */
    private static final Logger log = Logger.getLogger(DistrictRegionService.class);
    

	@Inject
    private IDistrictRegionDao districtRegionDao;
    
    @Inject
    private IPricingOrgDao pricingOrgDao;
    
    private DistrictRegionCacheDeal districtRegionCacheDeal;
    
    @Inject
    private IRegionDao regionDao;
    
    @Inject
    private IRegionBigGoodsDao  regionBigGoodsDao;
    
    @Inject
	private IRegionEcGoodsDao  regionEcGoodsDao;
    
    @Inject
    private IRegionAirDao regionAirDao;
    
    /**
     * 到达价格区域DAO
     */
    private IRegionArriveDao regionArriveDao;
    /**
     * 精准大票到达价格区域DAO
     */
    private IRegionBigGoodsArriveDao regionBigGoodsArriveDao;
    /**
     * 首续重到达价格区域DAO
     */
    private IRegionEcGoodsArriveDao regionEcGoodsArriveDao;
	/**
     * 到达区域与部门CacheDeal
     */
    private PriceRegionOrgArriveCacheDeal priceRegionOrgArriveCacheDeal;
    /**
     * 精准大票到达区域与部门CacheDeal
     */
    private PriceRegionBigOrgArriveCacheDeal priceRegionBigOrgArriveCacheDeal;
    /**
     * 首续重到达区域与部门CacheDeal
     */
    private PriceRegionEcGoodsOrgArriveCacheDeal priceRegionEcGoodsOrgArriveCacheDeal;
	/**                                          
     * 到达区域CacheDeal
     */
    private PriceRegionArriveCacheDeal priceRegionArriveCacheDeal;
    /**
     * 首续重到达区域CacheDeal
     */
    private PriceRegionEcGoodsArriveCacheDeal priceRegionEcGoodsArriveCacheDeal;
	/**
     * 精准大票到达区域CacheDeal
     */
    private PriceRegionBigArriveCacheDeal priceRegionBigArriveCacheDeal;
	/**
	 * 空运价格区域与部门 缓存处理
	 */
	private PriceRegionOrgAirCacheDeal priceRegionOrgAirCacheDeal;
	/**
	 * 精准大票价格区域与部门缓存处理
	 */
	private PriceRegionBigOrgCacheDeal  priceRegionBigOrgCacheDeal;
	/**
	 * 首续重价格区域与部门缓存处理
	 */
	private PriceRegionEcGoodsOrgCacheDeal priceRegionEcOrgCacheDeal;
	/**
	 * 空运价格区域 缓存处理
	 */
	private PriceRegionAirCacheDeal priceRegionAirCacheDeal;
	/**
	 * 精准大票价格区域 缓存处理
	 */
	private PriceRegionBigCacheDeal priceRegionBigCacheDeal;
	/**
	 * 首续重价格区域 缓存处理
	 */
	private PriceRegionEcGoodsCacheDeal priceRegionEcCacheDeal;
	/**
	 * 时效区域与部门 缓存处理
	 */
	private EffectiveRegionOrgCacheDeal effectiveRegionOrgCacheDeal;
	/**
	 * 时效区域 缓存处理
	 */
	private EffectiveRegionCacheDeal effectiveRegionCacheDeal;
	/**
	 * 
	 */
	private PriceRegionOrgCacheDeal priceRegionOrgCacheDeal;
	/**
	 * 价格区域 缓存处理
	 */
	private PriceRegionCacheDeal priceRegionCacheDeal;
	
	/**
	 * 组织机构接口
	 */
	@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 
	 */
	@Inject
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	private IRegionExpressDao regionExpressDao;
	/**
	 * 快递时效区域与部门 缓存处理
	 */
	private EffectiveRegionOrgExpressCacheDeal effectiveRegionOrgExpressCacheDeal;
	/**
	 * 快递时效区域 缓存处理
	 */
	private EffectiveRegionExpressCacheDeal effectiveRegionExpressCacheDeal;
	
	/**
	 * 快递价格区域与部门  缓存处理
	 */
	private PriceRegionOrgExpressCacheDeal priceRegionOrgExpressCacheDeal;
	/**
	 * 快递价格区域 缓存处理
	 */
	private PriceRegionExpressCacheDeal priceRegionExpressCacheDeal;
	
	
	/**
     * 
     * @Description: 查询所有行政区域与时效、汽运、空运价格区域关系
     * @author FOSSDP-sz
     * @date 2013-4-19 下午3:29:18
     * @return
     * @version V1.0
     */
	 @Override
     public List<DistrictRegionEntity> searchDistrictRegion() {
		 return districtRegionDao.selectByDistrictCode();
	 }
	/**
	 * 
	 * @Description: 添加记录
	 * @author FOSSDP-sz
	 * @date 2013-4-17 下午5:01:41
	 * @param districtCode
	 * @version V1.0
	 */
	@Override
    public void addDistrictRegion(String districtCode) {
	    Map<String, List<PriceRegionEntity>> regionMap = getRegionByDistrictCode(districtCode);
	    //汽运时效
	    String effectiveRegionIds = null;
		String effectiveRegionNames = null;
		//快递时效
		String effectiveExpressRegionIds = null;
		String effectiveExpressRegionNames = null;
		//汽运价格  - 出发
		String priceAutoRegionIds = null;
		String priceAutoRegionNames = null;
		//汽运价格  - 到达
		String priceArriveRegionIds = null;
		String priceArriveRegionNames = null;
		//空运价格
		String priceAirRegionIds = null;
		String priceAirRegionNames = null;
		//快递价格
		String priceExpressRegionIds = null;
		String priceExpressRegionNames = null;
		//精准大票价格  - 出发
		String priceBigRegionIds = null;
		String priceBigRegionNames = null;
		//精准大票价格  - 到达
		String priceBigArriveRegionIds = null;
		String priceBigArriveRegionNames = null;
		//首续重价格  - 出发
		String priceEcRegionIds = null;
		String priceEcRegionNames = null;
		//首续重价格  - 到达
		String priceEcArriveRegionIds = null;
		String priceEcArriveRegionNames = null;
		// 遍历赋值
		if(regionMap != null && regionMap.size() > 0) {
			StringBuilder sb1 = null;
			StringBuilder sb2 = null;
			//时效区域
    		Set<Entry<String, List<PriceRegionEntity>>> regionSet = regionMap.entrySet();
    		for (Entry<String, List<PriceRegionEntity>> entry : regionSet) {
    			List<PriceRegionEntity> list = entry.getValue();
    			String ids  =null;
    			String names = null;
    			if (CollectionUtils.isNotEmpty(list)) {
    				sb1 = new StringBuilder();
    				sb2 = new StringBuilder();
    				for (int i = 0; i < list.size(); i++) {
    					PriceRegionEntity regionEntity = list.get(i);
    					sb1.append(regionEntity.getId()).append(",");
    					sb2.append(regionEntity.getRegionName()).append(",");
    				}
    				ids = sb1.substring(0, sb1.length()-1);
    				names = sb2.substring(0, sb2.length()-1);
    			}
    			if(StringUtils.equals(entry.getKey(), "effectiveRegion")) {
    				effectiveRegionIds = ids;
    				effectiveRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "effectiveExpressRegion")){
    				effectiveExpressRegionIds = ids;
    				effectiveExpressRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceRegion")){
    				priceAutoRegionIds = ids; 
    				priceAutoRegionNames  = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceExpressRegion")){
    				priceExpressRegionIds = ids;
    				priceExpressRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceRegionAir")){
    				priceAirRegionIds = ids;
    				priceAirRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceArriveRegion")){
    				priceArriveRegionIds = ids;
    				priceArriveRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceRegionBig")){
    				priceBigRegionIds = ids;
    				priceBigRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceArriveRegionBig")){
    				priceBigArriveRegionIds = ids;
    				priceBigArriveRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceRegionEc")){
    				priceEcRegionIds = ids;
    				priceEcRegionNames = names;
    			}else if(StringUtils.equals(entry.getKey(), "priceArriveRegionEc")){
    				priceEcArriveRegionIds = ids;
    				priceEcArriveRegionNames = names;
    			}
    			sb1 = null;
    			sb2 = null;
    		}
    	}
		//根据编码查询数据
		DistrictRegionEntity districtRegionEntity = districtRegionDao.selectByDistrictCode(districtCode);
		//查询出的实体如果为空那么就新增，否则进行更新操作，如下：
		if(districtRegionEntity == null) {
			districtRegionEntity = new DistrictRegionEntity();
			districtRegionEntity.setId(UUIDUtils.getUUID());
			districtRegionEntity.setCreateTime(new Date());
			this.setDistrictRegionEntity(districtCode, effectiveRegionIds,
					effectiveRegionNames, effectiveExpressRegionIds,
					effectiveExpressRegionNames, priceAutoRegionIds,
					priceAutoRegionNames, priceArriveRegionIds,
					priceArriveRegionNames, priceAirRegionIds,
					priceAirRegionNames, priceExpressRegionIds,
					priceExpressRegionNames, priceBigRegionIds,
					priceBigRegionNames, priceBigArriveRegionIds,
					priceBigArriveRegionNames, priceEcRegionIds,
					priceEcRegionNames, priceEcArriveRegionIds,
					priceEcArriveRegionNames, districtRegionEntity);
			districtRegionDao.insertDistrictRegion(districtRegionEntity);
		} else {
			this.setDistrictRegionEntity(districtCode, effectiveRegionIds,
					effectiveRegionNames, effectiveExpressRegionIds,
					effectiveExpressRegionNames, priceAutoRegionIds,
					priceAutoRegionNames, priceArriveRegionIds,
					priceArriveRegionNames, priceAirRegionIds,
					priceAirRegionNames, priceExpressRegionIds,
					priceExpressRegionNames, priceBigRegionIds,
					priceBigRegionNames, priceBigArriveRegionIds,
					priceBigArriveRegionNames, priceEcRegionIds,
					priceEcRegionNames, priceEcArriveRegionIds,
					priceEcArriveRegionNames, districtRegionEntity);
			districtRegionDao.updateDistrictRegion(districtRegionEntity);
		}
		//加入缓存
		districtRegionCacheDeal.getDistrictRegionCache().invalid(districtCode);
	}
	//给DistrictRegionEntity中的变量赋值
	private void setDistrictRegionEntity(String districtCode,
			String effectiveRegionIds, String effectiveRegionNames,
			String effectiveExpressRegionIds,
			String effectiveExpressRegionNames, String priceAutoRegionIds,
			String priceAutoRegionNames, String priceArriveRegionIds,
			String priceArriveRegionNames, String priceAirRegionIds,
			String priceAirRegionNames, String priceExpressRegionIds,
			String priceExpressRegionNames, String priceBigRegionIds,
			String priceBigRegionNames, String priceBigArriveRegionIds,
			String priceBigArriveRegionNames, String priceEcRegionIds,
			String priceEcRegionNames, String priceEcArriveRegionIds,
			String priceEcArriveRegionNames,
			DistrictRegionEntity districtRegionEntity) {
		districtRegionEntity.setDistrictCode(districtCode);
		districtRegionEntity.setEffectiveRegionIds(effectiveRegionIds);
		districtRegionEntity.setEffectiveRegionNames(effectiveRegionNames);
		districtRegionEntity.setExpressEffectiveRegionIds(effectiveExpressRegionIds);
		districtRegionEntity.setExpressEffectiveRegionNames(effectiveExpressRegionNames);
		districtRegionEntity.setPriceAutoRegionIds(priceAutoRegionIds);
		districtRegionEntity.setPriceAutoRegionNames(priceAutoRegionNames);
		districtRegionEntity.setPriceArriveRegionIds(priceArriveRegionIds);
		districtRegionEntity.setPriceArriveRegionNames(priceArriveRegionNames);
		districtRegionEntity.setPriceAirRegionIds(priceAirRegionIds);
		districtRegionEntity.setPriceAirRegionNames(priceAirRegionNames);
		districtRegionEntity.setExpressPriceRegionIds(priceExpressRegionIds);
		districtRegionEntity.setExpressPriceRegionNames(priceExpressRegionNames);
		districtRegionEntity.setPriceBigRegionIds(priceBigRegionIds);
		districtRegionEntity.setPriceBigRegionNames(priceBigRegionNames);
		districtRegionEntity.setPriceBigArriveRegionIds(priceBigArriveRegionIds);
		districtRegionEntity.setPriceBigArriveRegionNames(priceBigArriveRegionNames);
		districtRegionEntity.setPriceEcRegionIds(priceEcRegionIds);
		districtRegionEntity.setPriceEcRegionNames(priceEcRegionNames);
		districtRegionEntity.setPriceEcArriveRegionIds(priceEcArriveRegionIds);
		districtRegionEntity.setPriceEcArriveRegionNames(priceEcArriveRegionNames);
		districtRegionEntity.setModifyTime(new Date());
	}
	/**
	 * 
	 * @Description: 根据行政区域获取时效、汽运、空运价格区域关系信息
	 * @author FOSSDP-sz
	 * @date 2013-4-17 下午4:47:52
	 * @param districtCode
	 * @return
	 * @version V1.0
	 */
	private Map<String, List<PriceRegionEntity>> getRegionByDistrictCode(String districtCode) {
		if (StringUtils.isBlank(districtCode)) {
    	    return null;
    	}
    	Map<String, List<PriceRegionEntity>> map = null;
    	List<String> deptList = this.composeDeptList(districtCode);
    	if(CollectionUtils.isNotEmpty(deptList)) {
    		map = new HashMap<String, List<PriceRegionEntity>>();
			//遍历封装map
			traverseMap(map, deptList);
		}
    	return map;
    }

	//遍历封装map
	private void traverseMap(Map<String, List<PriceRegionEntity>> map, List<String> deptList) {
		for (String deptCode : deptList) {
            PriceRegionEntity effectiveRegionEntity = this.findRegionOrgByDeptCode(deptCode, new Date(), null, PricingConstants.PRESCRIPTION_REGION);
            PriceRegionEntity priceRegionEntity = this.findRegionOrgByDeptCode(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
            PriceRegionAirEntity priceRegionAirEntity = this.findRegionAirOrgByDeptCode(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
            PriceRegionEntity effectiveExpressRegionEntity = this.findExpressRegionOrgByDeptCode(deptCode, new Date(), null, PricingConstants.PRESCRIPTION_REGION);
            PriceRegionEntity priceExpressRegionEntity = this.findExpressRegionOrgByDeptCode(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
            PriceRegionArriveEntity priceArriAddRegionEntity = this.findRegionArriveOrgByDeptNo(deptCode, new Date(), null, PricingConstants.VALUEADD_REGION);
            /**
             * 新增精准大票价格始发区域和价格到达区域       yangkang  2014-07-07
             */
            PriceRegionBigGoodsEntity priceRegionBigGoodsEntity =this.findRegionBigOrgByDeptCode(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
            //精准大票中一个到达区域可以包含多个组织部门
            List<PriceRegionBigGoodsArriveEntity> priceRegionBigGoodsArriveEntitys =this.findRegionBigArriveOrgByDeptNo(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
            /**
             * 新增首续重价格始发区域和价格到达区域       lijinze  2016-07-08
             */
            PriceRegionEcGoodsEntity priceRegionEcGoodsEntity= this.findRegionEcGoodsOrgByDeptCode(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
            PriceRegionEcGoodsArriveEntity priceRegionEcGoodsArriveEntity=this.findRegionEcGoodsArriveOrgByDeptNo(deptCode, new Date(), null, PricingConstants.PRICING_REGION);

            //时效
            if(effectiveRegionEntity != null && StringUtils.isNotEmpty(effectiveRegionEntity.getId())) {
                List<PriceRegionEntity> effectiveList = map.get("effectiveRegion");
                if(CollectionUtils.isEmpty(effectiveList)) {
                    effectiveList = new ArrayList<PriceRegionEntity>();
                }
                if(!effectiveList.contains(effectiveRegionEntity)) {
                    effectiveList.add(effectiveRegionEntity);
                    map.put("effectiveRegion", effectiveList);
                }
            }
            //快递时效
            if(effectiveExpressRegionEntity != null && StringUtils.isNotEmpty(effectiveExpressRegionEntity.getId())) {
            	List<PriceRegionEntity> effectiveList = map.get("effectiveExpressRegion");
            	if(CollectionUtils.isEmpty(effectiveList)) {
            		effectiveList = new ArrayList<PriceRegionEntity>();
            	}
            	if(!effectiveList.contains(effectiveExpressRegionEntity)) {
            		effectiveList.add(effectiveExpressRegionEntity);
            		map.put("effectiveExpressRegion", effectiveList);
            	}
            }
            //汽运价格-出发
            if(priceRegionEntity != null && StringUtils.isNotEmpty(priceRegionEntity.getId())) {
                List<PriceRegionEntity> priceList = map.get("priceRegion");
                if(CollectionUtils.isEmpty(priceList)) {
                    priceList = new ArrayList<PriceRegionEntity>();
                }
                if(!priceList.contains(priceRegionEntity)) {
                    priceList.add(priceRegionEntity);
                    map.put("priceRegion", priceList);
                }
            }
            //汽运价格-到达
            if(priceArriAddRegionEntity != null && StringUtils.isNotEmpty(priceArriAddRegionEntity.getId())) {
                List<PriceRegionEntity> arrivePriceList = map.get("priceArriveRegion");
                if(CollectionUtils.isEmpty(arrivePriceList)) {
                    arrivePriceList = new ArrayList<PriceRegionEntity>();
                }
                if(!arrivePriceList.contains(priceArriAddRegionEntity)) {
                    PriceRegionEntity regionEntity = new PriceRegionEntity();
                    regionEntity.setId(priceArriAddRegionEntity.getId());
                    regionEntity.setRegionCode(priceArriAddRegionEntity.getRegionCode());
                    regionEntity.setRegionName(priceArriAddRegionEntity.getRegionName());
                    arrivePriceList.add(regionEntity);
                    map.put("priceArriveRegion", arrivePriceList);
                }
            }
            //快递价格
            if(priceExpressRegionEntity != null && StringUtils.isNotEmpty(priceExpressRegionEntity.getId())) {
                List<PriceRegionEntity> priceList = map.get("priceExpressRegion");
                if(CollectionUtils.isEmpty(priceList)) {
                    priceList = new ArrayList<PriceRegionEntity>();
                }
                if(!priceList.contains(priceExpressRegionEntity)) {
                    priceList.add(priceExpressRegionEntity);
                    map.put("priceExpressRegion", priceList);
                }
            }
            //空运价格
            if(priceRegionAirEntity != null && StringUtils.isNotEmpty(priceRegionAirEntity.getId())) {
                List<PriceRegionEntity> airList = map.get("priceRegionAir");
                if(CollectionUtils.isEmpty(airList)) {
                    airList = new ArrayList<PriceRegionEntity>();
                }
                if(!airList.contains(priceRegionAirEntity)) {
                    PriceRegionEntity regionEntity = new PriceRegionEntity();
                    regionEntity.setId(priceRegionAirEntity.getId());
                    regionEntity.setRegionCode(priceRegionAirEntity.getRegionCode());
                    regionEntity.setRegionName(priceRegionAirEntity.getRegionName());
                    airList.add(regionEntity);
                    map.put("priceRegionAir", airList);
                }
            }

            //精准大票价格-出发
            if(priceRegionBigGoodsEntity != null && StringUtils.isNotEmpty(priceRegionBigGoodsEntity.getId())) {
                List<PriceRegionEntity> priceList = map.get("priceRegionBig");
                if(CollectionUtils.isEmpty(priceList)) {
                    priceList = new ArrayList<PriceRegionEntity>();
                }
                if(!priceList.contains(priceRegionBigGoodsEntity)) {
                    priceList.add(priceRegionBigGoodsEntity);
                    map.put("priceRegionBig", priceList);
                }
            }
            //精准大票价格-到达
            if(CollectionUtils.isNotEmpty(priceRegionBigGoodsArriveEntitys)) {
                List<PriceRegionEntity> arrivePriceList = map.get("priceArriveRegionBig");
                if(CollectionUtils.isEmpty(arrivePriceList)) {
                    arrivePriceList = new ArrayList<PriceRegionEntity>();
                }
                for(PriceRegionBigGoodsArriveEntity priceRegionBigGoodsArriveEntity:priceRegionBigGoodsArriveEntitys){
                    if(!arrivePriceList.contains(priceRegionBigGoodsArriveEntity)) {
                        PriceRegionEntity regionEntity = new PriceRegionEntity();
                        regionEntity.setId(priceRegionBigGoodsArriveEntity.getId());
                        regionEntity.setRegionCode(priceRegionBigGoodsArriveEntity.getRegionCode());
                        regionEntity.setRegionName(priceRegionBigGoodsArriveEntity.getRegionName());
                        arrivePriceList.add(regionEntity);
                    }
                }
                map.put("priceArriveRegionBig", arrivePriceList);
            }

            //首续重价格-出发
            if(priceRegionEcGoodsEntity != null && StringUtils.isNotEmpty(priceRegionEcGoodsEntity.getId())) {
                List<PriceRegionEntity> priceList = map.get("priceRegionEc");
                if(CollectionUtils.isEmpty(priceList)) {
                    priceList = new ArrayList<PriceRegionEntity>();
                }
                if(!priceList.contains(priceRegionEcGoodsEntity)) {
                    priceList.add(priceRegionEcGoodsEntity);
                    map.put("priceRegionEc", priceList);
                }
            }
            //首续重价格-到达
            if(priceRegionEcGoodsArriveEntity != null && StringUtils.isNotEmpty(priceRegionEcGoodsArriveEntity.getId())) {
                List<PriceRegionEntity> arrivePriceList = map.get("priceArriveRegionEc");
                if(CollectionUtils.isEmpty(arrivePriceList)) {
                    arrivePriceList = new ArrayList<PriceRegionEntity>();
                }
                if(!arrivePriceList.contains(priceRegionEcGoodsArriveEntity)) {
                    PriceRegionEntity regionEntity = new PriceRegionEntity();
                    regionEntity.setId(priceRegionEcGoodsArriveEntity.getId());
                    regionEntity.setRegionCode(priceRegionEcGoodsArriveEntity.getRegionCode());
                    regionEntity.setRegionName(priceRegionEcGoodsArriveEntity.getRegionName());
                    arrivePriceList.add(regionEntity);
                }
                map.put("priceArriveRegionEc", arrivePriceList);
            }
        }
	}

	/**
	 * 
	 * @Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author FOSSDP-sz
	 * @date 2013-4-15 下午6:37:32
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	private PriceRegionAirEntity findRegionAirOrgByDeptCode(String orgCode, Date billDate, String productCode, String regionNature) {
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		//   1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		PriceRegionOrgAirEntity priceRegionOrgAirEntity = null;
		
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				priceRegionOrgAirEntity = priceRegionOrgAirCacheDeal.getPriceRegionOrgAirByCache(orgCode, billDate);
			} catch (Exception e) {
				log.info("无法获取空运区域与部门关系缓存", e);
			}
		}
		if(priceRegionOrgAirEntity != null) {
			return regionAirDao.searchRegionByID(priceRegionOrgAirEntity.getPriceRegionId(), regionNature);
		} else {
			priceRegionOrgAirEntity = new PriceRegionOrgAirEntity();
			priceRegionOrgAirEntity.setIncludeOrgCode(orgCode);
			priceRegionOrgAirEntity.setActive(FossConstants.ACTIVE);
			priceRegionOrgAirEntity.setBillDate(billDate);
			List<PriceRegionOrgAirEntity> resultList = regionAirDao.searchRegionOrgByCondition(priceRegionOrgAirEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegionOrgAirEntity object = resultList.get(0);
				return regionAirDao.searchRegionByID(object.getPriceRegionId(), regionNature);
			}
		}

		// 2 ,没找到，按组织所在行政区域匹配     
		String countyCode = null;
		String cityCode = null;
		String provCode = null;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgCode, billDate);
		
		if (orginfo != null) {
			countyCode = orginfo.getCountyCode();
			cityCode = orginfo.getCityCode();
			provCode = orginfo.getProvCode();
			if(StringUtil.isEmpty(provCode))
			{
			    return null;
			}

		} else {
		    OuterBranchParamsDto dto = new OuterBranchParamsDto();
			dto.setDate(billDate);
			dto.setAgentDeptCode(orgCode);
			List<OuterBranchEntity> entity = vehicleAgencyDeptService
					.queryOuterBranchsSimpleInfo(dto);
			if (CollectionUtils.isNotEmpty(entity)) {
				OuterBranchEntity obkect = entity.get(0);
				countyCode = obkect.getCountyCode();
				cityCode = obkect.getCityCode();
				provCode = obkect.getProvCode();
				if(StringUtil.isEmpty(provCode))
				{
				    return null;
				}
			}else
			{
			    return null;
			}
		}

		//根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";
		
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
		
		log.info("key str>>"+key);
		List<PriceRegionAirEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存 服务器端才读取
			try {
				regionlist = priceRegionAirCacheDeal.getPriceRegionAirByCache(key, billDate);
			} catch (Exception e) {
				log.info("无法获取空运区域缓存", e);
			}
		}
		if(CollectionUtils.isEmpty(regionlist)) {
			PriceRegionAirEntity regionEntity = new PriceRegionAirEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionlist = regionAirDao
					.searchRegionByDistrictNew(regionEntity);
		} 
		
		//过滤最符合条件的数据
		PriceRegionAirEntity entity = filterBestMapAirEntity(regionlist,provCode,cityCode,countyCode);
		if (entity != null) {
			return entity;
		}
		return null;
	}
	/**
	 * 
	 * @Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author FOSSDP-yangkang
	 * @date 2014-7-7 下午6:37:32
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceRegionBigGoodsEntity findRegionBigOrgByDeptCode(String orgCode, Date billDate, String productCode, String regionNature){
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		//   1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		PriceRegioOrgnBigGoodsEntity priceRegionOrgBigEntity = null;
		
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				priceRegionOrgBigEntity = priceRegionBigOrgCacheDeal.getPriceRegionOrgByCache(orgCode, billDate);
			} catch (Exception e) {
				log.info("无法获取精准大票区域与部门关系缓存", e);
			}
		}
		if(priceRegionOrgBigEntity != null) {
			return regionBigGoodsDao.searchRegionByID(priceRegionOrgBigEntity.getPriceRegionId(), regionNature);
		} else {
			priceRegionOrgBigEntity = new PriceRegioOrgnBigGoodsEntity();
			priceRegionOrgBigEntity.setIncludeOrgCode(orgCode);
			priceRegionOrgBigEntity.setActive(FossConstants.ACTIVE);
			priceRegionOrgBigEntity.setBillDate(billDate);
			List<PriceRegioOrgnBigGoodsEntity> resultList = regionBigGoodsDao.searchRegionOrgByCondition(priceRegionOrgBigEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegioOrgnBigGoodsEntity object = resultList.get(0);
				return regionBigGoodsDao.searchRegionByID(object.getPriceRegionId(), regionNature);
			}
		}

		// 2 ,没找到，按组织所在行政区域匹配     
		String countyCode = null;
		String cityCode = null;
		String provCode = null;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgCode, billDate);
		
		if (orginfo != null) {
			countyCode = orginfo.getCountyCode();
			cityCode = orginfo.getCityCode();
			provCode = orginfo.getProvCode();
			if(StringUtil.isEmpty(provCode))
			{
			    return null;
			}

		} 

		//根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";
		
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
		
		log.info("key str>>"+key);
		List<PriceRegionBigGoodsEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存 服务器端才读取
			try {
				regionlist = priceRegionBigCacheDeal.getPriceRegionByCache(key, billDate);
			} catch (Exception e) {
				log.info("无法获取精准大票始发区域缓存", e);
			}
		}
		if(CollectionUtils.isEmpty(regionlist)) {
			PriceRegionBigGoodsEntity regionEntity = new PriceRegionBigGoodsEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionEntity.setRegionNature(PricingConstants.PRICING_REGION);
			regionlist = regionBigGoodsDao.searchBGRegionByDistrictNew(regionEntity);
		} 
		
		//过滤最符合条件的数据
		PriceRegionBigGoodsEntity entity = filterBestMapBigEntity(regionlist,provCode,cityCode,countyCode);
		if (entity != null) {
			return entity;
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 部门Code寻找价格到达区域ID 
	 * 查询规则： 
	 * 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author FOSSDP-yangkang
	 * @date 2014-7-7 下午6:37:32
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	public List<PriceRegionBigGoodsArriveEntity> findRegionBigArriveOrgByDeptNo(String orgCode, Date billDate, String productCode, String regionNature){
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		//   1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		List<PriceRegionBigGoodsOrgArriveEntity> priceRegionOrgArriveEntitys = null;
		List<PriceRegionBigGoodsArriveEntity>  priceRegionBigGoodsArriveEntityList =new ArrayList<PriceRegionBigGoodsArriveEntity>();
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				priceRegionOrgArriveEntitys = priceRegionBigOrgArriveCacheDeal.getPriceRegionOrgAirByCache(orgCode, billDate);
			} catch (Exception e) {
				log.info("无法获取精准大票区域与部门关系缓存", e);
			}
		}
		if(CollectionUtils.isNotEmpty(priceRegionOrgArriveEntitys)) {
			for(PriceRegionBigGoodsOrgArriveEntity entity:priceRegionOrgArriveEntitys){
				String eid =entity.getPriceRegionId();
				priceRegionBigGoodsArriveEntityList.add(regionBigGoodsArriveDao.searchRegionByID(eid, regionNature));
			}
			return priceRegionBigGoodsArriveEntityList;
		} else {
			PriceRegionBigGoodsOrgArriveEntity priceRegionOrgArriveEntity = new PriceRegionBigGoodsOrgArriveEntity();
			priceRegionOrgArriveEntity.setIncludeOrgCode(orgCode);
			priceRegionOrgArriveEntity.setActive(FossConstants.ACTIVE);
			priceRegionOrgArriveEntity.setBillDate(billDate);
			List<PriceRegionBigGoodsOrgArriveEntity> resultList = regionBigGoodsArriveDao.searchRegionOrgByCondition(priceRegionOrgArriveEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for(PriceRegionBigGoodsOrgArriveEntity priceRegionBigGoodsOrgArriveEntity:resultList){
					PriceRegionBigGoodsArriveEntity entity = new PriceRegionBigGoodsArriveEntity();
					entity = regionBigGoodsArriveDao.searchRegionByID(priceRegionBigGoodsOrgArriveEntity.getPriceRegionId(), regionNature);
					priceRegionBigGoodsArriveEntityList.add(entity);
				}
				return priceRegionBigGoodsArriveEntityList;
			}
		}

		// 2 ,没找到，按组织所在行政区域匹配     
		String countyCode = null;
		String cityCode = null;
		String provCode = null;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgCode, billDate);
		
		if (orginfo != null) {
			countyCode = orginfo.getCountyCode();
			cityCode = orginfo.getCityCode();
			provCode = orginfo.getProvCode();
			if(StringUtil.isEmpty(provCode))
			{
			    return null;
			}

		} 

		//根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";
		
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
		
		log.info("key str>>"+key);
		List<PriceRegionBigGoodsArriveEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存 服务器端才读取
			try {
				regionlist = priceRegionBigArriveCacheDeal.getPriceBigRegionByCache(key, billDate);
			} catch (Exception e) {
				log.info("无法获取精准大票区域缓存", e);
			}
		}
		if(CollectionUtils.isEmpty(regionlist)&&(provCode!=null||cityCode!=null||countyCode!=null)) {
			PriceRegionBigGoodsArriveEntity regionEntity = new PriceRegionBigGoodsArriveEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionEntity.setRegionNature(PricingConstants.PRICING_REGION);
			regionlist = regionBigGoodsArriveDao.searchRegionByDistrictNew(regionEntity);
		} 
		
		//过滤最符合条件的数据
		List<PriceRegionBigGoodsArriveEntity> entity = filterRegionBigArriveBestMapEntity(regionlist,provCode,cityCode,countyCode);
		if (CollectionUtils.isNotEmpty(entity)) {
			return entity;
		}
		return null;
	}

	
	private PriceRegionAirEntity filterBestMapAirEntity(
			List<PriceRegionAirEntity> regionlist,String provCode,String cityCode,String countryCode) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionAirEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		List<PriceRegionAirEntity> countryRegionlist=new ArrayList<PriceRegionAirEntity>();
		List<PriceRegionAirEntity> cityRegionlist=new ArrayList<PriceRegionAirEntity>();
		List<PriceRegionAirEntity> provRegionlist=new ArrayList<PriceRegionAirEntity>();
		
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 0; j < regionlist.size(); j++) { 
			PriceRegionAirEntity temp = regionlist.get(j);
			if(StringUtils.equalsIgnoreCase(temp.getCountyCode(), countryCode))
			{
			    countryRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode))
			{
			    cityRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getProCode(), provCode))
			{
			    provRegionlist.add(temp);
			}
		}
		if (CollectionUtils.isNotEmpty(countryRegionlist)){
		    return filterBestMapAirEntity(countryRegionlist);
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
		    return filterBestMapAirEntity(cityRegionlist);
		}
		return null;
	}
	
	/**
	 * @author yangkang 
	 *  过滤逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
	 * @param regionlist
	 * @param provCode
	 * @param cityCode
	 * @param countryCode
	 * @return
	 */
	private PriceRegionBigGoodsEntity filterBestMapBigEntity(
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
			if(StringUtils.equalsIgnoreCase(temp.getCountyCode(), countryCode))
			{
			    countryRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode))
			{
			    cityRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getProCode(), provCode))
			{
			    provRegionlist.add(temp);
			}
		}
		if (CollectionUtils.isNotEmpty(countryRegionlist)){
		    return filterBestMapBigEntity(countryRegionlist);
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
		    return filterBestMapBigEntity(cityRegionlist);
		}
		return null;
	}
	
	
	/**
	 * @author lijinze 
	 *  过滤逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
	 * @param regionlist
	 * @param provCode
	 * @param cityCode
	 * @param countryCode
	 * @return
	 */
	private PriceRegionEcGoodsEntity filterBestMapEcEntity(
			List<PriceRegionEcGoodsEntity> regionlist, String provCode,
			String cityCode, String countyCode) {
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
			if (StringUtil.isNotEmpty(countyCode) && StringUtils.equalsIgnoreCase(temp.getCountyCode(), countyCode)) {
				countryRegionlist.add(temp);
			}
			if ( StringUtil.isNotEmpty(cityCode) && StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode) && StringUtil.isEmpty(temp.getCountyCode())) {
				cityRegionlist.add(temp);
			}
			if (  StringUtil.isNotEmpty(provCode) && StringUtils.equalsIgnoreCase(temp.getProCode(), provCode) && StringUtil.isEmpty(temp.getCountyCode()) && StringUtil.isEmpty(temp.getCityCode())) {
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
	 * 过滤最匹配的区域
	 * </p>
	 * 
	 * @author yangkang
	 * 
	 * @date 2014-07-7 下午2:09:09
	 * 
	 * @param regionlist
	 * 
	 * @return
	 * 
	 * @see
	 */
	private PriceRegionBigGoodsEntity filterBestMapBigEntity(
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
	 *精准电商
	 * @Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author 311417 wangfeng
	 * @date 2016.07.14
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceRegionEcGoodsEntity findRegionEcGoodsOrgByDeptCode(String orgCode, Date billDate, String productCode, String regionNature){
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
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
			return regionEcGoodsDao.searchRegionByID(priceRegioOrgnEcGoodsEntity.getPriceRegionId(), regionNature);
		}else{
			priceRegioOrgnEcGoodsEntity =  new PriceRegioOrgnEcGoodsEntity();
			priceRegioOrgnEcGoodsEntity.setIncludeOrgCode(orgCode);
			priceRegioOrgnEcGoodsEntity.setRegionNature(regionNature);
			priceRegioOrgnEcGoodsEntity.setActive(FossConstants.ACTIVE);
			priceRegioOrgnEcGoodsEntity.setBillDate(billDate);
			//封装数据，调用Dao层进行查询
			List<PriceRegioOrgnEcGoodsEntity> resultList = regionEcGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEcGoodsEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegioOrgnEcGoodsEntity object = resultList.get(0);
				return regionEcGoodsDao.searchRegionByID(object.getPriceRegionId(), regionNature);
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
		if(CollectionUtils.isEmpty(regionEcGoodsList)) {
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
		PriceRegionEcGoodsEntity entity = filterBestMapEcEntity(regionEcGoodsList, provCode, cityCode, countyCode);
		if (entity != null) {
			return entity;
		}
		return null;
	}

	/**
	 *
	 * @Description: 部门Code寻找价格到达区域ID
	 * 查询规则：
	 * 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author 311417 wangfeng
	 * @date 2016.07.14
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceRegionEcGoodsArriveEntity findRegionEcGoodsArriveOrgByDeptNo(String destinationOrgCode, Date billDate, String productCode, String regionNature){
		if (StringUtil.isEmpty(destinationOrgCode)) {
			return null;
		}
		/**
		 * 1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		 */
		PriceRegionEcGoodsOrgArriveEntity priceRegionEcGoodsOrgArriveEntity =null;
		//先从缓存查找
		if(SqlUtil.loadCache){
			try{
				priceRegionEcGoodsOrgArriveEntity = priceRegionEcGoodsOrgArriveCacheDeal.getPriceRegionOrgEcByCache(destinationOrgCode, billDate);
			}
			catch (Exception e){
				log.info("无法获取区域与部门关系缓存",e);
			}
		}
		if(priceRegionEcGoodsOrgArriveEntity!=null){
			return regionEcGoodsArriveDao.searchRegionByID(priceRegionEcGoodsOrgArriveEntity.getPriceRegionId(), regionNature);
		}else{
			priceRegionEcGoodsOrgArriveEntity = new PriceRegionEcGoodsOrgArriveEntity();
			priceRegionEcGoodsOrgArriveEntity.setIncludeOrgCode(destinationOrgCode);
			priceRegionEcGoodsOrgArriveEntity.setRegionNature(regionNature);
			priceRegionEcGoodsOrgArriveEntity.setActive(FossConstants.ACTIVE);
			priceRegionEcGoodsOrgArriveEntity.setBillDate(billDate);
			//调用Dao层查询
			List<PriceRegionEcGoodsOrgArriveEntity> resultList = regionEcGoodsArriveDao.searchRegionOrgByCondition(priceRegionEcGoodsOrgArriveEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegionEcGoodsOrgArriveEntity temp = resultList.get(0);
				regionEcGoodsArriveDao.searchRegionByID(temp.getPriceRegionId(), regionNature);
			}
		}
		/**
		 * 2 ,没找到，按组织所在行政区域匹配
		 */
		String countyCode;
		String cityCode;
		String provCode;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode, billDate);
		GetOrgClass getOrgClass = new GetOrgClass(destinationOrgCode, billDate, orginfo).invoke();
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

		List<PriceRegionEcGoodsArriveEntity> regionEcGoodsArriveList =null;
		if (SqlUtil.loadCache) {
			try{
				regionEcGoodsArriveList = priceRegionEcGoodsArriveCacheDeal.getPriceEcGoodsRegionByCache(key, billDate);
			}catch (Exception e) {
				log.info("无法获取区域与部门关系缓存", e);
			}
		}
		if(CollectionUtils.isEmpty(regionEcGoodsArriveList)) {
			PriceRegionEcGoodsArriveEntity priceRegionEcGoodsArriveEntity = new PriceRegionEcGoodsArriveEntity();
			priceRegionEcGoodsArriveEntity.setProCode(provCode);
			priceRegionEcGoodsArriveEntity.setCityCode(cityCode);
			priceRegionEcGoodsArriveEntity.setCountyCode(countyCode);
			priceRegionEcGoodsArriveEntity.setBillDate(billDate);
			priceRegionEcGoodsArriveEntity.setRegionNature(regionNature);
			priceRegionEcGoodsArriveEntity.setActive(FossConstants.ACTIVE);
			regionEcGoodsArriveList = regionEcGoodsArriveDao.searchRegionByDistrictNew(priceRegionEcGoodsArriveEntity);
		}
		//过滤出最符合条件的数据
		PriceRegionEcGoodsArriveEntity entity = ecFilterRegionArriveBestMapEntity(regionEcGoodsArriveList, provCode, cityCode, countyCode);
		if (entity != null) {
			return entity;
		}
		return null;
	}

	/**
	 * 快递价格区域、时效区域
	 * @Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author FOSSDP-sz
	 * @date 2013-4-15 下午6:37:32
	 * @param deptNo
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */	
	private PriceRegionEntity findExpressRegionOrgByDeptCode(String orgCode, Date billDate, String productCode, String regionNature) {
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		/**
		 * 1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		 */
		PriceRegioOrgnExpressEntity priceRegioExpressOrgnEntity = null;
		
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				if (StringUtils.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
				    priceRegioExpressOrgnEntity = effectiveRegionOrgExpressCacheDeal.getPriceRegionOrgExpressByCache(orgCode, billDate);
				} else if (StringUtils.equals(PricingConstants.PRICING_REGION, regionNature)) {
				    priceRegioExpressOrgnEntity = priceRegionOrgExpressCacheDeal.getPriceRegionOrgExpressByCache(orgCode, billDate);
				}
			} catch (Exception e) {
				log.info("无法获取区域与部门关系缓存", e);
			}
		}
		if (priceRegioExpressOrgnEntity != null) { 
		   
		           return convertObject(priceRegioExpressOrgnEntity.getPriceRegionId(),regionNature);		      
			 
		} else {
		        priceRegioExpressOrgnEntity = new PriceRegioOrgnExpressEntity();
		        priceRegioExpressOrgnEntity.setIncludeOrgCode(orgCode);
		        priceRegioExpressOrgnEntity.setRegionNature(regionNature);
		        priceRegioExpressOrgnEntity.setActive(FossConstants.ACTIVE);
		        priceRegioExpressOrgnEntity.setBillDate(billDate);
			List<PriceRegioOrgnExpressEntity> resultList = regionExpressDao.searchRegionOrgByCondition(priceRegioExpressOrgnEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
			    PriceRegioOrgnExpressEntity objectRes = resultList.get(0);
			    return convertObject(objectRes.getPriceRegionId(),regionNature);
			    
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
		List<PriceRegionExpressEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				if (StringUtils.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
					regionlist = effectiveRegionExpressCacheDeal.getPriceRegionExpressByCache(key, billDate);
				} else if (StringUtils.equals(PricingConstants.PRICING_REGION, regionNature)) {
					regionlist = priceRegionExpressCacheDeal.getPriceRegionExpressByCache(key, billDate);
				}
			} catch (Exception e) {
				log.info("无法获取区域缓存", e);
			}
		}
		if (CollectionUtils.isEmpty(regionlist)) {
		        PriceRegionExpressEntity regionEntity = new PriceRegionExpressEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setRegionNature(regionNature);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionlist = regionExpressDao.searchRegionByDistrictNew(regionEntity);
		}
		// 过滤最符合条件的数据
		 
		PriceRegionEntity entity = filterBestMapEntity(convertObject(regionlist), provCode, cityCode, countyCode);
		if (entity != null) {
			return entity;
		}
		return null;
	}
	//对象转换
	private PriceRegionEntity convertObject(String id,String regionNature)
	{
	    PriceRegionExpressEntity object= regionExpressDao.searchRegionByID(id, regionNature);
	    if(object==null)
		{
		    return null;
		}else
		 {
		   PriceRegionEntity objectNew =new PriceRegionEntity();
		   objectNew.setId(object.getId());
		   objectNew.setRegionName(object.getRegionName());
		   return objectNew;
		 }
	    
	}
	
	//对象转换
	private List<PriceRegionEntity> convertObject(List<PriceRegionExpressEntity> list)
		{
	    List<PriceRegionEntity> newList=new ArrayList<PriceRegionEntity>();
        	    for (PriceRegionExpressEntity entityOld : list)
        	    {
        		PriceRegionEntity entityNew=new PriceRegionEntity();
        		BeanUtils.copyProperties(entityOld, entityNew);
        		newList.add(entityNew);
        	    }
        	    return newList;
		    
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
	private PriceRegionAirEntity filterBestMapAirEntity(
			List<PriceRegionAirEntity> regionlist) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionAirEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 1; j < regionlist.size(); j++) {
			PriceRegionAirEntity temp = regionlist.get(j);
			if (temp.getPriority() < result.getPriority()) {
			    //找到后替换
				result = temp;
			}

		}
		return result;
	}
	/**
	 * 
	 * @Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author FOSSDP-sz
	 * @date 2013-4-15 下午6:37:32
	 * @param deptNo
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */	
	private PriceRegionEntity findRegionOrgByDeptCode(String orgCode, Date billDate, String productCode, String regionNature) {
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
			return regionDao.searchRegionByID(priceRegioOrgnEntity.getPriceRegionId(), regionNature);
		} else {
			priceRegioOrgnEntity = new PriceRegioOrgnEntity();
			priceRegioOrgnEntity.setIncludeOrgCode(orgCode);
			priceRegioOrgnEntity.setRegionNature(regionNature);
			priceRegioOrgnEntity.setActive(FossConstants.ACTIVE);
			priceRegioOrgnEntity.setBillDate(billDate);
			List<PriceRegioOrgnEntity> resultList = regionDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegioOrgnEntity object = resultList.get(0);
				return regionDao.searchRegionByID(object.getPriceRegionId(), regionNature);
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
			return entity;
		}
		return null;
	}
	
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
		return null;
	}
	
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

	
	
	public PriceRegionArriveEntity findRegionArriveOrgByDeptNo(String orgCode, Date billDate,
			String productCode, String regionNature) {
		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		//   1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		PriceRegionOrgArriveEntity priceRegionOrgArriveEntity = null;
		
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				priceRegionOrgArriveEntity = priceRegionOrgArriveCacheDeal.getPriceRegionOrgAirByCache(orgCode, billDate);
			} catch (Exception e) {
				log.info("无法获取空运区域与部门关系缓存", e);
			}
		}
		if(priceRegionOrgArriveEntity != null) {
			return regionArriveDao.searchRegionByID(priceRegionOrgArriveEntity.getPriceRegionId(), regionNature);
		} else {
			priceRegionOrgArriveEntity = new PriceRegionOrgArriveEntity();
			priceRegionOrgArriveEntity.setIncludeOrgCode(orgCode);
			priceRegionOrgArriveEntity.setActive(FossConstants.ACTIVE);
			priceRegionOrgArriveEntity.setBillDate(billDate);
			List<PriceRegionOrgArriveEntity> resultList = regionArriveDao.searchRegionOrgByCondition(priceRegionOrgArriveEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegionOrgArriveEntity object = resultList.get(0);
				return regionArriveDao.searchRegionByID(object.getPriceRegionId(), regionNature);
			}
		}

		// 2 ,没找到，按组织所在行政区域匹配     
		String countyCode = null;
		String cityCode = null;
		String provCode = null;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgCode, billDate);
		
		if (orginfo != null) {
			countyCode = orginfo.getCountyCode();
			cityCode = orginfo.getCityCode();
			provCode = orginfo.getProvCode();
			if(StringUtil.isEmpty(provCode))
			{
			    return null;
			}

		} else {
		    OuterBranchParamsDto dto = new OuterBranchParamsDto();
			dto.setDate(billDate);
			dto.setAgentDeptCode(orgCode);
			List<OuterBranchEntity> entity = vehicleAgencyDeptService
					.queryOuterBranchsSimpleInfo(dto);
			if (CollectionUtils.isNotEmpty(entity)) {
				OuterBranchEntity obkect = entity.get(0);
				countyCode = obkect.getCountyCode();
				cityCode = obkect.getCityCode();
				provCode = obkect.getProvCode();
				if(StringUtil.isEmpty(provCode))
				{
				    return null;
				}
			}else
			{
			    return null;
			}
		}

		//根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";
		
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
		
		log.info("key str>>"+key);
		List<PriceRegionArriveEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存 服务器端才读取
			try {
				regionlist = priceRegionArriveCacheDeal.getPriceRegionAirByCache(key, billDate);
			} catch (Exception e) {
				log.info("无法获取空运区域缓存", e);
			}
		}
		if(CollectionUtils.isEmpty(regionlist)) {
			PriceRegionArriveEntity regionEntity = new PriceRegionArriveEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionlist = regionArriveDao.searchRegionByDistrictNew(regionEntity);
		} 
		
		//过滤最符合条件的数据
		PriceRegionArriveEntity entity = filterRegionArriveBestMapEntity(regionlist,provCode,cityCode,countyCode);
		if (entity != null) {
			return entity;
		}
		return null;
	}

	private PriceRegionArriveEntity filterRegionArriveBestMapEntity(
			List<PriceRegionArriveEntity> regionlist,String provCode,String cityCode,String countryCode) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionArriveEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		List<PriceRegionArriveEntity> countryRegionlist=new ArrayList<PriceRegionArriveEntity>();
		List<PriceRegionArriveEntity> cityRegionlist=new ArrayList<PriceRegionArriveEntity>();
		List<PriceRegionArriveEntity> provRegionlist=new ArrayList<PriceRegionArriveEntity>();
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 0; j < regionlist.size(); j++) {
			PriceRegionArriveEntity temp = regionlist.get(j);
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
		    return filterRegionArriveMapEntity(countryRegionlist);
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
		    return filterRegionArriveMapEntity(cityRegionlist);
		}
		return null;
	}
	
	private PriceRegionEcGoodsArriveEntity ecFilterRegionArriveBestMapEntity(
			List<PriceRegionEcGoodsArriveEntity> regionEcGoodsArriveList, String provCode,
			String cityCode, String countyCode) {
		
		if (CollectionUtils.isEmpty(regionEcGoodsArriveList)) {
			return null;
		}

		List<PriceRegionEcGoodsArriveEntity> countryRegionlist=new ArrayList<PriceRegionEcGoodsArriveEntity>();
		List<PriceRegionEcGoodsArriveEntity> cityRegionlist=new ArrayList<PriceRegionEcGoodsArriveEntity>();
		List<PriceRegionEcGoodsArriveEntity> provRegionlist=new ArrayList<PriceRegionEcGoodsArriveEntity>();

		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		//foss 343617 赵一清 20161004 增加省份查找ID
		for (int j = 0; j < regionEcGoodsArriveList.size(); j++) {
			PriceRegionEcGoodsArriveEntity temp = regionEcGoodsArriveList.get(j);
			if (StringUtil.isNotEmpty(countyCode) && StringUtils.equalsIgnoreCase(temp.getCountyCode(), countyCode)) {
				countryRegionlist.add(temp);
			}
			if (StringUtil.isNotEmpty(cityCode) && StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode) && StringUtil.isEmpty(temp.getCountyCode())) {
				cityRegionlist.add(temp);
			}
			if ( StringUtil.isNotEmpty(provCode) && StringUtils.equalsIgnoreCase(temp.getProCode(), provCode) && StringUtil.isEmpty(temp.getCountyCode()) && StringUtil.isEmpty(temp.getCityCode())) {
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
	 * @author yangkang
	 * @param regionlist
	 * @param provCode
	 * @param cityCode
	 * @param countryCode
	 * @return
	 */
	private List<PriceRegionBigGoodsArriveEntity> filterRegionBigArriveBestMapEntity(
			List<PriceRegionBigGoodsArriveEntity> regionlist,String provCode,String cityCode,String countryCode) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		List<PriceRegionBigGoodsArriveEntity> countryRegionlist=new ArrayList<PriceRegionBigGoodsArriveEntity>();
		List<PriceRegionBigGoodsArriveEntity> cityRegionlist=new ArrayList<PriceRegionBigGoodsArriveEntity>();
		List<PriceRegionBigGoodsArriveEntity> provRegionlist=new ArrayList<PriceRegionBigGoodsArriveEntity>();
		
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 0; j < regionlist.size(); j++) { 
			PriceRegionBigGoodsArriveEntity temp = regionlist.get(j);
			if(StringUtils.equalsIgnoreCase(temp.getCountyCode(), countryCode))
			{
			    countryRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode))
			{
			    cityRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getProCode(), provCode))
			{
			    provRegionlist.add(temp);
			}
		}
		if (CollectionUtils.isNotEmpty(countryRegionlist)){
		    return countryRegionlist ;
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
		    return cityRegionlist;
		}
		
		if (CollectionUtils.isNotEmpty(provRegionlist)){
		    return provRegionlist;
		}
		 
		return null;
	}

	private PriceRegionArriveEntity filterRegionArriveMapEntity(
			List<PriceRegionArriveEntity> regionlist) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionArriveEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 1; j < regionlist.size(); j++) {
			PriceRegionArriveEntity temp = regionlist.get(j);
			if (temp.getPriority() < result.getPriority()) {
			    //找到后替换
				result = temp;
			}
		}
		return result;
	}
	
    /**
     * 
     * @Description: 
     * @author FOSSDP-sz
     * @date 2013-4-17 下午4:59:20
     * @param districtCode
     * @return
     * @version V1.0
     */
    private List<String> composeDeptList(String districtCode) {
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
    

	public void setPriceRegionOrgAirCacheDeal(PriceRegionOrgAirCacheDeal priceRegionOrgAirCacheDeal) {
		this.priceRegionOrgAirCacheDeal = priceRegionOrgAirCacheDeal;
	}
	public void setPriceRegionOrgArriveCacheDeal(
			PriceRegionOrgArriveCacheDeal priceRegionOrgArriveCacheDeal) {
		this.priceRegionOrgArriveCacheDeal = priceRegionOrgArriveCacheDeal;
	}

	public void setPriceRegionArriveCacheDeal(
			PriceRegionArriveCacheDeal priceRegionArriveCacheDeal) {
		this.priceRegionArriveCacheDeal = priceRegionArriveCacheDeal;
	}

	public void setPriceRegionAirCacheDeal(PriceRegionAirCacheDeal priceRegionAirCacheDeal) {
		this.priceRegionAirCacheDeal = priceRegionAirCacheDeal;
	}
	public void setEffectiveRegionOrgCacheDeal(EffectiveRegionOrgCacheDeal effectiveRegionOrgCacheDeal) {
		this.effectiveRegionOrgCacheDeal = effectiveRegionOrgCacheDeal;
	}
	public void setEffectiveRegionCacheDeal(EffectiveRegionCacheDeal effectiveRegionCacheDeal) {
		this.effectiveRegionCacheDeal = effectiveRegionCacheDeal;
	}
	public void setPriceRegionOrgCacheDeal(PriceRegionOrgCacheDeal priceRegionOrgCacheDeal) {
		this.priceRegionOrgCacheDeal = priceRegionOrgCacheDeal;
	}
	public void setPriceRegionCacheDeal(PriceRegionCacheDeal priceRegionCacheDeal) {
		this.priceRegionCacheDeal = priceRegionCacheDeal;
	}
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	public void setDistrictRegionDao(IDistrictRegionDao districtRegionDao) {
		this.districtRegionDao = districtRegionDao;
	}
	public void setPricingOrgDao(IPricingOrgDao pricingOrgDao) {
		this.pricingOrgDao = pricingOrgDao;
	}
	public void setDistrictRegionCacheDeal(DistrictRegionCacheDeal districtRegionCacheDeal) {
		this.districtRegionCacheDeal = districtRegionCacheDeal;
	}
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}
	public void setRegionAirDao(IRegionAirDao regionAirDao) {
		this.regionAirDao = regionAirDao;
	}
    public void setRegionArriveDao(IRegionArriveDao regionArriveDao) {
		this.regionArriveDao = regionArriveDao;
	}
    public IRegionExpressDao getRegionExpressDao() {
	    return regionExpressDao;
	}
	
	public void setRegionExpressDao(IRegionExpressDao regionExpressDao) {
	    this.regionExpressDao = regionExpressDao;
	}
	
	public EffectiveRegionOrgExpressCacheDeal getEffectiveRegionOrgExpressCacheDeal() {
	    return effectiveRegionOrgExpressCacheDeal;
	}
	
	public void setEffectiveRegionOrgExpressCacheDeal(
		EffectiveRegionOrgExpressCacheDeal effectiveRegionOrgExpressCacheDeal) {
	    this.effectiveRegionOrgExpressCacheDeal = effectiveRegionOrgExpressCacheDeal;
	}
	
	public EffectiveRegionExpressCacheDeal getEffectiveRegionExpressCacheDeal() {
	    return effectiveRegionExpressCacheDeal;
	}
	
	public void setEffectiveRegionExpressCacheDeal(
		EffectiveRegionExpressCacheDeal effectiveRegionExpressCacheDeal) {
	    this.effectiveRegionExpressCacheDeal = effectiveRegionExpressCacheDeal;
	}
	
	public PriceRegionOrgExpressCacheDeal getPriceRegionOrgExpressCacheDeal() {
	    return priceRegionOrgExpressCacheDeal;
	}
	
	public void setPriceRegionOrgExpressCacheDeal(
		PriceRegionOrgExpressCacheDeal priceRegionOrgExpressCacheDeal) {
	    this.priceRegionOrgExpressCacheDeal = priceRegionOrgExpressCacheDeal;
	}
	
	public PriceRegionExpressCacheDeal getPriceRegionExpressCacheDeal() {
	    return priceRegionExpressCacheDeal;
	}
	
	public void setPriceRegionExpressCacheDeal(
		PriceRegionExpressCacheDeal priceRegionExpressCacheDeal) {
	    this.priceRegionExpressCacheDeal = priceRegionExpressCacheDeal;
	}
	
	public IDistrictRegionDao getDistrictRegionDao() {
	    return districtRegionDao;
	}
	
	public IPricingOrgDao getPricingOrgDao() {
	    return pricingOrgDao;
	}
	
	public DistrictRegionCacheDeal getDistrictRegionCacheDeal() {
	    return districtRegionCacheDeal;
	}
	
	public IRegionDao getRegionDao() {
	    return regionDao;
	}
	
	public IRegionAirDao getRegionAirDao() {
	    return regionAirDao;
	}
	
	public PriceRegionOrgAirCacheDeal getPriceRegionOrgAirCacheDeal() {
	    return priceRegionOrgAirCacheDeal;
	}
	
	public PriceRegionAirCacheDeal getPriceRegionAirCacheDeal() {
	    return priceRegionAirCacheDeal;
	}
	
	public EffectiveRegionOrgCacheDeal getEffectiveRegionOrgCacheDeal() {
	    return effectiveRegionOrgCacheDeal;
	}
	
	public EffectiveRegionCacheDeal getEffectiveRegionCacheDeal() {
	    return effectiveRegionCacheDeal;
	}
	
	public PriceRegionOrgCacheDeal getPriceRegionOrgCacheDeal() {
	    return priceRegionOrgCacheDeal;
	}
	
	public PriceRegionCacheDeal getPriceRegionCacheDeal() {
	    return priceRegionCacheDeal;
	}
	
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
	    return orgAdministrativeInfoService;
	}
	
	public IVehicleAgencyDeptService getVehicleAgencyDeptService() {
	    return vehicleAgencyDeptService;
	}
	public void setPriceRegionBigOrgArriveCacheDeal(
			PriceRegionBigOrgArriveCacheDeal priceRegionBigOrgArriveCacheDeal) {
		this.priceRegionBigOrgArriveCacheDeal = priceRegionBigOrgArriveCacheDeal;
	}
	public void setPriceRegionBigArriveCacheDeal(
			PriceRegionBigArriveCacheDeal priceRegionBigArriveCacheDeal) {
		this.priceRegionBigArriveCacheDeal = priceRegionBigArriveCacheDeal;
	}
	public void setPriceRegionBigOrgCacheDeal(
			PriceRegionBigOrgCacheDeal priceRegionBigOrgCacheDeal) {
		this.priceRegionBigOrgCacheDeal = priceRegionBigOrgCacheDeal;
	}
	public void setPriceRegionBigCacheDeal(
			PriceRegionBigCacheDeal priceRegionBigCacheDeal) {
		this.priceRegionBigCacheDeal = priceRegionBigCacheDeal;
	}
	public IRegionBigGoodsDao getRegionBigGoodsDao() {
		return regionBigGoodsDao;
	}
	public void setRegionBigGoodsDao(IRegionBigGoodsDao regionBigGoodsDao) {
		this.regionBigGoodsDao = regionBigGoodsDao;
	}
	public IRegionEcGoodsDao getRegionEcGoodsDao() {
		return regionEcGoodsDao;
	}
	public void setRegionEcGoodsDao(IRegionEcGoodsDao regionEcGoodsDao) {
		this.regionEcGoodsDao = regionEcGoodsDao;
	}
	public IRegionBigGoodsArriveDao getRegionBigGoodsArriveDao() {
		return regionBigGoodsArriveDao;
	}
	public void setRegionBigGoodsArriveDao(
			IRegionBigGoodsArriveDao regionBigGoodsArriveDao) {
		this.regionBigGoodsArriveDao = regionBigGoodsArriveDao;
	}
	public IRegionArriveDao getRegionArriveDao() {
		return regionArriveDao;
	}
	public void setPriceRegionEcOrgCacheDeal(
			PriceRegionEcGoodsOrgCacheDeal priceRegionEcOrgCacheDeal) {
		this.priceRegionEcOrgCacheDeal = priceRegionEcOrgCacheDeal;
	}
	public void setPriceRegionEcCacheDeal(
			PriceRegionEcGoodsCacheDeal priceRegionEcCacheDeal) {
		this.priceRegionEcCacheDeal = priceRegionEcCacheDeal;
	}
	public void setPriceRegionEcGoodsOrgArriveCacheDeal(
			PriceRegionEcGoodsOrgArriveCacheDeal priceRegionEcGoodsOrgArriveCacheDeal) {
		this.priceRegionEcGoodsOrgArriveCacheDeal = priceRegionEcGoodsOrgArriveCacheDeal;
	}
	public void setRegionEcGoodsArriveDao(
				IRegionEcGoodsArriveDao regionEcGoodsArriveDao) {
			this.regionEcGoodsArriveDao = regionEcGoodsArriveDao;
	}
	public void setPriceRegionEcGoodsArriveCacheDeal(
				PriceRegionEcGoodsArriveCacheDeal priceRegionEcGoodsArriveCacheDeal) {
			this.priceRegionEcGoodsArriveCacheDeal = priceRegionEcGoodsArriveCacheDeal;
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
                if(StringUtil.isEmpty(provCode))
                {
					myResult = true;
					return this;
                }

            } else {
                OuterBranchParamsDto dto = new OuterBranchParamsDto();
                dto.setDate(billDate);
                dto.setAgentDeptCode(orgCode);
                List<OuterBranchEntity> entity = vehicleAgencyDeptService
                        .queryOuterBranchsSimpleInfo(dto);
                if (CollectionUtils.isNotEmpty(entity)) {
                    OuterBranchEntity obkect = entity.get(0);
                    countyCode = obkect.getCountyCode();
                    cityCode = obkect.getCityCode();
                    provCode = obkect.getProvCode();
                    if(StringUtil.isEmpty(provCode))
                    {
						myResult = true;
						return this;
                    }
                }else
                {
					myResult = true;
					return this;
                }
            }
			myResult = false;
			return this;
		}
	}
}