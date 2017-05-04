/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.vo.exp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.WoodYokeVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpWaybillPanelVo extends WaybillPanelVo {

	/**
	 * 原始单号
	 */
	private String originalWaybillNo;

	/**
	 * 是否补码
	 */
	private String isAddCode;

	/**
	 * 补码时间
	 */
	private Date addCodeTime;

	/**
	 * 返单/返货类型
	 */
	private DataDictionaryValueVo returnType;

	/**
	 * 快递员code
	 */
	private String expressEmpCode;
	/**
	 * 快递员名称
	 */
	private String expressEmpName;

	/**
	 * 快递点部CODE
	 */
	private String expressOrgCode;

	/**
	 * 快递点部名称
	 */
	private String expressOrgName;
	
	/**
	 * PDA串号
	 */
	private String pdaSerial;
	
	/**
	 * 银行交易流水号
	 */
	private String bankTradeSerail;
	

	// ---------------------控件属性
	// 纸
	private Integer paper;

	// 木
	private Integer wood;

	// 纤
	private Integer fibre;

	// 托
	private Integer salver;

	// 膜
	private Integer membrane;

	// 其他
	private String otherPackage;

	// 后期加入
	// 手写金额
	private BigDecimal handWriteMoney;

	// 代打木架
	private WoodYokeVo yokeVo;

	// 储运事项
	private String transportationRemark;

	// 长途短途
	private String longOrShort;

	// 画布中需要新增的属性
	// 公布价运费
	private String transportFeeCanvas;

	// 保价声明
	private String insuranceAmountCanvas;

	// 代收货款
	private String codAmountCanvas;

	// 接货费
	private String pickUpFeeCanvas;

	// 送货费
	private String deliveryGoodsFeeCanvas;

	// 包装费
	private String packageFeeCanvas;

	// 装卸费
	private String serviceFeeCanvas;

	// 其他费用
	private String otherFeeCanvas;

	// 优惠费用
	private String promotionsFeeCanvas;

	// 总费用
	private String totalFeeCanvas;

	// 其他费用集合
	private List<OtherChargeVo> allChargeList;

	// ----------实体属性
	// 运单实体ID
	private String id;

	// 运单号
	private String waybillNo;

	// 导入的运单号
	private String waybillNoImported;

	// 订单号
	private String orderNo;

	// 订单来源
	private String orderChannel;

	// 订单付款方式
	private String orderPayment;

	// 结算付款(是否月结)
	private Boolean chargeMode;

	/**
	 * 优惠类型.
	 */
	private String preferentialType;

	// 合同编号
	private String auditNo;

	// 发货客户ID
	private String deliveryCustomerId;

	// 发货客户编码
	private String deliveryCustomerCode;

	// 发货客户名称
	private String deliveryCustomerName;

	// 发货客户手机
	private String deliveryCustomerMobilephone;

	// 发货客户电话
	private String deliveryCustomerPhone;

	// 发货客户联系人
	private String deliveryCustomerContact;

	// 发货客户联系人ID
	private String deliveryCustomerContactId;

	// 发货国家
	private String deliveryCustomerNationCode;

	// 发货省份
	private String deliveryCustomerProvCode;

	// 发货市
	private String deliveryCustomerCityCode;

	// 发货区
	private String deliveryCustomerDistCode;

	// 发货具体地址
	private String deliveryCustomerAddress;

	/**
	 * 发货人区域
	 */
	private String deliveryCustomerArea;

	/**
	 * 发货人省市区(数据绑定用)
	 */
	private AddressFieldDto deliveryCustomerAreaDto;

	// 收货客户ID
	private String receiveCustomerId;

	// 收货客户编码
	private String receiveCustomerCode;

	// 收货客户名称
	private String receiveCustomerName;

	// 收货客户手机
	private String receiveCustomerMobilephone;

	// 收货客户电话
	private String receiveCustomerPhone;

	// 收货客户联系人
	private String receiveCustomerContact;

	// 收货客户联系人ID
	private String receiveCustomerContactId;

	// 收货国家
	private String receiveCustomerNationCode;

	// 收货省份
	private String receiveCustomerProvCode;

	// 收货市
	private String receiveCustomerCityCode;

	// 收货区
	private String receiveCustomerDistCode;
	
	//收货乡镇(街道)
	private String receiveCustomerVillageCode;
	
	public String getReceiveCustomerVillageCode() {
		return receiveCustomerVillageCode;
	}

	public void setReceiveCustomerVillageCode(String receiveCustomerVillageCode) {
		this.receiveCustomerVillageCode = receiveCustomerVillageCode;
	}

	// 收货具体地址
	private String receiveCustomerAddress;

	/**
	 * 接送货地址ID
	 */
	private String contactAddressId;

	/**
	 * 收货客户区域
	 */
	private String receiveCustomerArea;
	/**
	 * 收货人省市区(数据绑定用)
	 */
	private AddressFieldDto receiveCustomerAreaDto;

	// 收货部门开业时间
	private Date receiveOrgCreateTime;

	// 收货部门
	private String receiveOrgCode;

	// 收货部门
	private String receiveOrgName;
	// 收货部门省份编码
	private String receiveOrgProvCode;
	// 运输性质
	private ProductEntityVo productCode;

	// 提货方式
	private DataDictionaryValueVo receiveMethod;

	/**
	 * 提货方式标记-标记是否内部带货
	 */
	private String receiveMethodFlag;
	
	// 提货网点
	private BranchVo customerPickupOrgCode;

	// 提货网点名称
	private String customerPickupOrgName;

	// 配载类型
	private String loadMethod;

	// 目的站
	private String targetOrgCode;

	// 是否上门接货
	private Boolean pickupToDoor;
	
	//是否上门发货
	private boolean homeDelivery;

	// 司机
	private String driverCode;

	// 是否集中接货
	private Boolean pickupCentralized;

	// 集中接货开单组所在外场的驻地营业部编码
	private String pickupCentralizedDeptCode;

	// 配载线路编码
	private String loadLineCode;

	// 配载线路名称
	private String loadLineName;

	// 配载部门编号
	private String loadOrgCode;

	// 配载部门名称
	private String loadOrgName;

	// 最终配载部门编号
	private String lastLoadOrgCode;

	// 最终配载部门名称
	private String lastLoadOrgName;

	// 预计出发时间
	private Date preDepartureTime;

	// 预计派送/提货时间
	private Date preCustomerPickupTime;

	// 是否大车直送
	private Boolean carDirectDelivery;

	// 货物名称
	private String goodsName;

	// 货物总件数
	private Integer goodsQtyTotal;

	// 货物总重量
	private BigDecimal goodsWeightTotal;

	// 货物总体积
	private BigDecimal goodsVolumeTotal;

	// 货物尺寸
	private String goodsSize;

	// 货物类型
	private String goodsType;

	// 线路是否能开AB货
	private Boolean goodsTypeIsAB;

	/**
	 * 空运货物类型
	 */
	private DataDictionaryValueVo airGoodsType;

	// 是否贵重物品
	private Boolean preciousGoods;

	// 是否异形物品
	private Boolean specialShapedGoods;

	// 对外备注
	private DataDictionaryValueVo outerNotes;

	// 对内备注
	private String innerNotes;

	// 对内备注数据备份
	private String innerNotesBack;

	// 货物包装
	private String goodsPackage;

	// 保险费ID
	private String insuranceId;

	// 保险费code
	private String insuranceCode;

	// 保价声明价值
	private BigDecimal insuranceAmount;

	// 保价费率
	private BigDecimal insuranceRate;

	// 保价费
	private BigDecimal insuranceFee;

	// 限保物品虚拟CODE
	private String virtualCode;

	// 保额最大值
	private BigDecimal maxInsuranceAmount;

	// 保价费最低一票
	private BigDecimal mixInsuranceAmount;

	// 代收货款ID
	private String codId;

	// 代收货款Code
	private String codCode;

	// 代收货款
	private BigDecimal codAmount;

	// 代收费率
	private BigDecimal codRate;

	// 代收货款手续费
	private BigDecimal codFee;

	// 是否能开代收货款
	private String canAgentCollected;

	// 是否可以货到付款
	private String arriveCharge;

	// 退款类型
	private DataDictionaryValueVo refundType;

	// 返单类别
	private DataDictionaryValueVo returnBillType;

	// 返单费用名称
	private String returnBillChargeName;

	// 预付费保密
	private Boolean secretPrepaid;

	// 到付金额
	private BigDecimal toPayAmount;

	// 预付金额
	private BigDecimal prePayAmount;

	// 送货费
	private BigDecimal deliveryGoodsFee;

	// 送货费集合（ 送货费、送货进仓费、超远派送费、送货上楼费）
	private List<DeliverChargeEntity> deliverList;

	// 公里数
	private BigDecimal kilometer;

	// 送货进仓费名称
	private String deliveryStorgeName;

	// 其他费用
	private BigDecimal otherFee;

	// 包装手续费
	private BigDecimal packageFee;

	// 计算运费获得的包装费
	private BigDecimal calculatedPackageFee;

	// 通过计算得到的送货费
	private BigDecimal calculateDeliveryGoodsFee;

	// 送货费上限
	private BigDecimal maxDeliveryGoodsFee;

	// 优惠费用
	private BigDecimal promotionsFee;

	// 运费计费类型
	private DataDictionaryValueVo billingType;

	// 运费计费费率
	private BigDecimal unitPrice;

	// 公布价运费ID
	private String transportFeeId;

	// 公布价运费Code
	private String transportFeeCode;

	// 公布价运费
	private BigDecimal transportFee;

	// 最低一票费用
	private BigDecimal minTransportFee;

	// 折扣
	private BigDecimal discount;
	// 快递续重折扣
	private BigDecimal expressContinueDiscount;

	// 增值费用
	private BigDecimal valueAddFee;

	// 开单付款方式
	private DataDictionaryValueVo paidMethod;

	// 到达类型
	private String arriveType;

	// 运单状态
	private String active;

	// 禁行
	private String forbiddenLine;

	// 合票方式
	private DataDictionaryValueVo freightMethod;

	// 航班类型
	private DataDictionaryValueVo flightNumberType;
	
	// 航班时间
	private String flightShift;

	// 总费用
	private BigDecimal totalFee;

	// 优惠编码
	private String promotionsCode;

	// 生效日期
	private Date beginTime;

	// 结束日期
	private Date endTime;

	// 开单时间
	private Date createTime;

	// 更新时间
	private Date modifyTime;

	// 开单人
	private String createUserCode;

	// 更新人
	private String modifyUserCode;

	// 开单组织
	private String createOrgCode;

	// 更新组织
	private String modifyOrgCode;

	// 原运单ID
	private String refId;

	// 原运单号
	private String refCode;

	// 币种
	private String currencyCode;

	// 是否整车运单
	private Boolean isWholeVehicle;

	// 是否经过营业部
	private Boolean isPassDept;

	// 整车编号
	private String vehicleNumber;

	// 整车约车报价
	private BigDecimal wholeVehicleAppfee;

	// 整车约车报价波动范围上限
	private BigDecimal wholeVehicleActualfeeFlowRangeUp;

	// 整车约车报价波动范围下限
	private BigDecimal wholeVehicleActualfeeFlowRangeLow;
	// 整车开单报价
	private BigDecimal wholeVehicleActualfee;

	// 代打木架部门编码
	private String packageOrgCode;

	// 代打木架部门编码名称
	private String packingOrganizationName;

	// 是否可以打木架
	private String doPacking;

	// 打木架货物件数
	private Integer standGoodsNum;

	// 代打木架要求
	private String standRequirement;

	// 打木架货物尺寸
	private String standGoodsSize;

	// 打木架货物体积
	private BigDecimal standGoodsVolume;

	// 打木架费用价格ID
	private String standChargeId;

	// 打木架费用价格CODE
	private String standChargeCode;

	// 打木架货物费用
	private BigDecimal standCharge;

	// 打木箱货物件数
	private Integer boxGoodsNum;

	// 代打木箱要求
	private String boxRequirement;

	// 打木箱货物尺寸
	private String boxGoodsSize;

	// 打木箱货物体积
	private BigDecimal boxGoodsVolume;

	// 打木箱费用价格ID
	private String boxChargeId;

	// 打木箱费用价格CODE
	private String boxChargeCode;

	// 打木箱货物费用
	private BigDecimal boxCharge;

	// 返款帐户开户ID
	private String accountID;

	// 返款帐户开户名称
	private String accountName;

	// 返款帐户开户账户
	private String accountCode;

	// 返款账户开户银行编码
	private String accountBankCode;

	// 返款帐户开户银行
	private String accountBank;

	// 计费重量
	private BigDecimal billWeight;

	// 接货费
	private BigDecimal pickupFee;

	// 装卸费
	private BigDecimal serviceFee;

	// 装卸费费率
	private BigDecimal serviceFeeRate;

	// 预计到达时间
	private Date preArriveTime;

	// 运输类型
	private String transportType;

	// 打印次数
	private Integer printTimes;

	// 新增时间
	private Date addTime;

	// 是否为PDA运单
	private Boolean isPdaBill;

	// PDA开单时间
	private Date billTime;

	// 运单处理状态
	private String waybillstatus;

	// 弃货处理的运单号
	private List<String> derelictNum;

	// 最终外场编码
	private String lastOutLoadOrgCode;

	// 优惠卷
	private CouponInfoDto couponInfoDto;
	
	/**
	 * 子母件单号集合
	 */
	List<String> waybillNos;
	
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * 是否为子母件
	 */
	private boolean isPicture;
	
	public boolean isPicture() {
		return isPicture;
	}

	public void setPicture(boolean isPicture) {
		this.isPicture = isPicture;
	}

	/**
	 * 手动修改后的总送货费
	 */
	private BigDecimal handDeliveryFee;

	/**
	 * 标示是否已经减去优惠券
	 */
	private String flagTakeCoupon;
	
	
	/**
	 * 出发营业部试点城市和营业部对照关系
	 */
	private SalesDepartmentCityDto createSalesDepartmentCityDto;
	/**
	 * 到达营业部试点城市和营业部对照关系
	 */
	private SalesDepartmentCityDto targetSalesDepartmentCityDto;
	
	/**
	 * 快递体积上限
	 */
	private BigDecimal volumeUp;
	
	/**
	 * 快递体积下限
	 */
	private BigDecimal volumeLow;
	
	/**
	 * 体积比率
	 */
	private BigDecimal volumeRate;
	
	/**
	 * 快递保价申明价值上限
	 */
	private BigDecimal insuranceAmountUp;
	
	/**
	 * 快递保价上限
	 */
	private BigDecimal insuranceFeeUp;
	
	/**
	 * 是否可以做返货开单
	 */
	private String canReturnCargo;
	
	/**
	 * 计算得到的体积
	 */
	private BigDecimal calculateVolumeTotal;
	
	//发货人工号
	private String deliveryEmployeeNo;
	
	/**
	 * 货物总体积(精确的总体积)
	 */
	private BigDecimal goodsVolumePreviousTotal;
	
	/**
	 * 体积重
	 */
	private BigDecimal goodsWeightByVolumeTotal;
	
	/**
	 * 短信标识
	 */
	private String isSMS;
	
	/**
	 * 未冲减优惠券之前的运费
	 */
	private BigDecimal beforeProTranFee;	
	
	//是否次日补录
    private String isNextDayPending;
    
    /**
     * 关联单号费用
     */
    private BigDecimal connectionNumFee;
    /**
     * 伙伴名称
     */
	private String partnerName;
	/**
	 * 伙伴手机
	 */
	private String partnerPhone;
	/**
	 * 伙伴开单
	 */
	private Boolean partnerBilling;
	/**
	 * 零时记录公布价费用
	 */
    private BigDecimal tempTransportFee;
    
    

	public BigDecimal getTempTransportFee() {
		return tempTransportFee;
	}

	public void setTempTransportFee(BigDecimal tempTransportFee) {
		this.tempTransportFee = tempTransportFee;
	}

	public Boolean getPartnerBilling() {
		return partnerBilling;
	}

	public void setPartnerBilling(Boolean partnerBilling) {
		this.partnerBilling = partnerBilling;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}


	public BigDecimal getConnectionNumFee() {
		return connectionNumFee;
	}

	public void setConnectionNumFee(BigDecimal connectionNumFee) {
		this.connectionNumFee = connectionNumFee;
	}

	public String getIsNextDayPending() {
		return isNextDayPending;
		}

	public void setIsNextDayPending(String isNextDayPending) {
		this.isNextDayPending = isNextDayPending;
		}

	public BigDecimal getBeforeProTranFee() {
		return beforeProTranFee;
	}

	public void setBeforeProTranFee(BigDecimal beforeProTranFee) {
		this.beforeProTranFee = beforeProTranFee;
	}

	public String getIsSMS() {
		return isSMS;
	}

	public void setIsSMS(String isSMS) {
		this.isSMS = isSMS;
	}

	public BigDecimal getGoodsWeightByVolumeTotal() {
		return goodsWeightByVolumeTotal;
	}

	public void setGoodsWeightByVolumeTotal(BigDecimal goodsWeightByVolumeTotal) {
		this.goodsWeightByVolumeTotal = goodsWeightByVolumeTotal;
	}

	public BigDecimal getGoodsVolumePreviousTotal() {
		return goodsVolumePreviousTotal;
	}

	public void setGoodsVolumePreviousTotal(BigDecimal goodsVolumePreviousTotal) {
		this.goodsVolumePreviousTotal = goodsVolumePreviousTotal;
	}

	public String getDeliveryEmployeeNo() {
		return deliveryEmployeeNo;
	}

	public void setDeliveryEmployeeNo(String deliveryEmployeeNo) {
		this.deliveryEmployeeNo = deliveryEmployeeNo;
	}

	public BigDecimal getCalculateVolumeTotal() {
		return calculateVolumeTotal;
	}

	public void setCalculateVolumeTotal(BigDecimal calculateVolumeTotal) {
		this.calculateVolumeTotal = calculateVolumeTotal;
	}

	public String getCanReturnCargo() {
		return canReturnCargo;
	}

	public void setCanReturnCargo(String canReturnCargo) {
		this.canReturnCargo = canReturnCargo;
	}
	
	public BigDecimal getInsuranceFeeUp() {
		return insuranceFeeUp;
	}

	public void setInsuranceFeeUp(BigDecimal insuranceFeeUp) {
		this.insuranceFeeUp = insuranceFeeUp;
	}

	public BigDecimal getInsuranceAmountUp() {
		return insuranceAmountUp;
	}

	public void setInsuranceAmountUp(BigDecimal insuranceAmountUp) {
		this.insuranceAmountUp = insuranceAmountUp;
	}

	public BigDecimal getVolumeUp() {
		return volumeUp;
	}

	public void setVolumeUp(BigDecimal volumeUp) {
		this.volumeUp = volumeUp;
	}

	public BigDecimal getVolumeLow() {
		return volumeLow;
	}

	public void setVolumeLow(BigDecimal volumeLow) {
		this.volumeLow = volumeLow;
	}

	public BigDecimal getVolumeRate() {
		return volumeRate;
	}

	public void setVolumeRate(BigDecimal volumeRate) {
		this.volumeRate = volumeRate;
	}

	public SalesDepartmentCityDto getCreateSalesDepartmentCityDto() {
		return createSalesDepartmentCityDto;
	}

	public void setCreateSalesDepartmentCityDto(
			SalesDepartmentCityDto createSalesDepartmentCityDto) {
		this.createSalesDepartmentCityDto = createSalesDepartmentCityDto;
	}

	public SalesDepartmentCityDto getTargetSalesDepartmentCityDto() {
		return targetSalesDepartmentCityDto;
	}

	public void setTargetSalesDepartmentCityDto(
			SalesDepartmentCityDto targetSalesDepartmentCityDto) {
		this.targetSalesDepartmentCityDto = targetSalesDepartmentCityDto;
	}

	public String getFlagTakeCoupon() {
		return flagTakeCoupon;
	}

	public void setFlagTakeCoupon(String flagTakeCoupon) {
		this.flagTakeCoupon = flagTakeCoupon;
	}

	/**
	 * 开户银行信息
	 */
	private CusAccountEntity openBank;

	/**
	 * PDA 总金额
	 */
	private BigDecimal totalCountPDA;

	/**
	 * CRM订单导入：官网用户名
	 */
	private String channelCustId;

	/**
	 * 原运单号
	 */
	private String oldWaybillNo;

	/**
	 * 优惠卷费用
	 */
	private BigDecimal couponFree;

	/**
	 * 是否是开单暂存补录
	 */
	private Boolean isPCPending;

	/**
	 * 限保物品：限制报价金额
	 */
	private BigDecimal limitedAmount;

	/**
	 * 基础送货费
	 */
	private BigDecimal basePickupFee;

	/**
	 * @return the waybillNoImported
	 */
	public String getWaybillNoImported() {
		return waybillNoImported;
	}

	/**
	 * @param waybillNoImported
	 *            the waybillNoImported to set
	 */
	public void setWaybillNoImported(String waybillNoImported) {
		this.waybillNoImported = waybillNoImported;
	}

	public BigDecimal getBasePickupFee() {
		return basePickupFee;
	}

	public void setBasePickupFee(BigDecimal basePickupFee) {
		this.basePickupFee = basePickupFee;
	}

	public BigDecimal getLimitedAmount() {
		return limitedAmount;
	}

	public void setLimitedAmount(BigDecimal limitedAmount) {
		this.limitedAmount = limitedAmount;
	}

	/**
	 * 
	 * 收货部门省份编码
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-27 下午4:03:39
	 */
	public String getReceiveOrgProvCode() {
		return receiveOrgProvCode;
	}

	/**
	 * 
	 * 收货部门省份编码
	 * 
	 * @param receiveOrgProvCode
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-27 下午4:04:15
	 */
	public void setReceiveOrgProvCode(String receiveOrgProvCode) {
		this.receiveOrgProvCode = receiveOrgProvCode;
	}

	/**
	 * @return the standChargeId
	 */
	public String getStandChargeId() {
		return standChargeId;
	}

	/**
	 * @param standChargeId
	 *            the standChargeId to set
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
	 * @param standChargeCode
	 *            the standChargeCode to set
	 */
	public void setStandChargeCode(String standChargeCode) {
		this.standChargeCode = standChargeCode;
	}

	/**
	 * @return the boxChargeId
	 */
	public String getBoxChargeId() {
		return boxChargeId;
	}

	/**
	 * @param boxChargeId
	 *            the boxChargeId to set
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
	 * @param boxChargeCode
	 *            the boxChargeCode to set
	 */
	public void setBoxChargeCode(String boxChargeCode) {
		this.boxChargeCode = boxChargeCode;
	}

	/**
	 * @return the transportFeeId
	 */
	public String getTransportFeeId() {
		return transportFeeId;
	}

	/**
	 * @param transportFeeId
	 *            the transportFeeId to set
	 */
	public void setTransportFeeId(String transportFeeId) {
		this.transportFeeId = transportFeeId;
	}

	/**
	 * @return the transportFeeCode
	 */
	public String getTransportFeeCode() {
		return transportFeeCode;
	}

	/**
	 * @param transportFeeCode
	 *            the transportFeeCode to set
	 */
	public void setTransportFeeCode(String transportFeeCode) {
		this.transportFeeCode = transportFeeCode;
	}

	/**
	 * 
	 * 获取优惠卷费用
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:21:21
	 */
	public BigDecimal getCouponFree() {
		return couponFree;
	}

	/**
	 * 
	 * 设置优惠券费用
	 * 
	 * @param couponFree
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:21:39
	 */
	public void setCouponFree(BigDecimal couponFree) {
		this.couponFree = couponFree;
	}

	/**
	 * @param channelCustId
	 *            the channelCustId to set
	 */
	public void setChannelCustId(String channelCustId) {
		this.channelCustId = channelCustId;
	}

	/**
	 * @return the channelCustId
	 */
	public String getChannelCustId() {
		return channelCustId;
	}

	/**
	 * 保存导入PDA订单时，值改变就设置颜色的控件与值
	 */
	private Map<JTextField, String> changeColorTxt;

	private BigDecimal codAmountMax;

	/**
	 * @return the totalCountPDA
	 */
	public BigDecimal getTotalCountPDA() {
		return totalCountPDA;
	}

	/**
	 * @param totalCountPDA
	 *            the totalCountPDA to set
	 */
	public void setTotalCountPDA(BigDecimal totalCountPDA) {
		this.totalCountPDA = totalCountPDA;
	}

	/**
	 * @return 优惠卷
	 */
	public CouponInfoDto getCouponInfoDto() {
		return couponInfoDto;
	}

	/**
	 * @param 优惠卷
	 */
	public void setCouponInfoDto(CouponInfoDto couponInfoDto) {
		this.couponInfoDto = couponInfoDto;
	}

	/**
	 * @return 最终外场编码
	 */
	public String getLastOutLoadOrgCode() {
		return lastOutLoadOrgCode;
	}

	/**
	 * @param 最终外场编码
	 *            the lastOutLoadOrgCode to set
	 */
	public void setLastOutLoadOrgCode(String lastOutLoadOrgCode) {
		this.lastOutLoadOrgCode = lastOutLoadOrgCode;
	}

	/**
	 * 包装-纸
	 */
	public Integer getPaper() {
		return paper;
	}

	/**
	 * 包装-纸
	 */
	public void setPaper(Integer paper) {
		this.paper = paper;
	}

	/**
	 * 包装-木
	 */
	public Integer getWood() {
		return wood;
	}

	/**
	 * 包装-木
	 */
	public void setWood(Integer wood) {
		this.wood = wood;
	}

	/**
	 * 包装-纤
	 */
	public Integer getFibre() {
		return fibre;
	}

	/**
	 * 包装-纤
	 */
	public void setFibre(Integer fibre) {
		this.fibre = fibre;
	}

	/**
	 * 包装-托
	 */
	public Integer getSalver() {
		return salver;
	}

	/**
	 * 包装-托
	 */
	public void setSalver(Integer salver) {
		this.salver = salver;
	}

	/**
	 * 包装-膜
	 */
	public Integer getMembrane() {
		return membrane;
	}

	/**
	 * 包装-膜
	 */
	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}

	/**
	 * 包装-其他
	 */
	public String getOtherPackage() {
		return otherPackage;
	}

	/**
	 * 包装-其他
	 */
	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}

	/**
	 * 手写金额
	 */
	public BigDecimal getHandWriteMoney() {
		return handWriteMoney;
	}

	/**
	 * 手写金额
	 */
	public void setHandWriteMoney(BigDecimal handWriteMoney) {
		// 四舍五入取整数
		this.handWriteMoney = CalculateFeeTotalUtils
				.formatNumberInteger(handWriteMoney);
	}

	/**
	 * 代打木架
	 */
	public WoodYokeVo getYokeVo() {
		return yokeVo;
	}

	/**
	 * 代打木架
	 */
	public void setYokeVo(WoodYokeVo yokeVo) {
		this.yokeVo = yokeVo;
	}

	/**
	 * 储运事项
	 */
	public String getTransportationRemark() {
		return transportationRemark;
	}

	/**
	 * 储运事项
	 */
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}

	/**
	 * 长途短途
	 */
	public String getLongOrShort() {
		return longOrShort;
	}

	/**
	 * 长途短途
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	/**
	 * 公布价运费
	 */
	public String getTransportFeeCanvas() {
		return transportFeeCanvas;
	}

	/**
	 * 公布价运费
	 */
	public void setTransportFeeCanvas(String transportFeeCanvas) {
		this.transportFeeCanvas = transportFeeCanvas;
	}

	/**
	 * 保价声明
	 */
	public String getInsuranceAmountCanvas() {
		return insuranceAmountCanvas;
	}

	/**
	 * 保价声明
	 */
	public void setInsuranceAmountCanvas(String insuranceAmountCanvas) {
		// 四舍五入取整数
		this.insuranceAmountCanvas = CalculateFeeTotalUtils
				.formatNumberInteger(insuranceAmountCanvas);
	}

	/**
	 * 代收货款
	 */
	public String getCodAmountCanvas() {
		return codAmountCanvas;
	}

	/**
	 * 代收货款
	 */
	public void setCodAmountCanvas(String codAmountCanvas) {
		this.codAmountCanvas = codAmountCanvas;
	}

	/**
	 * 接货费
	 */
	public String getPickUpFeeCanvas() {
		return pickUpFeeCanvas;
	}

	/**
	 * 接货费
	 */
	public void setPickUpFeeCanvas(String pickUpFeeCanvas) {
		this.pickUpFeeCanvas = pickUpFeeCanvas;
	}

	/**
	 * 送货费
	 */
	public String getDeliveryGoodsFeeCanvas() {
		return deliveryGoodsFeeCanvas;
	}

	/**
	 * 送货费
	 */
	public void setDeliveryGoodsFeeCanvas(String deliveryGoodsFeeCanvas) {
		this.deliveryGoodsFeeCanvas = deliveryGoodsFeeCanvas;
	}

	/**
	 * 包装费
	 */
	public String getPackageFeeCanvas() {
		return packageFeeCanvas;
	}

	/**
	 * 包装费
	 */
	public void setPackageFeeCanvas(String packageFeeCanvas) {
		this.packageFeeCanvas = packageFeeCanvas;
	}

	/**
	 * 装卸费
	 */
	public String getServiceFeeCanvas() {
		return serviceFeeCanvas;
	}

	/**
	 * 装卸费
	 */
	public void setServiceFeeCanvas(String serviceFeeCanvas) {
		this.serviceFeeCanvas = serviceFeeCanvas;
	}

	/**
	 * 其他费用
	 */
	public String getOtherFeeCanvas() {
		return otherFeeCanvas;
	}

	/**
	 * 其他费用
	 */
	public void setOtherFeeCanvas(String otherFeeCanvas) {
		this.otherFeeCanvas = otherFeeCanvas;
	}

	/**
	 * 优惠费用
	 */
	public String getPromotionsFeeCanvas() {
		return promotionsFeeCanvas;
	}

	/**
	 * 优惠费用
	 */
	public void setPromotionsFeeCanvas(String promotionsFeeCanvas) {
		// 四舍五入取整数
		this.promotionsFeeCanvas = CalculateFeeTotalUtils
				.formatNumberInteger(promotionsFeeCanvas);
	}

	/**
	 * 总费用
	 */
	public String getTotalFeeCanvas() {
		return totalFeeCanvas;
	}

	/**
	 * 总费用
	 */
	public void setTotalFeeCanvas(String totalFeeCanvas) {
		this.totalFeeCanvas = totalFeeCanvas;
	}

	/**
	 * 运单ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 运单ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 优惠类型
	 * 
	 * @return
	 */
	public String getPreferentialType() {
		return preferentialType;
	}

	/**
	 * 优惠类型
	 * 
	 * @param preferentialType
	 */
	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}

	/**
	 * 结算付款(是否月结)
	 */
	public Boolean getChargeMode() {
		return chargeMode;
	}

	/**
	 * 结算付款(是否月结)
	 */
	public void setChargeMode(Boolean chargeMode) {
		this.chargeMode = chargeMode;
	}

	/**
	 * 发货客户ID
	 */
	public String getDeliveryCustomerId() {
		return deliveryCustomerId;
	}

	/**
	 * 发货客户ID
	 */
	public void setDeliveryCustomerId(String deliveryCustomerId) {
		this.deliveryCustomerId = deliveryCustomerId;
	}

	/**
	 * 发货客户编码
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * 发货客户编码
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * 发货客户名称
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * 发货客户名称
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * 发货客户手机
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * 发货客户手机
	 */
	public void setDeliveryCustomerMobilephone(
			String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * 发货客户电话
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * 发货客户电话
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * 发货客户联系人
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * 发货客户联系人
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * 发货客户联系人ID
	 */
	public String getDeliveryCustomerContactId() {
		return deliveryCustomerContactId;
	}

	/**
	 * 发货客户联系人ID
	 */
	public void setDeliveryCustomerContactId(String deliveryCustomerContactId) {
		this.deliveryCustomerContactId = deliveryCustomerContactId;
	}

	/**
	 * 发货国家
	 */
	public String getDeliveryCustomerNationCode() {
		return deliveryCustomerNationCode;
	}

	/**
	 * 发货国家
	 */
	public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
		this.deliveryCustomerNationCode = deliveryCustomerNationCode;
	}

	/**
	 * 发货省份
	 */
	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	/**
	 * 发货省份
	 */
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	/**
	 * 发货市
	 */
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	/**
	 * 发货市
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	/**
	 * 发货区
	 */
	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}

	/**
	 * 发货区
	 */
	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}

	/**
	 * 发货具体地址
	 */
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	/**
	 * 发货具体地址
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	/**
	 * 收货客户ID
	 */
	public String getReceiveCustomerId() {
		return receiveCustomerId;
	}

	/**
	 * 收货客户ID
	 */
	public void setReceiveCustomerId(String receiveCustomerId) {
		this.receiveCustomerId = receiveCustomerId;
	}

	/**
	 * 收货客户编码
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * 收货客户编码
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * 收货客户名称
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * 收货客户名称
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * 收货客户手机
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * 收货客户手机
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * 收货客户电话
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * 收货客户电话
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * 收货客户联系人
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * 收货客户联系人
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * 收货客户联系人ID
	 */
	public String getReceiveCustomerContactId() {
		return receiveCustomerContactId;
	}

	/**
	 * 收货客户联系人ID
	 */
	public void setReceiveCustomerContactId(String receiveCustomerContactId) {
		this.receiveCustomerContactId = receiveCustomerContactId;
	}

	/**
	 * 收货客户联系人ID
	 */
	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}

	/**
	 * 收货客户联系人ID
	 */
	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
	}

	/**
	 * 收货省份
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * 收货省份
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * 收货市
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * 收货市
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * 收货区
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * 收货区
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	/**
	 * 收货具体地址
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * 收货具体地址
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * 收货部门
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * 收货部门
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * 收货部门
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * 收货部门
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * 运输性质
	 */
	public ProductEntityVo getProductCode() {
		return productCode;
	}

	/**
	 * 运输性质
	 */
	public void setProductCode(ProductEntityVo productCode) {
		this.productCode = productCode;
	}

	/**
	 * 提货方式
	 */
	public DataDictionaryValueVo getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * 提货方式
	 */
	public void setReceiveMethod(DataDictionaryValueVo receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * 提货网点
	 */
	public BranchVo getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * 提货网点
	 */
	public void setCustomerPickupOrgCode(BranchVo customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * 提货网点名称
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	/**
	 * 提货网点名称
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	/**
	 * 配载类型
	 */
	public String getLoadMethod() {
		return loadMethod;
	}

	/**
	 * 配载类型
	 */
	public void setLoadMethod(String loadMethod) {
		this.loadMethod = loadMethod;
	}

	/**
	 * 目的站
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * 目的站
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * 是否上门接货
	 */
	public Boolean getPickupToDoor() {
		return pickupToDoor;
	}

	/**
	 * 是否上门接货
	 */
	public void setPickupToDoor(Boolean pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	/**
	 * 司机
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 司机
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 是否集中接货
	 */
	public Boolean getPickupCentralized() {
		return pickupCentralized;
	}

	/**
	 * 是否集中接货
	 */
	public void setPickupCentralized(Boolean pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	/**
	 * 配载线路编码
	 */
	public String getLoadLineCode() {
		return loadLineCode;
	}

	/**
	 * 配载线路编码
	 */
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}

	/**
	 * 配载线路名称
	 */
	public String getLoadLineName() {
		return loadLineName;
	}

	/**
	 * 配载线路名称
	 */
	public void setLoadLineName(String loadLineName) {
		this.loadLineName = loadLineName;
	}

	/**
	 * 配载部门编号
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * 配载部门编号
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * 配载部门名称
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}

	/**
	 * 配载部门名称
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}

	/**
	 * 最终配载部门编号
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * 最终配载部门编号
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * 最终配载部门名称
	 */
	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	/**
	 * 最终配载部门名称
	 */
	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	/**
	 * 预计出发时间
	 */
	public Date getPreDepartureTime() {
		return preDepartureTime;
	}

	/**
	 * 预计出发时间
	 */
	public void setPreDepartureTime(Date preDepartureTime) {
		this.preDepartureTime = preDepartureTime;
	}

	/**
	 * 预计派送/提货时间
	 */
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	/**
	 * 预计派送/提货时间
	 */
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	/**
	 * 是否大车直送
	 */
	public Boolean getCarDirectDelivery() {
		return carDirectDelivery;
	}

	/**
	 * 是否大车直送
	 */
	public void setCarDirectDelivery(Boolean carDirectDelivery) {
		this.carDirectDelivery = carDirectDelivery;
	}

	/**
	 * 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 货物总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 货物总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * 货物总重量
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * 货物总重量
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * 货物总体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * 货物总体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * 货物尺寸
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * 货物尺寸
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	/**
	 * 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * 是否贵重物品
	 */
	public Boolean getPreciousGoods() {
		return preciousGoods;
	}

	/**
	 * 是否贵重物品
	 */
	public void setPreciousGoods(Boolean preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	/**
	 * 是否异形物品
	 */
	public Boolean getSpecialShapedGoods() {
		return specialShapedGoods;
	}

	/**
	 * 是否异形物品
	 */
	public void setSpecialShapedGoods(Boolean specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}

	/**
	 * 对外备注
	 */
	public DataDictionaryValueVo getOuterNotes() {
		return outerNotes;
	}

	/**
	 * 对外备注
	 */
	public void setOuterNotes(DataDictionaryValueVo outerNotes) {
		this.outerNotes = outerNotes;
	}

	/**
	 * 对内备注
	 */
	public String getInnerNotes() {
		return innerNotes;
	}

	/**
	 * 对内备注
	 */
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	/**
	 * 对内备注
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * 对内备注
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * 保险费ID
	 */
	public String getInsuranceId() {
		return insuranceId;
	}

	/**
	 * 保险费ID
	 */
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	/**
	 * 保险费code
	 */
	public String getInsuranceCode() {
		return insuranceCode;
	}

	/**
	 * 保险费code
	 */
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	/**
	 * 保价声明价值
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * 保价声明价值
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		// 四舍五入取整数
		this.insuranceAmount = CalculateFeeTotalUtils
				.formatNumberInteger(insuranceAmount);
	}

	/**
	 * 保价费率
	 */
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}

	/**
	 * 保价费率
	 */
	public void setInsuranceRate(BigDecimal insuranceRate) {
		// 保留两位小数
		this.insuranceRate = CalculateFeeTotalUtils
				.formatNumberTwoDecimal(insuranceRate);
	}

	/**
	 * 保价费
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * 保价费
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		// 四舍五入取整数
		this.insuranceFee = CalculateFeeTotalUtils
				.formatNumberInteger(insuranceFee);
	}

	/**
	 * 保额最大值
	 */
	public BigDecimal getMaxInsuranceAmount() {
		return maxInsuranceAmount;
	}

	/**
	 * 保额最大值
	 */
	public void setMaxInsuranceAmount(BigDecimal maxInsuranceAmount) {
		this.maxInsuranceAmount = maxInsuranceAmount;
	}

	/**
	 * 保价费最低一票
	 */
	public BigDecimal getMixInsuranceAmount() {
		return mixInsuranceAmount;
	}

	/**
	 * 保价费最低一票
	 */
	public void setMixInsuranceAmount(BigDecimal mixInsuranceAmount) {
		this.mixInsuranceAmount = mixInsuranceAmount;
	}

	/**
	 * 代收货款ID
	 */
	public String getCodId() {
		return codId;
	}

	/**
	 * 代收货款ID
	 */
	public void setCodId(String codId) {
		this.codId = codId;
	}

	/**
	 * 代收货款Code
	 */
	public String getCodCode() {
		return codCode;
	}

	/**
	 * 代收货款Code
	 */
	public void setCodCode(String codCode) {
		this.codCode = codCode;
	}

	/**
	 * 代收货款
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * 代收货款
	 */
	public void setCodAmount(BigDecimal codAmount) {
		// 四舍五入取整数
		//liyongfei 精确到角，放开对代收货款的限制
		this.codAmount = codAmount;
//		this.codAmount = CalculateFeeTotalUtils.formatNumberInteger(codAmount);
	}

	/**
	 * 代收费率
	 */
	public BigDecimal getCodRate() {
		return codRate;
	}

	/**
	 * 代收费率
	 */
	public void setCodRate(BigDecimal codRate) {
		// 保留两位小数
		this.codRate = CalculateFeeTotalUtils.formatNumberTwoDecimal(codRate);
	}

	/**
	 * 代收货款手续费
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * 代收货款手续费
	 */
	public void setCodFee(BigDecimal codFee) {
		// 四舍五入取整数
		this.codFee = CalculateFeeTotalUtils.formatNumberInteger(codFee);
	}

	/**
	 * 退款类型
	 */
	public DataDictionaryValueVo getRefundType() {
		return refundType;
	}

	/**
	 * 退款类型
	 */
	public void setRefundType(DataDictionaryValueVo refundType) {
		this.refundType = refundType;
	}

	/**
	 * 返单类别
	 */
	public DataDictionaryValueVo getReturnBillType() {
		return returnBillType;
	}

	/**
	 * 返单类别
	 */
	public void setReturnBillType(DataDictionaryValueVo returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * 返单费用名称
	 */
	public String getReturnBillChargeName() {
		return returnBillChargeName;
	}

	/**
	 * 返单费用名称
	 */
	public void setReturnBillChargeName(String returnBillChargeName) {
		this.returnBillChargeName = returnBillChargeName;
	}

	/**
	 * 预付费保密
	 */
	public Boolean getSecretPrepaid() {
		return secretPrepaid;
	}

	/**
	 * 预付费保密
	 */
	public void setSecretPrepaid(Boolean secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}

	/**
	 * 到付金额
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * 到付金额
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		// 四舍五入取整数
//		this.toPayAmount = CalculateFeeTotalUtils
//				.formatNumberInteger(toPayAmount);
//		liyongfei 精确到角，放开到付金额的限制
		this.toPayAmount = toPayAmount;
	}

	/**
	 * 预付金额
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * 预付金额
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		// 四舍五入取整数,
		/*this.prePayAmount = CalculateFeeTotalUtils
				.formatNumberInteger(prePayAmount);*/
		//精准计价放开
		this.prePayAmount=prePayAmount;
	}

	/**
	 * 送货费
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * 送货费
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		// 四舍五入取整数
		this.deliveryGoodsFee = CalculateFeeTotalUtils
				.formatNumberInteger(deliveryGoodsFee);
	}

	/**
	 * 其他费用
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * 其他费用
	 */
	public void setOtherFee(BigDecimal otherFee) {
		// 四舍五入取整数
		this.otherFee = CalculateFeeTotalUtils.formatNumberInteger(otherFee);
	}

	/**
	 * 包装手续费
	 */
	public BigDecimal getPackageFee() {
		return packageFee;
	}

	/**
	 * 包装手续费
	 */
	public void setPackageFee(BigDecimal packageFee) {
		// 四舍五入取整数
		this.packageFee = CalculateFeeTotalUtils
				.formatNumberInteger(packageFee);
	}

	/**
	 * 优惠费用
	 */
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	/**
	 * 优惠费用
	 */
	public void setPromotionsFee(BigDecimal promotionsFee) {
		// 四舍五入取整数
		this.promotionsFee = CalculateFeeTotalUtils
				.formatNumberInteger(promotionsFee);
	}

	/**
	 * 运费计费类型
	 */
	public DataDictionaryValueVo getBillingType() {
		return billingType;
	}

	/**
	 * 运费计费类型
	 */
	public void setBillingType(DataDictionaryValueVo billingType) {
		this.billingType = billingType;
	}

	/**
	 * 运费计费费率
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 运费计费费率
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		// 保留两位小数
		this.unitPrice = CalculateFeeTotalUtils
				.formatNumberTwoDecimal(unitPrice);
	}

	/**
	 * 公布价运费
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * 公布价运费
	 */
	public void setTransportFee(BigDecimal transportFee) {
		// 四舍五入取整数
		/*this.transportFee = CalculateFeeTotalUtils
				.formatNumberInteger(transportFee);*/
		//根据需求，精准报价
		this.transportFee=transportFee;
	}

	/**
	 * 折扣
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * 折扣
	 */
	public void setDiscount(BigDecimal discount) {
		// 四舍五入保留两位小数
		this.discount = CalculateFeeTotalUtils.formatNumberTwoDecimal(discount);
	}

	/**
	 * 增值费用
	 */
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	/**
	 * 增值费用
	 */
	public void setValueAddFee(BigDecimal valueAddFee) {
		// 四舍五入取整数
		this.valueAddFee = CalculateFeeTotalUtils
				.formatNumberInteger(valueAddFee);
	}

	/**
	 * 开单付款方式
	 */
	public DataDictionaryValueVo getPaidMethod() {
		return paidMethod;
	}

	/**
	 * 开单付款方式
	 */
	public void setPaidMethod(DataDictionaryValueVo paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * 到达类型
	 */
	public String getArriveType() {
		return arriveType;
	}

	/**
	 * 到达类型
	 */
	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}

	/**
	 * 运单状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 运单状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 禁行
	 */
	public String getForbiddenLine() {
		return forbiddenLine;
	}

	/**
	 * 禁行
	 */
	public void setForbiddenLine(String forbiddenLine) {
		this.forbiddenLine = forbiddenLine;
	}

	/**
	 * 合票方式
	 */
	public DataDictionaryValueVo getFreightMethod() {
		return freightMethod;
	}

	/**
	 * 合票方式
	 */
	public void setFreightMethod(DataDictionaryValueVo freightMethod) {
		this.freightMethod = freightMethod;
	}

	/**
	 * 航班类型
	 */
	public DataDictionaryValueVo getFlightNumberType() {
		return flightNumberType;
	}

	/**
	 * 航班类型
	 */
	public void setFlightNumberType(DataDictionaryValueVo flightNumberType) {
		this.flightNumberType = flightNumberType;
	}

	/**
	 * 航班时间
	 */
	public String getFlightShift() {
		return flightShift;
	}

	/**
	 * 航班时间
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	/**
	 * 总费用
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * 总费用
	 */
	public void setTotalFee(BigDecimal totalFee) {
		// 四舍五入取整数
//		this.totalFee = CalculateFeeTotalUtils.formatNumberInteger(totalFee);
		//liyongfei 精确到角，放开总运费限制
		this.totalFee = totalFee;
	}

	/**
	 * 优惠编码
	 */
	public String getPromotionsCode() {
		return promotionsCode;
	}

	/**
	 * 优惠编码
	 */
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}

	/**
	 * 生效日期
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 生效日期
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 结束日期
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 结束日期
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 开单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 开单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 更新时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 更新时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 开单人
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 开单人
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 更新人
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * 更新人
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 开单组织
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 开单组织
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 更新组织
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * 更新组织
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * 原运单ID
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * 原运单ID
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * 原运单号
	 */
	public String getRefCode() {
		return refCode;
	}

	/**
	 * 原运单号
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	/**
	 * 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 是否整车运单
	 */
	public Boolean getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * 是否整车运单
	 */
	public void setIsWholeVehicle(Boolean isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * 是否经过营业部
	 */
	public Boolean getIsPassDept() {
		return isPassDept;
	}

	/**
	 * 是否经过营业部
	 */
	public void setIsPassDept(Boolean isPassDept) {
		this.isPassDept = isPassDept;
	}

	/**
	 * 整车编号
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}

	/**
	 * 整车编号
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	/**
	 * 整车约车报价
	 */
	public BigDecimal getWholeVehicleAppfee() {
		return wholeVehicleAppfee;
	}

	/**
	 * 整车约车报价
	 */
	public void setWholeVehicleAppfee(BigDecimal wholeVehicleAppfee) {
		this.wholeVehicleAppfee = wholeVehicleAppfee;
	}

	/**
	 * 整车开单报价
	 */
	public BigDecimal getWholeVehicleActualfee() {
		return wholeVehicleActualfee;
	}

	/**
	 * 整车开单报价
	 */
	public void setWholeVehicleActualfee(BigDecimal wholeVehicleActualfee) {
		this.wholeVehicleActualfee = wholeVehicleActualfee;
	}

	/**
	 * 代打木架部门编码
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	/**
	 * 代打木架部门编码
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	/**
	 * 代打木架部门编码名称
	 */
	public String getPackingOrganizationName() {
		return packingOrganizationName;
	}

	/**
	 * 代打木架部门编码名称
	 */
	public void setPackingOrganizationName(String packingOrganizationName) {
		this.packingOrganizationName = packingOrganizationName;
	}

	/**
	 * 是否可以打木架
	 */
	public String getDoPacking() {
		return doPacking;
	}

	/**
	 * 是否可以打木架
	 */
	public void setDoPacking(String doPacking) {
		this.doPacking = doPacking;
	}

	/**
	 * 打木架货物件数
	 */
	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	/**
	 * 打木架货物件数
	 */
	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	/**
	 * 代打木架要求
	 */
	public String getStandRequirement() {
		return standRequirement;
	}

	/**
	 * 代打木架要求
	 */
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	/**
	 * 打木架货物尺寸
	 */
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	/**
	 * 打木架货物尺寸
	 */
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	/**
	 * 打木架货物体积
	 */
	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	/**
	 * 打木架货物体积
	 */
	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	/**
	 * 打木架货物费用
	 */
	public BigDecimal getStandCharge() {
		return standCharge;
	}

	/**
	 * 打木架货物费用
	 */
	public void setStandCharge(BigDecimal standCharge) {
		// 四舍五入取整数
		this.standCharge = CalculateFeeTotalUtils
				.formatNumberInteger(standCharge);
	}

	/**
	 * 打木箱货物件数
	 */
	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	/**
	 * 打木箱货物件数
	 */
	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	/**
	 * 代打木箱要求
	 */
	public String getBoxRequirement() {
		return boxRequirement;
	}

	/**
	 * 代打木箱要求
	 */
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	/**
	 * 打木箱货物尺寸
	 */
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	/**
	 * 打木箱货物尺寸
	 */
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	/**
	 * 打木箱货物体积
	 */
	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	/**
	 * 打木箱货物体积
	 */
	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}

	/**
	 * 打木箱货物费用
	 */
	public BigDecimal getBoxCharge() {
		return boxCharge;
	}

	/**
	 * 打木箱货物费用
	 */
	public void setBoxCharge(BigDecimal boxCharge) {
		// 四舍五入取整数
		this.boxCharge = CalculateFeeTotalUtils.formatNumberInteger(boxCharge);
	}

	/**
	 * 返款帐户开户名称
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * 返款帐户开户名称
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 返款帐户开户账户
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * 返款帐户开户账户
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * 返款帐户开户银行
	 */
	public String getAccountBank() {
		return accountBank;
	}

	/**
	 * 返款帐户开户银行
	 */
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	/**
	 * 计费重量
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * 计费重量
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * 接货费
	 */
	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	/**
	 * 接货费
	 */
	public void setPickupFee(BigDecimal pickupFee) {
		// 四舍五入取整数
		this.pickupFee = CalculateFeeTotalUtils.formatNumberInteger(pickupFee);
	}

	/**
	 * 装卸费
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	/**
	 * 装卸费
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		// 四舍五入取整数
		this.serviceFee = CalculateFeeTotalUtils
				.formatNumberInteger(serviceFee);
	}

	/**
	 * 装卸费费率
	 */
	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}

	/**
	 * 装卸费费率
	 */
	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		// 保留两位小数
		this.serviceFeeRate = CalculateFeeTotalUtils
				.formatNumberTwoDecimal(serviceFeeRate);
	}

	/**
	 * 预计到达时间
	 */
	public Date getPreArriveTime() {
		return preArriveTime;
	}

	/**
	 * 预计到达时间
	 */
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	/**
	 * 运输类型
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * 运输类型
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * 打印次数
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}

	/**
	 * 打印次数
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	/**
	 * 新增时间
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * 新增时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * 是否为PDA运单
	 */
	public Boolean getIsPdaBill() {
		return isPdaBill;
	}

	/**
	 * 是否为PDA运单
	 */
	public void setIsPdaBill(Boolean isPdaBill) {
		this.isPdaBill = isPdaBill;
	}

	/**
	 * 开单时间
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * 开单时间
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * 运单处理状态
	 */
	public String getWaybillstatus() {
		return waybillstatus;
	}

	/**
	 * 运单处理状态
	 */
	public void setWaybillstatus(String waybillstatus) {
		this.waybillstatus = waybillstatus;
	}

	/**
	 * 弃货处理的运单号
	 */
	public List<String> getDerelictNum() {
		return derelictNum;
	}

	/**
	 * 弃货处理的运单号
	 */
	public void setDerelictNum(List<String> derelictNum) {
		this.derelictNum = derelictNum;
	}

	/**
	 * 提货方式标记-标记是否内部带货
	 */
	public String getReceiveMethodFlag() {
		return receiveMethodFlag;
	}

	/**
	 * 提货方式标记-标记是否内部带货
	 */
	public void setReceiveMethodFlag(String receiveMethodFlag) {
		this.receiveMethodFlag = receiveMethodFlag;
	}

	/**
	 * 空运货物类型
	 */
	public DataDictionaryValueVo getAirGoodsType() {
		return airGoodsType;
	}

	/**
	 * 空运货物类型
	 */
	public void setAirGoodsType(DataDictionaryValueVo airGoodsType) {
		this.airGoodsType = airGoodsType;
	}

	/**
	 * 获取 客户开户银行.
	 * 
	 * @return the 客户开户银行
	 */
	public CusAccountEntity getOpenBank() {
		return openBank;
	}

	/**
	 * 设置 客户开户银行.
	 * 
	 * @param openBank
	 *            the new 客户开户银行
	 */
	public void setOpenBank(CusAccountEntity openBank) {
		this.openBank = openBank;
	}

	/**
	 * 计算运费获得的包装费
	 */
	public void setCalculatedPackageFee(BigDecimal calculatedPackageFee) {
		// 四舍五入取整数
		this.calculatedPackageFee = CalculateFeeTotalUtils
				.formatNumberInteger(calculatedPackageFee);
	}

	/**
	 * 计算运费获得的包装费
	 */
	public BigDecimal getCalculatedPackageFee() {
		return calculatedPackageFee;
	}

	/**
	 * @return the accountBankCode
	 */
	public String getAccountBankCode() {
		return accountBankCode;
	}

	/**
	 * @param accountBankCode
	 *            the accountBankCode to set
	 */
	public void setAccountBankCode(String accountBankCode) {
		this.accountBankCode = accountBankCode;
	}

	/**
	 * 
	 * 获取其他费用集合
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:25:06
	 * @return
	 */
	public List<OtherChargeVo> getAllChargeList() {
		return allChargeList;
	}

	/**
	 * 
	 * 设置其他费用集合
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:25:06
	 * @return
	 */
	public void setAllChargeList(List<OtherChargeVo> allChargeList) {
		this.allChargeList = allChargeList;
	}

	/**
	 * 
	 * 送货进仓费名称
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-15 下午06:21:07
	 * @return
	 */
	public String getDeliveryStorgeName() {
		return deliveryStorgeName;
	}

	/**
	 * 
	 * 送货进仓费名称
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-15 下午06:21:24
	 * @param deliveryStorgeName
	 */
	public void setDeliveryStorgeName(String deliveryStorgeName) {
		this.deliveryStorgeName = deliveryStorgeName;
	}

	/**
	 * @return the orderChannel
	 */
	public String getOrderChannel() {
		return orderChannel;
	}

	/**
	 * @param orderChannel
	 *            the orderChannel to set
	 */
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	/**
	 * @return the orderPayment
	 */
	public String getOrderPayment() {
		return orderPayment;
	}

	/**
	 * @param orderPayment
	 *            the orderPayment to set
	 */
	public void setOrderPayment(String orderPayment) {
		this.orderPayment = orderPayment;
	}

	/**
	 * 
	 * 线路是否能开AB货
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午03:49:55
	 * @return
	 */
	public Boolean getGoodsTypeIsAB() {
		return goodsTypeIsAB;
	}

	/**
	 * 
	 * 线路是否能开AB货
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午03:49:59
	 * @param goodsTypeIsAB
	 */
	public void setGoodsTypeIsAB(Boolean goodsTypeIsAB) {
		this.goodsTypeIsAB = goodsTypeIsAB;
	}

	/**
	 * 
	 * 是否能开代收货款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 上午10:36:59
	 * @return
	 */
	public String getCanAgentCollected() {
		return canAgentCollected;
	}

	/**
	 * 
	 * 是否能开代收货款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 上午10:37:15
	 * @param canAgentCollected
	 */
	public void setCanAgentCollected(String canAgentCollected) {
		this.canAgentCollected = canAgentCollected;
	}

	/**
	 * 通过计算得到的送货费
	 * 
	 * @return the calculateDeliveryGoodsFee
	 */
	public BigDecimal getCalculateDeliveryGoodsFee() {
		return calculateDeliveryGoodsFee;
	}

	/**
	 * 通过计算得到的送货费
	 * 
	 * @param calculateDeliveryGoodsFee
	 *            the calculateDeliveryGoodsFee to set
	 */
	public void setCalculateDeliveryGoodsFee(
			BigDecimal calculateDeliveryGoodsFee) {
		// 四舍五入取整数
		this.calculateDeliveryGoodsFee = CalculateFeeTotalUtils
				.formatNumberInteger(calculateDeliveryGoodsFee);
	}

	/**
	 * 送货费上限
	 * 
	 * @return the maxDeliveryGoodsFee
	 */
	public BigDecimal getMaxDeliveryGoodsFee() {
		return maxDeliveryGoodsFee;
	}

	/**
	 * 送货费上限
	 * 
	 * @param maxDeliveryGoodsFee
	 *            the maxDeliveryGoodsFee to set
	 */
	public void setMaxDeliveryGoodsFee(BigDecimal maxDeliveryGoodsFee) {
		this.maxDeliveryGoodsFee = maxDeliveryGoodsFee;
	}

	/**
	 * 
	 * 限保物品虚拟CODE
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 下午02:45:13
	 * @return
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * 
	 * 限保物品虚拟CODE
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 下午02:45:13
	 * @return
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * 收货部门开业时间
	 * 
	 * @return the receiveOrgCreateTime
	 */
	public Date getReceiveOrgCreateTime() {
		return receiveOrgCreateTime;
	}

	/**
	 * 收货部门开业时间
	 * 
	 * @param receiveOrgCreateTime
	 *            the receiveOrgCreateTime to set
	 */
	public void setReceiveOrgCreateTime(Date receiveOrgCreateTime) {
		this.receiveOrgCreateTime = receiveOrgCreateTime;
	}

	/**
	 * 最低一票费用
	 * 
	 * @return the minTransportFee
	 */
	public BigDecimal getMinTransportFee() {
		return minTransportFee;
	}

	/**
	 * 最低一票费用
	 * 
	 * @param minTransportFee
	 *            the minTransportFee to set
	 */
	public void setMinTransportFee(BigDecimal minTransportFee) {
		this.minTransportFee = minTransportFee;
	}

	/**
	 * 整车约车报价波动范围
	 * 
	 * @return the wholeVehicleActualfeeFlowRange
	 */
	public BigDecimal getWholeVehicleActualfeeFlowRangeUp() {
		return wholeVehicleActualfeeFlowRangeUp;
	}

	/**
	 * 整车约车报价波动范围
	 * 
	 * @param wholeVehicleActualfeeFlowRange
	 *            the wholeVehicleActualfeeFlowRange to set
	 */
	public void setWholeVehicleActualfeeFlowRangeUp(
			BigDecimal wholeVehicleActualfeeFlowRange) {
		this.wholeVehicleActualfeeFlowRangeUp = wholeVehicleActualfeeFlowRange;
	}

	/**
	 * @return the wholeVehicleActualfeeFlowRangeLow
	 */
	public BigDecimal getWholeVehicleActualfeeFlowRangeLow() {
		return wholeVehicleActualfeeFlowRangeLow;
	}

	/**
	 * @param wholeVehicleActualfeeFlowRangeLow
	 *            the wholeVehicleActualfeeFlowRangeLow to set
	 */
	public void setWholeVehicleActualfeeFlowRangeLow(
			BigDecimal wholeVehicleActualfeeFlowRangeLow) {
		this.wholeVehicleActualfeeFlowRangeLow = wholeVehicleActualfeeFlowRangeLow;
	}

	/**
	 * @return the changeColorTxt .
	 */
	public Map<JTextField, String> getChangeColorTxt() {
		return changeColorTxt;
	}

	/**
	 * @param changeColorTxt
	 *            the changeColorTxt to set.
	 */
	public void setChangeColorTxt(Map<JTextField, String> changeColorTxt) {
		this.changeColorTxt = changeColorTxt;
	}

	/**
	 * 
	 * 合同编号
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午09:57:56
	 * @return
	 */
	public String getAuditNo() {
		return auditNo;
	}

	/**
	 * 
	 * 合同编号
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午09:57:56
	 * @return
	 */
	public void setAuditNo(String auditNo) {
		this.auditNo = auditNo;
	}

	/**
	 * @return the deliveryCustomerArea .
	 */
	public String getDeliveryCustomerArea() {
		return deliveryCustomerArea;
	}

	/**
	 * @param deliveryCustomerArea
	 *            the deliveryCustomerArea to set.
	 */
	public void setDeliveryCustomerArea(String deliveryCustomerArea) {
		this.deliveryCustomerArea = deliveryCustomerArea;
	}

	/**
	 * @return the deliveryCustomerAreaDto .
	 */
	public AddressFieldDto getDeliveryCustomerAreaDto() {
		return deliveryCustomerAreaDto;
	}

	/**
	 * @param deliveryCustomerAreaDto
	 *            the deliveryCustomerAreaDto to set.
	 */
	public void setDeliveryCustomerAreaDto(
			AddressFieldDto deliveryCustomerAreaDto) {
		this.deliveryCustomerAreaDto = deliveryCustomerAreaDto;
	}

	/**
	 * @return the receiveCustomerArea .
	 */
	public String getReceiveCustomerArea() {
		return receiveCustomerArea;
	}

	/**
	 * @param receiveCustomerArea
	 *            the receiveCustomerArea to set.
	 */
	public void setReceiveCustomerArea(String receiveCustomerArea) {
		this.receiveCustomerArea = receiveCustomerArea;
	}

	/**
	 * @return the receiveCustomerAreaDto .
	 */
	public AddressFieldDto getReceiveCustomerAreaDto() {
		return receiveCustomerAreaDto;
	}

	/**
	 * @param receiveCustomerAreaDto
	 *            the receiveCustomerAreaDto to set.
	 */
	public void setReceiveCustomerAreaDto(AddressFieldDto receiveCustomerAreaDto) {
		this.receiveCustomerAreaDto = receiveCustomerAreaDto;
	}

	/**
	 * 
	 * 公里数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午04:58:25
	 * @return
	 */
	public BigDecimal getKilometer() {
		return kilometer;
	}

	/**
	 * 
	 * 公里数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午04:58:25
	 * @return
	 */
	public void setKilometer(BigDecimal kilometer) {
		this.kilometer = kilometer;
	}

	/**
	 * 
	 * 送货费集合
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午07:48:00
	 * @return
	 */
	public List<DeliverChargeEntity> getDeliverList() {
		return deliverList;
	}

	/**
	 * 
	 * 送货费集合
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午07:48:05
	 * @param deliverList
	 */
	public void setDeliverList(List<DeliverChargeEntity> deliverList) {
		this.deliverList = deliverList;
	}

	/**
	 * @return the contactAddressId .
	 */
	public String getContactAddressId() {
		return contactAddressId;
	}

	/**
	 * @param contactAddressId
	 *            the contactAddressId to set.
	 */
	public void setContactAddressId(String contactAddressId) {
		this.contactAddressId = contactAddressId;
	}

	/**
	 * @return the oldWaybillNo .
	 */
	public String getOldWaybillNo() {
		return oldWaybillNo;
	}

	/**
	 * @param oldWaybillNo
	 *            the oldWaybillNo to set.
	 */
	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}

	/**
	 * @return the isPCPending
	 */
	public Boolean isPCPending() {
		return isPCPending;
	}

	/**
	 * @param isPCPending
	 *            the isPCPending to set
	 */
	public void setPCPending(Boolean isPCPending) {
		this.isPCPending = isPCPending;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public Boolean getIsPCPending() {
		return isPCPending;
	}

	public void setIsPCPending(Boolean isPCPending) {
		this.isPCPending = isPCPending;
	}

	public String getInnerNotesBack() {
		return innerNotesBack;
	}

	public void setInnerNotesBack(String innerNotesBack) {
		this.innerNotesBack = innerNotesBack;
	}

	public String getArriveCharge() {
		return arriveCharge;
	}

	public void setArriveCharge(String arriveCharge) {
		this.arriveCharge = arriveCharge;
	}

	/**
	 * @return the pickupCentralizedDeptCode
	 */
	public String getPickupCentralizedDeptCode() {
		return pickupCentralizedDeptCode;
	}

	/**
	 * @param pickupCentralizedDeptCode
	 *            the pickupCentralizedDeptCode to set
	 */
	public void setPickupCentralizedDeptCode(String pickupCentralizedDeptCode) {
		this.pickupCentralizedDeptCode = pickupCentralizedDeptCode;
	}

	public BigDecimal getHandDeliveryFee() {
		return handDeliveryFee;
	}

	public void setHandDeliveryFee(BigDecimal handDeliveryFee) {
		this.handDeliveryFee = handDeliveryFee;
	}

	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}

	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}

	public String getIsAddCode() {
		return isAddCode;
	}

	public void setIsAddCode(String isAddCode) {
		this.isAddCode = isAddCode;
	}

	public Date getAddCodeTime() {
		return addCodeTime;
	}

	public void setAddCodeTime(Date addCodeTime) {
		this.addCodeTime = addCodeTime;
	}

	public DataDictionaryValueVo getReturnType() {
		return returnType;
	}

	public void setReturnType(DataDictionaryValueVo returnType) {
		this.returnType = returnType;
	}

	public String getExpressEmpName() {
		return expressEmpName;
	}

	public void setExpressEmpName(String expressEmpName) {
		this.expressEmpName = expressEmpName;
	}

	public String getExpressOrgCode() {
		return expressOrgCode;
	}

	public void setExpressOrgCode(String expressOrgCode) {
		this.expressOrgCode = expressOrgCode;
	}

	public String getExpressOrgName() {
		return expressOrgName;
	}

	public void setExpressOrgName(String expressOrgName) {
		this.expressOrgName = expressOrgName;
	}

	public String getExpressEmpCode() {
		return expressEmpCode;
	}

	public void setExpressEmpCode(String expressEmpCode) {
		this.expressEmpCode = expressEmpCode;
	}

	public String getPdaSerial() {
		return pdaSerial;
	}

	public void setPdaSerial(String pdaSerial) {
		this.pdaSerial = pdaSerial;
	}

	public String getBankTradeSerail() {
		return bankTradeSerail;
	}

	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}

	public void setCodAmountMax(BigDecimal bigDecimal) {
		this.codAmountMax = bigDecimal;
	}
	public BigDecimal getCodAmountMax() {
		return codAmountMax;
	}

	public BigDecimal getExpressContinueDiscount() {
		return expressContinueDiscount;
	}

	public void setExpressContinueDiscount(BigDecimal expressContinueDiscount) {
		this.expressContinueDiscount = expressContinueDiscount;
	}


	/**
	 * Dmana-9885运费
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02 下午16:49
	 */
	private BigDecimal crmTransportFee;
	
	/**
	 * Dmana-9885重量
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02下午16:50
	 */
	private BigDecimal crmWeight;
	
	/**
	 * Dmana-9885体积
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02下午16:52
	 */
	private BigDecimal crmVolume;	
	
	public BigDecimal getCrmTransportFee() {
		return crmTransportFee;
	}

	public void setCrmTransportFee(BigDecimal crmTransportFee) {
		this.crmTransportFee = crmTransportFee;
	}

	public BigDecimal getCrmWeight() {
		return crmWeight;
	}

	public void setCrmWeight(BigDecimal crmWeight) {
		this.crmWeight = crmWeight;
	}

	public BigDecimal getCrmVolume() {
		return crmVolume;
	}

	public void setCrmVolume(BigDecimal crmVolume) {
		this.crmVolume = crmVolume;
	}

	/**
	 * Dmana-9885巨商汇客户或者阿里巴巴客户是否满足特殊运费条件
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-05
	 */
	private boolean specialChannelFreight;

	public boolean getSpecialChannelFreight() {
		return specialChannelFreight;
	}

	public void setSpecialChannelFreight(boolean specialChannelFreight) {
		this.specialChannelFreight = specialChannelFreight;
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

	/**
	 * 上门发货
	 */	
	public boolean isHomeDelivery() {
		return homeDelivery;
	}
	
	public void setHomeDelivery(boolean homeDelivery) {
		this.homeDelivery = homeDelivery;
	}
	
	/**
	 * 修改前的费用
	 */
//	private PtpWaybillOrgVo ptpWaybillOrgVo ;
//
//	public PtpWaybillOrgVo getPtpWaybillOrgVo() {
//		return ptpWaybillOrgVo;
//	}
//
//	public void setPtpWaybillOrgVo(PtpWaybillOrgVo ptpWaybillOrgVo) {
//		this.ptpWaybillOrgVo = ptpWaybillOrgVo;
//	}
	
	//0:成本  1：提成  2：成本和提成
	private String feeType;

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
		
	//是否精准计价
	private boolean isAccurateCost;
	
	public boolean isAccurateCost() {
		return isAccurateCost;
	}
	public void setAccurateCost(boolean isAccurateCost) {
		this.isAccurateCost = isAccurateCost;
	}
	
}
