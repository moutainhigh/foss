package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;

/**
 * 应收冲应付Dao
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:01:18
 */
public interface IBillWriteoffBillRecQueryDao {

	/**
	 * (查询应收单列表)
	 * @author 095793-foss-LiQin
	 * @date 2012-10-16 下午3:07:08
	 * @param billRecWriteoffBillPayDto
	 *            应收冲应付 参数Dto
	 * @return List<BillReceivableEntity>
	 * 				应收单集合
	 */
	 List<BillReceivableEntity> queryReceivableList(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto);

	/**
	 * 根据输入应收单号，查询应收单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-23 上午9:44:40
	 * @param billRecWriteoffBillPayDto
	 *            应收冲应付 参数Dto
	 * @return List<BillReceivableEntity>
	 * 				应收单集合
	 */
	 List<BillReceivableEntity> queryReceivableNos(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto);

	/**
	 * 根据运单号查询应收单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午6:42:25
	 * @param billRecWriteoffBillPayDto
	 *            应收冲应付 参数Dto
	 * @return List<BillReceivableEntity>
	 * 				应收单集合
	 */
	 List<BillReceivableEntity> queryRecByWayBill(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto);

}
