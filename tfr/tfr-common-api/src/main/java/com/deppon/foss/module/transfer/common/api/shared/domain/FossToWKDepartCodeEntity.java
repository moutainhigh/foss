package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @description 悟空系统返回数据封装类
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月27日 下午5:56:54
 */
public class FossToWKDepartCodeEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//异常信息
	private String errMsg;
	
	//状态
	private String statusCode;

	//返回参数
	private List<String> destinationList;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public List<String> getDestinationList() {
		return destinationList;
	}

	public void setDestinationList(List<String> destinationList) {
		this.destinationList = destinationList;
	}

}
