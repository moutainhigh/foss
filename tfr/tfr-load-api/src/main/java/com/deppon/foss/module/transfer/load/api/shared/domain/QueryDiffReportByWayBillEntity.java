package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

public class QueryDiffReportByWayBillEntity extends BaseEntity{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5410587505267098968L;

	/**少货备注&&退回原因备注**/
	private String notes;
	/**预计装车件数**/
	private BigDecimal planLoadQty;
	/**派送单号**/
	private String deliverBillNo;
	/**差异报告类型--多货/少货/退回 */
	private String gapType;
	
	/**
	 * 少货备注
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * 少货备注
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * 预计装车件数
	 * @return the planLoadQty
	 */
	public BigDecimal getPlanLoadQty() {
		return planLoadQty;
	}
	/**
	 * 预计装车件数
	 * @param planLoadQty the planLoadQty to set
	 */
	public void setPlanLoadQty(BigDecimal planLoadQty) {
		this.planLoadQty = planLoadQty;
	}
	/**
	 * 派送单号
	 * @return the deliverBillNo
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}
	/**
	 * 派送单号
	 * @param deliverBillNo the deliverBillNo to set
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}
	/**
	 * 差异报告类型
	 * @return
	 */
	public String getGapType() {
		return gapType;
	}
	
	/**
	 * 差异报告类型
	 * @param gapType
	 */
	public void setGapType(String gapType) {
		this.gapType = gapType;
	}
	

}
