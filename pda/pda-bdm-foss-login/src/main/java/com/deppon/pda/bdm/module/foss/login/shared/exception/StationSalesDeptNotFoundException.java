package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;

/**
 * 
 * TODO(始发驻地营业部不存在异常)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2014-3-3 上午11:19:03,content:TODO </p>
 * @author 高佳
 * @date 2014-3-3 上午11:19:03
 * @since
 * @version
 */
public class StationSalesDeptNotFoundException extends PdaBusiException{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public StationSalesDeptNotFoundException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public StationSalesDeptNotFoundException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_STATION_NOTFOUND_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "未找到始发驻地营业部!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
