package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.List;
/**
 * 
 * TODO(强装运单明细)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-20 下午6:24:00,content:TODO </p>
 * @author Administrator
 * @date 2013-3-20 下午6:24:00
 * @since
 * @version
 */
public class ForceLoadDetail {
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 流水号
	 */
	private List<String> serialNo;
	/**
	 * 货物状态
	 */
	private String goodsState;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public List<String> getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(List<String> serialNo) {
		this.serialNo = serialNo;
	}
	public String getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	
}
