
package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPlrEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPlrDto;

/**
 * 偏线月报报表DAO实现.
 *
 * @author 095693-foss-liqin
 * @date 2013-3-7 上午10:00:01
 */
public class MvrPlrEntityDao extends iBatis3DaoImpl implements IMvrPlrEntityDao {

	/** 实例mybatis配置文件空间. */
	private static final String NAMESPACE = "foss.stv.MvrPlrEntityMapper.";


	/**
	 * 查询偏线月报报表列表. 分页
	 *
	 * @param dto the dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 095693-foss-liqin
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfrQueryService#queryMvrAfr(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrPlrEntity> selectMvrPlrByParam(MvrPlrEntity dto, int start,int limit) {
		if(null!=dto){
			// 返回结果
			RowBounds rowBounds=new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE+"selectMvrPlrByParam", dto,rowBounds);
		}
		return null;
	}

	/**
	 * 查询偏线月报表列表.
	 *
	 * @param dto the dto
	 * @return the mvr plr dto
	 * @author 095693-foss-liqin
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfrQueryService#queryMvrAfr(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto,
	 * int, int)
	 */
	@Override
	public MvrPlrDto selectMvrPlrByParamCount(MvrPlrEntity dto) {
		if(null!=dto){
			// 返回结果
			return (MvrPlrDto)this.getSqlSession().selectOne(NAMESPACE+"selectMvrPlrByParamCount",dto);
		}
		return null;
	}

	
	
	
	/** 
	 * 根据多个参数，查询偏线月报表（不分页）
	 * @author 095693-foss-liqin
	 * @date 2013-3-22 下午5:33:24
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPlrEntityDao#selectMvrPlrByParam(com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrPlrEntity> selectMvrPlrByParam(MvrPlrEntity dto) {
		if(null!=dto){
			return this.getSqlSession().selectList(NAMESPACE+"selectMvrPlrByParam",dto);
		}
		return null;
	}
}
