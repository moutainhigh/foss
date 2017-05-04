package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransTeamDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTransTeamService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTransTeamEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
/**
 * 公共查询组件--“车队组信息”的数据库对应数据访问Service接口
 * @author 187862-dujunhui
 * @date 2014-08-13 上午9:03:13
 */
public class CommonTransTeamService implements ICommonTransTeamService {
	
	
	/**
	 * 车队组信息DAO
	 */
	private ICommonTransTeamDao commonTransTeamDao;
	
	/**
	 * @param commonTransTeamDao the commonTransTeamDao to set
	 */
	public void setCommonTransTeamDao(ICommonTransTeamDao commonTransTeamDao) {
		this.commonTransTeamDao = commonTransTeamDao;
	}

	/** 
	 * <p>TODO(通过组织编码查询其下所属车队组信息)</p> 
	 * @author 187862
	 * @date 2014-8-13 上午9:03:59
	 * @param orgCode
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTransTeamService#queryTransTeams(java.lang.String, int, int)
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryTransTeams(CommonTransTeamEntity entity,
			int limit, int start) {
		// TODO Auto-generated method stub
		return commonTransTeamDao.queryTransTeams(entity,limit, start);
	}

	/** 
	 * <p>TODO(通过组织编码查询其下所属车队组信息统计)</p> 
	 * @author 187862
	 * @date 2014-8-13 上午9:03:59
	 * @param orgCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTransTeamService#countTitleByCondition(java.lang.String)
	 */
	@Override
	public Long countTransTeams(CommonTransTeamEntity entity) {
		// TODO Auto-generated method stub
		return commonTransTeamDao.countTransTeams(entity);
	}

}
