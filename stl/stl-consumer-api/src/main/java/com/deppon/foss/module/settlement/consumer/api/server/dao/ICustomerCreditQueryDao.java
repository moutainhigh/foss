package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerCreditGridDto;

/**
 * 客户欠款状态同步Dao
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-24 下午4:10:27
 */
public interface ICustomerCreditQueryDao {

	/**
	 * 从综合管理表中获得所有客户行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:40:23
	 */
	@Deprecated
	int queryTotalCustomersFromBse();

	/**
	 * 按页查询客户信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:39:14
	 */
	List<CustomerCreditGridDto> queryCustomerFromBse(Date start, Date end,
			int offset, int limit);

	/**
	 * 获得所有客户行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:40:23
	 */
	int queryTotalOrgsFromBse();

	/**
	 * 按页查询客户信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:39:14
	 */
	List<CustomerCreditGridDto> queryOrgFromBse(int offset, int limit);

	/**
	 * 获得超期欠款的月结运单单号
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午2:05:31
	 */
	String queryCreditOverdueNumber(String customerCode, Date overdueDate);

	/**
	 * 查询月结红冲的应收单金额之和
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午2:14:58
	 */
	BigDecimal queryCreditWriteBackAmount(String customerCode, Date inceptDate,
			Date endDate);

	/**
	 * 
	 * 获得超期欠款的应收单实体
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 下午4:11:47
	 * @param orgCode
	 *            组织编码
	 * @param overdueDays
	 *            超期日期
	 * @return 超期欠款的单号
	 */
	BillReceivableEntity queryDebtOverdueReceivable(String orgCode,
			Date overdueDate);

	/**
	 * 
	 * 查询指定日期范围内的红冲金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 上午11:00:43
	 * @param inceptDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 红冲金额
	 */
	BigDecimal queryDebtWriteBackAmount(String orgCode, Date inceptDate,
			Date endDate);
}
