package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity;


/**
* @description 称重量方查询界面VO
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月7日 下午1:47:33
*/
public class BCMachSortScanVo {
	
	private String waybillNo;//运单号
	private String operatorName;//操作人
	private String operationDept;//操作部门
	private Date endTime;//结束时间
	private Date begenTime;//开始时间
	private List<BCMachSortScanEntity>  sortScanList;//称重量方查询结果集
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperationDept() {
		return operationDept;
	}
	public void setOperationDept(String operationDept) {
		this.operationDept = operationDept;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getBegenTime() {
		return begenTime;
	}
	public void setBegenTime(Date begenTime) {
		this.begenTime = begenTime;
	}
	public List<BCMachSortScanEntity> getSortScanList() {
		return sortScanList;
	}
	public void setSortScanList(List<BCMachSortScanEntity> sortScanList) {
		this.sortScanList = sortScanList;
	}
	
	
}
