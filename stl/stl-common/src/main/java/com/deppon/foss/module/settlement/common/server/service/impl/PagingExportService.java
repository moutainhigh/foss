package com.deppon.foss.module.settlement.common.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.server.components.file.exception.FileAccessException;
import com.deppon.foss.framework.shared.compress.ZipCompressor;
import com.deppon.foss.module.settlement.common.api.server.service.IPagingExportDataSource;
import com.deppon.foss.module.settlement.common.api.server.service.IPagingExportService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.util.DateUtils;

/**
 * @描述：分批导出excel
 * @author 045738
 *
 */
public class PagingExportService implements IPagingExportService {
	
	/**
	 * 压缩导出excel
	 */
	@Override
	public ByteArrayInputStream pagingExport(IPagingExportDataSource dataSource,int pageSize)
			throws IOException {

		// 文件临时存放目录
		String tempDirectory = dataSource.tempDirectory();
		String directory = tempDirectory + File.separator
				+ System.currentTimeMillis();
		File directoryFile = new File(directory);
		if (!directoryFile.exists()) {
			if (!directoryFile.mkdirs()) {
				throw new FileAccessException("创建临时目录失败");
			}
		}

		// 导出xlsx文件名
		String filename = dataSource.filename();
		String extendname = ".xlsx";
		List<File> tempXlsxFiles = new ArrayList<File>();

		String[] headers = dataSource.headers();
		String[] mappings = dataSource.mappings();

		// 循环取出数据
		int page = 0;
		List dataList = dataSource.dataList(page++);
		while (CollectionUtils.isNotEmpty(dataList)) {

			String tempFileName = String.format("%s_%d%s", filename, page,
					extendname);
			exportExcel(dataList, headers, mappings, directory, tempFileName);

			tempXlsxFiles.add(new File(directory + File.separator
					+ tempFileName));
			if(dataList.size()>=pageSize){
				dataList = dataSource.dataList(page++);
			}else{
				break;
			}
		}

		// 压缩成ZIP文件
		String zipFilePath = compress(tempXlsxFiles, directory, filename);

		// 返回流
		return toByteArrayInputString(zipFilePath);
	}

	/**
	 * 导出文件
	 * 
	 * @param data
	 * @param directory
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public void exportExcel(List<Object> data, String[] headers,
			String[] mappings, String directory, String filename)
			throws IOException {

		// 创建excel工作簿
		OutputStream os = new FileOutputStream(directory + File.separator
				+ filename);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		XSSFCellStyle headStyle = headStyle(wb);
		XSSFCellStyle bodyStyle = bodyStyle(wb);
		
		// 设置表头
		XSSFRow headRow = sheet.createRow(0);
		int length = headers.length;
		for (int i = 0; i < length; i++) {
			XSSFCell cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(headers[i]);
			
		}

		Object obj = null;
		// 设置表数据
		XSSFRow row = null;
		for (int i = 0; i < data.size(); i++) {
			obj = data.get(i);
			row = sheet.createRow(i + 1);

			for (int j = 0; j < length; j++) {
				XSSFCell cell = row.createCell(j);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(getObjectValue(obj, mappings[j]));
			} 
		}

		// 写文件
		wb.write(os);
		// 关闭输出流
		os.close();
	}

	private String getObjectValue(Object obj, String fieldName) {

		String value = null;
		Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
		if (field != null) {
			ReflectionUtils.makeAccessible(field);
			Object fieldValue = ReflectionUtils.getField(field, obj);
			if (fieldValue != null) {
				if (Date.class.equals(field.getType())) {
					//日期转化
					value = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
				}else{
					value = String.valueOf(fieldValue);
				}
			}
		}
		return value;

	}

	/**
	 * 压缩
	 * 
	 * @param files
	 * @param directory
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	private String compress(List<File> files, String directory, String filename)
			throws IOException {

		// 压缩文件
		String extendname = ".zip";
		String zipFilePath = directory + File.separator + filename + extendname;
		File zipFile = new File(zipFilePath);

		ZipOutputStream zos = new ZipOutputStream(zipFile);
		zos.setEncoding(SettlementConstants.UNICODE_UTF);
		ZipEntry entry = null;
		FileInputStream fis = null;
		for (File file : files) {
			entry = new ZipEntry(file.getName());
			zos.putNextEntry(entry);
			fis = new FileInputStream(file);
			IOUtils.copy(fis, zos);
			IOUtils.closeQuietly(fis);
		}

		zos.flush();
		IOUtils.closeQuietly(zos);

		// 删除文件
		for (File xlsx : files) {
			xlsx.delete();
		}
		return zipFilePath;
	}

	/**
	 * 将文件转化成内存中的输入流
	 * 
	 * @param path
	 * @return
	 */
	private ByteArrayInputStream toByteArrayInputString(String path) {
		ByteArrayInputStream bas = null;
		FileInputStream fis = null;
		File file = null;
		try {
			file = new File(path);
			fis = new FileInputStream(file);
			int available = fis.available();
			byte[] buffer = new byte[available];
			fis.read(buffer);
			bas = new ByteArrayInputStream(buffer);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				IOUtils.closeQuietly(fis);
			}
			if (file != null) {
				file.delete();
				File parent = file.getParentFile();
				if (parent != null) {
					parent.delete();
				}
			}
		}
		return bas;
	}
	
	/**
	 * 标题样式
	 * @return HSSFCellStyle
	 */
	public static XSSFCellStyle headStyle(Workbook work) {
		XSSFCellStyle style = (XSSFCellStyle) work.createCellStyle();  
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setBorderLeft((short)1);
		style.setBorderRight((short)1);
	    return style;
	}
	
	/**
	 * 单元格样式
	 * @return HSSFCellStyle
	 */
	public static XSSFCellStyle bodyStyle(Workbook work) {
		XSSFCellStyle nameStyle = (XSSFCellStyle) work.createCellStyle();
        nameStyle.setBorderBottom((short)1);
        nameStyle.setBorderLeft((short)1);
        nameStyle.setBorderRight((short)1);
        nameStyle.setBorderTop((short)1);
        nameStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
        nameStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return nameStyle;
	}
	
	private ZipCompressor compresser;

	private ZipCompressor getCompresser() {
		if (compresser == null) {
			compresser = new ZipCompressor();
		}
		return compresser;
	}
	
}
