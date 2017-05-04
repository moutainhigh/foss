package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfostEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityTotalDto;


/**
 * 始发应收月报Dao接口
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:17:47
 */
public class MvrNrfostEntityDao extends iBatis3DaoImpl implements IMvrNrfostEntityDao {

	private static final String NAMESPACES = "foss.stv.MvrNrfostEntityMapper.";
	
	/** 
	 *  根据条件查询始发应收列表
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午3:17:57
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfostEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfostEntity> selectByConditionsAndPage(
			MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto,int start, int limit) {

		//分页始发应收查询
		RowBounds rowBounds = new RowBounds(start, limit);
		
		//返回查询结果
		return this.getSqlSession().selectList(
				NAMESPACES + "selectByConditions", mvrNrfostEntityQueryDto, rowBounds);
	}

	/** 
	 * 根据条件查询始发应收统计信息
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午3:17:59
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfostEntityDao#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto)
	 */
	@Override
	public MvrNrfostEntityTotalDto selectTotalByConditions(
			MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto) {
		
		//查询返回始发应收统计结果
		return (MvrNrfostEntityTotalDto)this.getSqlSession().selectOne(
				NAMESPACES + "selectTotalByConditions", mvrNrfostEntityQueryDto);
	}

	/** 
	 *  根据条件查询始发应收列表（不分页）
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:25:58
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfostEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfostEntity> selectByConditions(
			MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto) {
		
		//返回查询结果
		return this.getSqlSession().selectList(
						NAMESPACES + "selectByConditions", mvrNrfostEntityQueryDto);
	}

}
