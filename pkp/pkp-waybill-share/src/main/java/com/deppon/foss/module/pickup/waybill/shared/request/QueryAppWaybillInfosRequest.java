package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户在APP子母件发货记录查询运单数据的查询参数
 * @author 272311
 * 2015.09.06
 */
public class QueryAppWaybillInfosRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -84182425582847995L;
	/**
	 * 收货人或发货人的电话
	 */
	private String mobilePhone ;
	/**
	 * 类型
	 */
    private String type ;
    /**
     * 运单状态
     */
    private String status ;
    /**
     * 开始时间
     */
    private Date startDate ;
    /**
     * 结束时间
     */
    private Date endDate ;
    /**
     * 起始页
     */
    private int pageNum = 0 ;
    
    /**
     * 20
     */
    private static final int NUM20 = 20;
    
    /**
     * 每页显示记录数
     */
    private int pageSize = NUM20 ;
    /**
     * 关键字
     */
    private String keyWords ;
    
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	
	@Override
	public String toString() {
		return "QueryAppWaybillInfosRequest [mobilePhone=" + mobilePhone
				+ ", keyWords=" + keyWords + ", type=" + type + ", status="
				+ status + ", pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	
}
