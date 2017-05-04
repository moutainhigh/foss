/**
 * @author foss 257200
 * 2015-6-18
 * 257220
 */
package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.Date;

/**
 * @author 257220
 *
 */
public interface IBatchLoadingReportService {
	
	/**
	 * 分批配载上报 
	 * @author 257220
	 * @date 2015-6-18下午1:57:51
	 */
	 void reportForQMS();
	 
	 /**
	  * 生成上报数据 
	  * @author 257220
	 * @return 
	  * @date 2015-6-18下午4:32:25
	  */
	 int createBatchLoadingReport(Date bizJobStartTime,Date lastJobEndTime);
}
