package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;

/**
 * 定额发票登记VO.
 * @author 163576
 * @date 2014-7-11 9:03:39
 *
 */
public class InvoiceRegisterVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private List<String> waybillNOs;
	
	/**
	 * 小票号
	 */
	private List<String> otherRevenueNos;
	
	/**
	 * 现付总金额
	 */
	private BigDecimal curPayTotalAmount;

	/**
	 * 现付可开金额
	 */
	private BigDecimal curPayOpenAmount;
	
	/**
	 * 到付总金额
	 */
	private BigDecimal destPayTotalAmount;
	
	/**
	 * 到付可开金额
	 */
	private BigDecimal destPayOpenAmount;
	
	/**
	 * 小票总金额
	 */
	private BigDecimal noteTotalAmount;
	
	/**
	 * 小票可开金额
	 */
	private BigDecimal noteOpenAmount;

//	/**
//	 * 总金额 {@value #waybillTotalAmount} + {@value #noteTotalAmount}
//	 */
//	private BigDecimal totalAmount;
//	
//	/**
//	 * 总可开金额{@value #waybillOpenAmount} + {@value #noteOpenAmount}
//	 */
//	private BigDecimal openAmount;
	
	/**
	 * 本次开票金额
	 */
	private BigDecimal thisAmount;
	
	/**
	 * 无效运单号
	 */
	private List<String> unWaybillNOs;
	
	/**
	 * 无效小票号
	 */
	private List<String> unOtherRevenueNos;
	
	/**
	 * 出发或到达部门非登录部门运单号
	 */
	private List<String> unDeptWaybillNOs;

	/**
	 * 收入部门非登录部门小票号
	 */
	private List<String> unDeptOtherRevenueNos;
	
	/**
	 * 标示，表示成功或者失败
	 */
	private boolean success;
	
	/**
	 * 运单集合
	 */
	private transient List<WaybillEntity> waybillEntityList;
	
	/**
	 * 小票集合
	 */
	private transient List<OtherRevenueEntity> otherRevenueEntitieList;
	
	/**
	 * @return the waybillNOs
	 */
	public List<String> getWaybillNOs() {
		return waybillNOs;
	}

	/**
	 * @param waybillNOs the waybillNOs to set
	 */
	public void setWaybillNOs(List<String> waybillNOs) {
		this.waybillNOs = waybillNOs;
	}

	/**
	 * @return the otherRevenueNos
	 */
	public List<String> getOtherRevenueNos() {
		return otherRevenueNos;
	}

	/**
	 * @param otherRevenueNos the otherRevenueNos to set
	 */
	public void setOtherRevenueNos(List<String> otherRevenueNos) {
		this.otherRevenueNos = otherRevenueNos;
	}

//	/**
//	 * @return the totalAmount
//	 */
//	public BigDecimal getTotalAmount() {
//		return totalAmount;
//	}
//
//	/**
//	 * @param totalAmount the totalAmount to set
//	 */
//	public void setTotalAmount(BigDecimal totalAmount) {
//		this.totalAmount = totalAmount;
//	}
//
//	/**
//	 * @return the openAmount
//	 */
//	public BigDecimal getOpenAmount() {
//		return openAmount;
//	}
//
//	/**
//	 * @param openAmount the openAmount to set
//	 */
//	public void setOpenAmount(BigDecimal openAmount) {
//		this.openAmount = openAmount;
//	}

	/**
	 * @return the thisAmount
	 */
	public BigDecimal getThisAmount() {
		return thisAmount;
	}

	/**
	 * @param thisAmount the thisAmount to set
	 */
	public void setThisAmount(BigDecimal thisAmount) {
		this.thisAmount = thisAmount;
	}

	/**
	 * @return the unWaybillNOs
	 */
	public List<String> getUnWaybillNOs() {
		return unWaybillNOs;
	}

	/**
	 * @param unWaybillNOs the unWaybillNOs to set
	 */
	public void setUnWaybillNOs(List<String> unWaybillNOs) {
		this.unWaybillNOs = unWaybillNOs;
	}

	/**
	 * @return the unOtherRevenueNos
	 */
	public List<String> getUnOtherRevenueNos() {
		return unOtherRevenueNos;
	}

	/**
	 * @param unOtherRevenueNos the unOtherRevenueNos to set
	 */
	public void setUnOtherRevenueNos(List<String> unOtherRevenueNos) {
		this.unOtherRevenueNos = unOtherRevenueNos;
	}

	/**
	 * @return the waybillEntityList
	 */
	public List<WaybillEntity> getWaybillEntityList() {
		return waybillEntityList;
	}

	/**
	 * @param waybillEntityList the waybillEntityList to set
	 */
	public void setWaybillEntityList(List<WaybillEntity> waybillEntityList) {
		this.waybillEntityList = waybillEntityList;
	}

	/**
	 * @return the otherRevenueEntitieList
	 */
	public List<OtherRevenueEntity> getOtherRevenueEntitieList() {
		return otherRevenueEntitieList;
	}

	/**
	 * @param otherRevenueEntitieList the otherRevenueEntitieList to set
	 */
	public void setOtherRevenueEntitieList(
			List<OtherRevenueEntity> otherRevenueEntitieList) {
		this.otherRevenueEntitieList = otherRevenueEntitieList;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getUnDeptWaybillNOs() {
		return unDeptWaybillNOs;
	}

	public void setUnDeptWaybillNOs(List<String> unDeptWaybillNOs) {
		this.unDeptWaybillNOs = unDeptWaybillNOs;
	}

	public List<String> getUnDeptOtherRevenueNos() {
		return unDeptOtherRevenueNos;
	}

	public void setUnDeptOtherRevenueNos(List<String> unDeptOtherRevenueNos) {
		this.unDeptOtherRevenueNos = unDeptOtherRevenueNos;
	}

	public BigDecimal getCurPayTotalAmount() {
		return curPayTotalAmount;
	}

	public void setCurPayTotalAmount(BigDecimal curPayTotalAmount) {
		this.curPayTotalAmount = curPayTotalAmount;
	}

	public BigDecimal getCurPayOpenAmount() {
		return curPayOpenAmount;
	}

	public void setCurPayOpenAmount(BigDecimal curPayOpenAmount) {
		this.curPayOpenAmount = curPayOpenAmount;
	}

	public BigDecimal getDestPayTotalAmount() {
		return destPayTotalAmount;
	}

	public void setDestPayTotalAmount(BigDecimal destPayTotalAmount) {
		this.destPayTotalAmount = destPayTotalAmount;
	}

	public BigDecimal getDestPayOpenAmount() {
		return destPayOpenAmount;
	}

	public void setDestPayOpenAmount(BigDecimal destPayOpenAmount) {
		this.destPayOpenAmount = destPayOpenAmount;
	}

	public BigDecimal getNoteTotalAmount() {
		return noteTotalAmount;
	}

	public void setNoteTotalAmount(BigDecimal noteTotalAmount) {
		this.noteTotalAmount = noteTotalAmount;
	}

	public BigDecimal getNoteOpenAmount() {
		return noteOpenAmount;
	}

	public void setNoteOpenAmount(BigDecimal noteOpenAmount) {
		this.noteOpenAmount = noteOpenAmount;
	}
}
