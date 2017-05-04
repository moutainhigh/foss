package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.List;
/**
 * 
 * TODO(创建任务返回实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-11-8 上午11:17:59,content:TODO </p>
 * @author Administrator
 * @date 2012-11-8 上午11:17:59
 * @since
 * @version
 */
public class CreateTaskResult {

	private String createUser;
	//private List crgDetails;
	private List<UnloaderModel> userCodes;
	
	private String taskCode;
	
	/**
	 * 卸车类型
	 */
	private String unloadType;

	public String getUnloadType() {
		return unloadType;
	}

	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/*public List getCrgDetails() {
		return crgDetails;
	}

	public void setCrgDetails(List crgDetails) {
		this.crgDetails = crgDetails;
	}*/

	public List<UnloaderModel> getUserCodes() {
		return userCodes;
	}

	public void setUserCodes(List<UnloaderModel> userCodes) {
		this.userCodes = userCodes;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	

}
