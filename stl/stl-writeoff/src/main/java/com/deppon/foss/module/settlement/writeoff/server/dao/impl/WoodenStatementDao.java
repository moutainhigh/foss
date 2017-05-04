package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IWoodenStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WoodenStatementDto;

public class WoodenStatementDao extends iBatis3DaoImpl implements IWoodenStatementDao {
	private static final String NAMESPACE = "foss.stl.WoodenStatementDao.";
	/**
	 * 按客户查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementDEntity> queryWoodenStatementDByCustomer(WoodenStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<WoodenStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryWoodenStatementDByCustomer",dto,rb);
		return list;
	}
	/**
	 * 按单号查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementDEntity> queryWoodenStatementDByNumber(WoodenStatementDto dto) {
		List<WoodenStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryWoodenStatementDByNumber",dto);
		return list;
	}
	/**
	 * 按客户查询应收单应付单总行数
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int countWoodenStatementDByCustomer(WoodenStatementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "countWoodenStatementDByCustomer",dto);
		return count;
	}
	/**
	 * 按客户查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementEntity> queryWoodenStatementByCustomer(WoodenStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<WoodenStatementEntity> list = getSqlSession().selectList(NAMESPACE + "queryWoodenStatementByCustomer",dto,rb);
		return list;
	}
	/**
	 * 按单号查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementEntity> queryWoodenStatementByNumber(WoodenStatementDto dto) {
		List<WoodenStatementEntity> list = getSqlSession().selectList(NAMESPACE + "queryWoodenStatementByNumber",dto);
		return list;
	}
	/**
	 * 按客户查询对账单总个数
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int countWoodenStatementByCustomer(WoodenStatementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "countWoodenStatementByCustomer",dto);
		return count;
	}
	/**
	 * 按对账单单号查询对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementDEntity> queryWoodenDByStatementBillNo(WoodenStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<WoodenStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryWoodenDByStatementBillNo",dto,rb);
		return list;
	}
	/**
	 * 按对账单单号查询对账单明细总条数
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int countWoodenDByStatementBillNo(WoodenStatementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "countWoodenDByStatementBillNo",dto);
		return count;
	}
	/**
	 * 按客户保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int woodenStatementDSaveByCustomer(WoodenStatementDto woodenStatementDto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "woodenStatementDSaveByCustomer",woodenStatementDto);
		return insertDRows;
	}
	/**
	 * 按单号保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int woodenStatementDSaveByNumber(WoodenStatementDto woodenStatementDto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "woodenStatementDSaveByNumber",woodenStatementDto);
		return insertDRows;
	}
	/**
	 * 按对账单单号保存对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int woodenStatementSaveByStatementBillNo(WoodenStatementDto woodenStatementDto) {
		int insertRows = getSqlSession().insert(NAMESPACE + "woodenStatementSaveByStatementBillNo",woodenStatementDto);
		return insertRows;
	}
	/**
	 * 更新应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int woodenPayUpdateByStatementBillNo(WoodenStatementDto woodenStatementDto) {
		int updatePayRows = getSqlSession().update(NAMESPACE + "woodenPayableUpdateByStatementBillNo",woodenStatementDto);
		return updatePayRows;
	}
	/**
	 * 更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int woodenRecUpdateByStatementBillNo(WoodenStatementDto woodenStatementDto) {
		int updateRecRows = getSqlSession().update(NAMESPACE + "woodenReceivedUpdateByStatementBillNo",woodenStatementDto);
		return updateRecRows;
	}
	/**
	 * 确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int confirmWoodenStatement(WoodenStatementDto woodenStatementDto) {
		int updateRows = getSqlSession().update(NAMESPACE + "confirmWoodenStatement",woodenStatementDto);
		return updateRows;
	}
	/**
	 * 反确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int unConfirmWoodenStatement(WoodenStatementDto woodenStatementDto) {
		int updateRows = getSqlSession().update(NAMESPACE + "unConfirmWoodenStatement",woodenStatementDto);
		return updateRows;
	}
	/**
	 * 按单号添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int addWoodenStatementDByNumber(WoodenStatementDto woodenStatementDto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "woodenStatementDSaveByNumber",woodenStatementDto);
		return insertDRows;
	}
	/**
	 * 按客户添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int addWoodenStatementDByCustomer(WoodenStatementDto woodenStatementDto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "woodenStatementDSaveByCustomer",woodenStatementDto);
		return insertDRows;
	}
	/**
	 * 按单号保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementDEntity> queryWoodenDByStatementBillNo(WoodenStatementDto woodenStatementDto) {
		List<WoodenStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryWoodenDByStatementBillNo",woodenStatementDto);
		return list;
	}
	/**
	 * 更新对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int woodenStatementUpdateByStatementBillNo(WoodenStatementDto woodenStatementDto) {
		int updateRows = getSqlSession().update(NAMESPACE + "woodenStatementUpdateByStatementBillNo",woodenStatementDto);
		return updateRows;
	}
	/**
	 * 查询添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementDEntity> queryAddWoodenStatementD(WoodenStatementDto dto) {
		List<WoodenStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryWoodenStatementDByNumber",dto);
		return list;
	}
	/**
	 * 查询删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoodenStatementDEntity> queryDelWoodenStatementD(WoodenStatementDto dto) {
		List<WoodenStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryDelWoodenStatementD",dto);
		return list;
	}
	/**
	 * 按单号删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int delWoodenStatementD(WoodenStatementDto woodenStatementDto) {
		int updateRows = getSqlSession().update(NAMESPACE + "delWoodenStatementD",woodenStatementDto);
		return updateRows;
	}
	/**
	 * 按对账单单号更新应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int updatePayStatementBillNo(WoodenStatementDto woodenStatementDto) {
		int updatePayRows = getSqlSession().update(NAMESPACE + "updatePayStatementBillNo",woodenStatementDto);
		return updatePayRows;
	}
	/**
	 * 按对账单单号更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int updateRecStatementBillNo(WoodenStatementDto woodenStatementDto) {
		int updateRecRows = getSqlSession().update(NAMESPACE + "updateRecStatementBillNo",woodenStatementDto);
		return updateRecRows;
	}
	/**
	 * 按对账单单号查询核销单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int queryWriteoffBillByStatementBillNo(String statementBillNo) {
		int writeoffBillCount = (Integer)getSqlSession().selectOne(NAMESPACE + "queryWriteoffBillByStatementBillNo",statementBillNo);
		return writeoffBillCount;
	}

}
