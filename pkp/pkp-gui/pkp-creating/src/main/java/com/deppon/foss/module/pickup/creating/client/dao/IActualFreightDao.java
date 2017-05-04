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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/IActualFreightDao.java
 * 
 * FILE NAME        	: IActualFreightDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;

/**
 * 
 * 运单状态数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-30 下午5:43:53,content</p>
 * @author foss-sunrui
 * @date 2012-10-30 下午5:43:53
 * @since
 * @version
 */
public interface IActualFreightDao {
    
    /**
     * 
     * <p>插入一条记录</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午5:44:49
     * @param record
     * @return
     * @see
     */
    int insertSelective(ActualFreightEntity record);

    /**
     * 
     * <p>按主键查询记录</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午5:45:07
     * @param id
     * @return
     * @see
     */
    ActualFreightEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>按运单号码查询记录</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午5:45:07
     * @param id
     * @return
     * @see
     */
    ActualFreightEntity queryByWaybillNo(String waybillNo);

    /**
     * 
     * <p>根据主键更新</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午5:45:25
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(ActualFreightEntity record);
    
    /**
     * 根据运单号减去该运单已生成的到达联件数
     * @author 097972-foss-dengtingting
     * @date 2012-11-13 下午5:33:11
     */
    int updateSubGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity);
    
    /**
     * 根据运单号增加该运单已生成的到达联件数
     * @author 097972-foss-dengtingting
     * @date 2012-12-7 上午9:51:01
     */
    int updateAddGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity);
    
    /**
     * 根据运单号更改运单已生成的到达联
     * @author 097972-foss-dengtingting
     * @date 2012-12-7 上午10:16:36
     */
    int updateGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity);
    
    /**
     * 
     * <p>
     * 新增运单附件信息<br />
     * </p>
     * @author suyujun
     * @version 0.1 2012-11-29
     * @param entity
     * @return
     * int
     */
    int insertActualFreightEntity(ActualFreightEntity entity);
    /**
     * 根据运单号更新该运单到达未出库件数
     * @author foss-meiying
     * @date 2012-12-5 下午3:17:47
     * @param waybillNo 运单号
     * @param signGoodsQty 签收件数
     * @return
     * @see
     */
     int updateArriveNotoutGoodsQtyByWaybillNo(String waybillNo,Integer signGoodsQty);
     
     /**
      * 
      * 根据运单号
      * 更新ActualFreight中的“到达未出库件数”=“到达未出库件数”+“作废到达联的签收件数”，
		“已生成到达联件数”=“已生成到达联件数”-“作废到达联的件数”
      * @author foss-meiying
      * @date 2012-12-5 下午3:17:47
      * @param actualFreightEntity.waybillNo 运单号
      * @param actualFreightEntity.arriveNotoutGoodsQty 作废到达联的签收件数
      * @param actualFreightEntity.generateGoodsQty 作废到达联的件数
      * 
      * @return
      * @see
      */
      int updateActualFreightByWaybillNo(ActualFreightEntity actualFreightEntity);
      
      /**
       * 
       * 根据单号更新结清状态
       * @author 043258-foss-zhaobin
       * @date 2012-12-20 下午7:34:20
       */
      void updateActualFreightByNo(ActualFreightEntity actualFreightEntity);
}