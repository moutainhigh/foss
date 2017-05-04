package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonAirPartAndDeptEntity;
/**
 * 查询空运代理网点的公共选择器Service
 * @author 130566
 *
 */
public interface ICommonAirAgentAndOutfieldService {
	/**
	 * 查询空运网点信息
	 * @param airPartAndDeptEntity
	 * @return
	 */
	List<CommonAirPartAndDeptEntity> queryDepartmentsByEntity(CommonAirPartAndDeptEntity airPartAndDeptEntity,int offset,int limit);
	/**
	 * 查询总数
	 * @param airPartAndDeptEntity
	 * @return
	 */
	Long queryCount(CommonAirPartAndDeptEntity airPartAndDeptEntity);

}
