package com.deppon.esb.pojo.domain.foss2qms;

import java.io.Serializable;
import java.util.List;

public class ErrorResultEntity implements Serializable {

	private static final long serialVersionUID = 149063290877728567L;

	//处理结果
	private String dealResult;
	//处理时间
	private String dealTime;
	//处理结果明细
	private List<ErrorResultItemEntity> eriList;
	/**
	 * dealResult <p>getter method</p>
	 * @author 150976
	 * @return  the dealResult
	 */
	public String getDealResult() {
		return dealResult;
	}
	/**
	 * dealResult <p>setter method</p>
	 * @author 150976
	 * @param dealResult the dealResult to set
	 */
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	/**
	 * dealTime <p>getter method</p>
	 * @author 150976
	 * @return  the dealTime
	 */
	public String getDealTime() {
		return dealTime;
	}
	/**
	 * dealTime <p>setter method</p>
	 * @author 150976
	 * @param dealTime the dealTime to set
	 */
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	/**
	 * eriList <p>getter method</p>
	 * @author 150976
	 * @return  the eriList
	 */
	public List<ErrorResultItemEntity> getEriList() {
		return eriList;
	}
	/**
	 * eriList <p>setter method</p>
	 * @author 150976
	 * @param eriList the eriList to set
	 */
	public void setEriList(List<ErrorResultItemEntity> eriList) {
		this.eriList = eriList;
	}
	
	
}
