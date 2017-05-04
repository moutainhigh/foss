package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEffectiveDao;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryEffctiveBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionEffectiveDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPartnerEffectiveDto;

public class RegionEffectiveDao extends SqlSessionDaoSupport implements IRegionEffectiveDao {
	//mybatis namespace
	private static final String NAME_SPACE = "foss.pricing.RegionEffectiveEntityMapper.";
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<RegionEffectiveDto> queryEffctiveByQueryEffctiveBean(
			QueryEffctiveBean queryEffctiveBean) {
		String sqlAddress = NAME_SPACE+"findEffectiveByQueryEffectiveBean";
		Map map = new HashMap();	
		map.put("deptRegionId", queryEffctiveBean.getDeptRegionId());
		map.put("productList", queryEffctiveBean.getProductList());
		map.put("currentDateTime", queryEffctiveBean.getCurrentDateTime());
		map.put("active", queryEffctiveBean.getActive());
		map.put("arrType", "ADMINISTRATIVE");//默认只查询到达区域类型为行政区域的
		return getSqlSession().selectList(sqlAddress, map);
	}
	
	/**
	 * <p>TODO(获取当前出发部门的所有时间区域价格列表)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-26 上午10:48:48
	 * @param effectiveRegionId
	 * @param currentDateTime
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEffectiveDao#queryEffectivePriceList(java.lang.String, java.util.Date)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<RegionPartnerEffectiveDto> queryEffectivePriceList(
			QueryEffctiveBean queryEffctiveBean) {
		Map map = new HashMap();
		map.put("effectiveRegionId", queryEffctiveBean.getDeptRegionId());
		map.put("currentDateTime", queryEffctiveBean.getCurrentDateTime());
		map.put("active", queryEffctiveBean.getActive());
		map.put("productList", queryEffctiveBean.getProductList());
		//区域时效
		List<RegionPartnerEffectiveDto> queryCountryEffectivePriceList=getSqlSession().selectList(NAME_SPACE+"queryCountryEffectivePrice", map);
		//网点时效区域
		List<RegionPartnerEffectiveDto> queryNetworkEffectivePriceList=getSqlSession().selectList(NAME_SPACE+"queryNetworkEffectivePrice", map);
		queryCountryEffectivePriceList.addAll(queryNetworkEffectivePriceList);
		return queryCountryEffectivePriceList;
	}
}
