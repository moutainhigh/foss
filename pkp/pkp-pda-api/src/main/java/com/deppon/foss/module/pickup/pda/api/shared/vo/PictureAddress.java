package com.deppon.foss.module.pickup.pda.api.shared.vo;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.entity.BaseEntity;

public class PictureAddress extends BaseEntity{
	private static final long serialVersionUID = -8364559935496501670L;
	//运单号
	String waybillNo;
	//签收类型
	String signType;
	//各种照片
	Map<String,String> map;
	//其它照片
	List<String> otherPictures;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public List<String> getOtherPictures() {
		return otherPictures;
	}
	public void setOtherPictures(List<String> otherPictures) {
		this.otherPictures = otherPictures;
	}
}
