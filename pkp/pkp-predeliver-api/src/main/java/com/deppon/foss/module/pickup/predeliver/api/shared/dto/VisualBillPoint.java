/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 坐标点
 * @author 239284
 */
public class VisualBillPoint extends BaseEntity {
	
	private static final long serialVersionUID = 8932292185513155597L;

	// 经度
	private double longitude;
	// 纬度
	private double latitude;

	/**
	 * 创建一个新的实例 Point
	 */
	public VisualBillPoint() {

	}

	/**
	 * 创建一个新的实例 Point
	 */
	public VisualBillPoint(double x, double y) {
		this.longitude = x;
		this.latitude = y;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
