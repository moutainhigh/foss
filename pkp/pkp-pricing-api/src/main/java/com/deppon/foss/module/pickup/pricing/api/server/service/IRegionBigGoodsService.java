package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
public interface IRegionBigGoodsService {
	
	/**
	 * 
	 * searchRegionByCondition:根据条件查询区域. <br/>
	 * 
	 * Date:2014-6-13下午4:23:11
	 * @author 157229-zxy
	 * @param regionEntity
	 * @param start
	 * @param limit
	 * @return
	 * @since JDK 1.6
	 */
	public List<PriceRegionBigGoodsEntity> searchRegionByCondition(
			PriceRegionBigGoodsEntity regionEntity, int start, int limit);
	
	/**
	 * 
	 * countRegionByCondition:根据条件查询区域信息个数. <br/>
	 * 
	 * Date:2014-6-13下午6:52:37
	 * @author 157229-zxy
	 * @param regionEntity
	 * @return
	 * @since JDK 1.6
	 */
	Long countRegionByCondition(PriceRegionBigGoodsEntity regionEntity);
	
	/**
	 * 
	 * immedietelyActiveRegion:立即激活. <br/>
	 * 
	 * Date:2014-6-13下午6:53:36
	 * @author 157229-zxy
	 * @param regionId
	 * @param regionNature
	 * @param beginTime
	 * @since JDK 1.6
	 */
	void immedietelyActiveRegion(String regionId, String regionNature,Date beginTime);
	
	/**
	 * 
	 * deleteRegion:删除区域. <br/>
	 * 
	 * Date:2014-6-13下午6:54:34
	 * @author 157229-zxy
	 * @param regionIds
	 * @param regionNature
	 * @since JDK 1.6
	 */
	void deleteRegion(List<String> regionIds, String regionNature);
	
	/**
	 * 
	 * addRegion:新增区域. <br/>

	 * Date:2014-6-13下午6:55:21
	 * @author 157229-zxy
	 * @param regionEntity
	 * @param addPriceRegioOrgnEntityList
	 * @since JDK 1.6
	 */
	void addRegion(PriceRegionBigGoodsEntity regionEntity,
			List<PriceRegioOrgnBigGoodsEntity> addPriceRegioOrgnEntityList);

	/**
	 * 
	 * updateRegion:修改区域相关信息. <br/>
	 * 
	 * Date:2014-6-13下午6:56:16
	 * @author 157229-zxy
	 * @param regionEntity
	 * @param addPriceRegioOrgnEntityList
	 * @param updatePriceRegioOrgnEntityList
	 * @since JDK 1.6
	 */
	void updateRegion(PriceRegionBigGoodsEntity regionEntity,
			List<PriceRegioOrgnBigGoodsEntity> addPriceRegioOrgnEntityList,
			List<PriceRegioOrgnBigGoodsEntity> updatePriceRegioOrgnEntityList);
	
	/**
	 * 
	 * searchRegionByID:根据条件查询区域信息. <br/>
	 * 
	 * Date:2014-6-13下午6:57:28
	 * @author 157229-zxy
	 * @param id
	 * @param regionNature
	 * @return
	 * @since JDK 1.6
	 */
	PriceRegionBigGoodsEntity searchRegionByID(String id,String regionNature);
	
	/**
	 * 
	 * immedietelyStopRegion:立即中止. <br/>
	 * 
	 * Date:2014-6-13下午7:00:10
	 * @author 157229-zxy
	 * @param regionId
	 * @param regionNature
	 * @param endTime
	 * @since JDK 1.6
	 */
	void immedietelyStopRegion(String regionId, String regionNature, Date endTime);
	
	/**
	 * 
	 * searchRegionOrgByCondition:查询部门组织. <br/>
	 * 
	 * Date:2014-6-24下午7:00:10
	 * @author 157229-zxy
	 * @param priceRegioOrgnEntity
	 * @since JDK 1.6
	 */
	List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByCondition(PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity);
	
	
	/**
	 * 根据区域名称批量查找区域信息
	 * @author yangkang
	 * @date 2014-06-28
	 * @param names 区域名称List
	 * @param regionNature 区域性质
	 * @param billDate 开单日期
	 * @return
	 * @see
	 */ 
	Map<String,PriceRegionBigGoodsEntity> findRegionByNames(List<String> names,String regionNature,Date billDate);
	
	
	/**
	 * 
	 * @Description: 根据区域ID获取其下所有的营业部
	 * @author FOSSDP-yangkang
	 * @date 2014-7-11 下午2:15:29
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<String> searchRegionOrgCodeByRegionId(String deptRegionId, String regionNature);
}
