package com.deppon.foss.module.settlement.pay.server.service.impl;

//import com.deppon.tps.module.fundsClearance.shared.domain.foss.RepaymentDto;

/** 
 * VTS返回结果信息（VTS提供）
 * @author  395982 zhengyating
 * @date 2017-01-12  
 */
public class FossToVtsTail {

	/**
	 * 成功失败标识
	 */
	private boolean success;
	
	/**
	 * 消息
	 */
	private String msg;

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
}
