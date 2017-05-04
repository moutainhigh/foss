package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file AmountInfoEntity.java 
 * @description 优惠劵费用明细
 * @author ChenLiang
 * @created 2012-12-31 下午2:59:47    
 * @version 1.0
 */
public class AmountInfoEntity implements Serializable {

	private static final long serialVersionUID = -6345156686036242045L;

	/**
	 * 计价条目编码
	 */
	private String code;
	/**
	 * 计价金额
	 */
	private double value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
