package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.DvrReturnCodEntity;

/**
 * 退代收货款参数Dto.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午10:57:16
 * @since
 * @version
 */
public class DvrReturnCodDto implements Serializable {

	/** 序列号. */
	private static final long serialVersionUID = -5329344548543419597L;
	
	/** 退代收货款实体集合. */
	private List<DvrReturnCodEntity> dvrReturnCodEntities;
	
	/** 退代收货款实体. */
	private DvrReturnCodEntity dvrReturnCodEntity;
	
	/** 期间. */
	private String period;
    
    /** 付款途径 */
	private String refundPath;
	
	/** 部门名称. */
	private String payableOrgCode;
	
	/** 退代收货款业务类型 */
	private String productCode;

	/** 总条数. */
	private long sum;

	
	/**
	 * @return  the dvrReturnCodEntities
	 */
	public List<DvrReturnCodEntity> getDvrReturnCodEntities() {
		return dvrReturnCodEntities;
	}

	
	/**
	 * @param dvrReturnCodEntities the dvrReturnCodEntities to set
	 */
	public void setDvrReturnCodEntities(
			List<DvrReturnCodEntity> dvrReturnCodEntities) {
		this.dvrReturnCodEntities = dvrReturnCodEntities;
	}

	
	/**
	 * @return  the dvrReturnCodEntity
	 */
	public DvrReturnCodEntity getDvrReturnCodEntity() {
		return dvrReturnCodEntity;
	}

	
	/**
	 * @param dvrReturnCodEntity the dvrReturnCodEntity to set
	 */
	public void setDvrReturnCodEntity(DvrReturnCodEntity dvrReturnCodEntity) {
		this.dvrReturnCodEntity = dvrReturnCodEntity;
	}

	
	/**
	 * @return  the period
	 */
	public String getPeriod() {
		return period;
	}

	
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
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
	 * @return  the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	
	
	
	
}
