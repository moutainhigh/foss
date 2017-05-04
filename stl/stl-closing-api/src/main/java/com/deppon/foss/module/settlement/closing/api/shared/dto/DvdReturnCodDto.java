package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdReturnCodEntity;

/**
 * 退代收货款明细参数Dto.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午10:57:16
 * @since
 * @version
 */
public class DvdReturnCodDto implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -897399566426914710L;

	/** 退代收货款实体集合. */
	private List<DvdReturnCodEntity> dvdReturnCodEntities;
	
	/** 退代收货款实体. */
	private DvdReturnCodEntity dvdReturnCodEntity;
	
	private Date startDate;
	
	private Date endDate;
    
    /** 付款途径 */
	private String refundPath;
	
	/** 部门名称. */
	private String payableOrgCode;
	
	/** 退代收货款业务类型 */
	private String codType;
	
	/** 退代收货款业务类型集合 */
	private List<String> codTypes;
	
	/** 运输性质List */
	private List<String> productCodeList;

	/** 总条数. */
	private long sum;

	
	/**
	 * @return  the dvdReturnCodEntities
	 */
	public List<DvdReturnCodEntity> getDvdReturnCodEntities() {
		return dvdReturnCodEntities;
	}

	
	/**
	 * @param dvdReturnCodEntities the dvdReturnCodEntities to set
	 */
	public void setDvdReturnCodEntities(
			List<DvdReturnCodEntity> dvdReturnCodEntities) {
		this.dvdReturnCodEntities = dvdReturnCodEntities;
	}

	
	/**
	 * @return  the dvdReturnCodEntity
	 */
	public DvdReturnCodEntity getDvdReturnCodEntity() {
		return dvdReturnCodEntity;
	}

	
	/**
	 * @param dvdReturnCodEntity the dvdReturnCodEntity to set
	 */
	public void setDvdReturnCodEntity(DvdReturnCodEntity dvdReturnCodEntity) {
		this.dvdReturnCodEntity = dvdReturnCodEntity;
	}

	
	
	/**
	 * @return  the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}


	
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	
	/**
	 * @return  the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}


	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	/**
	 * @return  the refundPath
	 */
	public String getRefundPath() {
		return refundPath;
	}

	
	/**
	 * @param refundPath the refundPath to set
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}

	
	/**
	 * @return  the payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	
	/**
	 * @param payableOrgCode the payableOrgCode to set
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	
	/**
	 * @return  the codType
	 */
	public String getCodType() {
		return codType;
	}


	
	/**
	 * @param codType the codType to set
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}


	
	/**
	 * @return  the codTypes
	 */
	public List<String> getCodTypes() {
		return codTypes;
	}


	
	/**
	 * @param codTypes the codTypes to set
	 */
	public void setCodTypes(List<String> codTypes) {
		this.codTypes = codTypes;
	}


	/**
	 * @return  the sum
	 */
	public long getSum() {
		return sum;
	}

	
	/**
	 * @param sum the sum to set
	 */
	public void setSum(long sum) {
		this.sum = sum;
	}


	/**
	 * @return productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}


	/**
	 * @param productCodeList
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}
	
}
