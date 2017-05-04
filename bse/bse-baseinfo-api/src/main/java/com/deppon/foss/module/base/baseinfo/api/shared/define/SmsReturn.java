
/**   
 * @title SmsResult.java
 * @package com.deppon.ump.module.Interface.shared.domain
 * @description 
 * @author cbb   
 * @update 2012-10-20 下午5:43:42
 * @version V1.0   
 */
 
package com.deppon.foss.module.base.baseinfo.api.shared.define;

import java.io.Serializable;
import java.util.List;


/**
 * @description 
 * @version 1.0
 * @author cbb
 * @update 2012-10-20 下午5:43:42 
 */

public class SmsReturn implements Serializable{
	
	
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 2139626220844221695L;
	//错误短信语音唯一标识
	private List<String> failList;
	//错误原因
	private String reason;
	//错误结果码
	private String resultCode;
	
	public List<String> getFailList() {
		return failList;
	}
	
	public void setFailList(List<String> failList) {
		this.failList = failList;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
