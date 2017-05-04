package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * FOSS调用CUBC结清、反结清接口响应后返回的参数实体
 * 到付运费转临欠/月结
 * @author 378375
 * 
 */
@SuppressWarnings("serial")
public class CUBCResponseDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	public static final long serialVersionUID = 378375002L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 操作结果
	 * Y:表示成功；N:表示失败
	 */
	private String resultMark;

	/**
	 * 异常信息
	 * 非必须字段
	 */
	private String message;
	
	/**
	 * 结清状态(是否全部结清)
	 */
	private String settleStatus = "N";

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getResultMark() {
		return resultMark;
	}

	public void setResultMark(String resultMark) {
		this.resultMark = resultMark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
    
}
