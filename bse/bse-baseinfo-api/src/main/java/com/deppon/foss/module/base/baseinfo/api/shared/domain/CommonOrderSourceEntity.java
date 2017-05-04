package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * (订单来源公共选择对应的Entity实体)
 * @author 187862-dujunhui
 * @date 2014-9-20 下午6:05:41
 * @since
 * @version
 */
public class CommonOrderSourceEntity extends BaseEntity {

	/**
	 *（序列化）
	 */
	private static final long serialVersionUID = 3294375553459355448L;
	
	/**
	 * 订单来源编码
	 */
	private String sourceCode;
	/**
	 * 订单来源名称
	 */
	private String sourceName;
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
	
	
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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
