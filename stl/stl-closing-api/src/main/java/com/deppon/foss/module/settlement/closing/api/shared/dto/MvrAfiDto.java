package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfiEntity;

/**
 * 始发、空运参数Dto.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午10:57:16
 * @since
 * @version
 */
public class MvrAfiDto implements Serializable {

	/** 序列号. */
	private static final long serialVersionUID = -5329344548543419597L;
	
	/** 始发、空运实体集合. */
	private List<MvrAfiEntity> mvrAfiEntities;
	
	/** 始发、空运实体. */
	private MvrAfiEntity mvrAfiEntity;
	
	/** 期间. */
	private String period;
    
    /** 客户编码. */
	private String customerCode;
	
	/** 部门名称. */
	private String orgCode;
	
	/** 始发、空运. */
	private String orgType;

	/** 总条数. */
	private long sum;
	
	
	
	/**
	 * Gets the period.
	 *
	 * @return  the period
	 */
	public String getPeriod() {
		return period;
	}


	
	/**
	 * Sets the period.
	 *
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}


	/**
	 * Gets the customer code.
	 *
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * Sets the customer code.
	 *
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * Gets the org code.
	 *
	 * @return  the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	 * Sets the org code.
	 *
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	/**
	 * Gets the org type.
	 *
	 * @return  the orgType
	 */
	public String getOrgType() {
		return orgType;
	}

	
	/**
	 * Sets the org type.
	 *
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}


	
	
	/**
	 * Gets the mvr afi entity.
	 *
	 * @return  the mvrAfiEntity
	 */
	public MvrAfiEntity getMvrAfiEntity() {
		return mvrAfiEntity;
	}


	
	/**
	 * Sets the mvr afi entity.
	 *
	 * @param mvrAfiEntity the mvrAfiEntity to set
	 */
	public void setMvrAfiEntity(MvrAfiEntity mvrAfiEntity) {
		this.mvrAfiEntity = mvrAfiEntity;
	}


	/**
	 * Gets the mvr afi entities.
	 *
	 * @return  the mvrAfiEntities
	 */
	public List<MvrAfiEntity> getMvrAfiEntities() {
		return mvrAfiEntities;
	}


	
	/**
	 * Sets the mvr afi entities.
	 *
	 * @param mvrAfiEntities the mvrAfiEntities to set
	 */
	public void setMvrAfiEntities(List<MvrAfiEntity> mvrAfiEntities) {
		this.mvrAfiEntities = mvrAfiEntities;
	}


	
	/**
	 * Gets the sum.
	 *
	 * @return  the sum
	 */
	public long getSum() {
		return sum;
	}


	
	/**
	 * Sets the sum.
	 *
	 * @param sum the sum to set
	 */
	public void setSum(long sum) {
		this.sum = sum;
	}
	
	
}
