package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;


/**
 * 应收冲应付Dao
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:04:00
 */
public class BillWriteoffBillRecQueryDao extends iBatis3DaoImpl implements IBillWriteoffBillRecQueryDao {
	//命名空间
	private static final String NAMESPACES = "foss.stl.BillReceivableEntityDao.";// 命名应收单服务空间路径
	/**
	 * 
	 * 查询应收单实体
	 * @author 095793-foss-LiQin
	 * @date 2012-11-1 下午12:57:32
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao#queryReceivableList(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceivableList(BillRecWriteoffBillPayDto billRecWriteOffPayableDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACES+"selectBillByReceivableByParams", billRecWriteOffPayableDto) ;
	}
	

	/**
	 * 
	 * 根据应收单号，查询应收单
	 * @author 095793-foss-LiQin
	 * @date 2012-10-26 上午9:04:57
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao#queryReceivableNos(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceivableNos(
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACES+"selectWriteOffBillByReceivableNos",billRecWriteoffBillPayDto);
	}


	/** 
	 * 根据运单查询应收单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午6:43:20
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao#queryRecByWayBill(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryRecByWayBill(
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACES+"selectBillRecForWaybill",billRecWriteoffBillPayDto);
	}

}
