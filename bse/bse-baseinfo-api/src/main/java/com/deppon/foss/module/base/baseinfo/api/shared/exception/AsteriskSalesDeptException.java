package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 加星标营业部信息异常类
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-5-4 上午9:58:12
 * @since
 * @version
 */
public class AsteriskSalesDeptException extends BusinessException{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4580431442425605532L;
	
	public AsteriskSalesDeptException(String errCode){
		super();
		super.errCode = errCode;
	}
	public AsteriskSalesDeptException(String code,String msg){
		super(code,msg);
	}

}
