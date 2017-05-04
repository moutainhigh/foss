package com.deppon.foss.module.transfer.packaging.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageAssistPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.QueryAssistPackedDto;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.queryPackedWaybillNoDto;

/**
 * @desc PC端辅助包装类
 * @author wqh
 * @date   2014-05-07
 * */
public interface IPackageAssistPriceDao {

	/**
	 * 
	 * @desc:添加辅助包装信息，单条
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	void addPackageAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity);
	

	/**
	 * 
	 * @desc:提供修改辅助包装信息 
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	void updatePackageAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity);
	
	
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门查询已经生成的辅助包装金额信息
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	List<PackageAssistPriceEntity> queryAssistPackagePriceByWaybillNoAndSupplierCode(PackageAssistPriceEntity packageAssistPriceEntity);
	
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息，分页
	 * @author foss-中转开发组-105795-wqh
	 * @return PackageAssistPriceEntity
	 * @date   2014-05-14
	 * */
	List<PackageAssistPriceEntity> queryAssistPirceList(QueryAssistPackedDto queryAssistPackedDto,int limit,int start);
	
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息，不分页
	 * @author foss-中转开发组-105795-wqh
	 * @return PackageAssistPriceEntity
	 * @date   2014-05-14
	 * */
	List<PackageAssistPriceEntity> queryAssistPirceList(QueryAssistPackedDto queryAssistPackedDto);
	

	/**
	 * 
	 * @desc:根据ID删除辅助包装信息
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-14
	 * */
	void deletePackedAssistPriceById(String id);
	
	
	/**
	 * 
	 * @desc:根据ID查询辅助包装信息 
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-22
	 * */
	PackageAssistPriceEntity queryPackedAssistPriceById(String id);
	
	
	/**
	 * 
	 * @desc:提供修改打木架、打木箱 辅助包装信息给主要包装进行更新 ，只修改打木箱、打木架体积
	 * @author foss-中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-22
	 * */
	void updatePackageAssistPriceByMain(PackageAssistPriceEntity packageAssistPriceEntity);
	
	/**
	 * 
	 * @desc:查询运单开单时包装需求信息
	 * @author foss-中转开发组-105795-wqh
	 * @return List<queryPackedWaybillNoDto>
	 * @date   2014-05-22
	 * */
	List<queryPackedWaybillNoDto> queryPackedWaybillNoRequire(List<String> waybillNoList);
	
	/**
	 * 
	 * @desc:根据多个运单号、多个包装包装供应商、包装部门查询已经生成的辅助包装金额信息 
	 * @author foss-中转开发组-105795-wqh
	 * @return List<PackageAssistPriceEntity>
	 * @date   2014-05-22
	 * */
	List<PackageAssistPriceEntity> queryAssistPackagePriceListByWaybillNo(List<String> waybillNoList,String packageOrgCode);
	/**
	 * 
	     *
		 * @desc  根据id查询
		 * @author 042795
		 * @date 2014-6-28 下午7:36:00
		 * @see queryAssistPackagePriceListByWaybillNo
	 */
	List<PackageAssistPriceEntity> queryAssistPackagePriceListByIdList(List<String> idList);
	
	/**
	 * 
	 * @desc:批量添加辅助包装信息
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	int addPackageAssistPriceList(List<PackageAssistPriceEntity> packageAssistPriceList);
	//更新审核状态
	int updatePackageAssistPriceStatus(List<String> idList,PackageAssistPriceEntity packageAssistPrice);
}

