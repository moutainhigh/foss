package com.deppon.foss.shared.response;

import java.io.Serializable;

/**
 * 
* @description 修改约车信息返回参数（给悟空系统用）
* @version 1.0
* @author 283250-foss-ruilibao
* @update 2016-5-6 下午2:06:46
 */
//@XmlRootElement(name="responseEntity")
public class EditInviteVehicleInfoRespParEntity implements Serializable{
	
	// 序列化ID
	private static final long serialVersionUID = 3761476707437674022L;

	// 是否成功标识
	private boolean resultFlag;
	
	// 失败原因
	private String failureReason;
	
	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
}