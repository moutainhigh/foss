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
package com.deppon.foss.module.base.baseinfo.server.util;

import java.util.Comparator;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadableEntity;


/**
 * 按version对DownloadableEntity比较
 * 
 * @author foss-zhujunyong
 * @date Mar 21, 2013 11:58:28 AM
 * @version 1.0
 */
public class DownloadableComparator implements Comparator<DownloadableEntity> {

    @Override
    public int compare(DownloadableEntity o1, DownloadableEntity o2) {
	Long v1 = o1.getVersion();
	Long v2 = o2.getVersion();
	return (v1 < v2) ? -1 : ((v1.equals(v2)) ? 0 : 1);
    }

}
