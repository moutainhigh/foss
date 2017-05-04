package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ClassifiedIncomeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ClassifiedIncomeQueryDto;
public class ClassifiedIncomeVo {
	/**
	 * 重分类基础信息  查询参数Dto
	 *  @author 307196
	 */

	private ClassifiedIncomeQueryDto classifiedIncomeQueryDto;
	/**
	 * 重分类基础信息
	 */
	private ClassifiedIncomeEntity classifiedIncomeEntity;
	
	/**
	 * 重分类基础信息返回结果集
	 */
	private List<ClassifiedIncomeEntity> classifiedIncomeEntityList;
	
	private String downloadFileName;
	
	private File uploadFile;
	
	private String uploadFileFileName;
	
	private String uploadFileContentType;
	
	private InputStream excelStream;
	
	//记录没导入成功的行数
	private List<Integer> numList;
	
	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<Integer> getNumList() {
		return numList;
	}

	public void setNumList(List<Integer> numList) {
		this.numList = numList;
	}

	public List<ClassifiedIncomeEntity> getClassifiedIncomeEntityList() {
		return classifiedIncomeEntityList;
	}

	public void setClassifiedIncomeEntityList(
			List<ClassifiedIncomeEntity> classifiedIncomeEntityList) {
		this.classifiedIncomeEntityList = classifiedIncomeEntityList;
	}

	public ClassifiedIncomeQueryDto getClassifiedIncomeQueryDto() {
		return classifiedIncomeQueryDto;
	}

	public void setClassifiedIncomeQueryDto(
			ClassifiedIncomeQueryDto classifiedIncomeQueryDto) {
		this.classifiedIncomeQueryDto = classifiedIncomeQueryDto;
	}

	public ClassifiedIncomeEntity getClassifiedIncomeEntity() {
		return classifiedIncomeEntity;
	}

	public void setClassifiedIncomeEntity(
			ClassifiedIncomeEntity classifiedIncomeEntity) {
		this.classifiedIncomeEntity = classifiedIncomeEntity;
	}



	

	

	



}
