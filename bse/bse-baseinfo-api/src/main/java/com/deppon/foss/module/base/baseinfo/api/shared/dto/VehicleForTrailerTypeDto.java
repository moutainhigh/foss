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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/VehicleForTrailerTypeDto.java
 * 
 * FILE NAME        	: VehicleForTrailerTypeDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
/**
 * 用来交互“LMS挂车类型”的数据库对应“数据字典”数据访问Service接口：无SUC，提供给LMS同步
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-5 上午10:36:32</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-5 上午10:36:32
 * @since
 * @version
 */
public class VehicleForTrailerTypeDto implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 274794859366632635L;
    
    /**
     * 挂车类型编码
     */
    private String trailerTypeCode;
    
    /**
     * 挂车类型名称
     */
    private String trailerTypeName;

    /**
     * 挂车类型编码
     * 
     * @return  the trailerTypeCode
     */
    public String getTrailerTypeCode() {
        return trailerTypeCode;
    }

    /**
     * @param trailerTypeCode the trailerTypeCode to set
     */
    public void setTrailerTypeCode(String trailerTypeCode) {
        this.trailerTypeCode = trailerTypeCode;
    }
    
    /**
     * 挂车类型名称
     * 
     * @return  the trailerTypeName
     */
    public String getTrailerTypeName() {
        return trailerTypeName;
    }

    /**
     * @param trailerTypeName the trailerTypeName to set
     */
    public void setTrailerTypeName(String trailerTypeName) {
        this.trailerTypeName = trailerTypeName;
    }
}
