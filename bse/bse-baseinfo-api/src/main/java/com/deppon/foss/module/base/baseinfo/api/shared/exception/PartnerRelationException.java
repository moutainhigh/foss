package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 用来处理“网点映射”业务操作异常类类
 * @author 308861
 *
 */
public class PartnerRelationException extends BusinessException{

	/**
	 *序列化
	 */
	private static final long serialVersionUID = 8107250822486742725L;
	
	public PartnerRelationException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public PartnerRelationException(String msg) {
	super(msg);
    }

}
