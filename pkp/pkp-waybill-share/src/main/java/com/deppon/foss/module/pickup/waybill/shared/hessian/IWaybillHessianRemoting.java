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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/IWaybillHessianRemoting.java
 * 
 * FILE NAME        	: IWaybillHessianRemoting.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
//import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.IsCustomerCircleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.DispatchOrderChannelNumberEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.NotificationEntity2;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerCodAmountUpperLimitResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerMonthlyLineDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerPrestoreDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddedPlanFeeCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDtoResult;
import com.deppon.foss.module.pickup.waybill.shared.dto.CustomerAddressDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ITServiceResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerCodAmountUpperLimitDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerPrestoreDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SearchPictureWaybillCondiction;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.request.HisSegMatchRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.HisSegMatchResponse;
import com.deppon.foss.module.pickup.waybill.shared.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.SearchPictureVo;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:运单信息远程服务接口</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-10-10 sunrui 新增 </div>
 ******************************************** 
 */
public interface IWaybillHessianRemoting extends IHessianService {
	
	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
	 * @author Foss-206860
	 */
	public String queryIndustrySourceCategory(String goodsName);
	
	/**
	 * 获取系统配置
	 * @param model
	 * @param config
	 * @param orgCode
	 * @return
	 */
	ConfigurationParamsEntity getConfig(String model, String config, String orgCode);
	/**
	 * 检查运单号码是否符合规范
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */
	boolean checkWaybillNo(String waybillNo);

	/**
	 * 检查运单号码是否符合规范
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */
	boolean checkWaybillNoAndOrderNo(String waybillNo, String orderNo);

	/**
	 * 检查运单号码是否符合规范
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */
	boolean checkWaybillNoExceptPDAPending(String waybillNo);
	
	/**
	 * 检查补录时电子运单号码是否需要校验
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */	
	boolean checkEWaybillNoExceptPDAPending(String waybillNo);

	/**
	 * 
	 * 功能：submitWaybill提交运单信息
	 * 
	 * @param:
	 * @return:void
	 * @since:1.6
	 */
	void submitWaybill(WaybillDto waybill);

	/**
	 * 通过运单号获取运单实体信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:07:57
	 */
	WaybillEntity queryWaybillBasicByNo(String waybillNo);

	/**
	 * 通过运单号码集合获取运单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:08:00
	 */
	List<WaybillEntity> queryWaybillBasicByNoList(List<String> waybillNoList);

	/**
	 * 通过运单号获取运单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:08:03
	 */
	WaybillDto queryWaybillByNo(String waybillNo);

	/**
	 * 更新在线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	// int updateWaybillPrintTimes(String waybillNo);

	/**
	 * 根据出发部门、到达部门、产品类型、开单时间（数据生成时间）获得预计出发时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-19 下午3:40:43
	 */
	Date searchPreLeaveTime(String departDeptCode, String specialLine, String productCode, Date createTime);

	/**
	 * 根据预计出发日期、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺自提时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:06
	 */
	EffectiveDto searchPreSelfPickupTime(String departDeptCode, String arriveDetpCode, String productCode, Date preLeaveTime, Date createTime);

	/**
	 * 根据承诺自提时间、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺派送时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:49
	 */
	EffectiveDto searchPreDeliveryTime(String departDeptCode, String arriveDetpCode, String productCode, Date preLeaveTime, Date createTime);

	/**
	 * 判断能否欠款
	 * 
	 * 使用说明，判断能否欠款均使用同一方法，如果付款方式为月结时请提供客户编码，欠款方式为临欠是请提供组织编码
	 * 
	 * @param customerCode
	 *            客户编码
	 * @param orgCode
	 *            组织编码 （欠款组织编码）
	 * @param debtType
	 *            欠款类别 区分临欠与月结
	 *            请使用常量：SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
	 *            表示 月结 请使用常量：
	 *            SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
	 *            表示 临时欠款
	 * @param debtAmount
	 *            欠款金额
	 * @return 能否欠款的判断结果
	 */
	DebitDto isBeBebt(String customerCode, String orgCode, String debtType, BigDecimal debtAmount);

	/**
	 * 插入一条运单打印记录
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-5 下午7:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int insertSelective(PrintInfoEntity record);

	/**
	 * 查询在线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-5 下午7:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int queryPrintTimesByWaybillId(String waybillId, String waybillNo);

	/**
	 * 检查是否有此外请车编号</br>
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午4:48:45
	 * @param inviteNo
	 *            外请车编号
	 * @param deptCode
	 *            部门编码
	 * @return 外请车申请状态
	 */
	String checkInviteNoIsExists(String inviteNo, String deptCode);

	/**
	 * 根据外请车编号查询 请车价格 </br> 约车申请不存在 抛出异常
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午2:32:17
	 * @param inviteNo
	 *            外请车编号
	 * @return 外请车价格
	 */
	InviteVehicleDto queryInviteCostByInviteNo(String inviteNo);

	/**
	 * 
	 * 查询运单
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-11-26 下午08:37:46
	 */
	List<WaybillEntity> queryWaybill(WaybillDto waybillDto);

	/**
	 * 
	 * <p>
	 * 查询走货路径
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-17 下午6:20:08
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @param time
	 * @return
	 * @see
	 */
	FreightRouteDto queryFreightRouteBySourceTarget(String sourceCode, String targetCode, String productCode, Date time);

	/**
	 * 
	 * <p>
	 * 通过组织机构编码查组织机构
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-17 下午7:33:20
	 * @param code
	 * @return
	 * @see
	 */
	OrgAdministrativeInfoEntity queryOrgInfoByCode(String code);

	/**
	 * 
	 * 根据银行编码查询银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:43:42
	 */
	BankEntity queryBankByCode(String code);

	/**
	 * 弃货查询
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-15
	 * @param condition
	 * @return List<AbandonedGoodsDto>
	 */
	List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(AbandonedGoodsCondition condition);

	/**
	 * 查询当前用户所在大区信息
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @return OrgAdministrativeInfoEntity
	 */
	OrgAdministrativeInfoEntity queryBigRegionOfCurrDept();

	/**
	 * 
	 * <p>
	 * 通过代理网点编码查询代理网点
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-18 上午11:56:05
	 * @param agencyBranchCode
	 * @return
	 * @see
	 */
	AgencyBranchOrCompanyDto queryAgencyBranchCompanyInfo(String agencyBranchCode);

	/**
	 * 
	 * <p>
	 * 查询出发营业部的默认外场
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-21 上午10:50:53
	 * @param saleCode
	 * @param productCode
	 * @param isTransSale
	 *            是否驻地营业部
	 * @return
	 * @see
	 */
	String queryDefaultTransCodeDept(String saleCode, String productCode, boolean isTransSale);

	/**
	 * 
	 * <p>
	 * 优惠券验证或使用接口
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午2:11:40
	 * @param couponInfo
	 * @return
	 * @see
	 */
	CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo);

	/**
	 * 
	 * <p>
	 * 反使用优惠券接口
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午2:14:17
	 * @param couponNumber
	 * @return
	 * @see
	 */
	boolean effectCouponState(String couponNumber);

	/**
	 * 
	 * <p>
	 * 判断是否驻地营业部
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午4:26:29
	 * @param saleCode
	 * @return
	 * @see
	 */
	boolean identityTransSale(String saleCode);

	/**
	 * 根据gis网点匹配条件查询网点code
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午6:20:02
	 */
	List<GisDepartmentDto> queryPickupOrgCodeByGis(GisPickupOrgDto dto);

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“航班信息和关联信息”的封装DTO实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-28 下午1:52:18
	 * @param flight
	 *            以“航班信息”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 分页的Action和Service通讯封装对象
	 * @throws FlightException
	 * @see
	 */
	PaginationDto queryFlightDtoListBySelectiveCondition(FlightEntity flight, int offset, int limit) throws FlightException;

	/**
	 * 通过调用GIS的特殊地址查询接口获取地址类型
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:52:59
	 */
	List<String> querySpecialAddressByGis(GisPickupOrgDto dto);

	/**
	 * 
	 * 查询装卸费比率
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 上午11:22:41
	 * @param parmMouudle
	 * @param parmCode
	 * @param orgCode
	 * @return
	 */
	ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String orgCode,String config);

	
	/**
	 * 根据传入参加查询系统参数配置项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-14
	 */
	ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String orgCode,String config,String model);
	
	/**
	 * 检查订单号码是否符合规范
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */
	boolean checkWaybillOrderNo(String orderNo);

	/**
	 * <p>
	 * 根据合同编码和部门编码查询合同信息
	 * </p>
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-2-23 上午10:35:43
	 * @param bargainCode
	 *            合同编号
	 * @param deptCode
	 *            部门编码
	 * @return
	 * @see
	 */
	CusBargainEntity queryCusBargainByParams(String bargainCode, String deptCode);

	/**
	 * 
	 * 自有司机通过司机工号判断是否存在
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-28 下午4:56:25
	 * @param driverCode
	 * @return
	 * @see
	 */
	boolean isOwnDriverExists(String driverCode);

	/**
	 * 
	 * 获取库区信息
	 * 
	 * @author foss-sunrui
	 * @date 2013-4-2 下午1:54:42
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @return
	 * @see
	 */
	GoodsAreaEntity queryGoodsAreaByArriveRegionCode(String sourceCode, String targetCode, String productCode);

	/**
	 * 根据运单号查询费用明细中的其它费用
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:03:45
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);

	/**
	 * 
	 * 查询系统上线日期
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-11 下午7:22:10
	 */
	Date queryFossGoliveDate();

	/**
	 * 
	 * 应用监控数据添加
	 * 
	 * @author foss-sunrui
	 * @date 2013-4-15 下午3:22:30
	 * @param waybillDto
	 * @see
	 */
	void businessMonitor(WaybillDto waybillDto);

	/**
	 * 
	 * @Description: 计算物流费用 Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午3:05:21
	 * @param billCalculateDto
	 * @return
	 * @version V1.0
	 */
	List<GuiResultBillCalculateDto> queryGuiBillPrice(GuiQueryBillCalculateDto billCalculateDto);

	/**
	 * 根据历史时间和组织编码查询组织信息（查询历史组织信息）
	 * 
	 * 若时间为空，则只根据组织编码查询组织信息 否则将根据时间，查询在creatTime和modifyTime时间段的部门
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	OrgAdministrativeInfoEntity queryHisOrgInfoByCode(String code, Date billTime);

	/**
	 * 根据历史时间和营业部编码查询营业部信息（查询历史营业部信息）
	 * 
	 * 若时间为空，则只根据营业部编码查询营业部信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的营业部
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	SaleDepartmentEntity queryHisSaleDeptByCode(String code, Date billTime);

    /**
 	 * 根据历史时间和代理网点编码查询代理网点信息（查询历史代理网点信息）
 	 * 
 	 * 若时间为空，则只根据代理网点编码查询代理网点信息
 	 * 否则将根据时间，查询在creatTime和modifyTime时间段的代理网点
 	 * 不根据Active='Y'来查询
 	 * 
 	 * @author 026123-foss-lifengteng
 	 * @date 2013-4-17 下午6:02:26
 	 */
	OuterBranchEntity queryHisOuterBranchByCode(String code, Date billTime);

	/**
	 * 根据营业部部门编码查询相关的集中开单组列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 上午11:03:01
	 */
	List<MapDto> queryBillingGroupListBySalesCode(String salesCode);

	/**
	 * 根据集中开单组部门编码查询相关的营业部列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 上午11:05:36
	 */
	List<MapDto> querySalesListByBillingGroupCode(String billingGroupCode);
	
	/**
	 * 修改密码
	 */
	void modifyPassword(String userName, String newPwd);

	/**
	 * 
	 * 根据外场编码查询外场
	 * @author foss-sunrui
	 * @date 2013-5-21 下午3:12:39
	 * @param code
	 * @return
	 * @see
	 */
	OutfieldEntity queryOutfieldByCode(String code);

	/**
	 * 根据运单号查询ActualFreightEntity对象
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-29 下午4:42:19
	 */
	ActualFreightEntity queryActualFreightByNo(String waybillNo);

	/**
	 * 根据封装的DTO查询运单基本和冗余信息
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 上午10:43:05
	 */
	List<WaybillDto> queryActualFreightAndBasic(WaybillDto waybillDto);

	/**
	 * 根据外场组织编码查驻地营业部
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	String queryDefaultSalesDeptByTransCenter(String saleCode);
	
	/**
	 * 根据集中开单组编码查所属外场组织编码
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	String queryTransCenterByBillingGroupCode(
			String billingGroupCode);
	
	
	/**
	 * 查询营业部
	 * @param dto2
	 * @return
	 */
	List<SaleDepartmentEntity> queryByDepartmentInfo(
			QueryPickupPointDto dto) ;
	/**
	 * 查询外发网点
	 * @param dto2
	 * @return
	 */
	List<OuterBranchEntity> queryOuterBranchs(
			QueryPickupPointDto dto2);

	/**
	 * 获得服务器时间
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 下午3:44:42
	 */
	Date gainServerTime();

	/**
	 * 判断产品时效是否存在
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	boolean identityEffectivePlan(String departDeptCode, String arriveDetpCode,
			String productCode, Date createTime);

	/**
	 * 集中开单查开单组所属中转场的默认出发部门
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	SaleDepartmentEntity queryPickupCentralizedDeptCode(
			String billingGroupOrgCode);

	/**
	 * 根据部门编码在线查询营业部信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-27 下午8:36:10
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String code);
	
	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * 
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);
	
	/**
	 * 按标杆编码查询组织部门实体
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	OrgAdministrativeInfoEntity queryOrgByUnifiedCode(String unifiedCode);
	
	
	/**
	 * 按标杆编码查询组织部门实体 简化  
	 * @author huangwei
	 * @date 2016-10-11 下午4:16:21
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeClean(String unifiedCode);
	
	
	 /**
     * 
     * 记录打标签日志信息
     * @author 025000-FOSS-helong
     * @date 2013-7-1
     */
    public void addPrintLabelInfo(GUIPrintLabelDto printLabelDto);
    /**
	 * 
	 * <p>根据传入的人民币金额、外币代码、开单日期获取转汇后的外币金额</p>
	 * @author 025000-FOSS-helong
	 * @param rmbFee 人民币金额
     * @param currencyCode 外币代码
     * @param billTime 开单日期
	 * @date 2013-7-13
	 * @return 转汇后的外币金额 
	 */
    BigDecimal getExchangedFee(BigDecimal rmbFee, String currencyCode, Date billTime);
    
    /**
     * 
     * 返回币种汇率
     * @author 025000-FOSS-helong
     * @date 2013-7-15
     */
    BigDecimal getExchangedFeeRate(String currencyCode, Date billTime);
    
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
     * <p>获取所有禁运物品</p> 
     * @author foss-panguoyang
     * @date 2013-7-22 下午5:38:25
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryAllActive();
    
    /**
     * 
     * <p>根据类型查询有效的禁运物品</p> 
     * @author panguoyang
     * @date 2013-07-22
     * @param ProhibitedArticlesEntity
     * @return
     * @see
     */
	List<ProhibitedArticlesEntity> queryProhibitGoodsByType(String type);
	
	
	/**
     * 
     * <p>获取汽运禁运物品</p> 
     * @author panguoyang
     * @date 2013-07-22
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryProhibitGoodsForAutomobile(String otherType);
    
    /**
     * 
     * <p>判断是否是禁运物品</p> 
     * @author foss-panguoyang
     * @date 2013-07-22
     * @param goodsName
     * @return
     * @see
     */
    boolean isProhibitGoods(String goodsName);
    
    /**
	 * 根据获取名称获取是否是贵重物品
	 * @author WangQianJin
	 * @date 2013-7-23 上午11:06:50
	 */
	boolean isValuablesGoods(String goodsName);
	
	/**
	 * 通过物品的数量，体积，保价金额判断是否属于贵重物品
	 * @author WangQianJin
	 * @date 2013-7-24 上午9:57:44
	 */
	boolean isValueGoods(int goodsNum, String goodsVolume,String goodsValue);
	
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
	 */
	List<BranchQueryVo> queryListByDepartmentAndPro(SaleDepartmentEntity entity);
	
	/**
	 * 查询汽运偏线、空运网点
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity);
	
	/**
     * <p>
     * 查询提货方式-空运
     * </p>
     * 
     * @author 076234-FOSS-panguoyang
     * @date 2013-7-25 上午8:32:33
     * @return
     * @see
     */
	List<DataDictionaryValueEntity> queryPickUpGoodsAir();
	
	/**
     * <p>
     * 查询行业货源品类
     * </p>
     * 
     * @author zhangchengfu
     * @date 2015-6-5 15:23:00
     * @return
     * @see
     */
	List<DataDictionaryValueEntity> queryIndustrySourceCategory4Dict();
	
	/**
     * <p>
     * 查询客户分群
     * </p>
     * 
     * @author zhangchengfu
     * @date 2015-6-5 15:23:00
     * @return
     * @see
     */
	List<DataDictionaryValueEntity> queryFlabelleavemonth4Dict();
	
	/**
	 * 
	 * （查询运输性质为汽运的提货方式）
	 * 
	 * @author 076234-FOSS-panguoyang
	 * @date 2013-07-24 上午09:03:19
	 * @see com.deppon.foss.module.pickup.creating.client.service.IWaybillService#queryPickUpGoodsHighWays()
	 */
	List<DataDictionaryValueEntity> queryPickUpGoodsHighWays();

	/**
	 * 通过发货客户编码查找发货客户能否开月结
	 * @author panguoyang
	 * @date 2013-7-24 下午9:10:10
	 */
	boolean isCanPaidMethod(CusBargainVo vo);

	/**
	 * 通过发货客户编码查找发货客户能否开月结
	 * @author DP-FOSS
	 * @date 2016-10-14
	 */
	boolean isCanPaidMethodForUCBC(CusBargainVo vo);
	
	/**
	 * 
	 * 查询空运货物类型
	 * @author 076234-FOSS-panguoyang
	 * @date 2013-07-27 下午04:27:30
	 * @return
	 */
	List<DataDictionaryValueEntity> queryAirGoodsType();
	
	 /**
     * <p>
     * 查询计费方式
     * @author 076234-FOSS-panguoyang
	 * @date 2013-07-27 下午04:27:30
	 * @return
     */
    List<DataDictionaryValueEntity> queryBillingWay();
    
    /**
     * 
     * 查询货物定义信息（更改单）
     * @author Foss-潘国仰
     * @date 2013-10-22 下午5:13:38
     */
     List<GoodsTypeEntity> findGoodsTypeByCondiction(GoodsTypeEntity entity);
     
     
     /**
 	 * 
 	 * 根据部门Code和所属集中开单组查询
 	 * 
 	 * @author WangQianJin
 	 * @date 2013-08-02
 	 */
 	List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(String code,String billingGroup);

 	/**
     * 
     * <p>根据外场部门编码，查询该外场的驻地可出发营业部对象</p> 
     * @author foss-panguoyang
     * @date Apr 2, 2013 2:04:42 PM
     * @param code
     * @return
     * @see
     */
	SaleDepartmentEntity queryStationLeaveOrgByOutfieldCode(String code);
	
    /**
     * 
     * <p>(返回公布价信息,根据deptNo与arrive,deptNo默认为当前营业部网点，
     * arrive为可选项查询最新公布价信息其中包含承诺时效、取货时间、
     * 重货、轻货费率、最低一票相关信息在此组装时效最新List与价格最新List)</p> 
     * @author 何龙
     * @date 2013-08-09 
     * @param deptNo-营业部编号
     * @param arrive-目的站
     * @param billDate-开单日期
     * @return List<PublishPriceVo> - 公布价集合信息
     * @see
     */
    List<PublishPriceEntity> queryPublishPriceDetail(String startDeptNo ,String destinationCode, Date billDate);
    
    /**
	 * 根据部门名称和所属集中开单组查询
	 * @author WangQianJin
	 * @date 2013-8-11 下午3:20:19
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(String name,String billingGroup);
	

	/**
	 * 
	 * <p>查询营业部的默认外场</p> 
	 * @author foss-panguoyang
	 * @date 2013-8-11 下午2:00:13
	 * @param saleCode 营业部
	 * @param productCode 运输性质
	 * @return
	 * @see
	 */
	OutfieldEntity queryDefaultTransCodeDept(String saleCode, String productCode);

	OrgAdministrativeInfoEntity queryByCodeAndServerTime(String createOrgCode);

	/**
	 * 
	 * 获取集中开单组的外场
	 * 
	 * @author 025000-FOSS-panguoyang
	 * @date 2013-8-19 下午06:17:06
	 */
	SaleDepartmentEntity queryPickupCentralizedDeptCodeByBillTime(String billingGroupOrgCode,Date billTime);

	ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode); 

	/**
	 * 通过空运总调查外场
	 * panguoyang
	 */
	String queryTransferCenterByAirDispatchCode(String lastLoadOrgCode);
	
	/**
	 * 通过发货客户编码查找发货客户能否开月结-用于更改单
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	boolean isCanPaidMethodForRfc(CusBargainVo vo);

	//DP-FOSS zhaoyiqing 343617 20161025 配合CUBC结算中心改造，校验合同部门和催款部门编码
	boolean isCanPaidMethodCUBCForRfc(CusBargainVo vo);

	/**
	 * 根据开单时间获取自提件信息
	 * @author WangQianJin
	 * @date 2013-08-02
	 * @param billDate
	 * @return
	 */
	List<MinFeePlanEntity> getMinFeePlanEntityByDate(Date billDate);


	

	/**
	 * 查询指定渠道编码和日期获取产品信息
	 * @author WangQianJin
	 * @date 2013-08-02
	 * @param channelCode
	 * @param businessDate
	 * @return
	 */
	List<ProductEntity> getProductOfMinFeePlanByChannelCodeAndSpecifiedDate(String channelCode, Date businessDate);


	
    /**
	 * 获取整车的保费相关信息
	 * @param billCalculateDto
	 * @return
	 */
	public GuiResultBillCalculateDto getProductPriceDtoOfWVHAndBF(GuiQueryBillCalculateDto billCalculateDto);
	
	/**
	 * 查询运输性质
	 * @param code
	 * @param billDate
	 * @return
	 */
	ProductEntity getProductByCache(String code, Date billDate);
	
	
	/**
	 * 根据产品Id查询产品信息
	 * @param productId
	 * @return
	 * 版本	 	 用户		时间		内容
	 * 0001		zxy			20130924	新增：BUG-55905查不到产品问题，改成在线查
	 */
	public ProductEntity selectProductByPrimaryKey(String productId);
	
	
	    /**
	 * 根据产品编码查询产品信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-12 上午9:56:17
	 */
	ProductEntity queryProductByCode(String code);

    
	/**
 	 * 根据营业部网点编码获取该营业部所在城市的快递代理理/试点城市类型
 	 * @param salesDepartmentCode
 	 * @return
 	 */
 	ExpressCityResultDto queryExpressCityTypeByOrgCode(
 			String salesDepartmentCode);

 	/**
 	 * 
 	 * @param billCalculateDto
 	 * @return
 	 */
 	List<GuiResultBillCalculateDto> queryGuiExpressBillPrice(
 			GuiQueryBillCalculateDto billCalculateDto);

	/**
	 * 根据网点编码查询快递代理网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 上午11:47:58
	 */
	OuterBranchExpressEntity queryLdpAgencyDeptByCode(String deptCode);

	WaybillDto queryWaybillByReturnBillNo(String waybillNO);
	
	/**
	 * 查询其他费用信息
	 * @param entity	查询参数
	 * @param start		开始行
	 * @param limit		每页显示数
	 * @return
	 * 版本	 	 用户		时间		内容
	 * 0001		zxy			20130927	新增：BUG-56141 查询所有其他费用明细
	 */
	public List<PriceEntity> findPriceEntityPagingByCondition(PriceEntity entity, int start, int limit);
	
	/**
	 * 查询产品类型
	 * @param entity 参数
	 * @return
	 * 
	 *  版本 		用户		时间		   内容
	 *  0001		zxy			20130929		新增：BUG-56426 可以根据code id等条件查询
	 */
	public List<ProductEntity> findProducts(ProductEntity entity);
	
	/**
	 * @author WangQianJin
	 * @date 查询数据字典
	 * @return
	 */
	public List<DataDictionaryValueEntity> queryByTermsCode(String termsCode);

	public LimitedWarrantyItemsEntity isInsurGoods(String goodsName);

	List<SaleDepartmentEntity> querySaleDeptsByBillingGroupCode(
			String billGroupCode);

	/**
	 * 判断是否需要拍大车直送
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-10-2 9:57:47
	 * @param weight
	 * @param volume
	 * @return
	 */
	boolean isVehicleDirect(String weight, String volume);

	boolean isCanPaidMethodExp(CusBargainVo vo);

	boolean isCanPaidMethodForRfcExp(CusBargainVo vo);

	WaybillExpressEntity getWaybillExpressByWaybillNo(String waybillNo);

	List<WaybillDto> queryActualFreightAndBasicExpress(WaybillDto waybillDto);

	List<DataDictionaryValueEntity> queryRefundType();

	List<DataDictionaryValueEntity> queryPaymentMode();
	OuterBranchExpressEntity queryLdpAgencyDeptByCode(String code, String active);
	
	/**
	 * 一键上报到IT服务台
	 * @author WangQianJin
	 * @return
	 */
	public ITServiceResultDto uploadItService(List<ITServiceVo> itList);
	
	/**
	 * 根据部门编码查询城市编码
	 * @param orgCode
	 * @return
	 */
	String queryCityIdByOrgCode(String orgCode);

	
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	ConfigurationParamsEntity queryConfigurationParamsByEntity(String model,String config,String orgCode);
	
	/**
	 * 获取刚提交运单时的那条运单信息
	 * WangQianJin
	 * @param waybillNo
	 */
	WaybillEntity queryWaybillForFirst(String waybillNo);
	
	/**
	 * 查询偏线公布价信息
	 * @author WangQianJin
	 * @param queryBillCacilateDto
	 * @return
	 */
    List<PublishPriceEntity> queryPublishPriceDetailForPX(QueryBillCacilateDto queryBillCacilateDto);
    
    /**
	 * 根据运单号查询是否有运单状态信息 
	 * @author WangQianJin
	 * @param waybillTransactionEntity
	 * @return
	 */
	boolean isExistWaybillTransaction(String waybillNo);  
	
	/** 
	 * 查询是否为第一票运单信息
	 * MANA-257接货费优化
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 上午11:07:04
	 */
	boolean isFirstDeliveryWaybill(WaybillEntity entity);
	/**
	 * 查询是否存在活动
	 * @param deptCode 收货部门
	 * @param billDate 开单时间
	 * @return
	 */
	boolean isExistSpecialOffer(String deptCode,Date billDate);
	/**
	 * 通过部门编码\业务时间,查询大礼包名称(List<entity>) 
	 * @return
	 */
	List<CityMarketPlanEntity> searchCityMarketPlanEntityList(String orgCode,
			Date billDate);
	/**
	 * 通过大礼包编码、业务时间、查询大礼包信息entity
	 * @param cityMarketPlanCode
	 * @param billDate
	 * @return
	 */
	CityMarketPlanEntity getCityMarketPlanEntityCode(String code,String deptCode, Date date); 
	
	/**
	 * 精确查询行政区域
	 * @param code
	 * @return
	 * 版本   		时间			用户			内容
	 * 001		20140428    zxy			新增:MANA-2018
	 */
	public AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code);
	
	/**
     * 
     * 获取空运预计出发时间
	 * 版本   		时间			用户			内容
	 * 001		20140428    zxy			新增:MANA-2018
	 */
	public Date getAirLeaveTime(String orgCode , Date flightTime , String flightNumberType);
	/** 
	 * 查询该发货客户当天是否收取过接货费
	 * @author WangQianJin
	 * @date 2014-05-04
	 */
	boolean queryIsGetPickFeeByCust(WaybillEntity entity);	

	/**
	 * 获取CRM营销活动信息
	 * @创建时间 2014-4-16 下午7:46:54   
	 * @创建人： WangQianJin
	 */
	CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo pdaParamDto);
	
	/**
	 * 获取营销活动折扣信息
	 * @创建时间 2014-4-22 上午10:51:48   
	 * @创建人： WangQianJin
	 */
	MarkActivitiesQueryConditionDto getActiveDiscountInfo(MarkActivitiesQueryConditionDto entity);
	
	/**
	 * 根据客户编码获取客户信息
	 * @创建时间 2014-4-22 下午1:24:08   
	 * @创建人： WangQianJin
	 */
	CustomerDto queryCustInfoByCode(String custCode);
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo);
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlPendingEntity queryPendingActiveInfoByNoAndType(String waybillNo);
	
	/**
	 * 根据活动编码获取活动信息
	 * @创建时间 2014-6-12 上午10:09:51   
	 * @创建人： WangQianJin
	 */
	MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode);

	/**
	 * 查询整车价格参数方案
	 * @author 076234-foss-PanGuoYang
	 * @param date
	 * @param invoceType
	 * @param code
	 * @return
	 */
	CarloadPricePlanDto queryByConfCode(Date date, String invoceType,
			String code);
	
	/**
     * 根据部门编码与运输性质获取信息数量
     * @param entity
     * @author WangQianJin
     * @return
     */
    int queryCountByCodeAndProduct(SalesProductEntity entity);
	/**
	 * 判定偏线时效方案是否存在数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-25 09:11:08
	 */
	boolean identityOuterEffectivePlan(String departDeptCode,
			String arriveDetpCode, String productCode, Date createTime);

	List<PublishPriceEntity> queryPublishPriceDetailForSalesAndPx(
			String originalOrgCode, String destinationOrgCode); 
	
	/**
	 * 根据订单号查询订单表对应订单的类型
	 * @param orderNo
	 * @return
	 */
	String queryWaybillTypeByOrderNo(String orderNo);
	
	/**
	 * 定价优化项目
	 * 
	 * 根据优惠券编码获取区域线路限制
	 * 
	 * @author Foss-206860
	 */
	PendingSendCouponLogEntity queryLineAreaByNum(String couponNum);



	/**
	 * 根据运单Id查询图片
	 * queryImageByWaybillId: <br/>
	 * 
	 * Date:2014-10-20下午1:45:33
	 * @author 157229-zxy
	 * @param waybillNoId
	 * @return
	 * @since JDK 1.6
	 */
	
	/**
	 * 图片开单查询
	 * @param condiction
	 * @return
	 */
	List<SearchPictureVo> searchPictureWaybillByCondiction(SearchPictureWaybillCondiction condiction);
	
	/**
	 * 按运单号查询图片开单
	 * @param waybillNo
	 * @return
	 */
	WaybillPictureEntity queryWaybillPictureByWaybillNo(String waybillNo);
	
	/**
	 * 按运单号ID查询图片开单
	 * @param waybillNo
	 * @return
	 */
	WaybillPictureEntity queryWaybillPictureById(String waybillId);
	
	/**
	 * 根据司机工号判断是否完成接送货
	 * @param driverCode
	 * @return
	 */
	boolean querySumGoodsInfoByParams(String driverCode);
	
	/**
	 * 是否打印了标签
	 * @param waybillNo
	 * @return
	 */
	public boolean isPrintLabel(String waybillNo);
	/**
	 * 图片开单推送信息
	 * @param baiDuId 
	 */
	void pushMessage(int pushType,int messageType,int deviceType,String deviceId,String title,String description ,String value);
	
	/**
	 * 通过运单号查询费用
	 * @param waybillNo
	 * @return
	 */
	List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo);
	int saveWaybillPushMessage(WaybillPushMessageEntity e);
	
	/**
	 * 图片开单发送短信
	 * @param notificationEntity
	 */
	void sendDriverCodeSms(NotificationEntity2 notificationEntity);

    /**
     * 通过原单查询运单信息
     * @param originalWaybillNo
     * @param returnType
     * @return
     */
	public WaybillExpressEntity queryWaybillByOriginalWaybillNo(
			String originalWaybillNo, String returnType);
	/**
	 * 根据原单号查询单号信息
	 * @param originalWaybillNo
	 * @author foss-206860
	 * */
	public List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(
			String originalWaybillNo);
	/**
	 * 通过运单号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	public WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo,
			String returnType);

	/** 
	 * 验证运单签收变更、反签收申请情况
	 */
	public void checkWayBillRfcStatus(String originalWaybillNo);
    /**
     * 查询运单出发更改状态
     * @param id
     */
	public void checkWayBillChange(String id);
	/**
	 * 库存状态
	 * @param originalWaybillNo
	 * @param entity
	 * @return 
	 */
	public List<StockEntity> queryStockByWaybillNo(String originalWaybillNo);
    /**
     * 是否签收
     * @param waybillNo
     * @return
     */
	public boolean queryWaybillSignResultByWaybillNo(String waybillNo);

	/**
	 * 
	 * submitReturnGoodsApplyToCRM:(这里用一句话描述这个方法的作用). <br/>  
	 * 提交申请到CRM.<br/>  
	 *  
	 * @author 146831  
	 * @param crmReturnedGoodsDto
	 * @return  
	 * @since JDK 1.6
	 */
	public boolean submitReturnGoodsApplyToCRM(CrmReturnedGoodsDto crmReturnedGoodsDto);
    /**
     * 是否存在付款信息
     * @param originalWaybillNo
     * @return
     */
	public boolean isExistRepayment(String originalWaybillNo);
	/**
	 * 网上支付是否支付
	 * @param waybillNos
	 * @return
	 */
	public List<String> unpaidOnlinePay(List<String> waybillNos);
	/**
	 * 应收到付
	 * @param originalWaybillNo
	 * @return
	 */
	public BigDecimal queryFinanceSign(String originalWaybillNo);
   
	
	
	/**
	 * 返货管理——查询返货申请工单
	 */
	public List<CrmReturnedGoodsDto> queryReturnGoodsWorkOrder(
			CrmReturnedGoodsDto vo);
	/**
	 * 返货管理——查询返货申请工单结果返货
	 */
	public List<CrmReturnedGoodsDtoResult> queryReturnGoodsWorkOrderResult(
			CrmReturnedGoodsDtoResult vo);
       	
	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	public List<ExpBatchChangeWeightDto> queryExpBatchChangeWeightResult(
			ExpBatchChangeWeightQueryDto dto);

	/**
	 * 
	 * 批量执行重量更改（快递）
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
	 * 
	 */
	public void commitBatchChangeWeight(
			List<ExpBatchChangeWeightDto> expBatchChangeWeightDto);
	/**
	 * 批量导入重量体积
	 * @param expBatchChangeWeightDto
	 */
	public void commitLTLBatchChangeWeight(
			List<LTLEWaybillChangeWeightDto> expBatchChangeWeightDto);
	/**
	 * 查询导入的结果信息
	 * @param dto
	 * @return
	 */
	public List<LTLEWaybillChangeWeightDto> queryLTLEWaybillChangeWeightResult(LTLEWaybillChangeWeightQueryDto dto);
	/**
	 * 异常货不允许返货开单
	 * @param originalWaybillNo
	 * @return
	 */
	public boolean queryExcepSignResultByWaybillNo(String originalWaybillNo);
	/**
	 * 是否有外发交接单
	 * @param originalWaybillNo
	 * @return
	 */
	public boolean queryBeLdpHandOveredByWaybillNo(String originalWaybillNo);
	 /**
     * 返货开单页面，根据原单号在暂存表查询运单号
     * @param originalWaybillNo
     * @return 
     */
	String getWaybillNo(String originalWaybillNo);
	
	
	/**
	 * 检查订单号码在暂存中是否符合规范
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */
	boolean checkWaybillPendingOrderNo(String orderNo);

	/**
	 * DMANA-9463 整车运单未签收提示功能
	 * @author 218438-foss-zhulei
	 */
	List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto);
	
	/**
	 * 根据当前时间查询当前月份所有内部员工发货的优惠金额
	 * dp-foss-dongjialing
	 * 225131
	 */

	public BigDecimal queryDiscountFeeByEmployeeNo(String employeeNo,
			Date recevieDate);
	public List<InempDiscountPlanEntity> queryInempDiscountPlanEntity(
			Date recevieDate);

	public List<DataDictionaryValueEntity> queryDeliveryMode();
	
	/**
	 * 根据员工信息查询
	 * @author 218438-foss-zhulei
	 */
	public List<EmployeeEntity> queryEmployeeExactByEntity4Selector(EmployeeEntity employeeEntity, int start, int limit);
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String orgCode);
	void updateWaybill(WaybillEntity waybill);
	void updatePengdingWaybill(WaybillPendingEntity entity);	
	
	/**
	 * 根据客户编码查询客户信息
	 * @param customerEntity
	 * @return
	 */
	public CustomerEntity queryCustInfoByCustomerEntity(CustomerEntity customerEntity);

	String selectWaybillNo(String waybillNo);

	List<WaybillPendingEntity> queryPending(WaybillPendingDto waybillPendingDto);

	void savePengdingWaybill(List<WaybillPendingEntity> pendingList);

	public void saveWoodenRequirePending(
			List<WoodenRequirePendingEntity> woodenRequireList);


	void saveLabeledGood(List<LabeledGoodEntity> labelGoodList);


	WoodenRequirePendingEntity queryWoodenRequireByNo(String waybillNo);

	List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo);

	public ProductEntity findProductByName(String productName);
	
	List<DataDictionaryValueEntity> selectProductCodeDataDictValue(String termsCode);

	public String queryBillingByStore(String code);

	public void deleteWoodByWaybillNos(List<String> waybillNos);

	void deleteWoodByWaybillNo(String waybillNo);
	BigDecimal queryTotalFeeByDelevyCode(String code, Date recevieDate);

	BigDecimal queryDiscountInfo(String waybillNo);
	/**
	 * <th>
	 * 在线判定是否快递
	 * </th>
	 * @author Foss-105888-Zhangxingwang
	 * @param billTime 
	 * @date 2015-3-31 20:22:18
	 * @param maps
	 * @return
	 */
	boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);
	
	/**
	 * <th>
	 * 根据部门编码,一级产品查询所有对应的三级产品
	 * </th>
	 * @author Foss-105888-Zhangxingwang
	 * @param billTime 
	 * @date 2015-3-31 20:22:18
	 * @param maps
	 * @return
	 */
	List<ProductEntity> getAllProductEntityByDeptCodeForCargoAndExpress(String deptCode, String typeCode, Date billTime);
	
	
		//查询当前货物的入库记录--206860
	public List<InOutStockEntity> queryInStockInfo(String waybillNo,
			String serialNo, String orgCode, Date createBillTime);
	
	//根据运单号查询变更记录--206860
	List<WaybillRfcEntity> queryWaybillRfcAcceptByWaybillNo(String waybillNo);
	
	/**
	 * 插入转运或返货目的站记录
	 * 
	 * @author foss-206860
	 */
	public void addWaybillRfcTransferEntity(WaybillRfcTranferEntity waybillRfcTranferEntity);
	
	public List<WaybillRfcTranferEntity> queryWaybillRfcTransferEntity(
			WaybillRfcTranferEntity waybillRfcTranferEntity);
	
	public List<WaybillRfcTranferEntity> queryRfcTransferHistory(
			WaybillRfcTranferEntity waybillRfcTranferEntity);
	
	public List<HandOverBillDetailEntity> queryHandoverBillDetailByWaybillNoAndOrgCord(String waybillNo,
			String orgCode);
		
	
		
	/**
	 * 获取快递外发保价申明价值上限
	 * @author 153687-foss-fengzhang
	 * @date 2015-07-03 19:52:00
	 */
	 ConfigurationParamsEntity queryMaxInsuranceAmount(String code);
	 
	 /**
	  * 根据Code查找数据字典
	  * @param termsCode
	  * @param valueCode
	  * @return
	  */
	 DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode, String valueCode);

	List<InOutStockEntity> queryInStockInfo(String waybillNo,Object object, String customerPickupOrgCode, Date billTime);
	/**
	  * @Description: :检查预配线路是否预警线路
	  * @author hbhk 
	  * @date 2015-8-20 上午8:10:59 
	  * @param receiveOrgCode
	  * @param targetOrgCode
	  * @return
	  */
	String checkWarningLine(String receiveOrgCode,String targetOrgCode);
	
	/**
	 * 通过调用GIS的详细地址匹配接口查询是否展会货
	 * @author foss-218438
	 */
	String isExhibitCargo(ExhibitionKeywordEntity exhibitionKeyword);


	List<CusLtDiscountItemDto> queryLtDiscountBackInfo(String customerCode,
			Date date);

	 
	/**
	 * 查询最新的未受理返货工单
	 * @author LiZhongLin
	 * @param condition
	 * @return 
	 */
	 boolean queryReturnGoodsRequestEntityByCondition(String waybillNo);
	 
	 /**
	  * 根据单号 配载部门查询是否在第一外场出库
	  * @param waybillNo
	  * @param loadOrgCode
	  * @return
	  */
	public boolean checkProduct(String waybillNo, String loadOrgCode,Date billTime);

	 
	 /**
	  * 根据特殊增值服务查询提货方式
	  * @return
	  */
	public List<DataDictionaryValueEntity> querySpecialPickUp();

	/**
	 * 查询特殊增值服务
	 * @return
	 */
	public List<DataDictionaryValueEntity> querySpecialValueAddedServiceMode();


	 //查询中转走货路径
	TransportPathEntity queryTransportPath(String waybillNo);

	//通过组织名字查询组织code--206860
	List<OrgAdministrativeInfoEntity> querySimpleOrgAdministrativeInfoByEntity(OrgAdministrativeInfoEntity entity, int start, int limit);

	/**
	 * 根据部门名称和所属集中开单组查询
	 * @param name 要进行模糊查询的部门名称
	 * @param billingGroup 当前开组编号
	 * @param waybillNo 运单号
	 * @return 收货部门实体
	 * @author 272311
	 * @date 2015.10.13
	 */
	public List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralizedExp(
			String name, String billingGroup, String waybillNo);


	/**
	 * 家装网点查询营业部
	 * @return
	 */
	 List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntity(String countyId);
	 /**
	  * 家装网点查询营业网点
	  * @param countyId
	  * @return
	  */
	 SaleDepartmentEntity querySaleDepartmentEntity(String countyId);

	 List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNo(
			String waybillNo, String type);
	 
	 /**
	  * 根据原单号查询上报信息
	  * @param originalWaybillNo
	  * @return
	  */
	public ReturnGoodsRequestEntity queryReturnGoodsRequestEntityByWayBillNo(
			String originalWaybillNo);
	
	/**
	 * 查询返货信息
	 * @param waybillNo
	 * @return
	 */
	public WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo);
	/**
	 * 收银员当前密码到期提醒
	 * @return
	 */
	public int queryLeftDaysOfPsw();

	/**
	 * 从综合查询代收上限
	 * @param code
	 * @return
	 * @author:280747-foss-zhuxue
	 * @date:2015/10/29
	 */
	public SaleDepartmentEntity querySaleDepartmentByCodeNoCache(String code);
public List<ProductEntity> getAllExpressOrCargoProductInfo(String typeCode);	
	/**
	 * 根据参数查询是否是子母件以及其子母件信息
	 * 
	 * @author foss-206860
	 * */
	TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(
			String waybillNo);





	/**
	 *根据运单号查询工单申请数据
	 *@param waybillNo
	 *@author foss-206860 
	 */
	List<ReturnGoodsRequestEntity> queryReturnGoodsWorkOrderTWO(String waybillNo);

	/**
	  * 根据运单号 流水号查询是否少货找到
	  * 
	  * @author foss-206860
	  */
	public String queryLostFindGoods(String waybillNo);
	/**
	 * 根据运单号查询是否在丢货管理组
	 * 
	 * @author foss-206860
	 * */
	QmsYchExceptionReportEntity isLoseGroup(String waybillNo);
	

	//根据运单号、流水号、部门编码、以及货区编码查询该单号是否在提供的部门库存内
	public StockEntity queryStockEntityByNos(String orgCode, String waybillNo,
			String serialNO, String goodsAreaCode);

	/**
	 *  根据运单号查询同一子母件下的所有运单信息
	 *  @author 218459-foss-dongsiwei
	 * @param waybillNo
	 * @return
	 */
	public List<WaybillRelateDetailEntity> queryWaybillRelateByWaybillNo(String waybillNo);
	
	
	public Boolean queryArriveSheetByWaybillNo(String waybillNo);
	
	
	/**
	 * 根据机构号集合返回机构对应的集合名称
	 * @author Foss-278328-hujinyang
	 * @date 2015-12-15 15:28:10
	 * @param orgs	机构号集合
	 * @return  Map<code,orgName>  
	 */
	public Map<String, String> queryOrgNameMapsByCodes(List<String> orgs);
	
	
	/**
	 * 根据代理网点编码集合返回机构对应的代理网点名称
	 * @author Foss-278328-hujinyang
	 * @date 2015-12-16
	 * @param orgs
	 * @return
	 */
	public Map<String, String>  queryAgentNameMapsByAgentCode(List<String> orgs);
	/**
	 * 根据编码查询配置参数
	 * */
	String  queryConfValueByCode(String code);
	String queryOrderByOrderNo(String orderno);
	
	/**
	 * 根据 编码 查询 配置信息
	 * @param powerCode 编码
	 * @return  配置信息
	 * @author 272311-sangwenhao
	 * @date 2016-1-6
	 */
	public ConfigurationParamsEntity queryConfigurationParamsOneByCode(String powerCode) ;
	
	
	public List<WaybillEntity> queryWaybillForPrint(WaybillDto waybillDto);

	/**
	 * 校验运单是否在派送中
	 * @param waybillNo
	 */
	public boolean  queryArriveSheetListDeliver(String waybillNo);
	
	List<SortingScanEntity> queryEwayBillRecords(SortingScanDto sortingScanDto);
	/*public void updateOrderRefuseState(String originalWaybillNo, String orderNo,
			String deliveryCustomerCode);*/
	/**
	 * 根据订单号查询serviceType
	 * @param orderNo
	 * @return
	 */
	public String queryWaybillServiceTypeByOrderNo(String orderNo);
	//合伙人月结开单，校验资金额度 2016年1月23日 18:16:38 葛亮亮
	SynPartenerMonthlyLineDeductResponse partenerMonthlyLineDeduct(PartenerDeductDto deductDto);
	
	 /**
     * 通过返单查询
     * @param originalWaybillNo
     * @param returnType
     * @return
     */
	public List<WaybillExpressEntity> queryReturnWaybillNoInfo(String returnWaybillNo);
	
	/**
	 * 根据编码查询详细价格条目
	 * @param entryCode 编码
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-3-14
	 */
	public PriceEntity queryPriceEntryByCode(String entryCode) ;

	public void updateOrderRefuseState(String originalWaybillNo,
			String orderNo, String deliveryCustomerCode);
	//合伙人预存款资金开单校验 2016年1月25日 14:29:18 葛亮亮
	SynPartenerPrestoreDeductResponse partenerPrestoreDeductResponse(PartenerPrestoreDeductDto prestoreDeductDto);
	
	//合伙人代收货款上限额度校验 2016年1月28日 13:52:38 葛亮亮
	SynPartenerCodAmountUpperLimitResponse partenerCodAmountUpperLimitResponse(PartenerCodAmountUpperLimitDto partenerCodAmountUpperLimit);
	
	/**
	 * 根据部门编码在线查询营业部信息 优先读取缓存 如果没有则读数据库
	 * @param code
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-2-24
	 */
	SaleDepartmentEntity querySimpleSaleDepartmentByCodeCache(String code);
	
	//根据运单ID获取折前信息 2016年4月15日 14:51:53 葛亮亮
	PartnersWaybillEntity queryPartnersWaybillEntityById(String wayBillNoId);
	
	public Boolean queryISPICPACKAGEByWaybillNo(String waybillno);
	
	/**
	 * 根据订单号查询订单所有信息
	 * @param <DispatchOrderEntity>
	 * @param orderNo
	 * @return
	 */
	public DispatchOrderChannelNumberEntity queryWaybillTypeEntityByOrderNo(String orderNo);
	/**
	 * 查询零担电子运单信息
	 */
	public List<LTLEWaybillQueryResultDto> queryOmsOrderOrLabelStatusByWaybillNo(LTLEWaybillQueryResultDto ltleWaybillQueryResultDto);
	/**
	 * <p>查询营业部信息，不关联组织名称等只查询基础信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午8:51:55
	 * @param code
	 * @return
	 * @see
	 */
	SaleDepartmentEntity querySaleDepartmentNoAttach(String code);

	
	/**
	 * 根据单号查询打木信息
	 *@date zhuxue
	 */
	WoodenRequirementsEntity queryWoodenWaybillRequirement(String waybillNo);
	
	/**
	 * 查询运输性质
	 * @param code
	 * @return
	 */
	String getProductByCache(String code);
	
	/**
	 * @author 306486
	 * @date 2016年6月16日
	 * @param waybillNo
	 * @return
	 */
	public String queryPickupGoodsByWaybillNo(String waybillNo);
	
	boolean greenHandWrapWriteoffService(String waybillNo);
	
	/**
	 * 通过运单号获取运单实体信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:07:57
	 */
	WaybillEntity queryNewWaybillBasicByNo(String waybillNo);
	
	/**
	 * FOSS校验悟空快递单号
	 * @param map
	 * @return
	 * @author 272311- sangwenhao
	 */
	boolean validateWaybillNoIsCorrect(Map<String, String> map) ;
	/**
	 * 推送标签状态
	 * @param waybillList
	 */
	public void pushLabelStatus(List<String> waybillNoList);
	
	/**
	 * <p>根据营业部网点code查询进仓地址信息</p>
	 * @author 354805-taodongguo
	 * @date 2016-9-12 17:28:26
	 * @param depCode
	 * @return
	 */
	public List<DepotAddressEntity> queryDepotAddressByDepCode(String depCode) ;
	

	/**
	 * 
	 * service层，查询每票货物的木架区库存、是否已包装信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:11:47
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService#queryUnpackDetails(java.lang.String)
	 */
	public List<SwiftNumberInfoDto> queryUnpackDetailsss(String waybillNo);
	
	/**
	 * 合伙人计算加收费
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-1 上午10:39:55
	 * @param targetOrgCode
	 * @param billTime
	 * @param billingtype
	 * @param volume
	 * @param weight
	 * @return
	 * @see
	 */
	AddedPlanFeeCalculateDto caculateAddFee(String targetOrgCode,Date billTime,
			String billingtype,BigDecimal volume,BigDecimal weight,String createOrChange);

	
	/**
	 * 查询在线运单打印次数
	 * 
	 * @author foss-zhuxue
	 * @date 2016-10-13 
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public int queryPrintTimesByWaybill(String waybillNo);
	/**
	 * 更新运单打印表中打印状态
	 * zhuxue
	 */
	int updateByPrimarySelective(PrintInfoEntity record);

	/**
	 * 查询运单是否是合伙人开单
	 * 280747
	 * @param WaybillNo
	 * @return
	 */
	String queryWaybillForPrintPartner(String waybillNo);
	
	/**
	 * 根据到达部门编码查询对接外场
	 * 280747
	 */
	String selectqueryDeptDeptTransferForName(String code);
	
	/**
	 * 查询网点是否有开装卸费权限
	 * @author 354805-taodongguo
	 * @date 2016-12-7 10:15:01
	 * @param code 装卸费开单部门
	 * @param billTime 开单时间
	 * @return canPayServiceFee 是否可开装卸费
	 */
	String queryCanPayServiceFeeByCodeAndTime(String code , Date billTime);

	/**
	 * 根据发货客户编码和时间查询客户信息
	 */
	public CustomerCircleNewDto queryCustomerByCusCodegetByCustCode(
			String provinceCode, Date date, String active);	
	/**
	 * 根据gis网点匹配条件查询目的网点信息
	 * 
	 * @author 321993 zhangdinahao
	 * @date 2017-03-16 下午 21:35:53
	 */
	HisSegMatchResponse queryStationInfo(HisSegMatchRequest request);

	
	/**
	 * 根据条件查询推送CUBC接口日志
	 * @param queryDto
	 * @return
	 */
	public List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto);
	/**
	 * 重新推送运单信息至CUBC
	 * @param cubcLogEntitys
	 */
	public void pushWaybillToCUBC(List<WaybillLogEntity> cubcLogEntitys);

	/**
	 * author 306486
	 * 查询分拣开关
	 */
	List<ConfigurationParamsEntity> queryConfigurationParamsBatchByCode(String [] str);

	public IsCustomerCircleDto queryIsCustomerCircle(String code,Date date);

	 /**
	 *远程调用WK接口
	 * 根据手机号码查询收货人联系方式和地址信息
	 * @param mobilePhone
	 * @date 2017-03-17 下午 14:45
	 * @return
	 */
	List<CustomerAddressDto> queryConsignee(String mobilePhone);
	
	
}