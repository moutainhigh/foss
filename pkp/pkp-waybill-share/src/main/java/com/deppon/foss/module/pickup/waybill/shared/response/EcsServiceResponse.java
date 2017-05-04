package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
/**
 * gis查询目的站信息服务接口响应的实体信息
 * @author 321993 zhangdianhao
 * @date 2017-03-16 下午13:53:50
 * @version 1.0
 */
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
public class EcsServiceResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 12333333L;
	private List<CustomerQueryConditionDto> responses;
	
	public List<CustomerQueryConditionDto> getResponses() {
		return responses;
	}
	public void setResponses(List<CustomerQueryConditionDto> responses) {
		this.responses = responses;
	}

	
}
