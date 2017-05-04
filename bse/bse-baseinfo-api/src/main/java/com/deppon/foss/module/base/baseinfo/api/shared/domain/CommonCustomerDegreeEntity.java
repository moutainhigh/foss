package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * (客户等级公共选择对应的Entity实体)
 * @author 187862-dujunhui
 * @date 2014-9-22 下午4:25:43
 * @since
 * @version
 */
public class CommonCustomerDegreeEntity extends BaseEntity {

	/**
	 * （序列化）
	 */
	private static final long serialVersionUID = 3294375553459355448L;
	
	/**
	 * 客户等级编码
	 */
	private String degreeCode;
	/**
	 * 客户等级名称
	 */
	private String degreeName;
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
	
	public String getDegreeCode() {
		return degreeCode;
	}
	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
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
