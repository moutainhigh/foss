package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IDvrReturnCodDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvrReturnCodEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvrReturnCodDto;

/**
 * 退代收货款
 * @author foss-pengzhen
 * @date 2013-4-10 下午5:17:14
 * @since
 * @version
 */
public class DvrReturnCodDao extends iBatis3DaoImpl implements IDvrReturnCodDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stv.DvrReturnCodEntityMapper.";// 命名空间路径
	
	/**
	 * 根据多个参数查询退代收货款信息(分页）
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DvrReturnCodEntity> queryDvrReturnCodByConditions(
			DvrReturnCodDto dvrReturnCodDto, int start, int limit) {
		if(dvrReturnCodDto != null){
			// 设置分页
			RowBounds rowBounds = new RowBounds(start,limit);
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "queryDvrReturnCodByConditions", dvrReturnCodDto,rowBounds);
		}
		return null;
	}

	/**
	 * 根据多个参数查询退代收货款信息
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DvrReturnCodEntity> queryDvrReturnCodByConditions(
			DvrReturnCodDto dvrReturnCodDto) {
		if(dvrReturnCodDto != null){
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "queryDvrReturnCodByConditions", dvrReturnCodDto);
		}
		return null;
	}

	/**
	 * 根据多个参数查询退代收货款信息总和
	 * @author foss-pengzhen
	 * @date 2013-4-10 下午3:45:22
	 * @param dvrReturnCodDto
	 * @return
	 * @see
	 */
	@Override
	public Long queryDvrReturnCodByConditionCount(DvrReturnCodDto dvrReturnCodDto){
		if(dvrReturnCodDto != null){
			// 查询
			return (Long) this.getSqlSession().selectOne(NAMESPACES + "queryDvrReturnCodByConditionCount", dvrReturnCodDto);
		}
		return null;
	}
}
