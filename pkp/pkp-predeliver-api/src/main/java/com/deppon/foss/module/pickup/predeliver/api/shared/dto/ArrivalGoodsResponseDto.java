package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/**
 * 家装货物到达信息响应实体类
 * @author 243921-FOSS-zhangtingting
 * @date 2015-09-14 下午14:56：35
 * @
 */
public class ArrivalGoodsResponseDto implements Serializable {
	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 消息编码(返回状态)
	 */
	private String handleCode;
	
	/**
	 * 消息内容(对返回状态的解释)
	 */
	private String message;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getHandleCode() {
		return handleCode;
	}

	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
