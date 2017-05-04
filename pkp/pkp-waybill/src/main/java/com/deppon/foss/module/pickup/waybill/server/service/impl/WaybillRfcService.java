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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillRfcService.java
 * 
 * FILE NAME        	: WaybillRfcService.java
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.file.FileInfo;
import com.deppon.foss.framework.server.components.file.FileManager;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Decoder;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.server.service.IToDoMsgService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IValidateForCUBCService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CanChangeForCUBCDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodChangeDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPrintInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcProofDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISyncCUBCLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IinstallationService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.ChangeNodeDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgAdministrativeInfoDto;
import com.deppon.foss.module.pickup.waybill.api.shared.exception.AdjustPlanException;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillRfcBatchChangeDao;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.TradeDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IGrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.CUBCLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcBatchChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeChargeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyWriteOffStatus;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAWayBillRfcDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaReceiveGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TransportRecordDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.UploadVoucherDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNosDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLabeledGoodStockDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcExceptionType;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 更改单服务类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-25 上午10:35:01
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-25 上午10:35:01
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
 * 
 */
public class WaybillRfcService implements IWaybillRfcService {
	
	/**
	 * 日志
	 */
	protected final static Logger LOG = LoggerFactory.getLogger(WaybillRfcService.class.getName());
	
	/**
	 * 运单更改单打印记录表
	 */
	private IPrintInfoDao printInfoDao;
	
	private IWaybillExpressDao waybillExpressDao;
	/**
	 * 结算运单开单服务
	 * 提供结算运单开单服务接口
	 * 
	 * 
	 * 业务规则：
	 * 一、代收货款
	 * 1、	代收货款金额处于代收货款金额范围之内
	 * 2、	如果代收货款类型为“即日退或三日退”，则收款人且开户行信息不能为空
	 * 3、	如果代收货款类型为“即日退”，
	 * 则其开户银行必须在即日退所属银行范围内
	 * （即日退所属银行范围已经提交给综合管理做基础资料了）
	 * 4、	如果代收货款类型为“即日退”，则代收货款的退款账号类型不能为“对公帐号”
	 * 5、 	应付单为有效版本且非红单
	 * 6、	应付单单据类型为“应付代收货款”
	 * 7、	应付单的来源单据编号等于传入的运单号
	 */
	private IWaybillPickupService waybillPickupService;

	/**
	 * 接送货运单服务
	 * 提供接送货运单服务接口
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 更改单DAO
	 * 提供更改单持久化接口
	 */
	private IWaybillRfcDao waybillRfcDao;
	
	/**
	 * 异步代办DAO
	 * 提供更改单持久化接口
	 */
	private IPendingTodoDao pendingTodoDao;

	/**
	 * 快递服务
	 */
	private IWaybillExpressService waybillExpressService;
	/**
	 * 内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	private IInempDiscountPlanService inempDiscountPlanService;

		/**
	 	* 子母件数据持久层
	 	*/
		private IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao;

		public void setWaybillRelateDetailEntityDao(
			IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao) {
		this.waybillRelateDetailEntityDao = waybillRelateDetailEntityDao;
		}	public void setInempDiscountPlanService(
			IInempDiscountPlanService inempDiscountPlanService) {
		this.inempDiscountPlanService = inempDiscountPlanService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	ISysConfigService pkpsysConfigService;
	
	/**
	 * CUBC灰度接口
	 */
	private IGrayScaleService grayScaleService;
	
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	
	IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * 调用综合的上报工单
	 */
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	public IPendingTodoDao getPendingTodoDao() {
		return pendingTodoDao;
	}
	
	public void setPendingTodoDao(IPendingTodoDao pendingTodoDao) {
		this.pendingTodoDao = pendingTodoDao;
	}

	public IWaybillExpressDao getWaybillExpressDao() {
		return waybillExpressDao;
	}

	
	
	public void setGrayScaleService(IGrayScaleService grayScaleService) {
		this.grayScaleService = grayScaleService;
	}
	public void setWaybillExpressDao(IWaybillExpressDao waybillExpressDao) {
		this.waybillExpressDao = waybillExpressDao;
	}

	/**
	 * 运单暂存DAO
	 * 提供运单暂存持久化接口
	 */
	private IWaybillPendingDao waybillPendingDao;

	/**
	 * 货签DAO
	 * 提供货签持久化接口
	 */
	private ILabeledGoodDao labeledGoodDao;

	/**
	 * 签收结果Service
	 * 提供查询签收结果服务接口
	 */
	private IWaybillSignResultService waybillSignResultService;

	/**
	 * 派送中
	 * 提供派送中接口
	 */
	private ILoadService loadService;

	
	public ILoadService getLoadService() {
		return loadService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
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

    public void setSyncCUBCLogService(ISyncCUBCLogService syncCUBCLogService) {
        this.syncCUBCLogService = syncCUBCLogService;
    }

	/**
	 * 计算查询走货路径Service
	 *  提供计算查询走货路径服务接口
	 *   
	 *   
	 *   业务规则：
	 * 1、新增/修改界面，若“运输性质”为精准汽运、精准卡航，
	 * “出发站”只能从行政组织（外场）基础资料中选择，
	 * “到达站”只能从行政组织（外场）基础资料中选择；
	 * 若“运输性质”为汽运偏线，“出发站”只能从行政组织（外场）基础资料中选择，
	 * “到达站”只能从偏线代理基础资料中选择；若“运输性质”为精准空运，
	 * “出发站”只能从行政组织（空运总调或外场）基础资料中选择， 
	 * “到达站”只能从空运代理网点或可空运到达的营业部信息基础资料中选择；
	 * 2、新增/修改界面，时效（小时），根据走货路径的“运输性质”，
	 * 所选“线路名称”以及“出发站”，“到达站”，由系统自动计算
	 * 3、新增/修改线路页面，走货路径可以任意选取运作到运作线路，
	 * 线路选择框需要区分汽运，空运，偏线3个tab供用户选择
	 * 4、新增/修改线路页面，“出发站”根据所选线路动态获取该线路包含线段所有的出发站，
	 * 以下拉框显示，默认选中线路的出发站。；
	 * “到达站”根据所选线路动态获取该线路包含线段所有的到达站，
	 * 以下拉框显示，默认选中线路的到达站。
	 * “时效（小时）” 根据走货路径的“运输性质”，
	 * 所选“线路名称”以及“出发站”，“到达站”，由系统自动计算
	 * 5、查询支持模糊查询，条件“出发站”支持手动输入模糊查询，
	 * 从行政组织（外场、空运总调）基础资料中选择；
	 * “到达站”从行政组织（外场）、偏线代理、空运代理网点信息基础资料中选择；
	 * “运输性质”默认为全部，包含：精准卡航，精准城运，
	 * 精准汽运（长途），精准汽运（短途），汽运偏线，精准空运
	 * 6、走货路径的第一段线路的“出发站”必须与走货路径的“出发站”一致，
	 * 走货路径的最后一段线路的“到达站”必须与走货路径的“到达站”一致，
	 * 走货路径第n段线路的“出发站”必须与走货路径第n-1段线路的“到达站”一致
	 * 7、走货路径的线路中，选择的“到达站”在线路中的站点位置必须在“出发站”的站点位置之后
	 * 8、新增/修改线路页面，“是否可以打木架”单选按钮，
	 * 是或否，默认为否，若值为“否”，单选按钮为只读状态，
	 * 不允许选择“是”；若值为“是”，单选按钮可以选择为“否”；
	 * “打木架外场”与“是否可以打木架”联动，若“是否可以打木架”值为“否”，
	 * “打木架外场”隐藏；若“是否可以打木架”值为“是”，“打木架外场”显示，
	 * 下拉框里会把拥有“可以打木架”属性的外场查询出来，
	 * 默认为第一个具有“可以打木架” 属性的外场
	 * 9、新增/修改页面，相同运输性质，
	 * 出发站和到达站之间，只能有一条默认走货路径
	 */
	private ICalculateTransportPathService calculateTransportPathService;

	/**
	 * 变更凭证DAO
	 * 提供变更凭证持久化接口
	 */
	private IWaybillRfcProofDao waybillRfcProofDao;

	/**
	 * 任务车辆Service
	 * 提供查询任务车辆服务接口
	 */
	private ITruckTaskService truckTaskService;

	/**
	 * 库存Service
	 * 提供库存操作服务接口
	 */
	private IStockService stockService;
	
	/**
	 * 库存Service
	 * @description: 交接单模块service接口
	 */
	private IHandOverBillService handOverBillService;

	/**
	 * 卸车Service
	 * 提供卸车操作服务接口
	 */
	private IUnloadTaskService unloadTaskService;

	/**
	 * 运单状态Service
	 * 提供运单状态服务接口
	 */
	private IActualFreightService actualFreightService;

	/**
	 * DPAP文件管理类
	 * 提供DPAP文件管理类服务接口
	 */
	private FileManager fileManager;

	/**
	 * 更改单审核受理Service
	 * 提供更改单审核受理服务接口
	 */
	private IWaybillRfcVarificationService waybillRfcVarificationService;

	/**
	 * 货款结清Service
	 * 提供货款结清服务接口
	 */
	private IRepaymentService repaymentService;

	/**
	 * 运单DAO
	 * 提供运单持久化接口
	 */
	private IWaybillDao waybillDao;

	/**
	 * 待办事项DAO
	 * 提供待办事项持久化接口
	 */
	private ILabeledGoodTodoDao labeledGoodTodoDao;

	/**
	 * 待办事项DAO
	 * 提供待办事项持久化接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	/**
	 * 运单冗余数据处理DAO
	 */
	private IActualFreightDao actualFreightDao;

	/**
	 * 
	 * 注入ActualFreight的Dao
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 上午9:02:40
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}
	/**
	 * 待办消息服务
	 * 提供待办消息服务接口
	 * 
	 * 业务规则
	 * 1、	初始值设置：运单号为空，处理状态默认为“全部”，变更申请部门为空，
	 * 更改受理查询开始时间和更改受理查询结束时间分别为当前日期中的00:00:00和23:59:59
	 * 2、	只有涉及到包装、件数、货物类型、目的站、运单号的更改才需要生成待办事项并被查询出来
	 * 3、	待办事项的生成是在执行节点生成后在节点部门生成待办事项
	 * 4、	更改受理的查询时间段不能超过X天（具体天数可配置
	 * 5、	只能查询到由“变更申请部门”申请，
	 * 处理部门为当前操作部门的待办事项，
	 * 不能查询到其他部门的待办事项
	 * 6、	若查询条件中包含有运单号时，则只以运单号作为唯一查询条件
	 * 7、	【待办事项】栏中记录的“查看”按钮在任何时候都可点击查看标签信息
	 * 8、	【打印标签】栏中的“打印标签”按钮在任何时候都可点击再打印
	 * 9、	系统打印标签时，判断是否已打印过标签，若未打印过则生成新的走货路径，否则不生成。打印出新标签
	 * 10、     点击“打印标签”时，系统判断是否已出库，
	 * 若是则更新状态为“处理超时”同时更新处理时间，
	 * 否则根据是否已全部打印来更新状态：
	 * 未全部打印时更新为 “处理中”，全部打印时更新为“已处理”
	 * 11、   每打印一件则更新一次处理时间为当前时间
	 * 12、	若该标签已打印过，则不再更新状态和处理时间
	 * 当货物出库后，点击打印标签时，系统自动判断该票货是否已打标签，
	 * 是则继续，否则提示“该货物已出本部门库存，不能再处理
	 * 13、	执行节点的生成规则参见“SUC-509调整运单执行计划(自动)”的SR2的第2点
	 * 
	 */
	private IPickupToDoMsgService pickupToDoMsgService;
	
	/**
	 * 偏线service接口
	 * 提供偏线服务接口
	 */
	private IExternalBillService externalBillService;

	/**
	 * 计价条目dao
	 * 提供计价条目持久化接口
	 */
	private IPriceEntryDao priceEntryDao;
	
	/**
	 * 更改单修改件数和打木架数
	 * 量修改详细信息冗余保存对象dao
	 */
	private ILabeledGoodChangeDao labeledGoodChangeDao;
    /**
     * 业务监控服务
     * 提供业务监控服务接口
     */
	private IBusinessMonitorService businessMonitorService;
	
	/**
	 * @author 350909   郭倩云
	 * @date 2016-08-05 15:28:40
	 * @comment 推送快递轨迹给菜鸟的接口
	 */
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;

	/**
	 * @author 350909   郭倩云
	 * @date 2016-08-05 15:28:40
	 * @comment 推送快递轨迹给菜鸟的接口
	 */
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	
	/**
	 * @author 350909   郭倩云
	 * @date 2016-08-012 16:12:40
	 * @comment  快递代理服务接口
	 */
	private	ILdpAgencyDeptService  ldpAgencyDeptService;
	
	/**
	 * @author 350909   郭倩云
	 * @date 2016-08-012 16:12:40
	 * @comment  快递代理服务接口
	 */
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	/**
	 * @author 350909   郭倩云
	 * @date 2016-08-012 16:12:40
	 * @comment 行政组织服务接口
	 */
	private IAdministrativeRegionsService   administrativeRegionsService;
	
	/**
	 * @author 350909   郭倩云
	 * @date 2016-08-012 16:12:40
	 * @comment 行政组织服务接口
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
 

	/**
	 * 业务互斥锁服务
	 *  提供业务互斥锁服务接口
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 录入航空正单service
	 * 提供录入航空正单服务接口
	 */
	private  IAirWaybillService  airWaybillService;
	
	private IWaybillRfcVarificationDao waybillRfcVarificationDao;
	
	private IToDoMsgService toDoMsgService;
	
	/**
	 * 走货路劲
	 */
	private IFreightRouteService freightRouteService;

	/**
	 * 国际化信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	private IMessageBundle messageBundle; 
    /**
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 安装费明细
	 */
	private IinstallationService installationService;
	
	private IBillCaculateService billCaculateService;
		
	public IBillCaculateService getBillCaculateService() {
		return billCaculateService;
	}

	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}

	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private WaybillRfcBatchChangeDao waybillRfcBatchChangeDao;
	
	public void setWaybillRfcBatchChangeDao(
			WaybillRfcBatchChangeDao waybillRfcBatchChangeDao) {
		this.waybillRfcBatchChangeDao = waybillRfcBatchChangeDao;
	}

	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	
	//待人工补码记录service
	private IAutoAddCodeByHandService autoAddCodeByHandService;

	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}


	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}


	/**
	 * @return the freightRouteService
	 */
	public IFreightRouteService getFreightRouteService() {
		return freightRouteService;
	}


	/**
	 * @param freightRouteService the freightRouteService to set
	 */
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}

	
	private IWaybillRfcTodoJobService waybillRfcTodoJobService;

	
	
	public IWaybillRfcTodoJobService getWaybillRfcTodoJobService() {
		return waybillRfcTodoJobService;
	}


	public void setWaybillRfcTodoJobService(
			IWaybillRfcTodoJobService waybillRfcTodoJobService) {
		this.waybillRfcTodoJobService = waybillRfcTodoJobService;
	}


	public void setInstallationService(IinstallationService installationService) {
		this.installationService = installationService;
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
	 * @return the waybillPickupService
	 */
	public IWaybillPickupService getWaybillPickupService() {
		return waybillPickupService;
	}

	/**
	 * @param waybillPickupService the waybillPickupService to set
	 */
	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	/**
	 * @return the waybillManagerService
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @return the waybillRfcDao
	 */
	public IWaybillRfcDao getWaybillRfcDao() {
		return waybillRfcDao;
	}

	/**
	 * @param waybillRfcDao the waybillRfcDao to set
	 */
	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}

	/**
	 * @return the waybillPendingDao
	 */
	public IWaybillPendingDao getWaybillPendingDao() {
		return waybillPendingDao;
	}

	/**
	 * @param waybillPendingDao the waybillPendingDao to set
	 */
	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}

	/**
	 * @return the labeledGoodDao
	 */
	public ILabeledGoodDao getLabeledGoodDao() {
		return labeledGoodDao;
	}

	/**
	 * @param labeledGoodDao the labeledGoodDao to set
	 */
	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	/**
	 * @return the waybillSignResultService
	 */
	public IWaybillSignResultService getWaybillSignResultService() {
		return waybillSignResultService;
	}

	/**
	 * @param waybillSignResultService the waybillSignResultService to set
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * @return the calculateTransportPathService
	 */
	public ICalculateTransportPathService getCalculateTransportPathService() {
		return calculateTransportPathService;
	}

	/**
	 * @param calculateTransportPathService the calculateTransportPathService to set
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * @return the waybillRfcProofDao
	 */
	public IWaybillRfcProofDao getWaybillRfcProofDao() {
		return waybillRfcProofDao;
	}

	/**
	 * @param waybillRfcProofDao the waybillRfcProofDao to set
	 */
	public void setWaybillRfcProofDao(IWaybillRfcProofDao waybillRfcProofDao) {
		this.waybillRfcProofDao = waybillRfcProofDao;
	}

	/**
	 * @return the truckTaskService
	 */
	public ITruckTaskService getTruckTaskService() {
		return truckTaskService;
	}

	/**
	 * @param truckTaskService the truckTaskService to set
	 */
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	/**
	 * @return the stockService
	 */
	public IStockService getStockService() {
		return stockService;
	}

	/**
	 * @param stockService the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * @return the unloadTaskService
	 */
	public IUnloadTaskService getUnloadTaskService() {
		return unloadTaskService;
	}

	/**
	 * @param unloadTaskService the unloadTaskService to set
	 */
	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}

	/**
	 * @return the actualFreightService
	 */
	public IActualFreightService getActualFreightService() {
		return actualFreightService;
	}

	/**
	 * @param actualFreightService the actualFreightService to set
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * @return the fileManager
	 */
	public FileManager getFileManager() {
		return fileManager;
	}

	/**
	 * @param fileManager the fileManager to set
	 */
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	/**
	 * @return the waybillRfcVarificationService
	 */
	public IWaybillRfcVarificationService getWaybillRfcVarificationService() {
		return waybillRfcVarificationService;
	}

	/**
	 * @param waybillRfcVarificationService the waybillRfcVarificationService to set
	 */
	public void setWaybillRfcVarificationService(
			IWaybillRfcVarificationService waybillRfcVarificationService) {
		this.waybillRfcVarificationService = waybillRfcVarificationService;
	}

	/**
	 * @return the repaymentService
	 */
	public IRepaymentService getRepaymentService() {
		return repaymentService;
	}

	/**
	 * @param repaymentService the repaymentService to set
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	/**
	 * @return the waybillDao
	 */
	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}

	/**
	 * @param waybillDao the waybillDao to set
	 */
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	/**
	 * @return the labeledGoodTodoDao
	 */
	public ILabeledGoodTodoDao getLabeledGoodTodoDao() {
		return labeledGoodTodoDao;
	}

	/**
	 * @param labeledGoodTodoDao the labeledGoodTodoDao to set
	 */
	public void setLabeledGoodTodoDao(ILabeledGoodTodoDao labeledGoodTodoDao) {
		this.labeledGoodTodoDao = labeledGoodTodoDao;
	}

	/**
	 * @return the pickupToDoMsgService
	 */
	public IPickupToDoMsgService getPickupToDoMsgService() {
		return pickupToDoMsgService;
	}

	/**
	 * @param pickupToDoMsgService the pickupToDoMsgService to set
	 */
	public void setPickupToDoMsgService(IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}

	/**
	 * @return the externalBillService
	 */
	public IExternalBillService getExternalBillService() {
		return externalBillService;
	}

	/**
	 * @param externalBillService the externalBillService to set
	 */
	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}

	/**
	 * @return the priceEntryDao
	 */
	public IPriceEntryDao getPriceEntryDao() {
		return priceEntryDao;
	}

	/**
	 * @param priceEntryDao the priceEntryDao to set
	 */
	public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
		this.priceEntryDao = priceEntryDao;
	}

	/**
	 * @return the businessMonitorService
	 */
	public IBusinessMonitorService getBusinessMonitorService() {
		return businessMonitorService;
	}

	/**
	 * @param businessMonitorService the businessMonitorService to set
	 */
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	/**
	 * @return the businessLockService
	 */
	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	/**
	 * @param businessLockService the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * @return the orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	/**
	 * @return the labeledGoodChangeDao
	 */
	public ILabeledGoodChangeDao getLabeledGoodChangeDao() {
		return labeledGoodChangeDao;
	}

	/**
	 * @return the airWaybillService
	 */
	public IAirWaybillService getAirWaybillService() {
		return airWaybillService;
	}

	/**
	 * @param labeledGoodChangeDao the labeledGoodChangeDao to set
	 */
	public void setLabeledGoodChangeDao(ILabeledGoodChangeDao labeledGoodChangeDao) {
		this.labeledGoodChangeDao = labeledGoodChangeDao;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to
	 *            set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setWaybillRfcVarificationDao(IWaybillRfcVarificationDao waybillRfcVarificationDao) {
		this.waybillRfcVarificationDao = waybillRfcVarificationDao;
	}

	/**
	 * 是否已经结清货款（部分结清同样是true）
	 */
	public boolean isExistRepayment(String waybillNo){
		return repaymentService.isExistRepayment(waybillNo);
	}

	// 待刷卡运单service liding add
	private IWSCWayBillManageService wscWayBillManageService;

	public void setWscWayBillManageService(
			IWSCWayBillManageService wscWayBillManageService) {
		this.wscWayBillManageService = wscWayBillManageService;
	}
	
	
	/**
	 * 
	 * <p>
	 * 根据运单号导入运单
	 * </p>
	 * 
	 * @author 265475-foss-zoushengli
	 * @date 2016-1-18 上午11:00:07
	 * @param waybillNo
	 * @param deptCode
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	public WaybillRfcImportDto importWaybillBy(String waybillNo, String loginOrgCode) {
		WaybillDto waybill = checkWaybillRfc(waybillNo, loginOrgCode, null);//查询检查运单
		WaybillRfcImportDto rfcImportDto = new WaybillRfcImportDto();//创建对象
		// 原始运单
		rfcImportDto.setWaybillDto(waybill);

		if(waybill!=null && waybill.getWaybillEntity()!=null &&
				(productService.onlineDetermineIsExpressByProductCode(waybill.getWaybillEntity().getProductCode(), waybill.getWaybillEntity().getBillTime()))){
			WaybillExpressEntity exp = waybillExpressDao.queryWaybillExpressByNo(waybill.getWaybillEntity().getWaybillNo());
			rfcImportDto.setWaybillExpressEntity(exp);
		}
		
		// 货物状态
		WaybillStockStatusDto stockStatus = queryWaybillStockStatus(waybillNo,waybill.getWaybillEntity() );
		if (stockStatus != null) {// 货物状态不是空
			rfcImportDto.setStockStatus(stockStatus);
			// 有无出库记录
//			boolean isOutStock = stockService.isOutStock(waybillNo, waybill.getWaybillEntity().getBillTime());//出库记录
			stockStatus.setStockRecord(WaybillRfcConstants.RECEIVE_STOCK.equals(stockStatus.getResult()) ? 
					FossConstants.NO : FossConstants.YES);
		}
		// 转货、返货记录
		List<String> rfcTypes = new ArrayList<String>();//创建对象
		rfcTypes.add("N/A");//
		rfcTypes.add(WaybillRfcConstants.TRANSFER);//转货
		rfcTypes.add(WaybillRfcConstants.RETURN);//返货
		List<TransportRecordDto> transportRecordDtos = waybillRfcDao.queryWaybillRfcRecord(waybillNo, rfcTypes);
		
		boolean isNeedGetFirstRecord=true;
		
		if(transportRecordDtos!=null&&transportRecordDtos.size()>0){
		for(TransportRecordDto transportRecordDto:transportRecordDtos)
		{
			if(StringUtil.isEmpty(transportRecordDto.getRfcType()))
			{
				isNeedGetFirstRecord=false;
			}
		}
		}else{
			
			isNeedGetFirstRecord=false;
		}
		
		if(isNeedGetFirstRecord){
			List<TransportRecordDto> firstRecords=waybillRfcDao.queryFirstRecord(waybillNo);
 
			if(firstRecords!=null&&firstRecords.size()>0)
			{
				transportRecordDtos.add(0, firstRecords.get(0));
			}
		}
		
		
		rfcImportDto.setTransportRecordDtos(transportRecordDtos);
		
		//查询货签
		List<LabeledGoodEntity> labeledGoodEntities = labeledGoodDao.queryAllSerialByWaybillNo(waybillNo);
		rfcImportDto.setLabeledGoodEntities(labeledGoodEntities);
		
		//货签的冗余信息表
		queryLabeledGood(waybillNo, rfcImportDto, labeledGoodEntities);
		
		// 其他费用明细
		List<WaybillOtherChargeDto> otherChargeDtos = waybillRfcDao.queryOtherChargeByNo(waybillNo);
		//手动添加超重费    pgy
		List<WaybillChargeDtlEntity> chargeDts=waybill.getWaybillChargeDtlEntity();
		List<WaybillOtherChargeDto> othsSdtj = null;
		if(!chargeDts.isEmpty()){
			for(WaybillChargeDtlEntity chargeDt:chargeDts){
				if(PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(chargeDt.getPricingEntryCode())){
					othsSdtj  = waybillRfcDao.queryCZHCZFWFSDTJByNo(waybillNo);
				}
			}
		}
		
		if(othsSdtj !=null){
			otherChargeDtos.addAll(othsSdtj);
		}
		rfcImportDto.setOtherChargeDtos(otherChargeDtos);

		return rfcImportDto;
	}
	/**
	 * 
	 * <p>
	 * 根据运单号导入运单
	 * </p>
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-25 上午11:00:07
	 * @param waybillNo
	 * @param deptCode
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	public WaybillRfcImportDto importWaybillByNumber(String waybillNo, String loginOrgCode) {
		WaybillDto waybill = checkWaybillRfcMatched(waybillNo, loginOrgCode, null);//查询检查运单
		WaybillRfcImportDto rfcImportDto = new WaybillRfcImportDto();//创建对象
		// 原始运单
		rfcImportDto.setWaybillDto(waybill);

		if(waybill!=null && waybill.getWaybillEntity()!=null &&
				(productService.onlineDetermineIsExpressByProductCode(waybill.getWaybillEntity().getProductCode(), waybill.getWaybillEntity().getBillTime()))){
			WaybillExpressEntity exp = waybillExpressDao.queryWaybillExpressByNo(waybill.getWaybillEntity().getWaybillNo());
			rfcImportDto.setWaybillExpressEntity(exp);
		}
		
		// 货物状态
		WaybillStockStatusDto stockStatus = queryWaybillStockStatus(waybillNo,waybill.getWaybillEntity() );
		if (stockStatus != null) {// 货物状态不是空
			rfcImportDto.setStockStatus(stockStatus);
			// 有无出库记录
//			boolean isOutStock = stockService.isOutStock(waybillNo, waybill.getWaybillEntity().getBillTime());//出库记录
			stockStatus.setStockRecord(WaybillRfcConstants.RECEIVE_STOCK.equals(stockStatus.getResult()) ? 
					FossConstants.NO : FossConstants.YES);
		}
		// 转货、返货记录
		List<String> rfcTypes = new ArrayList<String>();//创建对象
		rfcTypes.add("N/A");//
		rfcTypes.add(WaybillRfcConstants.TRANSFER);//转货
		rfcTypes.add(WaybillRfcConstants.RETURN);//返货
		List<TransportRecordDto> transportRecordDtos = waybillRfcDao.queryWaybillRfcRecord(waybillNo, rfcTypes);
		
		boolean isNeedGetFirstRecord=true;
		
		if(transportRecordDtos!=null&&transportRecordDtos.size()>0){
		for(TransportRecordDto transportRecordDto:transportRecordDtos)
		{
			if(StringUtil.isEmpty(transportRecordDto.getRfcType()))
			{
				isNeedGetFirstRecord=false;
			}
		}
		}else{
			
			isNeedGetFirstRecord=false;
		}
		
		if(isNeedGetFirstRecord){
			List<TransportRecordDto> firstRecords=waybillRfcDao.queryFirstRecord(waybillNo);
 
			if(firstRecords!=null&&firstRecords.size()>0)
			{
				transportRecordDtos.add(0, firstRecords.get(0));
			}
		}
		
		
		rfcImportDto.setTransportRecordDtos(transportRecordDtos);
		
		//查询货签
		List<LabeledGoodEntity> labeledGoodEntities = labeledGoodDao.queryAllSerialByWaybillNo(waybillNo);
		rfcImportDto.setLabeledGoodEntities(labeledGoodEntities);
		
		//货签的冗余信息表
		queryLabeledGood(waybillNo, rfcImportDto, labeledGoodEntities);
		
		// 其他费用明细
		List<WaybillOtherChargeDto> otherChargeDtos = waybillRfcDao.queryOtherChargeByNo(waybillNo);
		//手动添加超重费    pgy
		List<WaybillChargeDtlEntity> chargeDts=waybill.getWaybillChargeDtlEntity();
		List<WaybillOtherChargeDto> othssdtj = null;
		if(!chargeDts.isEmpty()){
			for(WaybillChargeDtlEntity chargeDt:chargeDts){
				if(PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(chargeDt.getPricingEntryCode())){
					othssdtj  = waybillRfcDao.queryCZHCZFWFSDTJByNo(waybillNo);
				}
			}
		}
		
		if(othssdtj !=null){
			otherChargeDtos.addAll(othssdtj );
		}
		rfcImportDto.setOtherChargeDtos(otherChargeDtos);

		return rfcImportDto;
	}

	/**
	 * 
	 * 查询货签的冗余信息表
	 * 如果没有历史修改冗余信息 就采用原始货签记录来创造
	 * @param waybillNo
	 * @param rfcImportDto
	 */
	private void queryLabeledGood(String waybillNo,
			WaybillRfcImportDto rfcImportDto, List<LabeledGoodEntity> labeledGoodOriginalEntities) {
		//查询货签 之前我们需要查询以前审批货签的冗余信息表
		List<LabeledGoodChangeEntity> labeledGoodEntities = labeledGoodChangeDao
				.queryLastApprovedLabelGoodChangeHistory(waybillNo);
		List<LabeledGoodChangeHistoryDto> labeledGoodChangeHistoryDtoList = new ArrayList<LabeledGoodChangeHistoryDto>();//创建对象
		
		
		if(labeledGoodEntities!=null && labeledGoodEntities.size()>0){
			int maxChangeTimes = 0;
			for(LabeledGoodChangeEntity labeledGoodChangeHistoryEntity: labeledGoodEntities){
				//如果是0的话，取的List的第一个对象的change times 由于查询时根据change times 降序排列 所以第一个值肯定是最大的一个更改次数
				int currnetChangeTimes = Integer.parseInt(labeledGoodChangeHistoryEntity.getChangeTimes());
				if(maxChangeTimes == 0){
					maxChangeTimes = currnetChangeTimes;
				}
				//如果次数变小了 这表示是以前的历史修改  这时候我们就不对他做任何操作了
				if(maxChangeTimes > currnetChangeTimes){
					continue;
				}
				
				LabeledGoodChangeHistoryDto historyDto = new LabeledGoodChangeHistoryDto();//创建对象
				try {
					PropertyUtils.copyProperties(historyDto, labeledGoodChangeHistoryEntity);
				} catch (Exception e) {
					LOG.error("PropertyUtils.copyProperties exception", e);
				}
				
				//这个货签是从服务器端数据库读取  用于在gui客户端的时候和客户界面上新增加的货签更改信息区分开
				historyDto.setIsFromService(FossConstants.YES);
				
				historyDto.setChangeTimes(Integer.valueOf(maxChangeTimes+1).toString());
				historyDto.setFlowStatus(LabeledGoodChangeHistoryConstants.FLOW_STATUS_IN_PROCESS);
				//zxy 20131128 ISSUE-4391 start 新增：初始化查询的库存货签
				//已经存在的数据 统一设置成SALVER
				if(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_ADD_NEW.equals(historyDto.getPackageSalver())){
					historyDto.setPackageSalver(WaybillConstants.PACKAGE_TYPE_SALVER);
				}else if(LabeledGoodChangeHistoryConstants.PACKAGE_TYPE_SALVER_DELETE.equals(historyDto.getPackageSalver())){
					historyDto.setPackageSalver(StringUtils.EMPTY);
				}
				//zxy 20131128 ISSUE-4391 end 新增：初始化查询的库存货签
				labeledGoodChangeHistoryDtoList.add(historyDto);
				
			}
			
			//BUG-43349 改件数 中转可能在之后会更待我们的货签表，如果中转发生了更改  这时候我们一定要把中转新加的货签包含进来  这很重要
			//没有包含进来会算不对件数
			for(LabeledGoodEntity goodEntity : labeledGoodOriginalEntities){
				
				if(goodEntity==null || !FossConstants.YES.equals(goodEntity.getActive())){
					continue;
				}
				boolean hasThisGoods = false;
				for(LabeledGoodChangeHistoryDto historyDto: labeledGoodChangeHistoryDtoList){
					if(goodEntity.getSerialNo().equals(historyDto.getSerialNo())){
						hasThisGoods = true;
					}
				}
				if(!hasThisGoods){
					LabeledGoodChangeHistoryDto historyDto = new LabeledGoodChangeHistoryDto();//创建对象
					historyDto.setWaybillNo(waybillNo);
					historyDto.setSerialNo(goodEntity.getSerialNo());
					historyDto.setLabeledGoodId(goodEntity.getId());
					
					//这个货签是从服务器端数据库读取  用于在gui客户端的时候和客户界面上新增加的货签更改信息区分开
					historyDto.setIsFromService(FossConstants.YES);
					historyDto.setFlowStatus(LabeledGoodChangeHistoryConstants.FLOW_STATUS_IN_PROCESS);
					historyDto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
					
					//第一次
					historyDto.setChangeTimes(Integer.valueOf(maxChangeTimes+1).toString());
					
					labeledGoodChangeHistoryDtoList.add(historyDto);
				}
			}
		}else{
			//如果没有历史修改冗余信息 就采用原始货签记录来创造
			for(LabeledGoodEntity goodEntity : labeledGoodOriginalEntities){
				
				if(goodEntity==null || !FossConstants.YES.equals(goodEntity.getActive())){
					continue;
				}
				
				LabeledGoodChangeHistoryDto historyDto = new LabeledGoodChangeHistoryDto();//创建对象
				historyDto.setWaybillNo(waybillNo);
				historyDto.setSerialNo(goodEntity.getSerialNo());
				historyDto.setLabeledGoodId(goodEntity.getId());
				
				//这个货签是从服务器端数据库读取  用于在gui客户端的时候和客户界面上新增加的货签更改信息区分开
				historyDto.setIsFromService(FossConstants.YES);
				historyDto.setFlowStatus(LabeledGoodChangeHistoryConstants.FLOW_STATUS_IN_PROCESS);
				historyDto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
				
				//第一次
				historyDto.setChangeTimes(Integer.valueOf(1).toString());
				historyDto.setPackageSalver(goodEntity.getPackageType()); //zxy 20131118 ISSUE-4391 设置包装类型，如果有change的，则不会走这句，新开单无change需要从原始货签表中取
				labeledGoodChangeHistoryDtoList.add(historyDto);
			}
			
			
			
		}
		//更改单修改件数和打木架数量修改详细信息冗余保存对象
		rfcImportDto.setLabeledGoodChangeHistoryDtoList(labeledGoodChangeHistoryDtoList);
	}

	/**
	 * 
	 * 根据运单号查询运单状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:31:33
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#queryWaybillStockStatus(java.lang.String)
	 */
	@Override
	public WaybillStockStatusDto queryWaybillStockStatus(String waybillNo,WaybillEntity waybill) {
		if(waybill == null){
			waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		}
		
		String goodsDeptCode = null;
		// 运单首发库存、最末库存部门
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		if (actualFreightEntity == null) {
			return null;
		}

		WaybillStockStatusDto stockStatusDto = new WaybillStockStatusDto();//创建对象

		String firstStockDeptCode = actualFreightEntity.getStartStockOrgCode();
		String lastStockDeptCode = actualFreightEntity.getEndStockOrgCode();
		if (StringUtil.isEmpty(firstStockDeptCode) || StringUtil.isEmpty(lastStockDeptCode)) {
			/**
			 * 生成的运单出发、到达库存部门不正确！
			 */
			throw new WaybillImportException(WaybillImportException.START_DEPT_END_DEPT_ERROR);
		}
		stockStatusDto.setStartStockOrgCode(firstStockDeptCode);
		stockStatusDto.setEndStockOrgCode(lastStockDeptCode);

		// 1、获取最快的流水号
		String fastestSerialNo = null;
		// 2、获取分批配载情况的流水号、根据路径最短优先排列
		List<String> partSerialNos = calculateTransportPathService.listFastGoodsNo(waybillNo);
		if (partSerialNos == null || partSerialNos.size() == 0 || partSerialNos.get(0) == null) {
			List<LabeledGoodEntity> labeledGoodEntities = labeledGoodDao.queryAllSerialByWaybillNo(waybillNo);
			if (labeledGoodEntities.size() > 0) {
				fastestSerialNo = labeledGoodEntities.get(0).getSerialNo();
			}
		} else {
			fastestSerialNo = partSerialNos.get(0);
		}

		if (fastestSerialNo == null) {
			/**
			 * 未找到该运单对应的流水号！
			 */
			throw new WaybillImportException(WaybillImportException.NOT_FIND_WAYBILL_NUMBER_FOR_SERIALS);
		}

		// 3、根据运单号、流水号找当前库存部门
		StockEntity stockEntity = stockService.queryUniqueStock(waybillNo, fastestSerialNo);
		if (stockEntity != null) {
			goodsDeptCode = stockEntity.getOrgCode();
			//如果是外场库存，直接中转部门库存
			if (firstStockDeptCode.equals(goodsDeptCode)) {
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(goodsDeptCode);
				if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getTransferCenter())
						&& lastStockDeptCode.equals(firstStockDeptCode)){
					stockStatusDto.setResult(WaybillRfcConstants.DELIVERY_STOCK);
					stockStatusDto.setStockRecord(FossConstants.YES);
				}else{
					stockStatusDto.setResult(WaybillRfcConstants.RECEIVE_STOCK);
					stockStatusDto.setStockRecord(FossConstants.NO);
				}
			} else if (lastStockDeptCode.equals(goodsDeptCode)) {
				stockStatusDto.setResult(WaybillRfcConstants.DELIVERY_STOCK);
				stockStatusDto.setStockRecord(FossConstants.YES);
			} else {
				stockStatusDto.setResult(WaybillRfcConstants.TRANSFER_STOCK);
				stockStatusDto.setStockRecord(FossConstants.YES);
			}
		} else {
			

			boolean hasAirStockOut = false;//是否有航空正单交接
			
			//只有精准空运才需要判断是否有航空正单交接出库
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
				.equals(waybill.getProductCode())){
				//如果存在航空正单交接单
				hasAirStockOut = airWaybillService.queryWaybillNoStockExists(waybillNo);
				if(hasAirStockOut){//存在就设置状态为出库 
					stockStatusDto.setResult(WaybillRfcConstants.DELIVERY_STOCK_OUT);
					stockStatusDto.setStockRecord(FossConstants.YES);//有记录
					goodsDeptCode = waybill.getLastLoadOrgCode();
				}
			}
			
			if(!hasAirStockOut){//没有航空正单交接
				
				// 4、根据运单号、流水号查找最新的交接单
				HandOverBillEntity handOverBillEntity = truckTaskService.queryOnTheWayHandOverBillBySerialNo(waybillNo, fastestSerialNo);
				if (handOverBillEntity != null) {
					String departDeptCode = handOverBillEntity.getDepartDeptCode();
					goodsDeptCode = handOverBillEntity.getArriveDeptCode();
					if (firstStockDeptCode.equals(lastStockDeptCode) || lastStockDeptCode.equals(departDeptCode)) {
						stockStatusDto.setResult(WaybillRfcConstants.DELIVERY_STOCK_OUT);
						stockStatusDto.setStockRecord(FossConstants.YES);
					}else if (firstStockDeptCode.equals(departDeptCode)) {
						stockStatusDto.setResult(WaybillRfcConstants.RECEIVE_STOCK_OUT);
						stockStatusDto.setStockRecord(FossConstants.YES);
					}else {
						stockStatusDto.setResult(WaybillRfcConstants.TRANSFER_STOCK_OUT);
						stockStatusDto.setStockRecord(FossConstants.YES);
					}
				} else {
					
					//找不到交接单及库存，默认判断为送货中
					goodsDeptCode = lastStockDeptCode;
					stockStatusDto.setResult(WaybillRfcConstants.DELIVERY_STOCK_OUT);
					stockStatusDto.setStockRecord(FossConstants.YES);

				}
			}
		}

		stockStatusDto.setCurrentStockOrgCode(goodsDeptCode);
		return stockStatusDto;
	}

	/**
	 * 
	 * 查询运单所有货签库存状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-7 下午2:19:08
	 */
	@Override
	public WaybillLabeledGoodStockDto queryWaybillLabeledGoodStock(String waybillNo) {
		WaybillLabeledGoodStockDto labeledGoodStockDto = new WaybillLabeledGoodStockDto();//创建对象
		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);


		// 运单首发库存、最末库存部门
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		if (actualFreightEntity == null) {
			return null;
		}
		String lastStockDeptCode = actualFreightEntity.getEndStockOrgCode();

		// 所有流水号
		List<LabeledGoodEntity> labeledGoodEntities = labeledGoodDao.queryAllSerialByWaybillNo(waybillNo);
		List<LabeledGoodStockStatusDto> serialNoStockStatus = null;
		if(CollectionUtils.isNotEmpty(labeledGoodEntities)){
			serialNoStockStatus = new ArrayList<LabeledGoodStockStatusDto>(labeledGoodEntities.size());
		}else{
			serialNoStockStatus = new ArrayList<LabeledGoodStockStatusDto>();
		}
		labeledGoodStockDto.setLabeledGoodStockStatusDtos(serialNoStockStatus);
		/*
		 * BUG-12481 卸车多货\	清仓多货\单票入库\分配配载 都会导致运单流水分布在多个部门，listFastGoodsNo的判断不充分，都按分批配载处理
		 * 经讨论，还是判断分批配载，此类情况需要中转处理走货路径，满足分批
		*/
		// 判断是否分配配载
		String isPartLoad = stockService.querySeparateStatusByStock(waybillNo);
		//库存无法判断时，查询交接单
		if(isPartLoad == null){
			isPartLoad = handOverBillService.queryGoodsBeSeparatedFromHandOverBillByWaybillNo(waybillNo);
		}
		if(FossConstants.NO.equals(isPartLoad)){
			// 非分配配载
			if (labeledGoodEntities != null && labeledGoodEntities.size() > 0) {
				LabeledGoodStockStatusDto tempDto = null;
				for (LabeledGoodEntity labeledGoodEntity : labeledGoodEntities) {
					if (tempDto == null) {
						tempDto = getLabeledGoodStockStatusDto(waybillEntity,waybillNo, lastStockDeptCode, labeledGoodEntity);
					}
					LabeledGoodStockStatusDto dto = new LabeledGoodStockStatusDto();//创建对象
					dto.setCurrentStockOrgCode(tempDto.getCurrentStockOrgCode());
					dto.setLabeledGoodEntity(labeledGoodEntity);
					dto.setInStock(tempDto.isInStock());
					serialNoStockStatus.add(dto);
				}
			}
		} else {
			// 分配配载
			if (labeledGoodEntities != null && labeledGoodEntities.size() > 0) {
				for (LabeledGoodEntity labeledGoodEntity : labeledGoodEntities) {
					LabeledGoodStockStatusDto dto = getLabeledGoodStockStatusDto(waybillEntity,waybillNo, lastStockDeptCode, labeledGoodEntity);
					serialNoStockStatus.add(dto);
				}
			}
		}
		return labeledGoodStockDto;
	}

	/**
	 * 
	 * 查询单个货签库存状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-7 下午2:50:38
	 */
	private LabeledGoodStockStatusDto getLabeledGoodStockStatusDto(WaybillEntity waybillEntity,String waybillNo, String lastStockDeptCode, LabeledGoodEntity labeledGoodEntity) {

		String currentOrgCode = null;

		LabeledGoodStockStatusDto dto = new LabeledGoodStockStatusDto();//创建对象
		dto.setLabeledGoodEntity(labeledGoodEntity);
		// 3、根据运单号、流水号找当前库存部门
		StockEntity stockEntity = stockService.queryUniqueStock(waybillNo, labeledGoodEntity.getSerialNo());

		if (stockEntity != null) {
			dto.setInStock(true);
			currentOrgCode = stockEntity.getOrgCode();
		} else {
			//不在库存，待办不生成到具体部门
			dto.setInStock(false);
		}
		
		if(currentOrgCode != null){
			/**
			 * 如果运输性质是空运：需要把当前库存部门（即处理部门）设置为最终配载部门
			 * 如果不是空运，那么都设置为当前库存部门
			 */
			if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())
					&& currentOrgCode.equals(lastStockDeptCode)){
				dto.setCurrentStockOrgCode(waybillEntity.getLastLoadOrgCode());//最终配载部门
			}else{
				dto.setCurrentStockOrgCode(currentOrgCode);	//当前库存部门
			}
		}
		return dto;
	}

	/**
	 * 
	 * 校验运单是否允许发生变更
	 * 
	 * @author 265475-foss-zoushengli
	 * @date 2012-11-29 下午2:35:57
	 */
	private WaybillDto checkWaybillRfc(String waybillNo, String loginOrgCode, WaybillRfcSubmitDto submitDto) {
	
		WaybillDto waybill = waybillManagerService.queryWaybillByNo(waybillNo);
		//判定货物是否在到达部门库存中，外发的如果到了外发部门之后 发更改不进行校验，没到外发部门的时候是校验的
		boolean isArrive = stockService.queryStockByWaybillNo(waybillNo);
		if(isArrive){
			throw new WaybillImportException("库存不在到达部门");
		}
		// 运单信息表中未找到
		if (waybill == null || waybill.getWaybillEntity() == null) {
			// 检查是否PDA未补录
			WaybillPendingEntity pendingEntity = waybillPendingDao.queryByWaybillNumber(waybillNo);
			if (pendingEntity == null) {
				/**
				 * 运单号不存在！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_NUMBER_NOT_EXIST);
			}
			else {
				/**
				 * 运单未补录，不能起草运单变更！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_NOT_ADDITIONAL_RECORDING_CAN_NOT_CHANGE);
			}
		}
		
		
		//pending waybill
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybill.getWaybillEntity().getPendingType())) {
			/**
			 * 运单未补录，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_NOT_ADDITIONAL_RECORDING_CAN_NOT_CHANGE);
		}
		// 运单状态
		if (waybill.getActualFreightEntity() == null) {
			/**
			 * 运单ActualFreight无数据，获取不到货物状态！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_ACTUAL_FREIGHT_NOT_DATA);
		}
		/**
		 * @项目：家装项目
		 * @功能：若家装项目已经交货确认，不能起草更改单
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-10-17下午13:59
		 */
		if(!StringUtils.isEmpty(waybill.getActualFreightEntity().getSpecialValueAddedServiceType())){
			String state=queryWaybillState(waybillNo);
			if(!StringUtils.isEmpty(state)&&state.equals("DE_CONFIRM")){
				throw new WaybillImportException("此特殊增值服务单号已完成交货，不可发更改！");
			}
		}
		String waybillStatus = waybill.getActualFreightEntity().getStatus();
		if (WaybillConstants.OBSOLETE.equals(waybillStatus)) {
			/**
			 * 运单已作废，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_CANCEL_CAN_NOT_CAHNGE);
		} else if (WaybillConstants.ABORTED.equals(waybillStatus)) {
			/**
			 * 运单已中止，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_STOP_CAN_NOT_CAHNGE);
		}
		
		//是否结清货款
		String settleStatus = waybill.getActualFreightEntity().getSettleStatus();
		
		/**
		 * 已结清
		 */
		if(FossConstants.YES.equals(settleStatus)){
			/**
			 * 已结清货款, 不能起草运单变更！
			 */
//			throw new WaybillImportException(WaybillImportException.WAYBILL_SETTLE_CLEAR);
		}
		
		
//		//对于偏线运单，能做更改的前提条件是 中转以上接口返回null。
//		if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getWaybillEntity().getProductCode())){
//			ExternalBillDto billDto = new ExternalBillDto();//创建对象
//			billDto.setWaybillNo(waybillNo);
//			billDto = externalBillService.queryExternalBillByWaybillNoForChange(billDto);
//			if(billDto != null){
//				/**
//				 * 偏线运单已中转外发，不能起草运单变更！
//				 */
//				throw new WaybillImportException(WaybillImportException.WAYBILL_ALREADY_CELL_CAN_NOT_CHANGE);
//			}
//		}
		
		// 查询运单签收状态
		WaybillSignResultEntity signResultEntity = new WaybillSignResultEntity();
		signResultEntity.setActive(FossConstants.YES);
		signResultEntity.setWaybillNo(waybillNo);
		signResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signResultEntity);
		if (signResultEntity != null && (SignConstants.SIGN_STATUS_ALL.equals(signResultEntity.getSignStatus()) || SignConstants.SIGN_STATUS_PARTIAL.equals(signResultEntity.getSignStatus()))) {
			/**
			 * 货物已签收，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_ALREADY_SIGN_CAN_NOT_CHANGE);
		}

		List<String> waybillRfcStatus = new ArrayList<String>();//创建对象
		waybillRfcStatus.add(WaybillRfcConstants.PRE_AUDIT);
		waybillRfcStatus.add(WaybillRfcConstants.PRE_ACCECPT);
		WaybillRfcEntity rfcEntity = waybillRfcDao.queryRfcEntityByWaybillId(waybill.getWaybillEntity().getId(), waybillRfcStatus);
	
		//在到达部门库存
		//boolean isInReceiveStock = WaybillRfcConstants.DELIVERY_STOCK.equals(inventory);
		
		if (rfcEntity != null) {
			if (WaybillRfcConstants.PRE_AUDIT.equals(rfcEntity.getStatus())) {
				/**
				 * 该运单有运单变更单还未被审核，不能起草变更运单！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_HAVE_NOT_AUDIT_CAN_NOT_CHANGE);
			} else if (WaybillRfcConstants.PRE_ACCECPT.equals(rfcEntity.getStatus())) {
				/**
				 * 该运单有运单变更单还未被受理，不能起草变更运单！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILLRFC_HAVE_NOT_ACCEPT_CAN_NOT_CHANGE);
			}
		}

		// 非本部门运单包括 开单部门、收货部门

		if (loginOrgCode.equals(waybill.getWaybillEntity().getCreateOrgCode()) || loginOrgCode.equals(waybill.getWaybillEntity().getReceiveOrgCode())) {

			boolean isPayment = repaymentService.isExistRepayment(waybillNo);
			if (isPayment) {
				/**
				 * 该运单已进行结清货款操作，如需更改请联系到达部门进行反结清货款操作！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILLRFC_ALREADY_SETTLE_CAN_NOT_CHANGE);
			}
			
			try {
			    //灰度接口
                RequestDO requestDO = new RequestDO();
                requestDO.setServiceCode(WaybillRfcService.class.getName()+".checkWaybillRfc");
                requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
                requestDO.setSourceBillNos(waybillNo);
                requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
                VestResponse response = null;
                try{
                    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
                }catch (Exception e){
                	throw new BusinessException(Constants.GRAY_SERVICE_EXCEPTION+e.getMessage());
                }
                if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
                    List<VestBatchResult> batchResults = response.getVestBatchResult();
                    VestBatchResult batchResult = batchResults.get(0);
                    if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS, batchResult.getVestSystemCode())) {
                        waybillPickupService.canChange(waybillNo);
                    }else if(StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_CUBC, batchResult.getVestSystemCode())) {
                        //DP-FOSS zhaoyiqing 343617 配合CUBC，调用接口，传单号，判断能否发更改
                        //以下代码目的是判断是否零担，是零担才调用CUBC接口校验
                        WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);
                        if(!StringUtil.equals(WaybillConstants.YES,waybillEntity.getIsExpress())){
                            validateWaybillNoForCUBC(waybillNo,WaybillConstants.CHANGE);
                        }
                    }
                }

			} catch (SettlementException e) {
				throw new WaybillImportException(messageBundle.getMessage(e.getErrorCode(), e.getErrorArguments()));
				
			}
		}else {
			/**
			 * 非本部门运单，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.HAVE_NOT_AUTHORITY_TO_CHANGE);
		}
		/**
		 * 快递运单派送中，不能起草运单变更！
		 */
		if(null!=waybillExpressService.queryWaybillExpressByNo(waybillNo)){
			boolean isfale = loadService.ifCouldBeChangedeWaybillNo(waybillNo);
			if(!isfale){
				throw new WaybillImportException("快递货物若货物状态为派送中，则不允许发更改");
			}
		}
		if( submitDto!=null && submitDto.getRfcEntity()!=null && submitDto.getRfcEntity().getOldVersionWaybillId()!=null){
			boolean isOldWaybillActive = waybillManagerService.existsActiveWaybillById(submitDto.getRfcEntity().getOldVersionWaybillId());
			if(!isOldWaybillActive){
				throw new WaybillImportException(WaybillImportException.WAYBILLRFC_ALREADY_CHANGED);
			}
			
		
		}
		/**
		 * 子母件，子件不允许导入更改
		 * @author 283250-foss-liuyi
		 */
		WaybillRelateDetailEntity  waybillRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNo(waybillNo);
		if(waybillRelateDetailEntity!=null){
			WaybillRelateDetailEntity  waybillParentRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillParentRelateDetailByWaybillNo(waybillNo);
			if(waybillParentRelateDetailEntity==null){
				throw new WaybillImportException("子母件，子件不能起草变更运单");
			}else{
				// 重置母间运单体积重量信息，信息来源于子母件关系表
				waybill.getWaybillEntity().setGoodsVolumeTotal(waybillParentRelateDetailEntity.getGoodsVolumeTotal());
				waybill.getWaybillEntity().setGoodsWeightTotal(waybillParentRelateDetailEntity.getGoodsWeightTotal());
			}
		}
		/**
		 * 校验内部员工折扣额度
		 * dp-foss-dongjialing
		 * 225131
		 */
		if(submitDto != null) {
			if((WaybillConstants.DELIVERY_PAY.equals(submitDto.getWaybillDto().getActualFreightEntity().getInternalDeliveryType()) ||
					WaybillConstants.RECIVE_PAY.equals(submitDto.getWaybillDto().getActualFreightEntity().getInternalDeliveryType()))
					&&StringUtil.isNotBlank(submitDto.getWaybillDto().getActualFreightEntity().getEmployeeNo())) {
				InempDiscountPlanEntity entity = new InempDiscountPlanEntity();
				entity.setBillTime(waybill.getWaybillEntity().getBillTime());
				entity.setActive(FossConstants.ACTIVE);
				List<InempDiscountPlanEntity> list = inempDiscountPlanService.queryInempDiscountPanByCondition(entity);
				if(CollectionUtils.isNotEmpty(list)) {
					InempDiscountPlanEntity inempDiscountPlanEntity = list.get(0);
					if (inempDiscountPlanEntity.getHighstPreferentialLimit() != null
							&& inempDiscountPlanEntity.getHighstPreferentialLimit()
									.compareTo(BigDecimal.ZERO) > 0) {
						BigDecimal amount = waybillManagerService.queryDiscountFeeByEmployeeNo(submitDto.getWaybillDto().getActualFreightEntity().getEmployeeNo(),waybill.getWaybillEntity().getBillTime());
						if (amount == null) {
							amount = BigDecimal.ZERO;
						}
						if(StringUtil.isNotBlank(waybill.getActualFreightEntity().getEmployeeNo())&& waybill.getActualFreightEntity().getEmployeeNo().equals(submitDto.getWaybillDto().getActualFreightEntity().getEmployeeNo())) {
							if(waybill.getWaybillEntity().getPromotionsFee()  != null) {
								amount = amount.subtract(waybill.getWaybillEntity().getPromotionsFee().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
							}
						}
						
						
						BigDecimal differenceValue = inempDiscountPlanEntity
								.getHighstPreferentialLimit().subtract(
										amount.divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
						if (differenceValue.compareTo(BigDecimal.ZERO) <= 0) {
							throw new WaybillValidateException(
									WaybillValidateException.EMPLOYEE_HIGHSTPREFERENTIAL_LIMIT_NOT_ENABLE);
						} 
					} else {
						throw new WaybillValidateException(
								WaybillValidateException.EMPLOYEE_HIGHSTPREFERENTIAL_LIMIT_NOT_ENABLE);
					}
				} else {
					throw new WaybillValidateException(
							WaybillValidateException.NOT_INEMP_DISCOUNT_PLAN);
				}
			}
		}
		
		return waybill;
	}
	/**
	 * 根据是否合伙人字段设置SourceBillType字段值
	 * @author 306486
	 * @param submitDto
	 * @param requestDO
	 */
	private void isPartner(String partnerBillingLogo, RequestDO requestDO) {
		if (StringUtil.isNotBlank(partnerBillingLogo)) {
			if (WaybillConstants.YES.equals(partnerBillingLogo)) {
				requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_PTP);
			} else {
				requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_CU);
			} 
		}else{
			requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_CU);
		}
	}
		
		/**
		 * 
		 * 校验运单是否允许发生变更
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-11-29 下午2:35:57
		 */
		private WaybillDto checkWaybillRfcMatched(String waybillNo, String loginOrgCode, WaybillRfcSubmitDto submitDto) {
			WaybillDto waybill = waybillManagerService.queryWaybillByNo(waybillNo);
			// 运单信息表中未找到
			if (waybill == null || waybill.getWaybillEntity() == null) {
				// 检查是否PDA未补录
				WaybillPendingEntity pendingEntity = waybillPendingDao.queryByWaybillNumber(waybillNo);
				if (pendingEntity == null) {
					/**
					 * 运单号不存在！
					 */
					throw new WaybillImportException(WaybillImportException.WAYBILL_NUMBER_NOT_EXIST);
				}
				else {
					/**
					 * 运单未补录，不能起草运单变更！
					 */
					throw new WaybillImportException(WaybillImportException.WAYBILL_NOT_ADDITIONAL_RECORDING_CAN_NOT_CHANGE);
				}
			}
			
			if("Y".equals(waybill.getWaybillEntity().getIsecs())){
					/**
					 * 悟空单，无法在FOSS起草更改单
					 */
				throw new WaybillImportException(WaybillImportException.WAYBILLRFC_IDECS_CHANGED);
			}
			
			
			//pending waybill
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybill.getWaybillEntity().getPendingType())) {
				/**
				 * 运单未补录，不能起草运单变更！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_NOT_ADDITIONAL_RECORDING_CAN_NOT_CHANGE);
			}
			// 运单状态
			if (waybill.getActualFreightEntity() == null) {
				/**
				 * 运单ActualFreight无数据，获取不到货物状态！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_ACTUAL_FREIGHT_NOT_DATA);
			}
			/**
			 * @项目：家装项目
			 * @功能：若家装项目已经交货确认，不能起草更改单
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-10-17下午13:59
			 */
			if(!StringUtils.isEmpty(waybill.getActualFreightEntity().getSpecialValueAddedServiceType())){
				String state=queryWaybillState(waybillNo);
				if(!StringUtils.isEmpty(state)&&state.equals("DE_CONFIRM")){
					throw new WaybillImportException("此特殊增值服务单号已完成交货，不可发更改！");
				}
			}
			String waybillStatus = waybill.getActualFreightEntity().getStatus();
			if (WaybillConstants.OBSOLETE.equals(waybillStatus)) {
				/**
				 * 运单已作废，不能起草运单变更！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_CANCEL_CAN_NOT_CAHNGE);
			} else if (WaybillConstants.ABORTED.equals(waybillStatus)) {
				/**
				 * 运单已中止，不能起草运单变更！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_STOP_CAN_NOT_CAHNGE);
			}
			
			//是否结清货款
			String settleStatus = waybill.getActualFreightEntity().getSettleStatus();
			
			/**
			 * 已结清
			 */
			if(FossConstants.YES.equals(settleStatus)){
				/**
				 * 已结清货款, 不能起草运单变更！
				 */
//				throw new WaybillImportException(WaybillImportException.WAYBILL_SETTLE_CLEAR);
			}
			
			
//			//对于偏线运单，能做更改的前提条件是 中转以上接口返回null。
//			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getWaybillEntity().getProductCode())){
//				ExternalBillDto billDto = new ExternalBillDto();//创建对象
//				billDto.setWaybillNo(waybillNo);
//				billDto = externalBillService.queryExternalBillByWaybillNoForChange(billDto);
//				if(billDto != null){
//					/**
//					 * 偏线运单已中转外发，不能起草运单变更！
//					 */
//					throw new WaybillImportException(WaybillImportException.WAYBILL_ALREADY_CELL_CAN_NOT_CHANGE);
//				}
//			}
			
			// 查询运单签收状态
			WaybillSignResultEntity signResultEntity = new WaybillSignResultEntity();
			signResultEntity.setActive(FossConstants.YES);
			signResultEntity.setWaybillNo(waybillNo);
			signResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signResultEntity);
			if (signResultEntity != null && (SignConstants.SIGN_STATUS_ALL.equals(signResultEntity.getSignStatus()) || SignConstants.SIGN_STATUS_PARTIAL.equals(signResultEntity.getSignStatus()))) {
				/**
				 * 货物已签收，不能起草运单变更！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_ALREADY_SIGN_CAN_NOT_CHANGE);
			}

			List<String> waybillRfcStatus = new ArrayList<String>();//创建对象
			waybillRfcStatus.add(WaybillRfcConstants.PRE_AUDIT);
			waybillRfcStatus.add(WaybillRfcConstants.PRE_ACCECPT);
			WaybillRfcEntity rfcEntity = waybillRfcDao.queryRfcEntityByWaybillId(waybill.getWaybillEntity().getId(), waybillRfcStatus);
			if (rfcEntity != null) {
				if (WaybillRfcConstants.PRE_AUDIT.equals(rfcEntity.getStatus())) {
					/**
					 * 该运单有运单变更单还未被审核，不能起草变更运单！
					 */
					throw new WaybillImportException(WaybillImportException.WAYBILL_HAVE_NOT_AUDIT_CAN_NOT_CHANGE);
				} else if (WaybillRfcConstants.PRE_ACCECPT.equals(rfcEntity.getStatus())) {
					/**
					 * 该运单有运单变更单还未被受理，不能起草变更运单！
					 */
					throw new WaybillImportException(WaybillImportException.WAYBILLRFC_HAVE_NOT_ACCEPT_CAN_NOT_CHANGE);
				}
			}

			// 非本部门运单包括 开单部门、收货部门
			if (loginOrgCode.equals(waybill.getWaybillEntity().getCreateOrgCode()) || loginOrgCode.equals(waybill.getWaybillEntity().getReceiveOrgCode())) {
				boolean isPayment = repaymentService.isExistRepayment(waybillNo);
				if (isPayment) {
					/**
					 * 该运单已进行结清货款操作，如需更改请联系到达部门进行反结清货款操作！
					 */
					throw new WaybillImportException(WaybillImportException.WAYBILLRFC_ALREADY_SETTLE_CAN_NOT_CHANGE);
				}
				
				try {
                    //灰度接口
                    RequestDO requestDO = new RequestDO();
                    requestDO.setServiceCode(WaybillRfcService.class.getName()+".checkWaybillRfcMatched");
                    requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
                    requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
                    requestDO.setSourceBillNos(waybillNo);
                    VestResponse response = null;
                    try{
                        response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
                    }catch (Exception e){
                    	throw new BusinessException(Constants.GRAY_SERVICE_EXCEPTION+e.getMessage());
                    }
                    if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
                        List<VestBatchResult> batchResults = response.getVestBatchResult();
                        VestBatchResult batchResult = batchResults.get(0);
                        if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS, batchResult.getVestSystemCode())) {
                            waybillPickupService.canChange(waybillNo);
                        }else if(StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_CUBC, batchResult.getVestSystemCode())) {
                            //DP-FOSS zhaoyiqing 343617 配合CUBC，调用接口，传单号，判断能否发更改
                            //以下代码目的是判断是否零担，是零担才调用CUBC接口校验
                            WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);
                            if(!StringUtil.equals(WaybillConstants.YES,waybillEntity.getIsExpress())){
                                validateWaybillNoForCUBC(waybillNo,WaybillConstants.CHANGE);
                            }
                        }
                    }
                } catch (SettlementException e) {
					throw new WaybillImportException(messageBundle.getMessage(e.getErrorCode(), e.getErrorArguments()));
					
				}
			}else {
				/**
				 * 非本部门运单，不能起草运单变更！
				 */
				throw new WaybillImportException(WaybillImportException.HAVE_NOT_AUTHORITY_TO_CHANGE);
			}
			/**
			 * 快递运单派送中，不能起草运单变更！
			 */
			if(null!=waybillExpressService.queryWaybillExpressByNo(waybillNo)){
				boolean isfale = loadService.ifCouldBeChangedeWaybillNo(waybillNo);
				if(!isfale){
					throw new WaybillImportException("快递货物若货物状态为派送中，则不允许发更改");
				}
			}
			/**
			 * 快递运单派送中，不能起草运单变更！
			 */
			if(null!=waybillExpressService.queryWaybillExpressByNo(waybillNo)){
				boolean isfale = loadService.ifCouldBeChangedeWaybillNo(waybillNo);
				if(!isfale){
					throw new WaybillImportException("快递货物若货物状态为派送中，则不允许发更改");
				}
			}
			boolean returnboolen= returnGoodsRequestEntityService.queryIsHandleByWayBillNo(waybillNo);
			
			if(returnboolen==true){
				throw new WaybillImportException("转寄退回工单已经同意，请至转寄退回管理界面起草更改");
			}
			if( submitDto!=null && submitDto.getRfcEntity()!=null && submitDto.getRfcEntity().getOldVersionWaybillId()!=null){
				boolean isOldWaybillActive = waybillManagerService.existsActiveWaybillById(submitDto.getRfcEntity().getOldVersionWaybillId());
				if(!isOldWaybillActive){
					throw new WaybillImportException(WaybillImportException.WAYBILLRFC_ALREADY_CHANGED);
				}
			}
			/**
			 * 子母件，子件不允许导入更改
			 * @author 283250-foss-liuyi
			 */
			WaybillRelateDetailEntity  waybillRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNo(waybillNo);
			if(waybillRelateDetailEntity!=null){
				WaybillRelateDetailEntity  waybillParentRelateDetailEntity=waybillRelateDetailEntityDao.queryWaybillParentRelateDetailByWaybillNo(waybillNo);
				if(waybillParentRelateDetailEntity==null){
					throw new WaybillImportException("子母件，子件不能起草变更运单");
				}else{
					// 重置母间运单体积重量信息，信息来源于子母件关系表
					waybill.getWaybillEntity().setGoodsVolumeTotal(waybillParentRelateDetailEntity.getGoodsVolumeTotal());
					waybill.getWaybillEntity().setGoodsWeightTotal(waybillParentRelateDetailEntity.getGoodsWeightTotal());
				}
			}
		
		/**
		 * 校验内部员工折扣额度
		 * dp-foss-dongjialing
		 * 225131
		 */
		if(submitDto != null) {
			if((WaybillConstants.DELIVERY_PAY.equals(submitDto.getWaybillDto().getActualFreightEntity().getInternalDeliveryType()) ||
					WaybillConstants.RECIVE_PAY.equals(submitDto.getWaybillDto().getActualFreightEntity().getInternalDeliveryType()))
					&&StringUtil.isNotBlank(submitDto.getWaybillDto().getActualFreightEntity().getEmployeeNo())) {
				InempDiscountPlanEntity entity = new InempDiscountPlanEntity();
				entity.setBillTime(waybill.getWaybillEntity().getBillTime());
				entity.setActive(FossConstants.ACTIVE);
				List<InempDiscountPlanEntity> list = inempDiscountPlanService.queryInempDiscountPanByCondition(entity);
				if(CollectionUtils.isNotEmpty(list)) {
					InempDiscountPlanEntity inempDiscountPlanEntity = list.get(0);
					if (inempDiscountPlanEntity.getHighstPreferentialLimit() != null
							&& inempDiscountPlanEntity.getHighstPreferentialLimit()
									.compareTo(BigDecimal.ZERO) > 0) {
						BigDecimal amount = waybillManagerService.queryDiscountFeeByEmployeeNo(submitDto.getWaybillDto().getActualFreightEntity().getEmployeeNo(),waybill.getWaybillEntity().getBillTime());
						if (amount == null) {
							amount = BigDecimal.ZERO;
						}
						if(StringUtil.isNotBlank(waybill.getActualFreightEntity().getEmployeeNo())&& waybill.getActualFreightEntity().getEmployeeNo().equals(submitDto.getWaybillDto().getActualFreightEntity().getEmployeeNo())) {
							if(waybill.getWaybillEntity().getPromotionsFee()  != null) {
								amount = amount.subtract(waybill.getWaybillEntity().getPromotionsFee().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
							}
						}
						
						
						BigDecimal differenceValue = inempDiscountPlanEntity
								.getHighstPreferentialLimit().subtract(
										amount.divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
						if (differenceValue.compareTo(BigDecimal.ZERO) <= 0) {
							throw new WaybillValidateException(
									WaybillValidateException.EMPLOYEE_HIGHSTPREFERENTIAL_LIMIT_NOT_ENABLE);
						} 
					} else {
						throw new WaybillValidateException(
								WaybillValidateException.EMPLOYEE_HIGHSTPREFERENTIAL_LIMIT_NOT_ENABLE);
					}
				} else {
					throw new WaybillValidateException(
							WaybillValidateException.NOT_INEMP_DISCOUNT_PLAN);
				}
			}
		}
		
		return waybill;
	}

	//传递运单号给CUBC同步接口，判断能否发更改或者取消终止
    private void validateWaybillNoForCUBC(String waybillNo,String method) {
        Object[] objects = validateForCUBCService.canModForCUBC(waybillNo,method);
        if(objects==null){
            throw new WaybillValidateException();
        }
        CanChangeForCUBCDto canChangeForCUBCDto = (CanChangeForCUBCDto)objects[0];
        CUBCLogEntity cubcLogEntity = (CUBCLogEntity) objects[1];
        if(cubcLogEntity!=null){
            cubcLogEntity.setWaybillNo(cubcLogEntity.getDesc2());
        }
        syncCUBCLogService.insert(cubcLogEntity);
        if(canChangeForCUBCDto==null){
            throw new WaybillValidateException("CUBC接口传入参数异常！");
        }
        if(!canChangeForCUBCDto.getResult()) {
            if(StringUtil.isEmpty(canChangeForCUBCDto.getMessage())){
                throw new WaybillValidateException("CUBC接口传入参数异常！");
            }
            throw new WaybillValidateException(canChangeForCUBCDto.getMessage());
        }
    }

    /**
	 * 
	 * 传入更改单ID，将更改单的状态改为 核销通过、核销不通过状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-30 下午2:20:22
	 */
	@Transactional
	@Override
	public void writeOffWaybillChange(List<String> waybillChangeIds, String notes, String writeoffStatus, CurrentInfo cInfo) throws WaybillRfcException {
		if (waybillChangeIds == null || waybillChangeIds.size() < 1) {
			throw new WaybillRfcException(WaybillRfcExceptionType.WAYBILLRFC_ID_LIST_NULL_ERROR_CODE);
		}
		if (notes == null) {
			throw new WaybillRfcException(WaybillRfcExceptionType.WAYBILLRFC_NOTE_NULL_ERROR_CODE);
		}

		if (writeoffStatus == null) {
			throw new WaybillRfcException(WaybillRfcExceptionType.WAYBILLRFC_WRITE_OFF_STATUS_NULL_ERROR_CODE);
		}

		ModifyWriteOffStatus modifyWriteOffStatus = new ModifyWriteOffStatus();//创建对象
		modifyWriteOffStatus.setWaybillChangIDs(waybillChangeIds);
		modifyWriteOffStatus.setWriteoffStatus(WaybillRfcConstants.NO_WRITE_OFF);

		// 用户信息
		UserEntity user = cInfo.getUser();
		if (user != null) {
			modifyWriteOffStatus.setEmp(user.getEmployee());// 设置用户
			modifyWriteOffStatus.setDepart(user.getEmployee().getDepartment());// 设置部门
		}
		modifyWriteOffStatus.setWriteOffNote(notes);
		modifyWriteOffStatus.setWriteoffStatus(writeoffStatus);// 设置核销状态
		modifyWriteOffStatus.setWriteOffTime(new Date());// 设置核销时间
		waybillRfcDao.updateWriteOffStatus(modifyWriteOffStatus);
	}

	/**
	 * 
	 * 传入更改单ID，将核销通过、核销不通过更改单设置为未核销状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-30 下午2:20:07
	 */
	@Transactional
	@Override
	public void reverseWaybillChange(List<String> waybillChangeIds, String notes, CurrentInfo cInfo) {
		if (waybillChangeIds == null || waybillChangeIds.size() < 1) {
			throw new WaybillRfcException(WaybillRfcExceptionType.WAYBILLRFC_ID_LIST_NULL_ERROR_CODE);
		}
		if (notes == null) {
			throw new WaybillRfcException(WaybillRfcExceptionType.WAYBILLRFC_NOTE_NULL_ERROR_CODE);

		}
		ModifyWriteOffStatus modifyWriteOffStatus = new ModifyWriteOffStatus();//创建对象
		modifyWriteOffStatus.setWaybillChangIDs(waybillChangeIds);
		modifyWriteOffStatus.setWriteoffStatus(WaybillRfcConstants.NO_WRITE_OFF);// 设置为未核销

		modifyWriteOffStatus.setWriteOffNote(notes);

		// 用户信息
		UserEntity user = cInfo.getUser();
		if (user != null) {
			modifyWriteOffStatus.setEmp(user.getEmployee());// 设置用户
			modifyWriteOffStatus.setDepart(user.getEmployee().getDepartment());// 设置部门
		}
		modifyWriteOffStatus.setWriteOffTime(new Date());//反核销时间
		waybillRfcDao.updateWriteOffStatus(modifyWriteOffStatus);
	}

	/**
	 * 传入运单号，判断传入的运单号是否存在未处理的更改单
	 * 
	 * @param waybillNo
	 * @return
	 */
	@Override
	public Boolean isExsitsWayBillRfc(String waybillNo) {
		if (waybillNo == null) {
			throw new WaybillRfcException(WaybillRfcExceptionType.QUERY_WAYBILLNO_NULL_ERROR_CODE);
		}

		WaybillFRcQueryByWaybillNoDto waybillFRcQueryByWaybillNoDto = new WaybillFRcQueryByWaybillNoDto();//创建对象
		waybillFRcQueryByWaybillNoDto.setPreAccecpt(WaybillRfcConstants.PRE_ACCECPT);
		waybillFRcQueryByWaybillNoDto.setPreAudit(WaybillRfcConstants.PRE_AUDIT);
		waybillFRcQueryByWaybillNoDto.setWaybillNo(waybillNo);
		String waybillNoResult = waybillRfcDao.queryWaybillRfcByWaybillNo(waybillFRcQueryByWaybillNoDto);// 看是否有返回参数
		return waybillNoResult != null ? true : false;

	}

	/**
	 * 传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	 * 
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<String> isExsitsWayBillRfcs(List<String> waybillNoList) throws WaybillRfcException {
		// 如果单号列表大于1000个，返回查询量太大异常
		if (waybillNoList != null && waybillNoList.size() > NumberConstants.NUMBER_1000) {
			throw new WaybillRfcException(WaybillRfcExceptionType.QUERY_NUMBER_TOO_GARGE_ERROR_CODE);
		}

		// 如果运单列表大小为空，返回查询运单号为空异常
		if (waybillNoList == null || waybillNoList.size() == 0) {
			throw new WaybillRfcException(WaybillRfcExceptionType.QUERY_NUMBER_NULL_ERROR_CODE);

		}

		WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto = new WaybillFRcQueryByWaybillNosDto();//创建对象
		waybillFRcQueryByWaybillNosDto.setWaybillNos(waybillNoList);
		waybillFRcQueryByWaybillNosDto.setPreAccecpt(WaybillRfcConstants.PRE_ACCECPT);
		waybillFRcQueryByWaybillNosDto.setPreAudit(WaybillRfcConstants.PRE_AUDIT);

		return waybillRfcDao.queryWaybillRfcByWaybillNos(waybillFRcQueryByWaybillNosDto);
	}

	/**
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	@Override
	public List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit) {
		if (modifyBillWriteoffDto.getWriteoffStatus() != null) {
			modifyBillWriteoffDto.setWriteoffStatus(modifyBillWriteoffDto.getWriteoffStatus().trim());
		}

		if (modifyBillWriteoffDto.getDarftOrgCode() != null) {
			modifyBillWriteoffDto.setDarftOrgCode(modifyBillWriteoffDto.getDarftOrgCode().trim());

		}

		modifyBillWriteoffDto.setStatus(WaybillRfcConstants.ACCECPT);// 只查询已受理
		return waybillRfcDao.queryModifyBillWriteoffResult(modifyBillWriteoffDto, start, limit);
	}

	@Override
	public List<HandOverAndUnloadDto> queryHandoverDetail(String waybillNo) {
		return unloadTaskService.queryHandOverAndUnloadByWayBillNo(waybillNo);
	}

	/**
	 * 更新在线更改单打印次数 +1
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Transactional
	public int updateWaybillRfcPrintTimes(String waybillNo) {

		return waybillRfcDao.updateWaybillPrintTimes(waybillNo);
	}

	/**
	 * 根据更改单ID 查询更改单实体
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-18 下午2:49:01
	 * @param id
	 * @return
	 * @see
	 */
	public WaybillRfcEntity queryRfcEntityByWaybillId(String id) {
		List<String> waybillRfcStatus = new ArrayList<String>();//创建对象
		waybillRfcStatus.add(WaybillRfcConstants.PRE_AUDIT);
		waybillRfcStatus.add(WaybillRfcConstants.PRE_ACCECPT);
		return waybillRfcDao.queryRfcEntityByWaybillId(id, waybillRfcStatus);
	}
	
	private VestBatchResult getVestResult(WaybillRfcSubmitDto submitDto,String methodName) {
		RequestDO requestDO = new RequestDO();
		requestDO.setServiceCode(WaybillRfcService.class.getName()+"."+methodName);
		requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
		requestDO.setSourceBillNos(submitDto.getRfcEntity().getWaybillNo());
		requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
		VestResponse response = new VestResponse();
		try {
			response = grayScaleService.vestAscription(requestDO);
		} catch (Exception e1) {
		    throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
		}
		//如果灰度接口异常，那么为null
		List<VestBatchResult> vestBatchResults = response.getVestBatchResult();
		//return vestBatchResults==null?new VestBatchResult():vestBatchResults.get(0);
		//return (vestBatchResults==null||vestBatchResults.isEmpty())?new VestBatchResult():vestBatchResults.get(0);
		return CollectionUtils.isEmpty(vestBatchResults)?new VestBatchResult():vestBatchResults.get(0);
	}

	/**
	 * 
	 * 更改单提交
	 * 
	 * @author 102246-foss-shaohongliang
	 * @param currentInfo
	 * @date 2012-11-19 下午2:11:12
	 */
	@Transactional
	public WaybillRfcEntity commitWaybillRfc(WaybillRfcSubmitDto submitDto, CurrentInfo currentInfo) {
				
		// 验证是否允许提交
		ReturnGoodsRequestEntity returnGoodsRequestEntity = 
				returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo(submitDto.getRfcEntity().getWaybillNo());
		if(null!=returnGoodsRequestEntity){
			
		//根据上报工单判断是否是转寄退回的货物
		if(returnGoodsRequestEntity.getReturnStatus().equals("HANDLED")){
			checkWaybillRfc(submitDto.getRfcEntity().getWaybillNo(), currentInfo.getCurrentDeptCode(),submitDto);
		}else{
		checkWaybillRfcMatched(submitDto.getRfcEntity().getWaybillNo(), currentInfo.getCurrentDeptCode(),submitDto);
		}
		}else{
			checkWaybillRfcMatched(submitDto.getRfcEntity().getWaybillNo(), currentInfo.getCurrentDeptCode(),submitDto);
		}
		
		//调用cubc灰度接口
		VestBatchResult vestResult = this.getVestResult(submitDto, "commitWaybillRfc");
		if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
			//该单已经做过折扣，并且折扣单是未确认状态，请作废折扣单在更改！
			waybillPickupService.querydiscountPayable(submitDto.getRfcEntity().getWaybillNo());
		}
		
		// 校验开单规则
		// 1、单号更改后，判断更改后单号是否已经存在
		// 2、必填项更改后，判断更改后是否为空

		fillUpExtendValue(submitDto);
		
		/**
		 * 是否修改单号，是在fillUpExtendValue之后复制，由于校验数据完整性方法需要用到该标识，需要在fillUpExtendValue之后执行
		 */
		checkDataIntegrity(submitDto);

		// 当前系统时间
		Date currentDate = new Date();

		// liding add for NCI
		// 图片开单或者集中开单时,校验是否可以发更改为付款方式为银行卡
		checkNCIPaidMehod(submitDto);
		// 更改单主表
		WaybillRfcEntity rfcEntity = submitDto.getRfcEntity();
	
		//将转运返货信息填充至更改单主表实体WaybillRfcEntity中---206860
		rfcEntity.setRfcTranferList(submitDto.getRfcTranferList());

		// 运单信息
		WaybillDto waybillDto = submitDto.getWaybillDto();
		
		//合伙人开单库存在出发部门时发生更改，若优惠券不符合要求，则把优惠券信息置为空。--zoushengli
		if(StringUtils.equals(waybillDto.getActualFreightEntity().getPartnerBillingLogo(),FossConstants.YES)
			&& WaybillRfcConstants.RECEIVE_STOCK.equals(submitDto.getStockStatus().getResult())
			&& StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getPromotionsCode())
			&& BigDecimal.ZERO.compareTo(waybillDto.getPtpWaybillDto().getCouponFeeOrg()) == 0){
				waybillDto.getWaybillEntity().setPromotionsCode("");
		}
		
		rfcEntity.setPtpWaybillDto(waybillDto.getPtpWaybillDto());
		
		//修改创建人为更改起草人
		waybillDto.getWaybillEntity().setModifyUser(currentInfo.getEmpCode());
		waybillDto.getWaybillEntity().setModifyUserCode(currentInfo.getEmpCode());
		waybillDto.getWaybillEntity().setModifyOrgCode(currentInfo.getCurrentDeptCode());
		
		//追加运单完整信息 2016年3月17日 14:24:34 葛亮亮
		waybillManagerService.appendTempWaybillAfterChanged(waybillDto, currentDate);
		// 设置新运单ID
		rfcEntity.setNewVersionWaybillId(waybillDto.getWaybillEntity().getId());
		
		//设置waybillRfcEntity是否涉及目的站变更
		// 新增更改单变更项
		List<WaybillRfcChangeDetailDto> rfcChangeDetailDtos = submitDto.getRfcChangeDetailDtos();
		for(WaybillRfcChangeDetailDto detailDto : rfcChangeDetailDtos){
			if("customerPickupOrgName".equals(detailDto.getPropertyName())){
				rfcEntity.setIsChangeDestination(FossConstants.YES);
				break;
			}
		}
		
		addWaybillRfcEntity(rfcEntity, currentInfo, currentDate);

		waybillRfcVarificationService.addWaybillRfcActionHistory(rfcEntity, currentDate);
		//add by 306486 变更明细新增分拣结果
        if(StringUtils.isNotBlank(waybillDto.getSortingResultIsChanged())&&
                waybillDto.getSortingResultIsChanged().equals(WaybillConstants.YES)) {
            //更改后的分拣结果
            String afterRfcInfo=waybillDto.getActualFreightEntity().getSortingResult();
            //更改前的分拣结果
            String beforeRfcInfo=afterRfcInfo.equals(WaybillConstants.YES)?WaybillConstants.NO:WaybillConstants.YES;
            WaybillRfcChangeDetailDto changeDetailDto = new WaybillRfcChangeDetailDto();//创建对象
            // 变更前后信息
            changeDetailDto.settSrvWaybillRfcId(rfcEntity.getId());
            changeDetailDto.setPropertyName(WaybillConstants.SORTING_RESULT);
            changeDetailDto.setRfcItems("分拣结果");
            changeDetailDto.setBeforeRfcInfo(beforeRfcInfo);
            changeDetailDto.setAfterRfcInfo(afterRfcInfo);
            changeDetailDto.setVisible(WaybillConstants.YES);
            rfcChangeDetailDtos.add(changeDetailDto);
        }
		addWaybillRfcChangeDetail(rfcChangeDetailDtos, currentInfo, currentDate, rfcEntity);
		
		//配合快递自动补码
		if(waybillExpressService.onlineDetermineIsExpressByProductCode(submitDto.getWaybillDto().getWaybillEntity().getProductCode(), submitDto.getWaybillDto().getWaybillEntity().getBillTime())){
			if(WaybillRfcConstants.INVALID.equals(submitDto.getRfcEntity().getRfcType()) ||
					WaybillRfcConstants.ABORT.equals(submitDto.getRfcEntity().getRfcType())){
				autoAddCodeByHandService.deleteAddCodeByHand(submitDto.getWaybillDto().getWaybillEntity().getWaybillNo());
			}
		}

		// 上传附件
		List<UploadVoucherDto> uploadVoucherDtos = submitDto.getUploadVoucherDtos();
		uploadVoucherFile(uploadVoucherDtos, currentInfo, currentDate, rfcEntity);

		// 更新流水号
		List<LabeledGoodChangeHistoryDto> woodenRequirementLabeledGoods = submitDto.getWoodenRequirementLabeledGoods();
		if(woodenRequirementLabeledGoods!=null && woodenRequirementLabeledGoods.size()>0){
			for (LabeledGoodChangeHistoryDto labeledGoodEntity : woodenRequirementLabeledGoods) {
				LabeledGoodChangeEntity change = new LabeledGoodChangeEntity();
				try{
					PropertyUtils.copyProperties(change, labeledGoodEntity);
				}catch(Exception e){
					LOG.error("COPY EXCEPTION", e);
				}
				change.setId(UUIDUtils.getUUID());
				//老数据
				if(FossConstants.YES.equals(labeledGoodEntity.getIsFromService())){
					if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(change.getChangeType())){
						change.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
					}
					labeledGoodChangeDao.insertSelective(change);
					//客户端新增打木架
				}else{
					if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(change.getChangeType())){
						change.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW);
					}
					//客户端新增 没有删除打木架这个动作的
					if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_DELETE.equals(change.getChangeType())){
						change.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
					}
					if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE.equals(change.getChangeType())){
						//对于客户端新增的删除记录  我们这里不操作任何动作
						continue;
					}
					labeledGoodChangeDao.insertSelective(change);
					
				}
			
			}
		}
		
		//更新转运或者是返货变更目的站信息--提交时，将运单变更ID添加至运单更改中转表,将完整数据插入数据库--206860
		updateRfcTranferRfcIdToAdd(submitDto,rfcEntity);
		//更改安装明细		
		updateInstallationEntity(submitDto);
				
		boolean needManagerAduit = isNeedManagerAduit(submitDto); 
		//是否是快递  现在根据4种运输性质来判断的 
		if(submitDto.getWaybillDto().getWaybillEntity().getProductCode().equals(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT)||
				submitDto.getWaybillDto().getWaybillEntity().getProductCode().equals(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)||
				submitDto.getWaybillDto().getWaybillEntity().getProductCode().equals(WaybillConstants.ROUND_COUPON_PACKAGE)||
				submitDto.getWaybillDto().getWaybillEntity().getProductCode().equals(WaybillConstants.PACKAGE)){
			if(!needManagerAduit){
				//自动审核
				waybillRfcVarificationService.agreeWaybillRfcCheckAuto(rfcEntity, currentInfo, currentDate);
			}else{
				// 生成系统待办消息
				pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT_WAYBILL,
						DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT_WAYBILL, null, currentInfo.getCurrentDeptCode(), rfcEntity.getWaybillNo(), rfcEntity.getId());
			}
			
			// 更改单总数（简单计数器）
			businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_TOTAL_COUNT_WAY, currentInfo);
			
			// 更改单内部数（简单计数器）
			if(WaybillRfcConstants.INSIDE_REQUIRE.equals(rfcEntity.getRfcSource())){
				businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_INTERNAL_COUNT_WAY, currentInfo);
			}
		}else{
            //DP-FOSS zhaoyiqing 343617 20161117
            //如果是零担，则校验是否可以作废或者取消
            validateCanCancelForCUBC(submitDto);

		
		if(!needManagerAduit){
			//自动审核
			waybillRfcVarificationService.agreeWaybillRfcCheckAuto(rfcEntity, currentInfo, currentDate);
		}else{

            //人工审核-校验信用额度
            //DP-FOSS zhaoyiqing 343617
            //处理新旧订单DTO
            WaybillDto newVersionDto = waybillManagerService.getWaybillDtoById(rfcEntity.getNewVersionWaybillId());
            WaybillDto oldVersionDto = waybillManagerService.getWaybillDtoById(rfcEntity.getOldVersionWaybillId());
            WaybillEntity oldWaybill = oldVersionDto.getWaybillEntity();
            WaybillEntity newWaybill = newVersionDto.getWaybillEntity();
            waybillRfcVarificationService.getBeBebtForCUBC(oldWaybill,newWaybill);


			// 生成系统待办消息
			pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT,
					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT, null, currentInfo.getCurrentDeptCode(), rfcEntity.getWaybillNo(), rfcEntity.getId());
		}
		
		// 更改单总数（简单计数器）
		businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_TOTAL_COUNT, currentInfo);
		
		// 更改单内部数（简单计数器）
		if(WaybillRfcConstants.INSIDE_REQUIRE.equals(rfcEntity.getRfcSource())){
			businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_INTERNAL_COUNT, currentInfo);
		}
		}
		
		/**
		 * @author 350909       郭倩云
		 * @date  2016-08-05 16:06:35
		 * @comment  原单返货，在转寄退回件管理菜单里返货更改提交时，则推送拒签状态(faild)给菜鸟(eventType=faild)；
		 * 只有当该运单是快递并且返货类型是退回(RETURN_BACK)的时候才需要推送
		 */
		  if( StringUtils.equals(submitDto.getWaybillDto().getWaybillEntity().getIsExpress(), WaybillConstants.YES)
			  && null!=returnGoodsRequestEntity
			  && StringUtils.equals(returnGoodsRequestEntity.getReturnType(),WaybillConstants.RETURN_BACK)){
				  addSynTrackForCaiNiao(submitDto);
			  }
		  
		return rfcEntity;
	}

    //DP-FOSS zhaoyiqing 343617 20161117
    //如果是零担，则通过同步接口校验是否可以作废或者取消
    private void validateCanCancelForCUBC(WaybillRfcSubmitDto submitDto) {
        WaybillRfcEntity rfcEntity = submitDto.getRfcEntity();
        if(WaybillRfcConstants.INVALID.equals(rfcEntity.getRfcType()) || WaybillRfcConstants.ABORT.equals(rfcEntity.getRfcType())){
            //DP-FOSS 343617 zhaoyqing 20170107 能否作废灰度接口
            String waybillNo = submitDto.getRfcEntity().getWaybillNo();
            RequestDO requestDO = new RequestDO();
            requestDO.setServiceCode(WaybillRfcService.class.getName()+".commitWaybillRfc");
            requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
            requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
            requestDO.setSourceBillNos(waybillNo);
            VestResponse response;
            try{
                response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
            }catch (Exception e){
            	LOG.info(Constants.GRAY_SERVICE_EXCEPTION+e.getMessage());
                return;
            }
            if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())) {
                List<VestBatchResult> batchResults = response.getVestBatchResult();
                VestBatchResult batchResult = batchResults.get(0);
                if (StringUtil.equals(Constants.GRAY_VESTSYSTEM_CODE_CUBC, batchResult.getVestSystemCode())) {
                    validateWaybillNoForCUBC(rfcEntity.getWaybillNo(),WaybillConstants.CANCEL);
                }
            }
        }
    }

    /**
	 * @author 350909       郭倩云
	 * @date  2016-08-05 16:06:35
	 * @comment  原单返货，在转寄退回件管理菜单里返货更改提交时，则推送拒签状态(faild)给菜鸟(eventType=faild)；
	 */
    private void addSynTrackForCaiNiao(WaybillRfcSubmitDto submitDto) {
		
		WaybillDto waybillDto = null;
		if(null!=submitDto){
			waybillDto=submitDto.getWaybillDto();
		}
		WaybillEntity waybillEntity=null;
		if(null!=waybillDto){
			waybillEntity = waybillDto.getWaybillEntity();
		}
		SynTrackingEntity synTrackingEntity=new SynTrackingEntity();
		if(null!=waybillEntity){
			//运单号
			synTrackingEntity.setWayBillNo(waybillEntity.getWaybillNo());
			//物流订单号（渠道订单号）
			synTrackingEntity.setOrderNo(waybillEntity.getOrderNo());
			//到达部门编号
			synTrackingEntity.setOrgCode(StringUtils.defaultIfBlank(waybillEntity.getCustomerPickupOrgCode(),waybillEntity.getLastLoadOrgCode()));
			//到达部门名称
			synTrackingEntity.setOrgName(StringUtils.defaultIfBlank(waybillEntity.getCustomerPickupOrgName(),waybillEntity.getLastLoadOrgName()));
			//根据提货网点查询到达部门信息
			OrgAdministrativeInfoEntity orgEntity =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillEntity.getCustomerPickupOrgCode());
			String cityName = null;
			if (null!=orgEntity&&StringUtils.isBlank(orgEntity.getCityName())) {
				//根据城市编码从t_bas_district表中查询城市编码对应的城市名字
				cityName = administrativeRegionsService.gainDistrictNameByCode(orgEntity.getCityCode());
			}
			if (StringUtils.isNotBlank(cityName)) {
				//发生城市
				synTrackingEntity.setOperateCity(cityName);
			} else {
				     //根据提货网点查询快递外部网点信息
				    OuterBranchExpressEntity outerExp = ldpAgencyDeptService.queryLdpAgencyDeptByDeptCode(waybillEntity.getCustomerPickupOrgCode());
				  //根据城市编码从t_bas_district表中查询城市编码对应的城市名字
				    if(outerExp!=null){
				    	cityName = administrativeRegionsService.gainDistrictNameByCode(outerExp.getCityCode());
				    }
				    if(StringUtils.isNotBlank(cityName)){
				    	//发生城市
				    	synTrackingEntity.setOperateCity(cityName);
				    }
				}
			//目的部门名称
			synTrackingEntity.setDestinationDeptName(StringUtils.defaultIfBlank(waybillEntity.getCustomerPickupOrgName(),waybillEntity.getLastLoadOrgName()));
		}
		//发生时间
		synTrackingEntity.setOperateTime(new Date());
		//跟踪信息描述 --需求要求写死
		synTrackingEntity.setTrackInfo("客户拒签");
		//站点类型(1:网点)
		synTrackingEntity.setOrgType(WaybillConstants.ONE);
		//事件类型--需求要求写死
		synTrackingEntity.setEventType("FAILED");
		//设置SynTrackingEntity的主键id
		synTrackingEntity.setId(UUIDUtils.getUUID());
		//调用中转的接口将拒签状态(faild)以及其他的相关信息推送给菜鸟
		pushTrackForCaiNiaoService.addSynLpsTrack(synTrackingEntity);
	}
	
	private void updateInstallationEntity(WaybillRfcSubmitDto submitDto) {
		List<InstallationEntity> installationEntityList = submitDto.getInstallationEntityList();
		InstallationEntity installation=null;
		if(CollectionUtils.isNotEmpty(installationEntityList)){
			installationService.deleteInstallationByWaybillNo(submitDto.getRfcEntity().getWaybillNo());
			for (int i = 0; i < installationEntityList.size(); i++) {
				installation=installationEntityList.get(i);
				installationService.insertInstallation(installation);
			}
		}
	}
	/**
	 * 新增转运或者是返货变更表数据记录
	 * //插入数据：运单号 原目的站 新目的站 货物范围 费用 时间 是否有效(目前插入active="N"的数据，审核受理通过时，将数据修改至active="Y")
	 * @author foss-206860
	 * */
	private void updateRfcTranferRfcIdToAdd(WaybillRfcSubmitDto submitDto,WaybillRfcEntity rfcEntity) {
		List<WaybillRfcTranferEntity> rfcTranferList = submitDto.getRfcTranferList();
		if(CollectionUtils.isNotEmpty(rfcTranferList)){
			//当list大小大于1时，说明存在累加
			if(rfcTranferList != null && rfcTranferList.size() > 1){
				for (int i = 0; i < rfcTranferList.size(); i++) {
					if(FossConstants.YES.equals(rfcTranferList.get(i).getIsSum())){
						rfcTranferList.get(i).setWaybillRfcId(rfcEntity.getId());
						//将累加后的数据添加至数据库
						waybillRfcDao.addWaybillRfcTransferEntity(rfcTranferList.get(i));
					}
				}
			}else{
				rfcTranferList.get(0).setWaybillRfcId(rfcEntity.getId());
				waybillRfcDao.addWaybillRfcTransferEntity(rfcTranferList.get(0));
			}
		}
	}

	/**
	 * 更改单提交完整性校验
	 * @param submitDto
	 */
	private void checkDataIntegrity(WaybillRfcSubmitDto submitDto) {
		if (submitDto.getRfcEntity() != null) {
			WaybillRfcEntity rfcEntity = submitDto.getRfcEntity();
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
	}


	/**
	 * 
	 * 是否需要经理审核
	 * 
	 * 1、离开开单部门库存后，涉及"预付金额""到付金额"及付款方式变更的财务更改单需要经理审核 
	 * 2、开单组更改收货部门需要经理审核 
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-27 下午2:14:15
	 */
	private boolean isNeedManagerAduit(WaybillRfcSubmitDto submitDto) {
		WaybillRfcEntity rfcEntity = submitDto.getRfcEntity();
		if(FossConstants.YES.equals(rfcEntity.getIsChangeWaybillNo())){
			return true;
		}
		//如果是中止或作废，则必须要审核受理
		if(WaybillRfcConstants.INVALID.equals(rfcEntity.getRfcType()) || WaybillRfcConstants.ABORT.equals(rfcEntity.getRfcType())){
			return true;
		}
		WaybillStockStatusDto stockStatusDto = submitDto.getStockStatus();
		String inventory = stockStatusDto.getResult();
		//在开单部门库存
		boolean isInReceiveStock = WaybillRfcConstants.RECEIVE_STOCK.equals(inventory);
		
		List<WaybillRfcChangeDetailDto> rfcChangeDetailDtos = submitDto.getRfcChangeDetailDtos();
		if(rfcChangeDetailDtos == null || rfcChangeDetailDtos.size() ==0){
			return false;
		}
		for (WaybillRfcChangeDetailDto rfcChangeDetailDto : rfcChangeDetailDtos) {
			if(rfcChangeDetailDto == null)
				continue;
			// 是否修改收货部门
			if ("receiveOrgName".equals(rfcChangeDetailDto.getPropertyName())) {
				return true;
			}
			// 是否需要财务核销
			if (!isInReceiveStock && ("paidMethod".equals(rfcChangeDetailDto.getPropertyName()) 
					|| "toPayAmount".equals(rfcChangeDetailDto.getPropertyName())
					|| "prePayAmount".equals(rfcChangeDetailDto.getPropertyName()))) {
				return true;
			}
			// 整车 by lufeifei
			if(FossConstants.YES.equals(submitDto.getWaybillDto().getWaybillEntity().getIsWholeVehicle())){
				// 货物信息更改
				// 默认自动审核
				
				// 增值业务信息更改-代收货款
				if("codAmount".equals(rfcChangeDetailDto.getPropertyName())){
					if(!isInReceiveStock){
						return true;
					}
				}
				// 计费付款信息变更
				if("paidMethod".equals(rfcChangeDetailDto.getPropertyName())){
					if(!isInReceiveStock){
						return true;
					}
				}
				
			}
		}
		return false;
	}

	/**
	 * 
	 * 填充额外数据
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-17 下午4:50:27
	 */
	private void fillUpExtendValue(WaybillRfcSubmitDto submitDto) {
		// 更改单主表
		WaybillRfcEntity rfcEntity = submitDto.getRfcEntity();
		rfcEntity.setIsChangeDestination(FossConstants.NO);
		rfcEntity.setNeedWriteOff(FossConstants.NO);
		rfcEntity.setIsFinanceChange(FossConstants.NO);
		rfcEntity.setIsChangeWaybillNo(FossConstants.NO);

		List<WaybillRfcChangeDetailDto> rfcChangeDetailDtos = submitDto.getRfcChangeDetailDtos();
		for(WaybillRfcChangeDetailDto rfcChangeDetailDto : rfcChangeDetailDtos) {
			// 是否修改运单号
			if("waybillNo".equals(rfcChangeDetailDto.getPropertyName())) {
				rfcEntity.setIsChangeWaybillNo(FossConstants.YES);
			}
			// 是否修改目的站
			if("customerPickupOrgCode".equals(rfcChangeDetailDto.getPropertyName())) {
				rfcEntity.setIsChangeDestination(FossConstants.YES);
			}
			// 是否需要财务核销
			if("paidMethod".equals(rfcChangeDetailDto.getPropertyName()) || "totalFee".equals(rfcChangeDetailDto.getPropertyName())) {
				rfcEntity.setNeedWriteOff(FossConstants.YES);
				rfcEntity.setWriteOffStatus(WaybillRfcConstants.NO_WRITE_OFF);
			}
			// 是否财务更新
			// 【非财务类信息】发货人姓名/手机/电话/地址、收货人姓名/手机/电话/地址、对内/外备注、费用说明、货物名称/包装/件数。
			if(!("deliveryCustomerMobilephone".equals(rfcChangeDetailDto.getPropertyName()) || "deliveryCustomerName".equals(rfcChangeDetailDto.getPropertyName()) || "deliveryCustomerPhone".equals(rfcChangeDetailDto.getPropertyName())
					|| "deliveryCustomerContact".equals(rfcChangeDetailDto.getPropertyName()) || "deliveryCustomerAddress".equals(rfcChangeDetailDto.getPropertyName()) || "deliveryCustomerProvCityCounty".equals(rfcChangeDetailDto.getPropertyName())
					|| "receiveCustomerMobilephone".equals(rfcChangeDetailDto.getPropertyName()) || "receiveCustomerPhone".equals(rfcChangeDetailDto.getPropertyName()) || "receiveCustomerName".equals(rfcChangeDetailDto.getPropertyName())
					|| "receiveCustomerContact".equals(rfcChangeDetailDto.getPropertyName()) || "receiveCustomerAddress".equals(rfcChangeDetailDto.getPropertyName()) || "receiveCustomerProvCityCounty".equals(rfcChangeDetailDto.getPropertyName())
					|| "outerNotes".equals(rfcChangeDetailDto.getPropertyName()) || "innerNotes".equals(rfcChangeDetailDto.getPropertyName())
					|| "goodsName".equals(rfcChangeDetailDto.getPropertyName()) || "goodsPackage".equals(rfcChangeDetailDto.getPropertyName()) 
					|| "paper".equals(rfcChangeDetailDto.getPropertyName()) || "wood".equals(rfcChangeDetailDto.getPropertyName()) 
					|| "fibre".equals(rfcChangeDetailDto.getPropertyName()) || "salver".equals(rfcChangeDetailDto.getPropertyName()) 
					|| "membrane".equals(rfcChangeDetailDto.getPropertyName()) || "otherPackage".equals(rfcChangeDetailDto.getPropertyName()) 
					|| "goodsQtyTotal".equals(rfcChangeDetailDto.getPropertyName()))) {
				rfcEntity.setIsFinanceChange(FossConstants.YES);
			}
		}
		
		//提交的运单
		WaybillEntity waybillEntity = submitDto.getWaybillDto().getWaybillEntity();
		//修改后的运输性质
		String productCode = waybillEntity.getProductCode();
		
		if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productCode) ||
				ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productCode) ||
				ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productCode) ||
				ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productCode)||
				ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productCode)|| 
				ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productCode)){
			waybillEntity.setLastLoadOrgCode(waybillEntity.getCustomerPickupOrgCode());
			waybillEntity.setLastLoadOrgName(waybillEntity.getCustomerPickupOrgName());
		}

	}

	/**
	 * 上传凭证文件
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-30 下午1:56:48
	 */
	@Transactional
	private void uploadVoucherFile(List<UploadVoucherDto> uploadVoucherDtos, CurrentInfo currentInfo, Date currentDate, WaybillRfcEntity rfcEntity) {
		WaybillRfcProofEntity proofEntity = null;
		for (UploadVoucherDto voucherDto : uploadVoucherDtos) {

			// 上传文件接口
			try {
				BASE64Decoder decoder = new BASE64Decoder();//创建对象
				if(StringUtil.isEmpty(voucherDto.getProofBytes())){
					continue;
				}
				byte[] bytes = decoder.decodeBuffer(voucherDto.getProofBytes());
				FileInfo fileInfo = fileManager.create(new ByteArrayInputStream(bytes));
				proofEntity = new WaybillRfcProofEntity();

				// 数据修改者信息
				proofEntity.setCreateDate(currentDate);
				proofEntity.setCreateUser(currentInfo.getUserName());
				proofEntity.setModifyDate(currentDate);
				proofEntity.setModifyUser(currentInfo.getUserName());

				proofEntity.setRfcProofName(voucherDto.getProofName());
				proofEntity.setRfcProofPath(fileInfo.getRelativePath());
				proofEntity.setRfcProofSize(fileInfo.getLength());
				proofEntity.setWaybillRfcId(rfcEntity.getId());

				waybillRfcProofDao.addRfcProofEntity(proofEntity);
			} catch (IOException e) {
				throw new WaybillRfcException("更改单提交失败：" + e.getMessage());
			}

		}
	}

	/**
	 * 新增更改单变更项
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-30 下午1:55:34
	 */
	@Transactional
	private void addWaybillRfcChangeDetail(List<WaybillRfcChangeDetailDto> rfcChangeDetailDtos, CurrentInfo currentInfo, Date currentDate, WaybillRfcEntity rfcEntity) {

		WaybillRfcChangeDetailEntity changeDetailEntity = null;
		for (WaybillRfcChangeDetailDto changeDetailDto : rfcChangeDetailDtos) {
			changeDetailEntity = new WaybillRfcChangeDetailEntity();//创建对象
			// 变更前后信息
			changeDetailEntity.setWaybillRfcId(rfcEntity.getId());
			changeDetailEntity.setRfcItems(changeDetailDto.getPropertyName());
			changeDetailEntity.setRfcItemsName(changeDetailDto.getRfcItems());
			changeDetailEntity.setBeforeRfcInfo(changeDetailDto.getBeforeRfcInfo());
			changeDetailEntity.setAfterRfcInfo(changeDetailDto.getAfterRfcInfo());
			changeDetailEntity.setVisiable(changeDetailDto.getVisible());

			// 数据修改者信息
			changeDetailEntity.setCreateDate(currentDate);
			changeDetailEntity.setCreateUser(currentInfo.getUserName());
			changeDetailEntity.setModifyDate(currentDate);
			changeDetailEntity.setModifyUser(currentInfo.getUserName());

			waybillRfcDao.addRfcChangeDetailEntity(changeDetailEntity);

			// 涉及到金额的变更项
			List<WaybillRfcChangeChargeEntity> rfcChangeChargeEntities = changeDetailDto.getRfcChangeChargeEntities();
			if (rfcChangeChargeEntities != null) {
				for (WaybillRfcChangeChargeEntity changeChargeEntity : rfcChangeChargeEntities) {
					changeChargeEntity.setWaybillrfcChangedetailId(changeDetailEntity.getId());

					// 数据修改者信息
					changeChargeEntity.setCreateDate(currentDate);
					changeChargeEntity.setCreateUser(currentInfo.getUserName());
					changeChargeEntity.setModifyDate(currentDate);
					changeChargeEntity.setModifyUser(currentInfo.getUserName());

					waybillRfcDao.addRfcChangeChargeEntity(changeChargeEntity);
				}
			}
		}
	}

	/**
	 * 新增更改单主表
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-30 下午1:49:25
	 */
	@Transactional
	private WaybillRfcEntity addWaybillRfcEntity(WaybillRfcEntity rfcEntity, CurrentInfo currentInfo, Date currentDate) {
		if (rfcEntity != null) {
			// 起草人信息
			rfcEntity.setDraftOrgName(currentInfo.getCurrentDeptName());//起草部门名称
			rfcEntity.setDraftOrgCode(currentInfo.getCurrentDeptCode());//起草部门编码
			rfcEntity.setDrafter(currentInfo.getEmpName());//设置起草人名字
			rfcEntity.setDrafterCode(currentInfo.getEmpCode());//设置起草人编码
			rfcEntity.setDraftTime(currentDate);//起草时间

			rfcEntity.setStatus(WaybillRfcConstants.PRE_AUDIT);//更改单状态 -- 待审核、起草

			// 最终操作人信息
			rfcEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());//操作部门编码
			rfcEntity.setOperateOrgName(currentInfo.getCurrentDeptName());//操作部门名称
			rfcEntity.setOperateTime(currentDate);//设置操作时间
			rfcEntity.setOperator(currentInfo.getEmpName());//设置操作人名字
			rfcEntity.setOperatorCode(currentInfo.getEmpCode());//设置操作人编码

			// 数据修改者信息
			rfcEntity.setCreateDate(currentDate);//创建时间
			rfcEntity.setCreateUser(currentInfo.getUserName());//创建人
			rfcEntity.setModifyDate(currentDate);//修改时间
			rfcEntity.setModifyUser(currentInfo.getUserName());//修改人
		}
		waybillRfcDao.addWaybillRfcEntity(rfcEntity);//新增更改单主表
		return rfcEntity;
	}

	/**
	 * 
	 * <p>
	 * 根据更改单ID 查询更改单实体
	 * </p>
	 * 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午9:37:01
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#selectByPrimaryKey(java.lang.String)
	 */
	public WaybillRfcEntity selectByPrimaryKey(String id) {
		return waybillRfcDao.selectByPrimaryKey(id);
	}
	/**
	 * 
	 * 查询未审核总条数
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-7 上午9:25:41
	 */
	public long queryModifyBillWriteoffResultTotalNumber(ModifyBillWriteoffDto modifyBillWriteoffDto) {
		modifyBillWriteoffDto.setStatus(WaybillRfcConstants.ACCECPT);// 只查询已受理
		return waybillRfcDao.queryModifyBillWriteoffResultTotalNumber(modifyBillWriteoffDto);
	}
	
	

	/**
	 * 
	 * 快递接口
	 * 导出查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-27
	 */
	public List<ModifyBillWriteoffResultDto> queryExpressModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto) {
		if (modifyBillWriteoffDto.getWriteoffStatus() != null) {
			modifyBillWriteoffDto.setWriteoffStatus(modifyBillWriteoffDto.getWriteoffStatus().trim());
		}
	
		if (modifyBillWriteoffDto.getDarftOrgCode() != null) {
			modifyBillWriteoffDto.setDarftOrgCode(modifyBillWriteoffDto.getDarftOrgCode().trim());
	
		}
	
		modifyBillWriteoffDto.setStatus(WaybillRfcConstants.ACCECPT);// 只查询已受理
		return waybillRfcDao.queryExpressModifyBillWriteoffResult(modifyBillWriteoffDto);
	
	}






	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号、产品集合）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-26
	 */
	@Override
	public Map<Long,List<ModifyBillWriteoffResultDto>> queryExpressModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit) {
		if (modifyBillWriteoffDto.getWriteoffStatus() != null) {
			modifyBillWriteoffDto.setWriteoffStatus(modifyBillWriteoffDto.getWriteoffStatus().trim());
		}

		if (modifyBillWriteoffDto.getDarftOrgCode() != null) {
			modifyBillWriteoffDto.setDarftOrgCode(modifyBillWriteoffDto.getDarftOrgCode().trim());
		}

		modifyBillWriteoffDto.setStatus(WaybillRfcConstants.ACCECPT);// 只查询已受理
		List<ModifyBillWriteoffResultDto> modifyBillWriteoffResultList = waybillRfcDao.queryExpressModifyBillWriteoffResult(modifyBillWriteoffDto, start, limit);
		List<ModifyBillWriteoffResultDto> modifyBillWriteoffResultListReturn = new ArrayList<ModifyBillWriteoffResultDto>();
		BigDecimal oldPayAmount = null;
		BigDecimal newPayAmount = null;
		BigDecimal changeAmount = null;
		int count = 0;
		Map<Long,List<ModifyBillWriteoffResultDto>> modifyBillWriteoffResultMap = new HashMap<Long,List<ModifyBillWriteoffResultDto>>();
		for(ModifyBillWriteoffResultDto modifyBillWriteoffResultDto : modifyBillWriteoffResultList){
			oldPayAmount = (modifyBillWriteoffResultDto.getOldPrePayAmount() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getOldPrePayAmount()).add(modifyBillWriteoffResultDto.getOldToPayAmount() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getOldToPayAmount());
			newPayAmount = (modifyBillWriteoffResultDto.getNewPrePayAmount() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getNewPrePayAmount()).add(modifyBillWriteoffResultDto.getNewToPayAmount() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getNewToPayAmount());
			//从数据库中查询运单减少的金额
			changeAmount = oldPayAmount.subtract(newPayAmount);
			/**
			 * @author 218392 zhangyongxue
			 * @date 2016-06-21 21:20:15
			 * VTS整车项目 运单发生更改，减少金额大于等于300，并且满足以下4个其中一个就会进入更改单核销报表界面
			 * (1)更改前送货费 > 更改后送货费
			 * (2)更改前发货客户编码 和 发货后客户编码不同
			 * (3)更改前包装费 > 更改后包装费
			 * (4)更改前运输性质  更改后运输性质 不相等
			 */
			//系统中配置的减少金额，代码中
			BigDecimal systemChangeAmount = modifyBillWriteoffDto.getChangeAmount();
			//老的送货费
			BigDecimal deliveryGoodsFeeOld = modifyBillWriteoffResultDto.getDeliveryGoodsFeeOld() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getDeliveryGoodsFeeOld();
			//新的送货费
			BigDecimal deliveryGoodsFeeNew = modifyBillWriteoffResultDto.getDeliveryGoodsFeeNew() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getDeliveryGoodsFeeNew();
			//老的包装费
			BigDecimal packageFeeOld = modifyBillWriteoffResultDto.getPackageFeeOld() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getPackageFeeOld();
			//新的包装费
		    BigDecimal packageFeeNew = 	modifyBillWriteoffResultDto.getPackageFeeNew() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getPackageFeeNew();
			
			if(changeAmount.compareTo(systemChangeAmount) >= 0){
				if(deliveryGoodsFeeOld.compareTo(deliveryGoodsFeeNew) == 1 || 
						!StringUtils.equals(modifyBillWriteoffResultDto.getDeliveryCustomerCodeOld(), modifyBillWriteoffResultDto.getDeliveryCustomerCodeNew()) ||
						packageFeeOld.compareTo(packageFeeNew) == 1||
						!StringUtils.equals(modifyBillWriteoffResultDto.getProductCodeOld(), modifyBillWriteoffResultDto.getProductCodeNew())){
					
					++ count;
					if(count > start && count <= (start + limit)){
						modifyBillWriteoffResultListReturn.add(modifyBillWriteoffResultDto);
					}
				}
			}
			
		    //注释掉的是之前老的代码，这块断点打进来有问题
//			if(modifyBillWriteoffDto.getChangeAmount() != null &&  changeAmount.compareTo(modifyBillWriteoffDto.getChangeAmount()) != -1){
//				if((modifyBillWriteoffResultDto.getDeliveryGoodsFeeOld() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getDeliveryGoodsFeeOld()).compareTo(modifyBillWriteoffResultDto.getDeliveryGoodsFeeNew() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getDeliveryGoodsFeeNew()) == 1
//					|| !StringUtils.equals(modifyBillWriteoffResultDto.getDeliveryCustomerCodeOld(), modifyBillWriteoffResultDto.getDeliveryCustomerCodeNew())
//					|| (modifyBillWriteoffResultDto.getPackageFeeOld() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getPackageFeeOld()).compareTo(modifyBillWriteoffResultDto.getPackageFeeNew() == null ? new BigDecimal(0) : modifyBillWriteoffResultDto.getPackageFeeNew()) == 1
//					|| !StringUtils.equals(modifyBillWriteoffResultDto.getProductCodeOld(), modifyBillWriteoffResultDto.getProductCodeNew()))
//				{
//					++ count;
//					if(count > start && count <= (start + limit)){
//						modifyBillWriteoffResultListReturn.add(modifyBillWriteoffResultDto);
//					}
//				}
//			}
		}
		modifyBillWriteoffResultMap.put(new Long(count), modifyBillWriteoffResultListReturn);
		return modifyBillWriteoffResultMap;
	}
	
	
	
	@Override
	public long queryExpressModifyBillWriteoffResultCount(ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit) {
		if (modifyBillWriteoffDto.getWriteoffStatus() != null) {
			modifyBillWriteoffDto.setWriteoffStatus(modifyBillWriteoffDto.getWriteoffStatus().trim());
		}

		if (modifyBillWriteoffDto.getDarftOrgCode() != null) {
			modifyBillWriteoffDto.setDarftOrgCode(modifyBillWriteoffDto.getDarftOrgCode().trim());

		}

		modifyBillWriteoffDto.setStatus(WaybillRfcConstants.ACCECPT);// 只查询已受理
		return waybillRfcDao.queryExpressModifyBillWriteoffResultCount(modifyBillWriteoffDto, start, limit);
	}
	


    /**
     * 获取当前登录部门，如果是派送中心，查询对应外场的待办
     * @return
     */
    private String getCurrentDeptCode(String deptCode) {
    	SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(deptCode);
    	if(saleDepartmentEntity != null){
    		if(saleDepartmentEntity.checkStation()){
    			String transferCenter = saleDepartmentEntity.getTransferCenter();
    			if(StringUtil.isNotEmpty(transferCenter)){
    				deptCode = transferCenter;
    			}
    		}
    	}else{
    		//非营业部找到它上级所属外场的编码
    		List<String> bizTypes = new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
    		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(deptCode, bizTypes);
    		if(orgAdministrativeInfoEntity != null){
    			deptCode = orgAdministrativeInfoEntity.getCode();
    		}
    	}
		return deptCode;
	}
    
	/**
	 * 装车环节查询该单在当前部门是否有待办
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.service.
	 *       IWaybillRfcService#queryTodoWhenLoadTruck(java.lang.String,
	 *       java.lang.String, java.lang.String)
	 */
	public List<String> queryTodoWhenLoadTruck(String waybillNo, String serialNo, String currentOrgCode) {
		//当前部门转换为实际有待办部门（营业部、外场）
		currentOrgCode = getCurrentDeptCode(currentOrgCode);
		
		List<String> todoList = null;

		if (StringUtils.isEmpty(waybillNo)) {
			/**
			 * 请检查运单号是否输入
			 */
			throw new WaybillRfcException(WaybillRfcException.CHECK_WAYBILL_NUMBER_ALREADY_INPUT);
		} else if (StringUtils.isEmpty(serialNo)) {
			/**
			 * 流水号不能为空
			 */
			throw new WaybillRfcException(WaybillRfcException.WAYBILL_SERIAL_NUMBER_CAN_NOT_NULL);
		} else if (StringUtils.isEmpty(currentOrgCode)) {
			/**
			 * 部门编码不能为空
			 */
			throw new WaybillRfcException(WaybillRfcException.ORG_NUMBER_CAN_NOT_NULL);
		} else {
			Map<String, Object> args = new HashMap<String, Object>();//创建对象
			args.put("serialNo", serialNo);
			args.put("currentOrgCode", currentOrgCode);
			args.put("waybillNo", waybillNo);
			todoList = labeledGoodTodoDao.queryTodoWhenLoadTruck(args);
		}
		return todoList;
	}
	
	/**
	 * 为中转新增卸车扫描时候待办的提醒，不管是否已经打印，都需要提醒
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-2 17:37:08
	 */
	@Override
	public List<String> queryTodoWhenUnloadTruck(String waybillNo, String serialNo, String currentOrgCode) {
		//当前部门转换为实际有待办部门（营业部、外场）
		currentOrgCode = getCurrentDeptCode(currentOrgCode);
		
		List<String> todoList = null;

		if (StringUtils.isEmpty(waybillNo)) {
			/**
			 * 请检查运单号是否输入
			 */
			throw new WaybillRfcException(WaybillRfcException.CHECK_WAYBILL_NUMBER_ALREADY_INPUT);
		} else if (StringUtils.isEmpty(serialNo)) {
			/**
			 * 流水号不能为空
			 */
			throw new WaybillRfcException(WaybillRfcException.WAYBILL_SERIAL_NUMBER_CAN_NOT_NULL);
		} else if (StringUtils.isEmpty(currentOrgCode)) {
			/**
			 * 部门编码不能为空
			 */
			throw new WaybillRfcException(WaybillRfcException.ORG_NUMBER_CAN_NOT_NULL);
		} else {
			Map<String, Object> args = new HashMap<String, Object>();//创建对象
			args.put("serialNo", serialNo);
			args.put("currentOrgCode", currentOrgCode);
			args.put("waybillNo", waybillNo);
			todoList = labeledGoodTodoDao.queryTodoWhenUnloadTruck(args);
		}
		return todoList;
	}
	
	/**
	 * 当卸车少货的时候需要重置待办
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-2 18:58:20
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @return
	 */
	@Override
	public ResultDto resetTodoWhenLost(String waybillNo, String serialNo, String currentOrgCode){
		ResultDto resultDto = new ResultDto();
		//当前部门转换为实际有待办部门（营业部、外场）
		List<String> orgCodeList = getTopOrgCodeList(currentOrgCode);
		List<String> todoList = labeledGoodTodoDao.queryTodoActionWhenLost(waybillNo, serialNo, orgCodeList);
		if(todoList != null && todoList.size() > 0){
			if (todoList.size() < FossConstants.ORACLE_MAX_IN_SIZE) {
				labeledGoodTodoDao.resetTodoByIdList(todoList);
			} else {
				List<List<String>> idsLists = com.deppon.foss.util.CollectionUtils.splitListBySize(todoList, FossConstants.ORACLE_MAX_IN_SIZE);
				for (List<String> lists : idsLists) {
					labeledGoodTodoDao.resetTodoByIdList(lists);
				}
			}
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("处理完毕");
		}else{
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("没有查询到相应的数据");
		}
		return resultDto;		
	}
	
	/**
	 * 当少货找到的时候，不能按照卸车少货的情况去重置，此时你不能根据当前部门去重置
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @return
	 */
	@Override
	public ResultDto resetTodoWhenGoodFound(String waybillNo, String serialNo){
		ResultDto resultDto = new ResultDto();
		//当前部门转换为实际有待办部门（营业部、外场）
		List<LabeledGoodTodoEntity> todoList = labeledGoodTodoDao.queryNeedResetTodoBySerialNo(waybillNo, serialNo);
		if(todoList != null && todoList.size() > 0){
			LabeledGoodTodoEntity entity = todoList.get(0);
			if(entity != null && FossConstants.NO.equals(entity.getStatus())){
				List<String> ids = new ArrayList<String>();
				ids.add(entity.getId());
				labeledGoodTodoDao.resetTodoByIdList(ids);
				resultDto.setCode(ResultDto.SUCCESS);
				resultDto.setMsg("已经成功重置");
			}
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("不需要重置");
		}else{
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("没有查询到相应的数据");
		}
		return resultDto;		
	}
	
	/**
	 * 获取当前组织的顶级组织
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-2 18:59:31
	 * @return
	 */
	private List<String> getTopOrgCodeList(String currentOrgCode){
		List<String> orgCodeList = new ArrayList<String>();
		orgCodeList.add(currentOrgCode);
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(currentOrgCode);
    	if(saleDepartmentEntity != null){
    		//如果是派送中心，查询对应外场的待办
    		if(FossConstants.YES.equals(saleDepartmentEntity.getStation())){
    			orgCodeList.add(saleDepartmentEntity.getTransferCenter());    			
    		}
    	}else{
    		//非营业部找到它上级所属外场的编码
    		List<String> bizTypes = new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
    		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrgCode, bizTypes);
    		if(orgAdministrativeInfoEntity != null){
    			orgCodeList.add(orgAdministrativeInfoEntity.getCode());
    		}
    	}
    	return orgCodeList;
	}
	

	/**
	 * 卸车进行的代办飘逸	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @param lastOrgCode
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#queryTodoWhenDumpTruck(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> queryTodoWhenDumpTruck(String waybillNo, String serialNo, String currentOrgCode, String lastOrgCode) {
		return updateTodoWhenCommons(waybillNo, serialNo, currentOrgCode);
	}
	
	/**
	 * 装车进行的代办飘逸
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-6 11:37:49
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @param lastOrgCode
	 * @return
	 */
	@Override
	public List<String> queryTodoWhenLoad(String waybillNo, String serialNo, String currentOrgCode, String lastOrgCode) {
		return updateTodoWhenCommons(waybillNo,serialNo,lastOrgCode);
		
	}
	
	/**
	 * @功能 对相应的代办进行漂移
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-5 20:17:45
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @param lastOrgCode
	 */
	public List<String> updateTodoWhenCommons(String waybillNo, String serialNo, String currentOrgCode) {
		// 当前部门转换为实际有待办部门（营业部、外场）
		currentOrgCode = getCurrentDeptCode(currentOrgCode);
		Map<String, Object> args = new HashMap<String, Object>();// 创建对象
		args.put("waybillNo", waybillNo);
		args.put("serialNo", serialNo);
		LOG.debug("开始待办漂移：运单号" + waybillNo + ",流水号" + serialNo + ",待漂移部门"
				+ currentOrgCode);
		List<String> lastOrgTodo = new ArrayList<String>();
		// 查找上一部门所有未打印的待办
		List<LabeledGoodTodoEntity> actuatingNodeEntityList = labeledGoodTodoDao
				.queryActuatingNode(args);
		if(CollectionUtils.isEmpty(actuatingNodeEntityList)){
			return null;
			//throw new WaybillRfcChangeException("查询到对应流水号的代办为空");
		}
		//需要重置的集合
		List<String> needResetList = new ArrayList<String>();
		LOG.debug("找到等待漂移数据：" + (actuatingNodeEntityList != null ? actuatingNodeEntityList.size() : ""));
		
		//更新最新的代办
		LabeledGoodTodoEntity entity = new LabeledGoodTodoEntity();
		
		if(!CollectionUtils.isEmpty(actuatingNodeEntityList)){	
			entity = actuatingNodeEntityList.get(0);			
			String oldOrgCode = entity.getHandleOrgCode();
			//获取需要重置代办的数据
			if(actuatingNodeEntityList != null && actuatingNodeEntityList.size() > 1){
				for(int i=1;i<actuatingNodeEntityList.size();i++){
					needResetList.add(actuatingNodeEntityList.get(i).getId());
					lastOrgTodo.add(actuatingNodeEntityList.get(i).getStatus());
				}
			}
			
			//如果当前Code为当前部门则不进行飘逸,对重复代办进行重置
			if (oldOrgCode.equals(currentOrgCode)) {
				resetTodoStatus(needResetList);
				return lastOrgTodo;
			}
		}		
		
		//对最后一个代办进行飘逸,开始----------------------
		entity.setStatus(null);
		lastOrgTodo.add(entity.getStatus());
		entity.setHandleOrgCode(currentOrgCode);
		entity.setRemindTime(new Date());
		entity.setExceptionMsg(FossConstants.NO);
		String name = null;
		try {
			name = orgAdministrativeInfoService
					.queryCommonNameByCommonCode(currentOrgCode);
		} catch (Exception e) {
			LOG.error("queryOrgName Exception", e);
		}
		entity.setHandleOrgName(name);
		labeledGoodTodoDao.updateLabeledGoodTodoEntityById(entity);
		lastOrgTodo.add(entity.getStatus());
		//更新成功-----------------------
		
		if(!CollectionUtils.isEmpty(needResetList)){
			//重置重复生成代办数据
			resetTodoStatus(needResetList);
		}
		return lastOrgTodo;
	}
	
	/**
	 * @功能 重置重复生成代办的数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-6 11:20:09
	 * @param needResetList
	 */
	private void resetTodoStatus(List<String> needResetList) {
		if(!CollectionUtils.isEmpty(needResetList)){
			//重置代办数据，不让用户在操作，减少标签打印次数
			Map<String, Object> batchMap = new HashMap<String, Object>();// 创建对象
//			batchMap.put("isPrinted", 'R');
			//batchMap.put("serialNo", serialNo);
			batchMap.put("needResetList", needResetList);
			labeledGoodTodoDao.updateLabeledGoodTodoEntityBatchById(batchMap);
		}
	}

	/**
	 * 传入日期、司机工号、车牌号，查询PDA接货记录
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param queryDate 查询日期
	 * @param vehicleNo 车牌号
	 * @param driverCode 司机工号
	 * @return PdaReceiveGoodsDto
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#queryPdaReceiveGoodsDto(java.util.Date,
	 *      java.lang.String, java.lang.String)
	 */
	public List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDto(Date queryDate, String driverCode) {
		// 先从pending
		Map<String, Object> args = new HashMap<String, Object>();//创建对象
		args.put("queryDate", queryDate);
		args.put("driverCode", driverCode);
		args.put("pendingType", WaybillConstants.PDA_ACTIVE);
		//根据BUG-54438 进行修复 查有效的最新的运单信息
		/*List<PdaReceiveGoodsDto> pdaPendingReceiveList = waybillPendingDao.queryPdaReceiveGoodsDto(args);*/
		List<PdaReceiveGoodsDto> pdaWaybillReceiveList = waybillPendingDao.queryPdaWaybillReceiveGoodsDto(args);
		List<PdaReceiveGoodsDto> result = new ArrayList<PdaReceiveGoodsDto>();//创建对象
		/*result.addAll(pdaPendingReceiveList);*/
		result.addAll(pdaWaybillReceiveList);
		return result;
	}
	/**
	 * 导出查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	public List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto) {
		if (modifyBillWriteoffDto.getWriteoffStatus() != null) {
			modifyBillWriteoffDto.setWriteoffStatus(modifyBillWriteoffDto.getWriteoffStatus().trim());
		}

		if (modifyBillWriteoffDto.getDarftOrgCode() != null) {
			modifyBillWriteoffDto.setDarftOrgCode(modifyBillWriteoffDto.getDarftOrgCode().trim());

		}

		modifyBillWriteoffDto.setStatus(WaybillRfcConstants.ACCECPT);// 只查询已受理
		return waybillRfcDao.queryModifyBillWriteoffResult(modifyBillWriteoffDto);

	}

	/**
	 * 
	 * <p>
	 * 是否自动受理
	 * </p>
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-20 上午11:22:31
	 * @param waybillRfcNo
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#updateIsLabourHandle(java.lang.String)
	 */
	@Transactional
	public void updateIsLabourHandle(String waybillRfcNo, String isLabourHandle) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillRfcNo", waybillRfcNo);
		parms.put("isLabourHandle", isLabourHandle);
		waybillRfcDao.updateIsLabourHandle(parms);
	}

	/**
	 * 根据查询条件查询执行计划
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	public List<WaybillRfcChangeDetailEntity> queryRfcChangeDetail(String waybillRfcId) {
		return waybillRfcDao.queryRfcChangeDetail(waybillRfcId);
	}

	/**
	 * 
	 * 查询变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	public List<LabeledGoodTodoEntity> queryTodoByWaybillRfcId(String waybillRfcId) {
		return labeledGoodTodoDao.queryTodoByWaybillRfcId(waybillRfcId);
	}

	/**
	 * 
	 * <p>
	 * 取库存部门，下一库存部门
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-12-14 上午11:25:57
	 * @param waybillRfc
	 * @param labeledGoodEntity
	 * @param orgCode
	 * @return
	 * @see
	 */
	public String getOrgCodeStr(String waybillNo, String serialNo) {
		StringBuffer buffer = new StringBuffer();//创建对象
		List<PathDetailEntity> pathDetailEntityList = calculateTransportPathService.queryTotalPath(waybillNo, serialNo);
		for (PathDetailEntity pathDetailEntity : pathDetailEntityList) {
			// 货物在出发部门，出发部门开始存入操作节点
			if (TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE.equals(pathDetailEntity.getArriveOrLeave())) {
				// 出发部门设置到当前库存部门，下一到达部门设置到下一库存部门
				String orgiOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getOrigOrgCode());
				if (orgiOrgName != null) {
					buffer.append(orgiOrgName);
				}
				buffer.append(",");
				String destOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getObjectiveOrgCode());
				if (destOrgName != null) {
					buffer.append(destOrgName);
				}
				break;
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * 设置changeNodeDto的到达部门、下一到达部门、可执行节点
	 * 
	 * @param waybillNo 运单号
	 * @param serialNo 序列号
	 * @param actuatingNode 执行信息
	 * @param changeNodeDto 列表信息
	 * 
	 * @return 未添加最后到达部门的走货路径
	 * @author ibm-wangfei
	 * @date Mar 25, 2013 4:40:36 PM
	 */
	private String setChangeNodeDtoInfo(String waybillNo, String serialNo, String actuatingNode, ChangeNodeDto changeNodeDto) {
		List<PathDetailEntity> pathDetailEntityList = calculateTransportPathService.queryTotalPath(waybillNo, serialNo);
		StringBuffer path = new StringBuffer();//创建对象
		//对集合进行非空判断
		if (CollectionUtils.isEmpty(pathDetailEntityList)) {
			return path.toString();
		}
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = FossUserContext.getCurrentDept();//获得当前登录人部门
		
		actuatingNode=addActuatingNode(pathDetailEntityList,orgAdministrativeInfoEntity.getCode());
		for (PathDetailEntity pathDetailEntity : pathDetailEntityList) {
			// 货物在出发部门，出发部门开始存入操作节点
			if (TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE.equals(pathDetailEntity.getArriveOrLeave())) {
				// 出发部门设置到当前库存部门，下一到达部门设置到下一库存部门
				// 库存部门
				changeNodeDto.setOrigOrgCode(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getOrigOrgCode()));
				// 下一库存部门
				changeNodeDto.setObjectiveOrgCode(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getObjectiveOrgCode()));
				// 设置变更执行节点
				if (StringUtil.isNotEmpty(actuatingNode)) {
					if (actuatingNode.indexOf(pathDetailEntity.getOrigOrgCode()) != -1) {
						changeNodeDto.setActuatingNode(actuatingNode.substring(actuatingNode.indexOf(pathDetailEntity.getOrigOrgCode())));
					} else {
						changeNodeDto.setActuatingNode(actuatingNode);
					}
				}
				break;
			}
		}
		
		// 设置走货路径
		for (int i = 0; i < pathDetailEntityList.size(); i++) {
			String orgCode = ((PathDetailEntity) pathDetailEntityList.get(i)).getOrigOrgCode();
			String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode);
			path.append(orgName);
			if (i != pathDetailEntityList.size() - 1) {//在一直倒数第二个的时候  我们都需要加上箭头符号
				path.append("->");//拼装名称
			}
		}
		return path.toString();
	}
	
	
	
	/**
	 * 
	 * <p>根据走货路径生成待办操作节点</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-14 上午11:25:57
	 * @param waybillRfc
	 * @param labeledGoodEntity
	 * @param orgCode
	 * @return
	 * @see
	 */
	private String addActuatingNode(List<PathDetailEntity> pathDetailEntityList,String orgCode){
		/*List<PathDetailEntity> pathDetailEntityList = null;
		try {
			pathDetailEntityList = calculateTransportPathService.queryTotalPath(waybillRfc.getWaybillNo(), labeledGoodEntity.getSerialNo());
		} catch (TfrBusinessException e) {
			//新增件数没有走货路径
			return orgCode;
		}*/
		StringBuffer actuatingNode = new StringBuffer();
		for(PathDetailEntity pathDetailEntity : pathDetailEntityList){
			//找到当前部门为出发部门
			if(orgCode.equals(pathDetailEntity.getOrigOrgCode())){
    			actuatingNode.append(pathDetailEntity.getOrigOrgCode());
			}
    		//找到新的起点后，保存之后的全部走货路径
			if(actuatingNode.length()>0){
				actuatingNode.append(",").append(pathDetailEntity.getObjectiveOrgCode());
			}
		}
		// 走货路径上未找到当前部门直接加入执行节点
		if(actuatingNode.length()==0){
			actuatingNode.append(orgCode);
		}
		return actuatingNode.toString();
	}
	
	
	
	

	/**
	 * 查询货物全部路径
	 * 
	 * @author foss-yangtong
	 * @date 2012-12-6 下午4:12:20
	 * @see
	 */
	public String queryFreightRoute(String waybillNo, String serialNo) {
		List<PathDetailEntity> pathDetailList = calculateTransportPathService.queryTotalPath(waybillNo, serialNo);
		StringBuffer path = new StringBuffer();//创建对象
		//对集合进行非空判断
		if (CollectionUtils.isNotEmpty(pathDetailList)) {
			for (int i = 0; i < pathDetailList.size(); i++) {
				String orgCode = ((PathDetailEntity) pathDetailList.get(i)).getOrigOrgCode();
				String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode);
				path.append(orgName);
				if (i != pathDetailList.size() - 1) {//在一直倒数第二个的时候  我们都需要加上箭头符号
					path.append("->");//拼装名称
				}
			}
		}
		return path.toString();
	}

	/**
	 * 取执行节点列表
	 * 
	 * @author foss-yangtong
	 * @date 2012-12-6 下午4:12:20
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OrgAdministrativeInfoDto> getExeNodes(List list) {
		List orgList = null;
		if (list != null && list.size() > 0) {
			orgList = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				String orgCode = (String) list.get(i);//得到循环对象
				String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode);//查询
				OrgAdministrativeInfoDto orgInfo = new OrgAdministrativeInfoDto();//创建对象
				if (orgName != null) {//组织名称不是null
					orgInfo.setName(orgName);//设置组织名称
				} else {
					orgInfo.setName("");//设置组织名称为""
				}
				orgInfo.setCode(orgCode);//设置code
				orgList.add(orgInfo);//加入list
			}
		}
		return orgList;
	}

	/**
	 * 
	 * 批量修改变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	public int updateBatchNodes(List<ChangeNodeDto> list) {
		List<ChangeNodeDto> changeNodeDtoList = new ArrayList<ChangeNodeDto>();//创建对象
		for (ChangeNodeDto dto : list) {//循环
			// 验证是否在库
			Long count = labeledGoodTodoDao.queryOutStock(dto);
			if (count > 0) {
				throw new AdjustPlanException("该件货已出库，请重新选择变更执行节点部门！");
			}
			// 验证该流水号是否PDA建立装车任务
			count = labeledGoodTodoDao.getTotalCountForStock(dto);
			if (count > 0) {
				throw new AdjustPlanException("该件货已扫描装车，请重新选择变更执行节点部门！");
			}
			String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(dto.getExeNode());// 查询名称
			if (orgName != null) {//名称不是空
				dto.setExeNodeName(orgName);//设置名称
			} else {
				dto.setExeNodeName("");//设置空
			}
			changeNodeDtoList.add(dto);//加入list
		}
		return labeledGoodTodoDao.updateBatchNodes(changeNodeDtoList);//更新数据库
	}

	/**
	 * 
	 * 查询变更节点
	 * 
	 * @param waybillRfcId
	 * @param adjustPlanResultDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 17, 2013 2:52:45 PM
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#queryTodoByWaybillRfcInfo(java.lang.String,
	 *      java.lang.String)
	 */
	public List<ChangeNodeDto> queryTodoByWaybillRfcInfo(String waybillRfcId, AdjustPlanResultDto adjustPlanResultDto) {
		List<LabeledGoodTodoEntity> todoList = queryTodoByWaybillRfcId(waybillRfcId);//查询
		List<ChangeNodeDto> changeNodeDtoList = new ArrayList<ChangeNodeDto>();//创建对象
		if (CollectionUtils.isNotEmpty(todoList)) {
			ChangeNodeDto changeNodeDto = null;//dto对象
			String freightRoute = "";//走货路劲字符串
			String currOrgCode = FossUserContextHelper.getOrgCode();
			for (LabeledGoodTodoEntity entity : todoList) {//循环
				
				if(StringUtils.isEmpty(entity.getHandleOrgCode())
					|| !FossConstants.YES.equals(entity.getStatus())
					|| FossConstants.YES.equals(entity.getPrinted())
						){
					continue;
				}
				
				changeNodeDto = new ChangeNodeDto();//创建对象
				// 取库存部门，下一库存部门
//				String tempOrg = getOrgCodeStr(adjustPlanResultDto.getWaybillNo(), entity.getSerialNo());
				freightRoute = setChangeNodeDtoInfo(adjustPlanResultDto.getWaybillNo(), entity.getSerialNo(), entity.getActuatingNode(), changeNodeDto);
//				// 查询货物全部路径
//				freightRoute = queryFreightRoute(adjustPlanResultDto.getWaybillNo(), entity.getSerialNo());
				// 主键
				changeNodeDto.setId(entity.getId());
				// 标签标号
				changeNodeDto.setSerialNo(entity.getSerialNo());
				// 原走货路径
				if (StringUtil.isNotEmpty(freightRoute)) {
					// 走货路径添加提货网点
					if(StringUtils.isNotEmpty(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(adjustPlanResultDto.getOcustomerPickupOrgCode()))){
						changeNodeDto.setOfreightRoute(freightRoute + "->" + orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(adjustPlanResultDto.getOcustomerPickupOrgCode()));
					}else{
						changeNodeDto.setOfreightRoute(freightRoute+ "->" + "空");
					}
				}
				// 执行节点Code
				changeNodeDto.setExeNode(entity.getHandleOrgCode());
				// 执行节点Name
				changeNodeDto.setExeNodeName(entity.getHandleOrgName());
				// 运单号
				changeNodeDto.setWaybillNo(adjustPlanResultDto.getWaybillNo());
				// 更改单主键
				changeNodeDto.setWaybillRfcId(waybillRfcId);
				
				// 新走货路径
				setDfreightRoute(freightRoute, changeNodeDto, adjustPlanResultDto);
				// 设置超时处理
				setTimeoutInfo(changeNodeDto, currOrgCode);
				changeNodeDtoList.add(changeNodeDto);//加入list
			}
		}
		return changeNodeDtoList;//返回list
	}
	
	/**
	 * 
	 * 设置列表信息的超时处理分钟和超时提醒次数
	 * 
	 * @param changeNodeDto
	 * @param currOrgCode
	 * @author ibm-wangfei
	 * @date Apr 10, 2013 10:20:26 AM
	 */
	private void setTimeoutInfo(ChangeNodeDto changeNodeDto, String currOrgCode) {
		ToDoMsgDto dto = new ToDoMsgDto();
		// 设置dto的ReceiveOrgCode，实际上应该是receiveSubOrgCode
		dto.setReceiveOrgCode(currOrgCode);
		// 业务流水号
		dto.setSerialNumber(changeNodeDto.getId());
		// 根据查询条件查询待办明细
		List<ToDoMsgDto> dtos = toDoMsgService.queryToDoMsgDetail(dto);
		if (CollectionUtils.isNotEmpty(dtos)) {
			// 设置超时提醒次数
			changeNodeDto.setTimeoutRemindTotalNum(String.valueOf(dtos.size()));
			changeNodeDto.setTimeoutHandleMinute(String.valueOf(DateUtils.getMinuteDiff(dtos.get(0).getCreateTime(), new Date())));
		} else {
			return;
		}
	}

	/**
	 * 
	 * 设置新走货路径
	 * 
	 * @param changeNodeDto
	 * @param adjustPlanResultDto
	 * @author ibm-wangfei
	 * @date Jan 17, 2013 3:59:51 PM
	 */
	private void setDfreightRoute(String ofreightRoute, ChangeNodeDto changeNodeDto, AdjustPlanResultDto adjustPlanResultDto) {
		String dfreightRoute = "";
		StringBuffer sb = new StringBuffer();
		if (StringUtils.equals(adjustPlanResultDto.getOcustomerPickupOrgCode(), adjustPlanResultDto.getDcustomerPickupOrgCode())) {//头尾相同
			// 原到达部门==新到达部门,则走货路径不变
			dfreightRoute = ofreightRoute;
		} else {
			// 获取新的走货路径
			// 得到综合的走货路径 经过外场部门编码列表 查询参数为运单号 出发营业部 到达营业部
			List<String> orgCodeList = waybillManagerService.getRouteLineCodeInfo(adjustPlanResultDto.getWaybillNo(), changeNodeDto.getExeNode(), adjustPlanResultDto.getDcustomerPickupOrgCode());
			if (CollectionUtils.isEmpty(orgCodeList)) {//结果不是空
				return;
			}
			for (String orgCode : orgCodeList) {//for循环
				// 精确查询 通过 CODE 查询 NAME
				sb.append("->");
				//组合路径  
				//a -> b -> c
				sb.append(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode));
			}//for循环结束
			if (sb.length() > 0) {
				//截断字符串
				dfreightRoute = sb.toString().substring(2);
			}
			// 设置原有走货路径和新走货路径的连接
			if (StringUtils.isNotEmpty(ofreightRoute)) {
				// 设置原有走货路径和新走货路径的连接
//				dfreightRoute = ofreightRoute.substring(0, ofreightRoute.lastIndexOf(changeNodeDto.getExeNodeName())).concat(dfreightRoute);
				dfreightRoute = getDfreightRoute(ofreightRoute, dfreightRoute);
			} else {
				// 设置原有走货路径和新走货路径的连接
				dfreightRoute = sb.toString();
			}
		}
		if (StringUtil.isNotEmpty(dfreightRoute)) {
			//进行最后一项为空的判断如果为空，则再
			String destination = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(adjustPlanResultDto.getDcustomerPickupOrgCode());
			if(StringUtils.isEmpty(destination)){
				changeNodeDto.setDfreightRoute(dfreightRoute+ "->" + "空");//设置走货路径
			}else{
				changeNodeDto.setDfreightRoute(dfreightRoute + "->" + orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(adjustPlanResultDto.getDcustomerPickupOrgCode()));//设置走货路径
			}
		} else {
			changeNodeDto.setDfreightRoute(dfreightRoute);//设置走货路径
		}
	}

	/**
     * 
     * 根据Code查询价格DTO
     * @author 102246-foss-shaohongliang
     * @date 2013-1-22 下午2:27:16
     * @see com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
     */
	public PriceEntity queryValueAddPriceByCode(String pricingCodeZz) {
		PriceEntity priceEntity = null;//对象
		List<String> entryCodes = new ArrayList<String>();//创建对象
		entryCodes.add(pricingCodeZz);//设置价格编码
		List<PriceEntity> priceEntities = priceEntryDao.queryPriceEntryNameByEntryCodes(entryCodes);//查询
		if(priceEntities != null && priceEntities.size() > 0){//size不是0
			priceEntity = priceEntities.get(0);//取得第一个
		}
		return priceEntity;//返回对象
	}
	
	/**
	 * 
	 * 官网变更单申请
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午5:01:56
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#applyChangeOrder(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto)
	 */
	public boolean applyChangeOrder(WaybillRfcForAccountServiceDto waybillRfcForAccountServiceDto){
		try {
			WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity = new WaybillRfcForAccountServiceEntity();//创建对象
			waybillRfcForAccountServiceEntity.setApplyPerson(waybillRfcForAccountServiceDto.getApplyPerson());//申请人
			waybillRfcForAccountServiceEntity.setApplyTime(waybillRfcForAccountServiceDto.getApplyTime());//申请时间
			waybillRfcForAccountServiceEntity.setChangeContent(waybillRfcForAccountServiceDto.getChangeContent());//变更内容
			waybillRfcForAccountServiceEntity.setContactHandy(waybillRfcForAccountServiceDto.getContactHandy());//联系人
			waybillRfcForAccountServiceEntity.setContactName(waybillRfcForAccountServiceDto.getContact());//联系人
			waybillRfcForAccountServiceEntity.setUnifieldCode(waybillRfcForAccountServiceDto.getUnifieldCode());//标杆
			waybillRfcForAccountServiceEntity.setWaybillNumber(waybillRfcForAccountServiceDto.getWaybillNumber());//运单号
			waybillRfcForAccountServiceEntity.setActive(FossConstants.INACTIVE);//非激活
			waybillRfcDao.applyChangeOrder(waybillRfcForAccountServiceEntity);//变更单申请
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillRfcForAccountServiceDto.getWaybillNumber());
			if(waybill != null){
				if(FossConstants.YES.equals(waybill.getPickupCentralized())){//如果是集中接送货
					//消息提醒：提醒受理人员受理已通过审核的待受理更改单
	    			pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,
	    					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,null,waybill.getCreateOrgCode(),waybillRfcForAccountServiceDto.getWaybillNumber(), waybillRfcForAccountServiceEntity.getId());
	    			
	    			//消息提醒：提醒受理人员受理已通过审核的待受理更改单
	    			pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,
	    					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,null,waybill.getReceiveOrgCode(),waybillRfcForAccountServiceDto.getWaybillNumber(), waybillRfcForAccountServiceEntity.getId());
	    			
	    		
				}else {
					//消息提醒：提醒受理人员受理已通过审核的待受理更改单
	    			pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,
	    					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,null,waybill.getCreateOrgCode(),waybillRfcForAccountServiceDto.getWaybillNumber(), waybillRfcForAccountServiceEntity.getId());
	    			
				}
			}
			return true;//返回成功
		} catch (Exception e) {
			LOG.error("EXCEPTION", e);//异常
			return false;//返回失败
		}
	}
	
	
	
	
	
	@Override
	public Map<String, Object> aLiChangeOrder(
			WaybillRfcForAccountServiceDto waybillRfcForAccountServiceDto) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity = new WaybillRfcForAccountServiceEntity();//创建对象
			waybillRfcForAccountServiceEntity.setApplyPerson(waybillRfcForAccountServiceDto.getApplyPerson());//申请人
			waybillRfcForAccountServiceEntity.setApplyTime(waybillRfcForAccountServiceDto.getApplyTime());//申请时间
			waybillRfcForAccountServiceEntity.setChangeContent(waybillRfcForAccountServiceDto.getChangeContent());//变更内容
			waybillRfcForAccountServiceEntity.setContactHandy(waybillRfcForAccountServiceDto.getContactHandy());//联系人
			waybillRfcForAccountServiceEntity.setContactName(waybillRfcForAccountServiceDto.getContact());//联系人
			waybillRfcForAccountServiceEntity.setUnifieldCode(waybillRfcForAccountServiceDto.getUnifieldCode());//标杆
			waybillRfcForAccountServiceEntity.setWaybillNumber(waybillRfcForAccountServiceDto.getWaybillNumber());//运单号
			waybillRfcForAccountServiceEntity.setActive(FossConstants.INACTIVE);//非激活
			waybillRfcDao.applyChangeOrder(waybillRfcForAccountServiceEntity);//变更单申请
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillRfcForAccountServiceDto.getWaybillNumber());
			if(waybill != null){
				if(FossConstants.YES.equals(waybill.getPickupCentralized())){//如果是集中接送货
					//消息提醒：提醒受理人员受理已通过审核的待受理更改单
	    			pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,
	    					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,null,waybill.getCreateOrgCode(),waybillRfcForAccountServiceDto.getWaybillNumber(), waybillRfcForAccountServiceEntity.getId());
	    			
	    			//消息提醒：提醒受理人员受理已通过审核的待受理更改单
	    			pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,
	    					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,null,waybill.getReceiveOrgCode(),waybillRfcForAccountServiceDto.getWaybillNumber(), waybillRfcForAccountServiceEntity.getId());
	    			
	    		 
				}else {
					//消息提醒：提醒受理人员受理已通过审核的待受理更改单
	    			pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,
	    					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,null,waybill.getCreateOrgCode(),waybillRfcForAccountServiceDto.getWaybillNumber(), waybillRfcForAccountServiceEntity.getId());
	    			
				}
			}
			map.put("result", true);
			return map;//返回成功
		} catch (Exception e) {
			LOG.error("EXCEPTION", e);//异常
			map.put("result", false);
			map.put("failureReason", e.getMessage());
			return map;//返回失败
		}
	}

	/** 
	 * 更改单申请查询
	 * @param waybillRfcForAccountServiceCondition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 28, 2013 5:39:45 PM
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService#queryChangeOrder(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition)
	 */
	public List<WaybillRfcForAccountServiceDto> queryChangeOrder(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition){
		return waybillRfcDao.queryChangeOrder(waybillRfcForAccountServiceCondition);//更改单申请查询
	}
	
	/**
	 * 
	 * 分页查询更改单
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:21 AM
	 */
	public List<WaybillRfcForAccountServiceDto> queryChangeOrderList(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition, int start, int limit) {
		return waybillRfcDao.queryChangeOrderList(waybillRfcForAccountServiceCondition, start, limit);//分页查询更改单
	}

	/**
	 * 
	 * 查询更改单的记录总数
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:55 AM
	 */
	public Long queryChangeOrderCount(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition) {
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = FossUserContext.getCurrentDept();//获得当前登录人部门
		if(null ==FossUserContext.getCurrentDept()){
			return null;
		}
		OrgAdministrativeInfoEntity orgAdministrativeInfo=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgAdministrativeInfoEntity.getCode());
		if(orgAdministrativeInfo!= null){
			if(orgAdministrativeInfo.getBillingGroup()!= null && FossConstants.YES.equals(orgAdministrativeInfo.getBillingGroup())){//如果是集中开单组开单
				waybillRfcForAccountServiceCondition.setCreateOrgCode(orgAdministrativeInfoEntity.getCode());
				waybillRfcForAccountServiceCondition.setReceiveOrgCode(null);
			}else {
				waybillRfcForAccountServiceCondition.setReceiveOrgCode(orgAdministrativeInfoEntity.getCode());
				waybillRfcForAccountServiceCondition.setCreateOrgCode(null);
			}
		}else {
			return null;
		}
		/*if (orgAdministrativeInfoEntity != null) {// 设置当前登录用户的标杆编码
			// 设置当前登录用户的标杆编码
			waybillRfcForAccountServiceCondition.setUnifieldCode(orgAdministrativeInfoEntity.getUnifiedCode());
		}*/
		return waybillRfcDao.queryChangeOrderCount(waybillRfcForAccountServiceCondition);//查询
	}

	/**
	 * 
	 * 更新更改单处理状态
	 * 
	 * @param waybillRfcForAccountServiceEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:12:17 AM
	 */
	@Transactional
	public int updateWaybillRfcIds(String ids) {
		if (StringUtils.isEmpty(ids)) {//没有id
			return 0;//返回0条记录
		}
		String [] changeIds = ids.split(",");//逗号分割
		if (changeIds.length == 0) {//数组长度为0
			return 0;//返回0条记录
		}
		//创建对象
		WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity = new WaybillRfcForAccountServiceEntity();
		waybillRfcForAccountServiceEntity.setIds(changeIds);//设置ids
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();//获取当前对象
		waybillRfcForAccountServiceEntity.setProcessTime(new Date());//处理时间
		if (currentInfo != null) {//当前对象不是null
			waybillRfcForAccountServiceEntity.setProcessOrgCode(currentInfo.getCurrentDeptCode());//处理组织code
			waybillRfcForAccountServiceEntity.setProcessOrgName(currentInfo.getCurrentDeptName());//处理组织name
			waybillRfcForAccountServiceEntity.setProcessUserName(currentInfo.getEmpName());//处理人名字
			waybillRfcForAccountServiceEntity.setProcessUserCode(currentInfo.getEmpCode());//处理人编码
		}
		waybillRfcForAccountServiceEntity.setActive(FossConstants.ACTIVE);//激活
		for (String id : changeIds) {
			pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY,null,id);
		}
		return waybillRfcDao.updateWaybillRfcIds(waybillRfcForAccountServiceEntity);//更新数据库
	}
	
	@Override
	public WaybillRfcPrintDto queryWaybillRfcPrintDtoByRfcid(String rfcId) {
		return waybillRfcVarificationDao.queryWaybillRfcPrintDtoByRfcid(rfcId);
	}


	/**
	 * @param airWaybillService the airWaybillService to set
	 */
	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}


	public void setToDoMsgService(IToDoMsgService toDoMsgService) {
		this.toDoMsgService = toDoMsgService;
	}
	
	/**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 102246-foss-shaohongliang
     * @date 2013-4-12 下午4:52:38
     */
	public OuterBranchEntity queryAgencyBranchCompanyInfo(String agencyBranchCode){
		return waybillRfcDao.queryAgencyBranchCompanyInfo(agencyBranchCode);
	}
	
	/**
	 * 
	 *  通过 标识编码查询
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:56:44
	 */
	public SaleDepartmentEntity querySaleDepartmentByCode(String customerPickupOrgCode){
		return waybillRfcDao.querySaleDepartmentByCode(customerPickupOrgCode);
	}


	/**
	 * 根据virtual code查询走货路径
	 * @param virtualCode
	 */
	public FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode) {
		return freightRouteService.queryFreightRouteByVirtualCode( virtualCode);
	}

	public void setPrintInfoDao(IPrintInfoDao printInfoDao) {
		this.printInfoDao = printInfoDao;
	}

	@Override
	public int queryPrintTimesByWaybillRFCId(String waybillRFCId, String waybillNo) {
		return printInfoDao.queryPrintTimesByWaybillRFCId(waybillRFCId, waybillNo);
	}

	@Override
	public int updateByPrimaryKeySelective(PrintInfoEntity record) {
		return printInfoDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertPrintInfoSelective(PrintInfoEntity record) {
		return printInfoDao.insertSelective(record);
	}
	
	/**
	 * 
	 * 获取连接后的走货路径
	 * 
	 * @param ofreightRoute
	 * @param dfreightRoute
	 * @return
	 * @author ibm-wangfei
	 * @date May 8, 2013 5:25:56 PM
	 */
	private static String getDfreightRoute(String ofreightRoute, String dfreightRoute) {
		String	newNote = dfreightRoute.lastIndexOf("->") > 0 ? dfreightRoute.substring(0,dfreightRoute.lastIndexOf("->")) : dfreightRoute;
		String oldNote = "";
		if (ofreightRoute.lastIndexOf(newNote) > 0) {
			oldNote = ofreightRoute.substring(0,ofreightRoute.lastIndexOf(newNote));
		} else {
			return ofreightRoute;
		}
		return oldNote + dfreightRoute;
	}


	@Override
	public String queryWaybillNO(String waybillID) {
		WaybillEntity waybillEntity	=waybillDao.queryWaybillById(waybillID);
	
		if(waybillEntity!=null){
			return	waybillEntity.getWaybillNo();
		}
		 return null;
	}
	
	/**
	 * 判断一个waybillEntity是否发生过目的站的修改
	 * @param waybillEntity
	 * @return
	 */
	public boolean isChangeCustomerPickupOrgCode(WaybillEntity waybillEntity){
		boolean isChangeCustomerPickupOrgCode = false;
		
		String waybillNo = waybillEntity.getWaybillNo();
		List<LeaveChangeByWaybillNoResultDto> list 
			=waybillRfcDao.queryLeaveChangeByWaybillNo(waybillNo);
		
		//没有更改单的情况下 根本不用看的
		if(list==null || list.size()==0){
			return isChangeCustomerPickupOrgCode;
		}
		
		for (Iterator<LeaveChangeByWaybillNoResultDto> iterator = list.iterator(); iterator.hasNext();) {
			LeaveChangeByWaybillNoResultDto leaveChangeByWaybillNoResultDto =iterator.next();
			
			//没有受理通过  我们这里是不算的
			if(!WaybillRfcConstants.ACCECPT.equals(leaveChangeByWaybillNoResultDto.getStatus())){
				continue;
			}
			
			String rfcType = leaveChangeByWaybillNoResultDto.getRfcType();
			if(WaybillRfcConstants.RETURN.equals(rfcType)||WaybillRfcConstants.TRANSFER.equals(rfcType)){
				isChangeCustomerPickupOrgCode = true;
				return isChangeCustomerPickupOrgCode;
			}
			String rfcId = leaveChangeByWaybillNoResultDto.getId();
			List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList = 
					waybillRfcVarificationDao.queryWayBillRfcChangeDetailByRfcId(rfcId);
			for(WaybillRfcChangeDetailEntity changeDetailEntity : waybillRfcChangeDetailEntityList){
    		    String changeDetailItem = changeDetailEntity.getRfcItems();
    		    //标签信息变更
    		    if(checkWaybillRfcChangedItemNeedInvoke(changeDetailItem)){
    		    	isChangeCustomerPickupOrgCode = true;//需要调用中转
    		    	return isChangeCustomerPickupOrgCode;
    		    }
    		}	
			
			
		}
	
		
		return isChangeCustomerPickupOrgCode;
	}
	
	
	
	
	/**
	 * 判断一个waybillEntity是否有没有自动处理的代办
	 * @param waybillEntity
	 * @return
	 */
	public boolean isExistsNotHandleTodo(WaybillEntity waybillEntity, List<String> serialNos){
		boolean isExistsNotHandleTodo = false;
		
		String waybillNo = waybillEntity.getWaybillNo();
		List<LeaveChangeByWaybillNoResultDto> list 
			=waybillRfcDao.queryLeaveChangeByWaybillNo(waybillNo);
		
		//没有更改单的情况下 根本不用看的
		if(list==null || list.size()==0){
			return isExistsNotHandleTodo;
		}
		
		for (Iterator<LeaveChangeByWaybillNoResultDto> iterator = list.iterator(); iterator.hasNext();) {
			LeaveChangeByWaybillNoResultDto leaveChangeByWaybillNoResultDto =iterator.next();
			
			//没有受理通过  我们这里是不算的
			if(!WaybillRfcConstants.ACCECPT.equals(leaveChangeByWaybillNoResultDto.getStatus())){
				continue;
			}
			String rfcId = leaveChangeByWaybillNoResultDto.getId();
			
			List<LabeledGoodTodoEntity>  list2 =labeledGoodTodoDao.queryTodoByWaybillRfcId(rfcId);
			
			for( LabeledGoodTodoEntity todo:list2){
				for(String serialNo: serialNos){
					if(serialNo.equals(todo.getSerialNo())){
						if (FossConstants.NO.equals(todo.getExceptionMsg())
							&&  !FossConstants.YES.equals(todo.getStatus())){
							isExistsNotHandleTodo =true;
							return isExistsNotHandleTodo;
						}
					}
				}
			}
			
		}
		return isExistsNotHandleTodo;
	}
	
	private boolean checkWaybillRfcChangedItemNeedInvoke(String item){
	    String items = "customerPickupOrgName+";
	    return (items.indexOf(item+'+')>=0);
	}
	
	/**
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	public List<ProductEntity> queryTransType(String salesDeptId){
		return waybillRfcDao.queryBySalesDept(salesDeptId, "3");
	}
	
	/**
	 * 根据到达部门编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	public List<ProductEntity> queryByArriveDeptProduct(String arriveDept){
		return waybillRfcDao.queryByArriveDeptProduct(arriveDept, "3");
	}
	
	/**
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	public List<ProductEntity> queryBySalesDept(String salesDeptId,String productLevel){
		return waybillRfcDao.queryBySalesDept(salesDeptId, productLevel);
	}
			
	/**
	 * 根据到达部门编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	public List<ProductEntity> queryByArriveDept(String salesDeptId,String productLevel){
		return waybillRfcDao.queryByArriveDeptProduct(salesDeptId, productLevel);
	}
	
	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	public List<ProductEntity> searchByArriveDept(String salesDeptId,String productLevel){
		return waybillRfcDao.searchByArriveDeptProduct(salesDeptId, productLevel);
	}
	
	/**
	 * 添加PDA更改待办信息
	 * @author WangQianJin
	 * @date 2013-7-25 下午4:40:25
	 */
	@Transactional
	public String addPDAPendingForRfcInfo(PDAWayBillRfcDto dto){
		String isSuccess=FossConstants.NO;
		if(dto!=null && dto.isBusinessChange()){
			// 添加更改单信息(pkp.t_srv_waybill)
			if(dto.getWaybillRfcEntity()!=null){
				waybillRfcDao.addWaybillRfcEntity(dto.getWaybillRfcEntity());
			}
			// 添加变更项明细(pkp.t_srv_waybillrfc_changedetail)
			if(dto.getChangeDetailList()!=null){
				for(WaybillRfcChangeDetailEntity changeDetail:dto.getChangeDetailList()){
					changeDetail.setWaybillRfcId(dto.getWaybillRfcEntity().getId());
					waybillRfcDao.addRfcChangeDetailEntity(changeDetail);	
				}				
    		}
			// 添加起草审核受理记录信息(pkp.t_srv_waybillrfc_actionhistory)
			if(dto.getRfcActionList()!=null){
				for(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity actionRfc:dto.getRfcActionList()){
					actionRfc.setWaybillRfcId(dto.getWaybillRfcEntity().getId());					
					waybillRfcVarificationDao.addWaybillRfcActionHistory(actionRfc);
				}				
    		}
			// 添加待办信息(PKP.T_SRV_PENDING_TODO)
			if(dto.getPendingTodoEntity()!=null){
				dto.getPendingTodoEntity().setWaybillRfcId(dto.getWaybillRfcEntity().getId());
				pendingTodoDao.insert(dto.getPendingTodoEntity());
    		}
			isSuccess=FossConstants.YES;
		}	
		return isSuccess;
	}
	
	@Transactional
	@Override
	public String addPackageRfcInfo(PDAWayBillRfcDto dto){
		String isSuccess=FossConstants.NO;
		if(dto!=null && dto.isBusinessChange()){
			// 添加更改单信息(pkp.t_srv_waybill)
			if(dto.getWaybillRfcEntity()!=null){
				waybillRfcDao.addWaybillRfcInfoNoId(dto.getWaybillRfcEntity());
			}
			// 添加变更项明细(pkp.t_srv_waybillrfc_changedetail)
			if(dto.getChangeDetailList()!=null){
				for(WaybillRfcChangeDetailEntity changeDetail:dto.getChangeDetailList()){
					changeDetail.setWaybillRfcId(dto.getWaybillRfcEntity().getId());
					waybillRfcDao.addRfcChangeDetailEntity(changeDetail);	
				}				
    		}
			// 添加起草审核受理记录信息(pkp.t_srv_waybillrfc_actionhistory)
			if(dto.getRfcActionList()!=null){
				for(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity actionRfc:dto.getRfcActionList()){
					actionRfc.setWaybillRfcId(dto.getWaybillRfcEntity().getId());					
					waybillRfcVarificationDao.addWaybillRfcActionHistory(actionRfc);
				}				
    		}
			// 添加待办信息(PKP.T_SRV_PENDING_TODO)
			if(dto.getPendingTodoEntity()!=null){
				dto.getPendingTodoEntity().setWaybillRfcId(dto.getWaybillRfcEntity().getId());
				pendingTodoDao.insert(dto.getPendingTodoEntity());
    		}
			isSuccess=FossConstants.YES;
		}	
		return isSuccess;
	
	}
	
	public List<WaybillRfcEntity> queryWaybillRfcAcceptByWaybillNo(String waybillNo){
		List<WaybillRfcEntity> list = waybillRfcDao.queryWaybillRfcAcceptByWaybillNo(waybillNo, WaybillRfcConstants.ACCECPT);
		return list;
	}

	/**
	 * 传入日期、司机工号、车牌号，查询PDA接货记录
	 * @author maojianqiang
	 * @date 2014-5-5 下午4:40:25
	 */
	public List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDtoByDate(
			Date startDate, Date endDate, String driverCode) {
		// 先从pending
		Map<String, Object> args = new HashMap<String, Object>();//创建对象
		args.put("startDate", startDate);
		args.put("endDate", endDate);
		args.put("driverCode", driverCode);
		args.put("pendingType", WaybillConstants.PDA_ACTIVE);
		List<PdaReceiveGoodsDto> pdaWaybillReceiveList = waybillPendingDao.queryPdaWaybillReceiveGoodsDtoByDate(args);
		List<PdaReceiveGoodsDto> result = new ArrayList<PdaReceiveGoodsDto>();//创建对象
		result.addAll(pdaWaybillReceiveList);
		return result;
	}

	/**
	 * 查询运单是否有交接记录
	 * 
	 */
	public List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo){
		
		return handOverBillService.queryHandOveredRecordsByWaybillNo(waybillNo);
	}
	
	/**
	 * 查询运单是否在开单部门有卸车记录
	 *  
	 */
	public boolean queryUnloadTaskReport(String waybillNo){
		
		return unloadTaskService.queryUnloadTaskReport(waybillNo);
	}
	
	/**
	 * 传入日期、司机工号、车牌号，查询PDA接货记录（快递）
	 * @author WangQianJin
	 * @date 2014-06-06
	 */
	public List<PdaReceiveGoodsDto> queryPdaExpressReceiveGoodsDtoByDate(
			Date startDate, Date endDate, String driverCode) {
		// 先从pending
		Map<String, Object> args = new HashMap<String, Object>();//创建对象
		args.put("startDate", startDate);
		args.put("endDate", endDate);
		args.put("driverCode", driverCode);
		args.put("pendingType", WaybillConstants.PDA_ACTIVE);
		List<PdaReceiveGoodsDto> pdaWaybillReceiveList = waybillPendingDao.queryPdaExpressReceiveGoodsDtoByDate(args);
		List<PdaReceiveGoodsDto> result = new ArrayList<PdaReceiveGoodsDto>();//创建对象
		result.addAll(pdaWaybillReceiveList);
		return result;
	}

	
	@Override
	public List<String> queryUnActiveRfcWaybillNo(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		List<String> resultList = new ArrayList<String>();
		if(waybillNoList!=null){
			if(waybillNoList != null && waybillNoList.size()> NumberConstants.NUMBER_1000){
				int num = (waybillNoList.size()/NumberConstants.NUMBER_1000)+1;
				for(int i=0;i<num ;i++){
					int max = (i+1)* NumberConstants.NUMBER_1000;
					if(max>waybillNoList.size()){
						max = waybillNoList.size();
					}
					List<String> list = waybillNoList.subList(i * NumberConstants.NUMBER_1000,max);
					List<String> result = waybillRfcDao.queryUnActiveRfcWaybillNo(list);
					resultList.addAll(result);
				}
			}else{
				return waybillRfcDao.queryUnActiveRfcWaybillNo(waybillNoList);
			}
		}
		return resultList;
	}
	
	
	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	public List<ExpBatchChangeWeightDto> queryExpBatchChangeWeightResultByCondition(
			ExpBatchChangeWeightQueryDto dto){	
		
		List<ExpBatchChangeWeightDto> expBatchChangeWeightDtoList = waybillRfcBatchChangeDao.querybatchChangeDtoListResultByCondition(dto);

		return  expBatchChangeWeightDtoList;
		
	}

	//ACTION校验基本信息；
	
	//传重量体积到后台(封装一个DTO)；
	
	//判断重量是否变更
	
	//校验运单号是否存在，运单是否合法；
	
	//计算重量体积
	
	//封装submitDto(从)
	
	//批量修改重量并自动审核通过
	
	/**
	 * 批量修改重量并自动审核通过
	 * @author 136334-foss-bailei 
	 * @return
	 */
	//@Transactional
	public void batchChangeWeight(ExpBatchChangeWeightDto expBatchChangeWeightDto,CurrentInfo currentInfo){
		//不论成功与否，先将信息插入waybillRfcBatchChange表信息
		WaybillRfcBatchChangeEntity waybillRfcBatchChangeEntity = new WaybillRfcBatchChangeEntity();
		//如果体积重未发生变更则插入运单信息
		waybillRfcBatchChangeEntity.setId(UUIDUtils.getUUID());
		waybillRfcBatchChangeEntity.setImportTime(new Date());
		waybillRfcBatchChangeEntity.setCreateUserCode(currentInfo.getEmpCode());
		waybillRfcBatchChangeEntity.setModifyUserCode(currentInfo.getEmpCode());
		waybillRfcBatchChangeEntity.setChangeNote(expBatchChangeWeightDto.getChangeNote());
		waybillRfcBatchChangeEntity.setWaybillNo(expBatchChangeWeightDto.getWaybillNo());
		waybillRfcBatchChangeEntity.setVolumnChange(expBatchChangeWeightDto.getVolumeChanged());
		waybillRfcBatchChangeEntity.setWeightChange(expBatchChangeWeightDto.getWeightChanged());
		waybillRfcBatchChangeEntity.setServicefee(expBatchChangeWeightDto.getServicefee());
		//运单号
		String waybillNo = expBatchChangeWeightDto.getWaybillNo();
		//拼装WaybillDto
		WaybillDto oldWaybillDto = waybillManagerService.queryWaybillByNo(waybillNo);
		//如果为空，需要设置一个值进行逻辑校验
		if(oldWaybillDto == null){
			oldWaybillDto = new WaybillDto();
		}
		//对当前用户赋值   CN-267大客户电子运单更改无法导入
		oldWaybillDto.setCurrentInfo(currentInfo);
		//判定是否重量体积发生变化
		Boolean billWeightIsChanged = billWeightIsChanged(expBatchChangeWeightDto, oldWaybillDto);
		List<WaybillRelateDetailEntity> waybillRelateDetailEntityList = null;
		WaybillEntity waybillEntity = null;
		//如果体积重发生变更或者装卸费发生变更才发更改
		if(billWeightIsChanged||(expBatchChangeWeightDto.getServicefee()!=
				oldWaybillDto.getWaybillEntity().getServiceFee())){
			BusinessException exception = null;
			try{
				WaybillRfcSubmitDto submitDto = new WaybillRfcSubmitDto();
				//获取子件更改信息
//				childWaybillRelateDetailEntityList = getChildWaybillRelateDetailEntityList(expBatchChangeWeightDto);
				waybillRelateDetailEntityList = expBatchChangeWeightDto.getWaybillRelateDetailEntityList();
				//校验能否发更改
				validateCanChange(expBatchChangeWeightDto, oldWaybillDto);
				//设置值信息
				waybillRfcBatchChangeEntity.setWaybillNo(oldWaybillDto.getWaybillEntity().getWaybillNo());
				waybillRfcBatchChangeEntity.setWeightChange(oldWaybillDto.getWaybillEntity().getGoodsWeightTotal());
				waybillRfcBatchChangeEntity.setVolumnChange(oldWaybillDto.getWaybillEntity().getGoodsVolumeTotal());
				waybillRfcBatchChangeEntity.setTransportFeeChange(oldWaybillDto.getWaybillEntity().getTransportFee());
				waybillRfcBatchChangeEntity.setProductChange(oldWaybillDto.getWaybillEntity().getProductCode());
				waybillRfcBatchChangeEntity.setDeliverCustomerCode(oldWaybillDto.getWaybillEntity().getDeliveryCustomerCityCode());
				//waybillRfcBatchChangeEntity.setServicefee(oldWaybillDto.getWaybillEntity().getServiceFee());
				//设置计算的类型为开单，然后重新计算
				oldWaybillDto.setIsChangeWaybill(WaybillConstants.YES);
				
				//进行基础数据的赋值
				WaybillDto newWaybillDto = new WaybillDto();
				BeanUtils.copyProperties(oldWaybillDto, newWaybillDto);
				
				//赋值给运单，以便取平均值
				List<String> waybillNoList = waybillRelateDetailEntityDao.queryWaybillNoListByParentWaybillNo(waybillNo);
				newWaybillDto.getWaybillEntity().setWaybillNos(waybillNoList);
				
				//将更改的内容赋予新的WaybillDto
				setChangedWeight(newWaybillDto,expBatchChangeWeightDto);
				
				//如果是折让方式则不允许修改装卸费
				//if(customerService.queryCustomerInfoByCusCode
				//		(oldWaybillDto.getWaybillEntity().getDeliveryCustomerCode())
				//		.getExpHandChargeBusiType().equals("")){
				//	newWaybillDto.getWaybillEntity().setServiceFee(
				//	oldWaybillDto.getWaybillEntity().getServiceFee());
				//}else{
					//校验装卸费规则
				//validataServiceFee(newWaybillDto,oldWaybillDto);
				//}
				
				
				//重新计算费用
				caculateChangedFee(newWaybillDto);
				
				if(null!=newWaybillDto&&
						null!=newWaybillDto.getWaybillEntity()&&
						null!=newWaybillDto.getWaybillEntity().getDcServicefee()){
					ActualFreightEntity ac = new ActualFreightEntity();
					ac.setDcServicefee(newWaybillDto.getWaybillEntity().getDcServicefee());
					ac.setWaybillNo(oldWaybillDto.getWaybillEntity().getWaybillNo());
					actualFreightDao.updateAddGenerateGoodsQtyByWaybillNo(ac);
				}
				//封装submitDto
				submitDto = getBatchChangeWeightSubmitDto(oldWaybillDto, newWaybillDto,waybillRelateDetailEntityList);
				
				//执行更改并自动审核通过
				commitChangeWeight(submitDto,currentInfo);
				
				//更改子母件关系信息
				updateWaybillRelateDetailEntity(expBatchChangeWeightDto,currentInfo);
				
				if(CollectionUtils.isNotEmpty(waybillRelateDetailEntityList)){
					for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybillRelateDetailEntityList){
						waybillEntity = waybillManagerService.queryWaybillNo(waybillRelateDetailEntity.getWaybillNo());
						Date currentDate = new Date();
						//更改子件运单信息
						if(waybillEntity != null && !waybillRelateDetailEntity.getWaybillNo().equals(waybillRelateDetailEntity.getParentWaybillNo())&&waybillRelateDetailEntity.isChange())
							updateChildWaybill(waybillRelateDetailEntity,waybillEntity,newWaybillDto,currentInfo,currentDate);
						//因为母件的产品类型会更改，子件的产品类型也要随之更改
						if(waybillEntity != null && !waybillRelateDetailEntity.getWaybillNo().equals(waybillRelateDetailEntity.getParentWaybillNo())&&!waybillRelateDetailEntity.isChange())
							updateChildWaybillProductCode(waybillRelateDetailEntity,waybillEntity,newWaybillDto,currentInfo,currentDate);
						//新增子件更改日志
						if(waybillRelateDetailEntity.isChange())
							updateChildWaybillRfcBatchChange(waybillRelateDetailEntity,waybillEntity,null,currentInfo,currentDate);
					}
				}
				
				//更新对应的产品类型
				waybillRfcBatchChangeEntity.setProductChange(newWaybillDto.getWaybillEntity().getProductCode());
				//设置费用信息
				waybillRfcBatchChangeEntity.setTransportFeeChange(newWaybillDto.getWaybillEntity().getTotalFee());
				waybillRfcBatchChangeEntity.setWeightChange(newWaybillDto.getWaybillEntity().getGoodsWeightTotal());
				waybillRfcBatchChangeEntity.setVolumnChange(newWaybillDto.getWaybillEntity().getGoodsVolumeTotal());
				waybillRfcBatchChangeEntity.setModifyTime(new Date());
				//waybillRfcBatchChangeEntity.setServicefee(newWaybillDto.getWaybillEntity().getServiceFee());
				//设置更改成功
				waybillRfcBatchChangeEntity.setChangeStatus(WaybillRfcConstants.WAYBILL_BATCH_RFC_SUCCESS);
				//设置异常为N
				waybillRfcBatchChangeEntity.setFailReason(FossConstants.NO);
				waybillRfcBatchChangeEntity.setModifyTime(new Date());
				if(CollectionUtils.isEmpty(waybillRelateDetailEntityList))
					waybillRfcBatchChangeDao.insertSelective(waybillRfcBatchChangeEntity);
			}catch(BusinessException rfcChangeException){
				LOG.info("批量发更改出现异常"+rfcChangeException.getMessage());
				exception = rfcChangeException;
			}finally{
				if(exception != null){
				//在运单更改表中记录失败原因
					String failReason = exception.getMessage();
					//设置改成异常信息
					waybillRfcBatchChangeEntity.setFailReason(failReason);
					//设置更改失败
					waybillRfcBatchChangeEntity.setChangeStatus(WaybillRfcConstants.WAYBILL_BATCH_RFC_FAIL);
					waybillRfcBatchChangeEntity.setModifyTime(new Date());
					waybillRfcBatchChangeEntity.setTransportFeeChange(null);//更改失败，费用置空
					waybillRfcBatchChangeEntity.setProductChange(null);//更改失败，产品性质置空
					//更改失败,重量体积要恢复
					waybillRfcBatchChangeEntity.setVolumnChange(expBatchChangeWeightDto.getVolumeChanged());
					waybillRfcBatchChangeEntity.setWeightChange(expBatchChangeWeightDto.getWeightChanged());
					//waybillRfcBatchChangeEntity.setServicefee(expBatchChangeWeightDto.getServicefee());
					if(CollectionUtils.isEmpty(waybillRelateDetailEntityList))
						waybillRfcBatchChangeDao.insertSelective(waybillRfcBatchChangeEntity);
					//新增子件更改日志
					if(CollectionUtils.isNotEmpty(waybillRelateDetailEntityList)){
						for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybillRelateDetailEntityList){
							Date currentDate = new Date();
							if(waybillRelateDetailEntity.isChange())
								updateChildWaybillRfcBatchChange(waybillRelateDetailEntity,null,failReason,currentInfo,currentDate);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 子件更改产品类型
	 * @param waybillRelateDetailEntity
	 * @param waybillEntity
	 * @param newWaybillDto
	 * @param currentInfo
	 */
	private void updateChildWaybillProductCode(
			WaybillRelateDetailEntity waybillRelateDetailEntity,
			WaybillEntity waybillEntity, WaybillDto newWaybillDto,
			CurrentInfo currentInfo,Date currentDate) {
		//作废最新的运单
		waybillEntity.setActive(FossConstants.INACTIVE);
		waybillEntity.setModifyTime(currentDate);
		waybillManagerService.updateWaybill(waybillEntity);
		//新增一条运单信息
		waybillEntity.setProductCode(newWaybillDto.getWaybillEntity().getProductCode());
		waybillEntity.setActive(FossConstants.ACTIVE);
		waybillEntity.setCreateTime(currentDate);
		waybillEntity.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
		waybillEntity.setModifyUser(currentInfo.getEmpName());
		waybillEntity.setModifyOrgCode(currentInfo.getCurrentDeptCode());
		waybillEntity.setModifyUserCode(currentInfo.getEmpCode());
		waybillDao.addWaybillEntity(waybillEntity);
	}
	/**
	 * 新增子件更改日志
	 */
	private void updateChildWaybillRfcBatchChange(
			WaybillRelateDetailEntity waybillRelateDetailEntity,
			WaybillEntity waybillEntity, String failReason,CurrentInfo currentInfo,Date currentDate) {
		WaybillRfcBatchChangeEntity waybillRfcBatchChangeEntity = new WaybillRfcBatchChangeEntity();
		waybillRfcBatchChangeEntity.setId(UUIDUtils.getUUID());
		waybillRfcBatchChangeEntity.setImportTime(new Date());
		waybillRfcBatchChangeEntity.setCreateUserCode(currentInfo.getEmpCode());
		waybillRfcBatchChangeEntity.setModifyUserCode(currentInfo.getEmpCode());
		waybillRfcBatchChangeEntity.setWaybillNo(waybillRelateDetailEntity.getWaybillNo());
		waybillRfcBatchChangeEntity.setVolumnChange(waybillRelateDetailEntity.getVolume());
		waybillRfcBatchChangeEntity.setWeightChange(waybillRelateDetailEntity.getWeight());
		waybillRfcBatchChangeEntity.setModifyTime(new Date());
		//失败情况
		if(waybillEntity == null && failReason != null){
			//设置原因
			waybillRfcBatchChangeEntity.setFailReason(failReason);
			//设置更改失败
			waybillRfcBatchChangeEntity.setChangeStatus(WaybillRfcConstants.WAYBILL_BATCH_RFC_FAIL);
			waybillRfcBatchChangeEntity.setTransportFeeChange(null);//更改失败，费用置空
			waybillRfcBatchChangeEntity.setProductChange(null);//更改失败，产品性质置空
		}else{
			if(waybillEntity != null){
				waybillRfcBatchChangeEntity.setProductChange(waybillEntity.getProductCode());
				//设置费用信息
				waybillRfcBatchChangeEntity.setTransportFeeChange(waybillEntity.getTotalFee());
				//设置更改成功
				waybillRfcBatchChangeEntity.setChangeStatus(WaybillRfcConstants.WAYBILL_BATCH_RFC_SUCCESS);
				//设置异常为N
				waybillRfcBatchChangeEntity.setFailReason(FossConstants.NO);
			}
		}
		waybillRfcBatchChangeDao.insertSelective(waybillRfcBatchChangeEntity);
	}


//	/**
//	 * 获取更改的子件信息
//	 */
//	private List<WaybillRelateDetailEntity> getChildWaybillRelateDetailEntityList(
//			ExpBatchChangeWeightDto expBatchChangeWeightDto) {
//		List<WaybillRelateDetailEntity> allWaybillRelateDetailEntityList = expBatchChangeWeightDto.getWaybillRelateDetailEntityList();
//		if(CollectionUtils.isNotEmpty(allWaybillRelateDetailEntityList)){
//			for(int i = 0 ; i < allWaybillRelateDetailEntityList.size() ; i ++ ){
//				//如果母件发生更改则剔除
//				WaybillRelateDetailEntity waybillRelateDetailEntity = allWaybillRelateDetailEntityList.get(i);
//				if(waybillRelateDetailEntity.getWaybillNo().equals(expBatchChangeWeightDto.getWaybillNo())){
//					allWaybillRelateDetailEntityList.remove(waybillRelateDetailEntity);
//				}
//			}
//			return allWaybillRelateDetailEntityList;
//		}
//		return null;
//	}
	
	/**
	 * 更改运单信息和实际承运信息
	 */
	private void updateChildWaybill(
			WaybillRelateDetailEntity waybillRelateDetailEntity,WaybillEntity waybillEntity, WaybillDto newWaybillDto,
			CurrentInfo currentInfo,Date currentDate) {
				//作废最新的运单
				waybillEntity.setActive(FossConstants.INACTIVE);
				waybillEntity.setModifyTime(currentDate);
				waybillManagerService.updateWaybill(waybillEntity);
				//新增一条有效的运单信息
				waybillEntity.setGoodsWeightTotal(waybillRelateDetailEntity.getWeight());
				waybillEntity.setGoodsVolumeTotal(waybillRelateDetailEntity.getVolume());
				waybillEntity.setProductCode(newWaybillDto.getWaybillEntity().getProductCode());
				waybillEntity.setCreateTime(currentDate);
				waybillEntity.setActive(FossConstants.ACTIVE);
				waybillEntity.setModifyTime(WaybillRfcConstants.MAX_DATE_RFC);
				waybillEntity.setModifyUser(currentInfo.getEmpName());
				waybillEntity.setModifyOrgCode(currentInfo.getCurrentDeptCode());
				waybillEntity.setModifyUserCode(currentInfo.getEmpCode());
				waybillDao.addWaybillEntity(waybillEntity);
				//更改子件的实际承运信息
				ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillRelateDetailEntity.getWaybillNo());
				actualFreightEntity.setWeight(waybillRelateDetailEntity.getWeight());
				actualFreightEntity.setVolume(waybillRelateDetailEntity.getVolume());
				actualFreightEntity.setModifyTime(currentDate);
				actualFreightEntity.setModifyUser(currentInfo.getEmpName());
				actualFreightService.updateWaybillActualFreight(actualFreightEntity);
		
	}

	/**
	 * 更改子母件关系信息
	 * @param waybillRelateDetailEntityList
	 */
	private void updateWaybillRelateDetailEntity(
			ExpBatchChangeWeightDto expBatchChangeWeightDto,CurrentInfo currentInfo) {
		//是子母件
		if(CollectionUtils.isNotEmpty(expBatchChangeWeightDto.getWaybillRelateDetailEntityList())){
			BigDecimal totalWeight = expBatchChangeWeightDto.getWeightChanged();
			BigDecimal totalVolume = expBatchChangeWeightDto.getVolumeChanged();
			//更改子母件体积、重量
			for(WaybillRelateDetailEntity waybillRelateDetailEntity : expBatchChangeWeightDto.getWaybillRelateDetailEntityList()){
				WaybillRelateDetailEntity needWaybillRelateDetailEntity = getNeedWaybillRelateDetailEntity(waybillRelateDetailEntity,totalWeight,totalVolume,currentInfo);
				waybillRelateDetailEntityDao.updateWaybillRelateDetailByWaybillNoSelective(needWaybillRelateDetailEntity);
			}
			//更改子母件总重量、总体积
			WaybillRelateDetailEntity newWaybillRelateDetailEntity = new WaybillRelateDetailEntity();
			newWaybillRelateDetailEntity.setGoodsWeightTotal(totalWeight);
			newWaybillRelateDetailEntity.setGoodsVolumeTotal(totalVolume);
			newWaybillRelateDetailEntity.setParentWaybillNo(expBatchChangeWeightDto.getWaybillNo());
			waybillRelateDetailEntityDao.updateWaybillRelateDetailByWaybillNoSelective(newWaybillRelateDetailEntity);
			
		}
	}
	
	/**
	 * 获取需要更改的子母件信息
	 * @param waybillRelateDetailEntity
	 * @param totalWeight
	 * @param totalVolume
	 * @param currentInfo
	 * @return
	 */
	private WaybillRelateDetailEntity getNeedWaybillRelateDetailEntity(
			WaybillRelateDetailEntity waybillRelateDetailEntity,
			BigDecimal totalWeight, BigDecimal totalVolume,CurrentInfo currentInfo) {
		WaybillRelateDetailEntity needWaybillRelateDetailEntity = new WaybillRelateDetailEntity();
		needWaybillRelateDetailEntity.setWaybillNo(waybillRelateDetailEntity.getWaybillNo());
		needWaybillRelateDetailEntity.setGoodsWeightTotal(totalWeight);
		needWaybillRelateDetailEntity.setGoodsVolumeTotal(totalVolume);
		needWaybillRelateDetailEntity.setWeight(waybillRelateDetailEntity.getWeight());
		needWaybillRelateDetailEntity.setVolume(waybillRelateDetailEntity.getVolume());
		needWaybillRelateDetailEntity.setModifyTime(new Date());
		needWaybillRelateDetailEntity.setModifyOrgCode(currentInfo.getCurrentDeptCode());
		needWaybillRelateDetailEntity.setModifyUser(currentInfo.getEmpName());
		needWaybillRelateDetailEntity.setModifyUserCode(currentInfo.getEmpCode());
		return needWaybillRelateDetailEntity;
	}

	/**
	 * 判断体积重是否发生了更改
	 * @author 136334-foss-bailei 
	 * @return
	 */
	private Boolean billWeightIsChanged(ExpBatchChangeWeightDto expBatchChangeWeightDto, WaybillDto oldWaybillDto){
		Boolean billWeightIsChanged = false;
		if(oldWaybillDto.getWaybillEntity() == null){
			billWeightIsChanged = true;
		}
		//获取waybillEntity的计费重量
		if(oldWaybillDto.getWaybillEntity() != null && oldWaybillDto.getWaybillEntity().getBillWeight()!= null && 
				oldWaybillDto.getWaybillEntity().getBillWeight().compareTo(BigDecimal.ZERO)>0){
			BigDecimal billWeight = oldWaybillDto.getWaybillEntity().getBillWeight();
			
			//获取更改后的计费重量
			BigDecimal changedBillWeight = expBatchChangeWeightDto.getWeightChanged();
			
			String prarentProductCode = "";
			if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(oldWaybillDto.getWaybillEntity().getProductCode())
					|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(oldWaybillDto.getWaybillEntity().getProductCode())){
				prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
			}
			
			if(expBatchChangeWeightDto.getVolumeChanged()!=null && 
					expBatchChangeWeightDto.getVolumeChanged().compareTo(BigDecimal.ZERO)>0){
				//根据重抛比计算体积重
				BigDecimal tempVolumeWeight = billCaculateService.validateWeightBubbleRate(oldWaybillDto.getWaybillEntity().getDeliveryCustomerCode(),
						oldWaybillDto.getWaybillEntity().getBillTime(), prarentProductCode, 
							expBatchChangeWeightDto.getVolumeChanged(), oldWaybillDto.getWaybillEntity().getReceiveOrgCode());
				if(changedBillWeight.compareTo(tempVolumeWeight)<0){
					changedBillWeight=tempVolumeWeight; 
					}
			}
			
			//比较
			if(billWeight != changedBillWeight){
				billWeightIsChanged = true;
			}
		}
		return billWeightIsChanged;
	}
	
	/**
	 * 将新值赋予新的WaybillDto中的WaybillEntity
	 * @author 136334-foss-bailei 
	 * @return
	 */
	private void setChangedWeight(WaybillDto newWaybillDto, ExpBatchChangeWeightDto changeDto){
		WaybillEntity newEntity = new WaybillEntity();
		BeanUtils.copyProperties(newWaybillDto.getWaybillEntity(), newEntity);
		//设置ID
		newEntity.setId(UUIDUtils.getUUID());
		//设置货物重量信息
		newEntity.setGoodsWeightTotal(changeDto.getWeightChanged() == null ? 
				newEntity.getGoodsWeightTotal() : changeDto.getWeightChanged());
		//设置货物体积信息
		newEntity.setGoodsVolumeTotal((changeDto.getVolumeChanged()==null) ? 
				newEntity.getGoodsVolumeTotal() :changeDto.getVolumeChanged());
		//设置装卸费信息
				/*newEntity.setServiceFee((changeDto.getServicefee()==null)?
						newEntity.getServiceFee():changeDto.getServicefee());*/
		//设置产品类型信息
		setNewProductCode(newEntity);
		ActualFreightEntity actualFreight = newWaybillDto.getActualFreightEntity();
		//设置实际承运信息表
		BeanUtils.copyProperties(newWaybillDto.getActualFreightEntity(), actualFreight);
		//设置货物重量信息
		actualFreight.setWeight(changeDto.getWeightChanged() == null ? 
				newEntity.getGoodsWeightTotal() : changeDto.getWeightChanged());
		//设置货物体积信息
		actualFreight.setVolume((changeDto.getVolumeChanged()==null) ? 
				newEntity.getGoodsVolumeTotal() : changeDto.getVolumeChanged());
		//设置运单基础数据信息
		newWaybillDto.setWaybillEntity(newEntity);
		newWaybillDto.setActualFreightEntity(actualFreight);
	}
	
	/**
	 * 计算新的产品性质
	 * @author 136334-foss-bailei 
	 * @return
	 */
	private void setNewProductCode(WaybillEntity newEntity){
		if(!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(newEntity.getProductCode())
				&& !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(newEntity.getProductCode())){
			return;
		}
		//子母件件数
		BigDecimal czmQty = CollectionUtils.isEmpty(newEntity.getWaybillNos())  ? BigDecimal.ONE : BigDecimal.valueOf(newEntity.getWaybillNos().size());
		//获取更改后的计费重量
		BigDecimal changedBillWeight = newEntity.getGoodsWeightTotal() == null ? BigDecimal.ZERO : newEntity.getGoodsWeightTotal().divide(czmQty,NumberConstants.NUMBER_3,BigDecimal.ROUND_HALF_UP);
		
		String prarentProductCode = "";
		if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(newEntity.getProductCode())
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(newEntity.getProductCode())){
			prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		}
		
		if(newEntity.getGoodsVolumeTotal()!=null && newEntity.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO)>0){
			//根据重抛比计算体积重
			BigDecimal tempVolumeWeight = billCaculateService.validateWeightBubbleRate(newEntity.getDeliveryCustomerCode(),
					newEntity.getBillTime(), prarentProductCode, newEntity.getGoodsVolumeTotal().divide(czmQty,NumberConstants.NUMBER_3,BigDecimal.ROUND_HALF_UP), newEntity.getReceiveOrgCode());
			if(changedBillWeight.compareTo(tempVolumeWeight)<0){
				changedBillWeight=tempVolumeWeight; 
			}
		}
		
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity(
				DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,ConfigurationParamsConstants.PKP_EXPRESS_WEIGHT_CUT,FossConstants.ROOT_ORG_CODE);
		if(config != null && config.getConfValue() != null ){
			BigDecimal confValue = new BigDecimal(config.getConfValue());
			if(changedBillWeight.compareTo(confValue) < 0){
				newEntity.setProductCode(WaybillConstants.PACKAGE);//普通快递
			}else{
				newEntity.setProductCode(WaybillConstants.ROUND_COUPON_PACKAGE);//3.60
			}
			//设置产品信息
			ProductEntity product = productService.getLevel3ProductInfo(newEntity.getProductCode());
			newEntity.setProductId(product == null ? newEntity.getProductCode() : product.getId());//产品ID
		}
	}
	
	/**
	 * 校验能否更改
	 * @author 136334-foss-bailei 
	 * @return
	 */
	/**
	 * @param expBatchChangeWeightDto
	 * @param oldWaybillDto
	 */
	private void validateCanChange(ExpBatchChangeWeightDto expBatchChangeWeightDto, WaybillDto oldWaybillDto){
		String waybillNo = expBatchChangeWeightDto.getWaybillNo();
		//判断运单是否上报过工单并同意
		boolean returnboolen= returnGoodsRequestEntityService.queryIsHandleByWayBillNo(waybillNo);
		if(returnboolen==true){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_NOT_CHANGE);
		}
		//判定运单基础数据是否为空
		if(oldWaybillDto == null || oldWaybillDto.getWaybillEntity() == null){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_NOT_EXSIT);
		}
		//这里的WaybillDto不可能为空,但是为了代码的健全性，还是需要进行判定
		if(oldWaybillDto == null || oldWaybillDto.getWaybillEntity() == null || oldWaybillDto.getActualFreightEntity() == null){
			//没有查询到有效的运单基础数据
			LOG.info("没有查询到有效的运单数据");
			throw new BusinessException(WaybillValidateException.WAYBILLENTITY_NULL);
		}
		//悟空单无法在FOSS更改
		if("Y".equals(oldWaybillDto.getWaybillEntity().getIsecs())){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_IS_ISECS);
		}
		//判定是否为电子面单
		if(!WaybillConstants.EWAYBILL.equals(oldWaybillDto.getActualFreightEntity().getWaybillType())){
			LOG.info(waybillNo+"不是电子面单");
			throw new BusinessException(WaybillValidateException.NOT_EWAYBILL_ORDER);
		}
		//是否有已经存在待审核更改单存在
		List<WaybillRfcEntity> rfcEntityList = waybillRfcDao.
			queryWaybillRfcAcceptByWaybillNo(waybillNo, WaybillRfcConstants.PRE_AUDIT);
		if(rfcEntityList !=null && BigDecimal.ZERO.compareTo(new BigDecimal(rfcEntityList.size()))<0){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_APPROVING);
		}
		
		//是否有已经存在待受理更改单存在
		rfcEntityList = waybillRfcDao.queryWaybillRfcAcceptByWaybillNo(waybillNo, 
				WaybillRfcConstants.PRE_ACCECPT);
		if(rfcEntityList !=null && BigDecimal.ZERO.compareTo(new BigDecimal(rfcEntityList.size()))<0){
				throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_ACCECPTED);
		}
		//PDA待补录无法进行补录
		if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(oldWaybillDto.getWaybillEntity().getPendingType())){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_IS_PDA_PENDING);
		}
		//进行走货路径数据的判定
		FreightRouteDto freightRouteDto = waybillManagerService.queryFreightRouteBySourceTarget(
				oldWaybillDto.getWaybillEntity().getCreateOrgCode(), oldWaybillDto.getWaybillEntity().getCustomerPickupOrgCode(), 
				oldWaybillDto.getWaybillEntity().getProductCode(), new Date());
		//要么这个走货路径为空或者小于2条数据自动报错无效的走货路径
		if(CollectionUtils.isEmpty(freightRouteDto.getFreightRouteLinelist()) 
				|| freightRouteDto.getFreightRouteLinelist().size() < 2){
			throw new WaybillRfcChangeException(WaybillValidateException.FREIGHT_ROUTE_NOT_FOUND);
		}
		/*	//加入时间的限制：同城次日6点后无法发生更改;非同城12点之前可以 发生更改
		//进行判定是否同城
		boolean cityWide = false;
		String firstCityCode = null; 
		List<FreightRouteLineDto> freightRouteLinelist = freightRouteDto.getFreightRouteLinelist();//获得走货路径list
		//判断是否有外场
		if(freightRouteLinelist != null && freightRouteLinelist.size() >= 2){
			//若只有2个，则一定为同城
			if(freightRouteLinelist.size() == 2){
				//若为同城则设置第二城市外场简称为目的场
				cityWide = true;
			} else {
				// 得到途径始发营业部编码和外场编码 , 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
				List<String> addressInfoList = new ArrayList<String>();
				//拼接走货路径
				for (int i=1;i<freightRouteLinelist.size();i++) {
					addressInfoList.add(freightRouteLinelist.get(i).getSourceCode());
				}
				//第一外场名称
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(addressInfoList.get(0));
				//出发外场编码对应的城市名称
				if(null != org){
					firstCityCode = StringUtil.defaultIfNull(org.getCityCode()).trim();
				}
				//外场所在城市名称
				String loadCityCode = "";
				//遍历集合（从第2个开始遍历），查找不同城市的外场编码
				for (int i=1; i<=(addressInfoList.size()-1); i++) {
					//如果不是同城
					if(!cityWide){
						OrgAdministrativeInfoEntity loadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(addressInfoList.get(i));
						if(null != loadOrg){
							loadCityCode = loadOrg.getCityCode();
						}
						//外场所在城市编码与出发外场所在城市不一样
						if(StringUtil.isNotEmpty(firstCityCode) && StringUtil.isNotEmpty(loadCityCode) && firstCityCode.equals(loadCityCode)){
							//找到则设置第二城市外场简称,直接退出
							cityWide = true;
						}
					}
				}
			}
		}
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		if(cityWide){
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(oldWaybillDto.getWaybillEntity().getCreateTime());
			cal3.add(Calendar.DATE, 1);
			cal3.set(Calendar.HOUR_OF_DAY, 6); //0点 
			cal3.set(Calendar.MINUTE, 0);//0分
			cal3.set(Calendar.SECOND, 0);//0秒
			long sixStandardDate = now.getTime() - cal3.getTime().getTime();
			LOG.info(myFmt2.format(cal3.getTime()));
			LOG.info(myFmt2.format(now));
			//同城6点过后无法发生重量体积变更
			if(sixStandardDate > 0){
				throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_CANNOT_RFC_OVER_6HOUR_PDA_PENDING);
			}
		}else{
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(oldWaybillDto.getWaybillEntity().getCreateTime());
			cal3.add(Calendar.DATE, 1);
			cal3.set(Calendar.HOUR_OF_DAY, 12); //0点 
			cal3.set(Calendar.MINUTE, 0);//0分
			cal3.set(Calendar.SECOND, 0);//0秒
			long sixStandardDate = now.getTime() - cal3.getTime().getTime();
			LOG.info(myFmt2.format(cal3.getTime()));
			LOG.info(myFmt2.format(now));
			//非同城12点过后无法发生重量体积变更
			if(sixStandardDate > 0){
				throw new WaybillRfcChangeException(WaybillRfcChangeException.WAYBILL_CANNOT_RFC_OVER_12HOUR_PDA_PENDING);
			}
		}
		*/
		/*
		 * 运单是否已付款
		 */
		if(repaymentService.isExistRepayment(waybillNo)){
			/*
			 * 运单已付款
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_HAS_PAID);
		}
		// 运单信息表中未找到
		if (oldWaybillDto == null || oldWaybillDto.getWaybillEntity() == null) {
			// 检查是否PDA未补录
			WaybillPendingEntity pendingEntity = waybillPendingDao.queryByWaybillNumber(waybillNo);
			if (pendingEntity == null) {
				/**
				 * 运单号不存在！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_NUMBER_NOT_EXIST);
			} else {
				/**
				 * 运单未补录，不能起草运单变更！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_NOT_ADDITIONAL_RECORDING_CAN_NOT_CHANGE);
			}
		}
		
		
		
		//pending waybill
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(oldWaybillDto.getWaybillEntity().getPendingType())) {
			/**
			 * 运单未补录，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_NOT_ADDITIONAL_RECORDING_CAN_NOT_CHANGE);
		}
		// 运单状态
		if (oldWaybillDto.getActualFreightEntity() == null) {
			/**
			 * 运单ActualFreight无数据，获取不到货物状态！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_ACTUAL_FREIGHT_NOT_DATA);
		}
		String waybillStatus = oldWaybillDto.getActualFreightEntity().getStatus();
		if (WaybillConstants.OBSOLETE.equals(waybillStatus)) {
			/**
			 * 运单已作废，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_CANCEL_CAN_NOT_CAHNGE);
		} else if (WaybillConstants.ABORTED.equals(waybillStatus)) {
			/**
			 * 运单已中止，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_STOP_CAN_NOT_CAHNGE);
		}
			
		// 查询运单签收状态
		WaybillSignResultEntity signResultEntity = new WaybillSignResultEntity();
		signResultEntity.setActive(FossConstants.YES);
		signResultEntity.setWaybillNo(waybillNo);
		signResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signResultEntity);
		if (signResultEntity != null && (SignConstants.SIGN_STATUS_ALL.equals(signResultEntity.getSignStatus()) || SignConstants.SIGN_STATUS_PARTIAL.equals(signResultEntity.getSignStatus()))) {
			/**
			 * 货物已签收，不能起草运单变更！
			 */
			throw new WaybillImportException(WaybillImportException.WAYBILL_ALREADY_SIGN_CAN_NOT_CHANGE);
		}

		List<String> waybillRfcStatus = new ArrayList<String>();//创建对象
		waybillRfcStatus.add(WaybillRfcConstants.PRE_AUDIT);
		waybillRfcStatus.add(WaybillRfcConstants.PRE_ACCECPT);
		WaybillRfcEntity rfcEntity = waybillRfcDao.queryRfcEntityByWaybillId(oldWaybillDto.getWaybillEntity().getId(), waybillRfcStatus);
		if (rfcEntity != null) {
			if (WaybillRfcConstants.PRE_AUDIT.equals(rfcEntity.getStatus())) {
				/**
				 * 该运单有运单变更单还未被审核，不能起草变更运单！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILL_HAVE_NOT_AUDIT_CAN_NOT_CHANGE);
			} else if (WaybillRfcConstants.PRE_ACCECPT.equals(rfcEntity.getStatus())) {
				/**
				 * 该运单有运单变更单还未被受理，不能起草变更运单！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILLRFC_HAVE_NOT_ACCEPT_CAN_NOT_CHANGE);
			}
		}

		// 非本部门运单包括 开单部门、收货部门
		String loginOrgCode = oldWaybillDto.getCurrentInfo().getCurrentDeptCode();
		if (loginOrgCode.equals(oldWaybillDto.getWaybillEntity().getCreateOrgCode()) || 
				loginOrgCode.equals(oldWaybillDto.getWaybillEntity().getReceiveOrgCode())) {
			boolean isPayment = repaymentService.isExistRepayment(waybillNo);
			if (isPayment) {
				/**
				 * 该运单已进行结清货款操作，如需更改请联系到达部门进行反结清货款操作！
				 */
				throw new WaybillImportException(WaybillImportException.WAYBILLRFC_ALREADY_SETTLE_CAN_NOT_CHANGE);
			}
			
			try {
				waybillPickupService.canChange(waybillNo);
			} catch (SettlementException e) {
				throw new WaybillImportException(messageBundle.getMessage(e.getErrorCode(), e.getErrorArguments()));
				
			}
		}else {
			/**
			 * 非本部门运单，不能起草运单变更！
			 */
			//throw new WaybillImportException(WaybillImportException.HAVE_NOT_AUTHORITY_TO_CHANGE);
		}	
		
	}
	
	/**
	 * 计算费用
	 * @author 136334-foss-bailei 
	 * @return
	 */
	private void caculateChangedFee(WaybillDto waybillDto){
		waybillExpressService.calculateExpressAllFee(waybillDto, WaybillConstants.YES);
	}
	
	/**
	 * 组装WaybillRfcSubmitDto
	 * @author 136334-foss-bailei 
	 * @return
	 */
	private WaybillRfcSubmitDto getBatchChangeWeightSubmitDto(WaybillDto oldWaybillDto, WaybillDto newWaybillDto,List<WaybillRelateDetailEntity> waybillRelateDetailEntityList){
		WaybillRfcSubmitDto submitDto = new WaybillRfcSubmitDto();
		
		//修改的是子母件
		if(CollectionUtils.isNotEmpty(waybillRelateDetailEntityList)){
			BigDecimal goodsWeightTotal = BigDecimal.ZERO;
			BigDecimal goodsVolumeTotal = BigDecimal.ZERO;
			//如果修改单号里面包含母件单号则取修改之后的重量体积
			boolean isNotChangeParent = true;
			for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybillRelateDetailEntityList){
				if(waybillRelateDetailEntity.getWaybillNo().equals(newWaybillDto.getWaybillEntity().getWaybillNo())){
					goodsWeightTotal = waybillRelateDetailEntity.getWeight();
					goodsVolumeTotal = waybillRelateDetailEntity.getVolume();
					isNotChangeParent = false;
					break;
				}
			}
			//否则则取子母件关系表中的单件重量体积
			if(isNotChangeParent){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("waybillNo",newWaybillDto.getWaybillEntity().getWaybillNo());
				params.put("active",FossConstants.ACTIVE);
				List<WaybillRelateDetailEntity> parentWaybillRelateDetailList = waybillRelateDetailEntityDao.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
				if(CollectionUtils.isNotEmpty(parentWaybillRelateDetailList)){
					goodsWeightTotal = parentWaybillRelateDetailList.get(0).getWeight();
					goodsVolumeTotal = parentWaybillRelateDetailList.get(0).getVolume();
				}
			}
			newWaybillDto.getWaybillEntity().setGoodsWeightTotal(goodsWeightTotal);
			newWaybillDto.getWaybillEntity().setGoodsVolumeTotal(goodsVolumeTotal);
		}
		//设置WaybillDto
		submitDto.setWaybillDto(newWaybillDto);
		
		//设置WaybillRfcChangeDetailDto
		List<WaybillRfcChangeDetailDto> detailVos = new ArrayList<WaybillRfcChangeDetailDto>();
		detailVos = getWaybillRfcChangeDetailDto(oldWaybillDto, newWaybillDto);
		submitDto.setRfcChangeDetailDtos(detailVos);
		
		//设置WaybillRfcEntity
		WaybillRfcEntity waybillRfcEntity = new WaybillRfcEntity();
		waybillRfcEntity.setId(UUIDUtils.getUUID());
		//运单号为Old的运单号
		waybillRfcEntity.setWaybillNo(oldWaybillDto.getWaybillEntity().getWaybillNo());
		//变更来源全部定义为
		waybillRfcEntity.setRfcSource(WaybillRfcConstants.INSIDE_REQUIRE);
		//变更类型全部定义为：更改
		waybillRfcEntity.setRfcType(WaybillRfcConstants.INSIDE_CHANGE);
		//变更原因全部定义为
		waybillRfcEntity.setRfcReason(WaybillRfcConstants.WAYBILL_BATCHCHANGE_REASON);
		waybillRfcEntity.setNotes(WaybillRfcConstants.WAYBILL_BATCHCHANGE_REASON);
		// 定义工号变量
		String userCode = null;
		String currentDeptName = null;
		String userName = null;
		// 获取当前登录人工号
		// 如果不为空则 赋值变量 userCode
		if (FossUserContext.getCurrentUser() != null && FossUserContext.getCurrentUser().getEmployee() != null) {
			userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
			currentDeptName = FossUserContext.getCurrentUser().getEmployee().getOrgName();
			userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		}
		waybillRfcEntity.setDraftOrgCode(userCode);
		waybillRfcEntity.setDraftOrgName(currentDeptName);
		waybillRfcEntity.setDrafter(userName);
		waybillRfcEntity.setDrafterCode(userCode);
		waybillRfcEntity.setDraftTime(new Date());
		waybillRfcEntity.setNotes(WaybillRfcConstants.WAYBILL_BATCHCHANGE_REASON);
		waybillRfcEntity.setStatus(WaybillRfcConstants.PRE_AUDIT);
		waybillRfcEntity.setOperator(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperatorCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperateOrgCode(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperateOrgName(WaybillRfcConstants.WAYBILLRFC_NULL_DEFAULT);
		waybillRfcEntity.setOperateTime(new Date());
		//CN-302 
		waybillRfcEntity.setOldVersionWaybillId(oldWaybillDto.getWaybillEntity().getId());
		waybillRfcEntity.setNewVersionWaybillId(newWaybillDto.getWaybillEntity().getId());
		waybillRfcEntity.setIsFinanceChange(FossConstants.NO);
		//变更内容
		StringBuffer sb = new StringBuffer();
		if(CollectionUtils.isNotEmpty(detailVos)){
			for(WaybillRfcChangeDetailDto detailDto : detailVos){
				if(FossConstants.YES.equals(detailDto.getVisible())){
					sb.append(detailDto.getRfcItems());
					sb.append(":");
					sb.append(detailDto.getBeforeRfcInfo());
					sb.append("->");
					sb.append(detailDto.getAfterRfcInfo());
					sb.append(";");
				}
			}
		}
	    //canshu
	    waybillRfcEntity.setChangeItems(sb.toString());
		//是否给发货人发送短信
		waybillRfcEntity.setDeliverSms(FossConstants.NO);
		//是否给收货人发送短信
		waybillRfcEntity.setReceiverSms(FossConstants.NO);
		//是否目的站修改
		waybillRfcEntity.setIsChangeDestination(FossConstants.NO);
		//是否需要财务核销
		waybillRfcEntity.setNeedWriteOff(FossConstants.NO);
		//是否变更运单号
		waybillRfcEntity.setIsChangeWaybillNo(FossConstants.NO);
		//是否自动受理
		waybillRfcEntity.setIsLabourHandle(FossConstants.YES);
		LOG.info("更改数据："+ReflectionToStringBuilder.toString(waybillRfcEntity));
	    submitDto.setRfcEntity(waybillRfcEntity);
		return submitDto;
	}

	
	/**
	 * 执行更改并自动审核通过
	 * @author 136334-foss-bailei 
	 * @return
	 */
	private WaybillRfcEntity commitChangeWeight(WaybillRfcSubmitDto submitDto, CurrentInfo currentInfo){
		// 验证是否允许提交
		//checkWaybillRfcMatched(submitDto.getRfcEntity().getWaybillNo(), currentInfo.getCurrentDeptCode(),submitDto);
		
		// 校验开单规则
		// 1、单号更改后，判断更改后单号是否已经存在
		// 2、必填项更改后，判断更改后是否为空

		fillUpExtendValue(submitDto);
		
		/**
		 * 是否修改单号，是在fillUpExtendValue之后复制，由于校验数据完整性方法需要用到该标识，需要在fillUpExtendValue之后执行
		 */
		checkDataIntegrity(submitDto);

		// 当前系统时间
		Date currentDate = new Date();

		// 更改单主表
		WaybillRfcEntity rfcEntity = submitDto.getRfcEntity();

		// 运单信息
		WaybillDto waybillDto = submitDto.getWaybillDto();
		
		//修改创建人为更改起草人
		waybillDto.getWaybillEntity().setModifyUser(currentInfo.getEmpCode());
		waybillDto.getWaybillEntity().setModifyUserCode(currentInfo.getEmpCode());
		waybillDto.getWaybillEntity().setModifyOrgCode(currentInfo.getCurrentDeptCode());
		
		waybillManagerService.appendTempWaybillAfterChanged(waybillDto, currentDate);

		// 设置新运单ID
		rfcEntity.setNewVersionWaybillId(waybillDto.getWaybillEntity().getId());
		
		//设置waybillRfcEntity是否涉及目的站变更
		// 新增更改单变更项
		List<WaybillRfcChangeDetailDto> rfcChangeDetailDtos = submitDto.getRfcChangeDetailDtos();
		for(WaybillRfcChangeDetailDto detailDto : rfcChangeDetailDtos){
			if("customerPickupOrgName".equals(detailDto.getPropertyName())){
				rfcEntity.setIsChangeDestination(FossConstants.YES);
				break;
			}
		}
		
		addWaybillRfcEntity(rfcEntity, currentInfo, currentDate);

		waybillRfcVarificationService.addWaybillRfcActionHistory(rfcEntity, currentDate);

		addWaybillRfcChangeDetail(rfcChangeDetailDtos, currentInfo, currentDate, rfcEntity);
		
		//自动审核
		waybillRfcVarificationService.agreeWaybillRfcCheckAuto(rfcEntity, currentInfo, currentDate);

		// 更改单总数（简单计数器）
		businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_TOTAL_COUNT, currentInfo);
		
		// 更改单内部数（简单计数器）
		if(WaybillRfcConstants.INSIDE_REQUIRE.equals(rfcEntity.getRfcSource())){
			businessMonitorService.counter(BusinessMonitorIndicator.WAYBILLRFC_INTERNAL_COUNT, currentInfo);
		}
		return rfcEntity;	
	}
	
	
	private List<WaybillRfcChangeDetailDto> getWaybillRfcChangeDetailDto(WaybillDto oldWaybillDto, WaybillDto newWaybillDto){
		List<WaybillRfcChangeDetailDto> waybillRfcChangeDetailDtoList= new ArrayList<WaybillRfcChangeDetailDto>();
		WaybillRfcChangeDetailDto waybillRfcChangeDetailDto = null;
		WaybillEntity oldWaybillEntity = oldWaybillDto.getWaybillEntity();
		WaybillEntity newWaybillEntity = newWaybillDto.getWaybillEntity();
		
		//初始化属性和汉字名称的映射关系
		Map<String, String> waybillRfcChangeDetailColumnMap = new LinkedHashMap<String, String>();
		initPropertiesChineseMap(waybillRfcChangeDetailColumnMap);
		
		// 初始化更改信息MAP
		Map<String, WaybillRfcChangeDetailDto> waybillRfcChangeDetailMap = new LinkedHashMap<String, WaybillRfcChangeDetailDto>();
		initRfcChangeDetailMap(waybillRfcChangeDetailColumnMap, waybillRfcChangeDetailMap);
		
		//对比差异
		Field[] field = oldWaybillEntity.getClass().getDeclaredFields();  
		for(int j = 0; j < field.length; j++){  
			try{
				waybillRfcChangeDetailDto = new WaybillRfcChangeDetailDto();
				//获取属性名
				String propertyName = field[j].getName();
				
				if(propertyName.equals("serialVersionUID")){
					continue;
				}
				String propertyNameUpperCase = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
				oldWaybillEntity.getClass().getMethod("get" + propertyNameUpperCase);
				
				if(waybillRfcChangeDetailMap.containsKey(propertyName)){
					//保留新属性名
					String newPropertyName = propertyName;
					//是否运输信息变化
					boolean isTransportChanged = false;
					//是否运单号变化
					boolean isWaybillChanged = false;
//					cn_355更改电子运单综合查询运单更改信息显示有误,原新值原始值命名有误
					//新值
					Object newValueObj = PropertyUtils.getProperty(newWaybillEntity, newPropertyName);
					//原始值
					Object oldValueObj = PropertyUtils.getProperty(oldWaybillEntity, propertyName);
					//运输信息变化，变化后值为空则认为没有变化
					if (((newValueObj == null || StringUtil.isEmpty(String.valueOf(newValueObj))) && isTransportChanged)
							||((newValueObj == null || StringUtil.isEmpty(String.valueOf(newValueObj))) && isWaybillChanged)
							|| ((newValueObj == null || StringUtil.isEmpty(String.valueOf(newValueObj))) && (oldValueObj == null || StringUtil.isEmpty(String.valueOf(oldValueObj))))){
						waybillRfcChangeDetailMap.put(propertyName, null);
					//运单号变化，变化后值为空则认为没有变化
					} else if (String.valueOf(newValueObj).equals(String.valueOf(oldValueObj))) {
						waybillRfcChangeDetailMap.put(propertyName, null);
						// 返单类别在map中的名字是QS - BUG-58305
						if(propertyName.equals("returnBillType")){
							waybillRfcChangeDetailMap.put("QS", null);
						}
					}else{
						// Boolean类型的统一转换为“是”、“否”
						Class<?> clazz = PropertyUtils.getPropertyType(oldWaybillEntity, propertyName);
						//新值
						String newValue = null;
						//旧值
						String oldValue = null;
						
						//对于bigdecimal的比较由于会精度问题导致不同 所以特殊处理
						if(newValueObj instanceof BigDecimal && oldValueObj instanceof BigDecimal ){
							newValue = ((BigDecimal)newValueObj).stripTrailingZeros().toPlainString();
							oldValue = ((BigDecimal)oldValueObj).stripTrailingZeros().toPlainString();
						}else{
							//转换后新值
							newValue = convertValueToStr(newValueObj, clazz);
							//转换后旧值
							oldValue = convertValueToStr(oldValueObj, clazz);
						}
						//如果新的值和老的值不一样 才会显示在明细信息列表中
						if(!newValue.equals(oldValue)){
							// 运单号修改
							if ("WaybillNo".equals(propertyName)) {
								propertyName = "waybillNo";
								isWaybillChanged = true;
							}
							//WaybillRfcChangeDetailDto WaybillRfcChangeDetailDto = null;
							if (waybillRfcChangeDetailMap.get(propertyName) == null) {
								//将原值设置到变更前项上
								//旧值
								waybillRfcChangeDetailDto.setBeforeRfcInfo(oldValue);
								//将新值添加到变更后项
								waybillRfcChangeDetailDto.setAfterRfcInfo(newValue);
								//从对照中文表中设置变更项名称
								waybillRfcChangeDetailDto.setRfcItems(waybillRfcChangeDetailColumnMap.get(propertyName));
								waybillRfcChangeDetailDto.setPropertyName(propertyName);
								if(clazz == BigDecimal.class){
									//金额变化特殊标记,更改单提交后会再次单独存一张表
									waybillRfcChangeDetailDto.setChargeChange(FossConstants.YES);
								}
								waybillRfcChangeDetailMap.put(propertyName, waybillRfcChangeDetailDto);
							} else {
								waybillRfcChangeDetailDto = waybillRfcChangeDetailMap.get(propertyName);
							}
							//设置是否可见
							waybillRfcChangeDetailDto.setVisible(FossConstants.YES);
						}
					}
				}
			}catch(Exception e){
				LOG.error(e.getMessage(), e);//日志记录
		}
		if(waybillRfcChangeDetailDto !=null && StringUtils.isNotEmpty(waybillRfcChangeDetailDto.getPropertyName())){
			waybillRfcChangeDetailDtoList.add(waybillRfcChangeDetailDto);
		}
	}
		return waybillRfcChangeDetailDtoList;
	}
	
	private void initRfcChangeDetailMap(Map<String, String> waybillRfcChangeDetailColumnMap,
			Map<String, WaybillRfcChangeDetailDto> waybillRfcChangeDetailMap) {
		// 初始化更改信息MAP
		for (String key : waybillRfcChangeDetailColumnMap.keySet()) {
			waybillRfcChangeDetailMap.put(key, null);
		}
	}
	
	private String convertValueToStr(Object oldValueObj, Class<?> clazz) {
		//boolean类型转换
		if(clazz == Boolean.class){
			return (oldValueObj==null || !((Boolean)oldValueObj).booleanValue()) ? WaybillConstants.NO : WaybillConstants.YES;
		}else if(oldValueObj == null){
			return "-";
		}else{
			String val = oldValueObj.toString();
			if(StringUtil.isEmpty(val)){
				return "-";
			}else{
				return val;
			}
		}
	}
	
	private void initPropertiesChineseMap(Map<String, String> waybillRfcChangeDetailColumnMap) {
		
		//运单号
		waybillRfcChangeDetailColumnMap.put("waybillNo", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.waybillNo"));
		//运输性质
		waybillRfcChangeDetailColumnMap.put("productCode", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.productCode"));
		//对外备注
		waybillRfcChangeDetailColumnMap.put("outerNotes", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.outerNotes"));
		//对内备注
		waybillRfcChangeDetailColumnMap.put("innerNotes", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.innerNotes"));
		//货物名称
		waybillRfcChangeDetailColumnMap.put("goodsName", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.goodsName"));
		//包装
		waybillRfcChangeDetailColumnMap.put("goodsPackage", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.goodsPackage"));
		//件数
		waybillRfcChangeDetailColumnMap.put("goodsQtyTotal", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.goodsQtyTotal"));
		//重量
		waybillRfcChangeDetailColumnMap.put("goodsWeightTotal", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.goodsWeightTotal"));
		//尺寸
		waybillRfcChangeDetailColumnMap.put("goodsSize", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.goodsSize"));
		//体积
		waybillRfcChangeDetailColumnMap.put("goodsVolumeTotal", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.goodsVolumeTotal"));
		//储运事项
		waybillRfcChangeDetailColumnMap.put("transportationRemark", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.transportationRemark"));
		//保价申明价值
		waybillRfcChangeDetailColumnMap.put("insuranceAmount", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.insuranceAmount"));
		//保价费
		waybillRfcChangeDetailColumnMap.put("insuranceFee", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.insuranceFee"));
		//代收货款
		waybillRfcChangeDetailColumnMap.put("codAmount", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.codAmount"));
		//代收手续费
		waybillRfcChangeDetailColumnMap.put("codFee", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.codFee"));
		//退款类型
		waybillRfcChangeDetailColumnMap.put("refundType", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.refundType"));
		//收款人
		waybillRfcChangeDetailColumnMap.put("accountName", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.accountName"));
		//收款账号
		waybillRfcChangeDetailColumnMap.put("accountCode", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.accountCode"));
		//包装费
		waybillRfcChangeDetailColumnMap.put("packageFee", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.packageFee"));
		//装卸费
		waybillRfcChangeDetailColumnMap.put("serviceFee", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.serviceFee"));
		//公布价运费
		waybillRfcChangeDetailColumnMap.put("transportFee", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.transportFee"));
		//优惠费用
		waybillRfcChangeDetailColumnMap.put("promotionsFee", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.promotionsFee"));
		//费用合计
		waybillRfcChangeDetailColumnMap.put("totalFee", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.totalFee"));
		//付款方式
		waybillRfcChangeDetailColumnMap.put("paidMethod", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.paidMethod"));
		//到付金额
		waybillRfcChangeDetailColumnMap.put("toPayAmount", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.toPayAmount"));
		//现付金额
		waybillRfcChangeDetailColumnMap.put("prePayAmount", messageBundle.getMessage("pkp.waybill.waybillrfc.batchChangeWeight.item.prePayAmount"));
		
		/*
		//由于时间限制以及需求，对发件收件人的更改，不予处理；对非快递项目，不予处理
		
		//运单号
		waybillRfcChangeDetailColumnMap.put("waybillNo", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.waybillNo",""));
		//运输性质
		waybillRfcChangeDetailColumnMap.put("productCode", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.productCode",""));
		//对外备注
		waybillRfcChangeDetailColumnMap.put("outerNotes", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.outerNotes",""));
		//对内备注
		waybillRfcChangeDetailColumnMap.put("innerNotes", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.innerNotes",""));
		//货物名称
		waybillRfcChangeDetailColumnMap.put("goodsName", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.goodsPackage",""));
		//包装
		waybillRfcChangeDetailColumnMap.put("goodsPackage", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.goodsQtyTotal",""));
		//件数
		waybillRfcChangeDetailColumnMap.put("goodsQtyTotal", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.goodsWeightTotal",""));
		//重量
		waybillRfcChangeDetailColumnMap.put("goodsWeightTotal", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.goodsSize",""));
		//尺寸
		waybillRfcChangeDetailColumnMap.put("goodsSize", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.goodsVolumeTotal",""));
		//体积
		waybillRfcChangeDetailColumnMap.put("goodsVolumeTotal", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.transportationRemark",""));
		//储运事项
		waybillRfcChangeDetailColumnMap.put("transportationRemark", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.transportFee",""));
		//保价申明价值
		waybillRfcChangeDetailColumnMap.put("insuranceAmount", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.insuranceAmount",""));
		//保价费
		waybillRfcChangeDetailColumnMap.put("insuranceFee", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.insuranceFee",""));
		//代收货款
		waybillRfcChangeDetailColumnMap.put("codAmount", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.codAmount",""));
		//代收手续费
		waybillRfcChangeDetailColumnMap.put("codFee", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.codFee",""));
		//退款类型
		waybillRfcChangeDetailColumnMap.put("refundType", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.refundType",""));
		//收款人
		waybillRfcChangeDetailColumnMap.put("accountName", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.accountName",""));
		//收款账号
		waybillRfcChangeDetailColumnMap.put("accountCode", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.accountCode",""));
		//包装费
		waybillRfcChangeDetailColumnMap.put("packageFee", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.packageFee",""));
		//装卸费
		waybillRfcChangeDetailColumnMap.put("serviceFee", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.serviceFee",""));
		//公布价运费
		waybillRfcChangeDetailColumnMap.put("transportFee", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.promotionsFee",""));
		//优惠费用
		waybillRfcChangeDetailColumnMap.put("promotionsFee", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.promotionsFee",""));
		//费用合计
		waybillRfcChangeDetailColumnMap.put("totalFee", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.totalFee",""));
		//付款方式
		waybillRfcChangeDetailColumnMap.put("paidMethod", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.paidMethod",""));
		//到付金额
		waybillRfcChangeDetailColumnMap.put("toPayAmount", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.toPayAmount",""));
		//现付金额
		waybillRfcChangeDetailColumnMap.put("prePayAmount", messageBundle.getMessage(
				"pkp.waybill.waybillrfc.batchChangeWeight.item.prePayAmount",""));

		*/
		
		
		/*
		// zxy 20130927 BUG-56141 start 修改：其他费用code不全，写死在代码中不利于以后增加费用，改成查数据库
		// 其他费用CODE
		PriceEntity entityParam = new PriceEntity();
		entityParam.setActive(FossConstants.YES);
		entityParam
				.setRefCode(PricingConstants.PriceEntityConstants.PRICING_CODE_QT);
		List<PriceEntity> priceEntityLst = priceEntryDao
				.findPriceEntityPagingByCondition(entityParam, 0, 200);
		if (priceEntityLst != null && priceEntityLst.size() > 0) {
			for (int i = 0; i < priceEntityLst.size(); i++) {
				productCodeList.add(priceEntityLst.get(i).getCode());
			}
			productCodeList.add(PricingConstants.PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ);
		} else {
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_SHSL);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_SHJC);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_CY);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_JH);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_QS);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_CCF);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_QT);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_BZ);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_ZZ);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_GGF);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_PXZZF);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_DWTBF);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_CCDDJS);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_KYYFCJ);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_RYFJ);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_ZHXX);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_QTFYJS);
			productCodeList
					.add(PricingConstants.PriceEntityConstants.PRICING_CODE_DZYHQ);

		}
		// zxy 20130927 BUG-56141 end 修改：其他费用code不全，写死在代码中不利于以后增加费用，改成查数据库
		*/
	}
	
	@Override
	public void canDebit(String deliveryCustomerCode, String receiveOrgCode) {
		waybillPickupService.canDebit(deliveryCustomerCode, receiveOrgCode);
	}
	
	/**
	 * 插入转运或返货目的站记录
	 * 
	 * @author foss-206860
	 */
	@Override
	public void addWaybillRfcTransferEntity(WaybillRfcTranferEntity rfcTranferEntity) {
		waybillRfcDao.addWaybillRfcTransferEntity(rfcTranferEntity);//新增更改单主表
	}

	@Override
	public List<WaybillRfcTranferEntity> queryWaybillRfcTransferEntity(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		return waybillRfcDao.queryWaybillRfcTransferEntity(waybillRfcTranferEntity);
	}
	@Override
	public List<WaybillRfcTranferEntity> queryRfcTransferHistory(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		return waybillRfcDao.queryRfcTransferHistory(waybillRfcTranferEntity);
	}
	
	/**
	 * @项目：家装项目
	 * @功能：根据单号查询交货确认状态
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-10-17下午16:18
	 */
	public String queryWaybillState(String number){
		return waybillRfcDao.queryWayBillState(number);
	}
	@Override
	public List<WaybillRfcEntity> queryRecentRfc(String waybillNo) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		return waybillRfcDao.queryRecentRfc(waybillNo);
	}
	
	/**
	 * liding add for NCI
	 * 验证付款方式为银行卡的运单是否能提交
	 * 图片开单或者集中开单时，如果代刷卡中已有已支付的数据,不允许更改收货部门
	 * @param submitDto
	 */
	private void checkNCIPaidMehod(WaybillRfcSubmitDto submitDto) {

		// 更改单主表
		WaybillRfcEntity rfcEntity = submitDto.getRfcEntity();
		// 处理新旧订单DTO
		WaybillDto newVersionDto = submitDto.getWaybillDto();
		WaybillDto oldVersionDto = waybillManagerService
				.getWaybillDtoById(rfcEntity.getOldVersionWaybillId());

		// 开单付款方式为银行卡的,或者更改为银行卡
		boolean isCreateWaybillCardPay = WaybillConstants.CREDIT_CARD_PAYMENT
				.equals(oldVersionDto.getWaybillEntity().getPaidMethod());
		boolean isChangeWaybillCardPay = WaybillConstants.CREDIT_CARD_PAYMENT
				.equals(newVersionDto.getWaybillEntity().getPaidMethod());
		//如果是图片或者集中开单
		boolean isPickupCentralized = FossConstants.YES.equals(oldVersionDto
				.getWaybillEntity().getPickupCentralized());
		//收货部门变更
		boolean isChangeReciveOrg = !StringUtils.equals(oldVersionDto
				.getWaybillEntity().getReceiveOrgCode(), newVersionDto
				.getWaybillEntity().getReceiveOrgCode()); 
		
		
		// 提示错误信息
		String errMsg = "运单更改失败！\n原因：无法查询待刷卡数据";
		// 集中/图片开单付款方式为银行卡并且收货部门变更
		if ((isCreateWaybillCardPay || isChangeWaybillCardPay)
				&& isPickupCentralized && isChangeReciveOrg) {
			boolean isPaid = false;
			try {
				RequestDO requestDO = new RequestDO();
				requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".addWSCWayBillData");
				requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
				requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
				requestDO.setSourceBillNos(newVersionDto.getWaybillEntity().getWaybillNo());
				VestResponse response=null;
				// 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
				try {
				    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
				} catch (Exception e) {
					throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
				}
				if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
					List<VestBatchResult> batchResults = response.getVestBatchResult();
					List<String> wyabillNosCUBC=null;
					for (int i = 0; i < batchResults.size(); i++) {
						if(Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResults.get(i).getVestSystemCode())){
							wyabillNosCUBC=batchResults.get(i).getVestObject();
						}
					}
					for (int i = 0; i < batchResults.size(); i++) {
						if (batchResults.get(i).getVestSystemCode().equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS)) {
							isPaid = wscWayBillManageService.wayBillIsAlreadySwiped(newVersionDto.getWaybillEntity().getWaybillNo());
						}else if (Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResults.get(i).getVestSystemCode())) {
							 List<TradeDO> queryBySourceBillNOsToCUBC = queryBySourceBillNOsToCUBC(wyabillNosCUBC);
							if (queryBySourceBillNOsToCUBC.size()>0) {
								isPaid = true;
							}else {
								isPaid = false;
							}
						}
					}
				}	
				if (isPaid) {
					errMsg = "运单更改失败！\n原因：集中/图片开单付款方式为银行卡并且有已支付数据的情况下无法更改收货部门";
					throw new WaybillImportException(errMsg);
				}
			} catch (Exception e) {
				// 添加异常日志
				LOG.error("Excepton", e);
				throw new WaybillImportException(errMsg);
			}
		}

	}
	private List<TradeDO> queryBySourceBillNOsToCUBC(List<String> sourceBillNosList){
	    Map<String, Object> toCubcMap=new HashMap<String, Object>();
	    toCubcMap.put("sourceBillNosList", sourceBillNosList);
	    String requestparams=JSON.toJSONString(toCubcMap);
	    LOG.info("cubc的访问接口queryBySourceBillNOsToCUBC方法的json信息"+requestparams);
	    try {
	    	return grayScaleService.grayQueryOrderByNo(requestparams);
		} catch (Exception e) {
			LOG.info("cubc的访问接口queryBySourceBillNOsToCUBC方法时异常");
			return null;
		}
	}
	/**
     * 根据运单更改单id,查询更改单变更信息
     * @param waybillRfcId
     * @return
     * @author 311417 wangfeng
     * @date 2016-4-22
     */
    public String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId) {
        return  waybillRfcDao.queryRfcChangeItemsByWaybillRfcId(waybillRfcId);
    }
    
    /**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:26:47
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    @Override
    public String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime ){
    	return waybillRfcDao.queryCanPayServiceFeeByCodeAndTime(code, billTime);
    }
}