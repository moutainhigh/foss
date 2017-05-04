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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillQueryService.java
 * 
 * FILE NAME        	: IWaybillQueryService.java
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

import java.util.List;
import java.util.Map;
import java.util.Date;

import com.deppon.esb.inteface.domain.ccwaybillservice.WaybillCountDto;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.SimpleWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.BillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportBillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOneYearDto;

/**
 * 用于运单和更改单查询
 * 
 * @author 026113-foss-linwensheng
 * 
 */
public interface IWaybillQueryService extends IService {
	/**
	 * 根据不同的查询条件返回运单单号集合 ： 1、 输入参数中“运单号”优先级最高，如果起始运单号和结束运单号不为空，则其他条件可以忽略
	 * 2、输入参数如果为Null，则不作为查询条件 3、 列表最大返回行数3000行
	 * 
	 * @param waybillListByComplexCondationDto
	 * @return waybillNos
	 */
	List<String> queryWaybillListByComplexCondation(WaybillListByComplexCondationDto waybillListByComplexCondationDto);

	/**
	 * 根据运单号查询运单出发更改单信息
	 * 
	 * @param waybillNo
	 * @return
	 */
	List<LeaveChangeByWaybillNoResultDto> queryLeaveChangeByWaybillNo(String waybillNo);

	/**
	 * 根据不同的查询条件组合查询结果列表，结果为封装为dto的集合
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param condition
	 * @return List<SalesStatementByComplexCondationResultDto>
	 */
	Map<String, Object> querySalesStatementByComplexCondation(WaybillListByComplexCondationDto condition, int start, int limit);

	/**
	 * 
	 * 根据不同的查询条件组合查询结果列表，结果为封装为dto的集合
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param condition
	 * @return List<SalesStatementByComplexCondationResultDto>
	 */
	List<SalesStatementByComplexCondationResultDto> queryBillListByComplexCondation(WaybillListByComplexCondationDto condition);

	/**
	 * 根据运单号查询运单信息
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	WaybillInfoByWaybillNoReultDto queryWaybillInfoByWaybillNo(String waybillNO);

	/**
	 * 分页查询结果，官网
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param args
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryWaybillInfos(Map<String, Object> args);
	
	/**
	 * APP分页根据手机号分页查询我的发货/收货信息
	 * @author WangQianJin
	 * @date 2014-07-19
	 * @param args
	 * @return
	 */
	Map<String, Object> queryAppWaybillInfos(Map<String, Object> args);

	/**
	 * 根据查询条件查询执行计划
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	List<AdjustPlanResultDto> queryAdjustPlan(AdjustPlanSearcherDto adjustPlanSearcherDto,int start,int limit);

	/**
	 * 
	 * 查询记录数
	 * 
	 * @param adjustPlanSearcherDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 16, 2013 2:16:30 PM
	 */
	Long queryAdjustPlanCount(AdjustPlanSearcherDto adjustPlanSearcherDto);

	/**
	 * 
	 * 根据运单集合返回运单详细信息
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-6 下午5:21:02
	 */
	List<WaybillInfoDto> queryWaybillInfo(List<String> waybillList);
	
	/**
	 * 
	 * 根据运单集合返回运单详细信息
	 * @author 234773-xulixin
	 * @date 2015-07-02 10:14:03
	 */
	List<WaybillInfoDto> queryWaybillInfoForQms(List<String> waybillList);
	
	
	/**
	 * 根据运单号或运单号流水号查询走货路径明细
	 * @author 234773-xulixin
	 * @date 2015-08-20 9:46:03
	 * @param waybill
	 * @return
	 */
	String queryPathDetailByNos(String waybill);
	
	/**
	 * 
	 * 根据运单号查看该运单是否快递
	 * @author 234773-xulixin
	 * @date 2015-07-11 17:14:03
	 */
	String getIsExpressByWayBillNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 查询运单状态<br />
	 * </p>
	 * 
	 * @author 苏玉军
	 * @version 0.1 2013-1-7
	 * @param waybillNo
	 * @return String
	 */
	String queryWaybillStatus(String waybillNo);
	
	/**
	 * 
	 * 自定义查询
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-1 上午9:48:34
	 */
	List<SimpleWaybillDto> queryWayBillListByUserDefinedQuery(String where, int count);
	/**
	 * 
	 * 自定义查询
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-4 上午9:48:34
	 */
	Map<String, Object> queryActiveEWaybillInfos(Map<String, Object> args);

	
	/**
	 * 根据客户编码，开始时间，结束时间查询运单详细信息
	 * @author 198719-foss - 叶涛
	 * @date 2014-10-13 下午4:30:02
	 */
	List<WaybillOneYearDto> queryWaybillInfoOneYear(String deliveryCustomerCode,Date startTime,Date endTime);
	
	/**
	 * @param codition: 封装的Map对象，其中key 分别为：startDate 开始时间，endDate 结束时间，
	 * phones 拼装好的客户电话字符串，mobilePhones 拼装好的客户手机字符串，isAssociatedConsignee 是否关联发货人
	 * @author liyongfei
	 * @date 2014-07-31
	 */
	List<WaybillCountDto> queryWaybillByPhone(Map<String,Object> condition);
	/**
	 * APP根据用户电话号码查询用户相应时间内的费用信息
	 * @author yetao
	 * @date 2014-11-7
	 */
	ComponentDto queryComponentDetail(ReportComponentDto reportComponentDto);
	
	/**
	 * APP根据用户电话号码查询相应月份的电子账单
	 * @author 198719-foss-yetao
	 */
	List<BillingDto> queryBillingDetail(ReportBillingDto reportBillingDto);

	/**
	 * 打木架外场部门标杆编码
	 * @param waybillNo
	 * @return
	 */
	String getWoodenPackageOrgCode(String waybillNo);

	/**
	* 官网查询子母件
	* 
	* @Method: queryWaybillInfoForOfficialCP 
	* @param waybillList
	* @return List<WaybillInfoDto>
	* @author WangZengming
	* @date 2015-9-1 下午2:19:45
	* @see
	*/
	List<WaybillInfoDto> queryWaybillInfoForOfficialCP(List<String> waybillList);
	
	/**
	* 根据运单集合返回运单详细信息
	* 
	* @Method: queryWaybillInfoForSOC 
	* @param waybillList
	* @return List<WaybillInfoDto>
	* @author WangZengming
	* @date 2015-8-31 下午2:43:51
	* @see
	*/
	List<WaybillInfoDto> queryWaybillInfoForSOC(List<String> waybillList);
	
	/**
	 * APP分页查询Foss的发货/收货信息,包含子母件或正常件标示
	 * @param params 请求参数
	 * @return map键值对 count：记录数；list:AppWayBillDetaillVo结果集
	 * @author 272311
	 * 2015.09.06
	 */
	Map<String,Object> queryAppWaybillInfosExp(Map<String, Object> params);
	
}