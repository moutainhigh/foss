package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfonoEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfonoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityTotalDto;


/**
 * 01普通业务始发月报表Dao接口
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfonoEntityDao extends iBatis3DaoImpl implements IMvrNrfonoEntityDao {

	private static final String NAMESPACES = "foss.stv.MvrNrfonoEntityMapper.";
	
	/** 
	 *  根据条件查询始发应收列表
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.ImvrNrfonoEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfonoEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfonoEntity> selectByConditionsAndPage(
			MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto,int start, int limit) {

		//分页始发应收查询
		RowBounds rowBounds = new RowBounds(start, limit);
		
		//返回查询结果
		return this.getSqlSession().selectList(
				NAMESPACES + "selectByConditions", mvrNrfonoEntityQueryDto, rowBounds);
	}

	/** 
	 * 根据条件查询始发应收统计信息
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.ImvrNrfonoEntityDao#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfonoEntityQueryDto)
	 */
	@Override
	public MvrNrfonoEntityTotalDto selectTotalByConditions(
			MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto) {
		
		//查询返回始发应收统计结果
		return (MvrNrfonoEntityTotalDto)this.getSqlSession().selectOne(
				NAMESPACES + "selectTotalByConditions", mvrNrfonoEntityQueryDto);
	}

	/** 
	 *  根据条件查询始发应收列表（不分页）
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.ImvrNrfonoEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfonoEntityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfonoEntity> selectByConditions(
			MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto) {
		
		//返回查询结果
		return this.getSqlSession().selectList(
						NAMESPACES + "selectByConditions", mvrNrfonoEntityQueryDto);
	}

}
