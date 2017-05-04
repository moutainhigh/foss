package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity;
/**
 * 
 * 提前预警线路VO
 * @author 262036 - huangwei
 * @date 2015-8-19 下午6:10:02
 * @since
 * @version
 */
public class EarlyWarningVo implements Serializable{

	private static final long serialVersionUID = 1112789789541234545L;
	
	/**
	 * 提前预警线路实体类
	 */
	private EarlyWarningEntity entity;
	
	/**
	 * 提前预警线路集合
	 */
	private List<EarlyWarningEntity> entityList;
	
	//导出文件
	private String downloadFileName;
	
	private File uploadFile;
	
	private String uploadFileFileName;
	
	private String uploadFileContentType;
	
	private InputStream excelStream;
	
	//记录没导入成功的行数
	private List<Integer> numList;

	public List<EarlyWarningEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<EarlyWarningEntity> entityList) {
		this.entityList = entityList;
	}

	public EarlyWarningEntity getEntity() {
		return entity;
	}

	public void setEntity(EarlyWarningEntity entity) {
		this.entity = entity;
	}

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
	
}
