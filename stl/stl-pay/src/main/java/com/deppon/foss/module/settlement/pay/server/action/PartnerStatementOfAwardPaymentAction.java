package com.deppon.foss.module.settlement.pay.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardPaymentService;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillPaymentVo;

/** 
 * 合伙人奖罚对账单管理--付款操作
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-24 下午4:23:02    
 */
public class PartnerStatementOfAwardPaymentAction extends AbstractAction{

	//定义序列号
	private static final long serialVersionUID = 1L;
	
	//声明日志
	private static final Logger logger = LoggerFactory.getLogger(PartnerStatementOfAwardPaymentAction.class);
	
	/**
	 * 注入付款单vo
	 */
	private BillPaymentVo vo = new BillPaymentVo();
	
	/**
	 * 注入合伙人奖罚付款单Service
	 */
	private IPartnerStatementOfAwardPaymentService partnerStatementOfAwardPaymentService;

	/**
	 * 合伙人奖罚对账单付款
	 */
	@JSON
	public String partnerOfAwardToPayment(){
		
		try {
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();			
			//判断备注长度
			if(vo.getPaymentEntity().getNotes().getBytes().length > 1000){
				throw new SettlementException("备注信息过长不可以超过1000个字节");
			}			
			//调用付款单Service
			String paymentNo = partnerStatementOfAwardPaymentService.partnerStatementOfAwardToPayment(vo.getPaymentEntity(), vo.getStatementBillNo(), currentInfo);			
			//封装返回参数，设置返回付款单号
			vo.getPaymentEntity().setPaymentNo(paymentNo);
			//正常返回
			return returnSuccess();
			//异常处理
		} catch (BusinessException e) {
			//记录异常日志
			logger.error(e.getErrorCode(),e);
			//返回异常
			return returnError(e);
		}
		
	}
		
	public BillPaymentVo getVo() {
		return vo;
	}

	public void setVo(BillPaymentVo vo) {
		this.vo = vo;
	}


	public void setPartnerStatementOfAwardPaymentService(
			IPartnerStatementOfAwardPaymentService partnerStatementOfAwardPaymentService) {
		this.partnerStatementOfAwardPaymentService = partnerStatementOfAwardPaymentService;
	}

	
	
	
	
}
