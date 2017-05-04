package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 用来处理“代收延迟扣款”业务操作异常类类
 * @author 308861 
 * @date 2016-10-31 上午10:14:09
 * @since
 * @version
 */
public class DelayDeduRecordException extends BusinessException{

	/**
	 *序列化
	 */
	private static final long serialVersionUID = 8107250822486742725L;
	
	public DelayDeduRecordException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public DelayDeduRecordException(String msg) {
	super(msg);
    }

}
