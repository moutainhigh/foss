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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/PdaWaybillService.java
 * 
 * FILE NAME        	: PdaWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationNewService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFocusRecordManagementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.ICombinateBillDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsPdaService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceEntryService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEamDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IInsurGoodsDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOnlineAddressDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPrintInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IReModifyRouteDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillChargeDtlDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirePendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightBIService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPrintInfoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaScanService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.waybill.server.service.IWaybillStockService;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightBIEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.AppreciationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ComplaintDetail;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPrintEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PDAWoodenRequireEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReModifyRouteEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.RewardFineDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.TimeLookDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequireEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CenterBillOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponReverseResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpReturnGoodsApplyResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodPDADto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPictureWaybillResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SubCenterBillOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ValueAddServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLabeledGoodStockDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.PdaInterfaceException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.RewardFineDetailVo;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.primeton.bfs.engine.json.JSONException;
import com.primeton.bfs.engine.json.JSONObject;


/**
 * PDA服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-12-19 上午10:55:54,content:
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-12-19 上午10:55:54
 * @since
 * @version 1. 若货物为违禁品，则系统自动提示“货物为违禁品，不可开单！”； 2. 若货物为贵重物品，则系统自动勾选“贵重物品”，且不可修改；
 *          3. 若货物为限保物品，则系统自动限定保价金额，且不可修改，并提示“货物为限保物品”； 4.
 *          违禁品、拒收品、贵重物品、限保物品（含保价金额上限）具体类型可在系统中进行配置； 1. 货物重量单位为千克； 2.
 *          运单开单时，货物的件数、重量及体积文本框是必填项，默认值为空； 3. 件数只能是大于等于1的整数，重量及体积只能大于0。 4.
 *          体积、重量这种过程数据保留两位小数 1. 尺寸录入文本框，支持'长*宽*高*件数+长*宽*高*（即多个尺寸相加）的计算； 2.
 *          如果件数是1，则不强制在录入时要“*1”； 3.
 *          货物尺寸为计算器输入，输入的尺寸可以进行加减，例如：1*1*1*5+2*2*2*3-0.5*0.5*0.5， 显示为输入文本； 4.
 *          尺寸计算单位为厘米，尺寸计算出数据后转换单位为立方米后，在货物体积中显示数据；
 *          例如：尺寸录入为：50*50*20（其中20为件数），则体积显示数据为：0.05； 5.
 *          体积为空时，录入尺寸后，填充体积。当尺寸修改时，体积随之变处。当修改体积时，尺寸不变化。 6.
 *          体积初始值为“0”，仍保持必填，操作员根据实际情况改，选择木架或木箱或两者都选择后则该值不能为0 1. 货物体积单位为立方米； 2.
 *          营业员可以修改通过尺寸计算器计算得出的体积数据； 3.
 *          系统设置货物重量体积比区间值（该值由基础资料配置），在运单提交时，系统自动对重量体积比进行校验： 即重量体积比X=重量/体积； 3.1
 *          当X不在设置的区间中，弹出提示“请确认录入的重量体积是否准确！”；
 *          （该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面；点击取消，点返回运单录入界面； 3.2
 *          当X在区间中，无提示；直接进入确认运单信息界面； 4. 录入重量体积后，系统校验单票的重量体积及单件（平均单件）的重量体积是否
 *          满足“修改-查询行政组织业务属性”基础资料中的单票和单件重量体积限制；只要该四项中有
 *          一项不满足，则提示“XX超出提货网点限制，请重新选择提货网点！”； 1.
 *          货物包装总件数小于等于货物总件数，如果大于总件数，提示：“包装件数不能大于总件数”； 1.
 *          当木包装件数大于等于1时，系统校验出发城市和对应目的站的走货路由中是否有有打木架功
 *          能的部门，若有，则提醒营业员“是否收入代打木架？”，是，则进入场代打木架信息录入界面”，
 *          并显示第一个有打木架功能的部门名称；若走货路由中无有打木架功能的部门，则不提示“是否收入代打木架？”； 2.
 *          当走货路由中有有打木架功能的部门为多个时，只显示系统路由中第一有有打木架功能的部门， 且不可修改； 1.
 *          运输类型为汽运时，货物类型为唯一选择项；即，非A即B；默认不可勾选，只有当走货路由经过
 *          特定的城市时需要录入货物类型,特定城市可在系统中进行配置； 2.
 *          运输类型为空运时，货物类型为下拉选择框，默认显示为普货，目前只有这一个分类，该类型可做配置； 1. 贵重物品判断规则： a.
 *          单票单件货物，体积≤0.05立方且保价声明价值≥1万元； b. 单票多件货物，平均单件体积≤0.2立方且平均单件保价声明价值≥1万元；
 *          （注：平均单件体积=开单总体积÷开单件数，平均单件保价=开单保价声明价值÷开单件数） c. 高保价货物，单票货物保价≥10万元；
 *          满足以上任意一个条件时，系统将判定该票货物为贵重物品并自动勾选"贵重物品"复选框，且为灰色，
 *          不可编辑；不满足以上条件时，该复选框为可编辑状态，用户可根据实际情况自行选择是否勾选； 2.
 *          若"贵重物品"复选框被勾选，则在【储运注意事项】中自动加入提示记录"贵重物品"字段，
 *          若该货物为贵重物品时，"储运注意事项"中信息显示优先级为：贵重物品＞其他； 1.
 *          录入的打木架货物件数和打木箱件数之和必须大于等于录入的木包装件数； 2.
 *          系统默认标签流水号前X的货物为需要代打木架货物，X等于录入的“打木架货物件数”和“打木箱货物件数”之和； 3.
 *          营业员在打印标签时，按流水号先贴要打木架或打木箱的货物； 1.
 *          录入的打木架货物体积和打木箱货物体积之和乘以1.4必须小于等于货物总体积；该1.4为打木架体积计算系统， 可配置； 2.
 *          营业部开单时按打完木架后的包装开，即包装中含“木架/木箱”，开单件数为货物打木架/木箱前的实际件数
 *          （防止丢货），尺寸和重量按照以下公式计算： 1.1
 *          整票货物代打时：开单体积=代打货物体积*1.4；开单重量=所有货物重量+代打货物体积*42； 1.2
 *          部分货物代打时，开单体积=代打货物体积*1.4+未打木架货物体积；开单重量=所有货物重量+代打货物体积*42。
 *          即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，重量另加，单票中未打木架的货物的体积和重量不变； 1.3
 *          例如：一票货物需全部代打，货物体积为1个方，重量为100KG，则开单体积为1.4个方，
 *          开单重量为100+1*42=142KG，收取客户包装费为150*1.4=210元； 1.4
 *          需要加托时，仍按照50元/个另外收取费用，托的重量和体积不再另加；营业部不需要再更改由于打 木架引起重量和体积的变化； 3.
 *          打木架要在“对内备注”中备注“代打木架/木箱”，特殊要求（特别是合打情况）必须在对内备注
 *          和A4纸上都注明，例如：货物1、2、3合打成一件等； 4.
 *          开单件数为代打木架前货物实际件数，包装为打木架后的包装，打木架后件数发生变化后，需及时更改件数； 录入运输信息SUC业务 规则 1.
 *          收货部门默认为操作者所在的部门，不可修改； 2.
 *          若操作者部门为集中开单部门时，则收货部门可修改，其选择部门只能为开单组服务的集中接货区域营业部；
 *          且其进进入运单开单界面时，系统自动默认收货部门为上一次开单的收货部门； 1. 默认显示精准； 1.
 *          根据部门的性质或所在城市确定部门所作业务的性质，如有些部门可做所有运输性质的业务，
 *          部分部门无法做精准业务，所有规则根据营业部业务管制确定（根据出发城市、到达城市确定部门可做的业务性质，
 *          所有数据读取自营业部业务管制基础资料）； 1. 当运输性质为空运时，提货方式有：自提（不含机场提货费）、免费自提、机场自提、免费送货、
 *          送货进仓、送货上楼、送货（不含上楼)；默认为自提（不含机场提货费）； 2.
 *          如果客户是CRM中的合同客户，在开单时提货方式选项中增加"免费送货"选项，免费送货送货费为0（不可修改）； 3.
 *          当运输性质为精准、普货、偏线时，提货方式有：自提、免费送货、送货进仓、送货上楼、 送货（不含上楼)、内部带货自提；默认为自提； 1.
 *          当运输性质为空运时，开单提货网点显示空运代理网点及公司可做空运的自有网点； 2. 当运输性质为偏线时，提货网点只显示偏线代理网点； 3.
 *          当运输性质为精准、普货时，提货网点显示我司所有可所到达的网点； 1.
 *          当运输性质为空运，且提货方式为机场自提时，增值服务的其它费用中可添加对就有规则的费用
 *          （暂定方案：对于单独开单的可做增值服务费用添加。若以后在产品定义中区分了单独开单和合大票，则另考虑）； 1.
 *          提货方式为机场自提时，代收货款设置为0且不可编辑，不能开单含到付款，，付款方式不能开到付；
 *          否则，提示：“提货方式为【机场自提】时，到付金额必须为0，付款方式不能为到付”； 2.
 *          提货方式为内部带货自提时，只能填写发货人收货人信息和货物信息，所有涉及金额的控件均为0， 收货人和发货人只能为OA系统中部门； 1.
 *          提货方式为自提（不含机场提货费）、免费自提、内部带货自提时，只显示可做自提业务的网点； 2.
 *          提货方式为送货时，只显示可做送货业务的网点； 1. 目的站可由收货客户地址的城市（即收货客户地址中的“市”）生成，也可手工录入； 2.
 *          系统自动过滤只显示目的站城市符合条件的网点信息； 3. 选择确定提货网点信息后，系统自动生成对应网点的目的站名称于目的站框内；
 *          网点目的站读取对应的网点目的站基础资料； 4. 在网点目的站基础资料中有‘取消到达日期’，如果当前日期在‘取消到达日期’之前，
 *          那么提示“xx营业部将于xx年xx月xx日临时取消到达，届时货物将转至xx营业部，请做好客户解释工作！”
 *          （其中第一个xx营业部，为当前营业部、第二个xx营业部为网点目的站基础资料中的‘转货部门‘， xx年xx月xx日为‘取消到达日期’）
 *          5. 录入重量体积后，系统校验单票的重量体积及单件（平均单件）的重量体积是否满足
 *          “修改-查询行政组织业务属性”基础资料中的单票和单件重量体积限制；只要该四项中有一项不满足，
 *          则提示“XX超出提货网点限制，请重新选择提货网点！”； 1.
 *          当通过运输性质、提货方式和目的站过滤的提货网点唯一时，直接显示提货网点名称； 1.
 *          当勾选上门接货时，自动显示文本框，录入接货司机工号，接货费数字框可录入，手写现付金额，可编辑 2.
 *          当不勾选上门接货时，接货费清0变灰且不可录入，手写现付金额变灰，不可编辑 1.
 *          当录单部门为集中开单部门时，自动勾选上门接货，且不可修改； 2. 上门接货默认不勾选，可修改；3. 1.
 *          对外备注可多选，选择的项目信息依次显示在储运注意事项中；默认为空； 2. 当选择空时，则其它所有选项自动不勾选； 3.
 *          对外备注已选择录入后，若再选择空，则清空已选择的所有对内备注； 1.
 *          储运注意事项=对外备注&对内备注&大车直送（若勾选大车直送），各字段以“；”分开； 2. 对外备注永远在储运注意事项的最前面； 1.
 *          当运输性质为精准、普货、偏线时，显示配载线路；系统自动根据营业部所在城市和到达目的站 匹配走货线路基础资料，生成预配线路，且不可修改；
 *          2. 当运输性质为空运时，显示配载航班，包括：早班、中班、晚班、中转航班；默认为空； 1.
 *          系统自动匹配始发配载部门基础资料，通过配载类型来判断配载部门； 1.
 *          如果提货网点为自有网点时，最终配载部门为提货网点名称；如果提货网点不是自有网点，
 *          则最终配载部门为外发代理网点的管理部门；（参考基础资料：外发代理、部门基础信息基础资料）； 2.
 *          当运输类型为空运时，最终配载部门可编辑，且列举对应收货部门可走空运货的空运总调，
 *          默认显示为空；若对应收货部门可走空运货的总运总调唯一时，则直接显示； 1. 预计出发时间在运单提交时进行判断填充； 2.
 *          预计出发时间适用于运输类型为“精准”； 3. 预计出发时间=预计出发日期,准点出发时间。【格式如：2011-6-28
 *          ,12:00:00】 （部门对应的“准点出发时间”，数据取自基础资料：经营-运作基础资料）； 4.
 *          如果开单当前时间在准点出发时间前，则预计出发日期=开单日期；否则，预计出发日期=开单日期+1； 5.
 *          当运输性质为普货、偏线时，则预计出发日期=开单日期+1； 1. 预计提货/派送时间适用于运输类型为“精准”； 2.
 *          提货方式为“自提”时，若部门对应的“是否当天出发”为“是”，
 *          则预计提货/派送时间=预计出发日期+到达营业部承诺天数：到达营业部承诺时点；
 *          否则，预计提货/派送时间=预计出发日期+到达营业部承诺天数-1：到达营业部承诺时点；
 *          （部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准； “到达营业部承诺天数”和“到达营业部承诺时点”
 *          ，数据取自基础资料：专线-城市承诺时间标准；） 3. 提货方式包含为“送货 ”时，若部门对应的“是否当天出发”为“是”，
 *          则预计提货/派送时间=预计出发日期+到达营业部承诺天数+派送承诺需加天数：
 *          派送承诺时点；否则，预计提货/派送时间=预计出发日期+派送承诺需加天数-1：
 *          派送承诺时点；（部门对应的“是否当天出发”，数据取自基础资料：经营-运作发车标准；
 *          “到达营业部承诺天数”、“到达营业部承诺时点”、“派送承诺时点”、“派送承诺需加天数”， 数据取自基础资料：专线-城市承诺时间标准；）
 *          1. 大车直送默认不勾选，且不可修改； 2. 当单票货物重量超过8吨或体积大于25方时，系统提醒“是否需要大车直送？”；
 *          营业员确定后，自动勾选大车直送，且大车直送变为可修改状态； 3. 勾选大车直送时，在储运注意事项中增加大车直送显示；不勾选时，不显示；
 *          1、检验单号是否唯一（有效状态的运单唯一，中止/逻辑删除等的运单不参与检查），
 *          如果唯一系统不做操作，如果不唯一，系统提示“XX单号已经使用，请重新输入单号”
 *          2、新增一条业务规则：在提交运单之前，若相邻两次输入的单号（两次单号分别为A与B且单号输入合法）
 *          差别较大时，系统给予用户友好提示避免录错单号，但不限制单号的输入。具体规则为：
 *          1）若前后两次输入的单号位数相同，当|A-B|≥100时系统给予提示； 2）若前后两次输入的单号位数不同，系统给予提示；
 *          3）提示信息为：前后两票单号相差过大，请检查所输单号是否是本部门所属单号！ 增值服务SUC规则如下 1.1 相关业务用例
 *          BUC_FOSS_5.20.30_510 确认承运信息 1.2 用例描述 营业员通过本用例录入增值服务信息。 1.3 用例条件 条件类型
 *          描述 引用系统用例 前置条件 后置条件 1.4 操作用户角色 操作用户 描述 营业员 可查询、录入、修改增值服务信息 1.5 界面要求
 *          1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.2.1 录入增值服务信息 1.5.2.2 查询发货客户代收货款退款联系人
 *          1.5.2.3 查询其它费用 1.5.3 界面描述 营业员点击运单开单，进入运单开单界面。 本界面为录入增值服务信息。
 *          界面主要分为二个部分：录入增值服务、查询其它费用。 1. 录入增值服务
 *          录入增值服务分为三个部分：录入增值服务信息界面、录入其它费用列表、查询发货客户代收货款退款联系人； 1.1 录入增值服务信息界面
 *          录入增值服务信息界面包括： 1.1.1 保价声明价值； 1.1.2 保价费率：保价费率可由基础资料配置，可按出发城市区域，出发营业部等；
 *          1.1.3 保价费； 1.1.4 代收货款； 1.1.5 代收费率； 1.1.6 代收手续费； 1.1.7
 *          退款类型：包括三日退、退日退、审核退，默认三日退为空； 1.1.8 收款人姓名； 1.1.9 收款人帐号； 1.1.10 包装费；
 *          1.1.11 装卸费； 1.1.12 送货费； 1.1.13 其它费用合计； 1.1.14 接货费； 1.1.15
 *          返单类别：包含“无需返单”、“客户签收单原件返回”、“客户签收单传真返回”、
 *          “运单到达联传真返回”包括无需返单、原件签收单返回、传真件签收单返回、扫描件返回，默认无需返单； 1.1.16 预付费保密； 1.2
 *          录入其它费用列表 录入其它费用列表包括： 1.2.1 费用名称； 1.2.2 费用金额； 1.2.3 新增其它费用：功能按钮；
 *          1.2.4 删除其它费用：功能按钮； 1.3 查询发货客户代收货款退款联系人 1.3.1 操作列； 1.3.2 开户银行； 1.3.3
 *          收款人姓名； 1.3.4 银行帐号； 1.3.5 备注信息； 1.4 录入包装费 2. 查询其它费用
 *          查询其它费用分为四个部分：其它费用列表 、功能按钮； 2.1 其它费用列表： 其它费用列表包括： 2.1.1 名称； 2.1.2
 *          归集类别； 2.1.3 描述； 2.1.4 金额； 2.1.5 金额上限； 2.1.6 金额下限； 2.1.7 是否可修改； 2.1.8
 *          其它费用可基础资料配置，并支持是否启用和是否可见的维护； 2.2 功能按钮： 2.2.1 查询：包括查询条件：名称； 2.2.2 确定；
 *          2.2.3 取消； 1. 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,
 *          且保价费率可手动调整；（限保物品从限运物品基础资料中获取）；非限保物品的保价费率不可修改； 2.
 *          实际保险费小于最低保费的按最低保费收取； 3. 保价费 = 保价申明价值*保价费率，不可修改； 4.
 *          保价申明价值默认为3000，可以修改；保价声明价值不封顶； 5. 精准（长途）、普货（长途）、偏线，最低一票8元；普货（短途）、
 *          精准（短途）最低一票6元；空运最低一票10元；所有运输方式保价超过最低均按0.4%收取
 *          （数据读取自保价设置基础资料）；长短途数据读取计价基础资料； 6. 实际保价费小于最低保费的按最低保费收取； 7.
 *          保价费率首先是配置的标准费率。当有区域保价费率时，以区域保价费率为准。
 *          当客户为合同客户时，则以合同签订为准。所有的保价费率以读取的为准，不可修改。 限保物品的保价费率同样不可修改
 *          行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，
 *          开单是否可以进行返回签单、货到付款、代收货款需要根据到达部门属性 是否可以（返回签单、货到付款、代收货款）来决定 1.
 *          如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，代收货款设置为0且不可编辑； 2.
 *          如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
 *          代收货款可编辑，且可以选择所有的退款类型，若CRM中若无账户信息，则退款类型只能选择审核退 3. 开单时系统默认代收货款为空； 4.
 *          代收货款栏默认为空，如果没有代收货款，则要求输入0；否则，进行提示：“请确认该单没有代收货款，
 *          如无，请输入数字0”；当代收货款大于0时，输入后，对于选择的退款类型，有如下限制： 3.1
 *          三日退：在收到客户代收货款后第三天给客户打款。 3.1.1 默认退款类型为三日退； 3.1.2 代收10000元以下费率0.5%，
 *          10000元以上费率0.4%；最低10元/票，最高100元/票；
 *          有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。 3.2
 *          审核退：收到客户代收货款，出发部门审核后，给客户打款。 3.2.1
 *          代收10000元以下费率0.5%，10000元以上费率0.4%；最低10元/票，最高100元/票（
 *          通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。 3.2.2
 *          选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。 3.3
 *          即日退：在收到客户代收货款后24小时到账。 3.3.1
 *          代收手续费率1%，最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市
 *          与部门代收货款费率”实现）。 3.3.2 必须先录入客户收款银行信息，提交时，银行信息不能为空； 3.3.3
 *          只支持4个银行：农行、工行、建行、招行；否则，给予提示信息； 5.
 *          限制代收货款金额不能小于10元，可以等于10元；但可以为0；该数字“10”可由基础资料配置； 6.
 *          网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，有数据时不可对代收货款进行修改，
 *          只可起草出发更改进行修改；若网上代收货款为0 ，系统可支持修改代收货款金额； 7. 默认的代收费率由基础资料配置； 1.
 *          保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额； 1.
 *          代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，且只能选择，不能修改；
 *          当退款人姓名和帐号唯一时，直接显示；（数据读取CRM客户信息资料（退款帐户信息）） 2.
 *          CRM客户信息资料的要先在CRM中录入客户退款帐户信息，且第一次在我司办理代收货款业务时， 退款类型只能为审核退； 3.
 *          同一客户多个银行信息的显示问题：当有两个或以上账号时，弹出账号信息（包括开户银行、收款人、
 *          账号、备注），选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置； 1. 包装费默认为0，可手工修改； 2.
 *          当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
 *          且可修改，修改的金额只能大于等于默认显示金额；其中150、30、300、40为打木架单价（元/方）、打木架最低一票、
 *          打木箱单价（元/方）、打木箱最低一票，可由基础资料配置； 1. 装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 *          即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
 *          （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 *          开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言） 2.
 *          如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，为系统默认的公布价。
 *          开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，开单显示运费（C）=Q*Z=C0+M ； 3.
 *          当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），
 *          需先清空装卸费为零，然后再修改数据。如：月结客户与非月结客户、整车与非整车、目的站、重量、体积； 4.
 *          当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 *          如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。否则，清空装卸费为零，
 *          最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。（其中Q1是装卸费M1时的费率,Z为重量/体积）； 5.
 *          系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。（只限制配载类型为专线的，包括月结）； 6.
 *          对于显示费率不等于显示费率乘以重量的问题，要求如下： 6.1.
 *          若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。 且显示运费等于该显示费率*重量；
 *          6.2. 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，令费率（B）=费率（A）
 *          的取前2个小数位的值（注：直接截取A的值，不四舍五入）。则显示费率（Q）=费率（B）+0.01。 显示运费等于该显示费率*重量； 7.
 *          只要含与不含装卸费两者有交叉的，均以不含为准； 8. 装卸费特殊部门表： （建议：做为可配置的基础数据表） 9.
 *          2012年12月1日开业的部门不能开装卸费 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费 11.
 *          是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。 12.
 *          装卸费上限由增值服务配置基础资料实现（在产品API中体现）。 1.
 *          送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，
 *          不能下调。当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值， 但用户可以上调送货费； 2.
 *          通过送货费基础资料来实现： 2.1. 若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止； 2.2.
 *          若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
 *          （即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值， 取值终止。） 2.3.
 *          特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 2.3.1
 *          先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，再判断开单重量在已被体积筛选
 *          出来的记录中的哪个重量区间，来确定是哪一条记录。然后再根据费率计算，计算出来的值与该条的最低
 *          送货费进行比较，若小于最低送货费时，就取最低送货费，取值终止；若大于最低送货费且小于最高送货费时，
 *          就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。 2.3.2 标淮派送范围收取送货费标准： 货物重量 标准
 *          0-300KG 55元/票 301-500KG 0.2元/KG 501KG或2.5立方米以上 100元/票，不封顶 2.3.3
 *          当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
 *          开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于 【最高送货费】” 2.3.4
 *          仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。 2.3.5
 *          “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也 可以向下修改，最小为0） 2.3.6
 *          除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改 2.3.7 最高送货费做基础资料配置； 3.
 *          非标准派送范围加收操作费标准： 3.1 超远加收送货费标准： 距离（公里） 30-50 50-100 100-150 加收金额（元）
 *          50 100 150 3.1.1 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）； 3.1.2
 *          客户所在地30公里范围内如果有公司的营业网点，无论是否做派送，该区域均不能收取超远加收送货费； 3.1.3 非标准派送的费用添加无上限
 *          3.2 特殊区域（进仓）： 3.2.1 特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）； 3.2.2
 *          收费标准：进仓费实报实销，并加收150元操作费； 4. 区域送货费限制： 4.1
 *          当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，但前提是：提货方式必须为送货)时，
 *          送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置） 4.2
 *          当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦
 *          营业部、深圳华强钟表市场营业部）。（该类营业部类型、送货费固定标准、成本提取标准可配置） 4.3
 *          内部带货、空运、偏线及中转下线不受上述需求的限制。 5. 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域： 5.1
 *          当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，
 *          或体积是否超过X立方，是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。
 *          （单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】 5.2
 *          当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，其他费用中的“送货上楼费”屏蔽或显示但不可选择； 5.3
 *          若“XX区域”或其它限制类型区域再开派送部，适用以上需求； 5.4 空运、偏线及中转下线不受上述需求的限制； 5.5
 *          内部带货受上述需求的限制； 5.6 “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置； 1.
 *          开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
 *          费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置） 2.
 *          运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 *          运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置） 3.
 *          综合服务费：（费用金额由基础资料配置） 3.1 综合服务费默认为2元不可修改、剔除； 3.2 月结客户可以删除2元的综合服务费； 3.3
 *          淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费； 4. 燃油附加费：（费用金额由基础资料配置）
 *          运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 *          运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改； 5.
 *          其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改； 6.
 *          其他费用合计等于其他费用列表中各项费用数据之和； 1.
 *          原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单； 2.
 *          空运、偏线和中转下线的“返单类型”不可选择； 3.
 *          若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用； 4.
 *          如果选择有返单类型，系统会自动生成一条签收单记录，记录信息包含：运单号、运单ID、库存状态、
 *          当前操作部门（运单开单时，是填开部门）、是否签收、是否作废、出发部门(运单开单出发部门)、 签收单类型、签收状态； 5.
 *          月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
 *          非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除 1.
 *          运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密； 2.
 *          已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。未开启预付运费保密运单提交后，
 *          若货物未有非本部门入库操作，则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
 *          则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单； 3.
 *          运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择； 4.
 *          已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，涉及预付运费有变动时， 不影响预付费保密功能； 5.
 *          开启预付运费保密，预付运费不能为0，否则不能保存、提交； 6. 开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行；
 *          7. 开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时， 必须取消预付运费保密后才能提交； 8.
 *          预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示； 9.
 *          预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示； 1. ； 2.
 *          1）开单总费用、预付金额、到付金额，取整，四舍五入；
 *          2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100, 100.10显示100.1；
 *          3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。
 *          4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。 1. 接货费只能录入数字
 *          1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 2）限保物品的保价费率通过基础资料进行配置；
 *          3）取消偏线、空运最高保价5000元的限制； 2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置
 *          3、"其它费用"中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。
 *          4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制 * 运单收银SUC业务规则 1.
 *          计费类型分为重量计费、体积计费，由系统自动生成，不可修改；默认重量计费； 2.
 *          对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费； 若按重量计费运费较按体积计费运费较高，则计费类型为重量计费；
 *          若按体积计费运费较按重量计费运费较高，则计费类型为体积计费； 3. 运输类型为汽运时,计费重量为空，不可修改；
 *          4.运输类型为空运时计费重量应为重量和体积*1000000/6000进行对比，取大； 1.
 *          费率为对应计费类型、目的站、提货网点及运输类型的走货单价； 2. 目的站、提货网点及运输类型确认后，即可自动显示对应计费类型的费率；
 *          （来自价格基础资料） 3. 费率可以保留到小数点后2位；运费、预付金额、到付金额为整数， 按照四舍五入的原则； 1.
 *          公布价运费、增值服务费用、优惠合计需通过点击计算费用获取； 2. 当提货网点信息未录入，不可计费运费；当录了提货网点，
 *          货物的重量和体积以及其他服务费用录入不完整时， 点击计算费用，得到当前填写的信息费用信息； 3.
 *          公布价运费（即重量、体积计费的运费）=每公斤单价/每方价格与货物实际重量/体积的乘积，
 *          对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费；（来自价格基础资料） 4.
 *          增值服务费=送货费+包装费+保价费+代收手续费+接货费+其他费用合计； 5. 优惠合计=优惠总合计； 6.
 *          总运费=公布价运费+增值服务费-优惠合计=预付金额+到付金额； 7.
 *          在提交运单时，系统记录该次计算费用时所用的所有价格费用的规则版本号； 8. 采用费用明细取整、总费用取整的原则 9.
 *          任何客户包含月结客户不可减免综合服务费。 10. 开单界面选择付款方式为"现金"时，默认预付金额为开单总金额并允许修改，且修改的同时到付
 *          金额随预付金额变化而变化，变化规则为：到付金额=总金额-预付金额，到付金额不能修改。
 *          例如，开单界面选择付款方式为"现金"时，当一票货的总金额为300元时，默认预付金额为300元，到付
 *          金额为0，将预付金额改为100元之后，到付金额变为200元 1.
 *          必须输入提货网点、货物的重量和体积以及其他服务费用，计算出总费用后再输入验证编码进行验证；
 *          （优惠券编码开单时，对运单有上下限金额的限制，该上下限可配置） 2.
 *          输入优惠券编码后，焦点转移时，系统调用CRM接口，校验该编码是否存在，如果存在，则将优惠信息
 *          显示在优惠信息列表中，费用为对应的优惠券的金额；如果不存在提示为：你输入的优惠券编码不存在，请重新输入； 3.
 *          当修改优惠编码时，则焦点转移时，系统自动调用CRM接口，校验该编码，同时修改对应的优惠减免 记录和优惠信息； 4.
 *          优惠金额必须小于等于开单总金额； 5. 当付款金额为单一的现付或到付时，优惠券可以单独减免，当付款金额既有现付又有到付时，优先选
 *          择减免现付金额，如存在优惠金额此时减免现付有余时，可再充抵到付费用，但优惠金额必须小于等于开单总金额； 6.
 *          暂存时，只显示减免金额，运单实收或应收金额上不显示，即暂存时，不生成实收或应收单据； 7. 当费用变化时，优惠编码必须重新输入；8.
 *          1. 当发货客户有月结客户属性时，才可选择月结；并默认显示付款方式为月结； 2.
 *          当发货客户有信用额度的属性时，才可选择临时欠款的付款方式； 3. 支持发货客户的多种付款方式叠加，但月结和临时欠款不可同时出现； 4.
 *          当开单为订单导入开单，且为网上订单，并选择了网上支付时，导入开单后的付款方式显示网上支付；
 *          同时付款方式可修改，付款方式中增加网上支付选项； 5. 开单为空运、偏线、中转下线时不能选择“网上支付”； 1.
 *          当付款方式为现付时，预付金额必须大于0；否则，提示信息“付款方式非到付，预付金额不能小于等于0”； 2.
 *          当付款方式为到付时，预付金额不能大于0；否则，提示信息“付款方式为【到付】，预付金额不能大于0”； 3.
 *          月结客户的信用金额直接限制该客户的当月发货金额，当月发货金额超过信用额度，当月将无法再开单月结，
 *          提示“该客户的剩余可用信息额度不足，不能开月结”； 4.
 *          临时欠款时，需要客户中的信用额度超过运费，否则，提示“该客户的剩余可用信息额度不足， 不能开临时欠款”； 确认会员资质SUC规则 1.
 *          签约合同客户： 1.1 营业部合同客户只享受合同优惠；当有促销的优惠时，合同客户可享受最大优惠折扣，
 *          但不叠加；（促销优惠为基础资料配置属性） 备注：当运单为网络订单（官网订单、阿里巴巴订单、淘宝订单、呼叫中心订单），
 *          且客户为合同客户时，为了保证不重复享受折扣， 除运单是阿里巴巴订单且月结客户优惠类型为“普通货物打折方案”以阿里巴巴费率计算外，
 *          其他情况都按照合同优惠来进行计算； 1.2 客户为非本部门的合同客户时，不在本部门享受合同优惠；；
 *          合同客户有绑定其他营业部时，可在所绑定的其他营业部享受合同归属部门同等优惠； 1.3 所有合同客户可以减免综合服务费； 1.4
 *          所有合同客户享受合同签订的保价费率和代收货款费率； 1.5 合同客户通过月结审核的，可选择付款方式为：“月结”；
 *          其他客户不能选择为“月结”； 1.6 合同客户为“价格折扣”优惠时，客户发货除最低每票的运费不受影响外，
 *          其他运费超过最低每票标准的，总运费按照其折扣比例打折优惠（折后总运费不低于最低每票标准运费）； 1.7
 *          合同客户为“月发月送”优惠时，客户发货价格按照月发月送标准； 1.7.1. 开单不能含装卸费； 1.7.2.
 *          当月发越送与空运同时存在时，以空运规则为准，可开装卸费； 1.8 合同客户有免费送货属性的，在优惠信息列表中自动优惠标准派送的送货费用，
 *          对于月结属性的客户可以向下修改送货费，最小为0；其它所有情况的送货费不可向下修改，只能向上修改； 1.9
 *          合同用户仅在合同有效期内享受合同优惠。 1. 非公司签约客户： 1.1
 *          发货客户只能选择本部门的客户，但通过会员卡号可查询其他部门客户信息； 若客户为其它部门合同客户时，客户不享受合同优惠； 1.2
 *          客户优惠信息由基础资料配置，优惠信息包括：我司享受优惠区域部门、 优惠类型（如线路优惠、货物类型优惠等，可配置）、
 *          优惠名称，对应的的优惠的具体信息； 1.3 优惠类型有优先级，其优先级由基础资料配置；
 *          （当普通优惠与促销优惠同时同在时，促销优惠优于普通优惠；） 1.4 系统自动根据承运信息，给出对应运单可享的优先级最高的优惠，
 *          、当最高优先级并列出现时，默认勾选最大优惠的优惠方案； 对于同一优惠类型的优惠，存在互拆；
 *          例：当线路优惠和区域优惠同时存在时，系统只给出线路优惠； （点面原则（异常优先）：即点面同时存在时，以点为准。
 *          如：优惠1：上海出发货8折优惠；优惠2：上海到广州9优惠， 由于优惠2包含于优惠1中，为优惠1的一个异常，
 *          则在生成优惠信息时，只显示优惠2的优惠方案）； 1.5 阿里巴巴客户： 1.5.1 阿里巴巴订单导入开单时，对于诚信通会员，
 *          系统按如下方式进行折扣： 运输类型 限制要求 普货 精准（长/短途） 起步价格 30元 40元/20元 小于1000公斤或5立方
 *          大于等于1000公斤或5立方 （该折扣表内容由基础资料配置） 备注：对于阿里的普通会员，系统只减免2元的综合服务费。 1.5.2
 *          阿里巴巴订单导入开单时，在“优惠方案中”自动新增“阿里巴巴优惠费”项， 且系统自动计算优惠金额。公布价仍显示为公司散客开单标准的公布价。
 *          （阿里巴巴优惠费=公司标准公布价总运费—阿里巴巴折扣的总运费）； 也为公布价优惠的一种； 1.5.3
 *          阿里诚信通会员客户下单后，阿里订单导入开单， 且“该客户同时为部门的月结客户，其月结优惠类型为‘普通货物打折方案’时”，
 *          则开单时阿里价格优于月结客户价格，以“阿里诚信通会员价格方案”计算运费；
 *          其他的月结优惠类型(公布价、价格折扣、月发月送)开单时月结客户价格优于阿里价格规则； 1.5.4
 *          当阿里巴巴订单开空运和偏线不享受对应的阿里巴巴优惠， 但可享受正常开单的优惠； 1. 客户享受的优惠类型自动显示在优惠信息显示列表中；
 *          2. 合同优惠包括公布价折扣优惠和增值服务优惠； 3. 对于公布价优惠：只显示客户可以享受的公布价优惠信息，
 *          且默认勾选“合同规定的优惠方案”或“与合同优惠有冲突时， 系统规则使用的优惠方案”的公布价折扣优惠； 4.
 *          当客户为合同客户时，不仅显示合同公布价优惠， 还显示对应线路或货物类型或货物种类等其它配置的所有与本次承运相关的公布价优
 *          惠方案中系统规则使用的较合同优惠更优的优惠方案； 5. 当客户为合同客户时，若勾选非合同公布价优惠时且为非促销优惠时，
 *          则不再享受对应客户的所有合同优惠（包括公布价优惠、增值服务优惠及月发月送、月结等优惠）； 6.
 *          当客户为非合同客户时，自动默认勾选系统规则可使用的折扣最低的公布价优惠方案； 7. 公布价优惠方案只可勾选一种，不可多选； 8.
 *          合同客户的增值服务优惠项不可修改（即不可以取消或增加勾选）， 非合同客户的增值服务优惠项可修改（即可以取消或增加勾选） 9.
 *          任何客户包含月结客户不可减免综合服务费 1. 公布价优惠金额=本次承运的公布价总价*（1-优惠折扣）； 2. 增值服务优惠金额： 2.1
 *          保价费和代收手续款的优惠金额=声明价值/代收货款*（ 公司标准的保价费率/代收费率-签约合同的保价费率/代收费率）； 2.2
 *          其它优惠或费用减免由优惠规则配置生成； 1. 只有月结属性的客户才可以享受月结的付款方式；且当客户为月结客户时，
 *          付款方式自动默认为月结； 2. 只有信用额度的客户才可以享受临时欠款的付款方式；且对应客户的信用额度全国统配，
 *          不绑定部门，例：客户A在我司可享信用额度为5000，且其合同主体为部门a，
 *          A已用额度为2000，且无论客户A在a部门发货，或是在其它任务我司的部门发货， 其可用额度均为3000； 3.
 *          系统自动过滤客户不能享受的付款方式； 录入发货客户信息SUC业务规则 营业员点击运单开单，进入运单开单界面。
 *          本用例分为两个界面：录入发货客户信息、选择发货客户； 1. 录入发货客户信息： 界面为信息录入界面：包括：手机、电话、客户名称、
 *          客户编码、发货联系人（发货部门）、发货人地址； 1.1 手机：发货人手机号码； 1.2 电话：发货人电话号码； 1.3
 *          客户名称：发货客户公司或单位名称，可支持搜索查询； 1.4 客户编码：我司给客户的客户号； 1.5
 *          发货联系人（发货部门，支持模糊搜索）： 发货客户的客户姓名， 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *          “发货联系人”字段更改为“发货部门”； 1.6 发货人地址：发货客户的详细联系地址， 支持国家行政区域自动过滤； 2.
 *          选择发货客户界面： 界面为选择客户信息界面：包括两部分： 客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域：
 *          包括：客户编码、客户名称、联系人、手机、 电话、地址(规范化地址和详细地址)； 2.2. 功能按钮区域： 包括：确定、取消； 3.
 *          选择热门城市界面 界面信息包含人热门城市 4. 选择省份界面 界面信息包含省份 5. 选择城市界面 界面信息保护城市 6. 选择区县界面
 *          界面信息保护区县 1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1.
 *          系统自动查询CRM系统中对应手机号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展1a，如果没有，
 *          弹出FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作见扩展1b； 2. 规则-请参见系统规则SR1； 2 录入电话号码
 *          1. 如果手机号码没有填写， 系统自动查询CRM系统中对应电话号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展2a，如果没
 *          ，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展2b； 2. 规则-请参见系统规则SR1； 3
 *          录入客户名称和客户编码 1. 规则-请参见系统规则SR2、SR3； 4 录入发货联系人（发货部门） 1.
 *          规则-请参见系统规则SR4、SR5、SR8； 5 录入发货人地址 1. 提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段；
 *          2. 规则-请参见系统规则SR6； 1.6.2 扩展 序号 扩展事件 相关数据 备注 1a
 *          当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6、SR7； 1b
 *          当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 3.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 4. 规则-请参见系统规则SR5 、SR6、SR7； 2a
 *          当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2. 规则-请参见系统规则SR5、SR6、SR7； 2b
 *          当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2. 规则-请参见系统规则SR5、SR6、SR7； 1.7
 *          业务规则 序号 描述 SR1 1. 发货客户手机号码及固定电话至少提供一个，手机号码只能为数字并且为11位； 2.
 *          固定电话号码只能为数字，且可添加多个；添加多个时， 必须用“，”或“、”或“/”分开；固定电话号码字段也可以录入手机号； 3.
 *          手机、电话为精确查询全公司客户信息 4. 手机、电话带出的客户信息会覆盖原来已带出的客户信息。
 *          若未带出客户信息则当客户ID不为空时清空除手机外的其它已带出的客户信息（即），否则不清空 SR2 1.
 *          若发货客户为公司会员客户，则录入发货客户信息后系统界面显示该发货客户联系人编码； 2.
 *          客户名称精确查询全公司客户信息。当客户名称为带出的客户时（以隐藏的客户ID是否为空做为判断条件），
 *          清空客户时则清空手机、电话、联系人、地址、行政区域、客户编码，否则只清空客户名称、客户编码、客户ID（隐藏）； SR3 1.
 *          若发货客户为会员客户，则录入发货客户信息后系统给予提示； 2. 提未信息为在运单开单界面下方加色放大显示； 3.
 *          通过选择录入的客户名称和客户编码不可修改，但可删除录入； SR4 1. 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *          “发货联系人”字段更改为“发货部门”； 2. 联系人不用带出客户信息 SR5 1. 若为公司内部带货，输入发货部门关键字支持模糊搜索
 *          SR6 1. 客户详细地址必填至乡/镇，且乡镇下一级内容不能为空； 2. 当鼠标点击规范化地址文本框时，显示如下图片，
 *          包含热门城市、省份、城市、县区，选择热门城市， 会直接跳到区县，现在省份后自动跳到城市，
 *          选择城市后自动跳到区县（必须选择完上一级行政区域后，才能选择下一行政区域），
 *          选择完区县后，会把规范化地址显示在规范化文本框内，例如：江苏省-苏州市-相城区 3.
 *          地址可以进行拼音和首写字母进行匹配，例如输入“GZ”会在规范化地址文本框下面显示 ；
 *          匹配时可带出城市、区、县等符合的信息，该设计来自官网，具体可以参考官网 4.
 *          当增值服务中有返单业务时，则发货人地址为必填项；其他情况非必填。 SR7 1.
 *          使用电话号码和手机进行匹配，弹出CRM发货客户选择框进行选择，如果匹配不到，
 *          再使用运单发货历史客户进行匹配，弹窗选择，选择后填充客户编码、客户名称、联系人、地址； 2.
 *          但是对于电话号码匹配，只有当发货人手机、客户名称为空时，才会用电话号码检索并弹窗显示； 3.
 *          使用手机号码、电话号码、客户名称弹出选择框选择记录后覆盖原先记录 4. 修改联系人时，需要清空客户名称 5.
 *          使用手机号码弹出选择框选择记录后覆盖原先记录，查询不到时，清空客户名称 6.
 *          导入发货客户信息后，联系人名称不可修改，为灰色；当营业员进行清空发货客户的客户名称操作时， 联系人名称可修改，为可编辑状态； 7.
 *          点击客户名称查询控件，弹出查询信息:窗口SUC-424-查找会员 8. 如果查询出来的记录只有一条，也需要进行选择 9.
 *          发货客户名称精确查询且查询全公司 SR8 1. 发货人省市区默认为始发营业部的省市区 录入收货客户SUC业务规则
 *          营业员点击运单开单，进入运单开单界面。 本界面分为两个界面：录入收货客户信息、选择收货客户。 1. 录入收货客户信息：
 *          界面为信息录入界面：包括：手机、电话、发货收货联系人（发货收货部门）、发货收货人地址； 1.1 手机：发货收货人手机号码； 1.2
 *          电话：发货收货人电话号码； 1.3
 *          收货联系人（收货部门）：收货客户的客户姓名，当“运单开单”中的“开单提货方式”为“内部带货自提”时，“
 *          收货联系人”字段更改为“收货部门”； 1.4 收货人地址：收货客户的详细联系地址，支持国家行政区域自动过滤； 1.5 客户名称 1.6
 *          客户编码 2. 选择收货客户界面： 界面为选择客户信息界面：包括两部分：客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域：
 *          包括：联系人、手机、电话、地址（规范化地址和详细地址）； 2.2. 功能按钮区域： 包括：确定、取消； 3. 选择热门城市界面
 *          界面信息包含人热门城市 4. 选择省份界面 界面信息包含省份 5. 选择城市界面 界面信息保护城市 6. 选择区县界面 界面信息保护区县
 *          1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1.
 *          系统自动查询CRM系统中对应手机号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展1a，和如果没有，
 *          弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展1b； 2. 规则-请参见系统规则SR1、SR5、SR6；
 *          2 录入电话号码 1. 如果手机号码没有填写， 系统自动查询CRM系统中对应电话号码绑定的客户信息，
 *          如果有弹窗，弹窗操作见扩展2a，如果没有， 弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展2b； 1.
 *          1、系统自动查询CRM系统中对应电话号码绑定的客户信息 和FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，
 *          弹窗操作参见扩展2a； 2. 规则-请参见系统规则SR1、SR5 、SR6； 3 录入收货联系人（收货部门） 1.
 *          规则-请参见系统规则SR2、SR3； 4 录入收货人地址 1. 提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段； 2.
 *          地址在系统后台通过GIS系统进行匹配， 如果是禁行区域，地址颜色为红色，如果是进仓区域，地址颜色为黄色 3.
 *          规则-请参见系统规则SR4； 1.6.2 扩展 序号 扩展事件 相关数据 备注 1a
 *          当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *          当光标焦点移至录入手机号码时，系统自动调用CRM系统对应发货客户的历史发货记录，
 *          并自动弹窗显示所有该发货客户的历史发货记录；营业员选择一条记录，并确定， 选择的客户信息自动带信收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6； 1.
 *          规则-请参见系统规则SR5； 1b 当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时，
 *          则如果该客户在这3个月有发过货，则弹出历史收货记录自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6； 2a2a
 *          当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 当录入的电话号码在CRM系统中有电话号码绑定的客户信息，
 *          则自动弹窗并显示电话号码为录入的电话号码的所有客户信息， 营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中；
 *          客户信息客户信息 1. 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5
 *          、SR6； 1. 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2. 规则-请参见系统规则SR6； 2b
 *          当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史收货记录，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见系统规则SR5 、SR6； 1.7 业务规则
 *          序号 描述 SR1 1. 收货客户手机号码及固定电话至少提供一个，手机号码只能为数字并且为11位，
 *          固定电话号码只能为数字，且可添加多个； SR2 1. 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *          “收货联系人”字段更改为“收货部门”； SR3 1.
 *          若为公司内部带货，则收货客户信息中的收货部门名称必须与OA系统中组织架构名称保持一致； SR4 1.
 *          客户详细地址必填至乡/镇，且乡镇下一级内容不能为空； 2. 当鼠标点击规范化地址文本框时，显示如下图片，
 *          包含热门城市、省份、城市、县区，选择热门城市，会直接跳到区县， 现在省份后自动跳到城市，
 *          选择城市后自动跳到区县（必须选择完上一级行政区域后，才能选择下一行政区域），
 *          选择完区县后，会把规范化地址显示在规范化文本框内，例如：江苏省-苏州市-相城区 3.
 *          地址可以进行拼音和首写字母进行匹配，例如输入“GZ”会在规范化地址文本框下面显示 ；
 *          匹配时可带出城市、区、县等符合的信息，该设计来自官网，具体可以参考官网 4. 当提货方式含“自提”时，收货人地址为非必填项； SR5
 *          1. 使用电话号码和手机进行匹配，如果是唯一匹配一条CRM客户信息时，
 *          填充客户编码、客户名称、联系人、地址，如果有多条需弹出选择框进行选择，
 *          如果查询不到CRM客户信息时，使用FOSS三个月运单历史记录中的收货信息查询，
 *          唯一匹配一条进行填充收货客户信息，多条进行弹窗选择，如果都查询不到，不做其他操作 2.
 *          但是对于电话号码匹配，只有当发货人手机、客户名称为空时，才会用电话号码检索并弹窗显示 3.
 *          使用手机号码、电话号码、客户名称弹出选择框选择记录后覆盖原先记录 4.
 *          修改联系人时，需要清空客户名称，当清空客户名称时，会同时删除客户编码 5. 使用手机号码、电话号码弹出选择框选择记录后覆盖原先记录 6.
 *          点击客户名称查询控件，弹出查询信息:窗口SUC-424-查找会员 7. 身份证号、客户编码、客户名称、联系人编码可以查询到全公司的客户
 *          1. 只有当收货人手机为空，且发货客户信息已录入时，才会检索并弹窗显示； 2. 若未查询到历史记录，则无法提示； 3.
 *          通过选择录入的收货人信息均可修改； 4.
 *          使用号码进行匹配，如果是唯一匹配一条CRM客户信息时，填充客户编码和客户名称，如果有多条不做操作 SR6 1.
 *          通过选择录入收货发货信息，同时带出对应的目的站和提货网点信息； 2. 带出目的站仍然使用GIS进行查询，并以GIS返回为准； 3.
 *          若GIS未查询得出，则以历史开单的目的站和提货网点信息为准； 查找会员SUC业务规则
 *          营业员点击运单开单界面中的查询客户，进入查询客户信息界面。 本界面为查询客户信息。
 *          界面主要分为三个部分：查询条件区域、查询结果区域、功能按钮。 1. 查询条件区域： 1.1
 *          会员卡号：支持会员卡号的模糊搜索，并可查询部门全部会员信息； 1.2 发货联系人 1.3 电话 1.4 客户编码 1.5 客户名称
 *          1.6 手机 1.7 发货人地址 1.8 复选框"查询全公司" 2. 查询结果区域： 2.1 客户编码 2.2 客户名称 2.3
 *          联系人编码 2.4 月结审核 2.5 联系人 2.6 手机 2.7 电话 2.8 身份证 2.9 信用额度 2.10 地址 2.11
 *          越发越送审核编号 2.12 生效时间 2.13 失效时间 3. 功能按钮： 3.1 重置： 3.2 查询； 3.3 查询部门会员 1.6
 *          操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入查询条件，查询符合条件的客户信息 查询条件信息 1.
 *          系统查询CRM系统中本部门符合条件的客户信息； 2. 规则-请参见系统规则SR1； 2 查询本部门所有的会员客户信息 1.
 *          规则-请参见系统规则SR2； 3 导入选择的客户信息至发/收货客户信息中：双击选中的客户信息， 对应客户信息进入发/收货客户信息中 1.
 *          规则-请参见系统规则SR3； 1.6.2 扩展 序号 扩展事件 相关数据 备注 1a
 *          步骤1中，若未查询到符合条件的客户信息，系统给予提示 1. 提示信息为“未查询到符合条件的客户信息！”； 2b
 *          步骤2号，若本部门无会员，系统给予提示 1. 提示信息为“部门无会员信息！”； 1.7 业务规则 序号 描述 SR1 1.
 *          支持单一和组合查询条件查询； 2. 只有勾选复选框时，按照身份证号、客户编码、
 *          客户名称、联系人编码精确查全公司客户信息且查询条件中包含有身份证号、 客户编码、客户名称、联系人编码其中任意一项时，忽略其它查询条件，
 *          否则为模糊查询本部门客户信息； 3. 当查询到的记录为某客户编码中的其中一条信息时， 显示该编码对应的所有的客户信息； 4.
 *          当客户为越发越送客户时，则显示该客户对应的越发越送审核编号、 生效时间、失效时间；当越发越送审核编号有多个时，则只显示最后一次审核信息；
 *          SR2 1. 当条件都为空时，点击查询按钮，查询出本部门会员； SR3 1. 通过查询条件带出的客户信息被填充到相关控件中时，
 *          发货客户联系人为不可修改状态， 若要修改则需要清空全部带出的客户信息； 确认公布总价SUC业务规则 1 打开“运单开单”界面。 2
 *          录入发货人信息 参考SUC-492 3 录入收货客户信息 参考SUC-493 4 录入货物信息 参考SUC-494 5 录入运输信息
 *          参考SUC-496 进行前面5操作之后，若是空运， 系统读取空运公布价价格方案（基础资料参考SUC-581）
 *          计算出公布总价，显示在运单开单界面。 若是汽运，系统读取汽运公布价价格方案（基础资料参考SUC-581） 计算出公布总价，
 *          显示在运单开单界面。 参考规则SR1，SR2，SR3 扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 5a 5b 1.7
 *          业务规则 序号 描述 SR1 1）汽运：上门发货汽运运费最低X元一票； （同城、卡航；可配置）；
 *          上门接货汽运运费最低X元一票；（同城、卡航；可配置）； 2）空运：空运运费最低X元一票；（可配置）； SR2
 *          1）汽运：当货物为“接货”时， 系统自动匹配生成公布价“接货价格方案”； 当货物为“非接货”时，
 *          系统自动匹配生成公布价“非接货的价格方案”； 2）空运：系统自动匹配公布价 “空运价格方案”； 空运价格只有上门发货一套价格方案，
 *          如有接货费在其他费用里添加一项接货费。 SR3 1）计费方式分为重量计费、体积计费；
 *          重量、体积计费的运费=每公斤单价与货物实际重量的乘积 或 每方单价与货物实际体积的乘积，
 *          对于一票货物，系统按重量和体积分别计算并取大优先的原则计费给出公布价总运费, 计费方式即为取大的一方； 运单提交SUC业务规则
 *          客户上门发货确认承运信息后，营业员告知客户运输费用后， 为客户开具运单，打印标签并粘贴至货物的过程。 1.3 用例条件 条件类型 描述
 *          引用系统用例 前置条件 1. 运单已填写完整 DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-客户上门-根据订单确认承运信息-导入订单-V0.1.doc
 *          DP-FOSS-接送货系统用例-客户上门-确认承运信息-查询目的站-V0.1.doc
 *          DP-FOSS-接送货系统用例-客户上门-承运计费报价-确认公布总价-V0.1.doc 后置条件 1.
 *          传送运单号、金额、帐号等结算数据到财务子系统 2. 传送货物名称、件数、重量等货物信息到中转子系统，安排运输计划 3.
 *          传送运单号等信息到官网，客户查询运单状态 4. 给收货人发送出发短信，给上门接货客户发送短信 5.
 *          订单信息反馈给给CRM系统，订单处理结果为：已开单。 6、 当运输性质为精准空运时，
 *          提交成功后会自动生成订舱信息流到最终配载部门（即总调）的舱位信息中 1.4 操作用户角色 操作用户 描述 营业员 1.
 *          确认客户承运信息，开单收货， 收取现付金额，且打印标签及运单出发联让客户签字确认。 1.5 界面要求 1.5.1 表现方式 Web方式
 *          1.5.2 界面原型 1.5.2.1 集中开单界面 1.5.2.2 营业部开单界面 1.5.2.3 运单确认提交界面 1.5.3
 *          界面描述 1.5.3.1 营业部、集中开单界面 界面共包括7个部分：1、发货客户信息； 2、收货客户信息；3、货物信息；
 *          4、运输信息；5、增值服务信息； 6、计费付款；7、功能按钮。
 *          具体描述参考DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc 1.5.3.2 运单确认提交界面
 *          界面共包括3个部分：1、运单基本信息； 2、功能复选框；3、功能按钮 1、运单基本信息：单号、到付总运费、预付总运费、
 *          代收货款、保险价值、收货人名称、付款方式、提货网点、 提货方式、收货人地址、收货人电话、货物名称、
 *          重量/体积/件数、包装、重量/体积/件数（代打木架）。 2、功能选项：打印运单下拉框有各种版本（可以配置，且可以配置默认模板）
 *          （选择之后，点击确定系统自动打印运单，只能选其一，参考规则SR8）、 打印标签（选择之后，点击确定系统自动打印标签）、
 *          提交后新增（选择之后，点击确定系统打开一个新的开单界面）。 3、功能按钮：确定、取消 。 1.6 操作步骤 序号 基本步骤 相关数据
 *          补充步骤 1 营业员“填写”完整的运单信息。 2 点击暂存 1、客户上门提供的货物承运信息。 2、来此crm系统订单的数据。
 *          3、上门接货客户提供的承运信息。 1、用户暂存的运单数据时允许修改 23 点击“提交”按钮提交运单。 1、客户上门提供的货物承运信息。
 *          2、来此crm系统订单的数据。 3、上门接货客户提供的承运信息。 1、点击运单“提交”按钮，弹出“运单确认提交页面”，
 *          页面默认选择上“打印运单（全打）”、“打印标签”、 “确定后新增运单”复选框。
 *          2、提交后，同步数据到中转子系统，、CRM系统、官网系统， 财务子系统。(将单独出来写接口用例，此处将参考这些接口系统用例)
 *          3、系统自动根据出发部门、运输性质、到达部门生成默认唯一走货路径(基础资料)； 参考综合系统基础资料用例。
 *          4、当运输性质为精准空运时，提交成功后会自动生成订舱信息 （包括：预计出发时间、航班时间（早中晚）、重量）
 *          流到最终配载部门（即总调）的舱位信息中。 5、保存使用的价格版本号 56、参考规则SR1、SR12 34
 *          点击运单确认提交页面的“确定”按钮。 1、点击确定后，打开一个新的运单开单界面。 2、系统自动打印运单,选择系统默认的打印模板，
 *          参考DP-FOSS-接送货系统用例-客户上门-确认承运信息-打印运单-V0.1.doc
 *          3、系统自动打印标签，参考DP-FOSS-接送货系统用例-客户上门-确认承运信息-打印标签V0.1.doc 4、调用中转入库接口
 *          参考规则SR13 45 若有签收单返单时，需要打印签收单标签， 打印内容包括：单号、始发部门、到达部门、目的站。
 *          参考SUC-504打印签收单标签(整车) 扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 2a
 *          营业员在弹出代打木架对话框“录入第X件”需要打木架。 若货物需要代打木架，系统自动弹出代打木架对话框。代打木架精确到第几件。
 *          参考规则SR3 2b2a 如运单必填信息未填写完整或填写内容不符合要求 （参考数据元素输入限制、长度、是否必填、运单号重复等），
 *          提交时给予提示。 提示为：“×××未填写整或输入内容不符合要求，请重新输入！”， 且将此文本输入框标记为红色，光标置于此文本框中。
 *          重新填写正确完整后，跳转步骤1。 参考规则SR7 2c2b 若为月结或临时欠款，
 *          如果客户既有应收账款金额加上本次应收金额超过客户最大信用额度， 不能提交开单。
 *          弹出提示：客户既有应收账款金额加上本次应收金额超过客户最大信用额度， 不能提交开单。参考SR9 *2d2c
 *          若为月结或临时欠款，如果客户已有应收账款超过最大账期，不能提交开单。 弹出提示：客户已有应收账款超过最大账期，不能提交开单。参考SR9
 *          2e2d 提交时，当重量体积比不在设置的区间（重量体积比基础资料）中， 弹出提示“请确认录入的重量体积是否准确！”；
 *          （该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面； 点击取消，点返回运单录入界面；当X在区间中，无提示；
 *          直接进入确认运单信息界面； 参考规则SR10 3a 步骤3中，可点击“取消”按钮，取消提交运单。
 *          取消提交运单，返回到系统运单开单界面，界面信息可编辑， 修改信息后，可再次进行提交，跳到步骤2。 3b
 *          步骤3中，也可取消选择默认选择的复选框。 1、如取消选择“打印运单”，确定之后，系统不进行自动打印运单，
 *          需点击开单界面上的“打印运单”按钮，打印运单出发联。 2、如取消选择“确定后新增运单”，确定之后，
 *          系统返回到填写完整的运单开单界面，但是运单界面成灰色不可编辑， 需点击开单界面上的“新增”按钮，新增运单。
 *          3、如取消选择“打印标签”，确定之后， 系统不进行自动打印标签，需点击开单界面上的“打印标签”按钮，打印运单标签。 1.7 业务规则
 *          序号 描述 SR1 1、若为上门接货，开单提交生成后， 系统短信通知发货人及收货人。 短信模板可在系统中进行设置。
 *          给发货人或收货人发送短信时， 若无手机号码则不发送。 发送短信内容中包含“货物的件数”。 注：“货物的件数”为货物包装之前的件数。
 *          系统自动给发货人、收货人发送货物出发短信（短信模板内容可配置）； 否则只需要给收货人发短信；若收发货人无手机号码则不发送。
 *          给收货人的短信内容：您好！这里是德邦物流， （发货人姓名***）从（出发城市***）给您发来货物，
 *          单号为（****）的（***货物的件数）件货，即日出发。
 *          目的地（*****客户的收获地址）。附：德邦物流营业部的电话、地址、营业部名称。 给发货人的短信内容：您好！这里是德邦物流，
 *          您从（出发城市***）给（收货人姓名***）托运的货物， 单号为（****）的（***货物的件数）件货，即日出发。
 *          目的地（*****客户的收获地址）。附：德邦物流营业部的电话、地址、营业部名称。 SR2
 *          运单现付金额不为0，则在出发部门生成现金收款单； 若到付金额不为0，则在到达部门生成应收单若付款方式为“现付”，
 *          “银行卡”，则在出发部门生成现金收款单； 若付款方式为“到付”在到达部门生成应收单。 若付款方式为“临时欠款”，“网上支付”，
 *          “月结”在出发部门生成应收单；若运单包含“代收货款”， 则在出发部门生成应付单，到达部门生成应收单；
 *          若运单包含“装卸费”，则在出发部门生成应付单。 SR3 开单代打木架外场默认为第一外场，
 *          如果第一外场不支持打木架，则营业员自己判定选择的代打木架外场。 开单代打木架外场默认为开单走货路径中第一个可代打木架的外场，不可修改。
 *          1）若货物包装中含有“木”字样，且走货路径上有代打木架外场， 系统提示：是否需要代打木架？；
 *          2）选择代打木架后，若默认的代打木架外场非走货路径上第一外场时， 系统给予提醒，便于营业员与客户衡量是否继续代打木架；
 *          3）若货物包装中含有“木”字样，但走货路径上没有可代打木架的外场，
 *          则不能开代打木架，系统提示：走货路径上没有可代打木架的外场，不能代打木架。 SR4 当运输性质为 “精准卡航”及“精准城运”时，
 *          系统自动计算预计出发时间与预计提货/派送时间。 ①预计出发时间： 由预计出发日期和准点出发时点（取自基础资料）组成，
 *          形如【2012-04-09 12:00:00】。 若开单当前时点在准点出发时点前，
 *          则预计出发日期=开单日期；否则，预计出发日期=开单日期+1； ② 预计提货时间（开单提货方式为自提）：
 *          由预计提货日期和到达营业部承诺时点（取自基础资料）组成， 形如【2012-04-09 12:00:00】。若为当天出发，
 *          则预计提货日期=预计出发日期+到达营业部承诺天数； 否则预计提货时间=预计出发日期+到达营业部承诺天数-1。 ③
 *          预计派送时间（开单提货方式为送货）： 由预计派送日期和派送承诺时点（取自基础资料）组成， 形如【2012-04-09
 *          12:00:00】。若为当天出发， 预计派送时间=预计出发日期+到达营业部承诺天数+派送承诺需加天数；
 *          否则预计派送时间=预计出发日期+派送承诺需加天数-1。 SR5 如果开单信息来自集中或非集中上门接货： 1、司机有PDA
 *          PDA开单后：则点击“PDA补录”走PDA补录流程引用系统用例SUC-491-补录运单。 2、司机无PDA
 *          则新增运单，进入开单界面走提交运单流程。 SR6 “运单确认提交界面”上的复选框可以单选，可以多选。 SR7
 *          提交时系统校验规则参考如下用例规则： SUC-492录入发货客户信息 SUC-493录入收货客户信息 SUC-494录入货物信息
 *          SUC-495录入增值服务信息 SUC-496录入运输信息 SUC-311导入订单 SUC-397 确认公布总价 SUC-126
 *          查询目的站 SR8 运单确认提交页面，打印运运单下拉框只能选其一， 默认为运单模板配置基础资料中配置默认的模板，
 *          如果不需要打印，不勾选即可。 SR9 若为月结或临时欠款，则系统需要对客户应收账款日期及信用额度进行判断： 1）欠款天数
 *          以客户为基本单位，开单/派送时付款方式为“临时欠款”， 最长一笔欠款时间超过30天，该客户将不能继续开单为“临时欠款”，
 *          假如客户的临时欠款未还款金额超过客户自己的信用额度， 也不能继续开单；开单付款方式为“月结”，最长一笔欠款时间超过70天，
 *          该客户将不能继续开单为“月结”；（始发应收账款从开单之日起计算，
 *          到付应收账款从第一次派送出库之日起计算，以更改方式更改为“临时欠款”或“月结”的， 从开单之日开始计算） 2）欠款额度
 *          临时欠款：以部门为单位，根据收入等级（前三个月最高收入金额） 设置该部门每月临时欠款最高额度（余额），详见下表；
 *          当部门临时欠款未还款金额大于该金额时， 将无法继续开单未“临时欠款”； 收入区间 项目 10万以下 3万 10-15万 5万
 *          15-20万 7万 20-30万 10万 30-50万 15万 50-100万 20万 100万以上 40万
 *          月结：以客户为单位，连续2个月发货金额在3000元以上可申请月结， 月结额度不得高于近期最高走货金额的2倍，
 *          若客户连续两月发货量低于2000元，取消月结权限。 SR10 1. 系统设置货物重量体积比区间值（该值由基础资料配置），
 *          在运单提交时，系统自动对重量体积比进行校验：即重量体积比X=重量/体积；
 *          当X不在设置的区间中，弹出提示“请确认录入的重量体积是否准确！”； （该弹窗有两个按钮：确定、取消）点击确定时，弹出确认运单信息界面；
 *          点击取消，点返回运单录入界面；当X在区间中，无提示； 直接进入确认运单信息界面； SR11 1）用户可以随时录入运单信息随时暂存运单，
 *          但必须录入打印标签所需的关键字段才可暂存运单。 目前暂存运单打印标签所需运单信息包含收货部门、
 *          运单号、目的站、提货网点、运输性质、提货方式、包装、件数。 （注：当走货路由经过特定的城市时还需要录入货物类型）
 *          2）系统对录入的运单信息合法性进行校验； 3）运单暂存后不生成正式运单信息、库存信息及财务单据；
 *          4）用户可对已暂存的运单进行修改、暂存、提交（生成正式运单）； 5）若暂存运单，系统锁定运单号及订单信息；
 *          6）暂存的运单不可打印运单出发联； SR12 若PDA开单已打印标签， 在提交运单后弹出的确认界面中打印标签默认不勾选，
 *          若需打印标签则手动勾选"打印标签"复选框。 SR13 1）、如果开单组织是营业部门， 那么开单提交时，
 *          检验是否是驻地部门，如果是驻地部门， 入库对应驻地部门外场， 如果是营业部，入库对应营业部 2）、如果开单组织是开单组，那么开单提交时，
 *          校验是否已经打印标签，如果没有，入库配载部门； 录入运输信息（整车）SUC业务规则 营业员点击整车运单开单，进入整车运单开单界面。
 *          本界面为录入整车运输信息。 界面信息包括：收货部门、单号、目的站、提货网点、 对外备注、对内备注、储运注意事项、配载部门、
 *          最终配载部门、到达类型 1. 收货部门：揽货部门； 2. 单号：运单单号； 3. 提货网点：收货客户可以领取货物的部门； 4.
 *          对外备注：客户可以看到的备注信息，包括：空、 保丢不保损、“不承保发霉、变质、虫蛀虫咬之损失”、 “
 *          不承保刮花、变形、撞凹之损失”、不可重压、易潮、 不可倒置、客户指定提货网点； 5. 对内备注：仅限公司内部人员看到的备注信息； 6.
 *          到达类型：到达客户处还是到达营业部 7. 储运注意事项：对外备注和对内备注信息的叠加； 8. 配载部门:开单收货部门出发货配载专线；
 *          1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 录入收货部门 1. 规则-请参见系统规则SR1； 2 录入单号
 *          系统校验单号的合法性 1. 规则-请参见系统规则SR2； 3 录入提货网点 1. 规则-请参见系统规则SR3； 4 录入对外备注 1.
 *          规则-请参见系统规则SR4； 5 录入对内备注 6 到达类型 1. 规则-请参见系统规则SR5； 6 生成储运注意事项 1.
 *          规则-请参见系统规则SR6； 7 生成配载部门 1. 规则-请参见系统规则SR7； 8 生成最终配载部门 1.
 *          规则-请参见系统规则SR8； 1.7 业务规则 序号 描述 SR1 1. 收货部门默认为操作者所在的部门，不可修改； SR2 1.
 *          单号最大长度为8位，如果超过提示“单号大于8位” 2. 单号不能少于8位，“如果少于8位“单号长度少于7位” 3.
 *          单号与最近开单单号前六位如果不等，那么提示“前后两票单号相差过大， 请检查所输单号是否为本部门所属单号！” SR3 1.
 *          提货网点可由收货客户地址的城市生成，也可手工选择； 2. 系统自动过滤只显示符合录入文本条件的网点信息； SR4 参考SUC-496
 *          录入运输信息 对对外备注的处理 SR5 1. 如果在 “是否经过营业部”打勾，付款方式可以选择到付项， 代收货款框可以修改； 2.
 *          如果在“是否经过营业部”不打勾，付款方式中的到付项自动移除， 代收货款清0且不可修改； SR6 1.
 *          储运注意事项=对外备注&对内备注，各字段以“；”分开； 2. 对外备注永远在储运注意事项的最前面； SR7 1.
 *          通过出发部门和提货网点系统自动匹配始发配载部门基础资料； 录入收货客户信息（整车）SUC业务规则 营业员点击运单开单，进入运单开单界面。
 *          本界面分为两个界面：录入收货客户信息、选择收货客户。 1. 录入收货客户信息：
 *          界面为信息录入界面：包括：手机、电话、发货收货联系人（发货收货部门）、 发货收货人地址； 1.1 手机：发货收货人手机号码； 1.2
 *          电话：发货收货人电话号码； 1.3 收货联系人（收货部门）：收货客户的客户姓名，
 *          当“运单开单”中的“开单提货方式”为“内部带货自提”时， “收货联系人”字段更改为“收货部门”； 1.4
 *          收货人地址：收货客户的详细联系地址， 支持国家行政区域自动过滤； 1.5 客户名称 1.6 客户编码 2. 选择收货客户界面：
 *          界面为选择客户信息界面：包括两部分： 客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域：
 *          包括：联系人、手机、电话、地址（规范化地址和详细地址）； 2.2. 功能按钮区域： 包括：确定、取消； 3. 选择热门城市界面
 *          界面信息包含人热门城市 4. 选择省份界面 界面信息包含省份 5. 选择城市界面 界面信息保护城市 6. 选择区县界面 界面信息保护区县
 *          1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1.
 *          系统自动查询CRM系统中对应手机号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展1a，
 *          如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展1b； 2.
 *          规则-请参见SUC-493-录入收货客户信息SR1、SR5、SR6； 2 录入电话号码 1. 如果手机号码没有填写，
 *          系统自动查询CRM系统中对应电话号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展2a，
 *          如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展2b； 2.
 *          1、规则-请参见SUC-493-录入收货客户信息SR1、SR5 、SR6； 3 录入收货联系人 1. 4 录入收货人地址 1.
 *          提供下拉框选择输入， 系统自动过滤输入的行政区下一级行政级的字段； 2. 地址在系统后台通过GIS系统进行匹配，
 *          如果是禁行区域，地址颜色为红色，如果是进仓区域，地址颜色为黄色 3. 规则-请参见系统规则SR4； 1.6.2 扩展 序号 扩展事件
 *          相关数据 备注 1a 当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5
 *          、SR6； 1b 当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史收货记录，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5
 *          、SR6； 2a 当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5
 *          、SR6； 3. 2b 当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史收货记录，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2. 规则-请参见SUC-493-录入收货客户信息SR5
 *          、SR6； 4. 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1.
 *          系统自动查询CRM系统中对应手机号码绑定的客户信息和 FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗， 弹窗操作见扩展1b；
 *          2. 规则-请参见系统规则SR1； 2 录入电话号码 1.
 *          系统自动查询CRM系统中对应电话号码绑定的客户信息和FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作参见扩展2a；
 *          2. 规则-请参见系统规则SR1； 3 录入客户名称 1. 规则-请参见系统规则SR2 4 录入收货联系人 5 录入收货人地址 1.
 *          提供下拉框选择输入， 系统自动过滤输入的行政区下一级行政级的字段； 2. 地址在系统后台通过GIS系统进行匹配，
 *          如果是禁行区域，地址颜色为红色， 如果是进仓区域，地址颜色为黄色; 3. 规则-请参见系统规则SR3； 1.6.2 扩展 序号 扩展事件
 *          相关数据 备注 1a 当光标焦点移至录入手机号码时， 系统自动调用CRM系统对应发货客户的历史发货记录，
 *          并自动弹窗显示所有该发货客户的历史发货记录； 营业员选择一条记录，并确定，选择的客户信息自动带信收货客户信息中； 客户信息 1.
 *          规则-请参见系统规则SR4； 1b 当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *          则自动弹窗并显示手机号码为录入的手机号的所有客户信息， 营业员选择录入一条记录，并确定； 选择的客户信息自动带入收货客户信息中；
 *          客户信息 1. 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2a
 *          当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入收货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 1.7 业务规则 序号 描述 SR1 1.
 *          收货客户手机号码及固定电话至少提供一个， 手机号码只能为数字并且为11位，固定电话号码只能为数字， 且可添加多个； SR2 1.
 *          若收货客户为公司会员客户， 则录入收货客户信息后系统界面显示该收货客户联系人编码； SR3 1. 客户详细地址必填至乡/镇，
 *          且乡镇下一级内容不能为空； SR4 1. 只有当收货人手机为空， 且发货客户信息已录入时，才会检索并弹窗显示； 2.
 *          若未查询到历史记录，则无法提示； 3. 通过选择录入的收货人信息均可修改； 1. 录入货物信息（整车）SUC业务规则
 *          营业员点击运单开单，进入运单开单界面。 本界面为录入货物信息。 界面主要分为一个部分：录入货物信息。 1. 录入货物信息：
 *          录入信息包括：货物名称、总件数、总重量、货物尺寸、总体积、货物包装 1.1 货物名称：货物的名称； 1.2 总件数：收货时货物的总件数；
 *          1.3 总重量：收货时货物的总重量； 1.4 总体积：收货时货物的总体积； 1.5 货物包装：货物的包装数； 1.6 操作步骤
 *          1.6.1 录入货物信息 序号 基本步骤 相关数据 补充步骤 1 修改货物名称 1. 系统自动匹配违禁品，生成规则； 2.
 *          规则-请参见系统规则SR1； 1.7 业务规则 序号 描述 SR1 1. 若货物为违禁品，则系统自动提示“货物为违禁品， 不可开单！”；
 *          2. 违禁品、拒收品、具体类型可在系统中进行配置； SR2 新增一条业务规则：在提交运单之前，
 *          若相邻两次输入的单号（两次单号分别为A与B且单号输入合法）差别较大时，
 *          系统给予用户友好提示避免录错单号，但不限制单号的输入。具体规则为：
 *          1）若前后两次输入的单号位数相同，当|A-B|≥100时系统给予提示； 2）若前后两次输入的单号位数不同，系统给予提示；
 *          3）提示信息为：前后两票单号相差过大， 请检查所输单号是否是本部门所属单号！ 营业员点击运单开单，进入运单开单界面。
 *          本用例分为两个界面：录入发货客户信息、 选择发货客户； 1. 录入发货客户信息： 界面为信息录入界面：包括：手机、电话、客户名称、
 *          客户编码、发货联系人（发货部门）、发货人地址； 1.1 手机：发货人手机号码； 1.2 电话：发货人电话号码； 1.3
 *          客户名称：发货客户公司或单位名称， 可支持搜索查询； 1.4 客户编码：我司给客户的客户号； 1.5
 *          发货联系人（发货部门）：发货客户的客户姓名， 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *          “发货联系人”字段更改为“发货部门”； 1.6 发货人地址：发货客户的详细联系地址， 支持国家行政区域自动过滤； 2.
 *          选择发货客户界面： 界面为选择客户信息界面：包括两部分： 客户信息列表区域、功能按钮区域； 2.1. 客户信息列表区域：
 *          包括：客户编码、客户名称、联系人、手机、 电话、地址(规范化地址和详细地址)； 2.2. 功能按钮区域： 包括：确定、取消； 3.
 *          选择热门城市界面 界面信息包含人热门城市 4. 选择省份界面 界面信息包含省份 5. 选择城市界面 界面信息保护城市 6. 选择区县界面
 *          界面信息保护区县 1.6 操作步骤 1.6.1 录入 序号 基本步骤 相关数据 补充步骤 1 录入手机号码 1.
 *          系统自动查询CRM系统中对应手机号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展1a，
 *          如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展1b； 2.
 *          规则-请参见系统规则SUC-492-录入发货客户信息SR1； 2 录入电话号码 2.
 *          如果手机号码没有填写，系统自动查询CRM系统中对应电话号码绑定的客户信息， 如果有弹窗，弹窗操作见扩展2a，
 *          如果没有，弹出FOSS历史开单记录中对应手机号码绑定的客户信息， 并弹窗，弹窗操作见扩展2b； 3.
 *          规则-请参见系统规则SUC-492-录入发货客户信息SR1； 3 录入客户名称和客户编码 1.
 *          规则-请参见系统规则SUC-492-录入发货客户信息SR2、SR3； 4 录入发货联系人 5 录入发货人地址 1. 提供下拉框选择输入，
 *          系统自动过滤输入的行政区下一级行政级的字段； 2. 规则-请参见系统规则SUC-492-录入发货客户信息SR6； 1.6.2 扩展 序号
 *          扩展事件 相关数据 备注 1a 当录入的手机号码在CRM系统中有手机号码绑定的客户信息，
 *          则自动弹窗并显示手机号码为录入的手机号的所有客户信息， 营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息
 *          1. 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则； 2.
 *          规则-请参见系统规则SUC-492-录入发货客户信息SR5 、SR6、SR7； 1b
 *          当录入的手机号码在CRM系统中没有手机号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，营业员选择录入一条记录，
 *          并确定；选择的客户信息自动带入发货客户信息中； 客户信息 3. 规则-请参见系统用例SUC-492中的“1.6.2扩展1a”的规则；
 *          4. 规则-请参见系统规则SUC-492-录入发货客户信息SR5 、SR6、SR7； 2a
 *          当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1.
 *          规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则； 2.
 *          规则-请参见系统规则SUC-492-录入发货客户信息SR5、SR6、SR7； 2b
 *          当录入的电话号码在CRM系统中没有电话号码绑定的客户信息时， 如果该客户在这3个月有发过货，则弹出历史发货记录，营业员选择录入一条记录，
 *          并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1. 规则-请参见系统用例SUC-492中的“1.6.2扩展2a”的规则；
 *          2. 规则-请参见系统规则SUC-492-录入发货客户信息SR5、SR6、SR7； 1 录入手机号码 1.
 *          系统自动查询CRM系统中对应手机号码绑定的客户信息和 FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作见扩展1a；
 *          1. 规则-请参见系统规则SR1； 2 录入电话号码 1. 系统自动查询CRM系统中对应电话号码绑定的客户信息
 *          和FOSS历史开单记录中对应手机号码绑定的客户信息，并弹窗，弹窗操作参见扩展2a； 1. 规则-请参见系统规则SR1； 3
 *          录入客户名称和客户编码 1. 规则-请参见系统规则SR2、SR3； 4 录入发货联系人 5 录入发货人地址 1. 提供下拉框选择输入，
 *          系统自动过滤输入的行政区下一级行政级的字段； 2. 规则-请参见系统规则SR4； 1.6.2 扩展 序号 扩展事件 相关数据 备注 1a
 *          当录入的手机号码在CRM系统中有手机号码绑定的客户信息， 则自动弹窗并显示手机号码为录入的手机号的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1. 规则-请参见系统规则SR5； 2a
 *          当录入的电话号码在CRM系统中有电话号码绑定的客户信息， 则自动弹窗并显示电话号码为录入的电话号码的所有客户信息，
 *          营业员选择录入一条记录，并确定；选择的客户信息自动带入发货客户信息中； 客户信息 1. 规则-请参见系统规则SR6； 1.7 业务规则
 *          序号 描述 SR1 1.规则参考SUC-492-录入发货客户信息SR1 SR2 1. 规则参考SUC-492-录入发货客户信息SR2
 *          SR3 1.规则参考SUC-492-录入发货客户信息SR3 SR4 1. 规则参考SUC-492-录入发货客户信息SR6 SR5 1.
 *          规则参考SUC-492-录入发货客户信息SR7 SR6 1. 规则参考SUC-492-录入发货客户信息SR8 确认整车总价SUC业务规则
 *          营业员点击整车开单，进入整车开单界面。 本界面为录入整车收银界面。 1. 约车报价：营业员约整车后，请车员受理的价格； 2.
 *          开单报价：营业员根据请车员的受理价格，给客户的实际运费价格； 3. 增值服务费用 4. 总运费：本次承运的客户应付金额； 5.
 *          开单付款方式：客户的付款方式，包括：现金、银行卡、月结、临时欠款、到付； 6. 预付金额 7. 到付金额； 1.6 操作步骤 序号
 *          基本步骤 相关数据 补充步骤 1 录入开单报价 1. 规则-请参见系统规则SR1； 2 查看约车报价、增值服务费用入总运费 1.
 *          规则-请参见系统规则SR2、SR3； 3 选择开单付款方式 1. 规则-请参见系统规则SR4、SR5； 4 预付金额 1.
 *          规则-请参见系统规则SR4、SR5； 5 到付金额 1. 规则-请参见系统规则SR4、SR5； 1.7 业务规则 序号 描述 SR1
 *          1. 开单报价默认等于约车报价； 2. 修改的开单报价只能为约车报价的某个范围区间中，该范围区间可由公司统一配置； SR2 1.
 *          整车导入约车开单时，约车报价为请车员受理的约车价格，不可修改； SR3 1. 总运费=开单报价+增值服务费； 2.
 *          增值服务费=保价费+代收手续费+预付运费保密服务费+返单费+包装费； SR4 1.
 *          整车的付款方式包含现金、银行卡、月结、临时欠款、到付； 2. 支持发货客户的多种付款方式叠加，但月结和临时欠款不能同时存在； 2.
 *          开单只能选择一种付款方式，不可选择两种或以上； 3. 月结客户可以开月结； 4. 若客户非月结，则自运过滤月结的付款方式； 5.
 *          付款方式为到付或者临时欠款时，不能选择预付运费保密； 6. 若整车开单选择直接到达客户处，则不能办理到付； 7.
 *          有信用额度且额度大于等于总运费的发货客户，才可以选择临时欠款； SR5 1. 预付金额必须大于0才能选择预付运费保密； 2.
 *          当付款方式为现付时，预付金额必须大于0；否则， 提示信息“付款方式非到付，预付金额不能小于等于0”； 3.
 *          当付款方式为到付时，预付金额不能大于0；否则， 提示信息“付款方式为【到付】，预付金额不能大于0”； 4.
 *          月结客户的信用金额直接限制该客户的当月发货金额， 当月发货金额超过信用额度，当月将无法再开单月结，
 *          提示“该客户的剩余可用信息额度不足，不能开月结”； 5. 临时欠款时，需要客户中的信用额度超过运费，
 *          否则，提示“该客户的剩余可用信息额度不足，不能开临时欠款”； 6. 当付款方式为临时欠款等收款放货时， 现付到付均为零，不可修改；
 *          SR6 1. 约车报价、开单报价、增值服务费用、总运费、预付金额、 到付金额均为整数，按照四舍五入原则； 导入整车约车编号SUC业务规则
 *          营业员通过 界面标题： 约车信息 约车编号：受理后的约车编码 1. 录入发货客户信息： 界面为信息录入界面：包括：手机、电话、客户名称、
 *          客户编码、发货联系人、发货人地址； 1.1 手机：发货人手机号码； 1.2 电话：发货人电话号码； 1.3
 *          客户名称：发货客户公司或单位名称，可支持搜索查询； 1.4 客户编码：我司给客户的客户号； 1.5 发货联系人：发货客户的客户姓名；
 *          2. 录入收货客户信息： 界面为信息录入界面：包括：手机、电话、发货联系人、发货人地址； 1.6 手机：发货人手机号码； 1.7
 *          电话：发货人电话号码； 1.8 收货联系人：收货客户的客户姓名 1.9 收货人地址：收货客户的详细联系地址，支持国家行政区域自动过滤；
 *          3. 录入货物信息： 录入信息包括：货物名称、总件数、总重量、货物尺寸、总体积、货物包装 1.1 货物名称：货物的名称； 1.2
 *          总件数：收货时货物的总件数； 1.3 总重量：收货时货物的总重量； 1.4 总体积：收货时货物的总体积； 1.5
 *          货物包装：货物的包装数； 4.界面标题：计费付款 录入信息包括：约车报价、总运费、增值服务费、 开单付款方式、预付金额、到付金额 1.1
 *          约车报价：整车约车费用 1.2 总运费：运费总合 1.3 增值服务费：增值服务费总合 1.4 开单付款方式：开发付款的方式 1.5
 *          预付金额：现付金额 1.6 到付金额：到付金额 1.6 操作步骤 1.6.1 导入整车约车编号 序号 基本步骤 相关数据 补充步骤 1
 *          用户输入约车编号 2 用户点击“确定”按钮 约车信息 系统自动带出相应的约车信息， 约车编号不可编辑，规则参考SR1
 *          扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 1.7 业务规则 序号 描述 SR1
 *          1、校验是否有此约车编号，如果没有，提示无此约车编号， 如果有，但是没有受理，提示“约车编号未受理”，如果受理拒绝，
 *          提示“约车失败”并提示失败原因，如果单号已经导入过，不能重复导入， 提示“约车编号已经导入过”，如果不是本部门的整车约车编号，
 *          录入后提示“不能导入其他部门整车约车编号” 2、根据约车信息填充界面，请车费用填充进约车报价中。 运单提交（离线）SUC业务规则 1.1
 *          相关业务用例 BUC_FOSS_5.20.30_550 营业部离线开单 1.2 用例描述 营业员通过本用例录入提交。 1.3 用例条件
 *          条件类型 描述 引用系统用例 前置条件 1. 运单在线登录成功 2. 运单已填写完整 SUC-441录入收货客户信息(离线)
 *          SUC-442录入发货客户信息(离线) SUC-443录入货物信息(离线) SUC-444录入增值服务信息(离线)
 *          SUC-445录入运输信息(离线) SUC-412运单收银(离线) SUC-372暂存存运单(离线) SUC-362在线登录 后置条件
 *          1. 传送运单号、金额、帐号等结算数据到财务子系统 2. 传送货物名称、件数、重量等货物信息到中转子系统，安排运输计划 3.
 *          传送运单号等信息到官网，客户查询运单状态 4. 给收货人发送出发短信，给上门接货客户发送短信 5.
 *          订单信息反馈给CRM系统，订单处理结果为：离线已开单。 6、 当运输性质为精准空运时，
 *          提交成功后会自动生成订舱信息流到最终配载部门（即总调）的舱位信息中 1.4 操作用户角色 操作用户 描述 营业员
 *          通过运单开单界面，提交离线运单信息 1.5 界面要求 1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.2.1
 *          运单离线开单界面 1.5.2.2 运单确认提交界面 1.5.3 界面描述 1.5.3.1 运单开单界面 界面共包括7个部分：
 *          1、发货客户信息； 2、收货客户信息； 3、货物信息； 4、运输信息； 5、增值服务信息； 6、计费付款； 7、功能按钮。
 *          具体描述参考DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入发货客户信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入货物信息-V0.2.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入收货客户信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入运输信息-V0.1.doc
 *          DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息-V0.1.doc 运单确认提交界面 界面共包括3个部分：
 *          1、离线运单基本信息； 2、在线基本信息 3、功能复选框； 4、功能按钮 1、 运单基本信息：单号、到付总运费、预付总运费、
 *          代收货款、保险价值、收货人名称、付款方式、 提货网点、提货方式、收货人地址、收货人电话、 货物名称、重量/体积/件数、包装 。 2、
 *          运单基本信息：单号、到付总运费、 预付总运费、代收货款、保险价值、收货人名称、 付款方式、提货网点、提货方式、收货人地址、
 *          收货人电话、货物名称、重量/体积/件数、包装 3、功能复选框：打印运单（选择之后，点击确定系统自动打印运单）、
 *          打印标签（选择之后，点击确定系统自动打印标签）、 下一条离线运单信息（选择后提交运单成功后填充下一条）。 4、功能按钮：确定、取消 。
 *          1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 营业员在运单查询界面，查询出离线开单未提交的运单 2 点击“修改”
 *          收货客户信息、发货客户信息、货物信息、 增值服务信息、计费付款信息、运输信息、代打木架信息 进入开单界面 1、自动填充发货客户信息
 *          参考规则SR1、 SR2 2、自动填充发货收货客户信息 参考规则SR1 、SR2 3、自动填充货物信息 参考规则SR3，SR7
 *          4、自动填充运输信息 参考规则SR4，SR7 5、自动填充增值服务信息 参考规则SR5，SR7 6、自动填充计费付款信息
 *          参考规则SR6，SR7 填充完之后， 如果有代打木架，弹出代打木架信息录入界面 2 点击“提交”按钮提交运单。
 *          1、客户上门提供的货物承运信息。 2、上门接货客户提供的承运信息。 1.提交运单，根据系统规则SUC-439提交运单系统用例进行提交
 *          2. 调用中转接口，生成库存信息 3.系统自动弹出，运单确认提交界面 1.7 业务规则 序号 描述 SR1
 *          1.根据离线录入的发货客户信息， 使用发货联系人手机号码到远程服务器进行匹配客户信息， 如果没有，根据离线填写的发货客户信息，
 *          填充发货联系人手机号、发货联系人、发货人地址、发货人电话号码， 如果有，根据查询出来到客户信息，显示会员编码和客户名称，
 *          并根据离线填写的客户信息，填充发货联系人手机号、发货联系人、 发货人地址、发货人电话号码。填充时不需要进行联动和校验
 *          1.填充发货信息和发货信息时，根据发货客户手机到CRM进行查询， 无论查询出有多少条阻塞式弹出CRM查询框让用户进行选择，
 *          当发货客户手机为空时，根据电话号码到CRM进行查询， 无论查询出有多少条阻塞式弹出CRM查询框让用户进行选择，
 *          当用户选择时进行填充，如果客户选择取消时，不填充 2.如果修改，修改规则参考系统用例SUC-492录入发货客户信息 SR2
 *          11.如果修改，修改规则参考系统用例SUC-492录入发货客户信息和SUC-493录入收货客户信息 .根据离线录入的收货客户信息，
 *          使用收货联系人手机号码到远程服务器进行匹配客户信息， 如果没有，根据离线填写的收货客户信息，
 *          填充发货联系人手机号、发货联系人、发货人地址、发货人电话号码， 如果有，根据查询出来到客户信息，显示会员编码和客户名称，
 *          并根据离线填写的收货客户信息，填充发货联系人手机号、发货联系人、 发货人地址、发货人电话号码。填充时不需要进行联动和校验
 *          2.如果修改，修改规则参考系统用例SUC-493录入收货客户信息 SR3 1、 把货物信息填充到界面中，在填充过程中，
 *          不需要进行联动和校验 2、1、 如果修改、修改规则参考系统用例SUC-494 录入货物信息 SR4
 *          1、把运输信息到界面中，在填充过程中， 不需要进行联动和校验 2、如果修改，修改规则参考系统用例SUC-496录入运输信息 SR5
 *          1、把增值服务信息填充到界面中，在填充过程中，不需要进行联动和校验 21、如果修改，修改规则参考系统用例SUC-494录入增值服务信息
 *          SR6 1.如果发货客户有优惠协议，那么根据SUC-486-运单收银系统用例规则进行重新计价
 *          21.如果修改，修改规则参考系统用例SUC-408 运单收银 SR7 1、在填充过程中涉及到需要用基础资料、产品价格和客户资质的校验、
 *          计算、联动，都用最新信息进行校验、计算、联动， 如果离线保存时已经有计算或联动的值，需要进行覆盖， 需要最新计算和联动的值。
 *          运单收银（离线）SUC业务规则 1.1 相关业务用例 BUC_FOSS_5.20.30_550 (营业部离线开单) 1.2 用例描述
 *          当网络故障或服务器原因导致营业部不能正常开单时，客户上门发货为汽运或空运，营业员确认承运信息之后，通过离线系统计算货物总付款金额。
 *          1.3 用例条件 条件类型 描述 引用系统用例 前置条件 1. 确认承运信息已录入完毕； 后置条件 1. 录入收入； 1.4
 *          操作用户角色 操作用户 描述 营业员 可查询、确认发货/收货客户应付款金额，及客户的付款方式 开单员
 *          可查询、确认发货/收货客户应付款金额，及客户的付款方式 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2 界面原型
 *          1.5.3 界面描述 营业员点击离线运单开单，进入离线运单开单界面。 本界面标题：计费付款。 1.
 *          计费类型：包括重量计费、体积计费，默认显示重量计费； 2. 计费费率， 3. 公布价运费 4. 增值服务费用 5. 总运费 6.
 *          开单付款方式：包括现金、到付； 7. 预付金额； 8. 到付金额； 9. 计费重量。 1.6 操作步骤 1.6.1 运单收银 序号
 *          基本步骤 相关数据 补充步骤 1 查看计费类型和计费费率 1. 规则-请参见系统规则SR1、SR2； 2
 *          查看公布价运费和增值服务费及总运费 1. 规则-请参见系统规则SR3； 3 录入付款方式 1. 规则-请参见系统规则SR4； 4
 *          确认和录入预付金额和到付金额 1. 规则-请参见系统规则SR5； 5 查看计费类型和计费费率 1. 规则-请参见系统规则SR1、SR2；
 *          扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 5a 5b 1.7 业务规则 序号 描述 SR1 1.
 *          计费类型分为重量计费、体积计费， 由系统自动生成，不可修改； 默认重量计费； 2.
 *          对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费； 若按重量计费运费较按体积计费运费较高，
 *          则计费类型为重量计费；若按体积计费运费较按重量计费运费较高， 则计费类型为体积计费； 3. 运输类型为汽运时,计费重量为空，不可修改；
 *          2.4. 运输类型为空运时计费重量应为重量和体积*1000000/6000进行对比，取大； SR2 1.
 *          费率为对应计费类型、目的站、提货网点及运输类型的走货单价； 2. 目的站、提货网点及运输类型确认后，
 *          即可自动显示对应计费类型的费率；（来自本地价格基础资料） 3. 费率可以保留到小数点后2位；运费、预付金额、到付金额为整数，
 *          按照四舍五入的原则； SR3 1. 公布价运费（即重量、体积计费的运费）=每公斤单价/每方价格与货物实际重量/体积的乘积，
 *          对于一票货物系统分别按重量和体积分别计费并取大优先的原则计费； （来自价格基础资料，下载并且使用当前启用的最新的价格版本，
 *          参考SUC-547下载基础资料PC-SR1） 2. 增值服务费=送货费+包装费+保价费+代收手续费+其他费用和； 3.
 *          总运费费用=公布价运费+增值服务费+装卸费 =预付金额+到付金额； 3.4. 公布价运费=纯运费+装卸费 SR4 1.
 *          付款方式只有：到付，现付 SR5 1. 当付款方式为现付时，预付金额必须大于0；否则， 提示信息“付款方式为现付，预付金额必须大于0”；
 *          2. 当付款方式为到付时，到付金额必须大于0；否则， 提示信息“付款方式为【到付】，到付金额必须大于0”； 3.
 *          发货人和收货人都付款，付款方式为现付; 发货人跟收货人都付款，付款方式选择到付。 4.3. 支持发货人、收货人付款方式的组合，
 *          例如付款方式为到付500：那么预付金额可以未300， 到付金额为200，总和等于500 ，那么预付300，
 *          可以选择现金100元，临时欠款100，银行卡刷卡100元。 SR6 参考suc-403生成运单SR1: 系统自动检测，
 *          如果客户端X天还没有更新，不能打开离线运单界面， 并提示“请在间隔X天内，在线登录系统 确认公布总价（离线）SUC业务规则 1.1
 *          相关业务用例 BUC_FOSS_5.20.30_550 (营业部离线开单) 1.2 用例描述
 *          当网络故障或服务器原因导致营业部不能正常开单时， 客户上门发货为汽运或空运，营业员确认承运信息之后，
 *          通过离线系统计算出本地公布价与计费重量或计费体积乘积的运费。 1.3 用例条件 条件类型 描述 引用系统用例 前置条件
 *          离线系统已经更新最新且已启用的价格版本 参考规则SR4 后置条件 离线开单保存 1.4 操作用户角色 操作用户 描述 营业员
 *          营业员录入货物承运信息等。 开单员 开单员录入货物承运信息等。 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2 界面原型
 *          无 1.5.3 界面描述 无 1.6 操作步骤 1.6.1 计算公布总价 序号 基本步骤 相关数据 补充步骤 1 登陆离线系统 2
 *          打开“离线开单”界面。 3 录入发货人信息 参考SUC-442 4 录入收货客户信息 参考SUC-441 5 录入货物信息
 *          参考SUC-443 6 录入运输信息 参考SUC-445 进行前面5操作之后，若是空运，
 *          系统读取本地下载的空运公布价价格方案计算出公布总价， 显示在运单开单界面。
 *          若是汽运，系统读取本地下载的汽运公布价价格方案（计算出公布总价， 显示在运单开单界面。 参考规则SR1，SR2，SR3
 *          扩展事件写非典型或异常操作过程 序号 扩展事件 相关数据 备注 5a 1.7 业务规则 序号 描述 SR1
 *          1）汽运：上门发货汽运运费最低X元一票；（同城、卡航；可配置）； 上门接货汽运运费最低X元一票；（同城、卡航；可配置）；
 *          2）空运：空运运费最低X元一票；（可配置）； SR2 1）汽运：当货物为“接货”时，系统自动匹配生成公布价“接货价格方案”；
 *          当货物为“非接货”时，系统自动匹配生成公布价“非接货的价格方案”； 2）空运：系统自动匹配公布价 “空运价格方案”；
 *          空运价格只有上门发货一套价格方案， 如有接货费在其他费用里添加一项接货费。
 *          （来自价格基础资料，下载并且使用当前启用的最新的价格版本，参考SUC-547下载基础资料PC-SR1） SR3
 *          1）计费方式分为重量计费、体积计费；重量、 体积计费的运费=每公斤单价与货物实际重量的乘积 或 每方单价与货物实际体积的乘积，
 *          对于一票货物，系统按重量和体积分别计算并取大优先的原则计费给出公布价总运费, 计费方式即为取大的一方； SR4
 *          参考suc-403生成运单SR1: 系统自动检测，如果客户端X天还没有更新， 不能打开离线运单界面，并提示“请在间隔X天内，在线登录系统
 *          录入运输信息（离线）SUC业务规则 营业员点击运单开单，进入运单开单界面。 本界面标题：录入运输信息。
 *          界面信息包括：收货部门、单号、运输性质、配载类型、 提货方式、目的站、提货网点、上门接货、集中接货、对外备注、
 *          对内备注、储运注意事项、配载线路（配载航班）、配载部门、 最终配载部门、预计出发时间、预计派送/提货时间。 1. 收货部门：揽货部门；
 *          2. 单号：运单单号； 3. 运输性质：公司产品类型，包括精准空运、精准汽运（长途）、 精准卡航、精准城运、汽运偏线、精准汽运（短途）；
 *          4. 配载类型：公司走货方式，包括专线、偏线（外发）、合大票、单独开单； 5.
 *          提货方式：公司提供的送货方式，包括自提（不含机场提货费）、 免费自提、机场自提、送货上门、免费送货、自提、内部带货自提、送货进仓； 6.
 *          目的站：客户所发货物要到达的目的城市和区域； 7. 提货网点：收货客户可以领取货物的部门； 8.
 *          上门接货：我司提货的一种服务，司机上门接货，确认承运， 有区别于客户上门发货； 9.
 *          集中接货：是否为集中接送货区域，以集中接货的方式揽货， 是对上门接货的一个属性补充； 10.
 *          对外备注：客户可以看到的备注信息，包括：空、保丢不保损、 “不承保发霉、变质、虫蛀虫咬之损失”、“ 不承保刮花、变形、撞凹之损失”、
 *          不可重压、易潮、不可倒置、客户指定提货网点； 11. 对内备注：仅限公司内部人员看到的备注信息； 12.
 *          储运注意事项：对外备注和对内备注信息的叠加； 13. 配载线路（配载航班）:配载类型为偏线（外发）或专线时时，
 *          显示“配载线路”，为从出发部门到达开单目的站，我司走货的线路；
 *          配载类型为合大票或单独开单时，显示“配载航班”，为我司规则的空运走货的航班类型， 包括早班、中班、晚班； 14.
 *          配载部门:开单收货部门出发货配载专线； 15. 最终配载部门:货物到达的最终部门； 16. 预计出发时间:我司走货的预计出发时间，
 *          适用于运输类型为“精准卡航”及“精准城运”； 17. 预计派送/提货时间: 我司承诺客户的可提货或送货的时间，
 *          适用于运输类型为“精准卡航”及“精准城运”； 1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 录入收货部门 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR1； 2 录入单号 1. 系统校验单号的合法性；单号唯一在离线提交时，判断，
 *          除单号唯一性其他参考规则SR1 3 录入运输性质 1. 规则-请参考SUC-496录入运输信息-系统规则SR2、SR3； 4
 *          录入配载类型 1. 规则-请参考SUC-496录入运输信息-系统规则SR3、SR4、SR5、SR6； 5 录入提货方式 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR4、SR6、SR7、SR8； 6 录入目的站 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR9； 7 录入提货网点 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR5、SR8、SR9、SR10； 8 勾选是否上门接货 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR11； 2. 勾选上门接货，必须输入司机工号，司机工号为6为数字。参考规则SR3 9
 *          勾选是否集中接货 1. 规则-请参考SUC-496录入运输信息-系统规则SR11、SR12； 10 录入对外备注 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR13、SR14； 11 录入对内备注 1. 参考规则SR2 12 生成储运注意事项
 *          1. 规则-请参考SUC-496录入运输信息-系统规则SR14； 13 录入配载线路（配载航班） 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR15； 2. 参考SUC-547下载基础资料(PC) 14 录入配载部门 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR16； 15 录入最终配载部门 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR17； 16 生成预计出发时间 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR18； 17 生成预计派送/提货时间 1.
 *          规则-请参考SUC-496录入运输信息-系统规则SR19； 1.7 业务规则 序号 描述 SR1 1、单号为8-9位数字，
 *          不能输入重复单号。 SR2 1、对内备注仅公司内部人员可见， 输入字符长度不得超过500。 SR3 1、当勾选上门接货时，
 *          司机工号必填，且为6位数字。 SR4 参考suc-403生成运单SR1: 系统自动检测，如果客户端X天还没有更新，
 *          不能打开离线运单界面，并提示“请在间隔X天内，在线登录系统 录入收货客户信息（离线）SUC业务规则 1.1 相关业务用例
 *          BUC_FOSS_5.20.30_550 营业部离线开单 1.2 用例描述 营业员通过本用例录入收货客户信息。 1.3 用例条件
 *          条件类型 描述 引用系统用例 前置条件 1. 离线登录成功 后置条件 1.4 操作用户角色 操作用户 描述 营业员
 *          可查询、录入、修改收货客户信息 1.5 界面要求 1.5.1 表现方式 Web方式 1.5.2 界面原型 1.5.3 界面描述
 *          营业员点击运单开单，进入运单开单界面。 本界面为录入收货客户信息。 界面为信息录入界面包括：手机、电话、客户名称、
 *          客户编码、收货联系人（收货部门）、收货人地址； 1. 手机：收货人手机号码； 2. 电话：收货人电话号码，可以添加多个； 3.
 *          收货联系人：收货客户的客户姓名； 4. 收货人地址：收货客户的详细联系地址， 支持国家行政区域自动过滤； 1.6 操作步骤 序号
 *          基本步骤 相关数据 补充步骤 1 录入手机号码 1. 规则-请参见系统规则SR1； 2 录入电话号码 1. 规则-请参见系统规则SR1；
 *          3 录入收货联系人（收货部门） 规则-请参见系统规则SR3、SR4； 4 录入收货人地址 1. 规则-请参见系统规则SR2； 1.7
 *          业务规则 序号 描述 SR1 1. 收货客户手机号码及固定电话至少提供一个， 手机号码只能为数字并且为11位，固定电话号码只能为数字，
 *          且可添加多个；添加多个时，必须用“，”或“、”或“/”分开； 固定电话号码字段也可以录入手机号； SR2 1.
 *          客户详细地址必填至乡/镇， 且乡镇下一级内容不能为空； SR3 1. 当“运单开单”中的“开单提货方式”为“内部带货自提”时，
 *          “收货联系人”字段更改为“收货部门”； SR4 1.
 *          若为公司内部带货，则收货客户信息中的收货部门名称必须与OA系统中组织架构名称保持一致； SR5
 *          前提：收货人信息都已填充完毕，再进行手机号， 电话号码修改： ① 如在三月记录中，是否再次弹出框选择记录后覆盖原来的信息，
 *          不选择只修改手机号or电话号码，发货客户其他信息不置空 ② 如不在三月记录中，是否只修改手机号或电话，
 *          发货客户其他信息不置空------- 1、再次弹出框选择记录后覆盖原来的信息2、
 *          如果带出时是CRM客户，那么要删除客户编码和客户名称后才能够修改联系人信息，
 *          如果带出来不是，那么可以直接修改，那么修改电话号码和手机号，带出的信息都会覆盖 录入发货客户信息（离线）SUC业务规则 1.1
 *          相关业务用例 BUC_FOSS_5.60.05_520 确认承运信息 1.2 用例描述 营业员在网络不通时，通过本用例录入发货客户信息。
 *          1.3 用例条件 条件类型 描述 引用系统用例 前置条件 离线登录成功； 后置条件 1. 运单收银 2. 录入增值服务信息 1.4
 *          操作用户角色 操作用户 描述 营业员 可查询、录入、修改发货客户信息 1.5 界面要求 1.5.1 表现方式 Web方式 1.5.2
 *          界面原型 1.5.3 界面描述 营业员点击运单开单，进入运单开单界面。 本界面为录入发货客户信息。
 *          界面为信息录入界面：包括：手机、电话、 发货联系人（发货部门）、发货人地址； 1. 手机：发货人手机号码； 2.
 *          电话：发货人电话号码，可以添加多个； 3. 发货联系人（发货部门）：发货客户的客户姓名，
 *          当“运单开单”中的“开单提货方式”为“内部带货自提”时， “发货联系人”字段更改为“发货部门”； 4.
 *          发货人地址：发货客户的详细联系地址， 支持国家行政区域自动过滤； 1.6 操作步骤 序号 基本步骤 相关数据 补充步骤 1 录入手机号码
 *          1. 规则-请参见系统规则SR1； 2 录入电话号码 1. 规则-请参见系统规则SR1； 3 录入发货联系人（发货部门） 1.
 *          规则-请参见系统规则SR2、SR3； 4 录入发货人地址 1. 提供下拉框选择输入，系统自动过滤输入的行政区下一级行政级的字段； 2.
 *          规则-请参见系统规则SR4； 1.7 业务规则 序号 描述 SR1 1. 发货客户手机号码及固定电话至少提供一个，
 *          手机号码只能为数字并且为11位，固定电话号码只能为数字， 且可添加多个,
 *          添加多个时，必须用“，”或“、”或“/”分开；固定电话号码字段也可以录入手机号； SR2 1.
 *          当“运单开单”中的“开单提货方式”为“内部带货自提”时， “发货联系人”字段更改为“发货部门”； SR3 1.
 *          若为公司内部带货，则发货客户信息中的发货部门名称必须与OA系统中组织架构名称保持一致； SR4 1.
 *          客户详细地址必填至乡/镇，且乡镇下一级内容不能为空； SR5 1．焦点到联系人文本框后，下个焦点直接跳过地址分段输入框，到下个控件
 */
public class PdaWaybillService implements IPdaWaybillService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdaWaybillService.class);
	
	private static final double POINT_14 = 1.4;
	
	private static final double POINT_01 = 0.1;

	private static final double POINT_001 = 0.01;
	
	private static final double POINT_25 = 2.5;
	
	private static final int NUMBER_1337 = 1337;
	
	private static final int NUMBER_170 = 170;
	
	private static final int NUMBER_65 = 65;

	/**
	 * 异地调货调用综合接口查询客户，合同相关的信息
	 */
	private ICustomerCircleRelationNewService customerCircleRelationNewService;
	
	
	public ICustomerCircleRelationNewService getCustomerCircleRelationNewService() {
		return customerCircleRelationNewService;
	}

	public void setCustomerCircleRelationNewService(
			ICustomerCircleRelationNewService customerCircleRelationNewService) {
		this.customerCircleRelationNewService = customerCircleRelationNewService;
	}

	/**
	 * PDA标签服务
	 */
	private IPdaScanService pdaScanService;
	//OA服务参数
	private String oaError;
	public String getOaError() {
		return oaError;
	}

	public void setOaError(String oaError) {
		this.oaError = oaError;
	}
	@Resource
	IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}
	//CRM服务参数
	private String crmComplaintAddress;
	
	/**
	 * 运单信息服务
	 */
	@Resource
	private IWaybillService waybillService ;
	
	public String getCrmComplaintAddress() {
		return crmComplaintAddress;
	}

	public void setCrmComplaintAddress(String crmComplaintAddress) {
		this.crmComplaintAddress = crmComplaintAddress;
	}

	/**
	 * 异常货接口
	 * */
	@Resource
	private IQmsYchService qmsYchService;
	public void setQmsYchService(IQmsYchService qmsYchService) {
		this.qmsYchService = qmsYchService;
	}
	/**
	 * 交接单接口
	 * */
	@Resource
	private IHandOverBillService handOverBillService;
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
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
	 * 付款服务
	 */
	@Resource
	IRepaymentService repaymentService;
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	private static final String SEND = "送";
		/**
	 * 行政区域数据提供接口
	 */
	@Resource
	private IOnlineAddressDao onlineAddressDao;
		/**
	 * PDA标签服务
	 */
	private ILabeledGoodPDAService labeledGoodPDAService;
	/**
	 * CRM订单服务
	 */
	private ICrmOrderService crmOrderService;
	/**
	 * 运单服务
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 待处理运单主表
	 */
	private IWaybillPendingDao waybillPendingDao;

	/**
	 * 待处理运单费用
	 */
	private IWaybillCHDtlPendingDao waybillCHDtlPendingDao;

	/**
	 * 待处理运单打木架dao接口
	 */
	private IWoodenRequirePendingDao woodenRequirePendingDao;

	/**
	 * 待办的消息提醒
	 */
	private IPickupToDoMsgService pickupToDoMsgService;

	/**
	 * 车队接口
	 */
	private IMotorcadeService motorcadeService;

	/**
	 * 人员接口
	 */
	private IEmployeeService employeeService;

	/**
	 * 约车订单状态修改接口
	 */
	private IOrderTaskHandleService orderTaskHandleService;

	/**
	 * 约车订单Dao
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;

	/**
	 * 产品的服务
	 */
	private IProductService productService;
	
	/**
	 * 运单dao
	 */
	private IWaybillDao waybillDao;
	
	/**
	 * actualFreight服务
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * GUI查询走货路径相关服务接口 
	 */
	private IWaybillFreightRouteService waybillFreightRouteService; 
	/**
	 * 货签DAO
	 */
	private ILabeledGoodDao labeledGoodDao;
	
	/**
	 * 走货路径
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 营业部服务
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 组织服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * PDA开单服务
	 */
	private IPdaWaybillService pdaWaybillService;
	
	/**
	 * 组织服务
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	 // 查询客户信息服务
    private IQueryCustomerService queryCustomerService;
	
	private IBillingGroupTransFerService billingGroupTransFerService;
	
	/**
	 * 中专标签打印记录
	 */
	private IPrintLabelService printLabelService;
	
	private IActualFreightBIService actualFreightBIService;
	
	/**
	 * 快递运单服务类
	 */
	private IWaybillExpressService waybillExpressService;
	
	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	
	
	/**
	 * 业务锁定超时自动释放时间:15秒
	 */
	private static final int LOCK_TIMEOUT = 0;
	// 限保物品
	private IInsurGoodsDao pkpinsurGoodsDao;
	/**
	 * 业务互斥锁服务  提供业务互斥锁服务接口
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 电子运单服务类
	 */
	private IEWaybillService ewaybillService;
	
	public void setEwaybillService(IEWaybillService ewaybillService) {
		this.ewaybillService = ewaybillService;
	}
	
	
	private ISysConfigService pkpsysConfigService;
	
	private IUserService userService;
	
	/**
	 * 代理网点服务
	 */
	IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	/**
	 * 运单费用明细
	 */
	private IWaybillChargeDtlDao waybillChargeDtlDao;
	
	// 待处理运单费用折扣dao
    IWaybillDisDtlPendingDao waybillDisDtlPendingDao;
    
    /**
	 * 价格计算服务
	 */
	private IBillCaculateService billCaculateService;
	
	private ICombinateBillDao combinateBillDao;
	
	
	public ICombinateBillDao getCombinateBillDao() {
		return combinateBillDao;
	}

	public void setCombinateBillDao(ICombinateBillDao combinateBillDao) {
		this.combinateBillDao = combinateBillDao;
	}

	
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	
	
	public IPushTrackForCaiNiaoService getPushTrackForCaiNiaoService() {
		return pushTrackForCaiNiaoService;
	}

	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	private IEamDao eamDao;
	public void setEamDao(IEamDao eamDao) {
		this.eamDao = eamDao;
	}
	/**
	 * 系统参数配置项服务
	 */
	IConfigurationParamsService configurationParamsService;
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public IBillCaculateService getBillCaculateService() {
		return billCaculateService;
	}

	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}

	public IWaybillDisDtlPendingDao getWaybillDisDtlPendingDao() {
		return waybillDisDtlPendingDao;
	}

	public void setWaybillDisDtlPendingDao(
			IWaybillDisDtlPendingDao waybillDisDtlPendingDao) {
		this.waybillDisDtlPendingDao = waybillDisDtlPendingDao;
	}

	public void setWaybillChargeDtlDao(IWaybillChargeDtlDao waybillChargeDtlDao) {
		this.waybillChargeDtlDao = waybillChargeDtlDao;
	}
	
	/**
	 * 增值费用服务
	 */
	private IPriceEntryService priceEntryService;
	
	public void setPriceEntryService(IPriceEntryService priceEntryService) {
		this.priceEntryService = priceEntryService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public IVehicleAgencyDeptService getVehicleAgencyDeptService() {
		return vehicleAgencyDeptService;
	}

	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	public ISysConfigService getPkpsysConfigService() {
		return pkpsysConfigService;
	}

	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}

	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public IInsurGoodsDao getPkpinsurGoodsDao() {
		return pkpinsurGoodsDao;
	}

	public void setPkpinsurGoodsDao(IInsurGoodsDao pkpinsurGoodsDao) {
		this.pkpinsurGoodsDao = pkpinsurGoodsDao;
	}
	private IWaybillPictureDao waybillPictureDao;
	
	private ILabelPrintInfoService labelPrintInfoService;
	
	private IWaybillPendingService waybillPendingService;
	
	private IPrintInfoDao printInfoDao;
	/**
	 * 获取集中开单组
	 */
	private IFocusRecordManagementService focusRecordManagementService;
	
	public void setActualFreightBIService(
			IActualFreightBIService actualFreightBIService) {
		this.actualFreightBIService = actualFreightBIService;
	}
	/**
	 * 中专标签打印记录
	 */
	public void setPrintLabelService(IPrintLabelService printLabelService) {
		this.printLabelService = printLabelService;
	}
	public void setBillingGroupTransFerService(
			IBillingGroupTransFerService billingGroupTransFerService) {
		this.billingGroupTransFerService = billingGroupTransFerService;
	}

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * 运单库存服务
	 */
	private IWaybillStockService waybillStockService;
	
	public void setWaybillStockService(IWaybillStockService waybillStockService) {
		this.waybillStockService = waybillStockService;
	}

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * @param waybillFreightRouteService the waybillFreightRouteService to set
	 */
	public void setWaybillFreightRouteService(IWaybillFreightRouteService waybillFreightRouteService) {
		this.waybillFreightRouteService = waybillFreightRouteService;
	}

	/**
	 * @param labeledGoodDao the labeledGoodDao to set
	 */
	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	/**
	 * @param labeledGoodPDAService the labeledGoodPDAService to set
	 */
	public void setLabeledGoodPDAService(ILabeledGoodPDAService labeledGoodPDAService) {
		this.labeledGoodPDAService = labeledGoodPDAService;
	}

	/**
	 * @param crmOrderService the crmOrderService to set
	 */
	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @param waybillPendingDao the waybillPendingDao to set
	 */
	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}

	/**
	 * @param waybillCHDtlPendingDao the waybillCHDtlPendingDao to set
	 */
	public void setWaybillCHDtlPendingDao(IWaybillCHDtlPendingDao waybillCHDtlPendingDao) {
		this.waybillCHDtlPendingDao = waybillCHDtlPendingDao;
	}

	/**
	 * @param woodenRequirePendingDao the woodenRequirePendingDao to set
	 */
	public void setWoodenRequirePendingDao(IWoodenRequirePendingDao woodenRequirePendingDao) {
		this.woodenRequirePendingDao = woodenRequirePendingDao;
	}

	/**
	 * @param pickupToDoMsgService the pickupToDoMsgService to set
	 */
	public void setPickupToDoMsgService(IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}

	/**
	 * @param motorcadeService the motorcadeService to set
	 */
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 设置 约车订单状态修改接口
	 * 
	 * @param orderTaskHandleService
	 */
	public void setOrderTaskHandleService(IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}

	/**
	 * 设置 约车订单Dao
	 * 
	 * @param dispatchOrderEntityDao
	 */
	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	/**
	 * 设置 产品服务
	 * 
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	public IWaybillPictureDao getWaybillPictureDao() {
		return waybillPictureDao;
	}

	public void setWaybillPictureDao(IWaybillPictureDao waybillPictureDao) {
		this.waybillPictureDao = waybillPictureDao;
	}

	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}

	public void setPrintInfoDao(IPrintInfoDao printInfoDao) {
		this.printInfoDao = printInfoDao;
	}
	public void setLabelPrintInfoService(
			ILabelPrintInfoService labelPrintInfoService) {
		this.labelPrintInfoService = labelPrintInfoService;
	}
	/** 
	 * 更改单服务接口
	 * */
	private IWaybillRfcService waybillRfcService;    

    
    private IReModifyRouteDao reModifyRouteDao;
    
    
    public void setReModifyRouteDao(IReModifyRouteDao reModifyRouteDao) {
		this.reModifyRouteDao = reModifyRouteDao;
	}
    /**
	 * 库存接口
	 */
	private IStockService stockService;
	private IGpsPdaService gpsPdaService;
	public void setGpsPdaService(IGpsPdaService gpsPdaService) {
		this.gpsPdaService = gpsPdaService;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/**
	 * 内部员工折扣方案
	 */
	private IInempDiscountPlanService inempDiscountPlanService;
	
	public void setInempDiscountPlanService(
			IInempDiscountPlanService inempDiscountPlanService) {
		this.inempDiscountPlanService = inempDiscountPlanService;
	}
	/**
	 * <p>
	 * PDA上传货物属性
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午11:49:55
	 * @param goodAttr
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPDAService#submitGoodsAttr(com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto)
	 */
	public ResultDto submitGoodsAttr(PDAGoodsAttrDto goodAttr) {
		return waybillManagerService.updateGoodsAttr(goodAttr);
	}

	
	
	
	/**
	 * <p>
	 * 优惠券验证/使用
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午11:49:59
	 * @param couponInfo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPDAService#validateCoupon(com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto)
	 */
	public CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo) {
		return crmOrderService.validateCoupon(couponInfo);
	}

	/**
	 * <p>
	 * 反使用优惠券
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午11:50:03
	 * @param couponNumber
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPDAService#reverseCouponState(java.lang.String)
	 */
	public CouponReverseResultDto reverseCouponState(String couponNumber) {
		boolean retValue = crmOrderService.effectCouponState(couponNumber);
		CouponReverseResultDto res = new CouponReverseResultDto();
		res.setSuccess(retValue);
		res.setMessage("");
		return res;
	}

	/**
	 * <p>
	 * PDA上传标签打印信息)
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午11:50:07
	 * @param labeledGoodPDA
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IPDAService#uploadLabeledGood(com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodPDADto)
	 */
	public ResultDto uploadLabeledGood(LabeledGoodPDADto labeledGoodPDA) {
		// 1. 修改运单提交接口的实现。限制接口中传递的件数>0,且不能为空。根据开单件数，生成流水号。
		// 2. 弱化“上传标签信息接口”，暂时把实现的代码全部删除，在PDA打印标签时，不再插入标签信息。未来会和PDA配合，取消该接口。
		 if (labeledGoodPDA == null) {
		 throw new IllegalArgumentException("标签信息不能为空！");
		 }
		 if (StringUtils.isEmpty(labeledGoodPDA.getWaybillNo())) {
		 throw new IllegalArgumentException("运单号不能为空！");
		 }
		 if (StringUtils.isEmpty(labeledGoodPDA.getSerialNo())) {
		 throw new IllegalArgumentException("标签流水号不能为空！");
		 }
		 if (labeledGoodPDA.getPrintTime() == null) {
		 throw new IllegalArgumentException("打印时间不能为空！");
		 }
		 if (StringUtils.isEmpty(labeledGoodPDA.getPrintPerson())) {
		 throw new IllegalArgumentException("打印人编号不能为空！");
		 }
		 String empName = employeeService.queryEmpNameByEmpCode(labeledGoodPDA.getPrintPerson());
		 if(StringUtils.isEmpty(empName)){
			 throw new IllegalArgumentException("未查询到编码为:"+labeledGoodPDA.getPrintPerson()+"人的信息");
		 }
		 //赋值给对应的对象
		PrintLabelEntity printLabelEntity = new PrintLabelEntity();
		printLabelEntity.setPrintTime(labeledGoodPDA.getPrintTime());
		printLabelEntity.setPrintUserCode(labeledGoodPDA.getPrintPerson());
		printLabelEntity.setPrintUserName(empName);
		printLabelEntity.setWaybillNo(labeledGoodPDA.getWaybillNo());
		printLabelEntity.setSerialNo(labeledGoodPDA.getSerialNo());
		printLabelEntity.setId(UUIDUtils.getUUID());

		//新增打印记录
		int temp = printLabelService.addPrintLabel(printLabelEntity);
		 ResultDto res = new ResultDto();
		 if(temp==0){
		 res.setCode("0");
		 res.setMsg("上传标签信息失败");
		 throw new BusinessException("0","上传标签信息失败",printLabelEntity);
		 }else{
		 res.setCode("1");
		 res.setMsg("");
		 }
		 return res;
	}

	/**
	 * <p>
	 * PDA运单提交
	 * </p>
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-12 下午4:54:38
	 * @param waybillPendingDto
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService#submitWaybillPending(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto)
	 */
	public ResultDto submitWaybillByPDA(WaybillPdaDto waybillPdaDto) {
		if (waybillPdaDto == null) {
			throw new PdaInterfaceException("运单不能为空！");
		}
		if (StringUtils.isEmpty(waybillPdaDto.getWaybillNo())) {
			throw new PdaInterfaceException("运单号不能为空！");
		} else {
			if (waybillManagerService.isWayBillExsits(waybillPdaDto.getWaybillNo()) || waybillManagerService.isWayBillPendingExsits(waybillPdaDto.getWaybillNo())) {
				throw new PdaInterfaceException("此运单号已经开单或暂存！");
			}
			WaybillPictureEntity entity = new WaybillPictureEntity();
			entity.setWaybillNo(waybillPdaDto.getWaybillNo());
			entity.setActive(FossConstants.ACTIVE);
			WaybillPictureEntity picEntity =waybillPendingService.queryWaybillPictureByEntity(entity);
			if(picEntity!=null){
				throw new PdaInterfaceException("此运单号已经被图片开单使用，请更换运单号");
			}
			
		}
		if (!StringUtils.isEmpty(waybillPdaDto.getOrderNo())) {
			if (waybillManagerService.isWayBillExsitsByOrderNo(waybillPdaDto.getOrderNo()) || waybillManagerService.isWayBillPendingExsitsByOrderNo(waybillPdaDto.getOrderNo())) {
				throw new PdaInterfaceException("输入的订单号已经开单或暂存！");
			}
			WaybillPictureEntity entity = new WaybillPictureEntity();
			entity.setOrderNo(waybillPdaDto.getOrderNo());
			entity.setActive(FossConstants.ACTIVE);
			WaybillPictureEntity picEntity =waybillPendingService.queryWaybillPictureByEntity(entity);
			if(picEntity!=null){
				throw new PdaInterfaceException("此订单号已经被图片开单使用，请更换订单号");
			}
		}
		
		if (StringUtils.isEmpty(waybillPdaDto.getBillOrgCode())) {
			throw new PdaInterfaceException("司机所在车队部门不能为空！");
		}
		if (waybillPdaDto.getCreateTime() == null) {
			throw new PdaInterfaceException("创建时间不能为空！");
		}
		if (StringUtils.isEmpty(waybillPdaDto.getCreateUserCode())) {
			throw new PdaInterfaceException("创建人编号不能为空！");
		}
		if (waybillPdaDto.getBillStart() == null) {
			throw new PdaInterfaceException("开单时间不能为空！");
		}
		if (waybillPdaDto.getGoodsQty() == null || Integer.valueOf(0).equals(waybillPdaDto.getGoodsQty())) {
			throw new PdaInterfaceException("开单件数不能为空！");
		}
		// 查找对应的顶级车队
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(waybillPdaDto.getBillOrgCode());
		if(topFleet == null){
			throw new PdaInterfaceException("通过司机所在车队部门编码未找到对应的顶级车队");
		}
		// 司机所在车队部门
		MotorcadeEntity motorcadeEntity = motorcadeService.queryMotorcadeByCode(topFleet.getCode());
		// 车队对应的集中开单组编码
		String billOrgCode;
		if (motorcadeEntity != null) {
			if (StringUtils.isNotEmpty(motorcadeEntity.getServeBillTerm())) {
				billOrgCode = motorcadeEntity.getServeBillTerm();
			} else {
				throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
			}
		} else {
			throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表中找到有效记录");
		}
		// 正常提交运单
		pdaWaybillService.submitWaybill(waybillPdaDto, billOrgCode);

		try {
			// 添加t_srv_waybill，t_srv_actual_freight和t_srv_labeled_good记录，方便后续走货
			pdaWaybillService.addForTransfer(waybillPdaDto, billOrgCode);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillPdaDto.getProductCode(), waybillPdaDto.getBillStart())){
			if(StringUtils.isNotBlank(waybillPdaDto.getLicensePlateNum())){
				//车牌号不为空是调用GPS接口
				gpsPdaService.pDaPickupToGps(waybillPdaDto);
			}
		}
		return new ResultDto();
	}
	private void addSynTrack(Object object){
		if(object==null){
			return;
		}
		WaybillPdaDto wayDto =null;
		WaybillExpressPdaDto expDto =null;
		String waybillNo=null;
		String orderNo =null;
		Date operateTime =null;
		String orgCode =null;
		String productCode = null;
		String targetOrgCode = null ;
		if(object instanceof WaybillPdaDto){
			wayDto =(WaybillPdaDto) object;
			waybillNo=wayDto.getWaybillNo();
			orderNo =wayDto.getOrderNo();
			operateTime= wayDto.getBillStart();
			orgCode =wayDto.getBillOrgCode();
			productCode = wayDto.getProductCode();
			targetOrgCode = wayDto.getTargetOrgCode() ;
		}
		
		if(object instanceof WaybillExpressPdaDto){
			expDto =(WaybillExpressPdaDto) object;
			waybillNo=expDto.getWaybillNo();
			orderNo =expDto.getOrderNo();
			operateTime= expDto.getBillStart();
			orgCode =expDto.getBillOrgCode();
			productCode = expDto.getProductCode();
			targetOrgCode = expDto.getTargetOrgCode() ;
		}
	    
		SynTrackingEntity track = new SynTrackingEntity();
		track.setId(UUIDUtils.getUUID());
		//运单
		track.setWayBillNo(waybillNo);
		if(!StringUtils.isEmpty(orderNo)){
			DispatchOrderEntity order = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
			if(order != null){
				//订单
				track.setOrderNo(order.getChannelNumber());
			}
		}
		//发生时间
		track.setOperateTime(operateTime);
		track.setCreateDate(new Date());
		//跟踪信息描述
		//entity.setTrackInfo("已进行返货开单，返货单号【"+waybill.getOrderNo()+"】");
		SaleDepartmentEntity dept =saleDepartmentService.querySaleDepartmentByCode(orgCode);
		if(dept!=null){
			//跟踪信息描述
			track.setTrackInfo("揽收成功，【"+dept.getName()+"】库存中");
			//开单部门名称
			track.setOrgName(dept.getName());
		}
		//开单部门所在城市
		OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		if(org != null){
			track.setOperateCity(org.getCityName());
			
			
		}
		//产品类型
		track.setProductCode(productCode);
		//站点类型
		track.setOrgType(String.valueOf(1));
		//开单部门编码
		track.setOrgCode(orgCode);
		//目的部门名称
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(targetOrgCode);
		if (orgEntity!=null && StringUtils.isNotBlank(orgEntity.getName())) {
			track.setDestinationDeptName(orgEntity.getName());
		}else{
			//外发网点
			OuterBranchEntity outerBranchEntity = waybillService.queryAgencyBranchInfo(targetOrgCode);
			if(outerBranchEntity!=null && StringUtils.isNotBlank(outerBranchEntity.getAgentDeptName())){
				track.setDestinationDeptName(outerBranchEntity.getAgentDeptName());
			}
		}
		//事件
		track.setEventType(String.valueOf("GOT"));
		pushTrackForCaiNiaoService.addSynLpsTrack(track);
		
		//是否返货开单(是否返货业务 Y/N)
		if(expDto!=null 
				 &&
		  FossConstants.ACTIVE.equals(expDto.getIsReturnGoods())
		         &&
		         expDto.getOldWayBill()!=null
		  ){
			WaybillEntity waybillexp = waybillDao.queryWaybillByNo(expDto.getOldWayBill());
			if(null == waybillexp){
				return;
			}
			
			SynTrackingEntity entity = new SynTrackingEntity();
			entity.setId(UUIDUtils.getUUID());
			//运单
			entity.setWayBillNo(waybillexp.getWaybillNo());
			if(waybillexp.getOrderNo()!=null){
				DispatchOrderEntity orderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(waybillexp.getOrderNo());
				if(orderEntity != null){
					//订单
					entity.setOrderNo(orderEntity.getChannelNumber());
				}
			}
			//原单传发货开单时间
			entity.setOperateTime(operateTime);
			entity.setCreateDate(new Date());
			//跟踪信息描述
			entity.setTrackInfo("已进行返货开单，返货单号【"+waybillNo+"】");
			//开单部门所在城市
			OrgAdministrativeInfoEntity orga =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillexp.getCustomerPickupOrgCode());
			if(orga != null){
				entity.setOperateCity(orga.getCityName());
			}
			//产品类型
			entity.setProductCode(waybillexp.getProductCode());
			//站点类型
			entity.setOrgType(String.valueOf(1));
			//开单部门编码
			entity.setOrgCode(waybillexp.getCustomerPickupOrgCode());
			//开单部门名称
			entity.setOrgName(waybillexp.getCustomerPickupOrgName());
			//目的
			entity.setDestinationDeptName(waybillexp.getReceiveOrgName());
			//事件
			entity.setEventType(String.valueOf("FAILED"));
			pushTrackForCaiNiaoService.addSynTrack(entity);
			
		}
	    
	}

	/**
	 * 
	 * PDA运单提交
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-23 下午4:06:18
	 */
	@Transactional
	public void submitWaybill(WaybillPdaDto waybillPdaDto, String billOrgCode) {
		try {
			/**
			 * 开单基本信息，操作WaybillPendingEntity
			 */
			WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
			waybillPendingEntity.setActive(FossConstants.ACTIVE);
			waybillPendingEntity.setCreateTime(waybillPdaDto.getBillStart());
			waybillPendingEntity.setCreateUserCode(waybillPdaDto.getBillUserNo());
			waybillPendingEntity.setFibreNum(waybillPdaDto.getFibre());
			waybillPendingEntity.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
			waybillPendingEntity.setGoodsTypeCode(waybillPdaDto.getGoodsTypeCode());
			waybillPendingEntity.setGoodsVolumeTotal(waybillPdaDto.getGoodsVolumeTotal());
			waybillPendingEntity.setGoodsWeightTotal(waybillPdaDto.getGoodsWeightTotal());
			waybillPendingEntity.setLicensePlateNum(waybillPdaDto.getLicensePlateNum());
			waybillPendingEntity.setMembraneNum(waybillPdaDto.getMembrane());
			waybillPendingEntity.setOrderNo(waybillPdaDto.getOrderNo());
			// 根据订单号查询订单来源
			if (StringUtils.isNotEmpty(waybillPdaDto.getOrderNo())) {
				DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillPdaDto.getOrderNo());
				waybillPendingEntity.setOrderChannel(dispatchOrderEntity != null ? dispatchOrderEntity.getOrderSource() : null);
				//提货地址备注
				if(dispatchOrderEntity != null && StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
					waybillPendingEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
				}
				//发货地址备注
				if(dispatchOrderEntity != null && StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
					waybillPendingEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
				}
			}
			waybillPendingEntity.setOtherPackage(waybillPdaDto.getOtherPackageType());
			waybillPendingEntity.setPaidMethod(waybillPdaDto.getPaidMethod());
			waybillPendingEntity.setPaperNum(waybillPdaDto.getPaper());
			waybillPendingEntity.setProductCode(waybillPdaDto.getProductCode());
			waybillPendingEntity.setSalverNum(waybillPdaDto.getSalver());
			waybillPendingEntity.setReceiveOrgCode(waybillPdaDto.getStartOrg());
			waybillPendingEntity.setReceiveMethod(waybillPdaDto.getReceiveMethod());
			waybillPendingEntity.setCustomerPickupOrgCode(waybillPdaDto.getTargetOrgCode());
			waybillPendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
			waybillPendingEntity.setWoodNum(waybillPdaDto.getWood());
			waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);

			/**
			 * 车队的集中开单部门编码
			 */
			waybillPendingEntity.setCreateOrgCode(billOrgCode);

			/**
			 * 优惠信息
			 */
			waybillPendingEntity.setPromotionsFee(waybillPdaDto.getDiscountAmount());
			waybillPendingEntity.setPromotionsCode(waybillPdaDto.getDiscountNo());
			/**
			 * pda应收运费对应waybillPendingEntity的总费用totalFee
			 */
			waybillPendingEntity.setTotalFee(waybillPdaDto.getAmount());
			/**
			 * pda实收运费对应waybillPendingEntity的预付费用PrePayAmount
			 */
			waybillPendingEntity.setPrePayAmount(waybillPdaDto.geActualFee());
			/**
			 * 开单人工号
			 */
			waybillPendingEntity.setDriverCode(waybillPdaDto.getBillUserNo());
			/**
			 * 开单时间
			 */
			waybillPendingEntity.setBillTime(waybillPdaDto.getBillStart());
			/**
			 * 退款类型
			 */
			waybillPendingEntity.setRefundType(waybillPdaDto.getRefundType());
			/**
			 * 返单类型
			 */
			waybillPendingEntity.setReturnBillType(waybillPdaDto.getReturnBillType());
			/**
			 * 是否代打木架(否"N"或是"Y"),如需代打木架，操作WoodenRequirePendingEntity
			 */
			WoodenRequirePendingEntity woodenRequirePendingEntity = new WoodenRequirePendingEntity();
			if (FossConstants.YES.equals(waybillPdaDto.getIsWood())) {
				woodenRequirePendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
				woodenRequirePendingEntity.setStandGoodsSize(waybillPdaDto.getWoodSize());
				woodenRequirePendingEntity.setStandGoodsVolume(waybillPdaDto.getWoodVolume());
				woodenRequirePendingEntity.setBoxGoodsSize(waybillPdaDto.getWoodBoxSize());
				woodenRequirePendingEntity.setBoxGoodsVolume(waybillPdaDto.getWoodBoxVolume());
				woodenRequirePendingDao.insertSelective(woodenRequirePendingEntity);
			}

			/**
			 * 增值服务,操作WaybillCHDtlPendingEntity
			 */
			List<ValueAddServiceDto> valueAddServiceDtoList = waybillPdaDto.getValueAddServiceDtoList();
			if (valueAddServiceDtoList != null) {
				for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
					WaybillCHDtlPendingEntity waybillCHDtlPendingEntity = new WaybillCHDtlPendingEntity();
					waybillCHDtlPendingEntity.setActive(FossConstants.YES);
					waybillCHDtlPendingEntity.setAmount(valueAddServiceDto.getAmount());
					waybillCHDtlPendingEntity.setBillTime(waybillPdaDto.getBillStart());
					waybillCHDtlPendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
					waybillCHDtlPendingEntity.setPricingEntryCode(valueAddServiceDto.getCode());
					waybillCHDtlPendingEntity.setCreateTime(waybillPdaDto.getCreateTime());
					waybillCHDtlPendingEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);					
					waybillCHDtlPendingDao.addWaybillCHDtlPendingSelective(waybillCHDtlPendingEntity);
					if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
						// 保价费设置
						//waybillPendingEntity.setInsuranceFee(valueAddServiceDto.getAmount());
						waybillPendingEntity.setInsuranceAmount(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
						// 代收货款设置
						waybillPendingEntity.setCodAmount((valueAddServiceDto.getAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
						waybillPendingEntity.setRefundType(valueAddServiceDto.getSubType());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
						// 运费
						waybillPendingEntity.setTransportFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
						// 接货费
						waybillPendingEntity.setPickupFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
						// 送货费
						waybillPendingEntity.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
					} 
					/*
					 * else if
					 * (PricingConstants.PriceEntityConstants.PRICING_CODE_QS
					 * .equals(valueAddServiceDto.getCode())){
					 * waybillPendingEntity
					 * .setDeliveryGoodsFee(valueAddServiceDto.getAmount()); //
					 * 签收单返单未确定默认值，暂不设置 }
					 */
				}
			}
			waybillPendingEntity.setIsExpress(FossConstants.NO);
			
			/**
			 * 收货人省市区 DMANA-4296
			 */
			//收货人省 
			waybillPendingEntity.setReceiveCustomerProvCode(waybillPdaDto.getReceiveCustomerProvCode());
			//收货人市
			waybillPendingEntity.setReceiveCustomerCityCode(waybillPdaDto.getReceiveCustomerCityCode());
			//收货人区
			waybillPendingEntity.setReceiveCustomerDistCode(waybillPdaDto.getReceiveCustomerDistCode());
						
			waybillPendingDao.insertSelective(waybillPendingEntity);
			// 添加货签信息
			addLabelGoodPda(waybillPendingEntity);
			//添加CRM营销活动信息
			addCrmActiveInfo(waybillPdaDto);
			// 订单号是否空
			if (!StringUtil.isEmpty(waybillPdaDto.getOrderNo())) {
				// PDA开单后修改约车订单状态
				OrderHandleDto orderHandleDto = new OrderHandleDto();
				// 订单号
				orderHandleDto.setOrderNo(waybillPdaDto.getOrderNo());
				// 订单状态--已开单
				orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_WAYBILL);
				// 操作人编码
				orderHandleDto.setOperatorCode(waybillPdaDto.getBillUserNo());
				// 操作时间
				orderHandleDto.setOperateTime(waybillPdaDto.getBillStart());
				// 操作部门编码
				orderHandleDto.setOperateOrgCode(waybillPdaDto.getBillOrgCode());
				// 更新订单状态
				orderTaskHandleService.orderWaybill(orderHandleDto);
			}			
		} catch (BusinessException e) {
			LOGGER.info(e.getMessage(),e);
		}
		// pda补录消息提醒,通知司机所在车队的集中开单组
		UserEntity userEntity = new UserEntity();
		if (waybillPdaDto.getBillUserNo() != null) {
			userEntity.setEmpCode(waybillPdaDto.getBillUserNo());
			EmployeeEntity employEntity = null;
			if (waybillPdaDto.getBillUserNo() != null) {
				employEntity = employeeService.queryEmployeeByEmpCode(waybillPdaDto.getBillUserNo());
			} else {
				throw new PdaInterfaceException("司机编号为空！");
			}

			if (employEntity != null) {
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmployee(employEntity);
			} else {
				throw new PdaInterfaceException("查询不到该司机！");
			}

		}
		UserContext.setCurrentUser(userEntity);

		pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, null, billOrgCode,
				waybillPdaDto.getWaybillNo(), waybillPdaDto.getWaybillNo());
	}
	
	/**
	 * 添加CRM营销活动信息
	 * @创建时间 2014-5-8 下午8:26:56   
	 * @创建人： WangQianJin
	 */
	private void addCrmActiveInfo(WaybillPdaDto waybillPdaDto){
		if(waybillPdaDto.getActiveDto()!=null && StringUtils.isNotEmpty(waybillPdaDto.getActiveDto().getCode())){
			WaybillDisDtlEntity record=new WaybillDisDtlEntity();
			record.setPricingEntryCode(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
			record.setType(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
			record.setWaybillNo(waybillPdaDto.getWaybillNo());
			record.setWaybillChargeDetailId(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
			record.setActiveCode(waybillPdaDto.getActiveDto().getCode());
			record.setActiveName(waybillPdaDto.getActiveDto().getName());
			waybillManagerService.insertSelective(record);
		}		
	}
	
	/**
	 * 添加CRM营销活动信息(快递)
	 * @创建时间 2014-5-8 下午8:26:56   
	 * @创建人： WangQianJin
	 */
	private void addCrmActiveInfoExpress(WaybillExpressPdaDto waybillPdaDto){
		if(waybillPdaDto.getActiveDto()!=null && StringUtils.isNotEmpty(waybillPdaDto.getActiveDto().getCode())){
			WaybillDisDtlEntity record=new WaybillDisDtlEntity();
			record.setPricingEntryCode(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
			record.setType(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
			record.setWaybillNo(waybillPdaDto.getWaybillNo());
			record.setWaybillChargeDetailId(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
			record.setActiveCode(waybillPdaDto.getActiveDto().getCode());
			record.setActiveName(waybillPdaDto.getActiveDto().getName());
			waybillManagerService.insertSelective(record);
		}		
	}

	/**
	 * 添加货签信息
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-17 下午2:33:14
	 */
	private void addLabelGoodPda(WaybillPendingEntity waybillPendingEntity) {
		// 循环添加货签信息
		for (int i = 1; i <= waybillPendingEntity.getGoodsQtyTotal(); i++) {
			// new货签对象
			LabeledGoodPDAEntity labeledGood = new LabeledGoodPDAEntity();
			// id
			labeledGood.setWaybillPDAId(waybillPendingEntity.getId());
			// 运单号
			labeledGood.setWaybillNo(waybillPendingEntity.getWaybillNo());
			// 流水号
			labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
			// 开单时间
			labeledGood.setBillTime(waybillPendingEntity.getCreateTime());
			// 有效
			labeledGood.setActive(FossConstants.ACTIVE);
			// 生效时间
			labeledGood.setCreateTime(waybillPendingEntity.getCreateTime());
			// 失效时间
			labeledGood.setModifyTime(waybillPendingEntity.getCreateTime());
			// 创建人编码
			labeledGood.setCreateUserCode(waybillPendingEntity.getCreateUserCode());
			// 插入
			labeledGoodPDAService.insertSelective(labeledGood);
		}
	}

	/**
	 * 添加t_srv_waybill，t_srv_actual_freight和t_srv_labeled_good记录，方便后续走货
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-22 上午11:07:57
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addForTransfer(WaybillPdaDto waybillPdaDto, String billOrgCode) {
		// 添加运单
		WaybillEntity waybillEntity = addWaybill(waybillPdaDto, billOrgCode);
		// 添加ActualFreight
		addActualFreight(waybillEntity);
		// 添加货件
		addLabeledGood(waybillPdaDto);
		//推送货物轨迹
		addSynTrack(waybillPdaDto); 
	}

	private WaybillEntity addWaybill(WaybillPdaDto waybillPdaDto, String billOrgCode) {
		// 查询产品
		ProductEntity product = productService.getProductByCache(waybillPdaDto.getProductCode(), new Date());
		if (product == null) {
			LOGGER.info("根据产品编号查询不到对应的产品信息，编号为：" + waybillPdaDto.getProductCode());
			throw new PdaInterfaceException("根据产品编号查询不到对应的产品信息，编号为：" + waybillPdaDto.getProductCode());
		}
		OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(true, waybillPdaDto.getStartOrg(), waybillPdaDto.getTargetOrgCode(), waybillPdaDto.getProductCode());
		if(orgInfoDto == null) {
			LOGGER.info("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPdaDto.getStartOrg() + "到达部门：" + waybillPdaDto.getTargetOrgCode());
			throw new PdaInterfaceException("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPdaDto.getStartOrg() + "到达部门：" + waybillPdaDto.getTargetOrgCode());
		}
		// 对象传递
		TransportPathEntity transportPath = new TransportPathEntity();
		//运单号
		transportPath.setWaybillNo(waybillPdaDto.getWaybillNo());
		//开单时间
		transportPath.setBillingTime(waybillPdaDto.getBillStart());
		//开单组织 
		transportPath.setBillingOrgCode(waybillPdaDto.getBillOrgCode());
		
		//当前库存部门编码
		String currentOrgCode = "";
		
		//判断是否异地开单
		WaybillPictureEntity waybillPic = new WaybillPictureEntity();
		waybillPic.setActive(FossConstants.YES);
		waybillPic.setWaybillNo(waybillPdaDto.getWaybillNo());
		waybillPic = waybillPendingService.queryWaybillPictureByEntity(waybillPic);
		//根据集中接送货部门编码查询其对应的外场编码
		BillingGroupTransFerEntity entity = null;
		if(waybillPic!=null && FossConstants.NO.equals(waybillPic.getLocal())
				&& StringUtil.isNotEmpty(waybillPic.getLocalBillGroupCode())){
			//根据集中接送货部门编码查询其对应的外场编码
			 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(waybillPic.getLocalBillGroupCode());
		}else{
			//根据集中接送货部门编码查询其对应的外场编码
			 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(billOrgCode);
		}
		//根据集中接送货部门编码查询其对应的外场编码
		//BillingGroupTransFerEntity entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(billOrgCode);
		//判断对象是否为空
		if(null != entity){
			//外场编码
			currentOrgCode = entity.getTransFerCode();
			//非空判断
			if(StringUtil.isEmpty(currentOrgCode)){
				//若外场编码为空，则抛出异常信息（"集中接送货部门对应的外场编码为空！"）
				throw new PdaInterfaceException("集中接送货部门+"+waybillPdaDto.getBillOrgCode()+"+对应的外场编码为空！");
			}
		}else{
			//若没有查询到外场编码，则抛出异常信息
			throw new PdaInterfaceException(waybillPdaDto.getBillOrgCode()+"没有查询到外场编码");
		}

		//当前部门编码
		transportPath.setCurrentOrgCode(currentOrgCode);
		//最终到达部门编号
		transportPath.setDestOrgCode(waybillPdaDto.getTargetOrgCode());
		//总重量
		transportPath.setTotalWeight(waybillPdaDto.getGoodsWeightTotal());
		//总体积
		transportPath.setTotalVolume(waybillPdaDto.getGoodsVolumeTotal());
		//总件数
		transportPath.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
		//运输类型
		transportPath.setTransportModel(waybillPdaDto.getProductCode());
		// 生成走货路径
		calculateTransportPathService.createTransportPath(transportPath);
		WaybillEntity waybill = new WaybillEntity();
		// 主键id
		waybill.setId(UUIDUtils.getUUID());
		// 运单号
		waybill.setWaybillNo(waybillPdaDto.getWaybillNo());
		// 收货联系人
		waybill.setReceiveCustomerContact(WaybillConstants.TO_MAKEUP);
		// 送货联系人
		waybill.setDeliveryCustomerContact(WaybillConstants.TO_MAKEUP);
		// 收货部门
		waybill.setReceiveOrgCode(waybillPdaDto.getStartOrg());
		// 产品编码
		waybill.setProductCode(waybillPdaDto.getProductCode());
		// 产品id
		waybill.setProductId(product.getId());
		// 提货方式
		waybill.setReceiveMethod(waybillPdaDto.getReceiveMethod());
		// 是否上门接货
		waybill.setPickupToDoor(FossConstants.YES);
		// 是否集中接货
		waybill.setPickupCentralized(FossConstants.YES);
		// 始发配载部门
		waybill.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());
		// 始发配载部门名称
		waybill.setLoadOrgName(orgInfoDto.getFirstLoadOrgName());
		// 最终配载部门
		waybill.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());
		// 最终配载部门名称
		waybill.setLastLoadOrgName(orgInfoDto.getLastLoadOrgName());
		// 是否大车直送
		waybill.setCarDirectDelivery(FossConstants.NO);
		// 货物名称
		waybill.setGoodsName(WaybillConstants.TO_MAKEUP);
		// 货物体积
		waybill.setGoodsVolumeTotal(waybillPdaDto.getGoodsVolumeTotal());
		// 货物重量
		waybill.setGoodsWeightTotal(waybillPdaDto.getGoodsWeightTotal());
		// 车牌号
		waybill.setLicensePlateNum(waybillPdaDto.getLicensePlateNum());
		// 膜包装
		waybill.setMembraneNum(waybillPdaDto.getMembrane());
		// 订单号
		waybill.setOrderNo(waybillPdaDto.getOrderNo());
		// 件数
		waybill.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
		// 货物类型
		waybill.setGoodsTypeCode(waybillPdaDto.getGoodsTypeCode());
		// 纤包装
		waybill.setFibreNum(waybillPdaDto.getFibre());
		// 根据订单号查询订单来源
		if (StringUtils.isNotEmpty(waybillPdaDto.getOrderNo())) {
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillPdaDto.getOrderNo());
			waybill.setOrderChannel(dispatchOrderEntity != null ? dispatchOrderEntity.getOrderSource() : null);
		}
		waybill.setOtherPackage(waybillPdaDto.getOtherPackageType());
		waybill.setPaidMethod(waybillPdaDto.getPaidMethod());
		waybill.setPaperNum(waybillPdaDto.getPaper());
		waybill.setCustomerPickupOrgCode(waybillPdaDto.getTargetOrgCode());
		waybill.setWoodNum(waybillPdaDto.getWood());
		/**
		 * 优惠信息
		 */
		waybill.setPromotionsFee(waybillPdaDto.getDiscountAmount());
		waybill.setPromotionsCode(waybillPdaDto.getDiscountNo());
		/**
		 * pda应收运费对应waybill的总费用totalFee
		 */
		waybill.setTotalFee(waybillPdaDto.getAmount());
		/**
		 * pda实收运费对应waybill的预付费用PrePayAmount
		 */
		waybill.setPrePayAmount(waybillPdaDto.geActualFee());
		/**
		 * 开单人工号
		 */
		waybill.setDriverCode(waybillPdaDto.getBillUserNo());
		// 是否贵重物品
		waybill.setPreciousGoods(FossConstants.NO);
		// 是否异型货物
		waybill.setSpecialShapedGoods(FossConstants.NO);
		// 预付费保密
		waybill.setSecretPrepaid(FossConstants.NO);
		// 装卸费
		waybill.setServiceFee(BigDecimal.ZERO);
		// 有效
		waybill.setActive(FossConstants.ACTIVE);
		// 是否禁行
		waybill.setForbiddenLine(FossConstants.NO);
		// 总费用
		waybill.setTotalFee(waybillPdaDto.getAmount());
		// 创建时间
		waybill.setCreateTime(waybillPdaDto.getBillStart());
		// 修改时间
		waybill.setModifyTime(waybillPdaDto.getBillStart());
		// 开单时间
		waybill.setBillTime(waybillPdaDto.getBillStart());
		// 创建人编码
		waybill.setCreateUserCode(waybillPdaDto.getBillUserNo());
		// 创建部门编码
		waybill.setCreateOrgCode(billOrgCode);
		// 币种
		waybill.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 是否整车
		waybill.setIsWholeVehicle(FossConstants.NO);
		// 整车约车报价
		waybill.setWholeVehicleAppfee(BigDecimal.ZERO);
		// 计费重量
		waybill.setBillWeight(BigDecimal.ZERO);
		/**
		 * 退款类型
		 */
		waybill.setRefundType(waybillPdaDto.getRefundType());
		/**
		 * 返单类型
		 */
		waybill.setReturnBillType(waybillPdaDto.getReturnBillType());
		// 增值服务
		List<ValueAddServiceDto> valueAddServiceDtoList = waybillPdaDto.getValueAddServiceDtoList();
		// 接货费默认值
		waybill.setPickupFee(BigDecimal.ZERO);
		if (valueAddServiceDtoList != null) {
			for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
					// 保价费设置
					waybill.setInsuranceFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
					// 代收货款设置
					waybill.setCodFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
					// 运费
					waybill.setTransportFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
					// 接货费
					waybill.setPickupFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
					// 送货费
					waybill.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
				} 
			}
		}
		// 预计到达时间
		waybill.setPreArriveTime(waybillPdaDto.getCreateTime());
		// 补录状态
		waybill.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		waybill.setIsExpress(FossConstants.NO);
		/**
		 * 收货人省市区 DMANA-4296
		 */
		//收货人省 
		waybill.setReceiveCustomerProvCode(waybillPdaDto.getReceiveCustomerProvCode());
		//收货人市
		waybill.setReceiveCustomerCityCode(waybillPdaDto.getReceiveCustomerCityCode());
		//收货人区
		waybill.setReceiveCustomerDistCode(waybillPdaDto.getReceiveCustomerDistCode());
				
		waybillDao.addWaybillEntity(waybill);
		return waybill;
	}

	/**
	 * 
	 * 增加ActualFreight
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-23 下午3:31:05
	 */
	private void addActualFreight(WaybillEntity waybillEntity) {
		ActualFreightEntity actualFreight = new ActualFreightEntity();
		//添加地址备注
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillEntity.getOrderNo());
		if(dispatchOrderEntity != null){
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
				dispatchOrderEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
			}
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
				dispatchOrderEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
			}
		}
		// id
		actualFreight.setId(UUIDUtils.getUUID());
		// 运单号
		actualFreight.setWaybillNo(waybillEntity.getWaybillNo());
		// 货物名称
		actualFreight.setGoodsName(WaybillConstants.TO_MAKEUP);
		// 货物重量
		actualFreight.setWeight(waybillEntity.getGoodsWeightTotal());
		// 货物体积
		actualFreight.setVolume(waybillEntity.getGoodsVolumeTotal());
		// 件数
		actualFreight.setGoodsQty(waybillEntity.getGoodsQtyTotal());
		//PDA付款方式 addby 254615
		actualFreight.setPdaPaidMethod(waybillEntity.getPaidMethod());
		//PDA现付金额 addby 254615
		actualFreight.setPdaPrePayAmount(waybillEntity.getPrePayAmount());
		
		// 尺寸
		actualFreight.setDimension(WaybillConstants.UNKNOWN);
		// 保价费
		actualFreight.setInsuranceValue(BigDecimal.ZERO);
		// 包装费
		actualFreight.setPackingFee(BigDecimal.ZERO);
		// 送货费
		actualFreight.setDeliverFee(BigDecimal.ZERO);
		// 装卸费
		actualFreight.setLaborFee(BigDecimal.ZERO);
		// 代收货款
		actualFreight.setCodAmount(BigDecimal.ZERO);
		// 增值服务费
		actualFreight.setValueaddFee(BigDecimal.ZERO);
		// 公布价运费
		actualFreight.setFreight(BigDecimal.ZERO);
		// 设置始发库存部门
		String startStockOrgCode = queryStartStocksDepartmentService(waybillEntity);
		actualFreight.setStartStockOrgCode(startStockOrgCode);
		//创建时间
		actualFreight.setCreateTime(waybillEntity.getBillTime());
		// 设置最终库存部门和库区编号
		waybillStockService.getEndStockCodeAndAreaCode(actualFreight, waybillEntity);
		actualFreightService.insertWaybillActualFreight(actualFreight);
	}
	
	
	/**
	 * 
	 * 增加ActualFreight
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-23 下午3:31:05
	 */
	private void addActualFreightfd(WaybillPendingEntity waybillEntity) {
		ActualFreightEntity actualFreight = new ActualFreightEntity();
		//添加地址备注
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillEntity.getOrderNo());
		if(dispatchOrderEntity != null){
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
				dispatchOrderEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
			}
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
				dispatchOrderEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
			}
		}
		// id
		actualFreight.setId(UUIDUtils.getUUID());
		// 运单号
		actualFreight.setWaybillNo(waybillEntity.getWaybillNo());
		// 货物名称
		actualFreight.setGoodsName(WaybillConstants.TO_MAKEUP);
		// 货物重量
		actualFreight.setWeight(waybillEntity.getGoodsWeightTotal());
		// 货物体积
		actualFreight.setVolume(waybillEntity.getGoodsVolumeTotal());
		// 件数
		actualFreight.setGoodsQty(waybillEntity.getGoodsQtyTotal());
		//PDA付款方式 addby 254615
		actualFreight.setPdaPaidMethod(waybillEntity.getPaidMethod());
		//PDA现付金额 addby 254615
		actualFreight.setPdaPrePayAmount(waybillEntity.getPrePayAmount());
		
		// 尺寸
		actualFreight.setDimension(WaybillConstants.UNKNOWN);
		// 保价费
		actualFreight.setInsuranceValue(BigDecimal.ZERO);
		// 包装费
		actualFreight.setPackingFee(BigDecimal.ZERO);
		// 送货费
		actualFreight.setDeliverFee(BigDecimal.ZERO);
		// 装卸费
		actualFreight.setLaborFee(BigDecimal.ZERO);
		// 代收货款
		actualFreight.setCodAmount(BigDecimal.ZERO);
		// 增值服务费
		actualFreight.setValueaddFee(BigDecimal.ZERO);
		// 公布价运费
		actualFreight.setFreight(BigDecimal.ZERO);
		// 设置始发库存部门
		String startStockOrgCode = queryStartStocksDepartmentServicefh(waybillEntity);
		actualFreight.setStartStockOrgCode(startStockOrgCode);
		//创建时间
		actualFreight.setCreateTime(waybillEntity.getBillTime());
		// 设置最终库存部门和库区编号
		waybillStockService.getEndStockCodeAndAreaCodedhf(actualFreight, waybillEntity);
		actualFreightService.insertWaybillActualFreight(actualFreight);
	}
	
	
	/**
	 * 
	 * 获取始发库存部门
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-25 下午3:00:58
	 */
	private String queryStartStocksDepartmentService(WaybillEntity waybillEntry) {
		if (waybillEntry == null) {
			throw new WaybillStoreException("运单为空");
		}
		if (waybillEntry.getCreateOrgCode() == null) {
			throw new WaybillStoreException("创建部门为空");
		}

		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(waybillEntry.getCreateOrgCode());

		if (orgAdministrativeInfoEntity == null) {
			throw new WaybillStoreException("查询创建部门为空");
		}

		//在集中开单的情况下  配载部门取法不同
		if (FossConstants.YES.equals(waybillEntry.getPickupCentralized())) {
			if (waybillEntry.getLoadOrgCode() == null) {
				throw new WaybillStoreException("配载部门为空");
			}

			return waybillEntry.getLoadOrgCode();// 返回配载部门
		} else {

			if (FossConstants.YES.equals(orgAdministrativeInfoEntity
					.getSalesDepartment()))// 是否营业部
			{

				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
						.querySaleDepartmentByCode(waybillEntry.getCreateOrgCode());

				if (saleDepartmentEntity == null) {
					throw new WaybillStoreException("查询营业部为空:" + waybillEntry.getCreateOrgCode());
				}

				if (FossConstants.YES.equals(saleDepartmentEntity.getStation()))// 是否驻地部门
				{
					return saleDepartmentEntity.getTransferCenter();// 如果是，返回驻地营业部
				} else {
					return waybillEntry.getCreateOrgCode();
				}

			} else {
				throw new WaybillStoreException("不是营业部" + ReflectionToStringBuilder.toString(orgAdministrativeInfoEntity));
			}

		}

	}
	
	/**
	 * 
	 * 获取始发库存部门
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-25 下午3:00:58
	 */
	private String queryStartStocksDepartmentServicefh(WaybillPendingEntity waybillEntry) {
		if (waybillEntry == null) {
			throw new WaybillStoreException("运单为空");
		}
		if (waybillEntry.getCreateOrgCode() == null) {
			throw new WaybillStoreException("创建部门为空");
		}

		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(waybillEntry.getCreateOrgCode());

		if (orgAdministrativeInfoEntity == null) {
			throw new WaybillStoreException("查询创建部门为空");
		}

		//在集中开单的情况下  配载部门取法不同
		if (FossConstants.YES.equals(waybillEntry.getPickupCentralized())) {
			if (waybillEntry.getLoadOrgCode() == null) {
				throw new WaybillStoreException("配载部门为空");
			}

			return waybillEntry.getLoadOrgCode();// 返回配载部门
		} else {

			if (FossConstants.YES.equals(orgAdministrativeInfoEntity
					.getSalesDepartment()))// 是否营业部
			{

				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
						.querySaleDepartmentByCode(waybillEntry.getCreateOrgCode());

				if (saleDepartmentEntity == null) {
					throw new WaybillStoreException("查询营业部为空:" + waybillEntry.getCreateOrgCode());
				}

				if (FossConstants.YES.equals(saleDepartmentEntity.getStation()))// 是否驻地部门
				{
					return saleDepartmentEntity.getTransferCenter();// 如果是，返回驻地营业部
				} else {
					return waybillEntry.getCreateOrgCode();
				}

			} else {
				throw new WaybillStoreException("不是营业部" + ReflectionToStringBuilder.toString(orgAdministrativeInfoEntity));
			}

		}

	}

	/**
	 * 
	 * 增加货签信息
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-23 下午3:31:08
	 */
	private void addLabeledGood(WaybillPdaDto waybillPdaDto) {
		// 循环添加货签信息
		for (int i = 1; i <= waybillPdaDto.getGoodsQty(); i++) {
			LabeledGoodEntity labeledGood = new LabeledGoodEntity();
			// id
			labeledGood.setId(UUIDUtils.getUUID());
			// 运单号
			labeledGood.setWaybillNo(waybillPdaDto.getWaybillNo());
			// 有效
			labeledGood.setActive(FossConstants.ACTIVE);
			// 流水号
			labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
			// 创建时间
			labeledGood.setCreateTime(waybillPdaDto.getCreateTime());
			// 修改时间
			labeledGood.setModifyTime(waybillPdaDto.getCreateTime());
			// 开单时间
			labeledGood.setBillTime(waybillPdaDto.getBillStart());
			// 初始化
			labeledGood.setInitalVale(FossConstants.YES);
			labeledGoodDao.insertSelective(labeledGood);
		}
	}

	/**
	 * 偏线和航运的PDA运单提交
	 * 
	 * @author foss-gengzhe
	 * @date 2013-1-21 上午10:24:04
	 * @param waybillPdaDto
	 * @return
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService#submitWaybillByPDAForAirAndVehicle(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto)
	 */
	@Transactional()
	public ResultDto submitWaybillByPDAForAirAndVehicle(WaybillPdaDto waybillPdaDto) {
		String code = "1";
		String msg = "";
		if (waybillPdaDto == null) {
			throw new IllegalArgumentException("运单不能为空！");
		}
		if (StringUtils.isEmpty(waybillPdaDto.getWaybillNo())) {
			throw new IllegalArgumentException("运单号不能为空！");
		} else {
			if (waybillManagerService.isWayBillExsits(waybillPdaDto.getWaybillNo()) || waybillManagerService.isWayBillPendingExsits(waybillPdaDto.getWaybillNo())) {
				throw new PdaInterfaceException("此运单号已经开单或暂存！");
			}
		}
		if (!StringUtils.isEmpty(waybillPdaDto.getOrderNo())) {
			if (waybillManagerService.isWayBillExsitsByOrderNo(waybillPdaDto.getOrderNo()) || waybillManagerService.isWayBillPendingExsitsByOrderNo(waybillPdaDto.getOrderNo())) {
				throw new PdaInterfaceException("输入的订单号已经开单或暂存！");
			}
		}
		if (StringUtils.isEmpty(waybillPdaDto.getCreateUserCode())) {
			throw new IllegalArgumentException("创建人编号不能为空！");
		}
		if (waybillPdaDto.getBillStart() == null) {
			throw new IllegalArgumentException("开单时间不能为空！");
		}

		// 根据开单司机找到车队
		String motorcadeOrgCode = employeeService.queryEmployeeByEmpCode(waybillPdaDto.getBillUserNo()).getOrgCode();
		// 司机所在车队部门
		MotorcadeEntity motorcadeEntity = motorcadeService.queryMotorcadeByCode(motorcadeOrgCode);
		// 车队对应的集中开单组编码
		String billOrgCode;
		if (motorcadeEntity != null) {
			if (StringUtils.isNotEmpty(motorcadeEntity.getServeBillTerm())) {
				billOrgCode = motorcadeEntity.getServeBillTerm();
			} else {
				throw new IllegalArgumentException("通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
			}
		} else {
			throw new IllegalArgumentException("通过司机所在车队部门编码未在车辆司机组织表中找到有效记录");
		}

		try {
			/**
			 * 开单基本信息，操作WaybillPendingEntity
			 */
			WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
			waybillPendingEntity.setActive(FossConstants.ACTIVE);
			// 运单号
			waybillPendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
			// 开单时间
			waybillPendingEntity.setBillTime(waybillPdaDto.getBillStart());
			// 开单人工号
			waybillPendingEntity.setCreateUserCode(waybillPdaDto.getCreateUserCode());
			// 车牌号
			waybillPendingEntity.setLicensePlateNum(waybillPdaDto.getLicensePlateNum());
			// 开单件数
			waybillPendingEntity.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
			// 运输性质
			waybillPendingEntity.setProductCode(waybillPdaDto.getProductCode());
			// 订单号
			waybillPendingEntity.setOrderNo(waybillPdaDto.getOrderNo());
			// 创建时间
			waybillPendingEntity.setCreateTime(waybillPdaDto.getBillStart());
			waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);

			/**
			 * 车队的集中开单部门编码
			 */
			waybillPendingEntity.setCreateOrgCode(billOrgCode);

			/**
			 * pda实收运费对应waybillPendingEntity的预付费用PrePayAmount
			 */
			waybillPendingEntity.setPrePayAmount(waybillPdaDto.geActualFee());	
			//收货人省 DMANA-4296
			waybillPendingEntity.setReceiveCustomerProvCode(waybillPdaDto.getReceiveCustomerProvCode());
			//收货人市
			waybillPendingEntity.setReceiveCustomerCityCode(waybillPdaDto.getReceiveCustomerCityCode());
			//收货人区
			waybillPendingEntity.setReceiveCustomerDistCode(waybillPdaDto.getReceiveCustomerDistCode());
			
			int temp = waybillPendingDao.insertSelective(waybillPendingEntity);
			if (temp == 0) {
				code = "0";
				msg = "保存PDA运单基本信息失败";
			} else {
				// PDA开单后修改约车订单状态
				OrderHandleDto orderHandleDto = new OrderHandleDto();
				orderHandleDto.setOrderNo(waybillPdaDto.getOrderNo());
				orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_WAYBILL);
				this.orderTaskHandleService.orderWaybill(orderHandleDto);
			}
		} catch (BusinessException e) {
			code = "0";
			msg = "保存PDA运单基本信息时失败：" + e.getMessage();
		}
		ResultDto result = new ResultDto();
		result.setCode(code);
		result.setMsg(msg);

		// pda补录消息提醒,通知司机所在车队的集中开单组
		UserEntity userEntity = new UserEntity();
		if (waybillPdaDto.getBillUserNo() != null) {
			userEntity.setEmpCode(waybillPdaDto.getBillUserNo());
			EmployeeEntity employEntity = null;
			if (waybillPdaDto.getBillUserNo() != null) {
				employEntity = employeeService.queryEmployeeByEmpCode(waybillPdaDto.getBillUserNo());
			} else {
				throw new IllegalArgumentException("司机编号为空！");
			}

			if (employEntity != null) {
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmployee(employEntity);
			} else {

				throw new IllegalArgumentException("查询不到该司机！");
			}

		}
		UserContext.setCurrentUser(userEntity);

		pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, null, billOrgCode,
				waybillPdaDto.getWaybillNo(), null);

		return result;
	}

	
	/**
	 * 
	 * PDA快递开单
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-22
	 */
	public void submitWaybillExpress(WaybillExpressPdaDto waybillPdaDto, String billOrgCode) {

		if (waybillPdaDto == null) {
			throw new PdaInterfaceException("运单不能为空！");
		}
		String waybillNo=waybillPdaDto.getWaybillNo();
		
		if (StringUtils.isEmpty(waybillPdaDto.getWaybillNo())) {
			throw new PdaInterfaceException("运单号不能为空！");
		} else {
			if (waybillManagerService.isWayBillExsits(waybillPdaDto.getWaybillNo()) || waybillManagerService.isWayBillPendingExsits(waybillPdaDto.getWaybillNo())) {
				throw new PdaInterfaceException("此运单号已经开单或暂存！");
			}
		}
		if (!StringUtils.isEmpty(waybillPdaDto.getOrderNo())) {
			if (waybillManagerService.isWayBillExsitsByOrderNo(waybillPdaDto.getOrderNo()) || waybillManagerService.isWayBillPendingExsitsByOrderNo(waybillPdaDto.getOrderNo())) {
				throw new PdaInterfaceException("输入的订单号已经开单或暂存！");
			}
		}
		
		if (waybillPdaDto.getCreateTime() == null) {
			throw new PdaInterfaceException("创建时间不能为空！");
		}
		if (StringUtils.isEmpty(waybillPdaDto.getCreateUserCode())) {
			throw new PdaInterfaceException("创建人编号不能为空！");
		}
		if (waybillPdaDto.getBillStart() == null) {
			throw new PdaInterfaceException("开单时间不能为空！");
		}
		if (waybillPdaDto.getGoodsQty() == null || Integer.valueOf(0).equals(waybillPdaDto.getGoodsQty())) {
			throw new PdaInterfaceException("开单件数不能为空！");
		}
//		waybillPdaDto.setNeedDepponCustomerCode(FossConstants.YES);
//		if(WaybillConstants.INNER_PICKUP.equals(waybillPdaDto.getReceiveMethod())){
//			waybillPdaDto.setReceiveMethod(WaybillConstants.SELF_PICKUP);
//		
//		}else{
//			waybillPdaDto.setReceiveMethod(waybillPdaDto.getReceiveMethod());
//		}
		
		pdaWaybillService.submitPdaWaybill(waybillPdaDto, billOrgCode);
		try {
			// 添加t_srv_waybill，t_srv_actual_freight和t_srv_labeled_good记录，方便后续走货
			pdaWaybillService.addForTransferExpress(waybillPdaDto, billOrgCode);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		/**
		 * 新增时效推送信息表
		 **/
		TimeLookDto timelookDto=new TimeLookDto();
		timelookDto.setWaybillNo(waybillNo);
		eamDao.timeLookInsert(timelookDto);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void submitPdaWaybill(WaybillExpressPdaDto waybillPdaDto,
			String billOrgCode) {
		try {
			/**
			 * 开单基本信息，操作WaybillPendingEntity
			 */
			WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
			waybillPendingEntity.setActive(FossConstants.ACTIVE);
			waybillPendingEntity.setCreateTime(waybillPdaDto.getBillStart());
			waybillPendingEntity.setCreateUserCode(waybillPdaDto.getBillUserNo());
			waybillPendingEntity.setFibreNum(waybillPdaDto.getFibre());
			waybillPendingEntity.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
			waybillPendingEntity.setGoodsTypeCode(waybillPdaDto.getGoodsTypeCode());
			waybillPendingEntity.setGoodsVolumeTotal(waybillPdaDto.getGoodsVolumeTotal());
			waybillPendingEntity.setGoodsWeightTotal(waybillPdaDto.getGoodsWeightTotal());
			waybillPendingEntity.setLicensePlateNum(waybillPdaDto.getLicensePlateNum());
			waybillPendingEntity.setMembraneNum(waybillPdaDto.getMembrane());
			waybillPendingEntity.setOrderNo(waybillPdaDto.getOrderNo());
			/**
			 * 短信标识
			 */
			waybillPendingEntity.setIsSMS(waybillPdaDto.getIsSMS());
			// 根据订单号查询订单来源
			if (StringUtils.isNotEmpty(waybillPdaDto.getOrderNo())) {
				DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillPdaDto.getOrderNo());
				waybillPendingEntity.setOrderChannel(dispatchOrderEntity != null ? dispatchOrderEntity.getOrderSource() : null);
				if(dispatchOrderEntity!=null){
					String deliveryCustomerModle = dispatchOrderEntity.getMobile();
					if(StringUtils.isNotEmpty(deliveryCustomerModle)){
						waybillPendingEntity.setDeliveryCustomerMobilephone(deliveryCustomerModle);
						waybillPendingEntity.setDeliveryCustomerName(dispatchOrderEntity.getCustomerName());
						waybillPendingEntity.setDeliveryCustomerPhone(dispatchOrderEntity.getTel());
					}
					//提货地址备注
					if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
						waybillPendingEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
					}
					//发货地址备注
					if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
						waybillPendingEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
					}
				}
				
				
				
			}
			
			 
			
			if(FossConstants.YES.equals(waybillPdaDto.getNeedDepponCustomerCode())){
				waybillPendingEntity.setDeliveryCustomerCode(WaybillConstants.DEPPON_CUSTOMER);
				//CustomerDto dtoc = queryCustomerService.queryCustInfoByCode(WaybillConstants.DEPPON_CUSTOMER);
				CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
				condition.setExactQuery(true);
				condition.setCustCode(WaybillConstants.DEPPON_CUSTOMER);
				List<CustomerContactDto> dtos = queryCustomerService.queryCustomerInfo(condition);
				if(dtos!=null && dtos.size()>0){
					CustomerContactDto d = dtos.get(0);
					waybillPendingEntity.setDeliveryCustomerMobilephone(d.getMobilePhone());
					if(StringUtils.isNotEmpty(d.getCustName())){
						waybillPendingEntity.setDeliveryCustomerName(d.getCustName());
					}else if(StringUtils.isNotEmpty(d.getDeptName())){
						waybillPendingEntity.setDeliveryCustomerName(d.getDeptName());
					}else if(StringUtils.isNotEmpty(d.getContactName())){
						waybillPendingEntity.setDeliveryCustomerName(d.getContactName());
					}
					waybillPendingEntity.setDeliveryCustomerPhone(d.getOfficePhone());
					waybillPendingEntity.setDeliveryCustomerContact(d.getContactName());
				}
			
			}
			
			if(NULL.equalsIgnoreCase(waybillPdaDto.getOtherPackageType())){
				waybillPendingEntity.setOtherPackage(null);
			}else{
				waybillPendingEntity.setOtherPackage(waybillPdaDto.getOtherPackageType());
			}
		
			waybillPendingEntity.setPaidMethod(waybillPdaDto.getPaidMethod());
			waybillPendingEntity.setPaperNum(waybillPdaDto.getPaper());
			waybillPendingEntity.setProductCode(waybillPdaDto.getProductCode());
			waybillPendingEntity.setSalverNum(waybillPdaDto.getSalver());
			waybillPendingEntity.setReceiveOrgCode(billOrgCode  );//挂账用
			if(StringUtils.isNotBlank(waybillPdaDto.getSendEmployeeCode()) && 
				!"NULL".equalsIgnoreCase(waybillPdaDto.getSendEmployeeCode())){
				waybillPendingEntity.setInnerNotes("发货人工号："
					+waybillPdaDto.getSendEmployeeCode()+";");
			}
			
			
			Log.error("input recevei method : "+waybillPdaDto.getReceiveMethod() );
			waybillPendingEntity.setReceiveMethod(waybillPdaDto.getReceiveMethod());
			Log.error("BEFORE inert  waybillPendingEntity recevei method : "+waybillPendingEntity.getReceiveMethod() );
			waybillPendingEntity.setCustomerPickupOrgCode(waybillPdaDto.getTargetOrgCode());
			waybillPendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
			waybillPendingEntity.setWoodNum(waybillPdaDto.getWood());
			waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
			
			//快递新增字段
			//快递员工号
			waybillPendingEntity.setExpressEmpCode(waybillPdaDto.getExpressEmpCode());
			//快递员名称
			waybillPendingEntity.setExpressEmpName(waybillPdaDto.getExpressEmpName());
			//是否快递字段
			waybillPendingEntity.setIsExpress(FossConstants.YES);
			//快递点部门编号
//			waybillPendingEntity.setExpressOrgCode(waybillPdaDto.getExpressOrgCode());
//			//快递点部门名称
//			waybillPendingEntity.setExpressOrgName(waybillPdaDto.getExpressOrgName());
			
			
			EmployeeEntity employ = employeeService.queryEmployeeByEmpCode(StringUtil.defaultIfNull(waybillPdaDto.getExpressEmpCode()));
			OrgAdministrativeInfoEntity department = employ.getDepartment();
			if(null != department){
				//判断是否是快递点部
				if(FossConstants.YES.equals(department.getExpressPart())){
					// 快递点部CODE
					waybillPendingEntity.setExpressOrgCode(department.getCode());
					// 快递点部名称
					waybillPendingEntity.setExpressOrgName(department.getName());
				}
				
			}
			
			//快递PDA串号
			waybillPendingEntity.setPdaSerial(waybillPdaDto.getPdaSerial());
			//快递银行交易流水号
			waybillPendingEntity.setBankTradeSerail(waybillPdaDto.getBankTradeSerail());

			//快递这里很特殊  生成走货用
			waybillPendingEntity.setCreateOrgCode(waybillPdaDto.getStartOrg());

			/**
			 * 优惠信息
			 */
			waybillPendingEntity.setPromotionsFee(waybillPdaDto.getDiscountAmount());
			waybillPendingEntity.setPromotionsCode(waybillPdaDto.getDiscountNo());
			/**
			 * pda应收运费对应waybillPendingEntity的总费用totalFee
			 */
			waybillPendingEntity.setTotalFee(waybillPdaDto.getAmount());
			/**
			 * pda实收运费对应waybillPendingEntity的预付费用PrePayAmount
			 */
			waybillPendingEntity.setPrePayAmount(waybillPdaDto.geActualFee());
			/**
			 * 开单人工号
			 */
			waybillPendingEntity.setDriverCode(waybillPdaDto.getBillUserNo());
			/**
			 * 开单时间
			 */
			waybillPendingEntity.setBillTime(waybillPdaDto.getBillStart());
			/**
			 * 退款类型
			 */
			waybillPendingEntity.setRefundType(waybillPdaDto.getRefundType());
			/**
			 * 返单类型
			 */
			waybillPendingEntity.setReturnBillType(waybillPdaDto.getReturnBillType());

			/**  快递暂时没有打木架功能
			 * 是否代打木架(否"N"或是"Y"),如需代打木架，操作WoodenRequirePendingEntity
			 */
//			WoodenRequirePendingEntity woodenRequirePendingEntity = new WoodenRequirePendingEntity();
//			if (FossConstants.YES.equals(waybillPdaDto.getIsWood())) {
//				woodenRequirePendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
//				woodenRequirePendingEntity.setStandGoodsSize(waybillPdaDto.getWoodSize());
//				woodenRequirePendingEntity.setStandGoodsVolume(waybillPdaDto.getWoodVolume());
//				woodenRequirePendingEntity.setBoxGoodsSize(waybillPdaDto.getWoodBoxSize());
//				woodenRequirePendingEntity.setBoxGoodsVolume(waybillPdaDto.getWoodBoxVolume());
//				woodenRequirePendingDao.insertSelective(woodenRequirePendingEntity);
//			}

			/**
			 * 增值服务,操作WaybillCHDtlPendingEntity
			 */
			List<ValueAddServiceDto> valueAddServiceDtoList = waybillPdaDto.getValueAddServiceDtoList();
			if (valueAddServiceDtoList != null) {
				for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
					
					if (!PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())
							
							&& !PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
						WaybillCHDtlPendingEntity waybillCHDtlPendingEntity = new WaybillCHDtlPendingEntity();
						waybillCHDtlPendingEntity.setActive(FossConstants.YES);
						waybillCHDtlPendingEntity.setAmount(valueAddServiceDto.getAmount());
						waybillCHDtlPendingEntity.setBillTime(waybillPdaDto.getBillStart());
						waybillCHDtlPendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
						waybillCHDtlPendingEntity.setPricingEntryCode(valueAddServiceDto.getCode());
						waybillCHDtlPendingEntity.setCreateTime(waybillPdaDto.getCreateTime());
						waybillCHDtlPendingEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						waybillCHDtlPendingEntity.setPricingCriDetailId(valueAddServiceDto.getSubType());												
						waybillCHDtlPendingDao.addWaybillCHDtlPendingSelective(waybillCHDtlPendingEntity);
					}
					
					//返单类型是在子表中传的
					if(PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(valueAddServiceDto.getCode())){
						waybillPendingEntity.setReturnBillType(valueAddServiceDto.getSubType());
						waybillPdaDto.setReturnBillType(valueAddServiceDto.getSubType());
					}
					
					if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
						waybillPendingEntity.setInsuranceAmount(valueAddServiceDto.getAmount());
						
						// 保价费设置
						waybillPendingEntity.setInsuranceFee(BigDecimal.ZERO);
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
						// 代收货款设置
						waybillPendingEntity.setCodAmount(valueAddServiceDto.getAmount());
						String subType = valueAddServiceDto.getSubType();
						if(StringUtils.isNotEmpty(subType)){
							waybillPendingEntity.setRefundType(subType);
						}
						
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
						// 运费
						waybillPendingEntity.setTransportFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
						// 接货费
						waybillPendingEntity.setPickupFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
						// 送货费
						waybillPendingEntity.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_BZ.equals(valueAddServiceDto.getCode())) {
						// 送货费
						waybillPendingEntity.setPackageFee(valueAddServiceDto.getAmount());
					}/*
					 * else if
					 * (PricingConstants.PriceEntityConstants.PRICING_CODE_QS
					 * .equals(valueAddServiceDto.getCode())){
					 * waybillPendingEntity
					 * .setDeliveryGoodsFee(valueAddServiceDto.getAmount()); //
					 * 签收单返单未确定默认值，暂不设置 }
					 */
				}
			}
			
			//添加送货费
			waybillPendingEntity.setDeliveryGoodsFee(waybillPdaDto.getDeliveryGoodsFee());
			
			
			//是否返货开单(是否返货业务 Y/N)
			if(
			  FossConstants.ACTIVE.equals(waybillPdaDto.getIsReturnGoods())
			         &&
			  waybillPdaDto.getOldWayBill()!=null
			  ){
//				waybillPendingEntity.setReturnType(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				waybillPendingEntity.setReturnType(waybillPdaDto.getReturnWay());
				waybillPendingEntity.setOriginalWaybillNo(waybillPdaDto.getOldWayBill());
			}

			Log.error("inert  waybillPendingEntity recevei method : "+waybillPendingEntity.getReceiveMethod() );
			waybillPendingDao.insertSelective(waybillPendingEntity);
			// 添加货签信息
			addLabelGoodPda(waybillPendingEntity);
			//添加活动信息
			addCrmActiveInfoExpress(waybillPdaDto);
			// 订单号是否空
			if (!StringUtil.isEmpty(waybillPdaDto.getOrderNo())) {
				
				// PDA开单后修改约车订单状态
				OrderHandleDto orderHandleDto = new OrderHandleDto();
				// 订单号
				orderHandleDto.setOrderNo(waybillPdaDto.getOrderNo());
				// 订单状态--已开单
				orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_WAYBILL);
				// 操作人编码
				orderHandleDto.setOperatorCode(waybillPdaDto.getBillUserNo());
				// 操作时间
				orderHandleDto.setOperateTime(waybillPdaDto.getBillStart());
				// 操作部门编码
				orderHandleDto.setOperateOrgCode(waybillPdaDto.getBillOrgCode());
				//运单号
				orderHandleDto.setWaybillNo(waybillPdaDto.getWaybillNo());				
				// 更新订单状态
				orderTaskHandleService.pdaOrderWaybill(orderHandleDto,gainCrmModifyInfoDto(waybillPdaDto));				
			}			
		} catch (BusinessException e) {
			LOGGER.info(e.getMessage(),e);
		}
		// pda补录消息提醒,通知司机所在车队的集中开单组
		UserEntity userEntity = new UserEntity();
		if (waybillPdaDto.getBillUserNo() != null) {
			userEntity.setEmpCode(waybillPdaDto.getBillUserNo());
			EmployeeEntity employEntity = null;
			if (waybillPdaDto.getBillUserNo() != null) {
				employEntity = employeeService.queryEmployeeByEmpCode(waybillPdaDto.getBillUserNo());
			} else {
				throw new PdaInterfaceException("司机编号为空！");
			}

			if (employEntity != null) {
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmployee(employEntity);
			} else {
				throw new PdaInterfaceException("查询不到该司机！");
			}

		}
		UserContext.setCurrentUser(userEntity);

		pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, null, waybillPdaDto.getStartOrg(),
				waybillPdaDto.getWaybillNo(), waybillPdaDto.getWaybillNo());
	}	
	
	/**
	 * 根据运单基本信息封装需要更新的对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午10:02:13
	 */
	private CrmModifyInfoDto gainCrmModifyInfoDto(WaybillExpressPdaDto waybillPdaDto) {
		CrmModifyInfoDto dto = new CrmModifyInfoDto();
		// 订单编号
		dto.setOrderNumber(waybillPdaDto.getOrderNo());
		// 运单号码
		dto.setWaybillNumber(waybillPdaDto.getWaybillNo());
		// 操作人员
		dto.setOprateUserNum(waybillPdaDto.getBillUserNo());
		// 操作人员所在部门
		dto.setOprateDeptCode(waybillPdaDto.getBillOrgCode());	
		dto.setWeight(waybillPdaDto.getGoodsWeightTotal()==null ? 0 : waybillPdaDto.getGoodsWeightTotal().doubleValue());
		dto.setVolume(waybillPdaDto.getGoodsVolumeTotal()==null ? 0 : waybillPdaDto.getGoodsVolumeTotal().doubleValue());
		// 货物状态
		dto.setGoodsStatus(WaybillConstants.CRM_ORDER_WAYBILL);
		// 收入部门标杆编码
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillPdaDto.getStartOrg());
		if (null != org) {
			String unifedCode = StringUtil.defaultIfNull(org.getUnifiedCode());
			dto.setEarningDeptStandardCode(unifedCode);
			// 收入部门名称
			dto.setEarningDeptStandardName(org.getName());
		}
		
		//判断司机工号是否为空，根据DEFECT-3385进行修改		
		if(StringUtil.isNotEmpty(waybillPdaDto.getCreateUserCode())){
			// 员工信息
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(waybillPdaDto.getCreateUserCode());
			if (null != emp) {
				// 司机姓名
				dto.setDriverName(emp.getEmpName());
				// 司机手机
				dto.setDriverMobile(emp.getMobilePhone());	
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addForTransferExpress(WaybillExpressPdaDto waybillPdaDto, String billOrgCode) {
		// 添加运单
		WaybillEntity waybillEntity = addWaybillExpress(waybillPdaDto, billOrgCode);
		// 添加ActualFreight
		addActualFreight(waybillEntity);
		// 添加货件
		addLabeledGoodExpress(waybillPdaDto);
		//推送货物轨迹
		addSynTrack(waybillPdaDto);
	}
	
	
	/**
	 * 
	 * 增加货签信息
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-23 下午3:31:08
	 */
	private void addLabeledGoodExpress(WaybillExpressPdaDto waybillPdaDto) {
		// 循环添加货签信息
		for (int i = 1; i <= waybillPdaDto.getGoodsQty(); i++) {
			LabeledGoodEntity labeledGood = new LabeledGoodEntity();
			// id
			labeledGood.setId(UUIDUtils.getUUID());
			// 运单号
			labeledGood.setWaybillNo(waybillPdaDto.getWaybillNo());
			// 有效
			labeledGood.setActive(FossConstants.ACTIVE);
			// 流水号
			labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
			// 创建时间
			labeledGood.setCreateTime(waybillPdaDto.getCreateTime());
			// 修改时间
			labeledGood.setModifyTime(waybillPdaDto.getCreateTime());
			// 开单时间
			labeledGood.setBillTime(waybillPdaDto.getBillStart());
			// 初始化
			labeledGood.setInitalVale(FossConstants.YES);
			labeledGoodDao.insertSelective(labeledGood);
		}
	}
	
	private WaybillEntity addWaybillExpress(WaybillExpressPdaDto waybillPdaDto, String billOrgCode) {
		// 查询产品
		ProductEntity product = productService.getProductByCache(waybillPdaDto.getProductCode(), new Date());
		if (product == null) {
			LOGGER.info("根据产品编号查询不到对应的产品信息，编号为：" + waybillPdaDto.getProductCode());
			throw new PdaInterfaceException("根据产品编号查询不到对应的产品信息，编号为：" + waybillPdaDto.getProductCode());
		}
		OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(true, waybillPdaDto.getStartOrg(), waybillPdaDto.getTargetOrgCode(), waybillPdaDto.getProductCode());
		if(orgInfoDto == null) {
			LOGGER.info("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPdaDto.getStartOrg() + "到达部门：" + waybillPdaDto.getTargetOrgCode());
			throw new PdaInterfaceException("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPdaDto.getStartOrg() + "到达部门：" + waybillPdaDto.getTargetOrgCode());
		}
		// 对象传递
		TransportPathEntity transportPath = new TransportPathEntity();
		//运单号
		transportPath.setWaybillNo(waybillPdaDto.getWaybillNo());
		//开单时间
		transportPath.setBillingTime(waybillPdaDto.getBillStart());
		//开单组织 
		transportPath.setBillingOrgCode(waybillPdaDto.getBillOrgCode());
		
		//当前库存部门编码
		String currentOrgCode = waybillPdaDto.getStartOrg();// "";
		
		
//		//根据集中接送货部门编码查询其对应的外场编码
//		BillingGroupTransFerEntity entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(billOrgCode);
//		//判断对象是否为空
//		if(null != entity){
//			//外场编码
//			currentOrgCode = entity.getTransFerCode();
//			//非空判断
//			if(StringUtil.isEmpty(currentOrgCode)){
//				//若外场编码为空，则抛出异常信息（"集中接送货部门对应的外场编码为空！"）
//				throw new PdaInterfaceException("集中接送货部门+"+waybillPdaDto.getBillOrgCode()+"+对应的外场编码为空！");
//			}
//		}else{
//			//若没有查询到外场编码，则抛出异常信息
//			throw new PdaInterfaceException(waybillPdaDto.getBillOrgCode()+"没有查询到外场编码");
//		}

		//当前部门编码
		transportPath.setCurrentOrgCode(currentOrgCode);
		//最终到达部门编号
		transportPath.setDestOrgCode(waybillPdaDto.getTargetOrgCode());
		//总重量
		transportPath.setTotalWeight(waybillPdaDto.getGoodsWeightTotal());
		//总体积
		transportPath.setTotalVolume(waybillPdaDto.getGoodsVolumeTotal());
		//总件数
		transportPath.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
		//运输类型
		transportPath.setTransportModel(waybillPdaDto.getProductCode());
		// 生成走货路径
		calculateTransportPathService.createTransportPath(transportPath);
		WaybillEntity waybill = new WaybillEntity();
		// 主键id
		waybill.setId(UUIDUtils.getUUID());
		// 运单号
		waybill.setWaybillNo(waybillPdaDto.getWaybillNo());
		// 收货联系人
		waybill.setReceiveCustomerContact(WaybillConstants.TO_MAKEUP);
		// 送货联系人
		waybill.setDeliveryCustomerContact(WaybillConstants.TO_MAKEUP);
		// 收货部门
		waybill.setReceiveOrgCode(billOrgCode);
		String currentOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(billOrgCode);	
		waybill.setReceiveOrgName(currentOrgName);
		// 产品编码
		waybill.setProductCode(waybillPdaDto.getProductCode());
		// 产品id
		waybill.setProductId(product.getId());
		// 提货方式
		waybill.setReceiveMethod(waybillPdaDto.getReceiveMethod());
		// 是否上门接货
		waybill.setPickupToDoor(FossConstants.YES);
		// 是否集中接货
		waybill.setPickupCentralized(FossConstants.YES);
		// 始发配载部门
		waybill.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());
		// 始发配载部门名称
		waybill.setLoadOrgName(orgInfoDto.getFirstLoadOrgName());
		// 最终配载部门
		waybill.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());
		// 最终配载部门名称
		waybill.setLastLoadOrgName(orgInfoDto.getLastLoadOrgName());
		// 是否大车直送
		waybill.setCarDirectDelivery(FossConstants.NO);
		// 货物名称
		waybill.setGoodsName(WaybillConstants.TO_MAKEUP);
		// 货物体积
		BigDecimal volume = waybillPdaDto.getGoodsVolumeTotal();
		if(volume!=null){
			waybill.setGoodsVolumeTotal(volume.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		// 货物重量
		waybill.setGoodsWeightTotal(waybillPdaDto.getGoodsWeightTotal());
		// 车牌号
		waybill.setLicensePlateNum(waybillPdaDto.getLicensePlateNum());
		// 膜包装
		waybill.setMembraneNum(waybillPdaDto.getMembrane());
		// 订单号
		waybill.setOrderNo(waybillPdaDto.getOrderNo());
		// 件数
		waybill.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
		// 货物类型
		waybill.setGoodsTypeCode(waybillPdaDto.getGoodsTypeCode());
		// 纤包装
		waybill.setFibreNum(waybillPdaDto.getFibre());
		// 根据订单号查询订单来源
		if (StringUtils.isNotEmpty(waybillPdaDto.getOrderNo())) {
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillPdaDto.getOrderNo());
			waybill.setOrderChannel(dispatchOrderEntity != null ? dispatchOrderEntity.getOrderSource() : null);
		}
		waybill.setOtherPackage(waybillPdaDto.getOtherPackageType());
		waybill.setPaidMethod(waybillPdaDto.getPaidMethod());
		waybill.setPaperNum(waybillPdaDto.getPaper());
		waybill.setCustomerPickupOrgCode(waybillPdaDto.getTargetOrgCode());
		String currentOrgName2 = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillPdaDto.getTargetOrgCode());	
		waybill.setCustomerPickupOrgName(currentOrgName2);
		waybill.setWoodNum(waybillPdaDto.getWood());
		/**
		 * 优惠信息
		 */
		waybill.setPromotionsFee(waybillPdaDto.getDiscountAmount());
		waybill.setPromotionsCode(waybillPdaDto.getDiscountNo());
		/**
		 * pda应收运费对应waybill的总费用totalFee
		 */
		waybill.setTotalFee(waybillPdaDto.getAmount());
		/**
		 * pda实收运费对应waybill的预付费用PrePayAmount
		 */
		waybill.setPrePayAmount(waybillPdaDto.geActualFee());
		/**
		 * 开单人工号
		 */
		waybill.setDriverCode(waybillPdaDto.getBillUserNo());
		// 是否贵重物品
		waybill.setPreciousGoods(FossConstants.NO);
		// 是否异型货物
		waybill.setSpecialShapedGoods(FossConstants.NO);
		// 预付费保密
		waybill.setSecretPrepaid(FossConstants.NO);
		// 装卸费
		waybill.setServiceFee(BigDecimal.ZERO);
		// 有效
		waybill.setActive(FossConstants.ACTIVE);
		// 是否禁行
		waybill.setForbiddenLine(FossConstants.NO);
		// 总费用
		waybill.setTotalFee(waybillPdaDto.getAmount());
		// 创建时间
		waybill.setCreateTime(waybillPdaDto.getBillStart());
		// 修改时间
		waybill.setModifyTime(waybillPdaDto.getBillStart());
		// 开单时间
		waybill.setBillTime(waybillPdaDto.getBillStart());
		// 创建人编码
		waybill.setCreateUserCode(waybillPdaDto.getBillUserNo());
		// 创建部门编码
		waybill.setCreateOrgCode( waybillPdaDto.getStartOrg() );
		// 币种
		waybill.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 是否整车
		waybill.setIsWholeVehicle(FossConstants.NO);
		// 整车约车报价
		waybill.setWholeVehicleAppfee(BigDecimal.ZERO);
		// 计费重量
		waybill.setBillWeight(BigDecimal.ZERO);
		/**
		 * 退款类型
		 */
		waybill.setRefundType(waybillPdaDto.getRefundType());
		/**
		 * 返单类型
		 */
		waybill.setReturnBillType(waybillPdaDto.getReturnBillType());
		// 增值服务
		List<ValueAddServiceDto> valueAddServiceDtoList = waybillPdaDto.getValueAddServiceDtoList();
		// 接货费默认值
		waybill.setPickupFee(BigDecimal.ZERO);
		if (valueAddServiceDtoList != null) {
			for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
					// 保价费设置
					waybill.setInsuranceAmount(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
					// 代收货款设置
					waybill.setCodAmount(valueAddServiceDto.getAmount());
				} 
				/**
				else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
					// 运费
					waybill.setTransportFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
					// 接货费
					waybill.setPickupFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
					// 送货费
					waybill.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
				} */
			}
		}
		// 预计到达时间
		waybill.setPreArriveTime(waybillPdaDto.getCreateTime());
		// 补录状态
		waybill.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		
		
		if(FossConstants.YES.equals(waybillPdaDto.getNeedDepponCustomerCode())){
			waybill.setDeliveryCustomerCode(WaybillConstants.DEPPON_CUSTOMER);
			//CustomerDto dtoc = queryCustomerService.queryCustInfoByCode(WaybillConstants.DEPPON_CUSTOMER);
			CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
			condition.setExactQuery(true);
			condition.setCustCode(WaybillConstants.DEPPON_CUSTOMER);
			List<CustomerContactDto> dtos = queryCustomerService.queryCustomerInfo(condition);
			if(dtos!=null && dtos.size()>0){
				CustomerContactDto d = dtos.get(0);
				waybill.setDeliveryCustomerMobilephone(d.getMobilePhone());
				if(StringUtils.isNotEmpty(d.getCustName())){
					waybill.setDeliveryCustomerName(d.getCustName());
				}else if(StringUtils.isNotEmpty(d.getDeptName())){
					waybill.setDeliveryCustomerName(d.getDeptName());
				}else if(StringUtils.isNotEmpty(d.getContactName())){
					waybill.setDeliveryCustomerName(d.getContactName());
				}
				waybill.setDeliveryCustomerPhone(d.getOfficePhone());
				waybill.setDeliveryCustomerContact(d.getContactName());
			}
		
		}
		waybill.setIsExpress(FossConstants.YES);
		waybillDao.addWaybillEntity(waybill);
		
		return waybill;
	}
	
	
	
	public IQueryCustomerService getQueryCustomerService() {
		return queryCustomerService;
	}

	public void setQueryCustomerService(IQueryCustomerService queryCustomerService) {
		this.queryCustomerService = queryCustomerService;
	}

	private static final String NULL = "NULL";
	
	/***************************************PDA增加修改功能****************************************/
	/** 
	 * @remark 修改PDA信息(PDA外部调用)
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	@Transactional
	public ResultDto updateWaybillByPDA(WaybillPdaDto waybillPdaDto){
		WaybillPendingEntity pending=null;
		if (waybillPdaDto == null) {
			throw new PdaInterfaceException("运单不能为空！");
		}
		if (StringUtils.isEmpty(waybillPdaDto.getWaybillNo())) {
			throw new PdaInterfaceException("运单号不能为空！");
		}		
		pending=waybillPendingDao.getPendingByWaybillNoAndType(waybillPdaDto.getWaybillNo(),WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		boolean flag=waybillManagerService.isWayBillExsits(waybillPdaDto.getWaybillNo());
		if (pending==null && !flag) {
			//此运单还未在PDA开单，则调用增加方法，因为异步时修改与增加的顺序可能有变。
			return submitWaybillByPDA(waybillPdaDto);
		}else{
			//修改运单信息
			return updateWaybillInfo(waybillPdaDto, pending);
		}
	}
	
	/**
	 * 修改运单信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-2-22 下午4:16:08
	 */
	@Transactional
	private ResultDto updateWaybillInfo(WaybillPdaDto waybillPdaDto, WaybillPendingEntity pending){
		WaybillEntity waybill=waybillDao.queryWaybillByNoExceptPDAPending(waybillPdaDto.getWaybillNo());
		if(waybill!=null){
			throw new PdaInterfaceException(waybillPdaDto.getWaybillNo()+"此运单已经补录，无法进行修改！");
		}		
		if (!StringUtils.isEmpty(waybillPdaDto.getOrderNo())) {
			if (isOrderNoExists(waybillPdaDto.getOrderNo(),waybillPdaDto.getWaybillNo())) {
				throw new PdaInterfaceException(waybillPdaDto.getOrderNo()+"此订单号已经开单或被绑定！");
			}
		}
		if (StringUtils.isEmpty(waybillPdaDto.getBillOrgCode())) {
			throw new PdaInterfaceException("司机所在车队部门不能为空！");
		}
		if (waybillPdaDto.getCreateTime() == null) {
			throw new PdaInterfaceException("创建时间不能为空！");
		}
		if (StringUtils.isEmpty(waybillPdaDto.getCreateUserCode())) {
			throw new PdaInterfaceException("创建人编号不能为空！");
		}
		if (waybillPdaDto.getBillStart() == null) {
			throw new PdaInterfaceException("开单时间不能为空！");
		}
		if (waybillPdaDto.getGoodsQty() == null) {
			throw new PdaInterfaceException("件数不能为空！");
		}
		//修改件数会产生流水号无法处理，没有库存，无法调整走货路径的问题，限制修改件数
		if (pending!=null && pending.getGoodsQtyTotal()!=null) {
			if(pending.getGoodsQtyTotal().intValue()!=waybillPdaDto.getGoodsQty().intValue()){
				throw new PdaInterfaceException("件数不允许修改！");
			}			
		}
		// 查找对应的顶级车队
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(waybillPdaDto.getBillOrgCode());
		if(topFleet == null){
			throw new PdaInterfaceException("通过司机所在车队部门编码未找到对应的顶级车队");
		}
		// 司机所在车队部门
		MotorcadeEntity motorcadeEntity = motorcadeService.queryMotorcadeByCode(topFleet.getCode());
		// 车队对应的集中开单组编码
		String billOrgCode;
		if (motorcadeEntity != null) {
			if (StringUtils.isNotEmpty(motorcadeEntity.getServeBillTerm())) {
				billOrgCode = motorcadeEntity.getServeBillTerm();
			} else {
				throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
			}
		} else {
			throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表中找到有效记录");
		}
		
		// 修改待补录信息
		pdaWaybillService.updateWaybill(waybillPdaDto, billOrgCode);

		// 修改运单信息
		pdaWaybillService.updateForTransfer(waybillPdaDto, billOrgCode, pending);
		
		return new ResultDto();
	}
	
	/**
	 * 判断订单号是否存在
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-22 上午11:33:10
	 */
	private boolean isOrderNoExists(String orderNo,String waybillNo){
		//判断运单表中是否存在此订单号
		WaybillEntity waybill=waybillDao.queryWaybillByNoAndOrderNo(null, orderNo);
		if(waybill!=null && !waybillNo.equals(waybill.getWaybillNo())){
			return true;
		}
		//判断运单表中是否存在此订单号
		WaybillPendingEntity pending=waybillPendingDao.queryPendingByNoAndOrderNo(null, orderNo);
		if(pending!=null && !waybillNo.equals(pending.getWaybillNo())){
			return true;
		}		
		return false;
	}
	 
	
	/** 
	 * @remark pda下拉合票汇总表(PDA调用)
	 * @author FanBangyu
	 * @date 2015-9-16 下午14:10:30
	 */
	@Override
	public List<LabelPrintDto> downloadCombinateBill(
			List<String> listCustomerLableNums) {
		     //LabelPrintDto 
			List<CombinateBillEntity> labels = combinateBillDao
					.downloadCombillTotal();// 获取全部明细表
			List<CombinateBillEntity>  combinateBills=null;
			if(CollectionUtils.isNotEmpty(labels))
			{
				combinateBills = new ArrayList<CombinateBillEntity>();//
				combinateBills.addAll(labels);
				if(CollectionUtils.isNotEmpty(listCustomerLableNums)){
					for(String  cusnum:listCustomerLableNums){
						for(CombinateBillEntity label:labels){
							if(cusnum.equals(label.getCustomerLableNums())){
								combinateBills.remove(label);
				}
				}
										}
				}	
										}
	
			List<LabelPrintDto>  labelPrintDtos= null;
			if(CollectionUtils.isNotEmpty(combinateBills)){
				labelPrintDtos= new ArrayList<LabelPrintDto>();
				for(CombinateBillEntity cb:combinateBills){
					if(StringUtil.isBlank(cb.getWaybillNo())){
						continue;
		}
					String lableNums =cb.getCustomerLableNums();
					String  serinaNOs= cb.getSerialNo();
					String[] lablestr= lableNums.split(",");
					String[] serinastr= serinaNOs.split(",");
					if(lablestr!=null && lablestr.length>0 && lablestr.length==serinastr.length){
					  for(int i=0;i<lablestr.length;i++){
						 LabelPrintDto lb = new LabelPrintDto();
						lb.setId(cb.getId());
						lb.setWblCode(cb.getWaybillNo());
						lb.setBarcode(cb.getBarcode());
						lb.setSend(cb.getSend());
						lb.setPieces(cb.getPieces().toString());
						lb.setTransType(cb.getProductName());
						lb.setWrapType(cb.getPackageService());
						lb.setDestinationName(cb.getDestinationName());
						lb.setUserCode("");
						lb.setDestTransCenterName(cb.getDestTransCenterName());
						lb.setDepartmentCityName(cb.getDepartmentCityName());
						lb.setDestStationNumber(cb.getDestStationNumber());
						lb.setGoodsAreas(cb.getGoodsAreas());
						lb.setGoodsType(cb.getGoodsType());
						lb.setDeliveryBigCustomer(cb.getDeliveryBigCustomer());
						lb.setReceiveBigCustomer(cb.getReceiveBigCustomer());
						lb.setArriveStoreNUM(cb.getArriveStoreNUM());
						lb.setActive(cb.getActive());
						lb.setFoss_systime(cb.getFoss_systime());
					    lb.setCreateTime(cb.getCreateTime());
						lb.setModifyTime(cb.getModifyTime());
						lb.setCustomerLableNums(lablestr[i]);
						lb.setSeriCode(serinastr[i]);
						lb.setIsExhibitCargo(cb.getIsExhibitCargo());
						labelPrintDtos.add(lb);
		          }
		 }
		
		}
		}
			return labelPrintDtos;
		
		}

		
		
	/** 
	 * @remark 修改PDA待补录信息(FOSS内部调用)
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	@Transactional
	public void updateWaybill(WaybillPdaDto waybillPdaDto, String billOrgCode){
		try {
			/**
			 * 开单基本信息，操作WaybillPendingEntity
			 */
			WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
			waybillPendingEntity.setActive(FossConstants.ACTIVE);
			waybillPendingEntity.setCreateTime(waybillPdaDto.getBillStart());
			waybillPendingEntity.setCreateUserCode(waybillPdaDto.getBillUserNo());
			waybillPendingEntity.setFibreNum(waybillPdaDto.getFibre());
			waybillPendingEntity.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
			waybillPendingEntity.setGoodsTypeCode(waybillPdaDto.getGoodsTypeCode());
			waybillPendingEntity.setGoodsVolumeTotal(waybillPdaDto.getGoodsVolumeTotal());
			waybillPendingEntity.setGoodsWeightTotal(waybillPdaDto.getGoodsWeightTotal());
			waybillPendingEntity.setLicensePlateNum(waybillPdaDto.getLicensePlateNum());
			waybillPendingEntity.setMembraneNum(waybillPdaDto.getMembrane());
			waybillPendingEntity.setOrderNo(waybillPdaDto.getOrderNo());
			// 根据订单号查询订单来源
			if (StringUtils.isNotEmpty(waybillPdaDto.getOrderNo())) {
				DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillPdaDto.getOrderNo());
				waybillPendingEntity.setOrderChannel(dispatchOrderEntity != null ? dispatchOrderEntity.getOrderSource() : null);
				if(dispatchOrderEntity != null){
					//发货地址备注
					if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
						waybillPendingEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
					}
					//收货地址备注
					if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
						waybillPendingEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
					}
				}
			}
			waybillPendingEntity.setOtherPackage(waybillPdaDto.getOtherPackageType());
			waybillPendingEntity.setPaidMethod(waybillPdaDto.getPaidMethod());
			waybillPendingEntity.setPaperNum(waybillPdaDto.getPaper());
			waybillPendingEntity.setProductCode(waybillPdaDto.getProductCode());
			waybillPendingEntity.setSalverNum(waybillPdaDto.getSalver());
			waybillPendingEntity.setReceiveOrgCode(waybillPdaDto.getStartOrg());
			waybillPendingEntity.setReceiveMethod(waybillPdaDto.getReceiveMethod());
			waybillPendingEntity.setCustomerPickupOrgCode(waybillPdaDto.getTargetOrgCode());
			waybillPendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
			waybillPendingEntity.setWoodNum(waybillPdaDto.getWood());
			waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);

			/**
			 * 车队的集中开单部门编码
			 */
			waybillPendingEntity.setCreateOrgCode(billOrgCode);

			/**
			 * 优惠信息
			 */
			waybillPendingEntity.setPromotionsFee(waybillPdaDto.getDiscountAmount());
			waybillPendingEntity.setPromotionsCode(waybillPdaDto.getDiscountNo());
			/**
			 * pda应收运费对应waybillPendingEntity的总费用totalFee
			 */
			waybillPendingEntity.setTotalFee(waybillPdaDto.getAmount());
			/**
			 * pda实收运费对应waybillPendingEntity的预付费用PrePayAmount
			 */
			waybillPendingEntity.setPrePayAmount(waybillPdaDto.geActualFee());
			/**
			 * 开单人工号
			 */
			waybillPendingEntity.setDriverCode(waybillPdaDto.getBillUserNo());
			/**
			 * 开单时间
			 */
			waybillPendingEntity.setBillTime(waybillPdaDto.getBillStart());
			/**
			 * 退款类型
			 */
			waybillPendingEntity.setRefundType(waybillPdaDto.getRefundType());
			/**
			 * 返单类型
			 */
			waybillPendingEntity.setReturnBillType(waybillPdaDto.getReturnBillType());

			/**
			 * 是否代打木架(否"N"或是"Y"),如需代打木架，操作WoodenRequirePendingEntity
			 */
			WoodenRequirePendingEntity woodenRequirePendingEntity = new WoodenRequirePendingEntity();
			if (FossConstants.YES.equals(waybillPdaDto.getIsWood())) {
				woodenRequirePendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
				woodenRequirePendingEntity.setStandGoodsSize(waybillPdaDto.getWoodSize());
				woodenRequirePendingEntity.setStandGoodsVolume(waybillPdaDto.getWoodVolume());
				woodenRequirePendingEntity.setBoxGoodsSize(waybillPdaDto.getWoodBoxSize());
				woodenRequirePendingEntity.setBoxGoodsVolume(waybillPdaDto.getWoodBoxVolume());				
				if(StringUtil.isNotEmpty(woodenRequirePendingEntity.getWaybillNo())){
					//先将待处理运单的打木架信息设置为无效（根据MANA-385修改）
					woodenRequirePendingDao.deleteWoodenRequirementsByWaybillNo(woodenRequirePendingEntity.getWaybillNo());
					woodenRequirePendingDao.insertSelective(woodenRequirePendingEntity);
				}			
			}

			/**
			 * 增值服务,操作WaybillCHDtlPendingEntity
			 */
			List<ValueAddServiceDto> valueAddServiceDtoList = waybillPdaDto.getValueAddServiceDtoList();
			if (valueAddServiceDtoList != null) {
				if(StringUtil.isNotEmpty(waybillPdaDto.getWaybillNo())){
					//先将原来的费用明细删除（根据MANA-385修改）
					waybillCHDtlPendingDao.deleteCHDtlPendingByWaybillNo(waybillPdaDto.getWaybillNo());
					for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
						WaybillCHDtlPendingEntity waybillCHDtlPendingEntity = new WaybillCHDtlPendingEntity();
						waybillCHDtlPendingEntity.setActive(FossConstants.YES);
						waybillCHDtlPendingEntity.setAmount(valueAddServiceDto.getAmount());
						waybillCHDtlPendingEntity.setBillTime(waybillPdaDto.getBillStart());
						waybillCHDtlPendingEntity.setWaybillNo(waybillPdaDto.getWaybillNo());
						waybillCHDtlPendingEntity.setPricingEntryCode(valueAddServiceDto.getCode());
						waybillCHDtlPendingEntity.setCreateTime(waybillPdaDto.getCreateTime());
						waybillCHDtlPendingEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);											
						waybillCHDtlPendingDao.addWaybillCHDtlPendingSelective(waybillCHDtlPendingEntity);										
						if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
							// 保价费设置						
							waybillPendingEntity.setInsuranceAmount(valueAddServiceDto.getAmount());
						} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
							// 代收货款设置
							waybillPendingEntity.setCodAmount(valueAddServiceDto.getAmount());
							waybillPendingEntity.setRefundType(valueAddServiceDto.getSubType());
						} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
							// 运费
							waybillPendingEntity.setTransportFee(valueAddServiceDto.getAmount());
						} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
							// 接货费
							waybillPendingEntity.setPickupFee(valueAddServiceDto.getAmount());
						} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
							// 包装费
							waybillPendingEntity.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
						}					
					}
				}
			}
			if(StringUtil.isNotEmpty(waybillPendingEntity.getWaybillNo())){
				//先将原来的待补录信息删除（根据MANA-385修改）
				waybillPendingDao.deleteByWaybillNo(waybillPendingEntity.getWaybillNo());
				waybillPendingDao.insertSelective(waybillPendingEntity);	
			}			
			// 更新货签信息（修改件数没有库存，无法调整走货路径，取消修改）
//			updateLabelGoodPda(waybillPendingEntity);
			//更新DPA同步时保存的营销活动信息			
			updatePdaPendingActiveInfo(waybillPdaDto);
			// 订单号是否空
			if (!StringUtil.isEmpty(waybillPdaDto.getOrderNo())) {
				// PDA开单后修改约车订单状态
				OrderHandleDto orderHandleDto = new OrderHandleDto();
				// 订单号
				orderHandleDto.setOrderNo(waybillPdaDto.getOrderNo());
				// 订单状态--已开单
				orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_WAYBILL);
				// 操作人编码
				orderHandleDto.setOperatorCode(waybillPdaDto.getBillUserNo());
				// 操作时间
				orderHandleDto.setOperateTime(waybillPdaDto.getBillStart());
				// 操作部门编码
				orderHandleDto.setOperateOrgCode(waybillPdaDto.getBillOrgCode());
				// 更新订单状态
				orderTaskHandleService.orderWaybill(orderHandleDto);
			}			
		} catch (BusinessException e) {
			LOGGER.info(e.getMessage(),e);
		}
		// pda补录消息提醒,通知司机所在车队的集中开单组
		UserEntity userEntity = new UserEntity();
		if (waybillPdaDto.getBillUserNo() != null) {
			userEntity.setEmpCode(waybillPdaDto.getBillUserNo());
			EmployeeEntity employEntity = null;
			if (waybillPdaDto.getBillUserNo() != null) {
				employEntity = employeeService.queryEmployeeByEmpCode(waybillPdaDto.getBillUserNo());
			} else {
				throw new PdaInterfaceException("司机编号为空！");
			}

			if (employEntity != null) {
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmployee(employEntity);
			} else {
				throw new PdaInterfaceException("查询不到该司机！");
			}

		}
		UserContext.setCurrentUser(userEntity);

			
	}	
	
	/**
	 * 更新同步时保存的推广活动信息
	 * @创建时间 2014-5-10 下午6:31:43   
	 * @创建人： WangQianJin
	 */
	private void updatePdaPendingActiveInfo(WaybillPdaDto waybillPdaDto){
		//作废原来的活动信息
		waybillManagerService.updateInactiveByWaybillNo(waybillPdaDto.getWaybillNo());
		//添加活动信息
		addCrmActiveInfo(waybillPdaDto);
	}

	/** 
	 * @remark 修改PDA正式运单信息(FOSS内部调用)
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	@Transactional
	public void updateForTransfer(WaybillPdaDto waybillPdaDto, String billOrgCode, WaybillPendingEntity pending){
		// 更新运单
		WaybillEntity waybillEntity = updateWaybillInfo(waybillPdaDto, billOrgCode, pending);
		// 更新ActualFreight
		updateActualFreight(waybillEntity);
		// 更新货件(修改件数没有库存，无法调整走货路径，取消修改)
//		updateLabeledGood(waybillPdaDto);	
	}
	
	/** 
	 * @remark 修改运单信息
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	private WaybillEntity updateWaybillInfo(WaybillPdaDto waybillPdaDto, String billOrgCode, WaybillPendingEntity pending) {
		// 查询产品
		ProductEntity product = productService.getProductByCache(waybillPdaDto.getProductCode(), new Date());
		if (product == null) {
			LOGGER.info("根据产品编号查询不到对应的产品信息，编号为：" + waybillPdaDto.getProductCode());
			throw new PdaInterfaceException("根据产品编号查询不到对应的产品信息，编号为：" + waybillPdaDto.getProductCode());
		}
		OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(true, waybillPdaDto.getStartOrg(), waybillPdaDto.getTargetOrgCode(), waybillPdaDto.getProductCode());
		if(orgInfoDto == null) {
			LOGGER.info("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPdaDto.getStartOrg() + "到达部门：" + waybillPdaDto.getTargetOrgCode());
			throw new PdaInterfaceException("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPdaDto.getStartOrg() + "到达部门：" + waybillPdaDto.getTargetOrgCode());
		}
		// 对象传递
		TransportPathEntity transportPath = new TransportPathEntity();
		//运单号
		transportPath.setWaybillNo(waybillPdaDto.getWaybillNo());
		//开单时间
		transportPath.setBillingTime(waybillPdaDto.getBillStart());
		//开单组织 
		transportPath.setBillingOrgCode(waybillPdaDto.getBillOrgCode());
		
		//当前库存部门编码
		String currentOrgCode = "";
		//判断是否异地开单
		WaybillPictureEntity waybillPic = new WaybillPictureEntity();
		waybillPic.setActive(FossConstants.YES);
		String waybillNo = waybillPdaDto.getWaybillNo();
		waybillPic.setWaybillNo(waybillNo);
		if(StringUtil.isNotEmpty(waybillNo)){
			waybillPic = waybillPendingService.queryWaybillPictureByEntity(waybillPic);
		}
		//根据集中接送货部门编码查询其对应的外场编码
		BillingGroupTransFerEntity entity = null;
		if(waybillPic!=null && FossConstants.NO.equals(waybillPic.getLocal())
				&& StringUtil.isNotEmpty(waybillPic.getLocalBillGroupCode())){
			//根据集中接送货部门编码查询其对应的外场编码
			 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(waybillPic.getLocalBillGroupCode());
		}else{
			//根据集中接送货部门编码查询其对应的外场编码
			 entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(billOrgCode);
		}
		//根据集中接送货部门编码查询其对应的外场编码
		//BillingGroupTransFerEntity entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(billOrgCode);
		//判断对象是否为空
		if(null != entity){
			//外场编码
			currentOrgCode = entity.getTransFerCode();
			//非空判断
			if(StringUtil.isEmpty(currentOrgCode)){
				//若外场编码为空，则抛出异常信息（"集中接送货部门对应的外场编码为空！"）
				throw new PdaInterfaceException("集中接送货部门+"+waybillPdaDto.getBillOrgCode()+"+对应的外场编码为空！");
			}
		}else{
			//若没有查询到外场编码，则抛出异常信息
			throw new PdaInterfaceException(waybillPdaDto.getBillOrgCode()+"没有查询到外场编码");
		}

		//当前部门编码
		transportPath.setCurrentOrgCode(currentOrgCode);
		//最终到达部门编号
		transportPath.setDestOrgCode(waybillPdaDto.getTargetOrgCode());
		//总重量
		transportPath.setTotalWeight(waybillPdaDto.getGoodsWeightTotal());
		//总体积
		transportPath.setTotalVolume(waybillPdaDto.getGoodsVolumeTotal());
		//总件数
		transportPath.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
		//运输类型
		transportPath.setTransportModel(waybillPdaDto.getProductCode());
		
		
		
		WaybillEntity waybill = new WaybillEntity();
		// 主键id
		waybill.setId(UUIDUtils.getUUID());
		// 运单号
		waybill.setWaybillNo(waybillPdaDto.getWaybillNo());
		// 收货联系人
		waybill.setReceiveCustomerContact(WaybillConstants.TO_MAKEUP);
		// 送货联系人
		waybill.setDeliveryCustomerContact(WaybillConstants.TO_MAKEUP);
		// 收货部门
		waybill.setReceiveOrgCode(waybillPdaDto.getStartOrg());
		// 产品编码
		waybill.setProductCode(waybillPdaDto.getProductCode());
		// 产品id
		waybill.setProductId(product.getId());
		// 提货方式
		waybill.setReceiveMethod(waybillPdaDto.getReceiveMethod());
		// 是否上门接货
		waybill.setPickupToDoor(FossConstants.YES);
		// 是否集中接货
		waybill.setPickupCentralized(FossConstants.YES);
		// 始发配载部门
		waybill.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());
		// 始发配载部门名称
		waybill.setLoadOrgName(orgInfoDto.getFirstLoadOrgName());
		// 最终配载部门
		waybill.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());
		// 最终配载部门名称
		waybill.setLastLoadOrgName(orgInfoDto.getLastLoadOrgName());
		// 是否大车直送
		waybill.setCarDirectDelivery(FossConstants.NO);
		// 货物名称
		waybill.setGoodsName(WaybillConstants.TO_MAKEUP);
		// 货物体积
		waybill.setGoodsVolumeTotal(waybillPdaDto.getGoodsVolumeTotal());
		// 货物重量
		waybill.setGoodsWeightTotal(waybillPdaDto.getGoodsWeightTotal());
		// 车牌号
		waybill.setLicensePlateNum(waybillPdaDto.getLicensePlateNum());
		// 膜包装
		waybill.setMembraneNum(waybillPdaDto.getMembrane());
		// 订单号
		waybill.setOrderNo(waybillPdaDto.getOrderNo());
		// 件数
		waybill.setGoodsQtyTotal(waybillPdaDto.getGoodsQty());
		// 货物类型
		waybill.setGoodsTypeCode(waybillPdaDto.getGoodsTypeCode());
		// 纤包装
		waybill.setFibreNum(waybillPdaDto.getFibre());
		// 根据订单号查询订单来源
		if (StringUtils.isNotEmpty(waybillPdaDto.getOrderNo())) {
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillPdaDto.getOrderNo());
			waybill.setOrderChannel(dispatchOrderEntity != null ? dispatchOrderEntity.getOrderSource() : null);
		}
		waybill.setOtherPackage(waybillPdaDto.getOtherPackageType());
		waybill.setPaidMethod(waybillPdaDto.getPaidMethod());
		waybill.setPaperNum(waybillPdaDto.getPaper());
		waybill.setCustomerPickupOrgCode(waybillPdaDto.getTargetOrgCode());
		waybill.setWoodNum(waybillPdaDto.getWood());
		/**
		 * 优惠信息
		 */
		waybill.setPromotionsFee(waybillPdaDto.getDiscountAmount());
		waybill.setPromotionsCode(waybillPdaDto.getDiscountNo());
		/**
		 * pda应收运费对应waybill的总费用totalFee
		 */
		waybill.setTotalFee(waybillPdaDto.getAmount());
		/**
		 * pda实收运费对应waybill的预付费用PrePayAmount
		 */
		waybill.setPrePayAmount(waybillPdaDto.geActualFee());
		/**
		 * 开单人工号
		 */
		waybill.setDriverCode(waybillPdaDto.getBillUserNo());
		// 是否贵重物品
		waybill.setPreciousGoods(FossConstants.NO);
		// 是否异型货物
		waybill.setSpecialShapedGoods(FossConstants.NO);
		// 预付费保密
		waybill.setSecretPrepaid(FossConstants.NO);
		// 装卸费
		waybill.setServiceFee(BigDecimal.ZERO);
		// 有效
		waybill.setActive(FossConstants.ACTIVE);
		// 是否禁行
		waybill.setForbiddenLine(FossConstants.NO);
		// 总费用
		waybill.setTotalFee(waybillPdaDto.getAmount());
		// 创建时间
		waybill.setCreateTime(waybillPdaDto.getBillStart());
		// 修改时间
		waybill.setModifyTime(waybillPdaDto.getBillStart());
		// 开单时间
		waybill.setBillTime(waybillPdaDto.getBillStart());
		// 创建人编码
		waybill.setCreateUserCode(waybillPdaDto.getBillUserNo());
		// 创建部门编码
		waybill.setCreateOrgCode(billOrgCode);
		// 币种
		waybill.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 是否整车
		waybill.setIsWholeVehicle(FossConstants.NO);
		// 整车约车报价
		waybill.setWholeVehicleAppfee(BigDecimal.ZERO);
		// 计费重量
		waybill.setBillWeight(BigDecimal.ZERO);
		/**
		 * 退款类型
		 */
		waybill.setRefundType(waybillPdaDto.getRefundType());
		/**
		 * 返单类型
		 */
		waybill.setReturnBillType(waybillPdaDto.getReturnBillType());
		// 增值服务
		List<ValueAddServiceDto> valueAddServiceDtoList = waybillPdaDto.getValueAddServiceDtoList();
		// 接货费默认值
		waybill.setPickupFee(BigDecimal.ZERO);
		if (valueAddServiceDtoList != null) {
			for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
					// 保价费设置
					waybill.setInsuranceFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
					// 代收货款设置
					waybill.setCodFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
					// 运费
					waybill.setTransportFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
					// 接货费
					waybill.setPickupFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
					// 送货费
					waybill.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
				} 
			}
		}
		// 预计到达时间
		waybill.setPreArriveTime(waybillPdaDto.getCreateTime());
		// 补录状态
		waybill.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		if(StringUtil.isNotEmpty(waybill.getWaybillNo())){
			//先将原来的运单信息删除（根据MANA-385修改）
			waybillDao.deleteWaybillByWaybillNo(waybill.getWaybillNo());
			waybillDao.addWaybillEntity(waybill);
		if(waybillManagerService.isWayBillExsits(transportPath.getWaybillNo())){
				//如果运单已经存在，则调整走货路径
				if(isUpdateTransportPath(waybillPdaDto,pending)){
//					waybillRfcTodoJobService.updateWaybillPathDetail(transportPath,waybill);
					//新增一条修改目的站的记录方便进行操作
					addReModifyRouteData(waybill);
				}
			}else{
				//否则生成走货路径
				calculateTransportPathService.createTransportPath(transportPath);
			}
		}
		return waybill;
	}
	
	private void addReModifyRouteData(WaybillEntity waybill) {
		ReModifyRouteEntity record = new ReModifyRouteEntity();
		record.setId(UUIDUtils.getUUID());
		record.setWaybillNo(waybill.getWaybillNo());
		record.setProductCode(waybill.getProductCode());
		record.setFailReason(FossConstants.NO);
		record.setCreateTime(new Date());
		record.setModifyTime(record.getCreateTime());
		reModifyRouteDao.addReModifyRouteRecord(record );
	}
	/**
	 * 更新走货路径
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-22 下午3:04:13
	 */
	private void updateTransportPath(TransportPathEntity transportPath,WaybillPdaDto waybillPdaDto, WaybillPendingEntity pending){
		//判断运单表中是否存在此单
		if(waybillManagerService.isWayBillExsits(transportPath.getWaybillNo())){
			//如果运单已经存在，则调整走货路径
			if(isUpdateTransportPath(waybillPdaDto,pending)){
				updateTransportPathForTfr(transportPath,waybillPdaDto);
			}
		}else{
			//否则生成走货路径
			calculateTransportPathService.createTransportPath(transportPath);
		}		
	}
	
	/**
	 * 调用中转接口调整走货路径
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-2-11 上午9:14:05
	 */
	private void updateTransportPathForTfr(TransportPathEntity transportPath,WaybillPdaDto waybillPdaDto){
		
		//运单货签库存DTO
		WaybillLabeledGoodStockDto labeledGoodStockDto = null;
		//查询运单所有货签库存状态
		labeledGoodStockDto = waybillRfcService.queryWaybillLabeledGoodStock(transportPath.getWaybillNo());
		//运单货签库存状态列表
	    List<LabeledGoodStockStatusDto> labeledGoodStockStatusDtos = labeledGoodStockDto.getLabeledGoodStockStatusDtos();
	    
	    if(labeledGoodStockStatusDtos!=null){	    	
	    	for(LabeledGoodStockStatusDto labeledGoodStockStatusDto:labeledGoodStockStatusDtos){	    		
	    		LabeledGoodEntity labeledGoodEntity = labeledGoodStockStatusDto.getLabeledGoodEntity();	    		
	    		try{
	    			String exceptionMsg = null;
	    			//取消是否在库的判断，只要有当前部门库存，就可以调整走货路径
//		    	    if(labeledGoodStockStatusDto.isInStock()){
		    	    	//现所处状态
		    	    	transportPath.setAction(FossConstants.ACTIVE);		    	    	
		    	    	//每件货单独生成自己的走货路径
		    	    	List<String> modefiyTransportPathSerialNos = new ArrayList<String>();
		    	    	modefiyTransportPathSerialNos.add(labeledGoodEntity.getSerialNo());
						//是否找到走货路径
						boolean findFreightRoute = false;
						//当前库存
						String currentOrgCode = labeledGoodStockStatusDto.getCurrentStockOrgCode();						
						//操作者code
						String operatorCode=waybillPdaDto.getBillUserNo();
						//操作者名称
						String operatorName="";
						if(StringUtil.isNotEmpty(waybillPdaDto.getBillUserNo())){
							operatorName=employeeService.queryEmpNameByEmpCode(operatorCode);
						}					
						
						//当前库存部门
						String stockOrgCode= labeledGoodStockStatusDto.getCurrentStockOrgCode();
						//根据外场部门编码，查询该外场的驻地可出发营业部对象
						stockOrgCode = queryStationDeliverOrgCode(stockOrgCode);
												
						//从当前库存部门生成走货路径
						transportPath.setCurrentOrgCode(stockOrgCode);
						/**
						 * 将查询部门名称从部门表中获取，避免获取部门为空的现象
						 */
						String currentOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(currentOrgCode);	//当前部门名称
						String destOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(transportPath.getDestOrgCode());		//目的部门名称
												
						try{
							//调用中转接口，调整走货路径
							calculateTransportPathService.modifyTransportPathAmendmentBillType(transportPath, modefiyTransportPathSerialNos, currentOrgCode
									,operatorCode, operatorName, StockConstants.WAYBILL_PDA_PENDING_IN_STOCK_TYPE);							
							
							findFreightRoute = true;
						}catch(TfrBusinessException e){
							LOGGER.error("TFR Exception",e);
							findFreightRoute = false;							
						}
						
						if(!findFreightRoute){//找不到走货路径 使用汽运短途再查询一次							
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT);
							try{
								//调用中转接口，调整走货路径
								calculateTransportPathService.modifyTransportPathAmendmentBillType(transportPath, modefiyTransportPathSerialNos, currentOrgCode
									,operatorCode, operatorName, StockConstants.WAYBILL_PDA_PENDING_IN_STOCK_TYPE);
								
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准城运再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT);
							try{
								//调用中转接口，调整走货路径
								calculateTransportPathService.modifyTransportPathAmendmentBillType(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName, StockConstants.WAYBILL_PDA_PENDING_IN_STOCK_TYPE);
								
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用汽运长途再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
							try{
								//调用中转接口，调整走货路径
								calculateTransportPathService.modifyTransportPathAmendmentBillType(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName, StockConstants.WAYBILL_PDA_PENDING_IN_STOCK_TYPE);
								
								findFreightRoute = true;
							}catch(TfrBusinessException e){
								findFreightRoute = false;
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准卡航再查一次
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
							try{
								//调用中转接口，调整走货路径
								calculateTransportPathService.modifyTransportPathAmendmentBillType(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName, StockConstants.WAYBILL_PDA_PENDING_IN_STOCK_TYPE);
								
								findFreightRoute = true;
							//这次如果再找不到 就会抛出异常
							}catch(TfrBusinessException e){
								findFreightRoute = false;							
							}
						}
						
						if(!findFreightRoute){//如果找不到 就用精准包裹再查一次  zhangwei
							transportPath.setTransportModel(ProductEntityConstants.PRICING_PRODUCT_PCP);
							try{
								//调用中转接口，调整走货路径
								calculateTransportPathService.modifyTransportPathAmendmentBillType(transportPath, modefiyTransportPathSerialNos, currentOrgCode
										,operatorCode, operatorName, StockConstants.WAYBILL_PDA_PENDING_IN_STOCK_TYPE);
								
								findFreightRoute = true;
							//这次如果再找不到 就会抛出异常
							}catch(TfrBusinessException e){
								LOGGER.error("TFR Exception",e);
								findFreightRoute = false;
								//异常信息
								exceptionMsg = "运单号："+ transportPath.getWaybillNo() + "；库存部门："+currentOrgName+"；最终到达部门："+destOrgName;
							}
						}
						
						//判断异常信息是否为空
						if(exceptionMsg==null){						
							/**
							 * 如果不是偏线并且不是空运时且如果最终目的部门是驻地部门且对应的外场在走货路径内
							 */
							if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(transportPath.getTransportModel())									
									&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transportPath.getTransportModel())) {								
								SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(transportPath.getDestOrgCode());							
								// 是否驻地部门
								if (saleDepartment != null && saleDepartment.checkStation()) {	
									//外场编码
									String transferCenter=	saleDepartment.getTransferCenter();
									if(StringUtils.equals(transferCenter, currentOrgCode)){
										/**
										 * 驻地营业部调用中转接口调整库区
										 */
										stockService.adjustStockToStation(transportPath.getWaybillNo(), transportPath.getDestOrgCode(), modefiyTransportPathSerialNos, operatorCode, operatorName);												
									}
									
								}
							}							
						}else{
							LOGGER.error("PDA 待补录调整走货路径出现异常 updateTransportPathForTfr"+exceptionMsg);
						}
//		    	    }
	    		}catch(Exception e){
	    			LOGGER.error("PDA 待补录调整走货路径出现异常 updateTransportPathForTfr",e);
	    		}	    		
	    	}
	    }
	    
	}
	
	/**
	 * 查询该外场的驻地可出发营业部对象
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-2-25 下午5:37:34
	 */
	public String queryStationDeliverOrgCode(String orgCode) {
		//根据外场部门编码，查询该外场的驻地可出发营业部对象
		SaleDepartmentEntity deliverDepartment = orgAdministrativeInfoComplexService.queryStationLeaveOrgByOutfieldCode(orgCode);
		if(deliverDepartment == null){
			//如果当前营业部是驻地部门，取对应外场有出发的驻地部门
			//ISSUE-2122 如果当前营业部不能做出发，也取对应外场有出发的驻地部门
			SaleDepartmentEntity dept = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if(dept == null){
				return orgCode;
			}else if(FossConstants.YES.equals(dept.getStation()) || !FossConstants.YES.equals(dept.getLeave())){
				String transferCenter = dept.getTransferCenter();
				if(transferCenter == null){
					return null;
				}
				SaleDepartmentEntity department = orgAdministrativeInfoComplexService.queryStationLeaveOrgByOutfieldCode(transferCenter);
				if(department == null){
					return orgCode;
				}else{
					return department.getCode();
				}
			}
			return orgCode;
		}else{
			return deliverDepartment.getCode();
		}
	}
	
	/**
	 * 是否要调整走货路径
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-22 下午3:52:30
	 */
	private boolean isUpdateTransportPath(WaybillPdaDto waybillPdaDto, WaybillPendingEntity pending){
		boolean flag=false;		
		if(pending!=null){	
			//提货网点是否变更
			if(pending.getCustomerPickupOrgCode()!=null && !pending.getCustomerPickupOrgCode().equals(waybillPdaDto.getTargetOrgCode())){
				flag=true;
			}
			//件数是否变更
//			else if(pending.getGoodsQtyTotal()!=null && pending.getGoodsQtyTotal().intValue()!=waybillPdaDto.getGoodsQty().intValue()){
//				flag=true;
//			}			
		}
		return flag;
	}

	/** 
	 * @remark 增加ActualFreight
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	private void updateActualFreight(WaybillEntity waybillEntity) {
		ActualFreightEntity actualFreight = new ActualFreightEntity();
		//添加地址备注
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillEntity.getOrderNo());
		if(dispatchOrderEntity != null){
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
				dispatchOrderEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
			}
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
				dispatchOrderEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
			}
		}
		// id
		actualFreight.setId(UUIDUtils.getUUID());
		// 运单号
		actualFreight.setWaybillNo(waybillEntity.getWaybillNo());
		// 货物名称
		actualFreight.setGoodsName(WaybillConstants.TO_MAKEUP);
		// 货物重量
		actualFreight.setWeight(waybillEntity.getGoodsWeightTotal());
		// 货物体积
		actualFreight.setVolume(waybillEntity.getGoodsVolumeTotal());
		// 件数
		actualFreight.setGoodsQty(waybillEntity.getGoodsQtyTotal());
		// 尺寸
		actualFreight.setDimension(WaybillConstants.UNKNOWN);
		// 保价费
		actualFreight.setInsuranceValue(BigDecimal.ZERO);
		// 包装费
		actualFreight.setPackingFee(BigDecimal.ZERO);
		// 送货费
		actualFreight.setDeliverFee(BigDecimal.ZERO);
		// 装卸费
		actualFreight.setLaborFee(BigDecimal.ZERO);
		// 代收货款
		actualFreight.setCodAmount(BigDecimal.ZERO);
		// 增值服务费
		actualFreight.setValueaddFee(BigDecimal.ZERO);
		// 公布价运费
		actualFreight.setFreight(BigDecimal.ZERO);
		// 设置始发库存部门
		String startStockOrgCode = queryStartStocksDepartmentService(waybillEntity);
		actualFreight.setStartStockOrgCode(startStockOrgCode);
		//BILL_TIME
		actualFreight.setCreateTime(waybillEntity.getBillTime());
		// 设置最终库存部门和库区编号
		waybillStockService.getEndStockCodeAndAreaCode(actualFreight, waybillEntity);
		
		if(StringUtil.isNotEmpty(actualFreight.getWaybillNo())){
			/**
			 * 2014-7-10 配合BI下拉数据 新增实际承运信息冗余表  DEFECT-3441 赵斌
			 */
			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());
			if(actualFreightEntity != null){
				ActualFreightBIEntity actualFreightBIEntity = new ActualFreightBIEntity();
				actualFreightBIEntity.setActualFreightId(actualFreightEntity.getId());
				actualFreightBIEntity.setModifyTime(new Date());
				actualFreightBIService.insert(actualFreightBIEntity);
			}
			//先将原来的运单扩展信息删除（根据MANA-385修改）
			actualFreightService.deleteActualFreightByWaybillNo(waybillEntity.getWaybillNo());
			actualFreightService.insertWaybillActualFreight(actualFreight);
		}
		
	}
	
	/** 
	 * @remark 修改货签信息
	 * @author WangQianJin
	 * @date 2014-1-17 上午11:10:40
	 */
	private void updateLabeledGood(WaybillPdaDto waybillPdaDto) {
		//先将原来的流水号信息删除（根据MANA-385修改）
		if(StringUtil.isNotEmpty(waybillPdaDto.getWaybillNo())){			
			labeledGoodDao.deleteLabeledGoodByWaybillNo(waybillPdaDto.getWaybillNo());
			// 循环添加货签信息
			for (int i = 1; i <= waybillPdaDto.getGoodsQty(); i++) {
				LabeledGoodEntity labeledGood = new LabeledGoodEntity();
				// id
				labeledGood.setId(UUIDUtils.getUUID());
				// 运单号
				labeledGood.setWaybillNo(waybillPdaDto.getWaybillNo());
				// 有效
				labeledGood.setActive(FossConstants.ACTIVE);
				// 流水号
				labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
				// 创建时间
				labeledGood.setCreateTime(waybillPdaDto.getCreateTime());
				// 修改时间
				labeledGood.setModifyTime(waybillPdaDto.getCreateTime());
				// 开单时间
				labeledGood.setBillTime(waybillPdaDto.getBillStart());
				// 初始化
				labeledGood.setInitalVale(FossConstants.YES);
				labeledGoodDao.insertSelective(labeledGood);			
			}
		}		
	}
	
	/**
	 * 更新货签信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-20 下午2:30:20
	 */
	private void updateLabelGoodPda(WaybillPendingEntity waybillPendingEntity) {
		if(StringUtil.isNotEmpty(waybillPendingEntity.getWaybillNo())){
			//先将原来的货签信息删除（根据MANA-385修改）
			labeledGoodPDAService.updatePdaLabelByWaybillNo(waybillPendingEntity.getWaybillNo());
			// 循环添加货签信息
			for (int i = 1; i <= waybillPendingEntity.getGoodsQtyTotal(); i++) {
				// new货签对象
				LabeledGoodPDAEntity labeledGood = new LabeledGoodPDAEntity();
				// id
				labeledGood.setWaybillPDAId(waybillPendingEntity.getId());
				// 运单号
				labeledGood.setWaybillNo(waybillPendingEntity.getWaybillNo());
				// 流水号
				labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
				// 开单时间
				labeledGood.setBillTime(waybillPendingEntity.getCreateTime());
				// 有效
				labeledGood.setActive(FossConstants.ACTIVE);
				// 生效时间
				labeledGood.setCreateTime(waybillPendingEntity.getCreateTime());
				// 失效时间
				labeledGood.setModifyTime(waybillPendingEntity.getCreateTime());
				// 创建人编码
				labeledGood.setCreateUserCode(waybillPendingEntity.getCreateUserCode());
				// 增加
				labeledGoodPDAService.insertSelective(labeledGood);				
			}
		}
	}
	
	/**
	 * 获取CRM营销活动信息(PDA外部调用)
	 * @创建时间 2014-4-16 下午7:46:54   
	 * @创建人： WangQianJin
	 */
	public CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo pdaParamDto){
		pdaParamDto.setActivityCategory(DictionaryValueConstants.BUSINESS_LTT);
		pdaParamDto.setActive(FossConstants.ACTIVE);
		return waybillManagerService.getActiveInfoListNoIndurstry(pdaParamDto);
	}
	
	/**
	 * 快递获取CRM营销活动信息(PDA外部调用)
	 * @创建时间 2014-4-16 下午7:46:54   
	 * @创建人： WangQianJin
	 */
	public CrmActiveInfoDto getActiveInfoListExpress(CrmActiveParamVo pdaParamDto){
		pdaParamDto.setActivityCategory(DictionaryValueConstants.BUSINESS_EXPRESS);
		pdaParamDto.setActive(FossConstants.ACTIVE);
		return waybillManagerService.getActiveInfoList(pdaParamDto);
	}

	/**
	 * 针对大客户数据没有修改过记录可以直接激活
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-15 09:56:39
	 * @param eWaybillConditionDto
	 * @return
	 */
	@Override
	public ResultDto batchGenerateActiveEWaybillByPda(EWaybillConditionDto eWaybillConditionDto) {
		ResultDto resultDto = new ResultDto();
		try{
			ewaybillService.batchGenerateActiveEWaybillByPda(eWaybillConditionDto);
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("");
		}catch(Exception e){
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("错误详情"+e.getMessage());
		}
		return resultDto;
	}

	/**
	 * 针对散客信息什么的可以修改的数据进行激活
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-15 09:56:39
	 * @param eWaybillConditionDto
	 * @return
	 */
	@Override
	public ResultDto activeEWaybillByPda(WaybillExpressPdaDto waybillExpressPdaDto, String billOrgCode) {
		ResultDto resultDto = new ResultDto();
		try{
			ewaybillService.activeEWaybillByPda(waybillExpressPdaDto, billOrgCode);
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("");
		}catch(Exception e){
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("错误详情"+e.getMessage());
		}
		return resultDto;
	}
	
	/**
	 * 退回对应的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-15 09:57:38
	 */
	@Override
	public ResultDto returnEWaybillByPda(EWaybillConditionDto eWaybillConditionDto) {
		return ewaybillService.returnEWaybillByPda(eWaybillConditionDto);
	}

	/**
	 * 图片运单撤销
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService#cancelWaybillPictureByPDA(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultDto cancelWaybillPictureByPDA(String waybillNo, String driverCode) {
		ResultDto resultDto = new ResultDto();
		if (StringUtils.isEmpty(waybillNo)) {
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("运单号不能为空！");
			return resultDto;
			//throw new PdaInterfaceException("运单号不能为空！");
		}
		if (StringUtils.isEmpty(driverCode)) {
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("司机不能为空！");
			return resultDto;
			//throw new PdaInterfaceException("司机不能为空！");
		}
		WaybillPictureEntity waybillPictureEntity  = waybillPictureDao.queryWaybillPictureByWaybillNo(waybillNo);
		if(waybillPictureEntity == null){
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("运单不存在！");
			return resultDto;
			//throw new PdaInterfaceException("运单不存在！");
		}
		if(!WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(waybillPictureEntity.getPendgingType())){
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("不是待录入的运单不允许撤销！");
			return resultDto;
			//throw new PdaInterfaceException("不是待录入的运单不允许撤销！");
		}
		waybillPictureEntity.setModifyTime(new Date());
		waybillPictureEntity.setModifyUserCode(driverCode);
		waybillPictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL);
		waybillPictureDao.updateWaybillPictureByEntity(waybillPictureEntity);
		
		resultDto.setCode(ResultDto.SUCCESS);
		resultDto.setMsg("撤销成功");
		return resultDto;
	}

	/**
	 * 提交图片开单
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService#submitWaybillPictureByPDA(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto)
	 */
/*	@Override
	public ResultDto submitWaybillPictureByPDA(
			WaybillPicturePdaDto waybillPicturePdaDto) {
		if (waybillPicturePdaDto == null) {
			throw new PdaInterfaceException("运单不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getWaybillNo())) {
			throw new PdaInterfaceException("运单号不能为空！");
		} else {
			//判断运单号是否已存在图片开单表
			WaybillPictureEntity waybillPictureEntity1  = waybillPictureDao.queryWaybillPictureByWaybillNo(waybillPicturePdaDto.getWaybillNo());
			//排除本身之外，如果运单号重复则不能提交
			if (waybillPictureEntity1 != null && !WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(waybillPictureEntity1.getPendgingType())) {
				throw new PdaInterfaceException("运单号已存在！上传工号:" + waybillPictureEntity1.getDriverCode());
			}
			//判断运单号是否已录入或者已开单
			if (waybillManagerService.isWayBillExsits(waybillPicturePdaDto.getWaybillNo()) || waybillManagerService.isWayBillPendingExsits(waybillPicturePdaDto.getWaybillNo())) {
				throw new PdaInterfaceException("此运单号已经开单或暂存！");
			}
		}
		**********以下代码为已注释代码，如需还原原分单逻辑，请依旧注释*********
		if (!StringUtils.isEmpty(waybillPicturePdaDto.getOrderNo())) {
			//判断订单号是否存在图片开单表
			SearchPictureWaybillCondiction condiction = new SearchPictureWaybillCondiction();
			condiction.setOrderNo(waybillPicturePdaDto.getOrderNo());
			List<String> pendgingTypes = new ArrayList<String>();
			pendgingTypes.add(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
			pendgingTypes.add(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED);
			pendgingTypes.add(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING);
			pendgingTypes.add(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);
			pendgingTypes.add(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
			condiction.setWaybillPictureType(pendgingTypes);
			List<SearchPictureVo> pictures = waybillPictureDao.searchPictureWaybillByCondiction(condiction);
			if (pictures != null && pictures.size() > 0) {
				throw new PdaInterfaceException("输入的订单号已经被使用！");
			}
			
			if (waybillManagerService.isWayBillExsitsByOrderNo(waybillPicturePdaDto.getOrderNo()) || waybillManagerService.isWayBillPendingExsitsByOrderNo(waybillPicturePdaDto.getOrderNo())) {
				throw new PdaInterfaceException("输入的订单号已经开单或暂存！");
			}
		}
		**********以上代码为已注释代码，如需还原原分单逻辑，请依旧注释*********
		if (StringUtils.isEmpty(waybillPicturePdaDto.getBillOrgCode())) {
			throw new PdaInterfaceException("司机所在车队部门不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getDriverCode())) {
			throw new PdaInterfaceException("司机编号不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getBaiDuId())) {
			throw new PdaInterfaceException("百度ID不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getReceiveOrgCode())) {
			throw new PdaInterfaceException("收货部门不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getFilePath())) {
			throw new PdaInterfaceException("图片路劲不能为空！");
		}
		
		// 查找对应的顶级车队
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(waybillPicturePdaDto.getBillOrgCode());
		if(topFleet == null){
			LOGGER.info("图片录入：通过司机所在车队部门编码未找到对应的顶级车队,部门编码："+waybillPicturePdaDto.getBillOrgCode());
			throw new PdaInterfaceException("通过司机所在车队部门编码未找到对应的顶级车队");
		}
		// 司机所在车队部门
		MotorcadeEntity motorcadeEntity = motorcadeService.queryMotorcadeByCode(topFleet.getCode());
		// 车队对应的集中开单组编码
		String billOrgCode;
		if (motorcadeEntity != null) {
			if (StringUtils.isNotEmpty(motorcadeEntity.getServeBillTerm())) {
				billOrgCode = motorcadeEntity.getServeBillTerm();
			} else {
				LOGGER.info("图片录入：通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
				throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
			}
		} else {
			LOGGER.info("图片录入：通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
			throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表中找到有效记录");
		}
		WaybillPictureEntity waybillPictureEntity = new WaybillPictureEntity();
		BeanUtils.copyProperties(waybillPicturePdaDto, waybillPictureEntity);
		//设置所属机构
		waybillPictureEntity.setBelongOrgCode(billOrgCode);
		//设置本属开单组
		waybillPictureEntity.setLocalBillGroupCode(billOrgCode);
		//分配运单
		LOGGER.info("运单本属开单组:"+billOrgCode);
		// 判断是否展会货 是的 由本地开单（本地开单还有条件，如果是展会货不满足条件 怎么处理？），不参与异地开单规则; 不是的 走以前业务逻辑；
		if (!FossConstants.YES.equals(waybillPictureEntity.getIsExhibitCargo())) {
			try {
			distributeWaybill(waybillPictureEntity,waybillPicturePdaDto);
			} catch (Exception e) {
				LOGGER.info("运单分配规则有异常！message:"+e.getMessage());
			}
			LOGGER.info("结束分配运单:"+billOrgCode+"-->"+waybillPictureEntity.getBelongOrgCode());
			if(billOrgCode.equals(waybillPictureEntity.getBelongOrgCode())){
				//本地开单
				waybillPictureEntity.setLocal(FossConstants.YES);
			}else{
				//异地开单
				waybillPictureEntity.setLocal(FossConstants.NO);
			}
		} else {
			// 有展会货图票 不参与异地开货规则 需不需要 判断 车队对应的集中开单组编码 不等于 当前PDA录单 所属机构信息
			waybillPictureEntity.setLocal(FossConstants.YES);
		}
//		waybillPictureEntity.setBelongOrgCode("");
		//waybillManagerService.submitWaybillPictureByPDA(waybillPictureEntity);
		
		submitWaybillPictureByPDA(waybillPictureEntity);
		ResultDto resultDto = new ResultDto();
		resultDto.setCode(ResultDto.SUCCESS);
		return resultDto;
	}*/
	/**
	 * 
	* @Description: 分配运单
	* @author hbhk 
	* @date 2015-6-11 下午5:25:18 
	  @param wp
	 */
	@SuppressWarnings("unchecked")
	private void distributeWaybill(WaybillPictureEntity wp,WaybillPicturePdaDto waybillPicturePdaDto){
		/**
		 * 分配规则
		 * 1是否集中集中开单组（调用综合获取开单组）
		 * 2现付
		 * 3是否是退回运单
		 * 4开单量(如果当前开单量达上限,差量大的优先)
		 */
		//运单原所属开单组
		LOGGER.info("进入分配运单");
		String billOrgCode = wp.getBelongOrgCode();
		
		String wayBillNo = wp.getWaybillNo();
		String specialCustomer = waybillPicturePdaDto.getSpecialCustomer();
		LOGGER.info("是否特殊客户："+wayBillNo+":"+specialCustomer);
		if(FossConstants.YES.equals(specialCustomer)){
			LOGGER.info("特殊客户"+wayBillNo);
			return;
		}
		
		//2判断是否现付
		String cashPayFlag = wp.getCashPayFlag();
		if(FossConstants.YES.equals(cashPayFlag)){
			LOGGER.info("当前部门是现金支付设置原有部门:"+wayBillNo+":"+billOrgCode);
			return;
		}
		//3是否是退回运单
		WaybillPictureEntity queryEntity = new WaybillPictureEntity();
		queryEntity.setWaybillNo(wp.getWaybillNo());
		queryEntity.setActive(FossConstants.ACTIVE);
		queryEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN);
		List<WaybillPictureEntity> oldWaybillEntityLst = waybillPictureDao
				.queryWaybillPictureByEntity(queryEntity);
		if(oldWaybillEntityLst!=null && !oldWaybillEntityLst.isEmpty()){
			//设置原有所属机构
			LOGGER.info("当前运单为退回,设置原有开单组:"+wayBillNo+":"+billOrgCode);
			wp.setBelongOrgCode(oldWaybillEntityLst.get(0).getBelongOrgCode());
			return;
		}
		
		newDistributionRegularByDbProc(wp);
		
		/*//调用综合获取开单组
		List<CenterBillOrgDto> centerbillOrList = getCenterBillOrg(billOrgCode);
		if(centerbillOrList == null || centerbillOrList.isEmpty()){
			LOGGER.info("没有集中开单组");
			return;
		}
				
		Map<String, CenterBillOrgDto> centerBillOrgList = new HashMap<String, CenterBillOrgDto>();
		for (CenterBillOrgDto cbo : centerbillOrList) {
			centerBillOrgList.put(cbo.getBillOrgCode(), cbo);
		}
		LOGGER.info("当前所有开单组:"+centerBillOrgList.keySet());
		//1是否集中集中开单组
		if(!centerBillOrgList.containsKey(billOrgCode)){
			LOGGER.info("当前部门不是集中开单组:"+wayBillNo+":"+billOrgCode);
			return;
		}
		//4开单量  获取当前开单组的开单量（未开单量）
		CenterBillOrgDto cbo = centerBillOrgList.get(billOrgCode);
		int billCount = cbo.getBillCount();
		//计算出当前需要设置的开单组
		CenterBillOrgDto centerBillOrgBillCount = getCenterBillOrgBillCount(billOrgCode,billCount,centerBillOrgList);
		if(centerBillOrgBillCount != null){
			//设置开单组
			String cBillOrgCode = centerBillOrgBillCount.getBillOrgCode();
			LOGGER.info("当前分配部门:"+wayBillNo+":"+cBillOrgCode);
			wp.setBelongOrgCode(cBillOrgCode);
		}*/
		
	}
	
	/**
	 * 数据库分配规则
	 * @author Foss-278328-hujinyang
	 * @date 2016-1-28
	 * @param wp	图片开单实体信息
	 */
	private void newDistributionRegularByDbProc(WaybillPictureEntity wp) {
		
		//当前的开单组
		String billOrgCode = wp.getBelongOrgCode();
		
		Map<String, Object>  params = new HashMap<String, Object>();
		params.put("pendgingType", WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
		params.put("orgCode", billOrgCode);
		
		LOGGER.info("进入数据库分配规则方法中，当前开单组为："+billOrgCode);
		
		List<SubCenterBillOrgDto> subCenterBillOrgDtos = waybillPictureDao.queryDistributeInfoBySql(params);
		if(!CollectionUtils.isEmpty(subCenterBillOrgDtos) && subCenterBillOrgDtos.size() > 0){
			
			//获取分配的实体
			SubCenterBillOrgDto billOrg = subCenterBillOrgDtos.get(0);
			
			//设置当前处理的运单号  用于日志输出
			billOrg.setWaybillNo(wp.getWaybillNo());
			
			//输出详细日志
			LOGGER.info(billOrg.getCurrentInfo());
			//设置当前开单组
			wp.setBelongOrgCode(billOrg.getBillOrgCode());
			
		}else{
			LOGGER.info("当前开单组为："+billOrgCode+" 数据库分配规则方法中,未查到待补录量！");
		}
		
	}

	/**
	 * 
	* @Description:获取集中开单组
	* @author hbhk 
	* @date 2015-6-12 下午2:36:31 
	  @return
	 */
	private List<CenterBillOrgDto>  getCenterBillOrg(String billOrgCode){
		List<FocusRecordManagementEntity> mapDtos = focusRecordManagementService.queryAllBillingGroup();
		if(mapDtos != null){
			List<CenterBillOrgDto> cboList = new ArrayList<CenterBillOrgDto>();
			
			boolean isInCurrentTime = false;
			
			for (FocusRecordManagementEntity mapDto : mapDtos) {
				
				
				//获取开单组的起止开单时间
				Date sDate =	mapDto.getStartDate();
				Date eDate =	mapDto.getEndDate();
				String goupCode  = mapDto.getBillingGroupCode();
				
				String ssDate  = DateUtils.convert(sDate, DateUtils.TIME_FORMAT);
				String seDate  = DateUtils.convert(eDate, DateUtils.TIME_FORMAT);
				String scDate  = DateUtils.convert(new Date(), DateUtils.TIME_FORMAT);
				
				
				if(StringUtils.isBlank(ssDate ) ||StringUtils.isBlank(seDate) ){
					String groupName  = mapDto.getBillingGroupName();
					LOGGER.info("PdaWaybillService----> getCenterBillOrg 订单分配规则中，通过综合获取"+groupName+"("+goupCode+")起止时间未设定,请设置该开单组的开单起止时间！");
				}else{
				//只查找当前开单时间内的开单组
				if(ssDate.compareTo(scDate) <=0 && scDate.compareTo(seDate) <=0)
				{
						
						if(StringUtils.isBlank(goupCode)){
							LOGGER.info("PdaWaybillService----> getCenterBillOrg 订单分配规则中，通过综合接口获取集中开单组的运单号有空值!");
							continue;
						}
						
						//判定集中开单组中是否含有当前开单组
						if(billOrgCode.equals(goupCode)){
							isInCurrentTime = true;
						}
						
					CenterBillOrgDto cbo = new CenterBillOrgDto();
						cbo.setBillOrgCode(goupCode);
					cbo.setBillCount(Integer.parseInt(mapDto.getBillingAmount()));
					cboList.add(cbo);
				}			
			}
				
		}
			
			if(isInCurrentTime){
				return cboList;
			}else{
				LOGGER.info("PdaWaybillService----> getCenterBillOrg 订单分配规则中，当期开单时间不在本地开单组设置的开单起止时间内，故设置集中开单组为null,放回本地开单组!");
		}
		
	}
		return null;
	}
	
	/**
	 * 
	* @Description:计算出当前需要设置的开单组
	* @author hbhk 
	* @date 2015-6-12 下午2:40:29 
	  @param billCount
	  @return
	 */
	private CenterBillOrgDto getCenterBillOrgBillCount(String belongOrgCode,int billCount,Map<String, CenterBillOrgDto> centerbillOrList){
		//获取开单组与未开单量
		Map<String, Object>  params = new HashMap<String, Object>();
		params.put("active", FossConstants.ACTIVE);
		params.put("pendgingType", WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
		params.put("orgCodes", centerbillOrList.keySet());
		List<CenterBillOrgDto> centerBillOrgDtos = waybillPictureDao.queryPicBillOrgAndNotCount(params);
		if(centerBillOrgDtos==null || centerBillOrgDtos.isEmpty()){
			LOGGER.info("未查询到对应的开单部门");
			return null;
		}
		Double subBillCount = null;
		CenterBillOrgDto currentcbo =null;
		List<String> picBillOrgList = new ArrayList<String>();
		for (String code: centerbillOrList.keySet()) {
			picBillOrgList.add(code);
		}
//		List<String> picBillOrPendinggList = new ArrayList<String>();
		
		//数据库中中查询的待补录量
		CenterBillOrgDto pendingCboFromDb = null;
		
		for (CenterBillOrgDto cbo : centerBillOrgDtos) {
			String orgCode = cbo.getBillOrgCode();
//			picBillOrPendinggList.add(orgCode);
			
			if(StringUtils.equals(belongOrgCode, orgCode)){
				pendingCboFromDb = cbo;
			}
			
			if(centerbillOrList.keySet().contains(orgCode)){
				picBillOrgList.remove(orgCode);
			}
		}
		
		//1、优先处理本地开单组处理
		if(pendingCboFromDb == null){
			CenterBillOrgDto billOrg= new CenterBillOrgDto();
			billOrg.setBillOrgCode(belongOrgCode);
			return billOrg;
		}else{//查阅本地待开量是否达到上限
			Double count =  Double.parseDouble(pendingCboFromDb.getBillCount().toString());
			//判断对应开单组 是否达到上限
			if(count < billCount){
				return pendingCboFromDb;
			}
		}
		
		//2、处理全国没有开单的机构
		if(centerBillOrgDtos.size() < centerbillOrList.keySet().size() 
				&& !picBillOrgList.isEmpty()){
			currentcbo =  centerbillOrList.get(picBillOrgList.get(0));
			return currentcbo;
		}
		
		//3、处理比例各个待补录量/开单组的补录量  百分比取最小的值
		for (CenterBillOrgDto cbo : centerBillOrgDtos) {
			String orgCode = cbo.getBillOrgCode();
			//未开单量
			Double count =  Double.parseDouble(cbo.getBillCount().toString());
			CenterBillOrgDto billOrg = centerbillOrList.get(orgCode);
			if(billOrg == null){
				continue;
			}
			Double currBillCount = Double.parseDouble(billOrg.getBillCount().toString());
			
			//不处理已经开单满的
			if(count>=currBillCount){
				continue;
			}
			
			//百分比
			Double subCount = count/currBillCount;
			if(subBillCount == null){
				subBillCount =  subCount;
				currentcbo = cbo;
			}
			LOGGER.info("开单组:"+orgCode+"待录入量与开单量百分比:"+subCount);
			//分配给百分比最低的一个
			if(subCount < subBillCount){
				subBillCount =  subCount;
				currentcbo = cbo;
			}
		}
		return currentcbo;
				}				
		
	/**
	 * app提交上传的图片信息
	 * 
	 * @return
	 */
	public void submitWaybillPictureByPDA(WaybillPictureEntity waybillPictureEntity){
		List<MutexElement> mutexes = new ArrayList<MutexElement>();	
		
		/**
		 * 创建运单互斥对象
		 */
		MutexElement mutexElement = new MutexElement(waybillPictureEntity.getWaybillNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
		mutexes.add(mutexElement);
		/**
		 * 判断订单号是否为空
		 */
		if(waybillPictureEntity.getOrderNo()!=null && !"".equals(waybillPictureEntity.getOrderNo())){
			MutexElement mutexElementOrder = new MutexElement(waybillPictureEntity.getOrderNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
			mutexes.add(mutexElementOrder);
		}
		//互斥锁定
		boolean isLocked = false;
		try{
			isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
		}catch(Exception e){
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		if(!isLocked){
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		try{
			//判断运单号是否已存在图片开单表
			WaybillPictureEntity waybillPictureEntity1  = waybillPictureDao.queryWaybillPictureByWaybillNo(waybillPictureEntity.getWaybillNo());
			//排除本身之外，如果运单号重复则不能提交
			if (waybillPictureEntity1 != null && 
					!WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(waybillPictureEntity1.getPendgingType()) &&
					!WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(waybillPictureEntity1.getPendgingType())) {
				throw new PdaInterfaceException("运单号已存在！");
			}
			//判断运单号是否已录入或者已开单
			if (waybillManagerService.isWayBillExsits(waybillPictureEntity.getWaybillNo()) || waybillManagerService.isWayBillPendingExsits(waybillPictureEntity.getWaybillNo())) {
				throw new PdaInterfaceException("此运单号已经开单或暂存！");
			}

			/**
			 * 判断订单号是否为空
			 */
			if(waybillPictureEntity.getOrderNo()!=null && !"".equals(waybillPictureEntity.getOrderNo())){
				//根据订单号获取运单信息
				WaybillPictureEntity entity = new WaybillPictureEntity();
				entity.setActive(FossConstants.YES);
				entity.setOrderNo(waybillPictureEntity.getOrderNo());
				List<WaybillPictureEntity> lists = waybillPictureDao.queryWaybillPictureByEntity(entity);	
				/**
				 * 判断运单是否为空，如果不为空，说明此订单已存在，否则创建订单互斥对象
				 */
				if(lists != null && lists.size() > 0){
					for(WaybillPictureEntity entiry : lists){
						if(!WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(entiry.getPendgingType()) &&
								!WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(entiry.getPendgingType())){
							throw new PdaInterfaceException("输入的订单号已经开单或暂存！");
						}
					}
					//WaybillPictureEntity entiry = lists.get(0);
				}
				if (waybillManagerService.isWayBillExsitsByOrderNo(waybillPictureEntity.getOrderNo()) || waybillManagerService.isWayBillPendingExsitsByOrderNo(waybillPictureEntity.getOrderNo())) {
					throw new PdaInterfaceException("输入的订单号已经开单或暂存！");
				}
			}
			
			waybillManagerService.submitWaybillPictureByPDA(waybillPictureEntity);
		}finally{
			//释放锁
			businessLockService.unlock(mutexes);
		}	
		
	}

	/**
	 * 手机端反馈已接 删除推送信息
	 * gcl 14.12.4
	 * @return
	 */
	public int pushMessageStatus(String waybillCode){
		WaybillPushMessageEntity e=new WaybillPushMessageEntity();
		e.setWaybillCode(waybillCode);
		return waybillPictureDao.delWaybillPushMessage(e);
	}

	/**
	 * 下拉运单
	 * @throws Exception 
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService#queryWaybillPictureByPDA(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto)
	 */
	@Override
	public QueryPictureWaybillResultDto queryWaybillPictureByPDA(
			WaybillPicturePdaDto waybillPicturePdaDto) throws Exception {
		QueryPictureWaybillResultDto resultDto = new QueryPictureWaybillResultDto();
		if (StringUtils.isEmpty(waybillPicturePdaDto.getDriverCode())) {
			throw new PdaInterfaceException("司机编号不能为空！");
		}
		try{
			//设置用户信息到session
			UserEntity userEntity = userService.queryUserByEmpCode(waybillPicturePdaDto.getDriverCode());
			UserContext.setCurrentUser(userEntity);
			
			List<WaybillPictureLabelDto> barcodePrintLabelDtoLst = new ArrayList<WaybillPictureLabelDto>();
			//查询出司机当天的未打标签运单
			WaybillPictureDto waybillPictureDto = new WaybillPictureDto();
			BeanUtils.copyProperties(waybillPicturePdaDto, waybillPictureDto);
			waybillPictureDto.setActive(FossConstants.ACTIVE);
			Date todayStart = new Date();
			todayStart.setHours(0);
			todayStart.setMinutes(0);
			todayStart.setSeconds(0);
			Date todayEnd = new Date();
			todayEnd.setDate(todayEnd.getDate() + 1);
			todayEnd.setHours(0);
			todayEnd.setMinutes(0);
			todayEnd.setSeconds(0);
			
			waybillPictureDto.setCreateTimeEnd(todayEnd);
			waybillPictureDto.setCreateTimeStart(todayStart);
			waybillPictureDto.setLimit(-1);
			List<WaybillPictureEntity> waybillPictureLst  = (List<WaybillPictureEntity>) waybillPictureDao.queryWaybillPictureByDtoPage(waybillPictureDto);
			for(int i = 0; i < waybillPictureLst.size(); i++){
				WaybillPictureLabelDto item = new WaybillPictureLabelDto();
				WaybillPictureEntity waybillPicEntity = waybillPictureLst.get(i);
				String waybillNo = waybillPicEntity.getWaybillNo();
				WaybillEntity waybillBean = null;
				//如果离线未查到数据则查 waybill表
				WaybillPendingEntity pendingEntity = waybillPendingService.queryPendingByNo(waybillNo);
				
				//1、先按照提货网点看对应的站点属性标记是否需要追加打★
				//2、走货路由是否经过青岛如果经过则配载部门名称追加★符号
				
				//增加PDA补录的判断，当运单是PDA单的时候不应该查询该信息的
				if (pendingEntity != null) {
					waybillBean = new WaybillEntity();
					if(StringUtils.isNotEmpty(pendingEntity.getPendingType())){
						//如果是PDA补录的，并且数据为空，则不允许他进行打印，因为PDA单的数据不全
						if("PDA_PENDING".equals(pendingEntity.getPendingType())){
							waybillBean = waybillDao.queryWaybillByNo(waybillNo);
							if(waybillBean == null){
								return null;
							}
						}
					}
					BeanUtils.copyProperties(pendingEntity, waybillBean);
				}else {
					// 根据运单号查询 运单提交后基本信息
					waybillBean = waybillDao.queryWaybillByNo(waybillNo);
					// 查询该运单是否为作废运单
				}
				
				//如果waybill为空，未录入；不为空，则分为打过标签和未打标签
				if(waybillBean != null){
					List<PrintInfoEntity> printInfoLst = printInfoDao.queryByWaybillId(waybillBean.getId(), waybillBean.getWaybillNo());
					//如果为空，则表示未打过标签
					if(printInfoLst == null || printInfoLst.size() <= 0){
						//查询标签信息
						BarcodePrintLabelDto printLabelDto = labelPrintInfoService.getWaybillInfo(waybillPicEntity.getWaybillNo(),null);
						BeanUtils.copyProperties(printLabelDto, item);
					}
//					BeanUtils.copyProperties(waybillPicEntity, waybillBean);
					item.setWaybillBean(waybillBean);
				}
				item.setWaybillPictureEntity(waybillPicEntity);
				barcodePrintLabelDtoLst.add(item);
			}
			resultDto.setCode(QueryPictureWaybillResultDto.SUCCESS);
			resultDto.setWaybillPictureLabelDtoLst(barcodePrintLabelDtoLst);
		}catch(Exception e){
			LOGGER.error("司机运单下拉异常", e);
			throw e;
		}
		return resultDto;
	}
	
	
    /**
     * 图片开单，PDA外场补录体积、重量自动计算价格、提交生成运单
     * @author foss-PanGuoYang  雷锋
     * @throws Exception 
     * @date 2014-10-01
     */
	@Override
	public ResultDto autoSubmitWaybillByPDA(WoodenRequirePdaDto woodenPdaDto) throws Exception{
		LOGGER.error("进入autoSubmitWaybillByPDA===================================================================");
		    //运单号
		String waybillNo= "";
		if(woodenPdaDto != null){
			waybillNo = woodenPdaDto.getWaybillNo();
		}
		//待补录运单信息
		WaybillPendingEntity pendingEntity=null;
			
		try{
			if(woodenPdaDto ==null){
				throw new WaybillValidateException("外场补录重量体积信息为空！");
			}
			//待补录运单信息
			pendingEntity =waybillPendingDao.queryPendingByNo(waybillNo);
			if(pendingEntity==null){
				return null ;
			}
			
			WaybillEntity waybill =waybillDao.queryWaybillByNo(woodenPdaDto.getWaybillNo());
			if(waybill==null){
				throw new WaybillValidateException("运单信息为空");
			}
			/**
			 * 根据单号查出实际承运表信息
			 * dp-foss-dongjialing
			 * 225131
			 */
			ActualFreightEntity acf = actualFreightService.queryByWaybillNo(waybillNo);
			if(acf==null){
				throw new WaybillValidateException("实际承运信息为空");
			}
			/**
			 * 如果已开单，不作任何处理
			 */
			if(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybill.getPendingType()) 
			        || 
			   WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybill.getPendingType())){
				return null;
			}
			
			if(null!=pendingEntity.getOrderNo()){
				// 根据订单号查询运单基本信息
				WaybillEntity waybillEntity = waybillDao.queryWaybillByOrderNo(pendingEntity.getOrderNo());
				if(waybillEntity!=null 
						&& 
				   (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType())
						 ||
				    WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntity.getPendingType()))
				){
					throw new WaybillValidateException("该订单号已开单");
				}
				/*CrmOrderQueryDto queryVo = new CrmOrderQueryDto();
				queryVo.setOrderNumber(pendingEntity.getOrderNo());		
				queryVo.setPageNum(1);
				queryVo.setPageSize(5);
				OrgAdministrativeInfoEntity org = waybillManagerService.queryOrgInfoByCode(pendingEntity.getReceiveOrgCode());
				if(org!=null){
					queryVo.setSalesDept(org.getUnifiedCode());
				}else{
					throw new WaybillValidateException("收货营业部的标杆编码不能为空"); 
				}
				//获取订单信息
				CrmOrderInfoDto CrmOrderInfoDto= crmOrderService.searchOrder(queryVo);
				if(CrmOrderInfoDto!=null){
					List<CrmOrderInfo> crmOrderInfoList = CrmOrderInfoDto.getCrmOrderInfoList();
					if(CollectionUtils.isNotEmpty(crmOrderInfoList)){
						CrmOrderInfo crmOrderInfo =crmOrderInfoList.get(0);
						String status = crmOrderInfo.getOrderStatus();
						if(WaybillConstants.CRM_ORDER_GOBACK.equals(status)){
							throw new WaybillValidateException("该订单号已退回");
						}
					}
				}*/
			}
			
			WaybillDto  recordWaybillDto =new WaybillDto();
			
			//包装信息重新赋值
			woodenPdaDto = getWoodenRequirePdaDto(woodenPdaDto);
			
			//根据运单号查询d待打木架信息
			WoodenRequirePendingEntity  woodenRequirePend =woodenRequirePendingDao.queryWoodenRequireByNo(waybillNo);
			//补录货物体积总量
			pendingEntity.setGoodsWeightTotal(woodenPdaDto.getGoodsWeightTotal().setScale(1, BigDecimal.ROUND_HALF_UP));
			pendingEntity.setGoodsVolumeTotal(woodenPdaDto.getGoodsVolumeTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
			//运单基本信息
			WaybillEntity waybillEntity = waybillEntity(pendingEntity);
			//携带免费接货以便计算运费add by 306486 
			pendingEntity.setFreePickupGoods(waybill.getFreePickupGoods());
			//待处理运单费用明细
			List<WaybillCHDtlPendingEntity> chargePendList = waybillCHDtlPendingDao.queryCHDtlPendingByNo(waybillNo);
			 
			recordWaybillDto.setOldWaybillNo(pendingEntity.getWaybillNo());
			
			//图片信息
			WaybillPictureEntity waybillPictureEntity =getWaybillPictureEntity(woodenPdaDto);
			
			//打木架信息
			WoodenRequirementsEntity  woodenEntity = getWoodenRequirementsEntity(woodenPdaDto,pendingEntity,woodenRequirePend);
			if(woodenEntity!=null){
				String boxGoodsSize=woodenEntity.getBoxGoodsSize();
				String standGoodsSize =woodenEntity.getStandGoodsSize();
				if(StringUtil.isNotBlank(boxGoodsSize) && StringUtil.isNotBlank(standGoodsSize)){
					waybillEntity.setGoodsSize(woodenEntity.getBoxGoodsSize()+"+"+woodenEntity.getStandGoodsSize());	
				}else if(StringUtil.isNotBlank(boxGoodsSize)){
					waybillEntity.setGoodsSize(boxGoodsSize);
				}else if(StringUtil.isNotBlank(standGoodsSize)){
					waybillEntity.setGoodsSize(standGoodsSize);
				}
			}
			if(CollectionUtils.isNotEmpty(woodenPdaDto.getWoodenRequireEntityLis())){
				List<WoodenRequireEntity> woodeList= woodenPdaDto.getWoodenRequireEntityLis();
				if(CollectionUtils.isNotEmpty(woodeList)){
					StringBuilder noPackGoodsSize=new StringBuilder("");
					for(WoodenRequireEntity woode:woodeList){
						String packType = woode.getPackType();
						//件数
						BigDecimal  num = new BigDecimal(woode.getGoodsNum());
						//长
						BigDecimal length = woode.getLength();
						//宽
						BigDecimal width = woode.getWidth();
						//高
						BigDecimal height = woode.getHeight();
						//没打木箱、 木架
						if(WaybillConstants.WAYBILL_WOODEN_NOPACK.equals(packType)){
							if(StringUtil.isBlank(noPackGoodsSize.toString())){
								noPackGoodsSize.append(length).append("*").append(width).append("*").append(height).append("*").append(num);
							}else{
								noPackGoodsSize.append("+").append(length).append("*").append(width).append("*").append(height).append("*").append(num);
							}
						}
					}
					if(StringUtil.isNotBlank(noPackGoodsSize.toString()) && StringUtil.isNotBlank(waybillEntity.getGoodsSize())){
						waybillEntity.setGoodsSize(waybillEntity.getGoodsSize()+"+"+noPackGoodsSize.toString());
					}else if(StringUtil.isNotBlank(noPackGoodsSize.toString())){
						waybillEntity.setGoodsSize(noPackGoodsSize.toString());
					}
				}
				
			}
			recordWaybillDto.setWoodenRequirementsEntity(woodenEntity);
			pendingEntity.setInternalDeliveryType(acf.getInternalDeliveryType());
			pendingEntity.setEmployeeNo(acf.getEmployeeNo());
			//基本验证
			validate(pendingEntity,chargePendList,woodenEntity,waybillPictureEntity);
			
			//开户银行信息
			CusAccountEntity cusAccountEntity =queryCusAccountByAccount(waybillEntity.getDeliveryCustomerCode(),waybillEntity.getAccountCode());
			//开户银行信息
			recordWaybillDto.setOpenBank(cusAccountEntity);
			
			/**
			 * 添加统一结算信息到实际承运实体中调用结算接口使用
			 * dp-foss-dongjialing
			 * 225131
			 */
			if(recordWaybillDto.getActualFreightEntity() != null) {	
				//始发客户统一结算信息
				recordWaybillDto.getActualFreightEntity().setStartCentralizedSettlement(acf.getStartCentralizedSettlement());
				recordWaybillDto.getActualFreightEntity().setStartContractOrgCode(acf.getStartContractOrgCode());
				recordWaybillDto.getActualFreightEntity().setStartContractOrgName(acf.getStartContractOrgName());
				recordWaybillDto.getActualFreightEntity().setStartReminderOrgCode(acf.getStartReminderOrgCode());
				
				//到达客户统一结算信息
				recordWaybillDto.getActualFreightEntity().setArriveCentralizedSettlement(acf.getArriveCentralizedSettlement());
				recordWaybillDto.getActualFreightEntity().setArriveContractOrgCode(acf.getArriveContractOrgCode());
				recordWaybillDto.getActualFreightEntity().setArriveContractOrgName(acf.getArriveContractOrgName());
				recordWaybillDto.getActualFreightEntity().setArriveReminderOrgCode(acf.getArriveReminderOrgCode());
			}
			/*//存贮原发货客户编码
			String oldDeliveryCustomerCode=waybill.getDeliveryCustomerCode();
			//定义一个字段存贮是否统一结算
			String actualFreightEntity="";
			if(StringUtils.isNotBlank(waybillEntity.getDeliveryCustomerCode()) && waybillEntity.getBillTime()!=null){
				//判断发货客户是否在客户圈内，如果在就使用主客户的客户编码进行计价
				CustomerCircleNewDto customerCircleDto = waybillService.queryCustomerByCusCode(waybillEntity.getDeliveryCustomerCode(), waybillEntity.getBillTime(), "Y");
				//如果发货客户在客户圈就使用主客户的客户编码进行相关计费
				if(null !=customerCircleDto && "Y".equals(customerCircleDto.getIsCustCircle()) && 
						customerCircleDto.getCusBargainNewEntity() !=null && customerCircleDto.getCustomerCircleEntity() !=null){
					waybillEntity.setDeliveryCustomerCode(customerCircleDto.getCusBargainNewEntity().getCusCode());
					//始发客户统一结算信息
					recordWaybillDto.getActualFreightEntity().setStartCentralizedSettlement(customerCircleDto.getCustomerCircleEntity().getIsFocusPay() !=null?customerCircleDto.getCustomerCircleEntity().getIsFocusPay():"");
					recordWaybillDto.getActualFreightEntity().setStartCentralizedSettlement(customerCircleDto.getCustomerCircleEntity().getIsFocusPay() !=null ?customerCircleDto.getCustomerCircleEntity().getIsFocusPay() :"");
					//始发客户合同编码
					recordWaybillDto.getActualFreightEntity().setStartContractOrgCode(customerCircleDto.getCusBargainNewEntity().getBargainCode() !=null ?customerCircleDto.getCusBargainNewEntity().getBargainCode() :"");
					//催款部门 编码
					recordWaybillDto.getActualFreightEntity().setStartReminderOrgCode(customerCircleDto.getCusBargainNewEntity().getHastenfunddeptCode()  !=null ?customerCircleDto.getCusBargainNewEntity().getHastenfunddeptCode() :"");
					//存贮是否统一结算供结算用
					actualFreightEntity=customerCircleDto.getCustomerCircleEntity().getIsFocusPay() !=null?customerCircleDto.getCustomerCircleEntity().getIsFocusPay():"";
				}else{
					actualFreightEntity=acf.getStartCentralizedSettlement();
				}
			}*/
			// 运单明细集合
		    Map waybillMap =getWaybillMap(pendingEntity,waybillEntity,chargePendList,woodenEntity,woodenPdaDto);
			
			//运单基本信息
			recordWaybillDto.setWaybillEntity(waybillEntity);
			
			// 运单费用明细
		    List<WaybillChargeDtlEntity> chargeList=(List<WaybillChargeDtlEntity>) waybillMap.get("chargeDtlList");
			recordWaybillDto.setWaybillChargeDtlEntity(chargeList);
			
			// 运单折扣明细
		    recordWaybillDto.setWaybillDisDtlEntity((List<WaybillDisDtlEntity>)waybillMap.get("WaybillDisList"));
		    
		    // 运单付款明细
		    recordWaybillDto.setWaybillPaymentEntity((List<WaybillPaymentEntity>)waybillMap.get("paymentList"));
		    
		    //优惠卷   couponInfoDto
		    recordWaybillDto.setCouponInfoDto((CouponInfoDto)waybillMap.get("couponInfoDto"));
		    
			//实际承运信息
			recordWaybillDto.setActualFreightEntity((ActualFreightEntity)waybillMap.get("actualFreight"));
			//"货签信息"实体类 
			recordWaybillDto.setLabeledGoodEntities((List<LabeledGoodEntity>)waybillMap.get("labeledGoods"));
			
			//获取理货员信息
			 CurrentInfo currentInfo = getCurrentInfo(waybillEntity.getCreateUserCode());		
			 recordWaybillDto.setCurrentInfo(currentInfo);
			
			//是否快递
			recordWaybillDto.setIsExpress(FossConstants.NO);
			
			/**
			 * 判断目的站网点是否为二级网点以及到达模式、简装模式、代理模式，如是则根据加收方案计算加收费用
			 * 计算加收费用后需要处理recordWaybillDto中  运单信息、承运信息、ptp信息、折前信息
			 * 20160902 add by xingjun
			 */
			waybillManagerService.pdaWaybillReplenish(recordWaybillDto);
			
			//经确认图片开单时出发站不能选择合伙人（需要开单组主动维护服务合伙人才能选择），当开单从直营发货到合伙人时需要将合伙人的折前信息保留 2016年4月26日 11:50:01 葛亮亮			
			getPtpWaybillInfo(recordWaybillDto,chargeList,chargePendList);
			//计费完毕之后将发货客户编码设置回去
//			recordWaybillDto.getWaybillEntity().setDeliveryCustomerCode(oldDeliveryCustomerCode);
			
			//提交运单之前因需要调用结算接口所以需要将是否统一结算字段设置为客户圈中的字段，
			//业务场景是如果一旦有事统一结算的就直接传入是统一结算
//			recordWaybillDto.getActualFreightEntity().setStartCentralizedSettlement(actualFreightEntity);
			
			//提交运单
			pdaWaybillService.submitWaybill(recordWaybillDto);
			//业务监控
			businessMonitor(recordWaybillDto);
			//更新图片状态
			if(waybillMap.get("remark")!=null){
				waybillPictureEntity.setRemark(waybillMap.get("remark").toString());
			}
			waybillPictureEntity.setClearEmp(woodenPdaDto.getOperatorCode());
			waybillPictureEntity.setClearDept(woodenPdaDto.getOuterCode());
			waybillPictureEntity.setIsException(FossConstants.NO);
			waybillPictureEntity.setMakeupTime(new Date());
			updateWaybillPictureDao(pendingEntity, waybillPictureEntity);
			
		}catch (BusinessException e){
			String message="外场补录重量体积业务异常:";
			String gsgsg="";
			StringBuilder errorArgument = new StringBuilder("");
			Object[] ob =e.getErrorArguments();
			if(ob!=null && ob.length>0){
				for(int i=0;i<ob.length;i++){
					errorArgument.append(ob[i]);
				}
			}
			String messagee =e.getMessage();
			String localizedMessage =e.getLocalizedMessage();
			String nativeMessage =e.getNativeMessage();
			String errorCode = e.getErrorCode();
		
			if(StringUtil.isNotBlank(errorArgument.toString())){
				if(errorArgument.toString().equals("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable"))
				{
					errorArgument = new StringBuilder("此工号优惠额度已用完，不能开内部发货运单");
				}
				gsgsg=gsgsg+errorArgument.toString();
			}
			if(StringUtil.isNotBlank(messagee)){
				if(messagee.equals("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable"))
				{
					messagee = "此工号优惠额度已用完，不能开内部发货运单";
				}
				gsgsg=gsgsg+messagee;
			}
			if(StringUtil.isNotBlank(localizedMessage)){
				if(localizedMessage.equals("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable"))
				{
					localizedMessage = "此工号优惠额度已用完，不能开内部发货运单";
				}
				gsgsg=gsgsg+localizedMessage;
			}
			if(StringUtil.isNotBlank(nativeMessage)){
				if(nativeMessage.equals("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable"))
				{
					nativeMessage = "此工号优惠额度已用完，不能开内部发货运单";
				}
				gsgsg=gsgsg+nativeMessage;
			}
			if(StringUtil.isNotBlank(errorCode)){
				if(errorCode.equals("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable"))
				{
					errorCode = "此工号优惠额度已用完，不能开内部发货运单";
				}
				gsgsg=gsgsg+errorCode;
			}
			message=message+gsgsg;
			WaybillPictureEntity pictureEntity = new  WaybillPictureEntity();
			pictureEntity.setWaybillNo(waybillNo);
			pictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
			pictureEntity.setRemark(message);
			pictureEntity.setModifyTime(new Date());
			pictureEntity.setClearEmp(woodenPdaDto.getOperatorCode());
			pictureEntity.setClearDept(woodenPdaDto.getOuterCode());
			pictureEntity.setIsException(FossConstants.YES);
			pictureEntity.setMakeupTime(new Date());
			if(waybillNo!=null){
				//更新图片信息
				waybillPendingService.updatePictureWaybillByWaybillno(pictureEntity);
			}
			waybillPendingDao.updateByPrimaryKey(pendingEntity);
			LOGGER.error("外场补录重量体积业务异常:"+"栈信息："+ExceptionUtils.getFullStackTrace(e));
		}catch(Exception ee){
			String message="外场补录重量体积系统异常Exception:";
			
			String excepMessage = ExceptionUtils.getFullStackTrace(ee);
			 if(StringUtil.isNotBlank(excepMessage)){
				 message=message+excepMessage;
			 }
			 WaybillPictureEntity pictureEntity = new  WaybillPictureEntity();
			 pictureEntity.setWaybillNo(waybillNo);
			 pictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
			 pictureEntity.setRemark(message);
			 pictureEntity.setModifyTime(new Date());
			 pictureEntity.setClearEmp(woodenPdaDto.getOperatorCode());
			 pictureEntity.setClearDept(woodenPdaDto.getOuterCode());
			 pictureEntity.setIsException(FossConstants.YES);
			 pictureEntity.setMakeupTime(new Date());
			 if(waybillNo!=null){
				//跟新图片信息
				 waybillPendingService.updatePictureWaybillByWaybillno(pictureEntity);
			 }
			 waybillPendingService.updateByPrimaryKey(pendingEntity);
			 LOGGER.error("外场补录重量体积系统异常Exception:"+ee.getMessage()+"栈信息："+ExceptionUtils.getFullStackTrace(ee));
			 throw new Exception(ee);
		}catch(Throwable able){
			 
			String message="外场补录重量体积系统异常Throwable:";
			
			String throwable =ExceptionUtils.getFullStackTrace(able);
			if(StringUtil.isNotBlank(throwable)){
				message=message+throwable;
			}
			WaybillPictureEntity pictureEntity = new  WaybillPictureEntity();
			pictureEntity.setWaybillNo(waybillNo);
			pictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
			pictureEntity.setRemark(message);
			pictureEntity.setModifyTime(new Date());
			pictureEntity.setClearEmp(woodenPdaDto.getOperatorCode());
			pictureEntity.setClearDept(woodenPdaDto.getOuterCode());
			pictureEntity.setIsException(FossConstants.YES);
			pictureEntity.setMakeupTime(new Date());
			if(waybillNo!=null){
				//跟新图片信息
				waybillPendingService.updatePictureWaybillByWaybillno(pictureEntity);
		    }
			waybillPendingService.updateByPrimaryKey(pendingEntity);
			LOGGER.error("外场补录重量体积系统异常Throwable:"+able.getMessage()+"栈信息："+ExceptionUtils.getFullStackTrace(able));
			throw new Exception(able);
		}
		LOGGER.error("出去autoSubmitWaybillByPDA===================================================================");
		return new ResultDto();	
		
	}
	
	/**
	 * 图片开单到合伙人，保留合伙人折前信息
	 * @param recordWaybillDto
	 * @param chargeList
	 * @param chargePendList
	 */
	private void getPtpWaybillInfo(WaybillDto  recordWaybillDto,List<WaybillChargeDtlEntity> chargeList,List<WaybillCHDtlPendingEntity> chargePendList){
		
		if(recordWaybillDto==null ){
			return;
		}
		
		WaybillEntity waybillEntity = recordWaybillDto.getWaybillEntity();
		if(waybillEntity==null){
			return;
		}
		
		SaleDepartmentEntity sdept =saleDepartmentService.querySimpleSaleDepartmentByCodeCache(waybillEntity.getCustomerPickupOrgCode());
		if(null==sdept || !FossConstants.YES.equals(sdept.getIsLeagueSaleDept())){
			return;
		}
		
		PtpWaybillDto ptpWaybillDto = new PtpWaybillDto();
		//基础送货费
		ptpWaybillDto.setBaseDeliveryGoodsFeeOrg(waybillEntity.getDeliveryGoodsFee().toString());
		//木箱费用
		BigDecimal boxChargeOrg =new BigDecimal(0);
		//木架费用
		BigDecimal standChargeOrg = new BigDecimal(0);
		//木托费用
		BigDecimal salverGoodsChargeOrg = new BigDecimal(0);
	    //送货上楼费
		String upFloorFeeOrg = null;
		//送货进仓费
		BigDecimal deliveryWareHouseFeeOrg = new BigDecimal(0);
		//大件上楼费
	    BigDecimal bigGoodsUpFloorFeeOrg = new BigDecimal(0);
	    //超远派送费 CY
	    BigDecimal overDistanceFeeOrg = new BigDecimal(0);
	    //超远派送费加收
	    BigDecimal overDistanceFeeJSOrg = new BigDecimal(0);
		for(WaybillChargeDtlEntity charge:chargeList){
			if(DictionaryValueConstants.PACKAGE_TYPE__BOX.equals(charge.getPricingEntryCode())){
				boxChargeOrg=charge.getAmount();
			}else if(DictionaryValueConstants.PACKAGE_TYPE__FRAME.equals(charge.getPricingEntryCode())){
				standChargeOrg=charge.getAmount();
			}else if(DictionaryValueConstants.PACKAGE_TYPE__SALVER.equals(charge.getPricingEntryCode())){
				salverGoodsChargeOrg=charge.getAmount();
			}
			
			
			if(PriceEntityConstants.PRICING_CODE_SHSL.equals(charge.getPricingEntryCode())){
				upFloorFeeOrg=charge.getAmount()!=null?charge.getAmount().toString():null;
			}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(charge.getPricingEntryCode())){
				deliveryWareHouseFeeOrg=charge.getAmount();
			}else if(PriceEntityConstants.PRICING_CODE_DJSL.equals(charge.getPricingEntryCode())){
				bigGoodsUpFloorFeeOrg=charge.getAmount();
			}else if(PriceEntityConstants.PRICING_CODE_CY.equals(charge.getPricingEntryCode())){
				overDistanceFeeOrg=charge.getAmount();
			}
			 // 超远派送费加收 352676 2016年12月2日17:34:01 
			else if(PriceEntityConstants.PRICING_CODE_CCDDJS.equals(charge.getPricingEntryCode())){
				overDistanceFeeJSOrg=charge.getAmount();
				//overDistanceFeeOrg.add(null != charge.getAmount() ? charge.getAmount() : BigDecimal.ZERO);
			}
		}
		// 超远派送费加收 352676
		if (null != overDistanceFeeOrg) {
			overDistanceFeeOrg = overDistanceFeeOrg.add(null != overDistanceFeeJSOrg ? overDistanceFeeJSOrg : BigDecimal.ZERO);
		} else {
			overDistanceFeeOrg = null != overDistanceFeeJSOrg ? overDistanceFeeJSOrg : BigDecimal.ZERO;
		}
		
		//签收单费
		 BigDecimal signBillFeeOrg = new BigDecimal(0);
		//送货上楼安装（家装）
	    BigDecimal pickupToDoorJZFeeOrg = new BigDecimal(0);
		for(WaybillCHDtlPendingEntity chargePend:chargePendList){
			if(PriceEntityConstants.PRICING_CODE_QS.equals(chargePend.getPricingEntryCode())){
				signBillFeeOrg=chargePend.getAmount();
			}
			if(PriceEntityConstants.PRICING_CODE_SHAZ.equals(chargePend.getPricingEntryCode())){
				pickupToDoorJZFeeOrg=chargePend.getAmount();
			}
		}
		// 打木箱货物费用
		ptpWaybillDto.setBoxChargeOrg(boxChargeOrg);
		//代收货款手续费
		ptpWaybillDto.setCodFeeOrg(waybillEntity.getCodFee());
		// 送货费+送货上楼费
		ptpWaybillDto.setDeliveryGoodsFeeOrg(waybillEntity.getDeliveryGoodsFee()!=null ? waybillEntity.getDeliveryGoodsFee().toString():"0");
		//保价费 
		ptpWaybillDto.setInsuranceFeeOrg(waybillEntity.getInsuranceFee());
		//其他费用
		ptpWaybillDto.setOtherFeeOrg(waybillEntity.getOtherFee());
		//包装费
		ptpWaybillDto.setPackageFeeOrg(waybillEntity.getPackageFee()!=null ? waybillEntity.getPackageFee().toString():"0");
		//接货费
		ptpWaybillDto.setPickUpFeeOrg(waybillEntity.getPickupFee()!=null?waybillEntity.getPickupFee().toString():"0");
		//打木托货物费用
		ptpWaybillDto.setSalverGoodsChargeOrg(salverGoodsChargeOrg) ;
		//打木架货物费用
		ptpWaybillDto.setStandChargeOrg(standChargeOrg) ;
		// 总费用
		ptpWaybillDto.setTotalFeeOrg(waybillEntity.getTotalFee()) ;
		// 公布价运费
		ptpWaybillDto.setTransportFeeOrg(waybillEntity.getTransportFee()) ;
		//上楼费
		ptpWaybillDto.setUpFloorFeeOrg(upFloorFeeOrg) ;
		//运单号
		ptpWaybillDto.setWaybillNo(waybillEntity.getWaybillNo());
		//送货进仓费
		ptpWaybillDto.setDeliveryWareHouseFeeOrg(deliveryWareHouseFeeOrg);
		//大件上楼费
		ptpWaybillDto.setBigGoodsUpFloorFeeOrg(bigGoodsUpFloorFeeOrg) ;
		//出发部门
		ptpWaybillDto.setReceiveOrgCode(waybillEntity.getReceiveOrgCode());
		//到达部门 
		if(waybillEntity.getCustomerPickupOrgCode()!=null){
			ptpWaybillDto.setArriveOrgCode(waybillEntity.getCustomerPickupOrgCode());
		}else{
			ptpWaybillDto.setArriveOrgCode(waybillEntity.getLastLoadOrgCode());
		}
		//到达合伙人算提成
		ptpWaybillDto.setFeeType(WaybillConstants.ONE);//0:成本  1：提成  2：成本和提成		
		//现在只判断合伙人是到达部门
		ptpWaybillDto.setIsArriveDepPartner(true);		
		//出发部门是否合伙人
		ptpWaybillDto.setIsReceiveDepPartner(false);
		 
		// 是否上门接货
		boolean pickupToDoor =WaybillConstants.YES.equals(waybillEntity.getPickupToDoor())?true:false;
		ptpWaybillDto.setPickupToDoor(pickupToDoor);
		//送货上楼安装（家装）
		ptpWaybillDto.setPickupToDoorJZFeeOrg(pickupToDoorJZFeeOrg);
		//异常操作服务费 ZZ 图片开单没有异常操作费
		ptpWaybillDto.setExceptionOpreationFeeOrg( BigDecimal.ZERO);
		//超远派送费 CY
		ptpWaybillDto.setOverDistanceFeeOrg(overDistanceFeeOrg);
		//签收单
		ptpWaybillDto.setSignBillFeeOrg(signBillFeeOrg);
		//是否快递
		ptpWaybillDto.setIsExpress(WaybillConstants.NO);
		//是否更改单 Y：更改单；N:开单
		ptpWaybillDto.setIsChanged(WaybillConstants.NO);
		
		//返单类别
		 ptpWaybillDto.setReturnBillType(waybillEntity.getReturnBillType());
		//计费重量
		ptpWaybillDto.setBillWeight(waybillEntity.getBillWeight());
		//币种
		ptpWaybillDto.setCurrencyCode(waybillEntity.getCurrencyCode());
		
		//目前合伙人无法图片开单，直营开到合伙人不需要推送优惠券信息
//		if(null != recordWaybillDto.getCouponInfoDto()){
//			ptpWaybillDto.setCouponCode(recordWaybillDto.getCouponInfoDto().getCouponNumber());//优惠券code
//			ptpWaybillDto.setCouponFeeOrg(recordWaybillDto.getCouponInfoDto().getCouponFree());//优惠券金额
//		}
		//开单人生信息
		CurrentInfo currentInfo = getCurrentInfo(waybillEntity.getCreateUserCode());
		UserEntity user=null;
		if(currentInfo!=null){
		  user =currentInfo.getUser();
		}
		if(currentInfo != null && user!=null ){
			EmployeeEntity employeeEntity = user.getEmployee();
			if(employeeEntity!=null){
				ptpWaybillDto.setUserCode(user.getEmployee().getEmpCode());//职员编号
				ptpWaybillDto.setUserName(user.getEmployee().getEmpName());// 用户登录名
				ptpWaybillDto.setCreateUserCode(user.getEmployee().getEmpCode());
				OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
				if(dept!=null){
					ptpWaybillDto.setCurrentDeptCode(dept.getCode());//当前登录部门编码
					ptpWaybillDto.setCurrentDeptName(dept.getName());//当前登录部门名称
					ptpWaybillDto.setCreateOrgCode(dept.getCode());
					ptpWaybillDto.setCurrentDeptUnifieldCode(dept.getUnifiedCode());//标杆编码
				}
			}
			
		}
		//在处理加收费逻辑时，将加收费set到折前信息对象中，此处从该对象接收，并set到PTP信息
		PartnersWaybillEntity partnersWaybillEntity = recordWaybillDto.getPartnersWaybillEntity();
		if(null != partnersWaybillEntity){
			ptpWaybillDto.setOverTransportFeeOrg(partnersWaybillEntity.getOverTransportFee() != null ? partnersWaybillEntity.getOverTransportFee() : BigDecimal.ZERO);
			ptpWaybillDto.setOverTransportRateOrg(partnersWaybillEntity.getOverTransportRate() != null ? partnersWaybillEntity.getOverTransportRate() : BigDecimal.ZERO);
			//清空折前对象，防止提交时因为折前对象不为空走更改单逻辑，会导致折前信息全部为空
			recordWaybillDto.setPartnersWaybillEntity(null);
		}else{
			ptpWaybillDto.setOverTransportFeeOrg(BigDecimal.ZERO);
			ptpWaybillDto.setOverTransportRateOrg(BigDecimal.ZERO);
		}
		
		recordWaybillDto.setPtpWaybillDto(ptpWaybillDto);
	}
	
	/**
	 * 是否是香港
	 * @param code
	 * @return
	 */
	private  boolean  isHK(String code){
		OrgAdministrativeInfoEntity org= waybillManagerService.queryOrgInfoByCode(code);
		if(org!=null){
			String citycode = org.getCityCode();
			if("810000-1".equals(citycode)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取运单信息
	 * @author 076234 PanGuoYang
	 */
	private WaybillEntity waybillEntity(WaybillPendingEntity pendingEntity){
		
		//拷贝属性值
		WaybillEntity waybillEntity =new WaybillEntity();
		try {
			if(pendingEntity!=null){
				PropertyUtils.copyProperties(waybillEntity,pendingEntity);	
			}
		}catch (Exception e) {
			//添加异常日志
			LOGGER.error("对象拷贝失败！\n原因："+e.getMessage());
			//抛出异常信息
			throw new WaybillValidateException("对象拷贝失败！\n原因："+e.getMessage());
		}
		String goodsTypeCode=waybillEntity.getGoodsTypeCode();
		if(("A".equals(goodsTypeCode) ||"B".equals(goodsTypeCode)) && pendingEntity != null){
			// 总重量
			BigDecimal goodsWeightTotal = pendingEntity.getGoodsWeightTotal();
			//件数
			BigDecimal goodsQtyTotal = new BigDecimal(pendingEntity.getGoodsQtyTotal());
			//单件>50 是B货
			BigDecimal pjzl =goodsWeightTotal.divide(goodsQtyTotal,BigDecimal.ROUND_HALF_UP);
			if(pjzl.compareTo(new BigDecimal(NumberConstants.NUMBER_50))>0){
				waybillEntity.setGoodsTypeCode("B");
			}
			//木包装
			Integer woodNum =pendingEntity.getWoodNum();
			if(woodNum!=null && woodNum>0){
				waybillEntity.setGoodsTypeCode("B");
			}
		}
		waybillEntity.setCreateTime(new Date());
		return waybillEntity;
		
	}
	
	/**
	 * 获取图片信息
	 * @author 076234 PanGuoYang
	 */
	private WaybillPictureEntity getWaybillPictureEntity(WoodenRequirePdaDto woodenPdaDto){
		//图片信息
		WaybillEntity waybillEntity =waybillDao.queryWaybillByNo(woodenPdaDto.getWaybillNo());
		if(waybillEntity==null){
			throw new WaybillValidateException("运单信息为空");
		}
		if(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntity.getPendingType()) 
		        || 
		   WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntity.getPendingType())){
			throw new WaybillValidateException("该单运单已经开单");
		}
		WaybillPictureEntity pictureEntity= new WaybillPictureEntity();
		pictureEntity.setWaybillNo(woodenPdaDto.getWaybillNo());
		pictureEntity.setActive(FossConstants.ACTIVE);
		pictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING);
		//查询图片休息
		List<WaybillPictureEntity> pictureEntitys= waybillPictureDao.queryWaybillPictureByEntity(pictureEntity);
		
		if(CollectionUtils.isEmpty(pictureEntitys)){
			pictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION);
			pictureEntitys= waybillPictureDao.queryWaybillPictureByEntity(pictureEntity);
			if(CollectionUtils.isEmpty(pictureEntitys)){
				throw new WaybillValidateException("图片信息为空");
			}
		}
		WaybillPictureEntity waybillPictureEntity=pictureEntitys.get(0);
		//是否大件签收
		String picIsBigUp = waybillPictureEntity.getIsBigUp();
		//500-1000 件数
		Integer pdaFhToOtOverQty= woodenPdaDto.getFhToOtOverQty();
		Integer picFhToOtOverQty= waybillPictureEntity.getFhToOtOverQty();
		//1000-2000件数
		Integer pdaOtToTtOverQty =woodenPdaDto.getOtToTtOverQty();
		Integer picOtToTtOverQty =waybillPictureEntity.getOtToTtOverQty();
		if(FossConstants.ACTIVE.equals(picIsBigUp)){
			woodenPdaDto.setIsBigUp(picIsBigUp);
		}
		if(pdaFhToOtOverQty ==null || pdaFhToOtOverQty<0){
			woodenPdaDto.setFhToOtOverQty(picFhToOtOverQty);
		}
		if(pdaOtToTtOverQty==null || pdaOtToTtOverQty<0){
			 woodenPdaDto.setOtToTtOverQty(picOtToTtOverQty);
		}
		waybillPictureEntity.setIsBigUp(woodenPdaDto.getIsBigUp());
		waybillPictureEntity.setFhToOtOverQty(woodenPdaDto.getFhToOtOverQty());
		waybillPictureEntity.setOtToTtOverQty(woodenPdaDto.getOtToTtOverQty());
		return waybillPictureEntity;
		
	}
	
	/**
	 * 获取打木架信息
	 * @param woodenPdaDto
	 * @return
	 * @author 076234 PanGuoYang
	 */
    public WoodenRequirePdaDto getWoodenRequirePdaDto(WoodenRequirePdaDto woodenPdaDto){
    	//打木箱总体积
		BigDecimal allBoxVolume=BigDecimal.ZERO;
		//打木箱总件数
		Integer allBoxNum=0;
		//打木架总体积
		BigDecimal allStandVolume=BigDecimal.ZERO;
		//打木架总件数
		Integer allStandNum=0;
		//没打木架木箱总体积
		BigDecimal allNoVolume=BigDecimal.ZERO;
		//没打木架木箱总体积
		Integer allNoNum=0;
		//货物总重量
		BigDecimal goodsWeightTotal= woodenPdaDto.getGoodsWeightTotal();
		List<WoodenRequireEntity> woodeList= woodenPdaDto.getWoodenRequireEntityLis();
		if(CollectionUtils.isNotEmpty(woodeList)){
			for(WoodenRequireEntity woode:woodeList){
				String packType = woode.getPackType();
				//件数
				BigDecimal  num = new BigDecimal(woode.getGoodsNum());
				//长
				BigDecimal length = woode.getLength().divide(new BigDecimal(NumberConstants.NUMBER_100));
				//宽
				BigDecimal width = woode.getWidth().divide(new BigDecimal(NumberConstants.NUMBER_100));
				//高
				BigDecimal height = woode.getHeight().divide(new BigDecimal(NumberConstants.NUMBER_100));
				//打木箱
				if(WaybillConstants.WAYBILL_WOODEN_BOX.equals(packType)){
					BigDecimal oneBoxVolume =length.multiply(width).multiply(height).multiply(num);
					allBoxVolume=allBoxVolume.add(oneBoxVolume);
					allBoxNum=allBoxNum+woode.getGoodsNum();
				}
				//打木架
				if(WaybillConstants.WAYBILL_WOODEN_STAND.equals(packType)){
					BigDecimal oneStandVolume =length.multiply(width).multiply(height).multiply(num);
					allStandVolume=allStandVolume.add(oneStandVolume);
					allStandNum =allStandNum+(woode.getGoodsNum());
				}
				//没打木箱、 木架
				if(WaybillConstants.WAYBILL_WOODEN_NOPACK.equals(packType)){
					BigDecimal oneNoVolume =length.multiply(width).multiply(height).multiply(num);
					allNoVolume=allNoVolume.add(oneNoVolume);
					allNoNum =allNoNum+(woode.getGoodsNum());
				}
				/**
				 * 保存pda的包装信息
				 */
				PDAWoodenRequireEntity pdaWoodenRequireEntity= new PDAWoodenRequireEntity();
				pdaWoodenRequireEntity.setWaybillNo(woodenPdaDto.getWaybillNo());
				pdaWoodenRequireEntity.setOperatorCode(woodenPdaDto.getOperatorCode());
				pdaWoodenRequireEntity.setOuterCode(woodenPdaDto.getOuterCode());
				pdaWoodenRequireEntity.setGoodsVolumeTotal(woodenPdaDto.getGoodsVolumeTotal());
				pdaWoodenRequireEntity.setGoodsWeightTotal(woodenPdaDto.getGoodsWeightTotal());
				if(null!=woodenPdaDto.getIsBigUp()){
					pdaWoodenRequireEntity.setIsBigUp(woodenPdaDto.getIsBigUp());
				}else{
					pdaWoodenRequireEntity.setIsBigUp(FossConstants.NO);
				}
				pdaWoodenRequireEntity.setFhToOtOverQty(woodenPdaDto.getFhToOtOverQty());
				pdaWoodenRequireEntity.setOtToTtOverQty(woodenPdaDto.getOtToTtOverQty());
				pdaWoodenRequireEntity.setWoodenStockNum(woodenPdaDto.getWoodenStockNum());
				pdaWoodenRequireEntity.setGoodsNum(woode.getGoodsNum());
				pdaWoodenRequireEntity.setLength(woode.getLength());
				pdaWoodenRequireEntity.setWidth(woode.getWidth());
				pdaWoodenRequireEntity.setHeight(woode.getHeight());
				pdaWoodenRequireEntity.setPackType(woode.getPackType());
				waybillPictureDao.insertWaybillPDAWoodenRequireEntity(pdaWoodenRequireEntity);
			}
			//最终货物总体积  没有打木箱木架体积+（打木架体积+打木箱体积）*1.4
			BigDecimal volume14= (allBoxVolume.add(allStandVolume)).multiply(new BigDecimal(POINT_14));
			woodenPdaDto.setGoodsVolumeTotal(allNoVolume.add(volume14));
			//打木架木箱的重量=（木箱体积+木架体积）* 42
			BigDecimal bhWeight = (allBoxVolume.add(allStandVolume)).multiply(new BigDecimal(NumberConstants.NUMBER_42));
			//最终货物总重量
			woodenPdaDto.setGoodsWeightTotal(goodsWeightTotal.add(bhWeight));
		}else{
			/**
			 * 保存pda的包装信息
			 */
			PDAWoodenRequireEntity pdaWoodenRequireEntity= new PDAWoodenRequireEntity();
			pdaWoodenRequireEntity.setWaybillNo(woodenPdaDto.getWaybillNo());
			pdaWoodenRequireEntity.setOperatorCode(woodenPdaDto.getOperatorCode());
			pdaWoodenRequireEntity.setOuterCode(woodenPdaDto.getOuterCode());
			pdaWoodenRequireEntity.setGoodsVolumeTotal(woodenPdaDto.getGoodsVolumeTotal());
			pdaWoodenRequireEntity.setGoodsWeightTotal(woodenPdaDto.getGoodsWeightTotal());
			if(null!=woodenPdaDto.getIsBigUp()){
				pdaWoodenRequireEntity.setIsBigUp(woodenPdaDto.getIsBigUp());
			}else{
				pdaWoodenRequireEntity.setIsBigUp(FossConstants.NO);
			}
			
			pdaWoodenRequireEntity.setFhToOtOverQty(woodenPdaDto.getFhToOtOverQty());
			pdaWoodenRequireEntity.setOtToTtOverQty(woodenPdaDto.getOtToTtOverQty());
			pdaWoodenRequireEntity.setWoodenStockNum(woodenPdaDto.getWoodenStockNum());
			waybillPictureDao.insertWaybillPDAWoodenRequireEntity(pdaWoodenRequireEntity);
		}
		BigDecimal goodsVolumeTotal =woodenPdaDto.getGoodsVolumeTotal();
		if(goodsVolumeTotal.compareTo(new BigDecimal(POINT_001))<0){
			goodsVolumeTotal=new BigDecimal(POINT_001);
			woodenPdaDto.setGoodsVolumeTotal(goodsVolumeTotal);
		}
		 goodsWeightTotal = woodenPdaDto.getGoodsWeightTotal();
		if(goodsWeightTotal.compareTo(new BigDecimal(POINT_01))<0){
			goodsWeightTotal=new BigDecimal(POINT_01);
			woodenPdaDto.setGoodsWeightTotal(goodsWeightTotal);
		}
		
		return woodenPdaDto;
    	
    }
    /**
     * 获取打木架信息
     * @param woodenPdaDto
     * @return
     * @author 076234 PanGuoYang
     */
    private WoodenRequirementsEntity getWoodenRequirementsEntity(WoodenRequirePdaDto woodenPdaDto,WaybillPendingEntity pendingEntity,WoodenRequirePendingEntity  woodenPend){
    	
    	//打木架信息
		WoodenRequirementsEntity woodenEntity=new WoodenRequirementsEntity();
    	//打木箱总体积
		BigDecimal allBoxVolume=BigDecimal.ZERO;
		//打木箱总件数
		Integer allBoxNum=0;
		StringBuilder boxGoodsSize=new StringBuilder("");
		//打木架总体积
		BigDecimal allStandVolume=BigDecimal.ZERO;
		//打木架总件数
		Integer allStandNum=0;
		//打木架备注
		StringBuilder standGoodsSize=new StringBuilder("");
		List<WoodenRequireEntity> woodeList= woodenPdaDto.getWoodenRequireEntityLis();
		if(CollectionUtils.isNotEmpty(woodeList)){
			
			for(WoodenRequireEntity woode:woodeList){
				String packType = woode.getPackType();
				//件数
				BigDecimal  num = new BigDecimal(woode.getGoodsNum());
				//长
				BigDecimal length = woode.getLength().divide(new BigDecimal(NumberConstants.NUMBER_100));
				//宽
				BigDecimal width = woode.getWidth().divide(new BigDecimal(NumberConstants.NUMBER_100));
				//高
				BigDecimal height = woode.getHeight().divide(new BigDecimal(NumberConstants.NUMBER_100));
				//打木箱
				if(WaybillConstants.WAYBILL_WOODEN_BOX.equals(packType)){
					BigDecimal oneBoxVolume =length.multiply(width).multiply(height).multiply(num).multiply(new BigDecimal(POINT_14));
					allBoxVolume=allBoxVolume.add(oneBoxVolume);
					allBoxNum=allBoxNum+woode.getGoodsNum();
					if("".equals(boxGoodsSize.toString())){
						boxGoodsSize.append(length.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(width.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(height.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(num);
					}else{
						boxGoodsSize.append("+").append(length.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(width.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(height.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(num);	
					}
					
				}
				//打木架
				if(WaybillConstants.WAYBILL_WOODEN_STAND.equals(packType)){
					BigDecimal oneStandVolume =length.multiply(width).multiply(height).multiply(num).multiply(new BigDecimal(POINT_14));
					allStandVolume=allStandVolume.add(oneStandVolume);
					allStandNum =allStandNum+(woode.getGoodsNum());
					if("".equals(standGoodsSize.toString())){
						standGoodsSize.append(length.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(width.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(height.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(num);
					}else{
						standGoodsSize.append("+").append(length.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(width.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(height.multiply(new BigDecimal(NumberConstants.NUMBER_100))).append("*").append(num);
					}
				}
			}
		}
		//开单组打木托
		if(woodenPend!=null && woodenPend.getSalverGoodsAmount().compareTo(BigDecimal.ZERO)>0){
			//理货员所属外场
			woodenEntity.setPackageOrgCode(woodenPdaDto.getOuterCode());
			woodenEntity.setSalverGoodsNum(woodenPend.getSalverGoodsNum()!=null?woodenPend.getSalverGoodsNum():0);
			woodenEntity.setSalverRequirement(woodenPend.getSalverRequirement()!=null?woodenPend.getSalverRequirement():"");
			woodenEntity.setSalverGoodsAmount(woodenPend.getSalverGoodsAmount()!=null?woodenPend.getSalverGoodsAmount():BigDecimal.ZERO);
			woodenEntity.setActive(FossConstants.ACTIVE);
			woodenEntity.setCreateTime(new Date());
			woodenEntity.setModifyTime(new Date());
			//手输的包装费
			pendingEntity.setPackageFee(pendingEntity.getPackageFee().subtract(woodenPend.getSalverGoodsAmount()));
			
		}
		//外场打木托
        if(woodenPdaDto.getWoodenStockNum()!=null  &&  woodenPdaDto.getWoodenStockNum()>0){
        	QueryBillCacilateValueAddDto queryValueAddDto = getQueryYokeParam(pendingEntity,DictionaryValueConstants.PACKAGE_TYPE__SALVER,new BigDecimal(woodenPdaDto.getWoodenStockNum()), false);
        	List<ValueAddDto> valueAddDtos= billCaculateService.searchValueAddPriceList(queryValueAddDto);
            if(CollectionUtils.isNotEmpty(valueAddDtos)){
            	ValueAddDto	valueAddDto =valueAddDtos.get(0);
	            BigDecimal salverGoodsAmount= valueAddDto.getCaculateFee();
	            if(salverGoodsAmount!=null && salverGoodsAmount.compareTo(BigDecimal.ZERO)>0 ){
	            	//理货员所属外场
	    			woodenEntity.setPackageOrgCode(woodenPdaDto.getOuterCode());
	    			woodenEntity.setSalverGoodsNum(woodenPdaDto.getWoodenStockNum());
	    			woodenEntity.setSalverRequirement(woodenPdaDto.getWoodenStockNum()+"木托");
	    			woodenEntity.setSalverGoodsAmount(salverGoodsAmount);
	    			woodenEntity.setActive(FossConstants.ACTIVE);
	    			woodenEntity.setCreateTime(new Date());
	    			woodenEntity.setModifyTime(new Date());
	            }
            }
		} 
		
		if((allBoxNum+allStandNum)>0){
			
 			/*//走货路径
 			FreightRouteDto routeDto =	waybillManagerService.queryFreightRouteBySourceTarget(orginalOrganizationCode,
 			pendingEntity.getCustomerPickupOrgCode(), pendingEntity.getProductCode(), new Date());
 			*/
			//理货员所属外场			
			woodenEntity.setPackageOrgCode(woodenPdaDto.getOuterCode());
			//运单号
			woodenEntity.setWaybillNo(woodenPdaDto.getWaybillNo());
			// 代打木架要求
			woodenEntity.setStandRequirement(pendingEntity.getPackageRemark());
			// 代打木箱要求
			woodenEntity.setSalverRequirement(pendingEntity.getPackageRemark());
			//打木架件数
			woodenEntity.setStandGoodsNum(allStandNum);
			//打木架尺寸
			woodenEntity.setStandGoodsSize(standGoodsSize.toString());
			//打木架体积=原体积*1.4
			woodenEntity.setStandGoodsVolume(allStandVolume.setScale(2, BigDecimal.ROUND_HALF_UP));
			//打木箱件数
			woodenEntity.setBoxGoodsNum(allBoxNum);
			//打木箱尺寸
			woodenEntity.setBoxGoodsSize(boxGoodsSize.toString());
			//打木箱体积 
			woodenEntity.setBoxGoodsVolume(allBoxVolume.setScale(2, BigDecimal.ROUND_HALF_UP));
			woodenEntity.setActive(FossConstants.ACTIVE);
			woodenEntity.setCreateTime(new Date());
			woodenEntity.setModifyTime(new Date());
		}
		woodenEntity.setStandGoodsNum(woodenEntity.getStandGoodsNum()!=null?woodenEntity.getStandGoodsNum():0);
		woodenEntity.setBoxGoodsNum(woodenEntity.getBoxGoodsNum()!=null?woodenEntity.getBoxGoodsNum():0);
		woodenEntity.setStandGoodsVolume(woodenEntity.getStandGoodsVolume()!=null?woodenEntity.getStandGoodsVolume():BigDecimal.ZERO);
		woodenEntity.setBoxGoodsVolume(woodenEntity.getBoxGoodsVolume()!=null?woodenEntity.getBoxGoodsVolume():BigDecimal.ZERO);
		woodenEntity.setSalverGoodsNum(woodenEntity.getSalverGoodsNum()!=null?woodenEntity.getSalverGoodsNum():0);
		if(0==woodenEntity.getStandGoodsNum()
				&&
		   0==woodenEntity.getBoxGoodsNum()
		        &&
		   0==woodenEntity.getSalverGoodsNum()
		   ){
			return null;
		}
		return woodenEntity;
		 
    }
	
    
    
    /**
	 * 
	 * 获取查询参数
	 * @author	076234-PanGuoYang
	 * @date 2014-12-26 
	 * 
	 */
	private  QueryBillCacilateValueAddDto getQueryYokeParam(WaybillPendingEntity entity,
			String subType, BigDecimal volume,boolean isGetCurrentPrice) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(entity.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(entity.getCustomerPickupOrgCode());// 到达部门CODE
		queryDto.setProductCode(entity.getProductCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(entity.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(entity.getGoodsWeightTotal());// 重量
		queryDto.setVolume(volume);// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(entity.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(entity.getDeliveryCustomerCode());// 发货客户编码
		return queryDto;
	
	}
	/**
	 * 更新图片状态
	 * @param waybillNo
	 * @author 076234 PanGuoYang
	 */
	 private void updateWaybillPictureDao(WaybillPendingEntity entity,WaybillPictureEntity pictureEntity) {
		 pictureEntity.setWaybillNo(entity.getWaybillNo());
		 pictureEntity.setServiceFee(entity.getServiceFee());
		 pictureEntity.setServiceRate(entity.getServiceRate());
		 pictureEntity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);
		 
      waybillPictureDao.updatePictureWaybillByWaybillno(pictureEntity);
		 
		
	}

	/**
     * 运单明细集合
     * @author foss-PanGuoYang
     * @date 2014-10-08
     */
	private Map getWaybillMap(WaybillPendingEntity entity,
			                  WaybillEntity waybillEntity,
			                  List<WaybillCHDtlPendingEntity> chargePendList,
			                  WoodenRequirementsEntity woodEntity,
			                  WoodenRequirePdaDto woodenPdaDto){
		Map<String,Object> map = new HashMap<String, Object>();
		// 运单费用明细
		List<WaybillChargeDtlEntity> chargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		//折扣明细
		List<WaybillDisDtlEntity> disDtlEntityList = new ArrayList<WaybillDisDtlEntity>();
		//付款明细
		List<WaybillPaymentEntity> paymentEntityList = new ArrayList<WaybillPaymentEntity>();
		//优惠券
		 CouponInfoDto couponInfoDto=null;
		 String remark =null;
		 GuiResultBillCalculateDto tdo = null;
		// 非内部带货，内部带货不产生任何与费用相关的数据
		if (!WaybillConstants.INNER_PICKUP.equals(entity.getReceiveMethod())) {
			//统一返回的计价值
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos=queryGuiBillPrice(entity,woodEntity);
			 
			//获取代收货款手续费
			GuiResultBillCalculateDto codCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_HK, null);
			//费用明细添加代收费用
			setCodCollection(codCollection,entity,chargeDtlEntityList);
			
			//获取送货费
			List<DeliverChargeEntity> delivetList= getDeliverChargeEntity(guiResultBillCalculateDtos,entity);
			//费用明细添加送货费
			setDeliveryFeeCollection(delivetList,entity,chargeDtlEntityList);
			
			//获取接货费
			GuiResultBillCalculateDto dtoJH= getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_JH, null);
			// 获取公布价运费
			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_FRT, null);
			//保存一份公布价运费
			tdo = dto;
			//费用明细添加接货费
			setProductPriceDtoCollection(dto,dtoJH,entity,chargeDtlEntityList,remark);
			//费用明细添加公布价费
			setTransportFee(dto,waybillEntity, entity,chargeDtlEntityList);
			
			//获取保价费
			GuiResultBillCalculateDto insuranceCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BF, null);
			//费用明细添加保价费
			setInsurance(insuranceCollection,entity,chargeDtlEntityList);
			
			//获取打木架
			GuiResultBillCalculateDto packageCollectionFRAME=getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__FRAME);
			//费用明细添加打木架费
			setStandChargeCollection(packageCollectionFRAME,entity,woodEntity,chargeDtlEntityList);
			
			//获取打木箱
			GuiResultBillCalculateDto packageCollectionBOX=	 getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__BOX);
			//费用明细添加打木箱费
			setBoxChargeCollection(packageCollectionBOX,entity,woodEntity,chargeDtlEntityList);
			
			//包装费
			WaybillChargeDtlEntity packageCharge =setPackageCharge(packageCollectionFRAME,packageCollectionBOX,woodEntity,entity,chargeDtlEntityList);
			
			//其他费用
			List<GuiResultBillCalculateDto> otherCalculateDtos=otherCalculateDtos(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_QT);
			BigDecimal otherFee=  otherFee(entity,chargePendList,chargeDtlEntityList,woodenPdaDto,otherCalculateDtos,disDtlEntityList);
			
			// /////////////////////////////////////////////////////
			/**
			 * 将超远派送费加收用从其他费用中剥离，并至送货费费用项 by: 352676
			 */

			List<DeliverChargeEntity> overDistanceFeeJsList = new ArrayList<DeliverChargeEntity>();

			// 循环运单明细集合
			if (CollectionUtils.isNotEmpty(chargePendList)) {
				for (WaybillCHDtlPendingEntity chargePend : chargePendList) {
					// 如果是超远派加收送费
					if (StringUtil.equals(PriceEntityConstants.PRICING_CODE_CCDDJS,chargePend.getPricingEntryCode())) {
						DeliverChargeEntity deliver = new DeliverChargeEntity();
						
						// 费用编码
						deliver.setCode(chargePend.getPricingEntryCode());
						//费用名称
						deliver.setName(chargePend.getActiveName());
						// 是否激活
						deliver.setActive(FossConstants.ACTIVE);
						
						// 金额
						BigDecimal deliveryGoodsFee = null != chargePend.getAmount() ? chargePend.getAmount().setScale(0, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO;
						deliver.setAmount(deliveryGoodsFee);
						
						// 币种
						deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						// 费用ID
						deliver.setId(chargePend.getId());
						// 运单号
						deliver.setWaybillNo(chargePend.getWaybillNo());
					
						// 将新的派送费添加到派送费集合
						overDistanceFeeJsList.add(deliver);
						
						//判断送货费delivetList是否为空
						if(CollectionUtils.isNotEmpty(delivetList)){
							//将超远派送费加收放入派送费集合
							delivetList.add(deliver);
						}else{
							delivetList=new ArrayList<DeliverChargeEntity>();
							delivetList.add(deliver);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(overDistanceFeeJsList)) {
				// 费用明细添加送货费
				setDeliveryFeeCollection(overDistanceFeeJsList, entity, chargeDtlEntityList);
			}
			// /////////////////////////////////////////////////////
			//设置付款明细
			BigDecimal totalFee=setWaybillPaymentEntity(dto,
					                codCollection,
					                delivetList,
					                dtoJH,
					                insuranceCollection,
					                packageCharge,
					                otherFee,
					                entity,waybillEntity,
					                paymentEntityList,
					                couponInfoDto,
					                woodenPdaDto);
			
			//优惠券
			couponInfoDto=getCouponInfoDto(chargeDtlEntityList,totalFee,entity,disDtlEntityList,dto);
			
			if(null!=couponInfoDto.getRemark()){
				remark =couponInfoDto.getRemark();
			}
			//设置折扣明细
			setWaybillDisDtlEntity(waybillEntity,guiResultBillCalculateDtos,disDtlEntityList);
			
		    //如果优惠券不为空重新计算价格
			if(couponInfoDto!=null && couponInfoDto.getCouponFree()!=null 
					  && couponInfoDto.getCouponFree().compareTo(BigDecimal.ZERO)>0){
				setWaybillPaymentEntity(dto,
		                codCollection,
		                delivetList,
		                dtoJH,
		                insuranceCollection,
		                packageCharge,
		                otherFee,
		                entity,waybillEntity,
		                paymentEntityList,
		                couponInfoDto,
		                woodenPdaDto);
			}else{
				couponInfoDto=null;
			}
		}
		
		
		//实际承运信息
		ActualFreightEntity actuaf= getActualFreightEntity(waybillEntity,entity,chargeDtlEntityList,tdo);
		
		//"货签信息"实体类 
		List<LabeledGoodEntity>  labeledGoods= getLabeledGoodEntities(waybillEntity, woodenPdaDto);
		
		map.put("chargeDtlList", chargeDtlEntityList);
		map.put("WaybillDisList", disDtlEntityList);
		map.put("paymentList", paymentEntityList);
		map.put("couponInfoDto", couponInfoDto);
		map.put("actualFreight", actuaf);
		map.put("labeledGoods", labeledGoods);
		map.put("remark", remark);
		return map;
	}
	/**
	 * 签收单费用
	 * @author 076234
	 */
	private ValueAddDto setReturnBillCollection(WaybillPendingEntity entity){
		ValueAddDto valueAddDto=null;
		String returnBillType = entity.getReturnBillType();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(returnBillType)) {
			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(returnBillType)) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = returnBillType;
			}
			List<ValueAddDto> list = billCaculateService.searchValueAddPriceList(getQueryOtherChargeParam(entity, type));
			
			if(CollectionUtils.isNotEmpty(list)){
				valueAddDto=list.get(0);
			}
		}
		return valueAddDto;
	}
	
	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public  QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPendingEntity entity, String type) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(entity.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(entity.getCustomerPickupOrgCode());// 到达部门CODE
		queryDto.setProductCode(entity.getProductCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(entity.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setCustomerCode(entity.getDeliveryCustomerCode());
		if(entity.getGoodsWeightTotal() == null)
		{
			queryDto.setWeight(BigDecimal.ZERO);// 重量
		}else
		{
			queryDto.setWeight(entity.getGoodsWeightTotal());// 重量
		}

		if(entity.getGoodsVolumeTotal() == null)
		{
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}else
		{
			queryDto.setVolume(entity.getGoodsVolumeTotal());// 体积
		}
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		EffectiveDto effectiveDto = waybillManagerService.searchPreSelfPickupTime(entity.getReceiveOrgCode(), entity.getLastLoadOrgCode(), entity.getProductCode(), entity.getPreDepartureTime(), new Date());
		if(effectiveDto!=null){
			queryDto.setLongOrShort(effectiveDto.getLongOrShort());// 长途 还是短途
		}
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(type);
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		if (WaybillConstants.NOT_RETURN_BILL.equals(entity.getReturnBillType())) {// 判断有无返单类型
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
																				// 其他费用
		} else {
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);// 计价条目CODE
																				// 签收回单
		}
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	/**
	 * 获取其他费用
	 * @param guiResultBillCalculateDtos
	 * @param pricingCodeQt
	 * @return
	 */
	private List<GuiResultBillCalculateDto> otherCalculateDtos(
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,
			String pricingCodeQt) {
		List<GuiResultBillCalculateDto> calculateDtos = new ArrayList<GuiResultBillCalculateDto>(){};
		for(GuiResultBillCalculateDto dto:guiResultBillCalculateDtos){
			if(pricingCodeQt.equals(dto.getPriceEntryCode())){
				calculateDtos.add(dto);
			}
		}
          
		return calculateDtos;
	}

	/**
	 * 其他费用
	 * @param entity
	 * @param chargePendList
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang  雷锋
	 */
	private BigDecimal otherFee(WaybillPendingEntity entity,
			List<WaybillCHDtlPendingEntity> chargePendList,
			List<WaybillChargeDtlEntity> chargeDtlEntityList,
			WoodenRequirePdaDto woodenPdaDto,
			List<GuiResultBillCalculateDto> otherCalculateDtos,
			List<WaybillDisDtlEntity> disDtlEntityList) {
		BigDecimal otherFee = BigDecimal.ZERO;
		
		/**
		 * 添加其他费用（除综合信息费、超重服务费），其他费用是开单时暂存的
		 *   综合信息费(ZHXX)、超重服务费   需从新计算
		 */
		if (CollectionUtils.isNotEmpty(chargePendList)) {
			for(WaybillCHDtlPendingEntity chDtl:chargePendList){
				String code = chDtl.getPricingEntryCode();
				if(PriceEntityConstants.PRICING_CODE_FRT.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_BF.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_HK.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_SH.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_SHSL.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_SHJC.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_DJSL.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_BZ.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_QS.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_JH.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_RYFJ.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)
						||
						PriceEntityConstants.QT_CODE_CZHCZFWF.equals(code)
						||
				        PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(code)
				         /**超远派送费加收用不计入其他费用 by： 352676*/
				        ||
				        PriceEntityConstants.PRICING_CODE_CCDDJS.equals(code)
						){
					continue;
				}
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				chargeEntity.setId(UUIDUtils.getUUID());
				chargeEntity.setPricingEntryCode(code);
				chargeEntity.setAmount(chDtl.getAmount());
				chargeEntity.setWaybillNo(entity.getWaybillNo());
				chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
				chargeEntity.setActive(FossConstants.ACTIVE);
				chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				otherFee=otherFee.add(chargeEntity.getAmount());
				chargeDtlEntityList.add(chargeEntity);
			}
				
		}
		
		/**
		 * 添加综合信息费
		 */
		if(CollectionUtils.isNotEmpty(otherCalculateDtos)){
			for(GuiResultBillCalculateDto dto:otherCalculateDtos){
				String code =dto.getSubType();
				if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)){
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(code);
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					otherFee=otherFee.add(chargeEntity.getAmount());
					chargeDtlEntityList.add(chargeEntity);
					break;
				}
			}
		}
		
		/** 
		 * 燃油附加费  
		 */
		if(CollectionUtils.isNotEmpty(otherCalculateDtos)){
			for(GuiResultBillCalculateDto dto:otherCalculateDtos){
				String code =dto.getSubType();
				if(PriceEntityConstants.PRICING_CODE_RYFJ.equals(code)){
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(code);
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					otherFee=otherFee.add(chargeEntity.getAmount());
					chargeDtlEntityList.add(chargeEntity);
					break;
				}
			}
		}
		
		/**
		 *  大件上楼加收
		 */
		/*String rMethod =entity.getReceiveMethod();
		if(FossConstants.ACTIVE.equals(woodenPdaDto.getIsBigUp())
			&&
		 WaybillConstants.DELIVER_NOUP.equals(rMethod)){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setId(UUIDUtils.getUUID());
			//大件上楼加收
			chargeEntity.setPricingEntryCode("abcd");
			chargeEntity.setAmount(entity.getGoodsWeightTotal().multiply(new BigDecimal(1)));
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			otherFee=otherFee.add(chargeEntity.getAmount());
			chargeDtlEntityList.add(chargeEntity);
		}*/
		
		/**
		 * 超重货操作服务费
		 * 500kg - 1000 一件收100元
		 * 1000 -2000 一件收200元
		 */
		Integer fhToOtOverQty =woodenPdaDto.getFhToOtOverQty();
		Integer otToTtOverQty =woodenPdaDto.getOtToTtOverQty();
		WaybillChargeDtlEntity chargeDtl = null;
		//是否需计算超重货操作服务费
		boolean isok=true;
		if( fhToOtOverQty!=null && fhToOtOverQty>0){
			chargeDtl = new WaybillChargeDtlEntity();
			// 超重货操作服务费
			chargeDtl.setPricingEntryCode("CZHCZFWF");
			chargeDtl.setAmount(new BigDecimal(fhToOtOverQty*NumberConstants.NUMBER_100));
			chargeDtl.setWaybillNo(entity.getWaybillNo());
			chargeDtl.setPricingCriDetailId(UUIDUtils.getUUID());
			chargeDtl.setActive(FossConstants.ACTIVE);
			chargeDtl.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		}
		if(otToTtOverQty!=null && otToTtOverQty>0){
			if(chargeDtl==null){
				chargeDtl = new WaybillChargeDtlEntity();
				chargeDtl.setId(UUIDUtils.getUUID());
				chargeDtl.setPricingEntryCode("CZHCZFWF");
				chargeDtl.setAmount(new BigDecimal(otToTtOverQty*NumberConstants.NUMBER_200));
				chargeDtl.setWaybillNo(entity.getWaybillNo());
				chargeDtl.setPricingCriDetailId(UUIDUtils.getUUID());
				chargeDtl.setActive(FossConstants.ACTIVE);
				chargeDtl.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				chargeDtlEntityList.add(chargeDtl);
			}else{
				chargeDtl.setAmount(chargeDtl.getAmount().add(new BigDecimal(otToTtOverQty*NumberConstants.NUMBER_200)));
			}
		} 
		if(chargeDtl!=null){
			isok=false;
			otherFee=otherFee.add(chargeDtl.getAmount());
			chargeDtlEntityList.add(chargeDtl);
		}
		
		//如果没有超重服务费，从开单时暂存的其他费用取超重服务费
		if(isok){
			if(CollectionUtils.isNotEmpty(chargePendList)){
				for(WaybillCHDtlPendingEntity chDtl:chargePendList){
					String code = chDtl.getPricingEntryCode();
					if("CZHCZFWF_SDTJ".equals(code)|| "CZHCZFWF".equals(code)){
						WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
						chargeEntity.setId(UUIDUtils.getUUID());
						chargeEntity.setPricingEntryCode(code);
						chargeEntity.setAmount(chDtl.getAmount());
						chargeEntity.setWaybillNo(entity.getWaybillNo());
						chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
						chargeEntity.setActive(FossConstants.ACTIVE);
						chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						otherFee=otherFee.add(chargeEntity.getAmount());
						chargeDtlEntityList.add(chargeEntity);
						isok=false;
					}
				}
			}
		}
		//如果没有超重服务费，货物平均总量>500则添加超值服务费
		if(isok){
			//货物总量
			BigDecimal goodsWeightTotal= entity.getGoodsWeightTotal();
			//货物件数
			BigDecimal goodsQtyTotal =new BigDecimal( entity.getGoodsQtyTotal());
			BigDecimal pjzl =goodsWeightTotal.divide(goodsQtyTotal,BigDecimal.ROUND_HALF_UP);
			if(pjzl.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0){
				for(GuiResultBillCalculateDto dto:otherCalculateDtos){
					String code =dto.getSubType();
					if("CZHCZFWF".equals(code)){
						WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
						chargeEntity.setId(UUIDUtils.getUUID());
						chargeEntity.setPricingEntryCode(code);
						chargeEntity.setAmount(dto.getCaculateFee());
						chargeEntity.setWaybillNo(entity.getWaybillNo());
						chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
						chargeEntity.setActive(FossConstants.ACTIVE);
						chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						otherFee=otherFee.add(chargeEntity.getAmount());
						chargeDtlEntityList.add(chargeEntity);
						isok=false;
					}
				}
			}
		}
		
		/**
		 * 签收单费用
		 */
		ValueAddDto valueAddDto = setReturnBillCollection(entity);
		if(valueAddDto!=null){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setId(UUIDUtils.getUUID());
			chargeEntity.setPricingEntryCode(valueAddDto.getPriceEntityCode());
			chargeEntity.setAmount(valueAddDto.getCaculateFee());
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			otherFee=otherFee.add(chargeEntity.getAmount());
			chargeDtlEntityList.add(chargeEntity);
			//签收单折扣
			List<PriceDiscountDto> discountPrograms= valueAddDto.getDiscountPrograms();
			if(CollectionUtils.isNotEmpty(discountPrograms)){
				for (PriceDiscountDto dt : discountPrograms) {
					WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
					//优惠项目名称
	        		disDtlEntity.setPricingEntryName(dt.getPriceEntryName());
	        		//优惠项目CODE
	        		disDtlEntity.setPricingEntryCode(dt.getPriceEntryCode());
	        		//优惠类型名称
	        		disDtlEntity.setTypeName(dt.getTypeName());
	        		//优惠类型CODE
	    			disDtlEntity.setType(dt.getType());
	        		//优惠子类型名称
	        		disDtlEntity.setSubTypeName(dt.getSaleChannelName());
	        		//优惠子类型CODE
	    			disDtlEntity.setSubType(dt.getSaleChannelCode());
	    			//折扣费率
	        		disDtlEntity.setRate(dt.getDiscountRate());
	        		//折扣金额
	        		disDtlEntity.setAmount(dt.getReduceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
	        		//运单号
	        		disDtlEntity.setWaybillNo(entity.getWaybillNo());
	        		if(dt.getDiscountId()!=null){
	        			disDtlEntity.setDicountId(dt.getDiscountId());
	        		}else{
	        			disDtlEntity.setDicountId(UUIDUtils.getUUID());
	        		}
	        		if(dt.getChargeDetailId()!=null){
	        			disDtlEntity.setWaybillChargeDetailId(dt.getChargeDetailId());
	        		}else{
	        			disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
	        		}
	        		disDtlEntity.setCreateTime(new Date());
	        		disDtlEntity.setModifyTime(new Date());
	        		disDtlEntity.setActive(FossConstants.ACTIVE);
	        		disDtlEntity.setBillTime(new Date());
	        		disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	        		//营销活动编码
	        		disDtlEntity.setActiveCode(dt.getActiveCode());
	        		//营销活动名称
	        		disDtlEntity.setActiveName(dt.getActiveName());
	        		//营销活动开始时间
	        		disDtlEntity.setActiveStartTime(dt.getActiveStartTime());
	        		//营销活动结束时间
	        		disDtlEntity.setActiveEndTime(dt.getActiveEndTime());
	        		//营销活动折扣相应的CRM_ID
	        		disDtlEntity.setOptionsCrmId(dt.getOptionsCrmId());
	        		disDtlEntityList.add(disDtlEntity);
				}
			}
		}
		
		/**
		 * 到港代理过关费 
		 * 目的站为香港的货物，单票100kg（含）以下，加收150元/票，100kg以上，加收350元/票。
		 */
		String pickupOrgCode =entity.getCustomerPickupOrgCode();
		boolean isHK = isHK(entity.getCustomerPickupOrgCode());
	     //货物重量
	     BigDecimal goodsWeightTotal= entity.getGoodsWeightTotal();
	     //目的为香港的 才有商业区和住宅区
	     if(isHK){
	    	 if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_100))<0){
	    		 WaybillChargeDtlEntity  chargeDtlEntity= new WaybillChargeDtlEntity();
	    		 String type =ConfigurationParamsConstants.HK_GG_FEE_0To100;
				 ConfigurationParamsEntity config =queryConfigurationParamsByOrgCode(type,pickupOrgCode);
				 if(config!=null){
					 chargeDtlEntity.setAmount(new BigDecimal(config.getConfValue()));
				 }else{
					 chargeDtlEntity.setAmount(new BigDecimal(NumberConstants.NUMBER_150));
				 }
				 chargeDtlEntity.setId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setPricingEntryCode("DLGGF");
	    		 chargeDtlEntity.setWaybillNo(entity.getWaybillNo());
	    		 chargeDtlEntity.setPricingCriDetailId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setActive(FossConstants.ACTIVE);
	    		 chargeDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    		 otherFee=otherFee.add(chargeDtlEntity.getAmount());
				 chargeDtlEntityList.add(chargeDtlEntity);
	    	 }else{
	    		 WaybillChargeDtlEntity  chargeDtlEntity= new WaybillChargeDtlEntity();
	    		 String type =ConfigurationParamsConstants.HK_GG_FEE_100ToBG;
				 ConfigurationParamsEntity config =queryConfigurationParamsByOrgCode(type,pickupOrgCode);
				 if(config!=null){
					 chargeDtlEntity.setAmount(new BigDecimal(config.getConfValue()));
				 }else{
					 chargeDtlEntity.setAmount(new BigDecimal(NumberConstants.NUMBER_350));
				 }
				 chargeDtlEntity.setId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setPricingEntryCode("DGDLGGF");
	    		 chargeDtlEntity.setWaybillNo(entity.getWaybillNo());
	    		 chargeDtlEntity.setPricingCriDetailId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setActive(FossConstants.ACTIVE);
	    		 chargeDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    		 otherFee=otherFee.add(chargeDtlEntity.getAmount());
				chargeDtlEntityList.add(chargeDtlEntity);
	    	 }
	    	
	     }
		
		return otherFee;
		
	}

	/**
	 * 功能：submitWaybill提交运单信息
	 * @author 076234 PanGuoYang
	 * @param:
	 * @return:void
	 * @since:1.6
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
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
			waybillManagerService.submitWaybill(waybill);
		}finally{
			//释放锁
			businessLockService.unlock(mutexes);
		}			
	}
	/**
	 * 获取开单司机信息
	 * @author 076234 PanGuoYang
	 * @param billUserNo
	 * @return
	 */
	public CurrentInfo getCurrentInfo(String billUserNo){
		// pda补录消息提醒,通知司机所在车队的集中开单组
		UserEntity userEntity = new UserEntity();
		CurrentInfo currentInfo;
		if (billUserNo != null) {
			EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(billUserNo);
			if (employEntity != null) {
				userEntity.setEmpCode(billUserNo);
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmployee(employEntity);
				userEntity.setUserName(employEntity.getEmpName());
				currentInfo= new CurrentInfo (userEntity,employEntity.getDepartment());
				// 转换成HttpServletRequest
				javax.servlet.http.HttpSession session=new MockHttpSession();

				// 初始化sessionContext
				SessionContext.setSession(session);
				
				if(employEntity.getDepartment()!=null){
					if(StringUtil.isNotBlank(employEntity.getDepartment().getCode())){
						SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",
								(Object)employEntity.getDepartment().getCode());
					}				
					if(StringUtil.isNotBlank(employEntity.getDepartment().getName())){
						SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",
								(Object)employEntity.getDepartment().getName());
					}
				}
				
			} else {
				throw new PdaInterfaceException("查询不到该司机！");
			}
		}else{
			throw new PdaInterfaceException("司机编号为空！");
		}
		
		UserContext.setCurrentUser(userEntity);
		return currentInfo;
		
	}
	/**
	 * 应用监控数据添加
	 * @author 076234-FOSS-PanGuoYang
	 */
	public void businessMonitor(WaybillDto waybillDto) {
		waybillManagerService.businessMonitor(waybillDto);
	}
	
	
	
	/**
	 * @author	076234 PanGuoYang
	 * @param bean
	 */
	public List<LabeledGoodEntity>  getLabeledGoodEntities(WaybillEntity entity,WoodenRequirePdaDto woodenPdaDto){
		List<LabeledGoodEntity>   labeledGoodEntitys= new ArrayList<LabeledGoodEntity>();
		if(woodenPdaDto!=null && woodenPdaDto.getWoodenStockNum()!=null && woodenPdaDto.getWoodenStockNum()>0){
			Integer woodenStockNum = woodenPdaDto.getWoodenStockNum() ;
			Integer goodsTotal = entity.getGoodsQtyTotal();
			if(goodsTotal != null && goodsTotal > 0){
				for(int i = 0; i < goodsTotal; i++){
					LabeledGoodEntity labeledGoodEntity = new LabeledGoodEntity();
					String serialNo = StringHandlerUtil.lpad(String.valueOf(i + 1), NumberConstants.NUMBER_4, "0");
					labeledGoodEntity.setSerialNo(serialNo);
					if(i<woodenStockNum){
						labeledGoodEntity.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
					}
					labeledGoodEntitys.add(labeledGoodEntity);
				}
				return labeledGoodEntitys;
			}
		}else{
			labeledGoodEntitys =labeledGoodDao.queryAllSerialByWaybillNo(entity.getWaybillNo());
		}
		
		if(CollectionUtils.isNotEmpty(labeledGoodEntitys)){
			return labeledGoodEntitys;
		}else{
			Integer goodsTotal = entity.getGoodsQtyTotal();
			if(goodsTotal != null && goodsTotal > 0){
				
				for(int i = 0; i < goodsTotal; i++){
					LabeledGoodEntity labeledGoodEntity = new LabeledGoodEntity();
					String serialNo = StringHandlerUtil.lpad(String.valueOf(i + 1), NumberConstants.NUMBER_4, "0");
					labeledGoodEntity.setSerialNo(serialNo);
		            //labeledGoodEntity.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
					labeledGoodEntitys.add(labeledGoodEntity);
				}
				return labeledGoodEntitys;
			}
		}
		return null;	 
	}
	/**
	 * 
	 * <p>
	 * 运单其他信息封装<br />
	 * </p>
	 * @author PanGuoYang
	 * @return
	 * ActualFreightEntity
	 */
	private ActualFreightEntity getActualFreightEntity(WaybillEntity waybillEntity,WaybillPendingEntity pendingEntity,List<WaybillChargeDtlEntity> chargeDtlEntityList,GuiResultBillCalculateDto dto){
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		//收货地址
		actualFreightEntity.setReceiveCustomerAddressNote(pendingEntity.getReceiveCustomerAddressNote());
		//发货地址
		actualFreightEntity.setDeliveryCustomerAddressNote(pendingEntity.getDeliveryCustomerAddressNote());
		//开始开单时间
		actualFreightEntity.setStartBillTime(waybillEntity.getBillTime());
		//运单号
		actualFreightEntity.setWaybillNo(waybillEntity.getWaybillNo());
		//货物名称
		actualFreightEntity.setGoodsName(waybillEntity.getGoodsName());
		//重量
		actualFreightEntity.setWeight(waybillEntity.getBillWeight());
		//体积
		actualFreightEntity.setVolume(waybillEntity.getGoodsVolumeTotal());
		//件数
		actualFreightEntity.setGoodsQty(waybillEntity.getGoodsQtyTotal());
		//尺寸
		actualFreightEntity.setDimension(StringUtils.isNotEmpty(waybillEntity.getGoodsSize()) ? waybillEntity.getGoodsSize() : "1*1*1*1");
		//保险声明价值
		actualFreightEntity.setInsuranceValue(waybillEntity.getInsuranceAmount());
		//包装费
		actualFreightEntity.setPackingFee( waybillEntity.getPackageFee()!=null ? waybillEntity.getPackageFee() : BigDecimal.valueOf(0));
		//送货费
		actualFreightEntity.setDeliverFee( waybillEntity.getDeliveryGoodsFee() != null ? waybillEntity.getDeliveryGoodsFee(): BigDecimal.valueOf(0));
		//装卸费
		actualFreightEntity.setLaborFee( waybillEntity.getServiceFee()!=null ? waybillEntity.getServiceFee() :  BigDecimal.valueOf(0));
		//代收货款
		actualFreightEntity.setCodAmount( waybillEntity.getCodAmount() != null ? waybillEntity.getCodAmount(): BigDecimal.valueOf(0));
		//增值费
		actualFreightEntity.setValueaddFee( waybillEntity.getValueAddFee() !=null ? waybillEntity.getValueAddFee(): BigDecimal.valueOf(0));
		//公布价运费
		actualFreightEntity.setFreight( waybillEntity.getTransportFee()!=null ? waybillEntity.getTransportFee(): BigDecimal.valueOf(0));
		//结清状态 
		actualFreightEntity.setSettleStatus(FossConstants.NO);
		//结清时间
		actualFreightEntity.setSettleTime(new Date());
		//通知状态
		//actualFreightEntity.setNotificationType(vo.get)
		//通知时间
		//actualFreightEntity.setNotificationTime(vo);
		//送货时间 
		//actualFreightEntity.setDeliverDate(vo.getPreCustomerPickupTime());
		//实际送货方式
		actualFreightEntity.setActualDeliverType(waybillEntity.getReceiveMethod());
		//运单状态
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);
		//库存天数
		//actualFreightEntity.setStorageDay(vo.getst)
		//库存费用
		//actualFreightEntity.setStorageCharge(vo.get);
		actualFreightEntity.setStartStockOrgCode(queryStartStocksDepartmentService(waybillEntity));
		actualFreightEntity.setEndStockOrgCode(queryEndStocksDepartmentService(waybillEntity));
		//actualFreightEntity.setActualDeliverType(vo.getdelivery)
		// 已生效
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);
		//是否是大票货
        if(waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0 
        		||
        		waybillEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(POINT_25)) >0){	
        	actualFreightEntity.setBigTicketGoods(FossConstants.YES);
		} 
		//含有超重费不是大票货
		/*for(WaybillChargeDtlEntity chargeDtl:chargeDtlEntityList){
			String chargecode = chargeDtl.getPricingEntryCode();
			if("CZHCZFWF_SDTJ".equals(chargecode)||"CZHCZFWF".equals(chargecode)){
				actualFreightEntity.setBigTicketGoods(FossConstants.NO);
			}
		}
		//内部带货 不是大票货
		if (WaybillConstants.INNER_PICKUP.equals(waybillEntity.getReceiveMethod())) {
			actualFreightEntity.setBigTicketGoods(FossConstants.NO);
		}*/
		
		//整车开单的时候 对actual Freight表的字段做如下调整
		if(waybillEntity.getIsWholeVehicle()!= null && FossConstants.ACTIVE.equals(waybillEntity.getIsWholeVehicle())){
			//1  ARRIVE_GOODS_QTY = 开单件数
			actualFreightEntity.setArriveGoodsQty(waybillEntity.getGoodsQtyTotal());
			//2  ARRIVE_NOTOUT_GOODS_QTY 到达未签收件数  = 开单件数
			actualFreightEntity.setArriveNotoutGoodsQty(waybillEntity.getGoodsQtyTotal());
			//3  若预计到达时间 不为空则为预计到达时间，否则为当前时间
			//预计到达时间
			Date preArrivedTime = waybillEntity.getPreCustomerPickupTime();
			if(null != preArrivedTime){
				actualFreightEntity.setArriveTime(preArrivedTime);
			}else{
				actualFreightEntity.setArriveTime(new Date());
			}
		}
	 /* if(waybillEntity.getOrderNo()!=null){
		  CrmOrderDetailDto crmOrderDetailDto = crmOrderService.importOrder(waybillEntity.getOrderNo());
			//保存官网用户名
		  actualFreightEntity.setChannelCustId(crmOrderDetailDto.getChannelCustId());
	  } */
		
	  //保存官网用户名
	   actualFreightEntity.setChannelCustId(pendingEntity.getChannelCustId());
	  //住宅区
	  actualFreightEntity.setBusinessZone(pendingEntity.getBusinessZone());
	  //商业区
	  actualFreightEntity.setResidentialDistrict(pendingEntity.getResidentialDistrict());
	  //包装备注
	  actualFreightEntity.setPackageRemark(pendingEntity.getPackageRemark());
		//发票标记
		if(StringUtil.isNotEmpty(pendingEntity.getInvoice())){
			actualFreightEntity.setInvoice(pendingEntity.getInvoice());
		}else{
			actualFreightEntity.setInvoice(WaybillConstants.INVOICE_02);
		}
		//最低一票标记
		if(dto !=null) {
			actualFreightEntity.setMinTransportFee(dto.getMinFee());
		}
		return actualFreightEntity;
	}
	/**
	 * 查询基础信息
	 * @param waybillEntry
	 * @return
	 * @author 076234 PanGuoYang
	 */
	private String queryEndStocksDepartmentService(WaybillEntity waybillEntry) {
		if (waybillEntry == null) {
			throw new WaybillStoreException("运单基础信息不能为空");
		}
		if (waybillEntry.getLastLoadOrgCode() == null) {
			throw new WaybillStoreException("最终配载部门为空");
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
	 * 获取优惠券信息
	 * @param chargeList
	 * @param pendingEntity
	 * @author 076234 PanGuoYang
	 */
	private CouponInfoDto getCouponInfoDto(
			List<WaybillChargeDtlEntity> chargeList,
			BigDecimal totalFee,
			WaybillPendingEntity entity,
			List<WaybillDisDtlEntity> disDtlEntityList,
			GuiResultBillCalculateDto gDto) {
		// 优惠信息DTO
		CouponInfoDto couponInfo = new CouponInfoDto();
		if (!StringUtils.isEmpty(entity.getPromotionsCode())) {
			/**
			 * 不是整车才处理优惠券，因为整车没有走货路径，获取最终配载部门时会报异常			
			 */
			
			if(!FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())){
				/**
				 * 不是内部发货，才处理优惠券
				 */
				if(StringUtil.isBlank(entity.getInternalDeliveryType())
						|| StringUtil.isBlank(entity.getEmployeeNo()))
				{
				
				new CouponInfoDto();
				// 处理优惠券
				// 运单信息
				CouponWaybillInfoDto waybillInfo = new CouponWaybillInfoDto();
				// 运单号
				waybillInfo.setWaybillNumber(entity.getWaybillNo());
				// 产品号
				waybillInfo.setProduceType(entity.getProductCode());
				// 订单号
				waybillInfo.setOrderNumber(entity.getOrderNo());
				// 判断总金额是否已有
				if (totalFee != null && totalFee.compareTo(BigDecimal.ZERO) == 0) {
					throw new PdaInterfaceException("计算总金额为0！");
				}

				// 总金额
				waybillInfo.setTotalAmount(totalFee);
				// 发货人手机
				waybillInfo.setLeaveMobile(entity.getDeliveryCustomerMobilephone());
				// 发货人电话
				waybillInfo.setLeavePhone(entity.getDeliveryCustomerPhone());
				// 客户编码
				waybillInfo.setCustNumber(entity.getDeliveryCustomerCode());
				// 获取出发部门
				String receiveOrgCode = entity.getReceiveOrgCode();

				OrgAdministrativeInfoEntity receiveOrgAdministrative = waybillManagerService.queryOrgInfoByCode(receiveOrgCode);

				if (receiveOrgAdministrative == null) {
					throw new PdaInterfaceException("未查询出有效的出发部门！");
				}

				// 发货部门-标杆编码
				waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());

				if (entity.getLastLoadOrgCode() == null) {
					throw new PdaInterfaceException("未获取到有效的最终配载部门编码！");
				}
				// 最终配载部门-也就是最后一个自有网点
				String lastLoadOrgCode = entity.getLastLoadOrgCode();

				OrgAdministrativeInfoEntity lastLoadOrgAdministrative = waybillManagerService.queryOrgInfoByCode(lastLoadOrgCode);
				if (lastLoadOrgAdministrative == null) {
					throw new PdaInterfaceException("未查询出有效的最终配载部门！");
				}
				if (entity.getLoadOrgCode() == null) {
					throw new PdaInterfaceException("未获取到有效的始发配载部门编码！");
				}

				// 始发配载部门
				String firstLoadOutOrgInfoCode = waybillManagerService.queryOrgInfoByCode(entity.getLoadOrgCode()).getUnifiedCode();
				// 最终配载部门 标杆编码
				String lastLoadOutOrgInfoCode = null;
				if (!StringUtils.isEmpty(entity.getLastOutLoadOrgCode())) {
					// 获取最终配载部门 标杆编码
					lastLoadOutOrgInfoCode =  waybillManagerService.queryOrgInfoByCode(entity.getLastOutLoadOrgCode()).getUnifiedCode();
				} else {

					throw new PdaInterfaceException("最终配载部门编码为空");
				}

				// 到达部门-标杆编码-由于偏线最后到达部门是代理，这里是最后一个只有部门
				waybillInfo.setArrivedDept(lastLoadOrgAdministrative.getUnifiedCode());

				// 发货部门所在外场
				waybillInfo.setLeaveOutDept(firstLoadOutOrgInfoCode);
				// 到达部门所在外场
				waybillInfo.setArrivedOutDept(lastLoadOutOrgInfoCode);

				//WaybillOtherCharge model = (WaybillOtherCharge) ui.incrementPanel.getTblOther().getModel();
				// 获取费用明细
				//List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = WaybillDtoFactory.getWaybillChargeDtlEntity(model, bean);
				List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
				BigDecimal deliverGoodsFee= BigDecimal.ZERO;
				
				for (WaybillChargeDtlEntity waybillChargeDtlEntity : chargeList) { // 计价明细
					 		
					if(PriceEntityConstants.PRICING_CODE_SH.equals(waybillChargeDtlEntity.getPricingEntryCode())
							|| PriceEntityConstants.PRICING_CODE_SHSL.equals(waybillChargeDtlEntity.getPricingEntryCode()) 
							|| PriceEntityConstants.PRICING_CODE_SHJC.equals(waybillChargeDtlEntity.getPricingEntryCode())){
						deliverGoodsFee = deliverGoodsFee.add(waybillChargeDtlEntity.getAmount());
					}
				}
				
				for (WaybillChargeDtlEntity waybillChargeDtlEntity : chargeList) { // 计价明细
					AmountInfoDto amountInfo = new AmountInfoDto();
								
					if(PriceEntityConstants.PRICING_CODE_SH.equals(waybillChargeDtlEntity.getPricingEntryCode())
							|| PriceEntityConstants.PRICING_CODE_SHSL.equals(waybillChargeDtlEntity.getPricingEntryCode()) 
							|| PriceEntityConstants.PRICING_CODE_SHJC.equals(waybillChargeDtlEntity.getPricingEntryCode())){
						// 计价条目编码-送货费
						amountInfo.setValuationCode(PriceEntityConstants.PRICING_CODE_SH);
						// 获取通过计算得到的送货费
						amountInfo.setValuationAmonut(deliverGoodsFee);
					}else{
						// 计价条目编码-保险费
						amountInfo.setValuationCode(waybillChargeDtlEntity.getPricingEntryCode());
						// 计价金额
						amountInfo.setValuationAmonut(waybillChargeDtlEntity.getAmount());				
					}
					amountInfoList.add(amountInfo);
				}
				waybillInfo.setAmountInfoList(amountInfoList);
				couponInfo.setWaybillInfo(waybillInfo);
				couponInfo.setCouponNumber(entity.getPromotionsCode());
			    }					
			}
			
		}
		if (couponInfo.getCouponNumber()!=null ) {

			CouponInfoResultDto dto = crmOrderService.validateCoupon(couponInfo);
			if (dto != null) {
				if (!dto.isCanUse()) {			
					
					String canNotUseReason = StringUtil.defaultIfNull(dto.getCanNotUseReason());
					String waybill = StringUtils.substringBetween(canNotUseReason, ":", ";");

					// 判断：该优惠券是否是被该运单使用的（从不可使用原因的字符串中截取使用该优惠券的运单号）
					if (!entity.getWaybillNo().equals(StringUtil.defaultIfNull(waybill).trim())) {
						// 不能使用优惠券的原因
						//MsgBox.showInfo(canNotUseReason);
						couponInfo.setCouponNumber(null);
						couponInfo.setCouponFree(BigDecimal.ZERO);
						couponInfo.setRemark(canNotUseReason);
					}else{
						String lastAmount = StringUtils.substringAfterLast(canNotUseReason, "value:");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, ")");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, "}");
						
						/**
						 * 设置优惠券费用
						 */
						try{
							couponInfo.setCouponFree(new BigDecimal(lastAmount));							
						}catch(Exception e){
							couponInfo.setCouponFree(BigDecimal.ZERO);
						}
						/**
						 * 设置优惠券返回实体
						 */
						//bean.setCouponInfoDto(couponInfoDto);
						
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						couponInfo.setCouponRankType(dto.getDeductibleType());
					}
					
				} else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						/**
						 * 设置优惠券费用
						 */
						couponInfo.setCouponFree(dto.getCouponAmount());
						/**
						 * 设置优惠券返回实体
						 */
						//bean.setCouponInfoDto(couponInfoDto);
						
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						couponInfo.setCouponRankType(dto.getDeductibleType());
					} else {
						couponInfo.setCouponNumber(null);
						//bean.setPromotionsCode("");
					}
				}
				
			}
			
		}
		//优化折扣
		if(couponInfo.getCouponFree()!=null && BigDecimal.ZERO.compareTo(couponInfo.getCouponFree()) != 0){
			couponInfo.setUsed(true);
			WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
			disDtlEntity.setId(UUIDUtils.getUUID());
			disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
    		//优惠项目名称
    		disDtlEntity.setPricingEntryName(convertFeeToName(couponInfo.getCouponRankType()));
    		//优惠项目CODE
    		disDtlEntity.setPricingEntryCode(couponInfo.getCouponRankType());
    		//优惠类型名称
    		disDtlEntity.setTypeName("优惠券");
    		//优惠类型CODE
			disDtlEntity.setType("DISCOUNT");
			
			//RFOSS2015052801（DN201505260016）  取消优惠券最低一票校验  --206860
			BigDecimal	transportFee=gDto.getCaculateFee();
			if(transportFee != null){
				//当公布价运费不为空且公布价运费-优惠券费用小于0时，将优惠券金额设置为运费，即最大冲减为公布价运费
				if((transportFee.subtract(couponInfo.getCouponFree())).compareTo(BigDecimal.ZERO) < 0){
					disDtlEntity.setAmount(transportFee.setScale(0, BigDecimal.ROUND_HALF_UP));
				}else{
					disDtlEntity.setAmount(couponInfo.getCouponFree().setScale(0, BigDecimal.ROUND_HALF_UP));
				}
			}
    		//运单号
    		disDtlEntity.setWaybillNo(entity.getWaybillNo());
    		
    		disDtlEntity.setDicountId(entity.getPromotionsCode());
    		 
    		disDtlEntity.setCreateTime(new Date());
    		disDtlEntity.setModifyTime(new Date());
    		disDtlEntity.setActive(FossConstants.ACTIVE);
    		disDtlEntity.setBillTime(new Date());
    		disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
    		disDtlEntityList.add(disDtlEntity);
		}
		if(couponInfo.getCouponFree()==null || BigDecimal.ZERO.compareTo(couponInfo.getCouponFree()) == 0){
			couponInfo.setUsed(false);
		}
		return couponInfo;	
	}
    
	/**
	 * 设置付款明细
	 * @param entity
	 * @param dto
	 * @param paymentEntityList
	 * @author 076234 PanGuoYang
	 */
	private BigDecimal setWaybillPaymentEntity(GuiResultBillCalculateDto dto,
			GuiResultBillCalculateDto codCollection,
			List<DeliverChargeEntity> delivetList,
			GuiResultBillCalculateDto dtoJH,
			GuiResultBillCalculateDto insuranceCollection,
			WaybillChargeDtlEntity packageCharge,
			BigDecimal otherFee,
			WaybillPendingEntity entity,
			WaybillEntity waybillEntity,
			List<WaybillPaymentEntity> paymentEntityList,
			CouponInfoDto couponInfoDto,
			WoodenRequirePdaDto woodenPdaDto
			) {
		    paymentEntityList.clear();
		   
		    //运费
			BigDecimal	transportFee=dto.getCaculateFee();
			
			//增值费
			BigDecimal	valueAddFee= BigDecimal.ZERO;
			//优惠金额
			BigDecimal  couponFree= BigDecimal.ZERO;
			//包装费
			BigDecimal  packageFee = BigDecimal.ZERO;
			String couponRankType= null;
			if(couponInfoDto!=null && couponInfoDto.getCouponFree()!=null){
				couponFree=couponInfoDto.getCouponFree();
				couponRankType = couponInfoDto.getCouponRankType();
			}
			/**
			 * 若冲减类型为运费时，才可以对运费进行冲减
			 * MANA-1961 营销活动与优惠券编码关联
			 * 2014-04-10 026123
			 */
			if(couponFree!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(couponRankType)){
				couponFree = couponFree.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			}else{
				couponFree=BigDecimal.ZERO;
			}
			transportFee=transportFee.subtract(couponFree);	
			//RFOSS2015052801（DN201505260016）  取消优惠券最低一票校验  --206860
			if(transportFee!=null){
				//当运费小于0，将运费修改为0
				if(transportFee.compareTo(BigDecimal.ZERO) < 0){
					transportFee = BigDecimal.ZERO;
				}
			}
			
			//优惠费用
			BigDecimal promotionsFee = waybillEntity.getPromotionsFee()!=null?waybillEntity.getPromotionsFee():BigDecimal.ZERO;
			waybillEntity.setPromotionsFee(promotionsFee);
			
			//代收货款手续费
			BigDecimal codFee = BigDecimal.ZERO;
			//代收货款
			BigDecimal codAmount = waybillEntity.getCodAmount();
			if(codCollection!=null){
				codFee=codCollection.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(codFee);
				waybillEntity.setCodFee(codFee);
				waybillEntity.setCodRate(codCollection.getFeeRate());
			}else{
				waybillEntity.setCodFee(BigDecimal.ZERO);
				waybillEntity.setCodRate(BigDecimal.ZERO);
			}
			
			//送货费
			BigDecimal deliverFee = BigDecimal.ZERO;
			if(CollectionUtils.isNotEmpty(delivetList)){
				for(DeliverChargeEntity deliver:delivetList){
					  deliverFee = deliverFee.add(deliver.getAmount());
				}
				deliverFee=deliverFee.setScale(0, BigDecimal.ROUND_HALF_UP);
				if(deliverFee.compareTo(BigDecimal.ZERO)!=0){
					waybillEntity.setDeliveryGoodsFee(deliverFee);
				}
				
				valueAddFee=valueAddFee.add(deliverFee);
				
			}
			//接货费
			if(dtoJH!=null){
				BigDecimal jh =dtoJH.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(jh);
				waybillEntity.setPickupFee(jh);
			}
			//保价费
			if(insuranceCollection!=null){
				BigDecimal insurance=insuranceCollection.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(insurance);
				waybillEntity.setInsuranceFee(insurance);
				waybillEntity.setInsuranceRate(insuranceCollection.getFeeRate());
			}else{
				waybillEntity.setInsuranceFee(BigDecimal.ZERO);
				waybillEntity.setInsuranceRate(BigDecimal.ZERO);
			}
			
			//包装费
			if(packageCharge!=null){
				packageFee=packageCharge.getAmount();
			}
			waybillEntity.setPackageFee(packageFee.setScale(0, BigDecimal.ROUND_HALF_UP));
			valueAddFee=valueAddFee.add(packageFee.setScale(0, BigDecimal.ROUND_HALF_UP));
			
			//其他费用
			if(otherFee.compareTo(BigDecimal.ZERO)>0){
				otherFee=otherFee.setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(otherFee);
				waybillEntity.setOtherFee(otherFee);
			}
			
			//增值费用
			waybillEntity.setValueAddFee(valueAddFee);
			
			
			//计费规则
			String caculateType =dto.getCaculateType();
			waybillEntity.setBillingType(caculateType);
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())){
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)){
					BigDecimal  volumeTotal = waybillEntity.getGoodsVolumeTotal();
					volumeTotal=volumeTotal.multiply(FossConstants.VOLUME_TO_WEIGHT);
					waybillEntity.setUnitPrice(transportFee.divide(volumeTotal,2, BigDecimal.ROUND_HALF_DOWN));
				}
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)){
					waybillEntity.setUnitPrice(transportFee.divide(entity.getGoodsWeightTotal(),2, BigDecimal.ROUND_HALF_DOWN));
				}
			}else{
				//运费费率
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)){
					waybillEntity.setUnitPrice(transportFee.divide(entity.getGoodsWeightTotal(),2, BigDecimal.ROUND_HALF_DOWN));
				}
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)){
					waybillEntity.setUnitPrice(transportFee.divide(entity.getGoodsVolumeTotal(),2, BigDecimal.ROUND_HALF_DOWN));
				}
			}
			
			//公布价运费
			transportFee=transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			//公布价运费
			waybillEntity.setTransportFee(transportFee);
			//总费用   =公布价运费+增值费用(增值费用=代收货款手续费+送货费+接货费+保价费+打木架费+打木箱费+其他费用)+代收货款
			BigDecimal totalFee=(transportFee.add(valueAddFee).add(codAmount)).setScale(0, BigDecimal.ROUND_HALF_UP);
			waybillEntity.setTotalFee(totalFee);
			
			//判断是否为PAD导入开单
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(entity.getPendingType())) {
				WaybillPaymentEntity totalFeePDA = new WaybillPaymentEntity();
				totalFeePDA.setWaybillNo(entity.getWaybillNo());
				totalFeePDA.setType(WaybillConstants.PAYMENT_PDA_TOTAL_PAY);//pda总运费类型
				totalFeePDA.setAmount(totalFee);
				totalFeePDA.setActive(FossConstants.ACTIVE);
				totalFeePDA.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
				totalFeePDA.setPaymentTime(new Date());
				paymentEntityList.add(totalFeePDA);
				
			}
			
			//1.当预付金额为“0”时（开单员没有输入预付金额），计算总运费后，“预付金额”与“到付金额”文本框规则不变
			if(entity.getPrePayAmount() ==null){
				entity.setPrePayAmount(BigDecimal.ZERO);
			}
			
			if(entity.getPrePayAmount().equals(BigDecimal.ZERO) ){
			if(entity.getPaidMethod()!=null){
				if (WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())) {
					WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
					toPayAmount.setWaybillNo(entity.getWaybillNo());
					toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);// 到付金额类型
					toPayAmount.setAmount(totalFee);// 到付金额
					toPayAmount.setActive(FossConstants.ACTIVE);
					toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
					toPayAmount.setPaymentTime(new Date());
					waybillEntity.setToPayAmount(toPayAmount.getAmount());
					paymentEntityList.add(toPayAmount);
				} else {
					//预付
					WaybillPaymentEntity prementEntity = new WaybillPaymentEntity();
					prementEntity.setWaybillNo(entity.getWaybillNo());
					prementEntity.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
					prementEntity.setAmount(totalFee.subtract(codAmount));//预付金额
					prementEntity.setActive(FossConstants.ACTIVE);
					prementEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
					prementEntity.setPaymentTime(new Date());
					waybillEntity.setPrePayAmount(prementEntity.getAmount());
					paymentEntityList.add(prementEntity);
					//到付
					WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
					toPayAmount.setWaybillNo(entity.getWaybillNo());
					toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);//到付金额类型
					toPayAmount.setAmount(codAmount);//到付金额
					toPayAmount.setActive(FossConstants.ACTIVE);
					toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
					toPayAmount.setPaymentTime(new Date());
					waybillEntity.setToPayAmount(toPayAmount.getAmount());
					paymentEntityList.add(toPayAmount);								
				}
			}else{
				WaybillPaymentEntity prePayAmount = new WaybillPaymentEntity();
				prePayAmount.setWaybillNo(entity.getWaybillNo());
				prePayAmount.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
				prePayAmount.setAmount(totalFee);//预付金额
				prePayAmount.setActive(FossConstants.ACTIVE);
				prePayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
				prePayAmount.setPaymentTime(new Date());
				waybillEntity.setPrePayAmount(prePayAmount.getAmount());
				paymentEntityList.add(prePayAmount);
			}
			}
			//2.当预付金额不为“0”时（开单员输入预付金额数字）
			else{
     			//(1)当预付金额≤总运费时，foss自动生成运单，到付金额=总运费-预付金额
				//(2)当预付金额>总运费（不含代收货款）时，此单生成异常运单，且提醒“预付金额大于总运费（不含代收货款），请核实更改”
				BigDecimal prePayAmounts = entity.getPrePayAmount();
				if (entity.getPrePayAmount().compareTo(totalFee.subtract(codAmount)) <= 0) {
					//预付
					//huangwei 2015.11.14 付款方式为非到付的运单，若图片录入时预付金额≠0，取消（到付金额=总运费-预付金额）规则；即：非到付的运单，补录重量体积后，不生成到付金额（除含有代收货款运单）
					if(entity.getPaidMethod()!=null && WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())){
						//预付
						WaybillPaymentEntity prementEntity = new WaybillPaymentEntity();
						prementEntity.setWaybillNo(entity.getWaybillNo());
						prementEntity.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
						prementEntity.setAmount(prePayAmounts);//预付金额
						prementEntity.setActive(FossConstants.ACTIVE);
						prementEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
						prementEntity.setPaymentTime(new Date());
						waybillEntity.setPrePayAmount(prementEntity.getAmount());
						paymentEntityList.add(prementEntity);
					//到付
						WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
						toPayAmount.setWaybillNo(entity.getWaybillNo());
						toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);//到付金额类型
						toPayAmount.setAmount(totalFee.subtract(prePayAmounts));//到付金额
						toPayAmount.setActive(FossConstants.ACTIVE);
						toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
						toPayAmount.setPaymentTime(new Date());
						waybillEntity.setToPayAmount(toPayAmount.getAmount());
						paymentEntityList.add(toPayAmount);		
					}else{
						WaybillPaymentEntity prePayAmount = new WaybillPaymentEntity();
						prePayAmount.setWaybillNo(entity.getWaybillNo());
						prePayAmount.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
						prePayAmount.setAmount(totalFee);//预付金额
						prePayAmount.setActive(FossConstants.ACTIVE);
						prePayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
						prePayAmount.setPaymentTime(new Date());
						waybillEntity.setPrePayAmount(prePayAmount.getAmount());
						paymentEntityList.add(prePayAmount);
					}
				}else{
					throw new PdaInterfaceException("预付金额大于总运费（不含代收货款），请核实更改");
				}
			}
			return  totalFee;
		
	}
	
	
	/**
	 * 根据费用类型编码获取对应中文名
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 076234
	 */
	public  String convertFeeToName(String code){
		 
		//费用名称
		String name = "";
		if(PriceEntityConstants.PRICING_CODE_TOTAL.equals(code)){
			name = "总费用";
		}else if(PriceEntityConstants.PRICING_CODE_FRT.equals(code)){
			name = "运费";
		}else if(PriceEntityConstants.PRICING_CODE_VALUEADDED.equals(code)){
			name = "增值服务";
		}else if(PriceEntityConstants.PRICING_CODE_BF.equals(code)){
			name = "保费";
		}else if(PriceEntityConstants.PRICING_CODE_HK.equals(code)){
			name = "代收货款";
		}else if(PriceEntityConstants.PRICING_CODE_SH.equals(code)){
			name = "送货费";
		}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(code)){
			name = "送货上楼费";
		}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(code)){
			name = "送货进仓费";
		}else if(PriceEntityConstants.PRICING_CODE_CY.equals(code)){
			name = "超远派送费";
		}else if(PriceEntityConstants.PRICING_CODE_JH.equals(code)){
			name = "接货费";
		}else if(PriceEntityConstants.PRICING_CODE_CCF.equals(code)){
			name = "仓储费";
		}else if(PriceEntityConstants.PRICING_CODE_QT.equals(code)){
			name = "其他费用";
		}else if(PriceEntityConstants.PRICING_CODE_BZ.equals(code)){
			name = "包装费用";
		}else if(PriceEntityConstants.PRICING_CODE_ZZ.equals(code)){
			name = "中转费";
		}else if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)){
			name = "综合信息服务费";
		}else if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(code)){
			name = "快递包装费";
		}
		return name;
	}
	
	/**
	 * 获取装卸费
	 * @param entity
	 * @return
	 * @author 076234 PanGuoYang
	 */
	private BigDecimal getServiceFee(WaybillPendingEntity entity,GuiResultBillCalculateDto dto){
		 //运费
		BigDecimal	transportFee=dto.getCaculateFee();
		//装卸费
		BigDecimal serviceFee =BigDecimal.ZERO;
		//装卸费百分比
		BigDecimal serviceRate = entity.getServiceRate();
		if(serviceRate!=null){
			serviceRate=serviceRate.divide(new BigDecimal(NumberConstants.NUMBER_100));
		}
		
		/***********************add by hehaisu**************************/
		//读取装卸费率系统配置参数
		//初始化15%
	    BigDecimal  pCT15 =	new BigDecimal(NumberConstants.NUMBER_15).divide(new BigDecimal(NumberConstants.NUMBER_100));
	    String productCode = entity.getProductCode();
		// 调用接口读取装卸费费率
		ConfigurationParamsEntity paramsEntity = null;
		if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode))
		{
			paramsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
					ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO,entity.getReceiveOrgCode());
		}else
		{
			paramsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
					ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO,entity.getReceiveOrgCode());
		}
		
		if (paramsEntity == null) {
			throw new WaybillValidateException("当前部门不存在装卸费比率，无法进行装卸费计算");
		} else {
			// 判断是否存在装卸费费率
			if (paramsEntity.getConfValue() == null) {
				throw new WaybillValidateException("装卸费率为空");
			} else {
				pCT15 = new BigDecimal(paramsEntity.getConfValue());
			}
		}
		/******************************end***********************************/
		
		//图片装卸费
		BigDecimal service=entity.getServiceFee();
		if(serviceRate!=null && service!=null){
		    //百分15的运费
		    BigDecimal freePCT15 = transportFee.multiply(pCT15);
		    //装卸费大于百分15的运费
		    if(service.compareTo(freePCT15)>0){
		    	serviceFee=transportFee.multiply(serviceRate).compareTo(BigDecimal.ZERO)>0?transportFee.multiply(serviceRate):freePCT15;
		    }else if(service.compareTo(transportFee.multiply(serviceRate))>0){
		    	serviceFee=service;
		    }else {
		    	serviceFee=transportFee.multiply(serviceRate);
		    }
		    
		}
		if(serviceRate!=null && service==null){
			serviceFee=transportFee.multiply(serviceRate);
		}
		if(service!=null && serviceRate ==null){
		    //百分15的运费
		    BigDecimal freePCT15 = transportFee.multiply(pCT15);
		    if(service.compareTo(freePCT15)>0){
		    	throw new PdaInterfaceException("装卸费不能大于运费的15%");	
		    }else{
		    	serviceFee=service;
		    }
		    
		}
		return  serviceFee.setScale(0, BigDecimal.ROUND_HALF_UP);
    	
    }
	/**
	 * 包装费
	 * @param packageCollectionFRAME
	 * @param packageCollectionBOX
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private WaybillChargeDtlEntity setPackageCharge(
			GuiResultBillCalculateDto packageCollectionFRAME,
			GuiResultBillCalculateDto packageCollectionBOX,
			WoodenRequirementsEntity woodEntity,
			WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList) {
		BigDecimal packageFee= BigDecimal.ZERO;
		boolean isok=false;
		//木架
		if(packageCollectionFRAME!=null){
			packageFee=packageFee.add(packageCollectionFRAME.getCaculateFee());
			isok=true;
		}
		//木箱
		if(packageCollectionBOX!=null){
			packageFee=packageFee.add(packageCollectionBOX.getCaculateFee());
			isok=true;
		}
		//木托
		BigDecimal mt= BigDecimal.ZERO;
		if(woodEntity!=null && woodEntity.getSalverGoodsAmount()!=null 
				 && 
		  woodEntity.getSalverGoodsAmount().compareTo(BigDecimal.ZERO)>0){
			mt=woodEntity.getSalverGoodsAmount();
			isok=true;
		}
		//手输包装费
		BigDecimal sspackageFee= BigDecimal.ZERO; 
		if(entity.getPackageFee()!=null){
			sspackageFee=entity.getPackageFee();
			isok=true;
		}
		
		if(isok){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);
			chargeEntity.setAmount(packageFee.add(mt).add(sspackageFee));
			chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
			return chargeEntity;
		}
		
		return null;
		
	}

	/**
	 * 设置折扣明细
	 * @param entity
	 * @param guiResultBillCalculateDtos
	 * @param disDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setWaybillDisDtlEntity(WaybillEntity entity,
			List<GuiResultBillCalculateDto> dtos,
			List<WaybillDisDtlEntity> disDtlEntityList) {
		BigDecimal promotionsFee = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(dtos)){
			for(GuiResultBillCalculateDto dto:dtos){
				List<GuiResultDiscountDto> discountPrograms =dto.getDiscountPrograms();
				if(CollectionUtils.isNotEmpty(discountPrograms)){
					for(GuiResultDiscountDto dt:discountPrograms){
						WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
						//优惠项目名称
		        		disDtlEntity.setPricingEntryName(dt.getPriceEntryName());
		        		//优惠项目CODE
		        		disDtlEntity.setPricingEntryCode(dt.getPriceEntryCode());
		        		//优惠类型名称
		        		disDtlEntity.setTypeName(dt.getDiscountTypeName());
		        		//优惠类型CODE
		    			disDtlEntity.setType(dt.getDiscountType());
		        		//优惠子类型名称
		        		disDtlEntity.setSubTypeName(dt.getSaleChannelName());
		        		//优惠子类型CODE
		    			disDtlEntity.setSubType(dt.getSaleChannelCode());
		    			//折扣费率
		        		disDtlEntity.setRate(dt.getDiscountRate());
		        		//折扣金额
		        		disDtlEntity.setAmount(dt.getReduceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
		        		//运单号
		        		disDtlEntity.setWaybillNo(entity.getWaybillNo());
		        		if(dt.getDiscountId()!=null){
		        			disDtlEntity.setDicountId(dt.getDiscountId());
		        		}else{
		        			disDtlEntity.setDicountId(UUIDUtils.getUUID());
		        		}
		        		if(dt.getChargeDetailId()!=null){
		        			disDtlEntity.setWaybillChargeDetailId(dt.getChargeDetailId());
		        		}else{
		        			disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
		        		}
		        		disDtlEntity.setCreateTime(new Date());
		        		disDtlEntity.setModifyTime(new Date());
		        		disDtlEntity.setActive(FossConstants.ACTIVE);
		        		disDtlEntity.setBillTime(new Date());
		        		disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		        		//营销活动编码
		        		disDtlEntity.setActiveCode(dt.getActiveCode());
		        		//营销活动名称
		        		disDtlEntity.setActiveName(dt.getActiveName());
		        		//营销活动开始时间
		        		disDtlEntity.setActiveStartTime(dt.getActiveStartTime());
		        		//营销活动结束时间
		        		disDtlEntity.setActiveEndTime(dt.getActiveEndTime());
		        		//营销活动折扣相应的CRM_ID
		        		disDtlEntity.setOptionsCrmId(dt.getOptionsCrmId());
		        		disDtlEntityList.add(disDtlEntity);
					}
				}
			}
			
		}
		//优惠总金额
		if(CollectionUtils.isNotEmpty(disDtlEntityList)){
			for(WaybillDisDtlEntity dis:disDtlEntityList){
				promotionsFee=promotionsFee.add(dis.getAmount());
			}
		}
		//优惠费用
		entity.setPromotionsFee(promotionsFee);
		
	}

	/**
	 * 费用明细添加送货费
	 * @param delivetList
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setDeliveryFeeCollection(
			List<DeliverChargeEntity> delivetList, WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList) {
		if(CollectionUtils.isNotEmpty(delivetList))
		{
			for(int i=0;i<delivetList.size();i++)
			{
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				DeliverChargeEntity deliverCharge = delivetList.get(i);
				//送货费编码
				chargeEntity.setId(UUIDUtils.getUUID());
				chargeEntity.setPricingEntryCode(deliverCharge.getCode());
				chargeEntity.setAmount(deliverCharge.getAmount());
				chargeEntity.setWaybillNo(entity.getWaybillNo());
				chargeEntity.setPricingCriDetailId(deliverCharge.getId());
				chargeEntity.setActive(deliverCharge.getActive());
				chargeEntity.setCurrencyCode(deliverCharge.getCurrencyCode());
				chargeDtlEntityList.add(chargeEntity);
			}
		}
		
	}

	/**
	 * 费用明细添加代收费用
	 * @param codCollection
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setCodCollection(GuiResultBillCalculateDto codCollection,
			WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList) {
		if(codCollection!=null){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setId(UUIDUtils.getUUID());
			chargeEntity.setPricingEntryCode(codCollection.getPriceEntryCode());
			chargeEntity.setAmount(codCollection.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(codCollection.getId());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
		}
		
	}

	/**
	 * 费用明细添加公布价费
	 * @param dto
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setTransportFee(GuiResultBillCalculateDto dto,
			WaybillEntity waybillEntity,
			WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeList) {
		//装卸费  （装卸费放在运费里）
		BigDecimal  serviceFee = getServiceFee(entity,dto);
		waybillEntity.setServiceFee(serviceFee);
		dto.setCaculateFee(dto.getCaculateFee().add(serviceFee));
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		if(FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())){
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
    		chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
		}else{
    		chargeEntity.setPricingEntryCode(dto.getPriceEntryCode());
    		chargeEntity.setPricingCriDetailId(dto.getId());
		}
		chargeEntity.setId(UUIDUtils.getUUID());
		chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
		chargeEntity.setWaybillNo(entity.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeList.add(chargeEntity);
	}

	/**
	 * 费用明细添加打木箱费
	 * @param packageCollectionFRAME
	 * @param entity
	 * @author 076234 PanGuoYang
	 */
	private void setBoxChargeCollection(
			GuiResultBillCalculateDto dto,
			WaybillPendingEntity entity, WoodenRequirementsEntity woodEntity,
			List<WaybillChargeDtlEntity> chargeList) {
		if(woodEntity!=null){
			if (woodEntity.getBoxGoodsNum() != null && woodEntity.getBoxGoodsNum() > 0) {
				if (dto != null) {
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(dto.getSubType());
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setPricingCriDetailId(dto.getId());
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					chargeList.add(chargeEntity);
				} else {
					throw new PdaInterfaceException("没有打木箱费用");			
				}
			}
		}
	}

	/**
	 * 费用明细添加打木架费
	 * @param packageCollectionFRAME
	 * @param entity
	 * @author 076234 PanGuoYang
	 */
	private void setStandChargeCollection(
			GuiResultBillCalculateDto dto,WaybillPendingEntity entity,
			WoodenRequirementsEntity woodEntity,List<WaybillChargeDtlEntity> chargeList ) {
		if(woodEntity!=null){
			if (woodEntity.getStandGoodsNum() != null && woodEntity.getStandGoodsNum() > 0) {
				if (dto != null) {
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(dto.getSubType());
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setPricingCriDetailId(dto.getId());
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					chargeList.add(chargeEntity);
				} else {
					throw new PdaInterfaceException("没有打木架费用");
				}
			}
		}
	}

	/**
	 * 获取报价费
	 * @param insuranceCollection
	 * @param entity
	 * @author foss-PanGuoYang
     * @date 2014-10-08
	 */
	private void setInsurance(GuiResultBillCalculateDto dto,
			WaybillPendingEntity entity,List<WaybillChargeDtlEntity> chargeList) {
		if(dto!=null){
			
			BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			if(BigDecimal.ZERO.compareTo(entity.getInsuranceAmount())<0){
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				chargeEntity.setId(UUIDUtils.getUUID());
				chargeEntity.setPricingEntryCode(dto.getPriceEntryCode());
				chargeEntity.setAmount(insuranceFee);
				chargeEntity.setWaybillNo(entity.getWaybillNo());
				chargeEntity.setPricingCriDetailId(dto.getId());
				chargeEntity.setActive(FossConstants.ACTIVE);
				chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				chargeList.add(chargeEntity);
			}else{
				dto.setCaculateFee(BigDecimal.ZERO);
			} 
			
	  }
	}

	/**
	 * 获取接货费
	 * @param dtoJH
	 * @param entity
	 * @author foss-PanGuoYang
     * @date 2014-10-01 
	 */
	private void setProductPriceDtoCollection(GuiResultBillCalculateDto dto,GuiResultBillCalculateDto dtoJH,
			WaybillPendingEntity entity,List<WaybillChargeDtlEntity> chargeList,String remark) {
		if(entity.getPickupToDoor() != null && FossConstants.ACTIVE.equals(entity.getPickupToDoor())&&
				FossConstants.INACTIVE.equals(entity.getFreePickupGoods())){//modify by 306486 免费接货判断
			if (FossConstants.ACTIVE.equals(dto.getCentralizePickup())) {
				entity.setPickupFee(BigDecimal.ZERO);//设置接货费
				
			}else{
				if(dtoJH==null){
					throw new PdaInterfaceException("接货费不能为空");
				}
				
				WaybillEntity wentity= getWaybillEntity(entity);
				// 是否为发货人在当前收货部门的当天8时到次日8时间的第一票
				boolean firstWaybill =waybillManagerService.isFirstDeliveryWaybill(wentity);
				//GUI端手输接货费
				BigDecimal pickupFee = wentity.getPackageFee()!=null?wentity.getPackageFee(): BigDecimal.ZERO;
				if(firstWaybill){
					
					entity.setPickupFee(dtoJH.getCaculateFee());// 设置接货费
			
					if(dtoJH.getMinFee() != null){
						if(null == dtoJH.getFee() && dtoJH.getCaculateFee().compareTo(dtoJH.getMinFee()) < 0){
							entity.setPickupFee(dtoJH.getMinFee());// 设置接货费
						}
					}
					//判断最高一票与折扣金额
					if(dtoJH.getMaxFee() != null){
						if(null == dtoJH.getFee() && dtoJH.getCaculateFee().compareTo(dtoJH.getMaxFee()) > 0){
							entity.setPickupFee(dtoJH.getMaxFee());// 设置接货费
						}
					}
					// 返回的接货费值不为空，则表示有客户编码且有设置客户接货费值
					if (null != dtoJH.getFee() && null == dtoJH.getCaculateFee()) {
						entity.setPickupFee(dtoJH.getFee());// 设置接货费
					}
			
				}else{
					if(pickupFee != null && dtoJH != null && pickupFee.compareTo(BigDecimal.ZERO)>0 && dtoJH.getMinFee()!=null){
						if(pickupFee.compareTo(dtoJH.getMinFee())>0){
							entity.setPickupFee(pickupFee);// 设置接货费
						}else{
							entity.setPickupFee(dtoJH.getMinFee());// 设置接货费
						}
						remark = "页面手动添加接货费:"+pickupFee;
					}
					if(pickupFee != null && dtoJH != null && pickupFee.compareTo(BigDecimal.ZERO)>0 && dtoJH.getMaxFee()!=null){
						if(pickupFee.compareTo(dtoJH.getMaxFee())>0){
							entity.setPickupFee(dtoJH.getMaxFee());// 设置接货费
						}
						remark = "";
						remark = "页面手动添加接货费:"+pickupFee;
					}
					if(pickupFee==null || pickupFee.compareTo(BigDecimal.ZERO)==0){
						entity.setPickupFee(BigDecimal.ZERO);//设置接货费
					}
				}
			}
		}else{
			entity.setPickupFee(BigDecimal.ZERO);//设置接货费
		}
		
		if(dtoJH!=null){
			BigDecimal jhf =entity.getPickupFee()!=null?  entity.getPickupFee():BigDecimal.ZERO;
			dtoJH.setCaculateFee(jhf);
		}
		
		
		//费用明细添加接货费
		if (entity.getPickupFee() != null && entity.getPickupFee().compareTo(BigDecimal.ZERO) != 0) {
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setId(UUIDUtils.getUUID());
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_JH);
			if(dtoJH!=null && StringUtil.isNotBlank(dtoJH.getId())){
				chargeEntity.setPricingCriDetailId(dtoJH.getId());
			}else{
			chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
			}
			chargeEntity.setAmount(entity.getPickupFee());
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeList.add(chargeEntity);
		}
	}

	/**
	 * 封装运单部分数据
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 下午7:21:05
	 */
	public  WaybillEntity getWaybillEntity(WaybillPendingEntity entity) {
		WaybillEntity wentity = new WaybillEntity();
		wentity.setWaybillNo(entity.getWaybillNo());// 运单号
		wentity.setOrderNo(entity.getOrderNo());// 订单号
		wentity.setDeliveryCustomerCode(entity.getDeliveryCustomerCode());// 发货客户编码
		wentity.setDeliveryCustomerName(entity.getDeliveryCustomerName());// 发货客户名称
		wentity.setDeliveryCustomerMobilephone(entity.getDeliveryCustomerMobilephone());// 发货客户手机
		wentity.setDeliveryCustomerPhone(entity.getDeliveryCustomerPhone());// 发货客户电话
		wentity.setBillTime(entity.getBillTime());//开单时间
		wentity.setReceiveOrgCode(entity.getReceiveOrgCode());//收货部门编码
		//entity.setReceiveOrgName(entity.getReceiveOrgName());//收货部门名称
		wentity.setActive(FossConstants.YES);
		return wentity;
	}
	/**
	 * 获取派送费集合
	 * @author 026113-foss-PanGuoYang
	 * @date 2014-10-08 下午7:43:29
	 */
	private List<DeliverChargeEntity> getDeliverChargeEntity(List<GuiResultBillCalculateDto> dtos ,WaybillPendingEntity entity){
		GuiResultBillCalculateDto guiResultBillCalculateDto;
		// 整车没有送货费
		if (entity.getIsWholeVehicle() != null && FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())) {
			//cleanDeliverCharge(bean);
			return null;
		}

		//提货方式编码
		String code = entity.getReceiveMethod();
		// 获取派送费集合
		List<DeliverChargeEntity> delivetList = null;
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {

			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			/**
			 * 如果是汽运送货费，那么需要判断是否超过最高派送费，如果超过，赋值为派送费最大值
			 */
			if (WaybillConstants.DELIVER_NOUP.equals(code)) {

				BigDecimal maxDeliveFee = null;//设置最大派送费
				// 最大派送费
				ConfigurationParamsEntity maxDeliverFeeForConfig = 
				pkpsysConfigService.queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.PKP_DELIVER_NOUP_MAX_FEE, FossConstants.ROOT_ORG_CODE);
				if (maxDeliverFeeForConfig != null
						&& StringUtils.isNotEmpty(maxDeliverFeeForConfig
								.getConfValue())) {
					maxDeliveFee = new BigDecimal(
							maxDeliverFeeForConfig.getConfValue());
				}
				
				if (guiResultBillCalculateDto != null && maxDeliveFee!=null)
				{
					BigDecimal caculateFee = guiResultBillCalculateDto
							.getCaculateFee();
					if (caculateFee.compareTo(maxDeliveFee) > 0) {
						guiResultBillCalculateDto.setCaculateFee(maxDeliveFee);

					}
				}
			}
			
			// 设置送货费 
			delivetList= setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			}
			

		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			delivetList=setDeliverFee(guiResultBillCalculateDto,false,entity,delivetList);
			//获取上楼费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SHSL, null);
			
			// 设置上楼费
			delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			}

		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_QT, PriceEntityConstants.PRICING_CODE_SHJC);
			
			// 设置进仓费
			delivetList=setDeliverFee(guiResultBillCalculateDto,true,entity,delivetList);
			// 获取超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				delivetList=setDeliverFee(guiResultBillCalculateDto,false,entity,delivetList);
			}
		}else if (WaybillConstants.LARGE_DELIVER_UP.equals(code)// 大件上楼
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(code)) {

			//获取送货费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			// 设置送货费
			delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			//获取大件上楼费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_DJSL, null);
			/**
			 * @需求：大件上楼优化
			 * @功能：当大件上楼计算费用为null时，说明没有查询到相应的大件上楼费用，需要维护相关资料
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-04-16
			 */

            //foss 343617 zhaoyiqing 20160921 精准包裹价格方案中包含送货费时提货方式选择大件上楼允许大件上楼计价条目为空
            GuiResultBillCalculateDto ecGoodsFRTResultDto=null;
            //foss 343617 zhaoyiqing 20160921 是精准包裹就遍历结果集取出运费
            if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_PCP,entity.getProductCode())){
                for(GuiResultBillCalculateDto tempDto : dtos){
                    if(tempDto!=null && StringUtil.equals(tempDto.getPriceEntryCode(),PriceEntityConstants.PRICING_CODE_FRT)){
                        ecGoodsFRTResultDto = tempDto;
                        break;
                    }
                }
            }

            if(guiResultBillCalculateDto==null){
                if(ecGoodsFRTResultDto==null) {
                    throw new WaybillValidateException("大件上楼资料未维护，请维护相关资料！");
                }else{
                    //foss 343617 zhaoyiqing 20160921 精准包裹价格方案中不包含送货费就抛异常
                    if(StringUtil.equals(ecGoodsFRTResultDto.getCentralizeDeliveryResult(),FossConstants.INACTIVE)){
                        throw new WaybillValidateException("大件上楼资料未维护，请维护相关资料！");
                    }
                }
			}
			// 设置大件上楼费
			delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			}
		}
		/*
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)) {
			bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);
		}

		//送货费集合
		List<DeliverChargeEntity> deliverList = bean.getDeliverList();
		if (deliverList != null) {
			ui.canvasContentPanel.getOtherCost().setChangeDetail(deliverList);
		}*/
		return delivetList;
	}
	/**
	 * 获取配置参数
	 * @param type
	 * @retur
	 * @author 076234
	 */
	private ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String type,String pickupOrgCode){
		 ConfigurationParamsEntity config =configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,type,pickupOrgCode);
		return config;
	}
	/**
	 * 
	 * 设置送货费、送货进仓费、送货上楼等全部送货费
	 * 
	 * @param flag = true 表示是送货费中的基础送货费
	 * @param isDeliverStorge = true
	 *            表示送货费中的送货进仓费
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-08 上午11:52:25
	 */
	private List<DeliverChargeEntity> setDeliverFee(GuiResultBillCalculateDto dto, Boolean isDeliverStorge,WaybillPendingEntity entity,List<DeliverChargeEntity> delivetList) {
		if (dto != null) {

			DeliverChargeEntity deliver = new DeliverChargeEntity();
			// 是否激活
			deliver.setActive(FossConstants.ACTIVE);

			BigDecimal deliveryGoodsFee = dto.getCaculateFee();
			if (deliveryGoodsFee == null) {
				deliveryGoodsFee = BigDecimal.ZERO;
			} else {
				deliveryGoodsFee = deliveryGoodsFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			// 金额
			deliver.setAmount(deliveryGoodsFee);
			// 币种
			deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 费用ID
			deliver.setId(dto.getId());
			// 运单号
			deliver.setWaybillNo(entity.getWaybillNo());
			//提货方式编码
			if (isDeliverStorge)// 送货进仓
			{
				// 费用编码
				deliver.setCode(dto.getSubType());
				//费用名称
				deliver.setName(dto.getSubTypeName());
			} else {
				// 费用编码
				deliver.setCode(dto.getPriceEntryCode());

				//费用名称
				deliver.setName(dto.getPriceEntryName());
			}
			// 送货费合计
			//BigDecimal chargeTotal = BigDecimal.ZERO;
			// 送货费合计 = 送货费+上楼费/进仓费/超远派送费
			//chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
			//entity.setDeliveryGoodsFee(chargeTotal);
			// 画布-送货费
			//entity.setDeliveryGoodsFeeCanvas(chargeTotal.toString());
			// 计算的送货费
			//entity.setCalculateDeliveryGoodsFee(chargeTotal);
			// 获取派送费集合
			if (CollectionUtils.isEmpty(delivetList)) {
				delivetList = new ArrayList<DeliverChargeEntity>();
			}
			// 将新的派送费添加到派送费集合
			delivetList.add(deliver);
			// 将派送费集合进行更新
			//bean.setDeliverList(delivetList);
			// 此标识用来标记是否送货费,如果查询出来是送货费，则将送货费的最大上限设置
//			if (flag) {
//				bean.setMaxDeliveryGoodsFee(dto.getMaxFee());
//			}
			// 费用折扣
			//Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
		}
		return delivetList;
	}
	/**
	 * 获取GuiResultBillCalculateDto
	 * @param dtos
	 * @param valueAddType
	 * @param subType
	 * @author 026113-foss-PanGuoYang
	 * @date 2014-10-08 下午7:43:29
	 */
	public GuiResultBillCalculateDto getGuiResultBillCalculateDto(List<GuiResultBillCalculateDto> dtos, String valueAddType, String subType) {

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {

				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode()) && subType.equals(guiResultBillCalculateDto.getSubType())) {
					return guiResultBillCalculateDto;
				}
			}

		}

		return null;
	}
	
	/**
	 * 计算价格
	 * @param entity
	 * @param woodEntity
	 */
	public List<GuiResultBillCalculateDto> queryGuiBillPrice(WaybillPendingEntity entity,WoodenRequirementsEntity woodEntity){
		
		//获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getQueryParamCollection(entity);
		productPriceDtoCollection.setWaybillNo(entity.getWaybillNo());
		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();

		if(woodEntity!=null){
			// 打木架/大木箱 计算信息收集
			List<GuiQueryBillCalculateSubDto> yokeChargeCollections = getYokeChargeCollection(woodEntity);
			if (yokeChargeCollections != null && !yokeChargeCollections.isEmpty()) {
				priceEntities.addAll(yokeChargeCollections);//
			}
		}
		
		
		//获取保价费
		GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceParam(entity);
		if (insuranceCollection != null) {
			priceEntities.add(insuranceCollection);//加入增值服务
		}

		//代收货款手续费
		GuiQueryBillCalculateSubDto codCollection = getCodCollection(entity);
		if (codCollection != null) {
			priceEntities.add(codCollection);//代收货款手续费
		}
		
		//送货费
		List<GuiQueryBillCalculateSubDto> deliveryFees = getDeliveryFeeCollection(entity);
		if (deliveryFees != null && !deliveryFees.isEmpty()) {
			priceEntities.addAll(deliveryFees);
		}

		//其他费用
		GuiQueryBillCalculateSubDto otherChargeDataCollection = getQueryOtherChargeParam();
		if (otherChargeDataCollection != null) {
			priceEntities.add(otherChargeDataCollection);//代收货款手续费
		}

		productPriceDtoCollection.setPriceEntities(priceEntities);
		
		// 是否经济自提件
		productPriceDtoCollection.setEconomySince(entity.getIsEconomyGoods());
		
		//最终配载部门(计算偏线中转费时用得到)
		productPriceDtoCollection.setLastOrgCode(entity.getLastLoadOrgCode());
		
		WaybillDisDtlPendingEntity  disDtlPend =waybillDisDtlPendingDao.queryActiveInfoByNoAndType(entity.getWaybillNo());
		if(disDtlPend!=null && disDtlPend.getActiveCode()!=null){
			//营销活动DTO
			//=productPriceDtoCollection.setActiveDto(bean.getActiveDto());
			
			//是否计算市场营销折扣
			productPriceDtoCollection.setCalActiveDiscount(false);
			
			//封装市场营销活动校验条件
			settterActiveParamInfo(productPriceDtoCollection, entity,disDtlPend.getActiveCode());
		}
		productPriceDtoCollection.setBillTime(entity.getBillTime());
		//封装内部发货条件
				/**
				 * 根据条件查询当前月份的优惠总额
				 */
				BigDecimal amount = null;
				if(StringUtil.isNotBlank(entity.getEmployeeNo())) {
					amount = waybillManagerService.queryDiscountFeeByEmployeeNo(entity.getEmployeeNo(),entity.getBillTime());
				}
				productPriceDtoCollection.setInternalDeliveryType(entity.getInternalDeliveryType());
				
				productPriceDtoCollection.setEmployeeNo(entity.getEmployeeNo());
				productPriceDtoCollection.setAmount(amount);
				/**
				 * 封装梯度折扣
				 */
				if(StringUtil.isNotBlank(entity.getDeliveryCustomerCode())) {
					amount = waybillManagerService.queryTotalFeeByDelevyCode(entity.getDeliveryCustomerCode(),entity.getBillTime());
					productPriceDtoCollection.setTotalAmount(amount);
				}
				productPriceDtoCollection.setReceiveOrgCode(entity.getReceiveOrgCode());
		//统一返回的计价值
		 return waybillManagerService.queryGuiBillPrice(productPriceDtoCollection);
		
	}
	
	
	/**
	 * 封装营销活动参数信息
	 * @创建时间 2014-10-28 下午8:05:29   
	 * @创建人： PanGuoYang
	 */
	public  void settterActiveParamInfo(GuiQueryBillCalculateDto productPriceDtoCollection,WaybillPendingEntity entity,String activeCode){
		if(activeCode!=null){
			productPriceDtoCollection.setActiveCode(activeCode);
		}		
		productPriceDtoCollection.setGoodsName(entity.getGoodsName());
		productPriceDtoCollection.setDeliveryCustomerCode(entity.getDeliveryCustomerCode());
		productPriceDtoCollection.setOrderChannel(entity.getOrderChannel());
		productPriceDtoCollection.setReceiveOrgCode(entity.getReceiveOrgCode());
		productPriceDtoCollection.setLoadOrgCode(entity.getLoadOrgCode());
		productPriceDtoCollection.setLastOutLoadOrgCode(entity.getLastOutLoadOrgCode());
		productPriceDtoCollection.setCustomerPickupOrgCode(entity.getCustomerPickupOrgCode());
		productPriceDtoCollection.setBillTime(entity.getBillTime());
		productPriceDtoCollection.setTransportFee(entity.getTransportFee());
		productPriceDtoCollection.setGoodsWeightTotal(entity.getGoodsWeightTotal());
		productPriceDtoCollection.setGoodsVolumeTotal(entity.getGoodsVolumeTotal());
	}
	
	/**
	 * 获取其他费用查询参数
	 * 
	 * @author 076234-foss-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getQueryOtherChargeParam() {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();

		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		return queryDto;
	}
	/**
	 * 送货费
	 * @param entity
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 * @return
	 */
	private List<GuiQueryBillCalculateSubDto> getDeliveryFeeCollection(
			WaybillPendingEntity entity) {
		List<GuiQueryBillCalculateSubDto> queryBillCacilateValueAddDto = new ArrayList<GuiQueryBillCalculateSubDto>();
		// 整车没有送货费
		if (entity.getIsWholeVehicle() != null 
				&& FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())) {
			return null;
		}
		//提货方式编码
		String code = entity.getReceiveMethod();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SHSL, null, null));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		}else if (WaybillConstants.LARGE_DELIVER_UP.equals(code)// 大件上楼费
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_DJSL, null, null));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		}
		return queryBillCacilateValueAddDto;
	}
	
	/** 
	 * 获取超远派送费查询参数 
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 下午04:54:59
	 * @param valueAddType
	 * @param cost
	 * @param subType
	 * @return
	 */
	private GuiQueryBillCalculateSubDto getVeryFarQueryParam(String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	/**
	 *获取代收货款费率
	 * @param entity
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 * @return
	 */
	private GuiQueryBillCalculateSubDto getCodCollection(
			WaybillPendingEntity entity) {
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = entity.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0 && entity.getRefundType() != null) {
			return getQueryParam(PriceEntityConstants.PRICING_CODE_HK, entity.getCodAmount(), entity.getRefundType());
		}else{
			return null;
		}

	}

	/**
	 * 获取查询参数
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getQueryParam(String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	/**
	 * 
	 * 获取查询参数
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getInsuranceParam(WaybillPendingEntity pendingEntity) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(pendingEntity.getInsuranceAmount());// 原始费用
		LimitedWarrantyItemsEntity entity= pkpinsurGoodsDao.isInsurGoods(pendingEntity.getGoodsName());
		if (entity != null 
				&& entity.getVirtualCode()==null 
			    && !FossConstants.ACTIVE.equals(pendingEntity.getIsWholeVehicle())) {
			queryDto.setSubType(entity.getVirtualCode());// 限保物品才会具备的虚拟code
		}
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setWoodenVolume(null);// 木架体积
		queryDto.setInsuranceAmount(pendingEntity.getInsuranceAmount());//保险声明价值
		queryDto.setMinFeeRate(pendingEntity.getMinFeeRate());//最小费率
		queryDto.setMaxFeeRate(pendingEntity.getMaxFeeRate());//最大费率
		return queryDto;
	}
	/**
	 * 获取取打木架费用
	 * @param guiQueryBillCalculateSubDtos
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 * @return
	 */
	private List<GuiQueryBillCalculateSubDto> getYokeChargeCollection(WoodenRequirementsEntity woodEntity){
			List<GuiQueryBillCalculateSubDto> guiQueryBillCalculateSubDtos = new ArrayList<GuiQueryBillCalculateSubDto>();
			// 获取木架费用
			GuiQueryBillCalculateSubDto standChargeCollection = getStandChargeCollection(woodEntity);
			if (standChargeCollection != null) {
				guiQueryBillCalculateSubDtos.add(standChargeCollection);
			}
			// 获取木箱费用
			GuiQueryBillCalculateSubDto boxChargeCollection = getBoxChargeCollection(woodEntity);
			if (boxChargeCollection != null) {
				guiQueryBillCalculateSubDtos.add(boxChargeCollection);

			}
			// 获取木托费用 zxy 20131118 ISSUE-4391
			GuiQueryBillCalculateSubDto salverChargeCollection = getSalverChargeCollection(woodEntity);
			if (salverChargeCollection != null) {
				guiQueryBillCalculateSubDtos.add(salverChargeCollection);
			}
			return guiQueryBillCalculateSubDtos;
	}
	
	/**
	 * 获取代打木托
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getSalverChargeCollection(
			WoodenRequirementsEntity woodEntity) {
		if (woodEntity.getSalverGoodsNum() != null && woodEntity.getSalverGoodsNum() > 0) {
			// 打木托
			return getQueryYokeParam(DictionaryValueConstants.PACKAGE_TYPE__SALVER, new BigDecimal(woodEntity.getSalverGoodsNum()));

		} else {
			return null;
		}
	}

	/**
	 * 获取代打木箱
	* @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getBoxChargeCollection(
			WoodenRequirementsEntity woodEntity) {
		if (woodEntity.getBoxGoodsNum() != null && woodEntity.getBoxGoodsNum() > 0) {
			// 打木箱
			return getQueryYokeParam(DictionaryValueConstants.PACKAGE_TYPE__BOX, woodEntity.getBoxGoodsVolume());
		} else {
			return null;
		}
	}
	

	/**
	 * 获取代打木架
	* @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	public  GuiQueryBillCalculateSubDto getStandChargeCollection(WoodenRequirementsEntity woodEntity) {

		if (woodEntity.getStandGoodsNum() != null && woodEntity.getStandGoodsNum() > 0) {
			return getQueryYokeParam(DictionaryValueConstants.PACKAGE_TYPE__FRAME, woodEntity.getStandGoodsVolume());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * 获取查询参数
	* @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private  GuiQueryBillCalculateSubDto getQueryYokeParam(String subType, BigDecimal volume) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setWoodenVolume(volume);//代打体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BZ);// 计价条目CODE
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		return queryDto;
	}

	/**
	 * 
	 * 获取产品价格查询参数
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	public  GuiQueryBillCalculateDto  getQueryParamCollection(WaybillPendingEntity entity) {
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		if(entity.getFreightMethod() != null )
			queryDto.setCombBillTypeCode(entity.getFreightMethod());
		queryDto.setOriginalOrgCode(entity.getReceiveOrgCode());// 出发部门CODE
		if(entity.getCustomerPickupOrgCode() != null){
			queryDto.setDestinationOrgCode(entity.getCustomerPickupOrgCode());// 到达部门CODE			
		}else{
			throw new PdaInterfaceException("提货网点不能空");
		}
		queryDto.setProductCode(entity.getProductCode());// 产品CODE
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())) {
			queryDto.setGoodsCode(entity.getGoodsTypeCode());// 货物类型CODE
		}
		
		queryDto.setReceiveDate(entity.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		// 是否接货
		if(entity.getPickupToDoor()!=null && FossConstants.ACTIVE.equals(entity.getPickupToDoor())){
			if(entity.getFreePickupGoods()!=null&&FossConstants.ACTIVE.equals(entity.getFreePickupGoods())){//add by 306486
				queryDto.setIsReceiveGoods(FossConstants.INACTIVE);// 是否接货
			}else{
				queryDto.setIsReceiveGoods(FossConstants.ACTIVE);// 是否接货
			}
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);// 是否接货
		}
		
		queryDto.setWeight(entity.getGoodsWeightTotal());// 重量
		/*EffectiveDto effectiveDto = waybillManagerService.searchPreSelfPickupTime(entity.getReceiveOrgCode(), entity.getLastLoadOrgCode(), entity.getProductCode(), entity.getPreDepartureTime(), new Date());
		if(effectiveDto!=null){
			queryDto.setLongOrShort(effectiveDto.getLongOrShort());// 长途 还是短途
		}*/
		queryDto.setLongOrShort(entity.getLongOrShort());// 长途 还是短途
		queryDto.setVolume(entity.getGoodsVolumeTotal());// 体积
		if (entity.getFlightNumberType() == null) {
			queryDto.setFlightShift(null);// 航班号
		} else {
			queryDto.setFlightShift(entity.getFlightNumberType());// 航班号
		}
		//保价费率
		queryDto.setFeeRate(entity.getInsuranceRate());
		queryDto.setChannelCode(entity.getOrderChannel());//设置CRM渠道
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(entity.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setKilom(entity.getKilometer());//设置公里数
		if(entity.getIsEconomyGoods()!=null && FossConstants.ACTIVE.equals(entity.getIsEconomyGoods())){
			queryDto.setEconomySince(FossConstants.ACTIVE);
		}else{
			queryDto.setEconomySince(FossConstants.INACTIVE);
		}
		//最终配载部门(计算偏线中转费时用得到)
		queryDto.setLastOrgCode(entity.getLastLoadOrgCode());
		List<GuiQueryBillCalculateSubDto> priceEntities =new ArrayList<GuiQueryBillCalculateSubDto>();
		GuiQueryBillCalculateSubDto guiQueryBillCalculateSubDto= new GuiQueryBillCalculateSubDto();
		guiQueryBillCalculateSubDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntities.add(guiQueryBillCalculateSubDto);
		queryDto.setPriceEntities(priceEntities);
		//为了在后期计算保价做标识符，状态为true时，不走保费折扣计算---206860
		queryDto.setCalDiscount(true);
		return queryDto;
	}
	
	/**
	 * 根据客户编码和银行帐号编码查询客户开户银行实体类
	 * @author 076234-foss-PanGuoYany
	 * @date 2014-10-02 下午7:24:18
	 */
	public  CusAccountEntity queryCusAccountByAccount(String custCode,String account){
		//客户开户银行实体类集合对象
		List<CusAccountEntity> accountList =null;
	        if(account!=null){
	        	accountList =queryBankAccountByCode(custCode);
	        }
				
		CusAccountEntity cusAccount = null;
		if(StringUtil.isNotEmpty(account)){
			//集合非空判断
			if(CollectionUtils.isNotEmpty(accountList)){
				//根据银行帐号进行遍历
				for (CusAccountEntity entity : accountList) {
					//查询银行帐号信息
					if(StringUtil.defaultIfNull(account).equals(entity.getAccountNo())){
						cusAccount = entity;
					}
				}
			}
		}
		
		return cusAccount;
	}
	
	/**
	 * 根据客户编码查询客户银行信息
	 * @author 076234-foss-PanGuoYany
	 * @date 2014-10-02 下午7:24:18
	 */
	public List<CusAccountEntity> queryBankAccountByCode(String customerCode) {
		//从CRM正式客户中查询开户行信息
		CustomerDto custDto = queryCustomerService.queryCustInfoByCode(customerCode);
		//判断是否为空
		if(custDto == null){
			//散客帐户集合
			List<NonfixedCusAccountEntity> nonfixAccountList = queryCustomerService.queryBankAccountByCode(customerCode);
			//判空操作
			if(CollectionUtils.isNotEmpty(nonfixAccountList)){
				List<CusAccountEntity> cusAccountList = new ArrayList<CusAccountEntity>();
				//转换帐户对象
				for (NonfixedCusAccountEntity nonfixedAccount : nonfixAccountList) {
					CusAccountEntity cusAccount = convertCusToNonfixedAccount(nonfixedAccount);
					cusAccountList.add(cusAccount);
				}
				return cusAccountList;
			}
		}else{
			return custDto.getBankAccountList();
		}
		return null;
	}
	
	/**
	 * 将散客银行帐户信息转换为正式客户的银行账户信息
	 * @author 076234-foss-PanGuoYany
	 * @date 2014-10-02 下午7:24:18
	 */
	private CusAccountEntity convertCusToNonfixedAccount(NonfixedCusAccountEntity nonfixed){
		if(null != nonfixed){
			CusAccountEntity account = new CusAccountEntity();
			 
		    // 其他支行名称.
		    account.setOtherBranchBankName(nonfixed.getOtherBranchBankName());
		    // 开户账号.
		    account.setAccountNo(nonfixed.getAccountNo());
		    // 开户人姓名.
		    account.setAccountName(nonfixed.getAccountName());
		    // 开户行城市编码.
		    account.setCityCode(nonfixed.getCityCode());
		    // 开户行省份编码.
		    account.setProvCode(nonfixed.getProvCode());
		    // 开户行编码.
		    account.setBankCode(nonfixed.getBankCode());
		    // 手机号码.
		    account.setMobilePhone(nonfixed.getMobilePhone());
		    // 账号与客户关系.
		    account.setCustomer(nonfixed.getCustomer());
		    // 是否默认账号.
		    account.setDefaultAccount(nonfixed.getDefaultAccount());
		    // 支行编码.
		    account.setBranchBankCode(nonfixed.getBranchBankCode());
		    // 备注.
		    account.setNotes(nonfixed.getNotes());
		    // 是否启用.
		    account.setActive(nonfixed.getActive());
		    // 账户性质 对公；对私两种.
		    account.setAccountNature(nonfixed.getAccountNature());
		    // 在CRM中fid.
		    //如果crm id不为null
		    if(nonfixed.getCrmId()!=null){
		    	account.setCrmId(BigDecimal.valueOf(Long.valueOf(nonfixed.getCrmId().toString()))); 
		    }
		    // 开户行所在城市名称.
		    account.setCityName(nonfixed.getCityName());
		    // 开户行省份名称.
		    account.setProvinceName(nonfixed.getProvinceName());
		    // 开户行名称.
		    account.setOpeningBankName(nonfixed.getOpeningBankName());
		    // 支行名称.
		    account.setBranchBankName(nonfixed.getBranchBankName());
		    // 所属客户ID.
		    if(nonfixed.getNoncustCrmId()!=null){
		    	account.setBelongCustom(BigDecimal.valueOf(Long.valueOf(nonfixed.getNoncustCrmId().toString())));
		    }
		    return account;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 自动生成运单前的基本验证
	 * @param focusWaybillDto
	 */
	private void validate(WaybillPendingEntity entity,
			List<WaybillCHDtlPendingEntity> chargePendList,
			WoodenRequirementsEntity woodenEntity,
			WaybillPictureEntity pictureEntity) {
	
			//运单信息验证
			validateWaybillentity(entity);
			//包装信息不能大于50字符
			validateGoodPackage(entity.getGoodsPackage());
			//货物名称校验
			waybillGoogNameCheck(entity.getGoodsName());
			//发票标记
			setInvoiceType(entity);
		    //重量体积件数验证
			validateWeightVolume(entity,woodenEntity);
		    //产品验证
			validateProduct(entity);
		    //精准大票验证
			validateIsBigGoods(entity,chargePendList);
			//校验提货网点单票以及单件重量与体积上限
			validateVW(entity);
			/**
			 * @需求：大件上楼优化需求
			 * @功能：验证是否满足大件上楼和送货上楼的条件
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-05-05下午16:55
			 */
			validateShslOrDjsl(entity,woodenEntity);
			//验证额度
			validateEmployeeAmount(entity);
	}

	
	/**
	 * 通过单号获取运单信息（图片开单项目使用）
	 * @param waybillNo
	 * @author 076234 PanGuoYang
	 * @return
	 */
	@Override
	public String queryWaybillPictureByWaybillNo(String jsonMsg){
		String waybillNo = "";
		String driverCode = "";
		try {
			JSONObject jsonObject = new JSONObject(jsonMsg);
			//获取运单号
			if (jsonObject.get("wblcode") != null) {
				waybillNo = (String)jsonObject.get("wblcode");
			}
			//获取司机工号
			if (jsonObject.get("driverCode") != null) {
				driverCode=(String)jsonObject.get("driverCode");
			}
			
		} catch (JSONException e) {
			throw new PdaInterfaceException("json格式化异常");
		}
		
		//手机端反馈已接 删除推送信息
		//pushMessageStatus(waybillNo);
		
		if (StringUtil.isBlank(waybillNo)) {
			throw new BusinessException("WAYBILL_NO_IS_NULL", "运单号不能为空");
		}
		WaybillDetailEntity waybillDetailEntity = new WaybillDetailEntity();
		waybillDetailEntity.setWaybillCode(waybillNo);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		waybillDetailEntity.setCreateTime(format.format(new Date()));
		WaybillPictureEntity waybillPictureEntity = waybillPictureDao.queryWaybillPictureByWaybillNo(waybillNo);
		if (waybillPictureEntity == null) {
			throw new BusinessException("WAYBILL_NO_IS_NOT_EXISTS", "运单号【" +waybillNo+ "】不存在");
		}
		//如果传过来的工号与查出来的工号不一致，则不给其返回标签数据
		if (!driverCode.equals(waybillPictureEntity.getDriverCode())) {
			throw new BusinessException("DRIVER_ERROR", "该运单已开单");
		}
		
		if (WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(waybillPictureEntity.getPendgingType())
				|| WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(waybillPictureEntity.getPendgingType())
				|| WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(waybillPictureEntity.getPendgingType())) {
			throw new BusinessException("WAYBILL_STATUS_ERROR", "运单图片【" + covertCodeToName(waybillPictureEntity.getPendgingType()) + "】");
		} else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(waybillPictureEntity.getPendgingType())) {
			throw new BusinessException("WAYBILL_STATUS_ERROR", "运单图片【已退回】，退回原因为:" + waybillPictureEntity.getRemark());
		}else if (WaybillConstants.WAYBILL_PICTURE_TYPE_DISCONTINUE.equals(waybillPictureEntity.getPendgingType())) {
			throw new BusinessException("WAYBILL_STATUS_ERROR", "运单图片【已中止】，中止原因为:" + waybillPictureEntity.getRemark());
		}else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(waybillPictureEntity.getPendgingType())
				|| WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(waybillPictureEntity.getPendgingType())) {
			//查询pending表
			WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryPendingByNo(waybillNo);
			if (waybillPendingEntity == null) {
				throw new BusinessException("WAYBILL_NO_IS_NOT_EXISTS", "运单号【" +waybillNo+ "】不存在");
			}
			fullWaybillInfo(waybillPendingEntity, waybillDetailEntity, driverCode);
			
		} else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(waybillPictureEntity.getPendgingType())) {
			WaybillDto waybillDto = waybillManagerService.queryWaybillByNo(waybillNo);
			if (waybillDto == null) {
				throw new BusinessException("WAYBILL_NO_IS_NOT_EXISTS", "运单号【" +waybillNo+ "】不存在");
			}
			fullWaybillInfo(waybillDto, waybillDetailEntity, driverCode);
			
		} else {
			throw new BusinessException("WAYBILL_PICTURE_STATUS_EXCEPTION", "运单号【" +waybillNo+ "】对应的运单图片类型异常");
		}
		
		JSONObject json = new JSONObject(waybillDetailEntity);
		return json.toString();
	}
	
	/**
	 * 填充数据
	 * @param bean
	 */
	private void fullWaybillInfo (Object obj, WaybillDetailEntity waybillDetailEntity, String driverCode) {
		WaybillDto waybillDto = null;
		WaybillPendingEntity waybillPendingEntity = null;
		// 对传入的数据进行转换
		if (obj instanceof WaybillPendingEntity) {
			waybillPendingEntity = (WaybillPendingEntity)obj;
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(waybillPendingEntity.getReceiveOrgCode());
			//设置收货部门
			if (saleDepartmentEntity != null) {
				waybillDetailEntity.setDepartDeptName(saleDepartmentEntity.getName());
			}
			//开单人 和手机
			String createUserCode = waybillPendingEntity.getCreateUserCode();
			if(StringUtils.isNotEmpty(createUserCode)){
				addBillUserInfo(waybillDetailEntity, createUserCode);
			}
			waybillDetailEntity.setDestinationDeptName(waybillPendingEntity.getTargetOrgCode());
			waybillDetailEntity.setShipperName(waybillPendingEntity.getDeliveryCustomerName());
			waybillDetailEntity.setShipperPhone(waybillPendingEntity.getDeliveryCustomerPhone());
			waybillDetailEntity.setShipperAddress(waybillPendingEntity.getDeliveryCustomerAddress());
			waybillDetailEntity.setConsigneeName(waybillPendingEntity.getReceiveCustomerName());
			waybillDetailEntity.setConsigneePhone(waybillPendingEntity.getReceiveCustomerPhone());
			waybillDetailEntity.setConsigneeAddress(waybillPendingEntity.getReceiveCustomerAddress());
			//add by 306486  增加返单类别
			waybillDetailEntity.setReturnBillType(waybillPendingEntity.getReturnBillType());
			//设置增值服务费
			List<AppreciationEntity> appreciationCost = new ArrayList<AppreciationEntity>();
			List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = waybillChargeDtlDao.queryChargeDtlEntityByNo(waybillDetailEntity.getWaybillCode());
			for (WaybillChargeDtlEntity dtlEntity : waybillChargeDtlEntitys) {
				//排除费用code为空的、木箱、木架、木托
				if (StringUtil.isBlank(dtlEntity.getPricingEntryCode()) 
						|| "BOX".equals(dtlEntity.getPricingEntryCode()) 
						|| "FRAME".equals(dtlEntity.getPricingEntryCode()) 
						|| "SALVER".equals(dtlEntity.getPricingEntryCode())) {
					continue;
				}
				AppreciationEntity appEntity = new AppreciationEntity();
				appEntity.setAppreciationName(priceEntryService.queryPriceEntryNameByCode(dtlEntity.getPricingEntryCode()));
				appEntity.setAppreciationCost(dtlEntity.getAmount());
				appreciationCost.add(appEntity);
			}
			waybillDetailEntity.setAppreciationCost(appreciationCost);
			/*提货网点-20161212-FOSS-348757增  需求编号：DN201612050014*/
			SaleDepartmentEntity customerPickupOrg = saleDepartmentService.querySaleDepartmentNoAttach(waybillPendingEntity.getCustomerPickupOrgCode());
			waybillDetailEntity.setCustomerPickupOrgName(null==customerPickupOrg?"":customerPickupOrg.getName());
			fullLabelInfo(waybillDetailEntity,driverCode);
		} else {
			
			waybillDto = (WaybillDto)obj;
			//开单人 和手机
			WaybillEntity wb = waybillDto.getWaybillEntity();
			String createUserCode = wb.getCreateUserCode();
			if(StringUtils.isNotEmpty(createUserCode)){
				addBillUserInfo(waybillDetailEntity, createUserCode);
			}
			waybillDetailEntity.setDepartDeptName(waybillDto.getWaybillEntity().getReceiveOrgName());
			waybillDetailEntity.setDestinationDeptName(waybillDto.getWaybillEntity().getTargetOrgCode());
			waybillDetailEntity.setShipperName(waybillDto.getWaybillEntity().getDeliveryCustomerName());
			waybillDetailEntity.setShipperPhone(waybillDto.getWaybillEntity().getDeliveryCustomerPhone());
			waybillDetailEntity.setShipperAddress(waybillDto.getWaybillEntity().getDeliveryCustomerAddress());
			waybillDetailEntity.setConsigneeName(waybillDto.getWaybillEntity().getReceiveCustomerName());
			waybillDetailEntity.setConsigneePhone(waybillDto.getWaybillEntity().getReceiveCustomerPhone());
			waybillDetailEntity.setConsigneeAddress(waybillDto.getWaybillEntity().getReceiveCustomerAddress());
			//add by 306486  增加返单类别
			waybillDetailEntity.setReturnBillType(waybillDto.getWaybillEntity().getReturnBillType());
			//设置增值服务费
			List<AppreciationEntity> appreciationCost = new ArrayList<AppreciationEntity>();
			List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = waybillChargeDtlDao.queryChargeDtlEntityByNo(waybillDetailEntity.getWaybillCode());
			for (WaybillChargeDtlEntity dtlEntity : waybillChargeDtlEntitys) {
				//排除费用code为空的、木箱、木架、木托
				if (StringUtil.isBlank(dtlEntity.getPricingEntryCode()) 
						|| "BOX".equals(dtlEntity.getPricingEntryCode()) 
						|| "FRAME".equals(dtlEntity.getPricingEntryCode()) 
						|| "SALVER".equals(dtlEntity.getPricingEntryCode())) {
					continue;
				}
				AppreciationEntity appEntity = new AppreciationEntity();
				appEntity.setAppreciationName(priceEntryService.queryPriceEntryNameByCode(dtlEntity.getPricingEntryCode()));
				appEntity.setAppreciationCost(dtlEntity.getAmount());
				appreciationCost.add(appEntity);
			}
			waybillDetailEntity.setAppreciationCost(appreciationCost);
			/*提货网点-20161212-FOSS-348757增  需求编号：DN201612050014*/
			waybillDetailEntity.setCustomerPickupOrgName(waybillDto.getWaybillEntity().getCustomerPickupOrgName());
			fullLabelInfo(waybillDetailEntity,driverCode);
			
		}
	}
	/**
	 * 
	* @Description: 添加开单人姓名和手机号
	* @author hbhk 
	* @date 2015-6-23 下午2:39:55 
	  @param waybillDetailEntity
	  @param empCode
	 */
	private void addBillUserInfo(WaybillDetailEntity waybillDetailEntity, String empCode){
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(empCode);
		if(employee==null){
			throw new BusinessException("没有获取到开单员信息:"+empCode);
		}
		waybillDetailEntity.setBillUserName(employee.getEmpName());
		waybillDetailEntity.setBiilUserPhone(employee.getMobilePhone());
	}
	/**
	 * 设置标签打印数据
	 * @param waybillNo
	 * @param waybillDetailEntity
	 */
	private void fullLabelInfo (WaybillDetailEntity waybillDetailEntity, String driverCode) {
		
		// 设置登录用户
		EmployeeEntity employEntity = new EmployeeEntity();
		UserEntity user= new UserEntity();
		if (StringUtil.isBlank(driverCode)) {
			throw new PdaInterfaceException("司机编号为空！");
		}
		employEntity.setEmpCode(driverCode);
		user.setEmpCode(driverCode);
		UserContext.setCurrentUser(user);
		//FossUserContext.getCurrentUser().setEmployee(employEntity);
		
		//设置标签打印信息
		BarcodePrintLabelDto labelDto = labelPrintInfoService.getLabelPrintInfos(waybillDetailEntity.getWaybillCode(), null, "");
		LabelPrintEntity labelDetail = new LabelPrintEntity();
		labelDetail.setWblCode(labelDto.getWaybillNumber());
		//设置送标记
		if (WaybillConstants.YES.equals(labelDto.getDeliverToDoor()) || SEND.equals(labelDto.getDeliverToDoor())) {
			labelDetail.setSend("【送】");
		}
		//设置总件数
		labelDetail.setPieces(labelDto.getTotalPieces());
		//设置运输性质
		labelDetail.setTransType(labelDto.getTranstype());
		//设置包装类型
		labelDetail.setWrapType(labelDto.getPacking());
		//设置目的站名称
		labelDetail.setDestinationName(labelDto.getDestination());
		//设置目的站编码
		labelDetail.setDestStationNumber(labelDto.getDestinationCode());
		//设置创建人
		labelDetail.setUserCode(labelDto.getCreateUser());
		//关于PDA打印航班类型的字段添加  220125
		String preassembly=" ";
		if(StringUtil.isNotBlank(labelDto.getPreassembly()))
		{
			//对应早班类型
		if ("MORNING_FLIGHT".equals(labelDto.getPreassembly())) {
			preassembly="(一)";
		   } else {
			  //其余的班次
			preassembly="(二)";
		          }
		 }
		//设置到达外场名称
		if (StringUtil.isNotBlank(labelDto.getLastTransCenterCity()) && labelDto.getLastTransCenterCity().length() > NumberConstants.NUMBER_7) {
			labelDetail.setDestTransCenterName((labelDto.getLastTransCenterCity()).substring(0, NumberConstants.NUMBER_7) + "-" + labelDto.getCountyRegion()+preassembly);
		} else {
			labelDetail.setDestTransCenterName(labelDto.getLastTransCenterCity() + "-" + labelDto.getCountyRegion()+preassembly);
		}
		//设置始发城市名称
		labelDetail.setDepartmentCityName(labelDto.getLeavecity());
		//设置货物类型
		labelDetail.setGoodsType(labelDto.getGoodstype());
		
		//设置库位信息
		List<GoodsAreaEntity>  goodsAreas = new ArrayList<GoodsAreaEntity>();
		GoodsAreaEntity goodsAreaEntity = new GoodsAreaEntity();
		if (StringUtil.isNotBlank(labelDto.getAddr1())) {
			goodsAreaEntity = new GoodsAreaEntity();
			goodsAreaEntity.setTransferCode(labelDto.getAddr1());
			goodsAreaEntity.setGoodsAreaCode(labelDto.getLocation1());
			goodsAreas.add(goodsAreaEntity);
		}
		if (StringUtil.isNotBlank(labelDto.getAddr2())) {
			goodsAreaEntity = new GoodsAreaEntity();
			goodsAreaEntity.setTransferCode(labelDto.getAddr2());
			goodsAreaEntity.setGoodsAreaCode(labelDto.getLocation2());
			goodsAreas.add(goodsAreaEntity);
		}
		if (StringUtil.isNotBlank(labelDto.getAddr3())) {
			goodsAreaEntity = new GoodsAreaEntity();
			goodsAreaEntity.setTransferCode(labelDto.getAddr3());
			goodsAreaEntity.setGoodsAreaCode(labelDto.getLocation3());
			goodsAreas.add(goodsAreaEntity);
		}
		if (StringUtil.isNotBlank(labelDto.getAddr4())) {
			goodsAreaEntity = new GoodsAreaEntity();
			goodsAreaEntity.setTransferCode(labelDto.getAddr4());
			goodsAreaEntity.setGoodsAreaCode(labelDto.getLocation4());
			goodsAreas.add(goodsAreaEntity);
		}
		
		labelDetail.setGoodsAreas(goodsAreas);
		/*zhangchengfu 20150516 FOSS展会货开单提示优化需求 begin*/
		if (StringUtil.isNotBlank(labelDto.getIsExhibitCargo())) {
			labelDetail.setIsExhibitCargo(labelDto.getIsExhibitCargo());
		}
		if (StringUtil.isNotBlank(labelDto.getReceiveBigCustomer())) {
			labelDetail.setReceiveBigCustomer(labelDto.getReceiveBigCustomer());
		}
		if (StringUtil.isNotBlank(labelDto.getDeliveryBigCustomer())) {
			labelDetail.setDeliveryBigCustomer(labelDto.getDeliveryBigCustomer());
		}
		/*zhangchengfu 20150516 FOSS展会货开单提示优化需求 end*/
		waybillDetailEntity.setLabelDetail(labelDetail);
	}
	
	
	
	/**
	 * 
	 * 校验提货网点单票以及单件重量与体积上限
	 * @author 076234
	 */
	public  void validateVW(WaybillPendingEntity entity) {
		/**
		 * BUG-41949:运单储运事项过长，导致综合查询报错，建议在开单时进行限制长度。。。见图。。258000250
		 */
		//对内备注
		int innerNotes = StringUtil.defaultIfNull(entity.getInnerNotes()).length();
		//储运事项 
		int transNotes = StringUtil.defaultIfNull(entity.getTransportationRemark()).length();
		
		if(innerNotes > NUMBER_1337){
			///对内备注录入错误：最大字符不能超过1300个！
			throw new WaybillValidateException("对内备注录入错误：最大字符不能超过1300个");
		}
		
		if(transNotes > NUMBER_1337){
			//储运事项 录入错误：最大字符不能超过1300个！
			throw new WaybillValidateException("储运事项 录入错误：最大字符不能超过1300个！");
		}
		BigDecimal goodsWeight = entity.getGoodsWeightTotal();// 总重量
		BigDecimal goodsVolume = entity.getGoodsVolumeTotal();// 总体积
		BigDecimal goodsQty = new BigDecimal(entity.getGoodsQtyTotal());// 总件数

		BigDecimal pieceWeight = goodsWeight.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件重量
		BigDecimal pieceVolume = goodsVolume.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件体积
		
		String customerPickupOrgCode= entity.getCustomerPickupOrgCode();
		String productCode = entity.getProductCode();
		 
		SaleDepartmentEntity saleDepartment = getCustomerPickupOrg(customerPickupOrgCode,productCode,new Date());
		/**
		 * 整车不校验重量和体积
		 */
		if (saleDepartment != null && !FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())) {
			if (saleDepartment.getSinglePieceLimitkg() != null) {
				// 单件重量上限
				BigDecimal singlePieceLimitkg = BigDecimal.valueOf(saleDepartment.getSinglePieceLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceWeight.compareTo(singlePieceLimitkg) > 0) {
					throw new WaybillValidateException("当前运单单件重量超出该提货网点单件重量上限，请选择其他网点(网点单件重量上限为:"+singlePieceLimitkg+")");
				}
			}

			if (saleDepartment.getSinglePieceLimitvol() != null) {
				// 单件体积上限
				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(saleDepartment.getSinglePieceLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceVolume.compareTo(singlePieceLimitvol) > 0) {
					throw new WaybillValidateException("当前运单单件体积超出该提货网点单件体积上限，请选择其他网点");
				}
			}

			if (saleDepartment.getSingleBillLimitkg() != null) {
				// 单票重量上限
				BigDecimal singleBillLimitkg = BigDecimal.valueOf(saleDepartment.getSingleBillLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsWeight.compareTo(singleBillLimitkg) > 0) {
					throw new WaybillValidateException("当前运单总重量超出该提货网点单票重量上限，请选择其他网点");
				}
			}

			if (saleDepartment.getSingleBillLimitvol() != null) {
				// 单票体积上限
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(saleDepartment.getSingleBillLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsVolume.compareTo(singleBillLimitvol) > 0) {
					throw new WaybillValidateException("当前运单总体积超出该提货网点单票体积上限，请选择其他网点");
				}
			}
		}
	}
	
	/**
	 * 获取提货网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-24 下午3:34:24
	 */
	public  SaleDepartmentEntity getCustomerPickupOrg(String customerPickupOrgCode,String productCode,Date billTime){
		if(customerPickupOrgCode!=null&&productCode!=null){
    		 		
    		//根据产品来判断是查询自有网点还是代理网点
    		if(!isAgentDept(productCode)){
    			return saleDepartmentService.querySaleDepartmentByCode(customerPickupOrgCode, billTime);
    		}else{
    			//return vehicleAgencyDeptService.queryOuterBranchByCode(customerPickupOrgCode, billTime);;
    		}
		}else{
			return null;
		}
		return null;
	}
	
	/**
	 * 根据产品来判断是查询自有网点还是代理网点
	 * @author 076234 PanGuoYang
	 */
	public  boolean isAgentDept(String productCode) {
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
			return false;
		} else {
			return true;
		}
	}
	

	/**
	 * 验证运单信息
	 * @param entity
	 * @author 076234
	 */
	public  void validateWaybillentity(WaybillPendingEntity entity) {
		if(null==entity){
			throw new WaybillValidateException("货物基本信息不能为空");
		}
		BigDecimal volume =entity.getGoodsVolumeTotal();
		BigDecimal weigth =entity.getGoodsWeightTotal();
		if(volume==null||volume.compareTo(BigDecimal.ZERO)==0){
			throw new WaybillValidateException("货物体积不能为空或0");
		}
		if(weigth==null||weigth.compareTo(BigDecimal.ZERO)==0){
			throw new WaybillValidateException("货物重量不能为空或0");
		}
		String receiveMethod = entity.getReceiveMethod();
		if(WaybillConstants.DELIVER_FREE_AIR.equals(receiveMethod)
			||
		  WaybillConstants.DELIVER_NOUP_AIR.equals(receiveMethod)
		    ||
		  WaybillConstants.DELIVER_UP_AIR.equals(receiveMethod)
		    ||
		  WaybillConstants.DELIVER_INGA_AIR.equals(receiveMethod)
		  ){
			throw new WaybillValidateException("该单为空运单据且送货");  
		} 
		
		boolean isHK = isHK(entity.getCustomerPickupOrgCode());
		if(isHK){
			
			if(!WaybillConstants.SELF_PICKUP.equals(receiveMethod)){
				throw new WaybillValidateException("目的是香港的，提货方式不为送货");  
			}
		}
	}
	
	/**
     * <p>
     * 包装信息不能大于50字符
     * </p>
     * @author 076234
     * @param goodsPackage
     */
	public  void validateGoodPackage(String goodsPackage) {
		try {
			if(StringUtils.isEmpty(goodsPackage)){
				return;
			}
			String a = new String(goodsPackage.getBytes("GBK"),"ISO-8859-1");
			if(a.length()>NumberConstants.NUMBER_50){
				throw new WaybillValidateException("包装信息不能大于50字符(注意:中文占2个字符)");
			}
		} catch (UnsupportedEncodingException e) {
			throw new WaybillValidateException("包装信息不能大于50字符(注意:中文占2个字符)");
		}
		
	}
	
	/**
     * 货物名称不能为空
     * @author 076234
     * @param goodsName
     */
	public  void waybillGoogNameCheck(String goodsName) {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(goodsName)){
			throw new WaybillValidateException("货物名称不能空");
		}
	}
	
	/**
	 * 
	 * 验证重量、体积、件数不能为默认值0
	 * @author 076234
	 */
	public  void validateWeightVolume(WaybillPendingEntity entity,
			                         WoodenRequirementsEntity woodenEntity) {
		if (entity.getGoodsQtyTotal().intValue() == 0 || entity.getGoodsQtyTotal().intValue()<0 ) {
			throw new WaybillValidateException("件数不能小于等于0");
		}

		if (entity.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
			throw new WaybillValidateException("货物重量不能等于0");
		}

		if (entity.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
			throw new WaybillValidateException("货物总体积不能等于0");
		}
		
		if(woodenEntity!=null){
		   int 	boxGoodsNum = woodenEntity.getBoxGoodsNum()!=null?woodenEntity.getBoxGoodsNum():0;
		   int  standGoodsNum =woodenEntity.getStandGoodsNum()!=null?woodenEntity.getStandGoodsNum():0;
		   if(boxGoodsNum>0 ||standGoodsNum>0 ){
			   if(!FossConstants.YES.equals(entity.getDoPacking())){
				   throw new WaybillValidateException("当前线路无法代打木架，如果需要打木架请重新选择线路");
			   }
		   }
			//zxy 20131225 MANA-381 start 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
			int intBoxPieces = woodenEntity.getBoxGoodsNum() == null ? 0:woodenEntity.getBoxGoodsNum();
			int intYokePieces = woodenEntity.getStandGoodsNum() == null ? 0:woodenEntity.getStandGoodsNum();
			if((intBoxPieces + intYokePieces) == entity.getGoodsQtyTotal()){
				// 打木架体积
				BigDecimal decYokeGoodsVolume = woodenEntity.getStandGoodsVolume();
				// 打木箱体积
				BigDecimal decBoxGoodsVolume = woodenEntity.getBoxGoodsVolume();
				BigDecimal decMoodenGoodsValumn = decYokeGoodsVolume.add(decBoxGoodsVolume);
				if(decMoodenGoodsValumn.add(new BigDecimal(POINT_001)).compareTo(entity.getGoodsVolumeTotal()) < 0 
						|| decMoodenGoodsValumn.subtract(new BigDecimal(POINT_001)).compareTo(entity.getGoodsVolumeTotal()) > 0){
					throw new WaybillValidateException("单票全打包装时,打木架体积+打木箱体积必须等于开单总体积");
				}
			}	
		}
		
	}
	
	/**
	 * 发票标记验证
	 * @param bean
	 * @param date
	 * @author 076234 PanGuoYany
	 */
	public  void setInvoiceType(WaybillPendingEntity entity){
		
		if(entity.getInvoice()==null){
			throw new WaybillValidateException("发票标记不能为空");
		}
		// 判断是否内部带货自提
		if(WaybillConstants.INNER_PICKUP.equals(entity.getReceiveMethod())){
			//发票标记
			entity.setInvoice(WaybillConstants.INVOICE_02);
		}
	}
	
	
	
	/**
	 * 产品验证
	 * @param waybillEntity
	 * @author 076234
	 */
	public  void validateProduct(WaybillPendingEntity waybillEntity) {
		String productCode = waybillEntity.getProductCode();
		if(productCode==null){
			throw new WaybillValidateException("运输性质不能为空" );
		}
	}
	
	

	/**
	 *是否符合精准大票校验
	 * 
	 */
	public  void validateIsBigGoods(WaybillPendingEntity entity,List<WaybillCHDtlPendingEntity> chargePendList) {
		String productCode=entity.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(productCode)
			||
			ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(productCode)
			||
			ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)
			||
			ProductEntityConstants. PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)){
			
			Boolean falg = isHeavyWeight(entity);
			if (falg) {
				throw new WaybillValidateException("不符合开精准大票条件！");
			}else{
				if(isOverweightFee(chargePendList)){
					throw new WaybillValidateException("包超重货操作服务费不能开精准大票");
				}else{
					BigDecimal goodsWeightTotal = entity.getGoodsWeightTotal();
					BigDecimal goodsVolumeTotal = entity.getGoodsVolumeTotal();
					if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0 && goodsVolumeTotal.compareTo(new BigDecimal(POINT_25)) <= 0){
						throw new WaybillValidateException("不符合开精准大票条件！");
					}
				}
			}
		}
		
	}
	
	
	/**
	 * 是否超重货
	 * @author 076234
	 */
	public  boolean isHeavyWeight(WaybillPendingEntity entity){
		boolean bHeavyWeight = false;	//超重货标志
		String  productCode =entity.getProductCode();
		if(productCode!=null && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)){
			if(entity.getGoodsWeightTotal()==null
				||entity.getGoodsQtyTotal()==null){
				return bHeavyWeight;
			}
			BigDecimal goodsWeightTotal=entity.getGoodsWeightTotal();
			int goodsQtyTotal=entity.getGoodsQtyTotal();
			if(goodsQtyTotal == 0){
				return bHeavyWeight;
			}
			BigDecimal unitWeight=goodsWeightTotal.divide(new BigDecimal(goodsQtyTotal),1,BigDecimal.ROUND_HALF_UP);
			if(unitWeight.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0){
				bHeavyWeight = true;
			}
		}
		return bHeavyWeight;
	}
	
	/*
	 * 是否包含超重货操作服务费
	 */
	public  boolean isOverweightFee(List<WaybillCHDtlPendingEntity> chargePendList){
		Boolean isOverweightFee = false;
		if(CollectionUtils.isNotEmpty(chargePendList)){
			for(WaybillCHDtlPendingEntity vo : chargePendList){
				if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getPricingEntryCode()) ||
						PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(vo.getPricingEntryCode())){
					isOverweightFee = true;
				}
			}
		}
		return isOverweightFee;
	}
	/**
	 * 内部发货额度校验
	 * dp-foss-dongjialing
	 * 225131
	 */
	public  void validateEmployeeAmount(WaybillPendingEntity waybillEntity) {
		if((WaybillConstants.DELIVERY_PAY.equals(waybillEntity.getInternalDeliveryType()) ||
				WaybillConstants.RECIVE_PAY.equals(waybillEntity.getInternalDeliveryType()))
				&&StringUtil.isNotBlank(waybillEntity.getEmployeeNo())) {
			InempDiscountPlanEntity entity = new InempDiscountPlanEntity();
			entity.setBillTime(waybillEntity.getBillTime());
			entity.setActive(FossConstants.ACTIVE);
			List<InempDiscountPlanEntity> list = inempDiscountPlanService.queryInempDiscountPanByCondition(entity);
			if(CollectionUtils.isNotEmpty(list)) {
				InempDiscountPlanEntity inempDiscountPlanEntity = list.get(0);
				if (inempDiscountPlanEntity.getHighstPreferentialLimit() != null
						&& inempDiscountPlanEntity.getHighstPreferentialLimit()
								.compareTo(BigDecimal.ZERO) > 0) {
					BigDecimal amount = waybillManagerService.queryDiscountFeeByEmployeeNo(waybillEntity.getEmployeeNo(),waybillEntity.getBillTime());
					if (amount == null) {
						amount = BigDecimal.ZERO;
					}
					BigDecimal differenceValue = inempDiscountPlanEntity
							.getHighstPreferentialLimit().subtract(
									amount.divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
					if (differenceValue.compareTo(BigDecimal.ZERO) <= 0) {
						throw new WaybillValidateException(
								"此工号优惠额度已用完，不能开内部发货运单");
					} 
				} else {
					throw new WaybillValidateException(
							"此工号优惠额度已用完，不能开内部发货运单");
				}
			} else {
				throw new WaybillValidateException(
						"此工号优惠额度已用完，不能开内部发货运单");
			}
		}
	}

	/**
	 * 图片类型code转换name
	 * @param code
	 * @return
	 */
	private String covertCodeToName(String code) {
		String name = "";
		if (StringUtil.isBlank(code)) {
			return name;
		}
		if (WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(code)) {
			name = "待录入";
		} else if (WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(code)) {
			name = "已分配";
		} else if (WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.equals(code)) {
			name = "已取消";
		} else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(code)) {
			name = "已退回";
		}
		return name;
	}
	
	
	

	public void setPdaScanService(IPdaScanService pdaScanService) {
		this.pdaScanService = pdaScanService;
	}
	
	/**
	 * 保存PDA扫描信息
	 * @author 200972  lanhuilan
	 * @date 2015-01=31 上午8:58:38
	 */
	public  void savePdaScanInfo(PdaScanQueryDto pdaScanInfo){
		//集中设置为接货
		pdaScanInfo.setTaskType("PICKUP");
		pdaScanService.savePdaScanInfo(pdaScanInfo);
		
	}
	
	/**
	 * 完成接货并激活运单
	 * @author 200972 lanhuilan 
	 * @date 2015-02-03  下午10:02:53
	 */
	@Override
	public void overPickup(String taskId) {
		pdaScanService.overPickup(taskId);
		addEwaybillRelateEntityData(taskId);
	}
	
	
	
	/**
	 * DMANA-6657 PDA系统从FOSS间接获得差错和投诉信息(PDA从OA调用差错信息)
	 * @author Foss-198719-yetao
	 * @throws IOException 
	 * @date 2014-12-15
	 */
	@Override
	public List<RewardFineDetailEntity> queryRewardFineDetail(RewardFineDetailVo rewardFineConditionDto) throws IOException{

		if(rewardFineConditionDto == null){
			throw new PdaInterfaceException("参数为空！");
		}
		if(StringUtils.isEmpty(rewardFineConditionDto.getEmpCode())){
			throw new BusinessException("工号不能为空");
		}
		if(rewardFineConditionDto.getStartTime()==null){
			throw new BusinessException("开始时间不能为空");
		}
		if(rewardFineConditionDto.getEndTime()==null){
			throw new BusinessException("结束时间不能为空");
		}
		String url=this.getOaError();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String empcode=rewardFineConditionDto.getEmpCode();
		Long startTime=rewardFineConditionDto.getStartTime();
		Long endTime=rewardFineConditionDto.getEndTime();		
		//在这里我们要把传过去的日期格式要encode一下，让它成为能够被http解析的url参数		
		String startTimeNew=URLEncoder.encode(sdf.format(startTime),"UTF-8");
		String endTimeNew=URLEncoder.encode(sdf.format(endTime),"UTF-8");		
		String urls=url+"empCode="+empcode+"&startTime="+startTimeNew+"&endTime="+endTimeNew;		
		URL urlNew = new URL(urls);
		HttpURLConnection httpConn = (HttpURLConnection) urlNew.openConnection();		
		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
		httpConn.setRequestProperty("Content-Type","application/json;charset=UTF-8");		
		InputStream  input = httpConn.getInputStream();		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input,"UTF-8"));		
		String jsonString=new String(bufferedReader.readLine().getBytes(), "UTF-8");
		if(jsonString!=null){/*
			net.sf.json.JSONObject obj=net.sf.json.JSONObject.fromObject(jsonString);
			try {
				bufferedReader.close();
			} catch (IOException e) {			
				e.printStackTrace();
			}
			OAResultMessageEntity oaResult=null;
			Gson gson = new Gson();
			oaResult = gson.fromJson(obj.get("ResultMessageEntity").toString(),OAResultMessageEntity.class);
			String messageCod=oaResult.getMessage_code();
			if(messageCod.equals("00000") || messageCod==null){
				return null;
			}
			//差错信息详情
			List<RewardFineDetailEntity> detailList = new ArrayList<RewardFineDetailEntity>();
			if(oaResult!=null && oaResult.getDetail()!=null ){
				detailList.add(oaResult.getDetail());
				return detailList;	
			}else{
				LOGGER.info("OA返回差错明细为空");
				return null;
			}
		*/}else{
			return null;
		}
		return null;
	}
	/**
	 * DMANA-6657 PDA系统从FOSS间接获得差错和投诉信息(PDA从CRM调用差错信息)
	 * @author Foss-xudan
	 * @throws IOException 
	 * @date 
	 */
	@Override
	public List<ComplaintDetail> queryComplaintInfoDetail(RewardFineDetailVo rewardFineConditionDto) throws IOException {

		if(rewardFineConditionDto == null){
			throw new PdaInterfaceException("参数为空！");
		}
		if(StringUtils.isEmpty(rewardFineConditionDto.getEmpCode())){
			throw new BusinessException("工号不能为空");
		}
		HttpClient httpClient = new HttpClient();
		String code1="ESB_CRM2ESB_CRM_SELECT_GOODS_ORDER";
		//设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod postMethod =
				new PostMethod(this.getCrmComplaintAddress());
		postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
		postMethod.addRequestHeader("version","0.1");
		postMethod.addRequestHeader("esbServiceCode",code1);		

/*		net.sf.json.JSONObject jsonObj = new net.sf.json.JSONObject();
		String js = jsonObj.fromObject(rewardFineConditionDto).toString();
		RequestEntity entity = null;
		entity = new StringRequestEntity(js,"application/json","UTF-8");
		postMethod.setRequestEntity(entity);
		postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");	   
		// 执行postMethod
		httpClient.executeMethod(postMethod); 
		String responseBody = postMethod.getResponseBodyAsString();
		Map<String, Class> classMap = new HashMap<String, Class>();  
		classMap.put("complaintDetail",ComplaintDetail.class);  
		ComplaintInfoForPdaResponse response=(ComplaintInfoForPdaResponse)net.sf.json.JSONObject.toBean(
				net.sf.json.JSONObject.fromObject(responseBody), ComplaintInfoForPdaResponse.class, classMap);
		//------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
*/
		List<ComplaintDetail> detailList =null;
//		detailList = response.getComplaintDetail();
		return detailList;					
		}
	/**
	 * @需求：大件上楼优化需求
	 * @功能：验证是否满足大件上楼和送货上楼的条件
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-05-05下午16:55
	 */
	public void validateShslOrDjsl(WaybillPendingEntity entity,WoodenRequirementsEntity woodenEntity){
		judgeBeanForLDU(entity,woodenEntity);
		judgeBeanForDU(entity,woodenEntity);
	}
	
	/**
	 * @需求：大件上楼优化需求
	 * @功能：判断大件上楼是否满足开单规则
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02 下午15:50
	 */
	public void judgeBeanForLDU(WaybillPendingEntity bean,WoodenRequirementsEntity woodenEntity){
		if(bean.getReceiveMethod()==null){
			//提货方式不能为空
			throw new WaybillValidateException("提货方式不能为空");
		}else{
			String valueCode=bean.getReceiveMethod();
			if(WaybillConstants.LARGE_DELIVER_UP.equals(valueCode)
				||WaybillConstants.LARGE_DELIVER_UP_AIR.equals(valueCode)){
				BigDecimal goodsWeightTotal=bean.getGoodsWeightTotal();
				Integer goodsQtyTotal=bean.getGoodsQtyTotal();
				if(goodsWeightTotal==null
						||goodsWeightTotal.compareTo(BigDecimal.ZERO)<=0){
					//货物总重量为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总重量不可为空或者小于等于0");
				}else if(goodsQtyTotal==null
						||goodsQtyTotal<=0){
					//货物总件数为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总件数不可为空或者小于等于0");
				}else{
					BigDecimal qty=new BigDecimal(goodsQtyTotal);
					BigDecimal pertotle=(goodsWeightTotal.divide(qty,2,BigDecimal.ROUND_HALF_DOWN));
					if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_50).setScale(0))<0){
						//单件货物重量低于50公斤不可开大件上楼
						throw new WaybillValidateException("单票货物重量小于50公斤不可开大件上楼");
					}else{
						if(woodenEntity==null||!whetherMakeWoodYoke(woodenEntity)){
							//没有打木架时，单件货物重量超过130公斤不可开大件上楼
							if(pertotle.compareTo(new BigDecimal(NumberConstants.NUMBER_130).setScale(0))>0){
								throw new WaybillValidateException("单件货物重量大于130公斤不可开大件上楼");
							}
						}else{
							//您选择了打木架，单件货物重量超过170公斤不可开大件上楼
							if(pertotle.compareTo(new BigDecimal(NUMBER_170).setScale(0))>0){
								throw new WaybillValidateException("有打木架情况下，单件货物重量大于170公斤不可开大件上楼");
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * @需求：大件上楼优化需求
	 * @功能：判断是否有打木架信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02 下午15:50
	 */
	public boolean whetherMakeWoodYoke(WoodenRequirementsEntity bean) {
		Integer salverGoodsNum = bean.getSalverGoodsNum();
		if (salverGoodsNum == null || salverGoodsNum == 0) {
			boolean flagStandGoodsNum = false;
			boolean flagBoxGoodsNum = false;
			Integer standGoodsNum = bean.getStandGoodsNum();
			Integer boxGoodsNum = bean.getBoxGoodsNum();
			if (standGoodsNum != null && standGoodsNum != 0) {
				flagStandGoodsNum = true;
			}
			if (boxGoodsNum != null && boxGoodsNum != 0) {
				flagBoxGoodsNum = true;
			}
			if (flagStandGoodsNum || flagBoxGoodsNum) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}

	}
	
	/**
	 * @需求：大件上楼优化需求
	 * @功能：判断是否满足送货上楼开单
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02上午11:28
	 */
	public void judgeBeanForDU(WaybillPendingEntity bean,WoodenRequirementsEntity woodenEntity){
		if(bean.getReceiveMethod()==null){
			//提货方式不能为空
			throw new WaybillValidateException("提货方式不能为空");
		}else{
			String valueCode=bean.getReceiveMethod();
			if(WaybillConstants.DELIVER_UP.equals(valueCode)
				||WaybillConstants.DELIVER_UP_AIR.equals(valueCode)){
				BigDecimal goodsWeightTotal=bean.getGoodsWeightTotal();
				Integer goodsQtyTotal=bean.getGoodsQtyTotal();
				if(goodsWeightTotal==null
						||goodsWeightTotal.compareTo(BigDecimal.ZERO)<=0){
					//货物总重量为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总重量不可为空或者小于等于0");
				}else if(goodsQtyTotal==null
						||goodsQtyTotal<=0){
					//货物总件数为null或者为0时，提示不可开单送货上楼
					throw new WaybillValidateException("货物总件数不可为空或者小于等于0");
				}else{
					BigDecimal qty=new BigDecimal(goodsQtyTotal);
					BigDecimal pertotle=(goodsWeightTotal.divide(qty,2,BigDecimal.ROUND_HALF_DOWN));
					if(woodenEntity==null||!whetherMakeWoodYoke(woodenEntity)){
						//没有打木架时，单件货物重量超过50公斤不可开送货上楼
						BigDecimal judgeStandard=new BigDecimal(NumberConstants.NUMBER_50).setScale(0);
						if(pertotle.compareTo(judgeStandard)>0){
							throw new WaybillValidateException("单件货物重量超过50公斤不可开送货上楼");
						}
					}else{
						//您选择了打木架，只有在单件重量超过0kg，小于等于65kg时，允许开单送货上楼
						BigDecimal judgeStandard=new BigDecimal(NUMBER_65).setScale(0);
						if(pertotle.compareTo(judgeStandard)>0){
							throw new WaybillValidateException("有打木架情况下，单件货物重量超过65公斤不可开送货上楼");
						}
					}
				}
			}
		}
	}

	/**
	 * 提交快递PDA开快递返单数据
	 * 1.补录界面的收件人信息及发件人信息，根据原单号自动带出并对调，不可修改
	 * 2.开单类型：默认返单；原单号：默认显示
	 * 3.提货方式：默认送货，可修改
	 * 4.运输性质：默认标准快递，可修改
	 * 5.重量：默认为1kg，可修改
	 * 6.付款方式：默认为现付，可修改
	 * 7.由营业部补打标签
	 * 8.快递员可以补打标签
	 * 返单类型：无需返单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-18 21:26:18
	 * @param waybillExpressPdaDto
	 * @param billOrgCode
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public ResultDto submitWaybillExpressReturnBill(WaybillExpressPdaDto waybillExpressPdaDto, String billOrgCode) {

		if(waybillExpressPdaDto == null || StringUtils.isEmpty(billOrgCode)){
			throw new PdaInterfaceException("传入的Dto或收货部门编码不能为空");
		}
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isEmpty(waybillExpressPdaDto.getOriginalWaybillNo())){
			sb.append("原单号");
		}
		if(StringUtils.isEmpty(waybillExpressPdaDto.getWaybillNo())){
			sb.append("新单号");
		}
		if(StringUtils.isEmpty(waybillExpressPdaDto.getBillUserNo())){
			sb.append("司机工号");
		}
		if(StringUtils.isEmpty(waybillExpressPdaDto.getExpressEmpCode())){
			sb.append("快递员工号");
		}
		/*if(StringUtils.isEmpty(waybillExpressPdaDto.getTargetOrgCode())){
			sb.append("目的站");
		}*/
		
		if(StringUtils.isNotEmpty(sb.toString())){
			throw new PdaInterfaceException(sb.toString()+"为空");
		}
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillExpressPdaDto.getOriginalWaybillNo());
		if(waybillEntity == null){
			throw new PdaInterfaceException("根据运单号:"+waybillExpressPdaDto.getOriginalWaybillNo()+"没有查询到对应的运单信息数据");
		}
		//
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillExpressPdaDto.getOriginalWaybillNo());
		if(actualFreightEntity == null){
			throw new PdaInterfaceException("根据运单号:"+waybillExpressPdaDto.getOriginalWaybillNo()+"没有查询到对应的实际承运信息数据");
		}
		ResultDto resultDto = new ResultDto();
		//其实这块可以完全按照电子运单自动开单的逻辑进行走下去
		//进行数据的封装
		//NOT_RETURN_BILL
		//只是对数据的进行转换
		WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
		BeanUtils.copyProperties(waybillEntity, waybillPendingEntity);
		//当前时间
		Date nowDate = new Date();
		//进行原单号与现有单号的更换
		waybillPendingEntity.setId(UUIDUtils.getUUID());
		//运单号
		waybillPendingEntity.setWaybillNo(waybillExpressPdaDto.getWaybillNo());
		//这里的订单数据为NULL
		waybillPendingEntity.setOrderNo(null);
		//原始运单号
		waybillPendingEntity.setOriginalWaybillNo(waybillExpressPdaDto.getOriginalWaybillNo());
		//设置为无需返单
		waybillPendingEntity.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);
		//设置状态为有效
		waybillPendingEntity.setActive(FossConstants.YES);
		//设置是否完成接货为N
		waybillPendingEntity.setHandoverStatus(FossConstants.NO);
		//设置产品类型
		waybillPendingEntity.setIsExpress(FossConstants.YES);
		if(StringUtils.isNotEmpty(waybillExpressPdaDto.getProductCode())){
			ProductEntity productEntity = productService.getLevel3ProductInfo(waybillExpressPdaDto.getProductCode());
			waybillPendingEntity.setProductCode(productEntity == null ? WaybillConstants.PACKAGE : productEntity.getCode());
			waybillPendingEntity.setProductId(productEntity == null ? WaybillConstants.PACKAGE : productEntity.getId());
		}else{
			ProductEntity productEntity = productService.getLevel3ProductInfo(WaybillConstants.PACKAGE);
			waybillPendingEntity.setProductCode(productEntity == null ? WaybillConstants.PACKAGE : productEntity.getCode());
			waybillPendingEntity.setProductId(productEntity == null ? WaybillConstants.PACKAGE : productEntity.getId());
		}
		//设置开单类别
		waybillPendingEntity.setReturnType(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL);
		//设置总重量
		if(waybillExpressPdaDto.getGoodsWeightTotal() != null && waybillExpressPdaDto.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) >= 0){
			waybillPendingEntity.setGoodsWeightTotal(waybillExpressPdaDto.getGoodsWeightTotal());
		}else{
			waybillPendingEntity.setGoodsWeightTotal(BigDecimal.ONE);
		}
		//设置总体积
		if(waybillExpressPdaDto.getGoodsVolumeTotal() != null && waybillExpressPdaDto.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) >= 0){
			waybillPendingEntity.setGoodsVolumeTotal(waybillExpressPdaDto.getGoodsVolumeTotal());
		}else{
			waybillPendingEntity.setGoodsVolumeTotal(BigDecimal.ZERO);
		}
		//设置总件数
		if(waybillExpressPdaDto.getGoodsQty() != null && waybillExpressPdaDto.getGoodsQty() > 0){
			waybillPendingEntity.setGoodsQtyTotal(waybillExpressPdaDto.getGoodsQty());
		}else{
			waybillPendingEntity.setGoodsQtyTotal(1);
		}
		//设置货物名称
		if(StringUtils.isNotEmpty(waybillExpressPdaDto.getGoodsName())){
			waybillPendingEntity.setGoodsName(waybillExpressPdaDto.getGoodsName());
		}else{
			waybillPendingEntity.setGoodsName(waybillExpressPdaDto.getWaybillNo()+"返单");
		}
		//发货人数据
		//是否大客户
		waybillPendingEntity.setDeliveryBigCustomer(waybillEntity.getReceiveBigCustomer());
		waybillPendingEntity.setDeliveryCustomerId(waybillEntity.getReceiveCustomerId());
		waybillPendingEntity.setDeliveryCustomerCode(waybillEntity.getReceiveCustomerCode());
		waybillPendingEntity.setDeliveryCustomerName(waybillEntity.getReceiveCustomerName());
		waybillPendingEntity.setDeliveryCustomerContact(waybillEntity.getReceiveCustomerContact());
		waybillPendingEntity.setDeliveryCustomerContactId(waybillEntity.getReceiverCustContactId());
		waybillPendingEntity.setDeliveryCustomerMobilephone(waybillEntity.getReceiveCustomerMobilephone());
		waybillPendingEntity.setDeliveryCustomerPhone(waybillEntity.getReceiveCustomerPhone());

		waybillPendingEntity.setDeliveryCustomerNationCode(waybillEntity.getReceiveCustomerNationCode());
		waybillPendingEntity.setDeliveryCustomerProvCode(waybillEntity.getReceiveCustomerProvCode());
		waybillPendingEntity.setDeliveryCustomerCityCode(waybillEntity.getReceiveCustomerCityCode());
		waybillPendingEntity.setDeliveryCustomerDistCode(waybillEntity.getReceiveCustomerDistCode());
		waybillPendingEntity.setDeliveryCustomerAddress(waybillEntity.getReceiveCustomerAddress());
		waybillPendingEntity.setDeliveryCustomerAddressNote(actualFreightEntity.getReceiveCustomerAddressNote());
		
		//收货人数据
		//是否大客户
		waybillPendingEntity.setReceiveBigCustomer(waybillEntity.getDeliveryBigCustomer());
		waybillPendingEntity.setReceiveCustomerId(waybillEntity.getDeliveryCustomerId());
		waybillPendingEntity.setReceiveCustomerCode(waybillEntity.getDeliveryCustomerCode());
		waybillPendingEntity.setReceiveCustomerName(waybillEntity.getDeliveryCustomerName());
		waybillPendingEntity.setReceiveCustomerContact(waybillEntity.getDeliveryCustomerContact());
		waybillPendingEntity.setReceiveCustomerContactId(waybillEntity.getDeliverCustContactId());
		waybillPendingEntity.setReceiveCustomerMobilephone(waybillEntity.getDeliveryCustomerMobilephone());
		waybillPendingEntity.setReceiveCustomerPhone(waybillEntity.getDeliveryCustomerPhone());

		waybillPendingEntity.setReceiveCustomerNationCode(waybillEntity.getDeliveryCustomerNationCode());
		waybillPendingEntity.setReceiveCustomerProvCode(waybillEntity.getDeliveryCustomerProvCode());
		waybillPendingEntity.setReceiveCustomerCityCode(waybillEntity.getDeliveryCustomerCityCode());
		waybillPendingEntity.setReceiveCustomerDistCode(waybillEntity.getDeliveryCustomerDistCode());
		waybillPendingEntity.setReceiveCustomerAddress(waybillEntity.getDeliveryCustomerAddress());
		waybillPendingEntity.setReceiveCustomerAddressNote(actualFreightEntity.getDeliveryCustomerAddressNote());
		
		//设置提货网点默认为当初收货部门
		waybillPendingEntity.setCustomerPickupOrgCode(waybillEntity.getReceiveOrgCode());
		try {
			waybillPendingEntity.setActive(FossConstants.ACTIVE);
			if(waybillExpressPdaDto.getBillStart() != null){
				waybillPendingEntity.setCreateTime(waybillExpressPdaDto.getBillStart());
			}else{
				waybillPendingEntity.setCreateTime(nowDate);
			}
			//设置开单人编码
			waybillPendingEntity.setCreateUserCode(waybillExpressPdaDto.getBillUserNo());
			//设置纤
			if(waybillExpressPdaDto.getFibre() != null && waybillExpressPdaDto.getFibre() >= 0){
				waybillPendingEntity.setFibreNum(waybillExpressPdaDto.getFibre());
			}
			if(waybillExpressPdaDto.getPaper() != null && waybillExpressPdaDto.getPaper() >= 0){
				waybillPendingEntity.setPaperNum(waybillExpressPdaDto.getPaper());
			}
			if(waybillExpressPdaDto.getSalver() != null && waybillExpressPdaDto.getSalver() >= 0){
				waybillPendingEntity.setSalverNum(waybillExpressPdaDto.getSalver());
			}
			if(waybillExpressPdaDto.getMembrane() != null && waybillExpressPdaDto.getMembrane() >= 0){
				waybillPendingEntity.setMembraneNum(waybillExpressPdaDto.getMembrane());
			}
			if(NULL.equalsIgnoreCase(waybillExpressPdaDto.getOtherPackageType())){
				waybillPendingEntity.setOtherPackage(null);
			}else{
				waybillPendingEntity.setOtherPackage(waybillExpressPdaDto.getOtherPackageType());
			}
			//设置货物件数
			if(waybillExpressPdaDto.getGoodsQty() != null && waybillExpressPdaDto.getGoodsQty() >= 0){
				waybillPendingEntity.setGoodsQtyTotal(waybillExpressPdaDto.getGoodsQty());
			}
			if(waybillExpressPdaDto.getGoodsVolumeTotal() != null && waybillExpressPdaDto.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) >= 0){
				waybillPendingEntity.setGoodsVolumeTotal(waybillExpressPdaDto.getGoodsVolumeTotal());
			}
			if(waybillExpressPdaDto.getGoodsWeightTotal() != null && waybillExpressPdaDto.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) >= 0){
				waybillPendingEntity.setGoodsWeightTotal(waybillExpressPdaDto.getGoodsVolumeTotal());
			}
			//挂账用
			waybillPendingEntity.setReceiveOrgCode(billOrgCode);
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getGoodsTypeCode())){
				//设置
				waybillPendingEntity.setGoodsTypeCode(waybillExpressPdaDto.getGoodsTypeCode());
			}
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getLicensePlateNum())){
				waybillPendingEntity.setLicensePlateNum(waybillExpressPdaDto.getLicensePlateNum());
			}
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getOrderNo())){
				waybillPendingEntity.setOrderNo(waybillExpressPdaDto.getOrderNo());
			}
			//短信标识
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getIsSMS())){
				waybillPendingEntity.setIsSMS(waybillExpressPdaDto.getIsSMS());
			}else{
				waybillPendingEntity.setIsSMS(FossConstants.NO);
			}
			// 是否上门接货
			waybillPendingEntity.setPickupToDoor(FossConstants.NO);
			// 是否集中接货
			waybillPendingEntity.setPickupCentralized(FossConstants.NO);
			// 是否大车直送
			waybillPendingEntity.setCarDirectDelivery(FossConstants.NO);
			// 是否贵重物品
			waybillPendingEntity.setPreciousGoods(FossConstants.NO);
			// 是否异型货物
			waybillPendingEntity.setSpecialShapedGoods(FossConstants.NO);
			// 预付费保密
			waybillPendingEntity.setSecretPrepaid(FossConstants.NO);
			// 装卸费
			waybillPendingEntity.setServiceFee(BigDecimal.ZERO);
			// 有效
			waybillPendingEntity.setActive(FossConstants.ACTIVE);
			// 是否禁行
			waybillPendingEntity.setForbiddenLine(FossConstants.NO);
			// 币种
			waybillPendingEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 是否整车
			waybillPendingEntity.setIsWholeVehicle(FossConstants.NO);
			// 整车约车报价
			waybillPendingEntity.setWholeVehicleAppfee(BigDecimal.ZERO);
			// 计费重量
			waybillPendingEntity.setBillWeight(BigDecimal.ZERO);
			//退款类型
			waybillPendingEntity.setRefundType(waybillPendingEntity.getRefundType());
			// 根据订单号查询订单来源
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getOrderNo())){
				DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillExpressPdaDto.getOrderNo());
				waybillPendingEntity.setOrderChannel(dispatchOrderEntity != null ? dispatchOrderEntity.getOrderSource() : null);
				if(dispatchOrderEntity!=null){
					String deliveryCustomerModle = dispatchOrderEntity.getMobile();
					if(StringUtils.isNotEmpty(deliveryCustomerModle)){
						waybillPendingEntity.setDeliveryCustomerMobilephone(deliveryCustomerModle);
						waybillPendingEntity.setDeliveryCustomerName(dispatchOrderEntity.getCustomerName());
						waybillPendingEntity.setDeliveryCustomerPhone(dispatchOrderEntity.getTel());
					}
					//提货地址备注
					if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
						waybillPendingEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
					}
					//发货地址备注
					if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
						waybillPendingEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
					}
				}
			}
			//是否德邦沪内部发货客户
			if(FossConstants.YES.equals(waybillExpressPdaDto.getNeedDepponCustomerCode())){
				waybillPendingEntity.setDeliveryCustomerCode(WaybillConstants.DEPPON_CUSTOMER);
				//CustomerDto dtoc = queryCustomerService.queryCustInfoByCode(WaybillConstants.DEPPON_CUSTOMER);
				CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
				condition.setExactQuery(true);
				condition.setCustCode(WaybillConstants.DEPPON_CUSTOMER);
				List<CustomerContactDto> dtos = queryCustomerService.queryCustomerInfo(condition);
				if(CollectionUtils.isNotEmpty(dtos)){
					CustomerContactDto d = dtos.get(0);
					waybillPendingEntity.setDeliveryCustomerMobilephone(d.getMobilePhone());
					if(StringUtils.isNotEmpty(d.getCustName())){
						waybillPendingEntity.setDeliveryCustomerName(d.getCustName());
					}else if(StringUtils.isNotEmpty(d.getDeptName())){
						waybillPendingEntity.setDeliveryCustomerName(d.getDeptName());
					}else if(StringUtils.isNotEmpty(d.getContactName())){
						waybillPendingEntity.setDeliveryCustomerName(d.getContactName());
					}
					waybillPendingEntity.setDeliveryCustomerPhone(d.getOfficePhone());
					waybillPendingEntity.setDeliveryCustomerContact(d.getContactName());
				}
			}
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getPaidMethod())){
				waybillPendingEntity.setPaidMethod(waybillExpressPdaDto.getPaidMethod());
			}else{
				//默认为现金
				waybillPendingEntity.setPaidMethod(WaybillConstants.CASH_PAYMENT);
			}
			if(StringUtils.isNotBlank(waybillExpressPdaDto.getSendEmployeeCode()) && 
				!"NULL".equalsIgnoreCase(waybillExpressPdaDto.getSendEmployeeCode())){
				waybillPendingEntity.setInnerNotes("发货人工号："+waybillExpressPdaDto.getSendEmployeeCode()+";");
			}
			
			Log.error("input recevei method : "+waybillExpressPdaDto.getReceiveMethod());
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getReceiveMethod())){
				waybillPendingEntity.setReceiveMethod(waybillExpressPdaDto.getReceiveMethod());
			}else{
				//默认为汽运送货上楼
				waybillPendingEntity.setReceiveMethod(WaybillConstants.DELIVER_UP);
			}
			Log.error("BEFORE inert  waybillPendingEntity recevei method : "+waybillPendingEntity.getReceiveMethod());
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getTargetOrgCode())){
				waybillPendingEntity.setCustomerPickupOrgCode(waybillExpressPdaDto.getTargetOrgCode());
			}
			if(waybillExpressPdaDto.getWood() != null && waybillExpressPdaDto.getWood() >= 0){
				waybillPendingEntity.setWoodNum(waybillExpressPdaDto.getWood());
			}
			//设置为PDA_PENDING
			waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
			//快递新增字段
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getExpressEmpCode())){
				//快递员工号
				waybillPendingEntity.setExpressEmpCode(waybillExpressPdaDto.getExpressEmpCode());
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(waybillExpressPdaDto.getExpressEmpCode());
				if(employee != null){
					//快递员名称
					waybillPendingEntity.setExpressEmpName(waybillExpressPdaDto.getExpressEmpName());
					OrgAdministrativeInfoEntity empOrgInfo = employee.getDepartment();
					if(empOrgInfo != null){
						//快递点部门编号
						waybillPendingEntity.setExpressOrgCode(empOrgInfo.getCode());
						//快递点部门名称
						waybillPendingEntity.setExpressOrgName(empOrgInfo.getName());
					}else{
						OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(employee.getOrgCode(), new Date());
						if(orgInfo != null){
							//快递点部门编号
							waybillPendingEntity.setExpressOrgCode(orgInfo.getCode());
							//快递点部门名称
							waybillPendingEntity.setExpressOrgName(orgInfo.getName());
						}
					}
				}else{
					//快递员名称
					waybillPendingEntity.setExpressEmpName(waybillExpressPdaDto.getExpressEmpCode());
				}
			}
			//快递PDA串号
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getPdaSerial())){
				waybillPendingEntity.setPdaSerial(waybillExpressPdaDto.getPdaSerial());
			}
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getBankTradeSerail())){
				//快递银行交易流水号
				waybillPendingEntity.setBankTradeSerail(waybillExpressPdaDto.getBankTradeSerail());
			}
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getStartOrg())){
				//快递这里很特殊  生成走货用
				waybillPendingEntity.setCreateOrgCode(waybillExpressPdaDto.getStartOrg());
			}else{
				waybillPendingEntity.setCreateOrgCode(waybillEntity.getCustomerPickupOrgCode());
			}
			if(waybillExpressPdaDto.getDiscountAmount() != null && waybillExpressPdaDto.getDiscountAmount().compareTo(BigDecimal.ZERO) >= 0){
				//优惠信息
				waybillPendingEntity.setPromotionsFee(waybillExpressPdaDto.getDiscountAmount());
			}
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getDiscountNo())){
				waybillPendingEntity.setPromotionsCode(waybillExpressPdaDto.getDiscountNo());
			}
			if(waybillExpressPdaDto.getAmount() != null && waybillExpressPdaDto.getAmount().compareTo(BigDecimal.ZERO) >= 0){
				waybillPendingEntity.setTotalFee(waybillExpressPdaDto.getAmount());
			}else{
				waybillPendingEntity.setTotalFee(BigDecimal.ZERO);
			}
			//pda实收运费对应waybillPendingEntity的预付费用PrePayAmount
			if(waybillExpressPdaDto.geActualFee() != null && waybillExpressPdaDto.geActualFee().compareTo(BigDecimal.ZERO) >= 0){
				waybillPendingEntity.setPrePayAmount(waybillExpressPdaDto.geActualFee());
			}else{
				waybillPendingEntity.setPrePayAmount(BigDecimal.ZERO);
			}
			//开单人工号
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getBillUserNo())){
				waybillPendingEntity.setDriverCode(waybillExpressPdaDto.getBillUserNo());
			}
			//开单时间
			if(waybillExpressPdaDto.getBillStart() != null){
				waybillPendingEntity.setBillTime(waybillExpressPdaDto.getBillStart());
			}
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getRefundType())){
				waybillPendingEntity.setRefundType(waybillExpressPdaDto.getRefundType());
			}
			//返单类型
			if(StringUtils.isNotEmpty(waybillExpressPdaDto.getReturnBillType())){
				waybillPendingEntity.setReturnBillType(waybillExpressPdaDto.getReturnBillType());
			}

			/**  快递暂时没有打木架功能
			 * 是否代打木架(否"N"或是"Y"),如需代打木架，操作WoodenRequirePendingEntity
			 */
			WoodenRequirePendingEntity woodenRequirePendingEntity = new WoodenRequirePendingEntity();
			if(FossConstants.YES.equals(waybillExpressPdaDto.getIsWood())) {
				woodenRequirePendingEntity.setWaybillNo(waybillExpressPdaDto.getWaybillNo());
				woodenRequirePendingEntity.setStandGoodsSize(waybillExpressPdaDto.getWoodSize());
				woodenRequirePendingEntity.setStandGoodsVolume(waybillExpressPdaDto.getWoodVolume());
				woodenRequirePendingEntity.setBoxGoodsSize(waybillExpressPdaDto.getWoodBoxSize());
				woodenRequirePendingEntity.setBoxGoodsVolume(waybillExpressPdaDto.getWoodBoxVolume());
				woodenRequirePendingDao.insertSelective(woodenRequirePendingEntity);
			}

			//增值服务,操作WaybillCHDtlPendingEntity
			List<ValueAddServiceDto> valueAddServiceDtoList = waybillExpressPdaDto.getValueAddServiceDtoList();
			if (CollectionUtils.isNotEmpty(valueAddServiceDtoList)) {
				for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
					if (!PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())
							&& !PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
						WaybillCHDtlPendingEntity waybillCHDtlPendingEntity = new WaybillCHDtlPendingEntity();
						waybillCHDtlPendingEntity.setActive(FossConstants.YES);
						waybillCHDtlPendingEntity.setAmount(valueAddServiceDto.getAmount());
						waybillCHDtlPendingEntity.setBillTime(waybillExpressPdaDto.getBillStart());
						waybillCHDtlPendingEntity.setWaybillNo(waybillExpressPdaDto.getWaybillNo());
						waybillCHDtlPendingEntity.setPricingEntryCode(valueAddServiceDto.getCode());
						waybillCHDtlPendingEntity.setCreateTime(waybillExpressPdaDto.getCreateTime());
						waybillCHDtlPendingEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						waybillCHDtlPendingEntity.setPricingCriDetailId(valueAddServiceDto.getSubType());												
						waybillCHDtlPendingDao.addWaybillCHDtlPendingSelective(waybillCHDtlPendingEntity);
					}
					//返单类型是在子表中传的
					if(PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(valueAddServiceDto.getCode())){
						waybillPendingEntity.setReturnBillType(valueAddServiceDto.getSubType());
						waybillExpressPdaDto.setReturnBillType(valueAddServiceDto.getSubType());
					}
					if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
						waybillPendingEntity.setInsuranceAmount(valueAddServiceDto.getAmount());
						
						// 保价费设置
						waybillPendingEntity.setInsuranceFee(BigDecimal.ZERO);
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
						// 代收货款设置
						waybillPendingEntity.setCodAmount(valueAddServiceDto.getAmount());
						String subType = valueAddServiceDto.getSubType();
						if(StringUtils.isNotEmpty(subType)){
							waybillPendingEntity.setRefundType(subType);
						}
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
						// 运费
						waybillPendingEntity.setTransportFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
						// 接货费
						waybillPendingEntity.setPickupFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
						// 送货费
						waybillPendingEntity.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_BZ.equals(valueAddServiceDto.getCode())) {
						// 送货费
						waybillPendingEntity.setPackageFee(valueAddServiceDto.getAmount());
					}
				}
			}
			
			//添加送货费
			waybillPendingEntity.setDeliveryGoodsFee(waybillExpressPdaDto.getDeliveryGoodsFee());
			
			//是否返货开单(是否返货业务 Y/N)
			if(waybillExpressPdaDto != null && FossConstants.ACTIVE.equals(waybillExpressPdaDto.getIsReturnGoods())
			         && StringUtils.isNotEmpty(waybillExpressPdaDto.getOriginalWaybillNo())){
				waybillPendingEntity.setReturnType(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				waybillPendingEntity.setOriginalWaybillNo(waybillExpressPdaDto.getOldWayBill());
			}
			//根据出发部门、到达部门产品类型进行一次走货路径的查询
			OrgInfoDto orgInfoDto = null;
			try {
				orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(false, waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getCreateOrgCode(), waybillPendingEntity.getProductCode());
			} catch (Exception e) {
				LOGGER.info("根据始发部门和到达部门查询走货路径出现异常，始发部门:" + waybillPendingEntity.getCreateOrgCode() + "到达部门：" + waybillPendingEntity.getCustomerPickupOrgCode()+"产品类型:" + waybillPendingEntity.getProductCode());
			}
			if(orgInfoDto == null) {
				// 添加运单
				WaybillEntity newWaybillEntity = new WaybillEntity();
				BeanUtils.copyProperties(waybillPendingEntity, newWaybillEntity);
				SaleDepartmentEntity saleDepartmentEntity = null;
				try {
					saleDepartmentEntity = waybillExpressService.getTargetOrgCode(newWaybillEntity);
				} catch (Exception e1) {
					LOGGER.info("根据收货人所在城市匹配对应虚拟营业部出现异常"+e1);
					e1.printStackTrace();
				}
				if(saleDepartmentEntity != null){
					try {
						orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(false, waybillPendingEntity.getCreateOrgCode(), saleDepartmentEntity.getCode(), waybillPendingEntity.getProductCode());
					} catch (Exception e) {
						LOGGER.info("根据始发部门和到达部门查询走货路径出现异常，始发部门:" + waybillPendingEntity.getCreateOrgCode() + "到达部门：" + saleDepartmentEntity.getCode()+"产品类型:" + waybillPendingEntity.getProductCode());
					}
					if(orgInfoDto != null){
						waybillPendingEntity.setCustomerPickupOrgCode(saleDepartmentEntity.getCode());
						newWaybillEntity.setCustomerPickupOrgCode(saleDepartmentEntity.getCode());
					}
				}
			}
			if(orgInfoDto != null){
				// 始发配载部门
				waybillPendingEntity.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());
				// 最终配载部门
				waybillPendingEntity.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());
			}
			/*String waybillId=waybillPendingDao.addWaybillEntity(waybillPendingEntity);
			waybillPendingEntity.setWaybillId(waybillId);
			waybillPendingDao.addWaybillExpressEntity(waybillPendingEntity);
			// 添加ActualFreight
			addActualFreightfd(waybillPendingEntity);*/
			// 添加货件
		//	addLabeledGoodExpress(waybillExpressPdaDto);
			//推送货物轨迹
		//	addSynTrack(waybillExpressPdaDto);
			Log.error("inert  waybillPendingEntity recevei method : "+waybillPendingEntity.getReceiveMethod() );
			waybillPendingDao.insertSelective(waybillPendingEntity);
			// 添加货签信息
			addLabelGoodPda(waybillPendingEntity);		
		} catch (BusinessException e) {
			LOGGER.info(e.getMessage(),e);
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("异常信息为:"+e);
		}
		// pda补录消息提醒,通知司机所在车队的集中开单组
		UserEntity userEntity = new UserEntity();
		if(StringUtils.isNotEmpty(waybillExpressPdaDto.getBillUserNo())){
			userEntity.setEmpCode(waybillExpressPdaDto.getBillUserNo());
			EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(waybillExpressPdaDto.getBillUserNo());
			if(employEntity != null){
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmployee(employEntity);
			}else{
				throw new PdaInterfaceException("查询不到该司机！");
			}
		}else{
			throw new PdaInterfaceException("司机编号为空！");
		}
		UserContext.setCurrentUser(userEntity);
		pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, null, waybillExpressPdaDto.getStartOrg(),
				waybillExpressPdaDto.getWaybillNo(), waybillExpressPdaDto.getWaybillNo());
		try {
			this.addWaybillTransportLabelGoodsInfo(waybillExpressPdaDto, waybillPendingEntity);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		//进行基础数据的封装
		resultDto.setCode(ResultDto.SUCCESS);
		resultDto.setMsg("快递PDA返单开单成功");
		return resultDto;
	}
	

	@Transactional
	public void addWaybillTransportLabelGoodsInfo(WaybillExpressPdaDto waybillExpressPdaDto, WaybillPendingEntity waybillPendingEntity) {
		//根据出发部门、到达部门产品类型进行一次走货路径的查询
		OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(false, waybillPendingEntity.getCreateOrgCode(), waybillPendingEntity.getCustomerPickupOrgCode(), waybillPendingEntity.getProductCode());
		// 添加运单
		WaybillEntity newWaybillEntity = new WaybillEntity();
		BeanUtils.copyProperties(waybillPendingEntity, newWaybillEntity);
		if(orgInfoDto == null) {
			SaleDepartmentEntity saleDepartmentEntity = waybillExpressService.getTargetOrgCode(newWaybillEntity);
			if(saleDepartmentEntity == null){
				LOGGER.info("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPendingEntity.getCreateOrgCode() + "到达部门：" + waybillPendingEntity.getCustomerPickupOrgCode());
				throw new PdaInterfaceException("根据始发部门和到达部门查询走货路径，始发部门:" + waybillPendingEntity.getCreateOrgCode() + "到达部门：" + waybillPendingEntity.getCustomerPickupOrgCode());
			}
		}
		if(orgInfoDto != null){
			// 始发配载部门
			newWaybillEntity.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());
			// 始发配载部门名称
			newWaybillEntity.setLoadOrgName(orgInfoDto.getFirstLoadOrgName());
			// 最终配载部门
			newWaybillEntity.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());
			// 最终配载部门名称
			newWaybillEntity.setLastLoadOrgName(orgInfoDto.getLastLoadOrgName());
		}
		// 对象传递
		TransportPathEntity transportPath = new TransportPathEntity();
		//运单号
		transportPath.setWaybillNo(waybillPendingEntity.getWaybillNo());
		//开单时间
		transportPath.setBillingTime(waybillPendingEntity.getBillTime());
		//开单组织 
		transportPath.setBillingOrgCode(waybillExpressPdaDto.getBillOrgCode());
		//当前库存部门编码
		String currentOrgCode = "";
		//根据集中接送货部门编码查询其对应的外场编码
		BillingGroupTransFerEntity entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode(waybillExpressPdaDto.getBillOrgCode());
		//判断对象是否为空
		if(null != entity){
			//外场编码
			currentOrgCode = entity.getTransFerCode();
			//非空判断
			if(StringUtil.isEmpty(currentOrgCode)){
				//若外场编码为空，则抛出异常信息（"集中接送货部门对应的外场编码为空！"）
				throw new PdaInterfaceException("集中接送货部门+"+waybillExpressPdaDto.getBillOrgCode()+"+对应的外场编码为空！");
			}
		}else{
			//若没有查询到外场编码，则抛出异常信息
			throw new PdaInterfaceException(waybillExpressPdaDto.getBillOrgCode()+"没有查询到外场编码");
		}
		//当前部门编码
		transportPath.setCurrentOrgCode(currentOrgCode);
		//最终到达部门编号
		transportPath.setDestOrgCode(waybillPendingEntity.getCustomerPickupOrgCode());
		//总重量
		transportPath.setTotalWeight(waybillPendingEntity.getGoodsWeightTotal());
		//总体积
		transportPath.setTotalVolume(waybillPendingEntity.getGoodsVolumeTotal());
		//总件数
		transportPath.setGoodsQtyTotal(waybillPendingEntity.getGoodsQtyTotal());
		//运输类型
		transportPath.setTransportModel(waybillPendingEntity.getProductCode());
		// 生成走货路径
		calculateTransportPathService.createTransportPath(transportPath);
		// 主键id
		newWaybillEntity.setId(UUIDUtils.getUUID());
		// 是否上门接货
		newWaybillEntity.setPickupToDoor(FossConstants.NO);
		// 是否集中接货
		newWaybillEntity.setPickupCentralized(FossConstants.NO);
		if(orgInfoDto != null){
			// 始发配载部门
			newWaybillEntity.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());
			// 始发配载部门名称
			newWaybillEntity.setLoadOrgName(orgInfoDto.getFirstLoadOrgName());
			// 最终配载部门
			newWaybillEntity.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());
			// 最终配载部门名称
			newWaybillEntity.setLastLoadOrgName(orgInfoDto.getLastLoadOrgName());
		}
		// 是否大车直送
		newWaybillEntity.setCarDirectDelivery(FossConstants.NO);
		// 是否贵重物品
		newWaybillEntity.setPreciousGoods(FossConstants.NO);
		// 是否异型货物
		newWaybillEntity.setSpecialShapedGoods(FossConstants.NO);
		// 预付费保密
		newWaybillEntity.setSecretPrepaid(FossConstants.NO);
		// 装卸费
		newWaybillEntity.setServiceFee(BigDecimal.ZERO);
		// 有效
		newWaybillEntity.setActive(FossConstants.ACTIVE);
		// 是否禁行
		newWaybillEntity.setForbiddenLine(FossConstants.NO);
		// 币种
		newWaybillEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 是否整车
		newWaybillEntity.setIsWholeVehicle(FossConstants.NO);
		// 整车约车报价
		newWaybillEntity.setWholeVehicleAppfee(BigDecimal.ZERO);
		// 计费重量
		newWaybillEntity.setBillWeight(BigDecimal.ZERO);
		/**
		 * 退款类型
		 */
		newWaybillEntity.setRefundType(waybillPendingEntity.getRefundType());
		// 增值服务
		List<ValueAddServiceDto> valueAddServiceDtoList = waybillExpressPdaDto.getValueAddServiceDtoList();
		// 接货费默认值
		newWaybillEntity.setPickupFee(BigDecimal.ZERO);
		if (CollectionUtils.isNotEmpty(valueAddServiceDtoList)) {
			for (ValueAddServiceDto valueAddServiceDto : valueAddServiceDtoList) {
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(valueAddServiceDto.getCode())) {
					// 保价费设置
					newWaybillEntity.setInsuranceFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(valueAddServiceDto.getCode())) {
					// 代收货款设置
					newWaybillEntity.setCodFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_FRT.equals(valueAddServiceDto.getCode())) {
					// 运费
					newWaybillEntity.setTransportFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(valueAddServiceDto.getCode())) {
					// 接货费
					newWaybillEntity.setPickupFee(valueAddServiceDto.getAmount());
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(valueAddServiceDto.getCode())) {
					// 送货费
					newWaybillEntity.setDeliveryGoodsFee(valueAddServiceDto.getAmount());
				} 
			}
		}
		// 预计到达时间
		newWaybillEntity.setPreArriveTime(waybillExpressPdaDto.getCreateTime());
		
		waybillDao.addWaybillEntity(newWaybillEntity);
		// 添加ActualFreight
		addActualFreight(newWaybillEntity);
		// 添加货件
		// 循环添加货签信息
		for (int i = 1; i <= newWaybillEntity.getGoodsQtyTotal(); i++) {
			LabeledGoodEntity labeledGood = new LabeledGoodEntity();
			// id
			labeledGood.setId(UUIDUtils.getUUID());
			// 运单号
			labeledGood.setWaybillNo(newWaybillEntity.getWaybillNo());
			// 有效
			labeledGood.setActive(FossConstants.ACTIVE);
			// 流水号
			labeledGood.setSerialNo(StringHandlerUtil.lpad(String.valueOf(i), NumberConstants.NUMBER_4, "0"));
			// 创建时间
			labeledGood.setCreateTime(newWaybillEntity.getCreateTime());
			// 修改时间
			labeledGood.setModifyTime(newWaybillEntity.getCreateTime());
			// 开单时间
			labeledGood.setBillTime(newWaybillEntity.getBillTime());
			// 初始化
			labeledGood.setInitalVale(FossConstants.YES);
			labeledGoodDao.insertSelective(labeledGood);
		}
	}

	public void setFocusRecordManagementService(
			IFocusRecordManagementService focusRecordManagementService) {
		this.focusRecordManagementService = focusRecordManagementService;
	}
	
	/**
	 * PDA创建返货工单，提交 原单号，发货人，发货人电话，出发部门，收货人，收货人电话，到达部门，
	 * 代收货款，保价，发货人地址，上报人，上报时间，收货人地址，申请事由，返货原因，返货类型。
	 */
	@Override
	public boolean createReturneDworkOrder(WaybillEntity bean) {
		// 判断是否创建过返货申请
		ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
		condition.setOriWaybill(bean.getWaybillNo());
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
			if("N".equals(entity.getIsHandle())){
				throw new TfrBusinessException("已经上报过返货申请");
			}
		}
			WaybillEntity querywaybillEntity = waybillManagerService.queryWaybillBasicByNo(bean.getWaybillNo());
			
			//根据运单号查询子母件关系表
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("waybillNo", bean.getWaybillNo());
			params.put("active", FossConstants.YES);
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
			
			if(null!=twoInOneWaybillDto&&
					StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.ACTIVE)&&
					(StringUtils.equals(bean.getReturnType(),WaybillConstants.RETURNTYPE_SAME_CITY)||
					StringUtils.equals(bean.getReturnType(),WaybillConstants.RETURNTYPE_OTHER_CITY))){
				
				throw new TfrBusinessException("子母件上报工单只可选择转寄退回件类型为：退回件，7天转寄退回件和外发3天转寄退回件！");
			}
			if(null!=twoInOneWaybillDto&&
					StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.ACTIVE)&&
					StringUtils.isEmpty(bean.getReturnMode())){
				
				throw new TfrBusinessException("子母件上报工单转寄退回件方式不能为空！");
			}
			//发货地址省市区
			String queryProviceByName="";
			String queryCityByCodeName="";
			String queryCountyByName="";
			//收货地址省市区
			String queryProviceName="";
			String queryCityName="";
			String queryCountyName="";
			if(null!=querywaybillEntity){
				CrmReturnedGoodsDto crmReturnedGoodsDto = new CrmReturnedGoodsDto();
				//上报通道
				crmReturnedGoodsDto.setReturnChanle("PDA_ACTIVE");
				//上报工单原因明细
				crmReturnedGoodsDto.setReturnDetails(bean.getReturnDetail());
				// 工号
				crmReturnedGoodsDto.setManReportCode(bean.getUserCode());
				// 姓名
				crmReturnedGoodsDto.setManReport(bean.getUserName());
				//部门
				crmReturnedGoodsDto.setReportDepartmentCode(bean.getDeptCode());
				crmReturnedGoodsDto.setCreateUserCode(bean.getUserCode());//创建人工号
				//设置返货方式--206860
				crmReturnedGoodsDto.setReturnMode(bean.getReturnMode());
				//当返货类型为“7天返货”，“外发3天返货”时
				if(bean.getReturnType().equals(WaybillConstants.RETURNTYPE_SEVEN_DAYS_RETURN)||
						bean.getReturnType().equals(WaybillConstants.RETURNTYPE_SEND_OUT_THREE_DAYS_RETURN)){
					crmReturnedGoodsDto.setOriWaybill(bean.getWaybillNo());//原单号
					crmReturnedGoodsDto.setReturnReason(bean.getReturnReason());//返货原因
					crmReturnedGoodsDto.setReturnType(bean.getReturnType());//返货类型
					crmReturnedGoodsDto.setReportContent(bean.getApplicationReason());//申请事由
					// 设置id
					crmReturnedGoodsDto.setFossId(UUIDUtils.getUUID());
					if(StringUtils.isNotBlank(querywaybillEntity.getDeliveryCustomerMobilephone())){
						crmReturnedGoodsDto.setPhoneSend(querywaybillEntity.getDeliveryCustomerMobilephone());//发货人联系方式
					}else{
						crmReturnedGoodsDto.setPhoneSend(querywaybillEntity.getDeliveryCustomerPhone());//发货人联系方式
					}
					// 受理状态默认为未受理
					crmReturnedGoodsDto.setReturnStatus(WaybillConstants.ACCEPTSTATUS_REFUSED);
	
					crmReturnedGoodsDto.setReturnManReceive(querywaybillEntity.getReceiveCustomerName());//收货人
					if(StringUtils.isNotBlank(querywaybillEntity.getReceiveCustomerMobilephone())){
						crmReturnedGoodsDto.setPhoneReceive(querywaybillEntity.getReceiveCustomerMobilephone());//收货人电话
					}else{
						crmReturnedGoodsDto.setPhoneReceive(querywaybillEntity.getReceiveCustomerPhone());//收货人电话
					}
					crmReturnedGoodsDto.setTimeReport(new Date());//上报时间
	
					
					
					// 设置订单来源
					crmReturnedGoodsDto.setOrderSource(querywaybillEntity.getOrderChannel());
					// 设置发货人姓名
					crmReturnedGoodsDto.setManSend(querywaybillEntity
							.getDeliveryCustomerContact());
					// 设置发货人编码
					crmReturnedGoodsDto.setManSendCode(querywaybillEntity
							.getDeliveryCustomerCode());
					// 设置收货人姓名
					crmReturnedGoodsDto.setManReceive(querywaybillEntity
							.getReceiveCustomerContact());
					// 设置收货人编码
					crmReturnedGoodsDto.setManReceiveCode(querywaybillEntity
							.getReceiveCustomerCode());
					// 设置id
					crmReturnedGoodsDto.setFossId(UUIDUtils.getUUID());
					// 设置出发部门
					OrgAdministrativeInfoEntity deptStart = waybillManagerService.queryOrgInfoByCode(querywaybillEntity.getReceiveOrgCode());	
					if (null != deptStart) {
						crmReturnedGoodsDto.setDeptStart(deptStart.getName());
					}
					// 设置到达部门
					OrgAdministrativeInfoEntity deptArrive = waybillManagerService.queryOrgInfoByCode(querywaybillEntity.getCustomerPickupOrgCode());
					if (null != deptArrive) {
						crmReturnedGoodsDto.setDeptArrive(deptArrive.getName());
					}
					// 设置代收贷款
					if(null != bean.getCodAmount()){
						crmReturnedGoodsDto.setMoneyReceive(bean.getCodAmount());
					}else{
						crmReturnedGoodsDto.setMoneyReceive(querywaybillEntity.getCodAmount());
					}
					// 设置保价
					if(null != bean.getInsuranceAmount()){
						crmReturnedGoodsDto.setMoneyInsured(bean.getInsuranceAmount());
					}else{
						crmReturnedGoodsDto.setMoneyInsured(querywaybillEntity.getInsuranceAmount());
					}
					// 获取运单发货地址的省-市-区
					if(StringUtil.isNotEmpty(querywaybillEntity.getDeliveryCustomerProvCode())){
						queryProviceByName = onlineAddressDao.queryProviceByCode(querywaybillEntity.getDeliveryCustomerProvCode());
						// 发货地址的省id
						crmReturnedGoodsDto.setSendProvinceId(querywaybillEntity.getDeliveryCustomerProvCode());
						// 发货地址的省名称
						crmReturnedGoodsDto.setSendProvince(queryProviceByName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getDeliveryCustomerCityCode())){
						queryCityByCodeName = onlineAddressDao.queryCityByCode(querywaybillEntity.getDeliveryCustomerCityCode());
						// 发货地址的市id
						crmReturnedGoodsDto.setSendCityId(querywaybillEntity.getDeliveryCustomerCityCode());
						// 发货地址的市名称
						crmReturnedGoodsDto.setSendCity(queryCityByCodeName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getDeliveryCustomerDistCode())){
						queryCountyByName = onlineAddressDao.queryCountyByCode(querywaybillEntity.getDeliveryCustomerDistCode());
						// 发货地址的区id
						crmReturnedGoodsDto.setSendAreaId(querywaybillEntity.getDeliveryCustomerDistCode());
						// 发货地址的区名称
						crmReturnedGoodsDto.setSendArea(queryCountyByName);
					}
					StringBuilder sbAddress = new StringBuilder();
					
					// 发货地址：省-市-区-详细地址
					// 省
					sbAddress.append(queryProviceByName).append("-");
					// 市
					sbAddress.append(queryCityByCodeName).append("-");
					// 区
					sbAddress.append(queryCountyByName).append("-");
					// 详细地址
					sbAddress.append(querywaybillEntity.getDeliveryCustomerAddress());
					
					crmReturnedGoodsDto.setAddressSend(sbAddress.toString());
					
					// 获取运单收货地址的省-市-区
					if(StringUtil.isNotEmpty(querywaybillEntity.getReceiveCustomerProvCode())){
						queryProviceName = onlineAddressDao.queryProviceByCode(querywaybillEntity.getReceiveCustomerProvCode());
						// 收货地址的省id
						crmReturnedGoodsDto.setReceiveProvinceId(querywaybillEntity.getReceiveCustomerProvCode());
						// 收货地址的省名称
						crmReturnedGoodsDto.setReceiveProvince(queryProviceName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getReceiveCustomerCityCode())){
						queryCityName = onlineAddressDao.queryCityByCode(querywaybillEntity.getReceiveCustomerCityCode());
						// 收货地址的市id
						crmReturnedGoodsDto.setReceiveCityId(querywaybillEntity.getReceiveCustomerCityCode());
						// 收货地址的市名称
						crmReturnedGoodsDto.setReceiveCity(queryCityName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getReceiveCustomerDistCode())){
						queryCountyName = onlineAddressDao.queryCountyByCode(querywaybillEntity.getReceiveCustomerDistCode());
						// 收货地址的区id
						crmReturnedGoodsDto.setReceiveAreaId(querywaybillEntity.getReceiveCustomerDistCode());
						// 收货地址的区名称
						crmReturnedGoodsDto.setReceiveArea(queryCountyName);
					}
					sbAddress.delete(0, sbAddress.length());
					// 收货地址（省-市-区-详细地址）
					// 省
					sbAddress.append(queryProviceName).append("-");
					// 市
					sbAddress.append(queryCityName).append("-");
					// 区
					sbAddress.append(queryCountyName).append("-");
					// 详细地址
					sbAddress.append(querywaybillEntity.getReceiveCustomerAddress());
					
					crmReturnedGoodsDto.setAddressReceive(sbAddress.toString());
					sbAddress = null;
				}
				//当返货类型为“客户拒收“时，代收货款取消，默认为0；保价默认为原单保价。收货人姓名、联系方式、地址默认为原单发货人地址。 
//				if(bean.getReturnType().equals(WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE)){
				else{
					crmReturnedGoodsDto.setOriWaybill(bean.getWaybillNo());//原单号
					crmReturnedGoodsDto.setReturnReason(bean.getReturnReason());//返货原因
					crmReturnedGoodsDto.setReturnType(bean.getReturnType());//返货类型
					crmReturnedGoodsDto.setReportContent(bean.getApplicationReason());//申请事由
					// 设置id
					crmReturnedGoodsDto.setFossId(UUIDUtils.getUUID());
					if(StringUtils.isNotBlank(querywaybillEntity.getDeliveryCustomerMobilephone())){
						crmReturnedGoodsDto.setPhoneSend(querywaybillEntity.getDeliveryCustomerMobilephone());//发货人联系方式
					}else{
						crmReturnedGoodsDto.setPhoneSend(querywaybillEntity.getDeliveryCustomerPhone());//发货人联系方式
					}
					// 受理状态默认为未受理
					crmReturnedGoodsDto.setReturnStatus(WaybillConstants.ACCEPTSTATUS_REFUSED);
					
					crmReturnedGoodsDto.setMoneyReceive(BigDecimal.ZERO);//代收货款取消，默认为0
					if(StringUtils.isNotBlank(querywaybillEntity.getDeliveryCustomerMobilephone())){
						crmReturnedGoodsDto.setPhoneReceive(querywaybillEntity.getDeliveryCustomerMobilephone());//原发货人联系方式
					}else{
						crmReturnedGoodsDto.setPhoneReceive(querywaybillEntity.getDeliveryCustomerPhone());//原发货人手机
					}
					
					crmReturnedGoodsDto.setTimeReport(new Date());//上报时间
					
					// 设置订单来源
					crmReturnedGoodsDto.setOrderSource(querywaybillEntity.getOrderChannel());
					// 设置发货人姓名
					crmReturnedGoodsDto.setManSend(querywaybillEntity.getDeliveryCustomerName());
					// 设置发货人编码
					crmReturnedGoodsDto.setManSendCode(querywaybillEntity.getDeliveryCustomerCode());
					// 设置收货人姓名
					crmReturnedGoodsDto.setManReceive(querywaybillEntity.getReceiveCustomerContact());
					// 设置收货人编码
					crmReturnedGoodsDto.setManReceiveCode(querywaybillEntity.getReceiveCustomerCode());
					// 设置id
					crmReturnedGoodsDto.setFossId(UUIDUtils.getUUID());
					// 设置出发部门
					OrgAdministrativeInfoEntity deptStart = waybillManagerService.queryOrgInfoByCode(querywaybillEntity.getReceiveOrgCode());	
					if (null != deptStart) {
						crmReturnedGoodsDto.setDeptStart(deptStart.getName());
					}
					// 设置到达部门
					OrgAdministrativeInfoEntity deptArrive = waybillManagerService.queryOrgInfoByCode(querywaybillEntity.getCustomerPickupOrgCode());
					if (null != deptArrive) {
						crmReturnedGoodsDto.setDeptArrive(deptArrive.getName());
					}
					// 设置代收贷款
					if(null != bean.getCodAmount()){
						crmReturnedGoodsDto.setMoneyReceive(bean.getCodAmount());
					}else{
						crmReturnedGoodsDto.setMoneyReceive(querywaybillEntity.getCodAmount());
					}
					// 设置保价
					if(null != bean.getInsuranceAmount()){
						crmReturnedGoodsDto.setMoneyInsured(bean.getInsuranceAmount());
					}else{
						crmReturnedGoodsDto.setMoneyInsured(querywaybillEntity.getInsuranceAmount());
					}
					// 获取运单发货地址的省-市-区
					if(StringUtil.isNotEmpty(querywaybillEntity.getDeliveryCustomerProvCode())){
						queryProviceByName = onlineAddressDao.queryProviceByCode(querywaybillEntity.getDeliveryCustomerProvCode());
						// 发货地址的省id
						crmReturnedGoodsDto.setSendProvinceId(querywaybillEntity.getDeliveryCustomerProvCode());
						// 发货地址的省名称
						crmReturnedGoodsDto.setSendProvince(queryProviceByName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getDeliveryCustomerCityCode())){
						queryCityByCodeName = onlineAddressDao.queryCityByCode(querywaybillEntity.getDeliveryCustomerCityCode());
						// 发货地址的市id
						crmReturnedGoodsDto.setSendCityId(querywaybillEntity.getDeliveryCustomerCityCode());
						// 发货地址的市名称
						crmReturnedGoodsDto.setSendCity(queryCityByCodeName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getDeliveryCustomerDistCode())){
						queryCountyByName = onlineAddressDao.queryCountyByCode(querywaybillEntity.getDeliveryCustomerDistCode());
						// 发货地址的区id
						crmReturnedGoodsDto.setSendAreaId(querywaybillEntity.getDeliveryCustomerDistCode());
						// 发货地址的区名称
						crmReturnedGoodsDto.setSendArea(queryCountyByName);
					}
					StringBuilder sbAddress = new StringBuilder();
					
					// 发货地址：省-市-区-详细地址
					// 省
					sbAddress.append(queryProviceByName).append("-");
					// 市
					sbAddress.append(queryCityByCodeName).append("-");
					// 区
					sbAddress.append(queryCountyByName).append("-");
					// 详细地址
					sbAddress.append(querywaybillEntity.getDeliveryCustomerAddress());
					
					crmReturnedGoodsDto.setAddressSend(sbAddress.toString());
					// 获取运单收货地址的省-市-区
					// 获取运单收货地址的省-市-区
					if(StringUtil.isNotEmpty(querywaybillEntity.getReceiveCustomerProvCode())){
						queryProviceName = onlineAddressDao.queryProviceByCode(querywaybillEntity.getReceiveCustomerProvCode());
						// 收货地址的省id
						crmReturnedGoodsDto.setReceiveProvinceId(querywaybillEntity.getReceiveCustomerProvCode());
						// 收货地址的省名称
						crmReturnedGoodsDto.setReceiveProvince(queryProviceName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getReceiveCustomerCityCode())){
						queryCityName = onlineAddressDao.queryCityByCode(querywaybillEntity.getReceiveCustomerCityCode());
						// 收货地址的市id
						crmReturnedGoodsDto.setReceiveCityId(querywaybillEntity.getReceiveCustomerCityCode());
						// 收货地址的市名称
						crmReturnedGoodsDto.setReceiveCity(queryCityName);
					}
					if(StringUtil.isNotEmpty(querywaybillEntity.getReceiveCustomerDistCode())){
						queryCountyName = onlineAddressDao.queryCountyByCode(querywaybillEntity.getReceiveCustomerDistCode());
						// 收货地址的区id
						crmReturnedGoodsDto.setReceiveAreaId(querywaybillEntity.getReceiveCustomerDistCode());
						// 收货地址的区名称
						crmReturnedGoodsDto.setReceiveArea(queryCountyName);
					}
					sbAddress.delete(0, sbAddress.length());
					// 收货地址（省-市-区-详细地址）
					// 省
					sbAddress.append(queryProviceName).append("-");
					// 市
					sbAddress.append(queryCityName).append("-");
					// 区
					sbAddress.append(queryCountyName).append("-");
					// 详细地址
					sbAddress.append(querywaybillEntity.getReceiveCustomerAddress());
					
					crmReturnedGoodsDto.setAddressReceive(sbAddress.toString());
					sbAddress = null;
				}
				return this.submitReturnGoodsApplyToCRM(crmReturnedGoodsDto);
			}
			throw new TfrBusinessException("原单信息为空");
	}

	/**
	 * CRM处理完成返货工单
	 */
	@Override
	public List<CrmReturnedGoodsDto> disposeReturnedDworkOrder(String userCode) {
		Date date = new Date();//当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");//格式化对象
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);//设置当前日期
		calendar.add(Calendar.MONTH, -1);//月份减一
		String format = sdf.format(calendar.getTime());
		Date parse = new Date();//当前日期
		List<CrmReturnedGoodsDto> rs = new ArrayList<CrmReturnedGoodsDto>();
		try {
			parse = sdf.parse(format);
			//封装查询条件
			CrmReturnedGoodsDto crmReturnedGoodsDto = new CrmReturnedGoodsDto();
			crmReturnedGoodsDto.setCreateUserCode(userCode);//上报人员工号
			//查询当前时间一个月以内的返货工单
			crmReturnedGoodsDto.setCreateStartTime(parse);//返货开单创建时间
			crmReturnedGoodsDto.setCreateEndTime(date);//返货开单结束时间
			
			ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
			BeanUtils.copyProperties(crmReturnedGoodsDto, condition);
			// 查询所有当前快递上报的工单信息
			List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService
					.queryReturnGoodsRequestEntityByCondition(condition);
			CrmReturnedGoodsDto temp = null;
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					temp = new CrmReturnedGoodsDto();
					BeanUtils.copyProperties(list.get(i), temp);
					rs.add(temp);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new TfrBusinessException("查询返货管理数据失败");
		}
		return rs;
	}
	public boolean submitReturnGoodsApplyToCRM(
			CrmReturnedGoodsDto crmReturnedGoodsDto) {
		// 设置地址
		String address = PropertiesUtil
				.getKeyValue(WaybillConstants.EXP_RETURNGOODS_ADDRESS);
		WebClient webClient = WebClient.create(address);
		LOGGER.info("CRM接口地址："+address);
		// 设置请求的类型
		webClient.type(MediaType.APPLICATION_JSON);
		webClient.accept(MediaType.APPLICATION_JSON);
		// 发送的内容
		String body = com.alibaba.fastjson.JSONObject.toJSONString(crmReturnedGoodsDto);
		String content = "{\"returnBill\":" + body + "}";
		// 发送返货申请
		javax.ws.rs.core.Response response = webClient.post(content);
		try {
			// 序列化返回的结果
			String result = IOUtils
					.toString((InputStream) response.getEntity());
			// 取出状态码
			ExpReturnGoodsApplyResultDto returnStatus = com.alibaba.fastjson.JSONObject.parseObject(
					result, ExpReturnGoodsApplyResultDto.class);
			int status = returnStatus.getStatusCode();
			// 如果是1 则创建成功
			if (1 == status) {
				//向FOSS综合插入
				ReturnGoodsRequestEntity model = new ReturnGoodsRequestEntity();
				BeanUtils.copyProperties(crmReturnedGoodsDto, model);
				try {
					returnGoodsRequestEntityService.addReturnGoodsRequestEntity(model);					
				} catch (Exception e) {
					e.printStackTrace();
					throw new TfrBusinessException("向综合保存数据失败");
				}
				return true;	
			}			
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.info("处理crm回传数据失败CRM接口地址："+e.getMessage());
			throw new TfrBusinessException("处理crm回传数据失败");
		}
		LOGGER.info("向crm发送请求失败");
		throw new TfrBusinessException("向crm发送请求失败");
	}
	
	
	private IWaybillSignResultService waybillSignResultService;
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	
	private IArriveSheetManngerService arriveSheetManngerService;
	
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	
	/**
	 * 校验返货申请运单号和返货方式
	 * @author foss-206860
	 * */
	@Override
	public void validateReturnGoodsWaybillNo(String oriWayBillNo,
			String returnMode) {
		if(StringUtil.isNotEmpty(oriWayBillNo)){
			//通过运单号校验是否已经申请过返单且没有作废或者是中止
			String validateReturn = validateReturn(oriWayBillNo);
			//返回的返货类型不为空，说明已经申请过工单或者是已经开过返货
			if(StringUtil.isNotEmpty(validateReturn)){
				//该单号已经申请上报返货，不能再次申请--适用于所有快递运单
				throw new PdaInterfaceException("该单号已经申请上报返货，不能再次申请");
			}
			//根据运单号查询子母件关系表
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("waybillNo", oriWayBillNo);
			params.put("active", FossConstants.YES);
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(params);
			if(twoInOneWaybillDto != null){
				
				/**
				 * 签收的运单不能进行转寄退回件上报
				 */
				
				WaybillSignResultEntity result = new WaybillSignResultEntity();
				result.setWaybillNo(oriWayBillNo);
				result.setActive(FossConstants.ACTIVE);
				result=waybillSignResultService.queryWaybillSignResultByWaybillNo(result);
				if(result!=null){
					throw new PdaInterfaceException("该票货物已签收，不允许上报工单!");
				}
				
				//当货物在派送中时不允许进行返货申请
				//转寄更改和返货工单上报在派送中的时候不允许上报工单--FOSS20160116
				List<String> waybillNos= twoInOneWaybillDto.getWaybillNoList();
				if(CollectionUtils.isEmpty(waybillNos)){
					waybillNos=new ArrayList<String>();	
				}
				waybillNos.add(oriWayBillNo);
				if(CollectionUtils.isNotEmpty(waybillNos)){
					for(String waybillNo:waybillNos){
						ArriveSheetEntity entity=new ArriveSheetEntity();
						entity.setWaybillNo(waybillNo);
						entity.setActive(FossConstants.ACTIVE);
						entity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
						entity.setDestroyed(FossConstants.NO);
						List<ArriveSheetEntity> arriveSheetEntitys=
								 arriveSheetManngerService.queryArriveSheetByWaybillNo(entity);
						if(CollectionUtils.isNotEmpty(arriveSheetEntitys)){
							throw new PdaInterfaceException("有货在派送中不允许返货申请！");
						}
					}
				}
				
				
				//不是子母件
				if(FossConstants.NO.equals(twoInOneWaybillDto.getIsTwoInOne())){
					if(StringUtils.isNotEmpty(returnMode)){
						throw new PdaInterfaceException("抱歉，非子母件上报返货方式只能为空！");
					}
					/*//当返货方式为按件返时，提示"抱歉，按件返只允许子件和母件单号上报"
					if(returnMode != null && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(returnMode)){
						//抱歉，按件返只允许子件和母件单号上报
						throw new PdaInterfaceException("抱歉，按件返只允许子件和母件单号上报");
					}*/
					//1、存在未受理的 2、在派送中的、3、已签收的
					
					//是子母件
				}else{
					if(StringUtils.isEmpty(returnMode)){
						throw new PdaInterfaceException("抱歉，子母件上报返货方式不能为空！");
					}
					//母件单号
					String mainWaybillNo = twoInOneWaybillDto.getMainWaybillNo();
					//子件单号
					List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
					//判断子母单应收单是否结清
					boolean existRepayment = false;
					if(StringUtil.isNotEmpty(mainWaybillNo)){
						existRepayment = repaymentService.isExistRepayment(mainWaybillNo);
					}
					//判断是否存在丢货
					boolean loseGroupFlag = validateLoseGroup(twoInOneWaybillDto);
					if(returnMode != null && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnMode)){
						//-------------------按票返的校验
						//只有母件允许上报按票返
						if(!oriWayBillNo.equals(mainWaybillNo)){
							//抱歉，子母件上报按票返，只允许母件上报
							throw new PdaInterfaceException("抱歉，子母件上报按票返，只允许母件上报");
						}
						//应收单已结清，不允许按票返
						if(existRepayment){
							//母单应收单已结清，不可按票返
							throw new PdaInterfaceException("母单应收单已结清，不可按票返");
						}
						//判断是否存在母件其子件已经操作了按件返
						boolean validateWaybillReturnPiece = validateWaybillReturnPiece(twoInOneWaybillDto);
						if(validateWaybillReturnPiece){
							//已经存在子母件操作了按件返，不可操作按票返
							throw new PdaInterfaceException("已经存在子母件操作了按件返，不可操作按票返");
						}
						//判断所有子母件是否在到达部门库存（validateStock为true表示不在到达部门库存）
						boolean validateStock = validateStock(mainWaybillNo);
						boolean flag=validateLoseGroup(mainWaybillNo);
						if(validateStock && !flag){
							//子母件不存在到达部门库存，不允许返货
							throw new PdaInterfaceException("母件不存在到达部门库存，不允许返货");
						}
						//查询子件是否在库存
						if(CollectionUtils.isNotEmpty(waybillNoList)){
							for (int i = 0; i < waybillNoList.size(); i++) {
								if(StringUtil.isNotBlank(waybillNoList.get(i))){
									//判断是否在库存
									boolean stock = validateStock(waybillNoList.get(i));
									//若不在库存且没有丢货则提示“子母件不存在到达部门库存，不允许返货”
									if(stock && !loseGroupFlag){
										//子母件不存在到达部门库存，不允许返货
										throw new PdaInterfaceException("子件不存在到达部门库存，不允许返货");
									}
								}
							}
						}
						//判断用母件单号上报工单时，母件是否丢货找到，且子件单号有无已经返货开单成功，
						//如果是此种情况则不允许丢货找到的母件单号上报按票返dongsiwei
						String flagParent=validateLoseFound(oriWayBillNo);//母件丢货找到
						boolean flagParents= validateWaybillReturnCARGO(twoInOneWaybillDto);//子母件内子件有无按票返开单
						if(FossConstants.YES.equals(flagParent)){
							if(flagParents){
								throw new PdaInterfaceException("母件丢货找到上报工单，子件已按票返返货开单成功，母件不可以按票返！");
							}
						}
					}else{
						//--------------------按件返的校验
						//判断该单号是够是丢货找到--待写
						if(FossConstants.NO.equals(validateLoseFound(oriWayBillNo))){
							//没有丢货的情况下，应收单结清才可以按件返（没有丢货没有结清应收单不让按件返）
							if(!existRepayment){
								//母单应收单未结清，不可按件返
								throw new PdaInterfaceException("母单应收单未结清，不可按件返");
							}
							//判断子件其母件是否已经操作了按票返（丢货找到除外）
							//查询母件的返货方式
							String mainWaybillNoReturnMode = validateReturn(mainWaybillNo);
							//母件已经操作了按票返，则其他子母件不允许按件返
							if(StringUtil.isNotEmpty(mainWaybillNoReturnMode)
									&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(mainWaybillNoReturnMode)){
								//该单母件已经按票返，不能再次申请按件返
								throw new PdaInterfaceException("该单母件已经按票返，不能再次申请按件返");
							}
						}
						//校验当前返货运单的库存
						boolean stock = validateStock(oriWayBillNo);
						//若不在库存且没有丢货则提示“子母件不存在到达部门库存，不允许返货”
						if(stock){
							//子母件不存在到达部门库存，不允许返货
							throw new PdaInterfaceException("子母件不存在到达部门库存，不允许返货");
						}
					}
				}
			}
		}
	}
	
	/**
	 * 校验子母件内子件有无按票返货
	 * @param twoInOneWaybillDto
	 * @return
	 */
	private boolean validateWaybillReturnCARGO(
			TwoInOneWaybillDto twoInOneWaybillDto) {
		boolean pieceFlag = false;
		//子单号集合
		List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
//		String validateReturn = validateReturn(mainWaybillNo);
//		if(StringUtil.isNotEmpty(validateReturn)
//				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(validateReturn)){
//			return true;
//		}
		if(CollectionUtils.isNotEmpty(waybillNoList)){
			for (int i = 0; i < waybillNoList.size(); i++) {
				String validateReturn2 = validateReturnParent(waybillNoList.get(i));
				if(StringUtil.isNotEmpty(validateReturn2)
						&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(validateReturn2)){
					return true;
				}
			}
		}
		return pieceFlag;
	}
	
	/**
	 * 校验运单是否已经返货开单成功
	 * @author Foss-218459 dongsiwei
	 */
	private String validateReturnParent(String oriWayBillNo) {
		//不管是不是子母件，先校验是否已经申请过返货和已经返货开单，已开过提示"该单号已经申请上报返货，不能再次申请"
		String returnMode = "";
		//检验是否已经返货开单（中止和作废的不算）
		// 根据原单号查询返货开单信息
		if(StringUtil.isNotEmpty(oriWayBillNo)){
			//查询该单号是否已经开过返货
			List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo = waybillExpressService
					.queryWaybillByOriginalWaybillNo(oriWayBillNo);
			if(CollectionUtils.isNotEmpty(queryWaybillByOriginalWaybillNo)){
				//开过返货，返回返货类型
				return queryWaybillByOriginalWaybillNo.get(0).getReturnType();
			}
		}
		return returnMode;
	}
	
	/**
	 * 通过运单号校验是否已经申请过返单且没有作废或者是中止
	 * @param oriWayBillNo 原始运单号
	 * @return 返货方式
	 * @author foss-206860
	 * */
	private String validateReturn(String oriWayBillNo) {
		//不管是不是子母件，先校验是否已经申请过返货和已经返货开单，已开过提示"该单号已经申请上报返货，不能再次申请"
		String returnMode = "";
		//检验是否已经返货开单（中止和作废的不算）
		// 根据原单号查询返货开单信息
		if(StringUtil.isNotEmpty(oriWayBillNo)){
			//查询该单号是否已经开过返货
			List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo = waybillExpressService
					.queryWaybillByOriginalWaybillNo(oriWayBillNo);
			if(CollectionUtils.isNotEmpty(queryWaybillByOriginalWaybillNo)){
				//开过返货，返回返货类型
				return queryWaybillByOriginalWaybillNo.get(0).getReturnType();
				//没有开过返货，查询是否申请过返货工单
			}else{
				//查询综合的返货工单
				ReturnGoodsRequestEntity returnGoodsRequestEntity = new ReturnGoodsRequestEntity();
				returnGoodsRequestEntity.setOriWaybill(oriWayBillNo);
				List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByCondition(returnGoodsRequestEntity);
				//取最后一条上报的工单数据，如果状态为待受理中，则不用许再次上报
				ReturnGoodsRequestEntity entity=null;
				int index=0;
				if(CollectionUtils.isNotEmpty(list)){
					for(int i = 1;i<list.size();i++){
						if(list.get(index).getTimeReport().getTime()<
								list.get(i).getTimeReport().getTime()){
							index = i;
						}
					}
					entity = list.get(index);
					if(entity!=null&&WaybillConstants.ACCEPTSTATUS_REFUSED.equals(entity.getReturnStatus())){
						return entity.getReturnType();
					} 
				}
			}
		}
		return returnMode;
	}
	
	/**
	 *判断子母单号是否已经操作了按件返 
	 *@param twoInOneWaybillDto 
	 *@author foss-206860
	 */
	private boolean validateWaybillReturnPiece(
			TwoInOneWaybillDto twoInOneWaybillDto) {
		boolean pieceFlag = false;
		//母单号
		String mainWaybillNo = twoInOneWaybillDto.getMainWaybillNo();
		//子单号集合
		List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
		String validateReturn = validateReturn(mainWaybillNo);
		if(StringUtil.isNotEmpty(validateReturn)
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(validateReturn)){
			return true;
		}
		if(CollectionUtils.isNotEmpty(waybillNoList)){
			for (int i = 0; i < waybillNoList.size(); i++) {
				String validateReturn2 = validateReturn(waybillNoList.get(i));
				if(StringUtil.isNotEmpty(validateReturn2)
						&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(validateReturn2)){
					return true;
				}
			}
		}
		return pieceFlag;
	}
	
	/**
	 * 是否在到达部门库存
	 * @param oriWayBillNo
	 * @author foss-206860
	 * */
	private boolean validateStock(String oriWayBillNo){
		//若原单号存在落地配外发交接单（作废和有效），则返货开单时不校验该货物是否在到达部门库存
		boolean isWaiFa = handOverBillService
				.queryBeLdpHandOveredByWaybillNo(oriWayBillNo);
		if (!isWaiFa) {
			boolean stock = true;
			if(StringUtil.isNotEmpty(oriWayBillNo)){
				WaybillEntity entity = waybillManagerService.queryWaybillBasicByNo(oriWayBillNo);
				// 单号不在到达部门库存中不能返货开单
				WaybillStockEntity waybillStockEntity =new WaybillStockEntity();
				waybillStockEntity.setWaybillNO(oriWayBillNo);
				List<StockEntity> stockStatus = stockService.queryStockByWaybillNo(waybillStockEntity);
				if (CollectionUtils.isNotEmpty(stockStatus)) {
					String destOrgCode = entity.getCustomerPickupOrgCode();
					// 查询营业部的部门信息
					SaleDepartmentEntity dept = saleDepartmentService
							.querySaleDepartmentByCode(destOrgCode);
					// 是不是驻地营业部
					if (dept != null
							&& dept.getStation().equals(FossConstants.ACTIVE)) {
						// 驻地营业部对应外场
						String transferCenter = dept.getTransferCenter();
						for (StockEntity stockStatu : stockStatus) {
							String orgCode = stockStatu.getOrgCode();
							if (transferCenter.equals(orgCode)) {
								stock = false;
							}
						}
					} else {
						for (StockEntity stockStatu : stockStatus) {
							String orgCode = stockStatu.getOrgCode();
							if (destOrgCode.equals(orgCode)) {
								stock = false;
							}
						}
					}
					
				}
			}
			return stock;
		}
		return false;
	}
	
	/**
	 * 是否存在丢货
	 * @param twoInOneWaybillDto
	 * @author foss-206860
	 * */
	private boolean validateLoseGroup(TwoInOneWaybillDto twoInOneWaybillDto){
		Boolean loseGroupFlag = false;
		//查询所有子件
		List<String> noList = twoInOneWaybillDto.getWaybillNoList();
		for (int i = 0; i < noList.size(); i++) {
			if(StringUtil.isNotBlank(noList.get(i))){
				//判断子件是否丢货
				QmsYchExceptionReportEntity loseGroup = qmsYchService.isLoseGroup(noList.get(i));
				//存在丢货组
				if(loseGroup != null && loseGroup.getIsSuccess() == 1 && "Y".equals(loseGroup.getIsInLoseGroup())){
					loseGroupFlag = true;
					return loseGroupFlag;
				}
			}
		}
		//查询母件
		String mainWaybillNo = twoInOneWaybillDto.getMainWaybillNo();
		if(StringUtil.isNotEmpty(mainWaybillNo)){
			//判断子件是否丢货
			QmsYchExceptionReportEntity loseGroup = qmsYchService.isLoseGroup(mainWaybillNo);
			//存在丢货组
			if(loseGroup != null && loseGroup.getIsSuccess() == 1 && "Y".equals(loseGroup.getIsInLoseGroup())){
				loseGroupFlag = true;
				return loseGroupFlag;
			}
		}
		return loseGroupFlag;
	}
	
	
	//是否存在丢货
	private boolean validateLoseGroup(String waybillNo){
		Boolean loseGroupFlag = false;
		if(StringUtil.isNotEmpty(waybillNo)){
			//判断子件是否丢货
			QmsYchExceptionReportEntity loseGroup = qmsYchService.isLoseGroup(waybillNo);
			//存在丢货组
			if(loseGroup != null && loseGroup.getIsSuccess() == 1 && "Y".equals(loseGroup.getIsInLoseGroup())){
				loseGroupFlag = true;
				return loseGroupFlag;
			}
		}
		return loseGroupFlag;
	}
	
	/**
	 * 是否丢货找到
	 * @param oriWayBillNo
	 * @author foss-206860
	 * */
	private String validateLoseFound(String oriWayBillNo){
		String serialNo = WaybillConstants.SERIAL_NUMBER;
		return stockService.queryLostFindGoods(oriWayBillNo,serialNo);
	}
	
	public void addEwaybillRelateEntityData(String taskId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("taskId", taskId);
		params.put("jodId", WaybillConstants.UNKNOWN);
	    List<EwaybillRelateEntity> ewaybillRelateList=waybillRelateDetailEntityService.queryEwaybillRelateByTaskId(params);
	    if(CollectionUtils.isEmpty(ewaybillRelateList)){
		if(StringUtil.isNotEmpty(taskId)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("active", FossConstants.YES);
			map.put("scanType", "SCAN");
			map.put("taskType", WaybillConstants.STATUS_PICKUP);
			map.put("taskId", taskId);
			List<EcomWaybillRelateEntity> ecomWaybillRelateList=waybillRelateDetailEntityService.queryEcomWaybillRelateListByPdaScanTaskId(map);
			if(CollectionUtils.isNotEmpty(ecomWaybillRelateList)){
			for(EcomWaybillRelateEntity ecomWaybillRelateEntity:ecomWaybillRelateList){
			EwaybillRelateEntity ewaybillRelateEntity=new EwaybillRelateEntity();
			ewaybillRelateEntity.setId(UUIDUtils.getUUID());
			ewaybillRelateEntity.setJobId(WaybillConstants.UNKNOWN);
			ewaybillRelateEntity.setParentOrderNo(ecomWaybillRelateEntity.getOrignalOrderNo());
			ewaybillRelateEntity.setTaskId(taskId);
			ewaybillRelateEntity.setFailReason(FossConstants.NO);
			ewaybillRelateEntity.setCreateTime(new Date());
			ewaybillRelateEntity.setModifyTime(new Date());
			waybillRelateDetailEntityService.insertEwaybillRelateSelective(ewaybillRelateEntity);
			}
			}
		}}
	}
	//*******以下代码为   零担分单逻辑优化   需求编号：DN201703030017    开发时间2017年3月7日20:14:45  by：352676 *******//
	@Override
	public ResultDto submitWaybillPictureByPDA(
			WaybillPicturePdaDto waybillPicturePdaDto) {
		if (waybillPicturePdaDto == null) {
			throw new PdaInterfaceException("运单不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getWaybillNo())) {
			throw new PdaInterfaceException("运单号不能为空！");
		} else {
			//判断运单号是否已存在图片开单表
			WaybillPictureEntity waybillPictureEntity1  = waybillPictureDao.queryWaybillPictureByWaybillNo(waybillPicturePdaDto.getWaybillNo());
			//排除本身之外，如果运单号重复则不能提交
			if (waybillPictureEntity1 != null && !WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(waybillPictureEntity1.getPendgingType())) {
				throw new PdaInterfaceException("运单号已存在！上传工号:" + waybillPictureEntity1.getDriverCode());
			}
			//判断运单号是否已录入或者已开单
			if (waybillManagerService.isWayBillExsits(waybillPicturePdaDto.getWaybillNo()) || waybillManagerService.isWayBillPendingExsits(waybillPicturePdaDto.getWaybillNo())) {
				throw new PdaInterfaceException("此运单号已经开单或暂存！");
			}
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getBillOrgCode())) {
			throw new PdaInterfaceException("司机所在车队部门不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getDriverCode())) {
			throw new PdaInterfaceException("司机编号不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getBaiDuId())) {
			throw new PdaInterfaceException("百度ID不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getReceiveOrgCode())) {
			throw new PdaInterfaceException("收货部门不能为空！");
		}
		if (StringUtils.isEmpty(waybillPicturePdaDto.getFilePath())) {
			throw new PdaInterfaceException("图片路劲不能为空！");
		}
		
		// 查找对应的顶级车队
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(waybillPicturePdaDto.getBillOrgCode());
		if(topFleet == null){
			LOGGER.info("图片录入：通过司机所在车队部门编码未找到对应的顶级车队,部门编码："+waybillPicturePdaDto.getBillOrgCode());
			throw new PdaInterfaceException("通过司机所在车队部门编码未找到对应的顶级车队");
		}
		// 司机所在车队部门
		MotorcadeEntity motorcadeEntity = motorcadeService.queryMotorcadeByCode(topFleet.getCode());
		// 车队对应的集中开单组编码
		String billOrgCode;
		if (motorcadeEntity != null) {
			if (StringUtils.isNotEmpty(motorcadeEntity.getServeBillTerm())) {
				billOrgCode = motorcadeEntity.getServeBillTerm();
			} else {
				LOGGER.info("图片录入：通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
				throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
			}
		} else {
			LOGGER.info("图片录入：通过司机所在车队部门编码未在车辆司机组织表记录中找到有效的集中开单组部门编码");
			throw new PdaInterfaceException("通过司机所在车队部门编码未在车辆司机组织表中找到有效记录");
		}
		WaybillPictureEntity waybillPictureEntity = new WaybillPictureEntity();
		BeanUtils.copyProperties(waybillPicturePdaDto, waybillPictureEntity);
		//设置所属机构
		/**目前不设置所属机构*/
		//waybillPictureEntity.setBelongOrgCode(billOrgCode);
		//设置本属开单组
		waybillPictureEntity.setLocalBillGroupCode(billOrgCode);
		//分配运单
		LOGGER.info("运单本属开单组:"+billOrgCode);
		
		//标注【现金】和【空运】的图片，由本地开单组开单
		//是否现金
		if(StringUtil.equals(FossConstants.YES, waybillPictureEntity.getCashPayFlag())){
			//如果是现金的话有本地开单组开单
			waybillPictureEntity.setLocal(FossConstants.YES);
			waybillPictureEntity.setBelongOrgCode(billOrgCode);
		}
		//是否空运    字段
		if(StringUtil.equals(FossConstants.YES, waybillPicturePdaDto.getSpecialCustomer())){
			//如果是空运的话由本地开单组开单
			waybillPictureEntity.setLocal(FossConstants.YES);
			waybillPictureEntity.setBelongOrgCode(billOrgCode);
		}
		
		submitWaybillPictureByPDA(waybillPictureEntity);
		ResultDto resultDto = new ResultDto();
		resultDto.setCode(ResultDto.SUCCESS);
		return resultDto;
	}
	//*******零担分单逻辑优化     结束*****************************************************************************//
}
