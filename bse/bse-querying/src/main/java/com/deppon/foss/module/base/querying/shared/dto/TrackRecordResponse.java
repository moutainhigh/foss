package com.deppon.foss.module.base.querying.shared.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;

/**
 * 跟踪记录查询接口的响应实体
 * 
 * @author 310854
 * @date 2016-5-17
 */
@XmlRootElement(name = "TrackRecordResponse")
public class TrackRecordResponse {

	/**
	 * 查询是否成功
	 * 1   参数为空
	 * 2  系统异常
	 * 3  结果为空
	 * 4  成功  
	 */
	private int resultType;

	/**
	 * 跟踪记录
	 */
	private List<TrackRecordEntity>  records;
	
	/**
	 * 信息
	 */
	private String message;


	public int getResultType() {
		return resultType;
	}

	public void setResultType(int resultType) {
		this.resultType = resultType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<TrackRecordEntity> getRecords() {
		return records;
	}

	public void setRecords(List<TrackRecordEntity> records) {
		this.records = records;
	}
}
