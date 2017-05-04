package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 
 * 交单管理 异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:189284,date:2015-4-20 下午3:55:28,content:TODO </p>
 * @author 189284 
 * @date 2015-4-20 下午3:55:28
 * @since
 * @version
 */
public class NewbirdInfoException extends BusinessException {
	 /**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	 
	private static final long serialVersionUID = -8372339694836134644L;

	public NewbirdInfoException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	    }

	    public NewbirdInfoException(String code, String msg) {
		super(code, msg);
	    }

	    public NewbirdInfoException(String msg) {
		super(msg);
	    }
}
