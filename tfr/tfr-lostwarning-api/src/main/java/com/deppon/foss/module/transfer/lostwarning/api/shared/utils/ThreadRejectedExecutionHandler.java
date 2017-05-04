package com.deppon.foss.module.transfer.lostwarning.api.shared.utils;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;

/**
 * 线程池异常处理类
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：ThreadRejectedExecutionHandler
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-11 下午6:51:58
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class ThreadRejectedExecutionHandler implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.err.println("线程池已满！！！ Waiting for a second !!");
        try {
            Thread.sleep(LostWarningConstant.SONAR_NUMBER_1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //executor.execute(r);
	}

}
