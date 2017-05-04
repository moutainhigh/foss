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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IDataDictionaryValueService.java
 * 
 * FILE NAME        	: IDataDictionaryValueService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;



/**
 * 数据字典值服务接口
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午3:40:46
 */
public interface IDataDictionaryValueService {
	
	/**
	 * 插条记录
	 */
	void addDataDictionaryValue(DataDictionaryValueEntity dataDictionaryValue);
	
	/**
	 * 更新条记录
	 */
	void updateDataDictionaryValue(DataDictionaryValueEntity dataDictionaryValue);
	
	/**
	 * 
	 * 新增或更新记录
	 */
	void saveOrUpdate(DataDictionaryValueEntity dataDictionaryValue);
	
	/**
     * <p>
     * 通过词条代码获取数据集合
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:13:45
     * @param termsCode
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryByTermsCode(String termsCode);

    /**
     * <p>
     * 查询提货方式-空运
     * </p>
     * 
     * @author 025000-FOSS-helong
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryPickUpGoodsAir();

    /**
     * 
     * （查询提货方式-汽运）
     * 
     * @author 025000-FOSS-helong
     * @date 2012-10-22 下午04:09:11
     */
    List<DataDictionaryValueEntity> queryPickUpGoodsHighWays();
    
    /**
	 * 
	 * （查询行业货源品类）
	 * 
	 * @author zhangchengfu
	 * @date 2015-6-5
	 */
	List<DataDictionaryValueEntity> queryIndustrySourceCategory4Dict();
	
	/**
	 * 
	 * （查询客户分群）
	 * 
	 * @author zhangchengfu
	 * @date 2015-6-5
	 */
	List<DataDictionaryValueEntity> queryFlabelleavemonth4Dict();

    /**
     * <p>
     * 查询对外备注
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryOuterRemark();

    /**
     * <p>
     * 查询付款方式
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryPaymentMode();

    /**
     * <p>
     * 查询退款类型
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryRefundType();

    /**
     * <p>
     * 查询返单类型
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryReturnBillType();

    /**
     * <p>
     * 查询计费方式
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryBillingWay();

    /**
     * 
     * 查询预配航班
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-3 下午3:03:20
     */
    List<DataDictionaryValueEntity> queryPredictFlight();

    /**
     * 
     * 根据code查询数据字典项
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-6 上午11:16:38
     */
    DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode, String valueCode);

    /**
     * 
     * 内部变更类型
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-8 下午5:36:24
     */
    List<DataDictionaryValueEntity> queryInsideChangeType();

    /**
     * 
     * 客户变更类型
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-8 下午5:36:35
     */
    List<DataDictionaryValueEntity> queryCustomerChangeType();

    /**
     * 
     * 订单类型
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-13 上午10:58:37
     */
    List<DataDictionaryValueEntity> queryOrderType();

    /**
     * 
     * 订单受理状态
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-13 上午10:58:45
     */
    List<DataDictionaryValueEntity> queryOrderStatus();
    
    /**
     * 
     * 运单状态PENDING
     * 
     * @author 105089-foss-yangtong
     * @date 2012-11-13 上午10:58:45
     */
    List<DataDictionaryValueEntity> queryPendingType();
    
    /**
     * 
     * 运单状态OFFLINE
     * 
     * @author 105089-foss-yangtong
     * @date 2012-11-13 上午10:58:45
     */
    List<DataDictionaryValueEntity> queryOfflineActive();

    /**
     * 
     * 货物状态
     * @author 102246-foss-shaohongliang
     * @date 2012-11-23 上午10:02:38
     */
	List<DataDictionaryValueEntity> queryGoodsStatus();
	
	
	/**
	 * 
	 * 查询合票方式
	 * @author Administrator
	 * @date 2012-12-19 下午04:26:08
	 */
	List<DataDictionaryValueEntity> queryFreightMethod();
	
	/**
	 * 
	 * 查询空运货物类型
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 下午04:27:30
	 * @return
	 */
	List<DataDictionaryValueEntity> queryAirGoodsType();
	/**
	 * 删除
	 * @param dataDictionaryValue
	 */
	void delete(DataDictionaryValueEntity dataDictionaryValue) ;

	/**
	 * 查询发货方式
	 * @return
	 */
	List<DataDictionaryValueEntity> queryDeliveryMode();

	List<DataDictionaryValueEntity> queryRfcCusReason();

	/**
	 * 查询特殊增值服务
	 * @return
	 */
	List<DataDictionaryValueEntity> querySpecialValueAddedServiceMode();
}