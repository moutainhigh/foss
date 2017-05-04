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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/MapDto.java
 * 
 * FILE NAME        	: MapDto.java
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


/**
 * 给EXT用的，转换Map成的Dto
 * @author foss-zhujunyong
 * @date Jan 21, 2013 5:22:54 PM
 * @version 1.0
 */
public class MapDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -2301414059412622469L;

    /**
     * 编码
     */
    private String code;
    
    /**
     * 名称
     */
    private String name;

    
    /**
     * @return  the code
     */
    public String getCode() {
        return code;
    }

    
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    
    /**
     * @return  the name
     */
    public String getName() {
        return name;
    }

    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /** 
     * @author foss-zhujunyong
     * @date Apr 26, 2013 3:29:25 PM
     * @return 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return code == null ? 0 : code.hashCode();
    }


    /** 
     * @author foss-zhujunyong
     * @date Apr 26, 2013 3:29:25 PM
     * @param obj
     * @return 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MapDto other = (MapDto) obj;
	if (code == null) {
	    if (other.code != null)
		return false;
	} else if (!code.equals(other.code))
	    return false;
	return true;
    }
}
