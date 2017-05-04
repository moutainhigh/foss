package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.module.settlement.pay.api.shared.dto.TrunkedDiscountManagementDto;

/**
 * 
 * @ClassName: ITrunkedDiscountManagementService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-22 上午10:51:51
 * 
 */
public interface ITrunkedDiscountManagementService {
	/**
	 * 按客户查询折扣单
	 * 
	 */
	public TrunkedDiscountManagementDto queryTrunkedDiscountByCust(
			TrunkedDiscountManagementDto dto, int start, int limit);

	/**
	 * 按单号查询折扣单
	 */
	public TrunkedDiscountManagementDto queryTrunkedDiscountByNumber(
			TrunkedDiscountManagementDto dto, int start, int limit);

}
