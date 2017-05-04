package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IReceivableStatementQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账单明细
 * @author foss-youkun
 * @date 2016/1/21
 */
public class ReceivableStatementQueryDao  extends iBatis3DaoImpl implements IReceivableStatementQueryDao{

   private static  String NAMESPACE="foss.stl.StatementRecivableDEntityQuery.";

    /**
     * 添加对账单明细
     * @param list
     * @return
     */
    public int AddRecivalbeStatementQuery(List<Object> list) {
            //需要把list
            return getSqlSession().insert(NAMESPACE+"insert",list);
    }
    /**
     * 根据对账单号查询对账单明细
     * @param billStatementNo
     * @return
     */
    public List<StatementRecivableDEntity> queryReceivalbeStatementByBillNo(String billStatementNo) {
        return getSqlSession().selectList(NAMESPACE + "queryReceivalbeStatementByBillNo", billStatementNo);
    }

    /**
     * 根据Id删除收款对账单明细
     * @param id
     * @return
     */
    public int deleteReceivableStatementById(List<String> id) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list",id.get(0));
        map.put("isDelete","Y");
        return getSqlSession().update(NAMESPACE+"deleteReceivableStatementById",map);
    }

    /**
     * 根据id查询对账单明细的信息
     * @param id
     * @return
     */
    public StatementRecivableDEntity queryReceivalbeStatementById(String id) {
        return (StatementRecivableDEntity)getSqlSession().selectOne(NAMESPACE+"queryReceivalbeStatementById",id);
    }
    
	@Override
	public StatementRecivableEntity queryAmountByAllDetail(
			String statementBillNo) {
		return (StatementRecivableEntity) this.getSqlSession().selectOne(
				NAMESPACE + "queryAmountByAllDetail", statementBillNo);
	}

}
