/**  
 * Project Name:tfr-job  
 * File Name:AutoAddCodeJob.java  
 * Package Name:com.deppon.foss.module.transfer.autocomplement.server.job  
 * Date:2015年6月15日下午4:39:45  
 *  
 */
package com.deppon.foss.module.transfer.autocomplement.server.job;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddGisService;
import com.deppon.foss.module.transfer.load.api.shared.define.AutoAddCodeConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.CollectionUtils;

/**  
 * ClassName: AutoAddCodeJob <br/>  
 * Function: 自动补码job. <br/>  
 * date: 2015年6月15日 下午4:39:45 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class AutoAddCodeJob extends ThreadPoolcaller {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AutoAddCodeJob.class);
	
	private IAutoAddGisService autoAddGisService;
	
	private IAutoAddCodeService autoAddCodeService;
	
	/**  
	 * autoAddCodeService.  
	 *  
	 * @param   autoAddCodeService    the autoAddCodeService to set  
	 * @since   JDK 1.6  
	 */
	
	public void setAutoAddGisService(IAutoAddGisService autoAddGisService) {
		this.autoAddGisService = autoAddGisService;
	}
	

	
	/**  
	 * autoAddCodeService.  
	 *  
	 * @param   autoAddCodeService    the autoAddCodeService to set  
	 * @since   JDK 1.6  
	 */
	public void setAutoAddCodeService(IAutoAddCodeService autoAddCodeService) {
		this.autoAddCodeService = autoAddCodeService;
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#serviceCaller()  
	 */
	@Override
	public int serviceCaller() {
		System.out.println("serviceCaller====>");
		int backIntValue = 0;
		//读取数据字典的 自动补码总开关
		String readValue = autoAddCodeService.readAutoAddCodePower();
		if(StringUtils.isNotBlank(readValue)){
			if(StringUtils.endsWith(readValue, AutoAddCodeConstants.AUTO_ADD_JOB_OPEN+"")){
				//每次更新200条
				String jobId = autoAddGisService.GisUpdateGetJobId();
				//取出200条记录
				
				List<AutoAddGisEntity> entityList =null;
				try {
					entityList = autoAddGisService.queryGisAutoAddCodeEntityByJodId(jobId);
				} catch (Exception t) {
					StringWriter sw=new StringWriter();  
			        PrintWriter pw=new PrintWriter(sw);  
			        t.printStackTrace(pw);
					LOGGER.error("autoAddCodeService.queryGisAutoAddCodeEntityByJodId(jobId)查询异常",sw.toString());
				}
				//如果有待补码运单
				if(CollectionUtils.isNotEmpty(entityList)){
					backIntValue=entityList.size();
					for (AutoAddGisEntity autoAddGisEntity : entityList) {
						autoAddGisService.ThreadsPool(autoAddGisEntity);
						backIntValue--;
					}
					//backIntValue = outSize+gisSize;
				}
			}
		}else{
			LOGGER.error("AutoAddCodeJob:从数据字典获取自动补码总开关异常！"+readValue);
		}
		return backIntValue;
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#getSleepTime()  
	 */
	@Override
	public long getSleepTime() {
		//线程执行间隔 精确到毫秒
		return autoAddCodeService.readAutoAddCodeThreadExeTime();
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#reSetDate()  
	 */
	@Override
	public void reSetDate() {
		try {
			autoAddGisService.resetData();
		} catch (Exception e) {
			LOGGER.error("autoAddCodeService.resetData()异常",e.getMessage());
		}
	}

	/**  
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#getNoDataSleepTime()  
	 */
	@Override
	public long getNoDataSleepTime() {
		//5秒
		return ConstantsNumberSonar.SONAR_NUMBER_5*1000L;
	}

}
