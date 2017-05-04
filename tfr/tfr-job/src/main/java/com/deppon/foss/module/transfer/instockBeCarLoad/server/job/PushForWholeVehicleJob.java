/**  
 * Project Name:tfr-job  
 * File Name:PushForWholeVehicleJob.java  
 * Package Name:com.deppon.foss.module.transfer.instockBeCarLoad.server.job  
 * Date:2016年7月13日 下午6:12:14
 *  
 */
package com.deppon.foss.module.transfer.instockBeCarLoad.server.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.autocomplement.server.job.AutoAddCodeJob;
import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller;
import com.deppon.foss.module.transfer.departure.api.server.service.ISharedService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
 * ClassName: PushForWholeVehicleJob <br/>
 * Function: 整车入库job. <br/>
 * date: 2016年7月13日 下午6:12:14 <br/>
 * 
 * @author wangruipeng-316759 wangruipeng001@deppon.com
 * @version
 * @since JDK 1.6
 */
public class PushForWholeVehicleJob extends ThreadPoolcaller {

	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(AutoAddCodeJob.class);

	/**
	 * ***************** 出发底层接口 **************************.
	 */
	private ISharedService sharedService;

	/**
	 * sharedService.
	 * 
	 * @param sharedService
	 *            the sharedService to set
	 * @since JDK 1.6
	 */

	public void setSharedService(ISharedService sharedService) {
		this.sharedService = sharedService;
	}

	/**
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#serviceCaller()
	 */
	@Override
	public int serviceCaller() {
		int result = 0;
		try {
			sharedService.pushForWholeVehicle();
		} catch (Exception e) {
			LOGGER.error("PushForWholeVehicleJob:整车入库Job异常！" + e.getMessage());
		}
		return result;
	}

	/**
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#getSleepTime()
	 */
	@Override
	public long getSleepTime() {
		// 设置线程间隔时间(S)
		return 1 * ConstantsNumberSonar.SONAR_NUMBER_10;
	}

	/**
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#reSetDate()
	 */
	@Override
	public void reSetDate() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#getNoDataSleepTime()
	 */
	@Override
	public long getNoDataSleepTime() {
		// 无数据时线程休眠时间(MS)
		return 1 * 1000L;
	}

}
