package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class ExpressSalesAgentMapException extends BusinessException{
	
	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = -6065015073679230825L;
	/**
	 * 传入参数为空
	 */
	public static final String EXPRESSSALESAGENTMAP_PARMS_NULL="传入参数为空";
	/**
	 * 已经存在相同的虚拟营业部，请勿重复添加
	 */
	public static final String EXPRESSSALESAGENTMAP_SALES_REPEAT="已经存在相同的虚拟营业部，请勿重复添加";
	/**
	 * <p>抛出异常信息的方法</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午4:33:27
	 * @param msg
	 * @see
	 */
	public ExpressSalesAgentMapException(String msg) {
		super(msg);
	}
	
	
}
