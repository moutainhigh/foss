package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;

public interface IDopStatementService {
	/**
	 * 更新家装对账单
	 * @author zya
	 * @date 2015-12-10
	 */
	public void updateStatementForWriteOff(HomeStatementEntity entity,CurrentInfo cInfo);
	
	/**
	 * 按对账单单号查询对账单信息
	 * @author zya
	 * @date 2015-12-10
	 */
	public HomeStatementEntity queryByStatementNo(String statementNo);
	/**
	 * 按对账单单号查询对账单明细信息
	 * @author zya
	 * @date 2015-12-10
	 */
	public List<HomeStatementDEntity> queryByStatementBillNo(String statementNo,String orgCode);
	
	/**
	 * 根据对账单号去查询部门
	 * @author zya
	 * @date 2015-12-10
	 */
	public List<HomeStatementDEntity> getOrgNameByStatementBillNo(String statementNo);
}
