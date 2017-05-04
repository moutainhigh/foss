package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * (代收货款打包退款CodRefundAdditional的实体类)
 * @author 187862-dujunhui
 * @date 2014-7-23 下午3:22:43
 * @since
 * @version v1.0
 */
public class CodRefundAdditionalEntity extends BaseEntity {

	private static final long serialVersionUID = -575837681062590687L;
	
	//用户名称
	private String customerName;
	//用户编码
	private String customerCode;
	//附件
	private String additional;
	//备注
	private String remark;
	//是否有效
	private String active;
	//版本号
	private Long version;
	/**
	 * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return  the additional
	 */
	public String getAdditional() {
		return additional;
	}
	/**
	 * @param additional the additional to set
	 */
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	/**
	 * @return  the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return  the version
	 */
	public Long getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
