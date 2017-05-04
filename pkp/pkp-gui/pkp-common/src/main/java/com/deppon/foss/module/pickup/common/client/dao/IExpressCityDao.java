/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IExpressCityDao {

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addExpressCityEntity(ExpressCityEntity e);
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateExpressCityEntity(ExpressCityEntity e);
	
	/**
	 * 删除
	 */
	void delete(ExpressCityEntity e);
}
