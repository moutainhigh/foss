package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @ClassName: PrepaymentEntity 
 * @Description: TODO(预付款模块实体) 
 * @author &268974  wangzhili
 * @date 2016-1-28 上午8:51:13 
 *
 */
public class PrepaymentEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//多条预存款刷卡数据
	private List<AccountStatementEntitys> accountStatementEntitys;

	public List<AccountStatementEntitys> getAccountStatementEntitys() {
		return accountStatementEntitys;
	}

	public void setAccountStatementEntitys(
			List<AccountStatementEntitys> accountStatementEntitys) {
		this.accountStatementEntitys = accountStatementEntitys;
	}


	
}
