package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableDetailEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;

/**
 * 
 *申请作废明细
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午7:34:56
 */
public interface IRepaymentDisableDetailEntityDao {
	/**
	 * 
	 *插入作废明细
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午8:14:56 
	 * @param entity
	 */
	void addDisableDetail(RepaymentDisableDetailEntity entity);
	
	/**
	 * 
	 *查询作废申请明细
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午5:43:46 
	 * @param repaymentNo
	 * @return
	 */
	List<RepaymentDisableDetailEntity> queryDisableDetailByDto(BillRepaymentDisableDto dto);
	
	/**
	 * 更新明细
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-12-1 下午2:55:35 
	 * @param dto
	 */
	void updateDisableDetailByDto(BillRepaymentDisableDto dto);
}
