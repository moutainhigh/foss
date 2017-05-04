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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/EmployeeVo.java
 * 
 * FILE NAME        	: EmployeeVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;


/**
 * 人员VO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-29 下午4:10:24
 */
public class EmployeeVo implements Serializable {
    private static final long serialVersionUID = 8640505034012743142L;
    
    // 行政区域列表
    private List<EmployeeEntity> employeeList;

    // 行政区域详情
    private EmployeeEntity employeeDetail = new EmployeeEntity();
    
    /**
     * 下面是get,set方法
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-16 下午2:20:42
     */

    
    
    
    public List<EmployeeEntity> getEmployeeList() {
        return employeeList;
    }

    
    public void setEmployeeList(List<EmployeeEntity> employeeList) {
        this.employeeList = employeeList;
    }

    
    public EmployeeEntity getEmployeeDetail() {
        return employeeDetail;
    }

    
    public void setEmployeeDetail(EmployeeEntity employeeDetail) {
        this.employeeDetail = employeeDetail;
    }

}
