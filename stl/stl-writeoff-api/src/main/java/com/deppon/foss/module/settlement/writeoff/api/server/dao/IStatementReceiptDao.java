package com.deppon.foss.module.settlement.writeoff.api.server.dao;


import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;

/**
 * 对账单回执Dao接口
 * @author foss-qiaolifeng
 * @date 2012-11-2 下午6:26:49
 */
public interface IStatementReceiptDao {

	/**
	 * 查询最后打印的对账单回执
	 * @author foss-qiaolifeng
	 * @date 2012-11-2 下午6:26:22
	 * @param statementBillNo
	 *            对账单号
	 * @return StatementConfReceiptEntity
	 * 				对账单回执
	 */
	StatementConfReceiptEntity queryLastPrintReceipt(String statementBillNo);
	
	/**
	 * 根据对账单号查询对账单回执列表
	 * @author foss-qiaolifeng
	 * @date 2012-11-2 下午6:26:22
	 * @param statementBillNo
	 *            对账单号
	 * @return List<StatementConfReceiptEntity>
	 * 				对账单回执集合
	 */
	List<StatementConfReceiptEntity> queryReceiptList(String statementBillNo);
	
	/**
	 * 新增对账单回执
	 * @author foss-qiaolifeng
	 * @date 2012-11-6 上午11:01:33
	 * @param entity
	 *            对账单回执
	 * @return int
	 * 			对账单回执条数
	 */
	int add(StatementConfReceiptEntity entity);
	
	/**
	 * 修改对账单回执
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 上午11:42:52
	 * @param entity
	 *            对账单回执
	 * @return int
	 * 			对账单回执条数
	 */
	int updateStatementConfReceipt(StatementConfReceiptEntity entity);
}
