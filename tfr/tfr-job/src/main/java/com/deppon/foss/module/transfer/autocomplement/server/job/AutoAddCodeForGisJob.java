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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService;
import com.deppon.foss.module.transfer.load.api.shared.define.AutoAddCodeConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.CollectionUtils;

/**
* @description  自动补码job获取gis服务
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年11月3日 上午8:26:29
*/
public class AutoAddCodeForGisJob extends ThreadPoolcaller {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AutoAddCodeForGisJob.class);
	
	private IAutoAddCodeService autoAddCodeService;
	
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
		System.out.println("serviceCaller For Gis====>");
		int backIntValue = 0;
		//读取数据字典的 自动补码总开关
			//每次更新200条
				String jobId = autoAddCodeService.updateAndGetJobId();
			//取出200条记录
			
				List<AutoAddCodeEntity> entityList =null;
			try {
				entityList = autoAddCodeService.queryAutoAddCodeEntityByJodId(jobId);
			} catch (Exception t) {
				StringWriter sw=new StringWriter();  
		        PrintWriter pw=new PrintWriter(sw);  
		        t.printStackTrace(pw);
				LOGGER.error("autoAddGisService.queryAutoAddCodeEntityByJodId(jobId)查询异常",sw.toString());
				try {
					autoAddCodeService.restNaAJobId(jobId);
				} catch (Exception e1) {
					if(entityList!=null && entityList.size()>0){
						for (AutoAddCodeEntity autoAddCodeEntity : entityList) {
							autoAddCodeEntity.setJobId(jobId);
							StringWriter sw1=new StringWriter();  
					        PrintWriter pw1=new PrintWriter(sw1);  
					        e1.printStackTrace(pw1);
					        String msgError = "queryAutoAddCodeEntityByJodId 查询报错,重置补码时失败"+sw1.toString();
					        if(msgError.length()>ConstantsNumberSonar.SONAR_NUMBER_3500){
					        	msgError= msgError.substring(0,ConstantsNumberSonar.SONAR_NUMBER_3499);
					        }
					      //重置
							autoAddCodeService.addHand(autoAddCodeEntity,null, AutoAddCodeConstants.ADD_CODE_BY_JOB_FAILURE);
						}
					}
					
				}
			}
			//如果有待补码运单
			if(CollectionUtils.isNotEmpty(entityList)){
				backIntValue=entityList.size();
				for (AutoAddCodeEntity pojo : entityList) {
					pojo.setJobId(jobId);
					autoAddCodeService.ThreadsPool(pojo);
					backIntValue--;
				}
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
			autoAddCodeService.resetData();
		} catch (Exception e) {
			LOGGER.error("AutoAddCodeForGisJob.resetData()异常",e.getMessage());
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
