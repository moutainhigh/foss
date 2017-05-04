/**   
 * File Name：ConfigOrgRelationEntity.java   
 *   
 * Version:1.0
 * ：2013-3-28   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * Class Description：配置项与组织对应关系实体类
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-3-28 下午5:36:06
 */
public class ConfigOrgRelationEntity extends BaseEntity {

	private static final long serialVersionUID = 2214742071589241987L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 配置项编码
	 */
	private String confCode;
	/**
	 * 配置项名称
	 */
	private String confName;
	/**
	 * 组织编码
	 */
	private String orgCode;
	/**
	 * 组织名称
	 */
	private String orgName;
	/**
	 * 配置项类型
	 */
	private String confType;

	/**
	 * 配置项类型名称
	 */
	private String confTypeName;

	/**
	 * 创建人编号
	 */
	private String createUserCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改编号
	 */
	private String modifyUserCode;
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 是否启用
	 */
	private String active;
	/**
	 * 版本号
	 */
	private long versionNo;

	/**
	 * id
	 * 
	 * @return the id
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set Date:2013-3-28下午5:39:40
	 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * confCode
	 * 
	 * @return the confCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getConfCode() {
		return confCode;
	}

	/**
	 * @param confCode the confCode to set Date:2013-3-28下午5:39:40
	 */

	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}

	/**
	 * confName
	 * 
	 * @return the confName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getConfName() {
		return confName;
	}

	/**
	 * @param confName the confName to set Date:2013-3-28下午5:39:40
	 */

	public void setConfName(String confName) {
		this.confName = confName;
	}

	/**
	 * orgCode
	 * 
	 * @return the orgCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set Date:2013-3-28下午5:39:40
	 */

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * orgName
	 * 
	 * @return the orgName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set Date:2013-3-28下午5:39:40
	 */

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * confType
	 * 
	 * @return the confType
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getConfType() {
		return confType;
	}

	/**
	 * @param confType the confType to set Date:2013-3-28下午5:39:40
	 */

	public void setConfType(String confType) {
		this.confType = confType;
	}

	/**
	 * active
	 * 
	 * @return the active
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set Date:2013-3-28下午5:39:40
	 */

	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * versionNo
	 * 
	 * @return the versionNo
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo the versionNo to set Date:2013-3-28下午5:39:40
	 */

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * confTypeName
	 * 
	 * @return the confTypeName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getConfTypeName() {
		return confTypeName;
	}

	/**
	 * @param confTypeName the confTypeName to set Date:2013-4-1下午4:04:26
	 */

	public void setConfTypeName(String confTypeName) {
		this.confTypeName = confTypeName;
	}

	/**
	 * createUserCode
	 * 
	 * @return the createUserCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set Date:2013-4-1下午7:39:26
	 */

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * createTime
	 * 
	 * @return the createTime
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set Date:2013-4-1下午7:39:26
	 */

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * modifyUserCode
	 * 
	 * @return the modifyUserCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode the modifyUserCode to set Date:2013-4-1下午7:39:26
	 */

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * modifyTime
	 * 
	 * @return the modifyTime
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set Date:2013-4-1下午7:39:26
	 */

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
