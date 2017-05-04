package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectResultDto;

/**
 * 到付清查Service接口
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 4:07:40 PM
 */
public interface IBillFreightToCollectQueryService {
	
	/**
	 * 到付清查Service接口--通过业务日期查询到付清单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 4:08:40 PM
	 */
	List<BillFreightToCollectResultDto> queryBillByBusinessDate(int offset,int start,BillFreightToCollectQueryDto dto,CurrentInfo cInfo);

	/**
	 * 到付清查Service接口--通过记账日期查询到付清单
	 * @author foss-zhangxiaohui
	 * @date Dec 4, 2012 9:19:01 AM
	 */
	List<BillFreightToCollectResultDto> queryBillByAccountDate(int offset,int start,BillFreightToCollectQueryDto dto,CurrentInfo cInfo);
	
	/**
	 * 到付清查Service接口--根据业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:42:45 AM
	 */
	BillFreightToCollectQueryDto queryTotalAmountByBusinessDate(BillFreightToCollectQueryDto dto,CurrentInfo cInfo);

	/**
	 * 到付清查Service接口--查询记账日期数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Dec 10, 2012 10:42:45 AM
	 */
	BillFreightToCollectQueryDto queryTotalAmountByAccountDate(BillFreightToCollectQueryDto dto,CurrentInfo cInfo);

}
