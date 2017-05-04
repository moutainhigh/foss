/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;

/**
 *  加星标营业部dao（全国到达青岛区域的部分目的营业部需要加星标）
 * @author 026123-foss-lifengteng
 *
 */
public interface IAsteriskSalesDeptDao {
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addAsteriskSalesDept(AsteriskSalesDeptEntity asteriskSalesDept);
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateAsteriskSalesDept(AsteriskSalesDeptEntity asteriskSalesDept);
	
	/**
	 * 删除
	 */
	void delete(AsteriskSalesDeptEntity asteriskSalesDept);

}
