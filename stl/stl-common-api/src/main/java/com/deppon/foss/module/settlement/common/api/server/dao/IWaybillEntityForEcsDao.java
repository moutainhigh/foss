package com.deppon.foss.module.settlement.common.api.server.dao;

/**
 * 查询运单信息
 * @author 325369
 * @date 2016-09-06
 */
public interface IWaybillEntityForEcsDao {
	
	/**
	 * 通过运单号查询运单表判断是否是悟空运单
	 * 
	 * @author 325369
	 * @date 2016-09-06
	 * @param waybillNo
	 * @return
	 */
	Integer queryWaybillIsEcsByWaybillNo(String waybillNo);

}
