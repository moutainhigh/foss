package com.deppon.esb.pojo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 转换类生成器，如果需要生成的文件已经存在，会自动跳过.
 * 
 * @author HuangHua
 */
public class JibxTransUtil {
	// 需要扫描的文件路径
	/** The Constant FILE_PATH. */
	private static final String FILE_PATH = "O:/trunk/CODE/esb-extends/esb-extends-pojo/target/generated-sources/jibx";
	// 需要保存的文件路径
	/** The Constant SAVE_FILE_PATH_PREFIX. */
	private static final String SAVE_FILE_PATH_PREFIX = System
			.getProperty("user.dir")
			+ "/src/main/java/com/deppon/esb/pojo/transformer/";
	// 文件后缀，如xxxTrans.java
	/** The Constant SUFFIX. */
	private static final String SUFFIX = "Trans.java";

	/** The log. */
	private static Log log = LogFactory.getLog(JibxTransUtil.class);

	/**
	 * 执行这个方法，开始生成转换类。.
	 * 
	 * @param a
	 *            the arguments
	 */
	public static void main(String[] a) {
		File file = new File(FILE_PATH);
		List<File> list = new ArrayList<File>();
		traversal(file, ".java", list);
		log.info(list.size());
		String content = readFileAsString("O:/trunk/CODE/esb-extends/esb-extends-pojo/src/main/resources/jibx-model.txt");

		List<String> saveList = new ArrayList<String>();
		// 转换并保存文件
		for (File textFile : list) {
			String result = fillParam(content, textFile);
			String saveFilePath = SAVE_FILE_PATH_PREFIX
					+ textFile.getName().substring(0,
							textFile.getName().lastIndexOf(".java")) + SUFFIX;
			writeString2File(result, saveFilePath);
			saveList.add(saveFilePath);
		}
		// 保存生成文件列表信息
		StringBuffer stringBuffer = new StringBuffer(new Date().toString());
		stringBuffer.append("==start========================");
		for (String string : saveList) {
			stringBuffer.append("\n");
			stringBuffer.append(string);
		}
		stringBuffer.append("\n");
		stringBuffer.append(new Date());
		stringBuffer.append("==end========================");
		File saveListFile = new File(System.getProperty("user.dir")
				+ "/autoGenerateTrans.log");
		writeString2File(stringBuffer.toString(),
				saveListFile.getAbsolutePath());
	}

	/**
	 * 根据文件的路径来替换文本内容.
	 * 
	 * @param content
	 *            待转换的文本内容
	 * @param file
	 *            the file
	 * @return the string
	 */
	public static String fillParam(String content, File file) {
		String regStr = "target\\generated-sources\\jibx\\";
		String fullPath = file.getAbsolutePath();// 获取文件全路径
		String fullClassName = fullPath.substring(fullPath.indexOf(regStr)
				+ regStr.length());
		fullClassName = fullClassName.replaceAll("\\\\", ".");
		fullClassName = fullClassName.substring(0,
				fullClassName.indexOf(".java"));// 标准java格式的全类路径
		String className = fullClassName.substring(fullClassName
				.lastIndexOf('.') + 1);// 获取java类名
		// 替换
		String result = content.replaceAll("\\$\\{fullClassName\\}",
				fullClassName).replaceAll("\\$\\{className\\}", className);
		return result;
	}

	/**
	 * 遍历文件夹下的所有文件(包括所有子目录).
	 * 
	 * @param file
	 *            the file
	 * @param filter
	 *            endWith
	 * @param fileList
	 *            the file list
	 */
	public static void traversal(File file, final String filter,
			List<File> fileList) {
		if (!file.isDirectory()) {
			return;
		}
		File[] files = file.listFiles(); // 获取文件夹下面的所有文件
		for (File f : files) {
			// 判断是否为文件夹
			if (f.isDirectory()) {
				traversal(f, filter, fileList); // 如果是文件夹，重新遍历
			} else { // 如果是文件 就打印文件的路径
				if (f.getAbsolutePath().endsWith(filter)) {
					fileList.add(f);
				}
			}
		}
	}

	/**
	 * 把文本文件里的内容转换为String.
	 * 
	 * @param filePath
	 *            the file path
	 * @return the string
	 */
	public static String readFileAsString(String filePath) {
		StringBuffer fileData = new StringBuffer(1000);
		FileReader fileReader = null;
		BufferedReader reader = null;
		try {
			fileReader = new FileReader(filePath);
			reader = new BufferedReader(fileReader);
			char[] buf = new char[1024];
			int numRead = reader.read(buf);
			while (numRead != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
				numRead = reader.read(buf);
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		try {
			if (reader != null) {
				reader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return fileData.toString();
	}

	/**
	 * 把文本内容输出到文件.
	 * 
	 * @param content
	 *            文本内容
	 * @param fileName
	 *            文件绝对路径名
	 */
	public static void writeString2File(String content, String fileName) {
		if (new File(fileName).exists() && !fileName.endsWith(".log")) {
			return;
		}
		FileWriter fileWriter = null;
		BufferedWriter out = null;
		try {
			fileWriter = new FileWriter(fileName, true);
			out = new BufferedWriter(fileWriter);
			out.write(content);
			out.flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
