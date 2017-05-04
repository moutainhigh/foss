package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * 统计对账单明细的总条数
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-30 下午5:20:04
 */
public class StatementOfAccountDetailCountDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3201914646672805725L;

	/**
	 * 合计信息
	 */
	private int countNum;

	
	/**
	 * @return countNum
	 */
	public int getCountNum() {
		return countNum;
	}

	
	/**
	 * @param  countNum  
	 */
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

}
