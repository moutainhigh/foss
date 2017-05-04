package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口参数信息-省市聚合 
 * @author 239284
 *
 */
public class ParamsInfo {
	public String totalTicket;
	public String totalWeight;
	public String totalVolum;
	public String AdministrationCity;
	public List<TogetherInfo> districtTogetherList = new ArrayList<TogetherInfo>();//区级聚合
	
	public String getTotalTicket() {
		return totalTicket;
	}
	public void setTotalTicket(String totalTicket) {
		this.totalTicket = totalTicket;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getTotalVolum() {
		return totalVolum;
	}
	public void setTotalVolum(String totalVolum) {
		this.totalVolum = totalVolum;
	}
	public String getAdministrationCity() {
		return AdministrationCity;
	}
	public void setAdministrationCity(String administrationCity) {
		AdministrationCity = administrationCity;
	}
	public List<TogetherInfo> getDistrictTogetherList() {
		return districtTogetherList;
	}
	public void setDistrictTogetherList(List<TogetherInfo> districtTogetherList) {
		this.districtTogetherList = districtTogetherList;
	}

}
