/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;

/**
 * 增值区域Dao

  * @ClassName: IRegionValueAddDao

  * @Description: 20131011下载离线数据 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
public interface IRegionValueAddDao {

	/**
	  * Description: 新增空运区域
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionAirEntity
	  * @return
	 */
	boolean addRegion(PriceRegionValueAddEntity regionAirEntity);

	/**
	  * Description: 修改空运区域
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionEntity
	  * @return
	 */
	void updateRegion(PriceRegionValueAddEntity regionAirEntity);

	/**
	  * Description: 新增空运区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgnEntity
	  * @return
	 */
	boolean addRegionOrg(PriceRegionOrgValueAddEntity priceRegioOrgAirEntity);

	/**
	  * Description: 修改区域与部门的关联
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param priceRegioOrgnEntity
	  * @return
	 */
	void updateRegionOrg(PriceRegionOrgValueAddEntity priceRegioOrgAirEntity);

	/**
	  * Description: 根据条件查询区域信息
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionNature
	  * @return
	 */
	PriceRegionValueAddEntity searchRegionByID(String id);

	/**
	  * Description: 删除区域
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param regionIds
	  * @return
	 */
	void deleteRegion(List<String> regionIds);

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
	List<PriceRegionOrgValueAddEntity> searchRegionOrgByRegionId(String deptRegionId);
	
}