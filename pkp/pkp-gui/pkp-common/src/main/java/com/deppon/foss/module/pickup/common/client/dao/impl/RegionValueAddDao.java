/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import com.deppon.foss.module.pickup.common.client.dao.IRegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.google.inject.Inject;

/**
 * 增值区域Dao

  * @ClassName: IRegionValueAddDao

  * @Description: 20131011下载离线数据功能 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
public class RegionValueAddDao implements IRegionValueAddDao {
	// 价格区域
	private static final String PRICING_REGION_NAMESPACE = "foss.pkp.pkp-pricing.pricingRegionValueAdd.";
	// 插入区域信息
	private static final String INSERT_REGION = "insertRegion";
	// 插入区域部门信息
	private static final String INSERT_REGIONORG = "insertRegionOrg";
	// 插入区域部门信息
	private static final String UPDATE_REGIONORG = "updateRegionOrg";
	// 删除区域
	private static final String DELETE_REGION = "deleteRegion";
	// 删除区域关联部门
	private static final String DELETE_REGION_ORG = "deleteRegionOrg";
	// 激活区域
	private static final String UPDATE_REGION = "updateRegion";
	// 激活区域
	private static final String SEARCH_REGION_BY_ID = "searchRegionByID";

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
	  * Description: 新增空运区域
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionAirEntity
	  * @return
	 */
	@Override
	public boolean addRegion(PriceRegionValueAddEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGION;
		PriceRegionValueAddEntity entity = searchRegionByID(regionEntity.getId());
		if(entity != null){
			return false;
		}else{
			sqlSession.insert(sqlAddress, regionEntity);
			return true;
		}
	}

	/**
	  * Description: 新增空运区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgnEntity
	  * @return
	 */
	@Override
	public boolean addRegionOrg(PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGIONORG;
		List<PriceRegionOrgValueAddEntity> entityLst = searchRegionOrgByRegionId(priceRegionOrgValueAddEntity.getId());
		if(entityLst != null && entityLst.size() > 0){
			return false;
		}else{
			sqlSession.insert(sqlAddress, priceRegionOrgValueAddEntity);
			return true;
		}
	}

	/**
	  * Description: 删除区域
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
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
	  * Description: 修改空运区域
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionEntity
	  * @return
	 */
	@Override
	public void updateRegion(PriceRegionValueAddEntity regionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGION;
		sqlSession.update(sqlAddress, regionEntity);
	}

	/**
	  * Description: 修改区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgnEntity
	  * @return
	 */
	@Override
	public void updateRegionOrg(PriceRegionOrgValueAddEntity priceRegionOrgAirEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGIONORG;
		sqlSession.update(sqlAddress, priceRegionOrgAirEntity);
	}


	/**
	  * Description: 根据条件查询区域信息
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionNature
	  * @return
	 */
	@Override
	public PriceRegionValueAddEntity searchRegionByID(String id) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (PriceRegionValueAddEntity) sqlSession.selectOne(sqlAddress, map);
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
	public List<PriceRegionOrgValueAddEntity> searchRegionOrgByRegionId(String deptRegionId) {
		String sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByRegionIdClient";
		Map map = new HashMap();
		map.put("id", deptRegionId);
		map.put("billDate", new Date());
		return sqlSession.selectList(sqlAddress, map);
	}
}