/**
 *  initial comments.
 */
/*
 * PROJECT NAME: mainframeUI
 * PACKAGE NAME: com.deppon.foss.module.mainframe.client.service
 * FILE    NAME: LongTaskService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.mainframe.client.service;

import com.deppon.foss.framework.client.commons.task.ITask;
import com.deppon.foss.framework.client.commons.task.impl.TaskService;
import com.deppon.foss.module.mainframe.client.ui.LongTaskDialog;


/**
 * 长任务操作
 * @author 102246-foss-shaohongliang
 * @date 2013-3-4 上午11:41:32
 */
public class LongTaskService {
	
	/**
	 * 
	 * 执行耗时任务
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-4 上午11:42:20
	 */
	public void execute(String title, ITask task) {
		//创建任务服务
		TaskService taskService = new TaskService();
		//创建超时操作等待面板对象
		new LongTaskDialog(title, taskService);
		//调用任务服务执行任务
		taskService.execute(task);
	}
}