package com.deppon.foss.module.settlement.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;

/**
 * @项目：智能开单项目
 * @功能：定义所有的时间统计
 * @author:218371-foss-zhaoyanjun
 * @date:2016-05-19上午10:14
 */
public class IntelligenceBillTimeGather implements Serializable{
	/**
	 * uid  20160531  hujinyang add 278328
	 */
	private static final long serialVersionUID = 1L;

	//id
	private String id;
	
	//旋转图片时间
	private Double rotatePictureTime;
	
	//发货人电话号码输入时间
	private Double consignerTelliphoneTime;
	
	//收货人电话号码输入时间
	private Double consigneeTelliphoneTime;
	
	//运输性质选择时间
	private Double combProductTypeTime;
	
	//提货方式选择时间
	private Double combPickModeTime;
	
	//返单类型选择时间
	private Double combReturnBillTypeTime;
	
	//付款方式
	private Double combPaymentModeTime;
	
	//收发件人电话号码输入总时间
	private Double ccTotleTime;
	
	//面单服务总时间
	private Double bsTotleTime;
	
	//开单总时间
	private Double billTotleTime;
	
	//图片开单开始时间
	private Date pictureBillStartTime;
	
	//图片开单结束时间
	private Date pictureBillEndTime;
	
	//运单号
	private String waybillNo;
	
	//开单时间
	private Date date;

	public Double getRotatePictureTime() {
		return rotatePictureTime==null?0:rotatePictureTime;
	}

	public void setRotatePictureTime(Double rotatePictureTime) {
		this.rotatePictureTime = rotatePictureTime;
	}

	public Double getConsignerTelliphoneTime() {
		return consignerTelliphoneTime==null?0:consignerTelliphoneTime;
	}

	public void setConsignerTelliphoneTime(Double consignerTelliphoneTime) {
		this.consignerTelliphoneTime = consignerTelliphoneTime;
	}

	public Double getConsigneeTelliphoneTime() {
		return consigneeTelliphoneTime==null?0:consigneeTelliphoneTime;
	}

	public void setConsigneeTelliphoneTime(Double consigneeTelliphoneTime) {
		this.consigneeTelliphoneTime = consigneeTelliphoneTime;
	}

	public Double getCombProductTypeTime() {
		return combProductTypeTime==null?0:combProductTypeTime;
	}

	public void setCombProductTypeTime(Double combProductTypeTime) {
		this.combProductTypeTime = combProductTypeTime;
	}

	public Double getCombPickModeTime() {
		return combPickModeTime==null?0:combPickModeTime;
	}

	public void setCombPickModeTime(Double combPickModeTime) {
		this.combPickModeTime = combPickModeTime;
	}

	public Double getCombReturnBillTypeTime() {
		return combReturnBillTypeTime==null?0:combReturnBillTypeTime;
	}

	public void setCombReturnBillTypeTime(Double combReturnBillTypeTime) {
		this.combReturnBillTypeTime = combReturnBillTypeTime;
	}

	public Double getCombPaymentModeTime() {
		return combPaymentModeTime;
	}

	public void setCombPaymentModeTime(Double combPaymentModeTime) {
		this.combPaymentModeTime = combPaymentModeTime;
	}

	public Double getCcTotleTime() {
		return ccTotleTime;
	}

	public void setCcTotleTime(Double ccTotleTime) {
		this.ccTotleTime = ccTotleTime;
	}

	public Double getBsTotleTime() {
		return bsTotleTime;
	}

	public void setBsTotleTime(Double bsTotleTime) {
		this.bsTotleTime = bsTotleTime;
	}

	public Double getBillTotleTime() {
		return billTotleTime;
	}

	public void setBillTotleTime(Double billTotleTime) {
		this.billTotleTime = billTotleTime;
	}

	public Date getPictureBillStartTime() {
		return pictureBillStartTime;
	}

	public void setPictureBillStartTime(Date pictureBillStartTime) {
		this.pictureBillStartTime = pictureBillStartTime;
	}

	public Date getPictureBillEndTime() {
		return pictureBillEndTime;
	}

	public void setPictureBillEndTime(Date pictureBillEndTime) {
		this.pictureBillEndTime = pictureBillEndTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
