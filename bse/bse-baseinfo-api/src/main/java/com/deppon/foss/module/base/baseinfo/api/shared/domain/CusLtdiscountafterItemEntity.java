package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

public class CusLtdiscountafterItemEntity{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
		
		/**
		 * ID
		 */
		private String itemId;
		
		/**
		 * 以下都是CRM那边传过来的字段，字段名字未做改变,BaseEntity中修改（创建）人
		 * 修改（创建）时间也是CRM的，CRM也传过来了
		 */
		/**
		 * 创建时间
		 */
		private Date createDate;
		
		/**
		 * 创建人
		 */
		private String createUser;
		
		/**
		 * 修改时间
		 */
		private Date modifyDate;
		
		/**
		 * 修改人
		 */
		private String modifyUser;
		
		/**
		 * fid主键(CRM)
		 */
		private String fid;
		
		/**
		 *  等级(CRM)
		 */
		private String degree;
		
		/**
		 * 最小金额(CRM)
		 */
		private double minAmount;
		
		/**
		 *  最大金额(CRM)
		 */
		private double maxAmount;
		
		/**
		 *  折扣(CRM)
		 */
		private double rate;
		
		/**
		 *  描述(CRM)
		 */
		private String itemDesc;
		
		/**
		 * 优惠方案id(CRM),关联基础资料里的fid
		 */
		private String dealId;
		
		/**
		 * 是否有效
		 * @return
		 */
		private String active;
		
		public String getFid() {
			return fid;
		}
		
		public void setFid(String fid) {
			this.fid = fid;
		}
		
		public String getDegree() {
			return degree;
		}
		
		public void setDegree(String degree) {
			this.degree = degree;
		}
		
		public double getMinAmount() {
			return minAmount;
		}
		
		public void setMinAmount(double minAmount) {
			this.minAmount = minAmount;
		}
		
		public double getMaxAmount() {
			return maxAmount;
		}
		
		public void setMaxAmount(double maxAmount) {
			this.maxAmount = maxAmount;
		}
		
		public double getRate() {
			return rate;
		}
		
		public void setRate(double rate) {
			this.rate = rate;
		}
		
		public String getItemDesc() {
			return itemDesc;
		}
		
		public void setItemDesc(String itemDesc) {
			this.itemDesc = itemDesc;
		}
		
		public String getDealId() {
			return dealId;
		}
		
		public void setDealId(String dealId) {
			this.dealId = dealId;
		}

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public String getCreateUser() {
			return createUser;
		}

		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}

		public Date getModifyDate() {
			return modifyDate;
		}

		public void setModifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
		}

		public String getModifyUser() {
			return modifyUser;
		}

		public void setModifyUser(String modifyUser) {
			this.modifyUser = modifyUser;
		}
}