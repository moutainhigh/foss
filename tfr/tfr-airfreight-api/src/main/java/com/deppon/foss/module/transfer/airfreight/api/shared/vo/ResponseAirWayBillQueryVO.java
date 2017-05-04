package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;

/**
 * @description 返回根据运单号查询航空制单的制单部门信息实体
 * @version 1.0
 * @author 106162
 * @date   2016-05-24
 * @see  com.deppon.foss.module.transfer.airfreight.api.shared.vo#ResponseAirWayBillQueryVO
 */
public class ResponseAirWayBillQueryVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 成功标示
	 */
	private String success;
	
	/**
	 * 返回信息,包含成功返回的json信息
	 */
	private Object returnInfo;

	/**
	 * 成功标示
	 */
	public static final String SUCCESSFLAG="1";
	/**
	 * 成功提示信息
	 */
	public static final String SUCCESSINFO="成功，很给力!";
	/**
	 * 失败标示
	 */
	public static final String FAILFLAG="0";
	/**
	 * 失败提示信息
	 */
	public static final String FAILINFO="失败,输入的运单号是否正确或者数据库数据不全等原因,未能找到对应信息,请核实!";
	/**
	 * 异常标示
	 */
	public static final String EXCEPTIONFLAG="2";
	/**
	 * 异常提示信息
	 */
	public static final String EXCEPTIONINFO="失败,JSON数据格式、字节码或者网络原因,请核实!";
	
	/**
	 * 构造函数
	 * @param success
	 * @param returnInfo
	 */
	public ResponseAirWayBillQueryVO(String success, Object returnInfo) {
		super();
		this.success = success;
		this.returnInfo = returnInfo;
	}

	/**
	 * toString()
	 */
	@Override
	public String toString() {
		return "ResponseAirWayBillQueryVO [success=" + success
				+ ", returnInfo=" + returnInfo + "]";
	}

	public ResponseAirWayBillQueryVO() {
		super();
	}
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public Object getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(Object returnInfo) {
		this.returnInfo = returnInfo;
	}

	


}
