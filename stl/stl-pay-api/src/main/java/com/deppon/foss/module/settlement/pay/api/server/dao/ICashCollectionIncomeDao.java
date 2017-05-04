package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionIncomeEntity;

/**
 * 现金收入缴款信息dao（更新部门未缴款金额）
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-18 下午8:29:42
 */
public interface ICashCollectionIncomeDao {

	/**
	 * 查询缴款金额
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-18 下午8:31:28
	 */
	List<CashCollectionIncomeEntity> queryCashCollectionIncome(CashCollectionIncomeEntity entity);
	
    /**
     * 新增缴款记录
     * @author 095793-foss-LiQin
     * @date 2013-5-23 下午4:27:53
     * @param record
     * @return
     */
    int addCashCollectionIncome(CashCollectionIncomeEntity entity);
}
