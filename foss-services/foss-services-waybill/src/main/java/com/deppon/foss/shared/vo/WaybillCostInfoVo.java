package com.deppon.foss.shared.vo;

import java.math.BigDecimal;

public class WaybillCostInfoVo {
	
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:费用类型：
				费用名称		费用代码		备注
				运费			FRT	
				保费			BF	
				代收货款		HK	
				送货费		SH	
				接货费		JH	
				签收回单		QS	
				仓储费		CCF	
				其他费用		QT			其他费用中包括-综合服务费，燃油附加费等等
				包装费用		BZ	
				综合信息费	ZHXX		所属增值服务中其他费用
				燃油附加费	RYFJ		所属增值服务中其他费用
				中转费		ZZ			所属增值服务中其他费用
				接货差额补差	JHCEBC		所属增值服务中其他费用
				电子优惠券	DZYHQ		所属增值服务中其他费用
      */
    private String costType;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:费用名称
      */
    private String costName;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:费用金额
      */
    private BigDecimal costMoney;

	/**
	 * @return the costType
	 */
	public String getCostType() {
		return costType;
	}

	/**
	 * @param costType the costType to set
	 */
	public void setCostType(String costType) {
		this.costType = costType;
	}

	/**
	 * @return the costName
	 */
	public String getCostName() {
		return costName;
	}

	/**
	 * @param costName the costName to set
	 */
	public void setCostName(String costName) {
		this.costName = costName;
	}

	/**
	 * @return the costMoney
	 */
	public BigDecimal getCostMoney() {
		return costMoney;
	}

	/**
	 * @param costMoney the costMoney to set
	 */
	public void setCostMoney(BigDecimal costMoney) {
		this.costMoney = costMoney;
	}
    
    
}
