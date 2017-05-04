package com.deppon.foss.module.settlement.common.api.server.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 分页导出服务
 * 
 * @author luoyizhu@gmail.com
 * 
 *         2014-2-27 下午2:19:09
 */
public interface IPagingExportService {

	ByteArrayInputStream pagingExport(IPagingExportDataSource dataSource,int pageSize)
			throws IOException;

}
