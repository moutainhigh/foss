/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IAsteriskSalesDeptService {

	/**
     * 插条记录
     */
	boolean addAsteriskSalesDept(AsteriskSalesDeptEntity asteriskSalesDeptGroup);
	/**
	 * 更新条记录
	 */
	void updateAsteriskSalesDept(AsteriskSalesDeptEntity asteriskSalesDept);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(AsteriskSalesDeptEntity asteriskSalesDept);
	/**
	 * 删除
	 * @param delete
	 */
	void delete(AsteriskSalesDeptEntity asteriskSalesDept);
}
