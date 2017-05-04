/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;

/**
 * 
 * @Description: 空运价格区域Cache处理
 * PriceRegionCacheDeal.java Create on 2013-2-18 下午5:39:59
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionValueAddCacheDeal {
	/**
	 * priceRegionAirCache 缓存
	 */
	private PriceRegionValueAddCache priceRegionValueAddCache;

	
	public PriceRegionValueAddCache getPriceRegionValueAddCache() {
		return priceRegionValueAddCache;
	}

	public void setPriceRegionValueAddCache(
			PriceRegionValueAddCache priceRegionValueAddCache) {
		this.priceRegionValueAddCache = priceRegionValueAddCache;
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
	public List<PriceRegionValueAddEntity> getPriceRegionAirByCache(String code, Date date) {
		List<PriceRegionValueAddEntity> priceRegionAirEntities = priceRegionValueAddCache.get(code);
		List<PriceRegionValueAddEntity> regionEntities = new ArrayList<PriceRegionValueAddEntity>();
		if(CollectionUtils.isNotEmpty(priceRegionAirEntities)) {
			for (PriceRegionValueAddEntity priceRegionAirEntity : priceRegionAirEntities) {
				Date beginTime = priceRegionAirEntity.getBeginTime();
				Date endTime = priceRegionAirEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					regionEntities.add(priceRegionAirEntity);
				}
			}
		} 
		return regionEntities;
	}

}