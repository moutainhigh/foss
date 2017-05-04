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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/IWaybillPendingHessianRemoting.java
 * 
 * FILE NAME        	: IWaybillPendingHessianRemoting.java
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
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureSendSmsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;

/**
 * 
 * （运单待处理表，hessian接口）
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-22 下午6:43:03
 */
public interface IWaybillPendingHessianRemoting extends IHessianService{

    /**
     * 运单暂存
     * @author 026113-foss-linwensheng
     * @date 2012-10-22 下午6:44:46
     */
    void saveWaybill(WaybillPendingDto waybillDto);
    
    /**
     * <p>
     * (查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
    List<WaybillPendingEntity> queryPending(WaybillPendingDto waybillPendingDto);
    List<WaybillPendingEntity> queryPendingExpress(WaybillPendingDto waybillPendingDto);
    
    /**
     * <p>
     * 更改运单状态PENDING
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
    int updatePendingActive(String id);
    
    /**
     * <p>
     * (按id查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	 WaybillPendingEntity queryPendingById(String id);
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在Pending
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	boolean isPendingExsits(String mixNo);

	/**
	 * 根据运单号查询待处理运单基本信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午8:39:53
	 */
	WaybillPendingEntity queryWaybillPendingByNo(String waybillNo);
	
	/**
	 * 根据运单号查询待处理运单dto
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午3:31:57
	 */
	WaybillPendingDto queryWaybillPendingDtoByNo(String waybillNo);
	
	/**
	 * 根据运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:03:45
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);

	/**
	 * @param mixNo
	 * @param orderNo
	 * @return
	 */
	boolean isPendingExsitsAndOrderNo(String mixNo, String orderNo);
	//获取待补录的图片运单
	WaybillPictureEntity getPictureWaybill(WaybillPictureEntity wpe);
	//获取待补录的图片运单的总数
	int getPictureWaybillCount(WaybillPictureEntity entity);
	//更新待补录的图片运单状态
	int updateWaybillPicture(WaybillPictureEntity wpe);

	int updatePictureWaybillByWaybillno(
			WaybillPictureEntity waybillPictureEntity);

	WaybillPictureEntity queryWaybillPictureByEntity(WaybillPictureEntity entity);
	
	/**
	 * 通过条件查询图片开单数据
	 * @param entity
	 * @return
	 */
	List<WaybillPictureEntity> queryWaybillPicturesByEntity(WaybillPictureEntity entity);
	int saveWaybillPushMessage(WaybillPushMessageEntity e);

	/**
	 * @author hehaisu
	 * 保存数据到推送消息数据表
	 */
	void addWaybillPicturePushMessage(WaybillPicturePushMessageEntity entity);
	
	/**
	 * @author hehaisu
	 * 保存数据到发送短信数据表
	 */
	void addWaybillPictureSendMessage(WaybillPictureSendSmsEntity entity);

	/**
	 * <p>根据条件进行电子面单数据的查询</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 11:15:50
	 * @param ewaybillConditionDto
	 * @param type 
	 * @return
	 */
	List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto, String type);

	/**
	 * 查询暂存运单信息表数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-22 15:45:34
	 * @param maps
	 * @return
	 */
	WaybillPendingEntity queryBasicWaybillPendingData(WaybillPendingDto waybillPendingDto);

	void deleteByWaybillNo(String waybillNo);

	WaybillPendingEntity queryWaybillByWaybillNo(String waybillNo);
	void deleteByWaybillNos(List<String> waybillNos);

	Date discontinueWaybillPicture(WaybillPictureEntity entity);
	/**
	 * 图片开单查询本地待补录运单数 
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:47:14
	 * @param entity
	 * @return
	 * @see
	 */
	int getPictureWaybillLocalCount(WaybillPictureEntity entity);
	/**
	 * 图片开单查询全国待补录运单数
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:47:32
	 * @param entity
	 * @return
	 * @see
	 */
	int getPictureWaybillAllCount(WaybillPictureEntity entity);
	/**
	 * 查询是否有可补录的订单
	 * by 352676
	 */
	WaybillPictureEntity getPictureWaybillIsExit(WaybillPictureEntity wpe);
}