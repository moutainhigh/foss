package com.deppon.esb.pojo.domain.foss2sms;



import java.io.Serializable;
import java.util.List;

/**
 * The Class SmsResult.
 */
public class SmsResult implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3562270138853731877L;
	//错误原因
	/** The reason. */
	private String reason;
	//错误结果码
	/** The result code. */
	private String resultCode;
	//错误短信语音唯一标识
	/** The fail list. */
	private List<String> failList;

	/**
	 * Checks if is reason.
	 * 
	 * @return the string
	 */
	public String isReason() {
		return reason;
	}

	/**
	 * Sets the reason.
	 * 
	 * @param reason
	 *            the new reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Gets the result code.
	 * 
	 * @return the result code
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * Sets the result code.
	 * 
	 * @param resultCode
	 *            the new result code
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * Sets the fail list.
	 * 
	 * @param failList
	 *            the new fail list
	 */
	public void setFailList(List<String> failList) {
		this.failList = failList;
	}

	/**
	 * Gets the fail list.
	 * 
	 * @return the fail list
	 */
	public List<String> getFailList() {
		return failList;
	}

}
