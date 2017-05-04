/**   
 * File Name：ConfigItemEntity.java   
 *   
 * Version:1.0
 * ：2013-4-1   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * Class Description： 配置项实体类
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-1 下午2:13:40
 */

public class ConfigItemEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

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
	 * 配置项类型
	 */
	private String confType;

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
	 * 是否有效
	 */
	private String active;
	/**
	 * 版本号
	 */
	private long versionNo;

	/**
	 * 配置项类型名称
	 */
	private String confTypeName;

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
	 * @param id the id to set Date:2013-4-1下午2:16:04
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
	 * @param confCode the confCode to set Date:2013-4-1下午2:16:04
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
	 * @param confName the confName to set Date:2013-4-1下午2:16:04
	 */

	public void setConfName(String confName) {
		this.confName = confName;
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
	 * @param confType the confType to set Date:2013-4-1下午2:16:04
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
	 * @param active the active to set Date:2013-4-1下午2:16:04
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
	 * @param versionNo the versionNo to set Date:2013-4-1下午2:16:04
	 */

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
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
	 * @param createUserCode the createUserCode to set Date:2013-4-1下午7:40:05
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
	 * @param createTime the createTime to set Date:2013-4-1下午7:40:05
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
	 * @param modifyUserCode the modifyUserCode to set Date:2013-4-1下午7:40:05
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
	 * @param modifyTime the modifyTime to set Date:2013-4-1下午7:40:05
	 */

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
	 * @param confTypeName the confTypeName to set Date:2013-4-7上午10:43:56
	 */

	public void setConfTypeName(String confTypeName) {
		this.confTypeName = confTypeName;
	}

}
