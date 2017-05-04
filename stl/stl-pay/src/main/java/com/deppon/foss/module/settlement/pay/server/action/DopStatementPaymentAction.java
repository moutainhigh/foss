package com.deppon.foss.module.settlement.pay.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IDopStatementPaymentService;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillPaymentVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;

/**
 * 家装
 * 
 * @ClassName: DopStatementPaymentAction
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-10 下午3:48:24
 */
public class DopStatementPaymentAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(DopStatementPaymentAction.class);

	/**
	 * 注入付款单vo
	 */
	private BillPaymentVo vo = new BillPaymentVo();

	/**
	 * 家装Service
	 */
	private IDopStatementPaymentService dopStatementPaymentService;

	/**
	 * 家装付款
	 * @Title: dopToPayment
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public String dopToPayment() {
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (vo.getPaymentEntity().getNotes().getBytes().length > SettlementConstants.MAX_BILL_NO_SIZE) {
				throw new SettlementException("备注信息过长不可以超过1000个字节");
			}
			// 调用录入接口
			String paymentNo = dopStatementPaymentService.dopToPayment(vo.getPaymentEntity(),
							vo.getStatementBillNo(), currentInfo);
			// 设置返回付款单号
			vo.getPaymentEntity().setPaymentNo(paymentNo);
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getErrorCode(), e);
			return returnError(e);
		}
	}
	
	/*********getter/setter***********/
	public BillPaymentVo getVo() {
		return vo;
	}

	public void setVo(BillPaymentVo vo) {
		this.vo = vo;
	}

	public void setDopStatementPaymentService(
			IDopStatementPaymentService dopStatementPaymentService) {
		this.dopStatementPaymentService = dopStatementPaymentService;
	}
}
