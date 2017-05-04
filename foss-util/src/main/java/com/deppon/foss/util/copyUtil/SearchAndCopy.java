/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.action
 * FILE    NAME: FileDemo07.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.util.copyUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 查询拷贝文件
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-3-18 上午11:04:18
 */
public class SearchAndCopy {

	private static final Log LOGGER = LogFactory.getLog(SearchAndCopy.class);
	/**
	 * 文件名
	 */
	public static Map<String, String> FILE_NAME_MAP;
	/**
	 * 未找到的文件列表
	 */
	public static Map<String, String> UNEXIST_FILE_NAME_MAP;
	/**
	 * 日志内容
	 */
	public static Map<String, String> LOG_MAP = new HashMap<String, String>();
	/**
	 * 读取的excel文件（从哪个EXCEL文件读取）
	 */
	public static String EXCEL_FILE = "D:\\test.xls";
	/**
	 * 拷贝到的生成路径(拷贝到什么地方)
	 */
	public static String GENERAT_PATH = "F:\\core2";
	/**
	 * 代码扫描的根目录
	 */
	public static String READ_FILE_PATH = "F:\\foss_wk2\\foss";

	public static void main(String[] args) {
		// 在此目录中找文件
		// 查找目标目录
		String baseDIR = READ_FILE_PATH;
		// 写入目录
		String destDir = GENERAT_PATH;
		// 正式查找写入
		findAndCopyFile(baseDIR, destDir, EXCEL_FILE);

	}

	/**
	 * 查找拷贝文件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-18 下午1:14:51
	 */
	public static void findAndCopyFile(String baseDIR, String destDir, String excelFile) {
		// 在此目录中找文件
		// 查找目标目录
		// 实例读入工具
		ExcelOperate operate = new ExcelOperate();
		// 读出所有文件名
		FILE_NAME_MAP = operate.readFileNamesMap(excelFile);
		// 备份
		if (FILE_NAME_MAP != null) {
			if (operate.getDuplicFiles() != null) {
				String fileName;
				for (String key : operate.getDuplicFiles()) {
					fileName = key.split("-")[1];
					FILE_NAME_MAP.remove(fileName);

				}
			}
			UNEXIST_FILE_NAME_MAP = new HashMap<String, String>();
			UNEXIST_FILE_NAME_MAP.putAll(FILE_NAME_MAP);
		}
		// 根据EXCEL文件循环查询拷贝数据
		findAndCopyFiles(baseDIR, destDir);
		// 重复的文件
		if (operate.getDuplicFiles() != null) {
			for (String key : operate.getDuplicFiles()) {
				String[] tsa = key.split("#");
				LOGGER.info("重复的java文件:" + tsa[0] + "," + tsa[1]);

			}
		}

		// 重复的文件读取并拷贝
		findAndCopyDupFiles(baseDIR, destDir, operate.getDuplicFiles());

		// 成功扫描日志
		writeLog(LOG_MAP, GENERAT_PATH + "\\log\\okLog.txt");
		// 失败扫描日志
		if (UNEXIST_FILE_NAME_MAP != null) {
			LOGGER.info("读取失败条数:" + UNEXIST_FILE_NAME_MAP.size() + "条数据!");
			writeLog(UNEXIST_FILE_NAME_MAP, GENERAT_PATH + "\\log\\errorLog.txt");
		}
	}

	/**
	 * 写日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-18 下午1:15:28
	 */
	public static void writeLog(Map<String, String> map, String logPath) {
		if (StringUtils.isNotBlank(logPath)) {
			File tempFile = new File(logPath);
			if (map != null) {
				Iterator<String> it = map.keySet().iterator();
				List<String> lines = new ArrayList<String>();
				while (it.hasNext()) {
					String key = it.next();
					lines.add(key + "==>" + map.get(key));
				}
				try {
					FileUtils.writeLines(tempFile, "utf-8", lines, "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查找拷贝文件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-18 下午1:27:13
	 */
	private static void findAndCopyFiles(String baseDirName, String destDirName) {
		File baseDir = new File(baseDirName); // 创建一个File对象
		File destDir = new File(destDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) { // 判断目录是否存在
			LOGGER.info("文件查找失败：" + baseDirName + "不是一个目录！");
		}
		// 判断目录是否存在
		File tempFile;
		File[] files = baseDir.listFiles();
		String tempName;
		for (int i = 0; i < files.length; i++) {
			tempFile = files[i];
			if (tempFile.getName().indexOf(".svn") > 0 || tempFile.getName().indexOf(".setting") > 0
					|| tempFile.getName().indexOf("\\target") > 0) {
				continue;
			}

			if (tempFile.isDirectory()) {
				findAndCopyFiles(tempFile.getAbsolutePath(), destDirName);
			} else if (tempFile.isFile()) {
				tempName = tempFile.getName();
				if (FILE_NAME_MAP != null) {
					Iterator<String> it = FILE_NAME_MAP.keySet().iterator();
					String fname;
					while (it.hasNext()) {
						fname = it.next();
						if (tempName.equals(fname)) {
							try {
								FileUtils.copyFileToDirectory(tempFile, destDir);
								LOG_MAP.put(tempName, tempFile.getAbsolutePath());
								UNEXIST_FILE_NAME_MAP.remove(tempName);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 查找拷贝重复的文件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-18 下午1:27:13
	 */
	private static void findAndCopyDupFiles(String baseDirName, String destDirName, List<String> duplicFiles) {
		File baseDir = new File(baseDirName); // 创建一个File对象
		File destDir = null;
		if (!baseDir.exists() || !baseDir.isDirectory()) { // 判断目录是否存在

		}
		// 判断目录是否存在
		File tempFile;
		File[] files = baseDir.listFiles();
		String tempName;
		for (int i = 0; i < files.length; i++) {
			tempFile = files[i];
			if (tempFile.getName().indexOf(".svn") > 0 || tempFile.getName().indexOf(".setting") > 0
					|| tempFile.getName().indexOf("\\target") > 0) {
				continue;
			}

			if (tempFile.isDirectory()) {
				findAndCopyDupFiles(tempFile.getAbsolutePath(), destDirName, duplicFiles);
			} else if (tempFile.isFile()) {
				tempName = tempFile.getName();
				if (duplicFiles != null) {
					String fname;
					String pkgname;
					for (String st : duplicFiles) {
						String[] rs = st.split("#");
						fname = rs[0].trim();
						pkgname = rs[1].trim();
						if (tempName.equals(fname)) {
							LOGGER.info("找到重复的文件==>:" + tempName);
							try {
								destDir = new File(destDirName + "\\" + pkgname);
								FileUtils.copyFileToDirectory(tempFile, destDir);
								UNEXIST_FILE_NAME_MAP.remove(tempName);
								LOG_MAP.put(pkgname + "#" + tempName, tempFile.getAbsolutePath());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				}
			}
		}
	}
}
