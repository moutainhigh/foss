package com.deppon.foss.module.base.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * 查询快递交接单返回实体
 * @author 273311 
 * @date 2017-3-1 上午9:48:42
 * @since
 * @version
 */
public class MsgOnlineEcsDtoResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4911143378316108752L;

	private List<MsgOnlineEcsDto> data;

	public List<MsgOnlineEcsDto> getData() {
		return data;
	}

	public void setData(List<MsgOnlineEcsDto> data) {
		this.data = data;
	}

	private int status;

	private String exMsg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}
}
