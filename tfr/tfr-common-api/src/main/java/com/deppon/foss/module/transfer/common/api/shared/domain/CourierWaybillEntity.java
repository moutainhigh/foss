package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 调用Foss快递运单接口返回参数
 * 
 * @author 313352 gouyangyang
 * 
 */
public class CourierWaybillEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 异常信息
	private String exMsg;
	// 状态 0:失败,1:成功
	private String status;
	// 总记录数
	private int totalNumber;
	// 运单集合
	private List<QueryWaybillToFoss> details;
	// 页大小
	private int pageSize;
	
	private  int currentPageNo;
	
	public String getExMsg() {
		return exMsg;
	}
	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<QueryWaybillToFoss> getDetails() {
		return details;
	}
	public void setDetails(List<QueryWaybillToFoss> details) {
		this.details = details;
	}
}
