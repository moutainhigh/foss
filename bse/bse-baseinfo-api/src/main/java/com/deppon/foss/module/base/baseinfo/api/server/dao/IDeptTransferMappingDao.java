package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DeptTransferMappingException;

/**
 * 营业部交接映射管咧-Dao接口
 * @author 273296
 *
 */
public interface IDeptTransferMappingDao{

	/**
	 * 根据部门code查询是否外场
	 *273296
	 * @param vo
	 * @return
	 */
	String findOutFieldByCode(String deptCode);

	/**
	 *新增 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 * @throws DeptTransferMappingException 
	 */
	void saveDeptTransferMappingEntity(DeptTransferMappingEntity entity);

	
	/**
	 * 查询营业部 交接映射管理数据总数
	 *273296
	 * @return
	 */
	long queryDeptTransferMappingCount(DeptTransferMappingEntity entity);

	
	/**
	 * 作废营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	long deleteDeptTransferMappingById(String id);

	/**
	 * 根据部门code,一级网点查找记录 
	 *273296
	 * @param vo
	 * @return
	 */
	List<DeptTransferMappingEntity> queryDeptTransferMappingListByDeptCodeAndFthNetwork(
			DeptTransferMappingEntity entity);

	/**
	 * 根据营业部和一级网点作废记录
	 *273296
	 * @return
	 */
	long deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(
			DeptTransferMappingEntity entity);

	/**
	 * 根据Id查询
	 *273296
	 * @param id
	 * @return
	 */
	DeptTransferMappingEntity findById(String id);

	/**
	 * 查询是否存在二级网点
	 *273296
	 * @return
	 */
	long queryDeptTransferMappingModelBySecNetworkCode(String secNetworkCode);

	/**
	 * 查询营业部 交接映射管理数据
	 *273296
	 * @return
	 */

	List<DeptTransferMappingEntity> queryDeptTransferMappingList(
			DeptTransferMappingEntity entity, int start, int limit);

	
	/**
	 * 根据code 查询数据的接口
	 *273296
	 * @return
	 */
	List<DeptTransferMappingEntity> queryDeptTransferMappingListByCode(
			String code);

	/**
	 * 查询是否存在一级网点
	 *273296
	 * @return
	 */
	long queryDeptTransferMappingModelByFthNetworkCode(String fthNetworkCode);

	
	/**
	 * 修改营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	void updateDeptTransferMapping(DeptTransferMappingEntity entity);

	/**
	 * 根据一级网点查询数据
	 *273296
	 * @return
	 */
	List<DeptTransferMappingEntity> queryDeptTransferMappingByFthNetworkCode(
			String code);

	/**
	 * 根据二级网点查询数据
	 *273296
	 * @return
	 */
	DeptTransferMappingEntity queryDeptTransferMappingBySecNetworkCode(
			String code);
	
	
	


}
