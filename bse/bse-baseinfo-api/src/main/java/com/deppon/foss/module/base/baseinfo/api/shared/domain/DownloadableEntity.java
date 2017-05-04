/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 可下载实体
 * @author foss-zhujunyong
 * @date Mar 21, 2013 12:16:46 PM
 * @version 1.0
 */
public class DownloadableEntity extends BaseEntity {

    /**
     * 序列化 
     */
    private static final long serialVersionUID = 3083101866081295800L;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 13, 2013 9:48:47 AM
     * @return
     * @see
     */
    public Long getVersion() {
	return version;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 13, 2013 9:48:54 AM
     * @param version
     * @see
     */
    public void setVersion(Long version) {
	this.version = version;
    }

    
}
