package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 呼叫中心催运单信息异常类
 * @author 132599-foss-shenweihua
 * @date 2014-07-22 上午9:59:53
 * @since
 * @version
 */
public class CallCenterWaybillInfoException extends BusinessException{

/**
 * serialVersionUID
 */
 private static final long serialVersionUID = 1L;
	
 public static final String CALLCENTER_NULL_ERROR_CODE = "foss.bse.bse-baseinfo.CallCenterNullException";
 
 public CallCenterWaybillInfoException(String errCode) {
		super();
		super.errCode = errCode;
	    }

	    public CallCenterWaybillInfoException(String code, String msg) {

		super(code, msg);
	    }

}
