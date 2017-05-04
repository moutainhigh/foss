package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrWoodenEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrWoodenEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrWoodenDto;
/**
 * 代打木架dao
 * @author 045738
 */
public class MvrWoodenEntityDao extends iBatis3DaoImpl implements IMvrWoodenEntityDao{

	private static final String NAMESPACES = "foss.stv.MvrWoodenEntityMapper.";
	 /**
     *根据传入查询代打木架月报--分页
     * @author 045738 
     * @param 
     * @date 2014-4-11 上午11:57:20
     * @return 
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrWoodenEntity> selectMvrWoodenByParam(MvrWoodenDto dto,
			int start, int limit) {
		//分页始发应收查询
		RowBounds rowBounds = new RowBounds(start, limit);
		
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "selectByConditions", dto, rowBounds);
	}

	/**
     * 根据传入查询代打木架月报条数
     * @author 045738 
     * @param 
     * @date 2014-4-11 上午11:57:20
     * @return 
     */
	@Override
	public MvrWoodenDto selectMvrWoodenByParamCount(MvrWoodenDto dto) {
		//查询返回始发应收统计结果
		return (MvrWoodenDto)this.getSqlSession().selectOne(
				NAMESPACES + "selectTotalByConditions", dto);
	}

	/**
     * 根据传入查询代打木架月报表--不分页
     * @author 045738 
     * @date 2014-4-11 上午11:57:20
     * @param dto
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrWoodenEntity> selectMvrWoodenByParam(MvrWoodenDto dto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "selectByConditions", dto);
	}

}
