package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 
 * 提前预警线路异常类
 * @author 262036 - huangwei
 * @date 2015-8-19 下午6:14:54
 * @since
 * @version
 */
public class EarlyWarningException extends BusinessException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 937327213092392123L;

	public EarlyWarningException(String errCode){
		super();
		super.errCode = errCode;
	}
	public EarlyWarningException(String code,String msg){
		super(code,msg);
	}

}
