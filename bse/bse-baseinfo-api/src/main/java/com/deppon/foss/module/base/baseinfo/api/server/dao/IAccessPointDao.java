package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 接驳点Dao
 * @author 198771
 *
 */
public interface IAccessPointDao {
	/**
	 * 添加接驳点
	 * @param entity
	 */
	void addAccessPoint(AccessPointEntity entity);
	
	/**
	 * 根据条件查询
	 * @param entity
	 * @return
	 */
	List<AccessPointEntity> queryAccessPoints(AccessPointEntity entity,int start,int limit);

	/**
	 * 根据条件查询总条数
	 * @param entity
	 * @return
	 */
	long getCount(AccessPointEntity entity);
	/**
	 * 根据接驳点名称查找
	 */
	List<AccessPointEntity> queryAccessPointsByName(AccessPointEntity entity);
	
	/**
	 * 根据中转场编码
	 * @param entity
	 * @return
	 */
	List<AccessPointEntity> queryAccessPointsByTransferCode(
			AccessPointEntity entity);
	
	/**
	 * 根据接驳点编码查找
	 */
	AccessPointEntity queryAccessPointsByCode(AccessPointEntity entity);
	/**
	 * 禁用/启用
	 */
	void updateAccessPointStatu(Map<String,Object> maps);
	/**
	 * 作废
	 * @param entity
	 */
	void deleteAccessPoint(AccessPointEntity entity);
	
	/**
	 * 根据参数动态查询
	 * @param entity
	 */
	List<AccessPointEntity> queryAccessPointByCommon(AccessPointEntity entity);
	/**
	 *根据司机工号查询所属部门 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitysByEmpCode(String empCode);
}
