package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: QueryWaybillAndLableResult 
 * @Description: TODO(查询运单及标签信息结果) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:56:14 
 *
 */
public class QueryWaybillAndLableResult implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	
	//是否集中接货 
	private String isFocusReceiving;
	//总件数
	private int totalPieces;
	
	// 标签信息
	
	//运单号
	private String wblCode;
	//送货类型  值为自提或者送货
	private String takeType;
	//运输性质
	private String transType;
	//包装类型
	private String packageType;
	//到达外场名称
	private String arriveOutfieldName;
	//始发城市名称
	private String originateCityName;
	//目的站名称
	private String destinationStationName;
	//提货网点编码
	private String customerPickupOrgCode;
	//库区Code
	private List<String> addrs;
	// 库位编码
	private List<String> locations;
    
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getTakeType() {
		return takeType;
	}
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getArriveOutfieldName() {
		return arriveOutfieldName;
	}
	public void setArriveOutfieldName(String arriveOutfieldName) {
		this.arriveOutfieldName = arriveOutfieldName;
	}
	public String getOriginateCityName() {
		return originateCityName;
	}
	public void setOriginateCityName(String originateCityName) {
		this.originateCityName = originateCityName;
	}
	public String getDestinationStationName() {
		return destinationStationName;
	}
	public void setDestinationStationName(String destinationStationName) {
		this.destinationStationName = destinationStationName;
	}
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	public void setIsFocusReceiving(String isFocusReceiving) {
		this.isFocusReceiving = isFocusReceiving;
	}
	public int getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	
	public String getIsFocusReceiving() {
		return isFocusReceiving;
	}
	public List<String> getAddrs() {
		return addrs;
	}
	public void setAddrs(List<String> addrs) {
		this.addrs = addrs;
	}
	public List<String> getLocations() {
		return locations;
	}
	public void setLocations(List<String> locations) {
		this.locations = locations;
	}
	@Override
	public String toString() {
		
		return super.toString();
	}
	
	
	
}
