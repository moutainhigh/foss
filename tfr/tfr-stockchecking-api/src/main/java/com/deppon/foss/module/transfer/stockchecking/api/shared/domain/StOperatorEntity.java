/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StOperatorEntity.java
 *  
 *  FILE NAME          :StOperatorEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 清仓任务参与人实体类
 * @author foss-wuyingjie
 * @date 2012-10-16 下午3:44:44
 */
public class StOperatorEntity extends BaseEntity{
	
	private static final long serialVersionUID = -4886535165497462860L;

	/**用户编码*/
	private String empCode;
	/**用户姓名*/
    private String empName;
    /**清仓任务ID*/
    private String stTaskId;

    /**
     * 获取 用户编码.
     *
     * @return the 用户编码
     */
    public String getEmpCode() {
        return empCode;
    }

    /**
     * 设置 用户编码.
     *
     * @param empCode the new 用户编码
     */
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    /**
     * 获取 用户姓名.
     *
     * @return the 用户姓名
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * 设置 用户姓名.
     *
     * @param empName the new 用户姓名
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * 获取 清仓任务ID.
     *
     * @return the 清仓任务ID
     */
    public String getStTaskId() {
        return stTaskId;
    }

    /**
     * 设置 清仓任务ID.
     *
     * @param stTaskId the new 清仓任务ID
     */
    public void setStTaskId(String stTaskId) {
        this.stTaskId = stTaskId;
    }
}