package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
* @description FOSS推送合大票信息至OPP系统--FOSS推送实体
* @version 1.0
* @author 269701-foss-lln
* @update 2016年4月27日 上午11:27:44
 */
public class AirPickUpInfoEntity implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
		//合大票ID
		private String airPickUpID;
		//正单号
		private String airWayBillNo;
		//航空公司code
		private String airLineCode;
		//航空公司name
		private String airLineName;
		//到达网点编码
		private String destOrgCode;
		//到达网点名称
		private String destOrgName;
		//航班号
		private String flightNo;
		//目的站code
		private String arrRegionCode;
		//目的站name
		private String arrRegionName;
		//航班日期
		@JSONField(format="yyyy-MM-dd HH:mm:ss")
		private Date flightTime;
		//private String flightTime;
		//制单人编码
		private String createUserCode;
		//制单人名称
		private String createUserName;
		//制单人部门编码
		private String createOrgCode;
		//制单人部门名称
		private String createOrgName;
		//制单时间
		//private String createTime;
		private Date createTime;
		//修改人编号
		private String modifyUserCode;
		//修改人名称
		private String modifyUserName;
		//修改时间
		private Date modifyTime;
		///private String modifyTime;
		//总票数
		private BigDecimal waybillQtyTotal= new BigDecimal(0);
		//总件数
		private BigDecimal goodsQtyTotal= new BigDecimal(0);
		//总毛重
		private BigDecimal grossWeightTotal = new BigDecimal(0);
		//总送货费
		private BigDecimal deliverFeeTotal= new BigDecimal(0);
		//总到付款
		private BigDecimal arrivalFeeTotal= new BigDecimal(0);
		//总代收款
		private BigDecimal collectionFeeTotal= new BigDecimal(0);
		//币种
		private String currencyCode;
		//备注
		private String notes;
		//是否推送完成--推送中(N)/推送完成(Y)
		private String pushStatus;
		// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
		private String operStatus;
		
		
		public String getOperStatus() {
			return operStatus;
		}
		public void setOperStatus(String operStatus) {
			this.operStatus = operStatus;
		}
		/**
		 * @return airPickUpID : return the property airPickUpID.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getAirPickUpID() {
			return airPickUpID;
		}
		/**
		 * @param airPickUpID : set the property airPickUpID.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setAirPickUpID(String airPickUpID) {
			this.airPickUpID = airPickUpID;
		}
		/**
		 * @return airWayBillNo : return the property airWayBillNo.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getAirWayBillNo() {
			return airWayBillNo;
		}
		/**
		 * @param airWayBillNo : set the property airWayBillNo.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setAirWayBillNo(String airWayBillNo) {
			this.airWayBillNo = airWayBillNo;
		}
		/**
		 * @return airLineCode : return the property airLineCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getAirLineCode() {
			return airLineCode;
		}
		/**
		 * @param airLineCode : set the property airLineCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setAirLineCode(String airLineCode) {
			this.airLineCode = airLineCode;
		}
		/**
		 * @return airLineName : return the property airLineName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getAirLineName() {
			return airLineName;
		}
		/**
		 * @param airLineName : set the property airLineName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setAirLineName(String airLineName) {
			this.airLineName = airLineName;
		}
		/**
		 * @return destOrgCode : return the property destOrgCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getDestOrgCode() {
			return destOrgCode;
		}
		/**
		 * @param destOrgCode : set the property destOrgCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setDestOrgCode(String destOrgCode) {
			this.destOrgCode = destOrgCode;
		}
		/**
		 * @return destOrgName : return the property destOrgName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getDestOrgName() {
			return destOrgName;
		}
		/**
		 * @param destOrgName : set the property destOrgName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setDestOrgName(String destOrgName) {
			this.destOrgName = destOrgName;
		}
		/**
		 * @return flightNo : return the property flightNo.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getFlightNo() {
			return flightNo;
		}
		/**
		 * @param flightNo : set the property flightNo.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setFlightNo(String flightNo) {
			this.flightNo = flightNo;
		}
		/**
		 * @return arrRegionCode : return the property arrRegionCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getArrRegionCode() {
			return arrRegionCode;
		}
		/**
		 * @param arrRegionCode : set the property arrRegionCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setArrRegionCode(String arrRegionCode) {
			this.arrRegionCode = arrRegionCode;
		}
		/**
		 * @return arrRegionName : return the property arrRegionName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getArrRegionName() {
			return arrRegionName;
		}
		/**
		 * @param arrRegionName : set the property arrRegionName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setArrRegionName(String arrRegionName) {
			this.arrRegionName = arrRegionName;
		}
		
		/**
		 * @return createUserCode : return the property createUserCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getCreateUserCode() {
			return createUserCode;
		}
		/**
		 * @param createUserCode : set the property createUserCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setCreateUserCode(String createUserCode) {
			this.createUserCode = createUserCode;
		}
		/**
		 * @return createUserName : return the property createUserName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getCreateUserName() {
			return createUserName;
		}
		/**
		 * @param createUserName : set the property createUserName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setCreateUserName(String createUserName) {
			this.createUserName = createUserName;
		}
		/**
		 * @return createOrgCode : return the property createOrgCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getCreateOrgCode() {
			return createOrgCode;
		}
		/**
		 * @param createOrgCode : set the property createOrgCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setCreateOrgCode(String createOrgCode) {
			this.createOrgCode = createOrgCode;
		}
		/**
		 * @return createOrgName : return the property createOrgName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getCreateOrgName() {
			return createOrgName;
		}
		/**
		 * @param createOrgName : set the property createOrgName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setCreateOrgName(String createOrgName) {
			this.createOrgName = createOrgName;
		}
		
		/**
		 * @return modifyUserCode : return the property modifyUserCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getModifyUserCode() {
			return modifyUserCode;
		}
		/**
		 * @param modifyUserCode : set the property modifyUserCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setModifyUserCode(String modifyUserCode) {
			this.modifyUserCode = modifyUserCode;
		}
		/**
		 * @return modifyUserName : return the property modifyUserName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getModifyUserName() {
			return modifyUserName;
		}
		/**
		 * @param modifyUserName : set the property modifyUserName.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setModifyUserName(String modifyUserName) {
			this.modifyUserName = modifyUserName;
		}
		
		
		
		/**
		 * @return waybillQtyTotal : return the property waybillQtyTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public BigDecimal getWaybillQtyTotal() {
			return waybillQtyTotal;
		}
		/**
		 * @param waybillQtyTotal : set the property waybillQtyTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setWaybillQtyTotal(BigDecimal waybillQtyTotal) {
			this.waybillQtyTotal = waybillQtyTotal;
		}
		/**
		 * @return goodsQtyTotal : return the property goodsQtyTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public BigDecimal getGoodsQtyTotal() {
			return goodsQtyTotal;
		}
		/**
		 * @param goodsQtyTotal : set the property goodsQtyTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
			this.goodsQtyTotal = goodsQtyTotal;
		}
		/**
		 * @return grossWeightTotal : return the property grossWeightTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public BigDecimal getGrossWeightTotal() {
			return grossWeightTotal;
		}
		/**
		 * @param grossWeightTotal : set the property grossWeightTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setGrossWeightTotal(BigDecimal grossWeightTotal) {
			this.grossWeightTotal = grossWeightTotal;
		}
		/**
		 * @return deliverFeeTotal : return the property deliverFeeTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public BigDecimal getDeliverFeeTotal() {
			return deliverFeeTotal;
		}
		/**
		 * @param deliverFeeTotal : set the property deliverFeeTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setDeliverFeeTotal(BigDecimal deliverFeeTotal) {
			this.deliverFeeTotal = deliverFeeTotal;
		}
		/**
		 * @return arrivalFeeTotal : return the property arrivalFeeTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public BigDecimal getArrivalFeeTotal() {
			return arrivalFeeTotal;
		}
		/**
		 * @param arrivalFeeTotal : set the property arrivalFeeTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setArrivalFeeTotal(BigDecimal arrivalFeeTotal) {
			this.arrivalFeeTotal = arrivalFeeTotal;
		}
		/**
		 * @return collectionFeeTotal : return the property collectionFeeTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public BigDecimal getCollectionFeeTotal() {
			return collectionFeeTotal;
		}
		/**
		 * @param collectionFeeTotal : set the property collectionFeeTotal.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setCollectionFeeTotal(BigDecimal collectionFeeTotal) {
			this.collectionFeeTotal = collectionFeeTotal;
		}
		/**
		 * @return currencyCode : return the property currencyCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getCurrencyCode() {
			return currencyCode;
		}
		/**
		 * @param currencyCode : set the property currencyCode.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		/**
		 * @return notes : return the property notes.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getNotes() {
			return notes;
		}
		/**
		 * @param notes : set the property notes.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setNotes(String notes) {
			this.notes = notes;
		}
		/**
		 * @return pushStatus : return the property pushStatus.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		public String getPushStatus() {
			return pushStatus;
		}
		/**
		 * @param pushStatus : set the property pushStatus.
		 * @author 269701-foss-lln
		 * @update 2016年4月27日 上午11:36:12
		 * @version V1.0
		 */
		
		public void setPushStatus(String pushStatus) {
			this.pushStatus = pushStatus;
		}
		/**
		 * @return flightTime : return the property flightTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午7:57:10
		 * @version V1.0
		 */
		public Date getFlightTime() {
			return flightTime;
		}
		/**
		 * @param flightTime : set the property flightTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午7:57:10
		 * @version V1.0
		 */
		
		public void setFlightTime(Date flightTime) {
			this.flightTime = flightTime;
		}
		/**
		 * @return createTime : return the property createTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午7:57:10
		 * @version V1.0
		 */
		public Date getCreateTime() {
			return createTime;
		}
		/**
		 * @param createTime : set the property createTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午7:57:10
		 * @version V1.0
		 */
		
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		/**
		 * @return modifyTime : return the property modifyTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午7:57:10
		 * @version V1.0
		 */
		public Date getModifyTime() {
			return modifyTime;
		}
		/**
		 * @param modifyTime : set the property modifyTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午7:57:10
		 * @version V1.0
		 */
		
		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}
		
		
			
}
