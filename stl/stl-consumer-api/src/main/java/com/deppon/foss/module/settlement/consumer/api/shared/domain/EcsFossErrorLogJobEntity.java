package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递对接FOSS,JOB定时执行该表异常日志
 * @author 326181
 *
 */
public class EcsFossErrorLogJobEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编码类型，对应代码
	 */
	private String codeType;
	
	/**
	 * 编码名称,对应需求名称
	 */
	private String codeName;
	
	/**
	 * 方法参数
	 */
	private String requestMsg;
	
	/**
	 * 初始异常信息
	 */
	private String errorMsg;
	
	/**
	 * JOB执行异常信息
	 */
	private String jobErrorMsg;
	
	/**
	 * 是否有效 Y有效(异常数据) N无效(job定时修改成功的数据)
	 */
	private String active;

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getRequestMsg() {
		return requestMsg;
	}

	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getJobErrorMsg() {
		return jobErrorMsg;
	}

	public void setJobErrorMsg(String jobErrorMsg) {
		this.jobErrorMsg = jobErrorMsg;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}

