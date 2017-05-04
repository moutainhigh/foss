package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class ShortFieldMapException extends BusinessException{

	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = -2850937129563098103L;
	
	/**
	 * <p>抛出异常信息的方法</p>
	 * @author 232607 
	 * @date 2015-4-1 下午5:12:16
	 * @param msg
	 */
	public ShortFieldMapException(String msg) {
		super(msg);
	}
	
}
