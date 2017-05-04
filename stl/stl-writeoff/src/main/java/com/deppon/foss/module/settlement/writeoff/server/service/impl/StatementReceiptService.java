/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementReceiptDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService;
import com.deppon.foss.util.UUIDUtils;

/**
 * 对账单回执service
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-5 上午10:08:13
 */
public class StatementReceiptService implements IStatementReceiptService {

	/**
	 * 对账单回执Dao
	 */
	private IStatementReceiptDao statementReceiptDao;

	/**
	 * 对账单service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 查询最后打印的对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-5 上午10:08:08
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService#queryLastPrintReceipt()
	 */
	@Override
	public StatementConfReceiptEntity queryLastPrintReceipt(String statementBillNo) {

		// 查询该对账单号的最后一条已打印对账单回执
		return statementReceiptDao.queryLastPrintReceipt(statementBillNo);
	}

	/**
	 * 打印对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-5 下午5:57:05
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService#printStatementConfReceipt(java.lang.String)
	 */
	@Override
	@Transactional
	public void printStatementConfReceipt(String statementBillId,CurrentInfo info) {

		// 1、根据对账单I查询对账单信息
		StatementOfAccountEntity statementOfAccountEntity = statementOfAccountService.queryByPrimaryKey(statementBillId);

		// 2、根据对账单信息生成并保存对账单回执信息
		addStatementConfReceiptEntity(statementOfAccountEntity, info);
	}

	/**
	 * 根据对账单生成对账单确认回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-6 上午10:16:14
	 */
	private StatementConfReceiptEntity addStatementConfReceiptEntity(StatementOfAccountEntity statementOfAccountEntity, CurrentInfo info) {

		// 如果对账单实体不存在，提示没有找到对应的对账单
		if (statementOfAccountEntity == null) {
			//提示没有找到对应的对账单
			throw new SettlementException("没有找到对应的对账单!");
		}

		// 生成对账单确认回执
		StatementConfReceiptEntity statementConfReceiptEntity = new StatementConfReceiptEntity();

		// 对账单回执ID、编号均采用ID设置
		String id = UUIDUtils.getUUID();//获取ID
		statementConfReceiptEntity.setId(id);//设置ID
		statementConfReceiptEntity.setConReceiptNo(id);//设置编号

		// 对账单号
		statementConfReceiptEntity.setStatementBillNo(statementOfAccountEntity.getStatementBillNo());

		// 部门编码、名称，来源于对账单的制作部门编码、名称
		statementConfReceiptEntity.setOrgCode(statementOfAccountEntity.getCreateOrgCode());//创建网点编码
		statementConfReceiptEntity.setOrgName(statementOfAccountEntity.getCreateOrgName());//创建网点名称

		// 客户编码、名称，来源于对账单的客户编码、名称
		statementConfReceiptEntity.setCustomerCode(statementOfAccountEntity.getCustomerCode());//客户编码
		statementConfReceiptEntity.setCustomerName(statementOfAccountEntity.getCustomerName());//客户名称

		// 创建人、创建时间
		statementConfReceiptEntity.setCreateUserName(info.getEmpName());//创建人
		statementConfReceiptEntity.setCreateTime(new Date());//创建时间

		// 保存对账单确认回执
		statementReceiptDao.add(statementConfReceiptEntity);

		//返回打印回执对象
		return statementConfReceiptEntity;
	}

	/**
	 * 修改对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 上午11:43:47
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService#updateStatementConfReceipt(com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public int updateStatementConfReceipt(StatementConfReceiptEntity entity) {

		//判断传入实体不能为空
		if (entity != null) {
			// 修改对账单
			int i = statementReceiptDao.updateStatementConfReceipt(entity);
			//返回数据更改条数
			return i;
		//否则	
		} else {
			//直接返回0
			return 0;
		}

	}

	/**
	 * 根据对账单号查询对账单回执列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 下午1:27:53
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService#queryReceiptList(java.lang.String)
	 */
	@Override
	public List<StatementConfReceiptEntity> queryReceiptList(String statementBillNo) {

		return statementReceiptDao.queryReceiptList(statementBillNo);
	}

	/**
	 * @return statementReceiptDao
	 */
	public IStatementReceiptDao getStatementReceiptDao() {
		return statementReceiptDao;
	}

	/**
	 * @param statementReceiptDao
	 */
	public void setStatementReceiptDao(IStatementReceiptDao statementReceiptDao) {
		this.statementReceiptDao = statementReceiptDao;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

}
