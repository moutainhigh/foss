package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递开单、更改单请求实体
 * @author foss-231434-bieyexiong
 * @date 2016-4-20 16:27
 */
public class EcsFossWaybillPickupRequest implements Serializable{

    private final static long serialVersionUID = 11082011L;
    
    //开单员工号
    private String empCode;
    
    //开单员姓名
    private String empName;
    
    //开单员部门编码
    private String currentDeptCode;
    
    //开单员部门名称
    private String currentDeptName;
    
    //是否更改：Y：更改 N：作废
    private String isChange;
    
    //运单号(更改前)
    private String oldWaybillNo;
    
    //运输性质(更改前)
    private String oldProductCode;
    
    //开单部门(更改前)
    private String oldCreateOrgCode;
    
    //收货部门(出发部门)
    private String oldReceiveOrgCode;
    
    //开单付款方式
    private String oldPaidMethod;
    
    //预付金额
    private BigDecimal oldPrePayAmount;
    
    //运单ID
    private String id;
    
    //运单号
    private String waybillNo;
    
    //产品ID
    private String productId;
    
    //运输性质
    private String productCode;
    
    //发票标记
    private String invoiceMark;
    
    //开单部门
    private String createOrgCode;
    
    //运单提交人所在部门名称
    private String createUserDeptName;
    
    //收货部门(出发部门)
    private String receiveOrgCode;
    
    //出发部门名称
    private String receiveOrgName;
    
    //最终配载部门
    private String lastLoadOrgCode;
    
    //到达部门名称
    private String lastLoadOrgName;
    
    //提货网点
    private String customerPickupOrgCode;
    
    //目的站
    private String targetOrgCode;
    
    //更新组织
    private String modifyOrgCode;
    
    //发货客户编码
    private String deliveryCustomerCode;
    
    //发货客户名称
    private String deliveryCustomerName;
    
    //发货客户联系人
    private String deliveryCustomerContact;
    
    //发货客户手机
    private String deliveryCustomerMobilephone;
    
    //发货客户电话
    private String deliveryCustomerPhone;
    
    //收货客户编码
    private String receiveCustomerCode;
    
    //收货客户联系人
    private String receiveCustomerContact;
    
    //收货客户手机
    private String receiveCustomerMobilephone;
    
    //提货方式
    private String receiveMethod;
    
    //货物名称
    private String goodsName;
    
    //货物总件数
    private Integer goodsQtyTotal;
    
    //货物总体积
    private BigDecimal goodsVolumeTotal;
    
    //计费重量
    private BigDecimal billWeight;
    
    //货物总重量
    private BigDecimal goodsWeightTotal;
    
    //开单付款方式
    private String paidMethod;
    
    //退款类型
    private String refundType;
    
    //总费用
    private BigDecimal totalFee;
    
    //预付金额
    private BigDecimal prePayAmount;
    
    //到付金额
    private BigDecimal toPayAmount;
    
    //代收货款
    private BigDecimal codAmount;
    
    //公布价运费
    private BigDecimal transportFee;
    
    //接货费
    private BigDecimal pickupFee;
    
    //送货费
    private BigDecimal deliveryGoodsFee;
    
    //包装手续费
    private BigDecimal packageFee;
    
    //代收货款手续费
    private BigDecimal codFee;
    
    //保价费
    private BigDecimal insuranceFee;
    
    //其他费用
    private BigDecimal otherFee;
    
    //增值费用
    private BigDecimal valueAddFee;
    
    //优惠费用
    private BigDecimal promotionsFee;
    
    //装卸费
    private BigDecimal serviceFee;
    
    //币种
    private String currencyCode;
    
    //是否集中接货(Y:是)
    private String pickupCentralized;
  
    //开单时间
    private Date billTime;
    
    //返款帐户开户账户
    private String accountCode;
    
    //返款帐户开户名称
    private String accountName;
    
    //返款帐户开户银行
    private String accountBank;
    
    //始发统一结算
    private String origUnifiedSettlement;
    
    //到达统一结算
    private String destUnifiedSettlement;
    
    //出发统一结算合同部门标杆编码
    private String origContractUnifiedCode;
    
    //到达统一结算合同部门标杆编码
    private String destContractUnifiedCode;
    
    //出发催款部门标杆编码
    private String origUnifiedDuningCode;
    
    //到达催款部门标杆编码
    private String destUnifiedDuningCode;
    
    //开户行编码
    private String bankHQCode;
    
    //开户行名称
    private String bankHQName;
    
    //省份编码
    private String provinceCode;
    
    //省份名称
    private String provinceName;
    
    //城市编码
    private String cityCode;
    
    //城市名称
    private String cityName;
    
    //支行编码（行号）
    private String bankBranchCode;
    
    //支行名称
    private String bankBranchName;
    
    //对公对私标志  对公:PUBLIC_ACCOUNT 对私:PRIVATE_ACCOUNT
    private String publicPrivateFlag;
    
    //收款人与发货人关系
    private String payeeRelationship;
    
    //收款人手机号码
    private String payeePhone;
    
    //结算专用：是否需要查询快递点部（自有的、自有的非外场  Y:代理网点，需要查询  N:自有网点，无需查询
    private String isSelfStation;
    
    //POS串号
    private String posSerialNum;
    
    //银行交易流水号
    private String batchNo;

    //快递员工号
    private String expressEmpCode;
    
    //快递员姓名
    private String expressEmpName;
    
    //PDA开单时间
    private Date pdaBillTime;
    
    //是否转寄
    private String isRedirect;
    
    //中转费（转寄费）
    private BigDecimal isRedirectFee;
    
    //预付现金
    private BigDecimal prePayAmountCH;
    
    //预付月结
    private BigDecimal prePayAmountCT;

    /**
     * 是否裹裹运单<br/>
     * 结算根据标记判断是否是裹裹运单，如果isWrap 字段值是“Y”，
     * 则需校验暂存表是否存在支付信息，
     * 存在支付信息，就自动核销始发应收单，
     * 不存在支付信息，就不做自动核销操作；
     * 如果isWrap字段值为“N”,则不校验暂存表中的支付信息；
     * @author 326181
     * @date 2016-10-03 9:50:45
     * 
     */
    private String isWrap;

    //转寄新运单号
    private String newWaybillNo;
    
    //转寄付款方式
    private String redirectPaidMethod;
    
    //到达部门是否合伙人
    private String isPtp;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	public String getOldWaybillNo() {
		return oldWaybillNo;
	}

	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}

	public String getOldProductCode() {
		return oldProductCode;
	}

	public void setOldProductCode(String oldProductCode) {
		this.oldProductCode = oldProductCode;
	}

	public String getOldCreateOrgCode() {
		return oldCreateOrgCode;
	}

	public void setOldCreateOrgCode(String oldCreateOrgCode) {
		this.oldCreateOrgCode = oldCreateOrgCode;
	}

	public String getOldReceiveOrgCode() {
		return oldReceiveOrgCode;
	}

	public void setOldReceiveOrgCode(String oldReceiveOrgCode) {
		this.oldReceiveOrgCode = oldReceiveOrgCode;
	}

	public String getOldPaidMethod() {
		return oldPaidMethod;
	}

	public void setOldPaidMethod(String oldPaidMethod) {
		this.oldPaidMethod = oldPaidMethod;
	}

	public BigDecimal getOldPrePayAmount() {
		return oldPrePayAmount;
	}

	public void setOldPrePayAmount(BigDecimal oldPrePayAmount) {
		this.oldPrePayAmount = oldPrePayAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateUserDeptName() {
		return createUserDeptName;
	}

	public void setCreateUserDeptName(String createUserDeptName) {
		this.createUserDeptName = createUserDeptName;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public BigDecimal getBillWeight() {
		return billWeight;
	}

	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	public BigDecimal getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}

	public BigDecimal getCodFee() {
		return codFee;
	}

	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPickupCentralized() {
		return pickupCentralized;
	}

	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getOrigUnifiedSettlement() {
		return origUnifiedSettlement;
	}

	public void setOrigUnifiedSettlement(String origUnifiedSettlement) {
		this.origUnifiedSettlement = origUnifiedSettlement;
	}

	public String getDestUnifiedSettlement() {
		return destUnifiedSettlement;
	}

	public void setDestUnifiedSettlement(String destUnifiedSettlement) {
		this.destUnifiedSettlement = destUnifiedSettlement;
	}

	public String getOrigContractUnifiedCode() {
		return origContractUnifiedCode;
	}

	public void setOrigContractUnifiedCode(String origContractUnifiedCode) {
		this.origContractUnifiedCode = origContractUnifiedCode;
	}

	public String getDestContractUnifiedCode() {
		return destContractUnifiedCode;
	}

	public void setDestContractUnifiedCode(String destContractUnifiedCode) {
		this.destContractUnifiedCode = destContractUnifiedCode;
	}

	public String getOrigUnifiedDuningCode() {
		return origUnifiedDuningCode;
	}

	public void setOrigUnifiedDuningCode(String origUnifiedDuningCode) {
		this.origUnifiedDuningCode = origUnifiedDuningCode;
	}

	public String getDestUnifiedDuningCode() {
		return destUnifiedDuningCode;
	}

	public void setDestUnifiedDuningCode(String destUnifiedDuningCode) {
		this.destUnifiedDuningCode = destUnifiedDuningCode;
	}

	public String getBankHQCode() {
		return bankHQCode;
	}

	public void setBankHQCode(String bankHQCode) {
		this.bankHQCode = bankHQCode;
	}

	public String getBankHQName() {
		return bankHQName;
	}

	public void setBankHQName(String bankHQName) {
		this.bankHQName = bankHQName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	public String getPayeeRelationship() {
		return payeeRelationship;
	}

	public void setPayeeRelationship(String payeeRelationship) {
		this.payeeRelationship = payeeRelationship;
	}

	public String getPayeePhone() {
		return payeePhone;
	}

	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	public String getIsSelfStation() {
		return isSelfStation;
	}

	public void setIsSelfStation(String isSelfStation) {
		this.isSelfStation = isSelfStation;
	}

	public String getPosSerialNum() {
		return posSerialNum;
	}

	public void setPosSerialNum(String posSerialNum) {
		this.posSerialNum = posSerialNum;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getExpressEmpCode() {
		return expressEmpCode;
	}

	public void setExpressEmpCode(String expressEmpCode) {
		this.expressEmpCode = expressEmpCode;
	}

	public String getExpressEmpName() {
		return expressEmpName;
	}

	public void setExpressEmpName(String expressEmpName) {
		this.expressEmpName = expressEmpName;
	}

	public Date getPdaBillTime() {
		return pdaBillTime;
	}

	public void setPdaBillTime(Date pdaBillTime) {
		this.pdaBillTime = pdaBillTime;
	}

	public String getIsRedirect() {
		return isRedirect;
	}

	public void setIsRedirect(String isRedirect) {
		this.isRedirect = isRedirect;
	}

	public BigDecimal getIsRedirectFee() {
		return isRedirectFee;
	}

	public void setIsRedirectFee(BigDecimal isRedirectFee) {
		this.isRedirectFee = isRedirectFee;
	}

	public BigDecimal getPrePayAmountCH() {
		return prePayAmountCH;
	}

	public void setPrePayAmountCH(BigDecimal prePayAmountCH) {
		this.prePayAmountCH = prePayAmountCH;
	}

	public BigDecimal getPrePayAmountCT() {
		return prePayAmountCT;
	}

	public void setPrePayAmountCT(BigDecimal prePayAmountCT) {
		this.prePayAmountCT = prePayAmountCT;
	}

	public String getIsWrap() {
		return isWrap;
	}

	public void setIsWrap(String isWrap) {
		this.isWrap = isWrap;
	}

	public String getNewWaybillNo() {
		return newWaybillNo;
	}

	public void setNewWaybillNo(String newWaybillNo) {
		this.newWaybillNo = newWaybillNo;
	}

	public String getRedirectPaidMethod() {
		return redirectPaidMethod;
	}

	public void setRedirectPaidMethod(String redirectPaidMethod) {
		this.redirectPaidMethod = redirectPaidMethod;
	}

	public String getIsPtp() {
		return isPtp;
	}

	public void setIsPtp(String isPtp) {
		this.isPtp = isPtp;
	}

}
