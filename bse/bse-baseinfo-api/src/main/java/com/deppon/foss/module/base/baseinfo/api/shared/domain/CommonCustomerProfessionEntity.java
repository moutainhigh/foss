package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * (客户行业公共选择对应的Entity实体)
 * @author 187862-dujunhui
 * @date 2014-9-23 上午10:27:37
 * @since
 * @version
 */
public class CommonCustomerProfessionEntity extends BaseEntity {

	/**
	 *（序列化）
	 */
	private static final long serialVersionUID = 3294375553459355448L;
	
	/**
	 * 客户行业编码
	 */
	private String professionCode;
	/**
	 * 客户行业名称
	 */
	private String professionName;
	/**
	 * 备注（冗余）
	 */
	private String remark;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	public String getProfessionCode() {
		return professionCode;
	}
	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}
	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
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

}
