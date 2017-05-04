package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;

/**
 * 应收冲应付Dao
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:01:18
 */
public interface IBillWriteoffBillPayQueryDao {

	/**
	 * <p>
	 * 查询应付单列表
	 * </p>
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-16 下午3:10:30
	 * @param billRecWriteoffBillPayDto
	 *            应收冲应付 参数Dto
	 * @return List<BillPayableEntity> 
	 * 				应付单集合
	 */
	List<BillPayableEntity> queryPayableList(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto);

	/**
	 * <p>
	 * 根据输入应付单号，查询应付单
	 * </p>
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-23 上午9:45:36
	 * @param billRecWriteoffBillPayDto
	 *            应收冲应付 参数Dto
	 * @return List<BillPayableEntity> 
	 * 				应付单集合
	 */
	List<BillPayableEntity> queryPayableNos(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto);


	/**
	 * 根据运单查询应付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午6:42:49
	 * @param billRecWriteoffBillPayDto
	 *            应收冲应付 参数Dto
	 * @return List<BillPayableEntity> 
	 * 				应付单集合
	 */
	List<BillPayableEntity> queryPayByWayBill(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto);
}
