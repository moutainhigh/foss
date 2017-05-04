package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISupplierDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity;

public class SupplierService implements ISupplierService{
	/**
     * 加载日志文件
     */
	//private static final Logger log = Logger.getLogger(SupplierService.class);
	/**
	 * 供应商Dao
	 */
	private ISupplierDao supplierDao;
	
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}


	/** 
	 * <p>根据编码查询供应商信息</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:16:34
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService#querySupplierByCode(java.lang.String)
	 */
	public SupplierEntity querySupplierByCode(String code){
		if(StringUtils.isNotEmpty(code)){
			return supplierDao.querySupplierByCode(code);
		}else{
			return null;			
		}
	}
	
	
	/** 
	 * <p>新增供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:16:57
	 * @param entity 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService#addSupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity)
	 */
	@Override
	public void addSupplier(SupplierEntity entity) {
		supplierDao.addSupplier(entity);
	}


	/** 
	 * <p>根据编码作废供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:17:16
	 * @param supplierCode 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService#deleteSupplierByCode(java.lang.String)
	 */
	@Override
	public void deleteSupplierByCode(String supplierCode) {
		if(StringUtils.isNotEmpty(supplierCode)){
			supplierDao.deleteSupplierByCode(supplierCode);
		}
	}


	/** 
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:20:20
	 * @param supplierEntity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService#comboQuerySupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity, int, int)
	 */
	@Override
	public List<SupplierEntity> comboQuerySupplier(SupplierEntity supplierEntity, int start, int limit) {
		return supplierDao.comboQuerySupplier(supplierEntity);
	}


	/** 
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:20:47
	 * @param supplierEntity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISupplierService#countComboQuerySupplier(com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity)
	 */
	@Override
	public Long countComboQuerySupplier(SupplierEntity supplierEntity) {
		return supplierDao.countComboQuerySupplier(supplierEntity);
	}
	
	
	
	
	
	
}
