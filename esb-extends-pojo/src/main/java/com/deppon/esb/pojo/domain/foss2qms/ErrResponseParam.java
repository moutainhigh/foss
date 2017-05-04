package com.deppon.esb.pojo.domain.foss2qms;

import java.io.Serializable;

public class ErrResponseParam implements Serializable {

	private static final long serialVersionUID = 8934490762452821237L;

	//差错编号
	private String errorID;
	//调用结果
	private String handleCode;
	//错误信息
	private String message;
	//是否已处理
	private String handle;
	//处理结果
	private ErrorResultEntity result;
	
	
	public ErrorResultEntity getResult() {
		return result;
	}
	public void setResult(ErrorResultEntity result) {
		this.result = result;
	}
	/**
	 * errorID <p>getter method</p>
	 * @author 150976
	 * @return  the errorID
	 */
	public String getErrorID() {
		return errorID;
	}
	/**
	 * errorID <p>setter method</p>
	 * @author 150976
	 * @param errorID the errorID to set
	 */
	public void setErrorID(String errorID) {
		this.errorID = errorID;
	}
	/**
	 * handleCode <p>getter method</p>
	 * @author 150976
	 * @return  the handleCode
	 */
	public String getHandleCode() {
		return handleCode;
	}
	/**
	 * handleCode <p>setter method</p>
	 * @author 150976
	 * @param handleCode the handleCode to set
	 */
	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}
	/**
	 * message <p>getter method</p>
	 * @author 150976
	 * @return  the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * message <p>setter method</p>
	 * @author 150976
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * handle <p>getter method</p>
	 * @author 150976
	 * @return  the handle
	 */
	public String getHandle() {
		return handle;
	}
	/**
	 * handle <p>setter method</p>
	 * @author 150976
	 * @param handle the handle to set
	 */
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	
}
