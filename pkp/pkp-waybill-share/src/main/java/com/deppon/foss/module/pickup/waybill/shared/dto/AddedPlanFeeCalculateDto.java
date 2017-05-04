package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 加收费计算dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-351326-xingjun,date:2016-9-3 上午8:28:59,content:TODO </p>
 * @author Foss-351326-xingjun 
 * @date 2016-9-3 上午8:28:59
 * @since
 * @version
 */
public class AddedPlanFeeCalculateDto implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	//加收费
	private BigDecimal addedFee;
	
	//加收方案费率
	private BigDecimal feeRate;

	
	public BigDecimal getAddedFee() {
		return addedFee;
	}

	public void setAddedFee(BigDecimal addedFee) {
		this.addedFee = addedFee;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	
	
}
