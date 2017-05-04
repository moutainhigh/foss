/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IExpressCityService {

	/**
     * 插条记录
     */
	boolean addExpressCity(ExpressCityEntity e);
	/**
	 * 更新条记录
	 */
	void updateExpressCity(ExpressCityEntity e);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(ExpressCityEntity e);
	/**
	 * 删除
	 * @param delete
	 */
	void delete(ExpressCityEntity e);
}
