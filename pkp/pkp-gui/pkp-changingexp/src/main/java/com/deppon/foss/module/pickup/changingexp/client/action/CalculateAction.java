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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/CalculateAction.java
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
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.apache.commons.beanutils.PropertyUtils;
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.pickup.changingexp.client.listener.WaybillInfoBindingListener;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.transport.TransferChangedRecordPanel.TransferRecordTableModel;
import com.deppon.foss.module.pickup.changingexp.client.utils.Common;
import com.deppon.foss.module.pickup.changingexp.client.utils.WaybillDtoFactory;
import com.deppon.foss.module.pickup.changingexp.client.validation.descriptor.WaybillInfoDescriptor;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoTempVo;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.dto.ServiceFeeRateDto;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressFieldForExp;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
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
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
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
	protected final static Logger LOG = LoggerFactory.getLogger(CalculateAction.class.getName());
	/**
	 * 更改单服务类统一入口
	 */
	private IWaybillRfcService waybillService = WaybillRfcServiceFactory
			.getWaybillRfcService();

	/**
	 * 参数配置
	 */
	private IConfigurationParamsService configurationParamsService =  GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);

	/**
	 * 更改单主UI
	 */
	private WaybillRFCUI ui;
	
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(CalculateAction.class);
	
	//重量
	private static final double FIFTY = 50;
	
	// 重量上限
		private static final double WEIGHT_UPPER_LIMIT = 50;

		// 体积上限
		private static final double VOLUME_UPPER_LIMIT = 0.3;
	 /**
     * 小数点保留位数
     */
    private static int newScale=2;
    
    /**
	 * 运单基础数据服务
	 */
	private static IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
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
		// 如果descrptor校验通过则执行下面的代码
		try {
			WaybillInfoVo bean = ui.getBinderWaybill();
			//伙伴开单的时候把字段置为true
			if(BZPartnersJudge.IS_PARTENER){
				bean.setPartnerBilling(true);				
			}else{
				bean.setPartnerBilling(false);
			}
			//直营开单也需要重置
			bean.setPtpWaybillOrgVo(PtpWaybillOrgVo.init());
			
			// 基本校验
			validate(bean);
			//清理费用相关信息
			//是否精准计价
			boolean isAccurateCost=false;
		
			if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod()
					.getValueCode())) {
				CustomerEntity customerEntity=waybillService.selectCustomerEntitybyCode(bean.getDeliveryCustomerCode());
				if(customerEntity!=null&&"Y".equalsIgnoreCase(customerEntity.getIsAccuratePrice())){
					isAccurateCost = true;
				}	
			}
			bean.setAccurateCost(isAccurateCost);
			cleanFee(bean);
			
			//提货方式为空
			if(bean.getFinalReceiveMethod()==null){
				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.transportInfoPanel.pickMode.label.isNull"));
			}
			
			// 判断是否内部带货:如果内部带货，不能计算优惠券
			if (!WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())) {
				// 计算各种费用
				calculateFee(bean);				
				
				/**
				 * 设置优惠总费用
				 */
				calcaulatePromotionsFee(bean);				
				
				// 需要重新计算运费
				CalculateFeeTotalUtils.calculateTotalFee(bean,true);				
				
			}
			
			// 计算内部带货公布价运费
			//calculateInnerPickupTransFee(bean);	
			// 内部带货其他费用置0
			Common.innerPickup(bean, ui, bean.getFinalReceiveMethod().getValueCode());			
			
			
			//历史运单调整费用
			operAdjustFee(bean);
			
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
			
			// 处理完优惠券清空优惠券费用，防止再次冲减
			CalculateFeeTotalUtils.cleanCouponFree(bean);
			//没有时效 不让开单
			if(bean.getPreCustomerPickupTime()==null){
				ui.getButtonPanel().getBtnSubmit().setEnabled(false);
			}else{
				Common.setSaveAndSubmitTrue(ui);
			}
			
			//合伙人公布运价不重新计算，需要用之前折前费用重新付值给当前的折前信息 2016年4月16日 15:01:19 葛亮亮
			if(BZPartnersJudge.IS_PARTENER && !FossConstants.YES.equals(bean.getIsCalTraFee())){
				bean.setIsChanged(FossConstants.YES); //更改
				
				//从折前表中取出保价费和接货费
				PartnersWaybillEntity partnersWaybillEntity = waybillService.queryPartnersWaybillEntityById(bean.getId());
			  
				if(null == partnersWaybillEntity){
				    throw new BusinessException("未能获取合伙人折前费用信息");
			    }else{
				  //折前公布运价费
				  bean.setZqTransportFee(null != partnersWaybillEntity.getTransportFee() ? partnersWaybillEntity.getTransportFee().divide(new BigDecimal(100)) : BigDecimal.ZERO);
			    }
			}
			
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
			List<OtherChargeVo> voList = model.getData();
			//保存计算前的费用
			CommoForFeeUtils.keepStandardExpFee(voList, bean);
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
			
			
			//伙伴开单
			/*if (ui.getWaybillInfoPanel().getBasicPanel().getPartnerCheckBox()
					.isSelected()) {
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEditable(true);
				ui.billingPayPanel.billingPayBelongPanel.getTxtPublicCharge()
						.setEnabled(true);
				ui.billingPayPanel.getBtnCalculate().setEnabled(true);
				bean.setTempTransportFee(bean.getTransportFee());
			}*/

			if(bean.getIsChangeReason()==1||bean.getIsChangeReason()==2){
			if(bean.getPaidMethod().getValueCode().equals("CH")){
				ui.getWaybillInfoPanel().getBillingPayPanel().getTxtCashpayment().setEditable(true);
				ui.getWaybillInfoPanel().getBillingPayPanel().getTxtCashpayment().setEnabled(true);
			}}
		    
		} catch (BusinessException w) {
			if(!"".equals(w.getMessage()))
			{
				MsgBox.showInfo(w.getMessage());
			}
		}

	}
	
	/**
	 * 历史运单调整费用
	 */
	private void operAdjustFee(WaybillInfoVo bean){
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
			totalPromotionsFee = totalPromotionsFee
					.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		/**
		 * 设置优惠费用
		 */
		bean.setPromotionsFee(totalPromotionsFee);
		/**
		 * 设置画布的优惠费用
		 */
		bean.setPromotionsFeeCanvas(totalPromotionsFee != null ? totalPromotionsFee.toString() : "");		
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
		
		//如果不需要计算公布价，则需要判断是否修改了营销活动
		if(!isNeedCalcTransportFee){
			isNeedCalcTransportFee=isModifyActiveInfo();
		}
		
		// 清理送货费
		cleanDeliverCharge(bean);

		List<WaybillDiscountVo> waybillDiscountVos = new ArrayList<WaybillDiscountVo>();
		if (bean.getWaybillDiscountVos() != null&&StringUtil.isNotEmpty(bean.getPromotionsCode())) {
			for (WaybillDiscountVo waybillDiscountVo : bean.getWaybillDiscountVos()) {
				/**
				 * 判断是否计算公布价
				 */
				if(isNeedCalcTransportFee){
					if (waybillDiscountVo.getDiscountId()!=null && waybillDiscountVo.getDiscountId().equals(bean.getPromotionsCode())) {
						waybillDiscountVos.add(waybillDiscountVo);
					}
				}else{
					//如果公布价不重新计算，把原有公布价折扣加上
					if (waybillDiscountVo.getFavorableTypeCode().equals(PricingConstants.VALUATION_TYPE_DISCOUNT)) {
						waybillDiscountVos.add(waybillDiscountVo);
					}else{
						//添加优惠券折扣
						if (waybillDiscountVo.getDiscountId()!=null && waybillDiscountVo.getDiscountId().equals(bean.getPromotionsCode())) {
							waybillDiscountVos.add(waybillDiscountVo);
						}
					}
				}
			}
		}
		bean.setWaybillDiscountVos(waybillDiscountVos);
		
		// 清理折扣优惠
		bean.setPromotionsFee(BigDecimal.ZERO);
		bean.setPromotionsFeeCanvas(String.valueOf(BigDecimal.ZERO));
		
				
	}

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
		bean.setInsuranceRate(BigDecimal.ZERO);
		// 保险手续费
//		bean.setInsuranceFee(BigDecimal.ZERO);
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
	}

	/**
	 * 
	 * 清理送货费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午07:41:07
	 */
	private void cleanDeliverCharge(WaybillPanelVo bean) {
		if(bean.getWaybillstatus()!=null && 
				!WaybillConstants.PDA_ACTIVE.equals(bean.getWaybillstatus()) &&
				!WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(bean.getWaybillstatus())){			
			bean.setDeliveryGoodsFeeCanvas("0");
			bean.setDeliveryGoodsFee(BigDecimal.ZERO);
			bean.setDeliverList(null);
		}
		
	}

	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	private void calculateFee(WaybillInfoVo bean) {

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
		    		// 设置运费
			    	bean.setRecordUnitPrice(BigDecimal.ZERO);
					// 设置费率
			    	bean.setRecordFee(BigDecimal.ZERO);
			    	//修改中转费
			    	changeTransportOtherCharge(bean, row);
		    	}
		    }
		}
		
		if(WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())){
			isNeedCalcTransportFee = false;
		}
		/**
		 * 快递返货开单之后发更改，如果上报原因为我司原因不计算公布价运费
		 */
		if(StringUtils.isNotEmpty(bean.getWaybillNo())){
			WaybillExpressEntity entity=waybillService.queryWaybillByWaybillNo(bean.getWaybillNo());
			if(entity!=null){
				ReturnGoodsRequestEntity e=waybillService.queryReturnGoodsRequestEntityByWayBillNo(entity.getOriginalWaybillNo());
				if(e!=null && WaybillConstants.RETURNREASON_COMPANY_REASON.equals(e.getReturnReason())){
					isNeedCalcTransportFee=false;
				}
			}
		}

		if(isNeedCalcTransportFee){
			//最低一票
			BigDecimal minTransportFee  = BigDecimal.ZERO;
			if(!bean.getIsWholeVehicle()){
				//有转运记录或返货记录不计算公布价,重新计算最后一段中转费
				// 获取公布价运费
				ProductPriceDto dto = calculateTransportFee(bean);
				minTransportFee = dto.getMinFee();//最低一票
				bean.setMinTransportFee(minTransportFee);
				
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
		 * 防止出现优惠运费时，点击第三次时重复冲减的问题
		 * MANA-1961 营销活动与优惠券编码关联
		 * 2014-04-10 026123
		 */
		CalculateFeeTotalUtils.calculateFee(bean, false);
		
		try {  
			if(bean.getChangeType()==1 && bean.getIsChangeReason() == 1&&bean.getOldReceiveOrgCode()!=null
					&&bean.getOldReceiveOrgCode()!=bean.getCustomerPickupOrgCode().getCode()){//客户原因
				LOG.info("进入 转寄退回  客户原因 。");
				
				WaybillInfoVo tmp_bean = new WaybillInfoVo() ;//
				PropertyUtils.copyProperties(tmp_bean,bean);
				tmp_bean.setReceiveOrgCode(bean.getOldReceiveOrgCode());
				tmp_bean.setReceiveOrgName(bean.getOldReceiveOrgName());
				tmp_bean.setReceiveCustomerAddress(bean.getOldreceiveCustomerAddress());
				cleanFee(tmp_bean);
				
				// 获取公布价运费
				ProductPriceDto productPriceDto = calculateTransportFee(tmp_bean);
				
				/*
				//获取GUI端快递运单的价格
				List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = waybillService
						.queryGuiExpressBillPrice(productPriceDtoCollection);
				
				// 获取公布价运费
				GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(
							guiResultBillCalculateDtos,
							PriceEntityConstants.PRICING_CODE_FRT, null);*/
				//计算优惠总费用
				calcaulatePromotionsFee(tmp_bean);
//				
				CalculateFeeTotalUtils.calculateTotalFee(tmp_bean,true);
				
				BigDecimal tmp_deci = productPriceDto.getStandardTransportFee() ;
				
				//判断运输性质是否 ‘商务专递’ ，‘商务专递’返货不享受折扣
				if(!WaybillConstants.DEAP.equals(tmp_bean.getProductCode().getCode())//是商务专递  且配置参数打开商务专递打折
						){
					//查询CRM 客户合同信息
					PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(bean);
					if(preferentialInfoDto!=null){
						//原折扣
						if(PriceEntityConstants.OLD_PREFE.equals(preferentialInfoDto.getExpBackFreghtType())){
							//转寄退回 原折扣 统一按照首重折扣计算
							BigDecimal discount = preferentialInfoDto.getChargeRebate() ;//首重
							tmp_deci = tmp_deci.multiply(discount).setScale(0, BigDecimal.ROUND_HALF_UP);
						}else if(PriceEntityConstants.NEW_PREFE.equals(preferentialInfoDto.getExpBackFreghtType())){
							//新折扣
							BigDecimal discount = preferentialInfoDto.getBackFreghtFixed();
							tmp_deci = tmp_deci.multiply(discount).setScale(0, BigDecimal.ROUND_HALF_UP);
						}//if end 
					}//if end
				}
				
				//其他费用面板中添加 中转费
				setOtherCharge(bean,tmp_deci);
				
				//记录改变的值
				WaybillInfoTempVo waybillInfoTempVo = new WaybillInfoTempVo();
				waybillInfoTempVo.setTransferFee(tmp_deci);
				bean.setWaybillInfoTempVo(waybillInfoTempVo);
				
				//签收单返单费用处理
				//其他费用面板中添加 签收单回单费用(由于返单类型不可更改所以可以只用更改后的费用与之前的费用进行比较)
				/*
				if(!WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())
					&& productPriceDto.getStandardExpFirstFee()!=null){
					setReturnBillCharge(bean,productPriceDto.getStandardExpFirstFee().divide(BigDecimal.valueOf(100L),0,BigDecimal.ROUND_HALF_UP));
				}
				*/
				
				bean.setChangeType(0);
			}else if(bean.getIsChangeReason() == 2){
				LOG.info("进入 转寄退回  内部原因 。");
				//内部原因
				// 获取公布价运费
				calculateTransportFee(bean);
			}else if(bean.getChangeType()==3 && bean.getIsChangeReason() == 1
					&& bean.getWaybillInfoTempVo().isSameProv()){
				//转寄退回 费用重置
				setChangeFeeBack(bean);
				bean.setChangeType(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage()) ;
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod().getValueCode())) {
			isBeBebt(bean);// 判断是否可以开月结
		}
		//设置预付到付金额编辑状态 --快递没有一般现付一般到付的要求
		setPrePayArriveEditState(bean);
		
		
	}
	
	/**
	 * 计算增值服务 -- 签收单回单 费用
	 * @param bean
	 * @author 272311-sangwenhao
	 * @date 2016-3-3
	 */
	private BigDecimal getReturnBillFee(WaybillInfoVo bean){
		BigDecimal returnBillFee = BigDecimal.ZERO ;
		if (bean.getReturnBillType() != null
				&& !WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())){

			// 获取产品价格主参数
			GuiQueryBillCalculateDto dto2 = Common.getProductPriceDtoCollection(bean);
			
			dto2.setWeight(BigDecimal.valueOf(0.5));
			dto2.setVolume(BigDecimal.ZERO);
			dto2.setCustomerCode("");
			dto2.setIsSelfPickUp(FossConstants.NO);
			// 根据DMANA-4938修改，标准快递和3.60特惠件以及商务专递的原件签单返回计费统一按照标准快递首重收费
			if (ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(dto2.getProductCode()) ||
					WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(dto2.getProductCode())	) {
				dto2.setProductCode(ExpWaybillConstants.PACKAGE);
			}
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos2 = waybillService.queryGuiExpressBillPrice(dto2);
			if (guiResultBillCalculateDtos2 != null && guiResultBillCalculateDtos2.size() > 0) {
				GuiResultBillCalculateDto qsDto = guiResultBillCalculateDtos2.get(0);
				if (qsDto != null && qsDto.getCaculateFee() != null) {
					QueryBillCacilateValueAddDto dto = Common.getQueryOtherChargeParam(bean, bean.getReturnBillType().getValueCode());
					List<ValueAddDto> list = waybillService.queryValueAddPriceList(dto);
					//获取签收单回单 的 折扣优惠
					if (CollectionUtils.isNotEmpty(list)) {
						for (ValueAddDto addDto : list) {
							if (PriceEntityConstants.PRICING_CODE_QS.equals(addDto.getPriceEntityCode())) {
								// 优惠方案
								List<PriceDiscountDto> disDto = addDto.getDiscountPrograms();
								if (CollectionUtils.isNotEmpty(disDto)) {
									PriceDiscountDto pdto = disDto.get(0);
									// z折扣费率
									BigDecimal discountRate = pdto.getDiscountRate();
									if (discountRate != null) {
										// 折扣后费用
										BigDecimal fee = qsDto.getCaculateFee().multiply(discountRate);
										BigDecimal reduceFee = qsDto.getCaculateFee().subtract(fee);
										pdto.setReduceFee(reduceFee);
										qsDto.setCaculateFee(fee.setScale(0,BigDecimal.ROUND_HALF_UP));
									}
								}
//								ExpCommon.setReturnBillDiscount(
//										addDto.getDiscountPrograms(), ui, bean);
								break;
							}//if end
				}//for end
			}
		  }
		}//if end
		if (guiResultBillCalculateDtos2 != null&& guiResultBillCalculateDtos2.size() > 0) {
			GuiResultBillCalculateDto dto3 = guiResultBillCalculateDtos2.get(0);
			if (dto3 != null && dto3.getCaculateFee() != null) {
				returnBillFee= dto3.getCaculateFee();
			}
		}//if end
	 }//if end
		return returnBillFee	;		
	}
	
	/**
	 * 在其他面板中 设置签收单返单费用
	 * @param bean
	 * @param tmp_deci
	 * @author 272311-sangwenhao
	 * @date 2016-3-10
	 */
	private void setReturnBillCharge(WaybillInfoVo bean,BigDecimal tmp_deci){
		
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();	
		if (data == null || data.isEmpty()) {
			   data = new ArrayList<OtherChargeVo>();
		}
		//更新签收单费用(更改目的站后产生的费用) 项
		OtherChargeVo otherVo = getOtherCharge(PriceEntityConstants.PRICING_CODE_QS);
		if(otherVo == null){
			otherVo = new OtherChargeVo();
			otherVo.setCode(PriceEntityConstants.PRICING_CODE_QS);
			otherVo.setChargeName("签收回单");
		}
		BigDecimal originReturnFee = getReturnBillFee(ui.getOriginWaybill()) ;
		String moeny = StringUtils.defaultIfBlank(otherVo.getMoney(),originReturnFee.toPlainString() ) ;
		BigDecimal big_moeny = BigDecimal.valueOf(Double.valueOf(moeny));
		//保存原始值
		WaybillInfoTempVo waybillInfoTempVo = bean.getWaybillInfoTempVo() ;
		if(waybillInfoTempVo == null){
			waybillInfoTempVo = new WaybillInfoTempVo();
		}
		BigDecimal returnBillFeeOrg = waybillInfoTempVo.getReturnBillFee();
		if(returnBillFeeOrg==null){
			returnBillFeeOrg = big_moeny ;
		}
		//更改后的 返单费用 与 更改前的返单费用取 最大值		
		if(tmp_deci.compareTo(returnBillFeeOrg) == 1){//比较更改目的站前后  签收单回单 费用大小 
			BigDecimal tmp = tmp_deci.subtract(returnBillFeeOrg).setScale(2, BigDecimal.ROUND_HALF_UP);
			waybillInfoTempVo.setReturnBillFee(tmp) ;
			otherVo.setMoney(tmp.toPlainString());
		}else{
			BigDecimal tmp = tmp_deci.subtract(returnBillFeeOrg).setScale(2, BigDecimal.ROUND_HALF_UP);
			waybillInfoTempVo.setReturnBillFee(tmp) ;
			otherVo.setMoney(tmp.toPlainString());
		}
		addOtherCharge(data, otherVo, bean);
		ui.incrementPanel.setChangeDetail(data);
	}
	
	/**
	 * 转寄退回 费用重置
	 * @param bean
	 * @author 272311-sangwenhao
	 * @date 2016-3-1
	 */
	private void setChangeFeeBack(WaybillInfoVo bean){
		WaybillInfoTempVo waybillInfoTempVo = bean.getWaybillInfoTempVo();
		if(waybillInfoTempVo != null){
			//使增加的金额复原
			BigDecimal transferFee = waybillInfoTempVo.getTransferFee() ;
			if(transferFee!=null){
				JXTable otherTable = ui.incrementPanel.getTblOther();
				WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
				List<OtherChargeVo> data = model.getData();
				if(data!=null && !data.isEmpty()){
					for(int i = 0; i < data.size(); i++){
						OtherChargeVo vo = data.get(i);
						if(vo.getCode().equals(PriceEntityConstants.PRICING_CODE_ZZ)){
							String moeny = StringUtils.defaultIfBlank(vo.getMoney(), "0") ;
							BigDecimal big_moeny = BigDecimal.valueOf(Double.valueOf(moeny));
							BigDecimal mon = big_moeny.subtract(transferFee).setScale(0, BigDecimal.ROUND_HALF_UP) ;
							vo.setMoney(mon.toPlainString());
							// 累计其他费用合计
							otherSum(data,bean);
							if(mon.compareTo(BigDecimal.ZERO)==0){
								data.remove(i);
							}
						}/*else if(vo.getCode().equals(PriceEntityConstants.PRICING_CODE_QS)){
							WaybillInfoTempVo tmpVo = bean.getWaybillInfoTempVo() ;
							if(tmpVo != null && tmpVo.getReturnBillFee()!=null ){
								String moeny = StringUtils.defaultIfBlank(vo.getMoney(), "0") ;
								BigDecimal big_moeny = BigDecimal.valueOf(Double.valueOf(moeny));
								BigDecimal mon = big_moeny.subtract(tmpVo.getReturnBillFee()).setScale(0, BigDecimal.ROUND_HALF_UP) ;
								vo.setMoney(mon.toPlainString());
								// 累计其他费用合计
								otherSum(data,bean);
							}
						}*/
					}
					ui.incrementPanel.setChangeDetail(data);
				}
			}
		}
	}
	
	/**
	 * 
	 * 设置其他费用到运单开单界面的其他费用表格中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午08:41:48
	 */
	private void setOtherCharge(WaybillInfoVo bean,BigDecimal tmp_deci) {
		
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (data == null || data.isEmpty()) {
		   data = new ArrayList<OtherChargeVo>();
		}
		//添加 中转费(更改目的站后产生的费用) 项
		OtherChargeVo otherVo = getOtherCharge(PriceEntityConstants.PRICING_CODE_ZZ);
		if(otherVo != null){
			String moeny = StringUtils.defaultIfBlank(otherVo.getMoney(), "0.0") ;
			BigDecimal big_moeny = BigDecimal.valueOf(Double.valueOf(moeny));
			BigDecimal mon = big_moeny.add(tmp_deci).setScale(0, BigDecimal.ROUND_HALF_UP) ;
			otherVo.setMoney(mon.toPlainString());
			addOtherCharge(data, otherVo, bean);
		}else{
			PriceEntity priceEntity = waybillService.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_ZZ);
			if(priceEntity==null){
				throw new  BusinessException("价格条目，中转费用信息为空");
			}
			otherVo = new OtherChargeVo();
			otherVo.setCode(PriceEntityConstants.PRICING_CODE_ZZ);
			otherVo.setChargeName("中转费");
			otherVo.setMoney(tmp_deci.toPlainString());
			otherVo.setDescrition(priceEntity.getName()) ;
			otherVo.setId(priceEntity.getId());
//			data.add(otherVo);
			addOtherCharge(data, otherVo, bean);
		}
		ui.incrementPanel.setChangeDetail(data);
	}
	
	/**
	 * 根据传入 其他费用编码 查询 当前 其他费用面板 中是否包含此项费用
	 * @param code 其他费用编码
	 * @return 其他费用 对象
	 */
	private OtherChargeVo getOtherCharge(String code) {
		//获取其他费用
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge oldmodel = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> olddata = oldmodel.getData();
		if(olddata!=null&&!olddata.isEmpty()){
			for(OtherChargeVo vo:olddata ){
				if(vo.getCode().equals(code)){
					return vo;
				}
			}
		}
		return null ;
	}
	
	/**
	 * 比较是否存在当前选的费用，不存在则添加费用到表格中，并且进行其他费用合计
	 * @param data 原数据列表
	 * @param newOtherCharge 新其他费用项
	 * @param bean
	 * @author 272311-sangwenhao
	 * @date 2016-2-22
	 */
	private void addOtherCharge(List<OtherChargeVo> data, OtherChargeVo newOtherCharge, WaybillInfoVo bean) {
		boolean bool = true;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				// 比较费用名称/编码，判断是否存在重复的返单费用
				if (otherVo.getCode().equals(newOtherCharge.getCode())) {
					bool = false;
					data.remove(i);
					data.add(i, newOtherCharge);
					break;
				}
			}
		}
		// 如果选择的其他费用不存在，则直接添加
		if (bool) {
			if(data != null){
				data.add(newOtherCharge);
			}
		} 
		// 累计其他费用合计
		otherSum(data,bean);
	}
	
	/**
	 * 其他费用合计
	 * @param data 其他费用列表
	 * @param bean
	 */
	private void otherSum(List<OtherChargeVo> data,WaybillInfoVo bean)
	{
		BigDecimal otherCharge = BigDecimal.ZERO;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				otherCharge = otherCharge.add(new BigDecimal(otherVo.getMoney()));
			}
		}
		bean.setOtherFee(otherCharge);
		bean.setOtherFeeCanvas(otherCharge.toString());
	}
	
	/**
	 * 
	 * 获取GuiResultBillCalculateDto
	 * 
	 * @param dtos
	 * @param valueAddType
	 * @param subType
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-4-16 下午7:43:29
	 */
	private GuiResultBillCalculateDto getGuiResultBillCalculateDto(
			List<GuiResultBillCalculateDto> dtos, String valueAddType,
			String subType) {

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto
						.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {

				if (valueAddType.equals(guiResultBillCalculateDto
						.getPriceEntryCode())
						&& subType.equals(guiResultBillCalculateDto
								.getSubType())) {
					return guiResultBillCalculateDto;
				}
			}

		}

		return null;
	}
	
	/**
	 * 获取其他费用查询参数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-22 下午4:03:05
	 */
	private GuiQueryBillCalculateSubDto getQueryOtherChargeParam(
			WaybillPanelVo bean) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();

		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		return queryDto;
	}
	
	private GuiQueryBillCalculateSubDto getOtherChargeDataCollection(
			WaybillPanelVo bean) {
		return getQueryOtherChargeParam(bean);
	}
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getQueryParam(WaybillPanelVo bean,
			String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	
	/**
	 * 
	 * 获取代收货款费率
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午02:23:42
	 */
	private GuiQueryBillCalculateSubDto getCodCollection(WaybillPanelVo bean) {

		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0
				&& bean.getRefundType() != null) {
			return getQueryParam(bean, PriceEntityConstants.PRICING_CODE_HK,
					bean.getCodAmount(), bean.getRefundType().getValueCode());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * 获取保价费以及最高保额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 下午01:51:03
	 */
	private GuiQueryBillCalculateSubDto getInsuranceCollection(
			WaybillPanelVo bean) {
		return getInsuranceParam(bean);

	}
	
	/**
	 * 
	 * 获取查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getInsuranceParam(WaybillPanelVo bean) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(bean.getInsuranceAmount());// 原始费用
		queryDto.setSubType(bean.getVirtualCode());// 限保物品才会具备的虚拟code
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setWoodenVolume(null);// 木架体积
		return queryDto;
	}
	
	//-----------------------------------end-------------------- 
	
	/**
	 * 根据发货客户编码 调用crm接口查询客户合同信息 不验证客户是否是部门绑定客户
	 * @param bean
	 * @return 合同信息
	 * @author 272311-sangwenhao
	 * @date 2016-2-22
	 */
	public PreferentialInfoDto queryPreferentialInfo(WaybillInfoVo bean){
		String productCodeTemp = ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		PreferentialInfoDto preferentialInfoDto = BaseDataServiceFactory
				.getBaseDataService().queryPreferentialInfo(
						bean.getDeliveryCustomerCode(), null, productCodeTemp);
		if (preferentialInfoDto != null) {
			return preferentialInfoDto;
		}
		return null ;
	}
	
	/**
	 * gyk 调用crm接口查询客户合同信息 同时验证客户是否是部门绑定客户
	 * 
	 * @param bean
	 * @return
	 */
	/*private PreferentialInfoDto queryPreferentialInfo(WaybillPanelVo bean) {
		String productCodeTemp = ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		PreferentialInfoDto preferentialInfoDto = BaseDataServiceFactory
				.getBaseDataService().queryPreferentialInfo(
						bean.getDeliveryCustomerCode(), null, productCodeTemp);
		boolean isDiscount = false;
		if (preferentialInfoDto != null) {
			String unicode = baseDataService.queryUnifiedCodeByCode(bean
					.getReceiveOrgCode());
			List<String> list = new ArrayList<String>();
			if (unicode != null) {
				list.add(unicode);
			}
			// 验证客户是否是部门绑定客户
			List<BargainAppOrgEntity> bargainAppOrgEntities = baseDataService
					.queryAppOrgByBargainCrmId(
							preferentialInfoDto.getCusBargainId(), list);
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
				for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
					BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
							.get(i);
					String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = baseDataService
							.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
					if (orgAdministrativeInfoEntity == null) {
						continue;
					}
					String orgCode = orgAdministrativeInfoEntity.getCode();
					if (StringUtils.equals(bean.getReceiveOrgCode(), orgCode)) {
						isDiscount = true;
					}
				}
			}
		}
		if (isDiscount) {
			return preferentialInfoDto;
		} else {
			return null;
		}
	}*/
	
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
	private boolean setServiceChargeState(WaybillPanelVo bean) {

		// 5. 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改。
		// （只限制配载类型为专线的，包括月结）；
		/**
		 * 9. 2012年12月1日 (以后)开业的部门不能开装卸费 10.如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		 * 11.是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）。
		 * 12.装卸费上限由增值服务配置基础资料实现（在产品API中体现）。
		 */

		boolean serviceChargeEnabled = false;

		
		
//		// 是否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
//		serviceChargeEnabled = departPropertyServiceFee(bean);
//
//		// 系统如果是运费最低一票，要求装卸费=0，即装卸费不允许修改
//		if (serviceChargeEnabled) {
//			serviceChargeEnabled = lowestServiceFee(bean);
//		}
//
//		// 2012年12月1日 (以后)开业的部门不能开装卸费
//
//		// 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
//		// 月发越送 == 月结
//		if (serviceChargeEnabled) {
//			serviceChargeEnabled = channelServiceFee(bean);
//		}
//		
//		if (serviceChargeEnabled) {
//		// 月发月送允许修改装卸费
//			if (StringUtils.isNotEmpty(bean.getPreferentialType())) {
//				if (bean.getPreferentialType()
//						.equals(DictionaryValueConstants.CRM_PREFERENTIAL_TYPE_MONTH_SEND)) {
//					serviceChargeEnabled = true;
//				}
//
//			}
//		}

//		//快递只允许对应部门的月结客户开装卸费
//		CusBargainVo vo=new CusBargainVo();
//		vo.setExpayway(WaybillConstants.MONTH_END);
//		vo.setCustomerCode(bean.getDeliveryCustomerCode());
//		vo.setBillDate(new Date());
//		vo.setBillOrgCode(bean.getReceiveOrgCode());
//		boolean  isOk = waybillService.isCanPaidMethodExp(vo);
//		if(!isOk){
//			serviceChargeEnabled = false ;
//			bean.setServiceFee(BigDecimal.ZERO);
//			bean.setServiceFeeCanvas(String.valueOf('0'));
//		}else{
//			serviceChargeEnabled = true;
//		}
//				
//		ui.incrementPanel.getTxtServiceCharge().setEnabled(serviceChargeEnabled);
//		if (!serviceChargeEnabled) {
//			bean.setServiceFee(BigDecimal.ZERO);
//		}
		return serviceChargeEnabled;
	}
	
	/**
	 * 否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
	 * @param bean
	 * @return
	 */
	private boolean departPropertyServiceFee(WaybillPanelVo bean) {
	
		// 收货部门
		String orgCode = bean.getReceiveOrgCode();
		
		// org code is not null;
		if(StringUtils.isNotEmpty(orgCode)){
			// 根据编码查询
			SaleDepartmentEntity entity = waybillService.querySaleDeptByCode(orgCode);
			if(entity!=null){
				//不允许开装卸费
				if(!FossConstants.YES.equals(entity.getCanPayServiceFee())){
					return false;
				}
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
				bean.setServiceFee(BigDecimal.ZERO);
				serviceChargeEnabled = false;
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
			if (dto != null) {
				if (!dto.isCanUse()) {
					// 不能使用优惠券的原因
					MsgBox.showInfo(dto.getCanNotUseReason());
					bean.setPromotionsCode(null);
				} else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						/**
						 * 设置优惠券费用
						 */
						bean.setCouponFree(dto.getCouponAmount());
						/**
						 * 设置优惠券返回实体
						 */
						bean.setCouponInfoDto(couponInfoDto);
					} else {
						bean.setPromotionsCode("");
					}
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
		ui.billingPayPanel.getTxtAdvancesMoney().setEnabled(false);
		ui.billingPayPanel.getTxtArrivePayment().setEnabled(false);
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

		// 校验提货网点重量、体积上限
		validateVW(bean);
		
		Common.validateExpPayMethod(bean);
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
		
		//验证重量与提货方式
		validateWeightDeliveryMode(bean);
		if(bean.getIsChangeReason()==1||bean.getIsChangeReason()==2){
		}else{
			//检查付款方式只能是现金或者到付
			validatePaidMehtod(bean);
			}
		//如果德邦客户  则不能做修改 到付现付 只能作废或者终止
		validateDepponCustomer(bean);		
		
		//校验快递优惠活动与crm推广活动是否同时存在
//		validateSpecialAndActive(bean);
		CommonUtils.validateLinkMan(bean);
		// 返货开单不允许开现付
		if(StringUtils.isNotEmpty(bean.getWaybillNo())){
			WaybillExpressEntity entity=waybillService.queryWaybillByWaybillNo(bean.getWaybillNo());
			if(entity!=null){
				if (bean.getPaidMethod()!=null && WaybillConstants.CASH_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					throw new WaybillValidateException("返货开单不允许开现付");
	}
			}
		}

	}
	
	/**
	 * 校验快递优惠活动与crm推广活动是否同时存在
	 * @创建时间 2014-5-21 下午12:31:16   
	 * @创建人： WangQianJin
	 */
	private void validateSpecialAndActive(WaybillInfoVo bean){
		DataDictionaryValueVo specialOffer=bean.getSpecialOffer();
		DataDictionaryValueVo activeInfo=bean.getActiveInfo();
		if(specialOffer!=null && activeInfo!=null
				&& StringUtils.isNotEmpty(specialOffer.getValueCode()) && StringUtils.isNotEmpty(activeInfo.getValueCode())){
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.specialAndActive.isnotexist"));
		}
	}
	
	/**
	 * 如果德邦客户  则不能做修改 到付现付 只能作废或者终止
	 * @param bean
	 */
	private void validateDepponCustomer(WaybillInfoVo bean) {
		if(bean!=null && WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())){
			
			if(bean.getPaidMethod()!=null && ui.getOriginWaybill().getPaidMethod()!=null  
					&&bean.getPaidMethod().getValueCode()!=null && 
					!bean.getPaidMethod().getValueCode().equals( ui.getOriginWaybill().getPaidMethod().getValueCode())
					)
			throw new WaybillValidateException(i18n.get("pickup.changingexp.validateDepponCustomer"));
			
		}
	}

	//检查付款方式只能是现金或者到付
	public void validatePaidMehtod(WaybillInfoVo bean){
		//不是到付也不是现金，不让修改成其它付款方式
		if (!WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod().getValueCode()) 
				&& !WaybillConstants.CASH_PAYMENT.equals(bean.getPaidMethod().getValueCode())
				//若付款方式未改，则不提示。（KDTE-6288出发部门无法更改代收）
				&& !bean.getPaidMethod().getValueCode().equals(ui.getOriginWaybill().getPaidMethod().getValueCode())) {
			ComboBoxModel c = ui.billingPayPanel.getCombPaymentMode().getModel();
			for (int i = 0; i < c.getSize(); i++) {
				DataDictionaryValueVo v = (DataDictionaryValueVo) c.getElementAt(i);
				if (v.getValueCode().equals(ui.getOriginWaybill().getPaidMethod().getValueCode())) {
					c.setSelectedItem(v);
					bean.setPaidMethod(v);
				}
			}
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillInfoBindingListener.msgBox.nochangepaid"));
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
//			CommonUtils.validatePickupOrgCode(vo);
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
//		//货物总重量
//    	BigDecimal goodsWeightTotal=bean.getGoodsWeightTotal();
//    	Integer goodsNum=bean.getGoodsQtyTotal();
//    	/**
//    	 * 单件重量大于50kg
//    	 */
//    	if (goodsWeightTotal != null && goodsNum!=null && goodsNum != 0 && (Double.parseDouble(goodsWeightTotal+"")/goodsNum.intValue()) > FIFTY) {
//    		/**
//    		 * 如果提货方式是“送货上楼”
//    		 */
//    		if(bean.getFinalReceiveMethod()!=null && WaybillConstants.DELIVER_UP.equals(bean.getFinalReceiveMethod().getValueCode())){
//    			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.50kgNotDoorToDoor"));
//    		}
//    	}	
    	
    	
    	if (ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(bean.getOrderChannel())// 淘宝
    			|| ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(bean.getOrderChannel())// 阿里巴巴
    			|| ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG.equals(bean.getOrderChannel())) {// 淘宝商城
    		if(bean.getCodAmount()!=null 
    				&&  ui.getOriginWaybill().getCodAmount() != null
    				&&  bean.getCodAmount().doubleValue()!= ui.getOriginWaybill().getCodAmount().doubleValue()){
    			// 淘宝类订单（淘宝、阿里巴巴、淘宝商场）不能修改代收货款
    			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.expWaybillBindingListener.taoOrderValidate"));
    			
    			
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
	 * 验证重量、体积、件数不能为默认值0
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午08:16:30
	 */
	private void validateWeightVolume(WaybillPanelVo bean) {
		/*if (bean.getGoodsQtyTotal().intValue() == 0) {
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

		if (bean.getGoodsVolumeTotal() != null && !"0".equals(bean.getGoodsVolumeTotal().toString()) && bean.getGoodsWeightTotal() != null && !"0".equals(bean.getGoodsWeightTotal().toString())) {
			boolean bool = waybillService.isWeightByVolume(bean.getGoodsWeightTotal().toString(), bean.getGoodsVolumeTotal().toString());
			if (!bool) {
				if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(ui, i18n.get("foss.gui.creating.calculateAction.msgBox.confirmWeightVolume"),
						i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"), JOptionPane.YES_NO_OPTION)) {
					throw new WaybillValidateException("");
				}
			}
		}*/
		if (bean.getGoodsQtyTotal() == null
				|| bean.getGoodsQtyTotal().intValue() == 0) {
			ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullGoodsQtyTotal"));
		}

		// 件数不能大于1

	/*	if (bean.getGoodsQtyTotal().intValue() != 1) {
					ExpWaybillPanelVo bean2 = (ExpWaybillPanelVo)bean;
			if(bean.getReturnBillType()!=null){
				if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO_NAME.equals(bean.getReturnBillType().getValueName()) ||
						WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE_NAME.equals(bean.getReturnBillType().getValueName())	){
					//开返货单时  不需要验证部门是否能够开一票多件 
					//当不是返货单 仍然需要走原来的判断逻辑(即下面的判断逻辑)
				}else{
					SaleDepartmentEntity entitry = waybillService.querySaleDeptByCode(bean.getCreateOrgCode());
					//liyongfei 查询创建部门是否有一件多票的属性
					if(FossConstants.YES.equals(entitry.getCanExpressOneMany())){
				
					}else{						ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
						throw new WaybillValidateException(
								i18n.get("该开单部门不能开一票多件"));
					}
					
					// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件bean.getCustomerPickupOrgName().contains("远郊")
					//||远郊判断在后判断
					if (bean.getCustomerPickupOrgName() != null) {
						if ( bean.getCustomerPickupOrgName().contains(
										"出发")) {
							ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
							throw new WaybillValidateException(
									i18n.get("该目的站不能开一票多件"));
						}
					}
				}
				
			}else{
				 SaleDepartmentEntity entitry = waybillService.querySaleDeptByCode(bean.getCreateOrgCode());
				//liyongfei 查询创建部门是否有一件多票的属性
				if(FossConstants.YES.equals(entitry.getCanExpressOneMany())){
					
				}else{					ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
					throw new WaybillValidateException(
							i18n.get("该开单部门不能开一票多件"));
				}
				// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件// liyongfei 目的站包含“远郊、出发”的也不能开启一票多件bean.getCustomerPickupOrgName().contains("远郊")
				//||远郊判断在后判断
				if (bean.getCustomerPickupOrgName() != null) {
					if (bean.getCustomerPickupOrgName().contains("出发")) {
						ui.cargoInfoPanel.getTxtTotalPiece().requestFocus();
						throw new WaybillValidateException(
								i18n.get("该目的站不能开一票多件"));
					}
				}
			}
		}*/

		if (bean.getGoodsWeightTotal() == null
				|| bean.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
			ui.cargoInfoPanel.getTxtWeight().requestFocus();
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullGoodsWeightTotal"));
		}

		// if (bean.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
		// ui.cargoInfoPanel.getTxtVolume().requestFocus();
		// throw new
		// WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSVOLUMETOTAL));
		// }
		/**
		 * DN201603280022 需求,子母件优化
		 * 在体积为0时也能发更改，因此，暂时去掉此判断
		 * @author 305082
		 */
		//返回结果为false时非子母件，因此，取反
		Boolean flag = waybillService.queryISPICPACKAGEByWaybillNo(bean.getWaybillNo());
		//当不是子母件，并且体积为0时不能发更改，不影响以前逻辑
		if (!flag&&bean.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
			ui.cargoInfoPanel.getTxtVolume().requestFocus();
			throw new WaybillValidateException(i18n.get(WaybillValidateException.NULL_GOODSVOLUMETOTAL));
		}

		// zxy 20140111 DEFECT-1440 start 添加:增加空校验,否则有些地方会报错(不清楚之前为什么把这个体积校验去掉)
		if (bean.getGoodsVolumeTotal() == null) {
			ui.cargoInfoPanel.getTxtVolume().requestFocus();
			throw new WaybillValidateException(
					i18n.get("foss.gui.creating.calculateAction.exception.nullGoodsVolumeTotal1"));
		}
		// zxy 20140111 DEFECT-1440 end
		// 添加:增加空校验,否则有些地方会报错(不清楚之前为什么在descriptor里把这个体积校验去掉)


		// DEFECT-2646,快递允许体积为0
		if (bean.getGoodsVolumeTotal().intValue() != 0) {
			if (bean.getGoodsVolumeTotal() != null
					&& !"0".equals(bean.getGoodsVolumeTotal().toString())
					&& bean.getGoodsWeightTotal() != null
					&& !"0".equals(bean.getGoodsWeightTotal().toString())) {
				boolean bool = waybillService.isWeightByVolume(bean
						.getGoodsWeightTotal().toString(), bean
						.getGoodsVolumeTotal().toString());
				if (!bool) {
					if (JOptionPane.YES_OPTION != JOptionPane
							.showConfirmDialog(
									ui,
									i18n.get("foss.gui.creating.calculateAction.msgBox.confirmWeightVolume"),
									i18n.get("foss.gui.creating.calculateAction.msgBox.Prompt"),
									JOptionPane.YES_NO_OPTION)) {
						throw new WaybillValidateException("");
					}
				}
			}
		}

		/**
		 * ISSUE-4421　毛重小于等于50kg，体积小于等于0.3
		 * DMANA-805　毛重小于等于50kg*件数，体积小于等于0.30*件数
		 */
		BigDecimal qtyWeightUpperLimit = BigDecimal.valueOf(WEIGHT_UPPER_LIMIT)
				.multiply(BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		BigDecimal qtyVolumeUpperLimit = BigDecimal.valueOf(VOLUME_UPPER_LIMIT)
				.multiply(BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		// 获取快递公斤段分割点
		ConfigurationParamsEntity EXPRESS_WEIGHT_CUT = getConfigurationParamsByEntity(ConfigurationParamsConstants.PKP_EXPRESS_WEIGHT_CUT);
		BigDecimal qtyWeightCut = BigDecimal.valueOf(
				Double.valueOf(EXPRESS_WEIGHT_CUT.getConfValue())).multiply(
				BigDecimal.valueOf(bean.getGoodsQtyTotal()));
		// 获取快递公斤段分割点时间节点
		ConfigurationParamsEntity EXPRESS_WEIGHT_CUT_TIME = getConfigurationParamsByEntity(ConfigurationParamsConstants.PKP_EXPRESS_WEIGHT_CUT_TIME);
		/**
		 * @author 200945 返回限制的3.60特惠件判断的重量 需改时间：2014-11-28
		 *         修改内容：将代码2016以及2019行的bean
		 *         .getGoodsWeightTotal()修改为：judgeWeight变量;该变量的取值参考方法的注释；
		 */
		BigDecimal judgeWeight = validateExpressTo360Express(bean);
		if (null != EXPRESS_WEIGHT_CUT_TIME) {
			if (StringUtils.isNotEmpty(EXPRESS_WEIGHT_CUT_TIME.getConfValue())) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// 时间节点
				Date weightCutDate = null;
				try {
					weightCutDate = sdf.parse(EXPRESS_WEIGHT_CUT_TIME
							.getConfValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (weightCutDate != null
						&& bean.getBillTime().after(weightCutDate)) {
					if ((WaybillConstants.PACKAGE.equals(bean.getProductCode()
							.getCode()))
							&& judgeWeight != null
							&& qtyWeightCut.compareTo(judgeWeight) <= 0) {
						throw new WaybillValidateException(
								i18n.get(
										"foss.gui.creatingexp.expCalculateAction.qtyBigWeightCut",
										new Object[] { EXPRESS_WEIGHT_CUT
												.getConfValue() }));
		              }
					if (WaybillConstants.ROUND_COUPON_PACKAGE.equals(bean
							.getProductCode().getCode())
							&& judgeWeight != null
							&& qtyWeightCut.compareTo(judgeWeight) > 0) {
						throw new WaybillValidateException(
								i18n.get(
										"foss.gui.creatingexp.expCalculateAction.qtySmallWeightCut",
										new Object[] { EXPRESS_WEIGHT_CUT
												.getConfValue() }));
		             }
		        }
	        }
		}
		if ((bean.getGoodsWeightTotal() != null && qtyWeightUpperLimit
				.compareTo(bean.getGoodsWeightTotal()) < 0) // zxy 20140103
				// 增加bean.getGoodsWeightTotal()
				// !=null判断
				|| (bean.getGoodsVolumeTotal() != null && qtyVolumeUpperLimit
				.compareTo(bean.getGoodsVolumeTotal()) < 0)) {
			throw new WaybillValidateException(
					i18n.get("foss.gui.changingexp.calculateAction.exception.limitWeightVolume"));
		}
		
	}

	
	/**
	 * 需求号：DMANA-5535： 快递开单计费重量3kg（含3kg）以上限制3.60特惠件，3kg以下限制标准快递 业务逻辑：
	 * 3.60特惠件的3kg区分以计费重量为标准，即体积重量和实际重量两者取大作为判断标准
	 * 
	 * @author 200945 - wutao
	 * @param bean
	 * @return
	 * @date 2014-11-28
	 */
	private BigDecimal validateExpressTo360Express(WaybillPanelVo bean) {
		// 货物总重量
		BigDecimal weightTotal = bean.getGoodsWeightTotal();
		// 货物总体积
		BigDecimal volumeTotal = bean.getGoodsVolumeTotal();
		// 如果体积为【null】,则将体积转化为0
		if (null == volumeTotal) {
			volumeTotal = BigDecimal.ZERO;
		}
		String prarentProductCode = "";
		if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE
				.equals(bean.getProductCode().getCode())
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE
						.equals(bean.getProductCode().getCode())
						|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT
						.equals(bean.getProductCode().getCode())) {
			prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		}
		BigDecimal volumeWeight = ExpCommonUtils.validateWeightBubbleRate(
				bean.getDeliveryCustomerCode(), null, prarentProductCode,
				volumeTotal, bean.getReceiveOrgCode());
		// 判断标准：计费重量和实际总量相比较，取较大的值
		if (volumeWeight.compareTo(weightTotal) < 0) {
			return weightTotal;
		} else {
			return volumeWeight;
		}

	}

	
	/**
	 * 远程获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	private ConfigurationParamsEntity getConfigurationParamsByEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return waybillService.queryConfigurationParamsByEntity(
				DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type,
				FossConstants.ROOT_ORG_CODE);

	
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
			LOG.error("包装信息不允许为空！");
			//抛出异常信息
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.nullPackinginfo"));
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
	}
	
	/**
	 * 
	 * 验证合票方式如果是单独开单，则只能选机场自提
	 * 
	 * @author WangQianJin
	 * @date 2013-07-08 下午03:21:42
	 */
	private void validateReceiveMethod(WaybillInfoVo bean) {		
		/**
		 * 判断合票方式和运输性质和提货方式是否为空
		 */
		if (bean.getFreightMethod() != null && bean.getProductCode() != null && bean.getReceiveMethod()!=null) {
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

		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod().getValueCode())) {
			CusBargainVo vo=new CusBargainVo();
			vo.setExpayway(WaybillConstants.MONTH_END);
			vo.setCustomerCode(bean.getDeliveryCustomerCode());
			vo.setBillDate(bean.getBillTime());
			vo.setBillOrgCode(bean.getReceiveOrgCode());
			boolean  isOk = waybillService.isCanPaidMethodExp(vo);
			if(!isOk){
				ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.listener.Waybill.exception.NocanPaidMethod"));
			}
			
			// 判断客户是否月结
//			if (bean.getChargeMode() == null || !bean.getChargeMode()) {
//				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.notMonthPayment"));
//			}
		}
		//提货方式为空
		if(bean.getFinalReceiveMethod()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.transportInfoPanel.pickMode.label.isNull"));
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
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.noArrivedPay"));
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
		
		/**
		 * 注释掉该提示，参见DEFECT-506
		 */
//		//有到付付款方式不能为网上支付
//		if(bean.getToPayAmount().compareTo(BigDecimal.ZERO)>0){
//			if(WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod().getValueCode())){
//				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.noOnline_payment"));
//			}
//			
//		}
		

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
		
		//校验收货联系人的电话
		if(ui.consigneePanel.getTxtConsigneePhone().isEnabled()){
			String result = new WaybillInfoDescriptor(ui).validateReceiveCustomerPhone(bean.getReceiveCustomerPhone());
			if (!WaybillConstants.SUCCESS.equals(result)) {
				ui.consigneePanel.getTxtConsigneePhone().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.phoneNo.rule"));
		    }
			
		}
		// 校验发货联系人的电话
		if (ui.consignerPanel.getTxtConsignerPhone().isEnabled()) {
			String result = new WaybillInfoDescriptor(ui).validateDeliveryCustomerPhone(bean.getDeliveryCustomerPhone());
			if (!WaybillConstants.SUCCESS.equals(result)) {
				ui.consignerPanel.getTxtConsignerPhone().requestFocus();
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.phoneNo.rule"));
			}
		}
		
		if(ui.consignerPanel.getTxtConsignerArea().isEnabled()){
			// 原件签收单返回
			if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
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
				throw new WaybillValidateException(i18n.get("foss.gui.changingexp.transportInfoPanel.pickMode.label.isNull"));
			}
			String code = bean.getFinalReceiveMethod().getValueCode();
			if (WaybillConstants.DELIVER_NOUP.equals(code) 
					|| WaybillConstants.DELIVER_FREE.equals(code) 
					|| WaybillConstants.DELIVER_STORAGE.equals(code) 
					|| WaybillConstants.DELIVER_UP.equals(code)
					|| WaybillConstants.DELIVER_FREE_AIR.equals(code) 
					|| WaybillConstants.DELIVER_NOUP_AIR.equals(code) 
					|| WaybillConstants.DELIVER_UP_AIR.equals(code)
					|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
				JAddressFieldForExp jd = ui.consigneePanel.getTxtConsigneeArea();
				AddressFieldDto consigneeAddress = jd.getAddressFieldDto();
				if (StringUtils.isBlank(jd.getText())
						|| consigneeAddress == null
						|| StringUtils.isBlank(consigneeAddress.getProvinceId())
						|| StringUtils.isBlank(bean.getReceiveCustomerAddress())) {
					ui.consigneePanel.getTxtConsigneeArea().requestFocus();
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullConsigneeAddress"));
				}
			}
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
				Double doubleSingleBillLimitkg=intSingleBillLimitkg.doubleValue()/ NumberConstants.NUMBER_100;
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
				Double doubleSingleBillLimitvol=intSingleBillLimitvol.doubleValue()/ NumberConstants.NUMBER_100;
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(doubleSingleBillLimitvol);
				/**
				 * 判断单票体积是否大于0并且不是整车类型的情况下抛出异常
				 */
				if (goodsVolume.compareTo(singleBillLimitvol) > 0 && !codeType.equals(bean.getProductCode().getCode())) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.overSingleBillLimitvol"));
				}
			}
		}
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
		/**
		 * 条件1：如果提货网点没有变化
		 */
		if(originVo!=null && originVo.getCustomerPickupOrgCode()!=null && originVo.getCustomerPickupOrgCode().getCode()!=null){
			String oldOrgCode=originVo.getCustomerPickupOrgCode().getCode();
			if(finalOrg!=null && oldOrgCode.equals(finalOrg.getCode())){
				con1=true;
			}
		}		
		
		if(null != finalOrg){
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
	}

	/**
	 * 
	 * 校验目的站
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:26:27
	 */
	private void validateDistination(WaybillInfoVo bean) {
		
		//转寄退回的更改单必须改变目的站或收获地址
				if(1== bean.getIsChangeReason()|| 2==bean.getIsChangeReason()){
					if(bean.getOldReceiveOrgCode() ==bean.getReceiveOrgCode() && bean.getOldreceiveCustomerAddress() == bean.getReceiveCustomerAddress() ){	
						throw new WaybillValidateException(i18n.get("转寄退回的更改单必须改变目的站或收获地址"));
					}
				}
		if (bean.getFinalCustomerPickupOrgCode() == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullCustomerPickupOrg"));
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
		QueryBillCacilateDto queryDto = new QueryBillCacilateDto();
		// 查询产品价格
		if(!WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())){
			if (bean.getPickupToDoor()) {
				queryDto=Common.getQueryParam(bean, FossConstants.YES, Common.GBJ);
				productPrice = waybillService.queryProductPriceList(queryDto);
				if (productPrice == null) {
					queryDto=Common.getQueryParam(bean, FossConstants.NO, Common.GBJ);
					productPrice = waybillService.queryProductPriceList(queryDto);
				
					ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEnabled(true);
					ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEditable(true);
	
	                List<ValueAddDto> list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,false));
					
					if(list==null||list.isEmpty()){
						 list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,true));
					}
				}
			} else {
				queryDto=Common.getQueryParam(bean, FossConstants.NO, Common.GBJ);
				productPrice = waybillService.queryProductPriceList(queryDto);				
			}
			
			//开单版本取不到读当前版本
			if(productPrice == null){
	
				// 查询产品价格
				if (bean.getPickupToDoor()) {
					queryDto=Common.getNowQueryParam(bean, FossConstants.YES, Common.GBJ);
					productPrice = waybillService.queryProductPriceList(queryDto);
					if (productPrice == null) {
						queryDto=Common.getNowQueryParam(bean, FossConstants.NO, Common.GBJ);
						productPrice = waybillService.queryProductPriceList(queryDto);
					
						ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEnabled(true);
						ui.getWaybillInfoPanel().getIncrementPanel().getTxtPickUpCharge().setEditable(true);
	
						List<ValueAddDto> list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,false));
						
						if(list==null||list.isEmpty()){
							 list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,true));
						}
						ValueAddDto dto = null;
						//设置其他费用的折扣优惠
						if (list != null && !list.isEmpty()) {
							dto = list.get(0);
							setPickupFee(bean, dto);
							// 设置折扣优惠
	//						Common.setFavorableDiscount(dto.getDiscountPrograms(), bean);
						} else {
							setPickupFee(bean, null);
						}
					}
				} else {
					queryDto=Common.getNowQueryParam(bean, FossConstants.NO, Common.GBJ);
					productPrice = waybillService.queryProductPriceList(queryDto);
					
				}
			}
		}
		
		ProductPriceDto dto = null;
		
		if(WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())){
			productPrice = new ArrayList();
			ProductPriceDto dto1 = new ProductPriceDto();
			dto1.setActualFeeRate(BigDecimal.ZERO);
			dto1.setCaculateFee(BigDecimal.ZERO);
			dto1.setCaculateType("WEIGHT");
			dto1.setCentralizePickup(FossConstants.NO);
			dto1.setDiscountFee(BigDecimal.ZERO);
			productPrice.add(dto1);
		}
		
		
		if (productPrice == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		} else {
						
			// 第二次查询，只试用于签收单原件返回
			List<ProductPriceDto> productReturnPrice = null;

			// 是否为签收单原件返回
			if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())
					&& !WaybillConstants.DEPPON_CUSTOMER.equals(bean.getDeliveryCustomerCode())
					) {
				productReturnPrice = waybillService.queryProductPriceList(getQueryReturnParam(bean));
			}
			
			for (ProductPriceDto fee : productPrice) {
				if(fee.getCaculateFee() == null){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullTransportFee"));
				}
				
				BigDecimal transportFee = null;
				//保留 标准公布价运费
				BigDecimal standardTransportFee = fee.getCaculateFee();
				if(bean.isAccurateCost()){
					standardTransportFee=standardTransportFee.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
				}else{
					standardTransportFee=standardTransportFee.setScale(0,BigDecimal.ROUND_HALF_UP);// 四舍五入
				}
			
				// 是否为签收单原件返回
				if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
					if (CollectionUtils.isNotEmpty(productReturnPrice)) {
						
						//DEFECT-11243 发快递更改单，客户签收单原件返回的优惠不见了--begin
						List<ValueAddDto> list = waybillService
								.queryValueAddPriceList(Common
										.getQueryOtherChargeParam(bean, bean
												.getReturnBillType().getValueCode()));
						// 折扣优惠
						if (CollectionUtils.isNotEmpty(list)) {
							for (ValueAddDto addDto : list) {
								if (PriceEntityConstants.PRICING_CODE_QS
										.equals(addDto.getPriceEntityCode())) {
									// 优惠方案
									List<PriceDiscountDto> disDto = addDto
											.getDiscountPrograms();
									if (CollectionUtils.isNotEmpty(disDto)) {
										PriceDiscountDto pdto = disDto.get(0);
										// z折扣费率
										BigDecimal discountRate = pdto
												.getDiscountRate();
										if (discountRate != null) {
											// 折扣后费用
											BigDecimal pfee = productReturnPrice.get(0).getCaculateFee()
													.multiply(discountRate);
											BigDecimal reduceFee = productReturnPrice.get(0)
													.getCaculateFee().subtract(pfee);
											pdto.setReduceFee(reduceFee);
											productReturnPrice.get(0).setCaculateFee(pfee.setScale(0,
													BigDecimal.ROUND_HALF_UP));
										}
									}
									// 设置折扣优惠
									Common.setFavorableDiscount(addDto.getDiscountPrograms(),bean);
				
									//设置折扣值
									setDiscount(addDto.getDiscountPrograms(),bean);
									break;
								}

							}
						}
						
						//DEFECT-11243 发快递更改单，客户签收单原件返回的优惠不见了--end
						
						BigDecimal caculateReturn = productReturnPrice.get(0).getCaculateFee();
						if(bean.isAccurateCost()){
							transportFee = CommonUtils.defaultIfNull(fee.getCaculateFee()).add(caculateReturn).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
						}else{
							transportFee = CommonUtils.defaultIfNull(fee.getCaculateFee()).add(caculateReturn).setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
						}
						
					}
				} else {
					if(bean.isAccurateCost()){
					transportFee = fee.getCaculateFee().setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
					}else{
						transportFee = fee.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
					}
				}
				
			    
			    //如果是内部变更，且有返货记录，并且选择的不是第一条，价格计算至中转费中
			    if(WaybillRfcConstants.INSIDE_CHANGE.equals(bean.getRfcType().getValueCode())
			    		&&((bean.getReturnRecordList().size()> 0 || bean.getTransferRecordList().size()> 0))
			    		&& ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getSelectedRow()!=0){
			    	//第2行更新第一次中转费，第3行更新第二次中转费；依次类推
			    	int row = ui.getWaybillInfoPanel().getTransferPanel().getTransportRecordTabPanel().getTransferRecordPanel().getTable().getSelectedRow();
			    	if(row<0){
			    		return null;
			    	}
			    	// 设置运费
			    	bean.setRecordUnitPrice(fee.getActualFeeRate());
					// 设置费率
			    	bean.setRecordFee(CommonUtils.defaultIfNull(transportFee));
					// 设置计费类型（重量计费或者体积计费）
			    	DataDictionaryValueVo billType = new DataDictionaryValueVo();
			    	billType.setValueCode(fee.getCaculateType());
			    	bean.setRecordBillingType(billType);
			    	//修改中转费
			    	changeTransportOtherCharge(bean, row);
					dto = fee;
					//未计算公布价
					bean.setIsCalTraFee(FossConstants.NO);
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
			    	// 设置运费
					bean.setTransportFee(transportFee);
					
					// 设置费率
					if(fee.getActualFeeRate()!=null){
						bean.setUnitPrice(fee.getActualFeeRate().divide(new BigDecimal(NumberConstants.NUMBER_100), newScale, RoundingMode.HALF_EVEN));
					}else{
						//TODO
						if(transportFee!=null && bean.getBillWeight()!=null && bean.getBillWeight().doubleValue()>0){
							bean.setUnitPrice(transportFee.divide(bean.getGoodsWeightTotal(), newScale, RoundingMode.HALF_EVEN));
						}else if(transportFee!=null && bean.getGoodsWeightTotal()!=null && bean.getGoodsWeightTotal().doubleValue()>0){
							bean.setUnitPrice(transportFee.divide(bean.getGoodsWeightTotal(), newScale, RoundingMode.HALF_EVEN));
						}
						
					}
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
			  //保留 标准公布价运费
			    dto.setStandardTransportFee(standardTransportFee);
			}
		}
		return dto;
	}
	
	
	/**
	 * 获得折扣优惠信息
	 * @author WangQianJin
	 * @date 2013-6-18 上午9:36:06
	 */
	private void getFavorableDiscount(WaybillInfoVo bean) {
		List<ProductPriceDto> productPrice = null;
		// 查询产品价格
		if (bean.getPickupToDoor()) {
			productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.YES, Common.GBJ, false));
			if (productPrice == null) {
				productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, false));			
                List<ValueAddDto> list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,false));
				if(list==null||list.isEmpty()){
					 list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,true));
				}
				ValueAddDto dto = null;
				//设置其他费用的折扣优惠
				if (list != null && !list.isEmpty()) {
					dto = list.get(0);					
					// 设置折扣优惠
					Common.setFavorableDiscount(dto.getDiscountPrograms(), bean);
				}
			}
		} else {
			productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, false));			
		}
		
		//开单版本取不到读当前版本
		if(productPrice == null){
			// 查询产品价格
			if (bean.getPickupToDoor()) {
				productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.YES, Common.GBJ, true));
				if (productPrice == null) {
					productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, true));					
					List<ValueAddDto> list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,false));
					if(list==null||list.isEmpty()){
						 list = waybillService.queryValueAddPriceList(getPickupFeeParam(bean,true));
					}
					ValueAddDto dto = null;
					//设置其他费用的折扣优惠
					if (list != null && !list.isEmpty()) {
						dto = list.get(0);						
						// 设置折扣优惠
						Common.setFavorableDiscount(dto.getDiscountPrograms(), bean);
					}
				}
			} else {
				productPrice = waybillService.queryProductPriceList(Common.getQueryParamForReturn(bean, FossConstants.NO, Common.GBJ, true));				
			}
		}
		ProductPriceDto dto = null;
		if (productPrice == null) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.nullProductPrice"));
		} else {
			for (ProductPriceDto fee : productPrice) {
				dto = fee;
				// 设置折扣优惠
				Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
				//设置折扣值
				setDiscount(dto.getDiscountPrograms(),bean);			    
			}
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
			if(WaybillConstants.BILLINGWAY_WEIGHT.equals(bean.getBillingType().getValueCode()))
			{
				bean.setBillWeight(fee.getVolumeWeight());
			}else
			{
				// 计费重量
				if (fee.getVolumeWeight() == null) {
					bean.setBillWeight(BigDecimal.ZERO);
				} else {
					bean.setBillWeight(fee.getVolumeWeight());
				}
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
		// 保价费
		getInsurance(bean);
		// 代收货款手续费
		getCod(bean);
		// 送货费
		//getDeliveryFee(bean);		
//		Common.setDeliveryFeeCollection(bean);
		// 计算装卸费
		calculateServiceFee(bean);
		// 计算转运费
		calculateTransferFee(bean);
		// 计算返货费
		calculateReturnFee(bean);
		//添加其他费用 折扣
		queryOtherChargeData(bean);
		//推广活动会对其他费用打折，重新在其他费用面板赋值
		//setterOtherFeeByMakActive(bean);
		setReturnBillCollection(bean);
	}
	
	/**
	 * 签收单折扣
	 * pgy
	 * @param bean
	 */
	private void setReturnBillCollection(WaybillInfoVo bean) {
		 
		//获取其他费用
		boolean flag=false;
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();				
		if(CollectionUtils.isNotEmpty(data)){
			for(OtherChargeVo vo : data){
				if(PriceEntityConstants.PRICING_CODE_QS.equals(vo.getCode())){
					flag=true;								
					break;						
				}
			}						
		}
		if(flag){
			QueryBillCacilateValueAddDto addDto=getQueryOtherChargeParam(bean);
			//发货客户编码
			addDto.setCustomerCode(bean.getDeliveryCustomerCode());
			// 签回单
			addDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);
			addDto.setSubType(bean.getReturnBillType().getValueCode());
			List<ValueAddDto> list = waybillService.queryValueAddPriceList(addDto);
			//设置其他费用的折扣优惠
			if(list!=null && list.size()>0){
				for (ValueAddDto valueAddDto : list) {
					if (PriceEntityConstants.PRICING_CODE_QS.equals(valueAddDto.getPriceEntityCode())) {
						Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), bean);
						break;
				}
					
				}
			}
		
		}
			
		
	}
	
	/**
	 * 推广活动会对其他费用打折，重新在其他费用面板赋值
	 * @创建时间 2014-5-17 上午10:51:57   
	 * @创建人： WangQianJin
	 */
	private void setterOtherFeeByMakActive(WaybillInfoVo bean){		
		//推广活动折扣后的综合信息费
		BigDecimal zhxxDis=null;
		BigDecimal kdbzDis=null;
		BigDecimal zhxx=Common.getDefaultZhxxFee(bean);
		if(zhxx!=null){
			//获取折扣后的综合信息费四舍五入取整
			zhxxDis=zhxx.setScale(0, BigDecimal.ROUND_HALF_UP);
		}	
		BigDecimal kdbz=Common.getDefaultKdbzFee(bean);
		if(kdbz!=null){
			//获取折扣后的综合信息费四舍五入取整
			kdbzDis=kdbz.setScale(0, BigDecimal.ROUND_HALF_UP);
		}		
		//重新设置其他费用
		if(zhxxDis!=null){
			//获取其他费用
			JXTable otherTable = ui.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
			List<OtherChargeVo> data = model.getData();				
			if(CollectionUtils.isNotEmpty(data)){
				boolean flag=false;
				boolean flagKdbz=false;
				for(OtherChargeVo vo : data){
					if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())){
						vo.setMoney(zhxxDis.toString());
						flag=true;												
					}
					if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(vo.getCode())){
						if(kdbzDis != null){
							vo.setMoney(kdbzDis.toString());
						}
						flagKdbz=true;												
					}
				}
				if(flag || flagKdbz){						
					//重新获取其他费用
					CommoForFeeUtils.otherPanelSumFee(data,bean);
					//强制调用其他费用变更处理，否则变更项中无法显示其他费用的变化
					ui.getCompareListener().tableChangedEnforceCall();
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
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		//渠道订单
		queryDto.setChannelCode(bean.getOrderChannel());
		//设置营销活动查询条件
		Common.settterActiveParamInfoValueAdd(queryDto, bean);
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
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
		boolean flagZhxx=false;
		boolean flagKdbz=false;
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();	
		if(CollectionUtils.isNotEmpty(data)){			
			for(OtherChargeVo vo : data){
				if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())){
					flagZhxx=true;												
				}
				if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(vo.getCode())){
					flagKdbz=true;												
				}
			}								
		}
		//设置其他费用的折扣优惠		
		if(list!=null && list.size()>0){
			for (ValueAddDto valueAddDto : list) {
				if (PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto.getPriceEntityCode())) {
					if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(valueAddDto.getSubType())){
						if(flagZhxx){
							Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), bean);
						}
					}else if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(valueAddDto.getSubType())){
						if(flagKdbz){
							Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), bean);
						}
					}else{
						Common.setFavorableDiscount(valueAddDto.getDiscountPrograms(), bean);
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
		QueryBillCacilateValueAddDto queryDto = getInsuranceParam(bean,false);
		/**
		 *  查询保价费:根据需求，如果查找不到收货日期的价格，需要查找当前日期的价格
		 */
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(queryDto);//false表示按收货日期进行查找价格

		//gyk 调用crm接口取得数据
		String productCodeTemp = ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		PreferentialInfoDto preferentialInfoDto = BaseDataServiceFactory.getBaseDataService().queryPreferentialInfo(bean.getDeliveryCustomerCode(),null,productCodeTemp);
		if(list==null ||list.isEmpty())
		{
			queryDto = getInsuranceParam(bean,true);
			list = waybillService.queryValueAddPriceList(queryDto);//true表示按当前日期进行查找价格
		}
		if (list != null && !list.isEmpty()) {
			
			dto = list.get(0);
			
			/**
			 * gyk
			 * 更改单时，若单票保价手续费字段不为空，
			 * 单票保价手续费还是按CRM中的单票保价手续费的值计算。
			 */
			boolean isDiscount = false;
			if(preferentialInfoDto!=null){
				List<String> ls = new ArrayList<String>();
				String unicode =baseDataService.queryUnifiedCodeByCode(bean.getReceiveOrgCode());
				if(unicode!=null){
					ls.add(unicode);
				}
				
				//验证客户是否是部门绑定客户
				List<BargainAppOrgEntity> bargainAppOrgEntities = baseDataService
						.queryAppOrgByBargainCrmId(preferentialInfoDto.getCusBargainId(),ls);
				if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
					for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
						BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
								.get(i);
						String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = baseDataService
								.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
						if (orgAdministrativeInfoEntity == null) {
							continue;
						}
						String orgCode = orgAdministrativeInfoEntity.getCode();
						if (StringUtils.equals(bean.getReceiveOrgCode(), orgCode)) {
							isDiscount = true;
						}
					}
				}
			}
			if(isDiscount){
				if(preferentialInfoDto!=null){
					if(preferentialInfoDto.getSinTicketSurePriceCharge()!=null){
						
					}else{
						// 设置折扣优惠
						Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
					}
				}else{
					// 设置折扣优惠
					Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
				}
			}else{
				// 设置折扣优惠
				Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
			}
			
			setInsurance(bean, dto);
			
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
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		//设置营销活动查询条件
		Common.settterActiveParamInfoValueAdd(queryDto, bean);
		

		/***
		 * 菜鸟返货折扣
		 */
		WaybillExpressEntity exp = waybillService.queryWaybillByWaybillNo(bean.getWaybillNo(),WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		if(exp!=null && exp.getOriginalWaybillNo()!=null){
			WaybillEntity entyty = waybillService.queryWaybillByNumber(exp.getOriginalWaybillNo());
			
			/**
			 * 收货客户省市区
			 */
			AddressFieldDto consigneeAddress = null;
			
			if(entyty != null){
				consigneeAddress = new BusinessUtils().getProvCityCounty(
						entyty.getDeliveryCustomerProvCode(),
						entyty.getDeliveryCustomerCityCode(),
						entyty.getDeliveryCustomerDistCode());
			}
			
			String address="";
			
			if(consigneeAddress!=null && entyty!=null){
				String provName = consigneeAddress.getProvinceName()!=null? consigneeAddress.getProvinceName():"" ;
				String cityName = consigneeAddress.getCityName()!=null? consigneeAddress.getCityName():"";
				String distName = consigneeAddress.getCountyName()!=null? consigneeAddress.getCountyName():"";
				address=provName+cityName+distName+entyty.getDeliveryCustomerAddress();
				address =StringUtils.substring(address, 0,NumberConstants.NUMBER_100);
			}
			
			 if(entyty!=null 
					    &&
				bean.getReceiveCustomerAddress().equals(address)
				        &&
				bean.getReceiveCustomerDistCode().equals(entyty.getDeliveryCustomerDistCode())
					    &&
				bean.getReceiveCustomerCityCode().equals(entyty.getDeliveryCustomerCityCode()) 
				        &&
			    bean.getReceiveCustomerProvCode().equals(entyty.getDeliveryCustomerProvCode())
				        &&
				bean.getPaidMethod().getValueCode().equals(WaybillConstants.ARRIVE_PAYMENT)){
	       
	        	queryDto.setIsCainiao(true);
	        	queryDto.setReturnWaybillNo(exp.getOriginalWaybillNo());
	        	queryDto.setOldreceiveCustomerCode(entyty.getDeliveryCustomerCode());
	        	queryDto.setReturnbilltime(entyty.getBillTime());
	        	queryDto.setReturnTransportFee(entyty.getTransportFee());
	        	queryDto.setReturnInsuranceFee(entyty.getInsuranceFee());
	        	queryDto.setOriginalReceiveOrgCode(entyty.getReceiveOrgCode());
		        	
			} 
			
		}
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
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
		if (dto != null) {
			bean.setMaxInsuranceAmount(dto.getMaxFee());
		}
		// 将保险费率转换成千分率的格式
		BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
		// 保价
		BigDecimal insuranceAmount = CommonUtils.defaultIfNull(bean.getInsuranceAmount());
		// 保险费
//		BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);  // 四舍五入
		BigDecimal insuranceFee = CommonUtils.defaultIfNull(bean.getInsuranceFee());
		// 保险费率
		BigDecimal feeRate =  BigDecimal.ZERO;
		// 判断保险费是否为0
		if(insuranceAmount.compareTo(BigDecimal.ZERO) == 0 || insuranceFee.compareTo(BigDecimal.ZERO) == 0){
			//保险费率设置为基础资料中读取出的费率
			if(dto!=null){
				feeRate = CommonUtils.defaultIfNull(dto.getFeeRate()).multiply(permillage);
			}
//			// 保险手续费
//			bean.setInsuranceFee(BigDecimal.ZERO);
		}else{
			//根据保价和保费倒推费率
			feeRate = insuranceFee.divide(insuranceAmount, 5, BigDecimal.ROUND_HALF_UP).multiply(permillage);
//			// 保险手续费
//			bean.setInsuranceFee(insuranceFee);
		}
		bean.setInsuranceRate(feeRate);
		
		
		if(dto!=null){
			// 保险费ID
			bean.setInsuranceId(CommonUtils.defaultIfNull(dto.getId()));
			// 保险费CODE
			bean.setInsuranceCode(CommonUtils.defaultIfNull(dto.getPriceEntityCode()));
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
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = bean.getCodAmount();
		//gyk 调用crm接口取得数据
		String productCodeTemp = null;
		if(CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode())){
			productCodeTemp = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		}
		PreferentialInfoDto preferentialInfoDto = BaseDataServiceFactory.getBaseDataService().queryPreferentialInfo(bean.getDeliveryCustomerCode(),null,productCodeTemp);
		boolean isDiscount = false;
		if(preferentialInfoDto!=null){
			String unicode  = baseDataService.queryUnifiedCodeByCode(bean.getReceiveOrgCode());
			List<String> list = new ArrayList<String>();
			if(unicode!=null){
				list.add(unicode);
			}
			//验证客户是否是部门绑定客户
			List<BargainAppOrgEntity> bargainAppOrgEntities = baseDataService
					.queryAppOrgByBargainCrmId(preferentialInfoDto.getCusBargainId(),list);
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
				for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
					BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
							.get(i);
					String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = baseDataService
							.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
					if (orgAdministrativeInfoEntity == null) {
						continue;
					}
					String orgCode = orgAdministrativeInfoEntity.getCode();
					if (StringUtils.equals(bean.getReceiveOrgCode(), orgCode)) {
						isDiscount = true;
					}
				}
			}
		}
		if(!isDiscount){
			preferentialInfoDto = null;
		}
		if (codAmount != null && codAmount.compareTo(zero) > 0 && bean.getRefundType() != null) {
			ValueAddDto dto = new ValueAddDto();
			// 查询代收货款手续费
			queryDto = getQueryParam(bean, PriceEntityConstants.PRICING_CODE_HK, bean.getCodAmount(), bean.getRefundType().getValueCode(),false);
			List<ValueAddDto> list = waybillService.queryValueAddPriceList(queryDto);//false 表示按billtime去拿价格

			
			if(list==null||list.isEmpty())
			{
				queryDto = getQueryParam(bean, PriceEntityConstants.PRICING_CODE_HK, bean.getCodAmount(), bean.getRefundType().getValueCode(),true);
				// 查询代收货款手续费
				list = waybillService.queryValueAddPriceList(queryDto);//true 表示按当前时间去拿价格

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
				/**
				 * gyk
				 * 更改单时，若单票代收手续费字段不为空，代收金额大于0时，
				 * 代收手续费还是按CRM中的单票代收手续费的值计算。
				 */
//				if(preferentialInfoDto!=null){
//					if(preferentialInfoDto.getSinTicketCollCharge()!=null){
//						dto.setCaculateFee(preferentialInfoDto.getSinTicketCollCharge());
//					}else{
//						// 设置折扣优惠
//						Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
//					}
//				}else{
//				}
				// 设置折扣优惠
				Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
				setCod(bean, dto);
		
			} else {
				setCod(bean, null);
				throw new WaybillValidateException(bean.getRefundType().getValueName() + i18n.get("foss.gui.creating.calculateAction.exception.nullCodAmountFee"));
			}
		}else{
			//取消代收货款，如果出了开单部门，恢复开单时的代收手续费
			if(!WaybillRfcConstants.RECEIVE_STOCK.equals(bean.getGoodsStatus().getValueCode())){
				
				// 代收货款费率
				bean.setCodRate(ui.getOriginWaybill().getCodRate());
				// 代收货款金额
				bean.setCodFee(ui.getOriginWaybill().getCodFee());
				// 代收货款编码
				bean.setCodCode(ui.getOriginWaybill().getCodCode());
				// 代收货款ID
				bean.setCodId(ui.getOriginWaybill().getCodId());
			}
			/**
			 * 新添加gyk
			 * 开单时CRM中未勾选代收退费属性，则代收手续费保持不变 
			 * 开单时CRM中勾选代收退费属性，则重新计算运费时更改代收手续费为0。
			 */
			if(preferentialInfoDto!=null){
				if("Y".equals(preferentialInfoDto.getCollReturnCharge())){
					bean.setCodFee(BigDecimal.ZERO);
					bean.setCodRate(BigDecimal.ZERO);
				}else{
					// 代收货款费率
					bean.setCodRate(ui.getOriginWaybill().getCodRate());
					// 代收货款金额
					bean.setCodFee(ui.getOriginWaybill().getCodFee());
				}
			}else{
				// 代收货款费率
				bean.setCodRate(ui.getOriginWaybill().getCodRate());
				// 代收货款金额
				bean.setCodFee(ui.getOriginWaybill().getCodFee());
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
			//gyk
			if(dto.getCaculateFee()!=null){
				codRate=dto.getCaculateFee().divide(bean.getCodAmount(),5, BigDecimal.ROUND_HALF_UP);
			}
			codRate = codRate.multiply(permillage);
			bean.setCodRate(codRate);

			BigDecimal codFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			/**
			 * DMANA-3793 快递代收货款最低一票
			 * 与客户合同中读取代收货款手续费最低一票的值比较确定值
			 * 当且仅当当前代收货款手续费等于最低一票
			 */
			if(codFee.compareTo(dto.getMinFee())<=0){
				//根据客户编码和产品类型查询客户优惠信息
				PreferentialInfoDto entityInfo = BaseDataServiceFactory.getBaseDataService().queryPreferentialInfo(bean.getDeliveryCustomerCode(), null, ProductEntityConstants.PRICING_PRODUCT_C2_C20005);
				//实际代收货款手续费,可能小于增值服务中配置的最低一票

				BigDecimal actualCodFee = bean.getCodAmount().multiply(bean.getCodRate().divide(permillage));
				
				if(entityInfo!=null&&entityInfo.getLowestCharge()!=null&&entityInfo.getLowestCharge().compareTo(BigDecimal.ZERO)>=0){
					//当客户合同中的代收货款最低手续费值小于增值服务中配置的最低一票时，取客户合同中的最低手续费的值
					if(entityInfo.getLowestCharge().compareTo(dto.getMinFee())<0){
						//设置代收货款手续费为客户合同中的值
						codFee = entityInfo.getLowestCharge();
						//用客户合同中配置的代收汇款手续费与实际代收货款手续费作比较  取其中的大值
						if(codFee.compareTo(actualCodFee)<0){
							codFee = actualCodFee;
						}
					}
				}
			}
			
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
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
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

		// 整车没有送货费
		if (bean.getIsWholeVehicle() != null && bean.getIsWholeVehicle()) {
			cleanDeliverCharge(bean);
			return;
		}

		List<ValueAddDto> list = null;

		//提货方式编码
		if(bean.getFinalReceiveMethod()==null){
			throw new WaybillValidateException(i18n.get("foss.gui.changingexp.transportInfoPanel.pickMode.label.isNull"));
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
			
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,false));//false表示按照业务日期去找价格
			
			if(list==null||list.isEmpty())
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
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,false));//false 用业务日期
			
			if(list==null||list.isEmpty())
			{
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,true));//true 用当前日期
			}
			// 设置送货费
			setDeliverFee(list, bean, true , false);
			
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null,false));//false 用业务日期
			if(list==null||list.isEmpty())
			{
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null,true));//true 用当前日期
			}
			// 设置上楼费
			setDeliverFee(list, bean, false , false);
			// 超远派送费
			veryFarDeliveryFee(bean);
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			list = waybillService.queryValueAddPriceList(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null,false));
			if(list==null||list.isEmpty()){
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
			//送货费合计 = 送货费+上楼费/进仓费/超远派送费
			chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
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
			// 设置折扣优惠
			Common.setFavorableDiscount(dto.getDiscountPrograms(),bean);
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
		//如果不是转运与返货，则设置营销活动
		if(!WaybillRfcConstants.RETURN.equals(bean.getRfcType().getValueCode()) &&
				!WaybillRfcConstants.TRANSFER.equals(bean.getRfcType().getValueCode())){			
			//营销活动DTO
			queryDto.setActiveDto(bean.getActiveDto());				
			//是否计算市场营销折扣
			queryDto.setCalActiveDiscount(bean.isCalActiveDiscount());	
			if(bean.getActiveInfo()!=null){
				queryDto.setActiveCode(bean.getActiveInfo().getValueCode());
			}		
			queryDto.setGoodsName(bean.getGoodsName());
			queryDto.setDeliveryCustomerCode(bean.getDeliveryCustomerCode());
			queryDto.setOrderChannel(bean.getOrderChannel());
			queryDto.setReceiveOrgCode(bean.getReceiveOrgCode());
			queryDto.setLoadOrgCode(bean.getLoadOrgCode());
			queryDto.setLastOutLoadOrgCode(bean.getLastOutLoadOrgCode());
			queryDto.setCustomerPickupOrgCode(bean.getCustomerPickupOrgCode().getCode());
			queryDto.setBillTime(bean.getBillTime());
			queryDto.setTransportFee(bean.getTransportFee());
			queryDto.setGoodsWeightTotal(bean.getGoodsWeightTotal());
			queryDto.setGoodsVolumeTotal(bean.getGoodsVolumeTotal());
		}
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

		// 设置是否允许修改装卸费
		//boolean serviceChargeEnabled = setServiceChargeState(bean);

		// 装卸费无效
		//if (!serviceChargeEnabled) {
		//	return;
		//}
		
		validataServiceFee(bean);
		
		/**
		 * 如果计算了公布价，才将装卸费添加到公布价中，如果没计算公布价，则说明公布价中已包含装卸费，无需再次累加
		 */
		if(FossConstants.YES.equals(bean.getIsCalTraFee())){			
			//获取装卸费
			BigDecimal serivceFee = bean.getServiceFee();
			if (serivceFee != null && serivceFee.longValue() != 0) {
				// 输入的装卸费不为0的情况下，按照以下规则：
				// 输入装卸费之后的公布价运费 = 纯运费+装卸费
				// 输入装卸费之后的费率 = （纯运费+装卸费）/计费重量
				
				//获取公布价运费
				BigDecimal transportFee = bean.getTransportFee();
				//获取费率
				BigDecimal unitPrice = bean.getUnitPrice();
				//运费 = 运费+装卸费
				transportFee = transportFee.add(bean.getServiceFee()); 
				//设置新的运费
				bean.setTransportFee(transportFee);
				//费率  = 最新运费（运费+装卸费）/重量或体积
				unitPrice = transportFee.divide(bean.getBillWeight(),2,BigDecimal.ROUND_HALF_DOWN);
				//设置新的费率
				bean.setUnitPrice(unitPrice);
			}
		}		
		
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
		
		
		if (serivceFee.longValue() < 0) {
			throw new WaybillValidateException(i18n.get("foss.gui.creatingexp.expCalculateAction.serivceFeeExceedNegative"));
		}
		
		/*BigDecimal transportFee = bean.getTransportFee();
		
		//输入费用不得高于公布价运费*快递装卸费费率的积截断小数点后的值
		BigDecimal serviceFee2 = (transportFee.multiply(getServiceFeeRate(bean))).setScale(0,BigDecimal.ROUND_DOWN);

		if (serivceFee.compareTo(serviceFee2) > 0) {
			throw new WaybillValidateException(i18n.get("foss.gui.creatingexp.expCalculateAction.serivceFeeExceed"));
		}*/
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
			/*
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode()))
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO);
			}else
			{
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO);
			}*/
			if(CommonUtils.directDetermineIsExpressByProductCode(productVo.getCode())){
				dto = waybillService.queryConfigurationParamsByOrgCode(bean.getReceiveOrgCode(),ConfigurationParamsConstants.STL_SERVICE_EXPRESS_FEE_RATIO);
			}
			
			if (dto == null) {
				throw new WaybillValidateException(i18n.get("foss.gui.creatingexp.expCalculateAction.noSerivceFeeRatio"));
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
			//从增值服务费中减去上次计算的中转费
			bean.setOtherFee(bean.getOtherFee().subtract(tfrFee));
			
			if(!isCalcZZF){
				//费率
				bean.setTfrUnitPrice(BigDecimal.ZERO);
				//总费用
				tfrFee = BigDecimal.ZERO;
			}else if(bean.isTfrNeedHandWrite()){
				//确保计算运费时，转运费率已输入
				if(bean.getTfrBillingType() == null){
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.nullTfrBillingType"));
				}
				//如果转运费率人工编辑	
				String tfrBillType = bean.getTfrBillingType().getValueCode();
				if(StringUtil.isEmpty(tfrBillType)){
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.nullTfrBillingType"));
				}
				if(bean.getTfrUnitPrice()==null){
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.nullTfrUnitPrice"));
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
			//从增值服务费中减去上次计算的中转费
			bean.setOtherFee(bean.getOtherFee().subtract(rtnFee));
			
			if(!isCalcZZF){
				//费率
				bean.setRtnUnitPrice(BigDecimal.ZERO);
				//总费用
				rtnFee = BigDecimal.ZERO;
			}else if(bean.isRtnNeedHandWrite()){
				//确保计算运费时，转运费率已输入
				if(bean.getRtnBillingType() == null){
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.nullRtnBillingType"));
				}
				//如果返货费率人工编辑	
				String rtnBillType = bean.getRtnBillingType().getValueCode();
				if(bean.getRtnUnitPrice()==null){
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.nullTfrUnitPrice"));
				}
				if(StringUtil.isEmpty(rtnBillType)){
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.calculateAction.exception.nullRtnBillingType"));
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
		BranchVo branchVoOld = bean.getCustomerPickupOrgCode();
		
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
						
					if(FossConstants.ACTIVE.equals(saleDepartmentEntity.getStation()))
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
		if(bean.getInternalDeliveryType() != null) {
			queryDto.setInternalDeliveryType(bean.getInternalDeliveryType().getValueCode());
		}
		
		queryDto.setEmployeeNo(bean.getEmployeeNo());
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
				bean.setPickupFee(BigDecimal.ZERO);//设置接货费
				bean.setBasePickupFee(BigDecimal.ZERO);//原始接货费
				throw new WaybillValidateException(i18n.get

("foss.gui.creating.listener.Waybill.PickupFeeNull"));
			} else {
				bean.setPickupFee(dto.getCaculateFee());//设置接货费
				bean.setBasePickupFee(dto.getCaculateFee());//原始接货费
			}

		
	}
	
	/**
	 * 设置查询有签收单原件返回时的查询参数
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午6:03:41
	 */
	private QueryBillCacilateDto getQueryReturnParam(WaybillInfoVo bean) {
		// 新的查询条件
		QueryBillCacilateDto dto = new QueryBillCacilateDto();
		try {
			PropertyUtils.copyProperties(dto, Common.getQueryParam(bean));
			// 设置重量：固定值0.5
			dto.setWeight(BigDecimal.valueOf(0.5));
			// 设置体积
			dto.setVolume(BigDecimal.ZERO);
			dto.setCustomerCode("");
			dto.setIsSelfPickUp(FossConstants.NO);
			//根据DMANA-4938修改，标准快递和3.60特惠件两种产品的原件签单返回计费统一按照标准快递首重收费
			if(ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(dto.getProductCode()) ||
					WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(dto.getProductCode())){
				ConfigurationParamsEntity config=waybillService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.ORIGINAL_START_TIME,FossConstants.ROOT_ORG_CODE);
				if(null!=config){
					if(StringUtils.isNotEmpty(config.getConfValue())){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//签收单原件返回计算新规则生效时间
						Date startDate = null;
						try {
							startDate = sdf.parse(config.getConfValue());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						//如果开单日期在生效日期之后，则需要重新设置运输性质
						if(startDate!=null && bean.getBillTime().after(startDate)){
							dto.setProductCode(ExpWaybillConstants.PACKAGE);
						}
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

}