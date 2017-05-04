/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface ISalesDescExpandsDao {
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addSalesDescExpandEntity(SalesDescExpandEntity e);
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateSalesDescExpandEntity(SalesDescExpandEntity e);
	
	/**
	 * 删除
	 */
	void delete(SalesDescExpandEntity e);
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	List<String> queryByCodeAndPickup(String code);
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	List<String> queryByCodeAndDelivery(String code);
}
