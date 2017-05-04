package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrAfiEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto;

/**
 * 始发空运往返报表dao.
 *
 * @author foss-pengzhen
 * @date 2013-3-11 上午10:47:41
 * @since
 * @version
 */
public class MvrAfiEntityDao extends iBatis3DaoImpl implements IMvrAfiEntityDao {
	
	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stv.MvrAfiEntityMapper.";// 命名空间路径

	/**
	 * 根据多个参数查询始发空运信息.(分页)
	 *
	 * @param mvrAfiDto the mvr afi dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrAfiEntity> selectMvrAfiByConditions(MvrAfiDto mvrAfiDto,int start,int limit) {
		if(mvrAfiDto != null){
			// 设置分页
			RowBounds rowBounds = new RowBounds(start,limit);
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "selectMvrAfiByConditions", mvrAfiDto,rowBounds);
		}
		return null;
	}
	
	/**
	 * 根据多个参数查询始发空运信息
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrAfiEntity> selectMvrAfiByConditions(MvrAfiDto mvrAfiDto){
		if(mvrAfiDto != null){
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "selectMvrAfiByConditions", mvrAfiDto);
		}
		return null;
	}
	
	/**
	 * 根据多个参数查询始发空运信息总数.
	 *
	 * @param mvrAfiDto the mvr afi dto
	 * @return the long
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @see
	 */
	@Override
	public Long selectMvrAfiByConditionsCount(MvrAfiDto mvrAfiDto){
		if(mvrAfiDto != null){
			// 查询
			return (Long)this.getSqlSession().selectOne(NAMESPACES + "selectMvrAfiByConditionsCount", mvrAfiDto);
		}
		return null;
	}
	
	/**
	 * 根据多个参数查询统计始发空运信息总数.
	 *
	 * @param mvrAfiDto the mvr afi dto
	 * @return the mvr afi entity
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @see
	 */
	@Override
	public MvrAfiEntity selectMvrAfiByConditionsSum(MvrAfiDto mvrAfiDto){
		if(mvrAfiDto != null){
			// 查询
			return (MvrAfiEntity) this.getSqlSession().selectOne(NAMESPACES + "selectMvrAfiByConditionsSum", mvrAfiDto);
		}
		return null;
	}
}
