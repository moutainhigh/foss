package com.deppon.pda.bdm.module.core.shared.domain;

import java.io.Serializable;

/**
 * 
 * TODO(任务扫描信息实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-4-15 下午3:47:31,content:TODO </p>
 * @author Administrator
 * @date 2013-4-15 下午3:47:31
 * @since
 * @version
 */
public class ScanMsgTaskEntity extends DomainEntity implements Serializable{
	/**
	 * @description
	 */      
	private static final long serialVersionUID = -3830145893270403418L;
	private String taskCode;

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	
}
