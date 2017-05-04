package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IOppStatementDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.OppStatementDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by 302307 on 2016/4/26.
 */
public class OppStatementDao extends iBatis3DaoImpl implements IOppStatementDao {

    private static final String NAMESPACES = "foss.stl.oppStatementDao.";

    @Override
    public List<StatementOfAccountEntity> queryOppStatementByCondition(OppStatementDto dto) {
        //设置分页大小及起始页
        RowBounds rb = new RowBounds(dto.getStart(), dto.getLimit());
        List<StatementOfAccountEntity> statementOfAccountEntities = getSqlSession().selectList(NAMESPACES + "queryOppStatementByCondition", dto,rb);
        return statementOfAccountEntities;
    }

    @Override
    public int queryOppStatementByConditionCount(OppStatementDto dto) {
        int count = (Integer)getSqlSession().selectOne(NAMESPACES + "queryOppStatementByConditionCount", dto);
        return count;
    }

    @Override
    public List<String> queryOppStatementBillNosByCondition(OppStatementDto dto) {
        List<String> statementBillNos = getSqlSession().selectList(NAMESPACES + "queryOppStatementBillNosByCondition", dto);
        return statementBillNos;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<StatementOfAccountEntity> queryOppStatementByNo(OppStatementDto dto) {
        List<StatementOfAccountEntity> statementOfAccountEntities = getSqlSession().selectList(NAMESPACES + "queryOppStatementByNo", dto);
        return statementOfAccountEntities;
    }

    @Override
    public List<StatementOfAccountDEntity> queryOppStatementDetailsByNos(List<String> list) {
        List<StatementOfAccountDEntity> statementOfAccountDEntityList = getSqlSession().selectList(NAMESPACES + "queryOppStatementDetailsByNos", list);
        return statementOfAccountDEntityList;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<StatementOfAccountDEntity> queryOppStatementDetailsByNo(OppStatementDto dto) {
        //设置分页大小及起始页
        RowBounds rb = new RowBounds(dto.getStart(), dto.getLimit());
        List<StatementOfAccountDEntity> statementOfAccountDEntityList = this.getSqlSession().selectList(NAMESPACES + "queryOppStatementDetailsByNo", dto,rb);
        return statementOfAccountDEntityList;
    }

    public int queryOppStatementDetailsByNoCount(OppStatementDto dto){
        int count = (Integer)getSqlSession().selectOne(NAMESPACES + "queryOppStatementDetailsByNoCount", dto);
        return count;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<StatementOfAccountDEntity> queryOppStatementDetailsAllByNo(OppStatementDto dto) {
        List<StatementOfAccountDEntity> statementOfAccountDEntityList = getSqlSession().selectList(NAMESPACES + "queryOppStatementDetailsByNo", dto);
        return statementOfAccountDEntityList;
    }
}
