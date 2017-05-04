/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoaderQueryConditionDto.java
 *  
 *  FILE NAME          :LoaderQueryConditionDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderEntity;
/**
 * LoaderQueryConditionDto
 * @author dp-duyi
 * @date 2012-12-24 上午11:03:22
 */
public class LoaderQueryConditionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7406048761421161194L;
	/**理货员*/
	private String loader;
	/**部门*/
	private String org;
	/**查询时间开始*/
	private String queryTimeBegin;
	/**查询时间结束*/
	private String queryTimeEnd;
	/**职位*/
	private String title;
	/**登录部门*/
	private String loginOrgCode;
	/**理货员list*/
	private List<LoaderEntity> loaders;
	
	/**
	 * Gets the 理货员.
	 *
	 * @return the 理货员
	 */
	public String getLoader() {
		return loader;
	}
	
	/**
	 * Sets the 理货员.
	 *
	 * @param loader the new 理货员
	 */
	public void setLoader(String loader) {
		this.loader = loader;
	}
	
	/**
	 * Gets the 部门.
	 *
	 * @return the 部门
	 */
	public String getOrg() {
		return org;
	}
	
	/**
	 * Sets the 部门.
	 *
	 * @param org the new 部门
	 */
	public void setOrg(String org) {
		this.org = org;
	}
	
	/**
	 * Gets the 查询时间开始.
	 *
	 * @return the 查询时间开始
	 */
	public String getQueryTimeBegin() {
		return queryTimeBegin;
	}
	
	/**
	 * Sets the 查询时间开始.
	 *
	 * @param queryTimeBegin the new 查询时间开始
	 */
	public void setQueryTimeBegin(String queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}
	
	/**
	 * Gets the 查询时间结束.
	 *
	 * @return the 查询时间结束
	 */
	public String getQueryTimeEnd() {
		return queryTimeEnd;
	}
	
	/**
	 * Sets the 查询时间结束.
	 *
	 * @param queryTimeEnd the new 查询时间结束
	 */
	public void setQueryTimeEnd(String queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}
	
	/**
	 * Gets the 职位.
	 *
	 * @return the 职位
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the 职位.
	 *
	 * @param title the new 职位
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the 登录部门.
	 *
	 * @return the 登录部门
	 */
	public String getLoginOrgCode() {
		return loginOrgCode;
	}
	
	/**
	 * Sets the 登录部门.
	 *
	 * @param loginOrgCode the new 登录部门
	 */
	public void setLoginOrgCode(String loginOrgCode) {
		this.loginOrgCode = loginOrgCode;
	}
	
	/**
	 * Gets the 理货员list.
	 *
	 * @return the 理货员list
	 */
	public List<LoaderEntity> getLoaders() {
		return loaders;
	}
	
	/**
	 * Sets the 理货员list.
	 *
	 * @param loaders the new 理货员list
	 */
	public void setLoaders(List<LoaderEntity> loaders) {
		this.loaders = loaders;
	}
	
}