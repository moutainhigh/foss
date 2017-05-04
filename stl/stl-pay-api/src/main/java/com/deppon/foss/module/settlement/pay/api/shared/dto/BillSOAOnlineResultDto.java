package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;

/**
 * 
 * 网上支付，查询对账单结果DTO
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-29 下午2:51:26
 */
public class BillSOAOnlineResultDto extends StatementOfAccountEntity implements
		Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6639370918408545955L;

	/**
	 * 对账单所对应的运单应收单总数
	 */
	private int countDetailNum;

	/**
	 * @return countDetailNum
	 */
	public int getCountDetailNum() {
		return countDetailNum;
	}

	/**
	 * @param countDetailNum
	 */
	public void setCountDetailNum(int countDetailNum) {
		this.countDetailNum = countDetailNum;
	}

}
