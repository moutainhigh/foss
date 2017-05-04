package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;

/**
 * 小票DTO.
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-23 上午8:13:41
 */
/**
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-1-6 上午11:24:48</p>
 * @author 105762
 * @date 2014-1-6 上午11:24:48
 * @since
 * @version
 */
/**
 * <p style="display:none">
 * modifyRecord</p>
 * <p style="display:none">
 * version:V1.0,author:105762,date:2014-1-6 上午11:24:50</p>
 * @author 105762
 * @date 2014-1-6 上午11:24:50
 * @since
 * @version
 */
public class OtherRevenueDto extends OtherRevenueEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1859051693050388008L;

	/** 开始时间. */
	private Date createStartTime;

	/** 结束时间. */
	private Date createEndTime;
	/**
	 * @218392 zhangyongxue 2015-10-12 小票优化需求
	 * @return 时间类型
	 */
	private String timeType;
	
	/** 小票单号集合. */
	private List<String> otherRevenueNos;

	/** 查询选项卡标识. */
	private String queryPageTab;

	/**
	 * 总条数
	 */
	private long totalCount;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 业务类型
	 */
	private String businessCase;

	/**
	 * 运输性质
	 */
	private List<String> productCodeList;

	/**
	 * POS机流水号
	 */
	private String batchNo;

    /**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 临时租车记录
	 */
	private String rentCarNo;

	/**
	 * 催款部门
	 */
	private String dunningOrgCode;
	private String dunningOrgName;

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the creates the start time.
	 * 
	 * @return createStartTime
	 */
	public Date getCreateStartTime() {
		return createStartTime;
	}

	/**
	 * Sets the creates the start time.
	 * 
	 * @param createStartTime
	 *            the new creates the start time
	 */
	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	/**
	 * Gets the creates the end time.
	 * 
	 * @return createEndTime
	 */
	public Date getCreateEndTime() {
		return createEndTime;
	}

	/**
	 * Sets the creates the end time.
	 * 
	 * @param createEndTime
	 *            the new creates the end time
	 */
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	/**
	 * Gets the other revenue nos.
	 * 
	 * @return otherRevenueNos
	 */
	public List<String> getOtherRevenueNos() {
		return otherRevenueNos;
	}

	/**
	 * Sets the other revenue nos.
	 * 
	 * @param otherRevenueNos
	 *            the new other revenue nos
	 */
	public void setOtherRevenueNos(List<String> otherRevenueNos) {
		this.otherRevenueNos = otherRevenueNos;
	}

	/**
	 * Gets the query page tab.
	 * 
	 * @return queryPageTab
	 */
	public String getQueryPageTab() {
		return queryPageTab;
	}

	/**
	 * Sets the query page tab.
	 * 
	 * @param queryPageTab
	 *            the new query page tab
	 */
	public void setQueryPageTab(String queryPageTab) {
		this.queryPageTab = queryPageTab;
	}

	/**
	 * @return totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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

	/**
	 * @GET
	 * @return businessCase
	 */
	public String getBusinessCase() {
		/*
		 * @get
		 * 
		 * @ return businessCase
		 */
		return businessCase;
	}

	/**
	 * @SET
	 * @param businessCase
	 */
	public void setBusinessCase(String businessCase) {
		/*
		 * @set
		 * 
		 * @this.businessCase = businessCase
		 */
		this.businessCase = businessCase;
	}

	/**
	 * @GET
	 * @return productCodeList
	 */
	public List<String> getProductCodeList() {
		/*
		 * @get
		 * 
		 * @ return productCodeList
		 */
		return productCodeList;
	}

	/**
	 * @SET
	 * @param productCodeList
	 */
	public void setProductCodeList(List<String> productCodeList) {
		/*
		 * @set
		 * 
		 * @this.productCodeList = productCodeList
		 */
		this.productCodeList = productCodeList;
	}

	public String getRentCarNo() {
		return rentCarNo;
	}

	public void setRentCarNo(String rentCarNo) {
		this.rentCarNo = rentCarNo;
	}

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
	public String getDunningOrgCode() {
		return dunningOrgCode;
	}

	public void setDunningOrgCode(String dunningOrgCode) {
		this.dunningOrgCode = dunningOrgCode;
	}

	public String getDunningOrgName() {
		return dunningOrgName;
	}

	public void setDunningOrgName(String dunningOrgName) {
		this.dunningOrgName = dunningOrgName;
	}
}
