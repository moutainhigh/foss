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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/webstart/Launcher.java
 * 
 * FILE NAME        	: Launcher.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.webstart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 客户端启动器 (JDK1.6兼容)
 * 
 */

public class Launcher {

	public static final String PREFER_JRE_VERSION = "1.6";

	private static final String DEFAULT_REMOTE_HOME_URL = "http://localhost:8080/gui/";

	private static final String DEFAULT_MODULE_NAME = "appHome";

	private static final String DEFAULT_REMOTE_METADATA = "expApplication.dat";

	private static final String DEFAULT_LOCAL_DIRECTORY = "c:/deppontest/appHome/";

	private static final String LOCAL_TMP_DIRECTORY = "tmp";

	private static final String LOCAL_HISTORY_DIRECTORY = "history";

	private static final String PREFIX_IDENTIFY = "#@#";

	private static final String TMP_DISK = "c:/deppontest/download/";

	private static final String CONFIG_FILE = "update.ini";

	private static final String LANCHER_FILE = "run.bat";

	private static final int NUM_4 = 4 ;

	private static final int NUM_3 = 3 ;

	// 远程服务器端根目录
	private final String remoteHomeDirectory;

	// 远程服务器端application目录
	private final String remoteModuleDirectory;

	// 远程服务器端元素文件
	private final String remoteMetaFile;

	// 本地目录
	private final String localDirectory;

	private final String appHomeDir;

	private final String appWebroot;

	private final String appHttproot;

	private String currentHistoryDir;

	// 下载过程UI界面
	public static final LauncherUI ui = new LauncherUI();

	// 保留不需要更新的数据，直接拷贝
	private Hashtable reservedFilesMap = new Hashtable();

	private LauncherUtil util = new LauncherUtil();

	private static final int NUM_5 = 5 ;
             
	private static final int NUM_10 = 10 ;
           
	private static final int NUM_15 = 15 ;
             
	private static final int NUM_55 = 55 ;
           
	private static final int NUM_60 = 60 ;
             
	private static final int NUM_80 = 80 ;
            
	private static final int NUM_85 = 85 ;
            
	private static final int NUM_95 = 95 ;
            
	private static final int NUM_100 = 100 ;
             
	private static final long NUM_1000L = 1000L ;
           
	private static final int NUM_50 = 50 ;
            
	private static final int NUM_35 = 35 ;
            
	private static final long NUM_5000 = 5000;

	private static final Log logger = LogFactory.getLog(Launcher.class);

	/**
	 * 初始化类
	 * 
	 * @param remoteHomeUrl
	 * @param moduleName
	 * @param metaFile
	 * @param jnlpFile
	 * @param localDirectory
	 */
	public Launcher(String remoteHomeUrl, String moduleName, String metaFile,
			String localDirectory) {
		if (null == remoteHomeUrl)
			remoteHomeUrl = DEFAULT_REMOTE_HOME_URL;
		if ('/' != remoteHomeUrl.charAt(remoteHomeUrl.length() - 1))
			remoteHomeUrl = remoteHomeUrl.concat("/");
		this.remoteHomeDirectory = remoteHomeUrl;

		if (null == moduleName)
			moduleName = DEFAULT_MODULE_NAME;
		this.remoteModuleDirectory = moduleName;

		if (null == metaFile)
			metaFile = DEFAULT_REMOTE_METADATA;
		this.remoteMetaFile = metaFile;

		if (null == localDirectory)
			localDirectory = DEFAULT_LOCAL_DIRECTORY;
		if ('/' != localDirectory.charAt(localDirectory.length() - 1))
			localDirectory = localDirectory.concat("/");
		this.localDirectory = localDirectory;

		File f = new File(localDirectory);
		String appHomeDirPath = f.getParent();
		if ('/' != appHomeDirPath.charAt(appHomeDirPath.length() - 1))
			appHomeDirPath = appHomeDirPath.concat("/");

		this.appHomeDir = appHomeDirPath;

		this.appHttproot = this.remoteHomeDirectory.concat(
				this.remoteModuleDirectory).concat("/");

		this.appWebroot = DEFAULT_MODULE_NAME.concat("/");

		logger.info("this.remoteHomeDirectory" + this.remoteHomeDirectory);
		logger.info("remoteModuleDirectory" + this.remoteModuleDirectory);
		logger.info("appHomeDir" + this.appHomeDir);
		logger.info("appHttproot" + this.appHttproot);
		logger.info("appWebroot" + this.appWebroot);
		logger.info("localDirectory" + this.localDirectory);

	}

	/**
	 * 运行启动器
	 * @throws IOException 
	 * 运行失败时抛出
	 */
	public void start(String[] args) {
		try {

			logger.info("enter start()");
			ui.setGuideMessage(Messages.getString("guide.preparing"));

			// 检查本机是否安装了jre 1.6
			if (!testJre()) {
				logger.info("after testJre()");
				ui.showError("ERROR!!!",Messages.getString("guide.err.test.jre.failed"));
				System.exit(0);
			}
			boolean isConnect;// 服务器连接是否正常

			URL url = null;
			try {
				url = new URL(remoteHomeDirectory);
				InputStream in = url.openStream();
				isConnect = true;
				in.close();
			} catch (IOException e) {
				isConnect = false;
				// System.out.println("无法连接到：" + url.toString());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}

			if (isConnect) {

				String remoteMetaFile = this.remoteHomeDirectory.concat(this.remoteMetaFile);
				Hashtable remoteMetadataMap = readRemoteMetadata(remoteMetaFile);// 下载远程元数据

				ui.setProgressPercent(NUM_5);
				Hashtable differentMetadataMap = compareDifferentMetadata(remoteMetadataMap);// 对比远程与本地不同的元数据

				ui.setProgressPercent(NUM_10);

				boolean isCancelUpdate = false; // 是否忽略更新操作

				if (differentMetadataMap.size() > 0) {// 如果存在需要更新的文件
					ui.setGuideMessage(Messages.getString("guide.sync.application"));
					String tmpDirPath = this.appHomeDir.concat(LOCAL_TMP_DIRECTORY);// 本地临时文件

					isCancelUpdate = deleteDirectory(tmpDirPath);

					ui.setProgressPercent(NUM_15);
					ui.setGuideMessage(Messages.getString("progress.download.file")); // FTP下载文件

					// 连接FTP
					FTPUtil.getInstance().connect();
					if (!isCancelUpdate) {

						downloadFiles(differentMetadataMap, this.appWebroot, tmpDirPath);// 同步文件
						reloadLocalMetadata(reservedFilesMap, differentMetadataMap, this.localDirectory, tmpDirPath); // 拷贝未发生变化的文件

						ui.setProgressPercent(NUM_55);
						System.out.println("基础包下载完成,同步版本号发生改变的文件...");

						downloadConfigFile(differentMetadataMap, this.appWebroot, tmpDirPath, false); // 同步版本号发生改变的文件

						ui.setProgressPercent(NUM_60);
						File tmp = new File(tmpDirPath);

						if (tmp.exists()) {

							// 备份本地工作目录
							String historyHome = this.appHomeDir
									.concat(LOCAL_HISTORY_DIRECTORY)
									.concat("/")
									.concat(this.remoteModuleDirectory)
									.concat("/");

							backup(historyHome, this.localDirectory);

							ui.setProgressPercent(NUM_80);
							try {
								// 把当前工作目录重命名为临时文件夹
								String tmpFilePath = this.appHomeDir + "tmp" + System.currentTimeMillis();

								isCancelUpdate = renameDir(localDirectory,tmpFilePath);

								// 把下载的临时目录切换到工作目录
								if (!isCancelUpdate) {

									renameDir(tmpDirPath, localDirectory);

									// 删除临时文件
									ui.setGuideMessage(Messages.getString("guide.remove.cache.file"));
									deleteDirectory(tmpFilePath);
								}

								ui.setProgressPercent(NUM_85);
							} catch (Throwable e) {// 切换失败，恢复文件
								ui.showError("ERROR",Messages.getString("guide.launch.application.failed"));
								// 下载的时候如果出现了异常 就把history中的文件返回回来
								String historyHomeDirPath = this.appHomeDir
										.concat(LOCAL_HISTORY_DIRECTORY)
										.concat("/")
										.concat(this.remoteModuleDirectory)
										.concat("/");
								resumeApplication(historyHomeDirPath,localDirectory);
							}
						}
					}
					// 关闭FTP
					FTPUtil.getInstance().disconnect();
				} else {
					// 连接FTP
					FTPUtil.getInstance().connect();
					String destDir = this.localDirectory;
					downloadConfigFile(differentMetadataMap, this.appWebroot, destDir, true); // 同步版本号发生改变的文件
					ui.setProgressPercent(NUM_85);
					// 关闭FTP
					FTPUtil.getInstance().disconnect();
				}

				ui.setProgressPercent(NUM_95);
				ui.setGuideMessage(Messages.getString("guide.launch.application.success"));
				ui.setProgressPercent(NUM_100);

				reservedFilesMap = null;
				remoteMetadataMap = null;
				differentMetadataMap = null;

			}

		} catch (FileNotFoundException e) {
			ui.showWarn("WARNNING", Messages.getString("guide.err.file.miss") + e.getMessage());
			ui.showWarn("error", ExceptionUtils.getFullStackTrace(e));
		} catch (IOException e) {
			if (e.getMessage() != null && e.getMessage().startsWith("can not found")) {
				ui.showWarn("WARNNING",Messages.getString("guide.err.file.miss") + e.getMessage());
			}
			ui.showWarn("WARNNING", Messages.getString("err.update.failed"));
			ui.showWarn("error", ExceptionUtils.getFullStackTrace(e));
		} catch (Exception e) {
			ui.setGuideMessage(Messages.getString("guide.launch.application.failed") + e.toString());
			ui.showWarn("error", ExceptionUtils.getFullStackTrace(e));
		} catch (Throwable e) {
			ui.setGuideMessage(Messages.getString("guide.launch.application.failed") + e.toString());
			ui.showWarn("error", ExceptionUtils.getFullStackTrace(e));
		}

		try {
			Thread.sleep(NUM_5 * NUM_1000L);
		} catch (InterruptedException e) {
			//to do nothing
		}

		launchApplication(args);// 启动客户端应用

		ui.close();// 关闭下载窗口

	}

	/**
	 * 删除配置信息
	 * 
	 * @param dir
	 *            文件夹
	 * @throws Exception
	 */
	private void deleteConfiguration(String dir) throws Exception {
		File configuration = new File(dir);
		File[] files = configuration.listFiles();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file != null
						&& !"config.ini".equalsIgnoreCase(file.getName())) {
					util.deleteAll(file);
				}
			}
		}
	}

	/**
	 * 下载已经更新的文件
	 * 
	 * @param differentMetadataMap
	 *            文件列表
	 * @param romoteDir
	 *            远程跟目录
	 * @param localDir
	 *            目标目录
	 * @throws Exception
	 */
	private void downloadFiles(Hashtable differentMetadataMap,
			String romoteDir, String localDir) throws Exception {
		int i = NUM_15;
		for (Enumeration iterator = differentMetadataMap.keys(); iterator
				.hasMoreElements();) {

			String fileName = (String) iterator.nextElement();
			Long lastModified = (Long) differentMetadataMap.get(fileName);
			String remotePath = romoteDir + fileName;
			String localPath = localDir + "/" + fileName;
			String showmsg = remotePath;
			if (showmsg.length() > NUM_50) {
				showmsg = remotePath.substring(0, NUM_15) + "......"
						+ remotePath.substring(remotePath.length() - NUM_35);
			}
			ui.setProgressMessage(Messages.getString("progress.download.file")
					+ showmsg);
			if (i < NUM_35)
				ui.setProgressPercent(i + 1);
			i++;
			downloadFile(remotePath, localPath, lastModified);
		}
	}

	/**
	 * 下载文件，如失败将询问是否重试
	 * 
	 * @param remotePath
	 * @param localPath
	 * @param lastModified
	 * @throws Exception
	 */
	private void downloadFile(String remotePath, String localPath,
			Long lastModified) throws Exception {
		try {
			this.util.downloadFile(remotePath, localPath,
					lastModified.longValue());
		} catch (Exception e) {

			ui.showError("ERROR", ExceptionUtils.getFullStackTrace(e));

			int retInt = ui.showWarn("WARNING",
					Messages.getString("err.download.faild") + remotePath,
					ui.getButtons1());
			if (retInt == 1) {
				retInt = ui.showWarn("WARNING",
						Messages.getString("guide.err.update.ignore"),
						ui.getButtons2());
				if (retInt == 0) {
					throw e;
				} else {
					FTPUtil.getInstance().connect();
					downloadFile(remotePath, localPath, lastModified);
				}
			} else {
				FTPUtil.getInstance().connect();
				downloadFile(remotePath, localPath, lastModified);
			}
		}

	}

	/**
	 * 读取远程文件
	 * 
	 * @param remoteMetaFile
	 * @return
	 * @throws Exception
	 */
	private Hashtable readRemoteMetadata(String httpUrl) throws Exception {
		ui.setProgressMessage(Messages.getString("progress.download.remote.metadata"));
		Hashtable result = null;
		try {
			result = util.readRemoteMetadata(httpUrl);
		} catch (NoRouteToHostException e) {
			// throw e;
		} catch (ConnectException e) {
			// throw e;
		} catch (UnknownHostException e) {
			// throw e;
		} catch (UnknownServiceException e) {
			// throw e;
		} catch (Exception e) {
			int retInt = ui.showWarn("WARNING",Messages.getString("err.download.meta"), ui.getButtons1());
			if (retInt == 1) {
				retInt = ui.showWarn("WARNING",Messages.getString("guide.err.update.ignore"),ui.getButtons2());
				if (retInt == 0)
					throw e;
				result = readRemoteMetadata(httpUrl);
			} else {
				result = readRemoteMetadata(httpUrl);
			}
		}

		return result;
	}

	/**
	 * 重新拷贝本地未发生变化的文件
	 * 
	 * @param reserverdMetadataMap
	 *            未发生变化的文件列表
	 * @param differentMetadataMap
	 *            已经发生变化的文件列表
	 * @param differentConfigFiles
	 *            已经发生变化的配置文件列表
	 * @param frmDir
	 *            源目
	 * @param toDir
	 *            目标目录
	 * @throws Exception
	 */
	private void reloadLocalMetadata(Hashtable reserverdMetadataMap,
			Hashtable differentMetadataMap, String frmDir, String toDir)
			throws Exception {
		
		String newToDir = null;
		if ('/' != toDir.charAt(toDir.length() - 1)){
			newToDir = toDir.concat("/");
		}
		
		String newFrmDir = null;
		if ('/' != frmDir.charAt(frmDir.length() - 1)){
			newFrmDir = frmDir.concat("/");
		}

		int i = NUM_35;
		for (Enumeration iterator = reserverdMetadataMap.keys(); iterator
				.hasMoreElements();) {
			String fileName = (String) iterator.nextElement();
			if (fileName.startsWith(PREFIX_IDENTIFY))
				continue;
			String des = newToDir.concat("/").concat(fileName);
			String frm = newFrmDir.concat(fileName);
			File desFile = new File(des);
			if (!desFile.exists()) {// 若文件已经存在，则不需要复制（此种情况可能发生在按版本控制的配置文件发生更新的情况）
				String showmsg = newFrmDir;
				if (showmsg.length() > NUM_50) {
					showmsg = frm.substring(0, NUM_15) + "......"
							+ frm.substring(frm.length() - NUM_35);
				}
				ui.setProgressMessage(Messages.getString("progress.copy.file")
						+ frm);
				if (i < NUM_55)
					ui.setProgressPercent(i);
				i++;
				util.copyFile(frm, des);
			}
		}

		// copy database/hsqldb/foss.script 文件到临时目录对应位置
		String key = "database/hsqldb/foss.script";
		// sonra 高危  错误用法 - 可能出现空指针引用  
		if (org.apache.commons.lang.StringUtils.isNotEmpty(newFrmDir)) {
			String dataFilePath = newFrmDir.concat(key);
			File datafile = new File(dataFilePath);
			if (datafile.exists()) {
				util.copyFile(dataFilePath, newToDir.concat(key));
			}
		}
	}

	/**
	 * 对比远程与本地元数据,返回需要更新的文件列表
	 * 
	 * @param remoteMetadataMap
	 *            远程文件总列表
	 * @return
	 * @throws Exception
	 */
	private Hashtable compareDifferentMetadata(Hashtable remoteMetadataMap)
			throws Exception {

		ui.setProgressMessage(Messages.getString("progress.compare.different.metadata"));

		Hashtable diff = new Hashtable();

		for (Enumeration iterator = remoteMetadataMap.keys(); iterator.hasMoreElements();) {
			String fileName = (String) iterator.nextElement();
			Long lastModified = (Long) remoteMetadataMap.get(fileName);
			if (fileName.startsWith(PREFIX_IDENTIFY))
				continue;

			if (CONFIG_FILE.equals(fileName)) {
				// 首次下载
				File real = new File(localDirectory);
				if (real.exists())
					continue;
			}

			String localFileName = localDirectory + fileName;
			File localFile = new File(localFileName);
			if (!localFile.exists()) {
				diff.put(fileName, lastModified);
			} else {
				// 保存不需要更新的文件
				reservedFilesMap.put(fileName, lastModified);
			}
		}
		return diff;
	}

	/**
	 * 启动客户端应用
	 * 
	 * @throws IOException
	 *             启动失败时抛出
	 */
	private void launchApplication(String[] args) {
		String launchPath = localDirectory + LANCHER_FILE;

		try {
			File f = new File(launchPath);

			if (f.exists()) {

				// 启动bat文件
				Runtime.getRuntime().exec(
						"cmd /c " + launchPath);
			} else {
				ui.showWarn("WARNNING", Messages.getString("err.update.failed"));
			}

			Thread.sleep(NUM_5000);

		} catch (Throwable e) {
			ui.showWarn("error", ExceptionUtils.getFullStackTrace(e));
			ui.setGuideMessage(Messages
					.getString("guide.launch.application.failed")
					+ e.toString());
		}

	}

	/**
	 * 程序入口，web start 将调用此方法执行launcher
	 * 
	 * @param args
	 *            [0] 服务器根目录URL
	 * @param args
	 *            [1] 应用模块名
	 * @param args
	 *            [2] 远程文件列表文件
	 * 
	 * @param args
	 *            [3] 本地应用程序的目录
	 * 
	 */

	public static void main(String[] args) {
		try {

			 //args = new String[4]; 
			 //args[0] = "http://192.168.10.27:8080/foss-webstart-0.0.1-SNAPSHOT/"; 
			 //args[1] = "appHome";
			 //args[2] = "expApplication.dat"; 
			 //args[3] = "c:/depponApp/appHome/";

			if (null == args || NUM_4 > args.length) {
				ui.showError("ERROR",
						Messages.getString("guide.launch.application.failed")
								+ Messages.getString("guide.err.params.miss"));
				return;
			}
			String remoteHomeUrl = args[0];// 远程服务器端元数据
			String moduleName = args[1];// 当前应用模块名
			String metaFile = args[2];// 远程文件列表
			String localDirectory = args[NUM_3];// 本地目录

			Launcher launcher = new Launcher(remoteHomeUrl, moduleName,
					metaFile, localDirectory);// 创建启动器

			if (launcher.isAppRunning(launcher.localDirectory,
					launcher.remoteModuleDirectory)) {
				ui.showWarn("WARNING",
						Messages.getString("err.application.isRuning"));
				return;
			}

			launcher.start(args);// 启动
		} catch (Exception e) {
			ui.showError(
					"ERROR",
					Messages.getString("guide.launch.application.failed")
							+ e.getMessage());
		} finally {
			// 退出启动器
			System.exit(0);
		}
	}

	/**
	 * 根据属性名获取配置文件属性值
	 * 
	 * @param remoteMetadataMap
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public boolean getPropertyValue(Hashtable remoteMetadataMap, String param)
			throws IOException {
		Long paramVal = (Long) remoteMetadataMap.get(PREFIX_IDENTIFY
				.concat(param));
		if (null == paramVal) {
			return false;
		} else {
			return 1 == paramVal.longValue();
		}
	}

	/**
	 * 备份历史版本
	 * 
	 * @param resDir
	 * @param destDir
	 * @throws IOException
	 */
	public void backup(String historyHome, String workDir) throws IOException {
		ui.setGuideMessage(Messages.getString("guide.sync.application.backup"));

		File home = new File(workDir);
		if (home.exists()) {
			// 保存历史版本
			int historyNo = arrangeHistoryDirectories(historyHome);
			currentHistoryDir = "history_" + historyNo;
			String buckupPath = historyHome.concat("/").concat(
					currentHistoryDir);
			File buckup = new File(buckupPath);
			if (buckup.exists()) {
				deleteDirectory(buckupPath);
			}
			buckup.mkdirs();
			copyDirectory(workDir, buckupPath);
		}
	}

	/**
	 * 整理历史版本文件夹
	 * 
	 * @param historyDirPath
	 *            文件夹路径名
	 * @throws IOException
	 */
	public int arrangeHistoryDirectories(String historyDirPath)
			throws IOException {
		File home = new File(historyDirPath);
		if (!home.exists())
			home.mkdirs();

		File[] file = home.listFiles();
		if (file == null || file.length <= 0)
			return 1;

		if (file.length < NUM_5)
			return (file.length + 1);

		int retNo = 1;
		for (int i = 0; i < file.length; i++) {
			if (file[i] == null)
				continue;
			if (i == 0) {
				// file[i].delete();
				if (file[i].isDirectory()) {
					deleteDirectory(file[i].getAbsolutePath());
				} else {
					deleteFile(file[i]);
				}
			} else {
				File dest = new File(home + "/" + "history_" + retNo);
				file[i].renameTo(dest);
				retNo++;
			}
		}

		return retNo;
	}

	/**
	 * 当更新失败的时候系统恢复
	 * 
	 * @throws IOException
	 */
	public void resumeApplication(String resDir, String destDir)
			throws IOException {
		ui.setGuideMessage(Messages.getString("guide.resume.application"));
		File home = new File(resDir);
		if (!home.exists())
			return;

		File[] file = home.listFiles();
		if (file.length <= 0)
			return;

		String currentVersionDirectory = file[file.length - 1].getName();

		deleteDirectory(destDir);
		copyDirectory(resDir + "/" + currentVersionDirectory, destDir);
	}

	/**
	 * 下载配置文件, 返回已经更新的配置文件列表
	 * 
	 * @param differentMetadataMap
	 *            需要更新的文件列表
	 * @param remoteUrl
	 *            远程根目录
	 * @param copyToDir
	 *            目标目录
	 * @return
	 * @throws Exception
	 */
	public void downloadConfigFile(Hashtable differentMetadataMap,
			String remoteUrl, String copyToDir, boolean needBackup)
			throws Exception {
		Hashtable hs = new Hashtable();
		Hashtable deleteList = new Hashtable();
		boolean hasUpdated = false;
		String newCopyToDir = null;
		if ('/'!=copyToDir.charAt(copyToDir.length() - 1))
			newCopyToDir = copyToDir.concat("/");
		else 
			newCopyToDir = copyToDir;

		try {
			// 下载远程update.ini文件 修改此功能，从ftp上下载此文件
			File downloadtmp = new File(TMP_DISK);
			if (!downloadtmp.exists())
				downloadtmp.mkdir();

			String tmp = TMP_DISK.concat(CONFIG_FILE);
			File f = new File(tmp);
			deleteFile(f);
			
			util.downloadFile(remoteUrl.concat(CONFIG_FILE),tmp,-1);
			//String configFileUrl = this.appHttproot.concat(CONFIG_FILE);			
			//util.downloadFileHttp(configFileUrl, tmp, -1);

			File tmpconfig = new File(tmp);
			if (!tmpconfig.exists()) {
				return;
			}

			// 与本地update.ini做对比,得到需要更新的文件列表
			ConfigurationReader remoteConfig = new ConfigurationReader(tmp);
			String localCofig = this.localDirectory.concat(CONFIG_FILE);
			ConfigurationReader localConfig = null;
			boolean existsLocalConfigFile = false;
			File local = new File(localCofig);
			if (local.exists()) {
				localConfig = new ConfigurationReader(localCofig);
				existsLocalConfigFile = true;
			}

			Map<String, String> p = remoteConfig.getConfiguration();
			// sorna 性能 - keySet迭代是低效的，使用entrySet代替
			for (Iterator<Entry<String, String>> iterator = p.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = iterator.next();
				String fileName = entry.getKey();
				String version = entry.getValue();

				try {
					if (Double.parseDouble(version) < 0) {
						// 对于<=0的数据 做删除操作
						deleteList.put(fileName, NumberUtils.LONG_MINUS_ONE);
						hasUpdated = true;
						continue;// 删除文件以后跳过 不再比对
					}

				} catch (Throwable e) { 
					//to do nothing
				}

				if (existsLocalConfigFile) {
					if("F".equals(version)){
						//强制更新本地文件
						hs.put(fileName, NumberUtils.LONG_MINUS_ONE);
						hasUpdated = true;
					}
					else {
						String localVersion = localConfig.getValue(fileName);
						if (!version.equals(localVersion)) {
							if (differentMetadataMap == null || !differentMetadataMap.containsKey(fileName))
								hs.put(fileName, NumberUtils.LONG_MINUS_ONE);
							
							hasUpdated = true;
						}
					}
				} else {
					hs.put(fileName, NumberUtils.LONG_MINUS_ONE);
				}
			}

			// 下载需要更新的文件
			String configBakDir = compareReplaceAndBackupFile(remoteUrl, newCopyToDir, needBackup, hs);

			// 删除需要删除的文件
			compareDeleteAndBackUPFile(remoteUrl, newCopyToDir, needBackup, deleteList, configBakDir);

			// 更新update.ini
			String localFile = newCopyToDir.concat(CONFIG_FILE);
			if (hasUpdated && needBackup) {
				util.copyFile(localCofig, configBakDir.concat("/").concat(CONFIG_FILE));
			}
			File ff = new File(localFile);
			deleteFile(ff);
			util.copyFile(tmp, localFile);

			deleteFile(tmpconfig);
		} catch (java.io.FileNotFoundException e) {
			return;
		}
	}

	private void compareDeleteAndBackUPFile(String remoteUrl, String copyToDir,
			boolean needBackup, Hashtable deleteList, String configBakDir)
			throws IOException {
		Enumeration iterator2 = deleteList.keys();
		while (iterator2.hasMoreElements()) {
			String fileName = (String) iterator2.nextElement();
			String remotePath = remoteUrl + fileName;
			String localPath = copyToDir + fileName;
			File currentFile = new File(localPath);
			if (currentFile.exists()) {
				if (needBackup) {
					File configbak = new File(configBakDir);
					if (!configbak.exists()) {
						configbak.mkdirs();
					}
					util.copyFile(localPath,
							configBakDir.concat("/").concat(fileName));
				}
				deleteFile(currentFile);

			}
			ui.setProgressMessage(Messages.getString("progress.delete.file")
					+ remotePath);

			// util.downloadFile(remotePath, localPath, -1);

		}
	}

	private String compareReplaceAndBackupFile(String remoteUrl,
			String copyToDir, boolean needBackup, Hashtable hs)
			throws IOException, Exception {
		String timestamp = System.currentTimeMillis() + "";
		String configBakDir = copyToDir + "config/bak" + timestamp;
		Enumeration iterator = hs.keys();
		FTPUtil.getInstance().connect();
		while (iterator.hasMoreElements()) {
			String fileName = (String) iterator.nextElement();
			String remotePath = remoteUrl + fileName;
			String localPath = copyToDir + fileName;
			File currentFile = new File(localPath);
			if (currentFile.exists()) {
				if (needBackup) {
					File configbak = new File(configBakDir);
					if (!configbak.exists()) {
						configbak.mkdirs();
					}
					util.copyFile(localPath, configBakDir.concat("/").concat(fileName));
				}
				deleteFile(currentFile);
			}
			ui.setProgressMessage(Messages.getString("progress.download.file") + remotePath);

			util.downloadFile(remotePath, localPath, -1);

		}
		return configBakDir;
	}

	// 处理目录
	public void copyDirectory(String resDir, String destDir) throws IOException {
		File res = new File(resDir);
		if (!res.exists())
			return;

		File[] file = res.listFiles();
		if (file == null)
			return;

		for (int i = 0; i < file.length; i++) {
			String tmpRes = resDir + "/" + file[i].getName();
			String tmpDes = destDir + "/" + file[i].getName();
			if (file[i].isFile()) {
				String showmsg = tmpRes;
				if (showmsg.length() > NUM_50) {
					showmsg = tmpRes.substring(0, NUM_15) + "......"
							+ tmpRes.substring(tmpRes.length() - NUM_35);
				}
				ui.setProgressMessage(Messages.getString("progress.copy.file")
						+ showmsg);
				util.copyFile(tmpRes, tmpDes);
			} else {
				if (file[i].isDirectory()) {
					File dest2 = new File(tmpDes);
					dest2.mkdir();
					copyDirectory(tmpRes, tmpDes);
				}
			}
		}
	}

	private boolean deleteDirectory(String dir) {
		ui.setProgressMessage(Messages.getString("progress.delete.file") + dir);
		boolean isCancelUpdate = false;
		while (!util.deleteDirectory(dir)) {// 删除临时文件夹
			int result = ui.showWarn("WARNING",
					dir + Messages.getString("guide.err.file.inuse"),
					ui.getButtons1());
			if (result == 1) {
				result = ui.showWarn("WARNING",
						Messages.getString("guide.err.update.ignore"),
						ui.getButtons2());
				if (result == 0) {
					isCancelUpdate = true;
					break;
				}
			}
		}
		return isCancelUpdate;
	}

	/**
	 * 文件夹重命名，若发生错误将提示用户进行操作，知道操作成功或者用户取消操作
	 * 
	 * @param source
	 * @param dest
	 * @param buttons_1
	 * @param buttons_2
	 * @return true：取消操作
	 */
	private boolean renameDir(String source, String dest) {
		boolean isCancelUpdate = false;
		File local = new File(source);
		if (local.exists()) {

			File bak = new File(dest);
			while (!local.renameTo(bak)) {
				// 文件夹被打开导致重命名失败
				int result = ui.showWarn(
						"WARNING",
						localDirectory
								+ Messages.getString("guide.err.file.inuse"),
						ui.getButtons1());
				if (result == 1) {
					result = ui.showWarn("WARNING",
							Messages.getString("guide.err.update.ignore"),
							ui.getButtons2());
					if (result == 0) {
						isCancelUpdate = true;
						break;
					}
				}
			}
		}
		return isCancelUpdate;
	}

	private boolean deleteFile(File delFile) {
		boolean deleteOk = true;
		if (delFile.exists()) {
			while (!delFile.delete()) {
				// 文件删除失败
				int result = ui.showWarn("WARNING", delFile.getAbsolutePath()
						+ Messages.getString("guide.err.file.inuse"),
						ui.getButtons1());
				if (result == 1) {
					result = ui.showWarn("WARNING",
							Messages.getString("guide.err.update.ignore"),
							ui.getButtons2());
					if (result == 0) {
						deleteOk = false;
						break;
					}
				}
			}
		}
		return deleteOk;
	}

	/**
	 * 创建文件锁
	 * 
	 * @param dir
	 * @param module
	 * @return
	 */
	private boolean isAppRunning(String dir, String module) {
		try {
			File appDir = new File(dir);
			String parent = appDir.getParent();
			File home = new File(parent);
			if (!home.exists()) {
				return false;
			}
			File lock = new File(parent + "/" + module + ".lock");
			if (lock.exists()) {
				return !lock.delete();
			}

			lock.createNewFile();
			new RandomAccessFile(lock, "rw");
		} catch (Exception e) {
			//to do nothing
		}
		return false;
	}

	// private final String JRE_LOCAL_DIRECTORY = "jre6";
	// private final String JRE_META_FILE = "jreApplication.dat";
	// private final String JRE_MODULE_NAME = "jreApplication";

	/**
	 * 下载JRE环境
	 * 
	 * @return
	 */
	// private boolean downloadJre() {
	// try {
	// String remoteWebUrl = remoteHomeDirectory.concat(JRE_MODULE_NAME)
	// .concat("/");
	// String localDir = this.appHomeDir + JRE_LOCAL_DIRECTORY + "/";
	// String javaExe = localDir.concat("bin/javaw.exe");
	// File javafile = new File(javaExe);
	// if (javafile.exists()) {
	// return true;
	// }
	// Hashtable ht = getJreFileList(remoteWebUrl, localDir);
	// if (ht.size() > 0)
	// downloadFiles(ht, remoteWebUrl, localDir);
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	// }

	private boolean testJre() {
		String jreVersion = System.getProperty("java.version");
		if (jreVersion!=null 
				&& jreVersion.startsWith(PREFER_JRE_VERSION)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取远程服务器的文件列表
	 * 
	 * @param remoteDirPaht
	 * @param localDirPath
	 * @return
	 * @throws Exception
	 */
	// private Hashtable getJreFileList(String remoteDirPaht, String
	// localDirPath)
	// throws Exception {
	// String httpUrl = this.remoteHomeDirectory.concat(JRE_META_FILE);
	// Hashtable ht = readRemoteMetadata(httpUrl);
	// ui
	// .setGuideMessage(Messages
	// .getString("progress.compare.different.jre"));
	//
	// Hashtable diff = new Hashtable();
	// for (Enumeration iterator = ht.keys(); iterator.hasMoreElements();) {
	// String fileName = (String) iterator.nextElement();
	// if (fileName.startsWith(PREFIX_IDENTIFY))
	// continue;
	//
	// Long lastModified = (Long) ht.get(fileName);
	// String localFileName = this.appHomeDir + JRE_LOCAL_DIRECTORY + "/"
	// + fileName;
	// File localFile = new File(localFileName);
	// if (!localFile.exists()) {
	// diff.put(fileName, lastModified);
	// }
	// }
	// return diff;
	// }

}