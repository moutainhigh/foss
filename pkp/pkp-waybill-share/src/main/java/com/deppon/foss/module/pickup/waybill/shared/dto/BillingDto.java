package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BillingDto {
	//运单号
		private String waybillNo;
		//发货时间
		private Date shipping_date;
		//支付方式代码
		private String pay_type_code;
		//支付方式名称
		private String pay_type_name;
		//开单金额
		private BigDecimal total_amount;
		//运输性质
		private String trans_type;
		//货物名称
		private String cargo_name;
		//发货城市
		private String sender_city_name;
		//到达城市
		private String consignee_city_name;
		//货物件数
		private Integer cargo_count;
		//发货人姓名
		private String poster_name;
		//收货人姓名
		private String receiver_name;

		public String getWaybillNo() {
			return waybillNo;
		}
		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}
		public Date getShipping_date() {
			return shipping_date;
		}
		public void setShipping_date(Date shippingDate) {
			this.shipping_date = shippingDate;
		}
		public String getPay_type_code() {
			return pay_type_code;
		}
		public void setPay_type_code(String payTypeCode) {
			this.pay_type_code = payTypeCode;
		}
		public String getPay_type_name() {
			return pay_type_name;
		}
		public void setPay_type_name(String payTypeName) {
			this.pay_type_name = payTypeName;
		}
		public BigDecimal getTotal_amount() {
			return total_amount;
		}
		public void setTotal_amount(BigDecimal totalAmount) {
			this.total_amount = totalAmount;
		}
		public String getTrans_type() {
			return trans_type;
		}
		public void setTrans_type(String transType) {
			this.trans_type = transType;
		}
		public String getCargo_name() {
			return cargo_name;
		}
		public void setCargo_name(String cargoName) {
			this.cargo_name = cargoName;
		}
		public String getSender_city_name() {
			return sender_city_name;
		}
		public void setSender_city_name(String senderCityName) {
			this.sender_city_name = senderCityName;
		}
		public String getConsignee_city_name() {
			return consignee_city_name;
		}
		public void setConsignee_city_name(String consigneeCityName) {
			this.consignee_city_name = consigneeCityName;
		}
		public Integer getCargo_count() {
			return cargo_count;
		}
		public void setCargo_count(Integer cargoCount) {
			this.cargo_count = cargoCount;
		}
		public String getPoster_name() {
			return poster_name;
		}
		public void setPoster_name(String posterName) {
			this.poster_name = posterName;
		}
		public String getReceiver_name() {
			return receiver_name;
		}
		public void setReceiver_name(String receiverName) {
			this.receiver_name = receiverName;
		}

}
