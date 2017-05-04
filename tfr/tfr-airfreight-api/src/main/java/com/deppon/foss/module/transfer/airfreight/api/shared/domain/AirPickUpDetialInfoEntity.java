package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
* @description FOSS推送合大票明细信息至OPP系统--FOSS推送实体
* @version 1.0
* @author 269701-foss-lln
* @update 2016年4月27日 上午11:32:48
 */
public class AirPickUpDetialInfoEntity implements Serializable{

	
		/**
		* @fields serialVersionUID
		* @author 269701-foss-lln
		* @update 2016年4月27日 上午11:32:14
		* @version V1.0
		*/
		private static final long serialVersionUID = 1L;
		//合大票清单主表id
		private String airPickId;
		// 明细ID
		private String detialId;
		// 清单号
		private String airWayBillNo;
		// 运单号
		private String wayBillNo;
		// 目的站name
		private String arrRegionName;
		// 创建时间
		private Date createTime;
		// 合_品名
		private String goodsName;
		// 合_件数
		private BigDecimal goodsQty = new BigDecimal(0);
		//运单开单件数
		private BigDecimal billingQty = new BigDecimal(0);
		// 合_重量
		private BigDecimal Weight = new BigDecimal(0);
		// 合_计费重量
		private BigDecimal billingWeight = new BigDecimal(0);
		// 合_是否中转
		private String beTransfer;
		// 合_送货费
		private BigDecimal deliverFee = new BigDecimal(0);
		// 合_到付款
		private BigDecimal arrivalFee = new BigDecimal(0);
		// 合_代收款
		private BigDecimal collectionFee = new BigDecimal(0);
		// 合_币种
		private String currencyCode;
		// 合_收货人电话
		private String receivePhone;
		// 合_收货人手机
		private String receiveMobilePhone;
		// 合_收货人地址
		private String receiveAddress;
		// 合_收货人姓名
		private String receiveName;
		// 运_提货方式
		private String pickUpType;
		// 备注
		private String notes;
		// 尺寸
		private String goodsSize;
		// 体积
		private BigDecimal volume =new BigDecimal(0);
		// 包装
		private String goodsPackage;
		// 运单付款方式
		private String paidMothd;
		// 运单 总运费
		private BigDecimal totalFee =new BigDecimal(0);
		// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
		private String operStatus;
		
		public String getOperStatus() {
			return operStatus;
		}
		public void setOperStatus(String operStatus) {
			this.operStatus = operStatus;
		}
		public String getDetialId() {
			return detialId;
		}
		public void setDetialId(String detialId) {
			this.detialId = detialId;
		}
		public String getAirWayBillNo() {
			return airWayBillNo;
		}
		public void setAirWayBillNo(String airWayBillNo) {
			this.airWayBillNo = airWayBillNo;
		}
		public String getWayBillNo() {
			return wayBillNo;
		}
		public void setWayBillNo(String wayBillNo) {
			this.wayBillNo = wayBillNo;
		}
		public String getArrRegionName() {
			return arrRegionName;
		}
		public void setArrRegionName(String arrRegionName) {
			this.arrRegionName = arrRegionName;
		}
		
		public String getGoodsName() {
			return goodsName;
		}
		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public BigDecimal getWeight() {
			return Weight;
		}
		public void setWeight(BigDecimal weight) {
			Weight = weight;
		}
		public BigDecimal getBillingWeight() {
			return billingWeight;
		}
		public void setBillingWeight(BigDecimal billingWeight) {
			this.billingWeight = billingWeight;
		}
		public String getBeTransfer() {
			return beTransfer;
		}
		public void setBeTransfer(String beTransfer) {
			this.beTransfer = beTransfer;
		}
		public BigDecimal getDeliverFee() {
			return deliverFee;
		}
		public void setDeliverFee(BigDecimal deliverFee) {
			this.deliverFee = deliverFee;
		}
		public BigDecimal getArrivalFee() {
			return arrivalFee;
		}
		public void setArrivalFee(BigDecimal arrivalFee) {
			this.arrivalFee = arrivalFee;
		}
		public BigDecimal getCollectionFee() {
			return collectionFee;
		}
		public void setCollectionFee(BigDecimal collectionFee) {
			this.collectionFee = collectionFee;
		}
		public String getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		public String getReceivePhone() {
			return receivePhone;
		}
		public void setReceivePhone(String receivePhone) {
			this.receivePhone = receivePhone;
		}
		public String getReceiveMobilePhone() {
			return receiveMobilePhone;
		}
		public void setReceiveMobilePhone(String receiveMobilePhone) {
			this.receiveMobilePhone = receiveMobilePhone;
		}
		public String getReceiveAddress() {
			return receiveAddress;
		}
		public void setReceiveAddress(String receiveAddress) {
			this.receiveAddress = receiveAddress;
		}
		public String getReceiveName() {
			return receiveName;
		}
		public void setReceiveName(String receiveName) {
			this.receiveName = receiveName;
		}
		public String getPickUpType() {
			return pickUpType;
		}
		public void setPickUpType(String pickUpType) {
			this.pickUpType = pickUpType;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		/**
		 * @return airPickId : return the property airPickId.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public String getAirPickId() {
			return airPickId;
		}
		/**
		 * @param airPickId : set the property airPickId.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setAirPickId(String airPickId) {
			this.airPickId = airPickId;
		}
		/**
		 * @return goodsQty : return the property goodsQty.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public BigDecimal getGoodsQty() {
			return goodsQty;
		}
		/**
		 * @param goodsQty : set the property goodsQty.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setGoodsQty(BigDecimal goodsQty) {
			this.goodsQty = goodsQty;
		}
		/**
		 * @return billingQty : return the property billingQty.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public BigDecimal getBillingQty() {
			return billingQty;
		}
		/**
		 * @param billingQty : set the property billingQty.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setBillingQty(BigDecimal billingQty) {
			this.billingQty = billingQty;
		}
		/**
		 * @return goodsSize : return the property goodsSize.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public String getGoodsSize() {
			return goodsSize;
		}
		/**
		 * @param goodsSize : set the property goodsSize.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setGoodsSize(String goodsSize) {
			this.goodsSize = goodsSize;
		}
		/**
		 * @return volume : return the property volume.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public BigDecimal getVolume() {
			return volume;
		}
		/**
		 * @param volume : set the property volume.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setVolume(BigDecimal volume) {
			this.volume = volume;
		}
		/**
		 * @return goodsPackage : return the property goodsPackage.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public String getGoodsPackage() {
			return goodsPackage;
		}
		/**
		 * @param goodsPackage : set the property goodsPackage.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setGoodsPackage(String goodsPackage) {
			this.goodsPackage = goodsPackage;
		}
		/**
		 * @return paidMothd : return the property paidMothd.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public String getPaidMothd() {
			return paidMothd;
		}
		/**
		 * @param paidMothd : set the property paidMothd.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setPaidMothd(String paidMothd) {
			this.paidMothd = paidMothd;
		}
		/**
		 * @return totalFee : return the property totalFee.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		public BigDecimal getTotalFee() {
			return totalFee;
		}
		/**
		 * @param totalFee : set the property totalFee.
		 * @author 269701-foss-lln
		 * @update 2016年5月15日 上午11:52:47
		 * @version V1.0
		 */
		
		public void setTotalFee(BigDecimal totalFee) {
			this.totalFee = totalFee;
		}
		/**
		 * @return createTime : return the property createTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午9:11:57
		 * @version V1.0
		 */
		public Date getCreateTime() {
			return createTime;
		}
		/**
		 * @param createTime : set the property createTime.
		 * @author 269701-foss-lln
		 * @update 2016年5月25日 下午9:11:57
		 * @version V1.0
		 */
		
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		
		
}
