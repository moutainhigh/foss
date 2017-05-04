package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 用来处理“一般纳税人信息”业务操作异常类类
 * @author 308861
 *
 */
public class OrgGeneralTaxpayerException extends BusinessException{

	/**
	 *序列化
	 */
	private static final long serialVersionUID = 8107250822486742725L;
	
	public OrgGeneralTaxpayerException(String crmId, String msg, Throwable cause) {
	super(crmId, msg, cause);
    }

    public OrgGeneralTaxpayerException(String crmId,String operation, String msg) {
	super(crmId,operation, msg);
    }
    public OrgGeneralTaxpayerException(String msg) {
	super(msg);
    }

}
