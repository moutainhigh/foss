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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/ISynchronousExternalSystemService.java
 * 
 * FILE NAME        	: ISynchronousExternalSystemService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import java.util.List;

import com.deppon.esb.inteface.domain.vehicle.SiteInfo;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
/**
 * 用来操作交互“同步FOSS数据信息”给“外围系统”的应数据Service接口：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-26 上午10:32:03</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-26 上午10:32:03
 * @since
 * @version
 */
public interface ISynchronousExternalSystemService extends IService {

    /**
     * <p>同步FOSS的“外场信息”给LMS系统</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-26 上午10:32:07
     * @param siteInfoList “外场信息”封装实体列表
     * @throws SynchronousExternalSystemException
     * @see
     */
    public void synchronizeOutfieldDataToLms(List<SiteInfo> siteInfoList) throws SynchronousExternalSystemException;
    
    
    
    /**
     * <p>同步FOSS的“外场信息”给LMS系统</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-26 上午10:32:07
     * @param siteInfoList “外场信息”封装实体列表
     * @throws SynchronousExternalSystemException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.ISynchronousExternalSystemService#synchronizeOutfieldDataToLms(java.util.List)
     */
}
