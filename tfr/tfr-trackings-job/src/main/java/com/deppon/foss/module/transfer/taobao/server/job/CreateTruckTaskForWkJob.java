package com.deppon.foss.module.transfer.taobao.server.job;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolConfigcaller;
import com.deppon.foss.module.transfer.common.api.server.service.ICreateWkTruckTaskService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 根据快递系统同步过来的交接单表头信息生成车辆任务信息 job
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年5月9日 上午8:44:21
*/
public class CreateTruckTaskForWkJob extends ThreadPoolConfigcaller{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateTruckTaskForWkJob.class);
	
	/**主线程休眠时长 初始值*/
	private int sleepTime=ConstantsNumberSonar.SONAR_NUMBER_5000;
	
	/**主线程无数据情况追加休眠时长 初始值*/
	private int noDataSleepTime=ConstantsNumberSonar.SONAR_NUMBER_5000;
	
	
	private ITfrNotifyService tfrNotifyService;
	
	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}
	private ICreateWkTruckTaskService createWkTruckTaskService;
	
	
	public void setCreateWkTruckTaskService(
			ICreateWkTruckTaskService createWkTruckTaskService) {
		this.createWkTruckTaskService = createWkTruckTaskService;
	}
	/**通知查询条件初始化
	 * notifyType 需要同步给快递系统的车辆任务更新动作 
	 * */
	private static final Map<String,Object> param=new HashMap<String,Object>();
	static{
		/**批处理数量*/
		param.put("count", ConstantsNumberSonar.SONAR_NUMBER_20);
		/**通知类型*/
		param.put("notifyType",new String[]{NotifyWkConstants.NOTIFY_TYPE_CREATE_TRUCK_TASK});
		/**间隔时间*/
		param.put("lag", 2);
		/**重复通知次数*/
		param.put("notifyNum",ConstantsNumberSonar.SONAR_NUMBER_5);
	}

	@Override
	public int serviceCaller() {
		LOGGER.info("CreateTruckTaskForWkJob");
		//为推送为ECS系统临时表加锁
		upateUnloadMsgForJob();
		//调用父类读取控制线程的配置参数
		super.reSetParam(this.getClass().getName(),tfrNotifyService,param);
		return createWkTruckTaskService.createWkTruckTask(param);
	}
	
	/**
	 * 为推送为ECS系统临时表加锁
	 * @return
	 */
	private String upateUnloadMsgForJob(){
		//获取jobId，用于线程并发
		String jobId = UUIDUtils.getUUID();
		param.put("jobId", jobId);
		//更新查询到的jobId
		tfrNotifyService.updateTfrNotifyListJobId(param);
		return jobId;
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
	@Override
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	@Override
	public void setNoDataSleepTime(int noDataSleepTime) {
		this.noDataSleepTime = noDataSleepTime;
	}
}
