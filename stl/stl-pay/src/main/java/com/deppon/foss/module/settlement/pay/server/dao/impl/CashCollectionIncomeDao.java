package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionIncomeDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionIncomeEntity;


/**
 * 查询缴款记录信息dao
 * @author 095793-foss-LiQin
 * @date 2012-12-18 下午8:37:44
 */
public class CashCollectionIncomeDao extends iBatis3DaoImpl implements ICashCollectionIncomeDao{

	public final static String NAMESPACE="foss.stl.CashCollectionIncomeEntityDao.";
	/** 
	 * 查询缴款记录信息
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-18 下午8:38:20
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionIncomeDao#queryCashCollectionIncome(com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionIncomeEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashCollectionIncomeEntity> queryCashCollectionIncome(
			CashCollectionIncomeEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"selectCashCollectionIncome", entity);
	}
	/** 
	 *新增缴款记录
	 *MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 095793-foss-LiQin
	 * @date 2013-5-23 下午4:30:10
	 * @param record
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionIncomeDao#insert(com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionIncomeEntity)
	 */
	@Override
	public int addCashCollectionIncome(CashCollectionIncomeEntity entity) {
		return this.getSqlSession().insert(NAMESPACE+"insertCashCollectionIncome", entity);
	}
}
