package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * ClassName: RegionEcGoodsDao <br/>
 * Function: 精准电商区域DAO. <br/>
 * date: 2016.06.29 <br/>
 *
 * @author 311417 wangfeng
 * @version
 * @since JDK 1.6
 */
public class RegionEcGoodsDao extends SqlSessionDaoSupport implements IRegionEcGoodsDao{

	/** 价格区域. */
	private static final String PRICING_REGION_NAMESPACE = "foss.pkp.pkp-pricing.pricingEcGoodsRegion.";

    /** 根据条件查询区域. */
	private static final String SEARCH_REGION = "searchRegionByCondition";

    /** 根据条件查询区域个数 *. */
	private static final String COUNT_REGION = "countRegionByCondition";

    /** 根据区域ID查询区域 *. */
	private static final String SEARCH_REGION_BY_ID = "searchRegionByID";

    /** 修改区域 *. */
	private static final String UPDATE_REGION = "updateRegion";

    /** 插入区域部门信息 *. */
	private static final String UPDATE_REGIONORG = "updateRegionOrg";

    /** 删除区域 *. */
	private static final String DELETE_REGION = "deleteRegion";

    /** 删除区域关联部门 *. */
	private static final String DELETE_REGION_ORG = "deleteRegionOrg";

    /** 插入区域信息 *. */
	private static final String INSERT_REGION = "insertRegion";

    /** 根据条件查询区域部门信息 *. */
	private static final String SEARCH_REGIONORG = "searchRegionOrgByCondition";

    /** 插入区域部门信息 *. */
	private static final String INSERT_REGIONORG = "insertRegionOrg";

	@Override
	public List<PriceRegionEcGoodsEntity> searchRegionByCondition(
			PriceRegionEcGoodsEntity regionEcEntity, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION;
		return getSqlSession().selectList(sqlAddress, regionEcEntity, rowBounds);
	}

	@Override
	public Long countRegionByCondition(PriceRegionEcGoodsEntity regionEcEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + COUNT_REGION;// 价格区域
		return (Long) getSqlSession().selectOne(sqlAddress, regionEcEntity);
	}

	@Override
	public PriceRegionEcGoodsEntity searchRegionByID(String id,
			String regionNature) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (PriceRegionEcGoodsEntity) getSqlSession().selectOne(sqlAddress, map);
	}

	@Override
	public void updateRegion(PriceRegionEcGoodsEntity regionEcEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGION;
		getSqlSession().update(sqlAddress, regionEcEntity);
	}

	@Override
	public void updateRegionOrg(
			PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcGoodsEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGIONORG;
		getSqlSession().update(sqlAddress, priceRegioOrgnEcGoodsEntity);
	}

	@Override
	public void deleteRegion(List<String> regionIds, String regionNature) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		getSqlSession().update(sqlAddress, map);
	}

	@Override
	public void deleteRegionOrg(List<String> regionIds, String regionNature) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION_ORG;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		getSqlSession().update(sqlAddress, map);
	}

	@Override
	public void addRegion(PriceRegionEcGoodsEntity regionEcEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGION;
		getSqlSession().insert(sqlAddress, regionEcEntity);
	}

	/**
	 * 查询区域与组织关系
	 *
	 */
	@Override
	public List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByCondition(
			PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcGoodsEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGIONORG;
		return getSqlSession().selectList(sqlAddress, priceRegioOrgnEcGoodsEntity);
	}

	@Override
	public void addRegionOrg(
			PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcGoodsEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGIONORG;
		getSqlSession().insert(sqlAddress, priceRegioOrgnEcGoodsEntity);
	}
	/**
	 * 根据区域名称批量查找区域信息
	 *
	 * @author wangfeng
	 * @date 2016.06.29
	 * @param names 区域名称List
	 * @param regionNature 区域性质
	 * @param billDate 开单日期
	 * @return
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, PriceRegionEcGoodsEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "findRegionByNames";
		}
		Map map = new HashMap();
		map.put("regionNames", names);
		map.put("billDate", billDate);
		map.put("active", FossConstants.ACTIVE);
		List<PriceRegionEcGoodsEntity> regionEntityList = getSqlSession().selectList(sqlAddress, map);
		if (CollectionUtils.isNotEmpty(regionEntityList)) {
			Map<String, PriceRegionEcGoodsEntity> returnMap = new HashMap<String, PriceRegionEcGoodsEntity>();
			for (int loop = 0; loop < regionEntityList.size(); loop++) {
				returnMap.put(regionEntityList.get(loop).getRegionName(), regionEntityList.get(loop));
			}
			return returnMap;

		}
		return null;
	}

	/**
	 * Search region org by dept no.
	 * @param deptNo the dept no
	 * @param regionNature the region nature
	 * @return the list
	 * @Description: 根据网点CODE查询价格区域关联部门
	 * @author 311417 wangfeng
	 * @date 2016.06.29
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByDeptNo(String deptNo, String regionNature) {
		String sqlAddress = null;
		if (StringUtil.equals(PricingConstants.PRICING_REGION, regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByDeptNo";
		}
		return getSqlSession().selectList(sqlAddress, deptNo);
	}

	/**
	 * Search region by district for cache.
	 * @param provinceCode the province code
	 * @param cityCode the city code
	 * @param countyCode the county code
	 * @param regionNature the region nature
	 * @param billDate the bill date
	 * @return the list
	 * @Description: 根据District CODE查询区域信息
	 * @author 311417 wangfeng
	 * @date 2016.06.29
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PriceRegionEcGoodsEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode,
			String countyCode, String regionNature, Date billDate) {
		String sqlAddress = null;
		if (regionNature != null
				&& regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE+"searchRegionByDistrictForCache";
		}
		Map parameterMap = new HashMap();
		parameterMap.put("proCode", provinceCode);
		parameterMap.put("cityCode", cityCode);
		parameterMap.put("countyCode", countyCode);
		parameterMap.put("active", FossConstants.ACTIVE);
		parameterMap.put("billDate", billDate);
		return getSqlSession().selectList(sqlAddress, parameterMap);
	}

	/**
	 * <p>根据行政区域查逻辑区域（区域查询专用，其他不可用）</p>
	 *  行政区域由小到达，逐级过滤。传入的各级行政区域为或者的关系。</p>
	 * @author 311417 wangfeng
	 * @date 2016.06.29
	 * @param regionEntity
	 * @return
	 *
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionByDistrict(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionEcGoodsEntity> searchEcRegionByDistrictNew(PriceRegionEcGoodsEntity regionEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionByDistrictNew";
		}
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}
	/**
	 *
	 * @Description: 根据区域ID查询价格区域关联部门
	 * @author FOSSDP-yangkang
	 * @date 2014-7-11 下午1:50:42
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByRegionId(String deptRegionId, String regionNature) {
		String sqlAddress = null;
		Map map = new HashMap();
		map.put("id", deptRegionId);
		map.put("billDate", new Date());
		if (StringUtil.equals(PricingConstants.PRICING_REGION, regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByRegionId";
		}
		return getSqlSession().selectList(sqlAddress, map);
	}

    /**
      * 通过名称查询区域
      * pgy
      */
	@Override
	public List<PriceRegionEcGoodsEntity> findRegionByName(
			PriceRegionEcGoodsEntity regionEntity) {
		regionEntity.setBillDate(new Date());
		String sqlAddress =  PRICING_REGION_NAMESPACE + "findRegionByName";
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}
	
	/**
	 * 根据区域名称区域信息
	 */
	@Override
	public List<PriceRegionEcGoodsEntity> searchRegionByName(String regionName,
			String regionNature) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionByName";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("regionName", regionName);
		return getSqlSession().selectList(sqlAddress, map);
	}

	@Override
	public List<PriceRegionEcGoodsEntity> checkRegionOrganizationType(
			String administrativeRegionCodes, String regionNature, Date now) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "checkRegionOrganizationType";
		Map map = new HashMap();
		map.put("administrativeRegionCodes", administrativeRegionCodes);
		map.put("now", now);
		return getSqlSession().selectList(sqlAddress, map);
	}
	
	/**
	 * 根据区域id查询
	 * @param id
	 * @return
     */
	@Override
	public PriceRegionEcGoodsEntity searchRegionByID(String id) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (PriceRegionEcGoodsEntity) getSqlSession().selectOne(sqlAddress, map);
	}
}
