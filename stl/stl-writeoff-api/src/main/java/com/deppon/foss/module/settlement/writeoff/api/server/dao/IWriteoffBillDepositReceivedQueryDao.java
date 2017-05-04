package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;

/**
 * 预收单操作Dao
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-8 下午6:31:16
 */
public interface IWriteoffBillDepositReceivedQueryDao {

	/**
	 * 根据传入的一到多个预收单号，获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 下午6:32:11
	 * @param billDepositReceivedDto
	 *      	预收单参数Dto
	 * @return List<BillDepositReceivedEntity>
	 * 			预收单集合
	 */
	List<BillDepositReceivedEntity> queryByDepositReceivedNOs(BillDepositReceivedDto billDepositReceivedDto);

	/**
	 * 根据传入参数获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 下午6:32:15
	 * @param billDepositReceivedDto
	 *      	预收单参数Dto
	 * @return List<BillDepositReceivedEntity>
	 * 			预收单集合
	 */
	List<BillDepositReceivedEntity> queryByDepositReceivedParams(BillDepositReceivedDto billDepositReceivedDto);
}
