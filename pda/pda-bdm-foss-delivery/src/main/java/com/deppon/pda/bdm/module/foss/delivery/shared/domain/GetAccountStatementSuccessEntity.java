package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 对账单模块刷卡成功数据
 * @author 268974
 *
 */
public class GetAccountStatementSuccessEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//多条刷卡数据
	private List<AccountStatementEntitys> accountStatementEntitys;
	public List<AccountStatementEntitys> getAccountStatementEntitys() {
		return accountStatementEntitys;
	}
	public void setAccountStatementEntitys(
			List<AccountStatementEntitys> accountStatementEntitys) {
		this.accountStatementEntitys = accountStatementEntitys;
	}
	

}
