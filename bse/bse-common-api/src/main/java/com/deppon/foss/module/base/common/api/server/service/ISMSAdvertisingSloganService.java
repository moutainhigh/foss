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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/ISMSAdvertisingSloganService.java
 * 
 * FILE NAME        	: ISMSAdvertisingSloganService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;

/**
 * 短信广告语service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午4:56:25 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 下午4:56:25
 * @since
 * @version
 */
public interface ISMSAdvertisingSloganService extends IService {
    
    /**
     * <p>新增短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:56:25
     * @param entity
     * @return
     * @see
     */
    int addSMSAdvertisingSlogan(SMSSloganEntity entity) ;
    
    /**
     * <p>根据code作废短信广告语信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:56:25
     * @param codeStr
     * @return
     * @see
     */
    int deleteSMSAdvertisingSloganByCode(String codeStr,String modifyUser);
    
    /**
     * <p>修改短信广告语信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:56:25
     * @param entity
     * @return
     * @see
     */
    int updateSMSAdvertisingSlogan(SMSSloganEntity entity);
    
    /**
     * <p>根据传入对象查询符合条件所有短信广告语信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:56:25
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<SMSSloganEntity> querySMSAdvertisingSlogans(SMSSloganEntity entity,int limit,int start);
    
    /**
     * <p>统计总记录数</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:56:25
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(SMSSloganEntity entity);
    
    /**
     * <p>根据短信广告语代码查询短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param smsSloganCode 短信广告语代码
     * @param smsId 短信广告语ID（该参数可以为空）
     * @return
     * @see
     */
    SMSSloganEntity querySmsSloganBySmsSloganCode(String smsSloganCode,String smsId);
    
    /**
     * <p>根据短信广告语名称查询短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param sloganName 短信广告语名称
     * @param smsId 短信广告语ID（该参数可以为空）
     * @return
     * @see
     */
    SMSSloganEntity querySmsSloganBySmsSloganName(String sloganName,String smsId);
    
    /**
     * <p>根据短信广告语代码、部门编码查询短信广告语，若部门不存在广告语，查询上级部门的广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:24:10
     * @param smsSloganCode 短信广告语代码
     * @param orgCode 部门编码
     * @return 短信广告语
     * @see
     */
    String querySmsSloganContent(String smsSloganCode,String orgCode);

}
