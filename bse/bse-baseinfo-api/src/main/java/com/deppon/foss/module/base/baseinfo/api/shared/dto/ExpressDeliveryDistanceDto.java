package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

public class ExpressDeliveryDistanceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8122077595989643924L;

	//小区的范围id  给Gis的
	private String polygonId;
	//营业部的id 给Gis的
	private String pointId;
	/**
	 * get  set方法
	 * @return
	 */
	public String getPolygonId() {
		return polygonId;
	}
	public void setPolygonId(String polygonId) {
		this.polygonId = polygonId;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
}

