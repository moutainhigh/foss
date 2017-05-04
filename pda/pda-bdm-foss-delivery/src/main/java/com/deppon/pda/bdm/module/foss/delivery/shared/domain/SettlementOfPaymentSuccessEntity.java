package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.List;
/**
 * 结清货款模块刷卡成功返回实体
 * @author 268974
 *
 */
public class SettlementOfPaymentSuccessEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 多条刷卡数据
	private List<GetPaymentSuccessEntitys> getPaymentSuccessEntitys;
	
	public List<GetPaymentSuccessEntitys> getGetPaymentSuccessEntitys() {
		return getPaymentSuccessEntitys;
	}
	public void setGetPaymentSuccessEntitys(
			List<GetPaymentSuccessEntitys> getPaymentSuccessEntitys) {
		this.getPaymentSuccessEntitys = getPaymentSuccessEntitys;
	}
	
}
