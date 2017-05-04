package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirAgentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirAgentEntity;
/**
 * 用来操作交互“航空代理”（包含航空公司代理人信息，空运代理公司）的数据库对应数据访问DAO接口的实现类：
 * <p style="display:none">modifyairlinesAgent</p>
 * <p style="display:none">version:V1.0,author:130346-foss-lifanghong,date:2013-05-16</p>
 * @author 130346-foss-lifanghong
 * @date 2013-05-16 
 * @since
 * @version
 */
public class CommonAirAgentDao extends SqlSessionDaoSupport implements ICommonAirAgentDao {
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".AirAgentEntity";
	/**
	 * 公共查询组件-查询航空代理
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-27 上午8:50:06
	 * @since
	 * @version
	 */
	@Override
	public List<AirAgentEntity> queryAirAgentEntityByEntity(
			AirAgentEntity airAgentEntity, int limit, int start) {
		return getSqlSession().selectList(NAMESPACE + ".queryAllAgentListBySelectiveCondition",  airAgentEntity, new RowBounds(start, limit));
	}
	@Override
	public Long countAirAgentEntityByEntity(AirAgentEntity airAgentEntity) {
		Long totalCount = 0l;     
        Object obj = getSqlSession().selectOne(NAMESPACE + ".queryAllAgentRecordCountByAgentname", airAgentEntity);
        if (null != obj) {
	    totalCount = Long.valueOf(obj.toString());
	}
        return totalCount;
	
	}

}
