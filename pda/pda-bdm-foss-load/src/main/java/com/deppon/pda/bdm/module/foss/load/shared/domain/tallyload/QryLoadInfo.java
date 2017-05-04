package com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload;

/**
 * 刷新装车任务
 * 
 * @ClassName QryLoadInfo.java
 * @Description
 * @author 245955
 * @date 2015-4-24
 */
public class QryLoadInfo {

	private String taskCode;
	private String loadType;

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
}
