package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;

/**
 * 
 * 代收货款确认VO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-6 上午8:46:51
 */
public class CODPayConfirmVo extends BillCODPayConfirmDto implements
		Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -257397797892451622L;

	/**
	 * 运单号String
	 */
	private String waybillNoString;

	/**
	 * 选择的代收货款状态
	 */
	private String selectStatus;

	/**
	 * 开始时间字段串
	 */
	private String exportStartTimeString;

	/**
	 * 结束时间字段串
	 */
	private String exportEndTimeString;

	/**
	 * 代收货款ID
	 */
	private String[] codEntityIds;

	/**
	 * 失败原因
	 */
	private String failNotes;

	/**
	 * 返回代收货款数据
	 */
	private List<CODDto> cods;
	
	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * @return waybillNoString
	 */
	public String getWaybillNoString() {
		return waybillNoString;
	}

	/**
	 * @param waybillNoString
	 */
	public void setWaybillNoString(String waybillNoString) {
		this.waybillNoString = waybillNoString;
	}

	/**
	 * @return selectStatus
	 */
	public String getSelectStatus() {
		return selectStatus;
	}

	/**
	 * @param selectStatus
	 */
	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}

	/**
	 * @return exportStartTimeString
	 */
	public String getExportStartTimeString() {
		return exportStartTimeString;
	}

	/**
	 * @param exportStartTimeString
	 */
	public void setExportStartTimeString(String exportStartTimeString) {
		this.exportStartTimeString = exportStartTimeString;
	}

	/**
	 * @return exportEndTimeString
	 */
	public String getExportEndTimeString() {
		return exportEndTimeString;
	}

	/**
	 * @param exportEndTimeString
	 */
	public void setExportEndTimeString(String exportEndTimeString) {
		this.exportEndTimeString = exportEndTimeString;
	}

	/**
	 * @return codEntityIds
	 */
	public String[] getCodEntityIds() {
		return codEntityIds;
	}

	/**
	 * @param codEntityIds
	 */
	public void setCodEntityIds(String[] codEntityIds) {
		this.codEntityIds = codEntityIds;
	}

	/**
	 * @return failNotes
	 */
	public String getFailNotes() {
		return failNotes;
	}

	/**
	 * @param failNotes
	 */
	public void setFailNotes(String failNotes) {
		this.failNotes = failNotes;
	}

	/**
	 * @return cods
	 */
	public List<CODDto> getCods() {
		return cods;
	}

	/**
	 * @param cods
	 */
	public void setCods(List<CODDto> cods) {
		this.cods = cods;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
