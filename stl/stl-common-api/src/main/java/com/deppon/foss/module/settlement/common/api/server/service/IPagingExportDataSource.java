package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

public interface IPagingExportDataSource {

	/**
	 * 导出表头
	 * 
	 * @return
	 */
	String[] headers();

	/**
	 * excel列对应的值
	 * 
	 * @param index
	 * @return
	 */
	String[] mappings();

	/**
	 * 获取page页数据
	 * 
	 * @param page
	 * @return
	 */
	List dataList(int page);

	/**
	 * 获取临时文件存放目录 必须有读写权限
	 * 
	 * @return
	 */
	String tempDirectory();

	/**
	 * 获取需要导出的文件名
	 * 
	 * @return
	 */
	String filename();
}
