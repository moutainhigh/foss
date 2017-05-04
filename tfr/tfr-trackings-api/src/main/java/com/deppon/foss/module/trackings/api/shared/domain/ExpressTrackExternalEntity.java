package com.deppon.foss.module.trackings.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 快递轨迹表实体
 *@author 205109-foss-zenghaibin
 *@date 2015-04-29 上午 9:49：30 
 ***/
public class ExpressTrackExternalEntity  implements Serializable{
	//序列化
	private static final long serialVersionUID = 7354429328336724231L;
	
	private String id;
	
	/**运单号**/
	private String wayBillNo;
	
	/**订单号**/
	private String channelOrder;
	
	/**发生时间**/
	private Date operaTime;
	
	/**物流公司编号**/
	private String logisticsCompany;
	
	/**跟踪信息描述**/
	private String trackInfo;
	
	/**发生城市**/
	private String operateCity;
	
	/**站点类型**/
	private String orgType;
	
	/**部门编码**/
	private String orgCode;
	
	/**部门名称**/
	private String orgName;

	/**时间类型**/
	private String eventType;

	/**联系人**/
	private String contact;

	/**联系人方式**/
	private String contactPhone;

	/**创建时间**/
	private Date createTime;

	/**修改时间**/
	private Date modifyTime;

	/****/
	private String jobId;
	
	/**
	 * 流水号
	* @fields serialNo
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:14:55
	* @version V1.0
	*/
	private String serialNo;
	
	/**
	 * 开单件数
	* @fields goodsQtyTotal
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:04
	* @version V1.0
	*/
	private String goodsQtyTotal;
	
	/**
	 * 订单来源
	* @fields orderChannel
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:13
	* @version V1.0
	*/
	private String orderChannel;
	
	/**
	 * 到达部门Code
	* @fields arriveOrgCode
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:23
	* @version V1.0
	*/
	private String arriveOrgCode;
	
	/**
	 * 到达部门Name
	* @fields arriveOrgName
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:33
	* @version V1.0
	*/
	private String arriveOrgName;
	
	/**
	 * 到达城市
	* @fields arriveCity
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:47
	* @version V1.0
	*/
	private String arriveCity;
	
	/**
	 * 产品类型
	* @fields productCode
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:15:56
	* @version V1.0
	*/
	private String productCode;
	
	/**
	 * 是否快递
	* @fields expressIs
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午6:16:05
	* @version V1.0
	*/
	private String expressIs;

	/**
	 * 推送提货方式
	* @fields expressIs
	* @author 322610-foss-songjianlong
	* @update 2016年7月4日15:33:55
	* @version V1.0
	*/
	private String recvMethod;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getChannelOrder() {
		return channelOrder;
	}

	public void setChannelOrder(String channelOrder) {
		this.channelOrder = channelOrder;
	}

	public Date getOperaTime() {
		return operaTime;
	}

	public void setOperaTime(Date operaTime) {
		this.operaTime = operaTime;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
	}

	public String getOperateCity() {
		return operateCity;
	}

	public void setOperateCity(String operateCity) {
		this.operateCity = operateCity;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}

	public String getArriveOrgName() {
		return arriveOrgName;
	}

	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getExpressIs() {
		return expressIs;
	}

	public void setExpressIs(String expressIs) {
		this.expressIs = expressIs;
	}

	public String getRecvMethod() {
		return recvMethod;
	}
	
	public void setRecvMethod(String recvMethod) {
		this.recvMethod = recvMethod;
	}
	
	
}
