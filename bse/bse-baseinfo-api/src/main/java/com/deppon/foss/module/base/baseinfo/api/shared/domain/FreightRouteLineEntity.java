/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/FreightRouteLineEntity.java
 * 
 * FILE NAME        	: FreightRouteLineEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 走货路径线路实体
 * 
 * @author foss-zhujunyong
 * @date Oct 18, 2012 6:51:28 PM
 * @version 1.0
 */
public class FreightRouteLineEntity extends BaseEntity {

	/**
     * 
     */
	private static final long serialVersionUID = 1229547164522897036L;

	// 顺序
	private Long sequence;

	// 虚拟编码
	private String virtualCode;

	// 走货路径虚拟编码
	private String freightRouteVirtualCode;

	// 线路虚拟编码
	private String lineVirtualCode;

	// 出发部门编码
	private String orginalOrganizationCode;

	// 到达部门编码
	private String destinationOrganizationCode;

	// 时效 (冗余)
	private Long aging;

	// 经停时间 (千分之小时)
	private Long passbyAging;

	// 是否有效
	private String active;

	// 线路名称（冗余）
	private String lineName;
	// 线路简码（冗余）
	private String simpleCode;
	// 出发部门名称（冗余）
	private String orginalOrganizationName;
	// 到达部门名称（冗余）
	private String destinationOrganizationName;
	// 版本号
	private Long version;
	// 考核班次
	private Long classes;

	/**
	 * 
	 * <p>
	 * 检查出发站和到达站是否相同
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Jan 11, 2013 3:16:57 PM
	 * @return
	 * @see
	 */
	public boolean checkSameSite() {
		return StringUtils.equals(orginalOrganizationCode,
				destinationOrganizationCode);
	}

	/**
	 * 
	 * @author foss-zhujunyong
	 * @date Mar 19, 2013 4:04:28 PM
	 * @return
	 * @see
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * 
	 * @author foss-zhujunyong
	 * @date Mar 19, 2013 4:04:33 PM
	 * @param version
	 * @see
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * @return the sequence
	 */
	public Long getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * @param virtualCode
	 *            the virtualCode to set
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * @return the freightRouteVirtualCode
	 */
	public String getFreightRouteVirtualCode() {
		return freightRouteVirtualCode;
	}

	/**
	 * @param freightRouteVirtualCode
	 *            the freightRouteVirtualCode to set
	 */
	public void setFreightRouteVirtualCode(String freightRouteVirtualCode) {
		this.freightRouteVirtualCode = freightRouteVirtualCode;
	}

	/**
	 * @return the lineVirtualCode
	 */
	public String getLineVirtualCode() {
		return lineVirtualCode;
	}

	/**
	 * @param lineVirtualCode
	 *            the lineVirtualCode to set
	 */
	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}

	/**
	 * @return the orginalOrganizationCode
	 */
	public String getOrginalOrganizationCode() {
		return orginalOrganizationCode;
	}

	/**
	 * @param orginalOrganizationCode
	 *            the orginalOrganizationCode to set
	 */
	public void setOrginalOrganizationCode(String orginalOrganizationCode) {
		this.orginalOrganizationCode = orginalOrganizationCode;
	}

	/**
	 * @return the destinationOrganizationCode
	 */
	public String getDestinationOrganizationCode() {
		return destinationOrganizationCode;
	}

	/**
	 * @param destinationOrganizationCode
	 *            the destinationOrganizationCode to set
	 */
	public void setDestinationOrganizationCode(
			String destinationOrganizationCode) {
		this.destinationOrganizationCode = destinationOrganizationCode;
	}

	/**
	 * @return the aging
	 */
	public Long getAging() {
		return aging;
	}

	/**
	 * @param aging
	 *            the aging to set
	 */
	public void setAging(Long aging) {
		this.aging = aging;
	}

	/**
	 * @return the passbyAging
	 */
	public Long getPassbyAging() {
		return passbyAging;
	}

	/**
	 * @param passbyAging
	 *            the passbyAging to set
	 */
	public void setPassbyAging(Long passbyAging) {
		this.passbyAging = passbyAging;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getSimpleCode() {
		return simpleCode;
	}

	public void setSimpleCode(String simpleCode) {
		this.simpleCode = simpleCode;
	}

	public String getOrginalOrganizationName() {
		return orginalOrganizationName;
	}

	public void setOrginalOrganizationName(String orginalOrganizationName) {
		this.orginalOrganizationName = orginalOrganizationName;
	}

	public String getDestinationOrganizationName() {
		return destinationOrganizationName;
	}

	public void setDestinationOrganizationName(
			String destinationOrganizationName) {
		this.destinationOrganizationName = destinationOrganizationName;
	}

	public Long getClasses() {
		return classes;
	}

	public void setClasses(Long classes) {
		this.classes = classes;
	}

}
