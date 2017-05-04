package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto;

/**
 * 应收单操作Dao
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-8 下午6:31:38
 */
public interface IWriteoffBillReceivableQueryDao {

	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 下午6:33:16
	 * @param billReceivableDto
	 *      	应收单参数Dto
	 * @return List<BillReceivableEntity>
	 * 			应收单集合
	 */
	List<BillReceivableEntity> queryByReceivableNOs(BillReceivableDto billReceivableDto);

	/**
	 * 根据传入的参数获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 下午6:33:32
	 * @param billReceivableDto
	 *      	应收单参数Dto
	 * @return List<BillReceivableEntity>
	 * 			应收单集合
	 */
	List<BillReceivableEntity> queryByReceivableParams(BillReceivableDto billReceivableDto);
	

	/**
	 * 根据传入的运单号查询应收单
	 * 
	 * @author 杨书硕
	 * @param billReceivableDto 查询实体
	 * @return List<BillReceivableEntity> 应收单合集
	 * @date 2013-07-08 11:46
	 */
	List<BillReceivableEntity> queryByWayBillNOs(BillReceivableDto billReceivableDto);

}
