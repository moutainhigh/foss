package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 078816-WangPeng
 * @date   2014-02-17
 * @description 存放工作流上传附件的相关信息
 *
 */
public class WorkFlowAttachment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5221933694878529740L;

	 //工作流号
	 private String workFlowNo;
	 
	 //模块路径
	 private String modulePath;
	 
	 //文件名称
	 private String fileName;
	 
	 //文件大小
	 private String fileSize;
	  
	 //文件类型
	 private String fileType;
	 
	 //相对路径
	 private String relativePath;

	public String getWorkFlowNo() {
		return workFlowNo;
	}

	public void setWorkFlowNo(String workFlowNo) {
		this.workFlowNo = workFlowNo;
	}

	public String getModulePath() {
		return modulePath;
	}

	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	 
}
