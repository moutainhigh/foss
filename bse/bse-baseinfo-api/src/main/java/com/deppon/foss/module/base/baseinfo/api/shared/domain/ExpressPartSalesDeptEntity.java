package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递点部营业部映射关系
 * @author foss-qiaolifeng
 * @date 2013-7-24 下午6:20:13
 */
public class ExpressPartSalesDeptEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 445934543175538535L;

	/**
	 * 快递点部编码
	 */
	private String partCode;
	
	/**
	 * 营业部编码
	 */
	private String salesDeptCode;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 创建人编码
	 */
	private String createUserCode;
	
	/**
	 * 修改人编码
	 */
	private String modifyUserCode;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	/**
	 * 生效日期
	 */
	private Date beginTime;
	
	/**
	 * 失效日期
	 */
	private Date endTime;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getSalesDeptCode() {
		return salesDeptCode;
	}

	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
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

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	
	
}
