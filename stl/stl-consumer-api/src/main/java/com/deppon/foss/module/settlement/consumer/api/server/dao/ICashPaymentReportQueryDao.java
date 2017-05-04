package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashPaymentReportDto;

/**
 * 现金支出报表Dao接口
 * @author foss-zhangxiaohui
 * @date Dec 11, 2012 4:46:42 PM
 */
public interface ICashPaymentReportQueryDao {
	
	/**
	 * 现金支出报表Dao接口--根据Dto分页查询现金支出报表
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 4:45:24 PM
	 */
	List<BillPaymentEntity> queryCashPaymentReportByDto(int offset,int start,CashPaymentReportDto dto);
	
	/**
	 * 现金支出报表Dao接口--根据Dto查询符合条件的数据库记录
	 * @author foss-zhangxiaohui
	 * @date Dec 12, 2012 4:33:24 PM
	 */
	List<CashPaymentReportDto> queryTotalRecordsInDB(CashPaymentReportDto dto);
	
	/**
	 * 现金支出报表Dao接口--根据Dto查询现金支出报表
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 4:45:24 PM
	 */
	List<BillPaymentEntity> queryCashPaymentReportByDto(CashPaymentReportDto dto);
}
