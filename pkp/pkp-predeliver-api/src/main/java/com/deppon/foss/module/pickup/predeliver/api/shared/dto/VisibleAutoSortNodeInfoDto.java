package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 可视化自动排序节点详情Dto
 */
@SuppressWarnings("serial")
public class VisibleAutoSortNodeInfoDto implements Serializable{

   private String id;	//id
   private String gisID;	//gis ID
   private String baiduLng;	//经度
   private String baiduLat;	//纬度
   private double deliveryTimeStart;	//开始时间
   private double deliveryTimeOver;	//结束时间
   private Integer serialNo;	//序号
   private boolean flag;	//是否是小区
   
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getGisID() {
	return gisID;
}
public void setGisID(String gisID) {
	this.gisID = gisID;
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

public boolean isFlag() {
	return flag;
}
public void setFlag(boolean flag) {
	this.flag = flag;
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

}
