package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllAgentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AllAgentEntity;

/**
 * 用来操作交互“代理”的数据库对应数据访问DAO接口的实现类：
 * <p style="display:none">modifyairlinesAgent</p>
 * <p style="display:none">version:V1.0,author:130346-foss-lifanghong,date:2013-05-16</p>
 * @author 130346-foss-lifanghong
 * @date 2013-05-16 
 * @since
 * @version
 */
public class CommonAllAgentDao extends SqlSessionDaoSupport implements ICommonAllAgentDao {
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".AllAgent";
	@Override
	public List<AllAgentEntity> queryAgentByCondtion(
			AllAgentEntity allAgentEntity, int limit, int start) {		
		return getSqlSession().selectList(NAMESPACE + ".queryAllAgentListBySelectiveCondition",  allAgentEntity, new RowBounds(start, limit));
	}

	@Override
	public Long countRecordByCondtion(AllAgentEntity allAgentEntity) {
		Long totalCount = 0l;     
        Object obj = getSqlSession().selectOne(NAMESPACE + ".queryAllAgentRecordCountByAgentname", allAgentEntity);
        if (null != obj) {
	    totalCount = Long.valueOf(obj.toString());
	}
        return totalCount;
	
	}

}
