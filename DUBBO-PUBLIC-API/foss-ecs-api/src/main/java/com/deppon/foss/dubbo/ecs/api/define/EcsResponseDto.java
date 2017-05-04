package com.deppon.foss.dubbo.ecs.api.define;

import java.io.Serializable;
import java.util.List;

/**
 * 快递业务 响应的参数DTO
 * @author 243921-zhangtingting
 * @date 2016-04-15 下午02:10:58
 *
 */
public class EcsResponseDto implements Serializable {

	//序列号
	private static final long serialVersionUID = 1L;
		
	//结果
	private int result;
		
	//处理信息说明
	private String message;
	
	//运单号集合
	private List<String> waybillNos;

	public List<String> getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
