package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.closing.api.server.dao.IBalanceDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IBalanceService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 
 * 期末结账批次服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-15 下午3:43:01
 */
public class BalanceService implements IBalanceService {

	/**
	 * 日志
	 */
	private static final Logger LOG = LogManager.getLogger(BalanceService.class);

	/**
	 * 批次DAO
	 */
	private IBalanceDao balanceDao;

	public void setBalanceDao(IBalanceDao balanceDao) {
		this.balanceDao = balanceDao;
	}

	/**
	 * 结账
	 */
	@Override
	public void balance(Date date) {

		LOG.info("期末结账开始...");

		if (date == null) {
			throw new SettlementException("期末结账截止日期为空.");
		}

		balanceDao.balance(date);

		LOG.info("期末结账结束.");
	}

	@Override
	public void unBalance(Date date) {

		LOG.info("期末反结账开始...");

		if (date == null) {
			throw new SettlementException("期末反结账截止日期为空.");
		}

		balanceDao.unBalance(date);

		LOG.info("期末反结账结束.");

	}

	/**
	 * 查看结账功能是否在运行
	 */
	@Override
	public boolean isBalanceRun() {

		int result = balanceDao.isBalanceRun();

		return result > 0;
	}

	/**
	 * 查询当前结账期间
	 */
	@Override
	public Date currentBalanceDate() {
		return balanceDao.currentBalanceDate();
	}

}
