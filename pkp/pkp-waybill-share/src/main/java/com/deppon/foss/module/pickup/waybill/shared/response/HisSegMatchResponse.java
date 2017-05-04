package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
/**
 * gis查询目的站信息服务接口响应的实体信息
 * @author 321993 zhangdianhao
 * @date 2017-03-16 下午13:53:50
 * @version 1.0
 */
public class HisSegMatchResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 网点名称
	 */
	private String stationName;
	/**
	 * 网点编码
	 */
	private String stationCode;
	/**
	 * 备注
	 */
	private String comment;
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
