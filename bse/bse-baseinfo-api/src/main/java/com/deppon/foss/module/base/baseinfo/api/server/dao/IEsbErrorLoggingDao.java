package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;

/**
 * 
 * 异步接口请求超时或者发生异常时 将异常信息记录
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:268984,date:2016-7-6 下午3:34:04,content:TODO </p>
 * @author 268984 
 * @date 2016-7-6 下午3:34:04
 * @since
 * @version
 */
public interface IEsbErrorLoggingDao {
	/**
	 * 添加ESB异常日志
	 * @param entity
	 */
	void addErrorMessage(EsbErrorLogging entity);
}
