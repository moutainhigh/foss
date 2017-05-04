package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;

/**
 * TODO(PackagingSupplier的Service接口)
 * @author 187862-dujunhui
 * @date 2014-5-8 下午6:35:23
 * @since
 * @version
 */
public interface IPackagingSupplierService extends IService {
	
	/**
	 * 
	 * @Description:根据包装供应商List查询信息
	 * @author:187862-dujunhui
	 */
	public List<PackagingSupplierEntity> queryPackagingSupplierByEntityList(String deptCode,List<String> packagingSupplierCodeList);
	
	/**
	 * 
	 * @Description:精确查询包装供应商信息
	 * @author:187862-dujunhui
	 */
	PackagingSupplierEntity queryPackagingSupplierByEntity(String deptCode,String supplierCode);
	
	/**
	 * 
	 * @Description:添加包装供应商信息
	 * @author:187862-dujunhui
	 */
	int addPackagingSupplier(PackagingSupplierEntity entity);
	
	/**
	 * 
	 * @Description:删除包装供应商信息
	 * @author:187862-dujunhui
	 */
	int deletePackagingSupplier(String[] codeList);
	
	/**
	 * 
	 * @Description:修改包装供应商信息
	 * @author:187862-dujunhui
	 */
	int updatePackagingSupplier(PackagingSupplierEntity entity);
	
	/**
	 * 
	 * @Description:根据部门查询包装供应商信息
	 * @author:187862-dujunhui
	 */
	
	List<PackagingSupplierEntity> queryPackagingSupplierListByOrgCode(PackagingSupplierEntity entity, int limit, int start);
	
	/**
	 * 
	 * @Description:统计同一部门信息条数
	 * @author:187862-dujunhui
	 */
	Long queryOrgCodeCount(PackagingSupplierEntity entity);
	
	/**
	 * 
	 * @Description:统计所有包装供应商信息条数
	 * @author:187862-dujunhui
	 */
	Long queryRecordCount(PackagingSupplierEntity entity);
	
	/**
	 * @Description:查询同一外场包装供应商信息唯一性
	 * @author 187862-dujunhui
	 */
	List<PackagingSupplierEntity> queryPackagingSupplierUnique(
			PackagingSupplierEntity entity, int limit, int start);
	
	/**
	 * @Description:根据员工工号查询对应外场部门的包装供应商
	 * @author 187862-dujunhui
	 */
	List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(
			String empCode);
	
	/**
	 * 根据4个字段查询外场包装供应商信息唯一性
	 */
	PackagingSupplierEntity queryPackagingSupplierByCodes(String deptCode,
			String deptName, String supplierCode, String supplierName);


}
