package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 货物类型实体类
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-316759-wangruipeng,date:2017-3-30 下午7:04:50,content:货物类型实体类 </p>
 * @author Foss-316759-wangruipeng 
 * @date 2017-3-30 下午7:04:50
 * @since
 * @version
 */
public class GoodsTypeDubboDto extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	private String code;

	/** 名称 */
	private String name;

	/** 是否激活 */
	private String active;

	/** 描述 */
	private String mark;

	/** 版本号 */
	private Long versionNo;

	/** 开始时间 */
	private Date beginTime;

	/** 结束时间 */
	private Date endTime;

	/** 创建机构 */
	private String createOrgCode;

	/** 修改机构 */
	private String modifyOrgCode;

	/** 修改人姓名 */
	private String modifyUserName;

	/** 创建人人姓名 */
	private String createUserName;

	/** 业务日期 */
	private Date billDate;

	/** 详情 */
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

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

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
