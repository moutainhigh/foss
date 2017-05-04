/**
 * 
 */
package com.deppon.foss.module.pickup.sign.api.shared.domain;

/**
 * PTP合伙人代收货款扣款-反结清(退款)-响应Response实体
 * @author 239284
 *
 */
public class PtpCodReversDeductRespEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3289038428720056407L;

	/**
	 * 消息
	 */
	private String message;
	
	/**
	 * 是否成功
	 */
	private boolean isSuccess;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
