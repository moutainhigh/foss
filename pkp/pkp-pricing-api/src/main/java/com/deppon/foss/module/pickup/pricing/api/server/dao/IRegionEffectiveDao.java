package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryEffctiveBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionEffectiveDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPartnerEffectiveDto;

public interface IRegionEffectiveDao {
	List<RegionEffectiveDto> queryEffctiveByQueryEffctiveBean(
			QueryEffctiveBean queryEffctiveBean);

	List<RegionPartnerEffectiveDto> queryEffectivePriceList(
			QueryEffctiveBean queryEffctiveBean);
}
