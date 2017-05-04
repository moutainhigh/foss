package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfoEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityTotalDto;


/**
 * 始发应收月报Dao接口
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:17:47
 */
public class MvrRfoEntityDao extends iBatis3DaoImpl implements IMvrRfoEntityDao {

	private static final String NAMESPACES = "foss.stv.MvrRfoEntityMapper.";
	
	/** 
	 *  根据条件查询始发应收列表
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午3:17:57
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfoEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrRfoEntity> selectByConditionsAndPage(
			MvrRfoEntityQueryDto mvrRfoEntityQueryDto,int start, int limit) {

		//分页始发应收查询
		RowBounds rowBounds = new RowBounds(start, limit);
		
		//返回查询结果
		return this.getSqlSession().selectList(
				NAMESPACES + "selectByConditions", mvrRfoEntityQueryDto, rowBounds);
	}

	/** 
	 * 根据条件查询始发应收统计信息
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午3:17:59
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfoEntityDao#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto)
	 */
	@Override
	public MvrRfoEntityTotalDto selectTotalByConditions(
			MvrRfoEntityQueryDto mvrRfoEntityQueryDto) {
		
		//查询返回始发应收统计结果
		return (MvrRfoEntityTotalDto)this.getSqlSession().selectOne(
				NAMESPACES + "selectTotalByConditions", mvrRfoEntityQueryDto);
	}

	/** 
	 *  根据条件查询始发应收列表（不分页）
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:25:58
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfoEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrRfoEntity> selectByConditions(
			MvrRfoEntityQueryDto mvrRfoEntityQueryDto) {
		
		//返回查询结果
		return this.getSqlSession().selectList(
						NAMESPACES + "selectByConditions", mvrRfoEntityQueryDto);
	}

}
