package com.deppon.foss.module.pickup.pricing.api.server.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 
 * ClassName: IRegionEcGoodsDao <br/>
 * date: 2016.6.30 <br/>
 *
 * @author 311417 wangfeng
 * @version 
 * @since JDK 1.6
 */
public interface IRegionEcGoodsDao {

	/**
	 * 
	 * 根据条件查询区域信息
	 * Date:2016.6.30
	 * @author 311417	wangfeng
	 * @param regionEcEntity
	 * @param start
	 * @param limit
	 * @return
	 * @since JDK 1.6
	 */
	List<PriceRegionEcGoodsEntity> searchRegionByCondition(PriceRegionEcGoodsEntity regionEcEntity,
															int start, int limit);
	
	/**
	 * 
	 * countRegionByCondition:根据条件查询区域信息个数. <br/>
	 * 
	 * Date:2016.6.30
	 * @author 157229-zxy
	 * @param regionEcEntity
	 * @return
	 * @since JDK 1.6
	 */
	Long countRegionByCondition(PriceRegionEcGoodsEntity regionEcEntity);
	
	/**
	 * 
	 * searchRegionByID:根据ID与区域标识查询区域信息. <br/>
	 * 
	 * Date:2016.6.30
	 * @author 157229-zxy
	 * @param id
	 * @param regionNature
	 * @return
	 * @since JDK 1.6
	 */
	PriceRegionEcGoodsEntity searchRegionByID(String id, String regionNature);
	
	/**
	 * updateRegion:修改区域. <br/>
	 * 
	 * Date:2016.6.30
	 * @author 311417 wangfeng
	 * @param regionEcEntity
	 * @since JDK 1.6
	 */
	void updateRegion(PriceRegionEcGoodsEntity regionEcEntity);
	
	/**
	 * 
	 * updateRegionOrg:修改区域与部门的关联. <br/>
	 * 
	 * Date:2016.6.30
	 * @author 311417 wangfeng
	 * @param priceRegioOrgnEcGoodsEntity
	 * @since JDK 1.6
	 */
	void updateRegionOrg(PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcGoodsEntity);
	
	/**
	 * 
	 * deleteRegion:删除区域. <br/>
	 * 
	 * Date:2016.6.30
	 * @author 311417 wangfeng
	 * @param regionIds
	 * @param regionNature
	 * @since JDK 1.6
	 */
	public void deleteRegion(List<String> regionIds, String regionNature);
	
	/**
	 * 
	 * deleteRegionOrg:删除区域下关联部门. <br/>
	 * 
	 * Date:2016.6.30
	 * @author 311417 wangfeng
	 * @param regionIds
	 * @param regionNature
	 * @since JDK 1.6
	 */
	void deleteRegionOrg(List<String> regionIds, String regionNature);
	
	/**
	 * 
	 * addRegion:新增区域. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangfeng
	 * @param regionEcEntity
	 * @since JDK 1.6
	 */
	void addRegion(PriceRegionEcGoodsEntity regionEcEntity);
	
	/**
	 * 
	 * searchRegionOrgByCondition:根据条件查询区域关联部门信息. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangfeng
	 * @param priceRegioOrgnEcGoodsEntity
	 * @return
	 * @since JDK 1.6
	 */
	List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByCondition(PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcGoodsEntity);
	
	/**
	 * 
	 * addRegionOrg:新增区域与部门的关联. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangfeng
	 * @param priceRegioOrgnEcGoodsEntity
	 * @since JDK 1.6
	 */
	void addRegionOrg(PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcGoodsEntity);
	
	/**
	 * 根据区域名称批量查找区域信息
	 * @author 311417 wangfeng
	 * @date 2016.06.29
	 * @param names 区域名称List
	 * @param regionNature 区域性质
	 * @param billDate 开单日期
	 * @return
	 * @see
	 */
	 Map<String, PriceRegionEcGoodsEntity> findRegionByNames(
			 List<String> names, String regionNature, Date billDate);

	
	List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByDeptNo(String key,
															   String pricingRegion);

	
	
	/**
	 * 
	 * @Description: 根据District CODE查询区域信息
	 * @author 311417 wangfeng
	 * @date 2016.06.29
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionEcGoodsEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode,
																   String countyCode, String regionNature, Date billDate);
	

	List<PriceRegionEcGoodsEntity> searchEcRegionByDistrictNew(
			PriceRegionEcGoodsEntity regionEntity);
	/**
	 * 
	 * @Description: 根据区域ID查询价格区域关联部门 
	 * @author 311417 wangfeng
	 * @date 2016.06.29
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByRegionId(String deptRegionId, String regionNature);

	/**
	 * 通过名称查询区域
	 * @Date 2016.06.29
	 * @author 311417 wangfeng
	 * @param regionEntity
	 * @return
	 */
	List<PriceRegionEcGoodsEntity> findRegionByName(
			PriceRegionEcGoodsEntity regionEntity);
	
	/**
	 * 根据区域名称区域信息
	 * @param regionName
	 * @param regionNature
	 * @return
	 */
	List<PriceRegionEcGoodsEntity> searchRegionByName(String regionName,
			String regionNature);
	
	/**
	 * 查询行政组织区域存在项
	 * @param administrativeRegionCodes
	 * @param regionNature
	 * @param date
	 * @return
	 */
	List<PriceRegionEcGoodsEntity> checkRegionOrganizationType(
			String administrativeRegionCodes, String regionNature, Date date);
	
	/**
	 * 根据区域id查询区域信息
	 * @param id
	 * @return
     */
	PriceRegionEcGoodsEntity searchRegionByID(String id);


}
