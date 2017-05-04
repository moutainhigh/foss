package com.deppon.foss.module.transfer.load.api.shared.dto;

import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanEntity;

public class TrayScanDto extends TrayScanEntity{

	private static final long serialVersionUID = -4849435612111728111L;
	
	/**
	 * 每个任务的叉车总票数
	 * */
	private Integer  forkliftCount;
	
	/**
	 * 所有任务的叉车总票数
	 * */
	private Integer  totalCount;
	
	/**
	 * 流水号总件数
	 * */
	private Integer seriaNoCount;
	
	/**
	 * 运单信息，是否打木架、是否贵重物品
	 * **/
	private String waybillInfo;

	//卸车任务编号
	private String unloadTaskNo;
	
	public String getWaybillInfo() {
		return waybillInfo;
	}

	public void setWaybillInfo(String waybillInfo) {
		this.waybillInfo = waybillInfo;
	}

	public Integer getSeriaNoCount() {
		return seriaNoCount;
	}

	public void setSeriaNoCount(Integer seriaNoCount) {
		this.seriaNoCount = seriaNoCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getForkliftCount() {
		return forkliftCount;
	}

	public void setForkliftCount(Integer forkliftCount) {
		this.forkliftCount = forkliftCount;
	}

	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

  


	
	

}
