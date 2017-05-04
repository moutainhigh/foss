package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTransTeamEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 公共组件查询--组织查询
 * @author dujunhui-187862
 * @date 2014-08-13 上午8:59:37
 */
public interface ICommonTransTeamService {
	/**
	 * description 通过组织编码查询车队组信息
	 * @author dujunhui-187862
	 * @param orgCode
	 * @return List<OrgAdministrativeInfoEntity>:符合条件的组织集合
	 * @date 2014-08-13 上午9:00:51
	 */
	List<OrgAdministrativeInfoEntity> queryTransTeams(CommonTransTeamEntity entity,int limit, int start);
	
	/**
	 *description 通过组织编码查询车队组信息的总数
	 * @author dujunhui-187862
	 * @param orgCode
	 * @return 总条数
	 * @date 2014-08-08 上午8:13:44
	 */
	Long countTransTeams(CommonTransTeamEntity entity);

}

