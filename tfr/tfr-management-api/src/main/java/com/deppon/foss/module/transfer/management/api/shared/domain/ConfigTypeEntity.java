/**   
 * File Name：ConfigTypeEntity.java   
 *   
 * Version:1.0
 * ：2013-4-16   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * Class Description：配置项类型信息
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-16 上午9:15:29
 */

public class ConfigTypeEntity extends BaseEntity {

	/**
	 * serialVersionUID:
	 * 
	 * @since Ver 1.0
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 配置项类型
	 */
	private String confType;

	/**
	 * 配置项类型名称
	 */
	private String confTypeName;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 版本号
	 */
	private long versionNo;

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
	 * @param confType the confType to set Date:2013-4-16上午9:18:31
	 */

	public void setConfType(String confType) {
		this.confType = confType;
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
	 * @param confTypeName the confTypeName to set Date:2013-4-16上午9:18:31
	 */

	public void setConfTypeName(String confTypeName) {
		this.confTypeName = confTypeName;
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
	 * @param active the active to set Date:2013-4-16上午9:18:31
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
	 * @param versionNo the versionNo to set Date:2013-4-16上午9:18:31
	 */

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

}
