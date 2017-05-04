package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;

/**
 * TODO(描述类的职责)
 * @author 187862-杜军辉
 * @date 2014-5-8 下午5:08:17
 * @since
 * @version
 */
public interface IPackagingSupplierDao {
	
	/**
     * 
     * 增加单条包装供应商信息
     */
	int addPackagingSupplier(PackagingSupplierEntity entity);
	
	/**
     * 
     * 删除选中的包装供应商信息
     */
	int deletePackagingSupplier(String[] codeList);
	
	/**
     * 
     * 更新单条包装供应商信息
     */
	int updatePackagingSupplier(PackagingSupplierEntity entity);
	
	/**
     * 
     * 统计所有包装供应商信息分页查询
     */
	Long queryRecordCount(PackagingSupplierEntity entity);
	
	/**
     * 
     * 统计同一部门的包装供应商信息
     */
	Long queryOrgCodeCount(PackagingSupplierEntity entity);
	
	/**
     * 
     * 根据输入的部门名称查询包装供应商信息
     */
	List<PackagingSupplierEntity> queryPackagingSupplierListByOrgCode(PackagingSupplierEntity entity,int limit,int start);
	
	/**
     * 
     * 根据输入的包装供应商查询详细信息
     */
	PackagingSupplierEntity queryPackagingSupplierByEntity(String deptCode,String supplierCode);
	
	/**
     * 
     * 根据输入的包装供应商List查询详细信息
     */
	List<PackagingSupplierEntity> queryPackagingSupplierByEntityList(
			String deptCode,List<String> packagingSupplierCodeList);
	
	/**
	 * 
	 * 查询同一外场包装供应商信息唯一性
	 */
	List<PackagingSupplierEntity> queryPackagingSupplierUnique(
			PackagingSupplierEntity entity, int limit, int start);

	/**
	 * 
	 * 根据员工工号查询对应外场部门的包装供应商
	 */
	List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(
			String empCode);

	/**
	 * 
	 * 根据codeList批量查询对应外场部门的包装供应商
	 */
	List<PackagingSupplierEntity> queryByCodeList(String[] codeList);
	
	/**
	 * 
	 * 根据codeList批量查询对应外场部门的包装供应商
	 */
	List<PackagingSupplierEntity> queryByCodeList(List<String> codeList);
	/**
	 * 
	 * 根据4个字段查询外场包装供应商信息唯一性
	 */
	PackagingSupplierEntity queryPackagingSupplierByCodes(String deptCode,
			String deptName, String supplierCode, String supplierName);

}
