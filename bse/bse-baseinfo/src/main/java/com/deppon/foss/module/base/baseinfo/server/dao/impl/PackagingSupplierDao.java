package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现IPackagingSupplierDao接口)
 * @author 187862-杜军辉
 * @date 2014-5-8 下午6:09:05
 * @since
 * @version
 */
public class PackagingSupplierDao extends SqlSessionDaoSupport implements IPackagingSupplierDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.packagingsupplier.";
	/** 
	 * <p>TODO(添加包装供应商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#addPackagingSupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public int addPackagingSupplier(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(NAMESPACE+"insert", entity);
	}

	/** 
	 * <p>TODO(批量删除包装供应商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param codeList
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#deletePackagingSupplier(java.lang.String[])
	 */
	@Override
	public int deletePackagingSupplier(String[] codeList) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		return this.getSqlSession().delete(NAMESPACE+"deleteByCode", map);
	}

	/** 
	 * <p>TODO(更新包装供应商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#updatePackagingSupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public int updatePackagingSupplier(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(NAMESPACE+"update", entity);
	}

	/** 
	 * <p>TODO(查询包装供应商信息条数)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public Long queryRecordCount(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",entity);
	}

	/** 
	 * <p>TODO(查询同一部门下包装供应商信息条数)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryOrgCodeCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@Override
	public Long queryOrgCodeCount(PackagingSupplierEntity entity) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryOrgCodeCount",entity);
	}

	/** 
	 * <p>TODO(根据部门名查询包装供应商信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryPackagingSupplierListByOrgCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByOrgCode(
			PackagingSupplierEntity entity, int limit, int start) {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "select",entity, rowBounds);
	}
	
	/** 
	 * <p>TODO(根据部门名和包装供应商名查询包装供应商信息唯一性)</p> 
	 * @author 187862
	 * @date 2014-5-29 上午8:42:14
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryPackagingSupplierUnique(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierUnique(
			PackagingSupplierEntity entity, int limit, int start) {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "selectUnique",entity, rowBounds);
	}

	/** 
	 * <p>TODO(精确查询包装供应商详细信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryPackagingSupplierByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PackagingSupplierEntity queryPackagingSupplierByEntity(
			String deptCode,String supplierCode) {
		// TODO Auto-generated method stub
		PackagingSupplierEntity entity=new PackagingSupplierEntity();
		entity.setOrgCodeCode(deptCode);
		entity.setPackagingSupplierCode(supplierCode);
		entity.setActive(FossConstants.ACTIVE);
		List<PackagingSupplierEntity> entities=this.getSqlSession().selectList(NAMESPACE + "selectAccurate", entity);
		return entities.size()>0 ? entities.get(0):null;
	}
	
	/** 
	 * <p>TODO(根据4个字段精确查询包装供应商详细信息)</p> 
	 * @author 269231
	 * @date 2016-10-13 下午5:08:25
	 * @param entity
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PackagingSupplierEntity queryPackagingSupplierByCodes(
			String deptCode,String deptName,String supplierCode,String supplierName) {
		// TODO Auto-generated method stub
		PackagingSupplierEntity entity=new PackagingSupplierEntity();
		entity.setOrgCodeCode(deptCode);
		entity.setPackagingSupplierCode(supplierCode);
		entity.setOrgCode(deptName);
		entity.setPackagingSupplier(supplierName);
		entity.setActive(FossConstants.ACTIVE);
		List<PackagingSupplierEntity> entities=this.getSqlSession().selectList(NAMESPACE + "selectAccurateCodes", entity);
		return entities.size()>0 ? entities.get(0):null;
	}
	
	/** 
	 * <p>TODO(根据包装供应商List查询包装供应商详细信息)</p> 
	 * @author 187862
	 * @date 2014-5-8 下午6:09:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryPackagingSupplierByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
    
	@SuppressWarnings("unchecked")
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierByEntityList(
			String deptCode,List<String> packagingSupplierCodeList) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orgCodeCode", deptCode);
		map.put("packagingSupplierCodeList", packagingSupplierCodeList);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "selectAccurateList", map);
	}
	
	/** 
	 * <p>TODO(根据codeList批量查询包装供应商详细信息)</p> 
	 * @author 269231
	 * @param codeList
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryPackagingSupplierByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
    
	@SuppressWarnings("unchecked")
	@Override
	public List<PackagingSupplierEntity> queryByCodeList(String[] codeList) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("codeList", codeList);
		return this.getSqlSession().selectList(NAMESPACE + "queryByCodeString", map);
	}
	
	/** 
	 * <p>TODO(根据codeList批量查询包装供应商详细信息)</p> 
	 * @author 269231
	 * @param codeList
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryPackagingSupplierByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity)
	 */
    
	@SuppressWarnings("unchecked")
	@Override
	public List<PackagingSupplierEntity> queryByCodeList(List<String> codeList) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("codeList", codeList);
		return this.getSqlSession().selectList(NAMESPACE + "queryByCodeList", map);
	}
	
	/** 
	 * <p>TODO(根据员工工号查询对应外场部门的包装供应商)</p> 
	 * @author 187862
	 * @date 2014-6-25 下午7:35:36
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPackagingSupplierDao#queryPackagingSupplierListByEmpCode(String empCode)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(String empCode) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		map.put("empCode", empCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryPackagingSupplierListByEmpCode", map);
	}
	
	
}
