package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirAgentAndOutfieldDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonAirPartAndDeptEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 公共查询组件-- 空运代理网点和外场dao实现类
 * @author 130566
 *
 */
public class CommonAirAgentAndOutfieldDao extends SqlSessionDaoSupport implements ICommonAirAgentAndOutfieldDao{
	public static final String NAMESPACE ="foss.bse.bse-baseinfo.CommonAirAgentAndOutfield.";
	
	/**
	 * 分页查询网点信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonAirPartAndDeptEntity> queryDepartmentsByEntity(
			CommonAirPartAndDeptEntity airPartAndDeptEntity,int offset,int limit) {
		airPartAndDeptEntity.setActive(FossConstants.ACTIVE);
		RowBounds row = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryDepartmentsByEntity", airPartAndDeptEntity, row);
	}
	/**
	 * 查询总数
	 */
	@Override
	public Long queryCount(CommonAirPartAndDeptEntity airPartAndDeptEntity) {
			airPartAndDeptEntity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryCount", airPartAndDeptEntity);
	}

}
