package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-352676-YUANHB,date:2016-9-27 下午6:42:44,content:TODO </p>
 * @author Foss-352676-YUANHB 
 * @date 2016-9-27 下午6:42:44
 * @since
 * @version
 */
public class PartnerPriceTables extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4593155257625997724L;
	/**
	 * 分页查询结果集
	 */
	private List<PartnerPriceTableEntity> partnerPriceTableEntitys;
	/**
	 * 总数 
	 */
	private int total;

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<PartnerPriceTableEntity> getPartnerPriceTableEntitys() {
		return partnerPriceTableEntitys;
	}
	public void setPartnerPriceTableEntitys(
			List<PartnerPriceTableEntity> partnerPriceTableEntitys) {
		this.partnerPriceTableEntitys = partnerPriceTableEntitys;
	}
	
}
