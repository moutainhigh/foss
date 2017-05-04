package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
/**
 * gis查询目的站信息服务接口响应的实体信息
 * @author 321993 zhangdianhao
 * @date 2017-03-16 下午13:53:50
 * @version 1.0
 */
import java.util.List;
public class GisServiceResponse implements Serializable {
	private List<HisSegMatchResponse> responses;
	private String exceptionMSG;
	
	public List<HisSegMatchResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<HisSegMatchResponse> responses) {
		this.responses = responses;
	}
	public String getExceptionMSG() {
		return exceptionMSG;
	}
	public void setExceptionMSG(String exceptionMSG) {
		this.exceptionMSG = exceptionMSG;
	}
	
}
