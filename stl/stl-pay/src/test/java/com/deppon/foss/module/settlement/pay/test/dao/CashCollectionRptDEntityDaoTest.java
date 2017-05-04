package com.deppon.foss.module.settlement.pay.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;

public class CashCollectionRptDEntityDaoTest extends BaseTestCase{
	/**
	 * 现金收入(缴款记录)信息测试测试Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(CashCollectionRptDEntityDaoTest.class);

	@Autowired
	private ICashCollectionRptDEntityDao cashCollectionRptDEntityDao;
	
	@Test
	@Rollback(true)
	public void queryUpdateCashinDComerptTest() {
		BillCashRecPayInDDto billCashRecPayInDDto = new BillCashRecPayInDDto();
		billCashRecPayInDDto.setVerifyType(1);
		billCashRecPayInDDto.setModifyTime(new Date());
		
		
		List<CashCollectionRptDEntity> perList = new ArrayList<CashCollectionRptDEntity>();
		CashCollectionRptDEntity cashCollectionRptDEntity = new CashCollectionRptDEntity();
		cashCollectionRptDEntity.setId("test1");
		cashCollectionRptDEntity.setVersionNo("1");	
		perList.add(cashCollectionRptDEntity);
		cashCollectionRptDEntity.setId("test2");
		cashCollectionRptDEntity.setVersionNo("1");	
		perList.add(cashCollectionRptDEntity);
		logger.info(perList.toArray());
		
		logger.info("test start...");
		int n = cashCollectionRptDEntityDao.updateCashincomerptD(perList, billCashRecPayInDDto);
		logger.info(n);
		logger.info("test end...");
	}

	/**
	 * @return the cashCollectionRptDEntityDao
	 */
	public ICashCollectionRptDEntityDao getCashCollectionRptDEntityDao() {
		return cashCollectionRptDEntityDao;
	}

	/**
	 * @param cashCollectionRptDEntityDao the cashCollectionRptDEntityDao to set
	 */
	public void setCashCollectionRptDEntityDao(
			ICashCollectionRptDEntityDao cashCollectionRptDEntityDao) {
		this.cashCollectionRptDEntityDao = cashCollectionRptDEntityDao;
	}
	
	
}
