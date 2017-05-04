package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * MAC地址白名单 异常类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:132599-foss-shenweihua,date:2013-4-25 下午4:41:05 </p>
 * @author 132599-foss-shenweihua
 * @date 2013-4-25 下午4:41:05
 * @since
 * @version
 */
public class MacWhiteException extends BusinessException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2219322776787460457L;

	public MacWhiteException(String errCode){
		super();
		super.errCode = errCode;
	}
	public MacWhiteException(String code,String msg){
		super(code,msg);
	}
	

	

}
