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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IPickupAndDeliverySmallZoneDao.java
 * 
 * FILE NAME        	: IPickupAndDeliverySmallZoneDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewbirdinfoEntity;
import com.deppon.foss.ws.syncdata.CommonException;

/**
 * 菜鸟破损单据接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:css,date:2012-10-11 上午9:53:07
 * </p>
 * 
 * @author css
 * @date 2015-5-30 上午11:00:07
 * @since
 * @version
 */
public interface INewbirdinfoService  extends IService{
	
	
    /**
     * 根据传入对象查询符合条件所有菜鸟破损单信息
     * 
     * @author FOSS-css
     * @date 2015-06-03 下午16:39:57
     * @param entity
     *            菜鸟破损单实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<NewbirdinfoEntity> queryNewbirdinfo(
    		NewbirdinfoEntity entity, int limit, int start);

   

    /**
     * 统计总记录数
     * 
     * @author css
     * @date 2015-5-30 下午11:08:06
     * @param entity
     *            菜鸟破损单据信息
     * @return
     * @see
     */
    Long queryRecordCount(NewbirdinfoEntity entity);   
    
    /**
     * 插入一条菜鸟破损单信息
     * 
     * @author css
     * @date 2015-5-30 下午11:08:06
     * @param entity
     *            菜鸟破损单据信息
     * @return
     * @see
     */
    int addNewbirdinfo(NewbirdinfoEntity entity);
    
    /**
     * 判断是否为淘宝订单 
     * @author 261997-foss-css
     * @date 2015-6-4 上午8:56:49
     * @param string 运单号
     * @return
     * @see
     */
    boolean isBoolTaoBao(String str);
    
    /**
     * <p>接口获取信息</p>.
     *
     * @param String 
     * @return 
     * @throws CommonException 
     * @author 261997-foss-css
     * @date 2015-6-8 上午9:30:43     
     */
    NewbirdinfoEntity syncNewbirdinfoInfo(String waybillno)throws CommonException;
    
    
    /**
     * <p>同步信息到接送货</p>.
     *
     * @param String 
     * @return 
     * @throws CommonException 
     * @author 261997-foss-css
     * @date 2015-6-19 上午12:30:43     
     */
    void updateWaybillInfo(NewbirdinfoEntity newbirdinfo1) ;
    
    
    /**
     * 反写联系人，电话到运单表方法
     * 
     * @author css
     * @date 2015-10-30 上午10:13:06
     * @param map
     * @return
     * @see
     */
    int updateWaybillPhone(Map<String, Object> map);
    
    /**
     * 反写联系人，手机号码到运单表方法
     * 
     * @author css
     * @date 2015-10-30 上午10:13:06
     * @param map
     * @return
     * @see
     */
    int updateWaybillMobilephone(Map<String, Object> map);
    
    
    
}
