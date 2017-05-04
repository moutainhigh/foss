package com.deppon.foss.dubbo.crm.api.define.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 中转默认异常
 * @author foss-wuyingjie
 * @date 2012-10-26 下午4:31:05
 */
public class TfrBusinessException extends BusinessException{

	private static final long serialVersionUID = 8702128402954184055L;
	
	public TfrBusinessException() {
		super();
	}

	public TfrBusinessException(String errCode, String msg, String natvieMsg, Throwable cause) {
		super(errCode, msg, natvieMsg, cause);
	}

	public TfrBusinessException(String errCode, String msg, String natvieMsg) {
		super(errCode, msg, natvieMsg);
	}

	public TfrBusinessException(String errCode, String msg, Throwable cause) {
		super(errCode, msg, cause);
	}

	public TfrBusinessException(String errCode, String msg) {
		super(errCode, msg);
	}

	public TfrBusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public TfrBusinessException(String msg) {
		super(msg);
		this.errCode = msg;
	}
	
	public TfrBusinessException(String code,Object... args) {
		super(code,args);
	}
	
	public TfrBusinessException(String code,String msg, Object... args) {
		super(code,msg,args);
	}
}