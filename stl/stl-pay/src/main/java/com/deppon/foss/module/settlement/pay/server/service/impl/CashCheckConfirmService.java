package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCheckConfirmDao;
import com.deppon.foss.module.settlement.pay.api.server.service.ICashCheckConfirmService;
/**
 * @author 218392 张永雪
 * @date 2015-12-08 10:59:50
 * 现金盘点以及未收银确认查询Service
 */
public class CashCheckConfirmService implements ICashCheckConfirmService{
	/**
	 * 注入cashCheckConfirmDao
	 * set 注入
	 */
	private ICashCheckConfirmDao cashCheckConfirmDao;

	public void setCashCheckConfirmDao(ICashCheckConfirmDao cashCheckConfirmDao) {
		this.cashCheckConfirmDao = cashCheckConfirmDao;
	}


	@Override
	public List<String> queryCashUnconfirmCod(String deptCode) {
		
		return cashCheckConfirmDao.queryCashUnconfirmNo(deptCode);
	}
	
	
}
