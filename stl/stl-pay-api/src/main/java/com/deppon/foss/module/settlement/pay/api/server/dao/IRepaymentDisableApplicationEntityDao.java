package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableApplicationEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableResultDto;

/**
 *还款单申请DAO 
 *
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午5:42:08
 */
public interface IRepaymentDisableApplicationEntityDao {
	
	/**
	 * 
	 *添加申请作废记录
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午4:30:18 
	 * @param dto
	 */
	void addDisableApplication(RepaymentDisableApplicationEntity entity);
	
	/**查询作废申请RESULTDTO参数*/
	BillRepaymentDisableResultDto queryResultDto(BillRepaymentDisableDto dto);
	
	
	/**
	 * 
	 *查询作废申请
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午5:43:33 
	 * @param dto
	 * @return
	 */
	List<RepaymentDisableApplicationEntity> queryDisableApplicationByDto(BillRepaymentDisableDto dto);
	
	/**
	 * 
	 *查询作废申请
	 *(分页查询)
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-25 下午9:49:10 
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<RepaymentDisableApplicationEntity> queryDisableApplicationByDto(BillRepaymentDisableDto dto, int start ,int limit);
	
	/**
	 * 
	 *审批
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-26 上午9:53:05 
	 * @param dto
	 */
	void approveApply(BillRepaymentDisableDto dto) ;
	
}
