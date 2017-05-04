package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * ClassName: RegionBigGoodsDao <br/>
 * Function: 精准大票区域DAO. <br/>
 * date: 2014-6-13 下午7:07:18 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public class RegionBigGoodsDao extends SqlSessionDaoSupport implements IRegionBigGoodsDao{
	
	/** 价格区域. */
	private static final String PRICING_REGION_NAMESPACE = "foss.pkp.pkp-pricing.pricingBigGoodsRegion.";
	/** 时效区域. */
	private static final String PRESCRIPTION_REGION_NAMESPACE = "foss.pkp.pkp-pricing.perscriptionRegion.";
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
	public List<PriceRegionBigGoodsEntity> searchRegionByCondition(
			PriceRegionBigGoodsEntity regionBgEntity, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION;
		return getSqlSession().selectList(sqlAddress, regionBgEntity, rowBounds);
	}

	@Override
	public List<PriceRegionBigGoodsEntity> checkRegionOrganizationType(Map map, String regionNature) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + "checkRegionOrganizationType";
		
		return getSqlSession().selectList(sqlAddress, map);
	}

	@Override
	public Long countRegionByCondition(PriceRegionBigGoodsEntity regionBgEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + COUNT_REGION;// 价格区域
		return (Long) getSqlSession().selectOne(sqlAddress, regionBgEntity);
	}

	@Override
	public PriceRegionBigGoodsEntity searchRegionByID(String id,
			String regionNature) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (PriceRegionBigGoodsEntity) getSqlSession().selectOne(sqlAddress, map);
	}

	@Override
	public void updateRegion(PriceRegionBigGoodsEntity regionBgEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGION;
		getSqlSession().update(sqlAddress, regionBgEntity);
	}

	@Override
	public void updateRegionOrg(
			PriceRegioOrgnBigGoodsEntity priceRegioOrgnBigGoodsEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGIONORG;
		getSqlSession().update(sqlAddress, priceRegioOrgnBigGoodsEntity);
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
	public void addRegion(PriceRegionBigGoodsEntity regionBgEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGION;
		getSqlSession().insert(sqlAddress, regionBgEntity);
	}

	/**
	 * 查询大票区域与组织关系
	 * 作者：潘国仰
	 */
	@Override
	public List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByCondition(
			PriceRegioOrgnBigGoodsEntity priceRegioOrgnBigGoodsEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGIONORG;
		return getSqlSession().selectList(sqlAddress, priceRegioOrgnBigGoodsEntity);
	}

	@Override
	public void addRegionOrg(
			PriceRegioOrgnBigGoodsEntity priceRegioOrgnBigGoodsEntity) {
		String sqlAddress = null;
		sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGIONORG;
		getSqlSession().insert(sqlAddress, priceRegioOrgnBigGoodsEntity);
	}
	/**
	 * 
	 * 根据区域名称批量查找区域信息
	 * 
	 * @author yangkang	
	 * 
	 * @date 2014-6-28 下午8:26:15
	 * 
	 * @param names 区域名称List
	 * 
	 * @param regionNature 区域性质
	 * 
	 * @param billDate 开单日期
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, PriceRegionBigGoodsEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "findRegionByNames";
		} else if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "findRegionByNames";
		}
		Map map = new HashMap();
		map.put("regionNames", names);
		map.put("billDate", billDate);
		map.put("active", FossConstants.ACTIVE);
		List<PriceRegionBigGoodsEntity> regionEntityList = getSqlSession().selectList(sqlAddress, map);
		if (CollectionUtils.isNotEmpty(regionEntityList)) {
			Map<String, PriceRegionBigGoodsEntity> returnMap = new HashMap<String, PriceRegionBigGoodsEntity>();
			for (int loop = 0; loop < regionEntityList.size(); loop++) {
				returnMap.put(regionEntityList.get(loop).getRegionName(), regionEntityList.get(loop));
			}
			return returnMap;

		}
		return null;
	}
	
	/**
	 * Search region org by dept no.
	 *
	 * @param deptNo the dept no
	 * 
	 * @param regionNature the region nature
	 * 
	 * @return the list
	 * 
	 * @Description: 根据网点CODE查询价格区域关联部门
	 * 
	 * @author FOSS-潘国仰
	 * 
	 * @date 2014-07-09  下午1:54:28
	 * 
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByDeptNo(String deptNo, String regionNature) {
		String sqlAddress = null;
		if (StringUtil.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "searchRegionOrgByDeptNo";
		} else if (StringUtil.equals(PricingConstants.PRICING_REGION, regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByDeptNo";
		}
		return getSqlSession().selectList(sqlAddress, deptNo);
	}

	/**
	 * 
	 * Search region by district for cache.
	 *
	 * @param provinceCode the province code
	 * 
	 * @param cityCode the city code
	 * 
	 * @param countyCode the county code
	 * 
	 * @param regionNature the region nature
	 * 
	 * @param billDate the bill date
	 * 
	 * @return the list
	 * 
	 * @Description: 根据District CODE查询区域信息
	 * 
	 * @author FOSS-潘国仰
	 * 
	 * @date 2014-07-09 上午11:04:55
	 * 
	 * @version V1.0
	 * 
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PriceRegionBigGoodsEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode,
			String countyCode, String regionNature, Date billDate) {
		String sqlAddress = null;
		if (regionNature != null
				&& regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE+"searchRegionByDistrictForCache";
		} else if (regionNature != null
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
	 * 
	 * <p>根据行政区域查逻辑区域（区域查询专用，其他不可用）</p> 
	 * 
	 *  行政区域由小到达，逐级过滤。传入的各级行政区域为或者的关系。</p> 
	 *  
	 * @author DP-Foss-潘国仰
	 * 
	 * @date 2014-07-09 下午2:21:32
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionByDistrict(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionBigGoodsEntity> searchBGRegionByDistrictNew(PriceRegionBigGoodsEntity regionEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "searchRegionByDistrictNew";
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
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
	public List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByRegionId(String deptRegionId, String regionNature) {
		String sqlAddress = null;
		Map map = new HashMap();
		map.put("id", deptRegionId);
		map.put("billDate", new Date());
		if (StringUtil.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "searchRegionOrgByRegionId";
		} else if (StringUtil.equals(PricingConstants.PRICING_REGION, regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByRegionId";
		}
		return getSqlSession().selectList(sqlAddress, map);
	}
 /**
  * 通过名称查询区域
  * pgy
  */
	@Override
	public List<PriceRegionBigGoodsEntity> findRegionByName(
			PriceRegionBigGoodsEntity regionEntity) {
		regionEntity.setBillDate(new Date());
		String sqlAddress =  PRICING_REGION_NAMESPACE + "findRegionByName";
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}
}
