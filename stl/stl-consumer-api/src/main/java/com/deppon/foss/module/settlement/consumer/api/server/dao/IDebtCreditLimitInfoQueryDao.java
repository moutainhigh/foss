package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.DebtCreditLimitInfoResultDto;

/**
 * 查询最早欠款客户已用额度信息Dao接口
 * 
 * @author foss-zhangxiaohui
 * @date Jan 15, 2013 2:29:00 PM
 */
public interface IDebtCreditLimitInfoQueryDao {
	
	/**
	 * 查询最早欠款客户已用额度信息
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 15, 2013 3:08:46 PM
	 * @param  active 
	 *           有效
	 */
	List<DebtCreditLimitInfoResultDto> queryDebtCreditLimitInfo(String active,Date beginDate,Date endDate);
}
