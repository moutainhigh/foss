/**   
 * @title SmsResult.java
 * @package com.deppon.ump.module.Interface.shared.domain
 * @description 
 * @author cbb   
 * @update 2012-10-20 下午5:43:42
 * @version V1.0   
 */

package com.deppon.foss.module.base.common.api.shared.domain;

import java.io.Serializable;

/**
 * 短信发送返回结果信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-6-8 下午4:00:17
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-6-8 下午4:00:17
 * @since
 * @version
 */
public class SendResultInfo implements Serializable {

	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 2139626220844221695L;

	/**
	 * 错误短信语音唯一标识
	 */
	private String smsSequence;

	/**
	 * 错误原因
	 */
	private String failReason;

	/**
	 * 错误结果
	 */
	private boolean result;

	public String getSmsSequence() {
		return smsSequence;
	}

	public void setSmsSequence(String smsSequence) {
		this.smsSequence = smsSequence;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
