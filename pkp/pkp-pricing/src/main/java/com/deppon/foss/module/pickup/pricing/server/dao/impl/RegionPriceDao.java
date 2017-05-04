package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionPriceDao;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPriceBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPartnerPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.RegionPriceDto;

public class RegionPriceDao extends SqlSessionDaoSupport implements IRegionPriceDao {
	//mybatis namespace
	private static final String NAME_SPACE = "foss.pricing.RegionPriceEntityMapper.";
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<RegionPriceDto> queryPriceByQueryPriceBean(
			QueryPriceBean queryPriceBean) {
		String sqlAddress = NAME_SPACE+"findPriceByQueryPriceBean";
		Map map = new HashMap();	
		map.put("planId", queryPriceBean.getPlanId());
		map.put("productType", queryPriceBean.getProductType());
		map.put("currentDateTime", queryPriceBean.getCurrentDateTime());
		map.put("active", queryPriceBean.getActive());
		map.put("arrType", "ADMINISTRATIVE");//默认到达区域类型为"行政区域"
		map.put("centralize_pickup", "N");//默认是否上门接货为"否"
		return getSqlSession().selectList(sqlAddress, map);
	}
	/**
	 * <p>TODO到达目的站为网点价格区域</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-24 上午11:41:51
	 * @param id
	 * @param currentDateTime
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegionPartnerPriceDto> queryNetworkRegionPriceEntities(QueryPriceBean queryPriceBean) {
		String sqlAddress = NAME_SPACE+"queryNetworkRegionPriceEntities";
		Map map = new HashMap();
		map.put("id", queryPriceBean.getPlanId());
		map.put("currentDateTime", queryPriceBean.getCurrentDateTime());
		map.put("active", queryPriceBean.getActive());
		map.put("arrType", "DEPT");
		map.put("productCode", queryPriceBean.getProductType());
		return getSqlSession().selectList(sqlAddress, map);
	}
	/**
	 * <p>TODO(行政区域市级的要看市下面是否有营业网点)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-24 上午11:42:00
	 * @param id
	 * @param currentDateTime
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegionPartnerPriceDto> queryCityRegionPriceEntities(QueryPriceBean queryPriceBean) {
		String sqlAddress = NAME_SPACE+"queryCityRegionPriceEntities";
		Map map = new HashMap();
		map.put("id", queryPriceBean.getPlanId());
		map.put("currentDateTime", queryPriceBean.getCurrentDateTime());
		map.put("active", queryPriceBean.getActive());
		map.put("arrType", "ADMINISTRATIVE");
		map.put("productCode", queryPriceBean.getProductType());
		return getSqlSession().selectList(sqlAddress, map);
	}
	/**
	 * <p>TODO(区级的要看区下面是否有营业网点 )</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-24 上午11:42:08
	 * @param id
	 * @param currentDateTime
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegionPartnerPriceDto> queryCountyRegionPriceEntities(QueryPriceBean queryPriceBean) {
		String sqlAddress = NAME_SPACE+"queryCountyRegionPriceEntities";
		Map map = new HashMap();
		map.put("id", queryPriceBean.getPlanId());
		map.put("currentDateTime", queryPriceBean.getCurrentDateTime());
		map.put("active", queryPriceBean.getActive());
		map.put("arrType", "ADMINISTRATIVE");
		map.put("productCode", queryPriceBean.getProductType());
		return getSqlSession().selectList(sqlAddress, map);
	}
}