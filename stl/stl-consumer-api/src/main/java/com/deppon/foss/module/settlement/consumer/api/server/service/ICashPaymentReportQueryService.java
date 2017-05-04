package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashPaymentReportDto;

/**
 * 现金支出报表Service接口
 * @author foss-zhangxiaohui
 * @date Dec 11, 2012 4:43:15 PM
 */
public interface ICashPaymentReportQueryService {

	/**
	 * 现金支出报表Service接口--根据Dto分页查询现金支出报表
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 4:45:24 PM
	 */
	List<BillPaymentEntity> queryCashPaymentReportByDto(int offset,int start,CashPaymentReportDto dto,CurrentInfo cInfo);
	
	/**
	 * 现金支出报表Service接口--根据Dto查询符合条件的数据库记录
	 * @author foss-zhangxiaohui
	 * @date Dec 12, 2012 4:33:24 PM
	 */
	List<CashPaymentReportDto> queryTotalRecordsInDB(CashPaymentReportDto dto,CurrentInfo cInfo);
	
	/**
	 * 现金支出报表Service接口--根据Dto查询现金支出报表
	 * @author foss-zhangxiaohui
	 * @date Jan 08, 2013 4:45:24 PM
	 */
	List<BillPaymentEntity> queryCashPaymentReportByDto(CashPaymentReportDto dto,CurrentInfo cInfo);
}
