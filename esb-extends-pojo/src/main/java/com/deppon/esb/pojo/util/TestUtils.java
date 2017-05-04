package com.deppon.esb.pojo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 测试工具类.
 * 
 * @author HuangHua
 * @date 2012-12-20 下午5:42:42
 */
public class TestUtils {
	
	// 需要扫描的文件路径
	/** The Constant FILE_PATH. */
	private static final String FILE_PATH = "O:/trunk/CODE/esb-extends/esb-extends-pojo/src/main/java/com/deppon/esb/pojo/transformer/jaxb/";

	// 模板文件
	/** The Constant MODEL_FILE. */
	private static final String MODEL_FILE = "O:/trunk/CODE/esb-extends/esb-extends-pojo/src/main/resources/trans-test-model.txt";

	// 文件后缀，如xxxTrans.java
	/** The Constant SUFFIX. */
	private static final String SUFFIX = "Test.java";

	// 需要保存的文件路径
	/** The Constant SAVE_FILE_PATH_PREFIX. */
	private static final String SAVE_FILE_PATH_PREFIX = System
			.getProperty("user.dir") + "/src/test/java/";
	
	/** The log. */
	private static Log log = LogFactory.getLog(TestUtils.class);

	/**
	 * 程序入口.
	 * 
	 * @param args
	 *            the arguments
	 * @author HuangHua
	 * @date 2012-12-20 下午7:59:11
	 */
	public static void main(String[] args) {
		File file = new File(FILE_PATH);
		List<String> list = new ArrayList<String>();// 类全名列表
		traversal(file, null, list, ".java");
		log.info("generate file size :" + list.size());
		String content = readFileAsString(MODEL_FILE);
		// 转换并保存文件
		for (String string : list) {
			String result = fillParam(content, string);
			String saveFilePath = SAVE_FILE_PATH_PREFIX
					+ string.replaceAll("\\.", "/") + SUFFIX;
			writeString2File(result, saveFilePath);
			// saveList.add(saveFilePath);
		}
	}

	/**
	 * 遍历所有的set方法，最后生成代码.
	 * 
	 * @param clzz
	 *            the clzz
	 * @return the string buffer
	 * @author HuangHua
	 * @date 2012-12-20 下午7:07:12
	 */
	public static StringBuffer generateSetMethod(
			@SuppressWarnings("rawtypes") Class clzz) {
		StringBuffer result = new StringBuffer();
		String classSimpleName = clzz.getSimpleName();
		Method[] methods = clzz.getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				result.append("\t\t");
				result.append(classSimpleName.substring(0, 1).toLowerCase());
				result.append(classSimpleName.substring(1,
						classSimpleName.length()));
				result.append(".");
				result.append(method.getName());
				result.append("(");
				Class<?>[] classes = method.getParameterTypes();
				String paraName = classes[0].getName();
				if ("java.lang.String".equals(paraName)) {
					result.append("\"string\"");
				} else if ("boolean".equals(paraName)) {
					result.append("true");
				} else if ("java.sql.Timestamp".equals(paraName)) {
					result.append("new java.sql.Timestamp(0)");
				} else if ("java.math.BigDecimal".equals(paraName)) {
					result.append("java.math.BigDecimal.ZERO");
				} else if ("int".equals(paraName)) {
					result.append("1");
				} else if ("double".equals(paraName)) {
					result.append("1d");
				} else if ("long".equals(paraName)) {
					result.append("1l");
				} else if ("java.util.Date".equals(paraName)) {
					result.append("new java.util.Date()");
				} else if ("java.lang.Integer".equals(paraName)) {
					result.append("new java.lang.Integer(1)");
				} else if ("float".equals(paraName)) {
					result.append("2f");
					log.info("=========="+classSimpleName + ":" + paraName);
				} else {
					log.info(classSimpleName + ":" + paraName);
				}
				result.append(");\n");
			}
		}
		log.info(result);
		return result;
	}

	/**
	 * 生成对象的代码.
	 * 
	 * @param clzz
	 *            the clzz
	 * @return the string
	 * @author HuangHua
	 * @date 2012-12-20 下午7:14:20
	 */
	public static String generateObject(@SuppressWarnings("rawtypes") Class clzz) {
		StringBuffer result = new StringBuffer();
		String classSimpleName = clzz.getSimpleName();
		result.append(classSimpleName);
		result.append(" ");
		result.append(classSimpleName.substring(0, 1).toLowerCase());
		result.append(classSimpleName.substring(1, classSimpleName.length()));
		result.append(" = new ");
		result.append(classSimpleName);
		result.append("();\n");
		result.append(generateSetMethod(clzz));
		return result.toString();
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
	 * 遍历文件夹下的所有文件(包括所有子目录).
	 * 
	 * @param file
	 *            the file
	 * @param path
	 *            the path
	 * @param fileList
	 *            the file list
	 * @param filter
	 *            endWith
	 */
	public static void traversal(File file, String path, List<String> fileList,
			String filter) {
		if (!file.isDirectory()) {
			return;
		}
		File[] files = file.listFiles(); // 获取文件夹下面的所有文件
		for (File f : files) {
			// 判断是否为文件夹
			if (f.isDirectory()) {
				traversal(f, f.getName(), fileList, filter); // 如果是文件夹，重新遍历
			} else { // 文件
				if (f.getAbsolutePath().endsWith(filter)) {
					String fileFullName = f.getAbsolutePath();
					String split = "src\\main\\java";
					String result = fileFullName.substring(fileFullName
							.indexOf(split) + split.length() + 1);
					result = result.substring(0, result.indexOf(".java"));
					result = result.replaceAll("\\\\", ".");
					fileList.add(result);
				}
			}
		}
	}

	/**
	 * 根据类全名来替换文本内容.
	 * 
	 * @param content
	 *            待转换的文本内容
	 * @param string
	 *            the string
	 * @return the string
	 */
	public static String fillParam(String content, String string) {
		String fullClassName = string;
		String packageName = fullClassName.substring(0,
				fullClassName.lastIndexOf('.')); // 包名
		String className = fullClassName.substring(fullClassName
				.lastIndexOf('.') + 1);// 获取java类名
		String varClassName = className.substring(0, 1).toLowerCase()
				+ className.substring(1, className.length());// 实体类名的首字母小写
		String generateObject = null;

		String domainClassName = "";
		String varDomainClassName = "";
		try {
			Method[] methods = Class.forName(fullClassName).getMethods();
			for (Method method : methods) {
				if ("fromMessage".equals(method.getName())) {
					@SuppressWarnings("rawtypes")
					Class[] clazz = method.getParameterTypes();
					if ("java.lang.Object".equals(clazz[0].getName())) {
						continue;
					}
					domainClassName = clazz[0].getName();
				}
			}
			generateObject = generateObject(Class.forName(domainClassName));

			varDomainClassName = domainClassName.substring(
					domainClassName.lastIndexOf(".") + 1,
					domainClassName.length());
			varDomainClassName = varDomainClassName.substring(0, 1)
					.toLowerCase()
					+ varDomainClassName.substring(1,
							varDomainClassName.length());// 实体类名的首字母小写

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		// 替换
		String result = content
				.replaceAll("\\$\\{fullClassName\\}", fullClassName)
				.replaceAll("\\$\\{packageName\\}", packageName)
				.replaceAll("\\$\\{className\\}", className)
				.replaceAll("\\$\\{generateObject\\}", generateObject)
				.replaceAll("\\$\\{varDomainClassName\\}", varDomainClassName)
				.replaceAll("\\$\\{domainClassName\\}", domainClassName)
				.replaceAll("\\$\\{varClassName\\}", varClassName);
		return result;
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
		if (new File(fileName).exists()) {
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
