/**
 * 
 */
package com.deppon.foss.module.init.client.sync.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface ISynSaleDepartmentDao {
	/**
     * <p>
     * (按登录人查询收货部门)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	public List<SaleDepartmentEntity> querySaleDepartmentByBillGroup(
			String orgCode);
}
