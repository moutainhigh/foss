package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;



/**
 * 
 * ClassName: IRegionBigGoodsDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014-6-13 下午3:14:08 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public interface IRegionBigGoodsDao {

	/**
	 * 
	 * 根据条件查询区域信息
	 * Date:2014-6-13下午3:15:57
	 * @author 157229-zxy
	 * @param regionEntity
	 * @param start
	 * @param limit
	 * @return
	 * @since JDK 1.6
	 */
	List<PriceRegionBigGoodsEntity> searchRegionByCondition(PriceRegionBigGoodsEntity regionBgEntity,
			int start, int limit);
	
	/**
	 * 
	 * checkRegionOrganizationType:查询行政组织区域存在项. <br/>
	 * 
	 * Date:2014-6-13下午3:23:16
	 * @author 157229-zxy
	 * @param id
	 * @param administrativeRegionCodes
	 * @param regionNature
	 * @return
	 * @since JDK 1.6
	 */
	@SuppressWarnings("rawtypes")
	List<PriceRegionBigGoodsEntity> checkRegionOrganizationType(Map map,String regionNature);
	
	/**
	 * 
	 * countRegionByCondition:根据条件查询区域信息个数. <br/>
	 * 
	 * Date:2014-6-13下午7:10:11
	 * @author 157229-zxy
	 * @param regionEntity
	 * @return
	 * @since JDK 1.6
	 */
	Long countRegionByCondition(PriceRegionBigGoodsEntity regionBgEntity);
	
	/**
	 * 
	 * searchRegionByID:根据ID与区域标识查询区域信息. <br/>
	 * 
	 * Date:2014-6-13下午7:11:39
	 * @author 157229-zxy
	 * @param id
	 * @param regionNature
	 * @return
	 * @since JDK 1.6
	 */
	PriceRegionBigGoodsEntity searchRegionByID(String id,String regionNature);
	
	/**
	 * 
	 * updateRegion:修改区域. <br/>
	 * 
	 * Date:2014-6-13下午7:12:19
	 * @author 157229-zxy
	 * @param regionEntity
	 * @since JDK 1.6
	 */
	void updateRegion(PriceRegionBigGoodsEntity regionBgEntity);
	
	/**
	 * 
	 * updateRegionOrg:修改区域与部门的关联. <br/>
	 * 
	 * Date:2014-6-13下午7:12:56
	 * @author 157229-zxy
	 * @param PriceRegioOrgnBigGoodsEntity
	 * @since JDK 1.6
	 */
	void updateRegionOrg(PriceRegioOrgnBigGoodsEntity priceRegioOrgnBigGoodsEntity);
	
	/**
	 * 
	 * deleteRegion:删除区域. <br/>
	 * 
	 * Date:2014-6-13下午7:19:45
	 * @author 157229-zxy
	 * @param regionIds
	 * @param regionNature
	 * @since JDK 1.6
	 */
	public void deleteRegion(List<String> regionIds, String regionNature);
	
	/**
	 * 
	 * deleteRegionOrg:删除区域下关联部门. <br/>
	 * 
	 * Date:2014-6-13下午7:20:30
	 * @author 157229-zxy
	 * @param regionIds
	 * @param regionNature
	 * @since JDK 1.6
	 */
	void deleteRegionOrg(List<String> regionIds, String regionNature);
	
	/**
	 * 
	 * addRegion:新增区域. <br/>
	 * 
	 * Date:2014-6-13下午7:22:00
	 * @author 157229-zxy
	 * @param regionEntity
	 * @since JDK 1.6
	 */
	void addRegion(PriceRegionBigGoodsEntity regionBgEntity);
	
	/**
	 * 
	 * searchRegionOrgByCondition:根据条件查询区域关联部门信息. <br/>
	 * 
	 * Date:2014-6-13下午7:22:52
	 * @author 157229-zxy
	 * @param PriceRegioOrgnBigGoodsEntity
	 * @return
	 * @since JDK 1.6
	 */
	List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByCondition(PriceRegioOrgnBigGoodsEntity priceRegioOrgnBigGoodsEntity);
	
	/**
	 * 
	 * addRegionOrg:新增区域与部门的关联. <br/>
	 * 
	 * Date:2014-6-13下午7:23:57
	 * @author 157229-zxy
	 * @param PriceRegioOrgnBigGoodsEntity
	 * @since JDK 1.6
	 */
	void addRegionOrg(PriceRegioOrgnBigGoodsEntity priceRegioOrgnBigGoodsEntity);
	
	/**
	 * 根据区域名称批量查找区域信息
	 * @author yangkang	
	 * @date 2014-6-28 下午8:26:15
	 * @param names 区域名称List
	 * @param regionNature 区域性质
	 * @param billDate 开单日期
	 * @return
	 * @see
	 */
	 Map<String, PriceRegionBigGoodsEntity> findRegionByNames(
		List<String> names, String regionNature, Date billDate);

	
	List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByDeptNo(String key,
			String pricingRegion);

	
	
	/**
	 * 
	 * @Description: 根据District CODE查询区域信息
	 * @author FOSSDP-sz
	 * @date 2013-2-20 上午11:04:55
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionBigGoodsEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode,
			String countyCode, String regionNature, Date billDate);
	

	List<PriceRegionBigGoodsEntity> searchBGRegionByDistrictNew(
			PriceRegionBigGoodsEntity regionEntity);
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
	List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByRegionId(String deptRegionId, String regionNature);

	/**
	 * 通过名称查询区域
	 * @param regionEntity
	 * @return
	 */
	List<PriceRegionBigGoodsEntity> findRegionByName(
			PriceRegionBigGoodsEntity regionEntity);


}
