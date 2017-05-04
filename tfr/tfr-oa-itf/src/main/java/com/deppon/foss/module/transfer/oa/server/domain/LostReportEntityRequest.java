package com.deppon.foss.module.transfer.oa.server.domain;

import java.io.Serializable;
/**
 * @title: LostReportEntity
 * @description：零担丢货上报接口数据实体
 * @author： ZHANGDANDAN
 * @date： 2016-11-25 上午9:31:02
 */
public class LostReportEntityRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	//从CRM获取的用户投诉上报编号
	private String wayBillNo;
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
}
