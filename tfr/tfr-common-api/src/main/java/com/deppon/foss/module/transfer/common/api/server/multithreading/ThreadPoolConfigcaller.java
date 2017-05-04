package com.deppon.foss.module.transfer.common.api.server.multithreading;

import java.util.Map;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyConfig;


/**
* @description 支持配置参数功能的生产者单线程设计
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年5月7日 上午10:30:41
*/
public abstract class ThreadPoolConfigcaller extends ThreadPoolcaller{
	/**主线程执行业务接口次数计数 初始计数为0*/
	private int num=0;
	/**主线程执行业务接口最大次数，重置配置信息的界数*/
	private static final int CATCH_CONFIG_MAX_NUM=10;
	
	@Override
	public abstract int serviceCaller();

	@Override
	public abstract long getSleepTime();

	@Override
	public abstract void reSetDate();

	@Override
	public abstract long getNoDataSleepTime();
	
	
	public abstract void setSleepTime(int sleepTime);
	
	public abstract void setNoDataSleepTime(int noDataSleepTime);
	
	public void reSetParam(String className,ITfrNotifyService tfrNotifyService,Map<String,Object> param){
		/**
		 * 未找到FOSS缓存入口，故而用此伪缓存方式
		 * 伪缓存方式:主任务执行m次就查询一下数据库配置信息,重新配置下信息(原有设计,生产线程为单线程)
		 * */
		if(num++>=CATCH_CONFIG_MAX_NUM){
			startReSetParam(className,tfrNotifyService,param);
			num=0;
		}
	}
	
	private void startReSetParam(String className,
			ITfrNotifyService tfrNotifyService, Map<String, Object> param) {
		TfrNotifyConfig config=tfrNotifyService.queryNotifyConfig(className);
		if(config==null)
			return;
		/**至少大于等于1000毫秒*/
		if(config.getNodataSleepTime()!=null&&config.getNodataSleepTime()>=TransferConstants.SONAR_NUMBER_1000){
			setNoDataSleepTime(config.getNodataSleepTime());
		}
		/**至少大于等于1000毫秒*/
		if(config.getSleepTime()!=null&&config.getSleepTime()>=TransferConstants.SONAR_NUMBER_1000){
			setSleepTime(config.getSleepTime());
		}
		/**时间间隔 至少大于等于1分钟*/
		if(config.getNotifyTimeLag()!=null&&config.getNotifyTimeLag()>=1){
			param.put("lag", config.getNotifyTimeLag());
		}
		/**批量处理条数至少大于等于10条*/
		if(config.getNotifyCount()!=null&&config.getNotifyCount()>=TransferConstants.SONAR_NUMBER_10){
			param.put("count", config.getNotifyCount());
		}
		/**通知次数至少大于等于2*/
		if(config.getNotifyMaxNum()!=null&&config.getNotifyMaxNum()>=2){
			param.put("notifyNum",config.getNotifyMaxNum());
		}
		
	}

	
		

}
