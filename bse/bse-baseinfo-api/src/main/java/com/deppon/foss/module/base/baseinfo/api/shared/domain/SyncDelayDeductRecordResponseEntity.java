package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;
/**
 * 
 * 同步延时扣款查询列表返回参数
 * @author 308861 
 * @date 2016-10-31 下午4:24:09
 * @since
 * @version
 */
public class SyncDelayDeductRecordResponseEntity{
	/**
	 * 是否成功0：成功；1：失败
	 */
	private String isSuccess;
	/**
	 * 异常信息
	 */
	private String exptionMSG;
	/**
	 * 返回实体集合
	 */
	private List<DelayDeductRecordEntity> recordList;
	/**
	 * 查询集合总数
	 */
	private Long totalCount;
	
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getExptionMSG() {
		return exptionMSG;
	}
	public void setExptionMSG(String exptionMSG) {
		this.exptionMSG = exptionMSG;
	}
	public List<DelayDeductRecordEntity> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<DelayDeductRecordEntity> recordList) {
		this.recordList = recordList;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
}
