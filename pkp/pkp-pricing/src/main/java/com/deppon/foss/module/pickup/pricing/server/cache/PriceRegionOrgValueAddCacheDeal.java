package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;

/**
 * 
 * @Description: 价格区域与组织关系Cache处理
 * PriceRegionOrgCacheDeal.java Create on 2013-2-19 下午2:03:53
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionOrgValueAddCacheDeal {
	/**
	 * priceRegionOrgAirCache缓存
	 */
	private PriceRegionOrgValueAddCache priceRegionOrgValueAddCache;
	
	public PriceRegionOrgValueAddCache getPriceRegionOrgValueAddCache() {
		return priceRegionOrgValueAddCache;
	}

	public void setPriceRegionOrgValueAddCache(
			PriceRegionOrgValueAddCache priceRegionOrgValueAddCache) {
		this.priceRegionOrgValueAddCache = priceRegionOrgValueAddCache;
	}



	/**
	 * 
	 * @Description: 根据网点编号以及发生日期从缓存中查询符合条件 价格区域与组织关系实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public PriceRegionOrgValueAddEntity getPriceRegionOrgAirByCache(String code, Date date) {
		List<PriceRegionOrgValueAddEntity> priceRegionOrgAirEntities = priceRegionOrgValueAddCache.get(code);
		PriceRegionOrgValueAddEntity entity = null;
		if(CollectionUtils.isNotEmpty(priceRegionOrgAirEntities)) {
			for (PriceRegionOrgValueAddEntity priceRegionOrgAirEntity : priceRegionOrgAirEntities) {
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