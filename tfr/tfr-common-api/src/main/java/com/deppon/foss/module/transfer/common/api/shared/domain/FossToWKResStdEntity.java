package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

/**
 * @description 悟空系统返回数据封装类
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月27日 下午5:56:54
 */
public class FossToWKResStdEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//异常信息
	private String exMsg;
	
	//状态
	private String status;

	//返回参数
	private Object data;

	//自有时效
	private String ownLimitation;
	
	//外请时效
	private String outSourceLimitation;
	
	/**
	 * @description 获取 异常信息
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午5:53:39
	 */
	public String getExMsg() {
		return exMsg;
	}

	/**
	 * @description 设置 异常信息
	 * @param exMsg
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午5:54:43
	 */
	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}

	/**
	 * @description 获取 状态
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午5:55:14
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @description 设置 状态
	 * @param status
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午5:55:38
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @description 获取返回数据
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午5:56:00
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @description 设置 返回数据
	 * @param data
	 * @version 1.0
	 * @update 2016年4月27日 下午5:56:27
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public String getOwnLimitation() {
		return ownLimitation;
	}

	public void setOwnLimitation(String ownLimitation) {
		this.ownLimitation = ownLimitation;
	}

	public String getOutSourceLimitation() {
		return outSourceLimitation;
	}

	public void setOutSourceLimitation(String outSourceLimitation) {
		this.outSourceLimitation = outSourceLimitation;
	}

	
}
