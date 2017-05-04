package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SourceCategoriesDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.SourceCategoriesCondition;

/**
 * 行业货源类别VO
 * @author 198771
 *
 */
public class SourceCategoriesVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 144646464894796L;
	
	private SourceCategoriesEntity sourceCategoriesEntity;
	
	//查询条件
	private SourceCategoriesCondition condition;

	private List<SourceCategoriesDto> sourceCategoriesDtos;
	
	private List<String> sourceCategoriesIds;
	
	//是否是导出模板
	private boolean template;
	
	private InputStream excelStream;
	
	//导出
	private String downloadFileName;
	
	private File uploadFile;
	
	private String uploadFileFileName;
	
	private String uploadFileContentType;
	
	//记录没导入成功的行数
	private List<Integer> numList;
	
	
	public List<Integer> getNumList() {
		return numList;
	}

	public void setNumList(List<Integer> numList) {
		this.numList = numList;
	}

	public SourceCategoriesEntity getSourceCategoriesEntity() {
		return sourceCategoriesEntity;
	}

	public void setSourceCategoriesEntity(
			SourceCategoriesEntity sourceCategoriesEntity) {
		this.sourceCategoriesEntity = sourceCategoriesEntity;
	}

	public SourceCategoriesCondition getCondition() {
		return condition;
	}

	public void setCondition(SourceCategoriesCondition condition) {
		this.condition = condition;
	}

	public List<SourceCategoriesDto> getSourceCategoriesDtos() {
		return sourceCategoriesDtos;
	}

	public void setSourceCategoriesDtos(
			List<SourceCategoriesDto> sourceCategoriesDtos) {
		this.sourceCategoriesDtos = sourceCategoriesDtos;
	}

	public List<String> getSourceCategoriesIds() {
		return sourceCategoriesIds;
	}

	public void setSourceCategoriesIds(List<String> sourceCategoriesIds) {
		this.sourceCategoriesIds = sourceCategoriesIds;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public boolean isTemplate() {
		return template;
	}

	public void setTemplate(boolean template) {
		this.template = template;
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
	
}
