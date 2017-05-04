package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.io.Serializable;import java.util.List;

/**
 * 接受DOP发送过来的数据  
 * @author 352203
 *
 */
public class CommonInterTrackingRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  1.运单号
	 */
	private String waybillNo;
	
	 /**
	  * 2.代理单号，就是交接给代理公司的单号
	  * @return
	  */
	private String agencyNo;
	 
	 /**
	  * 3.代理公司编码
	  * @return
	  */
	private String agencyCompCode;
	 
	 /**
	  * 4.代理公司名称
	  * @return
	  */
	private String agencyCompName;
	 
	 /**
	  * 5.目的国
	  * @return
	  */
	private String destCountry;
	 
	 /**
	  * 7.渠道
	  * 1-韩国件  2-国际件
	  * 韩国件是很早就写好的，国际件是后面规范但是操作类型不同
	  * 所以只能以渠道分开 
	  * @return
	  */
	 private String channel;
	
	
	/**
	 * 运单轨迹
	 */
	private List<InterTrackingEntity> interTrackingEntitylist;
	
	public List<InterTrackingEntity> getInterTrackingEntitylist() {
		return interTrackingEntitylist;
	}

	public void setInterTrackingEntitylist(
			List<InterTrackingEntity> interTrackingEntitylist) {
		this.interTrackingEntitylist = interTrackingEntitylist;
	}

	/**
	 * 获取运单号
	 * @return
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置运单号
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 设置单号
	 * @return
	 */
	public String getAgencyNo() {
		return agencyNo;
	}

	/**
	 * 获取代理单号
	 * @param agencyNo
	 */
	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}

	/**
	 * 获取代理公司编码
	 * @return
	 */
	public String getAgencyCompCode() {
		return agencyCompCode;
	}

	/**
	 * 设置代理公司编码
	 * @param agencyCompCode
	 */
	public void setAgencyCompCode(String agencyCompCode) {
		this.agencyCompCode = agencyCompCode;
	}

	/**
	 * 获取代理公司名称
	 * @return
	 */
	public String getAgencyCompName() {
		return agencyCompName;
	}

	/**
	 * 设置代理公司名称
	 * @param agencyCompName
	 */
	public void setAgencyCompName(String agencyCompName) {
		this.agencyCompName = agencyCompName;
	}

	/**
	 * 获取目的国
	 * @return
	 */
	public String getDestCountry() {
		return destCountry;
	}

	/**
	 * 设置目的国
	 * @param destCountry
	 */
	public void setDestCountry(String destCountry) {
		this.destCountry = destCountry;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
