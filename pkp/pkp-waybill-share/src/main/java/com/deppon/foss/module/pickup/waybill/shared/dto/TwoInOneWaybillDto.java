package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 子母件查询通用接口
 * @author Foss-105888-Zhangxingwang
 * @date 2015-8-24 16:56:01
 */
public class TwoInOneWaybillDto implements Serializable {
	private static final long serialVersionUID = -2019633311520312159L;
	//是否子母件
	private String isTwoInOne;

	//母单号
	private String mainWaybillNo;
	
	//子单号集合
	private List<String> waybillNoList;
	
	public String getIsTwoInOne() {
		return isTwoInOne;
	}

	public void setIsTwoInOne(String isTwoInOne) {
		this.isTwoInOne = isTwoInOne;
	}

	public String getMainWaybillNo() {
		return mainWaybillNo;
	}

	public void setMainWaybillNo(String mainWaybillNo) {
		this.mainWaybillNo = mainWaybillNo;
	}

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
}