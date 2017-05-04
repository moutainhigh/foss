package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class GpsVehicleDailySummaryDTO extends BaseEntity{

	 private static final long serialVersionUID = 1L;
	 //id
	 private String id;
	
	//车牌号
	 private String carnum;
	 //数据时间
	 private Date  Datadate;
	 //机构编码
	 private String Orgcode;
	 //机构名称
	 private String Orgname;
	 //车辆当日运行次数
	 private BigDecimal countofdailytask;
	 //车辆当日运行合格次数
	 private BigDecimal Countofsuctask;
	 //车辆当日运行时效
	 private BigDecimal Agingofdailytask;
	 //车辆当日时间利用率，百分比
	 private BigDecimal Ratioofdailytask;
	 //车辆当日总里程
	 private BigDecimal Totalofdailymileage;
	 //车辆线路实际运营里程
	 private BigDecimal Totalofdailytask;
	 //数据创建时间
	 private Date Createdtime;
	 //gps同步至foss数据时间
	 private Date SynchronousDataTime;
	 
	 public String getId() {
			return id;
		}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarnum() {
		return carnum;
	}
	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}
	public Date getDatadate() {
		return Datadate;
	}
	public void setDatadate(Date datadate) {
		Datadate = datadate;
	}
	public String getOrgcode() {
		return Orgcode;
	}
	public void setOrgcode(String orgcode) {
		Orgcode = orgcode;
	}
	public String getOrgname() {
		return Orgname;
	}
	public void setOrgname(String orgname) {
		Orgname = orgname;
	}
	public BigDecimal getCountofdailytask() {
		return countofdailytask;
	}
	public void setCountofdailytask(BigDecimal countofdailytask) {
		this.countofdailytask = countofdailytask;
	}
	public BigDecimal getCountofsuctask() {
		return Countofsuctask;
	}
	public void setCountofsuctask(BigDecimal countofsuctask) {
		Countofsuctask = countofsuctask;
	}
	public BigDecimal getAgingofdailytask() {
		return Agingofdailytask;
	}
	public void setAgingofdailytask(BigDecimal agingofdailytask) {
		Agingofdailytask = agingofdailytask;
	}
	public BigDecimal getRatioofdailytask() {
		return Ratioofdailytask;
	}
	public void setRatioofdailytask(BigDecimal ratioofdailytask) {
		Ratioofdailytask = ratioofdailytask;
	}
	public BigDecimal getTotalofdailymileage() {
		return Totalofdailymileage;
	}
	public void setTotalofdailymileage(BigDecimal totalofdailymileage) {
		Totalofdailymileage = totalofdailymileage;
	}
	public BigDecimal getTotalofdailytask() {
		return Totalofdailytask;
	}
	public void setTotalofdailytask(BigDecimal totalofdailytask) {
		Totalofdailytask = totalofdailytask;
	}
	public Date getCreatedtime() {
		return Createdtime;
	}
	public void setCreatedtime(Date createdtime) {
		Createdtime = createdtime;
	}
	public Date getSynchronousDataTime() {
		return SynchronousDataTime;
	}
	public void setSynchronousDataTime(Date synchronousDataTime) {
		SynchronousDataTime = synchronousDataTime;
	}

	
	
}
