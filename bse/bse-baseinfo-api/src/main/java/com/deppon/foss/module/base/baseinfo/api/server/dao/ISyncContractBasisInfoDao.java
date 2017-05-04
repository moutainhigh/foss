package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContractBasisInfoEntity;
/**
 * 
 * 同步合同基础资料dao接口
 * @author 308861 
 * @date 2016-8-12 下午5:56:55
 */
public interface ISyncContractBasisInfoDao {
	
	/**
	 * 
	 * 插入 
	 * @author 308861 
	 * @date 2016-8-12 下午5:58:21
	 * @param entity
	 * @return
	 * @see
	 */
	ContractBasisInfoEntity addContractBasisInfo(ContractBasisInfoEntity entity);
	
	/**
	 * 
	 * 根据唯一标识列修改
	 * @author 308861 
	 * @date 2016-8-12 下午5:58:42
	 * @param entity
	 * @return
	 * @see
	 */
	ContractBasisInfoEntity updateContractBasisInfo(ContractBasisInfoEntity entity);
	
	/**
	 * 
	 * 根据ID精确查询 
	 * @author 308861 
	 * @date 2016-8-12 下午5:59:22
	 * @param Id
	 * @return
	 * @see
	 */
	ContractBasisInfoEntity queryContractBasisInfById(String ptpId);
}
