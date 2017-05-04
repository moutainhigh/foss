package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;



/**
* @description 内部带货实体类
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年6月1日 上午8:50:48
*/
public class InnerPickupCurrDeptEntity extends BaseEntity{
	
	/**
	* @fields serialVersionUID
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午9:07:39
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	
	//库存部门
	private String orgName;
	
	//成功或失败的标识(整数类型，0、失败；1、成功)
	private int isSuccess;
	
	//失败原因
	private String reason;
	
	//部门code
	private String orgCode;
	
	//货物总件数
	private int goodsQtyTotal;
	
	//开单时间
	private Date billTime;
	
	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	//货物总体积
	private int goodsVolumeTotal;
	
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}



	public int getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(int goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
	
}
