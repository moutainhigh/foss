package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;

/**
 * 自提价最低一票Dao

  * @ClassName: IMinFeePlanDao

  * @Description: 20131011离线下载 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
public interface IMinFeePlanDao {

	/**
	  * Description: 插入一条记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param record
	  * @return
	 */
	public boolean insertMinFeePlan(MinFeePlanEntity minFeePlanEntity);

	/**
	  * Description: 根据id删除记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param record
	  * @return
	 */
	public int deleteMinFeePlanById(String id);

	/**
	  * Description: 根据id查询记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param record
	  * @return
	 */
	public MinFeePlanEntity selectMinFeePlanById(String id);

	/**
	  * Description: 更新记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param record
	  * @return
	 */
	public int updateMinFeePlanEntity(MinFeePlanEntity minFeePlanEntity);

}
