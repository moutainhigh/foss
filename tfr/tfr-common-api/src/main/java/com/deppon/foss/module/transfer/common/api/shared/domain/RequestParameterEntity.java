package com.deppon.foss.module.transfer.common.api.shared.domain;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 类描述：	同步FOSS车辆发车到达指令入参实体
 * 创建人：	221464-ZOUYONG
 * 创建时间：	2014-12-23 下午1:32:55
 *
 */
@XmlRootElement(name="RequestParameterEntity")
public class RequestParameterEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7354429438336724231L;

	// 调用的方法名称或请求类型
	private String type;
	
	// 调用参数实体
	private Object requestEntity;

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
	
}
