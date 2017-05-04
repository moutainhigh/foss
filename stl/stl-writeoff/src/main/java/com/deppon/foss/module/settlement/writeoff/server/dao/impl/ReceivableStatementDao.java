/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IReceivableStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementRecivableDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author foss-youkun
 * @date 2016/1/19
 */
public class ReceivableStatementDao  extends iBatis3DaoImpl implements IReceivableStatementDao{

    private static  String NAMESPACE="foss.stl.StatementRecivable.";
    /**
     * 添加对账单
     * @param entity
     * @return
     */
    public int AddRecivalbeStatement(StatementRecivableEntity entity) {

        return getSqlSession().insert(NAMESPACE+"insert",entity);
    }

    /**
     * 更新对账单
     * @param statementStatus
     * @return
     */
    public int updateStatementByStatementStatus(String statementStatus,List<String> statementBillNo) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("statementStatus",statementStatus);
        map.put("list",statementBillNo);
        return getSqlSession().update(NAMESPACE+"updateStatementByStatementStatus",map);
    }

    /**
     * 查询合伙人收款对账单
     * @param dto
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<StatementRecivableEntity> queryPartnerReceivalbeStatement(StatementRecivableDto dto,int start ,int limit) {
        //分页设置
        //RowBounds rowBounds = new RowBounds(start, limit);

        return getSqlSession().selectList(NAMESPACE+"queryPartnerReceivalbeStatement",dto);
    }

    /**
     * 查询合伙人对账单的总记录数
     * @param dto
     * @return
     */
    public Long queryPartnerReceivalbeStatementCount(StatementRecivableDto dto) {
        return (Long)getSqlSession().selectOne(NAMESPACE+"queryPartnerReceivalbeStatementCount",dto);
    }

    /**
     *
     * @param id
     * @return
     */
    public StatementRecivableEntity queryReceivalbeStatementById(String id) {
        return (StatementRecivableEntity)getSqlSession().selectOne(NAMESPACE+"queryReceivalbeStatementById",id);
    }

    /**
     * 根据对账单号查询对账单信息
     * @param statementNo
     * @return
     */
    public StatementRecivableEntity queryPartnerReceivableByStatemenNo(String statementNo) {
        return (StatementRecivableEntity)getSqlSession().selectOne(NAMESPACE+"queryPartnerReceivableByStatemenNo",statementNo);
    }

    /**
     *
     * @param entity
     * @return
     */
    public int updateStatementRecivableEntity(StatementRecivableEntity entity) {

        StatementRecivableEntity statementRecivableEntity = new StatementRecivableEntity();
        //本期剩余应收金额
        statementRecivableEntity.setPeriodUnverifyRecAmount(entity.getPeriodUnverifyRecAmount());
        //本期已还金额
        statementRecivableEntity.setPeriodRrpayAmount(entity.getPeriodRrpayAmount());
        //本期未还金额
        statementRecivableEntity.setPeriodNpayAmount(entity.getPeriodUnverifyRecAmount());
        int i = entity.getSettleNum();
        i=i+1;
        //结账次数
        statementRecivableEntity.setSettleNum((short)i);
        //对账单描述
        statementRecivableEntity.setStatementDes(entity.getStatementDes());
        //对账单单号
        statementRecivableEntity.setStatementBillNo(entity.getStatementBillNo());

        return getSqlSession().update(NAMESPACE+"updateStatementRecivableEntity",statementRecivableEntity);
    }

    /**
     * 删除对账单明细时修改对账单中对应的金额
     * @param entity
     * @return
     */
    public int updateByStatementNo(StatementRecivableEntity entity) {
        return getSqlSession().update(NAMESPACE+"updateByStatementNo",entity);
    }

    /**
     * 根据对账单号查询对账单信息
     * @param list
     * @return
     */
    public List<StatementRecivableEntity> queryPartnerStatementList(List<String> list) {
        return (List<StatementRecivableEntity> )getSqlSession().selectList(NAMESPACE+"queryPartnerStatementList",list);
    }


}
