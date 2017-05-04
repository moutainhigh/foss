package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyComletedResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto;


/**
 * 未完全核销单据查询service
 * @author foss-qiaolifeng
 * @date 2013-5-14 下午3:11:09
 */
public interface IBillUnverifyCompletedService extends IService {
	
	/**
	 * 根据客户编码查询该客户的所有未完全核销单据
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-5-14 下午3:11:12
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	List<BillUnverifyComletedResultDto> queryBillUnverifyCompletedList(BillUnverifyCompletedQueryDto dto);
	
	/**
	 * 根据客户编码查询该客户的所有未完全核销单据总条数
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-5-14 下午4:12:42
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	Long queryBillUnverifyCompletedTotals(BillUnverifyCompletedQueryDto dto);
}
