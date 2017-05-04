package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfontEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityTotalDto;


/**
 * 始发应收月报Dao接口
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:17:47
 */
public class MvrNrfontEntityDao extends iBatis3DaoImpl implements IMvrNrfontEntityDao {

	private static final String NAMESPACES = "foss.stv.MvrNrfontEntityMapper.";
	
	/** 
	 *  根据条件查询始发应收列表
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午3:17:57
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfontEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfontEntity> selectByConditionsAndPage(
			MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto,int start, int limit) {

		//分页始发应收查询
		RowBounds rowBounds = new RowBounds(start, limit);
		
		//返回查询结果
		return this.getSqlSession().selectList(
				NAMESPACES + "selectByConditions", mvrNrfontEntityQueryDto, rowBounds);
	}

	/** 
	 * 根据条件查询始发应收统计信息
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午3:17:59
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfontEntityDao#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto)
	 */
	@Override
	public MvrNrfontEntityTotalDto selectTotalByConditions(
			MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto) {
		
		//查询返回始发应收统计结果
		return (MvrNrfontEntityTotalDto)this.getSqlSession().selectOne(
				NAMESPACES + "selectTotalByConditions", mvrNrfontEntityQueryDto);
	}

	/** 
	 *  根据条件查询始发应收列表（不分页）
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:25:58
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfontEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfontEntity> selectByConditions(
			MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto) {
		
		//返回查询结果
		return this.getSqlSession().selectList(
						NAMESPACES + "selectByConditions", mvrNrfontEntityQueryDto);
	}

}
