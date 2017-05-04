package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillDepositReceivedQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;

/**
 * 预收单操作Dao
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-9 上午9:02:54
 */
public class WriteoffBillDepositReceivedQueryDao extends iBatis3DaoImpl implements IWriteoffBillDepositReceivedQueryDao {
	//命名空间
	private static final String NAMESPACES = "foss.stl.BillDepositReceivedEntityDao.";// 命名空间路径

	/**
	 * 根据传入的一到多个预收单号，获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 上午9:03:18
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillDepositReceivedQueryDao#queryByDepositReceivedNOs(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedNOs(BillDepositReceivedDto billDepositReceivedDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACES + "selectBillByDepositReceivedNos",billDepositReceivedDto);
	}

	/**
	 * 根据传入参数获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 上午9:03:29
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillDepositReceivedQueryDao#queryByDepositReceivedParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedParams(BillDepositReceivedDto billDepositReceivedDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACES + "selectBillByDepositReceivedParams",billDepositReceivedDto);
	}

}
