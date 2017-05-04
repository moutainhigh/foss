package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

public class OtHandOverBillDetailEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	    private String id;
	    //点单任务编号
	    private String orderTaskNo;
	    
	    //交接单号
		private String handoverNo;
		
	    //运单号
		private String waybillNo;
		
		//运输性质
		private String transportType;
		
		//点单件数
		private BigDecimal orderGoodsQty;
		
		//开单件数
		private BigDecimal createBillQty;
		
		//已配件数
		private BigDecimal alAssembleQty;
		
		//已配重量
		private BigDecimal alAssembleWeight;
		
		//已配体积
		private BigDecimal alAssembleVolume;
	
		//出发部门code
		private String departCode;
			
		//出发部门名称
		private String departName;
			
		//到达部门code
		private String ArriveCode;
			
		//到达部门名称
		private String ArriveName;
		
		//包装
		private String packing;
		
		//货物名称
		private String goodsName;
		
		//操作人code
		private String createUserCode;

		/**
		 * @return the waybillNo
		 */
		public String getWaybillNo() {
			return waybillNo;
		}
		/**
		 * @param waybillNo the waybillNo to set
		 */
		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}
		
		public String getTransportType() {
			return transportType;
		}
		public void setTransportType(String transportType) {
			this.transportType = transportType;
		}
		public BigDecimal getCreateBillQty() {
			return createBillQty;
		}
		public void setCreateBillQty(BigDecimal createBillQty) {
			this.createBillQty = createBillQty;
		}
		public BigDecimal getAlAssembleQty() {
			return alAssembleQty;
		}
		public void setAlAssembleQty(BigDecimal alAssembleQty) {
			this.alAssembleQty = alAssembleQty;
		}
		public BigDecimal getAlAssembleWeight() {
			return alAssembleWeight;
		}
		public void setAlAssembleWeight(BigDecimal alAssembleWeight) {
			this.alAssembleWeight = alAssembleWeight;
		}
		public BigDecimal getAlAssembleVolume() {
			return alAssembleVolume;
		}
		public void setAlAssembleVolume(BigDecimal alAssembleVolume) {
			this.alAssembleVolume = alAssembleVolume;
		}
		public String getHandoverNo() {
			return handoverNo;
		}
		public void setHandoverNo(String handoverNo) {
			this.handoverNo = handoverNo;
		}
		public String getDepartCode() {
			return departCode;
		}
		public void setDepartCode(String departCode) {
			this.departCode = departCode;
		}
		public String getDepartName() {
			return departName;
		}
		public void setDepartName(String departName) {
			this.departName = departName;
		}
		public String getArriveCode() {
			return ArriveCode;
		}
		public void setArriveCode(String arriveCode) {
			ArriveCode = arriveCode;
		}
		public String getArriveName() {
			return ArriveName;
		}
		public void setArriveName(String arriveName) {
			ArriveName = arriveName;
		}
		public String getPacking() {
			return packing;
		}
		public void setPacking(String packing) {
			this.packing = packing;
		}
		public String getOrderTaskNo() {
			return orderTaskNo;
		}
		public void setOrderTaskNo(String orderTaskNo) {
			this.orderTaskNo = orderTaskNo;
		}
		public BigDecimal getOrderGoodsQty() {
			return orderGoodsQty;
		}
		public void setOrderGoodsQty(BigDecimal orderGoodsQty) {
			this.orderGoodsQty = orderGoodsQty;
		}
		public String getGoodsName() {
			return goodsName;
		}
		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCreateUserCode() {
			return createUserCode;
		}
		public void setCreateUserCode(String createUserCode) {
			this.createUserCode = createUserCode;
		}

}
