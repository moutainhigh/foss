package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.ICustomerStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomerStatementDto;

public class CustomerStatementDao extends iBatis3DaoImpl implements ICustomerStatementDao {
	private static final String NAMESPACE = "foss.stl.CustomerStatementDao.";
	/**
	 * 按客户查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementDEntity> queryCustomerStatementDByCustomer(CustomerStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<CustomerStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryCustomerStatementDByCustomer",dto,rb);
		return list;
	}

	/**
	 * 按单号查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementDEntity> queryCustomerStatementDByNumber(CustomerStatementDto dto) {
		List<CustomerStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryCustomerStatementDByNumber",dto);
		return list;
	}
	
	/**
	 * 按客户查询应收单应付单总行数
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int countCustomerStatementDByCustomer(CustomerStatementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "countCustomerStatementDByCustomer",dto);
		return count;
	}
	
	/**
	 * 按客户保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int customerStatementDSaveByCustomer(CustomerStatementDto dto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "customerStatementDSaveByCustomer",dto);
		return insertDRows;
	}
	
	/**
	 * 按单号保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int customerStatementDSaveByNumber(CustomerStatementDto dto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "customerStatementDSaveByNumber",dto);
		return insertDRows;
	}
	
	/**
	 * 按对账单单号保存对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int customerStatementSaveByStatementBillNo(CustomerStatementDto dto) {
		int insertRows = getSqlSession().insert(NAMESPACE + "customerStatementSaveByStatementBillNo",dto);
		return insertRows;
	}

	/**
	 * 按客户查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementEntity> queryCustomerStatementByCustomer(CustomerStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<CustomerStatementEntity> list = getSqlSession().selectList(NAMESPACE + "queryCustomerStatementByCustomer",dto,rb);
		return list;
	}

	/**
	 * 按客户查询对账单总个数
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int countCustomerStatementByCustomer(CustomerStatementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "countCustomerStatementByCustomer",dto);
		return count;
	}

	/**
	 * 按单号查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementEntity> queryCustomerStatementByNumber(CustomerStatementDto dto) {
		List<CustomerStatementEntity> list = getSqlSession().selectList(NAMESPACE + "queryCustomerStatementByNumber",dto);
		return list;
	}
	
	/**
	 * 按对账单单号查询对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementDEntity> queryCustomerDByStatementBillNo(CustomerStatementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<CustomerStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryCustomerDByStatementBillNo",dto,rb);
		return list;
	}
	
	/**
	 * 按对账单单号查询对账单明细总条数
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int countCustomerDByStatementBillNo(CustomerStatementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "countCustomerDByStatementBillNo",dto);
		return count;
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

	/**
	 * 确认或反确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int confirmOrUnConfirmStatement(CustomerStatementDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "confirmOrUnConfirmStatement",dto);
		return updateRows;
	}

	/**
	 * 更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int customerRecUpdateByStatementBillNo(CustomerStatementDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "customerRecUpdateByStatementBillNo",dto);
		return updateRows;
	}
	
	/**
	 * 按对账单单号查询对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementDEntity> queryCustomerDByStatementBillNo(CustomerStatementDto dto) {
		List<CustomerStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryCustomerDByStatementBillNo",dto);
		return list;
	}
	
	/**
	 * 更新对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int customerStatementUpdateByStatementBillNo(CustomerStatementDto customerStatementDto) {
		int updateRows = getSqlSession().update(NAMESPACE + "customerStatementUpdateByStatementBillNo",customerStatementDto);
		return updateRows;
	}
	
	/**
	 * 查询添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementDEntity> queryAddCustomerStatementD(CustomerStatementDto dto) {
		List<CustomerStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryCustomerStatementDByNumber",dto);
		return list;
	}
	
	/**
	 * 查询删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerStatementDEntity> queryDelCustomerStatementD(CustomerStatementDto dto) {
		List<CustomerStatementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryDelCustomerStatementD",dto);
		return list;
	}
	
	/**
	 * 按客户添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int addCustomerStatementDByCustomer(CustomerStatementDto customerStatementDto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "customerStatementDSaveByCustomer",customerStatementDto);
		return insertDRows;
	}
	
	/**
	 * 按单号添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int addCustomerStatementDByNumber(CustomerStatementDto customerStatementDto) {
		int insertDRows = getSqlSession().insert(NAMESPACE + "addCustomerStatementDByNumber",customerStatementDto);
		return insertDRows;
	}

	/**
	 * 按单号删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int delCustomerStatementD(CustomerStatementDto customerStatementDto) {
		int updateRows = getSqlSession().update(NAMESPACE + "delCustomerStatementD",customerStatementDto);
		return updateRows;
	}
	
	/**
	 * 按对账单单号更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public int updateRecStatementBillNo(CustomerStatementDto customerStatementDto) {
		int updateRecRows = getSqlSession().update(NAMESPACE + "updateRecStatementBillNo",customerStatementDto);
		return updateRecRows;
	}
}
