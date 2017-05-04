/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface ISalesDescExpandsService {

	/**
     * 插条记录
     */
	boolean addSalesDescExpands(SalesDescExpandEntity e);
	/**
	 * 更新条记录
	 */
	void updateSalesDescExpands(SalesDescExpandEntity e);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(SalesDescExpandEntity e);
	/**
	 * 删除
	 * @param delete
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
