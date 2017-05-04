package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 用来处理号段管理操作的异常
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 上午10:56:19
 */
public class WayBillNoSectionException extends BusinessException{
	
    /**
     * Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public WayBillNoSectionException(String errorCode){
		super();
		super.errCode = errorCode;
	}
	
	public WayBillNoSectionException(String code, String msg){
		super(code, msg);
	}
	
}
