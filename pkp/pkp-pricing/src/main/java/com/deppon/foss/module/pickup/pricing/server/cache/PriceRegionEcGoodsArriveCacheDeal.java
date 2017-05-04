/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @Description: 精准电商价格区域Cache处理
 * PriceRegionCacheDeal.java Create on 2014-07-09 下午5:39:59
 * @author FOSSDP-yangkang
 * Copyright (c) 2014 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionEcGoodsArriveCacheDeal {
	/**
	 * priceRegionAirCache 缓存
	 */
	private PriceRegionEcGoodsArriveCache priceRegionEcGoodsArriveCache;



	public PriceRegionEcGoodsArriveCache getPriceRegionEcGoodsArriveCache() {
		return priceRegionEcGoodsArriveCache;
	}



	public void setPriceRegionEcGoodsArriveCache(
			PriceRegionEcGoodsArriveCache priceRegionEcGoodsArriveCache) {
		this.priceRegionEcGoodsArriveCache = priceRegionEcGoodsArriveCache;
	}



	/**
	 * 
	 * @Description: 根据精准电商价格区域编号以及发生日期从缓存中
	 * 查询符合条件价格区域实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-yangkang
	 * @date 2014-07-09 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public List<PriceRegionEcGoodsArriveEntity> getPriceEcGoodsRegionByCache(String code, Date date) {
		List<PriceRegionEcGoodsArriveEntity> priceRegionEcGoodsEntities = priceRegionEcGoodsArriveCache.get(code);
		List<PriceRegionEcGoodsArriveEntity> regionEntities = new ArrayList<PriceRegionEcGoodsArriveEntity>();
		if(CollectionUtils.isNotEmpty(priceRegionEcGoodsEntities)) {
			for (PriceRegionEcGoodsArriveEntity priceRegionArriveEntity : priceRegionEcGoodsEntities) {
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