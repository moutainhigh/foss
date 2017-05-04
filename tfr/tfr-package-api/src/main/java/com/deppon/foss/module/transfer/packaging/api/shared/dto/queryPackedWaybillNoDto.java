package com.deppon.foss.module.transfer.packaging.api.shared.dto;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * @desc:包装运单dto
 * @author：foss-中转开发组-105795-wqh
 * @date:2014-06-07  
 * 
 * **/
public class queryPackedWaybillNoDto extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -860444194106146697L;

	//运单号
	private String waybillNo;
	
	//开单总体积
	private BigDecimal goodsTotalVolume;
	
	//开单部门code
	private String bingOrgCode;
	
	//开单部门名称
	private String bingOrgName;
	
	//打木架体积
	private BigDecimal frameVolume;
	
	//打木箱体积
	private BigDecimal boxVolume;
	
	//大木托个数
	private int maskNum;

	
	//set and get
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getGoodsTotalVolume() {
		return goodsTotalVolume;
	}

	public void setGoodsTotalVolume(BigDecimal goodsTotalVolume) {
		this.goodsTotalVolume = goodsTotalVolume;
	}

	public String getBingOrgCode() {
		return bingOrgCode;
	}

	public void setBingOrgCode(String bingOrgCode) {
		this.bingOrgCode = bingOrgCode;
	}

	public String getBingOrgName() {
		return bingOrgName;
	}

	public void setBingOrgName(String bingOrgName) {
		this.bingOrgName = bingOrgName;
	}

	public BigDecimal getFrameVolume() {
		return frameVolume;
	}

	public void setFrameVolume(BigDecimal frameVolume) {
		this.frameVolume = frameVolume;
	}

	public BigDecimal getBoxVolume() {
		return boxVolume;
	}

	public void setBoxVolume(BigDecimal boxVolume) {
		this.boxVolume = boxVolume;
	}

	public int getMaskNum() {
		return maskNum;
	}

	public void setMaskNum(int maskNum) {
		this.maskNum = maskNum;
	}
	
	
}
