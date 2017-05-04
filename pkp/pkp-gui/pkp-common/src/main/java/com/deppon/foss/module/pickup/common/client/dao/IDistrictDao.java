/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IDistrictDao.java
 * 
 * FILE NAME        	: IDistrictDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;


/**
 * 
 * 行政区域数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午2:55:55, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午2:55:55
 * @since
 * @version
 */
public interface IDistrictDao {

    /**
     * <p>通过主键获取行政区域</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:27:15
     * @param id
     * @return
     * @see
     */
     AdministrativeRegionsEntity queryByPrimaryKey(String id);
    
    /**
     * <p>获取指定级别的行政区域</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:27:34
     * @param degree
     * @return
     * @see
     */
     List<AdministrativeRegionsEntity> queryByDegree(String degree);
     
     /**
      * <p>获取指定名称的行政区域</p> 
      * @author foss-sunrui
      * @date 2012-10-16 下午5:27:34
      * @param dictrict
      * @return
      * @see
      */
     AdministrativeRegionsEntity queryByName(AdministrativeRegionsEntity dictrict);
    
    /**
     * <p>通过父级别行政区域代码获取子级别行政区域</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:27:40
     * @param parentDistCode
     * @return
     * @see
     */
     List<AdministrativeRegionsEntity> queryByParentDistCode(String parentDistCode);

}