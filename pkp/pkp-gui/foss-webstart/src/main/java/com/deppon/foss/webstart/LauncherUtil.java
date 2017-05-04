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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/webstart/LauncherUtil.java
 * 
 * FILE NAME        	: LauncherUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.webstart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 业务无关工具包 (JDK1.3兼容)
 * 
 */
public class LauncherUtil {

	HttpClient client = new HttpClient();
	boolean inited = false;
	private static final int NUM_300000 = 300000 ;
	private static final int NUM_200 = 200 ;
	private static final int NUM_8096 = 8096 ;

	public void init(String host, int port) {
		if (!inited) {
			this.client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(NUM_300000);
			this.client.getHttpConnectionManager().getParams().setSoTimeout(
					NUM_300000);
			this.client.getHostConfiguration().setHost(host, port);
			inited = true;
		}
	}

	public void copyFile(String fromFileName, String toFileName)
			throws IOException {
		String frmpath = fromFileName;
		String topath = toFileName;
		File fromFile = new File(frmpath);
		File toFile = new File(topath);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());

		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFileName);
			toFile.delete();
		} else {
			File dir = toFile.getParentFile();
			if (!dir.exists())
				dir.mkdirs();
		}
		InputStream from = null;
		OutputStream to = null;
		try {
			from = new BufferedInputStream(new FileInputStream(fromFile), NUM_8096);
			to = new BufferedOutputStream(new FileOutputStream(toFile), NUM_8096);
			byte[] buffer = new byte[NUM_8096];
			int bytesRead = -1;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					//to do nothing
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					//to do nothing
				}
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param dir
	 */
	public boolean deleteDirectory(String dir) {

		return deleteAll(new File(dir));
	}

	/**
	 * 递归删除文件或目录
	 * 
	 * @param file
	 *            文件
	 */
	public boolean deleteAll(File file) {
		// String cmd = "CMD.exe /Q /C RD " + file.getAbsolutePath() + " /S /Q";
		// try {
		// Process process = Runtime.getRuntime().exec(cmd);
		// try {
		// process.waitFor();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// return false;
		// }
		// return true;

		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				// 如果是目录, 先删除其子目录
				File[] children = file.listFiles();
				if (children != null && children.length > 0)
					for (int i = 0; i < children.length; i++)
						deleteAll(children[i]); // 递归删除
			}
			if (!file.delete()) {
				return false; // 删除文件
			}
		}
		return true;

	}

	/**
	 * 下载文件
	 * 
	 * @param remotePath
	 *            远程路径
	 * @param localPath
	 *            本地路径
	 * @throws Exception
	 */
	public void downloadFile(String remotePath, String localPath)
			throws Exception {
		// http
		// downloadFile(remotePath, localPath, System.currentTimeMillis());

		// ftp
		FTPUtil.getInstance().download(remotePath, localPath,
				System.currentTimeMillis());
	}

	public void downloadFile(String remotePath, String localPath,
			long remoteLastTime) throws Exception {
		FTPUtil.getInstance().download(remotePath, localPath, remoteLastTime);
	}

	/**
	 * 下载文件
	 * 
	 * @param remotePath
	 *            远程路径
	 * @param localPath
	 *            本地路径
	 * @throws Exception
	 */
	public void downloadFileHttp(String remotePath, String localPath,
			long remoteLastTime) throws Exception {
		HttpMethod getMethod = null;
		try {
			String f = remotePath.trim();
			String r = replaceAll(f, " ", "%20");
			URL url = new URL(r);
			this.init(url.getHost(), url.getPort());
			getMethod = new GetMethod(url.getFile());
			int retCode = client.executeMethod(getMethod);
			if (retCode != NUM_200) {
				throw new IOException("can not found[" + url.getFile() + "]");
			}

			File destFile = new File(localPath);
			File dir = destFile.getParentFile();
			if (!dir.exists())
				dir.mkdirs();
			InputStream ioi = null;
			OutputStream ioo = null;
			try {
				ioi = new BufferedInputStream(getMethod
						.getResponseBodyAsStream());
				ioo = new BufferedOutputStream(new FileOutputStream(destFile));
				byte[] abyte = new byte[NUM_8096];
				int size = -1;
				while ((size = ioi.read(abyte)) != -1)
					ioo.write(abyte, 0, size);
			} catch (Exception e) {
				throw e;
			} finally {
				if (null != ioi)
					ioi.close();
				if (null != ioo)
					ioo.close();
			}

			if (remoteLastTime > 0)
				destFile.setLastModified(remoteLastTime);

		} catch (Exception e) {
			throw e;
		} finally {
			if (null != getMethod)
				getMethod.releaseConnection();
		}
	}

	/**
	 * 读取远程文件列表，并解析
	 * 
	 * @param httpUrl
	 * @return
	 * @throws Exception
	 */
	public Hashtable readRemoteMetadata(String httpUrl) throws Exception {
		Hashtable result = new Hashtable();
		HttpMethod getMethod = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(httpUrl);
			this.init(url.getHost(), url.getPort());
			getMethod = new GetMethod(url.getFile());
			int retCode = client.executeMethod(getMethod);
			if (retCode != NUM_200) {
				throw new IOException("can not found [" + url.getFile() + "]");
			}

			reader = new BufferedReader(new InputStreamReader(
					getMethod.getResponseBodyAsStream()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] tmpTxt = splits(line, "\\|");// line.split("\\|");
				if (null != tmpTxt && tmpTxt.length == 2)
					result.put(tmpTxt[0], Long.valueOf(tmpTxt[1]));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != getMethod)
				getMethod.releaseConnection();
			if (reader != null ) {
				reader.close();
			}
		}

		return result;
	}

	/**
	 * 清理Windows路径, 避开空格. 如: 将C:\Documents and Settings\app 清理成: C:\"Documents
	 * and Settings"\app
	 * 
	 * @param path
	 *            清理前路径
	 * @return 清理后路径
	 */
	// public String cleanWindowsPath(String path) {
	// if (path != null) {
	// String[] tokens = path.split("[\\/|\\\\]");
	// if (tokens != null && tokens.length > 0) {
	// StringBuffer buf = new StringBuffer();
	// for (int i = 0, n = tokens.length; i < n; i++) {
	// String token = tokens[i];
	// if (token != null && token.length() > 0) {
	// if (i != 0)
	// buf.append(File.separatorChar);
	// if (token.indexOf(' ') > -1) {
	// buf.append('\"');
	// buf.append(token);
	// buf.append('\"');
	// } else {
	// buf.append(token);
	// }
	// }
	// }
	// return buf.toString();
	// }
	// }
	// return path;
	// }
	/**
	 * 将字符串中的某一个子串完全替换，JDK1.4之前适用
	 * 
	 * @param sOld
	 *            原字符串
	 * @param s1
	 *            被替换的字符串
	 * @param s2
	 *            用于替换的字符串
	 * @author
	 */
	public String replaceAll(String sOld, String s1, String s2) {
		StringBuffer sbNew = new StringBuffer();
		int nBegin = 0;
		int nLen = s1.length();
		int nPos = sOld.indexOf(s1, nBegin);
		while (nPos != -1) {
			sbNew.append(sOld.substring(nBegin, nPos)).append(s2);
			nBegin = nPos + nLen;
			nPos = sOld.indexOf(s1, nBegin);
		}
		sbNew.append(sOld.substring(nBegin));
		return sbNew.toString();
	}

	public String[] splits(String text, String separator) {
		StringTokenizer st = new StringTokenizer(text, separator);
		String[] values = new String[st.countTokens()];
		int pos = 0;
		while (st.hasMoreTokens()) {
			values[pos++] = st.nextToken();
		}
		return values;
	}

}