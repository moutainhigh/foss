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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISalesMotorcadeDao.java
 * 
 * FILE NAME        	: ISalesMotorcadeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;


/**
 * 
 * 营业部集中开单组关系 DAO接口
 * @author foss-zhujunyong
 * @date Apr 26, 2013 3:52:26 PM
 * @version 1.0
 */
public interface ISalesBillingGroupDao {

    /**
     * 
     * <p>添加单条营业部和集中开单组关系</p> 
     * @author foss-zhujunyong
     * @date Apr 26, 2013 3:53:39 PM
     * @param entity
     * @return
     * @see
     */
    SalesBillingGroupEntity addSalesBillingGroup(SalesBillingGroupEntity entity);

    /**
     * 
     * <p>按虚拟编码作废单条</p> 
     * @author foss-zhujunyong
     * @date Apr 27, 2013 5:03:35 PM
     * @param virtualCode
     * @return
     * @see
     */
    SalesBillingGroupEntity deleteSalesBillingGroupByVirtualCode(SalesBillingGroupEntity entity);

    /**
     * 
     * <p>按营业部部门编码作废营业部和集中开单组关系</p> 
     * @author foss-zhujunyong
     * @date Apr 27, 2013 5:09:11 PM
     * @param salesCode
     * @return
     * @see
     */
    int deleteSalesBillingGroupBySalesCode(SalesBillingGroupEntity entity);

    /**
     * 
     * <p>按集中开单组部门编码作废营业部和集中开单组关系</p> 
     * @author foss-zhujunyong
     * @date Apr 27, 2013 5:09:32 PM
     * @param billingGroupCode
     * @return
     * @see
     */
    int deleteSalesBillingGroupByBillingGroupCode(SalesBillingGroupEntity entity);
    
    /**
     * 
     * <p>根据营业部部门编码和集中开单组部门编码作废一笔实体</p> 
     * @author foss-zhujunyong
     * @date Apr 28, 2013 2:59:22 PM
     * @param entity
     * @return
     * @see
     */
    int deleteSalesBillingGroupBySalesBillingGroupCode(SalesBillingGroupEntity entity);
    
    /**
     * 
     * <p>根据集中开单组部门编码查找营业部列表</p> 
     * @author foss-zhujunyong
     * @date Apr 27, 2013 4:45:16 PM
     * @param billingGroupCode
     * @return
     * @see
     */
    List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(String billingGroupCode);
    
    /**
     * 
     * <p>根据集中开单组部门编码和营业部编码查找营业部与集中开单组关系</p> 
     * @author foss-zhujunyong
     * @date Apr 27, 2013 4:45:16 PM
     * @param billingGroupCode
     * @return
     * @see
     */
    SalesBillingGroupEntity querySalesListByBillingGroupCodeAndSalesCode(String billingGroupCode,String salesCode);
    
    /**
     * 
     * <p>根据营业部部门编码查找集中开单组列表</p> 
     * @author foss-zhujunyong
     * @date Apr 27, 2013 4:45:19 PM
     * @param salesCode
     * @return
     * @see
     */
    List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String salesCode);

    /**
     * 
     * <p>下载营业部和集中开单组关系表</p> 
     * @author foss-zhujunyong
     * @date May 2, 2013 11:28:36 AM
     * @param entity
     * @return
     * @see
     */
    List<SalesBillingGroupEntity> querySalesBillingGroupListForDownload(SalesBillingGroupEntity entity);

}
