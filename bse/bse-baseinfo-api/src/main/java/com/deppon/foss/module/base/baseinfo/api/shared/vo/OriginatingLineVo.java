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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OriginatingLineVo.java
 * 
 * FILE NAME        	: OriginatingLineVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineUserOrgDto;
/**
 * 始发线路VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-9 下午6:17:41 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-9 下午6:17:41
 * @since
 * @version
 */
public class OriginatingLineVo implements Serializable{

    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4175183420191649481L;
    
    /**
     * 始发线路实体类.
     */
    private LineEntity entity;
    
    /**
     * 始发线路实体集合.
     */
    private List<LineEntity> lineEntities;
    
    /**
     * codes字符串.
     */
    private String codes;
    
    /**
     * 线路虚拟编码.
     */
    private String lineVirtualCode;
    
    /**
     * 发车标准实体List.
     */
    private List<DepartureStandardEntity> departureStandardEntityList;
    
    /**
     * 发车标准实体.
     */
    private DepartureStandardEntity departureStandardEntity;
    
    /**
     * 线路虚拟编码集合.
     */
    private List<String> codeList;
    /**
     * 用户数据权限
     */
    private LineUserOrgDto lineUserOrgDto;
    /**
     * 获取 始发线路实体类.
     *
     * @return  the entity
     */
    public LineEntity getEntity() {
        return entity;
    }
    
    /**
     * 获取 线路虚拟编码集合.
     *
     * @return  the codeList
     */
    public List<String> getCodeList() {
        return codeList;
    }

    
    /**
     * 设置 线路虚拟编码集合.
     *
     * @param codeList the codeList to set
     */
    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    /**
     * 设置 始发线路实体类.
     *
     * @param entity the entity to set
     */
    public void setEntity(LineEntity entity) {
        this.entity = entity;
    }
    
    /**
     * 获取 始发线路实体集合.
     *
     * @return  the lineEntities
     */
    public List<LineEntity> getLineEntities() {
        return lineEntities;
    }
    
    /**
     * 设置 始发线路实体集合.
     *
     * @param lineEntities the lineEntities to set
     */
    public void setLineEntities(List<LineEntity> lineEntities) {
        this.lineEntities = lineEntities;
    }
    
    /**
     * 获取 codes字符串.
     *
     * @return  the codes
     */
    public String getCodes() {
        return codes;
    }
    
    /**
     * 设置 codes字符串.
     *
     * @param codes the codes to set
     */
    public void setCodes(String codes) {
        this.codes = codes;
    }
    
    /**
     * 获取 线路虚拟编码.
     *
     * @return  the lineVirtualCode
     */
    public String getLineVirtualCode() {
        return lineVirtualCode;
    }
    
    /**
     * 设置 线路虚拟编码.
     *
     * @param lineVirtualCode the lineVirtualCode to set
     */
    public void setLineVirtualCode(String lineVirtualCode) {
        this.lineVirtualCode = lineVirtualCode;
    }
    
    /**
     * 获取 发车标准实体List.
     *
     * @return  the departureStandardEntityList
     */
    public List<DepartureStandardEntity> getDepartureStandardEntityList() {
        return departureStandardEntityList;
    }
    
    /**
     * 设置 发车标准实体List.
     *
     * @param departureStandardEntityList the departureStandardEntityList to set
     */
    public void setDepartureStandardEntityList(
    	List<DepartureStandardEntity> departureStandardEntityList) {
        this.departureStandardEntityList = departureStandardEntityList;
    }
    
    /**
     * 获取 发车标准实体.
     *
     * @return  the departureStandardEntity
     */
    public DepartureStandardEntity getDepartureStandardEntity() {
        return departureStandardEntity;
    }
    
    /**
     * 设置 发车标准实体.
     *
     * @param departureStandardEntity the departureStandardEntity to set
     */
    public void setDepartureStandardEntity(
    	DepartureStandardEntity departureStandardEntity) {
        this.departureStandardEntity = departureStandardEntity;
    }

	public LineUserOrgDto getLineUserOrgDto() {
		return lineUserOrgDto;
	}

	public void setLineUserOrgDto(LineUserOrgDto lineUserOrgDto) {
		this.lineUserOrgDto = lineUserOrgDto;
	}
    
    
}
