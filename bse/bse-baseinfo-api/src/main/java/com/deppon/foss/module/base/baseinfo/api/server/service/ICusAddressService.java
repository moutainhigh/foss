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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ICusAddressService.java
 * 
 * FILE NAME        	: ICusAddressService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity;

/**
 * 客户接送货地址Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午1:44:28 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午1:44:28
 * @since
 * @version
 */
public interface ICusAddressService extends IService {
    
	 /**
	  * 新增散客接送货地址信息
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-22
	  *
	  */
     int addNonfixedCusAddress(CusAddressEntity entity);
	
    /**
     * 新增客户接送货地址
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午1:44:28
     * @param entity
     *            客户接送货地址实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addCusAddress(CusAddressEntity entity);

    /**
     * 根据code作废客户接送货地址
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:44:28
     * @param crmId
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteCusAddressByCode(BigDecimal crmId, String modifyUser);

    /**
     * 修改客户接送货地址
     * 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:44:28
     * @param entity
     *            客户接送货地址实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateCusAddress(CusAddressEntity entity);
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户接送货地址是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:15:29
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
     boolean queryCusAddressByCrmId(BigDecimal crmId,Date lastupdatetime);
     
     /**
      * <p>根据ownCustId查询客户接送货地址</p> 
      * @author css 
      * @date 2015-07-21 19:29:29
      * @param ownCustId    
      * @return
      * @see
      */
     CusAddressEntity queryCusAddressByOwnCustId(String ownCustId);
     
     /**
      * 修改客户接送货地址
      * @author css 
      * @date 2015-07-21 19:34:29
      * @param ownCustId    
      * @return
      * @see
      */
     int updateCusAddressByCusfossid(CusAddressEntity entity);

}
