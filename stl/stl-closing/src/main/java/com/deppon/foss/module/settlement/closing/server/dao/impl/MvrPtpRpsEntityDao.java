package com.deppon.foss.module.settlement.closing.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpRpsDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpsEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpsDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 *
 * 合伙人子奖罚特殊月报表DAO
 *
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */
public class MvrPtpRpsEntityDao extends iBatis3DaoImpl implements IMvrPtpRpsDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stl.MvrPtpRpsEntityMapper.";// 命名空间路径


	/**
	 * 查询合伙人奖罚特殊的月报表总条数
	 * @param dto
	 * @return
     */
	public Long queryMvrPtpRpsCount(MvrPtpRpsDto dto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACES+"queryMvrPtpRpsCount",dto);
	}

	/**
	 * 查询合伙人奖罚特殊的月报表集合
	 * @param dto
	 * @param offset
	 * @param limit
     * @return
     */
	public List<MvrPtpRpsEntity> queryMvrPtpRpsEntityList(MvrPtpRpsDto dto, int offset, int limit) {
		// 分页
		RowBounds rb = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(NAMESPACES+"queryMvrPtpRpsEntityList",dto,rb);
	}

	/**
	 * 导出合伙人奖罚特殊月报表
	 * @param dto
	 * @return
     */
	public List<MvrPtpRpsEntity> exportMvrPtpPsc(MvrPtpRpsDto dto) {
		return  this.getSqlSession().selectList(NAMESPACES+"exportMvrPtpPsc",dto);
	}
}
