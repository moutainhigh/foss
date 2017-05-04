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
 * FILE PATH        	: init/src/main/java/com/deppon/foss/module/init/client/dbupgrade/DBUpgradeAutoRunner.java
 * 
 * FILE NAME        	: DBUpgradeAutoRunner.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.init.client.dbupgrade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.java.plugin.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.component.dataaccess.ISqlExecutor;
import com.deppon.foss.framework.client.component.dataaccess.SqlExecutorFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.boot.client.app.Application;
import com.deppon.foss.module.boot.client.autorun.IAutoRunner;

 
/**
 * 数据库升级
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午10:10:40, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午10:10:40
 * @since
 * @version
 */
public class DBUpgradeAutoRunner implements IAutoRunner {

	/**
	 * 通过SQL执行工程，获取SQL执行器
	 */
	private ISqlExecutor sqlExecutor = SqlExecutorFactory.getSqlExecutor();
	/**
	 * 回滚文件路径
	 */
	/**
	 * 执行成功后日志文件
	 */
	private File succeedLog;
	/**
	 * 脚本文件
	 */
	private File scriptDir;
	/**
	 * 获取数据库Backup路径文件
	 */
	private File dbBackupDir;
	/**
	 * 数据库Home路径文件
	 */
	private File dbHomeDir;
	/**
	 * 定义backUpFileDir
	 */
	private static String backUpFileDir;
	/**
	 * 从日志工厂获取日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBUpgradeAutoRunner.class);

	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(DBUpgradeAutoRunner.class);
	
	/**
	 * 
	 * 加载所需要的环境
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void initEnvironment() {
		try {
			/**
			 * 通过应用上下文，获取HOME 路径
			 */
			String homeDir = URLDecoder.decode(ApplicationContext.getApplicationHome(), System.getProperty("file.encoding"));
			/**
			 * 获取到数据库HOME文件，并构造成实体
			 */
			dbHomeDir = new File(ApplicationContext.getDBHome());
			/**
			 * 创建一个文件
			 */
			createFolder(dbHomeDir);
			/**
			 * 获取backUpfile 路径
			 */
			backUpFileDir = homeDir + File.separator +"database"+File.separator+"backup";
			/**
			 * 获取脚本执行成功后，写入的文件
			 */
			succeedLog = new File(homeDir + File.separator+"database"+File.separator+"succeed.log");
			/**
			 * 获取脚本文件
			 */
			scriptDir = new File(homeDir + File.separator+"script");

		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(),e);
		}
		/**
		 * 判断脚本执行成功文件是否存在：如果不存在新建一个文件
		 */
		if (!succeedLog.exists()) {
			try {
				/**
				 * 新建一个脚本执行成功日志文件
				 */
				succeedLog.createNewFile();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		/**
		 * 获取数据库backUp文件
		 */
		//dbBackupDir = new File(backUpFileDir);
		/**
		 * 如果不存在，创建此文件夹
		 */
		//if (!dbBackupDir.exists() || dbBackupDir.isFile()) {
			//dbBackupDir.mkdirs();
		//} else {
			/**
			 * 如果存在，删除文件夹
			 */
			//deleteFolder(dbBackupDir);
			/**
			 * 再删除文件夹
			 */
			//dbBackupDir.mkdirs();
		//}
		/**
		 * 脚本文件夹，如果不存在，新建此文件夹
		 */
		if (!scriptDir.exists()) {
			//根据sonar(不良实践 - 方法忽略异常返回值 )修改
			try{
				scriptDir.mkdirs();
			}catch(Exception e){
				LOGGER.error(e.getMessage());
			}
		}
	}
	/**
	 * 
	 * 创建一个文件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private static void createFolder(File file) {
	    	/**
	    	 * 判断文件夹是否存在
	    	 */
		if (!file.exists() || file.isFile()) {
		    	/**
		    	 * 如果不存在则创建
		    	 */
			//根据sonar(不良实践 - 方法忽略异常返回值 )修改
			try{
				file.mkdirs();
			}catch(Exception e){
				LOGGER.error(e.getMessage());
			}
			
		}
	}


	/**
	 * 
	 * 执行多任务
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void execute(ITaskContext context) throws Exception {
		/**
		 * 初始化环境
		 */
		initEnvironment();
		//by xundq 2013-03-27
		//将备份数据库的操作推迟到有真正脚本执行时操作
		//大部分情况下没有脚本更新。所以可以避免这一操作
		/**
		 * 拷贝：把dbHomeDir 拷贝到dbBackupDir
		 */
		//backupDB(); 
		/**
		 * 新建list ，存放执行成功的脚本
		 */
		List<String> succ = new ArrayList<String>();
		/**
		 * 获取需要执行的脚本：首先先执行.schema 结尾的文件
		 */
		List<File> scripts = getSchema();
		/**
		 * 获取日志
		 */
		Set<String> log = getLog();
		/**
		 * 需要执行的脚本
		 */
		List<File> executeScripts = new ArrayList<File>();
		/**
		 * 获取需要执行的Schema脚本后进行遍历
		 */
		for (File schemaFile : scripts) {
			/**
			 * 如果已经包含此文件需要执行
			 */
			if (!log.contains(schemaFile.getName())) {
				/**
				 * 把Schema文件，加入到执行脚本列表中
				 */
				executeScripts.add(schemaFile);
			}
		}
		/**
		 * 获取.script 结尾的脚本文件
		 */
		scripts = getScript();
		/**
		 * 循环脚本文件
		 */
		for (File scriptFile : scripts) {
			/**
			 * 如果不在成功日志文件中，那么加入到执行列表
			 */
			if (!log.contains(scriptFile.getName())) {
				/**
				 * 加入到执行列表
				 */
				executeScripts.add(scriptFile);
			}
		}
		/**
		 * 获得所有创建索引index的文件 用于最后执行
		 */
		scripts = getIndex();
		/**
		 * 遍历索引脚本文件
		 */
		for (File indexFile : scripts) {
			/**
			 * 如果索引文件名称不在成功日志文件当中有记录
			 */
			if (!log.contains(indexFile.getName())) {
				/**
				 * 加入到执行脚本中
				 */
				executeScripts.add(indexFile);
			}
		}
		/**
		 * 如果执行脚本为空，或者长度小于1
		 */
		if (executeScripts == null || executeScripts.size() < 1) {
			/**
			 * 返回
			 */
			return;
		}
		
		//by xundq 2013-03-27
		//真正有脚本操作前备份数据库
		/**
		 * 拷贝：把dbHomeDir 拷贝到dbBackupDir
		 */
		backupDB();
		
		/**
		 * 100/获取执行脚本列表长度
		 */
		int len = NumberConstants.NUMBER_100 / executeScripts.size();
		/**
		 * 从1开始
		 */
		int i = 1;
		/**
		 * 控制是否写入到成功日志中
		 */
		boolean bool = true;
		//根据sonar(不良实践 - 方法可能在关闭流时因为异常而失败 )修改
		BufferedWriter writer=null;
		try {
			/**
			 * 遍历执行文件
			 */
			for (File file : executeScripts) {
				/**
				 * 获取文件名称
				 */
				String fileName = file.getName();
				/**
				 * 通过它文件名称获取可执行的ＳＱＬ，并执行SQL，如果成功则写入到成功日志中
				 */
				if (executeScript(file)) {
					/**
					 * 加入到成功日志列表中
					 */
					succ.add(fileName);
				} else {
					/**
					 * 设置标识状态
					 */
					bool = false;
					/**
					 * 跳出循环
					 */
					break;
				}
				/**
				 * 设置失误的主题
				 */
				context.setTitle(i18n.get("init.database.update"));
				/**
				 * 设置显示信息
				 */
				context.setMessage(i18n.get("init.datebase.updateNew"));
				/**
				 * 设置进度条
				 */
				context.setProgress(i * len);
				/**
				 * 递增
				 */
				i++;
			}
			/**
			 * 如果执行成功，写入到文件中
			 */
			if (bool) {
				/**
				 * 获取buffer写入器
				 */
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(succeedLog, true)));
				/**
				 * 循环成功日志列表
				 */
				for (String string : succ) {
					/**
					 * 进行写入
					 */
					writer.append(string);
					/**
					 * 新增以后
					 */
					writer.newLine();
				}
				
			}
			/**
			 * 捕捉异常
			 */
		} catch (Exception ex) {
			/**
			 * 写入异常信息
			 */
			LOGGER.error(ex.getMessage(),ex);
			/**
			 * 设置控制状态为否，这样不会在次写入
			 */
			bool = false;
		}finally{
			if(writer!=null){
				try{
					/**
					 * 关闭流
					 */
					writer.close();
				}catch(Exception e){
					LOGGER.error(e.getMessage(),e);
				}
			}
			
		}
		/**
		 * 如果写入为true：需要记录日志数据库更新失败

		 */
		if (!bool) {
			/**
			 * 需要记录日志数据库更新失败
			 */
			revertDB();
		}
		/**
		 * sql执行器关闭
		 */
		sqlExecutor.shutdown();

	}
	/**
	 * 把dbHomeDir 拷贝到dbBackupDir
	 * backupDB
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void backupDB() {
		
		try {
			/**
			 * 获取数据库backUp文件
			 */
			dbBackupDir = new File(backUpFileDir);
			/**
			 * 如果不存在，创建此文件夹
			 */

			if (!dbBackupDir.exists() || dbBackupDir.isFile()) {
				try{
					dbBackupDir.mkdirs();
				}catch(Exception e){
					LOGGER.error(e.getMessage());
				}
				
			} else {
				/**
				 * 如果存在，删除文件夹
				 */
				deleteFolder(dbBackupDir);
				/**
				 * 再删除文件夹
				 */
				dbBackupDir.mkdirs();
			}

			
			/**
			 * 把dbHomeDir 拷贝到dbBackupDir
			 */
			IoUtil.copyFolder(dbHomeDir, dbBackupDir, true);
		} catch (IOException e) {
			/**
			 * 写入错误日志
			 */
			LOGGER.error(e.getMessage(),e);
		}
	}
	/**
	 * 
	 * 需要记录日志数据库更新失败
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void revertDB() {
		
		Application.getSplash().setAlwaysOnTop(false);
		// 需要记录日志数据库更新失败
		
		JOptionPane.showMessageDialog(null, i18n.get("init.datebase.updateFaile"));
		
		
		try {
			/**
			 * 关闭sql执行器
			 */
			sqlExecutor.shutdown();

			/**
			 * 删除文件夹
			 */
			deleteFolder(dbHomeDir);

		} catch (SecurityException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(),e);
		}
		try {
			/**
			 * 创建文件夹
			 */
			dbHomeDir.mkdirs();
			/**
			 * 拷贝dbHomeDir 文件夹里面的内容到dbBackupDir文件夹中
			 */
			IoUtil.copyFolder(dbBackupDir, dbHomeDir, true);
		} catch (IOException e) {

			/**
			 * 写入错误日志
			 */
			LOGGER.error(e.getMessage(),e);
		}

	}
	/**
	 * 
	 * 删除文件夹
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public static void deleteFolder(File dir) {
		/**
		 * 如果目录不存在或者是一个文件，将返回
		 */
		if (!dir.exists() || dir.isFile()) {
			return;
		}
		/**
		 * 如果是文件夹，那么遍历里面的文件
		 */
		File filelist[] = dir.listFiles();
		/**
		 * 获取到文件列表的长度，逐个删除
		 */
		int listlen = filelist.length;
		for (int i = 0; i < listlen; i++) {
			/**
			 * 如果是文件夹（目录），删除文件夹（目录）
			 */
			if (filelist[i].isDirectory()) {
				deleteFolder(filelist[i]);
			} else {
				try{
					/**
					 * 如果不是，直接删除
					 */
					filelist[i].delete();
				}catch(Exception e){
					LOGGER.error(e.getMessage());
				}
				
			}
		}
		/**
		 * 删除当前目录
		 */
		dir.delete();// 删除当前目录
	}
	
	
	/**
	 * 
	 * 获得所有创建索引index的文件 用于最后执行
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public List<File> getIndex() {
		/**
		 * 创建一个文件集合
		 */
		List<File> scripts = new ArrayList<File>();
			/**
			 * 获取文件数组
			 */
			File[] files = scriptDir.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
				    	/**
				    	 * 获取文件路径
				    	 */
					String path = pathname.getAbsolutePath();
					/**
					 * 判断一个传进来的路径是否后缀名为.index
					 */
					return path.endsWith(".index");
				}
			});
			/**
			 * 判断文件数组是否为空
			 */
			if (files != null) {
			    	/**
			    	 * 如果不为空则循环遍历
			    	 */
				for (File file : files) {
					scripts.add(file);
				}
			}
			/**
			 * 使用集合排序
			 */
			Collections.sort(scripts, new Comparator<File>() {			    	
				@Override
				public int compare(File o1, File o2) {
				    	/**
				    	 * 将两个文件的字符串名字进行比较
				    	 */
					return o1.getName().compareTo(o2.getName());
				}
			});
		return scripts;
	}
	
	/**
	 * 
	 * 获取script
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public List<File> getScript() {
		/**
		 * 创建一个
		 */
		List<File> scripts = new ArrayList<File>();
			File[] files = scriptDir.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
				    	/**
					 * 获取文件路径
					 */
					String path = pathname.getAbsolutePath();
					/**
					 * 判断一个传进来的路径是否后缀名为.script
					 */
					return path.endsWith(".script");
				}
			});
			/**
			 * 判断文件数组是否为空
			 */
			if (files != null) {
			    	/**
			    	 * 如果不为空则循环遍历
			    	 */
				for (File file : files) {
					scripts.add(file);
				}
			}
			/**
			 * 使用集合排序
			 */
			Collections.sort(scripts, new Comparator<File>() {

				@Override
				public int compare(File o1, File o2) {
				    	/**
				    	 * 将两个文件的字符串名字进行比较
				    	 */
					return o1.getName().compareTo(o2.getName());
				}
			});
		return scripts;
	}
	/**
	 * 
	 * 获取Schema
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public List<File> getSchema() {
		List<File> scripts = new ArrayList<File>();
			File[] files = scriptDir.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
				    	/**
				    	 * 获取文件路径
				    	 */
					String path = pathname.getAbsolutePath();
					/**
					 * 判断一个传进来的路径是否后缀名为.schema
					 */
					return path.endsWith(".schema");
				}
			});
			/**
			 * 判断文件数组是否为空
			 */
			if (files != null) {
			    	/**
			    	 * 如果不为空则循环遍历
			    	 */
				for (File file : files) {
					scripts.add(file);
				}
			}
			/**
			 * 使用集合排序
			 */
			Collections.sort(scripts, new Comparator<File>() {

				@Override
				public int compare(File o1, File o2) {
				    	/**
				    	 * 将两个文件的字符串名字进行比较
				    	 */
					return o1.getName().compareTo(o2.getName());
				}
			});
		return scripts;
	}
	/**
	 * 
	 * 获取LOG
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private Set<String> getLog() {
	    	/**
	    	 * 定义一个读取文件对象
	    	 */
		BufferedReader reader = null;
		/**
		 * 创建一个String型的Set集合
		 */
		Set<String> logs = new HashSet<String>();
		try {
			/**
			 * 获取到读取器
			 */
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(succeedLog)));
			String line = null;
			
			line = reader.readLine();
			/**
			 * 如果读出来不为空，加入到日志列表中
			 */
			while (line != null) {
				logs.add(line);
				/**
				 * 将读取的每一行赋给line
				 */
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			/**
			 * 记录日志
			 */
			LOGGER.error(e.getMessage(),e);
		} catch (IOException e) {
			/**
			 * 记录错误日志
			 */
			LOGGER.error(e.getMessage(),e);
		} finally {
			/**
			 * 如果读取器不为空
			 */
			if (reader != null) {
				try {
					/**
					 * 关闭流
					 */
					reader.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(),e);
				}
			}
		}
		return logs;
	}
	/**
	 * 
	 * 执行script文件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public boolean executeScript(File file) throws IOException {
		try {
			/**
			 *通过文件读取到ＳＱＬ
			 */
			List<String> slqs = getSql(file);
			/**
			 * 设置自动提交事务为false
			 */
			sqlExecutor.setAutoCommit(false);
			/**
			 * 遍历执行sql
			 */
			for (String sql : slqs) {
				/**
				 * 一个ＳＱＬ　需要在文件中写一行，即一个ＳＱＬ一行
				 */
				sqlExecutor.executeUpdate(sql);
			}

			/**
			 * 提交事务
			 */
			sqlExecutor.commit();
		} catch (SQLException e) {
			/**
			 * 记录日志
			 */
			LOGGER.error(e.getMessage(),e);
			try {

				/**
				 * 回滚
				 */
				sqlExecutor.rollback();
				return false;
			} catch (SQLException e1) {
				LOGGER.error(e1.getMessage(),e1);
				return false;
			}
		}
		return true;
	}
	/**
	 * 
	 * 获取到SQL
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public List<String> getSql(File file) throws IOException {
		/**
		 * 新增sql列表
		 */
		List<String> sqls = new ArrayList<String>();
		/**
		 * 定义一个字节流通向字符流的桥梁类
		 */
		InputStreamReader inReader = null;
		/**
		 * 定义一个读取文件类
		 */
		BufferedReader reader = null;
		try {
		    	/**
		    	 * 创建一个字节流通向字符流的桥梁类
		    	 */
			inReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			/**
			 * 创建一个读取文件类
			 */
			reader = new BufferedReader(inReader);
			String line = null;			
			line = reader.readLine();
			while (line != null) {
				if (!"".equals(line)) {
					/**
					 * 添加到sql 列表中
					 */
					sqls.add(line);
				}
				/**
				 * 将读取的每一行赋给line
				 */
				line = reader.readLine();
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
			return sqls;
		}finally{
			/**
			 * 如果读取器不为空，关闭
			 */
			if(reader != null){
				reader.close();
			}
			/**
			 * 如果读取器不为空，关闭
			 */
			if(inReader != null){
				inReader.close();
			}
		}
		return sqls;
	}
}