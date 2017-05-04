package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

public class WKLoadTempDto implements Serializable {
	
	
	/**
	* @fields serialVersionUID
	* @author 328864-foss-xieyang
	* @update 2016年5月9日 下午6:43:51
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	/*任务编号*/
	private String taskNo;
	/*任务类型1装车,2完成装车*/
	private int taskType;
	/*传给悟空的数据*/
	private String jsonData;

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}
