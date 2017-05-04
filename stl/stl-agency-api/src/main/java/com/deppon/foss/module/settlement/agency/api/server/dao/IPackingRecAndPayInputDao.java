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
package com.deppon.foss.module.settlement.agency.api.server.dao;

import java.util.Map;

/**
 * 包装其他应收应付输入界面 dao
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-5 上午11:15:19,content:TODO </p>
 * @author 105762
 * @date 2014-6-5 上午11:15:19
 * @since 1.6
 * @version 1.0
 */
public interface IPackingRecAndPayInputDao {
	/**
	 * <p>插入包装其他应收应付年月记录</p> 
	 * @author 105762
	 * @date 2014-6-5 上午11:16:16
	 * @param map
	 * @return 成功条数
	 */
	int addPackingBillTime(Map<String, String> map);
}
