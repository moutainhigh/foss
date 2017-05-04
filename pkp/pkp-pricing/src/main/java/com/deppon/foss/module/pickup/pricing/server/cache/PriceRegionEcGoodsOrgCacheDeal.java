package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description: 价格区域与组织关系Cache处理
 * PriceRegionEcOrgCacheDeal.java Create on 2016.06.29
 * @author 311417 wangfeng
 * @version V1.0
 */
public class PriceRegionEcGoodsOrgCacheDeal {
	/**
	 * priceRegionEcOrgCache 缓存
	 */
	private PriceRegionEcGoodsOrgCache priceRegionEcOrgCache;
	
	/**
	 * 获得PriceRegionEcOrgCache缓存
	 * @return
	 */
	public PriceRegionEcGoodsOrgCache getPriceRegionEcOrgCache() {
		return priceRegionEcOrgCache;
	}

	/**
	 * 设置PriceRegionEcOrgCache的缓存
	 * @param priceRegionEcOrgCache
	 */
	public void setPriceRegionEcOrgCache(
			PriceRegionEcGoodsOrgCache priceRegionEcOrgCache) {
		this.priceRegionEcOrgCache = priceRegionEcOrgCache;
	}


	/**
	 * 
	 * @Description: 根据网点编号以及发生日期从缓存中查询符合条件 价格区域与组织关系实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author 311417 Wangfeng
	 * @date 2016.06.29
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public PriceRegioOrgnEcGoodsEntity getPriceRegionOrgByCache(String code, Date date) {
		List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntities = priceRegionEcOrgCache.get(code);
		PriceRegioOrgnEcGoodsEntity entity = null;
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntities)) {
			for (PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity : priceRegioOrgnEntities) {
				Date beginTime = priceRegioOrgnEntity.getBeginTime();
				Date endTime = priceRegioOrgnEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = priceRegioOrgnEntity;
					break;
				}
			}
		} 
		return entity;
	}
}