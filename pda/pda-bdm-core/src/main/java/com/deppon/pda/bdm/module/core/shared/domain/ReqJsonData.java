package com.deppon.pda.bdm.module.core.shared.domain;


/**
 * 请求JSON数据
 * @author wanghongling
 * @date 2012-09-07
 * @version 1.0
 *
 */
public class ReqJsonData {
	/**
	 * 包头
	 */
	private String pdaInfo;
	/**
	 * 包体
	 */
	private String body;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPdaInfo() {
		return pdaInfo;
	}
	public void setPdaInfo(String pdaInfo) {
		this.pdaInfo = pdaInfo;
	}
}
