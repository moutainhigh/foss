package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.vo.EscWayBillValueAddDetaillVo;


/**
 * 增值服务的响应实体
 * @author ECS-273279 liding
 * 2016-4-19 上午10:13:02
 */
public class WaybillValueAddResponse implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 成功标志
	 */
	private String result;
	
	/**
	 * 失败原因
	 */
	private String message;
	
	
	/**
	 * 增值信息
	 */
	private List<EscWayBillValueAddDetaillVo> data ;
	
	public List<EscWayBillValueAddDetaillVo> getData() {
		return data;
	}
	public void setData(List<EscWayBillValueAddDetaillVo> data) {
		this.data = data;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
