/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;

/**
 * 对账单回执service
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-5 上午10:06:57
 */
public interface IStatementReceiptService {

	/**
	 * 查询最后打印的对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-5 上午10:06:49
	 * @param statementBillNo
	 *  		对账单号
	 * @return StatementConfReceiptEntity
	 * 			对账单回执
	 */
	StatementConfReceiptEntity queryLastPrintReceipt(String statementBillNo);
	
	/**
	 * 根据对账单号查询对账单回执列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-5 上午10:06:49
	 * @param statementBillNo
	 *  		对账单号
	 * @return List<StatementConfReceiptEntity>
	 * 			对账单回执
	 */
	List<StatementConfReceiptEntity> queryReceiptList(String statementBillNo);

	/**
	 * 打印对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-5 下午5:56:33
	 * @param statementBillNo,info
	 *  		对账单号,当前登录用户
	 * @return void
	 * 			
	 */
	void printStatementConfReceipt(String statementBillId,CurrentInfo info);

	/**
	 * 修改对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 上午11:42:52
	 * @param entity
	 *  		对账单回执
	 * @return int
	 * 			修改的对账单条数
	 */
	int updateStatementConfReceipt(StatementConfReceiptEntity entity);
}
