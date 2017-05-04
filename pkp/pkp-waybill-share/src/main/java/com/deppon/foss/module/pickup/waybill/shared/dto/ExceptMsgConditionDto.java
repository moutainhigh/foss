package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExceptMsgConditionDto implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 更改单ID
	 */
	private String waybillRfcId;

	/**
	 * 处理状态
	 */
	private String status;

	/**
	 * 更改单状态
	 */
	private String rfcStatus;
	
	/**
	 * 变更起草部门
	 */
	private Date darftOrgCode;
	/**
	 * 变更起草部门
	 */
	private String darftOrgName;

	/**
	 * 更改受理时间
	 */
	private Date todoOperateTimeBegin;

	/**
	 * 更改受理时间
	 */
	private Date todoOperateTimeEnd;
	
	/**
	 * 入库开始时间
	 */
	private Date todoRemainTimeBegin;
	
	/**
	 * 入库结束时间
	 */
	private Date todoRemainTimeEnd;
	
	/**
	 * 待办受理部门
	 */
	private String handleOrgCode;
	
	/**
	 * 派送部的最终配载部门
	 */
	private String lastLoadOrgCode;
	
	/**
	 * 是否打印
	 */
	private String isPrinted;
	
	/**
	 * 当前查询部门
	 */
	private String currentDept;
	
	/**
	 * 交接单编号
	 */
	private String handlerOverNo;
	
	/**
	 * 配载单编号
	 */
	private String loadNo;
	
	/**
	 * 当前库存部门Code
	 */
	private String inStockOrgCode;
	
	/**
	 * 当前库存部门Name
	 */
	private String inStockOrgName;
	/**
	 * 关键字
	 */
	private String keyWord;
	/**
	 * 运单号集合
	 */
	private List<String> waybillNoList;

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getInStockOrgCode() {
		return inStockOrgCode;
	}

	public void setInStockOrgCode(String inStockOrgCode) {
		this.inStockOrgCode = inStockOrgCode;
	}

	public String getInStockOrgName() {
		return inStockOrgName;
	}

	public void setInStockOrgName(String inStockOrgName) {
		this.inStockOrgName = inStockOrgName;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public Date getDarftOrgCode() {
		return darftOrgCode;
	}

	public void setDarftOrgCode(Date darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRfcStatus() {
		return rfcStatus;
	}

	public void setRfcStatus(String rfcStatus) {
		this.rfcStatus = rfcStatus;
	}

	public String getDarftOrgName() {
		return darftOrgName;
	}

	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	public String getHandleOrgCode() {
		return handleOrgCode;
	}

	public void setHandleOrgCode(String handleOrgCode) {
		this.handleOrgCode = handleOrgCode;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}

	public String getCurrentDept() {
		return currentDept;
	}

	public void setCurrentDept(String currentDept) {
		this.currentDept = currentDept;
	}

	public String getHandlerOverNo() {
		return handlerOverNo;
	}

	public void setHandlerOverNo(String handlerOverNo) {
		this.handlerOverNo = handlerOverNo;
	}

	public String getLoadNo() {
		return loadNo;
	}

	public void setLoadNo(String loadNo) {
		this.loadNo = loadNo;
	}

	public Date getTodoOperateTimeBegin() {
		return todoOperateTimeBegin;
	}

	public void setTodoOperateTimeBegin(Date todoOperateTimeBegin) {
		this.todoOperateTimeBegin = todoOperateTimeBegin;
	}

	public Date getTodoOperateTimeEnd() {
		return todoOperateTimeEnd;
	}

	public void setTodoOperateTimeEnd(Date todoOperateTimeEnd) {
		this.todoOperateTimeEnd = todoOperateTimeEnd;
	}

	public Date getTodoRemainTimeBegin() {
		return todoRemainTimeBegin;
	}

	public void setTodoRemainTimeBegin(Date todoRemainTimeBegin) {
		this.todoRemainTimeBegin = todoRemainTimeBegin;
	}

	public Date getTodoRemainTimeEnd() {
		return todoRemainTimeEnd;
	}

	public void setTodoRemainTimeEnd(Date todoRemainTimeEnd) {
		this.todoRemainTimeEnd = todoRemainTimeEnd;
	}
}
