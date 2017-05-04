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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IBillAdvertisingSloganDao.java
 * 
 * FILE NAME        	: IBillAdvertisingSloganDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;

/**
 * 单据广告语Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-xieyantao,date:2012-10-11 下午2:34:35</p>
 * @author dp-xieyantao
 * @date 2012-10-11 下午3:46:07
 * @since
 * @version
 */
public interface IBillAdvertisingSloganDao {
    
    /**
     * 新增单据广告语 
     * @author dp-xieyantao
     * @date 2012-10-11 下午3:46:07
     * @param entity
     * @return
     * @see
     */
    int addBillAdvertisingSlogan(BillSloganEntity entity) ;
    
    /**
     * 根据code作废单据广告语信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午3:46:07
     * @param codes
     * @return
     * @see
     */
    int deleteBillAdvertisingSloganByCode(String[] codes,String modifyUser);
    
    /**
     * 修改单据广告语信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午3:46:07
     * @param entity
     * @return
     * @see
     */
    int updateBillAdvertisingSlogan(BillSloganEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有单据广告语信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午3:46:07
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<BillSloganEntity> queryBillAdvertisingSlogans(BillSloganEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-11 下午3:40:01
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(BillSloganEntity entity);
    
    /**
     * <p>根据单据广告语代码查询单据广告语信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-4 上午10:57:25
     * @param billSloganCode
     * @param sloganId 单据广告语ID
     * @return
     * @see
     */
    BillSloganEntity queryBillSloganContent(String billSloganCode,String sloganId);
    
    /**
     * <p>根据单据广告语名称查询单据广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param sloganName 单据广告语名称
     * @param sloganId 单据广告语ID
     * @return
     * @see
     */
    BillSloganEntity queryBillSloganBySmsSloganName(String sloganName,String sloganId);
    
    
}
