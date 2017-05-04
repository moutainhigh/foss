/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;

/**
 * 
 * @Description: 空运价格区域Cache处理
 * PriceRegionCacheDeal.java Create on 2013-2-18 下午5:39:59
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionArriveCacheDeal {
	/**
	 * priceRegionAirCache 缓存
	 */
	private PriceRegionArriveCache priceRegionArriveCache;

	public PriceRegionArriveCache getPriceRegionArriveCache() {
		return priceRegionArriveCache;
	}

	public void setPriceRegionArriveCache(
			PriceRegionArriveCache priceRegionArriveCache) {
		this.priceRegionArriveCache = priceRegionArriveCache;
	}


	/**
	 * 
	 * @Description: 根据空运价格区域编号以及发生日期从缓存中
	 * 查询符合条件价格区域实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public List<PriceRegionArriveEntity> getPriceRegionAirByCache(String code, Date date) {
		List<PriceRegionArriveEntity> priceRegionAirEntities = priceRegionArriveCache.get(code);
		List<PriceRegionArriveEntity> regionEntities = new ArrayList<PriceRegionArriveEntity>();
		if(CollectionUtils.isNotEmpty(priceRegionAirEntities)) {
			for (PriceRegionArriveEntity priceRegionArriveEntity : priceRegionAirEntities) {
				Date beginTime = priceRegionArriveEntity.getBeginTime();
				Date endTime = priceRegionArriveEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					regionEntities.add(priceRegionArriveEntity);
				}
			}
		} 
		return regionEntities;
	}

}