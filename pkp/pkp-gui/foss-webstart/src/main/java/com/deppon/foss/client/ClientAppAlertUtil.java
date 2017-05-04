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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ClientAppAlertUtil.java
 * 
 * FILE NAME        	: ClientAppAlertUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class ClientAppAlertUtil {
	
	private JFrame frame;
	private static ClientAppAlertUtil instance;
	private ClientAppAlertUtil(){ }
	public void setParentFrame(JFrame pframe){
		this.frame = pframe;
	}
	
	public static ClientAppAlertUtil getInstance(JFrame pframe){
		if(instance==null){
			instance = new ClientAppAlertUtil();
		}
		instance.setParentFrame(pframe);
		return instance;
	}
	
	/**
	 * 弹出询问信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            询问信息
	 * @param buttons
	 *            按钮数组
	 * @return 用户所点击的按钮数组下标, 如果用户直接点关闭对话框则返回-1
	 */
	public int showAsk(String title, String message, String[] buttons) {
		return showDialog(JOptionPane.QUESTION_MESSAGE, title, message, buttons);
	}

	/**
	 * 弹出普通信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            普通信息
	 * @param buttons
	 *            按钮数组
	 * @return 用户所点击的按钮数组下标, 如果用户直接点关闭对话框则返回-1
	 */
	public int showInfo(String title, String message, String[] buttons) {
		return showDialog(JOptionPane.INFORMATION_MESSAGE, title, message,
				buttons);
	}

	/**
	 * 弹出警告信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            警告信息
	 * @param buttons
	 *            按钮数组
	 * @return 用户所点击的按钮数组下标, 如果用户直接点关闭对话框则返回-1
	 */
	public int showWarn(String title, String message, String[] buttons) {
		return showDialog(JOptionPane.WARNING_MESSAGE, title, message, buttons);
	}

	/**
	 * 弹出错误信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            错误信息
	 * @param buttons
	 *            按钮数组
	 * @return 用户所点击的按钮数组下标, 如果用户直接点关闭对话框则返回-1
	 */
	public int showError(String title, String message, String[] buttons) {
		return showDialog(JOptionPane.ERROR_MESSAGE, title, message, buttons);
	}

	private int showDialog(int type, String title, String message,
			String[] buttons) {
		return JOptionPane.showOptionDialog(frame, message, title,
				JOptionPane.DEFAULT_OPTION, type, null, buttons, buttons[0]);
	}
	
	public int showConfirm( String message){
		return JOptionPane.showConfirmDialog(frame, message);
	}

	/**
	 * 弹出询问信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            询问信息
	 */
	public void showAsk(String title, String message) {
		showDialog(JOptionPane.QUESTION_MESSAGE, title, message);
	}

	/**
	 * 弹出普通信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            普通信息
	 */
	public void showInfo(String title, String message) {
		showDialog(JOptionPane.INFORMATION_MESSAGE, title, message);
	}

	/**
	 * 弹出警告信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            警告信息
	 */
	public void showWarn(String title, String message) {
		showDialog(JOptionPane.WARNING_MESSAGE, title, message);
	}

	/**
	 * 弹出错误信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param exception
	 *            错误信息
	 */
	public void showError(String title, Exception exception) {
		StringWriter out = new StringWriter();
		exception.printStackTrace(new PrintWriter(out));
		String message = out.getBuffer().toString();
		showDialog(JOptionPane.ERROR_MESSAGE, title, message);
	}

	/**
	 * 弹出错误信息对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            错误信息
	 */
	public void showError(String title, String message) {
		showDialog(JOptionPane.ERROR_MESSAGE, title, message);
	}

	private void showDialog(int type, String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, type);
	}

	/**
	 * 显示输入对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            输入提示
	 * @return 输入的内容
	 */
	public String showInput(String title, String message) {
		return JOptionPane.showInputDialog(frame, message, title,
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * 显示文件夹选择对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            选择提示
	 * @param button
	 *            选择按钮
	 * @param initDir
	 *            初始选中目录
	 * @return 选择的文件
	 */
	public File showDir(String title, final String message, String button,
			File initDir) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return true;
			}

			public String getDescription() {
				return message;
			}
		});
		if (initDir != null) {
			chooser.setCurrentDirectory(initDir.getParentFile());
			chooser.setCurrentDirectory(initDir);
		}
		chooser.setMultiSelectionEnabled(false);
		chooser.setDialogTitle(title);
		chooser.setApproveButtonText(button);
		chooser.showOpenDialog(frame);
		return chooser.getSelectedFile();
	}

	/**
	 * 显示文件选择对话框
	 *
	 * @param title
	 *            对话框标题
	 * @param message
	 *            文件类型提示
	 * @param button
	 *            按钮
	 * @param initFile
	 *            初始选中文件
	 * @param suffix
	 *            允许选择的文件后缀
	 * @return 选择的文件
	 */
	public File showFile(String title, final String message, String button,
			File initFile, final String suffix) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				if (suffix != null)
					return f.getName().toLowerCase().endsWith(suffix);
				return true;
			}

			public String getDescription() {
				if (suffix != null)
					return message + "(*" + suffix + ")";
				return message;
			}
		});
		if (initFile != null) {
			chooser.setCurrentDirectory(initFile.getParentFile());
			chooser.setSelectedFile(initFile);
		}
		chooser.setMultiSelectionEnabled(false);
		chooser.setDialogTitle(title);
		chooser.setApproveButtonText(button);
		chooser.showOpenDialog(frame);
		return chooser.getSelectedFile();
	}
}