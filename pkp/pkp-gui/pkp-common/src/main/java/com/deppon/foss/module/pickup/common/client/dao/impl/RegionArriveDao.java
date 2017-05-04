/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.deppon.foss.module.pickup.common.client.dao.IRegionArriveDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.google.inject.Inject;

/**
 * 到达区域Dao

  * @ClassName: IRegionArriveDao

  * @Description: 20131011下载离线数据 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
@Repository
public class RegionArriveDao implements IRegionArriveDao {
	// 价格区域
	private static final String PRICING_REGION_NAMESPACE = "foss.pkp.pkp-pricing.pricingRegionArrive.";
	// 插入区域信息
	private static final String INSERT_REGION = "insertRegion";
	// 删除区域
	private static final String DELETE_REGION = "deleteRegion";
	// 激活区域
	private static final String UPDATE_REGION = "updateRegion";
	// 激活区域
	private static final String SEARCH_REGION_BY_ID = "searchRegionByID";
	// 插入区域部门信息
	private static final String INSERT_REGIONORG = "insertRegionOrg";
	// 插入区域部门信息
	private static final String UPDATE_REGIONORG = "updateRegionOrg";
	// 删除区域关联部门
	private static final String DELETE_REGION_ORG = "deleteRegionOrg";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 
	 * @Description: 新增空运区域
	 * @date 2013-3-12 上午10:29:55
	 * @param regionEntity
	 * @version V1.0
	 */
	@Override
	public boolean addRegion(PriceRegionArriveEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGION;
		PriceRegionArriveEntity entity = searchRegionByID(regionEntity.getId());
		if(entity != null){
			return false;
		}else{
			sqlSession.insert(sqlAddress, regionEntity);
			return true;
		}
	}


	/**
	  * Description: 根据id删除记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param regionIds
	  * @return
	 */
	@Override
	public void deleteRegion(List<String> regionIds) {
		String sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		sqlSession.update(sqlAddress, map);
	}

	/**
	 * 
	 * @Description: 修改空运区域
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:30:58
	 * @param regionArriveEntity
	 * @version V1.0
	 */
	@Override
	public void updateRegion(PriceRegionArriveEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGION;
		sqlSession.update(sqlAddress, regionEntity);
	}


	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author deppon-157229-zxy
	 * @version 1.0 2013-10-11 上午8:10:37
	 * @param id
	 * @return
	 */
	@Override
	public PriceRegionArriveEntity searchRegionByID(String id) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (PriceRegionArriveEntity) sqlSession.selectOne(sqlAddress, map);
	}

	/**
	  * Description: 插入一条记录  新增空运区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgArriveEntity
	  * @return
	 */
	@Override
	public boolean addRegionOrg(PriceRegionOrgArriveEntity priceRegionOrgArriveEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGIONORG;
		List<PriceRegionOrgArriveEntity> entityLst= searchRegionOrgByRegionId(priceRegionOrgArriveEntity.getId());
		if(entityLst != null && entityLst.size() > 0){
			return false;
		}else{
			sqlSession.insert(sqlAddress, priceRegionOrgArriveEntity);
			return true;
		}
	}

	/**
	  * Description: 修改区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgArriveEntity
	  * @return
	 */
	@Override
	public void updateRegionOrg(PriceRegionOrgArriveEntity priceRegionOrgAirEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGIONORG;
		sqlSession.update(sqlAddress, priceRegionOrgAirEntity);
	}

	/**
	  * Description: 删除区域下关联部门
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionIds
	  * @return
	 */
	@Override
	public void deleteRegionOrg(List<String> regionIds) {
		String sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION_ORG;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		sqlSession.update(sqlAddress, map);
	}

	/**
	  * Description: 根据区域ID查询价格区域关联部门
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param deptRegionId
	  * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionOrgArriveEntity> searchRegionOrgByRegionId(String deptRegionId) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByRegionIdClient";
		Map map = new HashMap();
		map.put("id", deptRegionId);
		map.put("billDate", new Date());
		return sqlSession.selectList(sqlAddress, map);
	}

}