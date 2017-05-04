package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: SettlementOfPaymentSuccessResult
 * @Description: TODO(查询对账单数据返回值)
 * @author &245955
 * @date 2016-3-10 上午8:53:25
 * 
 */
public class SettlementOfPaymentSuccessResult implements Serializable{
	private static final long serialVersionUID = 1L;
	//部分核销成功数据信息
	private List<SettlementOfPaymentResult> settlementOfPaymentResult;
	//错误运单号
	private List<String> errcoMessage;
	
	public List<SettlementOfPaymentResult> getSettlementOfPaymentResult() {
		return settlementOfPaymentResult;
	}
	public void setSettlementOfPaymentResult(
			List<SettlementOfPaymentResult> settlementOfPaymentResult) {
		this.settlementOfPaymentResult = settlementOfPaymentResult;
	}
	public List<String> getErrcoMessage() {
		return errcoMessage;
	}
	public void setErrcoMessage(List<String> errcoMessage) {
		this.errcoMessage = errcoMessage;
	}
}
