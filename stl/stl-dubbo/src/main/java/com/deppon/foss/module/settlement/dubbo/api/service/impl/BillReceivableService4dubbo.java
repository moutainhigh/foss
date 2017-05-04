package com.deppon.foss.module.settlement.dubbo.api.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.ptp.api.define.exception.SettlementException;
import com.deppon.foss.module.settlement.dubbo.api.dao.IBillReceivableEntityDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;
import com.deppon.foss.module.settlement.dubbo.api.service.IBillReceivableService4dubbo;

/**
 * 以dubbo的方式重新实现
 * 
 * @author 335284
 * @since 2016.11.17
 *
 */
public class BillReceivableService4dubbo implements IBillReceivableService4dubbo {
	private static final Logger logger = LoggerFactory.getLogger(BillReceivableService4dubbo.class);

	/**
	 * 应收单Dao
	 */
	private IBillReceivableEntityDao4dubbo billReceivableEntityDao;

	@Override
	public void updateBillReceivableWithholdStatus(BillReceivableEntity entity) {
		// 输入参数校验主要校验id和应收单号ReceivableNo
		if (entity == null || StringUtils.isEmpty(entity.getId()) || StringUtils.isEmpty(entity.getReceivableNo())) {
			throw new SettlementException("确认应收单的应收单号和运单号不能为空！");
		}
		logger.info("entering service, id: " + entity.getId());
		// 修改应收单扣款状态并返回信息，每次修改一条
		int i = this.getBillReceivableEntityDao().updateBillReceivableWithholdStatus(entity);
		if (i != 1) {
			throw new SettlementException("确认应收单修改扣款状态操作失败。");
		}
		logger.info("successfully exit service, id: " + entity.getId());
	}

	@Override
	public BillReceivableEntity selectByWayBillNoAndBillType(String wayBillNo, String billType) {
		if (StringUtils.isEmpty(wayBillNo) || StringUtils.isEmpty(billType)) {
			throw new SettlementException("查询应收单，输入的运单号和应收单类型不能为空！");
		}

		List<BillReceivableEntity> billReceivableEntityList = this.getBillReceivableEntityDao()
				.selectByWayBillNoAndBillType(wayBillNo, billType);

		// 判断是否为空
		if (CollectionUtils.isEmpty(billReceivableEntityList)) {
			return null;
		} else if (billReceivableEntityList.size() != 1) {
			throw new SettlementException(String.format("运单号为:%s的有效应收单有多条数据，请检查数据是否正确", wayBillNo));
		} else {
			BillReceivableEntity billReceivableEntity = billReceivableEntityList.get(0);
			return billReceivableEntity;
		}
	}

	public IBillReceivableEntityDao4dubbo getBillReceivableEntityDao() {
		return billReceivableEntityDao;
	}
	@Autowired
	public void setBillReceivableEntityDao(IBillReceivableEntityDao4dubbo billReceivableEntityDao) {
		this.billReceivableEntityDao = billReceivableEntityDao;
	}

}