package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.util.List;

public class GisResponseDto implements java.io.Serializable {

	/**  
	 * serialVersionUID
	 * @since JDK 1.6  
	 */
	private static final long serialVersionUID = 3453462410878851L;
	
	/**
	 * 一次请求的异常信息
	 */
	private String exceptionMSG;
	
	/**
	 * 运单匹配信息list
	 */
	private List<GisWaybillResponseDto> responses;
	
	
	/**
	 * 兼顾esb的报错西信息
	* @fields message
	* @author 14022-foss-songjie
	* @update 2015年7月30日 上午8:24:20
	* @version V1.0
	*/
	private String message;
	private String exceptionCode;
	private String exceptionType;
	private String createdTime;
	private String detailedInfo;
	
	
	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getDetailedInfo() {
		return detailedInfo;
	}

	public void setDetailedInfo(String detailedInfo) {
		this.detailedInfo = detailedInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**  
	 * exceptionMSG.  
	 *  
	 * @return  the exceptionMSG  
	 * @since   JDK 1.6  
	 */
	public String getExceptionMSG() {
		exceptionMSG = exceptionMSG+"||"+getMessage();
		return exceptionMSG;
	}

	/**  
	 * exceptionMSG.  
	 *  
	 * @param   exceptionMSG    the exceptionMSG to set  
	 * @since   JDK 1.6  
	 */
	public void setExceptionMSG(String exceptionMSG) {
		this.exceptionMSG = exceptionMSG;
	}

	/**  
	 * responses.  
	 *  
	 * @return  the responses  
	 * @since   JDK 1.6  
	 */
	public List<GisWaybillResponseDto> getResponses() {
		return responses;
	}

	/**  
	 * responses.  
	 *  
	 * @param   responses    the responses to set  
	 * @since   JDK 1.6  
	 */
	public void setResponses(List<GisWaybillResponseDto> responses) {
		this.responses = responses;
	}
	
}
