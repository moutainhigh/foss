package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;

/**
 * 偏线价格Dao

  * @ClassName: IOuterPriceDao

  * @Description: 20131011下载离线数据 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
public interface IOuterPriceDao {


//	Long queryOuterPriceVoBatchInfoCount(
//			OuterPriceCondtionDto outerPriceCondtionDto);

	/**
	  * Description: 插入一条记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:10
	  * @param record
	  * @return
	 */
	public boolean insertSelective(OuterPriceEntity record);
	
	/**
	  * Description: 更新记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param record
	  * @return
	 */
	public int updateByPrimaryKeySelective(OuterPriceEntity record);

	/**
	  * Description: 根据id删除记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param ids
	  * @return
	 */
	public int deleteByPrimaryKey(List<String> ids);

	/**
	  * Description: 根据id查询记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param id
	  * @return
	 */
	public OuterPriceEntity selectByPrimaryKey(String id);
	

	/**
	  * Description: 根据id查询记录
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param id
	  * @return
	 */
//	public OuterPricePlanDto selectById(String id);
	
}
