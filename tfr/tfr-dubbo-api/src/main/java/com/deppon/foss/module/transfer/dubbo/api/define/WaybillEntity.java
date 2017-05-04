/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 运单entity
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-shaohongliang,date:2012-10-11 下午5:09:25</p>
 * @since
 * @author Administrator
 * @date 2012-10-29 上午10:36:10
 */
public class WaybillEntity extends BaseEntity {

	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 3804228315728929322L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 订单来源
	 */
	private String orderChannel;
	
	/**
	 * 订单付款方式
	 */
	private String orderPaidMethod;

	/**
	 * 发货客户ID
	 */
	private String deliveryCustomerId;

	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	
    /**
     * 发货客户是否是大客户
     */
    private String deliveryBigCustomer;

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
     * 收货客户是否是大客户
     */
    private String receiveBigCustomer;

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
	 * 申请事由
	 */
	private String applicationReason;
	
	/**
	 * 是否为运单更改
	 */
	private String isWaybillRfc;
	
	public String getIsWaybillRfc() {
		return isWaybillRfc;
	}
	
	public void setIsWaybillRfc(String isWaybillRfc) {
		this.isWaybillRfc = isWaybillRfc;
	}
	public String getApplicationReason() {
		return applicationReason;
	}
	public void setApplicationReason(String applicationReason) {
		this.applicationReason = applicationReason;
	}

	//收货乡镇(街道)
	private String receiveCustomerVillageCode;

	public String getReceiveCustomerVillageCode() {
		return receiveCustomerVillageCode;
	}
	public void setReceiveCustomerVillageCode(String receiveCustomerVillageCode) {
		this.receiveCustomerVillageCode = receiveCustomerVillageCode;
	}

	//折让方式-装卸费
		private  BigDecimal dcServicefee;

		public BigDecimal getDcServicefee() {
			return dcServicefee;
		}

		public void setDcServicefee(BigDecimal dcServicefee) {
			this.dcServicefee = dcServicefee;
		}
	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;

	/**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgCode;

	/**
	 * 产品ID
	 */
	private String productId;

	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;

	/**
	 * 配载类型
	 */
	private String loadMethod;

	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
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
	 * 是否上门接货
	 */
	private String pickupToDoor;

	/**
	 * 司机
	 */
	private String driverCode;

	/**
	 * 是否集中接货
	 */
	private String pickupCentralized;

	/**
	 * 配载线路
	 */
	private String loadLineCode;

	/**
	 * 配载部门
	 */
	private String loadOrgCode;

	/**
	 * 最终配载部门
	 */
	private String lastLoadOrgCode;

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
	private String carDirectDelivery;

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
	private String goodsTypeCode;

	/**
	 * 是否贵重物品
	 */
	private String preciousGoods;

	/**
	 * 是否异形物品
	 */
	private String specialShapedGoods;

	/**
	 * 对外备注
	 */
	private String outerNotes;

	/**
	 * 对内备注
	 */
	private String innerNotes;

	/**
	 * 货物包装
	 */
	private String goodsPackage;

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
	private String refundType;

	/**
	 * 返单类别
	 */
	private String returnBillType;

	/**
	 * 预付费保密
	 */
	private String secretPrepaid;

	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;

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
	private String billingType;

	/**
	 * 运费计费费率
	 */
	private BigDecimal unitPrice;

	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee;

	/**
	 * 增值费用
	 */
	private BigDecimal valueAddFee;

	/**
	 * 开单付款方式
	 */
	private String paidMethod;

	/**
	 * 到达类型
	 */
	private String arriveType;

	/**
	 * 禁行
	 */
	private String forbiddenLine;

	/**
	 * 合票方式
	 */
	private String freightMethod;

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
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间（业务时间）
	 */
	private Date modifyTime;

	/**
	 * 开单时间
	 */
	private Date billTime;

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
	 * 管理营业部出发运单，制单部门列表。当登录部门为集中开单区域营业部时，添加所属集中开单组作为制单部门查询条件
	 */
	private List<String> createOrgCodeList;
	
	/**
	 * 管理营业部出发运单，收货部门列表。当登录部门为集中开单区域营业部时，添加所有所属集中开单组的部门 作为收货部门查询条件
	 */
	private List<String> receiveOrgCodeList;

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
	private String isWholeVehicle;

	/**
	 * 整车约车报价
	 */
	private BigDecimal wholeVehicleAppfee;

	/**
	 * 整车开单报价
	 */
	private BigDecimal wholeVehicleActualfee;

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
	 * 装卸费
	 */
	private BigDecimal serviceFee;

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
	 * 联系人地址ID
	 */
	private String contactAddressId;

	/**
	 * 航班类型
	 */
	private String flightNumberType;

	/**
	 * 收款部门
	 */
	private String collectionDepartment;

	/**
	 * 储运事项
	 */
	private String transportationRemark;

	/**
	 * 运单状态
	 */
	private String active;

	/**
	 * 是否经过营业部
	 */
	private String isPassOwnDepartment;

	/**
	 * 其他包装
	 */
	private String otherPackage;

	/**
	 * 纸包装
	 */
	private Integer paperNum;

	/**
	 * 木包装
	 */
	private Integer woodNum;

	/**
	 * 纤包装
	 */
	private Integer fibreNum;

	/**
	 * 托包装
	 */
	private Integer salverNum;

	/**
	 * 膜包装
	 */
	private Integer membraneNum;

	/**
	 * 发货联系人Id
	 */
	private String deliverCustContactId;

	/**
	 * 收获联系人ID
	 */
	private String receiverCustContactId;

	/**
	 * 处理类型:"PDA_ACTIVE"--PDA已补录 ，"PC_ACTIVE"--暂存已开单
	 */
	private String pendingType;

	/**
	 * 车牌号
	 */
	private String licensePlateNum;

	/**
	 * 约车编号
	 */
	private String orderVehicleNum;
	
	/**
	 * 运单提交人姓名
	 */
	private String createUserName;
	
	/**
	 * 运单提交人所在部门名称
	 */
	private String createUserDeptName;
	
	/**
	 * 收货部门名称
	 */
	private String receiveOrgName;
	
	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;
	
	/**
	 * 配载部门名称
	 */
	private String loadOrgName;
	
	/**
	 * 最终配置部门名称
	 */
	private String lastLoadOrgName;
	
	/**
	 * 中转 返货的时候的中转起点外场
	 */
	private String transferStartOrgCode;
	
	/**
	 * 中转 返货的时候的中转起点外场名称
	 */
	private String transferStartOrgName;

	
	/**
	 * 公里数
	 */
	private BigDecimal kilometer;
	
	/**
	 * 是否迁移数据
	 */
	private String isInit;
	
	/**
	 * 是否经济自提件
	 */
	private String isEconomyGoods;
	/**
	 * 经济自提件类型
	 */
	private String economyGoodsType;
	
	
	private String specialOffer;
	
	/**
     * 最低费率--目前只有保费使用
     */
    private BigDecimal minFeeRate;
    
    /**
     * 最高费率--目前只有保费使用
     */
    private BigDecimal maxFeeRate;
	
	/**
	 * 是否快递
	 * @return
	 */
	private String isExpress;
	
	/**
	 * 是否子母件
	 */
	private String isPicPackage;
	//合伙人项目增加字段
	/**
	 * 保价费
	 */
	private BigDecimal supportFee ;
	
	/**
	 * 是否免费接货
	 */
	private String freePickupGoods;

	

	public String getFreePickupGoods() {
		return freePickupGoods;
	}

	public void setFreePickupGoods(String freePickupGoods) {
		this.freePickupGoods = freePickupGoods;
	}

	
	/**
	 * 代收手续费
	 */
	private BigDecimal collectingFee ;
	
	/**
	 * 整车存在自动受理
	 */
	private String oldVersionWaybillId;
	
	public String getOldVersionWaybillId() {
		return oldVersionWaybillId;
	}
	public void setOldVersionWaybillId(String oldVersionWaybillId) {
		this.oldVersionWaybillId = oldVersionWaybillId;
	}
	public BigDecimal getSupportFee() {
		return supportFee;
	}
	
	public void setSupportFee(BigDecimal supportFee) {
		this.supportFee = supportFee;
	}
	
	public BigDecimal getCollectingFee() {
		return collectingFee;
	}
	
	public void setCollectingFee(BigDecimal collectingFee) {
		this.collectingFee = collectingFee;
	}
	
	public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}
	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
	}
	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}
	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}
	//上报人
	private String userCode;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	// 最低一票费用---206860
	private BigDecimal minTransportFee;
	
	
	public BigDecimal getMinTransportFee() {
		return minTransportFee;
	}
	public void setMinTransportFee(BigDecimal minTransportFee) {
		this.minTransportFee = minTransportFee;
	}
	//优惠券的票面费用
	private BigDecimal couponFee;
	
	//优惠Dto
	//private CouponInfoDto couponInfoDtoParam;
	
	//优惠券优惠费用归集类型
	String couponRankType = null;
	
	
//liyongfei 新增运单类型
	private String waybillType;
	
	/**
	 * 添加原单号和原优惠费用
	 * @return
	 */
	private String originalEmployeeNo;
	private BigDecimal originalFee;
	
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
	public String getWaybillType() {
		return waybillType;
	}
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	public String getSpecialOffer() {
		return specialOffer;
	}
	public void setSpecialOffer(String specialOffer) {
		this.specialOffer = specialOffer;
	}
	public String getIsEconomyGoods() {
		return isEconomyGoods;
	}
	public void setIsEconomyGoods(String isEconomyGoods) {
		this.isEconomyGoods = isEconomyGoods;
	}
	public String getEconomyGoodsType() {
		return economyGoodsType;
	}
	public void setEconomyGoodsType(String economyGoodsType) {
		this.economyGoodsType = economyGoodsType;
	}
	
	/**
	 * 公里数
	 */
	public BigDecimal getKilometer() {
		return kilometer;
	}
	/**
	 * 公里数
	 */
	public void setKilometer(BigDecimal kilometer) {
		this.kilometer = kilometer;
	}

	/**
	 * @return the transferStartOrgCode
	 */
	public String getTransferStartOrgCode() {
		return transferStartOrgCode;
	}

	/**
	 * @param transferStartOrgCode the transferStartOrgCode to set
	 */
	public void setTransferStartOrgCode(String transferStartOrgCode) {
		this.transferStartOrgCode = transferStartOrgCode;
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
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the orderNo .
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set.
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the deliveryCustomerId .
	 */
	public String getDeliveryCustomerId() {
		return deliveryCustomerId;
	}

	/**
	 * @param deliveryCustomerId
	 *            the deliveryCustomerId to set.
	 */
	public void setDeliveryCustomerId(String deliveryCustomerId) {
		this.deliveryCustomerId = deliveryCustomerId;
	}

	/**
	 * @return the deliveryCustomerCode .
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * @param deliveryCustomerCode
	 *            the deliveryCustomerCode to set.
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * @return the deliveryCustomerName .
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * @param deliveryCustomerName
	 *            the deliveryCustomerName to set.
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * @return the deliveryCustomerMobilephone .
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * @param deliveryCustomerMobilephone
	 *            the deliveryCustomerMobilephone to set.
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * @return the deliveryCustomerPhone .
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * @param deliveryCustomerPhone
	 *            the deliveryCustomerPhone to set.
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * @return the deliveryCustomerContact .
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * @param deliveryCustomerContact
	 *            the deliveryCustomerContact to set.
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * @return the deliveryCustomerNationCode .
	 */
	public String getDeliveryCustomerNationCode() {
		return deliveryCustomerNationCode;
	}

	/**
	 * @param deliveryCustomerNationCode
	 *            the deliveryCustomerNationCode to set.
	 */
	public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
		this.deliveryCustomerNationCode = deliveryCustomerNationCode;
	}

	/**
	 * @return the deliveryCustomerProvCode .
	 */
	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	/**
	 * @param deliveryCustomerProvCode
	 *            the deliveryCustomerProvCode to set.
	 */
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	/**
	 * @return the deliveryCustomerCityCode .
	 */
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	/**
	 * @param deliveryCustomerCityCode
	 *            the deliveryCustomerCityCode to set.
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	/**
	 * @return the deliveryCustomerDistCode .
	 */
	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}

	/**
	 * @param deliveryCustomerDistCode
	 *            the deliveryCustomerDistCode to set.
	 */
	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}

	/**
	 * @return the deliveryCustomerAddress .
	 */
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	/**
	 * @param deliveryCustomerAddress
	 *            the deliveryCustomerAddress to set.
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	/**
	 * @return the receiveCustomerId .
	 */
	public String getReceiveCustomerId() {
		return receiveCustomerId;
	}

	/**
	 * @param receiveCustomerId
	 *            the receiveCustomerId to set.
	 */
	public void setReceiveCustomerId(String receiveCustomerId) {
		this.receiveCustomerId = receiveCustomerId;
	}

	/**
	 * @return the receiveCustomerCode .
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * @param receiveCustomerCode
	 *            the receiveCustomerCode to set.
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * @return the receiveCustomerName .
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName
	 *            the receiveCustomerName to set.
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return the receiveCustomerMobilephone .
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * @param receiveCustomerMobilephone
	 *            the receiveCustomerMobilephone to set.
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * @return the receiveCustomerPhone .
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone
	 *            the receiveCustomerPhone to set.
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return the receiveCustomerContact .
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact
	 *            the receiveCustomerContact to set.
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return the receiveCustomerNationCode .
	 */
	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}

	/**
	 * @param receiveCustomerNationCode
	 *            the receiveCustomerNationCode to set.
	 */
	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
	}

	/**
	 * @return the receiveCustomerProvCode .
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * @param receiveCustomerProvCode
	 *            the receiveCustomerProvCode to set.
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * @return the receiveCustomerCityCode .
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * @param receiveCustomerCityCode
	 *            the receiveCustomerCityCode to set.
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * @return the receiveCustomerDistCode .
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * @param receiveCustomerDistCode
	 *            the receiveCustomerDistCode to set.
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	/**
	 * @return the receiveCustomerAddress .
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * @param receiveCustomerAddress
	 *            the receiveCustomerAddress to set.
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * @return the receiveOrgCode .
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode
	 *            the receiveOrgCode to set.
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the productId .
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set.
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productCode .
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the receiveMethod .
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod
	 *            the receiveMethod to set.
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return the customerPickupOrgCode .
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * @param customerPickupOrgCode
	 *            the customerPickupOrgCode to set.
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * @return the loadMethod .
	 */
	public String getLoadMethod() {
		return loadMethod;
	}

	/**
	 * @param loadMethod
	 *            the loadMethod to set.
	 */
	public void setLoadMethod(String loadMethod) {
		this.loadMethod = loadMethod;
	}

	/**
	 * @return the targetOrgCode .
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * @param targetOrgCode
	 *            the targetOrgCode to set.
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * @return the pickupToDoor .
	 */
	public String getPickupToDoor() {
		return pickupToDoor;
	}

	/**
	 * @param pickupToDoor
	 *            the pickupToDoor to set.
	 */
	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	/**
	 * @return the driverCode .
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode
	 *            the driverCode to set.
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * @return the pickupCentralized .
	 */
	public String getPickupCentralized() {
		return pickupCentralized;
	}

	/**
	 * @param pickupCentralized
	 *            the pickupCentralized to set.
	 */
	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	/**
	 * @return the loadLineCode .
	 */
	public String getLoadLineCode() {
		return loadLineCode;
	}

	/**
	 * @param loadLineCode
	 *            the loadLineCode to set.
	 */
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}

	/**
	 * @return the loadOrgCode .
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * @param loadOrgCode
	 *            the loadOrgCode to set.
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * @return the lastLoadOrgCode .
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param lastLoadOrgCode
	 *            the lastLoadOrgCode to set.
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * @return the preDepartureTime .
	 */
	public Date getPreDepartureTime() {
		return preDepartureTime;
	}

	/**
	 * @param preDepartureTime
	 *            the preDepartureTime to set.
	 */
	public void setPreDepartureTime(Date preDepartureTime) {
		this.preDepartureTime = preDepartureTime;
	}

	/**
	 * @return the preCustomerPickupTime .
	 */
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	/**
	 * @param preCustomerPickupTime
	 *            the preCustomerPickupTime to set.
	 */
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	/**
	 * @return the carDirectDelivery .
	 */
	public String getCarDirectDelivery() {
		return carDirectDelivery;
	}

	/**
	 * @param carDirectDelivery
	 *            the carDirectDelivery to set.
	 */
	public void setCarDirectDelivery(String carDirectDelivery) {
		this.carDirectDelivery = carDirectDelivery;
	}

	/**
	 * @return the goodsName .
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the goodsQtyTotal .
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal
	 *            the goodsQtyTotal to set.
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return the goodsWeightTotal .
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * @param goodsWeightTotal
	 *            the goodsWeightTotal to set.
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * @return the goodsVolumeTotal .
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal
	 *            the goodsVolumeTotal to set.
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the goodsSize .
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * @param goodsSize
	 *            the goodsSize to set.
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	/**
	 * @return the goodsTypeCode .
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	/**
	 * @param goodsTypeCode
	 *            the goodsTypeCode to set.
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	/**
	 * @return the preciousGoods .
	 */
	public String getPreciousGoods() {
		return preciousGoods;
	}

	/**
	 * @param preciousGoods
	 *            the preciousGoods to set.
	 */
	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	/**
	 * @return the specialShapedGoods .
	 */
	public String getSpecialShapedGoods() {
		return specialShapedGoods;
	}

	/**
	 * @param specialShapedGoods
	 *            the specialShapedGoods to set.
	 */
	public void setSpecialShapedGoods(String specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}

	/**
	 * @return the outerNotes .
	 */
	public String getOuterNotes() {
		return outerNotes;
	}

	/**
	 * @param outerNotes
	 *            the outerNotes to set.
	 */
	public void setOuterNotes(String outerNotes) {
		this.outerNotes = outerNotes;
	}

	/**
	 * @return the innerNotes .
	 */
	public String getInnerNotes() {
		return innerNotes;
	}

	/**
	 * @param innerNotes
	 *            the innerNotes to set.
	 */
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	/**
	 * @return the goodsPackage .
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * @param goodsPackage
	 *            the goodsPackage to set.
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * @return the insuranceAmount .
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount
	 *            the insuranceAmount to set.
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return the insuranceRate .
	 */
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}

	/**
	 * @param insuranceRate
	 *            the insuranceRate to set.
	 */
	public void setInsuranceRate(BigDecimal insuranceRate) {
		this.insuranceRate = insuranceRate;
	}

	/**
	 * @return the insuranceFee .
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * @param insuranceFee
	 *            the insuranceFee to set.
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * @return the codAmount .
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount
	 *            the codAmount to set.
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the codRate .
	 */
	public BigDecimal getCodRate() {
		return codRate;
	}

	/**
	 * @param codRate
	 *            the codRate to set.
	 */
	public void setCodRate(BigDecimal codRate) {
		this.codRate = codRate;
	}

	/**
	 * @return the codFee .
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee
	 *            the codFee to set.
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	/**
	 * @return the refundType .
	 */
	public String getRefundType() {
		return refundType;
	}

	/**
	 * @param refundType
	 *            the refundType to set.
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	/**
	 * @return the returnBillType .
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * @param returnBillType
	 *            the returnBillType to set.
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * @return the secretPrepaid .
	 */
	public String getSecretPrepaid() {
		return secretPrepaid;
	}

	/**
	 * @param secretPrepaid
	 *            the secretPrepaid to set.
	 */
	public void setSecretPrepaid(String secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}

	/**
	 * @return the toPayAmount .
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount
	 *            the toPayAmount to set.
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the prePayAmount .
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount
	 *            the prePayAmount to set.
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return the deliveryGoodsFee .
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * @param deliveryGoodsFee
	 *            the deliveryGoodsFee to set.
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	/**
	 * @return the otherFee .
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * @param otherFee
	 *            the otherFee to set.
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * @return the packageFee .
	 */
	public BigDecimal getPackageFee() {
		return packageFee;
	}

	/**
	 * @param packageFee
	 *            the packageFee to set.
	 */
	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}

	/**
	 * @return the promotionsFee .
	 */
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	/**
	 * @param promotionsFee
	 *            the promotionsFee to set.
	 */
	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

	/**
	 * @return the billingType .
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType
	 *            the billingType to set.
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return the unitPrice .
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set.
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the transportFee .
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * @param transportFee
	 *            the transportFee to set.
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * @return the valueAddFee .
	 */
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	/**
	 * @param valueAddFee
	 *            the valueAddFee to set.
	 */
	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	/**
	 * @return the paidMethod .
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * @param paidMethod
	 *            the paidMethod to set.
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * @return the arriveType .
	 */
	public String getArriveType() {
		return arriveType;
	}

	/**
	 * @param arriveType
	 *            the arriveType to set.
	 */
	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}

	/**
	 * @return the forbiddenLine .
	 */
	public String getForbiddenLine() {
		return forbiddenLine;
	}

	/**
	 * @param forbiddenLine
	 *            the forbiddenLine to set.
	 */
	public void setForbiddenLine(String forbiddenLine) {
		this.forbiddenLine = forbiddenLine;
	}

	/**
	 * @return the freightMethod .
	 */
	public String getFreightMethod() {
		return freightMethod;
	}

	/**
	 * @param freightMethod
	 *            the freightMethod to set.
	 */
	public void setFreightMethod(String freightMethod) {
		this.freightMethod = freightMethod;
	}

	/**
	 * @return the flightShift .
	 */
	public String getFlightShift() {
		return flightShift;
	}

	/**
	 * @param flightShift
	 *            the flightShift to set.
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	/**
	 * @return the totalFee .
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee
	 *            the totalFee to set.
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the promotionsCode .
	 */
	public String getPromotionsCode() {
		return promotionsCode;
	}

	/**
	 * @param promotionsCode
	 *            the promotionsCode to set.
	 */
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}

	/**
	 * @return the createTime .
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime .
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the billTime .
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime
	 *            the billTime to set.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return the createUserCode .
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 *            the createUserCode to set.
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the modifyUserCode .
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 *            the modifyUserCode to set.
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return the createOrgCode .
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode
	 *            the createOrgCode to set.
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the modifyOrgCode .
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * @param modifyOrgCode
	 *            the modifyOrgCode to set.
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * @return the refId .
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * @param refId
	 *            the refId to set.
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * @return the refCode .
	 */
	public String getRefCode() {
		return refCode;
	}

	/**
	 * @param refCode
	 *            the refCode to set.
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	/**
	 * @return the currencyCode .
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            the currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the isWholeVehicle .
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * @param isWholeVehicle
	 *            the isWholeVehicle to set.
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * @return the wholeVehicleAppfee .
	 */
	public BigDecimal getWholeVehicleAppfee() {
		return wholeVehicleAppfee;
	}

	/**
	 * @param wholeVehicleAppfee
	 *            the wholeVehicleAppfee to set.
	 */
	public void setWholeVehicleAppfee(BigDecimal wholeVehicleAppfee) {
		this.wholeVehicleAppfee = wholeVehicleAppfee;
	}

	/**
	 * @return the wholeVehicleActualfee .
	 */
	public BigDecimal getWholeVehicleActualfee() {
		return wholeVehicleActualfee;
	}

	/**
	 * @param wholeVehicleActualfee
	 *            the wholeVehicleActualfee to set.
	 */
	public void setWholeVehicleActualfee(BigDecimal wholeVehicleActualfee) {
		this.wholeVehicleActualfee = wholeVehicleActualfee;
	}

	/**
	 * @return the accountName .
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set.
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountCode .
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode
	 *            the accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountBank .
	 */
	public String getAccountBank() {
		return accountBank;
	}

	/**
	 * @param accountBank
	 *            the accountBank to set.
	 */
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	/**
	 * @return the billWeight .
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * @param billWeight
	 *            the billWeight to set.
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * @return the pickupFee .
	 */
	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	/**
	 * @param pickupFee
	 *            the pickupFee to set.
	 */
	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	/**
	 * @return the serviceFee .
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	/**
	 * @param serviceFee
	 *            the serviceFee to set.
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * @return the preArriveTime .
	 */
	public Date getPreArriveTime() {
		return preArriveTime;
	}

	/**
	 * @param preArriveTime
	 *            the preArriveTime to set.
	 */
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	/**
	 * @return the transportType .
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @param transportType
	 *            the transportType to set.
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * @return the printTimes .
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}

	/**
	 * @param printTimes
	 *            the printTimes to set.
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	/**
	 * @return the addTime .
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set.
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
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
	 * @return the flightNumberType .
	 */
	public String getFlightNumberType() {
		return flightNumberType;
	}

	/**
	 * @param flightNumberType
	 *            the flightNumberType to set.
	 */
	public void setFlightNumberType(String flightNumberType) {
		this.flightNumberType = flightNumberType;
	}

	/**
	 * @return the collectionDepartment .
	 */
	public String getCollectionDepartment() {
		return collectionDepartment;
	}

	/**
	 * @param collectionDepartment
	 *            the collectionDepartment to set.
	 */
	public void setCollectionDepartment(String collectionDepartment) {
		this.collectionDepartment = collectionDepartment;
	}

	


	
	/**
	 * @return the transportationRemark .
	 */
	public String getTransportationRemark() {
		return transportationRemark;
	}

	
	/**
	 *@param transportationRemark the transportationRemark to set.
	 */
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}

	/**
	 * @return the active .
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set.
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the isPassOwnDepartment .
	 */
	public String getIsPassOwnDepartment() {
		return isPassOwnDepartment;
	}

	/**
	 * @param isPassOwnDepartment
	 *            the isPassOwnDepartment to set.
	 */
	public void setIsPassOwnDepartment(String isPassOwnDepartment) {
		this.isPassOwnDepartment = isPassOwnDepartment;
	}

	/**
	 * @return the otherPackage .
	 */
	public String getOtherPackage() {
		return otherPackage;
	}

	/**
	 * @param otherPackage
	 *            the otherPackage to set.
	 */
	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}

	/**
	 * @return the paperNum .
	 */
	public Integer getPaperNum() {
		return paperNum;
	}

	/**
	 * @param paperNum
	 *            the paperNum to set.
	 */
	public void setPaperNum(Integer paperNum) {
		this.paperNum = paperNum;
	}

	/**
	 * @return the woodNum .
	 */
	public Integer getWoodNum() {
		return woodNum;
	}

	/**
	 * @param woodNum
	 *            the woodNum to set.
	 */
	public void setWoodNum(Integer woodNum) {
		this.woodNum = woodNum;
	}

	/**
	 * @return the fibreNum .
	 */
	public Integer getFibreNum() {
		return fibreNum;
	}

	/**
	 * @param fibreNum
	 *            the fibreNum to set.
	 */
	public void setFibreNum(Integer fibreNum) {
		this.fibreNum = fibreNum;
	}

	/**
	 * @return the salverNum .
	 */
	public Integer getSalverNum() {
		return salverNum;
	}

	/**
	 * @param salverNum
	 *            the salverNum to set.
	 */
	public void setSalverNum(Integer salverNum) {
		this.salverNum = salverNum;
	}

	/**
	 * @return the membraneNum .
	 */
	public Integer getMembraneNum() {
		return membraneNum;
	}

	/**
	 * @param membraneNum
	 *            the membraneNum to set.
	 */
	public void setMembraneNum(Integer membraneNum) {
		this.membraneNum = membraneNum;
	}

	/**
	 * @return the deliverCustContactId .
	 */
	public String getDeliverCustContactId() {
		return deliverCustContactId;
	}

	/**
	 * @param deliverCustContactId
	 *            the deliverCustContactId to set.
	 */
	public void setDeliverCustContactId(String deliverCustContactId) {
		this.deliverCustContactId = deliverCustContactId;
	}

	/**
	 * @return the receiverCustContactId .
	 */
	public String getReceiverCustContactId() {
		return receiverCustContactId;
	}

	/**
	 * @param receiverCustContactId
	 *            the receiverCustContactId to set.
	 */
	public void setReceiverCustContactId(String receiverCustContactId) {
		this.receiverCustContactId = receiverCustContactId;
	}

	/**
	 * @return the pendingType .
	 */
	public String getPendingType() {
		return pendingType;
	}

	/**
	 * @param pendingType
	 *            the pendingType to set.
	 */
	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}

	/**
	 * @return the licensePlateNum .
	 */
	public String getLicensePlateNum() {
		return licensePlateNum;
	}

	/**
	 * @param licensePlateNum
	 *            the licensePlateNum to set.
	 */
	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}

	/**
	 * @return the orderVehicleNum .
	 */
	public String getOrderVehicleNum() {
		return orderVehicleNum;
	}

	/**
	 * @param orderVehicleNum
	 *            the orderVehicleNum to set.
	 */
	public void setOrderVehicleNum(String orderVehicleNum) {
		this.orderVehicleNum = orderVehicleNum;
	}

	/**
	  * @return  the orderChannel
	 */
	public String getOrderChannel() {
		return orderChannel;
	}

	/**
	 * @param orderChannel the orderChannel to set
	 */
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getOrderPaidMethod() {
		return orderPaidMethod;
	}

	public void setOrderPaidMethod(String orderPaidMethod) {
		this.orderPaidMethod = orderPaidMethod;
	}

	public List<String> getCreateOrgCodeList() {
		return createOrgCodeList;
	}

	public void setCreateOrgCodeList(List<String> createOrgCodeList) {
		this.createOrgCodeList = createOrgCodeList;
	}

	
	/**
	 * @return the receiveOrgCodeList .
	 */
	public List<String> getReceiveOrgCodeList() {
		return receiveOrgCodeList;
	}

	
	/**
	 *@param receiveOrgCodeList the receiveOrgCodeList to set.
	 */
	public void setReceiveOrgCodeList(List<String> receiveOrgCodeList) {
		this.receiveOrgCodeList = receiveOrgCodeList;
	}

	
	/**
	 * @return the createUserName .
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	
	/**
	 *@param createUserName the createUserName to set.
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	
	/**
	 * @return the createUserDeptName .
	 */
	public String getCreateUserDeptName() {
		return createUserDeptName;
	}

	
	/**
	 *@param createUserDeptName the createUserDeptName to set.
	 */
	public void setCreateUserDeptName(String createUserDeptName) {
		this.createUserDeptName = createUserDeptName;
	}

	
	/**
	 * @return the receiveOrgName .
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	
	/**
	 *@param receiveOrgName the receiveOrgName to set.
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	
	/**
	 * @return the customerPickupOrgName .
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	
	/**
	 *@param customerPickupOrgName the customerPickupOrgName to set.
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	
	/**
	 * @return the loadOrgName .
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}

	
	/**
	 *@param loadOrgName the loadOrgName to set.
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}

	
	/**
	 * @return the lastLoadOrgName .
	 */
	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	
	/**
	 *@param lastLoadOrgName the lastLoadOrgName to set.
	 */
	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}
	/**
	  * @return  the isInit
	 */
	public String getIsInit() {
		return isInit;
	}
	/**
	 * @param isInit the isInit to set
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}
	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	public String getIsExpress() {
		return isExpress;
	}
	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	/**
	 * 返货原因
	 */
	private String returnReason;
	/**
	 * 返货类型
	 */
	private String returnType;
	
	/**
	 * 返货方式
	 * */
	private String returnMode;
	/**
	 * 返货原因明细
	 */
	private String returnDetail;
	/**
	 * 上报通道
	 */
	private String returnChanle;
	
	public String getReturnDetail() {
		return returnDetail;
	}
	public void setReturnDetail(String returnDetail) {
		this.returnDetail = returnDetail;
	}
	public String getReturnChanle() {
		return returnChanle;
	}
	public void setReturnChanle(String returnChanle) {
		this.returnChanle = returnChanle;
	}
	public String getReturnMode() {
		return returnMode;
	}
	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}
	

	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	/**
	 * 部门表编号deptCode
	 */
	private String deptCode;
	/**
	 * 员工姓名userName
	 */
	private String userName;

	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
 	 public BigDecimal getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(BigDecimal couponFee) {
		this.couponFee = couponFee;
	}
	/*public CouponInfoDto getCouponInfoDtoParam() {
		return couponInfoDtoParam;
	}
	public void setCouponInfoDtoParam(CouponInfoDto couponInfoDtoParam) {
		this.couponInfoDtoParam = couponInfoDtoParam;
	}*/
	public String getCouponRankType() {
		return couponRankType;
	}
	public void setCouponRankType(String couponRankType) {
		this.couponRankType = couponRankType;
	}
	public String getIsPicPackage() {
		return isPicPackage;
	}
	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}}
