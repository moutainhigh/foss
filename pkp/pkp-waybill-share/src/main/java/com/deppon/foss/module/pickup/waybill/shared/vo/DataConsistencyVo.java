package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.Date;

public class DataConsistencyVo implements Serializable {
	
	
	
	 /**
	 * 下载数据检查模块VO
	 */
	private static final long serialVersionUID = -34647154682L;

			// ID
			private String id;
			
			// 实体类名
			private String entityClsName;
			
			// 同步日期
			private Date syncDate;
			
			// 组织编码
			private String orgCode;
			
			// 区域ID(计价区域 ID 或者 时效区域ID)
			private String regionID;
			
			private String needOrgSearch;
			
			//是否需要分页
			private String pagination;
			
			// 同步页数
			private int syncPage = 0;
			
		//表名
		private String tabelName;
		
		//本地同步日期
		private Date localSyncDate;

		//本地记录数
		private Integer localCounts;
		
		//服务器记录数
		private Integer counts;
		
		//版本号
		private Long versionNo;
		
		//用户ID
		private String userCode;

		public String getTabelName() {
			return tabelName;
		}

		public void setTabelName(String tabelName) {
			this.tabelName = tabelName;
		}

		public Integer getLocalCounts() {
			return localCounts;
		}

		public void setLocalCounts(Integer localCounts) {
			this.localCounts = localCounts;
		}
		


		/**
		 * @return the syncPage
		 */
		public int getSyncPage() {
			return syncPage;
		}

		/**
		 * @param syncPage the syncPage to set
		 */
		public void setSyncPage(int syncPage) {
			this.syncPage = syncPage;
		}

		/**
		 * @return the needOrgSearch
		 */
		public String getNeedOrgSearch() {
			return needOrgSearch;
		}

		/**
		 * @return the pagination
		 */
		public String getPagination() {
			return pagination;
		}

		/**
		 * @param pagination the pagination to set
		 */
		public void setPagination(String pagination) {
			this.pagination = pagination;
		}

		/**
		 * @param needOrgSearch the needOrgSearch to set
		 */
		public void setNeedOrgSearch(String needOrgSearch) {
			this.needOrgSearch = needOrgSearch;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the entityClsName
		 */
		public String getEntityClsName() {
			return entityClsName;
		}

		/**
		 * @param entityClsName the entityClsName to set
		 */
		public void setEntityClsName(String entityClsName) {
			this.entityClsName = entityClsName;
		}

		/**
		 * @return the syncDate
		 */
		public Date getSyncDate() {
			return syncDate;
		}

		/**
		 * @param syncDate the syncDate to set
		 */
		public void setSyncDate(Date syncDate) {
			this.syncDate = syncDate;
		}

		public String getOrgCode() {
			return orgCode;
		}

		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}

		public String getRegionID() {
			return regionID;
		}

		public void setRegionID(String regionID) {
			this.regionID = regionID;
		}

		public Date getLocalSyncDate() {
			return localSyncDate;
		}

		public void setLocalSyncDate(Date localSyncDate) {
			this.localSyncDate = localSyncDate;
		}

		public Integer getCounts() {
			return counts;
		}

		public void setCounts(Integer counts) {
			this.counts = counts;
		}

		public Long getVersionNo() {
			return versionNo;
		}

		public void setVersionNo(Long versionNo) {
			this.versionNo = versionNo;
		}

		public String getUserCode() {
			return userCode;
		}

		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}
			

}
