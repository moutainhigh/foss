package com.deppon.foss.module.transfer.taobao.server.job;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolConfigcaller;
import com.deppon.foss.module.transfer.common.api.server.service.IPushUnloadTruckTaskToWKService;
import com.deppon.foss.module.transfer.common .api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
* @description FOSS将卸车任务相关信息同步给WK
* @version 1.0
* @author 328768-foss-gaojianfu
* @update 2016年5月10日 上午9:53:58
 */
public class PushUnloadTruckTaskToWKJob extends ThreadPoolConfigcaller{
	/**主线程休眠时长 初始值*/
	private int sleepTime=ConstantsNumberSonar.SONAR_NUMBER_5000;
	
	/**主线程无数据情况追加休眠时长 初始值*/
	private int noDataSleepTime=ConstantsNumberSonar.SONAR_NUMBER_5000;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushUnloadTruckTaskToWKJob.class);
	/**
	* @fields pushUnloadTruckTaskToWKService
	* @author 328768-foss-gaojianfu
	* @update 2016年5月10日 上午9:53:58
	* @version V1.0
	*/
	private IPushUnloadTruckTaskToWKService pushUnloadTruckTaskToWKService;

	

	public void setPushUnloadTruckTaskToWKService(IPushUnloadTruckTaskToWKService pushUnloadTruckTaskToWKService) {
		this.pushUnloadTruckTaskToWKService = pushUnloadTruckTaskToWKService;
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
		param.put("notifyType", new String[]{
				//FOSS同步新建卸车任务给悟空
				"SYNC_NEW_EXPRESS_UNLOAD_TASK_TO_WK",
				//FOSS同步修改卸车任务到悟空系统
				"SYNC_UPDATE_EXPRESS_UNLOAD_TASK",
				//FOSS同步取消卸车任务到悟空系统
				"SYNC_CANCEL_UNLOAD_TASK_TO_WK",
				//Foss同步取消分配卸车任务到悟空
				"SYNC_CANCEL_ASSIGN_UNLOAD_TASK_TO_WK",
				//Foss同步分配卸车任务到悟空
				"SYNC_ASSIGN_UNLOAD_TASK_TO_WK",
				//FOSS同步确认卸车任务给悟空
				"SYNC_CONFIRM_EXPRESS_UNLOAD_TASK_TO_WK"
		 });
		/**间隔时间 分钟*/
		param.put("lag", 2);
		/**重复通知次数*/
		param.put("notifyNum",ConstantsNumberSonar.SONAR_NUMBER_15);
	}	
	
	@Override
	public int serviceCaller() {
		LOGGER.info("PushUnloadTruckTaskToWkJob");
		//调用父类读取控制线程的配置参数
		super.reSetParam(this.getClass().getName(),tfrNotifyService,param);
		//System.out.println(param.get("count")+"count--"+param.get("lag")+"lag--notifyNum"+param.get("notifyNum"));
		return pushUnloadTruckTaskToWKService.pushUnloadTruckTaskToWk(param);
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
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月10日 下午12:48:17
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
	* @author 328768-foss-gaojianfu
	* @update 2016年5月10日 下午12:48:49
	* @version V1.0
	 */
	@Override
	public void setNoDataSleepTime(int noDataSleepTime) {
		this.noDataSleepTime = noDataSleepTime;
	}
	
}
