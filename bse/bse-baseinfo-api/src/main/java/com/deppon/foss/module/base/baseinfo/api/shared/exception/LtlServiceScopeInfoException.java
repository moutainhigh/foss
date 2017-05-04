package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 零担服务范围信息异常类
 * 
 * @author 200664
 * @date 2014-8-22 上午8:20:12
 * @since
 * @version
 */
public class LtlServiceScopeInfoException extends BusinessException{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4580431442425605532L;
	
	public LtlServiceScopeInfoException(String errCode){
		super();
		super.errCode = errCode;
	}
	public LtlServiceScopeInfoException(String code,String msg){
		super(code,msg);
	}

}
