package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 价格区域Cache处理
 * PriceRegionEcGoodsCacheDeal.java Create on 2016.06.29
 * @author 311417 WangFeng
 */
public class PriceRegionEcGoodsCacheDeal {
	/**
	 * PriceRegionEcCache 缓存
	 */
	private PriceRegionEcGoodsCache priceRegionEcCache;
	/**
	 * 获得PriceRegionEcCache   缓存
	 * @return
	 */
	public PriceRegionEcGoodsCache getPriceRegionEcCache() {
		return priceRegionEcCache;
	}
	/**
	 * 设置PriceRegionEcCache 缓存
	 * @param priceRegionEcCache
	 */
	public void setPriceRegionEcCache(PriceRegionEcGoodsCache priceRegionEcCache) {
		this.priceRegionEcCache = priceRegionEcCache;
	}

	/**
	 * 
	 * @Description: 根据价格区域编号以及发生日期从缓存中查询符合条件价格区域实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author 311417 WangFeng
	 * @date 2016.06.29
	 * @param code
	 * @param date
	 * @return
	 */
	public List<PriceRegionEcGoodsEntity> getPriceRegionByCache(String code, Date date) {
		List<PriceRegionEcGoodsEntity> priceRegionEntities = priceRegionEcCache.get(code);
		List<PriceRegionEcGoodsEntity> regionEntities = new ArrayList<PriceRegionEcGoodsEntity>();
		if(CollectionUtils.isNotEmpty(priceRegionEntities)) {
			for (PriceRegionEcGoodsEntity priceRegionEntity : priceRegionEntities) {
				Date beginTime = priceRegionEntity.getBeginTime();
				Date endTime = priceRegionEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					regionEntities.add(priceRegionEntity);
				}
			}
		} 
		return regionEntities;
	}

}