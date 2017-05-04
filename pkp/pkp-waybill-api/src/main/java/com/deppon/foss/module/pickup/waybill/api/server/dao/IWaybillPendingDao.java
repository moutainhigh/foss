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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillPendingDao.java
 * 
 * FILE NAME        	: IWaybillPendingDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.DispatchEWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillCustomerDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaReceiveGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
/**
 * 
 * 运单暂存数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-31 下午3:39:09, </p>
 * @author foss-sunrui
 * @date 2012-10-31 下午3:39:09
 * @since
 * @version
 */
public interface IWaybillPendingDao {
    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);
    /**
     * 根据运单号删除数据
     * @param waybillNo
     * @return
     */
    int deleteByWaybillNo(String waybillNo);
    
    /**
     * 插入数据
     * @param record
     * @return
     */
    int insert(WaybillPendingEntity record);
    
    /**
     * 根据运单号更新数据
     * @param waybillNo
     * @return
     */
    int updateByWaybillNo(WaybillPendingEntity waybillPendingEntity);
    
    /**
     * 批量插入WaybillPendingEntity对象
     * @author 026123-foss-lifengteng
     * @date 2012-11-3 下午4:44:21
     */
    int addWaybillPendingEntityBatch(List<WaybillPendingEntity> pendingList);
    /**
     * 选择性插入数据
     * @param record
     * @return
     */
    int insertSelective(WaybillPendingEntity record);
    /**
     * 根据 id查询
     * @param id
     * @return
     */
    WaybillPendingEntity queryByPrimaryKey(String id);
    /**
     * 根据运单号查询
     * @param number
     * @return
     */
    WaybillPendingEntity queryByWaybillNumber(String number);
    /**
     * 选择性更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WaybillPendingEntity record);
    /**
     * 更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(WaybillPendingEntity record);
    
    /**
     * <p>
     * (查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     */
    List<WaybillPendingEntity> queryPending(WaybillPendingDto waybillPendingDto);
    List<WaybillPendingEntity> queryPendingNoExpress(WaybillPendingDto waybillPendingDto);
    
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
	WaybillPendingEntity queryPendingByNo(String mixNo);
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
	WaybillPendingEntity queryPendingByNoAndOrderNo(String mixNo, String orderNo);
	

	/**
	 * 查询PDA接货记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param args
	 * @return List<PdaReceiveGoodsDto>
	 */
	List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDto(Map<String, Object> args);

	/**
	 * 运单表中查询ＰＤＡ接货记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param args
	 * @return List<PdaReceiveGoodsDto>
	 */
	List<PdaReceiveGoodsDto> queryPdaWaybillReceiveGoodsDto(Map<String, Object> args);
	
	/**
	 * 根据运单号更新待处理运单状态为失效
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午5:03:55
	 */
	int updatePendingActiveByNo(String waybillNo);
	
	/**
	 * 通过运单号查询待补录运单明细
	 */
	WaybillPendingEntity queryWaybillPendingEntityNo(String waybillNo);
	
	/**
	 * 根据运单号获取待补录信息
	 * @author WangQianJin
	 * @date 2013-7-26 下午8:33:31
	 */
	WaybillPendingEntity getWaybillPendingEntityByWaybillNo(String waybillNo);
	WaybillPendingEntity queryIsExpressBill(String waybillNo, String productCode);
	
	/**
	 * 根据运单号删除待补录信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-20 下午2:13:08
	 */
	int deletePendingByWaybillNo(String waybillNo);
	
	/**
	 * 根据运单号和状态获取待补录信息
	 * @author WangQianJin
	 * @date 2014-01-22 下午13:47:31
	 */
	WaybillPendingEntity getPendingByWaybillNoAndType(String waybillNo,String pendingType);
	
	/**
	 * 运单表中查询ＰＤＡ接货记录
	 * @author 045738-maojianqiang
	 * @date 2014-05-05
	 * @param args
	 * @return List<PdaReceiveGoodsDto>
	 */
	List<PdaReceiveGoodsDto> queryPdaWaybillReceiveGoodsDtoByDate(Map<String, Object> args);
		
	/**
	 * 运单表中查询ＰＤＡ接货记录（快递）
	 * @author WangQianJin
	 * @date 2014-06-06
	 * @param args
	 * @return List<PdaReceiveGoodsDto>
	 */
	List<PdaReceiveGoodsDto> queryPdaExpressReceiveGoodsDtoByDate(Map<String, Object> args);


	/**
	 * 通过客户编码查询对应的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-25 19:54:47
	 * @param customerCodeList
	 * @return
	 */
	List<DispatchEWaybillDto> queryEWaybillByCust(EWaybillConditionDto eWaybillConditionDto);
	
	/**
	 * 通过司机工号和车牌号进行相关电子运单数据的扫描，主要统计每个客户对应的电子运单数量
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-25 19:31:35
	 * @param eWaybillConditionDto
	 * @return
	 */
	List<EWaybillCustomerDto> queryEWaybillBigCust(EWaybillConditionDto eWaybillConditionDto);
	
	/**
	 * 通过联系人编码查询对应联系方式
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-25 19:51:19
	 * @param eWaybillConditionDto
	 * @return
	 */
	EWaybillCustomerDto queryBigCustByCustContact(EWaybillConditionDto eWaybillConditionDto);
	
	/**
	 * 查询逾期为受理的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-2 09:15:49
	 */
	List<WaybillPendingEntity> queryOverDaysEWaybillData(int overDays, Date startDate, int start, int limited);
	
	/*
	 * 给官网提供的查询待激活电子运单详情方法
	 * @author FOSS-136334-BaiLei
	 */
	List<WaybillPendingEntity> queryUnactiveEWaybillInfoByCondition(Map<String, Object> args, boolean isRowBounds);
	
	/*
	 * 给官网提供的查询待激活电子运单数量方法
	 * @author FOSS-136334-BaiLei
	 */
	int countQueryUnactiveEWaybillInfoByCondition(Map<String, Object> args);
	
	/**
	 * 根据运单号查询该运单号是否存在
	 * @author BaiLei
	 * @date 2014-7-26 下午8:33:31
	 */
	int countQueryWaybillPendingByWaybillNO(String waybillNO);
	
	/**
	 * 获取电子运单号
	 */
	String getNextEWaybillNo();
	
	List<DispatchEWaybillDto> queryIndividualCustEwaybill(EWaybillConditionDto eWaybillConditionDto);
	
	/**
	 * 根据条件查询待激活的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-13 10:05:58
	 * @param args
	 * @return
	 */
	WaybillPendingEntity queryUnActiveEWaybillPendingByWaybilllNo(Map<String, Object> args);
	
	/**
	 * 根据条件查询待激活的电子运单数据
	 * @author Foss-liyongfei
	 * @param args
	 * @return
	 */
	WaybillPendingEntity queryUnActiveEWaybillPendingByOrderNo(Map<String, Object> args);
	
	/**
	 * 根据订单号/运单号查询waybillPending
	 * @param orderNo
	 * @param active
	 * @return
	 */
	WaybillPendingEntity queryEWaybillPendingByNo(String orderNo,String active);
	
	/**
	 * <p>根据条件进行电子面单数据的查询</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 11:15:50
	 * @param ewaybillConditionDto
	 * @param type 
	 * @return
	 */
	List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto);
	
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
	
	int deleteWaybillPendingEntityByOrderNo(Map<String, Object> params);
/**
	 * 查询暂存表pending中所有运单号
	 */
	String selectWaybillNo(String waybillNo);
	WaybillPendingEntity queryWaybillByWaybillNo(String waybillNo);
	int deleteByWaybillNos(List<String> waybillNos);

	
	/**
	 * 二程接驳项目-接送货-根据快递员工号查询当前未接货的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-29 09:02:46
	 * @param queryDto
	 * @return
	 */
	List<ExpressFeederPickupDetailDto> getExpressFeederPickupDetail(ExpressFeederPickupQueryDto queryDto);
	
	/**
	 * 根据运单号更新待补录运单交接状态
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-7 10:44:29
	 * @param maps
	 */
	int updateWaybillHandOverStatus(ExpressFeederPickupQueryDto queryDto);
	Long isExistPickUpDoneByWaybillNoList(ExpressFeederPickupQueryDto queryDto);
	
	List<WaybillPendingEntity> queryEWaybillPendingByParams(Map<String, Object> params);
	List<WaybillPendingEntity> queryWaybillPendingEntityByWaybillNoList(Map<String, Object> params);
	
	/**
	 * 新增运单
	 * @author Foss-265475-zoushengli
	 * @date 2016-3-10 10:44:29
	 * @param maps
	 */
	String addWaybillEntity(WaybillPendingEntity waybillPendingEntity);
	void addWaybillExpressEntity(WaybillPendingEntity waybillPendingEntity);
	 /**
     * 插入数据，包含waybillType字段
     * @param record
     * @return
     */
    int insertForWaybillType(WaybillPendingEntity record);
}