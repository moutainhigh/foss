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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/CalculateAction.java
 * 
 * FILE NAME        	: CalculateAction.java
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
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.validation.IValidationListener;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.commons.validation.ValidationErrorEvent;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.changing.client.listener.WaybillInfoBindingListener;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changing.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changing.client.ui.internal.transport.TransferChangedRecordPanel.TransferRecordTableModel;
import com.deppon.foss.module.pickup.changing.client.utils.Common;
import com.deppon.foss.module.pickup.changing.client.utils.WaybillDtoFactory;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.dto.BusinessCostEnum;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IAddedFeeCalculateService;
import com.deppon.foss.module.pickup.common.client.service.impl.AddedFeeCalculateService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.ValidateVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.print.labelprint.util.Log;
import com.deppon.foss.util.define.FossConstants;
 
/**
 * 
 * 计算运单更改后合计总费用
 * 1、校验WaybillInfoDescriptor类
 * 2、内部基本校验
 * 3、计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
 * 4、设置提交按钮可用
 * 
 * 变更单校验：
 *	validateUIError
 *	getValiationErrors
 *	filterSuccessValiation
 *
 * 迁移开单CalculateAction方法：
 * 	actionPerformed（将获取提货方式提取成getFinalReceiveMethod方法，根据变更、转运、返货分别拿不同的属性）
 * 	calculateFee（有修改，增加转运、返货不计算公布价）
 * 	setServiceChargeState
 *	channelServiceFee
 *	lowestServiceFee
 *	executeCoupon
 *	getCouponInfoDto
 *	setPrePayArriveEditState
 *	validate
 *	validateAirGoodsType
 *	validateAir
 *	validateInsurance（将获取提货方式提取成getFinalReceiveMethod方法，根据变更、转运、返货分别拿不同的属性）
 *	validateVehicleNumber
 *	validateWeightVolume
 *	validateCod
 *	validatePack
 *	validatePaymentMode（将获取提货方式提取成getFinalReceiveMethod方法，根据变更、转运、返货分别拿不同的属性）
 *	validateCustomer（将获取提货方式提取成getFinalReceiveMethod方法，根据变更、转运、返货分别拿不同的属性）
 *	validateVW（将获取目的站提取成getFinalCustomerPickupOrgCode方法，根据变更、转运、返货分别拿不同的属性）
 *	validateProduct
 *	validateDistination（将获取目的站提取成getFinalCustomerPickupOrgCode方法，根据变更、转运、返货分别拿不同的属性）
 *	isBeBebt
 *	calculateTransportFee
 *	getProductPriceDto（给表格【设置折扣优惠】不需要）
 *	setDiscount
 *	setBillWeight
 *	calculateValueAdd（计算增值服务费包含转运费、返货费）
 *	getInsurance（给表格【设置折扣优惠】不需要）
 *	getInsuranceParam（将获取目的站提取成getFinalCustomerPickupOrgCode方法，根据变更、转运、返货分别拿不同的属性）
 *	setInsurance
 *	getCod
 *	setCod
 *	getQueryParam（将获取目的站提取成getFinalCustomerPickupOrgCode方法，根据变更、转运、返货分别拿不同的属性）
 *	getDeliveryFee（给表格【设置折扣优惠】不需要）
 *					（将获取提货方式提取成getFinalReceiveMethod方法，根据变更、转运、返货分别拿不同的属性）
 *	setDeliveryFee
 *	veryFarDeliveryFee（将获取目的站提取成getFinalCustomerPickupOrgCode方法，根据变更、转运、返货分别拿不同的属性）
 *						（给表格【设置折扣优惠】不需要）
 *	getVeryFarQueryParam
 *	setBillingWay
 *	calculateServiceFee
 *	getWeightOrVolume
 *	validataServiceFee
 *	getServiceFeeRate
 *
 * 变更单扩展方法
 * 	calculateTransferFee
 * 	calculateReturnFee
 * 	isNeedCalcZZF
 * 
 * 
 * 
 * 1. 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,
 * 且保价费率可手动调整；
 * （限保物品从限运物品基础资料中获取）；
 * 非限保物品的保价费率不可修改；
 * 2. 实际保险费小于最低保费的按最低保费收取；
 * 3. 保价费 = 保价申明价值*保价费率，不可修改；
 * 4. 保价申明价值默认为3000，可以修改；
 * 保价声明价值不封顶；
 * 5. 精准（长途）、普货（长途）、偏线，最低一票8元；
 * 普货（短途）、精准（短途）最低一票6元；
 * 空运最低一票10元；
 * 所有运输方式保价超过最低均按0.4%收取（数据读取自保价设置基础资料）；
 * 长短途数据读取计价基础资料；
 * 6. 实际保价费小于最低保费的按最低保费收取；
 * 7. 保价费率首先是配置的标准费率。
 * 当有区域保价费率时，以区域保价费率为准。
 * 当客户为合同客户时，则以合同签订为准。
 * 所有的保价费率以读取的为准，不可修改。
 * 限保物品的保价费率同样不可修改
 * 8. 行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，
 * 开单是否可以进行返回签单、货到付款、
 * 代收货款需要根据到达部门属性是否可以（返回签单、货到付款、代收货款）来决定			
 * 1. 如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，
 * 代收货款设置为0且不可编辑；
 * 2. 如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
 * 代收货款可编辑，且可以选择所有的退款类型，若CRM中若无账户信息，
 * 则退款类型只能选择审核退
 * 3. 开单时系统默认代收货款为空；
 * 4. 代收货款栏默认为空，如果没有代收货款，则要求输入0；
 * 否则，进行提示：“请确认该单没有代收货款，如无，请输入数字0”；
 * 当代收货款大于0时，输入后，对于选择的退款类型，
 * 有如下限制：
 * 3.1 三日退：在收到客户代收货款后第三天给客户打款。
 * 3.1.1 默认退款类型为三日退；
 * 3.1.2 代收10000元以下费率0.5%， 
 * 10000元以上费率0.4%；最低10元/票，最高100元/票；
 * 有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2 审核退：收到客户代收货款，出发部门审核后，给客户打款。
 * 3.2.1 代收10000元以下费率0.5%，10000元以上费率0.4%；
 * 最低10元/票，最高100元/票（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2.2 选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。
 * 3.3 即日退：在收到客户代收货款后24小时到账。
 * 3.3.1 代收手续费率1%，
 * 最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.3.2 必须先录入客户收款银行信息，提交时，银行信息不能为空；
 * 3.3.3 只支持4个银行：农行、工行、建行、招行；否则，给予提示信息；
 * 5. 限制代收货款金额不能小于10元，可以等于10元；
 * 但可以为0；该数字“10”可由基础资料配置；
 * 6. 网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，
 * 有数据时不可对代收货款进行修改，只可起草出发更改进行修改；

 * 若网上代收货款为0 ，系统可支持修改代收货款金额；
 * 7. 默认的代收费率由基础资料配置；			
 * 1. 保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额；			
 * 1. 代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，
 * 且只能选择，不能修改；当退款人姓名和帐号唯一时，直接显示；
 * （数据读取CRM客户信息资料（退款帐户信息））
 * 2. CRM客户信息资料的要先在CRM中录入客户退款帐户信息，
 * 且第一次在我司办理代收货款业务时，退款类型只能为审核退；
 * 3. 同一客户多个银行信息的显示问题：当有两个或以上账号时，
 * 弹出账号信息（包括开户银行、收款人、账号、备注），选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置；			
 * 1. 包装费默认为0，可手工修改；
 * 2. 当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
 * 且可修改，修改的金额只能大于等于默认显示金额；
 * 其中150、30、300、40为打木架单价（元/方）、打木架最低一票、打木箱单价（元/方）、打木箱最低一票，
 * 可由基础资料配置；			
 * 1. 装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
 * 2. 如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，
 * 为系统默认的公布价。开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M ；
 * 3. 当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），
 * 需先清空装卸费为零，然后再修改数据。如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
 * 4. 当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。
 * （其中Q1是装卸费M1时的费率,Z为重量/体积）；
 * 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
 * （只限制配载类型为专线的，包括月结）；
 * 6. 对于显示费率不等于显示费率乘以重量的问题，要求如下：
 * 6.1. 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
 * 且显示运费等于该显示费率*重量；
 * 6.2. 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，
 * 令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
 * 则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
 * 7. 只要含与不含装卸费两者有交叉的，均以不含为准；
 * 8. 装卸费特殊部门表：（建议：做为可配置的基础数据表）
 * 9. 2012年12月1日开业的部门不能开装卸费
 * 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
 * 11. 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
 * 12. 装卸费上限由增值服务配置基础资料实现（在产品API中体现）。13. 14. 15. 			
 * 1. 送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，不能下调。
 * 当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值，但用户可以上调送货费；			
 * 2. 通过送货费基础资料来实现：			
 * 2.1. 若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止；			
 * 2.2. 若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
 * （即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值，取值终止。）			
 * 2.3. 特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 			
 * 2.3.1 先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，
 * 再判断开单重量在已被体积筛选出来的记录中的哪个重量区间，来确定是哪一条记录。
 * 然后再根据费率计算，计算出来的值与该条的最低送货费进行比较，若小于最低送货费时，
 * 就取最低送货费，取值终止；若大于最低送货费且小于最高送货费时，就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。			
 * 2.3.2 标淮派送范围收取送货费标准：			
 * 货物重量	标准		
 * 0-300KG	55元/票		
 * 301-500KG	0.2元/KG		
 * 501KG或2.5立方米以上	100元/票，不封顶		
 * 2.3.3 当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
 * 开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于【最高送货费】”			
 * 2.3.4 仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。			
 * 2.3.5 “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也可以向下修改，最小为0）			
 * 2.3.6 除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改			
 * 2.3.7 最高送货费做基础资料配置；			
 * 3. 非标准派送范围加收操作费标准：			
 * 3.1 超远加收送货费标准：			
 * 距离（公里）	30-50	50-100	100-150
 * 加收金额（元）	50	100	150
 * 3.1.1 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）；			
 * 3.1.2 客户所在地30公里范围内如果有公司的营业网点，
 * 无论是否做派送，该区域均不能收取超远加收送货费；			
 * 3.1.3 非标准派送的费用添加无上限			
 * 3.2 特殊区域（进仓）：			
 * 3.2.1 特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）；			
 * 3.2.2 收费标准：进仓费实报实销，并加收150元操作费；			
 * 4. 区域送货费限制：			
 * 4.1 当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，
 * 但前提是：提货方式必须为送货)时，送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置）			
 * 4.2 当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦营业部、深圳华强钟表市场营业部）。
 * （该类营业部类型、送货费固定标准、成本提取标准可配置）			
 * 4.3 内部带货、空运、偏线及中转下线不受上述需求的限制。			
 * 5. 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域：			
 * 5.1 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，或体积是否超过X立方，
 * 是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。
 * （单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】			
 * 5.2 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，
 * 其他费用中的“送货上楼费”屏蔽或显示但不可选择；			
 * 5.3 若“XX区域”或其它限制类型区域再开派送部，适用以上需求；			
 * 5.4 空运、偏线及中转下线不受上述需求的限制；			
 * 5.5 内部带货受上述需求的限制；			
 * 5.6  “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置；			
 * 1. 开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
 * 费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置）
 * 2. 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置）
 * 3. 综合服务费：（费用金额由基础资料配置）
 * 3.1 综合服务费默认为2元不可修改、剔除；
 * 3.2 月结客户可以删除2元的综合服务费；
 * 3.3 淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费；
 * 4. 燃油附加费：（费用金额由基础资料配置）运输类型为“精准（长途）、普货（长途）、偏线、空运”时，
 * 燃油附加费默认为4元；运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；
 * 5. 其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改；
 * 6. 其他费用合计等于其他费用列表中各项费用数据之和；			
 * 1. 原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单；
 * 2. 空运、偏线和中转下线的“返单类型”不可选择；
 * 3. 若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用；
 * 4. 如果选择有返单类型，系统会自动生成一条签收单记录，
 * 记录信息包含：运单号、运单ID、库存状态、当前操作部门（运单开单时，是填开部门）、
 * 是否签收、是否作废、出发部门(运单开单出发部门)、签收单类型、签收状态；
 * 5. 月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
 * 非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除			
 * 1. 运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密；
 * 2. 已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。
 * 未开启预付运费保密运单提交后，若货物未有非本部门入库操作，
 * 则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
 * 则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单；
 * 3. 运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择；
 * 4. 已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，
 * 涉及预付运费有变动时，不影响预付费保密功能；
 * 5. 开启预付运费保密，预付运费不能为0，否则不能保存、提交；
 * 6. 开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行；
 * 7. 开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时，
 * 必须取消预付运费保密后才能提交；
 * 8. 预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示；
 * 9. 预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示；			
 * "1. ；2. 1）开单总费用、预付金额、到付金额，取整，四舍五入； 
 * 2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100,100.10显示100.1； 
 * 3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。 
 * 4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。"			
 * 1. 接货费只能录入数字			
 * "1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 
 * 2）限保物品的保价费率通过基础资料进行配置； 
 * 3）取消偏线、空运最高保价5000元的限制； 
 * 
 * 2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置 
 * 
 * 3、""其它费用""中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。 
 * 
 * 4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制"			
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午4:13:31
 */
public class CalculateAction extends AbstractButtonActionListener<WaybillRFCUI> {
	//日志对象 
	protected final static Logger LOG = LoggerFactory.getLogger(AbstractButtonActionListener.class.getName());
	/**
	 * 更改单服务类统一入口
	 */
	private IWaybillRfcService waybillService = WaybillRfcServiceFactory
			.getWaybillRfcService();

	/**
	 * 参数配置
	 */
//	private IConfigurationParamsService configurationParamsService =  GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);

	/**
	 * 合伙人加收方案计算service
	 */
	private IAddedFeeCalculateService addedFeeCalculateService = GuiceContextFactroy.getInjector().getInstance(AddedFeeCalculateService.class);
	
	
	/**
	 * 更改单主UI
	 */
	private WaybillRFCUI ui;
	
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(CalculateAction.class);
	
	//重量
//	private static final double FIFTY = 50;
	
	/**
	 * 
	 * 更改单页面校验是否不通过	
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:36:38
	 */
	private boolean validateUIError() {
		// 错误结果集合
		List<ValidationError> finalValiationErrors = getValiationErrors();

		// 校验通过
		if (finalValiationErrors.isEmpty()){
			return false;
		}

		// 出错信息交与校验监听处理
		IValidationListener validationListener = ui.getValidationListener();
		ValidationErrorEvent errorEvent = new ValidationErrorEvent(
				finalValiationErrors);
		validationListener.validationError(errorEvent);
		return true;

	}

	/**
	 * 
	 * 获取校验出错数据集合
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:23:40
	 */
	private List<ValidationError> getValiationErrors() {

		// 校验更改单绑定验证是否合法
		List<ValidationError> valiationErrors = ui.getBinder().validate();

		// 过滤后的错误校验信息集合
		List<ValidationError> finalValiationErrors = filterSuccessValiation(valiationErrors);

		return finalValiationErrors;
	}

	/**
	 * 
	 * 过滤出真正错误的校验信息 绑定控件返回的Message为SUCCESS时认为是正确的校验
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:26:07
	 */
	private List<ValidationError> filterSuccessValiation(
			List<ValidationError> valiationErrors) {
		// 错误校验信息集合
		List<ValidationError> finalValiationErrors = new ArrayList<ValidationError>();

		if (valiationErrors == null){
			return finalValiationErrors;
		}

		for (ValidationError error : valiationErrors) {
			// SUCCESS为绑定控件消除气泡需要
			if (!error.getMessage().equals(WaybillConstants.SUCCESS)) {
				// 当出错信息的返回信息非SUCCESS时，添加进集合
				finalValiationErrors.add(error);
			}
		}
		return finalValiationErrors;
	}
	
	/**
	 * 
	 * 计算总费用
	 *
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:15:18
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// 校验不通过
		if (validateUIError()) {
			return;
		}
		
		WaybillInfoVo bean = ui.getBinderWaybill();
		//若界面没有免费接货 则设置默认值为false,防止其他功能调用计算 306486 wangshuai 2016年3月14日15:32:03
		if(bean.getFreePickupGoods()==null){
			bean.setFreePickupGoods(false);
			}
		// 如果descrptor校验通过则执行下面的代码
		try {
			//如果发货客户编码不为空 ，我们将发货客户编码保存起来
			if(StringUtil.isNotBlank(bean.getDeliveryCustomerCode())){
				bean.setOldDeliveryCustomerCode(bean.getDeliveryCustomerCode());
			}
			//如果在客户圈 ，将客户编码设置为主客户编码
			if( StringUtil.isNotBlank(bean.getIsCircle()) && 
					StringUtil.equals("Y", bean.getIsCircle()) 
							){
				bean.setDeliveryCustomerCode(bean.getCustomerCircleEntity().getCustCode());
				bean.setIsCircle("Y");
			}else{
				//如果导入之后直接计算总运费会调用这段代码，重新调用接口给bean赋值
				CustomerCircleNewDto customerCircleNewDto = waybillService.queryCustomerByCusCode(bean.getDeliveryCustomerCode(), bean.getBillTime(), "N");
				if(customerCircleNewDto!=null && customerCircleNewDto.getCustomerCircleEntity() != null &&
						customerCircleNewDto.getCusBargainNewEntity() != null  &&
								customerCircleNewDto.getCustomerNewEntity() !=null){
					if(StringUtil.isNotEmpty(customerCircleNewDto.getCustomerCircleEntity().getCustCode())){
						bean.setDeliveryCustomerCode(customerCircleNewDto.getCustomerCircleEntity().getCustCode());
					}
					bean.setIsCircle(customerCircleNewDto.getIsCustCircle() !=null ? customerCircleNewDto.getIsCustCircle() :"");
					bean.setCusBargainNewEntity(customerCircleNewDto.getCusBargainNewEntity());
					bean.setCustomerNewEntity(customerCircleNewDto.getCustomerNewEntity());
					bean.setCustomerCircleEntity(customerCircleNewDto.getCustomerCircleEntity());
				}
			}
		//	WaybillInfoVo bean = ui.getBinderWaybill();
			if(StringUtil.isEmpty(bean.getDeliveryCustomerCode())){
				bean.setInvoice(WaybillConstants.INVOICE_02);
				bean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
			}
			//伙伴开单的时候把字段置为true
			if(BZPartnersJudge.IS_PARTENER){
				bean.setPartnerBilling(true);				
			}else{
				bean.setPartnerBilling(false);
			}
			//直营开单也需要初始化
			PtpWaybillOrgVo ptpWaybillOrgVo = PtpWaybillOrgVo.init();
			bean.setPtpWaybillOrgVo(ptpWaybillOrgVo);
			//
			
			// 基本校验
			validate(bean);
			//如果在客户圈 ，将客户编码设置为主客户编码
			if( StringUtil.isNotBlank(bean.getIsCircle()) && 
					StringUtil.equals("Y", bean.getIsCircle()) 
							){
				bean.setDeliveryCustomerCode(bean.getCustomerCircleEntity().getCustCode());
				bean.setIsCircle("Y");
			}
			//清理费用相关信息
			cleanFee(bean);
			
			//定价体系优化项目POP-548
			// 查询其他费用
			queryOtherCharge(bean);
			// 计算其他费用合计
			calculateOtherCharge(bean);
			
			//整车发票标记不变
			if(bean.getIsWholeVehicle()){
				bean.setInvoice(ui.getOriginWaybill().getInvoice());
				bean.setInvoiceTab(ui.getOriginWaybill().getInvoiceTab());
			}
			//提货方式为空
			if(bean.getFinalReceiveMethod()==null){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.transportInfoPanel.pickMode.label.isNull"));
			}
			//获取运输信息变更里面的信息设置到Bean里面，因为运输信息变更里面的个别控件无法触发绑定事件
			getRecordPanelInfoSetBean(bean);
			// 判断是否内部带货:如果内部带货，不能计算优惠券
			if (!WaybillConstants.INNER_PICKUP.equals(bean.getFinalReceiveMethod().getValueCode())
					&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
							
				/**
				 * FOSS20150818）RFOSS2015052602-梯度保价
				 * 添加取消计算保价折扣的标识符
				 * @author foss-206860
				 * */
				bean.setCalDiscount(true);
				// 计算各种费用
				calculateFee(bean);
				bean.setCalDiscount(false);
				
				//合伙人 2016年1月18日 19:25:59 葛亮亮		
				if(false == bean.getIsWholeVehicle() && BZPartnersJudge.IS_PARTENER){
					bean.setIsChanged(FossConstants.YES);//设置为更改操作
					
				   //将计算好的值放在这个类中用户和用户在页面上修改值进行比较
    			   //安装费按钮
				   ui.incrementPanel.getBtnInstall().setEnabled(true);
				   //新增按钮
				   ui.incrementPanel.getBtnAdd().setEnabled(true);
				   //删除按钮
				   ui.incrementPanel.getBtnDelete().setEnabled(true);
    			   
    			   //代收手续费(因为i这个所有情况下都是可以调整的所以不用放在下面的判断中)
				   bean.setCollectingFee(bean.getCodFee());
    			   //如果之前是有代收款款，现在取消掉，那手续费也不清空，但是可以手动调整
				   BigDecimal codfeeLast = bean.getWaybillDto().getWaybillEntity().getCodFee();		
				   if(bean.getCodFee().compareTo(BigDecimal.ZERO) == 0
				      &&codfeeLast.compareTo(BigDecimal.ZERO) > 0){
					  bean.setCodFee(codfeeLast);		 
					  bean.setCollectingFee(codfeeLast);
					  //如果代收货款清空则按照计算代收货款手续费应该置为0,需要从总运费中减去 2016年4月15日 14:21:21 葛亮亮
					  bean.setIsCodZero(true);
				   }
				   
				   //代收手续费、包装费、公布价无论是否在开单处库存都可以调整
				   //代收手续费
				   ui.incrementPanel.getTxtCollectingFee().setEditable(true);
				   ui.incrementPanel.getTxtCollectingFee().setEnabled(true);
				   //2016年1月19日 17:34:06 葛亮亮 当计算的包装费大于0时包装费可编辑
				   if(bean.getPackageFee() != null && bean.getPackageFee().compareTo(BigDecimal.ZERO) > 0){
					  ui.incrementPanel.getTxtPackCharge().setEditable(true);
					  ui.incrementPanel.getTxtPackCharge().setEnabled(true);
				   }else{
					  ui.incrementPanel.getTxtPackCharge().setEditable(false);
					  ui.incrementPanel.getTxtPackCharge().setEnabled(false);
				   }
				   
				   //如果货物当前在开单部门库存一下控件可以修改 葛亮亮
				   if(WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode()))
				   {
					 
					  //保价费填充UI
					  bean.setSupportFee(bean.getInsuranceFee());
					   
					  //保价费
					  ui.incrementPanel.getTxtSupportFee().setEditable(true);
					  ui.incrementPanel.getTxtSupportFee().setEnabled(true);
					  
					  //送货费 滕超 送货费不能修改只能按照计算带出来
					  ui.incrementPanel.getTxtDeliveryCharge().setEditable(false);
					  ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);					  
					  // ================优化内容:合伙人出发运单发生更改时，可以更改接货费的金额/时间:2016年11月7日下午4:21:52/LianHe/start================
					  //取消这段代码
					  /*//2016年1月19日 17:34:32 葛亮亮 当接货费大于0时接货费可修改  
					  if(bean.getPickupFee() != null && bean.getPickupFee().compareTo(BigDecimal.ZERO) > 0){
						  ui.incrementPanel.getTxtPickUpCharge().setEditable(true);
						  ui.incrementPanel.getTxtPickUpCharge().setEnabled(true);
					  }else{
						  ui.incrementPanel.getTxtPickUpCharge().setEditable(false);
						  ui.incrementPanel.getTxtPickUpCharge().setEnabled(false);
					  }	*/
					  // ================优化内容:合伙人出发运单发生更改时，可以更改接货费的金额/时间:2016年11月7日下午4:23:43/LianHe/end================
				   }else{
					  //如果不在开单部门仓库,代收货款、包装费、代收手续费和公布价运费可以手动调整，其余的价格要维持原来的费用
					  //因为送货费原本就不可以修改所以只需要处理保价费和接货费，将这两个的值设置成导入时的费用并重新计算总费用
					  //保价费
					  BigDecimal insuranceFeeLast = bean.getWaybillDto().getWaybillEntity().getInsuranceFee();
					  //接货费
					  BigDecimal pickupFeeLast = bean.getWaybillDto().getWaybillEntity().getPickupFee();
					  //填充保价费
					  bean.setSupportFee(insuranceFeeLast);
					  bean.setInsuranceFee(insuranceFeeLast);
					  //填充接货费
					  bean.setPickupFee(pickupFeeLast);
					  bean.setPickUpFeeCanvas(pickupFeeLast+"");
					  //重新计算总运费
					  CalculateFeeTotalUtils.resetCalculateFee(bean);
					  //如果保价费和接货费被设置为开单时页面提交值，则需要从合伙人折前费用表中取出开单时存入的日  二〇一六年四月十五日 14:17:47 葛亮亮
					  bean.setIsBeginFee(true);
				   }
				  //从折前表中取出保价费和接货费
				  PartnersWaybillEntity partnersWaybillEntity = waybillService.queryPartnersWaybillEntityById(bean.getId());
				  
				  if(null == partnersWaybillEntity){
					  throw new BusinessException("未能获取合伙人折前费用信息");
				  }else{
					//折前公布运价费
					bean.setZqTransportFee(null != partnersWaybillEntity.getTransportFee() ? partnersWaybillEntity.getTransportFee().divide(new BigDecimal(NumberConstants.NUMBER_100)) : BigDecimal.ZERO);
					//折前接货费
					bean.setZqPickUpFee(null != partnersWaybillEntity.getPickupFee() ? partnersWaybillEntity.getPickupFee().divide(new BigDecimal(NumberConstants.NUMBER_100)) : BigDecimal.ZERO);
					//折前保价费
					bean.setZqInsuranceFee(null != partnersWaybillEntity.getInsuranceFee() ? partnersWaybillEntity.getInsuranceFee().divide(new BigDecimal(NumberConstants.NUMBER_100)) : BigDecimal.ZERO);
					//折前偏线费 by：352676
					bean.setZqPartialTransportFee(null != partnersWaybillEntity.getPartialTransportFee() ? partnersWaybillEntity.getPartialTransportFee().divide(new BigDecimal(100)) : BigDecimal.ZERO);
					//原折前异常操作费--LianHe--保存原来的折前异常操作费，待用--2016年11月1日
					bean.setOldZqExceptionOperateFee(null != partnersWaybillEntity.getExceptionOpreationFee() ? partnersWaybillEntity.getExceptionOpreationFee().divide(new BigDecimal(100)) : BigDecimal.ZERO);
				  }
				}
				
				/**
				 * 设置优惠总费用
				 */
				calcaulatePromotionsFee(bean);
				//合伙人更改单在出发部门发更改时，若有公布价运费优惠券则进入		
				if (!WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode())
						&& !WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode())
						&& false == bean.getIsWholeVehicle() 
						&& PriceEntityConstants.PRICING_CODE_FRT.equals(bean.getCouponRankType())
						&& BZPartnersJudge.IS_PARTENER 
						&& !StringUtils.isEmpty(bean.getPromotionsCode())
						&& WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())) {
					// 处理优惠券
					/**
					 * 内部发货不使用优惠券
					 * dp-foss-dongjialing
					 * 225131
					 */
					if(null == bean.getInternalDeliveryType() || StringUtil.isBlank(bean.getInternalDeliveryType().getValueCode())
							|| StringUtil.isBlank(bean.getEmployeeNo())){
						executeCoupon(bean);
					}
				}
				//重新计算运费
				CalculateFeeTotalUtils.calculateTotalFee(bean,true);
				
				//将其他费用分类归集
				collectionForOtherFee(bean);
				
				//判断：若提货方式为送货进仓时，若为月结客户则送货费可编辑
				String code = bean.getReceiveMethod().getValueCode();
				if(WaybillConstants.DELIVER_STORAGE.equals(code) && bean.getChargeMode()){
					ui.incrementPanel.getTxtDeliveryCharge().setEnabled(true);
				}
	
				//判断是否有手动修改过
				if(CommonUtils.defaultIfNull(bean.getHandDeliveryFee()).compareTo(BigDecimal.valueOf(0)) != 0){
						//有手动修改过送货费，则重新走一次手动修改送货费后所走的业务逻辑
						WaybillInfoBindingListener listener = new WaybillInfoBindingListener(ui);
						listener.deliveryGoodsFeeListener(bean);
				}
				
				/**
				 * 将优惠券冲减的费用进行归集
				 * MANA-1961 营销活动与优惠券编码关联
				 * 2014-04-10 026123
				 */
				Common.offsetCouponFee(ui,bean);
				
				//在优惠券冲减完成后将最终的优惠券金额进行保存（更改单时优惠券信息不能变更所以直接拿开单信息）  邹胜利 2016年9月24日09:44:32
				bean.setFossToPtpCouponFree(bean.getCouponFree());
				
				// 处理完优惠券清空优惠券费用，防止再次冲减
				CalculateFeeTotalUtils.cleanCouponFree(bean);
				
				//验证开月结时额度是否够用
				//CUBC项目组要求注释额度这块代码 2017/3/23
				//validatePaidMethod(bean);
			}
			
			// 处理内部带货
			Common.innerPickup(bean, ui, bean.getFinalReceiveMethod().getValueCode());			
			
			//历史运单调整费用
			historyAdjustmentFee(bean);			
			
			//伙伴开单
			if(ui.getWaybillInfoPanel().getBasicPanel().getPartnerCheckBox().isSelected()){
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEditable(true);
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge().setEnabled(true);
				ui.billingPayPanel.getBtnCalculate().setEnabled(true);
				bean.setTempTransportFee(bean.getTransportFee());
		    }
			
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
			List<OtherChargeVo> voList = model.getData();
			
			/**
			 * 判断是否运费重算则加收费也重算
			 * 20160903 add by xingjun
			 */
			String finalCustomerPickupOrgCode = bean.getFinalCustomerPickupOrgCodeForAdd() == null ? "" : bean.getFinalCustomerPickupOrgCodeForAdd().getCode();
			addedFeeCalculateService.guiWabillChange(bean,finalCustomerPickupOrgCode,bean.getWaybillDto());
		
			//保存计算前的费用
			CommoForFeeUtils.keepStandardFee(voList, bean);
			
			//合伙人改单时如果FOSS中重新计算了公布运价则通知PTP也需要重新计算 2016年4月11日 19:10:10 葛亮亮
			if(BZPartnersJudge.IS_PARTENER && FossConstants.YES.equals(bean.getIsCalTraFee())){
				/*偏线由于PTP不需要按照FOSS这边真实计算偏线公布价运费的目的站重新计算运价,只是将FOSS传过去的运价费乘以折扣，
				所以不处理最终配载部门bean.getLastLoadOrgCode()（详细的需要看运费计算方法） 2016年4月12日 11:26:20 葛亮亮*/
				//需要重新计算
				ptpWaybillOrgVo.setIsCalTraFee(FossConstants.YES);
			}else{
				//将出发和到达清空
				ptpWaybillOrgVo.setIsCalTraFee(null);
				ptpWaybillOrgVo.setStartOrgCodeCal(null);
				ptpWaybillOrgVo.setDestinationOrgCodeCal(null);
			}
			//设置界面是否可编辑 放在处理费用之后，防止异常时提交按钮没有控制好
			Common.setSaveAndSubmitTrue(ui);
			// 如果是伙伴开单着走下面逻辑
			if (BZPartnersJudge.IS_PARTENER) {
				//合作人 项目 费用字段可编辑
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEditable(true);
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEnabled(true);
				ui.billingPayPanel.getBtnCalculate().setEnabled(true);
				bean.setTempTransportFee(bean.getTransportFee());
			}
			
			//异地调货客户圈客户，设置实际发货客户编码 wangfeng 311417
			bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
		} catch (BusinessException w) {
			//异地调货客户圈客户，设置实际发货客户编码 wangfeng 311417
			//发生业务异常，需要给发货实际客户的客户编码重新赋值
			bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
			//在第二次计算总运费的时候抛错
			ui.getButtonPanel().getBtnSubmit().setEnabled(false);
			if(!"".equals(w.getMessage()))
			{
				//MsgBox.showInfo(w.getMessage());
				if("没有找到运费!".equals(w.getMessage())){
					MsgBox.showITServiceInfo(w.getMessage());
				}else{
					MsgBox.showInfo(w.getMessage());
				}
			}			
		}catch(Exception ee){
			//异地调货客户圈客户，设置实际发货客户编码 wangfeng 311417
			bean.setDeliveryCustomerCode(bean.getOldDeliveryCustomerCode());
			String message=ee.getMessage();
			if("java.lang.reflect.UndeclaredThrowableException".equals(message)){
				MsgBox.showInfo("找不到对应价格,请配置");
			}else{
				MsgBox.showInfo(message);
			}
		}

	}
	
	/**
	 * 
	 * 初始化其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午05:12:36
	 */
	private void calculateOtherCharge(WaybillPanelVo bean) {

		
		
		JXTable table = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();

		/**DP-FOSS-343617 20160805 10：00
		 * 增加逻辑，当产品类型为精准包裹时，遍历data，去掉综合信息费和燃油附加费
		 */
		WaybillInfoVo waybillInfoVo=null;
		if(bean!=null){
			 waybillInfoVo = (WaybillInfoVo)bean;
		}
		if(waybillInfoVo!=null && waybillInfoVo.getFinalProductCode()!=null && StringUtil.equals(waybillInfoVo.getFinalProductCode().getCode(),ProductEntityConstants.PRICING_PRODUCT_PCP)
				&& CollectionUtils.isNotEmpty(data)){
			ListIterator<OtherChargeVo> otherChargeVoListIterator = data.listIterator();
			//遍历集合，操作大小,利用ListIterator类
			while(otherChargeVoListIterator.hasNext()){
				OtherChargeVo temp = otherChargeVoListIterator.next();
				if(StringUtil.equals(temp.getCode(),PriceEntityConstants.PRICING_CODE_ZHXX)||StringUtil.equals(temp.getCode(),PriceEntityConstants.PRICING_CODE_RYFJ)){
//					temp.setMoney(String.valueOf(NumberConstants.ZERO));
					otherChargeVoListIterator.remove();
				}
			}
		}

		if(data != null && !data.isEmpty())
		{
			BigDecimal otherChargeSum = BigDecimal.ZERO;
			// 其他费用合计
			if(bean!=null && bean.getReceiveMethod()!= null && !WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
				for (OtherChargeVo vo : data) {
					BigDecimal money = new BigDecimal(vo.getMoney());
					otherChargeSum = otherChargeSum.add(money);
				}
			}
			//非空判断
			if(bean!=null){
				//其他费用
				bean.setOtherFee(otherChargeSum);
				//画布其他费用
				bean.setOtherFeeCanvas(otherChargeSum.toString());
			}
		}
	}

	/**
	 * 
	 * 查询其他费用
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:22:50
	 */
	private void queryOtherCharge(WaybillInfoVo bean) {
		List<ValueAddDto> list = waybillService
				.queryValueAddPriceList(getQueryOtherChargeParam(bean,false));
		
		
		if(list==null||list.isEmpty()){
			list = waybillService
					.queryValueAddPriceList(getQueryOtherChargeParam(bean,true));
		}
		
		List<OtherChargeVo> voList = getOtherChargeList(list, bean);
		if(voList != null)
		{
			if(!voList.isEmpty())
			{
				ui.incrementPanel.setChangeDetail(getAllChargeCompare(voList,bean));				
			}
		}
	}
	
	/**
	 * 
	 * 将原有其他费用与新查询出来其他费用进行比较，然后取他们共同的和不同的(如A集合里面有1和2，B集合里面有2和3，那么取123)
	 * @author WangQianJin
	 * @date 2013-07-12
	 */
	private List<OtherChargeVo> getAllChargeCompare(List<OtherChargeVo> voList,WaybillInfoVo bean){		 
		JXTable table = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();	
		String rfcType = bean.getRfcType().getValueCode();
		if(!WaybillRfcConstants.TRANSFER.equals(rfcType) && !WaybillRfcConstants.RETURN.equals(rfcType)){
			//删除原来的其他费用，除业务费用以外
			deleleOtherFeeNotInBusFee(data,bean);
		}
		List<OtherChargeVo> allList=data;
		if(data != null && !data.isEmpty()){			
			for(int i=0;i<voList.size();i++){
				OtherChargeVo queryVo = voList.get(i);
				//flag用来表示现有的其他费用中有没有queryVo这个元素，false为没有
				boolean flag=false;
				for(int j=0;j<data.size();j++){
					OtherChargeVo tableVo = data.get(j);
					if(tableVo.getCode().equals(queryVo.getCode())){
						//true表示voList有中已有此元素
						flag=true;
						break;
					}
				}
				//如果没有此元素，则添加到allList里
				if(!flag){
					allList.add(queryVo);
				}
			}
		}else{
			allList=voList;
		}
		return allList;		
	}
	
	/**
	 * 删除原来的其他费用，除业务费用以外
	 * @author WangQianJin
	 * @date 2013-7-23 下午3:45:48
	 */
	private void deleleOtherFeeNotInBusFee(List<OtherChargeVo> oldOtherList,WaybillInfoVo bean){
		if(oldOtherList != null && !oldOtherList.isEmpty()){
			//要删除的除业务费用以外的其他费用
			List<OtherChargeVo> deleteOtherList=new ArrayList<OtherChargeVo>();
			for(OtherChargeVo chargeVo : oldOtherList){		
				//标示业务费用
				boolean flag=false;
				//标示手动添加费用
				boolean flagMan=false;
				//循环业务费用
				for (BusinessCostEnum busFee : BusinessCostEnum.values()) {
					//判断其他费用是否是业务费用
					if (busFee.getCode().equals(chargeVo.getCode())) {
						flag=true;
						break;
	    			}
				}
				//循环手动添加的费用
				if(bean.getManualFeeList()!=null && bean.getManualFeeList().size()>0){
					for(OtherChargeVo manVo : bean.getManualFeeList()){
						if(manVo!=null && manVo.getCode()!=null && manVo.getCode().equals(chargeVo.getCode())){
							flagMan=true;
							break;
						}
					}		
				}				
				//不是业务费用并且不是手动添加的费用，则添加都删除列表中
				if(!flag && !flagMan){
					deleteOtherList.add(chargeVo);
				}
		    }
			//删除原来的除业务费用以外的其他费用
			if(deleteOtherList.size()>0){
				oldOtherList.removeAll(deleteOtherList);
			}		    
		}
	}
	
	
	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	private List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list, WaybillInfoVo bean) {
		
	    boolean isCz=false;
		// 总件数
		int zjs=bean.getGoodsQtyTotal();
		//总总量
		BigDecimal zzl=bean.getGoodsWeightTotal();
		//平均重量
		BigDecimal cz=zzl.divide(new BigDecimal(zjs),1,BigDecimal.ROUND_HALF_UP);
		if(cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0){
			isCz = true;
		}
		
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if(list != null)
		{
			for (ValueAddDto dto : list) {
				OtherChargeVo vo = new OtherChargeVo();
				if((isCz && PriceEntityConstants.QT_CODE_CZHCZFWF.equals(dto.getSubType())) || (dto.getCandelete() != null && !BooleanConvertYesOrNo.stringToBoolean(dto
						.getCandelete())))
				{
					// 费用编码
					vo.setCode(dto.getSubType());
					// 名称
					vo.setChargeName(dto.getSubTypeName());
					// 归集类别
					vo.setType(dto.getBelongToPriceEntityName());
					// 描述
					vo.setDescrition(dto.getPriceEntityCode());
					// 金额
					vo.setMoney(dto.getCaculateFee().toString());
					// 上限
					vo.setUpperLimit(dto.getMaxFee().toString());
					// 下限
					vo.setLowerLimit(dto.getMinFee().toString());
					// 是否可修改
					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto
							.getCanmodify()));
					// 是否可删除
					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto
							.getCandelete()));
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
		}
		return voList;
	}
	
	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPanelVo bean2,boolean isGetCurrentPrice) {
		WaybillInfoVo bean = (WaybillInfoVo)bean2;
		
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());//客户编码
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getFinalProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCaculateType(bean.getCaculateType());//计算类型
		//定价体系优化项目POP-489
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * 获取运输信息变更里面的信息设置到Bean里面，因为运输信息变更里面的个别控件无法触发绑定事件
	 */
	private void getRecordPanelInfoSetBean(WaybillInfoVo bean){
		//变更类型
		String rfcType = bean.getRfcType().getValueCode();
		//如果是内部变更，并且有转运或返货记录，选中的那条记录是最后一次转运或返货的记录
		if(WaybillRfcConstants.INSIDE_CHANGE.equals(rfcType)
	    		&&((bean.getReturnRecordList().size()> 0 || bean.getTransferRecordList().size()> 0))
	    		&& ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getSelectedRow()==
	    		ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getRowCount()-1){
			//获取面板里面的运输性质并设置
			bean.setProductCode(bean.getRecordProductCode());
			//获取面板里面的提货方式并设置
			bean.setReceiveMethod(bean.getRecordReceiveMethod());
		}
	}
	
	/**
	 * 验证付款方式
	 * @author WangQianJin
	 * @date 2013-8-10 下午6:54:09
	 */
	private void validatePaidMethod(WaybillInfoVo bean){
		// 判断是否可以开月结
		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {	
			BigDecimal oldPrePayAmount=ui.getOriginWaybill().getPrePayAmount();
			BigDecimal newPrePayAmount=bean.getPrePayAmount();
			if(oldPrePayAmount!=null && newPrePayAmount!=null){
				BigDecimal difference=newPrePayAmount.subtract(oldPrePayAmount);
				//如果更改后的预付金额减去更改前的预付金额大于0，那么将差额传给结算验证额度是否够用
				if(difference.doubleValue()>0){
					DebitDto dto = waybillService.isBeBebt(bean.getDeliveryCustomerCode(), bean.getReceiveOrgCode(), WaybillConstants.MONTH_PAYMENT, difference);
					if (!dto.isBeBebt()) {
						ui.getButtonPanel().getBtnSubmit().setEnabled(false);
						throw new WaybillValidateException(dto.getMessage());
					}
				}
			}
			
		}
	}
	
	/**
	 * 将其他费用进行分类归集
	 * @author WangQianJin
	 * @date 2013-8-3 下午2:38:46
	 */
	private void collectionForOtherFee(WaybillPanelVo bean) {
		//获取其他费用
		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//累计其他费用面板所有费用(因为当点击计算总运费以后，其他费用中已经去除各种特殊归集费用，要把特殊费用再次累加到其他费用，方便下一次归集)
		CommoForFeeUtils.otherPanelSumFee(data,bean);
		//归集费用
		CommoForFeeUtils.feeCollectionForRfc(data,bean);
		//重新计算运费
		CalculateFeeTotalUtils.resetCalculateFee(bean);
	}
	
	/**
	 * 历史运单调整费用
	 * @author WangQianJin
	 * @date 2013-8-3 下午2:38:46
	 */
	private void historyAdjustmentFee(WaybillInfoVo bean) {
		BigDecimal adjustFee = bean.getAdjustFee();
		if(adjustFee!=null){
			bean.setTransportFee(bean.getTransportFee().add(adjustFee));

			BigDecimal totalFee = bean.getTotalFee();
			if(bean.getPaidMethod()!=null){
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					
					totalFee = totalFee.add(adjustFee);
					// 总费用
					bean.setTotalFee(totalFee);
					//画布-总费用
					bean.setTotalFeeCanvas(totalFee.toString());
					// 到付金额
					
					bean.setToPayAmount(totalFee);
					// 预付金额
					bean.setPrePayAmount(BigDecimal.ZERO);
				} else {
					// 预付金额
					
					if(bean.getPrePayAmount()!=null){
						bean.setPrePayAmount(totalFee.subtract(bean.getToPayAmount()));
					}else{
						bean.setPrePayAmount(totalFee);
					}
				
					// 总金额加上代收货款
					totalFee = totalFee.add(adjustFee);
					// 总费用
					bean.setTotalFee(totalFee);
					bean.setTotalFeeCanvas(totalFee.toString());
					bean.setToPayAmount(bean.getCodAmount());
				}
			}else{
				// 预付金额
				bean.setPrePayAmount(totalFee);
				// 总金额加上代收货款
				totalFee = totalFee.add(adjustFee);
				// 总费用
				bean.setTotalFee(totalFee);
				bean.setTotalFeeCanvas(totalFee.toString());
				bean.setToPayAmount(bean.getCodAmount());
			}
		}
	}
	
	/**
	 * 计算优惠总费用
	 *
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:09:42
	 */
	private void calcaulatePromotionsFee(WaybillInfoVo bean ) {
		
		List<WaybillDiscountVo> waybillDiscountVos =bean.getWaybillDiscountVos();
		BigDecimal totalPromotionsFee = BigDecimal.ZERO;		
		//优惠券费用
		BigDecimal couponFree=BigDecimal.ZERO;
		//优惠券冲减类型
		String deductibleType = "";
		if (waybillDiscountVos != null) {
			for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
				if(waybillDiscountVo != null && StringUtils.isNotEmpty(waybillDiscountVo.getFavorableAmount())){
					totalPromotionsFee = totalPromotionsFee.add(new BigDecimal(waybillDiscountVo.getFavorableAmount()));				
					//设置优惠券费用
					if (waybillDiscountVo!=null && StringUtils.isNotEmpty(bean.getPromotionsCode()) &&  
							bean.getPromotionsCode().equals(waybillDiscountVo.getDiscountId())) {
						if(waybillDiscountVo.getFavorableAmount()!=null){
							couponFree=new BigDecimal(waybillDiscountVo.getFavorableAmount());						
							//返回冲减类型
							deductibleType = waybillDiscountVo.getFavorableItemCode();
						}					
					}
				}
			}
		}
		if(totalPromotionsFee != null){
			totalPromotionsFee = totalPromotionsFee.setScale(2, BigDecimal.ROUND_HALF_UP);					
		}
		/**
		 * 设置优惠费用
		 */
		bean.setPromotionsFee(totalPromotionsFee);
		/**
		 * 设置画布的优惠费用
		 */
		bean.setPromotionsFeeCanvas(totalPromotionsFee!=null?totalPromotionsFee.toString():"");		
		/**
		 * 设置优惠券冲减类型
		 * MANA-1961 营销活动与优惠券编码关联
		 * 2014-04-10 026123
		 */		
		bean.setCouponRankType(deductibleType);
		if(PriceEntityConstants.PRICING_CODE_FRT.equals(deductibleType)){
			//计算了公布价，才重新设置优惠券金额，否则，优惠券金额已计算
			if(FossConstants.YES.equals(bean.getIsCalTraFee())){
				bean.setCouponFree(couponFree);
			}
		}else{
			bean.setCouponFree(couponFree);
		}
			
	}

	
	
	/***
	 * 
	 * 清理所有与费用相关的信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午10:00:39
	 */
	private void cleanFee(WaybillInfoVo bean) {
		//在收货部门库存才清空
		if(WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())){
			// 清理代收货款
			cleanCod(bean);
		}		
		// 清理保价
		cleanInsurance(bean);
		// 清理木架信息
		cleanStandCharge(bean);
		// 清理木箱信息
		cleanBoxCharge(bean);
		// 清理接货费
		cleanPickupFee(bean);
		
		// 转运、返货不计算公布价
		boolean isNeedCalcTransportFee = false;
		// 更改类型
		String rfcType = bean.getRfcType().getValueCode();
		// 到达部门CODE
		isNeedCalcTransportFee = endCode(bean, isNeedCalcTransportFee, rfcType);
		
		
		/**
		 * 取消清理公布价，如果公布价有变动，后面再计算时会覆盖。
		 */
//		if(isNeedCalcTransportFee){
//			// 清理产品信息
//			cleanProductPrice(bean);
//		}
		
		//如果不需要计算公布价，则需要判断是否修改了营销活动
		if(!isNeedCalcTransportFee){
			isNeedCalcTransportFee=isModifyActiveInfo();
		}
		
		// 获取送货费设置到上次操作的送货费，因为开单时可能手工修改过送货费，需要保留，后面计算送货费时用的到
		/*if(bean.getHandDeliveryFee()==null || bean.getHandDeliveryFee().doubleValue()==0){
			bean.setHandDeliveryFee(bean.getDeliveryGoodsFee());
		}
		*/
		// 清理送货费
		cleanDeliverCharge(bean);

		List<WaybillDiscountVo> waybillDiscountVos = new ArrayList<WaybillDiscountVo>(); 
		if (bean.getWaybillDiscountVos() != null && bean.getWaybillDiscountVos().size()>0) {
			for (WaybillDiscountVo waybillDiscountVo : bean.getWaybillDiscountVos()) {				
				if(isNeedCalcTransportFee){					
					if (waybillDiscountVo.getDiscountId()!=null && waybillDiscountVo.getDiscountId().equals(bean.getPromotionsCode())) {
						waybillDiscountVos.add(waybillDiscountVo);
					}
				}else{					
					//如果公布价不重新计算，把原有公布价折扣加上
					if (PriceEntityConstants.PRICING_CODE_FRT.equals(waybillDiscountVo.getFavorableItemCode())
							|| PriceEntityConstants.PRICING_CODE_JH.equals(waybillDiscountVo.getFavorableItemCode())) {
						waybillDiscountVos.add(waybillDiscountVo);
						bean.setIsFlagAddDiscount(FossConstants.YES);
					}else{					
						//如果非运费折扣，则只添加优惠券折扣
						if (waybillDiscountVo.getDiscountId()!=null && waybillDiscountVo.getDiscountId().equals(bean.getPromotionsCode())) {
							waybillDiscountVos.add(waybillDiscountVo);
						}
					}
					
				}							
			}

		}		
		
		// 清理折扣优惠明细
		bean.setWaybillDiscountVos(waybillDiscountVos);		
				
	}

	private boolean endCode(WaybillInfoVo bean, boolean isNeedCalcTransportFee,
			String rfcType) {
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			//转运不重新计算公布价
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			//返货不重新计算公布价
			//货物状态
			DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
			String inventory = goodsStatus.getValueCode();
			//未出第一外场返货从第一外场开始
			if (WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)
					|| WaybillRfcConstants.RECEIVE_STOCK_OUT.equals(inventory)
					|| (WaybillRfcConstants.TRANSFER_STOCK.equals(inventory) && 
					bean.getLoadOrgCode().equals(bean.getStockStatus().getCurrentStockOrgCode()))) {
				isNeedCalcTransportFee = true;
			}
		} else {
			//外部更改，开单部门出库不重新计算公布价
			if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
				//货物状态
				DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
				String inventory = goodsStatus.getValueCode();
				//未出第一外场返货从第一外场开始
				if (WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)){
					isNeedCalcTransportFee = true;
				}else{
					isNeedCalcTransportFee = false;
				}
			}else{
				isNeedCalcTransportFee = true;
			}
		}
		return isNeedCalcTransportFee;
	}
	
	/**
	 * MANA-257接货费优化
	 * 
	 * 清空接货费
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-25 下午5:25:40
	 */
	private void cleanPickupFee(WaybillInfoVo bean){
		bean.setBasePickupFee(BigDecimal.ZERO);
		bean.setMaxPickupFee(BigDecimal.ZERO);
		bean.setMinPickupFee(BigDecimal.ZERO);
	}
	
	
	/**
	 * 注释掉未用到的方法
	 * 2014-03-05 026123
	 */
//	/**
//	 * 清理装卸费
//	 * @author WangQianJin
//	 * @date 2013-6-19 下午4:11:43
//	 */
//	private void cleanServiceFee(WaybillPanelVo bean) {
//		// 装卸费
//		bean.setServiceFee(BigDecimal.ZERO);
//		// 装卸费
//		bean.setServiceFeeCanvas("0");
//		// 装卸费率
//		bean.setServiceFeeRate(BigDecimal.ZERO);
//	}

	/**
	 * 
	 * 清理代收贷款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:27:39
	 * @param bean
	 */
	private void cleanCod(WaybillPanelVo bean) {
		// 代收货款费率
		bean.setCodRate(BigDecimal.ZERO);
		// 代收货款金额
		bean.setCodFee(BigDecimal.ZERO);
		// 代收货款编码
		bean.setCodCode("");
		// 代收货款ID
		bean.setCodId("");
	}

	/**
	 * 
	 * 清空保险费相关
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:26:16
	 */
	private void cleanInsurance(WaybillPanelVo bean) {
		// 保险声明值最大值
		bean.setMaxInsuranceAmount(BigDecimal.ZERO);
		// 保险费率
		//bean.setInsuranceRate(BigDecimal.ZERO);
		// 保险手续费
		//bean.setInsuranceFee(BigDecimal.ZERO);
		// 保险费ID
		bean.setInsuranceId("");
		// 保险费CODE
		bean.setInsuranceCode("");
	}

	/**
	 * 
	 * 清理木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:01:37
	 */
	private void cleanStandCharge(WaybillPanelVo bean) {
		bean.setStandChargeId("");
		bean.setStandChargeCode("");
//		bean.setStandCharge(BigDecimal.ZERO);
	}

	/**
	 * 
	 * 清理木架费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午12:01:37
	 */
	private void cleanBoxCharge(WaybillPanelVo bean) {
		bean.setBoxChargeId("");
		bean.setBoxChargeCode("");
//		bean.setBoxCharge(BigDecimal.ZERO);
	}

	/**
	 * 注释掉未用到的方法
	 * 2014-03-05 026123
	 */
//	/**
//	 * 
//	 * 清理产品价格相关
//	 * 
//	 * @author 025000-FOSS-helong
//	 * @date 2013-3-16 上午10:55:13
//	 */
//	private void cleanProductPrice(WaybillPanelVo bean) {
//		// 设置运费价格ID
//		bean.setTransportFeeId("");
//		// 设置运费价格CODE
//		bean.setTransportFeeCode("");
//		// 设置运费
//		
//		if(!bean.getIsWholeVehicle()){
//			bean.setTransportFee(BigDecimal.ZERO);
//			// 画布公布价运费
//			bean.setTransportFeeCanvas(BigDecimal.ZERO.toString());
//		}
//		// 设置费率
//		bean.setUnitPrice(BigDecimal.ZERO);
//		
//		// 计费重量
//		bean.setBillWeight(BigDecimal.ZERO);
//	}

	/**
	 * 
	 * 清理送货费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午07:41:07
	 */
	private void cleanDeliverCharge(WaybillPanelVo bean) {		
		bean.setDeliveryGoodsFeeCanvas("0");
		bean.setDeliveryGoodsFee(BigDecimal.ZERO);
		bean.setDeliverList(null);
	}

	
	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	private void calculateFee(WaybillInfoVo bean) {
		LimitedWarrantyItemsEntity entity = waybillService.isInsurGoods(bean.getGoodsName());
		if (entity != null && bean.getVirtualCode()==null && !bean.getIsWholeVehicle()) {
			bean.setVirtualCode(entity.getVirtualCode());
			bean.setLimitedAmount(entity.getLimitedAmount());//限制报价金额
			ui.incrementPanel.getTxtInsuranceValue().setEnabled(false);// 设置为不可编辑
			ui.getWaybillInfoPanel().getBasicPanel().getChbExhibitCargo().setEnabled(false);// 设置为不可编辑
			/**
			 * 如果限保物品保价金额为空时：设置为0，不可编辑
			 */
			if (entity.getLimitedAmount() != null ) {
				bean.setInsuranceAmount(entity.getLimitedAmount());
				bean.setInsuranceAmountCanvas(entity.getLimitedAmount().toString());
			} else {
				bean.setInsuranceAmount(BigDecimal.ZERO);
				bean.setInsuranceAmountCanvas(BigDecimal.ZERO.toString());
			}
			bean.setInsuranceId(entity.getId());
		}  
		
		
		// 转运、返货不计算公布价
		boolean isNeedCalcTransportFee = false;
		// 更改类型
		String rfcType = bean.getRfcType().getValueCode();
		// 到达部门CODE
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			//转运不重新计算公布价
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			//返货不重新计算公布价
			//货物状态
			DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
			String inventory = goodsStatus.getValueCode();
			//未出第一外场返货从第一外场开始
			if (WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)
					|| WaybillRfcConstants.RECEIVE_STOCK_OUT.equals(inventory)
					|| (WaybillRfcConstants.TRANSFER_STOCK.equals(inventory) && 
					bean.getLoadOrgCode().equals(bean.getStockStatus().getCurrentStockOrgCode()))) {
				isNeedCalcTransportFee = true;
			}
		} else {
			//外部更改，开单部门出库不重新计算公布价
			if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
				//合伙人客户原因要求要求全部重新计算运费（业务需求调整） 2016年4月24日 13:38:11 葛亮亮
				if(BZPartnersJudge.IS_PARTENER){
					isNeedCalcTransportFee = true;
				}else
				{
					//货物状态
					DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
					String inventory = goodsStatus.getValueCode();
					//未出第一外场返货从第一外场开始
					if (WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)){
						isNeedCalcTransportFee = true;
					}else{
						isNeedCalcTransportFee = false;
					}
				}
			}else{
				isNeedCalcTransportFee = true;
			}
		}
		
		//如果不需要计算公布价，则需要判断是否修改了营销活动
		if(!isNeedCalcTransportFee){
			isNeedCalcTransportFee=isModifyActiveInfo();
		}
		
		if(isNeedCalcTransportFee){
			 //如果是内部变更，且有返货记录，并且选择的不是第一条，价格计算至中转费中
		    if(WaybillRfcConstants.INSIDE_CHANGE.equals(bean.getRfcType().getValueCode())
		    		&&((bean.getReturnRecordList().size()> 0 || bean.getTransferRecordList().size()> 0))
		    		&& ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getSelectedRow()!=0){
		    	//如果不需要设置中转费，则清0
		    	int row = ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getSelectedRow();
		    	//第2行更新第一次中转费，第3行更新第二次中转费；依次类推
		    	isNeedCalcTransportFee = isNeedCalcZZFAfterTransportChange(bean, row);
		    	if(row>0 && !isNeedCalcTransportFee){
//		    		// 设置运费
//			    	bean.setRecordUnitPrice(BigDecimal.ZERO);
//					// 设置费率
//			    	bean.setRecordFee(BigDecimal.ZERO);
//			    	//修改中转费
//			    	changeTransportOtherCharge(bean, row);
		    	}
		    }
		}
		String caculateType = bean.getBillingType().getValueCode();
		if(isNeedCalcTransportFee){
			//最低一票
			BigDecimal minTransportFee  = BigDecimal.ZERO;
			if(!bean.getIsWholeVehicle()){
				//有转运记录或返货记录不计算公布价,重新计算最后一段中转费
				// 获取公布价运费
				ProductPriceDto dtoo = calculateTransportFee(bean);
				minTransportFee = dtoo.getMinFee();//最低一票
				bean.setMinTransportFee(minTransportFee);
				//liyongfei 新增计费类型，定价优化2015-01-23
				caculateType = dtoo.getCaculateType();
/**
				 * 根据Dmana-9885巨商汇客户的运费和运费折扣执行新的规则
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-02-05下午14:37
				 */
				if(bean.getSpecialChannelFreight()){
					bean.setTransportFee(bean.getCrmTransportFee());
				}
				
			}else
			{
				bean.setTransportFee(bean.getWholeVehicleActualfee());
				bean.setTransportFeeCanvas(bean.getWholeVehicleActualfee().toString());
				//未计算公布价
				bean.setIsCalTraFee(FossConstants.NO);
			}
		}else{
			//未计算公布价
			bean.setIsCalTraFee(FossConstants.NO);
		}
		bean.setCaculateType(caculateType);
		//获取折扣优惠
		//获取整车类型
		String codeType=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE;
		/**
		 * 如果不是整车，需要获取折扣
		 */
		if(!codeType.equals(bean.getProductCode().getCode())){
			getFavorableDiscount(bean);
		}
		// 计算增值服务费
		calculateValueAdd(bean);
		
		// 计算打木架/大木箱费用
		Common.getYokeCharge(bean);

		/**
		 * 计算总运费公共方法		
		 */
		CalculateFeeTotalUtils.calculateFee(bean,false);
		
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
			isBeBebt(bean);// 判断是否可以开月结
		}
		//设置预付到付金额编辑状态
		setPrePayArriveEditState(bean);
		
	}
	
	/**
	 * 是否修改了营销活动
	 * @author WangQianJin
	 * @date 2014-04-29
	 */
	private boolean isModifyActiveInfo(){
		boolean flag=false;
		List<WaybillRfcChangeDetailDto> rfcChangeDetailList = ui.getMessagePanel().getChangedInfoPanel().getTableData();
		for (WaybillRfcChangeDetailDto waybillRfcChangeDetailDto : rfcChangeDetailList) {
			if (waybillRfcChangeDetailDto!=null && waybillRfcChangeDetailDto.getPropertyName() != null){
				if("activeInfo".equals(waybillRfcChangeDetailDto.getPropertyName())){
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 设置是否允许修改装卸费
	 * @param bean
	 * @param transportFee
	 * @param minTransportFee
	 */
	private boolean setServiceChargeState(WaybillInfoVo bean) {

		// 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
		// （只限制配载类型为专线的，包括月结）；
		/**
		 * 9. 2012年12月1日 (以后)开业的部门不能开装卸费 10.如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		 * 11.是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
		 * 12.装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
		 */

		boolean serviceChargeEnabled = true;
		
		// 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
		serviceChargeEnabled = departPropertyServiceFee(bean);
		//更改单部门没有开装卸费的权限，因此也不能修改装卸费-update by taodongguo(354805)-2016-12-8 15:44:23
		if(!serviceChargeEnabled){
			//没有开装卸费权限，清空已开装卸费 -udpate by tdg - 354805 - 2016-12-28 17:29:08
			bean.setServiceFee(BigDecimal.ZERO);
			bean.setServiceFeeCanvas("0");
			ui.incrementPanel.getTxtServiceCharge().setEnabled(serviceChargeEnabled);
			throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.errorSerivceFee.noPermission"));
		}
		// 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
		if (serviceChargeEnabled) {
			serviceChargeEnabled = lowestServiceFee(bean);
		}

		// 2012年12月1日 (以后)开业的部门不能开装卸费

		// 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		// 月发越送 == 月结
		if (serviceChargeEnabled) {
			serviceChargeEnabled = channelServiceFee(bean);
		}
		
		if (serviceChargeEnabled) {
		// 月发月送允许修改装卸费
			if (StringUtils.isNotEmpty(bean.getPreferentialType())) {
				if (bean.getPreferentialType()
						.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
					serviceChargeEnabled = true;
				}

			}
		}
		
		//客户要求出了开单部门库存不可编辑装卸费		
		if(serviceChargeEnabled && WaybillRfcConstants.CUSTOMER_REQUIRE.equals(bean.getRfcSource())){
			//判断是否为开单部门库存
			DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();			
			String inventory = goodsStatus.getValueCode();
			//非开单库存,不可编辑装卸费
			if (!WaybillRfcConstants.RECEIVE_STOCK.equals(inventory)) {
				serviceChargeEnabled=false;
			}
		}
		/**
		 * dp-foss-dongjialing
		 * 225131
		 * 有事后折阶梯折扣合同不允许开装卸费
		 */
		if(serviceChargeEnabled) {
			serviceChargeEnabled = iSLtDiscountBackInfo(bean);
		}
		ui.incrementPanel.getTxtServiceCharge().setEnabled(serviceChargeEnabled);
		
		//没有开装卸费权限，清空已开装卸费 -udpate by tdg - 354805 - 2016-12-28 17:29:08
		//装卸费能否编辑，都不能清空装卸费，否则会导致开单有装卸费，更改单自动取消，对财务会产生影响。
		/*if (!serviceChargeEnabled) {
			bean.setServiceFee(BigDecimal.ZERO);
			bean.setServiceFeeCanvas("0");
		}*/

		return serviceChargeEnabled;
	}
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 有事后折阶梯折扣合同不允许开装卸费
	 */
	private boolean iSLtDiscountBackInfo(WaybillPanelVo bean) {
		List<CusLtDiscountItemDto> list = waybillService.queryLtDiscountBackInfo(bean.getDeliveryCustomerCode(), bean.getBillTime());
		if(null == list || list.size()==0) {
			return true;
		}
		return false;
	}
	/**
	 * 否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
	 * @param bean
	 * @return
	 */
	private boolean departPropertyServiceFee(WaybillPanelVo bean) {
	
		// 收货部门
		String orgCode = bean.getReceiveOrgCode();
		// 开单时间 -- update by taodongguo(354805) -- 2016-12-6 16:43:14
		Date billTime = bean.getBillTime() == null ? bean.getCreateTime() : bean.getBillTime();
		if(null == billTime){
			billTime = new Date();
		}
		
		// org code is not null;
		if(StringUtils.isNotEmpty(orgCode)){
			String canPayServiceFee = waybillService.queryCanPayServiceFeeByCodeAndTime(orgCode, billTime);
			if(StringUtil.isNotEmpty(canPayServiceFee) && FossConstants.YES.equals(canPayServiceFee)){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * @param bean
	 * @return
	 */
	private boolean channelServiceFee(WaybillPanelVo bean) {

		String channel = bean.getOrderChannel();
		// 阿里巴巴或者阿里巴巴诚信通
		if (WaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(channel)||WaybillConstants.CRM_ORDER_CHANNEL_ALIBABACXT.equals(channel)) {
			return false;
		}
		// 月发月结
		/*if (bean.getChargeMode() != null) {
			if (bean.getChargeMode()) {
				return false;
			}
		}*/
		return true;
	}

	/**
	 * 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
	 * 
	 * 
	 * 
	 * 1、装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
	 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
	 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
	 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
	 * 2、如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，为系统默认的公布价。
	 * 开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，开单显示运费（C）=Q*Z=C0+M ；
	 * 3、当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），需先清空装卸费为零，然后再修改数据。
	 * 如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
	 * 4、当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
	 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
	 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。（其中Q1是装卸费M1时的费率,Z为重量/体积）；
	 * 5、系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。（只限制配载类型为专线的，包括月结）；
	 * 6、对于显示费率不等于显示费率乘以重量的问题，要求如下：
	 * 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
	 * 	且显示运费等于该显示费率*重量；
	 * 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
	 * 	则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
	 * 7、只要含与不含装卸费两者有交叉的，均以不含为准；
	 * 8、装卸费特殊部门表：（建议：做为可配置的基础数据表）
	 * 
	 * 特殊部门												
	 * 华东事业部		上海虹桥营业部、上海闵行区营业部、上海嘉定区南翔营业部										
	 * 												
	 * 华北事业部		"北京丰台区新发地营业部、北京丰台区大红门营业部、北京顺义区营业部、北京中关村上地营业部、石家庄栾城营业部、河北廊坊营业部、天津塘沽区营业部、天津东丽区营业部、天津北辰区营业部、天津武清区营业部、沈阳东陵区南塔营业部、沈阳于洪区张士营业部、辽宁鞍山营业部、青岛即墨营业部、青岛李沧区营业部
	 * "										
	 * 												
	 * 												
	 * 												
	 * 深圳事业部		深圳八卦岭营业部、深圳华强桑达营业部、深圳宝安机场营业部、深圳宝安区塘尾营业部、深圳南山营业部、惠州市惠阳营业部、惠州惠城区河南岸营业部、东莞虎门镇营业部、广东汕头营业部										
	 * 												
	 * 												
	 * 												
	 * 广州事业部		广州白云区新市营业部、广州花都区花山营业部、广州花都区新华营业部、广州白云区嘉忠营业部、广州白云区三元里岗头大街营业部、中山小榄营业部、江门开平营业部、广西南宁安吉营业部、佛山张槎营业部、佛山市营业部										
	 * 												
	 * 												
	 * 												
	 * 西部大区		成都武候区双星大道营业部、重庆经开区营业部、云南昆明营业部、四川泸州营业部										
	 * 												
	 * 
	 * 特殊部门												
	 * 序号	部门	事业部										
	 * 1	上海虹桥营业部	华东事业部										
	 * 2	上海闵行区营业部	华东事业部										
	 * 3	上海嘉定区南翔营业部	华东事业部										
	 * 4	北京丰台区新发地营业部	华北事业部										
	 * 5	北京丰台区大红门营业部	华北事业部										
	 * 6	北京顺义区营业部	华北事业部										
	 * 7	北京中关村上地营业部	华北事业部										
	 * 8	石家庄栾城营业部	华北事业部										
	 * 9	河北廊坊营业部	华北事业部										
	 * 10	天津塘沽区营业部	华北事业部										
	 * 11	天津东丽区营业部	华北事业部										
	 * 12	天津北辰区营业部	华北事业部										
	 * 13	天津武清区营业部	华北事业部										
	 * 14	沈阳东陵区南塔营业部	华北事业部										
	 * 15	沈阳于洪区张士营业部	华北事业部										
	 * 16	辽宁鞍山营业部	华北事业部										
	 * 17	青岛即墨营业部	华北事业部										
	 * 18	青岛李沧区营业部	华北事业部										
	 * 19	深圳八卦岭营业部	深圳事业部										
	 * 20	深圳华强桑达营业部	深圳事业部										
	 * 21	深圳宝安机场营业部	深圳事业部										
	 * 22	深圳宝安区塘尾营业部	深圳事业部										
	 * 23	深圳南山营业部	深圳事业部										
	 * 24	惠州市惠阳营业部	深圳事业部										
	 * 25	惠州惠城区河南岸营业部	深圳事业部										
	 * 26	东莞虎门镇营业部	深圳事业部										
	 * 27	广东汕头营业部	深圳事业部										
	 * 28	广州白云区新市营业部	广州事业部										
	 * 29	广州花都区花山营业部	广州事业部										
	 * 30	广州花都区新华营业部	广州事业部										
	 * 31	广州白云区嘉忠营业部	广州事业部										
	 * 32	广州白云区三元里岗头大街营业部	广州事业部										
	 * 33	中山小榄营业部	广州事业部										
	 * 34	江门开平营业部	广州事业部										
	 * 35	广西南宁安吉营业部	广州事业部										
	 * 36	佛山张槎营业部	广州事业部										
	 * 37	佛山市营业部	广州事业部										
	 * 38	成都武候区双星大道营业部	华中事业部										
	 * 39	重庆经开区营业部	华中事业部										
	 * 40	云南昆明营业部	华中事业部										
	 * 41	四川泸州营业部	华中事业部										
	 * 
	 * 9、2012年12月1日开业的部门不能开装卸费
	 * 10、如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * 11、是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
	 * 12、装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
	 * 
	 * @param bean
	 * @param transportFee
	 * @param minTransportFee
	 * @return
	 */
	private boolean lowestServiceFee(WaybillPanelVo bean) {
		BigDecimal minTransportFee = bean.getMinTransportFee();
		BigDecimal transportFee = bean.getTransportFee();
		boolean serviceChargeEnabled = true;
		if (!bean.getIsWholeVehicle() && minTransportFee != null) {
			// 最低一票 装卸费=0
			if (transportFee.compareTo(minTransportFee) == 0) {
				// 存在月发月送客户没有折扣的情况，所以需要在运费和最低一票相等的情况下，额外判断是否月发月送客户
				if (StringUtils.isNotEmpty(bean.getPreferentialType()) &&
						bean.getPreferentialType()
							.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
					serviceChargeEnabled = true;
				}else{
					bean.setServiceFee(BigDecimal.ZERO);
					serviceChargeEnabled = false;					
				}
			}
		}

		return serviceChargeEnabled;
	}
	
	/**
	 * 
	 * 优惠劵
	 * 
	 *  1. 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,
 * 且保价费率可手动调整；
 * （限保物品从限运物品基础资料中获取）；
 * 非限保物品的保价费率不可修改；
 * 2. 实际保险费小于最低保费的按最低保费收取；
 * 3. 保价费 = 保价申明价值*保价费率，不可修改；
 * 4. 保价申明价值默认为3000，可以修改；
 * 保价声明价值不封顶；
 * 5. 精准（长途）、普货（长途）、偏线，最低一票8元；
 * 普货（短途）、精准（短途）最低一票6元；
 * 空运最低一票10元；
 * 所有运输方式保价超过最低均按0.4%收取（数据读取自保价设置基础资料）；
 * 长短途数据读取计价基础资料；
 * 6. 实际保价费小于最低保费的按最低保费收取；
 * 7. 保价费率首先是配置的标准费率。
 * 当有区域保价费率时，以区域保价费率为准。
 * 当客户为合同客户时，则以合同签订为准。
 * 所有的保价费率以读取的为准，不可修改。
 * 限保物品的保价费率同样不可修改
 * 8. 行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，
 * 开单是否可以进行返回签单、货到付款、
 * 代收货款需要根据到达部门属性是否可以（返回签单、货到付款、代收货款）来决定			
 * 1. 如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，
 * 代收货款设置为0且不可编辑；
 * 2. 如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
 * 代收货款可编辑，且可以选择所有的退款类型，若CRM中若无账户信息，
 * 则退款类型只能选择审核退
 * 3. 开单时系统默认代收货款为空；
 * 4. 代收货款栏默认为空，如果没有代收货款，则要求输入0；
 * 否则，进行提示：“请确认该单没有代收货款，如无，请输入数字0”；
 * 当代收货款大于0时，输入后，对于选择的退款类型，
 * 有如下限制：
 * 3.1 三日退：在收到客户代收货款后第三天给客户打款。
 * 3.1.1 默认退款类型为三日退；
 * 3.1.2 代收10000元以下费率0.5%， 
 * 10000元以上费率0.4%；最低10元/票，最高100元/票；
 * 有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2 审核退：收到客户代收货款，出发部门审核后，给客户打款。
 * 3.2.1 代收10000元以下费率0.5%，10000元以上费率0.4%；
 * 最低10元/票，最高100元/票（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2.2 选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。
 * 3.3 即日退：在收到客户代收货款后24小时到账。
 * 3.3.1 代收手续费率1%，
 * 最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.3.2 必须先录入客户收款银行信息，提交时，银行信息不能为空；
 * 3.3.3 只支持4个银行：农行、工行、建行、招行；否则，给予提示信息；
 * 5. 限制代收货款金额不能小于10元，可以等于10元；
 * 但可以为0；该数字“10”可由基础资料配置；
 * 6. 网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，
 * 有数据时不可对代收货款进行修改，只可起草出发更改进行修改；
 * 若网上代收货款为0 ，系统可支持修改代收货款金额；
 * 7. 默认的代收费率由基础资料配置；			
 * 1. 保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额；			
 * 1. 代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，
 * 且只能选择，不能修改；当退款人姓名和帐号唯一时，直接显示；
 * （数据读取CRM客户信息资料（退款帐户信息））
 * 2. CRM客户信息资料的要先在CRM中录入客户退款帐户信息，
 * 且第一次在我司办理代收货款业务时，退款类型只能为审核退；
 * 3. 同一客户多个银行信息的显示问题：当有两个或以上账号时，
 * 弹出账号信息（包括开户银行、收款人、账号、备注），选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置；			
 * 1. 包装费默认为0，可手工修改；
 * 2. 当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
 * 且可修改，修改的金额只能大于等于默认显示金额；
 * 其中150、30、300、40为打木架单价（元/方）、打木架最低一票、打木箱单价（元/方）、打木箱最低一票，
 * 可由基础资料配置；			
 * 1. 装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
 * 2. 如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，
 * 为系统默认的公布价。开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M ；
 * 3. 当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），
 * 需先清空装卸费为零，然后再修改数据。如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
 * 4. 当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。
 * （其中Q1是装卸费M1时的费率,Z为重量/体积）；
 * 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
 * （只限制配载类型为专线的，包括月结）；
 * 6. 对于显示费率不等于显示费率乘以重量的问题，要求如下：
 * 6.1. 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
 * 且显示运费等于该显示费率*重量；
 * 6.2. 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，
 * 令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
 * 则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
 * 7. 只要含与不含装卸费两者有交叉的，均以不含为准；
 * 8. 装卸费特殊部门表：（建议：做为可配置的基础数据表）
 * 9. 2012年12月1日开业的部门不能开装卸费
 * 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
 * 11. 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
 * 12. 装卸费上限由增值服务配置基础资料实现（在产品API中体现）。13. 14. 15. 			
 * 1. 送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，不能下调。
 * 当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值，但用户可以上调送货费；			
 * 2. 通过送货费基础资料来实现：			
 * 2.1. 若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止；			
 * 2.2. 若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
 * （即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值，取值终止。）			
 * 2.3. 特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 			
 * 2.3.1 先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，
 * 再判断开单重量在已被体积筛选出来的记录中的哪个重量区间，来确定是哪一条记录。
 * 然后再根据费率计算，计算出来的值与该条的最低送货费进行比较，若小于最低送货费时，
 * 就取最低送货费，取值终止；若大于最低送货费且小于最高送货费时，就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。			
 * 2.3.2 标淮派送范围收取送货费标准：			
 * 货物重量	标准		
 * 0-300KG	55元/票		
 * 301-500KG	0.2元/KG		
 * 501KG或2.5立方米以上	100元/票，不封顶		
 * 2.3.3 当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
 * 开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于【最高送货费】”			
 * 2.3.4 仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。			
 * 2.3.5 “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也可以向下修改，最小为0）			
 * 2.3.6 除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改			
 * 2.3.7 最高送货费做基础资料配置；			
 * 3. 非标准派送范围加收操作费标准：			
 * 3.1 超远加收送货费标准：			
 * 距离（公里）	30-50	50-100	100-150
 * 加收金额（元）	50	100	150
 * 3.1.1 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）；			
 * 3.1.2 客户所在地30公里范围内如果有公司的营业网点，
 * 无论是否做派送，该区域均不能收取超远加收送货费；			
 * 3.1.3 非标准派送的费用添加无上限			
 * 3.2 特殊区域（进仓）：			
 * 3.2.1 特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）；			
 * 3.2.2 收费标准：进仓费实报实销，并加收150元操作费；			
 * 4. 区域送货费限制：			
 * 4.1 当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，
 * 但前提是：提货方式必须为送货)时，送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置）			
 * 4.2 当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦营业部、深圳华强钟表市场营业部）。
 * （该类营业部类型、送货费固定标准、成本提取标准可配置）			
 * 4.3 内部带货、空运、偏线及中转下线不受上述需求的限制。			
 * 5. 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域：			
 * 5.1 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，或体积是否超过X立方，
 * 是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。
 * （单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】			
 * 5.2 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，
 * 其他费用中的“送货上楼费”屏蔽或显示但不可选择；			
 * 5.3 若“XX区域”或其它限制类型区域再开派送部，适用以上需求；			
 * 5.4 空运、偏线及中转下线不受上述需求的限制；			
 * 5.5 内部带货受上述需求的限制；			
 * 5.6  “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置；			
 * 1. 开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
 * 费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置）
 * 2. 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置）
 * 3. 综合服务费：（费用金额由基础资料配置）
 * 3.1 综合服务费默认为2元不可修改、剔除；
 * 3.2 月结客户可以删除2元的综合服务费；
 * 3.3 淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费；
 * 4. 燃油附加费：（费用金额由基础资料配置）运输类型为“精准（长途）、普货（长途）、偏线、空运”时，
 * 燃油附加费默认为4元；运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；
 * 5. 其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改；
 * 6. 其他费用合计等于其他费用列表中各项费用数据之和；			
 * 1. 原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单；
 * 2. 空运、偏线和中转下线的“返单类型”不可选择；
 * 3. 若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用；
 * 4. 如果选择有返单类型，系统会自动生成一条签收单记录，
 * 记录信息包含：运单号、运单ID、库存状态、当前操作部门（运单开单时，是填开部门）、
 * 是否签收、是否作废、出发部门(运单开单出发部门)、签收单类型、签收状态；
 * 5. 月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
 * 非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除			
 * 1. 运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密；
 * 2. 已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。
 * 未开启预付运费保密运单提交后，若货物未有非本部门入库操作，
 * 则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
 * 则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单；
 * 3. 运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择；
 * 4. 已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，
 * 涉及预付运费有变动时，不影响预付费保密功能；
 * 5. 开启预付运费保密，预付运费不能为0，否则不能保存、提交；
 * 6. 开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行；
 * 7. 开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时，
 * 必须取消预付运费保密后才能提交；
 * 8. 预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示；
 * 9. 预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示；			
 * "1. ；2. 1）开单总费用、预付金额、到付金额，取整，四舍五入； 
 * 2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100,100.10显示100.1； 
 * 3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。 
 * 4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。"			
 * 1. 接货费只能录入数字			
 * "1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 
 * 2）限保物品的保价费率通过基础资料进行配置； 
 * 3）取消偏线、空运最高保价5000元的限制； 
 * 
 * 2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置 
 * 
 * 3、""其它费用""中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。 
 * 
 * 4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制"	
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 下午04:33:42
	 * @param bean
	 */
	@SuppressWarnings("unused")
	private void executeCoupon(WaybillInfoVo bean) {
		// 优惠卷是否为空
		CouponInfoDto couponInfoDto = getCouponInfoDto(bean);

		if (couponInfoDto != null) {
			CouponInfoResultDto dto = waybillService.validateCoupon(couponInfoDto);
			//优惠券不符合使用规则，则将优惠券费用清空，在受理同意时优惠券制成有效，留在下次使用	
			if(dto != null){
				if(!dto.isCanUse()){
					String canNotUseReason = StringUtil.defaultIfNull(dto.getCanNotUseReason());	
						// 不能使用优惠券的原因
						MsgBox.showInfo(canNotUseReason);
						bean.setCouponFree(BigDecimal.ZERO);
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */		
						bean.setCouponRankType("");
					}
			  }else{
					//为防止先减少金额后增加，优惠券可继续使用，
					// 优惠金额
					if (bean.getPromotionsFee() != null) {
						/**
						 * 设置优惠券费用
						 */
						bean.setCouponFree(dto.getCouponAmount());
						/**
						 * 设置优惠券返回实体
						 */
						bean.setCouponInfoDto(couponInfoDto);
						
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						bean.setCouponRankType(dto.getDeductibleType());
					} else {
						bean.setPromotionsCode("");
					}
			}
		}
	}
	/**
	 * 
	 * 获取优惠传入参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-4 上午10:59:57
	 */
	private CouponInfoDto getCouponInfoDto(WaybillInfoVo bean) {
		// 优惠信息DTO
		CouponInfoDto couponInfo = new CouponInfoDto();
		// 运单信息
		CouponWaybillInfoDto waybillInfo = new CouponWaybillInfoDto();
		// 运单号
		waybillInfo.setWaybillNumber(bean.getWaybillNo());
		// 产品号
		waybillInfo.setProduceType(bean.getFinalProductCode().getCode());
		// 订单号
		waybillInfo.setOrderNumber(bean.getOrderNo());
		// 判断总金额是否已有
		if (bean.getTotalFee() != null && bean.getTotalFee().compareTo(BigDecimal.ZERO) == 0) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.totalFeeNullException"));
			return null;
		}

		// 总金额
		waybillInfo.setTotalAmount(bean.getTotalFee());
		// 发货人手机
		waybillInfo.setLeaveMobile(bean.getDeliveryCustomerMobilephone());
		// 发货人电话
		waybillInfo.setLeavePhone(bean.getDeliveryCustomerPhone());
		// 客户编码
		waybillInfo.setCustNumber(bean.getDeliveryCustomerCode());
		// 获取出发部门
		String receiveOrgCode = bean.getReceiveOrgCode();

		OrgAdministrativeInfoEntity receiveOrgAdministrative = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(receiveOrgCode);

		if (receiveOrgAdministrative == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullReceiveOrgAdmin"));
			return null;
		}

		// 发货部门-标杆编码
		waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());

		if (bean.getLastLoadOrgCode() == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLastLoadOrgCode"));
			return null;
		}
		// 最终配载部门-也就是最后一个自有网点
		String lastLoadOrgCode = bean.getLastLoadOrgCode();

		OrgAdministrativeInfoEntity lastLoadOrgAdministrative = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(lastLoadOrgCode);
		if (lastLoadOrgAdministrative == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLastLoadOrgAdmin"));
			return null;
		}
		if (bean.getLoadOrgCode() == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLoadOrgCode"));
			return null;
		}

		// 始发配载部门
		String firstLoadOutOrgInfoCode = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getLoadOrgCode()).getUnifiedCode();
		// 最终配载部门 标杆编码
		String lastLoadOutOrgInfoCode = null;
		if (!StringUtils.isEmpty(bean.getLastOutLoadOrgCode())) {
			// 获取最终配载部门 标杆编码
			lastLoadOutOrgInfoCode = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getLastOutLoadOrgCode()).getUnifiedCode();
		} else {

			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.nullLastOutLoadOrgCode"));
			return null;
		}

		// 到达部门-标杆编码-由于偏线最后到达部门是代理，这里是最后一个只有部门
		waybillInfo.setArrivedDept(lastLoadOrgAdministrative.getUnifiedCode());

		// 发货部门所在外场
		waybillInfo.setLeaveOutDept(firstLoadOutOrgInfoCode);
		// 到达部门所在外场
		waybillInfo.setArrivedOutDept(lastLoadOutOrgInfoCode);

		WaybillOtherCharge model = (WaybillOtherCharge) ui.incrementPanel.getTable().getModel();
		// 获取费用明细
		List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = WaybillDtoFactory.getWaybillChargeDtlEntity(model, bean);
		List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
		for (WaybillChargeDtlEntity waybillChargeDtlEntity : waybillChargeDtlEntitys) { // 计价明细
			AmountInfoDto amountInfo = new AmountInfoDto();
			// 计价条目编码-保险费
			amountInfo.setValuationCode(waybillChargeDtlEntity.getPricingEntryCode());
			// 计价金额
			amountInfo.setValuationAmonut(waybillChargeDtlEntity.getAmount());
			amountInfoList.add(amountInfo);
		}
		waybillInfo.setAmountInfoList(amountInfoList);
		waybillInfo.setCreateOrChange("chang");
		couponInfo.setWaybillInfo(waybillInfo);
		couponInfo.setCouponNumber(bean.getPromotionsCode());
		return couponInfo;
	}

	
	/**
	 * 
	 * 设置预付到付金额编辑状态
	 * @author 025000-FOSS-helong
	 * @date 2012-12-28 上午11:15:47
	 */
	private void setPrePayArriveEditState(WaybillPanelVo bean){
		// 只有到付不允许修改预付和到付金额，其他类型的付款方式均可修改预付和到付金额
		if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
			ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);
			ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
		} else {
			ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(true);
			ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
		}

	}
	

	/**
	 * 
	 * 保价费用校验
	 * 
	 *  1. 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,
 * 且保价费率可手动调整；
 * （限保物品从限运物品基础资料中获取）；
 * 非限保物品的保价费率不可修改；
 * 2. 实际保险费小于最低保费的按最低保费收取；
 * 3. 保价费 = 保价申明价值*保价费率，不可修改；
 * 4. 保价申明价值默认为3000，可以修改；
 * 保价声明价值不封顶；
 * 5. 精准（长途）、普货（长途）、偏线，最低一票8元；
 * 普货（短途）、精准（短途）最低一票6元；
 * 空运最低一票10元；
 * 所有运输方式保价超过最低均按0.4%收取（数据读取自保价设置基础资料）；
 * 长短途数据读取计价基础资料；
 * 6. 实际保价费小于最低保费的按最低保费收取；
 * 7. 保价费率首先是配置的标准费率。
 * 当有区域保价费率时，以区域保价费率为准。
 * 当客户为合同客户时，则以合同签订为准。
 * 所有的保价费率以读取的为准，不可修改。
 * 限保物品的保价费率同样不可修改
 * 8. 行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，
 * 开单是否可以进行返回签单、货到付款、
 * 代收货款需要根据到达部门属性是否可以（返回签单、货到付款、代收货款）来决定			
 * 1. 如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，
 * 代收货款设置为0且不可编辑；
 * 2. 如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
 * 代收货款可编辑，且可以选择所有的退款类型，若CRM中若无账户信息，
 * 则退款类型只能选择审核退
 * 3. 开单时系统默认代收货款为空；
 * 4. 代收货款栏默认为空，如果没有代收货款，则要求输入0；
 * 否则，进行提示：“请确认该单没有代收货款，如无，请输入数字0”；
 * 当代收货款大于0时，输入后，对于选择的退款类型，
 * 有如下限制：
 * 3.1 三日退：在收到客户代收货款后第三天给客户打款。
 * 3.1.1 默认退款类型为三日退；
 * 3.1.2 代收10000元以下费率0.5%， 
 * 10000元以上费率0.4%；最低10元/票，最高100元/票；
 * 有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2 审核退：收到客户代收货款，出发部门审核后，给客户打款。
 * 3.2.1 代收10000元以下费率0.5%，10000元以上费率0.4%；
 * 最低10元/票，最高100元/票（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2.2 选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。
 * 3.3 即日退：在收到客户代收货款后24小时到账。
 * 3.3.1 代收手续费率1%，
 * 最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.3.2 必须先录入客户收款银行信息，提交时，银行信息不能为空；
 * 3.3.3 只支持4个银行：农行、工行、建行、招行；否则，给予提示信息；
 * 5. 限制代收货款金额不能小于10元，可以等于10元；
 * 但可以为0；该数字“10”可由基础资料配置；
 * 6. 网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，
 * 有数据时不可对代收货款进行修改，只可起草出发更改进行修改；
 * 若网上代收货款为0 ，系统可支持修改代收货款金额；
 * 7. 默认的代收费率由基础资料配置；			
 * 1. 保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额；			
 * 1. 代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，
 * 且只能选择，不能修改；当退款人姓名和帐号唯一时，直接显示；
 * （数据读取CRM客户信息资料（退款帐户信息））
 * 2. CRM客户信息资料的要先在CRM中录入客户退款帐户信息，
 * 且第一次在我司办理代收货款业务时，退款类型只能为审核退；
 * 3. 同一客户多个银行信息的显示问题：当有两个或以上账号时，
 * 弹出账号信息（包括开户银行、收款人、账号、备注），选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置；			
 * 1. 包装费默认为0，可手工修改；
 * 2. 当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
 * 且可修改，修改的金额只能大于等于默认显示金额；
 * 其中150、30、300、40为打木架单价（元/方）、打木架最低一票、打木箱单价（元/方）、打木箱最低一票，
 * 可由基础资料配置；			
 * 1. 装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
 * 2. 如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，
 * 为系统默认的公布价。开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M ；
 * 3. 当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），
 * 需先清空装卸费为零，然后再修改数据。如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
 * 4. 当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。
 * （其中Q1是装卸费M1时的费率,Z为重量/体积）；
 * 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
 * （只限制配载类型为专线的，包括月结）；
 * 6. 对于显示费率不等于显示费率乘以重量的问题，要求如下：
 * 6.1. 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
 * 且显示运费等于该显示费率*重量；
 * 6.2. 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，
 * 令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
 * 则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
 * 7. 只要含与不含装卸费两者有交叉的，均以不含为准；
 * 8. 装卸费特殊部门表：（建议：做为可配置的基础数据表）
 * 9. 2012年12月1日开业的部门不能开装卸费
 * 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
 * 11. 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
 * 12. 装卸费上限由增值服务配置基础资料实现（在产品API中体现）。13. 14. 15. 			
 * 1. 送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，不能下调。
 * 当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值，但用户可以上调送货费；			
 * 2. 通过送货费基础资料来实现：			
 * 2.1. 若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止；			
 * 2.2. 若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
 * （即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值，取值终止。）			
 * 2.3. 特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 			
 * 2.3.1 先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，
 * 再判断开单重量在已被体积筛选出来的记录中的哪个重量区间，来确定是哪一条记录。
 * 然后再根据费率计算，计算出来的值与该条的最低送货费进行比较，若小于最低送货费时，
 * 就取最低送货费，取值终止；若大于最低送货费且小于最高送货费时，就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。			
 * 2.3.2 标淮派送范围收取送货费标准：			
 * 货物重量	标准		
 * 0-300KG	55元/票		
 * 301-500KG	0.2元/KG		
 * 501KG或2.5立方米以上	100元/票，不封顶		
 * 2.3.3 当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
 * 开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于【最高送货费】”			
 * 2.3.4 仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。			
 * 2.3.5 “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也可以向下修改，最小为0）			
 * 2.3.6 除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改			
 * 2.3.7 最高送货费做基础资料配置；			
 * 3. 非标准派送范围加收操作费标准：			
 * 3.1 超远加收送货费标准：			
 * 距离（公里）	30-50	50-100	100-150
 * 加收金额（元）	50	100	150
 * 3.1.1 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）；			
 * 3.1.2 客户所在地30公里范围内如果有公司的营业网点，
 * 无论是否做派送，该区域均不能收取超远加收送货费；			
 * 3.1.3 非标准派送的费用添加无上限			
 * 3.2 特殊区域（进仓）：			
 * 3.2.1 特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）；			
 * 3.2.2 收费标准：进仓费实报实销，并加收150元操作费；			
 * 4. 区域送货费限制：			
 * 4.1 当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，
 * 但前提是：提货方式必须为送货)时，送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置）			
 * 4.2 当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦营业部、深圳华强钟表市场营业部）。
 * （该类营业部类型、送货费固定标准、成本提取标准可配置）			
 * 4.3 内部带货、空运、偏线及中转下线不受上述需求的限制。			
 * 5. 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域：			
 * 5.1 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，或体积是否超过X立方，
 * 是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。
 * （单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】			
 * 5.2 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，
 * 其他费用中的“送货上楼费”屏蔽或显示但不可选择；			
 * 5.3 若“XX区域”或其它限制类型区域再开派送部，适用以上需求；			
 * 5.4 空运、偏线及中转下线不受上述需求的限制；			
 * 5.5 内部带货受上述需求的限制；			
 * 5.6  “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置；			
 * 1. 开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
 * 费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置）
 * 2. 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置）
 * 3. 综合服务费：（费用金额由基础资料配置）
 * 3.1 综合服务费默认为2元不可修改、剔除；
 * 3.2 月结客户可以删除2元的综合服务费；
 * 3.3 淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费；
 * 4. 燃油附加费：（费用金额由基础资料配置）运输类型为“精准（长途）、普货（长途）、偏线、空运”时，
 * 燃油附加费默认为4元；运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；
 * 5. 其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改；
 * 6. 其他费用合计等于其他费用列表中各项费用数据之和；			
 * 1. 原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单；
 * 2. 空运、偏线和中转下线的“返单类型”不可选择；
 * 3. 若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用；
 * 4. 如果选择有返单类型，系统会自动生成一条签收单记录，
 * 记录信息包含：运单号、运单ID、库存状态、当前操作部门（运单开单时，是填开部门）、
 * 是否签收、是否作废、出发部门(运单开单出发部门)、签收单类型、签收状态；
 * 5. 月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
 * 非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除			
 * 1. 运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密；
 * 2. 已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。
 * 未开启预付运费保密运单提交后，若货物未有非本部门入库操作，
 * 则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
 * 则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单；
 * 3. 运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择；
 * 4. 已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，
 * 涉及预付运费有变动时，不影响预付费保密功能；
 * 5. 开启预付运费保密，预付运费不能为0，否则不能保存、提交；
 * 6. 开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行；
 * 7. 开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时，
 * 必须取消预付运费保密后才能提交；
 * 8. 预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示；
 * 9. 预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示；			
 * "1. ；2. 1）开单总费用、预付金额、到付金额，取整，四舍五入； 
 * 2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100,100.10显示100.1； 
 * 3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。 
 * 4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。"			
 * 1. 接货费只能录入数字			
 * "1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 
 * 2）限保物品的保价费率通过基础资料进行配置； 
 * 3）取消偏线、空运最高保价5000元的限制； 
 * 
 * 2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置 
 * 
 * 3、""其它费用""中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。 
 * 
 * 4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制"	
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午11:09:49
	 */

	private void validate(WaybillInfoVo bean) {
		//开单金额
		if(new Long(bean.getTotalFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)).longValue()).toString().length() > NumberConstants.NUMBER_10){
		 throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillTempSaveAction.msgBox.overMaxTotalFee"));
		}
		//验证提货网点选择是否正确
		validatePickupOrgCode(bean);
		
		// 重量、体积、件数校验
		validateWeightVolume(bean);

		// 目的站校验

		validateDistination(bean);
		// 产品校验
		validateProduct(bean);

		// 包装校验
		validatePack(bean);

		// 客户校验
		validateCustomer(bean);

		// 验证保险声明价
		validateInsurance(bean);

		// 校验提货网点重量、体积上限
		validateVW(bean);
		
		//提货方式校验
		validateReceiveMethod(bean);

		// 付款方式校验
		validatePaymentMode(bean);

		// 代收货款校验
		validateCod(bean);

		// 验证空运合票方式和航班类型不能为空
		validateAir(bean);
		
		// 验证空运货物类型不能为空
		validateAirGoodsType(bean);

		// 只允许合同客户才可以选择免费送货
		Common.validateDeliverFree(bean, ui);
		
		/**
		 * 当运输时性质为门到门的时候不能选择自提
		 
		Common.setDTDSelectUpOwn(bean);
		
		  这个验证方法：主要是验证为转运的时候，进行验证，验证门到门，不能选择自提方式
		 
		Common.setDTDSelectUpTRF(bean);
		*/
		//验证重量与提货方式
		validateWeightDeliveryMode(bean);
		
		//验证自提件类型
		validateEconomyGoodsType(bean);
		
		//验证偏线中转费，8月30号之前还取原来增值服务里面的偏线中转费
		validateOuterTime(bean);		
		//验证报价费率范围
		insuranceRateListener(bean);
		validateActiveStart(bean);
		
		//计算费用时校验省市区DMANA-4292
		Common.validateCity(bean);
		
		//统一结算
		Common.validatePayMethod(bean);
		
		// ===========lianhe/增加校验：工号和联系人/2017年1月11日20:28:53/start=======
		/**
		 * @author 370613
		 * @date 2017年1月11日20:28:53
		 * 1.工号不为空时进入此校验，为空的时候必须置internalDeliveryType为null;
		 * 2.校验工号有效；
		 * 3.校验工号信息和输入的客户信息一致，并赋值‘内部发货internalDeliveryType’信息,故传入参数true；
		 */
		if (StringUtils.isNotBlank(bean.getEmployeeNo())) {
			CommonUtils.validateLinkMan(bean,true);
		}else {
			bean.setInternalDeliveryType(null);
		}
		// ===========lianhe/增加校验：工号和联系人/2017年1月11日20:28:53/end=======
		
		//地址校验
		CommonUtils.checkAddress(bean);
		
		//校验修改运输性质时，有无更改目的站或是有没出第一外场
		validateModifyProduct(bean);
		/**
		 * 当发货客户【是否统一结算】为【是】的时候，付款方式只能为【月结】或者【临欠】
		 */
		//异地调货客户圈发货客户是统一结算，付款方式只能为月结和到付 wangfeng 311417
		Common.validatePayMethod(bean);
				
	}
	
	/**
	 * 校验在更改运输性质  由 精准卡航/城际更改为精准汽运（长途/短途）
	 * 是否有更改目的站或是 货物有无出第一外场
	 * 校验产品类型-是否符合零担电子运单 -- 272311-sangwenhao
	 * @author 218459-foss-dongsiwei
	 * @param bean
	 */
	private void validateModifyProduct(WaybillInfoVo bean) {
		if(WaybillRfcConstants.CUSTOMER_REQUIRE.equals(bean.getRfcType().getValueCode())){
			return;
		}
		// 更改前  运输性质
		String oldProductCode=bean.getWaybillDto().getWaybillEntity().getProductCode();
		// 更改后  运输性质
		String newProductCode=bean.getProductCode().getCode();
		// 更改前  提货网点（目的站）（运单基础 提货网店点编码）
		String oldPickupOrgCode=bean.getWaybillDto().getWaybillEntity().getCustomerPickupOrgCode();
		// 更改后 目的站  （提货网点编号） 
		String newPickupOrgCode=bean.getCustomerPickupOrgCode().getCode();
		//[TRUCK_FLIGHT]精准卡航      //[LRF_FLIGHT]精准汽运(长途)   [FSF_FLIGHT精准城运]  [SRF_FLIGHT精准汽运(短途)]
		if(volidateIsProcess(newProductCode,oldProductCode)){
			//目的站没发生更改
			if(StringUtils.isEmpty(newPickupOrgCode) || oldPickupOrgCode.equals(newPickupOrgCode)){
				String waybillNo=bean.getWaybillDto().getWaybillEntity().getWaybillNo();
				String loadOrgCode=bean.getWaybillDto().getWaybillEntity().getLoadOrgCode();
				bean.getWaybillDto().getWaybillEntity().getBillTime();
				List<HandOverBillDetailEntity> list=waybillService.queryHandoverBillDetailByWaybillNoAndOrgCord(waybillNo ,loadOrgCode);				
				//根据  当前货物的出库记录  判断是否已经出了第一外场（是否从第一中转外场交接出库；是出第一外场库存，而非开单部门库存）
				//true 为已出库
				if(CollectionUtils.isNotEmpty(list)){					
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.validateModifyProduct"));
				}
			}else{
				/**
				 *  目的站发生更改：
				 *  
				 * （1）更改之后的运输线路与原运输线路具有同类型的运输性质，则不可更改运输性质（如原上海到重庆，开单卡航，更改为上海到广州，运输性质必须还是卡航）
				 * （2）更改之后没有相同的运输性质则可以更改不同的运输性质（原上海到重庆，开单卡航，更改为上海到拉萨，因没有卡航，可以更改为汽运）
				 * 
				 * 首先要获取运输线路  更改之前的运输线路  判断对应的运输性质； （该业务思路不成立）
				 * */
				// 不处理
				
			}
			
		}
				
	}
	/**
	 * 更改单判断运输性质是否：
	 * 1. 判断由  精准卡航 更改为 精准汽运（长途） 
	 * 2. 判断由  精准卡航 更改为 精准汽运（短途）
	 * 3. 判断由  精准城运  更改为 精准汽运（长途） 
	 * 4. 判断由  精准城运  更改为  精准汽运（短途）
	 * 5. 判断由  精准包裹  更改为 精准汽运（长途） 
	 * 6. 判断由  精准包裹  更改为  精准汽运（短途）
	 * 
	 * @author 321519-foss-zhangweisheng
	 * 
	 * @param newProductCode  更改之后的 运输性质
	 * @param oldProductCode  更改之前的 运输性质
	 * @return  isProcessFlag 判断是否符合上述业务场景  符合为true  默认为false
	 */
	private boolean volidateIsProcess(String newProductCode,String oldProductCode){
		boolean isProcessFlag =false;
		isProcessFlag= (WaybillConstants.TRUCK_FLIGHT.equals(oldProductCode) && WaybillConstants.LRF_FLIGHT.equals(newProductCode)) 			 
								||(WaybillConstants.TRUCK_FLIGHT.equals(oldProductCode) && WaybillConstants.SRF_FLIGHT.equals(newProductCode))   
								||(WaybillConstants.FSF_FLIGHT.equals(oldProductCode) && WaybillConstants.LRF_FLIGHT.equals(newProductCode))     
								||(WaybillConstants.FSF_FLIGHT.equals(oldProductCode) && WaybillConstants.SRF_FLIGHT.equals(newProductCode))
								||(WaybillConstants.EC_GOODS.equals(oldProductCode) && WaybillConstants.LRF_FLIGHT.equals(newProductCode))     
								||(WaybillConstants.EC_GOODS.equals(oldProductCode) && WaybillConstants.SRF_FLIGHT.equals(newProductCode));    
		
		return isProcessFlag;
		
	}

	/**
	 * 校验营销活动是否开启
	 * @创建时间 2014-5-15 下午12:42:49   
	 * @创建人： WangQianJin
	 */
	public void validateActiveStart(WaybillInfoVo bean){
		//判断营销活动是否为空
		if(bean.getActiveInfo()!=null 
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueName())){
			if(bean.getIsBigGoods()){
				throw new WaybillValidateException("营销活动不能开精准大票");
			}
		}
	}
		/**
	 * 验证报价费率范围
	 * @param bean
	 */
	private void insuranceRateListener(WaybillInfoVo bean) {
		//最低保价费率
		BigDecimal minFeeRate = bean.getMinFeeRate();
		//最高保价费率
		BigDecimal maxFeeRate = bean.getMaxFeeRate();
		//调整保价费率
		BigDecimal insuranceRate = bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000));
		//报价声明价值
		BigDecimal insuranceAmount =bean.getInsuranceAmount();
		//内部带货送货 不校验费用信息 --sangwenhao
		if(bean.getReceiveMethod()==null || !WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			if(minFeeRate!=null && maxFeeRate!=null){
				if(insuranceRate.compareTo(minFeeRate)<0
						||
						insuranceRate.compareTo(maxFeeRate)>0){
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);// 提交为不可编辑
				minFeeRate=minFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_1000));
				maxFeeRate=maxFeeRate.multiply(new BigDecimal(NumberConstants.NUMBER_1000));
					//如果是精准包裹，且保价不超过2000，不抛出异常
					if (!(StringUtil.equals(bean.getFinalProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)
							&& bean.getInsuranceAmount().compareTo(new BigDecimal(NumberConstants.NUMBER_2000)) <= 0))
						throw new WaybillValidateException(i18n.get("foss.gui.changing.listener.Waybill.exception.Outrange",new Object[]{insuranceAmount,minFeeRate+"‰",maxFeeRate+"‰"}));
				}
			}
		}
	}
	/**
	 * 验证偏线中转费，8月30号之前还取原来增值服务里面的偏线中转费
	 * @author WangQianJin
	 * @date 2013-08-28
	 */
	private void validateOuterTime(WaybillInfoVo bean){ 
		//从数据字典查询8-30日期
		ConfigurationParamsEntity entity = BaseDataServiceFactory.getBaseDataService().queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, PricingConstants.OUTER_PRICE_DEFAULT_TIME, FossConstants.ROOT_ORG_CODE);
		if(null!=entity){
			if(StringUtils.isNotEmpty(entity.getConfValue())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//偏线日期
				Date outPriceDate = null;
				try {
					outPriceDate = sdf.parse(entity.getConfValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//如果开单日期在偏线日期之前，则需要验证其他费用中是否有偏线中转费，如果没有，则给予提示
				if(outPriceDate!=null && bean.getBillTime().before(outPriceDate)){
					//获取其他费用
					JXTable otherTable = ui.incrementPanel.getTable();
					WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
					List<OtherChargeVo> data = model.getData();
					String productCode=null;
					if(bean.getFinalProductCode()!=null){
						productCode=bean.getFinalProductCode().getCode();
					}
					//如果运输性质为偏线，并且没有偏线中转费
					if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode) && !isOtherFeeHavePXZZF(data)){
						MsgBox.showInfo(i18n.get("foss.gui.changing.calculateaction.outerpxzzf.iserror",new Object[]{entity.getConfValue()}));						
					}
				}
			}
		}
	}
	
	/**
	 * 判断其他费用中是否有偏线中转费
	 * @author WangQianJin
	 * @date 2013-8-28 
	 */
	private boolean isOtherFeeHavePXZZF(List<OtherChargeVo> voList) {
		boolean flag=false;
		//其他费用
		if(voList!=null && voList.size()>0){			
			//判断其他费用中是否包含空运运费冲减
			for(OtherChargeVo otherVo:voList){	
				if(otherVo!=null){					
	                if(PriceEntityConstants.PRICING_CODE_PXZZF.equals(otherVo.getCode())){
	                	flag=true;
	                	break;
	                }	                
				}		
			}					
		}
		return flag;
	}
	
	/**
	 * 校验是否自提件
	 * @author WangQianJin
	 * @date 2013-08-21
	 */
	private void validateEconomyGoodsType(WaybillInfoVo bean){
		if(bean.getIsEconomyGoods()!=null && bean.getIsEconomyGoods()){
			//如果是自提件，并且自提件类型为空，则给予提示
			if(bean.getEconomyGoodsType()==null){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateaction.economygoodstype.isnull"));	
			}else{
				if(bean.getEconomyGoodsType().getValueCode()==null){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateaction.economygoodstype.isnull"));
				}
			}			
		}
	}
	
	/**
	 * 校验提货方式与合票方式等
	 * @author WangQianJin
	 * @date 2013-07-10
	 */
	private void validatePickupOrgCode(WaybillInfoVo bean){
		ValidateVo vo=new ValidateVo();
		if(bean.getFinalCustomerPickupOrgCode()!=null){
			vo.setCustomerPickupOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());	
		}
		if(bean.getFinalProductCode()!=null){
			vo.setProductCode(bean.getFinalProductCode().getCode());
		}
		if(bean.getFinalReceiveMethod()!=null){
			vo.setReceiveMethod(bean.getFinalReceiveMethod().getValueCode());
		}
		if(bean.getRfcType()!=null){
			//变更类型
			String rfcType = bean.getRfcType().getValueCode();
			if(WaybillRfcConstants.RETURN.equals(rfcType)){
				//返货没有合票方式
			}else if(WaybillRfcConstants.TRANSFER.equals(rfcType)){
				if(bean.getTfrFreightMethod()!=null){
					vo.setFreightMethod(bean.getTfrFreightMethod().getValueCode());
				}			
			}else{
				if(bean.getTfrFreightMethod()!=null){
					vo.setFreightMethod(bean.getFreightMethod().getValueCode());
				}			
			}	
		}		
		/**
		 * 如果没有更改目的站和提货方式，则不需要校验该网点是否支持该提货方式
		 */		
		if(isNeedValidate(bean,vo)){
			//验证提货网点选择是否正确
			CommonUtils.validatePickupOrgCode(vo);
		}
	}
	
	/**
	 * 判断需不需要验证提货方式与提货网点是否匹配
	 * @author WangQianJin
	 * @date 2013-7-12
	 */
	public boolean isNeedValidate(WaybillInfoVo bean,ValidateVo vo){		
		/**
		 * 如果是整车，则不需要校验提货网点与提货方式是否匹配
		 */
		if(bean.getIsWholeVehicle()){
			return false;
		}
		//自提改派送需求 条件为在到达营业部，本来是自提改成一下4个送货方式 还要提货部门不改动 满足以上条件 不进行校验
		if(bean.getWaybillDto().getWaybillEntity()!=null&&"SELF_PICKUP".equals(bean.getWaybillDto().getWaybillEntity().getReceiveMethod())
				&&bean.getStockStatus()!=null&&"DELIVERY_STOCK".equals(bean.getStockStatus().getResult())
				&&("DELIVER_INGA".equals(bean.getReceiveMethod().getValueCode())||"DELIVER_UP".equals(bean.getReceiveMethod().getValueCode())
						||"DELIVER_NOUP".equals(bean.getReceiveMethod().getValueCode())||"LARGE_DELIVER_UP".equals(bean.getReceiveMethod().getValueCode()))
				&&bean.getCustomerPickupOrgCode()!=null&&bean.getCustomerPickupOrgCode().getCode().equals(bean.getWaybillDto().getWaybillEntity().getCustomerPickupOrgCode())){
			return false;
		}
		
		boolean con1=false;
		boolean con2=false;	
		//未更改前VO
		WaybillInfoVo originVo = ui.getOriginWaybill();
		//最终的提货网点(此提货网点可能是运输或转运或返货的提货网点)
		BranchVo finalOrg = bean.getFinalCustomerPickupOrgCode();		
		/**
		 * 条件1：如果提货网点没有变化
		 */
		if(originVo!=null && originVo.getCustomerPickupOrgCode()!=null && originVo.getCustomerPickupOrgCode().getCode()!=null){
			String oldOrgCode=originVo.getCustomerPickupOrgCode().getCode();
			if(finalOrg!=null && oldOrgCode.equals(finalOrg.getCode())){
				con1=true;
				//不需要验证提货网点
				vo.setIsNeedValCus(FossConstants.NO);
			}
		}		
		/**
		 * 条件1：如果提货方式没有变化
		 */
		if(originVo!=null && originVo.getReceiveMethod()!=null && originVo.getReceiveMethod().getValueCode()!=null){
			String oldReceiveMethod=originVo.getReceiveMethod().getValueCode();
			if(bean.getFinalReceiveMethod()!=null && oldReceiveMethod.equals(bean.getFinalReceiveMethod().getValueCode())){
				con2=true;
			}
		}
		/**
		 * 如果满足条件1和2，则不需要校验
		 */
		if(con1 && con2){
			return false;
		}		
		return true;
	}
	
	/**
	 * 验证重量与提货方式
	 * @author WangQianJin
	 * @date 2013-5-27 下午4:22:07
	 */
	private void validateWeightDeliveryMode(WaybillInfoVo bean) {
		// 货物总重量
		BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
		Integer goodsNum = bean.getGoodsQtyTotal();
		Boolean isHasPackaged = waybillService.isHasPackaged(bean
				.getWaybillNo());
		String receiveMethod = bean.getWaybillDto().getWaybillEntity()
				.getReceiveMethod();
    	/**
    	 * @需求：大件上楼优化需求
    	 * @功能：替换原有判断逻辑，但是请保留此段代码
    	 * @author:218371-foss-zhaoyanjun
    	 * @date:2015-05-07上午08:58
    	 */
//    	/**
//    	 * 单件重量大于50kg
//    	 */
//    	if (goodsWeightTotal != null && goodsNum!=null && goodsNum != 0 && (Double.parseDouble(goodsWeightTotal+"")/goodsNum.intValue()) > FIFTY) {
//    		//如果是送货上楼
//    		if(bean.getFinalReceiveMethod()!=null && WaybillConstants.DELIVER_UP.equals(bean.getFinalReceiveMethod().getValueCode())){
//    			if(isHasPackaged==true && WaybillConstants.DELIVER_UP.equals(receiveMethod) && receiveMethod!=null && receiveMethod.equals(bean.getFinalReceiveMethod().getValueCode())){
//    				//MANA-1217 代打包装后平均重量大于50kg的更改单，满足A,B条件时，仍然能够选择送货上楼，成功提交更改单。
//    			}else{
//    				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.50kgNotDoorToDoor"));
//    			}
//    		}
//    	}
		/**
		 * @需求：大件上楼优化需求
		 * @功能：替换原有判断逻辑，但是请保留此段代码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-05-07上午08:58
		 */
		if (goodsWeightTotal != null && goodsNum != null && goodsNum != 0) {
			// 如果是送货上楼
			if (bean.getFinalReceiveMethod() != null
					&& WaybillConstants.DELIVER_UP.equals(bean
							.getFinalReceiveMethod().getValueCode())
					&& (Double.parseDouble(goodsWeightTotal + "") / goodsNum
							.intValue()) > Double.valueOf(NumberConstants.NUMBER_50)) {
				if (Boolean.TRUE.equals(isHasPackaged)
						&& WaybillConstants.DELIVER_UP.equals(receiveMethod)
						&& receiveMethod != null
						&& receiveMethod.equals(bean.getFinalReceiveMethod()
								.getValueCode())) {
					// MANA-1217
					// 代打包装后平均重量大于50kg的更改单，满足A,B条件时，仍然能够选择送货上楼，成功提交更改单。
				} else {
					throw new WaybillValidateException(
							i18n.get("foss.gui.creating.calculateAction.50kgNotDoorToDoor"));
				}
			}
			if (bean.getFinalReceiveMethod() != null
					&& WaybillConstants.LARGE_DELIVER_UP.equals(bean.getFinalReceiveMethod().getValueCode())){
				if (Boolean.TRUE.equals(isHasPackaged)
						&& WaybillConstants.LARGE_DELIVER_UP
								.equals(receiveMethod)
						&& receiveMethod != null
						&& receiveMethod.equals(bean.getFinalReceiveMethod()
								.getValueCode())) {
					// MANA-1217
					// 代打包装后平均重量大于50kg的更改单，满足A,B条件时，仍然能够选择送货上楼，成功提交更改单。
				} else {
					if(Double.parseDouble(goodsWeightTotal+"") < NumberConstants.NUMBER_50_0d){
						throw new WaybillValidateException("单票货物小于50公斤不可开单大件上楼");
					}
					else if((Double.parseDouble(goodsWeightTotal+"")/goodsNum.intValue()) > NumberConstants.NUMBER_130_0d){
						throw new WaybillValidateException("单件货物大于130公斤不可开单大件上楼");
					}
				}
			}
		}
	}

	/**
	 * 
	 * 验证空运货物类型
	 * @author 025000-FOSS-helong
	 * @date 2013-3-7 下午05:57:27
	 */
	private void validateAirGoodsType(WaybillPanelVo bean)
	{
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			if(bean.getAirGoodsType() == null)
			{
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_AIRGOODSTYPE));
			}
		}
	}
	
	/**
	 * 
	 * 验证空运合票方式和航班类型不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-20 下午05:47:47
	 */
	private void validateAir(WaybillPanelVo bean) {

		ProductEntityVo productVo = bean.getProductCode();
		// 只有空运才需要进行校验
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			if (bean.getFlightNumberType() == null || bean.getFlightNumberType().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FLIGHTNUMBERTYPE));
			}

			if (bean.getFreightMethod() == null || bean.getFreightMethod().getValueCode() == null) {
				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_FREIGHTMETHOD));
			}
		}

	}

	/**
	 * 
	 * 验证保险手续费
	 * 
	 * 	 * 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,且保价费率可手动调整；（限保物品从限运物品基础资料中获取）；
	 * 	非限保物品的保价费率不可修改；
	 * 实际保险费小于最低保费的按最低保费收取；
	 * 保价费 = 保价申明价值*保价费率，不可修改；
	 * 保价申明价值默认为3000，可以修改；保价声明价值不封顶；
	 * 精准（长途）、普货（长途）、偏线，最低一票8元；普货（短途）、精准（短途）最低一票6元；空运最低一票10元；
	 * 	所有运输方式保价超过最低均按0.4%收取（数据读取自保价设置基础资料）；长短途数据读取计价基础资料；
	 * 实际保价费小于最低保费的按最低保费收取；
	 * 保价费率首先是配置的标准费率。当有区域保价费率时，以区域保价费率为准。当客户为合同客户时，则以合同签订为准。
	 * 	所有的保价费率以读取的为准，不可修改。限保物品的保价费率同样不可修改
	 * 行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，开单是否可以进行返回签单、
	 * 	货到付款、代收货款需要根据到达部门属性是否可以（返回签单、货到付款、代收货款）来决定
	 * 
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-28 下午03:52:31
	 */
	private void validateInsurance(WaybillInfoVo bean) {
		Log.debug("maintaining method");
		/*//内部自提和来自于官网的订单不需要判断保价费
		if (!WaybillConstants.INNER_PICKUP.equals(bean.getFinalReceiveMethod().getValueCode())
				|| !WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getOrderChannel())) {
			boolean chargeMode = false;
			if(bean.getChargeMode()!=null){
				chargeMode = bean.getChargeMode();
			}
			//月结客户不需要验证保价费
			if(!chargeMode){
    			if (bean.getInsuranceAmount().compareTo(BigDecimal.ZERO) == 0) {
    				throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_INSURANCEAMOUNT));
    			}
			}
		}*/
		
		//zxy 20140101 MANA-409 start 新增:限制非月结客户开单0保价
		//1非月结客户无订单发货,2非月结客户通过内部渠道（400、官网、营业部下单） 保价声明价值等于0则抛出异常
		if (null == bean.getReceiveMethod()) {
			// 抛出异常信息
			throw new WaybillSubmitException(i18n.get("foss.gui.creating.listener.Waybill.exception.ReceiveMethodNotNull"));
		}
		/**
		 * 280747
		 * zhuxue
		 */
		//报价声明价值
		BigDecimal insuranceAmount =bean.getInsuranceAmount();
		// 如果内部带货则不用判断保价声明价值
		if (!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
				&& !WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
			//保价声明价值小于等于0
			if(insuranceAmount == null || insuranceAmount.compareTo(BigDecimal.ZERO) <= 0){
				//非月结客户
				if(bean.getChargeMode() == null || !bean.getChargeMode()){
					//订单号为空
					throw new WaybillSubmitException(i18n.get("foss.gui.creating.listener.Waybill.exception.noMonthEndInsuranceZero2"));
				}else{
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.exception.noMonthEndInsuranceZero3"));
				}
			}
		}
		//zxy 20140101 MANA-409 end 新增:限制非月结客户开单0保价
	}
	
	/*
	 * 
	 * 是否包含超重货操作服务费
	 */
	public Boolean isOverweightFee(){
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		Boolean isOverweightFee = false;
		for(OtherChargeVo vo : data){
			if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode()) ||
					PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(vo.getCode())){
				isOverweightFee = true;
			}
		}
		return isOverweightFee;
	}
	
	/**
	 * 
	 * 验证重量、体积、件数不能为默认值0
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午08:16:30
	 */
	private void validateWeightVolume(WaybillInfoVo bean) {
		if (bean.getGoodsQtyTotal().intValue() == 0) {
			ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSQTYTOTAL));
		}

		if (bean.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
			ui.cargoInfoPanel.getTxtWeight().requestFocus();
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSWEIGHTTOTAL));
		}

		if (bean.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
			ui.cargoInfoPanel.getTxtVolume().requestFocus();
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSVOLUMETOTAL));
		}

		/**
		 * 根据Dmana-9885，若是巨商汇或者阿里的开单，则需验证重量和体积
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-11
		 */
		Common.specialChannelFreight(bean);
		
		if (bean.getGoodsVolumeTotal() != null && !"0".equals(bean.getGoodsVolumeTotal().toString()) && bean.getGoodsWeightTotal() != null && !"0".equals(bean.getGoodsWeightTotal().toString())) {
//			Boolean falg = CommonUtils.isHeavyWeight(bean);
//			if(!falg){
//				if(isOverweightFee()){
//					bean.setBigTicket(false);
//				}else{
//					if(!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
//						BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
//						BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
//						if(goodsWeightTotal.compareTo(new BigDecimal(500)) > 0 || goodsVolumeTotal.compareTo(new BigDecimal(2.5)) > 0){
//							bean.setBigTicket(true);
//						}else{
//							bean.setBigTicket(false);
//						}
//					}else{
//						bean.setBigTicket(false);
//					}
//				}
//			}else{
//				bean.setBigTicket(false);
//			}
			/**
			 * DMANA-11054
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-08 下午15:14
			 */
			BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
			BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
			if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500)) > 0 || goodsVolumeTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_2_5d)) > 0){
				//合伙人的单子大票货置为false
				if(bean.getPartnerBilling()){
					bean.setBigTicket(false);
				}else{
					bean.setBigTicket(true);
				}
			}else{
				bean.setBigTicket(false);
			}
			
			boolean bool = waybillService.isWeightByVolume(bean.getGoodsWeightTotal().toString(), bean.getGoodsVolumeTotal().toString());
			if (!bool) {
				if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.creating.calculateAction.msgBox.confirmWeightVolume"),
						i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"), JOptionPane.YES_NO_OPTION)) {
					throw new WaybillValidateException("");
				}
			}
		}
		
		//zxy 20140626 JZDP start 新增:1.重量体积校验，必须满足重量>500kg或者体积>2.5F
		//							  2.当其他费用内包含超重货操作费,则不能开精准大票
		if(bean.getIsBigGoods()){
				/**
				 * DMANA-7725  
				 * @author YANGKANG
				 * 过滤新产品门到门和场到场  不做超重货或者超重货服务费的校验
				 */
				if(isOverweightFee()
						&&!ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(bean.getProductCode().getCode())
						&&!ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.isOverweightFee"));
				}else{
					BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
					BigDecimal goodsVolumeTotal = bean.getGoodsVolumeTotal();
					if(goodsWeightTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0 && goodsVolumeTotal.compareTo(new BigDecimal(NumberConstants.NUMBER_2_5d)) <= 0){
						throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.bigGoodsWeight"));
					}
				}
			//}
		}
		//zxy 20140626 JZDP end 新增
		
		//zxy 20140211 DEFECT-1572 start 新增:(与开单保持一致)起草更改，修改运单总体积小于代打木架体积，缺少限制
		// 打木架体积
		BigDecimal decYokeGoodsVolume = bean.getStandGoodsVolume() == null ? BigDecimal.ZERO : bean.getStandGoodsVolume();
		// 打木箱体积
		BigDecimal decBoxGoodsVolume = bean.getBoxGoodsVolume() == null ? BigDecimal.ZERO : bean.getBoxGoodsVolume();
		BigDecimal decMoodenGoodsValumn = decYokeGoodsVolume.add(decBoxGoodsVolume);
		if(decMoodenGoodsValumn.compareTo(bean.getGoodsVolumeTotal()) > 0){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.woodYokeEnterAction.exception.overYokeGoodsVolume.one"));
		}
		//zxy 20140211 DEFECT-1572 end 新增:起草更改，修改运单总体积小于代打木架体积，缺少限制
		
		//zxy 20131225 MANA-381 start 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
		//只在收货部门库存加校验
//		if(WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())){
		//在外场还未打包装前才校验
		if(!waybillService.isHasPackaged(bean.getWaybillNo())){
			int intBoxPieces = bean.getBoxGoodsNum() == null ? 0:bean.getBoxGoodsNum();
			int intYokePieces = bean.getStandGoodsNum() == null ? 0:bean.getStandGoodsNum();
			if((intBoxPieces + intYokePieces) == bean.getGoodsQtyTotal()){
				// 打木架体积
//				BigDecimal decYokeGoodsVolume = bean.getStandGoodsVolume();
				// 打木箱体积
//				BigDecimal decBoxGoodsVolume = bean.getBoxGoodsVolume();
//				BigDecimal decMoodenGoodsValumn = decYokeGoodsVolume.add(decBoxGoodsVolume);
				if(decMoodenGoodsValumn.add(new BigDecimal(NumberConstants.NUMBER_0_01d)).compareTo(bean.getGoodsVolumeTotal()) < 0 
						|| decMoodenGoodsValumn.subtract(new BigDecimal(NumberConstants.NUMBER_0_01d)).compareTo(bean.getGoodsVolumeTotal()) > 0){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.woodYokeEnterAction.exception.notEqualVolumn"));
				}
			}
		}
		//zxy 20131225 MANA-381 end 新增:当打木架+打木箱=总件数时，要满足 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
	}

	/**
	 * 
	 * 代收货款业务规则校验
	 * 
	 * 1、如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，代收货款设置为0且不可编辑；
	 * 2、如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，代收货款可编辑，
	 * 		且可以选择所有的退款类型，若CRM中若无账户信息，则退款类型只能选择审核退
	 * 3、开单时系统默认代收货款为空；
	 * 4、代收货款栏默认为空，如果没有代收货款，则要求输入0；否则，进行提示：“请确认该单没有代收货款，
	 * 		如无，请输入数字0”；当代收货款大于0时，输入后，对于选择的退款类型，有如下限制：
	 * 三日退：在收到客户代收货款后第三天给客户打款。
	 * 默认退款类型为三日退；
	 * 代收10000元以下费率0.5%， 10000元以上费率0.4%；最低10元/票，最高100元/票；有部分城市三日退费率为0.
	 * 		（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
	 * 审核退：收到客户代收货款，出发部门审核后，给客户打款。
	 * 代收10000元以下费率0.5%，10000元以上费率0.4%；最低10元/票，最高100元/票（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
	 * 选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。
	 * 即日退：在收到客户代收货款后24小时到账。
	 * 代收手续费率1%，最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
	 * 必须先录入客户收款银行信息，提交时，银行信息不能为空；
	 * 只支持4个银行：农行、工行、建行、招行；否则，给予提示信息；
	 * 5、限制代收货款金额不能小于10元，可以等于10元；但可以为0；该数字“10”可由基础资料配置；
	 * 6、网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，有数据时不可对代收货款进行修改，只可起草出发更改进行修改；
	 * 		若网上代收货款为0 ，系统可支持修改代收货款金额；
	 * 7、默认的代收费率由基础资料配置；
	 * 8、保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额； * 
	 * 9、代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，且只能选择，不能修改；
	 * 当退款人姓名和帐号唯一时，直接显示；（数据读取CRM客户信息资料（退款帐户信息））
	 * 10、CRM客户信息资料的要先在CRM中录入客户退款帐户信息，且第一次在我司办理代收货款业务时，退款类型只能为审核退；
	 * 11、同一客户多个银行信息的显示问题：当有两个或以上账号时，弹出账号信息（包括开户银行、收款人、账号、备注），
	 * 选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置；
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 上午11:35:22
	 */
	private void validateCod(WaybillPanelVo bean) {

		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal codAmount = bean.getCodAmount();
		
		// 代收货款不能小于0
		if(codAmount != null && codAmount.compareTo(zero) < 0){
			throw new WaybillValidateException("代收货款不能小于0");
		}	

		if (codAmount == null || codAmount.compareTo(zero) == 0) {

			DataDictionaryValueVo vo = bean.getRefundType();
			if (vo != null && vo.getValueCode() != null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.one") + vo.getValueName()
						+ i18n.get("foss.gui.creating.calculateAction.exception.validateCod.two"));
			}
			if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.creating.calculateAction.msgBox.confirmValidate"),
					i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"), JOptionPane.YES_NO_OPTION)) {
				ui.incrementPanel.getTxtCashOnDelivery().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.three"));
			}
		} else {
			if(!bean.getIsWholeVehicle())
			{
				if(!FossConstants.YES.equals(bean.getCanAgentCollected()))
				{
					throw new WaybillValidateException("对不起，您选择的网点不允许开代收货款！");
				}
			}
			
			Common.validateBankInfo(bean, ui, waybillService);
			//DP-FOSS zhaoyiqing 20161025 更改单配合CUBC校验银行信息，上面方法验证了即日退对公对私标志，所以下面方法可以把提示给关掉。
			Common.validateBankInfoCUBC(bean,false);
			
			if (bean.getRefundType() == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.four"));
			} else {
				if (!WaybillConstants.REFUND_TYPE_VALUE.equals(bean.getRefundType().getValueCode())) {
					if (StringUtils.isEmpty(bean.getAccountName()) || StringUtils.isEmpty(bean.getAccountCode()) || StringUtils.isEmpty(bean.getAccountBank())) {
						if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
							throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateCod.five"));
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 判断包装件数不能大于开单件数
	 * 
	 * 包装费默认为0，可手工修改；
	 * 当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
	 * 且可修改，修改的金额只能大于等于默认显示金额；
	 * 其中150、30、300、40为打木架单价（元/方）、打木架最低一票、打木箱单价（元/方）、打木箱最低一票，可由基础资料配置；
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 上午10:04:36
	 */
	private void validatePack(WaybillPanelVo bean) {
		Integer qtyTotal = bean.getGoodsQtyTotal();// 总件数
		
		//木架信息判空操作
		if(null == bean.getPaper() || null == bean.getWood() || null == bean.getFibre()
				|| null == bean.getSalver() || null == bean.getMembrane()){
			//记录异常日志信息
			LOG.error(i18n.get("foss.gui.changing.calculateAction.exception.nullPackinginfo"));
			//抛出异常信息
			throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.nullPackinginfo"));
		}
		
		// 木架信息判空操作
		if (0 == bean.getPaper().intValue() && 0 == bean.getWood().intValue() 
				&& 0 == bean.getFibre().intValue() && 0 == bean.getSalver().intValue() 
				&& 0 == bean.getMembrane().intValue() && StringUtils.isEmpty(bean.getOtherPackage())) {
			// 记录异常日志信息
			LOG.error(i18n.get("foss.gui.changing.calculateAction.validatePack.isZero"));
			// 抛出异常信息
			throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.validatePack.isZero"));
		}
		
		
		Integer paper = Integer.valueOf(bean.getPaper());// 纸
		Integer wood = Integer.valueOf(bean.getWood());// 木
		Integer fibre = Integer.valueOf(bean.getFibre());// 纤
		Integer salver = Integer.valueOf(bean.getSalver());// 托
		Integer membrane = Integer.valueOf(bean.getMembrane());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;
		
		if (packTotal > qtyTotal) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validatePack"));
			
		}
		int oldWood = bean.getOldWood();
		List<HandOverBillDetailEntity> handoverBillDetailFirst = waybillService.queryHandoverBillDetailByWaybillNoAndOrgCord(bean.getWaybillNo(),"");
		if(CollectionUtils.isNotEmpty(handoverBillDetailFirst)){
			if(oldWood >0 && wood==0){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillInfoBindingListener.msgBox.woodException"));
	}
		}
	}
	
	/**
	 * 
	 * 验证合票方式如果是单独开单，则只能选机场自提
	 * 
	 * @author WangQianJin
	 * @date 2013-07-08 下午03:21:42
	 */
	private void validateReceiveMethod(WaybillInfoVo bean) {		
		if(bean.getReceiveMethod() != null ){
		/**
		 * 判断合票方式和运输性质和提货方式是否为空
		 */
		if (bean.getFreightMethod() != null && bean.getProductCode() != null) {
			/**
			 * 判断合票方式是否为单独开单和运输性质是否为精准空运和提货方式是否为机场自提
			 */
			if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())
					&& ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())
					&& !WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
				//空运单独开单，提货方式只能是机场自提！
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateaction.exception.validatereceivemethod"));
			}
		}
		
		//非批量开单开的单 不能 开内部带货送货 -- sangwenhao
		if(WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod()
				.getValueCode()) && !ui.isBatchWaybill() ){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitConfirmAction.DeliverInnerPickerException"));
		}
		
	 }//if end
		//客户原因(更改、转运、返货) 不能更改 提货方式 为 内部带货送货
		/*if(bean.getProductCode() != null && bean.getReceiveMethod()!=null){
			if(WaybillRfcConstants.CUSTOMER_REQUIRE.equals(bean.getRfcSource())
					&& WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
				String rfcType = bean.getRfcType().getValueCode() ;
				if(WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)
						|| WaybillRfcConstants.TRANSFER.equals(rfcType)
						|| WaybillRfcConstants.RETURN.equals(rfcType)){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.waybillInfoBindingListener.msgBox.customer.pickupNotSupport"));
				}
			}
		}*/
		
	}

	/**
	 * 
	 * 验证提货方式为机场自提时，不能开到付
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午03:21:42
	 */
	private void validatePaymentMode(WaybillInfoVo bean) {
		if (bean.getPaidMethod() == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullPaidMethod"));
		}
		//非异地调货付款方式校验
		//零担电子运单付款方式 只能为 到付和月结 -- 272311-sangwenhao
		if(StringUtils.equals(bean.getWaybillType(), WaybillConstants.WAYBILL_STATUS_LTLEWAYBILL)){
			if(!(StringUtils.equals(bean.getPaidMethod().getValueCode(), WaybillConstants.MONTH_PAYMENT)
					||StringUtils.equals(bean.getPaidMethod().getValueCode(), WaybillConstants.ARRIVE_PAYMENT))){
				//零担电子面单付款方式只能选择到付和月结
				throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.LtlEwaybillPayment"));
			}
		}
		
//		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
//			// 判断客户是否月结
//			if (bean.getChargeMode() == null || !bean.getChargeMode()) {
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.notMonthPayment"));
//			}
//		}
		//提货方式为空
		if(bean.getFinalReceiveMethod()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.changing.transportInfoPanel.pickMode.label.isNull"));
		}
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getFinalReceiveMethod().getValueCode())) {
			if (bean.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.one"));
				}
			}
			
			if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateArrivePayment.two"));
			}
		}
		
		
		if(!bean.getIsWholeVehicle())
		{
			//提货网点是否可以货到付款
			if(!FossConstants.YES.equals(bean.getArriveCharge()))
			{
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.noArrivedPay"));
				}
			}
		}
		
		// 空运以及偏线无法选择网上支付
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()) || ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productVo.getCode())) {
			if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
				throw new WaybillValidateException(productVo.getName() + i18n.get("foss.gui.creating.calculateAction.exception.unableOnlinePayment"));
			}
		}
		//有到付付款方式不能为网上支付
		if(bean.getToPayAmount().compareTo(BigDecimal.ZERO)>0){
			if(WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
				//zxy 20131016 BUG-57455 start 修改：增加代收货款的判断，如果代收货款不为0，则可以设置到付，如果代收货款为0，则不可以设置到付
				if(bean.getCodAmount().compareTo(BigDecimal.ZERO)<=0)
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.noOnline_payment"));
				//zxy 20131016 BUG-57455 end 修改：增加代收货款的判断，如果代收货款不为0，则可以设置到付，如果代收货款为0，则不可以设置到付
			}
		}
	}

	
	/**
	 * 
	 * 校验手机号码与电话号码必须有一个不能为空
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-3 上午11:15:38
	 */
	private void validateCustomer(WaybillInfoVo bean) {
		
		if(ui.consignerPanel.getTxtConsignerMobile().isEnabled()){
			if (StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())) {
				String mobilePhone = bean.getDeliveryCustomerMobilephone();
				if (mobilePhone.length() > 0) {
					mobilePhone = mobilePhone.substring(0, 1);
					if (!WaybillConstants.MOBILEPHONE_FIRST.equals(mobilePhone)) {
						MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.exception.errorConsignorMobilPhoneFirst"));
					}
				}

			}

			if (StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone()) && StringUtils.isEmpty(bean.getDeliveryCustomerPhone())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorPhoneOrTel"));
			}
		}
		
		if(ui.consigneePanel.getTxtConsigneeMobile().isEnabled()){
			if (StringUtils.isNotEmpty(bean.getReceiveCustomerMobilephone())) {
				String mobilePhone = bean.getReceiveCustomerMobilephone();
				if (mobilePhone.length() > 0) {
					mobilePhone = mobilePhone.substring(0, 1);
					if (!WaybillConstants.MOBILEPHONE_FIRST.equals(mobilePhone)) {
						MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.exception.errorConsigneeMobilPhoneFirst"));
					}
				}

			}
			if (StringUtils.isEmpty(bean.getReceiveCustomerMobilephone()) && StringUtils.isEmpty(bean.getReceiveCustomerPhone())) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneePhoneOrTel"));
			}

		}
		
		if(StringUtils.isEmpty(ui.consignerPanel.getTxtConsignerArea().getText())){
			// 原件签收单返回
			if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
				/**
				 * 注释掉未用到的变量
				 * 2014-03-05 026123
				 */
				//AddressFieldDto consignerAddress = ui.consignerPanel.getTxtConsignerArea().getAddressFieldDto();
				
				//如果发货区域贺发货地址都为空，才不允许提交
//				String nerAddress=ui.consignerPanel.getTxtConsignerArea().getText();
//				if (StringUtils.isEmpty(nerAddress) && StringUtils.isEmpty(bean.getDeliveryCustomerAddress())) {						
//					ui.consignerPanel.getTxtConsignerArea().requestFocus();
//					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
//				}
				
				JAddressField jd = ui.consignerPanel.getTxtConsignerArea() ;
				AddressFieldDto consignerAddress = jd.getAddressFieldDto();
				if (StringUtils.isBlank(jd.getText())
						|| consignerAddress == null
						|| StringUtils.isBlank(consignerAddress.getProvinceId())
						|| StringUtils.isBlank(bean.getDeliveryCustomerAddress())) {
					ui.consignerPanel.getTxtConsignerArea().requestFocus();
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsignorAddress"));
				}
			}
		}

		if(ui.consigneePanel.getTxtConsigneeArea().isEnabled()){
			// 提货方式
			if(bean.getFinalReceiveMethod()==null){
				throw new WaybillValidateException(i18n.get("foss.gui.changing.transportInfoPanel.pickMode.label.isNull"));
			}
			
			JAddressField jd = ui.consigneePanel.getTxtConsigneeArea();
			AddressFieldDto consigneeAddress = jd.getAddressFieldDto();
			if (StringUtils.isBlank(jd.getText())
					|| consigneeAddress == null
					|| StringUtils.isBlank(consigneeAddress.getProvinceId())
					|| StringUtils.isBlank(consigneeAddress.getCityId())
					|| StringUtils.isBlank(consigneeAddress.getCountyId())) {
				ui.consigneePanel.getTxtConsigneeArea().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
			}else if(StringUtils.isBlank(bean.getReceiveCustomerAddress())){
				ui.consigneePanel.getTxtConsigneeAddress().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.changing.common.WaybillValidate.receiverCustomerAddress.detailAddressNotEmpty"));
			}else {
				String a="^[\\-\u4e00-\u9fa5A-Za-z0-9]+$";
				if (!bean.getReceiveCustomerAddress().matches(a)) {
					ui.consigneePanel.getTxtConsigneeAddress().requestFocus();
					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.Address"));
				}
			}
			
			/*
			String code = bean.getFinalReceiveMethod().getValueCode();
			if (WaybillConstants.DELIVER_NOUP.equals(code) 
					|| WaybillConstants.DELIVER_FREE.equals(code) 
					|| WaybillConstants.DELIVER_STORAGE.equals(code) 
					|| WaybillConstants.DELIVER_UP.equals(code)
					|| WaybillConstants.DELIVER_FREE_AIR.equals(code) 
					|| WaybillConstants.DELIVER_NOUP_AIR.equals(code) 
					|| WaybillConstants.DELIVER_UP_AIR.equals(code)
					|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
//				String consigneeAddress = ui.consigneePanel.getTxtConsigneeArea().getText();
				
				*//**
				 * 注释掉未用到的方法
				 * 2014-03-05 026123
				 *//*
				//AddressFieldDto consignerAddressDto = ui.consigneePanel.getTxtConsigneeArea().getAddressFieldDto();
				
				//如果收货区域和收货地址都为空，才不允许提交
//				if (StringUtils.isEmpty(consigneeAddress) && StringUtils.isEmpty(bean.getReceiveCustomerAddress())) {					 
//					ui.consigneePanel.getTxtConsigneeArea().requestFocus();
//					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
//				}
				
				JAddressField jd = ui.consigneePanel.getTxtConsigneeArea();
				AddressFieldDto consigneeAddress = jd.getAddressFieldDto();
				if (StringUtils.isBlank(jd.getText())
						|| consigneeAddress == null
						|| StringUtils.isBlank(consigneeAddress.getProvinceId())
						|| StringUtils.isBlank(bean.getReceiveCustomerAddress())) {
					ui.consigneePanel.getTxtConsigneeArea().requestFocus();
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
				}
			}*/
		}

	}

	/**
	 * 
	 * 校验提货网点单票以及单件重量与体积上限
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午08:10:17
	 */
	private void validateVW(WaybillInfoVo bean) {
		BigDecimal goodsWeight = bean.getGoodsWeightTotal();// 总重量
		BigDecimal goodsVolume = bean.getGoodsVolumeTotal();// 总体积
		BigDecimal goodsQty = new BigDecimal(bean.getGoodsQtyTotal());// 总件数

		BigDecimal pieceWeight = goodsWeight.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件重量
		BigDecimal pieceVolume = goodsVolume.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件体积

		//零担电子运单不校验重量上限 -- 2723111-sangwenhao
		if(!StringUtils.equals(bean.getWaybillType(), WaybillConstants.WAYBILL_STATUS_LTLEWAYBILL)){
		BranchVo selectedSaleDepartmentInfo = bean.getFinalCustomerPickupOrgCode();
		if (selectedSaleDepartmentInfo!=null && isNeedUpperLimit(bean)) {
			//获取整车类型
			String codeType=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE;
			if (selectedSaleDepartmentInfo.getSinglePieceLimitkg() != null) {
				// 单件重量上限
				Integer intSinglePieceLimitkg=selectedSaleDepartmentInfo.getSinglePieceLimitkg();
				Double doubleSinglePieceLimitkg=intSinglePieceLimitkg.doubleValue()/NumberConstants.NUMBER_100;
				BigDecimal singlePieceLimitkg = BigDecimal.valueOf(doubleSinglePieceLimitkg);					
				/**
				 * 判断单件重量是否大于0并且不是整车类型的情况下抛出异常
				 */
				if (pieceWeight.compareTo(singlePieceLimitkg) > 0 && !codeType.equals(bean.getProductCode().getCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSinglePieceLimitkg") + singlePieceLimitkg.toString()
							+ i18n.get("foss.gui.creating.waybillEditUI.common.antiBrackets"));
				}				
			}
				
			if (selectedSaleDepartmentInfo.getSinglePieceLimitvol() != null) {
				// 单件体积上限
				Integer intSinglePieceLimitvol=selectedSaleDepartmentInfo.getSinglePieceLimitvol();
				Double doubleSinglePieceLimitvol=intSinglePieceLimitvol.doubleValue()/NumberConstants.NUMBER_100;
				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(doubleSinglePieceLimitvol);	
				/**
				 * 判断单件体积是否大于0并且不是整车类型的情况下抛出异常
				 */
				if (pieceVolume.compareTo(singlePieceLimitvol) > 0 && !codeType.equals(bean.getProductCode().getCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSinglePieceLimitvol"));
				}
			}
				
			if (selectedSaleDepartmentInfo.getSingleBillLimitkg() != null) {
				// 单票重量上限
				Integer intSingleBillLimitkg=selectedSaleDepartmentInfo.getSingleBillLimitkg();
				Double doubleSingleBillLimitkg=intSingleBillLimitkg.doubleValue()/NumberConstants.NUMBER_100;
				BigDecimal singleBillLimitkg = BigDecimal.valueOf(doubleSingleBillLimitkg);
				/**
				 * 判断单票重量是否大于0并且不是整车类型的情况下抛出异常
				 */
				if (goodsWeight.compareTo(singleBillLimitkg) > 0 && !codeType.equals(bean.getProductCode().getCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSingleBillLimitkg"));
				}
			}
				
			if (selectedSaleDepartmentInfo.getSingleBillLimitvol() != null) {
				// 单票体积上限
				Integer intSingleBillLimitvol=selectedSaleDepartmentInfo.getSingleBillLimitvol();
				Double doubleSingleBillLimitvol=intSingleBillLimitvol.doubleValue()/NumberConstants.NUMBER_100;
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(doubleSingleBillLimitvol);
				/**
				 * 判断单票体积是否大于0并且不是整车类型的情况下抛出异常
				 */
				if (goodsVolume.compareTo(singleBillLimitvol) > 0 && !codeType.equals(bean.getProductCode().getCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSingleBillLimitvol"));
				}
			}
		}
		}//if end
	}
	
	/**
	 * 判断需不需要判断体积重量是否上限
	 * @author WangQianJin
	 * @date 2013-7-1 上午10:31:07
	 */
	public boolean isNeedUpperLimit(WaybillInfoVo bean){		
		/**
		 * 如果是整车，则不需要判断体积重量是否上限
		 */
		if(bean.getIsWholeVehicle()){
			return false;
		}
		boolean con1=false;
		boolean con2=false;	
		//未更改前VO
		WaybillInfoVo originVo = ui.getOriginWaybill();
		//最终的提货网点(此提货网点可能是运输或转运或返货的提货网点)
		BranchVo finalOrg = bean.getFinalCustomerPickupOrgCode();	
		
		if(finalOrg == null){
			throw new WaybillValidateException("最终的提货网点不允许为空！");
		}
		/**
		 * 条件1：如果提货网点没有变化
		 */
		if(originVo!=null && originVo.getCustomerPickupOrgCode()!=null && originVo.getCustomerPickupOrgCode().getCode()!=null){
			String oldOrgCode=originVo.getCustomerPickupOrgCode().getCode();
			if(finalOrg!=null && oldOrgCode.equals(finalOrg.getCode())){
				con1=true;
			}
		}		
		// 单件重量上限
		Integer intSinglePieceLimitkg=finalOrg.getSinglePieceLimitkg();
		// 单件体积上限
		Integer intSinglePieceLimitvol=finalOrg.getSinglePieceLimitvol();
		// 单票重量上限
		Integer intSingleBillLimitkg=finalOrg.getSingleBillLimitkg();
		// 单票体积上限
		Integer intSingleBillLimitvol=finalOrg.getSingleBillLimitvol();
		/**
		 * 条件2：判断提货网点上限是否都不为空且等于0(意思是取消到达网点的)
		 */
		if(intSinglePieceLimitkg!=null && intSinglePieceLimitvol!=null && intSingleBillLimitkg!=null && intSingleBillLimitvol!=null){
			if(intSinglePieceLimitkg.intValue()==0 && intSinglePieceLimitvol.intValue()==0
					&& intSingleBillLimitkg.intValue()==0 && intSingleBillLimitvol.intValue()==0){
				con2=true;
			}
		}
		/**
		 * 如果没有修改提货网点，而且取消到达网点的不做限制
		 */
		if(con1 && con2){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * 产品规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:31:32
	 */
	private void validateProduct(WaybillPanelVo bean) {
		//产品对象 
		ProductEntityVo product = bean.getProductCode();
		//产品对象是否为空或编码是否为空
		if (product == null || "".equals(StringUtil.defaultIfNull(product.getCode()))) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductType"));
		}
		// 精准包裹校验
		WaybillInfoVo vo  = (WaybillInfoVo)bean;
		//获取变更类型
		DataDictionaryValueVo valueVo = vo.getRfcType();
		if(valueVo!=null){			
			String rfcType = valueVo.getValueCode();
			if(WaybillRfcConstants.TRANSFER.equals(rfcType)){
				product = vo.getTfrProductCode();
			}else if(WaybillRfcConstants.RETURN.equals(rfcType)){
				product = vo.getRtnProductCode();
			}
		}
		if(!WaybillConstants.YES.equals(bean.getIsAccuratePackage())
				&&ProductEntityConstants.PRICING_PRODUCT_PCP.equals(product.getCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.isNotAccuratePackageCustomerException"));
		}
	}

	/**
	 * 
	 * 校验目的站
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:26:27
	 */
	private void validateDistination(WaybillInfoVo bean) {
		//DN201506250008内部要求更改目的站限制-218438
		//界面选择的新的提货网点
		BranchVo branchVo = bean.getFinalCustomerPickupOrgCode();
		if (branchVo == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullCustomerPickupOrg"));
		}
		//只在内部要求的时候做限制
		if(WaybillRfcConstants.INSIDE_REQUIRE.equals(bean.getRfcSource())){
			//开单提货网点
			String customerPickupOrgCode = null;
			//开单提货网点城市code
			String customerPickupOrgCityCode = null;
			//先从中转获取开单时走货路径，获取不到按导入的数据来获取走货路径
			TransportPathEntity queryTransportPath = waybillService.queryTransportPath(bean.getWaybillNo());
			if(queryTransportPath != null && StringUtil.isNotEmpty(queryTransportPath.getTransportPath())){
				String transportPath = queryTransportPath.getTransportPath();
				String[] path = transportPath.split("-");
				if(path != null){
					//提货网点
					if(StringUtil.isNotEmpty(path[path.length-1])){
						OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
						entity.setName(path[path.length-1]);
						List<OrgAdministrativeInfoEntity> querySimpleOrgAdministrativeInfoByEntity = waybillService.querySimpleOrgAdministrativeInfoByEntity(entity, 0, 1);
						if(CollectionUtils.isNotEmpty(querySimpleOrgAdministrativeInfoByEntity)){
							//开单时提货网点
							customerPickupOrgCode = querySimpleOrgAdministrativeInfoByEntity.get(0).getCode();
							if(StringUtils.isNotEmpty(customerPickupOrgCode)){
								//开单时提货网点所在城市code
								OrgAdministrativeInfoEntity receiveOrgAdministrative = 
										BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(customerPickupOrgCode);
								customerPickupOrgCityCode = receiveOrgAdministrative.getCityCode();
	}
						}
					}
				}
			}
			if(StringUtil.isEmpty(customerPickupOrgCode)){
				//导入的时候的提货部门所属的
				BranchVo branchVoOld = ui.getOriginWaybill().getCustomerPickupOrgCode();
				customerPickupOrgCode = branchVoOld.getCode();
				customerPickupOrgCityCode = branchVoOld.getCityCode();
			}
			//出发部门信息
			OrgAdministrativeInfoEntity receiveOrgAdministrative = 
					BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityByCode(bean.getReceiveOrgCode());
			//开单时提货网点和出发部门不一样
			if(customerPickupOrgCityCode != null && receiveOrgAdministrative != null && !customerPickupOrgCityCode.equals(receiveOrgAdministrative.getCityCode())){
				List<String> labeledGoodSerialNos = new ArrayList<String>();
				List<String> inOutStockSerialNos = new ArrayList<String>();
				if(StringUtils.isNotEmpty(bean.getWaybillNo())){
					//查询运单货物件数
					List<LabeledGoodEntity> labeledGoodEntityList = waybillService.queryAllSerialByWaybillNo(bean.getWaybillNo());
					if(CollectionUtils.isNotEmpty(labeledGoodEntityList)){
						for(LabeledGoodEntity labeledGoodEntity:labeledGoodEntityList){
							labeledGoodSerialNos.add(labeledGoodEntity.getSerialNo());
						}
					}
					if(StringUtil.isNotEmpty(customerPickupOrgCode)){
						//查询营业部的部门信息
						SaleDepartmentEntity dept = waybillService.querySaleDepartmentByCode(customerPickupOrgCode);
						//如果是驻地营业部取到达对应外场的件数，否则取到达目的站的件数
						if(dept !=null && dept.getStation().equals(FossConstants.ACTIVE)){
							//驻地营业部对应外场
							String transferCenter = dept.getTransferCenter();
							if(StringUtil.isNotEmpty(transferCenter)){
								List<InOutStockEntity> inOutStockEntityList=waybillService.queryInStockInfo(bean.getWaybillNo(), null, transferCenter, bean.getBillTime());
								if(CollectionUtils.isNotEmpty(inOutStockEntityList)){
									for(InOutStockEntity inOutStockEntity:inOutStockEntityList){
										inOutStockSerialNos.add(inOutStockEntity.getSerialNO());
									}
								}
							}
						}else{
							List<InOutStockEntity> inOutStockEntityList=waybillService.queryInStockInfo(bean.getWaybillNo(), null, customerPickupOrgCode, bean.getBillTime());
							if(CollectionUtils.isNotEmpty(inOutStockEntityList)){
								for(InOutStockEntity inOutStockEntity:inOutStockEntityList){
									inOutStockSerialNos.add(inOutStockEntity.getSerialNO());
								}
							}
						}
					}
					//货物到达目的站才做限制
					if(inOutStockSerialNos.containsAll(labeledGoodSerialNos)){
						//界面选择的提货网点和出发部门比较
						if(branchVo.getCityCode().equals(receiveOrgAdministrative.getCityCode())){
							throw new WaybillValidateException("目的站不能为：出发部门或出发部门所在城市的其他网点");
						}
					}
				}
			}
		}
	}


	/**
	 * 
	 * 临时欠款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:19:55
	 */
	private void isBeBebt(WaybillPanelVo bean) {
		if(StringUtil.isEmpty(bean.getDeliveryCustomerCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullDeliveryCustomerCode"));
		}
		BigDecimal money = bean.getPrePayAmount();
		DebitDto dto = waybillService.isBeBebt(bean.getDeliveryCustomerCode(), bean.getReceiveOrgCode(), WaybillConstants.TEMPORARY_DEBT, money);
		if (!dto.isBeBebt()) {
			throw new WaybillValidateException(dto.getMessage());
		}
	}

	/**
	 * 
	 * 计算公布价运费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 下午02:14:05
	 */
	private ProductPriceDto calculateTransportFee(WaybillInfoVo bean) {
		// 查询产品价格
		ProductPriceDto dto = getProductPriceDto(bean);
		if (dto == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		} else {			
			return dto;
		}

	}	

	/**
	 * 
	 * 获得产品价格详细信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-5 下午04:27:16
	 */
	private ProductPriceDto getProductPriceDto(WaybillInfoVo bean) {
		List<ProductPriceDto> productPrice = null;
		
		/**
		 * 获取查询产品价格计算的DTOp---为了配合降价返券的需求
		 * 
		 * @author Foss-206860
		 * */
		QueryBillCacilateDto queryBillCacilateDto  = new QueryBillCacilateDto();

		/**
		 * 定义增值服务对象
		 * 时间：2014-03-05
		 * 内容：MANA-257接货费优化
		 * 作者：026123
		 */
		ValueAddDto valueAdddto = null;
		//公布价读取的是否是接货价，默认为空
		Boolean isPickupPrice = null;
		
		// 查询产品价格
		if (bean.getPickupToDoor()) {
			//非免费接货 306486
			if (!bean.getFreePickupGoods()) {
				productPrice = waybillService.queryProductPriceList(Common
						.getQueryParam(bean, FossConstants.YES, Common.GBJ));
			}else{//免费接货 306486
				productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO, Common.GBJ));
				//降价返券的需求
				queryBillCacilateDto = Common.getQueryParam(bean, FossConstants.NO, Common.GBJ);
			}
			//降价返券的需求
			queryBillCacilateDto = Common.getQueryParam(bean, FossConstants.YES, Common.GBJ);
			if (CollectionUtils.isEmpty(productPrice)) {
				
				productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO, Common.GBJ));
				//降价返券的需求
				queryBillCacilateDto = Common.getQueryParam(bean, FossConstants.NO, Common.GBJ);
				/**
				 * 注释掉无用代码
				 * 时间：2014-03-05
				 * 内容：MANA-257接货费优化
				 * 作者：026123
				 */
				isPickupPrice = Boolean.valueOf(false);
//				ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEnabled(true);
//				ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEditable(true);
//
//                List<ValueAddDto> list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,false));
//				
//				if(list==null||list.isEmpty()){
//					 list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,true));
//				}
//				ValueAddDto dto = null;
//				//设置其他费用的折扣优惠
//				if (list != null && !list.isEmpty()) {
//					dto = list.get(0);
//					//更改单不需要重新设置接货费，应该以开单的接货费为准
////					setPickupFee(bean, dto);
//					// 设置折扣优惠
////					Common.setFavorableDiscount(dto.getDiscountPrograms(), bean);
//				} else {
//					//更改单不需要重新设置接货费，应该以开单的接货费为准
////					setPickupFee(bean, null);
//				}
			
			}else{
				//定价体系优化项目POP-530
				isPickupPrice = Boolean.valueOf(true);
			}
		} else {
			productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO, Common.GBJ));
			//降价返券的需求
			queryBillCacilateDto = Common.getQueryParam(bean, FossConstants.NO, Common.GBJ);
		}
		
		//开单版本取不到读当前版本
		//精准包裹不走这个逻辑
		if(CollectionUtils.isEmpty(productPrice) &&
				!StringUtil.equals(bean.getFinalProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)){

			// 查询产品价格
			if (bean.getPickupToDoor()) {
				productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.YES, Common.GBJ));
				//降价返券的需求
				queryBillCacilateDto = Common.getNowQueryParam(bean, FossConstants.YES, Common.GBJ);
				if (productPrice == null) {
					productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.NO, Common.GBJ));
					//降价返券的需求
					queryBillCacilateDto = Common.getNowQueryParam(bean, FossConstants.NO, Common.GBJ);
					/**
					 * 抽取公共方法
					 * 时间：2014-03-05
					 * 内容：MANA-257接货费优化
					 * 作者：026123
					 */
					isPickupPrice = Boolean.valueOf(false);
					valueAdddto = getValueAddDto(bean);
				}else{
					//定价体系优化项目POP-530
					isPickupPrice = Boolean.valueOf(true);
				}
			} else {
				productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.NO, Common.GBJ));
				//降价返券的需求
				queryBillCacilateDto = Common.getNowQueryParam(bean, FossConstants.NO, Common.GBJ);
			}
		}
		
		ProductPriceDto dto = null;
		if (CollectionUtils.isEmpty(productPrice)) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		} else {
			for (ProductPriceDto fee : productPrice) {
				if(fee.getCaculateFee() == null){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
				}
				
			    BigDecimal transportFee = fee.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			    
			    //如果是内部变更，且有返货记录，并且选择的不是第一条，价格计算至中转费中
			    if(WaybillRfcConstants.INSIDE_CHANGE.equals(bean.getRfcType().getValueCode())
			    		&&((bean.getReturnRecordList().size()> 0 || bean.getTransferRecordList().size()> 0))
			    		&& ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getSelectedRow()!=0){
			    	//第2行更新第一次中转费，第3行更新第二次中转费；依次类推
			    	int row = ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getSelectedRow();
			    	if(row<0){
			    		return null;
			    	}
//			    	// 设置运费
//			    	bean.setRecordUnitPrice(fee.getActualFeeRate());
//					// 设置费率
//			    	bean.setRecordFee(transportFee);
//					// 设置计费类型（重量计费或者体积计费）
//			    	DataDictionaryValueVo billType = new DataDictionaryValueVo();
//			    	billType.setValueCode(fee.getCaculateType());
//			    	bean.setRecordBillingType(billType);
			    	//修改中转费
			    	changeTransportOtherCharge(bean, row);
//					dto = fee;
//					//修改转运的记录，不重新计算公布价
//					bean.setIsCalTraFee(FossConstants.NO);
			    }else{
			    	 //如果是内部变更，且有返货记录,修改运输信息变更面板
		    	  if(WaybillRfcConstants.INSIDE_CHANGE.equals(bean.getRfcType().getValueCode())
				    		&& ((bean.getReturnRecordList().size()> 0 || bean.getTransferRecordList().size()> 0))){
				    	// 设置运费
				    	bean.setRecordUnitPrice(fee.getActualFeeRate());
						// 设置费率
				    	bean.setRecordFee(transportFee);
						// 设置计费类型（重量计费或者体积计费）
				    	DataDictionaryValueVo billType = new DataDictionaryValueVo();
				    	billType.setValueCode(fee.getCaculateType());
				    	bean.setRecordBillingType(billType);
		    	  }

		    	  	//设置ID，DP-foss 343617 zhaoyiqing 20160820
					bean.setTransportFeeId(fee.getId());

			    	// 设置运费
					bean.setTransportFee(transportFee);
					//偏线费用 2016年11月8日16:35:41  by:352676
					BigDecimal partialTransportFee = null != fee.getPartialTransportFee() ? fee.getPartialTransportFee() : BigDecimal.ZERO;
					partialTransportFee = partialTransportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
					bean.setPartialTransportFee(partialTransportFee);
					
					// 设置费率
					bean.setUnitPrice(fee.getActualFeeRate());
					// 设置计费类型（重量计费或者体积计费）
					setBillingWay(fee.getCaculateType(), bean);
					
					//设置计费重量
					setBillWeight(bean,fee);
					
					// 画布公布价运费
					bean.setTransportFeeCanvas(fee.getCaculateFee().toString());
					dto = fee;
					
					//计算了公布价
					bean.setIsCalTraFee(FossConstants.YES);
//					// 设置折扣优惠
//					Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
//
//					//设置折扣值
//					setDiscount(dto.getDiscountPrograms(),bean);
			    }
			}
			
			/**
			 * 定价优化项目：降价返券需求
			 * 
			 * @author Foss-206860
			 * **/
			bean.setQueryBillCacilateDto(queryBillCacilateDto);
			
		   /**
		    * 根据接货区域的非接货方案读取接货费的增值服务信息
			* 时间：2014-03-05
			* 内容：MANA-257接货费优化
			* 作者：026123
			*/
			if (StringUtil.equals(bean.getFinalProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)) {
				//价格中包含就设置为真并且选择上门接货
				if(CollectionUtils.isNotEmpty(productPrice)){
					isPickupPrice = StringUtil.equals(productPrice.get(0).getCentralizePickup(), FossConstants.YES) && bean.getPickupToDoor();
					//此处不需要置空valueAdddto，因为走不到赋值逻辑
				}else{
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
				}
			}

			//若为上门接货的非接货价，则满足“MANA-257接货费优化”的业务规则
			if(bean.getPickupToDoor() && isPickupPrice != null && !isPickupPrice.booleanValue()){
				//判断是否有增值服务对象
				if(null == valueAdddto){
					getValueAddDto(bean);
				}
			}
			
		}
		return dto;
	}
	
	   /**
	    * 查询接货费增值服务信息，并设置接货费信息
		* 时间：2014-03-05
		* 内容：MANA-257接货费优化
		* 作者：026123
		*/
	private ValueAddDto getValueAddDto(WaybillInfoVo bean){
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,false));
		ValueAddDto valueAdddto = null;
		if(list==null||list.isEmpty()){
			 list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,true));
		}
		
		//设置接货费的折扣优惠
		if (list != null && !list.isEmpty()) {
			valueAdddto = list.get(0);
			setPickupFee(bean, valueAdddto);
		} else {
			setPickupFee(bean, null);
		}
		
		return valueAdddto;
	}
	
	
	/**
	 * 获得折扣优惠信息
	 * @author WangQianJin
	 * @date 2013-6-18 上午9:36:06
	 */
	private void getFavorableDiscount(WaybillInfoVo bean) {
		//判断是否已累加过折扣优惠信息
		if(!FossConstants.YES.equals(bean.getIsFlagAddDiscount())){
			List<ProductPriceDto> productPrice = null;
			//公布价读取的是否是接货价，默认为空--//定价体系优化项目POP-530
			Boolean isPickupPrice = null;
			//精准包裹特有逻辑
			if (StringUtil.equals(bean.getFinalProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)) {
				productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, null, Common.GBJ, false));
				if(CollectionUtils.isNotEmpty(productPrice)){
					//价格中包含就设置为真
					isPickupPrice = StringUtil.equals(productPrice.get(0).getCentralizePickup(), FossConstants.YES) && bean.getPickupToDoor();
				}else{
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
				}
			}else {

				/**
				 * 查询开单版本的产品折扣
				 */
				if (bean.getPickupToDoor()) {
					//上门接货
					//当勾选上门接货并没有勾选选免费接货时
					//@author ZouShengLi
					// @date 2016-4-15 下午18:00:00
					if (!bean.getFreePickupGoods()) {
						productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.YES, Common.GBJ, false));
						//当价格方案没有配接货时走一下逻辑
						if (productPrice == null) {
							productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, false));
							//定价体系优化项目POP-530
							isPickupPrice = Boolean.valueOf(false);
						} else {
							//定价体系优化项目POP-530
							isPickupPrice = Boolean.valueOf(true);
						}
					} else {
						productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, false));
						//定价体系优化项目POP-530
						isPickupPrice = Boolean.valueOf(false);
					}

				} else {
					//非上门接货
					productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, false));
				}
				/**
				 * 查询当前版本的产品折扣
				 */
				GetIsPickupAndPrice getIsPickupAndPrice = new GetIsPickupAndPrice(bean, productPrice, isPickupPrice).invoke();
				productPrice = getIsPickupAndPrice.getProductPrice();
				isPickupPrice = getIsPickupAndPrice.getPickupPrice();
			}
			/**
			 * 设置产品折扣
			 */
			ProductPriceDto dto = null;
			if(productPrice!=null){
				for (ProductPriceDto fee : productPrice) {
					dto = fee;
					Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
					setDiscount(dto.getDiscountPrograms(),bean);			    
				}
			}
			
			/*判断是否有接货费*/
			//定价体系优化项目POP-530
			if(bean.getPickupToDoor() != null && bean.getPickupToDoor() && isPickupPrice != null && !isPickupPrice.booleanValue()){
				
				if (dto != null && FossConstants.ACTIVE.equals(dto.getCentralizePickup())) {
					ui.incrementPanel.getTxtPickUpCharge().setEnabled(false);
					bean.setPickupFee(BigDecimal.ZERO);//设置接货费
					// 画布-接货费
					bean.setPickUpFeeCanvas(BigDecimal.ZERO.toString());
				}else {
					/**
					 * 查询开单版本的接货费优惠
					 */
					List<ValueAddDto> list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,false));
					if(list==null||list.isEmpty()){
						/**
						 * 查询当前版本的接货费优惠
						 */
						list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,true));
					}
					ValueAddDto valueDto = null;
					/**
					 * 设置接货费优惠
					 */
					if (list != null && !list.isEmpty()) {
						valueDto = list.get(0);				
						// 设置折扣优惠
						Common.setFavorableDiscount(valueDto.getDiscountPrograms(), bean);
					}
					//日常版本缺陷管理DEFECT-8250 手动修改接货费后，费用变化为原计算费用
//					/**
//					 * 设置接货费( MANA-257接货费优化)
//					 */
//					//设置接货费最大值
//					bean.setMaxPickupFee(CommonUtils.defaultIfNull(valueDto.getMaxFee()));
//					//设置接货费最小值
//					bean.setMinPickupFee(CommonUtils.defaultIfNull(valueDto.getMinFee()));
//					//设置接货费实际值
//					setPickupCollection(bean,valueDto);
				}
				
			}else{
				ui.incrementPanel.getTxtPickUpCharge().setEnabled(false);
				bean.setPickupFee(BigDecimal.ZERO);//设置接货费
				bean.setBasePickupFee(BigDecimal.ZERO);//原始接货费
			}
			
		}		
	}
	
	/**
	 * 设置接货费
	 * MANA-257接货费优化
	 */
	private void setPickupCollection(WaybillInfoVo bean, ValueAddDto dto) {
		// 封装查询数据
		WaybillEntity queryParm = CommonUtils.getWaybillEntity(bean);
		// 是否为发货人在当前收货部门的当天8时到次日8时间的第一票
		boolean firstWaybill = waybillService.isFirstDeliveryWaybill(queryParm);
		// 判断是否是第一票
		if (firstWaybill) {
			bean.setPickupFee(dto.getCaculateFee());// 设置接货费
			bean.setBasePickupFee(dto.getCaculateFee());// 原始接货费
			// 画布-接货费
			bean.setPickUpFeeCanvas(dto.getCaculateFee().toString());
			//判断最低一票与折扣金额
			if(dto.getMinFee() != null){
				if(dto.getFee()==null&&dto.getCaculateFee().compareTo(dto.getMinFee()) < 0){
					bean.setPickupFee(dto.getMinFee());// 设置接货费
					bean.setBasePickupFee(dto.getMinFee());// 原始接货费
					// 画布-接货费
					bean.setPickUpFeeCanvas(dto.getMinFee().toString());
				}
			}
			//判断最高一票与折扣金额
			if(dto.getMaxFee() != null){
				if(dto.getFee()==null&&dto.getCaculateFee().compareTo(dto.getMaxFee()) > 0){
					bean.setPickupFee(dto.getMaxFee());// 设置接货费
					bean.setBasePickupFee(dto.getMaxFee());// 原始接货费
					// 画布-接货费
					bean.setPickUpFeeCanvas(dto.getMaxFee().toString());
				}
			}
			//【固定值】
			// 返回的接货费值不为空，则表示有客户编码且有设置客户接货费值
			if (null != dto.getFee()) {
				ui.incrementPanel.getTxtPickUpCharge().setEnabled(false);
				ui.incrementPanel.getTxtPickUpCharge().setEditable(false);
			} 
			//【默认费用，最小值，最大值】
			else{
				//DEFECT-5057 :开单时接货费不要代码限制为0，读取接货费基础资料实现
				ui.incrementPanel.getTxtPickUpCharge().setEnabled(true);
				ui.incrementPanel.getTxtPickUpCharge().setEditable(true);
			}
		}
		//【0，最大值】
		// 非第一票,默认为0，接货费只能在0到最大值之间
		else {
			ui.incrementPanel.getTxtPickUpCharge().setEnabled(true);
			ui.incrementPanel.getTxtPickUpCharge().setEditable(true);
//			bean.setPickupFee(BigDecimal.ZERO);// 设置接货费
//			bean.setBasePickupFee(BigDecimal.ZERO);// 原始接货费
//			// 画布-接货费
//			bean.setPickUpFeeCanvas(BigDecimal.ZERO.toString());
		}		
	}
	
	

	/**
	 * 
	 * 修改中转费
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-10 上午9:31:37
	 */
	public void changeTransportOtherCharge(WaybillInfoVo bean, int row) {
		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//中转费Code
		String zzCode = PriceEntityConstants.PRICING_CODE_ZZ;
		//中转费用项
		OtherChargeVo zzfChargeVo = null;
		int index = 1;
		for(OtherChargeVo chargeVo : data){
			if(zzCode.equals(chargeVo.getCode())&&chargeVo.getIsInit()){
				if(index == row){
					zzfChargeVo = chargeVo;
					break;
				}
				index++;
			}
		}
		if(zzfChargeVo != null){
			BigDecimal oldZZF = new BigDecimal(zzfChargeVo.getMoney());
			BigDecimal newZZF = bean.getRecordFee();
			//获取中转费用明细
			zzfChargeVo.setMoney(newZZF.toPlainString());
			zzfChargeVo.setIsEdit(true);
			bean.setOtherFee(bean.getOtherFee().subtract(oldZZF).add(newZZF));
			ui.incrementPanel.setChangeDetail(data);
			bean.setOtherChargeChanged(true);
		}
	}
	
	
	
	/**
	 * 
	 * 设置折扣优惠
	 * @author 025000-FOSS-helong
	 * @date 2013-3-9 下午01:46:39
	 */
	private void setDiscount(List<PriceDiscountDto> discountPrograms,WaybillPanelVo bean)
	{
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			for (PriceDiscountDto dto : discountPrograms) {
				//设置
				bean.setDiscount(dto.getDiscountRate());
			}
		}
	}
	
	/**
	 * 
	 * 设置计费重量
	 * @author 025000-FOSS-helong
	 * @date 2013-3-2 下午12:03:25
	 */
	private void setBillWeight(WaybillPanelVo bean,ProductPriceDto fee)
	{
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			
			if(WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode()))
			{
				bean.setBillWeight(bean.getGoodsWeightTotal());
			}else
			{
				// 计费重量
				if (fee.getVolumeWeight() == null) {
					bean.setBillWeight(BigDecimal.ZERO);
				} else {
					bean.setBillWeight(fee.getVolumeWeight());
				}
			}
		}else
		{
			bean.setBillWeight(BigDecimal.ZERO);
		}

	}

	
	/**
	 * 
	 * 计算增值费用
	 * 
	 * 1. 开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
	 * 费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置）
	 * 2. 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
	 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置）
	 * 3. 综合服务费：（费用金额由基础资料配置）
	 * 3.1 综合服务费默认为2元不可修改、剔除；
	 * 3.2 月结客户可以删除2元的综合服务费；
	 * 3.3 淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费；
	 * 4. 燃油附加费：（费用金额由基础资料配置）
	 * 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
	 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；
	 * 5. 其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改；
	 * 其他费用合计等于其他费用列表中各项费用数据之和；
	 * 
	 * 1. 原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单；
	 * 2. 空运、偏线和中转下线的“返单类型”不可选择；
	 * 3. 若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用；
	 * 4. 如果选择有返单类型，系统会自动生成一条签收单记录，记录信息包含：运单号、运单ID、库存状态、
	 * 当前操作部门（运单开单时，是填开部门）、是否签收、是否作废、出发部门(运单开单出发部门)、签收单类型、签收状态；
	 * 月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
	 * 非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除
	 * 
	 * 1. 运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密；
	 * 2. 已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。未开启预付运费保密运单提交后，
	 * 若货物未有非本部门入库操作，则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
	 * 则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单；
	 * 3. 运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择；
	 * 4. 已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，涉及预付运费有变动时，不影响预付费保密功能；
	 * 5. 开启预付运费保密，预付运费不能为0，否则不能保存、提交；
	 * 6. 开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行；
	 * 7. 开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时，必须取消预付运费保密后才能提交；
	 * 8. 预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示；
	 * 预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示；
	 * 
	 * "1. ；2. 1）开单总费用、预付金额、到付金额，取整，四舍五入； 
	 * 2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100,100.10显示100.1； 
	 * 3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。 
	 * 4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。"
	 * 1. 接货费只能录入数字
	 * "1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 
	 * 2）限保物品的保价费率通过基础资料进行配置； 
	 * 3）取消偏线、空运最高保价5000元的限制； 
	 * 
	 * 2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置 
	 * 
	 * 3、""其它费用""中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。 
	 * 
	 * 4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制"
	 * 
	 * 	 * 
    	 * 1. 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,
 * 且保价费率可手动调整；
 * （限保物品从限运物品基础资料中获取）；
 * 非限保物品的保价费率不可修改；
 * 2. 实际保险费小于最低保费的按最低保费收取；
 * 3. 保价费 = 保价申明价值*保价费率，不可修改；
 * 4. 保价申明价值默认为3000，可以修改；
 * 保价声明价值不封顶；
 * 5. 精准（长途）、普货（长途）、偏线，最低一票8元；
 * 普货（短途）、精准（短途）最低一票6元；
 * 空运最低一票10元；
 * 所有运输方式保价超过最低均按0.4%收取（数据读取自保价设置基础资料）；
 * 长短途数据读取计价基础资料；
 * 6. 实际保价费小于最低保费的按最低保费收取；
 * 7. 保价费率首先是配置的标准费率。
 * 当有区域保价费率时，以区域保价费率为准。
 * 当客户为合同客户时，则以合同签订为准。
 * 所有的保价费率以读取的为准，不可修改。
 * 限保物品的保价费率同样不可修改
 * 8. 行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，
 * 开单是否可以进行返回签单、货到付款、
 * 代收货款需要根据到达部门属性是否可以（返回签单、货到付款、代收货款）来决定			
 * 1. 如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，
 * 代收货款设置为0且不可编辑；
 * 2. 如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，
 * 代收货款可编辑，且可以选择所有的退款类型，若CRM中若无账户信息，
 * 则退款类型只能选择审核退
 * 3. 开单时系统默认代收货款为空；
 * 4. 代收货款栏默认为空，如果没有代收货款，则要求输入0；
 * 否则，进行提示：“请确认该单没有代收货款，如无，请输入数字0”；
 * 当代收货款大于0时，输入后，对于选择的退款类型，
 * 有如下限制：
 * 3.1 三日退：在收到客户代收货款后第三天给客户打款。
 * 3.1.1 默认退款类型为三日退；
 * 3.1.2 代收10000元以下费率0.5%， 
 * 10000元以上费率0.4%；最低10元/票，最高100元/票；
 * 有部分城市三日退费率为0.（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2 审核退：收到客户代收货款，出发部门审核后，给客户打款。
 * 3.2.1 代收10000元以下费率0.5%，10000元以上费率0.4%；
 * 最低10元/票，最高100元/票（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.2.2 选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。
 * 3.3 即日退：在收到客户代收货款后24小时到账。
 * 3.3.1 代收手续费率1%，
 * 最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
 * 3.3.2 必须先录入客户收款银行信息，提交时，银行信息不能为空；
 * 3.3.3 只支持4个银行：农行、工行、建行、招行；否则，给予提示信息；
 * 5. 限制代收货款金额不能小于10元，可以等于10元；
 * 但可以为0；该数字“10”可由基础资料配置；
 * 6. 网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，
 * 有数据时不可对代收货款进行修改，只可起草出发更改进行修改；
 * 若网上代收货款为0 ，系统可支持修改代收货款金额；
 * 7. 默认的代收费率由基础资料配置；			
 * 1. 保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额；			
 * 1. 代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，
 * 且只能选择，不能修改；当退款人姓名和帐号唯一时，直接显示；
 * （数据读取CRM客户信息资料（退款帐户信息））
 * 2. CRM客户信息资料的要先在CRM中录入客户退款帐户信息，
 * 且第一次在我司办理代收货款业务时，退款类型只能为审核退；
 * 3. 同一客户多个银行信息的显示问题：当有两个或以上账号时，
 * 弹出账号信息（包括开户银行、收款人、账号、备注），选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置；			
 * 1. 包装费默认为0，可手工修改；
 * 2. 当录入有打木加信息时，默认显示包装费=max（150*打木架货物体积，30）+max（300*打木箱货物体积，40），
 * 且可修改，修改的金额只能大于等于默认显示金额；
 * 其中150、30、300、40为打木架单价（元/方）、打木架最低一票、打木箱单价（元/方）、打木箱最低一票，
 * 可由基础资料配置；			
 * 1. 装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
 * 2. 如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，
 * 为系统默认的公布价。开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
 * 开单显示运费（C）=Q*Z=C0+M ；
 * 3. 当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），
 * 需先清空装卸费为零，然后再修改数据。如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
 * 4. 当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。
 * （其中Q1是装卸费M1时的费率,Z为重量/体积）；
 * 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
 * （只限制配载类型为专线的，包括月结）；
 * 6. 对于显示费率不等于显示费率乘以重量的问题，要求如下：
 * 6.1. 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
 * 且显示运费等于该显示费率*重量；
 * 6.2. 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，
 * 令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
 * 则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
 * 7. 只要含与不含装卸费两者有交叉的，均以不含为准；
 * 8. 装卸费特殊部门表：（建议：做为可配置的基础数据表）
 * 9. 2012年12月1日开业的部门不能开装卸费
 * 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
 * 11. 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
 * 12. 装卸费上限由增值服务配置基础资料实现（在产品API中体现）。13. 14. 15. 			
 * 1. 送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，不能下调。
 * 当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值，但用户可以上调送货费；			
 * 2. 通过送货费基础资料来实现：			
 * 2.1. 若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止；			
 * 2.2. 若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
 * （即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值，取值终止。）			
 * 2.3. 特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 			
 * 2.3.1 先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，
 * 再判断开单重量在已被体积筛选出来的记录中的哪个重量区间，来确定是哪一条记录。
 * 然后再根据费率计算，计算出来的值与该条的最低送货费进行比较，若小于最低送货费时，
 * 就取最低送货费，取值终止；若大于最低送货费且小于最高送货费时，就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。			
 * 2.3.2 标淮派送范围收取送货费标准：			
 * 货物重量	标准		
 * 0-300KG	55元/票		
 * 301-500KG	0.2元/KG		
 * 501KG或2.5立方米以上	100元/票，不封顶		
 * 2.3.3 当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
 * 开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于【最高送货费】”			
 * 2.3.4 仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。			
 * 2.3.5 “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也可以向下修改，最小为0）			
 * 2.3.6 除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改			
 * 2.3.7 最高送货费做基础资料配置；			
 * 3. 非标准派送范围加收操作费标准：			
 * 3.1 超远加收送货费标准：			
 * 距离（公里）	30-50	50-100	100-150
 * 加收金额（元）	50	100	150
 * 3.1.1 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）；			
 * 3.1.2 客户所在地30公里范围内如果有公司的营业网点，
 * 无论是否做派送，该区域均不能收取超远加收送货费；			
 * 3.1.3 非标准派送的费用添加无上限			
 * 3.2 特殊区域（进仓）：			
 * 3.2.1 特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）；			
 * 3.2.2 收费标准：进仓费实报实销，并加收150元操作费；			
 * 4. 区域送货费限制：			
 * 4.1 当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，
 * 但前提是：提货方式必须为送货)时，送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置）			
 * 4.2 当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦营业部、深圳华强钟表市场营业部）。
 * （该类营业部类型、送货费固定标准、成本提取标准可配置）			
 * 4.3 内部带货、空运、偏线及中转下线不受上述需求的限制。			
 * 5. 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域：			
 * 5.1 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，或体积是否超过X立方，
 * 是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。
 * （单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】			
 * 5.2 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，
 * 其他费用中的“送货上楼费”屏蔽或显示但不可选择；			
 * 5.3 若“XX区域”或其它限制类型区域再开派送部，适用以上需求；			
 * 5.4 空运、偏线及中转下线不受上述需求的限制；			
 * 5.5 内部带货受上述需求的限制；			
 * 5.6  “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置；			
 * 1. 开单时系统默认其他费用为综合服务费和燃油附加费且不可移除,其他费用由手动添加，
 * 费用根据基础资料中的内容进行读取，根据开单内容自动添加；（可基础资料配置）
 * 2. 运输类型为“精准（长途）、普货（长途）、偏线、空运”时，燃油附加费默认为4元；
 * 运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；（可基础资料配置）
 * 3. 综合服务费：（费用金额由基础资料配置）
 * 3.1 综合服务费默认为2元不可修改、剔除；
 * 3.2 月结客户可以删除2元的综合服务费；
 * 3.3 淘宝、阿里巴巴订单导入开单时，系统自动不收取2元的综合服务费；
 * 4. 燃油附加费：（费用金额由基础资料配置）运输类型为“精准（长途）、普货（长途）、偏线、空运”时，
 * 燃油附加费默认为4元；运输类型为“普货（短途）、精准（短途）”时，燃油附加费默认为2元；均不可修改；
 * 5. 其他费用中“是否可修改”打勾时，对应费用类型的金额可以金额上限和金额下限之间修改；
 * 6. 其他费用合计等于其他费用列表中各项费用数据之和；			
 * 1. 原件签收单返回类：系统默认收取客户15元/票，可更改收取客户20元/票。偏线不允许做签收单；
 * 2. 空运、偏线和中转下线的“返单类型”不可选择；
 * 3. 若有选择，则返单费用信息自动显示在其他费用信息列表中，可以其它费用列表中修改签收费用；
 * 4. 如果选择有返单类型，系统会自动生成一条签收单记录，
 * 记录信息包含：运单号、运单ID、库存状态、当前操作部门（运单开单时，是填开部门）、
 * 是否签收、是否作废、出发部门(运单开单出发部门)、签收单类型、签收状态；
 * 5. 月结客户允许修改对应返单类别的返单金额（区间为对应返单类别的上限值和下限值），
 * 非月结客户只能选择对应的返单类别的默认金额，不能修改；返单费用项目不能删除			
 * 1. 运单新增时，运输性质选择空运、提货网点非我司自有网点时，不能选择预付运费保密；
 * 2. 已开启预付运费保密运单提交后，始发更改中预付运费保密可以取消。
 * 未开启预付运费保密运单提交后，若货物未有非本部门入库操作，
 * 则始发更改中预付运费保密可选择；若货物有非本部门入库操作，
 * 则始发更改中预付运费保密否可选择，若要更改，则必须返货后，作废重新开单；
 * 3. 运单保存未提交时，可以在调出运单的时候，预付运费保密自由选择；
 * 4. 已开启预付运费保密的运单，始发更改中预付更改到付或到付更改预付，
 * 涉及预付运费有变动时，不影响预付费保密功能；
 * 5. 开启预付运费保密，预付运费不能为0，否则不能保存、提交；
 * 6. 开单付款方式为临时欠款时，预付运费保密选项不可选择，其他付款方式都行；
 * 7. 开启预付运费保密，运单出库后，始发更改中运输性质由汽运专线更改为偏线、空运时，
 * 必须取消预付运费保密后才能提交；
 * 8. 预付运费保密开启时，非出发部门用户综合查询预付运费不显示，即“预付金额”不显示；
 * 9. 预付运费保密开启后，到达联打印时，费率、运费、其他服务费用明细、费用合计中的现付总计均不显示；			
 * "1. ；2. 1）开单总费用、预付金额、到付金额，取整，四舍五入； 
 * 2）开单所有录入的金额数值，最多可录入到小数点后两位小数，无效的0省略不显示，如100.00显示100,100.10显示100.1； 
 * 3）开单总费用、预付金额、到付金额之外的，即中间环节的金额保留两位小数，依据四舍五入规则。 
 * 4）费率最多可保留两位小数，小数点多于两位时，全进1。比如，系统折算生成的费率为1.201时，显示为1.21。"			
 * 1. 接货费只能录入数字			
 * "1）限保物品不限制保价金额，限制保价费率，增值服务部门可以配置对应限保物品的保价费率； 
 * 2）限保物品的保价费率通过基础资料进行配置； 
 * 3）取消偏线、空运最高保价5000元的限制； 
 * 
 * 2、装卸费开单运费占比及特殊部门装卸费需做成基础资料配置 
 * 
 * 3、""其它费用""中的费用名称对应的归集类别的费用划分到增值服务对应的费用中，同时需增加归集类别的配置资料。 
 * 
 * 4、超远派送送货标准的基础资料是需要配置的；超远派送费用无上限限制"		
	 * 
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午08:58:39
	 */
	private void calculateValueAdd(WaybillInfoVo bean) {
		//定价项目POP-407foss没有将接货金额、保费金额5个字段传给规则引擎（更改单）
		 Map<String,GuiQueryBillCalculateSubDto> valuedtos=new HashMap<String,GuiQueryBillCalculateSubDto>();
		 //保费
		 GuiQueryBillCalculateSubDto bfDto  = new GuiQueryBillCalculateSubDto();
		//精准包裹特有逻辑
		if (bean != null && bean.getFinalProductCode() != null && StringUtil.equals(bean.getFinalProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)) {
			if (bean.getInsuranceAmount().compareTo(new BigDecimal(NumberConstants.NUMBER_2000))>0){
				bfDto.setOriginnalCost(bean.getInsuranceAmount().subtract(new BigDecimal(NumberConstants.NUMBER_2000)));
				valuedtos.put(PriceEntityConstants.PRICING_CODE_BF, bfDto);
			}
		} else {
			bfDto.setOriginnalCost(bean!=null ? bean.getInsuranceAmount() : bfDto.getOriginnalCost());
			valuedtos.put(PriceEntityConstants.PRICING_CODE_BF, bfDto);
		}
		 //代收
		 GuiQueryBillCalculateSubDto hkDto  = new GuiQueryBillCalculateSubDto();
		 hkDto.setOriginnalCost(bean != null ? bean.getCodAmount() : hkDto.getOriginnalCost());
		 valuedtos.put(PriceEntityConstants.PRICING_CODE_HK, hkDto);
		if(bean!=null){
			bean.setValuedtos(valuedtos);
			// 保价费
			getInsurance(bean);
			// 代收货款手续费
			getCod(bean);
			// 送货费
			getDeliveryFee(bean);
			//签收单折扣
			setReturnBillCollection(bean);
//--------------上面的设置了原始费用和上门接货-----------
//		Common.setDeliveryFeeCollection(bean);
			// 计算装卸费
			calculateServiceFee(bean);
//		// 计算转运费
//		calculateTransferFee(bean);
//		// 计算返货费
//		calculateReturnFee(bean);
			//超重货操作服务费
			setCZHCZFWFCollection(bean);
			//添加其他费用 折扣
			queryOtherChargeData(bean);
			//验证转运与返货时费率是否为空
			validateUnitPrice(bean);
			//规则引擎会对其他费用打折，重新再其他费用面板赋值
			setterOtherFeeByRuleEngine(bean);

			//推广活动会对其他费用打折，重新在其他费用面板赋值
			setterOtherFeeByMakActive(bean);
		}

	}
	
	private void setCZHCZFWFCollection(WaybillInfoVo bean) {
		// TODO Auto-generated method stub
		//获取其他费用
		boolean flag=false;
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();				
		if(CollectionUtils.isNotEmpty(data)){
			for(OtherChargeVo vo : data){
				if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())){
					flag=true;								
					break;						
				}
			}						
		}
		if(flag){
			QueryBillCacilateValueAddDto addDto=getQueryOtherChargeParam(bean);
			//发货客户编码
			addDto.setCustomerCode(bean.getDeliveryCustomerCode());
			waybillService
					.queryValueAddPriceList(addDto);
			
			//设置其他费用的折扣优惠
			//修复pop-532 更改单-起草更改单，优惠金额会增多  超重货操作服务费重复收取
//			if(list!=null && list.size()>0){
//				for (ValueAddDto valueAddDto : list) {
//					if (PriceEntityConstants.QT_CODE_CZHCZFWF.equals(valueAddDto.getSubType())) {
//						Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), bean);
//						break;
//				}
//					
//				}
//			}
		}
		
	}

	/**
	 * 签收单折扣
	 * pgy
	 * @param bean
	 */
	private void setReturnBillCollection(WaybillInfoVo bean) {
		
		JXTable otherTable = ui.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())) {
            
			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(bean.getReturnBillType().getValueCode())) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = bean.getReturnBillType().getValueCode();
			}
			 
			QueryBillCacilateValueAddDto addDto=getQueryOtherChargeParam(bean);
			//发货客户编码
			addDto.setCustomerCode(bean.getDeliveryCustomerCode());
			// 签回单
			addDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);
			addDto.setSubType(type);
			List<ValueAddDto> list = waybillService.queryValueAddPriceList(addDto);
			
			OtherChargeVo otherVo = getReturnBillCharge(bean,list);
			//设置其他费用的折扣优惠
			if(list!=null && list.size()>0){
				for (ValueAddDto valueAddDto : list) {
					if (PriceEntityConstants.PRICING_CODE_QS.equals(valueAddDto.getPriceEntityCode())) {
						Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), bean);
						break;
				    }
				}
				addReturnBillCharge(data, otherVo, bean);// 添加返单费用到其他费用表格
			}
			
		} else {
			deleteOtherCharge(data, bean);// 将已有的返单费用从其他费用表格中删除
		}
		ui.incrementPanel.setChangeDetail(data);
		
			
		
	}
	
	/**
	 * 
	 * 获取返单费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getReturnBillCharge(WaybillInfoVo bean,List<ValueAddDto> list) {
		ValueAddDto dto = new ValueAddDto();
		OtherChargeVo vo = new OtherChargeVo();
		if (CollectionUtils.isNotEmpty(list)) {
			dto = list.get(0);
			vo.setChargeName(dto.getPriceEntityName());
			//vo.setMoney(dto.getFee().toString());修复POP-414，应该设置计算费用,而不是固定费用
			//将费用四舍五入
			BigDecimal money = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
			vo.setMoney(money.toString());
			vo.setCode(dto.getPriceEntityCode());
			vo.setId(dto.getId());
		}else{
			MsgBox.showError(i18n.get("foss.gui.changing.waybillInfoBindingListener.msgBox.nullQueryParam"));
			return null;
		}
		return vo;
	}
	
	/**
	 * 
	 * 对其他费用进行校验，判断是否存在返单费用，不存在则添加返单费用到其他费用，并且进行其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 下午07:06:33
	 */
	private void addReturnBillCharge(List<OtherChargeVo> data, OtherChargeVo returnBillVo, WaybillInfoVo bean) {
		boolean bool = true;
		OtherChargeVo oldVo = null;
		for (int i = 0; i < data.size(); i++) {
			OtherChargeVo otherVo = data.get(i);
			// 比较费用名称，判断是否存在重复的返单费用
			if (otherVo.getChargeName().equals(returnBillVo.getChargeName())) {
				bool = false;
				oldVo = data.get(i);
				data.remove(i);
				data.add(i, returnBillVo);
			}
		}

		// 如果不存在任何返单费用，则直接添加
		if (bool) {
			data.add(returnBillVo);
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal newMoney = new BigDecimal(returnBillVo.getMoney());
			otherChargeSum = otherChargeSum.add(newMoney);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		} else {
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal oldMoney = new BigDecimal(oldVo.getMoney());
			BigDecimal newMoney = new BigDecimal(returnBillVo.getMoney());
			BigDecimal money = newMoney.subtract(oldMoney);
			otherChargeSum = otherChargeSum.add(money);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		}

		// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
		bean.setReturnBillChargeName(returnBillVo.getChargeName());
	}
	
	/**
	 * 
	 * 如果选择的返单类型为无返单，那么需要将之前存在的返单费用删除
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 上午10:44:29
	 */
	private void deleteOtherCharge(List<OtherChargeVo> data, WaybillInfoVo bean) {
		for (int i = 0; i < data.size(); i++) {
			OtherChargeVo otherVo = data.get(i);
			String chargeName= otherVo.getChargeName();
			if(StringUtil.isNotEmpty(chargeName)){
				// 比较费用名称，判断是否存在重复的返单费用
				if (otherVo.getChargeName().equals(bean.getReturnBillChargeName())  
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(otherVo.getCode())) {
					//POP-196 单号501501302客户要求发更改，签收单传真返单改成无需返单，仍有返单费用
					data.get(i).setMoney("0");
//					data.remove(i);
					// 累计其他费用合计
					BigDecimal otherChargeSum = bean.getOtherFee();
					BigDecimal money = new BigDecimal(otherVo.getMoney());
					otherChargeSum = otherChargeSum.subtract(money);
					bean.setOtherFee(otherChargeSum);
					bean.setOtherFeeCanvas(bean.getOtherFee().toString());
					break;
				}
			}		
		}
	}
	
	/**
	 * 规则引擎会对其他费用打折，重新在其他费用面板赋值
	 */
	private void setterOtherFeeByRuleEngine(WaybillInfoVo bean){	
		
		//获取面板上的其他费用信息
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		//获取折扣明细
		List<WaybillDiscountVo> waybillDiscountVos = bean.getWaybillDiscountVos();
		if(CollectionUtils.isNotEmpty(waybillDiscountVos)){
			for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
				//当类型为其他费用时，重新获取折扣后的费用金额
				//定价体系优化项目POP-510   针对其他费用，优惠项目的code存的是子类型而非父类型
//				if (StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_QT,waybillDiscountVo.getFavorableItemCode())) {
					if(CollectionUtils.isNotEmpty(data)){
									for(OtherChargeVo vo:data){
										//当其他费用面板的code与其他费用子类型一直，才进行更新操作
										//修复pop-474更改单-起草更改单，超远派送费会自动默认修改成6元调用规则引擎里面的折扣值
										//修复POP-492开单-FOSS开单手动添加的其他费用，计算总运费之后费用明细会改变
											if(StringUtil.equalsIgnoreCase(vo.getCode(),waybillDiscountVo.getFavorableItemSubTypeCode())){
												if(PriceEntityConstants.PRICING_CODE_RYFJ.equals(vo.getCode())
														   || PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())
														   || PriceEntityConstants.PRICING_CODE_CY.equals(vo.getCode())
														   || PriceEntityConstants.PRICING_CODE_GGF.equals(vo.getCode())
														   || PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())){
															if(StringUtil.isNotEmpty(waybillDiscountVo.getFavorableDiscount())
																	&& new BigDecimal(waybillDiscountVo.getFavorableDiscount()).compareTo(BigDecimal.ONE) < 0){
												 				//POP-521 ：优惠后的金额=优惠金额/优惠折扣-优惠金额，而不是“优惠后的金额=优惠金额/（1-优惠折扣）-优惠金额”
//																BigDecimal discountAmount
//																= (new BigDecimal(waybillDiscountVo.getFavorableAmount())).divide(new BigDecimal(waybillDiscountVo.getFavorableDiscount())).subtract(new BigDecimal(waybillDiscountVo.getFavorableAmount()));
																//优惠金额
																//POP-545 POP-543前台显示的其他费用，用折前金额*折扣率
																BigDecimal discountAmount = new BigDecimal(waybillDiscountVo.getFavorableBeforeAmount()).multiply(new BigDecimal(waybillDiscountVo.getFavorableDiscount()));
																vo.setMoney(discountAmount.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
															}
														}
											   }
								      }
					}
//				}
			}
		}
		//重新获取其他费用
		CommoForFeeUtils.otherPanelSumFee(data,bean);
		//强制调用其他费用变更处理，否则变更项中无法显示其他费用的变化
		ui.getCompareListener().tableChangedEnforceCall();
		
	}

	/**
	 * 推广活动会对其他费用打折，重新在其他费用面板赋值
	 * @创建时间 2014-5-17 上午10:51:57   
	 * @创建人： WangQianJin
	 */
	private void setterOtherFeeByMakActive(WaybillInfoVo bean){	
		//修复pop-455 更改单-起草更改单，综合信息服务费变大
		if (bean.getActiveInfo() != null
				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())) {
			// 推广活动折扣后的综合信息费
			BigDecimal zhxxDis = null;
			BigDecimal zhxx = Common.getDefaultZhxxFee(bean);
			if (zhxx != null) {
				// 获取折扣后的综合信息费四舍五入取整
				zhxxDis = zhxx.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			// 重新设置其他费用
			if (zhxxDis != null) {
				// 获取其他费用
				JXTable otherTable = ui.incrementPanel.getTblOther();
				WaybillOtherCharge model = (WaybillOtherCharge) otherTable
						.getModel();
				List<OtherChargeVo> data = model.getData();
				if (CollectionUtils.isNotEmpty(data)) {
					boolean flag = false;
					for (OtherChargeVo vo : data) {
						if (PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo
								.getCode())) {
							vo.setMoney(zhxxDis.toString());
							flag = true;
							break;
						}
					}
					if (flag) {
						// 重新获取其他费用
						CommoForFeeUtils.otherPanelSumFee(data, bean);
						// 强制调用其他费用变更处理，否则变更项中无法显示其他费用的变化
						ui.getCompareListener().tableChangedEnforceCall();
					}
				}
			}
		}
	}
	
	/**
	 * 验证转运与返货时费率是否为空
	 * @author WangQianJin
	 * @date 2013-08-31
	 */
	private void validateUnitPrice(WaybillInfoVo bean){
		String rfcType = bean.getRfcType().getValueCode();
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			//如果没有找到转运费率，需要手工添加
			if(bean.isTfrNeedHandWrite()){
				if(bean.getTfrUnitPrice()!=null){
					if(bean.getTfrUnitPrice().doubleValue()<=0){
						ui.getButtonPanel().getBtnSubmit().setEnabled(false);
						//没有找到符合条件的中转费率，请手动设置！
						throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateaction.zzfunitprice.iserror"));						
					}					
				}else{
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateaction.zzfunitprice.iserror"));
				}
			}
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			//如果没有找到返货费率，需要手工添加
			if(bean.isRtnNeedHandWrite()){
				if(bean.getRtnUnitPrice()!=null){
					if(bean.getRtnUnitPrice().doubleValue()<=0){
						ui.getButtonPanel().getBtnSubmit().setEnabled(false);
						//没有找到符合条件的中转费率，请手动设置！
						throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateaction.zzfunitprice.iserror"));	
					}					
				}else{
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateaction.zzfunitprice.iserror"));
				}
			}			
		}
	}
	
	/**
	 * 获取其他费用查询参数
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-22 下午4:03:05
	 */
	private QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillInfoVo bean) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());
		// 产品CODE
		queryDto.setProductCode(bean.getFinalProductCode().getCode());
		//设置计费类型 liyongfei 2015-01-23定价优化新增
		queryDto.setCaculateType(bean.getCaculateType());
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		//liyongfei 定价优化加入新的参数，导致必须传入重量和体积
		//注释掉以前的
//		queryDto.setWeight(BigDecimal.ZERO);// 重量
//		queryDto.setVolume(BigDecimal.ZERO);// 体积
		//加入重量和体积
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		//渠道订单
		queryDto.setChannelCode(bean.getOrderChannel());
		//修复POP-490 更改单-调用行业折扣的运单，起草更改单，燃油附加费不显示折扣金额
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());//客户编码
		//设置营销活动查询条件
		Common.settterActiveParamInfoValueAdd(queryDto, bean);
		queryDto.setBillTime(bean.getBillTime());// 设置开单时间
		queryDto.setValuedtos(bean.getValuedtos());//保费和代收，是否接货（条件折扣）
		//更改单，条件折扣，封装是否接货
		if(bean.getPickupToDoor()!=null&&bean.getPickupToDoor()){//是否上门接货为true，则设置“Y”
			queryDto.setIsReceiveGoods(FossConstants.YES);
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);//否则设置“N”
		}
		//定价体系优化项目POP-489
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
			/**
		 * 封装内部发货查询参数
		 */
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * 
	 * 查询其他费用折扣
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:22:50
	 */
	private void queryOtherChargeData(WaybillInfoVo bean) {
		List<ValueAddDto> list = waybillService
				.queryValueAddPriceList(getQueryOtherChargeParam(bean));
		
		//设置其他费用的折扣优惠
		if(list!=null && list.size()>0){
			for (ValueAddDto valueAddDto : list) {
				JXTable otherTable = ui.incrementPanel.getTblOther();
				WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
				List<OtherChargeVo> data = model.getData();				
				if(CollectionUtils.isNotEmpty(data)){
								for(OtherChargeVo vo : data){
									//修复pop-474更改单-起草更改单，超远派送费会自动默认修改成6元调用规则引擎里面的折扣值
									//修复POP-492开单-FOSS开单手动添加的其他费用，计算总运费之后费用明细会改变
									if(StringUtil.equalsIgnoreCase(valueAddDto.getSubType(), vo.getCode())){
											if(PriceEntityConstants.PRICING_CODE_RYFJ.equals(vo.getCode())
													   || PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())
													   || PriceEntityConstants.PRICING_CODE_CY.equals(vo.getCode())
													   || PriceEntityConstants.PRICING_CODE_GGF.equals(vo.getCode())
													   || PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())){
											           Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), bean);
											}
										}
								     }
				}
			}
		}
	}
	
	/**
	 * 
	 * 获取保价费以及最高保额
	 * 
	 * 限保物品（例如：“陶瓷”、“门窗”等）默认保价1000不可修改,且保价费率可手动调整；（限保物品从限运物品基础资料中获取）；
	 * 	非限保物品的保价费率不可修改；
	 * 实际保险费小于最低保费的按最低保费收取；
	 * 保价费 = 保价申明价值*保价费率，不可修改；
	 * 保价申明价值默认为3000，可以修改；保价声明价值不封顶；
	 * 精准（长途）、普货（长途）、偏线，最低一票8元；普货（短途）、精准（短途）最低一票6元；空运最低一票10元；
	 * 	所有运输方式保价超过最低均按0.4%收取（数据读取自保价设置基础资料）；长短途数据读取计价基础资料；
	 * 实际保价费小于最低保费的按最低保费收取；
	 * 保价费率首先是配置的标准费率。当有区域保价费率时，以区域保价费率为准。当客户为合同客户时，则以合同签订为准。
	 * 	所有的保价费率以读取的为准，不可修改。限保物品的保价费率同样不可修改
	 * 行政组织业务属性-营业部信息中增加增值服务（返回签单、货到付款、代收货款）选项，开单是否可以进行返回签单、
	 * 	货到付款、代收货款需要根据到达部门属性是否可以（返回签单、货到付款、代收货款）来决定
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午01:51:03
	 */
	private void getInsurance(WaybillInfoVo bean) {
		ValueAddDto dto = new ValueAddDto();
		/**
		 *  查询保价费:根据需求，如果查找不到收货日期的价格，需要查找当前日期的价格
		 */
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getInsuranceParam(bean,false));//false表示按收货日期进行查找价格

		if(list==null ||list.isEmpty())
		{
			list = waybillService.queryValueAddPriceList(getInsuranceParam(bean,true));//true表示按当前日期进行查找价格
		}
		if (list != null && !list.isEmpty()) {
				dto = list.get(0);
				setInsurance(bean, dto);
				// 设置折扣优惠
				Common.setFavorableDiscount(dto.getDiscountPrograms(), bean);
		} else {
			setInsurance(bean, null);
		}
	
	}
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getInsuranceParam(WaybillInfoVo bean,boolean isGetCurrentPrice) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getFinalProductCode().getCode());// 产品CODE
		//设置计费类型 liyongfei 2015-01-23定价优化新增
		queryDto.setCaculateType(bean.getCaculateType());
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		
		
		//精准包裹特有逻辑
		if(StringUtil.equals(bean.getFinalProductCode().getCode(),ProductEntityConstants.PRICING_PRODUCT_PCP)){
			if(bean.getInsuranceAmount().compareTo(new BigDecimal(NumberConstants.NUMBER_2000))>0){
				queryDto.setOriginnalCost(bean.getInsuranceAmount().subtract(new BigDecimal(NumberConstants.NUMBER_2000)));
			}else{
				return null;
			}
		}else {
			queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
		}
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setFeeRate(bean.getInsuranceRate().divide(new BigDecimal(NumberConstants.NUMBER_1000)));//保价费率
		//设置营销活动查询条件
		Common.settterActiveParamInfoValueAdd(queryDto, bean);
		queryDto.setBillTime(bean.getBillTime());// 设置开单时间
		queryDto.setValuedtos(bean.getValuedtos());//设置代收和保价，是否接货（条件折扣）
		//更改单，条件折扣，封装是否接货
		if(bean.getPickupToDoor()!=null&&bean.getPickupToDoor()){//是否上门接货为true，则设置“Y”
			queryDto.setIsReceiveGoods(FossConstants.YES);
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);//否则设置“N”
		}
		//定价体系优化项目POP-489
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
		/**
		 * FOSS20150818）RFOSS2015052602-梯度保价
		 * 添加取消计算保价折扣的标识符
		 * @author foss-206860
		 * */
		queryDto.setCalDiscount(bean.isCalDiscount());
		queryDto.setMinFeeRate(bean.getMinFeeRate());//最低费率
		queryDto.setMaxFeeRate(bean.getMaxFeeRate());
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * 
	 * 设置保险费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 上午10:05:31
	 */
	private void setInsurance(WaybillPanelVo bean, ValueAddDto dto) {
		if (dto == null) {
			// 保险声明值最大值
			bean.setMaxInsuranceAmount(BigDecimal.ZERO);
			// 保险费率
			bean.setInsuranceRate(BigDecimal.ZERO);
			// 保险手续费
			bean.setInsuranceFee(BigDecimal.ZERO);
			// 保险费ID
			bean.setInsuranceId("");
			// 保险费CODE
			bean.setInsuranceCode("");
			
			WaybillInfoVo waybillInfoVo = (WaybillInfoVo)bean;
			//精准包裹特有逻辑
			if (!(StringUtil.equals(waybillInfoVo.getFinalProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)
					&& bean.getInsuranceAmount().compareTo(new BigDecimal(NumberConstants.NUMBER_2000)) <= 0))
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullInsuranceFee"));
		} else {
			// 保险声明值最大值
			bean.setMaxInsuranceAmount(dto.getMaxFee());
			// 保险费率
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = dto.getFeeRate();
			feeRate = feeRate.multiply(permillage);
			bean.setInsuranceRate(feeRate);
			//修复pop-333
			Double caculateFee=dto.getCaculateFee().doubleValue() >= dto.getMinFee().doubleValue() 
					? dto.getCaculateFee().doubleValue():dto.getMinFee().doubleValue();
			BigDecimal insuranceFee = new BigDecimal(caculateFee).setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			//insuranceFee=insuranceFee.divide(permillage);
			if(BigDecimal.ZERO.compareTo(bean.getInsuranceAmount())==0){
				// 保险手续费
				bean.setInsuranceFee(BigDecimal.ZERO);
			}else{
				// 保险手续费
				bean.setInsuranceFee(insuranceFee);
			}
			// 保险费ID
			bean.setInsuranceId(dto.getId());
			// 保险费CODE
			bean.setInsuranceCode(dto.getPriceEntityCode());
		}

	}
	
	
	
	/**
	 * 
	 * 获取代收货款费率
	 * 
	 *  * 1、如果不是CRM客户或者派送网点基础资料中代收货款属性为”否”，代收货款设置为0且不可编辑；
	 * 2、如果是CRM客户、对应派送网点基础资料中代收货款属性为”是”且CRM中有账户信息，代收货款可编辑，
	 * 		且可以选择所有的退款类型，若CRM中若无账户信息，则退款类型只能选择审核退
	 * 3、开单时系统默认代收货款为空；
	 * 4、代收货款栏默认为空，如果没有代收货款，则要求输入0；否则，进行提示：“请确认该单没有代收货款，
	 * 		如无，请输入数字0”；当代收货款大于0时，输入后，对于选择的退款类型，有如下限制：
	 * 三日退：在收到客户代收货款后第三天给客户打款。
	 * 默认退款类型为三日退；
	 * 代收10000元以下费率0.5%， 10000元以上费率0.4%；最低10元/票，最高100元/票；有部分城市三日退费率为0.
	 * 		（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
	 * 审核退：收到客户代收货款，出发部门审核后，给客户打款。
	 * 代收10000元以下费率0.5%，10000元以上费率0.4%；最低10元/票，最高100元/票（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
	 * 选择审核退时，客户收款信息体现在开单界面，若无账号时可以提交运单后再走账号修改流程进行补充。
	 * 即日退：在收到客户代收货款后24小时到账。
	 * 代收手续费率1%，最低20元/票，最高200元/票；有部分城市特殊（通过基础资料“代收货款费率”及“特殊城市与部门代收货款费率”实现）。
	 * 必须先录入客户收款银行信息，提交时，银行信息不能为空；
	 * 只支持4个银行：农行、工行、建行、招行；否则，给予提示信息；
	 * 5、限制代收货款金额不能小于10元，可以等于10元；但可以为0；该数字“10”可由基础资料配置；
	 * 6、网上订单导入开单时，代收货款金额读取网上订单的代收货款金额，有数据时不可对代收货款进行修改，只可起草出发更改进行修改；
	 * 		若网上代收货款为0 ，系统可支持修改代收货款金额；
	 * 7、默认的代收费率由基础资料配置；
	 * 8、保价费和代收货款费用无法编辑更改，只能更改保险声明价值和代收货款金额；	 * 
	 * 9、代收退款收款人姓名只能为发货客户绑定的收款人姓名和帐号，且只能选择，不能修改；
	 * 当退款人姓名和帐号唯一时，直接显示；（数据读取CRM客户信息资料（退款帐户信息））
	 * 10、CRM客户信息资料的要先在CRM中录入客户退款帐户信息，且第一次在我司办理代收货款业务时，退款类型只能为审核退；
	 * 11、同一客户多个银行信息的显示问题：当有两个或以上账号时，弹出账号信息（包括开户银行、收款人、账号、备注），
	 * 选中其中的一条银行信息记录后，账号和收款人信息显示在开单界面对应的位置；
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午02:23:42
	 */
	private void getCod(WaybillInfoVo bean) { 
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0 && bean.getRefundType() != null) {
			ValueAddDto dto = new ValueAddDto();
			// 查询代收货款手续费
			List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryParamForReturn(bean, PriceEntityConstants.PRICING_CODE_HK, bean.getCodAmount(), bean.getRefundType().getValueCode(),false));//false 表示按billtime去拿价格

			
			if(list==null||list.isEmpty())
			{
			// 查询代收货款手续费
			list = waybillService.queryValueAddPriceList(getQueryParamForReturn(bean, PriceEntityConstants.PRICING_CODE_HK, bean.getCodAmount(), bean.getRefundType().getValueCode(),true));//true 表示按当前时间去拿价格

			}
			if (list != null && !list.isEmpty()) {
					dto = list.get(0);
					
					/*//来自官网的订单不能能设置，更改单不需要限制此规则
					if(WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean.getWaybillDto().getWaybillEntity().getOrderChannel())){
						ui.incrementPanel.getTxtInsuranceValue().setEnabled(false); // 保价申明价值
						ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false); // 代收货款
					}else{
						ui.incrementPanel.getTxtInsuranceValue().setEnabled(true); // 保价申明价值
						ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true); // 代收货款
					}*/
					setCod(bean, dto);
					// 设置折扣优惠
					Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
		
			} else {
				setCod(bean, null);
				throw new WaybillValidateException(bean.getRefundType().getValueName() + i18n.get("foss.gui.creating.calculateAction.exception.nullCodAmountFee"));
			}
		}else{
			/**
			 * FOSS更改代收货款计费逻辑优化    需求编码：    零担
			 * 原取消代收货款，如果出了开单部门，恢复开单时的代收手续费 现更改为代收手续费为0，费率不变
			 * 当代收货款更为0时，代收手续费 为0
			 * @author 376290
			 * 日期：2017-02-17
			 */
			if(!BZPartnersJudge.IS_PARTENER && !WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())){
				// 代收货款费率
				bean.setCodRate(ui.getOriginWaybill().getCodRate());
				// 代收货款金额
				bean.setCodFee(zero);
				// 代收货款编码
				bean.setCodCode(ui.getOriginWaybill().getCodCode());
				// 代收货款ID
				bean.setCodId(ui.getOriginWaybill().getCodId());
				
			}else{//取消代收货款，如果出了开单部门，恢复开单时的代收手续费        合伙人
				// 代收货款费率
				bean.setCodRate(ui.getOriginWaybill().getCodRate());
				// 代收货款金额
				bean.setCodFee(ui.getOriginWaybill().getCodFee());
				// 代收货款编码
				bean.setCodCode(ui.getOriginWaybill().getCodCode());
				// 代收货款ID
				bean.setCodId(ui.getOriginWaybill().getCodId());
			}
		}
	}

	/**
	 * 
	 * 设置代收货款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 上午10:12:34
	 */
	private void setCod(WaybillInfoVo bean, ValueAddDto dto) {
		if (dto == null) {
			// 代收货款费率
			bean.setCodRate(BigDecimal.ZERO);
			// 代收货款金额
			bean.setCodFee(BigDecimal.ZERO);
			// 代收货款编码
			bean.setCodCode("");
			// 代收货款ID
			bean.setCodId("");
		} else {
			// 代收货款费率
			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal codRate = dto.getActualFeeRate();
			codRate = codRate.multiply(permillage);
			bean.setCodRate(codRate);

			BigDecimal codFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			// 代收货款金额
			bean.setCodFee(codFee);

			// 代收货款编码
			bean.setCodCode(dto.getPriceEntityCode());
			// 代收货款ID
			bean.setCodId(dto.getId());
		}
	}


	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam(WaybillInfoVo bean, String valueAddType, BigDecimal cost, String subType,boolean isGetCurrentPrice) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getFinalProductCode().getCode());// 产品CODE
		//设置计费类型 liyongfei 2015-01-23定价优化新增
		queryDto.setCaculateType(bean.getCaculateType());
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(valueAddType);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		//设置营销活动查询条件
		Common.settterActiveParamInfoValueAdd(queryDto, bean);
		queryDto.setBillTime(bean.getBillTime());// 设置开单时间
		queryDto.setValuedtos(bean.getValuedtos());//保费和代收，是否接货（条件折扣）
		//更改单，条件折扣，封装是否接货
		if(bean.getPickupToDoor()!=null&&bean.getPickupToDoor()){//是否上门接货为true，则设置“Y”
			queryDto.setIsReceiveGoods(FossConstants.YES);
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);//否则设置“N”
		}
		//定价体系优化项目POP-457
		queryDto.setCaculateType(bean.getCaculateType());
		//定价体系优化项目POP-489
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
			/**
		 * 内部发货查询参数
		 */
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author WangQianJin
	 * @date 2013-07-18
	 */
	private QueryBillCacilateValueAddDto getQueryParamForReturn(WaybillInfoVo bean, String valueAddType, BigDecimal cost, String subType,boolean isGetCurrentPrice) {
		QueryBillCacilateValueAddDto queryDto = getQueryParam(bean,valueAddType,cost,subType,isGetCurrentPrice);
		/**
		 * 如果是转运或返货，则出发部门和到达部门设置为运输信息的出发和到达，目的是为了获取开单时增值服务的费率和费用
		 */
		if(bean.getRfcType()!=null){
			if(WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode()) || WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode())){
				queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
				queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
				queryDto.setValuedtos(bean.getValuedtos());//报价和代收条件折扣
				//更改单，条件折扣，封装是否接货
				if(bean.getPickupToDoor()!=null&&bean.getPickupToDoor()){//是否上门接货为true，则设置“Y”
					queryDto.setIsReceiveGoods(FossConstants.YES);
				}else{
					queryDto.setIsReceiveGoods(FossConstants.NO);//否则设置“N”
				}
			}
		}
		return queryDto;
	}
	
	/**
	 * 
	 * 计算送货费
	 * 
	 * 1. 送货费默认不可改小，但可改大。除了月结客户外，只能对系统计算出的送货费取值进行上调，不能下调。
	 * 当送货费取值大于最高送货费（基础资料配置）时，送货费自动跳改为最高送货费值，但用户可以上调送货费；			
	 * 2. 通过送货费基础资料来实现：			
	 * 2.1. 若提货方式为送货进仓，开单送货费直接读取送货进仓送货费基础资料，取值终止；			
	 * 2.2. 若提货方式为送货上门，则开单的送货费取值顺序：特殊区域送货费优于全国标准送货费；
	 * （即：先到特殊区域送货费基础资料中取值，若取到值，取值终止；否则，再到全国标准送货费基础资料取值，取值终止。）			
	 * 2.3. 特殊区域和全国标准送货费取值规则：送货费=货物重量*送货费率； 			
	 * 2.3.1 先判断开单体积在哪个体积区间，筛选出符合条件的所有记录，
	 * 再判断开单重量在已被体积筛选出来的记录中的哪个重量区间，来确定是哪一条记录。
	 * 然后再根据费率计算，计算出来的值与该条的最低送货费进行比较，若小于最低送货费时，就取最低送货费，取值终止；
	 * 若大于最低送货费且小于最高送货费时，就取计算出来的值，取值终止；若大于最高送货费时，就取最高送货费，取值终止。			
	 * 2.3.2 标淮派送范围收取送货费标准：			
	 * 货物重量	标准		
	 * 0-300KG	55元/票		
	 * 301-500KG	0.2元/KG		
	 * 501KG或2.5立方米以上	100元/票，不封顶		
	 * 2.3.3 当送货费取值小于最高送货费时，开单送货费不可以更改；当送货费取值高于最高送货费时，
	 * 开单送货费可手动更改，如果手动更改的值小于最高送货费时，系统弹出提示框：“该票送货费不得低于【最高送货费】”			
	 * 2.3.4 仅使用于汽运专线，对“空运”、“偏线”以及“中转下线” 的不受以上收费的限制。			
	 * 2.3.5 “月结”客户的送货费收费按以上计算出默认值，但可以修改。（送货费不受限制。可向上修改也可以向下修改，最小为0）			
	 * 2.3.6 除月结客户的属性外其它所有情况的送货费不可向下修改，只能向上修改			
	 * 2.3.7 最高送货费做基础资料配置；			
	 * 3. 非标准派送范围加收操作费标准：			
	 * 3.1 超远加收送货费标准：			
	 * 距离（公里）	30-50	50-100	100-150
	 * 加收金额（元）	50	100	150
	 * 3.1.1 距离为客户所在区域与公司最近网点的距离（距离以ERP谷歌地图测量的导航距离为准）；			
	 * 3.1.2 客户所在地30公里范围内如果有公司的营业网点，无论是否做派送，该区域均不能收取超远加收送货费；			
	 * 3.1.3 非标准派送的费用添加无上限			
	 * 3.2 特殊区域（进仓）：			
	 * 3.2.1 特殊区域类型：大型超市和商场、大型工厂（需提供进仓编号）；			
	 * 3.2.2 收费标准：进仓费实报实销，并加收150元操作费；			
	 * 4. 区域送货费限制：			
	 * 4.1 当开单提货网点的所在城市或区域为“XX”、提货方式为：送货”XX”(XX待定，但前提是：提货方式必须为送货)时，
	 * 送货费固定为XX元，且不可修改；财务成本提取为XX元。（该类型城市或区域、送货费固定标准、成本提取标准可配置）			
	 * 4.2 当开单提货网点为XX营业部时，开单送货费为XX元，内部成本提取为XX元（目前该方案营业部：深圳华强苏发大厦营业部、深圳华强钟表市场营业部）。
	 * （该类营业部类型、送货费固定标准、成本提取标准可配置）			
	 * 4.3 内部带货、空运、偏线及中转下线不受上述需求的限制。			
	 * 5. 限制大件大票货到达“XX部门”或“XX区域”或其它限制类型区域：			
	 * 5.1 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域，判断单件重量是否超过XXKG，或体积是否超过X立方，
	 * 是的话提示：“单件超过XKG或单票超过X立方，请开到XX派送部或其它内容”。（单件重量＝重量/件数）。【即开单限制：可通过到达部门限制基础资料实现】			
	 * 5.2 当开单提货网点为“XX部门”或“XX区域”或其它限制类型区域时，其他费用中的“送货上楼费”屏蔽或显示但不可选择；			
	 * 5.3 若“XX区域”或其它限制类型区域再开派送部，适用以上需求；			
	 * 5.4 空运、偏线及中转下线不受上述需求的限制；			
	 * 5.5 内部带货受上述需求的限制；			
	 * 5.6  “XX部门”或“XX区域”或其它限制类型区域及判断规则由基础资料配置；			
	 * 
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-17 下午04:00:59
	 */
	private void getDeliveryFee(WaybillInfoVo bean) {

		//定义标志位，得知是否包含送货，如果包含就去掉相应费用
		boolean isCentralizeDeliveryResult = false;
		if (StringUtil.equals(bean.getFinalProductCode().getCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)) {
			List<ProductPriceDto> productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, null, Common.GBJ));
			if (CollectionUtils.isNotEmpty(productPrice)) {
				isCentralizeDeliveryResult =StringUtil.equals(productPrice.get(0).getCentralizeDelivery(), FossConstants.YES);
			}
		}
		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return;
		}

		List<ValueAddDto> list = null;

		//提货方式编码
		if(bean.getFinalReceiveMethod()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.changing.transportInfoPanel.pickMode.label.isNull"));
		}
		
		String code = bean.getFinalReceiveMethod().getValueCode();
		
		if(WaybillRfcConstants.INSIDE_REQUIRE.equals(bean.getRfcSource()))
		{
			if(ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getRowCount() > 0)
			{
				if(bean.getRecordReceiveMethod() != null && bean.getRecordReceiveMethod().getValueCode() != null)
				{
					code = bean.getRecordReceiveMethod().getValueCode();
				}
			}
		}
		
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {

			if(!isCentralizeDeliveryResult) {
				list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null, false));//false表示按照业务日期去找价格
			}
			if((list==null||list.isEmpty()) && !isCentralizeDeliveryResult)
			{
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,true));//true表示按照当前日期去找价格
			}
			
			if (list != null && !list.isEmpty()) {
				
				ValueAddDto dto = list.get(0);
				/**
				 * 如果是汽运送货费，那么需要判断是否超过最高派送费，如果超过，赋值为派送费最大值
				 */
				if (WaybillConstants.DELIVER_NOUP.equals(code)) {

					BigDecimal maxDeliveFee = null;//设置最大派送费
					
					ConfigurationParamsEntity maxDeliverFeeForConfig = getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_DELIVER_NOUP_MAX_FEE);// 最大派送费
					
					if (maxDeliverFeeForConfig != null
							&& StringUtils.isNotEmpty(maxDeliverFeeForConfig
									.getConfValue())) {
						maxDeliveFee = new BigDecimal(
								maxDeliverFeeForConfig.getConfValue());
					}
					
					BigDecimal caculateFee = dto
							.getCaculateFee();
					if (maxDeliveFee!=null && caculateFee.compareTo(maxDeliveFee) > 0) {
						dto.setCaculateFee(maxDeliveFee);

					}
				}
				
				
				
			}
			
			
			
			// 设置送货费 
			setDeliverFee(list, bean, true , false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {
			if(!isCentralizeDeliveryResult) {
				list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null, false));//false 用业务日期
			}
			if((list==null||list.isEmpty()) && !isCentralizeDeliveryResult)
			{
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,true));//true 用当前日期
			}
			// 设置送货费
			setDeliverFee(list, bean, true , false);
			if(!isCentralizeDeliveryResult) {
				list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null, false));//false 用业务日期
			}
			if((list==null||list.isEmpty()) && !isCentralizeDeliveryResult)
			{
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null,true));//true 用当前日期
			}
			// 设置上楼费
			setDeliverFee(list, bean, false , false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			if(!isCentralizeDeliveryResult) {
				list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null, false));
			}
			if((list==null||list.isEmpty()) && !isCentralizeDeliveryResult){
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,true));
			}
			// 设置送货费
			setDeliverFee(list, bean, true , false);			
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC,false));
			if(list==null||list.isEmpty()){
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC,true));
			}
			// 设置进仓费
			setDeliverFee(list, bean, false , true);
			// 超远派送费
			veryFarDeliveryFee(bean);
		}
		
		/**
		 * 计算大件上楼费
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-19下午16:37
		 */
		else if (WaybillConstants.LARGE_DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(code)) {
			if(!isCentralizeDeliveryResult) {
				list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null, false));//false 用业务日期
			}
			if((list==null||list.isEmpty()) && !isCentralizeDeliveryResult)
			{
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,true));//true 用当前日期
			}
			// 设置送货费
			setDeliverFee(list, bean, true , false);
			if(!isCentralizeDeliveryResult) {
				list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_DJSL, null, null, false));//false 用业务日期
			}
			if((list==null||list.isEmpty()) && !isCentralizeDeliveryResult)
			{
				list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_DJSL, null, null,true));//true 用当前日期
			}
			/**
			 * @需求：大件上楼优化
			 * @功能：当大件上楼计算费用为null时，说明没有查询到相应的大件上楼费用，需要维护相关资料
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-04-16
			 */
			//foss 343617 zhaoyiqing 20160921 优化更改单，精准包裹没有大件上楼的费用
			if(list==null && !isCentralizeDeliveryResult){
				throw new WaybillValidateException("大件上楼资料未维护，请维护相关资料！");
			}
			// 设置上楼费
			setDeliverFee(list, bean, false , false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		}
		//变更类型
		String rfcType = bean.getRfcType().getValueCode();
		if (WaybillRfcConstants.TRANSFER.equals(rfcType) 
				||
			WaybillRfcConstants.RETURN.equals(rfcType)
				|| WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType) 
				|| WaybillRfcConstants.INSIDE_CHANGE.equals(rfcType)) {
			//String codes = bean.getRtnReceiveMethod().getValueCode();
			// 判断是否自提
			if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
					|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code)
					|| WaybillConstants.INNER_PICKUP.equals(code)) {
				bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);
			} 
		} 
		
		//根据手工输入与计算的送货费获取最后的送货费
		//getLastDeliverGoodsFee(bean);
	}
	
	
	/**
	 * 注释掉未用到的方法
	 * 2014-03-05 026123
	 */
//	/**
//	 * 根据手工输入与计算的送货费获取最后的送货费
//	 * @author WangQianJin
//	 * @date 2013-8-7 下午11:04:37
//	 */
//	private void getLastDeliverGoodsFee(WaybillInfoVo bean){
//		//如果原来的送货费大于计算出的送货费，则按照原来的送货费
//		if(bean.getDeliveryGoodsFee()!=null && bean.getHandDeliveryFee()!=null){
//			if(bean.getHandDeliveryFee().doubleValue()>bean.getDeliveryGoodsFee().doubleValue()){
//				bean.setDeliveryGoodsFee(bean.getHandDeliveryFee());
//				//画布-送货费
//				bean.setDeliveryGoodsFeeCanvas(bean.getHandDeliveryFee().toString());
//				//计算的送货费
//				bean.setCalculateDeliveryGoodsFee(bean.getHandDeliveryFee());			
//			}else{
//				bean.setHandDeliveryFee(bean.getDeliveryGoodsFee());
//			}			
//		}
//	}
	
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
		return BaseDataServiceFactory.getBaseDataService().queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);

	}
	/**
	 * 
	 * 设置送货费、送货进仓费、送货上楼等全部送货费
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午04:52:25
	 */
	private void setDeliverFee(List<ValueAddDto> list,WaybillInfoVo bean,Boolean flag , Boolean isDeliverStorge)
	{
		if (list != null && !list.isEmpty()) {
			
			ValueAddDto dto = list.get(0);
			DeliverChargeEntity deliver = new DeliverChargeEntity();
			//是否激活
			deliver.setActive(FossConstants.ACTIVE);
		
			BigDecimal deliveryGoodsFee = dto.getCaculateFee();
			if(deliveryGoodsFee == null)
			{
				deliveryGoodsFee = BigDecimal.ZERO;
			}else
			{	
				deliveryGoodsFee = deliveryGoodsFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			//金额
			deliver.setAmount(deliveryGoodsFee);
			//币种
			deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			//费用ID
			deliver.setId(dto.getId());
			//运单号
			deliver.setWaybillNo(bean.getWaybillNo());
			//提货方式编码
			if (isDeliverStorge){// 送货进仓
				// 费用编码
				deliver.setCode(dto.getSubType());
				//费用名称
				deliver.setName(dto.getSubTypeName());
			}else{
				//费用编码
				deliver.setCode(dto.getPriceEntityCode());
				//费用名称
				deliver.setName(dto.getPriceEntityName());
			}
			//送货费合计
			BigDecimal chargeTotal = BigDecimal.ZERO;
			//送货费合计 = 送货费+上楼费/进仓费/超远派送费/大件上楼费
			chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
			//bean.setHandDeliveryFee(chargeTotal);
			bean.setDeliveryGoodsFee(chargeTotal);
			//画布-送货费
			bean.setDeliveryGoodsFeeCanvas(chargeTotal.toString());
			//计算的送货费
			bean.setCalculateDeliveryGoodsFee(chargeTotal);
			
			//获取派送费集合
			List<DeliverChargeEntity> delivetList = bean.getDeliverList();
			if(delivetList == null)
			{
				delivetList = new ArrayList<DeliverChargeEntity>();
			}
			//将新的派送费添加到派送费集合
			delivetList.add(deliver);
			//将派送费集合进行更新
			bean.setDeliverList(delivetList);
			//此标识用来标记是否送货费
			if(flag)
			{
				bean.setMaxDeliveryGoodsFee(dto.getMaxFee());
			}
			//判断优惠项目CODE为超远派送时，不设置折扣优惠
			if(!PriceEntityConstants.PRICING_CODE_CY.equals(dto.getSubType())){
				// 设置折扣优惠
				Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
			}
		}
	}
	
	/**
	 * 
	 * 超远派送费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 上午11:16:36
	 */
	private void veryFarDeliveryFee(WaybillInfoVo bean) {
		if (bean.getKilometer() != null) {
			List<ValueAddDto> list = waybillService.queryValueAddPriceList(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null,false));//获取业务日期价格
			if(list==null||list.isEmpty()){
			list = waybillService.queryValueAddPriceList(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null,true));//获取当前价格
			}
			//设置进仓费
			setDeliverFee(list,bean,false,false);
		}
	}
	
	/**
	 * 
	 * 获取超远派送费查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午04:54:59
	 * @param bean
	 * @param valueAddType
	 * @param cost
	 * @param subType
	 * @return
	 */
	private QueryBillCacilateValueAddDto getVeryFarQueryParam(WaybillInfoVo bean, String valueAddType, BigDecimal cost, String subType,boolean isGetCurrentPrice) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getFinalProductCode().getCode());// 产品CODE
		//设置计费类型 liyongfei 2015-01-23定价优化新增
		queryDto.setCaculateType(bean.getCaculateType());
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(valueAddType);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		// 公里数
		queryDto.setKilom(bean.getKilometer());
		queryDto.setBillTime(bean.getBillTime());// 设置开单时间
		queryDto.setValuedtos(bean.getValuedtos());//设置代收和保价，是否接货（条件折扣）
		//更改单，条件折扣，封装是否接货
		if(bean.getPickupToDoor()!=null&&bean.getPickupToDoor()){//是否上门接货为true，则设置“Y”
			queryDto.setIsReceiveGoods(FossConstants.YES);
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);//否则设置“N”
		}
		//定价体系优化项目POP-489
		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
			/**
		 * 内部发货查询参数
		 */
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	
	/**
	 * 
	 * 设置计费类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-1 上午09:41:19
	 */
	private void setBillingWay(String billingWay, WaybillPanelVo bean) {
		DefaultComboBoxModel combBillingType = (DefaultComboBoxModel) ui.getCombBillingType();

		int size = combBillingType.getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) combBillingType.getElementAt(i);
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_WEIGHT.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}

			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(billingWay)) {
				if (WaybillConstants.BILLINGWAY_VOLUME.equals(vo.getValueCode())) {
					bean.setBillingType(vo);
				}
			}
		}
	}
	

	/**
	 * 
	 * 计算装卸费
	 * 
	 * 1、装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
	 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
	 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
	 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
	 * 2、如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，为系统默认的公布价。
	 * 开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，开单显示运费（C）=Q*Z=C0+M ；
	 * 3、当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），需先清空装卸费为零，然后再修改数据。
	 * 如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
	 * 4、当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
	 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
	 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。（其中Q1是装卸费M1时的费率,Z为重量/体积）；
	 * 5、系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。（只限制配载类型为专线的，包括月结）；
	 * 6、对于显示费率不等于显示费率乘以重量的问题，要求如下：
	 * 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
	 * 	且显示运费等于该显示费率*重量；
	 * 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
	 * 	则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
	 * 7、只要含与不含装卸费两者有交叉的，均以不含为准；
	 * 8、装卸费特殊部门表：（建议：做为可配置的基础数据表）
	 * 
	 * 特殊部门												
	 * 华东事业部		上海虹桥营业部、上海闵行区营业部、上海嘉定区南翔营业部										
	 * 												
	 * 华北事业部		"北京丰台区新发地营业部、北京丰台区大红门营业部、北京顺义区营业部、北京中关村上地营业部、石家庄栾城营业部、河北廊坊营业部、天津塘沽区营业部、天津东丽区营业部、天津北辰区营业部、天津武清区营业部、沈阳东陵区南塔营业部、沈阳于洪区张士营业部、辽宁鞍山营业部、青岛即墨营业部、青岛李沧区营业部
	 * "										
	 * 												
	 * 												
	 * 												
	 * 深圳事业部		深圳八卦岭营业部、深圳华强桑达营业部、深圳宝安机场营业部、深圳宝安区塘尾营业部、深圳南山营业部、惠州市惠阳营业部、惠州惠城区河南岸营业部、东莞虎门镇营业部、广东汕头营业部										
	 * 												
	 * 												
	 * 												
	 * 广州事业部		广州白云区新市营业部、广州花都区花山营业部、广州花都区新华营业部、广州白云区嘉忠营业部、广州白云区三元里岗头大街营业部、中山小榄营业部、江门开平营业部、广西南宁安吉营业部、佛山张槎营业部、佛山市营业部										
	 * 												
	 * 												
	 * 												
	 * 西部大区		成都武候区双星大道营业部、重庆经开区营业部、云南昆明营业部、四川泸州营业部										
	 * 												
	 * 
	 * 特殊部门												
	 * 序号	部门	事业部										
	 * 1	上海虹桥营业部	华东事业部										
	 * 2	上海闵行区营业部	华东事业部										
	 * 3	上海嘉定区南翔营业部	华东事业部										
	 * 4	北京丰台区新发地营业部	华北事业部										
	 * 5	北京丰台区大红门营业部	华北事业部										
	 * 6	北京顺义区营业部	华北事业部										
	 * 7	北京中关村上地营业部	华北事业部										
	 * 8	石家庄栾城营业部	华北事业部										
	 * 9	河北廊坊营业部	华北事业部										
	 * 10	天津塘沽区营业部	华北事业部										
	 * 11	天津东丽区营业部	华北事业部										
	 * 12	天津北辰区营业部	华北事业部										
	 * 13	天津武清区营业部	华北事业部										
	 * 14	沈阳东陵区南塔营业部	华北事业部										
	 * 15	沈阳于洪区张士营业部	华北事业部										
	 * 16	辽宁鞍山营业部	华北事业部										
	 * 17	青岛即墨营业部	华北事业部										
	 * 18	青岛李沧区营业部	华北事业部										
	 * 19	深圳八卦岭营业部	深圳事业部										
	 * 20	深圳华强桑达营业部	深圳事业部										
	 * 21	深圳宝安机场营业部	深圳事业部										
	 * 22	深圳宝安区塘尾营业部	深圳事业部										
	 * 23	深圳南山营业部	深圳事业部										
	 * 24	惠州市惠阳营业部	深圳事业部										
	 * 25	惠州惠城区河南岸营业部	深圳事业部										
	 * 26	东莞虎门镇营业部	深圳事业部										
	 * 27	广东汕头营业部	深圳事业部										
	 * 28	广州白云区新市营业部	广州事业部										
	 * 29	广州花都区花山营业部	广州事业部										
	 * 30	广州花都区新华营业部	广州事业部										
	 * 31	广州白云区嘉忠营业部	广州事业部										
	 * 32	广州白云区三元里岗头大街营业部	广州事业部										
	 * 33	中山小榄营业部	广州事业部										
	 * 34	江门开平营业部	广州事业部										
	 * 35	广西南宁安吉营业部	广州事业部										
	 * 36	佛山张槎营业部	广州事业部										
	 * 37	佛山市营业部	广州事业部										
	 * 38	成都武候区双星大道营业部	华中事业部										
	 * 39	重庆经开区营业部	华中事业部										
	 * 40	云南昆明营业部	华中事业部										
	 * 41	四川泸州营业部	华中事业部										
	 * 
	 * 9、2012年12月1日开业的部门不能开装卸费
	 * 10、如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * 11、是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
	 * 12、装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
	 * 
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午09:18:11
	 */
	private void calculateServiceFee(WaybillInfoVo bean) {
		// 获取装卸费，当装卸费大于0的时候，校验更改单部门是否有开装卸费的权限-update by taodongguo-354805-2016-12-8 15:14:37
		if(null != bean.getServiceFee() && bean.getServiceFee().compareTo(BigDecimal.ZERO) == 1){
			// 设置是否允许修改装卸费
			boolean serviceChargeEnabled = setServiceChargeState(bean);
			// 装卸费无效
			if (!serviceChargeEnabled) {
				return ;
			}
			validataServiceFee(bean);
		}
		
		//zxy 20130909 start 修改：当整车运单时，把装卸费计算到开单报价中，以前是不计算的。   
		if(!bean.getIsWholeVehicle()){
			/**
			 * 如果计算了公布价，才将装卸费添加到公布价中，如果没计算公布价，则说明公布价中已包含装卸费，无需再次累加
			 */
			if(iscalServiceFee(bean)){
				// 获取装卸费
				BigDecimal serviceFee = bean.getServiceFee();
				// 获取运费
				BigDecimal transportFee = bean.getTransportFee();
				// 获取费率
				BigDecimal unitPrice = bean.getUnitPrice();
				// 重量或体积
				BigDecimal weightOrVolume = getWeightOrVolume(bean);
				// 最低一票，没有的话默认为0
				BigDecimal minFee = bean.getMinTransportFee() == null ? BigDecimal.ZERO : bean.getMinTransportFee();
				// 折扣，没有的话默认为1
				BigDecimal discount = bean.getDiscount() == null ? new BigDecimal(1) : bean.getDiscount();
				
				/*
				 * 高于最低一票的情况，按照（费率 = （运费+装卸费）/ 重量或体积）公式重新计算费率
				 * 否则使用原来费率
				 * 运费 = 原运费 + 装卸费
				 * 
				 */
				if (serviceFee != null && serviceFee.longValue() != 0) {
					if(transportFee.compareTo(minFee.multiply(discount)) > 0){
						// 费率 = 最新运费（运费+装卸费）/ 重量或体积
						unitPrice = transportFee.add(serviceFee)
								.divide(weightOrVolume, 2, BigDecimal.ROUND_HALF_UP);
						// 设置新的费率
						bean.setUnitPrice(unitPrice);
					}
					// 运费 = 原运费 + 装卸费
					transportFee = transportFee.add(serviceFee);
					// 设置新的运费
					bean.setTransportFee(transportFee);
				}				
			}
		}else{
			//整车运费已包含装卸费，如果修改了装卸费，则添加差额到运费中
			//运费 = 运费+装卸费(差值)
			BigDecimal oldServiceFee=ui.getOriginWaybill().getServiceFee();
			BigDecimal newServiceFee=bean.getServiceFee();
			BigDecimal difference=BigDecimal.ZERO;
			if(oldServiceFee!=null && newServiceFee!=null){
				//获取运费
				BigDecimal transportFee = bean.getTransportFee();
				//获取费率
				BigDecimal unitPrice = bean.getUnitPrice();
				difference=newServiceFee.subtract(oldServiceFee);
				transportFee = transportFee.add(difference); 
				//设置新的运费
				bean.setTransportFee(transportFee);
				//费率  = 最新运费（运费+装卸费）/重量或体积
				unitPrice = transportFee.divide(getWeightOrVolume(bean),2,BigDecimal.ROUND_HALF_DOWN);
				//设置新的费率
				bean.setUnitPrice(unitPrice);
			}
		}
		//zxy 20130909   end 修改：当整车运单时，把装卸费计算到开单报价中，以前是不计算的。  		
		
	}
	
	/**
	 * 是否计算装卸费
	 * @param bean
	 * @return
	 */
	private boolean iscalServiceFee(WaybillInfoVo bean){
		boolean flag=false;
		if(FossConstants.YES.equals(bean.getIsCalTraFee())
				|| (bean.getIsWholeVehicle()!=null && bean.getIsWholeVehicle())){
			flag=true;
		} 
		return flag;
	}
	
	/**
	 * 
	 * 获取重量或者体积
	 * @author 025000-FOSS-helong
	 * @date 2013-3-9 下午03:29:54
	 */
	private BigDecimal getWeightOrVolume(WaybillInfoVo bean)
	{
		//判断是否按重量计费
		if (WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode())) {
			return bean.getGoodsWeightTotal();
		}else
		{
			//按体积计费
			ProductEntityVo productVo = bean.getFinalProductCode();
			//判断是否空运
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
				//是空运则返回计费重量
				return bean.getBillWeight();
			}else
			{
				//是汽运则返回体积
				return bean.getGoodsVolumeTotal();
			}
		}
	}
	
	/**
	 * 
	 * 验证是否可以开装卸费
	 * 
	 * 
	 * 1、装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
	 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
	 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
	 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
	 * 2、如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，为系统默认的公布价。
	 * 开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，开单显示运费（C）=Q*Z=C0+M ；
	 * 3、当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），需先清空装卸费为零，然后再修改数据。
	 * 如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
	 * 4、当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
	 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
	 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。（其中Q1是装卸费M1时的费率,Z为重量/体积）；
	 * 5、系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。（只限制配载类型为专线的，包括月结）；
	 * 6、对于显示费率不等于显示费率乘以重量的问题，要求如下：
	 * 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
	 * 	且显示运费等于该显示费率*重量；
	 * 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
	 * 	则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
	 * 7、只要含与不含装卸费两者有交叉的，均以不含为准；
	 * 8、装卸费特殊部门表：（建议：做为可配置的基础数据表）
	 * 
	 * 特殊部门												
	 * 华东事业部		上海虹桥营业部、上海闵行区营业部、上海嘉定区南翔营业部										
	 * 												
	 * 华北事业部		"北京丰台区新发地营业部、北京丰台区大红门营业部、北京顺义区营业部、北京中关村上地营业部、石家庄栾城营业部、河北廊坊营业部、天津塘沽区营业部、天津东丽区营业部、天津北辰区营业部、天津武清区营业部、沈阳东陵区南塔营业部、沈阳于洪区张士营业部、辽宁鞍山营业部、青岛即墨营业部、青岛李沧区营业部
	 * "										
	 * 												
	 * 												
	 * 												
	 * 深圳事业部		深圳八卦岭营业部、深圳华强桑达营业部、深圳宝安机场营业部、深圳宝安区塘尾营业部、深圳南山营业部、惠州市惠阳营业部、惠州惠城区河南岸营业部、东莞虎门镇营业部、广东汕头营业部										
	 * 												
	 * 												
	 * 												
	 * 广州事业部		广州白云区新市营业部、广州花都区花山营业部、广州花都区新华营业部、广州白云区嘉忠营业部、广州白云区三元里岗头大街营业部、中山小榄营业部、江门开平营业部、广西南宁安吉营业部、佛山张槎营业部、佛山市营业部										
	 * 												
	 * 												
	 * 												
	 * 西部大区		成都武候区双星大道营业部、重庆经开区营业部、云南昆明营业部、四川泸州营业部										
	 * 												
	 * 
	 * 特殊部门												
	 * 序号	部门	事业部										
	 * 1	上海虹桥营业部	华东事业部										
	 * 2	上海闵行区营业部	华东事业部										
	 * 3	上海嘉定区南翔营业部	华东事业部										
	 * 4	北京丰台区新发地营业部	华北事业部										
	 * 5	北京丰台区大红门营业部	华北事业部										
	 * 6	北京顺义区营业部	华北事业部										
	 * 7	北京中关村上地营业部	华北事业部										
	 * 8	石家庄栾城营业部	华北事业部										
	 * 9	河北廊坊营业部	华北事业部										
	 * 10	天津塘沽区营业部	华北事业部										
	 * 11	天津东丽区营业部	华北事业部										
	 * 12	天津北辰区营业部	华北事业部										
	 * 13	天津武清区营业部	华北事业部										
	 * 14	沈阳东陵区南塔营业部	华北事业部										
	 * 15	沈阳于洪区张士营业部	华北事业部										
	 * 16	辽宁鞍山营业部	华北事业部										
	 * 17	青岛即墨营业部	华北事业部										
	 * 18	青岛李沧区营业部	华北事业部										
	 * 19	深圳八卦岭营业部	深圳事业部										
	 * 20	深圳华强桑达营业部	深圳事业部										
	 * 21	深圳宝安机场营业部	深圳事业部										
	 * 22	深圳宝安区塘尾营业部	深圳事业部										
	 * 23	深圳南山营业部	深圳事业部										
	 * 24	惠州市惠阳营业部	深圳事业部										
	 * 25	惠州惠城区河南岸营业部	深圳事业部										
	 * 26	东莞虎门镇营业部	深圳事业部										
	 * 27	广东汕头营业部	深圳事业部										
	 * 28	广州白云区新市营业部	广州事业部										
	 * 29	广州花都区花山营业部	广州事业部										
	 * 30	广州花都区新华营业部	广州事业部										
	 * 31	广州白云区嘉忠营业部	广州事业部										
	 * 32	广州白云区三元里岗头大街营业部	广州事业部										
	 * 33	中山小榄营业部	广州事业部										
	 * 34	江门开平营业部	广州事业部										
	 * 35	广西南宁安吉营业部	广州事业部										
	 * 36	佛山张槎营业部	广州事业部										
	 * 37	佛山市营业部	广州事业部										
	 * 38	成都武候区双星大道营业部	华中事业部										
	 * 39	重庆经开区营业部	华中事业部										
	 * 40	云南昆明营业部	华中事业部										
	 * 41	四川泸州营业部	华中事业部										
	 * 
	 * 9、2012年12月1日开业的部门不能开装卸费
	 * 10、如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * 11、是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
	 * 12、装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-10 下午03:39:11
	 */
	private void validataServiceFee(WaybillInfoVo bean) {
		BigDecimal serivceFee = bean.getServiceFee();

		if (serivceFee == null || serivceFee.longValue() == 0) {
			return;
		}
		BigDecimal transportFee = bean.getTransportFee();
		BigDecimal serviceFee2 = transportFee.multiply(getServiceFeeRate(bean));
//		serviceFee2 = serviceFee2.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		if (serivceFee.compareTo(serviceFee2) > 0) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFee"));
		}
	}


	/**
	 * 
	 * 获取装卸费费率
	 * 
	 * 1、装卸费金额需满足不能超过纯运费的15%（特殊部门为35%）。
	 * 即：装卸费（M）<=纯运费（C0）*15%（特殊部门为35%）=【重量/体积】（Z）*原费率（Q0）*15%（特殊部门为35%），
	 * （其中，原费率Q0为：公布价）。 当装卸费大于零时，开单显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，
	 * 开单显示运费（C）=Q*Z=C0+M 。且此显示费率不可更改；（对于专线的散客而言）
	 * 2、如果该客户为月结客户、整车、中转下线或偏线，则原费率（Q0）为装卸费前的费率，为系统默认的公布价。
	 * 开单最终显示费率（Q）=（M+CO*优惠折扣）/（优惠折扣*Z） ，开单显示运费（C）=Q*Z=C0+M ；
	 * 3、当修改除装卸费影响外有影响费率的地方时（如目的站更改、重量和体积变化影响到费率），需先清空装卸费为零，然后再修改数据。
	 * 如：月结客户与非月结客户、整车与非整车、目的站、重量、体积；
	 * 4、当装卸费由M1修改为M2时：先判断M2是否满足公式：M2<=(Q1-M1/Z）*Z*15%（特殊部门为35%）。
	 * 如果公式成立，则最终显示费率Q2=Q1-M1/Z+M2/Z，最终显示运费（C2）= Q2*Z。
	 * 否则，清空装卸费为零，最终显示费率Q2=Q1-M1/Z，最终显示运费（C2）= Q2*Z。（其中Q1是装卸费M1时的费率,Z为重量/体积）；
	 * 5、系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。（只限制配载类型为专线的，包括月结）；
	 * 6、对于显示费率不等于显示费率乘以重量的问题，要求如下：
	 * 若该单含装卸费且折算后的费率（A）的有效小数位为小于或等于两位小数，则显示费率（Q）=费率（A）。
	 * 	且显示运费等于该显示费率*重量；
	 * 若该单含装卸费且折算后的费率（A）的有效小数位大于两位小数，令费率（B）=费率（A）的取前2个小数位的值（注：直接截取A的值，不四舍五入）。
	 * 	则显示费率（Q）=费率（B）+0.01。显示运费等于该显示费率*重量；
	 * 7、只要含与不含装卸费两者有交叉的，均以不含为准；
	 * 8、装卸费特殊部门表：（建议：做为可配置的基础数据表）
	 * 
	 * 特殊部门												
	 * 华东事业部		上海虹桥营业部、上海闵行区营业部、上海嘉定区南翔营业部										
	 * 												
	 * 华北事业部		"北京丰台区新发地营业部、北京丰台区大红门营业部、北京顺义区营业部、北京中关村上地营业部、石家庄栾城营业部、河北廊坊营业部、天津塘沽区营业部、天津东丽区营业部、天津北辰区营业部、天津武清区营业部、沈阳东陵区南塔营业部、沈阳于洪区张士营业部、辽宁鞍山营业部、青岛即墨营业部、青岛李沧区营业部
	 * "										
	 * 												
	 * 												
	 * 												
	 * 深圳事业部		深圳八卦岭营业部、深圳华强桑达营业部、深圳宝安机场营业部、深圳宝安区塘尾营业部、深圳南山营业部、惠州市惠阳营业部、惠州惠城区河南岸营业部、东莞虎门镇营业部、广东汕头营业部										
	 * 												
	 * 												
	 * 												
	 * 广州事业部		广州白云区新市营业部、广州花都区花山营业部、广州花都区新华营业部、广州白云区嘉忠营业部、广州白云区三元里岗头大街营业部、中山小榄营业部、江门开平营业部、广西南宁安吉营业部、佛山张槎营业部、佛山市营业部										
	 * 												
	 * 												
	 * 												
	 * 西部大区		成都武候区双星大道营业部、重庆经开区营业部、云南昆明营业部、四川泸州营业部										
	 * 												
	 * 
	 * 特殊部门												
	 * 序号	部门	事业部										
	 * 1	上海虹桥营业部	华东事业部										
	 * 2	上海闵行区营业部	华东事业部										
	 * 3	上海嘉定区南翔营业部	华东事业部										
	 * 4	北京丰台区新发地营业部	华北事业部										
	 * 5	北京丰台区大红门营业部	华北事业部										
	 * 6	北京顺义区营业部	华北事业部										
	 * 7	北京中关村上地营业部	华北事业部										
	 * 8	石家庄栾城营业部	华北事业部										
	 * 9	河北廊坊营业部	华北事业部										
	 * 10	天津塘沽区营业部	华北事业部										
	 * 11	天津东丽区营业部	华北事业部										
	 * 12	天津北辰区营业部	华北事业部										
	 * 13	天津武清区营业部	华北事业部										
	 * 14	沈阳东陵区南塔营业部	华北事业部										
	 * 15	沈阳于洪区张士营业部	华北事业部										
	 * 16	辽宁鞍山营业部	华北事业部										
	 * 17	青岛即墨营业部	华北事业部										
	 * 18	青岛李沧区营业部	华北事业部										
	 * 19	深圳八卦岭营业部	深圳事业部										
	 * 20	深圳华强桑达营业部	深圳事业部										
	 * 21	深圳宝安机场营业部	深圳事业部										
	 * 22	深圳宝安区塘尾营业部	深圳事业部										
	 * 23	深圳南山营业部	深圳事业部										
	 * 24	惠州市惠阳营业部	深圳事业部										
	 * 25	惠州惠城区河南岸营业部	深圳事业部										
	 * 26	东莞虎门镇营业部	深圳事业部										
	 * 27	广东汕头营业部	深圳事业部										
	 * 28	广州白云区新市营业部	广州事业部										
	 * 29	广州花都区花山营业部	广州事业部										
	 * 30	广州花都区新华营业部	广州事业部										
	 * 31	广州白云区嘉忠营业部	广州事业部										
	 * 32	广州白云区三元里岗头大街营业部	广州事业部										
	 * 33	中山小榄营业部	广州事业部										
	 * 34	江门开平营业部	广州事业部										
	 * 35	广西南宁安吉营业部	广州事业部										
	 * 36	佛山张槎营业部	广州事业部										
	 * 37	佛山市营业部	广州事业部										
	 * 38	成都武候区双星大道营业部	华中事业部										
	 * 39	重庆经开区营业部	华中事业部										
	 * 40	云南昆明营业部	华中事业部										
	 * 41	四川泸州营业部	华中事业部										
	 * 
	 * 9、2012年12月1日开业的部门不能开装卸费
	 * 10、如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
	 * 11、是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
	 * 12、装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-10 下午03:49:13
	 */
	private BigDecimal getServiceFeeRate(WaybillInfoVo bean) {
		BigDecimal serviceFeeRate = bean.getServiceFeeRate();

		if (serviceFeeRate == null) {
			ProductEntityVo productVo = bean.getProductCode();
			// 调用接口读取装卸费费率
			ServiceFeeRateDto dto = null;
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO);
			}else
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO);
			}
			if (dto == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.errorSerivceFeeCalculate="));
			} else {
				// 判断是否存在装卸费费率
				if (dto.getServiceFeeRate() == null) {
					throw new WaybillValidateException(dto.getMessage());
				} else {
					serviceFeeRate = dto.getServiceFeeRate();
				}
			}
		}
		return serviceFeeRate;
	}
	
	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:41:42
	 */
	@Override
	public void setIInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
	}

	
	/**
	 * 
	 * 计算转运费
	 * 
	 * 转运信息对应的相关业务规则：
	 * 转运目的站：转运提货网点对应的目的站，与收货人地址、提货方式进行关联。
	 * 转运提货网点：转运后的提货网点，与收货人地址、提货方式进行关联。
	 * 转运运输性质：
	 * 原运输类型为汽运：以原目的站作为转运始发站，与转运目的站匹配运输性质；
	 * 原运输类型为偏线或空运：
	 * ⅰ 若货物未出原最终配载部门库存，以原最终配载部门所在城市作为转运始发站，与转运目的站匹配运输性质；
	 * ⅱ 若货物已出原最终配载部门库存，转运运输性质只能为偏线或空运。
	 * 提货方式：起草转运单时，提货方式与原运输信息取消关联，与转运提货网点进行关联。
	 * 转运配载类型：转运运输性质为空运时，包含合大票、单独开单；其他转运运输性质时，只含有汽运。
	 * 转运预配航班：转运运输性质为空运时为可选状态，包含早班、中班、晚班。
	 * 转运计费类型：分为重量计费、体积计费，系统根据货物重量、体积、转运费率确定转运计费类型。
	 * 转运费率：
	 * 原运输类型为汽运：原目的站作为转运始发站，系统根据转运始发站、转运目的站、转运运输性质读取公布价基础资料（上门发货公布价）来确定转运费率，若未读取到公布价，则人工录入转运费率。
	 * 原运输类型为偏线或空运：
	 * ⅰ 若货物未出原最终配载部门库存，系统根据原最终配载部门所在城市、转运目的站、转运运输性质读取公布价基础资料（上门发货公布价）来确定转运费率，若未读取到公布价，则人工录入转运费率；
	 * ⅱ 若货物已出原最终配载部门库存，则人工录入转运费率。
	 *     注：转运费率读取的公布价为运单开单时对应的产品价格版本
	 * 转运费：
	 * 　　1）计费类型为“重量计费”时，转运费=转运费率*重量；
	 * 　　2）计费类型为“体积计费”时，转运费=转运费率*体积。
	 * 注：转运费无最低一票。
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:34:10
	 */
	private void calculateTransferFee(WaybillInfoVo bean) {
		//更改类型
		String rfcType = bean.getRfcType().getValueCode();
		// 转运时计算
		if(WaybillRfcConstants.TRANSFER.equals(rfcType)){
			
			boolean isCalcZZF = isNeedCalcZZF(bean);
		
			BigDecimal tfrFee = bean.getTfrFee();// +转运费
			if(tfrFee == null){
				tfrFee = BigDecimal.ZERO;
			}
			
			//判断是否累加过中转费
//			if(FossConstants.YES.equals(bean.getIsFlagAddZZF())){
				//从增值服务费中减去上次计算的中转费
				bean.setOtherFee(bean.getOtherFee().subtract(tfrFee));
//			}			
			
			if(!isCalcZZF){
				//费率
				bean.setTfrUnitPrice(BigDecimal.ZERO);
				//总费用
				tfrFee = BigDecimal.ZERO;
			}else if(bean.isTfrNeedHandWrite()){
				//确保计算运费时，转运费率已输入
				if(bean.getTfrBillingType() == null){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.nullTfrBillingType"));
				}
				//如果转运费率人工编辑	
				String tfrBillType = bean.getTfrBillingType().getValueCode();
				if(StringUtil.isEmpty(tfrBillType)){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.nullTfrBillingType"));
				}
				if(bean.getTfrUnitPrice()==null){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.nullTfrUnitPrice"));
				}
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(tfrBillType)){
					// 重量计费
					tfrFee = bean.getGoodsWeightTotal().multiply(bean.getTfrUnitPrice());
				}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(tfrBillType)){
					// 体积计费
					tfrFee = bean.getGoodsVolumeTotal().multiply(bean.getTfrUnitPrice());
				}
			}else{
				// 自动计算
				List<ProductPriceDto> productPrice = null;
				// 查询产品价格
				if (bean.getPickupToDoor()) {
					productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.YES, Common.ZYF));
					if (productPrice == null) {
						productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO, Common.ZYF));
					}
				} else {
					productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO, Common.ZYF));
				}

				//开单版本读不到取当前版本
				if(productPrice == null){
					// 查询产品价格
					if (bean.getPickupToDoor()) {
						productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.YES, Common.ZYF));
						if (productPrice == null) {
							productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.NO, Common.ZYF));
						}
					} else {
						productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.NO, Common.ZYF));
					}
				}
				if (productPrice == null) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
				} else {
					for (ProductPriceDto dto : productPrice) {
						//设置计费类型
						DataDictionaryValueVo dataDictionaryValueVo = new DataDictionaryValueVo();
						dataDictionaryValueVo.setValueCode(dto.getCaculateType());
						bean.setTfrBillingType(dataDictionaryValueVo);
						//费率
						bean.setTfrUnitPrice(dto.getActualFeeRate());
						//总费用
						tfrFee = dto.getCaculateFee();
					}
				}
			}

			
			bean.setTfrFee(tfrFee.setScale(0, BigDecimal.ROUND_HALF_UP));// 四舍五入
			bean.setRtnFee(BigDecimal.ZERO);
			//设置转运费到其他费用中
			ui.getBindingListener().setTransportOtherCharge(bean);
			//标记已累加过中转费
//			bean.setIsFlagAddZZF(FossConstants.YES);
		}
	}


	/**
	 * 计算返货费
	 * 
	 * 返货信息对应的相关业务规则：
	 * 返货目的站：返货提货网点对应的目的站，与收货人地址进行关联。。
	 * 返货提货网点：货物返回的提货网点，与收货人地址进行关联，只能选择公司自有营业网点。
	 * 返货运输性质：
	 * 原运输类型为汽运：
	 * ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，与返货目的站匹配返货运输性质；
	 * ⅱ 货物已出第一中转外场库存：以原目的站作为返货始发站，与返货目的站匹配运输性质；
	 * 原运输类型为偏线或空运：
	 * ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，与返货目的站匹配返货运输性质；
	 * ⅱ 货物已出第一中转外场库存，未出原最终配载部门库存：以原最终配载部门所在城市作为返货始发站，与返货目的站匹配运输性质。
	 * ⅲ 货物已出原最终配载部门库存：返货运输性质只能为偏线或空运。
	 * 返货计费类型：分为重量计费、体积计费，系统根据货物重量、体积、返货费率确定返货计费类型。
	 * 返货费率：
	 * 原运输类型为汽运：
	 * ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率变为原运输性质下的收货部门至返货始发站的费率；
	 * ⅱ 货物已出第一中转外场库存：原目的站作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率不变。
	 * 原运输类型为偏线或空运：
	 * ⅰ 货物未出第一中转外场库存：第一中转外场所在城市作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率变为原运输性质下的收货部门至返货始发站的费率；
	 * ⅱ 货物已出第一中转外场库存，未出原最终配载部门库存：原最终配载部门所在城市作为返货始发站，系统根据返货始发站、返货目的站、返货运输性质读取公布价基础资料（上门发货公布价）来确定返货费率，原费率变为原运输性质下的收货部门至返货始发站的费率；
	 * ⅲ 货物已出原最终配载部门库存：人工录入返货费率，原费率不变。
	 * 　　注：返货费率读取的公布价为运单开单时对应的产品价格版本。
	 * 返货费：
	 * 计费类型为“重量计费”时，返货费=返货费率*重量；
	 * 计费类型为“体积计费”时，返货费=返货费率*体积。
	 * 注：返货费无最低一票。
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午7:34:28
	 */
	private void calculateReturnFee(WaybillInfoVo bean) {
		
		//更改类型
		String rfcType = bean.getRfcType().getValueCode();
		// 返货时计算
		if(WaybillRfcConstants.RETURN.equals(rfcType)){
			
			boolean isCalcZZF = isNeedCalcZZF(bean);
			
			BigDecimal rtnFee = bean.getRtnFee();// +返货费
			if(rtnFee == null){
				rtnFee = BigDecimal.ZERO;
			}
			
			//判断是否累加过中转费
//			if(FossConstants.YES.equals(bean.getIsFlagAddZZF())){
				//从增值服务费中减去上次计算的中转费
				bean.setOtherFee(bean.getOtherFee().subtract(rtnFee));
//			}			
			
			if(!isCalcZZF){
				//费率
				bean.setRtnUnitPrice(BigDecimal.ZERO);
				//总费用
				rtnFee = BigDecimal.ZERO;
			}else if(bean.isRtnNeedHandWrite()){
				//确保计算运费时，转运费率已输入
				if(bean.getRtnBillingType() == null){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.nullRtnBillingType"));
				}
				//如果返货费率人工编辑	
				String rtnBillType = bean.getRtnBillingType().getValueCode();
				if(bean.getRtnUnitPrice()==null){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.nullTfrUnitPrice"));
				}
				if(StringUtil.isEmpty(rtnBillType)){
					throw new WaybillValidateException(i18n.get("foss.gui.changing.calculateAction.exception.nullRtnBillingType"));
				}
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(rtnBillType)){
				// 重量计费
					rtnFee = bean.getGoodsWeightTotal().multiply(bean.getRtnUnitPrice());
				}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(rtnBillType)){
					// 体积计费
					rtnFee = bean.getGoodsVolumeTotal().multiply(bean.getRtnUnitPrice());
				}
			}else{
				// 自动计算
				List<ProductPriceDto> productPrice = null;
				// 查询产品价格
				if (bean.getPickupToDoor()) {
					productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.YES, Common.FHF));
					if (productPrice == null) {
						productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO, Common.FHF));
					}
				} else {
					productPrice = waybillService.queryProductPriceList(Common.getQueryParam(bean, FossConstants.NO, Common.FHF));
				}
				
				//开单版本读不到取当前版本
				if(productPrice == null){
					// 查询产品价格
					if (bean.getPickupToDoor()) {
						productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.YES, Common.FHF));
						if (productPrice == null) {
							productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.NO, Common.FHF));
						}
					} else {
						productPrice = waybillService.queryProductPriceList(Common.getNowQueryParam(bean, FossConstants.NO, Common.FHF));
					}
				}
				
				if (productPrice == null) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
				} else {
					for (ProductPriceDto dto : productPrice) {
						//设置计费类型
						DataDictionaryValueVo dataDictionaryValueVo = new DataDictionaryValueVo();
						dataDictionaryValueVo.setValueCode(dto.getCaculateType());
						bean.setRtnBillingType(dataDictionaryValueVo);
						//费率
						bean.setRtnUnitPrice(dto.getActualFeeRate());
						//总费用
						rtnFee = dto.getCaculateFee();
						
					}
				}
			}
			bean.setRtnFee(rtnFee.setScale(0, BigDecimal.ROUND_HALF_UP));// 四舍五入
			bean.setTfrFee(BigDecimal.ZERO);
			//设置转运费到其他费用中
			ui.getBindingListener().setTransportOtherCharge(bean);
			//标记已累加过中转费
//			bean.setIsFlagAddZZF(FossConstants.YES);
		}
	}

	/**
	 * 
	 * 是否需要重新计算中转费
	 * 
	 * 现状：FOSS设计：货物离开出发部门库存后，客户要求发起目的站变更均要收取中转费。
	 * 从12月3日至12月18日，中转费投诉424条，最终外场出库前就起草同城更改并收取中转费的投诉有103条，
	 * 其中铂金客户4位，黄金客户8位。一线部门反馈，客户对现有中转费收取规则很不满意。 
	 * 
	 * 方案：1、同城定义：以原到达部门所在地级市为判断依据。更改前后部门在同一地级市行政区域范围内为同城，
	 * 更改前后部门不在同一地级市行政区域范围内为异地。 
	 * 2、货物离开出发部门库存后，客户要求将目的站更改为异地目的站，须收取中转费。
	 * 例如，客户要求将原发往重庆北碚区营业部的货物更改至四川泸州蓝田营业部，虽然是同一外场配载，但分属不同地级市，须收取中转费。 
	 * 3、货物离开出发部门库存后，客户要求将目的站更改为同城目的站： 
	 *  3.1、货物离开出发部门库存后至到达部门上一级外场交接出库前，不收取中转费。
	 *  例如客户要求将原发往上海浦东上南路营业部的货物更改至上海派送部，虽然是不同外场配载，但分属同意地级市，不收取中转费 
	 *  3.2、货物在到达部门（驻地派送部为到达部门）库存中，更改为同城部门，须收取中转费 
	 *  3.3、货物离开到达部门上一级外场库存或有交接记录且交接目的站为原到达部门，须收取中转费 
	 * 4、中转费计算规则不变 
	 * 
	 * 效益：每月货物在最终外场出库前，更改同城目的站的有17855条，公司每月开单量约为300万条，
	 * 规则优化后影响的更改率很小，为0.6%。但是可以提高这些更改运单客户的满意度。
	 * 平均每一条同城目的站更改需要20分钟时间解释，优化后将节省357100分钟、5951小时。
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-12 下午7:00:11
	 */
	private boolean isNeedCalcZZF(WaybillInfoVo bean) {
		
		//界面选择的新的提货网点
		BranchVo branchVo = bean.getFinalCustomerPickupOrgCode();
		
		//导入的时候的提货部门所属的
//		BranchVo branchVoOld = bean.getCustomerPickupOrgCode();
		BranchVo branchVoOld = ui.getOriginWaybill().getCustomerPickupOrgCode();		
		
		return compareOldNewBranchIsSameCity(bean, branchVo,
				branchVoOld);
	}
	
	/**
	 * 转运后发更改判断是否需要设置中转费
	 */
	private boolean isNeedCalcZZFAfterTransportChange(WaybillInfoVo bean, int selectedRow) {
		
		//界面选择的新的提货网点
		BranchVo branchVo = bean.getFinalCustomerPickupOrgCode();
		
		//选中的是第一行不需要计算中转费
		if(selectedRow <= 0)
			return false;
		
		JXTable table = ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable();
		TransferRecordTableModel tableModel = (TransferRecordTableModel)table.getModel();
		
		//获取选中的前一个提货网点（因为要改的是选中的那个提货网点，计算中转费要根据选中的前一个与更改后的提货网点之间的走货路径）
		BranchVo branchVoOld = tableModel.getData().get(selectedRow - 1).getCustomerPickupOrgCode();
		
		return compareOldNewBranchIsSameCity(bean, branchVo,
				branchVoOld);
	}
	
	/**
	 * 比较新旧网点是否同城
	 * @author WangQianJin
	 * @date 2013-6-15 下午3:54:53
	 */
	private boolean compareOldNewBranchIsSameCity(WaybillInfoVo bean,
			BranchVo branchVo, BranchVo branchVoOld) {

		boolean isCalcZZF = true;
		
		if(!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
				.equals(ui.getOriginWaybill().getProductCode().getCode())
				&& !ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
				.equals(ui.getOriginWaybill().getProductCode().getCode())){
			
			if(branchVo!=null && branchVoOld!=null){
				//修改后的提货网点如果是属于同一个城市
				if(branchVo.getCityCode()!=null 
						&&branchVoOld.getCityCode()!=null 
						&& branchVo.getCityCode().equals(branchVoOld.getCityCode())){
					String currentStockOrgCode = bean.getStockStatus().getCurrentStockOrgCode();
					//当前网点如果不是如果不是最后一个提货网点， 并且同城的情况下  不需要收取中转费
					boolean isCenter=false;
					try{
					SaleDepartmentEntity  saleDepartmentEntity =waybillService.querySaleDepartmentByCode(branchVoOld.getCode());
					
					if(saleDepartmentEntity!=null){
					//如果原来的提货网点是驻地营业部并且当前库存部门是驻地营业部所属外场，则收取中转费
					if(FossConstants.ACTIVE.equals(saleDepartmentEntity.getStation()) && currentStockOrgCode.equals(saleDepartmentEntity.getTransferCenter()))
							{
						      isCenter=true;
							}
				
					}
					}catch(Exception e){
						//不处理
					}
					
					if(!currentStockOrgCode.equals(branchVoOld.getCode())&&!isCenter){
						isCalcZZF = false;
					}
				}
			}
		}
		return isCalcZZF;
	}
	

/**
	 * 
	 * 获取接货费查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getPickupFeeParam(WaybillInfoVo bean,boolean isGetCurrentPrice) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getFinalCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getFinalProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		if(isGetCurrentPrice){
			queryDto.setReceiveDate(null);// 营业部收货日期（可选，无则表示当前日期）
		}else{
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_JH);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		//设置营销活动查询条件
		Common.settterActiveParamInfoValueAdd(queryDto, bean);
		//开单时间
		queryDto.setBillTime(bean.getBillTime());// 设置开单时间
		queryDto.setValuedtos(bean.getValuedtos());//设置代收和保价，是否接货（条件折扣）
		//更改单，条件折扣，封装是否接货
		if(bean.getPickupToDoor()!=null&&bean.getPickupToDoor()){//是否上门接货为true，则设置“Y”
			
			if(bean.getFreePickupGoods()){ //如果勾选了上门接货并勾选了免费接货，则设置“N”
				queryDto.setIsReceiveGoods(FossConstants.NO);
			}else{
			    queryDto.setIsReceiveGoods(FossConstants.YES);
			}
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);//否则设置“N”
		}
		//为了调用规则引擎，设置计费类型
		queryDto.setCaculateType(bean.getCaculateType());
		//定价体系优化项目POP-470  规则引擎设置了绑定折扣条件  将保费金额、代收货款个字段传给规则引擎（更改单）
		 Map<String,GuiQueryBillCalculateSubDto> valuedtos=new HashMap<String,GuiQueryBillCalculateSubDto>();
		 //保费
		 GuiQueryBillCalculateSubDto bfDto  = new GuiQueryBillCalculateSubDto();
		 bfDto.setOriginnalCost(bean.getInsuranceAmount());
		 valuedtos.put(PriceEntityConstants.PRICING_CODE_BF, bfDto);
		 //代收
		 GuiQueryBillCalculateSubDto hkDto  = new GuiQueryBillCalculateSubDto();
		 hkDto.setOriginnalCost(bean.getCodAmount());
		 valuedtos.put(PriceEntityConstants.PRICING_CODE_HK, hkDto);
		 queryDto.setValuedtos(valuedtos);
		 queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	
	/**
	 * 
	 * 设置接货费
	 * 
	 * @author 025000-FOSS-helong
	 * @param dto 
	 * @param bean 
	 * @date 2012-12-7 上午10:05:31
	 */
	private void setPickupFee(WaybillInfoVo bean, ValueAddDto dto) {
		if (dto == null) {
			bean.setPickupFee(BigDecimal.ZERO);// 设置接货费
			bean.setBasePickupFee(BigDecimal.ZERO);// 原始接货费
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.PickupFeeNull"));
		} else {
			/**
			 * 设置接货费(MANA-257接货费优化)
			 */
			// 设置接货费最大值
			bean.setMaxPickupFee(CommonUtils.defaultIfNull(dto.getMaxFee()));
			// 设置接货费最小值
			bean.setMinPickupFee(CommonUtils.defaultIfNull(dto.getMinFee()));
			// 【固定值】
			// 返回的接货费值不为空，则表示有客户编码且有设置客户接货费值
			if (null != dto.getFee()) {
				//设置接货费是否来自客户合同字段值 便于更改单界面接货费监听器使用
				bean.setPickUpFeeFromCustomer(true);
				bean.setBasePickupFee(dto.getFee());// 原始接货费				
			}
			// 【最小值，最大值】
			else {	
				bean.setBasePickupFee(dto.getMinFee());// 原始接货费	
				//设置接货费是否来自客户合同字段值 便于更改单界面接货费监听器使用
				bean.setPickUpFeeFromCustomer(false);
			}
			
			ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEnabled(true);
			ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEditable(true);
			// 封装查询数据
			WaybillEntity queryParm = CommonUtils.getWaybillEntity(bean);
			queryParm.setPickupFee(bean.getBasePickupFee());
			if(bean.getPickupToDoor()){
				queryParm.setPickupToDoor(FossConstants.YES);
			}
			//是否收取过接货费
			boolean isGetPickFee = waybillService.queryIsGetPickFeeByCust(queryParm);			
			if(!isGetPickFee){				
				//判断是否上门接货
				if(bean.getPickupToDoor() != null && bean.getPickupToDoor()){
					//如果修改了接货费，需要重新赋值
					if(bean.isModifyMark()){
						//如果没收取过接货费，则需要重新为接货费赋值
						if (null != dto.getFee()) {								
							bean.setPickupFee(dto.getFee());
						}else {								
							bean.setPickupFee(dto.getMinFee());			
						}
					}
					//日常版本缺陷管理DEFECT-8250 手动修改接货费后，费用变化为原计算费用
//					else{
//						//非接货价区域，开单或更改选择上门接货按钮时（集中开单默认勾选上门接货且不可修改），
//						//系统优先根据发货客户编码判断此客户有无合同存在，有合同存，且CRM合同中有规定接货费具体值，
//						//在开单部门当天[8时至次日8时）此客户编码没有运单收取过接货费，则系统在接货费栏显示CRM接货费值不可修改
//						if (null != dto.getFee()) {								
//							ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEnabled(false);
//							ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEditable(false);
//							bean.setPickupFee(dto.getFee());
//							bean.setPickUpFeeCanvas(dto.getFee().toString());
//						}else{
//							ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEnabled(false);
//							ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEditable(false);
//							bean.setPickupFee(dto.getCaculateFee());
//							bean.setPickUpFeeCanvas(dto.getCaculateFee().toString());
//						}
//					}	
					/**
					 * DMANA-6051   开单接货费优化—外租车系统需求
			           * 新增判断条件  
			           * 1.满足配置的接货费可以改小的参数是开启状态（Y）
			           * 2.满足当营业部为非集中接货区域，开单接货司机为外请司机的上门接货运单
			           * 3.满足不是crm合同客户，或者合同中对接货费没有设置
			           * 接货费可以改大或改小，但不能小于0
			           */
			          //获取开单和更改单接货费是否可以改小的配置参数 根据该配置参数来判断是否进行接货费更改的校验
			          ConfigurationParamsEntity isValidationEntity = BaseDataServiceFactory.getBaseDataService().queryConfigurationParamsByEntity(null,PricingConstants.PICKUP_FEE_IS_CHANGE_SMALL,null);
			          //获取当前登陆部门编码
			          String loginDeptCode = bean.getReceiveOrgCode();
			          SaleDepartmentEntity  deptEntity = new SaleDepartmentEntity();
			          
			          if(bean.getReceiveOrgCode()!=null){
			                  //根据当前登陆部门编码，查询对应的营业部信息
			            deptEntity= BaseDataServiceFactory.getBaseDataService().querySaleDepartmentByCodeOnline(loginDeptCode);

			          }
			          
			            if(isValidationEntity !=null 
			                && FossConstants.YES.equals(isValidationEntity.getConfValue())
			                &&deptEntity !=null 
			                &&FossConstants.NO.equals(deptEntity.getInCentralizedShuttle())
			                &&PricingConstants.DRIVER_CODE_OUT.equals(bean.getDriverCode())
			                &&!bean.isPickUpFeeFromCustomer()){
			              
			                if(bean.getPickupFee()!=null && bean.getPickupFee().compareTo(BigDecimal.ZERO)<0){
			                  //是否开启更改单接货费验证
			                  boolean isStartRfcPickfeeVal=waybillService.isStartRfcPickfeeVal();
			                  if(isStartRfcPickfeeVal){
			                    //接货费最低值不能小于0
			                    throw new WaybillRfcChangeException(i18n.get("foss.gui.changing.calculateaction.thiscust.noget.pickFee.isNotSmallThanZero"));
			                  }
			                }
			            }
			          //MANA-257接货费优化  判断接货费是否小于规定值
			          else if(bean.getPickupFee().doubleValue()<bean.getBasePickupFee().doubleValue()){  
			            //是否开启更改单接货费验证
			            boolean isStartRfcPickfeeVal=waybillService.isStartRfcPickfeeVal();
			            if(isStartRfcPickfeeVal){
			              //该客户未收取过规定的接货费值：
			              throw new WaybillRfcChangeException(i18n.get("foss.gui.changing.calculateaction.thiscust.noget.pickfee")+bean.getBasePickupFee().toString());
			            }
			          }	
				}						
			}					
			/**
			 * 【新需求】放开接货费
			 * @date 2014-10-11
			 * @author wutao
			 * 运输性质：门到门接货费为0;因此需要进行设置
			 
			if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())
			 		&& PriceEntityConstants.PRICING_CODE_JH.equals(dto.getPriceEntityCode())){
				bean.setPickupFee(BigDecimal.ZERO);
			}	
			*/	
		}

	}

	//内部类用与方法抽取
	private class GetIsPickupAndPrice {
		private WaybillInfoVo bean;
		private List<ProductPriceDto> productPrice;
		private Boolean isPickupPrice;

		public GetIsPickupAndPrice(WaybillInfoVo bean, List<ProductPriceDto> productPrice, Boolean isPickupPrice) {
			this.bean = bean;
			this.productPrice = productPrice;
			this.isPickupPrice = isPickupPrice;
		}

		public List<ProductPriceDto> getProductPrice() {
			return productPrice;
		}

		public Boolean getPickupPrice() {
			return isPickupPrice;
		}

		public GetIsPickupAndPrice invoke() {
			if (productPrice == null) {
                if (bean.getPickupToDoor()) {
                    //上门接货
                    //当勾选上门接货并没有勾选选免费接货时
                    //@author ZouShengLi
                    // @date 2016-4-15 下午18:00:00
                    if (!bean.getFreePickupGoods()) {
                        productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.YES, Common.GBJ, true));
                        if (productPrice == null) {
                            productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, true));
                            isPickupPrice = Boolean.valueOf(false);
                        } else {
                            isPickupPrice = Boolean.valueOf(true);
                        }
                    } else {
                        productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, true));
                        isPickupPrice = Boolean.valueOf(false);

                    }
                } else {
                    //非上门接货
                    productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, true));
                }
            }
			return this;
		}
	}
}