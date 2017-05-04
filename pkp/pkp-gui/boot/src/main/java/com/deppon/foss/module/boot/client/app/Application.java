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
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/app/Application.java
 * 
 * FILE NAME        	: Application.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.app;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;
import org.java.plugin.PluginLifecycleException;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;

import com.deppon.foss.framework.client.commons.exception.IErrorHandler;
import com.deppon.foss.framework.client.commons.exception.NormalException;
import com.deppon.foss.framework.client.commons.exception.SevereException;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.commons.task.ITaskService;
import com.deppon.foss.framework.client.commons.task.impl.TaskService;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.dataaccess.ISqlExecutor;
import com.deppon.foss.framework.client.component.dataaccess.SqlExecutorFactory;
import com.deppon.foss.framework.client.component.exception.ErrorHandler;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.component.task.TaskForegroundDialog;
import com.deppon.foss.framework.client.core.application.ApplicationEvent;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationListener;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.jpf.utils.JpfUtils;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.core.workbench.IWorkbench;
import com.deppon.foss.framework.client.core.workbench.impl.synthetica.DockableFrame;
import com.deppon.foss.module.boot.client.autorun.AutoRun;
import com.deppon.foss.module.boot.client.autorun.AutoRunPhase;
import com.deppon.foss.module.boot.client.autorun.AutoRunUtil;
import com.deppon.foss.module.boot.client.plugin.PluginInjectUtil;
import com.deppon.foss.module.boot.client.util.OSUtil;
import com.deppon.foss.module.login.shared.hessian.ILoginHessianRemoting;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

/**
 * 客户端GUI启动的Application对象
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:
 * 2012-10-12 上午9:27:43
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午9:27:43
 * @since
 * @version
 */
public class Application implements IApplication, org.java.plugin.boot.Application {
	/**
	 * 日志对象
	 */
	public static final Log LOG = LogFactory.getLog(Application.class);
	/**
	 * 工作台
	 */
	private IWorkbench workbench;
	/**
	 * 插件
	 */
	private ApplicationPlugin appPlugin;
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(Application.class);
	/**
	 * Map集合
	 */
	private Map<Object, Object> attributes;
	/**
	 * 任务管理服务
	 */
	private ITaskService taskService;
	/**
	 * 异常处理接口
	 */
	private IErrorHandler errorHandler;
	/**
	 * 定义布尔型变量
	 */
	private boolean enableAutoRuns;
	/**
	 * JFrame窗口子类
	 */
	private DockableFrame frame;
	
	private static JWindowStart splash;
	
	private static final int NUMBER_TNE = 10;
	private static final int NUMBER_NINETY_NINE = 99;
	private static final int NUMBER_ONE_HUNDRED_TWENTY = 120;
	
	/**
	 * 启动之后程序中的线程池，注销时销毁
	 */
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private ILoginHessianRemoting service =  DefaultRemoteServiceFactory
			.getService(ILoginHessianRemoting.class);
	/**
	 * 构造方法
	 */
	public Application() {
		this(null);
	}

	public ExecutorService getExecutorService2() {
		return executorService;
	}
	
	public static ExecutorService getExecutorService() {
		return ((Application)ApplicationContext.getApplication()).getExecutorService2();
	}


	/**
	 * 构造方法
	 * 
	 * @param appPlugin
	 */
	public Application(ApplicationPlugin appPlugin) {
		/**
		 * 应用插件
		 */
		this.appPlugin = appPlugin;
		/**
		 * 属性列表
		 */
		attributes = new HashMap<Object, Object>();
		/**
		 * 任务服务
		 */
		taskService = new TaskService();
		/**
		 * 新增一个任务Dialog
		 */
		new TaskForegroundDialog(taskService);
		/**
		 * 获取错误连接器
		 */
		errorHandler = new ErrorHandler(this);
		/**
		 * 是否自动运行设置为可自动运行
		 */
		enableAutoRuns = true;
	}

	/**
	 * 设置插件
	 * 
	 * @param appPlugin
	 */
	public void setApplicationPlugin(ApplicationPlugin appPlugin) {
		/**
		 * 设置应用插件
		 */
		this.appPlugin = appPlugin;
	}

	/**
	 * 异常处理类
	 * 
	 * @author 026123-foss-lifengteng
	 * 
	 */
	private class ThreadErrorHandler implements Thread.UncaughtExceptionHandler {
	    	/**
		 * 异常处理接口
		 */
		private IErrorHandler errorHandler;

		public ThreadErrorHandler(IErrorHandler errorHandler) {
		    	/**
			 * 将参数errorHandler的值赋给属性errorHandler
			 */
			this.errorHandler = errorHandler;
		}

		/**
		 * 
		 * 所有抛出的未被捕获的异常，使用错误拦截器进行拦截
		 * 
		 * @param t
		 * @param e
		 * @author 
		 *         026113-foss-linwensheng
		 * @date 2013-3-17 下午4:16:18
		 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread,
		 *      java.lang.Throwable)
		 */
		public void uncaughtException(Thread t, Throwable e) {
		    	/**
		    	 * 日志记录
		    	 */
			LOG.error("throwable catch in application", e);
			errorHandler.handle(e);
		}
	}

	/**
	 * 启动应用
	 */
	public void startApplication() throws Exception {
		
		//SplashWindow.splash();
		/**
		 * UI管理器，设置皮肤为windows风格
		 */
		UIManager.setLookAndFeel(new WindowsLookAndFeel());
		
		splash = new  JWindowStart();
		splash.setVisible(true); 
		splash.setAlwaysOnTop(false);
		splash.getProgress().setValue(splash.getProgress().getValue() + 1); // 设置进度条值
		/**
		 * 在线程中设置默认未被捕获的异常拦截器
		 */
		Thread.setDefaultUncaughtExceptionHandler(new ThreadErrorHandler(errorHandler));
		try {

			/**
			 * 做启动应用
			 */
			doStartApplication(splash);

		} catch (Exception t) {
			/**
			 * 对抛出的异常使用错误拦截器进行拦截
			 */
			errorHandler.handle(new SevereException(t));
		} catch (Error t) {
			/**
			 * 对抛出的错误使用错误拦截器进行拦截
			 */
			errorHandler.handle(new SevereException(t));
		}
	}

	/**
	 * 启动应用
	 */
	private void doStartApplication(JWindowStart splash ) {
		try {
			//获取mac地址
			InetAddress inetAddress = OSUtil.getLocalIP();
			String mac = OSUtil.getLocalMac(inetAddress);
			LOG.info("登录系统 主机IP地址:"+inetAddress+" ; MAC地址："+mac);
		} catch (Exception e) {
			LOG.warn("获取ip或mac地址失败。");
		}
		
		try {
			/**
			 * 设置字体为微软雅黑
			 */
			//SyntheticaLookAndFeel.setFont("微软雅黑", 14);
			splash.getProgress().setValue(splash.getProgress().getValue() + NUMBER_TNE); // 设置进度条值
		} catch (Exception e) {
			splash.dispose();
			/**
			 * 如果抛出异常，使用异常拦截器拦截
			 */
			errorHandler.handle(new NormalException(e));
		}
		//SplashWindow.disposeSplash();
		/**
		 * 插件注入
		 */
		PluginInjectUtil.inject(this);
		/**
		 * 自动运行
		 */
		List<AutoRun> autoRuns = AutoRunUtil.scanAutoRuns(this);
		for(AutoRun auto : autoRuns){
			if(AutoRunPhase.BEFORE_LOGIN.equals(auto.phase)){
				auto.hiddenDialog= true;
			}
		}
		AutoRunUtil.executeAutoRuns(autoRuns, AutoRunPhase.BEFORE_LOGIN);
		splash.getProgress().setValue(splash.getProgress().getValue() + NUMBER_NINETY_NINE); // 设置进度条值
		try {
			Thread.sleep(NUMBER_ONE_HUNDRED_TWENTY);
		} catch (InterruptedException e) {
			LOG.error(e);
		}
		splash.dispose(); // 释放窗口
		/**
		 * 弹出登陆框
		 */
		boolean login = login();
		if (login) {
			/**
			 * 登陆成功后，弹出主页面
			 */
			showMainWindow();
			/**
			 * 执行登陆后的任务
			 */
			AutoRunUtil.executeAutoRuns(autoRuns, AutoRunPhase.AFTER_LOGIN);

			/**
			 * 开始事件-加入监听
			 */
			started();
		}

	}

	/**
	 * 开始事件
	 */
	private void started() {
		/**
		 * 获取监听器
		 */
		for (IApplicationListener listener : appListeners.values()) {
			/**
			 * 加入系统事件事件
			 */
			listener.onStarted(new ApplicationEvent(this));
		}
	}
	/**
	 * 创建一个AtomicBoolean类型的变量，默认值为false
	 */
	public AtomicBoolean isLogin = new AtomicBoolean(false);

	/**
	 * 登录
	 * 
	 * @return
	 */
	private boolean login() {

		/**
		 * 拿到登陆扩展点
		 */
		ExtensionPoint extensionPoint = appPlugin.getDescriptor().getExtensionPoint("login");
		/**
		 * 获取到扩展点列表
		 */
		List<Extension> extensions = new ArrayList<Extension>(extensionPoint.getConnectedExtensions());

		/**
		 * 遍历登陆扩展点
		 */
		for (Extension extension : extensions) {
			/**
			 * 拿到登陆扩展点登陆类
			 */
			String cls = extension.getParameter("login-class").valueAsString();
			/**
			 * 判断变量cls是否为空或是否为空字符串
			 */
			if (cls == null || "".equals(cls)) {
			    	/**
				 * 如果变量cls为空或为空字符串，则继续循环下一条记录
				 */
				continue;
			}
			try {
				/**
				 * 实例化该类
				 */
				Object obj = JpfUtils.createInstance(appPlugin.getManager().getPlugin(extension.getDeclaringPluginDescriptor().getId()), cls);
				/**
				 * 如果实现了登陆接口
				 */
				if (obj instanceof ILogin) {
					ILogin login = (ILogin) obj;
					/**
					 * 调用登陆方法
					 */
					return login.login();
				}

				/**
				 * 捕获异常
				 */
			} catch (Exception e) {
				/**
				 * 记录错误日志
				 */
				LOG.error("throwable catch in application", e);
			}
		}

		return false;
	}

	/**
	 * 创建面板
	 * 
	 * @return
	 */
	private DockableFrame createMainFrame() {
		/**
		 * 获取gui-mainFrame （主页面）扩展点
		 */
		ExtensionPoint extensionPoint = appPlugin.getDescriptor().getExtensionPoint("gui-mainFrame");
		List<Extension> extensions = new ArrayList<Extension>(extensionPoint.getAvailableExtensions());
		/**
		 * 遍历扩展点
		 */
		for (Extension extension : extensions) {
			/**
			 * 拿到扩展点名称
			 */
			String className = extension.getParameter("mainFrame-class").valueAsString();
			Object obj = null;
			try {
				/**
				 * 实例化类
				 */
				obj = JpfUtils.createInstance(appPlugin.getManager().getPlugin(extension.getDeclaringPluginDescriptor().getId()), className);
				Class<?> clazz = obj.getClass();
				/**
				 * 获取init方法
				 */
				Method initMethod = clazz.getMethod(extension.getParameter("init-method").valueAsString());
				try {
					/**
					 * 调用该方法
					 */
					initMethod.invoke(obj);
				} catch (IllegalArgumentException e) {
					/**
					 * 记录日志
					 */
					LOG.error("throwable catch in application", e);
				} catch (IllegalAccessException e) {
					/**
					 * 记录日志
					 */
					LOG.error("throwable catch in application", e);
				} catch (InvocationTargetException e) {
					/**
					 * 记录日志
					 */
					LOG.error("throwable catch in application", e);
				}
				/**
				 * 强制转换为DockableFrame类实例
				 */
				DockableFrame dockableFrame = (DockableFrame) obj;
				return dockableFrame;
			} catch (NoSuchMethodException e) {
				/**
				 * 记录日志
				 */
				LOG.error("throwable catch in application", e);
			} catch (PluginLifecycleException e) {
				/**
				 * 记录日志
				 */
				LOG.error("throwable catch in application", e);
			}

		}
		return null;
	}

	/**
	 * 展现面板
	 */
	private void showMainWindow() {
		/**
		 * 拿到创建出来的主框架对象
		 */
		frame = createMainFrame();

		/**
		 * 拿到工作台
		 */
		workbench = frame.getWorkbench();
		/**
		 * 设置标题
		 */
		frame.setTitle(i18n.get("FOSS"));
		/**
		 * 设置图标
		 */
		frame.setIconImage(ImageUtil.getImage(this.getClass(), "app.gif"));

		/**
		 * 重现装置布局
		 */
		workbench.restoreLayouts();
		/**
		 * 设置工作空间中的view或者editor是否可以被移动
		 * setFrozen
		 */
		workbench.setFrozen(true);

		/**
		 * 如果是SyntheticaRootPaneUI，
		 * 设置最大显示
		 */
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		/**
		 * 设置主框架界面居中显示
		 */
		WindowUtil.centerAndShow(frame);
		frame.setVisible(true);
	}

	/**
	 * 设置属性
	 */
	public void setAttribute(Object key, Object value) {
		attributes.put(key, value);
	}

	/**
	 * 得到属性
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributes.get(key);
	}

	/**
	 * 得到属性
	 */
	public Map<Object, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}


	/**
	 * 重启
	 */
	public void restart() {
		
		hideFrame();
		String token = "";
		String uuid =(String)SessionContext.get("_TOKEN_UUID"); 
		try{
			service.keepSessionUuid(token,uuid);
		}catch(Exception e){
			LOG.error("request server error,please check server is running!",e);
		}
		new Thread("Restart"){
			public void run() {
				/**
				 * 自动运行
				 */
				List<AutoRun> autoRuns = AutoRunUtil.scanAutoRuns(Application.this);
				/**
				 * 弹出登陆框
				 */
				boolean login = login();
				if (login) {
					/**
					 * 登陆成功后，弹出主页面
					 */
					showMainWindow();
					/**
					 * 执行登陆后的任务
					 */
					AutoRunUtil.executeAutoRuns(autoRuns, AutoRunPhase.AFTER_LOGIN);

					/**
					 * 开始事件-加入监听
					 */
					started();
				}
			}
		}.start();
		
	}

	/**
	 * 隐藏窗口
	 */
	private void hideFrame() {
		/**
		 * 判断frame是否为空
		 */
		if (frame != null) {
		    	/**
			 * 如果不为空，则设置Visible为false
			 * 调用dispose()方法
			 */
			frame.setVisible(false);
			frame.dispose();
		}

		//释放启动后添加的所有线程
		executorService.shutdownNow();
		
		//zxy 20140423 MANA-2019 start 新增:注销系统时 客户端强制终止
		List<ITaskContext> taskCxtLst = frame.getWorkbench().getTaskService().getTaskContexts();
		if(taskCxtLst != null){
			for(int i = 0; i < taskCxtLst.size(); i++){
				ITaskContext taskContext = taskCxtLst.get(i);
				taskContext.setForceCancel(true);
			}
		}
		//zxy 20140423 MANA-2019 end 新增:注销系统时  客户端强制终止
		
		//由于关闭的线程池无法再次重启，需要重新建一个
		executorService = Executors.newCachedThreadPool();
		
	}

	/**
	 * 重启
	 */
	private void restartLauncher(String launcherHome) {
	    	/**
		 * 创建一个文件对象
		 */
		File fLauncherHome = new File(launcherHome);
		/**
		 * 判断文件是否存在
		 */
		if (!fLauncherHome.exists()) {
		    	/**
			 * 如果不存在，则重启应用
			 */
			restartApplication();
			return;
		}
		/**
		 * 通过拿到launcherHome路径，获取拿到command
		 */
		String command = ApplicationCommand.getStartLauncherCommand(launcherHome);
		try {
		    	/**
			 * 启动
			 */
			Runtime.getRuntime().exec(command, null, ApplicationCommand.getLauncherHome(fLauncherHome));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(workbench.getFrame(), "Can't restart application.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 重启应用
	 */
	private void restartApplication() {
		/**
		 * 通过拿到home路径，获取拿到command
		 */
		String home = System.getProperty("foss_home");
		String command = ApplicationCommand.getStartApplicationCommand(home);

		try {
			/**
			 * 启动
			 */
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(workbench.getFrame(), "Can't restart application.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 系统关闭
	 */
	public void exit() {
	    String token = "";
		String uuid =(String)SessionContext.get("_TOKEN_UUID"); 
		//zxy 20131010 BUG-56723 start 修改：退出的时候，如果连不上服务或者请求异常 不再往外抛
		try{
			service.keepSessionUuid(token,uuid);
		}catch(Exception e){
			LOG.error("request server error,please check server is running!",e);
		}
		//zxy 20131010 BUG-56723 end 修改：退出的时候，如果连不上服务或者请求异常 不再往外抛
		
		try { 
			hideFrame();
			
			ISqlExecutor sqlExecutor = SqlExecutorFactory.getSqlExecutor();
			sqlExecutor.closeHqsql();
			//zxy 20131010 BUG-56723 start 修改：退出的时候，如果连不上服务或者请求异常 不再往外抛
			try{
				service.keepSessionUuid(token,uuid);
			}catch(Exception e){
				LOG.error("request server error,please check server is running!",e);
			}
			//zxy 20131010 BUG-56723 end 修改：退出的时候，如果连不上服务或者请求异常 不再往外抛
			
			
		} catch (Exception e1) {
			LOG.error("shutdown exception",e1);
		}
		/*
		 * for (IApplicationListener
		 * listener :
		 * appListeners.values()) {
		 * listener.onExiting(new
		 * ApplicationEvent(this)); }
		 */
		System.exit(0);

	}

	/**
	 * 得到根目录
	 */
	public String getHome() {
	    	/**
		 * 获取配置文件中foss_home
		 */
		return System.getProperty("foss_home");
	}

	/**
	 * 
	 * 获取到插件路径
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:49:30
	 */
	public String getPluginsHome() {
	    	/**
		 * 获取配置文件中foss_plugins_home
		 */
		return System.getProperty("foss_plugins_home");
	}

	/**
	 * 
	 * 获取是否可以自动运行
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:49:50
	 * @see com.deppon.foss.framework.client.core.application.IApplication#isAutoRunsEnabled()
	 */
	@Override
	public boolean isAutoRunsEnabled() {
		return enableAutoRuns;
	}

	/**
	 * 
	 * 设置是否可以自动运行
	 * 
	 * @param enableAutoRuns
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:50:06
	 * @see com.deppon.foss.framework.client.core.application.IApplication#setEnableAutoRuns(boolean)
	 */
	@Override
	public void setEnableAutoRuns(boolean enableAutoRuns) {
		this.enableAutoRuns = enableAutoRuns;
	}

	/**
	 * 
	 * 获取应用插件
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:50:23
	 */
	public ApplicationPlugin getApplicationPlugin() {
		return appPlugin;
	}

	/**
	 * 
	 * 获取工作台
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:50:37
	 * @see com.deppon.foss.framework.client.core.application.IApplication#getWorkbench()
	 */
	@Override
	public IWorkbench getWorkbench() {
		return workbench;
	}

	/**
	 * 实例化应用监听器列表
	 */
	private Map<Object, IApplicationListener> appListeners = new HashMap<Object, IApplicationListener>();

	/**
	 * 
	 * 增加系统监听
	 * 
	 * @param listener
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:51:18
	 * @see com.deppon.foss.framework.client.core.application.IApplication#addApplicationListener(com.deppon.foss.framework.client.core.application.IApplicationListener)
	 */
	@Override
	public void addApplicationListener(IApplicationListener listener) {
	    	/**
		 * 判断listener是否为空
		 */
		if (listener == null) {
		    	/**
			 * 如果listener为空，则抛出异常
			 */
			throw new NullPointerException("listener is null....");
		}
		/**
		 * 向Map集合appListeners添加信息
		 */
		appListeners.put(listener.getClass(), listener);
	}

	/**
	 * 
	 * 移除应用系统监听器
	 * 
	 * @param listener
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:51:46
	 * @see com.deppon.foss.framework.client.core.application.IApplication#removeApplicationListener(com.deppon.foss.framework.client.core.application.IApplicationListener)
	 */
	@Override
	public void removeApplicationListener(IApplicationListener listener) {
	    	/**
		 * 判断参数listener是否为空
		 */
		if (listener == null) {
		    	/**
			 * 如果参数listener为空，则返回
			 */
			return;
		}
		/**
		 * 如果参数listener不为空，则删除appListeners集合中的元素listener.getClass()
		 */
		appListeners.remove(listener.getClass());
	}

	/**
	 * 
	 * 获取编辑器
	 * 
	 * @param config
	 * @param editorClass
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:57:51
	 * @see com.deppon.foss.framework.client.core.application.IApplication#openEditor(com.deppon.foss.framework.client.core.workbench.EditorConfig,
	 *      java.lang.String)
	 */
	public void openEditor(EditorConfig config, String editorClass) {

		/**
		 * 打开编辑器
		 */
		openUI(config, editorClass);
	}

	/**
	 * 打开编辑器
	 * 
	 * @param config
	 * @param editorClass
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午4:58:18
	 */
	@SuppressWarnings("rawtypes")
	private IEditor openUI(EditorConfig config, String editorClass) {
		/**
		 * 获取工作台的编辑器
		 */
		List<IEditor> editors = workbench.getEditors();
		/**
		 * 如果编辑器不为空
		 */
		if (editors != null) {
			/**
			 * 遍历编辑器
			 */
			for (IEditor editor : editors) {
				/**
				 * 获取编辑器配置等于传入的编辑器配置
				 */
				if (editor.getConfig() == config) {
					/**
					 * 设置编辑器设置前置
					 */
					editor.toFront();
					return editor;
				}
			}
		}
		/**
		 * 如果为空，新建一个编辑器
		 */
		final IEditor editor = workbench.createEditor(config);
		try {
			/**
			 * 同过插件管理器获取插件
			 */
			Plugin plugin = this.getApplicationPlugin().getManager().getPlugin(config.getPluginId());

			/**
			 * 获取class装置器
			 */
			ClassLoader loader = this.getApplicationPlugin().getManager().getPluginClassLoader(plugin.getDescriptor());
			/**
			 * 在class装置器中拿到该class
			 */
			Class clz = loader.loadClass(editorClass);
			/**
			 * 设置该编辑器需要显示的控件
			 */
			editor.setComponent((JComponent) clz.newInstance());

			/**
			 * 捕获异常
			 */
		} catch (Exception e) {
			/**
			 * 抛出一般异常
			 */
			LOG.error("throwable catch in application", e);
			throw new NormalException(e);
		}

		/**
		 * 线程同步JYTabbedPane
		 */
		synchronized (JTabbedPane.class) {
			editor.setVisible(true);
		}

		return editor;
	}

	/**
	 * 
	 * 代替编辑器和返回编辑器
	 * 
	 * @param config
	 * @param editorClass
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午5:06:57
	 * @see com.deppon.foss.framework.client.core.application.IApplication#openEditorAndRetrun(com.deppon.foss.framework.client.core.workbench.EditorConfig,
	 *      java.lang.String)
	 */
	@Override
	public IEditor openEditorAndRetrun(EditorConfig config, String editorClass) {
		//  Auto-generated method stub
	    	/**
		 * 打开编辑器
		 */
		return openUI(config, editorClass);
	}

	/**
	 * 
	 * 设置工作平台
	 * 
	 * @param workbench
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午5:07:20
	 * @see com.deppon.foss.framework.client.core.application.IApplication#setWorkbench(com.deppon.foss.framework.client.core.workbench.IWorkbench)
	 */
	@Override
	public void setWorkbench(IWorkbench workbench) {
		this.workbench = workbench;
	}

	/**
	 * 
	 * 打开界面
	 * 
	 * @param config
	 * @param comp
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-17 下午5:07:35
	 * @see com.deppon.foss.framework.client.core.application.IApplication#openUI(com.deppon.foss.framework.client.core.workbench.EditorConfig,
	 *      javax.swing.JComponent)
	 */
	@Override
	public IEditor openUI(EditorConfig config, JComponent comp) {
		/**
		 * 获取工作台的所有编辑器
		 */
		List<IEditor> editors = workbench.getEditors();
		/**
		 * 判断编辑器editors是否为空
		 */
		if (editors != null) {
		    	/**
			 * 循环遍历editors
			 */
			for (IEditor editor : editors) {
			    	/**
				 * 判断参数config和editor.getConfig()是否相等
				 */
				if (editor.getConfig() == config) {
				    	/**
				    	 * 设置此IEditor对象前段显示
				    	 */
					editor.toFront();
					return editor;
				}
			}
		}
		/**
		 * 创建一个IEditor类型的常量
		 */
		final IEditor editor = workbench.createEditor(config);
		/**
		 * 设置IWindow所持有的Swing控件
		 */
		editor.setComponent(comp);
		/**
		 * 调用同步方法synchronized(),使JYTabbedPane.class被锁定
		 */
		synchronized (JTabbedPane.class) {
		    	/**
			 * 设置Visible为ture
			 */
			editor.setVisible(true);
		}

		return editor;

	}

	/**
	 * @return the splash
	 */
	public static JWindowStart getSplash() {
		return splash;
	}

	
	
}