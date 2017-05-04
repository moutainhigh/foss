/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/ui/LongTaskDialog.java
 * 
 * FILE NAME        	: LongTaskDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.ui;

import java.awt.Component;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.commons.task.ITaskService;
import com.deppon.foss.framework.client.commons.task.TaskAdapter;
import com.deppon.foss.framework.client.commons.task.TaskEvent;
import com.deppon.foss.framework.client.commons.task.impl.TaskContext;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.workbench.impl.synthetica.Workbench;
import com.deppon.foss.module.mainframe.client.utills.ExceptionHandler;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 超时操作等待面板
 * @author 102246-foss-shaohongliang
 * @date 2013-3-2 下午2:13:33
 */
public class LongTaskDialog extends JDialog {

	/**
	 * 序列化属性
	 */
	private static final long serialVersionUID = 3753427499075455811L;
	//日志工厂
	private static final Logger LOGGER = LoggerFactory.getLogger(LongTaskDialog.class);
	/**
	 * 创建具有指定文本、图像和水平对齐方式的 JLabe实例
	 */
	private JLabel titleLabel;
	private JLabel loadingLabel;
	private JLabel timeLabel;

	// 国际化对象
	private static final II18n i18n = I18nManager.getI18n(LongTaskDialog.class);
	
	// 任务内容
	private volatile ITaskContext taskContext;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("restriction")
	public LongTaskDialog(String title, final ITaskService taskService) {
		// 设置为模式对话框
		setModal(true);
		// 窗口关闭时的处理
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// 固定大小
		this.setSize(NumberConstants.NUMBER_300, NumberConstants.NUMBER_50);
		this.setResizable(false);
		// 找到菜单面板
		Workbench workbench = (Workbench)ApplicationContext.getApplication().getWorkbench();
		JTabbedPane tabbedPane = workbench.getEditorContainer();
		Component comp = tabbedPane.getSelectedComponent();
		//菜单位置
		Point menubarLocation = comp.getLocationOnScreen();
		setLocation(menubarLocation.x + comp.getWidth() - NumberConstants.NUMBER_300, menubarLocation.y);
		// 设置背景色
//		getContentPane().setBackground(Color.white);
		// 隐藏标题
		setUndecorated(true);
		// 设置透明度
		try {
			com.sun.awt.AWTUtilities.setWindowOpacity(this, NumberConstants.NUMBER_0_9f);
		} catch (Exception e) {
			//根据sonar(不良实践 - 方法可能忽略异常 )修改
			LOGGER.error(e.getMessage(), e);
		}
		/**
		 * 设置布局,创建FormLayout对象并设置所需的参数
		 */
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		//创建JLabel对象titleLabel
		titleLabel = new JLabel(" "+ title);
		//将对象添加至容器内
		add(titleLabel, "2, 2, 3, 1");
		//创建JLabel对象loadingLabel
		loadingLabel = new JLabel();
		//为loadingLabel设置图标
		loadingLabel.setIcon(ImageUtil.getImageIcon(this.getClass().getClassLoader(), "loading.gif"));
		//将对象添加至容器内
		add(loadingLabel, "2, 4");
		//创建JLabel对象timeLabel
		timeLabel = new JLabel("已等待0秒");
		//将对象添加至容器内
		add(timeLabel, "4, 4");
		
		/**
		 * 添加任务监听器
		 */
		taskService.addTaskListener(new TaskAdapter() {
			private void checkForegroundTaskContext() {
			    	/**
			    	 * 创建任务会话上下文
			    	 */
				ITaskContext context = taskService.getForegroundTaskContext();
				/**
			    	 * 判断context是否为空并且是否为前台运行
			    	 */
				if (context != null && context.isInForeground()) {
				    	//显示前台任务内容
					showForegroundTaskContext(context);
				} else {
				    	//设置当前超时操作等待面板的任务内容为空
					LongTaskDialog.this.taskContext = null;
					//设置当前超时操作等待面板的不可见
					LongTaskDialog.this.setVisible(false);
					//释放资源
					LongTaskDialog.this.dispose();
				}
			}
			
			/**
			 * 
			 * <p>Title: showForegroundTaskContext</p>
			 * <p>Description: 显示前台任务内容</p>
			 * @param context
			 */
			private void showForegroundTaskContext(ITaskContext context) {
			    	/**
			    	 * 判断当前超时操作等待面板的任务内容是否等于参数context或者是否为前台运行，如果等于或者是，则返回
			    	 */
				if (LongTaskDialog.this.taskContext == context || ! context.isInForeground()) {
					return;
				}
				/**
				 * 设置当前超时操作等待面板的任务内容为参数context
				 */
				LongTaskDialog.this.taskContext = context;
				/**
				 * 设置当前超时操作等待面板的可见
				 */
				LongTaskDialog.this.setVisible(true);
			}
			
			@Override
			public void foregrounded(TaskEvent event) {
			    	/**
				 * 显示前台任务内容
				 */
				showForegroundTaskContext(event.getTaskContext());
			}
			
			@Override
			public void backgrounded(TaskEvent event) {
			    	/**
				 * 添加任务监听器
				 */
				checkForegroundTaskContext();
			}
			
			@Override
			public void executed(TaskEvent event) {
			    	/**
				 * 添加任务监听器
				 */
				checkForegroundTaskContext();
			}
			
			@Override
			public void titleChanged(TaskEvent event) {
			    	/**
				 * 判断任务内容是否等于当前超时操作等待面板的任务内容，如果不是，则返回
				 */
				if (event.getTaskContext() != LongTaskDialog.this.taskContext){
					return;
				}
				/**
				 * 设置面板文本为任务标题
				 */
				titleLabel.setText(event.getTaskContext().getTitle());
			}
			
			@Override
			public void cancelled(TaskEvent event) {
			    	/**
				 * 添加任务监听器
				 */
				checkForegroundTaskContext();
				/**
				 * 创建一个异常
				 */
				Exception ex = event.getTaskContext().getValue();
				/**
				 * 弹出异常
				 */
				ExceptionHandler.alert(ex);
				/**
				 * 取消事件
				 */
				super.cancelled(event);
			}
			
			@Override
			public void failed(TaskEvent event) {
			    	/**
				 * 添加任务监听器
				 */
				checkForegroundTaskContext();
				/**
				 * 创建一个异常
				 */
				Exception ex = event.getTaskContext().getValue();
				/**
				 * 弹出异常
				 */
				ExceptionHandler.alert(ex);
				/**
				 * 取消事件
				 */
				super.failed(event);
			}
			
			@Override
			public void executing(final TaskEvent event) {
			    	/**
			    	 * 创建一个定时任务对象
			    	 */
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
					    	/**
					    	 * 创建任务会话上下文对象context
					    	 */
						ITaskContext context = event.getTaskContext();
						/**
					    	 * 判断对象context是否为空并且是否为前台运行
					    	 */
						if (context != null && context.isInForeground()) {
						    	/**
						    	 * 判断当前任务是否等于超时操作等待面板的任务内容，如果不等于，则返回
						    	 */
							if (event.getTaskContext() != LongTaskDialog.this.taskContext) {
								return;
							}
							/**
							 * 获取当前任务的进度
							 */
							int p = event.getTaskContext().getProgress();
							/**
							 * 判断当前进度是否为未知，如果是，则设置面板文本为空字符串，否则，显示当前进度
							 */
							if (p == TaskContext.UNKNOWN_PROGRESS) {
								timeLabel.setText("");
							} else {
								timeLabel.setText("已等待"
										+ getRemainTime(event.getTaskContext()));
							}
						}
					}
				};
				/**
				 * 创建定时器，并每隔0.5秒执行一次任务
				 */
				Timer time = new Timer();
				time.schedule(task, 0, NumberConstants.NUMBER_500);

			}
			
		});
	}
	
	
	/**
	 * 
	 * @Title:getRemainTime
	 * @Description:根据任务上下文获得任务执行剩余时间
	 * @param @param context 任务上下文
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getRemainTime(ITaskContext context ) {
	    	/**
	    	 * 判断参数context是否为空，如果为空，则返回空格
	    	 */
		if (context == null) {
			return "";
		}
		//获取执行时间并处理
		long time = context.getDuration() - NumberConstants.NUMBER_200;
		//创建一个字符串
		String display;
		/**
		 * 根据时间长短设置字符串display
		 */
		if (time > NumberConstants.NUMBER_1000 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_24) {
		    	/**
			 * 如果时间大于一天，则计算出一共的天数
			 */
			display = ((int)(time / (NumberConstants.NUMBER_1000 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_24))) + i18n.get("Day");
		} else if (time > NumberConstants.NUMBER_1000 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_60) {
		    	/**
			 * 如果时间大于一小时，则计算出一共几小时
			 */
			display = ((int)(time / (NumberConstants.NUMBER_1000 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_60))) + i18n.get("Hours");
		} else if (time > NumberConstants.NUMBER_1000 * NumberConstants.NUMBER_60) {
		    	/**
			 * 如果时间大于一分钟，则计算出一共几分钟
			 */
			display = ((int)(time / (NumberConstants.NUMBER_1000 * NumberConstants.NUMBER_60))) + i18n.get("Minutes");
		} else {
		    	/**
			 * 如果时间大于一秒钟，则计算一共几秒
			 */
			display = ((int)(time / NumberConstants.NUMBER_1000)) + i18n.get("Seconds");
		}	
		/**
		 * 返回字符串display
		 */
		return display;//i18n.get("TimeRemain", display);
	}


}