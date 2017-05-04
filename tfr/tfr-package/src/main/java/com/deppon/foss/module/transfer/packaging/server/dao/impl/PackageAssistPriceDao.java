package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackageAssistPriceDao;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageAssistPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.QueryAssistPackedDto;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.queryPackedWaybillNoDto;


/**
 * @desc 辅助包装类dao
 * @author foss-中转开发组-105795-wqh
 * @date   2014-05-08
 * **/
public class PackageAssistPriceDao extends iBatis3DaoImpl implements IPackageAssistPriceDao {

	public final static String PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE = "foss.packaging.packAssistPrice.";

	/**
	 * 
	 * @desc:添加辅助包装信息，单条
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	public void addPackageAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity)
	{
	   getSqlSession().insert(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"addPackageAssistPrice", packageAssistPriceEntity);	
	}
	

	/**
	 * 
	 * @desc:提供修改辅助包装信息 
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	public void updatePackageAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		getSqlSession().update(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"updatePackageAssistPrice", packageAssistPriceEntity);
	}
	
	
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门查询已经生成的辅助包装金额信息
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	@SuppressWarnings("unchecked")
	public List<PackageAssistPriceEntity> queryAssistPackagePriceByWaybillNoAndSupplierCode(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		Map<String,String> dataMap=new HashMap<String,String>();
		dataMap.put("waybillNo", packageAssistPriceEntity.getWaybillNo());
		dataMap.put("packageOrgCode", packageAssistPriceEntity.getPackageOrgCode());
		dataMap.put("packageSupplierCode", packageAssistPriceEntity.getPackageSupplierCode());
		return getSqlSession().selectList(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"queryAssistPackagePriceByWaybillNoAndSupplierCode", dataMap);
	}
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息
	 * @author foss-中转开发组-105795-wqh
	 * @return PackageAssistPriceEntity
	 * @date   2014-05-14
	 * */
	@SuppressWarnings("unchecked")
	public List<PackageAssistPriceEntity> queryAssistPirceList(QueryAssistPackedDto queryAssistPackedDto,int limit,int start)
	{
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(
				PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"queryAssistPirceList", queryAssistPackedDto,rowBounds);
	}
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息，不分页
	 * @author foss-中转开发组-105795-wqh
	 * @return PackageAssistPriceEntity
	 * @date   2014-05-14
	 * */
	@SuppressWarnings("unchecked")
	public List<PackageAssistPriceEntity> queryAssistPirceList(QueryAssistPackedDto queryAssistPackedDto)
	{
		return getSqlSession().selectList(
				PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"queryAssistPirceList", queryAssistPackedDto);
	}
	
	/**
	 * 
	 * @desc:根据ID删除辅助包装信息
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-14
	 * */
	public void deletePackedAssistPriceById(String id)
	{
		getSqlSession().delete(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"deletePackedAssistPriceById", id);
	}
	
	/**
	 * 
	 * @desc:根据ID查询辅助包装信息 
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-22
	 * */
	public PackageAssistPriceEntity queryPackedAssistPriceById(String id)
	{
		return (PackageAssistPriceEntity) getSqlSession().selectOne(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"queryPackedAssistPriceById", id);
	}
	
	/**
	 * 
	 * @desc:提供修改打木架、打木箱 辅助包装信息给主要包装进行更新 ，只修改打木箱、打木架体积
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-22
	 * */
	public void updatePackageAssistPriceByMain(PackageAssistPriceEntity packageAssistPriceEntity)
	{
		getSqlSession().update(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"updatePackageAssistPriceByMain", packageAssistPriceEntity);
	}
	
	/**
	 * 
	 * @desc:查询运单开单时包装需求信息
	 * @author foss-中转开发组-105795-wqh
	 * @return List<queryPackedWaybillNoDto>
	 * @date   2014-05-22
	 * */
	@SuppressWarnings("unchecked")
	public List<queryPackedWaybillNoDto> queryPackedWaybillNoRequire(List<String> waybillNoList)
	{
		return getSqlSession().selectList(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"queryPackedWaybillNoRequire",waybillNoList);
	}
	
	/**
	 * 
	 * @desc:根据多个运单号、多个包装包装供应商、包装部门查询已经生成的辅助包装金额信息 
	 * @author foss-中转开发组-105795-wqh
	 * @return List<PackageAssistPriceEntity>
	 * @date   2014-05-22
	 * */
	@SuppressWarnings("unchecked")
	public List<PackageAssistPriceEntity> queryAssistPackagePriceListByWaybillNo(List<String> waybillNoList,String packageOrgCode)
	{
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("waybillNoList", waybillNoList);
		dataMap.put("packageOrgCode", packageOrgCode);
		return getSqlSession().selectList(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"queryAssistPackagePriceListByWaybillNo", dataMap);
		
	}
	
	/**
	 * 
	 * @desc:批量添加辅助包装信息
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	public int addPackageAssistPriceList(List<PackageAssistPriceEntity> packageAssistPriceList)
	{
		return getSqlSession().insert(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"addPackageAssistPriceList", packageAssistPriceList);
	}
	/** 
	 * @desc 修改审核状态
	 * @return 
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 下午3:00:16
	 */
	@Override
	public int updatePackageAssistPriceStatus(
			List<String> idList,PackageAssistPriceEntity packageAssistPrice) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("idList", idList);
		condition.put("packageAssistPrice", packageAssistPrice);
		return this.getSqlSession().update(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"updatePackageAssistPriceStatus", condition);
	}


	/** 
	     *
		 * @desc  
		 * @author 042795
		 * @date 2014-6-28 下午7:36:45
		 * @see queryAssistPackagePriceListById
		 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PackageAssistPriceEntity> queryAssistPackagePriceListByIdList(
			List<String> idList) {
		return getSqlSession().selectList(PACKAGING_ASSIST_PRICE_IBATIS_NAMESAPCE+"queryAssistPackagePriceListByIdList", idList);
		
	}
}

