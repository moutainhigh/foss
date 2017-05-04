package com.deppon.foss.module.settlement.dubbo.api.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deppon.foss.dubbo.ptp.api.define.exception.SettlementException;
import com.deppon.foss.module.settlement.dubbo.api.dao.IBillDepositReceivedEntityDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.dubbo.api.service.IBillDepositReceivedService4dubbo;

/**
 * 预收单服务
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-23 上午9:10:51
 */
@Service
public class BillDepositReceivedService4dubbo implements IBillDepositReceivedService4dubbo {

	private static final Logger logger = LogManager.getLogger(BillDepositReceivedService4dubbo.class);

	/**
	 * 预收单dao
	 */
	private IBillDepositReceivedEntityDao4dubbo billDepositReceivedEntityDao;

	/**
	 * 根据汇款编号查询合伙人预收单是否存在
	 * 
	 * @see com.deppon.foss.module.settlement.IBillDepositReceivedService4dubbo.api.server.service.IBillDepositReceivedService#queryByRemitNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public BillDepositReceivedEntity queryByRemitNo(String remitNo, String active) {
		if (StringUtils.isBlank(remitNo)) {
			throw new SettlementException("查询预收单，汇款编号不能为空");
		}

		// 根据汇款编号查询合伙人预收单
		List<BillDepositReceivedEntity> depositList = getBillDepositReceivedEntityDao().queryByRemitNo(remitNo, active);

		return CollectionUtils.isEmpty(depositList) ? null : depositList.get(0);
	}

	public IBillDepositReceivedEntityDao4dubbo getBillDepositReceivedEntityDao() {
		return billDepositReceivedEntityDao;
	}
	@Autowired
	public void setBillDepositReceivedEntityDao(IBillDepositReceivedEntityDao4dubbo billDepositReceivedEntityDao) {
		this.billDepositReceivedEntityDao = billDepositReceivedEntityDao;
	}
}
