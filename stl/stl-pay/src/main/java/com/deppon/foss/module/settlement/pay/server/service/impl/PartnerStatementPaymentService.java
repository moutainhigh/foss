package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillPayableQueryManageDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPartnerStatementPaymentDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementPaymentService;

public class PartnerStatementPaymentService implements IPartnerStatementPaymentService{
	
	private static final Logger logger = LogManager.getLogger(PartnerStatementPaymentService.class);

	/**
	 * 注入合伙人付款DAO
	 */
	private IPartnerStatementPaymentDao partnerStatementPaymentDao;
	/**
	 * 注入应付单DAO
	 */
	private IBillPayableQueryManageDao billPayableQueryManageDao;
	
	/**
	 * 对账单付款增加付款单明细
	 * @author wwb
	 * @date 2016-02-29
	 */
	@Override
	@Transactional
	public void addBillPaymentD(String paymentNum, List<String> statementBillNos) {
		partnerStatementPaymentDao.addBillPaymentD(paymentNum, statementBillNos);
	}
	
	/**
	 * 对账单付款更新应付单信息
	 * @author wwb
	 * @date 2016-02-29
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public void payForBillPayable(Map map) {
		partnerStatementPaymentDao.payForBillPayable(map);
	}
	
	/**
	 * 回调更新应付单信息
	 * @author 311396 wwb
	 * @date 2016-03-04 下午 3:35:32
	 */
	@SuppressWarnings("rawtypes")
	public void updatePayableForRollBack(Map map){	
		partnerStatementPaymentDao.updatePayableForRollBack(map);
	}
	
	/**
	 * 删除明细更新应付单信息
	 * @author 311396 wwb
	 * @date 2016-03-04 下午 3:35:32
	 */
	public int updatePayableForDel(Map<String, String> map){
		return partnerStatementPaymentDao.updatePayableForDel(map);
	}
	
	@Override
	public int batchUpdatePayableStatementBillNo(Map<String, Object> params) {
		return partnerStatementPaymentDao.batchUpdatePayableStatementBillNo(params);
	}

	
	/**
	 * 根据应付单号查询应付单
	 * @author 311396 wwb
	 * @date 2016-03-04 下午 3:35:32
	 */
	public BillPayableEntity queryPayableByPayableNo(String payableNo){
		return billPayableQueryManageDao.queryPayableByPayableNo(payableNo);
	}
	
	
	
	public void setPartnerStatementPaymentDao(
			IPartnerStatementPaymentDao partnerStatementPaymentDao) {
		this.partnerStatementPaymentDao = partnerStatementPaymentDao;
	}

	public void setBillPayableQueryManageDao(
			IBillPayableQueryManageDao billPayableQueryManageDao) {
		this.billPayableQueryManageDao = billPayableQueryManageDao;
	}

}
