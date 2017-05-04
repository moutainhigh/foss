package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonLdpAgencyDeptAndOwnExpressDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LdpOuterBranchsAndOrginfoEntity;

public class CommonLdpAgencyDeptAndOwnExpressDeptDao extends SqlSessionDaoSupport implements
		ICommonLdpAgencyDeptAndOwnExpressDeptDao {

	private final static String NAMESPACE = "foss.bse.bse-baseinfo.ldpandorg.";
	/**
     * 根据传入对象查询符合条件所有网点信息 
     * 
     * @author WangPeng
     * @date  2013-08-01 8:53 AM
     * @param entity 快递代理网点和公司快递点部
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpOuterBranchsAndOrginfoEntity> queryLdpAgencyDeptsByCondtion(
			LdpOuterBranchsAndOrginfoEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryLdpAgencyDeptsByCondtion", entity, rowBounds);
	}

	/**
     * 统计总记录数 
     * 
     * @author WangPeng
     * @date   2013-08-01 8:53 AM
     * @param  entity 快递代理网点和公司快递点部
     * @return 符合条件的总记录数
     */
	@Override
	public Long countRecordByCondition(LdpOuterBranchsAndOrginfoEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countRecordByCondition", entity);
	}

}
