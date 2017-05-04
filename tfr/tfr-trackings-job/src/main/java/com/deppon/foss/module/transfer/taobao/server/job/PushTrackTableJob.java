package com.deppon.foss.module.transfer.taobao.server.job;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller;
import com.deppon.foss.module.transfer.common.api.server.service.IPushCarGoTrackService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;


/**
* @description 往轨迹表里写数据
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年5月28日 上午11:21:35
*/
public class PushTrackTableJob extends ThreadPoolcaller {
	
	/**
	* @fields pushCarGoTrackService
	* @author 14022-foss-songjie
	* @update 2015年5月28日 上午11:21:29
	* @version V1.0
	*/
	private IPushCarGoTrackService pushCarGoTrackService;
	

	public void setPushCarGoTrackService(
			IPushCarGoTrackService pushCarGoTrackService) {
		this.pushCarGoTrackService = pushCarGoTrackService;
	}

	@Override
	public int serviceCaller() {
		int backNum = 0;
		//将常量BUSINESS_GOAL_TAOBAO 按'|'切割
		//String BUSINESS_GOAL_TAOBAO = BusinessGoalContants.BUSINESS_GOAL_TAOBAO;
		List<String> list = new ArrayList<String>();
		String[] strArray = BusinessGoalContants.BUSINESS_GOAL_TODO_COMM.split(":");
		if(strArray!=null&&strArray.length>0){
			for (int i = 0; i < strArray.length; i++) {
				if(strArray[i]!=null){
					list.add(strArray[i].trim());
				}
			}
		}
		
		
		try{
			//一次分析的业务id数量
			int count = 10;
			backNum = pushCarGoTrackService.pushTrackForDop(count,list); 
		}catch(Exception e){
			backNum=1;//继续处理后面的数据
			return backNum;
		}
		return backNum;
	}

	@Override
	public long getSleepTime() {
		//设置线程执行休眠时间1s
		long sleepTime =1000l;
		return sleepTime;
	}

	@Override
	public void reSetDate() {
		pushCarGoTrackService.reSetJobMsgbyJobId();
	}

	@Override
	public long getNoDataSleepTime() {
		//设置线程执行休眠时间1min
		long sleepTime =60000l;
		return sleepTime;
	}
	
}
