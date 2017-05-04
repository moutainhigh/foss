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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillManagerService.java
 * 
 * FILE NAME        	: WaybillManagerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.pojo.domain.foss2ecs.CustomerAddressFossRequest;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.crm.server.order.FossQueryAcctinfo;
import com.deppon.foss.esb.crm.server.order.FossQueryAcctinfoRequest;
import com.deppon.foss.esb.crm.server.order.FossQueryAcctinfoResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFocusRecordManagementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPriceCouponService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISourceCategoriesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISynNonfixedCustomerToCrmSerivce;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerAssociatedInformationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PriceCouponDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FreightRouteException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IReceiveAddressRfcService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.AbandGoodsApplicationConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ReceiveAddressRfcEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPdaBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IToAddPartnerProgramService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IValidateForCUBCService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ToAddPartnerProgramEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DebitForCUBCDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectiveExpressPlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReturnBillProcessConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaScanDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IReturnBillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISalesDepartmentDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IGisQueryService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IHisDeliveryCusService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IHisReceiveCusService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IIntelligenceTimeRecordService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOrderLocksService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPartnersWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaScanService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendSMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushFoss2EcsWaybillNoLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushFoss2EcsWaybillNoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISendSmsVoiceService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISyncCUBCLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISyncWaybillLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysWaybillInfoToOmsService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IValuateFoss2EcsWaybillNoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWSCWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillAcHisPdaService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillHomeImpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToCUBCService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPaymentService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IinstallationService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.CustomerAddressFossDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyConditionDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyDto;
import com.deppon.foss.module.pickup.waybill.server.service.IWaybillStockService;
import com.deppon.foss.module.pickup.waybill.server.utils.CommonUtils;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.HttpClientUtil;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.TradeAttributeDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.TradeDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IGrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.define.OrderConstant;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.CUBCLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockResult;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaScanEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddedPlanFeeCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.NeedCreateTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAWayBillRfcDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.RouteLineInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ValueAddServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.HandleTransportPathException;
import com.deppon.foss.module.pickup.waybill.shared.exception.PdaInterfaceException;
import com.deppon.foss.module.pickup.waybill.shared.exception.TodoActionException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.request.HisSegMatchRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.HisSegMatchResponse;
import com.deppon.foss.module.pickup.waybill.shared.util.ConvertBean;
import com.deppon.foss.module.pickup.waybill.shared.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmParamVo;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.domain.ExpressTrackExternalEntity;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.departure.api.shared.domain.BusinessInfoEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService;
import com.deppon.foss.module.transfer.load.api.server.service.IMakeUpWaybillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPathDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.InviteVehicleException;
import com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单管理服务wq
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午4:07:32,content:
 * 
 * * * 录入货物信息SUC业务规则 1. 若货物为违禁品，则系统自动提示“货物为违禁品，不可开单！”； 2.
 * 若货物为贵重物品，则系统自动勾选“贵重物品”，且不可修改； 3. 若货物为限保物品，则系统自动限定保价金额，且不可修改，并提示“货物为限保物品”； 4.
 * 违禁品、拒收品、贵重物品、限保物品（含保价金额上限）具体类型可在系统中进行配置； 1. 货物重量单位为千克； 2.
 * 运单开单时，货物的件数、重量及体积文本框是必填项，默认值为空； 3. 件数只能是大于等于1的整数，重量及体积只能大于0。 4.
 * 体积、重量这种过程数据保留两位小数 1. 尺寸录入文本框，支持'长*宽*高*件数+长*宽*高*（即多个尺寸相加）的计算； 2.
 * 如果件数是1，则不强制在录入时要“*1”； 3.
 * 货物尺寸为计算器输入，输入的尺寸可以进行加减，例如：1*1*1*5+2*2*2*3-0.5*0.5*0.5， 显示为输入文本； 4.
 * 尺寸计算单位为厘米，尺寸计算出数据后转换单位为立方米后，在货物体积中显示数据；
 * 例如：尺寸录入为：50*50*20（其中20为件数），则体积显示数据为：0.05； 5.
 * 体积为空时，录入尺寸后，填充体积。当尺寸修改时，体积随之变处。当修改体积时，尺寸不变化。 6.
 * 体积初始值为“0”，仍保持必填，操作员根据实际情况改，选择木架或木箱或两者都选择后则该值不能为0 1. 货物体积单位为立方米； 2.
 * 营业员可以修改通过尺寸计算器计算得出的体积数据； 3. 系统设置货物重量体积比区间值（该值由基础资料配置），在运单提交时，系统自动对重量体积比进行校验：
 * 即重量体积比X=重量/体积； 3.1 当X不在设置的区间中，弹出提示“请确认录入的重量体积是否准确！”；
 * （该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面；点击取消，点返回运单录入界面； 3.2 当X在区间中，无提示；直接进入确认运单信息界面；
 * 4. 录入重量体积后，系统校验单票的重量体积及单件（平均单件）的重量体积是否
 * 满足“修改-查询行政组织业务属性”基础资料中的单票和单件重量体积限制；只要该四项中有 一项不满足，则提示“XX超出提货网点限制，请重新选择提货网点！”；
 * 1. 货物包装总件数小于等于货物总件数，如果大于总件数，提示：“包装件数不能大于总件数”； 1.
 * 当木包装件数大于等于1时，系统校验出发城市和对应目的站的走货路由中是否有有打木架功
 * 能的部门，若有，则提醒营业员“是否收入代打木架？”，是，则进入场代打木架信息录入界面”，
 * 并显示第一个有打木架功能的部门名称；若走货路由中无有打木架功能的部门，则不提示“是否收入代打木架？”； 2.
 * 当走货路由中有有打木架功能的部门为多个时，只显示系统路由中第一有有打木架功能的部门， 且不可修改； 1.
 * 运输类型为汽运时，货物类型为唯一选择项；即，非A即B；默认不可勾选，只有当走货路由经过 特定的城市时需要录入货物类型,特定城市可在系统中进行配置； 2.
 * 运输类型为空运时，货物类型为下拉选择框，默认显示为普货，目前只有这一个分类，该类型可做配置； 1. 贵重物品判断规则： a.
 * 单票单件货物，体积≤0.05立方且保价声明价值≥1万元； b. 单票多件货物，平均单件体积≤0.2立方且平均单件保价声明价值≥1万元；
 * （注：平均单件体积=开单总体积÷开单件数，平均单件保价=开单保价声明价值÷开单件数） c. 高保价货物，单票货物保价≥10万元；
 * 满足以上任意一个条件时，系统将判定该票货物为贵重物品并自动勾选"贵重物品"复选框，且为灰色，
 * 不可编辑；不满足以上条件时，该复选框为可编辑状态，用户可根据实际情况自行选择是否勾选； 2.
 * 若"贵重物品"复选框被勾选，则在【储运注意事项】中自动加入提示记录"贵重物品"字段，
 * 若该货物为贵重物品时，"储运注意事项"中信息显示优先级为：贵重物品＞其他； 1. 录入的打木架货物件数和打木箱件数之和必须大于等于录入的木包装件数； 2.
 * 系统默认标签流水号前X的货物为需要代打木架货物，X等于录入的“打木架货物件数”和“打木箱货物件数”之和； 3.
 * 营业员在打印标签时，按流水号先贴要打木架或打木箱的货物； 1.
 * 录入的打木架货物体积和打木箱货物体积之和乘以1.4必须小于等于货物总体积；该1.4为打木架体积计算系统， 可配置； 2.
 * 营业部开单时按打完木架后的包装开，即包装中含“木架/木箱”，开单件数为货物打木架/木箱前的实际件数 （防止丢货），尺寸和重量按照以下公式计算： 1.1
 * 整票货物代打时：开单体积=代打货物体积*1.4；开单重量=所有货物重量+代打货物体积*42； 1.2
 * 部分货物代打时，开单体积=代打货物体积*1.4+未打木架货物体积；开单重量=所有货物重量+代打货物体积*42。
 * 即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，重量另加，单票中未打木架的货物的体积和重量不变； 1.3
 * 例如：一票货物需全部代打，货物体积为1个方，重量为100KG，则开单体积为1.4个方，
 * 开单重量为100+1*42=142KG，收取客户包装费为150*1.4=210元； 1.4
 * 需要加托时，仍按照50元/个另外收取费用，托的重量和体积不再另加；营业部不需要再更改由于打 木架引起重量和体积的变化； 3.
 * 打木架要在“对内备注”中备注“代打木架/木箱”，特殊要求（特别是合打情况）必须在对内备注 和A4纸上都注明，例如：货物1、2、3合打成一件等； 4.
 * 开单件数为代打木架前货物实际件数，包装为打木架后的包装，打木架后件数发生变化后，需及时更改件数；
 * 
 * 
 * 录入运输信息SUC业务 规则 1. 收货部门默认为操作者所在的部门，不可修改； 2.
 * 若操作者部门为集中开单部门时，则收货部门可修改，其选择部门只能为开单组服务的集中接货区域营业部；
 * 且其进进入运单开单界面时，系统自动默认收货部门为上一次开单的收货部门； 1. 默认显示精准； 1.
 * 根据部门的性质或所在城市确定部门所作业务的性质，如有些部门可做所有运输性质的业务，
 * 部分部门无法做精准业务，所有规则根据营业部业务管制确定（根据出发城市、到达城市确定部门可做的业务性质， 所有数据读取自营业部业务管制基础资料）； 1.
 * 当运输性质为空运时，提货方式有：自提（不含机场提货费）、免费自提、机场自提、免费送货、
 * 送货进仓、送货上楼、送货（不含上楼)；默认为自提（不含机场提货费）； 2.
 * 如果客户是CRM中的合同客户，在开单时提货方式选项中增加"免费送货"选项，免费送货送货费为0（不可修改）； 3.
 * 当运输性质为精准、普货、偏线时，提货方式有：自提、免费送货、送货进仓、送货上楼、 送货（不含上楼)、内部带货自提；默认为自提； 1.
 * 当运输性质为空运时，开单提货网点显示空运代理网点及公司可做空运的自有网点； 2. 当运输性质为偏线时，提货网点只显示偏线代理网点； 3.
 * 当运输性质为精准、普货时，提货网点显示我司所有可所到达的网点； 1. 当运输性质为空运，且提货方式为机场自提时，增值服务的其它费用中可添加对就有规则的费用
 * （暂定方案：对于单独开单的可做增值服务费用添加。若以后在产品定义中区分了单独开单和合大票，则另考虑）； 1.
 * 提货方式为机场自提时，代收货款设置为0且不可编辑，不能开单含到付款，，付款方式不能开到付；
 * 否则，提示：“提货方式为【机场自提】时，到付金额必须为0，付款方式不能为到付”； 2.
 * 提货方式为内部带货自提时，只能填写发货人收货人信息和货物信息，所有涉及金额的控件均为0， 收货人和发货人只能为OA系统中部门； 1.
 * 提货方式为自提（不含机场提货费）、免费自提、内部带货自提时，只显示可做自提业务的网点； 2. 提货方式为送货时，只显示可做送货业务的网点； 1.
 * 目的站可由收货客户地址的城市（即收货客户地址中的“市”）生成，也可手工录入； 2. 系统自动过滤只显示目的站城市符合条件的网点信息； 3.
 * 选择确定提货网点信息后，系统自动生成对应网点的目的站名称于目的站框内； 网点目的站读取对应的网点目的站基础资料； 4.
 * 在网点目的站基础资料中有‘取消到达日期’，如果当前日期在‘取消到达日期’之前，
 * 那么提示“xx营业部将于xx年xx月xx日临时取消到达，届时货物将转至xx营业部，请做好客户解释工作！”
 * （其中第一个xx营业部，为当前营业部、第二个xx营业部为网点目的站基础资料中的‘转货部门‘， xx年xx月xx日为‘取消到达日期’） 5.
 * 录入重量体积后，系统校验单票的重量体积及单件（平均单件）的重量体积是否满足
 * “修改-查询行政组织业务属性”基础资料中的单票和单件重量体积限制；只要该四项中有一项不满足， 则提示“XX超出提货网点限制，请重新选择提货网点！”； 1.
 * 当通过运输性质、提货方式和目的站过滤的提货网点唯一时，直接显示提货网点名称； 1.
 * 当勾选上门接货时，自动显示文本框，录入接货司机工号，接货费数字框可录入，手写现付金额，可编辑 2.
 * 当不勾选上门接货时，接货费清0变灰且不可录入，手写现付金额变灰，不可编辑 1. 当录单部门为集中开单部门时，自动勾选上门接货，且不可修改； 2.
 * 上门接货默认不勾选，可修改；3. 1. 对外备注可多选，选择的项目信息依次显示在储运注意事项中；默认为空； 2. 当选择空时，则其它所有选项自动不勾选；
 * 3. 对外备注已选择录入后，若再选择空，则清空已选择的所有对内备注； 1.
 * 储运注意事项=对外备注&对内备注&大车直送（若勾选大车直送），各字段以“；”分开； 2. 对外备注永远在储运注意事项的最前面； 1.
 * 当运输性质为精准、普货、偏线时，显示配载线路；系统自动根据营业部所在城市和到达目的站 匹配走货线路基础资料，生成预配线路，且不可修改； 2.
 * 当运输性质为空运时，显示配载航班，包括：早班、中班、晚班、中转航班；默认为空； 1. 系统自动匹配始发配载部门基础资料，通过配载类型来判断配载部门； 1.
 * 如果提货网点为自有网点时，最终配载部门为提货网点名称；如果提货网点不是自有网点，
 * 则最终配载部门为外发代理网点的管理部门；（参考基础资料：外发代理、部门基础信息基础资料）； 2.
 * 当运输类型为空运时，最终配载部门可编辑，且列举对应收货部门可走空运货的空运总调， 默认显示为空；若对应收货部门可走空运货的总运总调唯一时，则直接显示；
 * 1. 预计出发时间在运单提交时进行判断填充； 2. 预计出发时间适用于运输类型为“精准”； 3.
 * 预计出发时间=预计出发日期,准点出发时间。【格式如：2011-6-28 ,12:00:00】
 * （部门对应的“准点出发时间”，数据取自基础资料：经营-运作基础资料）； 4.
 * 如果开单当前时间在准点出发时间前，则预计出发日期=开单日期；否则，预计出发日期=开单日期+1； 5.
 * 当运输性质为普货、偏线时，则预计出发日期=开单日期+1； 1. 预计提货/派送时间适用于运输类型为“精准”； 2.
 * 提货方式为“自提”时，若部门对应的“是否当天出发”为“是”， 则预计提货/派送时间=预计出发日期+到达营业部承诺天数：到达营业部承诺时点；
 * 否则，预计提货/派送时间=预计出发日期+到达营业部承诺天数-1：到达营业部承诺时点； （部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准；
 * “到达营业部承诺天数”和“到达营业部承诺时点” ，数据取自基础资料：专线-城市承诺时间标准；） 3. 提货方式包含为“送货
 * ”时，若部门对应的“是否当天出发”为“是”， 则预计提货/派送时间=预计出发日期+到达营业部承诺天数+派送承诺需加天数：
 * 派送承诺时点；否则，预计提货/派送时间=预计出发日期+派送承诺需加天数-1：
 * 派送承诺时点；（部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准；
 * “到达营业部承诺天数”、“到达营业部承诺时点”、“派送承诺时点”、“派送承诺需加天数”， 数据取自基础资料：专线-城市承诺时间标准；） 1.
 * 大车直送默认不勾选，且不可修改； 2. 当单票货物重量超过8吨或体积大于25方时，系统提醒“是否需要大车直送？”；
 * 营业员确定后，自动勾选大车直送，且大车直送变为可修改状态； 3. 勾选大车直送时，在储运注意事项中增加大车直送显示；不勾选时，不显示；
 * 1、检验单号是否唯一（有效状态的运单唯一，中止/逻辑删除等的运单不参与检查），
 * 如果唯一系统不做操作，如果不唯一，系统提示“XX单号已经使用，请重新输入单号”
 * 2、新增一条业务规则：在提交运单之前，若相邻两次输入的单号（两次单号分别为A与B且单号输入合法）
 * 差别较大时，系统给予用户友好提示避免录错单号，但不限制单号的输入。具体规则为： 1）若前后两次输入的单号位数相同，当|A-B|≥100时系统给予提示；
 * 2）若前后两次输入的单号位数不同，系统给予提示； 3）提示信息为：前后两票单号相差过大，请检查所输单号是否是本部门所属单号！
 * 
 * 增值服务SUC规则如下 1.1 相关业务用例 BUC_FOSS_5.20.30_510 确认承运信息 1.2 用例描述 营业员通过本用例录入增值服务信息。
 * 1.3 用例条件 条件类型 描述 引用系统用例 前置条件 后置条件 1.4 操作用户角色 操作用户 描述 营业员 可查询、录入、修改增值服务信息 1.5
 * 界面要求 1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.2.1 录入增值服务信息 1.5.2.2 查询发货客户代收货款退款联系人
 * 1.5.2.3 查询其它费用 1.5.3 界面描述 营业员点击运单开单，进入运单开单界面。 本界面为录入增值服务信息。
 * 界面主要分为二个部分：录入增值服务、查询其它费用。 1. 录入增值服务
 * 录入增值服务分为三个部分：录入增值服务信息界面、录入其它费用列表、查询发货客户代收货款退款联系人； 1.1 录入增值服务信息界面
 * 录入增值服务信息界面包括： 1.1.1 保价声明价值； 1.1.2 保价费率：保价费率可由基础资料配置，可按出发城市区域，出发营业部等； 1.1.3
 * 保价费； 1.1.4 代收货款； 1.1.5 代收费率； 1.1.6 代收手续费； 1.1.7 退款类型：包括三日退、退日退、审核退，默认三日退为空；
 * 1.1.8 收款人姓名； 1.1.9 收款人帐号； 1.1.10 包装费； 1.1.11 装卸费； 1.1.12 送货费； 1.1.13 其它费用合计；
 * 1.1.14 接货费； 1.1.15 返单类别：包含“无需返单”、“客户签收单原件返回”、“客户签收单传真返回”、
 * “运单到达联传真返回”包括无需返单、原件签收单返回、传真件签收单返回、扫描件返回，默认无需返单； 1.1.16 预付费保密； 1.2 录入其它费用列表
 * 录入其它费用列表包括： 1.2.1 费用名称； 1.2.2 费用金额； 1.2.3 新增其它费用：功能按钮； 1.2.4 删除其它费用：功能按钮； 1.3
 * 查询发货客户代收货款退款联系人 1.3.1 操作列； 1.3.2 开户银行； 1.3.3 收款人姓名； 1.3.4 银行帐号； 1.3.5 备注信息；
 * 1.4 录入包装费 2. 查询其它费用 查询其它费用分为四个部分：其它费用列表 、功能按钮； 2.1 其它费用列表： 其它费用列表包括： 2.1.1
 * 名称； 2.1.2 归集类别； 2.1.3 描述； 2.1.4 金额； 2.1.5 金额上限； 2.1.6 金额下限； 2.1.7 是否可修改；
 * 2.1.8 其它费用可基础资料配置，并支持是否启用和是否可见的维护； 2.2 功能按钮： 2.2.1 查询：包括查询条件：名称； 2.2.2 确定；
 * 2.2.3 取消； 1. 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,
 * 且保价费率可手动调整；（限保物品从限运物品基础资料中获取）；非限保物品的保价费率不可修改； 2. 实际保险费小于最低保费的按最低保费收取； 3. 保价费
 * = 保价申明价值*保价费率，不可修改； 4. 保价申明价值默认为3000，可以修改；保价声明价值不封顶； 5.
 * 精准（长途）、普货（长途）、偏线，最低一票8元；普货（短途）、 精准（短途）最低一票6元；空运最低一票10元；所有运输方式保价超过最低均按0.4%收取
 * （数据读取自保价设置基础资料）；长短途数据读取计价基础资料； 6. 实际保价费小于最低保费的按最低保费收取； 7.
 * 保价费率首先是配置的标准费率。当有区域保价费率时，以区域保价费率为准。 当客户为合同客户时，则以合同签订为准。所有的保价费率以读取的为准，不可修改。
 * 限保物品的保价费率同样不可修改 行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，
 * 开单是否可以进行返回签单、货到付款、代收货款需要根据到达部门属性 是否可以（返回签单、货到付款、代收货款）来决定 1.
 * 如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，代收货款设置为0且不可编辑； 2.
 * 如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
 * 代收货款可编辑，且可以选择所有的退款类型，若CRM中若无账户信息，则退款类型只能选择审核退 3. 开单时系统默认代收货款为空； 4.
 * 代收货款栏默认为空，如果没有代收货款，则要求输入0；否则，进行提示：“请确认该单没有代收货款，
 * 如无，请输入数字0”；当代收货款大于0时，输入后，对于选择的退款类型，有如下限制： 3.1 三日退：在收到客户代收货款后第三天给客户打款。 3.1.1
 * 默认退款类型为三日退； 3.1.2 代收10000元以下费率0.5%， 10000元以上费率0.4%；最低10元/票，最高100元/票；
 * 有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。 3.2
 * 审核退：收到客户代收货款，出发部门审核后，给客户打款。 3.2.1
 * 代收10000元以下费率0.5%，10000元以上费率0.4%；最低10元/票，最高100元/票（
 * 通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。 3.2.2
 * 选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。 3.3 即日退：在收到客户代收货款后24小时到账。
 * 3.3.1 代收手续费率1%，最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市 与部门代收货款费率”实现）。
 * 3.3.2 必须先录入客户收款银行信息，提交时，银行信息不能为空； 3.3.3 只支持4个银行：农行、工行、建行、招行；否则，给予提示信息； 5.
 * 限制代收货款金额不能小于10元，可以等于10元；但可以为0；该数字“10”可由基础资料配置； 6.
 * 网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，有数据时不可对代收货款进行修改， 只可起草出发更改进行修改；若网上代收货款为0
 * ，系统可支持修改代收货款金额； 7. 默认的代收费率由基础资料配置； 1. 保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额； 1.
 * 代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，且只能选择，不能修改；
 * 当退款人姓名和帐号唯一时，直接显示；（数据读取CRM客户信息资料（退款帐户信息）） 2.
 * CRM客户信息资料的要先在CRM中录入客户退款帐户信息，且第一次在我司办理代收货款业务时， 退款类型只能为审核退； 3.
 * 同一客户多个银行信息的显示问题：当有两个或以上账号时，弹出账号信息（包括开户银行、收款人、
 * 账号、备注），选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置； 1. 包装费默认为0，可手工修改； 2.
 * 当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
 * 且可修改，修改的金额只能大于等于默认显示金额；其中150、30、300、40为打木架单价（元/方）、打木架最低一票、
 * 打木箱单价（元/方）、打木箱最低一票，可由基础资料配置； 1. 装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ， 开单显示运费（C）=Q*Z=C0+M
 * 。且此显示费率不可更改；（对于专线的散客而言） 2. 如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，为系统默认的公布价。
 * 开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，开单显示运费（C）=Q*Z=C0+M ； 3.
 * 当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），
 * 需先清空装卸费为零，然后再修改数据。如：月结客户与非月结客户、整车与非整车、目的站、重量、体积； 4.
 * 当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。否则，清空装卸费为零，
 * 最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。（其中Q1是装卸费M1时的费率,Z为重量/体积）； 5.
 * 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。（只限制配载类型为专线的，包括月结）； 6.
 * 对于显示费率不等于显示费率乘以重量的问题，要求如下： 6.1.
 * 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。 且显示运费等于该显示费率*重量； 6.2.
 * 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，令费率（B）=费率（A）
 * 的取前2个小数位的值（注：直接截取A的值，不四舍五入）。则显示费率（Q）=费率（B）+0.01。 显示运费等于该显示费率*重量； 7.
 * 只要含与不含装卸费两者有交叉的，均以不含为准； 8. 装卸费特殊部门表： （建议：做为可配置的基础数据表） 9.
 * 2012年12月1日开业的部门不能开装卸费 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费 11.
 * 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。 12.
 * 装卸费上限由增值服务配置基础资料实现（在产品API中体现）。 1. 送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，
 * 不能下调。当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值， 但用户可以上调送货费； 2. 通过送货费基础资料来实现： 2.1.
 * 若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止； 2.2.
 * 若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
 * （即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值， 取值终止。） 2.3.
 * 特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 2.3.1
 * 先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，再判断开单重量在已被体积筛选
 * 出来的记录中的哪个重量区间，来确定是哪一条记录。然后再根据费率计算，计算出来的值与该条的最低
 * 送货费进行比较，若小于最低送货费时，就取最低送货费，取值终止；若大于最低送货费且小于最高送货费时，
 * 就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。 2.3.2 标淮派送范围收取送货费标准： 货物重量 标准 0-300KG
 * 55元/票 301-500KG 0.2元/KG 501KG或2.5立方米以上 100元/票，不封顶 2.3.3
 * 当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
 * 开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于 【最高送货费】” 2.3.4
 * 仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。 2.3.5
 * “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也 可以向下修改，最小为0） 2.3.6
 * 除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改 2.3.7 最高送货费做基础资料配置； 3. 非标准派送范围加收操作费标准： 3.1
 * 超远加收送货费标准： 距离（公里） 30-50 50-100 100-150 加收金额（元） 50 100 150 3.1.1
 * 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）； 3.1.2
 * 客户所在地30公里范围内如果有公司的营业网点，无论是否做派送，该区域均不能收取超远加收送货费； 3.1.3 非标准派送的费用添加无上限 3.2
 * 特殊区域（进仓）： 3.2.1 特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）； 3.2.2 收费标准：进仓费实报实销，并加收150元操作费；
 * 4. 区域送货费限制： 4.1 当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，但前提是：提货方式必须为送货)时，
 * 送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置） 4.2
 * 当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦
 * 营业部、深圳华强钟表市场营业部）。（该类营业部类型、送货费固定标准、成本提取标准可配置） 4.3 内部带货、空运、偏线及中转下线不受上述需求的限制。 5.
 * 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域： 5.1
 * 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，
 * 或体积是否超过X立方，是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。
 * （单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】 5.2
 * 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，其他费用中的“送货上楼费”屏蔽或显示但不可选择； 5.3
 * 若“XX区域”或其它限制类型区域再开派送部，适用以上需求； 5.4 空运、偏线及中转下线不受上述需求的限制； 5.5 内部带货受上述需求的限制； 5.6
 * “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置； 1.
 * 开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加， 费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置）
 * 2. 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置） 3. 综合服务费：（费用金额由基础资料配置） 3.1
 * 综合服务费默认为2元不可修改、剔除； 3.2 月结客户可以删除2元的综合服务费； 3.3 淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费；
 * 4. 燃油附加费：（费用金额由基础资料配置） 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改； 5.
 * 其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改； 6. 其他费用合计等于其他费用列表中各项费用数据之和； 1.
 * 原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单； 2. 空运、偏线和中转下线的“返单类型”不可选择； 3.
 * 若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用； 4.
 * 如果选择有返单类型，系统会自动生成一条签收单记录，记录信息包含：运单号、运单ID、库存状态、
 * 当前操作部门（运单开单时，是填开部门）、是否签收、是否作废、出发部门(运单开单出发部门)、 签收单类型、签收状态； 5.
 * 月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值）， 非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除
 * 1. 运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密； 2.
 * 已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。未开启预付运费保密运单提交后，
 * 若货物未有非本部门入库操作，则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
 * 则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单； 3. 运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择； 4.
 * 已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，涉及预付运费有变动时， 不影响预付费保密功能； 5.
 * 开启预付运费保密，预付运费不能为0，否则不能保存、提交； 6. 开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行； 7.
 * 开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时， 必须取消预付运费保密后才能提交； 8.
 * 预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示； 9.
 * 预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示； 1. ； 2.
 * 1）开单总费用、预付金额、到付金额，取整，四舍五入；
 * 2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100, 100.10显示100.1；
 * 3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。
 * 4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。 1. 接货费只能录入数字
 * 1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 2）限保物品的保价费率通过基础资料进行配置；
 * 3）取消偏线、空运最高保价5000元的限制； 2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置
 * 3、"其它费用"中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。
 * 4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制
 * 
 * 
 * * 运单收银SUC业务规则 1. 计费类型分为重量计费、体积计费，由系统自动生成，不可修改；默认重量计费； 2.
 * 对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费； 若按重量计费运费较按体积计费运费较高，则计费类型为重量计费；
 * 若按体积计费运费较按重量计费运费较高，则计费类型为体积计费； 3. 运输类型为汽运时,计费重量为空，不可修改；
 * 4.运输类型为空运时计费重量应为重量和体积*1000000/6000进行对比，取大； 1. 费率为对应计费类型、目的站、提货网点及运输类型的走货单价；
 * 2. 目的站、提货网点及运输类型确认后，即可自动显示对应计费类型的费率； （来自价格基础资料） 3.
 * 费率可以保留到小数点后2位；运费、预付金额、到付金额为整数， 按照四舍五入的原则； 1. 公布价运费、增值服务费用、优惠合计需通过点击计算费用获取； 2.
 * 当提货网点信息未录入，不可计费运费；当录了提货网点， 货物的重量和体积以及其他服务费用录入不完整时， 点击计算费用，得到当前填写的信息费用信息； 3.
 * 公布价运费（即重量、体积计费的运费）=每公斤单价/每方价格与货物实际重量/体积的乘积，
 * 对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费；（来自价格基础资料） 4.
 * 增值服务费=送货费+包装费+保价费+代收手续费+接货费+其他费用合计； 5. 优惠合计=优惠总合计； 6.
 * 总运费=公布价运费+增值服务费-优惠合计=预付金额+到付金额； 7. 在提交运单时，系统记录该次计算费用时所用的所有价格费用的规则版本号； 8.
 * 采用费用明细取整、总费用取整的原则 9. 任何客户包含月结客户不可减免综合服务费。 10.
 * 开单界面选择付款方式为"现金"时，默认预付金额为开单总金额并允许修改，且修改的同时到付
 * 金额随预付金额变化而变化，变化规则为：到付金额=总金额-预付金额，到付金额不能修改。
 * 例如，开单界面选择付款方式为"现金"时，当一票货的总金额为300元时，默认预付金额为300元，到付
 * 金额为0，将预付金额改为100元之后，到付金额变为200元 1.
 * 必须输入提货网点、货物的重量和体积以及其他服务费用，计算出总费用后再输入验证编码进行验证； （优惠券编码开单时，对运单有上下限金额的限制，该上下限可配置）
 * 2. 输入优惠券编码后，焦点转移时，系统调用CRM接口，校验该编码是否存在，如果存在，则将优惠信息
 * 显示在优惠信息列表中，费用为对应的优惠券的金额；如果不存在提示为：你输入的优惠券编码不存在，请重新输入； 3.
 * 当修改优惠编码时，则焦点转移时，系统自动调用CRM接口，校验该编码，同时修改对应的优惠减免 记录和优惠信息； 4. 优惠金额必须小于等于开单总金额； 5.
 * 当付款金额为单一的现付或到付时，优惠券可以单独减免，当付款金额既有现付又有到付时，优先选
 * 择减免现付金额，如存在优惠金额此时减免现付有余时，可再充抵到付费用，但优惠金额必须小于等于开单总金额； 6.
 * 暂存时，只显示减免金额，运单实收或应收金额上不显示，即暂存时，不生成实收或应收单据； 7. 当费用变化时，优惠编码必须重新输入；8. 1.
 * 当发货客户有月结客户属性时，才可选择月结；并默认显示付款方式为月结； 2. 当发货客户有信用额度的属性时，才可选择临时欠款的付款方式； 3.
 * 支持发货客户的多种付款方式叠加，但月结和临时欠款不可同时出现； 4.
 * 当开单为订单导入开单，且为网上订单，并选择了网上支付时，导入开单后的付款方式显示网上支付； 同时付款方式可修改，付款方式中增加网上支付选项； 5.
 * 开单为空运、偏线、中转下线时不能选择“网上支付”； 1.
 * 当付款方式为现付时，预付金额必须大于0；否则，提示信息“付款方式非到付，预付金额不能小于等于0”； 2.
 * 当付款方式为到付时，预付金额不能大于0；否则，提示信息“付款方式为【到付】，预付金额不能大于0”； 3.
 * 月结客户的信用金额直接限制该客户的当月发货金额，当月发货金额超过信用额度，当月将无法再开单月结， 提示“该客户的剩余可用信息额度不足，不能开月结”； 4.
 * 临时欠款时，需要客户中的信用额度超过运费，否则，提示“该客户的剩余可用信息额度不足， 不能开临时欠款”；
 * 
 * 确认会员资质SUC规则 1. 签约合同客户： 1.1 营业部合同客户只享受合同优惠；当有促销的优惠时，合同客户可享受最大优惠折扣，
 * 但不叠加；（促销优惠为基础资料配置属性） 备注：当运单为网络订单（官网订单、阿里巴巴订单、淘宝订单、呼叫中心订单），
 * 且客户为合同客户时，为了保证不重复享受折扣， 除运单是阿里巴巴订单且月结客户优惠类型为“普通货物打折方案”以阿里巴巴费率计算外，
 * 其他情况都按照合同优惠来进行计算； 1.2 客户为非本部门的合同客户时，不在本部门享受合同优惠；；
 * 合同客户有绑定其他营业部时，可在所绑定的其他营业部享受合同归属部门同等优惠； 1.3 所有合同客户可以减免综合服务费； 1.4
 * 所有合同客户享受合同签订的保价费率和代收货款费率； 1.5 合同客户通过月结审核的，可选择付款方式为：“月结”； 其他客户不能选择为“月结”； 1.6
 * 合同客户为“价格折扣”优惠时，客户发货除最低每票的运费不受影响外，
 * 其他运费超过最低每票标准的，总运费按照其折扣比例打折优惠（折后总运费不低于最低每票标准运费）； 1.7
 * 合同客户为“月发月送”优惠时，客户发货价格按照月发月送标准； 1.7.1. 开单不能含装卸费； 1.7.2.
 * 当月发越送与空运同时存在时，以空运规则为准，可开装卸费； 1.8 合同客户有免费送货属性的，在优惠信息列表中自动优惠标准派送的送货费用，
 * 对于月结属性的客户可以向下修改送货费，最小为0；其它所有情况的送货费不可向下修改，只能向上修改； 1.9 合同用户仅在合同有效期内享受合同优惠。 1.
 * 非公司签约客户： 1.1 发货客户只能选择本部门的客户，但通过会员卡号可查询其他部门客户信息； 若客户为其它部门合同客户时，客户不享受合同优惠； 1.2
 * 客户优惠信息由基础资料配置，优惠信息包括：我司享受优惠区域部门、 优惠类型（如线路优惠、货物类型优惠等，可配置）、 优惠名称，对应的的优惠的具体信息；
 * 1.3 优惠类型有优先级，其优先级由基础资料配置； （当普通优惠与促销优惠同时同在时，促销优惠优于普通优惠；） 1.4
 * 系统自动根据承运信息，给出对应运单可享的优先级最高的优惠， 、当最高优先级并列出现时，默认勾选最大优惠的优惠方案； 对于同一优惠类型的优惠，存在互拆；
 * 例：当线路优惠和区域优惠同时存在时，系统只给出线路优惠； （点面原则（异常优先）：即点面同时存在时，以点为准。
 * 如：优惠1：上海出发货8折优惠；优惠2：上海到广州9优惠， 由于优惠2包含于优惠1中，为优惠1的一个异常， 则在生成优惠信息时，只显示优惠2的优惠方案）；
 * 1.5 阿里巴巴客户： 1.5.1 阿里巴巴订单导入开单时，对于诚信通会员， 系统按如下方式进行折扣： 运输类型 限制要求 普货 精准（长/短途）
 * 起步价格 30元 40元/20元 小于1000公斤或5立方 大于等于1000公斤或5立方 （该折扣表内容由基础资料配置）
 * 备注：对于阿里的普通会员，系统只减免2元的综合服务费。 1.5.2 阿里巴巴订单导入开单时，在“优惠方案中”自动新增“阿里巴巴优惠费”项，
 * 且系统自动计算优惠金额。公布价仍显示为公司散客开单标准的公布价。 （阿里巴巴优惠费=公司标准公布价总运费—阿里巴巴折扣的总运费）； 也为公布价优惠的一种；
 * 1.5.3 阿里诚信通会员客户下单后，阿里订单导入开单， 且“该客户同时为部门的月结客户，其月结优惠类型为‘普通货物打折方案’时”，
 * 则开单时阿里价格优于月结客户价格，以“阿里诚信通会员价格方案”计算运费；
 * 其他的月结优惠类型(公布价、价格折扣、月发月送)开单时月结客户价格优于阿里价格规则； 1.5.4 当阿里巴巴订单开空运和偏线不享受对应的阿里巴巴优惠，
 * 但可享受正常开单的优惠； 1. 客户享受的优惠类型自动显示在优惠信息显示列表中； 2. 合同优惠包括公布价折扣优惠和增值服务优惠； 3.
 * 对于公布价优惠：只显示客户可以享受的公布价优惠信息， 且默认勾选“合同规定的优惠方案”或“与合同优惠有冲突时， 系统规则使用的优惠方案”的公布价折扣优惠；
 * 4. 当客户为合同客户时，不仅显示合同公布价优惠， 还显示对应线路或货物类型或货物种类等其它配置的所有与本次承运相关的公布价优
 * 惠方案中系统规则使用的较合同优惠更优的优惠方案； 5. 当客户为合同客户时，若勾选非合同公布价优惠时且为非促销优惠时，
 * 则不再享受对应客户的所有合同优惠（包括公布价优惠、增值服务优惠及月发月送、月结等优惠）； 6.
 * 当客户为非合同客户时，自动默认勾选系统规则可使用的折扣最低的公布价优惠方案； 7. 公布价优惠方案只可勾选一种，不可多选； 8.
 * 合同客户的增值服务优惠项不可修改（即不可以取消或增加勾选）， 非合同客户的增值服务优惠项可修改（即可以取消或增加勾选） 9.
 * 任何客户包含月结客户不可减免综合服务费 1. 公布价优惠金额=本次承运的公布价总价*（1-优惠折扣）； 2. 增值服务优惠金额： 2.1
 * 保价费和代收手续款的优惠金额=声明价值/代收货款*（ 公司标准的保价费率/代收费率-签约合同的保价费率/代收费率）； 2.2
 * 其它优惠或费用减免由优惠规则配置生成； 1. 只有月结属性的客户才可以享受月结的付款方式；且当客户为月结客户时， 付款方式自动默认为月结； 2.
 * 只有信用额度的客户才可以享受临时欠款的付款方式；且对应客户的信用额度全国统配， 不绑定部门，例：客户A在我司可享信用额度为5000，且其合同主体为部门a，
 * A已用额度为2000，且无论客户A在a部门发货，或是在其它任务我司的部门发货， 其可用额度均为3000； 3. 系统自动过滤客户不能享受的付款方式；
 * 
 * 
 * 
 * 录入发货客户信息SUC业务规则 营业员点击运单开单，进入运单开单界面。 本用例分为两个界面：录入发货客户信息、选择发货客户； 1. 录入发货客户信息：
 * 界面为信息录入界面：包括：手机、电话、客户名称、 客户编码、发货联系人（发货部门）、发货人地址； 1.1 手机：发货人手机号码； 1.2
 * 电话：发货人电话号码； 1.3 客户名称：发货客户公司或单位名称，可支持搜索查询； 1.4 客户编码：我司给客户的客户号； 1.5
 * 发货联系人（发货部门，支持模糊搜索）： 发货客户的客户姓名， 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 * “发货联系人”字段更改为“发货部门”； 1.6 发货人地址：发货客户的详细联系地址， 支持国家行政区域自动过滤； 2. 选择发货客户界面：
 * 界面为选择客户信息界面：包括两部分： 客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域： 包括：客户编码、客户名称、联系人、手机、
 * 电话、地址(规范化地址和详细地址)； 2.2. 功能按钮区域： 包括：确定、取消； 3. 选择热门城市界面 界面信息包含人热门城市 4. 选择省份界面
 * 界面信息包含省份 5. 选择城市界面 界面信息保护城市 6. 选择区县界面 界面信息保护区县 1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据
 * 补充步骤 1 录入手机号码 1. 系统自动查询CRM系统中对应手机号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展1a，如果没有，
 * 弹出FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作见扩展1b； 2. 规则-请参见系统规则SR1；
 * 
 * 2 录入电话号码 1. 如果手机号码没有填写， 系统自动查询CRM系统中对应电话号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展2a，如果没
 * ，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展2b； 2. 规则-请参见系统规则SR1；
 * 
 * 3 录入客户名称和客户编码 1. 规则-请参见系统规则SR2、SR3； 4 录入发货联系人（发货部门） 1. 规则-请参见系统规则SR4、SR5、SR8；
 * 5 录入发货人地址 1. 提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段； 2. 规则-请参见系统规则SR6； 1.6.2 扩展 序号
 * 扩展事件 相关数据 备注 1a 当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6、SR7；
 * 
 * 1b 当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 3.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 4. 规则-请参见系统规则SR5 、SR6、SR7；
 * 
 * 2a 当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2. 规则-请参见系统规则SR5、SR6、SR7；
 * 
 * 
 * 2b 当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2. 规则-请参见系统规则SR5、SR6、SR7；
 * 
 * 1.7 业务规则 序号 描述 SR1 1. 发货客户手机号码及固定电话至少提供一个，手机号码只能为数字并且为11位； 2.
 * 固定电话号码只能为数字，且可添加多个；添加多个时， 必须用“，”或“、”或“/”分开；固定电话号码字段也可以录入手机号； 3.
 * 手机、电话为精确查询全公司客户信息 4. 手机、电话带出的客户信息会覆盖原来已带出的客户信息。
 * 若未带出客户信息则当客户ID不为空时清空除手机外的其它已带出的客户信息（即），否则不清空 SR2 1.
 * 若发货客户为公司会员客户，则录入发货客户信息后系统界面显示该发货客户联系人编码； 2.
 * 客户名称精确查询全公司客户信息。当客户名称为带出的客户时（以隐藏的客户ID是否为空做为判断条件），
 * 清空客户时则清空手机、电话、联系人、地址、行政区域、客户编码，否则只清空客户名称、客户编码、客户ID（隐藏）； SR3 1.
 * 若发货客户为会员客户，则录入发货客户信息后系统给予提示； 2. 提未信息为在运单开单界面下方加色放大显示； 3.
 * 通过选择录入的客户名称和客户编码不可修改，但可删除录入； SR4 1. 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 * “发货联系人”字段更改为“发货部门”； 2. 联系人不用带出客户信息 SR5 1. 若为公司内部带货，输入发货部门关键字支持模糊搜索 SR6 1.
 * 客户详细地址必填至乡/镇，且乡镇下一级内容不能为空； 2. 当鼠标点击规范化地址文本框时，显示如下图片， 包含热门城市、省份、城市、县区，选择热门城市，
 * 会直接跳到区县，现在省份后自动跳到城市， 选择城市后自动跳到区县（必须选择完上一级行政区域后，才能选择下一行政区域），
 * 选择完区县后，会把规范化地址显示在规范化文本框内，例如：江苏省-苏州市-相城区 3.
 * 地址可以进行拼音和首写字母进行匹配，例如输入“GZ”会在规范化地址文本框下面显示 ；
 * 匹配时可带出城市、区、县等符合的信息，该设计来自官网，具体可以参考官网 4. 当增值服务中有返单业务时，则发货人地址为必填项；其他情况非必填。 SR7
 * 1. 使用电话号码和手机进行匹配，弹出CRM发货客户选择框进行选择，如果匹配不到，
 * 再使用运单发货历史客户进行匹配，弹窗选择，选择后填充客户编码、客户名称、联系人、地址； 2.
 * 但是对于电话号码匹配，只有当发货人手机、客户名称为空时，才会用电话号码检索并弹窗显示； 3.
 * 使用手机号码、电话号码、客户名称弹出选择框选择记录后覆盖原先记录 4. 修改联系人时，需要清空客户名称 5.
 * 使用手机号码弹出选择框选择记录后覆盖原先记录，查询不到时，清空客户名称 6.
 * 导入发货客户信息后，联系人名称不可修改，为灰色；当营业员进行清空发货客户的客户名称操作时， 联系人名称可修改，为可编辑状态； 7.
 * 点击客户名称查询控件，弹出查询信息:窗口SUC-424-查找会员 8. 如果查询出来的记录只有一条，也需要进行选择 9. 发货客户名称精确查询且查询全公司
 * SR8 1. 发货人省市区默认为始发营业部的省市区
 * 
 * 
 * 录入收货客户SUC业务规则 营业员点击运单开单，进入运单开单界面。 本界面分为两个界面：录入收货客户信息、选择收货客户。 1. 录入收货客户信息：
 * 界面为信息录入界面：包括：手机、电话、发货收货联系人（发货收货部门）、发货收货人地址； 1.1 手机：发货收货人手机号码； 1.2
 * 电话：发货收货人电话号码； 1.3
 * 收货联系人（收货部门）：收货客户的客户姓名，当“运单开单”中的“开单提货方式”为“内部带货自提”时，“收货联系人”字段更改为“收货部门”； 1.4
 * 收货人地址：收货客户的详细联系地址，支持国家行政区域自动过滤； 1.5 客户名称 1.6 客户编码 2. 选择收货客户界面：
 * 界面为选择客户信息界面：包括两部分：客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域：
 * 包括：联系人、手机、电话、地址（规范化地址和详细地址）； 2.2. 功能按钮区域： 包括：确定、取消； 3. 选择热门城市界面 界面信息包含人热门城市
 * 4. 选择省份界面 界面信息包含省份 5. 选择城市界面 界面信息保护城市 6. 选择区县界面 界面信息保护区县
 * 
 * 1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1. 系统自动查询CRM系统中对应手机号码绑定的客户信息，
 * 如果有弹窗，弹窗操作见扩展1a，和如果没有， 弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展1b； 2.
 * 规则-请参见系统规则SR1、SR5、SR6； 2 录入电话号码 1. 如果手机号码没有填写， 系统自动查询CRM系统中对应电话号码绑定的客户信息，
 * 如果有弹窗，弹窗操作见扩展2a，如果没有， 弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展2b； 1.
 * 1、系统自动查询CRM系统中对应电话号码绑定的客户信息 和FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗， 弹窗操作参见扩展2a； 2.
 * 规则-请参见系统规则SR1、SR5 、SR6； 3 录入收货联系人（收货部门） 1. 规则-请参见系统规则SR2、SR3； 4 录入收货人地址 1.
 * 提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段； 2. 地址在系统后台通过GIS系统进行匹配，
 * 如果是禁行区域，地址颜色为红色，如果是进仓区域，地址颜色为黄色 3. 规则-请参见系统规则SR4； 1.6.2 扩展 序号 扩展事件 相关数据 备注 1a
 * 当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 当光标焦点移至录入手机号码时，系统自动调用CRM系统对应发货客户的历史发货记录，
 * 并自动弹窗显示所有该发货客户的历史发货记录；营业员选择一条记录，并确定， 选择的客户信息自动带信收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6； 1. 规则-请参见系统规则SR5；
 * 1b 当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时，
 * 则如果该客户在这3个月有发过货，则弹出历史收货记录自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6； 2a2a
 * 当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 * 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息， 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6； 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2. 规则-请参见系统规则SR6；
 * 
 * 2b 当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史收货记录，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6；
 * 
 * 1.7 业务规则 序号 描述 SR1 1. 收货客户手机号码及固定电话至少提供一个，手机号码只能为数字并且为11位，
 * 固定电话号码只能为数字，且可添加多个； SR2 1. 当“运单开单”中的“开单提货方式”为“内部带货自提”时， “收货联系人”字段更改为“收货部门”；
 * SR3 1. 若为公司内部带货，则收货客户信息中的收货部门名称必须与OA系统中组织架构名称保持一致； SR4 1.
 * 客户详细地址必填至乡/镇，且乡镇下一级内容不能为空；
 * 
 * 2. 当鼠标点击规范化地址文本框时，显示如下图片， 包含热门城市、省份、城市、县区，选择热门城市，会直接跳到区县， 现在省份后自动跳到城市，
 * 选择城市后自动跳到区县（必须选择完上一级行政区域后，才能选择下一行政区域），
 * 选择完区县后，会把规范化地址显示在规范化文本框内，例如：江苏省-苏州市-相城区 3.
 * 地址可以进行拼音和首写字母进行匹配，例如输入“GZ”会在规范化地址文本框下面显示 ；
 * 匹配时可带出城市、区、县等符合的信息，该设计来自官网，具体可以参考官网 4. 当提货方式含“自提”时，收货人地址为非必填项； SR5 1.
 * 使用电话号码和手机进行匹配，如果是唯一匹配一条CRM客户信息时， 填充客户编码、客户名称、联系人、地址，如果有多条需弹出选择框进行选择，
 * 如果查询不到CRM客户信息时，使用FOSS三个月运单历史记录中的收货信息查询，
 * 唯一匹配一条进行填充收货客户信息，多条进行弹窗选择，如果都查询不到，不做其他操作 2.
 * 但是对于电话号码匹配，只有当发货人手机、客户名称为空时，才会用电话号码检索并弹窗显示 3.
 * 使用手机号码、电话号码、客户名称弹出选择框选择记录后覆盖原先记录 4. 修改联系人时，需要清空客户名称，当清空客户名称时，会同时删除客户编码 5.
 * 使用手机号码、电话号码弹出选择框选择记录后覆盖原先记录 6. 点击客户名称查询控件，弹出查询信息:窗口SUC-424-查找会员 7.
 * 身份证号、客户编码、客户名称、联系人编码可以查询到全公司的客户 1. 只有当收货人手机为空，且发货客户信息已录入时，才会检索并弹窗显示； 2.
 * 若未查询到历史记录，则无法提示； 3. 通过选择录入的收货人信息均可修改； 4.
 * 使用号码进行匹配，如果是唯一匹配一条CRM客户信息时，填充客户编码和客户名称，如果有多条不做操作 SR6 1.
 * 通过选择录入收货发货信息，同时带出对应的目的站和提货网点信息； 2. 带出目的站仍然使用GIS进行查询，并以GIS返回为准； 3.
 * 若GIS未查询得出，则以历史开单的目的站和提货网点信息为准；
 * 
 * 
 * 查找会员SUC业务规则 营业员点击运单开单界面中的查询客户，进入查询客户信息界面。 本界面为查询客户信息。
 * 界面主要分为三个部分：查询条件区域、查询结果区域、功能按钮。 1. 查询条件区域： 1.1 会员卡号：支持会员卡号的模糊搜索，并可查询部门全部会员信息；
 * 1.2 发货联系人 1.3 电话 1.4 客户编码 1.5 客户名称 1.6 手机 1.7 发货人地址 1.8 复选框"查询全公司" 2. 查询结果区域：
 * 2.1 客户编码 2.2 客户名称 2.3 联系人编码 2.4 月结审核 2.5 联系人 2.6 手机 2.7 电话 2.8 身份证 2.9 信用额度
 * 2.10 地址 2.11 越发越送审核编号 2.12 生效时间 2.13 失效时间 3. 功能按钮： 3.1 重置： 3.2 查询； 3.3 查询部门会员
 * 1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入查询条件，查询符合条件的客户信息 查询条件信息 1.
 * 系统查询CRM系统中本部门符合条件的客户信息； 2. 规则-请参见系统规则SR1； 2 查询本部门所有的会员客户信息 1. 规则-请参见系统规则SR2；
 * 3 导入选择的客户信息至发/收货客户信息中：双击选中的客户信息， 对应客户信息进入发/收货客户信息中 1. 规则-请参见系统规则SR3； 1.6.2 扩展
 * 序号 扩展事件 相关数据 备注 1a 步骤1中，若未查询到符合条件的客户信息，系统给予提示 1. 提示信息为“未查询到符合条件的客户信息！”； 2b
 * 步骤2号，若本部门无会员，系统给予提示 1. 提示信息为“部门无会员信息！”； 1.7 业务规则 序号 描述 SR1 1. 支持单一和组合查询条件查询；
 * 2. 只有勾选复选框时，按照身份证号、客户编码、 客户名称、联系人编码精确查全公司客户信息且查询条件中包含有身份证号、
 * 客户编码、客户名称、联系人编码其中任意一项时，忽略其它查询条件， 否则为模糊查询本部门客户信息； 3. 当查询到的记录为某客户编码中的其中一条信息时，
 * 显示该编码对应的所有的客户信息； 4. 当客户为越发越送客户时，则显示该客户对应的越发越送审核编号、
 * 生效时间、失效时间；当越发越送审核编号有多个时，则只显示最后一次审核信息； SR2 1. 当条件都为空时，点击查询按钮，查询出本部门会员； SR3 1.
 * 通过查询条件带出的客户信息被填充到相关控件中时， 发货客户联系人为不可修改状态， 若要修改则需要清空全部带出的客户信息；
 * 
 * 
 * 确认公布总价SUC业务规则 1 打开“运单开单”界面。 2 录入发货人信息 参考SUC-492 3 录入收货客户信息 参考SUC-493 4 录入货物信息
 * 参考SUC-494 5 录入运输信息 参考SUC-496 进行前面5操作之后，若是空运， 系统读取空运公布价价格方案（基础资料参考SUC-581）
 * 计算出公布总价，显示在运单开单界面。 若是汽运，系统读取汽运公布价价格方案（基础资料参考SUC-581） 计算出公布总价， 显示在运单开单界面。
 * 参考规则SR1，SR2，SR3 扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 5a 5b 1.7 业务规则 序号 描述 SR1
 * 1）汽运：上门发货汽运运费最低X元一票； （同城、卡航；可配置）； 上门接货汽运运费最低X元一票；（同城、卡航；可配置）；
 * 2）空运：空运运费最低X元一票；（可配置）； SR2 1）汽运：当货物为“接货”时， 系统自动匹配生成公布价“接货价格方案”； 当货物为“非接货”时，
 * 系统自动匹配生成公布价“非接货的价格方案”； 2）空运：系统自动匹配公布价 “空运价格方案”； 空运价格只有上门发货一套价格方案，
 * 如有接货费在其他费用里添加一项接货费。 SR3 1）计费方式分为重量计费、体积计费； 重量、体积计费的运费=每公斤单价与货物实际重量的乘积 或
 * 每方单价与货物实际体积的乘积， 对于一票货物，系统按重量和体积分别计算并取大优先的原则计费给出公布价总运费, 计费方式即为取大的一方；
 * 
 * 运单提交SUC业务规则 客户上门发货确认承运信息后，营业员告知客户运输费用后， 为客户开具运单，打印标签并粘贴至货物的过程。 1.3 用例条件 条件类型
 * 描述 引用系统用例 前置条件 1. 运单已填写完整 DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc
 * DP-FOSS-接送货系统用例-客户上门-根据订单确认承运信息-导入订单-V0.1.doc
 * DP-FOSS-接送货系统用例-客户上门-确认承运信息-查询目的站-V0.1.doc
 * DP-FOSS-接送货系统用例-客户上门-承运计费报价-确认公布总价-V0.1.doc 后置条件 1. 传送运单号、金额、帐号等结算数据到财务子系统 2.
 * 传送货物名称、件数、重量等货物信息到中转子系统，安排运输计划 3. 传送运单号等信息到官网，客户查询运单状态 4.
 * 给收货人发送出发短信，给上门接货客户发送短信 5. 订单信息反馈给给CRM系统，订单处理结果为：已开单。 6、 当运输性质为精准空运时，
 * 提交成功后会自动生成订舱信息流到最终配载部门（即总调）的舱位信息中 1.4 操作用户角色 操作用户 描述 营业员 1. 确认客户承运信息，开单收货，
 * 收取现付金额，且打印标签及运单出发联让客户签字确认。 1.5 界面要求 1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.2.1
 * 集中开单界面 1.5.2.2 营业部开单界面 1.5.2.3 运单确认提交界面 1.5.3 界面描述 1.5.3.1 营业部、集中开单界面
 * 界面共包括7个部分：1、发货客户信息； 2、收货客户信息；3、货物信息； 4、运输信息；5、增值服务信息； 6、计费付款；7、功能按钮。
 * 具体描述参考DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc 1.5.3.2 运单确认提交界面
 * 界面共包括3个部分：1、运单基本信息； 2、功能复选框；3、功能按钮 1、运单基本信息：单号、到付总运费、预付总运费、
 * 代收货款、保险价值、收货人名称、付款方式、提货网点、 提货方式、收货人地址、收货人电话、货物名称、 重量/体积/件数、包装、重量/体积/件数（代打木架）。
 * 2、功能选项：打印运单下拉框有各种版本（可以配置，且可以配置默认模板） （选择之后，点击确定系统自动打印运单，只能选其一，参考规则SR8）、
 * 打印标签（选择之后，点击确定系统自动打印标签）、 提交后新增（选择之后，点击确定系统打开一个新的开单界面）。 3、功能按钮：确定、取消 。 1.6
 * 操作步骤 序号 基本步骤 相关数据 补充步骤 1 营业员“填写”完整的运单信息。 2 点击暂存 1、客户上门提供的货物承运信息。
 * 2、来此crm系统订单的数据。 3、上门接货客户提供的承运信息。 1、用户暂存的运单数据时允许修改 23 点击“提交”按钮提交运单。
 * 1、客户上门提供的货物承运信息。 2、来此crm系统订单的数据。 3、上门接货客户提供的承运信息。 1、点击运单“提交”按钮，弹出“运单确认提交页面”，
 * 页面默认选择上“打印运单（全打）”、“打印标签”、 “确定后新增运单”复选框。 2、提交后，同步数据到中转子系统，、CRM系统、官网系统，
 * 财务子系统。(将单独出来写接口用例，此处将参考这些接口系统用例) 3、系统自动根据出发部门、运输性质、到达部门生成默认唯一走货路径(基础资料)；
 * 参考综合系统基础资料用例。 4、当运输性质为精准空运时，提交成功后会自动生成订舱信息 （包括：预计出发时间、航班时间（早中晚）、重量）
 * 流到最终配载部门（即总调）的舱位信息中。 5、保存使用的价格版本号 56、参考规则SR1、SR12 34 点击运单确认提交页面的“确定”按钮。
 * 1、点击确定后，打开一个新的运单开单界面。 2、系统自动打印运单,选择系统默认的打印模板，
 * 参考DP-FOSS-接送货系统用例-客户上门-确认承运信息-打印运单-V0.1.doc
 * 3、系统自动打印标签，参考DP-FOSS-接送货系统用例-客户上门-确认承运信息-打印标签V0.1.doc 4、调用中转入库接口 参考规则SR13 45
 * 若有签收单返单时，需要打印签收单标签， 打印内容包括：单号、始发部门、到达部门、目的站。 参考SUC-504打印签收单标签(整车)
 * 扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 2a 营业员在弹出代打木架对话框“录入第X件”需要打木架。
 * 若货物需要代打木架，系统自动弹出代打木架对话框。代打木架精确到第几件。 参考规则SR3 2b2a 如运单必填信息未填写完整或填写内容不符合要求
 * （参考数据元素输入限制、长度、是否必填、运单号重复等）， 提交时给予提示。 提示为：“×××未填写整或输入内容不符合要求，请重新输入！”，
 * 且将此文本输入框标记为红色，光标置于此文本框中。 重新填写正确完整后，跳转步骤1。 参考规则SR7 2c2b 若为月结或临时欠款，
 * 如果客户既有应收账款金额加上本次应收金额超过客户最大信用额度， 不能提交开单。 弹出提示：客户既有应收账款金额加上本次应收金额超过客户最大信用额度，
 * 不能提交开单。参考SR9 *2d2c 若为月结或临时欠款，如果客户已有应收账款超过最大账期，不能提交开单。
 * 弹出提示：客户已有应收账款超过最大账期，不能提交开单。参考SR9 2e2d 提交时，当重量体积比不在设置的区间（重量体积比基础资料）中，
 * 弹出提示“请确认录入的重量体积是否准确！”； （该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面；
 * 点击取消，点返回运单录入界面；当X在区间中，无提示； 直接进入确认运单信息界面； 参考规则SR10 3a 步骤3中，可点击“取消”按钮，取消提交运单。
 * 取消提交运单，返回到系统运单开单界面，界面信息可编辑， 修改信息后，可再次进行提交，跳到步骤2。 3b 步骤3中，也可取消选择默认选择的复选框。
 * 1、如取消选择“打印运单”，确定之后，系统不进行自动打印运单， 需点击开单界面上的“打印运单”按钮，打印运单出发联。
 * 2、如取消选择“确定后新增运单”，确定之后， 系统返回到填写完整的运单开单界面，但是运单界面成灰色不可编辑， 需点击开单界面上的“新增”按钮，新增运单。
 * 3、如取消选择“打印标签”，确定之后， 系统不进行自动打印标签，需点击开单界面上的“打印标签”按钮，打印运单标签。 1.7 业务规则 序号 描述 SR1
 * 1、若为上门接货，开单提交生成后， 系统短信通知发货人及收货人。 短信模板可在系统中进行设置。 给发货人或收货人发送短信时， 若无手机号码则不发送。
 * 发送短信内容中包含“货物的件数”。 注：“货物的件数”为货物包装之前的件数。 系统自动给发货人、收货人发送货物出发短信（短信模板内容可配置）；
 * 否则只需要给收货人发短信；若收发货人无手机号码则不发送。 给收货人的短信内容：您好！这里是德邦物流，
 * （发货人姓名***）从（出发城市***）给您发来货物， 单号为（****）的（***货物的件数）件货，即日出发。
 * 目的地（*****客户的收获地址）。附：德邦物流营业部的电话、地址、营业部名称。 给发货人的短信内容：您好！这里是德邦物流，
 * 您从（出发城市***）给（收货人姓名***）托运的货物， 单号为（****）的（***货物的件数）件货，即日出发。
 * 目的地（*****客户的收获地址）。附：德邦物流营业部的电话、地址、营业部名称。 SR2 运单现付金额不为0，则在出发部门生成现金收款单；
 * 若到付金额不为0，则在到达部门生成应收单若付款方式为“现付”， “银行卡”，则在出发部门生成现金收款单； 若付款方式为“到付”在到达部门生成应收单。
 * 若付款方式为“临时欠款”，“网上支付”， “月结”在出发部门生成应收单；若运单包含“代收货款”， 则在出发部门生成应付单，到达部门生成应收单；
 * 若运单包含“装卸费”，则在出发部门生成应付单。 SR3 开单代打木架外场默认为第一外场， 如果第一外场不支持打木架，则营业员自己判定选择的代打木架外场。
 * 开单代打木架外场默认为开单走货路径中第一个可代打木架的外场，不可修改。 1）若货物包装中含有“木”字样，且走货路径上有代打木架外场，
 * 系统提示：是否需要代打木架？； 2）选择代打木架后，若默认的代打木架外场非走货路径上第一外场时， 系统给予提醒，便于营业员与客户衡量是否继续代打木架；
 * 3）若货物包装中含有“木”字样，但走货路径上没有可代打木架的外场， 则不能开代打木架，系统提示：走货路径上没有可代打木架的外场，不能代打木架。 SR4
 * 当运输性质为 “精准卡航”及“精准城运”时， 系统自动计算预计出发时间与预计提货/派送时间。 ①预计出发时间：
 * 由预计出发日期和准点出发时点（取自基础资料）组成， 形如【2012-04-09 12:00:00】。 若开单当前时点在准点出发时点前，
 * 则预计出发日期=开单日期；否则，预计出发日期=开单日期+1； ② 预计提货时间（开单提货方式为自提）：
 * 由预计提货日期和到达营业部承诺时点（取自基础资料）组成， 形如【2012-04-09 12:00:00】。若为当天出发，
 * 则预计提货日期=预计出发日期+到达营业部承诺天数； 否则预计提货时间=预计出发日期+到达营业部承诺天数-1。 ③ 预计派送时间（开单提货方式为送货）：
 * 由预计派送日期和派送承诺时点（取自基础资料）组成， 形如【2012-04-09 12:00:00】。若为当天出发，
 * 预计派送时间=预计出发日期+到达营业部承诺天数+派送承诺需加天数； 否则预计派送时间=预计出发日期+派送承诺需加天数-1。 SR5
 * 如果开单信息来自集中或非集中上门接货： 1、司机有PDA PDA开单后：则点击“PDA补录”走PDA补录流程引用系统用例SUC-491-补录运单。
 * 2、司机无PDA 则新增运单，进入开单界面走提交运单流程。 SR6 “运单确认提交界面”上的复选框可以单选，可以多选。 SR7
 * 提交时系统校验规则参考如下用例规则： SUC-492录入发货客户信息 SUC-493录入收货客户信息 SUC-494录入货物信息
 * SUC-495录入增值服务信息 SUC-496录入运输信息 SUC-311导入订单 SUC-397 确认公布总价 SUC-126 查询目的站 SR8
 * 运单确认提交页面，打印运运单下拉框只能选其一， 默认为运单模板配置基础资料中配置默认的模板， 如果不需要打印，不勾选即可。 SR9
 * 若为月结或临时欠款，则系统需要对客户应收账款日期及信用额度进行判断： 1）欠款天数 以客户为基本单位，开单/派送时付款方式为“临时欠款”，
 * 最长一笔欠款时间超过30天，该客户将不能继续开单为“临时欠款”， 假如客户的临时欠款未还款金额超过客户自己的信用额度，
 * 也不能继续开单；开单付款方式为“月结”，最长一笔欠款时间超过70天， 该客户将不能继续开单为“月结”；（始发应收账款从开单之日起计算，
 * 到付应收账款从第一次派送出库之日起计算，以更改方式更改为“临时欠款”或“月结”的， 从开单之日开始计算） 2）欠款额度
 * 临时欠款：以部门为单位，根据收入等级（前三个月最高收入金额） 设置该部门每月临时欠款最高额度（余额），详见下表； 当部门临时欠款未还款金额大于该金额时，
 * 将无法继续开单未“临时欠款”； 收入区间 项目 10万以下 3万 10-15万 5万 15-20万 7万 20-30万 10万 30-50万 15万
 * 50-100万 20万 100万以上 40万 月结：以客户为单位，连续2个月发货金额在3000元以上可申请月结， 月结额度不得高于近期最高走货金额的2倍，
 * 若客户连续两月发货量低于2000元，取消月结权限。 SR10 1. 系统设置货物重量体积比区间值（该值由基础资料配置），
 * 在运单提交时，系统自动对重量体积比进行校验：即重量体积比X=重量/体积； 当X不在设置的区间中，弹出提示“请确认录入的重量体积是否准确！”；
 * （该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面； 点击取消，点返回运单录入界面；当X在区间中，无提示； 直接进入确认运单信息界面；
 * SR11 1）用户可以随时录入运单信息随时暂存运单， 但必须录入打印标签所需的关键字段才可暂存运单。 目前暂存运单打印标签所需运单信息包含收货部门、
 * 运单号、目的站、提货网点、运输性质、提货方式、包装、件数。 （注：当走货路由经过特定的城市时还需要录入货物类型） 2）系统对录入的运单信息合法性进行校验；
 * 3）运单暂存后不生成正式运单信息、库存信息及财务单据； 4）用户可对已暂存的运单进行修改、暂存、提交（生成正式运单）；
 * 5）若暂存运单，系统锁定运单号及订单信息； 6）暂存的运单不可打印运单出发联； SR12 若PDA开单已打印标签，
 * 在提交运单后弹出的确认界面中打印标签默认不勾选， 若需打印标签则手动勾选"打印标签"复选框。 SR13 1）、如果开单组织是营业部门， 那么开单提交时，
 * 检验是否是驻地部门，如果是驻地部门， 入库对应驻地部门外场， 如果是营业部，入库对应营业部 2）、如果开单组织是开单组，那么开单提交时，
 * 校验是否已经打印标签，如果没有，入库配载部门；
 * 
 * 
 * 录入运输信息（整车）SUC业务规则 营业员点击整车运单开单，进入整车运单开单界面。 本界面为录入整车运输信息。
 * 界面信息包括：收货部门、单号、目的站、提货网点、 对外备注、对内备注、储运注意事项、配载部门、 最终配载部门、到达类型 1. 收货部门：揽货部门； 2.
 * 单号：运单单号； 3. 提货网点：收货客户可以领取货物的部门； 4. 对外备注：客户可以看到的备注信息，包括：空、
 * 保丢不保损、“不承保发霉、变质、虫蛀虫咬之损失”、 “ 不承保刮花、变形、撞凹之损失”、不可重压、易潮、 不可倒置、客户指定提货网点； 5.
 * 对内备注：仅限公司内部人员看到的备注信息； 6. 到达类型：到达客户处还是到达营业部 7. 储运注意事项：对外备注和对内备注信息的叠加； 8.
 * 配载部门:开单收货部门出发货配载专线； 1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 录入收货部门 1. 规则-请参见系统规则SR1； 2
 * 录入单号 系统校验单号的合法性 1. 规则-请参见系统规则SR2； 3 录入提货网点 1. 规则-请参见系统规则SR3； 4 录入对外备注 1.
 * 规则-请参见系统规则SR4； 5 录入对内备注 6 到达类型 1. 规则-请参见系统规则SR5； 6 生成储运注意事项 1. 规则-请参见系统规则SR6；
 * 7 生成配载部门 1. 规则-请参见系统规则SR7； 8 生成最终配载部门 1. 规则-请参见系统规则SR8； 1.7 业务规则 序号 描述 SR1 1.
 * 收货部门默认为操作者所在的部门，不可修改； SR2 1. 单号最大长度为8位，如果超过提示“单号大于8位” 2.
 * 单号不能少于8位，“如果少于8位“单号长度少于7位” 3. 单号与最近开单单号前六位如果不等，那么提示“前后两票单号相差过大，
 * 请检查所输单号是否为本部门所属单号！” SR3 1. 提货网点可由收货客户地址的城市生成，也可手工选择； 2.
 * 系统自动过滤只显示符合录入文本条件的网点信息； SR4 参考SUC-496 录入运输信息 对对外备注的处理 SR5 1. 如果在
 * “是否经过营业部”打勾，付款方式可以选择到付项， 代收货款框可以修改； 2. 如果在“是否经过营业部”不打勾，付款方式中的到付项自动移除，
 * 代收货款清0且不可修改； SR6 1. 储运注意事项=对外备注&对内备注，各字段以“；”分开； 2. 对外备注永远在储运注意事项的最前面； SR7 1.
 * 通过出发部门和提货网点系统自动匹配始发配载部门基础资料；
 * 
 * 
 * 录入收货客户信息（整车）SUC业务规则 营业员点击运单开单，进入运单开单界面。 本界面分为两个界面：录入收货客户信息、选择收货客户。 1.
 * 录入收货客户信息： 界面为信息录入界面：包括：手机、电话、发货收货联系人（发货收货部门）、 发货收货人地址； 1.1 手机：发货收货人手机号码； 1.2
 * 电话：发货收货人电话号码； 1.3 收货联系人（收货部门）：收货客户的客户姓名， 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 * “收货联系人”字段更改为“收货部门”； 1.4 收货人地址：收货客户的详细联系地址， 支持国家行政区域自动过滤； 1.5 客户名称 1.6 客户编码 2.
 * 选择收货客户界面： 界面为选择客户信息界面：包括两部分： 客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域：
 * 包括：联系人、手机、电话、地址（规范化地址和详细地址）； 2.2. 功能按钮区域： 包括：确定、取消； 3. 选择热门城市界面 界面信息包含人热门城市
 * 4. 选择省份界面 界面信息包含省份 5. 选择城市界面 界面信息保护城市 6. 选择区县界面 界面信息保护区县 1.6 操作步骤 1.6.1 录入 序号
 * 基本步骤 相关数据 补充步骤 1 录入手机号码 1. 系统自动查询CRM系统中对应手机号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展1a，
 * 如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展1b； 2.
 * 规则-请参见SUC-493-录入收货客户信息SR1、SR5、SR6； 2 录入电话号码 1. 如果手机号码没有填写，
 * 系统自动查询CRM系统中对应电话号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展2a， 如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 * 并弹窗，弹窗操作见扩展2b； 2. 1、规则-请参见SUC-493-录入收货客户信息SR1、SR5 、SR6； 3 录入收货联系人 1. 4
 * 录入收货人地址 1. 提供下拉框选择输入， 系统自动过滤输入的行政区下一级行政级的字段； 2. 地址在系统后台通过GIS系统进行匹配，
 * 如果是禁行区域，地址颜色为红色，如果是进仓区域，地址颜色为黄色 3. 规则-请参见系统规则SR4； 1.6.2 扩展 序号 扩展事件 相关数据 备注 1a
 * 当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5 、SR6； 1b
 * 当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史收货记录，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5 、SR6；
 * 
 * 2a 当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5 、SR6； 3. 2b
 * 当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史收货记录，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5 、SR6； 4.
 * 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1. 系统自动查询CRM系统中对应手机号码绑定的客户信息和
 * FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗， 弹窗操作见扩展1b； 2. 规则-请参见系统规则SR1； 2 录入电话号码 1.
 * 系统自动查询CRM系统中对应电话号码绑定的客户信息和FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作参见扩展2a； 2.
 * 规则-请参见系统规则SR1； 3 录入客户名称 1. 规则-请参见系统规则SR2 4 录入收货联系人 5 录入收货人地址 1. 提供下拉框选择输入，
 * 系统自动过滤输入的行政区下一级行政级的字段； 2. 地址在系统后台通过GIS系统进行匹配， 如果是禁行区域，地址颜色为红色，
 * 如果是进仓区域，地址颜色为黄色; 3. 规则-请参见系统规则SR3； 1.6.2 扩展 序号 扩展事件 相关数据 备注 1a
 * 当光标焦点移至录入手机号码时， 系统自动调用CRM系统对应发货客户的历史发货记录， 并自动弹窗显示所有该发货客户的历史发货记录；
 * 营业员选择一条记录，并确定，选择的客户信息自动带信收货客户信息中； 客户信息 1. 规则-请参见系统规则SR4； 1b
 * 当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息， 营业员选择录入一条记录，并确定；
 * 选择的客户信息自动带入收货客户信息中； 客户信息 1. 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2a
 * 当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 1.7 业务规则 序号 描述 SR1 1. 收货客户手机号码及固定电话至少提供一个，
 * 手机号码只能为数字并且为11位，固定电话号码只能为数字， 且可添加多个； SR2 1. 若收货客户为公司会员客户，
 * 则录入收货客户信息后系统界面显示该收货客户联系人编码； SR3 1. 客户详细地址必填至乡/镇， 且乡镇下一级内容不能为空； SR4 1.
 * 只有当收货人手机为空， 且发货客户信息已录入时，才会检索并弹窗显示； 2. 若未查询到历史记录，则无法提示； 3. 通过选择录入的收货人信息均可修改；
 * 1.
 * 
 * 录入货物信息（整车）SUC业务规则
 * 
 * 营业员点击运单开单，进入运单开单界面。 本界面为录入货物信息。 界面主要分为一个部分：录入货物信息。 1. 录入货物信息：
 * 录入信息包括：货物名称、总件数、总重量、货物尺寸、总体积、货物包装 1.1 货物名称：货物的名称； 1.2 总件数：收货时货物的总件数； 1.3
 * 总重量：收货时货物的总重量； 1.4 总体积：收货时货物的总体积； 1.5 货物包装：货物的包装数； 1.6 操作步骤 1.6.1 录入货物信息 序号
 * 基本步骤 相关数据 补充步骤 1 修改货物名称 1. 系统自动匹配违禁品，生成规则； 2. 规则-请参见系统规则SR1； 1.7 业务规则 序号 描述
 * SR1 1. 若货物为违禁品，则系统自动提示“货物为违禁品， 不可开单！”； 2. 违禁品、拒收品、具体类型可在系统中进行配置； SR2
 * 新增一条业务规则：在提交运单之前， 若相邻两次输入的单号（两次单号分别为A与B且单号输入合法）差别较大时，
 * 系统给予用户友好提示避免录错单号，但不限制单号的输入。具体规则为： 1）若前后两次输入的单号位数相同，当|A-B|≥100时系统给予提示；
 * 2）若前后两次输入的单号位数不同，系统给予提示； 3）提示信息为：前后两票单号相差过大， 请检查所输单号是否是本部门所属单号！
 * 
 * 
 * 营业员点击运单开单，进入运单开单界面。 本用例分为两个界面：录入发货客户信息、 选择发货客户； 1. 录入发货客户信息：
 * 界面为信息录入界面：包括：手机、电话、客户名称、 客户编码、发货联系人（发货部门）、发货人地址； 1.1 手机：发货人手机号码； 1.2
 * 电话：发货人电话号码； 1.3 客户名称：发货客户公司或单位名称， 可支持搜索查询； 1.4 客户编码：我司给客户的客户号； 1.5
 * 发货联系人（发货部门）：发货客户的客户姓名， 当“运单开单”中的“开单提货方式”为“内部带货自提”时， “发货联系人”字段更改为“发货部门”； 1.6
 * 发货人地址：发货客户的详细联系地址， 支持国家行政区域自动过滤； 2. 选择发货客户界面： 界面为选择客户信息界面：包括两部分：
 * 客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域： 包括：客户编码、客户名称、联系人、手机、 电话、地址(规范化地址和详细地址)； 2.2.
 * 功能按钮区域： 包括：确定、取消； 3. 选择热门城市界面 界面信息包含人热门城市 4. 选择省份界面 界面信息包含省份 5. 选择城市界面
 * 界面信息保护城市 6. 选择区县界面 界面信息保护区县 1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1.
 * 系统自动查询CRM系统中对应手机号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展1a， 如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 * 并弹窗，弹窗操作见扩展1b； 2. 规则-请参见系统规则SUC-492-录入发货客户信息SR1； 2 录入电话号码 2.
 * 如果手机号码没有填写，系统自动查询CRM系统中对应电话号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展2a，
 * 如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展2b； 3.
 * 规则-请参见系统规则SUC-492-录入发货客户信息SR1； 3 录入客户名称和客户编码 1.
 * 规则-请参见系统规则SUC-492-录入发货客户信息SR2、SR3； 4 录入发货联系人 5 录入发货人地址 1. 提供下拉框选择输入，
 * 系统自动过滤输入的行政区下一级行政级的字段； 2. 规则-请参见系统规则SUC-492-录入发货客户信息SR6； 1.6.2 扩展 序号 扩展事件
 * 相关数据 备注 1a 当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SUC-492-录入发货客户信息SR5 、SR6、SR7；
 * 1b 当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，营业员选择录入一条记录，
 * 并确定；选择的客户信息自动带入发货客户信息中； 客户信息 3. 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 4.
 * 规则-请参见系统规则SUC-492-录入发货客户信息SR5 、SR6、SR7； 2a 当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 * 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息， 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 * 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2. 规则-请参见系统规则SUC-492-录入发货客户信息SR5、SR6、SR7；
 * 2b 当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，营业员选择录入一条记录，
 * 并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1. 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2.
 * 规则-请参见系统规则SUC-492-录入发货客户信息SR5、SR6、SR7； 1 录入手机号码 1. 系统自动查询CRM系统中对应手机号码绑定的客户信息和
 * FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作见扩展1a； 1. 规则-请参见系统规则SR1； 2 录入电话号码 1.
 * 系统自动查询CRM系统中对应电话号码绑定的客户信息 和FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作参见扩展2a； 1.
 * 规则-请参见系统规则SR1； 3 录入客户名称和客户编码 1. 规则-请参见系统规则SR2、SR3； 4 录入发货联系人 5 录入发货人地址 1.
 * 提供下拉框选择输入， 系统自动过滤输入的行政区下一级行政级的字段； 2. 规则-请参见系统规则SR4； 1.6.2 扩展 序号 扩展事件 相关数据 备注
 * 1a 当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1. 规则-请参见系统规则SR5； 2a
 * 当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 * 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1. 规则-请参见系统规则SR6； 1.7 业务规则 序号 描述 SR1
 * 1.规则参考SUC-492-录入发货客户信息SR1 SR2 1. 规则参考SUC-492-录入发货客户信息SR2 SR3
 * 1.规则参考SUC-492-录入发货客户信息SR3 SR4 1. 规则参考SUC-492-录入发货客户信息SR6 SR5 1.
 * 规则参考SUC-492-录入发货客户信息SR7 SR6 1. 规则参考SUC-492-录入发货客户信息SR8
 * 
 * 
 * 确认整车总价SUC业务规则
 * 
 * 营业员点击整车开单，进入整车开单界面。 本界面为录入整车收银界面。 1. 约车报价：营业员约整车后，请车员受理的价格； 2.
 * 开单报价：营业员根据请车员的受理价格，给客户的实际运费价格； 3. 增值服务费用 4. 总运费：本次承运的客户应付金额； 5.
 * 开单付款方式：客户的付款方式，包括：现金、银行卡、月结、临时欠款、到付； 6. 预付金额 7. 到付金额； 1.6 操作步骤 序号 基本步骤 相关数据
 * 补充步骤 1 录入开单报价 1. 规则-请参见系统规则SR1； 2 查看约车报价、增值服务费用入总运费 1. 规则-请参见系统规则SR2、SR3； 3
 * 选择开单付款方式 1. 规则-请参见系统规则SR4、SR5； 4 预付金额 1. 规则-请参见系统规则SR4、SR5； 5 到付金额 1.
 * 规则-请参见系统规则SR4、SR5； 1.7 业务规则 序号 描述 SR1 1. 开单报价默认等于约车报价； 2.
 * 修改的开单报价只能为约车报价的某个范围区间中，该范围区间可由公司统一配置； SR2 1. 整车导入约车开单时，约车报价为请车员受理的约车价格，不可修改；
 * SR3 1. 总运费=开单报价+增值服务费； 2. 增值服务费=保价费+代收手续费+预付运费保密服务费+返单费+包装费； SR4 1.
 * 整车的付款方式包含现金、银行卡、月结、临时欠款、到付； 2. 支持发货客户的多种付款方式叠加，但月结和临时欠款不能同时存在； 2.
 * 开单只能选择一种付款方式，不可选择两种或以上； 3. 月结客户可以开月结； 4. 若客户非月结，则自运过滤月结的付款方式； 5.
 * 付款方式为到付或者临时欠款时，不能选择预付运费保密； 6. 若整车开单选择直接到达客户处，则不能办理到付； 7.
 * 有信用额度且额度大于等于总运费的发货客户，才可以选择临时欠款； SR5 1. 预付金额必须大于0才能选择预付运费保密； 2.
 * 当付款方式为现付时，预付金额必须大于0；否则， 提示信息“付款方式非到付，预付金额不能小于等于0”； 3. 当付款方式为到付时，预付金额不能大于0；否则，
 * 提示信息“付款方式为【到付】，预付金额不能大于0”； 4. 月结客户的信用金额直接限制该客户的当月发货金额，
 * 当月发货金额超过信用额度，当月将无法再开单月结， 提示“该客户的剩余可用信息额度不足，不能开月结”； 5. 临时欠款时，需要客户中的信用额度超过运费，
 * 否则，提示“该客户的剩余可用信息额度不足，不能开临时欠款”； 6. 当付款方式为临时欠款等收款放货时， 现付到付均为零，不可修改； SR6 1.
 * 约车报价、开单报价、增值服务费用、总运费、预付金额、 到付金额均为整数，按照四舍五入原则；
 * 
 * 
 * 导入整车约车编号SUC业务规则
 * 
 * 营业员通过 界面标题： 约车信息 约车编号：受理后的约车编码 1. 录入发货客户信息： 界面为信息录入界面：包括：手机、电话、客户名称、
 * 客户编码、发货联系人、发货人地址； 1.1 手机：发货人手机号码； 1.2 电话：发货人电话号码； 1.3
 * 客户名称：发货客户公司或单位名称，可支持搜索查询； 1.4 客户编码：我司给客户的客户号； 1.5 发货联系人：发货客户的客户姓名； 2.
 * 录入收货客户信息： 界面为信息录入界面：包括：手机、电话、发货联系人、发货人地址； 1.6 手机：发货人手机号码； 1.7 电话：发货人电话号码； 1.8
 * 收货联系人：收货客户的客户姓名 1.9 收货人地址：收货客户的详细联系地址，支持国家行政区域自动过滤； 3. 录入货物信息：
 * 录入信息包括：货物名称、总件数、总重量、货物尺寸、总体积、货物包装 1.1 货物名称：货物的名称； 1.2 总件数：收货时货物的总件数； 1.3
 * 总重量：收货时货物的总重量； 1.4 总体积：收货时货物的总体积； 1.5 货物包装：货物的包装数； 4.界面标题：计费付款
 * 录入信息包括：约车报价、总运费、增值服务费、 开单付款方式、预付金额、到付金额 1.1 约车报价：整车约车费用 1.2 总运费：运费总合 1.3
 * 增值服务费：增值服务费总合 1.4 开单付款方式：开发付款的方式 1.5 预付金额：现付金额 1.6 到付金额：到付金额 1.6 操作步骤 1.6.1
 * 导入整车约车编号 序号 基本步骤 相关数据 补充步骤 1 用户输入约车编号 2 用户点击“确定”按钮 约车信息 系统自动带出相应的约车信息，
 * 约车编号不可编辑，规则参考SR1 扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 1.7 业务规则 序号 描述 SR1
 * 1、校验是否有此约车编号，如果没有，提示无此约车编号， 如果有，但是没有受理，提示“约车编号未受理”，如果受理拒绝，
 * 提示“约车失败”并提示失败原因，如果单号已经导入过，不能重复导入， 提示“约车编号已经导入过”，如果不是本部门的整车约车编号，
 * 录入后提示“不能导入其他部门整车约车编号” 2、根据约车信息填充界面，请车费用填充进约车报价中。
 * 
 * 
 * 
 * 运单提交（离线）SUC业务规则
 * 
 * 1.1 相关业务用例 BUC_FOSS_5.20.30_550 营业部离线开单 1.2 用例描述 营业员通过本用例录入提交。 1.3 用例条件 条件类型
 * 描述 引用系统用例 前置条件 1. 运单在线登录成功 2. 运单已填写完整 SUC-441录入收货客户信息(离线) SUC-442录入发货客户信息(离线)
 * SUC-443录入货物信息(离线) SUC-444录入增值服务信息(离线) SUC-445录入运输信息(离线) SUC-412运单收银(离线)
 * SUC-372暂存存运单(离线) SUC-362在线登录 后置条件 1. 传送运单号、金额、帐号等结算数据到财务子系统 2.
 * 传送货物名称、件数、重量等货物信息到中转子系统，安排运输计划 3. 传送运单号等信息到官网，客户查询运单状态 4.
 * 给收货人发送出发短信，给上门接货客户发送短信 5. 订单信息反馈给CRM系统，订单处理结果为：离线已开单。 6、 当运输性质为精准空运时，
 * 提交成功后会自动生成订舱信息流到最终配载部门（即总调）的舱位信息中 1.4 操作用户角色 操作用户 描述 营业员 通过运单开单界面，提交离线运单信息
 * 1.5 界面要求 1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.2.1 运单离线开单界面 1.5.2.2 运单确认提交界面 1.5.3
 * 界面描述 1.5.3.1 运单开单界面 界面共包括7个部分： 1、发货客户信息； 2、收货客户信息； 3、货物信息； 4、运输信息； 5、增值服务信息；
 * 6、计费付款； 7、功能按钮。 具体描述参考DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 * DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc 运单确认提交界面 界面共包括3个部分： 1、离线运单基本信息；
 * 2、在线基本信息 3、功能复选框； 4、功能按钮 1、 运单基本信息：单号、到付总运费、预付总运费、 代收货款、保险价值、收货人名称、付款方式、
 * 提货网点、提货方式、收货人地址、收货人电话、 货物名称、重量/体积/件数、包装 。 2、 运单基本信息：单号、到付总运费、
 * 预付总运费、代收货款、保险价值、收货人名称、 付款方式、提货网点、提货方式、收货人地址、 收货人电话、货物名称、重量/体积/件数、包装
 * 3、功能复选框：打印运单（选择之后，点击确定系统自动打印运单）、 打印标签（选择之后，点击确定系统自动打印标签）、
 * 下一条离线运单信息（选择后提交运单成功后填充下一条）。 4、功能按钮：确定、取消 。 1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1
 * 营业员在运单查询界面，查询出离线开单未提交的运单 2 点击“修改” 收货客户信息、发货客户信息、货物信息、
 * 增值服务信息、计费付款信息、运输信息、代打木架信息 进入开单界面 1、自动填充发货客户信息 参考规则SR1、 SR2 2、自动填充发货收货客户信息
 * 参考规则SR1 、SR2 3、自动填充货物信息 参考规则SR3，SR7 4、自动填充运输信息 参考规则SR4，SR7 5、自动填充增值服务信息
 * 参考规则SR5，SR7 6、自动填充计费付款信息 参考规则SR6，SR7 填充完之后， 如果有代打木架，弹出代打木架信息录入界面 2
 * 点击“提交”按钮提交运单。 1、客户上门提供的货物承运信息。 2、上门接货客户提供的承运信息。
 * 1.提交运单，根据系统规则SUC-439提交运单系统用例进行提交 2. 调用中转接口，生成库存信息 3.系统自动弹出，运单确认提交界面 1.7 业务规则
 * 序号 描述 SR1 1.根据离线录入的发货客户信息， 使用发货联系人手机号码到远程服务器进行匹配客户信息， 如果没有，根据离线填写的发货客户信息，
 * 填充发货联系人手机号、发货联系人、发货人地址、发货人电话号码， 如果有，根据查询出来到客户信息，显示会员编码和客户名称，
 * 并根据离线填写的客户信息，填充发货联系人手机号、发货联系人、 发货人地址、发货人电话号码。填充时不需要进行联动和校验
 * 1.填充发货信息和发货信息时，根据发货客户手机到CRM进行查询， 无论查询出有多少条阻塞式弹出CRM查询框让用户进行选择，
 * 当发货客户手机为空时，根据电话号码到CRM进行查询， 无论查询出有多少条阻塞式弹出CRM查询框让用户进行选择，
 * 当用户选择时进行填充，如果客户选择取消时，不填充 2.如果修改，修改规则参考系统用例SUC-492录入发货客户信息 SR2
 * 11.如果修改，修改规则参考系统用例SUC-492录入发货客户信息和SUC-493录入收货客户信息 .根据离线录入的收货客户信息，
 * 使用收货联系人手机号码到远程服务器进行匹配客户信息， 如果没有，根据离线填写的收货客户信息，
 * 填充发货联系人手机号、发货联系人、发货人地址、发货人电话号码， 如果有，根据查询出来到客户信息，显示会员编码和客户名称，
 * 并根据离线填写的收货客户信息，填充发货联系人手机号、发货联系人、 发货人地址、发货人电话号码。填充时不需要进行联动和校验
 * 2.如果修改，修改规则参考系统用例SUC-493录入收货客户信息 SR3 1、 把货物信息填充到界面中，在填充过程中， 不需要进行联动和校验 2、1、
 * 如果修改、修改规则参考系统用例SUC-494 录入货物信息 SR4 1、把运输信息到界面中，在填充过程中， 不需要进行联动和校验
 * 2、如果修改，修改规则参考系统用例SUC-496录入运输信息 SR5 1、把增值服务信息填充到界面中，在填充过程中，不需要进行联动和校验
 * 21、如果修改，修改规则参考系统用例SUC-494录入增值服务信息 SR6
 * 1.如果发货客户有优惠协议，那么根据SUC-486-运单收银系统用例规则进行重新计价 21.如果修改，修改规则参考系统用例SUC-408 运单收银 SR7
 * 1、在填充过程中涉及到需要用基础资料、产品价格和客户资质的校验、 计算、联动，都用最新信息进行校验、计算、联动，
 * 如果离线保存时已经有计算或联动的值，需要进行覆盖， 需要最新计算和联动的值。
 * 
 * 运单收银（离线）SUC业务规则
 * 
 * 1.1 相关业务用例 BUC_FOSS_5.20.30_550 (营业部离线开单) 1.2 用例描述
 * 当网络故障或服务器原因导致营业部不能正常开单时，客户上门发货为汽运或空运，营业员确认承运信息之后，通过离线系统计算货物总付款金额。 1.3 用例条件
 * 条件类型 描述 引用系统用例 前置条件 1. 确认承运信息已录入完毕； 后置条件 1. 录入收入； 1.4 操作用户角色 操作用户 描述 营业员
 * 可查询、确认发货/收货客户应付款金额，及客户的付款方式 开单员 可查询、确认发货/收货客户应付款金额，及客户的付款方式 1.5 界面要求 1.5.1
 * 表现方式 Web页面 1.5.2 界面原型 1.5.3 界面描述 营业员点击离线运单开单，进入离线运单开单界面。 本界面标题：计费付款。 1.
 * 计费类型：包括重量计费、体积计费，默认显示重量计费； 2. 计费费率， 3. 公布价运费 4. 增值服务费用 5. 总运费 6.
 * 开单付款方式：包括现金、到付； 7. 预付金额； 8. 到付金额； 9. 计费重量。 1.6 操作步骤 1.6.1 运单收银 序号 基本步骤 相关数据
 * 补充步骤 1 查看计费类型和计费费率 1. 规则-请参见系统规则SR1、SR2； 2 查看公布价运费和增值服务费及总运费 1.
 * 规则-请参见系统规则SR3； 3 录入付款方式 1. 规则-请参见系统规则SR4； 4 确认和录入预付金额和到付金额 1. 规则-请参见系统规则SR5；
 * 5 查看计费类型和计费费率 1. 规则-请参见系统规则SR1、SR2； 扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 5a 5b 1.7
 * 业务规则 序号 描述 SR1 1. 计费类型分为重量计费、体积计费， 由系统自动生成，不可修改； 默认重量计费； 2.
 * 对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费； 若按重量计费运费较按体积计费运费较高，
 * 则计费类型为重量计费；若按体积计费运费较按重量计费运费较高， 则计费类型为体积计费； 3. 运输类型为汽运时,计费重量为空，不可修改； 2.4.
 * 运输类型为空运时计费重量应为重量和体积*1000000/6000进行对比，取大； SR2 1. 费率为对应计费类型、目的站、提货网点及运输类型的走货单价；
 * 2. 目的站、提货网点及运输类型确认后， 即可自动显示对应计费类型的费率；（来自本地价格基础资料） 3.
 * 费率可以保留到小数点后2位；运费、预付金额、到付金额为整数， 按照四舍五入的原则； SR3 1.
 * 公布价运费（即重量、体积计费的运费）=每公斤单价/每方价格与货物实际重量/体积的乘积， 对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费；
 * （来自价格基础资料，下载并且使用当前启用的最新的价格版本， 参考SUC-547下载基础资料PC-SR1） 2.
 * 增值服务费=送货费+包装费+保价费+代收手续费+其他费用和； 3. 总运费费用=公布价运费+增值服务费+装卸费 =预付金额+到付金额； 3.4.
 * 公布价运费=纯运费+装卸费 SR4 1. 付款方式只有：到付，现付 SR5 1. 当付款方式为现付时，预付金额必须大于0；否则，
 * 提示信息“付款方式为现付，预付金额必须大于0”； 2. 当付款方式为到付时，到付金额必须大于0；否则，
 * 提示信息“付款方式为【到付】，到付金额必须大于0”； 3. 发货人和收货人都付款，付款方式为现付; 发货人跟收货人都付款，付款方式选择到付。 4.3.
 * 支持发货人、收货人付款方式的组合， 例如付款方式为到付500：那么预付金额可以未300， 到付金额为200，总和等于500 ，那么预付300，
 * 可以选择现金100元，临时欠款100，银行卡刷卡100元。 SR6 参考suc-403生成运单SR1: 系统自动检测，
 * 如果客户端X天还没有更新，不能打开离线运单界面， 并提示“请在间隔X天内，在线登录系统
 * 
 * 
 * 确认公布总价（离线）SUC业务规则 1.1 相关业务用例 BUC_FOSS_5.20.30_550 (营业部离线开单) 1.2 用例描述
 * 当网络故障或服务器原因导致营业部不能正常开单时， 客户上门发货为汽运或空运，营业员确认承运信息之后，
 * 通过离线系统计算出本地公布价与计费重量或计费体积乘积的运费。 1.3 用例条件 条件类型 描述 引用系统用例 前置条件
 * 离线系统已经更新最新且已启用的价格版本 参考规则SR4 后置条件 离线开单保存 1.4 操作用户角色 操作用户 描述 营业员 营业员录入货物承运信息等。
 * 开单员 开单员录入货物承运信息等。 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2 界面原型 无 1.5.3 界面描述 无 1.6
 * 操作步骤 1.6.1 计算公布总价 序号 基本步骤 相关数据 补充步骤 1 登陆离线系统 2 打开“离线开单”界面。 3 录入发货人信息
 * 参考SUC-442 4 录入收货客户信息 参考SUC-441 5 录入货物信息 参考SUC-443 6 录入运输信息 参考SUC-445
 * 进行前面5操作之后，若是空运， 系统读取本地下载的空运公布价价格方案计算出公布总价， 显示在运单开单界面。
 * 若是汽运，系统读取本地下载的汽运公布价价格方案（计算出公布总价， 显示在运单开单界面。 参考规则SR1，SR2，SR3 扩展事件写非典型或异常操作过程
 * 序号 扩展事件 相关数据 备注 5a 1.7 业务规则 序号 描述 SR1 1）汽运：上门发货汽运运费最低X元一票；（同城、卡航；可配置）；
 * 上门接货汽运运费最低X元一票；（同城、卡航；可配置）； 2）空运：空运运费最低X元一票；（可配置）； SR2
 * 1）汽运：当货物为“接货”时，系统自动匹配生成公布价“接货价格方案”； 当货物为“非接货”时，系统自动匹配生成公布价“非接货的价格方案”；
 * 2）空运：系统自动匹配公布价 “空运价格方案”； 空运价格只有上门发货一套价格方案， 如有接货费在其他费用里添加一项接货费。
 * （来自价格基础资料，下载并且使用当前启用的最新的价格版本，参考SUC-547下载基础资料PC-SR1） SR3 1）计费方式分为重量计费、体积计费；重量、
 * 体积计费的运费=每公斤单价与货物实际重量的乘积 或 每方单价与货物实际体积的乘积，
 * 对于一票货物，系统按重量和体积分别计算并取大优先的原则计费给出公布价总运费, 计费方式即为取大的一方； SR4 参考suc-403生成运单SR1:
 * 系统自动检测，如果客户端X天还没有更新， 不能打开离线运单界面，并提示“请在间隔X天内，在线登录系统
 * 
 * 
 * 录入运输信息（离线）SUC业务规则 营业员点击运单开单，进入运单开单界面。 本界面标题：录入运输信息。 界面信息包括：收货部门、单号、运输性质、配载类型、
 * 提货方式、目的站、提货网点、上门接货、集中接货、对外备注、 对内备注、储运注意事项、配载线路（配载航班）、配载部门、
 * 最终配载部门、预计出发时间、预计派送/提货时间。 1. 收货部门：揽货部门； 2. 单号：运单单号； 3.
 * 运输性质：公司产品类型，包括精准空运、精准汽运（长途）、 精准卡航、精准城运、汽运偏线、精准汽运（短途）； 4.
 * 配载类型：公司走货方式，包括专线、偏线（外发）、合大票、单独开单； 5. 提货方式：公司提供的送货方式，包括自提（不含机场提货费）、
 * 免费自提、机场自提、送货上门、免费送货、自提、内部带货自提、送货进仓； 6. 目的站：客户所发货物要到达的目的城市和区域； 7.
 * 提货网点：收货客户可以领取货物的部门； 8. 上门接货：我司提货的一种服务，司机上门接货，确认承运， 有区别于客户上门发货； 9.
 * 集中接货：是否为集中接送货区域，以集中接货的方式揽货， 是对上门接货的一个属性补充； 10. 对外备注：客户可以看到的备注信息，包括：空、保丢不保损、
 * “不承保发霉、变质、虫蛀虫咬之损失”、“ 不承保刮花、变形、撞凹之损失”、 不可重压、易潮、不可倒置、客户指定提货网点； 11.
 * 对内备注：仅限公司内部人员看到的备注信息； 12. 储运注意事项：对外备注和对内备注信息的叠加； 13.
 * 配载线路（配载航班）:配载类型为偏线（外发）或专线时时， 显示“配载线路”，为从出发部门到达开单目的站，我司走货的线路；
 * 配载类型为合大票或单独开单时，显示“配载航班”，为我司规则的空运走货的航班类型， 包括早班、中班、晚班； 14. 配载部门:开单收货部门出发货配载专线；
 * 15. 最终配载部门:货物到达的最终部门； 16. 预计出发时间:我司走货的预计出发时间， 适用于运输类型为“精准卡航”及“精准城运”； 17.
 * 预计派送/提货时间: 我司承诺客户的可提货或送货的时间， 适用于运输类型为“精准卡航”及“精准城运”； 1.6 操作步骤 序号 基本步骤 相关数据
 * 补充步骤 1 录入收货部门 1. 规则-请参考SUC-496录入运输信息-系统规则SR1； 2 录入单号 1.
 * 系统校验单号的合法性；单号唯一在离线提交时，判断， 除单号唯一性其他参考规则SR1 3 录入运输性质 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR2、SR3； 4 录入配载类型 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR3、SR4、SR5、SR6； 5 录入提货方式 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR4、SR6、SR7、SR8； 6 录入目的站 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR9； 7 录入提货网点 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR5、SR8、SR9、SR10； 8 勾选是否上门接货 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR11； 2. 勾选上门接货，必须输入司机工号，司机工号为6为数字。参考规则SR3 9 勾选是否集中接货
 * 1. 规则-请参考SUC-496录入运输信息-系统规则SR11、SR12； 10 录入对外备注 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR13、SR14； 11 录入对内备注 1. 参考规则SR2 12 生成储运注意事项 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR14； 13 录入配载线路（配载航班） 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR15； 2. 参考SUC-547下载基础资料(PC) 14 录入配载部门 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR16； 15 录入最终配载部门 1. 规则-请参考SUC-496录入运输信息-系统规则SR17； 16
 * 生成预计出发时间 1. 规则-请参考SUC-496录入运输信息-系统规则SR18； 17 生成预计派送/提货时间 1.
 * 规则-请参考SUC-496录入运输信息-系统规则SR19； 1.7 业务规则 序号 描述 SR1 1、单号为8-9位数字， 不能输入重复单号。 SR2
 * 1、对内备注仅公司内部人员可见， 输入字符长度不得超过500。 SR3 1、当勾选上门接货时， 司机工号必填，且为6位数字。 SR4
 * 参考suc-403生成运单SR1: 系统自动检测，如果客户端X天还没有更新， 不能打开离线运单界面，并提示“请在间隔X天内，在线登录系统
 * 
 * 
 * 录入收货客户信息（离线）SUC业务规则
 * 
 * 1.1 相关业务用例 BUC_FOSS_5.20.30_550 营业部离线开单 1.2 用例描述 营业员通过本用例录入收货客户信息。 1.3 用例条件
 * 条件类型 描述 引用系统用例 前置条件 1. 离线登录成功 后置条件 1.4 操作用户角色 操作用户 描述 营业员 可查询、录入、修改收货客户信息 1.5
 * 界面要求 1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.3 界面描述 营业员点击运单开单，进入运单开单界面。 本界面为录入收货客户信息。
 * 界面为信息录入界面包括：手机、电话、客户名称、 客户编码、收货联系人（收货部门）、收货人地址； 1. 手机：收货人手机号码； 2.
 * 电话：收货人电话号码，可以添加多个； 3. 收货联系人：收货客户的客户姓名； 4. 收货人地址：收货客户的详细联系地址， 支持国家行政区域自动过滤；
 * 1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1. 规则-请参见系统规则SR1； 2 录入电话号码 1.
 * 规则-请参见系统规则SR1； 3 录入收货联系人（收货部门） 规则-请参见系统规则SR3、SR4； 4 录入收货人地址 1. 规则-请参见系统规则SR2；
 * 1.7 业务规则 序号 描述 SR1 1. 收货客户手机号码及固定电话至少提供一个， 手机号码只能为数字并且为11位，固定电话号码只能为数字，
 * 且可添加多个；添加多个时，必须用“，”或“、”或“/”分开； 固定电话号码字段也可以录入手机号； SR2 1. 客户详细地址必填至乡/镇，
 * 且乡镇下一级内容不能为空； SR3 1. 当“运单开单”中的“开单提货方式”为“内部带货自提”时， “收货联系人”字段更改为“收货部门”； SR4 1.
 * 若为公司内部带货，则收货客户信息中的收货部门名称必须与OA系统中组织架构名称保持一致； SR5 前提：收货人信息都已填充完毕，再进行手机号，
 * 电话号码修改： ① 如在三月记录中，是否再次弹出框选择记录后覆盖原来的信息， 不选择只修改手机号or电话号码，发货客户其他信息不置空 ②
 * 如不在三月记录中，是否只修改手机号或电话， 发货客户其他信息不置空------- 1、再次弹出框选择记录后覆盖原来的信息2、
 * 如果带出时是CRM客户，那么要删除客户编码和客户名称后才能够修改联系人信息，
 * 如果带出来不是，那么可以直接修改，那么修改电话号码和手机号，带出的信息都会覆盖
 * 
 * 
 * 录入发货客户信息（离线）SUC业务规则
 * 
 * 1.1 相关业务用例 BUC_FOSS_5.60.05_520 确认承运信息 1.2 用例描述 营业员在网络不通时，通过本用例录入发货客户信息。 1.3
 * 用例条件 条件类型 描述 引用系统用例 前置条件 离线登录成功； 后置条件 1. 运单收银 2. 录入增值服务信息 1.4 操作用户角色 操作用户 描述
 * 营业员 可查询、录入、修改发货客户信息 1.5 界面要求 1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.3 界面描述
 * 营业员点击运单开单，进入运单开单界面。 本界面为录入发货客户信息。 界面为信息录入界面：包括：手机、电话、 发货联系人（发货部门）、发货人地址； 1.
 * 手机：发货人手机号码； 2. 电话：发货人电话号码，可以添加多个； 3. 发货联系人（发货部门）：发货客户的客户姓名，
 * 当“运单开单”中的“开单提货方式”为“内部带货自提”时， “发货联系人”字段更改为“发货部门”； 4. 发货人地址：发货客户的详细联系地址，
 * 支持国家行政区域自动过滤； 1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1. 规则-请参见系统规则SR1； 2 录入电话号码
 * 1. 规则-请参见系统规则SR1； 3 录入发货联系人（发货部门） 1. 规则-请参见系统规则SR2、SR3； 4 录入发货人地址 1.
 * 提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段； 2. 规则-请参见系统规则SR4； 1.7 业务规则 序号 描述 SR1 1.
 * 发货客户手机号码及固定电话至少提供一个， 手机号码只能为数字并且为11位，固定电话号码只能为数字， 且可添加多个,
 * 添加多个时，必须用“，”或“、”或“/”分开；固定电话号码字段也可以录入手机号； SR2 1. 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 * “发货联系人”字段更改为“发货部门”； SR3 1. 若为公司内部带货，则发货客户信息中的发货部门名称必须与OA系统中组织架构名称保持一致； SR4 1.
 * 客户详细地址必填至乡/镇，且乡镇下一级内容不能为空； SR5 1．焦点到联系人文本框后，下个焦点直接跳过地址分段输入框，到下个控件
 * 
 * 
 * 
 * 
 * </p>
 * */
public class WaybillManagerService implements IWaybillManagerService {

	/**
	 * 定义日志静态类 通过日志工厂类获得该类的日志对象 使用该日志类的静态方法记录日志
	 */
	protected final static Logger LOG = LoggerFactory.getLogger(WaybillManagerService.class.getName());
	//350909  郭倩云   主要操作数据库表PKP.T_SRV_ADD_ASYN_WAYBILL ,异步推送数据给结算
	private IWSCWaybillProcessService  wSCWaybillProcessService;

	public IWSCWaybillProcessService getwSCWaybillProcessService() {
		return wSCWaybillProcessService;
	}

	public void setwSCWaybillProcessService(
			IWSCWaybillProcessService wSCWaybillProcessService) {
		this.wSCWaybillProcessService = wSCWaybillProcessService;
	}

	/**
	 * 服务编码
	 */
	private static final String ESB_FOSS2ESB_FOSS_ADDRESS = "ESB_FOSS2ESB_FOSS_ADDRESS";
	
	/**
	 * 定义常量值：0 1、不可继承 2、避免魔法数字
	 */
	private static final String ZEROSTR = "0";

	/**
	 * 定义常量值：4 1、不可继承 2、避免魔法数字
	 */
	private static final int FOUR = 4;

	/**
	 * 定义常量值：0.01 1、不可继承 2、避免魔法数字
	 */
	private static final double KEY_VOLUME = 0.01;

	/**
	 * 是否PDA提交后已成功在waybill、actualFreight、labelGoods表生成相关数据
	 */

	/**
	 * 定义常量值：4 准备时间 1、不可继承 2、避免魔法数字
	 */
	private static final int PREPARE_TIME = 0;

	/**
	 * 运单DAO 运单基本信息DAO服务接口 实现数据持久化
	 */
	private IWaybillDao waybillDao;
	
	/**
	 * 机构名称 code 缓存容器 减少数据交互
	 */
	private Map<String, String> orgCodeNameMap = new HashMap<String, String>();
	
	/**
	 * 更改单DAO 提供更改单持久化接口
	 */
	private IWaybillRfcDao waybillRfcDao;

	public IWaybillRfcDao getWaybillRfcDao() {
		return waybillRfcDao;
	}

	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}

	@Autowired(required = false)
	private ISysWaybillInfoToOmsService sysWaybillInfoToOmsService ; 
	
	// 校验运单号信息
	private IValuateFoss2EcsWaybillNoService valuateFoss2EcsWaybillNoService ;
	
	public IValuateFoss2EcsWaybillNoService getValuateFoss2EcsWaybillNoService() {
		return valuateFoss2EcsWaybillNoService;
	}

	public void setValuateFoss2EcsWaybillNoService(
			IValuateFoss2EcsWaybillNoService valuateFoss2EcsWaybillNoService) {
		this.valuateFoss2EcsWaybillNoService = valuateFoss2EcsWaybillNoService;
	}

	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类需求 货源品类服务
	 * 
	 * @author Foss-206860
	 */
	private ISourceCategoriesService sourceCategoriesService;

	public ISourceCategoriesService getSourceCategoriesService() {
		return sourceCategoriesService;
	}

	public void setSourceCategoriesService(
			ISourceCategoriesService sourceCategoriesService) {
		this.sourceCategoriesService = sourceCategoriesService;
	}

	/**
	 * 运单费用明细服务 提供与运单费用明细相关的服务接口
	 */
	private IWaybillChargeDtlService waybillChargeDtlService;

	/**
	 * 运单折扣明细服务 提供与运单折扣明细相关的服务接口
	 * 
	 */
	private IWaybillDisDtlService waybillDisDtlService;

	/**
	 * 运单付款明细服务 提供与运单付款明细相关的服务接口
	 */
	private IWaybillPaymentService waybillPaymentService;

	/**
	 * 在FOSS系统中创建散客服务接口
	 */
	private ISynNonfixedCustomerToCrmSerivce synNonfixedCustomerToCrmSerivce;

	private IGuiBillCaculateService guiBillCaculateService;

	/**
	 * 打印标签记录日志
	 */
	private IPrintLabelService printLabelService;

	private IExchangeRateService exchangeRateService;
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	/**
	 * 行政组织服务接口
	 */
	private IAdministrativeRegionsService administrativeRegionsService;

	// 自动补码添加信息
	private IAutoAddCodeService autoAddCodeService;

	public IAutoAddCodeService getAutoAddCodeService() {
		return autoAddCodeService;
	}

	public void setAutoAddCodeService(IAutoAddCodeService autoAddCodeService) {
		this.autoAddCodeService = autoAddCodeService;
	}

	/**
	 * 电子运单订单实体DAO
	 */
	IEWaybillOrderEntityDao ewaybillOrderEntityDao;

	private IStockService stockService;

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * 
	 * 调度订单实体DAO
	 * 
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	
	//sangwenhao-272311
	@Resource
	private IPushFoss2EcsWaybillNoLogService pushFoss2EcsWaybillNoLogService ;

	/**
	 * 交接明细dao接口
	 */
	private IStayHandoverDetailDao stayHandoverDetailDao;

	public IStayHandoverDetailDao getStayHandoverDetailDao() {
		return stayHandoverDetailDao;
	}

    /**
     * CUBC校验接口
     */
    private IValidateForCUBCService validateForCUBCService;

    public void setValidateForCUBCService(IValidateForCUBCService validateForCUBCService) {
        this.validateForCUBCService = validateForCUBCService;
    }


	/**
     * CUBC日志接口
     */
	private ISyncCUBCLogService syncCUBCLogService;
	
	/**
	 * CUBC灰度接口
	 */
	private IGrayScaleService grayScaleService;

    public void setSyncCUBCLogService(ISyncCUBCLogService syncCUBCLogService) {
        this.syncCUBCLogService = syncCUBCLogService;
    }

    public void setStayHandoverDetailDao(
			IStayHandoverDetailDao stayHandoverDetailDao) {
		this.stayHandoverDetailDao = stayHandoverDetailDao;
	}
    private ISyncWaybillLogService syncWaybillLogService;
    
    
	public ISyncWaybillLogService getSyncWaybillLogService() {
		return syncWaybillLogService;
	}

	public void setSyncWaybillLogService(
			ISyncWaybillLogService syncWaybillLogService) {
		this.syncWaybillLogService = syncWaybillLogService;
	}

	public void setGrayScaleService(IGrayScaleService grayScaleService) {
		this.grayScaleService = grayScaleService;
	}

	// 为配合快递100轨迹推送所需的方法
	private IWaybillTrackingsService  waybillTrackingsService;

	public void setWaybillTrackingsService(
			IWaybillTrackingsService waybillTrackingsService) {
			this.waybillTrackingsService = waybillTrackingsService;
			}

	// 添加 - 行政组织服务接口 SETTER AND GETTER
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	// end

	public IEWaybillOrderEntityDao getEwaybillOrderEntityDao() {
		return ewaybillOrderEntityDao;
	}

	public void setEwaybillOrderEntityDao(
			IEWaybillOrderEntityDao ewaybillOrderEntityDao) {
		this.ewaybillOrderEntityDao = ewaybillOrderEntityDao;
	}

	public IDispatchOrderEntityDao getDispatchOrderEntityDao() {
		return dispatchOrderEntityDao;
	}

	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}
	private IPdaScanDao pdaScanDao;
	public void setPdaScanDao(IPdaScanDao pdaScanDao) {
		this.pdaScanDao = pdaScanDao;
	}
	/**
	 * 快递代理理服务
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	private IWaybillPictureDao waybillPictureDao;

	public void setLdpAgencyDeptService(
			ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	/**
	 * 锁屏服务
	 */
	@Resource
	private IOrderLocksService orderLocksService;

	/**
	 * 快递冗余服务
	 */
	private IWaybillExpressService waybillExpressService;

	/**
	 * 人员接口
	 */
	private IEmployeeService employeeService;

	/**
	 * 返货服务
	 */
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	/**
	 * 客户信息Service接口.
	 */
	private ICustomerService customerService;

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	/**
	 * 获取集中开单组
	 */
	private IFocusRecordManagementService focusRecordManagementService;

	public IFocusRecordManagementService getFocusRecordManagementService() {
		return focusRecordManagementService;
	}
	
	public void setFocusRecordManagementService(
			IFocusRecordManagementService focusRecordManagementService) {
		this.focusRecordManagementService = focusRecordManagementService;
	}

	public IReturnGoodsRequestEntityService getReturnGoodsRequestEntityService() {
		return returnGoodsRequestEntityService;
	}

	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setExchangeRateService(IExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setPrintLabelService(IPrintLabelService printLabelService) {
		this.printLabelService = printLabelService;
	}

	/**
	 * 代打木架服务 提供与运单打木架相关的服务接口
	 * 
	 * == 业务规则 == 1、 查询条件中的开单时间的终止时间默认为当前时间， 起始时间默认为距当前时间的前48小时，
	 * 起止时间均可以修改，时间间隔不超过7天，不能为空 2、 货物状态默认为全部，代包装部门默认为当前操作人所在部门， 根据用户的数据权限可更换其他部门
	 * 3、 默认不执行查询操作 4、查询显示所有需要本部门代包装且含有未包装的货物运单信息，
	 * 运单明细可通过查看按钮实现，列表第一排序条件为预计发车时间升序排序， 其次按运输类型：精准城运、精准卡航、精准汽运降序排序
	 * 5、根据开单部门和本外场，通过走货路径得到货物到达本外场的时间为预计到达时间
	 * 6、根据该运单走货路径确定该运单在当前部门的预计出发时间为预计发车时间 7、代包装明细界面数据不可修改，
	 * 代包裝明细中的流水号为查询条件中货物状态相符的且需要代包装的流水号
	 * 
	 * ===========
	 */
	private IWoodenRequirementsService woodenRequirementsService;

	/**
	 * 结算接口service 提供与结算相关的服务接口
	 * 
	 * 业务规则： 一、代收货款 1、 代收货款金额处于代收货款金额范围之内 2、 如果代收货款类型为“即日退或三日退”，则收款人且开户行信息不能为空
	 * 3、 如果代收货款类型为“即日退”， 则其开户银行必须在即日退所属银行范围内 （即日退所属银行范围已经提交给综合管理做基础资料了） 4、
	 * 如果代收货款类型为“即日退”，则代收货款的退款账号类型不能为“对公帐号” 5、 应付单为有效版本且非红单 6、 应付单单据类型为“应付代收货款”
	 * 7、 应付单的来源单据编号等于传入的运单号
	 * 
	 */
	private IWaybillPickupService waybillPickupService;

	/**
	 * 运单待处理信息的处理 提供与待处理信息相关的服务接口
	 */
	private IWaybillPendingService waybillPendingService;

	/**
	 * 计算&调整走货路径服务 提供与计算&调整走货路径相关的服务接口
	 * 
	 * 
	 * 业务规则： 1、新增/修改界面，若“运输性质”为精准汽运、精准卡航， “出发站”只能从行政组织（外场）基础资料中选择，
	 * “到达站”只能从行政组织（外场）基础资料中选择； 若“运输性质”为汽运偏线，“出发站”只能从行政组织（外场）基础资料中选择，
	 * “到达站”只能从偏线代理基础资料中选择；若“运输性质”为精准空运， “出发站”只能从行政组织（空运总调或外场）基础资料中选择，
	 * “到达站”只能从空运代理网点或可空运到达的营业部信息基础资料中选择； 2、新增/修改界面，时效（小时），根据走货路径的“运输性质”，
	 * 所选“线路名称”以及“出发站”，“到达站”，由系统自动计算 3、新增/修改线路页面，走货路径可以任意选取运作到运作线路，
	 * 线路选择框需要区分汽运，空运，偏线3个tab供用户选择 4、新增/修改线路页面，“出发站”根据所选线路动态获取该线路包含线段所有的出发站，
	 * 以下拉框显示，默认选中线路的出发站。； “到达站”根据所选线路动态获取该线路包含线段所有的到达站， 以下拉框显示，默认选中线路的到达站。
	 * “时效（小时）” 根据走货路径的“运输性质”， 所选“线路名称”以及“出发站”，“到达站”，由系统自动计算
	 * 5、查询支持模糊查询，条件“出发站”支持手动输入模糊查询， 从行政组织（外场、空运总调）基础资料中选择；
	 * “到达站”从行政组织（外场）、偏线代理、空运代理网点信息基础资料中选择； “运输性质”默认为全部，包含：精准卡航，精准城运，
	 * 精准汽运（长途），精准汽运（短途），汽运偏线，精准空运 6、走货路径的第一段线路的“出发站”必须与走货路径的“出发站”一致，
	 * 走货路径的最后一段线路的“到达站”必须与走货路径的“到达站”一致， 走货路径第n段线路的“出发站”必须与走货路径第n-1段线路的“到达站”一致
	 * 7、走货路径的线路中，选择的“到达站”在线路中的站点位置必须在“出发站”的站点位置之后 8、新增/修改线路页面，“是否可以打木架”单选按钮，
	 * 是或否，默认为否，若值为“否”，单选按钮为只读状态， 不允许选择“是”；若值为“是”，单选按钮可以选择为“否”；
	 * “打木架外场”与“是否可以打木架”联动，若“是否可以打木架”值为“否”， “打木架外场”隐藏；若“是否可以打木架”值为“是”，“打木架外场”显示，
	 * 下拉框里会把拥有“可以打木架”属性的外场查询出来， 默认为第一个具有“可以打木架” 属性的外场 9、新增/修改页面，相同运输性质，
	 * 出发站和到达站之间，只能有一条默认走货路径
	 */
	private ICalculateTransportPathService calculateTransportPathService;

	/**
	 * 签收单返单 提供与签收单返单相关的持久化接口
	 */
	private IReturnBillProcessDao returnBillProcessDao;

	/**
	 * 货签信息 提供与货签信息相关的服务接口
	 */
	private ILabeledGoodService labeledGoodService;

	/**
	 * 运单库存 提供与运单库存相关的服务接口
	 */
	private IWaybillStockService waybillStockService;

	/**
	 * 线路信息服务 提供与线路信息相关的服务接口
	 */
	private ILineService lineService;

	/**
	 * 快递线路信息服务 提供与快递线路信息相关的服务接口
	 */
	private IExpressLineService expresslineService;

	/**
	 * 货区服务
	 */
	private IGoodsAreaService goodsAreaService;

	/**
	 * 部门信息服务 提供与部门信息相关的服务接口
	 */
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 代理网点 提供与代理网点相关的服务接口
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;

	/**
	 * 组织机构信息 提供与组织机构相关的服务接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/** 空运区域 Service. */
	private IRegionAirService regionAirService;

	public void setRegionAirService(IRegionAirService regionAirService) {
		this.regionAirService = regionAirService;
	}

	/** 出发区域 Service. */
	private IRegionService regionService;

	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}

	/** 到达区域Service. */
	private IRegionArriveService regionArriveService;

	public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}

	/** 客户优惠信息Service接口. */
    private IPreferentialService preferentialService;

	public void setPreferentialService(IPreferentialService preferentialService) {
		this.preferentialService = preferentialService;
	}

	/**
	 *  客户合同信息Service接口
	 */
	private ICusBargainService cusBargainService;

	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	/**
	 * 待处理返券服务
	 * 
	 * @author 206860
	 */
	private IPendingSendCouponService pendingSendCouponService;

	public void setPendingSendCouponService(
			IPendingSendCouponService pendingSendCouponService) {
		this.pendingSendCouponService = pendingSendCouponService;
	}

	/**
	 * 返券日志服务
	 * 
	 * @author 206860
	 */
	private IPendingSendCouponLogService pendingSendCouponLogService;

	public void setPendingSendCouponLogService(
			IPendingSendCouponLogService pendingSendCouponLogService) {
		this.pendingSendCouponLogService = pendingSendCouponLogService;
	}

	/**
	 * 获取综合基础资料服务
	 * 
	 * @author Foss-206860
	 * */
	private IPriceCouponService priceCouponService;

	public void setPriceCouponService(IPriceCouponService priceCouponService) {
		this.priceCouponService = priceCouponService;
	}

	/**
	 * 计价服务 提供与计价服务相关的服务接口
	 */
	private IBillCaculateService billCaculateService;

	/**
	 * 待办事项服务接口 提供与处理待办事项相关的服务接口
	 * 
	 * 业务规则 1、 初始值设置：运单号为空，处理状态默认为“全部”，变更申请部门为空，
	 * 更改受理查询开始时间和更改受理查询结束时间分别为当前日期中的00:00:00和23:59:59 2、
	 * 只有涉及到包装、件数、货物类型、目的站、运单号的更改才需要生成待办事项并被查询出来 3、 待办事项的生成是在执行节点生成后在节点部门生成待办事项
	 * 4、 更改受理的查询时间段不能超过X天（具体天数可配置 5、 只能查询到由“变更申请部门”申请， 处理部门为当前操作部门的待办事项，
	 * 不能查询到其他部门的待办事项 6、 若查询条件中包含有运单号时，则只以运单号作为唯一查询条件 7、
	 * 【待办事项】栏中记录的“查看”按钮在任何时候都可点击查看标签信息 8、 【打印标签】栏中的“打印标签”按钮在任何时候都可点击再打印 9、
	 * 系统打印标签时，判断是否已打印过标签，若未打印过则生成新的走货路径，否则不生成。打印出新标签 10、 点击“打印标签”时，系统判断是否已出库，
	 * 若是则更新状态为“处理超时”同时更新处理时间， 否则根据是否已全部打印来更新状态： 未全部打印时更新为 “处理中”，全部打印时更新为“已处理”
	 * 11、 每打印一件则更新一次处理时间为当前时间 12、 若该标签已打印过，则不再更新状态和处理时间
	 * 当货物出库后，点击打印标签时，系统自动判断该票货是否已打标签， 是则继续，否则提示“该货物已出本部门库存，不能再处理 13、
	 * 执行节点的生成规则参见“SUC-509调整运单执行计划(自动)”的SR2的第2点
	 * 
	 */
	private IPickupToDoMsgService pickupToDoMsgService;

	/**
	 * 调用中转接口查询走货路径 提供与中转走货路径相关的持久化接口
	 */
	private IPathDetailDao pathDetailDao;
	// PDA盲扫DAO
	private IPdaScanService pdaScanService;

	public void setPdaScanService(IPdaScanService pdaScanService) {
		this.pdaScanService = pdaScanService;
	}
	
	/**
	 * 合同适用部门Service接口 
	 */
	IBargainAppOrgService bargainAppOrgService;	
	public void setBargainAppOrgService(IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}
	

	private IMakeUpWaybillService makeUpWaybillService;

	/**
	 * 外请车 提供与外请车相关的服务接口
	 */
	private IInviteVehicleService inviteVehicleService;

	/**
	 * 走货路径 提供与走货路径相关的服务接口
	 */
	private IFreightRouteService freightRouteService;

	/**
	 * 走货路径线路 提供与走货路径线路相关的服务接口
	 */
	private IFreightRouteLineService freightRouteLineService;

	/**
	 * 部门 复杂查询 service 提供与部门 复杂查询相关的服务接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 运单完结状态 提供与运单完结状态相关的服务接口
	 */
	private IWaybillTransactionService waybillTransactionService;

	/**
	 * 运单附件信息service 提供与运单附件信息相关的服务接口
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * 安装费明细
	 */
	private IinstallationService installationService;

	/**
	 * PDA运单补录修改记录 提供与PDA运单补录修改记录相关的服务接口
	 */
	private IWaybillAcHisPdaService waybillAcHisPdaService;

	/**
	 * 发送短信语音服务 提供与发送短信语音服务相关的服务接口
	 */
	private ISendSmsVoiceService sendSmsVoiceService;

	/**
	 * GIS查询服务 提供与GIS查询服务相关的服务接口
	 */
	private IGisQueryService gisQueryService;

	/**
	 * 订单处理服务 提供与订单处理服务相关的服务接口
	 */
	private IOrderService orderService;

	/**
	 * 历史发货客户服务 提供与历史发货客户相关的服务接口
	 */
	private IHisDeliveryCusService hisDeliveryCusService;

	/**
	 * 历史收货客户服务 提供与历史收货客户相关的服务接口
	 */
	private IHisReceiveCusService hisReceiveCusService;

	/**
	 * 查询客户信息
	 */
	private IQueryCustomerService queryCustomerService;

	/**
	 * 银行服务 提供与银行相关的服务接口
	 */
	private IBankService bankService;

	/**
	 * 系统配置项服务 提供与系统配置项相关的服务接口
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * CRM访问服务 提供与CRM访问相关的服务接口
	 */
	private ICrmOrderService crmOrderService;

	/**
	 * 应用监控指标 提供与应用监控指标相关的服务接口
	 */
	private IBusinessMonitorService businessMonitorService;

	/**
	 * 定人定区/运单地址临时表Service 提供与定人定区/运单地址临时表相关的服务接口
	 */
	private IReceiveAddressRfcService receiveAddressRfcService;

	/**
	 * 待处理发送短信服务
	 */
	private IPendingSendSMSService pendingSendSMSService;

	/**
	 * 国际化信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	private IMessageBundle messageBundle;

	/**
	 * 外场服务
	 */
	private IOutfieldService outfieldService;

	/**
	 * 自有司机
	 */
	private IOwnDriverService ownDriverService;

	private IBillingGroupTransFerService billingGroupTransFerService;

	/**
	 * 更改单Service
	 */
	private IWaybillRfcService waybillRfcService;
	/**
	 * 货物类型
	 */
	private IGoodsTypeService goodsTypeService;
	/**
	 * 数据字典值服务
	 */
	@Resource
	private IDataDictionaryValueDao pkpdataDictionaryValueDao;

	private IPdaBillCaculateService pdaBillCaculateService;

	private ISalesBillingGroupService salesBillGroupService;

	private ISalesDepartmentDao salesDepartmentDao;

	/**
	 * 待办接口
	 */
	private ILabeledGoodTodoDao labeledGoodTodoDao;

	private IWaybillRfcVarificationDao waybillRfcVarificationDao;

	public void setWaybillRfcVarificationDao(
			IWaybillRfcVarificationDao waybillRfcVarificationDao) {
		this.waybillRfcVarificationDao = waybillRfcVarificationDao;
	}

	/**
	 * 营销活动服务接口
	 */
	private IMarkActivitiesService markActivitiesService;

	public void setMarkActivitiesService(
			IMarkActivitiesService markActivitiesService) {
		this.markActivitiesService = markActivitiesService;
	}

	private ILabeledGoodPDAService labeledGoodPDAService;

	public ILabeledGoodPDAService getLabeledGoodPDAService() {
		return labeledGoodPDAService;
	}

	public void setLabeledGoodPDAService(
			ILabeledGoodPDAService labeledGoodPDAService) {
		this.labeledGoodPDAService = labeledGoodPDAService;
	}

	/**
	 * 
	 * 偏线时效方案服务类
	 */
	private IOuterEffectivePlanService outerEffectivePlanService;

	private ISysConfigService pkpsysConfigService;

	// 待处理运单主表
	IWaybillPendingDao waybillPendingDao;

	/**
	 * 產品定义服务类
	 */
	private IProductService productService;

	/**
	 * 產品定义服务类
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;

	public IPushTrackForCaiNiaoService getPushTrackForCaiNiaoService() {
		return pushTrackForCaiNiaoService;
	}

	/**
	 * 内部员工折扣方案
	 */
	private IInempDiscountPlanService inempDiscountPlanService;

	public void setInempDiscountPlanService(
			IInempDiscountPlanService inempDiscountPlanService) {
		this.inempDiscountPlanService = inempDiscountPlanService;
	}

	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}

	public IWaybillPendingDao getWaybillPendingDao() {
		return waybillPendingDao;
	}

	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}

	/**
	 * 客户联系人信息Service接口
	 */
	private ICusContactService cusContactService;

	public void setCusContactService(ICusContactService cusContactService) {
		this.cusContactService = cusContactService;
	}

	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}

	/**
	 * 偏线时效方案服务类
	 */
	public void setOuterEffectivePlanService(
			IOuterEffectivePlanService outerEffectivePlanService) {
		this.outerEffectivePlanService = outerEffectivePlanService;
	}

	/**
	 * 待办接口
	 */
	public void setLabeledGoodTodoDao(ILabeledGoodTodoDao labeledGoodTodoDao) {
		this.labeledGoodTodoDao = labeledGoodTodoDao;
	}

	public void setSalesDepartmentDao(ISalesDepartmentDao salesDepartmentDao) {
		this.salesDepartmentDao = salesDepartmentDao;
	}

	public void setSalesBillGroupService(
			ISalesBillingGroupService salesBillGroupService) {
		this.salesBillGroupService = salesBillGroupService;
	}

	public IPdaBillCaculateService getPdaBillCaculateService() {
		return pdaBillCaculateService;
	}

	public void setPdaBillCaculateService(
			IPdaBillCaculateService pdaBillCaculateService) {
		this.pdaBillCaculateService = pdaBillCaculateService;
	}

	public IDataDictionaryValueDao getPkpdataDictionaryValueDao() {
		return pkpdataDictionaryValueDao;
	}

	public void setPkpdataDictionaryValueDao(
			IDataDictionaryValueDao pkpdataDictionaryValueDao) {
		this.pkpdataDictionaryValueDao = pkpdataDictionaryValueDao;
	}

	public IGoodsTypeService getGoodsTypeService() {
		return goodsTypeService;
	}

	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}

	public IMakeUpWaybillService getMakeUpWaybillService() {
		return makeUpWaybillService;
	}

	public void setMakeUpWaybillService(
			IMakeUpWaybillService makeUpWaybillService) {
		this.makeUpWaybillService = makeUpWaybillService;
	}

	public IWaybillRfcService getWaybillRfcService() {
		return waybillRfcService;
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	private ISignDetailService signDetailService;

	private IUserService userService;

	/**
	 * 货签DAO
	 */
	private ILabeledGoodDao labeledGoodDao;

	public ILabeledGoodDao getLabeledGoodDao() {
		return labeledGoodDao;
	}

	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	
	/**
	 * 子母件服务
	 * */
	public IWaybillRelateDetailEntityService waybillRelateDetailEntityService;

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	/**
	 * 异常货接口
	 * */
	private IQmsYchService qmsYchService;

	public void setQmsYchService(IQmsYchService qmsYchService) {
		this.qmsYchService = qmsYchService;
	}
	
	@Resource
	private IWaybillInfoToPtpService waybillInfoToPtpService ;
	
	@Resource
	private IWaybillInfoToCUBCService waybillInfoToCUBCService; 
	
	@Resource
	private IExpWaybillInfoToPtpService expWaybillInfoToPtpService ;
	
	/**
	 * @项目：智能开单项目
	 * @功能：定义时间统计service
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-27上午09:00
	 */
	IIntelligenceTimeRecordService intelligenceTimeRecordService;

	@Resource
	private IAreaAddressService areaAddressService;
	
	/**
	 * 根据运单号集合查询坏账信息
	 * 
	 * @author foss-叶涛
	 * @throws Exception 
	 * @date 2014-10-18 上午10:20:54
	 * 
	 */
	@SuppressWarnings("null")
	@Override
	public List<BillBadAccountEntity> queryByWaybillNOs(
			List<String> waybillNoList){
	    /**
	     * @author 306486
	     * @date  2017年1月13日
	     * CUBC接口改造
	     */
		//请求对象的数据装箱
		RequestDO requestDO = new RequestDO();		
		requestDO.setServiceCode(WaybillManagerService.class.getName()+".queryByWaybillNOs");
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		requestDO.setSourceBillNos(waybillNoList.toArray((new String[waybillNoList.size()])));
	    //FOSS对象
		List<BillBadAccountEntity> billBadAccountEntitysFOSS   = null;
		//CUBC对象
		List<BillBadAccountEntity> billBadAccountEntitysCUBC=null;
		//发起请求
		VestResponse response = null;
		try{
            response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
        } catch (Exception e){
        	throw new BusinessException(Constants.GRAY_SERVICE_EXCEPTION+e.getMessage());
        }
		//判断返回结果是否为空
		if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
			//返回的结果
			List<VestBatchResult> batchResults = response.getVestBatchResult();
			List<String> waybillNosFOSS=null;
			List<String> waybillNosCUBC=null;
			for (int i = 0; i < batchResults.size(); i++) {
				if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResults.get(i).getVestSystemCode())){
					waybillNosFOSS=batchResults.get(i).getVestObject();
				}else if(Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResults.get(i).getVestSystemCode())){
					waybillNosCUBC=batchResults.get(i).getVestObject();
				}
			}
			for(int i = 0; i < batchResults.size(); i++){
		    	VestBatchResult batchResult = batchResults.get(i);
    			if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResult.getVestSystemCode())){//FOSS
    				billBadAccountEntitysFOSS = waybillPickupService.queryByWaybillNOs(waybillNosFOSS);
    			}else if (Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResult.getVestSystemCode())){//CUBC
    				//CUBC接口
					List<TradeDO> tradeDOList = queryByWaybillNOsForCUBC(waybillNoList);
    				//遍历赋值
				    for (int j = 0; j < tradeDOList.size(); j++) {
				    //实体赋值
				    BillBadAccountEntity billBadAccountEntitys = converFromTradeDo(tradeDOList.get(j));
				    //List新增
				    billBadAccountEntitysCUBC.add(billBadAccountEntitys);
				    }
    			}
			}
		}
		billBadAccountEntitysFOSS.addAll(billBadAccountEntitysCUBC);
		return billBadAccountEntitysFOSS;
	}
	private List<TradeDO>  queryByWaybillNOsForCUBC(List<String> waybillNoList){
		Map<String, Object> toCubcMap=new HashMap<String, Object>();
	    toCubcMap.put("sourceBillNosList", waybillNoList);
	    toCubcMap.put("sourceBillType", Constants.GRAY_SOURCE_BILLTYPE_W);
	    toCubcMap.put("active", FossConstants.YES);
	    String requestparams=JSON.toJSONString(toCubcMap);
	    LOG.info("cubc的访问接口queryByWaybillNOsForCUBC方法的json信息"+requestparams);
	    try {
			return grayScaleService.grayQueryOrderByList(requestparams);
		} catch (Exception e) {
			LOG.info("cubc的访问接口queryByWaybillNOsForCUBC方法时异常");
			return null;
		}
	}
	/**
	 * @author 306486
	 * @param tradeDO
	 * @return
	 */
	private BillBadAccountEntity converFromTradeDo(TradeDO tradeDO){
		BillBadAccountEntity entity=new BillBadAccountEntity();
		//ID
		Long id=tradeDO.getId();
		if (null!=id) {
			entity.setId(String.valueOf(id));
		}
		
		//坏账单编号
		if (StringUtil.isNotBlank(tradeDO.getSourceBillNo() )) {
			entity.setBadDebatBillNo(tradeDO.getSourceBillNo());
		}
		
		// 申请部门编码
		if (StringUtil.isNotBlank(tradeDO.getSourceBillNo() )) {
			entity.setApplyOrgCode(tradeDO.getCreateOrgCode());
		}

		//申请部门名称
		if (StringUtil.isNotBlank(tradeDO.getCreateOrgName() )) {
			entity.setApplyOrgName(tradeDO.getCreateOrgName());
		}
		
		//申请人工号
		if (StringUtil.isNotBlank(tradeDO.getCreateUserCode() )) {
			entity.setApplyUserCode(tradeDO.getCreateUserCode());
		}
		
		//申请人名称
		if (StringUtil.isNotBlank(tradeDO.getCreateUserName() )) {
			entity.setApplyUserName(tradeDO.getCreateUserName());
		}
		
		//差错处理编号
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getMistakeNum())) {
			entity.setErrorHandingNo(tradeDO.getTradeAttributeDO().getMistakeNum());
		}
		//申请事由
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getBadDebtReason())) {
			entity.setApplyReason(tradeDO.getTradeAttributeDO().getBadDebtReason());
		}
		
		//应收单号
		if (StringUtil.isNotBlank(tradeDO.getOrderNo())) {
			entity.setReceivableNo(tradeDO.getOrderNo());
		}
		
		//运单号
		if (StringUtil.isNotBlank(tradeDO.getWaybillNo())) {
			entity.setWaybillNo(tradeDO.getWaybillNo());
		}
		
		//来源单据类型
		if (StringUtil.isNotBlank(tradeDO.getSourceBillType())) {
			entity.setSourceBillType(tradeDO.getSourceBillType());
		}
		
		//出发部门编码
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getOrigOrgCode())) {
			entity.setOrigOrgCode(tradeDO.getTradeAttributeDO().getOrigOrgCode());
		}
		
		//出发部门名称
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getOrigOrgName())) {
			entity.setOrigOrgName(tradeDO.getTradeAttributeDO().getOrigOrgName());
		}
		
		//收货部门编码
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getDescOrgCode())) {
			entity.setDestOrgCode(tradeDO.getTradeAttributeDO().getDescOrgCode());
		}
		
		//收货部门名称
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getDescOrgName())) {
			entity.setDestOrgName(tradeDO.getTradeAttributeDO().getDescOrgName());
		}
		
		//发货客户编码
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getDeliveryCustomerCode())) {
			entity.setFcustomerCode(tradeDO.getTradeAttributeDO().getDeliveryCustomerCode());
		}
		
		//发货客户名称
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getDeliveryCustomerName())) {
			entity.setFcustomerName(tradeDO.getTradeAttributeDO().getDeliveryCustomerName());
		}
		
		//发货客户电话
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getCustomerPhone())) {
			entity.setDeliveryCustomerPhone(tradeDO.getTradeAttributeDO().getCustomerPhone());
		}
		
		//目的站
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getDestination())) {
			entity.setArrvRegionCode(tradeDO.getTradeAttributeDO().getDestination());
		}
		
		//收货客户编码
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getReceiveCustomerCode())) {
			entity.setReceiveCustomerCode(tradeDO.getTradeAttributeDO().getReceiveCustomerCode());
		}
		
		//收货客户名称
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getReceiveCustomerName())) {
			entity.setReceiveCustomerName(tradeDO.getTradeAttributeDO().getReceiveCustomerName());
		}

		//货物名称
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getGoodsName())) {
			entity.setGoodsName(tradeDO.getTradeAttributeDO().getGoodsName());
		}
		
		//付款方式
		if (StringUtil.isNotBlank(tradeDO.getPaymentType())) {
			entity.setPaymentType(tradeDO.getPaymentType());
		}
		
		//坏账金额
		if (null!=tradeDO.getAmount()) {
			entity.setBadAmount(tradeDO.getAmount());
		}
		
		//审核状态
		if (null!=tradeDO.getOption()) {
			if((tradeDO.getOption() & 2048) ==2048){
			    	entity.setAuditStatus("未审核");
			    }else if((tradeDO.getOption() & 2048) == 0){
			    	entity.setAuditStatus("已审核");
			    }else{
			    	entity.setAuditStatus("已审核");
			    }
		}

		//工作流号
		if (StringUtil.isNotBlank(tradeDO.getPaymentNo())) {
			entity.setWorkflowNo(tradeDO.getPaymentNo());
		}
		
		//创建部门编码
		if (StringUtil.isNotBlank(tradeDO.getCreateOrgCode())) {
			entity.setCreateOrgCode(tradeDO.getCreateOrgCode());
		}


		//创建部门名称
		if (StringUtil.isNotBlank(tradeDO.getCreateOrgName())) {
			entity.setCreateOrgName(tradeDO.getCreateOrgName());
		}

		// 创建人工号
		if (StringUtil.isNotBlank(tradeDO.getCreateUserCode())) {
			entity.setCreateUserCode(tradeDO.getCreateUserCode());
		}
		
		// 创建人名称
		if (StringUtil.isNotBlank(tradeDO.getCreateUserName())) {
			entity.setCreateUserName(tradeDO.getCreateUserName());
		}
		
		// 是否核销
		if (tradeDO.getVerifyAmount().compareTo(new BigDecimal(0))!=0) {
			entity.setVerifyStatus("Y");
		}else{
			entity.setVerifyStatus("N");
		}
		
		//创建时间
		if (null!=tradeDO.getCreateDate()) {
			entity.setCreateTime(tradeDO.getCreateDate());
		}
		
		//备注
		if (StringUtil.isNotBlank(tradeDO.getRemark())) {
			entity.setNotes(tradeDO.getRemark());
		}
		
		//币种
		if (StringUtil.isNotBlank(tradeDO.getCurrencyType())) {
			entity.setCurrencyCode(tradeDO.getCurrencyType());
		}
		
		//冲账方式
		if (StringUtil.isNotBlank(tradeDO.getTradeAttributeDO().getBillType())) {
			entity.setNotes(tradeDO.getTradeAttributeDO().getBillType());
		}
	    return entity;
	}
	
	/**
	 * @param configurationParamsService
	 *            the configurationParamsService to set 
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		// 服务接口，用于spring注入
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param messageBundle
	 *            the messageBundle to set
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	public void setGuiBillCaculateService(
			IGuiBillCaculateService guiBillCaculateService) {
		this.guiBillCaculateService = guiBillCaculateService;
	}

	public void setSynNonfixedCustomerToCrmSerivce(
			ISynNonfixedCustomerToCrmSerivce synNonfixedCustomerToCrmSerivce) {
		this.synNonfixedCustomerToCrmSerivce = synNonfixedCustomerToCrmSerivce;
	}

	public void setQueryCustomerService(
			IQueryCustomerService queryCustomerService) {
		this.queryCustomerService = queryCustomerService;
	}

	/**
	 * @项目：新客户体验项目
	 * @功能：导入查询大区的包
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-07-22上午10:00
	 */
	ILateCouponService lateCouponService;

	public ILateCouponService getLateCouponService() {
		return lateCouponService;
	}

	public void setLateCouponService(ILateCouponService lateCouponService) {
		this.lateCouponService = lateCouponService;
	}
	
	IWaybillHomeImpService waybillHomeImpService;

	public IWaybillHomeImpService getWaybillHomeImpService() {
		return waybillHomeImpService;
	}

	public void setWaybillHomeImpService(
			IWaybillHomeImpService waybillHomeImpService) {
		this.waybillHomeImpService = waybillHomeImpService;
	}
	//待刷卡运单service liding add
	public IWSCWayBillManageService wscWayBillManageService;

	
	//合伙人项目
	private IPartnersWaybillService partnersWaybillService ;
	public void setPartnersWaybillService(
			IPartnersWaybillService partnersWaybillService) {
		this.partnersWaybillService = partnersWaybillService;
	}

	public void setWscWayBillManageService(
			IWSCWayBillManageService wscWayBillManageService) {
		this.wscWayBillManageService = wscWayBillManageService;
	}
		
	/** ======================== 业务方法 ====================== */
	/**
	 * 
	 * <p>
	 * 通过运单号获取运单基础信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:07:54
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillByNo(java.lang.String)
	 */
	@Override
	public WaybillEntity queryWaybillBasicByNo(String waybillNo) {
		// 返回运单基本信息
		return waybillDao.queryWaybillByNo(waybillNo);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号查询运单信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-31 上午10:17:33
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillByNo(java.lang.String)
	 */
	@Override
	public WaybillDto queryWaybillByNo(String waybillNo) {
		// 定义运单DTO对象
		WaybillDto waybill = new WaybillDto();
		// 设置运单基本信息 editor:306486
		WaybillEntity waybillEntity = waybillDao.queryWaybillBasicInfoByNo(waybillNo);
		//获取发货客户精准包裹权限
		if(waybillEntity!=null&&StringUtil.isNotEmpty(waybillEntity.getDeliveryCustomerCode())){
			 CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(waybillEntity.getDeliveryCustomerCode());
			 waybillEntity.setDeliveryCustomerIsAccuratePackage(cusBargainEntity==null?FossConstants.NO:cusBargainEntity.getIsAccuratePackage());
		}
		waybill.setWaybillEntity(waybillEntity);
		// 设置运单费用明细信息
		waybill.setWaybillChargeDtlEntity(waybillChargeDtlService
				.queryChargeDtlEntityByNo(waybillNo));
		// 设置运单折扣明细信息
		waybill.setWaybillDisDtlEntity(waybillDisDtlService
				.queryDisDtlEntityByNo(waybillNo));
		// 设置运单付款明细信息
		waybill.setWaybillPaymentEntity(waybillPaymentService
				.queryWaybillPayment(waybillNo));
		// 设置打木架信息
		waybill.setWoodenRequirementsEntity(woodenRequirementsService
				.queryWoodenRequirements(waybillNo));
		// 设置运单冗余信息
		waybill.setActualFreightEntity(queryActualFreightByNo(waybillNo));

		// TODO
		// 小件添加---------------------------
		if (waybill.getWaybillEntity() != null
				&& (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getWaybillEntity().getProductCode(), waybill.getWaybillEntity().getBillTime()))) {
			waybill.setWaybillExpressEntity(getWabillExpressEntityByWaybillId(waybill.getWaybillEntity().getId()));


			// TODO 返单 返货 都需要判断签收状态的 顶
			List<LabeledGoodEntity> labeledGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
			boolean allSigned = true;

			if (labeledGoodList != null && labeledGoodList.size() > 0) {
				for (int i = 0; i < labeledGoodList.size(); i++) {
					LabeledGoodEntity labeledGood = labeledGoodList.get(i);
					if (labeledGood == null || !FossConstants.ACTIVE.equals(labeledGood.getActive())) {
						continue;
					}

					String signResult = FossConstants.NO;

					// 结算这里没有引入jar所以可能会为null没有注入
					if (signDetailService != null) {
						signResult = signDetailService.querySerialNoIsSign(waybillNo, labeledGood.getSerialNo());
					}

					if (!FossConstants.YES.equals(signResult)) {
						allSigned = false;
					}

				}
			}
			// 在判断是否可以返货的时候有用
			waybill.setAllSigned(allSigned);
		}

		// 返回运单DTO
		return waybill;
	}

	/**
	 * 小件
	 * 
	 * @param id
	 * @return
	 */
	private WaybillExpressEntity getWabillExpressEntityByWaybillId(String waybillId) {
		return waybillExpressService.queryWaybillExpressByWaybillId(waybillId);
	}

	/**
	 * 根据封装的DTO查询运单基本和冗余信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public List<WaybillDto> queryActualFreightAndBasic(WaybillDto waybillDto) {
		// 定义集合对象
		List<WaybillDto> waybillDtoList = null;

		// 查询出实体集合信息
		List<WaybillEntity> entityList = queryWaybill(waybillDto);
		// 集合非空判断
		if (CollectionUtils.isNotEmpty(entityList)) {
			// 实例化对象
			waybillDtoList = new ArrayList<WaybillDto>();
			// 遍历集合
			for (WaybillEntity entity : entityList) {
				// 定义dto对象
				WaybillDto waybill = new WaybillDto();
				// 定义 冗余对象
				ActualFreightEntity actualFreight = queryActualFreightByNo(entity.getWaybillNo());

				// 封装对象
				waybill.setWaybillEntity(entity);
				waybill.setActualFreightEntity(actualFreight);

				// 加入集合
				waybillDtoList.add(waybill);
			}
		}

		return waybillDtoList;
	}

	/**
	 * 根据封装的DTO查询运单基本和冗余信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public List<WaybillDto> queryActualFreightAndBasicExpress(
			WaybillDto waybillDto) {
		// 定义集合对象
		List<WaybillDto> waybillDtoList = null;

		// 查询出实体集合信息
		List<WaybillEntity> entityList = queryWaybill(waybillDto);
		// 集合非空判断
		if (CollectionUtils.isNotEmpty(entityList)) {
			// 实例化对象
			waybillDtoList = new ArrayList<WaybillDto>();
			// 遍历集合
			for (WaybillEntity entity : entityList) {
				// 定义dto对象
				WaybillDto waybill = new WaybillDto();
				// 定义 冗余对象
				ActualFreightEntity actualFreight = queryActualFreightByNo(entity.getWaybillNo());
				//判断是否补录中心补录的单据，如果是则显示开单人写“供应商”开单部门写“供应商部门”
				if(null!=actualFreight && null!=actualFreight.getIsExpressFocus() && "Y".equals(actualFreight.getIsExpressFocus())){
						  entity.setCreateUserCode("供应商");
				 }
				// 封装对象
				waybill.setWaybillEntity(entity);
				waybill.setActualFreightEntity(actualFreight);

				// 加入集合
				waybillDtoList.add(waybill);
			}
		}

		return waybillDtoList;
	}

	/**
	 * 根据封装的DTO查询运单基本和冗余信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public List<WaybillDto> queryActualFreightAndBasicNoExpress(
			WaybillDto waybillDto) {
		// 定义集合对象
		List<WaybillDto> waybillDtoList = null;

		// 查询出实体集合信息
		List<WaybillEntity> entityList = queryWaybillNoExpress(waybillDto);
		// 集合非空判断
		if (CollectionUtils.isNotEmpty(entityList)) {
			// 实例化对象
			waybillDtoList = new ArrayList<WaybillDto>();
			// 遍历集合
			for (WaybillEntity entity : entityList) {
				// 定义dto对象
				WaybillDto waybill = new WaybillDto();
				// 定义 冗余对象
				ActualFreightEntity actualFreight = queryActualFreightByNo(entity.getWaybillNo());

				// 封装对象
				waybill.setWaybillEntity(entity);
				waybill.setActualFreightEntity(actualFreight);

				// 加入集合
				waybillDtoList.add(waybill);
			}
		}

		return waybillDtoList;
	}

	/**
	 * 根据运单号查询ActualFreightEntity对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-29 下午4:42:19
	 */
	@Override
	public ActualFreightEntity queryActualFreightByNo(String waybillNo) {
		return actualFreightService.queryByWaybillNo(waybillNo);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号码集合获取运单信息集合
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-31 上午10:19:51
	 * @param waybillNoList
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillByNoList(java.util.List)
	 */
	@Override
	public List<WaybillDto> queryWaybillByNoList(List<String> waybillNoList) {
		// 定义运单DTO集合，用于接收查询到集合数据
		List<WaybillDto> list = new ArrayList<WaybillDto>();
		// 判断集合对象个数是否大于0
		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			// 根据运单号循环查询运单DTO对象
			for (String waybillNo : waybillNoList) {
				// 将查询到的数据加入集合
				list.add(queryWaybillByNo(waybillNo));
			}
		}

		// 返回结果集合信息，若无数据，则返回空
		return list;
	}

	/**
	 * 
	 * <p>
	 * 通过运单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:44:25
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsits(java.lang.String)
	 */
	@Override
	public boolean isWayBillExsits(String waybillNo) {
		boolean isExsits = true;

		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(StringUtil.defaultIfNull(waybillNo));
		if (waybillEntity == null) {
			isExsits = false;
		}
		return isExsits;
	}

	/**
	 * 
	 * <p>
	 * 通过运单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:44:25
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsits(java.lang.String)
	 */
	@Override
	public boolean isWayBillExsitsAndOrderNo(String waybillNo, String orderNo) {
		boolean isExsits = true;

		WaybillEntity waybillEntity = waybillDao.queryWaybillByNoAndOrderNo(StringUtil.defaultIfNull(waybillNo), orderNo);
		if (waybillEntity == null) {
			isExsits = false;
		}
		return isExsits;
	}

	/**
	 * 
	 * <p>
	 * 通过运单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:44:25
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsits(java.lang.String)
	 */
	@Override
	public boolean isWayBillExsitsNoPDAPending(String waybillNo) {
		boolean isExsits = true;

		WaybillEntity waybillEntity = waybillDao.queryWaybillByNoExceptPDAPending(StringUtil.defaultIfNull(waybillNo));
		/**
		 * 如果有waybillEntity且waybillEntity.getActive()=N
		 */
		if (waybillEntity != null && FossConstants.INACTIVE.equals(waybillEntity.getActive())) {

			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(StringUtil.defaultIfNull(waybillNo));
			// 如果actualFreightEntity是空的，且不是暂存的单
			if (actualFreightEntity == null && !WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillEntity.getPendingType())) {
				return false;
			} else {
				// 如果actualFreightEntity不是空，且是中止的运单
				if (actualFreightEntity != null && WaybillRfcConstants.ABORT.equals(actualFreightEntity.getStatus())) {
					return false;
				}

			}
		}

		if (waybillEntity == null) {
			isExsits = false;
		}
		return isExsits;
	}

	/**
	 * 根据运单号判断该PDA_PENDING状态的运单是否存在
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-1 下午2:46:32
	 */
	@Override
	public boolean isWayBillExsitsPDAPending(String waybillNo) {
		// 初始化
		boolean isExsits = false;
		// 查询对象
		WaybillEntity waybillEntity = waybillDao.queryWaybillExistByPDAPending(StringUtil.defaultIfNull(waybillNo));
		// 判断是否存在
		if (waybillEntity != null) {
			isExsits = true;
		}
		return isExsits;
	}

	/**
	 * 
	 * <p>
	 * 通过运单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:44:25
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsits(java.lang.String)
	 */
	@Override
	public boolean isWayBillPendingExsits(String waybillNo) {
		// 直接返回方法调用结果
		return waybillPendingService.isPendingExsits(waybillNo);
	}

	/**
	 * 
	 * <p>
	 * 通过订单编号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-14 下午2:49:06
	 * @param orderNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsitsByOrderNo(java.lang.String)
	 */
	public boolean isWayBillExsitsByOrderNo(String orderNo) {
		if (StringUtils.isEmpty(orderNo)) {// 判断一下 减少执行一句sql
			return false;
		}
		// 根据订单号查询运单基本信息
		WaybillEntity waybillEntity = waybillDao.queryWaybillByOrderNo(orderNo);
		// 若查询数据为空则返回false，否则返回true
		return waybillEntity == null ? false : true;
	}

	/**
	 * 
	 * <p>
	 * 通过订单编号判断运单是否存在 同时返回运单号
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-14 下午2:49:06
	 * @param orderNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsitsByOrderNo(java.lang.String)
	 */
	public String getWayBillNoExsitsByOrderNo(String orderNo) {
		if (StringUtils.isEmpty(orderNo)) {// 判断一下 减少执行一句sql
			return null;
		}
		// 根据订单号查询运单基本信息
		WaybillEntity waybillEntity = waybillDao.queryWaybillByOrderNo(orderNo);
		if (waybillEntity != null) {
			return waybillEntity.getWaybillNo();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 通过订单编号判断暂存运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-14 下午2:49:06
	 * @param orderNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsitsByOrderNo(java.lang.String)
	 */
	@Override
	public boolean isWayBillPendingExsitsByOrderNo(String orderNo) {
		// 直接返回查询结果
		return waybillPendingService.isPendingExsits(orderNo);
	}

	/**
	 * 新增运单库存
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午6:48:53
	 */
	public void addWaybillStockService(WaybillDto waybillDto) {
		// 添加日志信息，记录库存生成信息
		LOG.info("运单提交生成库存信息addWaybillStockService开始。。。");
		// 获得当前登陆信息
		CurrentInfo currentInfo = waybillDto.getCurrentInfo();
		// 判空操作
		if (currentInfo == null) {
			// 抛出当前登陆用户信息为空的异常信息
			throw new IllegalArgumentException("当前用户信息不能为空！");
		}
		// 用户信息
		UserEntity userEntity = currentInfo.getUser();

		// 运单基本信息
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();

		// 待生成库存的流水号
		List<String> serialNos = new ArrayList<String>();
		// 总件数
		int totalPieces = waybillEntity.getGoodsQtyTotal();
		// 生成流水号
		for (int i = 1; i <= totalPieces; i++) {
			// 生成4位流水号，左边以0补充
			String s = StringHandlerUtil.lpad(String.valueOf(i), FOUR, ZEROSTR);
			// 将流水加入集合当中
			serialNos.add(s);
		}
		// 初始化布尔常量
		boolean isStockSyn = false;
		/**
		 * 获取系统参数， 如果为空，设置为同步入库 根据该系统参数，来配置入库数据
		 */
		ConfigurationParamsEntity stockSynentity = null;
		/**
		 * 获取系统参数 如果异步入库件数需要达到系统配置的参数
		 */
		ConfigurationParamsEntity stockNunberEntity = null;

		// 捕捉异常信息
		try {
			// 添加操作日志，以便进行日志出入库记录跟踪
			LOG.info("开始调用waybillStockService.saveWaybillStockService生成库存");

			/**
			 * 如果没有配置，那么直接执行同步入库操作
			 */
			try {
				/**
				 * 获取系统参数 如果为空，设置为同步入库
				 */
				stockSynentity = getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_WAYBILL_STOCK_SYN);
				/**
				 * 获取系统参数 如果异步入库件数需要达到系统配置的参数
				 */
				stockNunberEntity = getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_WAYBILL_STOCK_NUMBER);
			} catch (BusinessException e) {
				// 添加日志信息
				LOG.info("获取库存生成方式参数异常");
			}

			/**
			 * 系统参数配置对象是否为空 如果设置为异步，且件数打印系统配置的入库件数，可以为异步
			 */
			if (stockSynentity != null && stockNunberEntity != null) {
				/**
				 * 如果设置为异步，且件数打印系统配置的入库件数，可以为异步
				 */
				if (StringUtil.equals(FossConstants.ACTIVE, stockSynentity.getConfValue())) {
					// 配置项的值
					int stockNumber = Integer.parseInt(stockNunberEntity.getConfValue());
					// 判断配置项是否小于流水号个数，若是则设置为可异步操作
					if (serialNos.size() >= stockNumber) {
						// 设置为可异步
						isStockSyn = true;
					}
				}
			}

			// 根据是否异步操作来来添加库存信息
			if (isStockSyn) {
				// 若为异步操作，则保存待处理表
				waybillStockService.addWaybillStock(waybillDto.getActualFreightEntity(),
						waybillDto.getWaybillEntity(), userEntity);
			} else {
				// 若为同步操作， 运单入库
				waybillStockService.saveWaybillStockService(waybillDto.getActualFreightEntity(),
						waybillDto.getWaybillEntity(), serialNos, userEntity);
			}
		} catch (BusinessException e) {
			// 添加异常日志信息
			LOG.error("Excepton", e);
			// 抛出异常信息至前台，并终止程序继续运行
			throw new WaybillStoreException(WaybillStoreException.WAYBILLSTOCKINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getErrorCode(), e.getErrorArguments()) });
		}
	}

	/**
	 * 获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	private ConfigurationParamsEntity getConfigurationParamsEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 * 组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 * 异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);

	}

	/**
	 * 删除已补录的PDA运单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-2 下午6:31:19
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#deleteAdditionalState(java.lang.String)
	 */
	@Override
	public void deleteAdditionalState(String waybillNum) {
		// 根据运单号删除已补录的PDA运单信息
		waybillPendingService.deleteByWaybillNo(waybillNum);
	}

	public void addLine(WaybillDto waybill) {
		addLine(waybill, false);
	}

	/**
	 * 新增线路信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-3 上午9:11:01
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#addLine()
	 */
	public void addLine(WaybillDto waybill, boolean isPdaPending) {
		// 运单基本信息实体
		WaybillEntity waybillEntity = waybill.getWaybillEntity();
		// 生成线程信息
		String waybillNo = waybillEntity.getWaybillNo();
		/**
		 * 判断：PDA补录运单是否已生成相关数据,若PDA已在运单表中生成相关记录， 则不生成走货路径、不生成库存信息
		 * 且更新actualFreight表和waybill表
		 */
		if (!isPdaPending) {
			// 添加运单线路新增日志
			LOG.info("运单提交调用addLine新增线路。。。");
			// 捕捉新增线路时的异常信息
			// 开单时间
			Date billingTime = waybillEntity.getBillTime();
			// 开单组织
			String receiveOrgCode = waybillEntity.getReceiveOrgCode();
			// 制单部门 编码
			String createOrgCode = waybillEntity.getCreateOrgCode();
			// 添加日志，跟踪开单的营业部组织组织
			LOG.info("开单营业部receiveOrgCode:" + receiveOrgCode);
			// 最终配载部门
			String destOrgCode = waybillEntity.getCustomerPickupOrgCode();

			// 添加日志，提货网点CODE
			LOG.info("提货网点destOrgCode:" + destOrgCode);

			// 总重量
			BigDecimal totalWeight = waybillEntity.getGoodsWeightTotal();
			// 总体积
			BigDecimal totalVolume = waybillEntity.getGoodsVolumeTotal();
			// 货物总件数
			Integer goodsQtyTotal = waybillEntity.getGoodsQtyTotal();
			// 产品类型
			String transType = waybillEntity.getProductCode();

			// 添加日志，标识产品类型
			LOG.info("运输性质transType:" + transType);
			// 添加日志，标识当前部门组织编码
			LOG.info("开单营业部currentOrgCode:" + receiveOrgCode);
			// 当前库存部门编码
			String currentOrgCode = "";
			try {
				// 是否集中接送货部门
				if (FossConstants.YES.equals(waybillEntity
						.getPickupCentralized())) {
					//判断是否异地开单
					WaybillPictureEntity waybillPic = new WaybillPictureEntity();
					waybillPic.setActive(FossConstants.YES);
					waybillPic.setWaybillNo(waybillNo);
					waybillPic = waybillPendingService.queryWaybillPictureByEntity(waybillPic);
					//根据集中接送货部门编码查询其对应的外场编码
					BillingGroupTransFerEntity entity = null;
					
					if(waybillPic!=null && FossConstants.NO.equals(waybillPic.getLocal())
							&& StringUtil.isNotEmpty(waybillPic.getLocalBillGroupCode())){
						//根据集中接送货部门编码查询其对应的外场编码
						 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(waybillPic.getLocalBillGroupCode());
					}else{
						//根据集中接送货部门编码查询其对应的外场编码
						 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(createOrgCode);
					}
					// 根据集中接送货部门编码查询其对应的外场编码
					// 判断对象是否为空
					if (null != entity) {
						// 外场编码
						currentOrgCode = entity.getTransFerCode();
						// 非空判断
						if (StringUtil.isEmpty(currentOrgCode)) {
							// 若外场编码为空，则抛出异常信息（"集中接送货部门对应的外场编码为空！"）
							throw new HandleTransportPathException(
									HandleTransportPathException.NO_TRANSFERCENTER_CODE);
						}
					} else {
						// 若没有查询到外场编码，则抛出异常信息
						throw new HandleTransportPathException(
								HandleTransportPathException.NO_TRANSFERCENTER_INFO);
					}
				} else {
					// 是否批量开单(批量开单是仓管组开的单，不是营业部)
					if (FossConstants.ACTIVE
							.equals(waybill.getActualFreightEntity()
									.getIsBatchCreateWaybill())) {
						createOrgCode = receiveOrgCode;
					}
//					SaleDepartmentEntity salesDept = saleDepartmentService
//							.querySaleDepartmentByCode(createOrgCode);
					SaleDepartmentEntity salesDept = saleDepartmentService
							.querySimpleSaleDepartmentByCode(createOrgCode);
					// 是否驻地部门
					if (null != salesDept
							&& FossConstants.YES.equals(salesDept.getStation())) {
						currentOrgCode = lineService.queryDefaultTransCode(
								createOrgCode, transType,
								DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
					} else {
						currentOrgCode = createOrgCode;
					}
				}

				// 对象传递
				TransportPathEntity transportPath = new TransportPathEntity();
				// 运单号
				transportPath.setWaybillNo(waybillNo);
				// 开单时间
				transportPath.setBillingTime(billingTime);
				// 收货部门
				transportPath.setBillingOrgCode(receiveOrgCode);
				// 当前库存部门编码
				transportPath.setCurrentOrgCode(currentOrgCode);
				// 最终到达部门编号
				transportPath.setDestOrgCode(destOrgCode);
				// 总重量
				transportPath.setTotalWeight(totalWeight);
				// 总体积
				transportPath.setTotalVolume(totalVolume);
				// 总件数
				transportPath.setGoodsQtyTotal(goodsQtyTotal);
				// 运输类型
				transportPath.setTransportModel(transType);
				// 调用生成线路接口 开单新增走货路径
				calculateTransportPathService
						.createTransportPath(transportPath);
			} catch (BusinessException e) {
				// 获取产品类型名称
				ProductEntity productEntity = productService.getProductByCache(
						transType, billingTime);
				String transTyp = productEntity == null ? transType
						: productEntity.getName();
				// 添加异常日志信息
				LOG.error("Excepton", e);
				// 拼接传入参数
				String currentOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(currentOrgCode);
				String destOrgName = this.getCustomerPickUpNameByCode(destOrgCode, transType, new Date());
				// 添加当前库存部门，提货网点的部门名称
				StringBuffer arg = new StringBuffer("");
				arg.append("\n[调用生成线路接口 开单新增走货路径出错:");
				arg.append("]\n[当前库存部门:");
				arg.append(currentOrgName + "(" + currentOrgCode + ")");
				arg.append("]\n[最终到达部门:");
				arg.append(destOrgName + "(" + destOrgCode + ")");
				arg.append("]\n[运输类型:");
				arg.append(transTyp);
				arg.append("]\n异常详细信息:");
				// 添加综合走货路径异常信息，因为走货路径异常信息都已经被封装好了，直接抛出更合理
				if ("foss.scheduling.transportPath.cantFindLeaveTime".equals(e
						.getErrorCode())) {
					arg.append(" 没有配置发车标准时间，请配置发车标准时间以后再提交！");
				} else {
					arg.append(messageBundle.getMessage(e.getErrorCode() + "\n"
							+ e.getMessage())
							+ "]");
				}
				// arg.append("]\n请联系走货路径开发员解决");
				// 抛出生成运单线路信息的异常信息，中止程序继续运行
				System.out.println(e.getErrorCode() + e.getMessage());
				throw new HandleTransportPathException(
						HandleTransportPathException.ADDLINE_FAIL,
						new Object[] { messageBundle.getMessage(arg.toString()) });
			}

			/***
			 * 只推送订单来源为‘TAOBAO’且运输性质为‘标准快递、3.60特惠件’的运单 插入快递相关轨迹到货物表
			 * 推送的字段包括：运单号、物流订单号（渠道订单号）、发生时间、跟踪信息描述、发生城市、站点类型、编号、名称、事件；
			 * e：发生时间指开单时间；跟踪信息描述：揽收成功，【开单部门】库存中；发生城市：开单部门所在城市；站点类型：1；
			 * 编号：开单部门编码；名称：开单部门名称；事件：GOT
			 ****/
			WaybillExpressEntity expEntity = waybill.getWaybillExpressEntity();
			if (expEntity != null
					&& WaybillConstants.CRM_ORDER_CHANNEL_TAOBAO
							.equals(waybillEntity.getOrderChannel())) {
				ExpressTrackExternalEntity entity = new ExpressTrackExternalEntity();
				entity.setId(UUIDUtils.getUUID());
				// 运单
				entity.setWayBillNo(waybillNo);
				// 订单
				entity.setChannelOrder(waybillEntity.getOrderNo());
				// 创建时间
				entity.setCreateTime(new Date());
				// 发生时间
				entity.setOperaTime(waybillEntity.getBillTime());
				// 更新时间
				entity.setModifyTime(new Date());
				// 跟踪信息描述
				entity.setTrackInfo("揽收成功，【"
						+ waybillEntity.getReceiveOrgName() + "】库存中");
				// 开单部门所在城市
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(waybillEntity
								.getReceiveOrgCode());
				if (org != null) {
					entity.setOperateCity(org.getCityName());
				}
				// 站点类型
				entity.setOrgType("1");
				// 开单部门编码
				entity.setOrgCode(waybillEntity.getReceiveOrgCode());
				// 开单部门名称
				entity.setOrgName(waybillEntity.getReceiveOrgName());
				// 事件
				entity.setEventType("GOT");
				pushTrackForCaiNiaoService.addCarGoTrack(entity);
			}
			// 添加日志，新增线路处理完毕
			LOG.info("运单提交调用addLine新增线路处理完毕。。。");

			// 给中转推送货物轨迹（PDA补录不推送，因为PDA开单时已经推送）
			addSynTrack(waybill);
		} else {
			if (   waybill.getActualFreightEntity() != null
					&& waybill.getActualFreightEntity().getWaybillType() !=null 
					&& WaybillConstants.EWAYBILL.equals(waybill
							.getActualFreightEntity().getWaybillType())) {
				// 给中转推送货物轨迹（PDA补录不推送，因为PDA开单时已经推送）
				addSynTrack(waybill);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * 通过运单号码集合获取运单基础信息集合
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:11:39
	 * @param waybillNoList
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillByNoList(java.util.List)
	 */
	@Override
	public List<WaybillEntity> queryWaybillBasicByNoList(List<String> waybillNoList) {
		// 返回运单基本信息集合值
		return waybillDao.queryWaybillByNoList(waybillNoList);
	}

	/**
	 * 运单校验
	 * 
	 * @author Bobby
	 * @date 2012-10-17 下午3:40:17
	 * @param waybill
	 */
	private void verifyWaybill(WaybillDto waybill) {
		// 运单基本信息实体
		WaybillEntity waybillEntity = waybill.getWaybillEntity();
		// 运单号，初始化为空字符串
		String waybillNo = "";
		// 订单号，初始化为空字符串
		String orderNo = "";
		// 对象非空判断
		if (waybillEntity != null) {
			// 获得运单号
			waybillNo = StringUtil.defaultIfNull(waybillEntity.getWaybillNo());
			// 获得该运单对应的订单号
			orderNo = StringUtil.defaultIfNull(waybillEntity.getOrderNo());
		}

		// 校验运单号是否已存在
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybill.getWaybillEntity().getPendingType())) {
			// PDA补录
			if (isWayBillExsitsNoPDAPending(waybillNo) && waybillPendingService.checkEWaybillPending(waybillNo)) {
				/**
				 * 抛出异常日志信息
				 * 
				 * 该运单号已存在 主要是以抛出异常信息的方法中止程序的运行，防止继续走后面的代码逻辑 从而造成类似空指针异常等客户不友好提示
				 */
				throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS,
						new Object[] { waybillNo });
			}
		} else {
			// 非PDA补录
			if (isWayBillExsits(waybillNo)) {
				/**
				 * 抛出异常日志信息
				 * 
				 * 该运单号已存在 主要是以抛出异常信息的方法中止程序的运行，防止继续走后面的代码逻辑 从而造成类似空指针异常等客户不友好提示
				 */
				throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS, new Object[] { waybillNo });
			}
		}

		// 根据运单号查询出的订单
		WaybillEntity orderWaybill = null;
		if (StringUtil.isNotEmpty(orderNo)) {
			orderWaybill = queryWaybillByOrderNo(orderNo);
		}
		// 效验订单是否已导入开单:若不为空，则表示已导入
		if (null != orderWaybill && waybillEntity != null) {
			// 判断是否为PDA运单
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillEntity.getPendingType())) {
				/**
				 * 参见BUG-7744
				 * 
				 * 若为PDA运单，则需要分析
				 * 1、如果有订单号的PDA提交后成功生成了运单信息，则还判断当前导入的运单号与已存在的订单对应的运单号是否一致：
				 * 若是即使订单号已存在也可以提交（因为提交时只是更新，不会新增），否则不可以提交
				 * 2、如果有订单号的PDA提交后生成运单信息失败了
				 * ，在判断“判断当前导入的运单号与已存在的订单对应的运单号一致”的前提下，还要判断待处理运单表中的PDA是否已关联了
				 */
				if (isWayBillExsitsPDAPending(waybillNo)) {
					// 判断运单号是否一致
					if (orderWaybill != null) {
						ActualFreightEntity af = actualFreightService.queryByWaybillNo(orderWaybill.getWaybillNo());
						/**
						 * KDTE-4505 终止和作废的老运单是不需要检查的，直接排除
						 */
						if (!WaybillConstants.ABORTED.equals(af.getStatus())
								&& !WaybillConstants.OBSOLETE.equals(af.getStatus())) {
							if (!waybillNo.equals(orderWaybill.getWaybillNo())) {
								// 若不一致，则不能提交
								throw new WaybillValidateException(WaybillValidateException.WAYBILL_IMPORT, new Object[] { orderNo });
							}
						}
					}
				} else {
					// 判断运单号在待处理表中是否已存在
					boolean isExist = waybillPendingService.isPendingExsitsAndOrderNo("", orderNo);
					if (isExist) {
						// 抛出异常信息：订单号已导入过了
						throw new WaybillValidateException("该订单号在PDA待补录运单中已存在，请使用其它订单号！");
					}
				}

			} else {
				ActualFreightEntity af = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());

				if (af != null) {
					if (WaybillConstants.EFFECTIVE.equals(af.getStatus())
							|| af.getStatus() == null) {
						// 抛出异常信息：订单号已导入过了
						throw new WaybillValidateException(WaybillValidateException.WAYBILL_IMPORT, new Object[] { orderNo });
					}
				}

			}
		}

		// 校验数据合法性
		checkWaybillData(waybill);
		/**
		 * @功能：验证单号是否转储
		 * @author:218371-foss-zhaoyanjun
		 * @date2015-05-26上午10:02
		 */
		checkWaibillNo(waybillNo);
	}

	/**
	 * @功能：验证单号是否转储
	 * @author:218371-foss-zhaoyanjun
	 * @date2015-05-26上午10:02
	 */
	private void checkWaibillNo(String waybillNo) {
		if (isExistWaybillTransaction(waybillNo)) {
			throw new WaybillValidateException("此单号已被转储，不能再使用");
		}
	}

	/**
	 * 校验运单数据是否完整（必要信息）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:26:21
	 */
	private void checkWaybillData(WaybillDto waybill) {
		// 运单信息
		WaybillEntity waybillEntity = waybill.getWaybillEntity();
		// 运单冗余信息
		ActualFreightEntity acf = waybill.getActualFreightEntity();
		String returnType = null;
		WaybillExpressEntity expressEntity = waybill.getWaybillExpressEntity();
		if (expressEntity != null) {
			returnType = expressEntity.getReturnType();
		}
		// 付款信息
		List<WaybillPaymentEntity> paymentList = waybill.getWaybillPaymentEntity();
		// 费用信息
		List<WaybillChargeDtlEntity> chargeDetail = waybill.getWaybillChargeDtlEntity();
		// 折扣信息
		List<WaybillDisDtlEntity> discoutDetail = waybill
				.getWaybillDisDtlEntity();
		/**
		 * 校验内部员工折扣额度 dp-foss-dongjialing 225131
		 */
		if ((WaybillConstants.DELIVERY_PAY
				.equals(acf.getInternalDeliveryType()) || WaybillConstants.RECIVE_PAY
				.equals(acf.getInternalDeliveryType()))
				&& StringUtil.isNotBlank(acf.getEmployeeNo())) {
			InempDiscountPlanEntity entity = new InempDiscountPlanEntity();
			entity.setBillTime(waybillEntity.getBillTime());
			entity.setActive(FossConstants.ACTIVE);
			List<InempDiscountPlanEntity> list = inempDiscountPlanService
					.queryInempDiscountPanByCondition(entity);
			if (CollectionUtils.isNotEmpty(list)) {
				InempDiscountPlanEntity inempDiscountPlanEntity = list.get(0);
				if (inempDiscountPlanEntity.getHighstPreferentialLimit() != null
						&& inempDiscountPlanEntity.getHighstPreferentialLimit().compareTo(BigDecimal.ZERO) > 0) {
					BigDecimal amount = queryDiscountFeeByEmployeeNo(
							acf.getEmployeeNo(), waybillEntity.getBillTime());
					if (amount == null) {
						amount = BigDecimal.ZERO;
					}
					if (StringUtil.isNotBlank(waybillEntity
							.getOriginalEmployeeNo())
							&& waybillEntity.getOriginalEmployeeNo().equals(
									acf.getEmployeeNo())) {
						if (waybillEntity.getOriginalFee() != null) {
							amount = amount.subtract(waybillEntity
									.getOriginalFee().multiply(
											BigDecimal.valueOf(NumberConstants.NUMBER_100)));
						}
					}
				
						BigDecimal differenceValue = inempDiscountPlanEntity.getHighstPreferentialLimit().subtract(
									amount.divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
					if (differenceValue.compareTo(BigDecimal.ZERO) <= 0) {
						throw new WaybillValidateException(WaybillValidateException.EMPLOYEE_HIGHSTPREFERENTIAL_LIMIT_NOT_ENABLE);
					}
				} else {
					throw new WaybillValidateException(WaybillValidateException.EMPLOYEE_HIGHSTPREFERENTIAL_LIMIT_NOT_ENABLE);
				}
			} else {
				throw new WaybillValidateException(WaybillValidateException.NOT_INEMP_DISCOUNT_PLAN);
			}
		}
		// 校验数据是否符合逻辑
		if (waybillEntity != null) {
			String pickupMode = StringUtil.defaultIfNull(waybillEntity.getReceiveMethod());
			// 提货方式不为内部带货自提
			if (!WaybillConstants.INNER_PICKUP.equals(pickupMode)
					&& !WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(returnType)
					&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(pickupMode)) {
				// 判断费用明细的集合信息是否为空

				if (!isExpress(waybill)) {

					if (CollectionUtils.isEmpty(chargeDetail)) {
						// 抛出异常信息：内部带货自提时，费用明细信息不能为空！
						throw new WaybillValidateException(WaybillValidateException.CHARGEDETAIL_EMPTY);
					}
					// 判断付款明细集合信息是否为空
					if (CollectionUtils.isEmpty(paymentList) && !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
						// 抛出异常信息：内部带货自提时，付款明细信息不能为空！
						throw new WaybillValidateException(WaybillValidateException.PAYMENTLIST_EMPTY);
					}
				}
			}
			// 提货方式为内部带货自提费用明细必须为空
			else {
				// 开单提货方式为[内部带货自提],费用明细必须为空！
				if (chargeDetail != null && chargeDetail.size() > 0) {
					// 抛出异常信息：内部带货自提时，费用明细信息不能为空！
					throw new WaybillValidateException(WaybillValidateException.CHARGEDETAIL_EMPTY);
				}
				// 开单提货方式为[内部带货自提],付款明细信息必须为空！
				if (paymentList != null && paymentList.size() > 0) {
					// 抛出异常信息：内部带货自提时，付款明细信息不能为空！
					throw new WaybillValidateException(WaybillValidateException.PAYMENTLIST_EMPTY);
				}
				// 开单提货方式为[内部带货自提],折扣明细信息必须为空！
				if (discoutDetail != null && discoutDetail.size() > 0) {
					// 抛出异常信息：内部带货自提时，折扣明细信息不能为空！
					throw new WaybillValidateException(WaybillValidateException.DISCOUTDETAIL_NULL);
				}
			}
		}
		// 运单基本信息为空，则表明该运单为空，属于异常运单
		else {
			// 若运单基本信息为空，则说该运单号不存在，后面的业务逻辑也不能继续走下去了
			throw new WaybillValidateException(WaybillValidateException.WAYBILLENTITY_NULL);
		}

		String receiveMethod = waybill.getWaybillEntity().getReceiveMethod();
		String code = waybill.getWaybillEntity().getCustomerPickupOrgCode();

		if (isExpress(waybill)) {

			if (code != null) {
				WaybillExpressEntity expentity = waybillExpressService.queryWaybillExpressByNo(
						waybill.getWaybillEntity().getWaybillNo());

				// 是否自提
				if (!this.verdictPickUpSelf(receiveMethod)) {
					if (!StringUtils.isEmpty(code)
							&& (expentity == null || (expentity != null && !FossConstants.YES.equals(expentity.getIsAddCode())))) {
//						OrgAdministrativeInfoEntity o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(code);
						OrgAdministrativeInfoEntity o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
						if (o != null && !FossConstants.YES.equals(o.getExpressSalesDepartment())) {
							// 非自提的情况下 只能选虚拟营业部
							throw new WaybillValidateException(WaybillValidateException.WAYBILLENTITY_EXPRESS_VIRTAL);
						}
					}
				} else {
					if (!StringUtils.isEmpty(code)
							&& (expentity == null || (expentity != null && !FossConstants.YES.equals(expentity.getIsAddCode())))) {
//						OrgAdministrativeInfoEntity o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(code);
						OrgAdministrativeInfoEntity o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
						if (o != null && FossConstants.YES.equals(o.getExpressSalesDepartment())) {
							// 自提只能选非虚拟营业部
							throw new WaybillValidateException(WaybillValidateException.WAYBILLENTITY_EXPRESS);
						}
					}
				}
			}

		}

	}

	/**
	 * 根据提货方式判断：是否自提
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午3:52:17
	 */
	public boolean verdictPickUpSelf(String pickupType) {
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.SELF_PICKUP.equals(type)
				|| WaybillConstants.INNER_PICKUP.equals(type)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(type)
				|| WaybillConstants.AIRPORT_PICKUP.equals(type)
				|| WaybillConstants.AIR_SELF_PICKUP.equals(type)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 激活电子运单时校验
	 * 
	 * @author Bobby
	 * @date 2014-10-29 下午3:40:17
	 * @param waybill
	 */
	private void veryfyEWaybill(WaybillDto waybillDto) {

		String waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
		if (StringUtil.isNotBlank(waybillNo)) {
			if (isWayBillExsits(waybillNo)) {
				// 如果运单号在运单表存在，则抛异常
				throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS, new Object[] { waybillNo });
			}
		}
	}
	/**
		 * @功能：验证单号是否转储
		 * @author:218371-foss-zhaoyanjun
		 * @date2015-05-26上午10:02
		 */
		// checkWaibillNo(waybillNo);	}

	/**
	 * 生成运单基础信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-7 下午1:40:33
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#addWaybillEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity)
	 */
	public boolean addWaybillEntityWithPdaPending(WaybillDto waybill,
			WaybillSystemDto systemDto, boolean isPdaPending) {
		// 登陆用户信息对象
		UserEntity userEntity = systemDto.getCurrentUser().getUser();
		// 用户信息非空判断
		if (userEntity == null) {
			// 若用户信息为空，则不允许程序继续进行下去
			throw new IllegalArgumentException("当前用户信息不能为空！");
		}

		// 获得运单基本信息
		WaybillEntity waybillEntity = waybill.getWaybillEntity();
		waybillEntity.setIsExpress(waybill.getIsExpress());

		// 获得老运单号
		String oldWaybill = waybill.getOldWaybillNo();

		// 设置创建时间、创建人、修改时间、创建人工号、修改人工号、开单时间
		waybillEntity.setCreateTime(systemDto.getCreateTime());
		// 设置修改时间
		waybillEntity.setModifyTime(systemDto.getModifyTime());
		/**
		 * 图片开单,点击修改
		 */
		WaybillPictureEntity picture = waybillPictureDao
				.queryWaybillPictureByWaybillNo(waybillEntity.getWaybillNo());
		if (picture != null
				&& (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING
						.equals(picture.getPendgingType()) || WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION
						.equals(picture.getPendgingType()))) {
			if (null != picture.getOperator()) {
				waybillEntity.setCreateUserCode(picture.getOperator());
			}
			if (null != picture.getBelongOrgCode()) {
				waybillEntity.setCreateOrgCode(picture.getBelongOrgCode());
			}

		} else {
			// 创建人
			waybillEntity.setCreateUserCode(systemDto.getCurrentUser()
					.getEmpCode());
		}		// 修改人工号
		waybillEntity.setModifyUserCode(systemDto.getCurrentUser().getEmpCode());
		// 运输类型
		waybillEntity.setTransportType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillEntity.getProductCode()));

		String orgName = waybillEntity.getCreateUserDeptName();
		if (StringUtils.isEmpty(orgName)) {
//			orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getCreateOrgCode());
			orgName = queryOrgNameMapsByCodes(waybillEntity.getCreateOrgCode());
			waybillEntity.setCreateUserDeptName(orgName);
		}

		// 部门组织
		OrgAdministrativeInfoEntity dept = null;
		if(WaybillConstants.YES.equals(waybill.getIsGuiSubmit())){
			dept = systemDto.getCurrentUser().getUser().getEmployee().getDepartment();
		}else{
			dept = systemDto.getCurrentUser().getDept();
		}
		// 部门组织非空判断
		if (dept != null) {
			// 设置开单组织
			waybillEntity.setCreateOrgCode(StringUtil.defaultIfNull(dept.getCode()));

			// 非批量开单（批量开单是物料小组开单,不是营业部，所以改成发货部门）
			if (FossConstants.ACTIVE.equals(waybill.getActualFreightEntity()
					.getIsBatchCreateWaybill())) {
				// 设置开单组织
				waybillEntity.setCreateOrgCode(waybillEntity
						.getReceiveOrgCode());
			}
			// zxy 20130926 BUG-55867 start
			// 新增：[修改部门]改成当前登陆用户部门，之前：如果PDA单补录，用PDA的创建部门；否则应该用当前登陆人的
			waybillEntity.setModifyOrgCode(StringUtil.defaultIfNull(dept.getCode()));
			// zxy 20130926 BUG-55867 end
			// 新增：[修改部门]改成当前登陆用户部门，之前：如果PDA单补录，用PDA的创建部门；否则应该用当前登陆人的
		}

		// 设置是否有效
		waybillEntity.setActive(FossConstants.ACTIVE);
		// 新增历史客户
		addHistoryCustomer(waybill, systemDto);

		String waybillId = "";
		// 判断是否是PDA补录\
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillEntity.getPendingType())) {
			// 若老运单号为空，则不允许提交
			if ("".equals(oldWaybill)) {
				LOG.error("PDA补录时，老运单号不能为空！");
				throw new WaybillSubmitException("PDA补录时，老运单号不能为空！");
			}
			// 若PDA运单 ，设置运单开单时间为pda开单的那个时间
			waybillEntity.setBillTime(waybillEntity.getBillTime());
			// 设置运单类型为PDA补录
			waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE);

			/**
			 * PDA提交时，t_srv_waybill、t_srv_actualFreiht、t_srv_labelGoods表即使没有插入成功
			 * PDA也能成功提交，因此还需要判断t_srv_waybill是否有值
			 */
			isPdaPending = isWayBillExsitsPDAPending(oldWaybill);
			if (isPdaPending) {

				// ISSUE-4480
				// 中转提供一个删除老的走货路径，重新生成新的走货路径的功能
				// 只有快递调用
//				if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {
				if (isExpress(waybill)) {
					WaybillEntity oldEntity = waybillDao.queryWaybillByNo(oldWaybill);



					if (!StringUtils.isEmpty(waybillEntity.getCustomerPickupOrgCode())
							&& oldEntity != null && !StringUtils.isEmpty(oldEntity.getCustomerPickupOrgCode())
							&& !waybillEntity.getCustomerPickupOrgCode().equals(oldEntity.getCustomerPickupOrgCode())) {
						// 老的目的站和新的补录以后目的站不同的时候 就调用该方法
						calculateTransportPathService.changeDestinationPathForRecordAgain(
								oldEntity.getWaybillNo(), waybillEntity.getCustomerPickupOrgCode());
					}
				}
				// ISSUE-4480
				// 在更新运单状态前，保存是否PDA成功生成数据的状态
				// 根据运单信息
				waybillDao.updateWaybillByWaybillNo(waybillEntity, oldWaybill);
				/**
				 * 根据运单号查询运单ID
				 * 
				 * 因为生成运单后没有返回ID，因此需要再查询一次后将ID返回并保存到waybillEntity中
				 * 提供给结算使用，否则会报错错误信息
				 */
				// 若为更新，则一定会有运单ID
				WaybillEntity newWaybillEntity = waybillDao.queryWaybillByNo(StringUtil.defaultIfNull(waybillEntity.getWaybillNo()));
				// 判断查询出的对象是否为空
				if (null == newWaybillEntity) {
					throw new WaybillSubmitException("\n运单ID未查询到！");
				} else {
					waybillId = newWaybillEntity.getId();
				}

			} else {
				waybillId = waybillDao.addWaybillEntity(waybillEntity);
			}
		} else {
			// 设置统一的运单开单时间
			waybillEntity.setBillTime(systemDto.getBillTime());
			// 设置运单类型为PC开单
			waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE);

			waybillId = waybillDao.addWaybillEntity(waybillEntity);
		}

		// 设置运单ID
		waybill.getWaybillEntity().setId(waybillId);

		return isPdaPending;
	}

	/**
	 * 生成运单基础信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-7 下午1:40:33
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#addWaybillEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity)
	 */
	public boolean addWaybillEntityWithEWaybill(WaybillDto waybill,
			WaybillSystemDto systemDto, boolean isPdaPending) {
		// 登陆用户信息对象
		UserEntity userEntity = systemDto.getCurrentUser().getUser();
		// 用户信息非空判断
		if (userEntity == null) {
			// 若用户信息为空，则不允许程序继续进行下去
			throw new IllegalArgumentException("当前用户信息不能为空！");
		}

		// 获得运单基本信息
		WaybillEntity waybillEntity = waybill.getWaybillEntity();
		// 增加是否快递字段 add by yangkang
		if (StringUtil.isNotBlank(waybill.getIsExpress())) {
			waybillEntity.setIsExpress(waybill.getIsExpress());
		}

		// 获得老运单号
		String oldWaybill = waybill.getOldWaybillNo();

		// 设置创建时间、创建人、修改时间、创建人工号、修改人工号、开单时间
		waybillEntity.setCreateTime(systemDto.getCreateTime());
		// 设置修改时间
		waybillEntity.setModifyTime(systemDto.getModifyTime());
		// 创建人
		waybillEntity.setCreateUserCode(systemDto.getCurrentUser().getEmpCode());
		// 修改人工号
		waybillEntity.setModifyUserCode(systemDto.getCurrentUser().getEmpCode());
		// 运输类型
		waybillEntity.setTransportType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillEntity.getProductCode()));

		String orgName = waybillEntity.getCreateUserDeptName();
		if (StringUtils.isEmpty(orgName)) {
			orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getCreateOrgCode());
			waybillEntity.setCreateUserDeptName(orgName);
		}

		// 部门组织
		OrgAdministrativeInfoEntity dept = systemDto.getCurrentUser().getDept();
		// 部门组织非空判断
		if (dept != null) {
			// 设置开单组织
			waybillEntity.setCreateOrgCode(StringUtil.defaultIfNull(dept.getCode()));
			// zxy 20130926 BUG-55867 start
			// 新增：[修改部门]改成当前登陆用户部门，之前：如果PDA单补录，用PDA的创建部门；否则应该用当前登陆人的
			waybillEntity.setModifyOrgCode(StringUtil.defaultIfNull(dept.getCode()));
			// zxy 20130926 BUG-55867 end
			// 新增：[修改部门]改成当前登陆用户部门，之前：如果PDA单补录，用PDA的创建部门；否则应该用当前登陆人的
		}

		// 设置是否有效
		waybillEntity.setActive(FossConstants.ACTIVE);
		
		//PDA扫描上传的收货部门信息更新置运单实体
		updateReceiveOrgCodeFromPda(waybillEntity);
		
		// 新增历史客户
		addHistoryCustomer(waybill, systemDto);

		String waybillId = "";
		// 判断是否是PDA补录\
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillEntity.getPendingType())) {
			// 若老运单号为空，则不允许提交
			if (StringUtils.isEmpty(oldWaybill)) {
				LOG.error("PDA补录时，老运单号不能为空！");
				throw new WaybillSubmitException("PDA补录时，老运单号不能为空！");
			}
			// 若PDA运单 ，设置运单开单时间为pda开单的那个时间
			waybillEntity.setBillTime(waybillEntity.getBillTime());
			// 设置运单类型为PDA补录
			waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE);

			/**
			 * PDA提交时，t_srv_waybill、t_srv_actualFreiht、t_srv_labelGoods表即使没有插入成功
			 * PDA也能成功提交，因此还需要判断t_srv_waybill是否有值
			 */
			isPdaPending = isWayBillExsitsPDAPending(oldWaybill);
			if (isPdaPending) {
				// ISSUE-4480
				// 中转提供一个删除老的走货路径，重新生成新的走货路径的功能
				// 只有快递调用
				if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {
					WaybillEntity oldEntity = waybillDao.queryWaybillByNo(oldWaybill);
					if (!StringUtils.isEmpty(waybillEntity.getCustomerPickupOrgCode())
							&& oldEntity != null && !StringUtils.isEmpty(oldEntity.getCustomerPickupOrgCode())
							&& !waybillEntity.getCustomerPickupOrgCode().equals(oldEntity.getCustomerPickupOrgCode())) {








						// 老的目的站和新的补录以后目的站不同的时候 就调用该方法
						calculateTransportPathService.changeDestinationPathForRecordAgain(oldEntity.getWaybillNo(),
								waybillEntity.getCustomerPickupOrgCode());
					}
				}

				// 在更新运单状态前，保存是否PDA成功生成数据的状态
				// 根据运单信息

				waybillDao.updateWaybillByWaybillNo(waybillEntity, oldWaybill);
				/**
				 * 根据运单号查询运单ID
				 * 
				 * 因为生成运单后没有返回ID，因此需要再查询一次后将ID返回并保存到waybillEntity中
				 * 提供给结算使用，否则会报错错误信息
				 */
				// 若为更新，则一定会有运单ID
				WaybillEntity newWaybillEntity = waybillDao.queryWaybillByNo(StringUtil.defaultIfNull(waybillEntity.getWaybillNo()));
				// 判断查询出的对象是否为空
				if (null == newWaybillEntity) {
					throw new WaybillSubmitException("\n运单ID未查询到！");
				} else {
					waybillId = newWaybillEntity.getId();
				}

			} else {
				waybillId = waybillDao.addWaybillEntity(waybillEntity);
			}
		} else {
			// 设置统一的运单开单时间
			waybillEntity.setBillTime(systemDto.getBillTime());
			// 设置运单类型为PC开单
			waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE);

			waybillId = waybillDao.addWaybillEntity(waybillEntity);
		}

		// 设置运单ID
		waybill.getWaybillEntity().setId(waybillId);

		return isPdaPending;
	}

	/**
	 * PDA扫描添加的收货部门信息更新至运单实体
	 * @author 283250-foss-liuyi
	 */
	private void updateReceiveOrgCodeFromPda(WaybillEntity waybillEntity) {
		if(waybillEntity==null||StringUtils.isEmpty(waybillEntity.getWaybillNo())){
			return;
		}
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
		pdaScanQueryDto.setWaybillNo(waybillEntity.getWaybillNo());
		pdaScanQueryDto.setActive(FossConstants.YES);
		pdaScanQueryDto.setWaybillType(WaybillConstants.EWAYBILL);
		pdaScanQueryDto.setScanType(WaybillConstants.SCAN_TYPE_SACN);
		pdaScanQueryDto.setWhetherComplete(FossConstants.YES);
		pdaScanQueryDto.setTaskType(WaybillConstants.STATUS_PICKUP);
		List<PdaScanEntity> pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
		String receiveOrgCode="";
		if(CollectionUtils.isNotEmpty(pdaScanList)){
			for(PdaScanEntity pdaSum : pdaScanList){
				if(StringUtils.isNotEmpty(pdaSum.getReceiveOrgCode())){
					//waybillEntity.setReceiveOrgCode(pdaSum.getReceiveOrgCode());
					//String receiveOrgName = waybillDao.queryReceiveOrgNameByWayno(pdaSum.getReceiveOrgCode());
					//waybillEntity.setReceiveOrgName(receiveOrgName);
					receiveOrgCode=pdaSum.getReceiveOrgCode();
					break;
				}
			}
		}
		//获取合同实体
		//CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(waybillEntity.getDeliveryCustomerCode());
		//如果pda上传数据为空，就没有必要进行下面数据库操作， 对deliveryCustomerCode非空校验避免异常
		CusBargainEntity cusBargainEntity=
				(StringUtils.isEmpty(receiveOrgCode)||StringUtils.isEmpty(waybillEntity.getDeliveryCustomerCode()))
					?null:cusBargainService.queryCusBargainByCustCode(waybillEntity.getDeliveryCustomerCode());
		if(null == cusBargainEntity){
			return;
		}
		//合同适用部门List
		List<BargainAppOrgEntity> applicateOrgEntityList = bargainAppOrgService.queryAppOrgByBargainCrmId(cusBargainEntity.getCrmId(),null);
		//合同适用部门编码List
		List<String> applicateOrgCodeList = new ArrayList<String>();
		OrgAdministrativeInfoEntity applicateOrg = null;
		if(CollectionUtils.isNotEmpty(applicateOrgEntityList)){
			for (BargainAppOrgEntity bargainAppOrgEntity : applicateOrgEntityList) {
				if ( bargainAppOrgEntity.getUnifiedCode() != null && StringUtils.isNotEmpty(bargainAppOrgEntity.getUnifiedCode())) {
					applicateOrg = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCodeClean(bargainAppOrgEntity.getUnifiedCode());
					if (applicateOrg != null && StringUtils.isNotEmpty(applicateOrg.getCode())) {
						applicateOrgCodeList.add(applicateOrg.getCode());
					}
				}
			}
		}
		/**
		 * PDA扫描添加的收货部门,如果在合同适用部门集合内 则更新，否则不更新
		 * */
		for(String tempApplicateOrgCode : applicateOrgCodeList){
			if(StringUtils.isNotEmpty(tempApplicateOrgCode)&&tempApplicateOrgCode.equals(receiveOrgCode)){
				waybillEntity.setReceiveOrgCode(receiveOrgCode);
				String receiveOrgName = waybillDao.queryReceiveOrgNameByWayno(receiveOrgCode);
				waybillEntity.setReceiveOrgName(receiveOrgName);
				break;
			}
		}
	}

	/**
	 * 新增历史客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午10:43:17
	 */
	public void addHistoryCustomer(WaybillDto waybill, WaybillSystemDto systemDto) {
		WaybillEntity waybillEntity = waybill.getWaybillEntity();
		// 发货客户编码
		String deliverCustCode = StringUtil.defaultIfNull(waybillEntity.getDeliveryCustomerCode());
		// 收货客户编码
		String receiveCustCode = StringUtil.defaultIfNull(waybillEntity.getReceiveCustomerCode());

		// 捕捉异常信息
		try {
			/**
			 * 若发货客户编码为空，则说明为第一次发货 需要将该客户的相关信息录入到历史发货表中
			 */
			if (StringUtils.isEmpty(deliverCustCode)) {
				// 若为散客则新增立历史发货客户
				HisDeliveryCusEntity hisDeliveryCusEntity = gainHisDeliveryCusEntity(waybill, systemDto);
				if (!StringUtils.isEmpty(waybill.getActualFreightEntity().getDeliveryCustomerAddressNote())) {
					hisDeliveryCusEntity.setDeliveryCustomerAddressNote(waybill.getActualFreightEntity().getDeliveryCustomerAddressNote());
				}
				hisDeliveryCusService.addHisDeliveryCustomer(hisDeliveryCusEntity);
			}
			/**
			 * 若收货客户编码为空，则说明为第一次收货 需要将该客户的相关信息录入到历史收货表中
			 */
			if ("".equals(receiveCustCode)) {
				// 若为散客则新增立历史收货客户
				HisReceiveCusEntity hisReceiveCusEntity = gainHisReceiveCusEntity(waybill, systemDto);
				if (!StringUtils.isEmpty(waybill.getActualFreightEntity().getReceiveCustomerAddressNote())) {
					hisReceiveCusEntity.setReceiveCustomerAddressNote(waybill.getActualFreightEntity().getReceiveCustomerAddressNote());
				}
				hisReceiveCusService.addHisReceiveCustomer(hisReceiveCusEntity);
			}
		} catch (BusinessException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出历史客户生成失败的异常信息，中止程序继续运行
			throw new WaybillSubmitException(WaybillSubmitException.DELIVERCUSTCODE_FAIL,
					new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
		}
	}

	/**
	 * 封装发货历史客户
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午11:01:15
	 */
	private HisDeliveryCusEntity gainHisDeliveryCusEntity(
			WaybillDto waybill, WaybillSystemDto systemDto) {
		// 定义历史发客户对象
		HisDeliveryCusEntity delivery = new HisDeliveryCusEntity();
		// 发货客户手机号码
		delivery.setDeliveryCustomerMobilephone(waybill.getWaybillEntity().getDeliveryCustomerMobilephone());
		// 发货客户电话号码
		delivery.setDeliveryCustomerPhone(waybill.getWaybillEntity().getDeliveryCustomerPhone());
		// 发货联系人
		delivery.setDeliveryCustomerContact(waybill.getWaybillEntity().getDeliveryCustomerContact());
		// 发货国家
		delivery.setDeliveryCustomerNationCode(waybill.getWaybillEntity().getDeliveryCustomerNationCode());
		// 发货省份
		delivery.setDeliveryCustomerProvCode(waybill.getWaybillEntity().getDeliveryCustomerProvCode());
		// 发货城市
		delivery.setDeliveryCustomerCityCode(waybill.getWaybillEntity().getDeliveryCustomerCityCode());
		// 发货区域
		delivery.setDeliveryCustomerDisCode(waybill.getWaybillEntity().getDeliveryCustomerDistCode());
		// 发货地址
		delivery.setDeliveryCustomerAddress(waybill.getWaybillEntity().getDeliveryCustomerAddress());
		// 发货地址备注
		delivery.setDeliveryCustomerAddressNote(waybill.getActualFreightEntity().getDeliveryCustomerAddressNote());
		// 创建时间
		delivery.setCreateTime(systemDto.getCreateTime());

		// 返回封装的历史发货客户对象
		return delivery;
	}

	/**
	 * 封装收货历史客户
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 上午11:01:15
	 */
	private HisReceiveCusEntity gainHisReceiveCusEntity(
			WaybillDto waybill, WaybillSystemDto systemDto) {
		// 定义历史收货客户对象
		HisReceiveCusEntity receive = new HisReceiveCusEntity();
		// 收货客户手机号码
		receive.setReceiveCustomerMobilephone(waybill.getWaybillEntity().getReceiveCustomerMobilephone());
		// 收货客户电话号码
		receive.setReceiveCustomerPhone(waybill.getWaybillEntity().getReceiveCustomerPhone());
		// 收货联系人
		receive.setReceiveCustomerContact(waybill.getWaybillEntity().getReceiveCustomerContact());
		// 收货国家
		receive.setReceiveCustomerNationCode(waybill.getWaybillEntity().getReceiveCustomerNationCode());
		// 收货省份
		receive.setReceiveCustomerProvCode(waybill.getWaybillEntity().getReceiveCustomerProvCode());
		// 收货城市
		receive.setReceiveCustomerCityCode(waybill.getWaybillEntity().getReceiveCustomerCityCode());
		// 收货区域
		receive.setReceiveCustomerDisCode(waybill.getWaybillEntity().getReceiveCustomerDistCode());
		// 收货地址
		receive.setReceiveCustomerAddress(waybill.getWaybillEntity().getReceiveCustomerAddress());
		// 收货地址备注
		receive.setReceiveCustomerAddressNote(waybill.getActualFreightEntity().getReceiveCustomerAddressNote());
		// 创建时间
		receive.setCreateTime(systemDto.getCreateTime());
		// 返回封装的历史收货客户对象
		return receive;
	}

	/**
	 * 新增签收单返单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午4:18:00
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#addSignReturnBill()
	 */
	@Override
	public void addSignReturnBill(WaybillDto waybillDto) {
		// 从运单DTO对象中获得运单基本信息对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();

		// 获得创建人名字
		String createUserName = StringUtil.defaultIfNull(waybillEntity.getCreateUserName());
		// 获得创建组织名
		String createOrgName = StringUtil.defaultIfNull(waybillEntity.getCreateUserDeptName());
		if (StringUtils.isEmpty(createOrgName)) {
//			createOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getCreateOrgCode());
			createOrgName = queryOrgNameMapsByCodes(waybillEntity.getCreateOrgCode());
			waybillEntity.setCreateUserDeptName(createOrgName);
		}
		// 对数据操作进行异常捕捉
		try {
			// 非空判断
			if (!StringUtil.isEmpty(StringUtil.defaultIfNull(waybillEntity.getReturnBillType()))) {
				ReturnBillProcessEntity returnBill = new ReturnBillProcessEntity();
				// 运单号
				returnBill.setWaybillNo(waybillEntity.getWaybillNo());
				// 返单类型
				returnBill.setReturnbillType(waybillEntity.getReturnBillType());
				// 创建时间
				returnBill.setCreateTime(new Date());
				// 创建人工号
				returnBill.setCreateUserCode(waybillEntity.getCreateUserCode());
				// 创建组织编码
				returnBill.setCreateOrgCode(waybillEntity.getCreateOrgCode());
				// 返单状态
				returnBill.setReturnBillStatus(ReturnBillProcessConstants.NONE_RETURN_BILL);
				// 返单类型
				returnBill.setReturnbillType(waybillEntity.getReturnBillType());
				// 创建人名称
				returnBill.setCreateUserName(createUserName);
				// 创建组织名称
				returnBill.setCreateOrgName(createOrgName);
				// 对签收单信息进行持久化操作
				returnBillProcessDao.insertSelective(returnBill);
			}
		} catch (BusinessException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出生成签收单返单失败的异常信息
			throw new WaybillSubmitException(WaybillSubmitException.SIGNRETURNBILL_FAIL,
					new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
		}
	}

	/**
	 * 新增运单完整信息（包括基本信息、付款信息、费用信息、打木架信息、折扣信息）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:29:32
	 */
	@Transactional
	public boolean addWaybill(WaybillDto waybillDto,
			WaybillSystemDto systemDto, boolean isPdaPending) {
		// 生成全整运单信息
		try {
			// 付款信息
			List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();
			// 费用信息
			List<WaybillChargeDtlEntity> chargeDetail = waybillDto.getWaybillChargeDtlEntity();
			// 折扣信息
			List<WaybillDisDtlEntity> discoutDetail = waybillDto.getWaybillDisDtlEntity();
			// 打木架信息
			WoodenRequirementsEntity wooden = waybillDto.getWoodenRequirementsEntity();

			// 生成运单基本信息
			isPdaPending = this.addWaybillEntityWithPdaPending(waybillDto, systemDto, isPdaPending);

			// 折扣明细不为空
			if (CollectionUtils.isNotEmpty(discoutDetail)) {
				// 作废DPA同步时保存的营销活动信息
				updateInactiveByWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
				// 批量新增折扣明细信息
				waybillDisDtlService.addWaybillDisDtlEntityBatch(discoutDetail, systemDto);
			}

			// 费用明细不为空
			if (CollectionUtils.isNotEmpty(chargeDetail)) {
				// 批量新增费用明细信息
				waybillChargeDtlService.addWaybillChargeDtlEntityBatch(chargeDetail, systemDto);
			}

			// 付款明细不为空
			if (CollectionUtils.isNotEmpty(paymentList)) {
				// 批量新增付款明细信息
				waybillPaymentService.addWaybillPaymentEntityBatch(paymentList, systemDto);
			}

			// 打木架信息不为空
			if (wooden != null) {
				// 新增一条打木架信息
				woodenRequirementsService.addWoodenRequirements(wooden, systemDto);
			}
		} catch (BusinessException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出运单生成失败的异常信息，并中止程序继续运行
			throw new WaybillSubmitException(WaybillSubmitException.ADDWAYBILLINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
		}
		return isPdaPending;
	}

	/**
	 * 激活大客户,此处只生成运单基本信息
	 * 
	 * @author 026123-foss-liyongfei
	 * @date 2012-11-8 下午5:29:32
	 */
	public boolean addEWaybill(WaybillDto waybillDto,
			WaybillSystemDto systemDto, boolean isPdaPending) {
		// 生成全整运单信息
		try {
			// 付款信息
			List<WaybillPaymentEntity> paymentList = waybillDto
					.getWaybillPaymentEntity();
			// 费用信息
			List<WaybillChargeDtlEntity> chargeDetail = waybillDto
					.getWaybillChargeDtlEntity();
			// 折扣信息
			List<WaybillDisDtlEntity> discoutDetail = waybillDto
					.getWaybillDisDtlEntity();
			// 打木架信息
			WoodenRequirementsEntity wooden = waybillDto
					.getWoodenRequirementsEntity();

			// 生成运单基本信息
			isPdaPending = this.addWaybillEntityWithEWaybill(waybillDto,
					systemDto, isPdaPending);
			// 折扣明细不为空
			if (CollectionUtils.isNotEmpty(discoutDetail)) {
				waybillDisDtlService.addWaybillDisDtlEntityBatch(discoutDetail,
						systemDto);
			}

			// 费用明细不为空
			if (CollectionUtils.isNotEmpty(chargeDetail)) {
				// 批量更新费用明细信息
				waybillChargeDtlService.addWaybillChargeDtlEntityBatch(
						chargeDetail, systemDto);
			}

			// 付款明细不为空
			if (CollectionUtils.isNotEmpty(paymentList)) {
				// 批量新增付款明细信息
				waybillPaymentService.addWaybillPaymentEntityBatch(paymentList,
						systemDto);
			}

			// 打木架信息不为空
			if (wooden != null) {
				// 新增一条打木架信息
				woodenRequirementsService.addWoodenRequirements(wooden,
						systemDto);
			}
		} catch (BusinessException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出运单生成失败的异常信息，并中止程序继续运行
			throw new WaybillSubmitException(
					WaybillSubmitException.ADDWAYBILLINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getErrorCode()
							+ "\n" + e.getMessage()) });
		}
		return isPdaPending;
	}

	/**
	 * 
	 * 应用监控数据添加
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-21 下午04:19:20
	 */
	@Override
	public void businessMonitor(WaybillDto waybillDto) {
		// 运单信息
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		// 定义map集合
		Map<BusinessMonitorIndicator, Number> indicators = new HashMap<BusinessMonitorIndicator, Number>();

		// 提货方式监控
		receiveMethodMonitor(waybill, indicators);

		// 运输性质监控
		transportTypeMonitor(waybill, indicators);

		// 付款方式监控
		paidMethodMonitor(waybill, indicators);

		// 金额监控
		amountMonitor(waybill, indicators);

		// 监控计数器
		businessMonitorService.counter(indicators, waybillDto.getCurrentInfo());

	}

	/**
	 * 
	 * 提货方式监控
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-21 下午04:47:35
	 */
	private void receiveMethodMonitor(WaybillEntity waybill,
			Map<BusinessMonitorIndicator, Number> indicators) {
		String code = waybill.getReceiveMethod();
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code)
				|| WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)) {
			// 开单自提票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_PICKUP_COUNT, 1);
		} else {
			// 开单送货票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_DELIVERY_COUNT, 1);
		}
	}

	/**
	 * 
	 * 运输性质监控
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-21 下午04:48:59
	 */
	private void transportTypeMonitor(WaybillEntity waybill,
			Map<BusinessMonitorIndicator, Number> indicators) {
		// 精准卡航和精准城运 --精准
		if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(waybill.getProductCode())
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(waybill.getProductCode())) {
			// 开单为精准票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_RECISION_COUNT, 1);
		}// 精准汽运（长）和精准汽运（短） -- 普货
		else if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(waybill.getProductCode())
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(waybill.getProductCode())) {
			// 开单为普货票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_COMMON_COUNT, 1);
		}// 精准空运 -- 空运
		else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
			// 开单为空运票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_AIR_COUNT, 1);
		}// 汽运偏线-- 偏线
		else if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())) {
			// 开单为偏线票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_PARTIAL_COUNT, 1);
		}// 整车-- 整车
		else if (ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(waybill.getProductCode())) {
			// 开单为整车票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_WHOLE_COUNT, 1);
		}
	}

	/**
	 * 
	 * 付款方式监控
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-21 下午04:50:22
	 */
	private void paidMethodMonitor(WaybillEntity waybill,
			Map<BusinessMonitorIndicator, Number> indicators) {
		// 现金
		if (WaybillConstants.CASH_PAYMENT.equals(waybill.getPaidMethod())) {
			// 开单为现付票数计加1
			indicators.put(BusinessMonitorIndicator.BILLING_PREPAY_COUNT, 1);
		}// 到付
		else if (WaybillConstants.ARRIVE_PAYMENT.equals(waybill.getPaidMethod())) {
			// 开单为到付票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_TOPAY_COUNT, 1);
		}// 银行卡
		else if (WaybillConstants.CREDIT_CARD_PAYMENT.equals(waybill.getPaidMethod())) {
			// 开单为银行卡票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_CARD_COUNT, 1);
		}// 月结
		else if (WaybillConstants.MONTH_PAYMENT.equals(waybill.getPaidMethod())) {
			// 开单为月结票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_CREDIT_COUNT, 1);
		}// 临时欠款
		else if (WaybillConstants.TEMPORARY_DEBT.equals(waybill.getPaidMethod())) {
			// 开单为临时欠款票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_DEBT_COUNT, 1);
		}// 网上支付
		else if (WaybillConstants.ONLINE_PAYMENT.equals(waybill.getPaidMethod())) {
			// 开单为网上支付票数累计加1
			indicators.put(BusinessMonitorIndicator.BILLING_ONLINE_COUNT, 1);
		}
	}

	/**
	 * 
	 * 金额类别监控（开单总金额、开单保价金额、开单代收货款金额）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-21 下午05:03:52
	 */
	private void amountMonitor(WaybillEntity waybill,
			Map<BusinessMonitorIndicator, Number> indicators) {
		// 开单总金额
		indicators.put(BusinessMonitorIndicator.BILLING_TOTAL_AMOUNT, waybill.getTotalFee());

		// 开单保价金额
		indicators.put(BusinessMonitorIndicator.BILLING_INSURANCE_AMOUNT, waybill.getInsuranceAmount());

		// 开单代收货款金额
		if (waybill.getCodAmount() != null && waybill.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
			// 开单代收货款金额
			indicators.put(BusinessMonitorIndicator.BILLING_COD_AMOUNT, waybill.getCodAmount());
		}

	}
	
	/**
	 * 只查询机构名称、机构组织 名称
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-18
	 * @param orgCode
	 * @return
	 */
	private String queryOrgNameMapsByCodes(String  orgCode){
		String orgName = "";
		if (!StringUtil.isEmpty(orgCode)) {
			if(orgCodeNameMap.get(orgCode) !=null){
				orgName = orgCodeNameMap.get(orgCode);
			}else{
				Map<String,String> map = queryOrgNameMapsByCodesMap(orgCode);
				orgName = map.get(orgCode) == null ? "" : map.get(orgCode);
			}
		}
		return orgName;
	}
	
	
	/**
	 *  只查询机构名称、机构组织 集合
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-20
	 * @param orgCodes
	 * @return
	 */
	private Map<String,String> queryOrgNameMapsByCodesMap(String ...orgCodes){
		if (orgCodes != null && orgCodes.length > 0) {
			Map<String, String> maps =orgAdministrativeInfoService.queryOrgNameMapsByCodes(Arrays.asList(orgCodes));
			if(maps != null){
				orgCodeNameMap.putAll(maps);
			}
		}
		return orgCodeNameMap;
	}

	/**
	 * 运单提交
	 * 
	 * @param waybill
	 * @return Result
	 */
	@Transactional
	@Override
	public void submitWaybill(final WaybillDto waybillDto) {

		// 定义运单基本信息对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		// 根据运输性质设置是否快递字段的值
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {

			waybillDto.setIsExpress(FossConstants.YES);
		} else {
			waybillDto.setIsExpress(FossConstants.NO);
		}
		// 校验运单是否符合业务规则
		verifyWaybill(waybillDto);
		
		/*String guiTitleName = waybillDto.getGuiTitleName();
		if(WaybillConstants.YES.equals(waybillDto.getIsGuiSubmit()) && guiTitleName != null && "图片开单".equals(guiTitleName)){
			String waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
			final WaybillPictureEntity entity = new WaybillPictureEntity();
			entity.setWaybillNo(waybillNo);
			entity.setActive(FossConstants.ACTIVE);
			
			//查询老图片暂存信息
			final WaybillPictureEntity currEntity = waybillPendingService.queryWaybillPictureByEntity(entity);
			if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(currEntity.getPendgingType())){//直接图片补录体积开单
				entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_THREAD);
				waybillPendingService.updatePictureWaybillByWaybillno(entity);
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.currentThread();//让当前线程等待500毫秒
						Thread.sleep(WaybillConstants.PIC_THREAD_SLEEP_TIME);
						
						//在线提交接口抽取
						submitWaybillCommon(waybillDto);
					} catch (Exception e) {//异常处理
						e.printStackTrace();
						
						String errorMsg  = e.getMessage();
						StringBuffer sb = new StringBuffer();
						String remark = currEntity.getRemark();
						//获取备注信息  防止暂存图片修复信息 没有及时更新备注信息
						if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(currEntity.getPendgingType())){
							entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
						} 
						if(remark != null){
							sb.append(remark);
							sb.append("/n");
						}
						sb.append(errorMsg).append("  ").append(new Date());
						entity.setRemark(sb.toString());
						//更新图片开单表备注信息
						waybillPendingService.updatePictureWaybillByWaybillno(entity);
					}
				}
				
			}).start();
			
		}else{
		}*/
		submitWaybillCommon(waybillDto);
		try {
			CustomerAddressFossRequest dto=new CustomerAddressFossRequest();
			
			dto.setDistrictCode(waybillDto.getWaybillEntity().getReceiveCustomerDistCode());
			AdministrativeRegionsEntity  districtEntity= areaAddressService.queryRegionByCode(waybillDto.getWaybillEntity().getReceiveCustomerDistCode());
			dto.setDistrict(districtEntity.getName());
			
			dto.setCityCode(waybillDto.getWaybillEntity().getReceiveCustomerCityCode());
			dto.setCity(districtEntity.getParentDistrictName());
			
			dto.setProvinceCode(waybillDto.getWaybillEntity().getReceiveCustomerProvCode());
			AdministrativeRegionsEntity  proviceEntity= areaAddressService.queryRegionByCode(waybillDto.getWaybillEntity().getReceiveCustomerCityCode());
			dto.setProvince(proviceEntity.getParentDistrictName());
			dto.setStateCode(waybillDto.getWaybillEntity().getReceiveCustomerNationCode());
//			dto.setState(null);
			dto.setContactAddress(waybillDto.getWaybillEntity().getReceiveCustomerAddress());
			dto.setCustomerMobilePhone(waybillDto.getWaybillEntity().getReceiveCustomerMobilephone());
			dto.setCustomerPhone(waybillDto.getWaybillEntity().getReceiveCustomerPhone());
			dto.setAddressTime(new Date());
//			dto.setStreet("洞庭湖路");
//			dto.setStreetCode(null);
//			dto.setTownship("123");
//			dto.setTownshipCode(null);
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_FOSS2ESB_FOSS_ADDRESS);
			accessHeader.setBusinessDesc1("图片开单提交地址信息到地址库");
			accessHeader.setBusinessDesc2(UUIDUtils.getUUID());
			accessHeader.setVersion("0.1");
			accessHeader.setBusinessId(sdf.format(date));
//			String requestContent = JSON.toJSONString(dto);
			ESBJMSAccessor.asynReqeust(accessHeader, dto);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("piciureWayBill to addressINfo Exception ! ");
		}
		
	}	
	
	/**
	 * 在线提交接口抽取
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-30
	 * @param waybillDto
	 */
	@Transactional
	private void submitWaybillCommon(WaybillDto waybillDto){
		if(waybillDto == null){
			throw new WaybillValidateException("方法submitWaybillCommon-实体类waybillDto为空！");
		}
		
		boolean isPdaPending = false;
		
		/**
		 * @项目：智能开单项目
		 * @功能：提交耗时时间任务
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19下午18:19
		 */
		if(waybillDto.getIbtg()!=null){
			addIntelligenceBillTime(waybillDto);
		}
		
		// 定义运单基本信息对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
	
		// 获取运单处理状态
		String waybillStatus = StringUtil.defaultIfNull(waybillDto.getWaybillEntity().getPendingType());

		// 设置统一的创建人、创建时间、更新人、更新时间
		WaybillSystemDto systemDto = new WaybillSystemDto();
		// 设置当前用户信息
		CurrentInfo currentInfo = waybillDto.getCurrentInfo();
		systemDto.setCurrentUser(currentInfo);

		/**
		 * 缓存机构名称
		 * @author Foss-278328-hujinyang
		 * @TIME 20160422
		 */
		queryOrgNameMapsByCodesMap(waybillEntity.getCreateOrgCode(), waybillEntity.getReceiveOrgCode());
		
		// 设置修改时间
		systemDto.setModifyTime(new Date());
		// 设置创建时间
		if (WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING.equals(waybillStatus)) {
			// 若为离线导入开单，则使用离线客户端本地时间
			Date createTime = waybillDto.getWaybillEntity().getCreateTime();
			// 判断创建时间是否为空
			if (null == createTime) {
				createTime = new Date();
			}
			systemDto.setCreateTime(createTime);
		} else {
			systemDto.setCreateTime(new Date());
		}

		// 设置开单时间
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			WaybillPendingEntity pend = waybillPendingService
					.getWaybillPendingEntityByWaybillNo(waybillDto
							.getOldWaybillNo());
			if (pend != null) {
				// 若为PDA导入开单，则使用PDA时间
				Date billTime = waybillDto.getWaybillEntity().getBillTime();
				// 判断开单时间是否为空
				if (null == billTime) {
					billTime = new Date();
				}
				systemDto.setBillTime(billTime);
				if (null != waybillDto.getWaybillEntity()) {
				   waybillDto.getWaybillEntity().setBillTime(billTime);
				}
			} else {
				systemDto.setBillTime(new Date());
				if (null != waybillDto.getWaybillEntity()) {
				waybillDto.getWaybillEntity().setBillTime(new Date());
			  }
			}
		} else {
			systemDto.setBillTime(new Date());
			if (null != waybillDto.getWaybillEntity()) {
			  waybillDto.getWaybillEntity().setBillTime(new Date());
		  }
		}

		setExpChangeVolume(waybillDto);
		
		// 修改后的运输性质
		String productCode = waybillEntity.getProductCode();

		if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD, productCode)) {
			waybillEntity.setLastLoadOrgCode(waybillEntity.getCustomerPickupOrgCode());
			waybillEntity.setLastLoadOrgName(waybillEntity.getCustomerPickupOrgName());
		}
		if (StringUtils.isEmpty(waybillDto.getWaybillEntity().getCustomerPickupOrgName())) {
			String prdCode = waybillDto.getWaybillEntity().getProductCode();
			String orgcode = waybillDto.getWaybillEntity().getCustomerPickupOrgCode();
			if (!StringUtils.isEmpty(orgcode)) {
				String customerPickupOrgName = waybillDao.queryCustomerPickupOrgNameByWayno(orgcode, prdCode);
				waybillDto.getWaybillEntity().setCustomerPickupOrgName(customerPickupOrgName);
			}
		}
		if (StringUtils.isEmpty(waybillDto.getWaybillEntity().getReceiveOrgName())) {
			String orgcode = waybillDto.getWaybillEntity().getReceiveOrgCode();
//					String receiveOrgName = waybillDao.queryReceiveOrgNameByWayno(orgcode);
			/**
			 * @author Foss-278328-hujinyang
			 * @date 20160421
			 */
			String receiveOrgName = queryOrgNameMapsByCodes(orgcode);
			waybillDto.getWaybillEntity().setReceiveOrgName(receiveOrgName);
		}

		// 重新赋值
		if (StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
			/**
			 * DEFECT-824 部分营业部反馈客户网单无法在线跟踪运单详情
			 */
			ActualFreightEntity actualFreightEntity = waybillDto.getActualFreightEntity();
			if (actualFreightEntity != null) {
				// 若运单绑定的订单为官网订单，且运单状态为待补录的状态时，需要通过订单号查一遍订单信息，将官网登陆名查询出来
				if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE
						.equals(waybillEntity.getOrderChannel())
						&& StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
					// 待补录状态
					if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING
							.equals(waybillStatus)
							|| WaybillConstants.WAYBILL_STATUS_PC_PENDING
									.equals(waybillStatus)) {
						// 根据订单号查询订单信息
						CrmOrderDetailDto orderDetailVo = crmOrderService
								.importOrder(StringUtil
										.defaultIfNull(waybillEntity
												.getOrderNo()));
                        //抽取方法
                        setWaybillTypeMethod(waybillDto, waybillEntity, actualFreightEntity, orderDetailVo);

                    }
				}
			}
			waybillDto.setActualFreightEntity(actualFreightEntity);
		}

		// zxy 20130916 BUG-54827 start 新增：运单如果失效且是6月1号前导入的数据，则删除ActualFreight数据
		String waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
		// 根据运单号查询
		if (actualFreightService.isExistActualFreight(waybillNo)) {
			WaybillEntity we = waybillDao.queryRecentWaybillByNo(waybillNo);
			// 如果此运单存在ActualFreight，先判断此运单是否失效且是系统上线前导入的数据，如果是则可以删除ActualFreight
			if (we == null
					|| (FossConstants.NO.equals(we.getActive()) && we
							.getBillTime().before(
									DateUtils.convert("2013-06-01")))) {
				actualFreightService.deleteActualFreightByWaybillNo(waybillNo);
			}
		}
		// zxy 20130916 BUG-54827 end 新增：运单如果失效且是6月1号前导入的数据，则删除ActualFreight数据

		/**
		 * MANA-1937 系统开发生成客户编码 026123 2014-03-24
		 */
		procDeliverAndReceiveCustomer(waybillDto);

		// 生成运单完整信息
		isPdaPending = addWaybill(waybillDto, systemDto, isPdaPending);

		waybillEntity = waybillDto.getWaybillEntity();

		// 是否为整车，若为整车则不生成中转线路信息
		if (FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
			LOG.info("submitWaybill:进入整车生成库存部分");
			// 生成货签信息和库存信息
			addLabeledGood(waybillDto, systemDto, isPdaPending);
		} else {
			LOG.info("submitWaybill:进入生成库存部分");
			
			// 中转接口：生成中转线路信息
			addLine(waybillDto, isPdaPending);
			// 生成货签信息和库存信息
			addLabeledGood(waybillDto, systemDto, isPdaPending);

		}

		// 若为签收单返单类型，则需要生成签收单信息
		if (!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())) {
			// 生成签收单信息
			addSignReturnBill(waybillDto);
		}

		// 当打木架信息不为空时，才调用中转接口,（并且木架木托木箱至少有一个不为0时才调用中转接口--lianhe--2017年3月10日14:12:51）
		if (waybillDto.getWoodenRequirementsEntity() != null 
				&&((waybillDto.getWoodenRequirementsEntity().getStandGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getStandGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum() != 0))) {
			// 中转接口：生成包装信息
			woodenRequirementsService.addPackagingRequire(waybillDto);
		}
	
		// 内部带货不调用财务接口（已与周斌文确认2013-04-17）
		String pickupMode = StringUtil.defaultIfNull(waybillEntity
				.getReceiveMethod());
		
		
		//调用cubc灰度接口
		VestBatchResult vestResult = this.getVestResult(waybillDto,"submitWaybill");
		//不为CUBC(灰度接口异常或者为FOSS)，都走FOSS
		if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
			//合伙人开空运时才会进入此方法，为结算生成到达应收单。
			if (!pickupMode.equals(WaybillConstants.INNER_PICKUP)
					&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(pickupMode)
					&& FossConstants.YES.equals(waybillDto.getActualFreightEntity().getPartnerBillingLogo())
					&& (WaybillConstants.AIR_FLIGHT.equals(waybillEntity.getProductCode())
					    	&&(BigDecimal.ZERO.compareTo(waybillEntity.getToPayAmount()) < 0)
					    )
					&& !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
			//合伙人生成调结算生成始发应收，此处不能在生成始发应收	因此将预付金额改为0
				addSettleBill(waybillDto);
			}
		}
		if (!pickupMode.equals(WaybillConstants.INNER_PICKUP)
				&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(pickupMode)
				&& (!FossConstants.YES.equals(waybillDto.getActualFreightEntity().getPartnerBillingLogo()) || FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()))
				&& !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
			/*if(null!=waybillDto&&
					null!=waybillDto.getActualFreightEntity()&&
					null!=waybillDto.getActualFreightEntity().getDcServicefee()){
				waybillDto.getWaybillEntity().setServiceFee(waybillDto.getActualFreightEntity().getDcServicefee());
			}*/
				
				// 结算接口：生成结算相关财务单据
				//addSettleBill(waybillDto);
                //调用这个方法，原方法被许多情况调用
                addSettleBillForCUBC(waybillDto);

			/*if(null!=waybillDto&&
					null!=waybillDto.getActualFreightEntity()&&
					null!=waybillDto.getActualFreightEntity().getDcServicefee()){
				waybillDto.getWaybillEntity().setServiceFee(BigDecimal.ZERO);
			}*/
		}
		// 运单冗余信息新增
		addActualFreightInfo(waybillDto, systemDto, isPdaPending);
		
		// 新增特殊增值服务标示和相关数据  mabinliang-254615-foss
		addSpecialValueAddedServiceData(waybillDto);

		// 新增业务标识
		addWaybillTransactionStat(waybillDto);
		
		//付款方式选择为银行卡时，运单数据保存待刷卡运单数据表
		//PDA开单时不调用结算接口
		if (WaybillConstants.CREDIT_CARD_PAYMENT.equals(waybillEntity
				.getPaidMethod()) && !isPdaPending) {
			// liding add 新增待刷卡操作界面 NCI
			addWSCWayBillData(waybillEntity);
		}

		// 待补录运单信息
		WaybillPendingEntity waybillPendingEntity = null;
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			waybillPendingEntity = waybillPendingService.getWaybillPendingEntityByWaybillNo(waybillDto.getOldWaybillNo());

			// zxy 20140211 MANA-1044 start 新增:PDA补录时修改了重量体积需要调用中转更新相关信息
//					if (!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())
			if (FossConstants.NO.equals(waybillDto.getIsExpress())
					&& waybillPendingEntity != null) {
				BigDecimal preVolume = waybillPendingEntity.getGoodsVolumeTotal();
				BigDecimal preWeight = waybillPendingEntity.getGoodsWeightTotal();
				BigDecimal curVolume = waybillEntity.getGoodsVolumeTotal();
				BigDecimal curWeight = waybillEntity.getGoodsWeightTotal();
				MakeUpWaybillEntity makeUpWaybillEntity = new MakeUpWaybillEntity();
				// 只要重量和体积有一个修改了就传给中转进行刷新
				if ((preVolume == null || preVolume.compareTo(curVolume) != 0)
						|| (preWeight == null || preWeight.compareTo(curWeight) != 0)) {
					makeUpWaybillEntity.setVolume(curVolume);
					makeUpWaybillEntity.setWeight(curWeight);
					makeUpWaybillEntity.setWaybillNo(waybillNo);
					makeUpWaybillEntity
							.setMakeUpTime(systemDto.getModifyTime());
					makeUpWaybillEntity.setQuantity(new BigDecimal(
							waybillEntity.getGoodsQtyTotal()));
					// 通知中转重量体积变更，将变更信息放入中转跑批表
					makeUpWaybillService
							.addWaybillInfoForTfr(makeUpWaybillEntity);
				}
			}
		}

		// 图片直接开单 需添加暂存表供完成接货卸车操作
		WaybillPendingEntity billPendingEntity = waybillPendingDao
				.queryPendingByNo(waybillNo);

		// 判断是否是PDA补录或者运单暂存补录
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)
				|| WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillStatus)) {
			// 更新待处理运单记录信息
			if (!(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus) && billPendingEntity == null)){
					updatePendingData(waybillDto, waybillStatus);
			}
		}

		// 是否为弃货处理运单
		if (WaybillConstants.WAYBILL_STATUS_GOODS_PENDING.equals(waybillStatus)) {
			// 更新弃货处理运单记录信息，标识待弃货运单为N,更新弃货运单号储运事项为新运单号
			updateAbandonedGoodsData(waybillDto.getWaybillEntity());
		}
		
		// 待补录运单信息
		WaybillPendingEntity waybillpending = waybillPendingService.queryWaybillPendingEntityNo(waybillDto.getOldWaybillNo());
		if (waybillpending != null) {
			waybillDto.setReceiveOrgCode(waybillpending.getCreateOrgCode());
		}
		// 处理待办事务
		handleToDoMsg(waybillDto);
		// 保存待处理发送短信信息，执行异步定时任务
		savePendingSendMailInfo(waybillDto);

		// 只针对精准汽运（长/短途）、精准卡航、精准城运----保存待处理返券信息，执行异步定时任务----206860
		if (waybillDto != null) {// Online-定价体系优化项目DJTWO-63
			if (WaybillConstants.LRF_FLIGHT.equals(waybillDto
					.getWaybillEntity().getProductCode())
					|| WaybillConstants.SRF_FLIGHT.equals(waybillDto
							.getWaybillEntity().getProductCode())
					|| WaybillConstants.TRUCK_FLIGHT.equals(waybillDto
							.getWaybillEntity().getProductCode())
					|| WaybillConstants.FSF_FLIGHT.equals(waybillDto
							.getWaybillEntity().getProductCode())
					|| WaybillConstants.EC_GOODS.equals(waybillDto
							.getWaybillEntity().getProductCode())) {
				// 只有发货客户存在手机号码时，才做降价返券处理...
				if (StringUtil.isNotEmpty(waybillDto.getWaybillEntity()
						.getDeliveryCustomerMobilephone())) {
					// 当提货方式不为内部带货时，才做降价返券处理
					if (StringUtil.isNotBlank(waybillDto.getWaybillEntity()
							.getReceiveMethod())) {
						if (!waybillDto.getWaybillEntity().getReceiveMethod()
								.contains("INNER")) {
							makePendingSendCoupon(waybillDto);
						}
					}
				}
			}
		}
		// 更新约车状态
		updateVehicleState(waybillDto);

		// 判断优惠券对象是否为空
		if (waybillDto.getCouponInfoDto() != null) {
			// 使用优惠券
			useCoupon(waybillDto);
		}

		// 快递冗余信息
		if (waybillDto.getWaybillExpressEntity() != null) {
			addWaybillExpress(waybillDto, systemDto);
		}

		// 释放业务锁

		// unlock(waybillEntity);

		// 增加定人定区地址(放在最后面)
		addReceiveAddressRfc(waybillEntity);		// 如果是PDA补录的单，则判断是否需要生成待办（和更改单处理待办逻辑基本一致，只是PDA补录的单，如果修改了信息，更改状态都是自动审核、受理同意）
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus) && waybillPendingEntity != null) {
			// 处理PDA补录单的待办信息
			handlePDAPendingTODO(waybillDto, waybillPendingEntity);
			// 更新GUI中PDA待补录信息
			updateTodoMsgStatus(waybillDto);
		}

		// 图片信息
		WaybillPictureEntity pictureEntity = new WaybillPictureEntity();
		pictureEntity.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
		pictureEntity.setActive(FossConstants.ACTIVE);
		// 查询图片休息
		List<WaybillPictureEntity> pictureEntitys = waybillPictureDao.queryWaybillPictureByEntity(pictureEntity);
		if (CollectionUtils.isNotEmpty(pictureEntitys)) {
			WaybillPictureEntity entity = pictureEntitys.get(0);
			WaybillPictureEntity waybillPictureEntity = new WaybillPictureEntity();
			waybillPictureEntity.setId(entity.getId());
			waybillPictureEntity
					.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);
			// 如果是foss端开单员手动导入的订单那需要把订单号存入图片开单信息表中
			waybillPictureEntity.setOrderNo(waybillEntity.getOrderNo());
			waybillPictureEntity.setRemark("");
			if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(entity.getPendgingType())){				
				waybillPictureEntity.setEndBillTime(new Date());

			} else {
				waybillPictureEntity.setModifyTime(new Date());
			}
			/***新分单逻辑上传图片时 非【现金】【空运】运单未录入 是否本地和所属开单组，提交运单时添加。 2017年3月17日19:23:47 by 352676***********/
			//获取暂存运单 orgCode
			//获取登录用户所在的开单部门
			if(entity != null){
			UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
			String belongOrgCode=user.getEmployee().getDepartment().getCode();
			//判断是否本地开单
			if(StringUtil.isBlank(entity.getLocal())
					|| StringUtil.isBlank(entity.getOperator())
					|| StringUtil.isBlank(entity.getBelongOrgCode())){
				waybillPictureEntity.setBelongOrgCode(belongOrgCode);
				waybillPictureEntity.setOperator(user.getUserName());
				//判断本地开单组和当前部门是否相等
				if(StringUtil.equals(belongOrgCode, entity.getLocalBillGroupCode())){
					waybillPictureEntity.setLocal("Y");
				}else{
					waybillPictureEntity.setLocal("N");
				}
			}//判断当前开单人是否提交运单本人，解决一单分两人提交运单的开单人不一致情况
			else{
				if(!StringUtil.equals(user.getUserName(), entity.getOperator())
						&&
						WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(entity.getPendgingType())){
					waybillPictureEntity.setBelongOrgCode(belongOrgCode);
					waybillPictureEntity.setOperator(user.getUserName());
					// 判断本地开单组和当前部门是否相等
					if (StringUtil.equals(belongOrgCode,entity.getLocalBillGroupCode())) {
						waybillPictureEntity.setLocal("Y");
					} else {
						waybillPictureEntity.setLocal("N");
					}
				}
			}
			}
			/*************补录暂存时添加 是否本地和所属开单组 END******************/
			waybillPictureDao.updateWaybillPicture(waybillPictureEntity);

			/**
			 * 图片直接开单 需添加暂存表供完成接货卸车操作
			 */

			// 交接单明细
			StayHandoverDetailEntity stayHandover = stayHandoverDetailDao.queryByWaybillNo(waybillNo);
			// 是图片开单，如果没有待补录信息和交接单信息，添加暂存表供完成接货卸车操作
			if (billPendingEntity == null && stayHandover == null) {
				WaybillPendingEntity pendingEntity = new WaybillPendingEntity();
				BeanUtils.copyProperties(waybillEntity, pendingEntity);
				pendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
				pendingEntity.setHandoverStatus(FossConstants.NO);
				pendingEntity.setPackageRemark("M;");
				pendingEntity.setDriverCode(entity.getDriverCode());
				waybillPendingDao.insertSelective(pendingEntity);

				// 图片直接开单，推送货物轨迹
				if (!WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(entity
						.getPendgingType())) {
					// 推送货物轨迹
					addSynTrack(waybillDto);
				}
			}

			/**
			 * 图片直接开单，添加标签信息为为PDA卸车时获取流水号信息 添加pkp.t_srv_labeled_good_pda
			 */
			List<LabeledGoodPDAEntity> labelList = labeledGoodPDAService.queryByWaybillNo(waybillNo);
			if (CollectionUtils.isEmpty(labelList)) {
				// 插入
				addPDALabeledGood(waybillEntity);
			}

		}

		// 为配合快递100轨迹推送所需的方法
		addOneWaybillTrack(waybillDto);
		// 配合自动补码
		if (waybillDto.getWaybillEntity() != null
				&& WaybillConstants.YES.equals(waybillDto.getWaybillEntity()
						.getIsExpress())) {
			autoAddCode(waybillDto);
		}
		/**
		 * 解决批量开单流水号信息丢失 dp-foss-dongjialing 225131
		 */
		if (waybillDto.getActualFreightEntity() != null
				&& WaybillConstants.YES.equals(waybillDto
						.getActualFreightEntity().getIsBatchCreateWaybill())) {
			List<LabeledGoodEntity> list = labeledGoodService
					.queryAllSerialByWaybillNo(waybillDto
							.getActualFreightEntity().getWaybillNo());
			if (CollectionUtils.isEmpty(list)) {
				List<LabeledGoodEntity> labels = addLabeledGood(
						waybillDto.getWaybillEntity(),
						waybillDto.getWoodenRequirementsEntity());
				if (CollectionUtils.isNotEmpty(labels)) {
					labeledGoodDao.insertBatch(labels);
				}
			}
			/**
			 * 批量开单删除运单异常信息
			 * dp-foss-dongjialing
			 * 225131
			 */
			if(waybillDto.getWaybillEntity() != null && WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillDto.getWaybillEntity().getPendingType())) {				
				if(null != billPendingEntity && WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(billPendingEntity.getPendingType())) {
					waybillPendingService.deleteByWaybillNo(waybillDto.getOldWaybillNo());
		}
			}
		}

		//如果特殊增值服务标识不为空，且 为（家具送装，建材送装，家电送装）中的一种，则保存家装运单信息
		if(!StringUtil.isBlank(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) &&
				(WaybillConstants.FURNITURE_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) ||
				 WaybillConstants.BUILD_MATERIAL_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) ||
				 WaybillConstants.HOME_APPLICATION_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()))){
			waybillHomeImpService.saveWaybillHomeImpInfo(waybillDto);
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentWaybillNo",waybillNo);
		params.put("active",FossConstants.INACTIVE);
		//根据运单号查出子件信息
		waybillDto.setWaybillRelateDetailEntityList(waybillRelateDetailEntityService.queryWaybillRelateDetailListByOrderOrWaybillNo(params));
		//添加子母件数据的添
		submitWaybillRelateDetail(waybillDto, systemDto);
		//更新子母件状态位
		if(waybillDto != null && CollectionUtils.isNotEmpty(waybillDto.getWaybillRelateDetailEntityList())){
			//修改子母单关系表状态改为有效
			WaybillRelateDetailEntity waybillRelateDetail=new WaybillRelateDetailEntity();
			waybillRelateDetail.setActive(FossConstants.YES);
			waybillRelateDetail.setParentOrderNo(waybillNo);
			waybillRelateDetail.setModifyTime(new Date());
			waybillRelateDetailEntityService.updateWaybillRelateDetailByWaybillNoSelective(waybillRelateDetail);
		}
		
		//FOSS运单号同步给悟空，验证运单号是否存在(要确保推送ptp信息推送不会因为重复校验推送多次，必须写在PTPT推送前面，OMS异步接口之前)
		pushWaybillNo2Ecs(waybillDto);
		
		// 订单号是否为空,订单号不为空则更新订单状态
		if (StringUtil.isNotBlank(waybillDto.getWaybillEntity().getOrderNo())) {
			orderService.updateOrderInfo(waybillDto.getWaybillEntity());
		}
				
		//合伙人项目
		try {
			//判断是否需要推送信息(快递开单都需要推送，零担开单如果开单和目的地都不是合伙人不需要执行)
			if(!FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && waybillDto !=null && waybillDto.getPtpWaybillDto() !=null 
				&& (!StringUtils.equals(waybillDto.getPtpWaybillDto().getFeeType(), WaybillConstants.NEGATIVE_ONE)
					||WaybillConstants.YES.equals(waybillDto.getIsExpress()))){
				PtpWaybillDto ptpWaybillDto = ConvertBean.getPtpWaybillDto(waybillDto,currentInfo);
				//获取修改前的信息然后保存到foss
				PartnersWaybillEntity partnersWaybillEntity = getPartnersWaybillEntity(waybillDto,systemDto);
				//如果是快递开单都存入合伙人折前表				
				if(WaybillConstants.YES.equals(waybillDto.getIsExpress())){
					partnersWaybillService.addPartnersWaybillEntity(partnersWaybillEntity);
				}else{
					//零担（合伙人到达加收，FOSS端只负责零担）
					if(!WaybillConstants.NEGATIVE_ONE.equals(ptpWaybillDto.getFeeType())){
						//校验合伙人信息是否异常
						checkPartnerWaybillFee(waybillEntity,partnersWaybillEntity);
						partnersWaybillService.addPartnersWaybillEntity(partnersWaybillEntity);
					}
				}
				//出发部门标杆编码
				OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getReceiveOrgCode());
				if(orgReceiveEntity!=null){
					ptpWaybillDto.setReceiveDeptUnifieldCode(orgReceiveEntity.getUnifiedCode());
				}
				//到达部门标杆编码
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getArriveOrgCode());
				if(orgAdministrativeInfoEntity!=null){
					ptpWaybillDto.setArriveDeptUnifieldCode(orgAdministrativeInfoEntity.getUnifiedCode());
				}
				
				if(!StringUtils.equals(waybillDto.getPtpWaybillDto().getFeeType(), WaybillConstants.NEGATIVE_ONE)){
					//是否快递
					if(WaybillConstants.YES.equals(waybillDto.getIsExpress())){
						expWaybillInfoToPtpService.asynSendExpWaybillInfoToPtp(ptpWaybillDto);
					}else{//是零担开单
						//校验合伙人信息是否异常
						checkPartnerWaybillFee(waybillEntity, partnersWaybillEntity);
						waybillInfoToPtpService.asynSendWaybillInfoToPtp(ptpWaybillDto);
					}
				}
			}
		}catch(WaybillSubmitException w){
			throw new WaybillSubmitException(w.getMessage());
		}catch (Exception e) {
			LOG.error("合伙人项目 保存推送运单信息异常："+e.getStackTrace());
			e.printStackTrace();
			throw new BusinessException("合伙人项目保存推送运单信息异常："+System.err);
		} 
		
		//同步零担运单信息和财务单据给CUBC  zhangwei 2016-10-15 下午2:22:15
		if(Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
			if (!WaybillConstants.INNER_PICKUP.equals(pickupMode)
				&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(pickupMode)
				&& !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
				waybillInfoToCUBCService.asyncSendWaybillInfoToCUBC(waybillDto);
			}
		}
		
		//推送运单信息至OMS--sangwenhao-272311
		//是否传递开关
		boolean switch4EcsFlag = configurationParamsService.queryPkpSwitch4Ecs();
		if(switch4EcsFlag && !WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus) 
				&& StringUtils.equals(waybillDto.getIsExpress(), WaybillConstants.YES)
				&& StringUtils.equals(waybillDto.getIsGuiSubmit(), WaybillConstants.YES)){
			if(waybillDto.getWaybillEntity().getGoodsQtyTotal() > 1
					|| (StringUtils.isNotBlank(waybillDto.getWaybillEntity().getReturnType()))){//
						sysWaybillInfoToOmsService.pushWaybillNoToOMS(waybillDto) ;
			}
		}
	}

    private VestBatchResult getVestResult(WaybillDto waybillDto,String methodName) {
        RequestDO requestDO = new RequestDO();
        requestDO.setServiceCode(WaybillManagerService.class.getName()+"."+methodName);
        requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
        //校验是否合伙人，根据是否合伙人灰度不同数据
        isPartner(requestDO, waybillDto);
        requestDO.setSourceBillNos(waybillDto.getWaybillEntity().getWaybillNo());
        VestResponse response = new VestResponse();
        try {
            response = grayScaleService.vestByCustomer(requestDO);
        } catch (Exception e1) {
            //异常默认走foss
            throw new WaybillSubmitException(Constants.GRAY_SERVICE_EXCEPTION);
        }
        //如果灰度接口异常，那么为null
        List<VestBatchResult> vestBatchResults = response.getVestBatchResult();
        return CollectionUtils.isEmpty(vestBatchResults)?new VestBatchResult():vestBatchResults.get(0);
    }
    /**
     * 判断是否合伙人开单
     *
     */
    private void isPartner(RequestDO requestDO,WaybillDto waybillDto) {
        //合伙人字段
        String partnerBillingLogo = waybillDto.getActualFreightEntity().getPartnerBillingLogo();
        if (StringUtil.isNotBlank(partnerBillingLogo)) {
            if (WaybillConstants.YES.equals(partnerBillingLogo)) {
        	requestDO.setCustomerType(Constants.GRAY_SOURCE_BILLTYPE_PTP);
                requestDO.setCustomerCodes(waybillDto.getWaybillEntity().getCreateOrgCode());
            } else {
                requestDO.setCustomerType(Constants.GRAY_SOURCE_BILLTYPE_CU);
                requestDO.setCustomerCodes(waybillDto.getWaybillEntity().getDeliveryCustomerCode());
            }
        }else{
            requestDO.setCustomerType(Constants.GRAY_SOURCE_BILLTYPE_CU);
            requestDO.setCustomerCodes(waybillDto.getWaybillEntity().getDeliveryCustomerCode());
        }
    }

	/**
	 * 判断折前信息与运单信息是否不一致
	 * 判断是否对非加收网点进行加收
	 * xingjun 20161212
	 * @param waybillEntity
	 * @param partnersWaybillEntity
	 */
	private void checkPartnerWaybillFee(WaybillEntity waybillEntity,
			PartnersWaybillEntity partnersWaybillEntity) {
		LOG.info("开始校验折前信息，判断折前信息与运单信息是否不一致,判断是否对非加收网点进行加收");
		
		if(null == waybillEntity || null == partnersWaybillEntity){
			LOG.info("开始校验折前信息，运单信息或者折前信息为空,waybillEntity："+waybillEntity+";partnersWaybillEntity:"+partnersWaybillEntity);
			throw new WaybillSubmitException("开始校验折前信息，运单信息或者折前信息为空,waybillEntity："+waybillEntity+";partnersWaybillEntity:"+partnersWaybillEntity);
		}
		//如果是快递则不校验
		if(FossConstants.YES.equals(waybillEntity.getIsExpress()))
			return;

		//判断折前信息与运单信息是否不一致  add by xingjun 20161212
		LOG.info("折前信息，waybillNo:"+waybillEntity.getWaybillNo()+",transportFee:"+partnersWaybillEntity.getTransportFee()+
				";totalFee:"+partnersWaybillEntity.getTotalFee()+
				";valueAddFee:"+partnersWaybillEntity.getValueAddFee()+
				";overTransportFee:"+partnersWaybillEntity.getOverTransportFee());
		//校验运单费用明细与总运费
		checkWaybillAllFee(waybillEntity);
		
		if(null != waybillEntity.getTotalFee() &&
				null != partnersWaybillEntity.getTotalFee() &&
				BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) < 0
				 && BigDecimal.ZERO.compareTo(partnersWaybillEntity.getTotalFee()) == 0){
			throw new WaybillSubmitException("合伙人折前信息异常，运单总费用："+waybillEntity.getTotalFee()+",折前总费用："+
					partnersWaybillEntity.getTotalFee()+"，请关闭当前窗口重新开单，如问题仍然存在请上报合伙人项目组！");
		}
		//判断是否对非加收网点进行加收  add by xingjun 20161212
		if(null != partnersWaybillEntity.getOverTransportFee() && 
				BigDecimal.ZERO.compareTo(partnersWaybillEntity.getOverTransportFee()) < 0 && 
				!judgeIsAddedFee(waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getBillTime())){
			throw new WaybillSubmitException("合伙人折前信息异常，加收费用："+partnersWaybillEntity.getOverTransportFee()+","+
					waybillEntity.getCustomerPickupOrgCode()+"为非加收网点，请关闭当前窗口重新开单，如问题仍然存在请上报合伙人项目组！");
		}
	}
	
	/**
	 * 校验运单明细金额与总运费
	 * 逻辑同结算代码 WaybillPickupService.validAddedWaybillParam
	 * add by xingjun 20161229
	 * @param waybill
	 */
	private void checkWaybillAllFee(WaybillEntity waybill) {
		LOG.info("校验运单金额，预付、到付、公布价运费、送货费、包装费、代收货款手续费、保价费，其它费用，优惠费用单个字段的有效性，其中任意一项为空或者小于0");
		LOG.info("总金额："+waybill.getTotalFee()+"，预付款："+waybill.getPrePayAmount()+
				"，到付："+waybill.getToPayAmount()+"，代收："+waybill.getCodAmount() +"，运费："+waybill.getTransportFee()+"，接货费："+waybill.getPickupFee()+"，送货费："+waybill.getDeliveryGoodsFee()+
				"，包装费："+waybill.getPackageFee()+",代收费："+waybill.getCodFee()+"，保费："+waybill.getInsuranceFee()+"，其他费用："+waybill.getOtherFee()+"，增值费："+waybill.getValueAddFee()+"，优惠金额："+waybill.getPromotionsFee());
		// 校验运单金额，预付、到付、公布价运费、送货费、包装费、代收货款手续费、保价费，其它费用，优惠费用单个字段的有效性，其中任意一项为空或者小于0
		if (waybill.getTotalFee() == null
				|| waybill.getTotalFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getPrePayAmount() == null
				|| waybill.getPrePayAmount().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getToPayAmount() == null
				|| waybill.getToPayAmount().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getCodAmount() == null
				|| waybill.getCodAmount().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getTransportFee() == null
				|| waybill.getTransportFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getPickupFee() == null
				|| waybill.getPickupFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getDeliveryGoodsFee() == null
				|| waybill.getDeliveryGoodsFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getPackageFee() == null
				|| waybill.getPackageFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getCodFee() == null
				|| waybill.getCodFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getInsuranceFee() == null
				|| waybill.getInsuranceFee().compareTo(BigDecimal.ZERO) < 0
				|| waybill.getOtherFee() == null
				|| waybill.getValueAddFee() == null
				|| waybill.getPromotionsFee() == null
				|| waybill.getPromotionsFee().compareTo(BigDecimal.ZERO) < 0) {
			LOG.info("运单部分字段金额不正确，不能继续操作："+"总金额："+waybill.getTotalFee()+"，预付款："+waybill.getPrePayAmount()+
					"，到付："+waybill.getToPayAmount()+"，代收："+waybill.getCodAmount() +"，运费："+waybill.getTransportFee()+"，接货费："+waybill.getPickupFee()+"，送货费："+waybill.getDeliveryGoodsFee()+
					"，包装费："+waybill.getPackageFee()+",代收费："+waybill.getCodFee()+"，保费："+waybill.getInsuranceFee()+"，其他费用："+waybill.getOtherFee()+"，增值费："+waybill.getValueAddFee()+"，优惠金额："+waybill.getPromotionsFee());
			throw new WaybillSubmitException("运单部分字段金额不正确，不能继续操作");
		}
		
		// 校验运单内部金额是否正确。如果运单的（预付+到付-代收货款）不等于（公布价运费、接货费、送货费、包装费、代收货款手续费、保价费、其他费用之和时
		// 公布价运费已经包含了优惠费用，不再扣减
		BigDecimal totalTransFee = waybill.getTotalFee().subtract(waybill.getCodAmount());
		BigDecimal totalTransFee1 = waybill.getPrePayAmount().add(waybill.getToPayAmount()).subtract(waybill.getCodAmount());
		BigDecimal totalTransFee2 = NumberUtils.sum(waybill.getTransportFee(),
				waybill.getPickupFee(), waybill.getDeliveryGoodsFee(),
				waybill.getPackageFee(), waybill.getCodFee(),
				waybill.getInsuranceFee(), waybill.getOtherFee());
	
		if (totalTransFee.compareTo(totalTransFee1) != 0
				|| totalTransFee.compareTo(totalTransFee2) != 0) {
			throw new WaybillSubmitException("明细之和不等于总运费，不能继续操作");
		}
	}
	

	
    //抽取方法
    private void setWaybillTypeMethod(WaybillDto waybillDto, WaybillEntity waybillEntity, ActualFreightEntity actualFreightEntity, CrmOrderDetailDto orderDetailVo) {
        // 非空判断
        if (null != orderDetailVo) {
            if (StringUtils.isEmpty(orderDetailVo
                    .getChannelCustId())) {
                throw new WaybillValidateException(
                        "官网订单导入，登陆名为空时不允许提交！订单号："
                                + waybillEntity.getOrderNo());
            } else {
                actualFreightEntity
                        .setChannelCustId(orderDetailVo
                                .getChannelCustId());
            }
        }

        // 设置待补录的电子运单的运单类型
        if (StringUtil.isNotBlank(waybillDto.getOldWaybillNo())) {
            WaybillPendingEntity waybillpending = waybillPendingService
                    .getWaybillPendingEntityByWaybillNo(waybillDto
                            .getOldWaybillNo());
            if (waybillpending != null
                    && StringUtil.isNotBlank(waybillpending
                            .getWaybillType())
                    && waybillpending.getWaybillType().equals(
                            WaybillConstants.EWAYBILL)) {
                actualFreightEntity
                        .setWaybillType(waybillpending
                                .getWaybillType());
            }
        }
    }

    /**
	 * @项目：智能开单项目
	 * @功能：提交耗时时间任务
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19下午18:19
	 */
	private void addIntelligenceBillTime(WaybillDto waybillDto) {
		// TODO Auto-generated method stub
		try {
			intelligenceTimeRecordService.insertTimeRecord(waybillDto.getIbtg());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	/**
	 * 封装partnersWaybill实体
	 * @param waybillDto 
	 * @return PartnersWaybillEntity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author 272311-sangwenhao
	 * @date 2016-1-26
	 */
	private PartnersWaybillEntity getPartnersWaybillEntity(WaybillDto waybillDto,WaybillSystemDto systemDto)throws Exception {
		PtpWaybillDto ptpWaybillDto = waybillDto.getPtpWaybillDto() ;
		PartnersWaybillEntity partnersWaybillEntity = PartnersWaybillEntity.class.newInstance() ;
		
		partnersWaybillEntity.setWaybillNo(ptpWaybillDto.getWaybillNo());
		partnersWaybillEntity.setWaybillId(waybillDto.getWaybillEntity().getId());
		partnersWaybillEntity.setBoxCharge(ptpWaybillDto.getBoxChargeOrg()!=null ? ptpWaybillDto.getBoxChargeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setCodFee(ptpWaybillDto.getCodFeeOrg()!=null ? ptpWaybillDto.getCodFeeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setDeliveryGoodsFee(new BigDecimal(StringUtils.isNotBlank(ptpWaybillDto.getDeliveryGoodsFeeOrg()) ? ptpWaybillDto.getDeliveryGoodsFeeOrg():"0"));
		partnersWaybillEntity.setInsuranceFee(ptpWaybillDto.getInsuranceFeeOrg()!=null ? ptpWaybillDto.getInsuranceFeeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setOtherFee(ptpWaybillDto.getOtherFeeOrg()!=null ? ptpWaybillDto.getOtherFeeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setPackageFee(new BigDecimal(StringUtils.isNotBlank(ptpWaybillDto.getPackageFeeOrg()) ? ptpWaybillDto.getPackageFeeOrg():"0"));
		partnersWaybillEntity.setPickupFee(new BigDecimal(StringUtils.isNotBlank(ptpWaybillDto.getPickUpFeeOrg()) ? ptpWaybillDto.getPickUpFeeOrg():"0"));
		partnersWaybillEntity.setPromotionsFee(ptpWaybillDto.getPromotionsFeeOrg()!=null ? ptpWaybillDto.getPromotionsFeeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setSalverGoodsCharge(ptpWaybillDto.getSalverGoodsChargeOrg()!=null ? ptpWaybillDto.getSalverGoodsChargeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setServiceFee(ptpWaybillDto.getServiceFeeOrg()!=null ? ptpWaybillDto.getServiceFeeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setStandCharge(ptpWaybillDto.getStandChargeOrg()!=null ? ptpWaybillDto.getStandChargeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setTotalFee(ptpWaybillDto.getTotalFeeOrg()!=null ? ptpWaybillDto.getTotalFeeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setTransportFee(ptpWaybillDto.getTransportFeeOrg()!=null ? ptpWaybillDto.getTransportFeeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setUpFloorFee(ptpWaybillDto.getUpFloorFeeOrg());
		partnersWaybillEntity.setValueAddFee(ptpWaybillDto.getValueAddFeeOrg()!=null ? ptpWaybillDto.getValueAddFeeOrg():BigDecimal.ZERO);//增值服务费
		partnersWaybillEntity.setSalverGoodsCharge(ptpWaybillDto.getSalverGoodsChargeOrg()!=null ? ptpWaybillDto.getSalverGoodsChargeOrg():BigDecimal.ZERO);
		partnersWaybillEntity.setActive(FossConstants.YES);
		partnersWaybillEntity.setCreateTime(systemDto.getCreateTime());
		partnersWaybillEntity.setModifyTime(systemDto.getCreateTime());
		partnersWaybillEntity.setIsExpress(waybillDto.getIsExpress());
		partnersWaybillEntity.setChangeType(ptpWaybillDto.getChangeType());
		partnersWaybillEntity.setOverDistanceFeeOrg(ptpWaybillDto.getOverDistanceFeeOrg()!=null ? ptpWaybillDto.getOverDistanceFeeOrg():BigDecimal.ZERO); //超远派送费
		partnersWaybillEntity.setSignBillFeeOrg(ptpWaybillDto.getSignBillFeeOrg()!=null ? ptpWaybillDto.getSignBillFeeOrg():BigDecimal.ZERO);	//签收单
		partnersWaybillEntity.setBaseDeliveryGoodsFeeOrg(new BigDecimal(StringUtils.isNotBlank(ptpWaybillDto.getBaseDeliveryGoodsFeeOrg()) ? ptpWaybillDto.getBaseDeliveryGoodsFeeOrg():"0")); //基础送货费
		partnersWaybillEntity.setExceptionOpreationFee(ptpWaybillDto.getExceptionOpreationFeeOrg() != null ? ptpWaybillDto.getExceptionOpreationFeeOrg() : BigDecimal.ZERO); //异常操作服务费
		partnersWaybillEntity.setBigGoodsUpFloorFee(ptpWaybillDto.getBigGoodsUpFloorFeeOrg() != null ? ptpWaybillDto.getBigGoodsUpFloorFeeOrg() : BigDecimal.ZERO); //大件上楼费
		partnersWaybillEntity.setDeliveryWareHouseFee(ptpWaybillDto.getDeliveryWareHouseFeeOrg() != null ? ptpWaybillDto.getDeliveryWareHouseFeeOrg() : BigDecimal.ZERO); //送货进仓费
		partnersWaybillEntity.setPickupToDoorJZFee(ptpWaybillDto.getPickupToDoorJZFeeOrg() != null ? ptpWaybillDto.getPickupToDoorJZFeeOrg() : BigDecimal.ZERO); //送货安装费
		partnersWaybillEntity.setIsCalTraFee(ptpWaybillDto.getIsCalTraFee()); //转运返货是否重新计算公布价运费
		partnersWaybillEntity.setStartOrgCodeCal(ptpWaybillDto.getStartOrgCodeCal()); //重新计算公布价运费出发部门
		partnersWaybillEntity.setDestinationOrgCodeCal(ptpWaybillDto.getDestinationOrgCodeCal()); //重新计算公布价运费到达部门
		partnersWaybillEntity.setOverTransportFee(null != ptpWaybillDto.getOverTransportFeeOrg() ? ptpWaybillDto.getOverTransportFeeOrg() : BigDecimal.ZERO); //合伙人到达加收金额
		partnersWaybillEntity.setOverTransportRate(null != ptpWaybillDto.getOverTransportRateOrg() ? ptpWaybillDto.getOverTransportRateOrg() : BigDecimal.ZERO);//合伙人到达加收费率
		partnersWaybillEntity.setCouponFee(ptpWaybillDto.getCouponFeeOrg()!=null ? ptpWaybillDto.getCouponFeeOrg():BigDecimal.ZERO);//优惠券金额		--邹胜利
		//设置偏线费用
		partnersWaybillEntity.setPartialTransportFee(null != ptpWaybillDto.getPartialTransportFeeOrg() ? ptpWaybillDto.getPartialTransportFeeOrg() : BigDecimal.ZERO);
		
		return partnersWaybillEntity;
	}

	/**
	 * 安装明细插入数据库
	 * foss-254615-mabinliang
	 * @param waybillDto
	 */
	private void addSpecialValueAddedServiceData(WaybillDto waybillDto) {
		InstallationEntity installation=null;
		if(waybillDto.getSpecialValueAddedServiceEntity()!=null){
		List<InstallationEntity> list=waybillDto.getSpecialValueAddedServiceEntity();
		for (int i = 0; i < list.size(); i++) {
			installation=list.get(i);
			installationService.insertInstallation(installation);
		    }
	     }
	}
     /**
	/**
	 * 添加货签信息
	 * 
	 * @param pendingEntity
	 * @param wood
	 * @return
	 */
	public List<LabeledGoodEntity> addLabeledGood(WaybillEntity pendingEntity,
			WoodenRequirementsEntity wood) {
		List<LabeledGoodEntity> labeledGoodList = new ArrayList<LabeledGoodEntity>();
		if (pendingEntity != null) {
			if (pendingEntity.getGoodsQtyTotal() > 0) {
				for (int i = 1; i <= pendingEntity.getGoodsQtyTotal(); i++) {


					LabeledGoodEntity labeledGood = new LabeledGoodEntity();
					// id
					labeledGood.setId(UUIDUtils.getUUID());
					labeledGood.setWaybillNo(pendingEntity.getWaybillNo());
					labeledGood.setActive(FossConstants.ACTIVE);
					labeledGood.setBillTime(pendingEntity.getBillTime());
					labeledGood.setCreateTime(new Date());
					labeledGood.setModifyTime(new Date());
					// 流水号
					labeledGood.setSerialNo(StringHandlerUtil.lpad(
							String.valueOf(i), NumberConstants.NUMBER_4, "0"));
					labeledGood.setInitalVale(FossConstants.YES);
					if (null != wood && wood.getStandGoodsNum() > 0
							&& wood.getStandGoodsNum() >= i) {
						labeledGood.setIsNeedWooden(FossConstants.YES);
					}
					// labeledGood.setNumberChangItems(labeled.getNumberChangItems());
					// labeledGood.setOldSerialNo(labeled.getOldSerialNo());
					// zxy 20131118 ISSUE-4391 包装类型，目前只有木托=SALVER
					if (null != wood && wood.getSalverGoodsNum() > 0
							&& wood.getSalverGoodsNum() >= i) {
						labeledGood
								.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
					}

					labeledGoodList.add(labeledGood);
				}
			}
		}
		return labeledGoodList;
	}
    

	/**
	 * 添加货签信息
	 * 
	 * @author 076234-foss-panGuoYang
	 */
	private void addPDALabeledGood(WaybillEntity waybillEntity) {
		// 循环添加货签信息
		for (int i = 1; i <= waybillEntity.getGoodsQtyTotal(); i++) {
			// new货签对象
			LabeledGoodPDAEntity labeledGood = new LabeledGoodPDAEntity();
			// id
			labeledGood.setWaybillPDAId(waybillEntity.getId());
			// 运单号
			labeledGood.setWaybillNo(waybillEntity.getWaybillNo());
			// 流水号
			labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, String.valueOf(0)));
			// 开单时间
			labeledGood.setBillTime(waybillEntity.getCreateTime());
			// 有效
			labeledGood.setActive(FossConstants.ACTIVE);
			// 生效时间
			labeledGood.setCreateTime(waybillEntity.getCreateTime());
			// 失效时间
			labeledGood.setModifyTime(waybillEntity.getCreateTime());
			// 创建人编码
			labeledGood.setCreateUserCode(waybillEntity.getCreateUserCode());
			labeledGoodPDAService.insertSelective(labeledGood);
		}
	}
	
	/**
	 * 添加待刷卡运单数据
	 * 
	 * @author 273279-foss-liding
	 */
	private void addWSCWayBillData(WaybillEntity waybillEntity) {
		
		LOG.info("单号"+waybillEntity.getWaybillNo()+"添加待刷卡运单数据开始");
		
		WSCWayBillParamEntity wayBillParamEntity = new WSCWayBillParamEntity();
		// 运单号
		wayBillParamEntity.setWayBillNo(waybillEntity.getWaybillNo());
		// 数据来源(1-运单开单 2-运单更改)
		wayBillParamEntity.setWayBillSource(WaybillConstants.WAYBILL_SOURCE_CREATE);
		// 发货人编号
		wayBillParamEntity.setSendCustomerCode(waybillEntity.getDeliverCustContactId());
		// 发货人名称
		wayBillParamEntity.setSendCustomerName(waybillEntity.getDeliveryCustomerContact());
		// 开单部门编号 (全部设置为收货部门)
		wayBillParamEntity.setCreateBillOrgCode(waybillEntity.getReceiveOrgCode());
		// 开单部门名称
		String orgName= 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getReceiveOrgCode());
		wayBillParamEntity.setCreateBillOrgName(orgName);
		// 开单时间
		wayBillParamEntity.setCreateBillTime(waybillEntity.getBillTime());
		// 待刷卡金额 (运单金额=预付金额)
		wayBillParamEntity
				.setWaitSwipeAmount(waybillEntity.getPrePayAmount() != null ? waybillEntity
						.getPrePayAmount().longValue() : 0);
		//总金额
		wayBillParamEntity.setWayBillAmount(waybillEntity.getTotalFee().doubleValue());
		//支付状态:未支付
		wayBillParamEntity.setPaymentStatus(WaybillConstants.WAYBILL_PAYSTATUS_UNPAID);
		// 创建人编号
		wayBillParamEntity.setCreateUserCode(waybillEntity.getCreateUserCode());
		// 创建人名称
		wayBillParamEntity.setCreateUserName(waybillEntity.getCreateUserName());
		
		RequestDO requestDO = new RequestDO();
		requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".addWSCWayBillData");
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		//requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_CU);
		requestDO.setCustomerType(Constants.GRAY_SOURCE_BILLTYPE_CU);
		requestDO.setCustomerCodes(waybillEntity.getDeliveryCustomerCode());
		VestResponse response=null;
		String jsonString=JSONObject.toJSONString(wayBillParamEntity);
		try {
			response = grayScaleService.vestByCustomer(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
		} catch (Exception e) {
			throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
		}
		try {
			if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
				List<VestBatchResult> batchResults = response.getVestBatchResult();
				VestBatchResult batchResult = batchResults.get(0);
				if(batchResult.getVestSystemCode().equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS)){
					 String flag = FossConstants.NO;
					String[] codes = new String[1];
	    			codes[0]=WaybillConstants.PKP_WSCWAYBILL_AUTO_SCHEDULE;
	    			List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
	    			if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
	    				//开关是否关闭
	    				flag = configurationParamsEntitys.get(0).getConfValue();
	    			}
	    			if(StringUtils.equals(FossConstants.YES, flag)){
	    				//开关打开异步调用结算接口生成代刷卡数据
	    				wSCWaybillProcessService.addWSCWaybillProcessEntity(WaybillConstants.ADD_WAYBILL, jsonString);
	    			}else{
	    				//不存在开关或者开关关闭同步调用结算接口生成代刷卡数据
	    				wscWayBillManageService.addWSCWayBill(wayBillParamEntity);
	    			}
				}
			}/*else {
				wscWayBillManageService.addWSCWayBill(wayBillParamEntity);
			}*/
		} catch (Exception e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			throw new WaybillSubmitException("运单提交失败！\n原因：无法添加待刷卡数据");
		}
		LOG.info("单号"+waybillEntity.getWaybillNo()+"添加待刷卡运单数据结束");
	}

	/**
	 * <p>
	 * 大客户激活电子运单
	 * </p>
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-9 10:09:11
	 * @param waybill
	 * @return Result
	 */
	@Transactional
	@Override
	public void submitEWaybill(WaybillDto waybillDto){
		boolean isPdaPending = false;
		UserEntity userEntity = new UserEntity();
		if (StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getDriverCode())) {
			userEntity.setEmpCode(waybillDto.getWaybillEntity().getDriverCode());
			EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(waybillDto.getWaybillEntity().getDriverCode());
			if (null == employEntity && StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getCreateUserCode())) {
				employEntity = employeeService.queryEmployeeByEmpCode(waybillDto.getWaybillEntity().getCreateUserCode());

			}
			if (employEntity != null) {
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmpCode(employEntity.getEmpCode());
				userEntity.setEmployee(employEntity);
				// 转换成HttpServletRequest
				LOG.info("创建模拟Session开始");
				javax.servlet.http.HttpSession session = new MockHttpSession();
				// 初始化sessionContext
				SessionContext.setSession(session);
				if (StringUtil.isNotBlank(waybillDto.getCurrentInfo()
						.getCurrentDeptCode())) {
					SessionContext.getSession().setObject(
							"FOSS_KEY_CURRENT_DEPTCODE",
							(Object) waybillDto.getCurrentInfo()
									.getCurrentDeptCode());
				}
				if (StringUtil.isNotBlank(waybillDto.getCurrentInfo()
						.getCurrentDeptName())) {
					SessionContext.getSession().setObject(
							"FOSS_KEY_CURRENT_DEPTNAME",
							(Object) waybillDto.getCurrentInfo()
									.getCurrentDeptName());
				}
			} else {
				throw new PdaInterfaceException("查询不到该司机！");
			}
			LOG.info("创建模拟Session结束");

		}
		UserContext.setCurrentUser(userEntity);
		// 当前时间
		Date nowDate = new Date();
		// 校验运单是否符合业务规则(如果需要处理内部带货或者自提，则需要再增加verifyWaybill部分逻辑)
		veryfyEWaybill(waybillDto);
		// 获取运单处理状态
		String waybillStatus = StringUtil.defaultIfNull(waybillDto
				.getWaybillEntity().getPendingType());

		// 设置统一的创建人、创建时间、更新人、更新时间
		WaybillSystemDto systemDto = new WaybillSystemDto();
		// 设置当前用户信息
		CurrentInfo currentInfo = waybillDto.getCurrentInfo();
		systemDto.setCurrentUser(currentInfo);
		// 设置修改时间
		systemDto.setModifyTime(nowDate);// 统一设置为当前提交时间

		// 设置创建时间
		systemDto.setCreateTime(nowDate);// 电子运单的创建时间为生成待激活的时间

		// 设置开单时间
		systemDto.setBillTime(nowDate);// 电子运单的开单时间为生成待激活的时间，防止价格时效取错
		/*
		 * if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)){
		 * //若为PDA导入开单，则使用PDA时间 Date billTime =
		 * waybillDto.getWaybillEntity().getBillTime(); //判断开单时间是否为空 if(null ==
		 * billTime){ billTime = new Date(); } systemDto.setBillTime(billTime);
		 * }else{ systemDto.setBillTime(new Date()); }
		 */
		// 设置业务开单时间为当前时间
		waybillDto.getWaybillEntity().setBillTime(nowDate);
		// 定义运单基本信息对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();

		//判断提交、重量是否改变
		setExpChangeVolume(waybillDto);
		// 根据运输性质设置是否快递字段的值
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {

			waybillDto.setIsExpress(FossConstants.YES);
		} else {
			waybillDto.setIsExpress(FossConstants.NO);
		}
		// 修改后的运输性质
		String productCode = waybillEntity.getProductCode();

		if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
				.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT
						.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT
						.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT
						.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE
						.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_PCP
						.equals(productCode)) {
			waybillEntity.setLastLoadOrgCode(waybillEntity
					.getCustomerPickupOrgCode());
			waybillEntity.setLastLoadOrgName(waybillEntity
					.getCustomerPickupOrgName());
		}
		if (StringUtils.isEmpty(waybillDto.getWaybillEntity()
				.getCustomerPickupOrgName())) {
			String prdCode = waybillDto.getWaybillEntity().getProductCode();
			String orgcode = waybillDto.getWaybillEntity()
					.getCustomerPickupOrgCode();
			if (!StringUtils.isEmpty(orgcode)) {
				String customerPickupOrgName = waybillDao
						.queryCustomerPickupOrgNameByWayno(orgcode, prdCode);
				waybillDto.getWaybillEntity().setCustomerPickupOrgName(
						customerPickupOrgName);
			}
		}
		if (StringUtils.isEmpty(waybillDto.getWaybillEntity()
				.getReceiveOrgName())) {
			String orgcode = waybillDto.getWaybillEntity().getReceiveOrgCode();
			String receiveOrgName = waybillDao
					.queryReceiveOrgNameByWayno(orgcode);
			waybillDto.getWaybillEntity().setReceiveOrgName(receiveOrgName);
		}

		// 重新赋值
		if (StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
			// DEFECT-824 部分营业部反馈客户网单无法在线跟踪运单详情
			ActualFreightEntity actualFreightEntity = waybillDto
					.getActualFreightEntity();
			if (actualFreightEntity != null) {
				// 若运单绑定的订单为官网订单，且运单状态为待补录的状态时，需要通过订单号查一遍订单信息，将官网登陆名查询出来
				if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE
						.equals(waybillEntity.getOrderChannel())
						&& StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
					// 待补录状态
					if (WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING
							.equals(waybillStatus)
							|| WaybillConstants.WAYBILL_STATUS_PDA_PENDING
									.equals(waybillStatus)
							|| WaybillConstants.WAYBILL_STATUS_PC_PENDING
									.equals(waybillStatus)) {
						// 不允许登录帐号为空
						if (StringUtils.isEmpty(actualFreightEntity
								.getChannelCustId())) {
							throw new WaybillValidateException(
									"官网订单导入，登陆名为空时不允许提交！订单号："
											+ waybillEntity.getOrderNo());
						}
					}
				}
			}
		}

		// zxy 20130916 BUG-54827 start 新增：运单如果失效且是6月1号前导入的数据，则删除ActualFreight数据
		String waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
		ActualFreightEntity actualFreightEntity = waybillDto
				.getActualFreightEntity();
		if (actualFreightEntity != null) {
			if (WaybillConstants.EWAYBILL.equals(actualFreightEntity
					.getWaybillType())) {
				ActualFreightEntity actualFreightforUpdate = new ActualFreightEntity();
				// 设置记录创建时间
				actualFreightforUpdate.setCreateTime(systemDto.getBillTime());
				// 设置修改时间为当前时间
				actualFreightforUpdate.setModifyTime(nowDate);
				// 设置为有效
				actualFreightforUpdate.setStatus(WaybillConstants.EFFECTIVE);
				// 设置始发库存部门编码 解决DZYD-86 电子运单-货物状态当前位置显示错误，库存信息原因
				actualFreightforUpdate.setStartStockOrgCode(waybillEntity
						.getCreateOrgCode());
				//设置更改后总件数和纸张数
				actualFreightforUpdate.setGoodsQty(actualFreightEntity.getGoodsQty());
				actualFreightforUpdate.setVolume(actualFreightEntity.getVolume());
				actualFreightforUpdate.setWeight(actualFreightEntity.getWeight());
				// 老运单号
				String oldWaybillNo = StringUtil.defaultIfNull(waybillDto
						.getOldWaybillNo());
				// 判断PDA补录是否已在运单表中生成记录（若在运单表生成了记录，则会在标签表、actualFreight表中生成记录）
				// 不为空时，则更新
				actualFreightService.updateByWaybillNo(actualFreightforUpdate,
						oldWaybillNo);
				// 重新设置waybillDto中的承运信息，已备生成库存信息addWaybillStockService(waybillDto)方法调用
				waybillDto.setActualFreightEntity(actualFreightEntity);
			} else {
				WaybillEntity we = waybillDao.queryRecentWaybillByNo(waybillNo);
				// 如果此运单存在ActualFreight，先判断此运单是否失效且是系统上线前导入的数据，如果是则可以删除ActualFreight
				if (we == null
						|| (FossConstants.NO.equals(we.getActive()) && we
								.getBillTime().before(
										DateUtils.convert("2013-06-01")))) {
					actualFreightService
							.deleteActualFreightByWaybillNo(waybillNo);
				}
			}
		}

		/**
		 * MANA-1937 系统开发生成客户编码 026123 2014-03-24
		 */
		procDeliverAndReceiveCustomer(waybillDto);

		// 生成运单完整信息
		isPdaPending = addEWaybill(waybillDto, systemDto, isPdaPending);

		// 是否为整车，若为整车则不生成中转线路信息
		if (FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
			LOG.info("submitWaybill:进入整车生成库存部分");
			// 生成货签信息和库存信息
			addLabeledGood(waybillDto, systemDto, isPdaPending);
		} else {
			LOG.info("submitWaybill:进入生成库存部分");
			// 中转接口：生成中转线路信息
			addLine(waybillDto, isPdaPending);
			// 生成货签信息
			pdaScanService.addLabelGoodsInfo(waybillDto.getWaybillEntity(),
					systemDto);
			// 生成库存信息（电子运单二期）
			pdaScanService.waybillInStockByWaybillNo(waybillNo);
		}

		// 若为签收单返单类型，则需要生成签收单信息
		if (!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity
				.getReturnBillType())) {
			// 生成签收单信息
			addSignReturnBill(waybillDto);
		}

		// 当打木架信息不为空时，才调用中转接口,（并且木架木托木箱至少有一个不为0时才调用中转接口--lianhe--2017年3月10日14:12:51）
		if (waybillDto.getWoodenRequirementsEntity() != null 
				&&((waybillDto.getWoodenRequirementsEntity().getStandGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getStandGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum() != 0))) {
			// 中转接口：生成包装信息
			woodenRequirementsService.addPackagingRequire(waybillDto);
		}

		// 内部带货不调用财务接口（已与周斌文确认2013-04-17）
		String pickupMode = StringUtil.defaultIfNull(waybillEntity
				.getReceiveMethod());
		if (!pickupMode.equals(WaybillConstants.INNER_PICKUP)
				&& !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
			// 结算接口：生成结算相关财务单据
			addSettleBill(waybillDto);
		}

		// 新增业务标识
		// addWaybillTransactionStat(waybillDto);

		// 待补录运单信息
		WaybillPendingEntity waybillPendingEntity = null;
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("waybillNo", waybillNo);
			maps.put("active", FossConstants.NO);
			maps.put("pendingType",
					WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
			maps.put("waybillType", WaybillConstants.EWAYBILL);
			waybillPendingEntity = waybillPendingService
					.queryUnActiveEWaybillPendingByWaybilllNo(maps);

			// zxy 20140211 MANA-1044 start 新增:PDA补录时修改了重量体积需要调用中转更新相关信息
			if (!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {
				BigDecimal preVolume = waybillPendingEntity.getGoodsVolumeTotal();
				BigDecimal preWeight = waybillPendingEntity.getGoodsWeightTotal();


				BigDecimal curVolume = waybillEntity.getGoodsVolumeTotal();
				BigDecimal curWeight = waybillEntity.getGoodsWeightTotal();
				MakeUpWaybillEntity makeUpWaybillEntity = new MakeUpWaybillEntity();
				// 只要重量和体积有一个修改了就传给中转进行刷新
				if ((preVolume == null || preVolume.compareTo(curVolume) != 0)
						|| (preWeight == null || preWeight.compareTo(curWeight) != 0)) {
					makeUpWaybillEntity.setVolume(curVolume);
					makeUpWaybillEntity.setWeight(curWeight);
					makeUpWaybillEntity.setWaybillNo(waybillNo);
					makeUpWaybillEntity
							.setMakeUpTime(systemDto.getModifyTime());
					makeUpWaybillEntity.setQuantity(new BigDecimal(
							waybillEntity.getGoodsQtyTotal()));
					// 通知中转重量体积变更，将变更信息放入中转跑批表
					makeUpWaybillService
							.addWaybillInfoForTfr(makeUpWaybillEntity);
				}
			}
			// zxy 20140211 MANA-1044 end 新增:PDA补录时修改了重量体积需要调用中转更新相关信息
		}

		// 判断是否是PDA补录或者运单暂存补录
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)
				|| WaybillConstants.WAYBILL_STATUS_PC_PENDING
						.equals(waybillStatus)
				|| WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING
						.equals(waybillStatus)) {
			// 更新待处理运单记录信息
			if(waybillPendingEntity!=null){
			updateEWaybillData(waybillDto, waybillPendingEntity, waybillStatus);
			}
		}

		// 是否为弃货处理运单
		if (WaybillConstants.WAYBILL_STATUS_GOODS_PENDING.equals(waybillStatus)) {
			// 更新弃货处理运单记录信息，标识待弃货运单为N,更新弃货运单号储运事项为新运单号
			updateAbandonedGoodsData(waybillDto.getWaybillEntity());
		}

		// 处理待办事务
		handleToDoMsg(waybillDto);

		// 保存待处理发送短信信息，执行异步定时任务
		savePendingSendMailInfo(waybillDto);

		// 更新约车状态
		updateVehicleState(waybillDto);

		// 判断优惠券对象是否为空
		if (waybillDto.getCouponInfoDto() != null) {
			// 使用优惠券
			useCoupon(waybillDto);
		}

		// 快递冗余信息
		if (waybillDto.getWaybillExpressEntity() != null) {
			addWaybillExpress(waybillDto, systemDto);
		}

		// 给中转推送货物轨迹
		// addSynTrack(waybillDto);

		// 释放业务锁
		// unlock(waybillEntity);

		// 增加定人定区地址(放在最后面)
		addReceiveAddressRfc(waybillEntity);

		// 如果是PDA补录的单，则判断是否需要生成待办（和更改单处理待办逻辑基本一致，只是PDA补录的单，如果修改了信息，更改状态都是自动审核、受理同意）
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			// 处理PDA补录单的待办信息
			handlePDAPendingTODO(waybillDto, waybillPendingEntity);
			// 更新GUI中PDA待补录信息
			updateTodoMsgStatus(waybillDto);
		}
		// 激活完成后，更新订单表状态
		dispatchOrderEntityDao.updateSatusByOrderNo(waybillDto
				.getWaybillEntity().getOrderNo(), WaybillConstants.WAYBILL);
		// 激活成功，删除电子订单
		ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(waybillDto
				.getWaybillEntity().getOrderNo());
		// 为配合快递100轨迹推送所需的方法
		addOneWaybillTrack(waybillDto);
		
		//FOSS运单号同步给悟空，验证运单号是否存在（这边只同步母件必须写在下面子母件调用之前,放在OMS异步接口之前）
		pushWaybillNo2Ecs(waybillDto);
				
		// 订单号是否为空
		if (StringUtil.isNotBlank(waybillDto.getWaybillEntity().getOrderNo())) {
			// 订单号不为空则更新CRM订单状态
			orderService.updateOrderInfo(waybillDto.getWaybillEntity());
			Log.info("电子运单提交更新订单状态，订单号为"
					+ waybillDto.getWaybillEntity().getOrderNo());
		}
				
		//添加一票多件数据
		submitWaybillRelateDetail(waybillDto, systemDto);
	}

	/*
	 * 快递界面体积是否修改设置
	 * 
	 * @date 2014-08-27
	 * 
	 * @author 136334-BaiLei
	 */
	private void setExpChangeVolume(WaybillDto waybillDto) {
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		WaybillExpressEntity waybillExpressEntity = waybillDto
				.getWaybillExpressEntity();
		if (waybillExpressEntity != null)
			if (waybillEntity != null && isExpress(waybillDto)) {


				// 手动输入体积为0时设置ChangeVolume为Y
				if (waybillEntity.getGoodsVolumeTotal() != null
						&& waybillEntity.getGoodsVolumeTotal().doubleValue() <= Double.parseDouble(ZEROSTR)) {
					waybillEntity.setGoodsVolumeTotal(BigDecimal.valueOf(KEY_VOLUME));// 货物总体积
					if (waybillDto.getWaybillExpressEntity() != null) {
						waybillDto.getWaybillExpressEntity().setChangeVolume(FossConstants.YES);
					}
				}

				// 尺寸计算体积小于0.01时设置ChangeVolume为Y
				if ((new BigDecimal(KEY_VOLUME).compareTo(waybillEntity.getGoodsVolumeTotal()) == 0)
						&& StringUtil.isNotEmpty(waybillEntity.getGoodsSize())
						&& waybillDto.getWaybillExpressEntity() != null) {
					ScriptEngineManager manager = new ScriptEngineManager();
					ScriptEngine engine = manager.getEngineByName("JavaScript");
					try {
						Object result = engine.eval(waybillEntity.getGoodsSize());
						BigDecimal bigDecimal = new BigDecimal(result.toString());
						bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_3, BigDecimal.ROUND_HALF_UP);
						BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
						bigDecimal = bigDecimal.divide(m);

						if (bigDecimal.doubleValue() < KEY_VOLUME) {
							waybillDto.getWaybillExpressEntity().setChangeVolume(FossConstants.YES);
						}
					} catch (ScriptException e) {
						LOG.error("ScriptException", e);
						throw new WaybillValidateException("体积计算发生异常");
					}
				}
			}
		}

	/**
	 * 处理发货客户与收货客户生成编码优化
	 * 
	 * @创建时间 2014-5-30 下午5:05:08
	 * @创建人： WangQianJin
	 */
	private void procDeliverAndReceiveCustomer(WaybillDto waybillDto) {

		// 付款方式为现金、到付的新客户开单时调用综合接口生成散客信息
		// if(WaybillConstants.CASH_PAYMENT.equals(waybillEntity.getPaidMethod())
		// ||
		// WaybillConstants.ARRIVE_PAYMENT.equals(waybillEntity.getPaidMethod())){
		Log.debug("***************生成发货散客信息***************");
		createDeliveryCustomer(waybillDto,
				DictionaryValueConstants.CUSTOMER_SC_CUSTOMER);

		// 如果不是偏线和空运和快递，则生成收货客户信息
		// if(!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())
		// &&
		// !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())
		// &&
		// !ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybillEntity.getProductCode())){
		Log.debug("***************生成收货散客信息***************");
		createReceiveCustomer(waybillDto, DictionaryValueConstants.CUSTOMER_SC_CUSTOMER);
		// }
		// }

		// 发货潜客已开单
		if (StringUtil.isNotEmpty(waybillDto.getWaybillEntity().getDeliveryCustomerCode())) {
			CustomerDto customerDto = queryCustomerService.queryCustInfoByCode(waybillDto.getWaybillEntity().getDeliveryCustomerCode());
			if (customerDto != null && DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(customerDto.getCustomserNature())) {
				Log.debug("***************生成发货潜客信息***************");
				createDeliveryCustomer(waybillDto, DictionaryValueConstants.CUSTOMER_PC_CUSTOMER);
			}
		}
		// 收货潜客已开单
		if (StringUtil.isNotEmpty(waybillDto.getWaybillEntity().getReceiveCustomerCode())) {
			CustomerDto customerDto = queryCustomerService.queryCustInfoByCode(
					waybillDto.getWaybillEntity().getReceiveCustomerCode());
			if (customerDto != null
					&& DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(customerDto.getCustomserNature())) {
				Log.debug("***************生成收货潜客信息***************");
				createReceiveCustomer(waybillDto, DictionaryValueConstants.CUSTOMER_PC_CUSTOMER);
			}
		}

	}

	/**
	 * 封装运单发货客户信息 MANA-1937 系统开发生成客户编码 026123 2014-03-24
	 */
	private void createDeliveryCustomer(WaybillDto waybillDto, String type) {
		WaybillEntity entity = waybillDto.getWaybillEntity();
		// 非空判断
		if (null == entity) {
			throw new WaybillSubmitException("运单提交失败：运单基本信息为空！");
		}
		// 是否生成客户
		boolean isCreate = false;

		// 发货客户编码是否为空
		if (StringUtil.isNotEmpty(entity.getDeliveryCustomerCode())) {
			// 若有客户编码，则不需要生成新客户
			isCreate = false;
		}
		// 再次判断下手机或者电话能否查询出对应的客户信息（防止出现“开单选客户时没有客户但提交前却生成了客户的信息”）
		else {
			// 封装查询条件
			CustomerQueryConditionDto queryParam = new CustomerQueryConditionDto();
			// 手机号码是否为空
			if (StringUtil.isNotEmpty(entity.getDeliveryCustomerMobilephone())) {
				queryParam.setMobilePhone(entity.getDeliveryCustomerMobilephone());
			} else {
				queryParam.setContactPhone(entity.getDeliveryCustomerPhone());
				queryParam.setContactName(entity.getDeliveryCustomerContact());
			}
			// 如果手机号与固定电话都为空，则不允许提交
			if (StringUtils.isEmpty(queryParam.getMobilePhone())
					&& StringUtils.isEmpty(queryParam.getContactPhone())) {
				throw new WaybillSubmitException("发货人手机号与固定电话不允许全部为空！");
			}
			// 查询匹配记录
			ContactEntity dto = cusContactService.queryCusContactByMobileOrPhoneAndName(queryParam);
			// 若匹配成功，则不生成
			if (dto != null
					&& (dto.getCustomerCode() != null || dto.getOwnCustId() != null)) {
				// 根据crmCusId获取客户信息
				CustomerEntity cus = queryCustomerService.queryCustomerByCrmCusId(dto.getCustomerCode(), dto.getOwnCustId());
				if (cus != null && StringUtil.isNotEmpty(cus.getCusCode())) {
					entity.setDeliveryCustomerCode(cus.getCusCode());
					isCreate = false;
				} else {
					isCreate = true;
				}
			} else {
				isCreate = true;
			}
		}

		// 如果是潜客的话，则需要调用综合接口标记潜客已开单
		if (DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(type)) {
			isCreate = true;
		}

		// 判断是否需要生成客户
		if (isCreate) {
			// 定义客户信息对象
			NonfixedCustomerAssociatedInformationEntity customer = new NonfixedCustomerAssociatedInformationEntity();

			// 客户编码
			customer.setCustomerCode(entity.getDeliveryCustomerCode());
			// 客户名称
			customer.setCustomerName(entity.getDeliveryCustomerName());
			// 联系人名称
			customer.setLinkManName(entity.getDeliveryCustomerContact());
			// 联系人编码
			customer.setLinkManCode(entity.getDeliverCustContactId());
			// 客户固定电话
			customer.setMobile(entity.getDeliveryCustomerPhone());
			// 客户省
			customer.setProCode(entity.getDeliveryCustomerProvCode());
			// 客户市
			customer.setCityCode(entity.getDeliveryCustomerCityCode());
			// 客户区县
			customer.setCountyCode(entity.getDeliveryCustomerDistCode());
			// 地址
			customer.setAddress(entity.getDeliveryCustomerAddress());
			// 客户手机
			customer.setCellPhone(entity.getDeliveryCustomerMobilephone());
			// 添加地址备注
			customer.setCustAddrRemark(waybillDto.getActualFreightEntity().getDeliveryCustomerAddressNote());
			// 业务类别(零担和快递)
//			if (waybillExpressService.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())) {
//				customer.setBusinessType(DictionaryValueConstants.BUSINESS_EXPRESS);
//			} else {
//				customer.setBusinessType(DictionaryValueConstants.BUSINESS_LTT);
//			}
			/**
			 * @author Foss-278328-hujinyang
			 * @TIme 20160421
			 */
			if (isExpress(waybillDto)) {
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_EXPRESS);
			} else {
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_LTT);
			}
			
			// 客户所属部门标杆编码
			String unifiedCode = null;
//			OrgAdministrativeInfoEntity o = orgAdministrativeInfoService
//					.queryOrgAdministrativeInfoByCodeNoCache(entity.getReceiveOrgCode());
			OrgAdministrativeInfoEntity o = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeClean(entity.getReceiveOrgCode());
			if (null != o) {
				unifiedCode = o.getUnifiedCode();
			}
			customer.setUnifiedCode(unifiedCode);
			// 出发客户
			customer.setCustomerAttributes(DictionaryValueConstants.LEAVE_CUSTOMER_ATTRIBUTE);
			// 客户性质（潜客OR散客）
			customer.setCustType(type);
			// 调用综合接口生成发货客户信息
			ContactEntity dto = null;
			if (!StringUtils.isEmpty(entity.getDeliveryCustomerMobilephone())) {
				dto = cusContactService.queryCusContactByMobile(entity.getDeliveryCustomerMobilephone());
			}
			if (null == dto
					|| DictionaryValueConstants.CUSTOMER_PC_CUSTOMER
							.equals(type)) {
				NonfixedCustomerAssociatedInformationEntity nonCus = synNonfixedCustomerToCrmSerivce.createNonfixedCustomer(customer);
				// 如果是散客，需要将客户编码反写到运单中
				if (DictionaryValueConstants.CUSTOMER_SC_CUSTOMER.equals(type)) {
					if (nonCus != null && nonCus.getCustomerCode() != null) {
						entity.setDeliveryCustomerCode(nonCus.getCustomerCode());
					} else {
						Log.debug("***************开单生成发货客户信息---未生成客户编码***************" + entity.getWaybillNo());
					}
				}
			}

		} else {
			return;
		}
	}

	/**
	 * 封装运单收货客户信息 MANA-1937 系统开发生成客户编码 026123 2014-03-24
	 */
	private void createReceiveCustomer(WaybillDto waybillDto, String type) {
		WaybillEntity entity = waybillDto.getWaybillEntity();
		// 非空判断
		if (null == entity) {
			throw new WaybillSubmitException("运单提交失败：运单基本信息为空！");
		}

		// 是否生成客户
		boolean isCreate = false;

		// 收货客户编码是否为空
		if (StringUtil.isNotEmpty(entity.getReceiveCustomerCode())) {
			// 若有客户编码，则不需要生成新客户
			isCreate = false;
		}
		// 再次判断下手机或者电话能否查询出对应的客户信息（防止出现“开单选客户时没有客户但提交前却生成了客户的信息”）
		else {
			// 封装查询条件
			CustomerQueryConditionDto queryParam = new CustomerQueryConditionDto();
			// 是否和发货客户是同一客户
			boolean flag = false;
			// 手机号码是否为空
			if (StringUtil.isNotEmpty(entity.getReceiveCustomerMobilephone())) {
				// 有手机，则只根据手机查询
				queryParam.setMobilePhone(entity.getReceiveCustomerMobilephone());
				// 发货人手机和收货人手机相同
				if (StringUtil.isNotEmpty(entity.getReceiveCustomerMobilephone())
						&& entity.getReceiveCustomerMobilephone().equals(entity.getDeliveryCustomerMobilephone())) {
					flag = true;
				}
			} else {
				// 根据电话号码和联系人查询
				queryParam.setContactPhone(entity.getReceiveCustomerPhone());
				queryParam.setContactName(entity.getReceiveCustomerContact());
				// 发货人电话与联系人和收货人电话与联系人相同
				if (StringUtil.isNotEmpty(entity.getReceiveCustomerPhone())
						&& entity.getReceiveCustomerPhone().equals(entity.getDeliveryCustomerPhone())
						&& StringUtil.isNotEmpty(entity.getReceiveCustomerContact())
						&& entity.getReceiveCustomerContact().equals(entity.getDeliveryCustomerContact())) {
					flag = true;
				}
			}
			// 如果手机号与固定电话都为空，则不允许提交
			if (StringUtils.isEmpty(queryParam.getMobilePhone())
					&& StringUtils.isEmpty(queryParam.getContactPhone())) {
				throw new WaybillSubmitException("收货人手机号与固定电话不允许全部为空！");
			}
			// 查询匹配记录
			ContactEntity dto = cusContactService
					.queryCusContactByMobileOrPhoneAndName(queryParam);
			// 若匹配成功，则不生成
			if (dto != null && (dto.getCustomerCode() != null || dto.getOwnCustId() != null)) {
				CustomerEntity cus = queryCustomerService.queryCustomerByCrmCusId(dto.getCustomerCode(), dto.getOwnCustId());
				if (cus != null && StringUtil.isNotEmpty(cus.getCusCode())) {
					entity.setReceiveCustomerCode(cus.getCusCode());
					isCreate = false;
				} else {
					isCreate = true;
				}
			} else {
				// 发货人与收货人不是同一个才创建
				if (!flag) {
					isCreate = true;
				} else {
					if (StringUtil.isNotEmpty(entity.getDeliveryCustomerCode())) {
						entity.setReceiveCustomerCode(entity.getDeliveryCustomerCode());
						isCreate = false;
					} else {
						isCreate = true;
					}
				}
			}

		}

		// 如果是潜客的话，则需要调用综合接口标记潜客已开单
		if (DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(type)) {
			if (StringUtil.isNotEmpty(entity.getReceiveCustomerCode())
					&& !entity.getReceiveCustomerCode().equals(entity.getDeliveryCustomerCode())) {
				isCreate = true;
			}
		}

		// 判断运单固定号码是否与收货人手机号码相同，相同为false不创建，否则true创建
		if (StringUtil.isNotBlank(entity.getDeliveryCustomerCode())
				&& StringUtil
						.isNotBlank(entity.getReceiveCustomerMobilephone())) {
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity = customerService
					.queryNoDeletedCustInfoByCode(entity
							.getDeliveryCustomerCode());
			if (customerEntity != null) {
				String fixedMobilePhone = customerEntity
						.getFixedReceiveMobile();
				if (StringUtil.isNotEmpty(fixedMobilePhone)
						&& fixedMobilePhone.equals(entity
								.getReceiveCustomerMobilephone())) {
					isCreate = false;
				}
			}
		}

		// 判断是否需要生成客户
		if (isCreate) {
			// 定义客户信息对象
			NonfixedCustomerAssociatedInformationEntity customer = new NonfixedCustomerAssociatedInformationEntity();

			// 客户编码
			customer.setCustomerCode(entity.getReceiveCustomerCode());
			// 客户名称
			customer.setCustomerName(entity.getReceiveCustomerName());
			// 联系人名称
			customer.setLinkManName(entity.getReceiveCustomerContact());
			// 客户固定电话
			customer.setMobile(entity.getReceiveCustomerPhone());
			// 客户省
			customer.setProCode(entity.getReceiveCustomerProvCode());
			// 客户市
			customer.setCityCode(entity.getReceiveCustomerCityCode());
			// 客户区县
			customer.setCountyCode(entity.getReceiveCustomerDistCode());
			// 地址
			customer.setAddress(entity.getReceiveCustomerAddress());
			// 客户手机
			customer.setCellPhone(entity.getReceiveCustomerMobilephone());
			// 添加地址备注
			customer.setCustAddrRemark(waybillDto.getActualFreightEntity()
					.getReceiveCustomerAddressNote());
			// 业务类别(零担和快递)
			/**
			 * 优化判断是否快递
			 * @author Foss-278328-hujinyang
			 * @TIME  20160421 
			 */
//			if (waybillExpressService.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())) {
			if (isExpress(waybillDto)) {
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_EXPRESS);
			} else {
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_LTT);
			}

			// 客户所属部门标杆编码
			String unifiedCode = null;
			// 收货客户所属部门是到达部门
			// OrgAdministrativeInfoEntity o =
			// orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getCustomerPickupOrgCode());
			// 收货客户所属部门信息
			OrgAdministrativeInfoEntity o = null;
			// 如果运输性质为汽运偏线、精准空运，收货客户归属部门为该票货的发货部门
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())) {
//				o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getReceiveOrgCode());
				o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getReceiveOrgCode());
			}
			// 如果运输性质为德邦快递
			else if (isExpress(waybillDto)) {
//			else if (waybillExpressService.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())) {

				String code = entity.getReceiveMethod();
				// 判断是否自提，如果自提，收货客户归属部门为该票货的到货部门，如果是送货上楼，归属部门为该票货的收入部门
				if (WaybillConstants.SELF_PICKUP.equals(code)
						|| WaybillConstants.AIR_SELF_PICKUP.equals(code)
						|| WaybillConstants.AIR_PICKUP_FREE.equals(code)
						|| WaybillConstants.AIRPORT_PICKUP.equals(code)
						|| WaybillConstants.INNER_PICKUP.equals(code)) {
//					o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getCustomerPickupOrgCode());
					o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getCustomerPickupOrgCode());
				} else {
//					o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getReceiveOrgCode());
					o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getReceiveOrgCode());
				}
			} else {
//				o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getCustomerPickupOrgCode());
				o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getCustomerPickupOrgCode());
			}
			if (null != o) {
				unifiedCode = o.getUnifiedCode();
			}
			// if(DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(type)){
			// //收货人为潜客开单时，如果运输性质为汽运偏线、精准空运、德邦快递，则不传归属部门
			// if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())
			// ||
			// ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())
			// ||
			// ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(entity.getProductCode())){
			// customer.setUnifiedCode(null);
			// }else{
			// customer.setUnifiedCode(unifiedCode);
			// }
			// }else{
			customer.setUnifiedCode(unifiedCode);
			// }
			// 到达客户
			customer.setCustomerAttributes(DictionaryValueConstants.ARRIVE_CUSTOMER_ATTRIBUTE);
			// 客户性质（潜客OR散客）
			customer.setCustType(type);
			// 调用综合接口生成收货客户信息
			NonfixedCustomerAssociatedInformationEntity nonCus = synNonfixedCustomerToCrmSerivce
					.createNonfixedCustomer(customer);
			// 如果是散客，需要将客户编码反写到运单中
			if (DictionaryValueConstants.CUSTOMER_SC_CUSTOMER.equals(type)) {
				if (nonCus != null && nonCus.getCustomerCode() != null) {
					entity.setReceiveCustomerCode(nonCus.getCustomerCode());
				} else {
					Log.debug("***************开单生成收货客户信息---综合未生成客户编码***************" + entity.getWaybillNo());
				}
			}
		} else {
			return;
		}
	}

	/**
	 * @更新 更新GUI界面PDA补录信息状态
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-13 9:03:44
	 * @param waybillDto
	 */
	private void updateTodoMsgStatus(WaybillDto waybillDto) {
		/**
		 * DEFECT-2293 更改更新待办详情方法，根据运单更新所有待补录数据待办类型的数据
		 * serialNumber只有是在PKP_PDA_WAYBILL的类型时候才是
		 */
		pickupToDoMsgService.removeOneToDoMsg( DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL,
				null, waybillDto.getWaybillEntity().getWaybillNo());
	}

	/**
	 * 获得最终外场
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-8 下午5:53:04
	 */
	@Override
	public String queryLastOutFieldCode(WaybillEntity waybillEntity) {
		FreightRouteDto freightRouteDto = this.queryFreightRouteBySourceTarget(waybillEntity.getCreateOrgCode(),
				waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getProductCode(), new Date());
		// 最终外场
		String lastOutFieldCode = "";
		if (freightRouteDto.getFreightRouteLinelist() != null) {
			List<FreightRouteLineDto> freightRouteLinelist = freightRouteDto.getFreightRouteLinelist();// 获得走货路径list
			// 得到途径外场和 始发营业部, 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
			List<String> addressInfoList = new ArrayList<String>();
			for (FreightRouteLineDto f : freightRouteLinelist) {// 拼接走货路径
				addressInfoList.add(f.getSourceCode() + "-" + f.getTargetCode());
			}

			// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的 外场集合 同时包含 出发部门到达部门
			List<String> departmentInfoList = removeDuplicateRoute(addressInfoList);
			String lastLoadCode = "";// 最终配载部门 code
			OrgAdministrativeInfoEntity lastDepartment = null;
			// 代理网点的话从后往前取最后的一个自有部门
			for (int i = departmentInfoList.size() - 1; i > -1; i--) {
				lastLoadCode = departmentInfoList.get(i);
				lastDepartment = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastLoadCode);
				if (lastDepartment != null && lastDepartment.checkTransferCenter()) {
					break;
				} else {
					continue;
				}
			}

			if (!StringUtils.isEmpty(lastLoadCode)) {
				lastOutFieldCode = lastLoadCode;
			}
		}

		return lastOutFieldCode;
	}

	/**
	 * 获得最终外场
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-8 下午5:53:04
	 */
	@Override
	public String queryLastOutFieldCode(WaybillPendingEntity waybillEntity) {
		FreightRouteDto freightRouteDto = this.queryFreightRouteBySourceTarget(waybillEntity.getCreateOrgCode(),
				waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getProductCode(), new Date());
		// 最终外场
		String lastOutFieldCode = "";
		if (freightRouteDto.getFreightRouteLinelist() != null) {
			List<FreightRouteLineDto> freightRouteLinelist = freightRouteDto.getFreightRouteLinelist();// 获得走货路径list
			// 得到途径外场和 始发营业部, 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
			List<String> addressInfoList = new ArrayList<String>();
			for (FreightRouteLineDto f : freightRouteLinelist) {// 拼接走货路径
				addressInfoList.add(f.getSourceCode() + "-" + f.getTargetCode());
			}

			// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的 外场集合 同时包含 出发部门到达部门
			List<String> departmentInfoList = removeDuplicateRoute(addressInfoList);
			String lastLoadCode = "";// 最终配载部门 code
			OrgAdministrativeInfoEntity lastDepartment = null;
			// 代理网点的话从后往前取最后的一个自有部门
			for (int i = departmentInfoList.size() - 1; i > -1; i--) {
				lastLoadCode = departmentInfoList.get(i);
				lastDepartment = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastLoadCode);
				if (lastDepartment != null && lastDepartment.checkTransferCenter()) {
					break;
				} else {
					continue;
				}
			}

			if (!StringUtils.isEmpty(lastLoadCode)) {
				lastOutFieldCode = lastLoadCode;
			}
		}
		
		

		return lastOutFieldCode;
	}
	/**
	 * 快递冗余信息 TODO
	 * 
	 * @param waybillDto
	 */
	private String addWaybillExpress(WaybillDto waybillDto, WaybillSystemDto systemDto) {
		WaybillExpressEntity waybillExpress = waybillDto.getWaybillExpressEntity();
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();

		String expressEmpCode = waybillExpress.getExpressEmpCode();
		UserEntity user = null;
		if (StringUtil.isNotEmpty(expressEmpCode)) {
			user = userService.findByLoginName(expressEmpCode);
		}

		waybillExpress.setBillTime(systemDto.getBillTime());
		waybillExpress.setCreateTime(systemDto.getCreateTime());
		waybillExpress.setModifyDate(systemDto.getModifyTime());
		if (user != null) {
			if (user != null && user.getEmployee() != null && user.getEmployee().getDepartment() != null
					&& FossConstants.YES.equals(user.getEmployee().getDepartment().getExpressPart())) {

				waybillExpress.setExpressOrgCode(user.getEmployee().getDepartment().getCode());
				waybillExpress.setExpressOrgName(user.getEmployee().getDepartment().getName());
			} else {
				// 根据营业部查询快递点部
				ExpressPartSalesDeptResultDto dto = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(
						waybillDto.getWaybillEntity().getCreateOrgCode(), new Date());
				if (dto != null) {
					waybillExpress.setExpressOrgCode(dto.getPartCode());
					if (dto.getPartName() != null && dto.getPartName().equals(dto.getPartCode())) {
						OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getPartCode());
						if (org != null) {
							waybillExpress.setExpressOrgName(org.getName());
						}

					} else {
						waybillExpress.setExpressOrgName(dto.getPartName());
					}
				}
			}
		} else {
			// 根据营业部查询快递点部 --
			/**
			 * 收入部门对应的快递点部 BUGKD-1616运单查询明细接口：收货部门对应值取值错误，应该取收入部门（即收货部门）对应的值。
			 */
			ExpressPartSalesDeptResultDto dto = expressPartSalesDeptService
					.queryExpressPartSalesDeptBySalesCodeAndTime(waybillDto.getWaybillEntity().getReceiveOrgCode(), new Date());
			if (dto != null) {
				waybillExpress.setExpressOrgCode(dto.getPartCode());
				if (dto.getPartName() != null && dto.getPartName().equals(dto.getPartCode())) {
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getPartCode());
					if (org != null) {
						waybillExpress.setExpressOrgName(org.getName());
					}
				} else {
					waybillExpress.setExpressOrgName(dto.getPartName());
				}
			}
		}

		waybillExpress.setLastOutFieldCode(queryLastOutFieldCode(waybillEntity));
		String prdCode = waybillDto.getWaybillEntity().getProductCode();
		String orgcode = waybillDto.getWaybillEntity().getCustomerPickupOrgCode();
		if (!StringUtils.isEmpty(orgcode)) {
			String customerPickupOrgName = waybillDao.queryCustomerPickupOrgNameByWayno(orgcode, prdCode);
			waybillExpress.setCustomerPickupOrgName(customerPickupOrgName);
		}

		waybillExpress.setWaybillId(waybillDto.getWaybillEntity().getId());
		String expressId = waybillExpressService.addWaybillExpressEntity(waybillExpress);
		// 返货开单
		if (StringUtils.isNotEmpty(waybillExpress.getOriginalWaybillNo())
				&& !WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(waybillExpress.getReturnType())) {
			calculateReturnType(waybillExpress.getOriginalWaybillNo(),waybillDto);
			if(waybillEntity.isPicture( )&& CollectionUtils.isNotEmpty(waybillEntity.getWaybillNos())){
				waybillExpressService.addWaybillExpressEntityReturn(assembleWaybillExpressEntity(waybillEntity.getWaybillNos(),waybillExpress));
			}else{				
				// 生成返货开单信息
				waybillExpressService.addWaybillExpressEntityReturn(waybillExpress);			}
			// 返货开单 入库 改变原单的库存状态

			stockService.returngoodsBills(waybillExpress.getOriginalWaybillNo());
		}
		return expressId;
	}

	/***
	 * 插入快递相关轨迹到货物表 推送的字段包括：运单号、物流订单号（渠道订单号）、发生时间、跟踪信息描述、发生城市、站点类型、编号、名称、事件；
	 * 发生时间
	 * ：返货开单时间跟踪信息描述：【已进行返货开单，返货单号***】（是返货开单导致原单推送拒签时增加该部分内容）发生城市：到达部门所在城市站点类型
	 * ：根据发生部门性质进行判断 编号：到达部门编码 名称：到达部门名称 事件：FAILED
	 ****/
	private void addSynTrack(WaybillDto waybillDto) {
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		WaybillExpressEntity waybillExpress = waybillDto
				.getWaybillExpressEntity();

		
		SynTrackingEntity track = new SynTrackingEntity();
		track.setId(UUIDUtils.getUUID());
		// 运单
		track.setWayBillNo(waybill.getWaybillNo());
		if(WaybillConstants.CNJZ.equals(waybill.getOrderChannel())){
			//350909        郭倩云     大件仓配
			track.setOrderNo(waybill.getOrderNo());
		}else{
			if (!StringUtils.isEmpty(waybill.getOrderNo())) {
				DispatchOrderEntity order = dispatchOrderEntityDao
						.queryAllInfoByOrderNo(waybill.getOrderNo());
				if (order != null) {
					// 订单
					track.setOrderNo(order.getChannelNumber());
				}
			}
		}
		
		// 发生时间
		track.setOperateTime(waybill.getBillTime());
		track.setCreateDate(new Date());
		// 跟踪信息描述
		// entity.setTrackInfo("已进行返货开单，返货单号【"+waybill.getOrderNo()+"】");
		// 跟踪信息描述
		track.setTrackInfo("揽收成功，【" + waybill.getReceiveOrgName() + "】库存中");
		// 开单部门所在城市
//		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
//				.queryOrgAdministrativeInfoByCode(waybill.getReceiveOrgCode());
		String orgName = queryOrgNameMapsByCodes(waybill.getReceiveOrgCode());
		if (orgName != null) {
			track.setOperateCity(orgName);
		}
		// 产品类型
		track.setProductCode(waybill.getProductCode());
		// 站点类型
		track.setOrgType(String.valueOf(1));
		// 开单部门编码
		track.setOrgCode(waybill.getReceiveOrgCode());
		// 开单部门名称
		track.setOrgName(waybill.getReceiveOrgName());
		//目的部门名称
		track.setDestinationDeptName(waybill.getCustomerPickupOrgName());
		// 事件
		track.setEventType(String.valueOf("GOT"));
		pushTrackForCaiNiaoService.addSynLpsTrack(track);

		// 返货开单
		if (waybillExpress != null
				&& !StringUtils.isEmpty(waybillExpress.getOriginalWaybillNo())
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO
						.equals(waybillExpress.getReturnType())) {
			WaybillEntity waybillexp = queryWaybillBasicByNo(waybillExpress
					.getOriginalWaybillNo());
			if (null == waybillexp) {
				return;
			}
			SynTrackingEntity entity = new SynTrackingEntity();
			entity.setId(UUIDUtils.getUUID());
			// 运单
			entity.setWayBillNo(waybillexp.getWaybillNo());
			if(WaybillConstants.CNJZ.equals(waybillexp.getOrderChannel())){
				//350909        郭倩云     大件仓配
				entity.setOrderNo(waybillexp.getOrderNo());
			}else{
				if (waybillexp.getOrderNo() != null) {
					DispatchOrderEntity orderEntity = dispatchOrderEntityDao
							.queryAllInfoByOrderNo(waybillexp.getOrderNo());
					if (orderEntity != null) {
						// 订单
						entity.setOrderNo(orderEntity.getChannelNumber());
					}
				}
			}
			// 原单传发货开单时间
			entity.setOperateTime(waybill.getBillTime());
			entity.setCreateDate(new Date());
			// 跟踪信息描述
			entity.setTrackInfo("已进行返货开单，返货单号【" + waybill.getWaybillNo() + "】");
			
			// 开单部门所在城市
//			OrgAdministrativeInfoEntity orga = orgAdministrativeInfoService
//					.queryOrgAdministrativeInfoByCode(waybillexp
//							.getCustomerPickupOrgCode());
			OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybill.getReceiveOrgCode());
			String cityName = null;
			if (org !=null && StringUtils.isBlank(org.getCityName())) {
				cityName = administrativeRegionsService
						.gainDistrictNameByCode(org.getCityCode());
			}
			if (cityName != null) {
				entity.setOperateCity(cityName);
			} else {
				OuterBranchExpressEntity outerExp = ldpAgencyDeptService
						.queryLdpAgencyDeptByDeptCode(waybillexp
								.getCustomerPickupOrgCode());
				if (null != outerExp) {
					AdministrativeRegionsEntity city = administrativeRegionsService
							.queryAdministrativeRegionsByCode(outerExp
									.getCityCode());
					if (city != null) {
		    			entity.setOperateCity(city.getName());
		    		}
				}
			}
			// 产品类型
			entity.setProductCode(waybillexp.getProductCode());
			// 站点类型
			entity.setOrgType(String.valueOf(1));
			// 开单部门编码
			entity.setOrgCode(waybillexp.getCustomerPickupOrgCode());
			// 开单部门名称
			entity.setOrgName(waybillexp.getCustomerPickupOrgName());
			//目的部门
			entity.setDestinationDeptName(waybillexp.getReceiveOrgName());
			// 事件
			entity.setEventType(String.valueOf("FAILED"));
			pushTrackForCaiNiaoService.addSynTrack(entity);
		}
	}

	/**
	 * 处理PDA补录单的待办信息
	 * 
	 * @author WangQianJin
	 * @date 2013-07-24
	 */
	@Transactional
	@Override
	public void handlePDAPendingTODO(WaybillDto waybillDto,
			WaybillPendingEntity waybillPendingEntity) {
		// 对可能出现的异常进行捕捉
		try {
			// 运单基本信息
			WaybillEntity waybill = new WaybillEntity();
			waybill = waybillDto.getWaybillEntity();
			if (waybill != null && waybillPendingEntity != null) {
				// 获取PDA变更信息
				PDAWayBillRfcDto dto = getPDAChangeInfo(waybillPendingEntity, waybill, waybillDto);
				if (dto != null && dto.isBusinessChange()) {
					// 封装更改单信息(pkp.t_srv_waybill)
					packageWaybillRfcEntity(dto, waybillDto);
					// 封装起草审核受理记录信息(pkp.t_srv_waybillrfc_actionhistory)
					packageWaybillRfcActionHistoryEntity(dto, waybillDto);
					// 封装待办信息(PKP.T_SRV_PENDING_TODO)
					packagePendingTodoEntity(dto, waybillDto);
					// 添加PDA更改待办信息
					waybillRfcService.addPDAPendingForRfcInfo(dto);
				}
			}
		} catch (Exception e) {
			// 添加运单提交的异常日志
			LOG.error("运单提交时，调用【处理PDA补录单的待办信息】方法异常！", e);
			throw new WaybillImportException("运单提交时，调用【处理PDA补录单的待办信息】方法异常！", e.getMessage());
		}
	}

	/**
	 * 获取PDA变更信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-26 上午9:00:28
	 */
	private PDAWayBillRfcDto getPDAChangeInfo(
			WaybillPendingEntity waybillpending, WaybillEntity waybill,
			WaybillDto waybillDto) {
		PDAWayBillRfcDto dto = new PDAWayBillRfcDto();
		// 是否业务变更(涉及到标签更改的变更项)
		boolean isBusinessChange = false;
		// 变更项列表
		List<WaybillRfcChangeDetailEntity> changeList = new ArrayList<WaybillRfcChangeDetailEntity>();
		// 默认非财务类变更
		dto.setIsFinanceChange(FossConstants.NO);
		if (waybillpending != null && waybill != null) {
			/**
			 * 将业务变更数据进行转换
			 */
			// 运输性质
			waybillpending.setProductCode(convertValueToStr(waybillpending.getProductCode()));
			waybill.setProductCode(convertValueToStr(waybill.getProductCode()));
			// 目的站
			waybillpending.setTargetOrgCode(convertValueToStr(waybillpending.getTargetOrgCode()));
			waybill.setTargetOrgCode(convertValueToStr(waybill.getTargetOrgCode()));
			// 提货网点
			waybillpending.setCustomerPickupOrgCode(convertValueToStr(waybillpending.getCustomerPickupOrgCode()));
			waybill.setCustomerPickupOrgCode(convertValueToStr(waybill.getCustomerPickupOrgCode()));
			// 件数
			waybillpending.setGoodsQtyTotal(convertValueToInteger(waybillpending.getGoodsQtyTotal()));
			waybill.setGoodsQtyTotal(convertValueToInteger(waybill.getGoodsQtyTotal()));
			// 纸包装
			waybillpending.setPaperNum(convertValueToInteger(waybillpending.getPaperNum()));
			waybill.setPaperNum(convertValueToInteger(waybill.getPaperNum()));
			// 木包装
			waybillpending.setWoodNum(convertValueToInteger(waybillpending.getWoodNum()));
			waybill.setWoodNum(convertValueToInteger(waybill.getWoodNum()));
			// 纤包装
			waybillpending.setFibreNum(convertValueToInteger(waybillpending.getFibreNum()));
			waybill.setFibreNum(convertValueToInteger(waybill.getFibreNum()));
			// 托包装
			waybillpending.setSalverNum(convertValueToInteger(waybillpending.getSalverNum()));
			waybill.setSalverNum(convertValueToInteger(waybill.getSalverNum()));
			// 膜包装
			waybillpending.setMembraneNum(convertValueToInteger(waybillpending.getMembraneNum()));
			waybill.setMembraneNum(convertValueToInteger(waybill.getMembraneNum()));
			// 其他包装
			waybillpending.setOtherPackage(convertValueToStr(waybillpending.getOtherPackage()));
			waybill.setOtherPackage(convertValueToStr(waybill.getOtherPackage()));
			// 货物类型
			waybillpending.setGoodsTypeCode(convertValueToStr(waybillpending.getGoodsTypeCode()));
			waybill.setGoodsTypeCode(convertValueToStr(waybill.getGoodsTypeCode()));
			// 提货方式
			waybillpending.setReceiveMethod(convertValueToStr(waybillpending.getReceiveMethod()));
			waybill.setReceiveMethod(convertValueToStr(waybill.getReceiveMethod()));
			// 单号变更
			waybillpending.setWaybillNo(convertValueToStr(waybillpending.getWaybillNo()));
			waybill.setWaybillNo(convertValueToStr(waybill.getWaybillNo()));
			/**
			 * 判断是否涉及标签变更项
			 */
			// 运输性质是否变更
			if (!waybill.getProductCode().equals(waybillpending.getProductCode())) {
				isBusinessChange = true;
				WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
				change.setRfcItems("productCode");
				change.setRfcItemsName("运输性质");
				String pendingProduct = waybillpending.getProductCode();
				String wproduct = waybill.getProductCode();
				// 设置变更前的运输性质名称
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(pendingProduct)) {
					// 获取收货部门的运输性质
					List<ProductEntity> productList = waybillRfcService.queryTransType(waybillpending.getReceiveOrgCode());
					if (CollectionUtils.isNotEmpty(productList)) {
						for (ProductEntity product : productList) {
							if (pendingProduct.equals(product.getCode())) {
								pendingProduct = convertValueToStr(product.getName());
								break;
							}
						}
					}
				}
				// 设置变更后的运输性质名称
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(wproduct)) {
					// 获取收货部门的运输性质
					List<ProductEntity> productList = waybillRfcService.queryTransType(waybill.getReceiveOrgCode());
					if (productList != null && productList.size() > 0) {
						for (ProductEntity product : productList) {
							if (wproduct.equals(product.getCode())) {
								wproduct = convertValueToStr(product.getName());
								break;
							}
						}
					}
				}
				change.setBeforeRfcInfo(pendingProduct);
				change.setAfterRfcInfo(wproduct);
				change.setVisiable(FossConstants.YES);
				changeList.add(change);
				dto.setIsFinanceChange(FossConstants.YES);
			}
			// if(!waybill.getTargetOrgCode().equals(waybillpending.getTargetOrgCode())){
			// dto.setIsTargetChange(FossConstants.YES);
			// dto.setIsFinanceChange(FossConstants.YES);
			// }else{
			// dto.setIsTargetChange(FossConstants.NO);
			// }
			// 提货网点是否变更
			if (!waybill.getCustomerPickupOrgCode().equals(waybillpending.getCustomerPickupOrgCode())) {
				isBusinessChange = true;
				WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
				change.setRfcItems("customerPickupOrgName");
				change.setRfcItemsName("提货网点");
				String pendingOrgName = waybillpending.getCustomerPickupOrgCode();
				String wayOrgName = waybill.getCustomerPickupOrgCode();
				/**
				 * 设置变更前提货网点名称
				 */
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(waybillpending.getCustomerPickupOrgCode())) {
					pendingOrgName = getCustomerPickUpNameByCode(waybillpending.getCustomerPickupOrgCode(), waybillpending.getProductCode(), waybillpending.getBillTime());
					pendingOrgName = convertValueToStr(pendingOrgName);
				}
				/**
				 * 设置变更后提货网点名称
				 */
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(waybill.getCustomerPickupOrgCode())) {
					// 根据产品来判断是查询自有网点还是代理网点
					wayOrgName = getCustomerPickUpNameByCode(waybill.getCustomerPickupOrgCode(), waybill.getProductCode(), waybill.getBillTime());
					wayOrgName = convertValueToStr(wayOrgName);
				}
				// 设置变更后提货网点名称
				waybill.setCustomerPickupOrgName(convertValueToStr(waybill.getCustomerPickupOrgName()));
				change.setBeforeRfcInfo(pendingOrgName);
				change.setAfterRfcInfo(wayOrgName);
				change.setVisiable(FossConstants.YES);
				changeList.add(change);
				dto.setIsFinanceChange(FossConstants.YES);
				dto.setIsTargetChange(FossConstants.YES);
			} else {
				dto.setIsTargetChange(FossConstants.NO);
			}

			/**
			 * 收货人地址，区县是否发生了变更 这里主要是封装要生成待办事项的明细，即（变更的明细）
			 * 
			 * @date 2014-11-11
			 * @atuhor wutao 200945
			 *         业务逻辑：PDA传递过来的待补录的订单，在开单的时候，收货人地址即区县发送改变的时候，生成待办事项，
			 *         但是，前提是：当前的提货网带是驻地营业部，如果不是驻地营业部，即使收货人的地址区县发生了改变也不会生成待办事项
			 */
			if (checkDeptIsStation(waybillpending)) {
				if (StringUtils.isNotEmpty(waybill.getReceiveCustomerDistCode()) && StringUtils.isNotEmpty(waybillpending.getReceiveCustomerDistCode())) {
					if (!waybill.getReceiveCustomerDistCode().equals(waybillpending.getReceiveCustomerDistCode())) {
						isBusinessChange = true;
						WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
						change.setRfcItems("receiveCustomerArea");
						change.setRfcItemsName("收货人区县");
						// 获取对应的区县
						String breforeRegionCounty = administrativeRegionsService.gainDistrictNameByCode(waybillpending.getReceiveCustomerDistCode());
						String afterRegionCounty = administrativeRegionsService.gainDistrictNameByCode(waybill.getReceiveCustomerDistCode());
						change.setBeforeRfcInfo(breforeRegionCounty);
						change.setAfterRfcInfo(afterRegionCounty);
						change.setVisiable(FossConstants.YES);
						changeList.add(change);
					}
				}
			}
			// wutao ==== end
			// 件数是否变更
			boolean changeQty = false;
			if (waybill.getGoodsQtyTotal().intValue() != waybillpending.getGoodsQtyTotal().intValue()) {
				isBusinessChange = true;
				changeQty = true;
				WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
				change.setRfcItems("goodsQtyTotal");
				change.setRfcItemsName("件数");
				change.setBeforeRfcInfo(waybillpending.getGoodsQtyTotal().toString());
				change.setAfterRfcInfo(waybill.getGoodsQtyTotal().toString());
				change.setVisiable(FossConstants.YES);
				changeList.add(change);
			}
			// 纸是否变更
			// KDTE-4700 -- 小件如果改了包装 是不需要生成更改单的 但是如果发生了件数变更 是需要重新打标签的
//			if ((!waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())) || changeQty) {
			if ((!isExpress(waybillDto) || changeQty)) {
				if (waybill.getPaperNum().intValue() != waybillpending.getPaperNum().intValue()) {


					isBusinessChange = true;
					WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
					change.setRfcItems("paper");
					change.setRfcItemsName("纸");
					change.setBeforeRfcInfo(waybillpending.getPaperNum()
							.toString());
					change.setAfterRfcInfo(waybill.getPaperNum().toString());
					change.setVisiable(FossConstants.YES);
					changeList.add(change);
				}
				// 木是否变更
				if (waybill.getWoodNum().intValue() != waybillpending
						.getWoodNum().intValue()) {
					isBusinessChange = true;
					WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
					change.setRfcItems("wood");
					change.setRfcItemsName("木");
					change.setBeforeRfcInfo(waybillpending.getWoodNum()
							.toString());
					change.setAfterRfcInfo(waybill.getWoodNum().toString());
					change.setVisiable(FossConstants.YES);
					changeList.add(change);
				}
				// 纤是否变更
				if (waybill.getFibreNum().intValue() != waybillpending
						.getFibreNum().intValue()) {
					isBusinessChange = true;
					WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
					change.setRfcItems("fibre");
					change.setRfcItemsName("纤");
					change.setBeforeRfcInfo(waybillpending.getFibreNum()
							.toString());
					change.setAfterRfcInfo(waybill.getFibreNum().toString());
					change.setVisiable(FossConstants.YES);
					changeList.add(change);
				}
				// 托是否变更
				if (waybill.getSalverNum().intValue() != waybillpending
						.getSalverNum().intValue()) {
					isBusinessChange = true;
					WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
					change.setRfcItems("salver");
					change.setRfcItemsName("托");
					change.setBeforeRfcInfo(waybillpending.getSalverNum()
							.toString());
					change.setAfterRfcInfo(waybill.getSalverNum().toString());
					change.setVisiable(FossConstants.YES);
					changeList.add(change);
				}
				// 膜是否变更
				if (waybill.getMembraneNum().intValue() != waybillpending
						.getMembraneNum().intValue()) {
					isBusinessChange = true;
					WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
					change.setRfcItems("membrane");
					change.setRfcItemsName("膜");
					change.setBeforeRfcInfo(waybillpending.getMembraneNum()
							.toString());
					change.setAfterRfcInfo(waybill.getMembraneNum().toString());
					change.setVisiable(FossConstants.YES);
					changeList.add(change);
				}
				// 其他包装是否变更
				if (!waybill.getOtherPackage().equals(
						waybillpending.getOtherPackage())) {
					isBusinessChange = true;
					WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
					change.setRfcItems("otherPackage");
					change.setRfcItemsName("其他包装");
					change.setBeforeRfcInfo(waybillpending.getOtherPackage());
					change.setAfterRfcInfo(waybill.getOtherPackage());
					change.setVisiable(FossConstants.YES);
					changeList.add(change);
				}
			}
			// KDTE-4700 -- 小件如果改了包装 是不需要生成更改单的 end

			// 货物类型是否变更
			if (!waybill.getGoodsTypeCode().equals(
					waybillpending.getGoodsTypeCode())) {
				isBusinessChange = true;
				WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
				change.setRfcItems("goodsType");
				change.setRfcItemsName("货物类型");
				String pendingGoodType = waybillpending.getGoodsTypeCode();
				String wgoodType = waybill.getGoodsTypeCode();
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(pendingGoodType)) {
					// 设置货物类型
					if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillpending.getProductCode())) {
						GoodsTypeEntity goodType = goodsTypeService.queryGoodsTypeByGoodTypeCode(pendingGoodType, waybillpending.getBillTime());
						if (goodType != null) {
							pendingGoodType = convertValueToStr(goodType.getName());
						}
					}
				}
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(wgoodType)) {
					// 设置货物类型
					if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
						GoodsTypeEntity goodType = goodsTypeService.queryGoodsTypeByGoodTypeCode(wgoodType, waybill.getBillTime());
						if (goodType != null) {
							wgoodType = convertValueToStr(goodType.getName());
						}
					}
				}
				change.setBeforeRfcInfo(pendingGoodType);
				change.setAfterRfcInfo(wgoodType);
				change.setVisiable(FossConstants.YES);
				changeList.add(change);
				dto.setIsFinanceChange(FossConstants.YES);
			}
			// 提货方式是否变更
			if (!waybill.getReceiveMethod().equals(waybillpending.getReceiveMethod())) {
				isBusinessChange = true;
				WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
				change.setRfcItems("receiveMethod");
				change.setRfcItemsName("提货方式");
				String pendingReceiveMethod = waybillpending.getReceiveMethod();
				String wreceiveMethod = waybill.getReceiveMethod();
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(pendingReceiveMethod)) {
					List<DataDictionaryValueEntity> list = null;
					// 设置变更前提货方式名称
					if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillpending.getProductCode())) {
						list = pkpdataDictionaryValueDao.queryByTermsCode(WaybillConstants.PICKUP_GOODS_AIR);
					} else {
						list = pkpdataDictionaryValueDao.queryByTermsCode(WaybillConstants.PICKUP_GOODS_HIGHWAYS);
					}
					if (list != null && list.size() > 0) {
						for (DataDictionaryValueEntity entity : list) {
							if (null != entity && pendingReceiveMethod.equals(entity.getValueCode())) {
								pendingReceiveMethod = convertValueToStr(entity.getValueName());
								break;
							}
						}
					}
				}
				if (!WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(wreceiveMethod)) {
					List<DataDictionaryValueEntity> list = null;
					// 设置变更后提货方式名称
					if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
						list = pkpdataDictionaryValueDao.queryByTermsCode(WaybillConstants.PICKUP_GOODS_AIR);
					} else {
						list = pkpdataDictionaryValueDao.queryByTermsCode(WaybillConstants.PICKUP_GOODS_HIGHWAYS);
					}
					if (list != null && list.size() > 0) {
						for (DataDictionaryValueEntity entity : list) {
							if (null != entity && wreceiveMethod.equals(entity.getValueCode())) {
								wreceiveMethod = convertValueToStr(entity.getValueName());
								break;
							}
						}
					}
				}
				change.setBeforeRfcInfo(pendingReceiveMethod);
				change.setAfterRfcInfo(wreceiveMethod);
				change.setVisiable(FossConstants.YES);
				changeList.add(change);
				dto.setIsFinanceChange(FossConstants.YES);
			}
			// 运单号是否变更
			if (!waybill.getWaybillNo().equals(waybillpending.getWaybillNo())) {
				isBusinessChange = true;
				WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
				change.setRfcItems("waybillNo");
				change.setRfcItemsName("运单号");
				change.setBeforeRfcInfo(waybillpending.getWaybillNo());
				change.setAfterRfcInfo(waybill.getWaybillNo());
				change.setVisiable(FossConstants.YES);
				changeList.add(change);
				dto.setIsFinanceChange(FossConstants.YES);
				dto.setIsChangeWaybillNo(FossConstants.YES);
			} else {
				dto.setIsChangeWaybillNo(FossConstants.NO);
			}
		}
		dto.setBusinessChange(isBusinessChange);
		dto.setChangeDetailList(changeList);
		return dto;
	}

	/**
	 * @author 200945-wutao
	 * @date 2014-11-11 此方法的功能，主要是提货网点是否是驻地营业部 当收货人地址发生改变并且 提货网点是驻地营业部的时候生成待办事项
	 *       排除偏线，空运，快递
	 * @param WaybillPendingEntity
	 * @return boolean
	 */
	protected boolean checkDeptIsStation(WaybillPendingEntity waybillPendingEntity) {
		// 排除偏线，空运，快递
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillPendingEntity.getProductCode())
				&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillPendingEntity.getProductCode())
				&& !waybillExpressService.onlineDetermineIsExpressByProductCode(waybillPendingEntity.getProductCode(), waybillPendingEntity.getBillTime())) {
//			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(WaybillPendingEntity.getCustomerPickupOrgCode());
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(waybillPendingEntity.getCustomerPickupOrgCode());

			// 判断是否为驻地部门
			if (saleDepartmentEntity.getStation() != null
					&& FossConstants.YES.equals(saleDepartmentEntity
							.getStation())) {
				// 如果【驻地营业部】,并且更改后的方式为非自提的话，那就满足生成待办事项的前提
				if (!WaybillConstants.SELF_PICKUP.equals(waybillPendingEntity
						.getReceiveMethod())
						&& !WaybillConstants.INNER_PICKUP
								.equals(waybillPendingEntity.getReceiveMethod())
						&& !WaybillConstants.AIR_PICKUP_FREE
								.equals(waybillPendingEntity.getReceiveMethod())
						&& !WaybillConstants.AIRPORT_PICKUP
								.equals(waybillPendingEntity.getReceiveMethod())
						&& !WaybillConstants.AIR_SELF_PICKUP
								.equals(waybillPendingEntity.getReceiveMethod())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据产品来判断是查询自有网点还是代理网点
	 * 
	 * @author WangQianJin
	 * @date 2013-7-26 上午9:45:42
	 */
	private boolean isAgentDept(String productCode) {
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
				.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
						.equals(productCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**	 * 转换空数据
	 * 
	 * @author WangQianJin
	 * @date 2013-7-26 上午9:45:42
	 */
	private String convertValueToStr(String val) {
		if (StringUtil.isEmpty(val)) {
			return WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT;
		} else {
			return val;
		}
	}

	/**
	 * 转换空数据
	 * 
	 * @author WangQianJin
	 * @date 2013-7-26 上午9:45:42
	 */
	private Integer convertValueToInteger(Integer val) {
		if (val == null) {
			return 0;
		} else {
			return val;
		}
	}

	/**
	 * 封装更改单信息(pkp.t_srv_waybill)
	 * 
	 * @author WangQianJin
	 * @date 2013-7-25 下午5:15:19
	 */
	private void packageWaybillRfcEntity(PDAWayBillRfcDto dto, WaybillDto waybillDto) {
		WaybillRfcEntity waybillRfcEntity = null;
		// 如果是业务变更，则添加至变更信息
		if (dto.isBusinessChange()) {
			waybillRfcEntity = new WaybillRfcEntity();
			waybillRfcEntity.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
			waybillRfcEntity.setRfcSource(WaybillRfcConstants.INSIDE_REQUIRE);
			waybillRfcEntity.setRfcType(WaybillRfcConstants.PDA_CHANGE);
			waybillRfcEntity.setRfcReason("PDA补录时涉及到标签更改的变更项，系统自动变更！");
			waybillRfcEntity.setDraftOrgCode(waybillDto.getCurrentInfo().getCurrentDeptCode());
			waybillRfcEntity.setDraftOrgName(waybillDto.getCurrentInfo().getCurrentDeptName());
			waybillRfcEntity.setDrafter(waybillDto.getCurrentInfo().getEmpName());
			waybillRfcEntity.setDrafterCode(waybillDto.getCurrentInfo().getEmpCode());
			waybillRfcEntity.setDraftTime(new Date());
			waybillRfcEntity.setNotes("PDA补录系统自动同意！");
			waybillRfcEntity.setStatus(WaybillRfcConstants.ACCECPT);
			waybillRfcEntity.setOperator(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			waybillRfcEntity.setOperatorCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			waybillRfcEntity.setOperateOrgCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			waybillRfcEntity.setOperateOrgName(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			waybillRfcEntity.setOperateTime(new Date());
			waybillRfcEntity.setOldVersionWaybillId(waybillDto.getWaybillEntity().getId());
			waybillRfcEntity.setNewVersionWaybillId(waybillDto.getWaybillEntity().getId());
			waybillRfcEntity.setIsFinanceChange(dto.getIsFinanceChange());
			// 变更内容
			StringBuffer sb = new StringBuffer();
			if (dto.getChangeDetailList() != null && dto.getChangeDetailList().size() > 0) {
				for (WaybillRfcChangeDetailEntity detailDto : dto.getChangeDetailList()) {
					if (FossConstants.YES.equals(detailDto.getVisiable())) {
						sb.append(detailDto.getRfcItemsName());
						sb.append(":");
						sb.append(detailDto.getBeforeRfcInfo());
						sb.append("->");
						sb.append(detailDto.getAfterRfcInfo());
						sb.append(";");
					}
				}
			}
			waybillRfcEntity.setChangeItems(sb.toString());
			// 是否给发货人发送短信
			waybillRfcEntity.setDeliverSms(FossConstants.NO);
			// 是否给收货人发送短信
			waybillRfcEntity.setReceiverSms(FossConstants.NO);
			// 是否目的站修改
			waybillRfcEntity.setIsChangeDestination(dto.getIsTargetChange());
			// 是否需要财务核销
			waybillRfcEntity.setNeedWriteOff(FossConstants.NO);
			// 是否变更运单号
			waybillRfcEntity.setIsChangeWaybillNo(dto.getIsChangeWaybillNo());
			// 是否自动受理
			waybillRfcEntity.setIsLabourHandle(FossConstants.YES);
		}
		dto.setWaybillRfcEntity(waybillRfcEntity);
	}

	/**
	 * 封装起草审核受理记录信息(pkp.t_srv_waybillrfc_actionhistory)
	 * 
	 * @author WangQianJin
	 * @date 2013-7-25 下午5:15:19
	 */
	private void packageWaybillRfcActionHistoryEntity(PDAWayBillRfcDto dto,
			WaybillDto waybillDto) {
		List<WaybillRfcActionHistoryEntity> rfcActionList = null;
		// 如果是业务变更，则添加至记录信息
		if (dto.isBusinessChange()) {
			rfcActionList = new ArrayList<WaybillRfcActionHistoryEntity>();
			WaybillRfcActionHistoryEntity actionEntity = new WaybillRfcActionHistoryEntity();
			// 自动起草
			actionEntity.setNotes("PDA补录运单时涉及到标签更改的变更项，系统自动起草更改单！");
			actionEntity.setStatus(WaybillRfcConstants.PRE_AUDIT);
			actionEntity.setOperator(waybillDto.getCurrentInfo().getEmpName());
			actionEntity.setOperatorCode(waybillDto.getCurrentInfo().getEmpCode());
			actionEntity.setOperateOrgCode(waybillDto.getCurrentInfo().getCurrentDeptCode());
			actionEntity.setOperateOrgName(waybillDto.getCurrentInfo().getCurrentDeptName());
			actionEntity.setOperateTime(new Date());
			rfcActionList.add(actionEntity);
			// 自动审核
			actionEntity = new WaybillRfcActionHistoryEntity();
			actionEntity.setNotes("PDA补录运单，系统自动审核同意！");
			actionEntity.setStatus(WaybillRfcConstants.PRE_ACCECPT);
			actionEntity.setOperator(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperatorCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperateOrgCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperateOrgName(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperateTime(new Date());
			rfcActionList.add(actionEntity);
			// 自动受理
			actionEntity = new WaybillRfcActionHistoryEntity();
			actionEntity.setNotes("PDA补录运单，系统自动受理同意！");
			actionEntity.setStatus(WaybillRfcConstants.ACCECPT);
			actionEntity.setOperator(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperatorCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperateOrgCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperateOrgName(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
			actionEntity.setOperateTime(new Date());
			rfcActionList.add(actionEntity);
		}
		dto.setRfcActionList(rfcActionList);
	}

	/**
	 * 封装待办信息(PKP.T_SRV_PENDING_TODO)
	 * 
	 * @author WangQianJin
	 * @date 2013-7-25 下午5:15:19
	 */
	private void packagePendingTodoEntity(PDAWayBillRfcDto dto, WaybillDto waybillDto) {
		PendingTodoEntity pendingTodoEntity = null;
		// 如果是业务变更，则添加至记录信息
		if (dto.isBusinessChange()) {
			pendingTodoEntity = new PendingTodoEntity();
			pendingTodoEntity.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
		}
		dto.setPendingTodoEntity(pendingTodoEntity);
	}

	/**
	 * 增加运单地址临时表信息 =========== 业务规则： 1、“车牌号”和“区域”的组合不可重复； 2、一辆车只能对应一种“车辆职责类型”；
	 * 3、一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。
	 * 当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。
	 * 在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
	 * 4、当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车
	 * 5、查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；
	 * 查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆 6、用户登录后默认显示对应车队的定人定区信息，且不可编辑
	 * 7、当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。
	 * 用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。 ============
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-27 下午3:05:25
	 */
	private void addReceiveAddressRfc(WaybillEntity waybillEntity) {
		// 数据非空判断
		if (null == waybillEntity || StringUtils.isEmpty(waybillEntity.getReceiveCustomerAddress())) {
			return;
		}
		// 临时地址对象
		ReceiveAddressRfcEntity address = new ReceiveAddressRfcEntity();
		// 运单号
		address.setWaybillNo(waybillEntity.getWaybillNo());
		// 收货地址
		address.setReceiveCustomerAddress(waybillEntity.getReceiveCustomerAddress());
		// 省CODE
		address.setReceiveCustomerProvCode(waybillEntity.getReceiveCustomerProvCode());
		// 市CODE
		address.setReceiveCustomerCityCode(waybillEntity.getReceiveCustomerCityCode());
		// 区CODE
		address.setReceiveCustomerCountyCode(waybillEntity.getReceiveCustomerDistCode());
		// 收货客户手机
		address.setReceiveCustomerMobilephone(waybillEntity.getReceiveCustomerMobilephone());
		// 收货客户电话
		address.setReceiveCustomerPhone(waybillEntity.getReceiveCustomerPhone());
		// 将封装的对象传入
		receiveAddressRfcService.addReceiveAddressRfcEntity(address);
	}

	/**
	 * 使用优惠券
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-27 上午8:23:13
	 */
	public void useCoupon(WaybillDto waybillDto) {
		// 设置优惠券为已使用
		waybillDto.getCouponInfoDto().setUsed(true);
		// 校验并更新优惠券信息
		crmOrderService.validateCoupon(waybillDto.getCouponInfoDto());
	}

	/**
	 * 给收货客户或发货客户发送短信
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午8:23:13
	 */
	public void sendSmsToCust(WaybillDto waybillDto) {
		// 对可能出现的异常进行捕捉
		try {
			// 运单基本信息
			WaybillEntity waybill = waybillDto.getWaybillEntity();
			// 判断是否是上门接货
			if (FossConstants.YES.equals(waybill.getPickupToDoor())) {
				// 给发货客户发送短信
				sendSmsVoiceService.sendShipperSms(waybill);
			}

			// 给收货客户发送短信
			sendSmsVoiceService.sendConsigneeSms(waybill);
		} catch (Exception e) {
			// 添加运单提交的异常日志
			LOG.error("运单提交时，调用【发送短信】方法异常！", e);
		}
	}

	/**
	 * update:
	 * 
	 * @data 2014年7月10日 下午4:30:16 添加状态码的判断：业务需求：分成三种状态 1.发件人短信批量发送 2.发件人短信停发
	 *       3.收件人短信停发 0表示的是未选中 1表示的是选中 发件人短信批量发送
	 *       当CRM勾选发件人短信批量发送时，停发快递订单调度受理短信、快递签收收货人短信、签收单返单短信，次日向客户发送批量打包短信
	 *       发件人短信停发 当CRM勾选发件人短信停发时，停发快递订单调度受理短信、快递签收发件人短信。 收件人短信停发
	 *       当CRM勾选收件人短信停发时，停止发送快递派送收货人短信、快递开单收货人短信、快递签收收货人短信
	 *       ####################### 快递开单收货人短信 保存待处理发送短信信息
	 * 
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public void savePendingSendMailInfo(WaybillDto waybillDto) {
		// 对可能出现的异常进行捕捉
		try {
			// 运单基本信息
			WaybillEntity waybill = waybillDto.getWaybillEntity();
			/**
			 * @项目：新客户体验项目
			 * @功能：取出运单冗余信息
			 * @author:218371-foss-zhaoyanjun
			 * @date:015-07-17上午10:07
			 */
			ActualFreightEntity af = waybillDto.getActualFreightEntity();
			// 创建待处理发送短信实体类
			PendingSendSMSEntity entity = new PendingSendSMSEntity();
			// 通知类型
			entity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
			if (waybillDto.getCurrentInfo() != null) {
				// 操作人
				entity.setOperator(waybillDto.getCurrentInfo().getEmpName());
				// 操作人编码
				entity.setOperatorNo(waybillDto.getCurrentInfo().getEmpCode());
				// 操作部门
				entity.setOperateOrgName(waybillDto.getCurrentInfo().getCurrentDeptName());
				// 操作部门编码
				entity.setOperateOrgCode(waybillDto.getCurrentInfo().getCurrentDeptCode());
			}
			// 操作时间
			entity.setOperateTime(new Date());
			// 运单号
			entity.setWaybillNo(waybill.getWaybillNo());
			// 模块名称
			entity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILL);
			// if(WaybillConstants.PACKAGE.equals(waybillDto.getWaybillEntity().getProductCode())){
			// entity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILLEXP);
			// }else{
			// entity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILL);
			// }
			// 发货人名称
			entity.setDeliveryCustomerContact(waybill.getDeliveryCustomerContact());
			// 总件数
			entity.setGoodsQtyTotal(waybill.getGoodsQtyTotal());
			// 收货人地址
			entity.setReceiveCustomerAddress(waybill.getReceiveCustomerAddress());
			// 总费用
			entity.setTotalFee(waybill.getTotalFee());
			// 提货网点
			entity.setCustomerPickupOrgCode(waybill.getCustomerPickupOrgCode());
			
			// 快递短信
//			if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())) {
			/**
			 * @author Foss-278328-hujinyang
			 * @TIME 20160421
			 */
//			if (isExpress(waybillDto)) {
				// 更改 根据crm端传递过来的收件人手机号码是否为某个固定号码判断 是否需要快递开单收货人短信
//				Boolean isDeliveryCustomerMobileOK = true;
//                LOG.info("++++++判断收件人手机号是否为固定号+++++++"+isDeliveryCustomerMobileOK);
//				String deliveryCustomerCode = waybill.getDeliveryCustomerCode();
//				 LOG.info("++++++发货客户编码+++++++"+deliveryCustomerCode);
//				if (org.apache.commons.lang3.StringUtils
//						.isNotEmpty(deliveryCustomerCode)) {
//
//					 deliveryCustomerEntity = customerService
//							.queryCustInfoByCode(deliveryCustomerCode);
//					 LOG.info("++++++发货客户+++++++"+deliveryCustomerEntity);
//		 			if (deliveryCustomerEntity != null) {
//						String fixedReceiveMobile = deliveryCustomerEntity
//								.getFixedReceiveMobile();
//						String ReceiveCustomerMobilephone = StringUtil
//								.trim(waybill.getReceiveCustomerMobilephone());
//						LOG.info("++++++fixedReceiveMobile+++++++"+fixedReceiveMobile);
//						LOG.info("++++++ReceiveCustomerMobilephone+++++++"+ReceiveCustomerMobilephone);
//						if (StringUtil.isBlank(ReceiveCustomerMobilephone)) {
//							// 停发短信
//							isDeliveryCustomerMobileOK = false;
//							LOG.info("++++++空+++++++"+isDeliveryCustomerMobileOK);
//						} else if (StringUtil
//								.isNotBlank(ReceiveCustomerMobilephone)
//								&& !StringUtil
//										.isNumeric(ReceiveCustomerMobilephone)) {
//							// 停发短信
//							isDeliveryCustomerMobileOK = false;
//							LOG.info("++++++数字+++++++"+isDeliveryCustomerMobileOK);
//						} else if (StringUtil
//								.isNotBlank(ReceiveCustomerMobilephone)
//								&& StringUtil.isNotBlank(fixedReceiveMobile)
//								&& fixedReceiveMobile
//										.equals(ReceiveCustomerMobilephone)) {
//							isDeliveryCustomerMobileOK = false;
//							LOG.info("++++++固定+++++++"+isDeliveryCustomerMobileOK);
//						}
//					}
//				}
//
				// 收件人号码是否满足发送短信要求
//				boolean isReceiveCustomerMobilephoneOK = true;
//				/**
//				 * 以前业务是根据收货客户编码去查改为发货客户编码去查
//				 */
//				String receiveCustomerCode = waybill.getReceiveCustomerCode();
//				//String deliveryCustomerCode_=waybill.getDeliveryCustomerCode();
//
//				if (org.apache.commons.lang3.StringUtils
//						.isNotEmpty(receiveCustomerCode)) {
//
//					CustomerEntity customerEntity_ = customerDao.queryCustStateByCode(receiveCustomerCode);
//
//					if (customerEntity_ != null) {
//						/*	String receiverSms = customerEntity.getReceiverSms();
//						if (org.apache.commons.lang3.StringUtils
//								.isNotEmpty(receiverSms)
//								&& receiverSms
//										.equals(OrderConstant.STOP_MESSAGE_FOR_RECEIPT)) {*/
//						if (!isSendMessage(deliveryCustomerEntity,customerEntity_)) {
//							//停发短信
//							isReceiveCustomerMobilephoneOK = false;
//						} else {
//							// 需要发送 快递开单收货人短信
//							isReceiveCustomerMobilephoneOK = true;
//						}
//					} else {
//						// 需要发送 快递开单收货人短信
//						isReceiveCustomerMobilephoneOK = true;
//					}
//				} else {
//					// 需要发送 快递开单收货人短信
//					isReceiveCustomerMobilephoneOK = true;
//				}
//				LOG.info("++++++isDeliveryCustomerMobileOK+++++++"+isDeliveryCustomerMobileOK);
//				LOG.info("++++++isReceiveCustomerMobilephoneOK+++++++"+isReceiveCustomerMobilephoneOK);
//				//收件人和寄件人号码必须同时满足，才发送短信
//				if (isDeliveryCustomerMobileOK == true && isReceiveCustomerMobilephoneOK == true) {
//					if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(waybill.getReturnType())){
//						WaybillExpressEntity oldWaybillNoEntity = waybillExpressService.queryWaybillExpressByNo(waybillDto.getWaybillExpressEntity().getOriginalWaybillNo());
//						// 模块名称
//						entity.setModuleName("PKP_WAYBILL_EXP"); // PKP_WAYBILLEXP
//						
//						entity.setConsignee(waybill.getDeliveryCustomerContact());
//						// 手机号
//						entity.setMobile(waybill.getDeliveryCustomerMobilephone());
//						// 发送目标
//						entity.setSendTarget(NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNER);
//						PendingSendSMSEntity entity_=new PendingSendSMSEntity();
//						smsList.add(entity);
//						BeanUtils.copyProperties(entity, entity_);
//						if(null!=oldWaybillNoEntity){
//						entity_.setConsignee(oldWaybillNoEntity.getReceiveCustomerContact());
//						// 手机号
//						entity_.setMobile(oldWaybillNoEntity.getReceiveCustomerMobilephone());
//						// 发送目标
//						entity_.setSendTarget(NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNEE);
//						}
//						smsList.add(entity_);
//					}else{
//						// 模块名称
//						entity.setModuleName("PKP_WAYBILL_EXP"); // PKP_WAYBILLEXP
//						// 接收人姓名
//						entity.setConsignee(waybill.getReceiveCustomerContact());
//						// 手机号
//						entity.setMobile(waybill.getReceiveCustomerMobilephone());
//						// 发送目标
//						entity.setSendTarget(NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNEE);
//						// 添加待处理(给收货客户发送短信)
//						smsList.add(entity);
//					}
//					for(PendingSendSMSEntity entity_temp:smsList){
//					pendingSendSMSService.addPendingSendmail(entity_temp);
//					}
//				}						}
//			}
			/**			 * ###############更改 根据crm端传递过来的参数判断 是否需要快递开单收货人短信
			 * end###############
			 **/
			// zxy 20140221 MANA-581 end 新增:快递短信
			// 零担短信
			if(!isExpress(waybillDto)) {
				// 判断是否是上门接货
				/**
				 * @项目：新客户体验项目
				 * @功能：增加是否新客户判断并定义客户类型
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-06-18下午16:11
				 */
				String paidMethod = waybill.getPaidMethod();
				/**
				 * @modify 2016-03-31 从数据库中查询承运信息获取新客户分区信息（当图片开单外场补录时候，分群信息可能没有被带进来）
				 * @author 297064
				 */
				ActualFreightEntity tempAC = actualFreightService.queryByWaybillNo(waybill.getWaybillNo());
				String flabelleavemonth = "";
				//数据库中找到了承运信息适用数据库的数据，未找到走之前的逻辑
				if(tempAC!=null){
					flabelleavemonth = tempAC.getFlabelleavemonth();
				}else{
					flabelleavemonth = af.getFlabelleavemonth();
				}
				//新客户标识
				boolean flag=judgeNew(flabelleavemonth,waybill.getReceiveOrgCode(),paidMethod);
				//boolean flag=judgeNew(af.getFlabelleavemonth(),waybill.getReceiveOrgCode());
				if (FossConstants.YES.equals(waybill.getPickupToDoor())) {
					// 接收人姓名
					entity.setConsignee(waybill.getDeliveryCustomerContact());
					// 手机号
					entity.setMobile(waybill.getDeliveryCustomerMobilephone());
					// 发送目标
					entity.setSendTarget(NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNER);
					// 添加待处理(给发货客户发送短信)
					/**
					 * @项目：新客户体验项目
					 * @功能：增加费用种类
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-06-18下午16:11
					 */
					if (flag) {
						entity.setTransportFee(waybill.getTransportFee());
						entity.setInsuranceFee(waybill.getInsuranceFee());
						entity.setCodAmount(waybill.getCodAmount());
						entity.setPickupFee(waybill.getPickupFee());
						entity.setDeliveryGoodsFee(waybill
								.getDeliveryGoodsFee());
						entity.setPackageFee(waybill.getPackageFee());
						// entity.setServiceFee(waybill.getServiceFee());
						entity.setOtherFee(waybill.getOtherFee());
						entity.setNewOrOld("A");
						entity.setCodFee(waybill.getCodFee());
						entity.setReceiveOrgCode(waybill.getReceiveOrgCode());
					}
					pendingSendSMSService.addPendingSendmail(entity);
					/**
					 * @项目：新客户体验项目
					 * @bug编号：CEM-28
					 * @功能：清除多余的发货人entity信息
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-06-18下午16:11
					 */
					if (flag) {
						entity.setTransportFee(null);
						entity.setInsuranceFee(null);
						entity.setCodAmount(null);
						entity.setPickupFee(null);
						entity.setDeliveryGoodsFee(null);
						entity.setPackageFee(null);
						// entity.setServiceFee(waybill.getServiceFee());
						entity.setOtherFee(null);
						entity.setNewOrOld(null);
						entity.setCodFee(null);
						entity.setReceiveOrgCode(null);
					}
				}
				// 接收人姓名
				entity.setConsignee(waybill.getReceiveCustomerContact());
				// 手机号
				entity.setMobile(waybill.getReceiveCustomerMobilephone());
				// 发送目标
				/**
				 * @需求：短信模板
				 * @功能：添加支付方式和提货方式
				 * @author:254615-foss-mabingliang
				 * @date:2015-06-25下午16:36
				 */
				if (CommonUtils.verdictPickUpSelf(waybill.getReceiveMethod())) {
					// 自提到付
					if (WaybillConstants.ARRIVE_PAYMENT.equals(waybill
							.getPaidMethod())) {
						entity.setSendTarget(NotifyCustomerConstants.SMS_CODE_CONSIGNEE_SELF_PICKUP_FC);
					} else {
						entity.setSendTarget(NotifyCustomerConstants.SMS_CODE_CONSIGNEE_NO_SELF_PICKUP);
					}
				} else if (CommonUtils.verdictPickUpDoor(waybill
						.getReceiveMethod())) {
					if (WaybillConstants.ARRIVE_PAYMENT.equals(waybill
							.getPaidMethod())) {
						entity.setSendTarget(NotifyCustomerConstants.SMS_CODE_CONSIGNEE_PICKUP_SENDGOODS_FC);
					} else {
						entity.setSendTarget(NotifyCustomerConstants.SMS_CODE_CONSIGNEE_PICKUP_SENDGOODS_NO_FC);
					}
				} else {
					entity.setSendTarget(NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNEE);
				}
				// 添加待处理(给收货客户发送短信)
				pendingSendSMSService.addPendingSendmail(entity);
			}
		} catch (Exception e) {
			// 添加运单提交的异常日志
			LOG.error("运单提交时，调用【保存待处理发送短信】方法异常！", e);
			System.out.println("e:"+e.getMessage());
		/*	// 打印异常信息，并不做抛出操作，（手机短信待处理保存不成功不能影响正常业务流程）
			throw new WaybillImportException("运单提交时，调用【保存待处理发送短信】方法异常！",
					e.getMessage());*/		}
	}
	
	/**
	 * 是否为快的
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-21
	 * @param waybillDto
	 * @return
	 */
	private boolean isExpress(WaybillDto waybillDto){
		boolean isExpress = false;
		if (FossConstants.YES.equals(waybillDto.getIsExpress())) {
			isExpress = true;
		} else {
			if(!StringUtils.isEmpty(waybillDto.getIsExpress()) && FossConstants.NO.equals(waybillDto.getIsExpress())){
				isExpress = false;
			}else{
				WaybillEntity entity = waybillDto.getWaybillEntity();
				if (waybillExpressService.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())) {
					isExpress = true;
				}else{
					isExpress = false;
				}
			}
		}
		return isExpress;
	}
	

	/**
	 * @项目：新客户体验项目
	 * @功能：验证是否可发新模版短信
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-07-22上午09:57
	 */
	private boolean judgeNew(String flabelleavemonth, String receiveOrgCode,String paidMethod) {
		try {
			/*
			 * 零担新客户发短信,增加筛选条件,取消试点大区发送短信限制
			 */
//			LateCouponEntity lateCouponEntity=lateCouponService.returnSchemeToPKP();
			//hujinyang  278328  20151125与zhaoyanjun确认处理
//			if(lateCouponEntity != null && StringUtils.isEmpty(flabelleavemonth) || ("NEWCUST".equals(flabelleavemonth))){
//			if((StringUtils.isEmpty(flabelleavemonth)||"NEWCUST".equals(flabelleavemonth))
//					&&lateCouponEntity!=null){
			//设置默认值
			if(StringUtils.isBlank(paidMethod)){
				paidMethod = "";
			}
			if ((StringUtils.isEmpty(flabelleavemonth) || "NEWCUST"
					.equals(flabelleavemonth))
					&& (WaybillConstants.TEMPORARY_DEBT.contains(paidMethod) || WaybillConstants.ARRIVE_PAYMENT
							.contains(paidMethod))) {
				
//				//出发大区
//				List<String> startSalesDept = lateCouponEntity.getStartSalesDept();
//				//到达大区 
//				List<String>  arriveSalesDept = lateCouponEntity.getArriveSalesDept();
//				if(startSalesDept == null){
//					startSalesDept = new ArrayList<String>();
//				}
//				if(arriveSalesDept == null){
//					arriveSalesDept = new ArrayList<String>();
//				}
//				arriveSalesDept.addAll(startSalesDept);
//				//判断是否是 试点营业部
//				boolean  pilotBigArea = false;
//				//都为null取消 试点优惠券发送给 发送人
//				if(arriveSalesDept.isEmpty()){
//					pilotBigArea = true;
//				}
//				/**
//				 * 短息发送对象 start=出发 arrive=到达 all=全部
//				 */
//				String smsSent = lateCouponEntity.getSmsSent();
//				//不在试点大区
//				if(!arriveSalesDept.contains(receiveOrgCode) && 
//						!pilotBigArea &&
//						("all".equals(smsSent) || StringUtils.isEmpty(smsSent)) ){
//					LOG.info("新客户短信:"+receiveOrgCode+"不在试点大区");
//					return false;
//				}
//				if(!pilotBigArea){
//					for(String allow:arriveSalesDept){					if(allow.equalsIgnoreCase(receiveOrgCode)){
//							return true;
//						}
//					}
//				}else{
//					return true;
//				}
				return true;
			}
		} catch (Exception e) {
			LOG.error("WaybillManagerService--->judgeNew 新客户体验项目出错，原因：", e.getMessage());
		}
		return false;
	}

	/**
	 * 降价发券用户需求：保存待处理返券信息，执行异步定时任务
	 * 
	 * @author Foss-206860
	 * 
	 * */
	private void makePendingSendCoupon(WaybillDto waybillDto) {
		// 为了计算价格，获取的实体类--封装查询产品价格计算的DTO----QueryBillCacilateDto---开单
		QueryBillCacilateDto queryBillCacilateDto = getQueryBillCacilateDto(waybillDto);
		// 根据信息的运单信息做降价返券处理
		if (queryBillCacilateDto != null) {// Online-定价体系优化项目DJTWO-63
			savePendingSendCoupon(queryBillCacilateDto, waybillDto, null, null,
					FossConstants.YES, FossConstants.NO);
		}
	}

	/**
	 * 降价发券用户需求：保存待处理返券信息，执行异步定时任务
	 * 
	 * @author Foss-206860
	 * 
	 * */
	@Override
	public void savePendingSendCoupon(
			QueryBillCacilateDto queryBillCacilateDto, WaybillDto waybillDto,
			WaybillDto oldWaybillDto, CurrentInfo currentInfo, String isBill,
			String isChangeWaybillNo) {
		try {
			// 运单信息
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
			// 获取运费的折后金额---公布价运费
			BigDecimal chargeFrtAmount = BigDecimal.ZERO;
			chargeFrtAmount = waybillEntity.getTransportFee();

			// 运单折扣明细
			List<WaybillDisDtlEntity> waybillDisDtlEntity = waybillDto
					.getWaybillDisDtlEntity();
			// 获取运费的折扣金额
			BigDecimal disDtlFrtAmount = BigDecimal.ZERO;
			for (int i = 0; i < waybillDisDtlEntity.size(); i++) {
				// 当费用code与运费code相等时，获取运费金额（折扣金额）
				if (PriceEntityConstants.PRICING_CODE_FRT
						.equals(waybillDisDtlEntity.get(i)
								.getPricingEntryCode())) {
					disDtlFrtAmount = disDtlFrtAmount.add(waybillDisDtlEntity
							.get(i).getAmount());
				}
			}
			// 运单折前金额
			BigDecimal beforeDisDtlFrtAmount = chargeFrtAmount
					.add(disDtlFrtAmount);
			// 运费最低一票
			BigDecimal minTransportFee = waybillEntity.getMinTransportFee();
			if (minTransportFee == null) {
				minTransportFee = BigDecimal.ZERO;
			}

			// 通过重量计费或者是体积计费获取返券系数-----待写
			// 为了获取基础资料(返券系数)封装参数(出发区域Id、到达区域Id、产品类型、重量、体积、是否接货、计费方式、开单时间)---调用综合的接口---获取基础资料
			PriceCouponDto couponFactorDto = getQueryCouponFactorDto(
					waybillDto, queryBillCacilateDto);
			if (couponFactorDto == null) {
				// 当基础资料为空时，不处理...
			} else {
				BigDecimal couponFactor = couponFactorDto.getCouponRate();
				// 最终优惠返券金额
				BigDecimal couponAmount = BigDecimal.ZERO;
				// 判断发货客户编码是否存在
				if (StringUtil.isNotEmpty(waybillEntity
						.getDeliveryCustomerCode())) {
					// 从运单信息中获取到发货人编码
					String custCode = waybillEntity.getDeliveryCustomerCode();
					// 判断是否是月结客户（月结客户即为合同客户，判断条件为是否有合同）
					CusBargainEntity cusBargain = cusBargainService
							.queryCusBargainByCustCode(custCode);
					// 该单发货客户为月结客户
					if (cusBargain != null) {
						// 月结客户判断是否返券：折前金额A 当前价格版本计算的折前运费金额*（1-返券系数）B ---若A>B
						// 则返券，返券业务规则见非月结客户；反之不返券
						// -----beforeDisDtlFrtAmount ----> A 折前金额
						// 当前价格版本计算的折前运费金额
						BigDecimal currBeforeDisDtlFrtAmount = BigDecimal.ZERO;
						List<ProductPriceDto> searchProductPriceList = billCaculateService
								.searchProductPriceList(queryBillCacilateDto);
						for (int i = 0; i < searchProductPriceList.size(); i++) {
							if (PriceEntityConstants.PRICING_CODE_FRT
									.equals(searchProductPriceList.get(i)
											.getPriceEntityCode())) {
								currBeforeDisDtlFrtAmount = searchProductPriceList
										.get(i).getCaculateFee();
								if(waybillEntity.isAccurateCost()){
									// 运费四舍五入
									currBeforeDisDtlFrtAmount = currBeforeDisDtlFrtAmount
											.setScale(2, BigDecimal.ROUND_HALF_UP);
								}else{
									currBeforeDisDtlFrtAmount = currBeforeDisDtlFrtAmount
											.setScale(0, BigDecimal.ROUND_HALF_UP);
								}
								
								break;
							}
						}
						// 当前价格版本计算的折前运费金额*（1-返券系数）B
						BigDecimal beforeDisDtlFrtAmountB = currBeforeDisDtlFrtAmount
								.multiply(BigDecimal.ONE.min(couponFactor));// ----------B
						if (beforeDisDtlFrtAmount
								.compareTo(beforeDisDtlFrtAmountB) > 0) {
							// 通过业务规则计算优惠券金额
							couponAmount = getCouponAmount(
									beforeDisDtlFrtAmount, chargeFrtAmount,
									minTransportFee, couponFactor);
						}
						// 该单发货客户为非月结客户
					} else {
						// 通过业务规则计算优惠券金额
						couponAmount = getCouponAmount(beforeDisDtlFrtAmount,
								chargeFrtAmount, minTransportFee, couponFactor);
					}
				}
				// 将优惠券金额四舍五入
				couponAmount = couponAmount.setScale(0,
						BigDecimal.ROUND_HALF_UP);

				// 返券信息的定义
				PendingSendCouponEntity pendingSendCouponEntity = null;
				// 返券日志的信息
				PendingSendCouponLogEntity pendingSendCouponLogEntity = null;
				// 如果是更改单，则查询是否有相同的运单信息已经存在于返券信息表中;若是开单，则不处理...
				if (FossConstants.NO.equals(isBill)) {
					String waybillNo = waybillEntity.getWaybillNo();
					if (FossConstants.YES.equals(isChangeWaybillNo)) {
						// 如果是否改变运单号字段的值为Y 则参数运单号为旧运单号
						waybillNo = oldWaybillDto.getWaybillEntity()
								.getWaybillNo();
					}
					// 根据运单号和开单时间查询返券信息
					pendingSendCouponEntity = pendingSendCouponService
							.queryPendingSendCoupon(waybillNo,
									waybillEntity.getBillTime());

					pendingSendCouponLogEntity = pendingSendCouponLogService
							.queryPendingSendLogCoupon(
									waybillEntity.getWaybillNo(),
									waybillEntity.getBillTime());
				}

				// 添加待处理（给符合条件的运单发货客户返券：将数据保持至数据库，通过job定时返券）
				// 标志是否已返券的判断字段
				String isSendCoupon =  "";
				// 不管优惠券金额大小，不管是开单还是更改单，只要根据单号和开单时间查询到返券日志的，一律不处理...
				if (pendingSendCouponLogEntity == null) {
					// 当优惠券金额大于0时，返券处理-----------------
					if (couponAmount.compareTo(BigDecimal.ZERO) > 0) {
						// 当前是开单处理降价返券
						if (FossConstants.YES.equals(isBill)) {
							// 封装数据，添加待处理 参数：运单信息 基础资料 优惠券金额 ---- 基础资料待写
							// ---将基础资料、优惠券金额、运单信息写入数据库
							createPendingSendCoupon(waybillEntity,
									couponAmount, couponFactorDto);
						} else {
							// 更改单处理降价返券
							// 根据运单号和开单时间查询是否存在返券信息--- 若存在 则更新运单返券信息 若不存在
							// 则新增返券信息

							if (pendingSendCouponEntity != null) {
								// 根据Id更新运单返券信息----待写
								isSendCoupon = FossConstants.NO;
								// 如果对应返券信息中的SEND_COUPON为N,则更新返券信息；反之，不处理~
								if (FossConstants.NO
										.equals(pendingSendCouponEntity
												.getSendCoupon())) {
									updatePendingSendCoupon(
											pendingSendCouponEntity,
											waybillEntity, couponAmount,
											isSendCoupon, couponFactorDto);
								}
							} else {// --------待核实
									// 新增返券信息
									// 封装数据，添加待处理 参数：运单信息 基础资料 优惠券金额 ---- 基础资料待写
									// ---将基础资料、优惠券金额、运单信息写入数据库
								createPendingSendCoupon(waybillEntity,
										couponAmount, couponFactorDto);
							}
						}
						// 当优惠券金额小于0时，不返券处理-----------------
					} else {
						// 若是开单 优惠券金额<0 则不处理 若是更改单 则处理
						// 当前是更改单 根据运单号和开单时间查询是否存在返券信息 ------ 若存在 则更新返券信息
						// 将是否返券修改至不返券 若不存在则不处理
						if (FossConstants.NO.equals(isBill)) {
							// 开单时，存在返券信息
							if (pendingSendCouponEntity != null) {
								// 根据Id更新运单返券信息-----待写----是否返券修改至不返券(已返券)
								isSendCoupon = FossConstants.YES;
								// 如果对应返券信息中的SEND_COUPON为N,则更新返券信息---将是否返券修改为Y；反之，不处理~
								if (FossConstants.NO
										.equals(pendingSendCouponEntity
												.getSendCoupon())) {
									updatePendingSendCoupon(
											pendingSendCouponEntity,
											waybillEntity, couponAmount,
											isSendCoupon, couponFactorDto);
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			// 添加运单提交的异常日志
			LOG.error("运单提交时，调用【保存待处理返券】方法异常！", e);
			// 打印异常信息，并不做抛出操作，（返券待处理保存不成功不能影响正常业务流程）
			throw new WaybillImportException("运单提交时，调用【保存待处理返券】方法异常！",
					e.getMessage());
		}

	}

	/**
	 * 封装查询产品价格计算的DTO
	 * 
	 * @param waybillDto
	 *            运单实体
	 * 
	 * @author Foss-206860
	 * **/
	private QueryBillCacilateDto getQueryBillCacilateDto(WaybillDto waybillDto) {
		// 降價返券需求：為了獲取當前版本的價格計算折前運費而保存的产品价格主参数信息
		GuiQueryBillCalculateDto productPriceDtoCollection = waybillDto
				.getProductPriceDtoCollection();
		QueryBillCacilateDto queryBillCacilateDto = new QueryBillCacilateDto();
		if (productPriceDtoCollection != null) {// Online-定价体系优化项目DJTWO-63
			queryBillCacilateDto.setOriginalOrgCode(productPriceDtoCollection
					.getOriginalOrgCode());
			queryBillCacilateDto
					.setDestinationOrgCode(productPriceDtoCollection
							.getDestinationOrgCode());
			queryBillCacilateDto.setProductCode(productPriceDtoCollection
					.getProductCode());
			queryBillCacilateDto.setCurrencyCdoe(productPriceDtoCollection
					.getCurrencyCdoe());
			queryBillCacilateDto.setDeptRegionId(productPriceDtoCollection
					.getDeptRegionId());
			queryBillCacilateDto.setArrvRegionId(productPriceDtoCollection
					.getArrvRegionId());
			queryBillCacilateDto.setFlightShift(productPriceDtoCollection
					.getFlightShift());
			queryBillCacilateDto.setGoodsCode(productPriceDtoCollection
					.getGoodsCode());
			queryBillCacilateDto.setReceiveDate(productPriceDtoCollection
					.getReceiveDate());
			queryBillCacilateDto.setIsReceiveGoods(productPriceDtoCollection
					.getIsReceiveGoods());
			queryBillCacilateDto.setChannelCode(productPriceDtoCollection
					.getChannelCode());
			queryBillCacilateDto.setWeight(productPriceDtoCollection
					.getWeight());
			queryBillCacilateDto.setVolume(productPriceDtoCollection
					.getVolume());
			queryBillCacilateDto.setDeptRegionId(productPriceDtoCollection
					.getDeptRegionId());
			queryBillCacilateDto.setArrvRegionId(productPriceDtoCollection
					.getArrvRegionId());
			queryBillCacilateDto.setLongOrShort(productPriceDtoCollection
					.getLongOrShort());
			queryBillCacilateDto.setCustomerCode(productPriceDtoCollection
					.getCustomerCode());
			queryBillCacilateDto.setEconomySince(productPriceDtoCollection
					.getEconomySince());
			queryBillCacilateDto.setLastOrgCode(productPriceDtoCollection
					.getLastOrgCode());
			// zxy 20140522 DEFECT-2949 MANA-1253 start 新增
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
					.equals(productPriceDtoCollection.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_C2_C20004
							.equals(productPriceDtoCollection.getProductCode()))
				queryBillCacilateDto
						.setCombBillTypeCode(productPriceDtoCollection
								.getCombBillTypeCode()); // zxy 20140507
															// MANA-1253 新增
			// zxy 20140522 DEFECT-2949 MANA-1253 end 新增
			// 市场营销活动
			queryBillCacilateDto.setActiveDto(productPriceDtoCollection
					.getActiveDto());
			queryBillCacilateDto.setCalActiveDiscount(productPriceDtoCollection
					.isCalActiveDiscount());
			queryBillCacilateDto.setActiveCode(productPriceDtoCollection
					.getActiveCode());
			queryBillCacilateDto.setGoodsName(productPriceDtoCollection
					.getGoodsName());
			queryBillCacilateDto
					.setDeliveryCustomerCode(productPriceDtoCollection
							.getDeliveryCustomerCode());
			queryBillCacilateDto.setOrderChannel(productPriceDtoCollection
					.getOrderChannel());
			queryBillCacilateDto.setReceiveOrgCode(productPriceDtoCollection
					.getReceiveOrgCode());
			queryBillCacilateDto.setLoadOrgCode(productPriceDtoCollection
					.getLoadOrgCode());
			queryBillCacilateDto
					.setLastOutLoadOrgCode(productPriceDtoCollection
							.getLastOutLoadOrgCode());
			queryBillCacilateDto
					.setCustomerPickupOrgCode(productPriceDtoCollection
							.getCustomerPickupOrgCode());
			queryBillCacilateDto.setBillTime(productPriceDtoCollection
					.getBillTime());
			queryBillCacilateDto.setTransportFee(productPriceDtoCollection
					.getTransportFee());
			queryBillCacilateDto.setGoodsWeightTotal(productPriceDtoCollection
					.getGoodsWeightTotal());
			queryBillCacilateDto.setGoodsVolumeTotal(productPriceDtoCollection
					.getGoodsVolumeTotal());
			// 降价返券需求：为了再次计算获取当前价格版本的折前运费
			productPriceDtoCollection.setIsCurrContract(FossConstants.YES);
		}
		return queryBillCacilateDto;
	}

	/**
	 * 根据ID更新返券数据表中的返券信息--- 除了ID全部更新
	 * 
	 * @param pendingSendCouponEntity
	 *            待返券的初始信息
	 * @param waybillEntity
	 *            运单实体
	 * @param couponAmount
	 *            优惠券金额
	 * @param isSendCoupon
	 *            是否已返券
	 * 
	 * @author Foss-206860
	 * **/
	private void updatePendingSendCoupon(
			PendingSendCouponEntity pendingSendCouponEntity,
			WaybillEntity waybillEntity, BigDecimal couponAmount,
			String isSendCoupon, PriceCouponDto couponFactorDto) {
		// 创建待处理返券实体类
		PendingSendCouponEntity pendingSendCoupon = new PendingSendCouponEntity();

		// 实体ID
		pendingSendCoupon.setId(pendingSendCouponEntity.getId());

		// 运单号
		pendingSendCoupon.setWaybillNo(waybillEntity.getWaybillNo());
		// 发货人手机号码
		pendingSendCoupon.setDeliveryCustomerMobilephone(waybillEntity
				.getDeliveryCustomerMobilephone());
		// 开单时间
		pendingSendCoupon.setBillTime(waybillEntity.getBillTime());

		// 返券时间---待待定
		pendingSendCoupon.setSendCouponTime(couponFactorDto
				.getCouponTimeToSend());

		// 优惠券有效期
		BigDecimal affecticTime = new BigDecimal(
				couponFactorDto.getAvailablePeriod());

		// 优惠券起始时间=开单时间+返券时间
		java.util.Calendar calstart = java.util.Calendar.getInstance();
	    calstart.setTime(waybillEntity.getBillTime());
		// pendingSendCoupon.getSendCouponTime().intValue()为返券时间（计时单位：小时）
		calstart.add(java.util.Calendar.HOUR, pendingSendCoupon
				.getSendCouponTime().intValue());
		pendingSendCoupon.setCouponBeginTime(calstart.getTime());

		// 优惠券终止时间=起始时间+有效天数
		java.util.Calendar calstartEnd = java.util.Calendar.getInstance();
		calstartEnd.setTime(pendingSendCoupon.getCouponBeginTime());
		// affecticTime.intValue()为优惠券有效时间（计时单位：天）
		calstartEnd.add(java.util.Calendar.DATE, affecticTime.intValue());
		pendingSendCoupon.setCouponEndTime(calstartEnd.getTime());

		// 产品类型
		pendingSendCoupon.setProductCode(couponFactorDto.getProductItem());
		// 订单来源
		pendingSendCoupon.setOrderChannel(couponFactorDto.getOrderSourceCode());
		// 客户等级
		pendingSendCoupon.setCustomerDegree(couponFactorDto
				.getCustomerDegreeCode());
		// 客户行业
		pendingSendCoupon.setCustomerIndustry(couponFactorDto
				.getCustomerProfessionCode());

		// 创建时间
		pendingSendCoupon
				.setCreateTime(pendingSendCouponEntity.getCreateTime());
		// 修改时间
		pendingSendCoupon.setModifyTime(new Date());
		// 失败原因
		pendingSendCoupon.setFaileReason("");
		// 操作类型（成功：SUCCESS 失败：failure）
		pendingSendCoupon.setOperateType("");
		// 优惠券金额
		pendingSendCoupon.setPromotionsFee(couponAmount);

		// 判断是否已返券的标志
		pendingSendCoupon.setSendCoupon(isSendCoupon);

		// 区域线路要求
		pendingSendCoupon.setLineArea(couponFactorDto.getLineRegion());
		// 若区域线路要求选择了区域，则优惠券的使用只能在出发区域，若选择了线路，则只能在固定线路上使用，若无选择，则在全国使用
		if (DictionaryValueConstants.IS_DEPARTURE_CITY.equals(pendingSendCoupon
				.getLineArea())) {
			// 出发营业部所属行政区域
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(waybillEntity
							.getReceiveOrgCode());
			// 出发行政区域编码
			pendingSendCoupon.setReceiveCustomerCityCode(orgInfo.getCityCode());
			// 出发行政区域名称
			pendingSendCoupon.setReceiveCustomerCityName(orgInfo.getCityName());
		} else if (DictionaryValueConstants.IS_LINE.equals(pendingSendCoupon
				.getLineArea())) {
			// 出发营业部所属行政区域
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(waybillEntity
							.getReceiveOrgCode());
			// 出发行政区域编码
			pendingSendCoupon.setReceiveCustomerCityCode(orgInfo.getCityCode());
			// 出发行政区域名称
			pendingSendCoupon.setReceiveCustomerCityName(orgInfo.getCityName());
			// 到达营业部所属行政区域
			OrgAdministrativeInfoEntity deliveryOrgInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(waybillEntity
							.getCustomerPickupOrgCode());
			// 出发行政区域编码
			pendingSendCoupon.setDeliveryCustomerCityCode(deliveryOrgInfo
					.getCityCode());
			// 出发行政区域名称
			pendingSendCoupon.setDeliveryCustomerCityName(deliveryOrgInfo
					.getCityName());
		}

		// 开单金额
		pendingSendCoupon.setBillAmount(waybillEntity.getTotalFee().subtract(
				waybillEntity.getCodAmount()));
		// 短信内容---综合提供
		pendingSendCoupon
				.setSendCouponContent(couponFactorDto.getProgramDesc());

		pendingSendCouponService
				.updateSendCouponByID(pendingSendCoupon);

	}

	/**
	 * 添加待处理（给符合条件的运单发货客户返券：将数据保持至数据库，通过job定时返券）
	 * 
	 * @param waybillEntity
	 *            运单实体
	 * @param couponAmount
	 *            返券金额
	 * 
	 * @author Foss-206860
	 * */
	private void createPendingSendCoupon(WaybillEntity waybillEntity,
			BigDecimal couponAmount, PriceCouponDto couponFactorDto) {
		// 创建待处理返券实体类
		PendingSendCouponEntity pendingSendCoupon = new PendingSendCouponEntity();

		// 运单号
		pendingSendCoupon.setWaybillNo(waybillEntity.getWaybillNo());
		// 发货人手机号码
		pendingSendCoupon.setDeliveryCustomerMobilephone(waybillEntity
				.getDeliveryCustomerMobilephone());
		// 开单时间
		pendingSendCoupon.setBillTime(waybillEntity.getBillTime());

		// 返券时间
		pendingSendCoupon.setSendCouponTime(couponFactorDto
				.getCouponTimeToSend());

		// 优惠券有效期
		BigDecimal affecticTime = new BigDecimal(
				couponFactorDto.getAvailablePeriod());
		

		// 优惠券起始时间=开单时间+返券时间
		java.util.Calendar calstart = java.util.Calendar.getInstance();
	    calstart.setTime(waybillEntity.getBillTime());
		// pendingSendCoupon.getSendCouponTime().intValue()为返券时间（计时单位：小时）
		calstart.add(java.util.Calendar.HOUR, pendingSendCoupon
				.getSendCouponTime().intValue());
		pendingSendCoupon.setCouponBeginTime(calstart.getTime());

		// 优惠券终止时间=起始时间+有效天数
		java.util.Calendar calstartEnd = java.util.Calendar.getInstance();
		calstartEnd.setTime(pendingSendCoupon.getCouponBeginTime());
		// pendingSendCoupon.getSendCouponTime().intValue()为返券时间（计时单位：小时）
		calstartEnd.add(java.util.Calendar.DATE, affecticTime.intValue());
		pendingSendCoupon.setCouponEndTime(calstartEnd.getTime());

		// 产品类型
		pendingSendCoupon.setProductCode(couponFactorDto.getProductItem());
		// 订单来源
		pendingSendCoupon.setOrderChannel(couponFactorDto.getOrderSource());
		// 客户等级
		pendingSendCoupon
				.setCustomerDegree(couponFactorDto.getCustomerDegree());
		// 客户行业
		pendingSendCoupon.setCustomerIndustry(couponFactorDto
				.getCustomerProfession());

		// 创建时间
		pendingSendCoupon.setCreateTime(new Date());
		// 修改时间
		pendingSendCoupon.setModifyTime(new Date());
		// 失败原因
		pendingSendCoupon.setFaileReason("");
		// 操作类型（成功：SUCCESS 失败：failure）
		pendingSendCoupon.setOperateType("");
		// 优惠券金额
		pendingSendCoupon.setPromotionsFee(couponAmount);
		// 是否已返券
		pendingSendCoupon.setSendCoupon(FossConstants.NO);
		// 区域线路要求
		pendingSendCoupon.setLineArea(couponFactorDto.getLineRegion());
		// 若区域线路要求选择了区域，则优惠券的使用只能在营业部所属的出发行政区域---市，若选择了线路，则只能在固定线路上使用，若无选择，则在全国使用
		if (DictionaryValueConstants.IS_DEPARTURE_CITY.equals(pendingSendCoupon
				.getLineArea())) {
			// 出发营业部所属行政区域
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(waybillEntity
							.getReceiveOrgCode());
			// 出发行政区域编码
			pendingSendCoupon.setReceiveCustomerCityCode(orgInfo.getCityCode());
			// 出发行政区域名称
			pendingSendCoupon.setReceiveCustomerCityName(orgInfo.getCityName());
		} else if (DictionaryValueConstants.IS_LINE.equals(pendingSendCoupon
				.getLineArea())) {
			// 出发营业部所属行政区域
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(waybillEntity
							.getReceiveOrgCode());
			
			// 出发行政区域编码
			pendingSendCoupon.setReceiveCustomerCityCode(orgInfo.getCityCode());
			// 出发行政区域名称
			pendingSendCoupon.setReceiveCustomerCityName(orgInfo.getCityName());
			// 到达营业部所属行政区域
			OrgAdministrativeInfoEntity deliveryOrgInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(waybillEntity
							.getCustomerPickupOrgCode());
			// 出发行政区域编码
			pendingSendCoupon.setDeliveryCustomerCityCode(deliveryOrgInfo
					.getCityCode());
			// 出发行政区域名称
			pendingSendCoupon.setDeliveryCustomerCityName(deliveryOrgInfo
					.getCityName());
		}

		// 开单金额
		pendingSendCoupon.setBillAmount(waybillEntity.getTotalFee().subtract(
				waybillEntity.getCodAmount()));

		// 短信内容---综合提供
		pendingSendCoupon
				.setSendCouponContent(couponFactorDto.getProgramDesc());
		pendingSendCouponService.addPendingSendCoupon(pendingSendCoupon);

	}

	/**
	 * 为了获取基础资料(返券系数)封装参数(出发、到达、产品类型、重量、体积、是否接货、计费类型、开单时间)
	 * 
	 * @param waybillDto
	 *            运单实体
	 * @param queryBillCacilateDto
	 *            为了计算封装的dto
	 * 
	 * @author Foss-206860
	 * */
	private PriceCouponDto getQueryCouponFactorDto(WaybillDto waybillDto,
			QueryBillCacilateDto queryBillCacilateDto) {
		// 运单基础信息
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		// 降價返券需求：為了獲取當前版本的價格計算折前運費而保存的产品价格主参数信息
		// GuiQueryBillCalculateDto productPriceDtoCollection =
		// waybillDto.getProductPriceDtoCollection();

		// 产品类型(运输性质--三级产品)
		String productCode = waybillEntity.getProductCode();

		// 查询出发区域ID
		String originalId = queryBillCacilateDto.getDeptRegionId();
		String originalOrgCode = queryBillCacilateDto.getOriginalOrgCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_C2_C20004.equals(productCode)) {
			originalId = regionAirService.findRegionOrgByDeptNo(originalOrgCode, queryBillCacilateDto.getReceiveDate(), null, PricingConstants.PRICING_REGION);


		} else {
			originalId = regionService.findRegionOrgByDeptNo(originalOrgCode, queryBillCacilateDto.getReceiveDate(), null, PricingConstants.PRICING_REGION);

		}
		// 查询目的地区域ID
		String destinationId = queryBillCacilateDto.getArrvRegionId();
		String destinationOrgCode = queryBillCacilateDto.getDestinationOrgCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_C2_C20004.equals(productCode)) {
			destinationId = regionAirService.findRegionOrgByDeptNo(destinationOrgCode, queryBillCacilateDto.getReceiveDate(), null, PricingConstants.PRICING_REGION);




		} else {
			destinationId = regionArriveService.findRegionOrgByDeptNo(destinationOrgCode, queryBillCacilateDto.getReceiveDate(), null, PricingConstants.ARRIVE_REGION);

		}
		
		// 判断是否月结
		String isMonthlyDate = FossConstants.NO;
		if (StringUtil.isNotEmpty(queryBillCacilateDto.getCustomerCode())) {
			// 根据客户编码获取当前价格版本的相关合同信息
			PreferentialInfoDto preferentialInfoDto = preferentialService
					.queryPriceVersionInfo(
							queryBillCacilateDto.getCustomerCode(),
							queryBillCacilateDto.getReceiveDate());
			if (null != preferentialInfoDto) {
				Date preferentiaTime = preferentialInfoDto
						.getPriceVersionDate();
				if (null != preferentiaTime) {
					isMonthlyDate = FossConstants.YES;
				}
			}
		}

		// 业务时间
//		Date discountReceiveDate = queryBillCacilateDto.getReceiveDate();
		//如果当前是客户版本日期没有定位到区域。则以业务时间为准找区域
		if (StringUtil.isEmpty(originalId) && FossConstants.YES.equals(isMonthlyDate)) {
			/**
			 * 删除重复的代码 与上面的重复调用
			 * @author Foss-278328-hujinyang
			 * @time 20160422
			 */
			/*this.setSubmitDtoForNative(waybillDto.getWaybillEntity().getWaybillNo(),"getQueryCouponFactorDto-"+cTime+"-originalId-findRegionOrgByDeptNoStart", "com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService.findRegionOrgByDeptNo");
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_C2_C20004.equals(productCode)) {
				originalId = regionAirService.findRegionOrgByDeptNo(originalOrgCode, discountReceiveDate, null, PricingConstants.PRICING_REGION);




			} else {
				originalId = regionService.findRegionOrgByDeptNo(originalOrgCode, discountReceiveDate, null, PricingConstants.PRICING_REGION);

			}
			this.setSubmitDtoForNative(waybillDto.getWaybillEntity().getWaybillNo(),"getQueryCouponFactorDto-"+cTime+"-originalId-findRegionOrgByDeptNoEnd", "com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService.findRegionOrgByDeptNo");*/
			if (StringUtil.isEmpty(originalId)) {
				originalId = null;
			}
		}
	
		//如果当前是客户版本日期没有定位到区域。则以业务时间为准找区域
		if (StringUtil.isEmpty(destinationId) &&  StringUtil.isNotBlank(destinationOrgCode) && FossConstants.YES.equals(isMonthlyDate)) {
			/**
			 * 删除重复的代码 与上面的重复调用
			 * @author Foss-278328-hujinyang
			 * @time 20160422
			 */
			/*this.setSubmitDtoForNative(waybillDto.getWaybillEntity().getWaybillNo(),"getQueryCouponFactorDto-"+cTime+"-destinationId-findRegionOrgByDeptNoStart", "com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService.findRegionOrgByDeptNo");
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_C2_C20004.equals(productCode)) {
				destinationId = regionAirService.findRegionOrgByDeptNo(destinationOrgCode,discountReceiveDate , null, PricingConstants.PRICING_REGION);

			} else {
				destinationId = regionArriveService.findRegionOrgByDeptNo(destinationOrgCode,discountReceiveDate , null, PricingConstants.ARRIVE_REGION);

			}
			this.setSubmitDtoForNative(waybillDto.getWaybillEntity().getWaybillNo(),"getQueryCouponFactorDto-"+cTime+"-destinationId-findRegionOrgByDeptNoEnd", "com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService.findRegionOrgByDeptNo");*/
			if (StringUtil.isEmpty(destinationId)) {
				destinationId = null;
			}
		}
		// 重量
		 BigDecimal goodsWeightTotal = waybillEntity.getGoodsWeightTotal();
		// 体积
		 BigDecimal goodsVolumeTotal = waybillEntity.getGoodsVolumeTotal();
		// 是否接货
		 String pickupToDoor = waybillEntity.getPickupToDoor();
		// 计费类型
		 String billingType = waybillEntity.getBillingType();
		if (WaybillConstants.BILLINGWAY_WEIGHT.equals(billingType)) {
			 billingType = WaybillConstants.BILLINGWAY_WEIGHT;
		} else if (WaybillConstants.BILLINGWAY_VOLUME.equals(billingType)) {
			 billingType = WaybillConstants.BILLINGWAY_VOLUME;
		 }
		// 开单时间
		 Date billTime = waybillEntity.getBillTime();

		// 调用综合的接口获取基础资料信息---待写
		PriceCouponDto priceCouponDto = priceCouponService
				.selectPriceCouponByConditionToQuery(originalId, destinationId,
						productCode, goodsWeightTotal, goodsVolumeTotal,
						pickupToDoor, billingType, billTime);
		if (priceCouponDto == null) {
			 return null;
		} else {
			 return priceCouponDto;
		 }
	}

	/**
	 * 通过业务规则来获取返券优惠金额
	 * 
	 * @param beforeDisDtlFrtAmount
	 *            折前金额
	 * @param chargeFrtAmount
	 *            折后金额
	 * @param minTransportFee
	 *            最低一票
	 * @param couponFactor
	 *            返券系数
	 * 
	 * @return couponAmount 优惠金额
	 * 
	 * @author Foss-206860
	 * **/
	private BigDecimal getCouponAmount(BigDecimal beforeDisDtlFrtAmount,
			BigDecimal chargeFrtAmount, BigDecimal minTransportFee,
			BigDecimal couponFactor) {
		BigDecimal couponAmount = BigDecimal.ZERO;
		// 优惠券金额 = 折前运费金额 * 返券系数
		couponAmount = beforeDisDtlFrtAmount.multiply(couponFactor);
		// 计算 折后运费金额 减去 优惠券金额 的金额值
		BigDecimal differAmount = chargeFrtAmount.subtract(couponAmount);
		// 比较 折后运费金额 - 优惠券金额 与 运费最低一票的大小
		if (differAmount.compareTo(minTransportFee) >= 0) {
			// 当折后运费金额减去优惠券金额大于等于运费最低一票时，返回优惠券金额为折前运费金额*返券系数
			return couponAmount;
		} else {
			// 当折后运费金额减去优惠券金额小于运费最低一票时，返回优惠券金额为折后金额减去最低一票
			couponAmount = chargeFrtAmount.subtract(minTransportFee);
			return couponAmount;
		}
	}

	/**
	 * 
	 * 更新约车状态
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-24 下午04:45:59
	 * @param waybillDto
	 * @see
	 */
	private void updateVehicleState(WaybillDto waybillDto) {
		// 获得运单基本信息明细对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		// 判断约车编号是否为空，为空则不更新；否则根据订单更新数据
		if (!"".equals(StringUtil.defaultIfNull(waybillEntity.getOrderVehicleNum()))) {
			// 运单开单完成之后，更新状态
			updateInviteVehicleForFinishBill(waybillEntity.getOrderVehicleNum());
		}
	}

	/**
	 * 提交成功后更新待处理运单表中相关数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-21 上午11:15:18
	 */
	public void updateEWaybillData(WaybillDto waybillDto, WaybillPendingEntity waybillPendingEntity, String waybillStatus) {
		//原运单号 此处之所以使用原运单号是因为：PDA导入时，可以更改了运单号 在提交运单时， 若使用更改后的运单号会导致出现运单号不存在的空指针
		// PDA补录
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			// PDA补录运单是否已生成交接单，若是则删除待处理运单表中的记录
			if (FossConstants.ACTIVE.equals(StringUtil.defaultIfNull(waybillPendingEntity.getHandoverStatus()))) {
				// 删除已补录运单记录
				try {
					// 根据原运单号进行删除
					waybillPendingService.deleteByWaybillNo(waybillPendingEntity.getWaybillNo());
				} catch (BusinessException e) {
					// 添加异常信息日志
					LOG.error("删除待处理表中已补录的PDA运单失败", e);
					// 抛出异常信息，中止程序继续运行
					throw new WaybillImportException(WaybillImportException.DELETEPDAWAYBILL_FAIL,
							new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
				}
			} else {
				// 未生成交接单,则将该已补录PDA运单置为N。在做交接单时会自动删除
				try {
					// 更新待补录运单状态
					waybillPendingEntity.setActive(FossConstants.NO);
					waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
					waybillPendingService.updateByPrimaryKeySelective(waybillPendingEntity);
				} catch (BusinessException e) {
					// 添加异常信息日志
					LOG.error("更新待处理表中已补录的PDA运单状态失败", e);
					// 抛出异常信息，中止程序继续运行
					throw new WaybillImportException(WaybillImportException.UPDATEPDAWAYBILLSTATUS_FAIL,
							new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
				}
			}
		} else {
			// 捕获删除的异常信息
			try {
				// 根据运单号删除已成功补录的待处理运单信息
				waybillPendingService.deleteByWaybillNo(waybillPendingEntity.getWaybillNo());
			} catch (BusinessException e) {
				// 添加异常日志
				LOG.error("Excepton", e);
				// 抛出“删除待处理表中已补录的暂存运单失败”的异常信息，并中止程序继续运行
				throw new WaybillImportException(WaybillImportException.DELETEPDAWAYBILL_FAIL,
						new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
			}
		}
	}

	/**
	 * 提交成功后更新待处理运单表中相关数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-21 上午11:15:18
	 */
	public void updatePendingData(WaybillDto waybillDto, String waybillStatus) {
		/**
		 * 原运单号 此处之所以使用原运单号是因为：PDA导入时，可以更改了运单号 在提交运单时， 若使用更改后的运单号会导致出现运单号不存在的空指针
		 */
		String waybillNo = StringUtil.defaultIfNull(waybillDto.getOldWaybillNo());
		/**
		 * 判断原运单号是否为空 若为空则不允许提交 PDA导入时，原运单必须不能为空
		 */
		if ("".equals(waybillNo)) {
			// 增加错误日志
			LOG.error("PDA补录提交时，原运单号不能为空!");
			// 抛出异常信息至前台，中止程序继续运行
			throw new WaybillImportException(WaybillImportException.PDAWAYBILLNO_NULL);
		}

		// 待补录运单信息
		WaybillPendingDto pendingDto = waybillPendingService.queryPendingWaybillByNo(waybillNo);
		// 待补录运单基本信息
		WaybillPendingEntity waybillPending = waybillPendingService.queryPendingByNo(waybillNo);
		// PDA补录
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			// 原始PDA运单数据
			WaybillPdaDto oldWaybillPda = pdaValueCopyPending(pendingDto);
			// 修改PDA运单数据
			WaybillPdaDto newWaybillPda = pdaValueCopyWaybill(waybillDto);

			// PDA补录运单是否已生成交接单，若是则删除待处理运单表中的记录
			if (FossConstants.ACTIVE.equals(StringUtil.defaultIfNull(waybillPending.getHandoverStatus()))) {
				// 删除已补录运单记录
				try {
					// 根据原运单号进行删除
					waybillPendingService.deleteByWaybillNo(waybillNo);
				} catch (BusinessException e) {
					// 添加异常信息日志
					LOG.error("删除待处理表中已补录的PDA运单失败", e);
					// 抛出异常信息，中止程序继续运行
					throw new WaybillImportException(WaybillImportException.DELETEPDAWAYBILL_FAIL,
							new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
				}
			} else {
				// 未生成交接单,则将该已补录PDA运单置为N。在做交接单时会自动删除
				try {
					// 更新待补录运单状态
					waybillPendingService.updatePendingActiveByNo(waybillNo);
				} catch (BusinessException e) {
					// 添加异常信息日志
					LOG.error("更新待处理表中已补录的PDA运单状态失败", e);
					// 抛出异常信息，中止程序继续运行
					throw new WaybillImportException(WaybillImportException.UPDATEPDAWAYBILLSTATUS_FAIL,
							new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
				}
			}

			// 记录PDA运单补录操作历史
			try {
				// 运单补录（PDA，订单导入）记录更改历史
				waybillAcHisPdaService.addWaybillAcHisPdaEntity(newWaybillPda, oldWaybillPda);
			} catch (BusinessException e) {
				// 添加异常日志
				LOG.error("Excepton", e);
				// 抛出"生成PDA运单补录操作历史记录的失败"的异常信息，并中止程序的运行
				throw new WaybillImportException(WaybillImportException.ADDWAYBILLACHISPDA_FAIL,
						new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
			}
			// 运单暂存补录
		} else {
			// 捕获删除的异常信息
			try {
				// 根据运单号删除已成功补录的待处理运单信息
				waybillPendingService.deleteByWaybillNo(waybillNo);
			} catch (BusinessException e) {
				// 添加异常日志
				LOG.error("Excepton", e);
				// 抛出“删除待处理表中已补录的暂存运单失败”的异常信息，并中止程序继续运行
				throw new WaybillImportException(WaybillImportException.DELETEPDAWAYBILL_FAIL,
						new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
			}
		}
	}

	/**
	 * 更新弃货处理运单记录信息（标识待弃货运单为N,更新弃货运单号储运事项为新运单号）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午4:09:39
	 */
	private void updateAbandonedGoodsData(WaybillEntity waybill) {
		// 储运事项
		String note = String.valueOf(waybill.getTransportationRemark());
		// 根据运单号更新弃货状态
		List<String> waybillNos = resolveWaybillNo(note);
		
		/**
		 * @author Foss-278328-hujinyang
		 * @TIME 20160422
		 */
//		this.updateAbandonedGoods(resolveWaybillNo(note), waybill.getWaybillNo());
		this.updateAbandonedGoods(waybillNos, waybill.getWaybillNo());
		for (String oldWaybillNo : waybillNos) {
			// 更新原弃货运单备注信息
			String notes = "该运单已弃货处理成功，内部带货至大区办公室，运单号为" + waybill.getWaybillNo();
			WaybillEntity waybillEntity = new WaybillEntity();
			waybillEntity.setTransportationRemark(notes);
			waybillEntity.setActive(waybill.getActive());
			waybillDao.updateWaybillByWaybillNo(waybillEntity, oldWaybillNo);
		}
	}

	/**
	 * 将弃货处理运单号从储运事项中解析出来
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午4:45:20
	 */
	public static List<String> resolveWaybillNo(String note) {
		// 若储运事项为空，则返回空的集合
		if (StringUtil.isEmpty(note)) {
			return null;
		}
		// 将中文状态的冒号转换为英文状态的冒号
		String temp = note.replace('：', ':');
		// 由于TRANSPORTATION_REMARK最后一位是；号，所以要去掉；号
		String tempString = temp.substring(temp.lastIndexOf(":") + 1, temp.length() - 1);
		// 根据逗号分隔字符串数组
		String[] str = null;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(tempString)) {
			str = tempString.split("[|,|]");
		}
		// 将字符数组转换为list集合
		if (str != null) {
			return Arrays.asList(str);
		} else {
			return new ArrayList<String>();
		}
	}

	/**
	 * 将WaybillPendingDto对象转换为WaybillPdaDto对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-21 下午4:30:09
	 */
	private WaybillPdaDto pdaValueCopyPending(WaybillPendingDto pendingDto) {
		if (null == pendingDto) {
			throw new WaybillSubmitException("运单提交失败！\n原因：无法将空的WaybillPendingDto对象转换为WaybillPdaDto对象");
		}

		// 待补录运单基本信息
		WaybillPendingEntity pending = pendingDto.getWaybillPending();
		// 待补录运单打木架信息
		WoodenRequirePendingEntity wooden = pendingDto.getWoodenRequirePending();
		// 待补录运单付款明细
		List<WaybillPaymentPendingEntity> paymentPending = pendingDto.getWaybillPaymentPending();
		// 待补录运单费用明细
		List<WaybillCHDtlPendingEntity> chargePending = pendingDto.getWaybillChargeDtlPending();

		// 判断待处理运单基本信息对象是否为空
		if (pending == null) {
			// 若为空则返回空对象
			return null;
		} else {
			// 定义PDA开单信息对象
			WaybillPdaDto dto = new WaybillPdaDto();
			// waybillPendingId(记录补录历史必填)
			dto.setWaybillPendingId(pending.getId());
			// 设置司机
			dto.setCreateUserCode(pending.getDriverCode());
			// operateTime（记录补录历史必填）
			dto.setOperateTime(pending.getBillTime());
			// 运单号
			dto.setWaybillNo(pending.getWaybillNo());
			// 出发部门
			dto.setStartOrg(pending.getReceiveOrgCode());
			// 提货方式
			dto.setReceiveMethod(pending.getReceiveMethod());
			// 目的站
			dto.setTargetOrgCode(pending.getTargetOrgCode());
			// 运输性质
			dto.setProductCode(pending.getProductCode());
			// 重量（单位：千克）
			dto.setGoodsWeightTotal(pending.getGoodsWeightTotal());
			// 体积(单位：立方米)
			dto.setGoodsVolumeTotal(pending.getGoodsVolumeTotal());
			// 件数
			dto.setGoodsQty(pending.getGoodsQtyTotal());
			// 货物类型
			dto.setGoodsTypeCode(pending.getGoodsTypeCode());
			// 优惠券编号
			dto.setDiscountNo(pending.getPromotionsCode());
			// 优惠金额
			dto.setDiscountAmount(pending.getPromotionsFee());
			// 付款方式
			dto.setPaidMethod(pending.getPaidMethod());
			// 增值服务费
			dto.setValueAddServiceDtoList(getValueAddServiceDtoList(chargePending));
			// 车牌号
			dto.setLicensePlateNum(pending.getLicensePlateNum());
			// 开单时间
			dto.setBillStart(pending.getBillTime());
			// 总费用
			dto.setAmount(pending.getTotalFee());
			// 总件数
			dto.setGoodsQty(pending.getGoodsQtyTotal());
			// 纸
			dto.setPaper(pending.getPaperNum());
			// 木
			dto.setWood(pending.getWoodNum());
			// 纤
			dto.setFibre(pending.getFibreNum());
			// 托
			dto.setSalver(pending.getSalverNum());
			// 膜
			dto.setMembrane(pending.getMembraneNum());
			// 其它
			dto.setOtherPackageType(pending.getOtherPackage());
			// 货物类型
			dto.setGoodsTypeCode(pending.getGoodsTypeCode());
			// 实收运费(手写现付金额)
			if (null != paymentPending) {
				// 循环遍历付款信息
				for (WaybillPaymentPendingEntity payment : paymentPending) {
					// 付款类型是否是手写预付金额
					if (WaybillConstants.PAYMENT_REAL_PAY.equals(payment
							.getType())) {
						// 设置实收运费
						dto.setActualFee(payment.getAmount());
						// 跳出本次循环
						break;
					}
				}
			}
			// 判断打木架对象是否为空
			if (wooden != null) {
				// 代打木架体积(单位：立方米)
				dto.setWoodVolume(wooden.getStandGoodsVolume());
				// 尺寸(单位：cm*cm*cm)
				dto.setWoodSize(wooden.getStandGoodsSize());
				// 代打木箱体积(单位：立方米)
				dto.setWoodBoxVolume(wooden.getBoxGoodsVolume());
				// 代打木箱尺寸(单位：cm*cm*cm)
				dto.setWoodBoxSize(wooden.getBoxGoodsSize());
				// 是否打木架
				dto.setIsWood(FossConstants.YES);
			} else {
				// 是否打木架
				dto.setIsWood(FossConstants.NO);
			}
			return dto;
		}
	}

	/**
	 * 将WaybillEntity对象转换为WaybillPdaDto
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-21 下午4:30:13
	 */
	private WaybillPdaDto pdaValueCopyWaybill(WaybillDto waybillDto) {
		// 运单基本信息
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		// 打木架信息
		WoodenRequirementsEntity wooden = waybillDto.getWoodenRequirementsEntity();
		// 付款类型
		List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();

		// 运单基本信息非空判断，防止出现空指针
		if (waybill == null) {
			// 若对象为空，则返回空对象
			return null;
		} else {
			// 定义PDA运单对象，用于封装相关数据
			WaybillPdaDto dto = new WaybillPdaDto();
			// waybillPendingId(记录补录历史必填)
			dto.setWaybillPendingId(waybill.getId());
			// operateTime（记录补录历史必填）
			dto.setOperateTime(waybill.getBillTime());
			// 运单号
			dto.setWaybillNo(waybill.getWaybillNo());
			// 出发部门
			dto.setStartOrg(waybill.getReceiveOrgCode());
			// 提货方式
			dto.setReceiveMethod(waybill.getReceiveMethod());
			// 目的站
			dto.setTargetOrgCode(waybill.getTargetOrgCode());
			// 运输性质
			dto.setProductCode(waybill.getProductCode());
			// 重量（单位：千克）
			dto.setGoodsWeightTotal(waybill.getGoodsWeightTotal());
			// 体积(单位：立方米)
			dto.setGoodsVolumeTotal(waybill.getGoodsVolumeTotal());
			// 件数
			dto.setGoodsQty(waybill.getGoodsQtyTotal());
			// 货物类型
			dto.setGoodsTypeCode(waybill.getGoodsTypeCode());
			// 优惠券编号
			dto.setDiscountNo(waybill.getPromotionsCode());
			// 优惠金额
			dto.setDiscountAmount(waybill.getPromotionsFee());
			// 付款方式
			dto.setPaidMethod(waybill.getPaidMethod());
			// 增值服务费
			dto.setValueAddServiceDtoList(getValueAddServiceDtoList(waybillDto));
			// 车牌号
			dto.setLicensePlateNum(waybill.getLicensePlateNum());
			// 开单时间
			dto.setBillStart(waybill.getBillTime());
			// 总费用
			dto.setAmount(waybill.getTotalFee());
			// 总件数
			dto.setGoodsQty(waybill.getGoodsQtyTotal());
			// 纸
			dto.setPaper(waybill.getPaperNum());
			// 木
			dto.setWood(waybill.getWoodNum());
			// 纤
			dto.setFibre(waybill.getFibreNum());
			// 托
			dto.setSalver(waybill.getSalverNum());
			// 膜
			dto.setMembrane(waybill.getMembraneNum());
			// 其它
			dto.setOtherPackageType(waybill.getOtherPackage());
			// 货物类型
			dto.setGoodsTypeCode(waybill.getGoodsTypeCode());
			// 货物类型
			dto.setGoodsTypeCode(waybill.getGoodsTypeCode());
			// 实收运费(手写现付金额)
			if (null != paymentList) {
				// 循环遍历付款信息
				for (WaybillPaymentEntity payment : paymentList) {
					// 付款类型是否是手写预付金额
					if (WaybillConstants.PAYMENT_REAL_PAY.equals(payment
							.getType())) {
						// 设置实收运费
						dto.setActualFee(payment.getAmount());
						// 跳出本次循环
						break;
					}
				}
			}
			// 判断打木架对象是否为空
			if (wooden != null) {
				// 代打木架体积(单位：立方米)
				dto.setWoodVolume(wooden.getStandGoodsVolume());
				// 尺寸(单位：cm*cm*cm)
				dto.setWoodSize(wooden.getStandGoodsSize());
				// 代打木箱体积(单位：立方米)
				dto.setWoodBoxVolume(wooden.getBoxGoodsVolume());
				// 代打木箱尺寸(单位：cm*cm*cm)
				dto.setWoodBoxSize(wooden.getBoxGoodsSize());
				// 是否打木架
				dto.setIsWood(FossConstants.YES);
			} else {
				// 是否打木架
				dto.setIsWood(FossConstants.NO);
			}
			return dto;
		}
	}

	/**
	 * 获得增值服务项
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 下午2:39:04
	 */
	private List<ValueAddServiceDto> getValueAddServiceDtoList(Object o) {
		// 运单费用项单项费用信息
		List<WaybillChargeDtlEntity> entityList = null;
		// 待处理运单费用项单项费用信息
		List<WaybillCHDtlPendingEntity> pendingList = null;
		// 增值服务项信息集合
		List<ValueAddServiceDto> dtoList = null;

		// 判断是否是为正式运单信息
		if (o instanceof WaybillDto) {
			WaybillDto waybillDto = (WaybillDto) o;
			entityList = waybillDto.getWaybillChargeDtlEntity();
			// 判断集合是否为空
			if (!CollectionUtils.isEmpty(entityList)) {
				dtoList = new ArrayList<ValueAddServiceDto>();
				// 增值服务项信息
				for (WaybillChargeDtlEntity entity : entityList) {
					ValueAddServiceDto dto = new ValueAddServiceDto();
					// 增值服务code
					dto.setCode(entity.getPricingEntryCode());
					// 增值服务价格
					dto.setAmount(entity.getAmount());
					// 加入到集合中
					dtoList.add(dto);
				}
			}
		}

		// 判断是否为待处理运单信息
		if (o instanceof WaybillPendingDto) {
			// 定义待处理运单DTO
			WaybillPendingDto waybillPendingDto = (WaybillPendingDto) o;
			// 获得待处理运单费用明细集合
			pendingList = waybillPendingDto.getWaybillChargeDtlPending();
			// 判断集合是否为空
			if (!CollectionUtils.isEmpty(pendingList)) {
				dtoList = new ArrayList<ValueAddServiceDto>();
				// 增值服务项信息
				for (WaybillCHDtlPendingEntity entity : pendingList) {
					ValueAddServiceDto dto = new ValueAddServiceDto();
					// 增值服务code
					dto.setCode(entity.getPricingEntryCode());
					// 增值服务价格
					dto.setAmount(entity.getAmount());
					// 加入到集合中
					dtoList.add(dto);
				}
			}
		}

		return dtoList;
	}

	/**
	 * 处理待办事项
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 下午4:41:39
	 */
	public void handleToDoMsg(WaybillDto waybillDto) {
		// 捕捉可能出现业务异常信息
		try {
			// 定义运单基本信息对象
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
			// 运单状态
			String waybillPendingStatus = StringUtil
					.defaultIfNull(waybillEntity.getPendingType());
			// 是否为PDA已补录状态
			if (waybillPendingStatus.equals(WaybillConstants.PDA_ACTIVE)) {
//				if (waybillDto.getReceiveOrgCode() != null) {
//					// 收货组织编码
//					receiveOrgCode = StringUtil.defaultIfNull(waybillDto.getReceiveOrgCode());
//				} else {
//					// 收货组织编码
//					receiveOrgCode = StringUtil.defaultIfNull(waybillDto.getWaybillEntity().getCreateOrgCode());
//				}

				// 运单号
				String waybillNo = StringUtil.defaultIfNull(waybillEntity.getWaybillNo());
				// 捕捉“移除消息提醒”的异常
				try {
					// 移除消息提醒
					pickupToDoMsgService.removeOneToDoMsg(WaybillConstants.PKP_PDA_WAYBILL, null, waybillNo);
				} catch (BusinessException e) {
					// 添加运单提交时的异常日志
					LOG.error("运单提交时，调用【处理待事项】方法异常！", e);
					// 抛出“删除待办事项消息失败”的异常信息
					throw new TodoActionException(TodoActionException.DELETEPENDINGS_FAIL,
							new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
				}
			}
		} catch (Exception e) {
			// 添加接口调用异常日志
			LOG.error("运单提交时，调用【处理待办事项】方法时异常！", e);
			// 后台打印异常信息，不做抛出异常操作，以免影响正常业务流程
			throw new WaybillImportException("运单提交时，调用【处理待办事项】方法时异常！", e.getMessage());
		}
	}

	/**
	 * 
	 * <p>
	 * 更新货物数量
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-13 下午6:18:59
	 * @param waybillNo
	 * @param goodNum
	 * @param userCode
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#updateGoodsNum(java.lang.String,
	 *      int, java.lang.String, java.lang.String)
	 */
	public ResultDto updateGoodsNum(String waybillNo, int goodNum,
			String userCode, String orgCode) {
		// 定义结果对象
		ResultDto res = new ResultDto();
		// 根据运单号查询运单基本信息对象
		WaybillEntity waybill = waybillDao.queryWaybillByNo(waybillNo);
		// 运单基本信息对象非空判断
		if (waybill != null) {
			// 总件数
			waybill.setGoodsQtyTotal(goodNum);
			// 修改人工号
			waybill.setModifyUserCode(userCode);
			// 修改人所属组织
			waybill.setModifyOrgCode(orgCode);

			// 更改运单基本信息数据
			waybillDao.updateWaybill(waybill);
			// 更改冗余对象中的货物数量
			res = actualFreightService.updateGoodsNum(waybillNo, goodNum);

			/*
			 * 增加对待办的判断，因为在集中开单的时候一些开单组不做入库操作，减少的件数会新增流水号，
			 * //而新增的流水号不会产生待办，从而产生新增的流水号的走货路径不会调整过来，产生问题 BUG-58130
			 * 修改人:Foss-105888-Zhangxingwang 2013-11-5 9:32:57
			 */
			// 如果更改实际货物信息表数据成功，并且是集中开单才会走此方法,尽量不破坏之前逻辑
			if (!ZEROSTR.equals(res.getCode()) && FossConstants.YES.equals(waybill.getPickupCentralized())) {
				res = addNewSeriosLablegoodsTodo(waybill);
			}
		} else {
			// 设置查询数据失败
			res.setCode(ZEROSTR);
			// 设置返回的消息
			res.setMsg("传入的运单号未找到有效的记录");
		}
		return res;
	}

	/**
	 * @function 对新增流水号新增未处理的待办信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-5 10:03:09
	 * @param waybill
	 * @return
	 */
	private ResultDto addNewSeriosLablegoodsTodo(WaybillEntity waybill) {
		ResultDto res = new ResultDto();
		List<LabeledGoodEntity> labelGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybill.getWaybillNo());
		if (CollectionUtils.isEmpty(labelGoodList)) {
			// 设置查询数据失败
			res.setCode(ZEROSTR);
			// 设置返回的消息
			res.setMsg("传入的运单号未找到有效的流水号");
			return res;
		}
		// 根据流水号逐一查询，查询到了的不做任何操作，查询不到的新增对应的记录
		for (LabeledGoodEntity entity : labelGoodList) {
			List<LabeledGoodTodoEntity> labelGoodTodoList = labeledGoodTodoDao.queryLabelGoodTodoStatusIsNoByLabelGoodId(
					entity.getId(), entity.getSerialNo(), FossConstants.NO);
			// 没有则需要进行新增,只是针对新增流水号的这种情况
			if (CollectionUtils.isEmpty(labelGoodTodoList)) {
				List<WaybillRfcEntity> list = waybillRfcService.queryWaybillRfcAcceptByWaybillNo(entity.getWaybillNo());
				// 这里只是遍历一次所有更改单信息
				if (CollectionUtils.isNotEmpty(list)) {
					for (WaybillRfcEntity rfc : list) {
						// 内部变更和外部变更
						List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList = waybillRfcVarificationDao
								.queryWayBillRfcChangeDetailByRfcId(rfc.getId());
						// 这里只需要执行一次，所以不影响其性能
						for (WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList) {
							String changeDetailItem = changeDetailEntity.getRfcItems();
							// 标签信息变更
							if (checkWaybillRfcChangedItem(changeDetailItem)) {
								LabeledGoodTodoEntity labeled = new LabeledGoodTodoEntity();
								labeled.setWaybillRfcId(rfc.getId());// 更改单ID
								labeled.setStatus(FossConstants.NO);// 待办状态
								labeled.setExceptionMsg(FossConstants.NO);// 异常信息
								labeled.setPrinted(FossConstants.NO);// 设置为未打印
								labeled.setLabeledGoodId(entity.getId());// 货签信息ID
								labeled.setCreateTime(new Date());// 设置创建时间
								labeled.setSerialNo(entity.getSerialNo());// 设置流水号
								labeled.setIsSendRemind(FossConstants.NO);
								// 新增1条待办记录
								labeledGoodTodoDao.insertSelective(labeled);
								break;// 防止生成多次代办 这个break一定需要 否则会重复生成代办 xiaowei
										// add
							}
						}

					}
				}
			}
		}

		// 删除运单号已经作废了的流水号
		List<LabeledGoodEntity> depLabelGoodList = labeledGoodService.queryLabelGoodStatusisNByWaybillNo(waybill.getWaybillNo());
		if (CollectionUtils.isNotEmpty(depLabelGoodList)) {
			List<String> delabelGoodsList = new ArrayList<String>();
			for (LabeledGoodEntity en : depLabelGoodList) {
				delabelGoodsList.add(en.getId());
			}
			labeledGoodTodoDao.deleteTodoByLabelgoodIds(delabelGoodsList);
		}
		// DEFECT-475 Foss-105888-Zhangxingwang 2013-12-9 17:31:32
		res.setCode(ResultDto.SUCCESS);
		res.setMsg("");
		return res;
	}

	private boolean checkWaybillRfcChangedItem(String item) {
		// 改变类型:提货网点、包装(纸木纤托膜其他)、件数、货物类型、运输性质、提货方式
		String items = "customerPickupOrgName+paper+wood+fibre+salver+membrane+otherPackage+goodsQtyTotal+goodsType+productCode+waybillNo+receiveMethod+";
		return (items.indexOf(item + '+') >= 0);
	}

	/**
	 * 
	 * <p>
	 * 新增业务标识
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-4
	 * @param waybillDto
	 *            void
	 */
	private void addWaybillTransactionStat(WaybillDto waybillDto) {
		// 运单终结状态对象
		WaybillTransactionEntity transacionStat = new WaybillTransactionEntity();
		// 主键
		transacionStat.setId(UUIDUtils.getUUID());
		// 运单号
		transacionStat.setWaybillNo(waybillDto.getWaybillEntity()
				.getWaybillNo());
		// 业务完结情况
		transacionStat.setBusinessOver(ZEROSTR);
		// 财务完结情况
		transacionStat.setFinanceOver(ZEROSTR);
		// 创建日期
		transacionStat.setCreateDate(new Date());
		
		/**
		 * 防止多线程读取不到用户信息
		 * @author Foss-278328-hujinyang
		 */
		String userId = "";
		if(WaybillConstants.YES.equals(waybillDto.getIsGuiSubmit())){
			userId = waybillDto.getCurrentInfo().getUser().getId();
		}else{
			userId = FossUserContext.getCurrentUser().getId();
		}
		// 创建人
		transacionStat.setCreateUser(userId);
		// 新增业务标识
		waybillTransactionService.addWaybillTransaction(transacionStat);
	}

	/**
	 * 
	 * <p>
	 * 运单冗余信息保存<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param waybillDto
	 *            void
	 */
	public void addActualFreightInfo(WaybillDto waybillDto,
			WaybillSystemDto systemDto, boolean isPdaPending) {
		// 回复代码请参考 27619
		// 定义实际货物对象
		ActualFreightEntity actualFreightEntity = waybillDto
				.getActualFreightEntity();
		//设置实际货物是否统一结算字段
//		actualFreightEntity.setStartCentralizedSettlement(actualFreightEntity.getStartCentralizedSettlement());
		// 新运单号
		String waybillNo = StringUtil.defaultIfNull(actualFreightEntity.getWaybillNo());

		// 获取最终库存部门和货区
		actualFreightEntity = waybillStockService.getEndStockCodeAndAreaCode(
				actualFreightEntity, waybillDto.getWaybillEntity());
		// 当日补录还是次日补录
		actualFreightEntity.setIsNextDayPending(waybillDto
				.getActualFreightEntity().getIsNextDayPending());
		// 设置记录创建时间
		actualFreightEntity.setCreateTime(systemDto.getBillTime());
		// 设置运单补录时间
		if (actualFreightEntity.getIsNextDayPending() != null) {
			actualFreightEntity.setPendingTime(systemDto.getCreateTime());
		}
		// 老运单号
		String oldWaybillNo = StringUtil.defaultIfNull(waybillDto.getOldWaybillNo());
		/**
		 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
		 * 
		 * @author Foss-206860
		 */
		// 通过货物名称获取综合行业货源品类基础资料
		String industrySourceCategory = queryIndustrySourceCategory(actualFreightEntity.getGoodsName());
		actualFreightEntity.setIndustrySourceCategory(industrySourceCategory);
		// ------------DMANA-8928----end------------------------------------------
		// 判断PDA补录是否已在运单表中生成记录（若在运单表生成了记录，则会在标签表、actualFreight表中生成记录）
		if (isPdaPending) {
			// 不为空时，则更新
			actualFreightService.updateByWaybillNo(actualFreightEntity, oldWaybillNo);
		} else {
			// 根据运单号查询
			if (actualFreightService.isExistActualFreight(waybillNo)) {
				LOG.error("运单号对应的ActualFreight数据已存在！");
				throw new WaybillSubmitException("运单号[" + waybillNo + "],对应的ActualFreight对象已存在!");
			}
			int i = actualFreightService.insertWaybillActualFreight(actualFreightEntity);
			if (i <= 0) {
				throw new WaybillSubmitException("运单号[" + waybillNo + "],对应的ActualFreight对象已存在!");
			}
		}
	}

	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
	 * 
	 * @author Foss-206860
	 */
	@Override
	public String queryIndustrySourceCategory(String goodsName) {
		// 行业货源品类集合
		List<SourceCategoriesEntity> industrySourceCategoryList = new ArrayList<SourceCategoriesEntity>();
		industrySourceCategoryList = sourceCategoriesService.querySourceCategoriesEntitysByName(goodsName);
		// 通过货物名称获取综合基础资料---当返回集合只有一条记录时，表示匹配成功，返回行业货源品类编码，否则返回“其他类”编码
		if (CollectionUtils.isNotEmpty(industrySourceCategoryList) && industrySourceCategoryList.size() == 1) {
			return industrySourceCategoryList.get(0).getCategory();
		} else {
			return "BSE_OTHER";
		}
	}

	/**
	 * 生成结算单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午5:25:38
	 */
	public void addSettleBill(WaybillDto waybillDto) {
		//折让方式的装卸费赋值到装卸费字段
		if(null!=waybillDto&&
			null!=waybillDto.getActualFreightEntity()&&
			null!=waybillDto.getActualFreightEntity().getDcServicefee()){
		    waybillDto.getWaybillEntity().setServiceFee(waybillDto.getActualFreightEntity().getDcServicefee());
        } 
		// 捕捉生成结算单时，可能出现的异常信息
		try {
			if(waybillDto == null){
				throw new WaybillSubmitException("实体类waybillDto为空");
			}
			// 获取当前用户
			CurrentInfo currentInfo = waybillDto.getCurrentInfo();
			WaybillPickupInfoDto gainWaybillPickupInfo = gainWaybillPickupInfo(waybillDto);
			// 调用结算接口生成相关财务单据（异常自动抛出到action层）
			 //350909  郭倩云  将数据插入到数据库表PKP.T_SRV_ADD_ASYN_WAYBILL中
			String waybillPickupInfoJson=JSONObject.toJSONString(gainWaybillPickupInfo);
		    String currentInfoJson = JSONObject.toJSONString(currentInfo);
		    String jsonString =waybillPickupInfoJson+"#"+currentInfoJson;
		    String requestType=WaybillConstants.ADD_SETTLE_BILL;
		    String flag = FossConstants.NO;
			String[] codes = new String[1];
			codes[0]=WaybillConstants.PKP_WSCWAYBILL_AUTO_SCHEDULE;
			List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
			if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
				//开关是否关闭
				flag = configurationParamsEntitys.get(0).getConfValue();
			}
			if(StringUtils.equals(FossConstants.YES, flag)){
				//开关打开异步调用结算接口生成财务单据
				wSCWaybillProcessService.addWSCWaybillProcessEntity(requestType,jsonString);
			}else{
				//不存在开关或者开关关闭同步调用结算接口生成财务单据
				waybillPickupService.addWaybill(gainWaybillPickupInfo,currentInfo);
			}
			// CN-59:返货开单调用结算接口失败，开单后没有红冲原单财务单据
			// wutao
			// 返货开单
			WaybillExpressEntity waybillExpress = waybillDto
					.getWaybillExpressEntity();
			if (waybillExpress != null && StringUtils.isNotEmpty(waybillExpress.getOriginalWaybillNo())
					&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(waybillExpress.getReturnType())) {
				
				RequestDO requestDO = new RequestDO();
				requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".addSettleBill");
				requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
				requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
				requestDO.setSourceBillNos(waybillExpress.getOriginalWaybillNo());
				VestResponse response=null;
				try {
				    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
					if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
						List<VestBatchResult> batchResults = response.getVestBatchResult();
						VestBatchResult batchResult = batchResults.get(0);
						if(batchResult.getVestSystemCode().equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS)){
							waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(waybillDto), currentInfo);
						}
					}else {
						waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(waybillDto), currentInfo);
					}
				} catch (Exception e) {
					throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
				}
				
				
			}


			// 捕捉异常类型为业务异常
		} catch (BusinessException s) {
			// 添加异常日志
			LOG.error("Excepton", s);
			// 抛出“调用结算接口出错”的异常，并附上出错信息
			StringBuffer sb = new StringBuffer();
			sb.append(StringUtil.defaultIfNull(s.getErrorCode()));
			sb.append("\n");
			sb.append(StringUtil.defaultIfNull(s.getMessage()));
			throw new WaybillSubmitException(WaybillSubmitException.ADDSETTLEBILL_FAIL, new Object[] { messageBundle.getMessage(sb.toString()) });
		}finally{
		  //折让方式应收单生成之后更改成 原来的装卸费数据
		  	if(null!=waybillDto&&
				null!=waybillDto.getActualFreightEntity()&&
				null!=waybillDto.getActualFreightEntity().getDcServicefee()){
			    waybillDto.getWaybillEntity().setServiceFee(BigDecimal.ZERO);
            } 
		}
	}

    public void addSettleBillForCUBC(WaybillDto waybillDto) {
        //折让方式的装卸费赋值到装卸费字段
        if(null!=waybillDto&&
                null!=waybillDto.getActualFreightEntity()&&
                null!=waybillDto.getActualFreightEntity().getDcServicefee()){
            waybillDto.getWaybillEntity().setServiceFee(waybillDto.getActualFreightEntity().getDcServicefee());
        }
        // 捕捉生成结算单时，可能出现的异常信息
        try {
            if(waybillDto == null){
                throw new WaybillSubmitException("实体类waybillDto为空");
            }
            // 获取当前用户
            CurrentInfo currentInfo = waybillDto.getCurrentInfo();

            WaybillPickupInfoDto gainWaybillPickupInfo = gainWaybillPickupInfo(waybillDto);
            //DP-FOSS zhaoyiqing 343617 2016-10-17
            //配合CUBC将调用结算接口的校验前移
            if(!StringUtil.equals(waybillDto.getIsExpress(),WaybillConstants.YES)){
                addWaybillValidateForCUBC(gainWaybillPickupInfo);
            }
          //调用cubc灰度接口
    		VestBatchResult vestResult = this.getVestResult(waybillDto,"submitWaybill");
    		//不为CUBC(灰度接口异常或者为FOSS)，都走FOSS
    		if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
	            // 调用结算接口生成相关财务单据（异常自动抛出到action层）
    			String waybillPickupInfoJson=JSONObject.toJSONString(gainWaybillPickupInfo);
    		    String currentInfoJson = JSONObject.toJSONString(currentInfo);
    		    String jsonString =waybillPickupInfoJson+"#"+currentInfoJson;
    		    String requestType=WaybillConstants.ADD_SETTLE_BILL;
    		    String flag = FossConstants.NO;
    			String[] codes = new String[1];
    			codes[0]=WaybillConstants.PKP_WSCWAYBILL_AUTO_SCHEDULE;
    			List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
    			if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
    				//开关是否关闭
    				flag = configurationParamsEntitys.get(0).getConfValue();
    			}
    			if(StringUtils.equals(FossConstants.YES, flag)){
    				//开关打开异步调用结算接口生成财务单据
    				wSCWaybillProcessService.addWSCWaybillProcessEntity(requestType,jsonString);
    			}else{
    				//不存在开关或者开关关闭同步调用结算接口生成财务单据
    				waybillPickupService.addWaybill(gainWaybillPickupInfo,currentInfo);
    			}
    		}
            // CN-59:返货开单调用结算接口失败，开单后没有红冲原单财务单据
            // wutao
            // 返货开单
            WaybillExpressEntity waybillExpress = waybillDto
                    .getWaybillExpressEntity();
            if (waybillExpress != null && StringUtils.isNotEmpty(waybillExpress.getOriginalWaybillNo())
                    && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(waybillExpress.getReturnType())) {
				
				RequestDO requestDO = new RequestDO();
				requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".addSettleBill");
				requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
				requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
				requestDO.setSourceBillNos(waybillExpress.getOriginalWaybillNo());
				VestResponse response=null;
				try {
				    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
					if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
						List<VestBatchResult> batchResults = response.getVestBatchResult();
						VestBatchResult batchResult = batchResults.get(0);
						if(batchResult.getVestSystemCode().equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS)){
							waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(waybillDto), currentInfo);
						}
					}else {
						waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(waybillDto), currentInfo);
					}
				} catch (Exception e) {
					throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
				}
				
				
			}


            // 捕捉异常类型为业务异常
        } catch (BusinessException s) {
            // 添加异常日志
            LOG.error("Excepton", s);
            // 抛出“调用结算接口出错”的异常，并附上出错信息
            StringBuffer sb = new StringBuffer();
            sb.append(StringUtil.defaultIfNull(s.getErrorCode()));
            sb.append("\n");
            sb.append(StringUtil.defaultIfNull(s.getMessage()));
            throw new WaybillSubmitException(WaybillSubmitException.ADDSETTLEBILL_FAIL, new Object[] { messageBundle.getMessage(sb.toString()) });
        }finally{
            //折让方式应收单生成之后更改成 原来的装卸费数据
            if(null!=waybillDto&&
                    null!=waybillDto.getActualFreightEntity()&&
                    null!=waybillDto.getActualFreightEntity().getDcServicefee()){
                waybillDto.getWaybillEntity().setServiceFee(BigDecimal.ZERO);
            }
        }
    }


    //DP-FOSS zhaoyiqing 343617 2016-10-17
    //配合CUBC将调用结算接口的校验前移
    private void addWaybillValidateForCUBC(WaybillPickupInfoDto waybill) {


        // 校验运单的收货部门、接货部门、到达部门、录入部门为空
        if (StringUtils.isEmpty(waybill.getCreateOrgCode())
                || StringUtils.isEmpty(waybill.getReceiveOrgCode())
                || StringUtils.isEmpty(waybill.getLastLoadOrgCode())) {
            throw new BusinessException("部门信息不完整");
        }

        // 校验运单金额，预付、到付、公布价运费、送货费、包装费、代收货款手续费、保价费，其它费用，优惠费用单个字段的有效性，其中任意一项为空或者小于0
        if (waybill.getTotalFee() == null
                || waybill.getTotalFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getPrePayAmount() == null
                || waybill.getPrePayAmount().compareTo(BigDecimal.ZERO) < 0
                || waybill.getToPayAmount() == null
                || waybill.getToPayAmount().compareTo(BigDecimal.ZERO) < 0
                || waybill.getCodAmount() == null
                || waybill.getCodAmount().compareTo(BigDecimal.ZERO) < 0
                || waybill.getTransportFee() == null
                || waybill.getTransportFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getPickupFee() == null
                || waybill.getPickupFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getDeliveryGoodsFee() == null
                || waybill.getDeliveryGoodsFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getPackageFee() == null
                || waybill.getPackageFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getCodFee() == null
                || waybill.getCodFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getInsuranceFee() == null
                || waybill.getInsuranceFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getOtherFee() == null
                //ISSUE-2816
                //|| waybill.getOtherFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getValueAddFee() == null
                //ISSUE-2816
                //|| waybill.getValueAddFee().compareTo(BigDecimal.ZERO) < 0
                || waybill.getPromotionsFee() == null
                || waybill.getPromotionsFee().compareTo(BigDecimal.ZERO) < 0) {
            LOG.info("运单部分字段金额不正确，不能继续操作："+"总金额："+waybill.getTotalFee()+"，预付款："+waybill.getPrePayAmount()+
                    "，到付："+waybill.getToPayAmount()+"，代收："+waybill.getCodAmount() +"，运费："+waybill.getTransportFee()+"，接货费："+waybill.getPickupFee()+"，送货费："+waybill.getDeliveryGoodsFee()+
                    "，包装费："+waybill.getPackageFee()+",代收费："+waybill.getCodFee()+"，保费："+waybill.getInsuranceFee()+"，其他费用："+waybill.getOtherFee()+"，增值费："+waybill.getValueAddFee()+"，优惠金额："+waybill.getPromotionsFee());
            throw new BusinessException("运单部分字段金额不正确，不能继续操作");
        }


        // 校验发票标记是在范围内
        if (StringUtil.isEmpty(waybill.getInvoiceMark())) {
            throw new BusinessException("没有发票标记");
        }
        else if (!WaybillConstants.INVOICE_01.equals(waybill.getInvoiceMark())
                && !WaybillConstants.INVOICE_02.equals(waybill.getInvoiceMark())) {
            throw new BusinessException("发票标记不在范围之内："+waybill.getInvoiceMark());
        }

        //增加信用额度校验
        if(StringUtil.equals(WaybillConstants.MONTH_PAYMENT,waybill.getPaidMethod())||
                StringUtil.equals(WaybillConstants.TEMPORARY_DEBT,waybill.getPaidMethod())){

            //DP-FOSS 343617 zhaoyiqing 20170107
            //增加灰度接口
            RequestDO requestDO = new RequestDO();
            requestDO.setServiceCode(WaybillManagerService.class.getName()+".addWaybillValidateForCUBC");
            requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
            requestDO.setCustomerCodes(waybill.getDeliveryCustomerCode());
            requestDO.setCustomerType(Constants.GRAY_SOURCE_BILLTYPE_CU);
            VestResponse response;
            try{
                response = grayScaleService.vestByCustomer(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
            }catch (Exception e){
            	LOG.info("Exception:"+e.getMessage());
                return;
            }
            if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
                List<VestBatchResult> batchResults = response.getVestBatchResult();
                VestBatchResult batchResult = batchResults.get(0);
                if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_CUBC, batchResult.getVestSystemCode())) {
                    //客户编码
                    //收货部门
                    //付款方式在上面
                    //金额
                    DebitForCUBCDto beBebtForCUBC = this.isBeBebtForCUBC(waybill.getDeliveryCustomerCode(), waybill.getReceiveOrgCode(), waybill.getPaidMethod(), waybill.getPrePayAmount(), waybill.getWaybillNo());
                    if (!beBebtForCUBC.getIsSuccess()) {
                        if (StringUtil.isEmpty(beBebtForCUBC.getMsg())) {
                            throw new BusinessException("CUBC接口传入参数异常！");
                        }
                        throw new BusinessException(beBebtForCUBC.getMsg());
                    }
                }
            }
        }

    }

    /**
	 * 返货开单传递给结算封装实体！
	 */
	private WaybillPickupInfoDto gainOrignalWaybillPickUpInfo(
			WaybillDto waybillDto) {
		// 运单基本信息
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		// 开户银行信息
		CusAccountEntity openBank = waybillDto.getOpenBank();
		// 运单冗余信息
		ActualFreightEntity afhe = waybillDto.getActualFreightEntity();
		// 定义封闭对象
		WaybillPickupInfoDto dto = new WaybillPickupInfoDto();
		// 在这个地方设置值
		// 將是【否統一結算】【合同部門】【催款部門】傳遞給結算部門
		dto.setOrigUnifiedSettlement(afhe.getStartCentralizedSettlement());
		dto.setDestUnifiedSettlement(afhe.getArriveCentralizedSettlement());
		dto.setOrigContractUnifiedCode(afhe.getStartContractOrgCode());
		dto.setDestContractUnifiedCode(afhe.getArriveContractOrgCode());
		dto.setOrigUnifiedDuningCode(afhe.getStartReminderOrgCode());
		dto.setDestUnifiedDuningCode(afhe.getArriveReminderOrgCode());

		// 捕捉对象值拷贝可能出现的异常
		try {
			// 将waybill值拷贝到DTO变量中
			PropertyUtils.copyProperties(dto, waybill);

			dto.setWaybillNo(waybillDto.getWaybillExpressEntity().getOriginalWaybillNo());
		} catch (Exception e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出异常信息
			throw new WaybillSubmitException(WaybillSubmitException.COPYWAYBILLINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getMessage()) });
		}

		// 出发部门
		SaleDepartmentEntity leaveDept = saleDepartmentService.querySaleDepartmentByCode(waybill.getReceiveOrgCode());
		// 最终配载部门
		SaleDepartmentEntity arriveDept = saleDepartmentService
				.querySaleDepartmentByCode(waybill.getLastLoadOrgCode());
		// 增加使用新增的产品和是否快递字段判断
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())) {
			OrgAdministrativeInfoEntity org = this.queryOrgInfoByCode(waybill.getCustomerPickupOrgCode());
			if (org == null || (org != null && org.checkExpressSalesDepartment())) {
				dto.setIsSelfStation(FossConstants.NO);
			} else {
				dto.setIsSelfStation(FossConstants.YES);
			}
		}

		// 若出发部门对象不为空则，设置出发部门名称
		if (leaveDept != null) {
			// 出发部门名称
			dto.setReceiveOrgName(leaveDept.getName());
		}

		// 若到达部门对象不为空则，设置到达部门名称
		if (arriveDept != null) {
			// 到达部门名称
			dto.setLastLoadOrgName(arriveDept.getName());
			// 最终配载部门Code wutao
			dto.setLastLoadOrgCode(arriveDept.getCode());
		}

		// 非空判断
		if (null != openBank) {
			// 开户行编码
			dto.setBankHQCode(openBank.getBankCode());
			// 开户行名称
			dto.setBankHQName(openBank.getOpeningBankName());
			// 省份编码
			dto.setProvinceCode(openBank.getProvCode());
			// 省份名
			dto.setProvinceName(openBank.getProvinceName());
			// 城市编码
			dto.setCityCode(openBank.getCityCode());
			// 城市名
			dto.setCityName(openBank.getCityName());
			// 支行编码（行号）
			dto.setBankBranchCode(openBank.getBranchBankCode());
			// 支行名称
			dto.setBankBranchName(openBank.getBranchBankName());
			// 对公对私标志
			dto.setPublicPrivateFlag(openBank.getAccountNature());
			// 收款人与发货人关系
			dto.setPayeeRelationship(openBank.getCustomer());
			// 收款人手机号码
			dto.setPayeePhone(openBank.getMobilePhone());
		}

		// 小件新加字段
		if (waybillDto.getWaybillExpressEntity() != null) {
			dto.setPosSerialNum(waybillDto.getWaybillExpressEntity().getPdaSerial());
			dto.setBatchNo(waybillDto.getWaybillExpressEntity().getBankTradeSerail());
		}

		dto.setInvoiceMark(afhe.getInvoice());
		return dto;
	}

	/**
	 * 获得代收货款银企帐号信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-28 下午5:22:49
	 */
	private WaybillPickupInfoDto gainWaybillPickupInfo(WaybillDto waybillDto) {
		// 运单基本信息
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		// 开户银行信息
		CusAccountEntity openBank = waybillDto.getOpenBank();
		// 运单冗余信息
		ActualFreightEntity afhe = waybillDto.getActualFreightEntity();
		// 定义封闭对象
		WaybillPickupInfoDto dto = new WaybillPickupInfoDto();

		// 在这个地方设置值
		// 將是【否統一結算】【合同部門】【催款部門】傳遞給結算部門
		dto.setOrigUnifiedSettlement(afhe.getStartCentralizedSettlement());
		dto.setDestUnifiedSettlement(afhe.getArriveCentralizedSettlement());
		dto.setOrigContractUnifiedCode(afhe.getStartContractOrgCode());
		dto.setDestContractUnifiedCode(afhe.getArriveContractOrgCode());
		dto.setOrigUnifiedDuningCode(afhe.getStartReminderOrgCode());
		dto.setDestUnifiedDuningCode(afhe.getArriveReminderOrgCode());

		// 捕捉对象值拷贝可能出现的异常
		try {
			// 将waybill值拷贝到DTO变量中
			PropertyUtils.copyProperties(dto, waybill);
		} catch (Exception e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出异常信息
			throw new WaybillSubmitException(WaybillSubmitException.COPYWAYBILLINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getMessage()) });
		}

		// 出发部门
//		SaleDepartmentEntity leaveDept = saleDepartmentService
//				.querySaleDepartmentByCode(waybill.getReceiveOrgCode());
		// 最终配载部门
//		SaleDepartmentEntity arriveDept = saleDepartmentService
//				.querySaleDepartmentByCode(waybill.getLastLoadOrgCode());
		// 出发部门
		SaleDepartmentEntity leaveDept = saleDepartmentService
				.querySimpleSaleDepartmentByCode(waybill.getReceiveOrgCode());
		// 最终配载部门
		SaleDepartmentEntity arriveDept = saleDepartmentService
				.querySimpleSaleDepartmentByCode(waybill.getLastLoadOrgCode());
		
		// 增加使用新增的产品和是否快递字段判断
		/**
		 * 优化获取方式
		 * @author Foss-278328-hujinyang
		 * @time 20160422
		 */
//		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())) {
		if (isExpress(waybillDto)) {
//			OrgAdministrativeInfoEntity org = this.queryOrgInfoByCode(waybill.getCustomerPickupOrgCode());
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybill.getCustomerPickupOrgCode());

			if (org == null || (org != null && org.checkExpressSalesDepartment())) {
				dto.setIsSelfStation(FossConstants.NO);
			}else {
				dto.setIsSelfStation(FossConstants.YES);
			}
		}

		// 若出发部门对象不为空则，设置出发部门名称
		if (leaveDept != null) {
			// 出发部门名称
			dto.setReceiveOrgName(leaveDept.getName());
		}

		// 若到达部门对象不为空则，设置到达部门名称
		if (arriveDept != null) {
			// 到达部门名称
			dto.setLastLoadOrgName(arriveDept.getName());
		}

		// 非空判断
		if (null != openBank) {
			// 开户行编码
			dto.setBankHQCode(openBank.getBankCode());
			// 开户行名称
			dto.setBankHQName(openBank.getOpeningBankName());
			// 省份编码
			dto.setProvinceCode(openBank.getProvCode());
			// 省份名
			dto.setProvinceName(openBank.getProvinceName());
			// 城市编码
			dto.setCityCode(openBank.getCityCode());
			// 城市名
			dto.setCityName(openBank.getCityName());
			// 支行编码（行号）
			dto.setBankBranchCode(openBank.getBranchBankCode());
			// 支行名称
			dto.setBankBranchName(openBank.getBranchBankName());
			// 对公对私标志
			dto.setPublicPrivateFlag(openBank.getAccountNature());
			// 收款人与发货人关系
			dto.setPayeeRelationship(openBank.getCustomer());
			// 收款人手机号码
			dto.setPayeePhone(openBank.getMobilePhone());
		}

		// 小件新加字段
		if (waybillDto.getWaybillExpressEntity() != null) {
			dto.setPosSerialNum(waybillDto.getWaybillExpressEntity().getPdaSerial());
			dto.setBatchNo(waybillDto.getWaybillExpressEntity().getBankTradeSerail());
		}

		dto.setInvoiceMark(afhe.getInvoice());
		/**
		 * Dmana-4437
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-02下午19:39
		 */
		dto.setBatchNo(afhe.getTransactionSerialNumber());
		return dto;
	}

	/**
	 * 新增运单货签信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-12 下午1:43:39
	 */
	@Transactional
	public void addLabeledGood(WaybillDto waybillDto,
			WaybillSystemDto systemDto, boolean isPdaPending) {
		/**
		 * 生成货签信息
		 * 
		 * 一、当为PDA补录的运单信息时 1）若PDA补录后，增加了件数： 1
		 * 、新增PDA货件(T_SRV_LABELED_GOOD_PDA)。举例说明新增规则
		 * ：若PDA开单的件数为5，补录时修改件数为8。查询当前的PDA货件的最大流水号，应当为0005，需要添加货件0006-0008。 2、
		 * 将新增的PDA货件插入中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中。
		 * 3、更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),累加上增加的件数。
		 * 4、更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，累加上增加的件数。
		 * 
		 * 2）若PDA补录后，减少了件数： 1、
		 * 删除PDA货件(T_SRV_LABELED_GOOD_PDA)。举例说明新增规则：若PDA开单的件数为8
		 * ，补录时修改件数为5。查询当前的PDA货件的最大流水号，应当为0008，需要减少货件0006-0008。
		 * 2、将删除的PDA货件从中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中删除。
		 * 3、更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),减去件数。
		 * 4、更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，减去件数。
		 * 
		 * 3）在T_SRV_LABELED_GOOD表中直接生成从0001开单的修改后的总件数个流水号
		 * 
		 * 二、当不为PDA补录时 只生成在T_SRV_LABELED_GOOD表中直接生成从0001开单的修改后的总件数个流水号
		 */
		// 尽量不破坏之前开单逻辑，但需要判定是否电子运单提交的数据问题，保证打印出来的货签信息与实际走货的货签信息一致
		labeledGoodService.addLabeledGoods(waybillDto, systemDto);
		// 是否集中接送货部门
		if (FossConstants.YES.equals(waybillDto.getWaybillEntity()
				.getPickupCentralized())) {
			//判断是否异地开单
			WaybillPictureEntity waybillPic = new WaybillPictureEntity();
			waybillPic.setActive(FossConstants.YES);
			waybillPic.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
			waybillPic = waybillPendingService.queryWaybillPictureByEntity(waybillPic);
			//根据集中接送货部门编码查询其对应的外场编码
			BillingGroupTransFerEntity entity = null;
			if(waybillPic!=null && FossConstants.NO.equals(waybillPic.getLocal())
					&& StringUtil.isNotEmpty(waybillPic.getLocalBillGroupCode())){
				//根据集中接送货部门编码查询其对应的外场编码
				 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(waybillPic.getLocalBillGroupCode());
			}else{
				//判断所属部门和本地是否一致 by:352676
				if(waybillPic!=null && !StringUtil.equals(waybillPic.getBelongOrgCode(), waybillPic.getLocalBillGroupCode())){
					 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(waybillPic.getLocalBillGroupCode());
				}else{
				//根据集中接送货部门编码查询其对应的外场编码
				 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(waybillDto.getWaybillEntity().getCreateOrgCode());
				}
			}
			// 根据集中接送货部门编码查询其对应的外场编码
			//BillingGroupTransFerEntity entity1 = billingGroupTransFerService
			//		.queryTransFerListByBillingGroupCode(waybillDto.getWaybillEntity().getCreateOrgCode());
			// 判断对象是否为空

			if (null != entity) {
				// 外场编码
				String currentOrgCode = entity.getTransFerCode();
				// 非空判断
				if (StringUtil.isEmpty(currentOrgCode)) {
					// 若外场编码为空，则抛出异常信息（"集中接送货部门对应的外场编码为空！"）
					throw new HandleTransportPathException(
							HandleTransportPathException.NO_TRANSFERCENTER_CODE);
				}
				waybillDto.getActualFreightEntity().setStartStockOrgCode(
						currentOrgCode);
			} else {
				// 若没有查询到外场编码，则抛出异常信息
				throw new HandleTransportPathException(
						HandleTransportPathException.NO_TRANSFERCENTER_INFO);
			}
		}
		/**
		 * 判断：PDA补录运单是否已生成相关数据,若PDA已在运单表中生成相关记录， 则不生成走货路径、不生成库存信息
		 * 且更新actualFreight表和waybill表
		 */
		if (!isPdaPending) {
			// 货签信息生成成功后生成库存信息
			this.addWaybillStockService(waybillDto);
		}
	}

	/**
	 * 根据运单基本信息（出发部门、出发专线、产品类型、开单时间（数据生成时间））获得预计出发时间
	 * 
	 * 业务规则如下： 1、预计出发时间:我司走货的预计出发时间，适用于运输类型为“精准卡航”及“精准城运”
	 * 2、预计出发时间=预计出发日期,准点出发时间。【格式如：2011-6-28
	 * ,12:00:00】（部门对应的“准点出发时间”，数据取自基础资料：经营-运作基础资料）
	 * 3、如果开单当前时间在准点出发时间前，则预计出发日期=开单日期；否则，预计出发日期=开单日期+1
	 * 4、当运输性质为普货、偏线时，则预计出发日期=开单日期+1
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-19 上午11:47:58
	 */
	@Override
	public Date searchPreLeaveTime(String departDeptCode, String specialLine,
			String productCode, Date createTime) {
		// 准备时间
		int hour = PREPARE_TIME;
		// 获得部门信息
		SaleDepartmentEntity department = saleDepartmentService.querySaleDepartmentByCode(departDeptCode);
		// 对查询出的部门对象进行非空判断
		if (department == null) {
			// 添加异常日志
			LOG.error("searchPreLeaveTime()方法调用saleDepartmentService.querySaleDepartmentByCode异常，departDeptCode:" + departDeptCode);
			// 抛出“调用综合接口查询时效出现异常-未查询到部门信息”的异常信息
			throw new WaybillValidateException(WaybillValidateException.DEPARTMENTENTITY_NULL, new Object[] { departDeptCode });
		}
		// 可出发的时间
		Calendar calendar = Calendar.getInstance();
		// 设置时间
		calendar.setTime(createTime);
		// 获得出发时间
		Date leaveTime = calendar.getTime();
		// 定义是否驻地部门变量
		boolean isStation = department.checkStation();
		// 是否是驻地部门
		if (!isStation) {
			// 给出发时间添加小时
			calendar.add(Calendar.HOUR, hour);
			// 出发时间
			leaveTime = calendar.getTime();
		}

		// 获得班车信息
		List<DepartureStandardDto> departList = null;
		boolean flag = waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, createTime) ;
		if (flag) {// 快递或3.60特惠件
			departList = expresslineService.queryDepartureStandardListBySourceTarget(departDeptCode, specialLine, productCode);

		} else {
			departList = lineService.queryDepartureStandardListBySourceTarget(departDeptCode, specialLine, productCode);
		}
		// 集合值的非空判断
		if (CollectionUtils.isEmpty(departList)) {
			// 添加异常日志
			LOG.error("searchPreLeaveTime()方法调用lineService.queryDepartureStandardListBySourceTarget异常,departDeptCode:"
					+ departDeptCode
					+ ",specialLine:"
					+ specialLine
					+ ",productCode:" + productCode);
			try {
				//添加记录日志 - sangwenhao-272311
				//记录接口交易日志
				PushFoss2EcsWaybillNoLogEntity logEntity = new PushFoss2EcsWaybillNoLogEntity();
				//开单时间
				logEntity.setBillCreateTime(createTime);
				//传递内容
				logEntity.setSendMsg("FOSS开单始发班车、发车标准校验优化--开单部门或出发部门编码(departDeptCode):"+departDeptCode+" ;配载部门(specialLine):"+specialLine+" ;产品编码(productCode):"+productCode+" ;leaveTime:"+leaveTime);
				//接口返回参数异常信息为空表示成功，插入Y，否则N
				logEntity.setSuccessFlag(FossConstants.YES);
				//日志记录时间
				logEntity.setCreateTime(new Date());
				//系统调用关系
				logEntity.setSysRelation("PreLeaveTimeEmpty");
				//
				logEntity.setColumn2(flag ? "Y" : "N");
				LOG.info("FOSS开单始发班车、发车标准校验优化--推送内容："+JSONObject.toJSONString(logEntity));
				pushFoss2EcsWaybillNoLogService.insertWaybillNoLog(logEntity);
			} catch (Exception e) {
				//to do nothing
			}
			return Constants.mergeTime(leaveTime, "0000");
			// 抛出“开单营业部始发班车信息为空”的异常信息
//			throw new WaybillValidateException(
//					WaybillValidateException.DEPARTURESTANDARD_NULL);
		}

		// 定义时间的格式
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		// 出发时间的字符串格式
		String leaveTimeString = sdf.format(leaveTime);
		// 初始化标准时间
		String standardTime = "";
		// 3小时后的时点与下个班时间进行比较，以决定是否要走下班车
		for (int i = 0; i < departList.size(); i++) {
			// 标准时间
			standardTime = departList.get(i).getLeaveTime();
			// 晚于准点出发时间
			if (leaveTimeString.compareTo(standardTime) <= 0) {
				// 拼接出发时间
				return Constants.mergeTime(leaveTime, standardTime);
			}
		}

		// 设置时间
		calendar.setTime(leaveTime);
		// 加一天
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		// 返回预计出发时间
		return Constants.mergeTime(calendar.getTime(), departList.get(0).getLeaveTime());
	}

	/**
	 * 根据预计出发日期、出发外场、到达部门、产品类型、开单时间（数据生成时间）获得承诺自提时间
	 * 
	 * 业务规则如下： 1.预计提货/派送时间适用于运输类型为“精准” 2.提货方式为“自提”时， 若部门对应的“是否当天出发”为“是”，则
	 * 预计提货/派送时间=预计出发日期+到达营业部承诺天数：到达营业部承诺时点； 否则，
	 * 预计提货/派送时间=预计出发日期+到达营业部承诺天数-1：到达营业部承诺时点；
	 * （部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准； “到达营业部承诺天数”和“到达营业部承诺时点”
	 * ，数据取自基础资料：专线-城市承诺时间标准；） 3.提货方式包含为“送货 ”时 若部门对应的“是否当天出发”为“是”，则
	 * 预计提货/派送时间=预计出发日期+到达营业部承诺天数+派送承诺需加天数：派送承诺时点； 否则，
	 * 预计提货/派送时间=预计出发日期+派送承诺需加天数-1：派送承诺时点； （部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准；
	 * “到达营业部承诺天数”、“到达营业部承诺时点”、“派送承诺时点”、“派送承诺需加天数”，数据取自基础资料：专线-城市承诺时间标准；）
	 * 
	 * @author 026123-floss-lifengteng
	 * @date 2012-11-19 上午11:48:34
	 */
	@Override
	public EffectiveDto searchPreSelfPickupTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime) {
		// 时效DTO
		EffectivePlanDto effective = new EffectivePlanDto();
		// 预计出发时间是否为空
		if (preLeaveTime == null) {
			// 若为空则返回空
			return null;
		}

		// 时效对象集合：查询时效明细
		List<EffectivePlanDto> list = null;

		if (waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, createTime)) {
			List<EffectiveExpressPlanDto> tmpList =billCaculateService
					.searchExpressEffectivePlanDetailList(departDeptCode, arriveDetpCode, productCode, createTime);
			if (tmpList != null && tmpList.size() > 0) {
				list = new ArrayList<EffectivePlanDto>();
				for (EffectiveExpressPlanDto dto : tmpList) {
					EffectivePlanDto d2 = new EffectivePlanDto();
					try {
						PropertyUtils.copyProperties(d2, dto);
					} catch (Exception e) {
						LOG.info(e.getMessage());
					}
					list.add(d2);
				}

			}
		} else if (WaybillConstants.HIGHWAYS_REFERRALS.equals(productCode)) {
			List<FreightRouteLineDto> freightRouteList = freightRouteService
					.queryFreightRouteBySourceTarget(departDeptCode, arriveDetpCode, productCode, createTime);
			if (freightRouteList != null && freightRouteList.size() > 0) {
				ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity(
								DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.WEIXIN_PULL_START, FossConstants.ROOT_ORG_CODE);
				if (config != null) {
					// 判断是否开启开关
					if (FossConstants.YES.equals(config.getConfValue())) {
						OuterEffectivePlanEntity entity = outerEffectivePlanService.queryOuterEffectPlanByFieldAndBranch(
								freightRouteList.get(freightRouteList.size() - 1).getSourceCode(), arriveDetpCode, WaybillConstants.TRANS_VEHICLE, createTime);
						EffectivePlanDto d2 = new EffectivePlanDto();
						if (entity != null) {
							list = new ArrayList<EffectivePlanDto>();
							d2.setProductCode(WaybillConstants.HIGHWAYS_REFERRALS);
							d2.setDeptRegionCode(departDeptCode);
							d2.setArrvRegionCode(arriveDetpCode);
							d2.setMaxTime(entity.getMaxTime());
							d2.setMaxTimeUnit(entity.getMinTimeUnit());
							d2.setMinTime(entity.getMinTime());
							d2.setMinTimeUnit(entity.getMinTimeUnit());
							d2.setArriveTime(entity.getArriveOuterBranchTime());
							d2.setAddDay(entity.getAddDay());
							d2.setDeliveryTime(entity.getDeliveryTime());
							d2.setBillDate(createTime);
							d2.setActive(entity.getActive());
							list.add(d2);
						}
					} else {
						list = billCaculateService.searchEffectivePlanDetailList(departDeptCode,
								freightRouteList.get(freightRouteList.size() - 1).getSourceCode(), productCode, createTime);
					}
				} else {
					list = billCaculateService.searchEffectivePlanDetailList(departDeptCode,
							freightRouteList.get(freightRouteList.size() - 1).getSourceCode(), productCode, createTime);
				}
			}

		} else {
			list = billCaculateService.searchEffectivePlanDetailList(departDeptCode, arriveDetpCode, productCode, createTime);

		}

		// 集合非空判断
		if (CollectionUtils.isNotEmpty(list)) {
			// 若有多条记录时，则取第一条数据
			effective = list.get(0);
		} else {
			// 若集合信息为空，则添加异常日志
			LOG.info("searchPreSelfPickupTime()方法调用billCaculateService.searchEffectivePlanDetailList未获取到数据,departDeptCode:"
					+ departDeptCode
					+ ",arriveDetpCode:"
					+ arriveDetpCode
					+ ",productCode:"
					+ productCode
					+ ",createTime:"
					+ createTime);
			// 返回空数据
			return null;
		}

		// 获得日期对象
		Calendar calendar = Calendar.getInstance();
		// 设置出发时间
		calendar.setTime(preLeaveTime);
		// 定义时间格式
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		// 预计出发时间
		String preLeaveFormat = format.format(preLeaveTime);
		// 创建时间
		String createTimeFormat = format.format(createTime);
		/**
		 * 判断是否是当天出发,若是则在预计出发时间的基础上加承诺天数，否则加承诺天数-1 保证对客户的承诺时效
		 */
		if (StringUtil.defaultIfNull(preLeaveFormat).equals(createTimeFormat)) {
			// 当天出发，加承诺天数
			calendar.add(Calendar.DAY_OF_MONTH, effective.getMaxTime());
		} else {
			// 第二天出发，加承诺天数-1
			calendar.add(Calendar.DAY_OF_MONTH, effective.getMaxTime() - 1);
		}

		// 创建时间
		createTime = Constants.mergeTime(calendar.getTime(), StringUtil.defaultIfNull(effective.getArriveTime()));
		// 时效DTO
		EffectiveDto effectiveDto = new EffectiveDto();
		// 设置自提时间
		effectiveDto.setSelfPickupTime(createTime);
		// 设置长短途
		effectiveDto.setLongOrShort(effective.getLongOrShort());
		// 返回封装的对象
		return effectiveDto;
	}

	/**
	 * 根据承诺自提时间、出发外场、到达部门、产品类型、开单时间（数据生成时间）获得承诺派送时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-19 下午3:05:44
	 */
	@Override
	public EffectiveDto searchPreDeliveryTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime) {
		// 定义时间DTO
		EffectiveDto effectiveDto = new EffectiveDto();
		// 自提时间
		effectiveDto = searchPreSelfPickupTime(departDeptCode, arriveDetpCode, productCode, preLeaveTime, createTime);
		// 自提时间不能为空
		if (effectiveDto == null || effectiveDto.getSelfPickupTime() == null) {
			// 若为空返回空对象
			return null;
		}

		// 获得时效
		EffectivePlanDto effective = new EffectivePlanDto();
		// 根据 出发部门、到达部门、产品code、当前时间查询产品时效
		// 时效对象集合：查询时效明细

		List<EffectivePlanDto> list = null;

		if (waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, createTime)) {
			List<EffectiveExpressPlanDto> tmpList = billCaculateService.searchExpressEffectivePlanDetailList(
					departDeptCode, arriveDetpCode, productCode, createTime);
			if (tmpList != null && tmpList.size() > 0) {
				list = new ArrayList<EffectivePlanDto>();
				for (EffectiveExpressPlanDto dto : tmpList) {
					EffectivePlanDto d2 = new EffectivePlanDto();
					try {
						PropertyUtils.copyProperties(d2, dto);
					} catch (Exception e) {
						LOG.info(e.getMessage());
					}
					list.add(d2);
				}

			}
		} else {
			list = billCaculateService.searchEffectivePlanDetailList(departDeptCode, arriveDetpCode, productCode, createTime);
		}

		// List<EffectivePlanDto> list =
		// billCaculateService.searchEffectivePlanDetailList(departDeptCode,
		// arriveDetpCode, productCode, createTime);
		// 集合对象非空值判断
		if (CollectionUtils.isNotEmpty(list)) {
			// 若不为空，则返回第一条记录
			effective = list.get(0);
		} else {
			// 若为空，则返回空值
			return null;
		}
		// 派送承诺需加天数
		Integer addDates = effective.getAddDay();
		// 判断需加天数是否为空
		if (null == addDates) {
			// 设置初始值为0
			addDates = Integer.valueOf(0);
		}
		// 派送承诺时点
		String deliveryTime = effective.getDeliveryTime();
		// 获得日期操作对象
		Calendar calendar = Calendar.getInstance();
		// 设置时间
		calendar.setTime(effectiveDto.getSelfPickupTime());
		// 天数上增加需加天数
		calendar.add(Calendar.DATE, addDates);
		// 计算承诺派送时间
		Date deliveryDate = Constants.mergeTime(calendar.getTime(), deliveryTime);
		// 派送承诺时间
		effectiveDto.setDeliveryDate(deliveryDate);
		// 长短途
		effectiveDto.setLongOrShort(effective.getLongOrShort());
		// 返回时效对象
		return effectiveDto;
	}

	/**
	 * 判断产品时效是否存在
	 * 
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	@Override
	public boolean identityEffectivePlan(String departDeptCode,
			String arriveDetpCode, String productCode, Date createTime) {
		List<EffectivePlanDto> list = billCaculateService.searchEffectivePlanDetailList(
				departDeptCode, arriveDetpCode, productCode, createTime);
		if (list != null && list.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 集中开单查开单组所属中转场的默认出发部门
	 * 
	 * @author WangQianJin
	 * @date 2013-6-26下午3:55:19
	 */
	@Override
	public SaleDepartmentEntity queryPickupCentralizedDeptCode(
			String billingGroupOrgCode) {
		SaleDepartmentEntity pickupCentralizedDept = null;
		String stransCenterCode = this.queryTransCenterByBillingGroupCode(billingGroupOrgCode);
		if (!StringUtils.isEmpty(stransCenterCode)) {
			String salesDeptCode = this.queryDefaultSalesDeptByTransCenter(stransCenterCode);
			if (!StringUtils.isEmpty(salesDeptCode)) {
				pickupCentralizedDept = saleDepartmentService.querySaleDepartmentByCode(salesDeptCode);
			}
		}
		return pickupCentralizedDept;
	}

	/**
	 * 集中开单查开单组通过Code、时间查询所属中转场的默认出发部门
	 * 
	 * @author foss-105888-Zhangxingwang
	 * @date 2013-7-31 21:23:34
	 */
	@Override
	public SaleDepartmentEntity queryPickupCentralizedDeptCodeAndBillTime(
			String billingGroupOrgCode, Date billTime) {
		// 根据集中开单组Code与对应的开单时间查询
		SaleDepartmentEntity deliverDepartment = null;
		String stransCenterCode = null;
		BillingGroupTransFerEntity entity = billingGroupTransFerService.queryTransferCenterByBillingGroupCode(
				billingGroupOrgCode, billTime);
		if (entity != null) {
			stransCenterCode = entity.getTransFerCode();
		} else {
			stransCenterCode = "";
		}
		// stransCenterCode =
		// this.queryTransCenterByBillingGroupCode(billingGroupOrgCode);
		// 根据外场编码查询驻地可出发部门Code
		if (!StringUtils.isEmpty(stransCenterCode)) {
			// 根据外场部门编码，查询该外场的驻地可出发营业部对象
			deliverDepartment = orgAdministrativeInfoComplexService.queryStationLeaveOrgByOutfieldCode(stransCenterCode);
		}
		return deliverDepartment;
	}

	/**
	 * 
	 * <p>
	 * 运单与订单重绑定，该接口提供给接送货2组
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-28 下午4:10:18
	 * @param oldWaybillNo
	 * @param newWaybillNo
	 * @param orderNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#reBindOrder(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultDto reBindOrder(String oldWaybillNo, String newWaybillNo,
			String orderNo) {
		// 初始化结果集合代码
		String code = "1";
		// 初始化返回消息
		String msg = "";
		// 定义原运单基本信息对象
		WaybillEntity oldWaybill = null;
		// 运单号非空判断
		if (oldWaybillNo != null && !oldWaybillNo.equals("")) {
			// 根据运单号查询运单基本信息
			oldWaybill = waybillDao.queryWaybillByNo(oldWaybillNo);
			// 非空判断
			if (oldWaybill != null) {
				// 订单号
				oldWaybill.setOrderNo("");
				// 根据运单基本信息
				waybillDao.updateWaybill(oldWaybill);
			} else {
				// 没有成功则返回0
				code = ZEROSTR;
				// 返回没有成功的原因
				msg = "绑定关系更改失败，未找到传入旧运单号的有效记录。";
			}
		}
		// 定义新的运单基本信息对象
		WaybillEntity newWaybill = waybillDao.queryWaybillByNo(newWaybillNo);
		// 非空判断
		if (newWaybill != null) {
			// 订单号
			newWaybill.setOrderNo(orderNo);
			// 根据订单号
			waybillDao.updateWaybill(newWaybill);
		} else {
			// 更新失败，设置为0
			code = ZEROSTR;
			// 更新失败，返回失败原因
			msg = "绑定关系更改失败，未找到传入新运单号的有效记录。";
		}

		// 定义结果对象信息
		ResultDto res = new ResultDto();
		// 编码
		res.setCode(code);
		// 设置返回消息
		res.setMsg(msg);
		// 返回对象
		return res;
	}

	/**
	 * 
	 * <p>
	 * 更新运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param waybill
	 * @return int
	 */
	@Override
	public int updateWaybillEntity(WaybillEntity waybill) {
		// 更新运单基本信息
		return waybillDao.updateWaybill(waybill);
	}

	/**
	 * 
	 * <p>
	 * 根据运单Id查询运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-30
	 * @param waybillId
	 * @return WaybillEntity
	 */
	@Override
	public WaybillDto getWaybillDtoById(String waybillId) {
		// 定义运单DTO对象
		WaybillDto waybillDto = null;
		// 运单基本信息
		WaybillEntity waybillEntity = waybillDao.getWaybillEntityById(StringUtil.defaultIfNull(waybillId));
		// 判断运单基本信息是否为空
		if (waybillEntity == null) {
			// 若为空则返回空的运单DTO
			return null;
		} else {
			// 不为空，则实例化运单DTO
			waybillDto = new WaybillDto();
		}
		
		//判断是否为合伙运单
		PartnersWaybillEntity partnersWaybillEntity = partnersWaybillService.queryPartnerWaybillEntityByWaybillId(StringUtil.defaultIfNull(waybillEntity.getId()));
		if(partnersWaybillEntity!=null){
			waybillDto.setPartnersWaybillEntity(partnersWaybillEntity);
		}
		
		// 运单号
		String waybillNo = waybillEntity.getWaybillNo();
		// 运单基本信息
		waybillDto.setWaybillEntity(waybillEntity);

		// 查询条件封装对象实例化
		LastWaybillRfcQueryDto queryDto = new LastWaybillRfcQueryDto();
		// 运单号
		queryDto.setWaybillNo(waybillNo);
		// 创建时间
		queryDto.setCreateTime(waybillEntity.getCreateTime());
		queryDto.setModifyTime(waybillEntity.getModifyTime());

		// 有效状态
		queryDto.setActive(waybillEntity.getActive());
		// 运单费用明细
		waybillDto.setWaybillChargeDtlEntity(waybillChargeDtlService
				.queryNewChargeDtlEntityByNO(queryDto));
		// 运单折扣明细
		waybillDto.setWaybillDisDtlEntity(waybillDisDtlService
				.queryNewDisDtlEntityByNo(queryDto));
		// 运单付款明细
		waybillDto.setWaybillPaymentEntity(waybillPaymentService
				.queryNewWaybillPaymentEntityByNo(queryDto));
		// 代打木架
		waybillDto.setWoodenRequirementsEntity(woodenRequirementsService.queryNewWoodenRequirements(queryDto));
		// 　快递信息
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {
			waybillDto.setWaybillExpressEntity(waybillExpressService.queryWaybillExpressByWaybillId(waybillId));

		}
		// 返回运单DTO
		return waybillDto;
	}

	/**
	 * 删除运单对象
	 */
	@Override
	public int deleteWaybillEntityById(String waybillId) {
		// 根据运单ID来删除运单基本信息
		return waybillDao.deleteWaybillEntityById(waybillId);
	}

	/**
	 * 追加运单完整信息（包括基本信息、付款信息、费用信息、打木架信息、折扣信息）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午10:00:12
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#appendTempWaybillAfterChanged(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto,
	 *      java.util.Date)
	 */
	@Override
	public void appendTempWaybillAfterChanged(WaybillDto waybillDto,
			Date currentDate) {
		// 校验数据合法性
		checkWaybillData(waybillDto);
		// 生成运单完整信息
		appendTempWaybill(waybillDto, currentDate);
		// zxy 20131220 DEFECT-987 start 修改:注销此段,否则会未审核通过就修改了发票标记.把此功能放到受理通过才更新
		// 更改发票标记
		// updateActualFreightEntity(waybillDto);
		// zxy 20131220 DEFECT-987 end 修改:注销此段,否则会未审核通过就修改了发票标记.把此功能放到受理通过才更新
	}

	// 更改发票标记
	public void updateActualFreightEntity(WaybillDto waybillDto) {
		waybillDao.updateActualFreightEntity(waybillDto);

	}

	/**
	 * 
	 * 追加运单完整信息（包括基本信息、付款信息、费用信息、打木架信息、折扣信息）
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-3 下午4:04:51
	 */
	private void appendTempWaybill(WaybillDto waybillDto, Date currentDate) {
		// 生成全整运单信息
		try {
			// 运单信息
			WaybillEntity waybill = waybillDto.getWaybillEntity();
			// 付款信息
			List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();
			// 费用信息
			List<WaybillChargeDtlEntity> chargeDetail = waybillDto.getWaybillChargeDtlEntity();
			// 折扣信息
			List<WaybillDisDtlEntity> discoutDetail = waybillDto.getWaybillDisDtlEntity();
			// 打木架信息
			WoodenRequirementsEntity wooden = waybillDto.getWoodenRequirementsEntity();

			// 设置统一的创建人、创建时间、更新人、更新时间
			WaybillSystemDto systemDto = new WaybillSystemDto();
			// 创建时间
			systemDto.setCreateTime(currentDate);
			// 修改时间
			systemDto.setModifyTime(currentDate);
			// 开单时间
			systemDto.setBillTime(waybill.getBillTime());

			// 追加运单基础信息
			this.appendWaybillEntityAfterChange(waybill, systemDto);
			
			try {
				//到达部门是否是合伙部门
				SaleDepartmentEntity newSaleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCodeCache(StringUtils.isNotBlank(waybillDto.getWaybillEntity().getCustomerPickupOrgCode())?waybillDto.getWaybillEntity().getCustomerPickupOrgCode():waybillDto.getWaybillEntity().getLastLoadOrgCode());
				//追加合伙人运单信息
				if(waybillDto.getActualFreightEntity() != null 
					&& (FossConstants.YES.equals(waybillDto.getActualFreightEntity().getPartnerBillingLogo()) 
						|| (newSaleDepartmentEntity !=null && FossConstants.YES.equals(newSaleDepartmentEntity.getIsLeagueSaleDept())))){
					PartnersWaybillEntity partnersWaybillEntity = this.getPartnersWaybillEntity(waybillDto, systemDto);
					partnersWaybillEntity.setActive(FossConstants.INACTIVE);
					//校验合伙人异常信息  中止和作废折前表信息都为0，不做判断 xingjun 20161212
					if(!WaybillRfcConstants.ABORT.equals(partnersWaybillEntity.getChangeType()) &&
							!WaybillRfcConstants.INVALID.equals(partnersWaybillEntity.getChangeType())){
						checkPartnerWaybillFee(waybillDto.getWaybillEntity(), partnersWaybillEntity);
					}
					//foss本地保存记录信息(暂时注释)
					partnersWaybillService.addPartnersWaybillEntity(partnersWaybillEntity);
				}
			} catch (Exception e) {
				// 添加异常信息
				LOG.error("Excepton", e);
				// 抛出异常日志信息“运单信息生成失败”
				throw new BusinessException("更改单 合伙人信息 获取实体并添加信息 失败 \n" + e.getMessage());
			}
			
			
			
			// 判断运单折扣明细是否为空
			if (CollectionUtils.isNotEmpty(discoutDetail)) {
				if(FossConstants.YES.equals(waybillDto.getActualFreightEntity().getPartnerBillingLogo()) 
						&&  StringUtils.isEmpty(waybill.getPromotionsCode())){
					List<WaybillDisDtlEntity> disDtlEntity = new ArrayList<WaybillDisDtlEntity>();
					for(int i=0;i<discoutDetail.size();i++){
						if(!StringUtils.equals(discoutDetail.get(i).getTypeName(), "优惠券")){
							disDtlEntity.add(discoutDetail.get(i));
						}
					}
					if(CollectionUtils.isNotEmpty(disDtlEntity)){
						// 追加更改单起草时WaybillDisDtlEntity实休数据
						waybillDisDtlService.appendWaybillDisDtlEntityBatchAfterChange(disDtlEntity, systemDto);
					}
				}else{			
					// 追加更改单起草时WaybillDisDtlEntity实休数据
					waybillDisDtlService.appendWaybillDisDtlEntityBatchAfterChange(discoutDetail, systemDto);
				}
			}

			// 判断费用明细是否为空
			if (CollectionUtils.isNotEmpty(chargeDetail)) {
				// 更改单起草后追加费用明细实体
				waybillChargeDtlService.appendWaybillChargeDtlEntityBatchAfterChange(chargeDetail, systemDto);
			}

			// 判断付款明细是否为空
			if (CollectionUtils.isNotEmpty(paymentList)) {
				// 更改单起草后追加付款明细实体
				waybillPaymentService.appendWaybillPaymentEntityBatchAfterChange(paymentList, systemDto);
			}

			// 判断打木架信息是否为空
			if (wooden != null) {
				// 更改单起草后追加付款明细实体
				woodenRequirementsService.appendWoodenRequirementsAfterChange(wooden, systemDto);
			}

			// 捕捉异常信息
		} catch (BusinessException e) {
			// 添加异常信息
			LOG.error("Excepton", e);
			// 抛出异常日志信息“运单信息生成失败”
			throw new WaybillSubmitException(WaybillSubmitException.ADDWAYBILLINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
		}
	}

	/**
	 * 
	 * 追加运单基础信息
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-3 下午4:05:07
	 */
	private void appendWaybillEntityAfterChange(WaybillEntity waybillEntity,
			WaybillSystemDto systemDto) {
		// 设置创建时间、修改时间
		waybillEntity.setCreateTime(systemDto.getCreateTime());
		// 修改时间
		waybillEntity.setModifyTime(systemDto.getModifyTime());
		// 更改暂存
		waybillEntity
				.setPendingType(WaybillConstants.WAYBILL_STATUS_RFC_PENDING);
		// 运输类型
		waybillEntity.setTransportType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillEntity.getProductCode()));
		// 设置是否有效
		waybillEntity.setActive(FossConstants.INACTIVE);

		// 创建运单
		waybillDao.addWaybillEntity(waybillEntity);
	}

	/**
	 * 得到综合的走货路径实体
	 * 
	 * @author foss-jiangfei
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */
	@Override
	public FreightRouteEntity getRouteLineInfo(String waybillNo,
			String startOrg, String destOrg) {
		// 根据运单号查询运单基本信息实体对象
		WaybillEntity waybillBean = waybillDao.queryWaybillByNo(waybillNo);

		// 查询对象为空
		if (waybillBean == null) {
			// 抛出异常信息“传入的运单号不存在有效记录”
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILLBEAN_NULL);
		}
		// 通过出发部门，到达部门，产品类型选取一条走货路径
		return freightRouteService.queryFreightRouteBySourceTarget(startOrg, destOrg, waybillBean.getProductCode());
	}

	/**
	 * 得到综合的走货路径 经过外场部门编码列表
	 * 
	 * @author foss-jiangfei
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */
	@Override
	public List<String> getRouteLineCodeInfo(String waybillNo, String startOrg,
			String destOrg) {
		// 走货路径实体
		FreightRouteEntity freightRoute = this.getRouteLineInfo(waybillNo,
				startOrg, destOrg);
		// 对象非空判断
		if (freightRoute != null) {
			// 从走货路径的虚拟编码 查询走货路径线路的集合 就是所有外场集合 A-B-C 这个结果包含了 出发部门到达部门
			List<FreightRouteLineEntity> freightRouteLinelist = freightRouteLineService
					.queryFreightRouteLineListByFreightRoute(freightRoute.getVirtualCode());

			// 得到路径 编码集合LIST A-C C-D D-B 得到这种格式
			List<String> addressInfoList = new ArrayList<String>();
			// 拼接走货路径
			for (FreightRouteLineEntity f : freightRouteLinelist) {
				// 增加路径信息
				addressInfoList.add(f.getOrginalOrganizationCode() + "-" + f.getDestinationOrganizationCode());
			}

			// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的 外场集合 这个是有顺序的
			return removeDuplicateRoute(addressInfoList);
		} else {
			return null;
		}
	}

	/**
	 * 得到中转的走货路径线段列表
	 * 
	 * @author foss-jiangfei
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */
	@Override
	public List<RouteLineInfoDto> getRouteDetailInfo(String waybillNo,
			String serialNos) {
		// 走货路径明细
		// List<PathDetailEntity> pathDetailList =
		// calculateTransportPathService.queryTotalPath(waybillNo, serialNos);
		List<PathDetailEntity> pathDetailList = calculateTransportPathService
				.queryTotalPathForPrintLabel(waybillNo, serialNos);

		// List<PathDetailEntity> pathDetailListTemp = new
		// ArrayList<PathDetailEntity>();
		// 解决有时候运单号存在，流水号不存在的情况
		if (pathDetailList == null) {
			// 因为只有一段走货路径，所以需要我们帮他生成走货路径
			pathDetailList = calculateTransportPathService.queryTotalPathForPrintLabel(waybillNo, null);
		}
		// 得到中转的走货路径线段列表
		List<RouteLineInfoDto> routeLineInfoList = new ArrayList<RouteLineInfoDto>();
		// 获得线段列表
		for (PathDetailEntity path : pathDetailList) {
			// DTO接收从中转获取的走货路径 PathDetailEntity
			RouteLineInfoDto routeLineDto = new RouteLineInfoDto();
			// 出发部门
			routeLineDto.setOrigOrgCode(path.getOrigOrgCode());
			// 下一到达部门
			routeLineDto.setObjectiveOrgCode(path.getObjectiveOrgCode());
			// 流水号
			routeLineDto.setGoodsNo(serialNos);
			// 运单号
			routeLineDto.setWaybillNo(path.getWaybillNo());
			// 加入集合
			routeLineInfoList.add(routeLineDto);
		}
		return routeLineInfoList;

	}

	/**
	 * 得到中转的走货路径 有顺序的途径外场 包含始发到达部门编码集合
	 * 
	 * @author foss-jiangfei
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */
	// @Override
	public List<String> getRouteDetailCodeInfo(String waybillNo,
			String serialNos) {
		// 走货路径明细
		PathDetailEntity pathDetail = new PathDetailEntity();
		// 运单号
		pathDetail.setWaybillNo(waybillNo);
		// 流水号
		pathDetail.setGoodsNo(serialNos);
		// 对象集合
		List<PathDetailEntity> pathDetailList = pathDetailDao.queryPathDetailList(pathDetail);

		// 得到路径 编码集合LIST A-C C-D D-B 得到这种格式
		List<String> addressInfoList = new ArrayList<String>();
		// 拼接线路
		for (int i = 0; i < pathDetailList.size(); i++) {
			addressInfoList.add(pathDetailList.get(i).getOrigOrgCode() + "-" + pathDetailList.get(i).getObjectiveOrgCode());
		}
		// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的 外场集合 这个是有顺序的
		// list还包括外场和出发 到达营业部
		List<String> changePlaceInfoList = removeDuplicateRoute(addressInfoList);
		return changePlaceInfoList;
	}

	/**
	 * <p>
	 * 从走货路径A-B B-C 结构的路径列表集合, 取出单个外场列表 删除重复的
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-8 下午2:15:21
	 * @param addressInfoList
	 * @see
	 */
	public List<String> removeDuplicateRoute(List<String> routeList) {
		// 定义集合对象
		List<String> temp = new ArrayList<String>();
		// 添加线段
		for (int i = 0; i < routeList.size(); i++) {
			// 连接走货线路
			temp.add(routeList.get(i).substring(0,
					routeList.get(i).indexOf("-")));
			if (routeList.size() == (i + 1)) {
				temp.add(routeList.get(i).substring(routeList.get(i).indexOf("-") + 1));
			}
		}
		return temp;
	}

	/**
	 * 检查是否有此外请车编号</br>
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午4:48:45
	 * @param inviteNo
	 *            外请车编号
	 * @param deptCode
	 * @return 外请车申请状态
	 */
	public String checkInviteNoIsExists(String inviteNo, String deptCode) {
		// 捕捉异常信息
		try {
			// 检查是否有此外请车编号
			inviteVehicleService.checkInviteNoIsExists(inviteNo, deptCode);
		} catch (InviteVehicleException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出异常信息
			throw new WaybillSubmitException(WaybillSubmitException.QUERYINVITENO,
					new Object[] { messageBundle.getMessage(getArgumentMessage(e.getErrorArguments())) });
		}
		return WaybillConstants.SUCCESS;
	}

	/**
	 * 
	 * 获取异常信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-24 上午09:39:41
	 * @param argumentMessage
	 * @return
	 */
	public String getArgumentMessage(Object[] argumentMessage) {
		// 异常对象是否为空
		if (argumentMessage == null) {
			// 空字符串
			return StringUtil.EMPTY_STRING;
		}

		// 字符串缓冲对象
		StringBuilder stringBuilder = new StringBuilder();

		// 拼接字符串
		for (Object object : argumentMessage) {
			stringBuilder.append(object.toString());
		}
		return stringBuilder.toString();
	}

	/**
	 * 根据外请车编号查询 请车价格 </br> 约车申请不存在 抛出异常
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午2:32:17
	 * @param inviteNo
	 *            外请车编号
	 * @return 外请车价格
	 */
	public InviteVehicleDto queryInviteCostByInviteNo(String inviteNo) {
		// 外请车DTO
		InviteVehicleDto dto = null;
		// 捕捉异常信息
		try {
			// 查询外请车的对象
			dto = inviteVehicleService.queryInviteVehicleByInviteNo(inviteNo);
		} catch (InviteVehicleException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出异常信息
			throw new WaybillSubmitException(WaybillSubmitException.QUERYINVITECOST,
					new Object[] { messageBundle.getMessage(getArgumentMessage(e.getErrorArguments())) });
		}
		return dto;
	}

	/**
	 * 
	 * 查询运单
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-11-26 下午08:37:46
	 */
	@Override
	public List<WaybillEntity> queryWaybill(WaybillDto waybillDto) {
		// 查询运单基本信息集合
		return waybillDao.queryWaybill(waybillDto);
	}

	/**
	 * 
	 * 查询运单
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-11-26 下午08:37:46
	 */
	@Override
	public List<WaybillEntity> queryWaybillNoExpress(WaybillDto waybillDto) {
		// 查询运单基本信息集合
		return waybillDao.queryWaybillNoExpress(waybillDto);
	}

	/**
	 * 根据运单号查询运单部分数据（查询付款方式，到付金额，外发代理和币种, 运输性质,是否整车运单,提货方式,总费用,保价声明价值,代收货款,
	 * 货物总件数,最终配载部门，订单编号...)
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 下午2:50:00
	 */
	@Override
	public WaybillEntity queryPartWaybillByNo(String waybillNo) {
		// 查询运单部分数据
		return waybillDao.queryPartWaybillByNo(waybillNo);
	}

	/**
	 * 
	 * <p>
	 * 调用综合接口查询走货路径
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-17 下午6:16:26
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @param time
	 * @return
	 * @see
	 */
	@Override
	public FreightRouteDto queryFreightRouteBySourceTarget(String sourceCode,
			String targetCode, String productCode, Date time) {
		// 走货线路
		FreightRouteDto freightRouteDto = new FreightRouteDto();
		// 捕捉异常
		try {
			String freightRouteVitualCode = null;
			// 设置线路集合
			freightRouteDto.setFreightRouteLinelist(freightRouteService
					.query4Billing(sourceCode, targetCode, productCode));
			if (freightRouteDto.getFreightRouteLinelist() != null
					&& !freightRouteDto.getFreightRouteLinelist().isEmpty()) {
				for (FreightRouteLineDto f : freightRouteDto.getFreightRouteLinelist()) {
					if (f.getFreightRouteVirtualCode() != null) {
						freightRouteVitualCode = f.getFreightRouteVirtualCode();
						break;
					}
				}
			}
			if (freightRouteVitualCode != null) {
				// 设置线路实体
				freightRouteDto.setFreightRouteEntity(freightRouteService.queryFreightRouteByVirtualCode(freightRouteVitualCode));
			} else {
				// 设置线路实体
				freightRouteDto.setFreightRouteEntity(freightRouteService.queryFreightRouteBySourceTarget(sourceCode, targetCode, productCode));
			}
		} catch (FreightRouteException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出“获取走货路径失败”的异常信息
			throw new BaseInfoInvokeException(BaseInfoInvokeException.FREIGHTROUTE_NULL);
		}
		return freightRouteDto;
	}

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
	@Override
	public OrgAdministrativeInfoEntity queryOrgInfoByCode(String code) {
		// 根据组织编码查询组织信息
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
	}

	/**
	 * 
	 * 获取库区信息
	 * 
	 * @author foss-sunrui
	 * @date 2013-4-2 下午1:53:26
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryGoodsAreaByArriveRegionCode(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public GoodsAreaEntity queryGoodsAreaByArriveRegionCode(String sourceCode,
			String targetCode, String productCode) {
		return goodsAreaService.queryGoodsAreaByArriveRegionCode(sourceCode, targetCode, productCode);
	}

	/**
	 * 根据集中开单组编码查所属外场组织编码
	 * 
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public String queryTransCenterByBillingGroupCode(String billingGroupCode) {
		BillingGroupTransFerEntity entity = billingGroupTransFerService
				.queryTransFerListByBillingGroupCode(billingGroupCode);
		if (entity != null) {
			return entity.getTransFerCode();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 根据银行编码查询银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:43:42
	 */
	@Override
	public BankEntity queryBankByCode(String code) {
		return bankService.queryBankInfoByCode(code);
	}

	/**
	 * 弃货查询
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-15
	 * @param condition
	 * @param currentInfo
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryAbandonedGoodsDtoList(com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(
			AbandonedGoodsCondition condition, CurrentInfo currentInfo) {
		// 定义开单时间
		Date start = condition.getBeginDate();
		// 定义结束时间
		Date end = condition.getEndDate();
		// 状态为有效的运单
		condition.setActive(FossConstants.YES);
		// 1、检查申请日期是否为空
		if (start == null || end == null) {
			// 以异常的方式抛出消息，以达到中止程序运行目的（在前台对该异常进行捕捉）
			throw new WaybillRfcChangeException(WaybillRfcChangeException.APPLYDATE_NULL);
		}
		// 2、检查两日期值的逻辑-申请日期的起始日期≤截止日期
		if (start.compareTo(end) > 0) {
			// 以异常的方式抛出消息，以达到中止程序运行目的（在前台对该异常进行捕捉）
			throw new WaybillRfcChangeException(WaybillRfcChangeException.ENDTIME_OVER);
		}
		// 3、检查两日期值相差天数
		Long days = DateUtils.getTimeDiff(start, end);
		// 判断天数是否超过一个月
		if (days > NumberConstants.NUMBER_30) {
			// 以异常的方式抛出消息，以达到中止程序运行目的（在前台对该异常进行捕捉）
			throw new WaybillRfcChangeException(WaybillRfcChangeException.ThirtyDays_OVER);
		}
		return waybillDao.queryAbandonedGoodsDtoList(condition);
	}

	/**
	 * 
	 * <p>
	 * 通过代理网点编码查询代理网点信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-18 上午11:51:51
	 * @param agencyBranchCode
	 * @return
	 * @see
	 */
	@Override
	public AgencyBranchOrCompanyDto queryAgencyBranchCompanyInfo(String agencyBranchCode) {
		// 查询代理部门信息
		return vehicleAgencyDeptService.queryAgencyBranchCompanyInfo(agencyBranchCode);
	}

	/**
	 * 更新弃货状态
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param oldWaybillNo
	 * @param newWaybillNo
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#updateAbandonedGoods(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public void updateAbandonedGoods(List<String> oldWaybillNo, String newWaybillNo) {
		// 运单号非空判断
		if (CollectionUtils.isEmpty(oldWaybillNo)) {
			// 捕捉异常信息
			throw new WaybillValidateException(WaybillValidateException.WAYBILLNOLIST_NULL);
		}
		// 运单号非空判断
		if (StringUtils.isEmpty(newWaybillNo)) {
			// 捕捉异常信息
			throw new WaybillValidateException(WaybillValidateException.ABANDONEDWAYBILLNO_NULL);
		}

		// 键值对象
		Map<String, Object> args = new HashMap<String, Object>();
		// 定义消息
		String notes = "该运单已弃货处理成功，内部带货至大区办公室，运单号为" + newWaybillNo;
		// 弃货的运单号
		args.put("oldWaybillNo", oldWaybillNo);
		// 弃货的状态
		args.put("status",
				AbandGoodsApplicationConstants.ABANDGOODS_STATUS_DEALED);
		// 弃货的备注
		args.put("notes", notes);
		// 将active 置为N 将modify_time 设置为当前时间
		args.put("active", FossConstants.NO);
		args.put("modifyTime", new Date());
		// 捕捉异常
		try {
			// 更新弃货信息
			waybillDao.updateAbandonedGoods(args);
		} catch (BusinessException e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出异常信息
			throw new WaybillImportException(WaybillImportException.UPDATEABANDONEDWAYBILL_FAIL
							+ new Object[] { messageBundle.getMessage(e.getErrorCode() + "\n" + e.getMessage()) });
		}
	}

	/**
	 * 查询当前部门所在的大区
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param currentInfo
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.service.
	 *       IWaybillManagerService
	 *       #queryBigRegionOfCurrDept(com.deppon.foss.module
	 *       .frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public OrgAdministrativeInfoEntity queryBigRegionOfCurrDept(CurrentInfo currentInfo) {
		// 定义大区集合
		List<String> bizTypes = new ArrayList<String>();
		// 定义组织对象
		OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
		// 大区
		bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
		// 对象当前组织编码非空进行判断
		if (currentInfo.getCurrentDeptCode() != null) {
			// 查询组织对象
			org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode(), bizTypes);// 获得区域
		}
		return org;
	}

	/**
	 * 运单明细查询
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param waybillNoList
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillDetail(java.util.List)
	 */
	@Override
	public List<CrmWaybillServiceDto> queryWaybillDetail(
			List<String> waybillNoList) {
		// CRM系统查询运单信息Dto
		List<CrmWaybillServiceDto> resultList = null;
		// 非空判断
		if (waybillNoList != null && !waybillNoList.isEmpty()) {
			// 根据运单号进行查询
			resultList = waybillDao.queryWaybillDetail(waybillNoList);
		}
		return resultList;
	}

	@Override
	public String queryDepartureDeptNumber(String waybillNo) {
		// TODO Auto-generated method stub
		if (waybillNo != null) {
			return waybillDao.queryDepartureDeptNumber(waybillNo);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 修改货物属性
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午11:42:37
	 * @param goodAttr
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#updateGoodsAttr(com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto)
	 */
	@Override
	public ResultDto updateGoodsAttr(PDAGoodsAttrDto goodAttr) {
		// 处理结果代码
		String code = "1";
		// 返回的消息
		String msg = "";
		// PDA上传重量体积对象非空判断
		if (goodAttr != null) {
			// 判断运单是否已经补录
			// zxy 20140213 MANA-410 start 修改：查询此单号是否补录
			List<String> pendingTypesLst = new ArrayList<String>();
			pendingTypesLst.add(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE);
			WaybillEntity bill = waybillDao.queryWaybillIsPcOrPdaOrRfcActive(goodAttr.getWaybillNo(), pendingTypesLst);
			// zxy 20140213 MANA-410 end 修改：查询此单号是否补录
			if (bill != null) {
				return null;
			}
			int temp = 0;
			// 运单基本信息
			WaybillEntity waybill = waybillDao.queryWaybillByNo(goodAttr
					.getWaybillNo());

			if (waybill != null) { // zxy 20140213 MANA-410 修改：增加waybill空校验
				boolean isUpdate = false;
				// zxy 20140213 MANA-410 start 修改:当重量=0时更新重量，体积=0更新体积
				// 总重量
				if (waybill.getGoodsWeightTotal() == null
						|| waybill.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
					waybill.setGoodsWeightTotal(goodAttr.getWeight());
					isUpdate = true;
				}
				// 总体积
				if (waybill.getGoodsVolumeTotal() == null
						|| waybill.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
					waybill.setGoodsVolumeTotal(goodAttr.getVolume());
					isUpdate = true;
				}
				// zxy 20140213 MANA-410 end 修改:当重量=0时更新重量，体积=0更新体积
				if (isUpdate) {
					// 修改人工号
					waybill.setModifyUserCode(goodAttr.getOperator());
					// 修改人所属组织
					waybill.setModifyOrgCode(goodAttr.getOperaterOrg());
					// 修改时间
					// 成功更新数据的条数
					temp = waybillDao.updateWaybill(waybill);
				}
			}

			// 待补录运单基本信息
			WaybillPendingEntity waybillPending = waybillPendingService
					.queryPendingByNo(goodAttr.getWaybillNo());
			if (waybillPending != null) {
				WaybillPendingEntity pending = new WaybillPendingEntity();
				// zxy 20140213 MANA-410 start 修改:当重量=0时更新重量，体积=0更新体积
				boolean isUpdate = false;
				if (waybillPending.getGoodsWeightTotal() == null
						|| waybillPending.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
					pending.setGoodsWeightTotal(goodAttr.getWeight());
					isUpdate = true;
				}
				if (waybillPending.getGoodsVolumeTotal() == null
						|| waybillPending.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
					pending.setGoodsVolumeTotal(goodAttr.getVolume());
					isUpdate = true;
				}
				// zxy 20140213 MANA-410 end 修改:当重量=0时更新重量，体积=0更新体积
				if (isUpdate) {
					pending.setModifyUserCode(goodAttr.getOperator());
					pending.setModifyOrgCode(goodAttr.getOperaterOrg());
					pending.setId(waybillPending.getId());
					temp = waybillPendingService.updateByPrimaryKeySelective(pending);
				}
				// zxy 20140211 MANA-1044 start 新增:PDA修改了重量体积需要调用中转更新相关信息
				if (!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillPending.getProductCode(), waybillPending.getBillTime())) {
					BigDecimal preVolume = waybillPending.getGoodsVolumeTotal() == null ? BigDecimal.ZERO : waybillPending.getGoodsVolumeTotal();
					BigDecimal preWeight = waybillPending.getGoodsWeightTotal() == null ? BigDecimal.ZERO : waybillPending.getGoodsWeightTotal();
					BigDecimal curVolume = goodAttr.getVolume() == null ? BigDecimal.ZERO : goodAttr.getVolume();
					BigDecimal curWeight = goodAttr.getWeight() == null ? BigDecimal.ZERO : goodAttr.getWeight();
					MakeUpWaybillEntity makeUpWaybillEntity = new MakeUpWaybillEntity();
					// 只要重量和体积有一个修改了就传给中转进行刷新
					if ((preVolume == null || preVolume.compareTo(curVolume) != 0)
							|| (preWeight == null || preWeight.compareTo(curWeight) != 0)) {
						makeUpWaybillEntity.setVolume(curVolume);
						makeUpWaybillEntity.setWeight(curWeight);
						makeUpWaybillEntity.setWaybillNo(goodAttr
								.getWaybillNo());
						makeUpWaybillEntity.setMakeUpTime(new Date());
						makeUpWaybillEntity.setQuantity(new BigDecimal(
								waybillPending.getGoodsQtyTotal()));
						// 通知中转重量体积变更，将变更信息放入中转跑批表
						makeUpWaybillService
								.addWaybillInfoForTfr(makeUpWaybillEntity);
					}
				}
				// zxy 20140211 MANA-1044 end 新增:PDA修改了重量体积需要调用中转更新相关信息
			}
			// 若为0，则没有更新成功
			if (temp == 0) {
				// 设置更新状态为0
				code = ZEROSTR;
				// 返回失败原因
				msg = "修改货物属性失败";
			}
		} else {
			// 设置更新状态为0
			code = ZEROSTR;
			// 返回失败原因
			msg = "传入的对象为空";
		}

		// 定义返回对象
		ResultDto res = new ResultDto();
		// 设置编码
		res.setCode(code);
		// 消息
		res.setMsg(msg);
		return res;
	}

	/**
	 * 
	 * <p>
	 * 查询出发营业部的默认外场
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-21 上午9:45:47
	 * @param saleCode
	 * @param productCode
	 * @param isTransSale
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryDefaultTransCode(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public String queryDefaultTransCodeDept(String saleCode,
			String productCode, boolean isTransSale) {
		// 是否为营业部
		if (isTransSale) {
			// 营业部
			SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentByCode(saleCode);
			// 非空判断
			if (saleDept != null) {
				// 返回驻地营业部所属外场
				return saleDept.getTransferCenter();
			} else {
				// 返回空
				return null;
			}
		} else {
			// 查询线路信息
			if (waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, new Date())) {
				return expresslineService.queryDefaultTransCode(saleCode, productCode, DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
			} else {
				return lineService.queryDefaultTransCode(saleCode, productCode, DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
			}
		}
	}

	/**
	 * 根据外场组织编码查驻地营业部
	 * 
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public String queryDefaultSalesDeptByTransCenter(String saleCode) {
		return saleDepartmentService.queryLeaveStationSaleCodeByTransferCenterCode(saleCode);
	}

	/**
	 * 运单开单完成之后，更新状态</br> 标识此约车已经完成开单，重复更新抛出异常
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午5:23:02
	 * @param inviteNo
	 *            外请车编号
	 * @param billStatus
	 *            开单状态
	 */
	@Override
	public void updateInviteVehicleForFinishBill(String inviteNo) {
		// 更新约车状态
		inviteVehicleService.updateInviteVehicleForFinishBill(inviteNo);
	}

	/**
	 * 
	 * 查询到货金额
	 * 
	 * @param
	 * @author 038590-foss-wanghui
	 * @date 2012-12-24 下午8:47:55
	 */
	@Override
	public List<QueryMoneyDto> queryRecieveMoney(
			QueryMoneyConditionDto queryMoneyConditionDto) {
		// 查询到货金额
		return waybillDao.queryRecieveMoney(queryMoneyConditionDto);
	}

	/**
	 * 
	 * 查询发货金额
	 * 
	 * @param
	 * @author 038590-foss-wanghui
	 * @date 2012-12-24 下午8:47:58
	 */
	@Override
	public List<QueryMoneyDto> queryDeliveryMoney(
			QueryMoneyConditionDto queryMoneyConditionDto) {
		// 查询发货金额
		return waybillDao.queryDeliveryMoney(queryMoneyConditionDto);
	}

	/**
	 * 根据gis网点匹配条件查询网点code
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午8:35:53
	 */
	@Override
	public List<GisDepartmentDto> queryPickupOrgCodeByGis(GisPickupOrgDto dto) {
		// 根据gis网点匹配条件查询网点code
		return gisQueryService.queryPickupOrgCodeByGis(dto);
	}

	/**
	 * 通过调用GIS的特殊地址查询接口获取地址类型
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:52:59
	 */
	@Override
	public List<String> querySpecialAddressByGis(GisPickupOrgDto dto) {
		// 通过调用GIS的特殊地址查询接口获取地址类型
		return gisQueryService.querySpecialAddressByGis(dto);
	}

	/**
	 * <p>
	 * 判断是否驻地营业部
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午4:26:02
	 * @param saleCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#identityTransSale(java.lang.String)
	 */
	@Override
	public boolean identityTransSale(String saleCode) {
		// 根据部门编码查询营业部对象
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(saleCode);
		// 为驻地则返回true，否则返回false
		return saleDepartmentEntity == null ? false : saleDepartmentEntity.checkStation();
	}

	/**
	 * 根据运单Id查询运单
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-29
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillById(java.lang.String)
	 */
	@Override
	public WaybillEntity queryWaybillById(String id) {
		// 根据运单ID查询运单基本信息
		return waybillDao.queryWaybillById(id);
	}

	/**
	 * 
	 * 通过运单号、流水号校验 当前流水号是否存在
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-5 上午9:28:56
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public boolean isSerialNoExsits(String waybillNo, String serialNo) {
		// 通过运单号、流水号校验 当前流水号是否存在
		LabeledGoodEntity labeledGood = waybillDao.queryLabeledGoodByserialNo(
				StringUtil.defaultIfNull(waybillNo), StringUtil.defaultIfNull(serialNo));
		// 若对象不为空则表示存在流水号
		return labeledGood == null ? false : true;
	}

	/**
	 * 
	 * 自有司机通过司机工号判断是否存在
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-28 下午3:43:15
	 * @param driverCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isOwnDriverExists(java.lang.String)
	 */
	@Override
	public boolean isOwnDriverExists(String driverCode) {
		DriverAssociationDto driver = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(driverCode);
		if (driver != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 根据外场编码查询外场
	 * 
	 * @author foss-sunrui
	 * @date 2013-5-21 下午3:11:36
	 * @param code
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryOutfieldByCode(java.lang.String)
	 */
	@Override
	public OutfieldEntity queryOutfieldByCode(String code) {
		return outfieldService.querySimpleOutfieldByOrgCode(code);
	}

	/**
	 * 
	 * 查询系统上线日期
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-11 下午7:22:10
	 */
	public Date queryFossGoliveDate() {
		Date goliveDate = null;
		try {
			// 获得配置对象
			ConfigurationParamsEntity entity = configurationParamsService
					.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
							// 组织配置参数-接送货模块
							ConfigurationParamsConstants.PKP_FOSS_GOLIVE_DATE,
							// 运单变更定时任务 超时天数
							FossConstants.ROOT_ORG_CODE);
			// 集团
			if (entity != null) {
				String goliveDateStr = entity.getConfValue();
				// 参数
				goliveDate = new SimpleDateFormat("yyyy-M-d").parse(goliveDateStr);
				// 天数如果弄错了不能解析为INTEGER

			}
		} catch (Exception e) {
			LOG.error("Number format Exception", e);
			// default 3
		}
		return goliveDate;
	}

	/**
	 * 是否存在id为传入值 并且active=y的运单
	 * 
	 * @param oldVersionWaybillId
	 * @return
	 */
	@Override
	public boolean existsActiveWaybillById(String id) {
		return waybillDao.existsActiveWaybillById(id);
	}

	/**
	 * 根据运单号查询费用明细中的其它费用
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:01:04
	 */
	@Override
	public List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo) {
		return waybillChargeDtlService.queryOtherChargeByNo(waybillNo);
	}

	/**
	 * 获取GUI价格
	 * 
	 * @param billCalculateDto
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-17 上午11:10:14
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryGuiBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	@Override
	public List<GuiResultBillCalculateDto> queryGuiBillPrice(
			GuiQueryBillCalculateDto billCalculateDto) {
		// 根据产品类型判断调用哪类接口
		String productCode = billCalculateDto.getProductCode();
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, billCalculateDto.getBillTime())) {
			return guiBillCaculateService
					.queryGuiExpressBillPrice(billCalculateDto);
		} else {
			return guiBillCaculateService.queryGuiBillPrice(billCalculateDto);
		}
	}

	/**
	 * 获取GUI价格
	 * 
	 * @param billCalculateDto
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-17 上午11:10:14
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryGuiBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	@Override
	public List<GuiResultBillCalculateDto> queryGuiExpressBillPrice(
			GuiQueryBillCalculateDto billCalculateDto) {

		// PdaQueryBillCalculateDto p = new PdaQueryBillCalculateDto();
		//
		// try{
		// PropertyUtils.copyProperties(p, billCalculateDto);
		// }catch(Exception e){
		//
		// }
		// List <PdaQueryBillCalculateSubDto> list2= new
		// ArrayList<PdaQueryBillCalculateSubDto>();
		// List<GuiQueryBillCalculateSubDto> list =
		// billCalculateDto.getPriceEntities();
		// if(list!=null){
		//
		// for(int i =0; i <list.size();i++){
		// GuiQueryBillCalculateSubDto d = list.get(i);
		// PdaQueryBillCalculateSubDto d2 = new PdaQueryBillCalculateSubDto();
		// try{
		// PropertyUtils.copyProperties(d2 , d);
		// }catch(Exception e){
		//
		// }
		// list2.add(d2);
		// }
		// }
		// p.setPriceEntities(list2);

		// List<PdaResultBillCalculateDto> list3 =
		// pdaBillCaculateService.queryPdaExpressBillPrice(p);
		List<GuiResultBillCalculateDto> list4 = guiBillCaculateService
				.queryGuiExpressBillPrice(billCalculateDto);

		return list4;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
	}

	/**
	 * 通过订单编号查询运单
	 * 
	 * @param orderNo
	 */
	@Override
	public WaybillEntity queryWaybillByOrderNo(String orderNo) {
		return waybillDao.queryWaybillByOrderNo(orderNo);
	}

	/**
	 * 
	 * 注入应用监控对象
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-21 下午04:15:40
	 * @param businessMonitorService
	 */
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	/**
	 * @param bankService
	 *            the bankService to set
	 */
	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	/**
	 * @param crmOrderService
	 *            the crmOrderService to set
	 */
	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}

	/**
	 * 设置 发送短信语音服务.
	 * 
	 * @param sendSmsVoiceService
	 *            the new 发送短信语音服务
	 */
	public void setSendSmsVoiceService(ISendSmsVoiceService sendSmsVoiceService) {
		this.sendSmsVoiceService = sendSmsVoiceService;
	}

	/**
	 * 设置 gIS查询服务.
	 * 
	 * @param gisQueryService
	 *            the new gIS查询服务
	 */
	public void setGisQueryService(IGisQueryService gisQueryService) {
		this.gisQueryService = gisQueryService;
	}

	/**
	 * @param orderService
	 */
	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * @param waybillDao
	 *            the waybillDao to set.
	 */
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	/**
	 * @param waybillChargeDtlService
	 *            the waybillChargeDtlService to set.
	 */
	public void setWaybillChargeDtlService(
			IWaybillChargeDtlService waybillChargeDtlService) {
		this.waybillChargeDtlService = waybillChargeDtlService;
	}

	/**
	 * @param waybillDisDtlService
	 *            the waybillDisDtlService to set.
	 */
	public void setWaybillDisDtlService(
			IWaybillDisDtlService waybillDisDtlService) {
		this.waybillDisDtlService = waybillDisDtlService;
	}

	/**
	 * @param waybillPaymentService
	 *            the waybillPaymentService to set.
	 */
	public void setWaybillPaymentService(
			IWaybillPaymentService waybillPaymentService) {
		this.waybillPaymentService = waybillPaymentService;
	}

	/**
	 * @param woodenRequirementsService
	 *            the woodenRequirementsService to set.
	 */
	public void setWoodenRequirementsService(
			IWoodenRequirementsService woodenRequirementsService) {
		this.woodenRequirementsService = woodenRequirementsService;
	}

	/**
	 * @param waybillPickupService
	 *            the waybillPickupService to set.
	 */
	public void setWaybillPickupService(
			IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	/**
	 * @param waybillPendingService
	 *            the waybillPendingService to set.
	 */
	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}

	/**
	 * @param calculateTransportPathService
	 *            the calculateTransportPathService to set.
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * @param returnBillProcessDao
	 *            the returnBillProcessDao to set.
	 */
	public void setReturnBillProcessDao(
			IReturnBillProcessDao returnBillProcessDao) {
		this.returnBillProcessDao = returnBillProcessDao;
	}

	/**
	 * @param labeledGoodService
	 *            the labeledGoodService to set.
	 */
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	/**
	 * @param waybillStockService
	 *            the waybillStockService to set.
	 */
	public void setWaybillStockService(IWaybillStockService waybillStockService) {
		this.waybillStockService = waybillStockService;
	}

	/**
	 * @param lineService
	 *            the lineService to set.
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	/**
	 * @param expresslineService
	 *            the expresslineService to set.
	 */
	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	/**
	 * @param saleDepartmentService
	 *            the saleDepartmentService to set.
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * @param vehicleAgencyDeptService
	 *            the vehicleAgencyDeptService to set.
	 */
	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set.
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param billCaculateService
	 *            the billCaculateService to set.
	 */
	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}

	/**
	 * @param pickupToDoMsgService
	 *            the pickupToDoMsgService to set.
	 */
	public void setPickupToDoMsgService(
			IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}

	/**
	 * @param pathDetailDao
	 *            the pathDetailDao to set.
	 */
	public void setPathDetailDao(IPathDetailDao pathDetailDao) {
		this.pathDetailDao = pathDetailDao;
	}

	/**
	 * @param inviteVehicleService
	 *            the inviteVehicleService to set.
	 */
	public void setInviteVehicleService(
			IInviteVehicleService inviteVehicleService) {
		this.inviteVehicleService = inviteVehicleService;
	}

	/**
	 * @param freightRouteService
	 *            the freightRouteService to set.
	 */
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}

	/**
	 * @param freightRouteLineService
	 *            the freightRouteLineService to set.
	 */
	public void setFreightRouteLineService(
			IFreightRouteLineService freightRouteLineService) {
		this.freightRouteLineService = freightRouteLineService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService
	 *            the orgAdministrativeInfoComplexService to set.
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param waybillTransactionService
	 *            the waybillTransactionService to set.
	 */
	public void setWaybillTransactionService(
			IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}

	/**
	 * @param actualFreightService
	 *            the actualFreightService to set.
	 */
	public void setActualFreightService(
			IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	

	/**
	 * @param waybillAcHisPdaService
	 *            the waybillAcHisPdaService to set.
	 */
	public void setWaybillAcHisPdaService(
			IWaybillAcHisPdaService waybillAcHisPdaService) {
		this.waybillAcHisPdaService = waybillAcHisPdaService;
	}

	/**
	 * 安装明细
	 */
	
	public IinstallationService getInstallationService() {
		return installationService;
	}

	public void setInstallationService(IinstallationService installationService) {
		this.installationService = installationService;
	}

	/**
	 * @param hisDeliveryCusService
	 *            the hisDeliveryCusService to set.
	 */
	public void setHisDeliveryCusService(
			IHisDeliveryCusService hisDeliveryCusService) {
		this.hisDeliveryCusService = hisDeliveryCusService;
	}

	/**
	 * @param hisReceiveCusService
	 *            the hisReceiveCusService to set.
	 */
	public void setHisReceiveCusService(
			IHisReceiveCusService hisReceiveCusService) {
		this.hisReceiveCusService = hisReceiveCusService;
	}

	/**
	 * @param receiveAddressRfcService
	 *            the receiveAddressRfcService to set.
	 */
	public void setReceiveAddressRfcService(
			IReceiveAddressRfcService receiveAddressRfcService) {
		this.receiveAddressRfcService = receiveAddressRfcService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	/**
	 * @param goodsAreaService
	 *            the goodsAreaService to set
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	/**
	 * 发送待处理短信服务
	 * 
	 * @author WangQianJin
	 * @date 2013-4-19 下午1:14:29
	 */
	public void setPendingSendSMSService(
			IPendingSendSMSService pendingSendSMSService) {
		this.pendingSendSMSService = pendingSendSMSService;
	}

	/**
	 * @param outfieldService
	 *            the outfieldService to set
	 */
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setBillingGroupTransFerService(
			IBillingGroupTransFerService billingGroupTransFerService) {
		this.billingGroupTransFerService = billingGroupTransFerService;
	}

	public IWaybillPictureDao getWaybillPictureDao() {
		return waybillPictureDao;
	}

	public void setWaybillPictureDao(IWaybillPictureDao waybillPictureDao) {
		this.waybillPictureDao = waybillPictureDao;
	}

	/**
	 * 查询营业部
	 * 
	 * @param dto
	 * @return
	 */
	public List<SaleDepartmentEntity> queryByDepartmentInfo(
			QueryPickupPointDto dto) {
		if (FossConstants.YES.equals(dto.getPickUpSelf())) {
			if (waybillExpressService.onlineDetermineIsExpressByProductCode(dto.getTransType(), dto.getCurDate())) {
				return waybillDao.queryByDepartmentInfoExpress(dto);
			} else {
				return waybillDao.queryByDepartmentInfo(dto);
			}
		} else {
			if (waybillExpressService.onlineDetermineIsExpressByProductCode(dto.getTransType(), dto.getCurDate())) {
				// 这段代码新加的 小件 查询虚拟部门
				return waybillDao.queryByDepartmentInfoVirtual(dto);
			} else {
				return waybillDao.queryByDepartmentInfo(dto);
			}
		}

	}

	/**
	 * 查询外发网点
	 * 
	 * @param dto
	 * @return
	 */
	public List<OuterBranchEntity> queryOuterBranchs(QueryPickupPointDto dto) {
		return waybillDao.queryOuterBranchs(dto);
	}

	/**
	 * 获得服务器时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 下午3:44:42
	 */
	@Override
	public Date gainServerTime() {
		Date date = new Date();
		return date;
	}

	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * 
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	@Override
	public OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode) {
		return waybillDao.queryAgencyBranchInfo(agencyBranchCode);
	}

	/**
	 * 
	 * 打印标签记录日志
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-1
	 */
	@Override
	public void addPrintLabelInfo(GUIPrintLabelDto printLabelDto) {
		PrintLabelEntity printInfoEntity = new PrintLabelEntity();
		try {
			PropertyUtils.copyProperties(printInfoEntity, printLabelDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置为服务器时间
		printInfoEntity.setPrintTime(new Date());
		printLabelService.addPrintLabel(printInfoEntity);
	}

	@Override
	public BigDecimal getExchangedFee(BigDecimal rmbFee, String currencyCode, Date billTime) {
		return exchangeRateService.getExchangedFee(rmbFee, currencyCode, billTime);
	}

	@Override
	public BigDecimal getExchangedFeeRate(String currencyCode, Date billTime) {
		ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
		exchangeRateEntity.setCurrency(currencyCode);
		exchangeRateEntity.setBeginTime(billTime);
		BigDecimal value = exchangeRateService.getExchangedFeeRateByCurrencyCode(currencyCode, billTime);
		return value;
	}

	/**
	 * 根据条件查询营业部信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-19 上午11:15:54
	 */
	@Override
	public SaleDepartmentEntity queryDepartmentInfoByDto(QueryPickupPointDto dto2) {
		return waybillDao.queryDepartmentInfoByDto(dto2);
	}

	/**
	 * 根据条件查询偏线信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-19 下午1:52:21
	 */
	@Override
	public OuterBranchEntity queryOuterBranchByDto(QueryPickupPointDto dto2) {
		return waybillDao.queryOuterBranchByDto(dto2);
	}

	/**
	 * 
	 * 根据运单号集合查询未补录的运单
	 * 
	 * @author foss-meiying
	 * @date 2013-7-19
	 */
	public List<String> queryWaybillNoMakeupByWaybillNo(WaybillQueryInfoDto dto) {
		return waybillDao.queryWaybillNoMakeupByWaybillNo(dto);
	}

	/**
	 * 
	 * 根据运单号集合查询运单状态
	 * 
	 * @author foss-panguoyang
	 * @date 2013-7-23
	 */
	@Override
	public String getWaybillStatus(String waybillNo) {
		return waybillDao.getWaybillStatus(waybillNo);
	}

	/**
	 * 通过词条代码查询
	 * 
	 * @author WangQianJin
	 * @date 2013-7-23 上午11:28:25
	 */
	public List<DataDictionaryValueEntity> queryByTermsCode(String termsCode) {
		return waybillDao.queryByTermsCode(termsCode);
	}

	/**
	 * 通过物品的数量，体积，保价金额判断是否属于贵重物品
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 上午9:57:44
	 */
	public boolean isValueGoods(int goodsNum, String goodsVolume,
			String goodsValue) {
		boolean retVal = false;
		String volume = null;
		String value = null;
		String[] confCodes = new String[2];
		List<ConfigurationParamsEntity> sysConfigs;
		ConfigurationParamsEntity sysConfig = queryByConfCode(
				WaybillConstants.VALUEGOODS_WAYBILL_PRICE, null);
		/**
		 * 判断单票货物平均单件保价大于等于10万元；
		 */
		if (sysConfig != null && goodsNum != 0
				&& (Double.parseDouble(goodsValue) / goodsNum) >= Double.parseDouble(sysConfig.getConfValue())) {
			retVal = true;
		} else {
			if (goodsNum == 1) {
				confCodes[0] = WaybillConstants.VALUEGOODS_SINGLE_VOLUME;
				confCodes[1] = WaybillConstants.VALUEGOODS_SINGLE_PRICE;
				sysConfigs = queryByConfCodeArray(confCodes, null);
				if (sysConfigs != null && sysConfigs.size() > 0) {
					for (ConfigurationParamsEntity sysConfigLocal : sysConfigs) {
						if (sysConfigLocal.getCode().equals(confCodes[0])) {
							volume = sysConfigLocal.getConfValue();
						}
						if (sysConfigLocal.getCode().equals(confCodes[1])) {
							value = sysConfigLocal.getConfValue();
						}
					}
					if (volume != null && value != null
							&& Double.parseDouble(goodsVolume) <= Double.parseDouble(volume)
							&& Double.parseDouble(goodsValue) >= Double.parseDouble(value)) {
						retVal = true;
					}
				}
			} else {
				confCodes[0] = WaybillConstants.VALUEGOODS_MUTI_VOLUME;
				confCodes[1] = WaybillConstants.VALUEGOODS_MUTI_PRICE;
				sysConfigs = queryByConfCodeArray(confCodes, null);
				if (sysConfigs != null && sysConfigs.size() > 0) {
					for (ConfigurationParamsEntity sysConfigLocal : sysConfigs) {
						if (sysConfigLocal.getCode().equals(confCodes[0])) {
							volume = sysConfigLocal.getConfValue();
						}
						if (sysConfigLocal.getCode().equals(confCodes[1])) {
							value = sysConfigLocal.getConfValue();
						}
					}
					if (volume != null && value != null
							&& (Double.parseDouble(goodsVolume) / goodsNum) <= Double.parseDouble(volume)
							&& (Double.parseDouble(goodsValue) / goodsNum) >= Double.parseDouble(value)) {
						retVal = true;
					}
				}
			}
		}
		return retVal;
	}

	/**
	 * 通过配置项标示查询系统配置,根据confcode和orgcode一起查询
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 上午10:08:54
	 */
	public ConfigurationParamsEntity queryByConfCode(String confCode,
			String orgCode) {
		// 本部门及所有的上级部门Code
		StringBuilder orgCodeListStr = new StringBuilder("");
		// 如果部门为空，则获取当前用户的部门
		if (orgCode == null || "".equals(orgCode)) {
			// 获取当前用户
			UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
			orgCode = user.getEmployee().getDepartment().getCode();
		}
		// 获取本部门及所有的上级部门列表
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
		if (orgList != null && orgList.size() > 0) {
			for (int i = 0; i < orgList.size(); i++) {
				OrgAdministrativeInfoEntity org = orgList.get(i);
				if (org != null && org.getCode() != null
						&& !"".equals(org.getCode())) {
					orgCodeListStr.append("'").append(org.getCode()).append("'").append(",");
				}
			}
			if (orgCodeListStr.length() > 0) {
				orgCodeListStr = new StringBuilder(orgCodeListStr.toString().substring(0,
						orgCodeListStr.toString().length() - 1));
			}
		} else {
			// 如果查询不到上级部门，则获取当前部门和顶级部门
			orgCodeListStr.append("'").append(orgCode).append("',").append("'").append(FossConstants.ROOT_ORG_CODE).append("'");
		}
		return waybillDao.queryByConfCode(confCode, orgCodeListStr.toString());
	}

	/**
	 * 通过配置项集合查询系统配置
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 上午10:18:11
	 */
	public List<ConfigurationParamsEntity> queryByConfCodeArray(
			String[] confCodes, String orgCode) {
		// 本部门及所有的上级部门Code
		StringBuilder orgCodeListStr = new StringBuilder("");
		// 如果部门为空，则获取当前用户的部门
		if (orgCode == null || "".equals(orgCode)) {
			// 获取当前用户
			UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
			orgCode = user.getEmployee().getDepartment().getCode();
		}
		// 获取本部门及所有的上级部门列表
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
		if (orgList != null && orgList.size() > 0) {
			for (int i = 0; i < orgList.size(); i++) {
				OrgAdministrativeInfoEntity org = orgList.get(i);
				if (org != null && org.getCode() != null && !"".equals(org.getCode())) {
					orgCodeListStr.append("'").append(org.getCode()).append("'").append(",");
				}
			}
			if (orgCodeListStr.length() > 0) {
				orgCodeListStr = new StringBuilder(orgCodeListStr.toString().substring(0,
						orgCodeListStr.toString().length() - 1));
			}
		} else {
			// 如果查询不到上级部门，则获取当前部门和顶级部门
			orgCodeListStr.append("'").append(orgCode).append("',").append("'").append(FossConstants.ROOT_ORG_CODE).append("'");
		}
		return waybillDao.queryByConfCodeArray(confCodes, orgCodeListStr.toString());
	}

	/**
	 * 
	 * 查询网点自提区域信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	public List<String> queryByCodeAndPickup(String code) {
		return waybillDao.queryByCodeAndPickup(code);
	}

	/**
	 * 
	 * 查询网点自提区域信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	public List<String> queryByCodeAndDelivery(String code) {
		return waybillDao.queryByCodeAndDelivery(code);
	}

	/**
	 * 查询自有网点
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	public List<BranchQueryVo> queryListByDepartment(SaleDepartmentEntity entity) {
		return waybillDao.queryListByDepartment(entity);
	}

	/**
	 * 查询能开精准大票的自有网点
	 */
	public List<BranchQueryVo> queryListByDepartmentAndPro(
			SaleDepartmentEntity entity) {
		return waybillDao.queryListByDepartmentAndPro(entity);
	}

	/**
	 * 查询汽运偏线、空运网点
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	public List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity) {
		return waybillDao.queryListByBranch(entity);
	}

	/**
	 * 
	 * 根据部门Code和所属集中开单组查询
	 * 
	 * @author WangQianJin
	 * @date 2013-08-02
	 */
	public List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(
			String code, String billingGroup) {
		return waybillDao.querySalesBillGroupByCodeAndBillCode(code, billingGroup);
	}

	/**
	 * 根据产品编码查询产品信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-12 上午9:56:17
	 */
	@Override
	public ProductEntity queryProductByCode(String code) {
		return waybillDao.queryProductByCode(code);
	}

	/**
	 * 
	 * 更新锁屏信息
	 * 
	 * @author panguoyang
	 * @date 2013-08-02
	 */
	@Override
	public List<OrderLockResult> crmsyncorderLock(List<OrderLockEntity> records) {
		return orderLocksService.crmsyncorderLock(records);
	}

	/**
	 * 根据部门名称和所属集中开单组查询
	 * 
	 * @author WangQianJin
	 * @date 2013-8-11 下午3:32:15
	 */
	public List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(
			String name, String billingGroup) {
		LOG.info("进入 querySaleDepartmentByNameForCentralized");
	List<String> lists = new ArrayList<String>();
	   lists.add(billingGroup);
	   return waybillDao.querySaleDepartmentByNameForCentralized(name,lists);
	   }		
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralizedexp(
			String name, String billingGroup, String waybillNo) {
		LOG.info("进入 querySaleDepartmentByNameForCentralized_exp");
		List<String> lists = new ArrayList<String>();
		if(org.apache.commons.lang.StringUtils.isNotBlank(waybillNo)){
			LOG.info("运单号:"+waybillNo+" 不为空,查询图片运单表是否有此单信息");
			WaybillPictureEntity waybillPictureEntity = waybillPictureDao.queryWaybillPictureByWaybillNo(waybillNo);
			if(waybillPictureEntity != null){
				LOG.info("图片运单表中有此运单信息:"+waybillNo);
				String localBillGroupCode = waybillPictureEntity.getLocalBillGroupCode();
				if(org.apache.commons.lang3.StringUtils.isNotBlank(localBillGroupCode) && !billingGroup.equals(localBillGroupCode)){
					lists.add(localBillGroupCode);
				}			}
		}
			lists.add(billingGroup);
			return waybillDao.querySaleDepartmentByNameForCentralized(name,lists);
		}

	/**
	 * 
	 * 获取集中开单组的外场
	 * 
	 * @author 025000-FOSS-panguoyang
	 * @date 2013-8-19 下午06:17:06
	 */
	@Override
	public SaleDepartmentEntity queryPickupCentralizedDeptCodeByBillTime(
			String billingGroupCode, Date billTime) {
		SaleDepartmentEntity pickupCentralizedDept = null;
		String stransCenterCode = this.queryTransCenterByBillingGroupCodeByBillTime(billingGroupCode, billTime);
		if (!StringUtils.isEmpty(stransCenterCode)) {
			String salesDeptCode = this.queryDefaultSalesDeptByTransCenter(stransCenterCode);
			if (!StringUtils.isEmpty(salesDeptCode)) {
				pickupCentralizedDept = saleDepartmentService.querySaleDepartmentByCode(salesDeptCode);
			}
		}
		return pickupCentralizedDept;

	}

	/**
	 * 
	 * 获取集中开单组的外场
	 * 
	 * @author 025000-FOSS-panguoyang
	 * @date 2013-8-19 下午06:17:06
	 */
	public String queryTransCenterByBillingGroupCodeByBillTime(
			String billingGroupCode, Date billTime) {
		BillingGroupTransFerEntity entity = billingGroupTransFerService
				.queryTransferCenterByBillingGroupCode(billingGroupCode, billTime);
		if (entity != null) {
			return entity.getTransFerCode();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 按单号且创建时间升序查询运单
	 * 
	 * @author 025000-FOSS-panguoyang
	 * @date 2013-8-19 下午06:17:06
	 */
	@Override
	public WaybillEntity queryWaybillNo(String waybillNo) {
		return waybillDao.queryWaybillNo(waybillNo);
	}

	/**
	 * 获取整车的保费相关信息
	 * 
	 * @param billCalculateDto
	 * @return
	 */
	@Override
	public GuiResultBillCalculateDto getProductPriceDtoOfWVHAndBF(
			GuiQueryBillCalculateDto billCalculateDto) {
		return guiBillCaculateService.getProductPriceDtoOfWVHAndBF(billCalculateDto);
	}

	@Override
	public boolean isWayBillExsitsOnOnlineAndOffline(String waybillNo) {
		boolean isExsitsOffline = waybillPendingService.isPendingExsits(waybillNo);
		// 暂存表中存在数据
		if (isExsitsOffline) {
			return true;
		}
		// 判断在线表中是否存在数据
		boolean isExsitsOnline = this.isWayBillExsits(waybillNo);
		if (isExsitsOnline) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isPendingExsits(String mixNo) {
		WaybillPendingEntity pendingEntity = waybillDao.queryPendingByNo(StringUtil.defaultIfNull(mixNo));
		if (pendingEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据网点编码查询快递代理网点信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 上午11:47:58
	 */
	@Override
	public OuterBranchExpressEntity queryLdpAgencyDeptByCode(String deptCode) {
		if (StringUtils.isEmpty(deptCode)) {
			return null;
		}
		return ldpAgencyDeptService.queryLdpAgencyDeptByCode(deptCode, FossConstants.YES);
	}

	/**
	 * 小件 根据返单运单号查询运单
	 */
	public WaybillDto queryWaybillByReturnBillNo(String waybillNo2) {
		// 定义运单DTO对象
		WaybillDto waybill = new WaybillDto();
		String waybillNo = null;
		List<WaybillEntity> waybillEntityList = waybillDao.queryWaybillByReturnBillNo(waybillNo2);

		WaybillEntity waybillEntity = null;

		if (waybillEntityList != null && waybillEntityList.size() > 0) {
			for (WaybillEntity tmpEntity : waybillEntityList) {
				if (FossConstants.ACTIVE.equals(tmpEntity.getActive())) {
					// waybillEntity = tmpEntity;
					waybillNo = tmpEntity.getWaybillNo();
					ActualFreightEntity ac = queryActualFreightByNo(waybillNo);
					if (WaybillConstants.ABORTED.equals(ac.getStatus())
							|| WaybillConstants.OBSOLETE.equals(ac.getStatus())) {
						continue;
					} else {
						waybillEntity = tmpEntity;

						waybill.setActualFreightEntity(ac);
					}
				}
			}
		}

		if (waybillEntity == null) {
			return waybill;
		}

		// 设置运单基本信息
		waybill.setWaybillEntity(waybillEntity);

		// 设置运单费用明细信息
		waybill.setWaybillChargeDtlEntity(waybillChargeDtlService.queryChargeDtlEntityByNo(waybillNo));
		// 设置运单折扣明细信息
		waybill.setWaybillDisDtlEntity(waybillDisDtlService.queryDisDtlEntityByNo(waybillNo));
		// 设置运单付款明细信息
		waybill.setWaybillPaymentEntity(waybillPaymentService.queryWaybillPayment(waybillNo));
		// 设置打木架信息
		waybill.setWoodenRequirementsEntity(woodenRequirementsService.queryWoodenRequirements(waybillNo));
		// 设置运单冗余信息
		//

		// TODO
		// 小件添加---------------------------
		if (waybill.getWaybillEntity() != null
	&& (WaybillConstants
						.directDetermineIsExpressByProductCode(waybill
								.getWaybillEntity().getProductCode()))) {
			waybill.setWaybillExpressEntity(getWabillExpressEntityByWaybillId(waybill
					.getWaybillEntity().getId()));
			// TODO 返单 返货 都需要判断签收状态的 顶
			List<LabeledGoodEntity> labeledGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
			boolean allSigned = true;

			if (labeledGoodList != null && labeledGoodList.size() > 0) {
				for (int i = 0; i < labeledGoodList.size(); i++) {
					LabeledGoodEntity labeledGood = labeledGoodList.get(i);
					if (labeledGood == null
							|| !FossConstants.ACTIVE.equals(labeledGood.getActive())) {
						continue;
					}

					String signResult = FossConstants.NO;

					// 结算这里没有引入jar所以可能会为null没有注入
					if (signDetailService != null) {
						signResult = signDetailService.querySerialNoIsSign(waybillNo, labeledGood.getSerialNo());
					}

					if (!FossConstants.YES.equals(signResult)) {
						allSigned = false;
					}

				}
			}
			// 在判断是否可以返货的时候有用
			waybill.setAllSigned(allSigned);
		}

		// 返回运单DTO
		return waybill;

	}

	@Override
	public List<SaleDepartmentEntity> querySaleDeptsByBillingGroupCode(String billGroupCode) {
		// 定义对象接收数据集合
		List<SaleDepartmentEntity> saleDeptList = null;
		// 查询集合对象
		List<SalesBillingGroupEntity> billGroups = salesBillGroupService.querySalesListByBillingGroupCode(billGroupCode);
		// 集合非空判断
		if (CollectionUtils.isNotEmpty(billGroups)) {
			// 实例化对象
			saleDeptList = new ArrayList<SaleDepartmentEntity>();
			// 查询组织对象
			for (SalesBillingGroupEntity entity : billGroups) {
				// 此处不用使用queryByCode(code,date)方法，因为若部门已失效则无法登陆的
				SaleDepartmentEntity saleDept = salesDepartmentDao
						.queryByCode(StringUtil.defaultIfNull(entity.getSalesDeptCode()));
				if (null != saleDept) {
					saleDeptList.add(saleDept);
				}
			}
		}
		return saleDeptList;

	}

	@Override
	public boolean queryIsExpressBill(String waybillNo, String productCode) {
		boolean pendingActive = waybillPendingService.queryIsExpressBill(
				waybillNo, productCode);
		if (pendingActive) {
			return true;
		} else {
			boolean pcActive = waybillDao.queryIsExpressBill(waybillNo);
			if (pcActive) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取刚提交运单时的那条运单信息 WangQianJin
	 * 
	 * @param waybillNo
	 */
	@Override
	public WaybillEntity queryWaybillForFirst(String waybillNo) {
		return waybillDao.queryWaybillForFirst(waybillNo);
	}

	/**
	 * 查询是否为第一票运单信息 MANA-257接货费优化
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 上午11:07:04
	 */
	@Override
	public boolean isFirstDeliveryWaybill(WaybillEntity entity) {
		// 定义查询参数
		WaybillEntity queryParm = entity;
		// 定义查询结果
		WaybillEntity queryResult = null;
		// 设置查询条件
		queryParm.setActive(FossConstants.YES);
		queryParm.setReceiveOrgCode(entity.getReceiveOrgCode());
		// 对于待补录运单 可能没有在运单且中生成记录，故要非空判断
		if (null == entity.getBillTime()) {
			queryParm.setBillTime(new Date());
		}

		// 若有发货客户编码，则忽略使用手机、电话做查询条件
		if (StringUtil.isNotEmpty(entity.getDeliveryCustomerCode())) {
			// 设置手机、电话为空
			queryParm.setDeliveryCustomerMobilephone("");
			queryParm.setDeliveryCustomerPhone("");
			queryResult = waybillDao.queryFirstDeliveryWaybill(queryParm);
			// 若无值或者是第一票则直接返回结果
			if (null == queryResult || entity.getWaybillNo().equals(queryResult.getWaybillNo())) {
				// 找到想要的结果则返回
				return true;
			}
		}
		// 无客户编码，有手机号码
		else if (StringUtil.isNotEmpty(entity.getDeliveryCustomerMobilephone())) {
			// 设置电话为空
			queryParm.setDeliveryCustomerPhone("");
			queryResult = waybillDao.queryFirstDeliveryWaybill(queryParm);
			// 若无值或者是第一票则直接返回结果
			if (null == queryResult || entity.getWaybillNo().equals(queryResult.getWaybillNo())) {
				// 找到想要的结果则返回
				return true;
			}
		}
		// 只有电话号码
		else {
			// 对电话号码进行解析，并判断是否含有手机号码
			Map<String, List<String>> map = StringHandlerUtil.resolveMobileAndPhone(entity.getDeliveryCustomerPhone());
			List<String> mobileList = map.get("mobiles");
			List<String> phoneList = map.get("phones");
			// 若电话中包含手机号码，则对手机号码进行遍历，查找第一票运单
			if (CollectionUtils.isNotEmpty(mobileList)) {
				for (String mobile : mobileList) {
					queryParm.setDeliveryCustomerPhone(mobile);
					queryResult = waybillDao.queryFirstDeliveryWaybill(queryParm);
					// 若无值或者是第一票则直接返回结果
					if (null == queryResult
							|| entity.getWaybillNo().equals(queryResult.getWaybillNo())) {
						// 找到想要的结果则返回
						return true;
					} else {
						// 否则继续
						continue;
					}
				}
			} else {
				for (String phone : phoneList) {
					queryParm.setDeliveryCustomerPhone(phone);
					queryResult = waybillDao
							.queryFirstDeliveryWaybill(queryParm);
					// 若无值或者是第一票
					if (null == queryResult
							|| entity.getWaybillNo().equals(
									queryResult.getWaybillNo())) {
						return true;
					} else {
						// 否则继续
						continue;
					}
				}
			}

		}

		return false;
	}

	/**
	 * 根据运单号查询是否有运单状态信息
	 * 
	 * @author WangQianJin
	 * @param waybillTransactionEntity
	 * @return
	 */
	@Override
	public boolean isExistWaybillTransaction(String waybillNo) {
		return waybillTransactionService.isExistWaybillTransaction(waybillNo);
	}

	/**
	 * <p>
	 * 根据派送单ID查询运单详情
	 * </p>
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-12 19:35:25
	 */
	@Override
	public List<WaybillEntity> queryWaybillEntityByDeliverbillById(
			String deliverBillId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("deliverBillId", deliverBillId);
		map.put("active", FossConstants.YES);
		return waybillDao.queryWaybillEntityByDeliverbillById(map);
	}

	/**
	 * <p>
	 * 根据交接单No查询运单详情
	 * </p>
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-12 19:35:25
	 */
	@Override
	public List<WaybillEntity> queryWaybillEntityByHandoverBillNo(
			String handoverBillNo) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("handoverBillNo", handoverBillNo);
		map.put("active", FossConstants.YES);
		return waybillDao.queryWaybillEntityByHandoverBillNo(map);
	}

	/**
	 * 根据CRM请求返回运单及营销活动信息
	 * 
	 * @创建时间 2014-4-14 下午7:44:15
	 * @创建人： WangQianJin
	 */
	@Override
	public FossQueryAcctinfoResponse fossQueryAcctinfo(
			FossQueryAcctinfoRequest payload) {
		CrmParamVo crmParamVo = new CrmParamVo();
		crmParamVo.setDeliveryCustomerCode(payload.getDeliveryCustomerCode());
		crmParamVo.setStartDate(payload.getStartDate());
		crmParamVo.setEndDate(payload.getEndDate());
		crmParamVo.setPageSize(payload.getPageSize());
		crmParamVo.setCurrentPage(payload.getCurrentPage());
		crmParamVo.setDiscountType(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
		crmParamVo.setActive(FossConstants.YES);
		crmParamVo.setObsolete(WaybillConstants.OBSOLETE);
		crmParamVo.setAborted(WaybillConstants.ABORTED);
		List<CrmResultDto> dtoList = waybillDao.queryWaybillInfoByCrm(
				crmParamVo, true);
		return convertCrmResponseForWaybill(dtoList, crmParamVo);
	}

	/**
	 * 转换Dto到接口需要的类型
	 * 
	 * @创建时间 2014-4-12 下午7:33:18
	 * @创建人： WangQianJin
	 */
	private FossQueryAcctinfoResponse convertCrmResponseForWaybill(
			List<CrmResultDto> dtoList, CrmParamVo crmParamVo) {
		FossQueryAcctinfoResponse response = new FossQueryAcctinfoResponse();
		List<FossQueryAcctinfo> list = new ArrayList<FossQueryAcctinfo>();
		FossQueryAcctinfo info = null;
		if (dtoList != null && dtoList.size() > 0) {
			for (CrmResultDto dto : dtoList) {
				info = new FossQueryAcctinfo();
				info.setWaybillNo(dto.getWaybillNo());
				info.setReceiveCustomerName(dto.getReceiveCustomerName());
				info.setReceiveCustomerContact(dto.getReceiveCustomerContact());
				info.setReceiveCustomerMobilephone(dto
						.getReceiveCustomerMobilephone());
				info.setReceiveCustomerPhone(dto.getReceiveCustomerPhone());
				info.setReceiveCustomerAddress(dto.getReceiveCustomerAddress());
				info.setProductCode(dto.getProductCode());
				info.setCustomerPickupOrgCode(dto.getCustomerPickupOrgCode());
				info.setBillTime(dto.getBillTime());
				info.setActiveName(dto.getActiveName());
				info.setActiveType(dto.getActiveType());
				info.setActiveCode(dto.getActiveCode());
				info.setDiscountType(dto.getDiscountType());
				info.setActiveStartTime(dto.getActiveStartTime());
				info.setActiveEndTime(dto.getActiveEndTime());
				// System.out.println("waybill=========="+info.getWaybillNo());
				// System.out.println("getReceiveCustomerName=========="+info.getReceiveCustomerName());
				// System.out.println("getReceiveCustomerContact=========="+info.getReceiveCustomerContact());
				// System.out.println("getReceiveCustomerMobilephone=========="+info.getReceiveCustomerMobilephone());
				// System.out.println("getReceiveCustomerPhone=========="+info.getReceiveCustomerPhone());
				// System.out.println("getReceiveCustomerAddress=========="+info.getReceiveCustomerAddress());
				// System.out.println("getProductCode=========="+info.getProductCode());
				// System.out.println("getCustomerPickupOrgCode=========="+info.getCustomerPickupOrgCode());
				// System.out.println("getBillTime=========="+info.getBillTime());
				// System.out.println("getActiveName=========="+info.getActiveName());
				// System.out.println("getActiveType=========="+info.getActiveType());
				// System.out.println("getActiveCode=========="+info.getActiveCode());
				// System.out.println("getDiscountType=========="+info.getDiscountType());
				// System.out.println("getActiveStartTime=========="+info.getActiveStartTime());
				// System.out.println("getActiveEndTime=========="+info.getActiveEndTime());
				list.add(info);
			}
		}
		// 设置返回结果集
		response.getFossQueryAcctinfoList().addAll(list);
		int totalNum = waybillDao.countQueryWaybillInfoByCrm(crmParamVo);
		// System.out.println("totalNum=========="+totalNum);
		response.setTotalNum(totalNum);
		return response;
	}

	/**
	 * 获取CRM营销活动信息
	 * 
	 * @创建时间 2014-4-16 下午7:46:54
	 * @创建人： WangQianJin
	 */
	@Override
	public CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo pdaParamDto) {
		CrmActiveInfoDto crmActiveInfoDto = new CrmActiveInfoDto();
		MarkActivitiesQueryConditionDto entity = new MarkActivitiesQueryConditionDto();
		if (pdaParamDto != null) {
			entity.setBilllingTime(pdaParamDto.getCurrentDate());
			entity.setActivityCategory(pdaParamDto.getActivityCategory());
			entity.setActive(pdaParamDto.getActive());
			entity.setDevelopDeptCode(pdaParamDto.getCurrentOrgCode());
		}
		List<MarkActivitiesQueryConditionDto> list = markActivitiesService
				.queryMarkActivitiesByCondition(entity);
		crmActiveInfoDto.setActiveList(list);
		return crmActiveInfoDto;
	}

	/**
	 * 获取营销活动折扣信息
	 * 
	 * @创建时间 2014-4-22 上午10:51:48
	 * @创建人： WangQianJin
	 */
	@Override
	public MarkActivitiesQueryConditionDto getActiveDiscountInfo(
			MarkActivitiesQueryConditionDto entity) {
		return markActivitiesService.queryMarkActivitiesInfoByCondition(entity);
	}

	/**
	 * 根据客户编码获取客户信息
	 * 
	 * @创建时间 2014-4-22 下午1:24:08
	 * @创建人： WangQianJin
	 */
	@Override
	public CustomerDto queryCustInfoByCode(String custCode) {
		return queryCustomerService.queryCustInfoByCode(custCode);
	}

	/**
	 * 根据运单号和类型查询CRM营销活动
	 * 
	 * @创建时间 2014-4-22 下午8:34:33
	 * @创建人： WangQianJin
	 */
	@Override
	public WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo) {
		return waybillDisDtlService.queryActiveInfoByNoAndType(waybillNo);
	}

	/**
	 * 根据运单号和类型查询CRM营销活动
	 * 
	 * @创建时间 2014-4-22 下午8:34:33
	 * @创建人： WangQianJin
	 */
	@Override
	public WaybillDisDtlPendingEntity queryPendingActiveInfoByNoAndType(
			String waybillNo) {
		return waybillPendingService.queryActiveInfoByNoAndType(waybillNo);
	}

	/**
	 * 获取CRM营销活动信息(不包含行业相关的营销活动)
	 * 
	 * @创建时间 2014-4-16 下午7:46:54
	 * @创建人： WangQianJin
	 */
	@Override
	public CrmActiveInfoDto getActiveInfoListNoIndurstry(
			CrmActiveParamVo paramDto) {
		CrmActiveInfoDto crmActiveInfoDto = new CrmActiveInfoDto();
		MarkActivitiesQueryConditionDto entity = new MarkActivitiesQueryConditionDto();
		if (paramDto != null) {
			entity.setBilllingTime(paramDto.getCurrentDate());
			entity.setActivityCategory(paramDto.getActivityCategory());
			entity.setActive(paramDto.getActive());
			entity.setDevelopDeptCode(paramDto.getCurrentOrgCode());
		}
		List<MarkActivitiesQueryConditionDto> list = markActivitiesService
				.queryMarkActivitiesNoIndurstryByCondition(entity);
		crmActiveInfoDto.setActiveList(list);
		return crmActiveInfoDto;
	}

	/**
	 * 新增运单折扣明细
	 * 
	 * @创建时间 2014-5-8 下午8:33:14
	 * @创建人： WangQianJin
	 */
	@Override
	public int insertWaybillDisDtl(WaybillDisDtlEntity record) {
		record.setId(UUIDUtils.getUUID());
		// 设置创建人、创建时间、更新人、更新时间
		record.setCreateTime(new Date());
		record.setModifyTime(new Date());
		record.setBillTime(new Date());
		// 设置是否有效
		record.setActive(FossConstants.ACTIVE);
		return waybillDisDtlService.insert(record);
	}

	/**
	 * 插入部分对象数据
	 * 
	 * @author WangQianJin
	 * @date 2014-05-21
	 */
	@Override
	public int insertSelective(WaybillDisDtlEntity record) {
		record.setId(UUIDUtils.getUUID());
		// 设置创建人、创建时间、更新人、更新时间
		record.setCreateTime(new Date());
		record.setModifyTime(new Date());
		record.setBillTime(new Date());
		// 设置是否有效
		record.setActive(FossConstants.ACTIVE);
		return waybillDisDtlService.insertSelective(record);
	}

	/**
	 * 该方法为中转生成因为更改货物件数而生成待办而写的一个方法。所需参数除货物最初件数不需传递，其他为必填项
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-5 16:11:21
	 */
	@Override
	public ResultDto createNotAllPackageTodo(NeedCreateTodoDto needCreateTodoDto) {
		LOG.info("非全包装需生成待办数据处理开始");
		StringBuffer sb = new StringBuffer();
		// 运单号
		if (StringUtils.isEmpty(needCreateTodoDto.getWaybillNo())) {
			sb.append("运单号");
		}
		// 需生成待办对应流水号集合
		if (CollectionUtils.isEmpty(needCreateTodoDto.getNeedCreateSerialList())) {
			sb.append("需生成待办对应流水号集合");
		}
		// 当前操作部门编码
		if (StringUtils.isEmpty(needCreateTodoDto.getUserOrgCode())) {
			sb.append("当前操作部门编码");
		}
		// 当前操作部门名称
		if (StringUtils.isEmpty(needCreateTodoDto.getUserOrgName())) {
			sb.append("当前操作部门名称");
		}
		// 当前操作人编码
		if (StringUtils.isEmpty(needCreateTodoDto.getUserCode())) {
			sb.append("当前操作人编码");
		}
		// 当前操作人姓名
		if (StringUtils.isEmpty(needCreateTodoDto.getUserName())) {
			sb.append("当前操作人姓名");
		}
		// 修改运单货物件数
		if (needCreateTodoDto.getNewGoodNum() == null
				|| needCreateTodoDto.getNewGoodNum() < 0) {
			sb.append("修改运单货物件数");
		}
		// 错误详情一次性传给人家
		if (!StringUtils.isEmpty(sb.toString())) {
			throw new BusinessException(sb.toString() + "为空或者不合法,请重试");
		}
		LOG.info("传入的参数为：" + ReflectionToStringBuilder.toString(needCreateTodoDto));
		// 定义结果对象
		ResultDto res = new ResultDto();
		// 根据运单号查询运单基本信息对象
		WaybillEntity waybill = waybillDao.queryWaybillByNo(needCreateTodoDto.getWaybillNo());
		if (waybill != null) {
			// 进行新增旧标签的待办生成
			PDAWayBillRfcDto numRfcDto = new PDAWayBillRfcDto();
			// 拼装更改详情数据
			addWaybillRfcChangeDetail(needCreateTodoDto, waybill, numRfcDto);
			// 拼装更改单数据
			addWaybillRfcEntity(needCreateTodoDto, waybill, numRfcDto);
			// 拼装更改审核历史记录
			addWaybillRfActionHistory(needCreateTodoDto, waybill, numRfcDto);
			// 添加PDA更改待办信息
			waybillRfcService.addPackageRfcInfo(numRfcDto);
			// 查询出对应的流水号
			List<LabeledGoodEntity> entityList = labeledGoodService.queryLabeledGoodByWaybillNoWithSerial(
					needCreateTodoDto.getWaybillNo(), needCreateTodoDto.getNeedCreateSerialList());
			// 判断流水号与当前系统的流水号是否一致
			if (entityList != null && entityList.size() != needCreateTodoDto.getNeedCreateSerialList().size()) {
				throw new BusinessException("传入的流水号与后台有效流水号不一致,流水号为："
						+ needCreateTodoDto.getNeedCreateSerialList().toString() + ",请联系开发处理");
			} else {
				List<LabeledGoodTodoEntity> labelList = new ArrayList<LabeledGoodTodoEntity>();
				LabeledGoodTodoEntity labeled = null;
				for (LabeledGoodEntity labeledGoodEntity : entityList) {
					// 生成待办记录
					labeled = new LabeledGoodTodoEntity();
					// ID
					labeled.setId(UUIDUtils.getUUID());
					// 更改单ID
					labeled.setWaybillRfcId(numRfcDto.getWaybillRfcEntity().getId());
					// 待办状态
					labeled.setStatus(FossConstants.NO);
					// 异常信息
					labeled.setExceptionMsg(FossConstants.NO);
					// 设置为未打印
					labeled.setPrinted(FossConstants.NO);
					// 货签信息ID
					labeled.setLabeledGoodId(labeledGoodEntity.getId());
					// 设置创建时间
					labeled.setCreateTime(new Date());
					// 设置流水号
					labeled.setSerialNo(labeledGoodEntity.getSerialNo());
					// 设置为不可见
					labeled.setIsSendRemind(FossConstants.NO);
					// 添加到记录集合
					labelList.add(labeled);
				}
				// 批量新增
				labeledGoodTodoDao.insertSelectiveBatch(labelList);
			}

			// 拼装返回参数
			res.setCode(ResultDto.SUCCESS);
			res.setMsg("");
		} else {
			// 拼装错误参数
			res.setCode(ResultDto.SUCCESS);
			res.setMsg("未查询到有效的运单记录");
		}
		LOG.info("返回给中转的参数的参数为:" + ReflectionToStringBuilder.toString(res));
		LOG.info("非全包装需生成待办数据处理结束");
		return res;
	}

	/**
	 * 拼装更改详情数据
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-5 16:16:09
	 * @param needCreateTodoDto
	 * @param waybill
	 * @param numRfcDto
	 */
	private void addWaybillRfcChangeDetail(NeedCreateTodoDto needCreateTodoDto,
			WaybillEntity waybill, PDAWayBillRfcDto numRfcDto) {
		List<WaybillRfcChangeDetailEntity> changeList = new ArrayList<WaybillRfcChangeDetailEntity>();
		if (needCreateTodoDto.getNewGoodNum().intValue() != needCreateTodoDto.getOldGoodNum().intValue()) {
			numRfcDto.setBusinessChange(true);
			WaybillRfcChangeDetailEntity change = new WaybillRfcChangeDetailEntity();
			change.setRfcItems("goodsQtyTotal");
			change.setRfcItemsName("件数");
			change.setBeforeRfcInfo(String.valueOf(needCreateTodoDto.getOldGoodNum()));
			change.setAfterRfcInfo(String.valueOf(needCreateTodoDto.getNewGoodNum()));
			change.setVisiable(FossConstants.YES);
			LOG.info("更改详情：" + ReflectionToStringBuilder.toString(change));
			changeList.add(change);
		}
		numRfcDto.setChangeDetailList(changeList);
	}

	/**
	 * 拼装更改审核历史记录
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-5 16:16:49
	 * @param needCreateTodoDto
	 * @param waybill
	 * @param numRfcDto
	 */
	private void addWaybillRfcEntity(NeedCreateTodoDto needCreateTodoDto,
			WaybillEntity waybill, PDAWayBillRfcDto numRfcDto) {
		WaybillRfcEntity waybillRfcEntity = new WaybillRfcEntity();
		waybillRfcEntity.setId(UUIDUtils.getUUID());
		waybillRfcEntity.setWaybillNo(needCreateTodoDto.getWaybillNo());
		waybillRfcEntity.setRfcSource(WaybillRfcConstants.INSIDE_REQUIRE);
		waybillRfcEntity.setRfcType(WaybillRfcConstants.PACK_PIECE_CHANGE);
		waybillRfcEntity.setRfcReason("货物非全包装发生货物减少，系统自动变更！");
		waybillRfcEntity.setDraftOrgCode(needCreateTodoDto.getUserOrgCode());
		waybillRfcEntity.setDraftOrgName(needCreateTodoDto.getUserOrgName());
		waybillRfcEntity.setDrafter(needCreateTodoDto.getUserName());
		waybillRfcEntity.setDrafterCode(needCreateTodoDto.getUserCode());
		waybillRfcEntity.setDraftTime(new Date());
		waybillRfcEntity.setNotes("货物非全包装系统自动同意！");
		waybillRfcEntity.setStatus(WaybillRfcConstants.ACCECPT);
		waybillRfcEntity.setOperator(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperatorCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperateOrgCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperateOrgName(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperateTime(new Date());
		waybillRfcEntity.setOldVersionWaybillId(waybill.getId());
		waybillRfcEntity.setNewVersionWaybillId(waybill.getId());
		waybillRfcEntity.setIsFinanceChange(FossConstants.NO);
		// 变更内容
		StringBuffer sb = new StringBuffer();
		if (numRfcDto != null && CollectionUtils.isNotEmpty(numRfcDto.getChangeDetailList())) {
			for (WaybillRfcChangeDetailEntity detailDto : numRfcDto.getChangeDetailList()) {
				if (FossConstants.YES.equals(detailDto.getVisiable())) {
					sb.append(detailDto.getRfcItemsName());
					sb.append(":");
					sb.append(detailDto.getBeforeRfcInfo());
					sb.append("->");
					sb.append(detailDto.getAfterRfcInfo());
					sb.append(";");
				}
			}
		}
		// canshu
		waybillRfcEntity.setChangeItems(sb.toString());
		// 是否给发货人发送短信
		waybillRfcEntity.setDeliverSms(FossConstants.NO);
		// 是否给收货人发送短信
		waybillRfcEntity.setReceiverSms(FossConstants.NO);
		// 是否目的站修改
		waybillRfcEntity.setIsChangeDestination(FossConstants.NO);
		// 是否需要财务核销
		waybillRfcEntity.setNeedWriteOff(FossConstants.NO);
		// 是否变更运单号
		waybillRfcEntity.setIsChangeWaybillNo(FossConstants.NO);
		// 是否自动受理
		waybillRfcEntity.setIsLabourHandle(FossConstants.YES);
		LOG.info("更改数据：" + ReflectionToStringBuilder.toString(waybillRfcEntity));
		if(numRfcDto != null){
			numRfcDto.setWaybillRfcEntity(waybillRfcEntity);
		}
	}

	/**
	 * 拼装更改单数据
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年4月5日 16:16:29
	 * @param needCreateTodoDto
	 * @param waybill
	 * @param numRfcDto
	 */
	private void addWaybillRfActionHistory(NeedCreateTodoDto needCreateTodoDto,
			WaybillEntity waybill, PDAWayBillRfcDto numRfcDto) {
		List<WaybillRfcActionHistoryEntity> rfcActionList = new ArrayList<WaybillRfcActionHistoryEntity>();
		WaybillRfcActionHistoryEntity actionEntity = new WaybillRfcActionHistoryEntity();
		// 自动起草
		actionEntity.setNotes("PDA补录运单时涉及到标签更改的变更项，系统自动起草更改单！");
		actionEntity.setStatus(WaybillRfcConstants.PRE_AUDIT);
		actionEntity.setOperator(needCreateTodoDto.getUserName());
		actionEntity.setOperatorCode(needCreateTodoDto.getUserCode());
		actionEntity.setOperateOrgCode(needCreateTodoDto.getUserOrgCode());
		actionEntity.setOperateOrgName(needCreateTodoDto.getUserOrgName());
		actionEntity.setOperateTime(new Date());
		rfcActionList.add(actionEntity);
		// 自动审核
		actionEntity = new WaybillRfcActionHistoryEntity();
		actionEntity.setNotes("货物非全包装发生货物减少，系统自动审核同意！");
		actionEntity.setStatus(WaybillRfcConstants.PRE_ACCECPT);
		actionEntity.setOperator(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperatorCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperateOrgCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperateOrgName(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperateTime(new Date());
		rfcActionList.add(actionEntity);
		// 自动受理
		actionEntity = new WaybillRfcActionHistoryEntity();
		actionEntity.setNotes("货物非全包装发生货物减少，系统自动受理同意！");
		actionEntity.setStatus(WaybillRfcConstants.ACCECPT);
		actionEntity.setOperator(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperatorCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperateOrgCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperateOrgName(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		actionEntity.setOperateTime(new Date());
		rfcActionList.add(actionEntity);
		LOG.info("更改审核详情：" + ReflectionToStringBuilder.toString(actionEntity));
		numRfcDto.setRfcActionList(rfcActionList);
	}

	/**
	 * 根据运单号修改折扣信息状态
	 * 
	 * @创建时间 2014-5-21 下午6:18:29
	 * @创建人： WangQianJin
	 */
	@Override
	public int updateInactiveByWaybillNo(String waybillNo) {
		WaybillDisDtlEntity record = new WaybillDisDtlEntity();
		record.setActive(FossConstants.INACTIVE);
		record.setWaybillNo(waybillNo);
		record.setType(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
		return waybillDisDtlService.updateByWaybillNoAndType(record);
	}

	/**
	 * 查询该发货客户当天是否收取过接货费
	 * 
	 * @author WangQianJin
	 * @date 2014-05-04
	 */
	@Override
	public boolean queryIsGetPickFeeByCust(WaybillEntity entity) {
		// 定义查询参数
		WaybillEntity queryParm = entity;
		// 定义查询结果
		int queryResult = 0;
		// 设置查询条件
		queryParm.setActive(FossConstants.YES);
		queryParm.setReceiveOrgCode(entity.getReceiveOrgCode());
		// 对于待补录运单 可能没有在运单且中生成记录，故要非空判断
		if (null == entity.getBillTime()) {
			queryParm.setBillTime(new Date());
		}

		// 若有发货客户编码，则忽略使用手机、电话做查询条件
		if (StringUtil.isNotEmpty(entity.getDeliveryCustomerCode())) {
			// 设置手机、电话为空
			queryParm.setDeliveryCustomerMobilephone("");
			queryParm.setDeliveryCustomerPhone("");
			queryResult = waybillDao.queryIsGetPickFeeByCust(queryParm);
		}
		// 无客户编码，有手机号码
		else if (StringUtil.isNotEmpty(entity.getDeliveryCustomerMobilephone())) {
			// 设置电话为空
			queryParm.setDeliveryCustomerPhone("");
			queryResult = waybillDao.queryIsGetPickFeeByCust(queryParm);
		}
		// 只有电话号码
		else {
			// 对电话号码进行解析，并判断是否含有手机号码
			Map<String, List<String>> map = StringHandlerUtil.resolveMobileAndPhone(entity.getDeliveryCustomerPhone());
			List<String> mobileList = map.get("mobiles");
			List<String> phoneList = map.get("phones");
			// 若电话中包含手机号码，则对手机号码进行遍历，查找第一票运单
			if (CollectionUtils.isNotEmpty(mobileList)) {
				for (String mobile : mobileList) {
					queryParm.setDeliveryCustomerPhone(mobile);
					queryResult = waybillDao.queryIsGetPickFeeByCust(queryParm);
					if (queryResult > 0) {
						break;
					}
				}
			} else {
				for (String phone : phoneList) {
					queryParm.setDeliveryCustomerPhone(phone);
					queryResult = waybillDao.queryIsGetPickFeeByCust(queryParm);
					if (queryResult > 0) {
						break;
					}
				}
			}
		}
		// 如果该客户在8点至次日8点收取过规定的接货费，则表示收取过
		if (queryResult > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据活动编码获取活动信息
	 * 
	 * @创建时间 2014-6-12 上午10:09:51
	 * @创建人： WangQianJin
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode) {
		return markActivitiesService.queryMarkActivitiesByCode(activityCode);
	}

	/**
	 * 针对偏线做的一个时效方案的验证，目前主要针对偏线时效否方案进行验证
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 14:05:10
	 */
	@Override
	public boolean identityOuterEffectivePlan(String departDeptCode,
			String arriveDetpCode, String productCode, Date createTime) {
		// 查询走货路径对应的最终配载部门编码
		List<FreightRouteLineDto> freightRouteLineList = freightRouteService.queryFreightRouteBySourceTarget(
				departDeptCode, arriveDetpCode, productCode, createTime);
		// 判断是否非空
		if (CollectionUtils.isNotEmpty(freightRouteLineList)) {
			// 获取最终配载部门编码
			String lastChangeCenterOrgCode = freightRouteLineList.get(freightRouteLineList.size() - 1).getSourceCode();
			OuterEffectivePlanEntity entity = null;
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
				// 获取偏线时效方案是否存在该数据
				entity = outerEffectivePlanService.queryOuterEffectPlanByFieldAndBranch(lastChangeCenterOrgCode,
						arriveDetpCode, ProductEntityConstants.PRICING_PRODUCT_C1_C20002, createTime);
			} else {
				// 获取偏线时效方案是否存在该数据
				entity = outerEffectivePlanService.queryOuterEffectPlanByFieldAndBranch(lastChangeCenterOrgCode,
						arriveDetpCode, ProductEntityConstants.PRICING_PRODUCT_C1_C20001, createTime);
			}
			// 如果非空
			if (entity != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据部门编码与运输性质获取信息数量
	 * 
	 * @param entity
	 * @author WangQianJin
	 * @return
	 */
	@Override
	public int queryCountByCodeAndProduct(SalesProductEntity entity) {
		return salesDepartmentDao.queryCountByCodeAndProduct(entity);
	}

	/**
	 * @author liyongfei 根据运单号查询运单的操作历史
	 * @param waybillNo
	 *            运单号
	 * @param actionItem
	 *            指定的修改项集合，如"goodsQty，licensePlateNum" 表示查询货物件数和车牌号，若为空，则表示全查
	 * @return
	 */
	@Override
	public List<WaybillAcHisPdaEntity> queryWaybillHisByNo(String waybillNo,
			List<String> actionItem) {
		// TODO Auto-generated method stub
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("waybillNo", waybillNo);
		condition.put("actionItem", actionItem);
		return waybillDao.queryWaybillHisByNo(condition);
	}

	/**
	 * 批量激活运单
	 * 
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 *            运单号集合
	 * @return
	 */
	@Override
	public int setWaybillActive(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		return waybillDao.setWaybillActive(waybillNoList);
	}

	/**
	 * 插入图片开单信息
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#submitWaybillPictureByPDA(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity)
	 */
	@Override
	public void submitWaybillPictureByPDA(WaybillPictureEntity waybillPictureEntity) {
		waybillPictureEntity.setActive(WaybillConstants.YES);
		waybillPictureEntity.setCreateTime(new Date());
		waybillPictureEntity.setCreateUserCode(waybillPictureEntity.getDriverCode());
		waybillPictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
		// 作废之前的运单状态
		WaybillPictureEntity queryEntity = new WaybillPictureEntity();
		queryEntity.setWaybillNo(waybillPictureEntity.getWaybillNo());
		queryEntity.setActive(FossConstants.ACTIVE);
		List<WaybillPictureEntity> oldWaybillEntityLst = waybillPictureDao.queryWaybillPictureByEntity(queryEntity);
		// 如果存在，则作废
		if (CollectionUtils.isNotEmpty(oldWaybillEntityLst)) {
			for (WaybillPictureEntity entity : oldWaybillEntityLst) {
				entity.setActive(FossConstants.NO);
				waybillPictureDao.updatePictureWaybillByWaybillno(entity);
			}
		}
		waybillPictureDao.insertWaybillPicture(waybillPictureEntity);

	}

	/**
	 * 查询返货类型
	 */
	@Override
	public String selectReturnType(String waybillno) {

		 ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
		   condition.setOriWaybill(waybillno);
			List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByCondition(condition);

		if (CollectionUtils.isNotEmpty(list)) {
				ReturnGoodsRequestEntity re = null;
			if (list.size() == 1) {
					 re = list.get(0);
			} else if (list.size() > 1) {
				for (ReturnGoodsRequestEntity g : list) {
					if (null == re) {
							re = g;
					} else {
						if (g.getTimeReport().after(re.getTimeReport())) {
								re = g;
							}
						}
					}
				}
			if (re != null && WaybillConstants.ACCEPTSTATUS_HANDLED.equals(re
					.getReturnStatus())) {
					return re.getReturnType();
			} else {
					return null;
				}
			}
			return null;

	}

	/**
	 * 根据当前时间查询当前月份所有内部员工发货的优惠金额 dp-foss-dongjialing 225131
	 */
	@Override
	public BigDecimal queryDiscountFeeByEmployeeNo(String employeeNo,
			Date recevieDate) {
		
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(recevieDate);
		
		/**
		 * @author Foss-278328-hujinyang 20160418  sql优化
		 */
		calendar.set(Calendar.DAY_OF_MONTH,1);
		Date firstMothDay = DateUtils.getStartDatetime(calendar.getTime());
		
		calendar.add(Calendar.MONTH,1);//月增加1天 
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		Date lastMothDay = calendar.getTime();
		
		
		Long discountFee = waybillDao.queryDiscountFee(employeeNo, firstMothDay, lastMothDay);
		if (discountFee == null) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(discountFee);
	}

	/**
	 * 根据当期时间和发货客货编码查询当前月份的发货总金额 dp-foss-dongjialing 225131
	 */
	@Override
	public BigDecimal queryTotalFeeByDelevyCode(String code, Date recevieDate) {
		
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(recevieDate);
		
		calendar.set(Calendar.DAY_OF_MONTH,1);
		Date firstMothDay = DateUtils.getStartDatetime(calendar.getTime());
		
		calendar.add(Calendar.MONTH,1);//月增加1天 
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		Date lastMothDay = calendar.getTime();
		
		Long totalFee = waybillDao.queryTotalFee(code, firstMothDay, lastMothDay);
		if (totalFee == null) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(totalFee);
	}

	/**
	 * 根据运单号查询折扣信息
	 */
	@Override
	public BigDecimal queryDiscountInfo(String waybillNo) {
		String rate = waybillDao.queryDiscountInfo(waybillNo);
		if (StringUtil.isNotBlank(rate)) {
			BigDecimal r = new BigDecimal(rate);
			return r;
		} else {
			return BigDecimal.ZERO;
		}

	}

	/**
	 * 为配合快递100轨迹推送所需的方法
	 * 
	 * @author 220125 yangxiaolong
	 */
	@Override
	public void addOneWaybillTrack(WaybillDto waybilldto) {
		if(waybilldto == null || waybilldto.getCurrentInfo() == null){
			return;
		}
		WaybillTrackingsDto dto = null;
		if(CollectionUtils.isNotEmpty(waybilldto.getWaybillRelateDetailEntityList())){
			for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybilldto.getWaybillRelateDetailEntityList()){
				dto = new WaybillTrackingsDto();
				dto.setWaybillNo(waybillRelateDetailEntity.getWaybillNo());//运单号
				addOneWaybillTrack(waybilldto, dto);
			}
		}else{
			dto = new WaybillTrackingsDto();
	addOneWaybillTrack(waybilldto, dto);
		}
	}
	
	/**
	 * 添加货物轨迹的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 18:35:38
	 * @param waybilldto
	 * @param dto
	 */
	private void addOneWaybillTrack(WaybillDto waybilldto, WaybillTrackingsDto dto){
		dto.setOperateType(WaybillTrackingsConstants. OPERATE_TYPE_CREATE_PDA);
		dto.setOperateDeptCode(waybilldto.getCurrentInfo().getCurrentDeptCode());//开单部门编码          获取的是当前用户所在的部门编码
		dto.setOperateDeptName(waybilldto.getCurrentInfo().getCurrentDeptName()); //开单部门名称        获取的是当前用户所在的部门名称
		dto.setOperateTime(waybilldto.getWaybillEntity().getBillTime());//开单时间          
		dto.setOperatorName(waybilldto.getWaybillEntity().getCreateUserName());//开单人姓名
//		dto.setOperatorPhone(employeeService.queryEmployeeByEmpCode(waybilldto.getCurrentInfo().getEmpCode()).getPhone());//有就给，没有就不给
		dto.setOperatorPhone(employeeService.querySimpleEmployeeByEmpCode(waybilldto.getCurrentInfo().getEmpCode()).getPhone());//有就给，没有就不给
		
		waybillTrackingsService.addOneWaybillTrack(dto);
	}
	@Override
	public void updateWaybill(WaybillEntity waybillEntity) {
		waybillDao.updateWaybill(waybillEntity);
	}

	/**
	 * 配合自动补码
	 * 
	 * @param waybilldto
	 */
	public void autoAddCode(WaybillDto waybilldto) {
		AutoAddCodeEntity entity = new AutoAddCodeEntity();
		if (waybilldto != null && waybilldto.getWaybillEntity() != null
				&& waybilldto.getWaybillExpressEntity() != null) {

			entity.setWaybillNO(waybilldto.getWaybillEntity().getWaybillNo());
	singleAutoAddCode(waybilldto, entity);
			return;
		}
		if(waybilldto != null && waybilldto.getWaybillRelateDetailEntityList() != null){
			for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybilldto.getWaybillRelateDetailEntityList()){
				entity = new AutoAddCodeEntity();
				entity.setWaybillNO(waybillRelateDetailEntity.getWaybillNo());
				singleAutoAddCode(waybilldto, entity);
			}
		}
	}
	
	/**
	 * 添加自动补码的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 18:33:26
	 * @param waybilldto
	 * @param entity
	 */
	private void singleAutoAddCode(WaybillDto waybilldto, AutoAddCodeEntity entity){
		entity.setReceiveCustomerProvCode(waybilldto.getWaybillEntity().getReceiveCustomerProvCode());
		entity.setReceiveCustomerCityCode(waybilldto.getWaybillEntity().getReceiveCustomerCityCode());
		entity.setReceiveCustomerDistCode(waybilldto.getWaybillEntity().getReceiveCustomerDistCode());
		entity.setReceiveCustomerTownCode(waybilldto.getActualFreightEntity().getReceiveCustomerVillageCode());
		entity.setBillTime(waybilldto.getWaybillEntity().getBillTime());
		entity.setCreateOrgCode(waybilldto.getWaybillEntity().getCreateOrgCode());
		entity.setCustomerPickupOrgCode(waybilldto.getWaybillEntity().getCustomerPickupOrgCode());
		entity.setId(waybilldto.getWaybillEntity().getId());
	entity.setReceiveCustomerAddress(waybilldto.getWaybillEntity().getReceiveCustomerAddress());
		entity.setTargetOrgCode(waybilldto.getWaybillEntity().getTargetOrgCode());
		entity.setWaybillID(waybilldto.getWaybillExpressEntity().getWaybillId());
		if(waybilldto.getReceiveCustomerAreaDto() != null){				
			entity.setReceiveCustomerProvName(waybilldto.getReceiveCustomerAreaDto().getProvinceName());
			entity.setReceiveCustomerCityName(waybilldto.getReceiveCustomerAreaDto().getCityName());
			entity.setReceiveCustomerDistName(waybilldto.getReceiveCustomerAreaDto().getCountyName());
			entity.setReceiveCustomerTownName(waybilldto.getReceiveCustomerAreaDto().getVillageTownName());
		}
		try {
			autoAddCodeService.save(entity);
		} catch (Exception e) {
	try {
					LOG.info("自动补码中转插入数据异常");				
					e.printStackTrace();
	} catch (Exception e1) {
				LOG.info("自动补码中转插入数据异常");
					e1.printStackTrace();			
		}
	}
	}

	/**
	 * 获取对应提货网点名称
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:21:51
	 */
	@Override
	public String getCustomerPickUpNameByCode(String customerPickUpCode, String productCode, Date billTime) {
		String customerPickUpName = null;
		//偏线、空运
		if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode) 
				|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)){
			AgencyBranchOrCompanyDto dto = vehicleAgencyDeptService.queryAgencyBranchCompanyInfo(customerPickUpCode);
			if(dto != null){
				customerPickUpName = dto.getAgentDeptName();
			}
			//快递
		}else if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, billTime)){
			OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(customerPickUpCode,FossConstants.ACTIVE);
			if(companyDto != null){
				customerPickUpName = companyDto.getAgentDeptName();
			}else{
				customerPickUpName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(customerPickUpCode);
			}
		}else{
			//其他产品类型，主要针对汽运
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(customerPickUpCode, billTime);
			if(orgAdministrativeInfoEntity != null){
				customerPickUpName = orgAdministrativeInfoEntity.getName();
			}
		}
		return customerPickUpName;
	}
		/**
	 * 添加对应的子母单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-21 14:02:47
	 * @param waybillDto
	 * @param systemDto
	 */
	public void submitWaybillRelateDetail(WaybillDto waybillDto, WaybillSystemDto systemDto){
		//判定子母件关系表是否存在数据
		if(waybillDto == null || CollectionUtils.isEmpty(waybillDto.getWaybillRelateDetailEntityList())){
			return;
		}

	List<WaybillEntity> waybillEntityList=waybillDto.getWaybillEntityList();
		WaybillEntity waybillEntity=waybillDto.getWaybillEntity();
		waybillEntityList.add(waybillEntity);
		WaybillExpressEntity waybillExpress = waybillDto.getWaybillExpressEntity();
		String parentWaybillNo =waybillEntity.getWaybillNo();
		String waybillNo = null;
		String orderNo = null;
		ActualFreightEntity actualFreightEntity = null;
		ActualFreightEntity actualFreightforUpdate = null;
		String oldWaybillNo = null;
		boolean isPdaPending = false;
		String waybillStatus = null;
		String waybillId = null;
		for(WaybillRelateDetailEntity waybillRelateEntity : waybillDto.getWaybillRelateDetailEntityList()){
			//如果是母单，不需要对这些数据的添加
			if(StringUtils.isNotEmpty(parentWaybillNo) && parentWaybillNo.equals(waybillRelateEntity.getWaybillNo())){
				continue;
			}
			//重新设置wayBillEntity
			waybillEntity=new WaybillEntity();
			BeanUtils.copyProperties(waybillDto.getWaybillEntity(), waybillEntity);
			//运单号
			waybillNo = waybillRelateEntity.getWaybillNo();
			//订单号
			orderNo = waybillRelateEntity.getOrderNo();
			//运单实体从新设置运单号和订单号
			waybillEntity.setWaybillNo(waybillNo);
			waybillEntity.setOrderNo(orderNo);
			//设置子单的重量体积
			waybillEntity.setGoodsVolumeTotal(waybillRelateEntity.getVolume());
			waybillEntity.setGoodsWeightTotal(waybillRelateEntity.getWeight());
			
			waybillExpress.setWaybillNo(waybillNo);
			//实际承运信息表数据的添加
			actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
			//实际承认表子单费用清0
			actualFreightforUpdate=new ActualFreightEntity();
			BeanUtils.copyProperties(waybillDto.getActualFreightEntity(), actualFreightforUpdate);
			cleanActualFreightFeeZero(actualFreightforUpdate,BigDecimal.ZERO);
			if (actualFreightEntity != null && WaybillConstants.EWAYBILL.equals(actualFreightEntity.getWaybillType())) {
				actualFreightforUpdate.setWaybillNo(waybillNo);
				// 设置记录创建时间
				actualFreightforUpdate.setCreateTime(systemDto.getBillTime());
				//设置修改时间为当前时间
				actualFreightforUpdate.setModifyTime(systemDto.getModifyTime());
				// 设置为有效
				actualFreightforUpdate.setStatus(WaybillConstants.EFFECTIVE);
				// 老运单号
				oldWaybillNo = StringUtil.defaultIfNull(waybillNo);
				// 不为空时，则更新
				actualFreightService.updateByWaybillNo(actualFreightforUpdate, oldWaybillNo);
			} else {
				actualFreightforUpdate.setId(UUIDUtils.getUUID());
				actualFreightforUpdate.setWaybillNo(waybillNo);
				// 设置记录创建时间
				actualFreightforUpdate.setCreateTime(systemDto.getBillTime());
				//设置修改时间为当前时间
				actualFreightforUpdate.setModifyTime(systemDto.getModifyTime());
				// 设置为有效
				actualFreightforUpdate.setStatus(WaybillConstants.EFFECTIVE);
				actualFreightService.insertWaybillActualFreight(actualFreightforUpdate);
			}
			//设置子单费用、重量、体积为0
			cleanWayBillFeeZero(waybillEntity, BigDecimal.ZERO);
			waybillEntity.setOrderNo(waybillRelateEntity.getOrderNo());
			waybillEntity.setPendingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);
			// 判断是否是PDA补录
			if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(waybillEntity.getPendingType())) {
				// 若老运单号为空，则不允许提交
				if (StringUtils.isEmpty(waybillNo)) {
					LOG.error("PDA补录时，老运单号不能为空！");
					throw new WaybillSubmitException("PDA补录时，老运单号不能为空！");
				}
				waybillEntity.setWaybillNo(waybillNo);
				waybillEntity.setOrderNo(orderNo);				
				// 若PDA运单 ，设置运单开单时间为pda开单的那个时间
				waybillEntity.setBillTime(waybillEntity.getBillTime());
				// 设置运单类型为PDA补录
				waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE);

				//PDA提交时，t_srv_waybill、t_srv_actualFreiht、t_srv_labelGoods表即使没有插入成功PDA也能成功提交
				//因此还需要判断t_srv_waybill是否有值
				isPdaPending = isWayBillExsitsPDAPending(waybillNo);
				if (isPdaPending) {
					if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {
						WaybillEntity oldEntity = waybillDao.queryWaybillByNo(waybillNo);
						if (!StringUtils.isEmpty(waybillEntity.getCustomerPickupOrgCode())
								&& oldEntity != null && !StringUtils.isEmpty(oldEntity.getCustomerPickupOrgCode())
								&& !waybillEntity.getCustomerPickupOrgCode().equals(oldEntity.getCustomerPickupOrgCode())) {
							// 老的目的站和新的补录以后目的站不同的时候 就调用该方法
							calculateTransportPathService.changeDestinationPathForRecordAgain(oldEntity.getWaybillNo(), waybillEntity.getCustomerPickupOrgCode());
						}
					}
					waybillDao.updateWaybillByWaybillNo(waybillEntity, waybillNo);
					//根据运单号查询运单ID,因为生成运单后没有返回ID，因此需要再查询一次后将ID返回并保存到waybillEntity中提供给结算使用，否则会报错错误信息
					WaybillEntity newWaybillEntity = waybillDao.queryWaybillByNo(StringUtil.defaultIfNull(waybillEntity.getWaybillNo()));
					// 判断查询出的对象是否为空
					if (null == newWaybillEntity) {
						throw new WaybillSubmitException("\n运单ID未查询到！");
					} else {
						waybillId = newWaybillEntity.getId();
					}
				} else {
					waybillId = waybillDao.addWaybillEntity(waybillEntity);
					waybillEntityList.add(waybillEntity);
				}
			} else {
				waybillEntity.setWaybillNo(waybillNo);
				// 设置统一的运单开单时间
				waybillEntity.setBillTime(systemDto.getBillTime());
				// 设置运单类型为PC开单
				waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE);
				waybillId = waybillDao.addWaybillEntity(waybillEntity);
			}
			waybillDto.setWaybillEntity(waybillEntity);
			// 设置运单ID
			waybillDto.getWaybillEntity().setId(waybillId);
			// 是否为整车，若为整车则不生成中转线路信息
			if (FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
				LOG.info("submitWaybill:进入整车生成库存部分");
				// 生成货签信息和库存信息
				addLabeledGood(waybillDto, systemDto, isPdaPending);
			} else {
				LOG.info("submitWaybill:进入生成库存部分");
				// 中转接口：生成中转线路信息
				addLine(waybillDto, isPdaPending);
				// 生成货签信息
				pdaScanService.addLabelGoodsInfo(waybillDto.getWaybillEntity(), systemDto);
				// 生成库存信息（电子运单二期）
				pdaScanService.waybillInStockByWaybillNo(waybillNo);
			}

			// 若为签收单返单类型，则需要生成签收单信息
			if (!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())) {
				// 生成签收单信息
				addSignReturnBill(waybillDto);
			}

			// 新增业务标识:首先判定运单号是否存在
			boolean isWaybillTransactionExist = waybillTransactionService.isExistWaybillTransaction(waybillNo);
			//如果不存在需要进行数据的添加
			if(!isWaybillTransactionExist){
				// 运单终结状态对象
				WaybillTransactionEntity transacionStat = new WaybillTransactionEntity();
				// 主键
				transacionStat.setId(UUIDUtils.getUUID());
				// 运单号
				transacionStat.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
				// 业务完结情况
				transacionStat.setBusinessOver(ZEROSTR);
				// 财务完结情况
				transacionStat.setFinanceOver(ZEROSTR);
				// 创建日期
				transacionStat.setCreateDate(new Date());
				// 创建人
				transacionStat.setCreateUser(FossUserContext.getCurrentUser().getId());
				// 新增业务标识
				waybillTransactionService.addWaybillTransaction(transacionStat);
			}

			// 待补录运单信息
			WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryPendingByNo(waybillNo);
			if(null == waybillPendingEntity){
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("waybillNo", waybillNo);
				maps.put("active", FossConstants.NO);
				maps.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
				maps.put("waybillType", WaybillConstants.EWAYBILL);
				waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(maps);
			}

			// 判断是否是PDA补录或者运单暂存补录
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)
					|| WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillStatus)
					|| WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING.equals(waybillStatus)) {
				// 更新待处理运单记录信息
				if(waybillPendingEntity!=null){
				updateEWaybillData(waybillDto, waybillPendingEntity, waybillStatus);
				}
			}

			// 是否为弃货处理运单
			if (WaybillConstants.WAYBILL_STATUS_GOODS_PENDING.equals(waybillStatus)) {
				// 更新弃货处理运单记录信息，标识待弃货运单为N,更新弃货运单号储运事项为新运单号
				updateAbandonedGoodsData(waybillDto.getWaybillEntity());
			}

			// 处理待办事务
			handleToDoMsg(waybillDto);

			// 保存待处理发送短信信息，执行异步定时任务,子单不推送信息
			//savePendingSendMailInfo(waybillDto);

			// 更新约车状态
			updateVehicleState(waybillDto);

			// 快递冗余信息
			if (waybillDto.getWaybillExpressEntity() != null) {
				addWaybillExpress(waybillDto, systemDto);
			}

			// 如果是PDA补录的单，则判断是否需要生成待办（和更改单处理待办逻辑基本一致，只是PDA补录的单，如果修改了信息，更改状态都是自动审核、受理同意）
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
				// 处理PDA补录单的待办信息
				handlePDAPendingTODO(waybillDto, waybillPendingEntity);
				// 更新GUI中PDA待补录信息
				updateTodoMsgStatus(waybillDto);
			}
			// 激活完成后，更新订单表状态
			dispatchOrderEntityDao.updateSatusByOrderNo(orderNo, WaybillConstants.WAYBILL);
			// 激活成功，删除电子订单
			ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(orderNo);
			// 为配合快递100轨迹推送所需的方法
			addOneWaybillTrack(waybillDto);
			
			//FOSS运单号同步给悟空，验证运单号是否存在(母件在上层已经调用，放在OMS异步接口调用之前)
			pushWaybillNo2Ecs(waybillDto);
			
			// 订单号是否为空
			if (StringUtil.isNotBlank(waybillDto.getWaybillEntity().getOrderNo())) {
				// 订单号不为空则更新CRM订单状态
				orderService.updateOrderInfo(waybillDto.getWaybillEntity());
				Log.info("电子运单提交更新订单状态，订单号为"+waybillDto.getWaybillEntity().getOrderNo());
			}
		}
	}

	/**
	  * @description 设置子单一切费用、总量、提交为0
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-24 t下午12:23:53
	  * @param waybillEntity
	  * @param zero
	  * void
	   */
	public static void cleanWayBillFeeZero(WaybillEntity waybillEntity,BigDecimal zero) {
		//设置保价费
		waybillEntity.setInsuranceFee(zero);
		//保价费
		waybillEntity.setInsuranceAmount(zero);
		//代收货款
		waybillEntity.setCodAmount(zero);
		//代收货款
		waybillEntity.setCodFee(zero);
		//预付费保密--详细信息:Y是N否
		//waybillEntity.setSecretPrepaid(zero);
		//到付金额
		waybillEntity.setToPayAmount(zero);
		//预付金额
		waybillEntity.setPrePayAmount(zero);
		//送货费
		waybillEntity.setDeliveryGoodsFee(zero);
		//其他费
		waybillEntity.setOtherFee(zero);
		//包装手续费
		waybillEntity.setPackageFee(zero);
		//优惠费用
		waybillEntity.setPromotionsFee(zero);
		//公布价运费
		waybillEntity.setTransportFee(zero);
		//增值服务费
		waybillEntity.setValueAddFee(zero);
		//总费用
		waybillEntity.setTotalFee(zero);
		//接货费
		waybillEntity.setPickupFee(zero);
		//劳务费
		waybillEntity.setServiceFee(zero);
		//总重量
		//waybillEntity.setGoodsWeightTotal(zero);
		//总体积
		//waybillEntity.setGoodsVolumeTotal(zero);
	}
	
	/**
	  * @description 设置实际承运子单一切费用、总量、提交为0
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-24 t下午12:23:53
	  * @param waybillEntity
	  * @param zero
	  * void
	   */
	private void cleanActualFreightFeeZero(ActualFreightEntity actualFreightEntity, BigDecimal zero) {
		// 重量
		//actualFreightEntity.setWeight(zero);
		// 体积
		//actualFreightEntity.setVolume(zero);
		// 保险声明价值
		actualFreightEntity.setInsuranceValue(zero);
		// 包装费
		actualFreightEntity.setPackingFee(zero);
		// 送货费
		actualFreightEntity.setDeliverFee(zero);
		// 装卸费
		actualFreightEntity.setLaborFee(zero);
		// 代收货款
		actualFreightEntity.setCodAmount(zero);
		// 增值费
		actualFreightEntity.setValueaddFee(zero);
		// 公布价运费
		actualFreightEntity.setFreight(zero);
	}

//	/**
//	 * 添加货签信息
//	 * @param pendingEntity
//	 * @param wood
//	 * @return
//	 */
//	public List<LabeledGoodEntity> addLabeledGood(WaybillEntity pendingEntity,WoodenRequirementsEntity wood) {
//    	List<LabeledGoodEntity> labeledGoodList = new ArrayList<LabeledGoodEntity>();
//    	if(pendingEntity !=null) {
//    		if(pendingEntity.getGoodsQtyTotal()>0) {
//    			for (int i = 1; i <= pendingEntity.getGoodsQtyTotal(); i++) {
//    				
//    			 LabeledGoodEntity	labeledGood = new LabeledGoodEntity();
//    				// id
//    				labeledGood.setId(UUIDUtils.getUUID());
//    				labeledGood.setWaybillNo(pendingEntity.getWaybillNo());
//    				labeledGood.setActive(FossConstants.ACTIVE);
//    				labeledGood.setBillTime(pendingEntity.getBillTime());
//    				labeledGood.setCreateTime(new Date());
//    				labeledGood.setModifyTime(new Date());
//    				// 流水号
//    				labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), 4, "0"));
//    				labeledGood.setInitalVale(FossConstants.YES);
//    				if(null!=wood && wood.getStandGoodsNum()>0 && wood.getStandGoodsNum()>=i) {
//    					labeledGood.setIsNeedWooden(FossConstants.YES);
//    				}    				
//    				//labeledGood.setNumberChangItems(labeled.getNumberChangItems());
//    				//labeledGood.setOldSerialNo(labeled.getOldSerialNo());
//    				//zxy 20131118 ISSUE-4391 包装类型，目前只有木托=SALVER
//    				if(null!=wood &&wood.getSalverGoodsNum()>0 && wood.getSalverGoodsNum()>=i){
//    					labeledGood.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
//    				}    								    				    				
//    				
//    				labeledGoodList.add(labeledGood);
//    			}
//    		}
//    	}
//		return labeledGoodList;
//    }
	
	/**
	 * 根据客户编码查询客户信息
	 */
	@Override
	public CustomerEntity queryCustInfoByCustomerEntity(CustomerEntity customerEntity) {
		return customerService.queryCustInfoByCustomerEntity(customerEntity);
	}

	@Override
	public DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode, String valueCode) {
		return pkpdataDictionaryValueDao.queryDataDictoryValueByCode(termsCode,
				valueCode);
	}
	 /**
		 * 快递自动补录提交运单
		 * 快递自动补录中有些逻辑不需要，为避免影响其他业务，将提交方法独立出来，便于修改
		 * @author Foss-yangxiaolong
		 * @date 2015-05-20
		 * @param waybill
		 * @return Result
		 */
		@Transactional
		@Override
		public void submitExpressWaybill(WaybillDto waybillDto){
			if(waybillDto == null){
				throw new PdaInterfaceException("参数waybillDto为空！");
			}
			boolean isPdaPending = false;
			UserEntity userEntity = new UserEntity();
			if (waybillDto !=null && waybillDto.getWaybillEntity() != null &&waybillDto.getWaybillEntity().getDriverCode() != null) {
				userEntity.setEmpCode(waybillDto.getWaybillEntity().getDriverCode());
				EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(waybillDto.getWaybillEntity().getDriverCode());
				if (null == employEntity && waybillDto.getWaybillEntity().getCreateUserCode() != null) {
					employEntity = employeeService.queryEmployeeByEmpCode(waybillDto.getWaybillEntity().getCreateUserCode());
				}
				if (employEntity != null) {
					userEntity.setEmpName(employEntity.getEmpName());
					userEntity.setEmpCode(employEntity.getEmpCode());
					userEntity.setEmployee(employEntity);
					// 转换成HttpServletRequest
					LOG.info("创建模拟Session开始");
					javax.servlet.http.HttpSession session = new MockHttpSession();
					// 初始化sessionContext
					SessionContext.setSession(session);
					if (StringUtil.isNotBlank(waybillDto.getCurrentInfo().getCurrentDeptCode())) {
						SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",(Object) waybillDto.getCurrentInfo().getCurrentDeptCode());
					}
					if (StringUtil.isNotBlank(waybillDto.getCurrentInfo().getCurrentDeptName())) {
						SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME", (Object) waybillDto.getCurrentInfo().getCurrentDeptName());
					}
				} else {
					throw new PdaInterfaceException("查询不到该司机！");
				}
				LOG.info("创建模拟Session结束");

			}
			UserContext.setCurrentUser(userEntity);
			// 校验运单是否符合业务规则
			verifyWaybill(waybillDto);
			// 获取运单处理状态
			String waybillStatus = StringUtil.defaultIfNull(waybillDto
					.getWaybillEntity().getPendingType());

			// 设置统一的创建人、创建时间、更新人、更新时间
			WaybillSystemDto systemDto = new WaybillSystemDto();
			// 设置当前用户信息
			CurrentInfo currentInfo = waybillDto.getCurrentInfo();
			systemDto.setCurrentUser(currentInfo);

			// 设置修改时间
			systemDto.setModifyTime(new Date());
			// 设置创建时间
			if (WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING
					.equals(waybillStatus)) {
				// 若为离线导入开单，则使用离线客户端本地时间
				Date createTime = waybillDto.getWaybillEntity().getCreateTime();
				// 判断创建时间是否为空
				if (null == createTime) {
					createTime = new Date();
				}
				systemDto.setCreateTime(createTime);
			} else {
				systemDto.setCreateTime(new Date());
			}

			// 设置开单时间
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
				WaybillPendingEntity pend = waybillPendingService
						.getWaybillPendingEntityByWaybillNo(waybillDto
								.getOldWaybillNo());
				if (pend != null) {
				// 若为PDA导入开单，则使用PDA时间
				Date billTime = waybillDto.getWaybillEntity().getBillTime();
				// 判断开单时间是否为空
				if (null == billTime) {
					billTime = new Date();
				}
				systemDto.setBillTime(billTime);
					if (null != waybillDto.getWaybillEntity()) {
						waybillDto.getWaybillEntity().setBillTime(billTime);
					}
			} else {
				systemDto.setBillTime(new Date());
					if (null != waybillDto.getWaybillEntity()) {
						waybillDto.getWaybillEntity().setBillTime(new Date());
			}
				}
			} else {
				systemDto.setBillTime(new Date());
				if (null != waybillDto.getWaybillEntity()) {
					waybillDto.getWaybillEntity().setBillTime(new Date());
				}
			}

			// 定义运单基本信息对象
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();

			setExpChangeVolume(waybillDto);
			// 根据运输性质设置是否快递字段的值
			if (WaybillConstants
					.directDetermineIsExpressByProductCode(waybillEntity
							.getProductCode())) {
				waybillDto.setIsExpress(FossConstants.YES);
			} else {
				waybillDto.setIsExpress(FossConstants.NO);
			}
			// 修改后的运输性质
			String productCode = waybillEntity.getProductCode();

			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
					.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE
							.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_PCP
							.equals(productCode)
					|| StringUtil
							.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG,
									productCode)
					|| StringUtil
							.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG,
									productCode)
					|| StringUtil
							.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG,
									productCode)
					|| StringUtil
							.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG,
									productCode)
					|| StringUtil
							.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR,
									productCode)
					|| StringUtil
							.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD,
									productCode)) {
				waybillEntity.setLastLoadOrgCode(waybillEntity
						.getCustomerPickupOrgCode());
				waybillEntity.setLastLoadOrgName(waybillEntity
						.getCustomerPickupOrgName());
			}
			if (StringUtils.isEmpty(waybillDto.getWaybillEntity()
					.getCustomerPickupOrgName())) {
				String prdCode = waybillDto.getWaybillEntity().getProductCode();
				String orgcode = waybillDto.getWaybillEntity()
						.getCustomerPickupOrgCode();
				if (!StringUtils.isEmpty(orgcode)) {
					String customerPickupOrgName = waybillDao
							.queryCustomerPickupOrgNameByWayno(orgcode, prdCode);
					waybillDto.getWaybillEntity().setCustomerPickupOrgName(
							customerPickupOrgName);
				}
			}
			if (StringUtils.isEmpty(waybillDto.getWaybillEntity()
					.getReceiveOrgName())) {
				String orgcode = waybillDto.getWaybillEntity().getReceiveOrgCode();
				String receiveOrgName = waybillDao
						.queryReceiveOrgNameByWayno(orgcode);
				waybillDto.getWaybillEntity().setReceiveOrgName(receiveOrgName);
			}

			// 重新赋值
			if (StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
	/**
				 * DEFECT-824 部分营业部反馈客户网单无法在线跟踪运单详情
				 */
				ActualFreightEntity actualFreightEntity = waybillDto
						.getActualFreightEntity();
				if (actualFreightEntity != null) {
					// 若运单绑定的订单为官网订单，且运单状态为待补录的状态时，需要通过订单号查一遍订单信息，将官网登陆名查询出来
					if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE
							.equals(waybillEntity.getOrderChannel())
							&& StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
						// 待补录状态
						if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING
								.equals(waybillStatus)
								|| WaybillConstants.WAYBILL_STATUS_PC_PENDING
										.equals(waybillStatus)) {
							// 根据订单号查询订单信息
							CrmOrderDetailDto orderDetailVo = crmOrderService
									.importOrder(StringUtil
											.defaultIfNull(waybillEntity
													.getOrderNo()));
							// 非空判断
                            //抽取方法
                            setWaybillTypeMethod(waybillDto, waybillEntity, actualFreightEntity, orderDetailVo);
				}
					}
				}
				waybillDto.setActualFreightEntity(actualFreightEntity);
			}

			//新增：运单如果失效且是6月1号前导入的数据，则删除ActualFreight数据
			String waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
			// 根据运单号查询
			if (actualFreightService.isExistActualFreight(waybillNo)) {
				WaybillEntity we = waybillDao.queryRecentWaybillByNo(waybillNo);
				// 如果此运单存在ActualFreight，先判断此运单是否失效且是系统上线前导入的数据，如果是则可以删除ActualFreight
				if (we == null
						|| (FossConstants.NO.equals(we.getActive()) && we
								.getBillTime().before(
										DateUtils.convert("2013-06-01")))) {
					actualFreightService.deleteActualFreightByWaybillNo(waybillNo);
				}
			}
			/**
			 *  系统开发生成客户编码 
			 */
			try {
			procDeliverAndReceiveCustomer(waybillDto);
			} catch (Exception e) {
				throw new WaybillValidateException("该手机号码已经存在于系统中有效联系人，不能重复使用，手机号码必须唯一");
			}
			// 生成运单完整信息
			isPdaPending = addWaybill(waybillDto, systemDto, isPdaPending);

			waybillEntity = waybillDto.getWaybillEntity();

			// 是否为整车，若为整车则不生成中转线路信息
			if (FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
				LOG.info("submitWaybill:进入整车生成库存部分");
				// 生成货签信息和库存信息
				addLabeledGood(waybillDto, systemDto, isPdaPending);
			} else {
				LOG.info("submitWaybill:进入生成库存部分");
				// 中转接口：生成中转线路信息
				addLine(waybillDto, isPdaPending);
				// 生成货签信息和库存信息
				addLabeledGood(waybillDto, systemDto, isPdaPending);

			}

			// 若为签收单返单类型，则需要生成签收单信息
			if (!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity
					.getReturnBillType())) {
				// 生成签收单信息
				addSignReturnBill(waybillDto);
			}

			// 当打木架信息不为空时，才调用中转接口,（并且木架木托木箱至少有一个不为0时才调用中转接口--lianhe--2017年3月10日14:12:51）
			if (waybillDto.getWoodenRequirementsEntity() != null 
				&&((waybillDto.getWoodenRequirementsEntity().getStandGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getStandGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum() != 0))) {
				// 中转接口：生成包装信息
				woodenRequirementsService.addPackagingRequire(waybillDto);
			}

			// 内部带货不调用财务接口（已与周斌文确认2013-04-17）
			String pickupMode = StringUtil.defaultIfNull(waybillEntity
					.getReceiveMethod());
			if (!pickupMode.equals(WaybillConstants.INNER_PICKUP)
					&& !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
				// 结算接口：生成结算相关财务单据
				addSettleBill(waybillDto);
			}

			// 运单冗余信息新增
			addActualFreightInfo(waybillDto, systemDto, isPdaPending);
			
			// 新增特殊增值服务标示和相关数据  mabinliang-254615-foss
			addSpecialValueAddedServiceData(waybillDto);

			// 新增业务标识
			//addWaybillTransactionStat(waybillDto);

			// 待补录运单信息
			WaybillPendingEntity waybillPendingEntity = null;
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
				waybillPendingEntity = waybillPendingService
						.getWaybillPendingEntityByWaybillNo(waybillDto
								.getOldWaybillNo());


				//新增:PDA补录时修改了重量体积需要调用中转更新相关信息
				if (!WaybillConstants
						.directDetermineIsExpressByProductCode(waybillEntity
								.getProductCode())
						&& waybillPendingEntity != null) {
					BigDecimal preVolume = waybillPendingEntity
							.getGoodsVolumeTotal();
					BigDecimal preWeight = waybillPendingEntity
							.getGoodsWeightTotal();
					BigDecimal curVolume = waybillEntity.getGoodsVolumeTotal();
					BigDecimal curWeight = waybillEntity.getGoodsWeightTotal();
					MakeUpWaybillEntity makeUpWaybillEntity = new MakeUpWaybillEntity();
					// 只要重量和体积有一个修改了就传给中转进行刷新
					if ((preVolume == null || preVolume.compareTo(curVolume) != 0)
							|| (preWeight == null || preWeight.compareTo(curWeight) != 0)) {
						makeUpWaybillEntity.setVolume(curVolume);
						makeUpWaybillEntity.setWeight(curWeight);
						makeUpWaybillEntity.setWaybillNo(waybillNo);
						makeUpWaybillEntity
								.setMakeUpTime(systemDto.getModifyTime());
						makeUpWaybillEntity.setQuantity(new BigDecimal(
								waybillEntity.getGoodsQtyTotal()));
						// 通知中转重量体积变更，将变更信息放入中转跑批表
						makeUpWaybillService
								.addWaybillInfoForTfr(makeUpWaybillEntity);
					}
				}
			}

			// 判断是否是PDA补录或者运单暂存补录
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)
					|| WaybillConstants.WAYBILL_STATUS_PC_PENDING
							.equals(waybillStatus)) {
			// 更新待处理运单记录信息
			   updatePendingData(waybillDto, waybillStatus);
			}
			// 是否为弃货处理运单
			if (WaybillConstants.WAYBILL_STATUS_GOODS_PENDING.equals(waybillStatus)) {
				// 更新弃货处理运单记录信息，标识待弃货运单为N,更新弃货运单号储运事项为新运单号
				updateAbandonedGoodsData(waybillDto.getWaybillEntity());
			}
			
			// 待补录运单信息
			WaybillPendingEntity waybillpending = waybillPendingService
					.queryWaybillPendingEntityNo(waybillDto.getOldWaybillNo());
			if (waybillpending != null) {
				waybillDto.setReceiveOrgCode(waybillpending.getCreateOrgCode());
			}
			// 处理待办事务
			handleToDoMsg(waybillDto);

			// 保存待处理发送短信信息，执行异步定时任务
			savePendingSendMailInfo(waybillDto);
         //后面优化提交方法新加
			// 只针对精准汽运（长/短途）、精准卡航、精准城运----保存待处理返券信息，执行异步定时任务----206860
			if (waybillDto != null) {// Online-定价体系优化项目DJTWO-63
				if (WaybillConstants.LRF_FLIGHT.equals(waybillDto
						.getWaybillEntity().getProductCode())
						|| WaybillConstants.SRF_FLIGHT.equals(waybillDto
								.getWaybillEntity().getProductCode())
						|| WaybillConstants.TRUCK_FLIGHT.equals(waybillDto
								.getWaybillEntity().getProductCode())
						|| WaybillConstants.FSF_FLIGHT.equals(waybillDto
								.getWaybillEntity().getProductCode())) {
					// 只有发货客户存在手机号码时，才做降价返券处理...
					if (StringUtil.isNotEmpty(waybillDto.getWaybillEntity()
							.getDeliveryCustomerMobilephone())) {
						// 当提货方式不为内部带货时，才做降价返券处理
						if (StringUtil.isNotBlank(waybillDto.getWaybillEntity()
								.getReceiveMethod())) {
							if (!waybillDto.getWaybillEntity().getReceiveMethod()
									.contains("INNER")) {
								makePendingSendCoupon(waybillDto);
							}
						}
					}
				}
			}

			// 更新约车状态
			updateVehicleState(waybillDto);

			// 判断优惠券对象是否为空
			if (waybillDto.getCouponInfoDto() != null) {
				// 使用优惠券
				useCoupon(waybillDto);
			}

			// 快递冗余信息
			if (waybillDto.getWaybillExpressEntity() != null) {
				addWaybillExpress(waybillDto, systemDto);
			}
			// 增加定人定区地址(放在最后面)  == 新加
			addReceiveAddressRfc(waybillEntity);

			// 如果是PDA补录的单，则判断是否需要生成待办（和更改单处理待办逻辑基本一致，只是PDA补录的单，如果修改了信息，更改状态都是自动审核、受理同意）
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)
					&& waybillPendingEntity != null) {
				// 处理PDA补录单的待办信息
				handlePDAPendingTODO(waybillDto, waybillPendingEntity);
				// 更新GUI中PDA待补录信息
				updateTodoMsgStatus(waybillDto);
			}
			// 为配合快递100轨迹推送所需的方法
			addOneWaybillTrack(waybillDto);
			// 配合自动补码
			if (waybillDto.getWaybillEntity() != null
					&& WaybillConstants.YES.equals(waybillDto.getWaybillEntity()
							.getIsExpress())) {
				autoAddCode(waybillDto);
					}
			//如果特殊增值服务标识不为空，且 为（家具送装，建材送装，家电送装）中的一种，则保存家装运单信息
			if(!StringUtil.isBlank(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) &&
					(WaybillConstants.FURNITURE_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) ||
					 WaybillConstants.BUILD_MATERIAL_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) ||
					 WaybillConstants.HOME_APPLICATION_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()))){
				waybillHomeImpService.saveWaybillHomeImpInfo(waybillDto);
			}
			
			//FOSS运单号同步给悟空，验证运单号是否存在(OMS异步接口之前)
			pushWaybillNo2Ecs(waybillDto);
			
			// 订单号是否为空
			if (StringUtil.isNotBlank(waybillDto.getWaybillEntity().getOrderNo())) {
				// 订单号不为空则更新订单状态
				orderService.updateOrderInfo(waybillDto.getWaybillEntity());
			}
		}
	/**
	 * 通过调用GIS的详细地址匹配接口查询是否展会货
	 * @author foss-218438
	 */
	@Override
	public String isExhibitCargo(ExhibitionKeywordEntity exhibitionKeyword) {
		return gisQueryService.isExhibitCargo(exhibitionKeyword);
	}
	
	@Override
	public int updateRfcTranferEntity(WaybillRfcTranferEntity rfcTranfer) {
		return waybillRfcDao.updateRfcTranferEntity(rfcTranfer);
	}

	@Override
	public int updateRfcTranferRfcId(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		return waybillRfcDao.updateRfcTranferRfcId(waybillRfcTranferEntity);
	}

	/**
	 * (非 Javadoc)
	 * 
		* @author 270293-foss-zhangfeng
	 * @date 2015-7-29 下午6:03:27
	 *       <p>
	 *       Title: isSendMessage
	 *       </p>
	 *       <p>
	 *       Description:判断一个客户是否需要发送短信
	 *       1.当CRM勾选“客户作为收件人短信停发”时，停止对客户推送快递派送收件人短信、快递开单收件人短信、快递签收收件人短信。
	    * 2.当CRM勾选“客户的收件人短信停发”时，停止对客户推送快递派送收件人短信、快递开单收件人短信、快递签收收件人短信。
	 *       3.当CRM勾选“两者都停发”时，停止对客户和该客户的收件人推送快递派送收件人短信、快递开单收件人短信、快递签收收件人短信。
	 *       </p>
	    * @param @param customerEntity  根据客户id查询实体对象
	    * @return boolean
	 */

	public boolean isSendMessage(CustomerEntity customerEntity,
			CustomerEntity customerEntityNew) {
		String receiverSms = customerEntity.getReceiverSms();
		String receiverSmsNew = customerEntityNew.getReceiverSms();
			// 依次分3种情况
			if (OrderConstant.STOP_MESSAGE_FOR_RECEIPT.equals(receiverSmsNew)
					|| OrderConstant.STOP_MESSAGE_FOR_CUST_RECEIPT.equals(receiverSms)
					|| OrderConstant.STOP_MESSAGE_FOR_DOUBLE.equals(receiverSms)
					||OrderConstant.STOP_MESSAGE_FOR_DOUBLE.equals(receiverSmsNew)) {
				return false;
			}
			return true;
		
	}

	/**
 * 更加运单号获取相关的地址信息
 */
	@Override
	public String getReceivingAddress(String code) {
		return pkpdataDictionaryValueDao.getReceivingAddress(code);
	}
	
	/**
	 * @author 218459-foss-dongsiwei
	 * 根据返货原单号查询子母单号生成返货单
	 */	
	public List<WaybillExpressEntity> assembleWaybillExpressEntity(List<String> list,WaybillExpressEntity e){
		List<WaybillExpressEntity> expressEntity=new ArrayList<WaybillExpressEntity>();
		WaybillExpressEntity entity = null;
		for (int i = 0; i < list.size(); i++) {
			entity = new WaybillExpressEntity();
			BeanUtils.copyProperties(e, entity);
			entity.setId(UUIDUtils.getUUID());
			entity.setOriginalWaybillNo(list.get(i));
			/*QmsYchExceptionReportEntity loseGroup = qmsYchService.isLoseGroup(list.get(i));
			if(!FossConstants.ACTIVE.equals(loseGroup.getIsInLoseGroup())){
			}*/
			expressEntity.add(entity);
		}
		return expressEntity;
	}
	
	@Override
	public TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(
			String waybillNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		return waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
	}

	@Override
	public QmsYchExceptionReportEntity isLoseGroup(String waybillNo) {
		return qmsYchService.isLoseGroup(waybillNo);
	}

	@Override
	public StockEntity queryStockEntityByNos(String orgCode, String waybillNo,
			String serialNO, String goodsAreaCode) {
		return stockService.queryStockEntityByNos(orgCode, waybillNo, serialNO, goodsAreaCode);
	}

	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code) {
		return saleDepartmentService.querySaleDepartmentByCode(code);
	}

	/**
	 * 德邦家装接口取值
	 */
	@Override
	public Map<String, WaybillMessageEntity> getWaybillEntityMessage(
			String[] waybillNo) {
		Map<String, WaybillMessageEntity> mapL = new HashMap<String, WaybillMessageEntity>();
		for (String str : waybillNo) {
			//运单信息
			WaybillMessageEntity waybillMessageEntity = waybillDao
					.queryWaybillandOther(str);
			//家装接口信息安装费
			List<InstallationEntity> installationEntityList = waybillDao
					.queryWaybillInstallation(str);
			if(installationEntityList.size()>0)
			{
			waybillMessageEntity.setInstallList(installationEntityList);
			}
			if (waybillMessageEntity != null) {
				mapL.put(str, waybillMessageEntity);
			}
		}
		return mapL;
	}
	
	/**
	 * PDA返货补录时，校验是否为子母件
	 */
	public void calculateReturnType(String waybillNo,WaybillDto dto){
		WaybillExpressEntity waybillExpress = dto.getWaybillExpressEntity();
		TwoInOneWaybillDto waybillDto=queryWaybillRelateByWaybillOrOrderNo(waybillNo);
		if(waybillDto!=null && FossConstants.YES.equals(waybillDto.getIsTwoInOne())){
			dto.getWaybillEntity().setPicture(true);
		}else{
			dto.getWaybillEntity().setPicture(false);
			return;
		}
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add(waybillDto.getMainWaybillNo());
		waybillNos.addAll(waybillDto.getWaybillNoList());
		WaybillEntity waybillEntity=waybillDao.queryWaybillNo(waybillNo);
		
		String orgCode=waybillEntity.getCustomerPickupOrgCode();
		
		// 查询营业部的部门信息
		if(StringUtils.isNotEmpty(orgCode)){			
			SaleDepartmentEntity dept = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if(dept != null && FossConstants.ACTIVE.equals(dept.getStation())){
				// 驻地营业部对应外场
				orgCode=dept.getTransferCenter();
			}
		}
		//按票返货
		if(waybillExpress.getReturnType() !=null &&WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(waybillExpress.getReturnType())){
			for (int i = 0; i <waybillNos.size(); i++) {
				//判断是否丢货
				QmsYchExceptionReportEntity  entity=isLoseGroup(waybillNos.get(i));
				if(entity !=null && WaybillConstants.YES.equals(entity.getIsInLoseGroup()) && entity.getIsSuccess()==1){
					waybillNos.remove(i);
				}
			}
			dto.getWaybillEntity().setWaybillNos(waybillNos);
	
		}
		//按件返
		if(waybillExpress.getReturnType() !=null &&WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(waybillExpress.getReturnType())){
			List<String> newWaybillNos=new ArrayList<String>();
			for (int i = 0; i < waybillNos.size(); i++) {
				//判断是否在到达部门库存
				StockEntity  entity=queryStockEntityByNos(orgCode,waybillNos.get(i),WaybillConstants.SERIAL_NUMBER,null);
				if(entity !=null){
					newWaybillNos.add(waybillNos.get(i));
				}
			}
			dto.getWaybillEntity().setWaybillNos(newWaybillNos);

		}
		
	}
	
	
	/**
	 * 电子运单提交
	 * (取消运单状态发送)
	 * @param waybill
	 * @return Result
	 */
	@Transactional
	@Override
	public void submitLTLEWaybill(final WaybillDto waybillDto) {

		// 定义运单基本信息对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		// 根据运输性质设置是否快递字段的值
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {

			waybillDto.setIsExpress(FossConstants.YES);
		} else {
			waybillDto.setIsExpress(FossConstants.NO);
		}
		// 校验运单是否符合业务规则
		verifyWaybill(waybillDto);
		
		/*String guiTitleName = waybillDto.getGuiTitleName();
		if(WaybillConstants.YES.equals(waybillDto.getIsGuiSubmit()) && guiTitleName != null && "图片开单".equals(guiTitleName)){
			String waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
			final WaybillPictureEntity entity = new WaybillPictureEntity();
			entity.setWaybillNo(waybillNo);
			entity.setActive(FossConstants.ACTIVE);
			
			//查询老图片暂存信息
			final WaybillPictureEntity currEntity = waybillPendingService.queryWaybillPictureByEntity(entity);
			if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(currEntity.getPendgingType())){//直接图片补录体积开单
				entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_THREAD);
				waybillPendingService.updatePictureWaybillByWaybillno(entity);
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.currentThread();//让当前线程等待500毫秒
						Thread.sleep(WaybillConstants.PIC_THREAD_SLEEP_TIME);
						
						//在线提交接口抽取
						submitWaybillCommon(waybillDto);
					} catch (Exception e) {//异常处理
						e.printStackTrace();
						
						String errorMsg  = e.getMessage();
						StringBuffer sb = new StringBuffer();
						String remark = currEntity.getRemark();
						//获取备注信息  防止暂存图片修复信息 没有及时更新备注信息
						if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(currEntity.getPendgingType())){
							entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
						} 
						if(remark != null){
							sb.append(remark);
							sb.append("/n");
						}
						sb.append(errorMsg).append("  ").append(new Date());
						entity.setRemark(sb.toString());
						//更新图片开单表备注信息
						waybillPendingService.updatePictureWaybillByWaybillno(entity);
					}
				}
				
			}).start();
			
		}else{
		}*/
		submitLTLEWaybillCommon(waybillDto);
		
	}

    /**
     * 判断能否欠款（配合CUBC改造接口）
     *
     * 使用说明，判断能否欠款均使用同一方法，如果付款方式为月结时请提供客户编码，欠款方式为临欠是请提供组织编码
     *
     * @param customerCode
     *            客户编码
     * @param orgCode
     *            组织编码 （欠款组织编码）
     * @param debtType
     *            欠款类别 区分临欠与月结
     * @param debtAmount
     *            欠款金额
     * @return 能否欠款的判断结果
     */

    @Override
    public DebitForCUBCDto isBeBebtForCUBC(String customerCode, String orgCode, String debtType, BigDecimal debtAmount,String waybillNo) {
        Object[] objects = validateForCUBCService.isBeBebtForCUBC(customerCode, orgCode, debtType, debtAmount);
        DebitForCUBCDto debitForCUBCDto = (DebitForCUBCDto) objects[0];
        CUBCLogEntity cubcLogEntity = (CUBCLogEntity) objects[1];
        if(cubcLogEntity!=null){
            cubcLogEntity.setWaybillNo(waybillNo);
        }
        syncCUBCLogService.insert(cubcLogEntity);
        if(debitForCUBCDto==null){
            throw new WaybillValidateException("CUBC接口数据异常！");
        }
        return debitForCUBCDto;
    }

    /**
	 * 在线提交接口电子运单接口抽取
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-30
	 * @param waybillDto
	 */
	@Transactional
	private void submitLTLEWaybillCommon(WaybillDto waybillDto){
		if(waybillDto == null){
			throw new WaybillValidateException("方法submitWaybillCommon-实体类waybillDto为空！");
		}
		
		boolean isPdaPending = false;
		
		/**
		 * @项目：智能开单项目
		 * @功能：提交耗时时间任务
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19下午18:19
		 */
		if(waybillDto.getIbtg()!=null){
			addIntelligenceBillTime(waybillDto);
		}
		
		// 定义运单基本信息对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
	
		// 获取运单处理状态
		String waybillStatus = StringUtil.defaultIfNull(waybillDto.getWaybillEntity().getPendingType());

		// 设置统一的创建人、创建时间、更新人、更新时间
		WaybillSystemDto systemDto = new WaybillSystemDto();
		// 设置当前用户信息
		CurrentInfo currentInfo = waybillDto.getCurrentInfo();
		systemDto.setCurrentUser(currentInfo);

		/**
		 * 缓存机构名称
		 * @author Foss-278328-hujinyang
		 * @TIME 20160422
		 */
		queryOrgNameMapsByCodesMap(waybillEntity.getCreateOrgCode(), waybillEntity.getReceiveOrgCode());
		
		// 设置修改时间
		systemDto.setModifyTime(new Date());
		// 设置创建时间
		if (WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING.equals(waybillStatus)) {
			// 若为离线导入开单，则使用离线客户端本地时间
			Date createTime = waybillDto.getWaybillEntity().getCreateTime();
			// 判断创建时间是否为空
			if (null == createTime) {
				createTime = new Date();
			}
			systemDto.setCreateTime(createTime);
		} else {
			systemDto.setCreateTime(new Date());
		}

		// 设置开单时间
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			WaybillPendingEntity pend = waybillPendingService
					.getWaybillPendingEntityByWaybillNo(waybillDto
							.getOldWaybillNo());
			if (pend != null) {
				// 若为PDA导入开单，则使用PDA时间
				Date billTime = waybillDto.getWaybillEntity().getBillTime();
				// 判断开单时间是否为空
				if (null == billTime) {
					billTime = new Date();
				}
				systemDto.setBillTime(billTime);
				if (null != waybillDto.getWaybillEntity()) {
				   waybillDto.getWaybillEntity().setBillTime(billTime);
				}
			} else {
				systemDto.setBillTime(new Date());
				if (null != waybillDto.getWaybillEntity()) {
				waybillDto.getWaybillEntity().setBillTime(new Date());
			  }
			}
		} else {
			systemDto.setBillTime(new Date());
			if (null != waybillDto.getWaybillEntity()) {
			  waybillDto.getWaybillEntity().setBillTime(new Date());
		  }
		}

		setExpChangeVolume(waybillDto);
		
		// 修改后的运输性质
		String productCode = waybillEntity.getProductCode();

		if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD, productCode)) {
			waybillEntity.setLastLoadOrgCode(waybillEntity.getCustomerPickupOrgCode());
			waybillEntity.setLastLoadOrgName(waybillEntity.getCustomerPickupOrgName());
		}
		if (StringUtils.isEmpty(waybillDto.getWaybillEntity().getCustomerPickupOrgName())) {
			String prdCode = waybillDto.getWaybillEntity().getProductCode();
			String orgcode = waybillDto.getWaybillEntity().getCustomerPickupOrgCode();
			if (!StringUtils.isEmpty(orgcode)) {
				String customerPickupOrgName = waybillDao.queryCustomerPickupOrgNameByWayno(orgcode, prdCode);
				waybillDto.getWaybillEntity().setCustomerPickupOrgName(customerPickupOrgName);
			}
		}
		if (StringUtils.isEmpty(waybillDto.getWaybillEntity().getReceiveOrgName())) {
			String orgcode = waybillDto.getWaybillEntity().getReceiveOrgCode();
//					String receiveOrgName = waybillDao.queryReceiveOrgNameByWayno(orgcode);
			/**
			 * @author Foss-278328-hujinyang
			 * @date 20160421
			 */
			String receiveOrgName = queryOrgNameMapsByCodes(orgcode);
			waybillDto.getWaybillEntity().setReceiveOrgName(receiveOrgName);
		}

		// 重新赋值
		if (StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
			/**
			 * DEFECT-824 部分营业部反馈客户网单无法在线跟踪运单详情
			 */
			ActualFreightEntity actualFreightEntity = waybillDto.getActualFreightEntity();
			if (actualFreightEntity != null) {
				// 若运单绑定的订单为官网订单，且运单状态为待补录的状态时，需要通过订单号查一遍订单信息，将官网登陆名查询出来
				if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE
						.equals(waybillEntity.getOrderChannel())
						&& StringUtil.isNotEmpty(waybillEntity.getOrderNo())) {
					// 待补录状态
					if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING
							.equals(waybillStatus)
							|| WaybillConstants.WAYBILL_STATUS_PC_PENDING
									.equals(waybillStatus)) {
						// 根据订单号查询订单信息
						CrmOrderDetailDto orderDetailVo = crmOrderService
								.importOrder(StringUtil
										.defaultIfNull(waybillEntity
												.getOrderNo()));
						// 非空判断
						if (null != orderDetailVo) {
							if (StringUtils.isEmpty(orderDetailVo
									.getChannelCustId())) {
								throw new WaybillValidateException(
										"官网订单导入，登陆名为空时不允许提交！订单号："
												+ waybillEntity.getOrderNo());
							} else {
								actualFreightEntity
										.setChannelCustId(orderDetailVo
												.getChannelCustId());
							}
						}

						// 设置待补录的电子运单的运单类型
						if (StringUtil.isNotBlank(waybillDto.getOldWaybillNo())) {
							WaybillPendingEntity waybillpending = waybillPendingService
									.getWaybillPendingEntityByWaybillNo(waybillDto
											.getOldWaybillNo());
							if (waybillpending != null
									&& StringUtil.isNotBlank(waybillpending
											.getWaybillType())
									&& waybillpending.getWaybillType().equals(
											WaybillConstants.EWAYBILL)) {
								actualFreightEntity
										.setWaybillType(waybillpending
												.getWaybillType());
							}
						}
					}
				}
			}
			waybillDto.setActualFreightEntity(actualFreightEntity);
		}

		// zxy 20130916 BUG-54827 start 新增：运单如果失效且是6月1号前导入的数据，则删除ActualFreight数据
		String waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
		// 根据运单号查询
		if (actualFreightService.isExistActualFreight(waybillNo)) {
			WaybillEntity we = waybillDao.queryRecentWaybillByNo(waybillNo);
			// 如果此运单存在ActualFreight，先判断此运单是否失效且是系统上线前导入的数据，如果是则可以删除ActualFreight
			if (we == null
					|| (FossConstants.NO.equals(we.getActive()) && we
							.getBillTime().before(
									DateUtils.convert("2013-06-01")))) {
				actualFreightService.deleteActualFreightByWaybillNo(waybillNo);
			}
		}
		// zxy 20130916 BUG-54827 end 新增：运单如果失效且是6月1号前导入的数据，则删除ActualFreight数据

		/**
		 * MANA-1937 系统开发生成客户编码 026123 2014-03-24
		 */
		procDeliverAndReceiveCustomer(waybillDto);

		// 生成运单完整信息
		isPdaPending = addWaybill(waybillDto, systemDto, isPdaPending);

		waybillEntity = waybillDto.getWaybillEntity();

		// 是否为整车，若为整车则不生成中转线路信息
		if (FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
			LOG.info("submitWaybill:进入整车生成库存部分");
			// 生成货签信息和库存信息
			addLabeledGood(waybillDto, systemDto, isPdaPending);
		} else {
			LOG.info("submitWaybill:进入生成库存部分");
			
			// 中转接口：生成中转线路信息
			addLine(waybillDto, isPdaPending);
			// 生成货签信息和库存信息
			addLabeledGood(waybillDto, systemDto, isPdaPending);

		}

		// 若为签收单返单类型，则需要生成签收单信息
		if (!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())) {
			// 生成签收单信息
			addSignReturnBill(waybillDto);
		}

		// 当打木架信息不为空时，才调用中转接口,（并且木架木托木箱至少有一个不为0时才调用中转接口--lianhe--2017年3月10日14:12:51）
		if (waybillDto.getWoodenRequirementsEntity() != null 
				&&((waybillDto.getWoodenRequirementsEntity().getStandGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getStandGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getBoxGoodsNum() != 0)
				||(waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum()!=null && waybillDto.getWoodenRequirementsEntity().getSalverGoodsNum() != 0))) {
			// 中转接口：生成包装信息
			woodenRequirementsService.addPackagingRequire(waybillDto);
		}
	
		// 内部带货不调用财务接口（已与周斌文确认2013-04-17）
		String pickupMode = StringUtil.defaultIfNull(waybillEntity
				.getReceiveMethod());
		//调用cubc灰度接口
		VestBatchResult vestResult = this.getVestResult(waybillDto, "submitLTLEWaybill");
		
		if (!pickupMode.equals(WaybillConstants.INNER_PICKUP)
				&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(pickupMode)
				&& (!FossConstants.YES.equals(waybillDto.getActualFreightEntity().getPartnerBillingLogo()) || FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()))
				&& !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
			/*if(null!=waybillDto&&
					null!=waybillDto.getActualFreightEntity()&&
					null!=waybillDto.getActualFreightEntity().getDcServicefee()){
				waybillDto.getWaybillEntity().setServiceFee(waybillDto.getActualFreightEntity().getDcServicefee());
			}*/
				
				// 结算接口：生成结算相关财务单据
//				addSettleBill(waybillDto);
			addSettleBillForCUBC(waybillDto);
			/*if(null!=waybillDto&&
					null!=waybillDto.getActualFreightEntity()&&
					null!=waybillDto.getActualFreightEntity().getDcServicefee()){
				waybillDto.getWaybillEntity().setServiceFee(BigDecimal.ZERO);
			}*/
		}
		

		// 运单冗余信息新增
		addActualFreightInfo(waybillDto, systemDto, isPdaPending);
		
		// 新增特殊增值服务标示和相关数据  mabinliang-254615-foss
		addSpecialValueAddedServiceData(waybillDto);

		// 新增业务标识
		addWaybillTransactionStat(waybillDto);
		
		//付款方式选择为银行卡时，运单数据保存待刷卡运单数据表
		//PDA开单时不调用结算接口
		if (WaybillConstants.CREDIT_CARD_PAYMENT.equals(waybillEntity
				.getPaidMethod()) && !isPdaPending) {
			// liding add 新增待刷卡操作界面 NCI
			addWSCWayBillData(waybillEntity);
		}

		// 待补录运单信息
		WaybillPendingEntity waybillPendingEntity = null;
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)) {
			waybillPendingEntity = waybillPendingService.getWaybillPendingEntityByWaybillNo(waybillDto.getOldWaybillNo());

			// zxy 20140211 MANA-1044 start 新增:PDA补录时修改了重量体积需要调用中转更新相关信息
//					if (!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())
			if (FossConstants.NO.equals(waybillDto.getIsExpress())
					&& waybillPendingEntity != null) {
				BigDecimal preVolume = waybillPendingEntity.getGoodsVolumeTotal();
				BigDecimal preWeight = waybillPendingEntity.getGoodsWeightTotal();
				BigDecimal curVolume = waybillEntity.getGoodsVolumeTotal();
				BigDecimal curWeight = waybillEntity.getGoodsWeightTotal();
				MakeUpWaybillEntity makeUpWaybillEntity = new MakeUpWaybillEntity();
				// 只要重量和体积有一个修改了就传给中转进行刷新
				if ((preVolume == null || preVolume.compareTo(curVolume) != 0)
						|| (preWeight == null || preWeight.compareTo(curWeight) != 0)) {
					makeUpWaybillEntity.setVolume(curVolume);
					makeUpWaybillEntity.setWeight(curWeight);
					makeUpWaybillEntity.setWaybillNo(waybillNo);
					makeUpWaybillEntity
							.setMakeUpTime(systemDto.getModifyTime());
					makeUpWaybillEntity.setQuantity(new BigDecimal(
							waybillEntity.getGoodsQtyTotal()));
					// 通知中转重量体积变更，将变更信息放入中转跑批表
					makeUpWaybillService
							.addWaybillInfoForTfr(makeUpWaybillEntity);
				}
			}
		}

		// 图片直接开单 需添加暂存表供完成接货卸车操作
		WaybillPendingEntity billPendingEntity = waybillPendingDao
				.queryPendingByNo(waybillNo);

		// 判断是否是PDA补录或者运单暂存补录
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus)
				|| WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillStatus)) {
			// 更新待处理运单记录信息
			if (!(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus) && billPendingEntity == null)){
					updatePendingData(waybillDto, waybillStatus);
			}
		}

		// 是否为弃货处理运单
		if (WaybillConstants.WAYBILL_STATUS_GOODS_PENDING.equals(waybillStatus)) {
			// 更新弃货处理运单记录信息，标识待弃货运单为N,更新弃货运单号储运事项为新运单号
			updateAbandonedGoodsData(waybillDto.getWaybillEntity());
		}

		// 订单号是否为空,订单号不为空则更新订单状态
//		if (StringUtil.isNotBlank(waybillDto.getWaybillEntity().getOrderNo())) {
//			orderService.updateOrderInfo(waybillDto.getWaybillEntity());
//		}
		// 待补录运单信息
		WaybillPendingEntity waybillpending = waybillPendingService.queryWaybillPendingEntityNo(waybillDto.getOldWaybillNo());
		if (waybillpending != null) {
			waybillDto.setReceiveOrgCode(waybillpending.getCreateOrgCode());
		}
		// 处理待办事务
		handleToDoMsg(waybillDto);
		// 保存待处理发送短信信息，执行异步定时任务
		savePendingSendMailInfo(waybillDto);

		// 只针对精准汽运（长/短途）、精准卡航、精准城运----保存待处理返券信息，执行异步定时任务----206860
		if (waybillDto != null) {// Online-定价体系优化项目DJTWO-63
			if (WaybillConstants.LRF_FLIGHT.equals(waybillDto
					.getWaybillEntity().getProductCode())
					|| WaybillConstants.SRF_FLIGHT.equals(waybillDto
							.getWaybillEntity().getProductCode())
					|| WaybillConstants.TRUCK_FLIGHT.equals(waybillDto
							.getWaybillEntity().getProductCode())
					|| WaybillConstants.FSF_FLIGHT.equals(waybillDto
							.getWaybillEntity().getProductCode())) {
				// 只有发货客户存在手机号码时，才做降价返券处理...
				if (StringUtil.isNotEmpty(waybillDto.getWaybillEntity()
						.getDeliveryCustomerMobilephone())) {
					// 当提货方式不为内部带货时，才做降价返券处理
					if (StringUtil.isNotBlank(waybillDto.getWaybillEntity()
							.getReceiveMethod())) {
						if (!waybillDto.getWaybillEntity().getReceiveMethod()
								.contains("INNER")) {
							makePendingSendCoupon(waybillDto);
						}
					}
				}
			}
		}
		// 更新约车状态
		updateVehicleState(waybillDto);

		// 判断优惠券对象是否为空
		if (waybillDto.getCouponInfoDto() != null) {
			// 使用优惠券
			useCoupon(waybillDto);
		}

		// 快递冗余信息
		if (waybillDto.getWaybillExpressEntity() != null) {
			addWaybillExpress(waybillDto, systemDto);
		}

		// 释放业务锁

		// unlock(waybillEntity);

		// 增加定人定区地址(放在最后面)
		addReceiveAddressRfc(waybillEntity);		// 如果是PDA补录的单，则判断是否需要生成待办（和更改单处理待办逻辑基本一致，只是PDA补录的单，如果修改了信息，更改状态都是自动审核、受理同意）
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillStatus) && waybillPendingEntity != null) {
			// 处理PDA补录单的待办信息
			handlePDAPendingTODO(waybillDto, waybillPendingEntity);
			// 更新GUI中PDA待补录信息
			updateTodoMsgStatus(waybillDto);
		}

		// 图片信息
		WaybillPictureEntity pictureEntity = new WaybillPictureEntity();
		pictureEntity.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
		pictureEntity.setActive(FossConstants.ACTIVE);
		// 查询图片休息
		List<WaybillPictureEntity> pictureEntitys = waybillPictureDao.queryWaybillPictureByEntity(pictureEntity);
		if (CollectionUtils.isNotEmpty(pictureEntitys)) {
			WaybillPictureEntity entity = pictureEntitys.get(0);
			WaybillPictureEntity waybillPictureEntity = new WaybillPictureEntity();
			waybillPictureEntity.setId(entity.getId());
			waybillPictureEntity
					.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);
			// 如果是foss端开单员手动导入的订单那需要把订单号存入图片开单信息表中
			waybillPictureEntity.setOrderNo(waybillEntity.getOrderNo());
			waybillPictureEntity.setRemark("");
			if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(entity.getPendgingType())){				
				waybillPictureEntity.setEndBillTime(new Date());

			} else {
				waybillPictureEntity.setModifyTime(new Date());
			}
			waybillPictureDao.updateWaybillPicture(waybillPictureEntity);

			/**
			 * 图片直接开单 需添加暂存表供完成接货卸车操作
			 */

			// 交接单明细
			StayHandoverDetailEntity stayHandover = stayHandoverDetailDao.queryByWaybillNo(waybillNo);
			// 是图片开单，如果没有待补录信息和交接单信息，添加暂存表供完成接货卸车操作
			if (billPendingEntity == null && stayHandover == null) {
				WaybillPendingEntity pendingEntity = new WaybillPendingEntity();
				BeanUtils.copyProperties(waybillEntity, pendingEntity);
				pendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
				pendingEntity.setHandoverStatus(FossConstants.NO);
				pendingEntity.setPackageRemark("M;");
				pendingEntity.setDriverCode(entity.getDriverCode());
				waybillPendingDao.insertSelective(pendingEntity);

				// 图片直接开单，推送货物轨迹
				if (!WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(entity
						.getPendgingType())) {
					// 推送货物轨迹
					addSynTrack(waybillDto);
				}
			}

			/**
			 * 图片直接开单，添加标签信息为为PDA卸车时获取流水号信息 添加pkp.t_srv_labeled_good_pda
			 */
			List<LabeledGoodPDAEntity> labelList = labeledGoodPDAService.queryByWaybillNo(waybillNo);
			if (CollectionUtils.isEmpty(labelList)) {
				// 插入
				addPDALabeledGood(waybillEntity);
			}

		}

		// 为配合快递100轨迹推送所需的方法
		addOneWaybillTrack(waybillDto);
		// 配合自动补码
		if (waybillDto.getWaybillEntity() != null
				&& WaybillConstants.YES.equals(waybillDto.getWaybillEntity()
						.getIsExpress())) {
			autoAddCode(waybillDto);
		}
		/**
		 * 解决批量开单流水号信息丢失 dp-foss-dongjialing 225131
		 */
		if (waybillDto.getActualFreightEntity() != null
				&& WaybillConstants.YES.equals(waybillDto
						.getActualFreightEntity().getIsBatchCreateWaybill())) {
			List<LabeledGoodEntity> list = labeledGoodService
					.queryAllSerialByWaybillNo(waybillDto
							.getActualFreightEntity().getWaybillNo());
			if (CollectionUtils.isEmpty(list)) {
				List<LabeledGoodEntity> labels = addLabeledGood(
						waybillDto.getWaybillEntity(),
						waybillDto.getWoodenRequirementsEntity());
				if (CollectionUtils.isNotEmpty(labels)) {
					labeledGoodDao.insertBatch(labels);
				}
			}
			/**
			 * 批量开单删除运单异常信息
			 * dp-foss-dongjialing
			 * 225131
			 */
			if(waybillDto.getWaybillEntity() != null && WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillDto.getWaybillEntity().getPendingType())) {				
				if(null != billPendingEntity && WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(billPendingEntity.getPendingType())) {
					waybillPendingService.deleteByWaybillNo(waybillDto.getOldWaybillNo());
		}
			}
		}

		//如果特殊增值服务标识不为空，且 为（家具送装，建材送装，家电送装）中的一种，则保存家装运单信息
		if(!StringUtil.isBlank(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) &&
				(WaybillConstants.FURNITURE_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) ||
				 WaybillConstants.BUILD_MATERIAL_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()) ||
				 WaybillConstants.HOME_APPLICATION_EQUIP.equals(waybillDto.getActualFreightEntity().getSpecialValueAddedServiceType()))){
			waybillHomeImpService.saveWaybillHomeImpInfo(waybillDto);
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentWaybillNo",waybillNo);
		params.put("active",FossConstants.INACTIVE);
		//根据运单号查出子件信息
		waybillDto.setWaybillRelateDetailEntityList(waybillRelateDetailEntityService.queryWaybillRelateDetailListByOrderOrWaybillNo(params));
		//添加子母件数据的添
		submitWaybillRelateDetail(waybillDto, systemDto);
		//更新子母件状态位
		if(waybillDto != null && CollectionUtils.isNotEmpty(waybillDto.getWaybillRelateDetailEntityList())){
			//修改子母单关系表状态改为有效
			WaybillRelateDetailEntity waybillRelateDetail=new WaybillRelateDetailEntity();
			waybillRelateDetail.setActive(FossConstants.YES);
			waybillRelateDetail.setParentOrderNo(waybillNo);
			waybillRelateDetail.setModifyTime(new Date());
			waybillRelateDetailEntityService.updateWaybillRelateDetailByWaybillNoSelective(waybillRelateDetail);
		}
		
		//合伙人项目
		try {
			//判断是否需要推送信息(快递开单都需要推送，零担开单如果开单和目的地都不是合伙人不需要执行)
			if(!FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && waybillDto !=null && waybillDto.getPtpWaybillDto() !=null 
				&& (!StringUtils.equals(waybillDto.getPtpWaybillDto().getFeeType(), WaybillConstants.NEGATIVE_ONE)
					||WaybillConstants.YES.equals(waybillDto.getIsExpress()))){
				PtpWaybillDto ptpWaybillDto = ConvertBean.getPtpWaybillDto(waybillDto,currentInfo);
				//获取修改前的信息然后保存到foss
				PartnersWaybillEntity partnersWaybillEntity = getPartnersWaybillEntity(waybillDto,systemDto);
				//如果是快递开单都存入合伙人折前表				
				if(WaybillConstants.YES.equals(waybillDto.getIsExpress())){
					partnersWaybillService.addPartnersWaybillEntity(partnersWaybillEntity);
				}else{
					//零担
					if(!WaybillConstants.NEGATIVE_ONE.equals(ptpWaybillDto.getFeeType())){
						partnersWaybillService.addPartnersWaybillEntity(partnersWaybillEntity);
					}
				}
				//出发部门标杆编码
				OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getReceiveOrgCode());
				if(orgReceiveEntity!=null){
					ptpWaybillDto.setReceiveDeptUnifieldCode(orgReceiveEntity.getUnifiedCode());
				}
				//到达部门标杆编码
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getArriveOrgCode());
				if(orgAdministrativeInfoEntity!=null){
					ptpWaybillDto.setArriveDeptUnifieldCode(orgAdministrativeInfoEntity.getUnifiedCode());
				}
				
				if(!StringUtils.equals(waybillDto.getPtpWaybillDto().getFeeType(), WaybillConstants.NEGATIVE_ONE)){
					//是否快递
					if(WaybillConstants.YES.equals(waybillDto.getIsExpress())){
						expWaybillInfoToPtpService.asynSendExpWaybillInfoToPtp(ptpWaybillDto);
					}else{//是零担开单
						waybillInfoToPtpService.asynSendWaybillInfoToPtp(ptpWaybillDto);
					}
				}
			}
		}catch(WaybillSubmitException w){
			throw new WaybillSubmitException(w.getMessage());
		}catch (Exception e) {
			LOG.error("合伙人项目 保存推送运单信息异常："+e.getStackTrace());
			e.printStackTrace();
			throw new BusinessException("合伙人项目保存推送运单信息异常："+System.err);
		} 
		
		//同步零担电子运单信息和财务单据给CUBC  zhangwei 2016-10-15 下午2:22:15
		if(Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
			if (!WaybillConstants.INNER_PICKUP.equals(pickupMode)
				&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(pickupMode)
				&& !(BigDecimal.ZERO.compareTo(waybillEntity.getTotalFee()) == 0)) {
				waybillInfoToCUBCService.asyncSendWaybillInfoToCUBC(waybillDto);
			}
		}
	}
	
	

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-11-9 t上午10:15:37
	  * @param @param wayBillNoList
	  * @param @return
	  */
	@Override
	public List<WaybillEntity> queryWaybillListWayBillNo(List<String> wayBillNoList) {
		return waybillDao.queryWaybillListWayBillNo(wayBillNoList);
	}
	
	@Override
	public Map<String, String> queryAgentNameMapsByAgentCode(List<String> orgs) {
		return  vehicleAgencyDeptService.queryAgentNameMapsByAgentCode(orgs);
	}
	/**
	 * 
	 * 查询运单
	 * 
	 * @author 
	 * @date 2012-11-26 下午08:37:46
	 */
	@Override
	public List<WaybillEntity> queryWaybillForPrint(WaybillDto waybillDto) {
		// 查询运单基本信息集合
		return waybillDao.queryWaybillForPrint(waybillDto);
	}
	
	//通过运单ID查询合伙人表中的运单信息 2016年3月15日 19:49:02 葛亮亮
	public PartnersWaybillEntity queryPartnerWaybillEntityByWaybillId(String waybillNoid){
		return partnersWaybillService.queryPartnerWaybillEntityByWaybillId(waybillNoid);
	}

	/**
	 * 通过运单号查找免费接货值
	 * @author 306486
	 * @param waybillNo
	 * @return
	 */
	@Override
	public String queryPickupGoodsByWaybillNo(String waybillNo) {
		
		return waybillDao.queryPickupGoodsByWaybillNo(waybillNo);
	}


	public IIntelligenceTimeRecordService getIntelligenceTimeRecordService() {
		return intelligenceTimeRecordService;
	}

	public void setIntelligenceTimeRecordService(
			IIntelligenceTimeRecordService intelligenceTimeRecordService) {
		this.intelligenceTimeRecordService = intelligenceTimeRecordService;
	}
	@Resource
	private IPushFoss2EcsWaybillNoService pushFoss2EcsWaybillNoService;

	/**
	 * 
	 * <p>
	 * 通过运单号获取运单基础信息
	 * </p>
	 * 
	 * @author foss-zhuxue
	 * 280747
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillByNo(java.lang.String)
	 */
	@Override
	public WaybillEntity queryNewWaybillBasicByNo(String waybillNo) {
		// 返回运单基本信息
		return waybillDao.queryNewWaybillByNo(waybillNo);
	}
	
	/**
	 * 传递开关打开时传递运单信息到ECS
	 * @param waybillDto
	 * 351326 xingj
	 * @time 2016年8月4日15:18:46
	 */
	private void pushWaybillNo2Ecs(WaybillDto waybillDto){
		//是否传递开关
		boolean switch4EcsFlag = configurationParamsService.queryPkpSwitch4Ecs();
		//开关打开，且为快递
		if(switch4EcsFlag && null != waybillDto.getWaybillEntity() && WaybillConstants.YES.equals(waybillDto.getWaybillEntity().getIsExpress()))
			//将运单号推送ECS
			pushFoss2EcsWaybillNoService.pushFoss2EcsWaybillNo(waybillDto.getWaybillEntity().getId(),
					waybillDto.getWaybillEntity().getWaybillNo(), waybillDto.getWaybillEntity().getCreateTime(),
					waybillDto.getWaybillEntity().getCreateUserCode());
	}
	

	/**
	 * FOSS校验悟空快递单号地址
	 * @param map
	 * @return true:校验正常 可以开单，false：校验异常，不可开单
	 * @author 272311- sangwenhao
	 */
	@Override
	public boolean validateWaybillNoIsCorrect(Map<String, String> map){
		if(MapUtils.isEmpty(map)){
			throw new BusinessException("校验快递运单号异常，请求内容为空！");
		}
		return valuateFoss2EcsWaybillNoService.validateWaybillNoIsCorrect(map) ;
	}
	
	
	
	/**
	 * 菜鸟运单作废、中止完整率优化
	 * @author 265475
	 * @param oldVersionDto
	 * @return
	 */
	@Override
	public void addSynTracks(WaybillDto oldVersionDto) {
		WaybillEntity waybill = oldVersionDto.getWaybillEntity();
		
		SynTrackingEntity track = new SynTrackingEntity();
		track.setId(UUIDUtils.getUUID());
		// 运单
		track.setWayBillNo(waybill.getWaybillNo());
		if (!StringUtils.isEmpty(waybill.getOrderNo())) {
			DispatchOrderEntity order = dispatchOrderEntityDao
					.queryAllInfoByOrderNo(waybill.getOrderNo());
			if (order != null) {
				// 订单
				track.setOrderNo(order.getChannelNumber());
			}
		}
		// 发生时间
		track.setOperateTime(waybill.getBillTime());
		track.setCreateDate(new Date());
		// 跟踪信息描述
		// entity.setTrackInfo("已进行返货开单，返货单号【"+waybill.getOrderNo()+"】");
		// 跟踪信息描述
		track.setTrackInfo("客户拒签，【运单作废】");
		// 开单部门所在城市
		// OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		// .queryOrgAdministrativeInfoByCode(waybill.getReceiveOrgCode());
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(waybill.getCustomerPickupOrgCode());
		String cityName = null;
		if (org!=null && StringUtils.isBlank(org.getCityName())) {
			cityName = administrativeRegionsService.gainDistrictNameByCode(org.getCityCode());
		}
		if (cityName != null) {
			track.setOperateCity(cityName);
		} else {
			OuterBranchExpressEntity outerExp = ldpAgencyDeptService
					.queryLdpAgencyDeptByDeptCode(waybill.getCustomerPickupOrgCode());
			if (null != outerExp) {
				AdministrativeRegionsEntity city = administrativeRegionsService
						.queryAdministrativeRegionsByCode(outerExp.getCityCode());
				if (city != null) {
					track.setOperateCity(city.getName());
				}
			}
		}
		// 产品类型
		track.setProductCode(waybill.getProductCode());
		// 站点类型
		track.setOrgType(String.valueOf(1));
		// 部门编码
		track.setOrgCode(waybill.getCustomerPickupOrgCode());
		// 部门名称
		track.setOrgName(waybill.getCustomerPickupOrgName());
		// 事件
		track.setEventType(String.valueOf("FAILED"));

		pushTrackForCaiNiaoService.addSynLpsTrack(track);
	}
	/**
	 * 
	 * 查询运单
	 * 280747
	 * @author 
	 * @date 2016-10-19 下午08:37:46
	 */
	@Override
	public String partnerWaybillForPrint(String waybillNo) {
		// 查询运单基本信息集合
		return waybillDao.queryPartnerWaybillForPrint(waybillNo);
	}
	
	/**
	 * 据条件查询推送CUBC接口日志
	 */
	@Override
	public List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto) {
		// TODO Auto-generated method stub
		return syncWaybillLogService.queryLogEntityByCondition(queryDto);
	}
	
	/**
	 * 重新推送运单信息至CUBC
	 */
	@Override
	public void pushWaybillToCUBC(List<WaybillLogEntity> cubcLogEntitys) {
		for (WaybillLogEntity waybillLogEntity : cubcLogEntitys) {
			waybillInfoToCUBCService.asyncSendWaybillToCUBC(waybillLogEntity);
		}
	}

	/**
	 * pda运单补录处理加收费
	 * 
	 * @author Foss-351326-xingjun
	 * @date 2016-9-1 上午10:23:26
	 * @param waybillDto
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#pdaWaybillReplenish(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto)
	 */
	@Override
	public void pdaWaybillReplenish(WaybillDto waybillDto) {
		// 运单实体
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		// 目的站，使用提货网点
		String targetOrgCode = waybillEntity.getCustomerPickupOrgCode();
		// 开单时间
		Date createTime = waybillEntity.getCreateTime();
		// 判断是否需要加收
		if (!judgeIsAddedFee(targetOrgCode, createTime))
			return;
		// 承运信息
		ActualFreightEntity actualFreightEntity = waybillDto
				.getActualFreightEntity();
		
		// 计费重量
		BigDecimal billWeight = waybillEntity.getGoodsWeightTotal();
		// 体积
		BigDecimal volume = waybillEntity.getGoodsVolumeTotal();
		// 运费计费类型（体积、重量）
		String billingtype = waybillEntity.getBillingType();
		// 加收费用
		AddedPlanFeeCalculateDto dto = caculateAddFee(targetOrgCode, createTime,billingtype, volume, billWeight,"change");
		//加收费金额
		BigDecimal addedFee = dto.getAddedFee();
		//加收费费率
		BigDecimal feeRate = dto.getFeeRate();
		//四舍五入
		addedFee = addedFee.setScale(0, BigDecimal.ROUND_HALF_UP);
		// 付款方式
		String paidMethod = waybillEntity.getPaidMethod();
		// 根据到付或者其他付款方式设置金额
		if (WaybillConstants.ARRIVE_PAYMENT.equals(paidMethod)) {
			// 将加收费累加到付金额
			waybillEntity.setToPayAmount(waybillEntity.getToPayAmount().add(
					addedFee));
		} else {
			// 将加收费累加到预付金额
			waybillEntity.setPrePayAmount(waybillEntity.getPrePayAmount().add(
					addedFee));
		}
		// 运费
		BigDecimal transportFee = waybillEntity.getTransportFee().add(addedFee);
		// 总运费
		BigDecimal totalFee = waybillEntity.getTotalFee().add(addedFee);
		//费率
		BigDecimal unitPrice = waybillEntity.getUnitPrice().add(feeRate);
		// 运费
		waybillEntity.setTransportFee(transportFee);
		actualFreightEntity.setFreight(transportFee);
		//运费费率
		waybillEntity.setUnitPrice(unitPrice);
		
		// 总运费
		waybillEntity.setTotalFee(totalFee);
		
		//折前信息 new一个实例，用来传递加收费，在后续处理中需要set加收费到PtpWaybillDto信息中
		PartnersWaybillEntity partnersWaybillEntity = new PartnersWaybillEntity();
		partnersWaybillEntity.setOverTransportFee(addedFee);
		partnersWaybillEntity.setOverTransportRate(feeRate);
		waybillDto.setPartnersWaybillEntity(partnersWaybillEntity);
	}

	@Resource
	private IToAddPartnerProgramService addPartnerProgramService;

	/**
	 * 根据目的站网点、重量体积计算加收费
	 * 
	 * @author Foss-351326-xingjun
	 * @date 2016-8-31 上午9:49:47
	 * @param targetOrgCode
	 *            目的站网点编号
	 * @param billTime
	 *            开单时间
	 * @param billingtype
	 *            计费方式 重量/体积
	 * @param volume
	 *            体积
	 * @param weight
	 *            重量
	 * @param crateOrChange 开单（create）     or  更改（change）
	 * @return 加收费
	 * @see
	 */
	@Override
	public AddedPlanFeeCalculateDto caculateAddFee(String targetOrgCode, Date billTime,
			String billingtype, BigDecimal volume, BigDecimal weight,String createOrChange) {
		AddedPlanFeeCalculateDto dto = new AddedPlanFeeCalculateDto();
		
		ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto = new ToAddPartnerProgramCondtionDto();
		// 目的站网点编号
		toAddPartnerProgramCondtionDto.setOrgCode(targetOrgCode);
		// 开单时间
		toAddPartnerProgramCondtionDto.setBillTime("create".equals(createOrChange) ? new Date() : billTime);
		// 激活状态
		toAddPartnerProgramCondtionDto.setActive("Y");
		// 查询运单对应的加收方案信息
		ToAddPartnerProgramEntity toAddPartnerProgramEntity = addPartnerProgramService.addedFeePlanCaculateQuery(toAddPartnerProgramCondtionDto);
		//针对历史单以及pda补录体积重量做处理。如果根据开单时间无法查询到加收方案且为更改时，则使用当前时间查询加收
		if(null == toAddPartnerProgramEntity && "change".equals(createOrChange)){
			toAddPartnerProgramCondtionDto.setBillTime(new Date());
			toAddPartnerProgramEntity = addPartnerProgramService.addedFeePlanCaculateQuery(toAddPartnerProgramCondtionDto);
		}
			
		if (null == toAddPartnerProgramEntity) {
			LOG.debug("目的站网点：" + targetOrgCode + "查询加收方案为空！");
			OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService
					.queryOrgInfoByCodeAndTime(targetOrgCode, billTime);
			String orgName = entity == null ? "" : entity.getOrgSimpleName();
			throw new WaybillSubmitException("目的站网点：【" + targetOrgCode + "】"
					+ orgName + "未找到加收方案信息！");
		}
		// 最低一票
		BigDecimal lowestPrice = toAddPartnerProgramEntity.getLowestPrice();
		// 重货费率
		BigDecimal heavyPrice = toAddPartnerProgramEntity.getHeavyPrice();
		// 轻货费率
		BigDecimal lightPrice = toAddPartnerProgramEntity.getLightPrice();
		// 加收费用
		BigDecimal addedFee = null;
		//费率
		BigDecimal feeRate = null;

		if (WaybillConstants.BILLINGWAY_WEIGHT.equals(billingtype)) {
			// 按重量计费，根据重货费率计算加收费用
			addedFee = weight.multiply(heavyPrice);
			feeRate = heavyPrice;
		} else if (WaybillConstants.BILLINGWAY_VOLUME.equals(billingtype)) {
			// 按体积计费，按照轻货费率计算加收费用
			addedFee = volume.multiply(lightPrice);
			feeRate = lightPrice;
		}
		// 比较最低一票，如果低于最低一票则取最低一票作为加收费
		addedFee = addedFee.compareTo(lowestPrice) > 0 ? addedFee : lowestPrice;
		
		dto.setAddedFee(addedFee);
		dto.setFeeRate(feeRate);
		return dto;
	}

	/**
	 * 判断目的站网点模式，是否需要加收
	 * 
	 * @author Foss-351326-xingjun
	 * @date 2016-8-31 上午11:00:16
	 * @param targetOrgCode
	 *            目的站网点编号
	 * @return true 需要加收 false 不需要
	 * @see
	 */
	private boolean judgeIsAddedFee(String targetOrgCode, Date billTime) {
		// 查询网点信息
		
		SaleDepartmentEntity saleDepartmentEntity =saleDepartmentService.querySimpleSaleDepartmentByCodeCache(targetOrgCode);

		if (null == saleDepartmentEntity) {
			LOG.debug("根据网点编号：" + targetOrgCode + "查询目的站网点信息为空");
			//为空时，图片开单选择的为营业部以外的网点，开单时为外发，不需要计算加收费
			return false;
		}
		// 网点模式
		String netWorKmodel = saleDepartmentEntity.getNetworkModel();
		// 是否二级网点
		String isTwoLevelNetwork = saleDepartmentEntity.getIsTwoLevelNetwork();
		//是否合伙人
		String isLeagueSaleDept = saleDepartmentEntity.getIsLeagueSaleDept();
		// 判断网点模式
		return judgeNetWorkModel(isTwoLevelNetwork, netWorKmodel,isLeagueSaleDept);
	}

	/**
	 * 判断网点模式需要加收 标准模式 STANDARD_MODEL 到达模式 ARRIVAL_MODEL 简装模式 SIMPLE_MODEL 代理模式
	 * PROXY_MODEL
	 * 
	 * @author Foss-351326-xingjun
	 * @date 2016-8-31 下午3:53:14
	 * @param networkModel
	 *            网点模式
	 * @param isTwoLevelNetwork
	 *            是否二级网点
	 * @return true 需要加收 false 不需要
	 * @see
	 */
	private boolean judgeNetWorkModel(String isTwoLevelNetwork,
			String networkModel,String isLeagueSaleDept) {
		//非合伙人不需要加收
		if(!"Y".equals(isLeagueSaleDept))
			return false;
		// 二级网点，且为到达模式、简装模式、代理模式时，需要收取加收费用
		if ("Y".equals(isTwoLevelNetwork)
				&& ("ARRIVAL_MODEL".equals(networkModel)
						|| "SIMPLE_MODEL".equals(networkModel) || "PROXY_MODEL"
							.equals(networkModel)))
			return true;
		return false;
	}

	/**
	 * 根据gis网点匹配条件查询目的网点信息
	 * 
	 * @author 321993 zhangdinahao
	 * @date 2017-03-16 下午 21:35:53
	 */
	@Override
	public HisSegMatchResponse queryStationInfo(HisSegMatchRequest request) {
		// 根据gis网点匹配条件查询网点code
		return gisQueryService.queryStationInfo(request);
	}
	
}
