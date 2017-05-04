package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpDqpaDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpDqpaEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpDqpaQueryDto;

/**
 * 合伙人德启代付月报表dao
 * 
 * @author gpz
 * @date 2016年3月21日
 */
public class MvrPtpDqpaDao extends iBatis3DaoImpl implements IMvrPtpDqpaDao {

	/**
	 * mapper文件对应的命名空间
	 */
	private static final String NAMESPACE = "closing.MvrPtpDqpaMapper.";

	/**
	 * 分页查询合伙人德启代付月报表数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrPtpDqpaEntity> queryMvrPtpDqpaByParams(
			MvrPtpDqpaQueryDto queryDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryMvrPtpDqpaByParams", queryDto, rowBounds);
	}

	/**
	 * 查询合伙人德启代付月报表数据总数
	 */
	@Override
	public Long queryMvrPtpDqpaEntityTotalCount(MvrPtpDqpaQueryDto queryDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryMvrPtpDqpaEntityTotalCount", queryDto);
	}

}
