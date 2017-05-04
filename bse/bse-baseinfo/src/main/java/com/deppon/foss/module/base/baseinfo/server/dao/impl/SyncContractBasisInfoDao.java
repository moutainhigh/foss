package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.CollectionUtils;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncContractBasisInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContractBasisInfoEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 * 同步合同基础信息Dao实现
 * @author 308861 
 * @date 2016-8-12 下午6:15:29
 * @since
 * @version
 */
public class SyncContractBasisInfoDao extends SqlSessionDaoSupport implements ISyncContractBasisInfoDao{
	/**
     * mybatis 命名空间
     */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.contractBasisInfo.";
	
	/**
	 * 
	 * 插入  
	 * @author 308861 
	 * @date 2016-8-12 下午6:17:54
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncContractBasisInfoDao#addContractBasisInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContractBasisInfoEntity)
	 */
	@Override
	public ContractBasisInfoEntity addContractBasisInfo(
			ContractBasisInfoEntity entity) {
		//请求合法性判断
		if(null == entity){
			return entity;
		}
		entity.setId(UUIDUtils.getUUID());
		Date now=new Date();
		//版本号
		entity.setVersionNo(String.valueOf(now.getTime()));
		int result = getSqlSession().insert(NAMESPACE + "addContractInfo", entity);
		return result > 0 ? entity : null;
	}
	
	
	/**
	 * 
	 * 修改
	 * @author 308861 
	 * @date 2016-8-12 下午6:18:37
	 * @param entity
	 * @return 
	 */
	@Override
	public ContractBasisInfoEntity updateContractBasisInfo(
			ContractBasisInfoEntity entity) {
		//请求参数合法性验证
		if(null == entity || StringUtils.isBlank(entity.getPtpId())){
			return entity;
		}
		entity.setId(UUIDUtils.getUUID());
		Date now = new Date();
		//版本号
		entity.setVersionNo(String.valueOf(now.getTime()));
		int result = getSqlSession().update(NAMESPACE + "updateContractInfo", entity);
		return result > NumberConstants.ZERO ? entity : null;
	}
	
	/**
	 * 
	 * 根据id查询 
	 * @author 308861 
	 * @date 2016-8-12 下午6:18:50
	 * @param Id
	 * @return 
	 */
	@Override
	public ContractBasisInfoEntity queryContractBasisInfById(String ptpId) {
		if(StringUtils.isEmpty(ptpId)){
			return null;
		}
		ContractBasisInfoEntity entity=new ContractBasisInfoEntity();
		//构造查询条件
		entity.setId(UUIDUtils.getUUID());
		entity.setPtpId(ptpId);
		@SuppressWarnings("unchecked")
		List<ContractBasisInfoEntity> list=getSqlSession().selectList(NAMESPACE+"queryContractById",entity);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
}
