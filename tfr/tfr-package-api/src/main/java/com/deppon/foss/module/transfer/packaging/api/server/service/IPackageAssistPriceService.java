package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageAssistPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.QueryAssistPackedDto;


/**
 * @desc PC端辅助包装类
 * @author wqh
 * @date   2014-05-07
 * */
public interface IPackageAssistPriceService extends IService{

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
	Date updatePackageAssistPrice(PackageAssistPriceEntity packageAssistPriceEntity);
	
	
	/**
	 * 
	 * @desc:根据运单号、包装材料、包装部门查询已经生成的辅助包装金额信息
	 * @author foss-中转开发组-105795-wqh
	 * @date   2014-05-08
	 * */
	List<PackageAssistPriceEntity> queryAssistPackagePriceByWaybillNoAndSupplierCode(PackageAssistPriceEntity packageAssistPriceEntity);
	
	/**
	 * 
	 * @desc:根据运单号、包装供应商、包装部门、开单部门、包装时间查询已经生成的辅助包装金额信息
	 * @author foss-中转开发组-105795-wqh
	 * @return PackageAssistPriceEntity
	 * @date   2014-05-14
	 * */
	List<PackageAssistPriceEntity> queryAssistPirceList(QueryAssistPackedDto queryAssistPackedDto, int limit,int start);
	
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
	 * @desc:导出辅助包装金额信息
	 * @author foss中转开发组-105795-wqh
	 * @return void
	 * @date   2014-05-27
	 * */
	@SuppressWarnings("rawtypes")
	List exportPackedAssistPriceExcel(QueryAssistPackedDto queryAssistPackedDto);
	
	/**
	 * @desc 下载导入辅助包装金额模版
	 * @return list
	 * @author foss中转-105795-wqh
	 * @date   2014-05-27
	 * 
	 * **/
	@SuppressWarnings("rawtypes")
	 List downloadMasterplateToAssistExcel();
	 
	 
	 /**
		 * @desc 导入辅助包装金额信息
		 * @return list
		 * @author foss中转-105795-wqh
		 * @date   2014-06-07
		 * 
		 * **/
	 int importPackedAssistPriceInfo(List<PackageAssistPriceEntity> packageAssistPriceList);
	 /**
		 * 
		 * @desc:根据多个运单号、多个包装包装供应商、包装部门查询已经生成的辅助包装金额信息 
		 * @author foss-中转开发组-105795-wqh
		 * @return List<PackageAssistPriceEntity>
		 * @date   2014-05-22
		 * */
	List<PackageAssistPriceEntity> queryAssistPackagePriceListByWaybillNo(List<String> waybillNoList,String packageOrgCode);
	/** 
	 * @desc 审核辅助包装金额信息
	 * @return 
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 下午9:50:16
	 */
	public void auditPackedAssistPrice(List<String> waybillNos);
	/** 
	 * @desc 反审核辅助包装金额信息
	 * @return 
	 * @author 042795-foss-duyi
	 * @date 2014-06-25 下午2:50:16
	 */
	public void deauditPackedAssistPrice(List<String> wayBillNos);

	/** 
	 * @desc 提供运单发生更改时修改代包装体积和生成应付单
	 * @return 
	 * @author 205109-foss-zenghaibin
	 * @date 2014-12-08 下午4:27:16
	 */	
	public void updatePackageAssistPriceByWayBillNo(String wayBillNo);
	
	/** 
	 * @desc 提供运单发生更改时修改代包装体积和生成应付单
	 * @return 
	 * @author 205109-foss-zenghaibin
	 * @date 2014-12-08 下午4:27:16
	 */	
	public void updatePriceByWayBillNo(String wayBillNo);
}
