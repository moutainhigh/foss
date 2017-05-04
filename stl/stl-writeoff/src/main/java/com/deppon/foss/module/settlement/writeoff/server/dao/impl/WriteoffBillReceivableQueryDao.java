package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillReceivableQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto;

/**
 * 应收单操作Dao
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-9 上午9:03:46
 */
public class WriteoffBillReceivableQueryDao extends iBatis3DaoImpl implements IWriteoffBillReceivableQueryDao {
	//命名空间
	private static final String NAMESPACES = "foss.stl.BillReceivableEntityDao.";// 命名空间路径

	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 上午9:03:49
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillReceivableQueryDao#queryByReceivableNOs(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByReceivableNOs(BillReceivableDto billReceivableDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACES + "selectBillByReceivableNos",billReceivableDto);
	}

	/**
	 * 根据传入的参数获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 上午9:03:51
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillReceivableQueryDao#queryByDepositReceivedParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByReceivableParams(BillReceivableDto billReceivableDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACES + "selectBillByReceivableParams",billReceivableDto);
	}

	
	/**
	 * 根据传入的运单号查询应收单
	 * 
	 * @param billReceivableDto 查询实体
	 * @return List<BillReceivableEntity> 应收单合集
	 * @author 杨书硕
	 * @date 2013-07-08 11:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByWayBillNOs(
			BillReceivableDto billReceivableDto) {
		//查询
		return this.getSqlSession().selectList(NAMESPACES + "selectBillByWayBillNos",billReceivableDto);
	}
}
