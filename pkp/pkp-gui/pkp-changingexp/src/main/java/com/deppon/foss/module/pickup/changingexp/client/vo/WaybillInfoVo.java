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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/vo/WaybillInfoVo.java
 * 
 * FILE NAME        	: WaybillInfoVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.WoodYokeVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillStockStatusDto;

/**
 * 
 * 更改单VO
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-1 下午2:03:37
 */
public class WaybillInfoVo extends WaybillPanelVo {
	/**
	 * 是否其他增值服务费变更
	 */
	private boolean isOtherChargeChanged;
	
	/**
	 * 走货路径
	 */
	private OrgInfoDto orgInfoDto;
	
	/**
	 * 导入运单号
	 */
	private String importWaybillNo;

	/**
	 * 更改后运单号
	 */
	private String newWaybillNo;

	/**
	 * 变更来源
	 */
	private String rfcSource;

	/**
	 * 变更类型
	 */
	private DataDictionaryValueVo rfcType;

	/**
	 * 变更原因
	 */
	private String rfcReason;

	/**
	 * 起草部门
	 */
	private String darftOrgName;

	/**
	 * 起草人
	 */
	private String darfter;

	/**
	 * 起草时间
	 */
	private Date darftTime;

	/**
	 * 差错处理编号
	 */
	private String errorHandlingCode;

	/**
	 * 差错处理结果
	 */
	private String errorHandlingResult;

	/**
	 * 更改单ID
	 */
	private String waybillRfcId;

	/**
	 * 货物状态
	 */
	private DataDictionaryValueVo goodsStatus;

	/**
	 * 运单更改明细
	 */
	private List<WaybillRfcChangeDetailDto> rfcChangeDetailList;

	/**
	 * 转货记录
	 */
	private List<TransportRecordVo> transferRecordList;

	/**
	 * 返货记录
	 */
	private List<TransportRecordVo> returnRecordList;
	
	/**
	 * 变更记录
	 */
	private List<TransportRecordVo> transportRecordList;

	/**
	 * 转货运输信息(属性名规则为：tfr + 原始运输信息对应属性名（首字母改大写）)
	 */
	/**
	 * 转运运输性质是否需要过滤
	 */
	private boolean tfrNeedFilter;
	
	/**
	 * 需要手写费率
	 */
	private boolean tfrNeedHandWrite;

	/**
	 * 转运出发部门
	 */
	private String tfrStartOrgCode;

	/**
	 * 运输性质
	 */
	private ProductEntityVo tfrProductCode;

	/**
	 * 提货网点
	 */
	private BranchVo tfrCustomerPickupOrgCode;

	/**
	 * 提货网点名称
	 */
	private String tfrCustomerPickupOrgName;

	/**
	 * 目的站
	 */
	private String tfrTargetOrgCode;


	/**
	 * 提货方式
	 */
	private DataDictionaryValueVo tfrReceiveMethod;

	/**
	 * 预配航班
	 */
	private DataDictionaryValueVo tfrFlightNumberType;

	/**
	 * 运费计费类型
	 */
	private DataDictionaryValueVo tfrBillingType;

	/**
	 * 运费计费费率
	 */
	private BigDecimal tfrUnitPrice;

	/**
	 * 转运费
	 */
	private BigDecimal tfrFee;
	
	/**
	 * 转运合票方式
	 */
	private DataDictionaryValueVo tfrFreightMethod;

	
	
	/**
	 * 转运航班时间
	 */
	private String tfrFlightShift;

	/**
	 * 返货运输信息(属性名规则为：rtn + 原始运输信息对应属性名（首字母改大写）)
	 */

	/**
	 * 转运运输性质是否需要过滤
	 */
	private boolean rtnNeedFilter;

	/**
	 * 需要手写费率
	 */
	private boolean rtnNeedHandWrite;

	/**
	 * 返货出发部门
	 */
	private String rtnStartOrgCode;

	/**
	 * 运输性质
	 */
	private ProductEntityVo rtnProductCode;

	/**
	 * 提货网点
	 */
	private BranchVo rtnCustomerPickupOrgCode;

	/**
	 * 提货网点名称
	 */
	private String rtnCustomerPickupOrgName;

	/**
	 * 目的站
	 */
	private String rtnTargetOrgCode;

	/**
	 * 提货方式
	 */
	private DataDictionaryValueVo rtnReceiveMethod;

	/**
	 * 运费计费类型
	 */
	private DataDictionaryValueVo rtnBillingType;

	/**
	 * 运费计费费率
	 */
	private BigDecimal rtnUnitPrice;

	/**
	 * 转运费
	 */
	private BigDecimal rtnFee;
	
	/**
	 * 
	 */
	/**
	 * 转运出发部门
	 */
	private String recordStartOrgCode;

	/**
	 * 运输性质
	 */
	private ProductEntityVo recordProductCode;

	/**
	 * 提货网点
	 */
	private BranchVo recordCustomerPickupOrgCode;

	/**
	 * 提货网点名称
	 */
	private String recordCustomerPickupOrgName;

	/**
	 * 目的站
	 */
	private String recordTargetOrgCode;


	/**
	 * 提货方式
	 */
	private DataDictionaryValueVo recordReceiveMethod;

	/**
	 * 预配航班
	 */
	private DataDictionaryValueVo recordFlightNumberType;

	/**
	 * 运费计费类型
	 */
	private DataDictionaryValueVo recordBillingType;

	/**
	 * 运费计费费率
	 */
	private BigDecimal recordUnitPrice;

	/**
	 * 转运费
	 */
	private BigDecimal recordFee;
	
	/**
	 * 转运合票方式
	 */
	private DataDictionaryValueVo recordFreightMethod;

	
	
	/**
	 * 转运航班时间
	 */
	private String recordFlightShift;

	/**
	 * 发货人省市区(数据绑定用)
	 */
	private AddressFieldDto deliveryCustomerAreaDto;

	/**
	 * 收货人省市区(数据绑定用)
	 */
	private AddressFieldDto receiveCustomerAreaDto;

	/**
	 * 发货人省市区(数据绑定用)
	 */
	private String deliveryCustomerArea;
	
	/**
	 * 新增收货人乡镇(街道)
	 */
	private String receiveCustomerVillageCode;

	/**
	 * 收货人省市区(数据绑定用)
	 */
	private String receiveCustomerArea;

	/**
	 * 库存状态
	 */
	private WaybillStockStatusDto stockStatus;

	/**
	 * 原始运单DTO
	 */
	private WaybillDto waybillDto;

	/**
	 * 其他费用明细
	 */
	private List<OtherChargeVo> otherChargeVos;

	/**
	 * 运单流水号
	 */
	private List<LabeledGoodEntity> labeledGoodEntities;

	/**
	 * 打木架流水号
	 */
	private List<LabeledGoodChangeHistoryDto> selectedLabeledGoodEntities;
	
	private List<LabeledGoodChangeHistoryDto>  labeledGoodChangeHistoryDtoList;

	/**
	 * ---------------------控件属性
	 */
	/**
	 * 纸
	 */
	private Integer paper;

	/**
	 * 木
	 */
	private Integer wood;

	/**
	 * 纤
	 */
	private Integer fibre;

	/**
	 * 托
	 */
	private Integer salver;

	/**
	 * 膜
	 */
	private Integer membrane;

	/**
	 * 其他
	 */
	private String otherPackage;

	/**
	 * 后期加入
	 */
	/**
	 * 手写金额
	 */
	private BigDecimal handWriteMoney;

	/**
	 * 代打木架
	 */
	private WoodYokeVo yokeVo;

	/**
	 * 储运事项
	 */
	private String transportationRemark;

	/**
	 * 长途短途
	 */
	private String longOrShort;

	/**
	 * 画布中需要新增的属性
	 */
	/**
	 * 公布价运费
	 */
	private String transportFeeCanvas;

	/**
	 * 保价声明
	 */
	private String insuranceAmountCanvas;

	/**
	 * 代收货款
	 */
	private String codAmountCanvas;

	/**
	 * 接货费
	 */
	private String pickUpFeeCanvas;

	/**
	 * 送货费
	 */
	private String deliveryGoodsFeeCanvas;

	/**
	 * 包装费
	 */
	private String packageFeeCanvas;

	/**
	 * 装卸费
	 */
	private String serviceFeeCanvas;

	/**
	 * 其他费用
	 */
	private String otherFeeCanvas;

	/**
	 * 优惠费用
	 */
	private String promotionsFeeCanvas;

	/**
	 * 总费用
	 */
	private String totalFeeCanvas;

	/**
	 * ----------实体属性
	 */
	/**
	 * 运单实体ID
	 */
	private String id;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 订单号
	 */
	private String orderNo;

	// 结算付款(是否月结)
	private Boolean chargeMode;

	/**
	 * 发货客户ID
	 */
	private String deliveryCustomerId;

	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;

	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;

	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobilephone;

	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerPhone;

	/**
	 * 发货客户联系人
	 */
	private String deliveryCustomerContact;

	/**
	 * 发货客户联系人ID
	 */
	private String deliveryCustomerContactId;

	/**
	 * 发货国家
	 */
	private String deliveryCustomerNationCode;

	/**
	 * 发货省份
	 */
	private String deliveryCustomerProvCode;

	/**
	 * 发货市
	 */
	private String deliveryCustomerCityCode;

	/**
	 * 发货区
	 */
	private String deliveryCustomerDistCode;

	/**
	 * 发货具体地址
	 */
	private String deliveryCustomerAddress;

	/**
	 * 收货客户ID
	 */
	private String receiveCustomerId;

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;

	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;

	/**
	 * 收货客户联系人ID
	 */
	private String receiveCustomerContactId;

	/**
	 * 收货国家
	 */
	private String receiveCustomerNationCode;

	/**
	 * 收货省份
	 */
	private String receiveCustomerProvCode;

	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;

	/**
	 * 收货区
	 */
	private String receiveCustomerDistCode;

	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;

	/**
	 * 收货部门
	 */
	private String receiveOrgCode;

	/**
	 * 收货部门
	 */
	private String receiveOrgName;

	/**
	 * 运输性质
	 */
	private ProductEntityVo productCode;

	/**
	 * 提货方式
	 */
	private DataDictionaryValueVo receiveMethod;

	/**
	 * 提货网点
	 */
	private BranchVo customerPickupOrgCode;

	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;

	/**
	 * 配载类型
	 */
	private String loadMethod;

	/**
	 * 目的站
	 */
	private String targetOrgCode;

	/**
	 * 是否上门接货
	 */
	private Boolean pickupToDoor;

	/**
	 * 司机
	 */
	private String driverCode;

	/**
	 * 是否集中接货
	 */
	private Boolean pickupCentralized;

	/**
	 * 配载线路编码
	 */
	private String loadLineCode;

	/**
	 * 配载线路名称
	 */
	private String loadLineName;

	/**
	 * 配载部门编号
	 */
	private String loadOrgCode;

	/**
	 * 配载部门名称
	 */
	private String loadOrgName;

	/**
	 * 最终配载部门编号
	 */
	private String lastLoadOrgCode;

	/**
	 * 最终配载部门名称
	 */
	private String lastLoadOrgName;

	/**
	 * 预计出发时间
	 */
	private Date preDepartureTime;

	/**
	 * 预计派送/提货时间
	 */
	private Date preCustomerPickupTime;

	/**
	 * 是否大车直送
	 */
	private Boolean carDirectDelivery;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;

	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 货物尺寸
	 */
	private String goodsSize;

	/**
	 * 货物类型
	 */
	private String goodsType;

	/**
	 * 是否贵重物品
	 */
	private Boolean preciousGoods;

	/**
	 * 是否异形物品
	 */
	private Boolean specialShapedGoods;

	/**
	 * 对外备注
	 */
	private DataDictionaryValueVo outerNotes;

	/**
	 * 对内备注
	 */
	private String innerNotes;

	/**
	 * 货物包装
	 */
	private String goodsPackage;

	/**
	 * 保险费ID
	 */
	private String insuranceId;

	/**
	 * 保险费code
	 */
	private String insuranceCode;

	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;

	/**
	 * 保价费率
	 */
	private BigDecimal insuranceRate;

	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;

	/**
	 * 保额最大值
	 */
	private BigDecimal maxInsuranceAmount;

	/**
	 * 保价费最低一票
	 */
	private BigDecimal mixInsuranceAmount;

	/**
	 * 代收货款ID
	 */
	private String codId;

	/**
	 * 代收货款Code
	 */
	private String codCode;

	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;

	/**
	 * 代收费率
	 */
	private BigDecimal codRate;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;

	/**
	 * 退款类型
	 */
	private DataDictionaryValueVo refundType;

	/**
	 * 返单类别
	 */
	private DataDictionaryValueVo returnBillType;

	/**
	 * 返单费用名称
	 */
	private String returnBillChargeName;

	/**
	 * 预付费保密
	 */
	private Boolean secretPrepaid;

	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;

	/**
	 * 送货费ID
	 */
	private String deliveryGoodsFeeId;

	/**
	 * 送货费Code
	 */
	private String deliveryGoodsFeeCode;

	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;

	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;

	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;

	/**
	 * 优惠费用
	 */
	private BigDecimal promotionsFee;

	/**
	 * 运费计费类型
	 */
	private DataDictionaryValueVo billingType;

	/**
	 * 运费计费费率
	 */
	private BigDecimal unitPrice;

	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee;

	/**
	 * 折扣
	 */
	private BigDecimal discount;

	/**
	 * 增值费用
	 */
	private BigDecimal valueAddFee;

	/**
	 * 开单付款方式
	 */
	private DataDictionaryValueVo paidMethod;

	/**
	 * 到达类型
	 */
	private String arriveType;

	/**
	 * 运单状态
	 */
	private String active;

	/**
	 * 禁行
	 */
	private String forbiddenLine;

	/**
	 * 合票方式
	 */
	private DataDictionaryValueVo freightMethod;

	/**
	 * 航班类型
	 */
	private DataDictionaryValueVo flightNumberType;

	/**
	 * 航班时间
	 */
	private String flightShift;

	/**
	 * 总费用
	 */
	private BigDecimal totalFee;

	/**
	 * 优惠编码
	 */
	private String promotionsCode;

	/**
	 * 生效日期
	 */
	private Date beginTime;

	/**
	 * 结束日期
	 */
	private Date endTime;

	/**
	 * 开单时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	/**
	 * 开单人
	 */
	private String createUserCode;

	/**
	 * 更新人
	 */
	private String modifyUserCode;

	/**
	 * 开单组织
	 */
	private String createOrgCode;

	/**
	 * 更新组织
	 */
	private String modifyOrgCode;

	/**
	 * 原运单ID
	 */
	private String refId;

	/**
	 * 原运单号
	 */
	private String refCode;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 是否整车运单
	 */
	private Boolean isWholeVehicle;

	/**
	 * 是否经过营业部
	 */
	private Boolean isPassDept;

	/**
	 * 整车编号
	 */
	private String vehicleNumber;

	/**
	 * 整车约车报价
	 */
	private BigDecimal wholeVehicleAppfee;

	/**
	 * 整车开单报价
	 */
	private BigDecimal wholeVehicleActualfee;

	/**
	 * 代打木架部门编码
	 */
	private String packageOrgCode;

	/**
	 * 代打木架部门编码名称
	 */
	private String packingOrganizationName;

	// 是否可以打木架
	private String doPacking;

	/**
	 * 打木架货物件数
	 */
	private Integer standGoodsNum;

	/**
	 * 代打木架要求
	 */
	private String standRequirement;

	/**
	 * 打木架货物尺寸
	 */
	private String standGoodsSize;

	/**
	 * 打木架货物体积
	 */
	private BigDecimal standGoodsVolume;

	/**
	 * 打木架货物费用
	 */
	private BigDecimal standCharge;

	/**
	 * 打木箱货物件数
	 */
	private Integer boxGoodsNum;

	/**
	 * 代打木箱要求
	 */
	private String boxRequirement;

	/**
	 * 打木箱货物尺寸
	 */
	private String boxGoodsSize;

	/**
	 * 打木箱货物体积
	 */
	private BigDecimal boxGoodsVolume;
	
	// 打木箱费用价格ID
	private String boxChargeId;
	
	// 打木箱费用价格CODE
	private String boxChargeCode;

	/**
	 * 打木箱货物费用
	 */
	private BigDecimal boxCharge;

	/**
	 * 返款帐户开户名称
	 */
	private String accountName;

	/**
	 * 返款帐户开户账户
	 */
	private String accountCode;

	/**
	 * 返款帐户开户银行
	 */
	private String accountBank;

	/**
	 * 计费重量
	 */
	private BigDecimal billWeight;

	/**
	 * 接货费
	 */
	private BigDecimal pickupFee;

	/**
	 * 上一次输入的装卸费
	 */
	private BigDecimal serviceFeeOld;

	/**
	 * 装卸费
	 */
	private BigDecimal serviceFee;

	/**
	 * 装卸费费率
	 */
	private BigDecimal serviceFeeRate;

	/**
	 * 预计到达时间
	 */
	private Date preArriveTime;

	/**
	 * 运输类型
	 */
	private String transportType;

	/**
	 * 打印次数
	 */
	private Integer printTimes;

	/**
	 * 新增时间
	 */
	private Date addTime;

	/**
	 * 是否为PDA运单
	 */
	private Boolean isPdaBill;

	/**
	 * PDA开单时间
	 */
	private Date billTime;

	/**
	 * 运单处理状态
	 */
	private String waybillstatus;

	/**
	 * 弃货处理的运单号
	 */
	private List<String> derelictNum;


	/**
	 *  计算运费获得的包装费
	 */
	private BigDecimal calculatedPackageFee;
	
	
	
	/**
	 * 差错编号是否可以使用
	 */
	private boolean ErrorCodeIsTrue;

	/**
	 * 是否允许修改装卸费
	 */
	private boolean departServiceFeeEnable;
	
	// 整车约车报价波动范围上限
	private BigDecimal wholeVehicleActualfeeFlowRangeUp;

	// 整车约车报价波动范围下限
	private BigDecimal wholeVehicleActualfeeFlowRangeLow;

	// 打木架费用价格ID
	private String standChargeId;
	
	// 打木架费用价格CODE
	private String standChargeCode;
	
	/**
	 * 历史运单调整费用
	 */
	private BigDecimal adjustFee;
	
	/**
	 * 是否计算了公布价
	 */
	private String isCalTraFee;
	
	/**
	 * 更改过体积
	 */
	private String changeVolume;

	/**
	 * 货物总体积(精确的总体积)
	 */
	private BigDecimal goodsVolumePreviousTotal;
	
	
	/**
	 * 转寄退回更改的客户原因还是我司原因
	 */
	private int isChangeReason;
   
	/**
	 * 过去收货部门code
	 */
	private String oldReceiveOrgCode;
	/**
	 * 原单的返单类别
	 */
	private DataDictionaryValueVo oldreturnBillType;;
	
	/**
	 * 过去收获地址
	 */
	private String oldreceiveCustomerAddress;

	/**
	 * 过去收货部门name
	 */
	private String oldReceiveOrgName;
   
	
	
	

	//快递优惠活动类型
	
	private DataDictionaryValueVo specialOffer;
	
	//内部带货费用承担部门
	private String innerPickupFeeBurdenDept;

	/**
	 * 发货具体地址备注
	 */
	private String deliveryCustomerAddressNote;
	
	/**
	 * 收货具体地址备注
	 */
	private String receiveCustomerAddressNote;
    
    
	public DataDictionaryValueVo getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(DataDictionaryValueVo specialOffer) {
		this.specialOffer = specialOffer;
	}
	
	public BigDecimal getGoodsVolumePreviousTotal() {
		return goodsVolumePreviousTotal;
	}
	public void setGoodsVolumePreviousTotal(BigDecimal goodsVolumePreviousTotal) {
		this.goodsVolumePreviousTotal = goodsVolumePreviousTotal;
	}


	public String getChangeVolume() {
		return changeVolume;
	}

	
	public void setChangeVolume(String changeVolume) {
		this.changeVolume = changeVolume;
	}

	public String getIsCalTraFee() {
		return isCalTraFee;
	}

	public void setIsCalTraFee(String isCalTraFee) {
		this.isCalTraFee = isCalTraFee;
	}


	public String getReceiveCustomerVillageCode() {
		return receiveCustomerVillageCode;
	}

	public void setReceiveCustomerVillageCode(String receiveCustomerVillageCode) {
		this.receiveCustomerVillageCode = receiveCustomerVillageCode;
	}


	/**
	 * 中转 返货的时候的中转起点外场
	 */
	private String transferStartOrgCode;
	
	/**
	 * 中转 返货的时候的中转起点外场名称
	 */
	private String transferStartOrgName;
	
	/**
	 * 运单更改内部发货原工号和原单运费
	 * dp-foss-dongjialing
	 * 225131
	 */
	private String originalEmployeeNo;
	private BigDecimal originalFee;
	private BigDecimal calculateVolume;
	
	public BigDecimal getCalculateVolume() {
		return calculateVolume;
	}

	public void setCalculateVolume(BigDecimal calculateVolume) {
		this.calculateVolume = calculateVolume;
	}

	public BigDecimal getOriginalFee() {
		return originalFee;
	}

	public void setOriginalFee(BigDecimal originalFee) {
		this.originalFee = originalFee;
	}

	public String getOriginalEmployeeNo() {
		return originalEmployeeNo;
	}

	public void setOriginalEmployeeNo(String originalEmployeeNo) {
		this.originalEmployeeNo = originalEmployeeNo;
	}

	/**
	 * @return the transferStartOrgCode
	 */
	public String getTransferStartOrgCode() {
		return transferStartOrgCode;
	}

	
	/**
	 * @return the transferStartOrgName
	 */
	public String getTransferStartOrgName() {
		return transferStartOrgName;
	}





	/**
	 * @param transferStartOrgName the transferStartOrgName to set
	 */
	public void setTransferStartOrgName(String transferStartOrgName) {
		this.transferStartOrgName = transferStartOrgName;
	}





	/**
	 * @param transferStartOrgCode the transferStartOrgCode to set
	 */
	public void setTransferStartOrgCode(String transferStartOrgCode) {
		this.transferStartOrgCode = transferStartOrgCode;
	}





	/**
	 * @return the adjustFee
	 */
	public BigDecimal getAdjustFee() {
		return adjustFee;
	}

	
	/**
	 * @param adjustFee the adjustFee to set
	 */
	public void setAdjustFee(BigDecimal adjustFee) {
		this.adjustFee = adjustFee;
	}


	/**
	 * 由于更改单不需要对折扣进行显示，现在使用折扣列表进行装载
	 */
	private List<WaybillDiscountVo> waybillDiscountVos;

	/**
	 * 导入时的折扣信息
	 */
	private List<WaybillDiscountVo> importWaybillDiscountVos;
	
	public List<WaybillDiscountVo> getImportWaybillDiscountVos() {
		return importWaybillDiscountVos;
	}

	public void setImportWaybillDiscountVos(
			List<WaybillDiscountVo> importWaybillDiscountVos) {
		this.importWaybillDiscountVos = importWaybillDiscountVos;
	}
	
	/**
	 * 设置折扣明细
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-16 下午3:19:41
	 */
	public List<WaybillDiscountVo> getWaybillDiscountVos() {
		return waybillDiscountVos;
	}

	/**
	 * 
	 * 设置折扣明细
	 * 
	 * @param waybillDiscountVos
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-16 下午3:19:37
	 */
	public void setWaybillDiscountVos(List<WaybillDiscountVo> waybillDiscountVos) {
		this.waybillDiscountVos = waybillDiscountVos;
	}

	/**
	 * @return the errorCodeIsTrue
	 */
	public boolean isErrorCodeIsTrue() {
		return ErrorCodeIsTrue;
	}


	/**
	 * @param errorCodeIsTrue the errorCodeIsTrue to set
	 */
	public void setErrorCodeIsTrue(boolean errorCodeIsTrue) {
		ErrorCodeIsTrue = errorCodeIsTrue;
	}


	/**
	 * @return the calculatedPackageFee
	 */
	public BigDecimal getCalculatedPackageFee() {
		return calculatedPackageFee;
	}

	
	/**
	 * @param calculatedPackageFee the calculatedPackageFee to set
	 */
	public void setCalculatedPackageFee(BigDecimal calculatedPackageFee) {
		//四舍五入取整数
		this.calculatedPackageFee = CalculateFeeTotalUtils.formatNumberInteger(calculatedPackageFee);
	}

	/**
	 * @return the importWaybillNo
	 */
	public String getImportWaybillNo() {
		return importWaybillNo;
	}

	/**
	 * @param importWaybillNo
	 *            the importWaybillNo to set
	 */
	public void setImportWaybillNo(String importWaybillNo) {
		this.importWaybillNo = importWaybillNo;
	}

	/**
	 * @return the newWaybillNo
	 */
	public String getNewWaybillNo() {
		return newWaybillNo;
	}

	/**
	 * @param newWaybillNo
	 *            the newWaybillNo to set
	 */
	public void setNewWaybillNo(String newWaybillNo) {
		this.newWaybillNo = newWaybillNo;
	}

	/**
	 * @return the rfcSource
	 */
	public String getRfcSource() {
		return rfcSource;
	}

	/**
	 * @param rfcSource
	 *            the rfcSource to set
	 */
	public void setRfcSource(String rfcSource) {
		this.rfcSource = rfcSource;
	}

	/**
	 * @return the rfcType
	 */
	public DataDictionaryValueVo getRfcType() {
		return rfcType;
	}

	/**
	 * @param rfcType
	 *            the rfcType to set
	 */
	public void setRfcType(DataDictionaryValueVo rfcType) {
		this.rfcType = rfcType;
	}

	/**
	 * @return the rfcReason
	 */
	public String getRfcReason() {
		return rfcReason;
	}

	/**
	 * @param rfcReason
	 *            the rfcReason to set
	 */
	public void setRfcReason(String rfcReason) {
		this.rfcReason = rfcReason;
	}

	/**
	 * @return the darftOrgName
	 */
	public String getDarftOrgName() {
		return darftOrgName;
	}

	/**
	 * @param darftOrgName
	 *            the darftOrgName to set
	 */
	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	/**
	 * @return the darfter
	 */
	public String getDarfter() {
		return darfter;
	}

	/**
	 * @param darfter
	 *            the darfter to set
	 */
	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}

	/**
	 * @return the darftTime
	 */
	public Date getDarftTime() {
		return darftTime;
	}

	/**
	 * @param darftTime
	 *            the darftTime to set
	 */
	public void setDarftTime(Date darftTime) {
		this.darftTime = darftTime;
	}

	/**
	 * @return the errorHandlingCode
	 */
	public String getErrorHandlingCode() {
		return errorHandlingCode;
	}

	/**
	 * @param errorHandlingCode
	 *            the errorHandlingCode to set
	 */
	public void setErrorHandlingCode(String errorHandlingCode) {
		this.errorHandlingCode = errorHandlingCode;
	}

	/**
	 * @return the errorHandlingResult
	 */
	public String getErrorHandlingResult() {
		return errorHandlingResult;
	}

	/**
	 * @param errorHandlingResult
	 *            the errorHandlingResult to set
	 */
	public void setErrorHandlingResult(String errorHandlingResult) {
		this.errorHandlingResult = errorHandlingResult;
	}

	/**
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	/**
	 * @param waybillRfcId
	 *            the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	/**
	 * @return the goodsStatus
	 */
	public DataDictionaryValueVo getGoodsStatus() {
		return goodsStatus;
	}

	/**
	 * @param goodsStatus
	 *            the goodsStatus to set
	 */
	public void setGoodsStatus(DataDictionaryValueVo goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	/**
	 * @return the rfcChangeDetailList
	 */
	public List<WaybillRfcChangeDetailDto> getRfcChangeDetailList() {
		return rfcChangeDetailList;
	}

	/**
	 * @param rfcChangeDetailList
	 *            the rfcChangeDetailList to set
	 */
	public void setRfcChangeDetailList(
			List<WaybillRfcChangeDetailDto> rfcChangeDetailList) {
		this.rfcChangeDetailList = rfcChangeDetailList;
	}

	/**
	 * @return the transferRecordList
	 */
	public List<TransportRecordVo> getTransferRecordList() {
		return transferRecordList;
	}

	/**
	 * @param transferRecordList
	 *            the transferRecordList to set
	 */
	public void setTransferRecordList(List<TransportRecordVo> transferRecordList) {
		this.transferRecordList = transferRecordList;
	}

	/**
	 * @return the returnRecordList
	 */
	public List<TransportRecordVo> getReturnRecordList() {
		return returnRecordList;
	}

	/**
	 * @param returnRecordList
	 *            the returnRecordList to set
	 */
	public void setReturnRecordList(List<TransportRecordVo> returnRecordList) {
		this.returnRecordList = returnRecordList;
	}

	/**
	 * @return the tfrNeedFilter
	 */
	public boolean isTfrNeedFilter() {
		return tfrNeedFilter;
	}

	/**
	 * @param tfrNeedFilter
	 *            the tfrNeedFilter to set
	 */
	public void setTfrNeedFilter(boolean tfrNeedFilter) {
		this.tfrNeedFilter = tfrNeedFilter;
	}

	/**
	 * @return the tfrStartOrgCode
	 */
	public String getTfrStartOrgCode() {
		return tfrStartOrgCode;
	}

	/**
	 * @param tfrStartOrgCode
	 *            the tfrStartOrgCode to set
	 */
	public void setTfrStartOrgCode(String tfrStartOrgCode) {
		this.tfrStartOrgCode = tfrStartOrgCode;
	}

	/**
	 * @return the tfrProductCode
	 */
	public ProductEntityVo getTfrProductCode() {
		return tfrProductCode;
	}

	/**
	 * @param tfrProductCode
	 *            the tfrProductCode to set
	 */
	public void setTfrProductCode(ProductEntityVo tfrProductCode) {
		this.tfrProductCode = tfrProductCode;
	}

	/**
	 * @return the tfrCustomerPickupOrgCode
	 */
	public BranchVo getTfrCustomerPickupOrgCode() {
		return tfrCustomerPickupOrgCode;
	}

	/**
	 * @param tfrCustomerPickupOrgCode
	 *            the tfrCustomerPickupOrgCode to set
	 */
	public void setTfrCustomerPickupOrgCode(BranchVo tfrCustomerPickupOrgCode) {
		this.tfrCustomerPickupOrgCode = tfrCustomerPickupOrgCode;
	}

	/**
	 * @return the tfrCustomerPickupOrgName
	 */
	public String getTfrCustomerPickupOrgName() {
		return tfrCustomerPickupOrgName;
	}

	/**
	 * @param tfrCustomerPickupOrgName
	 *            the tfrCustomerPickupOrgName to set
	 */
	public void setTfrCustomerPickupOrgName(String tfrCustomerPickupOrgName) {
		this.tfrCustomerPickupOrgName = tfrCustomerPickupOrgName;
	}

	/**
	 * @return the tfrTargetOrgCode
	 */
	public String getTfrTargetOrgCode() {
		return tfrTargetOrgCode;
	}

	/**
	 * @param tfrTargetOrgCode
	 *            the tfrTargetOrgCode to set
	 */
	public void setTfrTargetOrgCode(String tfrTargetOrgCode) {
		this.tfrTargetOrgCode = tfrTargetOrgCode;
	}

	/**
	 * @return the tfrReceiveMethod
	 */
	public DataDictionaryValueVo getTfrReceiveMethod() {
		return tfrReceiveMethod;
	}

	/**
	 * @param tfrReceiveMethod
	 *            the tfrReceiveMethod to set
	 */
	public void setTfrReceiveMethod(DataDictionaryValueVo tfrReceiveMethod) {
		this.tfrReceiveMethod = tfrReceiveMethod;
	}

	/**
	 * @return the tfrFlightNumberType
	 */
	public DataDictionaryValueVo getTfrFlightNumberType() {
		return tfrFlightNumberType;
	}

	/**
	 * @param tfrFlightNumberType
	 *            the tfrFlightNumberType to set
	 */
	public void setTfrFlightNumberType(DataDictionaryValueVo tfrFlightNumberType) {
		this.tfrFlightNumberType = tfrFlightNumberType;
	}

	/**
	 * @return the tfrBillingType
	 */
	public DataDictionaryValueVo getTfrBillingType() {
		return tfrBillingType;
	}

	/**
	 * @param tfrBillingType
	 *            the tfrBillingType to set
	 */
	public void setTfrBillingType(DataDictionaryValueVo tfrBillingType) {
		this.tfrBillingType = tfrBillingType;
	}

	/**
	 * @return the tfrUnitPrice
	 */
	public BigDecimal getTfrUnitPrice() {
		return tfrUnitPrice;
	}

	/**
	 * @param tfrUnitPrice
	 *            the tfrUnitPrice to set
	 */
	public void setTfrUnitPrice(BigDecimal tfrUnitPrice) {
		this.tfrUnitPrice = tfrUnitPrice;
	}

	/**
	 * @return the tfrFee
	 */
	public BigDecimal getTfrFee() {
		return tfrFee;
	}

	/**
	 * @param tfrFee
	 *            the tfrFee to set
	 */
	public void setTfrFee(BigDecimal tfrFee) {
		this.tfrFee = tfrFee;
	}

	/**
	 * @return the rtnNeedFilter
	 */
	public boolean isRtnNeedFilter() {
		return rtnNeedFilter;
	}

	/**
	 * @param rtnNeedFilter
	 *            the rtnNeedFilter to set
	 */
	public void setRtnNeedFilter(boolean rtnNeedFilter) {
		this.rtnNeedFilter = rtnNeedFilter;
	}

	/**
	 * @return the rtnStartOrgCode
	 */
	public String getRtnStartOrgCode() {
		return rtnStartOrgCode;
	}

	/**
	 * @param rtnStartOrgCode
	 *            the rtnStartOrgCode to set
	 */
	public void setRtnStartOrgCode(String rtnStartOrgCode) {
		this.rtnStartOrgCode = rtnStartOrgCode;
	}

	/**
	 * @return the rtnProductCode
	 */
	public ProductEntityVo getRtnProductCode() {
		return rtnProductCode;
	}

	/**
	 * @param rtnProductCode
	 *            the rtnProductCode to set
	 */
	public void setRtnProductCode(ProductEntityVo rtnProductCode) {
		this.rtnProductCode = rtnProductCode;
	}

	/**
	 * @return the rtnCustomerPickupOrgCode
	 */
	public BranchVo getRtnCustomerPickupOrgCode() {
		return rtnCustomerPickupOrgCode;
	}

	/**
	 * @param rtnCustomerPickupOrgCode
	 *            the rtnCustomerPickupOrgCode to set
	 */
	public void setRtnCustomerPickupOrgCode(BranchVo rtnCustomerPickupOrgCode) {
		this.rtnCustomerPickupOrgCode = rtnCustomerPickupOrgCode;
	}

	/**
	 * @return the rtnCustomerPickupOrgName
	 */
	public String getRtnCustomerPickupOrgName() {
		return rtnCustomerPickupOrgName;
	}

	/**
	 * @param rtnCustomerPickupOrgName
	 *            the rtnCustomerPickupOrgName to set
	 */
	public void setRtnCustomerPickupOrgName(String rtnCustomerPickupOrgName) {
		this.rtnCustomerPickupOrgName = rtnCustomerPickupOrgName;
	}

	/**
	 * @return the rtnTargetOrgCode
	 */
	public String getRtnTargetOrgCode() {
		return rtnTargetOrgCode;
	}

	/**
	 * @param rtnTargetOrgCode
	 *            the rtnTargetOrgCode to set
	 */
	public void setRtnTargetOrgCode(String rtnTargetOrgCode) {
		this.rtnTargetOrgCode = rtnTargetOrgCode;
	}

	/**
	 * @return the rtnReceiveMethod
	 */
	public DataDictionaryValueVo getRtnReceiveMethod() {
		return rtnReceiveMethod;
	}

	/**
	 * @param rtnReceiveMethod
	 *            the rtnReceiveMethod to set
	 */
	public void setRtnReceiveMethod(DataDictionaryValueVo rtnReceiveMethod) {
		this.rtnReceiveMethod = rtnReceiveMethod;
	}

	/**
	 * @return the rtnBillingType
	 */
	public DataDictionaryValueVo getRtnBillingType() {
		return rtnBillingType;
	}

	/**
	 * @param rtnBillingType
	 *            the rtnBillingType to set
	 */
	public void setRtnBillingType(DataDictionaryValueVo rtnBillingType) {
		this.rtnBillingType = rtnBillingType;
	}

	/**
	 * @return the rtnUnitPrice
	 */
	public BigDecimal getRtnUnitPrice() {
		return rtnUnitPrice;
	}

	/**
	 * @param rtnUnitPrice
	 *            the rtnUnitPrice to set
	 */
	public void setRtnUnitPrice(BigDecimal rtnUnitPrice) {
		this.rtnUnitPrice = rtnUnitPrice;
	}

	/**
	 * @return the rtnFee
	 */
	public BigDecimal getRtnFee() {
		return rtnFee;
	}

	/**
	 * @param rtnFee
	 *            the rtnFee to set
	 */
	public void setRtnFee(BigDecimal rtnFee) {
		this.rtnFee = rtnFee;
	}

	/**
	 * @return the deliveryCustomerAreaDto .
	 */
	public AddressFieldDto getDeliveryCustomerAreaDto() {
		return deliveryCustomerAreaDto;
	}

	
	/**
	 *@param deliveryCustomerAreaDto the deliveryCustomerAreaDto to set.
	 */
	public void setDeliveryCustomerAreaDto(AddressFieldDto deliveryCustomerAreaDto) {
		this.deliveryCustomerAreaDto = deliveryCustomerAreaDto;
	}

	
	/**
	 * @return the receiveCustomerAreaDto .
	 */
	public AddressFieldDto getReceiveCustomerAreaDto() {
		return receiveCustomerAreaDto;
	}

	
	/**
	 *@param receiveCustomerAreaDto the receiveCustomerAreaDto to set.
	 */
	public void setReceiveCustomerAreaDto(AddressFieldDto receiveCustomerAreaDto) {
		this.receiveCustomerAreaDto = receiveCustomerAreaDto;
	}

	/**
	 * @return the deliveryCustomerArea .
	 */
	public String getDeliveryCustomerArea() {
		return deliveryCustomerArea;
	}

	
	/**
	 *@param deliveryCustomerArea the deliveryCustomerArea to set.
	 */
	public void setDeliveryCustomerArea(String deliveryCustomerArea) {
		this.deliveryCustomerArea = deliveryCustomerArea;
	}

	
	/**
	 * @return the receiveCustomerArea .
	 */
	public String getReceiveCustomerArea() {
		return receiveCustomerArea;
	}

	
	/**
	 *@param receiveCustomerArea the receiveCustomerArea to set.
	 */
	public void setReceiveCustomerArea(String receiveCustomerArea) {
		this.receiveCustomerArea = receiveCustomerArea;
	}

	/**
	 * @return the stockStatus
	 */
	public WaybillStockStatusDto getStockStatus() {
		return stockStatus;
	}

	/**
	 * @param stockStatus
	 *            the stockStatus to set
	 */
	public void setStockStatus(WaybillStockStatusDto stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * @return the waybillDto
	 */
	public WaybillDto getWaybillDto() {
		return waybillDto;
	}

	/**
	 * @param waybillDto
	 *            the waybillDto to set
	 */
	public void setWaybillDto(WaybillDto waybillDto) {
		this.waybillDto = waybillDto;
	}

	/**
	 * @return the otherChargeVos
	 */
	public List<OtherChargeVo> getOtherChargeVos() {
		return otherChargeVos;
	}

	/**
	 * @param otherChargeVos
	 *            the otherChargeVos to set
	 */
	public void setOtherChargeVos(List<OtherChargeVo> otherChargeVos) {
		this.otherChargeVos = otherChargeVos;
	}

	/**
	 * @return the labeledGoodEntities
	 */
	public List<LabeledGoodEntity> getLabeledGoodEntities() {
		return labeledGoodEntities;
	}

	/**
	 * @param labeledGoodEntities
	 *            the labeledGoodEntities to set
	 */
	public void setLabeledGoodEntities(
			List<LabeledGoodEntity> labeledGoodEntities) {
		this.labeledGoodEntities = labeledGoodEntities;
	}

	/**
	 * @return the selectedLabeledGoodEntities
	 */
	public List<LabeledGoodChangeHistoryDto> getSelectedLabeledGoodEntities() {
		return selectedLabeledGoodEntities;
	}

	/**
	 * @param selectedLabeledGoodEntities
	 *            the selectedLabeledGoodEntities to set
	 */
	public void setSelectedLabeledGoodEntities(
			List<LabeledGoodChangeHistoryDto> selectedLabeledGoodEntities) {
		this.selectedLabeledGoodEntities = selectedLabeledGoodEntities;
	}
	
	/**
	 * @return the labeledGoodChangeHistoryDtoList
	 */
	public List<LabeledGoodChangeHistoryDto> getLabeledGoodChangeHistoryDtoList() {
		return labeledGoodChangeHistoryDtoList;
	}

	/**
	 * @param labeledGoodChangeHistoryDtoList the labeledGoodChangeHistoryDtoList to set
	 */
	public void setLabeledGoodChangeHistoryDtoList(
			List<LabeledGoodChangeHistoryDto> labeledGoodChangeHistoryDtoList) {
		this.labeledGoodChangeHistoryDtoList = labeledGoodChangeHistoryDtoList;
	}

	/**
	 * @return the paper
	 */
	public Integer getPaper() {
		return paper;
	}

	/**
	 * @param paper
	 *            the paper to set
	 */
	public void setPaper(Integer paper) {
		this.paper = paper;
	}

	/**
	 * @return the wood
	 */
	public Integer getWood() {
		return wood;
	}

	/**
	 * @param wood
	 *            the wood to set
	 */
	public void setWood(Integer wood) {
		this.wood = wood;
	}

	/**
	 * @return the fibre
	 */
	public Integer getFibre() {
		return fibre;
	}

	/**
	 * @param fibre
	 *            the fibre to set
	 */
	public void setFibre(Integer fibre) {
		this.fibre = fibre;
	}

	/**
	 * @return the salver
	 */
	public Integer getSalver() {
		return salver;
	}

	/**
	 * @param salver
	 *            the salver to set
	 */
	public void setSalver(Integer salver) {
		this.salver = salver;
	}

	/**
	 * @return the membrane
	 */
	public Integer getMembrane() {
		return membrane;
	}

	/**
	 * @param membrane
	 *            the membrane to set
	 */
	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}

	/**
	 * @return the otherPackage
	 */
	public String getOtherPackage() {
		return otherPackage;
	}

	/**
	 * @param otherPackage
	 *            the otherPackage to set
	 */
	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}

	/**
	 * @return the handWriteMoney
	 */
	public BigDecimal getHandWriteMoney() {
		return handWriteMoney;
	}

	/**
	 * @param handWriteMoney
	 *            the handWriteMoney to set
	 */
	public void setHandWriteMoney(BigDecimal handWriteMoney) {
		this.handWriteMoney = handWriteMoney;
	}

	/**
	 * @return the yokeVo
	 */
	public WoodYokeVo getYokeVo() {
		return yokeVo;
	}

	/**
	 * @param yokeVo
	 *            the yokeVo to set
	 */
	public void setYokeVo(WoodYokeVo yokeVo) {
		this.yokeVo = yokeVo;
	}

	/**
	 * @return the transportationRemark
	 */
	public String getTransportationRemark() {
		return transportationRemark;
	}

	/**
	 * @param transportationRemark
	 *            the transportationRemark to set
	 */
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}

	/**
	 * @return the longOrShort
	 */
	public String getLongOrShort() {
		return longOrShort;
	}

	/**
	 * @param longOrShort
	 *            the longOrShort to set
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	/**
	 * @return the transportFeeCanvas
	 */
	public String getTransportFeeCanvas() {
		return transportFeeCanvas;
	}

	/**
	 * @param transportFeeCanvas
	 *            the transportFeeCanvas to set
	 */
	public void setTransportFeeCanvas(String transportFeeCanvas) {
		//四舍五入取整数
		this.transportFeeCanvas = transportFeeCanvas;
	}

	/**
	 * @return the insuranceAmountCanvas
	 */
	public String getInsuranceAmountCanvas() {
		return insuranceAmountCanvas;
	}

	/**
	 * @param insuranceAmountCanvas
	 *            the insuranceAmountCanvas to set
	 */
	public void setInsuranceAmountCanvas(String insuranceAmountCanvas) {
		//四舍五入取整数
		this.insuranceAmountCanvas = CalculateFeeTotalUtils.formatNumberInteger(insuranceAmountCanvas);
	}

	/**
	 * @return the codAmountCanvas
	 */
	public String getCodAmountCanvas() {
		return codAmountCanvas;
	}

	/**
	 * @param codAmountCanvas
	 *            the codAmountCanvas to set
	 */
	public void setCodAmountCanvas(String codAmountCanvas) {
		//四舍五入取整数
//		this.codAmountCanvas = CalculateFeeTotalUtils.formatNumberInteger(codAmountCanvas);
//		liyongfei 代收货款精确到角，不进行四舍五入
		this.codAmountCanvas = codAmountCanvas;
	}

	/**
	 * @return the pickUpFeeCanvas
	 */
	public String getPickUpFeeCanvas() {
		return pickUpFeeCanvas;
	}

	/**
	 * @param pickUpFeeCanvas
	 *            the pickUpFeeCanvas to set
	 */
	public void setPickUpFeeCanvas(String pickUpFeeCanvas) {
		//四舍五入取整数
		this.pickUpFeeCanvas = CalculateFeeTotalUtils.formatNumberInteger(pickUpFeeCanvas);
	}

	/**
	 * @return the deliveryGoodsFeeCanvas
	 */
	public String getDeliveryGoodsFeeCanvas() {
		return deliveryGoodsFeeCanvas;
	}

	/**
	 * @param deliveryGoodsFeeCanvas
	 *            the deliveryGoodsFeeCanvas to set
	 */
	public void setDeliveryGoodsFeeCanvas(String deliveryGoodsFeeCanvas) {
		//四舍五入取整数
		this.deliveryGoodsFeeCanvas = CalculateFeeTotalUtils.formatNumberInteger(deliveryGoodsFeeCanvas);
	}
	

	/**
	 * @return the packageFeeCanvas
	 */
	public String getPackageFeeCanvas() {
		return packageFeeCanvas;
	}

	/**
	 * @param packageFeeCanvas
	 *            the packageFeeCanvas to set
	 */
	public void setPackageFeeCanvas(String packageFeeCanvas) {
		//四舍五入取整数
		this.packageFeeCanvas = CalculateFeeTotalUtils.formatNumberInteger(packageFeeCanvas);
	}

	/**
	 * @return the serviceFeeCanvas
	 */
	public String getServiceFeeCanvas() {
		return serviceFeeCanvas;
	}

	/**
	 * @param serviceFeeCanvas
	 *            the serviceFeeCanvas to set
	 */
	public void setServiceFeeCanvas(String serviceFeeCanvas) {
		//四舍五入取整数
		this.serviceFeeCanvas = CalculateFeeTotalUtils.formatNumberInteger(serviceFeeCanvas);
	}

	/**
	 * @return the otherFeeCanvas
	 */
	public String getOtherFeeCanvas() {
		return otherFeeCanvas;
	}

	/**
	 * @param otherFeeCanvas
	 *            the otherFeeCanvas to set
	 */
	public void setOtherFeeCanvas(String otherFeeCanvas) {
		//四舍五入取整数
		this.otherFeeCanvas = CalculateFeeTotalUtils.formatNumberInteger(otherFeeCanvas);
	}

	/**
	 * @return the promotionsFeeCanvas
	 */
	public String getPromotionsFeeCanvas() {
		return promotionsFeeCanvas;
	}

	/**
	 * @param promotionsFeeCanvas
	 *            the promotionsFeeCanvas to set
	 */
	public void setPromotionsFeeCanvas(String promotionsFeeCanvas) {
		//四舍五入取整数
		this.promotionsFeeCanvas = CalculateFeeTotalUtils.formatNumberInteger(promotionsFeeCanvas);
	}

	/**
	 * @return the totalFeeCanvas
	 */
	public String getTotalFeeCanvas() {
		return totalFeeCanvas;
	}

	/**
	 * @param totalFeeCanvas
	 *            the totalFeeCanvas to set
	 */
	public void setTotalFeeCanvas(String totalFeeCanvas) {
		//四舍五入取整数
		this.totalFeeCanvas = totalFeeCanvas;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the chargeMode
	 */
	public Boolean getChargeMode() {
		return chargeMode;
	}

	/**
	 * @param chargeMode
	 *            the chargeMode to set
	 */
	public void setChargeMode(Boolean chargeMode) {
		this.chargeMode = chargeMode;
	}

	/**
	 * @return the deliveryCustomerId
	 */
	public String getDeliveryCustomerId() {
		return deliveryCustomerId;
	}

	/**
	 * @param deliveryCustomerId
	 *            the deliveryCustomerId to set
	 */
	public void setDeliveryCustomerId(String deliveryCustomerId) {
		this.deliveryCustomerId = deliveryCustomerId;
	}

	/**
	 * @return the deliveryCustomerCode
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * @param deliveryCustomerCode
	 *            the deliveryCustomerCode to set
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * @return the deliveryCustomerName
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * @param deliveryCustomerName
	 *            the deliveryCustomerName to set
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * @return the deliveryCustomerMobilephone
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * @param deliveryCustomerMobilephone
	 *            the deliveryCustomerMobilephone to set
	 */
	public void setDeliveryCustomerMobilephone(
			String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * @return the deliveryCustomerPhone
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * @param deliveryCustomerPhone
	 *            the deliveryCustomerPhone to set
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * @return the deliveryCustomerContact
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * @param deliveryCustomerContact
	 *            the deliveryCustomerContact to set
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * @return the deliveryCustomerContactId
	 */
	public String getDeliveryCustomerContactId() {
		return deliveryCustomerContactId;
	}

	/**
	 * @param deliveryCustomerContactId
	 *            the deliveryCustomerContactId to set
	 */
	public void setDeliveryCustomerContactId(String deliveryCustomerContactId) {
		this.deliveryCustomerContactId = deliveryCustomerContactId;
	}

	/**
	 * @return the deliveryCustomerNationCode
	 */
	public String getDeliveryCustomerNationCode() {
		return deliveryCustomerNationCode;
	}

	/**
	 * @param deliveryCustomerNationCode
	 *            the deliveryCustomerNationCode to set
	 */
	public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
		this.deliveryCustomerNationCode = deliveryCustomerNationCode;
	}

	/**
	 * @return the deliveryCustomerProvCode
	 */
	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	/**
	 * @param deliveryCustomerProvCode
	 *            the deliveryCustomerProvCode to set
	 */
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	/**
	 * @return the deliveryCustomerCityCode
	 */
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	/**
	 * @param deliveryCustomerCityCode
	 *            the deliveryCustomerCityCode to set
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	/**
	 * @return the deliveryCustomerDistCode
	 */
	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}

	/**
	 * @param deliveryCustomerDistCode
	 *            the deliveryCustomerDistCode to set
	 */
	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}

	/**
	 * @return the deliveryCustomerAddress
	 */
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	/**
	 * @param deliveryCustomerAddress
	 *            the deliveryCustomerAddress to set
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	/**
	 * @return the receiveCustomerId
	 */
	public String getReceiveCustomerId() {
		return receiveCustomerId;
	}

	/**
	 * @param receiveCustomerId
	 *            the receiveCustomerId to set
	 */
	public void setReceiveCustomerId(String receiveCustomerId) {
		this.receiveCustomerId = receiveCustomerId;
	}

	/**
	 * @return the receiveCustomerCode
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * @param receiveCustomerCode
	 *            the receiveCustomerCode to set
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName
	 *            the receiveCustomerName to set
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return the receiveCustomerMobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * @param receiveCustomerMobilephone
	 *            the receiveCustomerMobilephone to set
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone
	 *            the receiveCustomerPhone to set
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return the receiveCustomerContact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact
	 *            the receiveCustomerContact to set
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return the receiveCustomerContactId
	 */
	public String getReceiveCustomerContactId() {
		return receiveCustomerContactId;
	}

	/**
	 * @param receiveCustomerContactId
	 *            the receiveCustomerContactId to set
	 */
	public void setReceiveCustomerContactId(String receiveCustomerContactId) {
		this.receiveCustomerContactId = receiveCustomerContactId;
	}

	/**
	 * @return the receiveCustomerNationCode
	 */
	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}

	/**
	 * @param receiveCustomerNationCode
	 *            the receiveCustomerNationCode to set
	 */
	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
	}

	/**
	 * @return the receiveCustomerProvCode
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * @param receiveCustomerProvCode
	 *            the receiveCustomerProvCode to set
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * @return the receiveCustomerCityCode
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * @param receiveCustomerCityCode
	 *            the receiveCustomerCityCode to set
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * @return the receiveCustomerDistCode
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * @param receiveCustomerDistCode
	 *            the receiveCustomerDistCode to set
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	/**
	 * @return the receiveCustomerAddress
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * @param receiveCustomerAddress
	 *            the receiveCustomerAddress to set
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode
	 *            the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the receiveOrgName
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * @param receiveOrgName
	 *            the receiveOrgName to set
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * @return the productCode
	 */
	public ProductEntityVo getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setProductCode(ProductEntityVo productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the receiveMethod
	 */
	public DataDictionaryValueVo getReceiveMethod() {
		return receiveMethod;
	}
	
	/**
	 * @param receiveMethod
	 *            the receiveMethod to set
	 */
	public void setReceiveMethod(DataDictionaryValueVo receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return the customerPickupOrgCode
	 */
	public BranchVo getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * @param customerPickupOrgCode
	 *            the customerPickupOrgCode to set
	 */
	public void setCustomerPickupOrgCode(BranchVo customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * @return the customerPickupOrgName
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	/**
	 * @param customerPickupOrgName
	 *            the customerPickupOrgName to set
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	/**
	 * @return the loadMethod
	 */
	public String getLoadMethod() {
		return loadMethod;
	}

	/**
	 * @param loadMethod
	 *            the loadMethod to set
	 */
	public void setLoadMethod(String loadMethod) {
		this.loadMethod = loadMethod;
	}

	/**
	 * @return the targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * @param targetOrgCode
	 *            the targetOrgCode to set
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * @return the pickupToDoor
	 */
	public Boolean getPickupToDoor() {
		return pickupToDoor;
	}

	/**
	 * @param pickupToDoor
	 *            the pickupToDoor to set
	 */
	public void setPickupToDoor(Boolean pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	/**
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode
	 *            the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * @return the pickupCentralized
	 */
	public Boolean getPickupCentralized() {
		return pickupCentralized;
	}

	/**
	 * @param pickupCentralized
	 *            the pickupCentralized to set
	 */
	public void setPickupCentralized(Boolean pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	/**
	 * @return the loadLineCode
	 */
	public String getLoadLineCode() {
		return loadLineCode;
	}

	/**
	 * @param loadLineCode
	 *            the loadLineCode to set
	 */
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}

	/**
	 * @return the loadLineName
	 */
	public String getLoadLineName() {
		return loadLineName;
	}

	/**
	 * @param loadLineName
	 *            the loadLineName to set
	 */
	public void setLoadLineName(String loadLineName) {
		this.loadLineName = loadLineName;
	}

	/**
	 * @return the loadOrgCode
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * @param loadOrgCode
	 *            the loadOrgCode to set
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * @return the loadOrgName
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}

	/**
	 * @param loadOrgName
	 *            the loadOrgName to set
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}

	/**
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param lastLoadOrgCode
	 *            the lastLoadOrgCode to set
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * @return the lastLoadOrgName
	 */
	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	/**
	 * @param lastLoadOrgName
	 *            the lastLoadOrgName to set
	 */
	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	/**
	 * @return the preDepartureTime
	 */
	public Date getPreDepartureTime() {
		return preDepartureTime;
	}

	/**
	 * @param preDepartureTime
	 *            the preDepartureTime to set
	 */
	public void setPreDepartureTime(Date preDepartureTime) {
		this.preDepartureTime = preDepartureTime;
	}

	/**
	 * @return the preCustomerPickupTime
	 */
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	/**
	 * @param preCustomerPickupTime
	 *            the preCustomerPickupTime to set
	 */
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	/**
	 * @return the carDirectDelivery
	 */
	public Boolean getCarDirectDelivery() {
		return carDirectDelivery;
	}

	/**
	 * @param carDirectDelivery
	 *            the carDirectDelivery to set
	 */
	public void setCarDirectDelivery(Boolean carDirectDelivery) {
		this.carDirectDelivery = carDirectDelivery;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal
	 *            the goodsQtyTotal to set
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return the goodsWeightTotal
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * @param goodsWeightTotal
	 *            the goodsWeightTotal to set
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal
	 *            the goodsVolumeTotal to set
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the goodsSize
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * @param goodsSize
	 *            the goodsSize to set
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	/**
	 * @return the goodsType
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType
	 *            the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * @return the preciousGoods
	 */
	public Boolean getPreciousGoods() {
		return preciousGoods;
	}

	/**
	 * @param preciousGoods
	 *            the preciousGoods to set
	 */
	public void setPreciousGoods(Boolean preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	/**
	 * @return the specialShapedGoods
	 */
	public Boolean getSpecialShapedGoods() {
		return specialShapedGoods;
	}

	/**
	 * @param specialShapedGoods
	 *            the specialShapedGoods to set
	 */
	public void setSpecialShapedGoods(Boolean specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}

	/**
	 * @return the outerNotes
	 */
	public DataDictionaryValueVo getOuterNotes() {
		return outerNotes;
	}

	/**
	 * @param outerNotes
	 *            the outerNotes to set
	 */
	public void setOuterNotes(DataDictionaryValueVo outerNotes) {
		this.outerNotes = outerNotes;
	}

	/**
	 * @return the innerNotes
	 */
	public String getInnerNotes() {
		return innerNotes;
	}

	/**
	 * @param innerNotes
	 *            the innerNotes to set
	 */
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	/**
	 * @return the goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * @param goodsPackage
	 *            the goodsPackage to set
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * @return the insuranceId
	 */
	public String getInsuranceId() {
		return insuranceId;
	}

	/**
	 * @param insuranceId
	 *            the insuranceId to set
	 */
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	/**
	 * @return the insuranceCode
	 */
	public String getInsuranceCode() {
		return insuranceCode;
	}

	/**
	 * @param insuranceCode
	 *            the insuranceCode to set
	 */
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	/**
	 * @return the insuranceAmount
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount
	 *            the insuranceAmount to set
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		//四舍五入取整数
		this.insuranceAmount = CalculateFeeTotalUtils.formatNumberInteger(insuranceAmount);
	}

	/**
	 * @return the insuranceRate
	 */
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}

	/**
	 * @param insuranceRate
	 *            the insuranceRate to set
	 */
	public void setInsuranceRate(BigDecimal insuranceRate) {
		//四舍五入保留两位小数
		this.insuranceRate = CalculateFeeTotalUtils.formatNumberTwoDecimal(insuranceRate);
	}

	/**
	 * @return the insuranceFee
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * @param insuranceFee
	 *            the insuranceFee to set
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		//四舍五入取整数
		this.insuranceFee = CalculateFeeTotalUtils.formatNumberInteger(insuranceFee);
	}

	/**
	 * @return the maxInsuranceAmount
	 */
	public BigDecimal getMaxInsuranceAmount() {
		return maxInsuranceAmount;
	}

	/**
	 * @param maxInsuranceAmount
	 *            the maxInsuranceAmount to set
	 */
	public void setMaxInsuranceAmount(BigDecimal maxInsuranceAmount) {
		this.maxInsuranceAmount = maxInsuranceAmount;
	}

	/**
	 * @return the mixInsuranceAmount
	 */
	public BigDecimal getMixInsuranceAmount() {
		return mixInsuranceAmount;
	}

	/**
	 * @param mixInsuranceAmount
	 *            the mixInsuranceAmount to set
	 */
	public void setMixInsuranceAmount(BigDecimal mixInsuranceAmount) {
		this.mixInsuranceAmount = mixInsuranceAmount;
	}

	/**
	 * @return the codId
	 */
	public String getCodId() {
		return codId;
	}

	/**
	 * @param codId
	 *            the codId to set
	 */
	public void setCodId(String codId) {
		this.codId = codId;
	}

	/**
	 * @return the codCode
	 */
	public String getCodCode() {
		return codCode;
	}

	/**
	 * @param codCode
	 *            the codCode to set
	 */
	public void setCodCode(String codCode) {
		this.codCode = codCode;
	}

	/**
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount
	 *            the codAmount to set
	 */
	public void setCodAmount(BigDecimal codAmount) {
		//四舍五入取整数
//		this.codAmount = CalculateFeeTotalUtils.formatNumberInteger(codAmount);
//		liyongfei 代收货款精确到角，不进行四舍五入
		this.codAmount = codAmount;
	}

	/**
	 * @return the codRate
	 */
	public BigDecimal getCodRate() {
		return codRate;
	}

	/**
	 * @param codRate
	 *            the codRate to set
	 */
	public void setCodRate(BigDecimal codRate) {
		//四舍五入保留两位小数
		this.codRate = CalculateFeeTotalUtils.formatNumberTwoDecimal(codRate);
	}

	/**
	 * @return the codFee
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee
	 *            the codFee to set
	 */
	public void setCodFee(BigDecimal codFee) {
		//四舍五入取整数
		this.codFee = CalculateFeeTotalUtils.formatNumberInteger(codFee);
	}

	/**
	 * @return the refundType
	 */
	public DataDictionaryValueVo getRefundType() {
		return refundType;
	}

	/**
	 * @param refundType
	 *            the refundType to set
	 */
	public void setRefundType(DataDictionaryValueVo refundType) {
		this.refundType = refundType;
	}

	/**
	 * @return the returnBillType
	 */
	public DataDictionaryValueVo getReturnBillType() {
		return returnBillType;
	}

	/**
	 * @param returnBillType
	 *            the returnBillType to set
	 */
	public void setReturnBillType(DataDictionaryValueVo returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * @return the returnBillChargeName
	 */
	public String getReturnBillChargeName() {
		return returnBillChargeName;
	}

	/**
	 * @param returnBillChargeName
	 *            the returnBillChargeName to set
	 */
	public void setReturnBillChargeName(String returnBillChargeName) {
		this.returnBillChargeName = returnBillChargeName;
	}

	/**
	 * @return the secretPrepaid
	 */
	public Boolean getSecretPrepaid() {
		return secretPrepaid;
	}

	/**
	 * @param secretPrepaid
	 *            the secretPrepaid to set
	 */
	public void setSecretPrepaid(Boolean secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}

	/**
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount
	 *            the toPayAmount to set
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		//四舍五入取整数
//		this.toPayAmount = CalculateFeeTotalUtils.formatNumberInteger(toPayAmount);
		//liyongfei 精确到角
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the prePayAmount
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount
	 *            the prePayAmount to set
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		//四舍五入取整数
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return the deliveryGoodsFeeId
	 */
	public String getDeliveryGoodsFeeId() {
		return deliveryGoodsFeeId;
	}

	/**
	 * @param deliveryGoodsFeeId
	 *            the deliveryGoodsFeeId to set
	 */
	public void setDeliveryGoodsFeeId(String deliveryGoodsFeeId) {
		this.deliveryGoodsFeeId = deliveryGoodsFeeId;
	}

	/**
	 * @return the deliveryGoodsFeeCode
	 */
	public String getDeliveryGoodsFeeCode() {
		return deliveryGoodsFeeCode;
	}

	/**
	 * @param deliveryGoodsFeeCode
	 *            the deliveryGoodsFeeCode to set
	 */
	public void setDeliveryGoodsFeeCode(String deliveryGoodsFeeCode) {
		this.deliveryGoodsFeeCode = deliveryGoodsFeeCode;
	}

	/**
	 * @return the deliveryGoodsFee
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * @param deliveryGoodsFee
	 *            the deliveryGoodsFee to set
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		//四舍五入取整数
		this.deliveryGoodsFee = CalculateFeeTotalUtils.formatNumberInteger(deliveryGoodsFee);
	}

	/**
	 * @return the otherFee
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * @param otherFee
	 *            the otherFee to set
	 */
	public void setOtherFee(BigDecimal otherFee) {
		//四舍五入取整数
		this.otherFee = CalculateFeeTotalUtils.formatNumberInteger(otherFee);
	}

	/**
	 * @return the packageFee
	 */
	public BigDecimal getPackageFee() {
		return packageFee;
	}

	/**
	 * @param packageFee
	 *            the packageFee to set
	 */
	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}

	/**
	 * @return the promotionsFee
	 */
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	/**
	 * @param promotionsFee
	 *            the promotionsFee to set
	 */
	public void setPromotionsFee(BigDecimal promotionsFee) {
		//四舍五入取整数
		this.promotionsFee = CalculateFeeTotalUtils.formatNumberInteger(promotionsFee);
	}

	/**
	 * @return the billingType
	 */
	public DataDictionaryValueVo getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType
	 *            the billingType to set
	 */
	public void setBillingType(DataDictionaryValueVo billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		//四舍五入保留两位小数 
		this.unitPrice = CalculateFeeTotalUtils.formatNumberTwoDecimal(unitPrice);
	}

	/**
	 * @return the transportFee
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * @param transportFee
	 *            the transportFee to set
	 */
	public void setTransportFee(BigDecimal transportFee) {
		//四舍五入取整数
		this.transportFee = transportFee;
	}

	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	/**
	 * @return the valueAddFee
	 */
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	/**
	 * @param valueAddFee
	 *            the valueAddFee to set
	 */
	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	/**
	 * @return the paidMethod
	 */
	public DataDictionaryValueVo getPaidMethod() {
		return paidMethod;
	}

	/**
	 * @param paidMethod
	 *            the paidMethod to set
	 */
	public void setPaidMethod(DataDictionaryValueVo paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * @return the arriveType
	 */
	public String getArriveType() {
		return arriveType;
	}

	/**
	 * @param arriveType
	 *            the arriveType to set
	 */
	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the forbiddenLine
	 */
	public String getForbiddenLine() {
		return forbiddenLine;
	}

	/**
	 * @param forbiddenLine
	 *            the forbiddenLine to set
	 */
	public void setForbiddenLine(String forbiddenLine) {
		this.forbiddenLine = forbiddenLine;
	}

	/**
	 * @return the freightMethod
	 */
	public DataDictionaryValueVo getFreightMethod() {
		return freightMethod;
	}

	/**
	 * @param freightMethod
	 *            the freightMethod to set
	 */
	public void setFreightMethod(DataDictionaryValueVo freightMethod) {
		this.freightMethod = freightMethod;
	}

	/**
	 * @return the flightNumberType
	 */
	public DataDictionaryValueVo getFlightNumberType() {
		return flightNumberType;
	}

	/**
	 * @param flightNumberType
	 *            the flightNumberType to set
	 */
	public void setFlightNumberType(DataDictionaryValueVo flightNumberType) {
		this.flightNumberType = flightNumberType;
	}

	/**
	 * @return the flightShift
	 */
	public String getFlightShift() {
		return flightShift;
	}

	/**
	 * @param flightShift
	 *            the flightShift to set
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	/**
	 * @return the totalFee
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee
	 *            the totalFee to set
	 */
	public void setTotalFee(BigDecimal totalFee) {
		//四舍五入取整数
//		this.totalFee = CalculateFeeTotalUtils.formatNumberInteger(totalFee);
		this.totalFee = totalFee;
	}

	/**
	 * @return the promotionsCode
	 */
	public String getPromotionsCode() {
		return promotionsCode;
	}

	/**
	 * @param promotionsCode
	 *            the promotionsCode to set
	 */
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}

	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 *            the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 *            the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode
	 *            the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the modifyOrgCode
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * @param modifyOrgCode
	 *            the modifyOrgCode to set
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * @return the refId
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * @param refId
	 *            the refId to set
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * @return the refCode
	 */
	public String getRefCode() {
		return refCode;
	}

	/**
	 * @param refCode
	 *            the refCode to set
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the isWholeVehicle
	 */
	public Boolean getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * @param isWholeVehicle
	 *            the isWholeVehicle to set
	 */
	public void setIsWholeVehicle(Boolean isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * @return the isPassDept
	 */
	public Boolean getIsPassDept() {
		return isPassDept;
	}

	/**
	 * @param isPassDept
	 *            the isPassDept to set
	 */
	public void setIsPassDept(Boolean isPassDept) {
		this.isPassDept = isPassDept;
	}

	/**
	 * @return the vehicleNumber
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}

	/**
	 * @param vehicleNumber
	 *            the vehicleNumber to set
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	/**
	 * @return the wholeVehicleAppfee
	 */
	public BigDecimal getWholeVehicleAppfee() {
		return wholeVehicleAppfee;
	}

	/**
	 * @param wholeVehicleAppfee
	 *            the wholeVehicleAppfee to set
	 */
	public void setWholeVehicleAppfee(BigDecimal wholeVehicleAppfee) {
		this.wholeVehicleAppfee = wholeVehicleAppfee;
	}

	/**
	 * @return the wholeVehicleActualfee
	 */
	public BigDecimal getWholeVehicleActualfee() {
		return wholeVehicleActualfee;
	}

	/**
	 * @param wholeVehicleActualfee
	 *            the wholeVehicleActualfee to set
	 */
	public void setWholeVehicleActualfee(BigDecimal wholeVehicleActualfee) {
		this.wholeVehicleActualfee = wholeVehicleActualfee;
	}

	/**
	 * @return the packageOrgCode
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	/**
	 * @param packageOrgCode
	 *            the packageOrgCode to set
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	/**
	 * @return the packingOrganizationName
	 */
	public String getPackingOrganizationName() {
		return packingOrganizationName;
	}

	/**
	 * @param packingOrganizationName
	 *            the packingOrganizationName to set
	 */
	public void setPackingOrganizationName(String packingOrganizationName) {
		this.packingOrganizationName = packingOrganizationName;
	}

	/**
	 * @return the doPacking
	 */
	public String getDoPacking() {
		return doPacking;
	}

	/**
	 * @param doPacking
	 *            the doPacking to set
	 */
	public void setDoPacking(String doPacking) {
		this.doPacking = doPacking;
	}

	/**
	 * @return the standGoodsNum
	 */
	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	/**
	 * @param standGoodsNum
	 *            the standGoodsNum to set
	 */
	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	/**
	 * @return the standRequirement
	 */
	public String getStandRequirement() {
		return standRequirement;
	}

	/**
	 * @param standRequirement
	 *            the standRequirement to set
	 */
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	/**
	 * @return the standGoodsSize
	 */
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	/**
	 * @param standGoodsSize
	 *            the standGoodsSize to set
	 */
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	/**
	 * @return the standGoodsVolume
	 */
	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	/**
	 * @param standGoodsVolume
	 *            the standGoodsVolume to set
	 */
	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	/**
	 * @return the standCharge
	 */
	public BigDecimal getStandCharge() {
		return standCharge;
	}

	/**
	 * @param standCharge
	 *            the standCharge to set
	 */
	public void setStandCharge(BigDecimal standCharge) {
		this.standCharge = standCharge;
	}

	/**
	 * @return the boxGoodsNum
	 */
	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	/**
	 * @param boxGoodsNum
	 *            the boxGoodsNum to set
	 */
	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	/**
	 * @return the boxRequirement
	 */
	public String getBoxRequirement() {
		return boxRequirement;
	}

	/**
	 * @param boxRequirement
	 *            the boxRequirement to set
	 */
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	/**
	 * @return the boxGoodsSize
	 */
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	/**
	 * @param boxGoodsSize
	 *            the boxGoodsSize to set
	 */
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	/**
	 * @return the boxGoodsVolume
	 */
	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	/**
	 * @param boxGoodsVolume
	 *            the boxGoodsVolume to set
	 */
	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}

	/**
	 * @return the boxCharge
	 */
	public BigDecimal getBoxCharge() {
		return boxCharge;
	}

	/**
	 * @param boxCharge
	 *            the boxCharge to set
	 */
	public void setBoxCharge(BigDecimal boxCharge) {
		this.boxCharge = boxCharge;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode
	 *            the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountBank
	 */
	public String getAccountBank() {
		return accountBank;
	}

	/**
	 * @param accountBank
	 *            the accountBank to set
	 */
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	/**
	 * @return the billWeight
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * @param billWeight
	 *            the billWeight to set
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * @return the pickupFee
	 */
	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	/**
	 * @param pickupFee
	 *            the pickupFee to set
	 */
	public void setPickupFee(BigDecimal pickupFee) {
		//四舍五入取整数
		this.pickupFee = CalculateFeeTotalUtils.formatNumberInteger(pickupFee);
	}

	/**
	 * @return the serviceFeeOld
	 */
	public BigDecimal getServiceFeeOld() {
		return serviceFeeOld;
	}

	/**
	 * @param serviceFeeOld
	 *            the serviceFeeOld to set
	 */
	public void setServiceFeeOld(BigDecimal serviceFeeOld) {
		this.serviceFeeOld = serviceFeeOld;
	}

	/**
	 * @return the serviceFee
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	/**
	 * @param serviceFee
	 *            the serviceFee to set
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		//四舍五入取整数
		this.serviceFee = CalculateFeeTotalUtils.formatNumberInteger(serviceFee);
	}

	/**
	 * @return the serviceFeeRate
	 */
	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}

	/**
	 * @param serviceFeeRate
	 *            the serviceFeeRate to set
	 */
	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	/**
	 * @return the preArriveTime
	 */
	public Date getPreArriveTime() {
		return preArriveTime;
	}

	/**
	 * @param preArriveTime
	 *            the preArriveTime to set
	 */
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @param transportType
	 *            the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * @return the printTimes
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}

	/**
	 * @param printTimes
	 *            the printTimes to set
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * @return the isPdaBill
	 */
	public Boolean getIsPdaBill() {
		return isPdaBill;
	}

	/**
	 * @param isPdaBill
	 *            the isPdaBill to set
	 */
	public void setIsPdaBill(Boolean isPdaBill) {
		this.isPdaBill = isPdaBill;
	}

	/**
	 * @return the billTime
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime
	 *            the billTime to set
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return the waybillstatus
	 */
	public String getWaybillstatus() {
		return waybillstatus;
	}

	/**
	 * @param waybillstatus
	 *            the waybillstatus to set
	 */
	public void setWaybillstatus(String waybillstatus) {
		this.waybillstatus = waybillstatus;
	}

	/**
	 * @return the derelictNum
	 */
	public List<String> getDerelictNum() {
		return derelictNum;
	}

	/**
	 * @param derelictNum
	 *            the derelictNum to set
	 */
	public void setDerelictNum(List<String> derelictNum) {
		this.derelictNum = derelictNum;
	}

	
	/**
	 * @return the tfrNeedHandWrite
	 */
	public boolean isTfrNeedHandWrite() {
		return tfrNeedHandWrite;
	}

	
	/**
	 * @param tfrNeedHandWrite the tfrNeedHandWrite to set
	 */
	public void setTfrNeedHandWrite(boolean tfrNeedHandWrite) {
		this.tfrNeedHandWrite = tfrNeedHandWrite;
	}

	
	/**
	 * @return the rtnNeedHandWrite
	 */
	public boolean isRtnNeedHandWrite() {
		return rtnNeedHandWrite;
	}

	
	/**
	 * @param rtnNeedHandWrite the rtnNeedHandWrite to set
	 */
	public void setRtnNeedHandWrite(boolean rtnNeedHandWrite) {
		this.rtnNeedHandWrite = rtnNeedHandWrite;
	}

	
	/**
	 * @return the isOtherChargeChanged
	 */
	public boolean isOtherChargeChanged() {
		return isOtherChargeChanged;
	}

	
	/**
	 * @param isOtherChargeChanged the isOtherChargeChanged to set
	 */
	public void setOtherChargeChanged(boolean isOtherChargeChanged) {
		this.isOtherChargeChanged = isOtherChargeChanged;
	}


	/**
	 * @return the tfrFreightMethod
	 */
	public DataDictionaryValueVo getTfrFreightMethod() {
		return tfrFreightMethod;
	}


	/**
	 * @param tfrFreightMethod the tfrFreightMethod to set
	 */
	public void setTfrFreightMethod(DataDictionaryValueVo tfrFreightMethod) {
		this.tfrFreightMethod = tfrFreightMethod;
	}


	/**
	 * @return the tfrFlightShift
	 */
	public String getTfrFlightShift() {
		return tfrFlightShift;
	}


	/**
	 * @param tfrFlightShift the tfrFlightShift to set
	 */
	public void setTfrFlightShift(String tfrFlightShift) {
		this.tfrFlightShift = tfrFlightShift;
	}


	
	/**
	 * @return the orgInfoDto
	 */
	public OrgInfoDto getOrgInfoDto() {
		return orgInfoDto;
	}


	
	/**
	 * @param orgInfoDto the orgInfoDto to set
	 */
	public void setOrgInfoDto(OrgInfoDto orgInfoDto) {
		this.orgInfoDto = orgInfoDto;
	}


	public void setDepartServiceFeeEnable(boolean departServiceFeeEnable) {
		this.departServiceFeeEnable = departServiceFeeEnable;
	}


	
	/**
	 * @return the departServiceFeeEnable
	 */
	public boolean getDepartServiceFeeEnable() {
		return departServiceFeeEnable;
	}

	
	/**
	 * @return the boxChargeId
	 */
	public String getBoxChargeId() {
		return boxChargeId;
	}


	
	/**
	 * @param boxChargeId the boxChargeId to set
	 */
	public void setBoxChargeId(String boxChargeId) {
		this.boxChargeId = boxChargeId;
	}


	
	/**
	 * @return the boxChargeCode
	 */
	public String getBoxChargeCode() {
		return boxChargeCode;
	}


	
	/**
	 * @param boxChargeCode the boxChargeCode to set
	 */
	public void setBoxChargeCode(String boxChargeCode) {
		this.boxChargeCode = boxChargeCode;
	}


	
	/**
	 * @return the wholeVehicleActualfeeFlowRangeUp
	 */
	public BigDecimal getWholeVehicleActualfeeFlowRangeUp() {
		return wholeVehicleActualfeeFlowRangeUp;
	}


	
	/**
	 * @param wholeVehicleActualfeeFlowRangeUp the wholeVehicleActualfeeFlowRangeUp to set
	 */
	public void setWholeVehicleActualfeeFlowRangeUp(
			BigDecimal wholeVehicleActualfeeFlowRangeUp) {
		this.wholeVehicleActualfeeFlowRangeUp = wholeVehicleActualfeeFlowRangeUp;
	}


	
	/**
	 * @return the wholeVehicleActualfeeFlowRangeLow
	 */
	public BigDecimal getWholeVehicleActualfeeFlowRangeLow() {
		return wholeVehicleActualfeeFlowRangeLow;
	}


	
	/**
	 * @param wholeVehicleActualfeeFlowRangeLow the wholeVehicleActualfeeFlowRangeLow to set
	 */
	public void setWholeVehicleActualfeeFlowRangeLow(
			BigDecimal wholeVehicleActualfeeFlowRangeLow) {
		this.wholeVehicleActualfeeFlowRangeLow = wholeVehicleActualfeeFlowRangeLow;
	}


	
	
	/**
	 * @return the transportRecordList
	 */
	public List<TransportRecordVo> getTransportRecordList() {
		return transportRecordList;
	}

	
	/**
	 * @param transportRecordList the transportRecordList to set
	 */
	public void setTransportRecordList(List<TransportRecordVo> transportRecordList) {
		this.transportRecordList = transportRecordList;
	}

	/**
	 * @return the standChargeId
	 */
	public String getStandChargeId() {
		return standChargeId;
	}


	
	/**
	 * @param standChargeId the standChargeId to set
	 */
	public void setStandChargeId(String standChargeId) {
		this.standChargeId = standChargeId;
	}


	
	/**
	 * @return the standChargeCode
	 */
	public String getStandChargeCode() {
		return standChargeCode;
	}


	
	/**
	 * @param standChargeCode the standChargeCode to set
	 */
	public void setStandChargeCode(String standChargeCode) {
		this.standChargeCode = standChargeCode;
	}

	
	

	
	/**
	 * @return the recordStartOrgCode
	 */
	public String getRecordStartOrgCode() {
		return recordStartOrgCode;
	}

	
	/**
	 * @param recordStartOrgCode the recordStartOrgCode to set
	 */
	public void setRecordStartOrgCode(String recordStartOrgCode) {
		this.recordStartOrgCode = recordStartOrgCode;
	}

	
	/**
	 * @return the recordProductCode
	 */
	public ProductEntityVo getRecordProductCode() {
		return recordProductCode;
	}

	
	/**
	 * @param recordProductCode the recordProductCode to set
	 */
	public void setRecordProductCode(ProductEntityVo recordProductCode) {
		this.recordProductCode = recordProductCode;
	}

	
	/**
	 * @return the recordCustomerPickupOrgCode
	 */
	public BranchVo getRecordCustomerPickupOrgCode() {
		return recordCustomerPickupOrgCode;
	}

	
	/**
	 * @param recordCustomerPickupOrgCode the recordCustomerPickupOrgCode to set
	 */
	public void setRecordCustomerPickupOrgCode(BranchVo recordCustomerPickupOrgCode) {
		this.recordCustomerPickupOrgCode = recordCustomerPickupOrgCode;
	}

	
	/**
	 * @return the recordCustomerPickupOrgName
	 */
	public String getRecordCustomerPickupOrgName() {
		return recordCustomerPickupOrgName;
	}

	
	/**
	 * @param recordCustomerPickupOrgName the recordCustomerPickupOrgName to set
	 */
	public void setRecordCustomerPickupOrgName(String recordCustomerPickupOrgName) {
		this.recordCustomerPickupOrgName = recordCustomerPickupOrgName;
	}

	
	/**
	 * @return the recordTargetOrgCode
	 */
	public String getRecordTargetOrgCode() {
		return recordTargetOrgCode;
	}

	
	/**
	 * @param recordTargetOrgCode the recordTargetOrgCode to set
	 */
	public void setRecordTargetOrgCode(String recordTargetOrgCode) {
		this.recordTargetOrgCode = recordTargetOrgCode;
	}

	
	/**
	 * @return the recordReceiveMethod
	 */
	public DataDictionaryValueVo getRecordReceiveMethod() {
		return recordReceiveMethod;
	}

	
	/**
	 * @param recordReceiveMethod the recordReceiveMethod to set
	 */
	public void setRecordReceiveMethod(DataDictionaryValueVo recordReceiveMethod) {
		this.recordReceiveMethod = recordReceiveMethod;
	}

	
	/**
	 * @return the recordFlightNumberType
	 */
	public DataDictionaryValueVo getRecordFlightNumberType() {
		return recordFlightNumberType;
	}

	
	/**
	 * @param recordFlightNumberType the recordFlightNumberType to set
	 */
	public void setRecordFlightNumberType(
			DataDictionaryValueVo recordFlightNumberType) {
		this.recordFlightNumberType = recordFlightNumberType;
	}

	
	/**
	 * @return the recordBillingType
	 */
	public DataDictionaryValueVo getRecordBillingType() {
		return recordBillingType;
	}

	
	/**
	 * @param recordBillingType the recordBillingType to set
	 */
	public void setRecordBillingType(DataDictionaryValueVo recordBillingType) {
		this.recordBillingType = recordBillingType;
	}

	
	/**
	 * @return the recordUnitPrice
	 */
	public BigDecimal getRecordUnitPrice() {
		return recordUnitPrice;
	}

	
	/**
	 * @param recordUnitPrice the recordUnitPrice to set
	 */
	public void setRecordUnitPrice(BigDecimal recordUnitPrice) {
		this.recordUnitPrice = recordUnitPrice;
	}

	
	/**
	 * @return the recordFee
	 */
	public BigDecimal getRecordFee() {
		return recordFee;
	}

	
	/**
	 * @param recordFee the recordFee to set
	 */
	public void setRecordFee(BigDecimal recordFee) {
		this.recordFee = recordFee;
	}

	
	/**
	 * @return the recordFreightMethod
	 */
	public DataDictionaryValueVo getRecordFreightMethod() {
		return recordFreightMethod;
	}

	
	/**
	 * @param recordFreightMethod the recordFreightMethod to set
	 */
	public void setRecordFreightMethod(DataDictionaryValueVo recordFreightMethod) {
		this.recordFreightMethod = recordFreightMethod;
	}

	
	/**
	 * @return the recordFlightShift
	 */
	public String getRecordFlightShift() {
		return recordFlightShift;
	}

	
	/**
	 * @param recordFlightShift the recordFlightShift to set
	 */
	public void setRecordFlightShift(String recordFlightShift) {
		this.recordFlightShift = recordFlightShift;
	}

	
	
	public String getOldReceiveOrgCode() {
		return oldReceiveOrgCode;
	}

	public void setOldReceiveOrgCode(String oldReceiveOrgCode) {
		this.oldReceiveOrgCode = oldReceiveOrgCode;
	}

	public String getOldReceiveOrgName() {
		return oldReceiveOrgName;
	}

	public void setOldReceiveOrgName(String oldReceiveOrgName) {
		this.oldReceiveOrgName = oldReceiveOrgName;
	}


	public String getOldreceiveCustomerAddress() {
		return oldreceiveCustomerAddress;
	}

	public void setOldreceiveCustomerAddress(String oldreceiveCustomerAddress) {
		this.oldreceiveCustomerAddress = oldreceiveCustomerAddress;
	}


	
	/**
	 * 获取最终提货方式
	 */
	public DataDictionaryValueVo getFinalReceiveMethod() {
		// 更改类型
		String rfcType = getRfcType().getValueCode();
		// 到达部门CODE
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			// 转运分两段计费
			return tfrReceiveMethod;
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			// 返货分段计费
			return rtnReceiveMethod;
		} else if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
			return receiveMethod;
		} else {
			if(transferRecordList.size()>0 ||returnRecordList.size()>0){
				return recordReceiveMethod;
			}
			// 与开单计费方式一样
			return receiveMethod;
		}
	}
	
	/**
	 * 
	 * 获取最终目的站
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-14 下午4:01:13
	 */
	public BranchVo getFinalCustomerPickupOrgCode() {
		// 更改类型
		String rfcType = getRfcType().getValueCode();
		// 到达部门CODE
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			// 转运分两段计费
			return tfrCustomerPickupOrgCode;
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			// 返货分段计费
			return rtnCustomerPickupOrgCode;
		} else if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
			return customerPickupOrgCode;
		} else {
			if(transferRecordList.size()>0 ||returnRecordList.size()>0){
				return recordCustomerPickupOrgCode;
			}
			// 与开单计费方式一样
			return customerPickupOrgCode;
		}
	}

	/**
	 * 
	 * 获取最终运输性质
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-14 下午4:01:13
	 */
	public ProductEntityVo getFinalProductCode() {
		// 更改类型
		String rfcType = getRfcType().getValueCode();
		// 到达部门CODE
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			// 转运分两段计费
			return tfrProductCode;
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			// 返货分段计费
			return rtnProductCode;
		} else if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
			return productCode;
		} else {
			if(transferRecordList.size()>0 ||returnRecordList.size()>0){
				return recordProductCode;
			}
			// 与开单计费方式一样
			return productCode;
		}
	}

	/**
	 * 
	 * 获取最终航班类型
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-14 下午4:01:13
	 */
	public DataDictionaryValueVo getFinalFlightNumberType() {
		// 更改类型
		String rfcType = getRfcType().getValueCode();
		// 到达部门CODE
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			// 转运分两段计费
			return tfrFlightNumberType;
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			// 返货分段计费
			return null;
		} else if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
			return flightNumberType;
		} else {
			if(transferRecordList.size()>0 ||returnRecordList.size()>0){
				return recordFlightNumberType;
			}
			// 与开单计费方式一样
			return flightNumberType;
		}
	}


	/**
	 * 
	 * 获取最终出发部门
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-14 下午4:01:13
	 */
	public String getFinalStartOrgCode() {
		// 更改类型
		String rfcType = getRfcType().getValueCode();
		// 到达部门CODE
		if (WaybillRfcConstants.TRANSFER.equals(rfcType)) {
			// 转运分两段计费
			return tfrStartOrgCode;
		} else if (WaybillRfcConstants.RETURN.equals(rfcType)) {
			// 返货分段计费
			return rtnStartOrgCode;
		} else if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType)) {
			if(oldReceiveOrgCode!=customerPickupOrgCode.getCode()&&oldReceiveOrgCode!=null){
				return oldReceiveOrgCode;
			}else{
			return receiveOrgCode;
			}
		} else {
			if(transferRecordList.size()>0 ||returnRecordList.size()>0){
				return recordStartOrgCode;
			}
			// 与开单计费方式一样
			return receiveOrgCode;
		}
	}

	public String getInnerPickupFeeBurdenDept() {
		return innerPickupFeeBurdenDept;
	}

	public void setInnerPickupFeeBurdenDept(String innerPickupFeeBurdenDept) {
		this.innerPickupFeeBurdenDept = innerPickupFeeBurdenDept;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	
	
	public int getIsChangeReason() {
		return isChangeReason;
	}

	public void setIsChangeReason(int isChangeReason) {
		this.isChangeReason = isChangeReason;
	}


	/**
	 * 交易流水号
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-22下午18:55
	 */
	private String transactionSerialNumber;

	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}
	
	//标识目的站是否更改 0：未更改；1：更改
	private int changeType = 0 ;
	//保存原运费
	private BigDecimal transportFeeOrg ;
	//画布中运费
	private String transportFeeCanvasOrg ;

	public String getTransportFeeCanvasOrg() {
		return transportFeeCanvasOrg;
	}

	public void setTransportFeeCanvasOrg(String transportFeeCanvasOrg) {
		this.transportFeeCanvasOrg = transportFeeCanvasOrg;
	}

	public BigDecimal getTransportFeeOrg() {
		return transportFeeOrg;
	}

	public void setTransportFeeOrg(BigDecimal transportFeeOrg) {
		this.transportFeeOrg = transportFeeOrg;
	}

	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}

	public DataDictionaryValueVo getOldreturnBillType() {
		return oldreturnBillType;
	}

	public void setOldreturnBillType(DataDictionaryValueVo oldreturnBillType) {
		this.oldreturnBillType = oldreturnBillType;
	}
	
	/**
	 * 转寄退回 用于存放临时改变的值
	 */
	private  WaybillInfoTempVo waybillInfoTempVo ;

	public WaybillInfoTempVo getWaybillInfoTempVo() {
		return waybillInfoTempVo;
	}

	public void setWaybillInfoTempVo(WaybillInfoTempVo waybillInfoTempVo) {
		this.waybillInfoTempVo = waybillInfoTempVo;
	}
	
	//折前公布运价费
	private BigDecimal zqTransportFee;


	public BigDecimal getZqTransportFee() {
		return zqTransportFee;
	}

	public void setZqTransportFee(BigDecimal zqTransportFee) {
		this.zqTransportFee = zqTransportFee;
	}
	
	private boolean isAccurateCost;


	public boolean isAccurateCost() {
		return isAccurateCost;
	}

	public void setAccurateCost(boolean isAccurateCost) {
		this.isAccurateCost = isAccurateCost;
	}
	
}