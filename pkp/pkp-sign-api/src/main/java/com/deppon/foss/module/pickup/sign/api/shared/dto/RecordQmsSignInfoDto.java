package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @clasaName:com.deppon.foss.module.pickup.sign.api.shared.dto.RecordQmsSignInfoDto
 * @author: foss-bieyexiong
 * @description: 手动上报qms时，foss返回的签收信息 
 * @date:2015年5月4日 下午16:05:10
 */
public class RecordQmsSignInfoDto implements Serializable{
	
	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  签收类型(签收情况)
	 */
	private String signSituation;
	
	/**
	 *  签收时间
	 */
	private Date signTime;
	
	/**
	 * 是否全部签收
	 */
	private String isSignStatusAll;
	
	/**
	 * 是否正常签收
	 */
	private String isNormalSign;
	
	/**
	 * 理赔金额
	 */
	private BigDecimal claimAmount;

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getIsSignStatusAll() {
		return isSignStatusAll;
	}

	public void setIsSignStatusAll(String isSignStatusAll) {
		this.isSignStatusAll = isSignStatusAll;
	}

	public String getIsNormalSign() {
		return isNormalSign;
	}

	public void setIsNormalSign(String isNormalSign) {
		this.isNormalSign = isNormalSign;
	}

	public BigDecimal getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(BigDecimal claimAmount) {
		this.claimAmount = claimAmount;
	}

}
