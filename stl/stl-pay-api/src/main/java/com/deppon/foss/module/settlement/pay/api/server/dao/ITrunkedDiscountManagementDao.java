package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;
import com.deppon.foss.module.settlement.pay.api.shared.domain.TrunkDiscountManEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.TrunkedDiscountManagementDto;

/**
 * 零担事后折折扣管理Dao
 * @ClassName: ITrunkedDiscountManagementDao
 * @Description: (这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-22 上午10:52:05
 * 
 */
public interface ITrunkedDiscountManagementDao {
	/**
	 * 按客户查询折扣单
	 */
	public List<TrunkDiscountManEntity> queryTrunkedDiscountByCust(
			TrunkedDiscountManagementDto dto, int start, int limit);

	/**
	 * 按客户查询总行数
	 */
	public int queryCountDiscountByCust(TrunkedDiscountManagementDto dto);

	/**
	 * 按单号查询折扣单
	 */
	public List<TrunkDiscountManEntity> queryTrunkedDiscountByNumber(
			TrunkedDiscountManagementDto dto, int start, int limit);

	/**
	 * 按单号去查询总记录数
	 */
	public int queryCountDiscountByNumber(TrunkedDiscountManagementDto dto);

}
