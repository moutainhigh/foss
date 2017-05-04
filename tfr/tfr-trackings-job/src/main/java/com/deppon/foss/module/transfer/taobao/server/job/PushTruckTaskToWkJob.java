package com.deppon.foss.module.transfer.taobao.server.job;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolConfigcaller;
import com.deppon.foss.module.transfer.common.api.server.service.IPushTruckTaskToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
* @description FOSS将任务车辆相关信息同步给WK
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月22日 下午1:54:52
*/
public class PushTruckTaskToWkJob extends ThreadPoolConfigcaller{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushTruckTaskToWkJob.class);
	
	/**主线程休眠时长 初始值*/
	private int sleepTime=ConstantsNumberSonar.SONAR_NUMBER_5000;
	
	/**主线程无数据情况追加休眠时长 初始值*/
	private int noDataSleepTime=ConstantsNumberSonar.SONAR_NUMBER_5000;
	
	/**
	* @fields pushTruckTaskToWkService
	* @author 283250-foss-liuyi
	* @update 2016年4月22日 下午2:13:54
	* @version V1.0
	*/
	private IPushTruckTaskToWkService pushTruckTaskToWkService;
	
	public void setPushTruckTaskToWkService(
			IPushTruckTaskToWkService pushTruckTaskToWkService) {
		this.pushTruckTaskToWkService = pushTruckTaskToWkService;
	}

	private ITfrNotifyService tfrNotifyService;
	
	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}

	/**通知查询条件初始化
	 * notifyType 需要同步给快递系统的车辆任务
	 * */
	private static final Map<String,Object> param=new HashMap<String,Object>();
	static{
		/**批处理数量*/
		param.put("count", ConstantsNumberSonar.SONAR_NUMBER_20);
		/**通知类型*/
		param.put("notifyType", new String[]{NotifyWkConstants.NOTIFY_TYPE_PUSH_TRUCK_TASK});
		/**间隔时间 分钟*/
		param.put("lag", 2);
		/**重复通知次数*/
		param.put("notifyNum",ConstantsNumberSonar.SONAR_NUMBER_15);
	}	
	
	@Override
	public int serviceCaller() {
		LOGGER.info("PushTruckTaskToWkJob");
		//调用父类读取控制线程的配置参数
		super.reSetParam(this.getClass().getName(),tfrNotifyService,param);
		//System.out.println(param.get("count")+"count--"+param.get("lag")+"lag--notifyNum"+param.get("notifyNum"));
		return pushTruckTaskToWkService.pushTruckTaskToWk(param);
	}

	@Override
	public long getSleepTime() {
		return sleepTime;
	}
	@Override
	public void reSetDate() {
		
	}

	@Override
	public long getNoDataSleepTime() {
		return noDataSleepTime;
	}
	
	
	/**
	* @description 重配置主线程休眠时长
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolConfigcaller#setSleepTime(int)
	* @author 283250-foss-liuyi
	* @update 2016年5月7日 上午11:10:48
	* @version V1.0
	*/
	@Override
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	/**
	* @description 重配置无数据追加休眠时长
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolConfigcaller#setNoDataSleepTime(int)
	* @author 283250-foss-liuyi
	* @update 2016年5月7日 上午11:10:17
	* @version V1.0
	*/
	@Override
	public void setNoDataSleepTime(int noDataSleepTime) {
		this.noDataSleepTime = noDataSleepTime;
	}
	
}
