/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/util/PropertyFactory.java
 * 
 * FILE NAME        	: PropertyFactory.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.util;

/**
 * GIS和GPS的配置信息
 * 
 * @author 038590-foss-wanghui
 * @date 2013-3-5 上午11:55:21
 */
public class PropertyFactory {
	/**
	 * 
	 */
	private String gisExpressUrl;
	/**
	 * GIS地址
	 */
	private String gisUrl;
	/**
	 * GPS地址
	 */
	private String gpsUrl;
	/**
	 * GPS key
	 */
	private String appKey;
	/**
	 * GPS secret
	 */
	private String appSecret;
	/**
	 * GPS方法
	 */
	private String method;
	
	/**
	 * 短途GPSURL
	 */
	private String shortGpsUrl;
	private String esbRsUrl; //esb rs接口 DMANA-4130

	public String getEsbRsUrl() {
		return esbRsUrl;
	}

	public void setEsbRsUrl(String esbRsUrl) {
		this.esbRsUrl = esbRsUrl;
	}

	/**
	 * @return the gisUrl
	 */
	public String getGisUrl() {
		return gisUrl;
	}

	/**
	 * @param gisUrl the gisUrl to set
	 */
	public void setGisUrl(String gisUrl) {
		this.gisUrl = gisUrl;
	}

	/**
	 * @return the gpsUrl
	 */
	public String getGpsUrl() {
		return gpsUrl;
	}

	/**
	 * @param gpsUrl the gpsUrl to set
	 */
	public void setGpsUrl(String gpsUrl) {
		this.gpsUrl = gpsUrl;
	}

	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/**
	 * @param appSecret the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	public String getGisExpressUrl() {
		return gisExpressUrl;
	}

	public void setGisExpressUrl(String gisExpressUrl) {
		this.gisExpressUrl = gisExpressUrl;
	}
	
		public String getShortGpsUrl() {
		return shortGpsUrl;
	}

	public void setShortGpsUrl(String shortGpsUrl) {
		this.shortGpsUrl = shortGpsUrl;
	}

}