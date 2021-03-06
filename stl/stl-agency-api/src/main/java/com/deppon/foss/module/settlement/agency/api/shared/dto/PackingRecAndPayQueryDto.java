package com.deppon.foss.module.settlement.agency.api.shared.dto;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;

import java.util.Date;
import java.util.List;

/**
 * 包装其他应收应付查询 dto <p style="display:none">modifyRecord</p> <p style="display:none">version:V1.0,author:105762,date:2014-5-16 下午3:09:10,content:TODO</p>
 * 
 * @author 105762
 * @date 2014-5-16 下午3:09:10
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayQueryDto {
	/**
	 * 开始记账日期
	 */
	private Date beginAccountDate;
	/**
	 * 结束记账日期
	 */
	private Date endAccountDate;
	/**
	 * 审批状态
	 */
	private String status;
	/**
	 * 大区
	 */
	private String bigArea;
	/**
	 * 小区
	 */
	private String smallArea;
	/**
	 * 营业部
	 */
	private String department;
	/**
	 * 客户
	 */
	private String consumer;
	/**
	 * 查询方式
	 */
	private String queryType;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 当前操作人工号
	 */
	private String empCode;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 运单号
	 */
	private String[] waybillNos;
	/**
	 * 单号
	 */
	private String[] billNos;
	/**
	 * 营业部集合
	 */
	private List<String> depts;
	/**
	 * 页数
	 */
	private int start;
	/**
	 * 页面条数
	 */
	private int limit;

    /**
     * 包装其他应收单单据子类型
     */
    public static final String REC_BILL_TYPE = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE;

    /**
     * 包装其他应付单单据子类型
     */
    public static final String PAY_BILL_TYPE = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE;

	/**
	  * @return  the beginAccountDate
	 */
	public Date getBeginAccountDate() {
		return beginAccountDate;
	}

	/**
	 * @param beginAccountDate the beginAccountDate to set
	 */
	public void setBeginAccountDate(Date beginAccountDate) {
		this.beginAccountDate = beginAccountDate;
	}

	/**
	  * @return  the endAccountDate
	 */
	public Date getEndAccountDate() {
		return endAccountDate;
	}

	/**
	 * @param endAccountDate the endAccountDate to set
	 */
	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
	}

	/**
	  * @return  the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	  * @return  the bigArea
	 */
	public String getBigArea() {
		return bigArea;
	}

	/**
	 * @param bigArea the bigArea to set
	 */
	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}

	/**
	  * @return  the smallArea
	 */
	public String getSmallArea() {
		return smallArea;
	}

	/**
	 * @param smallArea the smallArea to set
	 */
	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
	}

	/**
	  * @return  the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	  * @return  the consumer
	 */
	public String getConsumer() {
		return consumer;
	}

	/**
	 * @param consumer the consumer to set
	 */
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	/**
	  * @return  the queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	  * @return  the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	  * @return  the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	  * @return  the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	  * @return  the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	  * @return  the waybillNos
	 */
	public String[] getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos the waybillNos to set
	 */
	public void setWaybillNos(String[] waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	  * @return  the billNos
	 */
	public String[] getBillNos() {
		return billNos;
	}

	/**
	 * @param billNos the billNos to set
	 */
	public void setBillNos(String[] billNos) {
		this.billNos = billNos;
	}

	/**
	  * @return  the depts
	 */
	public List<String> getDepts() {
		return depts;
	}

	/**
	 * @param depts the depts to set
	 */
	public void setDepts(List<String> depts) {
		this.depts = depts;
	}

	/**
	  * @return  the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	  * @return  the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

}