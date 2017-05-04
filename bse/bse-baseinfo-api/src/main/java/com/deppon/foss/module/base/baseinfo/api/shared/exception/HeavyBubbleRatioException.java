package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 重泡比异常类
 * @author 218392 张永雪
 *
 * 2014-11-21下午2:21:49
 */
public class HeavyBubbleRatioException extends BusinessException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public HeavyBubbleRatioException(String errCode){
		super();
		super.errCode = errCode;
	}
	
	public HeavyBubbleRatioException(String code,String msg){
		super(code,msg);
	}

}
