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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillRfcVarificationService.java
 * 
 * FILE NAME        	: WaybillRfcVarificationService.java
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
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.file.FileManager;
import com.deppon.foss.framework.server.components.file.exception.FileAccessException;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Encoder;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContactService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISynNonfixedCustomerToCrmSerivce;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerAssociatedInformationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillReturnDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DebitForCUBCDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReturnBillProcessConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodChangeDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IReturnBillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOAErrorService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPartnersWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupInstationJobMessageService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushPartnerWaybillLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISendSmsVoiceService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillHomeImpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToCUBCService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPaymentService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcJobService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.server.service.IWaybillStockService;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.impl.GrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants.WaybillRfcChangeItemsConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ErrRequestParam;
import com.deppon.foss.module.pickup.waybill.shared.domain.ErrorMainEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LDErrSubHasWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LDErrSubNoWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.NotificationEntity2;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushPartnerWaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OAErrorReportResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OaErrorReportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLabeledGoodStockDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.OAErrorException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.util.ConvertBean;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillReturnEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyPickupbillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IMakeUpWaybillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageAssistPriceService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * (更改单的审核和受理服务)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:kevin,date:2012-11-27 下午4:58:36 </p>
 * @author foss-gengzhe
 * @date 2012-11-27 下午4:58:36
 * @since
 * @version
 * 
 * 
 * 录入货物信息SUC业务规则
 *1.	若货物为违禁品，则系统自动提示“货物为违禁品，不可开单！”；
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
 */
public class WaybillRfcVarificationService implements IWaybillRfcVarificationService{ 
	
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(WaybillRfcVarificationService.class);
	/**
	 *同意
	 */
	private static final String AGREE_OF_PROCESS_WAYBILLRFC = "同意！";
	/**
	 * - 用于表示不存在
	 */
	private static final String SYSTEM_AUTOHANDLE_WAYBILLRFC = "-";
	/**
	 * 用于表示或者
	 */
	private static final String SYMBOL_OR = "/";
	/**
	 * 重量修改条目
	 */
	private static final String STR_WEIGHT_NAME = "重量:";
	/**
	 * 体积修改条目
	 */
	private static final String STR_VOLUMN_NAME = "体积:";
	/**
	 * 运费修改条目
	 */
	private static final String STR_TRANSLATEFEE_NAME = "运费:";
	/**
	 * 用于表示前后修改链接符
	 */
	private static final String SYMBOL_CHANGE = "->";
	/**
	 * 用于表示分割符
	 */
	private static final String SYMBOL_DIVISION = ";";
	/**
	 * 是
	 */
	private static final String STR_YES = "是";
	/**
	 * 否
	 */
	private static final String STR_NO = "否";
	/**
	 * QMS上报差错类型编号
	 */
	private static final String ERR_TYPE_ID = "L201503250015";
	/**
	 * QMS上报差错文件标准id(超重)
	 */
	private static final long OVERWEIGHT_DOC_ID = 3076;
	/**
	 * QMS上报差错文件标准id(超方)
	 */
	private static final long OVERVOLUMN_DOC_ID = 3077;
	
	public static final double POINT_01 = 0.1;
	
	/**
	 * 运单更改单数据持久层接口
	 */
	private IWaybillRfcDao waybillRfcDao;
	/**
	 * 更改单受理相关查询接口
	 */
	private IWaybillRfcVarificationDao waybillRfcVarificationDao;
	/** 
	 * 更改单服务接口
	 * */
	private IWaybillRfcService waybillRfcService;
	/**
	 * 运单开单服务，供接送货调用
	 */
	private IWaybillPickupService waybillPickupService;
	/**
	 * 文件管理服务
	 */
	private FileManager fileManager;
	/**
	 * 运单管理接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 合伙人服务接口
	 */
	private IPartnersWaybillService partnersWaybillService ;
	
	@Resource
	private IWaybillInfoToPtpService waybillInfoToPtpService ;
	
	@Resource
	private IExpWaybillInfoToPtpService expWaybillInfoToPtpService ;
	
	/**
	 * 货物的入库、出库及查询库存方法
	 */
	private IStockService stockService;
	/**
	 * 货签服务接口
	 */
	private ILabeledGoodService labeledGoodService;
	/**
	 * 到达联管理接口
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 运单费用明细服务接口
	 */
	private IWaybillChargeDtlService waybillChargeDtlService;
	/**
	 *  运单折扣费用明细服务接口
	 */
	private IWaybillDisDtlService waybillDisDtlService;
	/**
	 * 付款明细服务接口
	 */
	private IWaybillPaymentService waybillPaymentService;
	/**
	 * 代打木架服务接口
	 */
	private IWoodenRequirementsService woodenRequirementsService;
	
	
	/**
	 * 待办
	 */
	private ILabeledGoodTodoDao labeledGoodTodoDao;
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 查询和操作综合待办消息接口
	 */
	private IPickupToDoMsgService pickupToDoMsgService;
	/**
	 * 运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 * 计算&调整走货路径类
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 人员 Service接口
	 */
	private IEmployeeService employeeService;
	/**
	 * Pkp 封装调用综合组提供的站内消息接口
	 */
	private IPickupInstationJobMessageService pickupInstationJobMessageService;
	/**
	 *  短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;
	
	/**
	 * 派送服务
	 */
	private IDeliverbillService deliverbillService;
	/**
	 * Gps服务
	 */
	private IGpsDeliverService gpsDeliverService;
	
	/**
	 * 中转服务
	 */
	private IHandOverBillService handOverBillService;
	/**
	 * 中转服务
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	/**
	 * Gps服务
	 */
	public void setGpsDeliverService(IGpsDeliverService gpsDeliverService) {
		this.gpsDeliverService = gpsDeliverService;
	}
	/**
	 * 调用综合的上报工单
	 */
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	/**
	 * 签收服务
	 */
	private IWaybillSignResultService waybillSignResultService;

	/**
	 * 签收单返单
	 * 提供与签收单返单相关的持久化接口
	 */
	private IReturnBillProcessDao returnBillProcessDao;
	
    private IAirQueryModifyPickupbillService airQueryModifyPickupbillService;
    
    
    private IAirWaybillService airWaybillService;
	/**
	 * 外场 Service接口
	 * 
	 * @author 026113-foss-linwensheng
	 */
	private IOutfieldService outfieldService;
	
	/**
	 * 结算的接口，用来处理更改单后，如果目的站改变，将改变前的异常变为已处理
	 */
	private IExceptionProcessService exceptionProcessService;
	
	
	public void setExceptionProcessService(
			IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}
	@Autowired
	private GrayScaleService grayScaleService;
	
	/**
	 * 待处理返券服务
	 * 
	 * @author Foss-206860
	 */
	private IPendingSendCouponService pendingSendCouponService;
	
	public void setPendingSendCouponService(
			IPendingSendCouponService pendingSendCouponService) {
		this.pendingSendCouponService = pendingSendCouponService;
	}
	
	private IWaybillHomeImpService waybillHomeImpService;
	
	public IWaybillHomeImpService getWaybillHomeImpService() {
		return waybillHomeImpService;
	}

	public void setWaybillHomeImpService(
			IWaybillHomeImpService waybillHomeImpService) {
		this.waybillHomeImpService = waybillHomeImpService;
	}
	/**
	 * 查询产品类型
	 * @author Foss-105888-Zhangxingwang
	 */
	private IProductDao productDao;
	
	@Resource
	private IOAErrorService oaErrorService;
	/**
	 * 派送交单service
	 */
	private IDeliverHandoverbillService deliverHandoverbillService;
	
	public void setDeliverHandoverbillService(IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}
	
	IRepaymentService repaymentService;
	
	
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	/**
	 * 查询产品类型
	 * @author Foss-105888-Zhangxingwang
	 */
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}


    /**
     * 获取运单Dao接口
     */
    private IWaybillDao waybillDao;
    
    
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

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}

	/**
	 * @param airQueryModifyPickupbillService the airQueryModifyPickupbillService to set
	 */
	public void setAirQueryModifyPickupbillService(
			IAirQueryModifyPickupbillService airQueryModifyPickupbillService) {
		this.airQueryModifyPickupbillService = airQueryModifyPickupbillService;
	}

	/**
	 * @param returnBillProcessDao
	 *            the returnBillProcessDao to set.
	 */
	public void setReturnBillProcessDao(IReturnBillProcessDao returnBillProcessDao) {
		this.returnBillProcessDao = returnBillProcessDao;
	}
	
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}


	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * 审核状态列表
	 */
	private static List<String> checkStatusList;
	/**
	 * 处理状态列表 
	 */
	private static List<String> dealStatusList;
	/**
	 * CRM订单数据JMS服务接口
	 */
	private ICrmOrderJMSService crmOrderJMSService;
	/**
	 *  异步代办的请求dao
	 */
	private IPendingTodoDao pendingTodoDao;

	/**
	 * 国际化信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	private IMessageBundle messageBundle; 
	
	
	/**
	 * 系统配置项服务
	 * 提供与系统配置项相关的服务接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 通知客户Service
	 */
	private INotifyCustomerService notifyCustomerService;
	
	/**
	 * 
	 */
	private IWaybillInfoToCUBCService  waybillInfoToCUBCService;
	/**
	 * 查询客户信息
	 */
	private IQueryCustomerService queryCustomerService;
	/**
	 * 在FOSS系统中创建散客服务接口
	 */
	private ISynNonfixedCustomerToCrmSerivce synNonfixedCustomerToCrmSerivce;
	/**
	 * 客户联系人信息Service接口
	 */
	private ICusContactService cusContactService;
	
	/** 
		 * “公司司机”的数据库对应数据访问Service接口
		 */
		private IOwnDriverService ownDriverService;
		
		/** 
		 * “外请车司机”的数据库对应数据访问
		 */
		private ILeasedDriverService leasedDriverService;

		public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
			this.leasedDriverService = leasedDriverService;
		}
		public void setOwnDriverService(IOwnDriverService ownDriverService) {
			this.ownDriverService = ownDriverService;
		}
	
	//合伙人折前信息推送日志
	private IPushPartnerWaybillLogService pushPartnerWaybillLogService;
	
	public void setCusContactService(ICusContactService cusContactService) {
		this.cusContactService = cusContactService;
	}
	
	public void setQueryCustomerService(IQueryCustomerService queryCustomerService) {
		this.queryCustomerService = queryCustomerService;
	}
	
	public void setSynNonfixedCustomerToCrmSerivce(
			ISynNonfixedCustomerToCrmSerivce synNonfixedCustomerToCrmSerivce) {
		this.synNonfixedCustomerToCrmSerivce = synNonfixedCustomerToCrmSerivce;
	}
	
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	/**
	 * @param configurationParamsService
	 *            the configurationParamsService to set
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		//服务接口，用于spring注入
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param messageBundle the
	 *            messageBundle to set
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}
	

	/**
	 * 
	 *   CRM 接口订单JMS服务
	 * @param crmOrderJMSService
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-12 下午2:33:25
	 */
	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	/**
	 * @param waybillInfoToCUBCService the waybillInfoToCUBCService to set
	 */
	public void setWaybillInfoToCUBCService(
			IWaybillInfoToCUBCService waybillInfoToCUBCService) {
		this.waybillInfoToCUBCService = waybillInfoToCUBCService;
	}
	/**
	 * 
	 * CRM接口
	 */
	private ICrmOrderService crmOrderService;
	
	/**
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 运单完结状态操作Service
	 */
	private IWaybillTransactionService waybillTransactionService;
	/**
	 * 
	 */
	private IPackOutService packOutService;

	private ILabeledGoodDao labeledGoodDao;
	/**
	 * 更改单修改件数和打木架数量修改详细信息冗余保存对象dao
	 */
	private ILabeledGoodChangeDao labeledGoodChangeDao;

    /**
     * 业务监控服务
     */
	private IBusinessMonitorService businessMonitorService;
	
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 订单处理服务
	 * 提供与订单处理服务相关的服务接口
	 */
	private IOrderService orderService;
	
	private IWaybillExpressService waybillExpressService;
	
	/**
	 * 运单体积变更同步接口
	 */
	private IPackageAssistPriceService packageAssistPriceService;
	//运单快递
	private IWaybillExpressDao waybillExpressDao;

	 /**
     * 偏线代理网点DAO接口.
     */
    private IVehicleAgencyDeptDao vehicleAgencyDeptDao;

    
	/**
     * 设置 偏线代理网点DAO接口.
     *
     * @param vehicleAgencyDeptDao the new 偏线代理网点DAO接口
     */
    public void setVehicleAgencyDeptDao(
	    IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
	this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
    }
	
	public IPackageAssistPriceService getPackageAssistPriceService() {
		return packageAssistPriceService;
	}

	public void setPackageAssistPriceService(
			IPackageAssistPriceService packageAssistPriceService) {
		this.packageAssistPriceService = packageAssistPriceService;
	}

	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	/**
	 * @param orderService the orderService to set
	 */
	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	
	/**
	 * @param labeledGoodDao the labeledGoodDao to set
	 */
	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}


	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @return the pendingTodoDao
	 */
	public IPendingTodoDao getPendingTodoDao() {
		return pendingTodoDao;
	}


	/**
	 * @param pendingTodoDao the pendingTodoDao to set
	 */
	public void setPendingTodoDao(IPendingTodoDao pendingTodoDao) {
		this.pendingTodoDao = pendingTodoDao;
	}


	/**
	 * @param businessMonitorService the businessMonitorService to set
	 */
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}
	

	/**
	 * @param labeledGoodChangeDao the labeledGoodChangeDao to set
	 */
	public void setLabeledGoodChangeDao(ILabeledGoodChangeDao labeledGoodChangeDao) {
		this.labeledGoodChangeDao = labeledGoodChangeDao;
	}


	/** 
	 *  运单库存
	 */
	private IWaybillStockService waybillStockService;

	/**
	 * @param waybillStockService the waybillStockService to set
	 */
	public void setWaybillStockService(IWaybillStockService waybillStockService) {
		this.waybillStockService = waybillStockService;
	}
	/**
	 * @param labeledGoodDao the labeledGoodDao to set
	 */
	
	/**
	 * @param packOutService the packOutService to set
	 */
	public void setPackOutService(IPackOutService packOutService) {
		this.packOutService = packOutService;
	}
	/**
	 * @param waybillTransactionService the waybillTransactionService to set
	 */
	public void setWaybillTransactionService(
			IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}
	/**
	 * CRM接口
	 */
	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}
	/**
	 * 数据字典
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * @param dataDictionaryValueService the dataDictionaryValueService to set
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	
	/**
	 * 配合快递100轨迹推送所需的方法
	 * @author 220125 yangxiaolong
     */
	private IWaybillTrackingsService  waybillTrackingsService;
	public void setWaybillTrackingsService(
					IWaybillTrackingsService waybillTrackingsService) {
				this.waybillTrackingsService = waybillTrackingsService;
			}
	/**
	 * 线路信息服务
	 * 提供与线路信息相关的服务接口
	 */
    private ILineService lineService;
    
    public void setLineService(ILineService lineService) {
    	this.lineService = lineService;
    }
    /**
	 * 快递综合线路服务
	 */
	private IExpressLineService expresslineService;

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}
	
	/**
	 * 初始化运单审核状态
	 * 运单处理状态
	 */
	static{
		checkStatusList = new ArrayList<String>();
		dealStatusList = new ArrayList<String>();
		checkStatusList.add(WaybillRfcConstants.PRE_ACCECPT);//待受理、审核同意
		checkStatusList.add(WaybillRfcConstants.AUDIT_DENY);//审核拒绝
		dealStatusList.add(WaybillRfcConstants.ACCECPT);//已同意
		dealStatusList.add(WaybillRfcConstants.ACCECPT_DENY);//受理拒绝
	}
	
	/**
	 * @return the saleDepartmentService
	 */
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}
	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * @param pickupInstationJobMessageService the pickupInstationJobMessageService to set
	 */
	public void setPickupInstationJobMessageService(IPickupInstationJobMessageService pickupInstationJobMessageService) {
		this.pickupInstationJobMessageService = pickupInstationJobMessageService;
	}

	/**
	 * @param notifyCustomerService : return the property notifyCustomerService.
	 */
//	public void setNotifyCustomerService(
//			INotifyCustomerService notifyCustomerService) {
//		this.notifyCustomerService = notifyCustomerService;
//	}

	/**
	 * @param calculateTransportPathService : return the property calculateTransportPathService.
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * @param sMSTempleteService : return the property sMSTempleteService.
	 */
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 设置  运单开单服务，供接送货调用
	 * @param waybillPickupService
	 */
	public void setWaybillPickService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}
	/**
	 *设置   代打木架服务接口
	 *@param woodenRequirementsService
	 */
	public void setWoodenRequirementsService(
			IWoodenRequirementsService woodenRequirementsService) {
		this.woodenRequirementsService = woodenRequirementsService;
	}
	/**
	 * 设置  运单状态服务接口
	 * @param actualFreightService
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	/**
	 * 设置  查询和操作综合待办消息接口
	 * @param pickupToDoMsgService
	 */
	public void setPickupToDoMsgService(IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}
	/**
	 * 设置   待办
	 * @param labeledGoodTodoDao
	 */
	public void setLabeledGoodTodoDao(ILabeledGoodTodoDao labeledGoodTodoDao) {
		this.labeledGoodTodoDao = labeledGoodTodoDao;
	}
	/**
	 * 设置   组织信息 Service接口
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 设置   运单费用明细服务接口
	 * @param waybillChargeDtlService
	 */
	public void setWaybillChargeDtlService(
			IWaybillChargeDtlService waybillChargeDtlService) {
		this.waybillChargeDtlService = waybillChargeDtlService;
	}
	/**
	 * 返回 到达联管理接口
	 * @return
	 */
	public IArriveSheetManngerService getArriveSheetManngerService() {
		return arriveSheetManngerService;
	}
	/**
	 * 设置   运单折扣费用明细服务接口
	 * @param waybillChargeDtlService
	 */
	public void setWaybillDisDtlService(IWaybillDisDtlService waybillDisDtlService) {
		this.waybillDisDtlService = waybillDisDtlService;
	}
	/**
	 * 设置   付款明细服务接口
	 * @param waybillPaymentService
	 */
	public void setWaybillPaymentService(IWaybillPaymentService waybillPaymentService) {
		this.waybillPaymentService = waybillPaymentService;
	}
	/**
	 * 设置   更改单受理相关查询接口
	 *  @param waybillRfcVarificationDao
	 */
	public void setWaybillRfcVarificationDao(IWaybillRfcVarificationDao waybillRfcVarificationDao) {
		this.waybillRfcVarificationDao = waybillRfcVarificationDao;
	}
	/**
	 *设置   更改单服务接口
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/**
	 * 设置   运单开单服务，供接送货调用
	 * @param waybillPickupService
	 */
	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	/**
	 * 设置 运单更改单数据持久层接口
	 * @return
	 */
	public IWaybillRfcDao getWaybillRfcDao() {
		return waybillRfcDao;
	}
	/**
	 * 设置   运单更改单数据持久层接口
	 * @param waybillRfcDao
	 */
	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}
	/**
	 * 返回 文件管理服务
	 * @return
	 */
	public FileManager getFileManager() {
		return fileManager;
	}
	/**
	 * 设置 文件管理服务
	 * @return
	 */
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	/**
	 * 设置  运单管理接口
	 * @param waybillManagerService
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 *设置  货物的入库、出库及查询库存方法
	 * @param stockService
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	/**
	 * 货签服务接口
	 *
	 * @param labeledGoodService
	 */
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	/**
	 *  到达联管理接口
	 * @param arriveSheetManngerService
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	/**
	 * 调用中转接口查询走货路径 提供与中转走货路径相关的持久化接口
	 */
	private IMakeUpWaybillService makeUpWaybillService;
	
	
	public IMakeUpWaybillService getMakeUpWaybillService() {
		return makeUpWaybillService;
	}

	public void setMakeUpWaybillService(IMakeUpWaybillService makeUpWaybillService) {
		this.makeUpWaybillService = makeUpWaybillService;
	}

	// 调用qms上报超重超方地址
	private String QMSAutoReportUrl;

	public String getQMSAutoReportUrl() {
		return QMSAutoReportUrl;
	}

	public void setQMSAutoReportUrl(String qMSAutoReportUrl) {
		QMSAutoReportUrl = qMSAutoReportUrl;
	}
	/**
	 *查询运单详情
	 */
	private IWaybillQueryService waybillQueryService;
	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}
	private IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao;
	 public void setWaybillRelateDetailEntityDao(IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao) {
		this.waybillRelateDetailEntityDao = waybillRelateDetailEntityDao;
	}
	 
	// 待刷卡运单service liding add
	private IWSCWayBillManageService wscWayBillManageService;


	public void setWscWayBillManageService(
			IWSCWayBillManageService wscWayBillManageService) {
		this.wscWayBillManageService = wscWayBillManageService;
	}
	
	// NCI 更改单时更改运单号 2016年6月12日 11:01:57 葛亮亮
	private IWSCManageService wscManageService;

	public void setWscManageService(IWSCManageService wscManageService) {
		this.wscManageService = wscManageService;
	}

	/**
		 * 短信服务
		 */
		private ISendSmsVoiceService sendSmsVoiceService;
	    public void setSendSmsVoiceService(ISendSmsVoiceService sendSmsVoiceService) {
			this.sendSmsVoiceService = sendSmsVoiceService;
		}
	/**
	 * 
	 * <p>
	 * 查询更改单<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-27
	 * @param condition 封装查询条件
	 * @return WaybillRfcChangeDto 查询结果对象
	 * @see queryWaybillRfcEntity
	 */
	public List<WaybillRfcChangeDto> queryWaybillRfcVarifyDto(WaybillRfcCondition condition) {
		Date start = condition.getStartDate();
		Date end = condition.getEndDate();
		List<WaybillRfcChangeDto> list = new ArrayList<WaybillRfcChangeDto>();
		//审核，加入状态值
		List<String> fixedStatusList = new ArrayList<String>();
		fixedStatusList.add(WaybillRfcConstants.AUDIT_DENY);//审核拒绝
		fixedStatusList.add(WaybillRfcConstants.PRE_ACCECPT);//待受理、审核同意
		condition.setFixedStatusList(fixedStatusList);
		List<String> statusList = new ArrayList<String>();
		if(WaybillRfcConstants.WAYBILL_RFC_CHECK.equals(condition.getDealType())){//更改单受理类型 CHECK 审核  PROCESS 处理
			if(WaybillRfcConstants.PRE_AUDIT.equals(condition.getDealStatus())){//更改单状态 -- 待审核、起草
				statusList.add(WaybillRfcConstants.PRE_AUDIT);//更改单状态 -- 待审核、起草
			}else if(WaybillRfcConstants.PRE_ACCECPT.equals(condition.getDealStatus())){//待受理、审核同意
				statusList.add(WaybillRfcConstants.PRE_ACCECPT);// 待受理、审核同意
				statusList.add(WaybillRfcConstants.ACCECPT_DENY);//受理拒绝
				statusList.add(WaybillRfcConstants.ACCECPT);// 已同意
			}else if(WaybillRfcConstants.AUDIT_DENY.equals(condition.getDealStatus())){//CUSTOMER_REQUIRE
				statusList.add(WaybillRfcConstants.AUDIT_DENY);// 审核拒绝
			}/*else if(StringUtils.isEmpty(condition.getDealStatus())){
				statusList.add(WaybillRfcConstants.PRE_AUDIT);
				statusList.add(WaybillRfcConstants.PRE_ACCECPT);
				statusList.add(WaybillRfcConstants.AUDIT_DENY);
				statusList.add(WaybillRfcConstants.ACCECPT);
				statusList.add(WaybillRfcConstants.AUDIT_DENY);
			}*/
		}else{
			//转运场下直接挂属人员可能不做业务操作，所以要把小部门转成大部门去查才能查到
			String deptCode = condition.getDeptCode();
			
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
			
	    	if(orgAdministrativeInfoEntity != null){
	    		//是否可空运配载,是否营业部派送部
	    		if(!(FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment()) ||
	    				FossConstants.YES.equals(orgAdministrativeInfoEntity.getDoAirDispatch()))){
	    			//非营业部找到它上级所属外场的编码
	    			List<String>  deptcodes = new ArrayList<String>();
		    		List<String> bizTypes = new ArrayList<String>();
		    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		    		
		    		OrgAdministrativeInfoEntity orgAdministrativeInfoComplexEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(deptCode, bizTypes);
		    		if(orgAdministrativeInfoComplexEntity != null){
		    			String newDeptCode = orgAdministrativeInfoComplexEntity.getCode();
		    			if(!newDeptCode.equals(deptCode)){
		    				deptcodes.add(newDeptCode);
				    		//condition.setParentDeptCode(newDeptCode);
		    				 
		    			}
		    		}
		    		bizTypes.clear();
		    		bizTypes.add(BizTypeConstants.ORG_AIR_DISPATCH);
		    		OrgAdministrativeInfoEntity airorgAdministrativeInfoComplexEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(deptCode, bizTypes);
		    		if(airorgAdministrativeInfoComplexEntity != null){
		    			String newDeptCode = airorgAdministrativeInfoComplexEntity.getCode();
		    			if(!newDeptCode.equals(deptCode)){
		    				deptcodes.add(newDeptCode);
				    		//condition.setParentDeptCode(newDeptCode);
		    				 
		    			}
		    		}
		    	
		    		condition.setParentDeptCodes(deptcodes);
	    		}
	    	}
			
			//受理
			if(StringUtils.isEmpty(condition.getDealStatus())){
				statusList.add(WaybillRfcConstants.PRE_ACCECPT);// 待受理、审核同意
				statusList.add(WaybillRfcConstants.ACCECPT);//已同意
				statusList.add(WaybillRfcConstants.ACCECPT_DENY);//受理拒绝
				
			}else if(WaybillRfcConstants.PRE_ACCECPT.equals(condition.getDealStatus())){//待受理、审核同意
				statusList.add(WaybillRfcConstants.PRE_ACCECPT);//待受理、审核同意
			}else if(WaybillRfcConstants.ACCECPT.equals(condition.getDealStatus())){//已同意
				statusList.add(WaybillRfcConstants.ACCECPT);//已同意
			}else if(WaybillRfcConstants.ACCECPT_DENY.equals(condition.getDealStatus())){//受理拒绝
				statusList.add(WaybillRfcConstants.ACCECPT_DENY);//受理拒绝
			}
		}
		condition.setStatusList(statusList);
		if(StringUtils.isNotEmpty(condition.getWaybillNumber())){
			String waybillNo = condition.getWaybillNumber();
			int len = waybillNo.length();
			if(len < WaybillRfcConstants.WAYBILLNO_LENGTH_8  
					&& len > WaybillRfcConstants.WAYBILLNO_LENGTH_10){
				/**
				 * 请检查运单号,运单号只能是8位或9位！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.NUMBER_CHECK_ERROR);
			}
			Pattern pattern = Pattern.compile(WaybillRfcConstants.WAYBILLNO_REG_EXPRSSION);
			Matcher match = pattern.matcher(waybillNo);
			if(!match.matches()){
				/**
				 * 请检查运单号,运单号只能由数字组成
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.NUMBER_CHECK_MUST_INT);
			}
			list= waybillRfcVarificationDao.queryWaybillRfcVarifyDto(condition);
		}else{
			//1、检查申请日期是否为空
			if(start == null || end == null){
				/**
				 * 请检查申请开始日期及申请结束日期都已选择！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.APPLY_DATE_CHECK_MUST_SELECTED);
			}
			//2、检查两日期值的逻辑-申请日期的起始日期≤截止日期
			if(start.compareTo(end) > 0){
				/**
				 * 起始时间不能大于结束日期！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.STARTE_DATE_MUST_NOT_GREATER_THAN_END_DATE);
			}
			list= waybillRfcVarificationDao.queryWaybillRfcVarifyDto(condition);
		}
		//5、填充库存状态
		WaybillStockStatusDto stockStatus = null;
		//如果为受理，则填充库存状态
		if(WaybillRfcConstants.WAYBILL_RFC_PROCESS.equals(condition.getDealType())){
			for(WaybillRfcChangeDto dto:list){
				try {
					// 查询运单库存状态
					stockStatus = waybillRfcService.queryWaybillStockStatus(dto.getWaybillNumber(), null);
				} catch (BusinessException e) {
					//未找到库存
					stockStatus = new WaybillStockStatusDto();
				}
				dto.setStockStatus(stockStatus.getResult());
			}
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * 依据更改单Id查询对应的凭证信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcId
	 * @return List<WaybillRfcProofEntity> 凭证实体列表
	 * 
	 */
	public List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(
			String waybillRfcId) {
		if(StringUtils.isEmpty(waybillRfcId)){
			throw new WaybillRfcChangeException("code"); 
		}
		// 依据更改单ID查询凭证
		return waybillRfcVarificationDao.queryWayBillRfcProofByRfcId(waybillRfcId);
	}
	
	/**
	 * <p>判断是否进入人工受理（返回true为需进入人工受理）</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 上午11:58:44
	 * @return boolean 
	 */
	public boolean goToWaybillRfcHear(WaybillRfcEntity rfcEntity, WaybillEntity waybill, WaybillStockStatusDto waybillStockStatusDto) {
		// 库存状态
		String stockStatus = waybillStockStatusDto.getResult();
		// 最终结果
		boolean result = false;
		// 检测到达联是否打印
		boolean arriveSheetStatus = false; // 默认到达联未打印
		ArriveSheetEntity temp = new ArriveSheetEntity();
		temp.setWaybillNo(waybill.getWaybillNo());
		List<ArriveSheetEntity> arriveSheetEntityList = arriveSheetManngerService.queryArriveSheetByWaybillNo(temp);
		if (CollectionUtils.isNotEmpty(arriveSheetEntityList)) {
			for (ArriveSheetEntity arriveSheetEntity : arriveSheetEntityList) {
				if (arriveSheetEntity.getActive().equals(FossConstants.YES)) {
					arriveSheetStatus = true; // 运单中有已生成到达联
					break;
				}
			}
		}
		/**
		 * 根据运单产品类型确认审核路径
		 * 
		 * 1.空运类型
		 */
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
			if (WaybillRfcConstants.DELIVERY_STOCK.equals(stockStatus) || WaybillRfcConstants.DELIVERY_STOCK_OUT.equals(stockStatus)) {
				result = true; // 人工受理
			}
			// 梅影 接口，如果已经签收，需要人工受理
			WaybillSignResultEntity entity = new WaybillSignResultEntity(waybill.getWaybillNo(), FossConstants.YES);
			WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
			if (waybillSignResultEntity != null) {
				result = true;
			}

			/**
			 * 航空正单部门
			 */
			String airWaybillDept = airWaybillService.queryAirWaybillDept(rfcEntity.getWaybillNo());

			/**
			 * 判断运单是否已作航空正单
			 */
			if (StringUtil.isNotEmpty(airWaybillDept)) {
				result = true;
				updateLastorgCodeAndEndStockOrgCode(rfcEntity, airWaybillDept);
			}

			boolean isJudeTypeOne = airWaybillService.judgeWaybillInAir(rfcEntity.getWaybillNo(), "1");
			if (isJudeTypeOne) {
				result = true;
			}

		} else {
			// 2.汽运类型
			if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equalsIgnoreCase(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equalsIgnoreCase(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equalsIgnoreCase(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equalsIgnoreCase(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybill.getProductCode())
					|| ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(waybill.getProductCode())
					|| WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(waybill.getProductCode())
					|| WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_PCP.equals(waybill.getProductCode())
					) {
				result = arriveSheetStatus;

				// 林文升：如果汽运最终库存部门已经有库存，需要人工受理：重新获取最终库存部门，查询中转waybillstock表
				String endStockCode = waybillStockService.getEndStockCode(waybill);// 获取最终库存部门
				String isHaveStock = stockService.queryStockByWaybillNoOrgCode(waybill.getWaybillNo(), endStockCode);// 据运单号和当前部门编号查询库存
				if (FossConstants.ACTIVE.equals(isHaveStock)) {
					result = true;
				}
				// 赵斌：如果汽运已经排单，需要人工受理
				if (deliverbillService.isExistDeliverbill(waybill.getWaybillNo())) {
					result = true;
				}

			}

			// 3.整车类型
//			if (ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(waybill.getProductCode())
//					&& arriveSheetStatus
//					&& WaybillRfcConstants.DELIVERY_STOCK_OUT.equals(stockStatus)) {
//				// 人工受理
//				result = true;
//			}
			// 3.整车类型 updated by lufeifei
			if(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(waybill.getProductCode())){
				// 经过到达部门
				if(FossConstants.YES.equals(waybill.getIsPassOwnDepartment())){
					// 打印到达联
					if(arriveSheetStatus){
						result = true;
					}
				}
			}
			// 4.偏线类型
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())) {
				if (WaybillRfcConstants.DELIVERY_STOCK.equals(stockStatus) || WaybillRfcConstants.DELIVERY_STOCK_OUT.equals(stockStatus)) {
					// 人工受理
					result = true;
				}

				// 梅影 接口，如果已经签收，需要人工受理
				WaybillSignResultEntity entity = new WaybillSignResultEntity(waybill.getWaybillNo(), FossConstants.YES);
				WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
				if (waybillSignResultEntity != null) {
					result = true;
				}

			}
		}
		// 如果已经是人工受理，不需要再匹配
		if (!result) {
			Date update = DateUtils.convert("2013-06-01");
			if (waybill.getBillTime() != null) {

				// 如果日期对比后小于0，那么都需要人工受理
				long d = DateUtils.getTimeDiff(update, waybill.getBillTime());
				if (d < 0) {
					result = true;
				}

			}

		}

		if (!result) {
			/**
			 * 如果没有配置，那么直接执行同步入库操作
			 */
			List<String> depts = new ArrayList<String>();
			ConfigurationParamsEntity deptParamsnEntity = null;
			String confValue = null;
			try {
				/**
				 * 获取系统参数 如果为空，设置为同步入库
				 */
				deptParamsnEntity = getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_PARM__WAYBILLRFC_DEPT);
				/**
				 * confValue 需要受理的部门通过“；”号来进行设置，默认为W01000301050203 丢货改善管理小组
				 */
				confValue = deptParamsnEntity.getConfValue();

			} catch (BusinessException e) {
				// 添加日志信息
				LOG.info("获取部门配置获取不到");
			}
			if (StringUtil.isNotEmpty(confValue)) {

				depts = getDepts(depts, confValue);

			} else {
				depts.add("W01000301050203");
			}
			/**
			 * 如果库存部门为系统参数配置部门，将人工受理
			 */
			for (String dept : depts) {
				if (waybillStockStatusDto.getCurrentStockOrgCode() != null && dept.equals(waybillStockStatusDto.getCurrentStockOrgCode())) {
					result = true;
					break;
				}
			}
		}

		if (!result) {
			boolean isNotification = notifyCustomerService.isNotificationSuccessByWaybillNo(rfcEntity.getWaybillNo());
			if (isNotification) {
				result = true;
			}
		}
		
		// 5.经济快递
		if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())){
			/**
			 * BUGKD-1786
			 * 修改逻辑：非出发部门库存的中止，若提货网点为虚拟营业部，操作审核后自动受理（需要录入差错编号，已在前面判断过了）
			 */
	    	if(!WaybillRfcConstants.RECEIVE_STOCK.equals(stockStatus) && WaybillRfcConstants.ABORT.equals(rfcEntity.getRfcType())){
	    		OrgAdministrativeInfoEntity o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybill.getLastLoadOrgCode());
	    		if(o != null && FossConstants.YES.equals(o.getExpressSalesDepartment())){
	    			result = false; //自动受理
	    		}
	    	}
		}
		
		return result;
	}
	
	/**
	 * 更新最终配载部门和最终库存部门
	 */
	private void updateLastorgCodeAndEndStockOrgCode(WaybillRfcEntity rfcEntity,String airWaybillDept) {
		
		OrgAdministrativeInfoEntity orgInfo=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(airWaybillDept);
		String orgName="";
		if(orgInfo!=null){
			
			 orgName=orgInfo.getName();
		}
		waybillDao.updateLastOrgcode(airWaybillDept, orgName,rfcEntity.getOldVersionWaybillId());
		waybillDao.updateLastOrgcode(airWaybillDept, orgName,rfcEntity.getNewVersionWaybillId());
		String endStockOrgCode = outfieldService.queryTransferCenterByAirDispatchCode(airWaybillDept);// 获取空运对应的外场
		waybillDao.updateEndStockOrgcode(endStockOrgCode, rfcEntity.getWaybillNo());
		
	}

	/**
	 * 字符串获取List
	 * @param depts
	 * @param confValue
	 * @return
	 */
	List<String> getDepts(List<String> depts, String confValue) {
		StringTokenizer st = new StringTokenizer(confValue, ";");
		while (st.hasMoreElements()) {
			if (depts != null) {
				depts.add(st.nextToken());
			}
		}
		return depts;
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
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);

	}
	
	
	
	/**
	 * 
	 * <p>
	 * 根据文件路径下载附件（图片）<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param filePath
	 * @return String
	 */
	public String queryWaybillRfcProofByFilePath(String filePath) {
		if(StringUtil.isEmpty(filePath)){
			/**
			 * 凭证文件不存在！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.FILE_NOT_EXIST);
		}
		String proof;
		FileInputStream fis = null;
		try {
			File file = fileManager.read(filePath);
			fis = new FileInputStream(file);
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			BASE64Encoder encoder = new BASE64Encoder();
			proof = encoder.encode(bytes);
		} catch (FileAccessException e) {
			/**
			 * 凭证文件不存在！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.FILE_NOT_EXIST);
		} catch (Exception e) {
			throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILLRFC_BILL_READ_FAILURE);
		} finally {
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				/**
				 * 文件流关闭失败：
				 */
				LOG.error(WaybillRfcChangeException.FILE_CLOSS_FAILURE+e.getMessage(), e);
			}
		}
		return proof;
	}

    private IAutoAddCodeByHandService autoAddCodeByHandService;
	
	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}
	/**
	 * 
	 * <p>同意运单更改审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午8:29:15
	 * @param waybillRfcId
	 * @param notes
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#agreeWaybillRfcCheck(java.lang.String, java.lang.String)
	 */
	@Transactional
	public boolean agreeWaybillRfcCheck(WaybillRfcEntity waybillRfcEntity, String notes, CurrentInfo currentInfo) {
		//代理审核权限检查
		boolean isHoldAuthority = isHoldAuthority(waybillRfcEntity, currentInfo);
		if(!isHoldAuthority){
			/**
			 * 当前用户没有审核权限！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.USER_NOT_AUDIT_PERMISSION);
		}

		Date currentDate = new Date();
			return agreeWaybillRfcCheckNoAuthority(waybillRfcEntity, currentInfo, currentDate, notes, false);
	}

	/**
	 * 
	 * 自动审核 、 人工审核公用方法
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-11 下午4:30:54
	 */
	private boolean agreeWaybillRfcCheckNoAuthority(WaybillRfcEntity waybillRfcEntity, CurrentInfo currentInfo, Date currentDate,
			String notes, boolean isAutoCheck) {

  		String waybillRfcId = waybillRfcEntity.getId();

  		//处理新旧订单DTO
		WaybillDto newVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getNewVersionWaybillId());
		WaybillDto oldVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getOldVersionWaybillId());
		
		//该单已经做过折扣，并且折扣单是未确认状态，请作废折扣单在更改！
		waybillPickupService.querydiscountPayable(waybillRfcEntity.getWaybillNo());
		
  		/**
  		 * 判断审核之前状态是否是待审核
  		 */
  		if(!WaybillRfcConstants.PRE_AUDIT.equals(waybillRfcEntity.getStatus())){
  			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_APPROVED);
  		}  		
  		WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(waybillRfcEntity.getOldVersionWaybillId());
  		WaybillStockStatusDto stockStatusDto = waybillRfcService.queryWaybillStockStatus(waybillRfcEntity.getWaybillNo(),waybillEntity);
  		PtpWaybillDto ptpWaybillDto = waybillRfcEntity.getPtpWaybillDto();
  		if(ptpWaybillDto == null){
  			ptpWaybillDto = new PtpWaybillDto();
  		}
  		if(stockStatusDto != null){
  			ptpWaybillDto.setWaybillStock(stockStatusDto.getResult());
  		}
  		waybillRfcEntity.setPtpWaybillDto(ptpWaybillDto);
  		//1.系统判断是否拒绝审核通过
  		List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList = waybillRfcVarificationDao.queryWayBillRfcChangeDetailByRfcId(waybillRfcEntity.getId());
		for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
		    String changeDetailItem = changeDetailEntity.getRfcItems();
		    //1.1如修改提货网点，且货物已出开单部门库存，且外部变更(SUC变更)，则审核不能通过
		    if("customerPickupOrgCode".equals(changeDetailItem)
    	    		&&!WaybillRfcConstants.RECEIVE_STOCK.equals(stockStatusDto.getResult())
    	    		&&WaybillRfcConstants.CUSTOMER_CHANGE.equals(waybillRfcEntity.getRfcType())){
		    	/**
		    	 * 该运单已出开单部门库存，请重新起草转运单或返货单修改提货网点!
		    	 */
		    	throw new WaybillRfcChangeException(WaybillRfcChangeException.DRAFT_AGAIN_WAYBILLRFC);
		    }	
		    //1.2如修改运单号，且货物已出开单部门库存，则审核不能通过
		    else if("waybillNo".equals(changeDetailItem)
		    		&&!WaybillRfcConstants.RECEIVE_STOCK.equals(stockStatusDto.getResult())){
		    	/**
		    	 * 该运单已出开单部门库存，无法更改运单号!
		    	 */
		    	throw new WaybillRfcChangeException(WaybillRfcChangeException.CAN_NOT_CHANGE_WAYBILL_NUMBER);
		    }
		}	
  		
  		
  		//3.设置更改单
  		waybillRfcEntity.setStatus(WaybillRfcConstants.PRE_ACCECPT);
  		if(isAutoCheck){
  	  		waybillRfcEntity.setOperateOrgCode(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  	  		waybillRfcEntity.setOperateOrgName(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  	  		waybillRfcEntity.setOperator(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  	  		waybillRfcEntity.setOperatorCode(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  		}else{
  	  		waybillRfcEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
  	  		waybillRfcEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
  	  		waybillRfcEntity.setOperator(currentInfo.getEmpName());
  	  		waybillRfcEntity.setOperatorCode(currentInfo.getEmpCode());
  		}
  		waybillRfcEntity.setOperateTime(currentDate);
  		//设置更改单备注信息
  		if(StringUtils.isEmpty(notes)){
  			waybillRfcEntity.setNotes(AGREE_OF_PROCESS_WAYBILLRFC);
  		}else{
  			waybillRfcEntity.setNotes(notes);
  		}
  		
  		//5.新增更改历史记录
  		WaybillRfcActionHistoryEntity actionHistory = this.getActionHistoryFromWaybillRfcEntity(waybillRfcEntity, currentDate);
  		waybillRfcVarificationDao.addWaybillRfcActionHistory(actionHistory);
  		if(waybillEntity.getProductCode().equals(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ROUND_COUPON_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.PACKAGE)){
		//6.删除快递审核待办提醒
  		if(!isAutoCheck){
//			if(!WaybillConstants.PACKAGE.equals(waybillEntity.getProductCode())){
				pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT_WAYBILL, 
						null, waybillRfcId);
//			}
		//	pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT, currentInfo.getCurrentDeptCode(), waybillRfcId);
		}
		//7.1进入自动受理流程
	    if(!goToWaybillRfcHear(waybillRfcEntity,waybillEntity, stockStatusDto)){
	    	//设置变更单为自动受理状态
	    	waybillRfcEntity.setIsLabourHandle(FossConstants.YES);
	    	agreeWaybillRfcOpinionPure(waybillRfcEntity, notes, currentInfo, currentDate, true);
			
			//更改单自动受理数（简单计数）
			businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_AUTO_ACCEPT_COUNT_WAY, currentInfo);
			sendWillbill(newVersionDto,oldVersionDto);
    		return true;
	    }
	    //7.2进入人工受理流程
	    else{ 	
	    	waybillRfcEntity.setIsLabourHandle(FossConstants.NO);
	  		//4.更新更改单
	  		waybillRfcDao.updateByPrimaryKeySelective(waybillRfcEntity);
	  	//消息提醒：提醒受理人员快递受理已通过审核的待受理更改单
	    	pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT_WAYBILL,
	    			DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT_WAYBILL,null, waybillEntity.getLastLoadOrgCode(),waybillRfcEntity.getWaybillNo(), waybillRfcEntity.getId());
	    	sendWillbill(newVersionDto,oldVersionDto);
	    	return true;
	    }
  		
  		}else{
  	//6.删除审核待办提醒
  		if(!isAutoCheck){
//  			if(!WaybillConstants.PACKAGE.equals(waybillEntity.getProductCode())){
  				pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT, 
  						null, waybillRfcId);
//  			}
  		//	pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT, currentInfo.getCurrentDeptCode(), waybillRfcId);
  		}
	   
		//7.1进入自动受理流程
	    if(!goToWaybillRfcHear(waybillRfcEntity,waybillEntity, stockStatusDto)){
	    	//设置变更单为自动受理状态
	    	waybillRfcEntity.setIsLabourHandle(FossConstants.YES);
	    	agreeWaybillRfcOpinionPure(waybillRfcEntity, notes, currentInfo, currentDate, true);
			//更改单自动受理数（简单计数）
			businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_AUTO_ACCEPT_COUNT, currentInfo);
			sendWillbill(newVersionDto,oldVersionDto);
    		return true;
	    }
	    //7.2进入人工受理流程
	    else{
            //人工审核-校验信用额度
            //DP-FOSS zhaoyiqing 343617
            WaybillEntity oldWaybill = oldVersionDto.getWaybillEntity();
            WaybillEntity newWaybill = newVersionDto.getWaybillEntity();
            this.getBeBebtForCUBC(oldWaybill,newWaybill);


	    	waybillRfcEntity.setIsLabourHandle(FossConstants.NO);
	  		//4.更新更改单
	  		waybillRfcDao.updateByPrimaryKeySelective(waybillRfcEntity);
	  		//消息提醒：提醒受理人员受理已通过审核的待受理更改单
	    	pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT,
	    			DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT,null, waybillEntity.getLastLoadOrgCode(),waybillRfcEntity.getWaybillNo(), waybillRfcEntity.getId());
	    	sendWillbill(newVersionDto,oldVersionDto);
	    	return true;
	    }}
	}
	/**
	 * 运单体积发生更改时
	 */
	//DMANA-4382  营业部改小打木架运单总体积后同步更新代包装体积
	public void sendWillbill(WaybillDto newVersionDto,WaybillDto oldVersionDto){
		WaybillEntity newWaybillEntity = newVersionDto.getWaybillEntity();
		WoodenRequirementsEntity oldWoodRequirements = oldVersionDto.getWoodenRequirementsEntity();// oldWoodRequirement可能是null
		WoodenRequirementsEntity newWoodRequirements = newVersionDto.getWoodenRequirementsEntity();
		BigDecimal oldStandGoodsVolume = null;
		BigDecimal newStandGoodsVolume = null;
		//旧的打木架信息不为空时，如果打木架体积为空将打木架体积设为0；旧的打木架信息为空时，设置打木架体积为0；
		if(oldWoodRequirements!=null){
			 oldStandGoodsVolume = oldWoodRequirements.getStandGoodsVolume();
			if(oldStandGoodsVolume==null){
				oldStandGoodsVolume=BigDecimal.ZERO;
			}
		}else{
			oldStandGoodsVolume=BigDecimal.ZERO;
		}
		//新的打木架信息不为空时，如果打木架体积为空将打木架体积设为0；新的打木架信息为空时，设置打木架体积为0；
		if(newWoodRequirements!=null){
			 newStandGoodsVolume = newWoodRequirements.getStandGoodsVolume();
			if(newStandGoodsVolume==null){
				newStandGoodsVolume=BigDecimal.ZERO;
			}
		}else{
			newStandGoodsVolume=BigDecimal.ZERO;
		}
		if(oldStandGoodsVolume.compareTo(newStandGoodsVolume)!=0){
			//当打木架体积发生变更时调用中转的接口
			packageAssistPriceService.updatePriceByWayBillNo(newWaybillEntity.getWaybillNo());
		}
		
	}
	
	/**
	 * 
	 * <p>更改单自动审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午8:29:15
	 * @param waybillRfcId
	 * @param notes
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#agreeWaybillRfcCheck(java.lang.String, java.lang.String)
	 */
	@Transactional
	public boolean agreeWaybillRfcCheckAuto(WaybillRfcEntity rfcEntity, CurrentInfo currentInfo, Date currentDate){
		return agreeWaybillRfcCheckNoAuthority(rfcEntity, currentInfo, currentDate, AGREE_OF_PROCESS_WAYBILLRFC, true);
	}
	
	/**
	 * 
	 * 将WaybillEntity类型转换为WaybillPickupInfoDto
	 * @author 043260-foss-suyujun
	 * @date 2012-12-28
	 * @param waybill
	 * @return WaybillPickupInfoDto
	 */
	private WaybillPickupInfoDto convertWaybillToDto(WaybillEntity waybill, WaybillDto dto2,ActualFreightEntity afc){
		WaybillPickupInfoDto dto = new WaybillPickupInfoDto();//运单开单信息DTO
		dto.setInvoiceMark(afc.getInvoice());
		//將是【否統一結算】【合同部門】【催款部門】傳遞給結算部門
		dto.setOrigUnifiedSettlement(afc.getStartCentralizedSettlement());
		dto.setDestUnifiedSettlement(afc.getArriveCentralizedSettlement());
		dto.setOrigContractUnifiedCode(afc.getStartContractOrgCode());
		dto.setDestContractUnifiedCode(afc.getArriveContractOrgCode());
		dto.setOrigUnifiedDuningCode(afc.getStartReminderOrgCode());
		dto.setDestUnifiedDuningCode(afc.getArriveReminderOrgCode());
		try {
			PropertyUtils.copyProperties(dto, waybill);
		} catch (IllegalAccessException e) {
			throw new WaybillSubmitException(WaybillSubmitException.SUBMIT_WAYBILL_FAIL, new Object[]{messageBundle.getMessage(e.getMessage())});//运单提交失败
		} catch (InvocationTargetException e) {
			throw new WaybillSubmitException(WaybillSubmitException.SUBMIT_WAYBILL_FAIL, new Object[]{messageBundle.getMessage(e.getMessage())});//运单提交失败
		} catch (NoSuchMethodException e) {
			throw new WaybillSubmitException(WaybillSubmitException.SUBMIT_WAYBILL_FAIL, new Object[]{messageBundle.getMessage(e.getMessage())});//运单提交失败
		}
		
		
		//出发部门
		SaleDepartmentEntity leaveDept = saleDepartmentService.querySaleDepartmentByCode(waybill.getReceiveOrgCode());
		//最终配载部门
		SaleDepartmentEntity arriveDept = saleDepartmentService.querySaleDepartmentByCode(waybill.getLastLoadOrgCode());
		
		if(leaveDept != null){
			//出发部门名称
			dto.setReceiveOrgName(leaveDept.getName());
		}
		
		if(arriveDept != null){
			// 到达部门名称
			dto.setLastLoadOrgName(arriveDept.getName());
		}

		if(StringUtils.isNotEmpty(waybill.getAccountName()) && StringUtils.isNotEmpty(waybill.getAccountCode()) 
				&&StringUtils.isNotEmpty(waybill.getAccountBank()) && StringUtils.isNotEmpty(waybill.getDeliveryCustomerCode()) ){
			//开户银行信息
			CusAccountEntity openBank = waybillRfcVarificationDao.queryCusAccountByWaybillInfo(
					waybill.getAccountName(), waybill.getAccountCode(), waybill.getAccountBank(),waybill.getDeliveryCustomerCode());
		
			/**
			 * CRM统一客户信息会作废一些客户，如果查询不到有效的客户信息，则获取创建时间最晚的一次的客户信息
			 */
			if(openBank==null){
				openBank=waybillRfcVarificationDao.queryCusAccountByCreateTime(
						waybill.getAccountName(), waybill.getAccountCode(), waybill.getAccountBank(),waybill.getDeliveryCustomerCode());
			}
			
    		//非空判断
    		if(null != openBank){
    			//开户行编码
    			dto.setBankHQCode(openBank.getBankCode());
    			//开户行名称
    			dto.setBankHQName(openBank.getOpeningBankName());
    			//省份编码
    			dto.setProvinceCode(openBank.getProvCode());
    			//省份名
    			dto.setProvinceName(openBank.getProvinceName());
    			//城市编码
    			dto.setCityCode(openBank.getCityCode());
    			//城市名
    			dto.setCityName(openBank.getCityName());
    			//支行编码（行号）
    			dto.setBankBranchCode(openBank.getBranchBankCode());
    			//支行名称
    			dto.setBankBranchName(openBank.getBranchBankName());
    			//对公对私标志
    			dto.setPublicPrivateFlag(openBank.getAccountNature());
    			//收款人与发货人关系
    			dto.setPayeeRelationship(openBank.getCustomer());
    			//收款人手机号码
    			dto.setPayeePhone(openBank.getMobilePhone());
    		}
		}
		
		if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())){

			OrgAdministrativeInfoEntity org = this.queryOrgInfoByCode(waybill.getCustomerPickupOrgCode());
			if(null == org){
				dto.setIsSelfStation(FossConstants.NO);
			}else{
				if(org.checkExpressSalesDepartment() || org.checkTransferCenter() || org.checkDoAirDispatch()){
					dto.setIsSelfStation(FossConstants.NO);
				}else{
					dto.setIsSelfStation(FossConstants.YES );
				}
			}
		}
		
		//小件新加字段
		if (dto2.getWaybillExpressEntity() != null) {
			dto.setPosSerialNum(dto2.getWaybillExpressEntity().getPdaSerial());
			dto.setBatchNo(dto2.getWaybillExpressEntity().getBankTradeSerail());
		}
		/**
		 * 根据Dmana-4437将“交易流水号”传入
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-08
		 */
		dto.setBatchNo(afc.getTransactionSerialNumber());
		return dto;
	}
	
	public OrgAdministrativeInfoEntity queryOrgInfoByCode(String code) {
		//根据组织编码查询组织信息
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
	}
	
	private IWaybillRfcTodoJobService waybillRfcTodoJobService;
	
	/**
	 * @return the waybillRfcTodoJobService
	 */
	public IWaybillRfcTodoJobService getWaybillRfcTodoJobService() {
		return waybillRfcTodoJobService;
	}


	/**
	 * @param waybillRfcTodoJobService the waybillRfcTodoJobService to set
	 */
	public void setWaybillRfcTodoJobService(
			IWaybillRfcTodoJobService waybillRfcTodoJobService) {
		this.waybillRfcTodoJobService = waybillRfcTodoJobService;
	}

	private IWaybillRfcJobService waybillRfcJobService;
	

	/**
	 * @return the waybillRfcJobService
	 */
	public IWaybillRfcJobService getWaybillRfcJobService() {
		return waybillRfcJobService;
	}


	/**
	 * @param waybillRfcJobService the waybillRfcJobService to set
	 */
	public void setWaybillRfcJobService(IWaybillRfcJobService waybillRfcJobService) {
		this.waybillRfcJobService = waybillRfcJobService;
	}


	/**
	 * 
	 * <p>根据变更项生成待办事项</p>    
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-20 18:55:22
	 * @param waybillRfcEntity
	 * @param notes
	 * @see
	 */
	private void addToDoActionByWaybillRfcChangeDetail(WaybillRfcEntity waybillRfcEntity,
			WaybillDto newVersionDto,WaybillDto oldVersionDto) {
		boolean isNeedContinue = false;
		//查询更改明细
		List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList = waybillRfcVarificationDao.queryWayBillRfcChangeDetailByRfcId(waybillRfcEntity.getId());
		if(CollectionUtils.isNotEmpty(waybillRfcChangeDetailEntityList)){
			for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
    		    String changeDetailItem = changeDetailEntity.getRfcItems();
    		    //只要更改了除提货方式的，都是需要生成待办的
    		    if(checkWaybillRfcChangedItem(changeDetailItem)){
    		    	isNeedContinue = true;//需要生成带处理待办
    		    	break;
    		    }
    		    //【更改单待办事项：当更改后提货网点是驻地部门的时候，并且收货人地址发生变化的时候，生成待办事项】
    		    //当收货人地址发生改变并且 提货网点是驻地营业部的时候生成待办事项
    		    if("receiveCustomerArea".equals(changeDetailItem) &&checkDeptIsStation(newVersionDto)){
    		    	isNeedContinue = true;//需要生成带处理待办
    		    	break;
    		    }
    		}
			//用于存放更改提货方式的数据，方便后面进行比较
			WaybillRfcChangeDetailEntity details = new WaybillRfcChangeDetailEntity();
			if(!isNeedContinue){
				for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
	    		    String changeDetailItem = changeDetailEntity.getRfcItems();
	    		    //只要涉及到提货方式的变更
	    		    if("receiveMethod".equals(changeDetailItem)){
	    		    	isNeedContinue = true;//需要生成带处理待办
	    		    	details = changeDetailEntity;
	    		    	break;
	    		    }
	    		}
				//送货改成送货不需要生成，送货改成自提、自提改成送货需要生成
				if(isNeedContinue){
					if((details.getBeforeRfcInfo().indexOf("送货")>=0) 
							&& details.getAfterRfcInfo().indexOf("送货")>=0){
						isNeedContinue = false;
					}
				}
			}			
		}
		//只有需要才会生成待办，否则是不需要生成的
		if(isNeedContinue){
			PendingTodoEntity todoEntity = new PendingTodoEntity();
			todoEntity.setWaybillRfcId(waybillRfcEntity.getId());
			todoEntity.setWaybillNo(waybillRfcEntity.getWaybillNo());
			pendingTodoDao.insert(todoEntity);
		}	
	}
	/**
	 * @author 200945 - wutao
	 * <a>验证更改单后，提货网点是否是驻地部门</a>
	 * 排除偏线，空运，快递
	 * @param newVersionDto
	 * @return
	 */
	protected boolean checkDeptIsStation(WaybillDto newVersionDto) {
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
				.equals(newVersionDto.getWaybillEntity().getProductCode())
				&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
						.equals(newVersionDto.getWaybillEntity()
								.getProductCode())
				&& !waybillExpressService.onlineDetermineIsExpressByProductCode(newVersionDto.getWaybillEntity().getProductCode(), newVersionDto.getWaybillEntity().getBillTime())) {
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
					.querySaleDepartmentByCode(newVersionDto.getWaybillEntity()
							.getCustomerPickupOrgCode());
			// 判断是否为驻地部门
			if (saleDepartmentEntity.getStation() != null
					&& FossConstants.YES.equals(saleDepartmentEntity.getStation())) {
				// 如果【驻地营业部】,并且更改后的方式为非自提的话，那就满足生成待办事项的前提
				if (!WaybillConstants.SELF_PICKUP.equals(newVersionDto
						.getWaybillEntity().getReceiveMethod())
						&& !WaybillConstants.INNER_PICKUP.equals(newVersionDto
								.getWaybillEntity().getReceiveMethod())
						&& !WaybillConstants.AIR_PICKUP_FREE
								.equals(newVersionDto.getWaybillEntity()
										.getReceiveMethod())
						&& !WaybillConstants.AIRPORT_PICKUP
								.equals(newVersionDto.getWaybillEntity()
										.getReceiveMethod())
						&& !WaybillConstants.AIR_SELF_PICKUP
								.equals(newVersionDto.getWaybillEntity()
										.getReceiveMethod())) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean checkWaybillRfcChangedItem(String item){
	    //改变类型:提货网点、包装(纸木纤托膜其他)、件数、货物类型、运输性质、提货方式
	    String items = "customerPickupOrgName+paper+wood+fibre+salver+membrane+otherPackage+goodsQtyTotal+goodsType+productCode+waybillNo+isExhibitCargo+";
	    return (items.indexOf(item+'+')>=0);
	}
	
	/**
	 * 
	 * <p>审核通过，系统自动受理生成待办事项(更改项为除最终目的地变更外的其他更改项)</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-3 上午11:51:00
	 * @param waybillRfc
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#addTodoActionForOtherChanged(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity)
	 */
	@Transactional
	public void addTodoActionForOtherChanged(WaybillRfcEntity waybillRfc, WaybillDto newVersionDto,WaybillDto oldVersionDto) {
		//查询原运单
		WaybillEntity waybill = newVersionDto.getWaybillEntity();
		
		//整车不生成代办
		if(FossConstants.YES.equals(waybill.getIsWholeVehicle())){
			return;
		}
		
	    WaybillLabeledGoodStockDto labeledGoodStockDto = waybillRfcService.queryWaybillLabeledGoodStock(waybillRfc.getWaybillNo());
	    List<LabeledGoodStockStatusDto> labeledGoodStockStatusDtos = labeledGoodStockDto.getLabeledGoodStockStatusDtos();
	    //生成当前部门或下一环节(在途)的待办事项
	    if(labeledGoodStockStatusDtos!=null){
	    	List<LabeledGoodTodoEntity> labeledGoodTodoEntitys = new ArrayList<LabeledGoodTodoEntity>();
	    	for(LabeledGoodStockStatusDto labeledGoodStockStatusDto:labeledGoodStockStatusDtos){
	    		LabeledGoodEntity labeledGoodEntity = labeledGoodStockStatusDto.getLabeledGoodEntity();
	    		LabeledGoodTodoEntity labeledGoodTodoEntity = addLabeledGoodTodo(waybillRfc,labeledGoodEntity,labeledGoodStockStatusDto.getCurrentStockOrgCode(), labeledGoodStockStatusDto.isInStock());
	    		labeledGoodTodoEntitys.add(labeledGoodTodoEntity);
	    	}
	    	labeledGoodTodoDao.batchAddLabeledGoodTodo(labeledGoodTodoEntitys);
	    }
	}
	
	/**
	 * 
	 * <p>待办事项</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-3 下午8:51:19
	 * @param waybillRfc
	 * @param orgCode 
	 * @param isInStock 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#addTodoAction(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity, java.lang.String)
	 */
	@Transactional
	public LabeledGoodTodoEntity addLabeledGoodTodo(WaybillRfcEntity waybillRfc, LabeledGoodEntity labeledGoodEntity, String orgCode, boolean isInStock) {
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
	    if(orgCode==null)
	    {
	    	throw new WaybillRfcChangeException(WaybillRfcChangeException.ORG_ADMINISTRATIVE_INFO_NULL,new Object[]{orgCode});
	    }
	    
//	    Map<String,Object> args = new HashMap<String, Object>();
//	    args.put("orgCode", orgCode);
//	    args.put("waybillRfcNo", waybillRfc.getId());
//		args.put("serialNo", labeledGoodEntity.getSerialNo());
//		List<LabeledGoodTodoEntity> temp = labeledGoodTodoDao.queryByOrgAndRfcNo(args);
//	    if(temp.isEmpty()){
	    	LabeledGoodTodoEntity labeledGoodTodoEntity = new LabeledGoodTodoEntity();
		    labeledGoodTodoEntity.setLabeledGoodId(labeledGoodEntity.getId());
		    labeledGoodTodoEntity.setSerialNo(labeledGoodEntity.getSerialNo());
		    labeledGoodTodoEntity.setPrinted(FossConstants.NO);
		    labeledGoodTodoEntity.setWaybillRfcId(waybillRfc.getId());
		    labeledGoodTodoEntity.setHandleOrgCode(orgCode);
		    labeledGoodTodoEntity.setHandleOrgName(org.getName());
		    labeledGoodTodoEntity.setCreateTime(waybillRfc.getOperateTime());
		    labeledGoodTodoEntity.setStatus(FossConstants.NO);
		    //生成执行节点
		    //labeledGoodTodoEntity.setActuatingNode(addActuatingNode(waybillRfc,labeledGoodEntity,orgCode));
		    //判断条件：是否在途
		    if(isInStock){
		    	labeledGoodTodoEntity.setRemindTime(waybillRfc.getOperateTime());
		    }else{
		    	//设置默认时间1970-01-01 00:00:00
		    	labeledGoodTodoEntity.setRemindTime(new Date(0));
		    }
		    labeledGoodTodoEntity.setIsSendRemind(FossConstants.NO);
		    return labeledGoodTodoEntity;
		    //待办提醒，打印标签提醒
	    	//pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_LABELED_PRINT, null, orgCode, waybillRfc.getWaybillNo(), labeledGoodTodoEntity.getId());
//	    }
	}
	
//	/**
//	 * 
//	 * <p>根据走货路径生成待办操作节点</p> 
//	 * @author foss-gengzhe
//	 * @date 2012-12-14 上午11:25:57
//	 * @param waybillRfc
//	 * @param labeledGoodEntity
//	 * @param orgCode
//	 * @return
//	 * @see
//	 */
//	private String addActuatingNode(WaybillRfcEntity waybillRfc, LabeledGoodEntity labeledGoodEntity, String orgCode){
//		List<PathDetailEntity> pathDetailEntityList = calculateTransportPathService.queryTotalPath(waybillRfc.getWaybillNo(), labeledGoodEntity.getSerialNo());
//		StringBuffer actuatingNode = new StringBuffer();
//		for(PathDetailEntity pathDetailEntity : pathDetailEntityList){
//			//找到当前部门为出发部门
//			if(orgCode.equals(pathDetailEntity.getOrigOrgCode())){
//    			actuatingNode.append(pathDetailEntity.getOrigOrgCode());
//			}
//    		//找到新的起点后，保存之后的全部走货路径
//			if(actuatingNode.length()>0){
//				actuatingNode.append(",").append(pathDetailEntity.getObjectiveOrgCode());
//			}
//		}
//		// 走货路径上未找到当前部门直接加入执行节点
//		if(actuatingNode.length()==0){
//			actuatingNode.append(orgCode);
//		}
//		return actuatingNode.toString();
//	}
	
	/**
	 * 
	 * <p>拒绝运单更改审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午8:29:41
	 * @param waybillRfcId
	 * @param notes
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#refuseWaybillRfcCheck(java.lang.String, java.lang.String)
	 */
	@Transactional
	public boolean refuseWaybillRfcCheck(WaybillRfcEntity waybillRfcEntity, String notes, CurrentInfo currentInfo) {
		Date currentDate = new Date();
		//代理审核权限检查
		boolean isHoldAuthority = isHoldAuthority(waybillRfcEntity,currentInfo);
		if(!isHoldAuthority){
			/**
			 * 当前用户没有审核权限！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.USER_NOT_AUDIT_PERMISSION);
		}
		//1、判断拒绝时是否有备注
		if(StringUtils.isEmpty(notes)){
			/**
			 * 请填写审核拒绝备注
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.MUST_FILL_IN_AUDIT_NOTES);
		}else{
			waybillRfcEntity.setNotes(notes);
		}
		/**
  		 * 判断审核之前状态是否是待审核
  		 */
  		if(!WaybillRfcConstants.PRE_AUDIT.equals(waybillRfcEntity.getStatus())){
  			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_APPROVED);
  		}
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(waybillRfcEntity.getOldVersionWaybillId());
		//3、新增更改记录
		WaybillDto newVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getNewVersionWaybillId());
		WaybillDto oldVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getOldVersionWaybillId());
		handleNewOldWaybillEntity(newVersionDto,oldVersionDto,WaybillRfcConstants.ACCECPT_DENY,waybillRfcEntity, currentInfo);
		//4、更新更改单
		waybillRfcEntity.setStatus(WaybillRfcConstants.AUDIT_DENY);
		
		//TODO
		//将冗余表的信息更新为审批通过
		labeledGoodChangeDao.deleteLabeledGoodChangeInProcessByWaybillNo( waybillRfcEntity.getWaybillNo()); 
				
  		waybillRfcEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
  		waybillRfcEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
  		waybillRfcEntity.setOperateTime(currentDate);
  		waybillRfcEntity.setOperator(currentInfo.getEmpName());
  		waybillRfcEntity.setOperatorCode(currentInfo.getEmpCode());
		waybillRfcDao.updateByPrimaryKeySelective(waybillRfcEntity);
		//5,历史记录
		WaybillRfcActionHistoryEntity actionHistory = this.getActionHistoryFromWaybillRfcEntity(waybillRfcEntity, currentDate);
		waybillRfcVarificationDao.addWaybillRfcActionHistory(actionHistory);
  		if(waybillEntity.getProductCode().equals(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ROUND_COUPON_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.PACKAGE)){
		pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT_WAYBILL, null, waybillRfcEntity.getId());
  		}else{//消除待办消息
		pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT, null, waybillRfcEntity.getId());
  		}
		return true;
	}

	
	/**
	 * 更改单受理同意操作
	 * @author 043260-foss-suyujun
	 * @date 2012-12-5
	 * @param waybillRfcEntity
	 * @param notes
	 * @return void
	 */
	@Transactional()
	public boolean agreeWaybillRfcOpinion(WaybillRfcEntity waybillRfcEntity,
			String notes, CurrentInfo currentInfo){
		agreeWaybillRfcOpinionPure(waybillRfcEntity, notes, currentInfo, new Date(), false);
		//是否自动受理，自动受理删除受理待办提醒
		/**
		 * DEFECT-2293 更改更新待办详情方法，根据运单更新所有待补录数据待办类型的数据
		 * serialNumber只有是在PKP_PDA_WAYBILL的类型时候才是对应的运单号,与对应的组织无关
		 */
  		WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(waybillRfcEntity.getOldVersionWaybillId());
  		if(waybillEntity.getProductCode().equals(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ROUND_COUPON_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.PACKAGE)){
		pickupToDoMsgService.removeOneToDoMsg(
				DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT_WAYBILL,
				null, waybillRfcEntity.getId());
  		}else{
		pickupToDoMsgService.removeOneToDoMsg(
				DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT,
				null, waybillRfcEntity.getId());
		
		}
  		return true;
	}

	/**
	 * 设置审批同意时候的一些详细明细字段值
	 * @param waybillRfcId
	 * @param notes
	 * @param isAuto
	 */
	private void agreeWaybillRfcOpinionPure(WaybillRfcEntity waybillRfcEntity, String notes, CurrentInfo currentInfo, Date currentDate, boolean isAuto) {
		/**
  		 * 判断受理之前状态是否是待受理
  		 */
  		if(!WaybillRfcConstants.PRE_ACCECPT.equals(waybillRfcEntity.getStatus())){
  			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_ACCECPTED);
  		}
		//6、处理新旧订单DTO

		WaybillDto newVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getNewVersionWaybillId());
		WaybillDto oldVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getOldVersionWaybillId());	
		

 		 /**
	     * 判断运单在0或1中是否存在
	     * PCR-215
                         已合票空运货更改单受理增加限制          
                         运单已做合票，空运总调受理运输性质类型的更改单时（空运改其他类型运输性质），系统必须做出限制，运单不从合票清单中删除，更改单不可同意受理（可以拒绝受理），
                         同时系统给出提示信息：请将先该运单从合票清单中删除，才能受理更改单！ 
	     * @param waybillNo 运单号，type 0表示正单，1表示合票
	     * @return boolean
	     * @author 046130-foss-xuduowei
	     * @date 2013-07-12 下午3:16:23
	     */
		String newProductCode =newVersionDto.getWaybillEntity().getProductCode();
		String oldProductCode=oldVersionDto.getWaybillEntity().getProductCode();

		/**
		 * 如果为空运
		 */
		if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(oldProductCode)){
			/**
			 * 如果新的运单为非空运
			 */
			if(!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(newProductCode)){
			boolean isJudeTypeOne= airWaybillService.judgeWaybillInAir(waybillRfcEntity.getWaybillNo(), "1");
		    if(isJudeTypeOne){
		    	
		    	throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_ACCECPTED_ALREADY_ADD);
		    }
			
			}
			
		}
		
  		
  		
  		/**
  		 * 数据完整性校验
  		 */
  		checkDataIntegrity(waybillRfcEntity);
		
		//2、设置更改单备注信息
		if(StringUtils.isEmpty(notes)){
			waybillRfcEntity.setNotes(AGREE_OF_PROCESS_WAYBILLRFC);
		}else{
			waybillRfcEntity.setNotes(notes);
		}
		//3、设置更改单状态
		waybillRfcEntity.setStatus(WaybillRfcConstants.ACCECPT);
  		waybillRfcEntity.setOperateTime(currentDate);
  		//判断是否需要  合伙人受理更改单提成 标识  2016年10月29日09:21:03 by:352676
  		String isParnterAcceptStatus="N";
  		//判断是否系统自动受理
  		if(isAuto){
  	  		waybillRfcEntity.setOperateOrgCode(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  	  		waybillRfcEntity.setOperateOrgName(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  	  		waybillRfcEntity.setOperator(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  	  		waybillRfcEntity.setOperatorCode(SYSTEM_AUTOHANDLE_WAYBILLRFC);
  		}else{
  	  		waybillRfcEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
  	  		waybillRfcEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
  	  		waybillRfcEntity.setOperator(currentInfo.getEmpName());
  	  		waybillRfcEntity.setOperatorCode(currentInfo.getEmpCode());
  	  		
  	  		//为手动处理，判断受理部门是否为合伙人   2016年10月29日09:30:08 by:352676
  	  		if(StringUtil.isNotBlank(waybillRfcEntity.getOperateOrgCode())){
  	  		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCodeCache(waybillRfcEntity.getOperateOrgCode());
	  	  		if(null != saleDepartmentEntity && StringUtil.equals(saleDepartmentEntity.getIsLeagueSaleDept(), "Y")){
	  	  			isParnterAcceptStatus = "Y";
	  	  		}  	  		
  	  		}
  		}
		
  		//4、更新更改单
		waybillRfcDao.updateByPrimaryKeySelective(waybillRfcEntity);
		
		//zxy 20140308 DEFECT-1997 start 修改:移动代码段到doAfterVarification之前, 运单号发生变更导致旧运单号在承运信息表查不到数据
		//zxy 20131228 MANA-518 start 新增:(不含快递)货物离开出发部门库存后，若起草更改单，涉及到重量体积减小（重量减小超过4%，体积减小超过10%），且造成运费减少，系统自动上报一条超重超方差错，且自动成立。
		BigDecimal newWeight = newVersionDto.getWaybillEntity().getGoodsWeightTotal();
		BigDecimal oldWeight = oldVersionDto.getWaybillEntity().getGoodsWeightTotal();
		BigDecimal newVolumn = newVersionDto.getWaybillEntity().getGoodsVolumeTotal();
		BigDecimal oldVolumn = oldVersionDto.getWaybillEntity().getGoodsVolumeTotal();
		BigDecimal newTransportFee = newVersionDto.getWaybillEntity().getTransportFee();
		BigDecimal oldTransportFee = oldVersionDto.getWaybillEntity().getTransportFee();
		
		String newCustomerPickUpOrgCode = newVersionDto.getWaybillEntity().getCustomerPickupOrgCode();//新提货网点
		String oldCustomerPickUpOrgCode = oldVersionDto.getWaybillEntity().getCustomerPickupOrgCode();//旧提货网点
				//上报限制
		//重量在25KG以下不做要求。体积在0.1立方以下不做要求。弹性/可收缩货物允许误差范围＜25%(超方,注：弹性/可收缩货物为海绵、棉花、服装、布匹)
		//空运：重量允许误差范围1kg
		boolean weightLimit = oldWeight.compareTo(new BigDecimal(NumberConstants.NUMBER_25)) < 0;
		boolean volumnLimit = oldVolumn.compareTo(new BigDecimal(POINT_01)) < 0;
		String oldGoodsName = oldVersionDto.getWaybillEntity().getGoodsName();
		boolean isExtraGoods = false;
		//弹性/可收缩货物
		if (oldGoodsName != null) {
			isExtraGoods = oldGoodsName.indexOf("海绵") != -1
					|| oldGoodsName.indexOf("棉花") != -1
					|| oldGoodsName.indexOf("服装") != -1
					|| oldGoodsName.indexOf("布匹") != -1;

		}
		//空运
		boolean isTransAir = ReportConstants.TRANS_AIRCRAFT
				.equals(oldVersionDto.getWaybillEntity().getTransportType());
		//重量减少≥4%,空运：重量误差范围>1kg==>超重
		//重量在25KG以下不做要求
		boolean isOverWeight = (!weightLimit) && (isTransAir ? oldWeight.subtract(newWeight).compareTo(new BigDecimal("1")) >= 0
				: newWeight.divide(oldWeight, NumberConstants.NUMBER_4, BigDecimal.ROUND_HALF_UP)
						.compareTo(new BigDecimal("0.96")) <= 0);
		//体积减少≥10%,弹性/可收缩货物允许误差范围>25%==>超方
		//体积在0.1立方以下不做要求
		boolean isOverVolumn = (!volumnLimit) && (isExtraGoods ? newVolumn.divide(oldVolumn, NumberConstants.NUMBER_4,
				BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal("0.75")) <= 0
				: newVolumn.divide(oldVolumn, NumberConstants.NUMBER_4, BigDecimal.ROUND_HALF_UP)
						.compareTo(new BigDecimal("0.9")) <= 0);
		
		if (!WaybillConstants
				.directDetermineIsExpressByProductCode(newVersionDto
						.getWaybillEntity().getProductCode())) {
				//总运费减少并且超重或者超方
				if (newTransportFee.compareTo(oldTransportFee) < 0
						&& (isOverWeight || isOverVolumn)) {
					//查询运单库存状态
					WaybillStockStatusDto stockStatusDto = waybillRfcService
							.queryWaybillStockStatus(
									waybillRfcEntity.getWaybillNo(),
									newVersionDto.getWaybillEntity());				
					String stockStatus = stockStatusDto.getResult();
					// 判断是否不在收货部门库存中和收货部门已出库的状态
					if (!WaybillRfcConstants.RECEIVE_STOCK.equals(stockStatus)
							&& !WaybillRfcConstants.RECEIVE_STOCK_OUT
									.equals(stockStatus)) {
						// 上报OA差错
						//reportOAError(waybillRfcEntity,newVersionDto.getWaybillEntity(),oldVersionDto.getWaybillEntity());
						// 2015/08/17 liding add start-->
						// QMS系统实现上报
						createReport(waybillRfcEntity,
								newVersionDto.getWaybillEntity(),
								oldVersionDto.getWaybillEntity(), currentInfo, isOverWeight);
						// 2015/08/17 liding add end-->
				}
			}
		}
		//zxy 20131228 MANA-518 end 新增:(不含快递)货物离开出发部门库存后，若起草更改单，涉及到重量体积减小（重量减小超过4%，体积减小超过10%），且造成运费减少，系统自动上报一条超重超方差错，且自动成立。
		//zxy 20140308 DEFECT-1997 end 修改:移动代码段到doAfterVarification之前, 运单号发生变更导致旧运单号在承运信息表查不到数据
		
		
		//5、新增更改历史记录
		WaybillRfcActionHistoryEntity actionHistory = this.getActionHistoryFromWaybillRfcEntity(waybillRfcEntity, currentDate);
		waybillRfcVarificationDao.addWaybillRfcActionHistory(actionHistory);

		handleNewOldWaybillEntity(newVersionDto,oldVersionDto,WaybillRfcConstants.ACCECPT,waybillRfcEntity, currentInfo);
		//更新中转数据
		updateTfrWaybillNo(waybillRfcEntity, newVersionDto,oldVersionDto);
		
		//zxy 20131220 DEFECT-987 start 新增:修改发票标记
		//更新发票标记、是否大票货
		
		//处理更改单的时候修改过运单号  导致运单号不一致，在运费承运信息运单号更新之前，使用新运单号查询承运信息，查询不到数据的问题
		String wayBillNo = null;
		if(!StringUtils.equals(oldVersionDto.getWaybillEntity().getWaybillNo(), newVersionDto.getWaybillEntity().getWaybillNo())){
			wayBillNo = oldVersionDto.getWaybillEntity().getWaybillNo();
		}else{
			wayBillNo = newVersionDto.getWaybillEntity().getWaybillNo();
		}
		
		//zxy 20131220 DEFECT-987 start 新增:修改发票标记
		//更新发票标记、是否大票货
		updateInvoice(wayBillNo,waybillRfcEntity.getId());
		//处理更改单的时候修改过运单号  导致运单号不一致，在运费承运信息运单号更新之前，使用新运单号查询承运信息，查询不到数据的问题
		//zxy 20131220 DEFECT-987 end 新增:修改发票标记
		
		//如果特殊增值服务标识不为空，且 为（家具送装，建材送装，家电送装）中的一种，则保存家装运单信息（只针对零担）
		if(waybillHomeImpService.isAbledPush(oldVersionDto.getWaybillEntity().getWaybillNo())){
			waybillHomeImpService.saveWaybillHomeImpInfoRfc(waybillRfcEntity);
		}
		
		boolean isSyncToPTP = false;
		//合伙人项目于4.10凌晨上线，4.10前的运单更改不走PTP逻辑
		ConfigurationParamsEntity entityDate = configurationParamsService.queryConfigurationParamsOneByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
		if (entityDate !=null && StringUtils.isNotEmpty(entityDate.getConfValue())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parterOnlineDate = null;
			try {
				parterOnlineDate = sdf.parse(entityDate.getConfValue());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(newVersionDto.getWaybillEntity().getBillTime() != null && newVersionDto.getWaybillEntity().getBillTime().after(parterOnlineDate)){
				isSyncToPTP = true;
			}
		}
		//wutao
		updateChangedActualFreightEntity(wayBillNo,waybillRfcEntity.getId());
		//因为合伙人需要使用到货物当前库存状态，但是在下面（7、财务、库存状态处理）在作废审核时会将有效的库存记录置为“N” 2016年4月19日 11:06:51 葛亮亮
		WaybillStockStatusDto stockStatusDto = waybillRfcService.queryWaybillStockStatus(newVersionDto.getWaybillEntity().getWaybillNo(), newVersionDto.getWaybillEntity());
		
		//wutao == end
		//7、财务、库存状态处理
		doAfterVarification(waybillRfcEntity, newVersionDto,oldVersionDto, currentInfo, isSyncToPTP);
		//8、件数增加操作
		doWhenPiecesChange(waybillRfcEntity, newVersionDto,oldVersionDto, currentInfo);
		//9、货物名称修改操作----DMANA-8928 FOSS开单品名自动匹配货源品类-------206860
		doWhenGoodsNameChange(waybillRfcEntity, newVersionDto,oldVersionDto, currentInfo);
		// liding add for NCI
		// 开单付款方式为银行卡的,或者更改为银行卡的,进行代刷卡信息处理
		// 这里判断是否进行代刷卡处理逻辑越加越多,大部分需求后来加的,有必要抽象出一个方法
		boolean isCreateWaybillCardPay = WaybillConstants.CREDIT_CARD_PAYMENT
				.equals(oldVersionDto.getWaybillEntity().getPaidMethod());
		boolean isChangeWaybillCardPay = WaybillConstants.CREDIT_CARD_PAYMENT
				.equals(newVersionDto.getWaybillEntity().getPaidMethod());
		// 更改类型
		String rfcType = waybillRfcEntity.getRfcType();
		// NCI 运单中止优化需求-20160523 liding mod
//		// 中止时不对代刷卡进行处理
//		boolean isAbort = WaybillRfcConstants.ABORT.equals(rfcType);
		// 更改为作废或者中止(NCI优化需求中止和作废调用结算同一接口)
		boolean isInvalidOrisAbort = WaybillRfcConstants.INVALID
				.equals(rfcType) || WaybillRfcConstants.ABORT.equals(rfcType);
		//菜鸟运单作废、中止完整率优化--zoushengli
		if(isInvalidOrisAbort&& oldVersionDto.getWaybillEntity().getIsExpress().equals("Y")){
			waybillManagerService.addSynTracks(oldVersionDto);
		}
		// 是否是PDA开单
		// 根据运输性质设置是否快递字段的值
		boolean isExpressWaybill = false;
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(
				oldVersionDto.getWaybillEntity().getProductCode(),
				oldVersionDto.getWaybillEntity().getBillTime())) {
			 isExpressWaybill = true;
		}
		boolean isPdaWaybill = StringUtils.equals(WaybillConstants.PDA_ACTIVE,
				oldVersionDto.getWaybillEntity().getPendingType()) && isExpressWaybill;
		if(!isExpressWaybill){
			//给司机发送短信
			sendSmsToDriver(waybillRfcEntity,newVersionDto,oldVersionDto,currentInfo);	
		}
		//是否更改运单号 2016年6月12日 11:38:51 葛亮亮
		boolean isChangeWaybillNo = false;
		if(null != oldVersionDto.getWaybillEntity().getWaybillNo()){
			isChangeWaybillNo = !StringUtils.equals(
					newVersionDto.getWaybillEntity().getWaybillNo(),
					oldVersionDto.getWaybillEntity().getWaybillNo());
		}
		
		if ((!isPdaWaybill)
				&& (isCreateWaybillCardPay || isChangeWaybillCardPay)) {
			// 付款方式没变时并且总金额无变动时不做处理(作废除外)
			boolean noChangePaidMethod = StringUtils.equals(newVersionDto
					.getWaybillEntity().getPaidMethod(), oldVersionDto
					.getWaybillEntity().getPaidMethod());
			boolean noChangeTotalAmount = oldVersionDto.getWaybillEntity()
					.getTotalFee()
					.compareTo(newVersionDto.getWaybillEntity().getTotalFee()) == 0;
			// 解决到付增加金额与预付减少金额一致时总费用不变的bug
			// 预付金额是否变动
			boolean noChangePrePayAmount = oldVersionDto
					.getWaybillEntity()
					.getPrePayAmount()
					.compareTo(
							newVersionDto.getWaybillEntity().getPrePayAmount()) == 0;
			// 发货人未发生变更
			boolean noChangeDeliverCustomer = StringUtils.equals(oldVersionDto
					.getWaybillEntity().getDeliveryCustomerCode(),
					newVersionDto.getWaybillEntity().getDeliveryCustomerCode());
			// 收货部门未发生变更
			boolean noChangeReceiveOrgCode = StringUtils.equals(oldVersionDto
					.getWaybillEntity().getReceiveOrgCode(), newVersionDto
					.getWaybillEntity().getReceiveOrgCode());
			if ((!isInvalidOrisAbort) && noChangePaidMethod && noChangeTotalAmount
					&& noChangePrePayAmount && noChangeDeliverCustomer
					&& noChangeReceiveOrgCode && !isChangeWaybillNo) {
				// do nothing
			} else {
				// 待刷卡操作
				processWSCWayBill(newVersionDto.getWaybillEntity(),
						oldVersionDto.getWaybillEntity(), waybillRfcEntity,
						isCreateWaybillCardPay, isChangeWaybillCardPay, 
						isChangeWaybillNo);
			}

		}
		
		/**
		 * 电子运单自动审核不生成待办
		 * @author 283250-foss-liuyi
		 * */
		ActualFreightEntity actualFreightEntity=actualFreightService.queryByWaybillNo(waybillRfcEntity.getWaybillNo());
		if(!(isAuto&&actualFreightEntity!=null
				&&WaybillConstants.EWAYBILL.equals(actualFreightEntity.getWaybillType()))){
			//10、待办(根据修改后的流水号生成)
			addToDoActionByWaybillRfcChangeDetail(waybillRfcEntity, newVersionDto,oldVersionDto);
		}
		//11.内部发货类型和工号字段是否发生改变
		doIsDeliveryInemp(wayBillNo,waybillRfcEntity.getId());
		//12.特殊增值服务是否改变 
		doWhenSpecialValueAddedService(wayBillNo,waybillRfcEntity.getId());
		//给发货人发送短信
		sendSmsToDeliverCustomer(waybillRfcEntity,newVersionDto, currentInfo);
		//如果修改了运单号，调用CRM接口重新绑定订单号
		updateCRMOrderNo(waybillRfcEntity, newVersionDto);
	
		//如果老的单是非返单，新单需要返单，需要新增一条记录;如果老的单是返单，新单不是返单，需要删除记录
		updataSignReturnBill(oldVersionDto,newVersionDto);
		
		//调用空运合大票中转接口
		updateAirQueryModifyPickupbillService(oldVersionDto,newVersionDto);
		
		//TODO  这行代码不能提交 !
		//waybillRfcTodoJobService.prepareSendTodo();
		
		//判断是否需要修改正在派送中的运单详情
		makeSureModifyDeliverTaskToGps(waybillRfcEntity, newVersionDto, oldVersionDto);
		
		/**
		 * MANA-1937 系统开发生成客户编码
		 * 026123 2014-03-24
		 */
		procDeliverAndReceiveCustomer(newVersionDto.getWaybillEntity());
		
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(waybillRfcEntity.getOldVersionWaybillId());
  		if(waybillEntity.getProductCode().equals(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ROUND_COUPON_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.PACKAGE)){
		//快递更改单受理数（简单计数）
		businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_ACCEPT_COUNT_WAY, currentInfo);
  		}else{
		//更改单受理数（简单计数）
		businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_ACCEPT_COUNT, currentInfo);
  		}
		//黄伟：结算的接口，用来处理更改单后，如果目的站改变，将改变前的异常变为已处理
		if(!WaybillConstants.directDetermineIsExpressByProductCode(newVersionDto.getWaybillEntity().getProductCode()) && StringUtils.isNotBlank(newCustomerPickUpOrgCode) && !newCustomerPickUpOrgCode.equals(oldCustomerPickUpOrgCode)){
			exceptionProcessService.updateExceptionProcessStatus(wayBillNo);
		}
		
		/**
		 * 定价优化项目--降价返券需求
		 * 
		 * **/
		//只针对精准汽运（长/短途）、精准卡航、精准城运-----更改返券信息---206860
		if(WaybillConstants.LRF_FLIGHT.equals(newVersionDto.getWaybillEntity().getProductCode())
				|| WaybillConstants.SRF_FLIGHT.equals(newVersionDto.getWaybillEntity().getProductCode())
				|| WaybillConstants.TRUCK_FLIGHT.equals(newVersionDto.getWaybillEntity().getProductCode())
				|| WaybillConstants.FSF_FLIGHT.equals(newVersionDto.getWaybillEntity().getProductCode())
				|| WaybillConstants.EC_GOODS.equals(newVersionDto.getWaybillEntity().getProductCode())){
			//只有发货客户存在手机号码时，才做降价返券处理...
			if(StringUtil.isNotBlank(newVersionDto.getWaybillEntity().getDeliveryCustomerMobilephone())){
				//当提货方式不为内部带货时，才做降价返券处理
				if(StringUtil.isNotBlank(newVersionDto.getWaybillEntity().getReceiveMethod())){
					if(!newVersionDto.getWaybillEntity().getReceiveMethod().contains("INNER")){
						makePendingSendCoupon(waybillRfcEntity,newVersionDto,oldVersionDto, currentInfo);
					}
				}
			}
		}else{
			//当运输性质由返券运输性质更改为非返券运输性质时，需判断是否存在待返券信息，若存在，则作废，反之，不处理...
			//返券信息的定义
			PendingSendCouponEntity pendingSendCouponEntity = null;
			//根据原运单号和开单时间查询返券信息
			pendingSendCouponEntity = pendingSendCouponService.queryPendingSendCoupon(oldVersionDto.getWaybillEntity().getWaybillNo(),oldVersionDto.getWaybillEntity().getBillTime());
			if(pendingSendCouponEntity != null){
				//作废当前运单
				pendingSendCouponService.updateSendCouponStatusByID(pendingSendCouponEntity.getId());
			}
		}
		
		//更新转运或者是返货变更目的站信息--206860
		updateRfcTranferEntity(waybillRfcEntity.getId());
		
		ReturnGoodsRequestEntity returnGoodsRequestEntity = 
					returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo(waybillRfcEntity.getWaybillNo());
			if(null!=returnGoodsRequestEntity&&
					returnGoodsRequestEntity.getReturnStatus().equals("HANDLED")&&
					!StringUtils.equals(newVersionDto.getWaybillEntity().getCustomerPickupOrgCode(), 
							oldVersionDto.getWaybillEntity().getCustomerPickupOrgCode())){
				AutoAddCodeByHandEntity entity=new AutoAddCodeByHandEntity();
				entity.setId(UUIDUtils.getUUID());
				entity.setWaybillNo(waybillRfcEntity.getWaybillNo());
				entity.setOperatorCode(FossUserContext.getCurrentUser().getEmpCode());
				entity.setReason("RETURN_FORWARD");
				entity.setOperatorName(FossUserContext.getCurrentUser().getEmpName());
				entity.setCreateTime(new Date());
				autoAddCodeByHandService.insertAddCodeByHand(entity);
				WaybillEntity waybillEntityResult=waybillManagerService.queryWaybillById(waybillRfcEntity.getNewVersionWaybillId());
				WaybillExpressEntity waybillExpress=new WaybillExpressEntity();
				waybillExpress.setWaybillNo(waybillEntityResult.getWaybillNo());
				waybillExpress.setDeliveryCustomerCityCode(waybillEntityResult.getDeliveryCustomerCityCode());
				waybillExpress.setDeliveryCustomerDistCode(waybillEntityResult.getDeliveryCustomerDistCode());
				waybillExpress.setDeliveryCustomerProvCode(waybillEntityResult.getDeliveryCustomerProvCode());
//				waybillExpress.setDeliveryEmployeeCode(waybillEntity.get);
				waybillExpress.setReceiveCustomerAddress(waybillEntityResult.getReceiveCustomerAddress());
				waybillExpress.setReceiveCustomerCityCode(waybillEntityResult.getReceiveCustomerCityCode());
				waybillExpress.setReceiveCustomerCode(waybillEntityResult.getReceiveCustomerCode());
				waybillExpress.setReceiveCustomerContact(waybillEntityResult.getReceiveCustomerContact());
				waybillExpress.setReceiveCustomerDistCode(waybillEntityResult.getReceiveCustomerDistCode());
				waybillExpress.setReceiveCustomerMobilephone(waybillEntityResult.getReceiveCustomerMobilephone());
				waybillExpress.setReceiveCustomerName(waybillEntityResult.getReceiveCustomerName());
				waybillExpress.setReceiveCustomerPhone(waybillEntityResult.getReceiveCustomerPhone());
				waybillExpress.setReceiveCustomerProvCode(waybillEntityResult.getReceiveCustomerProvCode());
				waybillExpress.setReceiveMethod(waybillEntityResult.getReceiveMethod());
				waybillExpress.setReceiveOrgName(waybillEntityResult.getReceiveOrgName());
				waybillExpress.setReceiveOrgCode(waybillEntityResult.getReceiveOrgCode());
				waybillExpress.setReturnType(waybillEntityResult.getReturnType());
//				waybillExpress.setBillTime(billTime);
				waybillExpress.setChangeVolume(waybillEntityResult.getGoodsVolumeTotal().toString());
				waybillExpress.setTargetOrgCode(waybillEntityResult.getTargetOrgCode());
				waybillExpress.setVolumeWeight(waybillEntityResult.getGoodsWeightTotal());
				waybillExpress.setCreateOrgCode(FossUserContext.getCurrentDept().getCode());
				waybillExpress.setCreateTime(new Date());
				waybillExpress.setCreateUser(FossUserContext.getCurrentUser().getEmpCode());
				waybillExpress.setIsAddCode(FossConstants.INACTIVE);
				waybillExpress.setCustomerPickupOrgCode(waybillEntityResult.getCustomerPickupOrgCode());
				waybillExpress.setCustomerPickupOrgName(waybillEntityResult.getCustomerPickupOrgName());
				waybillExpressService.updateWaybillExpressByWaybillNo(waybillExpress);
			}
		//如果是整车就不进入PTP逻辑
		if(isSyncToPTP && newVersionDto.getWaybillEntity() != null && !FossConstants.YES.equals(newVersionDto.getWaybillEntity().getIsWholeVehicle())){
			newVersionDto.setPtpWaybillDto(waybillRfcEntity.getPtpWaybillDto());
			//2016年4月19日 11:44:19 葛亮亮
			if(null == newVersionDto.getPtpWaybillDto()){
				newVersionDto.setPtpWaybillDto(new PtpWaybillDto());
			}	
			//设置库存状态信息 2016年4月19日 11:44:52 葛亮亮
			if(stockStatusDto!=null){
				newVersionDto.getPtpWaybillDto().setWaybillStock(stockStatusDto.getResult());
			}
			handlePartnerInfo(newVersionDto, oldVersionDto, new Date(), currentInfo,isParnterAcceptStatus);
		}
		VestBatchResult vestResult = this.getVestResult(oldVersionDto.getWaybillEntity(), "agreeWaybillRfcOpinionPure");
		//推送零担更改单相关信息给CUBC
		if(Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
			//最低一票费用处理
			if (null != newVersionDto.getActualFreightEntity() && null != newVersionDto.getActualFreightEntity().getMinTransportFee()) {
				BigDecimal newMinTransportFee = newVersionDto.getActualFreightEntity().getMinTransportFee().divide(new BigDecimal(100));
				newVersionDto.getActualFreightEntity().setMinTransportFee(newMinTransportFee);
			}
		    waybillInfoToCUBCService.asyncSendWaybillRfcInfoToCUBC(waybillRfcEntity, newVersionDto);
		};
		
	}
	
	/**
	 * 调用ESB的接口上报
	 * */
	@Transactional
	private void createReport(WaybillRfcEntity waybillRfcEntity,WaybillEntity newWaybillEntity,WaybillEntity oldWaybillEntity,CurrentInfo currentInfo,boolean isOverWeight) {
		try{
			//差错上报主信息
			ErrorMainEntity mainInfo = new ErrorMainEntity();
			//运单号 
			mainInfo.setWayBillNum(waybillRfcEntity.getWaybillNo());
			//差错类型id
			mainInfo.setErrorTypeId(ERR_TYPE_ID);
			//差错类别
			mainInfo.setErrorCategory(ReportConstants.BUSINESSLTL);
			//文件标准id
			if(isOverWeight){
				mainInfo.setDocStandarId(OVERWEIGHT_DOC_ID);
			} else {
				mainInfo.setDocStandarId(OVERVOLUMN_DOC_ID);
			}
			//上报人工号
			mainInfo.setRepEmpcode(currentInfo.getEmpCode());
			//上报人姓名
			mainInfo.setRepEmpName(currentInfo.getEmpName());
			//上报人职位
			mainInfo.setRepEmpJob(currentInfo.getUser() == null ? "" : currentInfo.getUser().getTitle());
			//上报人部门标杆编码
			mainInfo.setRepDeptCode(this.getOrgByCode(newWaybillEntity.getReceiveOrgCode(),false));
			//收货部门标杆编码
			mainInfo.setReceiveDeptCode(this.getOrgByCode(newWaybillEntity.getReceiveOrgCode(),false));
			//收货部门名称 
			mainInfo.setReceiveDeptName(newWaybillEntity.getReceiveOrgName());
			//查询责任事业部
			List<String> orgTypeLst = new ArrayList<String>();
			orgTypeLst.add(BizTypeConstants.ORG_DIVISION);	//事业部类型
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(waybillRfcEntity.getDraftOrgCode(),orgTypeLst);
			if(orgEntity != null) {
				//上报人事业部标杆编码
				mainInfo.setRepDivisionCode(orgEntity.getCode());
				//上报人事业部名称
				mainInfo.setRepDivisionName(orgEntity.getName());
			}
			
			ErrRequestParam repParam = new ErrRequestParam();
			//运单号存在
			if(StringUtil.isNotEmpty(waybillRfcEntity.getWaybillNo())){
				LDErrSubHasWaybillEntity subInfo = new LDErrSubHasWaybillEntity();
				//运输类型
				subInfo.setTransType(this.getDictionaryValue
									(ReportConstants.TRANS_TYPE,newWaybillEntity.getTransportType()));
				//返单类型
				subInfo.setReturnBillType(this.getDictionaryValue
									(ReportConstants.RETURNBILLTYPE,newWaybillEntity.getReturnBillType()));
				//托运人
				subInfo.setShipper(newWaybillEntity.getDeliveryCustomerName());
				//运输性质
				subInfo.setTransNature(this.getProductName(newWaybillEntity.getProductCode()));
				//收货人电话(优先给手机号)
				if(StringUtils.isNotBlank(newWaybillEntity.getReceiveCustomerMobilephone())){
					subInfo.setReceiverPhone(newWaybillEntity.getReceiveCustomerMobilephone());
				}else{
					subInfo.setReceiverPhone(newWaybillEntity.getReceiveCustomerPhone());
				}
				//提货方式
				if(ReportConstants.TRANS_AIRCRAFT.equals(newWaybillEntity.getTransportType())){
					//如果运输类型为空运
					//提货方式
					subInfo.setPickUpType(this.getDictionaryValue
									(ReportConstants.PICKUPGOODSAIR,newWaybillEntity.getReceiveMethod()));
				}else{
					//如果不为空运
					//提货方式
					subInfo.setPickUpType(this.getDictionaryValue
									(ReportConstants.PICKUPGOODSHIGHWAYS,newWaybillEntity.getReceiveMethod()));
				}
				//重量(重量为开单时重量)
				subInfo.setSumWeight(String.valueOf(oldWaybillEntity.getGoodsWeightTotal()));
				//体积(体积为开单时体积)
				subInfo.setSumVolume(String.valueOf(oldWaybillEntity.getGoodsVolumeTotal()));
				//件数
				subInfo.setNum(String.valueOf(newWaybillEntity.getGoodsQtyTotal()));
				//货物名称
				subInfo.setGoodsName(newWaybillEntity.getGoodsName());
				//发货时间
				subInfo.setSendGoodsTime(DateUtils.convert(newWaybillEntity.getPreDepartureTime()));
				//目的站
				//到达部门(即提货网点，设置为标杆编码)
				subInfo.setArriveDeptCode(this.getOrgByCode(newWaybillEntity.getCustomerPickupOrgCode(),false));
				//到达部门名称
				subInfo.setArriveDeptName(newWaybillEntity.getCustomerPickupOrgName());
				//收货人
				subInfo.setReceiverName(newWaybillEntity.getReceiveCustomerName());
				//收货部门标杆编码
				subInfo.setTakeOverDeptCode(this.getOrgByCode(newWaybillEntity.getReceiveOrgCode(),false));
				//收货部门名称 
				subInfo.setTakeOverDeptName(newWaybillEntity.getReceiveOrgName());
				//付款方式
				subInfo.setPayType(this.getDictionaryValue
									(ReportConstants.SETTLEMENT__PAYMENT_TYPE,newWaybillEntity.getPaidMethod()));
				//保险金额
				subInfo.setSafeMoney(String.valueOf(newWaybillEntity.getInsuranceFee()));
				//货物包装
				subInfo.setGoodsPackage(newWaybillEntity.getGoodsPackage());
				//运费总额
				subInfo.setFreightSumFee(String.valueOf(newWaybillEntity.getTotalFee()));
				//开单部门
				//开单部门(设置为标杆编码)
				subInfo.setBillingDeptCode(this.getOrgByCode(newWaybillEntity.getCreateOrgCode(),false));
				//开单部门名称
				subInfo.setBillingDeptName(this.getOrgByCode(newWaybillEntity.getCreateOrgCode(),true));
				//司机工号
				subInfo.setDriverCode(newWaybillEntity.getDriverCode());
				//通过司机工号查询姓名
				EmployeeEntity empEntity = employeeService.queryEmployeeByEmpCode(newWaybillEntity.getDriverCode());
				if(empEntity != null){
					subInfo.setDriverName(empEntity.getEmpName());//得到员工信息
				}
				
				//将运单号封装成运单集合
				List<String> waybillList =new  ArrayList<String>();
				waybillList.add(newWaybillEntity.getWaybillNo());
				//查询运单详情
				List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfoForQms(waybillList);
				if(waybillInfoDtoList!=null && waybillInfoDtoList.size()!=0){
					//司机所在部门编号
					subInfo.setDriverDeptCode(waybillInfoDtoList.get(0).getDriverOrgCode());
				    //司机所在部门名称
					subInfo.setDriverDeptName(waybillInfoDtoList.get(0).getDriverOrgName());
				}
				
				//总件数
				subInfo.setSumNumber(String.valueOf(newWaybillEntity.getGoodsQtyTotal()));
				//实际重量
				subInfo.setActualWeight(String.valueOf(newWaybillEntity.getGoodsWeightTotal()));
				//实际体积
				subInfo.setActualVolume(String.valueOf(newWaybillEntity.getGoodsVolumeTotal()));
				subInfo.setBillingTime(DateUtils.convert(newWaybillEntity.getBillTime()));
				//责任部门名称
				subInfo.setRespDeptName(currentInfo.getCurrentDeptName());
				//根据部门编号查询部门信息
				OrgAdministrativeInfoEntity org= 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillRfcEntity.getDraftOrgCode());
				if(org != null){
					//责任部门(起草反签收人所在部门为责任部门，设置为标杆编码)
					subInfo.setRespDeptCode(org.getUnifiedCode());
					if(StringUtils.isBlank(org.getPrincipalNo())){
						//查询责任人及其工号
						org = this.getResponsible(org);
					}
					//责任人工号
					subInfo.setRespEmpCode(org.getPrincipalNo());
					EmployeeEntity emp = employeeService.querySimpleEmployeeByEmpCode(org.getPrincipalNo());
					if(emp != null){
						//责任人姓名
						subInfo.setRespEmpName(emp.getEmpName());
					}
					//短消息通知对象(责任人+责任人上级)
					String shortMessageCodes = subInfo.getRespEmpCode();
					String shortMessageNames = subInfo.getRespEmpName();
					//如果责任部门是大区，则不再往上查责任人上级
					if(!FossConstants.YES.equals(org.getBigRegion())){
						//查询责任人的上级部门(即上级部门负责人),根据部门标杆编号查询部门信息
						OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoService.
									queryOrgAdministrativeInfoByUnifiedCodeClean(org.getParentOrgUnifiedCode());
						if(parentOrg != null ){
							//短信通知对象工号(责任人、责任人上级)
							shortMessageCodes = shortMessageCodes + "," + parentOrg.getPrincipalNo();
							EmployeeEntity parentEmp = employeeService.querySimpleEmployeeByEmpCode(parentOrg.getPrincipalNo());
							if(parentEmp != null){
								//短信通知对象名字(责任人、责任人上级)
								shortMessageNames = shortMessageNames + "," + parentEmp.getEmpName();
							}
						}
					}
					subInfo.setShortMessageCodes(shortMessageCodes);
					subInfo.setShortMessageNames(shortMessageNames);
				}
				//储运事项
				subInfo.setStorageTransport(newWaybillEntity.getTransportationRemark());
				//纯运费
				subInfo.setPureFee(String.valueOf(newWaybillEntity.getTransportFee()));
				//送货费
				subInfo.setDeliveryFee(String.valueOf(newWaybillEntity.getDeliveryGoodsFee()));
				//是否集中接货
				boolean isConcentReceiving = FossConstants.YES.equals(newWaybillEntity.getPickupCentralized());
				if(isConcentReceiving){
					subInfo.setIsConcentReceiving(FossConstants.YES);
				}else{
					subInfo.setIsConcentReceiving(FossConstants.NO);
				}
				//填开人工号
				subInfo.setFillopenEmpCode(newWaybillEntity.getCreateUserCode());
				
				EmployeeEntity emp2 = employeeService
						.queryEmployeeByEmpCode(newWaybillEntity
								.getCreateUserCode());
				if (emp2 != null) {
					// 填开人姓名
					subInfo.setFillopenEmpName(emp2.getEmpName());
				}

				//零担有单
				repParam.setLdsubHasInfo(subInfo);
			} else {
				LDErrSubNoWaybillEntity subInfo = new LDErrSubNoWaybillEntity();
				//零担无单
				repParam.setLdsubNoInfo(subInfo);
			}
			// 业务类别
			repParam.setErrCategoty(ReportConstants.BUSINESSLTL);
			// 差错类型id
			repParam.setErrorTypeId(ERR_TYPE_ID);
			repParam.setReturnResult(true);
			repParam.setMainInfo(mainInfo);
			
			//转换成json格式
			String jsonParam = JSONObject.toJSONString(repParam); 
			LOG.info(jsonParam);
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setContentCharset("UTF-8");
			//构造PostMethod的实例
			PostMethod postMethod = new PostMethod(QMSAutoReportUrl);  
			RequestEntity entity = new StringRequestEntity(jsonParam,"application/json","UTF-8");
			postMethod.setRequestEntity(entity);
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			// 执行postMethod
			httpClient.executeMethod(postMethod);
			//接口返回信息
			String responseBody = postMethod.getResponseBodyAsString();
			LOG.debug(responseBody);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 根据组织编码，得到组织标杆编码或组织名称
	 * @param 273279-FOSS-Liding
	 * @date 2015-8-12 上午9:31:26
	 * @return
	 */
	public String getOrgByCode(String code,boolean isResultName){
		if(code != null){
			//根据部门编号查询部门信息
			OrgAdministrativeInfoEntity org= 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			if(org != null){
				if(isResultName){
					return org.getName();
				}else{
					return org.getUnifiedCode();
				}
			}
		}
		return null;
	}
	
	/**
	 * 通过当前部门，查询负责人工号和名字
	 * @param 231434-FOSS-bieyexiong
	 * @date 2015-5-8 下午4:01:21
	 */
	public OrgAdministrativeInfoEntity getResponsible(OrgAdministrativeInfoEntity org){
		int i = 0;
		OrgAdministrativeInfoEntity orgParent = null;
		if(org != null){
			//当前部门负责人为空，且当前部门不为大区(为了防止死循环，限制查询6次)
			while(StringUtils.isBlank(org.getPrincipalNo()) && !FossConstants.YES.equals(org.getBigRegion())
					&& i<NumberConstants.NUMERAL_SIX){
				//根据上级组织标杆编码查询上级组织
				orgParent = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByUnifiedCodeClean(org.getParentOrgUnifiedCode());
				//上级组织为空
				if(orgParent == null){
					return org;
				}else if((orgParent != null && !StringUtils.isBlank(orgParent.getPrincipalNo()))
						|| FossConstants.YES.equals(orgParent.getBigRegion())){
					//上级组织不为空，且上级组织负责人不为空,或者上级组织是大区

					//将上级组织负责人工号填入当前组织
					org.setPrincipalNo(orgParent.getPrincipalNo());
					return org;
				}
				//将上级组织的上级标杆编码 填入 当前组织的上级标杆编码
				org.setParentOrgUnifiedCode(orgParent.getParentOrgUnifiedCode());
				i++;
			}
		}
		return org;
	}
	
	
	/**
	 * 将数据字典内的值代码换成名称
	 * @param 273279-FOSS-liding
	 * @date 2015-9-9 上午11:34:13
	 * @return
	 */
	public String getDictionaryValue(String termsCode,String signSituation){
		//根据词条名和值编码，查询数据字典
		DataDictionaryValueEntity dictionary = 
				dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode
									(termsCode,signSituation);
		return dictionary == null ? null : dictionary.getValueName();
	}
	
	/**
	 * 根据产品编码 查询 产品名称
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-27 下午16:26:21
	 */
	public String getProductName(String productCode){
		//根据CODE查询产品实体集合
		List<ProductEntity> entitys = productDao.queryProductsByCode(productCode);
		if(entitys != null && entitys.size()>ArriveSheetConstants.ZERO){
			ProductEntity entity = entitys.get(ArriveSheetConstants.ZERO);
			return entity == null ? null : entity.getName();
		}
		return null;
	}
	
	
	/**
	 * 更新转运或者是返货变更目的站信息
	 * @author foss-206860
	 * */
	private void updateRfcTranferEntity(String waybillRfcId) {
		//根据waybillRfcId查询运单更改中转表数据
		WaybillRfcTranferEntity waybillRfcTranferEntity = new WaybillRfcTranferEntity();
		waybillRfcTranferEntity.setWaybillRfcId(waybillRfcId);
		//根据最新ID获取单号和货物范围
		List<WaybillRfcTranferEntity> rfcTranferList = waybillRfcService.queryWaybillRfcTransferEntity(waybillRfcTranferEntity);
		//根据现有效数据查询出的单号和货物范围设置状态为N
		if(CollectionUtils.isNotEmpty(rfcTranferList)){
			for (int i = 0; i < rfcTranferList.size(); i++) {
				waybillManagerService.updateRfcTranferEntity(rfcTranferList.get(i));
			}
		}
		//将当前waybillRfcId更新状态active=“Y”
		waybillManagerService.updateRfcTranferEntity(waybillRfcTranferEntity);
	}
	
	/**
	 * 更改降价返券信息
	 * 根据新的运单信息做降价返券处理
	 * 
	 * 若需要返券 判断当前运单号的返券信息是否在待返券信息表中
	 * --若无 则插入一条返券信息到待返券数据表中  --若有 则更新 相关信息（比如 运单号 发货人手机号码  返券金额 修改时间）
	 * 
	 * 若不需要返券 判断当前运单号的返券信息是否在待返券信息表中
	 * --若无 则不处理--- 若有 判断是否已经返券 若已经返券 则不处理 若没有返券 将信息更新不返券
	 * 
	 * @author Foss-206860
	 * */
	private void makePendingSendCoupon(WaybillRfcEntity waybillRfcEntity,
			WaybillDto newVersionDto,WaybillDto oldVersionDto, CurrentInfo currentInfo) {
		//当公布，才处理降价返券
//		if(FossConstants.YES.equals(waybillRfcEntity.getIsCalTraFee())){
		//若金额变化，则将新的最低一票赋值给WaybillDto对象中的运单实体中的最低一票属性
		if(waybillRfcEntity.getMinTransportFee() == null){
			waybillRfcEntity.setMinTransportFee(BigDecimal.ZERO);
		}
		newVersionDto.getWaybillEntity().setMinTransportFee(waybillRfcEntity.getMinTransportFee());
		//为了计算价格，获取的实体类
		QueryBillCacilateDto queryBillCacilateDto = waybillRfcEntity.getQueryBillCacilateDto();
		if(queryBillCacilateDto != null){
			//根据信息的运单信息做降价返券处理(逻辑处理和开单一样)
			waybillManagerService.savePendingSendCoupon(queryBillCacilateDto,newVersionDto,oldVersionDto,currentInfo,FossConstants.NO,waybillRfcEntity.getIsChangeWaybillNo());
		}
//		}
	}
	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类
	 * 货物名称修改修改实际承运表
	 * @author Foss-206860
	 * */
	private void doWhenGoodsNameChange(WaybillRfcEntity rfcEntity,
			WaybillDto newVersionDto, WaybillDto oldVersionDto,
			CurrentInfo currentInfo) {
		//获取运单号
		String waybillNo = rfcEntity.getWaybillNo();
		//根据最新运单ID获取变更后的运单实体
		WaybillEntity newVersionEntity = waybillManagerService.queryWaybillById(rfcEntity.getNewVersionWaybillId());	
		//判断是否改变了运单号
		if(FossConstants.YES.equals(rfcEntity.getIsChangeWaybillNo())){
			waybillNo = newVersionEntity.getWaybillNo();
		}
		//根据运单号获取实际承运表数据
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		//根据货物名称匹配综合基础资料，获取行业货源品类
		String industrySourceCategory = waybillManagerService.queryIndustrySourceCategory(newVersionEntity.getGoodsName());
		actualFreightEntity.setIndustrySourceCategory(industrySourceCategory);
		//修改实际承运表数据
		actualFreightService.updateWaybillActualFreight(actualFreightEntity);
	}
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * @param wayBillNo
	 * @param waybillRfcId
	 */
	private void doIsDeliveryInemp(String wayBillNo, String waybillRfcId) {
		
		// 获取变更项列表
		List<WaybillRfcChangeDetailEntity> rfcChangeDetailLst = waybillRfcDao.queryRfcChangeDetail(waybillRfcId);
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(wayBillNo);
		boolean isOk = false;
		if (CollectionUtils.isNotEmpty(rfcChangeDetailLst)) {
			for (int i = 0; i < rfcChangeDetailLst.size(); i++) {
				WaybillRfcChangeDetailEntity detailEntity = rfcChangeDetailLst
						.get(i);
				// 如果变更项是发货类型，则更新实际承运信息
				if (WaybillConstants.CHANGER_ITEM_DELIVERY_TYPE.equals(detailEntity.getRfcItems())) {
					String newValue = detailEntity.getAfterRfcInfo();
					if(WaybillConstants.DELIVERY_PAY_CH.equals(newValue)) {
						newValue = WaybillConstants.DELIVERY_PAY;
					}
					if(WaybillConstants.RECIVE_PAY_CH.equals(newValue)) {
						newValue = WaybillConstants.RECIVE_PAY;
					}
					if(WaybillConstants.SPLIT.equals(newValue)) {
						newValue = "";
					}
					actualFreightEntity.setInternalDeliveryType(newValue);
					isOk = true;

				}
				// 如果变更项是工号
				if (WaybillConstants.CHANGER_ITEM_EMPLOYEE.equals(detailEntity.getRfcItems())) {
					String newValue = detailEntity.getAfterRfcInfo();
					if(WaybillConstants.SPLIT.equals(newValue)) {
						newValue = "";
					}
					actualFreightEntity.setEmployeeNo(newValue);
					isOk = true;

				}
			}
			if (isOk) {
				actualFreightService
						.updateWaybillActualFreight(actualFreightEntity);
			}
		}
	}
	/**
	 * 更改单审核特殊增值服务和特殊提货方式
	 * @param wayBillNo
	 * @param waybillRfcId
	 */
	private void doWhenSpecialValueAddedService(String wayBillNo, String waybillRfcId) {
		// 获取变更项列表
				List<WaybillRfcChangeDetailEntity> rfcChangeDetailLst = waybillRfcDao
						.queryRfcChangeDetail(waybillRfcId);
				ActualFreightEntity actualFreightEntity = actualFreightService
						.queryByWaybillNo(wayBillNo);
				boolean isOk = false;
				if (rfcChangeDetailLst != null) {
					for (int i = 0; i < rfcChangeDetailLst.size(); i++) {
						WaybillRfcChangeDetailEntity detailEntity = rfcChangeDetailLst
								.get(i);
						// 如果变更项是特殊增值服务，则更新实际承运信息
						if (WaybillConstants.CHANGER_ITEM_SPECIALVALUEADDEDSERVICE_TYPE.equals(detailEntity.getRfcItems())) {
							String newValue = detailEntity.getAfterRfcInfo();
							if(WaybillConstants.BUILD_MATERIAL_EQUIP_CH.equals(newValue)) {
								newValue = WaybillConstants.BUILD_MATERIAL_EQUIP;
							}
							if(WaybillConstants.HOME_APPLICATION_EQUIP_CH.equals(newValue)) {
								newValue = WaybillConstants.HOME_APPLICATION_EQUIP;
							}
							if(WaybillConstants.FURNITURE_EQUIP_CH.equals(newValue)){
								newValue= WaybillConstants.FURNITURE_EQUIP;
							}
							if(WaybillConstants.SPLIT.equals(newValue)) {
								newValue = "";
							}
							actualFreightEntity.setSpecialValueAddedServiceType(newValue);
							isOk = true;

						}
					/*	//如果是提货方式，则更新提货方式
						if ("receiveMethod".equals(detailEntity.getRfcItems())) {
							isOk = true;
							String newValue = detailEntity.getAfterRfcInfo();
							if(WaybillConstants.SEND_AND_EQUIP_CH.equals(newValue)){
								newValue=WaybillConstants.SEND_AND_EQUIP;
							}else if(WaybillConstants.SEND_UPSTAIRS_EQUIP_CH.equals(newValue)){
								newValue=WaybillConstants.SEND_UPSTAIRS_EQUIP;
							}else if(WaybillConstants.SEND_NO_UPSTAIRS_CH.equals(newValue)){
								newValue=WaybillConstants.SEND_NO_UPSTAIRS;
							}else if(WaybillConstants.SPLIT.equals(newValue)) {
								newValue = "";
							}else{
								isOk = false;
							}
							actualFreightEntity.setActualDeliverType(newValue);
						}*/
					}
					if (isOk) {
						actualFreightService
								.updateWaybillActualFreight(actualFreightEntity);
					}
				}
	
	}
	
	
	
	/**
	 * 处理发货客户与收货客户生成编码优化
	 * @创建时间 2014-5-30 下午5:05:08   
	 * @创建人： WangQianJin
	 */
	private void procDeliverAndReceiveCustomer(WaybillEntity waybillEntity){
		
		//付款方式为现金、到付的新客户开单时调用综合接口生成散客信息
//		if(WaybillConstants.CASH_PAYMENT.equals(waybillEntity.getPaidMethod()) || WaybillConstants.ARRIVE_PAYMENT.equals(waybillEntity.getPaidMethod())){
			Log.debug("***************生成发货散客信息***************");
			createDeliveryCustomer(waybillEntity,DictionaryValueConstants.CUSTOMER_SC_CUSTOMER);			
			
			//如果不是偏线和空运和快递，则生成收货客户信息
//			if(!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())
//					&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())
//					&& !ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybillEntity.getProductCode())){
				Log.debug("***************生成收货散客信息***************");
				createReceiveCustomer(waybillEntity,DictionaryValueConstants.CUSTOMER_SC_CUSTOMER);
//			}			
//		}
		
		//发货潜客已开单
		if(StringUtil.isNotEmpty(waybillEntity.getDeliveryCustomerCode())){
			CustomerDto customerDto = queryCustomerService.queryCustInfoByCode(waybillEntity.getDeliveryCustomerCode());
			if(customerDto!=null && DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(customerDto.getCustomserNature())){
				Log.debug("***************生成发货潜客信息***************");
				createDeliveryCustomer(waybillEntity,DictionaryValueConstants.CUSTOMER_PC_CUSTOMER);
			}
		}
		//收货潜客已开单
		if(StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerCode())){
			CustomerDto customerDto = queryCustomerService.queryCustInfoByCode(waybillEntity.getReceiveCustomerCode());
			if(customerDto!=null && DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(customerDto.getCustomserNature())){
				Log.debug("***************生成收货潜客信息***************");
				createReceiveCustomer(waybillEntity,DictionaryValueConstants.CUSTOMER_PC_CUSTOMER);
			}
		}
		
	}
	
	/**
	 * 封装运单发货客户信息
	 * MANA-1937 系统开发生成客户编码
	 * 026123 2014-03-24
	 */
	private void createDeliveryCustomer(WaybillEntity entity,String type){
		//非空判断
		if(null == entity){
			throw new WaybillSubmitException("运单提交失败：运单基本信息为空！");
		}
		
		//是否生成客户
		boolean isCreate = false;
		
		//发货客户编码是否为空
		if(StringUtil.isNotEmpty(entity.getDeliveryCustomerCode())){
			//若有客户编码，则不需要生成新客户
			isCreate = false;
		}
		// 再次判断下手机或者电话能否查询出对应的客户信息（防止出现“开单选客户时没有客户但提交前却生成了客户的信息”）
		else{
			//封装查询条件 
			CustomerQueryConditionDto queryParam = new CustomerQueryConditionDto();			
			//手机号码是否为空
			if(StringUtil.isNotEmpty(entity.getDeliveryCustomerMobilephone())){
				queryParam.setMobilePhone(entity.getDeliveryCustomerMobilephone());
			}else{
				queryParam.setContactPhone(entity.getDeliveryCustomerPhone());
				queryParam.setContactName(entity.getDeliveryCustomerContact());
			}
			//如果手机号与固定电话都为空，则不允许提交
			if(StringUtils.isEmpty(queryParam.getMobilePhone()) && StringUtils.isEmpty(queryParam.getContactPhone())){
				throw new WaybillSubmitException("发货人手机号与固定电话不允许全部为空！");
			}
			//查询匹配记录
			ContactEntity dto=cusContactService.queryCusContactByMobileOrPhoneAndName(queryParam);
			//若匹配成功，则不生成
			if(dto!=null && (dto.getCustomerCode()!=null || dto.getOwnCustId()!=null)){
				//根据crmCusId获取客户信息
				CustomerEntity cus=queryCustomerService.queryCustomerByCrmCusId(dto.getCustomerCode(),dto.getOwnCustId());
				if(cus!=null && StringUtil.isNotEmpty(cus.getCusCode())){
					entity.setDeliveryCustomerCode(cus.getCusCode());
					isCreate = false;
				}else{
					isCreate = true;
				}	
			}else{
				isCreate = true;
			}
		}
		
		//如果是潜客的话，则需要调用综合接口标记潜客已开单
		if(DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(type)){
			isCreate=true;
		}
		
		//判断是否需要生成客户
		if(isCreate){
			//定义客户信息对象
			NonfixedCustomerAssociatedInformationEntity customer = new NonfixedCustomerAssociatedInformationEntity();
			
			//客户编码
			customer.setCustomerCode(entity.getDeliveryCustomerCode());
			//客户名称
			customer.setCustomerName(entity.getDeliveryCustomerName());
			//联系人名称
			customer.setLinkManName(entity.getDeliveryCustomerContact());
			//联系人编码
			customer.setLinkManCode(entity.getDeliverCustContactId());
			//客户固定电话
			customer.setMobile(entity.getDeliveryCustomerPhone());
			//客户省
			customer.setProCode(entity.getDeliveryCustomerProvCode());
			//客户市
			customer.setCityCode(entity.getDeliveryCustomerCityCode());
			//客户区县
			customer.setCountyCode(entity.getDeliveryCustomerDistCode());
			//地址
			customer.setAddress(entity.getDeliveryCustomerAddress());
			//客户手机
			customer.setCellPhone(entity.getDeliveryCustomerMobilephone());
			//业务类别(零担和快递)
			if(waybillExpressService.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())){
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_EXPRESS);
			}else{
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_LTT);
			}			
			//客户所属部门标杆编码
			String unifiedCode = null;
			OrgAdministrativeInfoEntity o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getReceiveOrgCode());
			if(null != o){
				unifiedCode = o.getUnifiedCode();
			}
			customer.setUnifiedCode(unifiedCode);
			//出发客户
			customer.setCustomerAttributes(DictionaryValueConstants.LEAVE_CUSTOMER_ATTRIBUTE);
			//客户性质（潜客OR散客）
			customer.setCustType(type);
			//调用综合接口生成发货客户信息
			NonfixedCustomerAssociatedInformationEntity nonCus=synNonfixedCustomerToCrmSerivce.createNonfixedCustomer(customer);
			//如果是散客，需要将客户编码反写到运单中
			if(DictionaryValueConstants.CUSTOMER_SC_CUSTOMER.equals(type)){
				if(nonCus!=null && nonCus.getCustomerCode()!=null){					
					//为收货客户判断用
					entity.setDeliveryCustomerCode(nonCus.getCustomerCode());
				}else{
					Log.debug("***************更改单生成发货客户信息---综合未生成客户编码***************"+entity.getWaybillNo());
				}
			}			
		}
		//更新客户编码
		WaybillEntity waybillEntity=new WaybillEntity();
		waybillEntity.setDeliveryCustomerCode(entity.getDeliveryCustomerCode());
		waybillEntity.setId(entity.getId());		
		waybillRfcVarificationDao.updateCustomerCodeByWaybillId(waybillEntity);
	}
	
	/**
	 * 封装运单收货客户信息
	 * MANA-1937 系统开发生成客户编码
	 * 026123 2014-03-24
	 */
	private void createReceiveCustomer(WaybillEntity entity,String type){
		//非空判断
		if(null == entity){
			throw new WaybillSubmitException("运单提交失败：运单基本信息为空！");
		}
		
		//是否生成客户
		boolean isCreate = false;
		
		//收货客户编码是否为空
		if(StringUtil.isNotEmpty(entity.getReceiveCustomerCode())){
			//若有客户编码，则不需要生成新客户
			isCreate = false;
		}
		// 再次判断下手机或者电话能否查询出对应的客户信息（防止出现“开单选客户时没有客户但提交前却生成了客户的信息”）
		else{
			//封装查询条件 
			CustomerQueryConditionDto queryParam = new CustomerQueryConditionDto();
			//是否和发货客户是同一客户
			boolean flag=false;
			//手机号码是否为空
			if(StringUtil.isNotEmpty(entity.getReceiveCustomerMobilephone())){
				//有手机，则只根据手机查询
				queryParam.setMobilePhone(entity.getReceiveCustomerMobilephone());
				//发货人手机和收货人手机相同
				if(StringUtil.isNotEmpty(entity.getReceiveCustomerMobilephone()) && entity.getReceiveCustomerMobilephone().equals(entity.getDeliveryCustomerMobilephone())){
					flag=true;
				}
			}else{
				//根据电话号码和联系人查询
				queryParam.setContactPhone(entity.getReceiveCustomerPhone());
				queryParam.setContactName(entity.getReceiveCustomerContact());
				//发货人电话与联系人和收货人电话与联系人相同
				if(StringUtil.isNotEmpty(entity.getReceiveCustomerPhone()) && entity.getReceiveCustomerPhone().equals(entity.getDeliveryCustomerPhone())
						&& StringUtil.isNotEmpty(entity.getReceiveCustomerContact()) && entity.getReceiveCustomerContact().equals(entity.getDeliveryCustomerContact())){
					flag=true;
				}
			}
			//如果手机号与固定电话都为空，则不允许提交
			if(StringUtils.isEmpty(queryParam.getMobilePhone()) && StringUtils.isEmpty(queryParam.getContactPhone())){
				throw new WaybillSubmitException("收货人手机号与固定电话不允许全部为空！");
			}
			//查询匹配记录
			ContactEntity dto=cusContactService.queryCusContactByMobileOrPhoneAndName(queryParam);
			//若匹配成功，则不生成
			if(dto!=null && (dto.getCustomerCode()!=null || dto.getOwnCustId()!=null)){
				//根据crmCusId获取客户信息
				CustomerEntity cus=queryCustomerService.queryCustomerByCrmCusId(dto.getCustomerCode(),dto.getOwnCustId());
				if(cus!=null && StringUtil.isNotEmpty(cus.getCusCode())){
					entity.setReceiveCustomerCode(cus.getCusCode());
					isCreate = false;
				}else{
					isCreate = true;
				}						
			}else{
				//发货人与收货人不是同一个才创建
				if(!flag){
					isCreate = true;	
				}else{
					if(StringUtil.isNotEmpty(entity.getDeliveryCustomerCode())){
						entity.setReceiveCustomerCode(entity.getDeliveryCustomerCode());
						isCreate = false;
					}else{
						isCreate = true;
					}
				}				
			}		
			
		}
		
		//如果是潜客的话，则需要调用综合接口标记潜客已开单
		if(DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(type)){
			if(StringUtil.isNotEmpty(entity.getReceiveCustomerCode())
					&& !entity.getReceiveCustomerCode().equals(entity.getDeliveryCustomerCode())){
				isCreate=true;
			}			
		}
		
		//判断是否需要生成客户
		if(isCreate){
			//定义客户信息对象
			NonfixedCustomerAssociatedInformationEntity customer = new NonfixedCustomerAssociatedInformationEntity();
			
			//客户编码
			customer.setCustomerCode(entity.getReceiveCustomerCode());
			//客户名称
			customer.setCustomerName(entity.getReceiveCustomerName());
			//联系人名称
			customer.setLinkManName(entity.getReceiveCustomerContact());
			//客户固定电话
			customer.setMobile(entity.getReceiveCustomerPhone());
			//客户省
			customer.setProCode(entity.getReceiveCustomerProvCode());
			//客户市
			customer.setCityCode(entity.getReceiveCustomerCityCode());
			//客户区县
			customer.setCountyCode(entity.getReceiveCustomerDistCode());
			//地址
			customer.setAddress(entity.getReceiveCustomerAddress());
			//客户手机
			customer.setCellPhone(entity.getReceiveCustomerMobilephone());
			//业务类别(零担和快递)
			if(waybillExpressService.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())){
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_EXPRESS);
			}else{
				customer.setBusinessType(DictionaryValueConstants.BUSINESS_LTT);
			}
			//客户所属部门标杆编码
			String unifiedCode = null;
			//收货客户所属部门是到达部门
//			OrgAdministrativeInfoEntity o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getCustomerPickupOrgCode());
			//收货客户所属部门信息
			OrgAdministrativeInfoEntity o=null;
			//如果运输性质为汽运偏线、精准空运，收货客户归属部门为该票货的发货部门
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())){
				o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getReceiveOrgCode());
			}
			//如果运输性质为德邦快递
			else if(waybillExpressService.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())){
				String code = entity.getReceiveMethod();
				// 判断是否自提，如果自提，收货客户归属部门为该票货的到货部门，如果是送货上楼，归属部门为该票货的收入部门
				if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code) || WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code)) {
					o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getCustomerPickupOrgCode());
				}else{
			        o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getReceiveOrgCode());
				}
			}else{
				 o = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(entity.getCustomerPickupOrgCode());
			}
			if(null != o){
				unifiedCode = o.getUnifiedCode();
			}			
//			if(DictionaryValueConstants.CUSTOMER_PC_CUSTOMER.equals(type)){
//				//收货人为潜客开单时，如果运输性质为汽运偏线、精准空运、德邦快递，则不传归属部门
//				if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())
//						|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())
//						|| ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(entity.getProductCode())){
//					customer.setUnifiedCode(null);
//				}else{
//					customer.setUnifiedCode(unifiedCode);
//				}
//			}else{
				customer.setUnifiedCode(unifiedCode);
//			}
			//到达客户
			customer.setCustomerAttributes(DictionaryValueConstants.ARRIVE_CUSTOMER_ATTRIBUTE);
			//客户性质（潜客OR散客）
			customer.setCustType(type);
			//调用综合接口生成收货客户信息
			NonfixedCustomerAssociatedInformationEntity nonCus=synNonfixedCustomerToCrmSerivce.createNonfixedCustomer(customer);			
			//如果是散客，需要将客户编码反写到运单中
			if(DictionaryValueConstants.CUSTOMER_SC_CUSTOMER.equals(type)){
				if(nonCus!=null && nonCus.getCustomerCode()!=null){					
					entity.setReceiveCustomerCode(nonCus.getCustomerCode());
				}else{
					Log.debug("***************更改单生成收货客户信息---综合未生成客户编码***************"+entity.getWaybillNo());
				}
			}			
		}
		WaybillEntity waybillEntity=new WaybillEntity();
		waybillEntity.setReceiveCustomerCode(entity.getReceiveCustomerCode());
		waybillEntity.setId(entity.getId());
		//更新客户编码
		waybillRfcVarificationDao.updateCustomerCodeByWaybillId(waybillEntity);
	}
	
	/**
	 * 是否需要修改送货任务时候传递派送单给Gps
	 * @param waybillRfcEntity
	 * @param newVersionDto
	 * @param oldVersionDto
	 */
	private void makeSureModifyDeliverTaskToGps(WaybillRfcEntity waybillRfcEntity, WaybillDto newVersionDto,WaybillDto oldVersionDto) {
		//如果不是快递单，因为目前派送这块不包含快递这块，无法跟踪
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(newVersionDto.getWaybillEntity().getProductCode(), newVersionDto.getWaybillEntity().getBillTime())){
			List<String> deliverStatusList = new ArrayList<String>();
			deliverStatusList.add(DeliverbillConstants.STATUS_CONFIRMED);
			//根据运单号、派送单状态进行派送单的查询
			DeliverbillEntity deliverbill = deliverbillService.queryDeliverbillDetailEntityByWaybillNo(newVersionDto.getWaybillEntity().getWaybillNo(), deliverStatusList);
			//只有在这种状态下才需要查询
			if(deliverbill != null){
				boolean needSend = false;
				List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList = waybillRfcVarificationDao.queryWayBillRfcChangeDetailByRfcId(waybillRfcEntity.getId());
				if(CollectionUtils.isNotEmpty(waybillRfcChangeDetailEntityList)){
					for(WaybillRfcChangeDetailEntity rfcDetail : waybillRfcChangeDetailEntityList){
						if(rfcDetail.getRfcItems().equals("goodsVolumeTotal") || rfcDetail.getRfcItems().equals("goodsWeightTotal")){
							needSend = true;
							break;
						}
					}
				}
				if(needSend){
					LOG.info("因为更改运单体积、重量而需要更改货任务中的运单数据");
					com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto resultDto = 
							gpsDeliverService.syscModifyDeliverTaskToGps(deliverbill, newVersionDto.getWaybillEntity());
					if(ResultDto.FAIL.equals(resultDto.getCode())){
						LOG.info("Foss更改Gps中运单重量体积信息失败,错误详情："+resultDto.getMsg());
					}
				}
			}
		}
	}
	
	/**
	 * 上报OA差错 -MANA-518
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-12-28 下午4:16:00
	  * @param waybillRfcEntity
	  * @param newWaybillEntiy
	  * @param oldWaybillEntiy
	  * @return
	 */
	private void reportOAError(WaybillRfcEntity waybillRfcEntity,WaybillEntity newWaybillEntiy,WaybillEntity oldWaybillEntiy){
		try{
			OaErrorReportDto oaErrorDto = createOAErrorDto(waybillRfcEntity, newWaybillEntiy, oldWaybillEntiy);
			OAErrorReportResultDto resultDto= (OAErrorReportResultDto)oaErrorService.reportOAError(oaErrorDto);
			if(WaybillConstants.FAIL_TYPE == resultDto.getSussces()){
				LOG.error(OAErrorException.REPORT_OAERRORS_BUZ_INACCESSIBLE_1 + "   " + resultDto.getMessage());
				throw new WaybillRfcChangeException(OAErrorException.REPORT_OAERRORS_BUZ_INACCESSIBLE_1 + "【错误信息:" + resultDto.getMessage() + "】");
			}
		}catch(OAErrorException e){
			throw new WaybillRfcChangeException(e.getMessage());
		}
		
	}
	
	/**
	 * 组建oa上报差错对象 -MANA-518
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-1-21 下午5:53:28
	  * @param waybillRfcEntity
	  * @param newWaybillEntity
	  * @param oldWaybillEntity
	  * @return
	 */
	private OaErrorReportDto createOAErrorDto(WaybillRfcEntity waybillRfcEntity,WaybillEntity newWaybillEntity, WaybillEntity oldWaybillEntity){
		OaErrorReportDto oaErrorReportDto = new OaErrorReportDto();
		BeanUtils.copyProperties(newWaybillEntity, oaErrorReportDto);
		//设置电话
		if(StringUtils.isNotBlank(newWaybillEntity.getReceiveCustomerMobilephone()))
			oaErrorReportDto.setReceiveCustomerPhones(newWaybillEntity.getReceiveCustomerMobilephone());
		if(StringUtils.isNotBlank(newWaybillEntity.getReceiveCustomerPhone()) && StringUtils.isNotBlank(oaErrorReportDto.getReceiveCustomerPhones()))
			oaErrorReportDto.setReceiveCustomerPhones(oaErrorReportDto.getReceiveCustomerPhones() + SYMBOL_OR + newWaybillEntity.getReceiveCustomerPhone());
		else if(StringUtils.isNotBlank(newWaybillEntity.getReceiveCustomerPhone()))
			oaErrorReportDto.setReceiveCustomerPhones(newWaybillEntity.getReceiveCustomerMobilephone());
		//查询责任事业部
		List<String> orgTypeLst = new ArrayList<String>();
		orgTypeLst.add(BizTypeConstants.ORG_DIVISION);	//事业部类型
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(waybillRfcEntity.getDraftOrgCode(),orgTypeLst);
		if(orgEntity != null)
			oaErrorReportDto.setBusDepartmentName(orgEntity.getName());
		//设置修改条目
		StringBuilder sb = new StringBuilder();
		if(!newWaybillEntity.getGoodsWeightTotal().equals(oldWaybillEntity.getGoodsWeightTotal()))
			sb.append(STR_WEIGHT_NAME).append(oldWaybillEntity.getGoodsWeightTotal().toString()).append(SYMBOL_CHANGE).append(newWaybillEntity.getGoodsWeightTotal().toString()).append(SYMBOL_DIVISION);
		if(!newWaybillEntity.getGoodsVolumeTotal().equals(oldWaybillEntity.getGoodsVolumeTotal()))
			sb.append(STR_VOLUMN_NAME).append(oldWaybillEntity.getGoodsVolumeTotal().toString()).append(SYMBOL_CHANGE).append(newWaybillEntity.getGoodsVolumeTotal().toString()).append(SYMBOL_DIVISION);
		if(!newWaybillEntity.getTransportFee().equals(oldWaybillEntity.getTransportFee())){
			sb.append(STR_TRANSLATEFEE_NAME).append(oldWaybillEntity.getTransportFee().toString()).append(SYMBOL_CHANGE).append(newWaybillEntity.getTransportFee().toString()).append(SYMBOL_DIVISION);
		}
	    oaErrorReportDto.setChangeItmes(sb.toString());
	    
	    oaErrorReportDto.setDiffTransportFee(oldWaybillEntity.getTransportFee().subtract(newWaybillEntity.getTransportFee()));
		
		//是否超重超方
		if(newWaybillEntity.getGoodsVolumeTotal().compareTo(oldWaybillEntity.getGoodsVolumeTotal()) < 0)
			oaErrorReportDto.setIsOverVolumn(STR_YES);
		else
			oaErrorReportDto.setIsOverVolumn(STR_NO);
		if(newWaybillEntity.getGoodsWeightTotal().compareTo(oldWaybillEntity.getGoodsWeightTotal()) < 0)
			oaErrorReportDto.setIsOverWeight(STR_YES);
		else
			oaErrorReportDto.setIsOverWeight(STR_NO);
		
		oaErrorReportDto.setPreGoodsVolumeTotal(oldWaybillEntity.getGoodsVolumeTotal());
		oaErrorReportDto.setPreGoodsWeightTotal(oldWaybillEntity.getGoodsWeightTotal());
		oaErrorReportDto.setSourceFossSys(WaybillConstants.SYSTEM_NAME);
		oaErrorReportDto.setResponsibilityDeptName(waybillRfcEntity.getDraftOrgName());
		oaErrorReportDto.setTransportationRemark(newWaybillEntity.getTransportationRemark() == null ? "":newWaybillEntity.getTransportationRemark());
		oaErrorReportDto.setTransportFee(oldWaybillEntity.getTransportFee());
		oaErrorReportDto.setDeliveryCustomerName(newWaybillEntity.getDeliveryCustomerContact());
		oaErrorReportDto.setReceiveCustomerName(newWaybillEntity.getReceiveCustomerContact());
		return oaErrorReportDto;
	}
	
	/**
	 * 更新发票标记-DEFECT-987  
	  * Description: 当变更项中有发票标记项，则要更新此标记到实际承运信息表中
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-12-20 下午5:31:19
	  * @param record
	  * @return
	 */
	private void updateInvoice(String waybillNo,String waybillRfcId){
		// 获取变更项列表
		List<WaybillRfcChangeDetailEntity> rfcChangeDetailLst = waybillRfcDao
				.queryRfcChangeDetail(waybillRfcId);
		ActualFreightEntity actualFreightEntity = actualFreightService
				.queryByWaybillNo(waybillNo);
		boolean isOk = false;
		boolean isChangeWeightVolume = false;
		//是否更改了目的站
		boolean isChangeDestination = false;
		BigDecimal weightChange = actualFreightEntity.getWeight();
		BigDecimal volumeChange = actualFreightEntity.getVolume();
		if (CollectionUtils.isNotEmpty(rfcChangeDetailLst)) {
			for (int i = 0; i < rfcChangeDetailLst.size(); i++) {
				WaybillRfcChangeDetailEntity detailEntity = rfcChangeDetailLst
						.get(i);
				// 如果变更项是发票标记，则更新实际承运信息
				if (WaybillConstants.INVOICE_TYPE.equals(detailEntity
						.getRfcItems())) {
					String newInvoice = detailEntity.getAfterRfcInfo();
					if (WaybillConstants.INVOICE_TYPE_01.equals(newInvoice)) {
						newInvoice = WaybillConstants.INVOICE_01;
					} else if (WaybillConstants.INVOICE_TYPE_02
							.equals(newInvoice)) {
						newInvoice = WaybillConstants.INVOICE_02;
					}
					if (StringUtils.isNotBlank(newInvoice)) {
						actualFreightEntity.setInvoice(newInvoice);
						isOk = true;
					}
				}
				// 如果变更项是是否大票
				if (WaybillConstants.BIGTICKET.equals(detailEntity
						.getRfcItems())) {
					String isBig = detailEntity.getAfterRfcInfo();
					if ("是".equals(isBig)) {
						isBig = WaybillConstants.YES;
					} else if ("否".equals(isBig)) {
						isBig = WaybillConstants.NO;
					}
					if (StringUtils.isNotBlank(isBig)) {
						actualFreightEntity.setBigTicketGoods(isBig);
						isOk = true;
					}
				}
				// 如果变更的是重量和体积
				if ("goodsVolumeTotal".equals(detailEntity.getRfcItems()) || "goodsWeightTotal".equals(detailEntity.getRfcItems())) {
					// 只要重量和体积有一个修改了就传给中转进行刷新
					if ("goodsVolumeTotal".equals(detailEntity.getRfcItems())) {
						volumeChange = new BigDecimal(detailEntity.getAfterRfcInfo());
					}
					if ("goodsWeightTotal".equals(detailEntity.getRfcItems())) {
						weightChange = new BigDecimal(detailEntity.getAfterRfcInfo());
					}
					isChangeWeightVolume = true;
				}
				//判定是否更改了目的站
				if(WaybillRfcChangeItemsConstants.ITEM_CUSTOMER_PICKUP_ORG_NAME.equals(detailEntity.getRfcItems())){
					isChangeDestination = true;
				}
			}
			if (isOk) {
				actualFreightService.updateWaybillActualFreight(actualFreightEntity);
			}

			//借口实体类
			MakeUpWaybillEntity makeUpWaybillEntity = new MakeUpWaybillEntity();
			makeUpWaybillEntity.setWaybillNo(actualFreightEntity.getWaybillNo());
			makeUpWaybillEntity.setWeight(weightChange);
			makeUpWaybillEntity.setVolume(volumeChange);
			makeUpWaybillEntity.setQuantity(new BigDecimal(actualFreightEntity.getGoodsQty()));
			makeUpWaybillEntity.setMakeUpTime(actualFreightEntity.getModifyTime());
			if(isChangeWeightVolume){
				// 通知中转重量体积变更，将变更信息放入中转跑批表		
				makeUpWaybillService.addWaybillInfoForTfr(makeUpWaybillEntity);
			}
			//判定是否更改了目的站
			if(isChangeDestination){
				DeliverHandoverbillReturnDto resultDto = new DeliverHandoverbillReturnDto();
				resultDto.setWaybillNo(waybillNo);
				deliverHandoverbillService.LastLoadOrgCodeChangeDeliverbill(resultDto );
			}
		
		}			
	}

	/**
	 * 更新【是否統一結算】【合同部門】【催款部門】
	 */
	private void updateChangedActualFreightEntity(String waybillNo,String waybillRfcId){
		List<WaybillRfcChangeDetailEntity> rfcChangeDetailLst = waybillRfcDao.queryRfcChangeDetail(waybillRfcId);
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		//查询根据运单号 查询下运单实体
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		boolean isOk=false;
		String newDeliveryCustomerCode = "";
		if(CollectionUtils.isNotEmpty(rfcChangeDetailLst)){
			for(int i = 0; i < rfcChangeDetailLst.size(); i ++){
				WaybillRfcChangeDetailEntity detailEntity = rfcChangeDetailLst.get(i);
				if(WaybillConstants.DELIVERY_CUSTOMER_CODE.equals(detailEntity.getRfcItems()) || WaybillConstants.DELIVERY_CENTRALIZED_SETTLEMENT.equals(detailEntity.getRfcItems())){
					if(WaybillConstants.DELIVERY_CUSTOMER_CODE.equals(detailEntity.getRfcItems())){
						 newDeliveryCustomerCode =  detailEntity.getAfterRfcInfo();
					}else{
						if(null != waybillEntity){
							newDeliveryCustomerCode = waybillEntity.getDeliveryCustomerCode();
						}
					}
					CusBargainEntity cusb =  queryCustomerService.queryCusBargainByCustCode(newDeliveryCustomerCode);
					actualFreightEntity = setDelveryCustomerInfoMation(actualFreightEntity,cusb);
					if(null != actualFreightEntity){
						isOk = true;
					}
				}
				if(WaybillConstants.RECEIVE_CUSTOMER_CODE.equals(detailEntity.getRfcItems()) || WaybillConstants.RECEIVE_CENTRALIZED_SETTLEMENT.equals(detailEntity.getRfcItems())){
					String newReceiveCustomerCode =  "";
					if(WaybillConstants.RECEIVE_CUSTOMER_CODE.equals(detailEntity.getRfcItems())){
						newReceiveCustomerCode =  detailEntity.getAfterRfcInfo();
					}else{
						if(null != waybillEntity){
							newReceiveCustomerCode = waybillEntity.getReceiveCustomerCode();
						}
					}
					CusBargainEntity cusb = queryCustomerService.queryCusBargainByCustCode(newReceiveCustomerCode);
					actualFreightEntity = setReceiveCustomerInfoMation(actualFreightEntity,cusb);
					if(null != actualFreightEntity){
						isOk = true;
					}
				}
				//add by 306486 根据变更明细设置分拣结果
				if(WaybillConstants.SORTING_RESULT.equals(detailEntity.getRfcItems())){
                    actualFreightEntity.setSortingResult(detailEntity.getAfterRfcInfo());
                    isOk = true;
                }
			}
			if(isOk && actualFreightEntity != null){
				//主要是用于 新增的【统一结算的字段信息】
				//查询客户分群
				if(StringUtils.isNotEmpty(newDeliveryCustomerCode)){
					CustomerEntity customerEntity = customerService.queryCustomerInfoByCusCode(newDeliveryCustomerCode);
					if(customerEntity != null){
						String flabelleavemonth = customerEntity.getFlabelleavemonth();
						actualFreightEntity.setFlabelleavemonth(flabelleavemonth);
					}
				}
				actualFreightService.updateActualById(actualFreightEntity);
			}
		}
	}
	
	/**
	 * 重新設置：業務邏輯和之前的設置的時候是一樣的，在此就不過多敘述
	 * @param actualFreightEntity
	 * @param cus
	 * @return
	 */
	private ActualFreightEntity setDelveryCustomerInfoMation(ActualFreightEntity actualFreightEntity,CusBargainEntity cus){
		if (null == cus) {
			actualFreightEntity
					.setStartCentralizedSettlement(FossConstants.NO);
			actualFreightEntity.setStartContractOrgCode(null);
			actualFreightEntity.setStartReminderOrgCode(null);
		} else {
			//发票标记 与 统一结算 无关联 --sangwenhao-272311
			/*String invoiceType = queryCustomerService.queryComtomerInvoiceType(null, cus.getCrmId());
			if (invoiceType !=null && WaybillConstants.INVOICE_02.equals(invoiceType)) {*/
				if (WaybillConstants.YES.equals(cus.getAsyntakegoodsCode())) {
					actualFreightEntity
							.setStartCentralizedSettlement(FossConstants.YES);
					actualFreightEntity.setStartContractOrgCode(cus.getUnifiedCode());
					actualFreightEntity.setStartReminderOrgCode(cus.getHastenfunddeptCode());
				} else {
					actualFreightEntity
							.setStartCentralizedSettlement(FossConstants.NO);
					actualFreightEntity.setStartContractOrgCode(null);
					actualFreightEntity.setStartReminderOrgCode(null);
				}
			/*} else {
				actualFreightEntity
						.setStartCentralizedSettlement(FossConstants.NO);
				actualFreightEntity.setStartContractOrgCode(null);
				actualFreightEntity.setStartReminderOrgCode(null);
			}*/
		}
		return actualFreightEntity;
	}
	/**
	 * 
	 * @param actualFreightEntity
	 * @param cus
	 * @return
	 */
	private ActualFreightEntity setReceiveCustomerInfoMation(
			ActualFreightEntity actualFreightEntity, CusBargainEntity cus) {
		// 判断CRM维护了此客户的信息
		if (null == cus ) {
			actualFreightEntity
					.setArriveCentralizedSettlement(FossConstants.NO);
			actualFreightEntity.setArriveContractOrgCode(null);
			actualFreightEntity.setArriveReminderOrgCode(null);
		} else {
			//发票标记 与 统一结算 无关联 --sangwenhao-272311
			/*String invoiceType = queryCustomerService.queryComtomerInvoiceType(null, cus.getCrmId());
			// 当发票标记为02时
			if (invoiceType !=null && WaybillConstants.INVOICE_02.equals(invoiceType)) {*/
				// 从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
				// 【是否统一结算】设置为【是】，【合同部门】设置为查询出来的对应的部门
				if (WaybillConstants.YES.equals(cus.getAsyntakegoodsCode())) {
					actualFreightEntity.setArriveCentralizedSettlement(FossConstants.YES);
					actualFreightEntity.setArriveContractOrgCode(cus.getUnifiedCode());
					actualFreightEntity.setArriveReminderOrgCode(cus.getHastenfunddeptCode());
				} else {
					// 从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
					// 【是否统一结算】设置为【否】，【合同部门】设置为【null】
					actualFreightEntity.setArriveCentralizedSettlement(FossConstants.NO);
					actualFreightEntity.setArriveContractOrgCode(null);
					actualFreightEntity.setArriveReminderOrgCode(null);
				}
			/*} else {
				// 当发票标记为1的时候，【是否统一结算】设置【否】，【合同部门】设置为【null】
				actualFreightEntity.setArriveCentralizedSettlement(FossConstants.NO);
				actualFreightEntity.setArriveContractOrgCode(null);
				actualFreightEntity.setArriveReminderOrgCode(null);
			}*/
		}
		return actualFreightEntity;
	}
	
	/**
	 * /调用空运合大票中转接口
	 * @param oldVersionDto
	 * @param newVersionDto
	 */
	private void updateAirQueryModifyPickupbillService(
			WaybillDto oldVersionDto, WaybillDto newVersionDto) {
		WaybillEntity newEntity = newVersionDto.getWaybillEntity();
//		WaybillEntity oldEntity = oldVersionDto.getWaybillEntity();
		String transType = newEntity.getProductCode();//运输类型
		String freightMethod = newEntity.getFreightMethod();//合票方式
		if(! ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transType)){
			return;
		}
		
		if(! WaybillConstants.FREIGHT_METHOD_HDP.equals(freightMethod)){
			return;//不是合大票 不做操作
		}
		
		AirPickupbillDetailEntity entity = new AirPickupbillDetailEntity();
		entity.setWaybillNo(newEntity.getWaybillNo());
		entity.setDeliverFee(newEntity.getDeliveryGoodsFee());
		entity.setArrivalFee(newEntity.getToPayAmount());
		entity.setCollectionFee(newEntity.getCodAmount());
		entity.setGoodsName(newEntity.getGoodsName());
		entity.setGoodsQty(newEntity.getGoodsQtyTotal());
		entity.setWeight(newEntity.getGoodsWeightTotal());
		entity.setBillingWeight(newEntity.getBillWeight());
		entity.setPickupType(newEntity.getReceiveMethod());
		String receiveCustomerMobilephone = newEntity.getReceiveCustomerPhone();
		if(StringUtils.isEmpty(receiveCustomerMobilephone)){
			receiveCustomerMobilephone = newEntity.getReceiveCustomerMobilephone();
		}
		entity.setReceiverContactPhone(receiveCustomerMobilephone);
		entity.setReceiverAddress(newEntity.getReceiveCustomerAddress());
		entity.setReceiverName(newEntity.getReceiveCustomerName());
		airQueryModifyPickupbillService.updatePickupDetailFromModifyWaybill(entity);
		
	}

	/**
	 * 如果老的单是非返单，新单需要返单，需要新增一条记录;如果老的单是返单，新单不是返单，需要删除记录
	 */
	private void updataSignReturnBill(WaybillDto oldVersionDto,WaybillDto newVersionDto){
		if(oldVersionDto!=null&&newVersionDto!=null){
			String oldReturnType=	oldVersionDto.getWaybillEntity().getReturnBillType();//旧返单类别
			String newReturnType=newVersionDto.getWaybillEntity().getReturnBillType();//新返单类型
			//如果老的单是非返单，新单需要返单，需要新增一条记录
			if(oldReturnType==null||WaybillConstants.NOT_RETURN_BILL.equals(oldReturnType)){
				if(newReturnType!=null&&!WaybillConstants.NOT_RETURN_BILL.equals(newReturnType)){
					addSignReturnBill(newVersionDto);//新增一条记录
				}
			}
			
			//如果老的单是返单，新单不是返单，需要删除记录
			if(!WaybillConstants.NOT_RETURN_BILL.equals(oldReturnType)){
				if(newReturnType==null||WaybillConstants.NOT_RETURN_BILL.equals(newReturnType)){
					deleteSignReturnBill(oldVersionDto);
				}
			}
		}
	}
	/**
	 * 新增签收单返单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午4:18:00
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#addSignReturnBill()
	 */
	private void deleteSignReturnBill(WaybillDto waybillDto) {
		
		// 从运单DTO对象中获得运单基本信息对象
				WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
				//对签收单信息进行持久化操作
				returnBillProcessDao.deleteByWaybillNO(waybillEntity.getWaybillNo());
		
	}
	
	
	
	/**
	 * 新增签收单返单信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午4:18:00
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#addSignReturnBill()
	 */
	private  void addSignReturnBill(WaybillDto waybillDto) {
		// 从运单DTO对象中获得运单基本信息对象
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();

		//获得创建人名字
		String createUserName = StringUtil.defaultIfNull(waybillEntity.getCreateUserName());
		//获得创建组织名
		String createOrgName = StringUtil.defaultIfNull(waybillEntity.getCreateUserDeptName());
		//对数据操作进行异常捕捉
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

				//对签收单信息进行持久化操作
				returnBillProcessDao.insertSelective(returnBill);
			}
		} catch (BusinessException e) {
			//添加异常日志
			LOG.error("Excepton", e);
			//抛出生成签收单返单失败的异常信息
			throw new WaybillSubmitException(WaybillSubmitException.SIGNRETURNBILL_FAIL,new Object[]{messageBundle.getMessage(e.getErrorCode()+"\n"+e.getMessage())});
		}
	}
	
	
	
	/**
	 * 更改单提交完整性校验
	 * @param submitDto
	 */
	private void checkDataIntegrity(WaybillRfcEntity rfcEntity) {
		
			WaybillEntity oldVersionEntity = waybillManagerService
					.queryWaybillById(rfcEntity.getOldVersionWaybillId());
			// 不是更改运单号且老版本运单的运单号不等于本次修改的运单号，抛出异常
			if (!FossConstants.ACTIVE.equals(rfcEntity.getIsChangeWaybillNo())) {
				if (!rfcEntity.getWaybillNo().equals(oldVersionEntity.getWaybillNo())) {

					/**
					 * 更改单提交失败,原因是老版本运单的运单号不等于本次修改的运单号！请重新发更改（重新打开更改菜单）！
					 */
				 throw new WaybillRfcException(WaybillRfcException.CHECK_DATA_INTEGRITY);
				}
			}
		
	}
	/**
	 * 
	 * 如果修改了运单号，调用CRM接口重新绑定订单号
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-18 下午6:57:15
	 */
	private void updateCRMOrderNo(WaybillRfcEntity waybillRfcEntity,WaybillDto waybillDto) {
		//判断是否修改了运单号
		if(FossConstants.YES.equals(waybillRfcEntity.getIsChangeWaybillNo())){
			// 订单号是否为空
			if (StringUtil.isNotBlank(waybillDto.getWaybillEntity().getOrderNo())) {
				LOG.info("update crm order no " + waybillDto.getWaybillEntity().getOrderNo() + "/" + waybillDto.getWaybillEntity().getWaybillNo());
				//订单号不为空则更新订单状态
				orderService.updateOrderInfo(waybillDto.getWaybillEntity());
			}

		}
	}


	/**
	 * 
	 * 修改运单号的时候
	 * 调用中转方法
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-18 下午6:57:15
	 */
	private void updateTfrWaybillNo(WaybillRfcEntity rfcEntity, WaybillDto newVersionDto,WaybillDto oldVersionDto) {
		//改变了运单号
		if(FossConstants.YES.equals(rfcEntity.getIsChangeWaybillNo())){
//			运单在出发部门库存，且未做交接单时，更改运单号
//			更改中转相关数据：
//			1.库存数据
//			2.走货路径数据
//			3.包装信息
			
			String oldProductCode=oldVersionDto.getWaybillEntity().getProductCode();
			//整车不调整走货路径，根据DEFECT-2021修改
			if(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(oldProductCode)){
				stockService.modifyWaybillNo(oldVersionDto.getWaybillEntity().getWaybillNo(), newVersionDto.getWaybillEntity().getWaybillNo(), oldVersionDto.getWaybillEntity().getBillTime(),oldProductCode);	
			}else{
				stockService.modifyWaybillNo(oldVersionDto.getWaybillEntity().getWaybillNo(), newVersionDto.getWaybillEntity().getWaybillNo(), oldVersionDto.getWaybillEntity().getBillTime());	
			}									
		
			//删除老的代办
			//删除代办
			labeledGoodTodoDao.deleteTodoByWaybillNo(rfcEntity.getWaybillNo());
			
			//BUG-44956删除代办
			List<WaybillRfcEntity> oldRfcEntityList = waybillRfcDao.queryWaybillRfcEntityByNewVersionId(rfcEntity.getOldVersionWaybillId());
			while(CollectionUtils.isNotEmpty(oldRfcEntityList)){
				for(int i=0;i<oldRfcEntityList.size();i++){
					WaybillRfcEntity old = oldRfcEntityList.get(i);
					labeledGoodTodoDao.deleteTodoByWaybillRfcId(old.getId());
					oldRfcEntityList.remove(old);
				}			
			}
		}
	}
	
	/**
	 * 如果件数增加，运单货签信息在原有流水号后递增流水号
	 * @author 043260-foss-suyujun
	 * @date 2012-12-19
	 * @param rfcEntity
	 * @return void
	 */
	private void doWhenPiecesChange(WaybillRfcEntity rfcEntity, WaybillDto newVersionDto,WaybillDto oldVersionDto, CurrentInfo currentInfo){

		String rfcType = rfcEntity.getRfcType();
		//作废、中止
		if(WaybillRfcConstants.INVALID.equals(rfcType)||WaybillRfcConstants.ABORT.equals(rfcType)){
			return;
		}
		
		String waybillNo = rfcEntity.getWaybillNo();
		WaybillEntity newVersionEntity = waybillManagerService.queryWaybillById(rfcEntity.getNewVersionWaybillId());	
		
		//改变了运单号
		if(FossConstants.YES.equals(rfcEntity.getIsChangeWaybillNo())){
			//如果运单号变化，更新lablegood中的运单号
			//原运单
			WaybillEntity oldVersionEntity = waybillManagerService.queryWaybillById(rfcEntity.getOldVersionWaybillId());
			//新运单
			labeledGoodService.modifyWaybillNo(oldVersionEntity.getWaybillNo(), newVersionEntity.getWaybillNo());
			labeledGoodChangeDao.modifyWaybillNo(oldVersionEntity.getWaybillNo(), newVersionEntity.getWaybillNo());
			waybillNo = newVersionEntity.getWaybillNo();
		}
		
		List<WaybillRfcChangeDetailEntity> changeDetail 
				= waybillRfcVarificationDao.queryWayBillRfcChangeDetailByRfcId(rfcEntity.getId());
		
		//
		List<LabeledGoodChangeEntity>  labeledChangeList = labeledGoodChangeDao.queryInProcessLabelGoodChangeHistory(waybillNo);
		List<LabeledGoodEntity>	 labeledList =	labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
		
		
		
		
		boolean changeGoodQtyTotal = false;
		WaybillRfcChangeDetailEntity detail2 = null;
		if(CollectionUtils.isNotEmpty(changeDetail)){
			for(WaybillRfcChangeDetailEntity detail:changeDetail){
				if("goodsQtyTotal".equals(detail.getRfcItems())){
					detail2 = detail;
					changeGoodQtyTotal = true;
					break;
				}
				
			}
		}
		
		
		if(changeGoodQtyTotal){
			Integer before = null;
			Integer after = null;
			if(detail2!=null){
				before = Integer.valueOf(detail2.getBeforeRfcInfo());
				after = Integer.valueOf(detail2.getAfterRfcInfo());
			}else{
				int j = 0;
				for(int i=0; i<labeledList.size(); i++){
					LabeledGoodEntity lentity=labeledList.get(i);
					if(lentity!=null &&FossConstants.YES.equals( lentity.getActive())){
						j++;
					}
				}
				
				before = j;
						
				int k = 0;
				for(int i=0; i<labeledChangeList.size(); i++){
					LabeledGoodChangeEntity lentity=labeledChangeList.get(i);
					if(lentity!=null &&
							//以下判断表示这个件存在
							(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW.equals(lentity.getChangeType())||
									LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(lentity.getChangeType())||
									LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(lentity.getChangeType())||
									LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE.equals(lentity.getChangeType())
									)
							 ){
						k++;
					}
				}
				
				after = k;
			}
			
		
			if(before!=null && after!=null){
				int count = after.intValue() - before.intValue();
				
				if(count!=0){
				
					List<LabeledGoodDto> newLabelList = new ArrayList<LabeledGoodDto>();
					List<LabeledGoodDto> deleteLabelList = new ArrayList<LabeledGoodDto>();
					//List<String>  addSerialNoList = new ArrayList<String> ();//需要调用增加的List
					List<LabeledGoodChangeEntity>  addList = new ArrayList<LabeledGoodChangeEntity> ();//需要调用增加的List
					List<String> deleteLabelNoList = new ArrayList<String> ();
					for(int i = 0 ; i<labeledChangeList.size(); i++){
						LabeledGoodChangeEntity changeEntity = labeledChangeList.get(i);
						if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW.equals(changeEntity.getChangeType())
								|| LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(changeEntity.getChangeType()) 
								|| LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(changeEntity.getChangeType())
								|| LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE.equals(changeEntity.getChangeType())){
							//新增一个货签
							boolean hasThisEntity = false;
							
							for(int j =0;j<labeledList.size();j++){
								
								LabeledGoodEntity goodEntity = labeledList.get(j);
								if(goodEntity.getSerialNo().equals(changeEntity.getSerialNo())){
									hasThisEntity = true; 
								}
							}
							//去掉重复  --
							for(int k=0;k <newLabelList.size();k++){
								LabeledGoodDto labeledGoodDto = newLabelList.get(k);
								if(labeledGoodDto.getSerialNo().equals(changeEntity.getSerialNo())){
									hasThisEntity = true; 
								}
								
							}
							
							
							if(!hasThisEntity){
								LabeledGoodDto labeledGoodDto = new LabeledGoodDto();
								labeledGoodDto.setLabledGoodChangeId(changeEntity.getId());
								labeledGoodDto.setSerialNo(changeEntity.getSerialNo());
								newLabelList.add(labeledGoodDto);
								addList.add(changeEntity);
								//addSerialNoList.add(changeEntity.getSerialNo());
							}
						}else if (LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE.equals(changeEntity.getChangeType())){
							LabeledGoodDto labeledGoodDto = new LabeledGoodDto();
							labeledGoodDto.setLabledGoodChangeId(changeEntity.getId());
							labeledGoodDto.setSerialNo(changeEntity.getSerialNo());
							
							boolean hasSerial = false;
							for(int k=0; k<deleteLabelList.size(); k++){
								LabeledGoodDto labeledGoodDto2 = deleteLabelList.get(k);
								if(labeledGoodDto2.getSerialNo().equals(labeledGoodDto.getSerialNo())){
									hasSerial = true; 
								}
							}
							
							if(!hasSerial){
								deleteLabelList.add(labeledGoodDto);
								deleteLabelNoList.add(changeEntity.getSerialNo());
							}
						}																
					}
					
					//对于labeledgood中纯在 但是change中不存在active='Y'的对象  我们应该删掉
					for(int w = 0 ; w<labeledList.size(); w++){
						LabeledGoodEntity goodEntity = labeledList.get(w);
						boolean hasThisGood = false;//首先得到这个货签是否则change中存在
						for(int i = 0 ; i<labeledChangeList.size(); i++){
							LabeledGoodChangeEntity changeEntity = labeledChangeList.get(i);
							if(changeEntity.getSerialNo().equals(goodEntity.getSerialNo())){
								hasThisGood = true;
							}
							
						}
						
						//如果不存在 就删掉
						if(!hasThisGood){
							LabeledGoodDto labeledGoodDto = new LabeledGoodDto();
							labeledGoodDto.setLabledGoodChangeId(null);// no need set this value it is no use
							labeledGoodDto.setSerialNo(goodEntity.getSerialNo());
							
							boolean hasSerial = false;
							for(int k=0; k<deleteLabelList.size(); k++){
								LabeledGoodDto labeledGoodDto2 = deleteLabelList.get(k);
								if(labeledGoodDto2.getSerialNo().equals(labeledGoodDto.getSerialNo())){
									hasSerial = true; 
								}
							}
							
							if(!hasSerial){
								deleteLabelList.add(labeledGoodDto);
								deleteLabelNoList.add(goodEntity.getSerialNo());
							}
							
						}
						
						
					}
					

					//这里需要调用中转增加走货路径中其中一票的接口  用来以后调用
					if(CollectionUtils.isNotEmpty(addList)){
						labeledGoodService.insertSerialNo(waybillNo, newLabelList);
						labeledGoodChangeDao.updateLabeledGoodChangeToNeedInvokeTfr(newLabelList);
						
						WaybillEntity waybillEntity = newVersionEntity;
						String transType = waybillEntity.getProductCode();//运输类型
						String destOrgCode = waybillEntity.getCustomerPickupOrgCode();
						//TODO 新增件数 放在受理里面做完
						
						if(FossConstants.YES.equals(newVersionEntity.getIsWholeVehicle())){
							//整车直接更新到达件数
							//TODO
							
							ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
							//运单件数
							int waybillGoodsQty = waybillEntity.getGoodsQtyTotal()==null?0:waybillEntity.getGoodsQtyTotal();
							//到达件数
							int arriveGoodsQty = actualFreightEntity.getArriveGoodsQty()==null?0:actualFreightEntity.getArriveGoodsQty();
							//到达未出库件数
							int arriveNotoutGoodsQty = actualFreightEntity.getArriveNotoutGoodsQty()==null?0:actualFreightEntity.getArriveNotoutGoodsQty();
							//新增件数
							int addSerialNoQty = addList.size();
							
							//到达件数=原到达件数+新增件数
							arriveGoodsQty+=addSerialNoQty;
							//到达未出库件数=原到达未出库件数+新增件数
							arriveNotoutGoodsQty+=addSerialNoQty;
							
							
							actualFreightEntity.setArriveGoodsQty(arriveGoodsQty>waybillGoodsQty?waybillGoodsQty:arriveGoodsQty);
							actualFreightEntity.setArriveNotoutGoodsQty(arriveNotoutGoodsQty>waybillGoodsQty?waybillGoodsQty:arriveNotoutGoodsQty);
							actualFreightEntity.setArriveTime(new Date());
							actualFreightService.updateWaybillActualFreight(actualFreightEntity);
							
							
						}else{
							
							for(int w = 0 ;w <addList.size(); w++ ){
								LabeledGoodChangeEntity changeGood = addList.get(w);
								if(changeGood==null || StringUtils.isEmpty( changeGood.getSerialNo() )){
									continue;
								}
								List<String> addSerialNoList = new ArrayList<String>();
								addSerialNoList.add(changeGood.getSerialNo());
								String currentOrgCode = changeGood.getReceiveOrgCode();
								/**
								 * 判断当前部门是否是驻地部门
								 */
								SaleDepartmentEntity salesDept = saleDepartmentService.querySaleDepartmentByCode(currentOrgCode);
								if(null != salesDept && FossConstants.YES.equals(salesDept.getStation())){
									//驻地部门需要转换为对应的外场
									if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())) {//快递或3.60特惠件
										currentOrgCode = expresslineService
												.queryDefaultTransCode(currentOrgCode,transType,DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
									} else {
										currentOrgCode = lineService
												.queryDefaultTransCode(currentOrgCode,transType,DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
									}
								}
								 
								String isWholeVehicle=newVersionDto.getWaybillEntity().getIsWholeVehicle();
								if(StringUtils.isNotEmpty(currentOrgCode) 
										&& 
										StringUtils.isNotEmpty(changeGood.getSerialNo())
										&&
										!isWholeVehicle.equals(FossConstants.ACTIVE)){
									calculateTransportPathService.addNewTransportPath(waybillNo, addSerialNoList, 
											currentOrgCode, destOrgCode, transType);
								}else if(StringUtils.isNotEmpty(currentOrgCode) 
										&& 
										StringUtils.isNotEmpty(changeGood.getSerialNo())
										&&
										isWholeVehicle.equals(FossConstants.ACTIVE)){
									// 目的地更改则需要更改库存 调用接口更改库存
									InOutStockEntity inOutStockEntity = null;
									for (int s = 0; s < addSerialNoList.size(); s++) {
										if(null == addSerialNoList.get(s)){
											continue;
										}
										// 新建入库实例
										inOutStockEntity = new InOutStockEntity();
										// 设置运载单号
										inOutStockEntity.setWaybillNO(waybillNo);
										// 设置序列号
										inOutStockEntity.setSerialNO(addSerialNoList.get(s));
										// 设置当前部门
										inOutStockEntity.setOrgCode(currentOrgCode);
										
										try {
											// 设置操作员工工号
											inOutStockEntity.setOperatorCode(FossUserContext.getCurrentInfo().getEmpCode());
											// 设置操作员工姓名
											inOutStockEntity.setOperatorName(FossUserContext.getCurrentInfo().getEmpName());
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										// 设置更改单类型
										inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_RFC_IN_STOCK_TYPE);
										// 入库
										stockService.inStockPC(inOutStockEntity);
									}
								}
								
								
								//* 将状态更新为不用更新中转接口了
								labeledGoodChangeDao.updateLabeledGoodChangeToNoNeedInVokeInterface(addList.get(w).getId());
								
								ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
								
								//整车不生成代办
								//生成到达件数、到达未出库件数
								//BUG-49475(受理更改单报错)加上非空判断								
								if(StringUtils.isNotEmpty(currentOrgCode)){
									if(currentOrgCode.equals(waybillEntity.getCustomerPickupOrgCode()) || currentOrgCode.equals(actualFreightEntity.getEndStockOrgCode())){
										//运单件数
										int waybillGoodsQty = waybillEntity.getGoodsQtyTotal()==null?0:waybillEntity.getGoodsQtyTotal();
										//到达件数
										int arriveGoodsQty = actualFreightEntity.getArriveGoodsQty()==null?0:actualFreightEntity.getArriveGoodsQty();
										//到达未出库件数
										int arriveNotoutGoodsQty = actualFreightEntity.getArriveNotoutGoodsQty()==null?0:actualFreightEntity.getArriveNotoutGoodsQty();
										//新增件数
										int addSerialNoQty = addSerialNoList.size();
										
										//到达件数=原到达件数+新增件数
										arriveGoodsQty+=addSerialNoQty;
										//到达未出库件数=原到达未出库件数+新增件数
										arriveNotoutGoodsQty+=addSerialNoQty;
										
										
										actualFreightEntity.setArriveGoodsQty(arriveGoodsQty>waybillGoodsQty?waybillGoodsQty:arriveGoodsQty);
										actualFreightEntity.setArriveNotoutGoodsQty(arriveNotoutGoodsQty>waybillGoodsQty?waybillGoodsQty:arriveNotoutGoodsQty);
										actualFreightEntity.setArriveTime(new Date());
										actualFreightService.updateWaybillActualFreight(actualFreightEntity);
									}
								}						
								
							}
								
						}
					}
					
					
					//这里需要调用中转取消走货路径中其中一票的接口  do it future
					if(CollectionUtils.isNotEmpty(deleteLabelList)){
						labeledGoodService.deleteSerialNo(waybillNo, deleteLabelList);
						//labeledGoodChangeDao.updateLabeledGoodChangeToNeedInvokeTfr(newLabelList);
					
						
						//获得出库操作人信息
						String operatorCode   = FossUserContext.getCurrentInfo().getEmpCode();//员工工号
						String operatorName   = FossUserContext.getCurrentInfo().getEmpName();////员工姓名
						String currentOrgCode = FossUserContext.getCurrentDept().getCode();
						
					
						if(!FossConstants.YES.equals(newVersionEntity.getIsWholeVehicle())){
							//中转出库 取消走货路径
//							stockService.outStockInvalidGoods(waybillNo,deleteLabelNoList,currentOrgCode ,operatorCode,operatorName);
							//BUG-55167 135557784清仓差异报告异常  修改时间：2013-9-22 14:17:26
							for(String serialsNo : deleteLabelNoList){
								InOutStockEntity inOutStockEntity = new InOutStockEntity();
								inOutStockEntity.setWaybillNO(waybillNo);
								inOutStockEntity.setSerialNO(serialsNo);
								inOutStockEntity.setOrgCode(currentOrgCode);
								inOutStockEntity.setOperatorCode(operatorCode);
								inOutStockEntity.setOperatorName(operatorName);
								inOutStockEntity.setInOutStockType(StockConstants.INVALID_GOODS_OUT_STOCK_TYPE);
								stockService.outStockDelivery(inOutStockEntity);
							}
							calculateTransportPathService.deleteTransportPathByGoodsNos(waybillNo,deleteLabelNoList );
							
						}
					}
				}
			}
		}
		
		if(FossConstants.YES.equals(newVersionEntity.getIsWholeVehicle())){
			labeledGoodChangeDao.updateLabeledGoodChangeToApprove(waybillNo); 
			return;//整车后面不用做了
		}
		
		
		//更新打木架信息
		//这里改成了从LabeledGoodChangeEntity 并且根据了其状态进行打木架处理
		List<String> addSerialNos = new ArrayList<String>();
		List<String> deleteSerialNos = new ArrayList<String>();
		//zxy 20131128 ISSUE-4391 start 新增：新增打木托列表和删除打木托列表
		List<String> addSalverSerialNos = new ArrayList<String>();
		List<String> deleteSalverSerialNos = new ArrayList<String>();
		int salverPieces = 0;		//打木托数
		//zxy 20131128 ISSUE-4391 end 新增：新增打木托列表和删除打木托列表
		for(LabeledGoodChangeEntity labeledGoodEntity : labeledChangeList){
			String serialNo = labeledGoodEntity.getSerialNo();
			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(labeledGoodEntity.getChangeType())
					|| LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(labeledGoodEntity.getChangeType())){
				//新增打木架  -- 和中转开发人员沟通  重复打木架没有问题
				
				boolean hasSerial = false;
				for(int k = 0 ;k < addSerialNos.size(); k++){
					if(addSerialNos.get(k).equals(serialNo)){
						hasSerial = true;
					}
				}
				
				if(!hasSerial){
					addSerialNos.add(serialNo);
				}
			}else{
				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE.equals(labeledGoodEntity.getChangeType())){
					//取消打木架
					//和中转开发人员沟通  重复取消打木架没有问题
					deleteSerialNos.add(serialNo);
					
					boolean hasSerial = false;
					for(int k = 0 ;k < deleteSerialNos.size(); k++){
						if(deleteSerialNos.get(k).equals(serialNo)){
							hasSerial = true;
						}
					}
					
					if(!hasSerial){
						deleteSerialNos.add(serialNo);
					}
					
				}
			
			}
			//zxy 20131128 ISSUE-4391 start 新增：新增打木托列表和删除打木托列表
			if(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_ADD_NEW.equals(labeledGoodEntity.getPackageSalver())){
				addSalverSerialNos.add(labeledGoodEntity.getSerialNo());
				salverPieces ++;
			}else if(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE.equals(labeledGoodEntity.getPackageSalver())){
				deleteSalverSerialNos.add(labeledGoodEntity.getSerialNo());
			}else if(WaybillConstants.PACKAGE_TYPE_SALVER.equals(labeledGoodEntity.getPackageSalver())){
				salverPieces ++;
			}
			//zxy 20131128 ISSUE-4391 end 新增：新增打木托列表和删除打木托列表
		}
		

		//运单基本信息
		WaybillEntity waybillEntity = newVersionDto.getWaybillEntity();
		//打木架信息
		WoodenRequirementsEntity wooden = newVersionDto.getWoodenRequirementsEntity();
		//将冗余表的信息更新为审批通过
		labeledGoodChangeDao.updateLabeledGoodChangeToApprove(waybillNo); 
		if(wooden==null){
			return;
		}
		
		// 将对象进行封装
		PackagingRequireEntity packagingRequire = new PackagingRequireEntity();
		//设置运单号
		packagingRequire.setWaybillNo(waybillEntity.getWaybillNo());
		//设置开单部门code
		packagingRequire.setWaybillCreateDeptCode(currentInfo.getCurrentDeptCode());
		//设置开单部门名称
		packagingRequire.setWaybillCreateDept(currentInfo.getCurrentDeptName());
		//开单件数
		packagingRequire.setWaybillNumber(waybillEntity.getGoodsQtyTotal());
		//开单体积
		packagingRequire.setWaybillVolume(waybillEntity.getGoodsVolumeTotal());
		// 创建日期
		packagingRequire.setCreateDate(waybillEntity.getBillTime());
		//运输性质
		packagingRequire.setProductType(waybillEntity.getProductCode());
		//货物名称
		packagingRequire.setGoodsName(waybillEntity.getGoodsName());
		//开单时间
		packagingRequire.setWaybillCreateDate(waybillEntity.getBillTime());
		//代包装部门Code
		packagingRequire.setPackagingDeptCode(wooden.getPackageOrgCode());
		/**
		 * 将此处的代码稍作修改，避免空指针异常
		 * @author:218371-foss-zhaoyanjun
		 */
		/**
		 * 将此处的代码稍作修改，避免空指针异常
		 * @author:218371-foss-zhaoyanjun
		 */
		boolean flag=false;
		if (wooden != null &&
				(wooden.getStandGoodsNum()!=null
				|| wooden.getBoxGoodsNum()!=null 
				|| wooden.getSalverGoodsNum()!=null)){
			flag=true;
		}
		BigDecimal volume = BigDecimal.ZERO;
		if(wooden.getBoxGoodsVolume()!=null){
			volume = volume.add(wooden.getBoxGoodsVolume());
		}
		if(wooden.getStandGoodsVolume()!=null){
			volume = volume.add(wooden.getStandGoodsVolume());
		}
		packagingRequire.setNeedPackVolume(volume);
		
		/*if(wooden.getBoxGoodsVolume()!=null&&wooden.getStandGoodsVolume()!=null){
			// 需要包装的总体积
			BigDecimal volume = BigDecimal.valueOf(0).add(wooden.getBoxGoodsVolume()).add(wooden.getStandGoodsVolume());
			packagingRequire.setNeedPackVolume(volume);
		}*/
		Integer pieces = new Integer(0);
		if(wooden.getBoxGoodsNum()!=null){
			pieces = pieces + wooden.getBoxGoodsNum();
		}
		if(wooden.getStandGoodsNum()!=null){
			pieces = pieces + wooden.getStandGoodsNum();
		}
		packagingRequire.setNeedPackNum(pieces + salverPieces);
		
		/*if(wooden.getBoxGoodsNum()!=null&&wooden.getStandGoodsNum()!=null){
			// 需要包装的总件数
			int pieces = wooden.getBoxGoodsNum() + wooden.getStandGoodsNum() + salverPieces;  ////zxy 20131212 ISSUE-4391 DEFECT-625 修改：增加托的个数(即打托又打木算一件)
			packagingRequire.setNeedPackNum(pieces);
		}*/
		// 代包装要求
		String requirements = "".concat(wooden.getBoxRequirement()==null?"":wooden.getBoxRequirement())
				.concat(wooden.getStandRequirement()==null?"":wooden.getStandRequirement())
				.concat(wooden.getSalverRequirement()==null?"":wooden.getSalverRequirement());//zxy 20131212 ISSUE-4391 DEFECT-675 修改：加上木托要求
		if(StringUtils.isEmpty(requirements)){
			requirements = "No Package";
		}
		packagingRequire.setPackagingRequire(requirements);
		packagingRequire.setPackagingDept(wooden.getPackageOrgCode());
		//zxy 20131128 ISSUE-4391 start 修改：修改中转接口,把打木架新增删除list和打木托新增删除list统一放入包装信息对象中
		//新增
		if(oldVersionDto.getWoodenRequirementsEntity()==null){
			if(CollectionUtils.isNotEmpty(addSerialNos) || CollectionUtils.isNotEmpty(addSalverSerialNos)){
				packagingRequire.setAddWSerialNo(addSerialNos);
				packagingRequire.setAddMSerialNo(addSalverSerialNos);
				packOutService.addPackagingRequire(packagingRequire);
			}
		}else{
			if((CollectionUtils.isEmpty(addSerialNos) && CollectionUtils.isNotEmpty(deleteSerialNos))  
					|| (CollectionUtils.isEmpty(addSalverSerialNos) && CollectionUtils.isNotEmpty(deleteSalverSerialNos))){
				packagingRequire.setAddWSerialNo(addSerialNos);
				packagingRequire.setAddMSerialNo(addSalverSerialNos);
				packagingRequire.setDeleteWSerialNo(deleteSerialNos);
				packagingRequire.setDeleteMSerialNo(deleteSalverSerialNos);
				if(flag){
					packOutService.updatePackagingRequire(packagingRequire);
				}
			}else{
				//更新  没有list没有内容 就表示没有任何更改 
				if((CollectionUtils.isNotEmpty(addSerialNos) || CollectionUtils.isNotEmpty(addSalverSerialNos))){
					packagingRequire.setAddWSerialNo(addSerialNos);
					packagingRequire.setAddMSerialNo(addSalverSerialNos);
					packagingRequire.setDeleteWSerialNo(deleteSerialNos);
					packagingRequire.setDeleteMSerialNo(deleteSalverSerialNos);
					if(flag){
						packOutService.updatePackagingRequire(packagingRequire);
					}
				}
			}
			
		}
		//zxy 20131128 ISSUE-4391 end 修改：修改中转接口,把打木架新增删除list和打木托新增删除list统一放入包装信息对象中
	
	}
	/**
	 * 更改单受理拒绝操作
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#refuseWaybillRfcOpinion(java.lang.String, java.lang.String)
	 */
	@Transactional
	public boolean refuseWaybillRfcOpinion(WaybillRfcEntity waybillRfcEntity, String notes, CurrentInfo currentInfo) {
		Date currentDate = new Date();
		//1、判断拒绝时是否有备注
		if(StringUtils.isEmpty(notes)){
			/**
			 * 请填写拒绝受理备注
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.MUST_FILL_IN_ACCEPT_NOTES);
		}else{
			waybillRfcEntity.setNotes(notes);
		}
		
		/**
  		 * 判断受理之前状态是否是待受理
  		 */
  		if(!WaybillRfcConstants.PRE_ACCECPT.equals(waybillRfcEntity.getStatus())){
  			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_ACCECPTED);
  		}
		
		//3、新增更改记录
		WaybillDto newVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getNewVersionWaybillId());
		WaybillDto oldVersionDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getOldVersionWaybillId());	
		handleNewOldWaybillEntity(newVersionDto,oldVersionDto,WaybillRfcConstants.ACCECPT_DENY,waybillRfcEntity, currentInfo);
		//4、更新更改单
		waybillRfcEntity.setStatus(WaybillRfcConstants.ACCECPT_DENY);
  		waybillRfcEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
  		waybillRfcEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
  		waybillRfcEntity.setOperateTime(currentDate);
  		waybillRfcEntity.setOperator(currentInfo.getEmpName());
  		waybillRfcEntity.setOperatorCode(currentInfo.getEmpCode());
		waybillRfcDao.updateByPrimaryKeySelective(waybillRfcEntity);
		//5、历史记录
		WaybillRfcActionHistoryEntity actionHistory = this.getActionHistoryFromWaybillRfcEntity(waybillRfcEntity, currentDate);
		waybillRfcVarificationDao.addWaybillRfcActionHistory(actionHistory);
		//将冗余表的信息删除
		labeledGoodChangeDao.deleteLabeledGoodChangeInProcessByWaybillNo( waybillRfcEntity.getWaybillNo()); 
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(waybillRfcEntity.getOldVersionWaybillId());
  		if(waybillEntity.getProductCode().equals(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.ROUND_COUPON_PACKAGE)||
  				waybillEntity.getProductCode().equals(WaybillConstants.PACKAGE)){
		pickupToDoMsgService.removeOneToDoMsg(
				DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT_WAYBILL,
				null, waybillRfcEntity.getId());
  		}else{
		//6、消息提醒
		pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT, null, waybillRfcEntity.getId());
  		}
		//7、生成更改单起草部门的消息提醒
		String context = getWaybillRfcDenyMessageContext(waybillRfcEntity, currentInfo);
		if(context == null){
			LOG.warn("未找到"+waybillRfcEntity.getOperateOrgCode()+"("+waybillRfcEntity.getOperateOrgName()+")"+"打印模板");
		}else{
			pickupInstationJobMessageService.createSysInstationMsgToOrg(waybillRfcEntity.getDraftOrgCode(), context);
		}
		return true;
	}
	
	/**
	 * 
	 * 获取更改单拒绝站内消息模板
	 * 贵部门于${draftTime}申请的运单变更，运单号：${waybillNo}已被拒绝受理，不会更新任何数据！受理备注：{note}
	 * 申请时间:draftTime
	 * 运单号:waybillNo
	 * 备注:note
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-11 上午8:59:56
	 */
	private String getWaybillRfcDenyMessageContext(WaybillRfcEntity waybillRfcEntity, CurrentInfo currentInfo) {
		SmsParamDto paramDto = new SmsParamDto();
		DateFormat df = new SimpleDateFormat("yyyy-M-d");
		
		paramDto.setSmsCode(NotifyCustomerConstants.SMS_CODE_WAYBILL_RFC_DENY);
		paramDto.setOrgCode(currentInfo.getCurrentDeptCode());
		Map<String,String> placeFieldMap = new HashMap<String, String>();
		placeFieldMap.put("draftTime", df.format(waybillRfcEntity.getDraftTime()));
		placeFieldMap.put("waybillNo", waybillRfcEntity.getWaybillNo());
		placeFieldMap.put("note", waybillRfcEntity.getNotes());
		paramDto.setMap(placeFieldMap);
		//获得短信内容
		String sendContent = sMSTempleteService.querySmsByParam(paramDto);
		return sendContent;
	}


	/**
	 * 
	 * <p>
	 * (审核 或者 受理同意后操作)</br>
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param rfcEntity
	 * void
	 */
	@Transactional
	public void doAfterVarification(WaybillRfcEntity rfcEntity,  WaybillDto newVersionDto,WaybillDto oldVersionDto, CurrentInfo currentInfo, boolean isSyncToPTP){
		String rfcType = rfcEntity.getRfcType();
		String waybillNumber = rfcEntity.getWaybillNo();
		/**
		 * 变更类型为作废运单的运单变更
		 * 1、运单状态变为“已作废”；
		 * 2、红冲财务类相关单据；
		 * 3、系统自动出库该运单
		 */
		
		//运单实体
		WaybillEntity oldWaybill = oldVersionDto.getWaybillEntity();
		WaybillEntity newWaybill = newVersionDto.getWaybillEntity();
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(oldWaybill.getId());
  		WaybillStockStatusDto stockStatusDto = waybillRfcService.queryWaybillStockStatus(rfcEntity.getWaybillNo(),waybillEntity);

		VestBatchResult vestResult = this.getVestResult(oldWaybill, "doAfterVarification");
		ActualFreightEntity actualFreight = actualFreightService.queryByWaybillNo(rfcEntity.getWaybillNo());
		//该单已经做过折扣，并且折扣单是未确认状态，请作废折扣单在更改！
		waybillPickupService.querydiscountPayable(waybillNumber);
		
		/**
		 * Dmana-4437修改了原有代码的位置，目的不变
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-08下午16:53
		 */
		//更改明细查询具体数据,这里查询不到具体数据，修改：2014-11-17 14:07:27   张兴旺
		List<WaybillRfcChangeDetailEntity> waybillrfcChangeDetailList = waybillRfcDao.queryRfcChangeDetail(rfcEntity.getId());
		if(CollectionUtils.isNotEmpty(waybillrfcChangeDetailList)){
			/**
			 * 增加“交易流水号”
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-26下午18:33
			 */
			String transactionSerialNumber=null;
			for(WaybillRfcChangeDetailEntity detail : waybillrfcChangeDetailList){
				/**
				 * 增加“交易流水号”
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-01-26下午18:33
				 */
				if("transactionSerialNumber".equals(detail.getRfcItems())){
					transactionSerialNumber=detail.getAfterRfcInfo();
				}
			}
			/**
			 * 增加“交易流水号”
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-26下午18:33
			 */
			if(StringUtils.isNotEmpty(transactionSerialNumber)){
				if(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(transactionSerialNumber)){
					actualFreight.setTransactionSerialNumber("");
				}else{
					actualFreight.setTransactionSerialNumber(transactionSerialNumber);
				}					
			}
		}
		
		if(WaybillRfcConstants.INVALID.equals(rfcType)){
			/**
			 * TODO 子母件子件的废除操作
			 * 如果是子母件的废除操作，
			 * 针对母件的废除，其关联的子件也一并废除
			 * 针对子件的废除，不予废除操作
			 */
			WaybillRelateDetailEntity  waybillRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNo(waybillNumber);
			if(waybillRelateDetailEntity!=null){
				//子母单
				WaybillRelateDetailEntity  waybillParentRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillParentRelateDetailByWaybillNo(waybillNumber);
				if(waybillParentRelateDetailEntity==null){
					/**
					 * 非母件  不能操作作废 操作
					 */
					throw new WaybillImportException(WaybillImportException.WAYBILL_CAN_NOT_INVALID);
				}else{
					/**
					 * 关联子单进行废除操作  不能操作作废 操作
					 */
					List<String> waybillNoList=new ArrayList<String>();
					waybillNoList.add(waybillNumber);
					List<WaybillRelateDetailEntity> waybillRelateDetailEntityList=waybillRelateDetailEntityDao.queryWaybillRelateDetailsByWaybillNos(waybillNoList,0,0,false) ;
					for(WaybillRelateDetailEntity entity:waybillRelateDetailEntityList){
						//子件判定
						if("N".equals(entity.getIsPicPackage())){
							WaybillEntity childrenWaybill=waybillDao.queryWaybillByNo(entity.getWaybillNo());
							//子件废除 中止操作
							childrenWayBillInvalidAndAbort(childrenWaybill,currentInfo,rfcType,rfcEntity);
						}
					}
					//运单更改实体对象的waybillNo重新设置成母件的waybillNo
					rfcEntity.setWaybillNo(waybillNumber);
				}
				
			}
			//设置运单状态为已作废
			actualFreight.setStatus(WaybillConstants.OBSOLETE);
			
			//删除代办
			labeledGoodTodoDao.deleteTodoByWaybillNo(rfcEntity.getWaybillNo());
			
			

			//BUG-44956删除代办			
			List<WaybillRfcEntity> oldRfcEntityList = waybillRfcDao.queryWaybillRfcEntityByNewVersionId(rfcEntity.getOldVersionWaybillId());
			while(CollectionUtils.isNotEmpty(oldRfcEntityList)){
				for(int i=0;i<oldRfcEntityList.size();i++){
					WaybillRfcEntity old = oldRfcEntityList.get(i);
					labeledGoodTodoDao.deleteTodoByWaybillRfcId(old.getId());
					oldRfcEntityList.remove(old);
				}			
			}
			//BUG-44956
			
			/**
			 * 如果作废，会遇到修改单号的场景，如果仅仅只是按照单号删除待办还不够，需要用现在单号的ID向后推出更改单ID，
			 * 例如单号B，查询newVersionID=B,的更改单a,在查询出a的oldVersionID=a.old 的更改单，把这些更改单的待办都删除
			 */
			
			// 红冲操作
			//调用红冲单据接口
			if(!isSyncToPTP || FossConstants.YES.equals(newVersionDto.getWaybillEntity().getIsWholeVehicle()) || !StringUtils.equals(actualFreight.getPartnerBillingLogo(),FossConstants.YES)
					//红冲合伙人空运费用--邹胜利
					||(WaybillConstants.AIR_FLIGHT.equals(newVersionDto.getWaybillEntity().getProductCode()))){
			    if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
				waybillPickupService.cancelWaybill(waybillNumber, currentInfo);
				}
			}
			//运单自动出库--一件一件出库
			List<InOutStockEntity> inOutStockList = getInOutStockEntity(rfcEntity, currentInfo);
			for(InOutStockEntity entity : inOutStockList){
				stockService.outStockPC(entity);				
			}
			//退回优惠卷
			if(!StringUtils.isEmpty(oldWaybill.getPromotionsCode()))
			{
				crmOrderService.effectCouponState(oldWaybill.getPromotionsCode());
			}			
			actualFreightService.updateWaybillActualFreight(actualFreight);
			
			//标识业务完结
			WaybillTransactionEntity te = new WaybillTransactionEntity();
			te.setWaybillNo(waybillNumber);
			waybillTransactionService.updateBusinessOver(te);
			/**
			 * 改变订单状态
			 */
	
			changeOrderStatus(oldWaybill, currentInfo);
			
			//将labelgood的active变成n
			List<LabeledGoodEntity> labeledGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybillNumber);
			
			if(CollectionUtils.isNotEmpty(labeledGoodList)){
				labeledGoodDao.deleteGoodEntityBatch(labeledGoodList);
			}
			
			/**
			 *返货开单新运单被终止和作废后，原单号恢复库存至被移动至特殊状态前的状态 StockService的backreturngoodsBills方法
			 */
			String returnType = WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO;
			WaybillExpressEntity expressEntity =waybillExpressService.queryWaybillByWaybillNo(waybillNumber, returnType);
			if(expressEntity!=null){
				//原单号
				String originalWaybillNo = expressEntity.getOriginalWaybillNo();
				stockService.backreturngoodsBills(originalWaybillNo);
				//新单开过返回单且新单存在付款信息不能中止
				if(repaymentService.isHaveRepayment(waybillNumber)){
					throw new WaybillRfcChangeException(waybillNumber+":该单开过返货单且该单存在付款信息不能中止");
				}
				//红冲单据财务单据
				newVersionDto.setActualFreightEntity(actualFreight);
				//灰度改造  3232098
				//waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(newVersionDto,originalWaybillNo),currentInfo);
				//灰度改造  3232098
				returnedGoodsWriteoffReceivableToGrayCubc(newVersionDto, originalWaybillNo,currentInfo);
			}
			//配合快递100做的轨迹推送
			addOneWaybillTrack(rfcEntity);
		}
		/**
		 * 变更类型为中止运单的运单变更，在审核同意后：
		 * 1、运单状态变为“已中止”；
		 * 2、红冲财务类相关单据
		 */
		else if(WaybillRfcConstants.ABORT.equals(rfcType)){
			/**
			 * TODO 子母件子件的废除操作
			 * 如果是子母件的废除操作，
			 * 针对母件的废除，其关联的子件也一并废除
			 * 针对子件的废除，不予废除操作
			 */
			WaybillRelateDetailEntity  waybillRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNo(waybillNumber);
			if(waybillRelateDetailEntity!=null){
				//子母单
				WaybillRelateDetailEntity  waybillParentRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillParentRelateDetailByWaybillNo(waybillNumber);
				if(waybillParentRelateDetailEntity==null){
					/**
					 * 非母件  不能操作作废 操作
					 */
					throw new WaybillImportException(WaybillImportException.WAYBILL_CAN_NOT_ABORT);
				}else{
					/**
					 * 关联子单进行废除操作  不能操作作废 操作
					 */
					List<String> waybillNoList=new ArrayList<String>();
					waybillNoList.add(waybillNumber);
					List<WaybillRelateDetailEntity> waybillRelateDetailEntityList=waybillRelateDetailEntityDao.queryWaybillRelateDetailsByWaybillNos(waybillNoList,0,0,false) ;
					for(WaybillRelateDetailEntity entity:waybillRelateDetailEntityList){
						//子件判定
						if("N".equals(entity.getIsPicPackage())){
							WaybillEntity childrenWaybill=waybillDao.queryWaybillByNo(entity.getWaybillNo());
							//子件废除 中止操作
							childrenWayBillInvalidAndAbort(childrenWaybill,currentInfo,rfcType,rfcEntity);
						}
					}
					//运单更改实体对象的waybillNo重新设置成母件的waybillNo
					rfcEntity.setWaybillNo(waybillNumber);
				}
				
			}

			//设置运单状态为已中止
			actualFreight.setStatus(WaybillConstants.ABORTED);
			//红冲财务单据
			if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
			    waybillPickupService.cancelWaybill(waybillNumber, currentInfo);
			}
			//运单自动出库--一件一件出库
			List<InOutStockEntity> inOutStockList = getInOutStockEntity(rfcEntity, currentInfo);
			for(InOutStockEntity entity : inOutStockList){
				stockService.outStockPC(entity);				
			}
			//退回优惠卷
			if(!StringUtils.isEmpty(oldWaybill.getPromotionsCode()))
			{
				crmOrderService.effectCouponState(oldWaybill.getPromotionsCode());
			}
			actualFreightService.updateWaybillActualFreight(actualFreight);

			//标识业务完结
			WaybillTransactionEntity te = new WaybillTransactionEntity();
			te.setWaybillNo(waybillNumber);
			waybillTransactionService.updateBusinessOver(te);
			/**
			 * 改变订单状态
			 */
			changeOrderStatus(oldWaybill, currentInfo);
			
			List<LabeledGoodEntity> labeledGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybillNumber);
			
			if(CollectionUtils.isNotEmpty(labeledGoodList)){
				labeledGoodDao.deleteGoodEntityBatch(labeledGoodList);
			}
			
			/**
			 *返货开单新运单被终止和作废后，原单号恢复库存至被移动至特殊状态前的状态 StockService的backreturngoodsBills方法
			 */
			String returnType = WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO;
			WaybillExpressEntity expressEntity =waybillExpressService.queryWaybillByWaybillNo(waybillNumber, returnType);
			if(expressEntity!=null){
				//原单号
				String originalWaybillNo = expressEntity.getOriginalWaybillNo();
				stockService.backreturngoodsBills(originalWaybillNo);
				//新单开过返回单且新单存在付款信息不能中止
				if(repaymentService.isHaveRepayment(waybillNumber)){
					throw new WaybillRfcChangeException(waybillNumber+":该单开过返货单且该单存在付款信息不能中止");
				}
				//红冲单据财务单据
				newVersionDto.setActualFreightEntity(actualFreight);
				//灰度改造  3232098
				//waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(newVersionDto,originalWaybillNo),currentInfo);
				//灰度改造  3232098
				returnedGoodsWriteoffReceivableToGrayCubc(newVersionDto, originalWaybillNo,currentInfo);
			}
			//配合快递100做的轨迹推送
			addOneWaybillTrack(rfcEntity);
			//将labelgood的active变成n
		}else{	
			//不为CUBC(灰度接口异常或者为FOSS)，都走FOSS
			if(WaybillConstants.INNER_PICKUP.equals(oldWaybill.getReceiveMethod()) && 
					WaybillConstants.INNER_PICKUP.equals(newWaybill.getReceiveMethod())){
				//如果原来是内部带货并且现在也是内部带货，则不需要更新财务信息
			}else if(WaybillConstants.INNER_PICKUP.equals(oldWaybill.getReceiveMethod()) && 
					!WaybillConstants.INNER_PICKUP.equals(newWaybill.getReceiveMethod())){
				//如果原来是内部带货并且现在不是内部带货，则需要更新财务信息
				if((!isSyncToPTP || FossConstants.YES.equals(newVersionDto.getWaybillEntity().getIsWholeVehicle()) || !StringUtils.equals(actualFreight.getPartnerBillingLogo(),FossConstants.YES)
						//配合 合伙人空运更改单处理财务单据--邹胜利
						||(WaybillConstants.AIR_FLIGHT.equals(newVersionDto.getWaybillEntity().getProductCode())))){
					//DP-FOSS zhaoyiqing 343617 2016-10-26 配合CUBC将校验提前
                    modifyWaybillValidateForCUBC(convertWaybillToDto(newWaybill,oldVersionDto,actualFreight));
                    //DP-FOSS zhaoyiqing 343617 2016-11-07 提交校验信用额度
                    this.getBeBebtForCUBC(oldWaybill, newWaybill);
                    if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
                    waybillPickupService.modifyWaybill(convertWaybillToDto(oldWaybill,oldVersionDto,actualFreight),
							convertWaybillToDto(newWaybill,oldVersionDto,actualFreight), currentInfo);}
				}
			}else if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())&&!WaybillConstants.INNER_PICKUP.equals(oldWaybill.getReceiveMethod()) && 
					WaybillConstants.INNER_PICKUP.equals(newWaybill.getReceiveMethod())){
				//如果原来不是内部带货并且现在是内部带货，则需要红冲财务单据
				waybillPickupService.cancelWaybill(waybillNumber, currentInfo);
			}else{
				//zxy 20140414 DEFECT-2553 start 新增:整车业务时,装卸费发生变更,需要红冲财务
				boolean bChlServiceFee = false;
				if(FossConstants.YES.equals(newVersionDto.getWaybillEntity().getIsWholeVehicle())){
					BigDecimal newServiceFee = newVersionDto.getWaybillEntity().getServiceFee();
					BigDecimal oldServiceFee = oldVersionDto.getWaybillEntity().getServiceFee();
					if(newServiceFee != null && oldServiceFee != null && newServiceFee.compareTo(oldServiceFee) != 0){
						bChlServiceFee = true;
					}
				}
				//装卸费为空或0，装卸费是折让方式 否则就是加收方式
				boolean isZr =false;
				CustomerEntity cus =customerService.queryCustomerInfoByCusCode(newWaybill.getDeliveryCustomerCode());
				if(null!=cus && StringUtils.isNotBlank(cus.getExpHandChargeBusiType())){
					String busiTye =cus.getExpHandChargeBusiType().trim();
					//加收
					if(busiTye.equals("CHARGETYPE")){
						isZr=false;
					}
					//折让
					else if(busiTye.equals("DISCOUNTTYPE")){
						isZr=true;
					}else{
						actualFreight.setDcServicefee(BigDecimal.ZERO);
					}
				} 
				//zxy 20140414 DEFECT-2553 end 新增:整车业务时,装卸费发生变更,需要红冲财务
				//如涉及财务类信息变更，更新财务信息
				if(FossConstants.ACTIVE.equals(rfcEntity.getIsFinanceChange()) || bChlServiceFee || null!=actualFreight.getDcServicefee()){
					if(null!=actualFreight.getDcServicefee() && isZr){
						newWaybill.setServiceFee(actualFreight.getDcServicefee());
					}
					if(!isSyncToPTP || FossConstants.YES.equals(newVersionDto.getWaybillEntity().getIsWholeVehicle()) || !StringUtils.equals(actualFreight.getPartnerBillingLogo(),FossConstants.YES)
							//配合 合伙人空运更改单处理财务单据--邹胜利
							||(WaybillConstants.AIR_FLIGHT.equals(newVersionDto.getWaybillEntity().getProductCode()))){
                        //DP-FOSS zhaoyiqing 343617 2016-10-26 配合CUBC将校验提前
                        modifyWaybillValidateForCUBC(convertWaybillToDto(newWaybill,oldVersionDto,actualFreight));
                        //DP-FOSS zhaoyiqing 343617 2016-11-07 提交校验信用额度
                        this.getBeBebtForCUBC(oldWaybill, newWaybill);
			    if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
						waybillPickupService.modifyWaybill(convertWaybillToDto(oldWaybill,oldVersionDto,actualFreight), 
								convertWaybillToDto(newWaybill,oldVersionDto,actualFreight), currentInfo);
						}
					}
					if(null!=actualFreight.getDcServicefee() && isZr){
						newWaybill.setServiceFee(BigDecimal.ZERO);
				}
				}
			   if(!isZr){
				   actualFreight.setDcServicefee(BigDecimal.ZERO) ; 
			   }
			}			
			//修改运单
			getActualFreightEntity(actualFreight, oldWaybill, newWaybill);
		
//			//更改明细查询具体数据,这里查询不到具体数据，修改：2014-11-17 14:07:27   张兴旺
//			List<WaybillRfcChangeDetailEntity> waybillrfcChangeDetailList = waybillRfcDao.queryRfcChangeDetail(rfcEntity.getId());
			
			if(CollectionUtils.isNotEmpty(waybillrfcChangeDetailList)){
				String isExhibitCargo = null;
				String deliveryCustomerAddressNote = null;
				String receiveCustomerAddressNote = null;
				String packingTotle = null;
				String paperBoxTotlePrice = null;
				String fibelBagTotlePrice = null;
				String otherTotle = null;
//				/**
//				 * 增加“交易流水号”
//				 * @author:218371-foss-zhaoyanjun
//				 * @date:2015-01-26下午18:33
//				 */
//				String transactionSerialNumber=null;
				for(WaybillRfcChangeDetailEntity detail : waybillrfcChangeDetailList){
					if("deliveryCustomerAddressNote".equals(detail.getRfcItems())){
						deliveryCustomerAddressNote = detail.getAfterRfcInfo();
					}
					if("receiveCustomerAddressNote".equals(detail.getRfcItems())){
						receiveCustomerAddressNote = detail.getAfterRfcInfo();
					}
					//是否存在是否展货的情况
					if("isExhibitCargo".equals(detail.getRfcItems())){
						isExhibitCargo = detail.getAfterRfcInfo();
					}
					//修改纸箱纸纤信息 218371-foss-zhaoyanjun
					if("packingTotle".equals(detail.getRfcItems())){
						packingTotle = detail.getAfterRfcInfo();
					}
					if("paperBoxTotlePrice".equals(detail.getRfcItems())){
						paperBoxTotlePrice = detail.getAfterRfcInfo();
					}
					if("fibelBagTotlePrice".equals(detail.getRfcItems())){
						fibelBagTotlePrice = detail.getAfterRfcInfo();
					}
					if("otherTotle".equals(detail.getRfcItems())){
						otherTotle = detail.getAfterRfcInfo();
					}
//					/**
//					 * 增加“交易流水号”
//					 * @author:218371-foss-zhaoyanjun
//					 * @date:2015-01-26下午18:33
//					 */
//					if("transactionSerialNumber".equals(detail.getRfcItems())){
//						transactionSerialNumber=detail.getAfterRfcInfo();
//					}
				}
				if(StringUtils.isNotEmpty(receiveCustomerAddressNote)){
					actualFreight.setReceiveCustomerAddressNote(receiveCustomerAddressNote);
				}
				if(StringUtils.isNotEmpty(deliveryCustomerAddressNote)){
					actualFreight.setDeliveryCustomerAddressNote(deliveryCustomerAddressNote);
				}
				//设置是否展货,该需求与FOSS20141130的地址备注一致，请直接复制以下代码到里面
				if(StringUtils.isNotEmpty(isExhibitCargo) && (FossConstants.YES.equals(isExhibitCargo) || "是".equals(isExhibitCargo))){
					actualFreight.setIsExhibitCargo(FossConstants.YES);
				}else if(StringUtils.isNotEmpty(isExhibitCargo) && (FossConstants.NO.equals(isExhibitCargo) || "否".equals(isExhibitCargo))){
					actualFreight.setIsExhibitCargo(FossConstants.NO);
				}
				//修改纸箱纸纤信息 218371-foss-zhaoyanjun
				if(StringUtils.isNotEmpty(packingTotle)){
					if(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(packingTotle)){
						actualFreight.setPackingTotle(null);
					}else{
						actualFreight.setPackingTotle(new BigDecimal(packingTotle));
					}					
				}
				if(StringUtils.isNotEmpty(paperBoxTotlePrice)){
					if(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(paperBoxTotlePrice)){
						actualFreight.setPaperBoxTotlePrice(null);
					}else{
						actualFreight.setPaperBoxTotlePrice(new BigDecimal(paperBoxTotlePrice));
					}						
				}
				if(StringUtils.isNotEmpty(fibelBagTotlePrice)){
					if(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(fibelBagTotlePrice)){
						actualFreight.setFibelBagTotlePrice(null);
					}else{
						actualFreight.setFibelBagTotlePrice(new BigDecimal(fibelBagTotlePrice));
					}						
				}
				if(StringUtils.isNotEmpty(otherTotle)){
					if(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(otherTotle)){
						actualFreight.setOtherTotle(null);
					}else{
						actualFreight.setOtherTotle(new BigDecimal(otherTotle));
					}					
				}
//				/**
//				 * 增加“交易流水号”
//				 * @author:218371-foss-zhaoyanjun
//				 * @date:2015-01-26下午18:33
//				 */
//				if(StringUtils.isNotEmpty(transactionSerialNumber)){
//					if(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT.equals(transactionSerialNumber)){
//						actualFreight.setTransactionSerialNumber("");
//					}else{
//						actualFreight.setTransactionSerialNumber(transactionSerialNumber);
//					}					
//				}
			}
			//合伙人在出发部门发生更改时，费用不符合优惠券金额则退回优惠卷--zoushengli
			if(StringUtils.equals(actualFreight.getPartnerBillingLogo(),FossConstants.YES)
					&& WaybillRfcConstants.RECEIVE_STOCK.equals(stockStatusDto.getResult())
					&& StringUtils.isEmpty(newWaybill.getPromotionsCode())
					&& !StringUtils.isEmpty(oldWaybill.getPromotionsCode())){
				crmOrderService.effectCouponState(oldWaybill.getPromotionsCode());
			}
			actualFreightService.updateWaybillActualFreight(actualFreight);
		}
		
		
		
		/**
		 * 如果是更改目的站，调用接口（更新运单到达件数）
		 */
//		boolean isTaskDetailTypeCO=false;
		if (!newWaybill.getCustomerPickupOrgCode().equals(
				oldWaybill.getCustomerPickupOrgCode())) {
			String productCode = newWaybill.getProductCode();
			
			//转寄退回件
			
			ReturnGoodsRequestEntity returnGoodsRequestEntity = 
					returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo(newWaybill.getWaybillNo());
			
			if(null!=returnGoodsRequestEntity&&returnGoodsRequestEntity.getReturnStatus().equals("HANDLED")){
			
			/**
			 * BUGKD-1635
			 * 
			 * 若为经济快递，且目的站不一致，则一定为补码造成
			 */
			}else{
				if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, newWaybill.getBillTime())){
					throw new WaybillRfcChangeException("该运单在起草的更改未处理完成期间进行了补码，操作导致目的站发生变更，无法进行后续更改，请重新起草更改！");
				}
			}
			
			/**
			 * 如果不是偏线或者不是空运时且如果最终目的部门是驻地部门且对应的外场在走货路径内
			 */
			if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
					.equals(productCode)
					&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
							.equals(productCode)) {

				SaleDepartmentEntity saleDepartment = saleDepartmentService
						.querySaleDepartmentByCode(newWaybill
								.getCustomerPickupOrgCode());

				if (saleDepartment == null) {
					throw new WaybillRfcChangeException("查询到达部门实体为空！");
				}

				// 是否驻地部门
				if (saleDepartment.checkStation()) {

//					String transferCenter = saleDepartment.getTransferCenter();// 驻地部门对应外场
//					TransportPathEntity transportPathEntity = transportationPathDao
//							.queryTransportPath(oldWaybill.getWaybillNo());
//					if(transportPathEntity == null){
//						throw new WaybillRfcChangeException("未查询到该运单的走货路径！");
//					}
//					
//					List<LabeledGoodEntity>  labeledGoodEntitys= labeledGoodService.queryAllSerialByWaybillNo(oldWaybill.getWaybillNo());
//					
//					
//					if (StringUtils.equals(transportPathEntity.getIfPartialStowage(),
//							TransportPathConstants.NOTPARTIALSTOWAGE)) 
//					{
//						// 如果不分批
//						
//						isTaskDetailTypeCO=getIsNeedChange(oldWaybill,labeledGoodEntitys.get(0).getSerialNo(),transferCenter);
//						
//						
//					}else{
//						// 如果分批
//						for(LabeledGoodEntity labeledGoodEntity:labeledGoodEntitys)
//						{
//							
//							isTaskDetailTypeCO=getIsNeedChange(oldWaybill,labeledGoodEntity.getSerialNo(),transferCenter);
//							
//							if(isTaskDetailTypeCO){
//								break;//跳出循环
//							}
//						}
//					}
					
					
					/**
					 * 改变到达件数：如果不是偏线或者不是空运时且如果最终目的部门是驻地部门且对应的外场在走货路径内，
					 * 传入为TaskDetailType为CO，其他情况为CN
					 */
					changeArriveItime(newWaybill.getWaybillNo(), true);
				

				}

			}
		
	}
		
		
	}

    //DP-FOSS zhaoyiqing 343617 2016-11-07 更改单付款方式为临欠或月结，提交校验信用额度
    @Override
    public void getBeBebtForCUBC(WaybillEntity oldWaybill, WaybillEntity newWaybill) {
        if(!StringUtil.equals(newWaybill.getIsExpress(),WaybillConstants.YES)){
            if(StringUtil.equals(WaybillConstants.MONTH_PAYMENT,newWaybill.getPaidMethod())||
                    StringUtil.equals(WaybillConstants.TEMPORARY_DEBT,newWaybill.getPaidMethod())){
                //DP-FOSS 343617 zhaoyiqing 20170107
                //增加灰度接口
                RequestDO requestDO = new RequestDO();
                requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".agreeWaybillRfcCheckNoAuthority");
                requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
                requestDO.setCustomerCodes(newWaybill.getDeliveryCustomerCode());
                requestDO.setCustomerType(Constants.GRAY_SOURCE_BILLTYPE_CU);
                VestResponse response;try{
                    response = grayScaleService.vestByCustomer(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
                }catch (Exception e){
                	LOG.info(Constants.GRAY_SERVICE_EXCEPTION+e.getMessage());
                    return;
                }
                if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
                    List<VestBatchResult> batchResults = response.getVestBatchResult();
                    VestBatchResult batchResult = batchResults.get(0);
                    if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_CUBC, batchResult.getVestSystemCode())) {
                        //如果更改单发货客户是同一个，金额是更改前后差值
                        if(oldWaybill.getDeliveryCustomerCode().equals(newWaybill.getDeliveryCustomerCode())){
                            BigDecimal changeAmount = (newWaybill.getPrePayAmount()).subtract(oldWaybill.getPrePayAmount());
                            //差额大于0，则进行额度校验
                            if(changeAmount.compareTo(BigDecimal.ZERO)>0){
                                waybillRfcBeBebtForCUBC(newWaybill, changeAmount);
                            }
                        }else{
                            //如果更改前后客户不是同一个，金额是预付金额
                            waybillRfcBeBebtForCUBC(newWaybill, newWaybill.getPrePayAmount());
                        }
                    }
                }
            }
        }
    }

    //DP-FOSS 343617 zhaoyiqing 抽取方法，减少if判断嵌套
    private void waybillRfcBeBebtForCUBC(WaybillEntity newWaybill, BigDecimal changeAmount) {
        DebitForCUBCDto beBebtForCUBC = waybillManagerService.isBeBebtForCUBC(newWaybill.getDeliveryCustomerCode(), newWaybill.getReceiveOrgCode(), newWaybill.getPaidMethod(), changeAmount,newWaybill.getWaybillNo());
        if(!beBebtForCUBC.getIsSuccess()){
            if(StringUtil.isEmpty(beBebtForCUBC.getMsg())){
                throw new WaybillRfcChangeException("CUBC接口传入参数异常！");
            }
            throw new WaybillRfcChangeException(beBebtForCUBC.getMsg());
        }
    }

    //DP-FOSS zhaoyiqing 343617 2016-10-26 配合CUBC，将原来在结算中的校验提前
    private void modifyWaybillValidateForCUBC(WaybillPickupInfoDto waybill) {
        //非快递提交才能走这个逻辑
        if(!StringUtil.equals(waybill.getIsExpress(),WaybillConstants.YES)){

            // 校验运单的收货部门、接货部门、到达部门、录入部门为空
            if (StringUtils.isEmpty(waybill.getCreateOrgCode())
                    || StringUtils.isEmpty(waybill.getReceiveOrgCode())
                    || StringUtils.isEmpty(waybill.getLastLoadOrgCode())) {
                throw new WaybillRfcChangeException("部门信息不完整");
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
                throw new WaybillRfcChangeException("运单部分字段金额不正确，不能继续操作");
            }


            // 校验发票标记是在范围内
            if (StringUtil.isEmpty(waybill.getInvoiceMark())) {
                throw new WaybillRfcChangeException("没有发票标记");
            }
            else if (!WaybillConstants.INVOICE_01.equals(waybill.getInvoiceMark())
                    && !WaybillConstants.INVOICE_02.equals(waybill.getInvoiceMark())) {
                throw new WaybillRfcChangeException("发票标记不在范围之内："+waybill.getInvoiceMark());
            }
        }


    }

    /**
	 * 
	 * 关联子件废除/中止操作
	 * @param oldWaybill
	 * @param currentInfo
	 * @author 283250-Liuyi
	 * @date 2015-09-25 14:51:00
	 */
	private void  childrenWayBillInvalidAndAbort(WaybillEntity waybill, CurrentInfo currentInfo,String rfcType,WaybillRfcEntity rfcEntity) {
		//该单已经做过折扣，并且折扣单是未确认状态，请作废折扣单在更改！
		waybillPickupService.querydiscountPayable(waybill.getWaybillNo());
		
		//运单更改实体对象的waybillNo重新设置成子件的waybillNo
		rfcEntity.setWaybillNo(waybill.getWaybillNo());
		
		ActualFreightEntity actualFreight = actualFreightService.queryByWaybillNo(waybill.getWaybillNo());
		
		if(WaybillRfcConstants.INVALID.equals(rfcType)){
			//设置运单状态为已作废
			actualFreight.setStatus(WaybillConstants.OBSOLETE);
			
			//删除代办
			
			labeledGoodTodoDao.deleteTodoByWaybillNo(waybill.getWaybillNo());
		}else if(WaybillRfcConstants.ABORT.equals(rfcType)){
			//设置运单状态为已中止
			actualFreight.setStatus(WaybillConstants.ABORTED);
			
		}
		
		//运单自动出库--一件一件出库
		List<InOutStockEntity> inOutStockList = getInOutStockEntity(rfcEntity, currentInfo);
		for(InOutStockEntity entity : inOutStockList){
			stockService.outStockPC(entity);				
		}
		
		//退回优惠卷
		if(!StringUtils.isEmpty(waybill.getPromotionsCode()))
		{
			crmOrderService.effectCouponState(waybill.getPromotionsCode());
		}
		actualFreightService.updateWaybillActualFreight(actualFreight);

		//标识业务完结
		WaybillTransactionEntity te = new WaybillTransactionEntity();
		te.setWaybillNo(waybill.getWaybillNo());
		waybillTransactionService.updateBusinessOver(te);
		
		//改变订单状态
		changeOrderStatus(waybill, currentInfo);
		
		//将labelgood的active变成n  
		List<LabeledGoodEntity> labeledGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybill.getWaybillNo());
		if(CollectionUtils.isNotEmpty(labeledGoodList)){
			labeledGoodDao.deleteGoodEntityBatch(labeledGoodList);
		}
		
		//配合快递100做的轨迹推送
		addOneWaybillTrack(rfcEntity);
	}
	
	/**
	 * 返货开单传递给结算封装实体！
	 */
	private WaybillPickupInfoDto gainOrignalWaybillPickUpInfo(
			WaybillDto waybillDto,String originalWaybillNo) {
		// 运单基本信息
		WaybillEntity waybill = waybillManagerService.queryWaybillNo(originalWaybillNo);
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
			dto.setWaybillNo(originalWaybillNo);
		} catch (Exception e) {
			// 添加异常日志
			LOG.error("Excepton", e);
			// 抛出异常信息
			throw new WaybillSubmitException(
					WaybillSubmitException.COPYWAYBILLINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getMessage()) });
		}

		// 出发部门
		SaleDepartmentEntity leaveDept = saleDepartmentService
				.querySaleDepartmentByCode(waybill.getReceiveOrgCode());
		// 最终配载部门
		SaleDepartmentEntity arriveDept = saleDepartmentService
				.querySaleDepartmentByCode(waybill.getLastLoadOrgCode());
		// 增加使用新增的产品和是否快递字段判断
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())) {

			OrgAdministrativeInfoEntity org = this.queryOrgInfoByCode(waybill
					.getCustomerPickupOrgCode());
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
			dto.setPosSerialNum(waybillDto.getWaybillExpressEntity()
					.getPdaSerial());
			dto.setBatchNo(waybillDto.getWaybillExpressEntity()
					.getBankTradeSerail());
		}

		dto.setInvoiceMark(afhe.getInvoice());
		return dto;
	}
	
	
	/**
	 * 
	 * 改变订单状态
	 * @param oldWaybill
	 * @param currentInfo
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-12 下午2:57:45
	 */
	private void changeOrderStatus(WaybillEntity oldWaybill, CurrentInfo currentInfo) {
		if (oldWaybill.getOrderNo() != null) {
			CrmModifyInfoDto request = new CrmModifyInfoDto();
			/**
			 * 订单号
			 */
			request.setOrderNumber(oldWaybill.getOrderNo());
			/**
			 * 运单号
			 */
			request.setWaybillNumber(oldWaybill.getWaybillNo());
			/**
			 * 状态
			 */
			request.setGoodsStatus(WaybillConstants.CRM_ORDER_ACCEPT);
			/**
			 * 操作部门编码
			 */
			request.setOprateDeptCode(currentInfo.getCurrentDeptCode());
			/**
			 * 操作人编码
			 */
			request.setOprateUserNum(currentInfo.getUser().getEmployee().getEmpCode());
			/**
			 * 调用CRMORDER接口
			 */
			ResultDto reg=		crmOrderJMSService.sendModifyOrder(request);
			if("0".equals(reg.getCode()))
			{
				throw new WaybillRfcChangeException(WaybillRfcChangeException.USE_CRM_SERVICE_FAIL);
			}
			
		}
	}
	
//	/**
//	 * 如果目的站是驻地部门且对应的外场是原走货路径中经过多外场
//	 * @param oldWaybill
//	 * @param waybillNO
//	 * @param transferCenter
//	 * @return
//	 */
//	private boolean getIsNeedChange(WaybillEntity oldWaybill,String waybillNO,String transferCenter)
//	{
//		boolean isTaskDetailTypeCO=false;
//		List<PathDetailEntity>  pathDetailEntitys=	calculateTransportPathService.queryTotalPath(oldWaybill.getWaybillNo(),waybillNO);
//		
//		
//		for (PathDetailEntity pathDetailEntity : pathDetailEntitys) {
//			/**
//			 * 如果驻地部门对应的外场属于
//			 */
//			if (StringUtils.equals(pathDetailEntity.getObjectiveOrgCode(), transferCenter)) {
//				isTaskDetailTypeCO= true;
//			}
//
//		}
//		
//		return isTaskDetailTypeCO;
//	}
	
	

	/**
	 * 改变到达件数：如果不是偏线或者不是空运时且如果最终目的部门是驻地部门且对应的外场在走货路径内，传入为TaskDetailType为CO，其他情况为CN
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-19
	 * @param rfcEntity
	 * @return void
	 */
	private void changeArriveItime(String waybillNo,boolean isTaskDetailTypeCO)
	{
		AutoTaskDTO dto=new AutoTaskDTO();
		dto.setTaskDetailId(waybillNo);
		if(isTaskDetailTypeCO){
			dto.setTaskDetailType("M");
		}else{
			dto.setTaskDetailType(FossConstants.NO);
		}
		arriveSheetManngerService.insertONTempForPKP(dto);
	}
	
	
	/**
	 * 
	 * <p>
	 * 运单其他信息封装<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param vo
	 * @return
	 * ActualFreightEntity
	 */
	private void getActualFreightEntity(ActualFreightEntity actualFreightEntity, WaybillEntity old, WaybillEntity vo){
		//运单号
		actualFreightEntity.setWaybillNo(vo.getWaybillNo());
		//货物名称
		actualFreightEntity.setGoodsName(vo.getGoodsName());
		//重量
		actualFreightEntity.setWeight(vo.getBillWeight());
		//体积
		actualFreightEntity.setVolume(vo.getGoodsVolumeTotal());
		//件数
		actualFreightEntity.setGoodsQty(vo.getGoodsQtyTotal());
		//尺寸
		actualFreightEntity.setDimension(StringUtils.isNotEmpty(vo.getGoodsSize()) ? vo.getGoodsSize() : "1*1*1*1");
		//保险声明价值
		actualFreightEntity.setInsuranceValue(vo.getInsuranceAmount());
		//包装费
		actualFreightEntity.setPackingFee( vo.getPackageFee()!=null ? vo.getPackageFee() : BigDecimal.valueOf(0));
		//送货费
		actualFreightEntity.setDeliverFee( vo.getDeliveryGoodsFee() != null ? vo.getDeliveryGoodsFee(): BigDecimal.valueOf(0));
		//装卸费
		actualFreightEntity.setLaborFee( vo.getServiceFee()!=null ? vo.getServiceFee() :  BigDecimal.valueOf(0));
		//代收货款
		actualFreightEntity.setCodAmount( vo.getCodAmount() != null ? vo.getCodAmount(): BigDecimal.valueOf(0));
		//增值费
		actualFreightEntity.setValueaddFee( vo.getValueAddFee() !=null ? vo.getValueAddFee(): BigDecimal.valueOf(0));
		//公布价运费
		actualFreightEntity.setFreight( vo.getTransportFee()!=null ? vo.getTransportFee(): BigDecimal.valueOf(0));
//		//到达件数:ARRIVE_GOODS_QTY = 新运单件数;
//		actualFreightEntity.setArriveGoodsQty(vo.getGoodsQtyTotal());
//		//到达未出库件数:ARRIVE_NOTOUT_GOODS_QTY =  ARRIVE_NOTOUT_GOODS_QTY + （新运单件数 - 老运单件数）
//		int noOutGoodsQty = actualFreightEntity.getArriveNotoutGoodsQty() + vo.getGoodsQtyTotal() - old.getGoodsQtyTotal();
//		if(noOutGoodsQty < 0){
//			noOutGoodsQty = 0;
//		}
//		actualFreightEntity.setArriveNotoutGoodsQty(noOutGoodsQty);

		//结清状态 
		//actualFreightEntity.setSettleStatus(FossConstants.NO);
		//结清时间
		//actualFreightEntity.setSettleTime(new Date());
		//通知状态
		//actualFreightEntity.setNotificationType(vo.get)
		//通知时间
		//actualFreightEntity.setNotificationTime(vo);
		//送货时间 
		//actualFreightEntity.setDeliverDate(vo.getPreCustomerPickupTime());
		//实际送货方式
		//actualFreightEntity.setActualDeliverType(vo.getReceiveMethod().getValueCode());
		//库存天数
		//actualFreightEntity.setStorageDay(vo.getst)
		//库存费用
		//actualFreightEntity.setStorageCharge(vo.get);
		//actualFreightEntity.setStartStockOrgCode(waybillManagerService.queryStartStocksDepartmentService(waybillEntity));
		actualFreightEntity.setEndStockOrgCode(queryEndStocksDepartmentService(vo));
		
		waybillStockService.getEndStockCodeAndAreaCode(actualFreightEntity, vo);
		
		//actualFreightEntity.setActualDeliverType(vo.getdelivery)
	}
	
	/**
	 * 
	 * 拷贝GUI端计算最终库存部门逻辑
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-18 下午5:53:37
	 */
	private String queryEndStocksDepartmentService(WaybillEntity waybillEntry) {
		if (waybillEntry == null) {
			/**
			 * 运单实体为空！
			 */
			throw new WaybillStoreException(WaybillStoreException.WAYBILL_ENTITY_IS_NULL);
		}
		if (waybillEntry.getLastLoadOrgCode() == null) {
			/**
			 * 最终配载为空！
			 */
			throw new WaybillStoreException(WaybillStoreException.FINAL_LOAD_DEPT_IS_NULL);
		}
		
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
				.querySaleDepartmentByCode(waybillEntry.getLastLoadOrgCode());
		// 如果是驻地部门，返回驻地部门外场
		if (saleDepartmentEntity != null && FossConstants.YES.equals(saleDepartmentEntity.getStation())) {
			return saleDepartmentEntity.getTransferCenter();// 如果是，返回驻地营业部
		}

		return waybillEntry.getLastLoadOrgCode();// 如果不是驻地部门，返回最终配载部门

	}
	
	/**
	 * 
	 * <p>
	 * 获得出库实体列表<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param waybillRfcEntity
	 * @return
	 * List<InOutStockEntity>
	 */
	private List<InOutStockEntity> getInOutStockEntity(WaybillRfcEntity waybillRfcEntity, CurrentInfo currentInfo){
		String waybillNumber = waybillRfcEntity.getWaybillNo();
		//操作人工号
		String operatorCode = currentInfo.getEmpCode();
		//操作人姓名
		String operatorName = currentInfo.getEmpName();
		List<InOutStockEntity> stockList = new ArrayList<InOutStockEntity>();
		//根据运单号查询所有的货签信息
		List<LabeledGoodEntity> labelGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybillNumber);
		//生成出库实体list
		for(LabeledGoodEntity entity:labelGoodList){
			InOutStockEntity inOutStockEntity = new InOutStockEntity();
			
			//根据运单号、流水号找当前库存部门
			StockEntity stockEntity = stockService.queryUniqueStock(waybillNumber, entity.getSerialNo());
			//货物在库存时则出库
			if(stockEntity != null){
				//出库人
				inOutStockEntity.setCreateUser(operatorCode);
				//出入库设备类型PC
				inOutStockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
				//出库时间
				inOutStockEntity.setCreateDate(new Date());
				//运单号
				inOutStockEntity.setWaybillNO(waybillNumber);
				//从货签信息获得流水号
				inOutStockEntity.setSerialNO(entity.getSerialNo());
				//操作人工号
				inOutStockEntity.setOperatorCode(operatorCode);
				//操作人姓名
				inOutStockEntity.setOperatorName(operatorName);
				//出入库类型--运单作废出库
				inOutStockEntity.setInOutStockType(StockConstants.WAYBILL_INVALID_OUT_STOCK_TYPE);
				//部门编号
				inOutStockEntity.setOrgCode(stockEntity.getOrgCode());

				stockList.add(inOutStockEntity);
			}
			
		}
		return stockList;
	}
	
	/**
	 * 
	 * <p>
	 * 根据更改单生成操作记录实体<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcEntity
	 * @return WaybillRfcActionHistoryEntity 操作记录实体
	 */
	private WaybillRfcActionHistoryEntity getActionHistoryFromWaybillRfcEntity(WaybillRfcEntity waybillRfcEntity, Date currentDate){
		WaybillRfcActionHistoryEntity  actionHistory = new WaybillRfcActionHistoryEntity();//运单变更历史记录
		actionHistory.setCreateDate(currentDate);// 创建日期
		actionHistory.setCreateUser(waybillRfcEntity.getOperatorCode());// 创建人
		actionHistory.setModifyDate(currentDate);// 修改日期
		actionHistory.setModifyUser(waybillRfcEntity.getOperatorCode());// 修改人
		actionHistory.setNotes(waybillRfcEntity.getNotes());//备注
		actionHistory.setOperateOrgCode(waybillRfcEntity.getOperateOrgCode());//操作部门编码
		actionHistory.setOperateOrgName(waybillRfcEntity.getOperateOrgName());//操作部门
		actionHistory.setOperateTime(waybillRfcEntity.getOperateTime());//操作时间
		actionHistory.setOperator(waybillRfcEntity.getOperator());//操作人
		actionHistory.setOperatorCode(waybillRfcEntity.getOperatorCode());//操作人编码
		actionHistory.setStatus(waybillRfcEntity.getStatus());//更改单状态
		actionHistory.setWaybillRfcId(waybillRfcEntity.getId());//id
		return actionHistory;
	}
	/**
     * 
     * <p>
     * 处理新版、旧版运单数据<br />
     * </p>
     * @author suyujun
     * @version 0.1 2012-12-1
     * @param newWaybillEntity
     * @param oldWaybillEntity
     * void
     */
	@Transactional
	public void handleNewOldWaybillEntity(WaybillDto newVersionDto,WaybillDto oldVersionDto,String verifyResult,WaybillRfcEntity rfcEntity, CurrentInfo currentInfo) {
		//获得新版运单Dto
		if(newVersionDto==null ||  oldVersionDto==null){
			/**
			 * 新旧运单数据异常，检查运单的Id及运单号！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.NEW_WAYBILL_AND_OLD_WAYBILL_IS_EXCEPTION);
		}		
		//运单实体
		WaybillEntity oldWaybill = oldVersionDto.getWaybillEntity();
		WaybillEntity newWaybill = newVersionDto.getWaybillEntity();
		//运单费用明细
		List<WaybillChargeDtlEntity> oldChargeDtlList = oldVersionDto.getWaybillChargeDtlEntity();
		List<WaybillChargeDtlEntity> newChargeDtlList = newVersionDto.getWaybillChargeDtlEntity();
		//运单折扣明细
		List<WaybillDisDtlEntity> oldWaybillDisDtlList = oldVersionDto.getWaybillDisDtlEntity();
		List<WaybillDisDtlEntity> newWaybillDisDtlList = newVersionDto.getWaybillDisDtlEntity();
		//付款明细
		List<WaybillPaymentEntity> oldWaybillPaymentList = oldVersionDto.getWaybillPaymentEntity();
		List<WaybillPaymentEntity> newWaybillPaymentList = newVersionDto.getWaybillPaymentEntity();
		//代打木架
		// 查询条件封装对象实例化
		LastWaybillRfcQueryDto queryDto = new LastWaybillRfcQueryDto();
		// 运单号
		queryDto.setWaybillNo(oldVersionDto.getWaybillEntity().getWaybillNo());
		
		queryDto.setActive(oldVersionDto.getWaybillEntity().getActive());
		queryDto.setCreateTime(oldVersionDto.getWaybillEntity().getCreateTime());
		WoodenRequirementsEntity oldWoodRequirements = oldVersionDto.getWoodenRequirementsEntity();// oldWoodRequirement可能是null
		if(oldWoodRequirements ==null){
			oldWoodRequirements = woodenRequirementsService.queryNewWoodenRequirements(queryDto);
			oldVersionDto.setWoodenRequirementsEntity(oldWoodRequirements);
		}
		WoodenRequirementsEntity newWoodRequirements = newVersionDto.getWoodenRequirementsEntity();
		
		//审核处理结果为同意，
		if(WaybillRfcConstants.ACCECPT.equals(verifyResult)){
			Date now = new Date();
			
			//1、设置原运单状态为N
			oldWaybill.setActive(FossConstants.NO);
			oldWaybill.setModifyTime(now);
			
			//2、设置新运单状态Y
			newWaybill.setActive(FossConstants.YES);
			newWaybill.setPendingType(oldWaybill.getPendingType());
			newWaybill.setCreateOrgCode(oldWaybill.getCreateOrgCode());
			newWaybill.setCreateUser(oldWaybill.getCreateUser());
			newWaybill.setCreateUserCode(oldWaybill.getCreateUserCode());
			newWaybill.setCreateUserDeptName(oldWaybill.getCreateUserDeptName());
			newWaybill.setCreateUserName(oldWaybill.getCreateUserName());
			newWaybill.setCreateTime(now);
			newWaybill.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
			newWaybill.setModifyOrgCode(rfcEntity.getDraftOrgCode());
			newWaybill.setModifyUserCode(rfcEntity.getDrafterCode());
			String productCode=newWaybill.getProductCode();
			
			if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productCode) ||
					ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productCode) ||
					ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productCode) ||
					ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productCode)||
					ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productCode)||
					ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productCode)){
				newWaybill.setLastLoadOrgCode(newWaybill.getCustomerPickupOrgCode());
				newWaybill.setLastLoadOrgName(newWaybill.getCustomerPickupOrgName());
			}
			if(StringUtils.isEmpty(newVersionDto.getWaybillEntity().getCustomerPickupOrgName())){
				String orgcode =newVersionDto.getWaybillEntity().getCustomerPickupOrgCode();
				String prdCode =newVersionDto.getWaybillEntity().getProductCode();
				String  customerPickupOrgName= waybillDao.queryCustomerPickupOrgNameByWayno(orgcode,prdCode);
				newWaybill.setCustomerPickupOrgName(customerPickupOrgName);
			}
			
			if(StringUtils.isEmpty(newWaybill.getReceiveOrgName())){
				String orgcode =newWaybill.getReceiveOrgCode();
				String  receiveOrgName= waybillDao.queryReceiveOrgNameByWayno(orgcode);
				newWaybill.setReceiveOrgName(receiveOrgName);
			}

			//3、更新新版、旧版运单
			waybillManagerService.updateWaybillEntity(oldWaybill);
			waybillManagerService.updateWaybillEntity(newWaybill);
			/**
			 * BUGKD-1695 SIT环境：单号5013110201第二次更改未受理，再次补码可以成功
			 * 问题原因：没有更新快递分表的运单ID
			 * 
			 * 更新快递分表
			 */
			if(waybillExpressService.onlineDetermineIsExpressByProductCode(newWaybill.getProductCode(), newWaybill.getBillTime())){
				WaybillExpressEntity e = new WaybillExpressEntity();
				e.setWaybillId(newWaybill.getId());
				e.setWaybillNo(newWaybill.getWaybillNo());
				waybillExpressService.updateWaybillExpressByWaybillNo(e);
			}
			
			//4、设置原费用明细
			for(WaybillChargeDtlEntity charge : oldChargeDtlList){
				charge.setActive(FossConstants.NO);
				charge.setModifyTime(now);
			}
			
			//5、设置新费用明细
			for(WaybillChargeDtlEntity charge : newChargeDtlList){
				charge.setActive(FossConstants.YES);
				charge.setCreateTime(now);
				charge.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
			}
			
			//6、更新费用明细
			for(WaybillChargeDtlEntity charge : oldChargeDtlList){
				waybillChargeDtlService.updateByPrimaryKeySelective(charge);
			}
			for(WaybillChargeDtlEntity charge : newChargeDtlList){
				waybillChargeDtlService.updateByPrimaryKeySelective(charge);
			}
			
			//7、设置原版本折扣明细
			for(WaybillDisDtlEntity waybillDisDtlEntity : oldWaybillDisDtlList){
				waybillDisDtlEntity.setActive(FossConstants.NO);
				waybillDisDtlEntity.setModifyTime(now);
			}
			//8、设置新版本折扣明细
			for(WaybillDisDtlEntity waybillDisDtlEntity : newWaybillDisDtlList){
				waybillDisDtlEntity.setActive(FossConstants.YES);
				waybillDisDtlEntity.setCreateTime(now);
				waybillDisDtlEntity.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
			}
			//9、更新折扣明细
			for(WaybillDisDtlEntity disDtl : oldWaybillDisDtlList){
				waybillDisDtlService.updateByPrimaryKeySelective(disDtl);
			}
			//10、更新折扣明细
			for(WaybillDisDtlEntity disDtl : newWaybillDisDtlList){
				waybillDisDtlService.updateByPrimaryKeySelective(disDtl);
			}
			//11、设置原版本付款明细
			//12、设置新原版本付款明细
			//13、更新付款明细
			for(WaybillPaymentEntity waybillPaymentEntity : oldWaybillPaymentList){
				waybillPaymentEntity.setActive(FossConstants.NO);
				waybillPaymentEntity.setModifyTime(now);
			}
			for(WaybillPaymentEntity payment : oldWaybillPaymentList){
				waybillPaymentService.updateWaybillPaymentEntity(payment);
			}				
			
			for(WaybillPaymentEntity waybillPaymentEntity : newWaybillPaymentList){
				waybillPaymentEntity.setActive(FossConstants.YES);
				waybillPaymentEntity.setCreateTime(now);
				waybillPaymentEntity.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
			}
			for(WaybillPaymentEntity payment : newWaybillPaymentList){
				waybillPaymentService.updateWaybillPaymentEntity(payment);
			}				
			
			//代打木架
			if(oldWoodRequirements!=null){
				oldWoodRequirements.setActive(FossConstants.NO);
				oldWoodRequirements.setModifyTime(now);
				woodenRequirementsService.updateWoodenRequirements(oldWoodRequirements);				
			}
			if(newWoodRequirements!=null){
				newWoodRequirements.setActive(FossConstants.YES);
				newWoodRequirements.setCreateTime(now);
				newWoodRequirements.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
				woodenRequirementsService.updateWoodenRequirements(newWoodRequirements);		
				woodenRequirementsService.updateAllOtherWoodenRequirementsToNo(newWoodRequirements);
			}
		}else if(WaybillRfcConstants.ACCECPT_DENY.equals(verifyResult)){
			//受理失败，要把运单相应的记录删除和把更改单字段NEW_VERSION_WAYBILL_ID设置为null.
		}
	}
	
	/**
	 * 根据新旧运单信息 处理 新旧 合伙人运单信息
	 * @param newVersionDto 
	 * @param oldVersionDto
	 * @param now 
	 * @author 272311-sangwenhao
	 * @date 2016-2-17
	 */
	private void handlePartnerInfo(WaybillDto newVersionDto,WaybillDto oldVersionDto,Date now, CurrentInfo currentInfo,String isParnterAcceptStatus) {
		LOG.info("进入处理合伙信息");
		WaybillEntity oldWaybill = oldVersionDto.getWaybillEntity();
		WaybillEntity newWaybill = newVersionDto.getWaybillEntity();
		
		PartnersWaybillEntity oldPartner = oldVersionDto.getPartnersWaybillEntity() ;
		PartnersWaybillEntity newPartner = newVersionDto.getPartnersWaybillEntity() ;
		//改单前到达部门（提货部门）编号
		String oldOrgCode = StringUtils.isNotBlank(oldWaybill.getCustomerPickupOrgCode())?oldWaybill.getCustomerPickupOrgCode():oldWaybill.getLastLoadOrgCode();
		//改单后到达部门（提货部门）编号
		String newOrgCode = StringUtils.isNotBlank(newWaybill.getCustomerPickupOrgCode())?newWaybill.getCustomerPickupOrgCode():newWaybill.getLastLoadOrgCode();
		//改单前的运单中的到达部门（提货部门）
		SaleDepartmentEntity oldSaleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCodeCache(oldOrgCode);
		//1.根据运单号查询ActualFreightEntity对象
		ActualFreightEntity actualFreightEntity = waybillManagerService.queryActualFreightByNo(newWaybill.getWaybillNo());

		newVersionDto.setActualFreightEntity(actualFreightEntity);
		
		//开户银行信息，这些银行信息是补充的没有存在先关运单表中所以这样查询出来 2016年3月9日 08:00:45 葛亮亮				
		CusAccountEntity openBank = waybillRfcVarificationDao.queryCusAccountByWaybillInfo(
				newWaybill.getAccountName(), newWaybill.getAccountCode(), newWaybill.getAccountBank(),newWaybill.getDeliveryCustomerCode());
	
		/**
		 * CRM统一客户信息会作废一些客户，如果查询不到有效的客户信息，则获取创建时间最晚的一次的客户信息
		 */
		if(openBank==null){
			openBank=waybillRfcVarificationDao.queryCusAccountByCreateTime(
					newWaybill.getAccountName(), newWaybill.getAccountCode(), newWaybill.getAccountBank(),newWaybill.getDeliveryCustomerCode());
		}
		
		//2.判断是否合伙人开单
		if(StringUtils.equals(actualFreightEntity.getPartnerBillingLogo(),FossConstants.YES)){
			if(oldPartner!=null && newPartner!=null){//(当更改部门是合伙人部门时 oldPartner和newPartner才不为空)
				LOG.info("更改单: 开单部门是合伙人营业网点");
				//1 设置原运单状态为N
				oldPartner.setActive(FossConstants.NO);
				oldPartner.setModifyTime(now);
				
				//2、设置新运单状态Y
				newPartner.setActive(FossConstants.YES);
				newPartner.setCreateTime(now);
				newPartner.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
				
				//3、 更新合伙人新旧运单信息
				partnersWaybillService.updatePartnerWaybillById(oldPartner);
				partnersWaybillService.updatePartnerWaybillById(newPartner);
				
				//4 、推送合伙人运单信息给ptp系统				
				//当代收货款大于0注入将银行补充信息
				if(newWaybill.getCodAmount() != null && newWaybill.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
					newVersionDto.setOpenBank(openBank);
				}
				
				PtpWaybillDto ptpWaybillDto = ConvertBean.getPtpWaybillDto(newVersionDto,currentInfo);
				ptpWaybillDto.setIsChanged(FossConstants.YES);
				newVersionDto.setPtpWaybillDto(ptpWaybillDto);
				
				//创建部门标杆编码
				OrgAdministrativeInfoEntity currentDept = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getCreateOrgCode());
				ptpWaybillDto.setCurrentDeptUnifieldCode(currentDept.getUnifiedCode()); 
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
				
				SaleDepartmentEntity arriveDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(ptpWaybillDto.getCustomerPickupOrgCode());
				if(null != arriveDepartmentEntity && StringUtils.equals(arriveDepartmentEntity.getIsLeagueSaleDept(),FossConstants.YES)){
					ptpWaybillDto.setIsArriveDepPartner(true);//到达部门是合伙人
				}else{
					ptpWaybillDto.setIsArriveDepPartner(false);//到达部门是合伙人
				}
				ptpWaybillDto.setIsReceiveDepPartner(true);//出发部门是合伙人
				
				ptpWaybillDto.setRefId(oldPartner.getId());//原ID
				ptpWaybillDto.setRefCode(oldPartner.getWaybillNo());//原单号
				
				//添加 是否 合伙人受理更改单提成 标识     by:352676
				//并且在下一步封装ptpWaybillDto时做判断，如果是作废或者中止时，设置isParnterAcceptStatus为N
				ptpWaybillDto.setIsParnterAcceptStatus(isParnterAcceptStatus);
				
				ptpWaybillDto = getPtpWaybillDto(newVersionDto,newPartner) ;
				/**
				 * 合伙人二期第三批    合伙人零担更改单-作废逻辑优化   20161102  xingjun
				 * 合伙人开单 ，更改作废时，推送运单库存状态（运单的库存状态在始发外场的库存中或者以后的任意库存）到PTP
				 */
				if(WaybillRfcConstants.INVALID.equals(newPartner.getChangeType())){
					//通过运单号查询该运单当前的库存有没有经过第一外场或者有没有在第一外场库存
					int partnerWaybillStockStat = stockService.whetherPass(newVersionDto.getWaybillEntity().getWaybillNo());
					LOG.info("调用中转接口查询是否在第一外场或在第一外场之后，运单号："+newVersionDto.getWaybillEntity().getWaybillNo()+
							",中转返回状态："+partnerWaybillStockStat);
					//中转返回的库存状态是在第一外场或者经过第一外场
					if(FossConstants.SUCCESS == partnerWaybillStockStat)
						ptpWaybillDto.setWaybillStock(WaybillRfcConstants.FIRST_OUTSIDE_STOCK_OR_OUT);
				}
				//合伙人开单更改但都需要查询历史数据
				ptpWaybillDto.setIsSearchHistory(FossConstants.YES);
				//获取快递应该分析的时间
				Date lastDate = this.getLastWayBillDate(newWaybill, oldWaybill);
				ptpWaybillDto.setLastCreateTime(lastDate);
				//改单时判断是否变更目的站，并传递改单前网点交接单状态到PTP
				setWaybillToPartnerBranchStat(oldWaybill, oldOrgCode,
						newOrgCode, oldSaleDepartmentEntity, ptpWaybillDto);
				asynSendToPtp(newVersionDto, ptpWaybillDto);
			}
		}else{//不是合伙人开单
			LOG.info("更改单: 开单部门不是合伙人营业网点");
			//新的运单中的到达部门（提货部门）
			SaleDepartmentEntity newSaleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCodeCache(newOrgCode);
			
			//当到达部门是合伙人且代收货款大于0注入将银行补充信息
			if(newSaleDepartmentEntity != null && StringUtils.equals(newSaleDepartmentEntity.getIsLeagueSaleDept(),FossConstants.YES)
			   && newWaybill.getCodAmount() != null && newWaybill.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
			   newVersionDto.setOpenBank(openBank);
			}
			
			// 推送合伙人运单信息给ptp系统
			PtpWaybillDto ptpWaybillDto = ConvertBean.getPtpWaybillDto(newVersionDto,currentInfo);
			ptpWaybillDto.setIsChanged(FossConstants.YES);
			ptpWaybillDto.setIsReceiveDepPartner(false);//出发部门不是合伙人
			newVersionDto.setPtpWaybillDto(ptpWaybillDto);
			//传入  合伙人受理更改单提成 标识 by: 352676
			ptpWaybillDto.setIsParnterAcceptStatus(isParnterAcceptStatus);
			
			//创建部门标杆编码
			OrgAdministrativeInfoEntity currentDept = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCodeCache(ptpWaybillDto.getCreateOrgCode());
			ptpWaybillDto.setCurrentDeptUnifieldCode(currentDept.getUnifiedCode()); 
			
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
			
			ptpWaybillDto.setRefId(oldWaybill.getId());//原ID
			ptpWaybillDto.setRefCode(oldWaybill.getWaybillNo());//原单号
			OuterBranchEntity otherBrab = null;
			
			if(oldSaleDepartmentEntity == null){
	   		    //查询偏线信息 保证直营开单偏线更改为合伙人能推信息到ptp 邹胜利
				otherBrab = vehicleAgencyDeptDao.queryOuterBranchByCode(StringUtils.isNotBlank(oldWaybill.getCustomerPickupOrgCode())?oldWaybill.getCustomerPickupOrgCode():oldWaybill.getLastLoadOrgCode(), new Date());
			}
			//设置原运单状态为N
			if(null != oldPartner){
				oldPartner.setActive(FossConstants.NO);
				oldPartner.setModifyTime(now);
			}
			//添加偏线判断 邹胜利
			if(oldSaleDepartmentEntity!=null || otherBrab != null){
				
				//更改前到达部门 是合伙人网点
				if(null != oldSaleDepartmentEntity && StringUtils.equals(oldSaleDepartmentEntity.getIsLeagueSaleDept(),FossConstants.YES)){
					
					/*零担直营开单先开到合伙人，然后改目的站到直营,这时变成了直营到直营导致 newPartner 获取的类为空（因为数据不用存入pkp.t_srv_partners_waybill）
					所以获取更改但的状态需要从pkp.t_srv_waybillrfc中获取 2016年3月17日 16:50:11 葛亮亮*/
					String changeType = "";
					if(null == newPartner){		
						//根据运单ID获取改单状态
						WaybillRfcChangeDto waybillRfcChange = waybillRfcVarificationDao.queryWaybillRfcTypeById(newWaybill.getId());
						changeType = waybillRfcChange.getRfcType();
					}else{
						changeType = newPartner.getChangeType();
						//将新记录刷新为有效 2016年3月22日 14:52:03 葛亮亮
						newPartner.setActive(FossConstants.YES);
						newPartner.setCreateTime(now);
						newPartner.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
					}
					
					//更新合伙人新旧运单信息 2016年3月15日 16:44:43 葛亮亮
					partnersWaybillService.updatePartnerWaybillById(oldPartner);
					partnersWaybillService.updatePartnerWaybillById(newPartner);
					//传入  合伙人受理更改单提成 标识 by: 352676
					ptpWaybillDto.setIsParnterAcceptStatus(isParnterAcceptStatus);
					if(WaybillRfcConstants.ABORT.equals(changeType) || WaybillRfcConstants.INVALID.equals(changeType)){
						SaleDepartmentEntity arriveDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(ptpWaybillDto.getCustomerPickupOrgCode());
						if(null != arriveDepartmentEntity && StringUtils.equals(arriveDepartmentEntity.getIsLeagueSaleDept(),FossConstants.YES)){
							ptpWaybillDto.setIsArriveDepPartner(true);//到达部门是合伙人
						}else{
							ptpWaybillDto.setIsArriveDepPartner(false);//到达部门非合伙人
						}
						ptpWaybillDto.setFeeType(WaybillConstants.THREE);//0:成本  1：提成  2：成本和提成 3:作废
						//如果是终止或者作废的情况下，没有  合伙人受理更改单提成, 标识的值为N  2016年10月31日  by:352676
				  		ptpWaybillDto.setIsParnterAcceptStatus("N");
					}else{
						//更改后到达部门 是合伙人网点
						if(newSaleDepartmentEntity!=null && StringUtils.equals(newSaleDepartmentEntity.getIsLeagueSaleDept(),FossConstants.YES)){
							//推送信息 重新计算提成
							ptpWaybillDto.setIsArriveDepPartner(true);
							ptpWaybillDto.setFeeType(WaybillConstants.ONE);//0:成本  1：提成  2：成本和提成
							
						}else{//更改后到达部门不是合伙人网点
							ptpWaybillDto.setIsArriveDepPartner(false);
							ptpWaybillDto.setFeeType(WaybillConstants.THREE);//0:成本  1：提成  2：成本和提成 3:作废
						}
					}
					//获取快递应该分析的时间
					Date lastDate = this.getLastWayBillDate(newWaybill, oldWaybill);
					//判定老运单(被修改的运单)提货网点是否是合伙人，如果是则修改后的运单(新运单)需要传递需要查询历史数据标识和老运单的生成时间 2016年3月29日 15:26:51 葛亮亮
					ptpWaybillDto.setIsSearchHistory(FossConstants.YES);
					//老运单的生成时间
					ptpWaybillDto.setLastCreateTime(lastDate);
					//传入更改类型
					ptpWaybillDto.setChangeType(changeType);
					//改单时判断是否变更目的站，并传递改单前网点交接单状态到PTP
					setWaybillToPartnerBranchStat(oldWaybill, oldOrgCode,
							newOrgCode, oldSaleDepartmentEntity, ptpWaybillDto);
					asynSendToPtp(newVersionDto, ptpWaybillDto);
				}else{//更改前到达部门不是合伙人网点
					//更改后到达部门 是合伙人网点
					if(newPartner != null && newSaleDepartmentEntity!=null && StringUtils.equals(newSaleDepartmentEntity.getIsLeagueSaleDept(),FossConstants.YES)){
						//将新记录刷新为有效 2016年3月22日 14:52:03 葛亮亮
						newPartner.setActive(FossConstants.YES);
						newPartner.setCreateTime(now);
						newPartner.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
						
						//3、 更新合伙人新旧运单信息 2016年3月15日 16:44:43 葛亮亮
						partnersWaybillService.updatePartnerWaybillById(oldPartner);
						partnersWaybillService.updatePartnerWaybillById(newPartner);
						
						//推送信息 重新计算提成
						ptpWaybillDto.setIsArriveDepPartner(true);
						ptpWaybillDto.setFeeType(WaybillConstants.ONE);//0:成本  1：提成  2：成本和提成
						
						//需要从折前日志表中获取老运单是否向PTP同步信息，如果同步信息则需要“查询历史数据”
						//获取快递应该分析的时间
						Date lastDate = this.getLastWayBillDate(newWaybill, oldWaybill);
						Map<String, Object> waybillLogMap = new HashMap<String, Object>();
						waybillLogMap.put("wayBillNo", newWaybill.getWaybillNo()); //老运单单号
						waybillLogMap.put("wayBillCreateTime", lastDate); //老运单创建时间
						PushPartnerWaybillLogEntity pushPartnerWaybillLogEntity = pushPartnerWaybillLogService.queryPushPartnerWaybillLogByMap(waybillLogMap);
						if(null != pushPartnerWaybillLogEntity){ //如果老运单有推送日志则需要查询历史数据
							ptpWaybillDto.setIsSearchHistory(FossConstants.YES);
							ptpWaybillDto.setLastCreateTime(lastDate);
						}
						//传入更改类型
						ptpWaybillDto.setChangeType(newPartner==null?null:newPartner.getChangeType());
						asynSendToPtp(newVersionDto, ptpWaybillDto);
					}
				}
			}
			
		}
	}
	
	/**
	 * 改单时判断是否变更目的站，并传递改单前网点交接单状态到PTP
	 * add by xingjun 20161107
	 * @param oldWaybill
	 * @param oldOrgCode
	 * @param newOrgCode
	 * @param oldSaleDepartmentEntity
	 * @param ptpWaybillDto
	 */
	private void setWaybillToPartnerBranchStat(WaybillEntity oldWaybill,
			String oldOrgCode, String newOrgCode,
			SaleDepartmentEntity oldSaleDepartmentEntity,
			PtpWaybillDto ptpWaybillDto) {
		LOG.info("改单时判断是否变更目的站，并传递改单前网点交接单状态到PTP,oldOrgCode:"+oldOrgCode+" newOrgCode:"+newOrgCode);
		//如果更改了目的站 且 更改前的目的站网点是合伙人网点
		if(!StringUtils.equals(oldOrgCode, newOrgCode) &&
				oldSaleDepartmentEntity!=null
				&& StringUtils.equals(oldSaleDepartmentEntity.getIsLeagueSaleDept(),FossConstants.YES)){
			//根据运单以及改单前网点编号获取最后一次交接单信息
			Integer handOverBillStat = handOverBillService.queryHandOverBillByLast(oldWaybill.getWaybillNo(), oldOrgCode);
			LOG.info("根据运单以及改单前网点编号获取最后一次交接单信息,运单号：+"+oldWaybill.getWaybillNo()+",中转返回状态:"+handOverBillStat);
			String toPartnerBranchStat = FossConstants.SUCCESS == handOverBillStat ? "Y" : "N"; 
			ptpWaybillDto.setToPartnerBranchStat(toPartnerBranchStat);
		}
	}
	
	//获取快递运单最后修改时间是补码时间还是改单时间 2016年3月29日 19:48:13 葛亮亮
	private Date getLastWayBillDate(WaybillEntity newWaybill, WaybillEntity oldWaybill){
		Date lastDate;
		if(!FossConstants.YES.equals(newWaybill.getIsExpress())){ //零担只需要考虑改单
			lastDate = oldWaybill.getCreateTime();
		}else{//快递还需要考虑补码
			WaybillExpressEntity waybillExpress =  waybillExpressDao.queryWaybillExpressByNo(newWaybill.getWaybillNo());
			if(waybillExpress == null){
				throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_RFC_EXPRESS_IS_NULL);
			}else{
				if(FossConstants.YES.equals(waybillExpress.getIsAddCode())){
					lastDate = oldWaybill.getCreateTime().after(waybillExpress.getAddCodeTime()) ? oldWaybill.getCreateTime() : waybillExpress.getAddCodeTime();
				}else{
					lastDate = oldWaybill.getCreateTime();
				}
			}
		}
		return lastDate;
	}

	/**
	 * 推送运单信息至ptp
	 * @param newVersionDto
	 * @param ptpWaybillDto 推送至ptp的dto
	 * @author 272311-sangwenhao
	 * @date 2016-2-17
	 */
	private void asynSendToPtp(WaybillDto newVersionDto,
			PtpWaybillDto ptpWaybillDto) {
		if(FossConstants.YES.equals(newVersionDto.getWaybillEntity() == null ? ptpWaybillDto.getIsExpress() : newVersionDto.getWaybillEntity().getIsExpress())){
			LOG.info("更改单:向PTP推送快递信息");
			//快递
			expWaybillInfoToPtpService.asynSendExpWaybillInfoToPtp(ptpWaybillDto);
		}else{//零担
			LOG.info("更改单:向PTP推送零担信息");
			waybillInfoToPtpService.asynSendWaybillInfoToPtp(ptpWaybillDto);
		}
	}
	
	/**
	 * 封装推送给合伙人的Dto
	 * @param waybillDto
	 * @return PtpWaybillDto
	 * @author 272311-sangwenhao
	 * @date 2016-1-26
	 */
	private PtpWaybillDto getPtpWaybillDto(WaybillDto waybillDto,PartnersWaybillEntity partner) {
		PtpWaybillDto ptpWaybillDto = waybillDto.getPtpWaybillDto() ;
		ptpWaybillDto.setChangeType(partner.getChangeType());
		if(WaybillRfcConstants.INVALID.equals(partner.getChangeType()) || WaybillRfcConstants.ABORT.equals(partner.getChangeType())){
			ptpWaybillDto.setFeeType(WaybillConstants.THREE) ;
			//如果是终止或者作废的情况下，没有  合伙人受理更改单提成    标识的值为N  2016年10月31日  by:352676
	  		ptpWaybillDto.setIsParnterAcceptStatus("N");
		}else{
			if(WaybillRfcConstants.RETURN.equals(partner.getChangeType()) || WaybillRfcConstants.TRANSFER.equals(partner.getChangeType())){
				ptpWaybillDto.setIsChangeLabel(true);
			}
			//判断到达部门是否为合伙人
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCodeCache(waybillDto.getWaybillEntity().getLastLoadOrgCode());
			if (saleDepartmentEntity != null) {
				if (StringUtils.equals(saleDepartmentEntity.getIsLeagueSaleDept(),FossConstants.ACTIVE)) {
					ptpWaybillDto.setIsArriveDepPartner(true);
					ptpWaybillDto.setFeeType(WaybillConstants.TWO);//0:成本  1：提成  2：成本和提成
				} else {
					ptpWaybillDto.setIsArriveDepPartner(false);
					ptpWaybillDto.setFeeType(WaybillConstants.ZERO);//0:成本  1：提成  2：成本和提成
				}
			}else{//合伙人开单偏线目的站时需要，非合伙开单即使在这边设置FeeType值在后续代码中还会根据具体情况重新付值所以不会有影响
				ptpWaybillDto.setIsArriveDepPartner(false);
				ptpWaybillDto.setFeeType(WaybillConstants.ZERO);//0:成本  1：提成  2：成本和提成
			}
		}//if end
		
		return ptpWaybillDto ;
	}
	
	
	/**
	 * 根据更改单生成操作记录实体
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param rfcEntity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#addWaybillRfcActionHistory(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity)
	 */
	@Transactional
	public void addWaybillRfcActionHistory(WaybillRfcEntity rfcEntity, Date currentDate) {
  		WaybillRfcActionHistoryEntity actionHistory = this.getActionHistoryFromWaybillRfcEntity(rfcEntity, currentDate);
  		waybillRfcVarificationDao.addWaybillRfcActionHistory(actionHistory);
		
	}

	/**
	 * 审核受理状态查询
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param conditon
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#queryWaybillRfcChkAndPro(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition)
	 */
	public List<WaybillRfcChangeDto> queryWaybillRfcChkAndPro(WaybillRfcCondition condition) {
		condition.setCheckStatusList(checkStatusList);
		condition.setDealStatusList(dealStatusList);
		
		String checkStatus = condition.getCheckStatus();
		String dealStatus = condition.getDealStatus();
		if(StringUtils.isNotEmpty(checkStatus)){
			if(WaybillRfcConstants.PRE_AUDIT.equals(checkStatus)){
				//待审核
				condition.setCheckStatusIsPreAduit(true);
			}else{
				//审核同意、拒绝
				condition.setCheckStatus(checkStatus);
			}
		}
		
		if(StringUtils.isNotEmpty(condition.getDealStatus())){
			if(WaybillRfcConstants.PRE_ACCECPT.equals(dealStatus)){
				//待受理
				condition.setDealStatusIsPreAccecpt(true);
			}else{
				//受理同意、拒绝
				condition.setDealStatus(dealStatus);
			}
		}
		
		Date start = condition.getStartDate();
		Date end = condition.getEndDate();
		List<WaybillRfcChangeDto> list = new ArrayList<WaybillRfcChangeDto>();
		if(StringUtils.isNotEmpty(condition.getWaybillNumber())){
			String waybillNo = condition.getWaybillNumber();
			int len = waybillNo.length();
			if(len != WaybillRfcConstants.WAYBILLNO_LENGTH_8  
					&& len != WaybillRfcConstants.WAYBILLNO_LENGTH_10){
				/**
				 * 请检查运单号,运单号只能是8位或9位！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.CHECK_NUMBER_ONLY_EIGHT_OR_NINE);
			}
			Pattern pattern = Pattern.compile(WaybillRfcConstants.WAYBILLNO_REG_EXPRSSION);
			Matcher match = pattern.matcher(waybillNo);
			if(!match.matches()){
				/**
				 * 请检查运单号,运单号只能由数字组成
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_NUMBER_MUST_INT);
			}
			list= waybillRfcVarificationDao.queryWaybillRfcChkAndPro(condition);
		}else{
			//1、检查申请日期是否为空
			if(start == null || end == null){
				/**
				 * 请检查申请开始日期及申请结束日期都已选择！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.CHECK_APPLY_START_AND_END_DATE);
			}
			//2、检查两日期值的逻辑-申请日期的起始日期≤截止日期
			if(start.compareTo(end) > 0){
				/**
				 * 起始时间不能大于结束日期！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.START_DATE_CAN_NOT_GREATER_THAN_END_DATE);
			}
			//3、检查两日期值相差天数
			Long days = DateUtils.getTimeDiff(start, end);
			if(days > NumberConstants.NUMBER_10){
				/**
				 * 起始时间与截止时间不能超过十天！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.START_DATE_CAN_NOT_GREATER_THAN_END_DAT_TEN_DAY);
			}
			list= waybillRfcVarificationDao.queryWaybillRfcChkAndPro(condition);
		}
		return list;
	}

	/**
	 *  根据运单号查询打印信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-10
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#queryWaybillRfcPrintDto(java.lang.String)
	 */
	public WaybillRfcPrintDto queryWaybillRfcPrintDto(String waybillNo) {
		WaybillRfcPrintDto waybillRfcPrintDto = null;
		if(StringUtils.isNotEmpty(waybillNo)){
			waybillRfcPrintDto = waybillRfcVarificationDao.queryWaybillRfcPrintDto(waybillNo);
		}
		return waybillRfcPrintDto;
	}

	/**
	 * 新增更改单代理
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#addWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Transactional
	public void addWayBillRfcAgent(WaybillRfcAgentEntity entity, CurrentInfo currentInfo) {
		if(entity.getAgentEmpList() == null || entity.getAgentEmpList().isEmpty()){
			/**
			 * 没有选择代理人！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.HAVE_NOT_SELECT_AGENT);
		}
//		if(StringUtils.isEmpty(entity.getId())){
			entity.setId(UUIDUtils.getUUID());	
			entity.setStatus(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE);
//		}
		if(entity.getValidTime() == null || entity.getOverdueTime() == null){
			/**
			 * 请确保生效时间和终止时间都已选择！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.INSURE_START_DATE_AND_END_DATE_HAVE_SELECTED);
		}
		/**
		 * 判断生效时间是否在当前时间之前并且不是修改时的新增
		 */
		if(entity.getValidTime().before(new Date()) && !WaybillConstants.UPDATE_ADD.equals(entity.getFlagAdd())){
			/**
			 * 生效时间不能早于当前时间！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.START_DATE_CAN_NOT_GREATER_THAN_CURRENT_DATE);
		}
		if(entity.getOverdueTime().before(entity.getValidTime())){
			/**
			 * 终止时间不能早于生效时间！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.END_DATE_CAN_NOT_GREATER_THAN_CURRENT_DATE);
		}
		entity.setActive(FossConstants.ACTIVE);
		entity.setEntrustEmpCode(currentInfo.getEmpCode());
		entity.setEntrustEmpName(currentInfo.getEmpName());
		
		List<WaybillRfcAgentPersonEntity> agentPersons = entity.getAgentEmpList();
		List<String> agentPersonCodes = new ArrayList<String>();
		for(WaybillRfcAgentPersonEntity agent : agentPersons){
			agentPersonCodes.add(agent.getAgentEmpCode());
		}
		//判断是否重复授权
		List<String> conflictEmpNames =	waybillRfcVarificationDao.queryWaybillRfcAgentByCondition(entity, agentPersonCodes);
		/**
		 * 判断生效时间是否在当前时间之前并且不是修改时的新增
		 */
//		if(conflictEmpNames.size()>0 && !WaybillConstants.UPDATE_ADD.equals(entity.getFlagAdd())){
		if(CollectionUtils.isNotEmpty(conflictEmpNames)){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.AGENT_REPEAT,new Object[]{conflictEmpNames.toString()});
		}
	
		entity = waybillRfcVarificationDao.addWayBillRfcAgent(entity);
		for(WaybillRfcAgentPersonEntity agent : agentPersons){
			agent.setId(UUIDUtils.getUUID());
			agent.setParentId(entity.getId());
			agent.setActive(FossConstants.ACTIVE);
			waybillRfcVarificationDao.addWayBillRfcAgentPerson(agent);
		}
	}
	
	
	/**
	 * 新增快递更改单代理
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#addWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Transactional
	public void addWayBillRfcAgentExpress(WaybillRfcAgentEntity entity, CurrentInfo currentInfo) {
		if(entity.getAgentEmpList() == null || entity.getAgentEmpList().isEmpty()){
			/**
			 * 没有选择代理人！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.HAVE_NOT_SELECT_AGENT);
		}
//		if(StringUtils.isEmpty(entity.getId())){
			entity.setId(UUIDUtils.getUUID());	
			entity.setStatus(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE);
//		}
		if(entity.getValidTime() == null || entity.getOverdueTime() == null){
			/**
			 * 请确保生效时间和终止时间都已选择！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.INSURE_START_DATE_AND_END_DATE_HAVE_SELECTED);
		}
		/**
		 * 判断生效时间是否在当前时间之前并且不是修改时的新增
		 */
		if(entity.getValidTime().before(new Date()) && !WaybillConstants.UPDATE_ADD.equals(entity.getFlagAdd())){
			/**
			 * 生效时间不能早于当前时间！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.START_DATE_CAN_NOT_GREATER_THAN_CURRENT_DATE);
		}
		if(entity.getOverdueTime().before(entity.getValidTime())){
			/**
			 * 终止时间不能早于生效时间！
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.END_DATE_CAN_NOT_GREATER_THAN_CURRENT_DATE);
		}
		entity.setActive(FossConstants.ACTIVE);
		entity.setEntrustEmpCode(currentInfo.getEmpCode());
		entity.setEntrustEmpName(currentInfo.getEmpName());
		entity.setType(WaybillRfcConstants.AGENTTYPE_EXPRESS);
		List<WaybillRfcAgentPersonEntity> agentPersons = entity.getAgentEmpList();
		List<String> agentPersonCodes = new ArrayList<String>();
		for(WaybillRfcAgentPersonEntity agent : agentPersons){
			agentPersonCodes.add(agent.getAgentEmpCode());
		}
		//判断是否重复授权
		List<String> conflictEmpNames =	waybillRfcVarificationDao.queryWaybillRfcAgentByCondition(entity, agentPersonCodes);
		/**
		 * 判断生效时间是否在当前时间之前并且不是修改时的新增
		 */
//		if(conflictEmpNames.size()>0 && !WaybillConstants.UPDATE_ADD.equals(entity.getFlagAdd())){
		if(CollectionUtils.isNotEmpty(conflictEmpNames)){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.AGENT_REPEAT,new Object[]{conflictEmpNames.toString()});
		}
	
		entity = waybillRfcVarificationDao.addWayBillRfcAgent(entity);
		for(WaybillRfcAgentPersonEntity agent : agentPersons){
			agent.setId(UUIDUtils.getUUID());
			agent.setParentId(entity.getId());
			agent.setActive(FossConstants.ACTIVE);
			waybillRfcVarificationDao.addWayBillRfcAgentPerson(agent);
		}
	}

	/**
	 * 根据查询条件查询审核代理信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param condition
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#queryWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto)
	 */
	public List<WaybillRfcAgentEntity> queryWaybillRfcAgent(
			WaybillRfcQueryAgentConDto condition, CurrentInfo currentInfo) {
		if(condition != null){
			//设置登录用户
			String empCode = currentInfo.getEmpCode();
			condition.setCurrentEmpCode(empCode);
			condition.setActive(FossConstants.ACTIVE);
			Date start = condition.getBeginDate();
			Date end = condition.getEndDate();
		
			if(DateUtils.getTimeDiff(start, end) > NumberConstants.NUMBER_30){
				/**
				 * 查询日期范围不能超过1个月！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.DATE_SCOPE_CAN_NOT_PASS_ONE_MONTH);
			}
			//已生效、未生效单独处理
			String status = condition.getStatus();
			if(WaybillRfcConstants.RFC_AGENT_STATUS_INVALID.equals(status)){
				//数据库中没有未生效状态，判断生效时间晚于当前时间
				condition.setStatus(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE);
				/**
				 * 判断开始时间是否在当前时间之前，如果是，则将当前时间设置为开始时间
				 */
				Date now = new Date();
				if(start.before(now)){
					condition.setBeginDate(now);
				}
			}else if(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE.equals(status)){
				//condition.setBeginDate(null);
				//condition.setEndDate(new Date());
				/**
				 * 判断结束时间是否在当前时间之后，如果是，则将当前时间设置为结束时间
				 */
				Date now = new Date();
				if(end.after(now)){
					condition.setEndDate(now);
				}
			}else{
				if(WaybillRfcConstants.SEARCH_TYPE_ALL.equals(status)){
					condition.setStatus(null);
				}
			}
			return waybillRfcVarificationDao.queryWaybillRfcAgent(condition);
		}else{
			/**
			 * 查询条件不能为空
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.QUERY_CONDITION_CAN_NOT_NULL);
		}
	}

	/**
	 * 修改审核代理权限
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#updateWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Transactional
	public void updateWaybillRfcAgent(WaybillRfcAgentEntity entity, CurrentInfo currentInfo) {
		if (entity!=null){
			/**
			 * 查询修改前审核代理实体
			 */
			WaybillRfcAgentEntity originalEntity = waybillRfcVarificationDao.queryWaybillRfcAgentById(entity.getId());
			List<WaybillRfcAgentPersonEntity> empList = originalEntity.getAgentEmpList();
			
			if(isSaveAuthorizationRecords(originalEntity)){
				/**
				 * 设置原来的为不启用状态
				 */
				for(WaybillRfcAgentPersonEntity agentPerson : empList){
//					//修改状态
//					agentPerson.setActive(FossConstants.INACTIVE);
					//更新
					waybillRfcVarificationDao.updateWaybillRfcAgentPerson(agentPerson);
				}
//				//修改状态
//				originalEntity.setActive(FossConstants.INACTIVE);
				//修改中止标记
				originalEntity.setStatus(WaybillRfcConstants.RFC_AGENT_STATUS_ABORT);
				waybillRfcVarificationDao.updateWaybillRfcAgent(originalEntity);
			}else{
				/**
				 * 删除原来的代理人
				 */
				for(WaybillRfcAgentPersonEntity agentPerson : empList){
					waybillRfcVarificationDao.deleteWaybillRfcAgentPerson(agentPerson);				
				}
				/**
				 * 删除原来的代理实体
				 */
				waybillRfcVarificationDao.deleteWaybillRfcAgent(originalEntity);
			}
			/**
			 * 新增
			 */				
			addWayBillRfcAgent(entity, currentInfo);
		}else{
			/**
			 * 请选择相应的记录执行操作
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.SELECT_RECOED);
		}
		
	}
	
	/**
	 * 是否可执行操作
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param entity
	 * @return true 保留  false 不保留
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#isSaveAuthorizationRecords(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	public boolean isSaveAuthorizationRecords(WaybillRfcAgentEntity entity) {
		Date now = new Date();
		Date validTime = entity.getValidTime();
		Date overdueTime = entity.getOverdueTime();
		if(now.before(validTime)){
			return false;
		}else if(now.after(validTime)&&now.before(overdueTime)){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 根据部门标杆编码查找部门人员
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午3:52:40
	 * @param orgCode
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#queryEmployeeByEntity(java.lang.String)
	 */
	public List<EmployeeEntity> queryEmployeeByEntity(String unifieldCode, CurrentInfo currentInfo) {
		EmployeeEntity entity = new EmployeeEntity();
		entity.setUnifieldCode(unifieldCode);
		List<EmployeeEntity> employeeEntityList= employeeService.queryEmployeeExactByEntity(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
		int index = 0;
		int removedIndex = -1;
		for(EmployeeEntity employeeEntity : employeeEntityList){
			if(employeeEntity.getEmpCode().equals(currentInfo.getEmpCode())){
				removedIndex = index;
			}else{
				//TITIE转成中文
				String titleCode = employeeEntity.getTitle();
				if(StringUtil.isNotEmpty(titleCode)){
					DataDictionaryValueEntity dataDict = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.UUMS_POSITION_TERMSCODE, titleCode);
					if(dataDict != null){
						employeeEntity.setTitle(dataDict.getValueName());
					}
				}
				
			}
			index++;
		}
		
		if(removedIndex >=0){
			employeeEntityList.remove(removedIndex);
		}
		return employeeEntityList;
	}

	/**
	 * 
	 * 删除审核代理数据
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午5:16:24
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#deleteWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Transactional
	public void deleteWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		if (entity!=null){
			WaybillRfcAgentEntity originalEntity = waybillRfcVarificationDao.queryWaybillRfcAgentById(entity.getId());
			if(originalEntity == null){
				/**
				 * 数据有误，不允许删除操作！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.DATA_ERROR_CAN_NOT_OPERATE);
			}
			Date now = new Date();
			Date validTime = entity.getValidTime();
			Date overdueTime = entity.getOverdueTime();
			if(now.after(validTime)&&now.before(overdueTime)){
				/**
				 * 当前状态下不允许删除操作！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.CURRENT_STATUS_CAN_NOT_OPERATE);
			}
			if(now.before(validTime)){
				/**
				 * 1、当前时间在生效时间之前，允许删除、修改，不能中止，不保留历史记录
				 */
				/**
				 * 删除原来的代理人
				 */
				List<WaybillRfcAgentPersonEntity> empList = entity.getAgentEmpList();
				for(WaybillRfcAgentPersonEntity agentPerson : empList){
					waybillRfcVarificationDao.deleteWaybillRfcAgentPerson(agentPerson);				
				}
				/**
				 * 删除原来的代理实体
				 */
				waybillRfcVarificationDao.deleteWaybillRfcAgent(entity);
			}else{
				/**
				 * 审核代理已生效，无法删除
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.AUDIT_AGENT_EFFECT_CAN_NOT_DELETE);
			}
		}else{
			/**
			 * 请选择相应的记录执行操作
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.SELECT_RECOED);
		}
	}
	/**
	 * 
	 * 中止审核代理
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:01:28
	 * @param entity
	 * @see
	 */
	@Transactional
	public void stopWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		if (entity!=null){
			/**
			 * 查询修改前审核代理实体
			 */
			WaybillRfcAgentEntity originalEntity = waybillRfcVarificationDao.queryWaybillRfcAgentById(entity.getId());
			if(originalEntity == null){
				/**
				 * 数据有误，不能进行中止操作！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.DATA_ERROR_CAN_NOT_STOP);
			}
			if(new Date().before(originalEntity.getValidTime())){
				/**
				 * 当前状态下不能进行中止操作！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.CURRENT_STATUS_CAN_NOT_STOP);
			}
//			List<WaybillRfcAgentPersonEntity> empList = entity.getAgentEmpList();
			if(isSaveAuthorizationRecords(entity)){
				/**
				 * 设置为不启用状态
				 */
//				for(WaybillRfcAgentPersonEntity agentPerson : empList){
//					//修改状态
//					agentPerson.setActive(FossConstants.INACTIVE);
//					//更新
//					waybillRfcVarificationDao.updateWaybillRfcAgentPerson(agentPerson);
//				}
//				//修改状态
//				entity.setActive(FossConstants.INACTIVE);
				entity.setStatus(WaybillRfcConstants.RFC_AGENT_STATUS_ABORT);
				waybillRfcVarificationDao.updateWaybillRfcAgent(entity);
			}
		}else{
			/**
			 * 请选择相应的记录执行操作
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.SELECT_RECOED);
		}
		
	}

	/** 
	 * 检查当前用户是否具有更改单审核或受理权限
	 * true 有权限
     * false 没有权限
	 * @author 043260-foss-suyujun
	 * @date 2012-12-28
	 * @param currentInfo
	 * @return boolean
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#isHoldAuthority(com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	public boolean isHoldAuthority(WaybillRfcEntity rfcEntity, CurrentInfo currentInfo) {
			
		//currentInfo.getUser().getRoleids();
		String loginEmpCode = currentInfo.getEmpCode();
		String principalNo = currentInfo.getDept().getPrincipalNo();
		//如果当前登录用户经理为空
		if(StringUtils.isEmpty(principalNo)){
			return true;
		}
		//如果部门负责人不是营业部经理，则自动授予员工审核权限
		if(!employeeService.queryEmpIsSaleDeptOfficer(principalNo)){
			return true;
		}
		//判断当前登录用户是否为经理
		if(loginEmpCode.equals(principalNo)){
			return true;
		}else{
			//不是经理，判断是否有代理权限
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillById(rfcEntity.getOldVersionWaybillId());
	  		
			
			WaybillRfcQueryAgentConDto conditionDto = new WaybillRfcQueryAgentConDto();
			conditionDto.setAgentCode(loginEmpCode);
			conditionDto.setCurrentDate(new Date());
			
			if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
				conditionDto.setType(WaybillRfcConstants.AGENTTYPE_EXPRESS);
			}
			
			conditionDto.setStatus(WaybillRfcConstants.RFC_AGENT_STATUS_AVALIABLE);
			List<WaybillRfcAgentEntity> authList = waybillRfcVarificationDao.
					queryAuthorityListByAgentCode(conditionDto);
			if(authList!=null && !authList.isEmpty()){
				return true;
			}
			
//			//零担需求
//			if(!WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
//				//判断是否有卫星点审核权限
//				return isHoldAuthorityForSatellite(rfcEntity, currentInfo);
//			}
			if (StringUtil.isNotEmpty(rfcEntity.getDraftOrgCode())) {
				SaleDepartmentEntity salesDepartment = saleDepartmentService
						.querySaleDepartmentByCode(rfcEntity.getDraftOrgCode());
				if (salesDepartment != null) {
					// 如果是卫星点
					if (FossConstants.YES.equals(salesDepartment
							.getSatelliteDept())) {
						// 判断是否有卫星点审核权限
						return isHoldAuthorityForSatellite(rfcEntity,
								currentInfo);			}
		}
			}
		}
		return false;
	}
	
	/**
	 * 是否有卫星点审核权限
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-3-24 下午8:32:25
	 */
	private boolean isHoldAuthorityForSatellite(WaybillRfcEntity rfcEntity, CurrentInfo currentInfo){
		boolean flag=false;		
		// 当前登录用户
			String loginEmpCode = currentInfo.getEmpCode();
		// 根据卫星点CODE获取对应的营业部负责人
		String principalNo = waybillRfcDao
				.queryPrincipalNoBySatellite(rfcEntity.getDraftOrgCode());
		// 判断当前用户是否为卫星点负责人
		if ((StringUtils.isEmpty(principalNo))
				|| (loginEmpCode.equals(principalNo))) {
			flag = true;
			}
		return flag;
	}
	
	  /**
	 	 * 更改单受理同意后发送短信，人工受理
		 * @author 265475-foss-zoushengli
		 * @date 2016-09-04
		 * @param waybillRfcEntity
		 * @return void	
		 */
		private void sendSmsToDriver(WaybillRfcEntity waybillRfcEntity, WaybillDto newVersionDto,WaybillDto oldVersionDto,CurrentInfo currentInfo){	
			//查询原运单
			WaybillEntity newwaybill = newVersionDto.getWaybillEntity();
			WaybillEntity oldwaybill = oldVersionDto.getWaybillEntity();
			ArriveSheetEntity arriveSheetEntity =new ArriveSheetEntity();
			arriveSheetEntity.setWaybillNo(oldwaybill.getWaybillNo());
			List<DeliverbillDto> deliverbillDtoList =deliverbillService.queryDriverByWaybillNo(arriveSheetEntity);
			if(CollectionUtils.isEmpty(deliverbillDtoList)){
						return;		
			}
			if(null != newwaybill.getTotalFee() && newwaybill.getTotalFee().equals(oldwaybill.getTotalFee())&&
		        	(null != newwaybill.getToPayAmount()&&newwaybill.getToPayAmount().equals(oldwaybill.getToPayAmount()))){
		        	return;
		      }
			//消息通知实体
			NotificationEntity2 notification = new NotificationEntity2();
			//运单号
			notification.setWaybillNo(waybillRfcEntity.getWaybillNo());
			//模块名称
			notification.setModuleName(NotifyCustomerConstants.PICKUP_FC_DELIVERY);
			 //通知类型
			notification.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
			 //操作人
			notification.setOperator(currentInfo.getEmpName());
			 //操作人编码
			notification.setOperatorNo(currentInfo.getEmpCode());
			 //操作部门
			notification.setOperateOrgName(currentInfo.getCurrentDeptName());
			 //操作部门编码
			notification.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			String mobile="";
			String Consignee= "";
			Map<String,DeliverbillDto> columnMap = new HashMap<String,DeliverbillDto>();
			for (DeliverbillDto deliverbill : deliverbillDtoList) {
				columnMap.put(deliverbill.getDriverCode(), deliverbill);
			}
			for (String key : columnMap.keySet()) {
				DeliverbillDto vehicleNo = columnMap.get(key);
				String deliverbill = vehicleNo.getDriverCode();
				//没有司机工号或者司机工号为000000时为外请车司机，	
				if(!("".equals(StringUtils.trimToEmpty(deliverbill)))&& !("000000".equals(deliverbill))){	
					 DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(deliverbill);
			         //用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
			         if (driverAssociationDto != null){ //司机姓名
			        	Consignee=driverAssociationDto.getDriverName();
			          // 司机电话
			        	mobile=driverAssociationDto.getDriverPhone(); 	
			         }   
		        } else{
		          // 外请司机根据车牌号进行查询
		          List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(key);
		          if (CollectionUtils.isNotEmpty(driverAssociationDtos))
		          {
			        //司机姓名
		        	  Consignee = driverAssociationDtos.get(0).getDriverName();
			        // 司机电话
		        	  mobile=driverAssociationDtos.get(0).getDriverPhone();
		          }
		        }        
	        	//调用接口发送短信
				try {
					//给司机发送短信
					if(StringUtil.isNotEmpty(mobile)){
						SmsParamDto paramDto = new SmsParamDto();
						paramDto.setSmsCode(NotifyCustomerConstants.PICKUP_FC_DELIVERY);
						Map<String,String> placeFieldMap = new HashMap<String, String>();
						placeFieldMap.put("waybillNo", oldwaybill.getWaybillNo());
						placeFieldMap.put("oldTotalFee", oldwaybill.getTotalFee().toString());
						placeFieldMap.put("newTotalFee", newwaybill.getTotalFee().toString());
						paramDto.setMap(placeFieldMap);
						//获得短信内容
						String sendContent = sMSTempleteService.querySmsByParam(paramDto);
						notification.setNoticeContent(sendContent);
						notification.setMobile(mobile);
						notification.setConsignee(Consignee);//收货联系人
						sendSmsVoiceService.sendDriverCodeSms(notification);
					}
				} catch (NotifyCustomerException e) {
					LOG.error(e.getMessage(), e);
					String errMessage = e.getErrorCode();
					if(StringUtil.isEmpty(errMessage)){	
						errMessage = e.getMessage();
					}
					throw new NotifyCustomerException(NotifyCustomerException.SENDMESSAGE_FAIL,new Object[]{messageBundle.getMessage(errMessage)});
				}
		    			}
		}
	/**
	 * 更改单受理同意后发送短信，包括自动受理及人工受理
	 * @author 043260-foss-suyujun
	 * @date 2012-12-29
	 * @param waybillRfcEntity
	 * @return void
	 */
	private void sendSmsToDeliverCustomer(WaybillRfcEntity waybillRfcEntity, WaybillDto newVersionDto, CurrentInfo currentInfo){
		//不需要发短信直接返回
		if(FossConstants.NO.equals(waybillRfcEntity.getDeliverSms())
				&& FossConstants.NO.equals(waybillRfcEntity.getReceiverSms())){
			return;
		}
		//消息通知实体
		NotificationEntity notification = new NotificationEntity();
		//运单号
		notification.setWaybillNo(waybillRfcEntity.getWaybillNo());
		 //通知类型
		notification.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		 //操作人
		notification.setOperator(currentInfo.getEmpName());
		 //操作人编码
		notification.setOperatorNo(currentInfo.getEmpCode());
		 //操作部门
		notification.setOperateOrgName(currentInfo.getCurrentDeptName());
		 //操作部门编码
		notification.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		//操作时间
		notification.setOperateTime(new Date());
		 //模块名称
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILLRFC);
		
		//调用接口发送短信
		try {
			//查询原运单
			WaybillEntity waybill = newVersionDto.getWaybillEntity();
			//给收货人发送短信
			//BUG-21099 发更改通知客户，只勾选通知发货人，但是收货人也收到了短信
			if(FossConstants.YES.equals(waybillRfcEntity.getReceiverSms())){
				String mobile = waybill.getReceiveCustomerMobilephone();
				if(StringUtil.isNotEmpty(mobile)){
					//zxy 20140221 MANA-581 start 新增:快递短信 
					//快递短信
					if(waybillExpressService.onlineDetermineIsExpressByProductCode(newVersionDto.getWaybillEntity().getProductCode(), newVersionDto.getWaybillEntity().getBillTime())){
						
						// 判断收货人手机号是否为发货人提供的固定手机号
						boolean isDeliveryCustomerMobileOK = true;
		                LOG.info("-------判断收件人手机号是否为固定号，默认为否-----"+isDeliveryCustomerMobileOK);
						String deliveryCustomerCode = waybill.getDeliveryCustomerCode();
						 LOG.info("-------发货客户编码-------"+deliveryCustomerCode);
						if (org.apache.commons.lang3.StringUtils
								.isNotEmpty(deliveryCustomerCode)) {

							CustomerEntity deliveryCustomerEntity = customerService
									.queryCustInfoByCode(deliveryCustomerCode);
							 LOG.info("-------根据发货客户编码，查询客户信息-------"+deliveryCustomerEntity);
				 			if (deliveryCustomerEntity != null) {
								String fixedReceiveMobile = deliveryCustomerEntity
										.getFixedReceiveMobile();
								LOG.info("-------根据发货客户，查询固定手机号-------"+fixedReceiveMobile);
							 if (StringUtil
										.isNotBlank(mobile)
										&& !StringUtil
												.isNumeric(mobile)) {
									// 停发短信
									isDeliveryCustomerMobileOK = false;
									LOG.info("++++++非数字+++++++"+isDeliveryCustomerMobileOK);
								} else if (StringUtil
										.isNotBlank(mobile)
										&& StringUtil.isNotBlank(fixedReceiveMobile)
										&& fixedReceiveMobile
												.equals(mobile)) {
									isDeliveryCustomerMobileOK = false;
									LOG.info("++++++固定号+++++++"+isDeliveryCustomerMobileOK);
								}
							}
						}
						
						
										
						if (isDeliveryCustomerMobileOK) {
						//设置短信内容
						notification.setModuleName("PKP_WAYBILLRFC_EXP");
						notification.setNoticeContent(buildExpReceiveSmsContent(waybillRfcEntity, currentInfo));
						notification.setMobile(mobile);
						notification.setConsignee(waybill.getReceiveCustomerContact());//收货联系人
						notifyCustomerService.sendMessage(notification);
						}
					}
					//zxy 20140221 MANA-581 end 新增:快递短信 
					//零担短信
					else{
						//设置短信内容
						notification.setNoticeContent(buildReceiveSmsContent(waybillRfcEntity, currentInfo));
						notification.setMobile(mobile);
						notification.setConsignee(waybill.getReceiveCustomerContact());//收货联系人
						notifyCustomerService.sendMessage(notification);
					}
				}
			}
			//给发货人发送短信
			if(FossConstants.YES.equals(waybillRfcEntity.getDeliverSms())){
				String mobile = waybill.getDeliveryCustomerMobilephone();
				if(StringUtil.isNotEmpty(mobile)){
					//zxy 20140221 MANA-581 start 新增:快递短信 
					//快递短信
					if(waybillExpressService.onlineDetermineIsExpressByProductCode(newVersionDto.getWaybillEntity().getProductCode(), newVersionDto.getWaybillEntity().getBillTime())){
						//设置短信内容
						notification.setModuleName("PKP_WAYBILLRFC_EXP");
						//设置短信内容
						notification.setNoticeContent(buildExpDeliverySmsContent(waybillRfcEntity, currentInfo));
						notification.setMobile(mobile);
						notification.setConsignee(waybill.getDeliveryCustomerContact());//发货联系人
						notifyCustomerService.sendMessage(notification);
					}
					//zxy 20140221 MANA-581 end 新增:快递短信 
					//零担短信
					else{
						//设置短信内容
						notification.setNoticeContent(buildDeliverySmsContent(waybillRfcEntity, currentInfo));
						notification.setMobile(mobile);
						notification.setConsignee(waybill.getDeliveryCustomerContact());//发货联系人
						notifyCustomerService.sendMessage(notification);
					}
				}
			}
		} catch (NotifyCustomerException e) {
			LOG.error(e.getMessage(), e);
			String errMessage = e.getErrorCode();
			if(StringUtil.isEmpty(errMessage)){
				errMessage = e.getMessage();
			}
			throw new NotifyCustomerException(NotifyCustomerException.SENDMESSAGE_FAIL,new Object[]{messageBundle.getMessage(errMessage)});
		}
		
	}
	
	protected String buildDeliverySmsContent(WaybillRfcEntity waybillRfcEntity, CurrentInfo currentInfo){
		WaybillEntity waybill = waybillManagerService.queryWaybillById(waybillRfcEntity.getNewVersionWaybillId());
		//根据部门编码查询部门信息
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillRfcEntity.getDraftOrgCode());
		//2016-06-23 优化更改单短信需求，讲更改类容里面的发货人编码信息去掉，降低公司成本
		if(StringUtils.isNotEmpty(waybillRfcEntity.getChangeItems())){
			String changeItems=waybillRfcEntity.getChangeItems();
			int start=changeItems.indexOf("收货人编码");
			if(start!=-1){
				int end=changeItems.indexOf(";",start)+1;
				String deliveryCustomerCode=changeItems.substring(start, end);
				changeItems = changeItems.replace(deliveryCustomerCode,"");
				waybillRfcEntity.setChangeItems(changeItems);
			}
			
		}
		SmsParamDto paramDto = new SmsParamDto();
		paramDto.setSmsCode(NotifyCustomerConstants.SMS_CODE_WAYBILL_RFC_ACCEPT);
		paramDto.setOrgCode(currentInfo.getCurrentDeptCode());
		Map<String,String> placeFieldMap = new HashMap<String, String>();
		placeFieldMap.put("customerContact",waybill.getDeliveryCustomerContact());
		placeFieldMap.put("waybillNo", waybill.getWaybillNo());
		placeFieldMap.put("changedItems", waybillRfcEntity.getChangeItems());
		placeFieldMap.put("drafterOrgPhone", orgInfo.getDepTelephone()==null?"":orgInfo.getDepTelephone());
		paramDto.setMap(placeFieldMap);
		//获得短信内容
		String sendContent = sMSTempleteService.querySmsByParam(paramDto);
		return sendContent;
	}
	
	protected String buildReceiveSmsContent(WaybillRfcEntity waybillRfcEntity, CurrentInfo currentInfo){
		WaybillEntity waybill = waybillManagerService.queryWaybillById(waybillRfcEntity.getNewVersionWaybillId());
		//根据部门编码查询部门信息
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillRfcEntity.getDraftOrgCode());
		//2016-06-23 优化更改单短信需求，讲更改类容里面的发货人编码信息去掉，降低公司成本
				if(StringUtils.isNotEmpty(waybillRfcEntity.getChangeItems())){
					String changeItems=waybillRfcEntity.getChangeItems();
					int start=changeItems.indexOf("收货人编码");
					if(start!=-1){
						int end=changeItems.indexOf(";",start)+1;
						String deliveryCustomerCode=changeItems.substring(start, end);
						changeItems = changeItems.replace(deliveryCustomerCode,"");
						waybillRfcEntity.setChangeItems(changeItems);
					}
					
				}
		SmsParamDto paramDto = new SmsParamDto();
		paramDto.setSmsCode(NotifyCustomerConstants.SMS_CODE_WAYBILL_RFC_ACCEPT);
		paramDto.setOrgCode(currentInfo.getCurrentDeptCode());
		Map<String,String> placeFieldMap = new HashMap<String, String>();
		placeFieldMap.put("customerContact",waybill.getReceiveCustomerContact());
		placeFieldMap.put("waybillNo", waybill.getWaybillNo());
		placeFieldMap.put("changedItems", waybillRfcEntity.getChangeItems());
		placeFieldMap.put("drafterOrgPhone", orgInfo.getDepTelephone()==null?"":orgInfo.getDepTelephone());
		paramDto.setMap(placeFieldMap);
		//获得短信内容
		String sendContent = sMSTempleteService.querySmsByParam(paramDto);
		return sendContent;
	}
	
	protected String buildExpDeliverySmsContent(WaybillRfcEntity waybillRfcEntity, CurrentInfo currentInfo){
		WaybillEntity waybill = waybillManagerService.queryWaybillById(waybillRfcEntity.getNewVersionWaybillId());
		//根据部门编码查询部门信息
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillRfcEntity.getDraftOrgCode());
		SmsParamDto paramDto = new SmsParamDto();
		paramDto.setSmsCode(NotifyCustomerConstants.EXPRESS_WAYBILL_RFC_SMS);//EXPRESS_WAYBILL_RFC_ACCECPT
		paramDto.setOrgCode(currentInfo.getCurrentDeptCode());
		Map<String,String> placeFieldMap = new HashMap<String, String>();
		placeFieldMap.put("customerContact",waybill.getDeliveryCustomerContact());
		placeFieldMap.put("waybillNo", waybill.getWaybillNo());
		placeFieldMap.put("changedItems", waybillRfcEntity.getChangeItems());
		placeFieldMap.put("drafterOrgPhone", orgInfo.getDepTelephone()==null?"":orgInfo.getDepTelephone());
		paramDto.setMap(placeFieldMap);
		//获得短信内容
		String sendContent = sMSTempleteService.querySmsByParam(paramDto);
		return sendContent;
	}
	
	protected String buildExpReceiveSmsContent(WaybillRfcEntity waybillRfcEntity, CurrentInfo currentInfo){
		WaybillEntity waybill = waybillManagerService.queryWaybillById(waybillRfcEntity.getNewVersionWaybillId());
		//根据部门编码查询部门信息
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillRfcEntity.getDraftOrgCode());
		SmsParamDto paramDto = new SmsParamDto();
		paramDto.setSmsCode(NotifyCustomerConstants.EXPRESS_WAYBILL_RFC_SMS);//EXPRESS_WAYBILL_RFC_ACCECPT
		paramDto.setOrgCode(currentInfo.getCurrentDeptCode());
		Map<String,String> placeFieldMap = new HashMap<String, String>();
		placeFieldMap.put("customerContact",waybill.getReceiveCustomerContact());
		placeFieldMap.put("waybillNo", waybill.getWaybillNo());
		placeFieldMap.put("changedItems", waybillRfcEntity.getChangeItems());
		placeFieldMap.put("drafterOrgPhone", orgInfo.getDepTelephone()==null?"":orgInfo.getDepTelephone());
		paramDto.setMap(placeFieldMap);
		//获得短信内容
		String sendContent = sMSTempleteService.querySmsByParam(paramDto);
		return sendContent;
	}

	/**
	 * 
	 * 是否已经打印过到达联
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-25 上午11:02:56
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#isPrintArrivesheet()
	 */
	@Override
	public boolean isPrintArrivesheet(String waybillNO) {
		ArriveSheetEntity entity=new ArriveSheetEntity();
		entity.setWaybillNo(waybillNO);
		List<ArriveSheetEntity> arriveSheetEntityList = arriveSheetManngerService.queryArriveSheetByWaybillNo(entity);
	    if (CollectionUtils.isNotEmpty(arriveSheetEntityList)) {
    		for (ArriveSheetEntity arriveSheetEntity : arriveSheetEntityList) {
    		    if (arriveSheetEntity.getIsPrinted().equals(FossConstants.YES)) {
    		    	return true;   //运单中有已打印到达联
 
    		    }
    		}
	    }
		return false;
	}
	
	/**
	 * 
	 * 根据id查询更改单
	 * 
	 * @author wangqianjin
	 * @version 2013-04-02
	 * @param id
	 * @return WaybillRfcEntity
	 */
	public WaybillRfcEntity selectByPrimaryKey(String id){
		return waybillRfcDao.selectByPrimaryKey(id);
	}

	
	/**
	 * 根据更改单ID查询变更明细
	 * @author foss-shaohongliang
	 * @date 2013-6-14 下午12:06:25
	 * @param rfcId
	 * @return
	 */
	@Override
	public List<WaybillRfcChangeDetailEntity> queryRfcChangeDetailList(String rfcId){
		return waybillRfcDao.queryRfcChangeDetail(rfcId);
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcCargoVarificationDto(
			WaybillRfcCondition waybillRfcCondition, String cargoPay) {
		String deptCode = FossUserContext.getCurrentDeptCode();
		waybillRfcCondition.setDeptCode(deptCode);
		waybillRfcCondition.setCheckStatusList(checkStatusList);
		waybillRfcCondition.setDealStatusList(dealStatusList);
		
		String checkStatus = waybillRfcCondition.getCheckStatus();
		String dealStatus = waybillRfcCondition.getDealStatus();
		if(StringUtils.isNotEmpty(checkStatus)){
			if(WaybillRfcConstants.PRE_AUDIT.equals(checkStatus)){
				//待审核
				waybillRfcCondition.setCheckStatusIsPreAduit(true);
			}else{
				//审核同意、拒绝
				waybillRfcCondition.setCheckStatus(checkStatus);
			}
		}
		
		if(StringUtils.isNotEmpty(waybillRfcCondition.getDealStatus())){
			if(WaybillRfcConstants.PRE_ACCECPT.equals(dealStatus)){
				//待受理
				waybillRfcCondition.setDealStatusIsPreAccecpt(true);
			}else{
				//受理同意、拒绝
				waybillRfcCondition.setDealStatus(dealStatus);
			}
		}
		Date start = waybillRfcCondition.getStartDate();
		Date end = waybillRfcCondition.getEndDate();
		List<WaybillRfcChangeDto> list = new ArrayList<WaybillRfcChangeDto>();
		//运单号存在，则只按照运单号与部门进行查询
		if(StringUtils.isNotEmpty(waybillRfcCondition.getWaybillNumber())){
			String waybillNo = waybillRfcCondition.getWaybillNumber();
//			int len = waybillNo.length();
			Pattern pattern = Pattern.compile(WaybillRfcConstants.WAYBILLNO_REG_EXPRSSION);
			Matcher match = pattern.matcher(waybillNo);
			if(!match.matches()){
				/**
				 * 请检查运单号,运单号只能由数字组成
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_NUMBER_MUST_INT);
			}
			list= convertSearchByCargoOrExpress(waybillRfcCondition,cargoPay);
		}else{
			//1、检查申请日期是否为空
			if(start == null || end == null){
				/**
				 * 请检查申请开始日期及申请结束日期都已选择！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.CHECK_APPLY_START_AND_END_DATE);
			}
			//2、检查两日期值的逻辑-申请日期的起始日期≤截止日期
			if(start.compareTo(end) > 0){
				/**
				 * 起始时间不能大于结束日期！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.START_DATE_CAN_NOT_GREATER_THAN_END_DATE);
			}
			//3、检查两日期值相差天数
			Long days = DateUtils.getTimeDiff(start, end);
			if(days > NumberConstants.NUMBER_10){
				/**
				 * 起始时间与截止时间不能超过十天！
				 */
				throw new WaybillRfcChangeException(WaybillRfcChangeException.START_DATE_CAN_NOT_GREATER_THAN_END_DAT_TEN_DAY);
			}
			list= convertSearchByCargoOrExpress(waybillRfcCondition,cargoPay);
		}
		return list;
	}

	private List<WaybillRfcChangeDto> convertSearchByCargoOrExpress(WaybillRfcCondition waybillRfcCondition, String cargoPay) {
		if(StringUtils.isEmpty(cargoPay)){
			return null;
		}
		//判定是否快递、零担产品
		List<String> productCodeList = new ArrayList<String>();
		if(WaybillConstants.TYPE_OF_CARGO.equals(cargoPay)){
			productCodeList = waybillExpressService.getAllLevels3CargoProductCode();
		}else{
			productCodeList = waybillExpressService.getAllLevels3ExpressProductCode();
		}
		waybillRfcCondition.setProductCodeList(productCodeList);
		return waybillRfcVarificationDao.queryWaybillRfcCargoVarificationDto(waybillRfcCondition);
	}

	@Override
	public void updateWaybillRfcAgentExpress(WaybillRfcAgentEntity entity,
			CurrentInfo currentInfo) {
		if (entity!=null){
			/**
			 * 查询修改前审核代理实体
			 */
			WaybillRfcAgentEntity originalEntity = waybillRfcVarificationDao.queryWaybillRfcAgentById(entity.getId());
			List<WaybillRfcAgentPersonEntity> empList = originalEntity.getAgentEmpList();
			
			if(isSaveAuthorizationRecords(originalEntity)){
				/**
				 * 设置原来的为不启用状态
				 */
				for(WaybillRfcAgentPersonEntity agentPerson : empList){
//					//修改状态
//					agentPerson.setActive(FossConstants.INACTIVE);
					//更新
					waybillRfcVarificationDao.updateWaybillRfcAgentPerson(agentPerson);
				}
//				//修改状态
//				originalEntity.setActive(FossConstants.INACTIVE);
				//修改中止标记
				originalEntity.setStatus(WaybillRfcConstants.RFC_AGENT_STATUS_ABORT);
				waybillRfcVarificationDao.updateWaybillRfcAgent(originalEntity);
			}else{
				/**
				 * 删除原来的代理人
				 */
				for(WaybillRfcAgentPersonEntity agentPerson : empList){
					waybillRfcVarificationDao.deleteWaybillRfcAgentPerson(agentPerson);				
				}
				/**
				 * 删除原来的代理实体
				 */
				waybillRfcVarificationDao.deleteWaybillRfcAgent(originalEntity);
			}
			/**
			 * 新增
			 */				
			addWayBillRfcAgentExpress(entity, currentInfo);
		}else{
			/**
			 * 请选择相应的记录执行操作
			 */
			throw new WaybillRfcChangeException(WaybillRfcChangeException.SELECT_RECOED);
		}
		
	}
	/**
	 * 为配合快递100轨迹推送所需的方法
	 * @author 220125 yangxiaolong
	 */
	@Override
	public void addOneWaybillTrack(WaybillRfcEntity rfcEntity) {
		WaybillTrackingsDto dto = new WaybillTrackingsDto();
		dto.setWaybillNo(rfcEntity.getWaybillNo());//运单号
		dto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_ABORTED);
		dto.setOperateDeptCode(rfcEntity.getDraftOrgCode());//部门编码              获取的是当前用户所在的部门编码
		dto.setOperateDeptName(rfcEntity.getDraftOrgName()); //部门名称        获取的是当前用户所在的部门名称
		dto.setOperateTime(rfcEntity.getDraftTime());//时间          
		dto.setOperatorName(rfcEntity.getDrafter());//操作人姓名
		dto.setOperatorPhone(employeeService.
				queryEmployeeByEmpCode(rfcEntity.getDrafterCode()).getPhone());//有就给，没有就不给
		waybillTrackingsService.addOneWaybillTrack(dto);
	}

	public IPartnersWaybillService getPartnersWaybillService() {
		return partnersWaybillService;
	}

	public void setPartnersWaybillService(
			IPartnersWaybillService partnersWaybillService) {
		this.partnersWaybillService = partnersWaybillService;
	}

	public IPushPartnerWaybillLogService getPushPartnerWaybillLogService() {
		return pushPartnerWaybillLogService;
	}

	public void setPushPartnerWaybillLogService(
			IPushPartnerWaybillLogService pushPartnerWaybillLogService) {
		this.pushPartnerWaybillLogService = pushPartnerWaybillLogService;
	}

	public IWaybillExpressDao getWaybillExpressDao() {
		return waybillExpressDao;
	}

	public void setWaybillExpressDao(IWaybillExpressDao waybillExpressDao) {
		this.waybillExpressDao = waybillExpressDao;
	}
	
	/**
	 * 待刷卡运单更改处理
	 * 
	 * @author foss-273279-liding
	 * */
	private void processWSCWayBill(WaybillEntity newWaybillEntity,
			WaybillEntity oldWaybillEntity, WaybillRfcEntity waybillRfcEntity,
			boolean isCreateWaybillCardPay, boolean isChangeWaybillCardPay,
			boolean isChangeWaybillNo) {

		//总金额有变动
		boolean changeTotalFee = newWaybillEntity.getTotalFee().compareTo(
				oldWaybillEntity.getTotalFee()) != 0;
		//发货客户有变动
		boolean changeDeliveryCustomer = !StringUtils.equals(
				newWaybillEntity.getDeliveryCustomerCode(),
				oldWaybillEntity.getDeliveryCustomerCode());
		// 开单部门设置为收货部门
		String createOrgCode = newWaybillEntity.getReceiveOrgCode();
		// 收货部门名
		String orgName = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoNameByCode(createOrgCode);
		// 代刷卡金额
		long waitSwipeAmount = 0;
		//报错信息
		String errMsg ="";
		// 开单为银行卡的运单
		if (isCreateWaybillCardPay) {
			// 判断该单据是否在【待刷卡运单管理】界面是未支付的状态
			boolean isPaid;
			WSCWayBillParamEntity queryParams = new WSCWayBillParamEntity();
			// 运单号
			queryParams.setWayBillNo(oldWaybillEntity.getWaybillNo()); //更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
			// 调用结算接口取得最新支付状态
			WSCWayBillReturnEntity result;
			try {
                //根据运单号获取(最新&未支付&有效)待刷卡运单数据
			    //DP-FOSS 343617 zhaoyiqing 20170105 灰度接口
                result = getLastActiveDataByWayBillNoToGrayCubc(queryParams);
                if (result != null) {
					// 取得运单信息
					WSCWayBillEntity wscWayBillEntity = result
							.getWscWayBillInfo();
					if (wscWayBillEntity != null) {
						// 支付状态为Y并且已刷卡金额不为0 =>已支付
						isPaid = WaybillConstants.WAYBILL_PAYSTATUS_PAID
								.equals(wscWayBillEntity.getPaymentStatus())
								&& new BigDecimal(wscWayBillEntity.getAlreadySwipeAmount()).compareTo(new BigDecimal(0)) != 0;

						// 更改类型
						String rfcType = waybillRfcEntity.getRfcType();
						// NCI优化需求,运单中止和作废时调用同一结算接口
						// 作废或者更改付款方式为非银行卡
						if (WaybillRfcConstants.INVALID.equals(rfcType)
								|| WaybillRfcConstants.ABORT.equals(rfcType)
								|| (!isChangeWaybillCardPay)) {
							WSCWayBillParamEntity deleteParams = new WSCWayBillParamEntity();
							// 运单号
							deleteParams.setWayBillNo(oldWaybillEntity.getWaybillNo()); //更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
							// 修改人编号
							deleteParams.setModifyUserCode(waybillRfcEntity
									.getDrafterCode());
							// 是否已支付
							deleteParams.setSwipedBillInvalide(isPaid);
							// 修改人
							if (waybillRfcEntity.getDrafterCode() != null) {
								EmployeeEntity employee = employeeService
										.queryEmployeeByEmpCode(waybillRfcEntity
												.getDrafterCode());
								if (employee != null) {
									deleteParams.setModifyUserName(employee
											.getEmpName());
								}
							}
							// 改成非银行时设置为True
							if (!isChangeWaybillCardPay) {
								deleteParams.setSwipedBillPayWayChange(true);
							}

							try {
								// 运单从【待刷卡运单管理】中消除
                                //DP-FOSS 343617 zhaoyiqing 20170105 灰度接口
                                invalidWSCWayBillByWayBillNoToGrayCubc(deleteParams);
                            } catch (Exception e) {
								// 添加异常日志
								LOG.error("Excepton", e);
								errMsg = "运单更改失败！\n原因：无法删除待刷卡数据";
								throw new WaybillRfcChangeException(errMsg);
							}
							// 虽然方法里应该只有一个出口，为了不继续往下走，这里return
							return;
						}
						//运单总金额、发货人编号发生变更时调用结算接口进行更新
						if (changeTotalFee || (changeDeliveryCustomer)) {
							// POS刷卡明细
							WSCWayBillParamEntity detail = new WSCWayBillParamEntity();
							// 运单号
							detail.setWayBillNo(oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
							// 总金额
							detail.setWayBillAmount(newWaybillEntity.getTotalFee().doubleValue());
							// 发货客户编码
							detail.setSendCustomerCode(newWaybillEntity.getDeliverCustContactId());
							// 发货客户名称
							detail.setSendCustomerName(newWaybillEntity.getDeliveryCustomerContact());
							try {
                                //运单发生更改时，将更改的客户、金额等同步运单字段信息更新到待刷卡和T+0明细表
                                //DP-FOSS 343617 zhaoyiqing 20170105 灰度接口
                                syncBillInfo2WscAndT0ToGrayCubc(detail);
                            } catch (Exception e) {
								// 添加异常日志
								LOG.error("Excepton", e);
								errMsg = "运单更改失败！\n原因：总金额/发货客户变动时调用结算接口更新失败";
								throw new WaybillRfcChangeException(errMsg);
							}
						}
						// 调用结算接口查询已刷卡运单总金额
						BigDecimal totalSwipedAmount = new BigDecimal(0);
						try{
							String waybillNo=oldWaybillEntity.getWaybillNo();
							RequestDO requestDO = new RequestDO();
							requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
							requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
							requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
							requestDO.setSourceBillNos(waybillNo);
							VestResponse response=null;
							try {
							    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
							} catch (Exception e) {
								throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
							}
							if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
								List<VestBatchResult> batchResults = response.getVestBatchResult();
								VestBatchResult batchResult = batchResults.get(0);
								if(batchResult.getVestSystemCode().equals("FOSS")){
									//刷卡运单总金额
									totalSwipedAmount = wscWayBillManageService.getTotalSwipedAmountByWayBillNo(
											oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
								}
							}
						} catch(Exception e){
							// 添加异常日志
							LOG.error("Excepton", e);
							errMsg ="运单更改失败！\n原因：调用结算接口查询已刷卡运单总金额失败";
							throw new WaybillRfcChangeException(errMsg);
						}
						//更改后预付金额增加
						boolean isIncreaseFee = newWaybillEntity.getPrePayAmount()
								.compareTo(totalSwipedAmount) > 0;
						//更改后预付金额减少
						boolean isDecreaseFee = newWaybillEntity.getPrePayAmount()
								.compareTo(totalSwipedAmount) < 0;
						//差额
						BigDecimal amount = newWaybillEntity.getPrePayAmount()
								.subtract(totalSwipedAmount)
								.abs();
						waitSwipeAmount = amount.longValue();
						// 已支付
						if (isPaid) {
							// 更改后金额增加时调用结算代刷卡接口
							if (isIncreaseFee) {
								try {
									WSCWayBillParamEntity addParams = new WSCWayBillParamEntity();
									// 运单号
									addParams.setWayBillNo(oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
									// 数据来源(1-运单开单 2-运单更改)
									addParams
											.setWayBillSource(WaybillConstants.WAYBILL_SOURCE_MOD);
									// 发货人编号
									addParams.setSendCustomerCode(newWaybillEntity.getDeliverCustContactId());
									// 发货人名称
									addParams.setSendCustomerName(newWaybillEntity.getDeliveryCustomerContact());
									// 开单部门编号
									addParams.setCreateBillOrgCode(createOrgCode);
									// 开单部门名称
									addParams.setCreateBillOrgName(orgName);
									// 开单时间
									addParams.setCreateBillTime(newWaybillEntity.getBillTime());
									// 创建人编号
									addParams.setCreateUserCode(newWaybillEntity.getCreateUserCode());
									// 创建人名称
									addParams.setCreateUserName(newWaybillEntity.getCreateUserName());
									// 待刷卡金额
									addParams
											.setWaitSwipeAmount(waitSwipeAmount);
									// 总金额
									addParams
									.setWayBillAmount(newWaybillEntity.getTotalFee().doubleValue());
									// 支付状态:未支付
									addParams
											.setPaymentStatus(WaybillConstants.WAYBILL_PAYSTATUS_UNPAID);
									// 调用结算接口更新代刷卡数据
									try {
										RequestDO requestDO = new RequestDO();
										requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
										requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
										requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
										requestDO.setSourceBillNos(oldWaybillEntity.getWaybillNo());
										VestResponse response=null;
										try {
										    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
										} catch (Exception e) {
											throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
										}
										if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
											List<VestBatchResult> batchResults = response.getVestBatchResult();
											VestBatchResult batchResult = batchResults.get(0);
											if(batchResult.getVestSystemCode().equals("FOSS")){
												wscWayBillManageService.addWSCWayBill(addParams);
											}
										}
									} catch (Exception e) {
										// 添加异常日志
										LOG.error("Excepton", e);
										errMsg = "运单更改失败！\n原因：无法添加待刷卡数据";
										throw new WaybillRfcChangeException(errMsg);
									}

								} catch (Exception e) {
									// 添加异常日志
									LOG.error("Excepton", e);
									errMsg ="运单更改失败！\n原因：无法添加待刷卡数据(已支付)";
									throw new WaybillRfcChangeException(errMsg);
								}
							}
							// 运单更改后，付款方式未变，金额减少
							else if (isDecreaseFee) {
								// 调用结算代刷卡接口更新T+0
								try {
									WSCWayBillParamEntity decreaseParams = new WSCWayBillParamEntity();
									// 运单号
									decreaseParams
											.setWayBillNo(oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
									// 待刷卡金额
									decreaseParams
											.setWaitSwipeAmount(waitSwipeAmount);
									// 总金额
									decreaseParams
									.setWayBillAmount(newWaybillEntity.getTotalFee().doubleValue());
									// 运单减少金额
									decreaseParams
											.setBillReduceAmount(waitSwipeAmount);
									//修改人
									decreaseParams
									.setModifyUserCode(waybillRfcEntity.getDrafterCode());
									// 修改人名称
									if (waybillRfcEntity.getDrafterCode() != null) {
										EmployeeEntity employee = employeeService
												.queryEmployeeByEmpCode(waybillRfcEntity.getDrafterCode());
										if (employee != null) {
											decreaseParams.setModifyUserName(employee
													.getEmpName());
										}
									}
									RequestDO requestDO = new RequestDO();
									requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
									requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
									requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
									requestDO.setSourceBillNos(oldWaybillEntity.getWaybillNo());
									VestResponse response=null;
									try {
									    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
									} catch (Exception e) {
										throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
									}
									if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
										List<VestBatchResult> batchResults = response.getVestBatchResult();
										VestBatchResult batchResult = batchResults.get(0);
										if(batchResult.getVestSystemCode().equals("FOSS")){
											// 调用结算接口更新T+0
											wscWayBillManageService.swipedBillAmountReduce(decreaseParams);
										}
									}
								} catch (Exception e) {
									// 添加异常日志
									LOG.error("Excepton", e);
									errMsg = "运单更改失败！\n原因：金额减少后无法更新T+0数据";
									throw new WaybillRfcChangeException(errMsg);
								}
							}
						} else {
							// 未支付
							// 预付增加
							if (isIncreaseFee) {
								try {
									WSCWayBillParamEntity addParams = new WSCWayBillParamEntity();
									// 运单号
									addParams.setWayBillNo(oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
									// 数据来源(1-运单开单 2-运单更改)
									addParams
											.setWayBillSource(WaybillConstants.WAYBILL_SOURCE_MOD);
									// 修改人编号
									addParams
											.setModifyUserCode(waybillRfcEntity
													.getDrafterCode());
									// 修改人
									if (waybillRfcEntity.getDrafterCode() != null) {
										EmployeeEntity employee = employeeService
												.queryEmployeeByEmpCode(waybillRfcEntity
														.getDrafterCode());
										if (employee != null) {
											addParams
													.setModifyUserName(employee
															.getEmpName());
										}
									}
									addParams.setWscItemId(wscWayBillEntity
											.getWscItemId());
									// 待刷卡金额
									addParams
											.setWaitSwipeAmount(waitSwipeAmount);
									// 总金额
									addParams.setWayBillAmount(newWaybillEntity
											.getTotalFee().doubleValue());
									// 支付状态:未支付
									addParams
											.setPaymentStatus(WaybillConstants.WAYBILL_PAYSTATUS_UNPAID);
									// 收货部门
									addParams
											.setCreateBillOrgCode(createOrgCode);
									// 收货部门名
									addParams
									.setCreateBillOrgName(orgName);
									
									RequestDO requestDO = new RequestDO();
									requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
									requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
									requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
									requestDO.setSourceBillNos(oldWaybillEntity.getWaybillNo());
									VestResponse response=null;
									try {
									    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
									} catch (Exception e) {
										throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
									}
									if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
										List<VestBatchResult> batchResults = response.getVestBatchResult();
										VestBatchResult batchResult = batchResults.get(0);
										if(batchResult.getVestSystemCode().equals("FOSS")){
											// 调用结算接口更新代刷卡数据
											wscWayBillManageService
													.invalidAndInsertWSCWayBill(addParams);
										}
									}
								} catch (Exception e) {
									// 添加异常日志
									LOG.error("Excepton", e);
									errMsg = "运单更改失败！\n原因：无法更新待刷卡数据(未支付)";
									throw new WaybillRfcChangeException(errMsg);
								}
							}
							//预付金额减少
							else if (isDecreaseFee){
								// 调用结算代刷卡接口更新T+0
								try {
									WSCWayBillParamEntity decreaseParams = new WSCWayBillParamEntity();
									// 运单号
									decreaseParams
											.setWayBillNo(oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
									// 待刷卡金额
									decreaseParams
											.setWaitSwipeAmount(waitSwipeAmount);
									// 总金额
									decreaseParams
									.setWayBillAmount(newWaybillEntity.getTotalFee().doubleValue());
									// 运单减少金额
									decreaseParams
											.setBillReduceAmount(waitSwipeAmount);
									//修改人
									decreaseParams
									.setModifyUserCode(waybillRfcEntity.getDrafterCode());
									// 修改人名称
									if (waybillRfcEntity.getDrafterCode() != null) {
										EmployeeEntity employee = employeeService
												.queryEmployeeByEmpCode(waybillRfcEntity.getDrafterCode());
										if (employee != null) {
											decreaseParams.setModifyUserName(employee
													.getEmpName());
										}
									}
									
									RequestDO requestDO = new RequestDO();
									requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
									requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
									requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
									requestDO.setSourceBillNos(oldWaybillEntity.getWaybillNo());
									VestResponse response=null;
									try {
									    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
									} catch (Exception e) {
										throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
									}
									if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
										List<VestBatchResult> batchResults = response.getVestBatchResult();
										VestBatchResult batchResult = batchResults.get(0);
										if(batchResult.getVestSystemCode().equals("FOSS")){
											// 调用结算接口更新T+0
											wscWayBillManageService
													.swipedBillAmountReduce(decreaseParams);
										}
									}
								} catch (Exception e) {
									// 添加异常日志
									LOG.error("Excepton", e);
									errMsg = "运单更改失败！\n原因：金额减少后无法更新T+0数据";
									throw new WaybillRfcChangeException(errMsg);
								}
							}
							//预付不变
							else {
								// 调用结算代刷卡接口删除运单下未支付的待刷卡数据
								try {
								    	//灰度接口 323098
									//wscWayBillManageService.invalidUnPayMentWSCDataByWayBillNo(oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
								    	//灰度接口 323098
								    	invalidUnPayMentWSCDataByWayBillNoToGrayCubc(oldWaybillEntity.getWaybillNo());
								}catch (Exception e) {
									// 添加异常日志
									LOG.error("Excepton", e);
									errMsg = "运单更改失败！\n原因：未支付运单预付金额不变时调用结算接口失败";
									throw new WaybillRfcChangeException(errMsg);
								}
							}
						}
						/*更改了运单号（和结算沟通后，在原有的更改单逻辑都走完后再判断是否更改了运单号。
						因为开单方式为银行卡会有两处判断不会向下继续执行，
						所以没有将这个更改运单号的接口直接写在下面 ELSE 后面（这样做会都执行））
						2016年6月12日 11:24:53 葛亮亮*/		
						if(isChangeWaybillNo){
							changeWaybillNO(newWaybillEntity, oldWaybillEntity, createOrgCode, orgName);
						}
					}
				}
			} catch (Exception e) {
				// 添加异常日志
				LOG.error("Excepton", e);
				if(StringUtils.isEmpty(errMsg)){
					errMsg = "运单更改失败！\n原因：无法查询待刷卡数据的支付状态";
				}
				throw new WaybillRfcChangeException(errMsg);
			}
		} else {
			// 开单方式为非银行卡改成银行卡
			// 待刷卡金额设置 (更改后的现付金额)
			waitSwipeAmount = newWaybillEntity.getPrePayAmount().longValue();
			WSCWayBillParamEntity wayBillParamEntity = new WSCWayBillParamEntity();
			// 运单号
			wayBillParamEntity.setWayBillNo(oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
			// 数据来源(1-运单开单 2-运单更改)
			wayBillParamEntity
					.setWayBillSource(WaybillConstants.WAYBILL_SOURCE_MOD);
			// 发货人编号
			wayBillParamEntity.setSendCustomerCode(newWaybillEntity
					.getDeliverCustContactId());
			// 发货人名称
			wayBillParamEntity.setSendCustomerName(newWaybillEntity
					.getDeliveryCustomerContact());
			// 开单部门编号
			wayBillParamEntity.setCreateBillOrgCode(createOrgCode);
			// 开单部门名称
			wayBillParamEntity.setCreateBillOrgName(orgName);
			// 开单时间
			wayBillParamEntity
					.setCreateBillTime(newWaybillEntity.getBillTime());
			// 待刷卡金额 (运单金额=预付金额)
			wayBillParamEntity.setWaitSwipeAmount(newWaybillEntity
					.getPrePayAmount() != null ? newWaybillEntity.getPrePayAmount()
					.longValue() : 0);
			//总金额
			wayBillParamEntity
			.setWayBillAmount(newWaybillEntity.getTotalFee().doubleValue());
			// 支付状态:未支付
			wayBillParamEntity
					.setPaymentStatus(WaybillConstants.WAYBILL_PAYSTATUS_UNPAID);
			// 创建人编号
			wayBillParamEntity.setCreateUserCode(newWaybillEntity
					.getCreateUserCode());
			// 创建人名称
			wayBillParamEntity.setCreateUserName(newWaybillEntity
					.getCreateUserName());

			try {
				RequestDO requestDO = new RequestDO();
				requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
				requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
				requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
				requestDO.setSourceBillNos(oldWaybillEntity.getWaybillNo());
				VestResponse response=null;
				try {
				    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
				} catch (Exception e) {
					throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
				}
				if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
					List<VestBatchResult> batchResults = response.getVestBatchResult();
					VestBatchResult batchResult = batchResults.get(0);
					if(batchResult.getVestSystemCode().equals("FOSS")){
						wscWayBillManageService.addWSCWayBill(wayBillParamEntity);
					}
				}
			} catch (Exception e) {
				// 添加异常日志
				LOG.error("Excepton", e);
				errMsg = "运单更改失败！\n原因：无法添加待刷卡数据";
				throw new WaybillRfcChangeException(errMsg);
			}
			
			//变更运单号 2016年6月12日 11:43:02 葛亮亮
			if(isChangeWaybillNo){
				changeWaybillNO(newWaybillEntity, oldWaybillEntity, createOrgCode, orgName);
			}
		}
	}
	
	//更改时更改运单编号
	public void changeWaybillNO(WaybillEntity newWaybillEntity, WaybillEntity oldWaybillEntity,
			String createOrgCode, String orgName){
		WSCEntity wscEntity = new WSCEntity();
		wscEntity.setWayBillNo(newWaybillEntity.getWaybillNo()); //现在运单号
		wscEntity.setOldWayBillNo(oldWaybillEntity.getWaybillNo()); //原运单号
		wscEntity.setSendCustomerCode(newWaybillEntity.getDeliverCustContactId()); //发货联系人编号
		wscEntity.setSendCustomerName(newWaybillEntity.getDeliveryCustomerContact()); //发货联系人名称
		wscEntity.setCreateBillOrgCode(createOrgCode); //开单部门编号
		wscEntity.setCreateBillOrgName(orgName); //开单部门名称
		wscEntity.setWayBillAmount(newWaybillEntity.getTotalFee().doubleValue()); //运单总金额
		wscEntity.setWayBillSource(WaybillConstants.WAYBILL_SOURCE_MOD); //数据来源 {1-运单开单 2-运单更改}
		
		try {
		    //DP-FOSS 343617 灰度接口
            //更改时，更改运单号
            changeWayBillNoToGrayCubc(wscEntity);
        } catch (Exception e) {
			//报错信息
			String errMsg ="";
			// 添加异常日志
			LOG.error("Excepton", e);
			errMsg = "运单更改失败！\n原因：变更运单单号处理失败";
			throw new WaybillRfcChangeException(errMsg);
		}

	}

    //根据运单号获取(最新&未支付&有效)待刷卡运单数据
    //DP-FOSS 343617 20170105
    private WSCWayBillReturnEntity getLastActiveDataByWayBillNoToGrayCubc(WSCWayBillParamEntity queryParams) throws Exception {
        String waybillNo = queryParams.getWayBillNo();
        RequestDO requestDO = new RequestDO();
        //由于本方法是抽取方法，所以调用方法名应该是上级方法名称
        requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
        requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
        requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
        requestDO.setSourceBillNos(waybillNo);
        WSCWayBillReturnEntity result=null;
        VestResponse response;
        try{
            response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
        } catch (Exception e){
            /*result=wscWayBillManageService.getLastActiveDataByWayBillNo(queryParams);
            return result;*/
            throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
        }
        if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
            List<VestBatchResult> batchResults = response.getVestBatchResult();
            VestBatchResult batchResult = batchResults.get(0);
            if(StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS,batchResult.getVestSystemCode())){
                result=wscWayBillManageService.getLastActiveDataByWayBillNo(queryParams);
            }
        }
        return result;
    }

    //修改待刷卡运单为无效
    //DP-FOSS 343617 20170105
    private void invalidWSCWayBillByWayBillNoToGrayCubc(WSCWayBillParamEntity deleteParams) throws Exception {
        String waybillNo = deleteParams.getWayBillNo();
        RequestDO requestDO = new RequestDO();
        //由于本方法是抽取方法，所以调用方法名应该是上级方法名称
        requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
        requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
        requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
        requestDO.setSourceBillNos(waybillNo);
        VestResponse response;
        try{
            response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
        }catch (Exception e){
            /*wscWayBillManageService.invalidWSCWayBillByWayBillNo(deleteParams);
            return;*/
            throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
        }
        if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
            List<VestBatchResult> batchResults = response.getVestBatchResult();
            VestBatchResult batchResult = batchResults.get(0);
            if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS, batchResult.getVestSystemCode())) {
                wscWayBillManageService.invalidWSCWayBillByWayBillNo(deleteParams);
            }
        }
    }

    //运单发生更改时，将更改的客户、金额等同步运单字段信息更新到待刷卡和T+0明细表
    //DP-FOSS 343617 20170105
    private void syncBillInfo2WscAndT0ToGrayCubc(WSCWayBillParamEntity detail) throws Exception {
        String waybillNo = detail.getWayBillNo();
        RequestDO requestDO = new RequestDO();
        //由于本方法是抽取方法，所以调用方法名应该是上级方法名称
        requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
        requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
        requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
        requestDO.setSourceBillNos(waybillNo);
        VestResponse response;
        try{
            response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
        }catch (Exception e){
           /* wscWayBillManageService.syncBillInfo2WscAndT0(detail);
            return;*/
            throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
        }
        if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
            List<VestBatchResult> batchResults = response.getVestBatchResult();
            VestBatchResult batchResult = batchResults.get(0);
            if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS, batchResult.getVestSystemCode())) {
                wscWayBillManageService.syncBillInfo2WscAndT0(detail);
            }
        }
    }

    //更改时，更改运单号
    //DP-FOSS 343617 20170105
    private void changeWayBillNoToGrayCubc(WSCEntity wscEntity) {
        String waybillNo = wscEntity.getOldWayBillNo();
        RequestDO requestDO = new RequestDO();
        //由于本方法是抽取方法，所以调用方法名应该是上级方法名称
        requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
        requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
        requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
        requestDO.setSourceBillNos(waybillNo);
        VestResponse response;
        try{
            response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
        }catch (Exception e){
            /*wscManageService.changeWayBillNo(wscEntity);
            return;*/
            throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
        }
        if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
            List<VestBatchResult> batchResults = response.getVestBatchResult();
            VestBatchResult batchResult = batchResults.get(0);
            if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS, batchResult.getVestSystemCode())) {
                wscManageService.changeWayBillNo(wscEntity);
            }
        }
    }


    /**
	 * wscWayBillManageService.invalidUnPayMentWSCDataByWayBillNo(
		oldWaybillEntity.getWaybillNo());//更改运单号后新运单号还没有信息，所以用原来运单号(没有更改运单号，原来的和新的都是一样)
	 * @throws Exception 
	 */
	public void invalidUnPayMentWSCDataByWayBillNoToGrayCubc(String waybillNo) throws Exception{
		RequestDO requestDO = new RequestDO();
		requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".processWSCWayBill");
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		requestDO.setSourceBillNos(waybillNo);
		VestResponse response=null;
		try {
		    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
		} catch (Exception e) {
		   /* wscWayBillManageService.invalidUnPayMentWSCDataByWayBillNo(waybillNo);
		    return;*/
		    throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
		}
		if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
			List<VestBatchResult> batchResults = response.getVestBatchResult();
			VestBatchResult batchResult = batchResults.get(0);
			if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResult.getVestSystemCode())){
				wscWayBillManageService.invalidUnPayMentWSCDataByWayBillNo(waybillNo);
			}
		}else{
		    wscWayBillManageService.invalidUnPayMentWSCDataByWayBillNo(waybillNo);
		}
	}
	private void returnedGoodsWriteoffReceivableToGrayCubc(WaybillDto waybillDto, String originalWaybillNo,CurrentInfo currentInfo){
	    	RequestDO requestDO = new RequestDO();		
		requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".doAfterVarification");
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		requestDO.setSourceBillNos(originalWaybillNo);
		VestResponse response=null;
		try {
		    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
		} catch (Exception e) {
		    /*waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(waybillDto,originalWaybillNo),currentInfo);
		    return;*/
		    throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
		}
		if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
			List<VestBatchResult> batchResults = response.getVestBatchResult();
			VestBatchResult batchResult = batchResults.get(0);
			if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResult.getVestSystemCode()))
				waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(waybillDto,originalWaybillNo),currentInfo);
			
			
		}/*else{
		    waybillPickupService.returnedGoodsWriteoffReceivable(gainOrignalWaybillPickUpInfo(waybillDto,originalWaybillNo),currentInfo);
		}*/
	}
	private VestBatchResult getVestResult(WaybillEntity waybillDto,String methodName) {
		RequestDO requestDO = new RequestDO();
		requestDO.setServiceCode(WaybillManagerService.class.getName()+"."+methodName);
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		requestDO.setSourceBillNos(waybillDto.getWaybillNo());
		VestResponse response = new VestResponse();
		try {
			response = grayScaleService.vestAscription(requestDO);
		} catch (Exception e1) {
			//异常默认走foss
		    throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
		}
		//如果灰度接口异常，那么为null
		List<VestBatchResult> vestBatchResults = response.getVestBatchResult();
		return CollectionUtils.isEmpty(vestBatchResults)?new VestBatchResult():vestBatchResults.get(0);
	}
}