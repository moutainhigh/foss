package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: BigCustmerLableResEntity
 * @Description: TODO(大客户标签打印返回实体)
 * @author &245960 |yangdeming@deppon.com
 * @date 2015-9-10 下午1:38:55
 * 
 */
public class BigCustmerLableResEntity implements Serializable {

	// 不明原因导致eclipse无法生成对应的uuid，只能为1L了
	private static final long serialVersionUID = 1L;

	// 状态
	private String status;

	// 获取时间
	private Date createTime;

	// 客户条形码
	private String customerLableNum;

	// 流水号
	private String serialNo;

	// 总件数
	private String pieces;

	// 最终目的站编码
	private String destStationNumber;

	// 运单号
	private String WblCode;

	// 送标记
	private String send;

	// 运输类型
	private String transType;

	// 包装类型
	private String wrapType;

	// 目的站名字
	private String destinationName;

	// 工号
	private String userCode;

	// 到达外场名称
	private String destTransCenterName;

	// 出发城市名字
	private String departmentCityName;

	// 货物类型
	private String goodsType;

	// 是否发货大客户
	private String deliveryBigCustomer;

	// 是否收货大客户
	private String receiveBigCustomer;

	// 否展会货
	private String isExhibitCargo;

	// 路由库位信息
	private String goodsAreas;
	
	//到达门店编码
	private String arriveStoreNUM;
	

	public String getArriveStoreNUM() {
		return arriveStoreNUM;
	}

	public void setArriveStoreNUM(String arriveStoreNUM) {
		this.arriveStoreNUM = arriveStoreNUM;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the customerLableNum
	 */
	public String getCustomerLableNum() {
		return customerLableNum;
	}

	/**
	 * @param customerLableNum
	 *            the customerLableNum to set
	 */
	public void setCustomerLableNum(String customerLableNum) {
		this.customerLableNum = customerLableNum;
	}

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the pieces
	 */
	public String getPieces() {
		return pieces;
	}

	/**
	 * @param pieces
	 *            the pieces to set
	 */
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return the destStationNumber
	 */
	public String getDestStationNumber() {
		return destStationNumber;
	}

	/**
	 * @param destStationNumber
	 *            the destStationNumber to set
	 */
	public void setDestStationNumber(String destStationNumber) {
		this.destStationNumber = destStationNumber;
	}

	/**
	 * @return the wblCode
	 */
	public String getWblCode() {
		return WblCode;
	}

	/**
	 * @param wblCode
	 *            the wblCode to set
	 */
	public void setWblCode(String wblCode) {
		WblCode = wblCode;
	}

	/**
	 * @return the send
	 */
	public String getSend() {
		return send;
	}

	/**
	 * @param send
	 *            the send to set
	 */
	public void setSend(String send) {
		this.send = send;
	}

	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @param transType
	 *            the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * @return the wrapType
	 */
	public String getWrapType() {
		return wrapType;
	}

	/**
	 * @param wrapType
	 *            the wrapType to set
	 */
	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}

	/**
	 * @return the destinationName
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * @param destinationName
	 *            the destinationName to set
	 */
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 *            the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the destTransCenterName
	 */
	public String getDestTransCenterName() {
		return destTransCenterName;
	}

	/**
	 * @param destTransCenterName
	 *            the destTransCenterName to set
	 */
	public void setDestTransCenterName(String destTransCenterName) {
		this.destTransCenterName = destTransCenterName;
	}

	/**
	 * @return the departmentCityName
	 */
	public String getDepartmentCityName() {
		return departmentCityName;
	}

	/**
	 * @param departmentCityName
	 *            the departmentCityName to set
	 */
	public void setDepartmentCityName(String departmentCityName) {
		this.departmentCityName = departmentCityName;
	}

	/**
	 * @return the goodsType
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType
	 *            the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * @return the deliveryBigCustomer
	 */
	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}

	/**
	 * @param deliveryBigCustomer
	 *            the deliveryBigCustomer to set
	 */
	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}

	/**
	 * @return the receiveBigCustomer
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	/**
	 * @param receiveBigCustomer
	 *            the receiveBigCustomer to set
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	/**
	 * @return the isExhibitCargo
	 */
	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}

	/**
	 * @param isExhibitCargo
	 *            the isExhibitCargo to set
	 */
	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}

	/**
	 * @return the goodsAreas
	 */
	public String getGoodsAreas() {
		return goodsAreas;
	}

	/**
	 * @param goodsAreas
	 *            the goodsAreas to set
	 */
	public void setGoodsAreas(String goodsAreas) {
		this.goodsAreas = goodsAreas;
	}
	
	
	
	public BigCustmerLableResEntity(){
		super();
	}

	public BigCustmerLableResEntity(String status, Date createTime,
			String customerLableNum, String serialNo, String pieces,
			String destStationNumber, String wblCode, String send,
			String transType, String wrapType, String destinationName,
			String userCode, String destTransCenterName,
			String departmentCityName, String goodsType,
			String deliveryBigCustomer, String receiveBigCustomer,
			String isExhibitCargo, String goodsAreas, String arriveStoreNUM) {
		super();
		this.status = status;
		this.createTime = createTime;
		this.customerLableNum = customerLableNum;
		this.serialNo = serialNo;
		this.pieces = pieces;
		this.destStationNumber = destStationNumber;
		WblCode = wblCode;
		this.send = send;
		this.transType = transType;
		this.wrapType = wrapType;
		this.destinationName = destinationName;
		this.userCode = userCode;
		this.destTransCenterName = destTransCenterName;
		this.departmentCityName = departmentCityName;
		this.goodsType = goodsType;
		this.deliveryBigCustomer = deliveryBigCustomer;
		this.receiveBigCustomer = receiveBigCustomer;
		this.isExhibitCargo = isExhibitCargo;
		this.goodsAreas = goodsAreas;
		this.arriveStoreNUM = arriveStoreNUM;
	}

	
	

}
