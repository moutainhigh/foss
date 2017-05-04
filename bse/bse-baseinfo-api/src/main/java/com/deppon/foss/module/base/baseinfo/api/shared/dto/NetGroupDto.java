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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/NetGroupDto.java
 * 
 * FILE NAME        	: NetGroupDto.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.SymbolConstants;


/**
 * 网点组Dto
 * @author foss-zhujunyong
 * @date Nov 4, 2012 9:39:28 AM
 * @version 1.0
 */
public class NetGroupDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 5755956075912327878L;

    // 网点组名称
    private String netGroupName;

    // 出发营业部列表
    private List<String> sourceOrganizationCodeList;
    
    // 到达营业部列表
    private List<String> targetOrganizationCodeList;
    
    // 出发营业部名称列表（冗余）
    private List<String> sourceOrganizationNameList;
    
    // 到达营业部名称列表（冗余）
    private List<String> targetOrganizationNameList;
    
    //出发营业部字符串（冗余）
    @SuppressWarnings("unused")
    private String sourceOrgs;
    
    //到达营业部字符串（冗余）
    @SuppressWarnings("unused")
    private String targetOrgs;
    
    // 创建用户
    private String createUser;
    
    // 修改用户
    private String modifyUser;

    // 走货路径虚拟编码
    private String freightRouteVirtualCode;
    
    //是否快递产品网点组
    private String expNetworkGroup;
    
    public String getExpNetworkGroup() {
		return expNetworkGroup;
	}
	public void setExpNetworkGroup(String expNetworkGroup) {
		this.expNetworkGroup = expNetworkGroup;
	}
	// 返回逗号分割的出发营业部名称字符串
    public String getSourceOrganizationName() {
	return StringUtils.join(sourceOrganizationNameList, SymbolConstants.EN_COMMA);
    }
    
    // 返回逗号分割的到达营业部名称字符串
    public String getTargetOrganizationName() {
	return StringUtils.join(targetOrganizationNameList, SymbolConstants.EN_COMMA);
    }
    
    /**
     * 
     * <p>去重复数据</p> 
     * @author foss-zhujunyong
     * @date Jun 6, 2013 10:58:38 AM
     * @see
     */
    public void trim(){
	if (CollectionUtils.isNotEmpty(sourceOrganizationCodeList)) {
	    List<String> tmp = new ArrayList<String> ();
	    for (String code : sourceOrganizationCodeList) {
		if (!tmp.contains(code)) {
		    tmp.add(code);
		}
	    }
	    sourceOrganizationCodeList = tmp;
	}
	if (CollectionUtils.isNotEmpty(targetOrganizationCodeList)) {
	    List<String> tmp = new ArrayList<String> ();
	    for (String code : targetOrganizationCodeList) {
		if (!tmp.contains(code)) {
		    tmp.add(code);
		}
	    }
	    targetOrganizationCodeList = tmp;
	}
	if (CollectionUtils.isNotEmpty(sourceOrganizationNameList)) {
	    List<String> tmp = new ArrayList<String> ();
	    for (String code : sourceOrganizationNameList) {
		if (!tmp.contains(code)) {
		    tmp.add(code);
		}
	    }
	    sourceOrganizationNameList = tmp;
	}
	if (CollectionUtils.isNotEmpty(targetOrganizationNameList)) {
	    List<String> tmp = new ArrayList<String> ();
	    for (String code : targetOrganizationNameList) {
		if (!tmp.contains(code)) {
		    tmp.add(code);
		}
	    }
	    targetOrganizationNameList = tmp;
	}
    }
    
    /**
     * @return  the sourceOrgs
     */
    public String getSourceOrgs() {
        return StringUtils.join(sourceOrganizationCodeList, SymbolConstants.EN_COMMA);
    }
    
    /**
     * @param sourceOrgs the sourceOrgs to set
     */
    public void setSourceOrgs(String sourceOrgs) {
        this.sourceOrgs = sourceOrgs;
    }

    
    /**
     * @return  the targetOrgs
     */
    public String getTargetOrgs() {
        return StringUtils.join(targetOrganizationCodeList, SymbolConstants.EN_COMMA);
    }
    
    /**
     * @param targetOrgs the targetOrgs to set
     */
    public void setTargetOrgs(String targetOrgs) {
        this.targetOrgs = targetOrgs;
    }

    /**
     * @return  the netGroupName
     */
    public String getNetGroupName() {
        return netGroupName;
    }

    
    /**
     * @param netGroupName the netGroupName to set
     */
    public void setNetGroupName(String netGroupName) {
        this.netGroupName = netGroupName;
    }

    
    /**
     * @return  the sourceOrganizationCodeList
     */
    public List<String> getSourceOrganizationCodeList() {
        return sourceOrganizationCodeList;
    }

    
    /**
     * @param sourceOrganizationCodeList the sourceOrganizationCodeList to set
     */
    public void setSourceOrganizationCodeList(
    	List<String> sourceOrganizationCodeList) {
        this.sourceOrganizationCodeList = sourceOrganizationCodeList;
//        this.sourceOrgs = montage(sourceOrganizationCodeList);
    }

    
    /**
     * @return  the targetOrganizationCodeList
     */
    public List<String> getTargetOrganizationCodeList() {
        return targetOrganizationCodeList;
    }

    
    /**
     * @param targetOrganizationCodeList the targetOrganizationCodeList to set
     */
    public void setTargetOrganizationCodeList(
    	List<String> targetOrganizationCodeList) {
        this.targetOrganizationCodeList = targetOrganizationCodeList;
//        this.targetOrgs = montage(targetOrganizationCodeList);
    }
    
    
    /**
     * @return  the createUser
     */
    public String getCreateUser() {
        return createUser;
    }


    
    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    
    /**
     * @return  the modifyUser
     */
    public String getModifyUser() {
        return modifyUser;
    }


    
    /**
     * @param modifyUser the modifyUser to set
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }


    
    /**
     * @return  the freightRouteVirtualCode
     */
    public String getFreightRouteVirtualCode() {
        return freightRouteVirtualCode;
    }


    
    /**
     * @param freightRouteVirtualCode the freightRouteVirtualCode to set
     */
    public void setFreightRouteVirtualCode(String freightRouteVirtualCode) {
        this.freightRouteVirtualCode = freightRouteVirtualCode;
    }

    /**
     * <p>拼接字符串</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-7 上午11:06:03
     * @param list
     * @return
     * @see
     */
   /* @SuppressWarnings("unused")
    private String montage(List<String> list){
	if (CollectionUtils.isNotEmpty(list)) {
	    StringBuffer buffer = new StringBuffer();
	    for (String orgName : list) {
		buffer.append(orgName).append(SymbolConstants.EN_COMMA);
	    }
	    // 截取最后的逗号
	    String orgNames = buffer.toString();
	    orgNames = orgNames.substring(0, orgNames.length() - 1);
	    // 赋值
	    return orgNames;
	} else {
	    return null;
	}
    }*/

    
    public List<String> getSourceOrganizationNameList() {
        return sourceOrganizationNameList;
    }

    
    public void setSourceOrganizationNameList(
    	List<String> sourceOrganizationNameList) {
        this.sourceOrganizationNameList = sourceOrganizationNameList;
    }

    
    public List<String> getTargetOrganizationNameList() {
        return targetOrganizationNameList;
    }

    
    public void setTargetOrganizationNameList(
    	List<String> targetOrganizationNameList) {
        this.targetOrganizationNameList = targetOrganizationNameList;
    }
    
    
    
    
}
