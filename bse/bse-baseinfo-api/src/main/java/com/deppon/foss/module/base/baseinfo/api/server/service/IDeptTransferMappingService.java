package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTransferMappingDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DeptTransferMappingException;

/**
 * 营业部交接映射管理-service接口
 * @author 273296
 *
 */
public interface IDeptTransferMappingService extends IService {

	/**
	 * 根据部门code查询是否外场
	 *273296
	 * @param vo
	 * @return
	 */
	String findOutFieldByCode(String deptCode) throws DeptTransferMappingException;

	/**
	 *新增 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 */
	void addDeptTransferMapping(
			List<DeptTransferMappingEntity> deptTransferMappingList) throws DeptTransferMappingException;

	/**
	 * 查询 营业部 交接映射管理数据
	 *273296
	 * @param vo
	 * @param limit 
	 * @param start 
	 * @return
	 */
	List<DeptTransferMappingEntity> queryDeptTransferMappingList(
			DeptTransferMappingDto vo, int start, int limit)throws DeptTransferMappingException;

	/**
	 * 查询 营业部 交接映射管理数据总数
	 *273296
	 * @param vo
	 * @return
	 */
	long queryDeptTransferMappingCount(DeptTransferMappingDto vo)throws DeptTransferMappingException;

	/**
	 * 作废营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	long deleteDeptTransferMappingById(String id) throws DeptTransferMappingException;

	
	/**
	 * 批量作废营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	long deleteDeptTransferMappingsByIdList(List<String> idList) throws DeptTransferMappingException;

	/**
	 *修改 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 */
	void updateDeptTransferMapping(DeptTransferMappingDto vo)throws DeptTransferMappingException;

	/**
	 * 根据部门code,一级网点查找记录 
	 *273296
	 * @param vo
	 * @return
	 */
	List<DeptTransferMappingEntity> queryDeptTransferMappingListByDeptCodeAndFthNetwork(
			DeptTransferMappingDto vo);

	/**
	 *  根据营业部和一级网点作废记录
	 *273296
	 * @return
	 */
	long deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(
			DeptTransferMappingDto vo);

	/**
	 * 查询是否存在二级网点
	 *273296
	 * @return
	 */
	long queryDeptTransferMappingModelBySecNetworkCode(String secNetworkCode);
	
	
	/**
	 * 根据code 查询数据的接口
	 *273296
	 * @return
	 */
	List<DeptTransferMappingEntity> queryDeptTransferMappingListByCode(String code);

	/**
	 * 根据一级网点查找记录 是否存在
	 *273296
	 * @param string
	 * @return
	 */
	long queryDeptTransferMappingModelByFthNetworkCode(String fthNetworkCode);
	
	/**
	 * 根据code查询 部门名称
	 * 查询规则：
	 * 1。二级编码 并且是外场，返回 一级名称
	 * 2.一级编码或者是二级编码并且不是外场，返回对接部门名称
	 *273296
	 * @param code
	 * @return
	 */
	String queryDeptDeptTransferForNameByCode(String code);

}
