package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * TODO完成任务实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:200689,date:2015-1-28 下午5:20:44,content:TODO </p>
 * @author 200689
 * @date 2015-1-28 下午5:20:44
 * @since
 * @version
 */
public class FinishTaskEntity  extends ScanMsgEntity implements Serializable{

    
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	//任务号
	private String  taskCode;
	
	//快递员工号
	private String userCode;
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
}
