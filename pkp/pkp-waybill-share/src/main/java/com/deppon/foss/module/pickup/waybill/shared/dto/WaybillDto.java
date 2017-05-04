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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillDto.java
 * 
 * FILE NAME        	: WaybillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.IntelligenceBillTimeGather;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;

/**
 * 
 * 运单实体
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-31 上午9:26:47
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-31 上午9:26:47
 * @since
 * @version
 */
public class WaybillDto implements Serializable{

    private static final long serialVersionUID = 1055897213230070574L;

	// 当前用户信息
    private CurrentInfo currentInfo;

    // 运单基础
    private WaybillEntity waybillEntity;

    // 运单费用明细
    private List<WaybillChargeDtlEntity> waybillChargeDtlEntity;

    // 运单折扣明细
    private List<WaybillDisDtlEntity> waybillDisDtlEntity;

    // 运单付款明细
    private List<WaybillPaymentEntity> waybillPaymentEntity;

    // 代打木架信息
    private WoodenRequirementsEntity woodenRequirementsEntity;
    
    //运单冗余信息
    private ActualFreightEntity actualFreightEntity;
    
	 //特殊增值服务信息
    private List<InstallationEntity> specialValueAddedServiceEntity;
	
    /**
     * 子母件关系
     */
    private List<WaybillRelateDetailEntity> waybillRelateDetailEntityList;
    //子母件运单信息
    private List<WaybillEntity> waybillEntityList;    //收货人地址
    private AddressFieldDto receiveCustomerAreaDto;
    
    //降價返券需求：獲取當前版本的價格計算折前運費，需要在提交的時候再次計算運費，故保存 产品价格主参数
    private GuiQueryBillCalculateDto productPriceDtoCollection;
    
    //产品价格主参数
    public GuiQueryBillCalculateDto getProductPriceDtoCollection() {
		return productPriceDtoCollection;
	}
    
    //产品价格主参数
	public void setProductPriceDtoCollection(
			GuiQueryBillCalculateDto productPriceDtoCollection) {
		this.productPriceDtoCollection = productPriceDtoCollection;
	}

	/**
     * 开户银行信息
     */
    private CusAccountEntity openBank;
    
    /**
     * 原运单号
     */
    private String oldWaybillNo;
    
    //------以下是配合查询页面,增加的属性 
    
    // 运单号
    private String mixNo;
    
    // 订单号
    private String orderNo;
    
    //开单开始时间
    private Date billStartTime;
    
    //开单结束时间
    private Date billEndTime;
    
    //Foss提交开始时间
    private Date createStartTime;
    
    //Foss提交结束时间
    private Date createEndTime;
    
    //优惠卷
    private CouponInfoDto couponInfoDto;
    
    // PDA开单收货部门
    private String receiveOrgCode;
    
    //liyongfei 新增运单类型
    private String waybillType;
    
    private List<LabeledGoodEntity> labeledGoodEntities;
    //是否快递
    private String isExpress;
    
    //打印类型
    private String printWaybillType;
    
    /**
   	 * 是否GUI提交方式
   	 */
   	private String isGuiSubmit;//是否GUI提交方式
   	
   	/**
   	 * GUI标题来源  图片开单、营业部开单、集中开单
   	 */
   	private String guiTitleName;//GUI标题
  
	/**
	 * 优惠类型
	 * @return
	 */
	private String specialOffer;
	
	//是否为更改单,Y为更改单，N或空为开单；
	private String isChangeWaybill;
	
	/**
	 * @项目：智能开单项目
	 * @功能：保存IntelligenceBillTimeGather类
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19下午18:02
	 */
	private IntelligenceBillTimeGather ibtg;
	
	public String getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(String specialOffer) {
		this.specialOffer = specialOffer;
	}
	
    public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the couponInfoDto
	 */
	public CouponInfoDto getCouponInfoDto() {
		return couponInfoDto;
	}

	/**
	 * @param couponInfoDto the couponInfoDto to set
	 */
	public void setCouponInfoDto(CouponInfoDto couponInfoDto) {
		this.couponInfoDto = couponInfoDto;
	}

	public ActualFreightEntity getActualFreightEntity() {
		return actualFreightEntity;
	}

	public void setActualFreightEntity(ActualFreightEntity actualFreightEntity) {
		this.actualFreightEntity = actualFreightEntity;
	}

	public WaybillEntity getWaybillEntity() {
    	return waybillEntity;
    }

    public void setWaybillEntity(WaybillEntity waybillEntity) {
    	this.waybillEntity = waybillEntity;
    }

    public List<WaybillChargeDtlEntity> getWaybillChargeDtlEntity() {
    	return waybillChargeDtlEntity;
    }

    public void setWaybillChargeDtlEntity(List<WaybillChargeDtlEntity> waybillChargeDtlEntity) {
    	this.waybillChargeDtlEntity = waybillChargeDtlEntity;
    }

    public List<WaybillDisDtlEntity> getWaybillDisDtlEntity() {
    	return waybillDisDtlEntity;
    }

    public void setWaybillDisDtlEntity(List<WaybillDisDtlEntity> waybillDisDtlEntity) {
    	this.waybillDisDtlEntity = waybillDisDtlEntity;
    }

    public List<WaybillPaymentEntity> getWaybillPaymentEntity() {
    	return waybillPaymentEntity;
    }

    public void setWaybillPaymentEntity(List<WaybillPaymentEntity> waybillPaymentEntity) {
    	this.waybillPaymentEntity = waybillPaymentEntity;
    }

    public WoodenRequirementsEntity getWoodenRequirementsEntity() {
    	return woodenRequirementsEntity;
    }

    public void setWoodenRequirementsEntity(WoodenRequirementsEntity woodenRequirementsEntity) {
    	this.woodenRequirementsEntity = woodenRequirementsEntity;
    }

    public CurrentInfo getCurrentInfo() {
    	return currentInfo;
    }

    public void setCurrentInfo(CurrentInfo currentInfo) {
    	this.currentInfo = currentInfo;
    }

	public String getMixNo() {
		return mixNo;
	}

	public void setMixNo(String mixNo) {
		this.mixNo = mixNo;
	}

	public Date getBillStartTime() {
		return billStartTime;
	}

	public void setBillStartTime(Date billStartTime) {
		this.billStartTime = billStartTime;
	}

	public Date getBillEndTime() {
		return billEndTime;
	}

	public void setBillEndTime(Date billEndTime) {
		this.billEndTime = billEndTime;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	/**
	 * 获取 开户银行信息
	 */
	public CusAccountEntity getOpenBank() {
		return openBank;
	}

	/**
	 * 设置 开户银行信息
	 */
	public void setOpenBank(CusAccountEntity openBank) {
		this.openBank = openBank;
	}

	
	/**
	 * @return the oldWaybillNo .
	 */
	public String getOldWaybillNo() {
		return oldWaybillNo;
	}

	
	/**
	 *@param oldWaybillNo the oldWaybillNo to set.
	 */
	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}

	//小件-------------------------------------------------
	//NEW
	//小件冗余信息
    private WaybillExpressEntity  waybillExpressEntity;
    
    //是否全部都签收
    private boolean allSigned;
    
    public boolean isAllSigned() {
		return allSigned;
	}

	public void setAllSigned(boolean allSigned) {
		this.allSigned = allSigned;
	}

	public WaybillExpressEntity getWaybillExpressEntity() {
  		return waybillExpressEntity;
  	}

  	public void setWaybillExpressEntity(
  			WaybillExpressEntity waybillExpressEntity) {
  		this.waybillExpressEntity = waybillExpressEntity;
  	}

	public List<LabeledGoodEntity> getLabeledGoodEntities() {
		return labeledGoodEntities;
	}

	public void setLabeledGoodEntities(List<LabeledGoodEntity> labeledGoodEntities) {
		this.labeledGoodEntities = labeledGoodEntities;

	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}


	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getIsChangeWaybill() {
		return isChangeWaybill;
	}

	public void setIsChangeWaybill(String isChangeWaybill) {
		this.isChangeWaybill = isChangeWaybill;
	}

	public AddressFieldDto getReceiveCustomerAreaDto() {
		return receiveCustomerAreaDto;
	}

	public void setReceiveCustomerAreaDto(AddressFieldDto receiveCustomerAreaDto) {
		this.receiveCustomerAreaDto = receiveCustomerAreaDto;
	}
	public List<InstallationEntity> getSpecialValueAddedServiceEntity() {
		return specialValueAddedServiceEntity;
	}

	public void setSpecialValueAddedServiceEntity(
			List<InstallationEntity> specialValueAddedServiceEntity) {
		this.specialValueAddedServiceEntity = specialValueAddedServiceEntity;
	}
	public List<WaybillRelateDetailEntity> getWaybillRelateDetailEntityList() {
		return waybillRelateDetailEntityList;
	}

	public void setWaybillRelateDetailEntityList(
			List<WaybillRelateDetailEntity> waybillRelateDetailEntityList) {
		this.waybillRelateDetailEntityList = waybillRelateDetailEntityList;
	}

	public List<WaybillEntity> getWaybillEntityList() {
		return waybillEntityList;
	}

	public void setWaybillEntityList(List<WaybillEntity> waybillEntityList) {
		this.waybillEntityList = waybillEntityList;
	}

	public String getIsGuiSubmit() {
		return isGuiSubmit;
	}

	public void setIsGuiSubmit(String isGuiSubmit) {
		this.isGuiSubmit = isGuiSubmit;
	}

	public String getGuiTitleName() {
		return guiTitleName;
	}

	public void setGuiTitleName(String guiTitleName) {
		this.guiTitleName = guiTitleName;
	}
	
	
	/**
	 * 存放修改前的费用
	 */
	private PtpWaybillDto ptpWaybillDto ;
	//是否为合伙人
	private boolean partnerBilling = false ;
	
	//合伙人实体
	private PartnersWaybillEntity partnersWaybillEntity ;
	
	public PtpWaybillDto getPtpWaybillDto() {
		return ptpWaybillDto;
	}

	public void setPtpWaybillDto(PtpWaybillDto ptpWaybillDto) {
		this.ptpWaybillDto = ptpWaybillDto;
	}

	public boolean isPartnerBilling() {
		return partnerBilling;
	}

	public void setPartnerBilling(boolean partnerBilling) {
		this.partnerBilling = partnerBilling;
	}

	public PartnersWaybillEntity getPartnersWaybillEntity() {
		return partnersWaybillEntity;
	}

	public void setPartnersWaybillEntity(PartnersWaybillEntity partnersWaybillEntity) {
		this.partnersWaybillEntity = partnersWaybillEntity;
	}

	public IntelligenceBillTimeGather getIbtg() {
		return ibtg;
	}

	public void setIbtg(IntelligenceBillTimeGather ibtg) {
		this.ibtg = ibtg;
	}

	public String getPrintWaybillType() {
		return printWaybillType;
	}

	public void setPrintWaybillType(String printWaybillType) {
		this.printWaybillType = printWaybillType;
	}

	/**
	 * 分拣开关
	 */

	private String SortingSwitch;

	public String getSortingSwitch() {
		return SortingSwitch;
	}

	public void setSortingSwitch(String sortingSwitch) {
		SortingSwitch = sortingSwitch;
	}

	/**
	 * author 306486
	 * 更改单分拣结果是否更改
	 */
	private String sortingResultIsChanged;

	public String getSortingResultIsChanged() {
		return sortingResultIsChanged;
	}

	public void setSortingResultIsChanged(String sortingResultIsChanged) {
		this.sortingResultIsChanged = sortingResultIsChanged;
	}
}