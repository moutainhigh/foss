package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
 * @author 313352-foss-gouyangyang
 * @version 1.0
 * @description 临时租车运单查询(按照单号或时间段查询)
 * @update 2016年5月13日 下午4:12:22
 */
public class ExpressDateDto implements Serializable {

    /**
     *
     */
    public static final long serialVersionUID = 1L;
    
    private String billGenerationBeginTime;//单据生成起始查询时间

    private String billGenerationEndTime;//单据生成结束查询时间

    private String waybillNo;// 运单号

    private int currentPageNo; // 当前页数

    private int pageSize;  // 页大小

    private String operatorDeptNo; // 操作部门
    

	public String getBillGenerationBeginTime() {
		return billGenerationBeginTime;
	}

	public void setBillGenerationBeginTime(String billGenerationBeginTime) {
		this.billGenerationBeginTime = billGenerationBeginTime;
	}

	public String getBillGenerationEndTime() {
		return billGenerationEndTime;
	}

	public void setBillGenerationEndTime(String billGenerationEndTime) {
		this.billGenerationEndTime = billGenerationEndTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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

	public String getOperatorDeptNo() {
		return operatorDeptNo;
	}

	public void setOperatorDeptNo(String operatorDeptNo) {
		this.operatorDeptNo = operatorDeptNo;
	}
    
}
