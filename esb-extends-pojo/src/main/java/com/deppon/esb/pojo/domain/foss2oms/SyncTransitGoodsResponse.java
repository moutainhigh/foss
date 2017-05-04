package com.deppon.esb.pojo.domain.foss2oms;

import java.io.Serializable;

/**
 * FOSS转货订单信息状态推送给OMS response对象
 * @author 332153 周猛
 * @date 2016年11月21日11:06:22
 * @version 1.0
 */
public class SyncTransitGoodsResponse  implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 157859329809883969L;
	//是否成功   Y N
	private String isSuccess;
	//异常信息
	private String exceptionMsg;

	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
}
