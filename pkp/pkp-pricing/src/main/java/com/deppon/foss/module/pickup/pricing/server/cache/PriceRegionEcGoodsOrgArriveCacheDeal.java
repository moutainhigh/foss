package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsOrgArriveEntity;

import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 
 * @Description: 到达区域与组织关系Cache处理
 * PriceRegionOrgCacheDeal.java Create on 2013-2-19 下午2:03:53
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionEcGoodsOrgArriveCacheDeal {
	/**
	 * priceRegionOrgArriveCache缓存
	 */
	private PriceRegionEcGoodsOrgArriveCache priceRegionEcGoodsOrgArriveCache;
	


	public PriceRegionEcGoodsOrgArriveCache getPriceRegionEcGoodsOrgArriveCache() {
		return priceRegionEcGoodsOrgArriveCache;
	}



	public void setPriceRegionEcGoodsOrgArriveCache(
			PriceRegionEcGoodsOrgArriveCache priceRegionEcGoodsOrgArriveCache) {
		this.priceRegionEcGoodsOrgArriveCache = priceRegionEcGoodsOrgArriveCache;
	}


	/**
	 * 
	 * @Description: 根据网点编号以及发生日期从缓存中查询符合条件 价格区域与组织关系实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 */
	public PriceRegionEcGoodsOrgArriveEntity getPriceRegionOrgEcByCache(String code, Date date) {
		List<PriceRegionEcGoodsOrgArriveEntity> priceRegionOrgAirEntities = priceRegionEcGoodsOrgArriveCache.get(code);
		PriceRegionEcGoodsOrgArriveEntity entity = null;
		if(CollectionUtils.isNotEmpty(priceRegionOrgAirEntities)) {
			for (PriceRegionEcGoodsOrgArriveEntity priceRegionOrgAirEntity : priceRegionOrgAirEntities) {
				Date beginTime = priceRegionOrgAirEntity.getBeginTime();
				Date endTime = priceRegionOrgAirEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = priceRegionOrgAirEntity;
					break;
				}
			}
		} 
		return entity;
	}
}