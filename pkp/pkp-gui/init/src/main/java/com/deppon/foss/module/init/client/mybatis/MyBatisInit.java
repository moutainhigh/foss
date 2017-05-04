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
 * FILE PATH        	: init/src/main/java/com/deppon/foss/module/init/client/mybatis/MyBatisInit.java
 * 
 * FILE NAME        	: MyBatisInit.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.init.client.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.java.plugin.Plugin;
import org.java.plugin.registry.PluginDescriptor;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.commons.task.TaskSupport;
import com.deppon.foss.framework.client.component.dataaccess.ClassPathFileScanner;
import com.deppon.foss.framework.client.component.dataaccess.DefaultTransactionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.application.ApplicationEvent;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.application.IApplicationListener;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.module.boot.client.app.Application;
import com.deppon.foss.module.boot.client.autorun.IAutoRunner;


/**
 * Mybatis初始化类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:41:46, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:41:46
 * @since
 * @version
 */
public class MyBatisInit extends TaskSupport implements IAutoRunner, IPluginAware, IApplicationAware {

    	/**
    	 * 从日志工厂获取日志
    	 */
	private static final Log LOG = LogFactory.getLog(MyBatisInit.class);
	
	private II18n i18n = I18nManager.getI18n(MyBatisInit.class);
	/**
	 * 定义一个插件对象
	 */
	private Plugin plugin;
	/**
	 * 定义一个字符串
	 */
	private String suffix = "mapper.xml";
	/**
	 * 
	 * 执行加载myBatis映射文件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void execute(ITaskContext context) throws Exception {
	    	/**
	    	 * 创建一个PluginDescriptor类型的List集合
	    	 */
		List<PluginDescriptor> descriptors = new ArrayList<PluginDescriptor>(plugin.getManager().getRegistry().getPluginDescriptors());
		
	
		/**
		 * 获取长度
		 */
		int len = NumberConstants.NUMBER_100 / descriptors.size();
		int i = 1;
		/**
		 * 记录日志
		 */
		LOG.info("开始添加注册myBatis映射文件...");
		/**
		 * 循环遍历PluginDescriptor类型的List集合
		 */
		for (PluginDescriptor pluginDescriptor : descriptors) {
		    	/**
		    	 * 从插件对象获取ClassLoader对象
		    	 */
			ClassLoader classLoader = plugin.getManager().getPluginClassLoader(pluginDescriptor);
			/**
		    	 * 根据当前线程获取ClassLoader对象
		    	 */
			ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
			/**
			 * 设置当前线程的ContextClassLoader
			 */
			Thread.currentThread().setContextClassLoader(classLoader);
			/**
			 * 创建一个为URL类型的List集合
			 */
			List<URL> mappers = new ArrayList<URL>();
			try {
			    	/**
			    	 * 将ClassPathFileScanner获取到List中的所有元素加入到URL类型的List集合中
			    	 */
				mappers.addAll(ClassPathFileScanner.scanFile(classLoader, suffix));
			} catch (Exception t) {
				LOG.error(t.getMessage(),t);
			}
			/**
		    	 * 解析mapper文件
		    	 */
			buildStatement(mappers);
			/**
		    	 * 设置当前线程的ContextClassLoader
		    	 */
			Thread.currentThread().setContextClassLoader(oldClassLoader);
			/**
		    	 * 设置标题
		    	 */
			context.setTitle(i18n.get("init.i18n.addFile"));
			/**
		    	 * 设置当前运行信息
		    	 */
			context.setMessage(i18n.get("init.i18n.addFileNew"));
			/**
			 * 设置进度
			 */
			context.setProgress(i * len);
			i++;
		}
	}
	/**
	 * 
	 * 解析mapper文件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private void buildStatement(List<URL> urls) {
		/**
		 * 从SQL工厂获取一个默认实例
		 */
		//SqlSessionFactory factory = DefaultTransactionFactory.getInstance().getDefaultSqlSessionFactory();
		/**
		 * 创建一个配置对象，从工厂获取
		 */
		//Configuration configuration = factory.getConfiguration();
		/**
		 * 创建一个配置对象，从GuiceContextFactroy实例获取
		 */
		Configuration configuration2 = GuiceContextFactroy.getInjector().getInstance(SqlSessionFactory.class).getConfiguration();
		/**
		 * 循环遍历URL对象
		 */
		int i = 0;
		for (URL url : urls) {
			
			progreeLogonScreen(i++);
		    	/**
		    	 * 定义一个路径字符串
		    	 */
			String path = null;
			try {
			    	/**
			    	 * 从配置文件获取路径信息
			    	 */
				path = URLDecoder.decode(url.getPath(), System.getProperty("file.encoding"));
				Application.getSplash().getProgress().setString(i18n.get("init.i18n.addFileView",new Object[]{StringUtils.substringAfterLast(path, "/")})); // 设置显示文字
				
				/**
				 * 获取输入流对象
				 */
				//InputStream inputStream = url.openStream();
				InputStream inputStream2 = url.openStream();
				/**
				 * 根据配置信息，路径，输入流获取XMLMapperBuilder对象
				 */
				//XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(inputStream, configuration, path, configuration.getSqlFragments());
				XMLMapperBuilder mapperBuilder1 = new XMLMapperBuilder(inputStream2, configuration2, path, configuration2.getSqlFragments());
				/**
				 * 将XMLMapperBuilder对象类型转化
				 */
				//mapperBuilder.parse();
				mapperBuilder1.parse();
			} catch (IOException e) {
				LOG.error(String.format("can not find mapper file %s", url), e);
			} catch (Exception e2) {
				LOG.error("解析mapper文件"+path+"发生错误，该文件没有解析，直接跳过", e2 );
			}
		}
	}
	/**
	 * 进入条
	 */
	private void progreeLogonScreen(int i) {
		//根据实际情况  这样设置进度条 用户体验最佳
		if(Application.getSplash().getProgress().getValue() < NumberConstants.NUMBER_50){
			Application.getSplash().getProgress().setValue(Application.getSplash().getProgress().getValue() + 1);
		}else{
			if(i%2==0){
				if(Application.getSplash().getProgress().getValue() < NumberConstants.NUMBER_98){
					Application.getSplash().getProgress().setValue(Application.getSplash().getProgress().getValue() + 1);
				}
			}
		}
	}

	@Override
	public void setPlugin(Plugin plugin) {
	    	/**
		 * 将参数plugin的值赋给属性plugin
		 */
		this.plugin = plugin;
	}

	@Override
	public void setApplication(IApplication application) {
	    	/**
		 * 调用addApplicationListener方法
		 */
		application.addApplicationListener(new IApplicationListener() {
		    	/**
			 * 定义数据库连接对象
			 */
			Connection connection = null;
			/**
			 * 定义数据库执行对象
			 */
			Statement statement = null;
			
			@Override
			public void onStarted(ApplicationEvent arg0) {
			}

			@Override
			public void onExiting(ApplicationEvent arg0) {
			    	/**
				 * 获得SqlSession实例
				 */
				SqlSession session = DefaultTransactionFactory.getInstance().getDefaultSqlSessionFactory().openSession();
				/**
				 * 获得数据库连接
				 */
				connection = session.getConnection();
				try {
				    	/**
					 * 获得数据库执行对象
					 */
					statement = connection.createStatement();
					/**
					 * 执行SHUTDOWN命令
					 */
					statement.execute("SHUTDOWN");
				} catch (SQLException e) {
					LOG.error(e.getMessage(),e);
				} finally {
					try {
					    	/**
						 * 关闭执行对象
						 */
						statement.close();
					} catch (SQLException e) {
					    	/**
						 * 记录日志
						 */
						LOG.error(e.getMessage(),e);
					}
					try {
					    	/**
						 * 关闭数据库连接
						 */
						connection.close();
					} catch (SQLException e) {
						LOG.error(e.getMessage(),e);
					}
				}
			}
		});
	}
}