package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 重新修改走货路径
 * @author Foss-105888-Zhangxingwang
 * @date 2014-6-24 15:58:16
 *
 */
public class ReModifyRouteEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 流水号ID
	 */
	private String labeleGoodId;
	
	/**
	 * Job主键
	 */
	private String jobId;
	
	/**
	 * 更改失败原因
	 */
	private String failReason;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 产品类型
	 */
	private String productCode;

	//get、set方法
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getLabeleGoodId() {
		return labeleGoodId;
	}

	public void setLabeleGoodId(String labeleGoodId) {
		this.labeleGoodId = labeleGoodId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
