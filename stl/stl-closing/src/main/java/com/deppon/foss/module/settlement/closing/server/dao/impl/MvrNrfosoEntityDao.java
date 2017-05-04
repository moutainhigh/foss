package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfosoEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfosoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityTotalDto;


/**
 * 01特殊业务始发月报表Dao接口
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfosoEntityDao extends iBatis3DaoImpl implements IMvrNrfosoEntityDao {

	private static final String NAMESPACES = "foss.stv.MvrNrfosoEntityMapper.";
	
	/** 
	 *  根据条件查询始发应收列表
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.ImvrNrfosoEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfosoEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfosoEntity> selectByConditionsAndPage(
			MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto,int start, int limit) {

		//分页始发应收查询
		RowBounds rowBounds = new RowBounds(start, limit);
		
		//返回查询结果
		return this.getSqlSession().selectList(
				NAMESPACES + "selectByConditions", mvrNrfosoEntityQueryDto, rowBounds);
	}

	/** 
	 * 根据条件查询始发应收统计信息
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.ImvrNrfosoEntityDao#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfosoEntityQueryDto)
	 */
	@Override
	public MvrNrfosoEntityTotalDto selectTotalByConditions(
			MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto) {
		
		//查询返回始发应收统计结果
		return (MvrNrfosoEntityTotalDto)this.getSqlSession().selectOne(
				NAMESPACES + "selectTotalByConditions", mvrNrfosoEntityQueryDto);
	}

	/** 
	 *  根据条件查询始发应收列表（不分页）
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.ImvrNrfosoEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfosoEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfosoEntity> selectByConditions(
			MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto) {
		
		//返回查询结果
		return this.getSqlSession().selectList(
						NAMESPACES + "selectByConditions", mvrNrfosoEntityQueryDto);
	}

}
