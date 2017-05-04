package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonLdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;

/**
 * 快递代理网点公共选择器Dao层
 * 
 * @author WangPeng
 * @date   2013-07-25 10:13 AM
 *
 */
public class CommonLdpAgencyDeptDao extends SqlSessionDaoSupport implements ICommonLdpAgencyDeptDao {

	//定义常量
	private static final String NAMESPACE="foss.bse.bse-baseinfo.commonOuterBranchLdp.";
	/**
	 * 返回查询列表
	 * 
	 * @author WangPeng
	 * @Date   2013-7-25 上午10:14:35
	 * @param  entity
	 * @param  limit
	 * @param  start
	 * @return List
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByCondtion(
			OuterBranchExpressEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryAgencyLdpDeptInfos",entity, rowBounds);
	}

	/**
	 * 记录查询行数
	 * 
	 * @author WangPeng
	 * @Date   2013-7-25 上午10:14:42
	 * @param  entity
	 * @return Long
	 *
	 */
	@Override
	public Long countRecordByCondition(OuterBranchExpressEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countRecordByCondition", entity);
	}

}
