package com.deppon.foss.module.trackings.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class DMPSynTrackingEntity implements Serializable{
	 /**
	  * 接收DMP的大件家装轨迹数据（综合页面轨迹信息）
	  */
	private static final long serialVersionUID = 1L;
	
	    //id号
		private String id;
		/**运单号**/
		private String wayBillNo;
		//当前状态
		private String currentStatus;
		//操作部门名称
		private String operateOrgName;
		//操作人员
		private String operateName;
		//操作类型名称
		private String operateTypeName;
		//操作时间(发生时间)
		private Date operateTime;
		//操作件数
		private Integer operateNumber;
		//备注
		private String notes;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCurrentStatus() {
			return currentStatus;
		}
		public void setCurrentStatus(String currentStatus) {
			this.currentStatus = currentStatus;
		}
		public String getOperateOrgName() {
			return operateOrgName;
		}
		public void setOperateOrgName(String operateOrgName) {
			this.operateOrgName = operateOrgName;
		}
		public String getOperateName() {
			return operateName;
		}
		public void setOperateName(String operateName) {
			this.operateName = operateName;
		}
		public String getOperateTypeName() {
			return operateTypeName;
		}
		public void setOperateTypeName(String operateTypeName) {
			this.operateTypeName = operateTypeName;
		}
		public Date getOperateTime() {
			return operateTime;
		}
		public void setOperateTime(Date operateTime) {
			this.operateTime = operateTime;
		}
		public Integer getOperateNumber() {
			return operateNumber;
		}
		public void setOperateNumber(Integer operateNumber) {
			this.operateNumber = operateNumber;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		public String getWayBillNo() {
			return wayBillNo;
		}
		public void setWayBillNo(String wayBillNo) {
			this.wayBillNo = wayBillNo;
		}
}
