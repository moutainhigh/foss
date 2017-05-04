package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;

public interface IDopStatementDao {
	/**
	 * 按单号查询对账单
	 * @author zya
	 * @date 2015-10-10
	 */
	public List<HomeStatementEntity> queryHomeStatementByNumber(HomeStatementDto dto);
	
	/**
	 * 按单号保存对账单明细
	 * @author zya
	 * @date 2015-10-10
	 */
	public List<HomeStatementDEntity> queryHomeDByStatementBillNo(HomeStatementDto homeStatementDto);
	
	/**
	 * 更新对账单
	 * @author zya
	 * @date 2015-10-10
	 */
	public int homeStatementUpdateByStatementBillNo(HomeStatementDto homeStatementDto);
	
	/**
	 * 根据对账单号去查询部门
	 * @author zya
	 * @date 2015-10-10
	 */
	public List<HomeStatementDEntity> getOrgNameByStatementBillNo(HomeStatementDto homeStatementDto);
}