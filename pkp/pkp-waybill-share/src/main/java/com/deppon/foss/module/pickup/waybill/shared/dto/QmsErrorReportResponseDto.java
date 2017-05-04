package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 自动上报QMS差错时的响应参数 dto
 * @author 231434-FOSS-bieyexiong
 * @date 2015-5-5 下午17:16:20
 */
public class QmsErrorReportResponseDto implements Serializable {

	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 差错编号(每个运单，对应的唯一差错编号)
	 */
	private String errorID;
	
	/**
	 * 消息编码(返回状态)
	 */
	private String handleCode;
	
	/**
	 * 消息内容(对返回状态的解释)
	 */
	private String message;
	
	/**
	 * 是否已经处理
	 */
	private String handle;
	
	/**
	 * 处理结果
	 */
	private Object result;

	public String getErrorID() {
		return errorID;
	}

	public void setErrorID(String errorID) {
		this.errorID = errorID;
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

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
