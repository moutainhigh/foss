
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;

/**
 * 批量修改重量体积DTO（快递）
 * 
 * @author 136334-foss-bailei
 * @date 2015-1-27 下午1:57:27
 */
public class ExpBatchChangeWeightDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3470133321676495482L;

	//start 
		//装卸费
		private BigDecimal  servicefee;
		
		public BigDecimal getServicefee() {
			return servicefee;
		}

		public void setServicefee(BigDecimal servicefee) {
			this.servicefee = servicefee;
		}

		//end
	//更改状态
	private String changeStatus;
	
	//订单号
	private String orderNo;
	
	//运单号
	private String waybillNo;
	
	//更改重量后单票重
	private BigDecimal weightChanged;
	
	//更改体积后单票体积
	private BigDecimal volumeChanged;
	
	//更改重量后的公布价运费
	private BigDecimal transportFeeChanged;
	
	//更改重量后的产品性质
	private String productCodeChanged;
	
	//失败原因
	private String failReason;
	
	//导入时间
	private Date importTime;
	
	//导入开始时间
	private Date importStartTime;
	
	//导入结束时间
	private Date importEndTime;
	
	//发货客户编码
	private String deliverCustomerCode;

    //更改备注
    private String changeNote;
    
    //子母件信息
    private List<WaybillRelateDetailEntity> waybillRelateDetailEntityList;
	
	public String getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getWeightChanged() {
		return weightChanged;
	}

	public void setWeightChanged(BigDecimal weightChanged) {
		this.weightChanged = weightChanged;
	}

	public BigDecimal getVolumeChanged() {
		return volumeChanged;
	}

	public void setVolumeChanged(BigDecimal volumeChanged) {
		this.volumeChanged = volumeChanged;
	}

	public BigDecimal getTransportFeeChanged() {
		return transportFeeChanged;
	}

	public void setTransportFeeChanged(BigDecimal transportFeeChanged) {
		this.transportFeeChanged = transportFeeChanged;
	}

	public String getProductCodeChanged() {
		return productCodeChanged;
	}

	public void setProductCodeChanged(String productCodeChanged) {
		this.productCodeChanged = productCodeChanged;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Date getImportStartTime() {
		return importStartTime;
	}

	public void setImportStartTime(Date importStartTime) {
		this.importStartTime = importStartTime;
	}

	public Date getImportEndTime() {
		return importEndTime;
	}

	public void setImportEndTime(Date importEndTime) {
		this.importEndTime = importEndTime;
	}

	public String getDeliverCustomerCode() {
		return deliverCustomerCode;
	}

	public void setDeliverCustomerCode(String deliverCustomerCode) {
		this.deliverCustomerCode = deliverCustomerCode;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}

	public List<WaybillRelateDetailEntity> getWaybillRelateDetailEntityList() {
		return waybillRelateDetailEntityList;
	}

	public void setWaybillRelateDetailEntityList(
			List<WaybillRelateDetailEntity> waybillRelateDetailEntityList) {
		this.waybillRelateDetailEntityList = waybillRelateDetailEntityList;
	}
	
	
}