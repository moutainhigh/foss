package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsPgEntity;

/**
 * 
 * @author 297064
 *
 */
public interface IWoodenRequirementsPgDao {
	
	public int insertSelective(WoodenRequirementsPgEntity entity);
	
	public int updateSelectiveByWaybillNo(WoodenRequirementsPgEntity entity);
	
	public WoodenRequirementsPgEntity queryByWayBillNo(String wayBillNo);
}
