/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsOrgArriveEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @Description:  精准大票到达区域新增修改查询dao
 * RegionAirDao.java Create on 2014-6-26 下午3:50:33
 * @author yangkang
 * Copyright (c) 2014 Company,Inc. All Rights Reserved
 * @version V1.0
 */
@Repository
public class RegionBigGoodsArriveDao extends SqlSessionDaoSupport implements IRegionBigGoodsArriveDao {
	// 价格区域
	private static final String PRICING_REGION_NAMESPACE = "foss.pkp.pkp-pricing.pricingRegionBigGoodsArrive.";
	// 根据条件查询区域
	private static final String SEARCH_REGION = "searchRegionByCondition";
	// 根据条件查询价格区域部门信息
	private static final String SELECT_PRICING_REGION = "selectPriceRegionByCondition";
	// 根据条件查询价格区域部门信息
	private static final String SELECT_PRICING_REGION_ORG = "selectPriceRegionOrgByCondition";
	// 根据行政区域查询区域
	private static final String SEARCH_REGION_BY_DIS = "searchRegionByDistrict";
	// 根据条件查询区域个数
	private static final String COUNT_REGION = "countRegionByCondition";
	// 插入区域信息
	private static final String INSERT_REGION = "insertRegion";
	// 插入区域部门信息
	private static final String INSERT_REGIONORG = "insertRegionOrg";
	// 插入区域部门信息
	private static final String UPDATE_REGIONORG = "updateRegionOrg";
	// 根据条件查询区域部门信息
	private static final String SEARCH_REGIONORG = "searchRegionOrgByCondition";
	// 激活区域
	private static final String ACTIVE_REGION = "activeRegion";
	// 删除区域
	private static final String DELETE_REGION = "deleteRegion";
	// 删除区域关联部门
	private static final String DELETE_REGION_ORG = "deleteRegionOrg";
	// 激活区域org
	private static final String ACTIVE_REGIONORG = "activeRegionOrg";
	// 激活区域
	private static final String UPDATE_REGION = "updateRegion";
	// 激活区域
	private static final String SEARCH_REGION_BY_NAME = "searchRegionByName";
	// 激活区域
	private static final String SEARCH_REGION_BY_ID = "searchRegionByID";

	/**
	 * 
	 * @Description: 根据条件查询区域信息(分页）
	 * @author yangkang
	 * @date 2014-6-26 上午10:25:57
	 * @param regionAirEntity
	 * @param start
	 * @param limit
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionBigGoodsArriveEntity> searchRegionByCondition(PriceRegionBigGoodsArriveEntity regionEntity, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION;
		return getSqlSession().selectList(sqlAddress, regionEntity, rowBounds);
	}

	/**
	  * 
	  * @Description: 根据条件查询区域信息
	  * @author yangkang
	  * @date 2014-6-26 上午11:08:30
	  * @param regionEntity
	  * @return
	  * @version V1.0
	  */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionBigGoodsArriveEntity> findRegionByCondition(PriceRegionBigGoodsArriveEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION;
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}

	/**
	 * 
	 * @Description: 根据条件查询区域信息个数
	 * @author yangkang
	 * @date 2014-6-26上午10:29:01
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	@Override
	public Long countRegionByCondition(PriceRegionBigGoodsArriveEntity regionEntity) {
		// 设置分页条件
		String sqlAddress = PRICING_REGION_NAMESPACE + COUNT_REGION;
		return (Long) getSqlSession().selectOne(sqlAddress, regionEntity);
	}

	/**
	 * 
	 * @Description: 新增空运区域
	 * @author yangkang
	 * @date 2014-6-26 上午10:29:55
	 * @param regionEntity
	 * @version V1.0
	 */
	@Override
	public void addRegion(PriceRegionBigGoodsArriveEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGION;
		getSqlSession().insert(sqlAddress, regionEntity);
	}

	/**
	 * 
	 * @Description: 新增空运区域与部门的关联
	 * @author yangkang
	 * @date 2014-6-26 上午10:31:49
	 * @param priceRegioOrgnEntity
	 * @version V1.0
	 */
	@Override
	public void addRegionOrg(PriceRegionBigGoodsOrgArriveEntity priceRegionBigGoodsOrgArriveEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGIONORG;
		getSqlSession().insert(sqlAddress, priceRegionBigGoodsOrgArriveEntity);
	}

	/**
	 * 
	 * @Description: 根据条件查询区域关联部门信息
	 * @author yangkang
	 * @date 2014-6-26 上午10:34:46
	 * @param priceRegioOrgnEntity
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionBigGoodsOrgArriveEntity> searchRegionOrgByCondition(PriceRegionBigGoodsOrgArriveEntity priceRegionBigGoodsOrgArriveEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGIONORG;
		return getSqlSession().selectList(sqlAddress, priceRegionBigGoodsOrgArriveEntity);
	}

	/**
	 * 
	 * @Description: 激活空运区域
	 * @author yangkang
	 * @date 2014-6-26上午10:35:27
	 * @param regionIds
	 * @param regionNature
	 * @version V1.0
	 */
	@Override
	public void activeRegion(List<String> regionIds, String regionNature) {
		String sqlAddress = PRICING_REGION_NAMESPACE + ACTIVE_REGION;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regionIds", regionIds);
		map.put("versionNo", new Date().getTime());
		getSqlSession().update(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 删除区域
	 * @author yangkang
	 * @date 2014-6-26 上午10:38:19
	 * @param regionIds
	 * @param regionNature
	 * @version V1.0
	 */
	@Override
	public void deleteRegion(List<String> regionIds, String regionNature) {
		String sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		getSqlSession().update(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 删除区域下关联部门
	 * @author yangkang
	 * @date 2014-6-26 上午10:38:30
	 * @param regionIds
	 * @param regionNature
	 * @version V1.0
	 */
	@Override
	public void deleteRegionOrg(List<String> regionIds, String regionNature) {
		String sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION_ORG;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		getSqlSession().update(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 激活区域下关联的区域部门
	 * @author yangkang
	 * @date 2014-6-26 上午10:38:05
	 * @param regionId
	 * @param regionNature
	 * @version V1.0
	 */
	@Override
	public void activeRegionOrg(String regionId, String regionNature) {
		String sqlAddress = PRICING_REGION_NAMESPACE + ACTIVE_REGIONORG;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regionId", regionId);
		map.put("versionNo", new Date().getTime());
		getSqlSession().update(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 修改空运区域
	 * @author yangkang
	 * @date 2014-6-26 上午10:30:58
	 * @param regionEntity
	 * @version V1.0
	 */
	@Override
	public void updateRegion(PriceRegionBigGoodsArriveEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGION;
		getSqlSession().update(sqlAddress, regionEntity);
	}

	/**
	 * 
	 * @Description: 修改区域与部门的关联
	 * @author yangkang
	 * @date 2014-6-26 上午10:33:31
	 * @param priceRegioOrgnEntity
	 * @version V1.0
	 */
	@Override
	public void updateRegionOrg(PriceRegionBigGoodsOrgArriveEntity priceRegionOrgAirEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGIONORG;
		getSqlSession().update(sqlAddress, priceRegionOrgAirEntity);
	}

	/**
	 * 
	 * @Description: 根据行政区域查逻辑区域
	 * @author yangkang
	 * @date 2014-6-26 上午10:36:49
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	@Override 
	@SuppressWarnings("unchecked")
	public List<PriceRegionBigGoodsArriveEntity> searchRegionByDistrict(PriceRegionBigGoodsArriveEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_DIS;
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}
	
	/**
	 * 
	 * @Description: 根据行政区域查逻辑区域
	 * @author yangkang
	 * @date 2014-6-26上午10:36:49
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionBigGoodsArriveEntity> searchRegionByDistrictNew(PriceRegionBigGoodsArriveEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionByDistrictNew";
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}

	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author yangkang
	 * @date 2014-3-12 上午10:37:32
	 * @param id
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionBigGoodsArriveEntity> searchRegionByName(String regionName, String regionNature) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_NAME;
		Map<String, String> map = new HashMap<String, String>();
		map.put("regionName", regionName);
		return getSqlSession().selectList(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author yangkang
	 * @date 2014-3-12 上午10:37:32
	 * @param id
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceRegionBigGoodsArriveEntity searchRegionByID(String id, String regionNature) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (PriceRegionBigGoodsArriveEntity) getSqlSession().selectOne(sqlAddress, map);
	}

	/**
	  * 
	  * @Description: 根据条件查询价格区域信息
	  * @author yangkang
	  * @date 2014-3-12 上午11:18:20
	  * @param priceRegionEntity
	  * @return
	  * @version V1.0
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionBigGoodsArriveEntity> selectPriceRegionByCondition(PriceRegionBigGoodsArriveEntity priceRegionAirEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SELECT_PRICING_REGION;
		return getSqlSession().selectList(sqlAddress, priceRegionAirEntity);
	}

	/**
	 * 
	 * @Description: 根据条件查询价格区域部门信息
	 * @author FOSSDP-Administrator
	 * @date 2014-3-12 上午10:39:05
	 * @param priceRegioOrgnEntity
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionBigGoodsOrgArriveEntity> selectPriceRegionOrgByCondition(PriceRegionBigGoodsOrgArriveEntity priceRegionOrgAirEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SELECT_PRICING_REGION_ORG;
		return getSqlSession().selectList(sqlAddress, priceRegionOrgAirEntity);
	}


	
	/**
	 * 
	 * @author zhangdongping
	 * @date 2012-12-22 下午8:26:15
	 * @param names 区域名称List
	 * @param regionNature 区域性质
	 * @param billDate 开单日期
	 * @return
	 * @see
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, PriceRegionBigGoodsArriveEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "findRegionByNames";
		Map map = new HashMap();
		map.put("regionNames", names);
		map.put("billDate", billDate);
		map.put("active", FossConstants.ACTIVE);
		List<PriceRegionBigGoodsArriveEntity> regionEntityList = getSqlSession().selectList(sqlAddress, map);
		if (CollectionUtils.isNotEmpty(regionEntityList)) {
			Map<String, PriceRegionBigGoodsArriveEntity> returnMap = new HashMap<String, PriceRegionBigGoodsArriveEntity>();
			for (int loop = 0; loop < regionEntityList.size(); loop++) {
				returnMap.put(regionEntityList.get(loop).getRegionName(), regionEntityList.get(loop));
			}
			return returnMap;

		}
		return null;
	}

	/**
	 * 
	 * @Description: 根据区域CODE 批量查找区域信息 Company:IBM
	 * @author yangkang
	 * @date 2014-1-8 下午8:09:34
	 * @param names
	 * @param regionNature
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PriceRegionBigGoodsArriveEntity> findRegionByCodes(List<String> codes, String regionNature, Date billDate) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "findRegionByCodes";
		Map map = new HashMap();
		map.put("regionCodes", codes);
		map.put("billDate", billDate);
		map.put("active", FossConstants.ACTIVE);
		return getSqlSession().selectList(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 查询行政组织区域存在项
	 * @author yangkang
	 * @date 2014-3-12 上午11:21:45
	 * @param administrativeRegionCodes
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionBigGoodsArriveEntity> checkRegionOrganizationType(String administrativeRegionCodes, String regionNature, Date now) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "checkRegionOrganizationType";
		Map map = new HashMap();
		map.put("administrativeRegionCodes", administrativeRegionCodes);
		map.put("now", now);
		return getSqlSession().selectList(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 根据District CODE查询区域信息
	 * @author yangkang
	 * @date 2014-6-26 上午11:04:55
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PriceRegionBigGoodsArriveEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode,
			String countyCode, String regionNature, Date billDate) {
		String sqlAddress = PRICING_REGION_NAMESPACE+"searchRegionByDistrictForCache";
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
	 * @Description: 根据网点CODE查询价格区域关联部门
	 * @author yangkang
	 * @date 2014-6-26 下午1:54:28
	 * @param deptNo
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionBigGoodsOrgArriveEntity> searchRegionOrgByDeptNo(String deptNo, String regionNature) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByDeptNo";
		return getSqlSession().selectList(sqlAddress, deptNo);
	}
	/**
	 * 
	 * @Description: 根据区域ID查询价格区域关联部门 
	 * @author yangkang
	 * @date 2014-6-26 下午1:50:42
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionBigGoodsOrgArriveEntity> searchRegionOrgByRegionId(String deptRegionId) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByRegionId";
		Map map = new HashMap();
		map.put("id", deptRegionId);
		map.put("billDate", new Date());
		return getSqlSession().selectList(sqlAddress, map);
	}
	
	
	/**
	 * 
	 * 获得编号序列
	 * @author 130376-foss-yangkang
	 * @date 2014-6-26 上午8:19:13
	 */
	@Override
	public String querySequence() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sequenceName", "ARRIVE_REGION_SEQ");
		return (String) this.getSqlSession().selectOne(PRICING_REGION_NAMESPACE + "getNextSequenceValue", params);
	}
}