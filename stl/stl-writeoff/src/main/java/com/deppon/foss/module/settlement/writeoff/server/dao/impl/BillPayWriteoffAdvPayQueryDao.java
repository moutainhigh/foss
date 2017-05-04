package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillPayWriteoffAdvPayQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.AdvPayWriteoffBillPayDto;

/**
 * 预付冲应付Dao
 * @author foss-pengzhen
 * @date 2012-10-26 下午3:17:50
 * @since
 * @version
 */
public class BillPayWriteoffAdvPayQueryDao extends iBatis3DaoImpl implements IBillPayWriteoffAdvPayQueryDao {
	//命名空间
	private static final String NAMESPACES = "foss.stl.BillPayableEntityDao.";// 命名空间路径
	
	
	
	/**
     * 
     * 根据传入的一到多个应付单号，获取一到多条应付单信息
     * 
     * @author foss-pengzhen
     * @date 2012-10-18 上午11:58:36
     * @param BillPayableDto
     *            单号集合
     * @return
     * @see 
     */
    @SuppressWarnings("unchecked")
    public List<BillPayableEntity> queryPayableNOs(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto) {
		//执行查询操作
    	return this.getSqlSession().selectList(NAMESPACES + "selectBillPayableNos", advPayWriteoffBillPayDto);
    }

    /**
     * 根据传入的参数获取一到多条应付单信息
     * @author foss-pengzhen
     * @date 2012-10-18 上午11:58:36
     * @param BillPayableDto
     *            单号集合
     * @return
     * @see 
     */
    @SuppressWarnings("unchecked")
    public List<BillPayableEntity> queryPayableParams(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto) {
		//执行查询操作
    	return this.getSqlSession().selectList(NAMESPACES + "selectBillPayableParams",advPayWriteoffBillPayDto);
    }
}
