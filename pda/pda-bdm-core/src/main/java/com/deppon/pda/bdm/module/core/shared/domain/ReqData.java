package com.deppon.pda.bdm.module.core.shared.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 请求数据
 * @author wanghongling
 * @date 2012-09-07
 * @version 1.0
 *
 */
@XmlRootElement(name="ReqData")
public class ReqData {
	/**
	 * 请求JSON数据
	 */
	private List<ReqJsonData> reqData;
	
	/**
	 * 是否手机
	 */
	private String isMobile;
	public List<ReqJsonData> getReqData() {
		return reqData;
	}

	public void setReqData(List<ReqJsonData> reqData) {
		this.reqData = reqData;
	}

	public String getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(String isMobile) {
		this.isMobile = isMobile;
	}
	
	
	
	
	
}
