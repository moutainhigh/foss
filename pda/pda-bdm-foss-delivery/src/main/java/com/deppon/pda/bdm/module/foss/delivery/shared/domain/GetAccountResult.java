package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: GetAccountResult 
 * @Description: TODO(对账单还款返回值) 
 * @author &268974  wangzhili
 * @date 2016-3-24 上午9:56:34 
 *
 */
public class GetAccountResult implements Serializable{
	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//部门核销成功数据
	private List<AccountStatementResult> accountStatementResult;
	//核销失败的单号及原因
	private List<String> errcoMessage;
	public List<AccountStatementResult> getAccountStatementResult() {
		return accountStatementResult;
	}
	public void setAccountStatementResult(
			List<AccountStatementResult> accountStatementResult) {
		this.accountStatementResult = accountStatementResult;
	}
	public List<String> getErrcoMessage() {
		return errcoMessage;
	}
	public void setErrcoMessage(List<String> errcoMessage) {
		this.errcoMessage = errcoMessage;
	}
	
}
