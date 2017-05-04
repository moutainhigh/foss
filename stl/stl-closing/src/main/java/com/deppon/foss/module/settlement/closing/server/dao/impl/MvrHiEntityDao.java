package com.deppon.foss.module.settlement.closing.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrHiEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrHiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrHiDto;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author 302307
 * @date 2015年12月16日11:17:59.
 */
public class MvrHiEntityDao extends iBatis3DaoImpl implements IMvrHiEntityDao {


    private static final String NAMESPACES = "foss.stv.MvrHiEntityMapper.";// 命名空间路径

    @Override
    public List<MvrHiEntity> queryMvrHi(MvrHiDto mvrHiDto, int start, int limit) {

        if(mvrHiDto != null){

            // 设置分页
            RowBounds rowBounds = new RowBounds(start,limit);
            // 查询
            return this.getSqlSession().selectList(NAMESPACES + "selectListByConditions", mvrHiDto,rowBounds);
        }
        return null;
    }

    @Override
    public MvrHiDto queryMvrHiTotal(MvrHiDto dto) {
    	
    	// 返回结果
    	return (MvrHiDto) getSqlSession().selectOne(
    			NAMESPACES + "selectTotalByConditions", dto);
    	
    }
}
