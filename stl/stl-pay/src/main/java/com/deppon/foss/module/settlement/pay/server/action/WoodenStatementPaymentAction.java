package com.deppon.foss.module.settlement.pay.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IWoodenStatementPaymentService;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillPaymentVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;


public class WoodenStatementPaymentAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(WoodenStatementPaymentAction.class);
	
	/**
	 * 注入付款单vo
	 */
	private BillPaymentVo vo = new BillPaymentVo();
	
	private IWoodenStatementPaymentService woodenStatementPaymentService;
	
	public String woodenToPayment(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if(vo.getPaymentEntity().getNotes().getBytes().length > SettlementConstants.MAX_BILL_NO_SIZE) {
				throw new SettlementException("备注信息过长不可以超过1000个字节");
			}
			//调用录入接口
			String paymentNo = woodenStatementPaymentService.woodenToPayment(vo.getPaymentEntity(), 
					vo.getStatementBillNo(), currentInfo, vo.getFactoring());
			//设置返回付款单号
			vo.getPaymentEntity().setPaymentNo(paymentNo);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getErrorCode(),e);
			return returnError(e);
		}
	}

	public BillPaymentVo getVo() {
		return vo;
	}

	public void setVo(BillPaymentVo vo) {
		this.vo = vo;
	}

	public void setWoodenStatementPaymentService(IWoodenStatementPaymentService woodenStatementPaymentService) {
		this.woodenStatementPaymentService = woodenStatementPaymentService;
	}
	
}
