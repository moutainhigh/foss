package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WoodenStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;

public interface IWoodenStatementDao {
	/**
	 * 按客户查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementDEntity> queryWoodenStatementDByCustomer(WoodenStatementDto dto, int start,int limit);
	/**
	 * 按单号查询应收单应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementDEntity> queryWoodenStatementDByNumber(WoodenStatementDto dto);
	/**
	 * 按客户查询应收单应付单总行数
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int countWoodenStatementDByCustomer(WoodenStatementDto dto);
	/**
	 * 按客户查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementEntity> queryWoodenStatementByCustomer(WoodenStatementDto dto, int start,int limit);
	/**
	 * 按单号查询对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementEntity> queryWoodenStatementByNumber(WoodenStatementDto dto);
	/**
	 * 按客户查询对账单总个数
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int countWoodenStatementByCustomer(WoodenStatementDto dto);
	/**
	 * 按客户保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int woodenStatementDSaveByCustomer(WoodenStatementDto woodenStatementDto);
	/**
	 * 按单号保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int woodenStatementDSaveByNumber(WoodenStatementDto woodenStatementDto);
	/**
	 * 按对账单单号查询对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementDEntity> queryWoodenDByStatementBillNo(WoodenStatementDto dto, int start, int limit);
	/**
	 * 按对账单单号查询对账单明细总条数
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int countWoodenDByStatementBillNo(WoodenStatementDto dto);
	/**
	 * 更新应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int woodenPayUpdateByStatementBillNo(WoodenStatementDto woodenStatementDto);
	/**
	 * 按对账单单号保存对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int woodenStatementSaveByStatementBillNo(WoodenStatementDto woodenStatementDto);
	/**
	 * 更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int woodenRecUpdateByStatementBillNo(WoodenStatementDto woodenStatementDto);
	/**
	 * 确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int confirmWoodenStatement(WoodenStatementDto woodenStatementDto);
	/**
	 * 反确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int unConfirmWoodenStatement(WoodenStatementDto woodenStatementDto);
	/**
	 * 按单号添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int addWoodenStatementDByNumber(WoodenStatementDto woodenStatementDto);
	/**
	 * 按客户添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int addWoodenStatementDByCustomer(WoodenStatementDto woodenStatementDto);
	/**
	 * 按单号保存对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementDEntity> queryWoodenDByStatementBillNo(WoodenStatementDto woodenStatementDto);
	/**
	 * 更新对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int woodenStatementUpdateByStatementBillNo(WoodenStatementDto woodenStatementDto);
	/**
	 * 查询添加对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementDEntity> queryAddWoodenStatementD(WoodenStatementDto dto);
	/**
	 * 查询删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementDEntity> queryDelWoodenStatementD(WoodenStatementDto dto);
	/**
	 * 按单号删除对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int delWoodenStatementD(WoodenStatementDto woodenStatementDto);
	/**
	 * 按对账单单号更新应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int updatePayStatementBillNo(WoodenStatementDto woodenStatementDto);
	/**
	 * 按对账单单号更新应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int updateRecStatementBillNo(WoodenStatementDto woodenStatementDto);
	/**
	 * 按对账单单号查询核销单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public int queryWriteoffBillByStatementBillNo(String statementBillNo);

}