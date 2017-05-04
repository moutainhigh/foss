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
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/ui/MenuBar.java
 * 
 * FILE NAME        	: MenuBar.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.dataaccess.ISqlExecutor;
import com.deppon.foss.framework.client.component.dataaccess.SqlExecutorFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.module.mainframe.client.action.OpenMenuAction;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;


 /**
     * @功能：
	 * @1、重新启动
	 * @2、退出
	 * @3、调用系统计算器
	 * @4、帮助
	 * @5、关于
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:48:44,content:
 * @author dp-yangtong
 * @date 2012-10-12 上午11:48:44
 * @since
 * @version
 */
public class MenuBar extends JPanel {
	/**
	 * 
	 * 序号
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 创建静态的II18n类型常量i18n
	 */
	private static final II18n i18n = I18nManager.getI18n(MenuBar.class);
	/**
	 * 创建静态的float类型常量divideValue
	 */
//	private static float divideValue=2;

	//log
	private static final Log LOG = LogFactory.getLog(MenuBar.class);
	
//	private MainFrame frame; 
	
	/**
	 * 新加一个构造
	 * zxy 20140423 MANA-2018 
	 */
	public MenuBar(MainFrame frame){
		this();
//		this.frame = frame;
	}
	
	public MenuBar() {
		setSize(NumberConstants.NUMBER_80, NumberConstants.NUMBER_130);// 设置大小
		setLayout(new BorderLayout());// 设置border布局
		/**
		 * 创建JMenuBar对象
		 */
		JMenuBar menuBar = new JMenuBar() {
			/**
			 * 
			 * 序列号
			 * @author 026113-foss-linwensheng
			 * @date 2012-12-18 上午10:03:50
			 */
			private static final long serialVersionUID = 1L;

			Image imgMenubar = ImageUtil.getImage(this.getClass(), "menubar.jpg");		//zxy 20131025 KDTE-1293/DEFECT-1222 新增
			@Override
			protected void paintComponent(Graphics g) {
			    	/**
				 * 设置透明
				 */
				setOpaque(false);
				/**
				 * 调用父类的paintComponent方法进行界面重绘
				 */
				super.paintComponent(g);
				
				//zxy 20131025 KDTE-1293/DEFECT-1222 start 新增:修改背景色
				if(imgMenubar != null){
					g.drawImage(imgMenubar,0, 0,this.getWidth(),this.getHeight(),null);
				}
				//zxy 20131025 KDTE-1293/DEFECT-1222 end 新增:修改背景色
			}
		};

		JMenu systemMenu = new JMenu(i18n.get("menuBar.system")){
			/**
			 * 
			 * 序列号
			 * @author 026113-foss-linwensheng
			 * @date 2012-12-18 上午10:03:50
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * 
			 * 画组件
			 * @author 026113-foss-linwensheng
			 * @date 2012-12-18 上午10:03:50
			 */
			@Override
			protected void paintComponent(Graphics g) {
			    	/**
				 * 设置透明
				 */
				setOpaque(false);
				/**
				 * 调用父类的paintComponent方法进行界面重绘
				 */
				super.paintComponent(g);

			}
		};
		/**
		 * 系统菜单设置透明
		 */
		systemMenu.getPopupMenu().setOpaque(false);
		systemMenu.setMnemonic(KeyEvent.VK_S);// 快捷键
		/**
		 * 系统菜单设置前景色
		 */
		systemMenu.setForeground(Color.white);
		/**
		 * 创建菜单项restartMenuItem
		 */
		restartMenuItem(systemMenu);
		
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {

		/**
		 * 创建菜单项restartMenuItem
		 */
		JMenuItem openModofyPasswordUIMenuItem = new JMenuItem(i18n.get("menuBar.modify"));
		/**
		 * 设置restartMenuItem对象的Accelerator
		 */
		openModofyPasswordUIMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		/**
		 * 将restartMenuItem对象添加至系统菜单
		 */
		systemMenu.add(openModofyPasswordUIMenuItem);
		/**
		 * 为restartMenuItem对象添加监听器
		 */
		openModofyPasswordUIMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 定义全局对象，用来判断该窗口是否已创建，已节约资源
				ModifyPasswordUI panel= new ModifyPasswordUI(ApplicationContext.getApplication().getWorkbench().getFrame());
				panel.setSize(NumberConstants.NUMBER_400, NumberConstants.NUMBER_200);
				// 居中显示弹出窗口
				WindowUtil.center(panel);
				panel.pack();
				panel.setResizable(false);
				panel.setVisible(true);
				
			}
		});
		
		
		
		}
		
		/**
		 * 创建菜单项 日志开关
		 */
		JMenuItem logToggleItem  = new JMenuItem(i18n.get("menuItem.logToggle"));
		systemMenu.add(logToggleItem);
		systemMenu.addSeparator();//添加分隔线
		logToggleItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LogToggleDialog logToggleDialog = new LogToggleDialog(ApplicationContext.getApplication().getWorkbench().getFrame(), "日志开关对话框", true);
				logToggleDialog.setSize(NumberConstants.NUMBER_200, NumberConstants.NUMBER_100);
				// 居中显示弹出窗口
				WindowUtil.center(logToggleDialog);
				logToggleDialog.setVisible(true);
			}
		});
		 
		
		/**
		 * 创建菜单项restartMenuItem
		 */
		JMenuItem optimizedbItem = new JMenuItem(i18n.get("menuBar.optimizedb"));
		/**
		 * 设置restartMenuItem对象的Accelerator
		 */
//		openModofyPasswordUIMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
//				ActionEvent.CTRL_MASK));
		/**
		 * 将restartMenuItem对象添加至系统菜单
		 */
		systemMenu.add(optimizedbItem);
		/**
		 * 为restartMenuItem对象添加监听器
		 */
		optimizedbItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(null, i18n.get("JOptionPane.ui.optimizedb"), "",
						JOptionPane.OK_CANCEL_OPTION);
				
				if (ret == JOptionPane.OK_OPTION) {
					
					ISqlExecutor sqlExecutor2 = SqlExecutorFactory.getSqlExecutor();
					try {
						sqlExecutor2.optimizeDb();
						Thread.sleep(NumberConstants.NUMBER_30000);
					} catch (Exception e1) {
						LOG.error("optimizeDb exception",e1);
					}
					
					
//							final JDialog jf = new JDialog(    ); 
//						
//							/**
//							 * 创建一个常规模式的进度条,其默认为水平方向,最小值为0,最大值为100,初始值为0
//							 */ 
//							
//							jf.getContentPane().setLayout(new BorderLayout());
//							final JProgressBar jpbFileLoadingIndeterminate = new JProgressBar(); 
//							 jpbFileLoadingIndeterminate.setIndeterminate(true); //设置进度条为不确定模式,默认为确定模式  
//							jpbFileLoadingIndeterminate.setStringPainted(true); 
//							
//							jf.getContentPane().add(jpbFileLoadingIndeterminate, BorderLayout.CENTER); 
//							jf.setSize(300, 150); 
//							WindowUtil.center(jf);
//							 jf.setUndecorated(true);        //禁用此窗体的装饰  
//							jf.setVisible(true);
//							
//							SwingUtilities.invokeLater(new Runnable(){
//								
//								public void run(){
//									
//									jpbFileLoadingIndeterminate.setIndeterminate(false); //设置进度条为确定模式,即常规模式,否则那个条还会走来走去  
//									jpbFileLoadingIndeterminate.setString("文件加载完毕..");
//									
//									/**
//									 * 关闭窗体
//									 */ 
//									jf.setVisible(false); //隐藏窗体  
//									jf.dispose();         //释放资源,关闭窗体  
//								  
//									
//								}
//							
//								
//								
//								
//							});
							
							
							
						
						
				
				}
			}
		});
		
		
		systemMenu.addSeparator();
		/**
		 * 创建JMenuItem对象ExitMenuItem
		 */
		JMenuItem exitMenuItem = new JMenuItem(i18n.get("menuBar.exit"));
		/**
		 * 将ExitMenuItem对象添加至系统菜单
		 */
		systemMenu.add(exitMenuItem);
		/**
		 * 为ExitMenuItem对象添加监听器
		 */
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(null, i18n.get("JOptionPane.ui.isExit"), "",
						JOptionPane.OK_CANCEL_OPTION);

				if (ret == JOptionPane.OK_OPTION) {
				ApplicationContext.getApplication().exit();
				}
			}
		});
		/**
		 * 设置ExitMenuItem对象的Accelerator
		 */
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));
		/**
		 * 创建JMenu对象
		 */
		JMenu toolMenu = new JMenu(i18n.get("menBar.tool")){
		    	/**
			 * 序列号
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
			    	/**
				 * 设置透明
				 */
				setOpaque(false);
				/**
				 * 调用父类的paintComponent方法进行界面重绘
				 */
				super.paintComponent(g);

			}
		};
		/**
		 * 设置前景色
		 */
		toolMenu.setForeground(Color.white);
		toolMenu.setMnemonic(KeyEvent.VK_T);// 快捷键
		/**
		 * 创建JMenuItem对象calculatorMenuItem
		 */
		JMenuItem calculatorMenuItem = new JMenuItem(
				i18n.get("menuBar.calculator"));
		/**
		 * 为toolMenu添加元素calculatorMenuItem
		 */
		toolMenu.add(calculatorMenuItem);
		/**
		 * 为calculatorMenuItem设置背景
		 */
		calculatorMenuItem.setBackground(Color.white);
		/**
		 * 设置calculatorMenuItem对象Accelerator属性
		 */
		calculatorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
				ActionEvent.CTRL_MASK));
		/**
		 * 调用calculatorMenuItem对象addActionListener()方法
		 */
		calculatorMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    	/**
				 * 创建定时器
				 */
				Timer tm = new Timer();
				/**
				 * 200毫秒后执行
				 */
				tm.schedule(new Calculator.MyTimerTask(), NumberConstants.NUMBER_200);
			}
		});
		/**
		 * 创建JMenuItem对象
		 *//*
		JMenuItem messageMenuItem = new JMenuItem("消息（不锁定）");
		*//**
		 * 设置快捷键
		 *//*
		toolMenu.setMnemonic(KeyEvent.VK_T);// 快捷键
		*//**
		 * 为toolMenu添加元素messageMenuItem
		 *//*
		toolMenu.add(messageMenuItem);
		*//**
		 * 为messageMenuItem设置背景
		 *//*
		messageMenuItem.setBackground(Color.white);
		*//**
		 * 为messageMenuItem添加监听器
		 *//*
		messageMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    	*//**
			    	 * 创建右下角弹出式提示框
			    	 *//*
				new RightCornerPopMessage();

			}
		});
		*//**
	    	 * 创建JMenuItem对象
	    	 *//*
		JMenuItem messageLockMenuItem = new JMenuItem("消息（锁定）");
		toolMenu.setMnemonic(KeyEvent.VK_T);// 快捷键
		*//**
	    	 * 为toolMenu添加元素messageLockMenuItem
	    	 *//*
		toolMenu.add(messageLockMenuItem);
		*//**
		 * 设置messageLockMenuItem背景
		 *//*
		messageLockMenuItem.setBackground(Color.white);
		*//**
		 * 设置messageLockMenuItem监听器
		 *//*
		messageLockMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    	//显示消息
				MsgBox.showInfo2("消息。。。。。。。。。", "系统消息");

			}
		});*/
		/**
		 * 设置是否绘制边框
		 */
		menuBar.setBorderPainted(false);
		/**
		 * 为menuBar添加元素systemMenu，toolMenu和helpMenu
		 */
		menuBar.add(systemMenu);
		menuBar.add(toolMenu);
		/**
		 * 创建JPanel对象menuBarPanel
		 */
		JPanel menuBarPanel = new JPanel() {
		    	/**
		    	 * 序列号
		    	 */
			private static final long serialVersionUID = 1L;
			/**
			 * 
			 * 画组件
			 * @author 026113-foss-linwensheng
			 * @date 2012-12-18 上午10:03:50
			 */
			@Override
			protected void paintComponent(Graphics g) {
			    	/**
			    	 * 设置透明
			    	 */
				setOpaque(false);
//				Graphics2D g2 = (Graphics2D) g;
//				GradientPaint p = new GradientPaint(0, 0,new Color(55,60,100),0,
//						getHeight()/divideValue,  new Color(85,92,120));
//
//				Paint oldPaint = g2.getPaint();
//				g2.setPaint(p);
//				g2.fillRect(0, 0, getWidth(), (int) (getHeight()/divideValue));
//				
//				GradientPaint p2 = new GradientPaint(0, getHeight()/divideValue-4,new Color(55,60,100),0,
//						getHeight()/divideValue-7, new Color(85,92,120));	
//				g2.setPaint(p2);
//				g2.fillRect(0, (int) (getHeight()/divideValue-1), getWidth(), getHeight());		
//				g2.setPaint(oldPaint);
				/**
				 * 调用父类的paintComponent方法进行界面重绘
				 */
				super.paintComponent(g);

			}

		};
		/**
		 * 为菜单面板添加元素
		 */
		menuBarPanel.add(menuBar);
		/**
		 * 创建JLabel对象photoLable
		 */
		JLabel photoLable = new JLabel();
		/**
		 * 为photoLable设置图标
		 */
		photoLable.setIcon(ImageUtil.getImageIcon(this.getClass(),
				"deppon-logo.png"));
		/**
		 * 为photoLable设置边框
		 */
		photoLable.setBorder(null);

//		add(photoLable, BorderLayout.EAST);
		/**
		 * 为menuBarPanel设置边框
		 */
		menuBarPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, NumberConstants.NUMBER_4, 0));
		/**
		 * 为当前对象添加组件 
		 */
		add(menuBarPanel, BorderLayout.WEST);
		/**
		 * 更新界面
		 */
		updateUI();
	}

	private void restartMenuItem(JMenu systemMenu) {
		/**
		 * 创建菜单项restartMenuItem
		 */
		JMenuItem restartMenuItem = new JMenuItem(i18n.get("menuBar.restart"));
		/**
		 * 设置restartMenuItem对象的Accelerator
		 */
		restartMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		/**
		 * 将restartMenuItem对象添加至系统菜单
		 */
		systemMenu.add(restartMenuItem);
		/**
		 * 为restartMenuItem对象添加监听器
		 */
		restartMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List list = OpenMenuAction.openMenuList;
				if(list!=null && list.size()>0){
					for(int i = 0;i<list.size();i++){
						IEditor editor =(IEditor) list.get(i);
						if(editor!=null && !editor.isClosed()){
							editor.close();
						}
					}
				}
				//zxy 20140504 MANA-2019 修改:注销此段，功能房在Application中
				//zxy 20140423 MANA-2018 start 新增:注销系统时 客户端强制终止
//				List<ITaskContext> taskCxtLst = frame.getTaskService().getTaskContexts();
//				if(taskCxtLst != null){
//					for(int i = 0; i < taskCxtLst.size(); i++){
//						ITaskContext taskContext = taskCxtLst.get(i);
//						taskContext.setForceCancel(true);
//					}
//				}
				//zxy 20140423 MANA-2018 end 新增:注销系统时  客户端强制终止
			    	/**
				 * 重启应用
				 */
				ApplicationContext.getApplication().restart();
			}
		});
	}
	
	
	/**
	 * 
	 * 更新菜单
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void updateUI()
	{
	    	/**
		 * 向集合UIManager添加元素
		 */
		UIManager.put( "MenuUI" ,  "javax.swing.plaf.basic.BasicMenuUI" );
	}
	/**
	 * 
	 * 换组件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	protected void paintComponent(Graphics g) {
	    	/**
		 * 设置透明
		 */
		setOpaque(false);
//		Graphics2D g2 = (Graphics2D) g;
//		GradientPaint p = new GradientPaint(0, 0,new Color(55,60,100),0,
//				getHeight()/divideValue,  new Color(85,92,120));
//
//		Paint oldPaint = g2.getPaint();
//		g2.setPaint(p);
//		g2.fillRect(0, 0, getWidth(), (int) (getHeight()/divideValue));
//		
//		GradientPaint p2 = new GradientPaint(0, getHeight()/divideValue-4,new Color(55,60,100),0,
//				getHeight()/divideValue-7, new Color(85,92,120));	
//		g2.setPaint(p2);
//		g2.fillRect(0, (int) (getHeight()/divideValue-1), getWidth(), getHeight());		
//		g2.setPaint(oldPaint);
		/**
		 * 调用父类的paintComponent方法进行界面重绘
		 */
		super.paintComponent(g);
		

	}

	
}