package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPriceBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPartnerPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPriceDto;

public interface IRegionPriceDao {

	List<RegionPriceDto> queryPriceByQueryPriceBean(
			QueryPriceBean queryPriceBean);

	List<RegionPartnerPriceDto> queryNetworkRegionPriceEntities(QueryPriceBean queryPriceBean);

	List<RegionPartnerPriceDto> queryCityRegionPriceEntities(QueryPriceBean queryPriceBean);

	List<RegionPartnerPriceDto> queryCountyRegionPriceEntities(QueryPriceBean queryPriceBean);
}
