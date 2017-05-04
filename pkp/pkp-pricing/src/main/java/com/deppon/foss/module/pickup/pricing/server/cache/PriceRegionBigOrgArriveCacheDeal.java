package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsOrgArriveEntity;
/**
 * 
 * @Description: 到达区域与组织关系Cache处理
 * PriceRegionOrgCacheDeal.java Create on 2013-2-19 下午2:03:53
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionBigOrgArriveCacheDeal {
	/**
	 * priceRegionOrgArriveCache缓存
	 */
	private PriceRegionBigOrgArriveCache priceRegionBigOrgArriveCache;
	


	public PriceRegionBigOrgArriveCache getPriceRegionBigOrgArriveCache() {
		return priceRegionBigOrgArriveCache;
	}



	public void setPriceRegionBigOrgArriveCache(
			PriceRegionBigOrgArriveCache priceRegionBigOrgArriveCache) {
		this.priceRegionBigOrgArriveCache = priceRegionBigOrgArriveCache;
	}



	/**
	 * 
	 * @Description: 根据网点编号以及发生日期从缓存中查询符合条件 价格区域与组织关系实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-PanGuoYang
	 * @date 2014-7-11 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public List<PriceRegionBigGoodsOrgArriveEntity> getPriceRegionOrgAirByCache(String code, Date date) {
		List<PriceRegionBigGoodsOrgArriveEntity> priceRegionOrgAirEntities = priceRegionBigOrgArriveCache.get(code);
		List<PriceRegionBigGoodsOrgArriveEntity> entitys = new ArrayList<PriceRegionBigGoodsOrgArriveEntity>();
		if(CollectionUtils.isNotEmpty(priceRegionOrgAirEntities)) {
			for (PriceRegionBigGoodsOrgArriveEntity priceRegionOrgAirEntity : priceRegionOrgAirEntities) {
				Date beginTime = priceRegionOrgAirEntity.getBeginTime();
				Date endTime = priceRegionOrgAirEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entitys.add(priceRegionOrgAirEntity);
					 
				}
			}
		} 
		return entitys;
	}

}