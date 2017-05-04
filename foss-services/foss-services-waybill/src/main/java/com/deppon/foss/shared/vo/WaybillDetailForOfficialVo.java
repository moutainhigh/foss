package com.deppon.foss.shared.vo;

import java.util.List;

public class WaybillDetailForOfficialVo {
	
	/**
	  * @author WangZengming
	  * @date 2015-9-2
	  * @see:是否子母件
	  */
	private boolean isChlidParentWaybill;
	
	/**
	  * @author WangZengming
	  * @date 2015-9-2
	  * @see:母件单号
	  */
	private String parentWaybillNo;
	
	/**
	  * @author WangZengming
	  * @date 2015-9-2
	  * @see:子单号集合
	  */
	private List<String> childWaybillNoList;


	/**
	 * @return the isChlidParentWaybill
	 */
	public boolean getIsChlidParentWaybill() {
		return isChlidParentWaybill;
	}

	/**
	 * @param isChlidParentWaybill the isChlidParentWaybill to set
	 */
	public void setIsChlidParentWaybill(boolean isChlidParentWaybill) {
		this.isChlidParentWaybill = isChlidParentWaybill;
	}

	/**
	 * @return the parentWaybillNo
	 */
	public String getParentWaybillNo() {
		return parentWaybillNo;
	}

	/**
	 * @param parentWaybillNo the parentWaybillNo to set
	 */
	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
	}

	/**
	 * @return the childWaybillNoList
	 */
	public List<String> getChildWaybillNoList() {
		return childWaybillNoList;
	}

	/**
	 * @param childWaybillNoList the childWaybillNoList to set
	 */
	public void setChildWaybillNoList(List<String> childWaybillNoList) {
		this.childWaybillNoList = childWaybillNoList;
	}
	

}
