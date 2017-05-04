/**
 *  initial comments.
 */

/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/TfrWaybillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class TfrWaybillEntity extends BaseEntity {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -8628362611784633952L;
		
		/**
		 * 
		 */
		private String waybillNo;
		
		/**
		 * 
		 */
		private String targetOrgCode;
		
		/**
		 * 
		 */
		private String paidMethod;
		
		/**
		 * 
		 */
		private String goodsSize;
		
		/**
		 * 
		 */
		private BigDecimal goodsWeightTotal;
		
		/**
		 * 
		 */
		private BigDecimal billWeight;
		
		/**
		 * 
		 */
		private BigDecimal goodsVolumeTotal;
		
		/**
		 * 
		 */
		private Integer goodsQtyTotal;
		
		/**
		 * 
		 */
		private String goodsName;
		
		/**
		 * 
		 */
		private BigDecimal toPayAmount;
		
		/**
		 * 
		 */
		private BigDecimal deliveryGoodsFee;
		
		/**
		 * 
		 */
		private String receiveCustomerAddress;
		
		/**
		 * 
		 */
		private String receiveCustomerPhone;
		
		/**
		 * 
		 */
		private String receiveCustomerName;
		
		/**
		 * 
		 */
		private String serialNo;
		
		/**
		 * 
		 */
		private BigDecimal codAmount;

		/**
		 * 
		 */
		private String receiveMethod;
		
		/**
		 * 
		 *
		 * @return 
		 */
		public String getWaybillNo() {
			return waybillNo;
		}

		/**
		 * 
		 *
		 * @param waybillNo 
		 */
		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getTargetOrgCode() {
			return targetOrgCode;
		}

		/**
		 * 
		 *
		 * @param targetOrgCode 
		 */
		public void setTargetOrgCode(String targetOrgCode) {
			this.targetOrgCode = targetOrgCode;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getPaidMethod() {
			return paidMethod;
		}

		/**
		 * 
		 *
		 * @param paidMethod 
		 */
		public void setPaidMethod(String paidMethod) {
			this.paidMethod = paidMethod;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getGoodsSize() {
			return goodsSize;
		}

		/**
		 * 
		 *
		 * @param goodsSize 
		 */
		public void setGoodsSize(String goodsSize) {
			this.goodsSize = goodsSize;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public BigDecimal getGoodsWeightTotal() {
			return goodsWeightTotal;
		}

		/**
		 * 
		 *
		 * @param goodsWeightTotal 
		 */
		public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
			this.goodsWeightTotal = goodsWeightTotal;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public BigDecimal getBillWeight() {
			return billWeight;
		}

		/**
		 * 
		 *
		 * @param billWeight 
		 */
		public void setBillWeight(BigDecimal billWeight) {
			this.billWeight = billWeight;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public BigDecimal getGoodsVolumeTotal() {
			return goodsVolumeTotal;
		}

		/**
		 * 
		 *
		 * @param goodsVolumeTotal 
		 */
		public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
			this.goodsVolumeTotal = goodsVolumeTotal;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public Integer getGoodsQtyTotal() {
			return goodsQtyTotal;
		}

		/**
		 * 
		 *
		 * @param goodsQtyTotal 
		 */
		public void setGoodsQtyTotal(Integer goodsQtyTotal) {
			this.goodsQtyTotal = goodsQtyTotal;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getGoodsName() {
			return goodsName;
		}

		/**
		 * 
		 *
		 * @param goodsName 
		 */
		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public BigDecimal getToPayAmount() {
			return toPayAmount;
		}

		/**
		 * 
		 *
		 * @param toPayAmount 
		 */
		public void setToPayAmount(BigDecimal toPayAmount) {
			this.toPayAmount = toPayAmount;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public BigDecimal getDeliveryGoodsFee() {
			return deliveryGoodsFee;
		}

		/**
		 * 
		 *
		 * @param deliveryGoodsFee 
		 */
		public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
			this.deliveryGoodsFee = deliveryGoodsFee;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getReceiveCustomerAddress() {
			return receiveCustomerAddress;
		}

		/**
		 * 
		 *
		 * @param receiveCustomerAddress 
		 */
		public void setReceiveCustomerAddress(String receiveCustomerAddress) {
			this.receiveCustomerAddress = receiveCustomerAddress;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getReceiveCustomerPhone() {
			return receiveCustomerPhone;
		}

		/**
		 * 
		 *
		 * @param receiveCustomerPhone 
		 */
		public void setReceiveCustomerPhone(String receiveCustomerPhone) {
			this.receiveCustomerPhone = receiveCustomerPhone;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getReceiveCustomerName() {
			return receiveCustomerName;
		}

		/**
		 * 
		 *
		 * @param receiveCustomerName 
		 */
		public void setReceiveCustomerName(String receiveCustomerName) {
			this.receiveCustomerName = receiveCustomerName;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getSerialNo() {
			return serialNo;
		}

		/**
		 * 
		 *
		 * @param serialNo 
		 */
		public void setSerialNo(String serialNo) {
			this.serialNo = serialNo;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public BigDecimal getCodAmount() {
			return codAmount;
		}

		/**
		 * 
		 *
		 * @param codAmount 
		 */
		public void setCodAmount(BigDecimal codAmount) {
			this.codAmount = codAmount;
		}

		/**
		 * 
		 *
		 * @return 
		 */
		public String getReceiveMethod() {
			return receiveMethod;
		}

		/**
		 * 
		 *
		 * @param receiveMethod 
		 */
		public void setReceiveMethod(String receiveMethod) {
			this.receiveMethod = receiveMethod;
		}
}