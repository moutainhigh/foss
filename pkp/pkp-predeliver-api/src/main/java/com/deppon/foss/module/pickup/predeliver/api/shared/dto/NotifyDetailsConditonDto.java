package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @功能 通知客户明细查询条件Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2013-12-27 9:22:18
 *
 */
public class NotifyDetailsConditonDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//运单号-集合
	private String waybillNo;
	
	//运单数组
	private String[] waybillNoList;
	
	//通知状态
	private String noticeMethod;
	
	//通知状态List
	private List<String> noticeMethodList;
	
	//提货方式
	private String receiveMethod;
	
	//开始通知时间
	private Date noticeTimeFrom;
	
	//结束通知时间
	private Date noticeTimeTo;
	//产品类型
	private String productCode;
	/**
	 * 模块名称
	 */
	private String moduleName;
	
	private List<String> productCodeList;
	/**
	 * 通知结果
	 */
	private String noticeResult;
	
	public List<String> getProductCodeList() {
		return productCodeList;
	}
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * 所在部门
	 */
	private String lastLoadOrgCode;
	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;

	/**
	 * 库区
	 */
	private String goodsAreaCode;
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	/**
	 * 库位
	 */
	private String position;
	
	//get、set方法
	public String getReceiveMethod() {
		return receiveMethod;
	}
	public List<String> getNoticeMethodList() {
		return noticeMethodList;
	}
	public void setNoticeMethodList(List<String> noticeMethodList) {
		this.noticeMethodList = noticeMethodList;
	}
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String[] getWaybillNoList() {
		return waybillNoList;
	}
	public void setWaybillNoList(String[] waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	public String getNoticeMethod() {
		return noticeMethod;
	}
	public void setNoticeMethod(String noticeMethod) {
		this.noticeMethod = noticeMethod;
	}
	public Date getNoticeTimeFrom() {
		return noticeTimeFrom;
	}
	public void setNoticeTimeFrom(Date noticeTimeFrom) {
		this.noticeTimeFrom = noticeTimeFrom;
	}
	public Date getNoticeTimeTo() {
		return noticeTimeTo;
	}
	public void setNoticeTimeTo(Date noticeTimeTo) {
		this.noticeTimeTo = noticeTimeTo;
	}
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getNoticeResult() {
		return noticeResult;
	}
	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}
	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}
	
}
