package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
/**
 * @功能 接送货提供给官网返回走货路径主要参数值
 * @author 105888-foss-zhangxingwang
 * @date 2013-8-31 12:09:38
 *
 */
public class FreightRouteCommonsDto implements Serializable{
	private static final long serialVersionUID = 1L;
	//请求参数ID
	private String paramsId;
	//只能选择true、false
	private String isStarFlag;
	//最终外场ID
	private String finalOutFieldId;
	//最终外场编码
	private String lastTransCenterNo;
	//最终外场城市名称
	private String lastTransCenterCity;
	//外场编码1
	private String addr1;
	//外场编码2
	private String addr2;
	//外场编码3
	private String addr3;
	//外场编码4
	private String addr4;
	//货区1
	private String location1;
	//货区2
	private String location2;
	//货区3
	private String location3;
	//货区4
	private String location4;
	//是否需要判断AB货
	private String isSureAB;
	//部门StationNumber
	private String destinationCode;
	//目的站简称
	private String destination;
	public String getParamsId() {
		return paramsId;
	}
	public void setParamsId(String paramsId) {
		this.paramsId = paramsId;
	}
	public String getIsStarFlag() {
		return isStarFlag;
	}
	public void setIsStarFlag(String isStarFlag) {
		this.isStarFlag = isStarFlag;
	}
	public String getFinalOutFieldId() {
		return finalOutFieldId;
	}
	public void setFinalOutFieldId(String finalOutFieldId) {
		this.finalOutFieldId = finalOutFieldId;
	}
	public String getLastTransCenterNo() {
		return lastTransCenterNo;
	}
	public void setLastTransCenterNo(String lastTransCenterNo) {
		this.lastTransCenterNo = lastTransCenterNo;
	}
	public String getLastTransCenterCity() {
		return lastTransCenterCity;
	}
	public void setLastTransCenterCity(String lastTransCenterCity) {
		this.lastTransCenterCity = lastTransCenterCity;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getAddr4() {
		return addr4;
	}
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}
	public String getLocation1() {
		return location1;
	}
	public void setLocation1(String location1) {
		this.location1 = location1;
	}
	public String getLocation2() {
		return location2;
	}
	public void setLocation2(String location2) {
		this.location2 = location2;
	}
	public String getLocation3() {
		return location3;
	}
	public void setLocation3(String location3) {
		this.location3 = location3;
	}
	public String getLocation4() {
		return location4;
	}
	public void setLocation4(String location4) {
		this.location4 = location4;
	}
	public String getIsSureAB() {
		return isSureAB;
	}
	public void setIsSureAB(String isSureAB) {
		this.isSureAB = isSureAB;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
