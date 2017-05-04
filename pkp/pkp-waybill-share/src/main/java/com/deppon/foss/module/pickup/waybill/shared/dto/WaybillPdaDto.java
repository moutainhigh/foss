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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillPdaDto.java
 * 
 * FILE NAME        	: WaybillPdaDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;

// TODO: Auto-generated Javadoc
/**
 * 
 * Pda开单信息
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-gengzhe,date:2012-12-19 下午8:18:15,content:</p>
 * @author foss-gengzhe
 * @date 2012-12-19 下午8:18:15
 * @since
 * @version
 */
public class WaybillPdaDto implements Serializable{

	/**
	 * 序列号标识
	 */
	private static final long serialVersionUID = -6894903743731583294L;
	
	/**
	 * 创建人员，司机
	 */
	private String createUserCode;
	
	/**
	 * 司机所在车队部门
	 */
	private String billOrgCode;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 状态
	 */
	private String active;
	
	/**
	 * waybillPendingId(记录补录历史必填)
	 */
	private String waybillPendingId;
	
	/**
	 * operateTime（记录补录历史必填）
	 */
	private Date operateTime;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 出发部门
	 */
	private String startOrg;
	
	/**
	 * 订单(转车任务)号
	 */
	private String orderNo;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 重量（单位：千克）
	 */
	private BigDecimal goodsWeightTotal;
	
	/**
	 * 体积(单位：立方米)
	 */
	private BigDecimal goodsVolumeTotal;
	
	/**
	 * 代打木架体积(单位：立方米)
	 */
	private BigDecimal woodVolume;
	
	/**
	 * 木架尺寸(单位：cm*cm*cm)
	 */
	private String woodSize;
	
	/**
	 * 代打木箱体积(单位：立方米)
	 */
	private BigDecimal woodBoxVolume;
	
	/**
	 * 代打木箱尺寸(单位：cm*cm*cm)
	 */
	private String woodBoxSize;
	
	/**
	 * 件数
	 */
	private Integer goodsQty;
	
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
	 * 其它
	 */
	private String otherPackageType;
	
	/**
	 * 货物类型
	 */
	private String goodsTypeCode;
	
	/**
	 * 付款方式
	 */
	private String paidMethod;
	
	/**
	 * 是否打木架
	 */
	private String isWood;
	
	/**
	 * 增值服务项
	 */
	private List<ValueAddServiceDto> valueAddServiceDtoList;
	
	/**
	 * 开单人工号
	 */
	private String billUserNo;
	
	/**
	 * PDA设备号
	 */
	private String pdaNo;
	
	/**
	 * 车牌号
	 */
	private String licensePlateNum;
	
	/**
	 * 开单时间
	 */
	private Date billStart;
	
	/**
	 * 优惠券编号
	 */
	private String discountNo;
	
	/**
	 * 优惠金额
	 */
	private BigDecimal discountAmount;
	
	/**
	 * 实收运费
	 */
	private BigDecimal actualFee;
	
	/**
	 * 应收运费,总运费
	 */
	private BigDecimal amount;
	
	/**
	 * 返单类别
	 */
	private String returnBillType;
	
	/**
	 * 退款类型
	 */
	private String refundType;
	
	/**
	 * PDA选择的营销活动
	 */
	MarkActivitiesQueryConditionDto activeDto;

	public MarkActivitiesQueryConditionDto getActiveDto() {
		return activeDto;
	}

	public void setActiveDto(MarkActivitiesQueryConditionDto activeDto) {
		this.activeDto = activeDto;
	}
	
	/**
	 * DMANA-4296
	 */
    //收货省
    private String receiveCustomerProvCode;
	
	// 收货市
    private String receiveCustomerCityCode;

    // 收货区
    private String receiveCustomerDistCode;	
	
    public String getReceiveCustomerProvCode() {
    	return receiveCustomerProvCode;
    }
    
    public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
    	this.receiveCustomerProvCode = receiveCustomerProvCode;
    }
    
    public String getReceiveCustomerCityCode() {
    	return receiveCustomerCityCode;
    }
    
    public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
    	this.receiveCustomerCityCode = receiveCustomerCityCode;
    }
    
    public String getReceiveCustomerDistCode() {
    	return receiveCustomerDistCode;
    }
    
    public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
    	this.receiveCustomerDistCode = receiveCustomerDistCode;
    }    



	/**
	 * Gets the returnBillType.
	 * 
	 * @return the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}


	/**
	 * Sets the returnBillType.
	 * 
	 * @param returnBillType the returnBillType to see
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}


	/**
	 * Gets the refundType.
	 * 
	 * @return the refundType
	 */
	public String getRefundType() {
		return refundType;
	}


	/**
	 * Sets the refundType.
	 * 
	 * @param refundType the refundType to see
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}


	/**
	 * Gets the waybill pending id.
	 *
	 * @return the waybill pending id
	 */
	public String getWaybillPendingId() {
		return waybillPendingId;
	}

	
	/**
	 * Sets the waybill pending id.
	 *
	 * @param waybillPendingId the new waybill pending id
	 */
	public void setWaybillPendingId(String waybillPendingId) {
		this.waybillPendingId = waybillPendingId;
	}


	/**
	 * Gets the goods weight total.
	 *
	 * @return the goods weight total
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}


	/**
	 * Sets the goods weight total.
	 *
	 * @param goodsWeightTotal the new goods weight total
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}


	/**
	 * Gets the goods volume total.
	 *
	 * @return the goods volume total
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	
	/**
	 * Sets the goods volume total.
	 *
	 * @param goodsVolumeTotal the new goods volume total
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	
	/**
	 * Gets the wood volume.
	 *
	 * @return the wood volume
	 */
	public BigDecimal getWoodVolume() {
		return woodVolume;
	}


	/**
	 * Sets the wood volume.
	 *
	 * @param woodVolume the new wood volume
	 */
	public void setWoodVolume(BigDecimal woodVolume) {
		this.woodVolume = woodVolume;
	}

	
	/**
	 * Gets the wood size.
	 *
	 * @return the wood size
	 */
	public String getWoodSize() {
		return woodSize;
	}

	
	/**
	 * Sets the wood size.
	 *
	 * @param woodSize the new wood size
	 */
	public void setWoodSize(String woodSize) {
		this.woodSize = woodSize;
	}

	
	/**
	 * Gets the goods qty.
	 *
	 * @return the goods qty
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	
	/**
	 * Sets the goods qty.
	 *
	 * @param goodsQty the new goods qty
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	
	/**
	 * Gets the bill start.
	 *
	 * @return the bill start
	 */
	public Date getBillStart() {
		return billStart;
	}

	
	/**
	 * Sets the bill start.
	 *
	 * @param billStart the new bill start
	 */
	public void setBillStart(Date billStart) {
		this.billStart = billStart;
	}

	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	/**
	 * Ge actual fee.
	 *
	 * @return the big decimal
	 */
	public BigDecimal geActualFee() {
		return actualFee;
	}

	
	/**
	 * Sets the actual fee.
	 *
	 * @param actualFee the new actual fee
	 */
	public void setActualFee(BigDecimal actualFee) {
		this.actualFee = actualFee;
	}


	/**
	 * Gets the waybill no.
	 *
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * Gets the start org.
	 *
	 * @return the start org
	 */
	public String getStartOrg() {
		return startOrg;
	}

	
	/**
	 * Sets the start org.
	 *
	 * @param startOrg the new start org
	 */
	public void setStartOrg(String startOrg) {
		this.startOrg = startOrg;
	}

	
	/**
	 * Gets the order no.
	 *
	 * @return the order no
	 */
	public String getOrderNo() {
		return orderNo;
	}

	
	/**
	 * Sets the order no.
	 *
	 * @param orderNo the new order no
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	/**
	 * Gets the receive method.
	 *
	 * @return the receive method
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	
	/**
	 * Sets the receive method.
	 *
	 * @param receiveMethod the new receive method
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	
	/**
	 * Gets the target org code.
	 *
	 * @return the target org code
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	
	/**
	 * Sets the target org code.
	 *
	 * @param targetOrgCode the new target org code
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	
	/**
	 * Gets the product code.
	 *
	 * @return the product code
	 */
	public String getProductCode() {
		return productCode;
	}

	
	/**
	 * Sets the product code.
	 *
	 * @param productCode the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	/**
	 * Gets the goods type code.
	 *
	 * @return the goods type code
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	
	/**
	 * Sets the goods type code.
	 *
	 * @param goodsTypeCode the new goods type code
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	
	/**
	 * Gets the paid method.
	 *
	 * @return the paid method
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	
	/**
	 * Sets the paid method.
	 *
	 * @param paidMethod the new paid method
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	
	/**
	 * Gets the is wood.
	 *
	 * @return the is wood
	 */
	public String getIsWood() {
		return isWood;
	}

	
	/**
	 * Sets the is wood.
	 *
	 * @param isWood the new is wood
	 */
	public void setIsWood(String isWood) {
		this.isWood = isWood;
	}

	
	/**
	 * Gets the bill user no.
	 *
	 * @return the bill user no
	 */
	public String getBillUserNo() {
		return billUserNo;
	}

	
	/**
	 * Sets the bill user no.
	 *
	 * @param billUserNo the new bill user no
	 */
	public void setBillUserNo(String billUserNo) {
		this.billUserNo = billUserNo;
	}

	
	/**
	 * Gets the pda no.
	 *
	 * @return the pda no
	 */
	public String getPdaNo() {
		return pdaNo;
	}

	
	/**
	 * Sets the pda no.
	 *
	 * @param pdaNo the new pda no
	 */
	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}

	
	/**
	 * Gets the license plate num.
	 *
	 * @return the license plate num
	 */
	public String getLicensePlateNum() {
		return licensePlateNum;
	}

	
	/**
	 * Sets the license plate num.
	 *
	 * @param licensePlateNum the new license plate num
	 */
	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}

	
	/**
	 * Gets the operate time.
	 *
	 * @return the operate time
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	
	/**
	 * Sets the operate time.
	 *
	 * @param operateTime the new operate time
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


	/**
	 * Gets the other package type.
	 *
	 * @return the other package type
	 */
	public String getOtherPackageType() {
		return otherPackageType;
	}

	
	/**
	 * Sets the other package type.
	 *
	 * @param otherPackageType the new other package type
	 */
	public void setOtherPackageType(String otherPackageType) {
		this.otherPackageType = otherPackageType;
	}

	
	/**
	 * Gets the discount no.
	 *
	 * @return the discount no
	 */
	public String getDiscountNo() {
		return discountNo;
	}

	
	/**
	 * Sets the discount no.
	 *
	 * @param discountNo the new discount no
	 */
	public void setDiscountNo(String discountNo) {
		this.discountNo = discountNo;
	}

	
	/**
	 * Gets the discount amount.
	 *
	 * @return the discount amount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	
	/**
	 * Sets the discount amount.
	 *
	 * @param discountAmount the new discount amount
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	
	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * Gets the creates the user code.
	 *
	 * @return the creates the user code
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	
	/**
	 * Sets the creates the user code.
	 *
	 * @param createUserCode the new creates the user code
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	
	/**
	 * Gets the bill org code.
	 *
	 * @return the bill org code
	 */
	public String getBillOrgCode() {
		return billOrgCode;
	}

	
	/**
	 * Sets the bill org code.
	 *
	 * @param billOrgCode the new bill org code
	 */
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}

	
	/**
	 * Gets the creates the time.
	 *
	 * @return the creates the time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the new creates the time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	/**
	 * Gets the paper.
	 *
	 * @return the paper
	 */
	public Integer getPaper() {
		return paper;
	}

	
	/**
	 * Sets the paper.
	 *
	 * @param paper the new paper
	 */
	public void setPaper(Integer paper) {
		this.paper = paper;
	}

	
	/**
	 * Gets the wood.
	 *
	 * @return the wood
	 */
	public Integer getWood() {
		return wood;
	}

	
	/**
	 * Sets the wood.
	 *
	 * @param wood the new wood
	 */
	public void setWood(Integer wood) {
		this.wood = wood;
	}

	
	/**
	 * Gets the fibre.
	 *
	 * @return the fibre
	 */
	public Integer getFibre() {
		return fibre;
	}

	
	/**
	 * Sets the fibre.
	 *
	 * @param fibre the new fibre
	 */
	public void setFibre(Integer fibre) {
		this.fibre = fibre;
	}

	
	/**
	 * Gets the salver.
	 *
	 * @return the salver
	 */
	public Integer getSalver() {
		return salver;
	}

	
	/**
	 * Sets the salver.
	 *
	 * @param salver the new salver
	 */
	public void setSalver(Integer salver) {
		this.salver = salver;
	}

	
	/**
	 * Gets the membrane.
	 *
	 * @return the membrane
	 */
	public Integer getMembrane() {
		return membrane;
	}

	
	/**
	 * Sets the membrane.
	 *
	 * @param membrane the new membrane
	 */
	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}


	
	/**
	 * Gets the wood box volume.
	 *
	 * @return the wood box volume
	 */
	public BigDecimal getWoodBoxVolume() {
		return woodBoxVolume;
	}


	
	/**
	 * Sets the wood box volume.
	 *
	 * @param woodBoxVolume the new wood box volume
	 */
	public void setWoodBoxVolume(BigDecimal woodBoxVolume) {
		this.woodBoxVolume = woodBoxVolume;
	}


	
	/**
	 * Gets the wood box size.
	 *
	 * @return the wood box size
	 */
	public String getWoodBoxSize() {
		return woodBoxSize;
	}


	
	/**
	 * Sets the wood box size.
	 *
	 * @param woodBoxSize the new wood box size
	 */
	public void setWoodBoxSize(String woodBoxSize) {
		this.woodBoxSize = woodBoxSize;
	}


	
	/**
	 * Gets the value add service dto list.
	 *
	 * @return the value add service dto list
	 */
	public List<ValueAddServiceDto> getValueAddServiceDtoList() {
		return valueAddServiceDtoList;
	}


	
	/**
	 * Sets the value add service dto list.
	 *
	 * @param valueAddServiceDtoList the new value add service dto list
	 */
	public void setValueAddServiceDtoList(
			List<ValueAddServiceDto> valueAddServiceDtoList) {
		this.valueAddServiceDtoList = valueAddServiceDtoList;
	}

	
	
}