/**
 * OA差错上报DTO
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;



/**
 * OA差错上报DTO   --MANA-518

  * @ClassName: OaErrorReportDto

  * @Description: TODO

  * @author deppon-157229-zxy

  * @date 2014-1-21 下午4:06:29

  *
  *版本    时间    用户        内容
  *0002   20140308 zxy        修改：对于必填字段,如果空串则传"无"--DEFECT-1997附带修复此问题
 */
public class OaErrorReportDto {
	private static String NULL_STR = "无"; 
	/**
	 * 运单号
	 */
	private String waybillNo = "";
	
	/**
	 * 运输类型
	 */
	private String transportType = "";
	
	/**
	 * 返单类别
	 */
	private String returnBillType = "";
	
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName = "";
	
	/**
	 * 发货联系人
	 */
	private String deliveryCustomerContact = "";
	
	
	/**
	 * 运输性质
	 */
	private String productCode = "";
	
	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhones = "无";
	
	/**
	 * 提货方式
	 */
	private String receiveMethod = "";
	
	/**
	 * 储运事项
	 */
	private String transportationRemark = "";
	
	/**
	 * 更改前货物总重量
	 */
	private BigDecimal preGoodsWeightTotal = new BigDecimal("0");
	
	/**
	 * 更改前货物总体积
	 */
	private BigDecimal preGoodsVolumeTotal = new BigDecimal("0");
	
	/**
	 * 货物名称
	 */
	private String goodsName = "";

	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal = 0;
	
	/**
	 * 开单时间
	 */
	private Date billTime;
	
	/**
	 * 发货时间 
	 */
	private Date createTime;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode = "";
	
	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName = "";
	
	/**
	 * 收货联系人
	 */
	private String receiveCustomerContact = "";
	
	/**
	 * 收货部门名称
	 */
	private String receiveOrgName = "";
	
	/**
	 * 开单部门
	 */
	private String createOrgName="";
	
	/**
	 * 开单部门标杆编码
	 */
	private String unifiedCode="";
	
	/**
	 * 开单付款方式
	 */
	private String paidMethod = "";
	
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount = new BigDecimal("0");
	
	/**
	 * 货物包装
	 */
	private String goodsPackage = "";
	
	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee = new BigDecimal("0");
	
	/**
	 * 运费差额
	 */
	private BigDecimal diffTransportFee = new BigDecimal("0");
	
	/**
	 * 运费总额
	 */
	private BigDecimal totalFee = new BigDecimal("0");
	
	/**
	 * 发现人(来源标志)
	 */
	private String sourceFossSys = "";
	
	/**
	 * 是否超重  是/否
	 */
	private String isOverWeight = "";
	
	/**
	 * 是否超方  是/否
	 */
	private String isOverVolumn = "";
	
	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal = new BigDecimal("0");
	
	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal = new BigDecimal("0");
	
	/**
	 * 事业部名称
	 */
	private String busDepartmentName = "";
	
	/**
	 * 责任部门名称
	 */
	private String responsibilityDeptName = "";
	
	/**
	 * 事件经过
	 */
	private String changeItmes = "";
	
	/**
	 * 附件
	 */
	private String accessory="";
	
	/**
	 * 操作员工号
	 */
	private String userId="";
	
	 /**
     * 职位名称
     */
    private String title="";
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		if(StringUtils.isBlank(waybillNo))
			return;
		else
			this.waybillNo = waybillNo;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		if(StringUtils.isBlank(transportType))
			this.transportType = NULL_STR;
		else
			this.transportType = transportType;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		if(StringUtils.isBlank(returnBillType))
			this.returnBillType = NULL_STR;
		else
			this.returnBillType = returnBillType;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		if(StringUtils.isBlank(deliveryCustomerName))
			this.deliveryCustomerName = NULL_STR;
		else
			this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		if(StringUtils.isBlank(productCode))
			this.productCode = NULL_STR;
		else
			this.productCode = productCode;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		if(StringUtils.isBlank(receiveMethod))
			this.receiveMethod = NULL_STR;
		else
			this.receiveMethod = receiveMethod;
	}

	public String getTransportationRemark() {
		return transportationRemark;
	}

	public void setTransportationRemark(String transportationRemark) {
		if(StringUtils.isBlank(transportationRemark))
			return;
		else
			this.transportationRemark = transportationRemark;
	}

	public BigDecimal getPreGoodsWeightTotal() {
		return preGoodsWeightTotal;
	}

	public void setPreGoodsWeightTotal(BigDecimal preGoodsWeightTotal) {
		if(preGoodsWeightTotal == null)
			return;
		else
			this.preGoodsWeightTotal = preGoodsWeightTotal;
	}

	public BigDecimal getPreGoodsVolumeTotal() {
		return preGoodsVolumeTotal;
	}

	public void setPreGoodsVolumeTotal(BigDecimal preGoodsVolumeTotal) {
		if(preGoodsVolumeTotal == null)
			return;
		else
			this.preGoodsVolumeTotal = preGoodsVolumeTotal;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		if(StringUtils.isBlank(goodsName))
			this.goodsName = NULL_STR;
		else
			this.goodsName = goodsName;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		if(goodsQtyTotal == null)
			return;
		else
			this.goodsQtyTotal = goodsQtyTotal;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		if(billTime == null)
			return;
		else
			this.billTime = billTime;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		if(StringUtils.isBlank(targetOrgCode))
			return;
		else 
			this.targetOrgCode = targetOrgCode;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		if(StringUtils.isBlank(receiveCustomerName))
			this.receiveCustomerName = NULL_STR;
		else
			this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		if(StringUtils.isBlank(receiveOrgName))
			this.receiveOrgName = NULL_STR;
		else
			this.receiveOrgName = receiveOrgName;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		if(StringUtils.isBlank(paidMethod))
			this.paidMethod = NULL_STR;
		else
			this.paidMethod = paidMethod;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		if(insuranceAmount == null)
			return;
		else
			this.insuranceAmount = insuranceAmount;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		if(StringUtils.isBlank(goodsPackage))
			this.goodsPackage = NULL_STR;
		else
			this.goodsPackage = goodsPackage;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		if(transportFee == null)
			return;
		else
			this.transportFee = transportFee;
	}

	public BigDecimal getDiffTransportFee() {
		return diffTransportFee;
	}

	public void setDiffTransportFee(BigDecimal diffTransportFee) {
		if(diffTransportFee == null)
			return;
		else
			this.diffTransportFee = diffTransportFee;
	}

	public String getIsOverWeight() {
		return isOverWeight;
	}

	public void setIsOverWeight(String isOverWeight) {
		if(StringUtils.isBlank(isOverWeight))
			return;
		else
			this.isOverWeight = isOverWeight;
	}

	public String getIsOverVolumn() {
		return isOverVolumn;
	}

	public void setIsOverVolumn(String isOverVolumn) {
		if(StringUtils.isBlank(isOverVolumn))
			return;
		else
			this.isOverVolumn = isOverVolumn;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		if(goodsWeightTotal == null)
			return;
		else
			this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		if(goodsVolumeTotal == null)
			return;
		else
			this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getBusDepartmentName() {
		return busDepartmentName;
	}

	public void setBusDepartmentName(String busDepartmentName) {
		if(StringUtils.isBlank(busDepartmentName))
			this.busDepartmentName = NULL_STR;
		else
			this.busDepartmentName = busDepartmentName;
	}

	public String getResponsibilityDeptName() {
		return responsibilityDeptName;
	}

	public void setResponsibilityDeptName(String responsibilityDeptName) {
		if(StringUtils.isBlank(responsibilityDeptName))
			this.responsibilityDeptName = NULL_STR;
		else{
			this.responsibilityDeptName = responsibilityDeptName;
		}
	}

	public String getChangeItmes() {
		return changeItmes;
	}

	public void setChangeItmes(String changeItmes) {
		if(StringUtils.isBlank(changeItmes))
			this.changeItmes = NULL_STR;
		else
			this.changeItmes = changeItmes;
	}

	public String getSourceFossSys() {
		return sourceFossSys;
	}

	public void setSourceFossSys(String sourceFossSys) {
		if(StringUtils.isBlank(sourceFossSys))
			return;
		this.sourceFossSys = sourceFossSys;
	}

	public String getReceiveCustomerPhones() {
		return receiveCustomerPhones;
	}

	public void setReceiveCustomerPhones(String receiveCustomerPhones) {
		if(StringUtils.isBlank(receiveCustomerPhones))
			return;
		this.receiveCustomerPhones = receiveCustomerPhones;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
    
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	
	
		
}
