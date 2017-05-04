package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IDvdReturnCodDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdReturnCodEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdReturnCodDto;

/**
 * 退代收货款明细
 * @author foss-pengzhen
 * @date 2013-4-10 下午5:17:01
 * @since
 * @version
 */
public class DvdReturnCodDao extends iBatis3DaoImpl implements IDvdReturnCodDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stv.DvdReturnCodEntityMapper.";// 命名空间路径
	
	/**
	 * 根据多个参数查询退代收货款明细信息(分页）
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DvdReturnCodEntity> queryDvdReturnCodByConditions(
			DvdReturnCodDto dvdReturnCodDto, int start, int limit) {
		if(dvdReturnCodDto != null){
			// 设置分页
			RowBounds rowBounds = new RowBounds(start,limit);
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "queryDvdReturnCodByConditions", dvdReturnCodDto,rowBounds);
		}
		return null;
	}

	/**
	 * 根据多个参数查询退代收货款明细信息
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DvdReturnCodEntity> queryDvdReturnCodByConditions(
			DvdReturnCodDto dvdReturnCodDto) {
		if(dvdReturnCodDto != null){
			// 查询
			return this.getSqlSession().selectList(NAMESPACES + "queryDvdReturnCodByConditions", dvdReturnCodDto);
		}
		return null;
	}

	/**
	 * 根据多个参数查询退代收货款信息总和
	 * @author foss-pengzhen
	 * @date 2013-4-10 下午3:45:22
	 * @param dvdReturnCodDto
	 * @return
	 * @see
	 */
	@Override
	public Long queryDvdReturnCodByConditionCount(DvdReturnCodDto dvdReturnCodDto){
		if(dvdReturnCodDto != null){
			// 查询
			return (Long) this.getSqlSession().selectOne(NAMESPACES + "queryDvdReturnCodByConditionCount", dvdReturnCodDto);
		}
		return null;
	}
}
