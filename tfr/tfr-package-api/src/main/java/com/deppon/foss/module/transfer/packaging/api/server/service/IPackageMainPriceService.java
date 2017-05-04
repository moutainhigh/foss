package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageMainPriceEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity;
/**
 * @desc 主要包装金额service
 * @author foss-105795-wqh
 * @date   2014-04-29
 * */
public interface IPackageMainPriceService extends IService{

	/**
	 * @desc 增加主要包装金额
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	void addPackageMainPrice(PDAPackagingInfoEntity pdaPackagingInfoEntity);
	
	/**
	 * @desc 一个运单分批扫描，如果是同一部门、同一包装材料默认为 修改已经录入的主包装信息
	 * @author foss-105795-wqh
	 * @date   2014-05-07
	 * */
	void updatePackageMainPrice(PackageMainPriceEntity packageMainPriceEntity);
	
	/**
	 * @desc 根据运单号、包装材料、包装部门查询已经生成的主包装金额信息
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	List<PackageMainPriceEntity> queryMainPackagePriceByWaybillNoAndSupplierCode(String waybillNo,
			String packageOrgCode,String supplierCode);
	
	/**
	 * @desc 添加包装异常信息
	 * @author foss-105795-wqh
	 * @date   2014-05-28
	 * */
	void addPackagePriceException(PackageMainPriceEntity packageMainPriceEntity);
	
	/**
	 * @desc 根据运单号、包装材料、包装部门查询已经生成的主包装金额信息
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	List<PackageMainPriceEntity> queryMainPackagePriceListByWaybillNo(List<String> waybillNoList,String packageOrgCode);
	
	/**
	 * @desc 根据运单号查询有哪些转运场有该运单的主要包装信息
	 * @author foss-205109-zenghaibin
	 * @date   2014-12-09 14:35:35
	 * @param String wayBillNo
	 * */
	public void updateMainPriceByWayBillNo(String wayBillNo);
}
