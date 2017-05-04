/**
 * 
 */
package com.deppon.foss.module.login.shared.domain;

import java.io.Serializable;

/**
 * gui版本检查和token返回
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-8 上午11:06:43, </p>
 * @author shixiaowei
 * @date 2013-3-8 上午11:06:43
 * @since
 * @version
 */
public class TokenDto  implements Serializable{

	/**
	  * serialVersionUID:类唯一标识
	  *
	*/
	private static final long serialVersionUID = 6586635903305099436L;
	/**
	 * token
	 */
	private String tokenParam; 
	/**
	 * gui版本
	 */
	private String clientVersion;
	/**
	 * @return the tokenParam
	 */
	public String getTokenParam() {
		return tokenParam;
	}
	/**
	 * @param tokenParam the tokenParam to set
	 */
	public void setTokenParam(String tokenParam) {
		this.tokenParam = tokenParam;
	}
	/**
	 * @return the clientVersion
	 */
	public String getClientVersion() {
		return clientVersion;
	}
	/**
	 * @param clientVersion the clientVersion to set
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	
}
