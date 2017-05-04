/**
 *  initial comments.
 */
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.mainframe.client.utills;

import java.awt.Dialog.ModalExclusionType;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.core.application.IApplication;

/**
 * 在设置的时间内，停止dialog
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:50:47,content:
 * @author dp-yangtong
 * @date 2012-10-12 上午11:50:47
 * @since
 * @version
 */
public class InitUIDialog extends SwingWorker<JDialog, Void> {

	/**
	 * 200
	 */
	private static final int TWOHUNDRED = 200;

	/**
	 * 50
	 */
	private static final int FIFTY = 50;

	/**
	 * 1000
	 */
	private static final int ONETHOUSAND = 1000;

	/**
	 * 100
	 */
	private static final int HUNDRED = 100;

	/**
	 * log
	 */
	private static final Log LOG = LogFactory.getLog(InitUIDialog.class);
	
	/**
	 * sleep time
	 */
	private  int continueTome = HUNDRED;
	/**
	 * application context
	 */
	private IApplication application;

	/**
	 * 
	 * @功能：创建一个新的实例 initUIDialog.制定持续时间
	 * @作者：linws
	 * @since
	 */
	public InitUIDialog(int continueTome, IApplication application) {
		this.continueTome = continueTome;
		this.application = application;
	}

	/**
	 * 
	 * @功能：创建一个新的实例 initUIDialog.不需要指定时间
	 * @作者：linws
	 * @since
	 */
	public InitUIDialog(IApplication application) {
		this.application = application;
	}

	/**
	 * background thread 
	 */
	@Override
	protected JDialog doInBackground() throws Exception {
		//frame
	    	/**
	    	 * 创建并获取JFrame对象frame
	    	 */
		JFrame frame = (JFrame) application.getWorkbench().getFrame();
		//dialog
		/**
	    	 * 创建JDialog类型的常量dialog
	    	 */
		final JDialog dialog = new JDialog(frame, true);
		/**
	    	 * 设置dialog大小
	    	 */
		dialog.setSize(ONETHOUSAND, FIFTY);
		/**
	    	 * 设置 路径
	    	 */
		dialog.setLocation(frame.getX() + TWOHUNDRED, frame.getY() + TWOHUNDRED);
		/**
	    	 * 设置标题
	    	 */
		dialog.setTitle("请稍候！！");
		/**
	    	 * 如果此窗口是可见的，则将此窗口置于前端
	    	 */
		dialog.toFront();
		/**
	    	 * 设置模式
	    	 */
		dialog.setModal(true);
		/**
	    	 * 获取模式对话框类型
	    	 */
		dialog.getModalExclusionType();
		/**
	    	 * 设置模式对话框类型
	    	 */
		dialog.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		/**
	    	 * 设置默认的关闭窗口
	    	 */
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			    	/**
			    	 * 创建JProgressBar对象progressBar
			    	 */
				JProgressBar progressBar = new JProgressBar();
				/**
			    	 * 设置Value
			    	 */
				progressBar.setValue(HUNDRED);
				/**
			    	 * 将progressBar添加至dialog
			    	 */
				dialog.add(progressBar);
				/**
			    	 * 将dialog设置为可见的
			    	 */
				dialog.setVisible(true);
			}
		});
		/**
	    	 * 睡眠并返回
	    	 */
		Thread.sleep(continueTome);
		return dialog;
	}
	/**
	 * 
	 * 功能：done在EDT线程中，关闭dialog
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	protected void done() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
				    	/**
				    	 * 设置为不可见的
				    	 */
					get().setVisible(false);
				} catch (InterruptedException e) {
					//exception log
				    	/**
				    	 * 记录错误日志
				    	 */
					LOG.error("InterruptedException", e);
				} catch (ExecutionException e) {
					//exception log
				    	/**
				    	 * 记录错误日志
				    	 */
					LOG.error("ExecutionException", e);
				}

			}
		});
		/**
	    	 * 完成释放资源
	    	 */
		super.done();

	}

}