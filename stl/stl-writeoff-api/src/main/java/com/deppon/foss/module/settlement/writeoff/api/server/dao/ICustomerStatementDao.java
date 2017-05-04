/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomerStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementEntity;

public interface ICustomerStatementDao {
	
	/**
	 * 按客户查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementDEntity> queryCustomerStatementDByCustomer(CustomerStatementDto dto, int start,int limit);

	/**
	 * 按来源单号查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementDEntity> queryCustomerStatementDByNumber(CustomerStatementDto dto);
	
	/**
	 * 按客户查询应收单应付单总行数
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int countCustomerStatementDByCustomer(CustomerStatementDto dto);
	
	/**
	 * 按客户保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int customerStatementDSaveByCustomer(CustomerStatementDto dto);
	/**
	 * 按单号保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int customerStatementDSaveByNumber(CustomerStatementDto dto);
	/**
	 * 按对账单单号保存对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int customerStatementSaveByStatementBillNo(CustomerStatementDto dto);

	/**
	 * 按客户查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementEntity> queryCustomerStatementByCustomer(CustomerStatementDto dto, int start, int limit);

	/**
	 * 按客户查询对账单总个数
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int countCustomerStatementByCustomer(CustomerStatementDto dto);

	/**
	 * 按单号查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementEntity> queryCustomerStatementByNumber(CustomerStatementDto dto);
	
	/**
	 * 按对账单单号查询对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementDEntity> queryCustomerDByStatementBillNo(CustomerStatementDto dto, int start, int limit);
	
	/**
	 * 按对账单单号查询对账单明细总条数
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int countCustomerDByStatementBillNo(CustomerStatementDto dto);
	
	/**
	 * 按对账单单号查询核销单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int queryWriteoffBillByStatementBillNo(String statementBillNo);

	/**
	 * 确认或反确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int confirmOrUnConfirmStatement(CustomerStatementDto dto);

	/**
	 * 更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int customerRecUpdateByStatementBillNo(CustomerStatementDto dto);
	
	/**
	 * 按单号查询对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementDEntity> queryCustomerDByStatementBillNo(CustomerStatementDto customerStatementDto);
	
	/**
	 * 更新对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int customerStatementUpdateByStatementBillNo(CustomerStatementDto customerStatementDto);
	
	/**
	 * 查询添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementDEntity> queryAddCustomerStatementD(CustomerStatementDto dto);
	
	/**
	 * 查询删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<CustomerStatementDEntity> queryDelCustomerStatementD(CustomerStatementDto dto);
	
	/**
	 * 按客户添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int addCustomerStatementDByCustomer(CustomerStatementDto customerStatementDto);
	
	/**
	 * 按单号添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int addCustomerStatementDByNumber(CustomerStatementDto customerStatementDto);
	
	/**
	 * 按单号删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int delCustomerStatementD(CustomerStatementDto customerStatementDto);
	
	/**
	 * 按对账单单号更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int updateRecStatementBillNo(CustomerStatementDto customerStatementDto);
	
}