package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;

import java.util.List;

/**
 * @author foss-youkun
 * @date 2016/1/21
 */
public interface IReceivableStatementQueryDao {

    /**
     * 添加对账单明细
     * @param list
     * @return
     */
    int AddRecivalbeStatementQuery(List<Object> list);

    /**
     * 根据对账单号查询对账单明细
     * @param billStatementNo
     * @return
     */
    List<StatementRecivableDEntity> queryReceivalbeStatementByBillNo(String billStatementNo);

    /**
     * 根据id删除对账单明细
     * @param id
     * @return
     */
    int deleteReceivableStatementById(List<String> id);


    /**
     * 根据id查询对账单明细信息
     * @param id
     * @return
     */
    StatementRecivableDEntity queryReceivalbeStatementById(String id);

	/**
	 * 根据对账单号查询明细中所有金额之和
	 * @author gpz
	 * @date 2017年2月14日
	 */
	StatementRecivableEntity queryAmountByAllDetail(String statementBillNo);

}
