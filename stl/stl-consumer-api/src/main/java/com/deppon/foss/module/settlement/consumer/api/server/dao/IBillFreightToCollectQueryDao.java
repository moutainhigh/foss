package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectResultDto;

/**
 * 到付清查Dao接口
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 4:05:29 PM
 */
public interface IBillFreightToCollectQueryDao {

	/**
	 * 到付清查Dao接口--通过业务日期查询到付清查清单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 4:05:29 PM
	 */
	List<BillFreightToCollectResultDto> queryBillByBusinessDate(int offset,int start,
			BillFreightToCollectQueryDto dto);

	/**
	 * 到付清查Dao接口--通过记账日期查询到付清查清单
	 * @author foss-zhangxiaohui
	 * @date Nov 23, 2012 12:01:29 PM
	 */
	List<BillFreightToCollectResultDto> queryBillByAccountDate(int offset,int start,
			BillFreightToCollectQueryDto dto);
	
	/**
	 * 到付清查Dao接口--通过业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:48:04 AM
	 */
	BillFreightToCollectQueryDto queryTotalAmountByBusinessDate(BillFreightToCollectQueryDto billFreightToCollectQueryDto);
	
	/**
	 * 到付清查Dao接口--通过记账日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:49:04 AM
	 */
	BillFreightToCollectQueryDto queryTotalAmountByAccountDate(BillFreightToCollectQueryDto billFreightToCollectQueryDto);
}
