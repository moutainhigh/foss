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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/IWaybillService.java
 * 
 * FILE NAME        	: IWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
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
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.IsCustomerCircleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.service.IWaybillStockService;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.DispatchOrderChannelNumberEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerCodAmountUpperLimitResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerMonthlyLineDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerPrestoreDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureSendSmsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDtoResult;
import com.deppon.foss.module.pickup.waybill.shared.dto.CustomerAddressDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerCodAmountUpperLimitDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerPrestoreDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOfflineDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.request.HisSegMatchRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.HisSegMatchResponse;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;

/**
 * 
 * 运单所有的业务操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-16 下午04:13:46,
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-16 下午04:13:46
 * @since
 * @version
 */
public interface IWaybillService { 

	/**
	 * 
	 * <p>
	 * (查询运输性质)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	List<ProductEntity> queryTransType(String salesDeptId);

	/**
	 * 
	 * （查询提货方式-空运）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-22 下午04:09:11
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
	 * <p>
	 * (查询对外备注)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午07:05:16
	 * @return
	 * @see
	 */
	List<DataDictionaryValueEntity> queryOuterRemark();

	/**
	 * 
	 * <p>
	 * (查询退款类型)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午07:10:03
	 * @return
	 * @see
	 */
	List<DataDictionaryValueEntity> queryRefundType();

	/**
	 * 
	 * <p>
	 * (查询返单类型)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午07:10:06
	 * @return
	 * @see
	 */
	List<DataDictionaryValueEntity> queryReturnBillType();

	/**
	 * 
	 * <p>
	 * (查询付款方式)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午07:24:23
	 * @return
	 * @see
	 */
	List<DataDictionaryValueEntity> queryPaymentMode();

	/**
	 * 
	 * <p>
	 * (是否限保物品)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-17 下午03:42:15
	 * @param goodsName
	 * @return
	 * @see
	 */
	LimitedWarrantyItemsEntity isInsurGoods(String goodsName);

	/**
	 * 
	 * <p>
	 * (是否禁运物品)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-17 下午03:49:31
	 * @param goodsName
	 * @return
	 * @see
	 */
	boolean isProhibitGoods(String goodsName);
	
	/**
	 * 
	 * 查询所有禁运物品
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-17 下午03:49:31
	 * @param goodsName
	 * @return
	 * @see
	 */
	List<ProhibitedArticlesEntity> queryAllProhibitGoods();
	
	/**
     * 
     * <p>根据类型查询有效的禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @param goodsName
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryProhibitGoodsByType(String type);
    
    /**
     * 
     * <p>获取汽运禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryProhibitGoodsForAutomobile();

	/**
	 * 
	 * <p>
	 * (检查单号是否存在)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-17 下午05:26:27
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @see
	 */
	boolean checkWaybillNo(String waybillNo);
	
	/**
	 * 
	 * <p>
	 * (检查单号是否存在)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-17 下午05:26:27
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @see
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
	 * 检查单号连续性是否超过100
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-9 下午03:41:31
	 * @param waybillNo
	 * @return
	 */
	boolean checkWaybillNoInterval(String waybillNo);

	/**
	 * 
	 * <p>
	 * (是否贵重物品)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-19 下午03:21:00
	 * @param goodsName
	 * @return
	 * @see
	 */
	boolean isValuablesGoods(String goodsName);

	/**
	 * 
	 * （查询限保物品限保金额）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 上午08:58:14
	 */
	String queryLimitAmount(String goodsName);

	/**
	 * 根据dto封装的查询条件查询客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午7:13:55
	 */
	List<CustomerContactDto> queryCustomerInfo(
			CustomerQueryConditionDto condition);

	/**
	 * 
	 * （判断是否需要大车直送）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-24 下午05:02:51
	 */
	boolean isVehicleDirect(String weight, String volume);

	/**
	 * 
	 * （根据件数、体积、保价判断是否贵重物品）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-25 上午10:39:52
	 */
	boolean isValueGoods(String goodsName,int goodsNum, String goodsVolume, String goodsValue);

	/**
	 * 
	 * （查询走货路径）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-29 下午03:25:14
	 */
	FreightRouteEntity queryRouteBySaleDept(String transType,
			String departureSaleDept, String arrivalSaleDept);

	/**
	 * <p>
	 * (根据运单号查询WaybillEntity)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午2:07:35
	 * @param number
	 * @return
	 * @see
	 */
	WaybillEntity queryWaybillByNumber(String number);

	/**
	 * 
	 * 获取打木架体积计算系统参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-30 下午03:01:16
	 */
	String queryGoodsPackingVolume(String orgCode,String config,String model);

	/**
	 * 
	 * 查询计费类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 上午11:32:26
	 */
	List<DataDictionaryValueEntity> queryBillingWay();

	/**
	 * 
	 * 判断货物的重量体积比值是否在区间内
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-1 下午01:46:43
	 */
	boolean isWeightByVolume(String goodsWeight, String goodsVolume);

	/**
	 * 
	 * 运单提交
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-2 上午10:12:45
	 */
	void submitWaybill(WaybillDto waybill);

	/**
	 * 
	 * 运单暂存
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-26 下午08:37:46
	 */
	void tempSaveWaybill(WaybillPendingDto waybillDto);

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
	 * 通过价格规则编码查询价格规则
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-10 上午09:42:55
	 */
	PriceRuleEntity queryPriceRuleByCode(String code, Date billTime);

	/**
	 * 
	 * 计算运费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午10:58:49
	 */
	List<ProductPriceDto> queryProductPriceList(QueryBillCacilateDto queryDto);

	/**
	 * 
	 * 计算增值服务
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午10:57:44
	 */
	List<ValueAddDto> queryValueAddPriceList(
			QueryBillCacilateValueAddDto queryDto);

	/**
	 * 
	 * 查询订单类型
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-13 上午10:55:43
	 */
	List<DataDictionaryValueEntity> queryOrderType();

	/**
	 * 
	 * 查询订单受理状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-13 上午10:56:00
	 */
	List<DataDictionaryValueEntity> queryOrderStatus();

	/**
	 * 获取标签打印信息远程接口 获得LIST
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-14 下午7:18:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	List<BarcodePrintLabelDto> getLabelPrintInfo(String waybillNo,
			List<String> serialNos);

	/**
	 * <p>
	 * 通过运单号查询所有流水号
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo);
	
	/**
	 * 按照运单号查询暂存流水号
	 * @author 097972-foss-dengtingting
	 * @date 2013-4-15 下午7:16:49
	 */
	List<LabeledGoodPDAEntity> querySerialByByWaybillNo(String waybillNo);

	/**
	 * 通过传入手机电话号码、部门Code，返回部门的客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-15 下午9:02:26
	 */
	List<CustomerContactDto> queryCustInfoByPhone(
			CustomerQueryConditionDto conditionDto);

	/**
	 * 
	 * 根据ID查询数据字典
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-6 上午11:11:56
	 */
	DataDictionaryValueEntity queryDataDictoryValueEntity(String termsCode,
			String valueCode);

	/**
	 * 
	 * <p>
	 * (查询运输性质)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	ProductEntity queryTransTypeById(String transTypeId);

	/**
	 * 
	 * （查询内部发货方式）
	 * 
	 * @author 025000-FOSS-guohao
	 * @date 2015-04-13 上午10:32:11
	 */
	List<DataDictionaryValueEntity> queryInternalDeliveryType();
	
	/**
	 * 查询特殊增值服务
	 * author 254615-foss-mabinliang
	 * @date 2015-08-10 上午15:32:11
	 */
	List<DataDictionaryValueEntity> querySpecialValueAddedServiceType();
	
	/**
	 * List<DataDictionaryValueEntity> queryInternalDeliveryType();
	 * 查询CRM订单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-14 上午9:14:44
	 */
	CrmOrderInfoDto queryCrmOrder(CrmOrderQueryDto queryVo);

	/**
	 * 
	 * 导入CRM订单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-14 上午9:14:44
	 */
	CrmOrderDetailDto importOrder(String orderNumber);

	/**
	 * 
	 * 查询产品时效
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 下午01:59:42
	 * @param originalOrgCode
	 *            出发部门
	 * @param destinationOrgCode
	 *            到达部门
	 * @param productCode
	 *            产品code
	 * @parm billDate 开单日期 可空 ，默认为当前时间
	 * 
	 */
	List<EffectivePlanDto> queryEffectivePlanDetailList(String originalOrgCode,
			String destinationOrgCode, String productCode, Date billDate);

	/**
	 * 更新在线运单打印次数 old
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int updateWaybillPrintTimesOnLine(String waybillNo);

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
	 * 根据出发部门、到达部门、产品类型、开单时间（数据生成时间）获得预计出发时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-19 下午3:40:43
	 */
	Date searchPreLeaveTime(String departDeptCode, String specialLine,
			String productCode, Date createTime);

	/**
	 * 根据预计出发日期、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺自提时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:06
	 */
	EffectiveDto searchPreSelfPickupTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime);

	/**
	 * 根据承诺自提时间、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺派送时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:49
	 */
	EffectiveDto searchPreDeliveryTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime);

	/**
	 * <p>
	 * 功能：按code查询组织部门实体
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-7 下午7:24:32
	 * @param id
	 * @return
	 * @see
	 */

	OrgAdministrativeInfoEntity queryByCode(String code);
	
	/**
	 * 按标杆编码查询组织部门实体
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-1 下午4:16:21
	 */
	OrgAdministrativeInfoEntity queryOrgByUnifiedCode(String unifiedCode);
	
	
	/**
	 * 按标杆编码查询组织部门实体 简化
	 * @author huangwei
	 * @date 2016-10-11 下午4:16:21
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeClean(String unifiedCode);

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-31 下午4:9:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
	 */

	AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code);

	/**
	 * 
	 * 根据产品编码与营业日期来筛选产品
	 * 
	 * @author Foss-jiangfei
	 * @date 2012-11-21 上午8:32:52
	 */

	ProductEntity getProductByCache(String productCode, Date billDate);

	/**
	 * 根据手机或电话号码查询历史发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-22 下午7:05:31
	 */
	List<CustomerContactDto> queryHisDeliveryCusByPhone(
			CustomerQueryConditionDto conditionDto);

	/**
	 * 根据手机或电话号码查询历史收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-22 下午7:04:40
	 */
	List<CustomerContactDto> queryHisReceiveCusByPhone(
			CustomerQueryConditionDto conditionDto);

	/**
	 * 
	 * 查询配载部门和最终配载部门信息以及走货线路
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午03:38:10
	 */
	OrgInfoDto queryLodeDepartmentInfo(boolean pickupCentralized,
			String orginalOrganizationCode, String destinationOrganizationCode,
			String productCode);

	/**
	 * 根据客户编码客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-28 下午10:36:15
	 */
	List<QueryMemberDialogVo> queryCustInfoByCode(String custCode);

	/**
	 * 判断能否欠款
	 * 
	 * 使用说明，判断能否欠款均使用同一方法，如果付款方式为月结时请提供客户编码，欠款方式为临欠是请提供组织编码
	 * 
	 * @author 025000-FOSS-helong
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
	DebitDto isBeBebt(String customerCode, String orgCode, String debtType,
			BigDecimal debtAmount);

	/**
	 * 开单提交后 调用的运单打印服务
	 * 
	 * @author FOSS-jiangfei
	 * @date 2012-11-26 下午03:38:10
	 */
	void printWaybillFirstTime(WaybillEditUI ui,String printTemplates);

	/**
	 * 
	 * <p>
	 * getWaybillStockService<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29 void
	 */
	IWaybillStockService getWaybillStockService();

	/**
	 * 始发库存部门
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-5
	 * @param waybillEntity
	 * @return
	 * @return String
	 * @see
	 */
	String queryStartStocksDepartmentService(WaybillEntity waybillEntity);

	/**
	 * 到达库存部门
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-5
	 * @param waybillEntity
	 * @return
	 * @return String
	 * @see
	 */
	String queryEndStocksDepartmentService(WaybillEntity waybillEntity);

	/**
	 * 根据运单号查询待处理运单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-5 下午9:08:45
	 */
	WaybillPendingDto queryPendingWaybill(String waybillNo);

	/**
	 * 
	 * 根据客户编码查询客户银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:43:42
	 */
	List<CusAccountEntity> queryBankAccountByCode(String customerCode);
	
	/**
	 * 
	 * 根据银行编码查询银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:43:42
	 */
	BankEntity queryBankByCode(String code);

	/**
	 * 获取标签打印信息远程接口
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-14 下午7:18:34
	 * @param waybillNo 运单号
	 * @param waybillstatus 运单状态
	 * @return
	 * @see
	 */
	BarcodePrintLabelDto getLabelPrintInfos(String waybillNo,String waybillStatus);

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
	 * 根据部门编码查询部门信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-14 上午10:48:14
	 */
	SaleDepartmentEntity querySaleDeptByCode(String code);

	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-21 下午4:29:25
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);

	/**
	 * 更新离线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int updateWaybillPrintTimes(String waybillNo);


	/**
	 * 
	 * 获取离线运单
	 * 
	 * @date 2012-10-16 下午5:29:27
	 * @return
	 * @see
	 */
	List<WaybillOfflineEntity> queryWaybillOffline(
			WaybillOfflineDto waybillOfflineDto);

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在OFFLINE
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see
	 */
	boolean isOfflineExsits(String mixNo);

	/**
	 * 根据运单号查询费用明细中的其它费用
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:10:38
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);

	/**
	 * 
	 * 根据部门名称查询
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-17 上午11:05:01
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByName(String name);
	
	/**
	 * 
	 * 根据部门名称和所属集中开单组查询
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-17 上午11:05:01
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(String name,String billingGroup);

	/**
	 * 根据部门名称和所属集中开单组查询
	 * @param name 要进行模糊查询的部门名称
	 * @param billingGroup 当前开组编号
	 * @param waybillNo 运单号
	 * @return 收货部门实体
	 * @author 272311
	 * @date 2015.10.13
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralizedExp(String name,String billingGroup,String waybillNo);

	/**
	 * 弃货查询接口
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-15
	 * @param condition
	 * @return List<AbandonedGoodsDto>
	 */
	List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(
			AbandonedGoodsCondition condition);

	/**
	 * 查询当前部门大区信息
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @return OrgAdministrativeInfoEntity
	 */
	OrgAdministrativeInfoEntity queryBigRegionOfCurrDept();

	/**
	 * 
	 * 查询预配航班
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午04:13:36
	 */
	List<DataDictionaryValueEntity> queryPredictFlight();

	/**
	 * 
	 * 查询合票方式
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午04:30:10
	 */
	List<DataDictionaryValueEntity> queryFreightMethod();

	/**
	 * 根据gis网点匹配条件查询网点code
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午8:35:53
	 */
	List<GisDepartmentDto> queryPickupOrgCodeByGis(GisPickupOrgDto dto);

	/**
	 * 根据组织标杆编码查询部门信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午11:11:11
	 */
	SaleDepartmentEntity querySaleDeptByUnifiedCode(String unifiedCode);

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
	PaginationDto queryFlightDtoListBySelectiveCondition(FlightEntity flight,
			int offset, int limit) throws FlightException;

	/**
	 * 
	 * 验证优惠编码
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 上午09:55:32
	 * @param couponInfo
	 * @return
	 */
	CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo);
	
	
	/**
	 * 定价优化项目
	 * 
	 * 根据优惠券编码获取区域线路限制
	 * 
	 * @author Foss-206860
	 */
	PendingSendCouponLogEntity queryLineAreaByNum(String couponNum);

	/**
	 * 
	 * 查询空运货物类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 下午04:28:46
	 * @return
	 */
	List<DataDictionaryValueEntity> queryAirGoodsType();

	/**
	 * 通过调用GIS的特殊地址查询接口获取地址类型
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:52:59
	 */
	List<String> querySpecialAddressByGis(GisPickupOrgDto dto);

	/**
	 * 
	 * 查询出发营业部的默认外场
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-6 上午10:32:59
	 * @param saleCode
	 * @param productCode
	 * @return
	 */
	OutfieldEntity queryDefaultTransCodeDept(String saleCode, String productCode);

	/**
	 * 根据客户名称精确查询全公司的客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-6 下午6:30:43
	 */
	List<CustomerContactDto> queryCustInfoByName(String custName);

	/**
	 * 根据多个手机号码查询最近三个月的历史发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisDeliveryCusByMobileList(
			List<String> mobileList);

	/**
	 * 根据多个电话号码查询最近三个月的历史发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisDeliveryCusByPhoneList(
			List<String> phoneList);

	/**
	 * 根据多个手机号码查询最近三个月的历史收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisReceiveCusByMobileList(
			List<String> mobileList);

	/**
	 * 根据多个电话号码查询最近三个月的历史收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisReceiveCusByPhoneList(
			List<String> phoneList);

	/**
	 * 
	 * 查询整车打印信息
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2013-1-6 上午10:32:59
	 * @param saleCode
	 * @param productCode
	 * @return
	 */
	WaybillDto queryWaybillByNo(String waybillNo);

	/**
	 * 
	 * 检查运单号是否存在
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2013-1-6 上午10:32:59
	 * @param saleCode
	 * @param productCode
	 * @return
	 */
	boolean isExsits(String waybillNo);

	
	/**
	 * 
	 * 检查运单号是否存在
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2013-1-6 上午10:32:59
	 * @param saleCode
	 * @param productCode
	 * @return
	 */
	boolean isExsitsByWaybillAndOrderNo(String waybillNo, String orderNo);
	
	/**
	 * 根据dto封装的查询条件查询客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-11 下午3:57:33
	 */
	List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto condition);

	/**
	 * 根据查询条件的对象集合对象客户综合信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午9:30:33
	 */
	List<CustomerQueryConditionDto> queryCustomerByConditionList(
			List<CustomerQueryConditionDto> conditions);

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
	ServiceFeeRateDto queryConfigurationParamsByOrgCode(String orgCode,String config);
    
    /**
     * 
     * 查询空运配载部门
     * @author 025000-FOSS-helong
     * @date 2013-1-21 下午07:31:00
     * @return
     */
    List<OrgAdministrativeInfoEntity> queryOrgAirDepartment();
    
    /**
	 * 根据运单号查询待处理运单基本信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午8:46:24
	 */
    WaybillPendingEntity queryPendingByNo(String waybillNo);
    
    /**
     * 
     * 查询行政区域编码
     * @author foss-sunrui
     * @date 2013-2-22 下午4:36:45
     * @param degree
     * @param name
     * @param parentDistCode
     * @return
     * @see
     */
    String queryDistrictCode(String degree,String name,String parentDistCode);
    
    /**
     * <p>根据合同编码和部门编码查询合同信息</p> 
     * @author 025000-foss-helong
     * @date 2013-2-23 上午10:35:43
     * @param bargainCode 合同编号
     * @param deptCode 部门编码
     * @return
     * @see
     */
     CusBargainEntity queryCusBargainByParams(String bargainCode,String deptCode);
     
     
     /**
      * 
      * 根据不同条件查询实体
      * @author 025000-FOSS-helong
      * @date 2013-3-7 下午05:18:36
      * @param entity
      * @return
      */
     List<GoodsTypeEntity> findGoodsTypeByCondiction();
     
     /**
      * 
      * 获取空运预计出发时间
      * @author 025000-FOSS-helong
      * @date 2013-3-8 上午11:55:47
      * @return
      */
     Date getAirLeaveTime(String orgCode , Date flightTime , String flightNumberType);
     
     /**
      * 
      * 通过运单号/订单号判断运单是否存在
      * @param mixNo
      * @return
      * @author 026113-foss-linwensheng
      * @date 2013-3-18 上午9:17:39
      */
     public boolean isPendingExsits(String mixNo);
     
 	/**
 	 * 根据工号集合查询对应的员工对象集合
 	 * @author 026123-foss-lifengteng
 	 * @date 2013-3-26 下午8:50:46
 	 */
 	List<EmployeeEntity> queryEmployeeByCodeList(List<String> codes);

     
     /**
      * 
      * 自有司机通过司机工号判断是否存在
      * @author foss-sunrui
      * @date 2013-3-28 下午4:59:49
      * @param driverCode
      * @return
      * @see
      */
     boolean isOwnDriverExists(String driverCode);

	/**
	 * 根据运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:10:38
	 */
	List<WaybillOtherChargeDto> queryWaybillOtherChargeByNo(String waybillNo);

	/**	
	 * 根据条目entryCodes批量查询条目信息,entryCode设置请参照常量类PricingConstants
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 下午5:14:07
	 */
	Map<String, String> queryPriceEntryNameByEntryCodes(List<String> entryCodes);

	/**
	 * 
	 * 应用监控数据添加
	 * @author foss-sunrui
	 * @date 2013-4-15 下午3:24:39
	 * @param waybillDto
	 * @see
	 */
	void businessMonitor(WaybillDto waybillDto);
	
	/**
	 * 
	 * 内部带货查询部门
	 * @author 025000-FOSS-helong
	 * @date 2013-4-16 下午08:41:41
	 * @param name
	 * @return
	 */
	List<SaleDepartmentEntity> querySaleDepartmentInner(String name);
	
	

	/**
	 * 
	 * @Description: 计算物流费用
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午3:05:21
	 * @param billCalculateDto
	 * @return
	 * @version V1.0
	 */
	 List<GuiResultBillCalculateDto>  queryGuiBillPrice(GuiQueryBillCalculateDto billCalculateDto);

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
	SaleDepartmentEntity querySaleDeptByCode(String code, Date billTime);

	 /**
	 * 根据客户编码查询客户合同信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-21 下午3:09:27
	 */
	CusBargainEntity queryCusBargainByCustCode(String custCode);
	
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
	List<WaybillDto> queryActualFreightAndBasicExpress(WaybillDto waybillDto);
	
	/**
	 * 获得服务器时间
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 下午3:55:19
	 */
	Date gainServerTime();
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	List<String> queryByCodeAndPickup(String code);
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	List<String> queryByCodeAndDelivery(String code);
	

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
	SaleDepartmentEntity queryPickupCentralizedDeptCode(String billingGroupOrgCode);
	
	/**
	 * 
	 * 记录打标签信息服务
	 * @author 025000-FOSS-helong
	 * @date 2013-7-1
	 */
	public void addPrintLabelInfo(GUIPrintLabelDto printLabelDto);

	String isExistDestinationAndTodoRfc(WaybillEntity waybillBean,
			List<String> serialNos);
	
	/**
	 * @功能 特殊运单流水号无法打印的情况
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-7-12 20:15:52
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */
	List<BarcodePrintLabelDto> specialSeriaNosPrintInfo(String waybillNo, List<String> serialNos);
	
	/**
	 * 
	 * 根据人民币，目标币种，开单时间查询转换后的金额
	 * @author 025000-FOSS-helong
	 * @date 2013-7-15
	 */
	public BigDecimal getExchangedFee(BigDecimal rmbFee, String currencyCode, Date billTime);
	
	/**
	 * 
	 * 查询目标币种汇率
	 * @author 025000-FOSS-helong
	 * @date 2013-7-15
	 */
	public BigDecimal getExchangedFeeRate(String currencyCode, Date billTime);
	
	/**
	 * 查询自有网点
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	List<BranchQueryVo> queryListByDepartment(SaleDepartmentEntity entity);
	
	/**
	 * 查询汽运偏线、空运网点
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity);

	/**
	 * 通过发货客户编码查找发货客户是否能开发月结
	 * @author panguoyang
	 * @date 2013-7-24 下午9:10:10
	 */
	boolean isCanPaidMethod(CusBargainVo vo);

	/**
	 * 通过发货客户编码查找发货客户是否能开发月结,配合客户结算中心需求
	 * @author DP-FOSS zhaoyiqing 343617
	 * @date 2016-10-14
	 */
	boolean isCanPaidMethodForUCBC(CusBargainVo vo);

	
	/**
	 * 
	 * 根据部门Code和所属集中开单组查询
	 * 
	 * @author WangQianJin
	 * @date 2013-08-02
	 */
	List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(String code,String billingGroup);
	
	/**
	 * 根据开单时间获取自提件信息
	 * @param billDate
	 * @return
	 */
	List<MinFeePlanEntity> getMinFeePlanEntityByDate(Date billDate);
	
	/**
	 * 查询指定渠道编码和日期获取产品信息
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
	GuiResultBillCalculateDto getProductPriceDtoOfWVHAndBF(
			GuiQueryBillCalculateDto billCalculateDto);
	/**
	 * 
	 * @Description: 计算物流费用
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午3:05:21
	 * @param billCalculateDto
	 * @return
	 * @version V1.0
	 */
	 List<GuiResultBillCalculateDto>  queryGuiExpressBillPrice(GuiQueryBillCalculateDto billCalculateDto);
	 
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
	 * 查询走货路径
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-29 下午6:15:31
	 */
	FreightRouteDto queryFreightRouteBySourceTarget(String sourceCode, String targetCode, String productCode, Date time);

	List<SaleDepartmentEntity> querySaleDeptsByBillingGroupCode(
			String billGroupCode);

	/**
	 * 判断是否为快递单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-10-1 20:42:24	 * 
	 * @param waybillNo
	 * @return
	 */
	boolean queryIsExpressBill(String waybillNo);

	boolean isCanPaidMethodExp(CusBargainVo vo);

	WaybillExpressEntity getWaybillExpressByWaybillNo(String waybillNo);
	
	/**
	 * 查询CRM快递订单
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-9 下午2:39:27
	 */
	CrmOrderInfoDto queryCrmExpressOrder(CrmOrderQueryDto queryVo);
	
	/**
	 * 内存溢出，性能优化
	 * @return
	 * add WangQianJin by 2010-10-17
	 */
	List<EmployeeEntity> queryEmployeeNameAndCodeByCodeList(List<String> codes);

	OuterBranchExpressEntity queryLdpAgencyDeptByCode(
			String customerPickupOrgCode, String yes);

	/**
	 * 根据编码查询员工信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-1 下午8:53:17
	 */
	EmployeeEntity queryEmployeeByEmpCode(String code);
	

	/**
	 * 根据部门编码查询城市编码
	 * @param orgCode
	 * @return
	 */
	String queryCityIdByOrgCode(String orgCode);
	
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
	CustomerDto queryCustInfoByCodeNew(String custCode);
	
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
	  * 是否启用CRM推广活动
	  * @创建时间 2014-5-15 上午11:23:54   
	  * @创建人： WangQianJin
	  */
	boolean isStartCrmActive();
	
	/**
	  * 获取配置参数是否启用
	  * @创建时间 2014-06-19  
	  * @创建人： WangQianJin
	  */
	boolean isStartConfig(String module,String key,String code);
	
	/*
	 * 获取配置参数（读取数据库）
	 */
	public ConfigurationParamsEntity queryConfigurationParamsByEntity
		(String model,String config,String orgCode);
	
	/**
	 * 查询是否存在活动
	 * @param deptCode 收货部门
	 * @param billDate 开单时间
	 * @return
	 */
	public boolean isExistSpecialOffer(String deptCode,Date billDate);
	/**
	 * 通过部门编码\业务时间,查询大礼包名称(List<entity>) 
	 * @return
	 */
	public List<CityMarketPlanEntity> searchCityMarketPlanEntityList(
			String orgCode, Date billDate);
	boolean identityOuterEffectivePlan(String departDeptCode, String code,
			String code2, Date date);
	
	/**
	 * 根据活动编码获取活动信息
	 * @创建时间 2014-6-12 上午10:09:51   
	 * @创建人： WangQianJin
	 */
	MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode);

	/**
	 * 获取收货城市优惠活动信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-2 18:52:57
	 */
	String getWaybillDocAd(String receiverOgCode, String cityPattern);
//获取待补录的图片运单
	WaybillPictureEntity getPictureWaybill(List<String> newWaybills);
//获取待补录的图片运单的总数
	int getPictureWaybillCount();
//退回运单更具运单id
	int backPictureWaybill(WaybillPictureEntity pictureWaybill);
//退回运单更具运单号
	int updatePictureWaybillByWaybillno(
			WaybillPictureEntity waybillPictureEntity);

	int updatePictureWaybillById(WaybillPictureEntity waybillPictureEntity);

	void pushMessage(int pushType,int messageType,int deviceType,String deviceId,String title,String description ,String value);

	List<EWaybillPrintDto> printEWaybillInfos(String waybillNo, List<String> serialNoList);


	boolean isEWaybillInfoByWaybillNo(String waybillNo);
	
	/**
	 * 根据订单号查询订单表对应订单的类型
	 * @param orderNo
	 * @return
	 */
	String queryWaybillTypeByOrderNo(String orderNo);
	
	/**
	 * 获取系统配置参数
	 * @param model
	 * @param config
	 * @param orgCode
	 * @return
	 */
	ConfigurationParamsEntity getConfig(String model, String config,
			String orgCode);
	WaybillPictureEntity queryWaybillPictureByEntity(WaybillPictureEntity entity);
	List<WaybillPictureEntity> queryWaybillPicturesByEntity(
			WaybillPictureEntity entity);

	/**
	 * 根据id查询纸纤包装增值服务单价
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-26下午16:12
	 */
	PriceFibelPaperPackingEntity queryPriceFibelPaperPackingEntity(String id);
	
	/**
	 * gcl 14.12.04
	 * 已开单成功, 保存开单要发送给手机的信息到数据库,
	 */
	String saveWayBillPushMessage(WaybillPushMessageEntity e);
	String saveWaybillpushmessage(String waybillcode,String pushmessage,String createusercode,String createdeptcode,String driverphone);	
	String saveWaybillpushmessageSendSms(String waybillcode,String pushmessage,String createusercode,String createdeptcode,String driverphone);

	
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
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	List<ExpBatchChangeWeightDto> queryExpBatchChangeWeightResult(
			ExpBatchChangeWeightQueryDto dto);
	
	/**
	 * 
	 * 批量执行更改重量（快递）
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
	 * 
	 */
	void commitBatchChangeWeight(
			List<ExpBatchChangeWeightDto> expBatchChangeWeightDto);


	/**
	 * 查询原单号信息
	 * @param originalWaybillNo
	 * @param returnType 
	 * @return 
	 */
	WaybillExpressEntity queryWaybillByOriginalWaybillNo(String originalWaybillNo, String returnType);
	
	/**
	 * 通过运单号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo,
			String returnType);

	/** 
	 * 验证运单签收变更、反签收申请情况
	 */
	void checkWayBillRfcStatus(String originalWaybillNo);
   /**
    * 查询运单出发更改状态
    * @param id
    */
	void checkWayBillChange(String id);
    /**
     * 查询运单库存状态
     * @param originalWaybillNo
     * @param entity
     * @return 
     */
	List<StockEntity> queryStockByWaybillNo(String originalWaybillNo);
    
	/**
	 * 通过返货实体查询返货数据
	 * 
	 */
	List<CrmReturnedGoodsDto> queryReturnGoodsWorkOrder(CrmReturnedGoodsDto vo);
	/**
	 * 通过返货实体查询返货数据结果返回
	 * 
	 */
	List<CrmReturnedGoodsDtoResult> queryReturnGoodsWorkOrderResult(CrmReturnedGoodsDtoResult vo);
	
	/**
	 *根据运单号查询工单申请数据
	 *@param waybillNo
	 *@author foss-206860 
	 */
	List<ReturnGoodsRequestEntity> queryReturnGoodsWorkOrder(String waybillNo);
	/**
	 * 
	 * 查询此运单是否签收.<br/>   
	 * @author 146831  
	 * @param entity
	 * @return  
	 * @since JDK 1.6
	 */
	boolean queryWaybillSignResultByWaybillNo(String waybillNo);
	/**
	 * 
	 * submitReturnGoodsApplyToCRM:(这里用一句话描述这个方法的作用). <br/>  
	 * 使用httppost客户端调用rest方法来创建返单的信息.<br/>   
	 * 调用成功就是true 调用失败就是false
	 * @author 146831  
	 * @param crmReturnedGoodsDto
	 * @return  
	 * @since JDK 1.6
	 */
	public abstract boolean submitReturnGoodsApplyToCRM(String wayBillNO,String content,WaybillEntity entity);
    /**
     * 是否存在付款信息.
     * @param originalWaybillNo
     * @return
     */
	boolean isExistRepayment(String originalWaybillNo);
	/**
	 * 网上支付是否支付
	 * @param waybillNos
	 * @return
	 */
	List<String> unpaidOnlinePay(List<String> waybillNos);
	/**
	 * 应收到付
	 * @param originalWaybillNo
	 * @return
	 */
	BigDecimal queryFinanceSign(String originalWaybillNo);
    /**
     * 异常或不允许返货开单
     * @param originalWaybillNo
     * @return
     */
	boolean queryExcepSignResultByWaybillNo(String originalWaybillNo);
	/**
	 * 
	 * @param originalWaybillNo
	 * @return
	 */
	boolean queryBeLdpHandOveredByWaybillNo(String originalWaybillNo);
	/**
	 * 查询营业部的部门信息
	 * @param destOrgCode
	 * @return
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String destOrgCode);
		
    /**
     * 返货开单页面，根据原单号在暂存表查询运单号
     * @param originalWaybillNo
     * @return 
     */
	String getWaybillNo(String originalWaybillNo);
	boolean checkWaybillAndPendingOrderNo(String orderNo);
	
	/**
	 * 
	 * @param customerQueryConditionDto
	 * @return
	 */
	boolean cheCustomerPhoneExistCustomerMobilePhone(CustomerQueryConditionDto customerQueryConditionDto);
	/**
	 * 根据当前时间查询当前月份所有内部员工发货的优惠金额
	 * 根据当前时间查询该员工该月额度
	 * dp-foss-dongjialing
	 * 225131
	 */
	public BigDecimal queryDiscountFeeByEmployeeNo(String employeeNo,Date recevieDate);
	
	public List<InempDiscountPlanEntity> queryInempDiscountPlanEntity(Date recevieDate);

	ActualFreightEntity queryAcfByWaybillNo(String waybillNo);
	
	/**
	 * 根据员工信息查询相关员工
	 * @param employeeEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<EmployeeEntity> queryEmployeeExactByEntity4Selector(EmployeeEntity employeeEntity, int start, int limit);
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String orgCode);
	void updateWaybill(WaybillEntity waybill);

	void updatePengdingWaybill(WaybillPendingEntity entity);
	/**
	 * 保存批量开单数据到暂存表penging中
	 * @param pendingList
	 */
	void savePengdingWaybill(List<WaybillPendingEntity> pendingList);
	String selectWaybillNo(String waybillNo);
    /**
     * 批量保存打木架信息
     * dp-foss-dongjialing
     * 225131
     */
	void saveWoodenRequirePending(List<WoodenRequirePendingEntity> woodenRequireList);

	void saveLabeledGood(List<LabeledGoodEntity> labelGoodList);

	WoodenRequirePendingEntity queryWoodenRequireByNo(String waybillNo);

	ProductEntity findProductByName(String temp);
	
	List<DataDictionaryValueEntity> selectProductCodeDataDictValue(String termsCode);

	String queryBillingByStore(String code);
	BigDecimal queryTotalFeeByDelevyCode(String code, Date recevieDate);

	/**
	 * 根据客户编码查询客户信息
	 */
	CustomerEntity queryCustInfoByCustomerEntity(CustomerEntity customerEntity);
	
	/**
	 * <th>
	 * 根据部门编码,一级产品查询所有对应的三级产品
	 * </th>
	 * @author Foss-105888-Zhangxingwang
	 * @param date 
	 * @date 2015-3-31 20:22:18
	 * @param maps
	 * @return
	 */
	List<ProductEntity> getAllProductEntityByDeptCodeForCargoAndExpress(String deptCode, String typeCode, Date date);
	/**
	 * 根据产品编码和时间判定是否快递
	 * @param productCode
	 * @param billTime
	 */
	boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);
	
	/**
	 * 根据Code查找数据字典
	 * @param custBlacklistCategory
	 * @param blackListCategory
	 * @return
	 */
	DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode, String valueCode); 

	
	

	/**
	 * 根据特殊增值服务查询提货方式
	 * @author LiZhongLin
	 * @param condition
	 * @return 
	 */


	List<DataDictionaryValueEntity> querySpecialPickUp();
    /**

	/**
	 * 根据发货客户编码查询客户信息
	 * @author LiZhongLin
	 * @param condition
	 * @return 
	 */
	CustomerEntity queryCustomerInfoByCusCode(String code);
    
	/**
	 * 根据运单号查询最新的未受理返货工单
	 * @author LiZhongLin
	 * @param condition
	 * @return 
	 */
	boolean queryReturnGoodsRequestEntityByCondition(String waybillNo);
	/**
	* @Description: :检查预配线路是否预警线路
	* @author hbhk 
	* @date 2015-8-20 上午8:10:59 
	  @param receiveOrgCode
	  @param targetOrgCode
	  @return
	 */
	String checkWarningLine(String receiveOrgCode,String targetOrgCode);

	
	/**
	 * dp-foss-dongjialing
	 * 225131
	 */
	 List<CusLtDiscountItemDto> iSLtDiscountBackInfo(String customerCode, Date date);

	
	/**
	 * 获取快递标签打印优化信息远程接口
	 * @param waybillNo
	 * @author foss-218438
	 * @return
	 */
	BarcodePrintLabelDto getCommonLabelPrintInfoExpress(String waybillNo,String waybillstatus);
	
	/**
	 * 通过调用GIS的详细地址匹配接口查询是否展会货
	 * @author foss-218438
	 */
	String isExhibitCargo(ExhibitionKeywordEntity exhibitionKeyword);	/**
	 * 根据参数查询是否是子母件以及其子母件信息
	 * 
	 * @author foss-206860
	 * */
	TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(String waybillNo) ;
	/**
	 * 根据原单号查询单号信息
	 * @param originalWaybillNo
	 * @author foss-206860
	 * */
	List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(String originalWaybillNo);
	
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
	

	/**
	 * 根据运单号、流水号、部门编码、以及货区编码查询该单号是否在提供的部门库存内
	 * @author 218459-foss-dongsiwei
	 */
	StockEntity queryStockEntityByNos(String orgCode, String waybillNo,
			String serialNO, String goodsAreaCode);
	
	/**
	 * 根据运单号查询同一字母件下的所有运单信息
	 * @author 218459-foss-dongsiwei
	 */
	List<WaybillRelateDetailEntity> queryWaybillRelateByWaybillNo(String waybillNo);
	
	

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
	 * 通过原单号查询上报工单信息
	 * 2015-11-09 huangwei
	 */
	ReturnGoodsRequestEntity queryReturnGoodsRequestEntityByWayBillNo(String originalWaybillNo);
	
	/**
	 * 查询代收上限
	 * @param code
	 * @return
	 * @author:280747-foss-zhuxue
	 * @date:2015/10/29
	 */
	SaleDepartmentEntity querySaleDepartmentByCodeNoCache(String code);

	
	/**
	 * 查询单号是否在派送中
	 * @param entity
	 */
	Boolean queryArriveSheetByWaybillNo(ArriveSheetEntity entity);
	/**
	 * 是否一票多件
	 * */
	int  queryOneticketornotBycode(String code);
	/**
	 * 根据编码查询配置参数
	 * */
	String  queryConfValueByCode(String code);
	/**
	 * 根据订单号查询订单信息
	 * */
	String  queryOrderByOrderNo(String orderno);
	
	/**
	 * 根据 编码 查询 配置信息
	 */
	ConfigurationParamsEntity queryConfigurationParamsOneByCode(String powerCode) ;
	
	/**
	 * 
	 * 查询运单
	 * 
	 * @date 2012-11-26 下午08:37:46
	 */
	List<WaybillEntity> queryWaybillForPrint(WaybillDto waybillDto);

/**
 * 查询最新的更改单信息
 */
	List<WaybillRfcEntity> queryRecentRfc(String waybillNo);


	/**
	 * 根据返单号查询运单
	 * @param returnWaybillNo
	 * @return
	 */
	public List<WaybillExpressEntity> queryReturnWaybillNoInfo(String returnWaybillNo);




	List<SortingScanEntity> queryEwayBillRecords(SortingScanDto sortingScanDto);
	/*void updateOrderRefuseState(String originalWaybillNo, String orderNo,
			String deliveryCustomerCode);*/
	
	/**
	 * 根据订单号查询serviceType
	 * @param orderNo
	 * @return
	 */
	String queryWaybillServiceTypeByOrderNo(String orderNo);

	/**
	 * 东方购物
	 * @param originalWaybillNo
	 * @param orderNo
	 * @param deliveryCustomerCode
	 */
	void updateOrderRefuseState(String originalWaybillNo, String orderNo,
			String deliveryCustomerCode);
	
	/**
	 * 
	 * 合伙人月结校验预存款资金池的资金额度
	 * 
	 * @date 2016年1月23日 09:20:36 葛亮亮
	 */
	SynPartenerMonthlyLineDeductResponse partenerMonthlyLineDeduct(PartenerDeductDto deductDto);
	
	/**
	 * 
	 * 合伙人预存款金池的资金额度
	 * 
	 * @date 2016年1月25日 14:18:36 葛亮亮
	 */
	SynPartenerPrestoreDeductResponse partenerPrestoreDeduct(PartenerPrestoreDeductDto prestoreDeductDto);
	
	/**
	 * 
	 * 合伙人代收款上限
	 * 
	 * @date 2016年1月28日 14:09:37 葛亮亮
	 */
	SynPartenerCodAmountUpperLimitResponse partenerCodAmountUpperLimitResponse(PartenerCodAmountUpperLimitDto partenerCodAmountUpperLimit);
	
	/**
	 * 零担电子运单批量导入重量体积
	 * @author 305082
	 */
	void commitLTLBatchChangeWeight(List<LTLEWaybillChangeWeightDto> ltlEWaybillChangeWeightDto);
	/**
	 * 查询更改重量体积的结果信息
	 */
	public List<LTLEWaybillChangeWeightDto> queryLTLEWaybillChangeWeightResult(LTLEWaybillChangeWeightQueryDto dto);
	/**
	 * 查询运单信息
	 * @param ltleWaybillQueryResultDto
	 * @return
	 */
	public List<LTLEWaybillQueryResultDto> queryOmsOrderOrLabelStatusByWaybillNo(LTLEWaybillQueryResultDto ltleWaybillQueryResultDto);
	/**
	 * 根据订单号查询订单所有信息
	 * @param <DispatchOrderEntity>
	 * @param orderNo
	 * @return
	 */
	public DispatchOrderChannelNumberEntity queryWaybillTypeEntityByOrderNo(String orderNo);
		/**
	 * 推送标签状态
	 */
	public void pushLabelStatus(List<String> waybillNoList);
	/**
	 * 根据单号查询打木信息
	 *@date zhuxue
	 */
	WoodenRequirementsEntity queryWoodenRequirement(String waybillNo);
	/**
	 * 根据code查询运输性质信息
	 * zhuxe
	 */
	String queryWoodenProductByCache(String code);


	
	boolean greenHandWrapWriteoffService(String waybillNo);
	
	/**
	 * <p>
	 * (根据运单号查询WaybillEntity)
	 * </p>
     * <p>根据订单号查询订单</p> 
     * @author foss-sangwenhao
     * @param orderNo 订单号
     */
	OmsOrderEntity queryOmsOrderByOrderNo(String orderNo) ;
	
	/**
     * <p>根据运单号查询订单</p> 
     * @author foss-sangwenhao
     * @param waybillNo 运单号
     */
	OmsOrderEntity queryOmsOrderByWaybillNo(String waybillNo) ;
	
	
	/**
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午2:07:35
	 * @param number
	 * @return
	 * @see
	 */
	WaybillEntity queryNewWaybillByNumber(String number);

	
	/**
	 * 
	 * <p>校验运单号是否存在</p> 
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-5 上午8:28:24
	 * @param waybillNo
	 * @param operator 操作人
	 * @return
	 * @see
	 */
	public boolean valuateFoss2EcsWaybillNo(String waybillNo,String operator);
	
	/**
	 * 
	 * <p>查询传递ECS开关</p> 
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-5 上午8:28:33
	 * @return
	 * @see
	 */
	public boolean queryPkpSwitch4Ecs();
	
	/**
	 * <p>FOSS校验悟空快递单号格式是否正确</p> 
	 * @author 272311-sangwenhao
	 * @date 2016-8-5 上午9:28:33
	 * @return
	 */
	public boolean validateWaybillNoIsCorrect(Map<String, String> map) ;
	
	/**
	 * <p>根据营业部网点code查询进仓地址信息</p>
	 * @author 354805-taodongguo
	 * @date 2016-9-12 17:24:02
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
	List<SwiftNumberInfoDto> queryUnpackDetailss(String waybillNo);
	/**
	 * 依据订单号查询订单类型
	 * @param orderNO
	 * @return
	 */
	public String queryServiceType(String orderNO);
	/**
	 * 查询在线运单打印次数
	 * 
	 * @author foss-zhuxue
	 * @date 2016-10-13 
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int queryPrintTimesByWaybill(String waybillNo);
	/**
	 * 更新离线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int updateWaybillPrimaryPrintTimes(PrintInfoEntity record);
	
	/**
	 * 根据单号查询是否是合伙人开单
	 * @param waybillNo
	 * @return
	 */
	String selectqueryWaybillForPrint(String waybillNo);
	
	/**
	 * 根据到达部门编码查询对接外场
	 */
	String selectqueryDeptDeptTransferForNameByCode(String code);
	
	/**
	 * 根据省份code查询国家信息
	 * @author 354805-taodongguo
	 * @date 2016-10-19 09:56:58
	 * @param provinceCode
	 * @return
	 */
	public AddressFieldDto queryNationByProvinceCode(String provinceCode);
	
	
	
	/**
	 * 根据条件查询推送CUBC接口日志
	 * @param queryDto
	 * @return
	 */
	List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto);
	
	/**
	 * 重新推送运单信息至CUBC
	 * @param cubcLogEntitys
	 */
	void pushWaybillToCUBC(List<WaybillLogEntity> cubcLogEntitys);
	/**
	 * 图片开单查询本地待补录运单数 
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:39:54
	 * @return 
	 */
	int getPictureWaybillLocalCount();
	
	/**
	 * 图片开单查询全国待补录运单数 
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:39:54
	 * @return 
	 */
	int getPictureWaybillAllCount();
	/**
	 * 根据gis网点匹配条件查询目的网点信息
	 * 
	 * @author 321993 zhangdinahao
	 * @date 2017-03-16 下午 21:35:53
	 */
	HisSegMatchResponse queryStationInfo(HisSegMatchRequest request);
	 /**
		 *远程调用WK接口
		 * 根据手机号码查询收货人联系方式和地址信息
		 * @param mobilePhone
		 * @date 2017-03-17 下午 14:45
		 * @return
		 */
	List<CustomerAddressDto> queryConsignee(String mobilePhone);

		/**
	 * 根据发货客户编码和开单时间查询是否可以开异地调货信息
	 * @param provinceCode
	 * @return
	 */
	public CustomerCircleNewDto queryCustomerByCusCode(String provinceCode,Date date, String active);

	/**
	 * 根据客户编码查询主客户编码
	 * @param code
	 * @return
	 */
	public IsCustomerCircleDto isQueryCustomerCircle(String code,Date date);
	/**
	 * 查询是否有可补录的图片订单  by 352676
	 * @param newWaybills
	 * @return
	 */
	WaybillPictureEntity getPictureWaybillIsExit(List<String> newWaybills);
}










