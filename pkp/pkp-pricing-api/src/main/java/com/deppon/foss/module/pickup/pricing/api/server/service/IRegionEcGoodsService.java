package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRegionEcGoodsService {
	
	/**
	 * searchRegionByCondition:根据条件查询区域. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417
	 * @param regionEntity
	 * @param start
	 * @param limit
	 * @return
	 * @since JDK 1.6
	 */
	public List<PriceRegionEcGoodsEntity> searchRegionByCondition(
			PriceRegionEcGoodsEntity regionEntity, int start, int limit);
	
	/**
	 * countRegionByCondition:根据条件查询区域信息个数. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangefng
	 * @param regionEntity
	 * @return
	 * @since JDK 1.6
	 */
	Long countRegionByCondition(PriceRegionEcGoodsEntity regionEntity);
	
	/**
	 * 
	 * immedietelyActiveRegion:立即激活. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangfeng
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
	 * Date:2016.06.29
	 * @author  311417 wangfeng
	 * @param regionIds
	 * @param regionNature
	 * @since JDK 1.6
	 */
	void deleteRegion(List<String> regionIds, String regionNature);
	
	/**
	 * 
	 * addRegion:新增区域. <br/>

	 * Date:2016.06.29
	 * @author 311417 wangfeng
	 * @param regionEntity
	 * @param addPriceRegioOrgnEntityList
	 * @since JDK 1.6
	 */
	void addRegion(PriceRegionEcGoodsEntity regionEntity,
				   List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList);

	/**
	 * 
	 * updateRegion:修改区域相关信息. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangfeng
	 * @param regionEntity
	 * @param addPriceRegioOrgnEntityList
	 * @param updatePriceRegioOrgnEntityList
	 * @since JDK 1.6
	 */
	void updateRegion(PriceRegionEcGoodsEntity regionEntity,
					  List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList,
					  List<PriceRegioOrgnEcGoodsEntity> updatePriceRegioOrgnEntityList);
	
	/**
	 * 
	 * searchRegionByID:根据条件查询区域信息. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangfeng
	 * @param id
	 * @param regionNature
	 * @return
	 * @since JDK 1.6
	 */
	PriceRegionEcGoodsEntity searchRegionByID(String id, String regionNature);
	
	/**
	 * 
	 * immedietelyStopRegion:立即中止. <br/>
	 * 
	 * Date:2016.06.29
	 * @author 311417 wangfeng
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
	 * Date:2016.06.29
	 * @author 311417 wangfeng
	 * @param priceRegioOrgnEntity
	 * @since JDK 1.6
	 */
	List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByCondition(PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity);
	
	
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
	Map<String,PriceRegionEcGoodsEntity> findRegionByNames(List<String> names, String regionNature, Date billDate);
	
	
	/**
	 * @Description: 根据区域ID获取其下所有的营业部
	 * @author 311417 wangfeng
	 * @date 2016.06.29
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<String> searchRegionOrgCodeByRegionId(String deptRegionId, String regionNature);
	
	/**
	 * 根据区域id查询区域信息
	 * @param id
	 * @return
	 */
	PriceRegionEcGoodsEntity searchRegionByID(String id);
}
