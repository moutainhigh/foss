package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * @author 李鹏飞
 * @version V1.0
 * @Description 待叉区异常类
 * @Time 2014-4-25
 */
public class WaitForkAreaException extends BusinessException {
	public static final String WAITFORKAREA_CODE_EXIST="foss.bse.baseinfo.waitForkArea.waitForkAreaCodeExist";
	public static final String WAITFORKAREA_NOT_EXIST="foss.bse.baseinfo.waitForkArea.waitForkAreaNotExist";
	/**
	 * 
	 */
	private static final long serialVersionUID = 9216540195769162541L;
	public WaitForkAreaException(String errorCode){
		super();
		super.errCode = errCode;
	}
	public WaitForkAreaException(String code,String msg){
		super(code,msg);	
	}
}
