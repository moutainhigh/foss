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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/WaybillHessianRemoting.java
 * 
 * FILE NAME        	: WaybillHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.framework.shared.util.string.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.security.SecurityNonCheckRequired;
import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationNewService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepotAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDeptTransferMappingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEarlyWarningService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILtDiscountBackInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStoreBillingMapService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterEntity;
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
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
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICityMarketPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
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
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.OrderStatusToCrmDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IInsurGoodsDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPrintInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IProhibitGoodsDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISysConfigDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEcsQueryService;
//import com.deppon.foss.module.pickup.waybill.api.server.service.IEcsQueryService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IIntelligenceTimeRecordService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPartnersWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISendSmsVoiceService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISynPartenerCodAmountUpperLimitService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISynPartenerMonthlyLineDeductService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISynPartenerPrestoreDeductService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IUploadITService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillPicturePushLogDto;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillRfcService;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IGrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
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
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpReturnGoodsApplyResultDto;
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
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillQueryException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.request.HisSegMatchRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.HisSegMatchResponse;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.pickup.waybill.shared.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.SearchPictureVo;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.server.service.IGreenHandWrapWriteoffService;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.module.transfer.common.api.server.service.IQuerySwiftNumberInfoService;
import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.ISortingScanService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.pda.bdm.module.push.server.service.IPushService;

/**
 * 
 * 运单远程服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-23 下午8:17:15,content
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-23 下午8:17:15
 * @since
 * @version
 * 
 * 录入货物信息SUC业务规则
 * 1.	若货物为违禁品，则系统自动提示“货物为违禁品，不可开单！”；
 *2.	若货物为贵重物品，则系统自动勾选“贵重物品”，且不可修改；
 *3.	若货物为限保物品，则系统自动限定保价金额，且不可修改，并提示“货物为限保物品”；
 *4.	违禁品、拒收品、贵重物品、限保物品（含保价金额上限）具体类型可在系统中进行配置；
 *1.	货物重量单位为千克；
 *2.	运单开单时，货物的件数、重量及体积文本框是必填项，默认值为空； 
 *3.	件数只能是大于等于1的整数，重量及体积只能大于0。
 *4.	体积、重量这种过程数据保留两位小数
 *1.	尺寸录入文本框，支持'长*宽*高*件数+长*宽*高*（即多个尺寸相加）的计算；
 *2.	如果件数是1，则不强制在录入时要“*1”；
 *3.	货物尺寸为计算器输入，输入的尺寸可以进行加减，例如：1*1*1*5+2*2*2*3-0.5*0.5*0.5，
 *显示为输入文本；
 *4.	尺寸计算单位为厘米，尺寸计算出数据后转换单位为立方米后，在货物体积中显示数据；
 *例如：尺寸录入为：50*50*20（其中20为件数），则体积显示数据为：0.05； 
 *5.	体积为空时，录入尺寸后，填充体积。当尺寸修改时，体积随之变处。当修改体积时，尺寸不变化。
 *6.	体积初始值为“0”，仍保持必填，操作员根据实际情况改，选择木架或木箱或两者都选择后则该值不能为0
 *1.	货物体积单位为立方米；
 *2.	营业员可以修改通过尺寸计算器计算得出的体积数据；
 *3.	系统设置货物重量体积比区间值（该值由基础资料配置），在运单提交时，系统自动对重量体积比进行校验：
 *即重量体积比X=重量/体积； 
 *3.1	当X不在设置的区间中，弹出提示“请确认录入的重量体积是否准确！”；
 *（该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面；点击取消，点返回运单录入界面；
 *3.2	当X在区间中，无提示；直接进入确认运单信息界面；
 *4.	录入重量体积后，系统校验单票的重量体积及单件（平均单件）的重量体积是否
 *满足“修改-查询行政组织业务属性”基础资料中的单票和单件重量体积限制；只要该四项中有
 *一项不满足，则提示“XX超出提货网点限制，请重新选择提货网点！”； 
 *1.	货物包装总件数小于等于货物总件数，如果大于总件数，提示：“包装件数不能大于总件数”；
 *1.	当木包装件数大于等于1时，系统校验出发城市和对应目的站的走货路由中是否有有打木架功
 *能的部门，若有，则提醒营业员“是否收入代打木架？”，是，则进入场代打木架信息录入界面”，
 *并显示第一个有打木架功能的部门名称；若走货路由中无有打木架功能的部门，则不提示“是否收入代打木架？”；
 *2.	当走货路由中有有打木架功能的部门为多个时，只显示系统路由中第一有有打木架功能的部门，
 *且不可修改；
 *1.	运输类型为汽运时，货物类型为唯一选择项；即，非A即B；默认不可勾选，只有当走货路由经过
 *特定的城市时需要录入货物类型,特定城市可在系统中进行配置；
 *2.	运输类型为空运时，货物类型为下拉选择框，默认显示为普货，目前只有这一个分类，该类型可做配置；
 *1.	贵重物品判断规则： 
 *a. 单票单件货物，体积≤0.05立方且保价声明价值≥1万元； 
 *b. 单票多件货物，平均单件体积≤0.2立方且平均单件保价声明价值≥1万元；
 *（注：平均单件体积=开单总体积÷开单件数，平均单件保价=开单保价声明价值÷开单件数） 
 *c. 高保价货物，单票货物保价≥10万元； 
 *满足以上任意一个条件时，系统将判定该票货物为贵重物品并自动勾选"贵重物品"复选框，且为灰色，
 *不可编辑；不满足以上条件时，该复选框为可编辑状态，用户可根据实际情况自行选择是否勾选； 
 *2.	若"贵重物品"复选框被勾选，则在【储运注意事项】中自动加入提示记录"贵重物品"字段，
 *若该货物为贵重物品时，"储运注意事项"中信息显示优先级为：贵重物品＞其他；
 *1.	录入的打木架货物件数和打木箱件数之和必须大于等于录入的木包装件数；
 *2.	系统默认标签流水号前X的货物为需要代打木架货物，X等于录入的“打木架货物件数”和“打木箱货物件数”之和；
 *3.	营业员在打印标签时，按流水号先贴要打木架或打木箱的货物；
 *1.	录入的打木架货物体积和打木箱货物体积之和乘以1.4必须小于等于货物总体积；该1.4为打木架体积计算系统，
 *可配置；
 *2.	营业部开单时按打完木架后的包装开，即包装中含“木架/木箱”，开单件数为货物打木架/木箱前的实际件数
 *（防止丢货），尺寸和重量按照以下公式计算：
 *1.1	整票货物代打时：开单体积=代打货物体积*1.4；开单重量=所有货物重量+代打货物体积*42；
 *1.2	部分货物代打时，开单体积=代打货物体积*1.4+未打木架货物体积；开单重量=所有货物重量+代打货物体积*42。
 *即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，重量另加，单票中未打木架的货物的体积和重量不变；
 *1.3	例如：一票货物需全部代打，货物体积为1个方，重量为100KG，则开单体积为1.4个方，
 *开单重量为100+1*42=142KG，收取客户包装费为150*1.4=210元；
 *1.4	需要加托时，仍按照50元/个另外收取费用，托的重量和体积不再另加；营业部不需要再更改由于打
 *木架引起重量和体积的变化；
 *3.	打木架要在“对内备注”中备注“代打木架/木箱”，特殊要求（特别是合打情况）必须在对内备注
 *和A4纸上都注明，例如：货物1、2、3合打成一件等；
 *4.	开单件数为代打木架前货物实际件数，包装为打木架后的包装，打木架后件数发生变化后，需及时更改件数；
 *
 *
 *录入运输信息SUC业务 规则
 *1.	收货部门默认为操作者所在的部门，不可修改；
 *2.	若操作者部门为集中开单部门时，则收货部门可修改，其选择部门只能为开单组服务的集中接货区域营业部；
 *且其进进入运单开单界面时，系统自动默认收货部门为上一次开单的收货部门；
 *1.	默认显示精准；
 *1.	根据部门的性质或所在城市确定部门所作业务的性质，如有些部门可做所有运输性质的业务，
 *部分部门无法做精准业务，所有规则根据营业部业务管制确定（根据出发城市、到达城市确定部门可做的业务性质，
 *所有数据读取自营业部业务管制基础资料）；
 *1.	当运输性质为空运时，提货方式有：自提（不含机场提货费）、免费自提、机场自提、免费送货、
 *送货进仓、送货上楼、送货（不含上楼)；默认为自提（不含机场提货费）；
 *2.	如果客户是CRM中的合同客户，在开单时提货方式选项中增加"免费送货"选项，免费送货送货费为0（不可修改）；
 *3.	当运输性质为精准、普货、偏线时，提货方式有：自提、免费送货、送货进仓、送货上楼、
 *送货（不含上楼)、内部带货自提；默认为自提；
 *1.	当运输性质为空运时，开单提货网点显示空运代理网点及公司可做空运的自有网点；
 *2.	当运输性质为偏线时，提货网点只显示偏线代理网点；
 *3.	当运输性质为精准、普货时，提货网点显示我司所有可所到达的网点；
 *1.	当运输性质为空运，且提货方式为机场自提时，增值服务的其它费用中可添加对就有规则的费用
 *（暂定方案：对于单独开单的可做增值服务费用添加。若以后在产品定义中区分了单独开单和合大票，则另考虑）；
 *1.	提货方式为机场自提时，代收货款设置为0且不可编辑，不能开单含到付款，，付款方式不能开到付；
 *否则，提示：“提货方式为【机场自提】时，到付金额必须为0，付款方式不能为到付”；
 *2.	提货方式为内部带货自提时，只能填写发货人收货人信息和货物信息，所有涉及金额的控件均为0，
 *收货人和发货人只能为OA系统中部门；
 *1.	提货方式为自提（不含机场提货费）、免费自提、内部带货自提时，只显示可做自提业务的网点；
 *2.	提货方式为送货时，只显示可做送货业务的网点；
 *1.	目的站可由收货客户地址的城市（即收货客户地址中的“市”）生成，也可手工录入；
 *2.	系统自动过滤只显示目的站城市符合条件的网点信息；
 *3.	选择确定提货网点信息后，系统自动生成对应网点的目的站名称于目的站框内；
 *网点目的站读取对应的网点目的站基础资料；
 *4.	在网点目的站基础资料中有‘取消到达日期’，如果当前日期在‘取消到达日期’之前，
 *那么提示“xx营业部将于xx年xx月xx日临时取消到达，届时货物将转至xx营业部，请做好客户解释工作！”
 *（其中第一个xx营业部，为当前营业部、第二个xx营业部为网点目的站基础资料中的‘转货部门‘，
 *xx年xx月xx日为‘取消到达日期’）
 *5.	录入重量体积后，系统校验单票的重量体积及单件（平均单件）的重量体积是否满足
 *“修改-查询行政组织业务属性”基础资料中的单票和单件重量体积限制；只要该四项中有一项不满足，
 *则提示“XX超出提货网点限制，请重新选择提货网点！”； 
 *1.	当通过运输性质、提货方式和目的站过滤的提货网点唯一时，直接显示提货网点名称；
 *1.	当勾选上门接货时，自动显示文本框，录入接货司机工号，接货费数字框可录入，手写现付金额，可编辑
 *2.	当不勾选上门接货时，接货费清0变灰且不可录入，手写现付金额变灰，不可编辑
 *1.	当录单部门为集中开单部门时，自动勾选上门接货，且不可修改；
 *2.	上门接货默认不勾选，可修改；3.	
 *1.	对外备注可多选，选择的项目信息依次显示在储运注意事项中；默认为空；
 *2.	当选择空时，则其它所有选项自动不勾选；
 *3.	对外备注已选择录入后，若再选择空，则清空已选择的所有对内备注；
 *1.	储运注意事项=对外备注&对内备注&大车直送（若勾选大车直送），各字段以“；”分开；
 *2.	对外备注永远在储运注意事项的最前面；
 *1.	当运输性质为精准、普货、偏线时，显示配载线路；系统自动根据营业部所在城市和到达目的站
 *匹配走货线路基础资料，生成预配线路，且不可修改；
 *2.	当运输性质为空运时，显示配载航班，包括：早班、中班、晚班、中转航班；默认为空；
 *1.	系统自动匹配始发配载部门基础资料，通过配载类型来判断配载部门；
 *1.	如果提货网点为自有网点时，最终配载部门为提货网点名称；如果提货网点不是自有网点，
 *则最终配载部门为外发代理网点的管理部门；（参考基础资料：外发代理、部门基础信息基础资料）；
 *2.	当运输类型为空运时，最终配载部门可编辑，且列举对应收货部门可走空运货的空运总调，
 *默认显示为空；若对应收货部门可走空运货的总运总调唯一时，则直接显示；
 *1.	预计出发时间在运单提交时进行判断填充；
 *2.	预计出发时间适用于运输类型为“精准”；
 *3.	预计出发时间=预计出发日期,准点出发时间。【格式如：2011-6-28 ,12:00:00】
 *（部门对应的“准点出发时间”，数据取自基础资料：经营-运作基础资料）；
 *4.	如果开单当前时间在准点出发时间前，则预计出发日期=开单日期；否则，预计出发日期=开单日期+1；
 *5.	当运输性质为普货、偏线时，则预计出发日期=开单日期+1；
 *1.	预计提货/派送时间适用于运输类型为“精准”；
 *2.	提货方式为“自提”时，若部门对应的“是否当天出发”为“是”，
 *则预计提货/派送时间=预计出发日期+到达营业部承诺天数：到达营业部承诺时点；
 *否则，预计提货/派送时间=预计出发日期+到达营业部承诺天数-1：到达营业部承诺时点；
 *（部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准；
 *“到达营业部承诺天数”和“到达营业部承诺时点” ，数据取自基础资料：专线-城市承诺时间标准；）
 *3.	提货方式包含为“送货 ”时，若部门对应的“是否当天出发”为“是”，
 *则预计提货/派送时间=预计出发日期+到达营业部承诺天数+派送承诺需加天数：
 *派送承诺时点；否则，预计提货/派送时间=预计出发日期+派送承诺需加天数-1：
 *派送承诺时点；（部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准；
 *“到达营业部承诺天数”、“到达营业部承诺时点”、“派送承诺时点”、“派送承诺需加天数”，
 *数据取自基础资料：专线-城市承诺时间标准；）
 *1.	大车直送默认不勾选，且不可修改；
 *2.	当单票货物重量超过8吨或体积大于25方时，系统提醒“是否需要大车直送？”；
 *营业员确定后，自动勾选大车直送，且大车直送变为可修改状态；
 *3.	勾选大车直送时，在储运注意事项中增加大车直送显示；不勾选时，不显示；
 *1、检验单号是否唯一（有效状态的运单唯一，中止/逻辑删除等的运单不参与检查），
 *如果唯一系统不做操作，如果不唯一，系统提示“XX单号已经使用，请重新输入单号”
 *2、新增一条业务规则：在提交运单之前，若相邻两次输入的单号（两次单号分别为A与B且单号输入合法）
 *差别较大时，系统给予用户友好提示避免录错单号，但不限制单号的输入。具体规则为： 
 *1）若前后两次输入的单号位数相同，当|A-B|≥100时系统给予提示； 
 *2）若前后两次输入的单号位数不同，系统给予提示； 
 *3）提示信息为：前后两票单号相差过大，请检查所输单号是否是本部门所属单号！
 *
 ** 增值服务SUC规则如下
 * 1.1	相关业务用例
 *BUC_FOSS_5.20.30_510  确认承运信息
 *1.2	用例描述
 *营业员通过本用例录入增值服务信息。
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件		
 *后置条件		
 *1.4	操作用户角色
 *操作用户	描述
 *营业员	可查询、录入、修改增值服务信息
 *1.5	界面要求
 *1.5.1	表现方式
 *Web方式
 *1.5.2	界面原型
 *1.5.2.1	录入增值服务信息
 *1.5.2.2	查询发货客户代收货款退款联系人
 *1.5.2.3	查询其它费用 
 *1.5.3	界面描述
 *营业员点击运单开单，进入运单开单界面。
 *本界面为录入增值服务信息。
 *界面主要分为二个部分：录入增值服务、查询其它费用。
 *1.	录入增值服务
 *录入增值服务分为三个部分：录入增值服务信息界面、录入其它费用列表、查询发货客户代收货款退款联系人；
 *1.1	录入增值服务信息界面
 *录入增值服务信息界面包括： 
 *1.1.1	保价声明价值；
 *1.1.2	保价费率：保价费率可由基础资料配置，可按出发城市区域，出发营业部等；
 *1.1.3	保价费；
 *1.1.4	代收货款；
 *1.1.5	代收费率；
 *1.1.6	代收手续费；
 *1.1.7	退款类型：包括三日退、退日退、审核退，默认三日退为空；
 *1.1.8	收款人姓名；
 *1.1.9	收款人帐号；
 *1.1.10	包装费；
 *1.1.11	装卸费；
 *1.1.12	送货费；
 *1.1.13	其它费用合计；
 *1.1.14	接货费；
 *1.1.15	返单类别：包含“无需返单”、“客户签收单原件返回”、“客户签收单传真返回”、
 *“运单到达联传真返回”包括无需返单、原件签收单返回、传真件签收单返回、扫描件返回，默认无需返单；
 *1.1.16	预付费保密；
 *1.2	录入其它费用列表
 *录入其它费用列表包括：
 *1.2.1	费用名称；
 *1.2.2	费用金额；
 *1.2.3	新增其它费用：功能按钮；
 *1.2.4	删除其它费用：功能按钮；
 *1.3	查询发货客户代收货款退款联系人
 *1.3.1	操作列；
 *1.3.2	开户银行；
 *1.3.3	收款人姓名；
 *1.3.4	银行帐号；
 *1.3.5	备注信息；
 *1.4	录入包装费
 *2.	查询其它费用
 *查询其它费用分为四个部分：其它费用列表 、功能按钮；
 *2.1	其它费用列表：
 *其它费用列表包括：
 *2.1.1	名称；
 *2.1.2	归集类别；
 *2.1.3	描述；
 *2.1.4	金额；
 *2.1.5	金额上限；
 *2.1.6	金额下限；
 *2.1.7	是否可修改；
 *2.1.8	其它费用可基础资料配置，并支持是否启用和是否可见的维护；
 *2.2	功能按钮：
 *2.2.1	查询：包括查询条件：名称；
 *2.2.2	确定；
 *2.2.3	取消；
 * 1.	限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,
 * 且保价费率可手动调整；（限保物品从限运物品基础资料中获取）；非限保物品的保价费率不可修改；
 *2.	实际保险费小于最低保费的按最低保费收取；
 *3.	保价费 = 保价申明价值*保价费率，不可修改；
 *4.	保价申明价值默认为3000，可以修改；保价声明价值不封顶；
 *5.	精准（长途）、普货（长途）、偏线，最低一票8元；普货（短途）、
 *精准（短途）最低一票6元；空运最低一票10元；所有运输方式保价超过最低均按0.4%收取
 *（数据读取自保价设置基础资料）；长短途数据读取计价基础资料；
 *6.	实际保价费小于最低保费的按最低保费收取；
 *7.	保价费率首先是配置的标准费率。当有区域保价费率时，以区域保价费率为准。
 *当客户为合同客户时，则以合同签订为准。所有的保价费率以读取的为准，不可修改。
 *限保物品的保价费率同样不可修改
 *行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，
 *开单是否可以进行返回签单、货到付款、代收货款需要根据到达部门属性
 *是否可以（返回签单、货到付款、代收货款）来决定
 *1.	如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，代收货款设置为0且不可编辑；
 *2.	如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
 *代收货款可编辑，且可以选择所有的退款类型，若CRM中若无账户信息，则退款类型只能选择审核退
 *3.	开单时系统默认代收货款为空；
 *4.	代收货款栏默认为空，如果没有代收货款，则要求输入0；否则，进行提示：“请确认该单没有代收货款，
 *如无，请输入数字0”；当代收货款大于0时，输入后，对于选择的退款类型，有如下限制：
 *3.1	三日退：在收到客户代收货款后第三天给客户打款。
 *3.1.1	默认退款类型为三日退；
 *3.1.2	代收10000元以下费率0.5%， 10000元以上费率0.4%；最低10元/票，最高100元/票；
 *有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 *3.2	审核退：收到客户代收货款，出发部门审核后，给客户打款。
 *3.2.1	代收10000元以下费率0.5%，10000元以上费率0.4%；最低10元/票，最高100元/票（
 *通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 *3.2.2	选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。
 *3.3	即日退：在收到客户代收货款后24小时到账。
 *3.3.1	代收手续费率1%，最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市
 *与部门代收货款费率”实现）。
 *3.3.2	必须先录入客户收款银行信息，提交时，银行信息不能为空；
 *3.3.3	只支持4个银行：农行、工行、建行、招行；否则，给予提示信息；
 *5.	限制代收货款金额不能小于10元，可以等于10元；但可以为0；该数字“10”可由基础资料配置；
 *6.	网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，有数据时不可对代收货款进行修改，
 *只可起草出发更改进行修改；若网上代收货款为0 ，系统可支持修改代收货款金额；
 *7.	默认的代收费率由基础资料配置；
 *1.	保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额；
 *1.	代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，且只能选择，不能修改；
 *当退款人姓名和帐号唯一时，直接显示；（数据读取CRM客户信息资料（退款帐户信息））
 *2.	CRM客户信息资料的要先在CRM中录入客户退款帐户信息，且第一次在我司办理代收货款业务时，
 *退款类型只能为审核退；
 *3.	同一客户多个银行信息的显示问题：当有两个或以上账号时，弹出账号信息（包括开户银行、收款人、
 *账号、备注），选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置；
 *1.	包装费默认为0，可手工修改；
 *2.	当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
 *且可修改，修改的金额只能大于等于默认显示金额；其中150、30、300、40为打木架单价（元/方）、打木架最低一票、
 *打木箱单价（元/方）、打木箱最低一票，可由基础资料配置；
 *1.	装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 *即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
 *（其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 *开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
 *2.	如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，为系统默认的公布价。
 *开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，开单显示运费（C）=Q*Z=C0+M ；
 *3.	当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），
 *需先清空装卸费为零，然后再修改数据。如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
 *4.	当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 *如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。否则，清空装卸费为零，
 *最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。（其中Q1是装卸费M1时的费率,Z为重量/体积）；
 *5.	系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。（只限制配载类型为专线的，包括月结）；
 *6.	对于显示费率不等于显示费率乘以重量的问题，要求如下：
 *6.1.	若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
 *且显示运费等于该显示费率*重量；
 *6.2.	若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，令费率（B）=费率（A）
 *的取前2个小数位的值（注：直接截取A的值，不四舍五入）。则显示费率（Q）=费率（B）+0.01。
 *显示运费等于该显示费率*重量；
 *7.	只要含与不含装卸费两者有交叉的，均以不含为准；
 *8.	装卸费特殊部门表： （建议：做为可配置的基础数据表）
 *9.	2012年12月1日开业的部门不能开装卸费
 *10.	如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
 *11.	是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
 *12.	装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
 *1.	送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，
 *不能下调。当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值，
 *但用户可以上调送货费；
 *2.	通过送货费基础资料来实现：
 *2.1.	若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止；
 *2.2.	若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
 *（即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值，
 *取值终止。）
 *2.3.	特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 
 *2.3.1	先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，再判断开单重量在已被体积筛选
 *出来的记录中的哪个重量区间，来确定是哪一条记录。然后再根据费率计算，计算出来的值与该条的最低
 *送货费进行比较，若小于最低送货费时，就取最低送货费，取值终止；若大于最低送货费且小于最高送货费时，
 *就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。
 *2.3.2	标淮派送范围收取送货费标准：
 *货物重量	标准
 *0-300KG	55元/票
 *301-500KG	0.2元/KG
 *501KG或2.5立方米以上	100元/票，不封顶
 *2.3.3	当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
 *开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于
 *【最高送货费】”
 *2.3.4	仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。
 *2.3.5	“月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也
 *可以向下修改，最小为0）
 *2.3.6	除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改
 *2.3.7	最高送货费做基础资料配置；
 *3.	非标准派送范围加收操作费标准：
 *3.1	超远加收送货费标准：
 *距离（公里）	30-50	50-100	100-150
 *加收金额（元）	50	100	150
 *3.1.1	距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）；
 *3.1.2	客户所在地30公里范围内如果有公司的营业网点，无论是否做派送，该区域均不能收取超远加收送货费；
 *3.1.3	非标准派送的费用添加无上限
 *3.2	特殊区域（进仓）：
 *3.2.1	特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）；
 *3.2.2	收费标准：进仓费实报实销，并加收150元操作费；
 *4.	区域送货费限制：
 *4.1	当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，但前提是：提货方式必须为送货)时，
 *送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置）
 *4.2	当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦
 *营业部、深圳华强钟表市场营业部）。（该类营业部类型、送货费固定标准、成本提取标准可配置）
 *4.3	内部带货、空运、偏线及中转下线不受上述需求的限制。
 *5.	限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域：
 *5.1	当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，
 *或体积是否超过X立方，是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。
 *（单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】
 *5.2	当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，其他费用中的“送货上楼费”屏蔽或显示但不可选择；
 *5.3	若“XX区域”或其它限制类型区域再开派送部，适用以上需求；
 *5.4	空运、偏线及中转下线不受上述需求的限制；
 *5.5	内部带货受上述需求的限制；
 *5.6	 “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置；
 *1.	开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
 *费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置）
 *2.	运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 *运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置）
 *3.	综合服务费：（费用金额由基础资料配置）
 *3.1	综合服务费默认为2元不可修改、剔除；
 *3.2	月结客户可以删除2元的综合服务费；
 *3.3	淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费；
 *4.	燃油附加费：（费用金额由基础资料配置）
 *运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 *运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；
 *5.	其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改；
 *6.	其他费用合计等于其他费用列表中各项费用数据之和；
 *1.	原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单；
 *2.	空运、偏线和中转下线的“返单类型”不可选择；
 *3.	若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用；
 *4.	如果选择有返单类型，系统会自动生成一条签收单记录，记录信息包含：运单号、运单ID、库存状态、
 *当前操作部门（运单开单时，是填开部门）、是否签收、是否作废、出发部门(运单开单出发部门)、
 *签收单类型、签收状态；
 *5.	月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
 *非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除
 *1.	运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密；
 *2.	已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。未开启预付运费保密运单提交后，
 *若货物未有非本部门入库操作，则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
 *则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单；
 *3.	运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择；
 *4.	已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，涉及预付运费有变动时，
 *不影响预付费保密功能；
 *5.	开启预付运费保密，预付运费不能为0，否则不能保存、提交；
 *6.	开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行；
 *7.	开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时，
 *必须取消预付运费保密后才能提交；
 *8.	预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示；
 *9.	预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示；
 *1.	；
 *2.	1）开单总费用、预付金额、到付金额，取整，四舍五入； 
 *2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100,
 *100.10显示100.1； 
 *3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。 
 *4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。
 *1.	接货费只能录入数字
 *1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 
 *2）限保物品的保价费率通过基础资料进行配置； 
 *3）取消偏线、空运最高保价5000元的限制； 
 *2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置 
 *3、"其它费用"中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。 
 *4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制
 *
 *
 * * 运单收银SUC业务规则
 * 1.	计费类型分为重量计费、体积计费，由系统自动生成，不可修改；默认重量计费；
 *2.	对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费；
 *若按重量计费运费较按体积计费运费较高，则计费类型为重量计费；
 *若按体积计费运费较按重量计费运费较高，则计费类型为体积计费；
 *3.	运输类型为汽运时,计费重量为空，不可修改；
 *4.运输类型为空运时计费重量应为重量和体积*1000000/6000进行对比，取大；
 *1.	费率为对应计费类型、目的站、提货网点及运输类型的走货单价；
 *2.	目的站、提货网点及运输类型确认后，即可自动显示对应计费类型的费率；
 *（来自价格基础资料）
 *3.	费率可以保留到小数点后2位；运费、预付金额、到付金额为整数，
 *按照四舍五入的原则；
 *1.	公布价运费、增值服务费用、优惠合计需通过点击计算费用获取；
 *2.	当提货网点信息未录入，不可计费运费；当录了提货网点，
 *货物的重量和体积以及其他服务费用录入不完整时，
 *点击计算费用，得到当前填写的信息费用信息；
 *3.	公布价运费（即重量、体积计费的运费）=每公斤单价/每方价格与货物实际重量/体积的乘积，
 *对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费；（来自价格基础资料）
 *4.	增值服务费=送货费+包装费+保价费+代收手续费+接货费+其他费用合计；
 *5.	优惠合计=优惠总合计；
 *6.	总运费=公布价运费+增值服务费-优惠合计=预付金额+到付金额；
 *7.	在提交运单时，系统记录该次计算费用时所用的所有价格费用的规则版本号；
 *8.	  采用费用明细取整、总费用取整的原则
 *9.	任何客户包含月结客户不可减免综合服务费。
 *10.	开单界面选择付款方式为"现金"时，默认预付金额为开单总金额并允许修改，且修改的同时到付
 *金额随预付金额变化而变化，变化规则为：到付金额=总金额-预付金额，到付金额不能修改。 
 *例如，开单界面选择付款方式为"现金"时，当一票货的总金额为300元时，默认预付金额为300元，到付
 *金额为0，将预付金额改为100元之后，到付金额变为200元
 *1.	必须输入提货网点、货物的重量和体积以及其他服务费用，计算出总费用后再输入验证编码进行验证；
 *（优惠券编码开单时，对运单有上下限金额的限制，该上下限可配置）
 *2.	输入优惠券编码后，焦点转移时，系统调用CRM接口，校验该编码是否存在，如果存在，则将优惠信息
 *显示在优惠信息列表中，费用为对应的优惠券的金额；如果不存在提示为：你输入的优惠券编码不存在，请重新输入；
 *3.	当修改优惠编码时，则焦点转移时，系统自动调用CRM接口，校验该编码，同时修改对应的优惠减免
 *记录和优惠信息；
 *4.	优惠金额必须小于等于开单总金额；
 *5.	当付款金额为单一的现付或到付时，优惠券可以单独减免，当付款金额既有现付又有到付时，优先选
 *择减免现付金额，如存在优惠金额此时减免现付有余时，可再充抵到付费用，但优惠金额必须小于等于开单总金额；
 *6.	暂存时，只显示减免金额，运单实收或应收金额上不显示，即暂存时，不生成实收或应收单据；
 *7.	当费用变化时，优惠编码必须重新输入；8.	
 *1.	当发货客户有月结客户属性时，才可选择月结；并默认显示付款方式为月结；
 *2.	当发货客户有信用额度的属性时，才可选择临时欠款的付款方式；
 *3.	支持发货客户的多种付款方式叠加，但月结和临时欠款不可同时出现；
 *4.	当开单为订单导入开单，且为网上订单，并选择了网上支付时，导入开单后的付款方式显示网上支付；
 *同时付款方式可修改，付款方式中增加网上支付选项；
 *5.	开单为空运、偏线、中转下线时不能选择“网上支付”；
 *1.	当付款方式为现付时，预付金额必须大于0；否则，提示信息“付款方式非到付，预付金额不能小于等于0”；
 *2.	当付款方式为到付时，预付金额不能大于0；否则，提示信息“付款方式为【到付】，预付金额不能大于0”；
 *3.	月结客户的信用金额直接限制该客户的当月发货金额，当月发货金额超过信用额度，当月将无法再开单月结，
 *提示“该客户的剩余可用信息额度不足，不能开月结”；
 *4.	临时欠款时，需要客户中的信用额度超过运费，否则，提示“该客户的剩余可用信息额度不足，
 *不能开临时欠款”；
 *
 *确认会员资质SUC规则
 *1.	签约合同客户：
 *1.1	营业部合同客户只享受合同优惠；当有促销的优惠时，合同客户可享受最大优惠折扣，
 *但不叠加；（促销优惠为基础资料配置属性）
 *备注：当运单为网络订单（官网订单、阿里巴巴订单、淘宝订单、呼叫中心订单），
 *且客户为合同客户时，为了保证不重复享受折扣，
 *除运单是阿里巴巴订单且月结客户优惠类型为“普通货物打折方案”以阿里巴巴费率计算外，
 *其他情况都按照合同优惠来进行计算；
 *1.2	客户为非本部门的合同客户时，不在本部门享受合同优惠；；
 *合同客户有绑定其他营业部时，可在所绑定的其他营业部享受合同归属部门同等优惠；
 *1.3	所有合同客户可以减免综合服务费；
 *1.4	所有合同客户享受合同签订的保价费率和代收货款费率；
 *1.5	合同客户通过月结审核的，可选择付款方式为：“月结”；
 *其他客户不能选择为“月结”；
 *1.6	合同客户为“价格折扣”优惠时，客户发货除最低每票的运费不受影响外，
 *其他运费超过最低每票标准的，总运费按照其折扣比例打折优惠（折后总运费不低于最低每票标准运费）；
 *1.7	合同客户为“月发月送”优惠时，客户发货价格按照月发月送标准；
 *1.7.1.	开单不能含装卸费；
 *1.7.2.	当月发越送与空运同时存在时，以空运规则为准，可开装卸费；
 *1.8	合同客户有免费送货属性的，在优惠信息列表中自动优惠标准派送的送货费用，
 *对于月结属性的客户可以向下修改送货费，最小为0；其它所有情况的送货费不可向下修改，只能向上修改；
 *1.9	合同用户仅在合同有效期内享受合同优惠。
 *1.	非公司签约客户：
 *1.1	发货客户只能选择本部门的客户，但通过会员卡号可查询其他部门客户信息；
 *若客户为其它部门合同客户时，客户不享受合同优惠；
 *1.2	客户优惠信息由基础资料配置，优惠信息包括：我司享受优惠区域部门、
 *优惠类型（如线路优惠、货物类型优惠等，可配置）、
 *优惠名称，对应的的优惠的具体信息；
 *1.3	优惠类型有优先级，其优先级由基础资料配置；
 *（当普通优惠与促销优惠同时同在时，促销优惠优于普通优惠；）
 *1.4	系统自动根据承运信息，给出对应运单可享的优先级最高的优惠，
 *、当最高优先级并列出现时，默认勾选最大优惠的优惠方案；
 *对于同一优惠类型的优惠，存在互拆；
 *例：当线路优惠和区域优惠同时存在时，系统只给出线路优惠；
 *（点面原则（异常优先）：即点面同时存在时，以点为准。
 *如：优惠1：上海出发货8折优惠；优惠2：上海到广州9优惠，
 *由于优惠2包含于优惠1中，为优惠1的一个异常，
 *则在生成优惠信息时，只显示优惠2的优惠方案）；
 *1.5	阿里巴巴客户：
 *1.5.1	阿里巴巴订单导入开单时，对于诚信通会员，
 *系统按如下方式进行折扣：
 *运输类型
  *限制要求                 	普货	精准（长/短途）
 *起步价格	30元	40元/20元
 *小于1000公斤或5立方		
 *大于等于1000公斤或5立方		
 *（该折扣表内容由基础资料配置）
 *备注：对于阿里的普通会员，系统只减免2元的综合服务费。
 *1.5.2	阿里巴巴订单导入开单时，在“优惠方案中”自动新增“阿里巴巴优惠费”项，
 *且系统自动计算优惠金额。公布价仍显示为公司散客开单标准的公布价。
 *（阿里巴巴优惠费=公司标准公布价总运费—阿里巴巴折扣的总运费）；
 *也为公布价优惠的一种；
 *1.5.3	阿里诚信通会员客户下单后，阿里订单导入开单，
 *且“该客户同时为部门的月结客户，其月结优惠类型为‘普通货物打折方案’时”，
 *则开单时阿里价格优于月结客户价格，以“阿里诚信通会员价格方案”计算运费；
 *其他的月结优惠类型(公布价、价格折扣、月发月送)开单时月结客户价格优于阿里价格规则；
 *1.5.4	当阿里巴巴订单开空运和偏线不享受对应的阿里巴巴优惠，
 *但可享受正常开单的优惠；
 *1.	客户享受的优惠类型自动显示在优惠信息显示列表中；
 *2.	合同优惠包括公布价折扣优惠和增值服务优惠；
 *3.	对于公布价优惠：只显示客户可以享受的公布价优惠信息，
 *且默认勾选“合同规定的优惠方案”或“与合同优惠有冲突时，
 *系统规则使用的优惠方案”的公布价折扣优惠；
 *4.	当客户为合同客户时，不仅显示合同公布价优惠，
 *还显示对应线路或货物类型或货物种类等其它配置的所有与本次承运相关的公布价优
 *惠方案中系统规则使用的较合同优惠更优的优惠方案；
 *5.	当客户为合同客户时，若勾选非合同公布价优惠时且为非促销优惠时，
 *则不再享受对应客户的所有合同优惠（包括公布价优惠、增值服务优惠及月发月送、月结等优惠）；
 *6.	当客户为非合同客户时，自动默认勾选系统规则可使用的折扣最低的公布价优惠方案；
 *7.	公布价优惠方案只可勾选一种，不可多选；
 *8.	合同客户的增值服务优惠项不可修改（即不可以取消或增加勾选），
 *非合同客户的增值服务优惠项可修改（即可以取消或增加勾选）
 *9.	任何客户包含月结客户不可减免综合服务费
 *1.	公布价优惠金额=本次承运的公布价总价*（1-优惠折扣）；
 *2.	增值服务优惠金额：
 *2.1	保价费和代收手续款的优惠金额=声明价值/代收货款*（
 *公司标准的保价费率/代收费率-签约合同的保价费率/代收费率）；
 *2.2	其它优惠或费用减免由优惠规则配置生成；
 *1.	只有月结属性的客户才可以享受月结的付款方式；且当客户为月结客户时，
 *付款方式自动默认为月结；
 *2.	只有信用额度的客户才可以享受临时欠款的付款方式；且对应客户的信用额度全国统配，
 *不绑定部门，例：客户A在我司可享信用额度为5000，且其合同主体为部门a，
 *A已用额度为2000，且无论客户A在a部门发货，或是在其它任务我司的部门发货，
 *其可用额度均为3000；
 *3.	系统自动过滤客户不能享受的付款方式；
 *
 *
 *
 *录入发货客户信息SUC业务规则
 *营业员点击运单开单，进入运单开单界面。
 *本用例分为两个界面：录入发货客户信息、选择发货客户；
 *1.	录入发货客户信息：
 *界面为信息录入界面：包括：手机、电话、客户名称、
 *客户编码、发货联系人（发货部门）、发货人地址；
 *1.1	手机：发货人手机号码；
 *1.2	电话：发货人电话号码；
 *1.3	客户名称：发货客户公司或单位名称，可支持搜索查询；
 *1.4	客户编码：我司给客户的客户号；
 *1.5	发货联系人（发货部门，支持模糊搜索）：
 *发货客户的客户姓名，
 *当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“发货联系人”字段更改为“发货部门”；
 *1.6	发货人地址：发货客户的详细联系地址，
 *支持国家行政区域自动过滤；
 *2.	选择发货客户界面：
 *界面为选择客户信息界面：包括两部分：
 *客户信息列表区域、功能按钮区域；
 *2.1.	客户信息列表区域：
 *包括：客户编码、客户名称、联系人、手机、
 *电话、地址(规范化地址和详细地址)；
 *2.2.	功能按钮区域：
 *包括：确定、取消；
 *3.	选择热门城市界面
 * 界面信息包含人热门城市
 *4.	选择省份界面
 *界面信息包含省份
 *5.	选择城市界面
 *  界面信息保护城市
 *6.	选择区县界面
 *       界面信息保护区县
 *1.6	操作步骤
 *1.6.1	录入
 *序号	基本步骤	相关数据	补充步骤
 *1	录入手机号码
 *	1.	系统自动查询CRM系统中对应手机号码绑定的客户信息，
 *	如果有弹窗，弹窗操作见扩展1a，如果没有，
 *	弹出FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作见扩展1b；
 *2.	规则-请参见系统规则SR1；
 *
 *2	录入电话号码
 *	1.	如果手机号码没有填写，
 *	系统自动查询CRM系统中对应电话号码绑定的客户信息，
 *	如果有弹窗，弹窗操作见扩展2a，如果没
 *，弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作见扩展2b；
 *2.	规则-请参见系统规则SR1；
 *
 *3	录入客户名称和客户编码		1.	规则-请参见系统规则SR2、SR3；
 *4	录入发货联系人（发货部门）		1.	规则-请参见系统规则SR4、SR5、SR8；
 *5	录入发货人地址		1.	提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段；
 *2.	规则-请参见系统规则SR6；
 *1.6.2	扩展
 *序号	扩展事件	相关数据	备注
 *1a	当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见系统规则SR5 、SR6、SR7；
 *
 *1b	当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时，
 *如果该客户在这3个月有发过货，则弹出历史发货记录，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；
 *	客户信息	3.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *4.	规则-请参见系统规则SR5 、SR6、SR7；
 *
 *2a	当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则；
 *2.	规则-请参见系统规则SR5、SR6、SR7；
 *
 *
 *2b	当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时，
 *如果该客户在这3个月有发过货，则弹出历史发货记录，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则；
 *2.	规则-请参见系统规则SR5、SR6、SR7；
 *
 *1.7	业务规则
 *序号	描述
 *SR1	1.	发货客户手机号码及固定电话至少提供一个，手机号码只能为数字并且为11位；
 *2.	固定电话号码只能为数字，且可添加多个；添加多个时，
 *必须用“，”或“、”或“/”分开；固定电话号码字段也可以录入手机号；
 *3.	手机、电话为精确查询全公司客户信息
 *4.	手机、电话带出的客户信息会覆盖原来已带出的客户信息。
 *若未带出客户信息则当客户ID不为空时清空除手机外的其它已带出的客户信息（即），否则不清空
 *SR2	1.	若发货客户为公司会员客户，则录入发货客户信息后系统界面显示该发货客户联系人编码；
 *2.	客户名称精确查询全公司客户信息。当客户名称为带出的客户时（以隐藏的客户ID是否为空做为判断条件），
 *清空客户时则清空手机、电话、联系人、地址、行政区域、客户编码，否则只清空客户名称、客户编码、客户ID（隐藏）；
 *SR3	1.	若发货客户为会员客户，则录入发货客户信息后系统给予提示；
 *2.	提未信息为在运单开单界面下方加色放大显示；
 *3.	通过选择录入的客户名称和客户编码不可修改，但可删除录入；
 *SR4	1.	当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“发货联系人”字段更改为“发货部门”；
 *2.	联系人不用带出客户信息
 *SR5	1.	若为公司内部带货，输入发货部门关键字支持模糊搜索 
 *SR6	1.	客户详细地址必填至乡/镇，且乡镇下一级内容不能为空；
 *2.	当鼠标点击规范化地址文本框时，显示如下图片，
 *包含热门城市、省份、城市、县区，选择热门城市，
 *会直接跳到区县，现在省份后自动跳到城市，
 *选择城市后自动跳到区县（必须选择完上一级行政区域后，才能选择下一行政区域），
 *选择完区县后，会把规范化地址显示在规范化文本框内，例如：江苏省-苏州市-相城区
 *3.	地址可以进行拼音和首写字母进行匹配，例如输入“GZ”会在规范化地址文本框下面显示 ；
 *匹配时可带出城市、区、县等符合的信息，该设计来自官网，具体可以参考官网
 *4.	当增值服务中有返单业务时，则发货人地址为必填项；其他情况非必填。
 *SR7	1.	使用电话号码和手机进行匹配，弹出CRM发货客户选择框进行选择，如果匹配不到，
 *再使用运单发货历史客户进行匹配，弹窗选择，选择后填充客户编码、客户名称、联系人、地址；
 *2.	但是对于电话号码匹配，只有当发货人手机、客户名称为空时，才会用电话号码检索并弹窗显示；
 *3.	使用手机号码、电话号码、客户名称弹出选择框选择记录后覆盖原先记录
 *4.	修改联系人时，需要清空客户名称 
 *5.	使用手机号码弹出选择框选择记录后覆盖原先记录，查询不到时，清空客户名称
 *6.	导入发货客户信息后，联系人名称不可修改，为灰色；当营业员进行清空发货客户的客户名称操作时，
 *联系人名称可修改，为可编辑状态；
 *7.	点击客户名称查询控件，弹出查询信息:窗口SUC-424-查找会员
 *8.	如果查询出来的记录只有一条，也需要进行选择
 *9.	发货客户名称精确查询且查询全公司
 *SR8	1. 发货人省市区默认为始发营业部的省市区
 *
 *
 *录入收货客户SUC业务规则
 *营业员点击运单开单，进入运单开单界面。
 *本界面分为两个界面：录入收货客户信息、选择收货客户。
 *1.	录入收货客户信息：
 *界面为信息录入界面：包括：手机、电话、发货收货联系人（发货收货部门）、发货收货人地址；
 *1.1	手机：发货收货人手机号码；
 *1.2	电话：发货收货人电话号码；
 *1.3	收货联系人（收货部门）：收货客户的客户姓名，当“运单开单”中的“开单提货方式”为“内部带货自提”时，“收货联系人”字段更改为“收货部门”；
 *1.4	收货人地址：收货客户的详细联系地址，支持国家行政区域自动过滤；
 *1.5	客户名称
 *1.6	客户编码
 *2.	选择收货客户界面：
 *界面为选择客户信息界面：包括两部分：客户信息列表区域、功能按钮区域；
 *2.1.	客户信息列表区域：
 *包括：联系人、手机、电话、地址（规范化地址和详细地址）；
 *2.2.	功能按钮区域：
 *包括：确定、取消；
 *3.	    选择热门城市界面
 *   界面信息包含人热门城市
 *4.	选择省份界面
 *  界面信息包含省份
 *5.	选择城市界面
 *           界面信息保护城市
 *6.	选择区县界面
 *          界面信息保护区县
 *
 *1.6	操作步骤
 *1.6.1	录入
 *序号	基本步骤	相关数据	补充步骤
 *1	录入手机号码		1.	系统自动查询CRM系统中对应手机号码绑定的客户信息，
 *如果有弹窗，弹窗操作见扩展1a，和如果没有，
 *弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作见扩展1b；
 *2.	规则-请参见系统规则SR1、SR5、SR6；
 *2	录入电话号码		1.	如果手机号码没有填写，
 *系统自动查询CRM系统中对应电话号码绑定的客户信息，
 *如果有弹窗，弹窗操作见扩展2a，如果没有，
 *弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作见扩展2b；
 *1.	1、系统自动查询CRM系统中对应电话号码绑定的客户信息
 *和FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，
 *弹窗操作参见扩展2a；
 *2.	规则-请参见系统规则SR1、SR5 、SR6；
 *3	录入收货联系人（收货部门）		
 *1.	规则-请参见系统规则SR2、SR3；
 *4	录入收货人地址		
 *1.	提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段；
 *2.	地址在系统后台通过GIS系统进行匹配，
 *如果是禁行区域，地址颜色为红色，如果是进仓区域，地址颜色为黄色
 *3.	规则-请参见系统规则SR4；
 *1.6.2	扩展
 *序号	扩展事件	相关数据	备注
 *1a	当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *当光标焦点移至录入手机号码时，系统自动调用CRM系统对应发货客户的历史发货记录，
 *并自动弹窗显示所有该发货客户的历史发货记录；营业员选择一条记录，并确定，
 *选择的客户信息自动带信收货客户信息中；	客户信息
 *	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见系统规则SR5 、SR6；
 *1.	规则-请参见系统规则SR5；
 *1b	当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时，
 *则如果该客户在这3个月有发过货，则弹出历史收货记录自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；	
 *客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见系统规则SR5 、SR6；
 *2a2a	当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *	客户信息客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见系统规则SR5 、SR6；
 *1.	规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则；
 *2.	规则-请参见系统规则SR6；
 *
 *2b	当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时，
 *如果该客户在这3个月有发过货，则弹出历史收货记录，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见系统规则SR5 、SR6；
 *	
 *1.7	业务规则
 *序号	描述
 *SR1	1.	收货客户手机号码及固定电话至少提供一个，手机号码只能为数字并且为11位，
 *固定电话号码只能为数字，且可添加多个；
 *SR2	1.	当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“收货联系人”字段更改为“收货部门”；
 *SR3	1.	若为公司内部带货，则收货客户信息中的收货部门名称必须与OA系统中组织架构名称保持一致；
 *SR4	1.	客户详细地址必填至乡/镇，且乡镇下一级内容不能为空；
 *
 *2.	当鼠标点击规范化地址文本框时，显示如下图片，
 *包含热门城市、省份、城市、县区，选择热门城市，会直接跳到区县，
 *现在省份后自动跳到城市，
 *选择城市后自动跳到区县（必须选择完上一级行政区域后，才能选择下一行政区域），
 *选择完区县后，会把规范化地址显示在规范化文本框内，例如：江苏省-苏州市-相城区
 *3.	地址可以进行拼音和首写字母进行匹配，例如输入“GZ”会在规范化地址文本框下面显示 ；
 *匹配时可带出城市、区、县等符合的信息，该设计来自官网，具体可以参考官网
 *4.	当提货方式含“自提”时，收货人地址为非必填项；
 *SR5	1.	使用电话号码和手机进行匹配，如果是唯一匹配一条CRM客户信息时，
 *填充客户编码、客户名称、联系人、地址，如果有多条需弹出选择框进行选择，
 * 如果查询不到CRM客户信息时，使用FOSS三个月运单历史记录中的收货信息查询，
  *唯一匹配一条进行填充收货客户信息，多条进行弹窗选择，如果都查询不到，不做其他操作
 *2.	但是对于电话号码匹配，只有当发货人手机、客户名称为空时，才会用电话号码检索并弹窗显示
 *3.	使用手机号码、电话号码、客户名称弹出选择框选择记录后覆盖原先记录
 *4.	修改联系人时，需要清空客户名称，当清空客户名称时，会同时删除客户编码
 *5.	使用手机号码、电话号码弹出选择框选择记录后覆盖原先记录
 *6.	点击客户名称查询控件，弹出查询信息:窗口SUC-424-查找会员
 *7.	身份证号、客户编码、客户名称、联系人编码可以查询到全公司的客户
 *1.	只有当收货人手机为空，且发货客户信息已录入时，才会检索并弹窗显示；
 *2.	若未查询到历史记录，则无法提示；
 *3.	通过选择录入的收货人信息均可修改；
 *4.	使用号码进行匹配，如果是唯一匹配一条CRM客户信息时，填充客户编码和客户名称，如果有多条不做操作
 *SR6	1.	通过选择录入收货发货信息，同时带出对应的目的站和提货网点信息；
 *2.	带出目的站仍然使用GIS进行查询，并以GIS返回为准；
 *3.	若GIS未查询得出，则以历史开单的目的站和提货网点信息为准；
 *
 *
 *查找会员SUC业务规则
 *营业员点击运单开单界面中的查询客户，进入查询客户信息界面。
 *本界面为查询客户信息。
 *界面主要分为三个部分：查询条件区域、查询结果区域、功能按钮。
 *1.	查询条件区域：
 *1.1	会员卡号：支持会员卡号的模糊搜索，并可查询部门全部会员信息；
 *1.2	发货联系人
 *1.3	电话
 *1.4	客户编码
 *1.5	客户名称
 *1.6	手机
 *1.7	发货人地址
 *1.8	复选框"查询全公司"
 *2.	查询结果区域：
 *2.1	客户编码
 *2.2	客户名称
 *2.3	联系人编码
 *2.4	月结审核
 *2.5	联系人
 *2.6	手机
 *2.7	电话
 *2.8	身份证
 *2.9	信用额度
 *2.10	地址
 *2.11	越发越送审核编号
 *2.12	生效时间
 *2.13	失效时间
 *3.	功能按钮：
 *3.1	重置：
 *3.2	查询；
 *3.3	查询部门会员
 *1.6	操作步骤
 *1.6.1	录入
 *序号	基本步骤	相关数据	补充步骤
 *1	录入查询条件，查询符合条件的客户信息	查询条件信息	
 *1.	系统查询CRM系统中本部门符合条件的客户信息；
 *2.	规则-请参见系统规则SR1；
 *2	查询本部门所有的会员客户信息		
 *1.	规则-请参见系统规则SR2；
 *3	导入选择的客户信息至发/收货客户信息中：双击选中的客户信息，
 *对应客户信息进入发/收货客户信息中		
 *1.	规则-请参见系统规则SR3；
 *1.6.2	扩展
 *序号	扩展事件	相关数据	备注
 *1a	步骤1中，若未查询到符合条件的客户信息，系统给予提示		
 *1.	提示信息为“未查询到符合条件的客户信息！”；
 *2b	步骤2号，若本部门无会员，系统给予提示		
 *1.	提示信息为“部门无会员信息！”；
 *1.7	业务规则
 *序号	描述
 *SR1	1.	支持单一和组合查询条件查询；
 *2.	只有勾选复选框时，按照身份证号、客户编码、
 *客户名称、联系人编码精确查全公司客户信息且查询条件中包含有身份证号、
 *客户编码、客户名称、联系人编码其中任意一项时，忽略其它查询条件，
 *否则为模糊查询本部门客户信息；
 *3.	当查询到的记录为某客户编码中的其中一条信息时，
 *显示该编码对应的所有的客户信息；
 *4.	当客户为越发越送客户时，则显示该客户对应的越发越送审核编号、
 *生效时间、失效时间；当越发越送审核编号有多个时，则只显示最后一次审核信息；
 *SR2	1.	当条件都为空时，点击查询按钮，查询出本部门会员；
 *SR3	1.	通过查询条件带出的客户信息被填充到相关控件中时，
 *发货客户联系人为不可修改状态，
 *若要修改则需要清空全部带出的客户信息；
 *
 *
 *确认公布总价SUC业务规则
 *1	打开“运单开单”界面。		
 *2	录入发货人信息		参考SUC-492
 *3	录入收货客户信息		参考SUC-493
 *4	录入货物信息		参考SUC-494
 *5	录入运输信息		参考SUC-496
 *进行前面5操作之后，若是空运，
 *系统读取空运公布价价格方案（基础资料参考SUC-581）
 *计算出公布总价，显示在运单开单界面。
 *若是汽运，系统读取汽运公布价价格方案（基础资料参考SUC-581）
 *计算出公布总价，
 *显示在运单开单界面。
 *参考规则SR1，SR2，SR3
 *扩展事件写非典型或异常操作过程
 *序号	扩展事件	相关数据	备注
 *5a			
 *5b			
 *1.7	业务规则
 *序号	描述
 *SR1	1）汽运：上门发货汽运运费最低X元一票；
 *（同城、卡航；可配置）；
 *上门接货汽运运费最低X元一票；（同城、卡航；可配置）；
 *2）空运：空运运费最低X元一票；（可配置）；
 *SR2	1）汽运：当货物为“接货”时，
 *系统自动匹配生成公布价“接货价格方案”；
 *当货物为“非接货”时，
 *系统自动匹配生成公布价“非接货的价格方案”；
 *2）空运：系统自动匹配公布价 “空运价格方案”；
 *空运价格只有上门发货一套价格方案，
 *如有接货费在其他费用里添加一项接货费。
 *SR3	1）计费方式分为重量计费、体积计费；
 *重量、体积计费的运费=每公斤单价与货物实际重量的乘积 或 每方单价与货物实际体积的乘积，
 *对于一票货物，系统按重量和体积分别计算并取大优先的原则计费给出公布价总运费,
 *计费方式即为取大的一方；
 *
 *运单提交SUC业务规则
 *客户上门发货确认承运信息后，营业员告知客户运输费用后，
 *为客户开具运单，打印标签并粘贴至货物的过程。
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件	1.	运单已填写完整	
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc
 *DP-FOSS-接送货系统用例-客户上门-根据订单确认承运信息-导入订单-V0.1.doc
 *DP-FOSS-接送货系统用例-客户上门-确认承运信息-查询目的站-V0.1.doc
 *DP-FOSS-接送货系统用例-客户上门-承运计费报价-确认公布总价-V0.1.doc
 *后置条件	1.	传送运单号、金额、帐号等结算数据到财务子系统
 *2.	传送货物名称、件数、重量等货物信息到中转子系统，安排运输计划
 *3.	传送运单号等信息到官网，客户查询运单状态
 *4.	给收货人发送出发短信，给上门接货客户发送短信
 *5.	订单信息反馈给给CRM系统，订单处理结果为：已开单。
 *6、 当运输性质为精准空运时，
 *提交成功后会自动生成订舱信息流到最终配载部门（即总调）的舱位信息中	
 *1.4	操作用户角色
 *操作用户	描述
 *营业员	1.  确认客户承运信息，开单收货，
 *收取现付金额，且打印标签及运单出发联让客户签字确认。
 *1.5	界面要求
 *1.5.1	表现方式
 *Web方式
 *1.5.2	界面原型
 *1.5.2.1	集中开单界面
 *1.5.2.2	营业部开单界面
 *1.5.2.3	运单确认提交界面
 *1.5.3	界面描述
 *1.5.3.1	营业部、集中开单界面
 *界面共包括7个部分：1、发货客户信息；
 *2、收货客户信息；3、货物信息；
 *4、运输信息；5、增值服务信息；
 *6、计费付款；7、功能按钮。
 *具体描述参考DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc
 *1.5.3.2	运单确认提交界面
 *界面共包括3个部分：1、运单基本信息；
 *2、功能复选框；3、功能按钮
 *1、运单基本信息：单号、到付总运费、预付总运费、
 *代收货款、保险价值、收货人名称、付款方式、提货网点、
 *提货方式、收货人地址、收货人电话、货物名称、
 *重量/体积/件数、包装、重量/体积/件数（代打木架）。
 *2、功能选项：打印运单下拉框有各种版本（可以配置，且可以配置默认模板）
 *（选择之后，点击确定系统自动打印运单，只能选其一，参考规则SR8）、
 *打印标签（选择之后，点击确定系统自动打印标签）、
 *提交后新增（选择之后，点击确定系统打开一个新的开单界面）。
 *3、功能按钮：确定、取消 。
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	营业员“填写”完整的运单信息。		
 *2	点击暂存	1、客户上门提供的货物承运信息。
 *2、来此crm系统订单的数据。
 *3、上门接货客户提供的承运信息。
 *	1、用户暂存的运单数据时允许修改
 *23	点击“提交”按钮提交运单。	
 *1、客户上门提供的货物承运信息。
 *2、来此crm系统订单的数据。
 *3、上门接货客户提供的承运信息。
 *	1、点击运单“提交”按钮，弹出“运单确认提交页面”，
 *页面默认选择上“打印运单（全打）”、“打印标签”、
 *“确定后新增运单”复选框。
 *2、提交后，同步数据到中转子系统，、CRM系统、官网系统，
 *财务子系统。(将单独出来写接口用例，此处将参考这些接口系统用例)
 *3、系统自动根据出发部门、运输性质、到达部门生成默认唯一走货路径(基础资料)； 
 *参考综合系统基础资料用例。
 *4、当运输性质为精准空运时，提交成功后会自动生成订舱信息
 *（包括：预计出发时间、航班时间（早中晚）、重量）
 *流到最终配载部门（即总调）的舱位信息中。
 *5、保存使用的价格版本号
 *56、参考规则SR1、SR12
 *34	点击运单确认提交页面的“确定”按钮。		
 *1、点击确定后，打开一个新的运单开单界面。
 *2、系统自动打印运单,选择系统默认的打印模板，
 *参考DP-FOSS-接送货系统用例-客户上门-确认承运信息-打印运单-V0.1.doc
 *3、系统自动打印标签，参考DP-FOSS-接送货系统用例-客户上门-确认承运信息-打印标签V0.1.doc
 *4、调用中转入库接口 参考规则SR13
 *45	若有签收单返单时，需要打印签收单标签，
 *打印内容包括：单号、始发部门、到达部门、目的站。		
 *参考SUC-504打印签收单标签(整车)
 *扩展事件写非典型或异常操作过程
 *序号	扩展事件	相关数据	备注
 *2a	营业员在弹出代打木架对话框“录入第X件”需要打木架。		
 *若货物需要代打木架，系统自动弹出代打木架对话框。代打木架精确到第几件。
 *参考规则SR3
 *2b2a	如运单必填信息未填写完整或填写内容不符合要求
 *（参考数据元素输入限制、长度、是否必填、运单号重复等），
 *提交时给予提示。		提示为：“×××未填写整或输入内容不符合要求，请重新输入！”，
 *且将此文本输入框标记为红色，光标置于此文本框中。
 *重新填写正确完整后，跳转步骤1。
 *参考规则SR7
 *2c2b	若为月结或临时欠款， 如果客户既有应收账款金额加上本次应收金额超过客户最大信用额度，
 *不能提交开单。		弹出提示：客户既有应收账款金额加上本次应收金额超过客户最大信用额度，
 *不能提交开单。参考SR9
 * *2d2c	若为月结或临时欠款，如果客户已有应收账款超过最大账期，不能提交开单。		
 * 弹出提示：客户已有应收账款超过最大账期，不能提交开单。参考SR9
 *2e2d	提交时，当重量体积比不在设置的区间（重量体积比基础资料）中，
 *弹出提示“请确认录入的重量体积是否准确！”；
 *（该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面；
 *点击取消，点返回运单录入界面；当X在区间中，无提示；
 *直接进入确认运单信息界面；		参考规则SR10
 *3a	步骤3中，可点击“取消”按钮，取消提交运单。		
 *取消提交运单，返回到系统运单开单界面，界面信息可编辑，
 *修改信息后，可再次进行提交，跳到步骤2。
 *3b	步骤3中，也可取消选择默认选择的复选框。		
 *1、如取消选择“打印运单”，确定之后，系统不进行自动打印运单，
 *需点击开单界面上的“打印运单”按钮，打印运单出发联。
 *2、如取消选择“确定后新增运单”，确定之后，
 *系统返回到填写完整的运单开单界面，但是运单界面成灰色不可编辑，
 *需点击开单界面上的“新增”按钮，新增运单。
 *3、如取消选择“打印标签”，确定之后，
 *系统不进行自动打印标签，需点击开单界面上的“打印标签”按钮，打印运单标签。
 *1.7	业务规则
 *序号	描述
 *SR1	1、若为上门接货，开单提交生成后，
 *系统短信通知发货人及收货人。
 *短信模板可在系统中进行设置。
 *给发货人或收货人发送短信时，
 *若无手机号码则不发送。
 *发送短信内容中包含“货物的件数”。
 *注：“货物的件数”为货物包装之前的件数。
 *系统自动给发货人、收货人发送货物出发短信（短信模板内容可配置）；
 *否则只需要给收货人发短信；若收发货人无手机号码则不发送。
 *给收货人的短信内容：您好！这里是德邦物流，
 *（发货人姓名***）从（出发城市***）给您发来货物，
 *单号为（****）的（***货物的件数）件货，即日出发。
 *目的地（*****客户的收获地址）。附：德邦物流营业部的电话、地址、营业部名称。
 *给发货人的短信内容：您好！这里是德邦物流，
 *您从（出发城市***）给（收货人姓名***）托运的货物，
 *单号为（****）的（***货物的件数）件货，即日出发。
 *目的地（*****客户的收获地址）。附：德邦物流营业部的电话、地址、营业部名称。
 *SR2	运单现付金额不为0，则在出发部门生成现金收款单；
 *若到付金额不为0，则在到达部门生成应收单若付款方式为“现付”，
 *“银行卡”，则在出发部门生成现金收款单；
 *若付款方式为“到付”在到达部门生成应收单。
 *若付款方式为“临时欠款”，“网上支付”，
 *“月结”在出发部门生成应收单；若运单包含“代收货款”，
 *则在出发部门生成应付单，到达部门生成应收单；
 *若运单包含“装卸费”，则在出发部门生成应付单。
 *SR3	开单代打木架外场默认为第一外场，
 *如果第一外场不支持打木架，则营业员自己判定选择的代打木架外场。
 *开单代打木架外场默认为开单走货路径中第一个可代打木架的外场，不可修改。 
 *1）若货物包装中含有“木”字样，且走货路径上有代打木架外场，
 *系统提示：是否需要代打木架？； 
 *2）选择代打木架后，若默认的代打木架外场非走货路径上第一外场时，
 *系统给予提醒，便于营业员与客户衡量是否继续代打木架； 
 *3）若货物包装中含有“木”字样，但走货路径上没有可代打木架的外场，
 *则不能开代打木架，系统提示：走货路径上没有可代打木架的外场，不能代打木架。
 *SR4	当运输性质为 “精准卡航”及“精准城运”时，
 *系统自动计算预计出发时间与预计提货/派送时间。
 *①预计出发时间：
 *由预计出发日期和准点出发时点（取自基础资料）组成，
 *形如【2012-04-09  12:00:00】。
 *若开单当前时点在准点出发时点前，
 *则预计出发日期=开单日期；否则，预计出发日期=开单日期+1；
 *② 预计提货时间（开单提货方式为自提）： 
 *由预计提货日期和到达营业部承诺时点（取自基础资料）组成，
 *形如【2012-04-09  12:00:00】。若为当天出发，
 *则预计提货日期=预计出发日期+到达营业部承诺天数；
 *否则预计提货时间=预计出发日期+到达营业部承诺天数-1。
 *③ 预计派送时间（开单提货方式为送货）：
 *    由预计派送日期和派送承诺时点（取自基础资料）组成，
 *    形如【2012-04-09  12:00:00】。若为当天出发，
 *    预计派送时间=预计出发日期+到达营业部承诺天数+派送承诺需加天数；
 *    否则预计派送时间=预计出发日期+派送承诺需加天数-1。
 *SR5	如果开单信息来自集中或非集中上门接货：
 *1、司机有PDA  PDA开单后：则点击“PDA补录”走PDA补录流程引用系统用例SUC-491-补录运单。
 *2、司机无PDA  则新增运单，进入开单界面走提交运单流程。
 *SR6	“运单确认提交界面”上的复选框可以单选，可以多选。
 *SR7	提交时系统校验规则参考如下用例规则：
 *SUC-492录入发货客户信息
 *SUC-493录入收货客户信息
 *SUC-494录入货物信息
 *SUC-495录入增值服务信息
 *SUC-496录入运输信息
 *SUC-311导入订单
 *SUC-397 确认公布总价
 *SUC-126 查询目的站
 *SR8	运单确认提交页面，打印运运单下拉框只能选其一，
 *默认为运单模板配置基础资料中配置默认的模板，
 *如果不需要打印，不勾选即可。
 *SR9
 *	若为月结或临时欠款，则系统需要对客户应收账款日期及信用额度进行判断：
 *1）欠款天数
 *以客户为基本单位，开单/派送时付款方式为“临时欠款”，
 *最长一笔欠款时间超过30天，该客户将不能继续开单为“临时欠款”，
 *假如客户的临时欠款未还款金额超过客户自己的信用额度，
 *也不能继续开单；开单付款方式为“月结”，最长一笔欠款时间超过70天，
 *该客户将不能继续开单为“月结”；（始发应收账款从开单之日起计算，
 *到付应收账款从第一次派送出库之日起计算，以更改方式更改为“临时欠款”或“月结”的，
 *从开单之日开始计算）
 *2）欠款额度
 *临时欠款：以部门为单位，根据收入等级（前三个月最高收入金额）
 *设置该部门每月临时欠款最高额度（余额），详见下表；
 *当部门临时欠款未还款金额大于该金额时，
 *将无法继续开单未“临时欠款”；
 *收入区间	项目
 *10万以下	3万
 *10-15万	5万
 *15-20万	7万
 *20-30万	10万
 *30-50万	15万
 *50-100万	20万
 *100万以上	40万
 *月结：以客户为单位，连续2个月发货金额在3000元以上可申请月结，
 *月结额度不得高于近期最高走货金额的2倍，
 *若客户连续两月发货量低于2000元，取消月结权限。
 *SR10	1. 系统设置货物重量体积比区间值（该值由基础资料配置），
 *在运单提交时，系统自动对重量体积比进行校验：即重量体积比X=重量/体积；
 * 当X不在设置的区间中，弹出提示“请确认录入的重量体积是否准确！”；
 * （该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面；
 * 点击取消，点返回运单录入界面；当X在区间中，无提示；
 * 直接进入确认运单信息界面；
 *SR11	1）用户可以随时录入运单信息随时暂存运单，
 *但必须录入打印标签所需的关键字段才可暂存运单。
 *目前暂存运单打印标签所需运单信息包含收货部门、
 *运单号、目的站、提货网点、运输性质、提货方式、包装、件数。
 *（注：当走货路由经过特定的城市时还需要录入货物类型） 
 *2）系统对录入的运单信息合法性进行校验； 
 *3）运单暂存后不生成正式运单信息、库存信息及财务单据； 
 *4）用户可对已暂存的运单进行修改、暂存、提交（生成正式运单）； 
 *5）若暂存运单，系统锁定运单号及订单信息； 
 *6）暂存的运单不可打印运单出发联；
 *SR12	若PDA开单已打印标签，
 *在提交运单后弹出的确认界面中打印标签默认不勾选，
 *若需打印标签则手动勾选"打印标签"复选框。
 *SR13	1）、如果开单组织是营业部门，
 *那么开单提交时，
 *检验是否是驻地部门，如果是驻地部门，
 *入库对应驻地部门外场，
 *如果是营业部，入库对应营业部
 *2）、如果开单组织是开单组，那么开单提交时，
 *校验是否已经打印标签，如果没有，入库配载部门；
 *
 *
 *录入运输信息（整车）SUC业务规则
 *营业员点击整车运单开单，进入整车运单开单界面。
 *本界面为录入整车运输信息。
 *界面信息包括：收货部门、单号、目的站、提货网点、
 *对外备注、对内备注、储运注意事项、配载部门、
 *最终配载部门、到达类型
 *1.	收货部门：揽货部门；
 *2.	单号：运单单号；
 *3.	提货网点：收货客户可以领取货物的部门；
 *4.	对外备注：客户可以看到的备注信息，包括：空、
 *保丢不保损、“不承保发霉、变质、虫蛀虫咬之损失”、
 *“ 不承保刮花、变形、撞凹之损失”、不可重压、易潮、
 *不可倒置、客户指定提货网点；
 *5.	对内备注：仅限公司内部人员看到的备注信息；
 *6.	到达类型：到达客户处还是到达营业部
 *7.	储运注意事项：对外备注和对内备注信息的叠加；
 *8.	配载部门:开单收货部门出发货配载专线；
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	录入收货部门		1.	规则-请参见系统规则SR1；
 *2	录入单号		系统校验单号的合法性
 *1.	规则-请参见系统规则SR2；
 *3	录入提货网点		1.	 规则-请参见系统规则SR3；
 *4	录入对外备注		1.	 规则-请参见系统规则SR4；
 *5	录入对内备注		
 *6	到达类型		1.	规则-请参见系统规则SR5；
 *6	生成储运注意事项		1.	规则-请参见系统规则SR6；
 *7	生成配载部门		1.	规则-请参见系统规则SR7；
 *8	生成最终配载部门		1.  规则-请参见系统规则SR8；
 *1.7	业务规则
 *序号	描述
 *SR1	1.	收货部门默认为操作者所在的部门，不可修改；
 *SR2	1.	单号最大长度为8位，如果超过提示“单号大于8位”
 *2.	单号不能少于8位，“如果少于8位“单号长度少于7位”
 *3.	单号与最近开单单号前六位如果不等，那么提示“前后两票单号相差过大，
 *请检查所输单号是否为本部门所属单号！”
 *SR3	1.	提货网点可由收货客户地址的城市生成，也可手工选择；
 *2.	系统自动过滤只显示符合录入文本条件的网点信息；
 *SR4	参考SUC-496 录入运输信息 对对外备注的处理
 *SR5	1.	如果在 “是否经过营业部”打勾，付款方式可以选择到付项，
 *代收货款框可以修改；
 *2.	如果在“是否经过营业部”不打勾，付款方式中的到付项自动移除，
 *代收货款清0且不可修改；
 *SR6	1.	储运注意事项=对外备注&对内备注，各字段以“；”分开；
 *2.	对外备注永远在储运注意事项的最前面；
 *SR7	1.	通过出发部门和提货网点系统自动匹配始发配载部门基础资料；
 *
 *
 *录入收货客户信息（整车）SUC业务规则
 *营业员点击运单开单，进入运单开单界面。
 *本界面分为两个界面：录入收货客户信息、选择收货客户。
 *1.	录入收货客户信息：
 *界面为信息录入界面：包括：手机、电话、发货收货联系人（发货收货部门）、
 *发货收货人地址；
 *1.1	手机：发货收货人手机号码；
 *1.2	电话：发货收货人电话号码；
 *1.3	收货联系人（收货部门）：收货客户的客户姓名，
 *当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“收货联系人”字段更改为“收货部门”；
 *1.4	收货人地址：收货客户的详细联系地址，
 *支持国家行政区域自动过滤；
 *1.5	客户名称
 *1.6	客户编码
 *2.	选择收货客户界面：
 *界面为选择客户信息界面：包括两部分：
 *客户信息列表区域、功能按钮区域；
 *2.1.	客户信息列表区域：
 *包括：联系人、手机、电话、地址（规范化地址和详细地址）；
 *2.2.	功能按钮区域：
 *包括：确定、取消；
 *3.	    选择热门城市界面
 * 界面信息包含人热门城市
 *4.	选择省份界面
 *   界面信息包含省份
 *5.	选择城市界面
 *           界面信息保护城市
 *6.	选择区县界面
 *      界面信息保护区县
 *1.6	操作步骤
 *1.6.1	录入
 *序号	基本步骤	相关数据	补充步骤
 *1	录入手机号码		
 *1.	系统自动查询CRM系统中对应手机号码绑定的客户信息，
 *如果有弹窗，弹窗操作见扩展1a，
 *如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作见扩展1b；
 *2.	规则-请参见SUC-493-录入收货客户信息SR1、SR5、SR6；
 *2	录入电话号码		1.	如果手机号码没有填写，
 *系统自动查询CRM系统中对应电话号码绑定的客户信息，
 *如果有弹窗，弹窗操作见扩展2a，
 *如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作见扩展2b；
 *2.	1、规则-请参见SUC-493-录入收货客户信息SR1、SR5 、SR6；
 *3	录入收货联系人		1.	
 *4	录入收货人地址		
 *1.	提供下拉框选择输入，
 *系统自动过滤输入的行政区下一级行政级的字段；
 *2.	地址在系统后台通过GIS系统进行匹配，
 *如果是禁行区域，地址颜色为红色，如果是进仓区域，地址颜色为黄色
 *3.	规则-请参见系统规则SR4；
 *1.6.2	扩展
 *序号	扩展事件	相关数据	备注
 *1a	当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见SUC-493-录入收货客户信息SR5 、SR6；
 *1b	当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时，
 *如果该客户在这3个月有发过货，则弹出历史收货记录，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见SUC-493-录入收货客户信息SR5 、SR6；
 *
 *2a	当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见SUC-493-录入收货客户信息SR5 、SR6；
 *3.	
 *2b	当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时，
 *如果该客户在这3个月有发过货，则弹出历史收货记录，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见SUC-493-录入收货客户信息SR5 、SR6；
 *4.	
 *1.6.1	录入
 *序号	基本步骤	相关数据	补充步骤
 *1	录入手机号码		
 *1.	系统自动查询CRM系统中对应手机号码绑定的客户信息和
 *FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，
 *弹窗操作见扩展1b；
 *2.	规则-请参见系统规则SR1；
 *2	录入电话号码		
 *1.	系统自动查询CRM系统中对应电话号码绑定的客户信息和FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作参见扩展2a；
 *2.	规则-请参见系统规则SR1；
 *3	录入客户名称		
 *1.	规则-请参见系统规则SR2
 *4	录入收货联系人		
 *5	录入收货人地址		
 *1.	提供下拉框选择输入，
 *系统自动过滤输入的行政区下一级行政级的字段；
 *2.	地址在系统后台通过GIS系统进行匹配，
 *如果是禁行区域，地址颜色为红色，
 *如果是进仓区域，地址颜色为黄色;
 *3.	规则-请参见系统规则SR3；
 *1.6.2	扩展
 *序号	扩展事件	相关数据	备注
 *1a	当光标焦点移至录入手机号码时，
 *系统自动调用CRM系统对应发货客户的历史发货记录，
 *并自动弹窗显示所有该发货客户的历史发货记录；
 *营业员选择一条记录，并确定，选择的客户信息自动带信收货客户信息中；
 *	客户信息	
 *1.	规则-请参见系统规则SR4；
 *1b	当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *营业员选择录入一条记录，并确定；
 *选择的客户信息自动带入收货客户信息中；	
 *客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2a	当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则；
 *1.7	业务规则
 *序号	描述
 *SR1	1.	收货客户手机号码及固定电话至少提供一个，
 *手机号码只能为数字并且为11位，固定电话号码只能为数字，
 *且可添加多个；
 *SR2	1.	若收货客户为公司会员客户，
 *则录入收货客户信息后系统界面显示该收货客户联系人编码；
 *SR3	1.	客户详细地址必填至乡/镇，
 *且乡镇下一级内容不能为空；
 *SR4	1.	只有当收货人手机为空，
 *且发货客户信息已录入时，才会检索并弹窗显示；
 *2.	若未查询到历史记录，则无法提示；
 *3.	通过选择录入的收货人信息均可修改；
 *1.	
 *
 *录入货物信息（整车）SUC业务规则
 *
 *营业员点击运单开单，进入运单开单界面。
 *本界面为录入货物信息。
 *界面主要分为一个部分：录入货物信息。
 *1.	录入货物信息：
 *录入信息包括：货物名称、总件数、总重量、货物尺寸、总体积、货物包装
 *1.1	货物名称：货物的名称；
 *1.2	总件数：收货时货物的总件数；
 *1.3	总重量：收货时货物的总重量；
 *1.4	总体积：收货时货物的总体积；
 *1.5	货物包装：货物的包装数；
 *1.6	操作步骤
 *1.6.1	录入货物信息
 *序号	基本步骤	相关数据	补充步骤
 *1	修改货物名称		1.	系统自动匹配违禁品，生成规则；
 *2.	规则-请参见系统规则SR1；
 *1.7	业务规则
 *序号	描述
 *SR1	1.	若货物为违禁品，则系统自动提示“货物为违禁品，
 *不可开单！”；
 *2.	违禁品、拒收品、具体类型可在系统中进行配置；
 *SR2	新增一条业务规则：在提交运单之前，
 *若相邻两次输入的单号（两次单号分别为A与B且单号输入合法）差别较大时，
 *系统给予用户友好提示避免录错单号，但不限制单号的输入。具体规则为： 
 *	1）若前后两次输入的单号位数相同，当|A-B|≥100时系统给予提示； 
 *	2）若前后两次输入的单号位数不同，系统给予提示； 
 *	3）提示信息为：前后两票单号相差过大，
 *请检查所输单号是否是本部门所属单号！
 *
 *
 *营业员点击运单开单，进入运单开单界面。
 *本用例分为两个界面：录入发货客户信息、
 *选择发货客户；
 *1.	录入发货客户信息：
 *界面为信息录入界面：包括：手机、电话、客户名称、
 *客户编码、发货联系人（发货部门）、发货人地址；
 *1.1	手机：发货人手机号码；
 *1.2	电话：发货人电话号码；
 *1.3	客户名称：发货客户公司或单位名称，
 *可支持搜索查询；
 *1.4	客户编码：我司给客户的客户号；
 *1.5	发货联系人（发货部门）：发货客户的客户姓名，
 *当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“发货联系人”字段更改为“发货部门”；
 *1.6	发货人地址：发货客户的详细联系地址，
 *支持国家行政区域自动过滤；
 *2.	选择发货客户界面：
 *界面为选择客户信息界面：包括两部分：
 *客户信息列表区域、功能按钮区域；
 *2.1.	客户信息列表区域：
 *包括：客户编码、客户名称、联系人、手机、
 *电话、地址(规范化地址和详细地址)；
 *2.2.	功能按钮区域：
 *包括：确定、取消；
 *3.	选择热门城市界面
 *   界面信息包含人热门城市
 *4.	选择省份界面
 *   界面信息包含省份
 *5.	选择城市界面
 *           界面信息保护城市
 *6.	选择区县界面
 *           界面信息保护区县
 *1.6	操作步骤
 *1.6.1	录入
 *序号	基本步骤	相关数据	补充步骤
 *1	录入手机号码		
 *1.	系统自动查询CRM系统中对应手机号码绑定的客户信息，
 *如果有弹窗，弹窗操作见扩展1a，
 *如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作见扩展1b；
 *2.	规则-请参见系统规则SUC-492-录入发货客户信息SR1；
 *2	录入电话号码		
 *2.	如果手机号码没有填写，系统自动查询CRM系统中对应电话号码绑定的客户信息，
 *如果有弹窗，弹窗操作见扩展2a，
 *如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
 *并弹窗，弹窗操作见扩展2b；
 *3.	规则-请参见系统规则SUC-492-录入发货客户信息SR1；
 *3	录入客户名称和客户编码		
 *1.	规则-请参见系统规则SUC-492-录入发货客户信息SR2、SR3；
 *4	录入发货联系人			
 *5	录入发货人地址		
 *1.	提供下拉框选择输入，
 *系统自动过滤输入的行政区下一级行政级的字段；
 *2.	规则-请参见系统规则SUC-492-录入发货客户信息SR6；
 *1.6.2	扩展
 *序号	扩展事件	相关数据	备注
 *1a	当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；
 *	客户信息	1.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *2.	规则-请参见系统规则SUC-492-录入发货客户信息SR5 、SR6、SR7；
 *1b	当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时，
 *如果该客户在这3个月有发过货，则弹出历史发货记录，营业员选择录入一条记录，
 *并确定；选择的客户信息自动带入发货客户信息中；	客户信息
 *	3.	规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *4.	规则-请参见系统规则SUC-492-录入发货客户信息SR5 、SR6、SR7；
 *2a	当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；	客户信息	
 *1.	规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则；
 *2.	规则-请参见系统规则SUC-492-录入发货客户信息SR5、SR6、SR7；
 *2b	当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时，
 *如果该客户在这3个月有发过货，则弹出历史发货记录，营业员选择录入一条记录，
 *并确定；选择的客户信息自动带入发货客户信息中；	客户信息	
 *1.	规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则；
 *2.	规则-请参见系统规则SUC-492-录入发货客户信息SR5、SR6、SR7；
 *1	录入手机号码		1.	系统自动查询CRM系统中对应手机号码绑定的客户信息和
 *FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作见扩展1a；
 *1.	规则-请参见系统规则SR1；
 *2	录入电话号码		1.	系统自动查询CRM系统中对应电话号码绑定的客户信息
 *和FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作参见扩展2a；
 *1.	规则-请参见系统规则SR1；
 *3	录入客户名称和客户编码		1.	规则-请参见系统规则SR2、SR3；
 *4	录入发货联系人		
 *5	录入发货人地址		1.	提供下拉框选择输入，
 *系统自动过滤输入的行政区下一级行政级的字段；
 *2.	规则-请参见系统规则SR4；
 *1.6.2	扩展
 *序号	扩展事件	相关数据	备注
 *1a	当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；	
 *客户信息	
 *1.	规则-请参见系统规则SR5；
 *2a	当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中；
 *	客户信息	1.	规则-请参见系统规则SR6；
 *1.7	业务规则
 *序号	描述
 *SR1	1.规则参考SUC-492-录入发货客户信息SR1 
 *SR2	1. 规则参考SUC-492-录入发货客户信息SR2
 *SR3	1.规则参考SUC-492-录入发货客户信息SR3 
 *SR4	1.	规则参考SUC-492-录入发货客户信息SR6
 *SR5	1. 规则参考SUC-492-录入发货客户信息SR7
 *SR6	1. 规则参考SUC-492-录入发货客户信息SR8
 *
 *
 *确认整车总价SUC业务规则
 *
 *营业员点击整车开单，进入整车开单界面。
 *本界面为录入整车收银界面。
 *1.	约车报价：营业员约整车后，请车员受理的价格；
 *2.	开单报价：营业员根据请车员的受理价格，给客户的实际运费价格；
 *3.	增值服务费用
 *4.	总运费：本次承运的客户应付金额；
 *5.	开单付款方式：客户的付款方式，包括：现金、银行卡、月结、临时欠款、到付；
 *6.	预付金额
 *7.	到付金额；
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	录入开单报价		1.	规则-请参见系统规则SR1；
 *2	查看约车报价、增值服务费用入总运费		1.	规则-请参见系统规则SR2、SR3；
 *3	选择开单付款方式		1.	规则-请参见系统规则SR4、SR5；
 *4	预付金额		1.	规则-请参见系统规则SR4、SR5；
 *5	到付金额		1.	规则-请参见系统规则SR4、SR5；
 *1.7	业务规则
 *序号	描述
 *SR1	1.	开单报价默认等于约车报价；
 *2.	修改的开单报价只能为约车报价的某个范围区间中，该范围区间可由公司统一配置；
 *SR2	1.	整车导入约车开单时，约车报价为请车员受理的约车价格，不可修改；
 *SR3	1.	总运费=开单报价+增值服务费；
 *2.	增值服务费=保价费+代收手续费+预付运费保密服务费+返单费+包装费；
 *SR4	1.	整车的付款方式包含现金、银行卡、月结、临时欠款、到付；
 *2.	支持发货客户的多种付款方式叠加，但月结和临时欠款不能同时存在；
 *2.	开单只能选择一种付款方式，不可选择两种或以上；
 *3.	月结客户可以开月结；
 *4.	若客户非月结，则自运过滤月结的付款方式；
 *5.	付款方式为到付或者临时欠款时，不能选择预付运费保密；
 *6.	若整车开单选择直接到达客户处，则不能办理到付；
 *7.	有信用额度且额度大于等于总运费的发货客户，才可以选择临时欠款；
 *SR5	1.	预付金额必须大于0才能选择预付运费保密；
 *2.	当付款方式为现付时，预付金额必须大于0；否则，
 *提示信息“付款方式非到付，预付金额不能小于等于0”；
 *3.	当付款方式为到付时，预付金额不能大于0；否则，
 *提示信息“付款方式为【到付】，预付金额不能大于0”；
 *4.	月结客户的信用金额直接限制该客户的当月发货金额，
 *当月发货金额超过信用额度，当月将无法再开单月结，
 *提示“该客户的剩余可用信息额度不足，不能开月结”；
 *5.	临时欠款时，需要客户中的信用额度超过运费，
 *否则，提示“该客户的剩余可用信息额度不足，不能开临时欠款”；
 *6.	当付款方式为临时欠款等收款放货时，
 *现付到付均为零，不可修改；
 *SR6	1.	约车报价、开单报价、增值服务费用、总运费、预付金额、
 *到付金额均为整数，按照四舍五入原则；
 *
 *
 *导入整车约车编号SUC业务规则
 *
 *营业员通过
 *界面标题： 约车信息
 *约车编号：受理后的约车编码
 *1.	录入发货客户信息：
 *界面为信息录入界面：包括：手机、电话、客户名称、
 *客户编码、发货联系人、发货人地址；
 *1.1	手机：发货人手机号码；
 *1.2	电话：发货人电话号码；
 *1.3	客户名称：发货客户公司或单位名称，可支持搜索查询；
 *1.4	客户编码：我司给客户的客户号；
 *1.5	发货联系人：发货客户的客户姓名；
 *2.	   录入收货客户信息：
 *界面为信息录入界面：包括：手机、电话、发货联系人、发货人地址；
 *1.6	手机：发货人手机号码；
 *1.7	电话：发货人电话号码；
 *1.8	收货联系人：收货客户的客户姓名
 *1.9	收货人地址：收货客户的详细联系地址，支持国家行政区域自动过滤；
 *3.	录入货物信息：
 *录入信息包括：货物名称、总件数、总重量、货物尺寸、总体积、货物包装
 *1.1	货物名称：货物的名称；
 *1.2	总件数：收货时货物的总件数；
 *1.3	总重量：收货时货物的总重量；
 *1.4	总体积：收货时货物的总体积；
 *1.5	货物包装：货物的包装数；
 *4.界面标题：计费付款
 *录入信息包括：约车报价、总运费、增值服务费、
 *开单付款方式、预付金额、到付金额
 *1.1	约车报价：整车约车费用
 *1.2	总运费：运费总合
 *1.3	增值服务费：增值服务费总合
 *1.4	开单付款方式：开发付款的方式
 *1.5	预付金额：现付金额
 *1.6	到付金额：到付金额
 *1.6	操作步骤
 *1.6.1	导入整车约车编号
 *序号	基本步骤	相关数据	补充步骤
 *1	用户输入约车编号		
 *2	用户点击“确定”按钮	约车信息	系统自动带出相应的约车信息，
 *约车编号不可编辑，规则参考SR1
 *扩展事件写非典型或异常操作过程
 *序号	扩展事件	相关数据	备注
 *1.7	业务规则
 *序号	描述
 *SR1	1、校验是否有此约车编号，如果没有，提示无此约车编号，
 *如果有，但是没有受理，提示“约车编号未受理”，如果受理拒绝，
 *提示“约车失败”并提示失败原因，如果单号已经导入过，不能重复导入，
 *提示“约车编号已经导入过”，如果不是本部门的整车约车编号，
 *录入后提示“不能导入其他部门整车约车编号”
 *2、根据约车信息填充界面，请车费用填充进约车报价中。
 *
 *
 *
 *运单提交（离线）SUC业务规则
 *
 *1.1	相关业务用例
 *BUC_FOSS_5.20.30_550  营业部离线开单
 *1.2	用例描述
 *营业员通过本用例录入提交。
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件	1.	运单在线登录成功
 *2.	运单已填写完整	SUC-441录入收货客户信息(离线)
 *SUC-442录入发货客户信息(离线)
 *SUC-443录入货物信息(离线)
 *SUC-444录入增值服务信息(离线)
 *SUC-445录入运输信息(离线)
 *SUC-412运单收银(离线)
 *SUC-372暂存存运单(离线)
 *SUC-362在线登录
 *后置条件	1.	传送运单号、金额、帐号等结算数据到财务子系统
 *2.	传送货物名称、件数、重量等货物信息到中转子系统，安排运输计划
 *3.	传送运单号等信息到官网，客户查询运单状态
 *4.	给收货人发送出发短信，给上门接货客户发送短信
 *5.	订单信息反馈给CRM系统，订单处理结果为：离线已开单。
 *6、 当运输性质为精准空运时，
 *提交成功后会自动生成订舱信息流到最终配载部门（即总调）的舱位信息中	
 *1.4	操作用户角色
 *操作用户	描述
 *营业员	通过运单开单界面，提交离线运单信息
 *1.5	界面要求
 *1.5.1	表现方式
 *Web方式
 *1.5.2	界面原型
 *1.5.2.1	运单离线开单界面 
 *1.5.2.2	运单确认提交界面 
 *1.5.3	界面描述
 *1.5.3.1	运单开单界面
 *界面共包括7个部分：
 *1、发货客户信息；
 *2、收货客户信息；
 *3、货物信息；
 *4、运输信息；
 *5、增值服务信息；
 *6、计费付款；
 *7、功能按钮。
 *具体描述参考DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 *DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc
 *运单确认提交界面
 *界面共包括3个部分：
 *1、离线运单基本信息；
 *2、在线基本信息  
 *3、功能复选框；
 *4、功能按钮
 *1、	运单基本信息：单号、到付总运费、预付总运费、
 *代收货款、保险价值、收货人名称、付款方式、
 *提货网点、提货方式、收货人地址、收货人电话、
 *货物名称、重量/体积/件数、包装 。
 *2、	运单基本信息：单号、到付总运费、
 *预付总运费、代收货款、保险价值、收货人名称、
 *付款方式、提货网点、提货方式、收货人地址、
 *收货人电话、货物名称、重量/体积/件数、包装
 *3、功能复选框：打印运单（选择之后，点击确定系统自动打印运单）、
 *打印标签（选择之后，点击确定系统自动打印标签）、
 *下一条离线运单信息（选择后提交运单成功后填充下一条）。
 *4、功能按钮：确定、取消 。
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	营业员在运单查询界面，查询出离线开单未提交的运单		
 *2	点击“修改”	收货客户信息、发货客户信息、货物信息、
 *增值服务信息、计费付款信息、运输信息、代打木架信息
进入开单界面
 *1、自动填充发货客户信息
 *参考规则SR1、 SR2 
 *2、自动填充发货收货客户信息
 *参考规则SR1 、SR2
 *3、自动填充货物信息
 *参考规则SR3，SR7
 *4、自动填充运输信息
 *参考规则SR4，SR7
 *5、自动填充增值服务信息
 *参考规则SR5，SR7
 *6、自动填充计费付款信息
 *参考规则SR6，SR7
 *填充完之后，
 *如果有代打木架，弹出代打木架信息录入界面
 *2	点击“提交”按钮提交运单。	
 *1、客户上门提供的货物承运信息。
 *2、上门接货客户提供的承运信息。	
 *1.提交运单，根据系统规则SUC-439提交运单系统用例进行提交
 *2. 调用中转接口，生成库存信息
 *3.系统自动弹出，运单确认提交界面
 *1.7	业务规则
 *序号	描述
 *SR1	1.根据离线录入的发货客户信息，
 *使用发货联系人手机号码到远程服务器进行匹配客户信息，
 *如果没有，根据离线填写的发货客户信息，
 *填充发货联系人手机号、发货联系人、发货人地址、发货人电话号码，
 *如果有，根据查询出来到客户信息，显示会员编码和客户名称，
 *并根据离线填写的客户信息，填充发货联系人手机号、发货联系人、
 *发货人地址、发货人电话号码。填充时不需要进行联动和校验
 *1.填充发货信息和发货信息时，根据发货客户手机到CRM进行查询，
 *无论查询出有多少条阻塞式弹出CRM查询框让用户进行选择，
 *当发货客户手机为空时，根据电话号码到CRM进行查询，
 *无论查询出有多少条阻塞式弹出CRM查询框让用户进行选择，
 *当用户选择时进行填充，如果客户选择取消时，不填充 
 *2.如果修改，修改规则参考系统用例SUC-492录入发货客户信息
 *SR2	11.如果修改，修改规则参考系统用例SUC-492录入发货客户信息和SUC-493录入收货客户信息
 *.根据离线录入的收货客户信息，
 *使用收货联系人手机号码到远程服务器进行匹配客户信息，
 *如果没有，根据离线填写的收货客户信息，
 *填充发货联系人手机号、发货联系人、发货人地址、发货人电话号码，
 *如果有，根据查询出来到客户信息，显示会员编码和客户名称，
 *并根据离线填写的收货客户信息，填充发货联系人手机号、发货联系人、
 *发货人地址、发货人电话号码。填充时不需要进行联动和校验
 *2.如果修改，修改规则参考系统用例SUC-493录入收货客户信息
 *SR3	1、	把货物信息填充到界面中，在填充过程中，
 *不需要进行联动和校验
 *2、1、	如果修改、修改规则参考系统用例SUC-494 录入货物信息
 *SR4	1、把运输信息到界面中，在填充过程中，
 *不需要进行联动和校验
 *2、如果修改，修改规则参考系统用例SUC-496录入运输信息
 *SR5	1、把增值服务信息填充到界面中，在填充过程中，不需要进行联动和校验
 *21、如果修改，修改规则参考系统用例SUC-494录入增值服务信息
 *SR6	1.如果发货客户有优惠协议，那么根据SUC-486-运单收银系统用例规则进行重新计价
 *21.如果修改，修改规则参考系统用例SUC-408  运单收银
 *SR7	1、在填充过程中涉及到需要用基础资料、产品价格和客户资质的校验、
 *计算、联动，都用最新信息进行校验、计算、联动，
 *如果离线保存时已经有计算或联动的值，需要进行覆盖，
 *需要最新计算和联动的值。 
 *
 *运单收银（离线）SUC业务规则
 *
 *1.1	相关业务用例
 *BUC_FOSS_5.20.30_550 (营业部离线开单)
 *1.2	用例描述
 *当网络故障或服务器原因导致营业部不能正常开单时，客户上门发货为汽运或空运，营业员确认承运信息之后，通过离线系统计算货物总付款金额。	
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件	1.	确认承运信息已录入完毕；	
 *后置条件	1.	录入收入；	
 *1.4	操作用户角色
 *操作用户	描述
 *营业员	可查询、确认发货/收货客户应付款金额，及客户的付款方式
 *开单员	可查询、确认发货/收货客户应付款金额，及客户的付款方式
 *1.5	界面要求
 *1.5.1	表现方式
 *Web页面 
 *1.5.2	界面原型 
 *1.5.3	界面描述
 *营业员点击离线运单开单，进入离线运单开单界面。
 *本界面标题：计费付款。
 *1.	计费类型：包括重量计费、体积计费，默认显示重量计费；
 *2.	计费费率，
 *3.	公布价运费
 *4.	增值服务费用
 *5.	总运费
 *6.	开单付款方式：包括现金、到付；
 *7.	预付金额；
 *8.	到付金额；
 *9.	计费重量。
 *1.6	操作步骤
 *1.6.1	运单收银
 *序号	基本步骤	相关数据	补充步骤
 *1	查看计费类型和计费费率		1.	规则-请参见系统规则SR1、SR2；
 *2	查看公布价运费和增值服务费及总运费		1.	规则-请参见系统规则SR3；
 *3	录入付款方式		1.	规则-请参见系统规则SR4；
 *4	确认和录入预付金额和到付金额		1.	规则-请参见系统规则SR5；
 *5	查看计费类型和计费费率		1.    规则-请参见系统规则SR1、SR2；
 *扩展事件写非典型或异常操作过程
 *序号	扩展事件	相关数据	备注
 *5a			
 *5b			
 *1.7	业务规则
 *序号	描述
 *SR1	1.	计费类型分为重量计费、体积计费，
 *由系统自动生成，不可修改；
 *默认重量计费；
 *2.	对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费；
 *若按重量计费运费较按体积计费运费较高，
 *则计费类型为重量计费；若按体积计费运费较按重量计费运费较高，
 *则计费类型为体积计费；
 *3.	运输类型为汽运时,计费重量为空，不可修改；
 *2.4.	运输类型为空运时计费重量应为重量和体积*1000000/6000进行对比，取大；
 *SR2	1.	费率为对应计费类型、目的站、提货网点及运输类型的走货单价；
 *2.	目的站、提货网点及运输类型确认后，
 *即可自动显示对应计费类型的费率；（来自本地价格基础资料）
 *3.	费率可以保留到小数点后2位；运费、预付金额、到付金额为整数，
 *按照四舍五入的原则；
 *SR3	1.	公布价运费（即重量、体积计费的运费）=每公斤单价/每方价格与货物实际重量/体积的乘积，
 *对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费；
 *（来自价格基础资料，下载并且使用当前启用的最新的价格版本，
 *参考SUC-547下载基础资料PC-SR1）
 *2.	增值服务费=送货费+包装费+保价费+代收手续费+其他费用和；
 *3.	总运费费用=公布价运费+增值服务费+装卸费 =预付金额+到付金额；
 *3.4.	公布价运费=纯运费+装卸费
 *SR4	1.	付款方式只有：到付，现付
 *SR5	1.	当付款方式为现付时，预付金额必须大于0；否则，
 *提示信息“付款方式为现付，预付金额必须大于0”；
 *2.	当付款方式为到付时，到付金额必须大于0；否则，
 *提示信息“付款方式为【到付】，到付金额必须大于0”；
 *3.	发货人和收货人都付款，付款方式为现付;
 *发货人跟收货人都付款，付款方式选择到付。
 *4.3.	支持发货人、收货人付款方式的组合，
 *例如付款方式为到付500：那么预付金额可以未300，
 *到付金额为200，总和等于500 ，那么预付300，
 *可以选择现金100元，临时欠款100，银行卡刷卡100元。
 *SR6	参考suc-403生成运单SR1: 系统自动检测，
 *如果客户端X天还没有更新，不能打开离线运单界面，
 *并提示“请在间隔X天内，在线登录系统
 *
 *
 *确认公布总价（离线）SUC业务规则
 *1.1	相关业务用例
 *BUC_FOSS_5.20.30_550 (营业部离线开单)
 *1.2	用例描述
 *当网络故障或服务器原因导致营业部不能正常开单时，
 *客户上门发货为汽运或空运，营业员确认承运信息之后，
 *通过离线系统计算出本地公布价与计费重量或计费体积乘积的运费。	
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件	离线系统已经更新最新且已启用的价格版本	参考规则SR4
 *后置条件	离线开单保存	
 *1.4	操作用户角色
 *操作用户	描述
 *营业员	营业员录入货物承运信息等。 
 *开单员	开单员录入货物承运信息等。
 *1.5	界面要求
 *1.5.1	表现方式
 *Web页面 
 *1.5.2	界面原型
 *无
 *1.5.3	界面描述
 *无
 *1.6	操作步骤
 *1.6.1	计算公布总价
 *序号	基本步骤	相关数据	补充步骤
 *1	登陆离线系统		
 *2	打开“离线开单”界面。		
 *3	录入发货人信息		参考SUC-442
 *4	录入收货客户信息		参考SUC-441
 *5	录入货物信息		参考SUC-443
 *6	录入运输信息		参考SUC-445
 *进行前面5操作之后，若是空运，
 *系统读取本地下载的空运公布价价格方案计算出公布总价，
 *显示在运单开单界面。
 *若是汽运，系统读取本地下载的汽运公布价价格方案（计算出公布总价，
 *显示在运单开单界面。
 *参考规则SR1，SR2，SR3
 *扩展事件写非典型或异常操作过程
 *序号	扩展事件	相关数据	备注
 *5a			
 *1.7	业务规则
 *序号	描述
 *SR1	1）汽运：上门发货汽运运费最低X元一票；（同城、卡航；可配置）；
 *上门接货汽运运费最低X元一票；（同城、卡航；可配置）；
 *2）空运：空运运费最低X元一票；（可配置）；
 *SR2	1）汽运：当货物为“接货”时，系统自动匹配生成公布价“接货价格方案”；
 *当货物为“非接货”时，系统自动匹配生成公布价“非接货的价格方案”；
 *2）空运：系统自动匹配公布价 “空运价格方案”；
 *空运价格只有上门发货一套价格方案，
 *如有接货费在其他费用里添加一项接货费。
 *（来自价格基础资料，下载并且使用当前启用的最新的价格版本，参考SUC-547下载基础资料PC-SR1）
 *SR3	1）计费方式分为重量计费、体积计费；重量、
 *体积计费的运费=每公斤单价与货物实际重量的乘积 或 每方单价与货物实际体积的乘积，
 *对于一票货物，系统按重量和体积分别计算并取大优先的原则计费给出公布价总运费,
 *计费方式即为取大的一方；
 *SR4	参考suc-403生成运单SR1: 系统自动检测，如果客户端X天还没有更新，
 *不能打开离线运单界面，并提示“请在间隔X天内，在线登录系统
 *
 *
 *录入运输信息（离线）SUC业务规则
 *营业员点击运单开单，进入运单开单界面。
 *本界面标题：录入运输信息。
 *界面信息包括：收货部门、单号、运输性质、配载类型、
 *提货方式、目的站、提货网点、上门接货、集中接货、对外备注、
 *对内备注、储运注意事项、配载线路（配载航班）、配载部门、
 *最终配载部门、预计出发时间、预计派送/提货时间。
 *1.	收货部门：揽货部门；
 *2.	单号：运单单号；
 *3.	运输性质：公司产品类型，包括精准空运、精准汽运（长途）、
 *精准卡航、精准城运、汽运偏线、精准汽运（短途）；
 *4.	配载类型：公司走货方式，包括专线、偏线（外发）、合大票、单独开单；
 *5.	提货方式：公司提供的送货方式，包括自提（不含机场提货费）、
 *免费自提、机场自提、送货上门、免费送货、自提、内部带货自提、送货进仓；
 *6.	目的站：客户所发货物要到达的目的城市和区域；
 *7.	提货网点：收货客户可以领取货物的部门；
 *8.	上门接货：我司提货的一种服务，司机上门接货，确认承运，
 *有区别于客户上门发货；
 *9.	集中接货：是否为集中接送货区域，以集中接货的方式揽货，
 *是对上门接货的一个属性补充；
 *10.	对外备注：客户可以看到的备注信息，包括：空、保丢不保损、
 *“不承保发霉、变质、虫蛀虫咬之损失”、“ 不承保刮花、变形、撞凹之损失”、
 *不可重压、易潮、不可倒置、客户指定提货网点；
 *11.	对内备注：仅限公司内部人员看到的备注信息；
 *12.	储运注意事项：对外备注和对内备注信息的叠加；
 *13.	配载线路（配载航班）:配载类型为偏线（外发）或专线时时，
 *显示“配载线路”，为从出发部门到达开单目的站，我司走货的线路；
 *配载类型为合大票或单独开单时，显示“配载航班”，为我司规则的空运走货的航班类型，
 *包括早班、中班、晚班；
 *14.	配载部门:开单收货部门出发货配载专线；
 *15.	最终配载部门:货物到达的最终部门；
 *16.	预计出发时间:我司走货的预计出发时间，
 *适用于运输类型为“精准卡航”及“精准城运”；
 *17.	预计派送/提货时间: 我司承诺客户的可提货或送货的时间，
 *适用于运输类型为“精准卡航”及“精准城运”；
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	录入收货部门		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR1；
 *2	录入单号		
 *1.	系统校验单号的合法性；单号唯一在离线提交时，判断，
 * 除单号唯一性其他参考规则SR1
 *3	录入运输性质		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR2、SR3；
 *4	录入配载类型		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR3、SR4、SR5、SR6；
 *5	录入提货方式		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR4、SR6、SR7、SR8；
 *6	录入目的站		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR9；
 *7	录入提货网点		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR5、SR8、SR9、SR10；
 *8	勾选是否上门接货		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR11；
 *2.	勾选上门接货，必须输入司机工号，司机工号为6为数字。参考规则SR3
 *9	勾选是否集中接货		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR11、SR12；
 *10	录入对外备注		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR13、SR14；
 *11	录入对内备注		
 *1.    参考规则SR2
 *12	生成储运注意事项		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR14；
 *13	录入配载线路（配载航班）		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR15；
 *2.	参考SUC-547下载基础资料(PC)
 *14	录入配载部门		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR16；
 *15	录入最终配载部门		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR17；
 *16	生成预计出发时间		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR18；
 *17	生成预计派送/提货时间		
 *1.	规则-请参考SUC-496录入运输信息-系统规则SR19；
 *1.7	业务规则
 *序号	描述
 *SR1	1、单号为8-9位数字，
 *不能输入重复单号。
 *SR2	1、对内备注仅公司内部人员可见，
 *输入字符长度不得超过500。
 *SR3	1、当勾选上门接货时，
 *司机工号必填，且为6位数字。
 *SR4	参考suc-403生成运单SR1: 
 *系统自动检测，如果客户端X天还没有更新，
 *不能打开离线运单界面，并提示“请在间隔X天内，在线登录系统
 *
 *
 *录入收货客户信息（离线）SUC业务规则
 *
 *1.1	相关业务用例
 *BUC_FOSS_5.20.30_550  营业部离线开单
 *1.2	用例描述
 *营业员通过本用例录入收货客户信息。
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件	1.	离线登录成功	
 *后置条件		
 *1.4	操作用户角色
 *操作用户	描述
 *营业员	可查询、录入、修改收货客户信息
 *1.5	界面要求
 *1.5.1	表现方式
 *Web方式
 *1.5.2	界面原型
 *1.5.3	界面描述
 *营业员点击运单开单，进入运单开单界面。
 *本界面为录入收货客户信息。
 *界面为信息录入界面包括：手机、电话、客户名称、
 *客户编码、收货联系人（收货部门）、收货人地址；
 *1.	手机：收货人手机号码；
 *2.	电话：收货人电话号码，可以添加多个；
 *3.	收货联系人：收货客户的客户姓名；
 *4.	收货人地址：收货客户的详细联系地址，
 *支持国家行政区域自动过滤；
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	录入手机号码		
 *1.	规则-请参见系统规则SR1；
 *2	录入电话号码		
 *1.	规则-请参见系统规则SR1；
 *3	录入收货联系人（收货部门）		
 *规则-请参见系统规则SR3、SR4；
 *4	录入收货人地址		
 *1.	规则-请参见系统规则SR2；
 *1.7	业务规则
 *序号	描述
 *SR1	1.	收货客户手机号码及固定电话至少提供一个，
 *手机号码只能为数字并且为11位，固定电话号码只能为数字，
 *且可添加多个；添加多个时，必须用“，”或“、”或“/”分开；
 *固定电话号码字段也可以录入手机号；
 *SR2	1.	客户详细地址必填至乡/镇，
 *且乡镇下一级内容不能为空；
 *SR3	1.	当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“收货联系人”字段更改为“收货部门”；
 *SR4	
 *1.	若为公司内部带货，则收货客户信息中的收货部门名称必须与OA系统中组织架构名称保持一致；
 *SR5		前提：收货人信息都已填充完毕，再进行手机号，
 *电话号码修改：
 *① 	如在三月记录中，是否再次弹出框选择记录后覆盖原来的信息，
 *不选择只修改手机号or电话号码，发货客户其他信息不置空
 *② 	如不在三月记录中，是否只修改手机号或电话，
 *发货客户其他信息不置空-------
 *1、再次弹出框选择记录后覆盖原来的信息2、
 *如果带出时是CRM客户，那么要删除客户编码和客户名称后才能够修改联系人信息，
 *如果带出来不是，那么可以直接修改，那么修改电话号码和手机号，带出的信息都会覆盖
 *
 *
 *录入发货客户信息（离线）SUC业务规则
 *
 *1.1	相关业务用例
 *BUC_FOSS_5.60.05_520  确认承运信息
 *1.2	用例描述
 *营业员在网络不通时，通过本用例录入发货客户信息。
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件	离线登录成功；	
 *后置条件		
 *1.	运单收银
 *2.	录入增值服务信息
 *1.4	操作用户角色
 *操作用户	描述
 *营业员	可查询、录入、修改发货客户信息
 *1.5	界面要求
 *1.5.1	表现方式
 *Web方式											
 *1.5.2	界面原型 
 *1.5.3	界面描述
 *营业员点击运单开单，进入运单开单界面。
 *本界面为录入发货客户信息。
 *界面为信息录入界面：包括：手机、电话、
 *发货联系人（发货部门）、发货人地址；
 *1.	手机：发货人手机号码；
 *2.	电话：发货人电话号码，可以添加多个；
 *3.	发货联系人（发货部门）：发货客户的客户姓名，
 *当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“发货联系人”字段更改为“发货部门”；
 *4.	发货人地址：发货客户的详细联系地址，
 *支持国家行政区域自动过滤；
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	录入手机号码		
 *1.	规则-请参见系统规则SR1；
 *2	录入电话号码		
 *1.	规则-请参见系统规则SR1；
 *3	录入发货联系人（发货部门）		
 *1.	规则-请参见系统规则SR2、SR3；
 *4	录入发货人地址		
 *1.	提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段；
 *2.	规则-请参见系统规则SR4；
 *1.7	业务规则
 *序号	描述
 *SR1	1.	发货客户手机号码及固定电话至少提供一个，
 *手机号码只能为数字并且为11位，固定电话号码只能为数字，
 *且可添加多个, 添加多个时，必须用“，”或“、”或“/”分开；固定电话号码字段也可以录入手机号；
 *SR2	1.	当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *“发货联系人”字段更改为“发货部门”；
 *SR3	1.	若为公司内部带货，则发货客户信息中的发货部门名称必须与OA系统中组织架构名称保持一致；
 *SR4	1.	客户详细地址必填至乡/镇，且乡镇下一级内容不能为空；
 *SR5 	1．焦点到联系人文本框后，下个焦点直接跳过地址分段输入框，到下个控件
 * 
 */
@SecurityNonCheckRequired
@Remote()
public class WaybillHessianRemoting implements IWaybillHessianRemoting {
	private static final Log log = LogFactory.getLog(WaybillHessianRemoting.class);
	
	private static final int NUM998 = 998;
	
	@Resource
	IDeptTransferMappingService deptTransferMappingService;
	/**
	 * 人员 DAO
	 */
	@Resource
	IEmployeeDao employeeDao;
	@Resource
	IQuerySwiftNumberInfoService querySwiftNumberInfoService;
	
	 public IQuerySwiftNumberInfoService getQuerySwiftNumberInfoService() {
		return querySwiftNumberInfoService;
	}
	public void setQuerySwiftNumberInfoService(
			IQuerySwiftNumberInfoService querySwiftNumberInfoService) {
		this.querySwiftNumberInfoService = querySwiftNumberInfoService;
	}

	/* 代办事项服务类
	 * */
	@Resource
	ITodoActionService todoActionService;
	@Resource
	ILTLEWaybillService lTlEWaybillService;
	@Resource
	IOmsOrderService omsOrderService;
	@Resource
	ILabelPushProcessService labelPushProcessService;
	/**
	 * 运单管理服务
	 */
	@Resource
	IWaybillManagerService waybillManagerService;
	
	/**
	 * 调用WK接口，查询收货人信息
	 */
	@Resource
	IEcsQueryService ecsQueryService;
	
	public void setEcsQueryService(IEcsQueryService ecsQueryService) {
		this.ecsQueryService = ecsQueryService;
	}

	@Resource
	IWaybillExpressService  waybillExpressService;
	/**
	 * 客户信用额度管理服务
	 */
	@Resource
	ICustomerBargainService customerBargainService;
	
	@Resource
	IConfigurationParamsService configurationParamsService;
	@Resource
	ICustomerCircleRelationNewService customerCircleRelationNewService;

	/**
	 * 运单暂存服务
	 */
	@Resource
	IWaybillPendingService waybillPendingService;
	
	/**
	 * 运单打印服务
	 */
	@Resource
	IPrintInfoDao printInfoDao;
	
	/**
	 * 订单服务(WebService)
	 */
	@Resource
	ICrmOrderService crmOrderService;
	
	/**
	 * 订单服务(JMS)
	 */
	@Resource
	ICrmOrderJMSService crmOrderJMSService;
	
	@Resource
	IFlightService  flightService;
	
	@Resource
	ICusBargainService cusBargainService;
	
	@Resource
	private IProductDao productDao;
	
	@Resource
	private ISortingScanService sortingScanService;
	
	/**
	 * CUBC灰度接口
	 */
	@Resource
	private IGrayScaleService grayScaleService;
	
	public ISortingScanService getSortingScanService() {
		return sortingScanService;
	}
	public void setSortingScanService(ISortingScanService sortingScanService) {
		this.sortingScanService = sortingScanService;
	}
	public List<SortingScanEntity> queryEwayBillRecords(SortingScanDto sortingScanDto){
		
		return sortingScanService.queryEwayBillRecords(sortingScanDto,1,NumberConstants.NUMBER_10);
	}
 
	/**
	 * 组织信息服务
	 */
	@Resource
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 营业部信息服务
	 */
	@Resource
	ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 代理网点服务
	 */
	@Resource
	IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	/**
	 * 业务互斥锁服务
	 *  提供业务互斥锁服务接口
	 */
	@Resource
	private IBusinessLockService businessLockService;
	
	@Resource
	private ILdpAgencyDeptService  ldpAgencyDeptService ;
	/**
	 * 业务锁定超时自动释放时间:15秒
	 */
	private static final int LOCK_TIMEOUT = 0;
	
	/**
	 * 营业部和集中开单组关系Service类
	 */
	@Resource
	private ISalesBillingGroupService salesBillingGroupService;
	
	
	/**
	 * 运单实时状态
	 */
	@Resource
	private IActualFreightService actualFreightService;

	/**
	 * 用户服务
	 */
	@Resource
	IUserService userService;
	
	/**
	 * 禁运物品
	 */
	@Resource
	IProhibitGoodsDao prohibitGoodsDao;
	
	// 限保物品
	@Resource
	private IInsurGoodsDao pkpinsurGoodsDao;
	
	@Resource
	private IPrintLabelService printLabelService;
	/**
	 * 
	 */
	@Resource
	ISysConfigDao sysConfigDao;
	
	/**
	 * 数据字典值服务
	 */
	@Resource
	private IDataDictionaryValueDao  pkpdataDictionaryValueDao;
	
	/**
	 * 国际化信息
	 * 
	 * @autor 278328
	 * @date 2015-05-17 上午8:59:57
	 */
    @Resource
	private IMessageBundle messageBundle; 
	
	/**
	 * 产品价格
	 */
	@Resource
	private IPublishPriceService publishPriceService;
	
	/**
	 * 货物类型DAO
	 */
	@Resource
    IGoodsTypeDao goodsTypeDao;
	
	@Resource
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 自提件服务
	 */
	@Resource
	private IMinFeePlanService minFeePlanService;

	/**
	 * zxy 20130927
	 * 
	 * 费用明细服务
	 */
	@Resource
	private IPriceEntryDao priceEntryDao;
	
	/**
	 * zxy 20140428 MANA-2018
	 * 行政区域服务
	 */
	@Resource
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**
	 * 异常货接口
	 * */
	@Resource
	private IQmsYchService qmsYchService;
	public void setQmsYchService(IQmsYchService qmsYchService) {
		this.qmsYchService = qmsYchService;
	}
	/**
	 * 订单dao
	 */
	@Resource
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	@Resource
	private IWaybillPictureDao waybillPictureDao;
	@Resource
	private IEarlyWarningService earlyWarningService;
	@Resource
	private ILateCouponService lateCouponService;
	/**
	 * 根据运单查询打木信息
	 */
	@Resource
	private IWoodenRequirementsDao woodenRequirementsDao;

	public IMinFeePlanService getMinFeePlanService() {
		return minFeePlanService;
	}
	
	public void setMinFeePlanService(IMinFeePlanService minFeePlanService) {
		this.minFeePlanService = minFeePlanService;
	}
	
	
	public void setGrayScaleService(IGrayScaleService grayScaleService) {
		this.grayScaleService = grayScaleService;
	}

	/**
	 * 综合外场服务
	 */
	@Resource
	private IOutfieldService pkpoutfieldService;
	
	/**
	 * 综合线路服务
	 */
	@Resource
	private ILineService pkplineService;
	
	/**
	 * 快递综合线路服务
	 */
	@Resource
	private IExpressLineService expresslineService;
	
	@Resource
	private ISysConfigService pkpsysConfigService;
	
	/**
	 * 百度推送服务
	 */
	@Resource
	private IPushService  pushService ;
	
	@Resource
	private IWaybillChargeDtlService waybillChargeDtlService;
	
	@Resource
	private IGreenHandWrapWriteoffService greenHandWrapWriteoffService;

	@Resource
	private IDepotAddressService depotAddressService ;
	
	/**
	 * 交接Dao
	 */
	@Resource
	private IStayHandoverDao stayHandoverDao;
	
	@Resource
	private IStoreBillingMapService storeBillingMapService;
	public void setStoreBillingMapService(
			IStoreBillingMapService storeBillingMapService) {
		this.storeBillingMapService = storeBillingMapService;
	}
	public void setStayHandoverDao(IStayHandoverDao stayHandoverDao) {
		this.stayHandoverDao = stayHandoverDao;
	}
	
	/**
	 * 发送短信
	 */
	@Resource
	private ISendSmsVoiceService sendSmsVoiceService;
	
	public void setSendSmsVoiceService(ISendSmsVoiceService sendSmsVoiceService) {
		this.sendSmsVoiceService = sendSmsVoiceService;
	}
	
	/**
	 * 签收变更结果
	 */
	@Resource
	ISignChangeService signChangeService;
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	/**
	 * 更改服务
	 */
	@Resource
	IWaybillRfcService waybillRfcService;
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/**
	 * 库存服务
	 */
	@Resource
	IStockService stockService;
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	/**
	 * 签收服务
	 */
	@Resource
	IWaybillSignResultService  waybillSignResultService;
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * 付款服务
	 */
	@Resource
	IRepaymentService repaymentService;
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	@Resource
	ITakingService takingService;
	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}
	@Resource
	IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}
	
	@Resource
	private IContrabandSignService contrabandSignService;
	public void setContrabandSignService(
			IContrabandSignService contrabandSignService) {
		this.contrabandSignService = contrabandSignService;
	}
	
	@Resource
	private IHandOverBillService handOverBillService;
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	//中转走货路径类
	@Resource
	private ITransportationPathDao transportationPathDao;
	public void setTransportationPathDao(
			ITransportationPathDao transportationPathDao) {
		this.transportationPathDao = transportationPathDao;
	}
	/**
	 * 上传到IT服务台Service
	 */
	@Resource
	private IUploadITService uploadITService;
	@Resource
	private ICityMarketPlanService cityMarketPlanService;
	
	@Resource
	private IPrintAgentWaybillService printAgentWaybillService;
	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}
	public IPrintAgentWaybillService getPrintAgentWaybillService() {
		return printAgentWaybillService;
	}
	public void setPushService(IPushService pushService) {
		this.pushService = pushService;
	}
	public IUploadITService getUploadITService() {
		return uploadITService;
	}
	public void setUploadITService(IUploadITService uploadITService) {
		this.uploadITService = uploadITService;
	}
	
	
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	
	/**
	 * @param actualFreightService the actualFreightService to set
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	/**
	 * 子母件服务
	 * */
	@Resource
	public IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	/**
	 * 定价优化项目---降价返券
	 * 
	 * 返券发送日志服务
	 * 
	 * @author Foss-206860
	 * **/
	@Resource
	public IPendingSendCouponLogService pendingSendCouponLogService;
	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
	 * @author Foss-206860
	 */
	@Override
	public String queryIndustrySourceCategory(String goodsName) {
		// TODO Auto-generated method stub
		return waybillManagerService.queryIndustrySourceCategory(goodsName);
	}
	/**
	 * 内部发货
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Resource
	private IInempDiscountPlanService inempDiscountPlanService;
	
	public void setInempDiscountPlanService(
			IInempDiscountPlanService inempDiscountPlanService) {
		this.inempDiscountPlanService = inempDiscountPlanService;
	}
	@Resource
	private ILtDiscountBackInfoService ltDiscountBackInfoService;
	
	public void setLtDiscountBackInfoService(
			ILtDiscountBackInfoService ltDiscountBackInfoService) {
		this.ltDiscountBackInfoService = ltDiscountBackInfoService;
	}
	
	//合伙人项目
	@Resource
	private IPartnersWaybillService partnersWaybillService ;

	public void setPartnersWaybillService(
			IPartnersWaybillService partnersWaybillService) {
		this.partnersWaybillService = partnersWaybillService;
	}
	
	/**
	 * 
	 * <p>
	 * 检查运单号码是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-23 下午8:21:07
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#checkWaybillNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean checkWaybillNo(String waybillNo) {
		return waybillManagerService.isWayBillExsits(waybillNo);
	}
	
	
	/**
	 * 
	 * <p>
	 * 检查运单号码是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-23 下午8:21:07
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#checkWaybillNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean checkWaybillNoAndOrderNo(String waybillNo,String orderNo) {
		return waybillManagerService.isWayBillExsitsAndOrderNo(waybillNo, orderNo);
	}
	
	/**
	 * 
	 * <p>
	 * 检查运单号码是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-23 下午8:21:07
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#checkWaybillNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean checkWaybillNoExceptPDAPending(String waybillNo) {
		return waybillManagerService.isWayBillExsitsNoPDAPending(waybillNo);
	}

	/**
	 * 检查补录时电子运单号码是否需要校验
	 * 
	 * @param waybillNo
	 * @param orgCode
	 * @return boolean
	 */	
	public boolean checkEWaybillNoExceptPDAPending(String waybillNo) {
		return waybillPendingService.checkEWaybillPending(waybillNo);
	}
	
	/**
	 * @项目：智能开单项目
	 * @功能：定义时间统计service
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-27上午09:00
	 */
	@Resource
	IIntelligenceTimeRecordService intelligenceTimeRecordService;
	
	/**
	 * 功能：submitWaybill提交运单信息
	 * 
	 * @param:
	 * @return:void
	 * @since:1.6
	 */
	@Override
	public void submitWaybill(WaybillDto waybill) {
		List<MutexElement> mutexes = new ArrayList<MutexElement>();	
		/**
		 * 创建运单互斥对象
		 */
		MutexElement mutexElement = new MutexElement(waybill.getWaybillEntity().getWaybillNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
		mutexes.add(mutexElement);
		/**
		 * 判断订单号是否为空
		 */
		if(waybill.getWaybillEntity().getOrderNo()!=null && !"".equals(waybill.getWaybillEntity().getOrderNo())){
			//根据订单号获取运单信息
			WaybillEntity waybillEntity=waybillManagerService.queryWaybillByOrderNo(waybill.getWaybillEntity().getOrderNo());
			/**
			 * 判断运单是否为空，如果不为空，说明此订单已存在，否则创建订单互斥对象
			 */
			if(waybillEntity!=null && !WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybill.getWaybillEntity().getPendingType())){
				
				ActualFreightEntity af = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());
				
				if(af!=null){
					if(WaybillConstants.EFFECTIVE.equals(af.getStatus())
							
						/**|| af.getStatus() == null*/){

						throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_ORDER_SUBMIT_LOCKED);
					}
				}
				
			}
			
			MutexElement mutexElementOrder = new MutexElement(waybill.getWaybillEntity().getOrderNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
			mutexes.add(mutexElementOrder);
			
			
		}
		//zxy 20130924 BUG-55409 start 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
		//互斥锁定
		boolean isLocked = false;
		try{
			isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
		}catch(Exception e){
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		//zxy 20130924 BUG-55409 end 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
		if(!isLocked){
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		try{
			// 获得当前用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();	
			String oldPendingType = waybill.getWaybillEntity().getPendingTypeByDb();
			waybill.setCurrentInfo(currentInfo);
			
			String guiTitleName = waybill.getGuiTitleName();
			if(guiTitleName != null && "图片开单".equals(guiTitleName)){
				try {
					waybillManagerService.submitWaybill(waybill);
				} catch (Exception e) {
					String waybillNo = waybill.getWaybillEntity().getWaybillNo();
					WaybillPictureEntity entity = new WaybillPictureEntity();
					entity.setWaybillNo(waybillNo);
					entity.setActive(FossConstants.ACTIVE);
					
					String errorMsg  = "";
					if(e instanceof BusinessException){
						BusinessException bise =	((BusinessException)e);
						String errorCode = bise.getErrorCode();
						try{
							errorMsg = messageBundle.getMessage(errorCode, bise.getErrorArguments());
						}catch(Exception bundle){
							log.info(e.getMessage());
						}
						
					}
					
					if(errorMsg == null || "".equals(errorMsg)){
						errorMsg = e.getMessage();
					}
					
//						WaybillPictureEntity oldPictureEntity =  waybillPendingService.queryWaybillPictureByEntity(entity);
					WaybillPictureEntity newEntity = new WaybillPictureEntity();
					newEntity.setWaybillNo(waybillNo);
					newEntity.setActive(FossConstants.ACTIVE);
					
					if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(oldPendingType)){
						//获取备注信息  防止暂存图片修复信息 没有及时更新备注信息
						entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
					}else{
						//获取备注信息  防止暂存图片修复信息 没有及时更新备注信息
						entity.setPendgingType(oldPendingType);
					}
					StringBuffer sb = new StringBuffer();
					/*if(oldPictureEntity != null){
						String remark = oldPictureEntity.getRemark();
						if(remark != null){
							sb.append(remark);
							sb.append("/n");
						}
					}*/
					sb.append(errorMsg).append("  ").append(new Date());
					entity.setRemark(sb.toString());
					//更新图片开单表备注信息
					waybillPendingService.updatePictureWaybillByWaybillno(entity);
					/**
					 * @项目：智能开单项目
					 * @功能：提交耗时时间任务
					 * @author:218371-foss-zhaoyanjun
					 * @date:2016-05-19下午18:19
					 */
					if(waybill.getIbtg()!=null){
						addIntelligenceBillTime(waybill);
					}
				}
			}else{
				waybillManagerService.submitWaybill(waybill);
			}
			
			
		}finally{
			//释放锁
			businessLockService.unlock(mutexes);
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
	 * 
	 * 应用监控数据添加
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-21 下午04:19:20
	 */
	@Override
	public void businessMonitor(WaybillDto waybillDto) {
		// 获得当前用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();		
		waybillDto.setCurrentInfo(currentInfo);
		waybillManagerService.businessMonitor(waybillDto);
	}

	/**
	 * 通过运单号获取运单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:16:52
	 */
	@Override
	public WaybillEntity queryWaybillBasicByNo(String waybillNo) {
		return waybillManagerService.queryWaybillBasicByNo(waybillNo);
	}

	/**
	 * 通过运单号码集合获取运单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:16:27
	 */
	@Override
	public List<WaybillEntity> queryWaybillBasicByNoList(
			List<String> waybillNoList) {
		return waybillManagerService.queryWaybillBasicByNoList(waybillNoList);
	}

	/**
	 * 通过运单号获取运单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:11:21
	 */
	@Override
	public WaybillDto queryWaybillByNo(String waybillNo) {
		return waybillManagerService.queryWaybillByNo(waybillNo);
	}

    /**
     * 根据出发部门、到达部门、产品类型、开单时间（数据生成时间）获得预计出发时间
     * @author 026123-foss-lifengteng
     * @date 2012-11-19 下午3:40:43
     */
	@Override
	public Date searchPreLeaveTime(String departDeptCode, String specialLine,
			String productCode, Date createTime) {
		return waybillManagerService.searchPreLeaveTime(departDeptCode,
				specialLine, productCode, createTime);
	}

    /**
     * 根据预计出发日期、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺自提时间
     * @author 026123-foss-lifengteng
     * @date 2012-11-20 下午3:31:06
     */
	@Override
	public EffectiveDto searchPreSelfPickupTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime) {
		return waybillManagerService.searchPreSelfPickupTime(departDeptCode,
				arriveDetpCode, productCode, preLeaveTime, createTime);
	}

    /**
     * 根据承诺自提时间、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺派送时间
     * @author 026123-foss-lifengteng
     * @date 2012-11-20 下午3:31:49
     */
	@Override
	public EffectiveDto searchPreDeliveryTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime) {
		return waybillManagerService.searchPreDeliveryTime(departDeptCode,
				arriveDetpCode, productCode, preLeaveTime, createTime);
	}
	
	/**
	 * 判断产品时效是否存在
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	@Override
	public boolean identityEffectivePlan(String departDeptCode,
			String arriveDetpCode, String productCode, Date createTime) {
		return waybillManagerService.identityEffectivePlan(departDeptCode, 
				arriveDetpCode, productCode, createTime);
	}

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
	@Override
	public DebitDto isBeBebt(String customerCode, String orgCode,
			String debtType, BigDecimal debtAmount) {
		DebitDto dto = customerBargainService.isBeBebt(customerCode, orgCode,
				debtType, debtAmount);
		return dto;
	}

	/**
	 * 插入一条运单打印记录
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-5 下午7:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public int insertSelective(PrintInfoEntity record) {
		return printInfoDao.insertSelective(record);
	}

	/**
	 * 查询在线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-5 下午7:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public int queryPrintTimesByWaybillId(String waybillId, String waybillNo) {
		return printInfoDao.queryPrintTimesByWaybillId(waybillId, waybillNo);
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

		return waybillManagerService.queryWaybill(waybillDto);
	}
	
	/**
	 * 
	 * 自有司机通过司机工号判断是否存在
	 * @author foss-sunrui
	 * @date 2013-3-28 下午4:57:29
	 * @param drive
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#isOwnDriverExists(java.lang.String)
	 */
	@Override
	public boolean isOwnDriverExists(String driverCode) {
		return waybillManagerService.isOwnDriverExists(driverCode);
	}

	 /**
	  * 检查是否有此外请车编号</br>
	  * @author 025000-foss-helong
	  * @date 2012-12-4 下午4:48:45
	  * @param inviteNo 外请车编号
	  * @param deptCode 部门编码
	  * @return 外请车申请状态
	  */
	@Override
	public String checkInviteNoIsExists(String inviteNo, String deptCode) {
		return waybillManagerService.checkInviteNoIsExists(inviteNo, deptCode);
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
	@Override
	public InviteVehicleDto queryInviteCostByInviteNo(String inviteNo) {
		return waybillManagerService.queryInviteCostByInviteNo(inviteNo);
	}

    /**
     * 弃货查询
     * @author 043260-foss-suyujun
     * @date 2012-12-15
     * @param condition
     * @return List<AbandonedGoodsDto>
     */
	@Override
	public List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(
			AbandonedGoodsCondition condition) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		return waybillManagerService.queryAbandonedGoodsDtoList(condition,currentInfo);
	}
	
    /**
     * 
     * <p>查询走货路径</p> 
     * @author foss-sunrui
     * @date 2012-12-17 下午6:20:08
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
		return waybillManagerService.queryFreightRouteBySourceTarget(
				sourceCode, targetCode, productCode, time);
	}
	
	/**
	 * 
	 * 获取库区信息
	 * @author foss-sunrui
	 * @date 2013-4-2 下午1:55:15
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryGoodsAreaByArriveRegionCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public GoodsAreaEntity queryGoodsAreaByArriveRegionCode(String sourceCode, String targetCode, String productCode) {
		return waybillManagerService.queryGoodsAreaByArriveRegionCode(sourceCode, targetCode, productCode);
	}
	
	/**
	 * 
	 * 根据外场编码查询外场
	 * @author foss-sunrui
	 * @date 2013-5-21 下午3:13:09
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryOutfieldByCode(java.lang.String)
	 */
	@Override
	public OutfieldEntity queryOutfieldByCode(String code) {
		return waybillManagerService.queryOutfieldByCode(code);
	}

    /**
	 * 
	 * <p>通过组织机构编码查组织机构</p> 
	 * @author foss-sunrui
	 * @date 2012-12-17 下午7:33:20
	 * @param code
	 * @return
	 * @see
	 */
	@Override
	public OrgAdministrativeInfoEntity queryOrgInfoByCode(String code) {
		return waybillManagerService.queryOrgInfoByCode(code);
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
		return waybillManagerService.queryBankByCode(code);
	}

	/**
	 * 
	 * <p>通过代理网点编码查询代理网点</p> 
	 * @author foss-sunrui
	 * @date 2012-12-18 上午11:56:05
	 * @param agencyBranchCode
	 * @return
	 * @see
	 */
	@Override
	public AgencyBranchOrCompanyDto queryAgencyBranchCompanyInfo(String agencyBranchCode) {
		return waybillManagerService.queryAgencyBranchCompanyInfo(agencyBranchCode);
	}

	/**
	 * 查询当前部门所在的大区
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryBigRegionOfCurrDept()
	 */
	@Override
	public OrgAdministrativeInfoEntity queryBigRegionOfCurrDept() {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		return waybillManagerService.queryBigRegionOfCurrDept(currentInfo);
	}

	/**
	 * 
	 * <p>查询出发营业部的默认外场</p> 
	 * @author foss-sunrui
	 * @date 2012-12-21 上午10:51:40
	 * @param saleCode
	 * @param productCode
	 * @param isTransSale 是否驻地营业部
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryDefaultTransCodeDept(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public String queryDefaultTransCodeDept(String saleCode,
			String productCode, boolean isTransSale) {
		// TODO Auto-generated method stub
		return waybillManagerService.queryDefaultTransCodeDept(saleCode, productCode, isTransSale);
	}
	
	/**
	 * 根据外场组织编码查驻地营业部
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public String queryDefaultSalesDeptByTransCenter(String saleCode) {
		return waybillManagerService.queryDefaultSalesDeptByTransCenter(saleCode);
	}
	
	/**
	 * 根据集中开单组编码查所属外场组织编码
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public String queryTransCenterByBillingGroupCode(
			String billingGroupCode){
		return waybillManagerService.queryTransCenterByBillingGroupCode(billingGroupCode);
	}
	
	/**
	 * 集中开单查开单组所属中转场的默认出发部门
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	@Override
	public SaleDepartmentEntity queryPickupCentralizedDeptCode(
			String billingGroupOrgCode) {
		return waybillManagerService.queryPickupCentralizedDeptCode(billingGroupOrgCode);
	}

	/**
	 * 
	 * <p>优惠券验证或使用接口</p> 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午2:12:26
	 * @param couponInfo
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#validateCoupon(com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto)
	 */
	@Override
	public CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo) {
		return crmOrderService.validateCoupon(couponInfo);
	}

	/**
	 * 
	 * <p>反使用优惠券接口</p> 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午2:14:54
	 * @param couponNumber
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#effectCouponState(java.lang.String)
	 */
	@Override
	public boolean effectCouponState(String couponNumber) {
		// TODO Auto-generated method stub
		return crmOrderService.effectCouponState(couponNumber);
	}

	/**
	 * 根据gis网点匹配条件查询网点code
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午8:35:53
	 */
	@Override
	public List<GisDepartmentDto> queryPickupOrgCodeByGis(GisPickupOrgDto dto) {
		return waybillManagerService.queryPickupOrgCodeByGis(dto);
	}

	/**
	 * 
	 * <p>判断是否驻地营业部</p> 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午4:27:45
	 * @param saleCode
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#identityTransSale(java.lang.String)
	 */
	@Override
	public boolean identityTransSale(String saleCode) {
		// TODO Auto-generated method stub
		return waybillManagerService.identityTransSale(saleCode);
	}

	
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
	@Override
	public PaginationDto queryFlightDtoListBySelectiveCondition(FlightEntity flight, int offset, int limit) throws FlightException {
		return flightService.queryFlightDtoListBySelectiveCondition(flight, offset, limit);
	}
	
	/**
	 * 通过调用GIS的特殊地址查询接口获取地址类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:52:59
	 */
	@Override
	public List<String> querySpecialAddressByGis(GisPickupOrgDto dto){
		return waybillManagerService.querySpecialAddressByGis(dto);
	}

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
	@Override
	public ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String orgCode,String config) {
		ConfigurationParamsEntity entity = configurationParamsService.
		queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
				config,orgCode);
		return entity;
	}
	
	/**
	 * 根据传入参加查询系统参数配置项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-14
	 */
	@Override
	public ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String orgCode,String config,String model) {
		ConfigurationParamsEntity entity = configurationParamsService.
		queryConfigurationParamsByOrgCode(model,
				config,orgCode);
		return entity;
	}

	/**
     * 检查订单号码是否存在
     * 
     * @param waybillNo
     * @param orgCode
     * @return boolean
     */
	@Override
	public boolean checkWaybillOrderNo(String orderNo) {
		 
		return waybillManagerService.isWayBillExsitsByOrderNo(orderNo);
	}

    /**
     * <p>根据合同编码和部门编码查询合同信息</p> 
     * @author 025000-foss-helong
     * @date 2013-2-23 上午10:35:43
     * @param bargainCode 合同编号
     * @param deptCode 部门编码
     * @return
     * @see
     */
	@Override
	public CusBargainEntity queryCusBargainByParams(String bargainCode, String deptCode) {
		return cusBargainService.queryCusBargainByParams(bargainCode, deptCode);
	}


	/**
	 * 根据运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:03:45
	 */
	public List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo) {
		return waybillManagerService.queryOtherChargeByNo(waybillNo);
	}
	
	/**
	 * 
	 * 查询系统上线日期
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-11 下午7:22:10
	 */
	public Date queryFossGoliveDate(){
		return waybillManagerService.queryFossGoliveDate();
	}


	@Override
	public List<GuiResultBillCalculateDto> queryGuiBillPrice(GuiQueryBillCalculateDto billCalculateDto) {
		// TODO Auto-generated method stub
		return waybillManagerService.queryGuiBillPrice(billCalculateDto);
	}
	
	/**
	 * 根据历史时间和组织编码查询组织信息（查询历史组织信息）
	 * 
	 * 若时间为空，则只根据组织编码查询组织信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的部门
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	@Override
	public OrgAdministrativeInfoEntity queryHisOrgInfoByCode(String code, Date billTime){
		return orgAdministrativeInfoService.queryOrgInfoByCodeAndTime(code, billTime);
	}
	
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
	@Override
	public SaleDepartmentEntity queryHisSaleDeptByCode(String code, Date billTime){
		return saleDepartmentService.querySaleDepartmentByCode(code, billTime);
	}
	
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
	@Override
     public OuterBranchEntity queryHisOuterBranchByCode(String code,Date billTime){
    	 return vehicleAgencyDeptService.queryOuterBranchByCode(code, billTime);
     }
	
	/**
	 * 根据营业部部门编码查询相关的集中开单组列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 上午11:03:01
	 */
	@Override
	public List<MapDto> queryBillingGroupListBySalesCode(String salesCode){ 
		return salesBillingGroupService.queryBillingGroupListBySalesCode(salesCode);
	}
	
	/**
	 * 根据集中开单组部门编码查询相关的营业部列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 上午11:05:36
	 */
	@Override
	public List<MapDto> querySalesListByBillingGroupCode(String billingGroupCode){
		return salesBillingGroupService.querySalesListByBillingGroupCode(billingGroupCode);
	}
	/**
	  * 
	  * @Description: 修改密码
	  * @author FOSSDP-sz
	  * @date 2013-3-29 上午11:27:47
	  * @param oldPwd
	  * @param newPwd
	  * @return 加密后的密码字符串
	  * @version V1.0
	  */
	@Override
	public void modifyPassword(String userName, String newPwd) {
		
		userService.modifyUserPwd(userName, newPwd);
	}
	
	/**
	 * 根据运单号查询ActualFreightEntity对象
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-29 下午4:42:19
	 */
	@Override
	public ActualFreightEntity queryActualFreightByNo(String waybillNo){
		return actualFreightService.queryByWaybillNo(waybillNo);
	}
	
	/**
	 * 根据封装的DTO查询运单基本和冗余信息
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 上午10:43:05
	 */
	@Override
	public List<WaybillDto> queryActualFreightAndBasic(WaybillDto waybillDto){
		return waybillManagerService.queryActualFreightAndBasicNoExpress(waybillDto);
	}
	
	
	public List<SaleDepartmentEntity> queryByDepartmentInfo(
			QueryPickupPointDto dto){
		return waybillManagerService.queryByDepartmentInfo( dto);
	}
	
	
	public List<OuterBranchEntity> queryOuterBranchs(
			QueryPickupPointDto dto){
		return waybillManagerService.queryOuterBranchs( dto);
	}
	
	/**
	 * 获得服务器时间
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 下午3:44:42
	 */
	@Override
	public Date gainServerTime(){
		Date date = new Date();
		return date;
	}
	
	/**
	 * 根据部门编码在线查询营业部信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-27 下午8:36:10
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code){
		return saleDepartmentService.querySaleDepartmentByCode(code);
	}
	
	
	
	
	/** 
	 * <p>查询营业部信息，不关联组织名称等只查询基础信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午8:49:19
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#querySaleDepartmentByCode(java.lang.String)
	 */
	public SaleDepartmentEntity querySaleDepartmentNoAttach(String code){
		return saleDepartmentService.querySaleDepartmentNoAttach(code);
	}
	
	
	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	@Override
	public OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode){
		return waybillManagerService.queryAgencyBranchInfo(agencyBranchCode);
	}
	
	/**
	 * 按标杆编码查询组织部门实体
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	public OrgAdministrativeInfoEntity queryOrgByUnifiedCode(String unifiedCode) {
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(unifiedCode);
	}
	
	
	/**
	 * 按标杆编码查询组织部门实体 简化  
	 * @author huangwei
	 * @date 2016-10-11 下午4:16:21
	 */
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeClean(String unifiedCode) {
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
	}
	
	/**
	 * 
	 * 打印标签记录日志
	 * @author 025000-FOSS-helong
	 * @date 2013-7-1
	 */
	@Override
	public void addPrintLabelInfo(GUIPrintLabelDto printLabelDto) {
		List<MutexElement> mutexes = new ArrayList<MutexElement>();	
		/**
		 * 创建运单互斥对象
		 */
		MutexElement mutexElement = new MutexElement(printLabelDto.getWaybillNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
		mutexes.add(mutexElement);
		
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
		if(!isLocked){
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		try{
			// 添加打印信息
			waybillManagerService.addPrintLabelInfo(printLabelDto);
		}finally{
			//释放锁
			businessLockService.unlock(mutexes);
		}	
	}
	
	/**
	 * 转换金额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-15
	 */
	@Override
	public BigDecimal getExchangedFee(BigDecimal rmbFee, String currencyCode,
			Date billTime) {
		return waybillManagerService.getExchangedFee(rmbFee, currencyCode, billTime);
	}
	
	/**
	 * 目标币种汇率
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-15
	 */
	@Override
	public BigDecimal getExchangedFeeRate(String currencyCode, Date billTime) {
		return waybillManagerService.getExchangedFeeRate(currencyCode, billTime);
	}
	
	/**
     * 根据条件查询营业部信息
     * @author WangQianJin
     * @date 2013-7-19 上午11:15:54
     */
	@Override
    public SaleDepartmentEntity queryDepartmentInfoByDto(QueryPickupPointDto dto2){
		return waybillManagerService.queryDepartmentInfoByDto(dto2);
	}
	
	/**
     * 根据条件查询偏线信息
     * @author WangQianJin
     * @date 2013-7-19 下午1:52:21
     */
	@Override
    public OuterBranchEntity queryOuterBranchByDto(QueryPickupPointDto dto2){
		return waybillManagerService.queryOuterBranchByDto(dto2);
	}
	
	/**
     * 
     * <p>根据类型查询有效的禁运物品</p> 
     * @author panguoyang
     * @date 2013-07-22
     * @param ProhibitedArticlesEntity
     * @return
     * @see
     */
	@Override
    public List<ProhibitedArticlesEntity> queryProhibitGoodsByType(String type) {
    	return prohibitGoodsDao.queryProhibitGoodsByType(type);
    }
	
	/**
     * 
     * <p>根据类型查询有效的禁运物品</p> 
     * @author panguoyang
     * @date 2013-07-22
     * @param ProhibitedArticlesEntity
     * @return
     * @see
     */
	@Override
	public List<ProhibitedArticlesEntity> queryAllActive() {
		// TODO Auto-generated method stub
		return prohibitGoodsDao.queryAllActive();
	}
	
	
	
	/**
     * 
     * <p>获取汽运禁运物品</p> 
     * @author panguoyang
     * @date 2013-07-22
     * @return
     * @see
     */
	@Override
	public List<ProhibitedArticlesEntity> queryProhibitGoodsForAutomobile(
			String otherType) {
		// TODO Auto-generated method stub
		return prohibitGoodsDao.queryProhibitGoodsForAutomobile(otherType);
	}
	
	

    /**
     * 
     * <p>判断是否是禁运物品</p> 
     * @author foss-panguoyang
     * @date 2013-07-22
     * @param goodsName
     * @return
     * @see
     */
	@Override
	public boolean isProhibitGoods(String goodsName) {
		// TODO Auto-generated method stub
		return prohibitGoodsDao.isProhibitGoods(goodsName);
	}
	
	/**
	 * 根据获取名称获取是否是贵重物品
	 * @author WangQianJin
	 * @date 2013-7-23 上午11:06:50
	 */
	public boolean isValuablesGoods(String goodsName){
		boolean retValue = false;
		List<DataDictionaryValueEntity> list = waybillManagerService.queryByTermsCode(WaybillConstants.VALUE_GOODS);				
		if (list.size() > 0) {
			for (DataDictionaryValueEntity entity : list) {
				if (entity.getValueName()!=null && goodsName.equals(entity.getValueName().trim())) {
					retValue = true;
					break;
				}
			}
		}
		return retValue;
	}
	
	/**
	 * 通过物品的数量，体积，保价金额判断是否属于贵重物品
	 * @author WangQianJin
	 * @date 2013-7-24 上午9:57:44
	 */
	public boolean isValueGoods(int goodsNum, String goodsVolume,String goodsValue){
		return waybillManagerService.isValueGoods(goodsNum,goodsVolume,goodsValue);
	}
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	public List<String> queryByCodeAndPickup(String code){
		return waybillManagerService.queryByCodeAndPickup(code);
	}
	
	/**
	 * 
	 * 查询网点自提区域信息
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	public List<String> queryByCodeAndDelivery(String code){
		return waybillManagerService.queryByCodeAndDelivery(code);
	}
	
	/**
	 * 查询自有网点
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	public List<BranchQueryVo> queryListByDepartment(SaleDepartmentEntity entity){
		return waybillManagerService.queryListByDepartment(entity);
	}
	
	/**
	 * 查询能开精准大票的自有网点
	 */
	public List<BranchQueryVo> queryListByDepartmentAndPro(SaleDepartmentEntity entity){
		return waybillManagerService.queryListByDepartmentAndPro(entity);
	}
	
	/**
	 * 查询汽运偏线、空运网点
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	public List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity){
		return waybillManagerService.queryListByBranch(entity);
	}
	
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
	@Override
	public List<DataDictionaryValueEntity> queryPickUpGoodsAir() {
		String termsCode = WaybillConstants.PICKUP_GOODS_AIR;
		return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
	}
	
	/**
	 * 
	 * （查询运输性质为汽运的提货方式）
	 * 
	 * @author 076234-FOSS-panguoyang
	 * @date 2013-07-24 上午09:03:19
	 * @see com.deppon.foss.module.pickup.creating.client.service.IWaybillService#queryPickUpGoodsHighWays()
	 */
	@Override
	public List<DataDictionaryValueEntity> queryPickUpGoodsHighWays() {
		String termsCode = WaybillConstants.PICKUP_GOODS_HIGHWAYS;
		return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
	}
	
	/**
	 * 通过发货客户编码查找发货客户能否开月结
	 * @author panguoyang
	 * @date 2013-7-24 下午9:10:10
	 */
	 
	@Override
	public boolean isCanPaidMethod(CusBargainVo vo) {
		CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVo(vo);
		if(cusBargain!=null){
			return true;
		}else{
			return false;
		}				
	}

    /**
     * 通过发货客户编码查找发货客户能否开月结(配合CUBC改造)
     * @author DP-FOSS zhaoyqing 343617
     * @date 2016-10-14
     */

    @Override
    public boolean isCanPaidMethodForUCBC(CusBargainVo vo) {
        CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVo(vo);
        validateCusBargain(cusBargain);
        return true;
    }
	
	
	/**
	 * 
	 * 查询空运货物类型(无用)
	 * @author 076234-FOSS-panguoyang
	 * @date 2013-07-27 下午04:27:30
	 * @return
	 */
	@Override
	public List<DataDictionaryValueEntity> queryAirGoodsType() {
		String termsCode = WaybillConstants.AIR_GOODS_TYPE;
		return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
	}
	
	 /**
     * <p>
     * 查询计费方式
     * @author 076234-FOSS-panguoyang
	 * @date 2013-07-27 下午04:27:30
	 * @return
     */
	@Override
	public List<DataDictionaryValueEntity> queryBillingWay() {
		String termsCode = WaybillConstants.BILLING_WAY;
		return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
	}
	
	/**
	 * 
	 * 查询空运货物类型
	 * @author 076234-FOSS-panguoyang
	 * @date 2013-07-27 下午04:27:30
	 * @return
	 */
	@Override
	public List<GoodsTypeEntity> findGoodsTypeByCondiction(
			GoodsTypeEntity entity) {
		return goodsTypeDao.findGoodsTypeByCondiction(entity);
	}
	
	/**
	 * 
	 * 根据部门Code和所属集中开单组查询
	 * 
	 * @author WangQianJin
	 * @date 2013-08-02
	 */
	@Override
	public List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(String code,String billingGroup){
		return waybillManagerService.querySalesBillGroupByCodeAndBillCode(code, billingGroup);
	}
	
	/**
     * 
     * <p>根据外场部门编码，查询该外场的驻地可出发营业部对象</p> 
     * @author foss-panguoyang
     * @date Apr 2, 2013 2:04:42 PM
     * @param code
     * @return
     * @see
     */
    @Override
    public SaleDepartmentEntity queryStationLeaveOrgByOutfieldCode(String code) {
		return orgAdministrativeInfoComplexService.queryStationLeaveOrgByOutfieldCode(code);
    }
    
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
	@Override
	public List<PublishPriceEntity> queryPublishPriceDetail(String startDeptNo,
			String destinationCode, Date billDate) {
		return publishPriceService.queryPublishPriceDetail(startDeptNo, destinationCode, new Date());
	}
    
	/**
	 * 根据部门名称和所属集中开单组查询
	 * @author WangQianJin
	 * @date 2013-8-11 下午3:20:19
	 */
    @Override
	public List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(String name,String billingGroup){
		return waybillManagerService.querySaleDepartmentByNameForCentralized(name, billingGroup);
	}
    /**
	 * 根据部门名称和所属集中开单组查询
	 * @param name 要进行模糊查询的部门名称
	 * @param billingGroup 当前开组编号
	 * @param waybillNo 运单号
	 * @return 收货部门实体
	 * @author 272311
	 * @date 2015.10.13
	 */
    @Override
    public List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralizedExp(String name,String billingGroup,String waybillNo){
    	return waybillManagerService.querySaleDepartmentByNameForCentralizedexp(name, billingGroup,waybillNo);
    }
    

	/**
	 * 
	 * 查询出发营业部的默认外场
	 * @author FOSS-panguoyang
	 * @date 2013-8-11 上午10:36:26
	 * @param saleCode
	 * @param productCode
	 * @return
	 */
	@Override
	public OutfieldEntity queryDefaultTransCodeDept(String saleCode,
			String productCode) {
		
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
				.querySaleDepartmentByCode(saleCode);
		if (saleDepartmentEntity == null) {
			throw new WaybillQueryException(WaybillSubmitException.OUTFIEL_SALEDEPT_NOTFOUND);
		}
		//获得驻地营业部外场
		if (saleDepartmentEntity.checkStation()) {
			return pkpoutfieldService.queryOutfieldByOrgCode(saleDepartmentEntity
					.getTransferCenter());
		} else {
			String code = "";
			if (waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, new Date())) {//快递或3.60特惠件
				code = expresslineService.queryDefaultTransCode(saleCode, productCode,
						DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
			} else {
				code = pkplineService.queryDefaultTransCode(saleCode, productCode,
						DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
			}
			//获得普通外场
			return pkpoutfieldService.queryOutfieldByOrgCode(code);
		}
	}
	

	/**
	 * 
	 * 获取集中开单组的外场
	 * 
	 * @author 025000-FOSS-panguoyang
	 * @date 2013-8-19 下午06:17:06
	 */
	@Override
	public SaleDepartmentEntity queryPickupCentralizedDeptCodeByBillTime(String billingGroupOrgCode,Date billTime){
		return waybillManagerService.queryPickupCentralizedDeptCodeByBillTime(billingGroupOrgCode,billTime);
		
	}
	@Override
	public OrgAdministrativeInfoEntity queryByCodeAndServerTime(
			String createOrgCode) {
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(createOrgCode, new Date());
	}
	@Override
	public ConfigurationParamsEntity queryByConfCode(String confCode,
			String orgCode) {
		//本部门及所有的上级部门Code
				StringBuilder orgCodeListStr= new StringBuilder("");
				//如果部门为空，则获取当前用户的部门
				if(orgCode==null || "".equals(orgCode)){
					//获取当前用户
					UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
					orgCode=user.getEmployee().getDepartment().getCode();
				}
				// 获取本部门及所有的上级部门列表
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
				if(orgList!=null && orgList.size()>0){
					for(int i=0;i<orgList.size();i++){
						OrgAdministrativeInfoEntity org=orgList.get(i);
						if(org!=null && org.getCode()!=null && !"".equals(org.getCode())){
							orgCodeListStr.append("'").append(org.getCode()).append("'").append(",");
						}				
					}
					if(orgCodeListStr.length()>0){
						orgCodeListStr=new StringBuilder(orgCodeListStr.toString().substring(0,orgCodeListStr.toString().length()-1));
					}
				}else{
					//如果查询不到上级部门，则获取当前部门和顶级部门
					orgCodeListStr.append("'").append(orgCode).append("',").append("'").append(FossConstants.ROOT_ORG_CODE).append("'");
				}		
				return sysConfigDao.queryByConfCode(confCode, orgCodeListStr.toString()); 
	}
	
	/**
	 * 通过空运总调查外场
	 * panguoyang
	 */
	@Override
	public String queryTransferCenterByAirDispatchCode(String lastLoadOrgCode) {
		// TODO Auto-generated method stub
		return pkpoutfieldService.queryTransferCenterByAirDispatchCode(lastLoadOrgCode);
	}
	
	/**
	 * 通过发货客户编码查找发货客户能否开月结-用于更改单
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	@Override
	public boolean isCanPaidMethodForRfc(CusBargainVo vo){ 
		CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoForRfc(vo);
		if(cusBargain!=null){
			return true;
		}else{
			return false;
		}
	}

    //DP-FOSS zhaoyiqing 343617 20161025 配合CUBC结算中心改造，校验合同部门和催款部门编码
    @Override
    public boolean isCanPaidMethodCUBCForRfc(CusBargainVo vo){
        CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoForRfc(vo);
        validateCusBargain(cusBargain);
        return true;
    }

    private void validateCusBargain(CusBargainEntity cusBargain) {
        if(cusBargain==null){
            throw new WaybillValidateException("该客户不能开月结!");
        }else{
            if(StringUtil.isEmpty(cusBargain.getUnifiedCode()))
                throw new WaybillValidateException("合同部门编码为空，不能开月结！");
            if(StringUtil.isEmpty(cusBargain.getHastenfunddeptCode()))
                throw new WaybillValidateException("催款部门编码为空，不能开月结！");
        }
    }

    /**
	 * 根据开单时间获取自提件信息
	 * @author WangQianJin
	 * @date 2013-08-02
	 * @param billDate
	 * @return
	 */
	@Override
	public List<MinFeePlanEntity> getMinFeePlanEntityByDate(Date billDate){
		return minFeePlanService.getMinFeePlanBySpecifiedDate(billDate);
	}
	
	/**
	 * 查询指定渠道编码和日期获取产品信息
	 * @author WangQianJin
	 * @date 2013-08-02
	 * @param channelCode
	 * @param businessDate
	 * @return
	 */
	@Override
	public List<ProductEntity> getProductOfMinFeePlanByChannelCodeAndSpecifiedDate(String channelCode, Date businessDate){
		return minFeePlanService.getProductOfMinFeePlanByChannelCodeAndSpecifiedDate(channelCode, businessDate);
	}

	/**
	 * 获取整车的保费相关信息
	 * @param billCalculateDto
	 * @return
	 */
	@Override
	public GuiResultBillCalculateDto getProductPriceDtoOfWVHAndBF(GuiQueryBillCalculateDto billCalculateDto){
		return waybillManagerService.getProductPriceDtoOfWVHAndBF(billCalculateDto);
	}
	
	@Override
	public ProductEntity getProductByCache(String productCode, Date billDate) {
		if (StringUtils.isEmpty(productCode)) {
		    return null;
		}
		//如果没有日期就用当前日期
		if (billDate == null) {
			return productDao.getProductByCache(productCode, new Date());
		}else{
			//否则就用传入的日期
			return productDao.getProductByCache(productCode, billDate);
		}
	}
	
	/**
	 * 根据产品Id查询产品信息
	 * @param productId 产品Id
	 * @return
	 * 版本	 	 用户		时间		内容
	 * 0001		zxy			20130924	新增：BUG-55905查不到产品问题，改成在线查
	 */
	@Override
	public ProductEntity selectProductByPrimaryKey(String productId) {
		if (StringUtils.isEmpty(productId)) {
		    return null;
		}
		return productDao.selectByPrimaryKey(productId);
	}
	
	
		//小件-----------
	@Resource
	private IExpressCityService expressCityService;
	@Override
	public List<GuiResultBillCalculateDto> queryGuiExpressBillPrice(GuiQueryBillCalculateDto billCalculateDto) {
		//  Auto-generated method stub
		return waybillManagerService.queryGuiExpressBillPrice(billCalculateDto);
	}


	/**
	 * 根据营业部网点编码获取该营业部所在城市的快递代理理/试点城市类型
	 */
	public ExpressCityResultDto queryExpressCityTypeByOrgCode(
			String salesDepartmentCode) {
		return expressCityService.queryExpressCityTypeByOrgCode(salesDepartmentCode);
		
	}
	

	/**
	 * 根据产品编码查询产品信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-12 上午9:56:17
	 */
	@Override
	public ProductEntity queryProductByCode(String code) { 
		return waybillManagerService.queryProductByCode(code);
	}
	
	/**
	 * 根据网点编码查询快递代理网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 上午11:47:58
	 */
	@Override
	public OuterBranchExpressEntity queryLdpAgencyDeptByCode(String deptCode){
		return waybillManagerService.queryLdpAgencyDeptByCode(deptCode);
	}
	@Override
	public WaybillDto queryWaybillByReturnBillNo(String waybillNO){
		return waybillManagerService.queryWaybillByReturnBillNo(waybillNO);
	}

	/**
	 * 查询其他费用信息
	 * @param entity	查询参数
	 * @param start		开始行
	 * @param limit		每页显示数
	 * @return
	 * 版本	 	 用户		时间		内容
	 * 0001		zxy			20130927	新增：BUG-56141 查询所有其他费用明细
	 */
	@Override
	public List<PriceEntity> findPriceEntityPagingByCondition(PriceEntity entity, int start, int limit){
		return priceEntryDao.findPriceEntityPagingByCondition(entity,start,limit);
	}
	
	/**
	 * @author WangQianJin
	 * @date 查询数据字典
	 * @return
	 */
	public List<DataDictionaryValueEntity> queryByTermsCode(String termsCode) {
		return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
	}

	/**
	 * 查询产品类型
	 * @param entity 参数
	 * @return
	 * 
	 *  版本 		用户		时间		   内容
	 *  0001		zxy			20130929		新增：BUG-56426 可以根据code id等条件查询
	 */
	@Override
	public List<ProductEntity> findProducts(ProductEntity entity) {
		return productDao.findProduct(entity);
	}
	@Override
	public LimitedWarrantyItemsEntity isInsurGoods(String goodsName) {
		// TODO Auto-generated method stub
		return pkpinsurGoodsDao.isInsurGoods(goodsName);
	}
	@Override
	public List<SaleDepartmentEntity> querySaleDeptsByBillingGroupCode(
			String billGroupCode) {
		return waybillManagerService.querySaleDeptsByBillingGroupCode(billGroupCode);
	}
	
	/**
	 * 判断是否需要拍大车直送
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-10-2 9:57:47
	 * @param weight
	 * @param volume
	 * @return
	 */
	@Override
	public boolean isVehicleDirect(String weight, String volume) {
		boolean retVal = false;
		ConfigurationParamsEntity sysConfig;
		String confCode = "";
		String weightVal;
		String volumeVal;
		if (weight != null && weight.length() > 0) {
			confCode = WaybillConstants.VEHICLE_DIRECT_WEIGHT;
			sysConfig = pkpsysConfigService.queryByConfCode(confCode, null);
			if (sysConfig != null) {
				weightVal = sysConfig.getConfValue();
				if (Double.parseDouble(weight) > Double.parseDouble(weightVal)) {
					retVal = true;
				}
			}
		}

		if (!retVal && volume != null && volume.length() > 0) {
			confCode = WaybillConstants.VEHICLE_DIRECT_VOLUME;
			sysConfig = pkpsysConfigService.queryByConfCode(confCode, null);
			if (sysConfig != null) {
				volumeVal = sysConfig.getConfValue();
				if (Double.parseDouble(volume) > Double.parseDouble(volumeVal)) {
					retVal = true;
				}
			}
		}
		return retVal;
	}
	@Override
	public boolean isCanPaidMethodExp(CusBargainVo vo) {
		CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoExp(vo);
		if(cusBargain!=null){
			return true;
		}else{
			return false;
		}	
	}
	@Override
	public boolean isCanPaidMethodForRfcExp(CusBargainVo vo) {
		CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoForRfcExp(vo);
		if(cusBargain!=null){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public WaybillExpressEntity getWaybillExpressByWaybillNo(String waybillNo) {
		return waybillExpressService.queryWaybillExpressByNo(waybillNo);
	}
	@Override
	public List<WaybillDto> queryActualFreightAndBasicExpress(
			WaybillDto waybillDto) {
		return waybillManagerService.queryActualFreightAndBasicExpress(waybillDto);
	}
	
	@Override
	public List<DataDictionaryValueEntity> queryRefundType() {
		String termsCode = WaybillConstants.REFUND_TYPE;
    	return queryByTermsCode(termsCode);
	}
	@Override
	public List<DataDictionaryValueEntity> queryPaymentMode() {
		String termsCode = WaybillConstants.PAYMENT_MODE;
		return queryByTermsCode(termsCode);
	}
	
	public OuterBranchExpressEntity  queryLdpAgencyDeptByCode(String code, String active){
		 OuterBranchExpressEntity bpe = ldpAgencyDeptService.queryLdpAgencyDeptByCode
			(code, FossConstants.YES);
		 return bpe;
	}
	
	/**
	 * 一键上报到IT服务台
	 * @author WangQianJin
	 * @return
	 */
	@Override
	public ITServiceResultDto uploadItService(List<ITServiceVo> itList){
		return uploadITService.uploadItService(itList);
	}
		
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	@Override
	public ConfigurationParamsEntity queryConfigurationParamsByEntity(String model,String config,String orgCode){
		return pkpsysConfigService.queryConfigurationParamsByEntity(model, config, orgCode);
	}
	/**
	 * 根据部门编码查询城市编码
	 * @param orgCode
	 * @return
	 */
	public String queryCityIdByOrgCode(String orgCode) {
		OrgAdministrativeInfoEntity orgadmin = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		if(orgadmin!=null){
			return orgadmin.getCityCode();
		}
		return null;
	}
	/**
	 * 根据部门编码查询组织信息
	 * dp-foss-dongjialing
	 * 225131
	 */
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String orgCode) {
		OrgAdministrativeInfoEntity orgadmin = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		return orgadmin;
	}
	/**
	 * 获取刚提交运单时的那条运单信息
	 * WangQianJin
	 * @param waybillNo
	 */
	@Override
	public WaybillEntity queryWaybillForFirst(String waybillNo){
		return waybillManagerService.queryWaybillForFirst(waybillNo);
	}
	
	/**
	 * 查询偏线公布价信息
	 * @author WangQianJin
	 * @param queryBillCacilateDto
	 * @return
	 */
	@Override
    public List<PublishPriceEntity> queryPublishPriceDetailForPX(QueryBillCacilateDto queryBillCacilateDto){
    	return publishPriceService.queryPublishPriceDetailForPX(queryBillCacilateDto);
    }
    
    /**
	 * 根据运单号查询是否有运单状态信息 
	 * @author WangQianJin 
	 * @param waybillTransactionEntity 
	 * @return
	 */
    @Override
	public boolean isExistWaybillTransaction(String waybillNo){ 
		return waybillManagerService.isExistWaybillTransaction(waybillNo); 
	}
    
	/** 
	 * 查询是否为第一票运单信息
	 * MANA-257接货费优化
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 上午11:07:04
	 */
	@Override
	public boolean isFirstDeliveryWaybill(WaybillEntity entity) {
		return waybillManagerService.isFirstDeliveryWaybill(entity);
	} 
	
	/**
	 * 查询是否存在活动
	 * @param deptCode 收货部门
	 * @param billDate 开单时间
	 * @return
	 */
	@Override
	public boolean isExistSpecialOffer(String deptCode,Date billDate) {
		Long count = cityMarketPlanService.countMarketPlanEntity(deptCode, billDate);
		if(count>0){
			return true;
		}
		return false;
	}
	@Override
	public List<CityMarketPlanEntity> searchCityMarketPlanEntityList(
			String orgCode, Date billDate) {
		return cityMarketPlanService.searchCityMarketPlanEntityList(orgCode, billDate);
	}
	/**
	 * 通过大礼包编码、业务时间、查询大礼包信息entity
	 * @param cityMarketPlanCode
	 * @param billDate
	 * @return
	 */
	@Override
	public CityMarketPlanEntity getCityMarketPlanEntityCode(String code,String deptCode,
			Date date) {
		return cityMarketPlanService.getCityMarketPlanEntityCode(code, deptCode,date);
	}	
	/**
	 * 精确查询行政区域
	 * @param code
	 * @return
	 * 版本   		时间			用户			内容
	 * 001		20140428    zxy			新增:MANA-2018
	 */
	@Override
	public AdministrativeRegionsEntity queryAdministrativeRegionsByCode(String code) {
		if (null == code) {
			return null;
		}
		AdministrativeRegionsEntity entity = administrativeRegionsService.queryAdministrativeRegionsByCode(code);
		return entity;
	}
	
	/**
     * 
     * 获取空运预计出发时间
	 * 版本   		时间			用户			内容
	 * 001		20140428    zxy			新增:MANA-2018
	 */
	@Override
	public Date getAirLeaveTime(String orgCode , Date flightTime , String flightNumberType)
	{
		if(DictionaryValueConstants.FLIGHT_TYPE_MORNING.equals(flightNumberType))
		{
			ConfigurationParamsEntity pickupThreshold = configurationParamsService.queryConfigurationParamsByOrgCode(
			DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
			ConfigurationParamsConstants.TFR_PARM__MORNING_FLIGHT_TIME,orgCode);
			if(null == pickupThreshold){
				throw new WaybillValidateException("查询系统配置失败，没有找到["+DictionaryConstants.SYSTEM_CONFIG_PARM__TFR__DESC+"]中的["+ConfigurationParamsConstants.TFR_PARM__MORNING_FLIGHT_TIME+"]配置项");
			}
			String time = pickupThreshold.getConfValue();
			//使用日历类 
			Calendar cal=Calendar.getInstance();
			cal.setTime(flightTime);
			//获得时
			int hour = cal.get(cal.HOUR_OF_DAY);
			//获得分
			int minute = cal.get(cal.MINUTE);
			String hourMinute = hour + ":" + minute;
			
			if(hourMinute.compareTo(time) > 0)
			{
				Date currentDate = new Date();
				Calendar current=Calendar.getInstance();
				current.setTime(currentDate);
				int day = current.get(current.DAY_OF_MONTH);
				current.set(current.DAY_OF_MONTH, day+1);
				current.set(current.HOUR_OF_DAY, hour);
				current.set(current.MINUTE, minute);
				current.set(current.SECOND, 0);
				flightTime = current.getTime();
			}else
			{
				Date currentDate = new Date();
				Calendar current=Calendar.getInstance();
				current.setTime(currentDate);
				current.set(current.HOUR_OF_DAY, hour);
				current.set(current.MINUTE, minute);
				current.set(current.SECOND, 0);
				flightTime = current.getTime();
			}
			return flightTime;
		}else if(DictionaryValueConstants.FLIGHT_TYPE_MIDDLE.equals(flightNumberType))
		{
			ConfigurationParamsEntity pickupThreshold = configurationParamsService.queryConfigurationParamsByOrgCode(
			DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
			ConfigurationParamsConstants.TFR_PARM__MIDDEL_FLIGHT_TIME,
			orgCode);
			if(null == pickupThreshold){
				throw new WaybillValidateException("查询系统配置失败，没有找到["+DictionaryConstants.SYSTEM_CONFIG_PARM__TFR__DESC+"]中的["+ConfigurationParamsConstants.TFR_PARM__MIDDEL_FLIGHT_TIME+"]配置项");
			}
			String time = pickupThreshold.getConfValue();
			//使用日历类 
			Calendar cal=Calendar.getInstance();
			cal.setTime(flightTime);
			//获得时
			int hour = cal.get(cal.HOUR_OF_DAY);
			//获得分
			int minute = cal.get(cal.MINUTE);
			String hourMinute = hour + ":" + minute;
			
			if(hourMinute.compareTo(time) > 0)
			{
				Date currentDate = new Date();
				Calendar current=Calendar.getInstance();
				current.setTime(currentDate);
				int day = current.get(current.DAY_OF_MONTH);
				current.set(current.DAY_OF_MONTH, day+1);
				current.set(current.HOUR_OF_DAY, hour);
				current.set(current.MINUTE, minute);
				current.set(current.SECOND, 0);
				flightTime = current.getTime();
			}else
			{
				Date currentDate = new Date();
				Calendar current=Calendar.getInstance();
				current.setTime(currentDate);
				current.set(current.HOUR_OF_DAY, hour);
				current.set(current.MINUTE, minute);
				current.set(current.SECOND, 0);
				flightTime = current.getTime();
			}
			return flightTime;
		}else
		{
			//使用日历类 
			Calendar cal=Calendar.getInstance();
			cal.setTime(flightTime);
			//获得时
			int hour = cal.get(cal.HOUR_OF_DAY);
			//获得分
			int minute = cal.get(cal.MINUTE);
			
			Date currentDate = new Date();
			Calendar current=Calendar.getInstance();
			current.setTime(currentDate);
			current.set(current.HOUR_OF_DAY, hour);
			current.set(current.MINUTE, minute);
			current.set(current.SECOND, 0);
			flightTime = current.getTime();
			return flightTime;
		}
		
	}

	/** 
	 * 查询该发货客户当天是否收取过接货费
	 * @author WangQianJin
	 * @date 2014-05-04
	 */
	@Override
	public boolean queryIsGetPickFeeByCust(WaybillEntity entity){
		return waybillManagerService.queryIsGetPickFeeByCust(entity);
	}
	
	/**
	 * 获取CRM营销活动信息
	 * @创建时间 2014-4-16 下午7:46:54   
	 * @创建人： WangQianJin
	 */
	@Override
	public CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo pdaParamDto){
		return waybillManagerService.getActiveInfoList(pdaParamDto);
	}
	
	/**
	 * 获取营销活动折扣信息
	 * @创建时间 2014-4-22 上午10:51:48   
	 * @创建人： WangQianJin
	 */
	@Override
	public MarkActivitiesQueryConditionDto getActiveDiscountInfo(MarkActivitiesQueryConditionDto entity){
		return waybillManagerService.getActiveDiscountInfo(entity);
	}
	
	/**
	 * 根据客户编码获取客户信息
	 * @创建时间 2014-4-22 下午1:24:08   
	 * @创建人： WangQianJin
	 */
	@Override
	public CustomerDto queryCustInfoByCode(String custCode){
		return waybillManagerService.queryCustInfoByCode(custCode);
	}
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	@Override
	public WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo){
		return waybillManagerService.queryActiveInfoByNoAndType(waybillNo);
	}
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	@Override
	public WaybillDisDtlPendingEntity queryPendingActiveInfoByNoAndType(String waybillNo){
		return waybillManagerService.queryPendingActiveInfoByNoAndType(waybillNo);
	}
	
	/**
	 * 根据活动编码获取活动信息
	 * @创建时间 2014-6-12 上午10:09:51   
	 * @创建人： WangQianJin
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode){
		return waybillManagerService.queryMarkActivitiesByCode(activityCode);
	}

	/** 
	 * 查询是否为第一票运单信息
	 * @author 076234-foss-PanGuoYang
	 * @date 2014-4-28 上午11:07:04
	 */
	@Override
	public CarloadPricePlanDto queryByConfCode(Date date, String invoceType,
			String code) {
		//如果部门为空，则获取当前用户的部门
		if(code==null || "".equals(code)){
			//获取当前用户
			UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
			code=user.getEmployee().getDepartment().getCode();
		}
		// 获取本部门及所有的上级部门列表
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(code);
		CarloadPricePlanDto carloadDto=null;
		for(OrgAdministrativeInfoEntity org:orgList){
			  carloadDto = sysConfigDao.queryByConfCode(date,invoceType,org.getCode());
			if(null!=carloadDto){
				break;
			}
		}		
		return carloadDto;

	}
	/**
	 * 查询偏线时效方案
	 */
	@Override
	public boolean identityOuterEffectivePlan(String departDeptCode,
			String arriveDetpCode, String productCode, Date createTime) {
		return waybillManagerService.identityOuterEffectivePlan(departDeptCode, 
				arriveDetpCode, productCode, createTime);
	}
	
	/**
     * 根据部门编码与运输性质获取信息数量
     * @param entity
     * @author WangQianJin
     * @return
     */
	@Override
    public int queryCountByCodeAndProduct(SalesProductEntity entity){
		return waybillManagerService.queryCountByCodeAndProduct(entity);
	}
    
	@Override
	public List<PublishPriceEntity> queryPublishPriceDetailForSalesAndPx(
			String originalOrgCode, String destinationOrgCode) {
		return publishPriceService.queryPublishPriceDetailForSalesAndPx(originalOrgCode, destinationOrgCode);
	}
	@Override
	public String queryWaybillTypeByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		String waybillType = null;
		DispatchOrderEntity entity = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
		if(entity !=null){
			waybillType = entity.getWaybillType();
		}
		return waybillType;
	}
	/**
	 * 根据订单号查询serviceType
	 */
	@Override
	public String queryWaybillServiceTypeByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		String serviceType = null;
		DispatchOrderEntity entity = dispatchOrderEntityDao.queryOrderByOrderNo(orderNo);
		if(entity !=null){
			serviceType = entity.getServiceType();
		}
		return serviceType;
	}
	@Override
	public ConfigurationParamsEntity getConfig(String model, String config,
			String orgCode) {
		return pkpsysConfigService.queryConfigurationParamsByEntity( model,  config,
				 orgCode);
	}
/*	@Override
	public byte[] queryImage(String waybillNo) {
		
		WaybillPictureEntity waybillPictureEntity  = waybillPictureDao.queryWaybillPictureByWaybillNo(waybillNo);
		String filePath = waybillPictureEntity.getFilePath();
		String pictureUrl = PropertiesUtil.getKeyValue(WaybillConstants.PICTURE_WAYBILL_ADDRESS);
		try {  
		    URL url = new URL(pictureUrl+"filePath"+filePath);  
	        URLConnection urlConnection = url.openConnection();  
	        ObjectInputStream inputStream = new ObjectInputStream(urlConnection.getInputStream());  
	        Object obj = inputStream.readObject();  
	        if(obj instanceof String){
	        	log.error("图片不存在  file=" + filePath);
                return null; 
	        }
	        if(obj instanceof byte[]){
	        	return (byte[]) obj;
	        }
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
		return null;
		
	}*/
	 
	/**
	 * 根据waybillId查询图片
	 * queryImageByWaybillId: <br/>
	 * 
	 * Date:2014-10-20下午1:44:16
	 * @author 157229-zxy
	 * @param waybillNoId
	 * @return
	 * @since JDK 1.6
	 */
/*	@Override
	public byte[] queryImageByWaybillId(String waybillNoId) {
		String pictureWaybillPath = PropertiesUtil.getKeyValue(WaybillConstants.PICTURE_WAYBILL_DIR);
		URL img;
		InputStream in = null;
		ByteArrayOutputStream os = null;
		try {
			WaybillPictureEntity waybillPictureEntity  = waybillPictureDao.queryWaybillPictureById(waybillNoId);
//			img = new  URL("file://f:/aaa.png");
//			in = img.openStream();
			String filePath = pictureWaybillPath + waybillPictureEntity.getFilePath();
			File picFile = new File(filePath);
			if(!picFile.exists()){
				log.error("图片不存在  file=" + filePath);
				return null;
			}
			BufferedImage image = ImageIO.read(picFile);
			os = new ByteArrayOutputStream();  
			ImageIO.write(image, "png", os);  
			in = new ByteArrayInputStream(os.toByteArray());  
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return os.toByteArray();
		   
	}*/
	
	/**
	 * 定价优化项目
	 * 
	 * 根据优惠券编码获取区域线路限制
	 * 
	 * @author Foss-206860
	 */
	@Override
	public PendingSendCouponLogEntity queryLineAreaByNum(String couponNum) {
		return pendingSendCouponLogService.queryLineAreaByNum(couponNum);
	}
	/**
	 * 图片开单查询
	 * @param condiction
	 * @return
	 */
	@Override
	public List<SearchPictureVo> searchPictureWaybillByCondiction(
			SearchPictureWaybillCondiction condiction) {
		String outsideName="外请车司机";
		List<SearchPictureVo>  searchPictureVos = waybillPictureDao.searchPictureWaybillByCondiction(condiction);
		if(searchPictureVos!=null && !searchPictureVos.isEmpty()){
			List<WaybillPicturePushLogDto> allWpols = new ArrayList<WaybillPicturePushLogDto>();
			//获取外场补录人和补录部门
			List<String> wayBillNoList = new ArrayList<String>();
			for (SearchPictureVo sp : searchPictureVos) {
				//根据需求要求，外请车的司机名称统一显示成‘外请车司机’---邹胜利
				if(sp.getDriverWorkNo().equals("000000")){
					sp.setDriverName(outsideName);
				}
				String wayBillNo = sp.getWaybillNo();
				wayBillNoList.add(wayBillNo);
				if(wayBillNoList.size() >= NUM998){
					//查询运单外场补录信息
					allWpols.addAll(waybillPictureDao.queryOuterWaybillPicturePushLog(wayBillNoList));
					wayBillNoList.clear();
				}
			}
			if(wayBillNoList.size()>0){
				//查询运单外场补录信息
				allWpols.addAll(waybillPictureDao.queryOuterWaybillPicturePushLog(wayBillNoList));
			}
			if(!allWpols.isEmpty()){
				//将外场补录信息设置到运单信息中
				Map<String, WaybillPicturePushLogDto> wayMap = new HashMap<String, WaybillPicturePushLogDto>();
				for (WaybillPicturePushLogDto wp : allWpols) {
					wayMap.put(wp.getWaybillNo(), wp);
				}
				for (SearchPictureVo spv : searchPictureVos) {
					String no = spv.getWaybillNo();
					WaybillPicturePushLogDto wppl= wayMap.get(no);
					if(wppl != null){
						spv.setOuterOptCode(wppl.getOperatorCode());
						spv.setOuterName(wppl.getOrgName());
					}
				}
			}
		}
		return searchPictureVos;
	}
	
	/**
	 * 按运单号查询图片开单
	 * @param waybillNo
	 * @return
	 */
	@Override
	public WaybillPictureEntity queryWaybillPictureByWaybillNo(String waybillNo) {
		return waybillPictureDao.queryWaybillPictureByWaybillNo(waybillNo);
	}
	
	/**
	 * 按运单号ID查询图片开单
	 * @param waybillNo
	 * @return
	 */
	@Override
	public WaybillPictureEntity queryWaybillPictureById(String waybillId) {
		return waybillPictureDao.queryWaybillPictureById(waybillId);
	}
	
	/**
	 * 根据司机工号判断是否完成接送货
	 * @param driverCode
	 * @return
	 */
	public boolean querySumGoodsInfoByParams(String driverCode) {
		StayHandoverEntity entity = stayHandoverDao.querySumGoodsInfoByParams(driverCode);
		if (entity == null)
			return false;
		return true;
	}
	public int saveWaybillPushMessage(WaybillPushMessageEntity e){
		return waybillPictureDao.saveWaybillPushMessage(e);
	}
	
	/**
	 * 是否打印了标签
	 * @param waybillNo
	 * @return
	 */
	public boolean isPrintLabel(String waybillNo) {
		List<GoodsLabelPrintDto> dtos = printLabelService.queryLabelPrintByWaybillNo(waybillNo);
		if (dtos == null || dtos.size() <= 0) {
			return false;
		}
		return true;
	}
	/**
	 * 图片开单推送信息
	 * @author 076234 PanGuoYang
	 */
	@Override
	public  void pushMessage(int pushType,int messageType,int deviceType,String deviceId,String title,String description ,String value){
		pushService.pushMessage(pushType, messageType, deviceType, deviceId, title, description, value);
	}
	
	/**
	 * 通过运单号查询费用
	 * @param waybillNo
	 * @return
	 */
	public List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo) {
		return waybillChargeDtlService.queryChargeDtlEntityByNo(waybillNo);
	}
	
	/**
	 * 图片开单发送短信
	 * @param notificationEntity
	 */
	public void sendDriverCodeSms(NotificationEntity2 notificationEntity) {
		sendSmsVoiceService.sendDriverCodeSms(notificationEntity);
	}
	@Override
	public boolean checkWaybillPendingOrderNo(String orderNo) {
		return waybillManagerService.isWayBillPendingExsitsByOrderNo(orderNo);
	}
	
	/**
	 * 通过原单编号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	@Override
	public WaybillExpressEntity queryWaybillByOriginalWaybillNo(
			String originalWaybillNo, String returnType) {
		return waybillExpressService.queryWaybillByOriginalWaybillNo(originalWaybillNo, returnType);
	}
	
	@Override
	public List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(
			String originalWaybillNo) {
		return waybillExpressService.queryWaybillByOriginalWaybillNo(originalWaybillNo);
	}
	
	/**
	 * 通过运单号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	@Override
	public WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo,
			String returnType) {
		return waybillExpressService.queryWaybillByWaybillNo(waybillNo, returnType);
	}
	
	/** 
	 * 验证运单签收变更、反签收申请情况
	 */
	@Override
	public void checkWayBillRfcStatus(String originalWaybillNo) {
		signChangeService.checkWayBillRfcStatus(originalWaybillNo);
		
	}
	/**
	 * 查询运单出发更改状态
	 */
	@Override
	public void checkWayBillChange(String id) {
		waybillExpressService.checkWayBillChange(id);
	}
	/**
	 * 查询运单库存状态
	 */
	@Override
	public List<StockEntity> queryStockByWaybillNo(String originalWaybillNo) {
		WaybillStockEntity waybillStockEntity =new WaybillStockEntity();
		waybillStockEntity.setWaybillNO(originalWaybillNo);
		return stockService.queryStockByWaybillNo(waybillStockEntity);
	}
	/**
	 * 是否签收
	 */
	@Override
	public boolean queryWaybillSignResultByWaybillNo(String waybillNo) {
		WaybillSignResultEntity result = new WaybillSignResultEntity();
		result.setWaybillNo(waybillNo);
		result.setActive(FossConstants.ACTIVE);
		result=waybillSignResultService.queryWaybillSignResultByWaybillNo(result);
		if(result!=null){
			return true;
		}
		return false;
		
	}

	/**
	 * 
	 * 提交申请到CRM.
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#submitReturnGoodsApplyToCRM(com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto)
	 */
	@Override
	@Transactional
	public boolean submitReturnGoodsApplyToCRM(
			CrmReturnedGoodsDto crmReturnedGoodsDto) {
		log.info("转寄退回件 工单上报 服务端开始.....") ;
		long begin = System.nanoTime();
		
		// 设置地址
		String address = PropertiesUtil
				.getKeyValue(WaybillConstants.EXP_RETURNGOODS_ADDRESS);
		WebClient webClient = WebClient.create(address);
		log.info("CRM接口地址："+address);
		// 设置请求的类型
		webClient.type(MediaType.APPLICATION_JSON);
		webClient.accept(MediaType.APPLICATION_JSON);
		// 发送的内容
		String body = JSONObject.toJSONString(crmReturnedGoodsDto);
		String content = "{\"returnBill\":" + body + "}";
		log.info("转寄退回件 工单上报 开始发送 发送内容 content："+content) ;
		
		// 发送返货申请
		javax.ws.rs.core.Response response = webClient.post(content);
		long endToCrm = System.nanoTime();
		log.info("转寄退回件 工单上报 发送完成 耗时："+((endToCrm-begin)/(NumberConstants.NUMBER_1000*NumberConstants.NUMBER_1000))) ;
		long beginToFoss = 0 ;
		try {
			// 序列化返回的结果
			String result = IOUtils
					.toString((InputStream) response.getEntity());
			log.info("转寄退回件 工单上报 发送完成 返回内容  result："+result) ;
			// 取出状态码
			ExpReturnGoodsApplyResultDto returnStatus = JSONObject.parseObject(
					result, ExpReturnGoodsApplyResultDto.class);
			int status = returnStatus.getStatusCode();
			// 如果是1 则创建成功
			if (1 == status) {
				//向FOSS综合插入
				ReturnGoodsRequestEntity model = new ReturnGoodsRequestEntity();
				BeanUtils.copyProperties(crmReturnedGoodsDto, model);
				try {
					beginToFoss = System.nanoTime() ;
					returnGoodsRequestEntityService.addReturnGoodsRequestEntity(model);
				} catch (Exception e) {
					e.printStackTrace();
					log.info("CRM接口地址："+e.getMessage());
					return false;
				}finally{
					log.info("转寄退回件 工单上报 推送信息至综合完成 "+crmReturnedGoodsDto.getOriWaybill()+" 耗时："+((System.nanoTime()-beginToFoss)/(NumberConstants.NUMBER_1000*NumberConstants.NUMBER_1000))) ;
				}
				return true;	
			}			
		} catch (IOException e) {
			e.printStackTrace();
			log.info("CRM接口地址："+e.getMessage());
			return false;
		}finally{
			log.info("转寄退回件 工单上报 信息推送至FOSS综合 "+crmReturnedGoodsDto.getOriWaybill()+" 总耗时："+((System.nanoTime()-endToCrm)/(NumberConstants.NUMBER_1000*NumberConstants.NUMBER_1000))) ;
			log.info("转寄退回件 工单上报 服务端完成 总耗时："+((System.nanoTime()-begin)/(NumberConstants.NUMBER_1000*NumberConstants.NUMBER_1000))) ;
		}
		return false;

	}
	/**
	 * 是否存在付款信息
	 */
	@Override
	public boolean isExistRepayment(String originalWaybillNo) {
		return repaymentService.isExistRepayment(originalWaybillNo);
	}
	/**
	 * 网上支付是否付款
	 */
	@Override
	public List<String> unpaidOnlinePay(List<String> waybillNos) {
		// TODO Auto-generated method stub
		if(waybillNos==null){
			return null;
		}
		RequestDO requestDO = new RequestDO();
		requestDO.setServiceCode(WaybillHessianRemoting.class.getName()+".unpaidOnlinePay");
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		String[] billNos = new String[CollectionUtils.isEmpty(waybillNos)?0:waybillNos.size()];
		for(int i=0;i<waybillNos.size();i++){
			billNos[i] = waybillNos.get(i);
		}
		requestDO.setSourceBillNos(billNos);
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		VestResponse response = new VestResponse();
		try {
			response = grayScaleService.vestByCustomer(requestDO);
		} catch (Exception e1) {
		    throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
		}
		//如果灰度接口异常，那么为null
		List<VestBatchResult> vestBatchResults = response.getVestBatchResult();
		VestBatchResult vestResult =  CollectionUtils.isEmpty(vestBatchResults)?new VestBatchResult():vestBatchResults.get(0);
		if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
			return takingService.unpaidOnlinePay(waybillNos);
		}
		return null;
	}
	@Override
	public BigDecimal queryFinanceSign(String originalWaybillNo) {
		FinancialDto dto =repaymentService.queryFinanceSign(originalWaybillNo);
		if(dto!=null){
			return dto.getReceiveablePayAmoout();
		}
		return null;
	}
	/**
	 * 返货管理——查询返货申请工单
	 */
	@Override
	public List<CrmReturnedGoodsDto> queryReturnGoodsWorkOrder(
			CrmReturnedGoodsDto vo) {
		// TODO Auto-generated method stub
		ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
		BeanUtils.copyProperties(vo, condition);
		List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService
				.queryReturnGoodsRequestEntityByCondition(condition);
		List<CrmReturnedGoodsDto> rs = new ArrayList<CrmReturnedGoodsDto>();
		CrmReturnedGoodsDto temp = null;
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				temp = new CrmReturnedGoodsDto();
				BeanUtils.copyProperties(list.get(i), temp);
				rs.add(temp);
			}
		}
		return rs;
	}

	@Override
	public List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNo(
			String waybillNo, String type) {
		// TODO Auto-generated method stub
		return printAgentWaybillService.queryRecordByWaybillNo(waybillNo,type);
	}

	
	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	public List<ExpBatchChangeWeightDto> queryExpBatchChangeWeightResult(ExpBatchChangeWeightQueryDto dto){
		return waybillRfcService.queryExpBatchChangeWeightResultByCondition(dto);
	}
	
	/**
	 * 
	 * 批量执行重量更改（快递）
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
	 * 
	 */
	public void commitBatchChangeWeight(List<ExpBatchChangeWeightDto> expBatchChangeWeightDtoList){
		List<MutexElement> mutexes = new ArrayList<MutexElement>();	
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		/**
		 *	对excel单号处理，获取纯母单的信息，子单重量体积累加到母单
		 */
		List<ExpBatchChangeWeightDto> parentWaybillExpBatchChangeWeightDtoList = getParentWaybillExpBatchChangeWeightDtoList(expBatchChangeWeightDtoList);
		/**
		 * 创建运单互斥对象
		 */
		for(ExpBatchChangeWeightDto expBatchChangeWeightDto:parentWaybillExpBatchChangeWeightDtoList){
			MutexElement mutexElement = new MutexElement(expBatchChangeWeightDto.getWaybillNo(), 
					WaybillConstants.WAYBILL_RFC, MutexElementType.WAYBILL_NO);//创建对象
			mutexes.add(mutexElement);
			
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
			if(!isLocked){
				throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			}
			try{
				// 执行重量体积更改
				waybillRfcService.batchChangeWeight(expBatchChangeWeightDto,currentInfo);
			}finally{
				//释放锁
				businessLockService.unlock(mutexes);
			}	
		}
	}
	/**
	 * 批量执行重量更改（零担）
	 */
	public void commitLTLBatchChangeWeight(List<LTLEWaybillChangeWeightDto> expBatchChangeWeightDtoList){
		List<MutexElement> mutexes = new ArrayList<MutexElement>();	
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		/**
		 * 创建运单互斥对象
		 */
		for(LTLEWaybillChangeWeightDto expBatchChangeWeightDto:expBatchChangeWeightDtoList){
			MutexElement mutexElement = new MutexElement(expBatchChangeWeightDto.getWaybillNo(), 
					WaybillConstants.WAYBILL_RFC, MutexElementType.WAYBILL_NO);//创建对象
			mutexes.add(mutexElement);
			
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
			if(!isLocked){
				throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			}
			try{
				// 执行重量体积更改
				lTlEWaybillService.ltlEWaybillChangeWeight(expBatchChangeWeightDto,currentInfo);
			}finally{
				//释放锁
				businessLockService.unlock(mutexes);
			}	
		}
	}
	/**
	 * 查询导入的结果信息
	 */
	public List<LTLEWaybillChangeWeightDto> queryLTLEWaybillChangeWeightResult(LTLEWaybillChangeWeightQueryDto dto){
		return lTlEWaybillService.queryLTLEWaybillChangeWeightResult(dto);
	}
	
	
	/**
	 * 对excel单号处理，获取纯母单的信息，子单重量体积累加到母单
	 * @param expBatchChangeWeightDtoList
	 * @return
	 */
	private List<ExpBatchChangeWeightDto> getParentWaybillExpBatchChangeWeightDtoList(
			List<ExpBatchChangeWeightDto> expBatchChangeWeightDtoList) {
		List<ExpBatchChangeWeightDto> parentWaybillExpBatchChangeWeightDtoList = new ArrayList<ExpBatchChangeWeightDto>();
		Map<String,ExpBatchChangeWeightDto> expBatchChangeWeightDtoMap = new HashMap<String,ExpBatchChangeWeightDto>();
		for(int i = 0 ; i < expBatchChangeWeightDtoList.size() ; i++){
			String waybillNo = expBatchChangeWeightDtoList.get(i).getWaybillNo();
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("waybillNo",waybillNo);
			params.put("active",FossConstants.ACTIVE);
			List<WaybillRelateDetailEntity> waybillRelateDetailList = waybillRelateDetailEntityService.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
			//是子母件
			if(CollectionUtils.isNotEmpty(waybillRelateDetailList)){
				//查询当前子母件
				String parentWaybillNo = waybillRelateDetailList.get(0).getParentWaybillNo();
				params.clear();
				params.put("parentWaybillNo",parentWaybillNo);
				params.put("active",FossConstants.ACTIVE);
				List<WaybillRelateDetailEntity> allWaybillRelateDetailList = waybillRelateDetailEntityService.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
				//母件更改Dto
				ExpBatchChangeWeightDto expBatchChangeWeightDto = null;
				BigDecimal weightChanged = BigDecimal.ZERO;
				BigDecimal volumeChanged = BigDecimal.ZERO;
				BigDecimal dtoWeightChanged = BigDecimal.ZERO;
				BigDecimal dtoVolumeChanged = BigDecimal.ZERO;
				//更改子母件Dto
				ExpBatchChangeWeightDto comparedto = null;
				//需要子母件更改entity
				WaybillRelateDetailEntity waybillRelateDetailEntity = null;
				//子母件是否更改
				boolean isChange = false;
				//是否重复提交字母件单号
				if(expBatchChangeWeightDtoMap.containsKey(parentWaybillNo))
					throw new WaybillSubmitException(WaybillSubmitException.WAYBILLNO_REPEAT,new Object[]{waybillNo});
				else
					expBatchChangeWeightDto = new ExpBatchChangeWeightDto(); 
				for(int j = 0 ; j < allWaybillRelateDetailList.size() ; j++){
					isChange = false;
					waybillRelateDetailEntity = allWaybillRelateDetailList.get(j);
					for(ExpBatchChangeWeightDto comto : expBatchChangeWeightDtoList){
						if(waybillRelateDetailEntity.getWaybillNo().equals(comto.getWaybillNo())){
							//子件更改体积重量
							comparedto = comto;
							isChange = true;
							break;
						}
					}
					//子母件更改体积重量
					if(isChange){
						//更改的重量
						weightChanged = comparedto.getWeightChanged() == null ? BigDecimal.ZERO : comparedto.getWeightChanged();
						//更改的体积
						volumeChanged = comparedto.getVolumeChanged() == null ? BigDecimal.ZERO : comparedto.getVolumeChanged();
						//累加前的重量
						dtoWeightChanged = expBatchChangeWeightDto.getWeightChanged() == null ? BigDecimal.ZERO : expBatchChangeWeightDto.getWeightChanged();
						//累加前的体积
						dtoVolumeChanged = expBatchChangeWeightDto.getVolumeChanged() == null ? BigDecimal.ZERO : expBatchChangeWeightDto.getVolumeChanged();
						//更改后的重量
						waybillRelateDetailEntity.setWeight(weightChanged);
						//更改后的体积
						waybillRelateDetailEntity.setVolume(volumeChanged);
						//设置更改的子件
						waybillRelateDetailEntity.setChange(true);
						//累加后的重量
						expBatchChangeWeightDto.setWeightChanged(dtoWeightChanged.add(weightChanged));
						//累加后的体积
						expBatchChangeWeightDto.setVolumeChanged(dtoVolumeChanged.add(volumeChanged));
						//移除当前已经参加计算过的更改子母件
						expBatchChangeWeightDtoList.remove((expBatchChangeWeightDtoList.indexOf(comparedto)));
					}else{
						//子件未更改体积重量
						//子母件关系表的重量
						weightChanged = waybillRelateDetailEntity.getWeight() == null ? BigDecimal.ZERO : waybillRelateDetailEntity.getWeight();
						//子母件关系表的体积
						volumeChanged = waybillRelateDetailEntity.getVolume() == null ? BigDecimal.ZERO : waybillRelateDetailEntity.getVolume();
						//累加前的重量
						dtoWeightChanged = expBatchChangeWeightDto.getWeightChanged() == null ? BigDecimal.ZERO : expBatchChangeWeightDto.getWeightChanged();
						//累加前的体积
						dtoVolumeChanged = expBatchChangeWeightDto.getVolumeChanged() == null ? BigDecimal.ZERO : expBatchChangeWeightDto.getVolumeChanged();
						//累加后的重量
						expBatchChangeWeightDto.setWeightChanged(dtoWeightChanged.add(weightChanged));
						//累加后的体积
						expBatchChangeWeightDto.setVolumeChanged(dtoVolumeChanged.add(volumeChanged));
					}
				}
				expBatchChangeWeightDto.setWaybillNo(parentWaybillNo);
				//更改的子母件信息
				expBatchChangeWeightDto.setWaybillRelateDetailEntityList(allWaybillRelateDetailList);
				if(!expBatchChangeWeightDtoMap.containsKey(parentWaybillNo))
					expBatchChangeWeightDtoMap.put(parentWaybillNo, expBatchChangeWeightDto);
				//list索引前置一位
				i -= 1;
			}else{
				parentWaybillExpBatchChangeWeightDtoList.add(expBatchChangeWeightDtoList.get(i));
			}
		}
		//添加母件单更改后的信息
		for(ExpBatchChangeWeightDto dto : expBatchChangeWeightDtoMap.values()){
			parentWaybillExpBatchChangeWeightDtoList.add(dto);
		}
		return parentWaybillExpBatchChangeWeightDtoList;
	}
	/**
	 * 异常货不允许返货开单
	 */
	@Override
	public boolean queryExcepSignResultByWaybillNo(String originalWaybillNo) {
		return contrabandSignService.queryExcepSignResultByWaybillNo(originalWaybillNo);
	}
	
	/**
	 * 是否有外发交接单（作废和有效）
	 */
	@Override
	public boolean queryBeLdpHandOveredByWaybillNo(String originalWaybillNo) {
		// TODO Auto-generated method stub
		return handOverBillService.queryBeLdpHandOveredAllByWaybillNo(originalWaybillNo);
								   
	}
	/**
	 * 返货开单页面，根据原单号在暂存表查询运单号
	 */
	@Override
	public String getWaybillNo(String originalWaybillNo) {
		return waybillPendingService.getWaybillNo(originalWaybillNo);
	}
	
	/**
	 * DMANA-9463 整车运单未签收提示功能
	 * @author 218438-foss-zhulei
	 */
	@Override
	public List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto) {
		return todoActionService.queryWaybillNotSignDataList(toDoMsgDto);
	}
	
	@Override
	public List<EmployeeEntity> queryEmployeeExactByEntity4Selector(EmployeeEntity employeeEntity,int start,int limit) {
		return employeeDao.queryEmployeeExactByEntity4Selector(employeeEntity,start,limit);
	}
	@Override
	public BigDecimal queryDiscountFeeByEmployeeNo(String employeeNo,
			Date recevieDate) {
		// TODO Auto-generated method stub
		return waybillManagerService.queryDiscountFeeByEmployeeNo(employeeNo, recevieDate);
	}	
	@Override
	public BigDecimal queryTotalFeeByDelevyCode(String code,
			Date recevieDate) {
		// TODO Auto-generated method stub
		return waybillManagerService.queryTotalFeeByDelevyCode(code, recevieDate);
	}
	@Override
	public BigDecimal queryDiscountInfo(String waybillNo) {
		return waybillManagerService.queryDiscountInfo(waybillNo);
	}
	@Override
	public List<InempDiscountPlanEntity> queryInempDiscountPlanEntity(
			Date recevieDate) {
		// TODO Auto-generated method stub
		InempDiscountPlanEntity entity = new InempDiscountPlanEntity();
		entity.setBillTime(recevieDate);
		entity.setActive(FossConstants.ACTIVE);
		return inempDiscountPlanService.queryInempDiscountPanByCondition(entity);
				
	}
	@Override
	public List<DataDictionaryValueEntity> queryDeliveryMode() {
		String termsCode = WaybillConstants.INTERNAL_DELIVERY_TYPE;
		return queryByTermsCode(termsCode);
	}

	@Override
	public List<DataDictionaryValueEntity> queryIndustrySourceCategory4Dict() {
		String termsCode = WaybillConstants.BSE_SOURCE_CATEGORY;
		return queryByTermsCode(termsCode);
	}
	@Override
	public List<DataDictionaryValueEntity> queryFlabelleavemonth4Dict() {
		String termsCode = WaybillConstants.CRM_CUSTOMER_GROUP;
		return queryByTermsCode(termsCode);
	}

	@Override
	public void updateWaybill(WaybillEntity waybill) {
		waybillManagerService.updateWaybill(waybill);
	}
	@Override
	public void updatePengdingWaybill(WaybillPendingEntity entity) {
		waybillPendingService.updateByPrimaryKeySelective(entity);
	}
	/**
	 * 保存批量开单数据到暂存表penging中
	 * @param pendingList
	 */
	@Override
	public void savePengdingWaybill(List<WaybillPendingEntity> pendingList) {
		waybillPendingService.savePengdingWaybill(pendingList);
	}
	@Override
	public String selectWaybillNo(String waybillNo){
		return waybillPendingService.selectWaybillNo(waybillNo);
	}
	
	/**
	 * 根据时间查询pending数据
	 */
	@Override
	public List<WaybillPendingEntity> queryPending( WaybillPendingDto waybillPendingDto){
		return waybillPendingService.queryPending(waybillPendingDto);
		
	}
	@Override
	public void saveWoodenRequirePending(
			List<WoodenRequirePendingEntity> woodenRequireList) {
		 waybillPendingService.batchAddWoodenRequirePending(woodenRequireList);
		
	}
	@Override
	public void saveLabeledGood(
			List<LabeledGoodEntity> labelGoodList) {
		 waybillPendingService.batchAddLabeledGood(labelGoodList);
		
	}
	@Override
	public WoodenRequirePendingEntity queryWoodenRequireByNo(String waybillNo) {
		return waybillPendingService.queryWoodenRequireByNo(waybillNo);
		
	}
	@Override
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		return waybillPendingService.queryAllSerialByWaybillNo(waybillNo);
	}
	
	@Override
	public ProductEntity findProductByName(String productName) {
		ProductEntity productEntity = new ProductEntity();
		productEntity.setName(productName);
		productEntity.setActive(FossConstants.ACTIVE);
		productEntity.setBeginTime(new Date());
		List<ProductEntity> productEntities = productDao.findProductByName(productEntity);
		if(CollectionUtils.isNotEmpty(productEntities)) {
		    return productEntities.get(0);
		}
		return null;
	}
	@Override
	public List<DataDictionaryValueEntity> selectProductCodeDataDictValue(String termsCode){
		return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
	}
	
	
	@Override
	public String queryBillingByStore(String code) {
		return storeBillingMapService.queryBillingByStore(code);
	}
	@Override
	public void deleteWoodByWaybillNos(List<String> waybillNos) {
		waybillPendingService.deleteWoodByWaybillNos(waybillNos);
		
	}
	@Override
	public void deleteWoodByWaybillNo(String waybillNo) {
		waybillPendingService.deleteWoodByWaybillNo(waybillNo);
	}
	
	@Override
	public CustomerEntity queryCustInfoByCustomerEntity(CustomerEntity customerEntity) {
		return waybillManagerService.queryCustInfoByCustomerEntity(customerEntity);
	}
	
	/**
	 * <th>
	 * 根据部门编码,一级产品查询所有对应的三级产品
	 * </th>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-31 20:22:18
	 * @param maps
	 * @return
	 */
	public List<ProductEntity> getAllProductEntityByDeptCodeForCargoAndExpress(String deptCode, String typeCode, Date billTime) {
		//判定typeCode是否为空
		if(StringUtils.isEmpty(typeCode)){
			return null;
		}
		//对应一级产品类型
		List<String> productCodeList = new ArrayList<String>();
		//物流标示
		if(WaybillConstants.TYPE_OF_CARGO.equals(typeCode)){
			//物流一级汽运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
			//物流一级空运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
			//快递标示
		}else if(WaybillConstants.TYPE_OF_EXPRESS.equals(typeCode)){
			//快递一级汽运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("deptCode", deptCode);
		maps.put("productCodeList", productCodeList);
		maps.put("active", FossConstants.YES);
		maps.put("levels", NumberConstants.NUMBER_3);
		maps.put("billTime", billTime);
		return productDao.getAllProductEntityByDeptCodeForCargoAndExpress(maps);
	}
	
	@Override
	public boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime) {
		if(StringUtils.isEmpty(productCode)){
			return false;
		}
		return productDao.onlineDetermineIsExpressByProductCode(productCode, billTime);
	}
	
	
	
	//查询当前货物的入库记录--206860
	@Override
	public List<InOutStockEntity> queryInStockInfo(String waybillNo,
			String serialNo, String orgCode, Date createBillTime) {
		return stockService.queryInStockInfo(waybillNo, serialNo, orgCode, createBillTime);
	}
	
	//查询当前货物的交接记录--206860
	@Override
	public List<HandOverBillDetailEntity> queryHandoverBillDetailByWaybillNoAndOrgCord(String waybillNo,String orgCode) {
		return handOverBillService.queryHandoverBillDetailByWaybillNoAndOrgCord(waybillNo, orgCode);
	}
	
	//查询中转原始走货路径--206860
	@Override
	public TransportPathEntity queryTransportPath(String waybillNo) {
		return transportationPathDao.queryTransportPath(waybillNo);
	}
	//通过组织名字查询组织code--206860
	@Override
	public List<OrgAdministrativeInfoEntity> querySimpleOrgAdministrativeInfoByEntity(
			OrgAdministrativeInfoEntity entity, int start, int limit) {
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoExactByEntity(entity,start,limit);
	}
	
	//根据运单号查询变更记录--206860
	@Override
	public List<WaybillRfcEntity> queryWaybillRfcAcceptByWaybillNo(String waybillNo){
		return waybillRfcService.queryWaybillRfcAcceptByWaybillNo(waybillNo);
	}
	/**
	 * 插入转运或返货目的站记录
	 * 
	 * @author foss-206860
	 */

	@Override
	public List<WaybillRfcTranferEntity> queryWaybillRfcTransferEntity(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		return waybillRfcService.queryWaybillRfcTransferEntity(waybillRfcTranferEntity);
	}
	@Override
	public List<WaybillRfcTranferEntity> queryRfcTransferHistory(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		return waybillRfcService.queryRfcTransferHistory(waybillRfcTranferEntity);
	}

	/**
	 * 获取快递外发保价申明价值上限
	 * @author 153687-foss-fengzhang
	 * @date 2015-07-03 19:52:00
	 */
	@Override
	public ConfigurationParamsEntity queryMaxInsuranceAmount(String code){
		return configurationParamsService.queryConfigurationParamsOneByCode(code);
	}
	/**
	 * 根据Code查找数据字典
	 */
	@Override
	public DataDictionaryValueEntity queryDataDictoryValueByCode(
			String termsCode, String valueCode) {
		return waybillManagerService.queryDataDictoryValueByCode(termsCode,valueCode);
	}
	@Override
	public List<InOutStockEntity> queryInStockInfo(String waybillNo,
			Object object, String customerPickupOrgCode, Date billTime) {
		return stockService.queryInStockInfo(waybillNo, null, customerPickupOrgCode, billTime);
	}
	@Override
	public List<ProductEntity> getAllExpressOrCargoProductInfo(String typeCode) {
		//判定typeCode是否为空
		if(StringUtils.isEmpty(typeCode)){
			return null;
		}
		//对应一级产品类型
		List<String> productCodeList = new ArrayList<String>();
		//物流标示
		if(WaybillConstants.TYPE_OF_CARGO.equals(typeCode)){
			//物流一级汽运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
			//物流一级空运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
			//快递标示
		}else if(WaybillConstants.TYPE_OF_EXPRESS.equals(typeCode)){
			//快递一级汽运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
		}else if(WaybillConstants.TRANS_VEHICLE.equals(typeCode)){
			//物流一级汽运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
		}else if(WaybillConstants.AIR_FREIGHT.equals(typeCode)){
			//物流一级空运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
		}
		return productDao.getLevel3ForProductInfo(productCodeList);
	}
	
	/**
	 * 根据运单号查询最新的未受理返货工单
	 */
	@Override
	public boolean queryReturnGoodsRequestEntityByCondition(
			String waybillNo) {
		
		ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
		condition.setOriWaybill(waybillNo);
		List<ReturnGoodsRequestEntity> entitys = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByCondition(condition);
		int index = 0;
		ReturnGoodsRequestEntity entity = null;
		if(entitys!=null&&entitys.size()!=0){
			for(int i = 1;i<entitys.size();i++){
				if(entitys.get(index).getTimeReport().getTime()<
						entitys.get(i).getTimeReport().getTime()){
					index = i;
				}

			}
			entity = entitys.get(index);
			if("Y".equals(entity.getIsHandle())){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
		@Override
	public boolean checkProduct(String waybillNo, String loadOrgCode,
			Date billTime) {
		// TODO Auto-generated method stub
		return false;
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
	public List<ReturnGoodsRequestEntity> queryReturnGoodsWorkOrderTWO(
			String waybillNo) {
		ReturnGoodsRequestEntity returnGoodsRequestEntity = new ReturnGoodsRequestEntity();
		returnGoodsRequestEntity.setOriWaybill(waybillNo);
		List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService
				.queryReturnGoodsRequestEntityByCondition(returnGoodsRequestEntity);
		return list;
	}
	@Override
	public String queryLostFindGoods(String waybillNo) {
		String serialNo = WaybillConstants.SERIAL_NUMBER;
		return stockService.queryLostFindGoods(waybillNo, serialNo);
	}
	@Override
	public QmsYchExceptionReportEntity isLoseGroup(String waybillNo){
		return qmsYchService.isLoseGroup(waybillNo);
	}
	
	
	//根据运单号、流水号、部门编码、以及货区编码查询该单号是否在提供的部门库存内
	@Override
	public StockEntity queryStockEntityByNos(String orgCode, String waybillNo,
			String serialNO, String goodsAreaCode) {
		return stockService.queryStockEntityByNos(orgCode, waybillNo, serialNO, goodsAreaCode);
	}
	
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateByWaybillNo(
			String waybillNo) {
		List<String> list=new ArrayList<String>();
		list.add(waybillNo);
		return waybillRelateDetailEntityService.queryWaybillRelateDetailsByWaybillNos(list,0,0,false);
	}


	@Override
	public void addWaybillRfcTransferEntity(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		// TODO Auto-generated method stub
		waybillRfcService.addWaybillRfcTransferEntity(waybillRfcTranferEntity);
	}


	@Override
	public String checkWarningLine(String receiveOrgCode, String targetOrgCode) {
		if(StringUtils.isNotEmpty(receiveOrgCode) && StringUtils.isNotEmpty(targetOrgCode)){
			//判断是否在试点大区
//			LateCouponEntity  lateCouponEntity = lateCouponService.returnSchemeToPKP();
//			if(lateCouponEntity == null){
//				log.info("提前预警方案为空");
//				return WaybillConstants.SUCCESS;
//			}
//			//出发大区
//			List<String> startSalesDept = lateCouponEntity.getStartSalesDept();
//			//到达大区 
//			List<String>  arriveSalesDept = lateCouponEntity.getArriveSalesDept();
//			if(startSalesDept == null){
//				startSalesDept = new ArrayList<String>();
//			}
//			if(arriveSalesDept == null){
//				arriveSalesDept = new ArrayList<String>();
//			}
//			arriveSalesDept.addAll(startSalesDept);
//			//判断是否是 试点营业部
//			boolean  pilotBigArea = false;
//			//都为null取消 试点优惠券发送给 发送人
//			if(arriveSalesDept.isEmpty()){
//				pilotBigArea = true;
//			}
//			/**
//			 * 短息发送对象 start=出发 arrive=到达 all=全部
//			 */
//			String smsSent = lateCouponEntity.getSmsSent();
//			//不在试点大区
//			if(!arriveSalesDept.contains(receiveOrgCode) && 
//					!pilotBigArea &&
//					("all".equals(smsSent) || StringUtils.isEmpty(smsSent)) ){
//				log.info("提前预警:"+receiveOrgCode+"不在试点大区");
//				return WaybillConstants.SUCCESS;
//			}
			String result = earlyWarningService.searchEarlyWarningInfo(receiveOrgCode, targetOrgCode);
			if(StringUtils.isEmpty(result)){
				return WaybillConstants.SUCCESS;
			}
			//综合返回完成提示
			//result = String.format("该线路历史时效兑现率低于%s,请提醒客户时效", new Object[]{result});
			return result;
		}
		return WaybillConstants.SUCCESS;
	}	
	/**
	 * 判断是否有阶梯折扣合同
	 */
	@Override
	public List<CusLtDiscountItemDto> queryLtDiscountBackInfo(
			String customerCode, Date date) {			
			CusLtdiscountafterEntity entity = new CusLtdiscountafterEntity();
			entity.setCustNum(customerCode);
			//entity.setPreferType(WaybillConstants.FRT_GRADESREBATE);
			entity.setPreferType(WaybillConstants.AFTER_FRT_GRADESREBATE);
			List<CusLtDiscountItemDto> o =  (List<CusLtDiscountItemDto>) ltDiscountBackInfoService
					.queryCusLtdiscountafterByCondition(entity);
			return o;
			
	}
	@Override
	public String isExhibitCargo(ExhibitionKeywordEntity exhibitionKeyword) {
		return waybillManagerService.isExhibitCargo(exhibitionKeyword);
	}

/**
 * 查询特殊增值服务
 */
	@Override
	public List<DataDictionaryValueEntity> querySpecialValueAddedServiceMode() {
	// TODO Auto-generated method stub
	String termsCode = WaybillConstants.SPECIAL_VALUE_ADDED_SERVICE_TYPE;
	return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
	}
	@Override
	public List<DataDictionaryValueEntity> querySpecialPickUp() {
		// TODO Auto-generated method stub
		return pkpdataDictionaryValueDao.queryByTermsCode(WaybillConstants.SPECIAL_DELIVERY_TYPE);
	}


	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntity(String countyId) {
		// TODO Auto-generated method stub
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByDistrictCode(countyId, new Date());
	}
	@Override
	public SaleDepartmentEntity querySaleDepartmentEntity(String countyId) {
		// TODO Auto-generated method stub
		return saleDepartmentService.querySaleDepartmentByCode(countyId);
	}
	
	@Override
	public ReturnGoodsRequestEntity queryReturnGoodsRequestEntityByWayBillNo(
			String originalWaybillNo){
		return returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo(originalWaybillNo);
		
	}
	@Override
	public WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo) {
		return waybillExpressService.queryWaybillByWaybillNo(waybillNo);
	}
	/**
	 * 收银员当前密码到期提醒
	 * @return
	 */
	@Override
	public int queryLeftDaysOfPsw(){
		UserEntity user = FossUserContext.getCurrentUser();
		int leftDays=userService.queryLeftDaysOfPsw(user.getEmployee().getEmpCode(),user.getPassword());
		if(leftDays>=0 && leftDays<NumberConstants.NUMBER_5){
			return leftDays+1;
		}
		return NumberConstants.NUMBER_NEGTIVE_1;
	}
	
	/**
	 * 从综合查询代收上限值
	 * @author:280747-foss-zhuxue
	 * @date:2015/10/29
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCodeNoCache(String code) {
		 return saleDepartmentService.querySaleDepartmentByCodeNoCache(code);
	}
	@Resource
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 
	 */
	@Override
	public Boolean queryArriveSheetByWaybillNo(String waybillNo) {
		ArriveSheetEntity entity=new ArriveSheetEntity();
		entity.setWaybillNo(waybillNo);
		entity.setActive(FossConstants.ACTIVE);
		entity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
		entity.setDestroyed(FossConstants.NO);
		List<ArriveSheetEntity> arriveSheetEntitys=
				 arriveSheetManngerService.queryArriveSheetByWaybillNo(entity);
		 return CollectionUtils.isEmpty(arriveSheetEntitys)?false:true;
	}
	
	@Override
	public Map<String, String> queryOrgNameMapsByCodes(List<String> orgs) {
		return orgAdministrativeInfoService.queryOrgNameMapsByCodes(orgs);
	}
	
	@Override
	public Map<String,String> queryAgentNameMapsByAgentCode(List<String> orgs) {
		 return waybillManagerService.queryAgentNameMapsByAgentCode(orgs);
	}
	@Override
	public String queryOrderByOrderNo(String orderno) {
		String str = null;
		DispatchOrderEntity dey = dispatchOrderEntityDao.queryOrderByOrderNo(orderno);
		if(null!=dey&&null!=dey.getServiceType()){
			str=  dey.getServiceType();
		}
		return str;
	}

	@Override
	public ConfigurationParamsEntity queryConfigurationParamsOneByCode(String powerCode){
		return configurationParamsService.queryConfigurationParamsOneByCode(powerCode) ;
	}

	@Override
	public String queryConfValueByCode(String code) {
		return configurationParamsService.queryConfValueByCode(code);
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
		return waybillManagerService.queryWaybillForPrint(waybillDto);
	}

	/*@Override
	public void updateOrderRefuseState(String originalWaybillNo,
			String orderNo, String deliveryCustomerCode) {
		OrderStatusToCrmDto orderStatusToCrmDto =new OrderStatusToCrmDto();
		CurrentInfo currentInfo=FossUserContext.getCurrentInfo();
		orderStatusToCrmDto.setWaybillNo(originalWaybillNo);
		orderStatusToCrmDto.setOrderNo(orderNo);
		orderStatusToCrmDto.setOprateUserCode(currentInfo.getEmpCode());
		if(deliveryCustomerCode==SignConstants.CRM_ORDER_CUSTOMER_CODE){
		orderStatusToCrmDto.setCustomerCode(SignConstants.CRM_ORDER_CUSTOMER_CODE);
		}else{
			orderStatusToCrmDto.setCustomerCode("");
		}
		orderStatusToCrmDto.setBackInfo("返单开单");
		orderStatusToCrmDto.setOprateDeptCode(currentInfo.getCurrentDeptCode());
		waybillSignResultService.updateCrmOrderForRefuse(orderStatusToCrmDto);
	}*/
	
	//合伙人月结开单，校验资金额度 2016年1月23日 18:08:06 葛亮亮
	@Resource
	ISynPartenerMonthlyLineDeductService synPartenerMonthlyLineDeductService; 
	/**
	 * 判断运单是否在派送中
	 */
	@Override
	public boolean queryArriveSheetListDeliver(String waybillNo) {
		List<ArriveSheetEntity> arriveSheetEntities=arriveSheetManngerService.queryArriveSheetListDeliver(waybillNo);
		return org.apache.commons.collections.CollectionUtils.isEmpty(arriveSheetEntities)?false:true;
	}
	/**
	 * 根据条件获取返货工单信息
	 */
	@Override
	public List<CrmReturnedGoodsDtoResult> queryReturnGoodsWorkOrderResult(
			CrmReturnedGoodsDtoResult vo) {
				ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
				BeanUtils.copyProperties(vo, condition);
				List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService
						.queryReturnGoodsRequestEntityByCondition(condition);
				List<CrmReturnedGoodsDtoResult> rs = new ArrayList<CrmReturnedGoodsDtoResult>();
				CrmReturnedGoodsDtoResult temp = null;
				if (null != list && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						temp = new CrmReturnedGoodsDtoResult();
						BeanUtils.copyProperties(list.get(i), temp);
						rs.add(temp);
					}
				}
				return rs;
	}
	/**
	 * 
	 */
	@Override
	public List<WaybillExpressEntity> queryReturnWaybillNoInfo(String returnWaybillNo) {
		WaybillExpressEntity waybillExpress=new WaybillExpressEntity();
		waybillExpress.setWaybillNo(returnWaybillNo);
		return waybillExpressService.queryWaybillReturnByWaybillNo(waybillExpress);
	}
	
	
	/**
	 * 根据编码查询详细价格条目
	 * @param entryCode 编码
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-3-14
	 */
	@Override
	public PriceEntity queryPriceEntryByCode(String entryCode){
		if(StringUtils.isBlank(entryCode)){
			return null ;
		}
		return priceEntryDao.queryPriceEntryByCode(entryCode); 
	 }
	@Override
	public void updateOrderRefuseState(String originalWaybillNo,
				String orderNo, String deliveryCustomerCode) {
			OrderStatusToCrmDto orderStatusToCrmDto =new OrderStatusToCrmDto();
			CurrentInfo currentInfo=FossUserContext.getCurrentInfo();
			orderStatusToCrmDto.setWaybillNo(originalWaybillNo);
			orderStatusToCrmDto.setOrderNo(orderNo);
			orderStatusToCrmDto.setOprateUserCode(currentInfo.getEmpCode());
			if(SignConstants.CRM_ORDER_CUSTOMER_CODE.equals(deliveryCustomerCode)){
			orderStatusToCrmDto.setCustomerCode(SignConstants.CRM_ORDER_CUSTOMER_CODE);
			}else{
				orderStatusToCrmDto.setCustomerCode("");
			}
			orderStatusToCrmDto.setBackInfo("返单开单");
			orderStatusToCrmDto.setOprateDeptCode(currentInfo.getCurrentDeptCode());
			waybillSignResultService.updateCrmOrderForRefuse(orderStatusToCrmDto);
	}

	public SynPartenerMonthlyLineDeductResponse partenerMonthlyLineDeduct(PartenerDeductDto deductDto){
		return synPartenerMonthlyLineDeductService.partenerMonthlyLineDeduct(deductDto);
	}
	
	//合伙人预存资金开单，校验资金额度 2016年1月25日 14:32:52 葛亮亮
	@Resource
	ISynPartenerPrestoreDeductService synPartenerPrestoreDeductService; 
	
	public SynPartenerPrestoreDeductResponse partenerPrestoreDeductResponse(PartenerPrestoreDeductDto prestoreDeductDto){
		return synPartenerPrestoreDeductService.partenerPrestoreDeductResponse(prestoreDeductDto);
	}
	
	//合伙人代收货款上限额度校验 2016年1月28日 13:53:40 葛亮亮
	@Resource
	ISynPartenerCodAmountUpperLimitService synPartenerCodAmountUpperLimitService;
	
	public SynPartenerCodAmountUpperLimitResponse partenerCodAmountUpperLimitResponse(PartenerCodAmountUpperLimitDto partenerCodAmountUpperLimit)
	{	
		return synPartenerCodAmountUpperLimitService.partenerCodAmountUpperLimitResponse(partenerCodAmountUpperLimit);
	}
	
	/**
	 * 根据部门编码在线查询营业部信息 优先读取缓存 如果没有则读数据库
	 * @param code
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-2-24
	 */
	@Override
	public SaleDepartmentEntity querySimpleSaleDepartmentByCodeCache(String code){
		return saleDepartmentService.querySimpleSaleDepartmentByCodeCache(code);
	}
	
	//根据运单ID获取折前信息 2016年4月15日 14:51:53 葛亮亮
	@Override
    public PartnersWaybillEntity queryPartnersWaybillEntityById(String wayBillNoId){
		return partnersWaybillService.queryPartnerWaybillEntityByWaybillId(wayBillNoId);
	}
	
	@Override
	public Boolean queryISPICPACKAGEByWaybillNo(String waybillno) {
		// 根据运单号查询子母件关系表，判断是否子母件
		String ispicpackageByWaybillNo = null;
		Boolean flag = false;
		ispicpackageByWaybillNo = dispatchOrderEntityDao.queryISPICPACKAGEByWaybillNo(waybillno);
		//当不为null或者不为空,并且是子母件
		if ((null != ispicpackageByWaybillNo&&!ispicpackageByWaybillNo.equals(""))&&ispicpackageByWaybillNo.equals("Y")) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 根据订单号查询订单所有信息
	 */
	@Override
	public DispatchOrderChannelNumberEntity queryWaybillTypeEntityByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		DispatchOrderChannelNumberEntity entity=new DispatchOrderChannelNumberEntity();
		DispatchOrderEntity queryAllInfoByOrderNo = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
		// 判断实体类不为NUll
	    if(queryAllInfoByOrderNo!=null){
			entity.setChannelNumber(queryAllInfoByOrderNo.getChannelNumber());
			entity.setOrderSource(queryAllInfoByOrderNo.getOrderSource());
		}
		return entity;
	}

	/**
	 * 根据运单号查询打木信息
	 * 祝学
	 */
	@Override
	public WoodenRequirementsEntity queryWoodenWaybillRequirement(
			String waybillNo) {
		return woodenRequirementsDao.queryNewWoodenByNo(waybillNo);
	}
	/**
	 * 根据运输性质的code查询运输性质
	 */
	@Override
	public String getProductByCache(String code) {
		return woodenRequirementsDao.getProductByCacheCode(code);
	}

	/**
	 * 根据运单号查询免费接货字段值
	 * @author 306486
	 * @param waybillNo
	 */
	@Override
	public String queryPickupGoodsByWaybillNo(String waybillNo) {
		
		return waybillManagerService.queryPickupGoodsByWaybillNo(waybillNo);
	}
	
	/**
	 * 裹裹需求查询未核销暂存表信息
	 * @param waybillNo
	 * @return
	 */
	@Override
	public boolean greenHandWrapWriteoffService(String waybillNo){
		List<GreenHandWrapWriteoffEntity> queryGreenHandWrapByWaybillNo = greenHandWrapWriteoffService.queryGreenHandWrapByWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(queryGreenHandWrapByWaybillNo)){
			if(null != queryGreenHandWrapByWaybillNo.get(0).getAmount() 
					&& BigDecimal.ZERO.compareTo(queryGreenHandWrapByWaybillNo.get(0).getAmount()) < 0){
				return true;
			}
		}
		return false;
	}
	/**
	 * 查询零担电子运单信息
	 */
	public List<LTLEWaybillQueryResultDto> queryOmsOrderOrLabelStatusByWaybillNo(LTLEWaybillQueryResultDto ltleWaybillQueryResultDto){
		return omsOrderService.queryOmsOrderOrLabelStatusByWaybillNo(ltleWaybillQueryResultDto);
	}
	/**
	 * 根据运单号推送标签状态
	 */
	/**
	 * 通过运单号获取运单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-1 上午9:16:52
	 */
	@Override
	public WaybillEntity queryNewWaybillBasicByNo(String waybillNo) {
		return waybillManagerService.queryNewWaybillBasicByNo(waybillNo);
	}

	
	@Override
	public void pushLabelStatus(List<String> waybillNoList) {
		//调用标签推送服务
		labelPushProcessService.retryFailedJob(waybillNoList);
	}
		
	@Override
	public boolean validateWaybillNoIsCorrect(Map<String, String> map){
		return waybillManagerService.validateWaybillNoIsCorrect(map) ;
	}
	
	/**
	 * <p>根据营业部网点code查询进仓地址信息</p>
	 * @author 354805-taodongguo
	 * @date 2016-9-12 17:35:20
	 * @param depCode
	 * @return
	 */
	@Override
	public List<DepotAddressEntity> queryDepotAddressByDepCode(String depCode) {
		return depotAddressService.queryDepotAddressByDepCode(depCode) ;
	}
	
	@Override
	public List<SwiftNumberInfoDto> queryUnpackDetailsss(String waybillNo){
		return querySwiftNumberInfoService.querySwiftNumberInfo(waybillNo);
	}

	/**
	 * 查询运单打印次数
	 * 
	 * @author foss-zhuxue
	 * @date 2016-10-13 
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public int queryPrintTimesByWaybill(String waybillNo) {
		return printInfoDao.queryPrintTimesByWaybill(waybillNo);
	}
	/**
	 * 更新运单打印表中打印状态
	 * zhuxue
	 */
	@Override
	public int updateByPrimarySelective(PrintInfoEntity record) {
		return printInfoDao.updatePrintJudgeSelective(record);
	}

	/**
	 * 
	 * 查询运单是否是合伙人开单
	 * 280747
	 * @author 
	 * @date 2016-10-19 下午08:37:46
	 */
	public String queryWaybillForPrintPartner(String waybillNo) {
		return waybillManagerService.partnerWaybillForPrint(waybillNo);
	}
	
	/**
	 * 根据到达部门编码查询对接外场
	 * 280747
	 */
	public String selectqueryDeptDeptTransferForName(String code){
		return deptTransferMappingService.queryDeptDeptTransferForNameByCode(code);
	}
	
	/**
	 * 根据条件查询推送CUBC接口日志
	 */
	@Override
	public List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto) {
		return waybillManagerService.queryLogEntityByCondition(queryDto);
	}
	
	/**
	 * 重新推送运单信息至cubc
	 */
	@Override
	public void pushWaybillToCUBC(List<WaybillLogEntity> cubcLogEntitys) {
		waybillManagerService.pushWaybillToCUBC(cubcLogEntitys);
	}
	/**
	 * 合伙人计算加收费
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-1 上午10:40:30
	 * @param targetOrgCode
	 * @param billTime
	 * @param billingtype
	 * @param volume
	 * @param weight
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#caculateAddFee(java.lang.String, java.util.Date, java.lang.String, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public AddedPlanFeeCalculateDto caculateAddFee(String targetOrgCode, Date billTime,
			String billingtype, BigDecimal volume, BigDecimal weight,String createOrChange) {
		// TODO Auto-generated method stub
		return waybillManagerService.caculateAddFee(targetOrgCode, billTime, billingtype, volume, weight,createOrChange);
	}
	
	/**
	 * 查询网点是否有开装卸费权限
	 * @author 354805-taodongguo
	 * @date 2016-12-7 10:13:16
	 * @param code 装卸费开单部门
	 * @param billTime 开单时间
	 * @return canPayServiceFee 是否可开装卸费
	 */
	@Override
	public String queryCanPayServiceFeeByCodeAndTime(String code , Date billTime){
		return waybillRfcService.queryCanPayServiceFeeByCodeAndTime(code, billTime);
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
		return waybillManagerService.queryStationInfo(request);
	}
	@Override
	public List<CustomerAddressDto> queryConsignee(String mobilePhone) {
		// TODO Auto-generated method stub
		return ecsQueryService.queryConsignee(mobilePhone);
	}
	
	public CustomerCircleNewDto queryCustomerByCusCodegetByCustCode(
			String provinceCode, Date date, String active) {
		return customerCircleRelationNewService.getByCustCode(provinceCode, date, active);
	}
	@Override
	public IsCustomerCircleDto queryIsCustomerCircle(String code,Date date) {
		return customerCircleRelationNewService.isCustomerCircle(code,date);
	}

    /**
     * author 306486
     *  获取分拣开关
     */
    @Override
    public List<ConfigurationParamsEntity> queryConfigurationParamsBatchByCode(String[] str) {
        return configurationParamsService.queryConfigurationParamsBatchByCode(str);
    }
}
