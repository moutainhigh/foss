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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/ISMSAdvertisingSloganForDeptDao.java
 * 
 * FILE NAME        	: ISMSAdvertisingSloganForDeptDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity;

/**
 * 部门短信广告语Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午2:34:35 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 下午3:24:04
 * @since
 * @version
 */
public interface ISMSAdvertisingSloganForDeptDao {
    
    /**
     * 新增部门短信广告语 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:24:04
     * @param entity
     * @return
     * @see
     */
    int addSMSAdvertisingSloganForDept(SloganAppOrgEntity entity) ;
    
    /**
     * 根据code作废部门短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:24:04
     * @param codes 这里是ID拼接的字符
     * @return
     * @see
     */
    int deleteSMSAdvertisingSloganForDeptByCode(String[] codes,String modifyUser);
    
    /**
     * 修改短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:24:04
     * @param entity
     * @return
     * @see
     */
    int updateSMSAdvertisingSloganForDept(SloganAppOrgEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有部门短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:24:04
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<SloganAppOrgEntity> querySMSAdvertisingSloganForDepts(SloganAppOrgEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:24:04
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(SloganAppOrgEntity entity);
    
    /**
     * <p>根据短信广告语虚拟编码、部门编码查询部门短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param orgCode 部门编码
     * @param smsSloganVirtualCode 短信广告语虚拟编码
     * @param sloganAppOrgId 部门短信广告语ID
     * @return
     * @see
     */
    public SloganAppOrgEntity querySloganAppOrgByCode(String orgCode,String smsSloganVirtualCode,String sloganAppOrgId);
    
    
}
