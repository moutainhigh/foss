package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackageMainPriceDao;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageMainPriceEntity;


/**
 * @desc 主要包装金额 dao
 * @author foss-105795-wqh
 * @date   2014-04-29
 * */
public class PackageMainPriceDao  extends iBatis3DaoImpl implements IPackageMainPriceDao {

	public final static String PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE = "foss.packaging.packMojarPrice.";

	/**
	 * @desc 增加主要包装金额
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	public void addPackageMainPrice(PackageMainPriceEntity packageMainPriceEntity){
		
		getSqlSession().insert(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"addPackageMainPrice", packageMainPriceEntity);
		
	}
	/**
	 * @desc 一个运单分批扫描，如果是同一部门、同一包装材料默认为 修改已经录入的主包装信息
	 * @author foss-105795-wqh
	 * @date   2014-05-07
	 * */
	public void updatePackageMainPrice(PackageMainPriceEntity packageMainPriceEntity)
	{
		getSqlSession().update(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"updatePackageMainPrice", packageMainPriceEntity);
	}
	/**
	 * @desc 根据运单号、包装材料、包装供应商查询已经生成的主包装金额信息
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	@SuppressWarnings("unchecked")
	public List<PackageMainPriceEntity> queryMainPackagePriceByWaybillNoAndSupplierCode(String waybillNo,
			String packageOrgCode,String supplierCode)
	{
		Map dataMap=new HashMap();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("packageOrgCode", packageOrgCode);
		dataMap.put("supplierCode", supplierCode);
		return getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryMainPackagePriceByWaybillNoAndSupplierCode", dataMap);

	}
	
	/**
	 * @desc 添加包装异常信息
	 * @author foss-105795-wqh
	 * @date   2014-05-28
	 * */
	public void addPackagePriceException(PackageMainPriceEntity packageMainPriceEntity){
		getSqlSession().insert(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"addPackagePriceException", packageMainPriceEntity);

	}
	
	/**
	 * @desc 根据运单号、包装材料、包装部门查询已经生成的主包装金额信息
	 * @author foss-105795-wqh
	 * @date   2014-05-04
	 * */
	public List<PackageMainPriceEntity> queryMainPackagePriceListByWaybillNo(List<String> waybillNoList,String packageOrgCode)
	{
		Map dataMap=new HashMap();
		dataMap.put("waybillNoList", waybillNoList);
		dataMap.put("packageOrgCode", packageOrgCode);
		return getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryMainPackagePriceListByWaybillNo", dataMap);
		
	}
	
	/**
	 * @desc 根据运单号查询有哪些转运场有该运单的主要包装信息
	 * @author foss-205109-zenghaibin
	 * @date   2014-12-09 14:35:35
	 * @param String wayBillNo
	 * @param List<PackageMainPriceEntity>
	 * */
	public  List<PackageMainPriceEntity> queryPriceListByWaybillNo(String wayBillNo){
		
		return  this.getSqlSession().selectList(PACKAGING_MAIN_PRICE_IBATIS_NAMESAPCE+"queryPriceListByWaybillNo", wayBillNo);
		
	}
}
