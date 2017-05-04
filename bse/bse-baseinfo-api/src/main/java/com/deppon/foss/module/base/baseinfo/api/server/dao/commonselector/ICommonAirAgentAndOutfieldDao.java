package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonAirPartAndDeptEntity;
/**
 * 公共选择器空运代理网点(新)dao接口
 * @author 130566
 *
 */
public interface ICommonAirAgentAndOutfieldDao { 
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
