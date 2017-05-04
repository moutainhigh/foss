package com.deppon.foss.module.pickup.sign.api.shared.request;

import java.io.Serializable;

/**
 * 保证金余额查询
 * @author 239284
 * @date 2016-1-14
 * @since 2016-1-1
 * @version 1.0.0 
 * @remark
 * @copyright Copyright (c) 2015 Deppon
 */
public class BondBalanceQueryRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269097133323691542L;
	
	/**
	 * 合伙人编码
	 */
	private String partnerOrgCode;


	/**
	 * @return the partnerOrgCode
	 */
	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}


	/**
	 * @param partnerOrgCode the partnerOrgCode to set
	 */
	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}
	

}
