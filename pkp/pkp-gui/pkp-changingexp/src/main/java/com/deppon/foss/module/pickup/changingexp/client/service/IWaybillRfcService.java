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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/service/IWaybillRfcService.java
 * 
 * FILE NAME        	: IWaybillRfcService.java
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
package com.deppon.foss.module.pickup.changingexp.client.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerMonthlyLineDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerPrestoreDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerPrestoreDeductDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;

/**
 * 
 * 更改单所有的业务操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-16 下午04:13:46,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-16 下午04:13:46
 * @since
 * @version
 */
public interface IWaybillRfcService {

    /**
     * 
     * <p>
     * 查询运输性质
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
     * 查询到达部门拥有的产品属性
     * @author 025000-FOSS-helong
     * @date 2013-1-30 上午11:20:01
     * @param arriveDept
     * @param productLevel
     * @return
     */
    List<ProductEntity> queryByArriveDeptProduct(String arriveDept);

    /**
     * 
     * <p>
     * 查询运输性质
     * </p>
     * 
     * @author 025000-FOSS-helong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
    ProductEntity queryTransTypeByCode(String code, Date billDate);

    /**
     * 
     * <p>
     * 查询运单
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-24 上午9:08:33
     * @param number
     * @return
     * @see
     */
    WaybillEntity queryWaybillByNumber(String number);

    /**
     * 
     * 查询提货方式-空运
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-22 下午04:09:11
     */
    List<DataDictionaryValueEntity> queryPickUpGoodsAir();

    /**
     * 
     * 查询提货方式-汽运
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-22 下午04:09:11
     */
    List<DataDictionaryValueEntity> queryPickUpGoodsHighWays();

    /**
     * 
     * <p>
     * 查询对外备注
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-16 下午07:05:16
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryOuterRemark();

    /**
     * 
     * <p>
     * 查询退款类型
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-16 下午07:10:03
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryRefundType();

    /**
     * 
     * <p>
     * 查询返单类型
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-16 下午07:10:06
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryReturnBillType();

    /**
     * 
     * <p>
     * 查询付款方式
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-16 下午07:24:23
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryPaymentMode();

    /**
     * 
     * <p>
     * 查询预配航班
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-16 下午07:24:23
     * @return
     * @see
     */
    List<DataDictionaryValueEntity> queryPredictFlight();

    /**
     * 
     * <p>
     * 是否限保物品
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-17 下午03:42:15
     * @param goodsName
     * @return
     * @see
     */
    LimitedWarrantyItemsEntity isInsurGoods(String goodsName);

    /**
     * 
     * <p>
     * 是否禁运物品
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-17 下午03:49:31
     * @param goodsName
     * @return
     * @see
     */
    boolean isProhibitGoods(String goodsName);
    
    /**
     * 
     * 查询所有违禁品
     * @author foss-sunrui
     * @date 2013-3-13 上午10:42:17
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
     * 检查单号连续性
     * </p>
     * 
     * @author 102246-foss-shaohongliang
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
     * 是否贵重物品
     * </p>
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-19 下午03:21:00
     * @param goodsName
     * @return
     * @see
     */
    boolean isValuablesGoods(String goodsName);


    /**
     * 
     * 导入运单
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-29 下午1:42:28
     */
    WaybillRfcImportDto importWaybillByNumber(String waybillNo);
    
    /**
    * 
    * 导入运单
    * 
    * @author 265475-foss-邹胜利
    * @date 2016-1-18 下午1:42:28
    */
   WaybillRfcImportDto importWaybillBy(String waybillNo);


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
     * 根据ID查询数据字典
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-6 上午11:11:56
     */
    DataDictionaryValueEntity queryDataDictoryValueEntity(String termsCode,
	    String valueCode);

    /**
     * 
     * 查询限保物品限保金额
     * 
     * @author 025000-FOSS-helong
     * @date 2012-10-24 上午08:58:14
     */
    String queryLimitAmount(String goodsName);

    /**
     * 
     * 查询客户变更类型
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-8 下午5:34:27
     */
    List<DataDictionaryValueEntity> queryInsideChangeType();

    /**
     * 
     * 查询客户变更类型
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-8 下午5:34:27
     */
    List<DataDictionaryValueEntity> queryCustomerChangeType();

    /**
     * 
     * 查询交接明细
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-9 下午4:08:20
     */
    List<HandOverAndUnloadDto> queryHandoverDetail(String waybillNo);

    /**
     * 更新在线更改单打印次数 +1
     * 
     * @author foss-jiangfei
     * @date 2012-11-15 下午11:48:25
     * @param waybillNo
     * @return
     * @see
     */
    int updateWaybillRfcPrintTimes(String waybillNo);

    /**
     * 根据更改单ID 查询更改单实体
     * 
     * @author foss-jiangfei
     * @date 2012-11-18 下午2:49:01
     * @param id
     * @return
     * @see
     */
    WaybillRfcEntity queryRfcEntityByWaybillId(String id);

    /**
     * 
     * 提交更改单
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-19 下午2:08:24
     */
    WaybillRfcEntity commitWaybillRfc(WaybillRfcSubmitDto submitDto);

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
     * 查询货物状态
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-23 上午9:56:00
     */
    List<DataDictionaryValueEntity> queryGoodsStatus();

    /**
     * 
     * 查询差错处理结果
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-11-27 上午10:00:13
     */
    QueryVirtualResultDto queryErrorHandlingResult(String handingID);

    /**
     * 
     * 判断货物的重量体积比值是否在区间内
     * 
     * @author 102246-foss-shaohongliang
     * @date 2012-12-1 上午9:44:11
     */
    boolean isWeightByVolume(String goodsWeight, String goodsVolume);

    /**
     * 
     * 判断是否需要大车直送
     * 
     * @author 025000-FOSS-helong
     * @date 2012-10-24 下午05:02:51
     */
    boolean isVehicleDirect(String weight, String volume);

    /**
     * 
     * 根据件数、体积、保价判断是否贵重物品
     * 
     * @author 025000-FOSS-helong
     * @date 2012-10-25 上午10:39:52
     */
    boolean isValueGoods(String goodsName,int goodsNum, String goodsVolume, String goodsValue);

    /**
     * 
     * 根据编码查询省份名称
     * @author 102246-foss-shaohongliang
     * @date 2012-12-18 下午8:35:45
     */
    String queryProvinceByCode(String provCode);

    /**
     * 
     * 根据编码查询城市名称
     * @author 102246-foss-shaohongliang
     * @date 2012-12-18 下午8:35:45
     */
    String queryCityByCode(String cityCode);

    /**
     * 
     * 根据编码查询区域名称
     * @author 102246-foss-shaohongliang
     * @date 2012-12-18 下午8:35:45
     */
    String queryCountyByCode(String distCode);

    /**
     * 
     * 根据编码查询部门名称
     * @author 102246-foss-shaohongliang
     * @date 2012-12-18 下午8:35:45
     */
    String queryDeptNameByCode(String code);

    /**
     * 
	 * 根据部门编码查询部门信息
     * @author 102246-foss-shaohongliang
     * @param string 
     * @date 2012-12-18 下午8:33:57
     */
    SaleDepartmentEntity querySaleDepartmentByCode(String customerPickupOrgCode);
    
    /**
     * 
	 * 根据部门编码查询部门信息
     * @author 102246-foss-shaohongliang
     * @param string 
     * @date 2012-12-18 下午8:33:57
     */
    SaleDepartmentEntity queryExsitSaleDepartmentByCode(String customerPickupOrgCode);

    /**
     * 
     * 查询配载部门和最终配载部门信息以及走货线路
     * 
     * @author 025000-FOSS-helong
     * @date 2012-11-22 下午03:38:10
     */
    OrgInfoDto queryLodeDepartmentInfo(boolean pickupCentralized, String orginalOrganizationCode,
	    String destinationOrganizationCode, String productCode);

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
     * 插入一条更改单打印记录
     * 
     * @author foss-jiangfei
     * @date 2012-12-5 下午7:48:25
     * @param waybillNo
     * @return
     * @see
     */
    int insertSelective(PrintInfoEntity record);

    /**
     * 在线查询更改单打印次数
     * 
     * @author foss-jiangfei
     * @date 2012-12-5 下午7:48:25
     * @param waybillNo
     * @return
     * @see
     */
    int queryPrintTimesByWaybillId(String waybillId,String waybillNo) ;

    /**
     * 
     * 获取打木架体积计算系统参数
     * @author 102246-foss-shaohongliang
     * @date 2012-12-18 下午7:45:15
     */
    String queryGoodsPackingVolume();

    /**
     * 
     * 根据客户编码查询客户银行信息
     * @author 025000-FOSS-helong
     * @date 2012-12-6 下午04:43:42
     */
    List<CusAccountEntity> queryBankAccountByCode(String customerCode);

    /**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息
     * @author 102246-foss-shaohongliang
     * @date 2012-12-19 下午9:00:12
     */
    OuterBranchEntity queryAgencyBranchCompanyInfo(
			String customerPickupOrgCode);
    
    /**
     * 根据dto封装的查询条件查询客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-24 下午7:13:55
     */
	List<CustomerContactDto> queryCustomerInfo(CustomerQueryConditionDto dto);


    /**
     * 根据手机或电话号码查询历史收货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-22 下午7:04:40
     */
    List<CustomerContactDto> queryHisReceiveCusByPhone(CustomerQueryConditionDto conditionDto);

    /**
     * 根据手机或电话号码查询历史发货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-22 下午7:05:31
     */
    List<CustomerContactDto> queryHisDeliveryCusByPhone(CustomerQueryConditionDto conditionDto);

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
	List<ProductPriceDto> queryProductPriceList(
			QueryBillCacilateDto queryDto);

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
     * 根据客户编码查询客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-26 上午10:45:29
     */
    CustomerDto queryCustInfoByCode(String custCode);

    /**
     * 
     * 查询空运货物类型
     * @author 025000-FOSS-helong
     * @date 2013-1-4 下午04:28:46
     * @return
     */
     List<DataDictionaryValueEntity> queryAirGoodsType();
     
 	/**
 	 * 
 	 * 查询合票方式
 	 * 
 	 * @author 025000-FOSS-helong
 	 * @date 2012-12-19 下午04:30:10
 	 */
 	List<DataDictionaryValueEntity> queryFreightMethod();

     /**
      * 根据客户名称精确查询全公司的客户信息
      * @author 026123-foss-lifengteng
      * @date 2013-1-6 下午6:30:43
      */
 	List<CustomerContactDto> queryCustInfoByName(String custName);
     
	   
	/**
	 * 根据多个手机号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
    List<CustomerQueryConditionDto> queryHisDeliveryCusByMobileList(List<String> mobileList);
    
    /**
	 * 根据多个电话号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisDeliveryCusByPhoneList(List<String> phoneList);
	
	/**
	 * 根据多个手机号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisReceiveCusByMobileList(List<String> mobileList);

	/**
	 * 根据多个电话号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisReceiveCusByPhoneList(List<String> phoneList);
	

	  /**
	 * <p>功能：按code查询组织部门实体</p> 
	 * @author foss-jiangfei
	 * @date 2012-11-7 下午7:24:32
	 * @param id
	 * @return
	 * @see
	 */
   
	OrgAdministrativeInfoEntity queryByCode(String code);

    /**
     * 精确查询 
     * 通过 CODE 查询
     * 
     * @author foss-jiangfei
     * @date 2012-10-31 下午4:9:39
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
     */
  
  AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code) ;
  

	
  /**
   * <p>根据条件有选择的检索出符合条件的“航班信息和关联信息”的封装DTO实体列表（条件做自动判断，只选择实体中非空值）</p> 
   * @author 025000-foss-helong
   * @date 2012-12-28 下午1:52:18
   * @param flight 以“航班信息”实体承载的条件参数实体
   * @param offset 开始记录数
   * @param limit 限制记录数
   * @return 分页的Action和Service通讯封装对象 
   * @throws FlightException
   * @see
   */
  PaginationDto queryFlightDtoListBySelectiveCondition(FlightEntity flight, int offset, int limit)throws FlightException;
  

	/**
	 * 通过调用GIS的特殊地址查询接口获取地址类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:52:59
	 */
	List<String> querySpecialAddressByGis(GisPickupOrgDto dto);
	

	/**
	 * 根据gis网点匹配条件查询网点code
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午8:35:53
	 */
	List<GisDepartmentDto> queryPickupOrgCodeByGis(GisPickupOrgDto dto);
	

	/**
	 * 根据组织标杆编码查询部门信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午11:11:11
	 */
	SaleDepartmentEntity querySaleDeptByUnifiedCode(String unifiedCode);
	

    /**
     * 
     * 根据部门名称查询
     * @author 025000-FOSS-helong
     * @date 2012-12-17 上午11:05:01
     */
    List<SaleDepartmentEntity> querySaleDepartmentByName(String name);

	/**
	 * 根据出发部门、到达部门、产品类型、开单时间（数据生成时间）获得预计出发时间
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-19 下午3:40:43
	 */
	Date searchPreLeaveTime(String departDeptCode, String specialLine, String productCode, Date createTime);
	
	/**
	 * 根据预计出发日期、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺自提时间
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:06
	 */
	EffectiveDto searchPreSelfPickupTime(String departDeptCode, String arriveDetpCode, String productCode, Date preLeaveTime, Date createTime);

	/**
	 * 根据承诺自提时间、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺派送时间
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:49
	 */
	EffectiveDto searchPreDeliveryTime(String departDeptCode, String arriveDetpCode, String productCode, Date preLeaveTime, Date createTime);
	
	/**
	 * 根据dto封装的查询条件查询客户信息 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-11 下午3:57:33
	 */
	 List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition);

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
     * 根据Code查询价格DTO
     * @author 102246-foss-shaohongliang
     * @date 2013-1-22 下午2:25:02
     */
    PriceEntity queryValueAddPriceByCode(String pricingCodeZz);
    
	/**
	 * 根据部门编码查询部门信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-14 上午10:48:14
	 */
	SaleDepartmentEntity querySaleDeptByCode(String code);

	/**
	 * 
	 * 查询外场驻地营业部或本营业部
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-28 下午12:11:36
	 */
	String queryStationDeliverOrgCode(String orgCode);
	
	/**
	 * 按标杆编码查询组织部门实体
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-1 下午4:16:21
	 */
	OrgAdministrativeInfoEntity queryOrgByUnifiedCode(String unifiedCode);
	
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
 	 * 根据银行编码查询银行信息
 	 * 
 	 * @author 025000-FOSS-helong
 	 * @date 2012-12-6 下午04:43:42
 	 */
 	BankEntity queryBankByCode(String code);
 	
 	/**
	 * 
	 * 查询货物类型
	 * @author 025000-FOSS-helong
	 * @date 2013-3-7 下午05:24:44
	 * @param entity
	 * @return
	 */
	List<GoodsTypeEntity> findGoodsTypeByCondiction();
	/**
	 * 
	 * 获取订单DTO
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-16 下午7:08:05
	 */
	public CrmOrderDetailDto importOrder(String orderNumber);

	/**
	 * 
	 * 查询系统上线日期
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-11 下午7:22:10
	 */
	Date queryFossGoliveDate();

	/**
	 * 
	 * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息(查远程)
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:54:01
	 */
	OuterBranchEntity queryExsitAgencyBranchCompanyInfo(
			String customerPickupOrgCode);
	
	
	/**
	 * 根据virtual code查询走货路径
	 * @param virtualCode
	 * @return
	 */
	FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode);
	  
    /**
     * 
     * 获取空运预计出发时间
     * @author 025000-FOSS-helong
     * @date 2013-3-8 上午11:55:47
     * @return
     */
    Date getAirLeaveTime(String orgCode , Date flightTime , String flightNumberType);

    /**
   	 * 根据客户编码查询客户合同信息
   	 * @author 026123-foss-lifengteng
   	 * @date 2013-5-21 下午3:09:27
   	 */
   	public CusBargainEntity queryCusBargainByCustCode(String custCode);

 	/**
 	 * 根据工号集合查询对应的员工对象集合
 	 * @author 026123-foss-lifengteng
 	 * @date 2013-3-26 下午8:50:46
 	 */
	public List<EmployeeEntity> queryEmployeeByCodeList(List<String> codes);
	
	/**
	 * 统一返回的计价值
	 * @author WangQianJin
	 * @date 2013-6-17 下午6:32:19
	 */
	public List<GuiResultBillCalculateDto> queryGuiBillPrice(GuiQueryBillCalculateDto billCalculateDto);


	/**
	 * 通过运单ＩＤ获取运单号
	 * @param waybillID
	 * @return
	 */
	public String queryWaybillNOByID(String waybillID);
	
	/**
	 * 集中开单查开单组所属中转场的默认出发部门
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	SaleDepartmentEntity queryPickupCentralizedDeptCode(String billingGroupOrgCode);
	
	/**
	 * 判断产品时效是否存在
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	boolean identityEffectivePlan(String departDeptCode, String arriveDetpCode,
			String productCode, Date createTime);

	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * 
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);
	
	/**
	 * 
	 * 根据运单号，快递代理理理公司代码查询外发单(用于结算查询是否存在有效外发单)
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-24
	 */
	boolean checkIfExistValidLdpExternalBillForStl(String waybillNo,String agentCompanyCode);

	/**
	 * 根据网点编码查快递代理网点信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 上午11:47:58
	 */
	OuterBranchExpressEntity queryLdpAgencyDeptByCode(String deptCode);

	/**
	 * 根据运单号查询运单是否快递代理递代理递代理公司
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-29 下午12:43:04
	 */
	boolean isLdpHandOveredOutBranch(String waybillNo);

	/**
	 * 根据运单号查询快递运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午3:43:53
	 */
	WaybillExpressEntity queryWaybillExpressByNo(String waybillNo);

	boolean isCanPaidMethod(CusBargainVo vo);

	boolean isCanPaidMethodExp(CusBargainVo vo);
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo);
	
	/**
	 * 获取CRM营销活动信息
	 * @创建时间 2014-4-16 下午7:46:54   
	 * @创建人： WangQianJin
	 */
	CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo param);
	
	/**
	 * 根据客户编码获取客户信息
	 * @创建时间 2014-4-22 下午1:24:08   
	 * @创建人： WangQianJin
	 */
	CustomerDto queryCustInfoByCodeNew(String custCode);
	
	/**
	 * 获取营销活动折扣信息
	 * @创建时间 2014-4-22 上午10:51:48   
	 * @创建人： WangQianJin
	 */
	MarkActivitiesQueryConditionDto getActiveDiscountInfo(MarkActivitiesQueryConditionDto entity);

	CityMarketPlanEntity getCityMarketPlanEntityCode(String code,
			String deptCode, Date date);
	
	/*
	 * 获取配置参数（读取数据库）
	 */
	public ConfigurationParamsEntity queryConfigurationParamsByEntity
		(String model,String config,String orgCode);
	/**
	 * 是否有效的外发营业部，从而判定是否建立绑定关系
	 * @创建时间 2015-2-2 下午1:24:08   
	 * @创建人： yangxiaolong
	 */
	boolean isExternalBillRecords(String waybillNo,String type);

	WaybillExpressEntity queryWaybillByOriginalWaybillNo(String waybillNo,
			String returnType);

	
	WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo,
			String waybillExpresstypeReturnCargo);
	/**
	 * 查询发货类型
	 * @return
	 */
	public List<DataDictionaryValueEntity> queryDeliveryMode();

	boolean cheCustomerPhoneExistCustomerMobilePhone(CustomerQueryConditionDto customerQueryConditionDto);

	BigDecimal queryDiscountFeeByEmployeeNo(String employeeNo, Date recevieDate);

	//通过原单号查询上报工单信息
	ReturnGoodsRequestEntity queryReturnGoodsRequestEntityByWayBillNo(
			String originalWaybillNo);

	WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo);
	
	List<GuiResultBillCalculateDto> queryGuiExpressBillPrice(GuiQueryBillCalculateDto billCalculateDto) ;
	
	/**
	 * 根据订单号查询订单信息
	 * */
	String  queryOrderByOrderNo(String orderno);

	PriceEntity queryPriceEntryByCode(String pricingCode);
	
	//合伙人月结开单，校验资金额度 2016年1月23日 18:16:38 葛亮亮
	SynPartenerMonthlyLineDeductResponse partenerMonthlyLineDeduct(PartenerDeductDto deductDto);
	
	//合伙人预存款资金开单校验 2016年1月25日 14:29:18 葛亮亮
	SynPartenerPrestoreDeductResponse partenerPrestoreDeductResponse(PartenerPrestoreDeductDto prestoreDeductDto);
	
	/**
	 * 查询代收上限
	 * @param code
	 * @return
	 * @author:280747-foss-zhuxue
	 * @date:2015/10/29
	 */
	SaleDepartmentEntity querySaleDepartmentByCodeNoCache(String code);
	
	//根据运单ID获取折前信息 2016年4月15日 14:51:53 葛亮亮
    PartnersWaybillEntity queryPartnersWaybillEntityById(String wayBillNoId);

	Boolean queryISPICPACKAGEByWaybillNo(String waybillNo);
	
	/**
	 * <p>FOSS校验悟空快递单号格式是否正确</p> 
	 * @author 272311-sangwenhao
	 * @date 2016-8-5 上午9:28:33
	 * @return
	 */
	boolean validateWaybillNoIsCorrect(Map<String, String> map) ;
	CustomerEntity selectCustomerEntitybyCode(String code);
}