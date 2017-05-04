package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ElectronicTicketEntity;
public class ElectronicTicketProcVo {

	/**
	 * NON_DE_CONFIRM
	 */
	private ElectronicTicketEntity electronicTicketDetail = new ElectronicTicketEntity();

	/**
	 * 
	 */
	private List<ElectronicTicketEntity> electronicTicketEntitys;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount = BigDecimal.ZERO;
	// 总条数
	private int totalCount;


	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ElectronicTicketEntity getElectronicTicketDetail() {
		return electronicTicketDetail;
	}

	public void setElectronicTicketDetail(
			ElectronicTicketEntity electronicTicketDetail) {
		this.electronicTicketDetail = electronicTicketDetail;
	}

	public List<ElectronicTicketEntity> getElectronicTicketEntitys() {
		return electronicTicketEntitys;
	}

	public void setElectronicTicketEntitys(
			List<ElectronicTicketEntity> electronicTicketEntitys) {
		this.electronicTicketEntitys = electronicTicketEntitys;
	}

	

}
