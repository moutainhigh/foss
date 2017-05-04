package com.deppon.pda.bdm.module.core.shared.exception.sys.common;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaSysException;



/**
 * 无效参数异常
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class ArgumentInvalidException extends PdaSysException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1142325066189640314L;
	
	/**
	 * 无效参数名称
	 */
	private String invalidArgName;
	
	private INVALID_ARG_TYPE invalidArgType;
	
	public static enum INVALID_ARG_TYPE{
		NOT_NULL,NOT_EMPTY,HAS_TEXT,IS_TRUE,IS_FALSE,NOT_EMPTY_ELEMENTS,IS_POSITIVE,IS_ZERO,IS_NEGATIVE
	}
	
	/**
	 * 构造方法
	 * @param invalidArgName 无效参数
	 */
	public ArgumentInvalidException(String invalidArgName, INVALID_ARG_TYPE invalidArgType) {
		super();
		this.invalidArgName = invalidArgName;
		this.invalidArgType = invalidArgType;
	}
	
//	/**
//	 * 构造方法
//	 * @param invalidArgName 无效参数
//	 * @param cause 原因
//	 */
//	public ArgumentInvalidException(String invalidArgName, INVALID_ARG_TYPE invalidArgType, Throwable cause) {
//		super(cause);
//		this.invalidArgName = invalidArgName;
//		this.invalidArgType = invalidArgType;
//	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS_INVALID_ARG;
	}
	
	protected String getNativeMessageKey(){
		switch (invalidArgType) {
		case NOT_NULL:
			return null; 
		case NOT_EMPTY:
			return null; 
		case HAS_TEXT:
			return null; 
		case IS_TRUE:
			return null; 
		case IS_FALSE:
			return null; 
		case NOT_EMPTY_ELEMENTS:
			return null; 
		case IS_POSITIVE:
			return null;
		case IS_ZERO:
			return null;
		case IS_NEGATIVE:
			return null;

		default:
			return null; 
		}
	}

	@Override
	public String getDefaultMessage() {
		switch (invalidArgType) {
		case NOT_NULL:
			return "无效参数,参数[%s]不能为NULL!"; 
		case NOT_EMPTY:
			return "无效参数,参数[%s]不能为空!"; 
		case HAS_TEXT:
			return "无效参数,参数[%s]不能为空或为空字符串!"; 
		case IS_TRUE:
			return "无效参数,参数[%s]应为TRUE!"; 
		case IS_FALSE:
			return "无效参数,参数[%s]应为FALSE"; 
		case NOT_EMPTY_ELEMENTS:
			return "无效参数,参数[%s]元素不能为空!"; 
		case IS_POSITIVE:
			return "无效参数,参数[%s]应大于0!"; 
		case IS_ZERO:
			return "无效参数,参数[%s]应等于0!"; 
		case IS_NEGATIVE:
			return "无效参数,参数[%s]应为负数!"; 

		default:
			return "无效参数,参数名称：%s";
		}
	}
	
	@Override
	public Object[] getErrorArguments(){
		Object[] arg = new Object[]{
			this.getInvalidArgName()
		};
		return arg;
	}
	
	public String getInvalidArgName(){
		return invalidArgName;
	}

	public INVALID_ARG_TYPE getInvalidArgType() {
		return invalidArgType;
	}

}
