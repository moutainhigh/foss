package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class AgentDeliveryFeeSchemeException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4397814056622992208L;
    public static final String AGENTDELIVERYFEESCHEME_SCHEME_EXIST = "方案已存在!";
    
    public AgentDeliveryFeeSchemeException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public AgentDeliveryFeeSchemeException(String code,String msg){
	super(code,msg);
    }
}
