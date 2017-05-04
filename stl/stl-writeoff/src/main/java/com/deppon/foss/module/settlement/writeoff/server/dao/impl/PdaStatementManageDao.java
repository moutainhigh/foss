package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.PdaStatementManageEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPdaStatementManageDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PdaSoaRepaymentEntity;

/**
 * DPA对账单管理Dao
 * 
 * @ClassName: PdaStatementManageDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-18 下午5:10:30
 */
public class PdaStatementManageDao extends iBatis3DaoImpl implements IPdaStatementManageDao {
    // 命名空间
    private static final String NAMESPACE = "foss.stl.PdaStatementManageDao.";

    /**
     * 根据单号去查询对账单
     * 
     * @ClassName: PdaStatementManageDao
     * @author &269052 |zhouyuan008@deppon.com
     * @date 2016-1-18 下午5:10:30
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PdaStatementManageEntity> queryStatementByNo(CommonQueryParamDto dto, int start, int limit) {
        // 最大查询100条数据
        RowBounds rb = new RowBounds(start, limit);
        List<PdaStatementManageEntity> list = this.getSqlSession().selectList(
                NAMESPACE + "queryStatementByNo", dto, rb);
        return list;
    }

    /**
     * 单条更新(根据单据号和交易流水号去更新明细的该单据本次刷卡金额和单据未核销金额)
     * 
     * @Title: updatePosCardMessage
     * @author： 269052 |zhouyuan008@deppon.com
     * @param entity 明细数据
     */
    @Override
    public int updateSinglePosCardDetail(PosCardDetailEntity entity) {
        int result = this.getSqlSession().update(NAMESPACE + "updateSinglePosCardDetailByNo", entity);
        return result;
    }

    /**
     * 根据交易流水号去更新T+0(已使用流水号金额和未使用金额)
     * 
     * @Title: updatePosCardByNumber
     * @author： 269052 |zhouyuan008@deppon.com
     */
    @Override
    public int updatePosCardByNumber(PosCardDetailEntity entity) {
        int result = this.getSqlSession().update(NAMESPACE + "updatePosCardByNo", entity);
        return result;
    }

    /**
     * 新增对账单还款关系数据
     * 
     * @Title: updatePosCardByNumber
     * @author： 269052 |zhouyuan008@deppon.com
     */
    @Override
    public int add(PdaSoaRepaymentEntity entity) {
        if (entity != null) {
            return this.getSqlSession().insert(NAMESPACE + "insertSoaRepaymentEntity", entity);
        }
        return 0;
    }
}
