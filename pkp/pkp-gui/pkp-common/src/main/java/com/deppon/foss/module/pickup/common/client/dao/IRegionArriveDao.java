/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;

/**
 * 到达区域Dao

  * @ClassName: IRegionArriveDao

  * @Description: 20131011下载离线数据 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
public interface IRegionArriveDao {

	/**
	  * Description: 插入一条记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionArriveEntity
	  * @return
	 */
	boolean addRegion(PriceRegionArriveEntity regionArriveEntity);

	/**
	 * 
	 * @Description: 修改空运区域
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:30:58
	 * @param regionArriveEntity
	 * @version V1.0
	 */
	void updateRegion(PriceRegionArriveEntity regionArriveEntity);

	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author deppon-157229-zxy
	 * @version 1.0 2013-10-11 上午8:10:37
	 * @param id
	 * @return
	 */
	PriceRegionArriveEntity searchRegionByID(String id);

	/**
	  * Description: 根据id删除记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param regionIds
	  * @return
	 */
	void deleteRegion(List<String> regionIds);
	
	/**
	  * Description: 插入一条记录  新增空运区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgArriveEntity
	  * @return
	 */
	boolean addRegionOrg(PriceRegionOrgArriveEntity priceRegioOrgArriveEntity);
	
	/**
	  * Description: 修改区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgArriveEntity
	  * @return
	 */
	void updateRegionOrg(PriceRegionOrgArriveEntity priceRegioOrgArriveEntity);
	
	/**
	  * Description: 删除区域下关联部门
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionIds
	  * @return
	 */
	void deleteRegionOrg(List<String> regionIds);
	
	/**
	  * Description: 根据区域ID查询价格区域关联部门
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param deptRegionId
	  * @return
	 */
	List<PriceRegionOrgArriveEntity> searchRegionOrgByRegionId(String deptRegionId);
}