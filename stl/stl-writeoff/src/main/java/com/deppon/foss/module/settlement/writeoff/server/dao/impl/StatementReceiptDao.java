/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementReceiptDao;

/**
 * 对账单回执Dao接口实现类
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-2 下午6:28:22
 */
public class StatementReceiptDao extends iBatis3DaoImpl implements IStatementReceiptDao {
	//命名空间
	private static final String NAMESPACE = "foss.stl.StatementConfReceiptEntityDao.";

	/**
	 * 查询最后打印的对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-6 上午11:02:33
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementReceiptDao#queryLastPrintReceipt(java.lang.String)
	 */
	@Override
	public StatementConfReceiptEntity queryLastPrintReceipt(String statementBillNo) {

		//执行查询操作
		return (StatementConfReceiptEntity) this.getSqlSession().selectOne(NAMESPACE + "selectLastPrintReceipt", statementBillNo);
	}

	/**
	 * 新增对账单确认回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-6 上午11:04:20
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementReceiptDao#add(com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity)
	 */
	@Override
	public int add(StatementConfReceiptEntity entity) {
		//执行插入操作
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/**
	 * 修改对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 上午11:45:41
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementReceiptDao#updateStatementConfReceipt(com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity)
	 */
	@Override
	public int updateStatementConfReceipt(StatementConfReceiptEntity entity) {
		//执行修改操作
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKey",entity);
	}

	/**
	 * 根据对账单号查询对账单回执列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 下午1:29:14
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementReceiptDao#queryReceiptList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementConfReceiptEntity> queryReceiptList(String statementBillNo) {
		// 根据创建时间倒序排序，并执行查最后一条
		return this.getSqlSession().selectList(NAMESPACE + "selectReceiptListByStatementBillNo",statementBillNo);
	}

}
