package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceValuationEntity;
/**
 * <p>导入降价发券明细的dto </p>
 * @author dujunhui-187862
 * @date 2014-10-31 下午5:22:14
 */
public class DetailExcelDto {
	/**
	 * 计价明细实体
	 */
	private PriceCriteriaDetailEntity priceCriteriaDetailEntity;
	/**
	 * 计价规则实体
	 */
	private PriceValuationEntity priceValuationEntity;
	/**
	 * 行号
	 */
	private int rowNum;
	/**
	 * @return  the priceCriteriaDetailEntity
	 */
	public PriceCriteriaDetailEntity getPriceCriteriaDetailEntity() {
		return priceCriteriaDetailEntity;
	}
	/**
	 * @param priceCriteriaDetailEntity the priceCriteriaDetailEntity to set
	 */
	public void setPriceCriteriaDetailEntity(
			PriceCriteriaDetailEntity priceCriteriaDetailEntity) {
		this.priceCriteriaDetailEntity = priceCriteriaDetailEntity;
	}
	
	/**
	 * @return  the priceValuationEntity
	 */
	public PriceValuationEntity getPriceValuationEntity() {
		return priceValuationEntity;
	}
	/**
	 * @param priceValuationEntity the priceValuationEntity to set
	 */
	public void setPriceValuationEntity(PriceValuationEntity priceValuationEntity) {
		this.priceValuationEntity = priceValuationEntity;
	}
	/**
	 * @return  the rowNum
	 */
	public int getRowNum() {
		return rowNum;
	}
	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
}
