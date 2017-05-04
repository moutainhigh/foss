/**
 * 
 */
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 */
public class FuelConsumptionTotalDto {
		//公里数合计
		private BigDecimal runKmAll;
		
		//加油升数总计
		private BigDecimal fuelQtyAll;
		
		//燃油费总计
		private BigDecimal fuelFeeAll;
		
		//路桥费总计
		private BigDecimal roadTollAll;
		//平均加油单价
		private BigDecimal avgPriceAll;
		//公里路桥费
		private BigDecimal kmRoadTollAll;
		//百公里油耗
		private BigDecimal hdKmFuelAll;
		//单边载重
		private BigDecimal sideLoadAll;
		//吨百公里燃油费
		private BigDecimal thdKmFuelAll;

		/**
		 * @return the runKmAll
		 */
		public BigDecimal getRunKmAll() {
			return runKmAll;
		}

		/**
		 * @param runKmAll the runKmAll to set
		 */
		public void setRunKmAll(BigDecimal runKmAll) {
			this.runKmAll = runKmAll;
		}

		/**
		 * @return the fuelQtyAll
		 */
		public BigDecimal getFuelQtyAll() {
			return fuelQtyAll;
		}

		/**
		 * @param fuelQtyAll the fuelQtyAll to set
		 */
		public void setFuelQtyAll(BigDecimal fuelQtyAll) {
			this.fuelQtyAll = fuelQtyAll;
		}

		/**
		 * @return the fuelFeeAll
		 */
		public BigDecimal getFuelFeeAll() {
			return fuelFeeAll;
		}

		/**
		 * @param fuelFeeAll the fuelFeeAll to set
		 */
		public void setFuelFeeAll(BigDecimal fuelFeeAll) {
			this.fuelFeeAll = fuelFeeAll;
		}

		/**
		 * @return the roadTollAll
		 */
		public BigDecimal getRoadTollAll() {
			return roadTollAll;
		}

		/**
		 * @param roadTollAll the roadTollAll to set
		 */
		public void setRoadTollAll(BigDecimal roadTollAll) {
			this.roadTollAll = roadTollAll;
		}

		/**
		 * @return the avgPriceAll
		 */
		public BigDecimal getAvgPriceAll() {
			return avgPriceAll;
		}

		/**
		 * @param avgPriceAll the avgPriceAll to set
		 */
		public void setAvgPriceAll(BigDecimal avgPriceAll) {
			this.avgPriceAll = avgPriceAll;
		}

		/**
		 * @return the kmRoadTollAll
		 */
		public BigDecimal getKmRoadTollAll() {
			return kmRoadTollAll;
		}

		/**
		 * @param kmRoadTollAll the kmRoadTollAll to set
		 */
		public void setKmRoadTollAll(BigDecimal kmRoadTollAll) {
			this.kmRoadTollAll = kmRoadTollAll;
		}

		/**
		 * @return the hdKmFuelAll
		 */
		public BigDecimal getHdKmFuelAll() {
			return hdKmFuelAll;
		}

		/**
		 * @param hdKmFuelAll the hdKmFuelAll to set
		 */
		public void setHdKmFuelAll(BigDecimal hdKmFuelAll) {
			this.hdKmFuelAll = hdKmFuelAll;
		}

		/**
		 * @return the sideLoadAll
		 */
		public BigDecimal getSideLoadAll() {
			return sideLoadAll;
		}

		/**
		 * @param sideLoadAll the sideLoadAll to set
		 */
		public void setSideLoadAll(BigDecimal sideLoadAll) {
			this.sideLoadAll = sideLoadAll;
		}

		/**
		 * @return the thdKmFuelAll
		 */
		public BigDecimal getThdKmFuelAll() {
			return thdKmFuelAll;
		}

		/**
		 * @param thdKmFuelAll the thdKmFuelAll to set
		 */
		public void setThdKmFuelAll(BigDecimal thdKmFuelAll) {
			this.thdKmFuelAll = thdKmFuelAll;
		}

		
		
}
