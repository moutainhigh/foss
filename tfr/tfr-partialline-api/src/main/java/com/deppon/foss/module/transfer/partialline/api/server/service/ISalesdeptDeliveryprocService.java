package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;

public interface ISalesdeptDeliveryprocService {

	/**
	 * 
	 * @param wayno
	 * @return
	 */
	public List<SalesdeptDeliveryEntity> salesDeptDeliveryQuery(
			String waynoStr,String status);

	/**
	 * 
	 * @param salesdeptDeliveryProcs
	 */
	public void addSalesDeptDeliveryList(
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs);

	/**
	 * 
	 * @param salesdeptDeliveryProcs
	 */
	public void updateSalesDeptDeliveryList(
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs);

	/**
	 * 
	 * @param wayno
	 * @return
	 */
	public ExportResource exprotSalesDeptDeliveryQuery(
			SalesdeptDeliveryEntity entity);
	
	
	/**
	 * 
	 * @param wayno
	 * @return
	 */
	public SalesdeptDeliveryEntity querySalesDeptDeliverysToTfr(String wayno,String orgCode);
	

	/**
	 * 
	 * @param salesdeptDeliveryProcs
	 * @return
	 */
	public boolean canleSalesDeptDeliverys(List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs);
	
	/**
	 * 
	* @Title: salesDeptDeliveryQuery 
	* @Description:  提供给DOP接口查询
	* @param @param waynoStr
	* @param @param status
	* @param @return    设定文件 
	* @return SalesdeptDeliveryEntity    返回类型 
	* @throws
	 */
	public SalesdeptDeliveryEntity salesDeptDeliveryQueryByWayBillNo(String wayBillNo);
	
	
}
