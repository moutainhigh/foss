package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerCreditGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerCreditQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 客户信用额度还原服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-24 下午4:19:53
 */
public class CustomerCreditQueryDao extends iBatis3DaoImpl implements
		ICustomerCreditQueryDao {

	private static final String NAMESPACE = "foss.stl.customerCreditQueryDao.";

	/**
	 * 获得所有客户行数（有合同的月结客户）
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:43:32
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryTotalCustomerRowsFromBse()
	 */
	@Deprecated
	@Override
	public int queryTotalCustomersFromBse() {
		CustomerCreditQueryDto queryDto = new CustomerCreditQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);
		// 返回mybatis执行结果
		return (Integer) this.getSqlSession().selectOne(
				NAMESPACE + "queryTotalCustomerRows", queryDto);
	}

	/**
	 * 获得所有组织个数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:43:47
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryTotalOrgsFromBse()
	 */
	@Override
	public int queryTotalOrgsFromBse() {
		CustomerCreditQueryDto queryDto = new CustomerCreditQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);
		// 返回mybatis执行结果
		return (Integer) this.getSqlSession().selectOne(
				NAMESPACE + "queryTotalOrgRows", queryDto);
	}

	/**
	 * 
	 * 通过分页的形式查询客户信息（有合同的月结客户）
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:44:07
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryCustomerFromBse(int,
	 *      int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCreditGridDto> queryCustomerFromBse(Date start,
			Date end, int offset, int limit) {

		// 构建分页器
		RowBounds rowBounds = new RowBounds(offset, limit);

		// 构建初步的queryDto
		CustomerCreditQueryDto queryDto = new CustomerCreditQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setStartCreateTime(start);
		queryDto.setEndCreateTime(end);

		// 返回mybatis执行结果
		return this.getSqlSession().selectList(NAMESPACE + "queryCustomer",
				queryDto, rowBounds);
	}

	/**
	 * 分页查询组织信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 下午2:49:44
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryOrgFromBse(int,
	 *      int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCreditGridDto> queryOrgFromBse(int offset, int limit) {
		// 构建分页器
		RowBounds rowBounds = new RowBounds(offset, limit);

		// 构建初步的queryDto
		CustomerCreditQueryDto queryDto = new CustomerCreditQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);

		// 返回mybatis执行结果
		return this.getSqlSession().selectList(NAMESPACE + "queryOrg",
				queryDto, rowBounds);
	}

	/**
	 * 查询超期欠款的客户
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午2:07:06
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryCreditOverdueNumber(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public String queryCreditOverdueNumber(String customerCode, Date overdueDate) {
		BillReceivableQueryDto queryDto = new BillReceivableQueryDto();
		// 组织编码
		queryDto.setCustomerCode(customerCode);
		// 最大超期欠款日期
		queryDto.setOverdueDate(overdueDate);
		// 付款方式
		queryDto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT); // 月结

		// 是否红冲
		queryDto.setIsRedBack(FossConstants.NO);

		// 是否有效
		queryDto.setActive(FossConstants.ACTIVE);

		// 返回mybatis执行结果
		return (String) this.getSqlSession().selectOne(
				NAMESPACE + "queryCreditOverdueNumber", queryDto);
	}

	/**
	 * 查询月结红冲的应收金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午2:16:47
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryCreditWriteBackAmount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	@Override
	public BigDecimal queryCreditWriteBackAmount(String customerCode,
			Date inceptDate, Date endDate) {
		// 返回结果集
		BigDecimal writebackAmount = null;
		// 查询条件
		BillReceivableQueryDto queryDto = new BillReceivableQueryDto();
		// 付款方式
		queryDto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT); // 月结
		// 客户组织编码
		queryDto.setCustomerCode(customerCode);
		// 起始日期
		queryDto.setInceptDate(inceptDate);
		// 结束日期
		queryDto.setEndDate(endDate);
		// 是否红冲
		queryDto.setIsRedBack(FossConstants.YES);
		// 返回mybatis执行结果
		writebackAmount = (BigDecimal) this.getSqlSession().selectOne(
				NAMESPACE + "queryCreditWriteBackAmount", queryDto);
		if (writebackAmount == null) {
			writebackAmount = BigDecimal.ZERO;
		}
		return writebackAmount;
	}

	/**
	 * 
	 * 获得临时欠款的超期运单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 下午4:20:10
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryDebtOverdueNumber(java.lang.String,
	 *      int)
	 */
	@Override
	public BillReceivableEntity queryDebtOverdueReceivable(String orgCode,
			Date overdueDate) {
		
		if(overdueDate.before(SettlementConstants.FOSS_ONLINE_DATE)){
			return null;
		}
		
		BillReceivableQueryDto queryDto = new BillReceivableQueryDto();
		// 组织编码
		queryDto.setCustomerCode(orgCode);
		// 最大超期欠款日期
		queryDto.setOverdueDate(overdueDate);
		// 付款方式
		queryDto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT); // 临时欠款

		// 是否红冲
		queryDto.setIsRedBack(FossConstants.NO);

		// 是否有效
		queryDto.setActive(FossConstants.ACTIVE);
		
		//是否初始化
		queryDto.setIsInit(FossConstants.NO);
		
		//排除迁移过来的数据
		queryDto.setInceptDate(SettlementConstants.FOSS_ONLINE_DATE);
		
		// 返回mybatis执行结果
		return (BillReceivableEntity) this.getSqlSession().selectOne(
				NAMESPACE + "queryDebtOverdueReceivable", queryDto);

	}

	/**
	 * 查询临欠的红冲金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 上午11:05:29
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao#queryDebtWriteBackAmount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	@Override
	public BigDecimal queryDebtWriteBackAmount(String orgCode, Date inceptDate,
			Date endDate) {
		// 返回结果集
		BigDecimal writebackAmount = null;
		// 查询条件
		BillReceivableQueryDto queryDto = new BillReceivableQueryDto();
		// 付款方式
		queryDto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT); // 临时欠款
		// 客户组织编码
		queryDto.setCustomerCode(orgCode);
		// 起始日期
		queryDto.setInceptDate(inceptDate);
		// 结束日期
		queryDto.setEndDate(endDate);
		// 是否红冲
		queryDto.setIsRedBack(FossConstants.YES);
		
		queryDto.setIsInit(FossConstants.NO);
		
		// 返回mybatis执行结果
		writebackAmount = (BigDecimal) this.getSqlSession().selectOne(
				NAMESPACE + "queryDebtWriteBackAmount", queryDto);
		
		if (writebackAmount == null) {
			writebackAmount = BigDecimal.ZERO;
		}
		return writebackAmount;
	}

}
