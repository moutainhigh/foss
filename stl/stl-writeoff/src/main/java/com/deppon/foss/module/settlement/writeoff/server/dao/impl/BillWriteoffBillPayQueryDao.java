package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillPayQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;


/**
 * 应收冲应付Dao
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:04:00
 */
public class BillWriteoffBillPayQueryDao extends iBatis3DaoImpl implements IBillWriteoffBillPayQueryDao {
	//命名空间
	private static final String NAMESPACES = "foss.stl.BillPayableEntityDao.";// 命名应收单服务空间路径
	
	
	/**
	 * 
	 * 查询应付单
	 * @author 095793-foss-LiQin
	 * @date 2012-10-26 上午9:04:34
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao#queryPayableList(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryPayableList(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto){
		//执行查询操作
		return getSqlSession().selectList(NAMESPACES+"selectByPayableParams",billRecWriteoffBillPayDto);
	}


	/**
	 * 
	 * 根据应付单号，查询应付单
	 * @author 095793-foss-LiQin
	 * @date 2012-10-26 上午9:05:12
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao#queryPayableNos(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryPayableNos(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACES+"selectWriteOffBillPayableNos",billRecWriteoffBillPayDto);
	}
	
	/** 
	 * 根据运单查询应付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午6:43:20
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillWriteoffBillRecQueryDao#queryPayByWayBill(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryPayByWayBill(BillRecWriteoffBillPayDto billRecWriteoffBillPayDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACES+"selectBillPayForWaybill",billRecWriteoffBillPayDto );
	}
}
