/**
 * Created by 105762 on 2015/3/31.
 */
package com.deppon.foss.module.settlement.closing.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDciDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDciEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciResultDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author 105762
 */
public class MvrDciDao extends iBatis3DaoImpl implements IMvrDciDao {

    private static final String NAMESPACE = "foss.stv.MvrDciMapper.";

    @Override
    public List<MvrDciEntity> query(MvrDciQueryDto dto){
        return getSqlSession().selectList(NAMESPACE + "query",dto,new RowBounds(dto.getStart(),dto.getLimit()));
    }

    @Override
    public MvrDciResultDto queryTotal(MvrDciQueryDto dto){
        return (MvrDciResultDto)getSqlSession().selectOne(NAMESPACE + "queryTotal", dto);
    }
}
