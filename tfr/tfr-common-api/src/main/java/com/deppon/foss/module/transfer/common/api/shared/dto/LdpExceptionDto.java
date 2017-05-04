package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.Date;

/**
 * 落地配公司调用Foss系统异常实体
 * @author ibm-liuzhaowei
 * @date 2013-08-05 下午7:15:36
 */
public class LdpExceptionDto {
	
	/**
	 * 创建时间
	 */
	public Date createdTime;
	/**
	 * 异常消息
	 */
	public String message;
	/**
	 * 异常详细信息
	 */
	public String detailedInfo;
	/**
	 *<pre>
	 *异常类型
	 * SYS：系统异常，是与业务无关的，如编码错误、链接错误等
     * APP：业务异常，是与业务有关的，如落地配代理编码不存在等
	 *</pre>
	 * 
	 * @return
	 */
	public String exceptionType;
	
	/**
	 * <pre>
	 * 异常编码
	 * 针对每一种类型的异常所做的编码。
	 * 其中系统异常的编码格式为:Sxxxxxx，S为SYS, xxxxxx是六位数字；
	 * 业务异常的编码格式为Axxxxxx，A为APP，同样xxxxxx是六位数字。
	 * 
	 * </pre>
	 */
	public String exceptionCode;
	
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetailedInfo() {
		return detailedInfo;
	}
	public void setDetailedInfo(String detailedInfo) {
		this.detailedInfo = detailedInfo;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
}
