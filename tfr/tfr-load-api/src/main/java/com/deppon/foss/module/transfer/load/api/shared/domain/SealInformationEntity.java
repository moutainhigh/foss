package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.List;

/**
 * 封签信息实体类
 * @author 106162-gis-liping
 * @date 2016-04-25 上午10:25:28
 */
public class SealInformationEntity{

	/**
	 * version序列号
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 封签号信息
	 */
	private List<String>  sealNumberList;
	
	
	/**
	 * 成功标示
	 */
	private int isSuccess;
	
	/**
	 * 失败原因
	 */
	private String failMessage;
	
	
	
	
	
    /**
     * get...set...
     * @return
     */
	
	public int getIsSuccess() {
		return isSuccess;
	}

	public List<String> getSealNumberList() {
		return sealNumberList;
	}

	public void setSealNumberList(List<String> sealNumberList) {
		this.sealNumberList = sealNumberList;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SealInformationEntity() {
		super();
	}

	@Override
	public String toString() {
		return "SealInformationEntity [sealNumberList=" + sealNumberList
				+ ", isSuccess=" + isSuccess + ", failMessage=" + failMessage
				+ "]";
	}
	
}
