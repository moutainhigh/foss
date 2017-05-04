package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;
/**
 * 判断是否是同一部门标识
 * @ClassName TaskModel.java 
 * @Description 
 * @author 245955
 * @date 2015-4-27
 */
public class TaskModel {
	private  String TaskCode;
	private  String IsFlag;
	
	public String getIsFlag() {
		return IsFlag;
	}
	
	public void setIsFlag(String isFlag) {
		IsFlag = isFlag;
	}

	public String getTaskCode() {
		return TaskCode;
	}

	public void setTaskCode(String taskCode) {
		TaskCode = taskCode;
	}
	
}
