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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillPendingService.java
 * 
 * FILE NAME        	: IWaybillPendingService.java
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureSendSmsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;

/**
 * 
 * （运单待处理保存）
 * @author 026113-foss-linwensheng
 * @date 2012-10-22 下午7:42:42
 */
public interface IWaybillPendingService {
    /**
     * 
     * <p>通过运单号码删除</p> 
     * @author foss-sunrui
     * @date 2012-10-31 下午3:51:46
     * @param waybillNo
     * @return
     * @see
     */
    int deleteByWaybillNo(String waybillNo);
    
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
	
    List<WaybillPendingEntity> 
    	queryPendingNoExpress(WaybillPendingDto waybillPendingDto);
    
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
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
    boolean isPendingExsits(String mixNo);
    /**
  	 * 
  	 * <p>
  	 * 通过运单号/订单号判断运单是否存在
  	 * </p>
  	 * @author foss-yangtong
  	 * @date 2012-10-30 下午7:44:25
  	 * @return
  	 * @see 
  	 */
    boolean isPendingExsitsAndOrderNo(String mixNo, String orderNo);
    

	/**
	 * 根据运单号查询待处理运单基本信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午8:46:24
	 */
	WaybillPendingEntity queryPendingByNo(String waybillNo);

	/**
	 * 通过运单ID查询待处理运单付款信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午10:33:17
	 */
	List<WaybillPaymentPendingEntity> queryPaymentPendingByNo(String waybillNo);

	/**
	 * 通过运单ID查询待处理运单折扣信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:07:40
	 */
	List<WaybillDisDtlPendingEntity> queryDisDtlPendingByNo(String waybillNo);

	/**
	 * 通过运单ID查询待处理费用信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:53:24
	 */
	List<WaybillCHDtlPendingEntity> queryCHDtlPendingByNo(String id);

	/**
	 * 通过运单号查询待处理运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:57:48
	 */
	WaybillPendingDto queryPendingWaybillByNo(String waybillNo);
	
	/**
	 * 
	 * 插入赛处理运单信息
	 * @author foss-sunrui
	 * @date 2013-3-22 下午1:13:00
	 * @param waybillPending
	 * @param systemDto
	 * @param isUpdate
	 * @see
	 */
	void addWaybillPending(WaybillPendingEntity waybillPending,
			WaybillSystemDto systemDto, boolean isUpdate);

	/**
	 * 批量插入待处理运单折扣信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 上午8:47:07
	 */
	void addWaybillDisDtlPending(List<WaybillDisDtlPendingEntity> discoutDetail, WaybillSystemDto systemDto, boolean isUpdate);

	/**
	 * 根据运单号查询打木架信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 上午9:26:55
	 */
	WoodenRequirePendingEntity queryWoodenRequireByNo(String waybillNo);

	/**
	 * 根据运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:01:04
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);

	/**
	 * 根据运单号更新待处理运单记录状态
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午5:12:02
	 */
	int updatePendingActiveByNo(String waybillNo);
	/**
	 * 根据主键id修改待处理运单信息
	 * @author foss-meiying
	 * @date 2013-1-22 下午2:22:48
	 * @param record
	 * @return
	 * @see
	 */
	int updateByPrimaryKeySelective(WaybillPendingEntity record);

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-sunrui
	 * @date 2013-3-22 下午1:13:57
	 * @param chargeDetail
	 * @param systemDto
	 * @param isUpdate
	 * @see
	 */
	void addWaybillChDtlPending(List<WaybillCHDtlPendingEntity> chargeDetail,
			WaybillSystemDto systemDto, boolean isUpdate);

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-sunrui
	 * @date 2013-3-22 下午1:14:00
	 * @param paymentList
	 * @param systemDto
	 * @param isUpdate
	 * @see
	 */
	void addWaybillPaymentPending(
			List<WaybillPaymentPendingEntity> paymentList,
			WaybillSystemDto systemDto, boolean isUpdate);

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-sunrui
	 * @date 2013-3-22 下午1:14:03
	 * @param woodenRequire
	 * @param systemDto
	 * @param isUpdate
	 * @see
	 */
	void addWoodenRequirePending(WoodenRequirePendingEntity woodenRequire,
			WaybillSystemDto systemDto, boolean isUpdate);

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-sunrui
	 * @date 2013-3-22 下午1:15:16
	 * @param waybillDto
	 * @see
	 */
	void addPendingWaybill(WaybillPendingDto waybillDto);
	
	/**
	 * 通过运单获取待补录运单明细
	 */
	WaybillPendingEntity queryWaybillPendingEntityNo(String waybillNo);
	
	/**
	 * 根据运单号获取待补录信息
	 * @author WangQianJin
	 * @date 2013-7-26 下午8:33:31
	 */
	WaybillPendingEntity getWaybillPendingEntityByWaybillNo(String waybillNo);

	boolean queryIsExpressBill(String waybillNo, String productCode);
	WaybillPictureEntity getPictureWaybill(WaybillPictureEntity wpe);

	int getPictureWaybillCount(WaybillPictureEntity entity);

	int updateWaybillPicture(WaybillPictureEntity wpe);

	int updatePictureWaybillByWaybillno(
			WaybillPictureEntity waybillPictureEntity);
	
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlPendingEntity queryActiveInfoByNoAndType(String waybillNo);
	/**
	 * 查询超期 电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-18 11:18:51
	 * @param overDays
	 * @param startDate
	 * @param start
	 * @param limited
	 * @return
	 */
	List<WaybillPendingEntity> queryOverDaysEWaybillData(int overDays, Date startDate, int start, int limited);
	
	/**
	 * 根据运单号查询该运单号是否可用
	 * @author BaiLei
	 * @date 2014-7-26 下午8:33:31
	 */
	boolean isExsitWaybillPending(String waybillNO);	

	/*
	 * 	根据条件查询电子运单相关信息（官网专用）
	 *  @创建时间 2014-9-2 下午8:34:33   
	 *  @创建人： 136334 - BaiLei
	 * 
	 */
	Map<String, Object> queryUnactiveEWaybillInfos(Map<String, Object> args);

	/**
	 * 获取电子运单号
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-13 10:05:58
	 * @param args
	 * @return
	 */
	String getNextEWaybillNo();	
	
	/**
	 * 根据条件查询待激活的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-13 10:05:58
	 * @param args
	 * @return
	 */
	WaybillPendingEntity queryUnActiveEWaybillPendingByWaybilllNo(Map<String, Object> args);

	/**
	 * 检查补录时电子运单号码是否需要校验
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */	
	boolean checkEWaybillPending(String waybillNo);
	
	/**
	 * 根据条件查询待激活的电子运单数据
	 * @author Foss-liyongfei
	 * @param args
	 * @return
	 */
	WaybillPendingEntity queryUnActiveEWaybillPendingByOrderNo(Map<String, Object> args);
	WaybillPictureEntity queryWaybillPictureByEntity(WaybillPictureEntity entity);
	/**
	 * 根据条件查询图片开单数据
	 * @param entity
	 * @return
	 */
	List<WaybillPictureEntity> queryWaybillPicturesByEntity(WaybillPictureEntity entity);
	int saveWaybillPushMessage(WaybillPushMessageEntity e);
	List<WaybillPushMessageEntity> queryWaybillPushMessageEntity(int min);

	void updateByPrimaryKey(WaybillPendingEntity pendingEntity);
	
	/**
	 * 插入推送消息表
	 * @param entity
	 */
	void insertWaybillPicturePushMessage(WaybillPicturePushMessageEntity entity);
	/**
	 * 插入短信发送表
	 * @param entity
	 */
	void insertWaybillPictureSendMessage(WaybillPictureSendSmsEntity entity);
	
	void batchExecutePicturePushJobs();
	
	void batchExecutePicturePushMessageJobs();
	
	void batchExecutePictureSendSmsJobs();

		List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto, String type);

	/**
	 * 查询暂存运单信息表数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-22 15:45:34
	 * @param maps
	 * @return
	 */
	WaybillPendingEntity queryBasicWaybillPendingData(WaybillPendingDto waybillPendingDto);
	
	/**
     * 返货开单页面，根据原单号在暂存表查询运单号
     * @param originalWaybillNo
     * @return 
     */
	String getWaybillNo(String originalWaybillNo);
	
	String selectWaybillNo(String waybillNo);

	WaybillPendingEntity queryWaybillByWaybillNo(String waybillNo);

	void savePengdingWaybill(List<WaybillPendingEntity> pendingList);

	void batchAddWoodenRequirePending(
			List<WoodenRequirePendingEntity> woodenRequire);

	void batchAddLabeledGood(List<LabeledGoodEntity> labelGoodList);

	List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo);

	int deleteByWaybillNos(List<String> waybillNos);

	void deleteWoodByWaybillNos(List<String> waybillNos);

	void deleteWoodByWaybillNo(String waybillNo);
	
	List<WaybillPendingEntity> queryEWaybillPendingByParams(Map<String, Object> params);
	
	void updateWaybillByWaybillNo(WaybillEntity waybill, String oldWaybillNo);
	
	public void updateWaybillActiveByWaybillNo(WaybillEntity waybillEntity) ;
	
	List<WaybillPendingEntity> queryWaybillPendingEntityByWaybillNoList(List<String> waybillNoList);
	/**
	 * 图片开单查询本地待补录运单数 
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:51:56
	 * @param entity
	 * @return
	 * @see
	 */
	int getPictureWaybillLocalCount(WaybillPictureEntity entity);
	/**
	 * 图片开单查询全国待补录运单数 
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:52:02
	 * @return
	 * @see
	 */
	int getPictureWaybillAllCount(WaybillPictureEntity entity);
	/**
	 * 查询是否有可补录的订单
	 * by 352676
	 */
	WaybillPictureEntity getPictureWaybillIsExit(WaybillPictureEntity wpe);
	/**
	 * 图片开单分单逻辑加锁处理
	 * @author Foss-352676-YUANHB
	 * @date 2017年4月21日21:53:59
	 * @param wpe
	 * @return
	 */
	WaybillPictureEntity getPictureWaybillInLock(WaybillPictureEntity wpe);
}