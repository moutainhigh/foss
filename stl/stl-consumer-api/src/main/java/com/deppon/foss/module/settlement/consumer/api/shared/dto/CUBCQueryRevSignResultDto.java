package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;

/**
 * CUBC反签收申请接口返回结果集实体
 * @author 353654
 *
 */
public class CUBCQueryRevSignResultDto implements Serializable {
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6281135112341421511L;
	
	//返回是否可以反签收的标记  Y成功N失败
	private String resultMark;
	
	//异常信息
	private String meg;

	public String getResultMark() {
		return resultMark;
	}

	public void setResultMark(String resultMark) {
		this.resultMark = resultMark;
	}

	public String getMeg() {
		return meg;
	}

	public void setMeg(String meg) {
		this.meg = meg;
	}
	
}
