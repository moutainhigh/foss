package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto;

/**
 * 查询现金收款单Dao接口
 * @author foss-zhangxiaohui
 * @date Nov 2, 2012 3:31:23 PM
 */
public interface IBillCashCollectionQueryDao {
	
	/**
	 * 查询现金收款单Dao接口--根据业务日期查询现金收款单
	 * @author foss-zhangxiaohui
	 * @date Nov 02, 2012 3:35:55 PM
	 */
	List<BillCashCollectionEntity> queryBillCashCollectionByBusinessDate(int offset,int start,BillCashCollectionQueryDto billCashCollectionQueryDto);

	/**
	 * 查询现金收款单Dao接口--根据记账日期查询现金收款单
	 * @author foss-zhangxiaohui
	 * @date Nov 02, 2012 3:36:55 PM
	 */
	List<BillCashCollectionEntity> queryBillCashCollectionByAccountDate(int offset,int start,BillCashCollectionQueryDto billCashCollectionQueryDto);
	
	/**
	 * 查询现金收款单Dao接口--通过业务日期查询数据库中记录总条数，总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:47:06 AM
	 */
	BillCashCollectionQueryDto queryTotalAmountByBusinessDate(BillCashCollectionQueryDto billCashCollectionQueryDto);
	
	/**
	 * 查询现金收款单Dao接口--通过记账日期查询数据库中记录总条数，总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:47:06 AM
	 */
	BillCashCollectionQueryDto queryTotalAmountByAccountDate(BillCashCollectionQueryDto billCashCollectionQueryDto);
	
	
	
	/**
	 * 按运单号查询现金收款单（来源于小票和运单号）
	 * @author 095793-foss-LiQin
	 * @date 2013-4-3 上午9:29:25
	 * @param billCashCollectionQueryDto
	 * @return
	 */
	List<BillCashCollectionEntity> queryBillCashCollectionByWaybillNo(BillCashCollectionQueryDto billCashCollectionQueryDto);
}
