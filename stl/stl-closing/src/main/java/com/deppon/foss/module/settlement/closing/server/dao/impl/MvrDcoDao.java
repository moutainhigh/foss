/**
 * Created by 105762 on 2015/3/31.
 */
package com.deppon.foss.module.settlement.closing.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoQueryDto;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDcoDao;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoResultDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author 105762
 */
public class MvrDcoDao extends iBatis3DaoImpl implements IMvrDcoDao {

    private static final String NAMESPACE = "foss.stv.MvrDcoMapper.";

    @Override
    public List<MvrDcoEntity> query(MvrDcoQueryDto dto){
        return getSqlSession().selectList(NAMESPACE + "query",dto,new RowBounds(dto.getStart(),dto.getLimit()));
    }

    @Override
    public MvrDcoResultDto queryTotal(MvrDcoQueryDto dto){
        return (MvrDcoResultDto)getSqlSession().selectOne(NAMESPACE + "queryTotal", dto);
    }
}
