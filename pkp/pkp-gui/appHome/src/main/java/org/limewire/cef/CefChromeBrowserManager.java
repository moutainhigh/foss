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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/CefChromeBrowserManager.java
 * 
 * FILE NAME        	: CefChromeBrowserManager.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

/**
 * Chrome browser 调用帮助类
 * 
 * @author niujian
 * 
 */
public class CefChromeBrowserManager {

	static {
		CefContext.initialize(null);
	}
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory
			.getLog(CefChromeBrowserManager.class);

	/**
	 * 得到对象
	 * 
	 * @param url
	 * @param displayControl
	 * @return
	 * @throws Exception
	 */
	public static ChromeBrowser getChromeBrowser(String url,
			boolean displayControl) throws Exception {
		// return ChromeBrowser.getInstance(url,displayControl);
		ChromeBrowser browser = new ChromeBrowser(url);
		browser.setForseToNPaint(true);
		browser.setControlVisible(displayControl);
		return browser;
	}

	/**
	 * 得到对象
	 * 
	 * @param url
	 * @param displayControl
	 * @param cefcookie
	 * @return
	 * @throws Exception
	 */
	public static ChromeBrowser getChromeBrowser(String url,
			boolean displayControl, CefCookie cefcookie) throws Exception {
		// return ChromeBrowser.getInstance(url,displayControl);
		ChromeBrowser browser = new ChromeBrowser(url, cefcookie);
		browser.setForseToNPaint(true);
		browser.setControlVisible(displayControl);
		return browser;
	}

	/**
	 * 弹出浏览器对话框
	 * 
	 * @param url
	 * @param width
	 * @param height
	 * @param callBack
	 * @param displayControl
	 * @throws Exception
	 */
	public static JDialog PopChromeBrowserAsDialog(String url, int width,
			int height, IChromeBrowserCallBack callBack, boolean displayControl)
			throws Exception {
		final ChromeBrowser browser = getChromeBrowser(url, displayControl);
		final String f_url = url; 
		browser.setSize(new Dimension(width, height));
		browser.setCallBack(callBack);
		JDialog dialog = new JDialog();
		dialog.setTitle("Deppon Foss");
		ImageIcon imgicon = new ImageIcon(browser.getClass().getResource("/org/limewire/cef/images/dpfossguib.png"));
		dialog.setIconImage(imgicon.getImage());
		dialog.setModal(true);
		dialog.getContentPane().add(browser);
		browser.setParent(dialog);
		dialog.setSize(new Dimension(width, height));
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		double width1 = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		dialog.setLocation((int) (width1 - width) / 2, 10);

		try {
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try{
						browser.resetBrowser(f_url, null);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			});						
			dialog.setVisible(true);
		} catch (Exception e) {// 改sonar 不用throwable
			LOG.error("exception", e);
		}
		return dialog;
	}

	/**
	 * 测试方法
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		try{
		
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
	
			JButton btn = new JButton("Chrome Browser");
			btn.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent e) {
					try {
	
						IChromeBrowserCallBack myCallBack = new IChromeBrowserCallBack() {
	
							public boolean doCallBack(String[] params)
									throws Exception {
								System.out.println("test call back ");
								return true;
							}
						};
	
						//String url = "http://gis.deppon.com/gis-biz/biz-destination/index.action";
						String url = "http://foss.deppon.com/gis-biz/biz-destination/index.action";
	
						CefChromeBrowserManager.PopChromeBrowserAsDialog(url, 1200,	700, myCallBack, true);
					} catch (Exception exp) {
						LOG.error("exception", exp);
					}
				}
			});
			panel.add(btn);
			frame.getContentPane().add(panel);
			frame.setSize(new Dimension(200, 200));		
			frame.setVisible(true);
		}catch (Exception e) { LOG.error("exception", e); }
	}
}