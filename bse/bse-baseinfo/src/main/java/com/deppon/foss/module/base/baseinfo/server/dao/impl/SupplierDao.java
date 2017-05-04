package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISupplierDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 供应商dao
 * @author 232607 
 * @date 2015-12-21 下午7:49:35
 * @since
 * @version
 */
public class SupplierDao extends SqlSessionDaoSupport implements ISupplierDao {
	
	private String namespace = "foss.bse.bse-baseinfo.supplier.";
	
	/** 
	 * <p>根据编码查询供应商信息</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:18:53
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISupplierDao#querySupplierByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SupplierEntity querySupplierByCode(String code) {
		SupplierEntity entity=new SupplierEntity();
		entity.setCode(code);
		List<SupplierEntity> list=getSqlSession().selectList(namespace+"querySupplierByCode", entity);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/** 
	 * <p>新增供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:19:12
	 * @param entity 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISupplierDao#addSupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity)
	 */
	@Override
	public void addSupplier(SupplierEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateTime(new Date());
		getSqlSession().insert(namespace + "addSupplier",entity);
	}
	/** 
	 * <p>根据编码作废供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:19:21
	 * @param supplierCode 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISupplierDao#deleteSupplierByCode(java.lang.String)
	 */
	@Override
	public void deleteSupplierByCode(String supplierCode) {
		SupplierEntity entity=new SupplierEntity();
		entity.setCode(supplierCode);
		entity.setUpdateTime(new Date());
		getSqlSession().update(namespace + "deleteSupplierByCode", entity);
	}
	/** 
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:26:07
	 * @param supplierEntity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISupplierDao#comboQuerySupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplierEntity> comboQuerySupplier(SupplierEntity supplierEntity) {
		return getSqlSession().selectList(namespace+"comboQuerySupplier", supplierEntity);
	}
	/** 
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:26:16
	 * @param supplierEntity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISupplierDao#countComboQuerySupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity)
	 */
	@Override
	public Long countComboQuerySupplier(SupplierEntity supplierEntity) {
		return (Long) getSqlSession().selectOne(namespace+"countComboQuerySupplier", supplierEntity);
	}
	
	
	
}
