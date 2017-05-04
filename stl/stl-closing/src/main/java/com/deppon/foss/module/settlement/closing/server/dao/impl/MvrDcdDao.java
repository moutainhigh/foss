/**
 * Created by 105762 on 2015/3/31.
 */
package com.deppon.foss.module.settlement.closing.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDcdDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdResultDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author 105762
 */
public class MvrDcdDao extends iBatis3DaoImpl implements IMvrDcdDao {

    private static final String NAMESPACE = "foss.stv.MvrDcdMapper.";

    @Override
    public List<MvrDcdEntity> query(MvrDcdQueryDto dto){
        return getSqlSession().selectList(NAMESPACE + "query",dto,new RowBounds(dto.getStart(),dto.getLimit()));
    }

    @Override
    public MvrDcdResultDto queryTotal(MvrDcdQueryDto dto){
        return (MvrDcdResultDto)getSqlSession().selectOne(NAMESPACE + "queryTotal", dto);
    }
}
