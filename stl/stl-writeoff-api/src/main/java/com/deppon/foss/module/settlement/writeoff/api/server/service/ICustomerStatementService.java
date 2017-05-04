package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomerStatementDto;

public interface ICustomerStatementService {

	/**
	 * 生成客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto customerStatementSave(CustomerStatementDto dto);
	
	/**
	 * 客户对账单新增查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto queryCustomerStatementD(CustomerStatementDto dto, int start, int limit);
	
	/**
	 * 查询客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto queryCustomerStatement(CustomerStatementDto dto, int start, int limit);
	
	/**
	 * 双击对账单记录查看明细，按对账单单号查询
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto queryCustomerDByStatementBillNo(CustomerStatementDto dto, int start, int limit);
	
	/**
	 * 确认或反确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public void confirmOrUnConfirmStatement(CustomerStatementDto dto);
	
	/**
	 * 按对账单单号查询对账单信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementEntity queryByStatementNo(String statementNo);
	
	/**
	 * 按对账单单号查询对账单明细信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementDEntity> queryByStatementBillNo(String statementNo);
	
	/**
	 * 更新大客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public void updateStatementForWriteOff(CustomerStatementEntity entity,CurrentInfo cInfo);
	
	/**
	 * 大客户对账单添加明细查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto queryAddCustomerStatementD(CustomerStatementDto dto, int start, int limit);
	
	/**
	 * 大客户对账单添加明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto addCustomerStatementD(CustomerStatementDto customerStatementDto);
	
	/**
	 * 大客户对账单删除明细查询应收应付明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto queryDelCustomerStatementD(CustomerStatementDto dto);
	
	/**
	 * 大客户对账单删除明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public CustomerStatementDto delCustomerStatementD(CustomerStatementDto customerStatementDto);
}
