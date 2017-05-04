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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/PaginationDto.java
 * 
 * FILE NAME        	: PaginationDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 分页的Action和Service通讯封装对象
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-30 下午8:59:14</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-30 下午8:59:14
 * @since
 * @version
 */
public class PaginationDto implements Serializable {
	
	public PaginationDto(){
		
	}
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5499409353308384718L;

    @SuppressWarnings("rawtypes")
    protected List paginationDtos = new ArrayList();
    
    protected Long totalCount = 0l;
    
    /**
     * @return  the paginationDtos
     */
    @SuppressWarnings("rawtypes")
    public List getPaginationDtos() {
        return paginationDtos;
    }
    
    /**
     * @param paginationDtos the paginationDtos to set
     */
    public void setPaginationDtos(@SuppressWarnings("rawtypes") List paginationDtos) {
	this.paginationDtos = paginationDtos;
    }
    
    /**
     * @return  the totalCount
     */
    public Long getTotalCount() {
        return totalCount;
    }
    
    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    } 
}
