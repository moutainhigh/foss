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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/FreightRouteVo.java
 * 
 * FILE NAME        	: FreightRouteVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineDeptDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupSiteDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetWorkGroupDto;

/**
 * 走货路径信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-8 下午1:56:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-8 下午1:56:11
 * @since
 * @version
 */
public class FreightRouteVo implements Serializable{

    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5743230381268315971L;
    
    /**
     * 走货路径实体类.
     */
    private FreightRouteEntity entity;
    
    /**
     * 走货路径信息集合.
     */
    private List<FreightRouteEntity> freightRouteEntities;
    
    /**
     * 走货路径虚拟编码.
     */
    private List<String> ids;
    
    /**
     * 走货路径的虚拟编码.
     */
    private String freightRouteVirtualCode;
    
    /**
     * 网点组信息列表.
     */
    private List<NetWorkGroupDto> netGroupDtos;
    
    /**
     * 走货路径线路信息列表.
     */
    private List<FreightRouteLineEntity> lineList;
    
    /**
     * 外场编码.
     */
    private String orgCode;
    
    /**
     * 出发部门编码.
     */
    private String sourceOrgCode;
    
    /**
     * 中转线路虚拟编码.
     */
    private String lineVirtualCode;
    
    /**
     * 中转线路简码.
     */
    private String lineSimpleCode;
    
    /**
     * 外场对应的营业部.
     */
    private List<NetGroupSiteDto> groupSiteList;
    
    /**
     * 线路时效.
     */
    private Long aging;
    
    /**
     * 出发/到达站编码、名称集合.
     */
    private List<LineDeptDto> lineDeptList;
    
    /**
     * 第三级产品代码.
     */
    private String productCode;
    
    /**
     * 走货路径线路
     */
    private List<FreightRouteLineDto> freightRouteLineDtoList;
    /**
     * 始发站CODE
     */
    private String sourceCode;
    /**
     * 目的站CODE
     */
    private String targetCode;
    /**
     * 时间
     */
    private Date time;
    /**
     * 用户部门权限
     */
    private List<String> userOrgList;
    
    public List<String> getUserOrgList() {
		return userOrgList;
	}

	public void setUserOrgList(List<String> userOrgList) {
		this.userOrgList = userOrgList;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<FreightRouteLineDto> getFreightRouteLineDtoList() {
		return freightRouteLineDtoList;
	}

	public void setFreightRouteLineDtoList(
			List<FreightRouteLineDto> freightRouteLineDtoList) {
		this.freightRouteLineDtoList = freightRouteLineDtoList;
	}

	/**
     * 获取 走货路径实体类.
     *
     * @return  the entity
     */
    public FreightRouteEntity getEntity() {
        return entity;
    }
    
    /**
     * 设置 走货路径实体类.
     *
     * @param entity the entity to set
     */
    public void setEntity(FreightRouteEntity entity) {
        this.entity = entity;
    }
    
    /**
     * 获取 走货路径信息集合.
     *
     * @return  the freightRouteEntities
     */
    public List<FreightRouteEntity> getFreightRouteEntities() {
        return freightRouteEntities;
    }
    
    /**
     * 设置 走货路径信息集合.
     *
     * @param freightRouteEntities the freightRouteEntities to set
     */
    public void setFreightRouteEntities(
    	List<FreightRouteEntity> freightRouteEntities) {
        this.freightRouteEntities = freightRouteEntities;
    }
    
    /**
     * 获取 走货路径虚拟编码.
     *
     * @return  the ids
     */
    public List<String> getIds() {
        return ids;
    }
    
    /**
     * 设置 走货路径虚拟编码.
     *
     * @param ids the ids to set
     */
    public void setIds(List<String> ids) {
        this.ids = ids;
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
    
    /**
     * 获取 网点组信息列表.
     *
     * @return  the netGroupDtos
     */
    public List<NetWorkGroupDto> getNetGroupDtos() {
        return netGroupDtos;
    }
    
    /**
     * 设置 网点组信息列表.
     *
     * @param netGroupDtos the netGroupDtos to set
     */
    public void setNetGroupDtos(List<NetWorkGroupDto> netGroupDtos) {
        this.netGroupDtos = netGroupDtos;
    }
    
    /**
     * 获取 走货路径线路信息列表.
     *
     * @return  the lineList
     */
    public List<FreightRouteLineEntity> getLineList() {
        return lineList;
    }
    
    /**
     * 设置 走货路径线路信息列表.
     *
     * @param lineList the lineList to set
     */
    public void setLineList(List<FreightRouteLineEntity> lineList) {
        this.lineList = lineList;
    }
    
    /**
     * 获取 外场编码.
     *
     * @return  the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }
    
    /**
     * 设置 外场编码.
     *
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    /**
     * 获取 出发部门编码.
     *
     * @return  the sourceOrgCode
     */
    public String getSourceOrgCode() {
        return sourceOrgCode;
    }
    
    /**
     * 设置 出发部门编码.
     *
     * @param sourceOrgCode the sourceOrgCode to set
     */
    public void setSourceOrgCode(String sourceOrgCode) {
        this.sourceOrgCode = sourceOrgCode;
    }
    
    /**
     * 获取 中转线路虚拟编码.
     *
     * @return  the lineVirtualCode
     */
    public String getLineVirtualCode() {
        return lineVirtualCode;
    }
    
    /**
     * 设置 中转线路虚拟编码.
     *
     * @param lineVirtualCode the lineVirtualCode to set
     */
    public void setLineVirtualCode(String lineVirtualCode) {
        this.lineVirtualCode = lineVirtualCode;
    }
    
    /**
     * 获取 中转线路简码.
     *
     * @return  the lineSimpleCode
     */
    public String getLineSimpleCode() {
        return lineSimpleCode;
    }
    
    /**
     * 设置 中转线路简码.
     *
     * @param lineSimpleCode the lineSimpleCode to set
     */
    public void setLineSimpleCode(String lineSimpleCode) {
        this.lineSimpleCode = lineSimpleCode;
    }
    
    /**
     * 获取 外场对应的营业部.
     *
     * @return  the groupSiteList
     */
    public List<NetGroupSiteDto> getGroupSiteList() {
        return groupSiteList;
    }
    
    /**
     * 设置 外场对应的营业部.
     *
     * @param groupSiteList the groupSiteList to set
     */
    public void setGroupSiteList(List<NetGroupSiteDto> groupSiteList) {
        this.groupSiteList = groupSiteList;
    }
    
    /**
     * 获取 线路时效.
     *
     * @return  the aging
     */
    public Long getAging() {
        return aging;
    }
    
    /**
     * 设置 线路时效.
     *
     * @param aging the aging to set
     */
    public void setAging(Long aging) {
        this.aging = aging;
    }
    
    /**
     * 获取 出发/到达站编码、名称集合.
     *
     * @return  the lineDeptList
     */
    public List<LineDeptDto> getLineDeptList() {
        return lineDeptList;
    }
    
    /**
     * 设置 出发/到达站编码、名称集合.
     *
     * @param lineDeptList the lineDeptList to set
     */
    public void setLineDeptList(List<LineDeptDto> lineDeptList) {
        this.lineDeptList = lineDeptList;
    }
    
    /**
     * 获取 第三级产品代码.
     *
     * @return  the productCode
     */
    public String getProductCode() {
        return productCode;
    }
    
    /**
     * 设置 第三级产品代码.
     *
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    
    
}
