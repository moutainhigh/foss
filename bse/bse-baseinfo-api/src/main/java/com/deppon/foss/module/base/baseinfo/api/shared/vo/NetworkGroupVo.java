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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/NetworkGroupVo.java
 * 
 * FILE NAME        	: NetworkGroupVo.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetWorkGroupDto;


/**
 * 网点组信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-5 下午2:51:18 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-5 下午2:51:18
 * @since
 * @version
 */
public class NetworkGroupVo implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6562672769575526646L;
    
    /**
     * 网点组列表.
     */
    private List<NetWorkGroupDto> netGroupList;
    
    /**
     * 网点组.
     */
    private NetWorkGroupDto netGroupDto;
    
    /**
     * 走货路径的虚拟编码.
     */
    private String freightRouteVirtualCode;
    
    /**
     * 获取 网点组列表.
     *
     * @return  the netGroupList
     */
    public List<NetWorkGroupDto> getNetGroupList() {
        return netGroupList;
    }
    
    /**
     * 设置 网点组列表.
     *
     * @param netGroupList the netGroupList to set
     */
    public void setNetGroupList(List<NetWorkGroupDto> netGroupList) {
        this.netGroupList = netGroupList;
    }
    
    /**
     * 获取 网点组.
     *
     * @return  the netGroupDto
     */
    public NetWorkGroupDto getNetGroupDto() {
        return netGroupDto;
    }
    
    /**
     * 设置 网点组.
     *
     * @param netGroupDto the netGroupDto to set
     */
    public void setNetGroupDto(NetWorkGroupDto netGroupDto) {
        this.netGroupDto = netGroupDto;
    }
    
    /**
     * 获取 走货路径的虚拟编码.
     *
     * @return  the freightRouteVirtualCode
     */
    public String getFreightRouteVirtualCode() {
        return freightRouteVirtualCode;
    }
    
    /**
     * 设置 走货路径的虚拟编码.
     *
     * @param freightRouteVirtualCode the freightRouteVirtualCode to set
     */
    public void setFreightRouteVirtualCode(String freightRouteVirtualCode) {
        this.freightRouteVirtualCode = freightRouteVirtualCode;
    }
    

}
