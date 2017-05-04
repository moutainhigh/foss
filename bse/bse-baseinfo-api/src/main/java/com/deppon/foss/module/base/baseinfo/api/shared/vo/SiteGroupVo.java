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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/SiteGroupVo.java
 * 
 * FILE NAME        	: SiteGroupVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;

/**
 * (站点组VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:078838-foss-zhangbin,date:2012-11-7 下午6:15:07
 * </p>
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-17 下午6:15:07
 * @since
 * @version
 */
public class SiteGroupVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3875916350859197349L;
	
	/**
	 * 站点List.
	 */
	private List<SiteGroupSiteEntity> siteGroupSiteEntityList;
	
	/**
	 * 站点.
	 */
	private SiteGroupSiteEntity siteGroupSiteEntity;
	
	/**
	 * 站点组List.
	 */
	private List<SiteGroupEntity> siteGroupEntityList;
	
	/**
	 * 站点.
	 */
	private SiteGroupEntity siteGroupEntity;
	
	/**
	 * 批量作废站点组的参数.
	 */
	private String codeStr;
	
	/**
	 * 站点组虚拟编码.
	 */
	private String siteGroupVirtualCode;
	
	/**
	 * 部门LIST.
	 */
	private List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList;
	
	/**
	 * 部门.
	 */
	private OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;
	
	/**
	 * 站点组选择的站点列表.
	 */
	private List<MapDto> mapDtoList;
	
	/**
	 * 部门编码.
	 */
	private String deptCode;
	
	/**
	 * 部门名称.
	 */
	private String deptName;
	
	/**
	 * 选择站点列表站点名称.
	 */
	private String siteName;
	
	/**
	 * 获取部门名称.
	 *
	 * 
	 */
	public String getDeptName() {
		return deptName;
	}
	
	/**
	 * 设置部门名称
	 *
	 * 
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 获取 站点List.
	 *
	 * @return the 站点List
	 */
	public List<SiteGroupSiteEntity> getSiteGroupSiteEntityList() {
		return siteGroupSiteEntityList;
	}
	
	/**
	 * 获取 部门编码.
	 *
	 * @return  the deptCode
	 */
	public String getDeptCode() {
	    return deptCode;
	}
	
	/**
	 * 设置 部门编码.
	 *
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
	    this.deptCode = deptCode;
	}

	/**
	 * 获取 选择站点列表站点名称.
	 *
	 * @return  the siteName
	 */
	public String getSiteName() {
	    return siteName;
	}

	
	/**
	 * 设置 选择站点列表站点名称.
	 *
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
	    this.siteName = siteName;
	}

	/**
	 * 设置 站点List.
	 *
	 * @param siteGroupSiteEntityList the new 站点List
	 */
	public void setSiteGroupSiteEntityList(
			List<SiteGroupSiteEntity> siteGroupSiteEntityList) {
		this.siteGroupSiteEntityList = siteGroupSiteEntityList;
	}
	
	
	/**
	 * 获取 站点组选择的站点列表.
	 *
	 * @return  the mapDtoList
	 */
	public List<MapDto> getMapDtoList() {
	    return mapDtoList;
	}

	
	/**
	 * 设置 站点组选择的站点列表.
	 *
	 * @param mapDtoList the mapDtoList to set
	 */
	public void setMapDtoList(List<MapDto> mapDtoList) {
	    this.mapDtoList = mapDtoList;
	}

	/**
	 * 获取 站点.
	 *
	 * @return the 站点
	 */
	public SiteGroupSiteEntity getSiteGroupSiteEntity() {
		return siteGroupSiteEntity;
	}

	/**
	 * 设置 站点.
	 *
	 * @param siteGroupSiteEntity the new 站点
	 */
	public void setSiteGroupSiteEntity(SiteGroupSiteEntity siteGroupSiteEntity) {
		this.siteGroupSiteEntity = siteGroupSiteEntity;
	}

	/**
	 * 获取 站点组List.
	 *
	 * @return the 站点组List
	 */
	public List<SiteGroupEntity> getSiteGroupEntityList() {
		return siteGroupEntityList;
	}

	/**
	 * 设置 站点组List.
	 *
	 * @param siteGroupEntityList the new 站点组List
	 */
	public void setSiteGroupEntityList(List<SiteGroupEntity> siteGroupEntityList) {
		this.siteGroupEntityList = siteGroupEntityList;
	}

	/**
	 * 获取 站点.
	 *
	 * @return the 站点
	 */
	public SiteGroupEntity getSiteGroupEntity() {
		return siteGroupEntity;
	}

	/**
	 * 设置 站点.
	 *
	 * @param siteGroupEntity the new 站点
	 */
	public void setSiteGroupEntity(SiteGroupEntity siteGroupEntity) {
		this.siteGroupEntity = siteGroupEntity;
	}

	/**
	 * 获取 批量作废站点组的参数.
	 *
	 * @return the 批量作废站点组的参数
	 */
	public String getCodeStr() {
		return codeStr;
	}

	/**
	 * 设置 批量作废站点组的参数.
	 *
	 * @param codeStr the new 批量作废站点组的参数
	 */
	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

	/**
	 * 获取 站点组虚拟编码.
	 *
	 * @return the 站点组虚拟编码
	 */
	public String getSiteGroupVirtualCode() {
		return siteGroupVirtualCode;
	}

	/**
	 * 设置 站点组虚拟编码.
	 *
	 * @param siteGroupVirtualCode the new 站点组虚拟编码
	 */
	public void setSiteGroupVirtualCode(String siteGroupVirtualCode) {
		this.siteGroupVirtualCode = siteGroupVirtualCode;
	}

	/**
	 * 获取 部门LIST.
	 *
	 * @return the 部门LIST
	 */
	public List<OrgAdministrativeInfoEntity> getOrgAdministrativeInfoEntityList() {
		return orgAdministrativeInfoEntityList;
	}

	/**
	 * 设置 部门LIST.
	 *
	 * @param orgAdministrativeInfoEntityList the new 部门LIST
	 */
	public void setOrgAdministrativeInfoEntityList(
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList) {
		this.orgAdministrativeInfoEntityList = orgAdministrativeInfoEntityList;
	}

	/**
	 * 获取 部门.
	 *
	 * @return the 部门
	 */
	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}

	/**
	 * 设置 部门.
	 *
	 * @param orgAdministrativeInfoEntity the new 部门
	 */
	public void setOrgAdministrativeInfoEntity(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}


}
