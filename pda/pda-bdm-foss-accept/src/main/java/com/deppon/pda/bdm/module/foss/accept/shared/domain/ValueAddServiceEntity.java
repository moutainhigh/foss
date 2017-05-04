package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

import com.deppon.pda.bdm.module.core.shared.domain.DomainEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file ValueAddServiceEntity.java
 * @description 增值服务项
 * @author ChenLiang
 * @created 2012-12-31 下午3:05:05
 * @version 1.0
 */
public class ValueAddServiceEntity extends DomainEntity implements Serializable {

	private static final long serialVersionUID = -8384833270457327465L;

	/**
	 * 运单ID
	 */
	private String billingID;

	/**
	 * 增值服务code
	 */
	private String code;

	/**
	 * 增值服务价格
	 */
	private double amount;
	
	/**
     * 增值服务类型
     */
    private String subType;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBillingID() {
		return billingID;
	}

	public void setBillingID(String billingID) {
		this.billingID = billingID;
	}

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

}
