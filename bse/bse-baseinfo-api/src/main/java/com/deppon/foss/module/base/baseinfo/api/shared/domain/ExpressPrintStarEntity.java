package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(ExpressPrintStar的实体类)
 * @author 187862-dujunhui
 * @date 2014-5-21 上午9:34:00
 * @since
 * @version
 */
public class ExpressPrintStarEntity extends BaseEntity {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = -2323885735737609489L;
	
	    // 组织代码
		private String organizationCode;
		
		// 组织名称(冗余)
		private String organizationName;

		// 外场编码（扩展）
		private String transferCode;
		
		// 虚拟编码
		private String virtualCode;
		
		// 库区编码
		private String goodsAreaCode;
		
		// 库区名称
		private String goodsAreaName;
		
		// 库区类型(卡货库区、普货库区、城际快车库区,混装库区和偏线库区等,贵重物品，待包装等)
		private String goodsAreaType;
		
		// 货物类型（A货，B货）
		private String goodsType;
		
		// 目的站
		private String arriveRegionCode;
		
		// 目的站（冗余）
		private String arriveRegionName;
		
		// 库区类别（长途，短途）
		private String goodsAreaUsage;
		
		// 星标编码
		private String asteriskCode;
		
		// 是否有效
		private String active;	
		
		// 版本号
		private Long version;
		
		// 所关联的库位列表
    	private List<StorageEntity> storageList;
		
		// 部门列表（数据权限用）
		private List<String> orgCodeList;

		/**
		 * @return  the organizationCode
		 */
		public String getOrganizationCode() {
			return organizationCode;
		}

		/**
		 * @param organizationCode the organizationCode to set
		 */
		public void setOrganizationCode(String organizationCode) {
			this.organizationCode = organizationCode;
		}

		/**
		 * @return  the organizationName
		 */
		public String getOrganizationName() {
			return organizationName;
		}

		/**
		 * @param organizationName the organizationName to set
		 */
		public void setOrganizationName(String organizationName) {
			this.organizationName = organizationName;
		}

		/**
		 * @return  the transferCode
		 */
		public String getTransferCode() {
			return transferCode;
		}

		/**
		 * @param transferCode the transferCode to set
		 */
		public void setTransferCode(String transferCode) {
			this.transferCode = transferCode;
		}

		/**
		 * @return  the virtualCode
		 */
		public String getVirtualCode() {
			return virtualCode;
		}

		/**
		 * @param virtualCode the virtualCode to set
		 */
		public void setVirtualCode(String virtualCode) {
			this.virtualCode = virtualCode;
		}

		/**
		 * @return  the goodsAreaCode
		 */
		public String getGoodsAreaCode() {
			return goodsAreaCode;
		}

		/**
		 * @param goodsAreaCode the goodsAreaCode to set
		 */
		public void setGoodsAreaCode(String goodsAreaCode) {
			this.goodsAreaCode = goodsAreaCode;
		}

		/**
		 * @return  the goodsAreaName
		 */
		public String getGoodsAreaName() {
			return goodsAreaName;
		}

		/**
		 * @param goodsAreaName the goodsAreaName to set
		 */
		public void setGoodsAreaName(String goodsAreaName) {
			this.goodsAreaName = goodsAreaName;
		}

		/**
		 * @return  the goodsAreaType
		 */
		public String getGoodsAreaType() {
			return goodsAreaType;
		}

		/**
		 * @param goodsAreaType the goodsAreaType to set
		 */
		public void setGoodsAreaType(String goodsAreaType) {
			this.goodsAreaType = goodsAreaType;
		}

		/**
		 * @return  the goodsType
		 */
		public String getGoodsType() {
			return goodsType;
		}

		/**
		 * @param goodsType the goodsType to set
		 */
		public void setGoodsType(String goodsType) {
			this.goodsType = goodsType;
		}

		/**
		 * @return  the arriveRegionCode
		 */
		public String getArriveRegionCode() {
			return arriveRegionCode;
		}

		/**
		 * @param arriveRegionCode the arriveRegionCode to set
		 */
		public void setArriveRegionCode(String arriveRegionCode) {
			this.arriveRegionCode = arriveRegionCode;
		}

		/**
		 * @return  the arriveRegionName
		 */
		public String getArriveRegionName() {
			return arriveRegionName;
		}

		/**
		 * @param arriveRegionName the arriveRegionName to set
		 */
		public void setArriveRegionName(String arriveRegionName) {
			this.arriveRegionName = arriveRegionName;
		}

		/**
		 * @return  the goodsAreaUsage
		 */
		public String getGoodsAreaUsage() {
			return goodsAreaUsage;
		}

		/**
		 * @param goodsAreaUsage the goodsAreaUsage to set
		 */
		public void setGoodsAreaUsage(String goodsAreaUsage) {
			this.goodsAreaUsage = goodsAreaUsage;
		}

		/**
		 * @return  the asteriskCode
		 */
		public String getAsteriskCode() {
			return asteriskCode;
		}

		/**
		 * @param asteriskCode the asteriskCode to set
		 */
		public void setAsteriskCode(String asteriskCode) {
			this.asteriskCode = asteriskCode;
		}

		/**
		 * @return  the active
		 */
		public String getActive() {
			return active;
		}

		/**
		 * @param active the active to set
		 */
		public void setActive(String active) {
			this.active = active;
		}

		/**
		 * @return  the version
		 */
		public Long getVersion() {
			return version;
		}

		/**
		 * @param version the version to set
		 */
		public void setVersion(Long version) {
			this.version = version;
		}

		/**
		 * @return  the storageList
		 */
		public List<StorageEntity> getStorageList() {
			return storageList;
		}

		/**
		 * @param storageList the storageList to set
		 */
		public void setStorageList(List<StorageEntity> storageList) {
			this.storageList = storageList;
		}

		/**
		 * @return  the orgCodeList
		 */
		public List<String> getOrgCodeList() {
			return orgCodeList;
		}

		/**
		 * @param orgCodeList the orgCodeList to set
		 */
		public void setOrgCodeList(List<String> orgCodeList) {
			this.orgCodeList = orgCodeList;
		}
		
		


}
