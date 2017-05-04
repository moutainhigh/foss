package com.deppon.foss.module.settlement.common.api.shared.domain;


/**
 * 
 * 类描述：	WebService接口请求参数实体
 * 创建人：	221464-ZOUYONG
 * 创建时间：	2014-12-19 上午10:12:12
 *
 */
public class RequestParameterEntity {
	
	// 公共实体,可为数组类型
	private Object requestEntity;
	
	// 接口类型
	private String type;
	


	public Object getRequestEntity() {
		
		return requestEntity;
	}

	public void setRequestEntity(Object requestEntity) {
		this.requestEntity = requestEntity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RequestParameterEntity [requestEntity=" + requestEntity
				+ ", type=" + type + "]";
	}
	
}