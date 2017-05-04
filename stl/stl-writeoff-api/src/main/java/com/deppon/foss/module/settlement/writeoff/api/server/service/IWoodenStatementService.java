package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WoodenStatementDto;

public interface IWoodenStatementService {
	/**
	 * 查询代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto queryWoodenStatement(WoodenStatementDto dto, int start, int limit);
	/**
	 * 生成代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto woodenStatementSave(WoodenStatementDto woodenStatementDto);
	/**
	 * 代打木架对账单新增查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto queryWoodenStatementD(WoodenStatementDto woodenStatementDto, int start, int limit);
	/**
	 * 双击对账单记录查看明细，按对账单单号查询
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto queryWoodenDByStatementBillNo(WoodenStatementDto dto, int start, int limit);
	/**
	 * 确认代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public void confirmWoodenStatement(WoodenStatementDto woodenStatementDto);
	/**
	 * 代打木架对账单删除明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto delWoodenStatementD(WoodenStatementDto woodenStatementDto);
	/**
	 * 代打木架对账单添加明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto addWoodenStatementD(WoodenStatementDto woodenStatementDto);
	/**
	 * 代打木架对账单删除明细查询应收应付明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto queryDelWoodenStatementD(WoodenStatementDto dto);
	/**
	 * 代打木架对账单添加明细查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementDto queryAddWoodenStatementD(WoodenStatementDto dto, int start, int limit);
	/**
	 * 更新代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	public void updateStatementForWriteOff(WoodenStatementEntity entity,CurrentInfo cInfo);
	/**
	 * 按对账单单号查询对账单信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementEntity queryByStatementNo(String statementNo);
	/**
	 * 按对账单单号查询对账单明细信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementDEntity> queryByStatementBillNo(String statementNo);
	
	/**
	 * 按对账单单号查询对账单信息 给 付款接口使用
	 * @author ddw
	 * @date 2014-06-16
	 */
	public WoodenStatementEntity queryByStateBillNoForInterface(String statementNo);
	
	/**
	 * 按对账单单号查询对账单信息 
	 * @author ddw
	 * @date 2014-06-16
	 */
	public List<WoodenStatementEntity> queryWoodenStatementByNumber(List<String> statementNoList);
	
}
