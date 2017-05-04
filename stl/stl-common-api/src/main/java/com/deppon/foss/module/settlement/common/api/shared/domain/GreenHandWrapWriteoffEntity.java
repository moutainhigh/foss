package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *@author 310970 
 *@date 2016-2-17
 *@since 
 *@version 
 */
public class GreenHandWrapWriteoffEntity {
	
	//序列化
	private static final long serialVersionUID = -8853255634581981965L;
	
		/**
		 * ID
		 */
		private String id;
		/**
		 * 运单号
		 */
		private String waybillNo;
		/**
		 * 金额
		 */
		private BigDecimal amount;
		/**
		 * 费用类型
		 */
		private String costType;
		
		/**
		 * dop传数据过来的时间
		 */
		private Date dopTime;
		/**
		 * 创建人
		 */
		private String createUser;
		/**
		 * 是否有效状态
		 */
		private String active;
		/**
		 * 是否核销状态
		 */
		private String writeoffStatus;
		/**
		 * 创建时间
		 */
		private Date createTime;
		/**
		 * 修改时间
		 */
		private Date modifyTime;
		/**
		 * 修改人
		 */
		private String modifyUser;
		/**
		 *备注 
		 */
		private String note;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getWaybillNo() {
			return waybillNo;
		}
		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public String getCostType() {
			return costType;
		}
		public void setCostType(String costType) {
			this.costType = costType;
		}
		public Date getDopTime() {
			return dopTime;
		}
		public void setDopTime(Date dopTime) {
			this.dopTime = dopTime;
		}
		public String getCreateUser() {
			return createUser;
		}
		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}
		public String getActive() {
			return active;
		}
		public void setActive(String active) {
			this.active = active;
		}
		public String getWriteoffStatus() {
			return writeoffStatus;
		}
		public void setWriteoffStatus(String writeoffStatus) {
			this.writeoffStatus = writeoffStatus;
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
		public String getModifyUser() {
			return modifyUser;
		}
		public void setModifyUser(String modifyUser) {
			this.modifyUser = modifyUser;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		
}
