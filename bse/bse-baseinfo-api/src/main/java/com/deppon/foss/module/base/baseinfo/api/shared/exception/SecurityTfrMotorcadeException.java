package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * TODO(SecurityTfrMotorcade的异常处理类)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午10:28:57
 * @since
 * @version
 */
public class SecurityTfrMotorcadeException extends BusinessException {
	
	/**
     * 异常类
     */
    

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = -6883030506401177011L;

	
    public SecurityTfrMotorcadeException(String errCode) {
	    
    	super();
	    super.errCode = errCode;
    }

    public SecurityTfrMotorcadeException(String code, String msg) {

	    super(code, msg);
    }

}
