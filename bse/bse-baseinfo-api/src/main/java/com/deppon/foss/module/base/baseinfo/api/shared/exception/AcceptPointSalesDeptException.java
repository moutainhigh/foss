package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 
 * @author 132599 ShenWeiHua
 *
 * @date 2015-4-16 下午4:48:04
 */
public class AcceptPointSalesDeptException extends BusinessException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	public AcceptPointSalesDeptException(String errCode){
		super();
		super.errCode = errCode;
	}
	public AcceptPointSalesDeptException(String code,String msg){
		super(code,msg);
	}
}
