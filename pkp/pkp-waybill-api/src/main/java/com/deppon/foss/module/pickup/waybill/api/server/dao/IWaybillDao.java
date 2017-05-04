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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillDao.java
 * 
 * FILE NAME        	: IWaybillDao.java
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

import com.deppon.esb.inteface.domain.ccwaybillservice.WaybillCountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyConditionDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.SimpleWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.GrandGoodAbnormalEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AccountingQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AccountingResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.BatchSendSMSDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.BillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EarliestCreateTimeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryListDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportBillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SignDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOneYearDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryOneYearDto;
import com.deppon.foss.module.pickup.waybill.shared.request.CubcQueryTotalAmountRequest;
import com.deppon.foss.module.pickup.waybill.shared.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmParamVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;
/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:IWaybillDao</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-10-10 sunrui 新增 </div>
 ******************************************** 
 */
public interface IWaybillDao {

	/**
	 * 提交时新增运单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:35:42
	 */
	String addWaybillEntity(WaybillEntity waybill);

	/**
	 * 修改运单
	 * 
	 * @param waybill
	 */
	int updateWaybill(WaybillEntity waybill);

	/**
	 * 通过运单编号查询运单
	 * 
	 * @param waybill
	 */
	WaybillEntity queryWaybillByNoAndOrderNo(String waybillNo, String orderNo);
	
	/**
	 * 通过运单编号查询运单
	 * 
	 * @param waybill
	 */
	WaybillEntity queryWaybillByNo(String waybillNo);
	
	/**
	 * 
	 * 通过运单编号查询非PDA待补录运单
	 * @author foss-sunrui
	 * @date 2013-3-21 下午5:33:48
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillEntity queryWaybillByNoExceptPDAPending(String waybillNo);

	/**
	 * 通过订单编号查询运单
	 * 
	 * @param orderNo
	 */
	WaybillEntity queryWaybillByOrderNo(String orderNo);

	/**
	 * 通过运单编号集合查询运单
	 * 
	 * @param waybillNoList
	 */
	List<WaybillEntity> queryWaybillByNoList(List<String> waybillNoList);

	/**
	 * 通过运单系统编号查询家装信息
	 * 
	 * @param waybill
	 */
	WaybillEntity queryWaybillById(String id);

	/**
	 * 查询最近一次建立的运单
	 * 
	 * @param orgCode
	 */
	WaybillEntity queryLastWaybill(String orgCode);

	/**
	 * 
	 * <p>
	 * 查询运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-30
	 * @param waybillId
	 * @return WaybillEntity
	 */
	WaybillEntity getWaybillEntityById(String waybillId);

	/**
	 * 
	 * <p>
	 * 删除运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param waybillEntity
	 * @return int
	 */
	int deleteWaybillEntityById(String waybillId);

	/**
	 * 
	 * 查询官网需要的运单信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-10 上午9:51:49
	 */
	List<AccountingResultDto> queryWaybillInfosByAccount(
			AccountingQueryDto accountingQueryDto);

	/**
	 * 
	 * 查询运单
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-11-26 下午08:37:46
	 */
	List<WaybillEntity> queryWaybill(WaybillDto waybillDto);
	
	List<WaybillEntity> queryWaybillNoExpress(WaybillDto waybillDto);

	/**
	 * 根据运单号查询运单部分数据（查询付款方式，到付金额，外发代理和币种,
	 * 运输性质,是否整车运单,提货方式,总费用,保价声明价值,
	 * 代收货款,货物总件数,最终配载部门，订单编号)
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 下午2:47:39
	 */
	WaybillEntity queryPartWaybillByNo(String waybillNo);

	/**
	 * 弃货查询
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-15
	 * @param condition
	 * @return List<AbandonedGoodsDto>
	 */
	List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(
			AbandonedGoodsCondition condition);

	/**
	 * 更新弃货
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param args
	 * @return void
	 * @see
	 */
	void updateAbandonedGoods(Map<String, Object> args);

	/**
	 * 运单明细查询（接口使用）
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param waybillNoList
	 * @return List<CrmWaybillServiceDto>
	 */
	List<CrmWaybillServiceDto> queryWaybillDetail(QueryListDto queryListDto);
	
	/**
	 * 
	 * 查询到货金额
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-24 下午8:47:55
	 */
	List<QueryMoneyDto> queryRecieveMoney(QueryMoneyConditionDto queryMoneyConditionDto);
	
	/**
	 * 
	 * 查询发货金额
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-24 下午8:47:58
	 */
	List<QueryMoneyDto> queryDeliveryMoney(QueryMoneyConditionDto queryMoneyConditionDto);

	/**
	 * 
	 * 根据不同的查询条件返回运单单号集合
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-24 上午9:51:49
	 */

	List<String> queryWaybillListByComplexCondation(
			WaybillListByComplexCondationDto waybillListByComplexCondationDto);

	/**
	 * 根据不同的查询条件返回运单单号集合
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param condition
	 * @return List<SalesStatementByComplexCondationResultDto>
	 */
	List<SalesStatementByComplexCondationResultDto> querySalesStatementByComplexCondation(
			WaybillListByComplexCondationDto condition,int start,int limit);
	/**
	 * 根据不同的查询条件返回运单单号集合  不分页
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param condition
	 * @return List<SalesStatementByComplexCondationResultDto>
	 */
	List<SalesStatementByComplexCondationResultDto> querySalesStatementByComplexCondation(
			WaybillListByComplexCondationDto condition);

	/**
	 * 根据不同的查询条件组合查询结果列表，结果为封装为dto的集合
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param condition
	 * @return List<SalesStatementByComplexCondationResultDto>
	 */
	List<SalesStatementByComplexCondationResultDto> queryBillListByComplexCondation(
			WaybillListByComplexCondationDto condition);

	/**
	 * 根据运单号查询运单信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	WaybillInfoByWaybillNoReultDto queryWaybillInfoByWaybillNo(String waybillNO);
	
	/**
	 * 根据查询条件查询执行计划
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	List<AdjustPlanResultDto> queryAdjustPlan(AdjustPlanSearcherDto adjustPlanSearcherDto, int start,int limit);

	/**
	 * 根据查询条件查询执行计划count
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	Long queryAdjustPlanCount(AdjustPlanSearcherDto adjustPlanSearcherDto);

	
	/**
	 * 计算总数，分页使用
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param condition
	 * @return int
	 */
	int countQuerySalesStatementByComplexCondation(
			WaybillListByComplexCondationDto condition);

	/**
	 * 查询运单信息--官网
	 * @author 043260-foss-suyujun
	 * @date 2012-12-28
	 * @param args
	 * @return
	 * @return List<WayBillDetailInfo>
	 * @see
	 */
	List<CrmWaybillServiceDto> queryWaybillInfoByCondition(Map<String, Object> args,boolean isRowBounds);
	
	/**
	 * APP查询我的发货/收货信息
	 * @author WangQianJin
	 * @date 2014-07-19
	 * @param args
	 * @param isRowBounds
	 * @return
	 */
	List<CrmWaybillServiceDto> queryAppWaybillInfoByCondition(Map<String, Object> args,boolean isRowBounds);

	/**
	 * 查询运单信息 总数--官网
	 * @author 043260-foss-suyujun
	 * @date 2012-12-28
	 * @param args
	 * @return
	 * @return int
	 * @see
	 */
	int countQueryWaybillInfoByCondition(Map<String, Object> args);
	
	/**
	 * APP查询我的发货/收货信息总数
	 * @author WangQianJin
	 * @date 2014-07-19
	 * @param args
	 * @param isRowBounds
	 * @return
	 */
	int countQueryAppWaybillInfoByCondition(Map<String, Object> args);
	
	/**
	 * 
	 * 通过运单号、流水号校验 当前流水号是否存在
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-5 上午9:28:56
	 * @param waybillNo
	 * @return
	 * @see
	 */
	LabeledGoodEntity queryLabeledGoodByserialNo(String waybillNo,String serialNo);

	/**
	 * 
	 * @param waybillNoList
	 * @return
	 */
	List<CrmWaybillServiceDto> queryWaybillDetail(List<String> waybillNoList);
	
	
	/**
	 * 查询部门标杆编码
	 * @param waybillNo
	 * @return
	 */
	String queryDepartureDeptNumber(String waybillNo);
	
	/**
	 * 
	 * 根据运单集合返回运单详细信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-6 下午5:21:02
	 */
	List<WaybillInfoDto> queryWaybillInfo(WaybillQueryInfoDto waybillQueryInfoDto);
	
	/**
	 * 
	 * 根据运单集合返回运单详细信息
	 * @author 234773-xulixin
	 * @date 2015-07-06 下午14:21:02
	 */
	List<WaybillInfoDto> queryWaybillInfoForQms(WaybillQueryInfoDto waybillQueryInfoDto);

	/**
	 * 
	 * 根据运单号查看该运单是否快递
	 * @author 234773-xulixin
	 * @date 2015-07-11 17:14:03
	 */
	String getIsExpressByWayBillNo(String waybillNo);
	/**
	 * <p>
	 * 查询运单状态<br />
	 * </p>
	 * @author 苏玉军
	 * @version 0.1 2013-1-7
	 * @param waybillNo
	 * @return
	 * String
	 */
	String queryWaybillStatus(String waybillNo);
	
	/**
	 * 
	 * 自定义查询
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-1 上午9:48:34
	 */
	List<SimpleWaybillDto> queryWayBillListByUserDefinedQuery(SimpleWaybillDto simpleWaybillDto, int count);

	/**
	 * 
	 * 通过运单号码更新运单记录
	 * @author foss-sunrui
	 * @date 2013-3-23 下午5:12:05
	 * @param waybill
	 * @return
	 * @see
	 */
	int updateWaybillByWaybillNo(WaybillEntity waybill,String oldWaybillNo);
	
	/**
	 * 
	 * 通过运单编号查询非PDA待补录运单
	 * @author foss-sunrui
	 * @date 2013-3-21 下午5:33:48
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillEntity queryWaybillExistByPDAPending(String waybillNo);

	/**
	 * 是否存在id为传入值 并且active=y的运单
	 * @param id
	 * @return
	 */
	boolean existsActiveWaybillById(String id);
	
	
	 List<SaleDepartmentEntity> queryByDepartmentInfo(
				QueryPickupPointDto dto);
	 /**
	  * 合伙人查询目的站网点信息
	  * @author Foss-351326-xingjun 
	  * @date 2016-8-31 下午4:44:20
	  * @param dto
	  * @return
	  * @see
	  */
	 SaleDepartmentEntity queryPartnerSalesDepartment(QueryPickupPointDto dto);

	/**
	 * 查询外发网点
	 * @param dto
	 * @return
	 */
	List<OuterBranchEntity> queryOuterBranchs(QueryPickupPointDto dto);
	
	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);
	
	/**
	 * 更新最终配载部门
	 * @param lastOrgCode
	 * @param lastOrgName
	 * @param waybillID
	 * @return
	 */
	int updateLastOrgcode(String lastOrgCode,String lastOrgName,String waybillID);
	
	/**
	 * 更新最终库存部门
	 * @param lastOrgCode
	 * @param waybillNO
	 * @return
	 */
	int updateEndStockOrgcode(String lastOrgCode,String waybillNO);
	
	/**
     * 根据条件查询营业部信息
     * @author WangQianJin
     * @date 2013-7-19 上午11:15:54
     */
    SaleDepartmentEntity queryDepartmentInfoByDto(QueryPickupPointDto dto2);
    
    /**
     * 根据条件查询偏线信息
     * @author WangQianJin
     * @date 2013-7-19 下午1:52:21
     */
    OuterBranchEntity queryOuterBranchByDto(QueryPickupPointDto dto2);
    /**
	 * 
	 * 根据运单号集合查询未补录的运单
	 * @author foss-meiying
	 * @date 2013-7-19
	 */
	 List<String> queryWaybillNoMakeupByWaybillNo(WaybillQueryInfoDto dto);
	 
	 /**
		 * 
		 * 根据运单号集合查询运单状态
		 * @author  panguoyang
		 * @date 2013-7-23
	*/
	 
	 public String getWaybillStatus(String waybillNo);
	 
     /**
      * 通过词条代码查询
      * @author WangQianJin
      * @date 2013-7-23 上午11:28:25
      */
	List<DataDictionaryValueEntity> queryByTermsCode(String termsCode);
	 
	/**
	 * 通过配置项集合查询系统配置
	 * @author WangQianJin
	 * @date 2013-7-24 上午10:18:11
	 */
	List<ConfigurationParamsEntity> queryByConfCodeArray(String[] confCodes, String orgCode);
	
	/**
	 * 通过配置项标示查询系统配置,根据confCode和orgCode一起查询
	 * @author WangQianJin
	 * @date 2013-7-24 上午10:35:28
	 */
	ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode);
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	List<String> queryByCodeAndPickup(String code);
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	List<String> queryByCodeAndDelivery(String code);
	
	/**
	 * 查询自有网点
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	List<BranchQueryVo> queryListByDepartment(SaleDepartmentEntity entity);
	
	/**
	 * 查询能开精准大票的自有网点
	 * 
	 * @date 2014-9-16 
	 */
	List<BranchQueryVo> queryListByDepartmentAndPro(SaleDepartmentEntity entity);

	
	/**
	 * 查询汽运偏线、空运网点
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity);

	/**
	 * 通过运单号查询提货网点名称
	 * @author panguoyang
	 * @date 2013-7-30下午9:10:10
	 */
	String queryCustomerPickupOrgNameByWayno(String waybillNo,String productCode);
	
	/**
	 * 通过运单号查询出发部门名称
	 * @author panguoyang
	 * @date 2013-7-30下午9:10:10
	 */
	String queryReceiveOrgNameByWayno(String waybillNO);
	
	/**
 	 * 
 	 * 根据部门Code和所属集中开单组查询
 	 * 
 	 * @author WangQianJin
 	 * @date 2013-08-02
 	 */
 	List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(String code,String billingGroup);
 	
 	/**
 	 * 根据部门名称和所属集中开单组查询
 	 * @author WangQianJin
 	 * @date 2013-8-11 下午3:32:15
 	 */
 	List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(String name,List<String> billingGroup);

 	/**
 	 * 按单号且创建时间升序查询运单
 	 * panguoyang;
 	 * @param waybillNo
 	 * @return
 	 */
	WaybillEntity queryWaybillNo(String waybillNo);
	
	/**
	 * 通过运单编号查询最新的运单：可以生效和失效
	 * @param waybill
	 * 
	 * 版本		时间			用户			内容
	 * 0001		20130916	zxy				新增:BUG-54827
	 *
	 */
	WaybillEntity queryRecentWaybillByNo(String waybillNo);

	/**
	 * 判断该单是否已经PDA、PC、RFC的状态为Active
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-23 15:20:18
	 * @param map
	 * @return
	 */
	WaybillEntity queryWaybillIsPcOrPdaOrRfcActive(String waybillNo,
			List<String> pendingTypeList); 

	WaybillPendingEntity queryPendingByNo(String defaultIfNull);
	
	 	//-----------------------
 	/**
 	 * 这段代码新加的 小件  查询虚拟部门
 	 */
 	List<SaleDepartmentEntity> queryByDepartmentInfoVirtual(
 			QueryPickupPointDto dto);
 	
 	/**
 	 * -------小件查询签收员工等信息
 	 * @param waybillNo
 	 * @return
 	 */
 	SignDto getWaybillSignInfo(String waybillNo);
 	
 	
 	/**
 	 * 根据产品编码查询产品信息
 	 * @author 026123-foss-lifengteng
 	 * @date 2013-8-12 上午9:56:17
 	 */
 	ProductEntity queryProductByCode(String id);

	/**
	 * @param waybillNo
	 * @return
	 */
	List<WaybillEntity> queryWaybillByReturnBillNo(String waybillNo);
 		

	/**
	 * 
	 * 查询运单
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-11-26 下午08:37:46
	 */
	List<WaybillEntity> selectByWaybillNoNoActive(String waybillNo);

	List<SaleDepartmentEntity> queryByDepartmentInfoExpress(
			QueryPickupPointDto dto);

	boolean queryIsExpressBill(String waybillNo);

	List<EarliestCreateTimeDto> selectPdaWaybillCreateTime(List<String> waybillNoList);

	void updateActualFreightEntity(WaybillDto waybillDto);
	
	/**
	 * 获取刚提交运单时的那条运单信息
	 * WangQianJin
	 * @param waybillNo
	 */
	WaybillEntity queryWaybillForFirst(String waybillNo);

	/**
	 * <p>根据派送单ID查询运单详情</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-12 19:35:25
	 */
	List<WaybillEntity> queryWaybillEntityByDeliverbillById(
			Map<Object, Object> map);
	
	/**
	 * 根据运单号删除运单信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-20 下午6:38:56
	 */
	int deleteWaybillByWaybillNo(String waybillNo);

	/**
	 * <p>根据交接单No查询运单详情</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-12 19:35:25
	 */
	List<WaybillEntity> queryWaybillEntityByHandoverBillNo(
			Map<Object, Object> map);
	

	/** 
	 * MANA-257接货费优化
	 * 
	 * 查询第一票运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 上午11:07:04
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao#queryWaybillByNo(java.lang.String)
	 */
	WaybillEntity queryFirstDeliveryWaybill(WaybillEntity entity);
	/**
	 * 新增重大货物异常
	 * @author 076234
	 * @param grangGood
	 */
	void insertGrandGoodAbnormal(GrandGoodAbnormalEntity grangGood);
	
	/**
	 * 查询该发货客户当天是否收取过接货费
	 * @param entity
	 * @return
	 */
	int queryIsGetPickFeeByCust(WaybillEntity entity);

	/**
	 * 查询重大货物
	 * @param waybillNo
	 * @return
	 */
	GrandGoodAbnormalEntity queryGrandGoodAbnormal(String waybillNo);
	/**
	 * 更新重大货物异常
	 * @param grangGood
	 */
	void updateGrandGoodAbnormal(GrandGoodAbnormalEntity grangGood);

	/**
	 * 根据CRM传入参数获取运单信息
	 * @创建时间 2014-4-12 下午7:17:00   
	 * @创建人： WangQianJin
	 */
	List<CrmResultDto> queryWaybillInfoByCrm(CrmParamVo crmParamVo,boolean isRowBounds);
	
	/**
	 * 根据CRM传入参数获取运单信息数量
	 * @创建时间 2014-4-14 下午5:12:21   
	 * @创建人： WangQianJin
	 */
	int countQueryWaybillInfoByCrm(CrmParamVo crmParamVo);	

	/**
	 * @param codition: 封装的Map对象，其中key 分别为：startDate 开始时间，endDate 结束时间，
	 * phones 拼装好的客户电话字符串，mobilePhones 拼装好的客户手机字符串，isAssociatedConsignee 是否关联发货人
	 * @author liyongfei
	 * @date 2014-07-31
	 */
	List<WaybillCountDto> queryWaybillByPhone(Map<String,Object> condition);
	
	
	/**
	 * 根据官网传入参数获取运单信息
	 * @创建时间 2014-9-12 下午7:17:00   
	 * @创建人： BaiLei
	 */
	List<WaybillEntity> queryActiveEWaybillInfoByCondition
			(Map<String, Object> args, boolean isRowBounds);
	/**
	 * 根据官网传入参数获取电子运单信息数量
	 * @创建时间 2014-9-12 下午7:17:00   
	 * @创建人： BaiLei
	 */
	int countQueryActiveEWaybillInfoByCondition(Map<String, Object> args);
	
	/**
	 * 批量激活运单
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList 运单号集合
	 * @return
	 */
	int setWaybillActive(List<String> waybillNoList);
	
	/**
	 * 通过custcode查询收获和返回总数 每天统计
	 * @param entity
	 * @return
	 */
	List<BatchSendSMSDto> querySendAndReceiveGoodsDayCount(String cusCode);

	/**
	 * 查通过custcode查询 发货 和返单数
	 * @param entity
	 * @return
	 */
	List<BatchSendSMSDto> queryReceiveAndSignBillGoodsCount(String cusCode);

	/**
	 * 通过custcode查询收货和返回总数  周次统计
	 * @param entity
	 * @return
	 */
	List<BatchSendSMSDto> querySendAndReceiveGoodsWeekCount(String cusCode);

	/**
	 * 查通过custcode查询 发货 和返单数  周次统计
	 * @param entity
	 * @return
	 */
	List<BatchSendSMSDto> queryReceiveAndSignBillGoodsWeekCount(String cusCode);

	/***
	 * 修改  短信2count分开统计
	 * 成功签收#票  每天统计
	 * @author yuting@163.com
	 * @date 2014-07-16 下午3:46:39
	 */
	List<BatchSendSMSDto> querySignBillGoodsCount(String cusCode,int day);

	/***
	 * 修改  短信2 count分开统计
	 * 成功返回的签单共有#票  每天统计
	 * @author yuting@163.com
	 * @date 2014-07-16 下午3:46:39
	 */
	List<BatchSendSMSDto> queryReceiveGoodsCount(String cusCode,int day);

	/**
	 * @author liyongfei 根据运单号查询运单的操作历史
	 * @param condition 包含运单号，变更项（变更项以逗号分开）
	 * @return
	 */
	List<WaybillAcHisPdaEntity> queryWaybillHisByNo(Map<String , Object> condition);
	
	/**
	 * 
	 * 根据客户编码、开始时间、结束时间查询运单详细信息
	 */
	List<WaybillOneYearDto> queryWaybillOneYear(WaybillQueryOneYearDto waybillQueryOneYearDto);

	
	/**
	 * App用户通过手机号码查询用户的费用详细信息
	 * @author 198719-yetao
	 * @date 2014-11-7
	 */
	ComponentDto queryComponentDetail(ReportComponentDto reportComponentDto);
	
	/**
	 * APP根据用户电话号码查询相应月份的电子账单
	 * @author 198719-foss-yetao
	 */
	List<BillingDto> queryBillingDetail(ReportBillingDto reportBillingDto);

	List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto);

	List<SaleDepartmentEntity> queryPickupStationInforExpress(QueryPickupPointDto dto);

	List<SaleDepartmentEntity> selectPickupStationInfoVirtualExpress(QueryPickupPointDto dto);

	String getWoodenPackageOrgCode(String waybillNo);
	/**
	 根据当前时间查询当前月份所有内部员工发货的优惠金额
	 dp-foss-dongjialing
	 225131
	 */
	Long queryDiscountFee(String employeeNo, Date firstMonthDate, Date lastMonthDate);
	Long queryTotalFee(String code, Date firstMothDay, Date lastMothDay);

	String queryDiscountInfo(String waybillNo);
	
	/**
	 * 根据当前登陆 客户编码 的客户以及 开始时间和结束时间  获取 快递签收状态 的详细信息
	 * @param waybillSignDetailQueryVo
	 * @param start 起始页码
	 * @param limit 每页条数
	 * @param flag 是否分页，true:分页；false:不进行分页
	 * @return 
	 */
	List<WaybillSignDetailVo> queryWayBillSignDetail(WaybillSignDetailQueryVo waybillSignDetailQueryVo,int start,int limit,boolean flag) ;
	/**
	 * 获取 快递签收状态 的详细信息 的总数量
	 * @param waybillSignDetailQueryVo
	 * @return 总数量
	 * @author 272311
	 */
	int countQueryWayBillSignDetail(WaybillSignDetailQueryVo waybillSignDetailQueryVo) ;

	/**
	 * 根据运单号获取作废的票数
	 * @param waybillNumList 运单号列表
	 * @return 作废的票数
	 * @author 272311
	 */
	int queryWaybillInvalid(List<String> waybillNumList);
	/**
	 *  根据运单号获取退回的票数
	 * @param waybillNumList 运单号列表
	 * @return 退回的票数
	 * @author 272311
	 */
	int queryWaybillBack(List<String> waybillNumList);  
	
	
	
	void updateWaybillActiveByWaybillNo(WaybillEntity waybillEntity) ;
	/**
	 * 根据请求参数 查询 发货清单 列表 的总记录数
	 * @param deliverGoodsListQueryVo 请求参数
	 * @return 数据总记录数
	 */
	int queryWaybillDeliverGoodsListCount(DeliverGoodsListQueryVo deliverGoodsListQueryVo);
	
	
	/**
	 * 德邦家装信息查询接口给综合
	 */
	WaybillMessageEntity queryWaybillandOther(String waybillNo);
	/**
	 * 德邦家装信息查询接口给综合
	 */
	List<InstallationEntity>  queryWaybillInstallation(String waybillNo);
	
	
	/**
	 * 根据请求参数 查询 发货清单 列表
	 * @param deliverGoodsListQueryVo 请求参数
	 * @return DeliverGoodsListVo 获取的货物信息-发货清单
	 * @author 272311
	 */
	List<DeliverGoodsListVo> queryWaybillDeliverGoodsList(DeliverGoodsListQueryVo deliverGoodsListQueryVo,int start,int limit) ;

   /**
	 * 
	 * 根据运单集合返回运单详细信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-6 下午5:21:02
	 */
	List<WaybillInfoDto> queryWaybillInfoForSOC(WaybillQueryInfoDto waybillQueryInfoDto);
	
	/**
	 * 根据子母运单号集合获取运单信息
	 * @author 270293-foss-zhangfeng
	 * @date 2015-10-29下午17:17:02
	 */
	List<WaybillEntity> queryWaybillListWayBillNo(List<String> wayBillNoList);
	
	/**
	 * 根据运单号集合返回签收情况
	 * @param waybillNoList
	 * @return
	 */
	List<WaybillSignDetailVo> getWaybillSignList(List<String> waybillNoList);
	
	/**
	 * 通过运单编号查询第一次开单时运单信息
	 * @param waybill 
	 * @return WaybillEntity 
	 * @author 273279
	 */
	WaybillEntity queryFirstWaybillByWaybillNo(String waybillNo);
	/**
	 * 根据开单人工号查询运单信息
	 * @param waybill 
	 * @return WaybillEntity 
	 * @author 273279
	 */
	public List<WaybillEntity> queryWaybillForPrint(WaybillDto waybill);

	
	/**
	 * 通过运单编号查询第一次开单时运单信息
	 * @param waybillNo 
	 * @return WaybillEntity 
	 * @author 306486
	 */
	WaybillEntity queryWaybillBasicInfoByNo(String waybillNo);

	/**
	 * 通过运单号查询免费接货值
	 * @author 306486
	 * @param waybillNo
	 * @return
	 */
	public String queryPickupGoodsByWaybillNo(String waybillNo);
	
	/**
	 * 通过运单编号查询运单
	 * zhuxue
	 * 280747
	 * @param waybill
	 */
	WaybillEntity queryNewWaybillByNo(String waybillNo);
	/**
	 * 通过运单号判断是否为悟空快递单
	 * @param waybillNo 
	 * @return
	 * @author 272311-sangwenhao
	 */
	String queryIsECSByWayBillNo(String waybillNo) ;

	/**
	 * 根据运单号判断是否是合伙人运单
	 * @param waybillNo
	 * @return
	 */
	String queryPartnerWaybillForPrint(String waybillNo);
	
	/**
	 * cubc，根据开单时间和客户编码查询（预付+到付-代收货款）总金额
	 * String
	 * @author 198771-zhangwei
	 * 2017-1-6 下午4:54:23
	 */
	String queryTotalAmount(CubcQueryTotalAmountRequest requestParam);
}