package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/***
 * 可视化自动排序节点详情Dto
 */
@SuppressWarnings("serial")
public class VisibleAutoSortNodeReturnInfoDto implements Serializable{

   private String id;	//id
   private String baiduLng;	//经度
   private String baiduLat;	//纬度
   private double deliveryTimeStart;	//开始时间
   private double deliveryTimeOver;	//结束时间
   private Integer serialNo;	//序号
   private double navDistance; //至下一节点距离
   
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getBaiduLng() {
	return baiduLng;
}
public void setBaiduLng(String baiduLng) {
	this.baiduLng = baiduLng;
}
public String getBaiduLat() {
	return baiduLat;
}
public void setBaiduLat(String baiduLat) {
	this.baiduLat = baiduLat;
}
public double getDeliveryTimeStart() {
	return deliveryTimeStart;
}
public void setDeliveryTimeStart(double deliveryTimeStart) {
	this.deliveryTimeStart = deliveryTimeStart;
}
public double getDeliveryTimeOver() {
	return deliveryTimeOver;
}
public void setDeliveryTimeOver(double deliveryTimeOver) {
	this.deliveryTimeOver = deliveryTimeOver;
}
public Integer getSerialNo() {
	return serialNo;
}
public void setSerialNo(Integer serialNo) {
	this.serialNo = serialNo;
}
public double getNavDistance() {
	return navDistance;
}
public void setNavDistance(double navDistance) {
	this.navDistance = navDistance;
}

}
