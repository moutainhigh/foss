package com.deppon.foss.module.transfer.unload.api.tools;

import java.lang.reflect.Field;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.util.ReflectionUtils;

import com.deppon.foss.module.transfer.unload.api.tools.UnloadTheadPool.SendTask;

public class UnloadThreadAborPolicy implements RejectedExecutionHandler{
	
	/**
	 * 线程异常处理
	 * @author 105869
	 * @date 2015年3月20日 20:18:58
	 */
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("yichang");
		//获取执行任务的对象
		if (r instanceof FutureTask) {
			Field syncF = ReflectionUtils.findField(FutureTask.class, "sync");
			if (syncF != null) {
				ReflectionUtils.makeAccessible(syncF);
				Object syncV = ReflectionUtils.getField(syncF, r);
				//获取calable的对象
				Field runF = ReflectionUtils.findField(syncV.getClass(),
						"callable");
				if (runF != null) {
					ReflectionUtils.makeAccessible(runF);
					Object runV = ReflectionUtils.getField(runF, syncV);
					if (runV != null) {
                       //获取Task对象
						Field taskF = ReflectionUtils.findField(
								runV.getClass(), "task");
							if (taskF != null) {
								ReflectionUtils.makeAccessible(taskF);
								SendTask taskV = (SendTask) ReflectionUtils
										.getField(taskF, runV);
								if (taskV != null) {
									outOfPool(taskV.obj);
								}
							}

						}
					}

				} 
			}
	}
	
	/**
	 * 缓冲池溢出处理方式,用于不通业务实现
	 * */
	 void outOfPool(Object obj){
		return ;
	}

}
