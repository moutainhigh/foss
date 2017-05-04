package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPliEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPliDto;

/**
 * 始发偏线往返报表dao.
 *
 * @author foss-pengzhen
 * @date 2013-3-11 上午10:47:02
 * @since
 * @version
 */
public class MvrPliEntityDao extends iBatis3DaoImpl implements IMvrPliEntityDao {
	
	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stv.MvrPliEntityMapper.";// 命名空间路径

	/**
	 * 根据多个参数查询始发偏线信息.
	 *
	 * @param mvrPliDto the mvr pli dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrPliEntity> selectMvrPliByConditions(MvrPliDto mvrPliDto,int start,int limit) {
		if(mvrPliDto != null){
			// 设置分页
			RowBounds rowBounds = new RowBounds(start,limit);
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "selectMvrPliByConditions", mvrPliDto,rowBounds);
		}
		return null;
	}
	
	/**
	 * 根据多个参数查询始发偏线信息.(不分页)
	 *
	 * @param mvrPliDto the mvr pli dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrPliEntity> selectMvrPliByConditions(MvrPliDto mvrPliDto) {
		if(mvrPliDto != null){
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "selectMvrPliByConditions", mvrPliDto);
		}
		return null;
	}
	
	/**
	 * 根据多个参数查询始发偏线信息总数.
	 *
	 * @param mvrPliDto the mvr pli dto
	 * @return the long
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @see
	 */
	@Override
	public Long selectMvrPliByConditionsCount(MvrPliDto mvrPliDto){
		if(mvrPliDto != null){
			// 查询
			return (Long)this.getSqlSession().selectOne(NAMESPACES + "selectMvrPliByConditionsCount", mvrPliDto);
		}
		return null;
	}
	
	/**
	 * 根据多个参数查询统计始发偏线信息总数.
	 *
	 * @param mvrPliDto the mvr pli dto
	 * @return the mvr pli entity
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @see
	 */
	@Override
	public MvrPliEntity selectMvrPliByConditionsSum(MvrPliDto mvrPliDto){
		if(mvrPliDto != null){
			// 查询
			return (MvrPliEntity) this.getSqlSession().selectOne(NAMESPACES + "selectMvrPliByConditionsSum", mvrPliDto);
		}
		return null;
	}
}
