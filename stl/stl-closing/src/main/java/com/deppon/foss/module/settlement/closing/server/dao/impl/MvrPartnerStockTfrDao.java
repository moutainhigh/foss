package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPartnerStockTfrDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpStEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpStDto;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-18 下午2:28:02    
 */
public class MvrPartnerStockTfrDao extends iBatis3DaoImpl implements IMvrPartnerStockTfrDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stl.MvrPartnerStockTfrDao.";// 命名空间路径
	
	/**
	 * 分页查询总数
	 * @param mvrPtpStDto
	 * @return
	 */
	@Override
	public Long queryMvrPtpStCount(MvrPtpStDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACES+"queryMvrPtpStCount",dto);
	}

	/**
	 * 查询合伙人股份中转月报表集合
	 * @param mvrPtpStDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<MvrPtpStEntity> queryMvrPtpStEntityList(MvrPtpStDto dto, int start, int limit) {
		// 分页
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACES+"queryMvrPtpStEntityList",dto,rb);
	}

}
